/**
 * 2016-09-13版本清楚快递自动调度
 */
/*package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressAutoScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoScheduleEntity;
import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressHandService;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressOrderService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderBusinessLockService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.OrderMutexElementConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressCityDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderMutexElement;
import com.deppon.foss.module.pickup.order.server.dao.impl.DispatchOrderEntityDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

public class AutoExpressOrderService implements IAutoExpressOrderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoExpressOrderService.class);
	
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	private IAutoExpressHandService autoExpressHandService;
	// 业务异常日志
	private IOrderAutoExceptionLogService orderAutoExceptionLogService;
	// 城市分开关服务
	private IExpressAutoScheduleService expressAutoScheduleService;
	private DispatchOrderEntityDao dispatchOrderEntityDao;
	 //短信通知服务
	private INotifyCustomerService notifyCustomerService;
	//查询短信模板DAO
	private ISMSTempleteDao sMSTempleteDao;
	//查询部门Service
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	//业务互斥锁服务
	private IOrderBusinessLockService orderBusinessLockService;
	//系统配置参数 Service接口
	private IConfigurationParamsService configurationParamsService;
	//快递自动调度关闭，短信费用承担部门
	private static final String EXPRESS_AUTO_CLOSE_PAY_ORG = "W0122190401";
	*//**
	 * 加入线程池
	 * @param changeList
	 * @update zxy 20140716 AUTO-165 修改:按每个订单判断开关
	 *//*
	@Transactional
	@Override
	public void aotoDispatchOrderList(List<DispatchOrderChangeEntity> changeList){
		Iterator<DispatchOrderChangeEntity> iterator = changeList.iterator();
		//获取订单业务锁自动释放时间
		String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
		while(iterator.hasNext()){
			DispatchOrderChangeEntity orderChangeEntity = iterator.next();
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrderById(orderChangeEntity.getChangeId());
			OrderMutexElement mutexElement = null;
			if (StringUtils.isBlank(orderLockTtl)) {
				mutexElement = new OrderMutexElement(dispatchOrderEntity.getOrderNo(), "订单更新_快递自动调度", 
						OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
	     		LOGGER.info("自动受理快递订单"+dispatchOrderEntity.getOrderNo()+"时订单业务锁自动释放时间采用默认值");
			} else {
				mutexElement = new OrderMutexElement(dispatchOrderEntity.getOrderNo(), "订单更新_快递自动调度", 
						OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
			}
			
			//互斥锁定
	     	boolean isLocked = orderBusinessLockService.lock(mutexElement, DispatchOrderStatusConstants.ZERO);	
	     	//如果订单锁定直接走预处理建议
	     	if(!isLocked){
	     		dispatchOrderChangeJobId(orderChangeEntity.getChangeId());
	     		LOGGER.info("自动受理快递订单"+dispatchOrderEntity.getOrderNo()+"时订单已锁定");
	     		continue;
	     	}
			try {
				ExpressCityDto cityDto = getActiveExpressCity(orderChangeEntity.getCityCode()); 
				//如果能找到开关城市，且在有效的时间范围内的订单，则走自动调度，否则走预处理
				if(cityDto != null) {
					if(orderChangeEntity.getChangeTime().after(cityDto.getStartTime())
						&& orderChangeEntity.getChangeTime().before(cityDto.getEndTime())){
							autoExpressHandService.process(dispatchOrderEntity);
					}else if(orderChangeEntity.getChangeTime().before(cityDto.getStartTime())) {
						//早于自动调度开始时间
						sendSmsToCustomer(dispatchOrderEntity, NotifyCustomerConstants.SMS_CODE_AUTODISPATCH_OFF_EARLY);
						LOGGER.info("早于自动调度开启时间,转预处理执行[ID:" + orderChangeEntity.getChangeId() + "]");
						dispatchOrderChangeJobId(orderChangeEntity.getChangeId());
					}else if(orderChangeEntity.getChangeTime().after(cityDto.getEndTime())) {
						//晚于自动调度结束时间
						sendSmsToCustomer(dispatchOrderEntity, NotifyCustomerConstants.SMS_CODE_AUTODISPATCH_OFF_LATE);
						LOGGER.info("晚于自动调度关闭时间,转预处理执行[ID:" + orderChangeEntity.getChangeId() + "]");
						dispatchOrderChangeJobId(orderChangeEntity.getChangeId());
					}
				}else{
					LOGGER.info("未配置城市开关,转预处理执行[ID:" + orderChangeEntity.getChangeId() + "]");
					//把状态改成B
					dispatchOrderChangeJobId(orderChangeEntity.getChangeId());
					addExceptionLog(orderChangeEntity,"MATCH_EXPRESS_PREPROCESS","查不到有效开关,转预处理执行");
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				String exceptionType = "MATCH_EXPRESS_SYS_EXCEPTION";
				String reason = "自动匹配出现系统异常"+e.getMessage();
				//zxy 20140709  内部优化 start 修改:日志完善
				addExceptionLog(orderChangeEntity,exceptionType,reason);
				//zxy 20140709  内部优化 end 修改:日志完善
				continue;
			}finally{
				orderBusinessLockService.unlock(mutexElement);
				LOGGER.info("自动受理订单"+dispatchOrderEntity.getOrderNo()+"结束");
			}
		}
	}
	*//**
	 * 
	 * @Title: addExceptionLog 
	 * @Description: 自动处理异常日志新增
	 * @param @param dispatchOrderEntity
	 * @param @param excptionType
	 * @param @param reason    设定文件 
	 * @return void    返回类型 
	 * @throws
	 *//*
	private void addExceptionLog(DispatchOrderChangeEntity orderChangeEntity,
		String excptionType,String reason){
		// 如果待分配的快递员为空，则新增日常日志，同时转为人工处理
		OrderAutoExceptionLogEntity orderAutoExceptionLogEntity = new OrderAutoExceptionLogEntity();
		orderAutoExceptionLogEntity.setId(UUIDUtils
				.getUUID());
		orderAutoExceptionLogEntity
				.setDispatchOrderId(orderChangeEntity.getChangeId());
		orderAutoExceptionLogEntity
				.setOrderNo("-");
		orderAutoExceptionLogEntity
				.setExceptionType(excptionType);
		orderAutoExceptionLogEntity
				.setExceptionReason(reason);
		orderAutoExceptionLogEntity
				.setCreateTime(new Date());
		orderAutoExceptionLogService
				.insertAutoOrderExceptionLog(orderAutoExceptionLogEntity);		
	}
	
	*//**
	 * 获取开关城市
	 * @Title: getActiveExpressCity
	 * @Description: 快递设置分开关      zxy 20140716 AUTO-165 新增
	 * @param @return 设定文件
	 * @return List<ExpressCityDto> 返回类型
	 * @throws
	 *//*
	private ExpressCityDto getActiveExpressCity(String cityCode) {
		//zxy 20140724 如果城市code为空，则不进行查询
		if(StringUtils.isBlank(cityCode)){
			return null;
		}
		// 如果是总开关，城市结果集合为空
		ExpressAutoScheduleEntity queryEntity = new ExpressAutoScheduleEntity();
		// 设置有效
		queryEntity.setActive("Y");
		queryEntity.setCityCode(cityCode);
		// 取得结果集
		List<ExpressAutoScheduleEntity> autoCityList = expressAutoScheduleService
				.queryExpressAutoScheduleList(queryEntity);
		// 判断结果不为空
		if(CollectionUtils.isNotEmpty(autoCityList)){
			for(ExpressAutoScheduleEntity entity : autoCityList){
				ExpressCityDto cityDto = getExpressCityDto(entity);
				//如果为空，跳出当前循环
				if(null != cityDto){
					return cityDto;
				}
			}
		}
		return null;
	}

	private ExpressCityDto getExpressCityDto(ExpressAutoScheduleEntity entity){
		// 校验传入的参数是否为空
		if(null == entity){
			return null;
		}
		if(StringUtils.isEmpty(entity.getCityCode())){
			return null;
		}
		if(StringUtils.isEmpty(entity.getStartTime())){
			return null;
		}
		if(StringUtils.isEmpty(entity.getEndTime())){
			return null;
		}
		// 定义城市对象
		ExpressCityDto cityDto = new ExpressCityDto();
		// 设置城市编码
		cityDto.setCityCode(entity.getCityCode());
		// 设置起始有效时间
		String startTimeStr = entity.getStartTime();
		Calendar calendar1 = Calendar.getInstance(); 
		String[] strs1 = getHourMinNumArray(startTimeStr);
		int startHourNum = Integer.valueOf(strs1[0]);
		int startMinNum = Integer.valueOf(strs1[1]);		
		calendar1.set(Calendar.HOUR_OF_DAY, startHourNum);
		calendar1.set(Calendar.MINUTE, startMinNum);
		calendar1.set(Calendar.SECOND, 0);
		Date startTime = calendar1.getTime();
		cityDto.setStartTime(startTime);
		
		String endTimeStr = entity.getEndTime();
		Calendar calendar2 = Calendar.getInstance(); 
		String[] strs2 = getHourMinNumArray(endTimeStr);
		int endHourNum = Integer.valueOf(strs2[0]);
		int endMinNum = Integer.valueOf(strs2[1]);		
		calendar2.set(Calendar.HOUR_OF_DAY, endHourNum);
		calendar2.set(Calendar.MINUTE, endMinNum);
		calendar2.set(Calendar.SECOND, 0);
		Date endTime = calendar2.getTime();
		cityDto.setEndTime(endTime);	
		return cityDto;
		
	}
	
	private String[] getHourMinNumArray(String timeStr){
		String[] strs = timeStr.split(":");
		return strs;
	}
	
	*//**
	 * change状态设置成B
	 * @param dispatchOrderEntity
	 *//*
	private void dispatchOrderChangeJobId(String dispatchOrderId) {
		//更新状态B执行原来job
		//标记B关闭状态
		DispatchOrderChangeEntity dispatchOrderChangeEntity = new DispatchOrderChangeEntity();
		dispatchOrderChangeEntity.setChangeId(dispatchOrderId);
		dispatchOrderChangeEntity.setJobId("B");//todo B的值
		dispatchOrderChangeEntityDao.updateChangebyOrderId(dispatchOrderChangeEntity);
	}

	*//**
	 * 发送短信(不在自动调度时间范围内)
	 * @author 219396-foss-chengdaolin
	 * @date 2015-02-04 上午09:06:56
	 *//*
	private void sendSmsToCustomer(DispatchOrderEntity dispatchOrderEntity, String smsCode) {
		if(StringUtils.isEmpty(dispatchOrderEntity.getMobile())||StringUtils.isEmpty(dispatchOrderEntity.getCustomerName())) {
			LOGGER.info("快递自动调度开关关闭，发送短信时客户信息不完整["+dispatchOrderEntity.getOrderNo()+"]");
			return;
		}
		NotificationEntity notificationEntity = new NotificationEntity();
		notificationEntity.setId(dispatchOrderEntity.getId());
		// 设置订单号
		notificationEntity.setWaybillNo(dispatchOrderEntity.getOrderNo());
		// 设置通知模块名(小件订单发送给客户)
		notificationEntity.setModuleName(DispatchOrderStatusConstants.TEMPLATE_EXPRESS_ORDER_TO_CUSTOMER_SMS);
		// 设置通知类型(短信)
		notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		//业务类型
		notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_EXPRESS_AUTO_DISPATCH);
		// 设置通知内容
		notificationEntity.setNoticeContent(this.getSmsContent(smsCode));
		// 设置手机号
		notificationEntity.setMobile(dispatchOrderEntity.getMobile());		
		//设置客户姓名
		notificationEntity.setConsignee(dispatchOrderEntity.getCustomerName());
		// 设置操作人
		notificationEntity.setOperator("System");
		// 设置操作人编号
		notificationEntity.setOperatorNo("000000");
		// 设置操作部门编码(快递营销管理组) 将承担短信费用
		notificationEntity.setOperateOrgCode(EXPRESS_AUTO_CLOSE_PAY_ORG);
		//设置操作部门名
		String operateOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(notificationEntity.getOperateOrgCode()).getName();
		notificationEntity.setOperateOrgName(operateOrgName);
		// 设置操作时间
		notificationEntity.setOperateTime(new Date());
		notifyCustomerService.sendMessage(notificationEntity);
	}
	
	*//**
	 * 获取短信信息(不在自动调度时间范围内)
	 * @author 219396-foss-chengdaolin
	 * @date 2015-02-04 上午09:06:56
	 *//*
	public String getSmsContent(String smsCode) {
		SMSTemplateEntity tempEntity = sMSTempleteDao.querySmsByCode(smsCode);
		return tempEntity.getContent();
	}
	
	public void setDispatchOrderChangeEntityDao(
			IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
	}


	public void setAutoExpressHandService(
			IAutoExpressHandService autoExpressHandService) {
		this.autoExpressHandService = autoExpressHandService;
	}
	public void setOrderAutoExceptionLogService(
			IOrderAutoExceptionLogService orderAutoExceptionLogService) {
		this.orderAutoExceptionLogService = orderAutoExceptionLogService;
	}
	public IExpressAutoScheduleService getExpressAutoScheduleService() {
		return expressAutoScheduleService;
	}
	public void setExpressAutoScheduleService(
			IExpressAutoScheduleService expressAutoScheduleService) {
		this.expressAutoScheduleService = expressAutoScheduleService;
	}
	public DispatchOrderEntityDao getDispatchOrderEntityDao() {
		return dispatchOrderEntityDao;
	}
	public void setDispatchOrderEntityDao(
			DispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}
	public void setsMSTempleteDao(ISMSTempleteDao sMSTempleteDao) {
		this.sMSTempleteDao = sMSTempleteDao;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setOrderBusinessLockService(
			IOrderBusinessLockService orderBusinessLockService) {
		this.orderBusinessLockService = orderBusinessLockService;
	}
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
}
*/
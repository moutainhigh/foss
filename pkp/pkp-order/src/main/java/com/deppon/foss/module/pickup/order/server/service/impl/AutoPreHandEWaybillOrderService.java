package com.deppon.foss.module.pickup.order.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICourierScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderThreadPoollogDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPreHandEWaybillOrderDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressAcceptService;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoPreHandEWaybillOrderService;
import com.deppon.foss.module.pickup.order.api.server.service.IExpressWorkerStatusService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderPdaReturnRecordService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderReportService;
import com.deppon.foss.module.pickup.order.api.server.service.IRegionCourierReportService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderActionHistoryEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderThreadPoollogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PreHandEWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.order.api.shared.util.NumberResolutionFloor;
import com.deppon.foss.module.pickup.order.api.shared.util.PropertyFactory;
import com.deppon.foss.module.pickup.order.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnBillTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 电子运单预处理线程池:用于取代自动调度线程池的问题
 * @author Foss-105888-Zhangxingwang
 * @date 2015-4-17 19:23:14
 */
public class AutoPreHandEWaybillOrderService extends OrderTheadPool implements IAutoPreHandEWaybillOrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoPreHandEWaybillOrderService.class);
	/**
	 * 预处理电子运单数据持久层
	 */
	private IPreHandEWaybillOrderDao preHandEWaybillOrderDao;
	
	/**
	 * 电子运单订单数据持久层
	 */
	private IEWaybillOrderEntityDao ewaybillOrderEntityDao;
	
	/**
	 * 查询CRM订单服务类
	 */
	private ICrmOrderService crmOrderService;
	
	/**
	 *  快递订单自动受理服务
	 */
	private IAutoExpressAcceptService autoExpressAcceptService;
	
	/**
	 *  线程池异常日志
	 */
	private IOrderThreadPoollogDao orderThreadPoollogDao;
	
	/**
	 *  业务异常日志
	 */
	private IOrderAutoExceptionLogService orderAutoExceptionLogService;
	
	/**
	 *  配置
	 */
	private PropertyFactory propertyFactory;
	
	/**
	 *  每日统计报表服务
	 */
	private IOrderReportService orderReportService;
	
	/**
	 *  每日PDA退回记录服务
	 */
	private IOrderPdaReturnRecordService orderPdaReturnRecordService;
	
	/**
	 *  查询收派小区服务
	 */
	private IExpressDeliverySmallZoneService expressDeliverySmallZoneService;
	
	/**
	 *  快递排班信息service
	 */
	private ICourierScheduleService courierScheduleService;
	
	/**
	 *  快递员工作状态服务
	 */
	private IExpressWorkerStatusService expressWorkerStatusService;
	
	/**
	 *  订单dao
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	
	/**
	 *  应用监控服务
	 */
	private IBusinessMonitorService businessMonitorService;
	
	/**
	 * 订单可视化查询服务 
	 */
	private IRegionCourierReportService regionCourierReportService;
	
	/**
	 * 客户合同信息Service接口
	 */
	private ICusBargainService cusBargainService;
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 员工信息服务类
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 合同适用部门Service接口
	 */
	private IBargainAppOrgService bargainAppOrgService;
	
	/**
	 * 营业部服务类
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 快递员车辆服务类
	 */
	private IExpressVehiclesService expressVehiclesService;
	
	/**
	 * 快递点部Service
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	
	/**
	 * 调度订单操作历史Service
	 */
	private IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao;
	
	/**
	 * 派车记录Service
	 */
	private IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao;
	
	/**
	 * 约车申请Service
	 */
	private IOrderVehicleService orderVehicleService;
	
	/**
	 * CRM订单数据JMS服务Service
	 */
	private ICrmOrderJMSService crmOrderJMSService;
	
	/**
	 * 快递车牌查询公共选择器Service
	 */
	private ICommonExpressVehicleService commonExpressVehicleService;
	
	
	private IAutoPreHandEWaybillOrderService autoPreHandEWaybillOrderService;
	
	/**
	 * 子母件服务service
	 */
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	/**
	 * 获取配置参数服务
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * 批量进行核心业务的操作
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-17 19:22:23
	 */
	@Override
	public void businessExecutor(Object obj) {
		//进行数据转换
		String jobId = (String) obj;
		LOGGER.info("本次执行任务ID为:" + jobId);
		//预处理数据的查询
		List<PreHandEWaybillOrderEntity> preHandOrderList = this.queryGeneratePreEWaybillOrderByJobID(jobId);
		if(CollectionUtils.isEmpty(preHandOrderList)){
			return;
		}
		//进行数据的循环操作
		for(PreHandEWaybillOrderEntity preHandEWaybillOrderEntity : preHandOrderList){
			try {
				autoPreHandEWaybillOrderService.singleHandlePreHandEWaybillOrder(preHandEWaybillOrderEntity);
			} catch (Exception e) {
				//进行异常信息的捕捉
				LOGGER.info(preHandEWaybillOrderEntity.getOrderNo()+"执行出错，异常详情"+e.getMessage());
				String error = ExceptionUtils.getFullStackTrace(e);
				if (error != null && error.length() > SettlementReportNumber.FOUR_HUNDRED_AND_NINETY) {	
					error = error.substring(0, SettlementReportNumber.FOUR_HUNDRED_AND_NINETY);
				}
				//进行数据的更新
				preHandEWaybillOrderEntity.setExceptionMsg(error);
				preHandEWaybillOrderEntity.setStatus(FossConstants.NO);
				preHandEWaybillOrderDao.updatePreEWaybillOrderByIdSelective(preHandEWaybillOrderEntity);
			}
		}
		LOGGER.info("本次执行任务执行完毕,任务ID为:" + jobId);
	}
	
	/**
	 * 主要进行两大数据的操作:1、分配对应的快递员给订单数据;2、新增Ewaybill_Order数据的插入
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-17 18:55:28 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void singleHandlePreHandEWaybillOrder(PreHandEWaybillOrderEntity preHandEWaybillOrderEntity) {
		//订单号
		String orderNo = preHandEWaybillOrderEntity.getOrderNo();
		LOGGER.info("============电子运单订单调度预处理开始,订单号:"+orderNo);
		//订单号为空，根本无法继续受理，直接跳过执行
		if(StringUtils.isEmpty(orderNo)){
			preHandEWaybillOrderEntity.setExceptionMsg("preHandEWaybillOrder's orderNo is null");
			preHandEWaybillOrderEntity.setStatus(FossConstants.NO);//设置状态为为未受理
			preHandEWaybillOrderDao.updatePreEWaybillOrderByIdSelective(preHandEWaybillOrderEntity);
			return;
		}
		boolean isCreate = false;
		EWaybillOrderEntity ewaybillOrder = ewaybillOrderEntityDao.queryEWaybillByOrderNo(orderNo);
		//如果EwaybillOrder 表中存在，则更新ewaybillOrder，由待激活线程池跑
		if(ewaybillOrder != null){
			isCreate = true;
		}
		//查询订单数据
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderNo);
		//判定订单数据是否为空
		if(dispatchOrderEntity == null){
			LOGGER.info("dispatchOrder entity is null");
			preHandEWaybillOrderEntity.setExceptionMsg("dispatchOrder entity is null");
			preHandEWaybillOrderEntity.setStatus(FossConstants.NO);//设置状态为为未受理
			preHandEWaybillOrderDao.updatePreEWaybillOrderByIdSelective(preHandEWaybillOrderEntity);
			return;
		}else if(!WaybillConstants.EWAYBILL.equals(dispatchOrderEntity.getWaybillType())){
			LOGGER.info("dispatchOrder entity is ewaybill order");
			//非电子运单无法走此流程
			preHandEWaybillOrderEntity.setExceptionMsg("dispatchOrder entity is ewaybill order");
			preHandEWaybillOrderEntity.setStatus(FossConstants.NO);//设置状态为为未受理
			preHandEWaybillOrderDao.updatePreEWaybillOrderByIdSelective(preHandEWaybillOrderEntity);
			return;
		}
		/**
		 * @author 297064 2016-04-18 
		 * @discription 注释结算分配快递,约车接口,快递此功能将迁移只OMS系统
		//这里主要进行两大数据的操作：1、分配对应的快递员给订单数据;2、新增Ewaybill_Order数据的插入
		//1、分配对应的快递员给订单数据
		boolean isMatchSuccess =matchSuitExpressCode(dispatchOrderEntity, preHandEWaybillOrderEntity);
		//考虑问题根源：存在一些异常订单数据需要进行ewaybillOrder的更新
		if(isCreate){
			//这里不需要管订单调度的问题是否成功
			addEwaybillOrderEntity(dispatchOrderEntity, preHandEWaybillOrderEntity, ewaybillOrder);
			//判断是否子母件单子
			if(DispatchOrderStatusConstants.ISPICPACKAGE.equals(dispatchOrderEntity.getIsPicPackage())){
				//1.判断ewaybill_relate存在
				//2.先判断pda_scan,存在才添加，不存在就不添加
				addEwaybillRelateEntityData(dispatchOrderEntity.getOriginalNumber(),dispatchOrderEntity.getOrderNo());
			}
		}else{
			//需要考虑调度是否成功的问题
			if(isMatchSuccess){
				addEwaybillOrderEntity(dispatchOrderEntity, preHandEWaybillOrderEntity, ewaybillOrder);
				//判断是否子母件单子
				if(DispatchOrderStatusConstants.ISPICPACKAGE.equals(dispatchOrderEntity.getIsPicPackage())){
					//1.判断ewaybill_relate存在
					//2.先判断pda_scan,存在才添加，不存在就不添加
					addEwaybillRelateEntityData(dispatchOrderEntity.getOriginalNumber(),dispatchOrderEntity.getOrderNo());
				}
			}else{
				throw new WaybillValidateException(WaybillValidateException.ORDER_PRE_HANDLE_FAULURE);
			}
		}
		*/
		// 这里不需要管订单调度的问题是否成功
		addEwaybillOrderEntity(dispatchOrderEntity,preHandEWaybillOrderEntity, ewaybillOrder);
		// 判断是否子母件单子
		if (DispatchOrderStatusConstants.ISPICPACKAGE.equals(dispatchOrderEntity.getIsPicPackage())) {
			// 1.判断ewaybill_relate存在
			// 2.先判断pda_scan,存在才添加，不存在就不添加
			addEwaybillRelateEntityData(dispatchOrderEntity.getOriginalNumber(),dispatchOrderEntity.getOrderNo());
		}
		LOGGER.info("===========电子运单订单调度预处理结束,订单号:"+orderNo);
	}

	/**
	 * 处理电子运单，并不发送短信，不推送微信、德邦APP等其他周边系统消息
	 * (因为随机分配的快递员其实数据不准确，避免减少误会，不发送消息)
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-4 16:28:36
	 * @param orderHandleDto
	 */
	@Override
	public boolean acceptOrder(OrderHandleDto orderHandleDto) {
		boolean success = false;
		try{
			orderHandleDto.setIsCustomer(FossConstants.YES);
			orderHandleDto.setIsSms(FossConstants.NO);
			// 设置状态为已派车
			orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
			// 发送中
			orderHandleDto.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDING);
			//14.7.30 gcl AUTO-212 设置订单受理方式为自动
			orderHandleDto.setAcceptStatus(DispatchOrderStatusConstants.ORDER_ACCEPT_STATUS_SYS);
			//14.7.21 gcl AUTO-184
			if(StringUtils.isEmpty(orderHandleDto.getDriverName())){
				String driverName = employeeService.queryEmpNameByEmpCode(orderHandleDto.getDriverCode());
				orderHandleDto.setDriverName(driverName);
			}
			int count = dispatchOrderEntityDao.updateByExpressOrderHandle(orderHandleDto);
			if (count == 1) {
				try{
					if(StringUtils.isEmpty(orderHandleDto.getOperatorCode())){
						orderHandleDto.setOperatorCode(orderHandleDto.getDriverCode());//14.7.16 gclAUTO-163
					}
					// 添加订单操作历史
					addDispatchOrderActionHistory(orderHandleDto);
					// 添加派车记录
					addDispatchVehicleRecord(orderHandleDto);
				}catch(Exception e){
					String exceptionType = "AUTO_ACCEPT_ERROR";
					String reason = "快递订单添加订单历史记录/派车记录报错";	
					LOGGER.info(orderHandleDto.getOrderNo()+":"+e.getMessage());
					addExceptionLog(orderHandleDto,exceptionType,reason);	
				}
				
				// 更新外部系统状态
				try{
					LOGGER.info("更新外部系统开始！订单号"+orderHandleDto.getOrderNo());
					updateExternalSystem(orderHandleDto, null);
					LOGGER.info("更新外部系统结束！订单号"+orderHandleDto.getOrderNo());
				}catch(Exception e){
					String exceptionType = "UPDATE_CRM_ERROR";
					String reason = "更新外部系统报错";	
					addExceptionLog(orderHandleDto,exceptionType,reason);	
				}	
				success = true;
				LOGGER.info("快递订单自动受理成功！！！");
			} else {
				// 更新调度订单失败、添加失败详细信息 
				success = false;
				String exceptionType = "AUTO_ACCEPT_ERROR";
				String reason = "快递订单自动受理,更新订单状态为已派车时失败！";	
				LOGGER.info(orderHandleDto.getOrderNo()+":"+reason);
				addExceptionLog(orderHandleDto,exceptionType,reason);	
				return success;
			}
		}catch(Exception e){
			// 更新调度订单失败、添加失败详细信息 
			success = false;
			String exceptionType = "AUTO_ACCEPT_ERROR";
			String reason = orderHandleDto.getOrderNo()+"快递订单自动受理,更新订单状态为已派车时，抛异常失败！"+e.getMessage();	
			LOGGER.info(orderHandleDto.getOrderNo()+":"+e.getMessage());
			addExceptionLog(orderHandleDto,exceptionType,reason);	
			return success;
		}
		return success;
	}

	/**
	 * 分配合适的快递员等信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-18 11:28:20
	 * @param dispatchOrderEntity
	 */
	private boolean matchSuitExpressCode(DispatchOrderEntity dispatchOrderEntity, PreHandEWaybillOrderEntity preHandEWaybillOrderEntity) {
		boolean isMatchSuccess = false;
		//异常信息
		String failReson = null;
		Exception exception = null;
		try{
			// 返回对应定人定区快递员或机动快递员
			LOGGER.info("businessExecutor:" + dispatchOrderEntity.getOrderNo());
			Map<String, Object> expMap = matchExpress(dispatchOrderEntity);
			if(null != expMap && expMap.size() > 0){
				// 快递订单预处理
				OrderHandleDto orderHandleDto = getOrderHandleDto(dispatchOrderEntity, expMap);
				// 判断不为空，将订单进行自动受理
				if (null == orderHandleDto) {
					return false;
				}
				// 线程受理订单调用开始
				boolean success = this.acceptOrder(orderHandleDto);
				if(success){
					LOGGER.info("线程自动受理成功。。。。。。");
					isMatchSuccess = true;
				}else{
					LOGGER.info("线程自动受理失败！！！！！！");
					try {
						setPreReceiveOrgInfo(dispatchOrderEntity, preHandEWaybillOrderEntity);
						isMatchSuccess = true;
					}catch(Exception e){
						exception = e;
						isMatchSuccess = false;
					}
				}
			}else{
				try{
					setPreReceiveOrgInfo(dispatchOrderEntity, preHandEWaybillOrderEntity);
					isMatchSuccess = true;
				}catch(Exception e){
					exception = e;
					isMatchSuccess = false;
				}
			}
		}catch (Exception e){
			try{
				setPreReceiveOrgInfo(dispatchOrderEntity, preHandEWaybillOrderEntity);
				isMatchSuccess = true;
			}catch (Exception e2){
				exception = e;
				isMatchSuccess = false;
			}
		}
		//进行数据的统一处理
		//处理成功，更新为成功
		if(isMatchSuccess){
			preHandEWaybillOrderEntity.setExceptionMsg(FossConstants.NO);
			preHandEWaybillOrderEntity.setStatus(FossConstants.YES);
			preHandEWaybillOrderDao.updatePreEWaybillOrderById(preHandEWaybillOrderEntity);
		}else{
			//更新为失败
			String error = ExceptionUtils.getFullStackTrace(exception);
			if (StringUtils.isNotEmpty(error) && error.length() > SettlementReportNumber.FOUR_HUNDRED_AND_NINETY) {				
				failReson = error.substring(0, SettlementReportNumber.FOUR_HUNDRED_AND_NINETY);
			} else {
				failReson = error;
			}
			isMatchSuccess = false;
			preHandEWaybillOrderEntity.setExceptionMsg(failReson);
			preHandEWaybillOrderEntity.setStatus(FossConstants.NO);
			preHandEWaybillOrderDao.updatePreEWaybillOrderById(preHandEWaybillOrderEntity);
		}
		return isMatchSuccess;
	}
	
	/**
	 * //月结客户如果没有绑定部门，则归属部门为收货部门；
	 * //月结客户有绑定部门，则根据快递员所在点部匹配点部营业部映射关系，匹配出的营业部和归属部门、绑定部门进行比对，
	 * //比对成功为收货部门；如果比对出来再有多个，优先选定归属部门，如都为绑定部门，则系统随机选定一个；
	 * //如果比对不成功，则月结客户归属部门、绑定部门所在城市和快递员所在城市相同的部门为收货部门，
	 * //如果出现多个优先选定归属部门，如都为绑定部门，则系统随机选定一个；
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-24 18:25:23
	 * @param dispatchOrderEntity
	 * @param preHandEWaybillOrderEntity
	 */
	private void setPreReceiveOrgInfo(DispatchOrderEntity dispatchOrderEntity, PreHandEWaybillOrderEntity preHandEWaybillOrderEntity){
		//设置订单表信息开始
		LOGGER.info("设置订单表快递员与对应车牌号信息开始");
		//进行数据的校验
		if(StringUtils.isEmpty(dispatchOrderEntity.getReceiveOrgCode()) && StringUtils.isEmpty(dispatchOrderEntity.getDeliveryCustomerCode())){
			LOGGER.info("收货部门编码，发货客户编码不能同时为空.订单号为:"+dispatchOrderEntity.getOrderNo());
			throw new WaybillValidateException(WaybillValidateException.NOT_ALL_NULL_RECEIVEORGCODE_DELIVER_CUSTOMER_CODE);
		}
		//设置是否继续查询
		boolean isContinue = true;
		//设置收货部门编码
		String receiveOrgCode = null;
		if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveOrgCode())){
			//根据部门编码获取对应点部的快递员
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dispatchOrderEntity.getReceiveOrgCode());
			//查询结果是否为空，如果为空，再次判定客户编码是否为空，如果为空，只好抛错
			if(orgInfo == null){
				if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerCode())){
					isContinue = true;
				}else{
					throw new WaybillValidateException(WaybillValidateException.DEPARTMENTENTITY_NULL);
				}
			}
			//获取随机数，不要怪我，我只是让系统抉择。Math.random()取数范围为[0,1)之间,完全没必要担心数组越界问题
			if(orgInfo != null){
				//是否为快递点部
				if(FossConstants.YES.equals(orgInfo.getExpressPart())){
					//抽取一个快递员即可
					EmployeeEntity entity = new EmployeeEntity();
					entity.setOrgCode(orgInfo.getCode());
					entity.setActive(FossConstants.YES);
					List<EmployeeEntity> list = employeeService.queryEmployeeByEntity(entity , 0, SettlementReportNumber.TEN);
					if(CollectionUtils.isNotEmpty(list)){
						ExpressVehiclesEntity expressVehiclesEntity = getRandomExpressVehiclesEntity(list);
						if(expressVehiclesEntity != null){
							dispatchOrderEntity.setDriverCode(expressVehiclesEntity.getEmpCode());
							dispatchOrderEntity.setVehicleNo(expressVehiclesEntity.getVehicleNo());
						}
						//根据快递点部随机找到对应一个所辐射的营业部
						receiveOrgCode = getRandomReceiveOrgCodeByExpressPartOrgCode(orgInfo.getExpressPart());
						if(StringUtils.isNotEmpty(receiveOrgCode)){
							//获取对应的营业部
							dispatchOrderEntity.setReceiveOrgCode(receiveOrgCode);
							isContinue = false;
						}
					}else{
						if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerCode())){
							isContinue = true;
						}else{
							throw new WaybillValidateException(WaybillValidateException.DEPARTMENTENTITY_NULL);
						}
					}
				}else{
					//如果不是对应的快递点部，则分两种可能，一个是卫星点，一个是营业部
					SaleDepartmentEntity salesDepartment = saleDepartmentService.querySaleDepartmentByCode(orgInfo.getCode());
					if(salesDepartment == null){
						if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerCode())){
							isContinue = true;
						}else{
							throw new WaybillValidateException(WaybillValidateException.DEPARTMENTENTITY_NULL);
						}
					}else{
						//如果是卫星点
						if(FossConstants.YES.equals(salesDepartment.getSatelliteDept())){
							receiveOrgCode = orgInfo.getParentOrgCode();
						}else{
							receiveOrgCode = orgInfo.getCode();
						}
						ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto = getRandomexpressPartOrgCodeBySalesDepartInfo(receiveOrgCode, new Date());
						isContinue = validaDispatchExtracted(
								dispatchOrderEntity, isContinue,
								receiveOrgCode, expressPartSalesDeptResultDto);
					}
				}
				
			}
		}
		//如果上述没有查询到数据，而发货客户编码没有，则需要抛错
		if(isContinue && StringUtils.isEmpty(dispatchOrderEntity.getDeliveryCustomerCode())){
			LOGGER.info("订单号为:"+dispatchOrderEntity.getOrderNo()+"根据收货部门没有查询到快递员信息"+"收货部门编码:"+dispatchOrderEntity.getReceiveOrgCode());
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_ORG_NULL);
		}
		//上述执行不成功才走下面的方法
		if(isContinue && StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerCode())){
			//获取合同实体
			CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(dispatchOrderEntity.getDeliveryCustomerCode());
			if(null == cusBargainEntity){
				LOGGER.info("根据客户编码为:"+dispatchOrderEntity.getDeliveryCustomerCode()+"查询不到对应的合同信息");
				throw new WaybillValidateException(WaybillValidateException.CUSTOMER_BARGAIN_NULL);
			} 
			//归属部门unifiedCode编码
			String cusBarginSaleDeptUnifiedCode = cusBargainEntity.getUnifiedCode();
			//归属部门编码
			String cusBarginSaleDeptCode = null;
			OrgAdministrativeInfoEntity cusBarginSaleDept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(cusBarginSaleDeptUnifiedCode);
			if(cusBarginSaleDept == null){
				LOGGER.info("根据归属部门标杆编码为:"+cusBarginSaleDeptUnifiedCode+"查询不到对应的组织信息");
				throw new WaybillValidateException(WaybillValidateException.RECEIVE_SALESDEPARTMENT_UNMATCHED);
			}
			cusBarginSaleDeptCode = cusBarginSaleDept.getCode();
			
			//合同适用部门List
			List<BargainAppOrgEntity> applicateOrgEntityList = bargainAppOrgService.queryAppOrgByBargainCrmId(cusBargainEntity.getCrmId(),null);
			//合同适用部门编码List
			List<String> applicateOrgCodeList = null;
			//合同适用部门组织List
			List<OrgAdministrativeInfoEntity> applicateOrgList = null;
			
			OrgAdministrativeInfoEntity applicateOrg = null;
			if(CollectionUtils.isNotEmpty(applicateOrgEntityList)){
				applicateOrgCodeList = new ArrayList<String>();
				applicateOrgList = new ArrayList<OrgAdministrativeInfoEntity>();
				for (BargainAppOrgEntity bargainAppOrgEntity : applicateOrgEntityList) {
					if (bargainAppOrgEntity != null && StringUtils.isNotEmpty(bargainAppOrgEntity.getUnifiedCode())) {
						applicateOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(bargainAppOrgEntity.getUnifiedCode());
						if (applicateOrg != null && StringUtils.isNotEmpty(applicateOrg.getCode())) {
							applicateOrgCodeList.add(applicateOrg.getCode());
							applicateOrgList.add(applicateOrg);
						}
					}
				}
			}
			
			//归属部门所在城市编码
			String  cusBarginSaleDeptCityCode = queryRegionsBySaleDeptCode(cusBarginSaleDeptCode);
			//绑定部门(合同适用部门)所在城市编码list
			List<String> applicateOrgCityCodeList = null;
			//OrgAdministrativeInfoEntity applicateOrg = null;
			if(CollectionUtils.isNotEmpty(applicateOrgEntityList)){
				applicateOrgCityCodeList = new ArrayList<String>();
				for(BargainAppOrgEntity bargainAppOrgEntity : applicateOrgEntityList){
					if(null != bargainAppOrgEntity && StringUtils.isNotBlank(bargainAppOrgEntity.getUnifiedCode())){
						applicateOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(bargainAppOrgEntity.getUnifiedCode());
						if(applicateOrg != null && StringUtils.isNotEmpty(applicateOrg.getCode())){
							applicateOrgCityCodeList.add(queryRegionsBySaleDeptCode(applicateOrg.getCode()));
						}
					}
				}
			}
			//获得绑定部门
			if(StringUtils.isBlank(cusBargainEntity.getApplicateOrgId())){
				//没有绑定部门，则归属部门为收货部门
				dispatchOrderEntity.setReceiveOrgCode(cusBarginSaleDeptCode);
				receiveOrgCode = cusBarginSaleDeptCode;
			}else{
				//如果部门对比不成功
				if(StringUtils.isBlank(dispatchOrderEntity.getReceiveOrgCode())){
					//月结客户归属部门、绑定部门所在城市和快递员所在城市相同的部门为收货部门
					if(StringUtils.isNotEmpty(cusBarginSaleDeptCityCode) &&  cusBarginSaleDeptCityCode.equals(dispatchOrderEntity.getPickupCityCode())){		
						dispatchOrderEntity.setReceiveOrgCode(cusBarginSaleDeptCode);
						receiveOrgCode = cusBarginSaleDeptCode;
					}else{
						if(CollectionUtils.isNotEmpty(applicateOrgList)){
							//如果出现多个优先选定归属部门，如都为绑定部门，则系统随机选定一个					
							for(OrgAdministrativeInfoEntity tempOrg : applicateOrgList){	
								//适用部门城市对比快递员所在城市
								if(StringUtils.isNotEmpty(tempOrg.getCityCode()) && tempOrg.getCityCode().equals(dispatchOrderEntity.getPickupCityCode())){
									dispatchOrderEntity.setReceiveOrgCode(tempOrg.getCode());
									receiveOrgCode = tempOrg.getCode();
									break;
								}
							}
						}
					}
				}
			}
			//随机获取一个快递员
			//现将对应部门信息直接赋值给对应的收货部门，再随机挑选一个快递员
			ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto = getRandomexpressPartOrgCodeBySalesDepartInfo(dispatchOrderEntity.getReceiveOrgCode(), new Date());
			if(expressPartSalesDeptResultDto == null || StringUtils.isEmpty(expressPartSalesDeptResultDto.getPartCode())){
				LOGGER.info("根据收货部门编码查询对应的快递点部信息不存在,收货部门编码为:"+dispatchOrderEntity.getReceiveOrgCode());
				throw new WaybillValidateException(WaybillValidateException.CAN_NOT_EXPRESS_SALEDEPT_UNTEST);
			}else{
				//抽取一个快递员即可
				EmployeeEntity employeeEntity = new EmployeeEntity();
				employeeEntity.setOrgCode(expressPartSalesDeptResultDto.getPartCode());
				employeeEntity.setActive(FossConstants.YES);
				List<EmployeeEntity> employeeEntityList = employeeService.queryEmployeeByEntity(employeeEntity , 0, SettlementReportNumber.TEN);
				ExpressVehiclesEntity expressVehiclesEntity = getRandomExpressVehiclesEntity(employeeEntityList);
				if(expressVehiclesEntity == null){
					LOGGER.info("根据快递点部编码查询对应的快递员信息不存在,快递点部编码为:"+expressPartSalesDeptResultDto.getPartCode());
					throw new WaybillValidateException(WaybillValidateException.CAN_NOT_EXPRESS_SALEDEPT_UNTEST);
				}else{
					LOGGER.info("根据快递点部编码查询对应的快递员信息为:"+expressVehiclesEntity.getEmpCode()+expressVehiclesEntity.getVehicleNo());
					//设置快递员工号与车牌号
					dispatchOrderEntity.setDriverCode(expressVehiclesEntity.getEmpCode());
					dispatchOrderEntity.setVehicleNo(expressVehiclesEntity.getVehicleNo());
					//获取对应的营业部
					dispatchOrderEntity.setReceiveOrgCode(receiveOrgCode);
				}
			}
		}
		if(StringUtils.isEmpty(dispatchOrderEntity.getReceiveOrgCode())){
			LOGGER.info("根据订单信息没有查询到有效的收入部门信息,客户编码为:"+dispatchOrderEntity.getDeliveryCustomerCode());
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_SALESDEPARTMENT_UNMATCHED);
		}
		//查询对应的收货部门名称
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dispatchOrderEntity.getReceiveOrgCode());
		if(orgInfo != null){
			dispatchOrderEntity.setReceiveOrgName(orgInfo.getName());
		}
		Map<String, Object> expMap = new HashMap<String, Object>();
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(dispatchOrderEntity.getDriverCode());
		// 匹配司机，自动匹配规则
		expMap.put("driverCode", dispatchOrderEntity.getDriverCode());
		expMap.put("driverName", dispatchOrderEntity.getDriverName());
		expMap.put("vehicleNo", dispatchOrderEntity.getVehicleNo());
		if(employee != null && StringUtils.isNotEmpty(employee.getMobilePhone())){
			expMap.put("driverPhone", employee.getMobilePhone());
		}
		//expMap.put("pickupRegionCode", value);
		//expMap.put("pickupRegionName", value);
		OrderHandleDto orderHandleDto = getOrderHandleDto(dispatchOrderEntity, expMap );
		if(!this.acceptOrder(orderHandleDto)){
			LOGGER.info("根据订单信息没有查询到有效的收入部门信息,客户编码为:"+dispatchOrderEntity.getDeliveryCustomerCode());
			throw new WaybillValidateException(WaybillValidateException.EWAYBILL_ORDER_ACCEPT_FAIL);
		}
		LOGGER.info("设置订单表快递员与对应车牌号信息结束");
	}

	private boolean validaDispatchExtracted(
			DispatchOrderEntity dispatchOrderEntity, boolean isContinue,
			String receiveOrgCode,
			ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto) {
		if(expressPartSalesDeptResultDto == null || StringUtils.isEmpty(expressPartSalesDeptResultDto.getPartCode())){
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerCode())){
				isContinue = true;
			}else{
				throw new WaybillValidateException(WaybillValidateException.CAN_NOT_EXPRESS_SALEDEPT_UNTEST);
			}
		}else{
			//抽取一个快递员即可
			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setOrgCode(expressPartSalesDeptResultDto.getPartCode());
			employeeEntity.setActive(FossConstants.YES);
			List<EmployeeEntity> employeeEntityList = employeeService.queryEmployeeByEntity(employeeEntity , 0, SettlementReportNumber.TEN);
			ExpressVehiclesEntity expressVehiclesEntity = getRandomExpressVehiclesEntity(employeeEntityList);
			if(expressVehiclesEntity == null){
				if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerCode())){
					isContinue = true;
				}else{
					throw new WaybillValidateException(WaybillValidateException.CAN_NOT_EXPRESS_SALEDEPT_UNTEST);
				}
			}else{
				//设置快递员工号与车牌号
				dispatchOrderEntity.setDriverCode(expressVehiclesEntity.getEmpCode());
				dispatchOrderEntity.setVehicleNo(expressVehiclesEntity.getVehicleNo());
				//获取对应的营业部
				dispatchOrderEntity.setReceiveOrgCode(receiveOrgCode);
				isContinue = false;
			}
		}
		return isContinue;
	}
	
	/**
	 * 根据员工列表随机查询出一条车辆信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-23 11:18:16
	 * @param employeeEntityList
	 * @return
	 */
	private ExpressVehiclesEntity getRandomExpressVehiclesEntity(List<EmployeeEntity> employeeEntityList){
		if(CollectionUtils.isEmpty(employeeEntityList)){
			return null;
		}
		ExpressVehiclesEntity expressVehiclesEntity = null;
		int randumInt = (int) (Math.random() * employeeEntityList.size());
		//必须通过快递员来查其车辆，进而匹配组织部门，不能通过车牌号，因为在激活时，有可能快递员更改，但是车牌号没更改的情况	
		ExpressVehiclesEntity expressVehiclesEntityPara = new ExpressVehiclesEntity();
		LOGGER.info("随机获取的数字为:"+randumInt);
		expressVehiclesEntityPara.setEmpCode(employeeEntityList.get(randumInt).getEmpCode());
		expressVehiclesEntityPara.setActive(WaybillConstants.YES);
		List<ExpressVehiclesEntity> expressVehiclesEntityList = 
				expressVehiclesService.queryExpressVehiclesByEntity(expressVehiclesEntityPara);
		if(CollectionUtils.isNotEmpty(expressVehiclesEntityList)){
			expressVehiclesEntity = expressVehiclesEntityList.get(0);
		}else{
			//如果获取的快递员没有获取到对应的车辆信息，需将所有员工信息循环一遍进行数据的查询
			for(EmployeeEntity employeeEntity : employeeEntityList){
				expressVehiclesEntityPara.setEmpCode(employeeEntity.getEmpCode());
				expressVehiclesEntityList = expressVehiclesService.queryExpressVehiclesByEntity(expressVehiclesEntityPara);
				if(CollectionUtils.isNotEmpty(expressVehiclesEntityList)){
					randumInt = (int) (Math.random() * expressVehiclesEntityList.size());
					LOGGER.info("随机获取的数字为:"+randumInt);
					expressVehiclesEntity = expressVehiclesEntityList.get(randumInt);
					break;
				}
			}
		}
		return expressVehiclesEntity;
	}
	
	/**
	 * 根据收货部门找到对应所对应的快递点部部门信息
	 * @param salesDeptCode
	 * @param billTime
	 * @return
	 */
	private ExpressPartSalesDeptResultDto getRandomexpressPartOrgCodeBySalesDepartInfo(String salesDeptCode, Date billTime){
		ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto =
				expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(salesDeptCode, billTime);
		if(expressPartSalesDeptResultDto == null){
			return null;
		}
		return expressPartSalesDeptResultDto;
	}
	/**
	 * 随机获取一个收货部门
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-23 11:01:35
	 * @param expressPartOrgCode
	 * @return
	 */
	private String getRandomReceiveOrgCodeByExpressPartOrgCode(String expressPartOrgCode){
		String receiveOrgCode = null;
		int randumInt = 0;
		//根据点部查询对应辐射的营业部
		ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto = new ExpressPartSalesDeptQueryDto();
		if(StringUtils.isNotEmpty(expressPartOrgCode)){
			expressPartSalesDeptQueryDto.setExpressPartCode(expressPartOrgCode);				
		}else{
			return null;
		}
		//根据快递点部查询对应所辐射的营业部集合
		List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDtoList = 
				expressPartSalesDeptService.queryExpressPartSalesDeptByExpressPartCode(expressPartSalesDeptQueryDto);
		if(CollectionUtils.isEmpty(expressPartSalesDeptResultDtoList)){
			return null;
		}else{
			//随机抽取一个营业部编码出来,如果该营业部编码为空,批量进行数据对比，直到找到一条不为空的记录为止
			randumInt = (int) (Math.random() * expressPartSalesDeptResultDtoList.size());
			LOGGER.info("随机获取的数字为:"+randumInt);
			receiveOrgCode = expressPartSalesDeptResultDtoList.get(randumInt).getSalesDeptCode();
			if(StringUtils.isEmpty(receiveOrgCode)){
				for(ExpressPartSalesDeptResultDto resultDto : expressPartSalesDeptResultDtoList){
					if(StringUtils.isNotEmpty(resultDto.getSalesDeptCode())){
						receiveOrgCode = resultDto.getSalesDeptCode();
						break;
					}
				}
			}
		}
		return receiveOrgCode;
	}
	
	
	/**
	 * 查询营业部所在城市
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-24 13:21:17
	 */
	public String queryRegionsBySaleDeptCode(String saleDeptCode){
		OrgAdministrativeInfoEntity org =  orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(saleDeptCode);
		return org == null ? null : org.getCityCode();
	}
	
	/**
	 * 
	 * 
	 * @Title: matchExpress
	 * @Description: 根据GIS返回地址ID，到综合匹配出对应时间接货小区， 1、根据小区、解析出的楼层匹配快递定人定区集合
	 *               2、1情况不存在，根据小区匹配机动区
	 * @param @param dispatchOrderEntity
	 * @param @param result
	 * @param @return 设定文件
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	private Map<String, Object> matchExpress(DispatchOrderEntity dispatchOrderEntity) {
		Map<String, Object> expMap = null;
		// 判断订单地址，是否能匹配出对应的每日报表统计数据
		StringBuffer pickupAddress = new StringBuffer();
		pickupAddress.append(dispatchOrderEntity.getPickupProvince());
		pickupAddress.append(dispatchOrderEntity.getPickupCity());
		pickupAddress.append(dispatchOrderEntity.getPickupCounty());
		pickupAddress.append(dispatchOrderEntity.getPickupElseAddress());
		if (StringUtils.isNotEmpty(pickupAddress.toString())) {
			dispatchOrderEntity.appendLog("接货地址信息:" + pickupAddress.toString());
			// 根据快递员，地址，当日时间，是否有退回记录
			OrderPdaReturnRecordEntity orderPdaReturnRecordEntity = new OrderPdaReturnRecordEntity();
			orderPdaReturnRecordEntity.setPickupAddress(pickupAddress.toString());
			// 根据地址查询每日报表，在当天是否存在数据
			OrderReportEntity queryParam = new OrderReportEntity();
			queryParam.setPickupAddress(pickupAddress.toString());
			OrderReportEntity orderReportEntity = orderReportService.selectOrderReportByAddress(queryParam);
			// 如果当日匹配记录存在数据
			if (null != orderReportEntity) {
				dispatchOrderEntity.appendLog("当日报表ID:" + orderReportEntity.getId());
				orderPdaReturnRecordEntity.setCreateCode(orderReportEntity.getExpressDriverCode());
				OrderPdaReturnRecordEntity recordEntity = orderPdaReturnRecordService.queryOrderPdaReturnByCommon(orderPdaReturnRecordEntity);
				// 如果存在退回记录  14.7.2 匹配当日分配记录失败，进入下一步收派小区匹配  
				// yb-2014.07.08 AUTO-115如果生成报表的时间  早于  退回记录时间，则需要进行自动匹配，否则，进行读取报表数据
				if (null != recordEntity&&recordEntity.getOperateTime().after(orderReportEntity.getCreateDate())) {
					dispatchOrderEntity.appendLog("退单记录ID:" + orderReportEntity.getId());
					expMap = expressDeliveryArea(expMap,dispatchOrderEntity,pickupAddress,orderPdaReturnRecordEntity);
				}else{
					// 取得对应快递员工号，姓名
					expMap = new HashMap<String, Object>();
					expMap.put("driverCode", orderReportEntity.getExpressDriverCode());
					expMap.put("driverName", orderReportEntity.getExpressDriverName());
					expMap.put("driverPhone", orderReportEntity.getExpressDriverPhone());
					expMap.put("vehicleNo", orderReportEntity.getVehicleNo());
					expMap.put("pickupRegionCode", orderReportEntity.getPickupRegionCode());
					expMap.put("pickupRegionName", orderReportEntity.getPickupRegionName());
					expMap.put("isCollect",FossConstants.YES);
				}
			} else {
				expMap=expressDeliveryArea(expMap,dispatchOrderEntity,pickupAddress,orderPdaReturnRecordEntity);
			}
		}
		return expMap;
	}
	

	/**
	 * @Description: 根据GIS返回地址ID，到综合匹配出对应时间接货小区， 1、根据小区、解析出的楼层匹配快递定人定区集合
	 *               2、1情况不存在，根据小区匹配机动区
	 * @param expMap
	 * @param dispatchOrderEntity
	 * @param pickupAddress
	 * @param orderPdaReturnRecordEntity
	 */
	private  Map<String, Object> expressDeliveryArea(Map<String, Object> expMap,DispatchOrderEntity dispatchOrderEntity,StringBuffer pickupAddress,OrderPdaReturnRecordEntity orderPdaReturnRecordEntity){
		///解析收派小区 14.7.2
		dispatchOrderEntity.appendLog("快递自动调度分配订单号:" + dispatchOrderEntity.getOrderNo()+"解析楼层号");
		Map<String, String> map = formatToMap(dispatchOrderEntity);
		Map<String, Object> result = null;
		try {
			// GIS地址
			String gisUrl = propertyFactory.getGisExpressUrl();
			// 根据GIS接口，返回坐标ID，获取收派小区编码
			result = GisInterfaceUtil.callGisInterface(gisUrl, map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			//zxy 20140709  内部优化 start 修改:日志完善
			dispatchOrderEntity.appendException(e.getMessage());
			dispatchOrderEntity.appendLog(e.getMessage());
			addExceptionLog(dispatchOrderEntity,"gisIDException",dispatchOrderEntity.getSbLog().toString());
			//zxy 20140709  内部优化 end 修改:日志完善
			return null;
		}
		String scopeCoordinatesId = null;
		// 获得GIS坐标id
		if (!result.isEmpty() && result.size() > 0) {
			scopeCoordinatesId = (String) result.get("scopeCoordinatesId");
			dispatchOrderEntity.appendLog("gisID:" + scopeCoordinatesId);
			// GIS匹配规则的ID，如果不为空,存在收派小区
			if (StringUtils.isNotEmpty(scopeCoordinatesId)) {
				ExpressDeliverySmallZoneEntity queryEntity = new ExpressDeliverySmallZoneEntity();
				queryEntity.setGisid(scopeCoordinatesId);
				queryEntity.setActive(FossConstants.YES);
				// 根据GIS返回ID，获取对应唯一的收派小区，如果不存在，则退出自动处理
				ExpressDeliverySmallZoneEntity entity = expressDeliverySmallZoneService.querySmallZoneByGisId(queryEntity);
				if (null == entity || StringUtils.isEmpty(entity.getRegionCode())) {
					// 添加异常服务日志
					String exceptionType = "EXPRESSSMALLZONECODE_IS_NULL";
					String reason = dispatchOrderEntity.getOrderNo()+"订单号GIS返回的ID:"+scopeCoordinatesId+"，找不到对应的收派小区编码";
					addExceptionLog(dispatchOrderEntity,exceptionType,reason);	
					expMap = null;
					return expMap;
				}
				dispatchOrderEntity.setPreprocessId(scopeCoordinatesId);//14.7.23 gcl AUTO-195 gisid
				// 取得快递收派小区编码
				String expressSmallZoneCode = entity.getRegionCode();
				String expressSmallZoneName = entity.getRegionName();
				dispatchOrderEntity.setSmallZoneCodeSuggested(expressSmallZoneCode);
				dispatchOrderEntity.setSmallZoneNameSuggested(expressSmallZoneName);
				// 收派小区存在，再根据地址解析出楼层，如果小区和楼层不为空，则取获得快递员排班信息
				Integer floor = NumberResolutionFloor.getFloorNum(pickupAddress.toString());
				dispatchOrderEntity.appendLog("收派小区:" + expressSmallZoneCode + "楼层号:" + floor);
				// 根据快递收派小区、解析的楼层号获得对应的快递员排班信息
				List<CourierScheduleEntity> scheduleList =null;
				// 更新预处理订单，采集数据
				updateExpOrderPreprocess(dispatchOrderEntity, result);
				//14.7.8 gcl AUTO-113 1:根据解析的楼层号获得对应的快递员排班信息 2:如果没有匹配成功 根据收派小区查询排班信息
				if(null != floor){
					scheduleList = courierScheduleService.queryCourierScheduleByCondition(expressSmallZoneName, floor);//14.9.10 gcl 修改为按小区名称查询
					if (CollectionUtils.isNotEmpty(scheduleList)) {
						expMap = getexpMapByFloor(expMap, scheduleList, dispatchOrderEntity, orderPdaReturnRecordEntity);
					}else{
						dispatchOrderEntity.appendLog("楼层快递员排班为空");
					}
				}
				if(null == expMap){
					scheduleList = courierScheduleService.queryCourierScheduleByCondition(expressSmallZoneName, null);
					if(CollectionUtils.isNotEmpty(scheduleList)){
						expMap = getexpMapBySmallZone(expMap, scheduleList, dispatchOrderEntity, orderPdaReturnRecordEntity);
					}else{
						dispatchOrderEntity.appendLog("小区快递员排班为空");
					}
				}
			}else {
				String exceptionType = "MATCH_EXPRESS_GIS_ID_NULL";
				String reason = dispatchOrderEntity.getOrderNo()+pickupAddress.toString()+"GIS无法匹配出收派小区ID";	
				addExceptionLog(dispatchOrderEntity,exceptionType,reason);
			}
		}else{
			String exceptionType = "MATCH_EXPRESS_GIS_ID_NULL";
			String reason = dispatchOrderEntity.getOrderNo()+pickupAddress.toString()+"GIS无法匹配出收派小区ID";	
			addExceptionLog(dispatchOrderEntity,exceptionType,reason);
		}
		return expMap;
	}
	
	/**
	 * @Description:14.7.8 根据解析出的收派小区和楼层，根据快递区域日常排班表内的信息匹配对应定区快递员
	 * @param expMap
	 * @param scheduleList
	 * @param dispatchOrderEntity
	 * @param orderPdaReturnRecordEntity
	 * @return
	 */
	private Map<String, Object> getexpMapByFloor(Map<String, Object> expMap,List<CourierScheduleEntity> scheduleList,DispatchOrderEntity dispatchOrderEntity,OrderPdaReturnRecordEntity orderPdaReturnRecordEntity){
		if (CollectionUtils.isEmpty(scheduleList)) {
			String reason = dispatchOrderEntity.getOrderNo()+"快递收派小区、解析的楼层号获得对应的快递员排班信息为空";	
			addExceptionLog(dispatchOrderEntity,"MATCH_EXPRESS_SMALLZONE_FLOOR",reason);
			return expMap;
		}
		for(CourierScheduleEntity courier : scheduleList){
			// 判断是否是定区快递员
			if(DispatchOrderStatusConstants.COURIER_NATURE_FIXED.equals(courier.getCourierNature())){
				// 获得快递员工号
				String empCode = courier.getCourierCode();
				// 根据快递员工号，判断是否暂停PDA
				ExpressWorkerStatusEntity workerEntity = expressWorkerStatusService.queryOneByEmpcode(empCode);
				// 可用人员不为空，且为开启状态
				if (null != workerEntity && ExpressWorkerStatusConstants.OPEN_STATUS.equals(workerEntity.getWorkStatus())) {
					// 设置退回记录 快递员
					orderPdaReturnRecordEntity.setCreateCode(empCode);
					// 是否存在退回记录
					Long count = isExistsPdareturn(orderPdaReturnRecordEntity);
					// 不存在退回记录
					if(null == count || count == 0){
						expMap = new HashMap<String, Object>();
						expMap.put("driverCode", courier.getCourierCode());
						expMap.put("driverName", courier.getCourierName());
						expMap.put("driverPhone", courier.getCourierPhone());
						expMap.put("vehicleNo", courier.getVehicleNo());
						expMap.put("pickupRegionCode", dispatchOrderEntity.getSmallZoneCodeSuggested());
						expMap.put("pickupRegionName", dispatchOrderEntity.getSmallZoneNameSuggested());
						break;
					}else{
						dispatchOrderEntity.appendLog(empCode+"有退回");
					}
				}else{
					dispatchOrderEntity.appendLog(empCode+"暂停");
				}
			}
		}
		return expMap;
	}
	/**
	 * AUTO-115  是否存在退回记录 
	 * @param orderPdaReturnRecordEntity
	 * @return
	 */
	private Long isExistsPdareturn(OrderPdaReturnRecordEntity orderPdaReturnRecordEntity){
		Long count=0L;
		OrderPdaReturnRecordEntity recordEntity = orderPdaReturnRecordService.queryOrderPdaReturnByCommon(orderPdaReturnRecordEntity);
		if (null != recordEntity) {
			OrderReportEntity orderReportEntity = new OrderReportEntity();
			orderReportEntity.setExpressDriverCode(recordEntity.getCreateCode());
			orderReportEntity.setPickupAddress(recordEntity.getPickupAddress());
			orderReportEntity=orderReportService.selectOrderReportByAddressDriverCode(orderReportEntity);
			// yb-2014.07.08 AUTO-115如果退回记录时间  晚于  生成报表的时间，则存在退回记录 
			if(null == orderReportEntity){
				count=1L;
			}else if(recordEntity.getOperateTime().after(orderReportEntity.getCreateDate())){
				count=1L;
			}
		}
		return count;
	}
	
	/**
	 * 
	 * @Title: updateExpOrderPreprocess
	 * @Description: 更新预处理意见
	 * @param @param dispatchOrderEntity
	 * @param @param result 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void updateExpOrderPreprocess(DispatchOrderEntity dispatchOrderEntity, Map<String, Object> result) {
		if (result != null) {
			dispatchOrderEntity.setOrderStatus("");
			// 获得GIS坐标id
			String scopeCoordinatesId = (String) result.get("scopeCoordinatesId");
			// 判断 scopeCoordinatesId为空
			if (scopeCoordinatesId == null) {
				// 不处理
				addExceptionLog(dispatchOrderEntity,"scopeCoordinatesId_IS_NULL","调用GIS接口GIS坐标ID为空");
				return;
			}
			// 是否已入库标记
			String isCollect = (String) result.get("isCollect");
			if (StringUtils.isNotEmpty(isCollect) && FossConstants.YES.equals(isCollect)) {
				dispatchOrderEntity.setIsCollect(isCollect);
			}
			// 更新数据库中对应订单的采集情况
			dispatchOrderEntityDao.updateByPrimaryKeySelective(dispatchOrderEntity);
			UserEntity user = new UserEntity();
			// 设置编码
			user.setEmpCode(dispatchOrderEntity.getCreateUserCode());
			// 设置雇员名称
			user.setEmpName(dispatchOrderEntity.getCreateUserName());
			// 设置用户名称
			user.setUserName(dispatchOrderEntity.getCreateUserName());
			
			EmployeeEntity employeeEntity = new EmployeeEntity();
			// 设置雇员名称
			employeeEntity.setEmpCode(dispatchOrderEntity.getCreateUserCode());
			// 设置用户名称
			employeeEntity.setEmpName(dispatchOrderEntity.getCreateUserName());
			user.setEmployee(employeeEntity);
			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			// 设置编码
			dept.setCode(dispatchOrderEntity.getSalesDepartmentCode());
			// 设置名称
			dept.setName(dispatchOrderEntity.getSalesDepartmentName());
			//订单自动分配成功数
			businessMonitorService.counter(BusinessMonitorIndicator.ORDER_AUTO_ASSIGN_SUCCESS_COUNT, new CurrentInfo(user, dept));
		} else {
			// GIS返回异常
			String exceptionType = "GIS_ID_IS_NULL";
			String reason = "GIS返回的ID为空";
			addExceptionLog(dispatchOrderEntity,exceptionType,reason);
		}
	}
	
	/**
	 * @Description:14.7.8 根据收派小区查询排班信息
	 *              先在可用定区快递员中查当日订单量最少的，如果没有匹配成功，查可用机动快递员中当日订单量最少的
	 * @param expMap
	 * @param scheduleList
	 * @param dispatchOrderEntity
	 * @param orderPdaReturnRecordEntity
	 * @return
	 */
	private Map<String, Object> getexpMapBySmallZone(Map<String, Object> expMap,List<CourierScheduleEntity> scheduleList,DispatchOrderEntity dispatchOrderEntity,OrderPdaReturnRecordEntity orderPdaReturnRecordEntity){
		// 判断快递集合是否为空
		if (CollectionUtils.isNotEmpty(scheduleList)) {
			List<CourierScheduleEntity> autoCourierList = new ArrayList<CourierScheduleEntity>();
			List<CourierScheduleEntity> zoneCourierList = new ArrayList<CourierScheduleEntity>();
			// 循环对应的快递员
			for (CourierScheduleEntity courier : scheduleList) {
				// 判断是否是定区快递员
				if(DispatchOrderStatusConstants.COURIER_NATURE_FIXED.equals(courier.getCourierNature())){
					zoneCourierList.add(courier);
				}else if(DispatchOrderStatusConstants.COURIER_NATURE_MOTORIZED.equals(courier.getCourierNature())){
					autoCourierList.add(courier);
				}	
			}
			//先在可用定区快递员中查当日订单量最少的
			if(null == expMap && CollectionUtils.isNotEmpty(zoneCourierList)){
				dispatchOrderEntity.appendLog("定区" + zoneCourierList.size() + "人");
				expMap = minDriverCode(expMap, dispatchOrderEntity, orderPdaReturnRecordEntity, zoneCourierList);
			}else{
				dispatchOrderEntity.appendLog("无定区快递员");
			}
			//没有匹配成功，查可用机动快递员中当日订单量最少的
			if(null == expMap){
				if(CollectionUtils.isNotEmpty(autoCourierList)){
					dispatchOrderEntity.appendLog("机动" + autoCourierList.size() + "人");
					expMap = minDriverCode(expMap, dispatchOrderEntity, orderPdaReturnRecordEntity, autoCourierList);
                }else{
					dispatchOrderEntity.appendLog("无机动快递员");
				}
			}else{
				dispatchOrderEntity.appendLog("定区车牌:" + expMap.get("vehicleNo") + ",司机:" + expMap.get("driverCode"));
			}
			// 判断机动快递员是否为空  14.7.2定区快递员中未有匹配到
			if (null != expMap){
				dispatchOrderEntity.appendLog("机动车牌:" + expMap.get("vehicleNo") + ",司机:" + expMap.get("driverCode"));
			}
		}else{
			String reason = dispatchOrderEntity.getOrderNo()+"快递收派小区获得对应的快递员排班信息为空";	
			addExceptionLog(dispatchOrderEntity,"MATCH_EXPRESS_SMALLZONE",reason);
		}
		return expMap;
	}
	
	/**
	 * 14.7.8 快递员中查当日订单量最少的
	 * @param expMap
	 * @param dispatchOrderEntity
	 * @param orderPdaReturnRecordEntity
	 * @param autoCourierList
	 * @return
	*/
	private Map<String, Object> minDriverCode(Map<String, Object> expMap,DispatchOrderEntity dispatchOrderEntity,OrderPdaReturnRecordEntity orderPdaReturnRecordEntity,List<CourierScheduleEntity> autoCourierList){
		if(CollectionUtils.isEmpty(autoCourierList)){
			return null;
		}
		List<String> autoDriverCodes = new ArrayList<String>();
		for(CourierScheduleEntity courier : autoCourierList){
			// 是否存在退回记录
			//14.7.2 未有存在退回记录
			String empCode = courier.getCourierCode();
			// 设置退回记录 快递员
			orderPdaReturnRecordEntity.setCreateCode(empCode);
			Long count = isExistsPdareturn(orderPdaReturnRecordEntity);
			// 不存在退回记录
			if(count == null || count == 0){
				//14.7.30 根据快递员工号，判断是否暂停PDA
				ExpressWorkerStatusEntity workerEntity = expressWorkerStatusService.queryOneByEmpcode(empCode);
				// 可用人员不为空，且为开启状态  
				if (null != workerEntity && ExpressWorkerStatusConstants.OPEN_STATUS.equals(workerEntity.getWorkStatus())) {
					autoDriverCodes.add(empCode);
				}else{
					dispatchOrderEntity.appendLog(empCode + "暂停");
				}
			}else{
				dispatchOrderEntity.appendLog(empCode + "有退回");
			}
		}
		//14.7.14 gcl AUTO-157  无可用快递员直接返回null
		if(CollectionUtils.isEmpty(autoDriverCodes)){
			return null;
		}
		// 每日报表中取当日收取最少的信息 并且可用的
		String minDriverCode = regionCourierReportService.queryMinCourierByCommon(autoDriverCodes);
	    // 判断是否存在
		if(StringUtils.isEmpty(minDriverCode)){
			return null;
		}
		// 循环出快递员
		for(CourierScheduleEntity courier : autoCourierList){
			//如果找到对应最少快递员编码
			if(StringUtils.equals(minDriverCode, courier.getCourierCode())){
				expMap = new HashMap<String, Object>();
				expMap.put("driverCode", courier.getCourierCode());
				expMap.put("driverName", courier.getCourierName());
				expMap.put("driverPhone", courier.getCourierPhone());
				expMap.put("vehicleNo", courier.getVehicleNo());
				expMap.put("pickupRegionCode", dispatchOrderEntity.getSmallZoneCodeSuggested());
				expMap.put("pickupRegionName", dispatchOrderEntity.getSmallZoneNameSuggested());
				break;
			}
		}
		return expMap;
	}

	/**
	 * 
	 * @Title: formatToMap
	 * @Description: 将订单实体转为Map
	 * @param @param dispatchOrderEntity
	 * @param @return 设定文件
	 * @return Map<String,String> 返回类型
	 * @throws
	 */
	private static Map<String, String> formatToMap(DispatchOrderEntity dispatchOrderEntity) {
		Map<String, String> map = new HashMap<String, String>();
		// 订单号
		map.put("appNum", dispatchOrderEntity.getOrderNo());
		// 省份
		map.put("province", dispatchOrderEntity.getPickupProvince());
		// 城市
		map.put("city", dispatchOrderEntity.getPickupCity());
		// 区县
		map.put("county", dispatchOrderEntity.getPickupCounty());
		// 自动调度类型
		map.put("transportway", "motor_self");
		// 其他详细地址
		map.put("otherAddress", dispatchOrderEntity.getPickupElseAddress());
		// 手机
		map.put("phone", dispatchOrderEntity.getMobile());
		// 电话
		map.put("tel", dispatchOrderEntity.getTel());
		// 快递订单收派区域
		map.put("matchtype", DispatchOrderStatusConstants.REGION_TYPE_KD);
		return map;
	}
	

	/**
	 * 
	 * @Title: getOrderHandleDto
	 * @Description: 重新组装成待受理的订单
	 * @param @param dispatchOrderEntity
	 * @param @param expMap
	 * @param @return 设定文件
	 * @return OrderHandleDto 返回类型
	 * @throws
	 */
	private OrderHandleDto getOrderHandleDto(DispatchOrderEntity dispatchOrderEntity, Map<String, Object> expMap) {
		if (null == expMap || expMap.size() <= 0) {
			return null;
		}
		OrderHandleDto orderHandleDto = new OrderHandleDto();
		orderHandleDto.setId(dispatchOrderEntity.getId());
		orderHandleDto.setOrderNo(dispatchOrderEntity.getOrderNo());
		orderHandleDto.setOrderType(dispatchOrderEntity.getOrderType());
		orderHandleDto.setOriginOrderStatus(dispatchOrderEntity.getOrderStatus());
		orderHandleDto.setIsCustomer(dispatchOrderEntity.getIsCustomer());
		orderHandleDto.setIsPda(dispatchOrderEntity.getIsPda());
		//设置是否发送短信为否
		orderHandleDto.setIsSms(FossConstants.NO);

		// 匹配司机，自动匹配规则
		String driverCode = (String) expMap.get("driverCode");
		String driverName = (String) expMap.get("driverName");
		String vehicleNo = (String) expMap.get("vehicleNo");
		String driverMobile = (String) expMap.get("driverPhone");
		String pickupRegionCode = (String) expMap.get("pickupRegionCode");
		String pickupRegionName = (String) expMap.get("pickupRegionName");
		//14.8.15 gcl AUTO-230
		String isCollect = (String) expMap.get("isCollect");
		if(StringUtils.isNotEmpty(isCollect) && FossConstants.YES.equals(isCollect)){
			orderHandleDto.setIsCollect(isCollect);
		}
		orderHandleDto.setDriverCode(driverCode);
		orderHandleDto.setDriverName(driverName);
		orderHandleDto.setVehicleNo(vehicleNo);
		orderHandleDto.setDriverMobile(driverMobile);
		orderHandleDto.setPickupRegionCode(pickupRegionCode);
		orderHandleDto.setPickupRegionName(pickupRegionName);
		orderHandleDto.setSmallZoneCodeSuggested(pickupRegionCode);////14.7.22 gcl AUTO-194当日订单时 更新预处理小区为当日订单的小区
		orderHandleDto.setSmallZoneNameSuggested(pickupRegionName);
		orderHandleDto.setSmallZoneCodeActual(pickupRegionCode);//14.8.11 gcl 实际接货小区为预处理小区
		//14.8.21 gcl 自动调度时 同时设置预处理车和司机信息 方便下次退回后找预处理
		orderHandleDto.setVehicleNoSuggested(vehicleNo);
		orderHandleDto.setDriverCodeSuggested(driverCode);
		orderHandleDto.setDriverNameSuggested(driverName);
		
		orderHandleDto.setWeight(dispatchOrderEntity.getWeight());
		orderHandleDto.setVolume(dispatchOrderEntity.getVolume());
		orderHandleDto.setCustomerMobile(dispatchOrderEntity.getMobile());
		orderHandleDto.setTel(dispatchOrderEntity.getTel());
		orderHandleDto.setCustomerName(dispatchOrderEntity.getCustomerName());
		StringBuffer pickupAddress = new StringBuffer();
		pickupAddress.append(dispatchOrderEntity.getPickupProvince());
		pickupAddress.append(dispatchOrderEntity.getPickupCity());
		pickupAddress.append(dispatchOrderEntity.getPickupCounty());
		pickupAddress.append(dispatchOrderEntity.getPickupElseAddress());
		orderHandleDto.setPickupCity(dispatchOrderEntity.getPickupCity());
		orderHandleDto.setPickupAddress(pickupAddress.toString());
		orderHandleDto.setSalesDepartmentCode(dispatchOrderEntity.getSalesDepartmentCode());
		orderHandleDto.setSalesDepartmentName(dispatchOrderEntity.getSalesDepartmentName());
		orderHandleDto.setOrderNotes(dispatchOrderEntity.getOrderNotes());
		//设置收货部门信息
		orderHandleDto.setReceiveOrgCode(dispatchOrderEntity.getReceiveOrgCode());
		orderHandleDto.setReceiveOrgName(dispatchOrderEntity.getReceiveOrgName());
		// 接货时间
		StringBuffer pickupTime = new StringBuffer();
		Date earliestPickupTime = dispatchOrderEntity.getEarliestPickupTime();
		Date latestPickupTime = dispatchOrderEntity.getLatestPickupTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(null != earliestPickupTime && null != latestPickupTime){
			String start = formatter.format(earliestPickupTime);
			String end = formatter.format(latestPickupTime);
			pickupTime.append(start);
			pickupTime.append("-");
			pickupTime.append(end);
			orderHandleDto.setPickupTime(pickupTime.toString());
		}
		StringBuffer owsPickupTime = new StringBuffer();
		if(null != earliestPickupTime) {
			owsPickupTime.append(formatter.format(earliestPickupTime)+"-");
		} 
		if(null != latestPickupTime) {
			owsPickupTime.append(formatter.format(latestPickupTime));
		}
		if(owsPickupTime != null && owsPickupTime.length()>0) {
			orderHandleDto.setOwsPickupTime(owsPickupTime.toString());
		}
		orderHandleDto.setGoodsQty(dispatchOrderEntity.getGoodsQty());
		orderHandleDto.setGoodsPackage(dispatchOrderEntity.getGoodsPackage());
		orderHandleDto.setProductCode(dispatchOrderEntity.getProductCode());
		orderHandleDto.setOperateOrgCode(dispatchOrderEntity.getFleetCode());
		// 操作时间
		orderHandleDto.setOperateTime(new Date());
		// 操作人
		orderHandleDto.setOperator("system");
		// 操作人编码
		orderHandleDto.setOperatorCode("000000");
		// 操作部门
		orderHandleDto.setOperateOrgName("电子运单自动调度");
		return orderHandleDto;
	}
	
	/**
	 * @Title: addExceptionLog 
	 * @Description: 自动处理异常日志新增
	 * @param @param dispatchOrderEntity
	 * @param @param excptionType
	 * @param @param reason    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addExceptionLog(DispatchOrderEntity dispatchOrderEntity, String excptionType,String reason){
		// 如果待分配的快递员为空，则新增日常日志，同时转为人工处理
		OrderAutoExceptionLogEntity orderAutoExceptionLogEntity = new OrderAutoExceptionLogEntity();
		orderAutoExceptionLogEntity.setId(UUIDUtils.getUUID());
		orderAutoExceptionLogEntity.setDispatchOrderId(dispatchOrderEntity.getId());
		orderAutoExceptionLogEntity.setOrderNo(dispatchOrderEntity.getOrderNo());
		orderAutoExceptionLogEntity.setExceptionType(excptionType);
		orderAutoExceptionLogEntity.setExceptionReason(reason);
		orderAutoExceptionLogEntity.setCreateTime(new Date());
		orderAutoExceptionLogService.insertAutoOrderExceptionLog(orderAutoExceptionLogEntity);		
	}
	
	/**
	 * 添加Ewaybill_Order记录
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-18 10:52:41
	 * @param dispatchOrderEntity
	 * @param preHandEWaybillOrderEntity
	 * @param isCreate
	 */
	private void addEwaybillOrderEntity(DispatchOrderEntity dispatchOrderEntity, PreHandEWaybillOrderEntity preHandEWaybillOrderEntity, EWaybillOrderEntity eWaybillOrderEntity) {
		LOGGER.info("订单号:"+preHandEWaybillOrderEntity.getOrderNo()+"添加EWaybillOrder entity 开始");
		//将信息复制在EWAybillOrderEntity中
		//判定是否
		boolean isCreate = true;
		if(eWaybillOrderEntity == null){
			eWaybillOrderEntity = new EWaybillOrderEntity();
			isCreate = false;
		}
		// 通过查询订单接口查询订单信息
		CrmOrderDetailDto crmOrderDetailDto = null;
		//try {
			crmOrderDetailDto = crmOrderService.importOrder(preHandEWaybillOrderEntity.getOrderNo());
		/*} catch (Exception e) {
			LOGGER.info("查询CRM订单信息出现异常:");
			e.printStackTrace();
			String error = ExceptionUtils.getFullStackTrace(e);
			if (StringUtils.isNotEmpty(error) && error.length() > 490) {
				preHandEWaybillOrderEntity.setExceptionMsg("异常：" + error.substring(0, 490));
			} else {
				preHandEWaybillOrderEntity.setExceptionMsg("异常：" + error);
			}
			preHandEWaybillOrderEntity.setStatus(FossConstants.NO);
			preHandEWaybillOrderDao.updatePreEWaybillOrderByIdSelective(preHandEWaybillOrderEntity);
			//如果eWaybillOrderEntity数据存在，可以进行一次数据的重置,这里不需要考虑异常更新出现异常的情况
			try {
				if(StringUtils.isNotEmpty(eWaybillOrderEntity.getID())){
					EWaybillOrderEntity eWaybillOrderEntityTemp = new EWaybillOrderEntity();
					eWaybillOrderEntityTemp.setID(eWaybillOrderEntity.getID());
					eWaybillOrderEntityTemp.setJobID(WaybillConstants.UNKNOWN);
					eWaybillOrderEntityTemp.setFailReason(FossConstants.NO);
					eWaybillOrderEntityTemp.setModifyTime(new Date());	
					ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybillOrderEntityTemp);
					return;
				}
			} catch (Exception e1) {
				// 查询不到订单数据进行数据一次重置,保证数据的健壮性
				LOGGER.info("查询不到订单数据进行数据一次重置,用于保证数据的健壮性,EWaybillOrderEntity对应ID为:"+eWaybillOrderEntity.getID());
				e1.printStackTrace();
			}
			return;
		}*/
		//判定从CRM查询过来的订单数据是否为空
		if(crmOrderDetailDto == null){
			/*LOGGER.info("crmOrderDetail dto is null");
			preHandEWaybillOrderEntity.setExceptionMsg("crmOrderDetail dto is null");
			preHandEWaybillOrderEntity.setStatus(FossConstants.NO);
			preHandEWaybillOrderDao.updatePreEWaybillOrderByIdSelective(preHandEWaybillOrderEntity);
			return;*/
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		LOGGER.info(ReflectionToStringBuilder.toString(crmOrderDetailDto));
		// 设置为未进行后台线程的跑记录
		// 如果是子母件走以前的业务逻辑
		String jobId = null;
		LOGGER.info("是否子母件" + dispatchOrderEntity.getOriginalNumber() + "=="+ dispatchOrderEntity.getIsPicPackage());
		if (FossConstants.YES.equals(dispatchOrderEntity.getIsPicPackage())) {
			// 子母件
			LOGGER.info("子母件原始订单号" + dispatchOrderEntity.getOriginalNumber());
			jobId = UUIDUtils.getUUID();
		} else {
			// 普通电子运单
			jobId = WaybillConstants.UNKNOWN;
		}
		LOGGER.info("电子运单jobID" + jobId);
		eWaybillOrderEntity.setJobID(jobId);
		eWaybillOrderEntity.setOrderNO(preHandEWaybillOrderEntity.getOrderNo());
		eWaybillOrderEntity.setReceiveCustomerID(crmOrderDetailDto.getReceiverCustId());
		eWaybillOrderEntity.setReceiveCustomerCode(crmOrderDetailDto.getReceiverCustNumber());
		eWaybillOrderEntity.setReceiveCustomerName(crmOrderDetailDto.getReceiverCustName());
		eWaybillOrderEntity.setReceiveCustomerContact(crmOrderDetailDto.getReceiverCustName());
		eWaybillOrderEntity.setReceiveCustomerMobilephone(crmOrderDetailDto.getReceiverCustMobile());
		eWaybillOrderEntity.setReceiveCustomerPhone(crmOrderDetailDto.getReceiverCustPhone());
		eWaybillOrderEntity.setDeliveryCustomerContactId(crmOrderDetailDto.getContactManId());
		eWaybillOrderEntity.setReceiveCustomerNationCode(null);
		eWaybillOrderEntity.setReceiveCustomerProvCode(crmOrderDetailDto.getReceiverCustProvinceCode());
		eWaybillOrderEntity.setReceiveCustomerCityCode(crmOrderDetailDto.getReceiverCustCityCode());
		eWaybillOrderEntity.setReceiveCustomerDistCode(crmOrderDetailDto.getReceiverCustAreaCode());
		eWaybillOrderEntity.setReceiveCustomerAddress(crmOrderDetailDto.getReceiverCustAddress());
		eWaybillOrderEntity.setDeliveryCustomerContact(crmOrderDetailDto.getContactName());
		eWaybillOrderEntity.setChannelCustID(crmOrderDetailDto.getChannelCustId());
		// 签收单
		String crmReturnBillType = crmOrderDetailDto.getReturnBillType();
		for (CrmReturnBillTypeEnum crm : CrmReturnBillTypeEnum.values()) {
			// 订单返单方式
			if (crm.getCrmCode().equals(crmReturnBillType)) {
				eWaybillOrderEntity.setReturnBillType(crm.getFossCode());
			}
		}
		if (StringUtils.isEmpty(crmReturnBillType)) {
			eWaybillOrderEntity.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);
		}
		
		//设置为无异常
		eWaybillOrderEntity.setFailReason(FossConstants.NO);
		if(isCreate){
			eWaybillOrderEntity.setModifyTime(new Date());
			ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybillOrderEntity);
			LOGGER.info("ewaybillOrder 更新成功");
		}else{
			eWaybillOrderEntity.setCreateTime(new Date());
			eWaybillOrderEntity.setModifyTime(new Date());
			ewaybillOrderEntityDao.insertEWaybillOrder(eWaybillOrderEntity);
			LOGGER.info("ewaybillOrder 插入成功"+eWaybillOrderEntity.getOrderNO());
		}
		LOGGER.info("订单号:"+preHandEWaybillOrderEntity.getOrderNo()+"添加EWaybillOrder entity 结束");
	}
	
	/**
	 * 
	 * @Title: addExceptionLog 
	 * @Description: 自动处理异常日志新增
	 * @param @param dispatchOrderEntity
	 * @param @param excptionType
	 * @param @param reason    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void addExceptionLog(OrderHandleDto orderHandleDto, String excptionType, String reason){
		// 如果待分配的快递员为空，则新增日常日志，同时转为人工处理
		OrderAutoExceptionLogEntity orderAutoExceptionLogEntity = new OrderAutoExceptionLogEntity();
		orderAutoExceptionLogEntity.setId(UUIDUtils.getUUID());
		orderAutoExceptionLogEntity.setDispatchOrderId(orderHandleDto.getId());
		orderAutoExceptionLogEntity.setOrderNo(orderHandleDto.getOrderNo());
		orderAutoExceptionLogEntity.setExceptionType(excptionType);
		orderAutoExceptionLogEntity.setExceptionReason(reason);
		orderAutoExceptionLogEntity.setCreateTime(new Date());
		orderAutoExceptionLogService.insertAutoOrderExceptionLog(orderAutoExceptionLogEntity);		
	}
	
	/**
	 * 
	 * @Title: addDispatchOrderActionHistory 
	 * @Description: 新增操作记录
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addDispatchOrderActionHistory(OrderHandleDto orderHandleDto) {
		DispatchOrderActionHistoryEntity dispatchOrderActionHistoryEntity = new DispatchOrderActionHistoryEntity();
		// 主键id
		dispatchOrderActionHistoryEntity.setId(UUIDUtils.getUUID());
		// 订单操作备注
		dispatchOrderActionHistoryEntity.setNotes(orderHandleDto.getOperateNotes());
		// 订单操作人
		dispatchOrderActionHistoryEntity.setOperator(orderHandleDto.getOperator());
		// 订单操作人code
		dispatchOrderActionHistoryEntity.setOperatorCode(orderHandleDto.getOperatorCode());
		// 调度订单id
		dispatchOrderActionHistoryEntity.settSrvDispatchOrderId(orderHandleDto.getId());
		// 调度订单状态
		dispatchOrderActionHistoryEntity.setOrderStatus(orderHandleDto.getOrderStatus());
		// 操作时间
		dispatchOrderActionHistoryEntity.setOperateTime(new Date());
		// 插入订单操作历史
		dispatchOrderActionHistoryEntityDao.addDispatchOrderActionHistory(dispatchOrderActionHistoryEntity);
	}
	
	/**
	 * 
	 * @Title: addDispatchVehicleRecord 
	 * @Description: 增加快递订单派车记录
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addDispatchVehicleRecord(OrderHandleDto orderHandleDto) {
		DispatchVehicleRecordEntity vehicleRecordEntity = new DispatchVehicleRecordEntity();
		// 调度订单id
		vehicleRecordEntity.settSrvDispatchOrderId(orderHandleDto.getId());
		// 车牌号
		vehicleRecordEntity.setVehicleNo(orderHandleDto.getVehicleNo());
		// id
		vehicleRecordEntity.setId(UUIDUtils.getUUID());
		// 司机姓名
		vehicleRecordEntity.setDriverName(orderHandleDto.getDriverName());
		// 司机code
		vehicleRecordEntity.setDriverCode(orderHandleDto.getDriverCode());
		// 派车时间
		vehicleRecordEntity.setDeliverTime(new Date());//14.7.16 gcl AUTO-163
		// 是否PDA
		vehicleRecordEntity.setPdaStatus(FossConstants.YES.equals(orderHandleDto.getIsPda()) ? DispatchOrderStatusConstants.ISPDA_NORMAL : DispatchOrderStatusConstants.ISPDA_NO);
		// 定人定区Code
		vehicleRecordEntity.setPickupRegionCode(orderHandleDto.getPickupRegionCode());
		// 定人定区名称
		vehicleRecordEntity.setPickupRegionName(orderHandleDto.getPickupRegionName());
		// 订单状态
		vehicleRecordEntity.setOrderStatus(orderHandleDto.getOrderStatus());
		if(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE.equals(orderHandleDto.getOrderStatus())){
			// 受理状态
			vehicleRecordEntity.setProcessStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE.equals(orderHandleDto.getOriginOrderStatus()) ? DispatchOrderStatusConstants.STATUS_PROCESS_ACCEPT : DispatchOrderStatusConstants.STATUS_PROCESS_AGAIN);
		} else {
			// 退回状态
			vehicleRecordEntity.setProcessStatus(DispatchOrderStatusConstants.STATUS_PROCESS_RETURN);
		}
		dispatchVehicleRecordEntityDao.addDispatchVehicleRecord(vehicleRecordEntity);
	}
	
	/**
	 * 
	 * @Title: updateExternalSystem 
	 * @Description: 更新其它系统接口
	 * @param @param orderHandleDto
	 * @param @param reason    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void updateExternalSystem(OrderHandleDto orderHandleDto, String reason) {
		LOGGER.info("updateExternalSystem start" + orderHandleDto + "-" + orderHandleDto.getOrderStatus() + "-" + reason);
		if(StringUtils.isBlank(orderHandleDto.getOrderNo()) || StringUtils.isBlank(orderHandleDto.getOrderType()) || StringUtils.isBlank(orderHandleDto.getOrderStatus()))
		{
			throw new DispatchException("订单信息缺失，无法继续操作！");
		}
		if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(orderHandleDto.getOrderType())) {
			// 更新CRM订单状态
			updateCrmOrder(orderHandleDto);
		} else {
			// 调用中转接口修改转货订单的接口
			orderVehicleService.updateOrderVehicleApplyStatusByOrderNo(orderHandleDto.getOrderNo(), orderHandleDto.getOrderStatus(), reason);
		}
	}
	
	/**
	 * 
	 * @Title: updateCrmOrder 
	 * @Description: 更新CRM订单
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void updateCrmOrder(OrderHandleDto orderHandleDto) {
		LOGGER.info("更新CRM订单开始：" + orderHandleDto + "-" + orderHandleDto.getOrderStatus());
		// 获取CRM的映射订单状态
		String crmOrderStatus = DispatchOrderStatusConstants.CRM_ORDER_STATUS_MAPPING.get(orderHandleDto.getOrderStatus());
		// 为空则表明不需要将状态反馈给CRM
		if (StringUtils.isEmpty(crmOrderStatus)) {
			return;
		}
		// 更新CRM订单
		CrmModifyInfoDto crmModifyInfoDto = new CrmModifyInfoDto();
		// 订单号
		crmModifyInfoDto.setOrderNumber(orderHandleDto.getOrderNo());
		// 司机姓名
		crmModifyInfoDto.setDriverName(orderHandleDto.getDriverName());
		// 司机手机号
		crmModifyInfoDto.setDriverMobile(orderHandleDto.getDriverMobile());
		// 操作人编码
		crmModifyInfoDto.setOprateUserNum(orderHandleDto.getOperatorCode());
		// 获取操作部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orderHandleDto.getOperateOrgCode());
		// 操作部门code
		crmModifyInfoDto.setOprateDeptCode(org != null ? org.getUnifiedCode() : "");
		// 订单状态
		crmModifyInfoDto.setGoodsStatus(crmOrderStatus);
		// 操作备注
		crmModifyInfoDto.setBackInfo(orderHandleDto.getOperateNotes());
		// 设置收入部门
		DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
		dto.setOrderNo(orderHandleDto.getOrderNo());
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrdersByOrderNo(dto);
		if (dispatchOrderEntity != null ) {//14.10.16 gcl 去掉快递判断 : && StringUtils.equals(dispatchOrderEntity.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE)
			// 收入部门标杆编码
			OrgAdministrativeInfoEntity receiveOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(dispatchOrderEntity.getReceiveOrgCode());
			crmModifyInfoDto.setEarningDeptStandardCode(receiveOrg != null ? receiveOrg.getUnifiedCode() : "");
			crmModifyInfoDto.setEarningDeptStandardName(receiveOrg != null ? receiveOrg.getName() : "");
			if (StringUtils.isEmpty(orderHandleDto.getDriverMobile()) && StringUtils.isNotEmpty(orderHandleDto.getDriverCode())) {
				// 如果是小件派送单,并且司机工号或者司机车牌号不为空
				ExpressVehicleDto expressVeh = new ExpressVehicleDto();
				// 司机工号
				expressVeh.setEmpCode(orderHandleDto.getDriverCode());
				// 车牌号
				expressVeh.setVehicleNo(orderHandleDto.getVehicleNo());
				List<ExpressVehicleDto> expressVehicleDtos = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh, NumberConstants.NUMBER_10, NumberConstants.ZERO);
				if (CollectionUtils.isNotEmpty(expressVehicleDtos)) {
					// 司机电话
					crmModifyInfoDto.setDriverMobile(expressVehicleDtos.get(0).getMobilePhone());
				}
			}
		}
		// 调用CRM订单修改接口
		crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);		
		LOGGER.info("更新CRM订单成功");
	}

	@Override
	public void process(String jobId) {
		pushThreadsPool(jobId);
	}

	@Override
	public void outOfOrderPool(Object obj) {
		//进行数据转换
		String jobId = (String) obj;
		LOGGER.info("本次执行任务ID为:" + jobId);
		
		
		//预处理数据的查询
		List<PreHandEWaybillOrderEntity> preHandOrderList = this.queryGeneratePreEWaybillOrderByJobID(jobId);
		if(CollectionUtils.isEmpty(preHandOrderList)){
			return;
		}
		//预处理线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次预处理后续流程，151211，杨套红
		for(PreHandEWaybillOrderEntity preHandEWaybillOrderEntity : preHandOrderList){
			preHandEWaybillOrderDao.updatePreEWaybillOrderByPrimaryKey(preHandEWaybillOrderEntity.getId());
		}
		//进行数据的循环操作
		for(PreHandEWaybillOrderEntity preHandEWaybillOrderEntity : preHandOrderList){
			//新增线程异常日志
			OrderThreadPoollogEntity record = new OrderThreadPoollogEntity();
			record.setId(UUIDUtils.getUUID());
			record.setServiceName("AutoPreHandEWaybillOrderService");
			record.setExceptionData(preHandEWaybillOrderEntity.getId()+"-"+preHandEWaybillOrderEntity.getOrderNo());
			record.setExceptionPoolname("");
			record.setFailTimes(new BigDecimal(1));
			record.setCreateDate(new Date());
			record.setExceptionMsg("线程池满异常"+preHandEWaybillOrderEntity.getExceptionMsg());
			orderThreadPoollogDao.insert(record);
		}
	}

	/**
	  * @description EwaybillRelateEntity写数据
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午7:05:29
	  * @param taskId
	  * void
	 */
	public void addEwaybillRelateEntityData(String originalNumber,String orderNo){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("parentOrderNo", originalNumber);
		params.put("jobId", WaybillConstants.UNKNOWN);
	    List<EwaybillRelateEntity> ewaybillRelateList=waybillRelateDetailEntityService.selectEwaybillRelateByPrimaryOrderNo(params);
	    if(CollectionUtils.isEmpty(ewaybillRelateList)){
		if(StringUtil.isNotEmpty(originalNumber)){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("active", FossConstants.YES);
			map.put("scanType", "SCAN");
			map.put("taskType", WaybillConstants.STATUS_PICKUP);
			map.put("orignalOrderNo", originalNumber);
			List<EcomWaybillRelateEntity> ecomWaybillRelateList=waybillRelateDetailEntityService.queryAllEwaybillRelateByEcomWaybillRelateAndPdascan(map);
			if(CollectionUtils.isNotEmpty(ecomWaybillRelateList)){
//			for(EcomWaybillRelateEntity ecomWaybillRelateEntity:ecomWaybillRelateList){
			EcomWaybillRelateEntity ecomWaybillRelateEntity = ecomWaybillRelateList.get(0);
			EwaybillRelateEntity ewaybillRelateEntity=new EwaybillRelateEntity();
			ewaybillRelateEntity.setId(UUIDUtils.getUUID());
			ewaybillRelateEntity.setJobId(WaybillConstants.UNKNOWN);
			ewaybillRelateEntity.setParentOrderNo(ecomWaybillRelateEntity.getOrignalOrderNo());
			ewaybillRelateEntity.setTaskId(ecomWaybillRelateEntity.getTaskId());
			ewaybillRelateEntity.setCreateTime(new Date());
			ewaybillRelateEntity.setModifyTime(new Date());
			ewaybillRelateEntity.setOrderNo(orderNo);
			waybillRelateDetailEntityService.insertEwaybillRelateSelective(ewaybillRelateEntity);
//			}
			}
		  }
		}
	}
	
	@Override
	public int getActiveThreads() {
		Integer initThreads=5;
		String[] codes = new String[1];
		codes[0]=WaybillConstants.PKP_EWAYBILL_AUTO_PREHANDLE_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			if(null!=configurationParamsEntitys.get(0).getConfValue()){
				initThreads = Integer.valueOf(configurationParamsEntitys.get(0).getConfValue());
			}
		}
		return initThreads;
	}
	
	@Override
	public int deletePreEWaybillOrderByPrimaryKey(String id) {
		return preHandEWaybillOrderDao.deletePreEWaybillOrderByPrimaryKey(id);
	}

	@Override
	public int insertPreEWaybillOrder(PreHandEWaybillOrderEntity record) {
		return preHandEWaybillOrderDao.insertPreEWaybillOrder(record);
	}

	@Override
	public int insertPreEWaybillOrderSelective(PreHandEWaybillOrderEntity record) {
		return preHandEWaybillOrderDao.insertPreEWaybillOrderSelective(record);
	}

	@Override
	public PreHandEWaybillOrderEntity selectPreEWaybillOrderByPrimaryKey(String id) {
		return preHandEWaybillOrderDao.selectPreEWaybillOrderByPrimaryKey(id);
	}

	@Override
	public int updatePreEWaybillOrderByIdSelective(PreHandEWaybillOrderEntity record) {
		return preHandEWaybillOrderDao.updatePreEWaybillOrderById(record);
	}

	@Override
	public int updatePreEWaybillOrderById(PreHandEWaybillOrderEntity record) {
		return preHandEWaybillOrderDao.updatePreEWaybillOrderById(record);
	}

	@Override
	public int updateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo) {
		return preHandEWaybillOrderDao.updateJobIDTopByRowNum(vo);
	}

	@Override
	public List<PreHandEWaybillOrderEntity> queryGeneratePreEWaybillOrderByJobID(String jobId) {
		return preHandEWaybillOrderDao.queryGeneratePreEWaybillOrderByJobID(jobId);
	}
	
	/**
	 * 预处理电子运单数据持久层
	 */
	public void setPreHandEWaybillOrderDao(IPreHandEWaybillOrderDao preHandEWaybillOrderDao) {
		this.preHandEWaybillOrderDao = preHandEWaybillOrderDao;
	}

	/**
	 * 电子运单订单数据持久层
	 */
	public void setEwaybillOrderEntityDao(IEWaybillOrderEntityDao ewaybillOrderEntityDao) {
		this.ewaybillOrderEntityDao = ewaybillOrderEntityDao;
	}

	public void setDispatchOrderEntityDao(IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}
	
	public void setAutoExpressAcceptService(IAutoExpressAcceptService autoExpressAcceptService) {
		this.autoExpressAcceptService = autoExpressAcceptService;
	}

	public void setOrderThreadPoollogDao(IOrderThreadPoollogDao orderThreadPoollogDao) {
		this.orderThreadPoollogDao = orderThreadPoollogDao;
	}

	public void setOrderAutoExceptionLogService(IOrderAutoExceptionLogService orderAutoExceptionLogService) {
		this.orderAutoExceptionLogService = orderAutoExceptionLogService;
	}

	public void setPropertyFactory(PropertyFactory propertyFactory) {
		this.propertyFactory = propertyFactory;
	}

	public void setOrderReportService(IOrderReportService orderReportService) {
		this.orderReportService = orderReportService;
	}

	public void setOrderPdaReturnRecordService(
			IOrderPdaReturnRecordService orderPdaReturnRecordService) {
		this.orderPdaReturnRecordService = orderPdaReturnRecordService;
	}

	public void setExpressDeliverySmallZoneService(
			IExpressDeliverySmallZoneService expressDeliverySmallZoneService) {
		this.expressDeliverySmallZoneService = expressDeliverySmallZoneService;
	}

	public void setCourierScheduleService(
			ICourierScheduleService courierScheduleService) {
		this.courierScheduleService = courierScheduleService;
	}

	public void setExpressWorkerStatusService(
			IExpressWorkerStatusService expressWorkerStatusService) {
		this.expressWorkerStatusService = expressWorkerStatusService;
	}

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	public void setRegionCourierReportService(
			IRegionCourierReportService regionCourierReportService) {
		this.regionCourierReportService = regionCourierReportService;
	}

	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setBargainAppOrgService(IBargainAppOrgService bargainAppOrgService) {
		this.bargainAppOrgService = bargainAppOrgService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	public void setExpressVehiclesService(IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
	
	public void setExpressPartSalesDeptService(IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

	public void setDispatchOrderActionHistoryEntityDao(
			IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao) {
		this.dispatchOrderActionHistoryEntityDao = dispatchOrderActionHistoryEntityDao;
	}

	public void setDispatchVehicleRecordEntityDao(
			IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao) {
		this.dispatchVehicleRecordEntityDao = dispatchVehicleRecordEntityDao;
	}

	public void setOrderVehicleService(IOrderVehicleService orderVehicleService) {
		this.orderVehicleService = orderVehicleService;
	}

	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	public void setCommonExpressVehicleService(
			ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}
	
	public void setAutoPreHandEWaybillOrderService(
			IAutoPreHandEWaybillOrderService autoPreHandEWaybillOrderService) {
		this.autoPreHandEWaybillOrderService = autoPreHandEWaybillOrderService;
	}
	
	/**
	 * @param waybillRelateDetailEntityService the waybillRelateDetailEntityService to set
	 */
	public void setWaybillRelateDetailEntityService(IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
}
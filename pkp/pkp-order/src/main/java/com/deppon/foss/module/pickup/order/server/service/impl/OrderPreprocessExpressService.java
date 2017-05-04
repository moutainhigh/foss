/**
 * 2016-09-13快递预处理建议清理
 */
/*package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderBusinessLockService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderPreprocessExpressService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.OrderMutexElementConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressCityDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderMutexElement;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.order.api.shared.util.PropertyFactory;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

*//**
 * 
 * @ClassName: OrderPreprocessExpressService
 * @Description: 快递订单-预处理服务类
 * @author YANGBIN
 * @date 2014-5-4 下午4:19:06
 * 
 *//*
public class OrderPreprocessExpressService implements
		IOrderPreprocessExpressService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderPreprocessExpressService.class);
	// 变更表dao
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	// 订单dao
	private IDispatchOrderEntityDao dispatchOrderEntityDao;

	// 应用监控服务
	private IBusinessMonitorService businessMonitorService;
	// job日志dao
	private IShareJobDao shareJobDao;
	// 配置
	private PropertyFactory propertyFactory;

	// 城市分开关服务
//	private IExpressAutoScheduleService expressAutoScheduleService;
	private IConfigurationParamsService configurationParamsService;
	private static final String JOB_NAME = "ORDER_PREPROCESS_JOB";
	// 查询收派小区服务
	private IExpressDeliverySmallZoneService expressDeliverySmallZoneService;
	// 签到dao
	private IPdaSignEntityDao pdaSignEntityDao;
	//业务互斥锁服务
	private IOrderBusinessLockService orderBusinessLockService;
	*//**
	 * 
	 * @Title: preprocess
	 * @Description: 处理方法
	 * @return void 返回类型
	 * @throws
	 *//*
	@Override
	public void preprocess() {
		// 从配置参数中读取自动调度开关值
		String flag = FossConstants.NO;// 默认开关关闭
		String[] codes = new String[1];
		codes[0] = ConfigurationParamsConstants.PKP_AUTO_EXPRESS_SCHEDULE_MANAGE;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService
				.queryConfigurationParamsBatchByCode(codes);
		if (null != configurationParamsEntitys
				&& configurationParamsEntitys.size() > 0) {
			flag = configurationParamsEntitys.get(0).getConfValue();// 开关是否关闭
		}
		*//**
		 * 14.7.15 gcl AUTO-165 1: 总开关未开 取所有订单；
		 *  2:总开关开启  取订单为B
		 *//*
		List<DispatchOrderChangeEntity> changeList=null;
		//判断总开关是否开启
		if (StringUtils.equals(FossConstants.YES, flag)) {
			ExpressOrderDto queryOrderChangeDto = new ExpressOrderDto();
			String jobId = "B";
			queryOrderChangeDto.setJobId(jobId);
			queryOrderChangeDto.setRownum(1000);
			queryOrderChangeDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
			changeList = dispatchOrderChangeEntityDao.queryChangeByExpressDto(queryOrderChangeDto);
			handlePreExpressOrder(changeList);
		} else { //总开关未开 取所有订单
//			changeList = dispatchOrderChangeEntityDao
//					.queryExpressChange();
			ExpressOrderDto queryOrderChangeDto = new ExpressOrderDto();
			String jobId = "N/A";
			queryOrderChangeDto.setJobId(jobId);
			queryOrderChangeDto.setRownum(1000);
			//cdl 1017 ---begin---
			List<String> productCodes = new ArrayList<String>();
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE);
			productCodes.add(ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
			queryOrderChangeDto.setProductCodes(productCodes);
			//cdl 1017 ---end---
			changeList = dispatchOrderChangeEntityDao.queryChangeByExpressDto(queryOrderChangeDto);
			handlePreExpressOrder(changeList);
			jobId = "B";
			queryOrderChangeDto.setJobId(jobId);
			changeList = dispatchOrderChangeEntityDao.queryChangeByExpressDto(queryOrderChangeDto);
			handlePreExpressOrder(changeList);
		}
	}
	*//**
	 * 
	 * @Title: handlePreExpressOrder
	 * @Description: 预处理快递订单
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 *//*
	private void handlePreExpressOrder(List<DispatchOrderChangeEntity> changeList) {
		if (changeList != null && changeList.size() != 0) {
			LOGGER.info("快递订单预处理开始。。。。。。");
			// 获得待处理的订单
			List<DispatchOrderEntity> orderList = dispatchOrderChangeEntityDao
					.queryOrder(changeList);
			//8.25 gcl ON-843
			dispatchOrderChangeEntityDao.deleteChange(changeList);
			//获取订单业务锁自动释放时间
			String orderLockTtl = configurationParamsService.queryConfValueByCode(DispatchOrderStatusConstants.ORDER_LOCK_TTL);
			// 调用GIS接口返回接货小区编码
			for (DispatchOrderEntity dispatchOrderEntity : orderList) {
				LOGGER.info("快递预处理订单号：" + dispatchOrderEntity.getOrderNo());
				OrderMutexElement mutexElement = null;
				if (StringUtils.isBlank(orderLockTtl)) {
					mutexElement = new OrderMutexElement(dispatchOrderEntity.getOrderNo(), "订单更新_快递预处理建议", 
							OrderMutexElementConstants.DISPATCHORDERNO_LOCK);
					LOGGER.info("快递预处理订单"+dispatchOrderEntity.getOrderNo()+"时订单业务锁自动释放时间采用默认值");
				} else {
					mutexElement = new OrderMutexElement(dispatchOrderEntity.getOrderNo(), "订单更新_快递预处理建议", 
							OrderMutexElementConstants.DISPATCHORDERNO_LOCK,Integer.valueOf(orderLockTtl));
				}
				
				//互斥锁定
		     	boolean isLocked = orderBusinessLockService.lock(mutexElement, DispatchOrderStatusConstants.ZERO);	
		     	if(!isLocked){
		     		LOGGER.info("自动受理快递订单"+dispatchOrderEntity.getOrderNo()+"预处理建议时订单已锁定");
		     		dispatchOrderChangeEntityDao.deleteChangeByOrderID(dispatchOrderEntity.getId());
		     		continue;
		     	}
				Map<String, String> map = formatToMap(dispatchOrderEntity);
				Map<String, Object> result = null;
				try {
					// 调用GIS接口，
					result = GisInterfaceUtil.callGisInterface(
							propertyFactory.getGisExpressUrl(), map);
					updateOrderPreprocess(dispatchOrderEntity, result);
					String orderId = dispatchOrderEntity.getId();
					dispatchOrderChangeEntityDao.deleteChangeByOrderID(orderId);
				} catch (Exception e) {
					dispatchOrderChangeEntityDao.deleteChangeByOrderID(dispatchOrderEntity.getId());
					LOGGER.error(
							dispatchOrderEntity.getOrderNo() + e.getMessage(),
							e);
					addExceptionLog(dispatchOrderEntity, e);
					continue;
				} finally {
					dispatchOrderChangeEntityDao.deleteChangeByOrderID(dispatchOrderEntity.getId());
					orderBusinessLockService.unlock(mutexElement);
					LOGGER.info("自动受理订单"+dispatchOrderEntity.getOrderNo()+"预处理建议结束");
				}
			}
			

		} else {
			LOGGER.info("无快递订单进行预处理");
		}
	}

	*//**
	 * 
	 * @Title: getActiveExpressCities
	 * @Description: 快递设置分开关
	 * @param @return 设定文件
	 * @return List<ExpressCityDto> 返回类型
	 * @throws
	 *//*
	private List<ExpressCityDto> getActiveExpressCities() {
		// 如果是总开关，城市结果集合为空
		List<ExpressCityDto> cityList = new ArrayList<ExpressCityDto>();
		ExpressAutoScheduleEntity queryEntity = new ExpressAutoScheduleEntity();
		// 设置有效
		queryEntity.setActive("Y");
		
		int start = 0;
		int limit = 0;
		// 查询是否存在结果
		Long count = expressAutoScheduleService.queryCityNameCount(queryEntity);
		if(null != count
				&& count > 1){
			limit = count.intValue();
			// 取得结果集
			List<ExpressAutoScheduleEntity> autoCityList = expressAutoScheduleService
					.queryExpressAutoScheduleListByCityName(queryEntity, limit, start);
			// 判断结果不为空
			if(CollectionUtils.isNotEmpty(autoCityList)){
				for(ExpressAutoScheduleEntity entity : autoCityList){
					ExpressCityDto cityDto = getExpressCityDto(entity);
					//如果为空，跳出当前循环
					if(null != cityDto){
						cityList.add(cityDto);
					}
				}
			}else {
				cityList = null;
			}
		}else{
			cityList = null;
		}
		return cityList;

	}

	*//**
	 * 
	 * @Title: getExpressCityDto
	 * @Description: 取得分开关城市
	 * @param @param ExpressAutoScheduleEntity 设定文件
	 * @return void 返回类型
	 * @throws
	 *//*
	private ExpressCityDto getExpressCityDto(ExpressAutoScheduleEntity entity) {
		// 校验传入的参数是否为空
		if (null == entity) {
			return null;
		}
		if (StringUtils.isEmpty(entity.getCityCode())) {
			return null;
		}
		if (StringUtils.isEmpty(entity.getStartTime())) {
			return null;
		}
		if (StringUtils.isEmpty(entity.getEndTime())) {
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
		calendar1.add(Calendar.MINUTE, startMinNum);
		calendar1.add(Calendar.SECOND, 0);
		Date startTime = calendar1.getTime();
		cityDto.setStartTime(startTime);

		String endTimeStr = entity.getEndTime();
		Calendar calendar2 = Calendar.getInstance();
		String[] strs2 = getHourMinNumArray(endTimeStr);
		int endHourNum = Integer.valueOf(strs2[0]);
		int endMinNum = Integer.valueOf(strs2[1]);
		calendar2.set(Calendar.HOUR_OF_DAY, endHourNum);
		calendar2.add(Calendar.MINUTE, endMinNum);
		calendar2.add(Calendar.SECOND, 0);
		Date endTime = calendar2.getTime();
		cityDto.setEndTime(endTime);
		return cityDto;
	}

	*//**
	 * getHourMinNumArray
	 * 
	 * @param timeStr
	 * @return
	 *//*

	private String[] getHourMinNumArray(String timeStr) {
		String[] strs = timeStr.split(":");
		return strs;
	}

	*//**
	 * 
	 * @Title: addExceptionLog
	 * @Description: 添加异常日志
	 * @param @param dispatchOrderEntity
	 * @param @param e 设定文件
	 * @return void 返回类型
	 * @throws
	 *//*
	@Override
	public void addExceptionLog(DispatchOrderEntity dispatchOrderEntity,
			Exception e) {
		// 添加至异常日志表
		JobExceptionLogEntity exceptionLogEntity = new JobExceptionLogEntity();
		exceptionLogEntity.setId(UUIDUtils.getUUID());
		exceptionLogEntity.setJobName(JOB_NAME);
		exceptionLogEntity.setErrorId(dispatchOrderEntity.getId());
		exceptionLogEntity.setErrorCode(e.getMessage()==null?"":e.getMessage().substring(0,50));
		StringBuffer sb = new StringBuffer();
		// 堆栈信息
		for (StackTraceElement element : e.getStackTrace()) {
			if (element.toString().indexOf("com.deppon") != -1) {
				sb.append(element.toString());
				sb.append(",");
			}
		}
		exceptionLogEntity.setErrorMsg(sb.substring(0, sb.length() - 1));
		shareJobDao.addJobExceptionLogEntity(exceptionLogEntity);
	}

	*//**
	 * 
	 * @Title: updateOrderPreprocess
	 * @Description: 根据GIS返回结果，查询接货小区，更新订单预处理建议
	 * @param @param dispatchOrderEntity
	 * @param @param result 设定文件
	 * @return void 返回类型
	 * @throws
	 *//*
	@Override
	public void updateOrderPreprocess(DispatchOrderEntity dispatchOrderEntity,
			Map<String, Object> result) {
		if (result != null) {
			dispatchOrderEntity.setOrderStatus("");
			// 获得GIS坐标id
			String scopeCoordinatesId = (String) result
					.get("scopeCoordinatesId");
			LOGGER.info("GIS坐标ID:" + scopeCoordinatesId);
			// 是否已入库标记
			String isCollect = (String) result.get("isCollect");
			if (StringUtils.isNotEmpty(isCollect)
							&& FossConstants.YES.equals(isCollect)) {
				dispatchOrderEntity.setIsCollect(isCollect);
				dispatchOrderEntityDao.updateByPrimaryKeySelective(dispatchOrderEntity);
			}
			// scopeCoordinatesId为空
			if (scopeCoordinatesId == null) {
				JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
				successLogEntity.setId(UUIDUtils.getUUID());
				successLogEntity.setJobName(JOB_NAME);
				successLogEntity.setSuccessId(dispatchOrderEntity.getId());
				successLogEntity.setSuccessMsg("快递调用GIS接口GIS坐标ID为空");
				shareJobDao.addJobSuccessLogEntity(successLogEntity);
				dispatchOrderChangeEntityDao.deleteChangeByOrderID(dispatchOrderEntity.getId());
				// 不处理
				LOGGER.info("快递调用GIS接口GIS坐标ID为空");
				return;
			}
			
			if(StringUtils.isEmpty(dispatchOrderEntity.getSmallZoneCodeSuggested())){
				//14.7.15 gcl AUTO-165 预处理意见调用
				ExpressDeliverySmallZoneEntity queryEntity = new ExpressDeliverySmallZoneEntity();
				queryEntity.setGisid(scopeCoordinatesId);
				queryEntity.setActive("Y");
				// 根据GIS返回ID，获取对应唯一的收派小区，如果不存在，则退出
				ExpressDeliverySmallZoneEntity entity = expressDeliverySmallZoneService
						.querySmallZoneByGisId(queryEntity);
				if (null == entity
						|| StringUtils.isEmpty(entity.getRegionCode())) {
					String reason = dispatchOrderEntity.getOrderNo()+"订单号GIS返回的ID:"+scopeCoordinatesId+"，找不到对应的收派小区编码";
					LOGGER.info(reason);
					//zxy 20140718 AUTO-165 start 新增:增加日志
					JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
					successLogEntity.setId(UUIDUtils.getUUID());
					successLogEntity.setJobName(JOB_NAME);
					successLogEntity.setSuccessId(dispatchOrderEntity.getId());
					successLogEntity.setSuccessMsg(reason);
					shareJobDao.addJobSuccessLogEntity(successLogEntity);
					dispatchOrderChangeEntityDao.deleteChangeByOrderID(dispatchOrderEntity.getId());
					//zxy 20140718 AUTO-165 end 新增:增加日志
					return;
				}
				// 取得快递收派小区编码
				String expressSmallZoneCode = entity.getRegionCode();
				String expressSmallZoneName = entity.getRegionName();
				dispatchOrderEntity.setSmallZoneCodeSuggested(expressSmallZoneCode);
				dispatchOrderEntity.setSmallZoneNameSuggested(expressSmallZoneName);
				dispatchOrderEntity.setPreprocessId(scopeCoordinatesId); //14.7.23 gcl AUTO-195 gisid
				//取 此收派小区的一个可用快递员
				List<OwnTruckDto> ownList=pdaSignEntityDao.queryExpressDriverBySmallZone(expressSmallZoneCode);
					if(ownList!=null&&ownList.size()>0){
						OwnTruckDto dto=ownList.get(0);
						// 车牌号
						dispatchOrderEntity.setVehicleNoSuggested(dto.getVehicleNo());
						// 司机姓名
						dispatchOrderEntity.setDriverNameSuggested(dto.getDriverName());
						// 司机编码
						dispatchOrderEntity.setDriverCodeSuggested(dto.getDriverCode());
					}
			}
			// 更新数据库中对应订单的采集情况
			dispatchOrderEntityDao
			.updateByPrimaryKeySelective(dispatchOrderEntity);
			*//**
			 * 构造UserEntity
			 *//*
			UserEntity user = new UserEntity();
			// 设置编码
			user.setEmpCode(dispatchOrderEntity.getCreateUserCode());
			// 设置雇员名称
			user.setEmpName(dispatchOrderEntity.getCreateUserName());
			// 设置用户名称
			user.setUserName(dispatchOrderEntity.getCreateUserName());
			*//**
			 * 构造EmployeeEntity
			 *//*
			EmployeeEntity employeeEntity = new EmployeeEntity();
			// 设置雇员名称
			employeeEntity.setEmpCode(dispatchOrderEntity
					.getCreateUserCode());
			// 设置用户名称
			employeeEntity.setEmpName(dispatchOrderEntity
					.getCreateUserName());
			user.setEmployee(employeeEntity);
			*//**
			 * 构造部门实体
			 *//*
			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			// 设置编码
			dept.setCode(dispatchOrderEntity.getSalesDepartmentCode());
			// 设置名称
			dept.setName(dispatchOrderEntity.getSalesDepartmentName());
			// 订单自动分配成功数
			businessMonitorService
			.counter(
					BusinessMonitorIndicator.ORDER_AUTO_ASSIGN_SUCCESS_COUNT,
					new CurrentInfo(user, dept));
			JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
			successLogEntity.setId(UUIDUtils.getUUID());
			successLogEntity.setJobName(JOB_NAME);
			successLogEntity.setSuccessId(dispatchOrderEntity.getId());
			successLogEntity.setSuccessMsg("快递订单处理成功");
			shareJobDao.addJobSuccessLogEntity(successLogEntity);
			dispatchOrderChangeEntityDao.deleteChangeByOrderID(dispatchOrderEntity.getId());
			LOGGER.info("快递订单订单预处理完成:" + dispatchOrderEntity.getOrderNo());
		} else {
			JobSuccessLogEntity successLogEntity = new JobSuccessLogEntity();
			successLogEntity.setId(UUIDUtils.getUUID());
			successLogEntity.setJobName(JOB_NAME);
			successLogEntity.setSuccessId(dispatchOrderEntity.getId());
			successLogEntity.setSuccessMsg("快递订单调用GIS接口返回空");
			shareJobDao.addJobSuccessLogEntity(successLogEntity);
			dispatchOrderChangeEntityDao.deleteChangeByOrderID(dispatchOrderEntity.getId());
			// 不处理
			LOGGER.info("快递订单调用Gis接口返回空");
		}
	}

	*//**
	 * 
	 * @Title: formatToMap
	 * @Description: 将订单实体转为Map
	 * @param @param dispatchOrderEntity
	 * @param @return 设定文件
	 * @return Map<String,String> 返回类型
	 * @throws
	 *//*
	private static Map<String, String> formatToMap(
			DispatchOrderEntity dispatchOrderEntity) {
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

	*//**
	 * Sets the dispatchOrderEntityDao.
	 * 
	 * @param dispatchOrderEntityDao
	 *            the dispatchOrderEntityDao to set
	 *//*
	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	*//**
	 * Sets the regionalVehicleService.
	 * 
	 * @param regionalVehicleService
	 *            the regionalVehicleService to set
	 *//*
	public void setRegionalVehicleService(
			IRegionalVehicleService regionalVehicleService) {
	}

	*//**
	 * Sets the pdaSignEntityDao.
	 * 
	 * @param pdaSignEntityDao
	 *            the pdaSignEntityDao to set
	 *//*
	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	*//**
	 * Sets the businessMonitorService.
	 * 
	 * @param businessMonitorService
	 *            the businessMonitorService to set
	 *//*
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	*//**
	 * Sets the shareJobDao.
	 * 
	 * @param shareJobDao
	 *            the shareJobDao to set
	 *//*
	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	public void setExpressDeliverySmallZoneService(
			IExpressDeliverySmallZoneService expressDeliverySmallZoneService) {
		this.expressDeliverySmallZoneService = expressDeliverySmallZoneService;
	}
	*//**
	 * Sets the propertyFactory.
	 * 
	 * @param propertyFactory
	 *            the propertyFactory to set
	 *//*
	public void setPropertyFactory(PropertyFactory propertyFactory) {
		this.propertyFactory = propertyFactory;
	}

	public void setPickupAndDeliverySmallZoneService(
			IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService) {
	}

	public void setDispatchOrderChangeEntityDao(
			IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
	}

//	public void setExpressAutoScheduleService(
//			IExpressAutoScheduleService expressAutoScheduleService) {
//		this.expressAutoScheduleService = expressAutoScheduleService;
//	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	public void setOrderBusinessLockService(
			IOrderBusinessLockService orderBusinessLockService) {
		this.orderBusinessLockService = orderBusinessLockService;
	}

}
*/
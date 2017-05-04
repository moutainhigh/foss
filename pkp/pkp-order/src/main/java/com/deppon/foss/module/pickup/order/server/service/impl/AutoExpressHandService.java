/**
 * 2016-09-13版本清楚快递自动调度
 */
/*package com.deppon.foss.module.pickup.order.server.service.impl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.ICourierScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderThreadPoollogDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressAcceptService;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressHandService;
import com.deppon.foss.module.pickup.order.api.server.service.IExpressWorkerStatusService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderPdaReturnRecordService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderReportService;
import com.deppon.foss.module.pickup.order.api.server.service.IRegionCourierReportService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderThreadPoollogEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.order.api.shared.util.NumberResolutionFloor;
import com.deppon.foss.module.pickup.order.api.shared.util.PropertyFactory;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.ProductCodeUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

*//**
 * 
 * @ClassName: AutoExpressHandService 
 * @Description: 自动处理service 
 * @author YANGBIN
 * @date 2014-5-6 上午10:28:24 
 *
 *//*
public class AutoExpressHandService extends OrderTheadPool implements IAutoExpressHandService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoExpressHandService.class);
	
	// 快递订单自动受理服务
	private IAutoExpressAcceptService autoExpressAcceptService;
	// 线程池异常日志
	private IOrderThreadPoollogDao orderThreadPoollogDao;
	// 变更表dao
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	// 业务异常日志
	private IOrderAutoExceptionLogService orderAutoExceptionLogService;
	// 配置
	private PropertyFactory propertyFactory;
	// 每日统计报表服务
	private IOrderReportService orderReportService;
	// 每日PDA退回记录服务
	private IOrderPdaReturnRecordService orderPdaReturnRecordService;
	// 查询收派小区服务
	private IExpressDeliverySmallZoneService expressDeliverySmallZoneService;
	// 快递排班信息service
	private ICourierScheduleService courierScheduleService;
	// 快递员工作状态服务
	private IExpressWorkerStatusService expressWorkerStatusService;
	// 订单dao
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	// 应用监控服务
	private IBusinessMonitorService businessMonitorService;
	private IRegionCourierReportService regionCourierReportService;
	
	
	//裹裹抢单标示
		private static final String GG_SERVICE_TYPE = "抢单";
		*//**
		 * 员工
		 * service接口
		 *//*
		private IEmployeeService employeeService;
	*//**
	 * 自动受理
	 * @author YANGBIN
	 * @date 2014-4-29 上午10:05:38
	 *//*
	@Override
	public void businessExecutor(Object obj) {
		DispatchOrderEntity dispatchOrderEntity = (DispatchOrderEntity)obj;
		if(ProductCodeUtils.isExpress(dispatchOrderEntity.getProductCode())) {
			if(dispatchOrderEntity.getOrderStatus().equals(DispatchOrderStatusConstants.STATUS_RETURN)) {
				return;	
			}
		}
		try {
			// 返回对应定人定区快递员或机动快递员
			LOGGER.info("businessExecutor:" + dispatchOrderEntity.getOrderNo());

			Map<String, Object> expMap =null;
			OrderHandleDto orderHandleDto =null;
			//裹裹抢单业务 直接分配给签单人员
			if (StringUtils.isNotBlank(dispatchOrderEntity.getServiceType())
					&& StringUtils.isNotBlank(dispatchOrderEntity.getPickupManId())
					&& GG_SERVICE_TYPE.equals(dispatchOrderEntity.getGgOrderType())) {
				LOGGER.info("裹裹抢单订单："+dispatchOrderEntity.getOrderNo());
				//通过司机工号查询姓名
				EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(dispatchOrderEntity.getPickupManId());
				LOGGER.info("裹裹抢单订单快递员为："+emp.getEmpName());
				if (emp != null && StringUtils.isNotBlank(emp.getEmpName())) {
					orderHandleDto = getGGOrderHandleDto(dispatchOrderEntity, emp);
				}else{
					expMap = matchExpress(dispatchOrderEntity);
					orderHandleDto = getEOrderHandleDto(dispatchOrderEntity, expMap);
				}
			}else {
				expMap = matchExpress(dispatchOrderEntity);
				orderHandleDto = getEOrderHandleDto(dispatchOrderEntity, expMap);
			}
			
			// 判断不为空，将订单进行自动受理
			if (null != orderHandleDto) {
				// 线程受理订单调用开始
				boolean success = autoExpressAcceptService.acceptOrder(orderHandleDto);
				if(success){
					LOGGER.info("线程自动受理成功。。。。。。");
				}else {
					LOGGER.info("线程自动受理失败！！！！！！");
				}
			}
		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
			String exceptionType = "MATCH_EXPRESS_SYS_EXCEPTION";
			String reason = "自动匹配出现系统异常";
			//zxy 20140709  内部优化 start 修改:日志完善
			dispatchOrderEntity.appendException(reason);
			dispatchOrderEntity.appendLog(e.getMessage());
			addExceptionLog(dispatchOrderEntity,exceptionType,dispatchOrderEntity.getSbLog().toString());
			// 删除新待预处理 记录状态，转为人工处理
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getId())){
				dispatchOrderChangeJobId(dispatchOrderEntity.getId());
			}
			//zxy 20140709  内部优化 end 修改:日志完善
		}
		
	}
	
	
	public OrderHandleDto getEOrderHandleDto(DispatchOrderEntity dispatchOrderEntity, Map<String, Object> expMap){
		OrderHandleDto orderHandleDto = null;
		if (null != expMap && expMap.size() > 0) {
			// 快递订单预处理
			orderHandleDto = getOrderHandleDto(dispatchOrderEntity, expMap);
			
		} else {
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getId())){
				String orderId = dispatchOrderEntity.getId();
				String exceptionType = "MATCH_EXPRESS_NULL";
				String reason = "无法找到待分配的快递员";
				//zxy 20140709  内部优化 start 修改:日志完善
				dispatchOrderEntity.appendException(reason);
				addExceptionLog(dispatchOrderEntity,exceptionType,dispatchOrderEntity.getSbLog().toString());
				//zxy 20140709  内部优化 end 修改:日志完善
				//zxy 20140716 AUTO-165 修改:将change状态改成B
				// 删除新待预处理 记录状态，转为人工处理
				dispatchOrderChangeJobId(orderId);
			}							
		}
		return orderHandleDto;
	}
	
	public OrderHandleDto getGGOrderHandleDto(DispatchOrderEntity dispatchOrderEntity,
			EmployeeEntity emp){

		if (null == emp ) {
			return null;
		}
		OrderHandleDto orderHandleDto = new OrderHandleDto();
		orderHandleDto.setId(dispatchOrderEntity.getId());
		orderHandleDto.setOrderNo(dispatchOrderEntity.getOrderNo());
		orderHandleDto.setDeliveryCustomerCode(dispatchOrderEntity.getDeliveryCustomerCode());
		orderHandleDto.setOrderType(dispatchOrderEntity.getOrderType());
		orderHandleDto.setOriginOrderStatus(dispatchOrderEntity
				.getOrderStatus());
		orderHandleDto.setIsCustomer(dispatchOrderEntity.getIsCustomer());
		orderHandleDto.setIsPda(dispatchOrderEntity.getIsPda());
		orderHandleDto.setIsSms(dispatchOrderEntity.getIsSms());
		orderHandleDto.setIsCollect(FossConstants.YES);
		orderHandleDto.setDriverCode(dispatchOrderEntity.getPickupManId());
		orderHandleDto.setDriverName(emp.getEmpName());
		orderHandleDto.setVehicleNo("德"+dispatchOrderEntity.getPickupManId());
		orderHandleDto.setDriverMobile(emp.getMobilePhone()); 
		//14.8.21 gcl 自动调度时 同时设置预处理车和司机信息 方便下次退回后找预处理
		orderHandleDto.setVehicleNoSuggested("德"+dispatchOrderEntity.getPickupManId());
		orderHandleDto.setDriverCodeSuggested(dispatchOrderEntity.getPickupManId());
		orderHandleDto.setDriverNameSuggested(emp.getEmpName());
		
		orderHandleDto.setWeight(dispatchOrderEntity.getWeight());
		orderHandleDto.setVolume(dispatchOrderEntity.getVolume());
		orderHandleDto.setCustomerMobile(dispatchOrderEntity.getMobile());
		orderHandleDto.setTel(dispatchOrderEntity.getTel());
		orderHandleDto.setDeliveryCustomerMobile(dispatchOrderEntity.getMobile());
		orderHandleDto.setCustomerName(dispatchOrderEntity.getCustomerName());
		StringBuffer pickupAddress = new StringBuffer();
		pickupAddress.append(dispatchOrderEntity.getPickupProvince());
		pickupAddress.append(dispatchOrderEntity.getPickupCity());
		pickupAddress.append(dispatchOrderEntity.getPickupCounty());
		pickupAddress.append(dispatchOrderEntity.getPickupElseAddress());
		orderHandleDto.setPickupCity(dispatchOrderEntity.getPickupCity());
		orderHandleDto.setPickupAddress(pickupAddress.toString());
		orderHandleDto.setSalesDepartmentCode(dispatchOrderEntity
				.getSalesDepartmentCode());
		orderHandleDto.setSalesDepartmentName(dispatchOrderEntity
				.getSalesDepartmentName());
		orderHandleDto.setOrderNotes(dispatchOrderEntity.getOrderNotes());
		// 接货时间
		StringBuffer pickupTime = new StringBuffer();
		Date earliestPickupTime = dispatchOrderEntity.getEarliestPickupTime();
		Date latestPickupTime = dispatchOrderEntity.getLatestPickupTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(null != earliestPickupTime
				&& null != latestPickupTime){
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
		if(owsPickupTime.length()>0) {
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
		orderHandleDto.setOperateOrgName("系统自动调度");
		return orderHandleDto;
	
	}
	*//**
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
	 *//*
	private Map<String, Object> matchExpress(
			DispatchOrderEntity dispatchOrderEntity) {
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
			orderPdaReturnRecordEntity.setPickupAddress(pickupAddress
					.toString());
			// 根据地址查询每日报表，在当天是否存在数据
			OrderReportEntity queryParam = new OrderReportEntity();
			queryParam.setPickupAddress(pickupAddress.toString());
			OrderReportEntity orderReportEntity = orderReportService
					.selectOrderReportByAddress(queryParam);
			// 如果当日匹配记录存在数据
			if (null != orderReportEntity) {
				dispatchOrderEntity.appendLog("当日报表ID:" + orderReportEntity.getId());
				orderPdaReturnRecordEntity.setCreateCode(orderReportEntity
						.getExpressDriverCode());
				OrderPdaReturnRecordEntity recordEntity = 
						orderPdaReturnRecordService.queryOrderPdaReturnByCommon(orderPdaReturnRecordEntity);
				// 如果存在退回记录  14.7.2 匹配当日分配记录失败，进入下一步收派小区匹配  
				// yb-2014.07.08 AUTO-115如果生成报表的时间  早于  退回记录时间，则需要进行自动匹配，否则，进行读取报表数据
				if (null != recordEntity&&recordEntity.getOperateTime().after(orderReportEntity.getCreateDate())) {
					dispatchOrderEntity.appendLog("退单记录ID:" + orderReportEntity.getId());
					expMap = expressDeliveryArea(expMap,dispatchOrderEntity,pickupAddress,orderPdaReturnRecordEntity);
				}
				else {
					// 取得对应快递员工号，姓名
					expMap = new HashMap<String, Object>();
					expMap.put("driverCode",
							orderReportEntity.getExpressDriverCode());
					expMap.put("driverName",
							orderReportEntity.getExpressDriverName());
					expMap.put("driverPhone",
							orderReportEntity.getExpressDriverPhone());
					expMap.put("vehicleNo",
							orderReportEntity.getVehicleNo());
					expMap.put("pickupRegionCode",
							orderReportEntity.getPickupRegionCode());
					expMap.put("pickupRegionName",
							orderReportEntity.getPickupRegionName());
					//14.8.15 gcl AUTO-230
					expMap.put("isCollect",FossConstants.YES);
				}
			} else { 
				expMap=expressDeliveryArea(expMap,dispatchOrderEntity,pickupAddress,orderPdaReturnRecordEntity);
			}
		}
		return expMap;
	}
	*//**
	 * @Description: 根据GIS返回地址ID，到综合匹配出对应时间接货小区， 1、根据小区、解析出的楼层匹配快递定人定区集合
	 *               2、1情况不存在，根据小区匹配机动区
	 * @param expMap
	 * @param dispatchOrderEntity
	 * @param pickupAddress
	 * @param orderPdaReturnRecordEntity
	 *//*
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
		if (!result.isEmpty()
				&& result.size() > 0) {
			scopeCoordinatesId = (String) result
					.get("scopeCoordinatesId");
			dispatchOrderEntity.appendLog("gisID:" + scopeCoordinatesId);
			// GIS匹配规则的ID，如果不为空,存在收派小区
			if (StringUtils.isNotEmpty(scopeCoordinatesId)) {
				ExpressDeliverySmallZoneEntity queryEntity = new ExpressDeliverySmallZoneEntity();
				queryEntity.setGisid(scopeCoordinatesId);
				queryEntity.setActive("Y");
				// 根据GIS返回ID，获取对应唯一的收派小区，如果不存在，则退出自动处理
				ExpressDeliverySmallZoneEntity entity = expressDeliverySmallZoneService
						.querySmallZoneByGisId(queryEntity);
				if (null == entity
						|| StringUtils.isEmpty(entity.getRegionCode())) {
					
					// 添加异常服务日志
					String exceptionType = "EXPRESSSMALLZONECODE_IS_NULL";
					String reason = dispatchOrderEntity.getOrderNo()+"订单号GIS返回的ID:"+scopeCoordinatesId+"，找不到对应的收派小区编码";
					addExceptionLog(dispatchOrderEntity,exceptionType,reason);	
					expMap=null;
					return expMap;
				}
				dispatchOrderEntity.setPreprocessId(scopeCoordinatesId);//14.7.23 gcl AUTO-195 gisid
				// 取得快递收派小区编码
				String expressSmallZoneCode = entity.getRegionCode();
				String expressSmallZoneName = entity.getRegionName();
				dispatchOrderEntity.setSmallZoneCodeSuggested(expressSmallZoneCode);
				dispatchOrderEntity.setSmallZoneNameSuggested(expressSmallZoneName);
				// 收派小区存在，再根据地址解析出楼层，如果小区和楼层不为空，则取获得快递员排班信息
				Integer floor = NumberResolutionFloor
						.getFloorNum(pickupAddress.toString());
				dispatchOrderEntity.appendLog("收派小区:" + expressSmallZoneCode + "楼层号:" + floor);
				// 根据快递收派小区、解析的楼层号获得对应的快递员排班信息
				List<CourierScheduleEntity> scheduleList =null;
				// 更新预处理订单，采集数据
				updateExpOrderPreprocess(dispatchOrderEntity, result);
				//14.7.8 gcl AUTO-113 1:根据解析的楼层号获得对应的快递员排班信息 2:如果没有匹配成功 根据收派小区查询排班信息
				if(null != floor){
					scheduleList = courierScheduleService
							.queryCourierScheduleByCondition(
									expressSmallZoneName, floor);//14.9.10 gcl 修改为按小区名称查询
					if (CollectionUtils.isNotEmpty(scheduleList)) {
						expMap = getexpMapByFloor(expMap, scheduleList, dispatchOrderEntity, orderPdaReturnRecordEntity);
					}else{
						dispatchOrderEntity.appendLog("楼层快递员排班为空");
					}
				}
				if(null == expMap){
					scheduleList = courierScheduleService
							.queryCourierScheduleByCondition(
									expressSmallZoneName, null);
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
	*//**
	 * @Description:14.7.8 根据收派小区查询排班信息
	 *              先在可用定区快递员中查当日订单量最少的，如果没有匹配成功，查可用机动快递员中当日订单量最少的
	 * @param expMap
	 * @param scheduleList
	 * @param dispatchOrderEntity
	 * @param orderPdaReturnRecordEntity
	 * @return
	 *//*
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
				}/////////////快递员为机动快递 14.7.2
				else if(DispatchOrderStatusConstants.COURIER_NATURE_MOTORIZED.equals(courier.getCourierNature())){
					autoCourierList.add(courier);
				}	
			}
			//先在可用定区快递员中查当日订单量最少的
			if(null == expMap&&CollectionUtils.isNotEmpty(zoneCourierList)
					&& zoneCourierList.size() > 0){
				dispatchOrderEntity.appendLog("定区" + zoneCourierList.size() + "人");
				expMap = minDriverCode(expMap, dispatchOrderEntity, orderPdaReturnRecordEntity, zoneCourierList);
			}else{
				dispatchOrderEntity.appendLog("无定区快递员");
			}
			//没有匹配成功，查可用机动快递员中当日订单量最少的
			if(null == expMap){
				if(CollectionUtils.isNotEmpty(autoCourierList)
					&& autoCourierList.size() > 0){
					dispatchOrderEntity.appendLog("机动" + autoCourierList.size() + "人");
					expMap = minDriverCode(expMap, dispatchOrderEntity, orderPdaReturnRecordEntity, autoCourierList);
                }else{
					dispatchOrderEntity.appendLog("无机动快递员");
				}
			}else{
				dispatchOrderEntity.appendLog("定区车牌:" + expMap.get("vehicleNo") + ",司机:" + expMap.get("driverCode"));
			}
			// 判断机动快递员是否为空  14.7.2定区快递员中未有匹配到
			if (null == expMap){
//				String exceptionType = "MATCH_EXPRESS_REPORT";
//				String reason = dispatchOrderEntity.getOrderNo()+"每日快递报表最少快递员不存在";	
//				addExceptionLog(dispatchOrderEntity,exceptionType,reason);
			}else{
				dispatchOrderEntity.appendLog("机动车牌:" + expMap.get("vehicleNo") + ",司机:" + expMap.get("driverCode"));
			}
		}else{
			String reason = dispatchOrderEntity.getOrderNo()+"快递收派小区获得对应的快递员排班信息为空";	
			addExceptionLog(dispatchOrderEntity,"MATCH_EXPRESS_SMALLZONE",reason);
		}
		return expMap;
	}
	*//**
	 * AUTO-115  是否存在退回记录 
	 * @param orderPdaReturnRecordEntity
	 * @return
	 *//*
	private Long isExistsPdareturn(OrderPdaReturnRecordEntity orderPdaReturnRecordEntity){
		Long count=0L;
		OrderPdaReturnRecordEntity recordEntity = 
				orderPdaReturnRecordService.queryOrderPdaReturnByCommon(orderPdaReturnRecordEntity);
		if (null != recordEntity) {
			OrderReportEntity orderReportEntity=new OrderReportEntity();
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
	*//**
		 * 14.7.8 快递员中查当日订单量最少的
		 * @param expMap
		 * @param dispatchOrderEntity
		 * @param orderPdaReturnRecordEntity
		 * @param autoCourierList
		 * @return
	*//*
	private Map<String, Object> minDriverCode(Map<String, Object> expMap,DispatchOrderEntity dispatchOrderEntity,OrderPdaReturnRecordEntity orderPdaReturnRecordEntity,List<CourierScheduleEntity> autoCourierList){
		List<String> autoDriverCodes = new ArrayList<String>();
		for(CourierScheduleEntity courier : autoCourierList){
			// 是否存在退回记录
			//14.7.2 未有存在退回记录
			String empCode = courier.getCourierCode();
			// 设置退回记录 快递员
			orderPdaReturnRecordEntity.setCreateCode(empCode);
			Long count = isExistsPdareturn(orderPdaReturnRecordEntity);
			// 不存在退回记录
			if(null == count
					|| count == 0){
				//14.7.30 根据快递员工号，判断是否暂停PDA
				ExpressWorkerStatusEntity workerEntity = expressWorkerStatusService
						.queryOneByEmpcode(empCode);
				// 可用人员不为空，且为开启状态  
				if (null != workerEntity
						&& ExpressWorkerStatusConstants.OPEN_STATUS
								.equals(workerEntity
										.getWorkStatus())) {
					autoDriverCodes.add(empCode);
				}else{
					dispatchOrderEntity.appendLog(empCode + "暂停");
				}
			}else{
				dispatchOrderEntity.appendLog(empCode + "有退回");
			}
		}
		//14.7.14 gcl AUTO-157  无可用快递员直接返回null
		if(autoDriverCodes.size()<=0){
			return null;
		}
		// 每日报表中取当日收取最少的信息 并且可用的
//		String minDriverCode = orderReportService.queryMinReceiveOrderDriver(autoDriverCodes);
		//14.7.30 gcl AUTO-212 优化
		String minDriverCode = regionCourierReportService.queryMinCourierByCommon(autoDriverCodes);
        // 判断是否存在
		if(StringUtils.isNotEmpty(minDriverCode)){
			// 循环出快递员
			for(CourierScheduleEntity courier : autoCourierList){
				//如果找到对应最少快递员编码
				if(StringUtils.equals(minDriverCode, courier.getCourierCode())){
					expMap = new HashMap<String, Object>();
					expMap.put("driverCode",
							courier.getCourierCode());
					expMap.put("driverName",
							courier.getCourierName());
					expMap.put("driverPhone",
							courier.getCourierPhone());
					expMap.put("vehicleNo",
							courier.getVehicleNo());
					expMap.put("pickupRegionCode",
							dispatchOrderEntity.getSmallZoneCodeSuggested());
					expMap.put("pickupRegionName",
							dispatchOrderEntity.getSmallZoneNameSuggested());
					break;
				}
			}
		}
		return expMap;
	}
	*//**
	 * @Description:14.7.8 根据解析出的收派小区和楼层，根据快递区域日常排班表内的信息匹配对应定区快递员
	 * @param expMap
	 * @param scheduleList
	 * @param dispatchOrderEntity
	 * @param orderPdaReturnRecordEntity
	 * @return
	 *//*
	private Map<String, Object> getexpMapByFloor(Map<String, Object> expMap,List<CourierScheduleEntity> scheduleList,DispatchOrderEntity dispatchOrderEntity,OrderPdaReturnRecordEntity orderPdaReturnRecordEntity){
		if (CollectionUtils.isNotEmpty(scheduleList)) {
			for (CourierScheduleEntity courier : scheduleList) {
				// 判断是否是定区快递员
				if(DispatchOrderStatusConstants.COURIER_NATURE_FIXED.equals(courier.getCourierNature())){
					// 获得快递员工号
					String empCode = courier.getCourierCode();
					// 根据快递员工号，判断是否暂停PDA
					ExpressWorkerStatusEntity workerEntity = expressWorkerStatusService
						.queryOneByEmpcode(empCode);
					// 可用人员不为空，且为开启状态
					if (null != workerEntity
						&& ExpressWorkerStatusConstants.OPEN_STATUS
								.equals(workerEntity
										.getWorkStatus())) {
						// 设置退回记录 快递员
						orderPdaReturnRecordEntity.setCreateCode(empCode);
						// 是否存在退回记录
						Long count = isExistsPdareturn(orderPdaReturnRecordEntity);
						// 不存在退回记录
						if(null == count
							|| count == 0){
							expMap = new HashMap<String, Object>();
							expMap.put("driverCode",
								courier.getCourierCode());
							expMap.put("driverName",
								courier.getCourierName());
							expMap.put("driverPhone",
								courier.getCourierPhone());
							expMap.put("vehicleNo",
								courier.getVehicleNo());
							expMap.put("pickupRegionCode",
								dispatchOrderEntity.getSmallZoneCodeSuggested());
							expMap.put("pickupRegionName",
								dispatchOrderEntity.getSmallZoneNameSuggested());
							break;
						}else{
							dispatchOrderEntity.appendLog(empCode+"有退回");
						}
					}else{
						dispatchOrderEntity.appendLog(empCode+"暂停");
					}
				}
			}
		}else{
			String reason = dispatchOrderEntity.getOrderNo()+"快递收派小区、解析的楼层号获得对应的快递员排班信息为空";	
			addExceptionLog(dispatchOrderEntity,"MATCH_EXPRESS_SMALLZONE_FLOOR",reason);
		}
		return expMap;
	}
	
	*//**
	 * 
	 * @Title: updateExpOrderPreprocess
	 * @Description: 更新预处理意见
	 * @param @param dispatchOrderEntity
	 * @param @param result 设定文件
	 * @return void 返回类型
	 * @throws
	 *//*
	private void updateExpOrderPreprocess(
			DispatchOrderEntity dispatchOrderEntity, Map<String, Object> result) {
		if (result != null) {
			dispatchOrderEntity.setOrderStatus("");
			// 获得GIS坐标id
			String scopeCoordinatesId = (String) result
					.get("scopeCoordinatesId");
			// 判断 scopeCoordinatesId为空
			if (scopeCoordinatesId == null) {
				// 不处理
				addExceptionLog(dispatchOrderEntity,"scopeCoordinatesId_IS_NULL","调用GIS接口GIS坐标ID为空");
				return;
			}
			// 是否已入库标记
			String isCollect = (String) result.get("isCollect");
			if (StringUtils.isNotEmpty(isCollect)
					&& FossConstants.YES.equals(isCollect)) {
				dispatchOrderEntity.setIsCollect(isCollect);
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
//				 订单自动分配成功数
			businessMonitorService
			.counter(
					BusinessMonitorIndicator.ORDER_AUTO_ASSIGN_SUCCESS_COUNT,
					new CurrentInfo(user, dept));
		} else {
			// GIS返回异常
			String exceptionType = "GIS_ID_IS_NULL";
			String reason = "GIS返回的ID为空";
			addExceptionLog(dispatchOrderEntity,exceptionType,reason);
		}

	}
	*//**
	 * 
	 * @Title: getOrderHandleDto
	 * @Description: 重新组装成待受理的订单
	 * @param @param dispatchOrderEntity
	 * @param @param expMap
	 * @param @return 设定文件
	 * @return OrderHandleDto 返回类型
	 * @throws
	 *//*
	private OrderHandleDto getOrderHandleDto(
			DispatchOrderEntity dispatchOrderEntity, Map<String, Object> expMap) {
		if (null == expMap || expMap.size() <= 0) {
			return null;
		}
		OrderHandleDto orderHandleDto = new OrderHandleDto();
		orderHandleDto.setId(dispatchOrderEntity.getId());
		orderHandleDto.setOrderNo(dispatchOrderEntity.getOrderNo());
		orderHandleDto.setDeliveryCustomerCode(dispatchOrderEntity.getDeliveryCustomerCode());
		orderHandleDto.setOrderType(dispatchOrderEntity.getOrderType());
		orderHandleDto.setOriginOrderStatus(dispatchOrderEntity
				.getOrderStatus());
		orderHandleDto.setIsCustomer(dispatchOrderEntity.getIsCustomer());
		orderHandleDto.setIsPda(dispatchOrderEntity.getIsPda());
		orderHandleDto.setIsSms(dispatchOrderEntity.getIsSms());

		// 匹配司机，自动匹配规则
		String driverCode = (String) expMap.get("driverCode");
		String driverName = (String) expMap.get("driverName");
		String vehicleNo = (String) expMap.get("vehicleNo");
		String driverMobile = (String) expMap.get("driverPhone");
		String pickupRegionCode = (String) expMap.get("pickupRegionCode");
		String pickupRegionName = (String) expMap.get("pickupRegionName");
		//14.8.15 gcl AUTO-230
		String isCollect = (String) expMap.get("isCollect");
		if(StringUtils.isNotEmpty(isCollect)&& FossConstants.YES.equals(isCollect)){
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
		orderHandleDto.setDeliveryCustomerMobile(dispatchOrderEntity.getMobile());
		orderHandleDto.setCustomerName(dispatchOrderEntity.getCustomerName());
		StringBuffer pickupAddress = new StringBuffer();
		pickupAddress.append(dispatchOrderEntity.getPickupProvince());
		pickupAddress.append(dispatchOrderEntity.getPickupCity());
		pickupAddress.append(dispatchOrderEntity.getPickupCounty());
		pickupAddress.append(dispatchOrderEntity.getPickupElseAddress());
		orderHandleDto.setPickupCity(dispatchOrderEntity.getPickupCity());
		orderHandleDto.setPickupAddress(pickupAddress.toString());
		orderHandleDto.setSalesDepartmentCode(dispatchOrderEntity
				.getSalesDepartmentCode());
		orderHandleDto.setSalesDepartmentName(dispatchOrderEntity
				.getSalesDepartmentName());
		orderHandleDto.setOrderNotes(dispatchOrderEntity.getOrderNotes());
		// 接货时间
		StringBuffer pickupTime = new StringBuffer();
		Date earliestPickupTime = dispatchOrderEntity.getEarliestPickupTime();
		Date latestPickupTime = dispatchOrderEntity.getLatestPickupTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(null != earliestPickupTime
				&& null != latestPickupTime){
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
		if(owsPickupTime.length()>0) {
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
		orderHandleDto.setOperateOrgName("系统自动调度");
		return orderHandleDto;
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
	 * 
	 * @Title: addExceptionLog 
	 * @Description: 自动处理异常日志新增
	 * @param @param dispatchOrderEntity
	 * @param @param excptionType
	 * @param @param reason    设定文件 
	 * @return void    返回类型 
	 * @throws
	 *//*
	private void addExceptionLog(DispatchOrderEntity dispatchOrderEntity,
		String excptionType,String reason){
		// 如果待分配的快递员为空，则新增日常日志，同时转为人工处理
		OrderAutoExceptionLogEntity orderAutoExceptionLogEntity = new OrderAutoExceptionLogEntity();
		orderAutoExceptionLogEntity.setId(UUIDUtils
				.getUUID());
		orderAutoExceptionLogEntity
				.setDispatchOrderId(dispatchOrderEntity
						.getId());
		orderAutoExceptionLogEntity
				.setOrderNo(dispatchOrderEntity
						.getOrderNo());
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
	 * 线程池满异常处理
	 * @author YANGBIN
	 * @date 2014-4-29 上午10:05:38
	 *//*
	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoExpressHandleService【线程池满异常处理开始。。。。。。】");
		//zxy 20140723 AUTO-197 start 修改:当线程满了改成N/A
		DispatchOrderEntity dispatchOrderEntity = (DispatchOrderEntity) obj;		
		if(null != dispatchOrderEntity){
			//新增线程异常日志
			OrderThreadPoollogEntity record = new OrderThreadPoollogEntity();
			record.setId(UUIDUtils.getUUID());
			record.setServiceName("AutoExpressHandService");
			record.setExceptionData(dispatchOrderEntity.getId()+"-"+dispatchOrderEntity.getOrderNo());
			record.setExceptionPoolname("");
			record.setFailTimes(new BigDecimal(1));
			record.setCreateDate(new Date());
			record.setExceptionMsg("线程池满异常");
			orderThreadPoollogDao.insert(record);
			//更新预处理订单表查询状态，下次执行线程查询时，可查询
			DispatchOrderChangeEntity entity = new DispatchOrderChangeEntity();
			entity.setChangeId(dispatchOrderEntity.getId());
			entity.setJobId(DispatchOrderStatusConstants.CHANGE_ORDER_NO_QUERY);
			dispatchOrderChangeEntityDao.updateChangebyOrderId(entity);
//			List<DispatchOrderChangeEntity> changeList = new ArrayList<DispatchOrderChangeEntity>();
//			DispatchOrderChangeEntity entity = new DispatchOrderChangeEntity();
//			entity.setChangeId(dispatchOrderEntity.getId());
//			changeList.add(entity);
//			//更新预处理订单表查询状态，下次执行线程查询时，可查询
//			if(!CollectionUtils.isEmpty(changeList)
//					&& changeList.size() > 0){
//				dispatchOrderChangeEntityDao.batchUpdateChange(changeList);
//			}
			LOGGER.info("AutoExpressHandleService【线程池满异常处理结束。。。。。。】");
		}
		//zxy 20140723 AUTO-197 end 修改:
		
	}
	
	*//**
	 * change状态设置成B	
	 * @param dispatchOrderEntity
	 * @add zxy 20140716 AUTO-165
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
	 * 设置最大线程数
	 * @author YANGBIN
	 * @date 2014-4-29 上午10:05:38
	 *//*
	@Override
	public int getActiveThreads() {
		return 10;
	}
	
	public void setAutoExpressAcceptService(
			IAutoExpressAcceptService autoExpressAcceptService) {
		this.autoExpressAcceptService = autoExpressAcceptService;
	}

	public void setOrderThreadPoollogDao(
			IOrderThreadPoollogDao orderThreadPoollogDao) {
		this.orderThreadPoollogDao = orderThreadPoollogDao;
	}

	public void setDispatchOrderChangeEntityDao(
			IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
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

	public void setOrderAutoExceptionLogService(
			IOrderAutoExceptionLogService orderAutoExceptionLogService) {
		this.orderAutoExceptionLogService = orderAutoExceptionLogService;
	}
	public void setPropertyFactory(PropertyFactory propertyFactory) {
		this.propertyFactory = propertyFactory;
	}
	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}
//	@Override
//	public void process(OrderHandleDto orderHandleDto) {
//		pushThreadsPool(orderHandleDto);
//	}
	public void process(DispatchOrderEntity dispatchOrderEntity) {
		pushThreadsPool(dispatchOrderEntity);
	}

	public void setRegionCourierReportService(
			IRegionCourierReportService regionCourierReportService) {
		this.regionCourierReportService = regionCourierReportService;
	}

	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
}
*/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAutoScheduleManageService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AutoScheduleManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoPdaReturnRecordService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderPdaReturnRecordService;
import com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderActionHistoryEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaReturnDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.order.api.shared.util.PropertyFactory;
import com.deppon.foss.module.pickup.order.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.util.ProductCodeUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.system.utility.StringUtil;

public class AutoPdaReturnRecordService implements IAutoPdaReturnRecordService{
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoDispathchOrderService.class);	
	/**
	 * 派车记录DAO
	 */
	private IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao;
	/**
	 * 车辆管理Service
	 */
	private IVehicleManageService vehicleManageService;
	/**
	 * 订单操作历史DAO
	 */
	private IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao;
	/**
	 * CRM订单查询服务
	 */
	private ICrmOrderService crmOrderService;
	// pda签到dao
	private IPdaSignEntityDao pdaSignEntityDao;
	//综合 定人定区service接口
	private IRegionalVehicleService regionalVehicleService;
	//中转排班
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	/**
	 * 业务异常日志
	 */
	private IOrderAutoExceptionLogService orderAutoExceptionLogService;
	/**
	 * 调度订单DAO
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	// 配置
	private PropertyFactory propertyFactory;
	//快递员工作状态Dao
	private IExpressWorkerStatusDao expressWorkerStatusDao;
	private IOrderPdaReturnRecordService orderPdaReturnRecordService;
	private IConfigurationParamsService configurationParamsService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IAutoScheduleManageService autoScheduleManageService;
	private IMotorcadeService motorcadeService;
	/**
	 * PDA退回重新匹配
	 * @param entity
	 * @return
	 * 	返回值为
	 * 1、退单原因为“客户取消或重复下单、客户要求延迟揽货、联系不上客户、接货地址不准/不详、违禁品”的订单将回退CRM系统，由营业部处理；
	 * 2、退单原因类型为“车辆空间不足”或“来不及接货”的，将进入系统二次车辆匹配环节：上一级车退单，系统自动将该单按车辆匹配顺序分给下一级车，下一级车不存在时，由调度人工处理；若存在下一级车辆且该订单继续是上述原因退回，则依次类推至最后一级车辆，最后一级车辆非规则1原因的退单或不存在最后一级车，则由调度进行人工处理；
	 * 3、除以上描述的退单原因的订单，都将由调度手工处理
	 * @update zxy 20140716 AUTO-175 日志优化
	 */
	@Override
	public int pdaReturnProcess(String orderNo, String returnType,PdaReturnDto pdaReturnDto){
		//根据订单号找到车牌号
		DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
		dto.setOrderNo(orderNo);	
		DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao.queryOrdersByOrderNo(dto);
		//14.8.11 gcl AUTO-221 零担 退回后记录退回日志
		addReturnRecord(pdaReturnDto,dispatchOrder);
		//cdl 1017 门对门、场对场的退回订单使用人工处理 -----begin---
		if(dispatchOrder.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD)||dispatchOrder.getProductCode().equals(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR)){
			return 1;
		}
		//cdl 1017 -----end---
		//退单原因为“客户取消或重复下单、客户要求延迟揽货、联系不上客户、接货地址不准/不详、违禁品”的订单将回退CRM系统，由营业部处理；
		/*if(StringUtils.equals(DispatchOrderStatusConstants.REJECT_CANCEL_REPEAT_ORDERS, returnType)
			||StringUtils.equals(DispatchOrderStatusConstants.REJECT_REQUIRE_DELAY, returnType)
			||StringUtils.equals(DispatchOrderStatusConstants.REJECT_CONTACT_NO_CUSTOMER, returnType)
			||StringUtils.equals(DispatchOrderStatusConstants.REJECT_PICKUP_ADDRESS_ERROR, returnType)){
			return 1;
		//退单原因类型为“车辆空间不足”或“来不及接货”的，将进入系统二次车辆匹配环节：上一级车退单，系统自动将该单按车辆匹配顺序分给下一级车，下一级车不存在时，由调度人工处理；若存在下一级车辆且该订单继续是上述原因退回，则依次类推至最后一级车辆，最后一级车辆非规则1原因的退单或不存在最后一级车，则由调度进行人工处理	
		}else*/ 
		//零担集中接送货-退单状态优化需求 author-231438
		//退单原因为“客户取消或重复下单、客户要求延迟揽货、联系不上客户、接货地址不准/不详、违禁品”的订单将回退调度
		if(StringUtils.equals(DispatchOrderStatusConstants.REJECT_VEHICLE_NO_SPACE, returnType)	//zxy 20140716 AUTO-175 修改:类型常量使用不对135行
				||StringUtils.equals(DispatchOrderStatusConstants.REJECT_NO_TIME_PICKUP, returnType)){
				//非空间不足/来不及接货
				return returnOrder(dispatchOrder);
			}
		if(!DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(dispatchOrder.getOrderType())
				||StringUtils.isEmpty(dispatchOrder.getVehicleNo())){//非接货类型,或者车牌为空
			return SettlementReportNumber.THREE;
		}
	
		return SettlementReportNumber.THREE;
	};
	/**
	 * 根据车牌号查询车辆工作状态 AUTO-170
	 * gcl 14.7.17
	 * @return
	 */
	private Long vehicleNoOpenStatus(String vehicleNo){
	   Long l=1l;
	   if(vehicleNo!=null&&StringUtil.isNotEmpty(vehicleNo)){
		   ExpressWorkerStatusEntity worker=new ExpressWorkerStatusEntity();
		   worker.setActive(FossConstants.ACTIVE);
		   worker.setBusinessArea(ExpressWorkerStatusConstants.ORDER_BUSINESS);
		   worker.setVehicleNo(vehicleNo);
		   worker.setWorkStatus(ExpressWorkerStatusConstants.STOP_STATUS);//14.7.24 gcl AUTO-200
		   l = expressWorkerStatusDao.selectOneByVehicleNo(worker);// l>0 为暂停
		   if( l > 0 ){
			   l=-1l;
		   }else{
			   l=1l;
		   }
	   }
	   return l;
	}
	//进行自动调度前 先判断开关状态 14.8.23 gcl
	private boolean autoScheduleIsOpen(DispatchOrderEntity dispatchOrder){
		//从配置参数中读取自动调度开关值
		String flag = FossConstants.NO;//默认开关关闭
		String[] codes = new String[1];
		codes[0]=ConfigurationParamsConstants.PKP_AUTO_SCHEDULE_MANAGE;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(null!=configurationParamsEntitys&&configurationParamsEntitys.size()>0){
				flag = configurationParamsEntitys.get(0).getConfValue();//开关是否关闭
		}
		if(StringUtils.equals(FossConstants.YES, flag)){//总开关开启
			//调用综合车队开关 若车队开关开 则执行
			String deptCode = dispatchOrder.getFleetCode();//获取的部门code
			OrgAdministrativeInfoEntity  motorcadeOrg = null;//有开关车队	
			//车队
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(deptCode);
			if(null==orgInfo){//不存在部门
				return false;//不处理
			}
			//获取开关
			AutoScheduleManageEntity autoScheduleManageEntity= autoScheduleManageService.queryAutoScheduleManageBydeptCode(deptCode,FossConstants.YES);
			if(null!=autoScheduleManageEntity&&matchAutoSchedule(dispatchOrder,autoScheduleManageEntity)){//存在开关并且在开关开启范围内
				return true;
			}else if(StringUtils.equals(FossConstants.NO,orgInfo.getTransDepartment())){//不是车队，查询所属车队
				motorcadeOrg= orgAdministrativeInfoComplexService.getOrgAdministrativeInfoMotorcadeByCode(deptCode);//车队
			}else{
				motorcadeOrg=orgInfo;
			}
			if(null==motorcadeOrg){
				return false;//不处理
			}else{
				deptCode= motorcadeOrg.getCode();//车队CODE
			}
			//车队组织对应的车队实体
			MotorcadeEntity motorcadeEntity= motorcadeService.queryMotorcadeByCodeClean(deptCode);
			String topFleetCode = null;//顶级车队编码
			//zxy 20140723 AUTO-197 修改:增加motorcadeEntity空检验
			if(motorcadeEntity != null && FossConstants.YES.equals(motorcadeEntity.getIsTopFleet())){
				topFleetCode = deptCode;
			}else {
				OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(deptCode);//顶级车队
				if(null!=topFleet){
					topFleetCode = topFleet.getCode();
				}
			}
			if(null==topFleetCode){ //不存在顶级车队，说明没有对应车队
				return false;
			}
			while (true){//从下向上查询有开关的车队
				if(StringUtils.isEmpty(deptCode)){//不存在车队		
					return false;
				}
				//获取车队锁
				autoScheduleManageEntity= autoScheduleManageService.queryAutoScheduleManageBydeptCode(deptCode,FossConstants.YES);
				if(null!=autoScheduleManageEntity&&matchAutoSchedule(dispatchOrder,autoScheduleManageEntity)){//存在锁并且在开关开启范围内
					return true;
				}else if(deptCode.equals(topFleetCode)){//是顶级车队，停止向上查找
					return false;	
				}else{									
					//查询上级组织
					List<OrgAdministrativeInfoEntity> entitys = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(deptCode,true);
					if(null!=entitys&&entitys.size()>0){//存在上级部门
						deptCode = entitys.get(0).getCode();
					}else{//没有上级部门
						return false;
					}
				}
			}
		}else{
			return false;
		}
	}
	private int returnOrder(DispatchOrderEntity dispatchOrder) {
		if(!autoScheduleIsOpen(dispatchOrder)){
			insertLog(dispatchOrder.getId(),dispatchOrder.getOrderNo(),"MATCH_SCHEDULE_pdaReturnProcess","退回时查不到有效开关，转人工" );
			return SettlementReportNumber.THREE;
		}
		// 调用GIS接口返回接货小区编码
		// 获得GIS坐标id
		String scopeCoordinatesId = null;
		Map<String, String> map = formatToMap(dispatchOrder);
		Map<String, Object> result = null;
		// 调用GIS接口获得坐标id
		try {
			LOGGER.info("AutoOrderHandleService-pdaReturnProcess-gis-begin");
			result = GisInterfaceUtil.callGisInterface(propertyFactory.getGisUrl(), map);
			LOGGER.info("AutoOrderHandleService-pdaReturnProcess-gis-end");
			if (result != null&&result.size()>0) {
				// 获得GIS坐标id
				scopeCoordinatesId = (String) result.get("scopeCoordinatesId");
				LOGGER.info("GIS坐标ID:" + scopeCoordinatesId);
				dispatchOrder.appendLog("GIS坐标ID:" + scopeCoordinatesId);
			}else{
				insertLog(dispatchOrder.getId(),dispatchOrder.getOrderNo(),"GISresultNULL","GISresult为空" );
				return SettlementReportNumber.THREE;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			dispatchOrder.appendException("GISException");
			insertLog(dispatchOrder.getId(),dispatchOrder.getOrderNo(),"GISException",dispatchOrder.getSbLog().toString());
			return SettlementReportNumber.THREE;
		}
		if(StringUtils.isEmpty(scopeCoordinatesId)){
			dispatchOrder.appendException("GISID为空");
			insertLog(dispatchOrder.getId(),dispatchOrder.getOrderNo(),"GISIDNULL",dispatchOrder.getSbLog().toString());
			return SettlementReportNumber.THREE;
		}		
		// 调用综合接口获得对应的车辆 调用原始的定区车辆接口（判断一级车、二级车、定区车）
		RegionVehicleInfoDto regionVehicleInfoDto = regionalVehicleService.querySmallZoneInfoByGisIdForNew(scopeCoordinatesId);
		if(null==regionVehicleInfoDto){
			dispatchOrder.appendException("坐标获取车辆为空");
			insertLog(dispatchOrder.getId(),dispatchOrder.getOrderNo(),"VehicleByGISNULL",dispatchOrder.getSbLog().toString());
			return SettlementReportNumber.THREE;
		}
		if(dispatchOrder.getVehicleNo().equals(regionVehicleInfoDto.getFristRegionVehicleNo())){//退回一级车
			String secondRegionVehicleNo = regionVehicleInfoDto.getSecondRegionVehicleNo();
			OwnTruckDto ownTruckDto1=null;
			Long l=0L;
			//14.8.21 gcl NO-796
			if(StringUtils.isNotEmpty(secondRegionVehicleNo)){
				dispatchOrder.setVehicleNo(secondRegionVehicleNo);
				//14.8.1 gcl AUTO-218
				dispatchOrder.appendLog("退回一级车：" + regionVehicleInfoDto.getFristRegionVehicleNo()
						+ ",二级车:" + secondRegionVehicleNo
						+ ",机动车:" + regionVehicleInfoDto.getMotorVehicleNo());
				l=vehicleNoOpenStatus(secondRegionVehicleNo);
				if(l<=0){
					dispatchOrder.appendLog("二级车暂停");
				}else{
					ownTruckDto1 = getDriver(secondRegionVehicleNo);
				}
			}
			if(ownTruckDto1 != null){
				dispatchOrder.appendLog("二级车司机:" + ownTruckDto1.getDriverCode());
				//车牌
				dispatchOrder.setVehicleNoSuggested(regionVehicleInfoDto.getSecondRegionVehicleNo());
				dispatchOrder.setVehicleNo(regionVehicleInfoDto.getSecondRegionVehicleNo());
				// 司机姓名
				dispatchOrder.setDriverNameSuggested(ownTruckDto1.getDriverName());
				dispatchOrder.setDriverName(ownTruckDto1.getDriverName());
				// 司机编码
				dispatchOrder.setDriverCodeSuggested(ownTruckDto1.getDriverCode());
				dispatchOrder.setDriverCode(ownTruckDto1.getDriverCode());
			}else{
				insertLog(dispatchOrder.getId(),dispatchOrder.getOrderNo(),"SecondRegionVeNoNULL","二级车信息为空" );
				String motorVehicleNo = regionVehicleInfoDto.getMotorVehicleNo();
				OwnTruckDto ownTruckDto2=null;
				//14.8.21 gcl NO-796
				if(StringUtils.isNotEmpty(motorVehicleNo)){
					dispatchOrder.setVehicleNo(motorVehicleNo);
					l=vehicleNoOpenStatus(motorVehicleNo);
					if(l<=0){
						dispatchOrder.appendLog("定区车暂停");
					}else{
						ownTruckDto2 = getDriver(motorVehicleNo);
					}
				}
				if(ownTruckDto2 != null){
					dispatchOrder.appendLog("机动车司机:" + ownTruckDto2.getDriverCode());
					//车牌
					dispatchOrder.setVehicleNoSuggested(regionVehicleInfoDto.getMotorVehicleNo());
					dispatchOrder.setVehicleNo(regionVehicleInfoDto.getMotorVehicleNo());
					// 司机姓名
					dispatchOrder.setDriverNameSuggested(ownTruckDto2.getDriverName());
					dispatchOrder.setDriverName(ownTruckDto2.getDriverName());
					// 司机编码
					dispatchOrder.setDriverCodeSuggested(ownTruckDto2.getDriverCode());
					dispatchOrder.setDriverCode(ownTruckDto2.getDriverCode());
				}else{
					dispatchOrder.appendException("定区车信息为空");
					insertLog(dispatchOrder.getId(),dispatchOrder.getOrderNo(),"SecondRegionVeNoNULL",dispatchOrder.getSbLog().toString());
					return SettlementReportNumber.THREE;
				}
			}
			returnAutoMatch(dispatchOrder);
		}else if(dispatchOrder.getVehicleNo().equals(regionVehicleInfoDto.getSecondRegionVehicleNo())){//退回二级车
			dispatchOrder.appendLog("退回二级车：" + regionVehicleInfoDto.getSecondRegionVehicleNo()
					+ ",机动车:" + regionVehicleInfoDto.getMotorVehicleNo());
			String motorVehicleNo = regionVehicleInfoDto.getMotorVehicleNo();
			OwnTruckDto ownTruckDto2=null;
			//14.8.21 gcl NO-796
			if(StringUtils.isNotEmpty(motorVehicleNo)){
				dispatchOrder.setVehicleNo(motorVehicleNo);
				Long l=vehicleNoOpenStatus(motorVehicleNo);
				if(l<=0){
					dispatchOrder.appendLog("定区车暂停");
				}else{
					ownTruckDto2 = getDriver(motorVehicleNo);
				}
			}
			if(ownTruckDto2 != null){
				dispatchOrder.appendLog("机动车司机:" + ownTruckDto2.getDriverCode());
				//车牌
				dispatchOrder.setVehicleNoSuggested(regionVehicleInfoDto.getMotorVehicleNo());
				dispatchOrder.setVehicleNo(regionVehicleInfoDto.getMotorVehicleNo());
				// 司机姓名
				dispatchOrder.setDriverNameSuggested(ownTruckDto2.getDriverName());
				dispatchOrder.setDriverName(ownTruckDto2.getDriverName());
				// 司机编码
				dispatchOrder.setDriverCodeSuggested(ownTruckDto2.getDriverCode());
				dispatchOrder.setDriverCode(ownTruckDto2.getDriverCode());
			}else{
				dispatchOrder.appendException("定区车信息为空");
				insertLog(dispatchOrder.getId(),dispatchOrder.getOrderNo(),"SecondRegionVeNoNULL",dispatchOrder.getSbLog().toString());
				return SettlementReportNumber.THREE;
			}
			returnAutoMatch(dispatchOrder);
		}else{
			return SettlementReportNumber.THREE;
		}
		return 2;
	}
	
	/**
	 * 将订单实体转为Map.
	 * 
	 * @param dispatchOrderEntity the dispatchOrderEntity
	 * @return the map
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午7:54:16
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
		// 其他详细地址
		map.put("otherAddress", dispatchOrderEntity.getPickupElseAddress());
		// 手机
		map.put("phone", dispatchOrderEntity.getMobile());
		// 电话
		map.put("tel", dispatchOrderEntity.getTel());
		// 接货区域
		map.put("matchtype", DictionaryValueConstants.REGION_TYPE_PK);
		// 自动调度类型
		map.put("transportway", "motor_self");
		return map;
	}
	
	/**
	 * 
	 * @Title: addReturnRecord 
	 * @Description: 生成退回记录
	 * @param @param returnDto
	 * @param @param dispatchOrderEntity    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addReturnRecord(PdaReturnDto returnDto,DispatchOrderEntity dispatchOrderEntity){
		OrderPdaReturnRecordEntity orderPdaReturnRecordEntity = new OrderPdaReturnRecordEntity();
		orderPdaReturnRecordEntity.setDispatchOrderId(dispatchOrderEntity.getId());
		orderPdaReturnRecordEntity.setOrderNo(dispatchOrderEntity.getOrderNo());
		orderPdaReturnRecordEntity.setOperateType("ORDER_RETURN");
		orderPdaReturnRecordEntity.setOrderStatus(returnDto.getOrderStatus());
		orderPdaReturnRecordEntity.setReturnType(returnDto.getReturnReason());
		orderPdaReturnRecordEntity.setReturnRemark(returnDto.getRemark());
		StringBuffer pickupAddress = new StringBuffer();
		pickupAddress.append(dispatchOrderEntity.getPickupProvince());
		pickupAddress.append(dispatchOrderEntity.getPickupCity());
		pickupAddress.append(dispatchOrderEntity.getPickupCounty());
		pickupAddress.append(dispatchOrderEntity.getPickupElseAddress());
		orderPdaReturnRecordEntity.setPickupAddress(pickupAddress.toString());
		
		orderPdaReturnRecordEntity.setOperateTime(new Date());
		orderPdaReturnRecordEntity.setId(UUIDUtils.getUUID());
		orderPdaReturnRecordEntity.setCreateDate(new Date());
		orderPdaReturnRecordEntity.setCreateCode(returnDto.getDriverCode());
		orderPdaReturnRecordEntity.setCreateName(returnDto.getDriverName());
		orderPdaReturnRecordEntity.setProductCode(dispatchOrderEntity.getProductCode());
		orderPdaReturnRecordService.insertPdaReturnRecord(orderPdaReturnRecordEntity);
	}
	/**
	 * 业务日志
	 * @param dispatchOrderId
	 * @param dispatchOrderNo
	 * @param ExceptionType
	 * @param exceptionReason
	 */
	private void insertLog(String dispatchOrderId,String dispatchOrderNo,String ExceptionType,String exceptionReason ){		
		try {
			OrderAutoExceptionLogEntity orderAutoExceptionLogEntity = new OrderAutoExceptionLogEntity();
			orderAutoExceptionLogEntity.setId(UUIDUtils.getUUID());
			if(StringUtils.isEmpty(dispatchOrderId)){
				orderAutoExceptionLogEntity.setDispatchOrderId(UUIDUtils.getUUID());
			}else{				
				orderAutoExceptionLogEntity.setDispatchOrderId(dispatchOrderId);
			}
			if(StringUtils.isEmpty(dispatchOrderNo)){
				orderAutoExceptionLogEntity.setOrderNo(UUIDUtils.getUUID());
			}else{				
				orderAutoExceptionLogEntity.setOrderNo(dispatchOrderNo);
			}
			orderAutoExceptionLogEntity.setExceptionType(ExceptionType);
			orderAutoExceptionLogEntity.setExceptionReason(exceptionReason);
			orderAutoExceptionLogEntity.setCreateTime(new Date());
			orderAutoExceptionLogService.insertAutoOrderExceptionLog(orderAutoExceptionLogEntity);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * 找寻司机
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-4 下午5:41:30
	 */
	private OwnTruckDto getDriver(String vehicleNo){
		//是否存在绑定关系
		PdaSignEntity condition = new PdaSignEntity();
		OwnTruckDto ownTruckDto = new OwnTruckDto();
		condition.setVehicleNo(vehicleNo);
		condition.setStatus(PdaSignStatusConstants.BUNDLE);
		// 查询签到获得对应司机
		PdaSignEntity pdaSignEntity = pdaSignEntityDao.querySignByVehicleNo(condition);
		//zxy 20140710 AUTO-135 修改:增加司机空判断
		if(pdaSignEntity!= null && StringUtils.isNotBlank(pdaSignEntity.getDriverCode())){
			ownTruckDto.setDriverCode(pdaSignEntity.getDriverCode());
			ownTruckDto.setDriverName(pdaSignEntity.getDriverName());
			return ownTruckDto;
		}else {
			//根据司机排班信息拿到司机 
			TruckSchedulingZoneEntity truckSchedulingZoneEntity2 = truckSchedulingTaskService.queryDriverByVehicle(vehicleNo, new Date());
			if(null!=truckSchedulingZoneEntity2){
				ownTruckDto.setDriverCode(truckSchedulingZoneEntity2.getDriverCode());
				ownTruckDto.setDriverName(truckSchedulingZoneEntity2.getDriverName());
				return ownTruckDto;
			}else {
				return null;
			}
		}
	}
	
	/**
	 * 封装自动匹配
	 * @param dispatchOrderEntity
	 */
	
	private void returnAutoMatch(DispatchOrderEntity dispatchOrderEntity){
		//判断接货订单在CRM中是否存在
		/*if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(dispatchOrderEntity.getOrderType())
				&& crmOrderService.searchOrder(dispatchOrderEntity.getOrderNo()) == false) 
		{
			dispatchOrderEntity.appendException("订单在CRM中不存在");
			insertLog(dispatchOrderEntity.getId(),dispatchOrderEntity.getOrderNo(),"orderNotInCRM",dispatchOrderEntity.getSbLog().toString());
			throw new DispatchException("订单"+ dispatchOrderEntity.getOrderNo() +"在CRM中不存在");
		}*/
		OrderHandleDto orderHandleDto = new OrderHandleDto();
		orderHandleDto.setId(dispatchOrderEntity.getId());
		// 设置状态为已派车
		orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
		// 发送中
		orderHandleDto.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDING);
		//车牌
		orderHandleDto.setVehicleNo(dispatchOrderEntity.getVehicleNo());
		//司机code
		orderHandleDto.setDriverCode(dispatchOrderEntity.getDriverCode());
		//司机姓名
		orderHandleDto.setDriverName(dispatchOrderEntity.getDriverName());
		// 操作时间
		orderHandleDto.setOperateTime(new Date());
		// 操作人
		orderHandleDto.setOperator("System");
		// 操作人编码
		orderHandleDto.setOperatorCode("000000");
		// 操作部门编码
		orderHandleDto.setOperateOrgCode(dispatchOrderEntity.getFleetCode());
		//14.7.30 gcl AUTO-212 设置订单受理方式为自动
		orderHandleDto.setAcceptStatus(DispatchOrderStatusConstants.ORDER_ACCEPT_STATUS_SYS);
		//更新订单信息
		int count =  dispatchOrderEntityDao.updateByOrderHandle(orderHandleDto);
		if (count == 1) {
			// 添加订单操作历史
			addDispatchOrderActionHistory(orderHandleDto);
			// 添加派车记录
			addDispatchVehicleRecord(orderHandleDto);
			if (!ProductCodeUtils.isExpress(orderHandleDto.getProductCode())) {
				addVehicleWV(orderHandleDto);
			}
		}else{
			dispatchOrderEntity.appendException("订单在CRM中不存在");
			insertLog(dispatchOrderEntity.getId(),dispatchOrderEntity.getOrderNo(),"orderNotInCRM",dispatchOrderEntity.getSbLog().toString());
			throw new DispatchException("订单"+ dispatchOrderEntity.getOrderNo() +"在CRM中不存在");
		} 
	}
	

	/**
	 * 
	 * 增加未接票数.
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午3:39:35
	 */
	private void addVehicleWV(OrderHandleDto orderHandleDto) {
		VehicleActualSituationDto vehicleActualSituationDto = new VehicleActualSituationDto();
		// 车牌号
		vehicleActualSituationDto.setVehicleNo(orderHandleDto.getVehicleNo());
		// 未接票数
		vehicleActualSituationDto.setNonePickupGoodsQty(1);
		// 修改车载信息
		vehicleManageService.addVehicleWVByVehicleNo(vehicleActualSituationDto);
	}
	

	/**
	 * 
	 * 添加派车记录
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午3:31:56
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
		vehicleRecordEntity.setDeliverTime(orderHandleDto.getOperateTime());
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
	 * 添加订单操作历史
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午3:29:20
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
		dispatchOrderActionHistoryEntity.setOperateTime(orderHandleDto.getOperateTime());
		// 插入订单操作历史
		dispatchOrderActionHistoryEntityDao.addDispatchOrderActionHistory(dispatchOrderActionHistoryEntity);
	}
	/**
	 * 订单在开关时间范围内
	 * @param dispatchOrderEntity
	 * @param autoScheduleManageEntity
	 * @return
	 */
	private boolean matchAutoSchedule(DispatchOrderEntity dispatchOrderEntity,
			AutoScheduleManageEntity autoScheduleManageEntity) {
		//如果在开启范围加入线程池
		Date orderTime = dispatchOrderEntity.getOrderVehicleTime();
		if(null==orderTime){
			return false;
		}
		String startTimeStr = autoScheduleManageEntity.getStartTime();
		String endTimeStr = autoScheduleManageEntity.getEndTime();
        SimpleDateFormat hmsFmt  =   new  SimpleDateFormat( "HH:mm" ); 
        SimpleDateFormat dateFmt  =   new  SimpleDateFormat( "yyyy-MM-dd HH:mm" ); 
        String dateStr = "2014-01-01 ";
        Date startTime = null;
        Date endTime = null;
        try {
        	String orderTimeStr = hmsFmt.format( orderTime); 
			startTime = dateFmt.parse( dateStr+startTimeStr); //order时间
			endTime = dateFmt.parse( dateStr+endTimeStr);//开关时间
			orderTime = dateFmt.parse( dateStr+orderTimeStr);//开关时间
			if(orderTime.before(endTime)&&!orderTime.before(startTime)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return false;
	}
	public void setDispatchVehicleRecordEntityDao(
			IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao) {
		this.dispatchVehicleRecordEntityDao = dispatchVehicleRecordEntityDao;
	}

	public void setVehicleManageService(IVehicleManageService vehicleManageService) {
		this.vehicleManageService = vehicleManageService;
	}

	public void setDispatchOrderActionHistoryEntityDao(
			IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao) {
		this.dispatchOrderActionHistoryEntityDao = dispatchOrderActionHistoryEntityDao;
	}

	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}

	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	public void setRegionalVehicleService(
			IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}

	public void setTruckSchedulingTaskService(
			ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	public void setOrderAutoExceptionLogService(
			IOrderAutoExceptionLogService orderAutoExceptionLogService) {
		this.orderAutoExceptionLogService = orderAutoExceptionLogService;
	}

	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	public void setPropertyFactory(PropertyFactory propertyFactory) {
		this.propertyFactory = propertyFactory;
	}
	public void setExpressWorkerStatusDao(
			IExpressWorkerStatusDao expressWorkerStatusDao) {
		this.expressWorkerStatusDao = expressWorkerStatusDao;
	}
	public void setOrderPdaReturnRecordService(
			IOrderPdaReturnRecordService orderPdaReturnRecordService) {
		this.orderPdaReturnRecordService = orderPdaReturnRecordService;
	}
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setAutoScheduleManageService(
			IAutoScheduleManageService autoScheduleManageService) {
		this.autoScheduleManageService = autoScheduleManageService;
	}
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}
	

}

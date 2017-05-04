package com.deppon.foss.module.pickup.order.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckResourceDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAppOrderJMSService;
import com.deppon.foss.module.pickup.order.api.server.service.IGPSOrderTaskService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialPickupAddressService;
import com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderActionHistoryEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.AppOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleInfoDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.exception.SmsException;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.order.api.shared.util.PropertyFactory;
import com.deppon.foss.module.pickup.order.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.inteface.domain.orderstate.OrderStateRequest;
import com.eos.system.utility.StringUtil;

/**
 * 
 * 自动处理
 * 
 * @author 043258-foss-zhaobin
 * @date 2014-5-4 下午2:43:56
 */
public class AutoOrderHandleService extends OrderTheadPool {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AutoOrderHandleService.class);

	// 配置
	private PropertyFactory propertyFactory;

	private ISpecialPickupAddressService specialPickupAddressService;

	// pda签到dao
	private IPdaSignEntityDao pdaSignEntityDao;

	// 中转排班
	private ITruckSchedulingTaskService truckSchedulingTaskService;

	// 综合 定人定区service接口
	private IRegionalVehicleService regionalVehicleService;
	// 快递员工作状态Dao
	private IExpressWorkerStatusDao expressWorkerStatusDao;
	/**
	 * CRM订单更新服务
	 */
	private ICrmOrderJMSService crmOrderJMSService;
	/**
	 * CRM订单查询服务
	 */
	private ICrmOrderService crmOrderService;
	/**
	 * 短信模板服务
	 */
	private ISMSTempleteService sMSTempleteService;
	/**
	 * 短信通知服务
	 */
	private INotifyCustomerService notifyCustomerService;
	/**
	 * 调度订单DAO
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	/**
	 * 订单操作历史DAO
	 */
	private IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao;
	/**
	 * 派车记录DAO
	 */
	private IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao;
	/**
	 * 车辆管理Service
	 */
	private IVehicleManageService vehicleManageService;
	/**
	 * 中转约车服务
	 */
	private IOrderVehicleService orderVehicleService;
	/**
	 * 综合的营业部服务Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 车辆服务
	 */
	private ITruckResourceDao truckResourceDao;
	/**
	 * 应用监控
	 */
	private IBusinessMonitorService businessMonitorService;
	/**
	 * 消息服务
	 */
	private IMessageService messageService;
	/**
	 * 业务异常日志
	 */
	private IOrderAutoExceptionLogService orderAutoExceptionLogService;

	// 变更表dao
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;

	//综合的接口，排除外请车
	private ILeasedVehicleService leasedVehicleService;
		
	//综合的接口,本公司的车
	private static final String ORDER_RESOURCE_LEASEDVENHICLE = "leasedVehicle";
	
	/**
	 * GPS服务
	 */
	private IGPSOrderTaskService GPSOrderTaskService;

	private IAppOrderJMSService appOrderJMSService;
	
	private IConfigurationParamsService configurationParamsService;
	
	private ICustomerService customerService;

	/**
	 * 
	 * 自动处理
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-4 下午2:43:56
	 * @see com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool#businessExecutor(java.lang.Object)
	 */
	@Override
	public void businessExecutor(Object obj) {
		DispatchOrderEntity dispatchOrderEntity = (DispatchOrderEntity) obj;
		long begin = System.currentTimeMillis();
		// 是否特殊地址
		StringBuffer sb = new StringBuffer();
		sb.append(dispatchOrderEntity.getPickupProvince());
		sb.append(dispatchOrderEntity.getPickupCity());
		sb.append(dispatchOrderEntity.getPickupCounty());
		sb.append(dispatchOrderEntity.getPickupElseAddress());
		try {
			// //14.7.11 gcl AUTO-139 根据订单地址 查找特殊地址库中存在该订单地址信息
			SpecialAddressEntity specialAddressEntity = specialPickupAddressService
					.selectByAddress(sb.toString());
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"查询特殊地址,共耗时:{}ms", System.currentTimeMillis() - begin);
			/**
			 * 如果特殊地址库中存在该订单地址信息，有车辆则进行匹配，若无车辆则手工处理
			 */
			if (specialAddressEntity != null) {
				if(StringUtils.isNotEmpty(specialAddressEntity
						.getVehicleNo())) {
					long begin1 = System.currentTimeMillis();
					// 通过特殊地址匹配司机和车牌
					dispatchOrderEntity.appendLog("特殊地址ID:"
							+ specialAddressEntity.getId() + ",车牌:"
							+ specialAddressEntity.getVehicleNo());
					specialAddressEntity = matchBySpecilaAddr(dispatchOrderEntity,
							specialAddressEntity);
					LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"特殊地址匹配,共耗时:{}ms", System.currentTimeMillis() - begin1);
					try {
						Map<String, Object> result = null;
						Map<String, String> map = formatToMap(dispatchOrderEntity);
						String isCollect = null;
						result = GisInterfaceUtil.callGisInterface(
								propertyFactory.getGisUrl(), map);
						LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"特殊地址调用GIS,共耗时:{}ms", System.currentTimeMillis() - begin1);
						if (result != null && result.size() > 0) {
							isCollect = (String) result.get("isCollect");
							if (StringUtils.isNotEmpty(isCollect)
									&& FossConstants.YES.equals(isCollect)) {
								dispatchOrderEntity.setIsCollect(isCollect);	
								LOGGER.info("特殊地址已采集");
								dispatchOrderEntity.appendLog("特殊地址已采集");
							}
						}
					} catch (Exception e) {
						LOGGER.info("特殊地址调用gis接口失败");
						dispatchOrderEntity.appendLog("特殊地址调用gis接口失败");
						specialAddressEntity = matchBySpecilaAddr(dispatchOrderEntity,
								specialAddressEntity);
					}
				}else {
					LOGGER.info("特殊地址车牌为空");
					dispatchOrderEntity.appendLog("特殊地址车牌为空");
				}
			} else {
				// 14.7.11 gcl AUTO-139 如果地址库不存在 解析gis
				// 获得GIS坐标id
				String scopeCoordinatesId = null;
				Map<String, String> map = formatToMap(dispatchOrderEntity);
				Map<String, Object> result = null;
				// 调用GIS接口获得坐标id
				try {
					long begin2 = System.currentTimeMillis();
					result = GisInterfaceUtil.callGisInterface(
							propertyFactory.getGisUrl(), map);
					LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"非特殊地址调用GIS,共耗时:{}ms", System.currentTimeMillis() - begin2);
					if (result != null && result.size() > 0) {
						// 获得GIS坐标id
						scopeCoordinatesId = (String) result
								.get("scopeCoordinatesId");
						LOGGER.info("GIS坐标ID:" + scopeCoordinatesId);
						dispatchOrderEntity.appendLog("GIS坐标ID:"
								+ scopeCoordinatesId);
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					dispatchOrderChangeJobId(dispatchOrderEntity);
					// zxy 20140709 内部优化 start 修改:日志完善
					dispatchOrderEntity.appendException(e.getMessage());
					insertLog(dispatchOrderEntity.getOrderNo(),
							dispatchOrderEntity.getOrderNo(), "gisIDException",
							dispatchOrderEntity.getSbLog().toString());
					// zxy 20140709 内部优化 end 修改:日志完善
					return;
				}
				if (StringUtils.isEmpty(scopeCoordinatesId)) {
					dispatchOrderChangeJobId(dispatchOrderEntity);
					// zxy 20140709 内部优化 start 修改:日志完善
					dispatchOrderEntity.appendException("GISID is Null");
					insertLog(dispatchOrderEntity.getOrderNo(),
							dispatchOrderEntity.getOrderNo(), "gisIDNull",
							dispatchOrderEntity.getSbLog().toString());
					// zxy 20140709 内部优化 end 修改:日志完善
					return;
				}
				dispatchOrderEntity.setPreprocessId(scopeCoordinatesId);
				// 是否已入库标记
				String isCollect = (String) result.get("isCollect");
				if (StringUtils.isNotEmpty(isCollect)
						&& FossConstants.YES.equals(isCollect)) {
					dispatchOrderEntity.setIsCollect(isCollect);
				}
				dispatchOrderEntity.setOrderStatus("");
				long begin3 = System.currentTimeMillis();
				dispatchOrderEntityDao
						.updateByPrimaryKeySelective(dispatchOrderEntity);
				LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"非特殊地址GISID更新,共耗时:{}ms", System.currentTimeMillis() - begin3);
				// 调用综合接口获得对应的车辆 调用原始的定区车辆接口（判断一级车、二级车、定区车）
				long begin4 = System.currentTimeMillis();
				RegionVehicleInfoDto regionVehicleInfoDto = regionalVehicleService
						.querySmallZoneInfoByGisIdForNew(scopeCoordinatesId);
				LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"非特殊地址GISID获取车辆,共耗时:{}ms", System.currentTimeMillis() - begin4);
				if (null == regionVehicleInfoDto
						|| StringUtils.isEmpty(regionVehicleInfoDto
								.getSmallZoneCode())) {
					long begin5 = System.currentTimeMillis();
					dispatchOrderChangeJobId(dispatchOrderEntity);
					LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"预处理,共耗时:{}ms", System.currentTimeMillis() - begin5);
					// zxy 20140709 内部优化 start 修改:日志完善
					dispatchOrderEntity
							.appendException("综合根据GISID查询smallZoneCode Null");
					insertLog(dispatchOrderEntity.getOrderNo(),
							dispatchOrderEntity.getOrderNo(),
							"smallZoneCodeNull", dispatchOrderEntity.getSbLog()
									.toString());
					// zxy 20140709 内部优化 end 修改:日志完善
					return;
				}
				//将小区插入订单表中
				OrderHandleDto temp = new OrderHandleDto();
				temp.setId(dispatchOrderEntity.getId());
				temp.setPickupRegionCode(regionVehicleInfoDto.getSmallZoneCode());
				temp.setPickupRegionName(regionVehicleInfoDto.getSmallZoneName());
				long begin6 = System.currentTimeMillis();
				dispatchOrderEntityDao.updateByOrderHandle(temp);
				LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"非特殊地址更新小区,共耗时:{}ms", System.currentTimeMillis() - begin6);
				regionVehicleInfoDto.getSmallZoneCode();
				regionVehicleInfoDto.getSmallZoneName();
				dispatchOrderEntity.appendLog("小区编码:"
						+ regionVehicleInfoDto.getSmallZoneCode());
				long begin7 = System.currentTimeMillis();
				// 特殊日期排班
				TruckSchedulingZoneEntity truckSchedulingZoneEntity = truckSchedulingTaskService
						.queryVehicleByZoneCode(
								regionVehicleInfoDto.getSmallZoneCode(),
								new Date());
				LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"非特殊地址获取特殊排班,共耗时:{}ms", System.currentTimeMillis() - begin7);
				long begin8 = System.currentTimeMillis();
				// 通过中转排班
				dispatchOrderEntity = mathByTruckSchedulingZone(
						truckSchedulingZoneEntity, dispatchOrderEntity,
						regionVehicleInfoDto);
				LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"非特殊地址获取中转排班排班,共耗时:{}ms", System.currentTimeMillis() - begin8);
				// 14.7.18 gcl AUTO-182 设置预处理建议小区
				dispatchOrderEntity
						.setSmallZoneCodeSuggested(regionVehicleInfoDto
								.getSmallZoneCode());
				dispatchOrderEntity
						.setSmallZoneNameSuggested(regionVehicleInfoDto
								.getSmallZoneName());
			}
			/**
			 * 如果车牌和司机都已经存在 则证明符合自动调度的条件
			 */
			if (StringUtils.isNotEmpty(dispatchOrderEntity.getVehicleNo())
					&& StringUtils.isNotEmpty(dispatchOrderEntity
							.getDriverCode())) {
				encapsulationAutoMatch(dispatchOrderEntity);
			} else {
				// 未开启开关标记
				dispatchOrderChangeJobId(dispatchOrderEntity);
				// zxy 20140709 内部优化 start 修改:日志完善
				dispatchOrderEntity.appendException("车牌或司机为空");
				insertLog(dispatchOrderEntity.getOrderNo(),
						dispatchOrderEntity.getOrderNo(), "VNoOrDriverNULL",
						dispatchOrderEntity.getSbLog().toString());
				// zxy 20140709 内部优化 end 修改:日志完善
			}
		} catch (Exception e) {
			long begin9 = System.currentTimeMillis();
			dispatchOrderChangeJobId(dispatchOrderEntity);
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"预处理,共耗时:{}ms", System.currentTimeMillis() - begin9);
			LOGGER.error(e.getMessage(), e);
			// zxy 20140709 内部优化 start 修改:日志完善
			dispatchOrderEntity.appendLog(e.getMessage());
			insertLog(dispatchOrderEntity.getOrderNo(),
					dispatchOrderEntity.getOrderNo(), "businessException",
					dispatchOrderEntity.getSbLog().toString());
			// zxy 20140709 内部优化 end 修改:日志完善
		}
	}

	private DispatchOrderEntity mathByTruckSchedulingZone(
			TruckSchedulingZoneEntity truckSchedulingZoneEntity,
			DispatchOrderEntity dispatchOrder,
			RegionVehicleInfoDto regionVehicleInfoDto) {
		DispatchOrderEntity dispatchOrderEntity = dispatchOrder;
		// 调用中转排班 获取特殊日期排班信息
		if (truckSchedulingZoneEntity != null) {
			// ///14.7.17 gcl 先判断车辆是否暂停 如果暂停手工处理
			Long l = vehicleNoOpenStatus(truckSchedulingZoneEntity
					.getVehicleNo());
			if (l <= 0) {
				dispatchOrderEntity.appendLog("特殊日期排班车辆暂停"
						+ truckSchedulingZoneEntity.getVehicleNo());
				return dispatchOrderEntity;
			}
			dispatchOrderEntity.appendLog("中转特殊日期排班司机:"
					+ truckSchedulingZoneEntity.getDriverCode() + ",车牌:"
					+ truckSchedulingZoneEntity.getVehicleNo());
			// 车牌
			dispatchOrderEntity.setVehicleNoSuggested(truckSchedulingZoneEntity
					.getVehicleNo());
			dispatchOrderEntity.setVehicleNo(truckSchedulingZoneEntity
					.getVehicleNo());
			// 司机姓名
			dispatchOrderEntity
					.setDriverNameSuggested(truckSchedulingZoneEntity
							.getDriverName());
			dispatchOrderEntity.setDriverName(truckSchedulingZoneEntity
					.getDriverName());
			// 司机编码
			dispatchOrderEntity
					.setDriverCodeSuggested(truckSchedulingZoneEntity
							.getDriverCode());
			dispatchOrderEntity.setDriverCode(truckSchedulingZoneEntity
					.getDriverCode());
		} else {
			// 根据gis接口获取车辆司机信息
			dispatchOrderEntity = matchByGis(dispatchOrderEntity,
					regionVehicleInfoDto);
		}
		return dispatchOrderEntity;
	}

	/**
	 * 根据gis接口获取车辆司机信息
	 * 
	 * @param dispatchOrderEntity
	 * @return
	 */
	private DispatchOrderEntity matchByGis(
			DispatchOrderEntity dispatchOrderEntity,
			RegionVehicleInfoDto regionVehicleInfoDto) {
		// insertLog(dispatchOrderEntity.getOrderNo(),dispatchOrderEntity.getOrderNo(),"truckSchedNULL","特殊日期排班信息为空"
		// );
		dispatchOrderEntity.appendLog("特殊日期排班为空");
		// ///14.7.17 gcl 先判断一级区车辆是否暂停 如果暂停走二级区
		Long l = vehicleNoOpenStatus(regionVehicleInfoDto
				.getFristRegionVehicleNo());
		OwnTruckDto ownTruckDto = null;
		if (l <= 0) {
			dispatchOrderEntity.appendLog("一级车暂停:"
					+ regionVehicleInfoDto.getFristRegionVehicleNo());
		} else {
			ownTruckDto = getDriver(regionVehicleInfoDto
					.getFristRegionVehicleNo());
		}
		if (ownTruckDto != null) {
			dispatchOrderEntity.appendLog("一级区车牌:"
					+ regionVehicleInfoDto.getFristRegionVehicleNo() + ",司机:"
					+ ownTruckDto.getDriverCode());
			// 车牌
			dispatchOrderEntity.setVehicleNoSuggested(regionVehicleInfoDto
					.getFristRegionVehicleNo());
			dispatchOrderEntity.setVehicleNo(regionVehicleInfoDto
					.getFristRegionVehicleNo());
			// 司机姓名
			dispatchOrderEntity.setDriverNameSuggested(ownTruckDto
					.getDriverName());
			dispatchOrderEntity.setDriverName(ownTruckDto.getDriverName());
			// 司机编码
			dispatchOrderEntity.setDriverCodeSuggested(ownTruckDto
					.getDriverCode());
			dispatchOrderEntity.setDriverCode(ownTruckDto.getDriverCode());
		} else {
			if (l > 0) {
				dispatchOrderEntity.appendLog("一级车信息为空:"
						+ regionVehicleInfoDto.getFristRegionVehicleNo());
			}
			// 14.7.17 gcl 先判断二级区车辆是否暂停 如果暂停走定区
			OwnTruckDto ownTruckDto1 = null;
			l = vehicleNoOpenStatus(regionVehicleInfoDto
					.getSecondRegionVehicleNo());
			if (l <= 0) {
				dispatchOrderEntity.appendLog("二级车暂停:"
						+ regionVehicleInfoDto.getSecondRegionVehicleNo());
			} else {
				ownTruckDto1 = getDriver(regionVehicleInfoDto
						.getSecondRegionVehicleNo());
			}
			if (ownTruckDto1 != null) {
				dispatchOrderEntity.appendLog("二级区车牌:"
						+ regionVehicleInfoDto.getSecondRegionVehicleNo()
						+ ",司机:" + ownTruckDto1.getDriverCode());
				// 车牌
				dispatchOrderEntity.setVehicleNoSuggested(regionVehicleInfoDto
						.getSecondRegionVehicleNo());
				dispatchOrderEntity.setVehicleNo(regionVehicleInfoDto
						.getSecondRegionVehicleNo());
				// 司机姓名
				dispatchOrderEntity.setDriverNameSuggested(ownTruckDto1
						.getDriverName());
				dispatchOrderEntity.setDriverName(ownTruckDto1.getDriverName());
				// 司机编码
				dispatchOrderEntity.setDriverCodeSuggested(ownTruckDto1
						.getDriverCode());
				dispatchOrderEntity.setDriverCode(ownTruckDto1.getDriverCode());
			} else {
				if (l > 0) {
					dispatchOrderEntity.appendLog("二级车信息为空:"
							+ regionVehicleInfoDto.getSecondRegionVehicleNo());
				}
				// 14.7.17 gcl 先判断定区车辆是否暂停 如果暂停走手工处理
				OwnTruckDto ownTruckDto2 = null;
				l = vehicleNoOpenStatus(regionVehicleInfoDto
						.getMotorVehicleNo());
				if (l <= 0) {
					dispatchOrderEntity.appendLog("定区车暂停:"
							+ regionVehicleInfoDto.getMotorVehicleNo());
				} else {
					ownTruckDto2 = getDriver(regionVehicleInfoDto
							.getMotorVehicleNo());
				}
				if (ownTruckDto2 != null) {
					dispatchOrderEntity.appendLog("定区车牌:"
							+ regionVehicleInfoDto.getMotorVehicleNo() + ",司机:"
							+ ownTruckDto2.getDriverCode());
					// 车牌
					dispatchOrderEntity
							.setVehicleNoSuggested(regionVehicleInfoDto
									.getMotorVehicleNo());
					dispatchOrderEntity.setVehicleNo(regionVehicleInfoDto
							.getMotorVehicleNo());
					// 司机姓名
					dispatchOrderEntity.setDriverNameSuggested(ownTruckDto2
							.getDriverName());
					dispatchOrderEntity.setDriverName(ownTruckDto2
							.getDriverName());
					// 司机编码
					dispatchOrderEntity.setDriverCodeSuggested(ownTruckDto2
							.getDriverCode());
					dispatchOrderEntity.setDriverCode(ownTruckDto2
							.getDriverCode());
				} else {
					if (l > 0) {
						dispatchOrderEntity.appendLog("定区车信息为空:"
								+ regionVehicleInfoDto.getMotorVehicleNo());
					}
				}
			}
		}
		return dispatchOrderEntity;
	}

	/**
	 * 根据车牌号查询车辆工作状态 AUTO-170 gcl 14.7.17
	 * 
	 * @return
	 */
	private Long vehicleNoOpenStatus(String vehicleNo) {
		Long l = 1l;
		if (vehicleNo != null && StringUtil.isNotEmpty(vehicleNo)) {
			ExpressWorkerStatusEntity worker = new ExpressWorkerStatusEntity();
			worker.setActive(FossConstants.ACTIVE);
			worker.setBusinessArea(ExpressWorkerStatusConstants.ORDER_BUSINESS);
			worker.setVehicleNo(vehicleNo);
			worker.setWorkStatus(ExpressWorkerStatusConstants.STOP_STATUS);// 14.7.24
																			// gcl
																			// AUTO-200
			l = expressWorkerStatusDao.selectOneByVehicleNo(worker);// l>0 为暂停
			if (l > 0) {
				l = -1l;
			} else {
				l = 1l;
			}
		}
		return l;
	}

	/**
	 * 根据特殊地址和中转排班匹配司机何车牌号
	 * 
	 * @param dispatchOrderEntity
	 * @param specialAddrntity
	 * @return
	 */
	private SpecialAddressEntity matchBySpecilaAddr(
			DispatchOrderEntity dispatchOrderEntity,
			SpecialAddressEntity specialAddrntity) {
		long begin = System.currentTimeMillis();
		SpecialAddressEntity specialAddressEntity = specialAddrntity;
		dispatchOrderEntity.setVehicleNoSuggested(specialAddressEntity
				.getVehicleNo());
		// 设置车牌为特殊地址库中的车牌
		dispatchOrderEntity.setVehicleNo(specialAddressEntity.getVehicleNo());
		// 14.7.17 gcl begin 先判断车辆是否暂停 如果暂停手工处理
		Long l = vehicleNoOpenStatus(specialAddressEntity.getVehicleNo());
		if (l <= 0) {
			dispatchOrderEntity.appendLog(specialAddressEntity.getVehicleNo()
					+ "-暂停");// 14.7.28 gcl 日志添加
			return specialAddressEntity;
		}
		// 14.7.17 gcl end 先判断车辆是否暂停 如果暂停手工处理
		// 是否存在绑定关系
		PdaSignEntity condition = new PdaSignEntity();
		condition.setVehicleNo(specialAddressEntity.getVehicleNo());
		condition.setStatus(PdaSignStatusConstants.BUNDLE);
		// 查询签到获得对应司机
		PdaSignEntity pdaSignEntity = pdaSignEntityDao
				.querySignByVehicleNo(condition);
		LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"获取签到,共耗时:{}ms", System.currentTimeMillis() - begin);
		if (pdaSignEntity != null
				&& StringUtils.isNotEmpty(pdaSignEntity.getDriverCode())
				&& StringUtils.isNotEmpty(pdaSignEntity.getDriverName())) {
			dispatchOrderEntity.appendLog("签到司机:"
					+ pdaSignEntity.getDriverCode());
			// 司机姓名
			dispatchOrderEntity.setDriverNameSuggested(pdaSignEntity
					.getDriverName());
			dispatchOrderEntity.setDriverName(pdaSignEntity.getDriverName());
			// 司机编码
			dispatchOrderEntity.setDriverCodeSuggested(pdaSignEntity
					.getDriverCode());
			dispatchOrderEntity.setDriverCode(pdaSignEntity.getDriverCode());
		} else {
			dispatchOrderEntity.appendLog("签到司机为空");
			// 根据中转排班 拿到司机
			TruckSchedulingZoneEntity truckSchedulingZoneEntity1 = truckSchedulingTaskService
					.queryDriverByVehicle(specialAddressEntity.getVehicleNo(),
							new Date());
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"特殊地址查询排班,共耗时:{}ms", System.currentTimeMillis() - begin);
			if (truckSchedulingZoneEntity1 != null) {
				dispatchOrderEntity.appendLog("中转排班司机:"
						+ truckSchedulingZoneEntity1.getDriverCode());
				// 司机姓名
				dispatchOrderEntity
						.setDriverNameSuggested(truckSchedulingZoneEntity1
								.getDriverName());
				dispatchOrderEntity.setDriverName(truckSchedulingZoneEntity1
						.getDriverName());
				// 司机编码
				dispatchOrderEntity
						.setDriverCodeSuggested(truckSchedulingZoneEntity1
								.getDriverCode());
				dispatchOrderEntity.setDriverCode(truckSchedulingZoneEntity1
						.getDriverCode());
			} else {
				dispatchOrderEntity.appendLog("排班司机为空");
			}
		}
		// 更新数据库中对应订单的预处理建议、车辆和司机
		dispatchOrderEntityDao.updateByPrimaryKeySelective(dispatchOrderEntity);
		LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"更新订单预处理建议,共耗时:{}ms", System.currentTimeMillis() - begin);
		return specialAddressEntity;
	}

	/**
	 * 将订单实体转为Map.
	 * 
	 * @param dispatchOrderEntity
	 *            the dispatchOrderEntity
	 * @return the map
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午7:54:16
	 */
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
		// 其他详细地址
		map.put("otherAddress", dispatchOrderEntity.getPickupElseAddress());
		// 自动调度类型
		map.put("transportway", "motor_self");
		// 手机
		map.put("phone", dispatchOrderEntity.getMobile());
		// 电话
		map.put("tel", dispatchOrderEntity.getTel());
		// 接货区域
		map.put("matchtype", DictionaryValueConstants.REGION_TYPE_PK);
		return map;
	}

	/**
	 * 封装自动匹配
	 * 
	 * @param dispatchOrderEntity
	 */
	private void encapsulationAutoMatch(DispatchOrderEntity dispatchOrderEntity) {
		// 判断接货订单在CRM中是否存在
		/*if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER
				.equals(dispatchOrderEntity.getOrderType())
				&& crmOrderService
						.searchOrder(dispatchOrderEntity.getOrderNo()) == false) {
			insertLog(dispatchOrderEntity.getOrderNo(),
					dispatchOrderEntity.getOrderNo(), "orderNotInCRM",
					"订单在CRM中不存在");
			throw new DispatchException("订单" + dispatchOrderEntity.getOrderNo()
					+ "在CRM中不存在");
		}*/
		OrderHandleDto orderHandleDto = new OrderHandleDto();
		orderHandleDto.setId(dispatchOrderEntity.getId());
		// 设置运输性质
		orderHandleDto.setProductCode(dispatchOrderEntity.getProductCode());
		// 设置出发城市
		orderHandleDto.setPickupCity(dispatchOrderEntity.getPickupCity());
		// 设置订单来源
		orderHandleDto.setOrderSource(dispatchOrderEntity.getOrderSource());
		// 设置状态为已派车
		orderHandleDto
				.setOrderStatus(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
		// 发送中
		orderHandleDto
				.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDING);
		// 车牌
		orderHandleDto.setVehicleNo(dispatchOrderEntity.getVehicleNo());
		// 司机code
		orderHandleDto.setDriverCode(dispatchOrderEntity.getDriverCode());
		// 司机姓名
		orderHandleDto.setDriverName(dispatchOrderEntity.getDriverName());
		// 14.8.13 gcl AUTO-224 设置司机手机号
		orderHandleDto
				.setDriverMobile(getExpressOwnTruckDto(dispatchOrderEntity
						.getDriverCode()));
		orderHandleDto.setCustomerName(dispatchOrderEntity.getCustomerName());
		orderHandleDto.setCustomerMobile(dispatchOrderEntity.getMobile());
		// 操作时间
		orderHandleDto.setOperateTime(new Date());
		// 操作人
		orderHandleDto.setOperator("System");
		// 操作人编码
		orderHandleDto.setOperatorCode("000000");
		// 操作部门编码
		orderHandleDto.setOperateOrgCode(dispatchOrderEntity.getFleetCode());
		// zxy 20140710 AUTO-134 新增:设置字段内容
		// 设置订单号
		orderHandleDto.setOrderNo(dispatchOrderEntity.getOrderNo());
		// 设置订单类型
		orderHandleDto.setOrderType(dispatchOrderEntity.getOrderType());
		// 14.8.12 gcl AUTO-224
		orderHandleDto.setIsCustomer("Y");
		orderHandleDto.setIsSms("Y");
		// 14.7.18 gcl AUTO-182 设置预处理建议小区和实际接货小区为预处理小区
		orderHandleDto.setPickupRegionCode(dispatchOrderEntity
				.getSmallZoneCodeSuggested());
		orderHandleDto.setPickupRegionName(dispatchOrderEntity
				.getSmallZoneNameSuggested());
		orderHandleDto.setSmallZoneCodeActual(dispatchOrderEntity
				.getSmallZoneCodeSuggested());
		// zxy 20140710 AUTO-134 end 新增:设置字段内容
		// 14.7.30 gcl AUTO-212 设置订单受理方式为自动
		orderHandleDto
				.setAcceptStatus(DispatchOrderStatusConstants.ORDER_ACCEPT_STATUS_SYS);
		// 14.8.20 gcl 自动调度时 同时设置预处理车和司机信息 方便下次退回后找预处理
		orderHandleDto.setVehicleNoSuggested(dispatchOrderEntity
				.getVehicleNoSuggested());
		orderHandleDto.setDriverCodeSuggested(dispatchOrderEntity
				.getDriverCodeSuggested());
		orderHandleDto.setDriverNameSuggested(dispatchOrderEntity
				.getDriverNameSuggested());
		// 更新订单信息
		long begin = System.currentTimeMillis();
		int count = dispatchOrderEntityDao.updateByOrderHandle(orderHandleDto);
		LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"获更新订单,共耗时:{}ms", System.currentTimeMillis() - begin);
		if (count == 1) {
			// 添加订单操作历史
			long begin1 = System.currentTimeMillis();
			addDispatchOrderActionHistory(orderHandleDto);
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"获更新订单操作历史,共耗时:{}ms", System.currentTimeMillis() - begin1);
			// 添加派车记录
			long begin2 = System.currentTimeMillis();
			addDispatchVehicleRecord(orderHandleDto);
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"获更新订单派车记录,共耗时:{}ms", System.currentTimeMillis() - begin2);
			long begin3 = System.currentTimeMillis();
			addVehicleWV(orderHandleDto);
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"增加未接票数,共耗时:{}ms", System.currentTimeMillis() - begin3);
			long begin4 = System.currentTimeMillis();
			dispatchOrderChangeEntityDao
					.deleteChangeByOrderID(dispatchOrderEntity.getId());
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"删除变更表,共耗时:{}ms", System.currentTimeMillis() - begin4);
			// 获得订单对应的运输产品类型
			DispatchOrderConditionDto dto = new DispatchOrderConditionDto();
			dto.setOrderNo(orderHandleDto.getOrderNo());
			DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao
					.queryOrdersByOrderNo(dto);
			if (StringUtils.isNotEmpty(dispatchOrder.getProductCode())) {
				orderHandleDto.setProductCode(dispatchOrder.getProductCode());
			}
			// 14.8.14 gcl 设置短信内容
			long begin5 = System.currentTimeMillis();
			StringBuffer pickupAddress = new StringBuffer();
			pickupAddress.append(dispatchOrder.getPickupProvince());
			pickupAddress.append(dispatchOrder.getPickupCity());
			pickupAddress.append(dispatchOrder.getPickupCounty());
			pickupAddress.append(dispatchOrder.getPickupElseAddress());
			orderHandleDto.setPickupAddress(pickupAddress.toString());
			orderHandleDto.setGoodsQty(dispatchOrder.getGoodsQty());
			orderHandleDto.setWeight(dispatchOrder.getWeight());
			orderHandleDto.setVolume(dispatchOrder.getVolume());
			StringBuffer pickupTime = new StringBuffer();
			Date earliestPickupTime = dispatchOrder.getEarliestPickupTime();
			Date latestPickupTime = dispatchOrder.getLatestPickupTime();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			if (null != earliestPickupTime && null != latestPickupTime) {
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
			orderHandleDto.setOrderNotes(dispatchOrder.getOrderNotes());
			orderHandleDto.setTel(dispatchOrder.getTel());
			orderHandleDto.setSalesDepartmentName(dispatchOrder
					.getSalesDepartmentName());
			try {
				// 发送在线通知给营业部
				addNotice(orderHandleDto, getNotice(orderHandleDto));
			} catch (BusinessException e) {
				insertLog(dispatchOrderEntity.getOrderNo(),
						dispatchOrderEntity.getOrderNo(), "sendSmsError",
						"在线通知失败！");
				throw new DispatchException("在线通知失败！");
			}
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"在线通知营业部,共耗时:{}ms", System.currentTimeMillis() - begin5);
			// 发送短信
			long begin6 = System.currentTimeMillis();
			if(FossConstants.ACTIVE.equals(
					this.configurationParamsService.queryConfValueByCode(
							ConfigurationParamsConstants.STL_ORDER_MESSAGE_SWITCH))){
					try {
						LOGGER.info("短信发送开始！订单号"+orderHandleDto.getOrderNo());
						sendSmsByHandle(orderHandleDto);
						LOGGER.info("短信发送结束！订单号"+orderHandleDto.getOrderNo());
					} catch (Exception e) {
						String exceptionType = "SMS_SEND_ERROR";
						String reason = "发送短信异常";	
						backOrderHandle(orderHandleDto);
						insertLog(orderHandleDto.getId(), orderHandleDto.getOrderNo(), exceptionType, reason);
						throw new DispatchException("发送短信异常，无法继续操作！");
					}
				}else {
					String exceptionType = "SMS_SEND_ERROR";
					String reason = "短信功能关闭，短信发送失败！";	
					insertLog(orderHandleDto.getId(), orderHandleDto.getOrderNo(), exceptionType, reason);
					LOGGER.info("短信功能关闭，短信发送失败！订单号【"+orderHandleDto.getOrderNo()+"】");
				}
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"发送短信,共耗时:{}ms", System.currentTimeMillis() - begin6);
			
			long begin7 = System.currentTimeMillis();
			// 更新外部系统状态
			try{
				LOGGER.info("更新外部系统开始！订单号"+orderHandleDto.getOrderNo());
				updateExternalSystem(orderHandleDto, null);
				LOGGER.info("更新外部系统结束！订单号"+orderHandleDto.getOrderNo());
			}catch(Exception e){
				String exceptionType = "UPDATE_CRM_ERROR";
				String reason = "更新外部系统报错";	
				insertLog(orderHandleDto.getId(), orderHandleDto.getOrderNo(), exceptionType, reason);
			}
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"更新外部系统,共耗时:{}ms", System.currentTimeMillis() - begin7);
			//判断车辆是否是公司的车辆
			String queryVehicleType = leasedVehicleService.queryVehicleType(dispatchOrderEntity.getVehicleNo());
			if(queryVehicleType != null && !ORDER_RESOURCE_LEASEDVENHICLE.equals(queryVehicleType)){
				//是公司的车辆就走下面的逻辑
				// 同步约车信息给GPS
				long begin8 = System.currentTimeMillis();
				try{
					sendToGPS(orderHandleDto,1);
				}catch(Exception e){
					String exceptionType = "UPDATE_GPS_ERROR";
					String reason = "自动调度同步约车信息报错";	
					insertLog(orderHandleDto.getId(), orderHandleDto.getOrderNo(), exceptionType, reason);
				}
				LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"同步约车信息给GPS,共耗时:{}ms", System.currentTimeMillis() - begin8);
			}
			
			//推送订单信息给APP
			long begin9 = System.currentTimeMillis();
			try{
				sendEmpCodeToApp(orderHandleDto);
			}catch(Exception e){
				String exceptionType = "UPDATE_APP_ERROR";
				String reason = "自动调度推送订单信息给APP报错";	
				insertLog(orderHandleDto.getId(), orderHandleDto.getOrderNo(), exceptionType, reason);
			}
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"推送订单信息给APP,共耗时:{}ms", System.currentTimeMillis() - begin9);
			
			//推送订单信息给微信
			long begin10 = System.currentTimeMillis();
			try{
				sendOrderStateToApp(orderHandleDto);
			}catch(Exception e){
				String exceptionType = "UPDATE_WEIXIN_ERROR";
				String reason = "自动调度推送订单信息给微信报错";	
				LOGGER.info(orderHandleDto.getOrderNo()+":"+e.getMessage());
				insertLog(orderHandleDto.getId(), orderHandleDto.getOrderNo(), exceptionType, reason);
			}
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"推送订单信息给微信,共耗时:{}ms", System.currentTimeMillis() - begin10);
			long begin11 = System.currentTimeMillis();
			// 应用监控--订单分派票数
			if (DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP
					.equals(orderHandleDto.getOriginOrderStatus())) {
				// 应用监控--订单改派票数
				businessMonitorService.counter(
						BusinessMonitorIndicator.ORDER_REASSIGN_COUNT,
						currentInfo(dispatchOrderEntity));
			}
			// 应用监控--订单分派票数
			businessMonitorService.counter(
					BusinessMonitorIndicator.ORDER_ASSIGN_COUNT,
					currentInfo(dispatchOrderEntity));// 14.7.11 gcl AUTO-142
														// 构造当前用户信息
			// 应用监控--订单处理票数
			businessMonitorService.counter(
					BusinessMonitorIndicator.ORDER_PROCESSED_COUNT,
					currentInfo(dispatchOrderEntity));
			LOGGER.info("自动调度"+dispatchOrderEntity.getOrderNo()+"应用监控,共耗时:{}ms", System.currentTimeMillis() - begin11);
		} else {
			// 未开启开关标记
			dispatchOrderChangeJobId(dispatchOrderEntity);
		}
	}

	/**
	 * 
	 * 约车信息受理时同步快递员信息给APP
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-10-29 下午4:54:49
	 */
	private void sendEmpCodeToApp(OrderHandleDto orderHandleDto) {
		AppOrderDto appOrderDto = new AppOrderDto();
		appOrderDto.setOrderNo(orderHandleDto.getOrderNo());
		appOrderDto.setDriverCode(orderHandleDto.getDriverCode());
		appOrderDto.setPosterMobilePhone(orderHandleDto.getCustomerMobile());
		appOrderDto.setProductCode(orderHandleDto.getProductCode());
		appOrderDto.setStatus(orderHandleDto.getOrderStatus());
		appOrderJMSService.sendOrderState(appOrderDto);
	}

	private String getExpressOwnTruckDto(String driverCode) {
		return pdaSignEntityDao.queryPhoneByDriverCode(driverCode);
	}

	/**
	 * 14.7.11 gcl AUTO-142 构造当前用户信息
	 */
	private CurrentInfo currentInfo(DispatchOrderEntity dispatchOrderEntity) {
		/**
		 * 构造UserEntity
		 */
		UserEntity user = new UserEntity();
		// 设置编码
		user.setEmpCode(dispatchOrderEntity.getCreateUserCode());
		// 设置雇员名称
		user.setEmpName(dispatchOrderEntity.getCreateUserName());
		// 设置用户名称
		user.setUserName(dispatchOrderEntity.getCreateUserName());
		/**
		 * 构造EmployeeEntity
		 */
		EmployeeEntity employeeEntity = new EmployeeEntity();
		// 设置雇员名称
		employeeEntity.setEmpCode(dispatchOrderEntity.getCreateUserCode());
		// 设置用户名称
		employeeEntity.setEmpName(dispatchOrderEntity.getCreateUserName());
		user.setEmployee(employeeEntity);
		/**
		 * 构造部门实体
		 */
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		// 设置编码
		dept.setCode(dispatchOrderEntity.getSalesDepartmentCode());
		// 设置名称
		dept.setName(dispatchOrderEntity.getSalesDepartmentName());
		return new CurrentInfo(user, dept);
	}

	/**
	 * 未开启开关标记
	 * 
	 * @param dispatchOrderEntity
	 */
	private void dispatchOrderChangeJobId(
			DispatchOrderEntity dispatchOrderEntity) {
		// 更新状态B执行原来job
		// 标记B关闭状态
		DispatchOrderChangeEntity dispatchOrderChangeEntity = new DispatchOrderChangeEntity();
		dispatchOrderChangeEntity.setChangeId(dispatchOrderEntity.getId());
		dispatchOrderChangeEntity.setJobId("B");// todo B的值
		dispatchOrderChangeEntityDao
				.updateChangebyOrderId(dispatchOrderChangeEntity);
	}

	/**
	 * 
	 * 找寻司机
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-4 下午5:41:30
	 */
	private OwnTruckDto getDriver(String vehicleNo) {
		// 14.7.12 gcl AUTO-139 添加车牌不为空判断 为空返回Null
		if (StringUtils.isNotBlank(vehicleNo)) {
			// 是否存在绑定关系
			PdaSignEntity condition = new PdaSignEntity();
			OwnTruckDto ownTruckDto = new OwnTruckDto();
			condition.setVehicleNo(vehicleNo);
			condition.setStatus(PdaSignStatusConstants.BUNDLE);
			// 查询签到获得对应司机
			PdaSignEntity pdaSignEntity = pdaSignEntityDao
					.querySignByVehicleNo(condition);
			// zxy 20140710 AUTO-135 修改:增加司机为空判断
			if (pdaSignEntity != null
					&& StringUtils.isNotBlank(pdaSignEntity.getDriverCode())) {
				ownTruckDto.setDriverCode(pdaSignEntity.getDriverCode());
				ownTruckDto.setDriverName(pdaSignEntity.getDriverName());
				return ownTruckDto;
			} else {
				// 根据司机排班信息拿到司机
				TruckSchedulingZoneEntity truckSchedulingZoneEntity2 = truckSchedulingTaskService
						.queryDriverByVehicle(vehicleNo, new Date()); // 14.7.12
																		// gcl
																		// AUTO-139
																		// 根据车牌号找司机
				if (null != truckSchedulingZoneEntity2) {
					ownTruckDto.setDriverCode(truckSchedulingZoneEntity2
							.getDriverCode());
					ownTruckDto.setDriverName(truckSchedulingZoneEntity2
							.getDriverName());
					return ownTruckDto;
				} else {
					return null;
				}
			}
		} else {
			return null;
		}

	}

	/**
	 * 
	 * 添加订单操作历史
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午3:29:20
	 */
	private void addDispatchOrderActionHistory(OrderHandleDto orderHandleDto) {
		DispatchOrderActionHistoryEntity dispatchOrderActionHistoryEntity = new DispatchOrderActionHistoryEntity();
		// 主键id
		dispatchOrderActionHistoryEntity.setId(UUIDUtils.getUUID());
		// 订单操作备注
		dispatchOrderActionHistoryEntity.setNotes(orderHandleDto
				.getOperateNotes());
		// 订单操作人
		dispatchOrderActionHistoryEntity.setOperator(orderHandleDto
				.getOperator());
		// 订单操作人code
		dispatchOrderActionHistoryEntity.setOperatorCode(orderHandleDto
				.getOperatorCode());
		// 调度订单id
		dispatchOrderActionHistoryEntity.settSrvDispatchOrderId(orderHandleDto
				.getId());
		// 调度订单状态
		dispatchOrderActionHistoryEntity.setOrderStatus(orderHandleDto
				.getOrderStatus());
		// 操作时间
		dispatchOrderActionHistoryEntity.setOperateTime(orderHandleDto
				.getOperateTime());
		// 插入订单操作历史
		dispatchOrderActionHistoryEntityDao
				.addDispatchOrderActionHistory(dispatchOrderActionHistoryEntity);
	}

	/**
	 * 
	 * 添加派车记录
	 * 
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
		// 司机手机
		vehicleRecordEntity.setDriverMobile(orderHandleDto.getDriverMobile());
		// 派车时间
		vehicleRecordEntity.setDeliverTime(orderHandleDto.getOperateTime());
		// 是否PDA
		vehicleRecordEntity
				.setPdaStatus(FossConstants.YES.equals(orderHandleDto
						.getIsPda()) ? DispatchOrderStatusConstants.ISPDA_NORMAL
						: DispatchOrderStatusConstants.ISPDA_NO);
		// 定人定区Code
		vehicleRecordEntity.setPickupRegionCode(orderHandleDto
				.getPickupRegionCode());
		// 定人定区名称
		vehicleRecordEntity.setPickupRegionName(orderHandleDto
				.getPickupRegionName());
		// 订单状态
		vehicleRecordEntity.setOrderStatus(orderHandleDto.getOrderStatus());
		if (DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE
				.equals(orderHandleDto.getOrderStatus())) {
			// 受理状态
			vehicleRecordEntity
					.setProcessStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE
							.equals(orderHandleDto.getOriginOrderStatus()) ? DispatchOrderStatusConstants.STATUS_PROCESS_ACCEPT
							: DispatchOrderStatusConstants.STATUS_PROCESS_AGAIN);
		} else {
			// 退回状态
			vehicleRecordEntity
					.setProcessStatus(DispatchOrderStatusConstants.STATUS_PROCESS_RETURN);
		}
		dispatchVehicleRecordEntityDao
				.addDispatchVehicleRecord(vehicleRecordEntity);
	}

	/**
	 * 
	 * 增加未接票数.
	 * 
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
	 * 修改外部系统状态
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午3:40:03
	 */
	public void updateExternalSystem(OrderHandleDto orderHandleDto,
			String reason) {
		if (StringUtils.isBlank(orderHandleDto.getOrderNo())
				|| StringUtils.isBlank(orderHandleDto.getOrderType())
				|| StringUtils.isBlank(orderHandleDto.getOrderStatus())) {
			throw new DispatchException("UPDATE_EXTSYS_CRM",
					"订单信息缺失，更新CRM操作失败！");
		}
		if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER
				.equals(orderHandleDto.getOrderType())) {
			// 更新CRM订单状态
			updateCrmOrder(orderHandleDto);
		} else {
			// 调用中转接口修改转货订单的接口
			orderVehicleService.updateOrderVehicleApplyStatusByOrderNo(
					orderHandleDto.getOrderNo(),
					orderHandleDto.getOrderStatus(), reason);
		}
	}

	/**
	 * 
	 * @Title: sendToGPS
	 * @Description: 将约车订单信息发送给GPS
	 * @param @param optState 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void sendToGPS(OrderHandleDto orderHandleDto, int optState) {
		boolean success = GPSOrderTaskService.putOrderTaskToGPS(orderHandleDto,
				optState);
		// 如果发送成功
		if (success) {
			LOGGER.info("订单发送GPS成功！");
		} else {
			LOGGER.error("订单发送GPS失败！");
			throw new BusinessException("订单发送GPS失败！");
		}
	}

	/**
	 * 
	 * 修改CRM系统状态
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午3:40:13
	 */
	private void updateCrmOrder(OrderHandleDto orderHandleDto) {
		// 获取CRM的映射订单状态
		String crmOrderStatus = DispatchOrderStatusConstants.CRM_ORDER_STATUS_MAPPING
				.get(orderHandleDto.getOrderStatus());
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
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(orderHandleDto
						.getOperateOrgCode());
		// 操作部门code
		crmModifyInfoDto.setOprateDeptCode(org != null ? org.getUnifiedCode()
				: "");
		// 订单状态
		crmModifyInfoDto.setGoodsStatus(crmOrderStatus);
		// 操作备注
		crmModifyInfoDto.setBackInfo(orderHandleDto.getOperateNotes());
		// 调用CRM订单修改接口
		crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);

	}

	/**
	 * 
	 * 发送短信
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午4:00:48 231440
	 */
	public void sendSmsByHandle(OrderHandleDto orderHandleDto) {
		try {
			// 发送短信给司机
			if (FossConstants.YES.equals(orderHandleDto.getIsSms())) {
				// 接货和转货订单短信模板不同
				if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER
						.equals(orderHandleDto.getOrderType())) {
					DispatchOrderEntity dispatchOrder = dispatchOrderEntityDao
							.queryAllInfoByOrderNo(orderHandleDto.getOrderNo());
					String smsCodeForDriver = DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_DRIVER_SMS;
					if(StringUtils.isNotBlank(dispatchOrder.getDeliveryCustomerCode())){
						CustomerEntity customer=new CustomerEntity();
						customer.setCusCode(dispatchOrder.getDeliveryCustomerCode());
						CustomerEntity toCustomer=customerService.queryCustInfoByCustomerEntity(customer);
						if(toCustomer!=null&&FossConstants.CUSTOMER_CONTRABAND.equals(toCustomer.getBlackListCategory())) {
							smsCodeForDriver = DispatchOrderStatusConstants.LESS_CARLOAD_FREIGHT_BLACKLIST_SMS;
						}
					}
					sendSms(smsCodeForDriver, orderHandleDto, true,NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
				} else {
					sendSms(DispatchOrderStatusConstants.TEMPLATE_TRANSFER_ORDER_TO_DRIVER_SMS,
							orderHandleDto, true,
							NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
				}
			}
		}  catch (NotifyCustomerException e) {
			throw new DispatchException("司机短信发送失败！");
		} catch (SMSSendLogException e) {
			throw new DispatchException("司机短信发送失败！");
		} catch (BusinessException e) {
			throw new DispatchException("短信模板获取失败！");
		} catch (Exception e) {
			throw new DispatchException("司机短信发送失败！");
		}
		try {
			// 发送短信给客户
			if (FossConstants.YES.equals(orderHandleDto.getIsCustomer())) {
				OwnTruckConditionDto conditionDto = new OwnTruckConditionDto();
				// 车牌号
				conditionDto.setVehicleNo(orderHandleDto.getVehicleNo());
				// 有效
				conditionDto.setActive(FossConstants.ACTIVE);
				// 获取车型
				VehicleInfoDto vehicleInfoDto = truckResourceDao
						.queryVehicleTypeByVehicleNo(conditionDto);
				// 设置车型
				orderHandleDto.setVehicleLengthName(vehicleInfoDto
						.getVehicleLengthName());
				// 零担
				sendSms(DispatchOrderStatusConstants.TEMPLATE_ORDER_TO_CUSTOMER_SMS,
						orderHandleDto, false,
						NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
			}
		} catch (NotifyCustomerException e) {
			LOGGER.info("客户短信发送失败");
			LOGGER.error(e.getMessage(), e);
		} catch (SMSSendLogException e) {
			LOGGER.info("客户短信发送失败");
			LOGGER.error(e.getMessage(), e);
		} catch (BusinessException e) {
			LOGGER.info("短信模板获取失败");
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.info("客户短信发送失败");
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 发送短信
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午4:06:15
	 */
	private void sendSms(String smsCode, OrderHandleDto orderHandleDto,
			boolean isDriver, String moduleName) {
		NotificationEntity notificationEntity = new NotificationEntity();
		// 设置订单号
		notificationEntity.setWaybillNo(orderHandleDto.getOrderNo());
		// 设置通知模块名 NotifyCustomerConstants.BS_TYPE_PKP_ORDER
		notificationEntity.setModuleName(moduleName);
		// 设置通知类型
		notificationEntity
				.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		// 设置通知内容
		notificationEntity.setNoticeContent(this.getSmsContent(smsCode,
				orderHandleDto));
		// 设置手机号
		if (isDriver) {
			notificationEntity.setMobile(orderHandleDto.getDriverMobile());
			notificationEntity.setConsignee(orderHandleDto.getDriverName());
		} else {
			notificationEntity.setMobile(orderHandleDto.getCustomerMobile());
			notificationEntity.setConsignee(orderHandleDto.getCustomerName());
		}
		// 设置发送对象
		// 设置操作人
		notificationEntity.setOperator(orderHandleDto.getOperator());
		// 设置操作人编号
		notificationEntity.setOperatorNo(orderHandleDto.getOperatorCode());
		// 设置操作部门编码
		notificationEntity
				.setOperateOrgCode(orderHandleDto.getOperateOrgCode());
		// 设置操作时间
		notificationEntity.setOperateTime(new Date());
		notifyCustomerService.sendMessage(notificationEntity);
	}

	/**
	 * 零担&自动调度 发送订单状态（已派车）到app DMANA-8883
	 * 
	 * @author 219396-foss-daolin
	 * @date 2014-12-05 上午11:10:49
	 */
	private void sendOrderStateToApp(OrderHandleDto orderHandleDto) {
		OrderStateRequest request = new OrderStateRequest();
		// 订单状态
		request.setOrderSource(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE);
		// 渠道来源
		request.setChannel(orderHandleDto.getOrderSource());
		// 订单号
		request.setOrderNo(orderHandleDto.getOrderNo());
		// 运输性质
		request.setTransportProperties(orderHandleDto.getProductCode());
		// 发货人
		request.setSender(orderHandleDto.getCustomerName());
		// 发货人手机
		request.setSenderTel(orderHandleDto.getCustomerMobile());
		// 司机名称
		request.setDriverName(orderHandleDto.getDriverName());
		// 司机电话
		request.setDriverTel(getExpressOwnTruckDto(orderHandleDto.getDriverMobile()));
		// 车牌号
		request.setCarNumber(orderHandleDto.getVehicleNo());
		// 出发城市
		request.setLeaveCity(orderHandleDto.getPickupCity());
		// 接单时间
		request.setOrderTime(orderHandleDto.getOwsPickupTime());


		appOrderJMSService.sendOrderStateToApp(request);
	}

	/**
	 * 
	 * 获取短信信息
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午4:06:56
	 */
	public String getSmsContent(String smsCode, OrderHandleDto orderHandleDto) {
		String sms = "";
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		// 部门编码
		smsParamDto.setOrgCode(orderHandleDto.getOperateOrgCode());
		// 参数Map
		smsParamDto.setMap(this.getSmsParam(orderHandleDto));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (Exception e) {
			throw new SmsException(e.getMessage(), e);
		}
		if (sms == null) {
			throw new SmsException(ActionMessageType.SMS_NOTFOUND);
		}
		return sms;
	}

	/**
	 * 
	 * 设置短信模版内容的参数
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午4:09:06
	 */
	private Map<String, String> getSmsParam(OrderHandleDto orderHandleDto) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 订单号
		paramMap.put("orderNo", orderHandleDto.getOrderNo());
		// 营业部名称
		paramMap.put("salesDepartmentName",
				orderHandleDto.getSalesDepartmentName());
		// 客户姓名
		paramMap.put("customerName", orderHandleDto.getCustomerName());
		// 电话
		paramMap.put("tel", orderHandleDto.getTel());
		// 手机号码
		paramMap.put("mobile", orderHandleDto.getCustomerMobile());
		// 接货地址
		paramMap.put("pickupAddress", orderHandleDto.getPickupAddress());
		// 件数
		paramMap.put("goodsQty", String.valueOf(orderHandleDto.getGoodsQty()));
		// 重量
		paramMap.put("weight", String.valueOf(orderHandleDto.getWeight()));
		// 体积
		paramMap.put("volume", String.valueOf(orderHandleDto.getVolume()));
		// 接货时间
		paramMap.put("pickupTime", orderHandleDto.getPickupTime());
		// 订单备注
		paramMap.put("orderNotes", orderHandleDto.getOrderNotes());
		// 车型
		paramMap.put("vehicleType", orderHandleDto.getVehicleLengthName());
		// 车牌号
		paramMap.put("vehicleNo", orderHandleDto.getVehicleNo());
		// 司机姓名
		paramMap.put("driverName", orderHandleDto.getDriverName());
		// 司机手机
		paramMap.put("driverMobile", orderHandleDto.getDriverMobile());
		// 包装
		paramMap.put("goodsPackage", orderHandleDto.getGoodsPackage());
		return paramMap;
	}

	/**
	 * 
	 * 获取在线通知内容
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午4:18:05
	 */
	private String getNotice(OrderHandleDto orderHandleDto) {
		SmsParamDto smsParam = new SmsParamDto();
		// code
		smsParam.setSmsCode("PKP_ORDER_NOTICE");
		// 参数初始化
		smsParam.setMap(initParams(orderHandleDto));
		// 获取通知内容
		return sMSTempleteService.querySmsByParam(smsParam);
	}

	/**
	 * 
	 * 初始化在线通知参数
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午4:18:39
	 */
	private Map<String, String> initParams(OrderHandleDto orderHandleDto) {
		Map<String, String> map = new HashMap<String, String>();
		// 营业部名称
		map.put("salesDepartment", orderHandleDto.getSalesDepartmentName());
		// 订单号
		map.put("orderNo", orderHandleDto.getOrderNo());
		// 司机姓名
		map.put("driverName", orderHandleDto.getDriverName());
		// 车牌号
		map.put("vehicleNo", orderHandleDto.getVehicleNo());
		// 操作时间
		map.put("operateTime",
				DateUtils.convert(orderHandleDto.getOperateTime(), null));
		// 操作人
		map.put("operator", orderHandleDto.getOperator());
		// 受理部门
		map.put("dept", orderHandleDto.getOperateOrgName());
		return map;
	}

	/**
	 * 
	 * 抛出异常之后 回滚订单状态
	 * @author 043258-foss-zhaobin
	 * @date 2014-12-26 下午3:49:26
	 */
	public void backOrderHandle(OrderHandleDto orderHandleDto){
		orderHandleDto.setSmallZoneCodeActual("");
		orderHandleDto.setDriverCode("");
		orderHandleDto.setDriverName("");
		orderHandleDto.setDeviceNo("");
		orderHandleDto.setIsPda("");
		orderHandleDto.setIsSms("");
		orderHandleDto.setIsCustomer("");
		orderHandleDto.setOperator("");
		orderHandleDto.setOperatorCode("");
		orderHandleDto.setOperateOrgCode("");
		orderHandleDto.setOperateOrgName("");
		// 设置状态为已派车
		orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_NONE_HANDLE);
		// 发送状态制空
		orderHandleDto.setOrderSendStatus("");
		// 设置订单受理方式为空
		orderHandleDto.setAcceptStatus("");
		orderHandleDto.setDriverMobile("");
		orderHandleDto.setOriginOrderStatus(null);
		dispatchOrderEntityDao.updateByOrderHandle(orderHandleDto);
	}
	
	/**
	 * 
	 * 发送在线通知
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2014-5-6 下午4:21:34
	 */
	private void addNotice(OrderHandleDto orderHandleDto, String noticeContent)
			throws ExceptionProcessException {
		try {
			// 如果接受组织code、通知内容不为空
			if (StringUtils.isNotEmpty(orderHandleDto.getSalesDepartmentCode())
					&& StringUtils.isNotEmpty(noticeContent)) {
				InstationJobMsgEntity entity = new InstationJobMsgEntity();
				// UUID
				entity.setId(UUIDUtils.getUUID());
				// 发送方编码
				entity.setSenderCode(orderHandleDto.getOperatorCode());
				// 发送人名称
				entity.setSenderName(orderHandleDto.getOperator());
				// 发送方组织编码
				entity.setSenderOrgCode(orderHandleDto.getOperateOrgCode());
				// 接收方组织编码
				entity.setReceiveOrgCode(orderHandleDto
						.getSalesDepartmentCode());
				// 接收方类型
				entity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
				// 消息内容
				entity.setContext(noticeContent);
				// 站内消息类型
				entity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
				// 发送时间
				entity.setPostDate(new Date());
				// 消息状态
				entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
				messageService.createBatchInstationMsg(entity);
			}
		} catch (MessageException e) {
			throw new DispatchException(e.getErrorCode(), e);
		}
	}

	/**
	 * 业务日志
	 * 
	 * @param dispatchOrderId
	 * @param dispatchOrderNo
	 * @param ExceptionType
	 * @param exceptionReason
	 */
	public void insertLog(String dispatchOrderId, String dispatchOrderNo,
			String ExceptionType, String exceptionReason) {
		try {
			OrderAutoExceptionLogEntity orderAutoExceptionLogEntity = new OrderAutoExceptionLogEntity();
			orderAutoExceptionLogEntity.setId(UUIDUtils.getUUID());
			if (StringUtils.isEmpty(dispatchOrderId)) {
				orderAutoExceptionLogEntity.setDispatchOrderId(UUIDUtils
						.getUUID());
			} else {
				orderAutoExceptionLogEntity.setDispatchOrderId(dispatchOrderId);
			}
			if (StringUtils.isEmpty(dispatchOrderNo)) {
				orderAutoExceptionLogEntity.setOrderNo(UUIDUtils.getUUID());
			} else {
				orderAutoExceptionLogEntity.setOrderNo(dispatchOrderNo);
			}
			orderAutoExceptionLogEntity.setExceptionType(ExceptionType);
			orderAutoExceptionLogEntity.setExceptionReason(exceptionReason);
			orderAutoExceptionLogEntity.setCreateTime(new Date());
			orderAutoExceptionLogService
					.insertAutoOrderExceptionLog(orderAutoExceptionLogEntity);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void outOfOrderPool(Object obj) {
		// zxy 20140723 AUTO-197 start 修改:对象转换问题
		LOGGER.info("AutoOrderHandleService【线程池满异常处理开始。。。。。。】");
		DispatchOrderEntity dispatchOrderEntity = (DispatchOrderEntity) obj;
		if (null != dispatchOrderEntity) {
			insertLog(dispatchOrderEntity.getOrderNo(),
					dispatchOrderEntity.getOrderNo(), "outOfOrderPool", "线程池满");
			DispatchOrderChangeEntity entity = new DispatchOrderChangeEntity();
			entity.setChangeId(dispatchOrderEntity.getId());
			entity.setJobId(DispatchOrderStatusConstants.CHANGE_ORDER_NO_QUERY); // zxy
																					// 20140723
			dispatchOrderChangeEntityDao.updateChangebyOrderId(entity);
		}
		LOGGER.info("AutoOrderHandleService【线程池满异常处理结束。。。。。。】");
		// zxy 20140723 AUTO-197 end 修改:对象转换问题
	}

	@Override
	public int getActiveThreads() {
		return SettlementReportNumber.TEN;
	}

	public void setSpecialPickupAddressService(
			ISpecialPickupAddressService specialPickupAddressService) {
		this.specialPickupAddressService = specialPickupAddressService;
	}

	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	public void setTruckSchedulingTaskService(
			ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	public void setRegionalVehicleService(
			IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}

	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}

	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	public void setDispatchOrderActionHistoryEntityDao(
			IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao) {
		this.dispatchOrderActionHistoryEntityDao = dispatchOrderActionHistoryEntityDao;
	}

	public void setDispatchVehicleRecordEntityDao(
			IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao) {
		this.dispatchVehicleRecordEntityDao = dispatchVehicleRecordEntityDao;
	}

	public void setVehicleManageService(
			IVehicleManageService vehicleManageService) {
		this.vehicleManageService = vehicleManageService;
	}

	public void setOrderVehicleService(IOrderVehicleService orderVehicleService) {
		this.orderVehicleService = orderVehicleService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setTruckResourceDao(ITruckResourceDao truckResourceDao) {
		this.truckResourceDao = truckResourceDao;
	}

	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setExpressWorkerStatusDao(
			IExpressWorkerStatusDao expressWorkerStatusDao) {
		this.expressWorkerStatusDao = expressWorkerStatusDao;
	}

	public void setOrderAutoExceptionLogService(
			IOrderAutoExceptionLogService orderAutoExceptionLogService) {
		this.orderAutoExceptionLogService = orderAutoExceptionLogService;
	}

	public void setPropertyFactory(PropertyFactory propertyFactory) {
		this.propertyFactory = propertyFactory;
	}

	public void setDispatchOrderChangeEntityDao(
			IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
	}

	public void setGPSOrderTaskService(IGPSOrderTaskService gPSOrderTaskService) {
		GPSOrderTaskService = gPSOrderTaskService;
	}

	public void setAppOrderJMSService(IAppOrderJMSService appOrderJMSService) {
		this.appOrderJMSService = appOrderJMSService;
	}
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}
}

package com.deppon.foss.module.pickup.order.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressVehiclesDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderActionHistoryEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.ITruckResourceDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAppOrderJMSService;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressAcceptService;
import com.deppon.foss.module.pickup.order.api.server.service.IGPSOrderTaskService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderAutoExceptionLogService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderForwardRecordService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderPdaReturnRecordService;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderReportService;
import com.deppon.foss.module.pickup.order.api.server.service.IRegionCourierReportService;
import com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderActionHistoryEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderForwardRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.RegionCourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.AppOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaReturnDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleInfoDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.shared.define.OrderConstant;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.ows.inteface.domain.orderstate.OrderStateRequest;

/**
 * 
 * @ClassName: AutoExpressAcceptService 
 * @Description: 自动处理-受理订单service 
 * @author YANGBIN
 * @date 2014-5-7 下午1:51:09 
 *
 */
public class AutoExpressAcceptService implements IAutoExpressAcceptService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoExpressAcceptService.class);
	/**
	 * 调度订单DAO
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	/**
	 * 派车记录DAO
	 */
	private IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao;
	/**
	 * 订单操作历史DAO
	 */
	private IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao;
	/**
	 * 车辆管理Service
	 */
	private IVehicleManageService vehicleManageService;
	
	/**
	 * 客户相关
	 */
	private ICustomerDao customerDao;
	
	/**
	 * 综合的营业部服务Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
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
	 * 中转约车服务
	 */
	private IOrderVehicleService orderVehicleService;

	/**
	 * 车辆服务
	 */
	private ITruckResourceDao truckResourceDao;
	
	/**
	 * 快递车牌查询公共选择器Service接口
	 */
	private ICommonExpressVehicleService commonExpressVehicleService;
	
	/**
	 * 变更表dao
	 */
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	/**
	 * 快递每日统计记录表
	 */
	private IOrderReportService orderReportService;
	
	private IOrderPdaReturnRecordService orderPdaReturnRecordService;
	
	private IOrderForwardRecordService orderForwardRecordService;
	
	private IPdaSignEntityDao pdaSignEntityDao;
	
	private IOrderAutoExceptionLogService orderAutoExceptionLogService;
	
	private IEmployeeService employeeService;
	
	private IRegionCourierReportService regionCourierReportService;
	
	private IAppOrderJMSService appOrderJMSService;
	/**
	 * GPS服务
	 */
	private IGPSOrderTaskService GPSOrderTaskService;
	
	private IConfigurationParamsService configurationParamsService;
		
	private IWaybillSignResultService waybillSignResultService;
	
	private IExpressVehiclesDao expressVehiclesDao;
	
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;
	
	private ICustomerService customerService;
	
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * 快递订单自动受理
	 */
	@Transactional(propagation =Propagation.REQUIRES_NEW)
	@Override
	public boolean acceptOrder(OrderHandleDto orderHandleDto) {
		boolean success = false;
		try{
			//判断接货订单在CRM中是否存在
			/*if (DispatchOrderStatusConstants.TYPE_PICKUP_ORDER.equals(orderHandleDto.getOrderType())
					&& crmOrderService.searchOrder(orderHandleDto.getOrderNo()) == false) {
				String exceptionType = "AUTO_ACCEPT_ERROR";
				String reason =orderHandleDto.getOrderNo()+"CRM中不存在";	
				LOGGER.info(reason);	
				addExceptionLog(orderHandleDto,exceptionType,reason);	
				return false;
			}*/
			//14.8.12 gcl AUTO-224
			orderHandleDto.setIsCustomer("Y");
			orderHandleDto.setIsSms("Y");
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
				/*try {
					//受理订单的时候向电子运单订单表添加数据
					List<PreHandEWaybillOrderEntity> prehandList = preHandEWaybillOrderDao.selectPreEWaybillOrderByOrderNo(orderHandleDto.getOrderNo());
					boolean isNeedInsertOrUpdate = true;
					if(CollectionUtils.isNotEmpty(prehandList)){
						for(PreHandEWaybillOrderEntity preHand : prehandList){
							//如果处理不成功,则需要进行数据的插入
							if(FossConstants.YES.equals(preHand.getStatus())){
								isNeedInsertOrUpdate = false;
							}else{
								LOGGER.info("电子运单不需要添加电子运单订单表！订单号"+orderHandleDto.getOrderNo());
							}
						}
					}
					//是否需要进行数据的更新或者插入
					if(isNeedInsertOrUpdate){
						orderTaskHandleService.addEWaybilllOrder(orderHandleDto.getOrderNo());
						LOGGER.info("电子运单添加结束！订单号"+orderHandleDto.getOrderNo());
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}*/
				
				 // 发送短信
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
							addExceptionLog(orderHandleDto, exceptionType, reason);
							throw new DispatchException("发送短信异常，无法继续操作！");
						}
				}else {
					String exceptionType = "SMS_SEND_ERROR";
					String reason = "短信功能关闭，短信发送失败！";	
					addExceptionLog(orderHandleDto, exceptionType, reason);
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
				
				// 同步约车信息给GPS
				try{
					LOGGER.info("同步约车信息给GPS开始！订单号"+orderHandleDto.getOrderNo());
					sendToGPS(orderHandleDto,1);
					LOGGER.info("同步约车信息给GPS结束！订单号"+orderHandleDto.getOrderNo());
				}catch(Exception e){
					String exceptionType = "UPDATE_GPS_ERROR";
					String reason = "同步约车信息报错";	
					addExceptionLog(orderHandleDto,exceptionType,reason);	
				}
				
				//推送订单信息给APP
				try{
					LOGGER.info("推送订单信息给APP开始！订单号"+orderHandleDto.getOrderNo());
					sendEmpCodeToApp(orderHandleDto);
					LOGGER.info("推送订单信息给APP结束！订单号"+orderHandleDto.getOrderNo());
				}catch(Exception e){
					String exceptionType = "UPDATE_APP_ERROR";
					String reason = "推送订单信息给APP报错";	
					addExceptionLog(orderHandleDto,exceptionType,reason);	
				}
				
				//推送订单信息给微信
				try{
					LOGGER.info("推送订单信息给微信开始！订单号"+orderHandleDto.getOrderNo());
					sendOrderStateToApp(orderHandleDto);
					LOGGER.info("推送订单信息给微信结束！订单号"+orderHandleDto.getOrderNo());
				}catch(Exception e){
					String exceptionType = "UPDATE_WEIXIN_ERROR";
					String reason = "推送订单信息给微信报错";	
					LOGGER.info(orderHandleDto.getOrderNo()+":"+e.getMessage());
					addExceptionLog(orderHandleDto,exceptionType,reason);	
				}
				
				try{
					// 新增统计报表
					addOrderReport(orderHandleDto);
					addRegionCourierReport(orderHandleDto);
					String orderId = orderHandleDto.getId();
					// 删除预计处理单
					dispatchOrderChangeEntityDao.deleteChangeByOrderID(orderId);
				}catch(Exception e){
					String exceptionType = "AUTO_ACCEPT_ERROR";
					String reason = "新增统计报表、删除预计处理单";	
					LOGGER.info(orderHandleDto.getOrderNo()+":"+e.getMessage());
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
	 * 约车信息受理时同步快递员信息给APP
	 * @author 043258-foss-zhaobin
	 * @date 2014-10-29 下午4:54:49
	 */
	private void sendEmpCodeToApp(OrderHandleDto orderHandleDto){
		AppOrderDto appOrderDto = new AppOrderDto();
		appOrderDto.setOrderNo(orderHandleDto.getOrderNo());
		appOrderDto.setDriverCode(orderHandleDto.getDriverCode());
		appOrderDto.setPosterMobilePhone(orderHandleDto.getCustomerMobile());
		appOrderDto.setProductCode(orderHandleDto.getProductCode());
		appOrderDto.setStatus(orderHandleDto.getOrderStatus());
		appOrderJMSService.sendOrderState(appOrderDto);
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
	 * DMANA-8883 微信订单状态推送需求 将FOSS中的“已派车”状态推送至APP
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
		// 出发城市
		request.setLeaveCity(orderHandleDto.getPickupCity());
		// 接单时间
		request.setOrderTime(orderHandleDto.getOwsPickupTime());
		// 快递员工号
		request.setCourierNumber(orderHandleDto.getDriverCode());
		// 快递员姓名
		request.setCourierName(orderHandleDto.getDriverName());
		// 快递员电话
		request.setCourierTel(orderHandleDto.getDriverMobile());
		
		appOrderJMSService.sendOrderStateToApp(request);
	}
	
	/**
	 * 
	 * @Title: addVehicleWV 
	 * @Description: 更新车载信息
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
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
		crmModifyInfoDto.setDriverName(orderHandleDto.getDriverName() + "-" + orderHandleDto.getDriverCode());
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
		//根据快递员工号查询快递车辆信息
		ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity();
		expressVehiclesEntity.setActive(FossConstants.ACTIVE);
		expressVehiclesEntity.setEmpCode(orderHandleDto.getDriverCode());
		List<ExpressVehiclesEntity> expressVehiclesResult = expressVehiclesDao.queryInfos(expressVehiclesEntity, 1, 0);
		if(CollectionUtils.isNotEmpty(expressVehiclesResult)) {
			String billingOrgCode = expressVehiclesResult.get(0).getOrgCode();
			if(StringUtils.isNotBlank(billingOrgCode)) {
				//设置开单部门编码
				crmModifyInfoDto.setBillingOrgCode(expressVehiclesResult.get(0).getOrgCode());
				OrgAdministrativeInfoEntity  orgInfoEntity = orgAdministrativeInfoDao.queryOrgAdministrativeInfoByCode(billingOrgCode);
				if(orgInfoEntity!=null) {
					//设置开单部门电话
					crmModifyInfoDto.setBillingOrgPhone(orgInfoEntity.getDepTelephone());
				}
			}
		}
		// 调用CRM订单修改接口
		crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);		
		LOGGER.info("更新CRM订单成功");
	}
	
	/**
	 * 
	 * @Title: sendSmsByHandle 
	 * @Description: 快递自动发送短信
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void sendSmsByHandle(OrderHandleDto orderHandleDto) {
		
		try {
			// 发送短信给司机
			if (FossConstants.YES.equals(orderHandleDto.getIsSms())) {
                // 小件
				String smsCode = getSmsCodeForCourier(orderHandleDto.getDeliveryCustomerCode());
				sendSms(smsCode, orderHandleDto, true,NotifyCustomerConstants.BS_TYPE_PKP_ORDER);
            }
		} catch (NotifyCustomerException e) {
			LOGGER.info("司机短信发送失败");
			LOGGER.error(e.getMessage(), e);	
			throw new DispatchException("发送短信异常");
		} catch (SMSSendLogException e) {
			LOGGER.info("司机短信发送失败");
			LOGGER.error(e.getMessage(), e);
			throw new DispatchException("发送短信异常");
		} catch (BusinessException e) {
			LOGGER.info("短信模板获取失败");
			LOGGER.error(e.getMessage(), e);
			throw new DispatchException("发送短信异常");
		} catch (Exception e){
			LOGGER.info("司机短信发送失败");
			LOGGER.error(e.getMessage(), e);
			throw new DispatchException("发送短信异常");
		}
		//发送短信给客户时间节点改到PDA上点击受理按钮时发送
		/*try {
			// 发送短信给客户
			if (FossConstants.YES.equals(orderHandleDto.getIsCustomer())) {
				OwnTruckConditionDto conditionDto = new OwnTruckConditionDto();
				// 车牌号
				conditionDto.setVehicleNo(orderHandleDto.getVehicleNo());
				// 有效
				conditionDto.setActive(FossConstants.ACTIVE);
				// 获取车型
				VehicleInfoDto vehicleInfoDto = truckResourceDao.queryVehicleTypeByVehicleNo(conditionDto);
				// 设置车型
				orderHandleDto.setVehicleLengthName(vehicleInfoDto.getVehicleLengthName());

				// 小件，快递发短信 MANA-581
				boolean isSendpackageFlag=false;
				String customerMobile = orderHandleDto.getCustomerMobile();
				if(StringUtils.isNotEmpty(customerMobile)){
					String code=customerDao.queryCustCodeByCustMobile(customerMobile);
					if(StringUtils.isNotEmpty(code)){
						CustomerEntity customerEntity = customerDao.queryCustStateByCode(code);
						if(customerEntity!=null){
							String shipperSms = customerEntity.getShipperSms();
							if(StringUtils.isNotEmpty(shipperSms)&&(shipperSms.equals(OrderConstant.STOP_MESSAGE_FOR_DELIVER)||shipperSms.equals(OrderConstant.BATCH_MESSAGE_FOR_DELIVER))){
								isSendpackageFlag=false;//停发短信
							}else{
								isSendpackageFlag=true;//发送短信
							}
						}else{
							isSendpackageFlag=true;//发送短信
						}
					}else{
						isSendpackageFlag=true;//发送短信
					}
				}else{
					isSendpackageFlag=true;//发送短信
				}
				*//**###########更改  如果是快递的话 需要根据状态判断是否需要发送  临单不需要判断 都发送  begin############**//*  
				if(isSendpackageFlag){
					// 发送短信
					sendSms(DispatchOrderStatusConstants.EXPRESS_ORDER_ACCEPT_SMS, orderHandleDto, false,DispatchOrderStatusConstants.EXPRESS_ORDER_MODULE);
				}
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
		} catch (Exception e){
			LOGGER.info("客户短信发送失败");
			LOGGER.error(e.getMessage(), e);
		}*/
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
	 * @Title: sendSms 
	 * @Description: 发送短信 
	 * @param @param smsCode
	 * @param @param orderHandleDto
	 * @param @param isDriver
	 * @param @param moduleName    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void sendSms(String smsCode, OrderHandleDto orderHandleDto, boolean isDriver,String moduleName) {
		LOGGER.info("sendSms start : order " + orderHandleDto + " isDriver " + isDriver);
		NotificationEntity notificationEntity = new NotificationEntity();
		EmployeeEntity employeeEntity = new EmployeeEntity();
		// 设置订单号
		notificationEntity.setWaybillNo(orderHandleDto.getOrderNo());
		// 设置通知模块名  NotifyCustomerConstants.BS_TYPE_PKP_ORDER
		notificationEntity.setModuleName(moduleName);
		// 设置通知类型
		notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		// 设置通知内容
		notificationEntity.setNoticeContent(this.getSmsContent(smsCode, orderHandleDto));
		// 设置手机号
		if (isDriver) {
			notificationEntity.setMobile(orderHandleDto.getDriverMobile());
			notificationEntity.setConsignee(orderHandleDto.getDriverName());
		} else {
			notificationEntity.setMobile(orderHandleDto.getCustomerMobile());
			notificationEntity.setConsignee(orderHandleDto.getCustomerName());
		}
		// 设置发送对象  
		notificationEntity.setOperator("system");
		// 设置操作人编号
		notificationEntity.setOperatorNo("000000");
		// 设置操作部门名称
		notificationEntity.setOperateOrgName("system");
		//ON-1435 自动调度上线后，发送短信时费用承担部门全部为总裁办，与实际业务不符
		if(StringUtils.isNotEmpty(orderHandleDto.getDriverCode())){
			employeeEntity = employeeService.queryEmployeeByEmpCodeNoCache(orderHandleDto.getDriverCode());
			// 设置操作部门编码
			notificationEntity.setOperateOrgCode(employeeEntity.getOrgCode());
		}else{
			// 设置操作部门编码
			notificationEntity.setOperateOrgCode("W01");
		}
		// 设置操作时间
		notificationEntity.setOperateTime(new Date());
		notifyCustomerService.sendMessage(notificationEntity);
	}
	
	/**
	 * 
	* @Title: getSmsContent 
	* @Description: 获得短信内容
	* @param @param smsCode
	* @param @param orderHandleDto
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private String getSmsContent(String smsCode, OrderHandleDto orderHandleDto) {
		String sms = "";
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		// 部门编码   
		smsParamDto.setOrgCode("");
		// 参数Map
		smsParamDto.setMap(this.getSmsParam(orderHandleDto));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
		if (sms == null) {
			//短信模板为空,新增处理异常日志
		}
		return sms;
	}
	
	/**
	 * 
	* @Title: getSmsParam 
	* @Description: 设置短信模板数据
	* @param @param orderHandleDto
	* @param @return    设定文件 
	* @return Map<String,String>    返回类型 
	* @throws
	 */
	private Map<String, String> getSmsParam(OrderHandleDto orderHandleDto) {
		Map<String, String> paramMap = new HashMap<String, String>();
		// 订单号
		paramMap.put("orderNo", orderHandleDto.getOrderNo());
		// 营业部名称
		paramMap.put("salesDepartmentName", orderHandleDto.getSalesDepartmentName());
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
	 * @Title: addOrderReport 
	 * @Description: 增加快递每日报表记录 
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addOrderReport(OrderHandleDto orderHandleDto){
		OrderReportEntity orderReportEntity = new OrderReportEntity();
		orderReportEntity.setId(UUIDUtils.getUUID());
		orderReportEntity.setOrderNo(orderHandleDto.getOrderNo());//gcl 2014.7.9 AUTO-113 保存当日报表 没有设置订单号
		orderReportEntity.setDispatchOrderId(orderHandleDto.getId());
		orderReportEntity.setPickupAddress(orderHandleDto.getPickupAddress());
		orderReportEntity.setExpressDriverCode(orderHandleDto.getDriverCode());
		orderReportEntity.setExpressDriverName(orderHandleDto.getDriverName());
		orderReportEntity.setExpressDriverPhone(orderHandleDto.getDriverMobile());
		orderReportEntity.setVehicleNo(orderHandleDto.getVehicleNo());
		orderReportEntity.setPickupRegionCode(orderHandleDto.getPickupRegionCode());
		orderReportEntity.setPickupRegionName(orderHandleDto.getPickupRegionName());
		orderReportService.insertOrderReport(orderReportEntity);
	};
	/**
	 * 
	 * @Title: addRegionCourierReport 
	 * @Description: 增加快递每日报表记录 
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addRegionCourierReport(OrderHandleDto orderHandleDto){
		RegionCourierReportEntity regionCourierReportEntity = new RegionCourierReportEntity();
		regionCourierReportEntity.setCourierCode(orderHandleDto.getDriverCode());
		regionCourierReportEntity.setRegionCode(orderHandleDto.getPickupRegionCode());
		regionCourierReportEntity.setRecieveOrders(new BigDecimal(1));
		regionCourierReportService.insert(regionCourierReportEntity);
	}
	
	/**
	 * 
	 * @Title: pdaReturnProcess 
	 * @Description: 快递PDA退回处理 (来不及接货)
	 * @param @param map
	 * @param @param orderEntity    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Override
	public void pdaReturnProcess(PdaReturnDto pdaReturnDto) {
		try{
			//传入参数为空
			if(null == pdaReturnDto){
				LOGGER.error("PDA快递退回参数为空");
				return;
			}
			LOGGER.error("快递订单号："+pdaReturnDto.getOrderNo()+"执行"+pdaReturnDto.getOptState()+"开始");
            //gcl 14.7.10 AUTO-118 只要是退回 都需添加退回记录表 数据
			String orderId = pdaReturnDto.getId();
			//判断订单ID是否为空
			if(StringUtils.isEmpty(orderId)){
				LOGGER.error("PDA快递退回，传入订单ID为空");
				return;
			}
			//根据订单ID查询是否存在订单
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrderById(orderId);
			if(null == dispatchOrderEntity){
				LOGGER.error("PDA快递退回，根据ID："+orderId+"无法找到对应订单");
				return;
			}
			//如果不是快递
//			if(!DispatchOrderStatusConstants.EXPRESS_PRODUCT_CODE
//					.equals(dispatchOrderEntity.getProductCode())){
//				LOGGER.error("PDA快递退回，根据ID："+orderId+"判断为非快递订单");
//				return;
//			}
			addReturnRecord(pdaReturnDto,dispatchOrderEntity);
			//14.8.2 gcl 退回订单后 退回人接收订单数量减一 
			addRegionCourierReportDel(pdaReturnDto);
			
			LOGGER.info("--收件联系不上客户"+ ReflectionToStringBuilder.toString(pdaReturnDto));//记录日志
			//判断原因是否是来不及接货
			if(pdaReturnDto.getReturnReason()
					.equals(DispatchOrderStatusConstants.REJECT_NO_TIME_PICKUP)){/*
				//查询得到预处理ID
				String orderChangeId = dispatchOrderChangeEntityDao.queryChanageIdByOrder(orderId);
				if(StringUtils.isEmpty(orderChangeId)){
					//新增一条记录预处理变更表记录
					DispatchOrderChangeEntity dispatchOrderChangeEntity = new DispatchOrderChangeEntity();
					// id
					dispatchOrderChangeEntity.setId(UUIDUtils.getUUID());
					// 变更id
					dispatchOrderChangeEntity.setChangeId(dispatchOrderEntity.getId());
					// 变更时间
					dispatchOrderChangeEntity.setChangeTime(new Date());
					// 设置接货城市
					dispatchOrderChangeEntity.setCityCode(dispatchOrderEntity.getPickupCityCode());
					// 设置产品类型
					dispatchOrderChangeEntity.setProductCode(dispatchOrderEntity.getProductCode());
					dispatchOrderChangeEntity.setJobId(DispatchOrderStatusConstants.CHANGE_ORDER_NO_QUERY);
					dispatchOrderChangeEntityDao.insertChange(dispatchOrderChangeEntity);
				}else {
					//更新
					DispatchOrderChangeEntity changeEntity = new DispatchOrderChangeEntity();
					changeEntity.setId(orderChangeId);
					changeEntity.setJobId(DispatchOrderStatusConstants.CHANGE_ORDER_NO_QUERY);
					dispatchOrderChangeEntityDao.updateChangebyOrderId(changeEntity);
				}
			*/} else if(pdaReturnDto.getReturnReason().equals(DispatchOrderStatusConstants.REJECT_CONTACT_NO_CUSTOMER)){
				if(null != dispatchOrderEntity){
					
					UserEntity user = new UserEntity();
					OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
					EmployeeEntity employ = new EmployeeEntity();
					
					employ.setEmpCode(pdaReturnDto.getDriverCode());
					employ.setEmpName(pdaReturnDto.getDriverName());
					
					user.setEmployee(employ);
					
					dept.setCode(dispatchOrderEntity.getSalesDepartmentCode());
					dept.setName(dispatchOrderEntity.getSalesDepartmentName());
					
					CurrentInfo currentInfo = new CurrentInfo(user,dept);// 获取当前用户
					
					LOGGER.info("取件联系不上客户退回-当前用户信息:" + ReflectionToStringBuilder.toString(currentInfo));
					boolean toDeliverResult = false; //给发货人发送短信返回结果
					//调用短信接口发送短信至客户
					toDeliverResult = waybillSignResultService.sendMessCustomer(null,pdaReturnDto, null,dispatchOrderEntity, currentInfo,false);
					
					if(toDeliverResult){
						LOGGER.info("--收件短信，在线通知发送成功");//记录日志
					}else{
						LOGGER.info("--收件短信，在线通知发送失败");//记录日志
					}
				}
			}
		}catch(Exception e){
			LOGGER.error("PDA快递退回抛出异常"+e.getMessage());
			return;
		}
		
	}
	/**
	 * 
	 * @Title: addRegionCourierReport 
	 * @Description: 增加快递每日报表记录 
	 * @param @param orderHandleDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addRegionCourierReportDel(PdaReturnDto pdaReturnDto){
		RegionCourierReportEntity regionCourierReportEntity = new RegionCourierReportEntity();
		regionCourierReportEntity.setCourierCode(pdaReturnDto.getDriverCode());
//		regionCourierReportEntity.setRegionCode(orderHandleDto.getPickupRegionCode());
		regionCourierReportEntity.setRecieveOrders(new BigDecimal(-1));
		regionCourierReportService.insert(regionCourierReportEntity);
	}
	/**
	 * 
	 * @Title: pdaForwardProcess 
	 * @Description: 快递PDA转发处理
	 * @param @param pdaReturnDto    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	@Override
	@Transactional
	public void pdaForwardProcess(PdaReturnDto pdaReturnDto) {
		try{
			//传入参数为空
			if(null == pdaReturnDto){
				LOGGER.error("PDA转发操作时，传入的实体参数为空");
				throw new PdaProcessException("PDA转发操作时，传入的实体参数为空");
			}
			String orderId = pdaReturnDto.getId();
			//判断订单ID是否为空
			if(StringUtils.isEmpty(orderId)){
				LOGGER.error("PDA转发操作时，传入的订单ID为空");
				throw new PdaProcessException("PDA转发操作时，传入的订单ID为空");
			}
			//根据订单ID查询是否存在订单
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrderById(orderId);
			if(null == dispatchOrderEntity){
				LOGGER.error("PDA转发操作时，传入的订单ID不存在！");
				throw new PdaProcessException("PDA转发操作时，传入的订单ID不存在！");
			}
			if(StringUtils.isEmpty(pdaReturnDto.getForwardDriverCode())){
				LOGGER.error("PDA转发操作时，传入的转发人工号为空！");
				throw new PdaProcessException("PDA转发操作时，传入的转发人工号为空！");
			}
			String driverName = employeeService.queryEmpNameByEmpCode(pdaReturnDto.getDriverCode());
			pdaReturnDto.setDriverName(driverName);
			//新增退回记录
			addReturnRecord(pdaReturnDto,dispatchOrderEntity);
			//14.8.1 gcl 退回订单后 退回人接收订单数量减一
			addRegionCourierReportDel(pdaReturnDto);
			//新增转发记录
			addForwardRecord(pdaReturnDto,dispatchOrderEntity);
			//获取订单处理对象
			OrderHandleDto orderHandleDto = getOrderHandleDto(dispatchOrderEntity,pdaReturnDto);
			//进行自动受理
			this.acceptOrder(orderHandleDto);
		}catch(Exception e){
			LOGGER.error("PDA快递转发抛出异常"+e.getMessage());
			return;
		}
		
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
		orderPdaReturnRecordEntity.setOperateType(returnDto.getOptState());
		orderPdaReturnRecordEntity.setOrderStatus(returnDto.getOrderStatus());
		orderPdaReturnRecordEntity.setReturnType(returnDto.getReturnReason());
		orderPdaReturnRecordEntity.setReturnRemark(returnDto.getRemark());
		StringBuffer pickupAddress = new StringBuffer();
		pickupAddress.append(dispatchOrderEntity.getPickupProvince());
		pickupAddress.append(dispatchOrderEntity.getPickupCity());
		pickupAddress.append(dispatchOrderEntity.getPickupCounty());
		pickupAddress.append(dispatchOrderEntity.getPickupElseAddress());
		orderPdaReturnRecordEntity.setPickupAddress(pickupAddress.toString());
		
		orderPdaReturnRecordEntity.setOperateTime(new Date()); ////14.7.11 gcl AUTO-115 退回时 记录操作时间  orderPdaReturnRecordEntity表中没有createdate
		orderPdaReturnRecordEntity.setId(UUIDUtils.getUUID());
		orderPdaReturnRecordEntity.setCreateDate(new Date());
		orderPdaReturnRecordEntity.setCreateCode(returnDto.getDriverCode());//14.7.11 gcl AUTO-147退回时 记录当前退回人
		orderPdaReturnRecordEntity.setCreateName(returnDto.getDriverName());
		orderPdaReturnRecordEntity.setProductCode(dispatchOrderEntity.getProductCode());
		orderPdaReturnRecordService.insertPdaReturnRecord(orderPdaReturnRecordEntity);
	}
	
	/**
	 * 
	 * @Title: addForwardRecord 
	 * @Description: 新增转发记录
	 * @param @param returnDto
	 * @param @param dispatchOrderEntity    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void addForwardRecord(PdaReturnDto returnDto,DispatchOrderEntity dispatchOrderEntity){
		OrderForwardRecordEntity orderForwardRecordEntity = new OrderForwardRecordEntity();
		orderForwardRecordEntity.setId(UUIDUtils.getUUID());
		orderForwardRecordEntity.setDispatchOrderId(dispatchOrderEntity.getId());
		orderForwardRecordEntity.setOrderNo(dispatchOrderEntity.getOrderNo());
		orderForwardRecordEntity.setExpressDriverCode(returnDto.getDriverCode());
		orderForwardRecordEntity.setExpressDriverName(returnDto.getDriverName());
		orderForwardRecordEntity.setOperateTime(new Date());
		orderForwardRecordService.insertOrderForward(orderForwardRecordEntity);
	}
	
	private OrderHandleDto getOrderHandleDto(
			DispatchOrderEntity dispatchOrderEntity, PdaReturnDto pdaReturnDto) {
		OrderHandleDto orderHandleDto = new OrderHandleDto();
		orderHandleDto.setId(dispatchOrderEntity.getId());
		orderHandleDto.setOrderNo(dispatchOrderEntity.getOrderNo());
		orderHandleDto.setOrderType(dispatchOrderEntity.getOrderType());
		orderHandleDto.setOriginOrderStatus(dispatchOrderEntity
				.getOrderStatus());
		orderHandleDto.setIsCustomer(dispatchOrderEntity.getIsCustomer());
		orderHandleDto.setIsPda(dispatchOrderEntity.getIsPda());
		orderHandleDto.setIsSms(dispatchOrderEntity.getIsSms());

		// 匹配司机，转发司机
		String driverCode = pdaReturnDto.getForwardDriverCode();
		String driverName = pdaReturnDto.getForwardDriverName();
		OwnTruckDto ownTruckDto = getExpressOwnTruckDto(driverCode);
		if(null != ownTruckDto){
			String vehicleNo = ownTruckDto.getVehicleNo();
			String driverMobile = ownTruckDto.getDriverMobile();
			orderHandleDto.setVehicleNo(vehicleNo);
			orderHandleDto.setDriverMobile(driverMobile);
		}
		orderHandleDto.setDriverCode(driverCode);
		orderHandleDto.setDriverName(driverName);
		orderHandleDto.setPickupRegionCode(dispatchOrderEntity.getSmallZoneCodeSuggested());
		orderHandleDto.setPickupRegionName(dispatchOrderEntity.getSmallZoneNameSuggested());
		orderHandleDto.setWeight(dispatchOrderEntity.getWeight());
		orderHandleDto.setVolume(dispatchOrderEntity.getVolume());
		orderHandleDto.setCustomerMobile(dispatchOrderEntity.getMobile());
		orderHandleDto.setTel(dispatchOrderEntity.getTel());
		orderHandleDto.setCustomerName(dispatchOrderEntity.getCustomerName());
		orderHandleDto.setPickupCity(dispatchOrderEntity.getPickupCity());
		StringBuffer pickupAddress = new StringBuffer();
		pickupAddress.append(dispatchOrderEntity.getPickupProvince());
		pickupAddress.append(dispatchOrderEntity.getPickupCity());
		pickupAddress.append(dispatchOrderEntity.getPickupCounty());
		pickupAddress.append(dispatchOrderEntity.getPickupElseAddress());
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
		orderHandleDto.setGoodsQty(dispatchOrderEntity.getGoodsQty());
		orderHandleDto.setGoodsPackage(dispatchOrderEntity.getGoodsPackage());
		orderHandleDto.setProductCode(dispatchOrderEntity.getProductCode());
		return orderHandleDto;
	}
	
	/**
	 * 判断发货人是否为违禁品黑名单，选择不同的短信模板 
	 * @author 219396 FOSS-CHENGDAOLIN  15-07-2
	 */
	private String getSmsCodeForCourier(String cusCode) {
		if(StringUtils.isNotBlank(cusCode)) {
			//按发货人客户编码查询
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setActive(FossConstants.YES);
			customerEntity.setCusCode(cusCode);
			CustomerEntity ce = customerService.queryCustInfoByCustomerEntity(customerEntity);
			if(ce!=null&&FossConstants.CUSTOMER_CONTRABAND.equals(ce.getBlackListCategory()))
				return DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_COURIER_SMS_CONTRABAND;
		}
		return DispatchOrderStatusConstants.TEMPLATE_PICKUP_ORDER_TO_COURIER_SMS;
	}
	
	private OwnTruckDto getExpressOwnTruckDto(String driverCode){
		OwnTruckDto ownTruckDto = null;
		ownTruckDto = pdaSignEntityDao.queryExpressVehicleNoByCourier(driverCode);
		return ownTruckDto;
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
	public void addExceptionLog(OrderHandleDto orderHandleDto,
		String excptionType,String reason){
		// 如果待分配的快递员为空，则新增日常日志，同时转为人工处理
		OrderAutoExceptionLogEntity orderAutoExceptionLogEntity = new OrderAutoExceptionLogEntity();
		orderAutoExceptionLogEntity.setId(UUIDUtils
				.getUUID());
		orderAutoExceptionLogEntity
				.setDispatchOrderId(orderHandleDto.getId());
		orderAutoExceptionLogEntity
				.setOrderNo(orderHandleDto
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
	
	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	public void setDispatchVehicleRecordEntityDao(
			IDispatchVehicleRecordEntityDao dispatchVehicleRecordEntityDao) {
		this.dispatchVehicleRecordEntityDao = dispatchVehicleRecordEntityDao;
	}

	public void setDispatchOrderActionHistoryEntityDao(
			IDispatchOrderActionHistoryEntityDao dispatchOrderActionHistoryEntityDao) {
		this.dispatchOrderActionHistoryEntityDao = dispatchOrderActionHistoryEntityDao;
	}

	public void setVehicleManageService(IVehicleManageService vehicleManageService) {
		this.vehicleManageService = vehicleManageService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
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

	public void setOrderVehicleService(IOrderVehicleService orderVehicleService) {
		this.orderVehicleService = orderVehicleService;
	}

	public void setTruckResourceDao(ITruckResourceDao truckResourceDao) {
		this.truckResourceDao = truckResourceDao;
	}

	public void setCommonExpressVehicleService(
			ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
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

	public void setOrderForwardRecordService(
			IOrderForwardRecordService orderForwardRecordService) {
		this.orderForwardRecordService = orderForwardRecordService;
	}

	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	public IOrderAutoExceptionLogService getOrderAutoExceptionLogService() {
		return orderAutoExceptionLogService;
	}

	public void setOrderAutoExceptionLogService(
			IOrderAutoExceptionLogService orderAutoExceptionLogService) {
		this.orderAutoExceptionLogService = orderAutoExceptionLogService;
	}

	public IDispatchOrderEntityDao getDispatchOrderEntityDao() {
		return dispatchOrderEntityDao;
	}

	public IDispatchVehicleRecordEntityDao getDispatchVehicleRecordEntityDao() {
		return dispatchVehicleRecordEntityDao;
	}

	public IDispatchOrderActionHistoryEntityDao getDispatchOrderActionHistoryEntityDao() {
		return dispatchOrderActionHistoryEntityDao;
	}

	public IVehicleManageService getVehicleManageService() {
		return vehicleManageService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public ICrmOrderJMSService getCrmOrderJMSService() {
		return crmOrderJMSService;
	}

	public ICrmOrderService getCrmOrderService() {
		return crmOrderService;
	}

	public ISMSTempleteService getsMSTempleteService() {
		return sMSTempleteService;
	}

	public INotifyCustomerService getNotifyCustomerService() {
		return notifyCustomerService;
	}

	public IOrderVehicleService getOrderVehicleService() {
		return orderVehicleService;
	}

	public ITruckResourceDao getTruckResourceDao() {
		return truckResourceDao;
	}

	public ICommonExpressVehicleService getCommonExpressVehicleService() {
		return commonExpressVehicleService;
	}

	public IDispatchOrderChangeEntityDao getDispatchOrderChangeEntityDao() {
		return dispatchOrderChangeEntityDao;
	}

	public IOrderReportService getOrderReportService() {
		return orderReportService;
	}

	public IOrderPdaReturnRecordService getOrderPdaReturnRecordService() {
		return orderPdaReturnRecordService;
	}

	public IOrderForwardRecordService getOrderForwardRecordService() {
		return orderForwardRecordService;
	}

	public IPdaSignEntityDao getPdaSignEntityDao() {
		return pdaSignEntityDao;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setRegionCourierReportService(
			IRegionCourierReportService regionCourierReportService) {
		this.regionCourierReportService = regionCourierReportService;
	}

	public void setGPSOrderTaskService(IGPSOrderTaskService gPSOrderTaskService) {
		GPSOrderTaskService = gPSOrderTaskService;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setAppOrderJMSService(IAppOrderJMSService appOrderJMSService) {
		this.appOrderJMSService = appOrderJMSService;
	}
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param orgAdministrativeInfoDao the orgAdministrativeInfoDao to set
	 */
	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}

	/**
	 * @param expressVehiclesDao the expressVehiclesDao to set
	 */
	public void setExpressVehiclesDao(IExpressVehiclesDao expressVehiclesDao) {
		this.expressVehiclesDao = expressVehiclesDao;
	}

	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	
	
	
	/**
	 * 获取短信信息
	 * @author foss-sunyanfei
	 * @date 2015-7-14 上午10:14:13
	 * @param empCode
	 * @return
	 */
	//根据部门code，获取快递员所在组织架构部门(营业部和快递点部层级)。
//	private OrgAdministrativeInfoEntity getDeptEntity(String deptCode){
//		LOGGER.info("（订单）---获取快递员所在组织架构部门");//记录日志
////		EmployeeEntity employ = new EmployeeEntity();
//		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
//		//判断快递员是否为空，若为空则无法根据快递员所属部门查询快递点部
//		if(StringUtils.isNotEmpty(deptCode)){
//			LOGGER.info("---------- 派送拉回调用接口：设置快递员及快递点信息----------");//记录日志
////			//快递员编码查询所属部门
////			employ = employeeService.queryEmployeeByEmpCode(StringUtil.defaultIfNull(empCode));
////			OrgAdministrativeInfoEntity department = employ.getDepartment();
////			if(null != department){
////				//判断是否是快递点部
////				if(FossConstants.YES.equals(department.getExpressPart())){
////					// 快递点部CODE
////					employ.setOrgCode(department.getCode());
////					// 快递点部名称
////					employ.setOrgName(department.getName());
////				}
////
////			}
//			//部门名称
////			String deptName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(deptCode);
////			if(emp != null){
////				user.setEmployee(emp);//得到员工信息
////			}else {
////				emp = new EmployeeEntity();
////				emp.setEmpName(PdaConstants.DEFAULT_EMP_NAME_PDA);//员工名称
////				emp.setEmpCode(dto.getDriverCode());//员工编码
////				user.setEmployee(emp);//得到员工信息
////			}
//			//如果部门名称不为空.
////			if(StringUtils.isNotBlank(deptName)){
////				dept.setName(deptName);// 得到部门名称
////			}else {//其他
////				dept.setName(PdaConstants.DEFAULT_EMP_NAME_PDA);//默认的部门名称
////			}
////			//部门编码
////			dept.setCode(deptCode);
//		}
//		LOGGER.info("--快递员所在组织架构部门 " + ReflectionToStringBuilder.toString(dept));
//		return dept;
//	}
	
}

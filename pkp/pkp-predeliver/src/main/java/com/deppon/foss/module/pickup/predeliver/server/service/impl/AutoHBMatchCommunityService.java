package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.tools.ant.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialDeliveryAddressService;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialDeliveryAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillExceptionLogService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.HandoverBillExceptionConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.HandoverBillExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.tools.DeliverTheadPool;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.PropertyValues;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;

/**
 * @ClassName: AutoHBMatchCommunityService
 * @Description: 交单自动匹配小区和车辆
 * @author 237982-foss-fangwenjun
 * @date 2015-5-9 下午2:31:55
 * 
 */
public class AutoHBMatchCommunityService extends DeliverTheadPool {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoHBMatchCommunityService.class);

	/**
	 * 配置
	 */
	private PropertyValues propertyValues;

	/**
	 * 交单
	 */
	private IHandoverBillVoService handoverBillVoService;

	/**
	 * 特殊送货地址Service
	 */
	private ISpecialDeliveryAddressService specialDeliveryAddressService;

	/**
	 * 定人定区service
	 */
	private IRegionalVehicleService regionalVehicleService;

	/**
	 * 排班任务Service
	 */
	private ITruckSchedulingTaskService truckSchedulingTaskService;

	/**
	 * 交单自动匹配小区和车辆异常日志Service
	 */
	private IHandoverBillExceptionLogService handoverBillExceptionLogService;

	/**
	 * 集中接送小区DAO接口.
	 */
	private IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao;

	/**
	 * 设置handoverBillVoService
	 * 
	 * @param handoverBillVoService
	 *            要设置的handoverBillVoService
	 */
	@Resource
	public void setHandoverBillVoService(IHandoverBillVoService handoverBillVoService) {
		this.handoverBillVoService = handoverBillVoService;
	}

	@Override
	public void businessExecutor(Object obj) {
		HandoverBillVo hbmc = (HandoverBillVo) obj;
		// 定义一个变量接收访问Gis返回结果
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 修改参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			// 访问Gis
			resultMap = GisInterfaceUtil.callGisInterface(propertyValues.getGisMatchCommunityUrl(),
					beanFieldToMap(hbmc));
		} catch (Exception e) {
			LOGGER.info("交单地址匹配小区调用gis接口出现异常");
			handoverBillExceptionLogService.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc
					.getId(), hbmc.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_GIS, e.getMessage(),
					new Date()));
		}
		// 获取完成的送货地址
		String address = hbmc.getReceiveCustomerProvName() + hbmc.getReceiveCustomerCityName() +
				 hbmc.getReceiveCustomerDistName() + hbmc.getReceiveCustomerAddress() + 
				 (hbmc.getReceiveCustomerAddressNote() == null ? "" : hbmc.getReceiveCustomerAddressNote());
		// 定义特殊送货地址库变量 接收根据地址查询特殊送货地址库的结果
		SpecialDeliveryAddressEntity sdae = null;
		// 接收gis是否匹配成功
		boolean flag = false;
		if (resultMap.size() > 0) {
			flag = (Boolean) resultMap.get("success");
			paramMap.put("longitude", resultMap.get("lng"));
			paramMap.put("latitude", resultMap.get("lat"));
			paramMap.put("matchType", resultMap.get("matchName"));
		}

		if (flag) {
			try {
				// 获得特殊送货地址库
				sdae = specialDeliveryAddressService.selectSpecialDeliveryAddress(address);
			} catch (Exception e) {
				LOGGER.info("交单匹配小区和车辆查询特殊送货地址库出现异常");
				handoverBillExceptionLogService.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc
						.getId(), hbmc.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_SPECIALADDRESS, e
						.getMessage(), new Date()));
				throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_SPECIALADDRESS + "--->"
						+ "调用特殊送货地址库接口出现异常");
			}
			if (sdae == null) {
				// 获得小区GisId
				Object smallzoneGisId = resultMap.get("scopeCoordinatesId");
				// 定义变量接收定人定区获得的车牌号
				String vehicleNo = "";

				if (smallzoneGisId != null) {
					List<SmallZoneEntity> smallZoneList = null;
					try {
						smallZoneList = pickupAndDeliverySmallZoneDao.querySmallZoneByGisId(smallzoneGisId.toString());
					} catch (Exception e) {
						LOGGER.info("根据GisId查询小区信息失败");
						handoverBillExceptionLogService
								.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc.getId(), hbmc
										.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_QUERY_SMALLZONE, e
										.getMessage(), new Date()));
						throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_QUERY_SMALLZONE + "--->"
								+ "根据GisId查询小区信息失败");
					}
					SmallZoneEntity smallZone = null;
					if (smallZoneList != null && smallZoneList.size() > 0) {
						smallZone = smallZoneList.get(0);
					}
					if (smallZone != null) {
						paramMap.put("smallzoneCode", smallZone.getRegionCode());
						paramMap.put("smallzoneName", smallZone.getRegionName());

						SimpleTruckScheduleDto simpleTruckScheduleDto = null;
						try {
							simpleTruckScheduleDto = truckSchedulingTaskService.queryTaskByZoneCodeAndDate(
									smallZone.getRegionCode(),
									DateUtils.format(hbmc.getPreDeliverDate(), DateUtils.ISO8601_DATE_PATTERN));
						} catch (Exception e1) {
							LOGGER.info("检查是否节假日出现异常");
							handoverBillExceptionLogService
									.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc.getId(),
											hbmc.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_ISHOLIDAY, e1
													.getMessage(), new Date()));
							throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_ISHOLIDAY + "--->"
									+ "检查是否节假日出现异常");
						}
						// 判断是否节假日
						if ("Y".equals(simpleTruckScheduleDto.getIsHoliday())) {
							vehicleNo = simpleTruckScheduleDto.getVehicleNo();
							// 设置车牌号
							paramMap.put("vehicleNo", vehicleNo);
							// 设置是否排班
							paramMap.put("isVehicleScheduling", "Y");
						} else {
							// 获取车牌号
							try {
								vehicleNo = regionalVehicleService.getRegionVehicleNoBySmallZoneCode(smallZone.getRegionCode());
							} catch (Exception e) {
								e.printStackTrace();
								LOGGER.info("交单匹配小区和车辆:调用定人定区接口出现异常");
								handoverBillExceptionLogService
										.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(
												hbmc.getId(), hbmc.getWaybillNo(),
												HandoverBillExceptionConstants.EXCEPTION_REGIONALVEHICLE, e
														.getMessage(), new Date()));
								throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_REGIONALVEHICLE
										+ "--->" + "调用定人定区接口出现异常");
							}
							if (vehicleNo != null && !"".equals(vehicleNo.trim()) && hbmc.getPreDeliverDate() != null) {
								// 设置车牌号
								paramMap.put("vehicleNo", vehicleNo);
								try {
									// 设置是否排班
									paramMap.put(
											"isVehicleScheduling",
											isVehicleScheduling(vehicleNo,
													DateUtils.format(hbmc.getPreDeliverDate(), DateUtils.ISO8601_DATE_PATTERN)));
								} catch (Exception e) {
									LOGGER.info("查询车辆是否排班出现异常");
									handoverBillExceptionLogService
											.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc
													.getId(), hbmc.getWaybillNo(),
													HandoverBillExceptionConstants.EXCEPTION_PAIBAN, e.getMessage(),
													new Date()));
									throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_PAIBAN + "--->"
											+ "查询车辆是否排班出现异常");
								}
							}
						}
					}
				}
			} else {
				if (sdae.getVehicleNo() != null && !"".equals(sdae.getVehicleDeptCode().trim())
						&& hbmc.getPreDeliverDate() != null) {
					// 设置车牌号
					paramMap.put("vehicleNo", sdae.getVehicleNo());
					paramMap.put("smallzoneName", sdae.getDeliveryResidenceName());
					paramMap.put("smallzoneCode", sdae.getDeliveryResidenceCode());
					paramMap.put("specialAddressType", sdae.getAddressType());
					// 设置是否排班
					try {
						paramMap.put(
								"isVehicleScheduling",
								isVehicleScheduling(sdae.getVehicleNo(),
										DateUtils.format(hbmc.getPreDeliverDate(), DateUtils.ISO8601_DATE_PATTERN)));
					} catch (Exception e) {
						LOGGER.info("查询车辆是否排班出现异常");
						handoverBillExceptionLogService
								.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc.getId(), hbmc
										.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_PAIBAN, e
										.getMessage(), new Date()));
						throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_PAIBAN + "--->"
								+ "查询车辆是否排班出现异常");
					}
				}
			}
		} else {
			LOGGER.info("交单匹配小区和车辆Gis没有匹配到小区");
			try {
				// 获得特殊送货地址库
				sdae = specialDeliveryAddressService.selectSpecialDeliveryAddress(address);
			} catch (Exception e) {
				LOGGER.info("交单匹配小区和车辆查询特殊送货地址库出现异常");
				handoverBillExceptionLogService.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc
						.getId(), hbmc.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_SPECIALADDRESS, e
						.getMessage(), new Date()));
				throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_SPECIALADDRESS + "--->"
						+ "调用特殊送货地址库接口出现异常");
			}
			if (sdae != null) {
				// 设置小区
				paramMap.put("smallzoneCode", sdae.getDeliveryResidenceCode());
				paramMap.put("smallzoneName", sdae.getDeliveryResidenceName());
				paramMap.put("specialAddressType", sdae.getAddressType());
				if (sdae.getVehicleNo() != null && !"".equals(sdae.getVehicleDeptCode().trim())
						&& hbmc.getPreDeliverDate() != null) {
					// 设置车牌号
					paramMap.put("vehicleNo", sdae.getVehicleNo());
					// 设置是否排班
					try {
						paramMap.put(
								"isVehicleScheduling",
								isVehicleScheduling(sdae.getVehicleNo(),
										DateUtils.format(hbmc.getPreDeliverDate(), DateUtils.ISO8601_DATE_PATTERN)));
					} catch (Exception e) {
						LOGGER.info("查询车辆是否排班出现异常");
						handoverBillExceptionLogService
								.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc.getId(), hbmc
										.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_PAIBAN, e
										.getMessage(), new Date()));
						throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_PAIBAN + "--->"
								+ "查询车辆是否排班出现异常");
					}
				}
			}
		}
		// 设置交单Id
		paramMap.put("id", hbmc.getId());
		// 设置是否被Gis处理过
		paramMap.put("isGisDeal", "Y");

		try {
			// 更新已交单
			handoverBillVoService.updateHandoverBill(paramMap);
		} catch (Exception e) {
			LOGGER.info("修改已交单的小区跟车辆出现异常");
			handoverBillExceptionLogService.insertHandoverBillExceptionLog(new HandoverBillExceptionLogEntity(hbmc
					.getId(), hbmc.getWaybillNo(), HandoverBillExceptionConstants.EXCEPTION_HANDOVERBILL, e
					.getMessage(), new Date()));
			throw new RuntimeException(HandoverBillExceptionConstants.EXCEPTION_HANDOVERBILL + "--->"
					+ "修改已交单的小区跟车辆出现异常");
		}
	}

	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoHBMatchCommunityService【线程池满异常处理开始。。。。。。】");
		HandoverBillVo hbmc = (HandoverBillVo) obj;
		if (hbmc != null) {
			handoverBillVoService.updateHandoverBillIsUsed(hbmc.getId(), "N");
		}
		LOGGER.info("AutoHBMatchCommunityService【线程池满异常处理结束。。。。。。】");
	}

	@Override
	public int getActiveThreads() {
		return NumberConstants.NUMBER_3;
	}

	/**
	 * @Title: matchCommunity
	 * @Description: 添加至线程池
	 * @param handoverBillList
	 *            传入已交单Vo集合
	 */
	public void matchCommunity(List<HandoverBillVo> handoverBillList) {
		if (handoverBillList != null && handoverBillList.size() > 0) {
			for (int i = 0, len = handoverBillList.size(); i < len; i++) {
				HandoverBillVo hbmc = handoverBillList.get(i);
				int result = handoverBillVoService.updateHandoverBillIsUsed(hbmc.getId(), "Y");
				if (result == 1) {
					super.pushThreadsPool(hbmc);
				} else {
					LOGGER.info("交单匹配小区使用状态没修改成功！");
				}
			}
		} else {
			LOGGER.info("交单没有要匹配小区和车辆的数据");
		}
	}

	/**
	 * @Title: beanFieldToMap
	 * @Description: 将交单对象的部分属性添加到Map中
	 * @param hbmc
	 *            交单对象
	 * @return map集合(传给Gis参数)
	 */
	private Map<String, String> beanFieldToMap(HandoverBillVo hbmc) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appNum", hbmc.getWaybillNo());
		paramMap.put("province", hbmc.getReceiveCustomerProvName());
		paramMap.put("city", hbmc.getReceiveCustomerCityName());
		paramMap.put("county", hbmc.getReceiveCustomerDistName());
		paramMap.put("otherAddress", hbmc.getReceiveCustomerAddress());
		paramMap.put("tel", hbmc.getReceiveCustomerPhone());
		paramMap.put("phone", hbmc.getReceiveCustomerMobilePhone());
		paramMap.put("lat", hbmc.getLongitude());
		paramMap.put("lng", hbmc.getLatitude());
		paramMap.put("matchtype", "DE");
		return paramMap;
	}

	/**
	 * @Title: isVehicleScheduling
	 * @Description: 检查车辆是否排班
	 * @param vehicleNo
	 *            车牌号
	 * @param dateStr
	 *            排班日期
	 * @return "Y":已排班 "N":没排班
	 */
	private String isVehicleScheduling(String vehicleNo, String dateStr) {
		if (vehicleNo == null || "".equals(vehicleNo.trim())) {
			return "";
		}
		// 创建排班表简单查询Dto实例
		SimpleTruckScheduleDto stsd = new SimpleTruckScheduleDto();
		stsd.setVehicleNo(vehicleNo);
		stsd.setYmd(dateStr);
		stsd.setSchedulingStatus("Y");
		// 定义排班表简单查询Dto结合变量 接收查询结果
		List<SimpleTruckScheduleDto> queryTaskByVehicleNoAndDate = truckSchedulingTaskService
				.queryTaskByVehicleNoAndDate(stsd);
		// 判断是否查出结果
		if (queryTaskByVehicleNoAndDate != null && queryTaskByVehicleNoAndDate.size() > 0) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * 设置specialDeliveryAddressService
	 * 
	 * @param specialDeliveryAddressService
	 *            要设置的specialDeliveryAddressService
	 */
	@Resource
	public void setSpecialDeliveryAddressService(ISpecialDeliveryAddressService specialDeliveryAddressService) {
		this.specialDeliveryAddressService = specialDeliveryAddressService;
	}

	/**
	 * 设置regionalVehicleService
	 * 
	 * @param regionalVehicleService
	 *            要设置的regionalVehicleService
	 */
	@Resource
	public void setRegionalVehicleService(IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}

	/**
	 * 设置truckSchedulingTaskService
	 * 
	 * @param truckSchedulingTaskService
	 *            要设置的truckSchedulingTaskService
	 */
	@Resource
	public void setTruckSchedulingTaskService(ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	/**
	 * 获取propertyValues
	 * 
	 * @return the propertyValues
	 */
	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	/**
	 * 设置propertyValues
	 * 
	 * @param propertyValues
	 *            要设置的propertyValues
	 */
	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	/**
	 * 设置handoverBillExceptionLogService
	 * 
	 * @param handoverBillExceptionLogService
	 *            要设置的handoverBillExceptionLogService
	 */
	@Resource
	public void setHandoverBillExceptionLogService(IHandoverBillExceptionLogService handoverBillExceptionLogService) {
		this.handoverBillExceptionLogService = handoverBillExceptionLogService;
	}

	/**
	 * 设置pickupAndDeliverySmallZoneDao
	 * 
	 * @param pickupAndDeliverySmallZoneDao
	 *            要设置的pickupAndDeliverySmallZoneDao
	 */
	@Resource
	public void setPickupAndDeliverySmallZoneDao(IPickupAndDeliverySmallZoneDao pickupAndDeliverySmallZoneDao) {
		this.pickupAndDeliverySmallZoneDao = pickupAndDeliverySmallZoneDao;
	}

}

package com.deppon.foss.module.transfer.dubbo.api.service.impl.expose;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.crm.api.define.WaybillDetailEntity;
import com.deppon.foss.dubbo.crm.api.define.WaybillDetailForSOCEntity;
import com.deppon.foss.dubbo.crm.api.service.ICrmClaimApplicationService4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoDto;
import com.deppon.foss.module.transfer.dubbo.api.service.IWaybillQueryService4dubbo;

public class CrmClaimApplicationService4dubbo implements ICrmClaimApplicationService4dubbo {
	private static final String TRANS_EXPRESS = "TRANS_EXPRESS";
	private static final String RETURN_CARGO = "RETURN_CARGO";

	private static final Logger logger = LogManager.getLogger(CrmClaimApplicationService4dubbo.class);

	private IWaybillQueryService4dubbo waybillQueryService4dubbo;

	@Override
	public WaybillDetailForSOCEntity queryWaybillDetailForSOC(List<String> waybillNoList) {
		logger.info("进入queryWaybillDetailForSOC");
		List<WaybillInfoDto> waybillInfoDtoList = getWaybillQueryService4dubbo().queryWaybillInfoForSOC(waybillNoList);

		WaybillDetailForSOCEntity waybillDetailForSOCResponse = new WaybillDetailForSOCEntity();
		// 运单信息集合
		List<WaybillDetailEntity> waybillDetailForSOCVoList = new ArrayList<WaybillDetailEntity>();
		// 运单费用信息集合
		// 运单信息
		WaybillDetailEntity waybillDetailForSOCVo = null;
		// 运单费用信息
		if (CollectionUtils.isNotEmpty(waybillInfoDtoList)) {
			logger.info("------queryWaybillDetailForSOC waybillInfoDtoList " + waybillInfoDtoList.size());
			List<String> returnWaybillNoList = null;
			for (WaybillInfoDto infoDto : waybillInfoDtoList) {
				if (StringUtils.isNotEmpty(infoDto.getWaybillNo())) {
					if (StringUtils.isEmpty(infoDto.getOriginalWaybillNo())) {
						WaybillExpressEntity entity = waybillQueryService4dubbo
								.queryWaybillByOriginalWaybillNo(infoDto.getWaybillNo(), RETURN_CARGO);
						if (entity != null && StringUtils.isNotEmpty(entity.getOriginalWaybillNo())) {
							infoDto.setOriginalWaybillNo(entity.getWaybillNo());
							returnWaybillNoList = new ArrayList<String>();
							returnWaybillNoList.add(entity.getWaybillNo());
							infoDto.setReturnWaybillNoList(returnWaybillNoList);
						}
					}
				}

			}
			// 运单信息
			for (WaybillInfoDto waybillInfo : waybillInfoDtoList) {
				waybillDetailForSOCVo = new WaybillDetailEntity();
				waybillDetailForSOCVo.setNumber(waybillInfo.getWaybillNo());
				// 设置运输类型(产品)
				if (directDetermineIsExpressByProductCode(waybillInfo.getProductCode())) {
					waybillDetailForSOCVo.setTranType(TRANS_EXPRESS);
				} else {
					waybillDetailForSOCVo.setTranType(waybillInfo.getTransportType());
				}
				// 设置运输性质
				waybillDetailForSOCVo.setTranProperty(waybillInfo.getProductCode());
				// 发货联系人
				waybillDetailForSOCVo.setSender(waybillInfo.getDeliveryCustomerName());
				// 发货客户为大客户标示，返回给官网

				waybillDetailForSOCVo.setIsBigDeliverCustomer(waybillInfo.getIsBigDeliverCustomer());
				// 返货类型

				waybillDetailForSOCVo.setReturnType(waybillInfo.getReturnType());
				// 返货:原运单号

				waybillDetailForSOCVo.setOriginalWaybillNo(waybillInfo.getOriginalWaybillNo());
				// 发货人电话
				waybillDetailForSOCVo.setSenderPhone(waybillInfo.getDeliveryCustomerPhone());
				// 发货客户手机
				waybillDetailForSOCVo.setSenderMobile(waybillInfo.getDeliveryCustomerMobilephone());
				// 始发站
				waybillDetailForSOCVo.setDeparture(waybillInfo.getDeliveryCustomerCityName());
				// 发货人地址
				waybillDetailForSOCVo.setSenderAddress(waybillInfo.getDeliveryCustomerAddress());
				// 发货人地址备注
				waybillDetailForSOCVo.setSenderAddressNote(waybillInfo.getDeliveryCustomerAddressNote());
				// 收货人
				waybillDetailForSOCVo.setConsignee(waybillInfo.getReceiveCustomerName());
				// 收货人电话
				waybillDetailForSOCVo.setConsigneePhone(waybillInfo.getReceiveCustomerPhone());
				// 收货人手机
				waybillDetailForSOCVo.setConsigneeMobile(waybillInfo.getReceiveCustomerMobilephone());
				// 目的站
				waybillDetailForSOCVo.setDestination(waybillInfo.getTargetOrgCode());
				// 收货人地址
				waybillDetailForSOCVo.setConsigneeAddress(waybillInfo.getReceiveCustomerAddress());
				// 收货地址备注
				waybillDetailForSOCVo.setConsigneeAddressNote(waybillInfo.getReceiveCustomerAddressNote());
				// 货物名称
				waybillDetailForSOCVo.setGoodName(waybillInfo.getGoodsName());
				// 件数
				waybillDetailForSOCVo.setPieces(waybillInfo.getGoodsQtyTotal());
				// 重量
				waybillDetailForSOCVo.setWeight(Float.parseFloat(waybillInfo.getGoodsWeightTotal().toString()));
				// 体积
				waybillDetailForSOCVo.setCubage(Float.parseFloat(waybillInfo.getGoodsVolumeTotal().toString()));
				// 总费用
				waybillDetailForSOCVo.setTotalCharge(waybillInfo.getTotalFee());
				// 付款方式
				waybillDetailForSOCVo.setPayment(waybillInfo.getPaidMethod());
				// 预付金额
				waybillDetailForSOCVo.setPreCharge(waybillInfo.getPrePayAmount());
				// 到付金额
				waybillDetailForSOCVo.setArriveCharge(waybillInfo.getToPayAmount());
				// 代收货款类型
				waybillDetailForSOCVo.setRefundType(waybillInfo.getRefundType());
				// 代收货款
				waybillDetailForSOCVo.setRefund(waybillInfo.getCodAmount());
				// 代收货款手续费
				waybillDetailForSOCVo.setRefundFee(waybillInfo.getCodFee());
				// 开单提货方式
				waybillDetailForSOCVo.setDeliveryType(waybillInfo.getReceiveMethod());
				// 接货费
				waybillDetailForSOCVo.setConsignCharge(waybillInfo.getPickupFee());
				// 送货费
				waybillDetailForSOCVo.setDeliveryCharge(waybillInfo.getDeliveryGoodsFee());
				// 包装费
				waybillDetailForSOCVo.setPickCharge(waybillInfo.getPackageFee());
				// 装卸费
				waybillDetailForSOCVo.setLaborRebate(waybillInfo.getServiceFee());
				// 公布价运费
				waybillDetailForSOCVo.setPublishCharge(waybillInfo.getTransportFee());
				// 出发部门名称
				waybillDetailForSOCVo.setDepartureDeptName(waybillInfo.getDepartureDeptName());
				// 出发部门标杆编码
				waybillDetailForSOCVo.setDepartureDeptNumber(waybillInfo.getDepartureDeptNumber());
				// 出发部门地址
				waybillDetailForSOCVo.setDepartureDeptAddr(waybillInfo.getDepartureDeptAddr());
				// 出发部门电话
				waybillDetailForSOCVo.setDepartureDeptPhone(waybillInfo.getDepartureDeptPhone());
				// 出发部门传真
				waybillDetailForSOCVo.setDepartureDeptFax(waybillInfo.getDepartureDeptFax());
				// 到达网点名称
				waybillDetailForSOCVo.setLadingStationName(waybillInfo.getLadingStationName());
				// 到达网点标杆编码
				waybillDetailForSOCVo.setLadingStationNumber(waybillInfo.getLadingStationNumber());
				// 到达网点地址
				waybillDetailForSOCVo.setLadingStationAddr(waybillInfo.getLadingStationAddr());
				// 到达网点电话
				waybillDetailForSOCVo.setLadingStationPhone(waybillInfo.getLadingStationPhone());
				// 到达网点传真
				waybillDetailForSOCVo.setLadingStationFax(waybillInfo.getLadingStationFax());
				// 是否签收
				waybillDetailForSOCVo.setIsSigned(waybillInfo.isSigned());
				// 是否正常签收
				waybillDetailForSOCVo.setIsNormalSigned(waybillInfo.isNormalSigned());
				// 是否异常签收
				waybillDetailForSOCVo.setUuormaSign(waybillInfo.isAbnormalSigned());
				// 是否弃货签收
				waybillDetailForSOCVo.setMissingSign(waybillInfo.isAbandonGoodsSigned());
				// 子件单号集合
				waybillDetailForSOCVo.setChildWaybillNoList(waybillInfo.getChildWaybillNoList());
				// 返货单号集合
				waybillDetailForSOCVo.setReturnWaybillNoList(waybillInfo.getReturnWaybillNoList());
				// 签收录入人
				waybillDetailForSOCVo.setSignRecorderId(waybillInfo.getDeliverymanName());
				// 签收时间
				waybillDetailForSOCVo.setSignedDate(waybillInfo.getSignTime());
				// 第一次签收时间
				waybillDetailForSOCVo.setFirstSignedDate(waybillInfo.getFirstSignTime());
				// 签收备注
				waybillDetailForSOCVo.setSignedDesc(waybillInfo.getSignNote());
				// 订单号
				waybillDetailForSOCVo.setOrderNumber(waybillInfo.getOrderNo());
				// 保价声明
				waybillDetailForSOCVo.setInsuranceValue(waybillInfo.getInsuranceAmount());
				// 保价手续费
				waybillDetailForSOCVo.setInsurance(waybillInfo.getInsuranceFee());
				// 货物包装
				waybillDetailForSOCVo.setPacking(waybillInfo.getGoodsPackage());
				// 运单状态
				waybillDetailForSOCVo.setOrderState(waybillInfo.getActive());
				// 其它费用
				waybillDetailForSOCVo.setOtherPayment(waybillInfo.getOtherFee());
				// 托运备注
				waybillDetailForSOCVo.setTranDesc(waybillInfo.getOuterNotes());
				// 发货客户编码
				waybillDetailForSOCVo.setSenderNumber(waybillInfo.getDeliveryCustomerCode());
				// 收货客户编码
				waybillDetailForSOCVo.setConsigneeNumber(waybillInfo.getReceiveCustomerCode());
				// 是否已结款
				waybillDetailForSOCVo.setIsClear(waybillInfo.getSettleStatus());
				// 返单类型
				waybillDetailForSOCVo.setSignBackType(waybillInfo.getReturnBillType());
				// 储运事项
				waybillDetailForSOCVo.setTransNotice(waybillInfo.getTransportationRemark());
				// 发货时间
				waybillDetailForSOCVo.setSendTime(waybillInfo.getBillTime());
				// 收货部门名称
				waybillDetailForSOCVo.setReceiveDeptName(waybillInfo.getReceiveOrgName());
				// 收货部门标杆代码
				waybillDetailForSOCVo.setReceiveDeptNumber(waybillInfo.getReceiveOrgNumber());
				// 配载部门标杆编码
				waybillDetailForSOCVo.setStowageDept(waybillInfo.getLoadOrgNumber());
				// 发货人城市编码
				waybillDetailForSOCVo.setSenderCityCode(waybillInfo.getDeliveryCustomerCityCode1());
				// 发货人城市名称
				waybillDetailForSOCVo.setSenderCityName(waybillInfo.getDeliveryCustomerCityName1());
				// 发货人省份编码
				waybillDetailForSOCVo.setSenderProvinceCode(waybillInfo.getDeliveryCustomerProvCode());
				// 发货人省份名称
				waybillDetailForSOCVo.setSenderProvinceName(waybillInfo.getDeliveryCustomerProvName());
				// 收货人城市编码
				waybillDetailForSOCVo.setConsigneeCityCode(waybillInfo.getReceiveCustomerCityCode());
				// 收货人城市名称
				waybillDetailForSOCVo.setConsigneeCityName(waybillInfo.getReceiveCustomerCityName());
				// 收货人省份编码
				waybillDetailForSOCVo.setConsigneeProvinceCode(waybillInfo.getReceiveCustomerProvCode());
				// 收货人省份名称
				waybillDetailForSOCVo.setConsigneeProvinceName(waybillInfo.getReceiveCustomerProvName());
				// 是否上门接货
				waybillDetailForSOCVo.setIsDoorToDoorPick(waybillInfo.isPickup());
				// 短信通知状态
				waybillDetailForSOCVo.setSmsNoticeResult(waybillInfo.getNotificationResult());
				// 签收单返回方式
				waybillDetailForSOCVo.setSignBillBackWay(waybillInfo.getReturnBillType());

				// 小件 新加的逻辑----------------------
				// 运单所属快递大区编码
				waybillDetailForSOCVo.setExDepartureRegionNubmer(waybillInfo.getDistrictCode());
				// 运单所属快递大区名称
				waybillDetailForSOCVo.setExDepartureRegionName(waybillInfo.getDistrictName());
				// 运单所属快递大区标杆编码
				waybillDetailForSOCVo.setExDepartureRegionStandardNubmer(waybillInfo.getDistrictUnifiedCode());

				// 运单所属快递大区编码
				waybillDetailForSOCVo.setExDestinationRegionNubmer(waybillInfo.getEndDistrictCode());
				// 运单所属快递大区名称
				waybillDetailForSOCVo.setExDestinationRegionName(waybillInfo.getEndDistrictName());
				// 运单所属快递大区标杆编码
				waybillDetailForSOCVo.setExDestinationRegionStandardNubmer(waybillInfo.getEndDistrictUnifiedCode());
				// 是否子母件
				waybillDetailForSOCVo.setIsPicPackage(waybillInfo.isParentChildWaybill());
				// 母件单号
				waybillDetailForSOCVo.setParentWaybillNo(waybillInfo.getParentWaybillNo());
				// 快递员CODE-出发
				waybillDetailForSOCVo.setExDepartureCourierNumber(waybillInfo.getStartExpressEmpCode());
				// 快递员名称-出发
				waybillDetailForSOCVo.setExDepartureCourierName(waybillInfo.getStartExpressEmpName());
				// 快递点部CODE-出发
				waybillDetailForSOCVo.setExDepartureDeptNumber(waybillInfo.getStartExpressOrgCode());
				// 快递点部标杆编码-出发
				waybillDetailForSOCVo.setExDepartureDeptStandardNumber(waybillInfo.getStartExpressUnfiedCode());
				// 快递点部名称-出发
				waybillDetailForSOCVo.setExDepartureDeptName(waybillInfo.getStartExpressOrgName());
				// 快递员CODE-到达
				waybillDetailForSOCVo.setExDestinationCourierNumber(waybillInfo.getEndExpressEmpCode());
				// 快递员名称-到达
				waybillDetailForSOCVo.setExDestinationCourierName(waybillInfo.getEndExpressEmpName());
				// 快递点部CODE-到达
				waybillDetailForSOCVo.setExDestinationDeptNumber(waybillInfo.getEndExpressOrgCode());
				// 快递点部名称-到达
				waybillDetailForSOCVo.setExDestinationDeptName(waybillInfo.getEndExpressOrgName());

				// 快递点部标杆编码-到达
				waybillDetailForSOCVo.setExDestinationDeptStandardNumber(waybillInfo.getEndExpressUnfiedCode());
				waybillDetailForSOCVoList.add(waybillDetailForSOCVo);
				logger.info("-- 运单查询---- " + ReflectionToStringBuilder.toString(waybillDetailForSOCVo));
			}
		}
		waybillDetailForSOCResponse.setWaybillDetailForOfficialList(waybillDetailForSOCVoList);
		logger.info("queryWaybillDetailForSOC结束：" + waybillDetailForSOCVoList.size());
		return waybillDetailForSOCResponse;

	}

	private static boolean directDetermineIsExpressByProductCode(String productCode) {
		if (productCode == null || "".equals(productCode)) {
			return false;
		}
		if ("PACKAGE".equals(productCode) || "RCP".equals(productCode) || "EPEP".equals(productCode)
				|| "DEAP".equals(productCode)) {
			return true;
		}
		return false;
	}

	public IWaybillQueryService4dubbo getWaybillQueryService4dubbo() {
		return waybillQueryService4dubbo;
	}

	@Autowired
	public void setWaybillQueryService4dubbo(IWaybillQueryService4dubbo waybillQueryService4dubbo) {
		this.waybillQueryService4dubbo = waybillQueryService4dubbo;
	}

}

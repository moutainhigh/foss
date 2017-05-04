package com.deppon.foss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.customerservice.CommonException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillChargeCostDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.shared.request.QueryDetailRequest;
import com.deppon.foss.shared.response.QueryDetailResponse;
import com.deppon.foss.shared.response.WayBillDetail;
import com.deppon.foss.shared.response.WaybillCostInfo;

/**
 * FOSS与OMS的接口
 * 
 * @author 305082
 */
@Controller
public class OmsWaybillServiceImpl {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OmsWaybillServiceImpl.class);
	
	/**
	 * ESB_RESULT_CODE
	 */
	public static final String ESB_RESULT_CODE = "ESB-ResultCode";
	
	/**
	 * ESB_RESULT_FIAL_CODE
	 */
	public static final String ESB_RESULT_FIAL_CODE = "00000";
	
	/**
	 * ESB_RESULT_SUCCESS_CODE
	 */
	public static final String ESB_RESULT_SUCCESS_CODE = "10000";

	/**
	 * 运单管理service
	 */

	@Resource
	private IWaybillQueryService waybillQueryService;
	
	@Resource
	private IWaybillExpressService waybillExpressService;
	
	
	/**
	 * 
	 * 查询运单详细信息
	 */
	@RequestMapping(value = "/queryDetail", method = RequestMethod.POST)
	@ResponseBody
	public QueryDetailResponse queryDetail(@RequestBody QueryDetailRequest request,HttpServletResponse response) throws CommonException{
		//ESB返回对象
		QueryDetailResponse queryDetailResponse = new QueryDetailResponse();
		//设置esb返回头信息
		response.setHeader(ESB_RESULT_CODE, String.valueOf(1));
		
		try {
			//判断接收到的参数是否为空
			//为空则返回null
			if(request != null){
				//ESB提供运单集合
				List<String> waybillList = request.getWaybillNo();
				//运单信息集合
				List<WayBillDetail> wayBillDetailList = new ArrayList<WayBillDetail> ();
				//运单费用信息集合
				List<WaybillCostInfo> waybillCostInfos = new ArrayList<WaybillCostInfo> ();
				//ESB运单信息
				WayBillDetail wayBillDetail = null;
				//运单费用信息
				WaybillCostInfo waybillCostInfo = null;
				
				List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfo(waybillList);
				
				for(WaybillInfoDto waybillInfo : waybillInfoDtoList)
				{
					wayBillDetail =  new WayBillDetail();
					if(waybillInfo.getWaybillChargeCostDto() != null)
					{
						for (WaybillChargeCostDto waybillChargeCostDto : waybillInfo.getWaybillChargeCostDto())
						{
							//复制费用信息
							waybillCostInfo = new WaybillCostInfo();
							waybillCostInfo.setCostType(waybillChargeCostDto.getCostType());
							waybillCostInfo.setCostName(waybillChargeCostDto.getCostName());
							waybillCostInfo.setCostMoney(waybillChargeCostDto.getCostMoney());
							waybillCostInfos.add(waybillCostInfo);
						}
					}
					wayBillDetail.getWaybillCostInfos().addAll(waybillCostInfos);
					//设置运单号
					wayBillDetail.setNumber(waybillInfo.getWaybillNo());
					/**
					 * KDTE-5323 快递单号待补录状态时FOSS与CRM系统信息不一致
					 */
					//设置运输类型(产品)
					wayBillDetail.setTranType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillInfo.getProductCode()));
					//设置运输性质
					wayBillDetail.setTranProperty(waybillInfo.getProductCode());
					//发货联系人
					wayBillDetail.setSender(waybillInfo.getDeliveryCustomerName());
					//发货人电话
					wayBillDetail.setSenderPhone(waybillInfo.getDeliveryCustomerPhone());
					//发货客户手机
					wayBillDetail.setSenderMobile(waybillInfo.getDeliveryCustomerMobilephone());
					//始发站
					wayBillDetail.setDeparture(waybillInfo.getDeliveryCustomerCityName());
					//发货人地址
					wayBillDetail.setSenderAddress(waybillInfo.getDeliveryCustomerAddress());
					//发货人地址备注
					wayBillDetail.setSenderAddressNote(waybillInfo.getDeliveryCustomerAddressNote());
					//收货人
					wayBillDetail.setConsignee(waybillInfo.getReceiveCustomerName());
					//收货人电话
					wayBillDetail.setConsigneePhone(waybillInfo.getReceiveCustomerPhone());
					//收货人手机
					wayBillDetail.setConsigneeMobile(waybillInfo.getReceiveCustomerMobilephone());
					//目的站
					wayBillDetail.setDestination(waybillInfo.getTargetOrgCode());
					//收货人地址
					wayBillDetail.setConsigneeAddress(waybillInfo.getReceiveCustomerAddress());
					//收货人地址备注
					wayBillDetail.setConsigneeAddressNote(waybillInfo.getReceiveCustomerAddressNote());
					//货物名称
					wayBillDetail.setGoodName(waybillInfo.getGoodsName());
					//件数
					wayBillDetail.setPieces(waybillInfo.getGoodsQtyTotal());
					//重量
					wayBillDetail.setWeight(Float.parseFloat(waybillInfo.getGoodsWeightTotal().toString()));
					//体积
					wayBillDetail.setCubage(Float.parseFloat(waybillInfo.getGoodsVolumeTotal().toString()));
					//总费用
					wayBillDetail.setTotalCharge(waybillInfo.getTotalFee());
					//付款方式
					wayBillDetail.setPayment(waybillInfo.getPaidMethod());
					//预付金额
					wayBillDetail.setPreCharge(waybillInfo.getPrePayAmount());
					//到付金额
					wayBillDetail.setArriveCharge(waybillInfo.getToPayAmount());
					//代收货款类型
					wayBillDetail.setRefundType(waybillInfo.getRefundType());
					//代收货款
					wayBillDetail.setRefund(waybillInfo.getCodAmount());
					//代收货款手续费
					wayBillDetail.setRefundFee(waybillInfo.getCodFee());
					//开单提货方式
					wayBillDetail.setDeliveryType(waybillInfo.getReceiveMethod());
					//接货费
					wayBillDetail.setConsignCharge(waybillInfo.getPickupFee());
					//送货费
					wayBillDetail.setDeliveryCharge(waybillInfo.getDeliveryGoodsFee());
					//包装费
					wayBillDetail.setPickCharge(waybillInfo.getPackageFee());
					//装卸费
					wayBillDetail.setLaborRebate(waybillInfo.getServiceFee());
					//公布价运费
					wayBillDetail.setPublishCharge(waybillInfo.getTransportFee());
					//出发部门名称
					wayBillDetail.setDepartureDeptName(waybillInfo.getDepartureDeptName());
					//出发部门标杆编码
					wayBillDetail.setDepartureDeptNumber(waybillInfo.getDepartureDeptNumber());
					//出发部门地址
					wayBillDetail.setDepartureDeptAddr(waybillInfo.getDepartureDeptAddr());
					//出发部门电话
					wayBillDetail.setDepartureDeptPhone(waybillInfo.getDepartureDeptPhone());
					//出发部门传真
					wayBillDetail.setDepartureDeptFax(waybillInfo.getDepartureDeptFax());
					//到达网点名称
					wayBillDetail.setLadingStationName(waybillInfo.getLadingStationName());
					//到达网点标杆编码
					wayBillDetail.setLadingStationNumber(waybillInfo.getLadingStationNumber());
					//到达网点地址
					wayBillDetail.setLadingStationAddr(waybillInfo.getLadingStationAddr());
					//到达网点电话
					wayBillDetail.setLadingStationPhone(waybillInfo.getLadingStationPhone());
					//到达网点传真
					wayBillDetail.setLadingStationFax(waybillInfo.getLadingStationFax());
					//是否签收
					wayBillDetail.setIsSigned(waybillInfo.isSigned());
					//是否正常签收
					wayBillDetail.setIsNormalSigned(waybillInfo.isNormalSigned());
					//签收录入人
					wayBillDetail.setSignRecorderId(waybillInfo.getDeliverymanName());
					//签收时间
					wayBillDetail.setSignedDate(waybillInfo.getSignTime());
					//第一次签收时间
					wayBillDetail.setFirstSignedDate(waybillInfo.getFirstSignTime());
					//签收备注
					wayBillDetail.setSignedDesc(waybillInfo.getSignNote());
					//订单号
					wayBillDetail.setOrderNumber(waybillInfo.getOrderNo());
					//保价声明
					wayBillDetail.setInsuranceValue(waybillInfo.getInsuranceAmount());
					//保价手续费
					wayBillDetail.setInsurance(waybillInfo.getInsuranceFee());
					//货物包装
					wayBillDetail.setPacking(waybillInfo.getGoodsPackage());
					//运单状态
					wayBillDetail.setOrderState(waybillInfo.getActive());
					//其它费用
					wayBillDetail.setOtherPayment(waybillInfo.getOtherFee());
					//托运备注
					wayBillDetail.setTranDesc(waybillInfo.getOuterNotes());
					//发货客户编码
					wayBillDetail.setSenderNumber(waybillInfo.getDeliveryCustomerCode());
					//收货客户编码
					wayBillDetail.setConsigneeNumber(waybillInfo.getReceiveCustomerCode());
					//是否已结款
					wayBillDetail.setIsClear(waybillInfo.getSettleStatus());
					//返单类型
					wayBillDetail.setSignBackType(waybillInfo.getReturnBillType());
					//储运事项
					wayBillDetail.setTransNotice(waybillInfo.getTransportationRemark());
					//发货时间
					wayBillDetail.setSendTime(waybillInfo.getBillTime());
					//收货部门名称
					wayBillDetail.setReceiveDeptName(waybillInfo.getReceiveOrgName());
					//收货部门标杆代码
					wayBillDetail.setReceiveDeptNumber(waybillInfo.getReceiveOrgNumber());
					//配载部门标杆编码
					wayBillDetail.setStowageDept(waybillInfo.getLoadOrgNumber());
					//发货人城市编码
					wayBillDetail.setSenderCityCode(waybillInfo.getDeliveryCustomerCityCode1());
					//发货人城市名称
					wayBillDetail.setSenderCityName(waybillInfo.getDeliveryCustomerCityName1());
					//发货人省份编码
					wayBillDetail.setSenderProvinceCode(waybillInfo.getDeliveryCustomerProvCode());
					//发货人省份名称
					wayBillDetail.setSenderProvinceName(waybillInfo.getDeliveryCustomerProvName());
					//收货人城市编码
					wayBillDetail.setConsigneeCityCode(waybillInfo.getReceiveCustomerCityCode());
					//收货人城市名称
					wayBillDetail.setConsigneeCityName(waybillInfo.getReceiveCustomerCityName());
					//收货人省份编码
					wayBillDetail.setConsigneeProvinceCode(waybillInfo.getReceiveCustomerProvCode());
					//收货人省份名称
					wayBillDetail.setConsigneeProvinceName(waybillInfo.getReceiveCustomerProvName());
					//是否上门接货
					wayBillDetail.setIsDoorToDoorPick(waybillInfo.isPickup());
					//短信通知状态
					wayBillDetail.setSmsNoticeResult(waybillInfo.getNotificationResult());
					//签收单返回方式
					wayBillDetail.setSignBillBackWay(waybillInfo.getReturnBillType());
					
					
					//小件 新加的逻辑----------------------
					//运单所属快递大区编码
					wayBillDetail.setExDepartureRegionNubmer(waybillInfo.getDistrictCode());
					//运单所属快递大区名称
					wayBillDetail.setExDepartureRegionName(waybillInfo.getDistrictName());
					//运单所属快递大区标杆编码
					wayBillDetail.setExDepartureRegionStandardNubmer(waybillInfo.getDistrictUnifiedCode());
					
					//运单所属快递大区编码
					wayBillDetail.setExDestinationRegionNubmer(waybillInfo.getEndDistrictCode());
					//运单所属快递大区名称
					wayBillDetail.setExDestinationRegionName(waybillInfo.getEndDistrictName());
					//运单所属快递大区标杆编码
					wayBillDetail.setExDestinationRegionStandardNubmer(waybillInfo.getEndDistrictUnifiedCode());
					
					
					
					//快递员CODE-出发
					wayBillDetail.setExDepartureCourierNumber(waybillInfo.getStartExpressEmpCode());
					//快递员名称-出发
					wayBillDetail.setExDepartureCourierName(waybillInfo.getStartExpressEmpName());
					//快递点部CODE-出发
					wayBillDetail.setExDepartureDeptNumber(waybillInfo.getStartExpressOrgCode());
					//快递点部标杆编码-出发
					wayBillDetail.setExDepartureDeptStandardNumber(waybillInfo.getStartExpressUnfiedCode());
					//快递点部名称-出发
					wayBillDetail.setExDepartureDeptName(waybillInfo.getStartExpressOrgName());
					
					//快递员CODE-到达
					wayBillDetail.setExDestinationCourierNumber(waybillInfo.getEndExpressEmpCode());
					
					//快递员名称-到达
					wayBillDetail.setExDestinationCourierName(waybillInfo.getEndExpressEmpName());
					//快递点部CODE-到达
					wayBillDetail.setExDestinationDeptNumber(waybillInfo.getEndExpressOrgCode());
					
					//快递点部名称-到达
					wayBillDetail.setExDestinationDeptName(waybillInfo.getEndExpressOrgName());
					
					//快递点部标杆编码-到达
					wayBillDetail.setExDestinationDeptStandardNumber(waybillInfo.getEndExpressUnfiedCode());
					wayBillDetail.setWaybillStatus(waybillInfo.getWaybillStatus());
					wayBillDetailList.add(wayBillDetail);
					LOGGER.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(wayBillDetail));
	
				}
				queryDetailResponse.getWayBillDetailList().addAll(wayBillDetailList);
			}
		} catch (Exception e) {
			LOGGER.error("查询运单详细信息异常",e);
		}
		return queryDetailResponse;
	}

	/**
	 * 
	 * 将Date 类型转换为XML日期格式
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date){
		if(date == null)
		{
			return null;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar gc = null;
		try {
		    gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {
			LOGGER.info("XML日期类型转换错误：", e.getMessage());
		}
		return gc;
	}
	
	public IWaybillQueryService getWaybillQueryService(){
		return waybillQueryService;
	}
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
}

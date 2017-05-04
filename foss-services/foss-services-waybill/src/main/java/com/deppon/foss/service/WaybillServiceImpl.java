package com.deppon.foss.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.waybillservice.BadWayBillDetail;
import com.deppon.esb.inteface.domain.waybillservice.OAWayBillDetail;
import com.deppon.esb.inteface.domain.waybillservice.OAWaybillCostInfo;
import com.deppon.esb.inteface.domain.waybillservice.OaQueryDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.OaQueryDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailForOfficialRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailForOfficialResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryOneYearDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryOneYearDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.WayBillDetail;
import com.deppon.esb.inteface.domain.waybillservice.WayBillDetailForOfficial;
import com.deppon.esb.inteface.domain.waybillservice.WayBillOneYearDetail;
import com.deppon.esb.inteface.domain.waybillservice.WaybillCostInfo;
import com.deppon.esb.inteface.domain.waybillservice.WaybillCostInfoForOfficial;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillChargeCostDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOneYearDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.waybillservice.CommonException;
import com.deppon.foss.waybillservice.WaybillService;

/**
 * 
 * 运单接口服务
 * @author 043258-foss-zhaobin
 * @date 2013-1-6 下午3:26:39
 */
public class WaybillServiceImpl implements WaybillService
{
	/**
     * 日志
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillServiceImpl.class.getName());
	/**
	 * 默认值1
	 */
	private static final int ONE = 1;
	/**
	 * 运单查询服务
	 */
	private IWaybillQueryService waybillQueryService;
	
	private IWaybillExpressService waybillExpressService;
	/**
	 * 应付单服务.
	 */
	private IBillPayableService billPayableService;
	
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	/**
	 * 运单管理服务
	 */
	private IWaybillManagerService waybillManagerService;
	

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public IWaybillQueryService getWaybillQueryService()
	{
		return waybillQueryService;
	}

	public void setWaybillQueryService(IWaybillQueryService waybillQueryService)
	{
		this.waybillQueryService = waybillQueryService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	// 注册BigDecimal转换器，否则Bigdecimal转换报错
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		DateConverter dateConverter = new DateConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
		ConvertUtils.register(dateConverter, Date.class);
	}
	
	/**
	 * 
	 * 将Date 类型转换为XML日期格式
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-7 上午11:01:35
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date)
	{
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
	
	/**
	 * 
	 * 将XML日期格式转换为Date 类型
	 * @author foss-叶涛
	 * @date 2014-10-16 上午11:01:35
	 */
	 public  Date convertToDate(XMLGregorianCalendar cal) throws Exception{  
         GregorianCalendar ca = cal.toGregorianCalendar();  
         return ca.getTime();  
     }  
	/**
	 * 
	 * 查询运单详细信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-6 下午3:27:13
	 * @see com.deppon.foss.waybillservice.WaybillService#queryDetail(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.waybillservice.QueryDetailRequest)
	 */
	@Override
	public QueryDetailResponse queryDetail(Holder<ESBHeader> esbHeader, QueryDetailRequest payload) throws CommonException
	{
		esbHeader.value.setResponseId(payload.getWaybillNo().get(0));
		esbHeader.value.setResultCode(ONE); 
		
		if(payload != null)
		{
			//ESB提供运单集合
			List<String> waybillList = payload.getWaybillNo();
			//ESB返回对象
			QueryDetailResponse queryDetailResponse = new QueryDetailResponse();
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
				wayBillDetail.setSignedDate(convertToXMLGregorianCalendar(waybillInfo.getSignTime()));
				//第一次签收时间
				wayBillDetail.setFirstSignedDate(convertToXMLGregorianCalendar(waybillInfo.getFirstSignTime()));
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
				
				//update by foss-231434-bieyexiong 非异常弃货，直接发送签收信息
				if(!ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(waybillInfo.getSignSituation())){
					//运单状态
					wayBillDetail.setOrderState(waybillInfo.getActive());
				}else if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillInfo.getProductCode(),null)){
					//(需求DN201603140026只针对零担) 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
					List<BillPayableEntity> billPayables = billPayableService.queryByWaybillNosAndByBillTypes(
							Arrays.asList(waybillInfo.getWaybillNo()),
							Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM));
					//异常弃货，且有上报理赔，才发送签收信息
					if(CollectionUtils.isNotEmpty(billPayables)){
						//运单状态
						wayBillDetail.setOrderState(waybillInfo.getActive());
					}
				}
				
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
				wayBillDetail.setSendTime(convertToXMLGregorianCalendar(waybillInfo.getBillTime()));
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
				wayBillDetailList.add(wayBillDetail);
				LOGGER.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(wayBillDetail));

			}
			queryDetailResponse.getWayBillDetailList().addAll(wayBillDetailList);
			return queryDetailResponse;
		}
		return null;
	}

	@Override
	public QueryOneYearDetailResponse queryOneYearDetail(
			Holder<ESBHeader> esbHeader,
			QueryOneYearDetailRequest queryOneYearDetailRequest)
			throws CommonException {
		if(queryOneYearDetailRequest !=null){
			//ESB提供的发货客户编码
			String deliveryCustomerCode = queryOneYearDetailRequest.getDeliveryCustomerCode();
			//ESB提供的开始时间
			XMLGregorianCalendar startTime = queryOneYearDetailRequest.getStartTime();
			//ESB提供的结束时间
			XMLGregorianCalendar endTime = queryOneYearDetailRequest.getEndTime();
			//ESB返回的对象集合
			QueryOneYearDetailResponse queryOneYearDetailResponse = new QueryOneYearDetailResponse();
			//运单详细信息集合
			List<WayBillOneYearDetail> wayBillOneYearDetails = new ArrayList<WayBillOneYearDetail>();
			//运单坏账信息集合
			List<BadWayBillDetail> badWayBillDetails = new ArrayList<BadWayBillDetail>(); 
			//运单号集合
			List<String> waybillNoList = new ArrayList<String>(); 
			//运单信息
			WayBillOneYearDetail wayBillOneYearDetail = null; 
			//坏账信息
			BadWayBillDetail badWayBillDetail = null;
			List<WaybillOneYearDto> waybillQueryOneYearDtoList = null;
			try {
				waybillQueryOneYearDtoList = waybillQueryService.queryWaybillInfoOneYear(deliveryCustomerCode,convertToDate(startTime),convertToDate(endTime));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(waybillQueryOneYearDtoList.size()!=0){
			for(WaybillOneYearDto waybillOneYearDto : waybillQueryOneYearDtoList){
				if(waybillOneYearDto !=null){
				   wayBillOneYearDetail = new WayBillOneYearDetail();
				//获得客户编码
				wayBillOneYearDetail.setDeliveryCustomerCode(waybillOneYearDto.getDeliveryCustomerCode());
				//获得运单号
				wayBillOneYearDetail.setWaybillNo(waybillOneYearDto.getWaybillNo());
				//获得开单日期
				wayBillOneYearDetail.setCreateTime(convertToXMLGregorianCalendar(waybillOneYearDto.getCreateTime()));
				//获得出发城市
				wayBillOneYearDetail.setDeliveryCustomerCity(waybillOneYearDto.getDeliveryCustomerCityName());
				//获得到达城市
				wayBillOneYearDetail.setReceiveCustomerCity(waybillOneYearDto.getReceiveCustomerCityName());
				//货物名称
				wayBillOneYearDetail.setGoodsName(waybillOneYearDto.getGoodsName());
				//货物包装
				wayBillOneYearDetail.setGoodsPackage(waybillOneYearDto.getGoodsPackage());
				//货物件数
				wayBillOneYearDetail.setGoodsQtyTotal(waybillOneYearDto.getGoodsQtyTotal());
				//货物重量
				wayBillOneYearDetail.setGoodsWeightTotal(Float.parseFloat(waybillOneYearDto.getGoodsWeightTotal().toString()));
				//货物体积
				wayBillOneYearDetail.setGoodsVolumeTotal(Float.parseFloat(waybillOneYearDto.getGoodsVolumeTotal().toString()));
				//退款类型
				wayBillOneYearDetail.setRefundType(waybillOneYearDto.getRefundType());
				//提货方式
				wayBillOneYearDetail.setDeliveryType(waybillOneYearDto.getReceiveMethod());
				//付款方式
				wayBillOneYearDetail.setPayment(waybillOneYearDto.getPaidMethod());
				//公布价运费
				wayBillOneYearDetail.setPublishCharge(waybillOneYearDto.getTransportFee());
				//预付金额
				wayBillOneYearDetail.setPreCharge(waybillOneYearDto.getPrePayAmount());
				//到付金额
				wayBillOneYearDetail.setArriveCharge(waybillOneYearDto.getToPayAmount());
				//保险价值
				wayBillOneYearDetail.setInsuranceValue(waybillOneYearDto.getInsuranceAmount());
				//保价费
				wayBillOneYearDetail.setInsurance(waybillOneYearDto.getInsuranceFee());
				//代收货款
				wayBillOneYearDetail.setCodAmount(waybillOneYearDto.getCodAmount());
				//代收货款手续费
				wayBillOneYearDetail.setCodFee(waybillOneYearDto.getCodFee());
				//接货费
				wayBillOneYearDetail.setConsignCharge(waybillOneYearDto.getPickupFee());
				//送货费
				wayBillOneYearDetail.setDeliveryCharge(waybillOneYearDto.getDeliveryGoodsFee());
				//包装费
				wayBillOneYearDetail.setPickCharge(waybillOneYearDto.getPackageFee());
				//装卸费
				wayBillOneYearDetail.setServiceFee(waybillOneYearDto.getServiceFee());
				//其他费用
				wayBillOneYearDetail.setOtherPayment(waybillOneYearDto.getOtherFee());
				//运单信息集合
				wayBillOneYearDetails.add(wayBillOneYearDetail);
				//运单号集合
				waybillNoList.add(waybillOneYearDto.getWaybillNo());
			}
			}
			//当运单集合不为空时，查询该客户编码对应的坏账信息
			if(waybillNoList.size()!=0){
				//查询坏账信list
				List<BillBadAccountEntity> billBadAccountEntityList = new ArrayList<BillBadAccountEntity>();
				billBadAccountEntityList = waybillManagerService.queryByWaybillNOs(waybillNoList);
					for(BillBadAccountEntity billBadAccountEntity : billBadAccountEntityList){
						if(billBadAccountEntity !=null){
						badWayBillDetail = new BadWayBillDetail();
						//坏账客户编码
						badWayBillDetail.setDeliveryCustomerCode(billBadAccountEntity.getFcustomerCode());
						//运单号
						badWayBillDetail.setWaybillNo(billBadAccountEntity.getWaybillNo());
						//应收单号
						badWayBillDetail.setStlPayNo(billBadAccountEntity.getReceivableNo());
						//坏账创建时间
						badWayBillDetail.setCreateTime(convertToXMLGregorianCalendar(billBadAccountEntity.getCreateTime()));
						//坏账金额
						badWayBillDetail.setCharg(billBadAccountEntity.getBadAmount());
						//返回esb坏账信息集合
						badWayBillDetails.add(badWayBillDetail);
					}
				}
			}
			queryOneYearDetailResponse.getWayBillOneYearDetails().addAll(wayBillOneYearDetails);
			queryOneYearDetailResponse.getBadWayBillDetails().addAll(badWayBillDetails);
			return queryOneYearDetailResponse;
			}
			return queryOneYearDetailResponse;
		}
		return null;
	}
	
  /**
   * @author 198719 - yetao
   * DMANA-5127快递差错类型细分及系统半自动化处理（OA）
   */
	@Override
	public OaQueryDetailResponse queryOADetail(Holder<ESBHeader> esbHeader,
			OaQueryDetailRequest oAQueryDetailRequest) throws CommonException {
		esbHeader.value.setResponseId(oAQueryDetailRequest.getWaybillNo().get(0));
		esbHeader.value.setResultCode(ONE); 
		
		if(oAQueryDetailRequest != null)
		{
			//ESB提供运单集合
			List<String> waybillList = oAQueryDetailRequest.getWaybillNo();
			//ESB返回对象
			OaQueryDetailResponse queryDetailResponse = new OaQueryDetailResponse();
			//运单信息集合
			List<OAWayBillDetail> wayBillDetailList = new ArrayList<OAWayBillDetail> ();
			//运单费用信息集合
			List<OAWaybillCostInfo> waybillCostInfos = new ArrayList<OAWaybillCostInfo> ();
			//ESB运单信息
			OAWayBillDetail wayBillDetail = null;
			//运单费用信息
			OAWaybillCostInfo waybillCostInfo = null;
			
			List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfo(waybillList);
			for(WaybillInfoDto waybillInfo : waybillInfoDtoList)
			{
				wayBillDetail =  new OAWayBillDetail();
				if(waybillInfo.getWaybillChargeCostDto() != null)
				{
					for (WaybillChargeCostDto waybillChargeCostDto : waybillInfo.getWaybillChargeCostDto())
					{
						//复制费用信息
						waybillCostInfo = new OAWaybillCostInfo();
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
				wayBillDetail.setSignedDate(convertToXMLGregorianCalendar(waybillInfo.getSignTime()));
				//第一次签收时间
				wayBillDetail.setFirstSignedDate(convertToXMLGregorianCalendar(waybillInfo.getFirstSignTime()));
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
				wayBillDetail.setSendTime(convertToXMLGregorianCalendar(waybillInfo.getBillTime()));
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
				
				//补码操作部门CODE
				wayBillDetail.setComplementOperationOrgCode(waybillInfo.getComplementOperationOrgCode());
				
				//补码操作部门名称
				wayBillDetail.setComplementOperationOrgName(waybillInfo.getComplementOperationOrgName());
				//当PDA快递员开单时，责任人和责任部门为快递员和其所在部门
			    if(waybillInfo.getStartExpressEmpCode()!=null){
			    	//责任人工号
			    	wayBillDetail.setResponsiblePersonCode(waybillInfo.getStartExpressEmpCode());
			    	//责任人名称
			    	wayBillDetail.setResponsiblePersonName(waybillInfo.getStartExpressEmpName());
			    	//责任部门code
			    	wayBillDetail.setResponsibleOrgCode(waybillInfo.getStartExpressOrgCode());
			    	//责任部门名称
			    	wayBillDetail.setResponsibleOrgName(waybillInfo.getStartExpressOrgName());
			    	//开单人code
			    	wayBillDetail.setCreateUserCode(waybillInfo.getStartExpressEmpCode());
			    	//开单人名称
			    	wayBillDetail.setCreateUserName(waybillInfo.getStartExpressEmpName());
			    	//开单人是否是快递员
			    	wayBillDetail.setIsCreateByExpress("Y");
			    }else//当非PDA快递员开单时，责任人和责任部门为营业部经理和其所在部门
			    	{
			    	//责任人code
			    	wayBillDetail.setResponsiblePersonCode(waybillInfo.getPrincipalNo());
			    	//责任人name
			    	wayBillDetail.setResponsiblePersonName(waybillInfo.getPrincipalName());
			    	//责任部门code
			    	wayBillDetail.setResponsibleOrgCode(waybillInfo.getCreateOrgCode());
			    	//责任部门name
			    	wayBillDetail.setResponsibleOrgName(waybillInfo.getPrincipalOrgName());
			    	//开单人code
			    	wayBillDetail.setCreateUserCode(waybillInfo.getCreateUserCode());
			    	//开单人名称
			    	wayBillDetail.setCreateUserName(waybillInfo.getCreateUserName());
			    	//开单人是否是快递员
			    	wayBillDetail.setIsCreateByExpress("N");
			    }
				
				wayBillDetailList.add(wayBillDetail);
				LOGGER.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(wayBillDetail));

			}
			queryDetailResponse.getOAwayBillDetailList().addAll(wayBillDetailList);
			return queryDetailResponse;
		}
		return null;
	}


	/**
	 * @author 200945 - wutao
	 * queryDetailForOffical
	 * 查询运单详情：专门给官网提货的接口信息;
	 *单独给官网写这个方法的原因：由于之前的接口为公共的接口；
	 *当在服务端增加字段，会导致其他系统调用次接口的时候报错，因此新增一个方法，单独给官网使用
	 */
	@Override
	public QueryDetailForOfficialResponse queryDetailForOfficial(
			Holder<ESBHeader> esbHeader, QueryDetailForOfficialRequest queryDetailForOfficialRequest)
			throws CommonException {
		esbHeader.value.setResponseId(queryDetailForOfficialRequest.getWaybillNo().get(0));
		esbHeader.value.setResultCode(ONE); 
		
		if(queryDetailForOfficialRequest != null)
		{
			//ESB提供运单集合
			List<String> waybillList = queryDetailForOfficialRequest.getWaybillNo();
			//ESB返回对象
			QueryDetailForOfficialResponse queryDetailForOfficialResponse = new QueryDetailForOfficialResponse();
			//运单信息集合
			List<WayBillDetailForOfficial> wayBillDetailList = new ArrayList<WayBillDetailForOfficial> ();
			//运单费用信息集合
			List<WaybillCostInfoForOfficial> waybillCostInfos = new ArrayList<WaybillCostInfoForOfficial> ();
			//ESB运单信息
			WayBillDetailForOfficial wayBillDetail = null;
			//运单费用信息
			WaybillCostInfoForOfficial waybillCostInfo = null;
			
			List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfo(waybillList);
			if(CollectionUtils.isNotEmpty(waybillInfoDtoList)) {
				for(WaybillInfoDto infoDto : waybillInfoDtoList) {
					if(StringUtils.isNotEmpty(infoDto.getWaybillNo())) {
						if(StringUtils.isEmpty(infoDto.getOriginalWaybillNo())) {
							WaybillExpressEntity  entity = waybillExpressService.queryWaybillByOriginalWaybillNo(infoDto.getWaybillNo(), 
									WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
							if(entity != null && StringUtils.isNotEmpty(entity.getOriginalWaybillNo())) {
								infoDto.setOriginalWaybillNo(entity.getWaybillNo());
							}
						}
					}
					
				}
			}
			for(WaybillInfoDto waybillInfo : waybillInfoDtoList)
			{
				wayBillDetail =  new WayBillDetailForOfficial();
				if(waybillInfo.getWaybillChargeCostDto() != null)
				{
					for (WaybillChargeCostDto waybillChargeCostDto : waybillInfo.getWaybillChargeCostDto())
					{
						//复制费用信息
						waybillCostInfo = new WaybillCostInfoForOfficial();
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
				/**
				 * @author 200945 - wutao
				 * 发货客户为大客户标示，返回给官网
				 */
				wayBillDetail.setIsBigDeliverCustomer(waybillInfo.getIsBigDeliverCustomer());
				/**
				 * @author 200945 - wutao
				 * 返货类型
				 */
				wayBillDetail.setReturnType(waybillInfo.getReturnType());
				/**
				 * @author 200945 - wutao
				 * 返货:原运单号
				 */
				wayBillDetail.setOriginalWaybillNo(waybillInfo.getOriginalWaybillNo());
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
				//收货地址备注
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
				wayBillDetail.setSignedDate(convertToXMLGregorianCalendar(waybillInfo.getSignTime()));
				//第一次签收时间
				wayBillDetail.setFirstSignedDate(convertToXMLGregorianCalendar(waybillInfo.getFirstSignTime()));
				//签收备注
				wayBillDetail.setSignedDesc(waybillInfo.getSignNote());
				
				//update by foss-231434-bieyexiong 非异常弃货，直接发送签收信息
				if(!ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(waybillInfo.getSignSituation())){
					//运单状态
					wayBillDetail.setOrderState(waybillInfo.getActive());
				}else if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillInfo.getProductCode(),null)){
					//(需求DN201603140026只针对零担) 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
					List<BillPayableEntity> billPayables = billPayableService.queryByWaybillNosAndByBillTypes(
							Arrays.asList(waybillInfo.getWaybillNo()),
							Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM));
					//异常弃货，且有上报理赔，才发送签收信息
					if(CollectionUtils.isNotEmpty(billPayables)){
						//运单状态
						wayBillDetail.setOrderState(waybillInfo.getActive());
					}
				}

				//订单号
				wayBillDetail.setOrderNumber(waybillInfo.getOrderNo());
				//保价声明
				wayBillDetail.setInsuranceValue(waybillInfo.getInsuranceAmount());
				//保价手续费
				wayBillDetail.setInsurance(waybillInfo.getInsuranceFee());
				//货物包装
				wayBillDetail.setPacking(waybillInfo.getGoodsPackage());
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
				wayBillDetail.setSendTime(convertToXMLGregorianCalendar(waybillInfo.getBillTime()));
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
				
				//wayBillDetail.set
				
				
				wayBillDetailList.add(wayBillDetail);
				LOGGER.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(wayBillDetail));
			}
			queryDetailForOfficialResponse.getWayBillDetailList().addAll(wayBillDetailList);
			return queryDetailForOfficialResponse;
		}
		return null;
	}
	
}

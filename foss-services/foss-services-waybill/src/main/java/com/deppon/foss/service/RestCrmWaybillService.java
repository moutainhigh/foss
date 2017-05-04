package com.deppon.foss.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WayBillDetailForCrm;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCostInfoForCrm;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillChargeCostDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.CollectionUtils;

/**
 * rest风格接口(供crm调用)
 * @author foss-218438
 */
//请求数据格式为json
@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN })
//响应数据格式为json
@Produces("application/json;charset=UTF-8") 
public class RestCrmWaybillService {
	/**
     * 日志
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestWaybillServiceImpl.class.getName());
	/**
	 *查询运单详情
	 */
	private IWaybillQueryService waybillQueryService;
	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}
	/**
	 * 查询运单
	 */
	private IWaybillManagerService waybillManagerService;
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	private IWaybillExpressService waybillExpressService;
	
	public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	/**
	 * 查询运单派送信息列表，用于运单轨迹查询
	 */
	private IDeliverbillService deliverbillService;
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	
	/**
	 * 查询运单实际承运信息
	 * */
    private IActualFreightService  actualFreightService;
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
	/**根据区县编码查询区县名称*/
	private IAdministrativeRegionsService administrativeRegionsService; 
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	/**
	 * 查询到达部门时间
	 */
	private IStockService stockService;
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}


	/**
	 * 新增运单详情接口
	 */
	@POST
	@Path("/waybilldetail")
	public @ResponseBody String waybilldetail(
			@RequestBody String waybillNo,@Context HttpServletResponse response){
		//返回值
		String r="";
	    //运单信息集合
		List<WayBillDetailForCrm> wayBillDetailForCrmList = new ArrayList<WayBillDetailForCrm> ();
		//运单费用信息集合
		List<WaybillCostInfoForCrm> waybillCostInfosForCrm = new ArrayList<WaybillCostInfoForCrm> ();
	   //ESB运单信息
		WayBillDetailForCrm wayBillDetailForCrm = null;
		//运单费用信息
		WaybillCostInfoForCrm waybillCostInfoForCrm = null;
//		//运单详情
//		WaybillDetaillListEntity waybillDetailInfo=new WaybillDetaillListEntity();
		//判断传入运单号是否为空
		if(StringUtils.isNotEmpty(waybillNo)){
			//判断运单号是否已开单
			if(waybillManagerService.isWayBillExsits(waybillNo.trim())){
				//判断运单号是否存在承运信息
				ActualFreightEntity actualFreightEntity =actualFreightService.queryByWaybillNo(waybillNo.trim());
				if(actualFreightEntity!=null){
			    //将运单号封装成运单集合
				List<String> waybillList =new  ArrayList<String>();
				waybillList.add(waybillNo);
				//查询运单详情
				List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfo(waybillList);
				if(waybillInfoDtoList!=null && waybillInfoDtoList.size()!=0){
			   //遍历运单详情
				for(WaybillInfoDto waybillInfo : waybillInfoDtoList){
					if(waybillInfo!=null){
						wayBillDetailForCrm =  new WayBillDetailForCrm();
						//运单派送信息列表
						List<WaybillDeliveryDto> WaybillDeliveryDtoList = deliverbillService.queryWaybillDeliveryListByWaybillNo(waybillInfo.getWaybillNo());
						//快递与零担区分 确认到达时间
						if(WaybillConstants.YES.equals(waybillInfo.getIsExpress())){
								//如果有签收时间取签收时间，否则取派送时间	
							if(waybillInfo.isSigned()){
								wayBillDetailForCrm.setArriveDate(waybillInfo.getSignTime());
							}else{
								if(CollectionUtils.isNotEmpty(WaybillDeliveryDtoList)){									
									for(WaybillDeliveryDto waybillDeliveryDto:WaybillDeliveryDtoList){
										if(DeliverbillConstants.STATUS_CONFIRMED.equals(waybillDeliveryDto.getStatus())){
											wayBillDetailForCrm.setArriveDate(waybillDeliveryDto.getOperateTime());
										}
									}
								}
							}							
						}else{
							List<InOutStockEntity> list=stockService.queryInStockInfo(waybillNo, null, waybillInfo.getCustomerPickupOrgCode(), waybillInfo.getBillTime());
							if(CollectionUtils.isNotEmpty(list)){								
								wayBillDetailForCrm.setArriveDate(list.get(0).getInOutStockTime());
							}
						}
						//设置承诺到达时间
						//线上BUG 与综合查询保持一致都取preCustomerPickupTime
						wayBillDetailForCrm.setPreArriveTime(waybillInfo.getPreCustomerPickupTime());
//						wayBillDetailForCrm.setPreArriveTime(waybillInfo.getPreArriveTime());
						if(waybillInfo.getWaybillChargeCostDto()!=null && waybillInfo.getWaybillChargeCostDto().size()!=0 )
						{
							for (WaybillChargeCostDto waybillChargeCostDto : waybillInfo.getWaybillChargeCostDto())
							{
								//复制费用信息
								waybillCostInfoForCrm = new WaybillCostInfoForCrm();
								waybillCostInfoForCrm.setCostType(waybillChargeCostDto.getCostType());
								waybillCostInfoForCrm.setCostName(waybillChargeCostDto.getCostName());
								waybillCostInfoForCrm.setCostMoney(waybillChargeCostDto.getCostMoney());
								waybillCostInfosForCrm.add(waybillCostInfoForCrm);
							}
						}
						wayBillDetailForCrm.setWaybillCostInfos(waybillCostInfosForCrm);
						//设置运单号
						wayBillDetailForCrm.setNumber(waybillInfo.getWaybillNo());
						//设置运输类型(产品)
						wayBillDetailForCrm.setTranType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillInfo.getProductCode()));
						//设置运输性质
						wayBillDetailForCrm.setTranProperty(waybillInfo.getProductCode());
						//发货联系人
						wayBillDetailForCrm.setSender(waybillInfo.getDeliveryCustomerName());
						//发货人电话
						wayBillDetailForCrm.setSenderPhone(waybillInfo.getDeliveryCustomerPhone());
						//发货客户手机
						wayBillDetailForCrm.setSenderMobile(waybillInfo.getDeliveryCustomerMobilephone());
						//始发站
						wayBillDetailForCrm.setDeparture(waybillInfo.getDeliveryCustomerCityName());
						//发货人地址
						wayBillDetailForCrm.setSenderAddress(waybillInfo.getDeliveryCustomerAddress());
						//发货人地址备注
						wayBillDetailForCrm.setSenderAddressNote(waybillInfo.getDeliveryCustomerAddressNote());
						//收货人
						wayBillDetailForCrm.setConsignee(waybillInfo.getReceiveCustomerName());
						//收货人电话
						wayBillDetailForCrm.setConsigneePhone(waybillInfo.getReceiveCustomerPhone());
						//收货人手机
						wayBillDetailForCrm.setConsigneeMobile(waybillInfo.getReceiveCustomerMobilephone());
						//目的站
						wayBillDetailForCrm.setDestination(waybillInfo.getTargetOrgCode());
						//收货人地址
						wayBillDetailForCrm.setConsigneeAddress(waybillInfo.getReceiveCustomerAddress());
						//收货人地址备注
						wayBillDetailForCrm.setConsigneeAddressNote(waybillInfo.getReceiveCustomerAddressNote());
						//货物名称
						wayBillDetailForCrm.setGoodName(waybillInfo.getGoodsName());
						//件数
						wayBillDetailForCrm.setPieces(waybillInfo.getGoodsQtyTotal());
						//重量
						wayBillDetailForCrm.setWeight(Float.parseFloat(waybillInfo.getGoodsWeightTotal().toString()));
						//体积
						wayBillDetailForCrm.setCubage(Float.parseFloat(waybillInfo.getGoodsVolumeTotal().toString()));
						//总费用
						wayBillDetailForCrm.setTotalCharge(waybillInfo.getTotalFee());
						//付款方式
						wayBillDetailForCrm.setPayment(waybillInfo.getPaidMethod());
						//预付金额
						wayBillDetailForCrm.setPreCharge(waybillInfo.getPrePayAmount());
						//到付金额
						wayBillDetailForCrm.setArriveCharge(waybillInfo.getToPayAmount());
						//代收货款类型
						wayBillDetailForCrm.setRefundType(waybillInfo.getRefundType());
						//代收货款
						wayBillDetailForCrm.setRefund(waybillInfo.getCodAmount());
						//代收货款手续费
						wayBillDetailForCrm.setRefundFee(waybillInfo.getCodFee());
						//开单提货方式
						wayBillDetailForCrm.setDeliveryType(waybillInfo.getReceiveMethod());
						//接货费
						wayBillDetailForCrm.setConsignCharge(waybillInfo.getPickupFee());
						//送货费
						wayBillDetailForCrm.setDeliveryCharge(waybillInfo.getDeliveryGoodsFee());
						//包装费
						wayBillDetailForCrm.setPickCharge(waybillInfo.getPackageFee());
						//装卸费
						wayBillDetailForCrm.setLaborRebate(waybillInfo.getServiceFee());
						//公布价运费
						wayBillDetailForCrm.setPublishCharge(waybillInfo.getTransportFee());
						//出发部门名称
						wayBillDetailForCrm.setDepartureDeptName(waybillInfo.getDepartureDeptName());
						//出发部门标杆编码
						wayBillDetailForCrm.setDepartureDeptNumber(waybillInfo.getDepartureDeptNumber());
						//出发部门地址
						wayBillDetailForCrm.setDepartureDeptAddr(waybillInfo.getDepartureDeptAddr());
						//出发部门电话
						wayBillDetailForCrm.setDepartureDeptPhone(waybillInfo.getDepartureDeptPhone());
						//出发部门传真
						wayBillDetailForCrm.setDepartureDeptFax(waybillInfo.getDepartureDeptFax());
						//到达网点名称
						wayBillDetailForCrm.setLadingStationName(waybillInfo.getLadingStationName());
						//到达网点标杆编码
						wayBillDetailForCrm.setLadingStationNumber(waybillInfo.getLadingStationNumber());
						//到达网点地址
						wayBillDetailForCrm.setLadingStationAddr(waybillInfo.getLadingStationAddr());
						//到达网点电话
						wayBillDetailForCrm.setLadingStationPhone(waybillInfo.getLadingStationPhone());
						//到达网点传真
						wayBillDetailForCrm.setLadingStationFax(waybillInfo.getLadingStationFax());
						//是否签收
						wayBillDetailForCrm.setIsSigned(waybillInfo.isSigned());
						//是否正常签收
						wayBillDetailForCrm.setIsNormalSigned(waybillInfo.isNormalSigned());
						//签收录入人
						wayBillDetailForCrm.setSignRecorderId(waybillInfo.getDeliverymanName());
						//签收时间
						wayBillDetailForCrm.setSignedDate(waybillInfo.getSignTime());
						//第一次签收时间
						wayBillDetailForCrm.setFirstSignedDate(waybillInfo.getFirstSignTime());
						//签收备注
						wayBillDetailForCrm.setSignedDesc(waybillInfo.getSignNote());
						//订单号
						wayBillDetailForCrm.setOrderNumber(waybillInfo.getOrderNo());
						//保价声明
						wayBillDetailForCrm.setInsuranceValue(waybillInfo.getInsuranceAmount());
						//保价手续费
						wayBillDetailForCrm.setInsurance(waybillInfo.getInsuranceFee());
						//货物包装
						wayBillDetailForCrm.setPacking(waybillInfo.getGoodsPackage());
						//运单状态
						wayBillDetailForCrm.setOrderState(waybillInfo.getActive());
						//其它费用
						wayBillDetailForCrm.setOtherPayment(waybillInfo.getOtherFee());
						//托运备注
						wayBillDetailForCrm.setTranDesc(waybillInfo.getOuterNotes());
						//发货客户编码
						wayBillDetailForCrm.setSenderNumber(waybillInfo.getDeliveryCustomerCode());
						//收货客户编码
						wayBillDetailForCrm.setConsigneeNumber(waybillInfo.getReceiveCustomerCode());
						//是否已结款
						wayBillDetailForCrm.setIsClear(waybillInfo.getSettleStatus());
						//返单类型
						wayBillDetailForCrm.setSignBackType(waybillInfo.getReturnBillType());
						//储运事项
						wayBillDetailForCrm.setTransNotice(waybillInfo.getTransportationRemark());
						//发货时间
						wayBillDetailForCrm.setSendTime(waybillInfo.getBillTime());
						//收货部门名称
						wayBillDetailForCrm.setReceiveDeptName(waybillInfo.getReceiveOrgName());
						//收货部门标杆代码
						wayBillDetailForCrm.setReceiveDeptNumber(waybillInfo.getReceiveOrgNumber());
						//配载部门标杆编码
						wayBillDetailForCrm.setStowageDept(waybillInfo.getLoadOrgNumber());
						//发货人城市编码
						wayBillDetailForCrm.setSenderCityCode(waybillInfo.getDeliveryCustomerCityCode1());
						//发货人城市名称
						wayBillDetailForCrm.setSenderCityName(waybillInfo.getDeliveryCustomerCityName1());
						//发货人省份编码
						wayBillDetailForCrm.setSenderProvinceCode(waybillInfo.getDeliveryCustomerProvCode());
						//发货人省份名称
						wayBillDetailForCrm.setSenderProvinceName(waybillInfo.getDeliveryCustomerProvName());
						//收货人城市编码
						wayBillDetailForCrm.setConsigneeCityCode(waybillInfo.getReceiveCustomerCityCode());
						//收货人城市名称
						wayBillDetailForCrm.setConsigneeCityName(waybillInfo.getReceiveCustomerCityName());
						//收货人省份编码
						wayBillDetailForCrm.setConsigneeProvinceCode(waybillInfo.getReceiveCustomerProvCode());
						//收货人省份名称
						wayBillDetailForCrm.setConsigneeProvinceName(waybillInfo.getReceiveCustomerProvName());
						//是否上门接货
						wayBillDetailForCrm.setIsDoorToDoorPick(waybillInfo.isPickup());
						//短信通知状态
						wayBillDetailForCrm.setSmsNoticeResult(waybillInfo.getNotificationResult());
						//签收单返回方式
						wayBillDetailForCrm.setSignBillBackWay(waybillInfo.getReturnBillType());
						
						
						//小件 新加的逻辑----------------------
						//运单所属快递大区编码
						wayBillDetailForCrm.setExDepartureRegionNubmer(waybillInfo.getDistrictCode());
						//运单所属快递大区名称
						wayBillDetailForCrm.setExDepartureRegionName(waybillInfo.getDistrictName());
						//运单所属快递大区标杆编码
						wayBillDetailForCrm.setExDepartureRegionStandardNubmer(waybillInfo.getDistrictUnifiedCode());
						
						//运单所属快递大区编码
						wayBillDetailForCrm.setExDestinationRegionNubmer(waybillInfo.getEndDistrictCode());
						//运单所属快递大区名称
						wayBillDetailForCrm.setExDestinationRegionName(waybillInfo.getEndDistrictName());
						//运单所属快递大区标杆编码
						wayBillDetailForCrm.setExDestinationRegionStandardNubmer(waybillInfo.getEndDistrictUnifiedCode());
						
						
						
						//快递员CODE-出发
						wayBillDetailForCrm.setExDepartureCourierNumber(waybillInfo.getStartExpressEmpCode());
						//快递员名称-出发
						wayBillDetailForCrm.setExDepartureCourierName(waybillInfo.getStartExpressEmpName());
						//快递点部CODE-出发
						wayBillDetailForCrm.setExDepartureDeptNumber(waybillInfo.getStartExpressOrgCode());
						//快递点部标杆编码-出发
						wayBillDetailForCrm.setExDepartureDeptStandardNumber(waybillInfo.getStartExpressUnfiedCode());
						//快递点部名称-出发
						wayBillDetailForCrm.setExDepartureDeptName(waybillInfo.getStartExpressOrgName());
						
						//快递员CODE-到达
						wayBillDetailForCrm.setExDestinationCourierNumber(waybillInfo.getEndExpressEmpCode());
						
						//快递员名称-到达
						wayBillDetailForCrm.setExDestinationCourierName(waybillInfo.getEndExpressEmpName());
						//快递点部CODE-到达
						wayBillDetailForCrm.setExDestinationDeptNumber(waybillInfo.getEndExpressOrgCode());
						
						//快递点部名称-到达
						wayBillDetailForCrm.setExDestinationDeptName(waybillInfo.getEndExpressOrgName());
						
						//快递点部标杆编码-到达
						wayBillDetailForCrm.setExDestinationDeptStandardNumber(waybillInfo.getEndExpressUnfiedCode());
						
						//发货城市区县编码
						wayBillDetailForCrm.setDepartureDistrictCode(waybillInfo.getDeliveryCustomerDistCode());
						
						//发货城市区县名称
						if(waybillInfo.getDeliveryCustomerDistCode() != null){
				    		AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(waybillInfo.getDeliveryCustomerDistCode());
				    		if(city != null){
								wayBillDetailForCrm.setDepartureDistrictName(StringUtil.defaultIfNull(city.getName()));
				    		}
				    	}else{
				    		wayBillDetailForCrm.setDepartureDistrictName(null);
				    	}
						
						//收货城市区县编码
						wayBillDetailForCrm.setConsigneeDistrictCode(waybillInfo.getReceiveCustomerDistCode());
						
						//收货城市区县名称
						if(waybillInfo.getReceiveCustomerDistCode() != null){
				    		AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(waybillInfo.getReceiveCustomerDistCode());
				    		if(city != null){
								wayBillDetailForCrm.setConsigneeDistrictName(StringUtil.defaultIfNull(city.getName()));
				    		}
				    	}else{
				    		wayBillDetailForCrm.setConsigneeDistrictName(null);
				    	}
						//发货客户为大客户标示
						wayBillDetailForCrm.setIsBigDeliverCustomer(waybillInfo.getIsBigDeliverCustomer());
						//返货类型
						wayBillDetailForCrm.setReturnType(waybillInfo.getReturnType());
						//返货:原运单号
						wayBillDetailForCrm.setOriginalWaybillNo(waybillInfo.getOriginalWaybillNo());
						//签收回单费(此属性CRM与原本的老接口中都没用到，避免报错，暂且给个0)
						wayBillDetailForCrm.setSignBackCharge(new BigDecimal(0));
						wayBillDetailForCrmList.add(wayBillDetailForCrm);
						LOGGER.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(wayBillDetailForCrm));
					}	
				}
				  r = JSONObject.toJSONString(wayBillDetailForCrmList);
				  response.setHeader("ESB-ResultCode","1");
				  return r;
				}else{
					r = JSONObject.toJSONString(wayBillDetailForCrmList);
					response.setHeader("ESB-ResultCode","1");
					return r;
				}	
			}else{
				r = JSONObject.toJSONString(wayBillDetailForCrmList);
				response.setHeader("ESB-ResultCode","1");
				return r;
			}
		}else{
          r = JSONObject.toJSONString(wayBillDetailForCrmList);
          response.setHeader("ESB-ResultCode","1");
          return r;
		}
	}else{
		r = JSONObject.toJSONString(wayBillDetailForCrmList);
		response.setHeader("ESB-ResultCode","1");
		return r;
	}
  }
	
}

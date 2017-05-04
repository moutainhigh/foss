package com.deppon.foss.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.IQmsErrorService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.BillingListEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ComponentListEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WayBillDetailForApp;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillBillingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillComponentDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillComponentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCostInfoForApp;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDetaillListEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BillingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ComponentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ReportBillingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ReportComponentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillChargeCostDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao;
import com.deppon.foss.module.transfer.unload.api.server.service.ITfrForQmsService;
import com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * rest风格接口（供APP调用）
 * @author 198719-foss-yetao
 * @date 2014-11-6 上午9:26:39
 */

//请求数据格式为json
@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN })
//响应数据格式为json
@Produces("application/json;charset=UTF-8") 
public class RestWaybillServiceImpl{
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
	
	
	private IWaybillExpressService waybillExpressService;
	
	public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**根据区县编码查询区县名称*/
	private IAdministrativeRegionsService administrativeRegionsService; 
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * 查询运单实际承运信息
	 * */
    private IActualFreightService  actualFreightService;
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
	/**
	 * QMS差错服务中转入口
	 */
	private ITfrForQmsService tfrForQmsService;
	public void setTfrForQmsService(ITfrForQmsService tfrForQmsService) {
		this.tfrForQmsService = tfrForQmsService;
	}
	

	/**
	 * QMS差错服务结算入口
	 */
	private IQmsErrorService qmsErrorService;
	public void setQmsErrorService(IQmsErrorService qmsErrorService) {
		this.qmsErrorService = qmsErrorService;
	}
	
	/**
	 * 应付单服务.
	 */
	private IBillPayableService billPayableService;
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	/**
	 * 运单库存
	 */
	private IWaybillStockDao waybillStockDao;
	public void setWaybillStockDao(IWaybillStockDao waybillStockDao) {
		this.waybillStockDao = waybillStockDao;
	}
	/**
	 * 客户信息
	 */
	private ICustomerDao customerDao;
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	/**
	 * 通过复杂的流程查询组织
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
     * FOSS根据APP查询参数按照费用构成对客户查询月份发货记录进行分析统计
     * @author 198719-foss-yetao
     * @date 2014-11-06
     * @param mibilephone
     * @param startTime
     * @param endTime
     * @return
     */
	@GET
	@Path("/reportComponent")
	public WaybillComponentEntity reportComponent(
			@QueryParam("mibilephone") String mibilephone,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,
			@Context HttpServletResponse response){
		//返回给App的结果 费用构成信息
		WaybillComponentEntity waybillComponent = new WaybillComponentEntity();
		if(StringUtils.isNotEmpty(mibilephone) && startTime!=null && endTime!=null){
			//将传入参数封装到查询条件dto中
			ReportComponentDto reportComponentDto = new ReportComponentDto();
			reportComponentDto.setMibilephone(mibilephone);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(startTime!=null){
				//偏线日期
				Date dstartTime = null;
				try {
					dstartTime = sdf.parse(startTime);
				}catch(Exception e){
					
				}				
				reportComponentDto.setStartTime(dstartTime);				
			}
			if(endTime!=null){
				//偏线日期
				Date dendTime = null;
				try {
					dendTime = sdf.parse(endTime);
				}catch(Exception e){
					
				}				
				reportComponentDto.setEndTime(dendTime);				
			}			
			//reportComponentDto.setStartTime(startTime);
			//reportComponentDto.setEndTime(endTime);
			reportComponentDto.setActive(FossConstants.YES);
			List<ComponentListEntity>  componentList=new ArrayList<ComponentListEntity>();
			//后台根据条件查询各种费用的单个总和
			ComponentDto componentDtoList = waybillQueryService.queryComponentDetail(reportComponentDto);
			if(componentDtoList !=null && componentDtoList.getTransportFee()!=null){
				//设置接货费及总和
				ComponentListEntity componentPickupFee = new ComponentListEntity();
				componentPickupFee.setComponent_name("接货费");
				componentPickupFee.setAmount(componentDtoList.getPickupFee());
				componentList.add(componentPickupFee);
				//设置送货费及总和
				ComponentListEntity componentDeliveryGoodsFee = new ComponentListEntity();
				componentDeliveryGoodsFee.setComponent_name("送货费");
				componentDeliveryGoodsFee.setAmount(componentDtoList.getDeliveryGoodsFee());
				componentList.add(componentDeliveryGoodsFee);
				//设置包装费及总和
				ComponentListEntity componentPackageFee = new ComponentListEntity();
				componentPackageFee.setComponent_name("包装费");
				componentPackageFee.setAmount(componentDtoList.getPackageFee());
				componentList.add(componentPackageFee);
				//设置公布价运费及总和
				ComponentListEntity componentTransportFee = new ComponentListEntity();
				componentTransportFee.setComponent_name("运费");
				componentTransportFee.setAmount(componentDtoList.getTransportFee());
				componentList.add(componentTransportFee);
				//设置保价费及总和
				ComponentListEntity componentInsuranceFee = new ComponentListEntity();
				componentInsuranceFee.setComponent_name("保价费");
				componentInsuranceFee.setAmount(componentDtoList.getInsuranceFee());
				componentList.add(componentInsuranceFee);
				//设置代收货款及总和
				ComponentListEntity componentCodFee = new ComponentListEntity();
				componentCodFee.setComponent_name("代收货款手续费");
				componentCodFee.setAmount(componentDtoList.getCodFee());
				componentList.add(componentCodFee);
				//设置其他费用及总和
				ComponentListEntity componentOtherFee = new ComponentListEntity();
				componentOtherFee.setComponent_name("其他费用");
				componentOtherFee.setAmount(componentDtoList.getOtherFee());
				componentList.add(componentOtherFee);
				//设置处理明细
				WaybillComponentDetailEntity waybillDetail = new WaybillComponentDetailEntity();
				//费用构成列表
				waybillDetail.setComponentList(componentList);
				//运单总票数
				waybillDetail.setTotal_count(componentDtoList.getCountWaybill());
				//运单费用总支出
				waybillDetail.setTotal_amount(componentDtoList.getTotal_amount());
				//查询结果不为空时返回以下数据
				//消息代码成功为“10000”
				waybillComponent.setMessage_code("10000");
				//消息代码说明
				waybillComponent.setMessage_detail("Get information succeed");
				//返回记录的条数
				waybillComponent.setCount(componentList.size());
				//处理明细
				waybillComponent.setDetail(waybillDetail);
				response.setHeader("ESB-ResultCode","1");
				return waybillComponent;
			}else{
				//消息代码查询失败为“00000”
				waybillComponent.setMessage_code("00000");
				//消息代码说明
				waybillComponent.setMessage_detail("Get information failed,information is null");
				//返回记录的条数
				waybillComponent.setCount(0);
				//处理明细
				waybillComponent.setDetail(null);
				response.setHeader("ESB-ResultCode","1");
				return waybillComponent;
			}
		}else{
		//消息代码查询参数无效"20000"
		waybillComponent.setMessage_code("20000");
		//消息代码说明(参数为空或者接收不到参数)
		waybillComponent.setMessage_detail("One of the parameters is null or FOSS can't get the parameters from APP");
		//返回记录的条数
		waybillComponent.setCount(0);
		//处理明细
		waybillComponent.setDetail(null);
		response.setHeader("ESB-ResultCode","1");
		return waybillComponent;
		}
	}
	
	@GET
	@Path("/reportBilling")
	public WaybillBillingEntity reportBilling(
			@QueryParam("mibilephone") String mibilephone,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,
			@QueryParam("page_index") Integer page_index,
			@QueryParam("page_size") Integer page_size,
			@Context HttpServletResponse response){
		WaybillBillingEntity waybillBilling =new WaybillBillingEntity();
		
		if(StringUtils.isNotEmpty(mibilephone) && startTime!=null && endTime!=null && page_index!=null && page_size !=null){
			//将参数封装到查询dto中
			ReportBillingDto reportBillingDto =new ReportBillingDto();
			reportBillingDto.setMibilephone(mibilephone);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(startTime!=null){
				//偏线日期
				Date dstartTime = null;
				try {
					dstartTime = sdf.parse(startTime);
				}catch(Exception e){
					
				}				
				reportBillingDto.setStartTime(dstartTime);				
			}
			if(endTime!=null){
				//偏线日期
				Date dendTime = null;
				try {
					dendTime = sdf.parse(endTime);
				}catch(Exception e){
					
				}				
				reportBillingDto.setEndTime(dendTime);				
			}			
//			reportBillingDto.setStartTime(startTime);
//			reportBillingDto.setEndTime(endTime);
			reportBillingDto.setPage_index(page_index);
			reportBillingDto.setPage_size(page_size);
			reportBillingDto.setActive(FossConstants.YES);
			//后台根据条件查询用户电子账单
			List<BillingDto> billingDtoList = waybillQueryService.queryBillingDetail(reportBillingDto);
			List<BillingListEntity> billingListEntityList = new ArrayList<BillingListEntity>();
			if(billingDtoList.size()!=0){
				for(BillingDto billingDto : billingDtoList){
					if(billingDto!=null){
						BillingListEntity billingListEntity = new BillingListEntity();
						//运单号
						billingListEntity.setWaybillNo(billingDto.getWaybillNo());
						//发货时间
						billingListEntity.setShipping_date(billingDto.getShipping_date());
						//支付方式代码
						billingListEntity.setPay_type_code(billingDto.getPay_type_code());
						//支付方式名称
						billingListEntity.setPay_type_name(billingDto.getPay_type_name());
						//开单金额
						billingListEntity.setTotal_amount(billingDto.getTotal_amount());
						//运输性质
						billingListEntity.setTrans_type(billingDto.getTrans_type());
						//货物名称
						billingListEntity.setCargo_name(billingDto.getCargo_name());
						//发货城市
						billingListEntity.setSender_city_name(billingDto.getSender_city_name());
						//到达城市
						billingListEntity.setConsignee_city_name(billingDto.getConsignee_city_name());
						//货物件数
						billingListEntity.setCargo_count(billingDto.getCargo_count());
						//发货人姓名
						billingListEntity.setPoster_name(billingDto.getPoster_name());
						//收货人姓名
						billingListEntity.setReceiver_name(billingDto.getReceiver_name());
						//电子账单详细
						billingListEntityList.add(billingListEntity);	
					}	
				}
				//消息代码成功为“10000”
				waybillBilling.setMessage_code("10000");
				//消息代码说明
				waybillBilling.setMessage_detail("Get information succeed");
				//返回记录的条数
				waybillBilling.setCount(billingListEntityList.size());
				//处理明细
				waybillBilling.setDetail(billingListEntityList);
				response.setHeader("ESB-ResultCode","1");
				return waybillBilling;
			}else{
				//消息代码失败为“00000”
				waybillBilling.setMessage_code("00000");
				//消息代码说明
				waybillBilling.setMessage_detail("Get information failed,information is null");
				//返回记录的条数
				waybillBilling.setCount(0);
				//处理明细
				waybillBilling.setDetail(null);
				response.setHeader("ESB-ResultCode","1");
				return waybillBilling;
			}		
		}else{
			//消息代码失败为“20000”
			waybillBilling.setMessage_code("20000");
			//消息代码说明
			waybillBilling.setMessage_detail("One of the parameters is null or FOSS can't get the parameters from APP");
			//返回记录的条数
			waybillBilling.setCount(0);
			//处理明细
			waybillBilling.setDetail(null);	
			response.setHeader("ESB-ResultCode","1");
			return waybillBilling;
		}
	}
	/**
	 * @author foss-LiuTao
	 * @date 2015-01-07 下午7:44:25
	 * DMANA-10676 DP-FOSS-新增运单详情接口需求
	 * */
	@GET
	@Path("/waybilldetail")
	public WaybillDetaillListEntity waybilldetail(
			@QueryParam("waybillNo") String waybillNo,
			@Context HttpServletResponse response){
	    //运单信息集合
		List<WayBillDetailForApp> wayBillDetailForAppList = new ArrayList<WayBillDetailForApp> ();
		//运单费用信息集合
		List<WaybillCostInfoForApp> waybillCostInfosForApp = new ArrayList<WaybillCostInfoForApp> ();
	   //ESB运单信息
		WayBillDetailForApp wayBillDetailForApp = null;
		//运单费用信息
		WaybillCostInfoForApp waybillCostInfoForApp = null;
		//运单详情
		WaybillDetaillListEntity waybillDetailInfo=new WaybillDetaillListEntity();
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
						wayBillDetailForApp =  new WayBillDetailForApp();
						if(waybillInfo.getWaybillChargeCostDto()!=null && waybillInfo.getWaybillChargeCostDto().size()!=0 )
						{
							for (WaybillChargeCostDto waybillChargeCostDto : waybillInfo.getWaybillChargeCostDto())
							{
								//复制费用信息
								waybillCostInfoForApp = new WaybillCostInfoForApp();
								waybillCostInfoForApp.setCostType(waybillChargeCostDto.getCostType());
								waybillCostInfoForApp.setCostName(waybillChargeCostDto.getCostName());
								waybillCostInfoForApp.setCostMoney(waybillChargeCostDto.getCostMoney());
								waybillCostInfosForApp.add(waybillCostInfoForApp);
							}
						}
						wayBillDetailForApp.setWaybillCostInfos(waybillCostInfosForApp);
						//wayBillDetailForApp.getWaybillCostInfos().addAll(waybillCostInfosForApp);
						//设置运单号
						wayBillDetailForApp.setNumber(waybillInfo.getWaybillNo());
						/**
						 * KDTE-5323 快递单号待补录状态时FOSS与CRM系统信息不一致
						 */
						//设置运输类型(产品)
						wayBillDetailForApp.setTranType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillInfo.getProductCode()));
						//设置运输性质
						wayBillDetailForApp.setTranProperty(waybillInfo.getProductCode());
						//发货联系人
						wayBillDetailForApp.setSender(waybillInfo.getDeliveryCustomerName());
						//发货人电话
						wayBillDetailForApp.setSenderPhone(waybillInfo.getDeliveryCustomerPhone());
						//发货客户手机
						wayBillDetailForApp.setSenderMobile(waybillInfo.getDeliveryCustomerMobilephone());
						//始发站
						wayBillDetailForApp.setDeparture(waybillInfo.getDeliveryCustomerCityName());
						//发货人地址
						wayBillDetailForApp.setSenderAddress(waybillInfo.getDeliveryCustomerAddress());
						//发货人地址备注
						wayBillDetailForApp.setSenderAddressNote(waybillInfo.getDeliveryCustomerAddressNote());
						//收货人
						wayBillDetailForApp.setConsignee(waybillInfo.getReceiveCustomerName());
						//收货人电话
						wayBillDetailForApp.setConsigneePhone(waybillInfo.getReceiveCustomerPhone());
						//收货人手机
						wayBillDetailForApp.setConsigneeMobile(waybillInfo.getReceiveCustomerMobilephone());
						//目的站
						wayBillDetailForApp.setDestination(waybillInfo.getTargetOrgCode());
						//收货人地址
						wayBillDetailForApp.setConsigneeAddress(waybillInfo.getReceiveCustomerAddress());
						//收货人地址备注
						wayBillDetailForApp.setConsigneeAddressNote(waybillInfo.getReceiveCustomerAddressNote());
						//货物名称
						wayBillDetailForApp.setGoodName(waybillInfo.getGoodsName());
						//件数
						wayBillDetailForApp.setPieces(waybillInfo.getGoodsQtyTotal());
						//重量
						wayBillDetailForApp.setWeight(Float.parseFloat(waybillInfo.getGoodsWeightTotal().toString()));
						//体积
						wayBillDetailForApp.setCubage(Float.parseFloat(waybillInfo.getGoodsVolumeTotal().toString()));
						//总费用
						wayBillDetailForApp.setTotalCharge(waybillInfo.getTotalFee());
						//付款方式
						wayBillDetailForApp.setPayment(waybillInfo.getPaidMethod());
						//预付金额
						wayBillDetailForApp.setPreCharge(waybillInfo.getPrePayAmount());
						//到付金额
						wayBillDetailForApp.setArriveCharge(waybillInfo.getToPayAmount());
						//代收货款类型
						wayBillDetailForApp.setRefundType(waybillInfo.getRefundType());
						//代收货款
						wayBillDetailForApp.setRefund(waybillInfo.getCodAmount());
						//代收货款手续费
						wayBillDetailForApp.setRefundFee(waybillInfo.getCodFee());
						//开单提货方式
						wayBillDetailForApp.setDeliveryType(waybillInfo.getReceiveMethod());
						//接货费
						wayBillDetailForApp.setConsignCharge(waybillInfo.getPickupFee());
						//送货费
						wayBillDetailForApp.setDeliveryCharge(waybillInfo.getDeliveryGoodsFee());
						//包装费
						wayBillDetailForApp.setPickCharge(waybillInfo.getPackageFee());
						//装卸费
						wayBillDetailForApp.setLaborRebate(waybillInfo.getServiceFee());
						//公布价运费
						wayBillDetailForApp.setPublishCharge(waybillInfo.getTransportFee());
						//出发部门名称
						wayBillDetailForApp.setDepartureDeptName(waybillInfo.getDepartureDeptName());
						//出发部门标杆编码
						wayBillDetailForApp.setDepartureDeptNumber(waybillInfo.getDepartureDeptNumber());
						//出发部门地址
						wayBillDetailForApp.setDepartureDeptAddr(waybillInfo.getDepartureDeptAddr());
						//出发部门电话
						wayBillDetailForApp.setDepartureDeptPhone(waybillInfo.getDepartureDeptPhone());
						//出发部门传真
						wayBillDetailForApp.setDepartureDeptFax(waybillInfo.getDepartureDeptFax());
						//到达网点名称
						wayBillDetailForApp.setLadingStationName(waybillInfo.getLadingStationName());
						//到达网点标杆编码
						wayBillDetailForApp.setLadingStationNumber(waybillInfo.getLadingStationNumber());
						//到达网点地址
						wayBillDetailForApp.setLadingStationAddr(waybillInfo.getLadingStationAddr());
						//到达网点电话
						wayBillDetailForApp.setLadingStationPhone(waybillInfo.getLadingStationPhone());
						//到达网点传真
						wayBillDetailForApp.setLadingStationFax(waybillInfo.getLadingStationFax());
						//是否签收
						wayBillDetailForApp.setIsSigned(waybillInfo.isSigned());
						//是否正常签收
						wayBillDetailForApp.setIsNormalSigned(waybillInfo.isNormalSigned());
						//签收录入人
						wayBillDetailForApp.setSignRecorderId(waybillInfo.getDeliverymanName());
						//签收时间
						wayBillDetailForApp.setSignedDate(waybillInfo.getSignTime());
						//第一次签收时间
						wayBillDetailForApp.setFirstSignedDate(waybillInfo.getFirstSignTime());
						//签收备注
						wayBillDetailForApp.setSignedDesc(waybillInfo.getSignNote());
						
						//update by foss-231434-bieyexiong 非异常弃货，直接发送签收信息
						if(!ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(waybillInfo.getSignSituation())){
							//运单状态
							wayBillDetailForApp.setOrderState(waybillInfo.getActive());
						}else if(!waybillExpressService.onlineDetermineIsExpressByProductCode(waybillInfo.getProductCode(),null)){
							//(需求DN201603140026只针对零担) 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
							List<BillPayableEntity> billPayables = billPayableService.queryByWaybillNosAndByBillTypes(
									Arrays.asList(waybillInfo.getWaybillNo()),
									Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM));
							//异常弃货，且有上报理赔，才发送签收信息
							if(CollectionUtils.isNotEmpty(billPayables)){
								//运单状态
								wayBillDetailForApp.setOrderState(waybillInfo.getActive());
							}
						}

						//订单号
						wayBillDetailForApp.setOrderNumber(waybillInfo.getOrderNo());
						//保价声明
						wayBillDetailForApp.setInsuranceValue(waybillInfo.getInsuranceAmount());
						//保价手续费
						wayBillDetailForApp.setInsurance(waybillInfo.getInsuranceFee());
						//货物包装
						wayBillDetailForApp.setPacking(waybillInfo.getGoodsPackage());
						//其它费用
						wayBillDetailForApp.setOtherPayment(waybillInfo.getOtherFee());
						//托运备注
						wayBillDetailForApp.setTranDesc(waybillInfo.getOuterNotes());
						//发货客户编码
						wayBillDetailForApp.setSenderNumber(waybillInfo.getDeliveryCustomerCode());
						//收货客户编码
						wayBillDetailForApp.setConsigneeNumber(waybillInfo.getReceiveCustomerCode());
						//是否已结款
						wayBillDetailForApp.setIsClear(waybillInfo.getSettleStatus());
						//返单类型
						wayBillDetailForApp.setSignBackType(waybillInfo.getReturnBillType());
						//储运事项
						wayBillDetailForApp.setTransNotice(waybillInfo.getTransportationRemark());
						//发货时间
						wayBillDetailForApp.setSendTime(waybillInfo.getBillTime());
						//收货部门名称
						wayBillDetailForApp.setReceiveDeptName(waybillInfo.getReceiveOrgName());
						//收货部门标杆代码
						wayBillDetailForApp.setReceiveDeptNumber(waybillInfo.getReceiveOrgNumber());
						//配载部门标杆编码
						wayBillDetailForApp.setStowageDept(waybillInfo.getLoadOrgNumber());
						//发货人城市编码
						wayBillDetailForApp.setSenderCityCode(waybillInfo.getDeliveryCustomerCityCode1());
						//发货人城市名称
						wayBillDetailForApp.setSenderCityName(waybillInfo.getDeliveryCustomerCityName1());
						//发货人省份编码
						wayBillDetailForApp.setSenderProvinceCode(waybillInfo.getDeliveryCustomerProvCode());
						//发货人省份名称
						wayBillDetailForApp.setSenderProvinceName(waybillInfo.getDeliveryCustomerProvName());
						//收货人城市编码
						wayBillDetailForApp.setConsigneeCityCode(waybillInfo.getReceiveCustomerCityCode());
						//收货人城市名称
						wayBillDetailForApp.setConsigneeCityName(waybillInfo.getReceiveCustomerCityName());
						//收货人省份编码
						wayBillDetailForApp.setConsigneeProvinceCode(waybillInfo.getReceiveCustomerProvCode());
						//收货人省份名称
						wayBillDetailForApp.setConsigneeProvinceName(waybillInfo.getReceiveCustomerProvName());
						//是否上门接货
						wayBillDetailForApp.setIsDoorToDoorPick(waybillInfo.isPickup());
						//短信通知状态
						wayBillDetailForApp.setSmsNoticeResult(waybillInfo.getNotificationResult());
						//签收单返回方式
						wayBillDetailForApp.setSignBillBackWay(waybillInfo.getReturnBillType());
						
						
						//小件 新加的逻辑----------------------
						//运单所属快递大区编码
						wayBillDetailForApp.setExDepartureRegionNubmer(waybillInfo.getDistrictCode());
						//运单所属快递大区名称
						wayBillDetailForApp.setExDepartureRegionName(waybillInfo.getDistrictName());
						//运单所属快递大区标杆编码
						wayBillDetailForApp.setExDepartureRegionStandardNubmer(waybillInfo.getDistrictUnifiedCode());
						
						//运单所属快递大区编码
						wayBillDetailForApp.setExDestinationRegionNubmer(waybillInfo.getEndDistrictCode());
						//运单所属快递大区名称
						wayBillDetailForApp.setExDestinationRegionName(waybillInfo.getEndDistrictName());
						//运单所属快递大区标杆编码
						wayBillDetailForApp.setExDestinationRegionStandardNubmer(waybillInfo.getEndDistrictUnifiedCode());
						
						
						
						//快递员CODE-出发
						wayBillDetailForApp.setExDepartureCourierNumber(waybillInfo.getStartExpressEmpCode());
						//快递员名称-出发
						wayBillDetailForApp.setExDepartureCourierName(waybillInfo.getStartExpressEmpName());
						//快递点部CODE-出发
						wayBillDetailForApp.setExDepartureDeptNumber(waybillInfo.getStartExpressOrgCode());
						//快递点部标杆编码-出发
						wayBillDetailForApp.setExDepartureDeptStandardNumber(waybillInfo.getStartExpressUnfiedCode());
						//快递点部名称-出发
						wayBillDetailForApp.setExDepartureDeptName(waybillInfo.getStartExpressOrgName());
						
						//快递员CODE-到达
						wayBillDetailForApp.setExDestinationCourierNumber(waybillInfo.getEndExpressEmpCode());
						
						//快递员名称-到达
						wayBillDetailForApp.setExDestinationCourierName(waybillInfo.getEndExpressEmpName());
						//快递点部CODE-到达
						wayBillDetailForApp.setExDestinationDeptNumber(waybillInfo.getEndExpressOrgCode());
						
						//快递点部名称-到达
						wayBillDetailForApp.setExDestinationDeptName(waybillInfo.getEndExpressOrgName());
						
						//快递点部标杆编码-到达
						wayBillDetailForApp.setExDestinationDeptStandardNumber(waybillInfo.getEndExpressUnfiedCode());
						
						//发货城市区县编码
						wayBillDetailForApp.setDepartureDistrictCode(waybillInfo.getDeliveryCustomerDistCode());
						
						//发货城市区县名称
						if(waybillInfo.getDeliveryCustomerDistCode() != null){
				    		AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(waybillInfo.getDeliveryCustomerDistCode());
				    		if(city != null){
								wayBillDetailForApp.setDepartureDistrictName(StringUtil.defaultIfNull(city.getName()));
				    		}
				    	}else{
				    		wayBillDetailForApp.setDepartureDistrictName(null);
				    	}
						
						//收货城市区县编码
						wayBillDetailForApp.setConsigneeDistrictCode(waybillInfo.getReceiveCustomerDistCode());
						
						//收货城市区县名称
						if(waybillInfo.getReceiveCustomerDistCode() != null){
				    		AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(waybillInfo.getReceiveCustomerDistCode());
				    		if(city != null){
								wayBillDetailForApp.setConsigneeDistrictName(StringUtil.defaultIfNull(city.getName()));
				    		}
				    	}else{
				    		wayBillDetailForApp.setConsigneeDistrictName(null);
				    	}
						wayBillDetailForAppList.add(wayBillDetailForApp);
						LOGGER.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(wayBillDetailForApp));
					}	
				}
				  //消息代码成功为“00000”  获取运单信息成功
				  waybillDetailInfo.setMessage_code("00000");
				  //消息代码说明
				  waybillDetailInfo.setMessage_detail("Gets the waybill information is success");
				  //返回记录的条数
				  waybillDetailInfo.setCount(waybillInfoDtoList.size());
				  //处理明细
				  waybillDetailInfo.setDetail(wayBillDetailForAppList);
				  response.setHeader("ESB-ResultCode","1");
				  return waybillDetailInfo;
				}else{
					//消息代码失败为“10000”  获取运单信息失败，运单信息为空
					waybillDetailInfo.setMessage_code("10000");
					//消息代码说明
					waybillDetailInfo.setMessage_detail("Gets the waybill information is failure,information is null");
					//返回记录的条数
					waybillDetailInfo.setCount(0);
					//处理明细
					waybillDetailInfo.setDetail(null);
					response.setHeader("ESB-ResultCode","1");
					return waybillDetailInfo;
				}	
			}else{
				//消息代码失败为“10000” 运单承运信息为空
				waybillDetailInfo.setMessage_code("20000");
				//消息代码说明
				waybillDetailInfo.setMessage_detail("Waybill ActualFreight information is null");
				//返回记录的条数
				waybillDetailInfo.setCount(0);
				//处理明细
				waybillDetailInfo.setDetail(null);
				response.setHeader("ESB-ResultCode","1");
				return waybillDetailInfo;
			}
		}else{
           //消息代码失败为“20000”   运单未开单
           waybillDetailInfo.setMessage_code("30000");
           //消息代码说明
           waybillDetailInfo.setMessage_detail("Waybill without billing");
          //返回记录的条数
          waybillDetailInfo.setCount(0);
          //处理明细
          waybillDetailInfo.setDetail(null);
          response.setHeader("ESB-ResultCode","1");
          return waybillDetailInfo;
		}
	}else{
		//消息代码失败为“30000”   运单号为空或无法从APP获取运单号
		waybillDetailInfo.setMessage_code("40000");
		//消息代码说明
		waybillDetailInfo.setMessage_detail("waybill number is null or FOSS can't get the waybill number from APP");
		//返回记录的条数
		waybillDetailInfo.setCount(0);
		//处理明细
		waybillDetailInfo.setDetail(null);	
		response.setHeader("ESB-ResultCode","1");
		return waybillDetailInfo;
	}
  }
	
	/**
	 * @author foss-qms-xulixin
	 * @date 2015-06-08 下午8:03
	 * @param waybillNo
	 * 根据运单号查询运单信息
	 */
	public WaybillDetaillListEntity pkpWaybilldetail(String waybillNo){
	    //运单信息集合
		List<WayBillDetailForApp> wayBillDetailForAppList = new ArrayList<WayBillDetailForApp> ();
		//运单费用信息集合
		List<WaybillCostInfoForApp> waybillCostInfosForApp = new ArrayList<WaybillCostInfoForApp> ();
	   //ESB运单信息
		WayBillDetailForApp wayBillDetailForApp = null;
		//运单费用信息
		WaybillCostInfoForApp waybillCostInfoForApp = null;
		//运单详情
		WaybillDetaillListEntity waybillDetailInfo=new WaybillDetaillListEntity();
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
				List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfoForQms(waybillList);
				if(waybillInfoDtoList!=null && waybillInfoDtoList.size()!=0){
			   //遍历运单详情
				for(WaybillInfoDto waybillInfo : waybillInfoDtoList){
					if(waybillInfo!=null){
						wayBillDetailForApp =  new WayBillDetailForApp();
						if(waybillInfo.getWaybillChargeCostDto()!=null && waybillInfo.getWaybillChargeCostDto().size()!=0 )
						{
							for (WaybillChargeCostDto waybillChargeCostDto : waybillInfo.getWaybillChargeCostDto())
							{
								//复制费用信息
								waybillCostInfoForApp = new WaybillCostInfoForApp();
								waybillCostInfoForApp.setCostType(waybillChargeCostDto.getCostType());
								waybillCostInfoForApp.setCostName(waybillChargeCostDto.getCostName());
								waybillCostInfoForApp.setCostMoney(waybillChargeCostDto.getCostMoney());
								waybillCostInfosForApp.add(waybillCostInfoForApp);
							}
						}
						wayBillDetailForApp.setWaybillCostInfos(waybillCostInfosForApp);
						//wayBillDetailForApp.getWaybillCostInfos().addAll(waybillCostInfosForApp);
						//设置运单号
						wayBillDetailForApp.setNumber(waybillInfo.getWaybillNo());
						/**
						 * KDTE-5323 快递单号待补录状态时FOSS与CRM系统信息不一致
						 */
						//设置运输类型(产品)
						wayBillDetailForApp.setTranType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillInfo.getProductCode()));
						//设置运输性质
						wayBillDetailForApp.setTranProperty(waybillInfo.getProductCode());
						//发货联系人
						wayBillDetailForApp.setSender(waybillInfo.getDeliveryCustomerName());
						//发货人电话
						wayBillDetailForApp.setSenderPhone(waybillInfo.getDeliveryCustomerPhone());
						//发货客户手机
						wayBillDetailForApp.setSenderMobile(waybillInfo.getDeliveryCustomerMobilephone());
						//始发站
						wayBillDetailForApp.setDeparture(waybillInfo.getDeliveryCustomerCityName());
						//发货人地址
						wayBillDetailForApp.setSenderAddress(waybillInfo.getDeliveryCustomerAddress());
						//发货人地址备注
						wayBillDetailForApp.setSenderAddressNote(waybillInfo.getDeliveryCustomerAddressNote());
						//收货人
						wayBillDetailForApp.setConsignee(waybillInfo.getReceiveCustomerName());
						//收货人电话
						wayBillDetailForApp.setConsigneePhone(waybillInfo.getReceiveCustomerPhone());
						//收货人手机
						wayBillDetailForApp.setConsigneeMobile(waybillInfo.getReceiveCustomerMobilephone());
						//目的站
						wayBillDetailForApp.setDestination(waybillInfo.getTargetOrgCode());
						//收货人地址
						wayBillDetailForApp.setConsigneeAddress(waybillInfo.getReceiveCustomerAddress());
						//收货人地址备注
						wayBillDetailForApp.setConsigneeAddressNote(waybillInfo.getReceiveCustomerAddressNote());
						//货物名称
						wayBillDetailForApp.setGoodName(waybillInfo.getGoodsName());
						//件数
						wayBillDetailForApp.setPieces(waybillInfo.getGoodsQtyTotal());
						//重量
						wayBillDetailForApp.setWeight(Float.parseFloat(waybillInfo.getGoodsWeightTotal().toString()));
						//体积
						wayBillDetailForApp.setCubage(Float.parseFloat(waybillInfo.getGoodsVolumeTotal().toString()));
						//总费用
						wayBillDetailForApp.setTotalCharge(waybillInfo.getTotalFee());
						//付款方式
						wayBillDetailForApp.setPayment(waybillInfo.getPaidMethod());
						//预付金额
						wayBillDetailForApp.setPreCharge(waybillInfo.getPrePayAmount());
						//到付金额
						wayBillDetailForApp.setArriveCharge(waybillInfo.getToPayAmount());
						//代收货款类型
						wayBillDetailForApp.setRefundType(waybillInfo.getRefundType());
						//代收货款
						wayBillDetailForApp.setRefund(waybillInfo.getCodAmount());
						//代收货款手续费
						wayBillDetailForApp.setRefundFee(waybillInfo.getCodFee());
						//开单提货方式
						wayBillDetailForApp.setDeliveryType(waybillInfo.getReceiveMethod());
						//接货费
						wayBillDetailForApp.setConsignCharge(waybillInfo.getPickupFee());
						//送货费
						wayBillDetailForApp.setDeliveryCharge(waybillInfo.getDeliveryGoodsFee());
						//包装费
						wayBillDetailForApp.setPickCharge(waybillInfo.getPackageFee());
						//装卸费
						wayBillDetailForApp.setLaborRebate(waybillInfo.getServiceFee());
						//公布价运费
						wayBillDetailForApp.setPublishCharge(waybillInfo.getTransportFee());
						//出发部门名称
						wayBillDetailForApp.setDepartureDeptName(waybillInfo.getDepartureDeptName());
						//出发部门标杆编码
						wayBillDetailForApp.setDepartureDeptNumber(waybillInfo.getDepartureDeptNumber());
						//出发部门地址
						wayBillDetailForApp.setDepartureDeptAddr(waybillInfo.getDepartureDeptAddr());
						//出发部门电话
						wayBillDetailForApp.setDepartureDeptPhone(waybillInfo.getDepartureDeptPhone());
						//出发部门传真
						wayBillDetailForApp.setDepartureDeptFax(waybillInfo.getDepartureDeptFax());
						//到达网点名称
						wayBillDetailForApp.setLadingStationName(waybillInfo.getLadingStationName());
						//到达网点标杆编码
						wayBillDetailForApp.setLadingStationNumber(waybillInfo.getLadingStationNumber());
						//到达网点地址
						wayBillDetailForApp.setLadingStationAddr(waybillInfo.getLadingStationAddr());
						//到达网点电话
						wayBillDetailForApp.setLadingStationPhone(waybillInfo.getLadingStationPhone());
						//到达网点传真
						wayBillDetailForApp.setLadingStationFax(waybillInfo.getLadingStationFax());
						//是否签收
						wayBillDetailForApp.setIsSigned(waybillInfo.isSigned());
						//是否正常签收
						wayBillDetailForApp.setIsNormalSigned(waybillInfo.isNormalSigned());
						//签收录入人
						wayBillDetailForApp.setSignRecorderId(waybillInfo.getDeliverymanName());
						//签收时间
						wayBillDetailForApp.setSignedDate(waybillInfo.getSignTime());
						//第一次签收时间
						wayBillDetailForApp.setFirstSignedDate(waybillInfo.getFirstSignTime());
						//签收备注
						wayBillDetailForApp.setSignedDesc(waybillInfo.getSignNote());
						//订单号
						wayBillDetailForApp.setOrderNumber(waybillInfo.getOrderNo());
						//保价声明
						wayBillDetailForApp.setInsuranceValue(waybillInfo.getInsuranceAmount());
						//保价手续费
						wayBillDetailForApp.setInsurance(waybillInfo.getInsuranceFee());
						//货物包装
						wayBillDetailForApp.setPacking(waybillInfo.getGoodsPackage());
						//运单状态
						wayBillDetailForApp.setOrderState(waybillInfo.getActive());
						//其它费用
						wayBillDetailForApp.setOtherPayment(waybillInfo.getOtherFee());
						//托运备注
						wayBillDetailForApp.setTranDesc(waybillInfo.getOuterNotes());
						//发货客户编码
						wayBillDetailForApp.setSenderNumber(waybillInfo.getDeliveryCustomerCode());
						//收货客户编码
						wayBillDetailForApp.setConsigneeNumber(waybillInfo.getReceiveCustomerCode());
						//是否已结款
						wayBillDetailForApp.setIsClear(waybillInfo.getSettleStatus());
						//返单类型
						wayBillDetailForApp.setSignBackType(waybillInfo.getReturnBillType());
						//储运事项
						wayBillDetailForApp.setTransNotice(waybillInfo.getTransportationRemark());
						//发货时间
						wayBillDetailForApp.setSendTime(waybillInfo.getBillTime());
						//收货部门名称
						wayBillDetailForApp.setReceiveDeptName(waybillInfo.getReceiveOrgName());
						//收货部门标杆代码
						wayBillDetailForApp.setReceiveDeptNumber(waybillInfo.getReceiveOrgNumber());
						//配载部门标杆编码
						wayBillDetailForApp.setStowageDept(waybillInfo.getLoadOrgNumber());
						//发货人城市编码
						wayBillDetailForApp.setSenderCityCode(waybillInfo.getDeliveryCustomerCityCode1());
						//发货人城市名称
						wayBillDetailForApp.setSenderCityName(waybillInfo.getDeliveryCustomerCityName1());
						//发货人省份编码
						wayBillDetailForApp.setSenderProvinceCode(waybillInfo.getDeliveryCustomerProvCode());
						//发货人省份名称
						wayBillDetailForApp.setSenderProvinceName(waybillInfo.getDeliveryCustomerProvName());
						//收货人城市编码
						wayBillDetailForApp.setConsigneeCityCode(waybillInfo.getReceiveCustomerCityCode());
						//收货人城市名称
						wayBillDetailForApp.setConsigneeCityName(waybillInfo.getReceiveCustomerCityName());
						//收货人省份编码
						wayBillDetailForApp.setConsigneeProvinceCode(waybillInfo.getReceiveCustomerProvCode());
						//收货人省份名称
						wayBillDetailForApp.setConsigneeProvinceName(waybillInfo.getReceiveCustomerProvName());
						//是否上门接货
						wayBillDetailForApp.setIsDoorToDoorPick(waybillInfo.isPickup());
						//短信通知状态
						wayBillDetailForApp.setSmsNoticeResult(waybillInfo.getNotificationResult());
						//签收单返回方式
						wayBillDetailForApp.setSignBillBackWay(waybillInfo.getReturnBillType());
						
						
						//小件 新加的逻辑----------------------
						//运单所属快递大区编码
						wayBillDetailForApp.setExDepartureRegionNubmer(waybillInfo.getDistrictCode());
						//运单所属快递大区名称
						wayBillDetailForApp.setExDepartureRegionName(waybillInfo.getDistrictName());
						//运单所属快递大区标杆编码
						wayBillDetailForApp.setExDepartureRegionStandardNubmer(waybillInfo.getDistrictUnifiedCode());
						
						//运单所属快递大区编码
						wayBillDetailForApp.setExDestinationRegionNubmer(waybillInfo.getEndDistrictCode());
						//运单所属快递大区名称
						wayBillDetailForApp.setExDestinationRegionName(waybillInfo.getEndDistrictName());
						//运单所属快递大区标杆编码
						wayBillDetailForApp.setExDestinationRegionStandardNubmer(waybillInfo.getEndDistrictUnifiedCode());
						
						
						//快递员CODE-出发
						wayBillDetailForApp.setExDepartureCourierNumber(waybillInfo.getStartExpressEmpCode());
						//快递员名称-出发
						wayBillDetailForApp.setExDepartureCourierName(waybillInfo.getStartExpressEmpName());
						//快递点部CODE-出发
						wayBillDetailForApp.setExDepartureDeptNumber(waybillInfo.getStartExpressOrgCode());
						//快递点部标杆编码-出发
						wayBillDetailForApp.setExDepartureDeptStandardNumber(waybillInfo.getStartExpressUnfiedCode());
						//快递点部名称-出发
						wayBillDetailForApp.setExDepartureDeptName(waybillInfo.getStartExpressOrgName());
						
						//快递员CODE-到达
						wayBillDetailForApp.setExDestinationCourierNumber(waybillInfo.getEndExpressEmpCode());
						
						//快递员名称-到达
						wayBillDetailForApp.setExDestinationCourierName(waybillInfo.getEndExpressEmpName());
						//快递点部CODE-到达
						wayBillDetailForApp.setExDestinationDeptNumber(waybillInfo.getEndExpressOrgCode());
						
						//快递点部名称-到达
						wayBillDetailForApp.setExDestinationDeptName(waybillInfo.getEndExpressOrgName());
						
						//快递点部标杆编码-到达
						wayBillDetailForApp.setExDestinationDeptStandardNumber(waybillInfo.getEndExpressUnfiedCode());
						
						//发货城市区县编码
						wayBillDetailForApp.setDepartureDistrictCode(waybillInfo.getDeliveryCustomerDistCode());
						
						//发货城市区县名称
						if(waybillInfo.getDeliveryCustomerDistCode() != null){
				    		AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(waybillInfo.getDeliveryCustomerDistCode());
				    		if(city != null){
								wayBillDetailForApp.setDepartureDistrictName(StringUtil.defaultIfNull(city.getName()));
				    		}
				    	}else{
				    		wayBillDetailForApp.setDepartureDistrictName(null);
				    	}
						
						//收货城市区县编码
						wayBillDetailForApp.setConsigneeDistrictCode(waybillInfo.getReceiveCustomerDistCode());
						
						//收货城市区县名称
						if(waybillInfo.getReceiveCustomerDistCode() != null){
				    		AdministrativeRegionsEntity city = administrativeRegionsService.queryAdministrativeRegionsByCode(waybillInfo.getReceiveCustomerDistCode());
				    		if(city != null){
								wayBillDetailForApp.setConsigneeDistrictName(StringUtil.defaultIfNull(city.getName()));
				    		}
				    	}else{
				    		wayBillDetailForApp.setConsigneeDistrictName(null);
				    	}
						
						//是否集中接货
						wayBillDetailForApp.setPickupCentralized(waybillInfo.getPickupCentralized());
						//开单人工号
						wayBillDetailForApp.setCreateUserCode(waybillInfo.getCreateUserCode());
						//开单人姓名
						wayBillDetailForApp.setCreateUserName(waybillInfo.getCreateUserName());
						//开单组织编码
					    wayBillDetailForApp.setCreateOrgCode(waybillInfo.getCreateOrgCode());
					    //开单组织名称
					    wayBillDetailForApp.setCreateOrgName(waybillInfo.getCreateOrgName());
					    if("000000".equals(waybillInfo.getDriverCode())){
					    //司机工号
					    	wayBillDetailForApp.setDriverCode(waybillInfo.getCreateUserCode());
					    	//司机姓名
					    	wayBillDetailForApp.setDriverName(waybillInfo.getCreateUserName());
					    	//司机所在部门编号
						    wayBillDetailForApp.setDriverOrgCode(waybillInfo.getCreateOrgCode());
						    //司机所在部门名称
						    wayBillDetailForApp.setDriverOrgName(waybillInfo.getCreateOrgName());
					    }else{
					    	//司机工号
					    wayBillDetailForApp.setDriverCode(waybillInfo.getDriverCode());
					    //司机姓名
					    wayBillDetailForApp.setDriverName(waybillInfo.getDriverName());
					    //司机所在部门编号
					    wayBillDetailForApp.setDriverOrgCode(waybillInfo.getDriverOrgCode());
					    //司机所在部门名称
					    wayBillDetailForApp.setDriverOrgName(waybillInfo.getDriverOrgName());
					    }
					    //车牌号
					    wayBillDetailForApp.setLicensePlateNum(waybillInfo.getLicensePlateNum());
					    //补录时间
					    wayBillDetailForApp.setModifyTime(waybillInfo.getModifyTime());
					    //补录人工号
					    wayBillDetailForApp.setModifyUserCode(waybillInfo.getModifyUserCode());
					    //补录人姓名
					    wayBillDetailForApp.setModifyUserName(waybillInfo.getModifyUserName());
					    //补录部门编码
					    wayBillDetailForApp.setModifyOrgCode(waybillInfo.getModifyOrgCode());
					    //补录部门名称
					    wayBillDetailForApp.setModifyOrgName(waybillInfo.getModifyOrgName());
					    //货物尺寸
					    wayBillDetailForApp.setGoodsSize(waybillInfo.getGoodsSize());
					    //责任部门名称
					    wayBillDetailForApp.setPrincipalOrgName(waybillInfo.getPrincipalOrgName());
					    //责任部门编码
					    wayBillDetailForApp.setPrincipalOrgCode(waybillInfo.getPrincipalOrgCode());
					    //开单部门负责人工号
					    wayBillDetailForApp.setCreateOrgPrincipalCode(waybillInfo.getCreateOrgPrincipalCode());
					    //开单部门负责人姓名
					    wayBillDetailForApp.setCreateOrgPrincipalName(waybillInfo.getCreateOrgPrincipalName());
					    //是否为快递
					    wayBillDetailForApp.setIsExpress(waybillInfo.getIsExpress());
					    //快递补录创建时间
					    wayBillDetailForApp.setExpCreateTime(waybillInfo.getExpCreateTime());
					    //快递修改时间
					    wayBillDetailForApp.setExpModifyTime(waybillInfo.getExpModifyTime());
					    //快递开单时间
					    wayBillDetailForApp.setExpBillTime(waybillInfo.getExpBillTime());
					    //快递到达部门编号
					    wayBillDetailForApp.setExpArriveOrgCode(waybillInfo.getExpArriveOrgCode());
					    //快递到达部门名称
					    wayBillDetailForApp.setExpArriveOrgName(waybillInfo.getExpArriveOrgName());
					    //补录类型
					    wayBillDetailForApp.setPendingType(waybillInfo.getPendingType());
					    //DN201512110012 273279/liding add QMS差错上报新增字段
					    //是否快递集中补录
					    wayBillDetailForApp.setIsExpressFocus(waybillInfo.getIsExpressFocus());
					    //开单快递员
					    wayBillDetailForApp.setBillingCourier(waybillInfo.getBillingCourier());
					    
						wayBillDetailForAppList.add(wayBillDetailForApp);
						LOGGER.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(wayBillDetailForApp));
					}	
				}
				  //消息代码成功为“00000”  获取运单信息成功
				  waybillDetailInfo.setMessage_code("00000");
				  //消息代码说明
				  waybillDetailInfo.setMessage_detail("Gets the waybill information is success");
				  //返回记录的条数
				  waybillDetailInfo.setCount(waybillInfoDtoList.size());
				  //处理明细
				  waybillDetailInfo.setDetail(wayBillDetailForAppList);
				  return waybillDetailInfo;
				}else{
					//消息代码失败为“10000”  获取运单信息失败，运单信息为空
					waybillDetailInfo.setMessage_code("10000");
					//消息代码说明
					waybillDetailInfo.setMessage_detail("Gets the waybill information is failure,information is null");
					//返回记录的条数
					waybillDetailInfo.setCount(0);
					//处理明细
					waybillDetailInfo.setDetail(null);
					return waybillDetailInfo;
				}	
			}else{
				//消息代码失败为“10000” 运单承运信息为空
				waybillDetailInfo.setMessage_code("20000");
				//消息代码说明
				waybillDetailInfo.setMessage_detail("Waybill ActualFreight information is null");
				//返回记录的条数
				waybillDetailInfo.setCount(0);
				//处理明细
				waybillDetailInfo.setDetail(null);
				return waybillDetailInfo;
			}
		}else{
           //消息代码失败为“20000”   运单未开单
           waybillDetailInfo.setMessage_code("30000");
           //消息代码说明
           waybillDetailInfo.setMessage_detail("Waybill without billing");
          //返回记录的条数
          waybillDetailInfo.setCount(0);
          //处理明细
          waybillDetailInfo.setDetail(null);
          return waybillDetailInfo;
		}
	}else{
		//消息代码失败为“30000”   运单号为空或无法从APP获取运单号
		waybillDetailInfo.setMessage_code("40000");
		//消息代码说明
		waybillDetailInfo.setMessage_detail("waybill number is null or FOSS can't get the waybill number from APP");
		//返回记录的条数
		waybillDetailInfo.setCount(0);
		//处理明细
		waybillDetailInfo.setDetail(null);	
		return waybillDetailInfo;
	}
  }
	/**
	 * @author foss-qms-xulixin
	 * @date 2015-06-08 下午9:53
	 * 为品质管理系统提供运单信息
	 * */
	@GET
	@Path("/findInfoByBillNo")
	public String findInfoByBillNo(
			@QueryParam("waybillNo") String waybillNo,
			@QueryParam("businessType") String businessType,
			@QueryParam("errorType") String errorType,
			@QueryParam("repairDeptCode") String repairDeptCode,
			@Context HttpServletResponse response){
		response.setHeader("ESB-ResultCode","1");
		String version = "20150822";
		 //运单号
		if(!StringUtils.isNotEmpty(waybillNo)){
			
			return "{\"success\":\"0\",\"version\":\""+version+"\",\"message\":\"运单号不能为空!\",\"result\":\"\"}";
		}
		
		 //差错类型
		  if(!StringUtils.isNotEmpty(errorType)){
				
				return "{\"success\":\"0\",\"version\":\""+version+"\",\"message\":\"差错类型不能为空!\",\"result\":\"\"}";
		  }
		  
		 //业务类型
		  if(!StringUtils.isNotEmpty(businessType)){
				
				return "{\"success\":\"0\",\"version\":\""+version+"\",\"message\":\"业务类型不能为空!\",\"result\":\"\"}";
		  }
		  
		  /*校验运单类型  start*/
		  String waybillType = "K";
		  String isExpress = this.waybillQueryService.getIsExpressByWayBillNo(waybillNo);
		  if(FossConstants.NO.equals(isExpress)){
				waybillType = "L";
		  }
		  String woodenPackageOrgCode = this.waybillQueryService.getWoodenPackageOrgCode(waybillNo);
		  
		  if(!businessType.equals(waybillType)){
				if("K".equals(businessType)){
					return "{\"success\":\"3\",\"version\":\""+version+"\",\"message\":\"运单号 "+waybillNo+" 不是快递单号!\",\"result\":{\"waybillNo\":\""+waybillNo+"\",\"isExpress\":\"N\"}}";
				}
				if("L".equals(businessType)){
					return "{\"success\":\"3\",\"version\":\""+version+"\",\"message\":\"运单号 "+waybillNo+" 不是零担单号!\",\"result\":{\"waybillNo\":\""+waybillNo+"\",\"isExpress\":\"Y\"}}";
				}
		  }
		  /*校验运单类型  end*/
		  
		if (StringUtils.isNotEmpty(repairDeptCode) && !StringUtils.equals("null", repairDeptCode)) {
			// 查询运单对应货物库存所在部门
			List<WaybillStockEntity> waybillStockEntities = waybillStockDao.queryWaybillStockByWaybillNo(waybillNo);
			if (CollectionUtils.isNotEmpty(waybillStockEntities) && waybillStockEntities.size() > 0) {
				// 通过组织标杆编码获取部门编码
				String orgCode = customerDao.queryOrgCodeByUnifiedCode(repairDeptCode);
				if (!StringUtils.equals(orgCode, waybillStockEntities.get(0).getOrgCode())) {
					// 查询上报部门所对应的顶级外场
					List<OrgAdministrativeInfoEntity> entities = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
					if (CollectionUtils.isNotEmpty(entities) && entities.size() > 0) {
						for (int i = 0; i < entities.size(); i++) {
							// 判断是否为外场
							if (StringUtils.equals("Y", entities.get(i).getTransferCenter())) {
								// 判断部门编码与货物所在部门编码是否相同
								if (!StringUtils.equals(entities.get(i).getCode(), waybillStockEntities.get(0).getOrgCode())) {
									if (i + 1 == entities.size()) {
										// 循环到最后一次如果不同的话，则不可上报
										return "{\"success\":\"0\",\"version\":\"" + version + "\",\"message\":\"运单号 " + waybillNo + ",该运单号货物不在库存中，无法上报异常修复!\",\"result\":\"\"}";
									} else {
										continue;
									}
								} else {
									break;
								}
							} else if (i + 1 == entities.size()) {
								// 循环到最后一次部门编码也非外场
								return "{\"success\":\"0\",\"version\":\"" + version + "\",\"message\":\"运单号 " + waybillNo + ",该运单号货物不在库存中，无法上报异常修复!\",\"result\":\"\"}";
							}
						}
					} else {
						return "{\"success\":\"0\",\"version\":\"" + version + "\",\"message\":\"运单号 " + waybillNo + ",未获取到上报部门所对应的外场，无法上报异常修复!\",\"result\":\"\"}";
					}
				}
			} else {
				return "{\"success\":\"0\",\"version\":\"" + version + "\",\"message\":\"运单号 " + waybillNo + ",根据运单号无法获取货物库存，无法上报异常修复!\",\"result\":\"\"}";
			}
		}

		//中转数据  
		String tfr =  this.tfrForQmsService.getInfoFromTfrForQms(errorType, waybillNo, businessType);
		
		if(!"".equals(tfr) && !"null".equals(tfr)){
			tfr =tfr.substring(1, tfr.length()-1);
		}else{
			tfr = "";
		}
		
		//接送货数据
		String pkp = "";
		WaybillDetaillListEntity WaybillDetaillListEntity = this.pkpWaybilldetail(waybillNo);
		if(null != WaybillDetaillListEntity.getDetail()){
			pkp = JSONObject.toJSON(WaybillDetaillListEntity.getDetail().get(0)).toString();
		}
		if(!"".equals(pkp) && !"null".equals(pkp)){
			pkp =pkp.substring(1, pkp.length()-1);
		}else{
			return "{\"success\":\"2\",\"version\":\""+version+"\",\"message\":\"运单号 "+waybillNo+" 没有符合条件的数据!\",\"result\":{\"waybillNo\":\""+waybillNo+"\"}}";
		}
		
		
		//结算数据
		String stl = this.qmsErrorService.querySignInfo(waybillNo, businessType, errorType);
		if(!"".equals(stl) && !"null".equals(stl)){
			stl =stl.substring(1, stl.length()-1);
		}else{
			stl = "";
		}
		  
		  String resultStr = "";
		  
		  if(!"".equals(tfr) ){
			  resultStr=resultStr+tfr+",";
		  }
		  
		  if(!"".equals(pkp)){
			  resultStr=resultStr+pkp+",";
		  }
		  
		  if(!"".equals(stl) ){
			  resultStr=resultStr+stl+",";
		  }
		  if(!"".equals(woodenPackageOrgCode) ){
			  resultStr=resultStr+
		              "\"woodenPackageOrgCode\":\""+woodenPackageOrgCode+"\",";
		  }
		  
		  if(resultStr.endsWith(",")){
			  resultStr=resultStr.substring(0, resultStr.length()-1);
		  }
		  
		  String pathDetail = this.waybillQueryService.queryPathDetailByNos(waybillNo);
		  String result = "{" +
		          "\"success\":\"1\"," +
		          "\"version\":\""+version+"\"," +
		          "\"message\":\"\"," +
		          "\"result\":{" +
		              "\"waybillNo\":\""+waybillNo+"\","+
		              "\"pathDetail\":"+pathDetail+","+
			          resultStr+
		            "}" +
		     "}";
		
		 /*String result2 = "{" +
		          "\"success\":\"1\"," +
		          "\"success2\":\"2\"," +
		          "\"message\":\"\"," +
		          "\"result\":{" +
		              "\"waybillNo\":\""+waybillNo+"\","+
		              "\"pkp\":"+pkp+","+
		              "\"tfr\":"+tfr+","+
		              "\"stl\":"+stl+""+
		            "}" +
		     "}";*/
		  return result;
	}
}

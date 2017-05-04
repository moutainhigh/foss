package com.deppon.foss.prt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillAddPropertyDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillChargeDtlPrintDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCQueryReceivableAmountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.MoneyUtils;
import com.deppon.foss.util.define.FossConstants;

public class ArriveSheetPrintService {
	
	private static String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		ArriveSheetPrintService.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
//	private static final String grayByWaybillNoUrl = "http://cubc.deppon.com/ashy-web/webservice/v1/ashy/vestService/vestAscription";
	private static final String SERVICE_CODE = "com.deppon.foss.prt.ArriveSheetPrintService";	
	private static ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService;
	public void setCubcQueryReceivableAmountService(
			ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService) {
		ArriveSheetPrintService.cubcQueryReceivableAmountService = cubcQueryReceivableAmountService;
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(ArriveSheetPrintService.class);
	
	/**
	 * 产品类型:汽运偏线
	 */
	public static final String PLF_NAME = "汽运偏线";
	/**
	 * 
	 * 到达联打印基本信息
	 * 
	 * @param waybillEntity
	 * @param parameter
	 * @param _ApplicationContext
	 * @param arriveEntity
	 * @param currentUserDepCode
	 * @param currentUserEmpName
	 * 
	 * @author ibm-wangfei
	 * @date Jun 17, 2013 4:29:59 PM
	 */
	public static void setArriveSheetBaseInfo(ArriveSheetWaybillAddPropertyDto waybillEntity, Map<String, Object> parameter, ApplicationContext _ApplicationContext, ArriveSheetEntity arriveEntity, String currentUserDepCode,
			String currentUserEmpName, String suffix) {
		//数据来源
		String orderChannel =waybillEntity.getOrderChannel();
		//设置打印次数
		if(arriveEntity!=null && arriveEntity.getPrinttimes()!=null){					
			waybillEntity.setPrintNum(arriveEntity.getPrinttimes().intValue());
		}else{
			waybillEntity.setPrintNum(1);
		}
		IWaybillSignResultService waybillSignResultService = (IWaybillSignResultService) _ApplicationContext.getBean("waybillSignResultService");
		/****************************************************************** 头部信息 ******************************************************************/
		//打印条码
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_barcode + suffix, arriveEntity.getArrivesheetNo());
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_arriveSheetNo + suffix, arriveEntity.getArrivesheetNo());
		/****************************************************************** 客户信息 ******************************************************************/
		//MANA-1976 在FOSS开单等界面添加大客户标记 判断发货客户是否大客户，如果是  在内容前面加"VIP"
		if(orderChannel !=null && !"".equals(orderChannel) && ArriveSheetConstants.ORDERCHANNEL.equals(orderChannel)){
			if(StringUtils.isNotBlank(waybillEntity.getDeliveryBigCustomer())&& FossConstants.YES.equals(waybillEntity.getDeliveryBigCustomer())){
				//发货客户名称(托运人公司)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliveryCustomerName + suffix, ArriveSheetConstants.DELIVERYCUSTOMERNAMEALBB);
			}else {
				//发货客户名称(托运人公司)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliveryCustomerName + suffix, ArriveSheetConstants.DELIVERYCUSTOMERNAMEALBB);
			}
		}else{
			if(StringUtils.isNotBlank(waybillEntity.getDeliveryBigCustomer())&& FossConstants.YES.equals(waybillEntity.getDeliveryBigCustomer())){
				//发货客户名称(托运人公司)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliveryCustomerName + suffix, defaultIfNull(ArriveSheetConstants.VIP+waybillEntity.getDeliveryCustomerContact()));
			}else {
				//发货客户名称(托运人公司)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliveryCustomerName + suffix, defaultIfNull(waybillEntity.getDeliveryCustomerContact()));
			}
			//发货客户编码(联系人编码)
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliveryCustomerCode + suffix, defaultIfNull(waybillEntity.getDeliveryCustomerCode()));
		}
		//打印整车运单时,如果是出发部门打印到达联且到达类型是“到达营业部”那么不打印收货人信息。
		if (StringUtil.equals(waybillEntity.getReceiveOrgCode(), currentUserDepCode) && StringUtil.equals(waybillEntity.getIsWholeVehicle(), FossConstants.YES) && StringUtil.equals(waybillEntity.getIsPassOwnDepartment(), FossConstants.YES)) {
			//收货客户名称(收货人/公司)
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerName + suffix, "");
			//收货客户手机(联系电话)
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerMobilephone + suffix, "");
		} else {
			//收货客户名称(收货人/公司)
			// BUG-35644 2013-6-21 9:15:42 wangfei
			//单号110320801货物被系统中"系统中收获客户"提走，真正的收货人（系统中收获联系人）没有提到货物！
			//			if (StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerName())) {
			//				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerName + suffix, waybillEntity.getReceiveCustomerContact() + ArriveSheetConstants.BACKSLASH + waybillEntity.getReceiveCustomerName());
			//			} else {
			//MANA-1976 在FOSS开单等界面添加大客户标记 判断收货客户是否大客户，如果是  在内容前面加"VIP"
			if(StringUtils.isNotBlank(waybillEntity.getReceiveBigCustomer())&& FossConstants.YES.equals(waybillEntity.getReceiveBigCustomer())){
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerName + suffix, ArriveSheetConstants.VIP+waybillEntity.getReceiveCustomerContact());
				//			}
				//收货客户名称(收货人/公司)
				//			if (StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerName())) {
				//				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerNameA + suffix, defaultIfNull(waybillEntity.getReceiveCustomerContact()) + ArriveSheetConstants.BACKSLASH + defaultIfNull(waybillEntity.getReceiveCustomerName()));
				//			} else {
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerNameA + suffix, defaultIfNull(ArriveSheetConstants.VIP+waybillEntity.getReceiveCustomerContact()));
			//			}
			}else {
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerName + suffix, waybillEntity.getReceiveCustomerContact());
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerNameA + suffix, defaultIfNull(waybillEntity.getReceiveCustomerContact()));
			}
			StringBuffer phone = new StringBuffer();
			if (StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerPhone()) && StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerMobilephone())) {
				//收货客户手机(联系电话)
				phone.append(waybillEntity.getReceiveCustomerMobilephone());
				phone.append(ArriveSheetConstants.BACKSLASH);
				phone.append(waybillEntity.getReceiveCustomerPhone());
			} else if (StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerPhone())) {
				phone.append(waybillEntity.getReceiveCustomerPhone());
			} else if (StringUtil.isNotEmpty(waybillEntity.getReceiveCustomerMobilephone())) {
				phone.append(waybillEntity.getReceiveCustomerMobilephone());
			}
			phone.append(ArriveSheetConstants.SPAN1);
			String tel = null;
			if(phone.length() > NumberConstants.NUMBER_36)
			{
				tel = phone.toString().replace("+", "/").replace("/", "/ ").substring(0,NumberConstants.NUMBER_36);
			}else{
				tel = phone.toString().replace("+", "/").replace("/", "/ ");
			}
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerMobilephone + suffix, tel);
		}
		//收货具体地址(地址)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerAddress + suffix, waybillEntity.getReceiveCustomerAddress());
		//货物名称(货物品名)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_goodsName + suffix, waybillEntity.getGoodsName());
		if(ProductEntityConstants.PRICING_PRODUCT_C1_C20002.equals(waybillEntity.getTransportType())){//如果运输类型是空运
			if(StringUtils.isNotBlank(arriveEntity.getTogetherSendCode())&&StringUtils.isNotBlank(suffix)&& (suffix.equals(ArriveSheetConstants.ARRIVE_SHEET_PRINT_SUFFIX_ONE)||suffix.equals(ArriveSheetConstants.ARRIVE_SHEET_PRINT_SUFFIX_TWO))){
				//交货方式+合送编码－客户联显示
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveMethod + suffix+suffix.substring(1), DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS_AIR));
				//交货方式+合送编码
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveMethod + suffix, DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS_AIR)+"/"+arriveEntity.getTogetherSendCode());
			}else {
				if(StringUtils.isNotBlank(suffix)){
					//交货方式-客户联显示
					parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveMethod + suffix+suffix.substring(1), DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS_AIR));
					//交货方式
				}
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveMethod + suffix, DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS_AIR));
			}
		}else {
			if(StringUtils.isNotBlank(arriveEntity.getTogetherSendCode())&&StringUtils.isNotBlank(suffix)&& (suffix.equals(ArriveSheetConstants.ARRIVE_SHEET_PRINT_SUFFIX_ONE)||suffix.equals(ArriveSheetConstants.ARRIVE_SHEET_PRINT_SUFFIX_TWO))){
				//交货方式+合送编码
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveMethod + suffix+suffix.substring(1), DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
				//交货方式+合送编码
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveMethod + suffix, DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS)+"/"+arriveEntity.getTogetherSendCode());
			}else{
				if(StringUtils.isNotBlank(suffix)){
					//交货方式
					parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveMethod + suffix+suffix.substring(1), DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
				}
				//交货方式
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveMethod + suffix, DictUtil.rendererSubmitToDisplay(waybillEntity.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
			
			}
		}
		//备注(其他服务)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_outerNotes + suffix, waybillEntity.getTransportationRemark());
		//运单号
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_waybillNo + suffix, getWaybillNoStyle(waybillEntity.getWaybillNo()));
		//流水号
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_serial + suffix, arriveEntity.getArrivesheetNo().substring(waybillEntity.getWaybillNo().length()));

		/****************************************************************** 货物基本信息 ******************************************************************/
		//货物包装(货物基本信息-包装)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_goodsPackage + suffix, waybillEntity.getGoodsPackage());
		//件数-当前签收件数--以签收件数--总件数--开始
		WaybillSignResultEntity waybillSign = new WaybillSignResultEntity();
		waybillSign.setWaybillNo(arriveEntity.getWaybillNo());
		waybillSign.setActive(FossConstants.ACTIVE);
		WaybillSignResultEntity signResult = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybillSign);
		Integer signGoodsQty = signResult == null ? 0 : signResult.getSignGoodsQty();
		//到达联模板上需打印已送多少件，还剩多少件(当前件数/以签收件数/总件数)件数-当前签收件数--以签收件数--总件数--结束
		//(件数)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_goodsQty + suffix, arriveEntity.getArriveSheetGoodsQty() + ArriveSheetConstants.BACKSLASH + signGoodsQty.intValue() + ArriveSheetConstants.BACKSLASH + waybillEntity.getGoodsQtyTotal());
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_goodsQty1 + suffix, arriveEntity.getArriveSheetGoodsQty());
		if(orderChannel !=null && !"".equals(orderChannel) && ArriveSheetConstants.ORDERCHANNEL.equals(orderChannel)){
		}else{
			//计费信息
			//货物总重量(货物基本信息-重量)
			if (waybillEntity.getGoodsWeightTotal() != null) {
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_goodsWeightTotal + suffix, "重量 " + waybillEntity.getGoodsWeightTotal() + ArriveSheetConstants.WEIGHT_UNIT);
			}
			//货物总体积(货物基本信息-体积)
			if (waybillEntity.getGoodsVolumeTotal() != null) {
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_goodsVolumeTotal + suffix, "体积 " + waybillEntity.getGoodsVolumeTotal() + ArriveSheetConstants.VOLUME_UNIT);
			}
			//运费计费类型(货物基本信息-计费方式)
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_billingType + suffix, StringUtil.equals(WaybillConstants.BILLINGWAY_WEIGHT, waybillEntity.getBillingType()) ? "重量计费" : "体积计费");
			//如果是汽运偏线，隐藏
			if(!StringUtil.equals(waybillEntity.getProductCode().trim(), ArriveSheetPrintService.PLF_NAME)){
				//计费重量(货物基本信息-计费重量)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_billWeight + suffix, waybillEntity.getBillWeight());
			}
		}
		/****************************************************************** 到达部门信息 ******************************************************************/
		//收货市(始发站FROM)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveCustomerCityCode + suffix, waybillEntity.getReceiveCustomerCityCode());
		//目的站(目的站TO)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_targetOrgCode + suffix, waybillEntity.getTargetOrgCode());
		/*********** 到达部门存根开始 ************/
		//收货市
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliveryCustomerCityCode + suffix, waybillEntity.getDeliveryCustomerCityCode());
		//运输方式 BUG-17788
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_transportType + suffix, waybillEntity.getProductCode());

		/****************************************************************** 底部信息 ******************************************************************/
		//【收货部门(出发部门)+地址+发货网点电话】(发货网点电话)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_receiveOrgCodeAndPhone + suffix, waybillEntity.getReceiveOrgName() + ArriveSheetConstants.BACKSLASH
				+ waybillEntity.getReceiveDepTelephone()+ ArriveSheetConstants.BACKSLASH + defaultIfNull(waybillEntity.getReceiveOrgAddress()));
		//【提货网点+提货网点电话】（提货网点电话）
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_deliveryOrgCodeAndPhone + suffix, defaultIfNull(waybillEntity.getCustomerPickupOrgCode()) + ArriveSheetConstants.BACKSLASH + defaultIfNull(waybillEntity.getCustomerPickupOrgAddress())
				+ ArriveSheetConstants.BACKSLASH + defaultIfNull(waybillEntity.getCustomerPickupTelephone()));
		//提货网点(制单人/时间)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_createAndPrintTime + suffix, StringUtil.defaultIfNull(currentUserEmpName) + ArriveSheetConstants.BACKSLASH + DateUtils.convert(new Date()));
		//开单人/开单时间 打印次数
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_billUserAndTime + suffix, "开单人："+StringUtil.defaultIfNull(waybillEntity.getBillCreateUserName()) + ArriveSheetConstants.BACKSLASH + DateUtils.convert(waybillEntity.getBillCreateTime())+" ");
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_printTime + suffix, "第"+waybillEntity.getPrintNum() +"次打印");
	}

	/**
	 * 
	 * 到达联打印基本费用
	 * 
	 * @param waybillEntity
	 * @param parameter
	 * @param _ApplicationContext
	 * @author ibm-wangfei
	 * @date Apr 8, 2013 5:05:17 PM
	 */
	public static boolean setGatArriveSheetInfo(ArriveSheetWaybillAddPropertyDto waybillEntity, Map<String, Object> parameter, ApplicationContext _ApplicationContext, String suffix) {
		//保价声明价值(保价声明价值)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_insuranceAmount + suffix, waybillEntity.getInsuranceAmount() + " ");
		//代收货款(代收货款)
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_codAmount + suffix, waybillEntity.getCodAmount() + " ");
		boolean isGat = false;
		//		boolean isOnlinePay = true;
		//货款结清接口
		//		if (StringUtil.equals(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE, waybillEntity.getPaidMethod())) {
		//			// 如果是网上支付，判断是否货款已结清
		//			IBillReceivableService billReceivableService = (IBillReceivableService) _ApplicationContext.getBean("billReceivableService");
		//			//根据运单号，查询开单付款方式为网上支付的应收单的未核销金额
		//			WaybillReceivableDto receivableDto = billReceivableService.queryReceivableAmountByWaybillNO(waybillEntity.getWaybillNo());
		//			//没有数据的话，返回为空,没有未结清欠款,yes为结清货款，no为未结清货款
		//			if (receivableDto != null) {
		//				isOnlinePay = false;
		//			}
		//		}
		//		BigDecimal exchangeRate = null;
		// 判断运单的提货网点所在城市 港澳台
		if (StringUtils.equals(waybillEntity.getCustomerPickupProvCode(), WaybillConstants.ORG_CODE_HONGKONG) || StringUtils.equals(waybillEntity.getCustomerPickupProvCode(), WaybillConstants.ORG_CODE_TAIWAN)
				|| StringUtils.equals(waybillEntity.getCustomerPickupProvCode(), WaybillConstants.ORG_CODE_HONGKONG)) {
			isGat = true;
			//			exchangeRate = getExchangeRate(_ApplicationContext);
			//			if (exchangeRate == null || exchangeRate.intValue() == 0) {
			//				// 如果汇率为null或者为0，不设置
			//				isGat = false;
			//			}
		}

		//如果货款已经结清 则费用合计显示为0 ，到达联模板上需打印已送多少件，还剩多少件。
		//Boolean freeBoolean = FossConstants.YES.equals(waybillEntity.getSettleStatus()) ? true : false;
		Boolean freeBoolean = true;// true 已结清、false 未结清
		BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(waybillEntity.getWaybillNo());
		// 根据运单号查询客户的应收单到付金额和应收代收货款金额
		IBillReceivableService billReceivableService = (IBillReceivableService) _ApplicationContext.getBean("billReceivableService");
		//财务单据查询，灰度改造   353654 ---------------------------start 
		List<BillReceivableEntity> billReceivableEntities = null;
		String vestSystemCode = null;
        try {
        	List<String> arrayList = new ArrayList<String>();
        	arrayList.add(waybillEntity.getWaybillNo());
        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".setGatArriveSheetInfo",
        			SettlementConstants.TYPE_FOSS);
			if(StringUtils.isBlank(grayByWaybillNoUrl)){
				LOGGER.error("grayByWaybillNoUrl isBlank" );
			}
        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),StringUtils.isBlank(grayByWaybillNoUrl)?"http://cubc.deppon.com/ashy-web/webservice/v1/ashy/vestService/vestAscription":grayByWaybillNoUrl);
        	List<VestBatchResult> list = response.getVestBatchResult();
        	vestSystemCode = list.get(0).getVestSystemCode();	
		} catch (Exception e) {
			LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".setGatArriveSheetInfo");
			LOGGER.error("打印到达联异常：" , e);
			throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		}
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			LOGGER.info("FOSS查询财务单据开始!运单号："+ waybillEntity.getWaybillNo());
			billReceivableEntities = billReceivableService.queryReceivableAmountByCondition(billReceiveable);
			LOGGER.info("FOSS查询财务单据完成!运单号："+ waybillEntity.getWaybillNo());
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			try {
				billReceivableEntities = cubcQueryReceivableAmountService.queryReceivableAmount(waybillEntity.getWaybillNo());			
			} catch (Exception e) {
				LOGGER.error("调用CUBC结清查询财务单据异常信息为："+e.getMessage());
				throw new CUBCGrayException("呀,亲!系统繁忙,CUBC查询财务单据失败,请稍后重试!");
			}
		}
		//财务单据查询，灰度改造   353654 ---------------------------end
		//运单到付运费用来判断
		BigDecimal toPayFreight = waybillEntity.getToPayAmount() == null ? new BigDecimal(0) : waybillEntity.getToPayAmount();
		if (CollectionUtils.isEmpty(billReceivableEntities)) {
			if(toPayFreight.compareTo(BigDecimal.valueOf(0)) > 0){
				freeBoolean = false;
			}else{
				freeBoolean = true;
			}
		} else {
			for (BillReceivableEntity billReceivableEntity : billReceivableEntities) {
				// 到达应收单
				//				if (StringUtil.equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE, billReceivableEntity.getBillType()) || 
				//						StringUtil.equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE, billReceivableEntity.getBillType())) {
				// 有应收到付款或代收货款应收单
				if (billReceivableEntity.getUnverifyAmount() != null && billReceivableEntity.getUnverifyAmount().compareTo(BigDecimal.valueOf(0)) > 0) {
					freeBoolean = false;
					break;
				}
				//				} 
			}
		}
		String orderChannel=waybillEntity.getOrderChannel();
		if(orderChannel !=null && !"".equals(orderChannel) && ArriveSheetConstants.ORDERCHANNEL.equals(orderChannel)){
			//如果订单来源是阿里巴巴商城则将保管费设置为空值
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_storageCharge + suffix, "");
		}else{
			//如果是汽运偏线，隐藏
			if(!StringUtil.equals(waybillEntity.getProductCode().trim(), ArriveSheetPrintService.PLF_NAME)){
			// 已结清
			//预付保密--显示
			if (FossConstants.NO.equals(waybillEntity.getSecretPrepaid())) {
				//(货物基本信息-费率)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_unitPrice + suffix, waybillEntity.getUnitPrice() + ArriveSheetConstants.SPAN1);
				//(货物基本信息-运费)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_transportFee + suffix, WaybillConstants.RMB + waybillEntity.getTransportFee() + ArriveSheetConstants.SPAN1);
				StringBuffer prePayAmount = new StringBuffer();
				prePayAmount.append("现付:");
				prePayAmount.append(WaybillConstants.RMB);
				prePayAmount.append(waybillEntity.getPrePayAmount());
				//预付金额(货物基本信息-费用合计)
				if (isGat == true) {
					// 港版添加
					prePayAmount.append(WaybillConstants.HK_UNIT);
					prePayAmount.append(numToChinese(waybillEntity.getPrePayAmount(), _ApplicationContext));
				}
				//			if (isOnlinePay == false) {
				//				// 网上支付未付款添加
				//				prePayAmount.append("(未付)");
				//			}
				prePayAmount.append("  \n");
				prePayAmount.append(MoneyUtils.amountToChinese(waybillEntity.getPrePayAmount()));
				prePayAmount.append(ArriveSheetConstants.SPAN1);
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_prePayAmount + suffix, prePayAmount.toString());
			} else {
				//(货物基本信息-费率)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_unitPrice + suffix, ArriveSheetConstants.XINGHAO);
				//(货物基本信息-运费)
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_transportFee + suffix, ArriveSheetConstants.XINGHAO);
				//预付金额(货物基本信息-费用合计)
				//			if (isOnlinePay == false) {
				//				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_prePayAmount + suffix, "现付:" + ArriveSheetConstants.XINGHAO + "(未付) ");
				//			} else {
				parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_prePayAmount + suffix, "现付:" + ArriveSheetConstants.XINGHAO);
				//			}
			}
			}
			//到付金额(货物基本信息-费用合计) 
			StringBuffer toPayAmount = new StringBuffer();
			toPayAmount.append("到付:");
			toPayAmount.append(WaybillConstants.RMB);
			toPayAmount.append(waybillEntity.getToPayAmount() == null ? 0 : waybillEntity.getToPayAmount());
			if (isGat == true) {
				// 港版添加
				toPayAmount.append(WaybillConstants.HK_UNIT);
				toPayAmount.append(numToChinese(waybillEntity.getToPayAmount(), _ApplicationContext));
			}
/*			ISaleDepartmentService saleDepartmentService = (ISaleDepartmentService) _ApplicationContext.getBean("saleDepartmentService");
			IWaybillManagerService waybillManagerService = (IWaybillManagerService) _ApplicationContext.getBean("waybillManagerService");
			//根据运单号查询 查询运单信息
			WaybillEntity entity = waybillManagerService.queryPartWaybillByNo(waybillEntity.getWaybillNo());
			SaleDepartmentEntity  startSaleDept = saleDepartmentService.querySaleDepartmentInfoByCode(entity.getReceiveOrgCode());
			SaleDepartmentEntity  destSaleDept = saleDepartmentService.querySaleDepartmentInfoByCode(entity.getLastLoadOrgCode());*/
			/*2016-07-25 DBA转数据遗漏数据，导致打印到达联出现：有到达应收金额显示已付（实际未支付）；
			 决策临时方案：到达联打印不显示（已付、未付）。（后续需要走需求做更完善的优化）
			 if (null != destSaleDept && null != startSaleDept) {
				if (FossConstants.YES.equals(destSaleDept.getIsLeagueSaleDept()) || FossConstants.YES.equals(startSaleDept.getIsLeagueSaleDept())) {
					// 设置到付金额（合伙人相关去掉支付状态）
					// 不设置已付，不做任何处理
				}else{
					if (freeBoolean == true) {
						toPayAmount.append("(已付)");
					}
				}
			} else {
				if (freeBoolean == true) {
					toPayAmount.append("(已付)");
				}
			}*/
			if (freeBoolean == true) {
				toPayAmount.append("(已付)");
			}
			toPayAmount.append("  \n");
			toPayAmount.append(MoneyUtils.amountToChinese(waybillEntity.getToPayAmount())).append(ArriveSheetConstants.SPAN1);
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_toPayAmount + suffix, toPayAmount.toString());
			//保管费金额(货物基本信息-费用合计) 
			StringBuffer storageCharge = new StringBuffer();
			storageCharge.append("保管费:");
			storageCharge.append(WaybillConstants.RMB);
			IWaybillExpressService waybillExpressService = (IWaybillExpressService)_ApplicationContext.getBean("waybillExpressService");
			//过滤数据     派送方式非自提    整车、偏线、空运及快递货物免收保管费  add by yangkang
			if(waybillEntity.getReceiveMethod().indexOf(SignConstants.RECEIVE_METHOD)==-1
					||WaybillConstants.MONTH_PAYMENT.equals(waybillEntity.getPaidMethod())
					||ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())
					||ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillEntity.getProductCode())
					||ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(waybillEntity.getProductCode())
					||waybillExpressService.onlineDetermineIsExpressByProductCode
										(waybillEntity.getProductCode(),waybillEntity.getBillTime())
					){
				
					waybillEntity.setStorageCharge(BigDecimal.ZERO);
						
			}
			storageCharge.append(waybillEntity.getStorageCharge() == null ? 0 : waybillEntity.getStorageCharge().setScale(0, BigDecimal.ROUND_HALF_UP));
			if (isGat == true) {
				// 港版添加
				storageCharge.append(WaybillConstants.HK_UNIT);
				storageCharge.append(numToChinese(waybillEntity.getStorageCharge(), _ApplicationContext));
			}
			storageCharge.append("  \n");
			storageCharge.append(MoneyUtils.amountToChinese(waybillEntity.getStorageCharge())).append(ArriveSheetConstants.SPAN1);
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_storageCharge + suffix, storageCharge.toString());
		}
		return freeBoolean;

	}

	/**
	 * 
	 * 设置其他费用
	 * 
	 * @author ibm-wangfei
	 * @date Jun 3, 2013 10:55:34 PM
	 */
	public static void setOtherFee(ArriveSheetWaybillAddPropertyDto waybillEntity, boolean secretPrepaid, Map<String, Object> parameter, String arrivePrintType, String suffix) {
		
		//如果是汽运偏线，隐藏其他费用（送货费 代收货款费 包装费）
		if(StringUtil.equals(waybillEntity.getProductCode().trim(), ArriveSheetPrintService.PLF_NAME)){
			return;
		}
		
		StringBuffer ortherFree = new StringBuffer();
		List<WaybillChargeDtlPrintDto> newDtos = new ArrayList<WaybillChargeDtlPrintDto>();
		for (WaybillChargeDtlPrintDto chargeDtlPrintDto : waybillEntity.getWaybillChargeDtlPrintDtos()) {
			if (!ArriveSheetConstants.NO_PRINT_FEE.containsKey(chargeDtlPrintDto.getPricingEntryCode()) && isAdd(chargeDtlPrintDto)) {
				// 对查询出来的不需要的费用列表取消
				newDtos.add(chargeDtlPrintDto);
			}
		}
		boolean isInit = StringUtil.equals(FossConstants.ACTIVE, waybillEntity.getIsInit());
		WaybillChargeDtlPrintDto chargeDtlPrintDto = null;
		// 送货费
		if (isInit == true && isAdd(waybillEntity.getDeliveryGoodsFee())) {
			chargeDtlPrintDto = new WaybillChargeDtlPrintDto();
			chargeDtlPrintDto.setPricingEntryName("送货费");
			chargeDtlPrintDto.setAmount(waybillEntity.getDeliveryGoodsFee());
			newDtos.add(chargeDtlPrintDto);
		}
		// 接货费
		if (isAdd(waybillEntity.getPickupFee())) {
			chargeDtlPrintDto = new WaybillChargeDtlPrintDto();
			chargeDtlPrintDto.setPricingEntryName("接货费");
			chargeDtlPrintDto.setAmount(waybillEntity.getPickupFee());
			newDtos.add(chargeDtlPrintDto);
		}
		//保价费(保价费)
		if (secretPrepaid == true) {
			// 保密
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_insuranceFee + suffix, ArriveSheetConstants.XINGHAO);
		} else {
			parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_insuranceFee + suffix, WaybillConstants.RMB + waybillEntity.getInsuranceFee());
		}
		// 返单费--运单表没有
		// 代收货款费
		if (isAdd(waybillEntity.getCodFee())) {
			chargeDtlPrintDto = new WaybillChargeDtlPrintDto();
			chargeDtlPrintDto.setPricingEntryName("代收货款费");
			chargeDtlPrintDto.setAmount(waybillEntity.getCodFee());
			newDtos.add(chargeDtlPrintDto);
		}
		// 包装费
		if (isAdd(waybillEntity.getPackageFee())) {
			chargeDtlPrintDto = new WaybillChargeDtlPrintDto();
			chargeDtlPrintDto.setPricingEntryName("包装费");
			chargeDtlPrintDto.setAmount(waybillEntity.getPackageFee());
			newDtos.add(chargeDtlPrintDto);
		}
		//		if (isInit == true) {
		//			// 迁移运单添加其他费用
		//			if (isAdd(waybillEntity.getOtherFee())) {
		//				chargeDtlPrintDto = new WaybillChargeDtlPrintDto();
		//				chargeDtlPrintDto.setPricingEntryName("其他费");
		//				chargeDtlPrintDto.setAmount(waybillEntity.getOtherFee());
		//				newDtos.add(chargeDtlPrintDto);
		//			}
		//		}
		String split = " \n\n";
		if (newDtos.size() > NumberConstants.NUMBER_5) {
			split = " \n";
		}
		for (WaybillChargeDtlPrintDto dto : newDtos) {
			//添加费用名字和金额
			if (StringUtil.equals(dto.getPricingEntryName(), "送货费") || secretPrepaid == false) {
				ortherFree.append(dto.getPricingEntryName()).append(ArriveSheetConstants.SPAN1).append(dto.getAmount()).append(split);
			} else {
				ortherFree.append(dto.getPricingEntryName()).append(ArriveSheetConstants.SPAN1).append(ArriveSheetConstants.XINGHAO).append(split);
			}
		}
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_ortherFree + suffix, ortherFree);

		if (StringUtil.equals(ArriveSheetConstants.ARRIVE_SHEET_PRINT_TYPE_PIN, arrivePrintType)) {
			// 如果到达联模版不是激光普通版，返回本方法
			return;
		}

		StringBuffer ortherFree1 = new StringBuffer();
		if (CollectionUtils.isEmpty(newDtos)) {
			return;
		}
		// 添加保价费
		if (isAdd(waybillEntity.getInsuranceFee())) {
			chargeDtlPrintDto = new WaybillChargeDtlPrintDto();
			chargeDtlPrintDto.setPricingEntryName("保价费");
			chargeDtlPrintDto.setAmount(waybillEntity.getInsuranceFee());
			newDtos.add(chargeDtlPrintDto);
		}
		if (newDtos.size() <= NumberConstants.NUMBER_4) {
			for (WaybillChargeDtlPrintDto dto : newDtos) {
				//添加费用名字和金额
				if (StringUtil.equals(dto.getPricingEntryName(), "送货费") || secretPrepaid == false) {
					ortherFree1.append(dto.getPricingEntryName()).append(ArriveSheetConstants.SPAN1).append(dto.getAmount()).append("\n");
				} else {
					ortherFree1.append(dto.getPricingEntryName()).append(ArriveSheetConstants.SPAN1).append(ArriveSheetConstants.XINGHAO).append("\n");
				}
			}
		} else {
			for (int i = 0; i < newDtos.size(); i++) {
				WaybillChargeDtlPrintDto dto = newDtos.get(i);
				if (StringUtil.equals(dto.getPricingEntryName(), "送货费") || secretPrepaid == false) {
					ortherFree1.append(dto.getPricingEntryName()).append(ArriveSheetConstants.SPAN1).append(dto.getAmount()).append(ArriveSheetConstants.SPAN);
				} else {
					ortherFree1.append(dto.getPricingEntryName()).append(ArriveSheetConstants.SPAN1).append(ArriveSheetConstants.XINGHAO).append(ArriveSheetConstants.SPAN);
				}
				if (i + 2 <= newDtos.size()) {
					if (StringUtil.equals(newDtos.get(i + 1).getPricingEntryName(), "送货费") || secretPrepaid == false) {
						ortherFree1.append(newDtos.get(i + 1).getPricingEntryName()).append(ArriveSheetConstants.SPAN1).append(newDtos.get(i + 1).getAmount()).append("\n");
					} else {
						ortherFree1.append(newDtos.get(i + 1).getPricingEntryName()).append(ArriveSheetConstants.SPAN1).append(ArriveSheetConstants.XINGHAO).append("\n");
					}
					i++;
				}
			}
		}
		parameter.put(ArriveSheetConstants.ARRIVE_SHEET_PRINT_ortherFree1 + suffix, ortherFree1.toString());
	}

	/**
	 * 
	 * 获取汇率
	 * 
	 * @param _ApplicationContext
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 9, 2013 10:00:44 AM
	 */
	@SuppressWarnings("unused")
	private static BigDecimal getExchangeRate(ApplicationContext _ApplicationContext) {
		// 获取汇率
		//IConfigurationParamsService
		IConfigurationParamsService configurationParamsService = (IConfigurationParamsService) _ApplicationContext.getBean("configurationParamsService");
		String confValue = configurationParamsService.queryConfValueByCode(WaybillConstants.RMB_AGAINST_HK);
		if (StringUtils.isEmpty(confValue)) {
			return null;
		}
		// 汇率
		try {
			return BigDecimal.valueOf(Float.valueOf(confValue)).setScale(NumberConstants.NUMBER_4, BigDecimal.ROUND_DOWN);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * 汇率计算
	 * 
	 * @param money
	 * @param number
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 9, 2013 10:59:14 AM
	 */
	private static int numToChinese(BigDecimal money, ApplicationContext _ApplicationContext) {
		if (money == null || money.intValue() == 0) {
			return 0;
		}
		try {
			IExchangeRateService exchangeRateService = (IExchangeRateService) _ApplicationContext.getBean("exchangeRateService");
			BigDecimal moneyBigDecimal = exchangeRateService.getExchangedFee(money, DictionaryValueConstants.SETTLEMENT__CURRENCY_CODE__HKD, new Date());
			if (moneyBigDecimal == null) {
				// 如果没有汇率
				return money.intValue();
			}
			moneyBigDecimal = moneyBigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
			return moneyBigDecimal.intValue();
		} catch (Exception e) {
			return money.intValue();
		}
	}

	/**
	 * 
	 * 判断是否添加
	 * 
	 * @param chargeDtlPrintDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 4, 2013 8:40:06 PM
	 */
	private static boolean isAdd(WaybillChargeDtlPrintDto chargeDtlPrintDto) {
		if (StringUtil.isEmpty(chargeDtlPrintDto.getPricingEntryName())) {
			return false;
		}
		if (chargeDtlPrintDto.getAmount() == null) {
			return false;
		}
		if (chargeDtlPrintDto.getAmount().intValue() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 判断是否添加
	 * 
	 * @param chargeDtlPrintDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 4, 2013 8:40:06 PM
	 */
	private static boolean isAdd(BigDecimal fee) {
		if (fee == null) {
			return false;
		}
		if (fee.intValue() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 获取分割后的运单号
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 20, 2013 11:49:52 AM
	 */
	private static String getWaybillNoStyle(String waybillNo) {
		int length = waybillNo.length();
		char[] value = new char[length << 1];
		for (int i = 0, j = 0; i < length; ++i, j = i << 1) {
			value[j] = waybillNo.charAt(i);
			value[1 + j] = ' ';
		}
		return new String(value);
	}

	/**
	 * 
	 * 判断字符是否等于null or 空
	 * 
	 * @param str
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 20, 2013 11:50:13 AM
	 */
	private static String defaultIfNull(String str) {
		return StringUtil.defaultIfNull(str);
	}

	/**
	 * 
	 * 返回经过封装的到达联编号
	 * 
	 * @param arriveSheetNos
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 20, 2013 11:54:56 AM
	 */
	public static List<String> getTwoArriveSheetNoList(String arriveSheetNos[]) {
		List<String> twoArriveSheetNoList = new ArrayList<String>();
		for (int i = 0; i < arriveSheetNos.length; i++) {
			String twoArriveSheetNo = arriveSheetNos[i];
			if (i + 2 <= arriveSheetNos.length) {
				twoArriveSheetNo = twoArriveSheetNo + "," + arriveSheetNos[i + 1];
				i++;
			}
			twoArriveSheetNoList.add(twoArriveSheetNo);
		}
		return twoArriveSheetNoList;
	}
}

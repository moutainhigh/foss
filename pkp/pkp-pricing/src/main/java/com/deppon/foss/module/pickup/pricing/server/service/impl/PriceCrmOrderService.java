package com.deppon.foss.module.pickup.pricing.server.service.impl;
import java.lang.reflect.InvocationTargetException;

import javax.xml.ws.Holder;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm._interface.crmservice.CommException;
import com.deppon.crm._interface.crmservice.FossToCrmService;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.esb.crm.client.AmountInfo;
import com.deppon.foss.esb.crm.client.CouponWaybillInfo;
import com.deppon.foss.esb.crm.client.ValidateCouponRequest;
import com.deppon.foss.esb.crm.client.ValidateCouponResponse;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceCrmOrderService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.CrmOrderImportException;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.util.UUIDUtils;


public class PriceCrmOrderService implements IPriceCrmOrderService {


	/**
	 * 日志
	 */
	protected final static Logger logger = LoggerFactory.getLogger(PriceCrmOrderService.class.getName());

	/**
	 * 版本号
	 */
	private static final String VERSION = "0.1";

	/**
	 * 业务ID
	 */
	private static final String BUSINESS_ID = "ORDER";

	/**
	 * ws连接超时
	 */
	private static final String DEFAULT_CONNECTION_TIMEOUT = "5000";

	/**
	 * ws请求超时
	 */
	private static final String DEFAULT_RECEIVE_TIMEOUT = "5000";

	/**
	 * CRM服务接口
	 */
	private FossToCrmService crmService;

	/**
	 * ESB处理SOAP HEADER帮助
	 */
	private Holder<ESBHeader> esbHeader;

	/**
	 * OMS查询订单详细信息URL
	 */
	private String importOmsOrderUrl;

	/**
	 * OMS查询订单列表URL
	 */
	private String 	queryOmsOrderListUrl;
	
	/**
	 * OMS restful接口地址 修改订单解屏  oms接口
	 */
	private String modifyOrderLockInfoUrl;
	

	public void setCrmService(FossToCrmService crmService) {
		this.crmService = crmService;
	}

	public String getImportOmsOrderUrl() {
		return importOmsOrderUrl;
	}

	public void setImportOmsOrderUrl(String importOmsOrderUrl) {
		this.importOmsOrderUrl = importOmsOrderUrl;
	}
	
	public String getQueryOmsOrderListUrl() {
		return queryOmsOrderListUrl;
	}

	public void setQueryOmsOrderListUrl(String queryOmsOrderListUrl) {
		this.queryOmsOrderListUrl = queryOmsOrderListUrl;
	}
   
	public String getModifyOrderLockInfoUrl() {
		return modifyOrderLockInfoUrl;
	}

	public void setModifyOrderLockInfoUrl(String modifyOrderLockInfoUrl) {
		this.modifyOrderLockInfoUrl = modifyOrderLockInfoUrl;
	}

	
	
	@Override
	/**
	 * 
	 * <p>
	 * 优惠劵验证
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-15 下午5:41:50
	 * @param couponInfo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService#validateCoupon(com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto)
	 */
	public CouponInfoResultDto validateCoupon(CouponInfoDto couponInfo) {
		CouponInfoResultDto res = null;
		try {
			ValidateCouponRequest request = convertValidateCouponRequest(couponInfo);
			if (request != null) {
				createHeader();
				esbHeader.value.setRequestId(UUIDUtils.getUUID());
				esbHeader.value
						.setEsbServiceCode("ESB_FOSS2ESB_VALIDATE_COUPON");
				//setInvokeTimeOut();
				ValidateCouponResponse response = crmService.validateCoupon(
						request, esbHeader);
				if (response != null) {
					res = new CouponInfoResultDto();
					res.setCanUse(response.isIsCanUse());
					res.setCouponAmount(response.getCouponAmount());
					logger.info("CRM返回优惠劵:"+couponInfo.getCouponNumber()+" 金额："+response.getCouponAmount());
					res.setCanNotUseReason(response.getCanNotUseReason());
					
					/**
					 * MANA-1961 营销活动与优惠券编码关联
					 * 2014-04-10 026123
					 */
					//测试数据
//					String[] strs = {PriceEntityConstants.PRICING_CODE_FRT,
//							PriceEntityConstants.PRICING_CODE_JH,
//							PriceEntityConstants.PRICING_CODE_BF,
//							PriceEntityConstants.PRICING_CODE_HK,
//							PriceEntityConstants.PRICING_CODE_SH,
//							PriceEntityConstants.PRICING_CODE_BZ,
//							PriceEntityConstants.PRICING_CODE_ZHXX};
					//Random random = new Random();
					//res.setDeductibleType(PriceEntityConstants.PRICING_CODE_BF);
					
					
					res.setDeductibleType(response.getDeductibleType());
					
				}
			}
		} catch (CommException e) {
			logger.info("调用优惠劵验证接口失败：" + e.getMessage(), e);
			throw new CrmOrderImportException(
					CrmOrderImportException.VALID_ATE_COUPON_FAIL, e);
		} catch (IllegalAccessException e) {
			logger.info("调用优惠劵验证接口失败-数据对象转换错误：" + e.getMessage(), e);
			throw new CrmOrderImportException(
					CrmOrderImportException.CONVERT_VALID_ATE_ERROR, e);
		} catch (InvocationTargetException e) {
			logger.info("调用优惠劵验证接口失败-数据对象转换错误：" + e.getMessage(), e);
			throw new CrmOrderImportException(
					CrmOrderImportException.CONVERT_VALID_ATE_ERROR, e);
		} catch (Exception e) {
			logger.info("调用优惠劵验证接口失败" + e.getMessage(), e);
			throw new CrmOrderImportException(
					CrmOrderImportException.VALID_ATE_COUPON_FAIL, e);
		}
		return res;
	}
	
	/**
	 * 
	 * <p>
	 * 优惠券验证接口请求对象转换
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-15 下午5:42:29
	 * @param couponInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @see
	 */
	private ValidateCouponRequest convertValidateCouponRequest(
			CouponInfoDto couponInfo) throws IllegalAccessException,
			InvocationTargetException {
		ValidateCouponRequest request = null;
		if (couponInfo != null) {
			request = new ValidateCouponRequest();
			if (couponInfo.getWaybillInfo() != null) {
				CouponWaybillInfo waybillInfo = new CouponWaybillInfo();
				CouponWaybillInfoDto waybill = couponInfo.getWaybillInfo();
				AmountInfo amountInfo;
				if (waybill.getAmountInfoList().size() > 0) {
					for (AmountInfoDto amountInfoDto : waybill
							.getAmountInfoList()) {
						amountInfo = new AmountInfo();
						BeanUtils.copyProperties(amountInfo, amountInfoDto);
						waybillInfo.getAmountList().add(amountInfo);
					}
				}
				waybillInfo.setArrivedDept(waybill.getArrivedDept());
				waybillInfo.setArrivedOutDept(waybill.getArrivedOutDept());
				waybillInfo.setCustNumber(waybill.getCustNumber());
				waybillInfo.setLeaveDept(waybill.getLeaveDept());
				waybillInfo.setLeaveMobile(waybill.getLeaveMobile());
				waybillInfo.setLeaveOutDept(waybill.getLeaveOutDept());
				waybillInfo.setLeavePhone(waybill.getLeavePhone());
				waybillInfo.setOrderNumber(waybill.getOrderNumber());
				waybillInfo.setOrderSource(waybill.getOrderSource());
				waybillInfo.setProduceType(waybill.getProduceType());
				waybillInfo.setTotalAmount(waybill.getTotalAmount());
				waybillInfo.setWaybillNumber(waybill.getWaybillNumber());
				/**
				 * 定价优化项目---配合降价返券需求改进优惠券生成
				 * 
				 * 接口新增四个字段（非必填）：出发城市名称、编码；到达城市名称、编码
				 * 
				 * @author Foss-206860
				 * */
				waybillInfo.setArrivedCity(waybill.getArrivedCity());
				waybillInfo.setArrivedCityCode(waybill.getArrivedCityCode());
				waybillInfo.setLeaveCity(waybill.getLeaveCity());
				waybillInfo.setLeaveCityCode(waybill.getLeaveCityCode());
				
				request.setWaybillInfo(waybillInfo);
			}
			request.setCouponNumber(couponInfo.getCouponNumber());
			request.setIsUsed(couponInfo.isUsed());
		}
		return request;
	}

	/**
	 * 
	 * <p>
	 * 创建SOAP Header
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-25 下午9:12:30
	 * @see
	 */
	private void createHeader() {
		if (this.esbHeader == null) {
			esbHeader = new Holder<ESBHeader>();
			ESBHeader header = new ESBHeader();
			header.setVersion(VERSION);
			header.setBusinessId(BUSINESS_ID);
			header.setSourceSystem("FOSS");
			header.setTargetSystem("CRM");
			header.setMessageFormat("SOAP");
			header.setExchangePattern(1);
			esbHeader.value = header;
		}
	}
}

package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.billcaculateservice.CommonException;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateDubboService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultOuterPriceCaccilateDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.WsGuiQueryBillCalculateSubDubboDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.WsGuiResultBillCalculateDubboDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.WsGuiResultDiscountDubboDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.WsResultOuterPriceCaccilateDubboDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.request.WsGuiQueryBillCalculateDubboRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryGuiBillPriceDubboResponse;
import com.deppon.foss.util.define.FossConstants;

/**
 * CC调用FOSS的价格计算
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-28 下午4:29:55,content:1.快递价格计算.2.零担价格计算</p>
 * 
 * @author 316759
 * @date 2017-2-28 下午4:29:55
 * @since
 * @version
 */
public class BillCaculateDubboService implements IBillCaculateDubboService {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillCaculateDubboService.class);

	private static String SUCCESS_YES = "Y";
	private static String SUCCESS_NO = "N";
	private BigDecimal defaultFirstWeight = BigDecimal.valueOf(0.5);
	/** 价格计算Service接口 */
	private IGuiBillCaculateService guiBillCaculateService;

	/**
	 * 注册BigDecimal转换器，否则Bigdecimal转换报错
	 */
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		DateConverter dateConverter = new DateConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
		ConvertUtils.register(dateConverter, Date.class);
	}

	/**
	 * <p>
	 * 快递价格查询
	 * </p>
	 * 
	 * @author 316759
	 * @date 2017-2-28 下午6:04:36
	 * @param request
	 * @return
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateDubboService#queryGuiExpressBillPrice(com.deppon.foss.module.pickup.waybill.shared.request.WsGuiQueryBillCalculateDubboRequest)
	 */
	@Override
	public QueryGuiBillPriceDubboResponse queryGuiExpressBillPrice(WsGuiQueryBillCalculateDubboRequest request) {
		QueryGuiBillPriceDubboResponse response = null;
		String message = "";
		List<GuiResultBillCalculateDto> guiResultBillCalculateLst = null;
		try {
			// 参数验证
			validateRequest(request);
			// 数据封装
			GuiQueryBillCalculateDto billCalculateDto = requestCopyProperties(request);
			// 计算快递物流费用
			guiResultBillCalculateLst = guiBillCaculateService.queryGuiExpressBillPrice(billCalculateDto);
			guiResultBillCalculateLst = queryExpQS(billCalculateDto, guiResultBillCalculateLst);
		} catch (BusinessException e) {
			if (StringUtil.isNotEmpty(e.getErrorCode())) {
				message = e.getErrorCode();
			}
			if (StringUtil.isNotEmpty(e.getMessage())) {
				message = message + e.getMessage();
			}
			if (StringUtils.isNotEmpty(message)) {
				if (message.indexOf("没有找到运费!") >= 0) {
					LOGGER.error("没有找到运费", message);
					response = new QueryGuiBillPriceDubboResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg(message);
					return response;
				} else if (message.indexOf("没有配置系统参数:") >= 0) {
					LOGGER.error("没有配置系统参数", message);
					response = new QueryGuiBillPriceDubboResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg(message);
					return response;
				} else {
					LOGGER.error("价格计算异常", message);
					response = new QueryGuiBillPriceDubboResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg("价格计算异常 =>" + message);
					return response;
				}
			} else {
				LOGGER.error("价格计算异常", "价格计算异常");
				response = new QueryGuiBillPriceDubboResponse();
				response.setSuccess(SUCCESS_NO);
				response.setMsg("价格计算异常 =>" + "价格计算异常");
				return response;
			}
		} catch (Exception ee) {
			message = ee.getMessage();
			if ("java.lang.reflect.UndeclaredThrowableException".equals(message)) {
				LOGGER.error("精准大票货找不到对应价格,请配置", message);
				message = "精准大票货找不到对应价格,请配置";
				response = new QueryGuiBillPriceDubboResponse();
				response.setSuccess(SUCCESS_NO);
				response.setMsg(message);
				return response;
			} else {
				LOGGER.error("价格计算异常", message);
				response = new QueryGuiBillPriceDubboResponse();
				response.setSuccess(SUCCESS_NO);
				response.setMsg("价格计算异常 =>" + message);
				return response;
			}
		}
		try {
			response = responseCopyProperties(guiResultBillCalculateLst);
		} catch (IllegalAccessException e) {
			message = "返回参数封装异常";
			LOGGER.error("返回参数封装异常", e);
			response = new QueryGuiBillPriceDubboResponse();
			response.setSuccess(SUCCESS_NO);
			response.setMsg(message);
			return response;
		} catch (InvocationTargetException e) {
			message = "返回参数封装异常";
			LOGGER.error("返回参数封装异常", e);
			response = new QueryGuiBillPriceDubboResponse();
			response.setSuccess(SUCCESS_NO);
			response.setMsg(message);
			return response;
		}
		response.setSuccess(SUCCESS_YES);
		return response;
	}

	/**
	 * <p>查询快递首重</p>
	 * 
	 * @author 316759
	 * @date 2017-2-28 下午6:05:12
	 * @param billCalculateDto
	 * @param guiResultBillCalculateLst
	 * @return
	 * @throws CommonException
	 * @see
	 */
	private List<GuiResultBillCalculateDto> queryExpQS(GuiQueryBillCalculateDto billCalculateDto, List<GuiResultBillCalculateDto> guiResultBillCalculateLst) throws CommonException {
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos2 = new ArrayList<GuiResultBillCalculateDto>();
		List<GuiQueryBillCalculateSubDto> reqBillCalculateSubDto = billCalculateDto.getPriceEntities();
		for (int i = 0; i < reqBillCalculateSubDto.size(); i++) {
			if (null != reqBillCalculateSubDto.get(i) && null != reqBillCalculateSubDto.get(i).getPriceEntityCode()) {
				if (StringUtil.equals(PriceEntityConstants.PRICING_CODE_QS, reqBillCalculateSubDto.get(i).getPriceEntityCode()) && StringUtil.equals(WaybillConstants.RETURNBILLTYPE_ORIGINAL, reqBillCalculateSubDto.get(i).getSubType())) {
					GuiQueryBillCalculateDto dto2 = new GuiQueryBillCalculateDto();
					try {
						PropertyUtils.copyProperties(dto2, billCalculateDto);
					} catch (Exception e) {
						e.printStackTrace();
					}
					List<GuiQueryBillCalculateSubDto> priceEntities2 = new ArrayList<GuiQueryBillCalculateSubDto>();
					for (GuiQueryBillCalculateSubDto d : billCalculateDto.getPriceEntities()) {
						if (PriceEntityConstants.PRICING_CODE_FRT.equals(d.getPriceEntityCode())) {
							GuiQueryBillCalculateSubDto d2 = new GuiQueryBillCalculateSubDto();
							try {
								PropertyUtils.copyProperties(d2, d);
							} catch (Exception e) {
								e.printStackTrace();
							}
							priceEntities2.add(d2);
						}
					}
					dto2.setPriceEntities(priceEntities2);
					dto2.setWeight(defaultFirstWeight);
					dto2.setVolume(BigDecimal.ZERO);
					dto2.setCustomerCode("");
					dto2.setIsSelfPickUp(FossConstants.NO);
					// 根据DMANA-4938修改，标准快递和3.60特惠件两种产品的原件签单返回计费统一按照标准快递首重收费
					if (ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(dto2.getProductCode()) || WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(dto2.getProductCode())) {
						dto2.setProductCode(ExpWaybillConstants.PACKAGE);
					}
					guiResultBillCalculateDtos2 = guiBillCaculateService.queryGuiExpressBillPrice(dto2);
					// 获取快递首重
					if (null != guiResultBillCalculateDtos2 && guiResultBillCalculateDtos2.size() > 0) {
						BigDecimal firstRateFee = guiResultBillCalculateDtos2.get(0).getCaculateFee();
						for (GuiResultBillCalculateDto resultDto : guiResultBillCalculateLst) {
							if (null != resultDto && null != resultDto.getPriceEntryCode()) {
								if (StringUtil.equals(PriceEntityConstants.PRICING_CODE_QS, resultDto.getPriceEntryCode()) && StringUtil.equals(WaybillConstants.RETURNBILLTYPE_ORIGINAL, resultDto.getSubType())) {
									resultDto.setCaculateFee(firstRateFee.setScale(0, BigDecimal.ROUND_HALF_UP));
								}
							}
						}
					}
				}
			}
		}
		return guiResultBillCalculateLst;
	}

	/**
	 * <p>零担价格查询</p>
	 * 
	 * @author 316759
	 * @date 2017-2-28 下午6:05:27
	 * @param request
	 * @return
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateDubboService#queryGuiBillPrice(com.deppon.foss.module.pickup.waybill.shared.request.WsGuiQueryBillCalculateDubboRequest)
	 */
	@Override
	public QueryGuiBillPriceDubboResponse queryGuiBillPrice(WsGuiQueryBillCalculateDubboRequest request) {
		QueryGuiBillPriceDubboResponse response = null;
		String message = "";
		// 计算结果集
		List<GuiResultBillCalculateDto> guiResultBillCalculateLst = null;
		try {
			// 参数验证
			validateRequest(request);
			// 数据封装
			GuiQueryBillCalculateDto billCalculateDto = requestCopyProperties(request);
			// 计算价格实现
			guiResultBillCalculateLst = guiBillCaculateService.queryGuiBillPrice(billCalculateDto);
		} catch (BusinessException e) {
			if (StringUtil.isNotEmpty(e.getErrorCode())) {
				message = e.getErrorCode();
			}
			if (StringUtil.isNotEmpty(e.getMessage())) {
				message = message + e.getMessage();
			}
			if (StringUtils.isNotEmpty(message)) {
				if (message.indexOf("没有找到运费!") >= 0) {
					LOGGER.error("没有找到运费", message);
					response = new QueryGuiBillPriceDubboResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg(message);
					return response;
				} else if (message.indexOf("没有配置系统参数:") >= 0) {
					LOGGER.error("没有配置系统参数", message);
					response = new QueryGuiBillPriceDubboResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg(message);
					return response;
				} else {
					LOGGER.error("价格计算异常", message);
					response = new QueryGuiBillPriceDubboResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg("价格计算异常 =>" + message);
					return response;
				}
			} else {
				LOGGER.error("价格计算异常", "价格计算异常");
				response = new QueryGuiBillPriceDubboResponse();
				response.setSuccess(SUCCESS_NO);
				response.setMsg("价格计算异常 =>" + "价格计算异常");
				return response;
			}
		} catch (Exception ee) {
			message = ee.getMessage();
			if ("java.lang.reflect.UndeclaredThrowableException".equals(message)) {
				LOGGER.error("精准大票货找不到对应价格,请配置", message);
				message = "精准大票货找不到对应价格,请配置";
				response = new QueryGuiBillPriceDubboResponse();
				response.setSuccess(SUCCESS_NO);
				response.setMsg(message);
				return response;
			} else {
				LOGGER.error("价格计算异常", message);
				response = new QueryGuiBillPriceDubboResponse();
				response.setSuccess(SUCCESS_NO);
				response.setMsg("价格计算异常 =>" + message);
				return response;
			}
		}
		try {
			// 结果数据封装
			response = responseCopyProperties(guiResultBillCalculateLst);
		} catch (IllegalAccessException e) {
			message = "返回参数封装异常";
			LOGGER.error("返回参数封装异常", e);
			response = new QueryGuiBillPriceDubboResponse();
			response.setSuccess(SUCCESS_NO);
			response.setMsg(message);
			return response;
		} catch (InvocationTargetException e) {
			message = "返回参数封装异常";
			LOGGER.error("返回参数封装异常", e);
			response = new QueryGuiBillPriceDubboResponse();
			response.setSuccess(SUCCESS_NO);
			response.setMsg(message);
			return response;
		}
		response.setSuccess(SUCCESS_YES);
		return response;
	}

	/**
	 * <p>请求参数转换方法</p>
	 * 
	 * @author 316759
	 * @date 2017-2-28 下午6:05:46
	 * @param request
	 * @return
	 * @throws CommonException
	 * @see
	 */
	private GuiQueryBillCalculateDto requestCopyProperties(WsGuiQueryBillCalculateDubboRequest request) throws CommonException {
		GuiQueryBillCalculateDto billCalculateDto = new GuiQueryBillCalculateDto();
		List<WsGuiQueryBillCalculateSubDubboDto> reqBillCalculateSubDto = request.getPriceEntities();
		try {
			XMLGregorianCalendar date = request.getReceiveDate();
			String[] receiveDate = new String[]{"receiveDate"};
			if (null == request.getFeeRate()) {
				request.setFeeRate(BigDecimal.ZERO);
			}
			if (null == request.getKilom()) {
				request.setKilom(BigDecimal.ZERO);
			}
			if (null == request.getOriginnalCost()) {
				request.setOriginnalCost(BigDecimal.ZERO);
			}
			if (null == request.getVolume()) {
				request.setVolume(BigDecimal.ZERO);
			}
			if (null == request.getWeight()) {
				request.setWeight(BigDecimal.ZERO);
			}
			if (null == request.getWoodenVolume()) {
				request.setWoodenVolume(BigDecimal.ZERO);
			}
			for (WsGuiQueryBillCalculateSubDubboDto priceEntity : request.getPriceEntities()) {
				if (null == priceEntity.getInsuranceAmount()) {
					priceEntity.setInsuranceAmount(BigDecimal.ZERO);
				}
				if (null == priceEntity.getInsuranceRate()) {
					priceEntity.setInsuranceRate(BigDecimal.ZERO);
				}
				if (null == priceEntity.getOriginnalCost()) {
					priceEntity.setOriginnalCost(BigDecimal.ZERO);
				}
				if (null == priceEntity.getWoodenVolume()) {
					priceEntity.setWoodenVolume(BigDecimal.ZERO);
				}
			}
			org.springframework.beans.BeanUtils.copyProperties(request, billCalculateDto, receiveDate);
			billCalculateDto.setReceiveDate(xmlGregorianCalendar2Date(date));
			List<GuiQueryBillCalculateSubDto> billCalculateSubDto = new ArrayList<GuiQueryBillCalculateSubDto>();
			// 复制计价条目
			for (int i = 0; i < reqBillCalculateSubDto.size(); i++) {
				if (null != reqBillCalculateSubDto.get(i) && null != reqBillCalculateSubDto.get(i).getPriceEntityCode()) {
					if (StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, reqBillCalculateSubDto.get(i).getPriceEntityCode())) {
						GuiQueryBillCalculateSubDto subDto = new GuiQueryBillCalculateSubDto();
						BeanUtils.copyProperties(subDto, reqBillCalculateSubDto.get(i));
						billCalculateSubDto.add(subDto);
						break;
					}
				}
			}
			for (int i = 0; i < reqBillCalculateSubDto.size(); i++) {
				if (null != reqBillCalculateSubDto.get(i) && null != reqBillCalculateSubDto.get(i).getPriceEntityCode()) {
					if (!StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, reqBillCalculateSubDto.get(i).getPriceEntityCode())) {
						GuiQueryBillCalculateSubDto subDto = new GuiQueryBillCalculateSubDto();
						BeanUtils.copyProperties(subDto, reqBillCalculateSubDto.get(i));
						billCalculateSubDto.add(subDto);
					}
				}
			}
			billCalculateDto.setPriceEntities(billCalculateSubDto);
		} catch (IllegalAccessException e) {
			LOGGER.error("请求参数封装异常", e);
			throw new CommonException("请求参数封装异常");

		} catch (InvocationTargetException e) {
			LOGGER.error("请求参数封装异常", e);
			throw new CommonException("请求参数封装异常");
		}
		return billCalculateDto;
	}

	/**
	 * <p>将XMLGregorianCalendar转换为Date</p>
	 * 
	 * @author 316759
	 * @date 2017-2-28 下午6:06:00
	 * @param date
	 * @return
	 * @see
	 */
	public static Date xmlGregorianCalendar2Date(XMLGregorianCalendar date) {
		if (date == null) {
			return null;
		} else {
			return date.toGregorianCalendar().getTime();
		}
	}

	/**
	 * <p>结果集转换成response</p>
	 * 
	 * @author 316759
	 * @date 2017-2-28 下午6:06:12
	 * @param guiResultBillCalculateLst
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @see
	 */
	private QueryGuiBillPriceDubboResponse responseCopyProperties(List<GuiResultBillCalculateDto> guiResultBillCalculateLst) throws IllegalAccessException, InvocationTargetException {
		QueryGuiBillPriceDubboResponse response = new QueryGuiBillPriceDubboResponse();
		BigDecimal value = new BigDecimal(0);
		List<WsGuiResultBillCalculateDubboDto> resResultBillCalDto = new ArrayList<WsGuiResultBillCalculateDubboDto>();
		for (int i = 0; i < guiResultBillCalculateLst.size(); i++) {
			GuiResultBillCalculateDto resultDto = guiResultBillCalculateLst.get(i);
			WsGuiResultBillCalculateDubboDto wsResultDto = new WsGuiResultBillCalculateDubboDto();
			BigDecimal minFee = resultDto.getMinFee();
			BigDecimal caculate = resultDto.getCaculateFee();
			if (null != resultDto && caculate == null) {
				if (null != minFee) {
					resultDto.setCaculateFee(minFee);
				} else {
					resultDto.setCaculateFee(new BigDecimal("0"));
				}
			} else {
				if (resultDto != null) {
					if (null != minFee) {
						if (caculate.compareTo(minFee) < 0) {
							resultDto.setCaculateFee(minFee);
						} else {
							resultDto.setCaculateFee(caculate);
						}
					} else {
						resultDto.setCaculateFee(caculate);
					}
				}
			}
			BigDecimal caculateFee = resultDto.getCaculateFee();
			if (null == caculateFee) {
				value = value.add(new BigDecimal(0));
			} else {
				value = value.add(caculateFee);
			}
			BeanCopier copy = BeanCopier.create(resultDto.getClass(), wsResultDto.getClass(), false);
			copy.copy(resultDto, wsResultDto, null);
			List<GuiResultDiscountDto> resultDicountDtoLst = resultDto.getDiscountPrograms();
			wsResultDto.setDiscountPrograms(null);
			// 复制折扣信息
			if (resultDicountDtoLst != null) {
				List<WsGuiResultDiscountDubboDto> wsResultDiscountDtoLst = new ArrayList<WsGuiResultDiscountDubboDto>();
				for (int j = 0; j < resultDicountDtoLst.size(); j++) {
					WsGuiResultDiscountDubboDto wsResultDiscountDto = new WsGuiResultDiscountDubboDto();
					BeanCopier copyer = BeanCopier.create(resultDicountDtoLst.get(j).getClass(), wsResultDiscountDto.getClass(), false);
					copyer.copy(resultDicountDtoLst.get(j), wsResultDiscountDto, null);
					wsResultDiscountDtoLst.add(wsResultDiscountDto);
				}
				wsResultDto.getDiscountPrograms().addAll(wsResultDiscountDtoLst);
			}
			// 复制偏线信息
			ResultOuterPriceCaccilateDto outerDto = resultDto.getResultOuterPriceCaccilateDto();
			if (outerDto != null) {
				WsResultOuterPriceCaccilateDubboDto wsOuterDto = new WsResultOuterPriceCaccilateDubboDto();
				BeanCopier copyer = BeanCopier.create(outerDto.getClass(), wsOuterDto.getClass(), false);
				copyer.copy(outerDto, wsOuterDto, null);
				wsResultDto.setResultOuterPriceCaccilateDto(wsOuterDto);
			}
			resResultBillCalDto.add(wsResultDto);
		}
		response.getBillPriceList().addAll(resResultBillCalDto);
		response.setTotalFee(value);
		return response;
	}

	/**
	 * <p>请求参数校验</p>
	 * 
	 * @author 316759
	 * @date 2017-2-28 下午5:52:10
	 * @param request
	 * @throws CommonException
	 * @see
	 */
	private void validateRequest(WsGuiQueryBillCalculateDubboRequest request) throws CommonException {

		if (StringUtils.isBlank(request.getOriginalOrgCode())) {
			LOGGER.error("参数OriginalOrgCode is null");
			throw new CommonException("OriginalOrgCode不能为空");
		}

		if (StringUtils.isBlank(request.getDestinationOrgCode())) {
			LOGGER.error("参数DestinationOrgCode不能为空");
			throw new CommonException("DestinationOrgCode不能为空");
		}

		if (StringUtils.isBlank(request.getProductCode())) {
			LOGGER.error("参数ProductCode不能为空");
			throw new CommonException("参数ProductCode不能为空");
		}

		if (request.getWeight() == null || request.getWeight().compareTo(new BigDecimal("0")) <= 0) {
			LOGGER.error("参数Weight不能为空且大于0");
			throw new CommonException("参数Weight不能为空且大于0");
		}

		if (request.getVolume() == null || request.getVolume().compareTo(new BigDecimal("0")) <= 0) {
			LOGGER.error("参数Volume不能为空且大于0");
			throw new CommonException("参数Volume不能为空且大于0");
		}

		if (request.getPriceEntities() == null || request.getPriceEntities().size() <= 0) {
			LOGGER.error("参数PriceEntities列表信息不能为空");
			throw new CommonException("参数PriceEntities列表信息不能为空");
		}

		// request.getPriceEntities() 1= null
		// 1.循环列表，判断code是否为空，
		// 2.如果是code保费BF 原始费用大于等于0
		if (request.getPriceEntities() != null && request.getPriceEntities().size() > 0) {
			for (int i = 0; i < request.getPriceEntities().size(); i++) {
				WsGuiQueryBillCalculateSubDubboDto subDto = request.getPriceEntities().get(i);
				if (StringUtils.isEmpty(subDto.getPriceEntityCode())) {
					LOGGER.error("费用类型代码PriceEntityCode不能为空！");
					throw new CommonException("费用类型代码PriceEntityCode不能为空");
				}
				if (PriceEntityConstants.PRICING_CODE_BF.equals(request.getPriceEntities().get(i).getPriceEntityCode())) {
					if (subDto.getOriginnalCost().compareTo(BigDecimal.ZERO) == 0 || subDto.getOriginnalCost().compareTo(BigDecimal.ZERO) == -1) {
						LOGGER.error("如果费用类型代码PriceEntityCode为BF时,如果原始费用类型OriginnalCost为传入的参数不为空，且大于0");
						throw new CommonException("如果费用类型代码PriceEntityCode为BF时,如果原始费用类型OriginnalCost为传入的参数不为空，且大于0");
					}
				}
				if (PriceEntityConstants.PRICING_CODE_BZ.equals(request.getPriceEntities().get(i).getPriceEntityCode())) {
					if (subDto.getWoodenVolume().compareTo(BigDecimal.ZERO) < 0) {
						LOGGER.error("如果费用类型代码为BZ，即包装费的时候，体积必须传入，且大于0");
						throw new CommonException("如果费用类型代码为BZ，即包装费的时候，体积必须传入，且大于0");
					}
				}
			}
		}

		// 1.receiveDate 如果为null的话 设置为当前时间参数
		if (null == request.getReceiveDate()) {
			LOGGER.error("参数ReceiveDate列表信息不能为空，请传当前时间");
			throw new CommonException("参数ReceiveDate列表信息不能为空，请传当前时间");
		}
	}

	public IGuiBillCaculateService getGuiBillCaculateService() {
		return guiBillCaculateService;
	}

	public void setGuiBillCaculateService(
			IGuiBillCaculateService guiBillCaculateService) {
		this.guiBillCaculateService = guiBillCaculateService;
	}
}

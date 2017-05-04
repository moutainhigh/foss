package com.deppon.foss.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.billcaculateservice.QueryGuiBillPriceResponse;
import com.deppon.esb.inteface.domain.billcaculateservice.WsGuiQueryBillCalculateDto;
import com.deppon.esb.inteface.domain.billcaculateservice.WsGuiQueryBillCalculateSubDto;
import com.deppon.esb.inteface.domain.billcaculateservice.WsGuiResultBillCalculateDto;
import com.deppon.esb.inteface.domain.billcaculateservice.WsGuiResultDiscountDto;
import com.deppon.esb.inteface.domain.billcaculateservice.WsResultOuterPriceCaccilateDto;
import com.deppon.foss.billcaculateservice.BillCaculateService;
import com.deppon.foss.billcaculateservice.CommonException;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultOuterPriceCaccilateDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * CC调用FOSS的价格计算
 * ClassName: BillCaculateServiceImpl <br/>
 * Function: 1.快递价格计算. 2.零担价格计算<br/>
 * date: 2014-7-22 下午1:42:50 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public class BillCaculateServiceImpl implements BillCaculateService{
	
	/**
     * 日志
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(BillCaculateServiceImpl.class);

	 private static String SUCCESS_YES = "Y";
	 private static String SUCCESS_NO = "N";
	 private BigDecimal defaultFirstWeight = BigDecimal.valueOf(0.5);
	/**
	 * 价格计算服务
	 */
	private IGuiBillCaculateService guiBillCaculateService;
	
	// 注册BigDecimal转换器，否则Bigdecimal转换报错
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		DateConverter dateConverter = new DateConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
		ConvertUtils.register(dateConverter, Date.class);
	}

	/**
	 * 快递价格查询
	 * @see com.deppon.foss.billcaculateservice.BillCaculateService#queryGuiExpressBillPrice(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.billcaculateservice.WsGuiQueryBillCalculateDto)
	 */
	@Override
	public QueryGuiBillPriceResponse queryGuiExpressBillPrice(
			Holder<ESBHeader> esbHeader,
			WsGuiQueryBillCalculateDto request)
			throws CommonException {
		QueryGuiBillPriceResponse response = null;
		String message = "";
		//参数验证
		validateRequest(esbHeader,request);
		//数据封装
		GuiQueryBillCalculateDto billCalculateDto = requestCopyProperties(request);
		List<GuiResultBillCalculateDto> guiResultBillCalculateLst = null;
		try{
			guiResultBillCalculateLst = guiBillCaculateService.queryGuiExpressBillPrice(billCalculateDto);
			guiResultBillCalculateLst=queryExpQS(billCalculateDto,guiResultBillCalculateLst);
		}catch (BusinessException e) {
			if(StringUtil.isNotEmpty(e.getErrorCode())){
				  message = e.getErrorCode();
				}
			if(StringUtil.isNotEmpty(e.getMessage())){
			   message =message+e.getMessage();
			}
			if (StringUtils.isNotEmpty(message)) {
				if (message.indexOf("没有找到运费!") >= 0) {
					LOGGER.error("没有找到运费", message);
					response = new QueryGuiBillPriceResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg(message);
					return response;
				} else if (message.indexOf("没有配置系统参数:") >= 0) {
					LOGGER.error("没有配置系统参数", message);
					response = new QueryGuiBillPriceResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg(message);
					return response;
				} else {
					LOGGER.error("价格计算异常", message);
					throw new CommonException("价格计算异常 =>" + message);
				}
			} else {
				LOGGER.error("价格计算异常", "价格计算异常");
				throw new CommonException("价格计算异常 =>" + "价格计算异常");
			}
		} catch (Exception ee) {
			message = ee.getMessage();
			if ("java.lang.reflect.UndeclaredThrowableException"
					.equals(message)) {
				LOGGER.error("精准大票货找不到对应价格,请配置", message);
				message = "精准大票货找不到对应价格,请配置";
				response = new QueryGuiBillPriceResponse();
				response.setSuccess(SUCCESS_NO);
				response.setMsg(message);
				return response;
			} else {
				LOGGER.error("价格计算异常", message);
				throw new CommonException("价格计算异常 =>" + message);
			}
		}
		try {
			response = responseCopyProperties(guiResultBillCalculateLst);
		} catch (IllegalAccessException e) {
			message = "返回参数封装异常";
			LOGGER.error("返回参数封装异常", e);
			response = new QueryGuiBillPriceResponse();
			response.setSuccess(SUCCESS_NO);
			response.setMsg(message);
			return response;
		} catch (InvocationTargetException e) {
			message = "返回参数封装异常";
			LOGGER.error("返回参数封装异常", e);
			response = new QueryGuiBillPriceResponse();
			response.setSuccess(SUCCESS_NO);
			response.setMsg(message);
			return response;
		}
		response.setSuccess(SUCCESS_YES);
		return response;
	}
	/**
	 *查询快递首重
	 * 
	 * Date:2015-04-27下午1:47:27
	 * @author 065340
	 */
	private List<GuiResultBillCalculateDto> queryExpQS(GuiQueryBillCalculateDto billCalculateDto,List<GuiResultBillCalculateDto> guiResultBillCalculateLst) throws CommonException{
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos2 = new ArrayList<GuiResultBillCalculateDto>();
		List<GuiQueryBillCalculateSubDto> reqBillCalculateSubDto = billCalculateDto.getPriceEntities();
		for(int i = 0; i < reqBillCalculateSubDto.size(); i++){
			if(null!=reqBillCalculateSubDto.get(i) && null!=reqBillCalculateSubDto.get(i).getPriceEntityCode()){
			   if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QS, reqBillCalculateSubDto.get(i).getPriceEntityCode())
			&& StringUtil.equals(WaybillConstants.RETURNBILLTYPE_ORIGINAL, reqBillCalculateSubDto.get(i).getSubType())){
					GuiQueryBillCalculateDto dto2 = new GuiQueryBillCalculateDto();
						try {
							PropertyUtils.copyProperties(dto2, billCalculateDto);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					List<GuiQueryBillCalculateSubDto>  priceEntities2 = new ArrayList<GuiQueryBillCalculateSubDto> ();
					for(GuiQueryBillCalculateSubDto d : billCalculateDto.getPriceEntities()){
						if(PriceEntityConstants.PRICING_CODE_FRT.equals(d.getPriceEntityCode())){
							GuiQueryBillCalculateSubDto d2 = new GuiQueryBillCalculateSubDto();
							try{
								PropertyUtils.copyProperties(d2, d);
							}catch(Exception e){
							}
							priceEntities2.add(d2);
						}
					}
					dto2.setPriceEntities(priceEntities2);
					dto2.setWeight(defaultFirstWeight);
					dto2.setVolume(BigDecimal.ZERO);
					dto2.setCustomerCode("");
					dto2.setIsSelfPickUp(FossConstants.NO);
					//根据DMANA-4938修改，标准快递和3.60特惠件两种产品的原件签单返回计费统一按照标准快递首重收费
					if(ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(dto2.getProductCode()) ||
							WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(dto2.getProductCode())){
						dto2.setProductCode(ExpWaybillConstants.PACKAGE);
					}
					guiResultBillCalculateDtos2 = guiBillCaculateService.queryGuiExpressBillPrice(dto2);
					//获取快递首重
					if(null!=guiResultBillCalculateDtos2 && guiResultBillCalculateDtos2.size()>0){
					    BigDecimal firstRateFee= guiResultBillCalculateDtos2.get(0).getCaculateFee();
						for(GuiResultBillCalculateDto resultDto :guiResultBillCalculateLst){
									if(null!=resultDto && null!=resultDto.getPriceEntryCode()){
										   if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QS, resultDto.getPriceEntryCode())
										&& StringUtil.equals(WaybillConstants.RETURNBILLTYPE_ORIGINAL, resultDto.getSubType())){
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
	 * 零担价格查询
	 * @see com.deppon.foss.billcaculateservice.BillCaculateService#queryGuiBillPrice(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.billcaculateservice.WsGuiQueryBillCalculateDto)
	 */
	@Override
	public QueryGuiBillPriceResponse queryGuiBillPrice(
			Holder<ESBHeader> esbHeader,
			WsGuiQueryBillCalculateDto request)
			throws CommonException {
		//参数验证
		validateRequest(esbHeader,request);
		QueryGuiBillPriceResponse response = null;
		String message = "";
		//数据封装
		GuiQueryBillCalculateDto billCalculateDto = requestCopyProperties(request);
		//计算结果集
		List<GuiResultBillCalculateDto> guiResultBillCalculateLst = null;
		try{
			//计算价格实现
			guiResultBillCalculateLst = guiBillCaculateService.queryGuiBillPrice(billCalculateDto);
		}catch (BusinessException e) {
			if(StringUtil.isNotEmpty(e.getErrorCode())){
				  message = e.getErrorCode();
				}
			if(StringUtil.isNotEmpty(e.getMessage())){
			   message =message+e.getMessage();
			}
			if (StringUtils.isNotEmpty(message)) {
				if (message.indexOf("没有找到运费!") >= 0) {
					LOGGER.error("没有找到运费", message);
					response = new QueryGuiBillPriceResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg(message);
					return response;
				} else if (message.indexOf("没有配置系统参数:") >= 0) {
					LOGGER.error("没有配置系统参数", message);
					response = new QueryGuiBillPriceResponse();
					response.setSuccess(SUCCESS_NO);
					response.setMsg(message);
					return response;
				} else {
					LOGGER.error("价格计算异常", message);
					throw new CommonException("价格计算异常 =>" + message);
				}
			} else {
				LOGGER.error("价格计算异常", "价格计算异常");
				throw new CommonException("价格计算异常 =>" + "价格计算异常");
			}
		} catch (Exception ee) {
			message = ee.getMessage();
			if ("java.lang.reflect.UndeclaredThrowableException"
					.equals(message)) {
				LOGGER.error("精准大票货找不到对应价格,请配置", message);
				message = "精准大票货找不到对应价格,请配置";
				response = new QueryGuiBillPriceResponse();
				response.setSuccess(SUCCESS_NO);
				response.setMsg(message);
				return response;
			} else {
				LOGGER.error("价格计算异常", message);
				throw new CommonException("价格计算异常 =>" + message);
			}
		}
		try {
			// 结果数据封装
			response = responseCopyProperties(guiResultBillCalculateLst);
		} catch (IllegalAccessException e) {
			message = "返回参数封装异常";
			LOGGER.error("返回参数封装异常", e);
			response = new QueryGuiBillPriceResponse();
			response.setSuccess(SUCCESS_NO);
			response.setMsg(message);
			return response;
		} catch (InvocationTargetException e) {
			message = "返回参数封装异常";
			LOGGER.error("返回参数封装异常", e);
			response = new QueryGuiBillPriceResponse();
			response.setSuccess(SUCCESS_NO);
			response.setMsg(message);
			return response;
		}
		response.setSuccess(SUCCESS_YES);
		return response;
	}
	
	/**
	 * 请求参数转换方法
	 * 
	 * Date:2014-7-22下午1:47:27
	 * @author 157229-zxy
	 * @param request
	 * @return
	 * @throws CommonException
	 * @since JDK 1.6FRT
	 */
	private GuiQueryBillCalculateDto requestCopyProperties(WsGuiQueryBillCalculateDto request) throws CommonException{
		GuiQueryBillCalculateDto billCalculateDto = new GuiQueryBillCalculateDto();
		List<WsGuiQueryBillCalculateSubDto> reqBillCalculateSubDto = request.getPriceEntities();
		try {
			BeanUtils.copyProperties(billCalculateDto, request);
			billCalculateDto.setReceiveDate(xmlGregorianCalendar2Date(request.getReceiveDate()));
			List<GuiQueryBillCalculateSubDto> billCalculateSubDto = new ArrayList<GuiQueryBillCalculateSubDto>();
			//复制计价条目
			for(int i = 0; i < reqBillCalculateSubDto.size(); i++){
				if(null!=reqBillCalculateSubDto.get(i) && null!=reqBillCalculateSubDto.get(i).getPriceEntityCode()){
				   if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, reqBillCalculateSubDto.get(i).getPriceEntityCode())){
					   GuiQueryBillCalculateSubDto subDto = new GuiQueryBillCalculateSubDto();
					   BeanUtils.copyProperties(subDto,reqBillCalculateSubDto.get(i));
					   billCalculateSubDto.add(subDto);
					   break;
				  }
				}
			}
			for(int i = 0; i < reqBillCalculateSubDto.size(); i++){
				if(null!=reqBillCalculateSubDto.get(i) && null!=reqBillCalculateSubDto.get(i).getPriceEntityCode()){
				   if(!StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, reqBillCalculateSubDto.get(i).getPriceEntityCode())){
					   GuiQueryBillCalculateSubDto subDto = new GuiQueryBillCalculateSubDto();
					   BeanUtils.copyProperties(subDto,reqBillCalculateSubDto.get(i));
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
	 * @author 200945-wutao
	 * 将XMLGregorianCalendar转换为Date
	 * @param date
	 * @return
	 * @Date 2014-08-25
	 */
	public static Date xmlGregorianCalendar2Date(XMLGregorianCalendar date) {
		if (date == null) {
			return null;
		} else {
			return date.toGregorianCalendar().getTime();
		}
	}
	/**
	 * 结果集转换成response
	 * 
	 * Date:2014-7-22下午1:48:10
	 * @author 157229-zxy
	 * @param guiResultBillCalculateLst
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @since JDK 1.6
	 */
	private QueryGuiBillPriceResponse responseCopyProperties(List<GuiResultBillCalculateDto> guiResultBillCalculateLst) throws IllegalAccessException, InvocationTargetException{
		QueryGuiBillPriceResponse response = new QueryGuiBillPriceResponse();
		BigDecimal value = new BigDecimal(0);
		List<WsGuiResultBillCalculateDto> resResultBillCalDto = new ArrayList<WsGuiResultBillCalculateDto>();
		for(int i = 0; i < guiResultBillCalculateLst.size(); i++){
			GuiResultBillCalculateDto resultDto = guiResultBillCalculateLst.get(i);
			WsGuiResultBillCalculateDto wsResultDto = new WsGuiResultBillCalculateDto();
			BigDecimal minFee = resultDto.getMinFee();
			BigDecimal caculate = resultDto.getCaculateFee();
			if(null != resultDto && caculate==null){
				if(null!=minFee){
				   resultDto.setCaculateFee(minFee);
				}else{
					resultDto.setCaculateFee(new BigDecimal("0"));
				}
			}else{
				if(resultDto!=null){
					if(null!=minFee){
						if(caculate.compareTo(minFee)<0){
							resultDto.setCaculateFee(minFee);
						}else{
							resultDto.setCaculateFee(caculate);
						}
					}else{
						    resultDto.setCaculateFee(caculate);
					}
				}
			}
			BigDecimal caculateFee = resultDto.getCaculateFee();
			if(null == caculateFee){
				value = value.add(new BigDecimal(0));
			}else{
				value = value.add(caculateFee);
			}
			BeanUtils.copyProperties(wsResultDto,resultDto);
			List<GuiResultDiscountDto> resultDicountDtoLst = resultDto.getDiscountPrograms();
			//复制折扣信息
			if(resultDicountDtoLst != null){
				List<WsGuiResultDiscountDto> wsResultDiscountDtoLst = new ArrayList<WsGuiResultDiscountDto>();
				for(int j = 0; j < resultDicountDtoLst.size(); j++){
					WsGuiResultDiscountDto wsResultDiscountDto= new WsGuiResultDiscountDto();
					BeanUtils.copyProperties(wsResultDiscountDto,resultDicountDtoLst.get(j));
					wsResultDiscountDtoLst.add(wsResultDiscountDto);
				}
				wsResultDto.getDiscountPrograms().addAll(wsResultDiscountDtoLst);
			}
			//复制偏线信息
			ResultOuterPriceCaccilateDto outerDto = resultDto.getResultOuterPriceCaccilateDto();
			if(outerDto != null){
				WsResultOuterPriceCaccilateDto wsOuterDto = new WsResultOuterPriceCaccilateDto();
				BeanUtils.copyProperties(wsOuterDto,outerDto);
				wsResultDto.setResultOuterPriceCaccilateDto(wsOuterDto);
			}
			resResultBillCalDto.add(wsResultDto);
		}
		response.getBillPriceList().addAll(resResultBillCalDto);
		response.setTotalFee(value);
		return response;
	}
	
	/**
	 * 请求参数校验
	 * validateRequest:. <br/>
	 * 
	 * Date:2014-7-22上午10:58:28
	 * @author 157229-zxy
	 * @param esbHeader
	 * @param request
	 * @throws CommonException 
	 * @since JDK 1.6
	 */
	private void validateRequest(Holder<ESBHeader> esbHeader,
			WsGuiQueryBillCalculateDto request) throws CommonException{
		//esbHeader验证
		if(esbHeader == null){
			LOGGER.error("esbHeader is null");
			throw new CommonException("esbHeader is null");
		}
		
		
		if(StringUtils.isBlank(request.getOriginalOrgCode())){
			LOGGER.error("参数OriginalOrgCode is null");
			throw new CommonException("OriginalOrgCode不能为空");
		}
		
		if(StringUtils.isBlank(request.getDestinationOrgCode())){
			LOGGER.error("参数DestinationOrgCode不能为空");
			throw new CommonException("DestinationOrgCode不能为空");
		}
		
		if(StringUtils.isBlank(request.getProductCode())){
			LOGGER.error("参数ProductCode不能为空");
			throw new CommonException("参数ProductCode不能为空");
		}
			
		if(request.getWeight() == null || request.getWeight().compareTo(new BigDecimal("0")) <= 0){
			LOGGER.error("参数Weight不能为空且大于0");
			throw new CommonException("参数Weight不能为空且大于0");
		}
		
		if(request.getVolume() == null || request.getVolume().compareTo(new BigDecimal("0")) <= 0){
			LOGGER.error("参数Volume不能为空且大于0");
			throw new CommonException("参数Volume不能为空且大于0");
		}
		
		if(request.getPriceEntities() == null || request.getPriceEntities().size() <= 0){
			LOGGER.error("参数PriceEntities列表信息不能为空");
			throw new CommonException("参数PriceEntities列表信息不能为空");
		}
		
		//request.getPriceEntities() 1= null  
		//1.循环列表，判断code是否为空，
		//2.如果是code保费BF  原始费用大于等于0
		if(request.getPriceEntities() != null && request.getPriceEntities().size()> 0){
			for(int i = 0;i<request.getPriceEntities().size();i++){
				WsGuiQueryBillCalculateSubDto subDto = request.getPriceEntities().get(i);
				if(StringUtils.isEmpty(subDto.getPriceEntityCode())){
					LOGGER.error("费用类型代码PriceEntityCode不能为空！");
					throw new CommonException("费用类型代码PriceEntityCode不能为空");
				}
				if(PriceEntityConstants.PRICING_CODE_BF.equals(request.getPriceEntities().get(i).getPriceEntityCode())){
					if(subDto.getOriginnalCost().compareTo(BigDecimal.ZERO)==0 || subDto.getOriginnalCost().compareTo(BigDecimal.ZERO)==-1){
						LOGGER.error("如果费用类型代码PriceEntityCode为BF时,如果原始费用类型OriginnalCost为传入的参数不为空，且大于0");
						throw new CommonException("如果费用类型代码PriceEntityCode为BF时,如果原始费用类型OriginnalCost为传入的参数不为空，且大于0");
					}
				}
				if(PriceEntityConstants.PRICING_CODE_BZ.equals(request.getPriceEntities().get(i).getPriceEntityCode())){
					if(subDto.getWoodenVolume().compareTo(BigDecimal.ZERO) < 0){
						LOGGER.error("如果费用类型代码为BZ，即包装费的时候，体积必须传入，且大于0");
						throw new CommonException("如果费用类型代码为BZ，即包装费的时候，体积必须传入，且大于0");
					}
				}
			}
		}
		
		//1.receiveDate 如果为null的话 设置为当前时间参数
		if(null == request.getReceiveDate()){
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

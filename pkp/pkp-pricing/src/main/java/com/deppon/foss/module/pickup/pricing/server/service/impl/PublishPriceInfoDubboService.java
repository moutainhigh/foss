package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceInfoDubboService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPriceDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ExpressPriceDubboDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.GeneralPriceDubboDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.LttRateDubboDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductAgingDubboDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.PublishPriceInfoDubboDto;
import com.deppon.foss.module.pickup.waybill.shared.request.QueryPublishPriceDubboRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.QueryPublishPriceDubboResponse;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询公布价
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:316759,date:2017-2-23 下午5:11:14,content:查询公布价</p>
 * @author 316759
 * @date 2017-2-23 下午5:11:14
 * @since
 * @version
 */
public class PublishPriceInfoDubboService implements IPublishPriceInfoDubboService {

	/**
	 * Logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(PublishPriceInfoDubboService.class);

	/** 客户信息Service接口 */
	@Autowired
	private IPublishPriceService publishPriceService;

	/** 产品 Service接口 */
	@Autowired
	private IProductService productService;

	/** 公布价Service接口 */
	@Autowired
	private IPublishPriceExpressService publishPriceExpressService;

	/**
	 * 查询公布价
	 * 
	 * @author 316759
	 * @date 2017-2-23 下午5:11:44
	 * @param esbHeader
	 * @param request
	 * @return
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceInfoDubboService#queryPublishPrice(javax.xml.ws.Holder,
	 *      com.deppon.foss.module.pickup.waybill.shared.request.QueryPublishPriceDubboRequest)
	 */
	@Override
	public QueryPublishPriceDubboResponse queryPublishPrice(QueryPublishPriceDubboRequest request) {
		String startCityCode = request.getDepartureCity();
		String destinationCityCode = request.getDestinationCity();
		try {
			//判断出发城市编码和到达城市编码是否为省，如果为省，就返回null
			if(publishPriceService.queryPublishPriceDetailisProvince(startCityCode, destinationCityCode)){
				return null;
			}
			List<PublicPriceDto> publicPriceDtos = publishPriceService.queryPublishPriceDetailByCity(startCityCode, destinationCityCode, new Date());
			List<PublishPriceExpressEntity> publishPriceExpressEntitys = publishPriceExpressService.queryPublishPriceByCityCode(startCityCode, destinationCityCode, new Date());

			QueryPublishPriceDubboResponse response = new QueryPublishPriceDubboResponse();

			// 快递价格
			if (CollectionUtils.isNotEmpty(publishPriceExpressEntitys)) {
				for (int i = 0; i < publishPriceExpressEntitys.size(); i++) {
					PublishPriceExpressEntity expressEntity = publishPriceExpressEntitys.get(i);
					PublishPriceInfoDubboDto info = new PublishPriceInfoDubboDto();
					info.setProductCode(expressEntity.getProductCode());
					info.setProductName(expressEntity.getProductName());
					// 快递价格
					ExpressPriceDubboDto expressPrice = new ExpressPriceDubboDto();
					expressPrice.setGroundPrice(expressEntity.getFirstWeight());
					expressPrice.setLowerGround(expressEntity.getWeightLowLimit());
					expressPrice.setLowerOfStage1(expressEntity.getWeightOffline1());
					expressPrice.setLowerOfStage2(expressEntity.getWeightOffline2());
					expressPrice.setRateOfStage1(expressEntity.getFeeRate1());
					expressPrice.setRateOfStage2(expressEntity.getFeeRate2());
					expressPrice.setUpperGround(expressEntity.getWeightHighLimit());
					expressPrice.setUpperOfStage1(expressEntity.getWeightOnline1());
					expressPrice.setUpperOfStage2(expressEntity.getWeightOnline2());

					info.setExpressRate(expressPrice);

					ProductAgingDubboDto value = new ProductAgingDubboDto();
					value.setDeliveryAddDays(expressEntity.getAddDay());
					value.setGoodTypeCode(expressEntity.getGoodsTypeCode());
					value.setGoodTypeName(expressEntity.getGoodsTypeName());
					value.setLongShort(expressEntity.getLongOrShort());
					value.setPromiseArriveTime(expressEntity.getArriveTime());
					value.setPromiseDeliveryTime(expressEntity.getDeliveryTime());
					value.setPromiseQuickTime(expressEntity.getMinTime());
					value.setPromiseSlowTime(expressEntity.getMaxTime());
					value.setUnitOfPromiseQuickTime(expressEntity.getMinTimeUnit());
					value.setUnitPromiseOfSlowTime(expressEntity.getMaxTimeUnit());
					info.setSpeed(value);
					response.getPublishPriceInfos().add(info);
				}
			}

			// 普通公布价
			if (CollectionUtils.isNotEmpty(publicPriceDtos)) {
				for (int i = 0; i < publicPriceDtos.size(); i++) {
					PublicPriceDto dto = publicPriceDtos.get(i);
					PublishPriceInfoDubboDto info = new PublishPriceInfoDubboDto();
					info.setProductCode(dto.getProductCode());
					info.setProductName(dto.getProductName());
					String productCode = dto.getProductCode();
					// 根据三级产品code 获取二级产品code
					ProductEntity productEntity = productService.getProductByCache(productCode, new Date());
					if (productEntity == null) {
						return null;
					}
					// 根据客户端传入的三级产品得到二级产品
					productCode = productEntity.getParentCode();

					// 如果是零担汽运，则查询分段价格
					if (productCode != null
							&& (StringUtil
									.equalsIgnoreCase(
											productCode,
											ProductEntityConstants.PRICING_PRODUCT_C2_C20002) || StringUtil
									.equalsIgnoreCase(
											productCode,
											ProductEntityConstants.PRICING_PRODUCT_C2_C20001))) {
						// 查询分段信息
						QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
						queryProductPriceDto.setOriginalOrgId(dto.getDeptRegionId());
						queryProductPriceDto.setDestinationId(dto.getArrvRegionId());
						queryProductPriceDto.setProductCode(productCode);
						queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING); // 价格定义
						queryProductPriceDto.setActive(FossConstants.ACTIVE);
						queryProductPriceDto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						queryProductPriceDto.setReceiveDate(new Date());
						queryProductPriceDto.setGoodsTypeCode("H00001");
						List<ResultPriceDto> list = publishPriceService.queryResultPrice(queryProductPriceDto);
						List<LttRateDubboDto> rateList = new ArrayList<LttRateDubboDto>();
						LttRateDubboDto[] arrays = null;
						if (list != null && list.size() > 0) {

							Map<String, LttRateDubboDto> map = new HashMap<String, LttRateDubboDto>();
							for (ResultPriceDto result : list) {
								String sectionId = result.getSectionId();
								String centralizePickup = result.getCentralizePickup();
								String caculateType = result.getCaculateType();
								LttRateDubboDto lttRate = null;
								GeneralPriceDubboDto valuePrice = null;
								if (map.containsKey(sectionId)) {
									lttRate = map.get(sectionId);
									valuePrice = lttRate.getGenerateRate();
								} else {
									lttRate = new LttRateDubboDto();
									valuePrice = new GeneralPriceDubboDto();
								}
								// 非上门接货
								// 由于不管是上门接货还是非上门接货，重量范围和体积费范围都是一样的，因此该段的起点都一样
								if ("N".equals(centralizePickup)) {
									if ("WEIGHT".equalsIgnoreCase(caculateType)) {
										// 设置重货价格（不上门接货）
										valuePrice.setHeavyRate(result.getFee());
										// 设置重量范围起点
										lttRate.setWeightStart(new BigDecimal(result.getCriticalValue()));
									} else {
										// 设置轻货价格（不上门接货）
										valuePrice.setLightRate(result.getFee());
										// 设置体积范围起点
										lttRate.setVolumeStart(new BigDecimal(result.getCriticalValue()));
									}
									// 设置最低一票（不上门接货）
									valuePrice.setLowestPrice(new BigDecimal(result.getMinFee()));
								} else {
									if ("WEIGHT".equalsIgnoreCase(caculateType)) {
										// 设置重货价格（上门接货）
										valuePrice.setReceiveHeavyRate(result.getFee());
										// 设置重量范围起点
										lttRate.setWeightStart(new BigDecimal(result.getCriticalValue()));
									} else {
										// 设置轻货价格（上门接货）
										valuePrice.setReceiveLightRate(result.getFee());
										// 设置体积范围起点
										lttRate.setVolumeStart(new BigDecimal(result.getCriticalValue()));
									}
									// 设置最低一票（上门接货）
									valuePrice.setReceiveLowestPrice(new BigDecimal(result.getMinFee()));
								}

								valuePrice.setDestinationArea(dto.getArrvRegionName());
								valuePrice.setOriginatingArea(dto.getDeptRegionName());
								lttRate.setGenerateRate(valuePrice);
								lttRate.setSectionId(sectionId);
								map.put(sectionId, lttRate);
							}

							// 遍历map，设置范围终点
							if (!map.isEmpty()) {// 如果map不为空
								arrays = new LttRateDubboDto[map.size()];
								Set<Map.Entry<String, LttRateDubboDto>> set = map.entrySet();
								for (Iterator<Map.Entry<String, LttRateDubboDto>> it = set.iterator(); it.hasNext();) {
									Map.Entry<String, LttRateDubboDto> entry = (Map.Entry<String, LttRateDubboDto>) it.next();
									int sectionId = Integer.valueOf(entry.getKey());
									if (sectionId > 0) {
										LttRateDubboDto lttRate = entry.getValue();
										LttRateDubboDto nextLttRate = map.get(sectionId + 1 + "");
										if (nextLttRate != null) {// 如果存在下一个阶段
											// 下一个分段的重量范围起点设置为本阶段重量范围的终点
											lttRate.setWeightEnd(nextLttRate.getWeightStart());
											// 下一个分段的体积范围起点设置为本阶段体积范围的终点
											lttRate.setVolumeEnd(nextLttRate.getVolumeStart());
										} else {
											lttRate.setWeightEnd(null);
											lttRate.setVolumeEnd(null);
										}
										arrays[sectionId - 1] = lttRate;
									}
								}
							}
						}
						if (arrays != null && arrays.length > 0) {
							for (LttRateDubboDto ltr : arrays) {
								rateList.add(ltr);
							}
							info.getLttRates().addAll(rateList);
						}

					}

					GeneralPriceDubboDto valuePrice = new GeneralPriceDubboDto();
					valuePrice.setReceiveHeavyRate(dto.getHeavyFeeRatePickUpYes());
					valuePrice.setReceiveLightRate(dto.getLightFeeRatePickUpYes());
					valuePrice.setHeavyRate(dto.getHeavyFeeRatePickUpNo());
					valuePrice.setLightRate(dto.getLightFeeRatePickUpNo());
					valuePrice.setLowestPrice(dto.getMinFeePickUpNo());
					valuePrice.setReceiveLowestPrice(dto.getMinFeePickUpYes());
					valuePrice.setDestinationArea(dto.getArrvRegionName());
					valuePrice.setOriginatingArea(dto.getDeptRegionName());

					info.setGenerateRate(valuePrice);

					ProductAgingDubboDto value = new ProductAgingDubboDto();
					value.setDeliveryAddDays(dto.getAddDay());
					value.setGoodTypeCode(dto.getGoodsTypeCode());
					value.setGoodTypeName(dto.getGoodsTypeName());
					value.setLongShort(dto.getLongOrShort());
					value.setPromiseArriveTime(dto.getArriveTime());
					value.setPromiseDeliveryTime(dto.getDeliveryTime());
					value.setPromiseQuickTime(dto.getMinTime());
					value.setPromiseSlowTime(dto.getMaxTime());
					value.setUnitOfPromiseQuickTime(dto.getMinTimeUnit());
					value.setUnitPromiseOfSlowTime(dto.getMaxTimeUnit());
					info.setSpeed(value);
					response.getPublishPriceInfos().add(info);
				}
			}
			return response;
		} catch (Exception e) {

			logger.info(startCityCode + ":" + destinationCityCode + ":"
					+ "CC官网调用价格时效接口" + e.getMessage() + "栈信息"
					+ ExceptionUtils.getFullStackTrace(e));

		}
		return null;
	}

	public IPublishPriceService getPublishPriceService() {
		return publishPriceService;
	}

	public void setPublishPriceService(IPublishPriceService publishPriceService) {
		this.publishPriceService = publishPriceService;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public IPublishPriceExpressService getPublishPriceExpressService() {
		return publishPriceExpressService;
	}

	public void setPublishPriceExpressService(IPublishPriceExpressService publishPriceExpressService) {
		this.publishPriceExpressService = publishPriceExpressService;
	}

}

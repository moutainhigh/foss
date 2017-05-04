package com.deppon.foss.module.pickup.pricing.server.calculateservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class BillCaculateOldService extends BillCaculateService{
	
	private static final Logger log = Logger.getLogger(BillCaculateOldService.class);
	
	@Override  
	public List<ProductPriceDto> searchProductPriceList(QueryBillCacilateDto queryBillCacilateDto) throws BillCaculateServiceException{
    		/**1.1 根据始发部门code 获取始发区域IDT_SRV_PRICE_PLAN
        	 * 1.2 根据达到部门code 获取到达区域ID
        	 * 2.1 根据始发区域ID,到达区域ID, 营业部收货日期, 是否接货查询计费规则和计价方式明细列表，以ProductPriceDto对象返回；
        	 */
		log.debug("FRT start calcuate>>"+new Date());
		
	
		// 数据检验
		PriceUtil.checkQueryBillCacilateDtoDate(queryBillCacilateDto);
		//业务时间 
		Date discountReceiveDate = queryBillCacilateDto.getReceiveDate();
		//如果客户编码存在, 查询合同优惠信息,取出对应的价格版本日期作为当前查询价格信息。 
		//super.checkPreferentiaTime(queryBillCacilateDto);
		// 如果当前货物为NULL,设置货物编码为通用状态
		if (StringUtil.isEmpty(queryBillCacilateDto.getGoodsCode())) {
			queryBillCacilateDto.setGoodsCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
		}
		//获得当前传入的产品、始发部门、到达部门、业务日期、币种、货物类别，是否接货
		String productCode = queryBillCacilateDto.getProductCode();
		String originalOrgCode = queryBillCacilateDto.getOriginalOrgCode();
		String destinationOrgCode = queryBillCacilateDto.getDestinationOrgCode();
		Date receiveDate = queryBillCacilateDto.getReceiveDate();
		String currencyCode = queryBillCacilateDto.getCurrencyCdoe();
		String flightShift = queryBillCacilateDto.getFlightShift();
		String goodsCode = queryBillCacilateDto.getGoodsCode();
		String combBillTypeCode = queryBillCacilateDto.getCombBillTypeCode();				//zxy 20140507 MANA-1253 新增
		String isReceiveGoods = queryBillCacilateDto.getIsReceiveGoods();
		//默认是否接货为否
		if (StringUtil.isEmpty(isReceiveGoods)) {
			isReceiveGoods = FossConstants.NO;
		}
		//重货
		BigDecimal weight = queryBillCacilateDto.getWeight();
		//轻货
		BigDecimal volume = queryBillCacilateDto.getVolume();
		//查询出发区域ID
		String originalId = queryBillCacilateDto.getDeptRegionId();
				originalId = super.getRegionService().findBGRegionOrgByDeptNo(originalOrgCode, queryBillCacilateDto.getReceiveDate(), null,
						PricingConstants.PRICING_REGION);
		// 查询目的地区域ID
		String destinationId = queryBillCacilateDto.getArrvRegionId();
		List<String>   destinationIds = super.getRegionArriveService().findBGRegionOrgByDeptNo(destinationOrgCode, queryBillCacilateDto.getReceiveDate(), null,
						PricingConstants.ARRIVE_REGION);
		if(CollectionUtils.isEmpty(destinationIds) || StringUtil.isEmpty(originalId)){
			return null;
		}
		//运费查询Bean
		QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
		if (null == currencyCode) {
			currencyCode = FossConstants.CURRENCY_CODE_RMB;
		}
		ProductEntity productEntity = super.getProductService().getProductByCache(productCode, discountReceiveDate);
		if (productEntity == null) {
			return null;
		}
		
		GoodsTypeEntity goodsTypeEntity = super.getGoodsTypeService().getGoodsTypeByCache(goodsCode, receiveDate);
		
		//根据客户端传入的三级产品得到二级产品
		productCode = productEntity.getParentCode();
		queryProductPriceDto.setProductCode(productCode);
		//设置货币、始发区域、到达区域、航班类别、货物、是否接货、计费规则类型、状态
		queryProductPriceDto.setCurrencyCode(currencyCode);
		queryProductPriceDto.setOriginalOrgId(originalId);
		queryProductPriceDto.setDestinationId(destinationId);
		queryProductPriceDto.setFlightShift(flightShift);
		queryProductPriceDto.setGoodsTypeCode(goodsCode);
		queryProductPriceDto.setReceiveDate(receiveDate);
		queryProductPriceDto.setIsReceiveGoods(isReceiveGoods);
		queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING);//价格定义 
		queryProductPriceDto.setActive(FossConstants.ACTIVE); 
		PriceEntity priceEntity = super.getPriceEntryService().getPriceEntryByCache(PriceEntityConstants.PRICING_CODE_FRT, discountReceiveDate);
		if (priceEntity == null) {
			return null;
		}
		
	    //直达区域
		List<PriceRegionBigGoodsArriveEntity>  arriveEntityZds = new ArrayList<PriceRegionBigGoodsArriveEntity>();
		//中转区域
		List<PriceRegionBigGoodsArriveEntity>  arriveEntityZzs = new ArrayList<PriceRegionBigGoodsArriveEntity>();
		// 根据三级产品查询计算费用
		List<ResultProductPriceDto> resultList=null;
		for(String destId:destinationIds){
			//大票到达区域信息
			PriceRegionBigGoodsArriveEntity entity= super.getRegionArriveService().searchRegionByID(destId);
			if(null!=entity){
				if(PricingConstants.BIG_SEND_DIRECTLY.equals(entity.getTranSportType())){
					arriveEntityZds.add(entity);
				}
				if(PricingConstants.BIG_MIDDLE_TRANSIT.equals(entity.getTranSportType())){
					arriveEntityZzs.add(entity);
				}
			}
		}
		
		//直发价格
		for(PriceRegionBigGoodsArriveEntity entity:arriveEntityZds){
			queryProductPriceDto.setDestinationId(entity.getId());
			resultList = super.getPriceValuationService().queryPriceValuationByCalculaCondition(queryProductPriceDto);
			if(CollectionUtils.isNotEmpty(resultList)){
				break;
			}
		}
		//中转价格
		if(CollectionUtils.isEmpty(resultList)){
			for(PriceRegionBigGoodsArriveEntity entity:arriveEntityZzs){
				queryProductPriceDto.setDestinationId(entity.getId());
				resultList = super.getPriceValuationService().queryPriceValuationByCalculaCondition(queryProductPriceDto);
				if(CollectionUtils.isNotEmpty(resultList)){
					break;
				}
			}
		}
		
		if(CollectionUtils.isEmpty(resultList)){
			return null;
		}
		List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
		if (StringUtil.equalsIgnoreCase(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
			//zxy 20140520 DEFECT-2913 MANA-1253 start 新增:如果未配置单独开单的方案，则取合大票
			if((resultList == null || resultList.size() <= 0) && ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(combBillTypeCode)){
				queryProductPriceDto.setCombBillTypeCode(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
				resultList = super.getPriceValuationService().queryPriceValuationByCalculaCondition(queryProductPriceDto);
				queryProductPriceDto.setCombBillTypeCode(combBillTypeCode);	//还原参数
			}
			//zxy 20140520 DEFECT-2913 MANA-1253 end 新增:如果未配置单独开单的方案，则取合大票
			// 计算空运的价格
			caculateresultList = PriceUtil.calculateAirCostServices(weight, volume, resultList, receiveDate,
					productEntity, goodsTypeEntity, priceEntity);
		} else {
			// 其他运输方式价格
			caculateresultList = PriceUtil.bigGoodscalculateCostServices(weight, volume, resultList, receiveDate,
					productEntity, goodsTypeEntity, priceEntity);
		}
		//如果当前是算月结客户 
		/*if(StringUtils.equals(FossConstants.YES,queryBillCacilateDto.getIsMonthlyDate())){
			// 根据三级产品查询计算费用
			queryProductPriceDto.setReceiveDate(discountReceiveDate);
			List<ResultProductPriceDto> monthlyResultList = super.getPriceValuationService().queryPriceValuationByCalculaCondition(queryProductPriceDto);
			if (CollectionUtils.isEmpty(caculateresultList)) {
				if (StringUtil.equalsIgnoreCase(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
					//zxy 20140520 DEFECT-2913 MANA-1253 start 新增:如果未配置单独开单的方案，则取合大票
					if((monthlyResultList == null || monthlyResultList.size() <= 0) && ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(combBillTypeCode)){
						queryProductPriceDto.setCombBillTypeCode(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
						monthlyResultList = super.getPriceValuationService().queryPriceValuationByCalculaCondition(queryProductPriceDto);
						queryProductPriceDto.setCombBillTypeCode(combBillTypeCode);	//还原参数
					}
					//zxy 20140520 DEFECT-2913 MANA-1253 end 新增:如果未配置单独开单的方案，则取合大票
					// 计算空运的价格
					caculateresultList = PriceUtil.calculateAirCostServices(weight, volume, monthlyResultList, discountReceiveDate,
							productEntity, goodsTypeEntity, priceEntity);
				} else {
					// 其他运输方式价格
					caculateresultList = PriceUtil.calculateCostServices(weight, volume, monthlyResultList, discountReceiveDate,
							productEntity, goodsTypeEntity, priceEntity);
				}
			}
		}*/
		
		//只有在没有找到价格的情况下才走这个方法   MANA-1242：偏线更改读取价格版本规则更新
		//@author 张兴旺  2014-5-13 08:59:57
		if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(queryBillCacilateDto.getProductCode())
									&& CollectionUtils.isEmpty(caculateresultList)){
			//重新赋予新值为当前时间以便得到新的计算方式
			queryBillCacilateDto.setReceiveDate(new Date());
			//时间重新赋值
			discountReceiveDate = queryBillCacilateDto.getReceiveDate();
			//重新计算价格
			caculateresultList = super.reCalateTranferForOuterprice(queryBillCacilateDto);
		}
		
		if (CollectionUtils.isEmpty(caculateresultList)) {
			return null;
		}else{
			BigDecimal caculateFee = caculateresultList.get(0).getCaculateFee();
			if(caculateFee != null && BigDecimal.ZERO.compareTo(caculateFee)==0){
				throw new BillCaculateServiceException("精准大票运费不能为0","精准大票运费不能为0");
			}
		}
		
		//是否经济自提件赋值最低一票 
		//super.assignEconomyMentMinFee(queryBillCacilateDto,caculateresultList,discountReceiveDate);
		//如果到达部门不为空、则在价格计算完成后的基础上接着计算折扣、否则不计费算折扣、直接返回价格
		/*if (StringUtil.isNotBlank(destinationOrgCode)) {
			*//**
			 * 计算折扣
			 *//*
			List<ProductPriceDto> list = doFRTDiscount(originalOrgCode, destinationOrgCode, discountReceiveDate, weight,
					volume, originalId, destinationId, caculateresultList, queryBillCacilateDto);
			super.calateOuterPrice(partialLineCode,int_productCode,queryBillCacilateDto,discountReceiveDate,caculateresultList);
			log.debug("FRT end calcuate>>"+new Date());
			return list;
		} else {
			super.calateOuterPrice(partialLineCode,int_productCode,queryBillCacilateDto,discountReceiveDate,caculateresultList);
			return caculateresultList;
		}*/
		
		return caculateresultList;
	}


	
}

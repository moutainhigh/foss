package com.deppon.foss.module.pickup.pricing.server.calculateservice.service;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 赵一清 on 2016/7/12.
 * 精准包裹计算逻辑
 */
public class EcGoodsBillCalculateService extends BillCaculateService {
    private static final Logger log = Logger.getLogger(EcGoodsBillCalculateService.class);

    /**
     * @param queryBillCacilateDto 提供GUI客户端开单服务-查询产品价格计算的DTO，如果productCode为”PCP“则实现这个方法
     * @return List<ProductPriceDto>
     * @throws BillCaculateServiceException
     */
    @Override
    public List<ProductPriceDto> searchProductPriceList(QueryBillCacilateDto queryBillCacilateDto) throws BillCaculateServiceException {
        //控制台输出开始时间
        log.info("精准电商运费计算开始>>" + new Date());

        // 数据检验
        PriceUtil.checkQueryBillCacilateDtoDate(queryBillCacilateDto);

        if (StringUtil.isEmpty(queryBillCacilateDto.getGoodsCode())) {
            queryBillCacilateDto.setGoodsCode(PricingConstants.GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
        }
        //获得当前传入的产品编码、始发部门编码、到达部门编码、业务日期、币种编码、航班号、货物编码、是否接货
        String productCode = queryBillCacilateDto.getProductCode();
        String originalOrgCode = queryBillCacilateDto.getOriginalOrgCode();
        String destinationOrgCode = queryBillCacilateDto.getDestinationOrgCode();
        Date receiveDate = queryBillCacilateDto.getReceiveDate();
        String currencyCode = queryBillCacilateDto.getCurrencyCdoe();
        String flightShift = queryBillCacilateDto.getFlightShift();
        String goodsCode = queryBillCacilateDto.getGoodsCode();
        //货物重量（BigDecimal格式参与计算）
        BigDecimal weight = queryBillCacilateDto.getWeight();
        //货物体积（BigDecimal格式参与计算）
        BigDecimal volume = queryBillCacilateDto.getVolume();

        if (StringUtil.isEmpty(currencyCode)) {
            currencyCode = FossConstants.CURRENCY_CODE_RMB;
        }

        /**
         * Created by 赵一清 on 2016/7/15
         * 出发区域ID和到达区域ID(根据Code调用方法获取暂时先取固定值)
         *
         * 根据出发部门的code去查PKP.T_SRV_PRICE_REGION_ORG_EC表的INCLUDE_ORG_CODE字段，能查到就获取T_SRV_PRICE_REGION_ID
         * 这个获取到的ID就是出发区域ID
         * 如果获取不到就去查综合BSE.T_BAS_ORG表的CODE字段，获取其COUNTRY_CODE和CITY_CODE字段
         * 再根据获得的两个字段去PKP.T_SRV_PRICE_REGION_EC表中查找COUNTRY_CODE，有就获取ID，没有就查找CITY_CODE字段对应的值，获取其ID，还没有就抛异常
         * 到达区域同理。
         */

        String originalId = this.getRegionService().findEcGoodsRegionOrgByDeptNo
                (originalOrgCode, receiveDate, null, PricingConstants.PRICING_REGION);
        String destinationId = this.getRegionArriveService().findEcGoodsRegionOrgByDeptNo
                (destinationOrgCode, receiveDate, null, PricingConstants.ARRIVE_REGION);

        if (StringUtil.isEmpty(destinationId) || StringUtil.isEmpty(originalId)) {
            return null;
        }
        //获取产品实体的Entity
        ProductEntity productEntity = this.getProductService().getProductByCache(productCode, receiveDate);
        //获取货物类型实体的Entity
        GoodsTypeEntity goodsTypeEntity = this.getGoodsTypeService().getGoodsTypeByCache(goodsCode, receiveDate);
        //获取价格实体类的Entity
        PriceEntity priceEntity = this.getPriceEntryService().getPriceEntryByCache(PricingConstants.PriceEntityConstants.PRICING_CODE_FRT, receiveDate);

        if (productEntity == null || goodsTypeEntity == null || priceEntity == null) {
            return null;
        }

        //得到二级产品Code
        productCode = productEntity.getParentCode();
        //建立运费查询的Bean，根据这个Bean来查询到计价条目
        //设置产品Code、货币、始发区域、到达区域、航班类别、货物、日期、是否接货、计费规则类型、状态等参数
        QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
        queryProductPriceDto.setProductCode(productCode);
        queryProductPriceDto.setCurrencyCode(currencyCode);
        queryProductPriceDto.setOriginalOrgId(originalId);
        queryProductPriceDto.setDestinationId(destinationId);
        queryProductPriceDto.setFlightShift(flightShift);
        queryProductPriceDto.setGoodsTypeCode(goodsCode);
        queryProductPriceDto.setReceiveDate(receiveDate);
        String customerCode = queryBillCacilateDto.getCustomerCode();
        //价格定义
        queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING);
        queryProductPriceDto.setActive(FossConstants.ACTIVE);


        //是否有重泡比
        boolean isBubbleRate = false;
        //重量体积与重量相同，传入此标记，将计价方式转换为体积
        boolean isVolumeWeightEqual = false;

        // 产品查询计算费用,调用sql语句查询计算价格的详情

        List<ResultProductPriceDto> resultList = this.getPriceValuationService().queryPriceValuationByCalculaCondition(queryProductPriceDto);
        if (CollectionUtils.isEmpty(resultList)) {
            return null;
        } else {
            //如果不为空，就查询是否有重泡比

            BigDecimal volumeWeight = validateWeightBubbleRateEcGoods(customerCode, receiveDate, productCode, volume, originalOrgCode);

            //不等于空的情况下和重量进行比较，哪个大取哪个，二者相同，取体积重量，计费类型为体积计费
            if (volumeWeight != null) {
                isBubbleRate = true;
                if (volumeWeight.compareTo(weight) >= 0) {
                    weight = volumeWeight;
                    isVolumeWeightEqual = true;
                }
            }


        }

        //价格计算
        List<ProductPriceDto> calculateResultList = PriceUtil.ecGoodsCalculateCostServices(weight, volume, resultList,productEntity,goodsTypeEntity,
                 priceEntity, isBubbleRate, isVolumeWeightEqual);

        //非空判断
        if (CollectionUtils.isEmpty(calculateResultList)) {
            throw new BillCaculateServiceException("精准电商找不到运费", "精准电商找不到运费，请配置");
        } else {
            //遍历List，设置最低一票为0，用于打完折后和比较最低一票
            for(ProductPriceDto tempDto:calculateResultList){
                tempDto.setMinFee(BigDecimal.ZERO);
            }
            BigDecimal calculateFee = calculateResultList.get(0).getCaculateFee();
            if (calculateFee != null && BigDecimal.ZERO.compareTo(calculateFee) == 0) {
                throw new BillCaculateServiceException("精准包裹运费不能为0", "精准包裹运费不能为0");
            }
            //=======================优化内容：合伙人无运费折扣/LianHe/2016-11-24/start=====================
            List<ProductPriceDto> list = null;
            if (FossConstants.YES.equals(queryBillCacilateDto.getPartnerBillingLogo())) {
				//合伙人无运费折扣，不走打折逻辑
            	list = calculateResultList;
			}else {
				//非合伙人时，走打折逻辑，进入此方法
				list = this.getProductPriceDtos(queryBillCacilateDto, receiveDate, productCode, originalOrgCode, destinationOrgCode, weight, volume, originalId, destinationId, calculateResultList);
			}
            //=======================优化内容：合伙人无运费折扣/LianHe/2016-11-24/end=====================
            log.info("精准电商运费计算结束>>" + new Date());
            return list;
        }
    }


}

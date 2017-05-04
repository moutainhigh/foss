package com.deppon.foss.module.pickup.pricing.server.service.impl;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.server.cache.ProductCacheDeal;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by 343617 on 2017/3/20.
 */
public class ProductDubboService implements IProductService {

    private static final Logger log = Logger.getLogger(ProductDubboService.class);

    /**
     * 产品数据管理服务
     */
    @Autowired
    private IProductDao productDao;

    /**
     * 产品缓存处理
     */
    @Autowired
    private ProductCacheDeal productCacheDeal;


    @Override
    public List<ProductEntity> findExternalProductByCondition(ProductDto condtion, Date billDate) {
        condtion.setActive(FossConstants.ACTIVE);//对于外部只查询有效激活的产品信息
        if (billDate == null) {
            billDate = new Date();
        }
        return productDao.findExternalProductByConditionForDubbo(condtion, billDate);
    }

    @Override
    public List<String> getAllLevels3CargoProductCode() {
        List<String> productCodeList = new ArrayList<String>();
        //物流一级汽运
        productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
        //物流一级空运
        productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
        return productDao.getAllLevels3ProductCodeForDubbo(productCodeList);
    }

    @Override
    public List<String> getAllLevels3ExpressProductCode() {
        List<String> productCodeList = new ArrayList<String>();
        //快递一级汽运
        productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
        return productDao.getAllLevels3ProductCodeForDubbo(productCodeList);
    }

    @Override
    public ProductEntity getLevel3ProductInfo(String productCode) {
        Map<Object, Object> maps = new HashMap<Object, Object>();
        maps.put("active", FossConstants.YES);
        maps.put("productCode", productCode);
        maps.put("levels", NumberConstants.NUMBER_3);
        return productDao.getLevel3ProductInfoForDubbo(maps);
    }

    @Override
    public ProductEntity getProductByCache(String productCode, Date billDate) {
        if (StringUtils.isEmpty(productCode)) {
            return null;
        }
        if (billDate == null) {
            billDate = new Date();
        }
        //客户端不读缓存
        if (SqlUtil.loadCache) {
            try {
                com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity productEntity = productCacheDeal.getProductEntityByCache(productCode, billDate);
                ProductEntity productDubboEntity = new ProductEntity();
                if (productEntity != null) {
                    BeanUtils.copyProperties(productEntity, productDubboEntity);
                    return productDubboEntity;
                }
            } catch (Exception e) {
                log.info("无法从产品缓存中获取数据", e);
            }
        }
        return productDao.getProductByCacheForDubbo(productCode, billDate);
    }

    @Override
    public ProductEntity getProductLele(String productCode, Date billDate, int n) {
        if (StringUtils.isEmpty(productCode)) {
            return null;
        }
        if (n > NumberConstants.NUMBER_3 || n < 1) {
            return null;
        }
        if (billDate == null) {
            billDate = new Date();
        }
        ProductEntity productEntity = null;

        for (int i = 0; i <= NumberConstants.NUMBER_3 - n; i++) {
            productEntity = productDao.getProductByCacheForDubbo(productCode, billDate);
            if (productEntity != null) {
                if (productEntity.getLevels() == n) {
                    return productEntity;
                } else {
                    productCode = productEntity.getParentCode();
                }
            }
        }
        return productEntity;
    }

    @Override
    public boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime) {
        if (StringUtils.isEmpty(productCode)) {
            return false;
        }
        return productDao.onlineDetermineIsExpressByProductCode(productCode, billTime);
    }

    @Override
    public List<ProductEntity> queryLevel3ProductInfo() {
        ProductDto condtion = new ProductDto();
        condtion.setActive(FossConstants.ACTIVE);
        condtion.setLevels(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
        condtion.setBillDate(new Date());
        return productDao.findProductByConditionForDubbo(condtion);
    }
}

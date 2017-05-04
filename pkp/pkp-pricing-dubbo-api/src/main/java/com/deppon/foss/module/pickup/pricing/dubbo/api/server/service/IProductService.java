package com.deppon.foss.module.pickup.pricing.dubbo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductDto;

import java.util.Date;
import java.util.List;

/**
 * Created by 343617 on 2017/3/20.
 */
public interface IProductService extends IService {

    //查询有效产品-对外接口
    List<ProductEntity> findExternalProductByCondition(ProductDto condtion, Date billDate);

    //获得所有对应零担所有产品类型
    List<String> getAllLevels3CargoProductCode();

    //获得所有对应快递所有产品类型
    List<String> getAllLevels3ExpressProductCode();

    //根据产品编码查询所有三级产品
    ProductEntity getLevel3ProductInfo(String productCode);

    //根据产品编码与营业日期来筛选产品
    ProductEntity getProductByCache(String productCode , Date billDate);

    //按级别查询产品
    ProductEntity getProductLele(String productCode, Date billDate, int n);

    //判断是否快递
    boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime);

    //查询所有有效的三级产品
    List<ProductEntity> queryLevel3ProductInfo();
}

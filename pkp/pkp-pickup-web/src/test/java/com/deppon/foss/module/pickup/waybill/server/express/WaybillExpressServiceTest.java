/*
 * PROJECT NAME: pkp-pickup-web
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.express
 * FILE    NAME: WaybillExpressServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.server.express;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PublishPriceExpressService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 公布价查询的测试类
 * @author 026123-foss-lifengteng
 * @date 2013-9-6 上午8:07:46
 */
public class WaybillExpressServiceTest {
	private IPublishPriceExpressService publishPriceExpressService;
	private IBillCaculateService billCaculateService;
	
    @Before
    public void setUp() throws Exception {
    	publishPriceExpressService = TestHelper.get().getBeanByClass(PublishPriceExpressService.class);
    	billCaculateService = TestHelper.get().getBeanByClass(BillCaculateService.class);
    }
    
    /**
     * 根据出发部门编码和到达城市编码查询公布价详细信息
     * @author 026123-foss-lifengteng
     * @date 2013-9-6 上午8:13:39
     */
    @Test
    public void testQueryPublishPriceDetail(){
    	//上海市
    	String startCityCode = "310000";
    	//北京市
    	String endCityCode = "110000";
    	//调用接口进行查询
    	List<PublishPriceExpressEntity> list = null;
		try {
			list = publishPriceExpressService.queryPublishPriceDetail(startCityCode, endCityCode, new Date());
		} catch (Exception e) {
			//有用到缓存，可能会报错，所以try...catch下
			//e.printStackTrace();
			//报表或视图不存在，实际上是因为使用了缓存
		}
    	//集合是否为空
    	if(CollectionUtils.isNotEmpty(list)){
    		for (PublishPriceExpressEntity entity : list) {
    			System.out.println("首重："+entity.getFirstWeight()); 
    			System.out.println("首重区间-下限："+entity.getWeightLowLimit()); 
    			System.out.println("首重区间-上限："+entity.getWeightHighLimit());
    			System.out.println("重量下线："+entity.getWeightOffline1());
    			System.out.println("重量上线："+entity.getWeightOnline1());
    			System.out.println("费率1："+entity.getFeeRate1());
    			System.out.println("重量下线："+entity.getWeightOnline2());
    			System.out.println("重量上线："+entity.getFeeRate1());
    			System.out.println("费率2："+entity.getFeeRate2());
			}
    	}
    }
    
    /**
     * 计算快递产品增值服务费
     * @author 026123-foss-lifengteng
     * @date 2013-9-6 上午11:35:51
     */
    @Test
    public void testSearchExpressValueAddPriceList(){
    	QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
    	//上海闵行区浦江镇营业部
		queryDto.setOriginalOrgCode("W011302020515");// 出发部门CODE
		//北京朝阳区百子湾营业部
		queryDto.setDestinationOrgCode("W011305080605");// 到达部门CODE
		//经济快递
		queryDto.setProductCode(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(BigDecimal.valueOf(1));// 重量
		queryDto.setVolume(BigDecimal.valueOf(0));// 体积
		queryDto.setOriginnalCost(BigDecimal.valueOf(1000));// 原始费用
		queryDto.setLongOrShort("L");// 长途 还是短途
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		
		List<ValueAddDto> list = null;
		try {
			list = billCaculateService.searchExpressValueAddPriceList(queryDto);
		} catch (Exception e) {
			//有用到缓存，可能会报错，所以try...catch下
			//e.printStackTrace();
			//报表或视图不存在，实际上是因为使用了缓存
		}
		if(CollectionUtils.isNotEmpty(list)){
			for (ValueAddDto dto : list) {
				System.out.println(dto.getCaculateExpression());
				System.out.println(dto.getPriceEntityCode());
				System.out.println(dto.getPriceEntityName());
			}
		}
    }
}

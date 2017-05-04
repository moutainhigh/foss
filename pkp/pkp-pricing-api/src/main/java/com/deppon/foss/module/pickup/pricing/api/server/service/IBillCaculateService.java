/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IBillCaculateService.java
 * 
 * FILE NAME        	: IBillCaculateService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountResultDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectiveExpressPlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;

/**
 * 
 * FOSS 开单计算总接口
 * 涵盖时效、运费、运费折扣、增值服务费、增值优惠
 * @author DP-Foss-YueHongJie
 * @date 2013-4-16 下午3:27:42
 * @version 1.0
 */
public interface IBillCaculateService extends IService {
    	 /**
    	  * 
    	  * <p>查询产品时效</p> 
    	  * @author DP-Foss-YueHongJie
    	  * @date 2013-4-16 下午3:28:28
    	  * @param originalOrgCode
    	  * @param destinationOrgCode
    	  * @param productCode
    	  * @param billDate
    	  * @return
    	  * @see
    	  */
	 List<EffectivePlanDto> searchEffectivePlanDetailList(String originalOrgCode, 
			String destinationOrgCode, String productCode,Date billDate);
	
	/**
	 * <p>计算运费</p> 
	 * @author DP-Foss-YueHongJie
	 * @date  2012-11-12 
	 * @param  queryDto  输入产品价格计算条件dto
	 * @return ProductPriceDto 输出产品价格计算后的dto
	 * @see
	 */
	 List<ProductPriceDto> searchProductPriceList(QueryBillCacilateDto queryDto);
	
	 /**
	  * 获取整车的保费相关信息
	  * @param queryBillCacilateValueAddDto
	  * @return
	  */
	 public ValueAddDto getProductPriceDtoOfWVHAndBF(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);

	 /**
	 * <p>计算增值服务费</p> 
	 * @author zhangdongping
	 * @date 2012-10-29 下午3:41:05
	 * @param queryDto   输入产品价格增值服务计算前条件dto
	 * @return ValueAddDto 输出产品价格增值服务计算后的dto
	 * @see
	 */
	 List<ValueAddDto>  searchValueAddPriceList(QueryBillCacilateValueAddDto queryDto);
	
	/**
	 * 运费折扣计算
	 * @author zhangdongping
	 * @date 2012-12-25 下午3:32:38
	 * @param originalOrgCode
	 * @param destinationOrgCode
	 * @param receiveDate
	 * @param weight
	 * @param volume
	 * @param originalId
	 * @param destinationId
	 * @param caculateresultList
	 * @return
	 * @see
	 */
	 List<ProductPriceDto> doFRTDiscount(String originalOrgCode,
			String destinationOrgCode, Date receiveDate, BigDecimal weight,
			BigDecimal volume, String originalId, String destinationId,
			List<ProductPriceDto> caculateresultList,QueryBillCacilateDto queryBillCacilateDto);
	
	
	/**
	 * 计算增值服务折扣
	 * @author zhangdongping
	 * @date 2012-12-25 下午3:27:04
	 * @param originalOrgCode
	 * @param destinationOrgCode
	 * @param receiveDate
	 * @param originalId
	 * @param destinationId
	 * @param weight
	 * @param volume
	 * @param resultList
	 * @see
	 */
	 List<ValueAddDto> doValueAddDiscount(String originalOrgCode,
			String destinationOrgCode, Date receiveDate, String originalId,
			String destinationId, BigDecimal weight, BigDecimal volume,
			List<ValueAddDto> resultList, QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
	 
	 
	 /**
    	  * 
    	  * <p>查询快递产品时效</p> 
    	  * @author DP-Foss-zdp
    	  * @date 2013-726 下午3:28:28
    	  * @param originalOrgCode
    	  * @param destinationOrgCode
    	  * @param productCode
    	  * @param billDate
    	  * @return
    	  * @see
    	  */
	 List<EffectiveExpressPlanDto> searchExpressEffectivePlanDetailList(String originalOrgCode, 
			String destinationOrgCode, String productCode,Date billDate);
	
	/**
	 * <p>计算快递产品运费</p> 
    	  * @author DP-Foss-zdp
    	  * @date 2013-726 下午3:28:28
	 * @param  queryDto  输入产品价格计算条件dto
	 * @return ProductPriceDto 输出产品价格计算后的dto
	 * @see
	 */
	 List<ProductPriceDto> searchExpressProductPriceList(QueryBillCacilateDto queryDto);
	
	
	/**
	 * <p>计算快递产品增值服务费</p> 
    	  * @author DP-Foss-zdp
    	  * @date 2013-726 下午3:28:28
	 * @param queryDto   输入产品价格增值服务计算前条件dto
	 * @return ValueAddDto 输出产品价格增值服务计算后的dto
	 * @see
	 */
	 List<ValueAddDto>  searchExpressValueAddPriceList(QueryBillCacilateValueAddDto queryDto);

	List<ValueAddDto> doZZFRTDiscount(String originalOrgCode,
			String destinationOrgCode, Date receiveDate, BigDecimal weight,
			BigDecimal volume, String originalId, String destinationId,
			List<ValueAddDto> caculateresultList,
			QueryBillCacilateValueAddDto queryBillCacilateDto);

	List<ProductPriceDto> doExpressFRTDiscount(String originalOrgCode,
			String destinationOrgCode, Date receiveDate, BigDecimal weight,
			BigDecimal volume, String originalId, String destinationId,
			List<ProductPriceDto> caculateresultList,
			QueryBillCacilateDto queryBillCacilateDto,BigDecimal firstDiscountRate,BigDecimal continueHeavyDiscount,BigDecimal lowestSecondDiscountFee);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService.limitDiscountFeeRate
	 * @Description:快递运费打折费率最低限制
	 *
	 * @param discountResultDto
	 * @param productPriceDto
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-10-25 上午9:55:04
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-10-25    DP-FOSS-YANGKANG      v1.0.0         create
	 */
		void  limitDiscountFeeRate(DiscountResultDto discountResultDto,ProductPriceDto productPriceDto);
		
		/**
		 * 
		 *
		 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService.limitDiscountFeeRate
		 * @Description:快递运费打折费率最低限制  用于crm打折活动中
		 *
		 * @param discountResultDto
		 * @param productPriceDto
		 *
		 * @version:v1.0
		 * @author:DP-FOSS-YANGKANG
		 * @date:2014-10-26 上午9:55:04
		 *
		 * Modification History:
		 * Date         Author      Version     Description
		 * -----------------------------------------------------------------
		 * 2014-10-26    DP-FOSS-YANGKANG      v1.0.0         create
		 */
	    void limitDiscountFeeRateCompare(ProductPriceDto productPriceDto,PriceDiscountDto priceDiscountDto);
	    
        /**
         * 菜鸟折扣
         * @param originalOrgCode
         * @param destinationOrgCode
         * @param receiveDate
         * @param weight
         * @param volume
         * @param originalId
         * @param destinationId
         * @param caculateresultList
         * @param queryBillCacilateDto
         * @return
         */
		List<ProductPriceDto> doExpressFRTDiscountReturnGood(
				String originalOrgCode, String destinationOrgCode,
				Date receiveDate, BigDecimal weight, BigDecimal volume,
				String originalId, String destinationId,
				List<ProductPriceDto> caculateresultList,
				QueryBillCacilateDto queryBillCacilateDto);
		/**
		 * 
		 *
		 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService.searchExpressProductPriceListOld
		 * @Description:快递定价之前计算快递运费方法  确保快递定价上线之前运单更改单与开单保持一致
		 *
		 * @param queryBillCacilateDto
		 * @return
		 *
		 * @version:v1.0
		 * @author:DP-FOSS-YANGKANG
		 * @date:2015-2-7 上午9:19:42
		 *
		 * Modification History:
		 * Date         Author      Version     Description
		 * -----------------------------------------------------------------
		 * 2015-2-7    DP-FOSS-YANGKANG      v1.0.0         create
		 */
		List<ProductPriceDto> searchExpressProductPriceListOld(
	  			QueryBillCacilateDto queryBillCacilateDto);
		
		/**
		 * 
		 *
		 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService.doExpressFRTDiscountOld
		 * @Description:快递定价上线前  计算快递运费方法
		 *
		 * @param originalOrgCode
		 * @param destinationOrgCode
		 * @param receiveDate
		 * @param weight
		 * @param volume
		 * @param originalId
		 * @param destinationId
		 * @param caculateresultList
		 * @param queryBillCacilateDto
		 * @return
		 *
		 * @version:v1.0
		 * @author:DP-FOSS-YANGKANG
		 * @date:2015-2-7 上午9:32:02
		 *
		 * Modification History:
		 * Date         Author      Version     Description
		 * -----------------------------------------------------------------
		 * 2015-2-7    DP-FOSS-YANGKANG      v1.0.0         create
		 */
		public List<ProductPriceDto> doExpressFRTDiscountOld(String originalOrgCode,
			String destinationOrgCode, Date receiveDate, BigDecimal weight,
			BigDecimal volume, String originalId, String destinationId,
			List<ProductPriceDto> caculateresultList,QueryBillCacilateDto queryBillCacilateDto);
		
		
		/**
		 *快递计算体积重方法 
		 * FossConstants.VOLUME_TO_WEIGHT = new BigDecimal(1000.0/6.0)
	              业务逻辑：
	         1.系统读取客户合同中约定的体积重量转换值，当合同中数值为空时，体积重量转换公式仍为长*宽*高/6000
	         2.定体积重量转换值，当合同中数值不为空时，体积重量转换公式为长*宽*高/X(X为合同中值)
	         3.非合同客户仍使用现有体积重量转换公式
		 * @author 200945 wutao
		 * <a></a>
		 * @param customerCode
		 * @param date
		 * @param code
		 * @param volume
		 * @param originalOrgCode
		 * @return
		 * 
		 */
		public  BigDecimal validateWeightBubbleRate(String customerCode,Date date,String code,BigDecimal volume,String originalOrgCode);
		
		/**
		 * <p>计算快递产品增值服务费</p> 
	    	  * @author DP-Foss-liding
	    	  * @date 2016-4-20 下午3:28:28
		 * @param queryDto   输入产品价格增值服务计算前条件dto
		 * @return ValueAddDto 输出产品价格增值服务计算后的dto
		 * @see
		 */
		List<ValueAddDto>  searchECSValueAddPriceList(QueryBillCacilateValueAddDto queryDto);

		
}
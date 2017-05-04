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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/exception/MessageType.java
 * 
 * FILE NAME        	: MessageType.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.exception;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:操作提示类型类</small></b> </br> 
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> 
 * <b style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%">
 * 1 2012-10-09 张斌    新增
 * </div>
 ******************************************** 
 */
public class MessageType {

	/**
	 * 保存成功
	 */
	public static final String SAVE_SUCCESS = "foss.pricing.saveSuccess";
	/**
	 * 导入成功
	 */
	public static final String IMPORT_SUCCESS = "foss.pricing.importSuccess";
	/**
	 * 保存时效方案成功
	 */
	public static final String SAVE_EFFECTIVEPLAN_SUCCESS = "foss.pricing.saveEffectivePlanSuccess";
	
	/**
	 * 保存时效方案明细成功
	 */
	public static final String SAVE_EFFECTIVEPLANDETAIL_SUCCESS = "foss.pricing.saveEffectivePlanDetailSuccess";
	
	/**
	 * 修改时效方案明细成功
	 */
	public static final String UPDATE_EFFECTIVEPLANDETAIL_SUCCESS = "foss.pricing.updateEffectivePlanDetailSuccess";
	
	/**
	 * 删除时效方案明细成功
	 */
	public static final String DELETE_EFFECTIVEPLANDETAIL_SUCCESS = "foss.pricing.deleteEffectivePlanDetailSuccess";
	
	/**
	 * 修改时效方案成功
	 */
	public static final String UPDATE_EFFECTIVEPLAN_SUCCESS = "foss.pricing.updateEffectivePlanSuccess";
	
	/**
	 * 保存价格方案成功
	 */
	public static final String SAVE_PRICE_PLAN_SUCCESS = "foss.pricing.savePricePlanSuccess";
	
	/**
	 * 激活成功
	 */
	public static final String ACTIVE_SUCCESS = "dpap.authorization.activeSuccess";
	
	public static final String SHOW_FAILURE_MESSAGE ="foss.pricing.activemessage";
	/**
	 * 激活时效方案成功
	 */
	public static final String ACTIVE_EFFECTIVEPLAN_SUCCESS = "dpap.authorization.activEffectivePlaneSuccess";

	/**
	 * 禁用成功
	 *//*
	public static final String END_SUCCESS = "dpap.authorization.endSuccess";*/

	/**
	 * 删除成功
	 */
	public static final String DELETE_SUCCESS = "foss.pricing.deleteSuccess";

	/**
	 * 更新成功
	 */
	public static final String UPDATE_SUCCESS = "foss.pricing.updateSuccess";
	
	/**
	 * 删除折扣方案成功
	 */
	public static final String DELETE_PRICEDISCOUNT_SUCCESS = "foss.pricing.deletePriceDiscountSuccess";
	
	/**
	 * 保存折扣方案成功
	 */
	public static final String SAVE_PRICEDISCOUNT_SUCCESS = "foss.pricing.savePriceDiscountSuccess";
	
	/**
	 * 修改折扣方案成功
	 */
	public static final String UPDATE_PRICEDISCOUNT_SUCCESS = "foss.pricing.updatePriceDiscountSuccess";
	
	/**
	 * 激活折扣方案成功
	 */
	public static final String ACTIVE_PRICEDISCOUNT_SUCCESS = "foss.pricing.activePriceDiscountSuccess";
	
	/**
	 * 升级版本成功
	 */
	public static final String COPY_PRICEDISCOUNT_SUCCESS = "foss.pricing.copyPriceDiscountSuccess";
	
	/**
	 * 删除折扣方案明细成功
	 */
	public static final String DELETE_PRICEDISCOUNTDETAIL_SUCCESS = "foss.pricing.deletePriceDiscountDetailSuccess";
	
	/**
	 * 保存折扣方案明细成功
	 */
	public static final String SAVE_PRICEDISCOUNTDETAIL_SUCCESS = "foss.pricing.savePriceDiscountDetailSuccess";
	
	/**
	 * 修改折扣方案明细成功
	 */
	public static final String UPDATE_PRICEDISCOUNTDETAIL_SUCCESS = "foss.pricing.updatePriceDiscountDetailSuccess";
	
	/**
	 * 激活折扣方案明细成功
	 */
	public static final String ACTIVE_PRICEDISCOUNTDETAIL_SUCCESS = "foss.pricing.activePriceDiscountDetailSuccess";
	
	/**
	 * 删除货物类型成功
	 */
	public static final String DELETE_GOODSTYPE_SUCCESS = "foss.pricing.deleteGoodsTypeSuccess";
	
	/**
	 * 保存货物类型成功
	 */
	public static final String SAVE_GOODSTYPE_SUCCESS = "foss.pricing.saveGoodsTypeSuccess";
	
	/**
	 * 修改货物类型成功
	 */
	public static final String UPDATE_GOODSTYPE_SUCCESS = "foss.pricing.updateGoodsTypeSuccess";
	
	/**
	 * 激活货物类型成功
	 */
	public static final String ACTIVE_GOODSTYPE_SUCCESS = "foss.pricing.activeGoodsTypeSuccess";
	
	
	/**
	 * 删除产品条目成功
	 */
	public static final String DELETE_PRODUCTITEM_SUCCESS = "foss.pricing.deleteProductItemSuccess";
	
	/**
	 * 保存产品条目成功
	 */
	public static final String SAVE_PRODUCTITEM_SUCCESS = "foss.pricing.saveProductItemSuccess";
	
	/**
	 * 修改产品条目成功
	 */
	public static final String UPDATE_PRODUCTITEM_SUCCESS = "foss.pricing.updateProductItemSuccess";
	
	/**
	 * 激活产品条目成功
	 */
	public static final String ACTIVE_PRODUCTITEM_SUCCESS = "foss.pricing.activeProductItemSuccess";
	
	/**
	 * 中止成功
	 */
	public static final String STOP_SUCCESS = "foss.pricing.stopSuccess";
	
	/**
	 * 删除航空增值服务成功
	 */
	public static final String DELETE_AIRLINESVALUEADD_SUCCESS = "foss.pricing.deleteAirlinesValueAddSuccess";
	
	/**
	 * 保存航空增值服务成功
	 */
	public static final String SAVE_AIRLINESVALUEADD_SUCCESS = "foss.pricing.saveAirlinesValueAddSuccess";
	
	/**
	 * 修改航空增值服务成功
	 */
	public static final String UPDATE_AIRLINESVALUEADD_SUCCESS = "foss.pricing.updateAirlinesValueAddSuccess";
	
	/**
	 * 激活航空增值服务成功
	 */
	public static final String ACTIVE_AIRLINESVALUEADD_SUCCESS = "foss.pricing.activeAirlinesValueAddSuccess";
	
	/**
	 * 删除航空运价成功
	 */
	public static final String DELETE_FLIGHTPRICE_SUCCESS = "foss.pricing.deleteFlightPriceSuccess";
	
	/**
	 * 保存航空运价成功
	 */
	public static final String SAVE_FLIGHTPRICE_SUCCESS = "foss.pricing.saveFlightPriceSuccess";
	
	/**
	 * 修改航空运价成功
	 */
	public static final String UPDATE_FLIGHTPRICE_SUCCESS = "foss.pricing.updateFlightPriceSuccess";
	
	/**
	 * 激活航空运价成功
	 */
	public static final String ACTIVE_FLIGHTPRICE_SUCCESS = "foss.pricing.activeFlightPriceSuccess";
	
	/**
	 * 删除航空运价明细成功
	 */
	public static final String DELETE_FLIGHTPRICEDETAIL_SUCCESS = "foss.pricing.deleteFlightPriceDetailSuccess";
	
	/**
	 * 保存航空运价明细成功
	 */
	public static final String SAVE_FLIGHTPRICEDETAIL_SUCCESS = "foss.pricing.saveFlightPriceDetailSuccess";
	
	/**
	 * 修改航空运价明细成功
	 */
	public static final String UPDATE_FLIGHTPRICEDETAIL_SUCCESS = "foss.pricing.updateFlightPriceDetailSuccess";
	
	
	/**
	 * 保存产品成功
	 */
	public static final String SAVE_PRODUCT_SUCCESS = "foss.pricing.saveProductSuccess";
	
	/**
	 * 修改产品成功
	 */
	public static final String UPDATE_PRODUCT_SUCCESS = "foss.pricing.updateProductSuccess";
	
	/**
	 * 激活产品成功
	 */
	public static final String ACTIVE_PRODUCT_SUCCESS = "foss.pricing.activeProductSuccess";
	
	/**
	 * 复制成功
	 */
	public static final String COPY_PRICE_SUCCESS = "foss.pricing.copySuccess";
	
	/**
	 * 保存最低一票方案成功
	 */
	public static final String SAVE_MINFEEPLAN_SUCCESS = "foss.pricing.saveMinFeePlanSuccess";
	
	/**
	 * 删除最低一票方案成功
	 */
	public static final String DELETE_MINFEEPLAN_SUCCESS = "foss.pricing.deleteMinFeePlanSuccess";
}
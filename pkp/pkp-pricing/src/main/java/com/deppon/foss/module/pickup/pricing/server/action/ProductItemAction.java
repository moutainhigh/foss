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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/ProductItemAction.java
 * 
 * FILE NAME        	: ProductItemAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductItemService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ProductItemVo;

/**
 * 公布价管理ACTION
 * 
 * @author 张斌
 * @date 2012-12-18
 * @version 1.0
 */
public class ProductItemAction extends AbstractAction {
	/**
	 * 序列化標識	
	 */
    	private static final long serialVersionUID = 2883644272419312426L;
	
    	/**
    	 * 頁面交互數據VO
    	 */
	private ProductItemVo productItemVo = new ProductItemVo();
	
	/**
	 *獲取VO 
	 */
	public ProductItemVo getProductItemVo() {
		return productItemVo;
	}

	/**
	 *設置VO 
	 */
	public void setProductItemVo(ProductItemVo productItemVo) {
		this.productItemVo = productItemVo;
	}
	
	/**
	 * 產品條目服務
	 */
	private IProductItemService productItemService;
	public void setProductItemService(IProductItemService productItemService) {
		this.productItemService = productItemService;
	}

	/**
	 * .
	 * <p>
	 * 根据条件查询产品条目<br/>
	 * 方法名：searchProductItemByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-18
	 * @since JDK1.6
	 */
	@JSON
	public String searchProductItemByCondition() {
		try {
			List<ProductItemEntity> productItemEntityList =  productItemService.findProductItemPagingByCondiction(productItemVo.getProductItemEntity(), start, limit);
			productItemVo.setProductItemEntityList(productItemEntityList);
			this.setTotalCount(productItemService.countProductItemPagingByCondiction(productItemVo.getProductItemEntity()));//查询总格属
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 新增产品条目<br/>
	 * 方法名：addProductItem
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-18
	 * @since JDK1.6
	 */
	@JSON
	public String addProductItem() {
		try {
			productItemService.addProductItem(productItemVo.getProductItemEntity());
			return returnSuccess(MessageType.SAVE_PRODUCTITEM_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 删除产品条目<br/>
	 * 方法名：deleteProductItem
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-18
	 * @since JDK1.6
	 */
	@JSON
	public String deleteProductItem() {
		try {
			return returnSuccess(MessageType.DELETE_PRODUCTITEM_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 修改产品条目<br/>
	 * 方法名：updateProductItem
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-18
	 * @since JDK1.6
	 */
	@JSON
	public String updateProductItem() {
		try {
			productItemService.updateProductItem(productItemVo.getProductItemEntity());
			return returnSuccess(MessageType.UPDATE_PRODUCTITEM_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 激活产品条目<br/>
	 * 方法名：activationProductItem
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-18
	 * @since JDK1.6
	 */
	@JSON
	public String activationProductItem() {
		try {
			productItemService.activateProductItem(productItemVo.getProductItemids());
			return returnSuccess(MessageType.ACTIVE_PRODUCTITEM_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 查看产品条目明细<br/>
	 * 方法名：findProductItemById
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-18
	 * @since JDK1.6
	 */
	@JSON
	public String findProductItemById() {
		try {
			ProductItemEntity productItemEntity = productItemService.findProductItemById(productItemVo.getProductItemEntity().getId());
			productItemVo.setProductItemEntity(productItemEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * <p>查询二级条目</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-14 下午4:56:14
	 * @return
	 * @see
	 */
	@JSON
	public String queryProductItemLevel2(){
	    try {
		productItemVo.setProductItemEntityList(productItemService.queryProductItemLevel2Info());
		return returnSuccess();
	    } catch (BusinessException e) {
		return returnError(e);
	    }
	}
	
	/**
	 * 
	 * <p>查询三级条目</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-14 下午4:56:27
	 * @return
	 * @see
	 */
	@JSON
	public String queryProductItemLevel3(){
	    try {
		productItemVo.setProductItemEntityList(productItemService.queryProductItemLevel3Info());
		return returnSuccess();
	    } catch (BusinessException e) {
		return returnError(e);
	    }
	}
	
	/**
	 * <p>查询快递二级条目</p> 
	 * @author foss-206860
	 */
	@JSON
	public String queryExpressProductItemLevel2(){
	    try {
	    List<String> productCodeList = new ArrayList<String>();
	    productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
		productItemVo.setProductItemEntityList(productItemService.findProductItemByLevel2ProductCode(productCodeList));
		return returnSuccess();
	    } catch (BusinessException e) {
		return returnError(e);
	    }
	}
}
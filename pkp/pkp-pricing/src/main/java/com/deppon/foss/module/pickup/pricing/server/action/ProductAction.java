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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/ProductAction.java
 * 
 * FILE NAME        	: ProductAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.action;
import java.util.List;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ProductVo;
/**
 * 产品管理-其中包括增加产品、修改产品、激活产品以及查询产品.
 *
 * @author DP-Foss-YueHongJie
 * @date 2012-10-22 下午2:47:08
 */
public class ProductAction extends AbstractAction {
    	/** 
    	 * 
    	 * 序列化标识
    	 * 
    	 */
    	private static final long serialVersionUID = 5531428455896101662L;
	
	/**
	 * 
	 * 产品管理 
	 * 
	 */
	private IProductService productService;

	/**
	 * 
	 *  界面交互对象
	 *  
	 */
	private ProductVo productVo = new ProductVo();

	 
	/**
	 * Gets the product vo.
	 *
	 * @return the product vo
	 */
	public ProductVo getProductVo() {
		return productVo;
	}

	 
	/**
	 * Sets the product vo.
	 *
	 * @param productVo the new product vo
	 */
	public void setProductVo(ProductVo productVo) {
		this.productVo = productVo;
	}

	/**
	 * Sets the product service.
	 *
	 * @param productService the new product service
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * 查询产品信息-按条件分页查询返回.
	 *
	 * @return the string
	 * @author DP-Foss-YueHongJie
	 * @date 2012-10-22 下午2:52:34
	 */
	@JSON
	public String queryProductInfo() {
		try {
			List<ProductEntity> listEntity = productService.findProductPagingByCondition(productVo.getProductEntity(),this.getStart(), this.getLimit());
			productVo.setProductEntityList(listEntity);
			this.setTotalCount(productService.countProductPagingByCondition(productVo.getProductEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 添加产品.
	 *
	 * @return the string
	 * @author DP-Foss-YueHongJie
	 * @date 2012-10-22 下午2:53:46
	 */
	@JSON
	public String addProduct() {
		try {
			productService.addProduct(productVo.getProductEntity());
			return returnSuccess(MessageType.SAVE_PRODUCT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 修改产品.
	 *
	 * @return the string
	 * @author DP-Foss-YueHongJie
	 * @date 2012-10-22 下午2:54:58
	 */
	@JSON
	public String updateProduct() {
		try {
			productService.updateProduct(productVo.getProductEntity());
			return returnSuccess(MessageType.UPDATE_PRODUCT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 激活产品.
	 *
	 * @return the string
	 * @author DP-Foss-YueHongJie
	 * @date 2012-10-22 下午2:55:38
	 */
	@JSON
	public String activateProduct() {
		try {
			productService.activateProducts(productVo.getProductIds());
			return returnSuccess(MessageType.ACTIVE_PRODUCT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	
	/**
	 * <p>查询2-3级产品信息</p>.
	 *
	 * @return the string
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-10 下午8:06:36
	 * @see
	 */
	@JSON
	public String queryProductInfoByLevel2AndLevel3(){
	    try {
		productVo.setProductEntityList(productService.getLevel2And3ProductInfo());
		return returnSuccess();
	    } catch (BusinessException e) {
		return returnError(e);
	    }
	}
	
	@JSON
	public String queryAllLevels3ExpressProductCode(){
	    try {
		productVo.setProductEntityList(productService.getLevel3ForExpress());
		return returnSuccess();
	    } catch (BusinessException e) {
		return returnError(e);
	    }
	}
	
	
	/**
	 * Query product common to level by condition.
	 *
	 * @return the string
	 */
	@JSON
	/**
	 * 
	 * <p>公共选择器查询产品信息productVo.productEnity.
	 * 	levels属性不能为空,必须指定二，
	 * 	三产品其中一种来确定对产品定位筛选</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-1-29 下午5:13:30
	 * @return
	 * @see
	 */
	public String queryProductCommonToLevelByCondition(){
	    productVo.setProductEntityList(productService.queryProductCommonToLevelByCondition(productVo.getProductEntity(), start, limit));
	    this.setTotalCount(productService.countProductCommonToLevelByCondition(productVo.getProductEntity()));
	    return returnSuccess();
	}
}
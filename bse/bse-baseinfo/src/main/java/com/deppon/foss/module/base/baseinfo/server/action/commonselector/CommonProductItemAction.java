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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonProductItemAction.java
 * 
 * FILE NAME        	: CommonProductItemAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonProductItemAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IProductService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonProductItemService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ProductDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonThirdLevelProductVo;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ProductItemVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
/**
 * 公共查询组件--查询产品条目.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午3:00:40
 */
public class CommonProductItemAction extends AbstractAction implements
		IQueryAction {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 8109355746553510458L;
	// vo
	/** The product item vo. */
	private ProductItemVo productItemVo = new ProductItemVo();
	// service
	/** The common product item service. */
	private ICommonProductItemService commonProductItemService;
	//三级产品公共选择器Vo
	private CommonThirdLevelProductVo thirdLevelProductVo;
	//产品查询Service
	private IProductService bseProductService;

	/**
	 * 查询产品条目.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-4 下午3:01:00
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		productItemVo.setProductItemEntities(this.commonProductItemService
				.findPagingByCondition(productItemVo.getProductItemEntity(),
						start, limit));
		setTotalCount(this.commonProductItemService
				.countPagingByCondition(productItemVo.getProductItemEntity()));
		return returnSuccess();
	}
	/**
	 * 查询三级产品（用于公共选择器）(配合POP，三级产品只要精准汽运长短途 精准卡航 精准城运)
	 * @return the string
	 * @author 187862-dujunhui
	 * @date 2014-10-24 下午3:49:24
	 */
	public String queryCommonThirdLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		condtion.setName(thirdLevelProductVo.getThirdLevelProductEntity().getName());
		List<ProductEntity> productEntityList = bseProductService.
				findThirdLevelProductByCondition(condtion, null,start, limit);
		thirdLevelProductVo.setThirdLevelProductEntities(productEntityList);
		totalCount = bseProductService.countThirdLevelProductByCondition(condtion, null);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	/**
	 * getter.
	 *
	 * @return the product item vo
	 */
	public ProductItemVo getProductItemVo() {
		return productItemVo;
	}

	/**
	 * setter.
	 *
	 * @param productItemVo the new product item vo
	 */
	public void setProductItemVo(ProductItemVo productItemVo) {
		this.productItemVo = productItemVo;
	}

	/**
	 * setter.
	 *
	 * @param commonProductItemService the new common product item service
	 */
	public void setCommonProductItemService(
			ICommonProductItemService commonProductItemService) {
		this.commonProductItemService = commonProductItemService;
	}
	/**
	 * @return  the thirdLevelProductVo
	 */
	public CommonThirdLevelProductVo getThirdLevelProductVo() {
		return thirdLevelProductVo;
	}
	/**
	 * @param thirdLevelProductVo the thirdLevelProductVo to set
	 */
	public void setThirdLevelProductVo(CommonThirdLevelProductVo thirdLevelProductVo) {
		this.thirdLevelProductVo = thirdLevelProductVo;
	}
	/**
	 * @param bseProductService the bseProductService to set
	 */
	public void setBseProductService(IProductService bseProductService) {
		this.bseProductService = bseProductService;
	}
	
}

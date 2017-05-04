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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonGoodsAreaAction.java
 * 
 * FILE NAME        	: CommonGoodsAreaAction.java
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
 * FILE    NAME: CmmonGoodsAreaActino.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.GoodsAreaVo;

/**
 * 公共选择器--库区.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午8:26:43
 */
public class CommonGoodsAreaAction extends AbstractAction implements
		IQueryAction {

	/** serializeId. */
	private static final long serialVersionUID = -6327316619927198748L;

	/** The goods area vo. */
	private GoodsAreaVo goodsAreaVo = new GoodsAreaVo();

	// 库区service
	/** The common goods area service. */
	private ICommonGoodsAreaService commonGoodsAreaService;

	/**
	 * 库区查询方法.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-1 上午8:27:25
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	@Override
	public String query() {
		this.setTotalCount(commonGoodsAreaService
				.countGoodsAreaByCondition(goodsAreaVo.getGoodsAreaEntity()));
		List<GoodsAreaEntity> goodsAreaEntityList = commonGoodsAreaService
				.queryGoodsAreaByCondition(goodsAreaVo.getGoodsAreaEntity(),
						start, limit);
		goodsAreaVo.setGoodsAreaEntityList(goodsAreaEntityList);
		return returnSuccess();
	}

	/**
	 * 以下是get 、set方法.
	 *
	 * @return the goods area vo
	 * @author panGuangJun
	 * @date 2012-12-1 上午8:30:07
	 */
	public GoodsAreaVo getGoodsAreaVo() {
		return goodsAreaVo;
	}

	/**
	 * Sets the goods area vo.
	 *
	 * @param goodsAreaVo the new goods area vo
	 */
	public void setGoodsAreaVo(GoodsAreaVo goodsAreaVo) {
		this.goodsAreaVo = goodsAreaVo;
	}

	/**
	 * Sets the common goods area service.
	 *
	 * @param commonGoodsAreaService the new common goods area service
	 */
	public void setCommonGoodsAreaService(
			ICommonGoodsAreaService commonGoodsAreaService) {
		this.commonGoodsAreaService = commonGoodsAreaService;
	}


}

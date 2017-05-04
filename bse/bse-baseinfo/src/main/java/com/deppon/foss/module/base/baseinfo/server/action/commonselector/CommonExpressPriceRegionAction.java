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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonPriceRegionAction.java
 * 
 * FILE NAME        	: CommonPriceRegionAction.java
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
 * FILE    NAME: CommonPriceRegionAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressPriceRegionService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceRegionEntityVo;

/**
 * 公共组件--快递价格区域查询action.
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-8-12 下午6:34:26
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-8-12 下午6:34:26
 * @since
 * @version
 */
public class CommonExpressPriceRegionAction extends AbstractAction implements
	IQueryAction {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7836557540324954109L;

    /**
     * 快递价格区域VO.
     */
    private PriceRegionEntityVo priceRegionEntityVo = new PriceRegionEntityVo();

    /**
     * 公共组件--快递价格区域Service接口.
     */
    private ICommonExpressPriceRegionService commonExpressPriceRegionService;
    
    /**
     * 设置 公共组件--快递价格区域Service接口.
     *
     * @param commonExpressPriceRegionService the commonExpressPriceRegionService to set
     */
    public void setCommonExpressPriceRegionService(
    	ICommonExpressPriceRegionService commonExpressPriceRegionService) {
        this.commonExpressPriceRegionService = commonExpressPriceRegionService;
    }

    /**
     * 价格区域查询方法.
     * 
     * @return the string
     * @author panGuangJun
     * @date 2012-12-4 上午10:47:31
     * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
     */
    @Override
    public String query() {
	
	priceRegionEntityVo.setPriceRegionEntities(commonExpressPriceRegionService
		.searchRegionByCondition(
			priceRegionEntityVo.getPriceRegionEntity(), start,
			limit));
	setTotalCount(commonExpressPriceRegionService
		.countRegionByCondition(priceRegionEntityVo
			.getPriceRegionEntity()));
	return returnSuccess();
    }

    /**
     * 获取 快递价格区域VO.
     *
     * @return the price region entity vo
     * @author panGuangJun
     * @date 2012-12-4 上午10:51:57
     * @retrun PriceRegionEntityVo
     */

    public PriceRegionEntityVo getPriceRegionEntityVo() {
	return priceRegionEntityVo;
    }

    /**
     * 设置 快递价格区域VO.
     *
     * @param priceRegionEntityVo the new price region entity vo
     */
    public void setPriceRegionEntityVo(PriceRegionEntityVo priceRegionEntityVo) {
	this.priceRegionEntityVo = priceRegionEntityVo;
    }

}

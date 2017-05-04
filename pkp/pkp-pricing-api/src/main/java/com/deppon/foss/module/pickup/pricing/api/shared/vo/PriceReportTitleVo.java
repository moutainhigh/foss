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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/FlightPriceVo.java
 * 
 * FILE NAME        	: FlightPriceVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity;
/**
 * 汽运价格报表表头信息VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-1-13 上午10:08:40 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-1-13 上午10:08:40
 * @since
 * @version
 */
public class PriceReportTitleVo implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4644268548176294564L;

    /**
     * 汽运价格报表表头信息实体类.
     */
    private PriceReportTitleEntity priceReportTitle;

    /**
     * 汽运价格报表表头信息实体类.
     */
    private List<PriceReportTitleEntity> priceReportTitleList;

    /**
     * idList.
     */
    private List<String> idList;

    
    /**
     * 获取 汽运价格报表表头信息实体类.
     *
     * @return  the priceReportTitle
     */
    public PriceReportTitleEntity getPriceReportTitle() {
        return priceReportTitle;
    }

    
    /**
     * 设置 汽运价格报表表头信息实体类.
     *
     * @param priceReportTitle the priceReportTitle to set
     */
    public void setPriceReportTitle(PriceReportTitleEntity priceReportTitle) {
        this.priceReportTitle = priceReportTitle;
    }

    
    /**
     * 获取 汽运价格报表表头信息实体类.
     *
     * @return  the priceReportTitleList
     */
    public List<PriceReportTitleEntity> getPriceReportTitleList() {
        return priceReportTitleList;
    }

    
    /**
     * 设置 汽运价格报表表头信息实体类.
     *
     * @param priceReportTitleList the priceReportTitleList to set
     */
    public void setPriceReportTitleList(
    	List<PriceReportTitleEntity> priceReportTitleList) {
        this.priceReportTitleList = priceReportTitleList;
    }

    
    /**
     * 获取 idList.
     *
     * @return  the idList
     */
    public List<String> getIdList() {
        return idList;
    }

    
    /**
     * 设置 idList.
     *
     * @param idList the idList to set
     */
    public void setIdList(List<String> idList) {
        this.idList = idList;
    }
    

    
    

}
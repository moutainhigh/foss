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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/BillSloganVo.java
 * 
 * FILE NAME        	: BillSloganVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity;

/**
 * 单据广告语VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午6:15:07 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 上午9:13:21
 * @since
 * @version
 */
public class BillSloganVo implements Serializable{

    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1138215477732082009L;
    
    /**
     * 单据广告语实体类.
     */
    private BillSloganEntity entity;
    
    /**
     * 单据广告语信息集合.
     */
    private List<BillSloganEntity> billSloganEntities;
    
    /**
     * codes字符串.
     */
    private String codes;
    
    /**
     * 获取 单据广告语实体类.
     *
     * @return the 单据广告语实体类
     */
    public BillSloganEntity getEntity() {
        return entity;
    }
    
    /**
     * 设置 单据广告语实体类.
     *
     * @param entity the new 单据广告语实体类
     */
    public void setEntity(BillSloganEntity entity) {
        this.entity = entity;
    }
    
    /**
     * 获取 单据广告语信息集合.
     *
     * @return the 单据广告语信息集合
     */
    public List<BillSloganEntity> getBillSloganEntities() {
        return billSloganEntities;
    }
    
    /**
     * 设置 单据广告语信息集合.
     *
     * @param billSloganEntities the new 单据广告语信息集合
     */
    public void setBillSloganEntities(List<BillSloganEntity> billSloganEntities) {
        this.billSloganEntities = billSloganEntities;
    }

    /**
     * 获取 codes字符串.
     *
     * @return the codes字符串
     */
    public String getCodes() {
        return codes;
    }
    
    /**
     * 设置 codes字符串.
     *
     * @param codes the new codes字符串
     */
    public void setCodes(String codes) {
        this.codes = codes;
    } 
    
}

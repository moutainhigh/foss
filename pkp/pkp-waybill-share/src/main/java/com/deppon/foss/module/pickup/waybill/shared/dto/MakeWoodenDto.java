/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/MakeWoodenDto.java
 * 
 * FILE NAME        	: MakeWoodenDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.vo
 * FILE    NAME: MakeWooden.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;


/**
 * 运单打木架VO
 * @author 026123-foss-lifengteng
 * @date 2012-10-31 下午3:04:48
 */
public class MakeWoodenDto {
    //运单编号
    private String waybillNo;
    //代打木架部门
    private String packageOrgCode;
    //打木架货物件数
    private Integer standGoodsNum;
    //代打木架要求
    private String standRequirement;
    //打木架货物尺寸
    private String standGoodsSize;
    //打木架货物体积
    private BigDecimal standGoodsVolume;
    //打木箱货物件数
    private Integer boxGoodsNum;
    //代打木箱要求
    private String boxRequirement;
    //打木箱货物尺寸
    private String boxGoodsSize;
    //打木箱货物体积
    private BigDecimal boxGoodsVolume;
    
    
    
    
    //get和set方法
    public String getPackageOrgCode() {
        return packageOrgCode;
    }
    
    public void setPackageOrgCode(String packageOrgCode) {
        this.packageOrgCode = packageOrgCode;
    }
    
    public Integer getStandGoodsNum() {
        return standGoodsNum;
    }
    
    public void setStandGoodsNum(Integer standGoodsNum) {
        this.standGoodsNum = standGoodsNum;
    }
    
    public String getStandRequirement() {
        return standRequirement;
    }
    
    public void setStandRequirement(String standRequirement) {
        this.standRequirement = standRequirement;
    }
    
    public String getStandGoodsSize() {
        return standGoodsSize;
    }
    
    public void setStandGoodsSize(String standGoodsSize) {
        this.standGoodsSize = standGoodsSize;
    }
    
    public BigDecimal getStandGoodsVolume() {
        return standGoodsVolume;
    }
    
    public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
        this.standGoodsVolume = standGoodsVolume;
    }
    
    public Integer getBoxGoodsNum() {
        return boxGoodsNum;
    }
    
    public void setBoxGoodsNum(Integer boxGoodsNum) {
        this.boxGoodsNum = boxGoodsNum;
    }
    
    public String getBoxRequirement() {
        return boxRequirement;
    }
    
    public void setBoxRequirement(String boxRequirement) {
        this.boxRequirement = boxRequirement;
    }
    
    public String getBoxGoodsSize() {
        return boxGoodsSize;
    }
    
    public void setBoxGoodsSize(String boxGoodsSize) {
        this.boxGoodsSize = boxGoodsSize;
    }
    
    public BigDecimal getBoxGoodsVolume() {
        return boxGoodsVolume;
    }
    
    public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
        this.boxGoodsVolume = boxGoodsVolume;
    }

    public String getWaybillNo() {
	return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
	this.waybillNo = waybillNo;
    }
    
    
}
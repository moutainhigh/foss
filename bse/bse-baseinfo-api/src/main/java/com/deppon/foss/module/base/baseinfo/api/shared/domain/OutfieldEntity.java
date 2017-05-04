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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/OutfieldEntity.java
 * 
 * FILE NAME        	: OutfieldEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 外场
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */

public class OutfieldEntity  extends BaseEntity {
	
	/**
	 * 序列化ID
	 */
    private static final long serialVersionUID = -3967231350887724390L;

    /**
    * 数据版本号
    */	
    private Long versionNo;

    /**
    *部门编码
    */	
    private String orgCode;

    /**
    *外场编码
    */	
    private String code;

    /**
    *外场名称
    */	
    private String name;

    /**
    *拼音
    */	
    private String pinyin;

    /**
    *外场简码
    */	
    private String simpleCode;

    /**
    *可汽运配载
    */	
    private String vehicleAssemble;

    /**
    *可外发配载
    */	
    private String outAssemble;

    /**
    *可打木架
    */	
    private String packingWood;

    /**
    *可中转
    */	
    private String transfer;

    /**
    *是否有自动分拣机
    */	
    private String sortingMachine;

    /**
    *货区面积
    */	
    private String goodsArea;

    /**
    *货台面积
    */	
    private String platArea;

    /**
    *库型
    */	
    private String platType;

    /**
    *所属外场
    */	
    private String parentOrgId;

    /**
    *是否启用
    */	
    private String active;

    /**
     * 空运总调编码
    */
    private String airDispatchCode;
    
    /**
     * 空运总调名称
    */
    private String airDispatchName;
    
    /**
     * 外场标杆编码(冗余信息，仅作为容器)
     */
    private String unifiedCode;

    /**
     * 月台总数
     */
    private int platformCount; 
    
    /**
     * 有升降机的月台总数
     */
    private int platformWithLiftCount; 
    
    /**
     * 可停靠车长大于等于9.6米的月台个数
     */
    private int bigPlatformCount; 
    
    /**
     * 可停靠车长小于9.6米的月台个数
     */
    private int smallPlatformCount; 
    
    /**
     * 操作类型（同步时使用）
     */
    private int acitonType;
    
    /**
     * 所属车队部门编码
     */
    private String motorcadeCode;
    
    /**
     * 所属车队部门名称(冗余)
     */
    private String motorcadeName;
    /**
     * 可落地外发配载
     */
    private String expressOutAssemble;
    
    /**
     * 是否有待叉区
     */
    private String isHaveWaitForkArea;
    
    /**
     * 人工速度
     */
    private double manSpeed;
    
    /**
     * 电叉速度
     */
    private double forkSpeed;
    
    /**
     * 外场业务渠道
     */
    private String transferServiceChannel;
    
    /**
     * 
     * <p>是否可打木架</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 11:08:51 AM
     * @return
     * @see
     */
    public boolean checkPackingWood() {
	return StringUtils.equals(packingWood, FossConstants.YES);
    }

    
    public String getExpressOutAssemble() {
		return expressOutAssemble;
	}


	public void setExpressOutAssemble(String expressOutAssemble) {
		this.expressOutAssemble = expressOutAssemble;
	}


	/**
	 * @return  the isHaveWaitForkArea
	 */
	public String getIsHaveWaitForkArea() {
		return isHaveWaitForkArea;
	}


	/**
	 * @param isHaveWaitForkArea the isHaveWaitForkArea to set
	 */
	public void setIsHaveWaitForkArea(String isHaveWaitForkArea) {
		this.isHaveWaitForkArea = isHaveWaitForkArea;
	}




	/**
	 * @return  the manSpeed
	 */
	public double getManSpeed() {
		return manSpeed;
	}


	/**
	 * @param manSpeed the manSpeed to set
	 */
	public void setManSpeed(double manSpeed) {
		this.manSpeed = manSpeed;
	}


	/**
	 * @return  the forkSpeed
	 */
	public double getForkSpeed() {
		return forkSpeed;
	}


	/**
	 * @param forkSpeed the forkSpeed to set
	 */
	public void setForkSpeed(double forkSpeed) {
		this.forkSpeed = forkSpeed;
	}


	/**
     * @return  the motorcadeCode
     */
    public String getMotorcadeCode() {
        return motorcadeCode;
    }


    
    /**
     * @param motorcadeCode the motorcadeCode to set
     */
    public void setMotorcadeCode(String motorcadeCode) {
        this.motorcadeCode = motorcadeCode;
    }


    
    /**
     * @return  the motorcadeName
     */
    public String getMotorcadeName() {
        return motorcadeName;
    }


    
    /**
     * @param motorcadeName the motorcadeName to set
     */
    public void setMotorcadeName(String motorcadeName) {
        this.motorcadeName = motorcadeName;
    }


	/**
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param  orgCode  
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param  code  
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param  name  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param  pinyin  
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**
	 * @return simpleCode
	 */
	public String getSimpleCode() {
		return simpleCode;
	}

	/**
	 * @param  simpleCode  
	 */
	public void setSimpleCode(String simpleCode) {
		this.simpleCode = simpleCode;
	}

	/**
	 * @return vehicleAssemble
	 */
	public String getVehicleAssemble() {
		return vehicleAssemble;
	}

	/**
	 * @param  vehicleAssemble  
	 */
	public void setVehicleAssemble(String vehicleAssemble) {
		this.vehicleAssemble = vehicleAssemble;
	}

	/**
	 * @return outAssemble
	 */
	public String getOutAssemble() {
		return outAssemble;
	}

	/**
	 * @param  outAssemble  
	 */
	public void setOutAssemble(String outAssemble) {
		this.outAssemble = outAssemble;
	}

	/**
	 * @return packingWood
	 */
	public String getPackingWood() {
		return packingWood;
	}

	/**
	 * @param  packingWood  
	 */
	public void setPackingWood(String packingWood) {
		this.packingWood = packingWood;
	}

	/**
	 * @return transfer
	 */
	public String getTransfer() {
		return transfer;
	}

	/**
	 * @param  transfer  
	 */
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	/**
	 * @return sortingMachine
	 */
	public String getSortingMachine() {
		return sortingMachine;
	}

	/**
	 * @param  sortingMachine  
	 */
	public void setSortingMachine(String sortingMachine) {
		this.sortingMachine = sortingMachine;
	}

	/**
	 * @return goodsArea
	 */
	public String getGoodsArea() {
		return goodsArea;
	}

	/**
	 * @param  goodsArea  
	 */
	public void setGoodsArea(String goodsArea) {
		this.goodsArea = goodsArea;
	}

	/**
	 * @return platArea
	 */
	public String getPlatArea() {
		return platArea;
	}

	/**
	 * @param  platArea  
	 */
	public void setPlatArea(String platArea) {
		this.platArea = platArea;
	}

	/**
	 * @return platType
	 */
	public String getPlatType() {
		return platType;
	}

	/**
	 * @param  platType  
	 */
	public void setPlatType(String platType) {
		this.platType = platType;
	}

	/**
	 * @return parentOrgId
	 */
	public String getParentOrgId() {
		return parentOrgId;
	}

	/**
	 * @param  parentOrgId  
	 */
	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return airDispatchCode
	 */
	public String getAirDispatchCode() {
		return airDispatchCode;
	}

	/**
	 * @param  airDispatchCode  
	 */
	public void setAirDispatchCode(String airDispatchCode) {
		this.airDispatchCode = airDispatchCode;
	}

	/**
	 * @return acitonType
	 */
	public int getAcitonType() {
		return acitonType;
	}

	/**
	 * @param  acitonType  
	 */
	public void setAcitonType(int acitonType) {
		this.acitonType = acitonType;
	}

	/**
	 * @return unifiedCode
	 */
	public String getUnifiedCode() {
		return unifiedCode;
	}

	/**
	 * @param  unifiedCode  
	 */
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	/**
	 * @return the airDispatchName
	 */
	public String getAirDispatchName() {
		return airDispatchName;
	}

	/**
	 * @param airDispatchName the airDispatchName to set
	 */
	public void setAirDispatchName(String airDispatchName) {
		this.airDispatchName = airDispatchName;
	}

	/**
	 * @return platformCount
	 */
	public int getPlatformCount() {
		return platformCount;
	}

	/**
	 * @param  platformCount  
	 */
	public void setPlatformCount(int platformCount) {
		this.platformCount = platformCount;
	}

	/**
	 * @return platformWithLiftCount
	 */
	public int getPlatformWithLiftCount() {
		return platformWithLiftCount;
	}

	/**
	 * @param  platformWithLiftCount  
	 */
	public void setPlatformWithLiftCount(int platformWithLiftCount) {
		this.platformWithLiftCount = platformWithLiftCount;
	}

	/**
	 * @return bigPlatformCount
	 */
	public int getBigPlatformCount() {
		return bigPlatformCount;
	}

	/**
	 * @param  bigPlatformCount  
	 */
	public void setBigPlatformCount(int bigPlatformCount) {
		this.bigPlatformCount = bigPlatformCount;
	}

	/**
	 * @return smallPlatformCount
	 */
	public int getSmallPlatformCount() {
		return smallPlatformCount;
	}

	/**
	 * @param  smallPlatformCount  
	 */
	public void setSmallPlatformCount(int smallPlatformCount) {
		this.smallPlatformCount = smallPlatformCount;
	}

	public String getTransferServiceChannel() {
		return transferServiceChannel;
	}

	public void setTransferServiceChannel(String transferServiceChannel) {
		this.transferServiceChannel = transferServiceChannel;
	}
	
}
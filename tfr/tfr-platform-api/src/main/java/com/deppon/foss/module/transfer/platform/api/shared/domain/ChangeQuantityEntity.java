/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/ChangeQuantityEntity.java
 * 
 *  FILE NAME     :ChangeQuantityEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.platform.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 修改货量预测货量entity
 */
public class ChangeQuantityEntity extends BaseEntity{
    
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2935597778330343486L;
	/**
	 * 货量预测改变ID
	 */
	private String changeQtyId;
    /**
     * 原目的地
     */
    private String origDestOrg;
    
    private String origDestOrgName;
    /**
     * 新目的地
     */
    private String newDestOrg;
    
    private String newDestOrgName;
    /**
     * 所属部门
     */
    private String belongTransferCenter;
    
    private String belongTransferCenterName;
    /**
     * 调整重量
     */
    private BigDecimal modifyWeight;
    /**
     * 是否生效
     */
    private String status;
    /**
     * 调整货量类型 出发/到达
     */
    private String type;
    /**
     * 调整时间
     */
    private Date changeTime;

    /**
     * 获取 货量预测改变ID.
     *
     * @return the 货量预测改变ID
     */
    public String getChangeQtyId() {
        return changeQtyId;
    }

    /**
     * 设置 货量预测改变ID.
     *
     * @param changeQtyId the new 货量预测改变ID
     */
    public void setChangeQtyId(String changeQtyId) {
        this.changeQtyId = changeQtyId;
    }

    /**
     * 获取 原目的地.
     *
     * @return the 原目的地
     */
    public String getOrigDestOrg() {
        return origDestOrg;
    }

    /**
     * 设置 原目的地.
     *
     * @param origDestOrg the new 原目的地
     */
    public void setOrigDestOrg(String origDestOrg) {
        this.origDestOrg = origDestOrg;
    }

    /**
     * 获取 新目的地.
     *
     * @return the 新目的地
     */
    public String getNewDestOrg() {
        return newDestOrg;
    }

    /**
     * 设置 新目的地.
     *
     * @param newDestOrg the new 新目的地
     */
    public void setNewDestOrg(String newDestOrg) {
        this.newDestOrg = newDestOrg;
    }

    /**
     * 获取 所属部门.
     *
     * @return the 所属部门
     */
    public String getBelongTransferCenter() {
        return belongTransferCenter;
    }

    /**
     * 设置 所属部门.
     *
     * @param belongTransferCenter the new 所属部门
     */
    public void setBelongTransferCenter(String belongTransferCenter) {
        this.belongTransferCenter = belongTransferCenter;
    }

    /**
     * 获取 调整重量.
     *
     * @return the 调整重量
     */
    public BigDecimal getModifyWeight() {
        return modifyWeight;
    }

    /**
     * 设置 调整重量.
     *
     * @param modifyWeight the new 调整重量
     */
    public void setModifyWeight(BigDecimal modifyWeight) {
        this.modifyWeight = modifyWeight;
    }

    /**
     * 获取 是否生效.
     *
     * @return the 是否生效
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置 是否生效.
     *
     * @param status the new 是否生效
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取 调整货量类型 出发/到达.
     *
     * @return the 调整货量类型 出发/到达
     */
    public String getType() {
        return type;
    }

    /**
     * 设置 调整货量类型 出发/到达.
     *
     * @param type the new 调整货量类型 出发/到达
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * 获取 调整时间.
     *
     * @return the 调整时间
     */
    @DateFormat
    public Date getChangeTime() {
        return changeTime;
    }

    /**
     * 设置 调整时间.
     *
     * @param changeTime the new 调整时间
     */
    @DateFormat
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

	/**
	 * 
	 *
	 * @return 
	 */
	public String getOrigDestOrgName() {
		return origDestOrgName;
	}

	/**
	 * 
	 *
	 * @param origDestOrgName 
	 */
	public void setOrigDestOrgName(String origDestOrgName) {
		this.origDestOrgName = origDestOrgName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getNewDestOrgName() {
		return newDestOrgName;
	}

	/**
	 * 
	 *
	 * @param newDestOrgName 
	 */
	public void setNewDestOrgName(String newDestOrgName) {
		this.newDestOrgName = newDestOrgName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getBelongTransferCenterName() {
		return belongTransferCenterName;
	}

	/**
	 * 
	 *
	 * @param belongTransferCenterName 
	 */
	public void setBelongTransferCenterName(String belongTransferCenterName) {
		this.belongTransferCenterName = belongTransferCenterName;
	}

	/**
	 * 版本号
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
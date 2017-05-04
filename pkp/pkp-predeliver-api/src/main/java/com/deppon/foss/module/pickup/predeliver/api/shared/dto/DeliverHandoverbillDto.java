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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/NotifyCustomerConditionDto.java
 * 
 * FILE NAME        	: NotifyCustomerConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;


/**
 * 已派送交单查询结果DTO
 * @author 159231 meiying
 * 2015-4-20  上午11:28:00
 */
public class DeliverHandoverbillDto extends DeliverHandoverbillEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 排单件数
	 */
	private Integer  arrangeGoodsQty;
	 /**
     * 部门角色类型
     */
    private int OrgRoleType;
	/**
	 * 规定兑现时间
	 */
    private Date cashTime;
    
    public void setArrangeGoodsQty(Integer arrangeGoodsQty) {
        this.arrangeGoodsQty = arrangeGoodsQty;
    }

    /**
	 * 获取orgRoleType  
	 * @return orgRoleType orgRoleType
	 */
	public int getOrgRoleType() {
		return OrgRoleType;
	}

	/**
	 * 设置orgRoleType  
	 * @param orgRoleType orgRoleType 
	 */
	public void setOrgRoleType(int orgRoleType) {
		OrgRoleType = orgRoleType;
	}

	/**
     * 排单状态
     */

    private String deliverBillStatus;

    public Integer getArrangeGoodsQty() {
        return arrangeGoodsQty;
    }

    public String getDeliverBillStatus() {
        return deliverBillStatus;
    }

    public void setDeliverBillStatus(String deliverBillStatus) {
		this.deliverBillStatus = deliverBillStatus;
	}

	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}

	
}
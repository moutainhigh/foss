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


/**
 * 派送交单退回DTO
 * @author 159231 meiying
 * 2015-4-20  上午11:28:00
 */
public class DeliverHandoverbillReturnDto  implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /**
     * 运单号
     */
    private String waybillNo;
    /**
     * 交单件数
     */
    private Integer billQty;
    /**
     * 运单集合
     */
    private String[] waybillNos;
    /**
     * 预计送货日期
     */
    private Date preDeliverDate;


    public String[] getWaybillNos() {
        return waybillNos;
    }

    public void setWaybillNos(String[] waybillNos) {
        this.waybillNos = waybillNos;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public Date getPreDeliverDate() {
        return preDeliverDate;
    }

    public void setPreDeliverDate(Date preDeliverDate) {
        this.preDeliverDate = preDeliverDate;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public void setBillQty(Integer billQty) {
        this.billQty = billQty;
    }

    public Integer getBillQty() {
        return billQty;
    }
}
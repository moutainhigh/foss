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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/domain/StTaskListEntity.java
 *  
 *  FILE NAME          :StTaskListEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 清仓任务所对应的清仓货物清单 实体类
 * @author foss-wuyingjie
 * @date 2012-10-16 下午3:47:39
 */
public class StTaskListEntity extends BaseEntity{

	private static final long serialVersionUID = 54037726167094398L;
	 /**包号*/
    private String packageNo;
	/**运单号*/
	private String waybillNo;
	/**流水号*/
    private String serialNo;
    /**清仓任务编号*/
    private String stTaskId;
    /**入库时间*/
    private Date inStockTime;
    /**库位号*/
    private String positionNo;
    
	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	public Date getInStockTime() {
		return inStockTime;
	}

	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	/**
     * 获取 运单号.
     *
     * @return the 运单号
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * 设置 运单号.
     *
     * @param waybillNo the new 运单号
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    /**
     * 获取 流水号.
     *
     * @return the 流水号
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * 设置 流水号.
     *
     * @param serialNo the new 流水号
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * 获取 清仓任务编号.
     *
     * @return the 清仓任务编号
     */
    public String getStTaskId() {
        return stTaskId;
    }

    /**
     * 设置 清仓任务编号.
     *
     * @param stTaskId the new 清仓任务编号
     */
    public void setStTaskId(String stTaskId) {
        this.stTaskId = stTaskId;
    }
}
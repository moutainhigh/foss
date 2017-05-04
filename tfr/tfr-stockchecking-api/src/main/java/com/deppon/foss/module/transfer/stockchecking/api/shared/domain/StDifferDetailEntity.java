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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/domain/StDifferDetailEntity.java
 *  
 *  FILE NAME          :StDifferDetailEntity.java
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
 *  清仓差异明细实体类
 * @author foss-wuyingjie
 * @date 2012-10-16 下午3:42:20
 */
public class StDifferDetailEntity extends BaseEntity{
	private static final long serialVersionUID = -6406864104612731044L;
	/**清仓差异报告ID*/
	private String stDifferReportId;
	/**OA差错单编号*/
    private String oaErrorNo;
    /**差异类型*/
    private String differenceType;
    /**差异原因**/
    private String differenceReason;
    /**处理时间*/
    private Date handleTime;
    /**处理状态*/
    private String handleStatus;
    /**包号*/
    private String packageNo;
    /**运单号*/
    private String waybillNo;
    /**流水号*/
    private String serialNo;
    /**处理人编号*/
    private String userCode;
    /**备注*/
    private String remark;

	//是否入库
   	private String isInStock;
   	//操作时间
   	private Date operateTime;
   	
   	//运输性质
   	private String transProperty;
   	
   	/**
	 * @return the transProperty
	 */
	public String getTransProperty() {
		return transProperty;
	}

	/**
	 * @param transProperty the transProperty to set
	 */
	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}
	
	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public Date getOperateTime() {
   		return operateTime;
   	}
   	
   	public void setOperateTime(Date operateTime) {
   		this.operateTime = operateTime;
   	}

	public String getIsInStock() {
		return isInStock;
	}

	public void setIsInStock(String isInStock) {
		this.isInStock = isInStock;
	}

	/**
     * 获取 清仓差异报告ID.
     *
     * @return the 清仓差异报告ID
     */
    public String getStDifferReportId() {
        return stDifferReportId;
    }

    /**
     * 设置 清仓差异报告ID.
     *
     * @param stDifferReportId the new 清仓差异报告ID
     */
    public void setStDifferReportId(String stDifferReportId) {
        this.stDifferReportId = stDifferReportId;
    }

    /**
     * 获取 oA差错单编号.
     *
     * @return the oA差错单编号
     */
    public String getOaErrorNo() {
        return oaErrorNo;
    }

    /**
     * 设置 oA差错单编号.
     *
     * @param oaErrorNo the new oA差错单编号
     */
    public void setOaErrorNo(String oaErrorNo) {
        this.oaErrorNo = oaErrorNo;
    }

    /**
     * 获取 差异类型.
     *
     * @return the 差异类型
     */
    public String getDifferenceType() {
        return differenceType;
    }

    /**
     * 设置 差异类型.
     *
     * @param differenceType the new 差异类型
     */
    public void setDifferenceType(String differenceType) {
        this.differenceType = differenceType;
    }

    /**
     * 获取 处理时间.
     *
     * @return the 处理时间
     */
    public Date getHandleTime() {
        return handleTime;
    }

    /**
     * 设置 处理时间.
     *
     * @param handleTime the new 处理时间
     */
    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
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
	 * 获取 处理人编号.
	 *
	 * @return the 处理人编号
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * 设置 处理人编号.
	 *
	 * @param userCode the new 处理人编号
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 获取 处理状态.
	 *
	 * @return the 处理状态
	 */
	public String getHandleStatus() {
		return handleStatus;
	}

	/**
	 * 设置 处理状态.
	 *
	 * @param handleStatus the new 处理状态
	 */
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	/**
	 * 获取 备注.
	 *
	 * @return the 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置 备注.
	 *
	 * @param remark the new 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDifferenceReason() {
		return differenceReason;
	}

	public void setDifferenceReason(String differenceReason) {
		this.differenceReason = differenceReason;
	}
	
	
}
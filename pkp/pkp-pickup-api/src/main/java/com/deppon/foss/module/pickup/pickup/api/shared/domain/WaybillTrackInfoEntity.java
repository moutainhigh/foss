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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/domain/WaybillTrackInfoEntity.java
 * 
 * FILE NAME        	: WaybillTrackInfoEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 运单追踪Entity
 * @author ibm-wangfei
 * @date Jan 4, 2013 11:58:09 AM
 */
public class WaybillTrackInfoEntity  extends BaseEntity{

    /**
	 * 持久化ID
	 */
	private static final long serialVersionUID = 6307188016170055850L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 跟踪类型
	 */
    private String trackType;

    /**
     * 跟踪方式
     */
    private String trackMethod;

    /**
     * 联系方式
     */
    private String contactMethod;

    /**
     * 联系对象
     */
    private String trackMan;

    /**
     * 跟踪内容
     */
    private String trackContent;

    /**
     * 跟踪状态
     */
    private String trackStatus;

    /**
     * 操作人名称
     */
    private String operator;

    /**
     * 操作人编码
     */
    private String operatorCode;

    /**
     * 操作部门code
     */
    private String operateOrgCode;

    /**
     * 操作部门名称
     */
    private String operateOrgName;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 设置 运单号.
     *
     * @return the 运单号
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * 获取 运单号.
     *
     * @param waybillNo the new 运单号
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    /**
     * 设置 跟踪类型.
     *
     * @return the 跟踪类型
     */
    public String getTrackType() {
        return trackType;
    }

    /**
     * 获取 跟踪类型.
     *
     * @param trackType the new 跟踪类型
     */
    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    /**
     * 设置 跟踪方式.
     *
     * @return the 跟踪方式
     */
    public String getTrackMethod() {
        return trackMethod;
    }

    /**
     * 获取 跟踪方式.
     *
     * @param trackMethod the new 跟踪方式
     */
    public void setTrackMethod(String trackMethod) {
        this.trackMethod = trackMethod;
    }

    /**
     * 设置 联系方式.
     *
     * @return the 联系方式
     */
    public String getContactMethod() {
        return contactMethod;
    }

    /**
     * 获取 联系方式.
     *
     * @param contactMethod the new 联系方式
     */
    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    /**
     * 设置 联系对象.
     *
     * @return the 联系对象
     */
    public String getTrackMan() {
        return trackMan;
    }

    /**
     * 获取 联系对象.
     *
     * @param trackMan the new 联系对象
     */
    public void setTrackMan(String trackMan) {
        this.trackMan = trackMan;
    }

    /**
     * 设置 跟踪内容.
     *
     * @return the 跟踪内容
     */
    public String getTrackContent() {
        return trackContent;
    }

    /**
     * 获取 跟踪内容.
     *
     * @param trackContent the new 跟踪内容
     */
    public void setTrackContent(String trackContent) {
        this.trackContent = trackContent;
    }

    /**
     * 设置 跟踪状态.
     *
     * @return the 跟踪状态
     */
    public String getTrackStatus() {
        return trackStatus;
    }

    /**
     * 获取 跟踪状态.
     *
     * @param trackStatus the new 跟踪状态
     */
    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    /**
     * 设置 操作人名称.
     *
     * @return the 操作人名称
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 获取 操作人名称.
     *
     * @param operator the new 操作人名称
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 设置 操作人编码.
     *
     * @return the 操作人编码
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * 获取 操作人编码.
     *
     * @param operatorCode the new 操作人编码
     */
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    /**
     * 设置 操作部门code.
     *
     * @return the 操作部门code
     */
    public String getOperateOrgCode() {
        return operateOrgCode;
    }

    /**
     * 获取 操作部门code.
     *
     * @param operateOrgCode the new 操作部门code
     */
    public void setOperateOrgCode(String operateOrgCode) {
        this.operateOrgCode = operateOrgCode;
    }

    /**
     * 设置 操作部门名称.
     *
     * @return the 操作部门名称
     */
    public String getOperateOrgName() {
        return operateOrgName;
    }

    /**
     * 获取 操作部门名称.
     *
     * @param operateOrgName the new 操作部门名称
     */
    public void setOperateOrgName(String operateOrgName) {
        this.operateOrgName = operateOrgName;
    }

    /**
     * 设置 操作时间.
     *
     * @return the 操作时间
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * 获取 操作时间.
     *
     * @param operateTime the new 操作时间
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 
     * 重写toString方法
     * @return
     * @author ibm-wangfei
     * @date Jan 4, 2013 2:41:58 PM
     * @see java.lang.Object#toString()
     */
	@Override
	public String toString() {
		return "WaybillTrackInfoEntity [waybillNo=" + waybillNo + ", trackType=" + trackType + ", trackMethod=" + trackMethod + ", contactMethod=" + contactMethod + ", trackMan=" + trackMan + ", trackContent=" + trackContent + ", trackStatus="
				+ trackStatus + ", operator=" + operator + ", operatorCode=" + operatorCode + ", operateOrgCode=" + operateOrgCode + ", operateOrgName=" + operateOrgName + ", operateTime=" + operateTime + "]";
	}
}
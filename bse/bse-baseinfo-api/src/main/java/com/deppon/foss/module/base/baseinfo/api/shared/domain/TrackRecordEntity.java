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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/domain/TrackRecordEntity.java
 * 
 * FILE NAME        	: TrackRecordEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class TrackRecordEntity extends BaseEntity {

    private static final long serialVersionUID = -6134112752567019098L;

    private String waybillNo;

    private String contacts;

    private String trackContent;

    private Date createTime;

    private String createUserCode;

    private String createUserName;

    private String createOrgCode;

    private String createOrgName;
    
    private String traceCategories; //跟踪类别
    
    private Date acceptedTime; //受理时间
    
    

	private String acceptedOrgName; //受理部门
    
    private String acceptedMan; //受理人
    
    private String acceptedRemark; //受理备注

    
	/**
     * @return the waybillNo
     */
    public String getWaybillNo() {
	return waybillNo;
    }

    /**
     * @param waybillNo
     *            the waybillNo to set
     */
    public void setWaybillNo(String waybillNo) {
	this.waybillNo = waybillNo;
    }

    /**
     * @return the contacts
     */
    public String getContacts() {
	return contacts;
    }

    /**
     * @param contacts
     *            the contacts to set
     */
    public void setContacts(String contacts) {
	this.contacts = contacts;
    }

    /**
     * @return the trackContent
     */
    public String getTrackContent() {
	return trackContent;
    }

    /**
     * @param trackContent
     *            the trackContent to set
     */
    public void setTrackContent(String trackContent) {
	this.trackContent = trackContent;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
	return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    /**
     * @return the createUserCode
     */
    public String getCreateUserCode() {
	return createUserCode;
    }

    /**
     * @param createUserCode
     *            the createUserCode to set
     */
    public void setCreateUserCode(String createUserCode) {
	this.createUserCode = createUserCode;
    }

    /**
     * @return the createUserName
     */
    public String getCreateUserName() {
	return createUserName;
    }

    /**
     * @param createUserName
     *            the createUserName to set
     */
    public void setCreateUserName(String createUserName) {
	this.createUserName = createUserName;
    }

    /**
     * @return the createOrgCode
     */
    public String getCreateOrgCode() {
	return createOrgCode;
    }

    /**
     * @param createOrgCode
     *            the createOrgCode to set
     */
    public void setCreateOrgCode(String createOrgCode) {
	this.createOrgCode = createOrgCode;
    }

    /**
     * @return the createOrgName
     */
    public String getCreateOrgName() {
	return createOrgName;
    }

    /**
     * @param createOrgName
     *            the createOrgName to set
     */
    public void setCreateOrgName(String createOrgName) {
	this.createOrgName = createOrgName;
    }
    
    /**
     * @param 得到跟踪类别信息
     */
    public String getTraceCategories() {
		return traceCategories;
	}
    
    /**
     * @param 设置跟踪类别信息
     */
	public void setTraceCategories(String traceCategories) {
		this.traceCategories = traceCategories;
	}
	
	/**
     * @param 得到受理时间
     */
	public Date getAcceptedTime() {
		return acceptedTime;
	}
	
	/**
     * @param 设置受理时间
     */
	public void setAcceptedTime(Date acceptedTime) {
		this.acceptedTime = acceptedTime;
	}
	
	/**
     * @param 得到受理部门
     */
	public String getAcceptedOrgName() {
		return acceptedOrgName;
	}
	
	/**
     * @param 设置受理部门
     */
	public void setAcceptedOrgName(String acceptedOrgName) {
		this.acceptedOrgName = acceptedOrgName;
	}
	
	/**
     * @param 得到受理人
     */
	public String getAcceptedMan() {
		return acceptedMan;
	}
	
	/**
     * @param 设置受理人
     */
	public void setAcceptedMan(String acceptedMan) {
		this.acceptedMan = acceptedMan;
	}
	
	/**
     * @param 得到受理备注
     */
	public String getAcceptedRemark() {
		return acceptedRemark;
	}
	
	/**
     * @param 设置受理备注
     */
	public void setAcceptedRemark(String acceptedRemark) {
		this.acceptedRemark = acceptedRemark;
	}

}

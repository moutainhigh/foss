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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillSystemDto.java
 * 
 * FILE NAME        	: WaybillSystemDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: WaybillSystemDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;


/**
 * 封装运单系统信息
 * @author 026123-foss-lifengteng
 * @date 2012-11-16 上午8:56:10
 */
public class WaybillSystemDto  implements Serializable{
    /**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1538698918309102968L;
	/** 
	 *  创建时间
	 */
    private Date createTime ;
    /** 
     *  修改时间
     */
    private Date modifyTime ;
    /** 
     *  当前用户信息
     */
    private CurrentInfo currentUser;
    /** 
     *  开单时间
     */
    private Date billTime;
	
	/**
	 * @return the createTime .
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 *@param createTime the createTime to set.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @return the modifyTime .
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	
	/**
	 *@param modifyTime the modifyTime to set.
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * @return the currentUser .
	 */
	public CurrentInfo getCurrentUser() {
		return currentUser;
	}
	
	/**
	 *@param currentUser the currentUser to set.
	 */
	public void setCurrentUser(CurrentInfo currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * @return the billTime .
	 */
	public Date getBillTime() {
		return billTime;
	}
	
	/**
	 *@param billTime the billTime to set.
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
    
   
}
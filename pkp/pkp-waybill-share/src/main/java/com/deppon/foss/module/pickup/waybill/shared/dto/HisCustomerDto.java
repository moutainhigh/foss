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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/HisCustomerDto.java
 * 
 * FILE NAME        	: HisCustomerDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

/**
 * 
 * 历史接/发货客户
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-7 下午2:22:04 </p>
 * @author foss-sunrui
 * @date 2012-11-7 下午2:22:04
 * @since
 * @version
 */
public class HisCustomerDto {

    /**
     * 联系人名
     */
    private String contact; 
    
    /**
     * 联系人手机
     */
    private String mobilePhone;
    
    /**
     * 联系人座机
     */
    private String phone;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 接送货地址
     */
    private String address;
	
	/**
	 * @return the contact .
	 */
	public String getContact() {
		return contact;
	}
	
	/**
	 *@param contact the contact to set.
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	/**
	 * @return the mobilePhone .
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	
	/**
	 *@param mobilePhone the mobilePhone to set.
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	/**
	 * @return the phone .
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 *@param phone the phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
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
	 * @return the address .
	 */
	public String getAddress() {
		return address;
	}

	
	/**
	 *@param address the address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

   
}
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/ValueAddServiceDto.java
 * 
 * FILE NAME        	: ValueAddServiceDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 增值服务Dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-gengzhe,date:2012-12-24 下午4:45:26,content:</p>
 * @author foss-gengzhe
 * @date 2012-12-24 下午4:45:26
 * @since
 * @version
 */
public class ValueAddServiceDto  implements Serializable{

	private static final long serialVersionUID = -4695457580376786401L;

	//增值服务code
	private String code;
	
	//增值服务价格
	private BigDecimal amount;
	
	
	private String subType;

	/**
	 * 营销活动CODE
	 */
	private String activeCode;
	/**
	 * 营销活动名称
	 */
	private String activeName;
	/**
	 * 营销活动开始时间
	 */
	private Date activeStartTime;
	/**
	 * 营销活动结束时间
	 */
	private Date activeEndTime;
	/**
	 * 营销活动折扣关联的CRM_ID
	 */
	private BigDecimal optionsCrmId;
	

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public Date getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public Date getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public BigDecimal getOptionsCrmId() {
		return optionsCrmId;
	}

	public void setOptionsCrmId(BigDecimal optionsCrmId) {
		this.optionsCrmId = optionsCrmId;
	}
	
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * 
	 * <p>增值服务Codeget方法</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-24 下午5:00:59
	 * @return
	 * @see
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * <p>增值服务Codeset方法</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-24 下午5:01:05
	 * @param code
	 * @see
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 
	 * <p>增值服务价格get方法</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-24 下午5:02:12
	 * @return
	 * @see
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 
	 * <p>增值服务价格set方法</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-24 下午5:02:20
	 * @param amount
	 * @see
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
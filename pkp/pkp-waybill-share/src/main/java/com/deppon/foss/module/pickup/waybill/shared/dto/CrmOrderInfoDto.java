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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmOrderInfoDto.java
 * 
 * FILE NAME        	: CrmOrderInfoDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * CRM系统订单信息
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-15 下午3:49:48, </p>
 * @author foss-sunrui
 * @date 2012-11-15 下午3:49:48
 * @since
 * @version
 */
public class CrmOrderInfoDto implements Serializable {
	/**
     * 序列化标识
     */
    private static final long serialVersionUID = -6907958060059309350L;

    /**
     * CRM订单信息列表
     */
    private List<CrmOrderInfo> crmOrderInfoList;
    
    /**
     * 订单总个数
     */
    private int totalCount;

	
	/**
	 * @return the crmOrderInfoList .
	 */
	public List<CrmOrderInfo> getCrmOrderInfoList() {
		return crmOrderInfoList;
	}

	
	/**
	 *@param crmOrderInfoList the crmOrderInfoList to set.
	 */
	public void setCrmOrderInfoList(List<CrmOrderInfo> crmOrderInfoList) {
		this.crmOrderInfoList = crmOrderInfoList;
	}

	
	/**
	 * @return the totalCount .
	 */
	public int getTotalCount() {
		return totalCount;
	}

	
	/**
	 *@param totalCount the totalCount to set.
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
    
   
}
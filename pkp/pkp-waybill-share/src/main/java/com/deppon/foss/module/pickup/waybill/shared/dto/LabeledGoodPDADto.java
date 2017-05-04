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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/LabeledGoodPDADto.java
 * 
 * FILE NAME        	: LabeledGoodPDADto.java
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
 * PDA货签信息上传实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-11 下午3:10:37,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-12-11 下午3:10:37
 * @since
 * @version
 */
public class LabeledGoodPDADto {

	//运单号
	private String waybillNo;
    //流水号
    private String serialNo;
    //打印人
    private String printPerson;
    //打印时间
    private Date printTime;
	
	/**
	 * @return the waybillNo .
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 *@param waybillNo the waybillNo to set.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * @return the serialNo .
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 *@param serialNo the serialNo to set.
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * @return the printPerson .
	 */
	public String getPrintPerson() {
		return printPerson;
	}
	
	/**
	 *@param printPerson the printPerson to set.
	 */
	public void setPrintPerson(String printPerson) {
		this.printPerson = printPerson;
	}
	
	/**
	 * @return the printTime .
	 */
	public Date getPrintTime() {
		return printTime;
	}
	
	/**
	 *@param printTime the printTime to set.
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

}
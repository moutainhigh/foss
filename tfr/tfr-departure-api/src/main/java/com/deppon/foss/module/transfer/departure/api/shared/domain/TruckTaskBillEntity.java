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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/TruckTaskBillEntity.java
 *  
 *  FILE NAME          :TruckTaskBillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class TruckTaskBillEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********交接编号************/
	private String billNo;
	
	/**********目的站************/
	private String destOrgName;
	
	/**********货物柜号************/
	private String containerNo;
	
	/**********任务车辆明细************/
	private String truckTaskDetailId;
	
	/**********单据级别************/
	private String billLevel;
	
	/**********单据类型************/
	private String billType;
	
	/**********装车人名称**********/
	private String loaderName;

	/**
	 * 获取 ********交接编号***********.
	 *
	 * @return the ********交接编号***********
	 */
	public String getBillNo(){
		return billNo;
	}

	/**
	 * 设置 ********交接编号***********.
	 *
	 * @param billNo the new ********交接编号***********
	 */
	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	/**
	 * 获取 ********目的站***********.
	 *
	 * @return the ********目的站***********
	 */
	public String getDestOrgName(){
		return destOrgName;
	}

	/**
	 * 设置 ********目的站***********.
	 *
	 * @param destOrgName the new ********目的站***********
	 */
	public void setDestOrgName(String destOrgName){
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 ********货物柜号***********.
	 *
	 * @return the ********货物柜号***********
	 */
	public String getContainerNo(){
		return containerNo;
	}

	/**
	 * 设置 ********货物柜号***********.
	 *
	 * @param containerNo the new ********货物柜号***********
	 */
	public void setContainerNo(String containerNo){
		this.containerNo = containerNo;
	}

	/**
	 * 获取 ********任务车辆明细***********.
	 *
	 * @return the ********任务车辆明细***********
	 */
	public String getTruckTaskDetailId(){
		return truckTaskDetailId;
	}

	/**
	 * 设置 ********任务车辆明细***********.
	 *
	 * @param truckTaskDetailId the new ********任务车辆明细***********
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId){
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * 获取 ********单据级别***********.
	 *
	 * @return the ********单据级别***********
	 */
	public String getBillLevel(){
		return billLevel;
	}

	/**
	 * 设置 ********单据级别***********.
	 *
	 * @param billLevel the new ********单据级别***********
	 */
	public void setBillLevel(String billLevel){
		this.billLevel = billLevel;
	}

	/**
	 * 获取 ********单据类型***********.
	 *
	 * @return the ********单据类型***********
	 */
	public String getBillType(){
		return billType;
	}

	/**
	 * 设置 ********单据类型***********.
	 *
	 * @param billType the new ********单据类型***********
	 */
	public void setBillType(String billType){
		this.billType = billType;
	}

	/**
	 * 获取 ********装车人名称*********.
	 *
	 * @return the ********装车人名称*********
	 */
	public String getLoaderName(){
		return loaderName;
	}

	/**
	 * 设置 ********装车人名称*********.
	 *
	 * @param loaderName the new ********装车人名称*********
	 */
	public void setLoaderName(String loaderName){
		this.loaderName = loaderName;
	}

	
	
}
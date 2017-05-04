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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/VehicleAssembleBillOptLogEntity.java
 *  
 *  FILE NAME          :VehicleAssembleBillOptLogEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 配载单操作日志实体类
 * @author 045923-foss-shiwei
 * @date 2012-11-8 上午10:25:28
 */
public class VehicleAssembleBillOptLogEntity extends BaseEntity{
	
	private static final long serialVersionUID = -1985445242504483302L;
	
	//配载单ID
    private String vehicleAssembleBillId;
    //配载车次号
    private String vehicleAssembleNo;
    //操作类别
    private String optType;
    //操作人工号
    private String operatorCode;
    //操作人姓名
    private String operatorName;
    //操作内容
    private String optContent;
    //操作时间
    private Date optTime;
    //制单日期
    private Date billingTime;
    
	public String getVehicleAssembleBillId() {
		return vehicleAssembleBillId;
	}
	public void setVehicleAssembleBillId(String vehicleAssembleBillId) {
		this.vehicleAssembleBillId = vehicleAssembleBillId;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOptContent() {
		return optContent;
	}
	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}
	@DateFormat
	public Date getBillingTime() {
		return billingTime;
	}
	@DateFormat
	public void setBillingTime(Date billingTime) {
		this.billingTime = billingTime;
	}
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	@DateFormat
	public Date getOptTime() {
		return optTime;
	}
	@DateFormat
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

}
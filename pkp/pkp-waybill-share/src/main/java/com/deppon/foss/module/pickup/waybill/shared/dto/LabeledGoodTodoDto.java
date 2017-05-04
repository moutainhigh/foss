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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/LabeledGoodTodoDto.java
 * 
 * FILE NAME        	: LabeledGoodTodoDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;

/**
 * 
 * 标签重打
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:邵宏亮,date:2012-10-10 下午6:52:10,content:TODO </p>
 * @author 邵宏亮
 * @date 2012-10-10 下午6:52:10
 * @since
 * @version
 */
public class LabeledGoodTodoDto 
{
	/**
	 * 运单号
	 */
    private String waybillNo;
    /**
     *  变更起草部门
     */
    private String darftOrgName;
    /**
     *  起草时间
     */
    private Date darftTime;
    /**
     *  受理时间
     */
    private Date operateTime;
    /**
     *  起草人
     */
    private String darfter;
    /**
     *  操作人
     */
    private String operator;
    /**
     *  操作人
     */
    private String todooperator;
    /**
     *  处理状态
     */
    private String status;
    /**
     *  操作时间
     */
    private Date todooperateTime;
    /**
     * 变更详情
     */
    private List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList;
    /**
     * 打印详情
     */
    private List<LabeledGoodTodoEntity> labeledGoodTodoEntityList;
    
    /**
	 * 更改单ID
	 */
	private String waybillRfcId;
	
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * @return the darftOrgName
	 */
	public String getDarftOrgName() {
		return darftOrgName;
	}
	
	/**
	 * @param darftOrgName the darftOrgName to set
	 */
	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}
	
	/**
	 * @return the darftTime
	 */
	public Date getDarftTime() {
		return darftTime;
	}
	
	/**
	 * @param darftTime the darftTime to set
	 */
	public void setDarftTime(Date darftTime) {
		this.darftTime = darftTime;
	}
	
	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}
	
	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	/**
	 * @return the darfter
	 */
	public String getDarfter() {
		return darfter;
	}
	
	/**
	 * @param darfter the darfter to set
	 */
	public void setDarfter(String darfter) {
		this.darfter = darfter;
	}
	
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * @return the todooperator
	 */
	public String getTodooperator() {
		return todooperator;
	}
	
	/**
	 * @param todooperator the todooperator to set
	 */
	public void setTodooperator(String todooperator) {
		this.todooperator = todooperator;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the todooperateTime
	 */
	public Date getTodooperateTime() {
		return todooperateTime;
	}
	
	/**
	 * @param todooperateTime the todooperateTime to set
	 */
	public void setTodooperateTime(Date todooperateTime) {
		this.todooperateTime = todooperateTime;
	}
	
	/**
	 * @return the waybillRfcChangeDetailEntityList
	 */
	public List<WaybillRfcChangeDetailEntity> getWaybillRfcChangeDetailEntityList() {
		return waybillRfcChangeDetailEntityList;
	}
	
	/**
	 * @param waybillRfcChangeDetailEntityList the waybillRfcChangeDetailEntityList to set
	 */
	public void setWaybillRfcChangeDetailEntityList(
			List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntityList) {
		this.waybillRfcChangeDetailEntityList = waybillRfcChangeDetailEntityList;
	}
	
	/**
	 * @return the labeledGoodTodoEntityList
	 */
	public List<LabeledGoodTodoEntity> getLabeledGoodTodoEntityList() {
		return labeledGoodTodoEntityList;
	}
	
	/**
	 * @param labeledGoodTodoEntityList the labeledGoodTodoEntityList to set
	 */
	public void setLabeledGoodTodoEntityList(
			List<LabeledGoodTodoEntity> labeledGoodTodoEntityList) {
		this.labeledGoodTodoEntityList = labeledGoodTodoEntityList;
	}

	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}
	
}
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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/LoadGaprepDto.java
 * 
 * FILE NAME        	: LoadGaprepDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

/**
 * 差异报告Dto，属性从com.deppon.foss.module.transfer.load.api.server.service.
 * DeliverLoadGapReportEntity中拷贝
 * @author ibm-wangxiexu
 * @date 2012-12-6 下午2:24:50
 */
public class LoadGaprepDto implements Serializable {
	private static final long serialVersionUID = -7584231817481680002L;

	private String taskNo;
	private String reportNo;
	private String deliverBillNo;
	private String createTime;
	private String state;
	private int planLoadGoodsQty;
	private int actualLoadGoodsQty;
	private int lackGoodsQty;
	private String vehicleNo;

	/**
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * @param taskNo the taskNo to see
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * @return the reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}

	/**
	 * @param reportNo the reportNo to see
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	/**
	 * @return the deliverBillNo
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}

	/**
	 * @param deliverBillNo the deliverBillNo to see
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to see
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to see
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the planLoadGoodsQty
	 */
	public int getPlanLoadGoodsQty() {
		return planLoadGoodsQty;
	}

	/**
	 * @param planLoadGoodsQty the planLoadGoodsQty to see
	 */
	public void setPlanLoadGoodsQty(int planLoadGoodsQty) {
		this.planLoadGoodsQty = planLoadGoodsQty;
	}

	/**
	 * @return the actualLoadGoodsQty
	 */
	public int getActualLoadGoodsQty() {
		return actualLoadGoodsQty;
	}

	/**
	 * @param actualLoadGoodsQty the actualLoadGoodsQty to see
	 */
	public void setActualLoadGoodsQty(int actualLoadGoodsQty) {
		this.actualLoadGoodsQty = actualLoadGoodsQty;
	}

	/**
	 * @return the lackGoodsQty
	 */
	public int getLackGoodsQty() {
		return lackGoodsQty;
	}

	/**
	 * @param lackGoodsQty the lackGoodsQty to see
	 */
	public void setLackGoodsQty(int lackGoodsQty) {
		this.lackGoodsQty = lackGoodsQty;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to see
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

}
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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaSignDto.java
 * 
 * FILE NAME        	: PdaSignDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 签收出库(PDA和中转接口) 参数
 * @author foss-meiying
 * @date 2012-11-27 下午2:11:26
 * @since
 * @version
 */
public class PdaSignDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1085658235666590764L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 到达联编号
	 */
	private String arrivesheetNo;
	/**
	 * 签收部门编码
	 */
	private String signDeptCode;
	/**
	 * 签收件数
	 */
	private Integer signGoodsQty;
	/**
	 * 签收情况（正常签收、异常-破损、异常-潮湿、异常-污染、异常-其他、部分签收）
	 */
	private String situation;
	
	/***
	 * 签收人类型（本人、门卫、前台、收发室、同事、亲属、其他）
	 */
	private String deliverymanType;
	/**
	 * 签收时间
	 */
	private Date signTime;
	/**
	 * 操作人工号
	 */
	private String operatorCode;
	/**
	 * 签收流水号列表
	 */
	private List<String> serialNos;
	/**
	 * 签收备注
	 */
	private String signNote;

	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 签收件数.
	 *
	 * @return the 签收件数
	 */
	public Integer getSignGoodsQty() {
		return signGoodsQty;
	}

	/**
	 * Sets the 签收件数.
	 *
	 * @param signGoodsQty the new 签收件数
	 */
	public void setSignGoodsQty(Integer signGoodsQty) {
		this.signGoodsQty = signGoodsQty;
	}

	/**
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arrivesheetNo the new 到达联编号
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * Gets the 签收时间.
	 *
	 * @return the 签收时间
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * Sets the 签收时间.
	 *
	 * @param signTime the new 签收时间
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * Gets the 签收部门编码.
	 *
	 * @return the 签收部门编码
	 */
	public String getSignDeptCode() {
		return signDeptCode;
	}

	/**
	 * Sets the 签收部门编码.
	 *
	 * @param signDeptCode the new 签收部门编码
	 */
	public void setSignDeptCode(String signDeptCode) {
		this.signDeptCode = signDeptCode;
	}

	/**
	 * Gets the 签收情况（正常签收、异常-破损、异常-潮湿、异常-污染、异常-其他、部分签收）.
	 *
	 * @return the 签收情况（正常签收、异常-破损、异常-潮湿、异常-污染、异常-其他、部分签收）
	 */
	public String getSituation() {
		return situation;
	}

	/**
	 * Sets the 签收情况（正常签收、异常-破损、异常-潮湿、异常-污染、异常-其他、部分签收）.
	 *
	 * @param situation the new 签收情况（正常签收、异常-破损、异常-潮湿、异常-污染、异常-其他、部分签收）
	 */
	public void setSituation(String situation) {
		this.situation = situation;
	}

	/**
	 * Gets the 操作人工号.
	 *
	 * @return the 操作人工号
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * Sets the 操作人工号.
	 *
	 * @param operatorCode the new 操作人工号
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * Gets the 签收流水号列表.
	 *
	 * @return the 签收流水号列表
	 */
	public List<String> getSerialNos() {
		return serialNos;
	}

	/**
	 * Sets the 签收流水号列表.
	 *
	 * @param serialNos the new 签收流水号列表
	 */
	public void setSerialNos(List<String> serialNos) {
		this.serialNos = serialNos;
	}

	/**
	 * Gets the 签收备注.
	 *
	 * @return the 签收备注
	 */
	public String getSignNote() {
		return signNote;
	}

	/**
	 * Sets the 签收备注.
	 *
	 * @param signNote the new 签收备注
	 */
	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}
	
	/**
	 * Gets the 签收人类型.
	 *
	 * @return the 签收人类型
	 */
	public String getDeliverymanType() {
		return deliverymanType;
	}

	/**
	 * Sets the 签收人类型.
	 *
	 * @param deliverymanType the new 签收人类型
	 */
	public void setDeliverymanType(String deliverymanType) {
		this.deliverymanType = deliverymanType;
	}

}
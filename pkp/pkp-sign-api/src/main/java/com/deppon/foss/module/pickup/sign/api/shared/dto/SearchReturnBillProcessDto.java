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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/SearchReturnBillProcessDto.java
 * 
 * FILE NAME        	: SearchReturnBillProcessDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ReturnBillProcessEntity;

/**
 * 签收单返单
 * @date 2012-10-20 下午2:07:55
 */
public class SearchReturnBillProcessDto implements Serializable {
	private static final long serialVersionUID = -203004523512179272L;

	// ids
	private String ids;

	// 签收或者开单时间查询 0表示签收时间查询 1表示开单时间查询
	private String signOrWaybillType;

	// 运单号
	private String waybillNo;

	// 返单类型
	private String type;

	// 出发部门
	private String fromDepartmentCode;

	// 到达部门
	private String toDepartmentCode;

	// 返单状态
	private String status;

	// 签收起始时间
	private Date signStartTime;

	// 签收结束时间
	private Date signEndTime;
	
	// 签收起始时间
	private Date billStartTime;

	// 签收结束时间
	private Date billEndTime;
	
	// 签收起始时间
	private Date arriveStartTime;

	// 签收结束时间
	private Date arriveEndTime;
	
	// 签收起始时间
	private Date instockStartTime;

	// 签收结束时间
	private Date instockEndTime;
	
	// 库存部门
	private String stockOrgCode;
	
	// 库区编码
	private String goodsAreaCode;
	/**
	 * 快递零担库区
	 */
	private List<String> goodsAreaCodes;
	public List<String> getGoodsAreaCodes() {
		return goodsAreaCodes;
	}

	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
		this.goodsAreaCodes = goodsAreaCodes;
	}
	// 部门区分
	private String orgDiff;
	
	public String getStockOrgCode() {
		return stockOrgCode;
	}

	public void setStockOrgCode(String stockOrgCode) {
		this.stockOrgCode = stockOrgCode;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public Date getSignStartTime() {
		return signStartTime;
	}

	public void setSignStartTime(Date signStartTime) {
		this.signStartTime = signStartTime;
	}

	public Date getSignEndTime() {
		return signEndTime;
	}

	public void setSignEndTime(Date signEndTime) {
		this.signEndTime = signEndTime;
	}

	public Date getBillStartTime() {
		return billStartTime;
	}

	public void setBillStartTime(Date billStartTime) {
		this.billStartTime = billStartTime;
	}

	public Date getBillEndTime() {
		return billEndTime;
	}

	public void setBillEndTime(Date billEndTime) {
		this.billEndTime = billEndTime;
	}

	public Date getArriveStartTime() {
		return arriveStartTime;
	}

	public void setArriveStartTime(Date arriveStartTime) {
		this.arriveStartTime = arriveStartTime;
	}

	public Date getArriveEndTime() {
		return arriveEndTime;
	}

	public void setArriveEndTime(Date arriveEndTime) {
		this.arriveEndTime = arriveEndTime;
	}

	public Date getInstockStartTime() {
		return instockStartTime;
	}

	public void setInstockStartTime(Date instockStartTime) {
		this.instockStartTime = instockStartTime;
	}

	public Date getInstockEndTime() {
		return instockEndTime;
	}

	public void setInstockEndTime(Date instockEndTime) {
		this.instockEndTime = instockEndTime;
	}

	// 签收单返单实体
	private ReturnBillProcessEntity returnBillProcessEntity = new ReturnBillProcessEntity();

	/**
	 * @return the toDepartmentCode
	 */
	public String getToDepartmentCode() {
		return toDepartmentCode;
	}

	/**
	 * @param toDepartmentCode the toDepartmentCode to see
	 */
	public void setToDepartmentCode(String toDepartmentCode) {
		this.toDepartmentCode = toDepartmentCode;
	}

	/**
	 * @return the returnBillProcessEntity
	 */
	public ReturnBillProcessEntity getReturnBillProcessEntity() {
		return returnBillProcessEntity;
	}

	/**
	 * @param returnBillProcessEntity the returnBillProcessEntity to see
	 */
	public void setReturnBillProcessEntity(
			ReturnBillProcessEntity returnBillProcessEntity) {
		this.returnBillProcessEntity = returnBillProcessEntity;
	}

	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to see
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @return the signOrWaybillType
	 */
	public String getSignOrWaybillType() {
		return signOrWaybillType;
	}

	/**
	 * @param signOrWaybillType the signOrWaybillType to see
	 */
	public void setSignOrWaybillType(String signOrWaybillType) {
		this.signOrWaybillType = signOrWaybillType;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to see
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the fromDepartmentCode
	 */
	public String getFromDepartmentCode() {
		return fromDepartmentCode;
	}

	/**
	 * @param fromDepartmentCode the fromDepartmentCode to see
	 */
	public void setFromDepartmentCode(String fromDepartmentCode) {
		this.fromDepartmentCode = fromDepartmentCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrgDiff() {
		return orgDiff;
	}

	public void setOrgDiff(String orgDiff) {
		this.orgDiff = orgDiff;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
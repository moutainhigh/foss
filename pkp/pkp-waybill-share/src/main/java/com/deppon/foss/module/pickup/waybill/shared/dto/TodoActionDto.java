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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/TodoActionDto.java
 * 
 * FILE NAME        	: TodoActionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;

/**
 * 
 * 返回todoAction
 * 
 * @author dp-zhaobin
 * @date 2012-10-20 下午3:22:24
 */
public class TodoActionDto {

	/**
	 * 更改单ID
	 */
	private String waybillRfcId;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 处理状态
	 */
	private String status;
	
	/**
	 * 变更起草部门
	 */
	private String darftOrgCode;

	/**
	 * 变更起草部门
	 */
	private String darftOrgName;
	
	/**
	 * 变更受理部门Name
	 */
	private String handleOrgName;
	
	/**
	 * 待办受理部门Code
	 */
	private String handleOrgCode;
	
	/**
	 * 操作部门name
	 */
	private String operateOrgName;
	
	/**
	 * 操作部门name
	 */
	private String operateOrgCode;

	/**
	 * 变更内容
	 */
	private String rfcInfo;

	/**
	 * 变更申请人
	 */
	private String darfter;

	/**
	 * 更改受理时间
	 */
	private Date operateTime;

	private String startOrgCode;
	private String startOrgName;
	private String targetOrgCode;
	private String targetOrgName;
	private BigDecimal weight;
	private BigDecimal volume;
	private BigDecimal goodsQtyTotal;
	
	private String destChange;
	/**
	 * 纸包装
	 */
	private Integer paperNum;

	/**
	 * 木包装
	 */
	private Integer woodNum;

	/**
	 * 纤包装
	 */
	private Integer fibreNum;

	/**
	 * 托包装
	 */
	private Integer salverNum;

	/**
	 * 膜包装
	 */
	private Integer membraneNum;
	private String otherPackage;
	
	private String goodsPackage;
	
	/**
	 * 交接单编号
	 */
	private String handlerOverNo;
	
	/**
	 * 是否目的站变更
	 */
	private String isDestiChage;
	
	/**
	 * 配载单编号
	 */
	private String loadNo;
	private List<WaybillRfcChangeDetailEntity> changeDetail = new ArrayList<WaybillRfcChangeDetailEntity>();
	
	/**
	 * 流水号IdList
	 */
	private List<String> labelGoodIdsList;
	
	private List<String> handoverBillNoList;
	
	
	public List<String> getLabelGoodIdsList() {
		return labelGoodIdsList;
	}

	public void setLabelGoodIdsList(List<String> labelGoodIdsList) {
		this.labelGoodIdsList = labelGoodIdsList;
	}

	/**
	 * @return the goodsPackage
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * @param goodsPackage the goodsPackage to set
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * @return the changeDetail
	 */
	public List<WaybillRfcChangeDetailEntity> getChangeDetail() {
		return changeDetail;
	}

	/**
	 * @param changeDetail the changeDetail to set
	 */
	public void setChangeDetail(List<WaybillRfcChangeDetailEntity> changeDetail) {
		this.changeDetail = changeDetail;
	}

	/**
	 * @return the startOrgName
	 */
	public String getStartOrgName() {
		return startOrgName;
	}

	/**
	 * @param startOrgName the startOrgName to set
	 */
	public void setStartOrgName(String startOrgName) {
		this.startOrgName = startOrgName;
	}

	/**
	 * @return the targetOrgName
	 */
	public String getTargetOrgName() {
		return targetOrgName;
	}

	/**
	 * @param targetOrgName the targetOrgName to set
	 */
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}

	/**
	 * @return the startOrgCode
	 */
	public String getStartOrgCode() {
		return startOrgCode;
	}

	/**
	 * @param startOrgCode the startOrgCode to set
	 */
	public void setStartOrgCode(String startOrgCode) {
		this.startOrgCode = startOrgCode;
	}

	/**
	 * @return the targetOrgCode
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * @param targetOrgCode the targetOrgCode to set
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return the goodsQtyTotal
	 */
	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * @param goodsQtyTotal the goodsQtyTotal to set
	 */
	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	

	/**
	 * @return the paperNum
	 */
	public Integer getPaperNum() {
		return paperNum;
	}

	/**
	 * @param paperNum the paperNum to set
	 */
	public void setPaperNum(Integer paperNum) {
		this.paperNum = paperNum;
	}

	/**
	 * @return the woodNum
	 */
	public Integer getWoodNum() {
		return woodNum;
	}

	/**
	 * @param woodNum the woodNum to set
	 */
	public void setWoodNum(Integer woodNum) {
		this.woodNum = woodNum;
	}

	/**
	 * @return the fibreNum
	 */
	public Integer getFibreNum() {
		return fibreNum;
	}

	/**
	 * @param fibreNum the fibreNum to set
	 */
	public void setFibreNum(Integer fibreNum) {
		this.fibreNum = fibreNum;
	}

	/**
	 * @return the salverNum
	 */
	public Integer getSalverNum() {
		return salverNum;
	}

	/**
	 * @param salverNum the salverNum to set
	 */
	public void setSalverNum(Integer salverNum) {
		this.salverNum = salverNum;
	}

	/**
	 * @return the membraneNum
	 */
	public Integer getMembraneNum() {
		return membraneNum;
	}

	/**
	 * @param membraneNum the membraneNum to set
	 */
	public void setMembraneNum(Integer membraneNum) {
		this.membraneNum = membraneNum;
	}

	/**
	 * @return the otherPackage
	 */
	public String getOtherPackage() {
		return otherPackage;
	}

	/**
	 * @param otherPackage the otherPackage to set
	 */
	public void setOtherPackage(String otherPackage) {
		this.otherPackage = otherPackage;
	}

	/**
	 * @return the waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	/**
	 * @param waybillRfcId
	 *            the waybillRfcId to set
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the darftOrgName
	 */
	public String getDarftOrgName() {
		return darftOrgName;
	}

	/**
	 * @param darftOrgName
	 *            the darftOrgName to set
	 */
	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	/**
	 * @return the rfcInfo
	 */
	public String getRfcInfo() {
		return rfcInfo;
	}

	/**
	 * @param rfcInfo
	 *            the rfcInfo to set
	 */
	public void setRfcInfo(String rfcInfo) {
		this.rfcInfo = rfcInfo;
	}

	/**
	 * @return the darfter
	 */
	public String getDarfter() {
		return darfter;
	}

	/**
	 * @param darfter
	 *            the darfter to set
	 */
	public void setDarfter(String darfter) {
		this.darfter = darfter;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getDarftOrgCode() {
		return darftOrgCode;
	}

	public void setDarftOrgCode(String darftOrgCode) {
		this.darftOrgCode = darftOrgCode;
	}

	public String getHandleOrgName() {
		return handleOrgName;
	}

	public void setHandleOrgName(String handleOrgName) {
		this.handleOrgName = handleOrgName;
	}

	public String getHandleOrgCode() {
		return handleOrgCode;
	}

	public void setHandleOrgCode(String handleOrgCode) {
		this.handleOrgCode = handleOrgCode;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public String getHandlerOverNo() {
		return handlerOverNo;
	}

	public void setHandlerOverNo(String handlerOverNo) {
		this.handlerOverNo = handlerOverNo;
	}

	public String getLoadNo() {
		return loadNo;
	}

	public void setLoadNo(String loadNo) {
		this.loadNo = loadNo;
	}

	public String getDestChange() {
		return destChange;
	}

	public void setDestChange(String destChange) {
		this.destChange = destChange;
	}

	public String getIsDestiChage() {
		return isDestiChage;
	}

	public void setIsDestiChage(String isDestiChage) {
		this.isDestiChage = isDestiChage;
	}
	
	public List<String> getHandoverBillNoList() {
		return handoverBillNoList;
	}
	public void setHandoverBillNoList(List<String> handoverBillNoList) {
		this.handoverBillNoList = handoverBillNoList;
	}
}
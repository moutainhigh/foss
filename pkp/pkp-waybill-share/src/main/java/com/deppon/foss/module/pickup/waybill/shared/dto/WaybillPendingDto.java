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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillPendingDto.java
 * 
 * FILE NAME        	: WaybillPendingDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: waybillPendingDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.IntelligenceBillTimeGather;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;

/**
 * 运单暂存DTO
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-2 下午7:21:33
 */

public class WaybillPendingDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 4249869794618633871L;

	// ------以下是配合查询页面,增加的属性

	// 运单号/
	private String mixNo;
	
	// 订单号
	private String orderNo;

	// 开单开始时间
	private Date billStartTime;

	// 开单结束时间
	private Date billEndTime;

	// Foss提交开始时间
	private Date createStartTime;

	// Foss提交结束时间
	private Date createEndTime;

	// -------------------

	// 当前用户信息
	private CurrentInfo currentInfo;

	// 待处理运单基础
	private WaybillPendingEntity waybillPending;

	// 待处理运单费用明细
	private List<WaybillCHDtlPendingEntity> waybillChargeDtlPending;

	// 待处理运单折扣明细
	private List<WaybillDisDtlPendingEntity> waybillDisDtlPending;

	// 待处理运单付款明细
	private List<WaybillPaymentPendingEntity> waybillPaymentPending;

	// 待处理运单打木架信息
	private WoodenRequirePendingEntity woodenRequirePending;
	
	//优惠卷
    private CouponInfoDto couponInfoDto;
    //承运表信息
	private ActualFreightEntity actualFreightEntity;
	public ActualFreightEntity getActualFreightEntity() {
		return actualFreightEntity;
	}

	public void setActualFreightEntity(ActualFreightEntity actualFreightEntity) {
		this.actualFreightEntity = actualFreightEntity;
	}

	/**
	 * 开户银行
	 */
	private CusAccountEntity openBank;
	
	/**
	 * 是否暂存运单导入再暂存
	 */
	private Boolean isPendingImport;
	
	/**
	 * 是否快递
	 */
	private String isExpress;
	
	/**
	 * 运单类型
	 */
	private String waybillType;
	/**
	 * 是否有效
	 */
	private String active;
	

	/**
	 * 是否GUI提交方式
	 */
	private String isGuiSubmit;//是否GUI提交方式
	
	/**
	 * GUI标题来源  图片开单、营业部开单、集中开单
	 */
	private String guiTitleName;//GUI标题
	
	private List<LabeledGoodEntity> labeledGoodEntities;
	/**
	 * @return the mixNo .
	 */
	public String getMixNo() {
		return mixNo;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @param mixNo
	 *            the mixNo to set.
	 */
	public void setMixNo(String mixNo) {
		this.mixNo = mixNo;
	}

	/**
	 * @return the billStartTime .
	 */
	public Date getBillStartTime() {
		return billStartTime;
	}

	/**
	 * @param billStartTime
	 *            the billStartTime to set.
	 */
	public void setBillStartTime(Date billStartTime) {
		this.billStartTime = billStartTime;
	}

	/**
	 * @return the billEndTime .
	 */
	public Date getBillEndTime() {
		return billEndTime;
	}

	/**
	 * @param billEndTime
	 *            the billEndTime to set.
	 */
	public void setBillEndTime(Date billEndTime) {
		this.billEndTime = billEndTime;
	}

	/**
	 * @return the createStartTime .
	 */
	public Date getCreateStartTime() {
		return createStartTime;
	}

	/**
	 * @param createStartTime
	 *            the createStartTime to set.
	 */
	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	/**
	 * @return the createEndTime .
	 */
	public Date getCreateEndTime() {
		return createEndTime;
	}

	/**
	 * @param createEndTime
	 *            the createEndTime to set.
	 */
	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	/**
	 * @return the currentInfo .
	 */
	public CurrentInfo getCurrentInfo() {
		return currentInfo;
	}

	/**
	 * @param currentInfo
	 *            the currentInfo to set.
	 */
	public void setCurrentInfo(CurrentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}

	/**
	 * @return the waybillPending .
	 */
	public WaybillPendingEntity getWaybillPending() {
		return waybillPending;
	}

	/**
	 * @param waybillPending
	 *            the waybillPending to set.
	 */
	public void setWaybillPending(WaybillPendingEntity waybillPending) {
		this.waybillPending = waybillPending;
	}

	/**
	 * @return the waybillChargeDtlPending .
	 */
	public List<WaybillCHDtlPendingEntity> getWaybillChargeDtlPending() {
		return waybillChargeDtlPending;
	}

	/**
	 * @param waybillChargeDtlPending
	 *            the waybillChargeDtlPending to set.
	 */
	public void setWaybillChargeDtlPending(List<WaybillCHDtlPendingEntity> waybillChargeDtlPending) {
		this.waybillChargeDtlPending = waybillChargeDtlPending;
	}

	/**
	 * @return the waybillDisDtlPending .
	 */
	public List<WaybillDisDtlPendingEntity> getWaybillDisDtlPending() {
		return waybillDisDtlPending;
	}

	/**
	 * @param waybillDisDtlPending
	 *            the waybillDisDtlPending to set.
	 */
	public void setWaybillDisDtlPending(List<WaybillDisDtlPendingEntity> waybillDisDtlPending) {
		this.waybillDisDtlPending = waybillDisDtlPending;
	}

	/**
	 * @return the waybillPaymentPending .
	 */
	public List<WaybillPaymentPendingEntity> getWaybillPaymentPending() {
		return waybillPaymentPending;
	}

	/**
	 * @param waybillPaymentPending
	 *            the waybillPaymentPending to set.
	 */
	public void setWaybillPaymentPending(List<WaybillPaymentPendingEntity> waybillPaymentPending) {
		this.waybillPaymentPending = waybillPaymentPending;
	}

	/**
	 * @return the woodenRequirePending .
	 */
	public WoodenRequirePendingEntity getWoodenRequirePending() {
		return woodenRequirePending;
	}

	/**
	 * @param woodenRequirePending
	 *            the woodenRequirePending to set.
	 */
	public void setWoodenRequirePending(WoodenRequirePendingEntity woodenRequirePending) {
		this.woodenRequirePending = woodenRequirePending;
	}

	/**
	 * 获取 开户银行信息
	 */
	public CusAccountEntity getOpenBank() {
		return openBank;
	}

	/**
	 * 设置 开户银行信息
	 */
	public void setOpenBank(CusAccountEntity openBank) {
		this.openBank = openBank;
	}

	/**
	  * @return  the isPendingImport
	 */
	public Boolean getIsPendingImport() {
		return isPendingImport;
	}

	/**
	 * @param isPendingImport the isPendingImport to set
	 */
	public void setIsPendingImport(Boolean isPendingImport) {
		this.isPendingImport = isPendingImport;
	}

	/**
	  * @return  the couponInfoDto
	 */
	public CouponInfoDto getCouponInfoDto() {
		return couponInfoDto;
	}

	/**
	 * @param couponInfoDto the couponInfoDto to set
	 */
	public void setCouponInfoDto(CouponInfoDto couponInfoDto) {
		this.couponInfoDto = couponInfoDto;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	public List<LabeledGoodEntity> getLabeledGoodEntities() {
		return labeledGoodEntities;
	}
	public void setLabeledGoodEntities(List<LabeledGoodEntity> labeledGoodEntities) {
		this.labeledGoodEntities = labeledGoodEntities;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIsGuiSubmit() {
		return isGuiSubmit;
	}

	public void setIsGuiSubmit(String isGuiSubmit) {
		this.isGuiSubmit = isGuiSubmit;
	}

	public String getGuiTitleName() {
		return guiTitleName;
	}

	public void setGuiTitleName(String guiTitleName) {
		this.guiTitleName = guiTitleName;
	}
	
	/**
	 * @项目：智能开单项目
	 * @功能：保存IntelligenceBillTimeGather类
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19下午18:02
	 */
	private IntelligenceBillTimeGather ibtg;
	public IntelligenceBillTimeGather getIbtg() {
		return ibtg;
	}

	public void setIbtg(IntelligenceBillTimeGather ibtg) {
		this.ibtg = ibtg;
	}
}
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/NotifyCustomerDto.java
 * 
 * FILE NAME        	: NotifyCustomerDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 运单通知查询Dto
 * @author ibm-wangfei
 * @date Oct 15, 2012 3:29:58 PM
 */
public class NotifyCustomerDto extends WaybillEntity {

	private static final long serialVersionUID = 2879012036589978679L;
	/**
	 * 始发部门(集中开单的始发部门是当前部门所属外场的驻地营业部)
	 */
	private String startOrgCode;
	public String getStartOrgCode() {
		return startOrgCode;
	}
	public void setStartOrgCode(String startOrgCode) {
		this.startOrgCode = startOrgCode;
	}
	/**
	 * 库存状态
	 */
	private String stockStatus;
	/**
	 * 到达件数
	 */
	private Integer arriveGoodsQty;
	/**
	 * 库存件数
	 */
	private Integer stockQty;
	/**
	 * 在库天数
	 */
	private Integer storageDay;
	/**
	 * 运费
	 */
	private Long wayFee;
	/**
	 * 仓储费
	 */
	private BigDecimal storageCharge;
	/**
	 * 出发日期
	 */
	private Date departureTime;
	/**
	 * 到达日期
	 */
	private Date arriveTime;
	/**
	 * 上次通知日期
	 */
	private Date lastNotifiTime;
	/**
	 * 交接单号
	 */
	private String handoverNo;

	/**
	 * 通知情况
	 */
	private String noticeResult;
	/**
	 * 入库时间
	 */
	private Date inStockTime;
	/**
	 * 预计到达时间
	 */
	private Date planArriveTime;
	/**
	 * 交接件数
	 */
	private Integer handoverGoodsQty;
	/**
	 * 送货习惯
	 */
	private String receivingHabits;

	/**
	 * 查询方式
	 */
	private String selectType;

	/**
	 * 最后通知日期与当前日期的间隔天数
	 */
	private Long notificationTimeSpace;

	/**
	 * 最后通知日期
	 */
	private Date notificationTime;
	/**
	 * 逾期天数
	 */
	private Integer overdueDay;
	/**
	 * 异常类型
	 */
	private String exceptionType;
	/**
	 * 异常信息
	 */
	private String exceptionNotes;

	/**
	 * 通知信息
	 */
	private String noticeContent;
	/**
	 * 通知信息_语音
	 */
	private String noticeContentVoice;
	/**
	 * 车辆到达状态
	 */
	private String taskStatus;
	/**
	 * 客户资质（结算方式）
	 */
	private String customerQulification;
	/**
	 * 信用额度
	 */
	private BigDecimal creditAmount;
	/**
	 * 最后入库时间
	 */
	private Date lastInStockTime;
	/**
	 * 始发部门名称
	 */
	private String receiveOrgName;

	/**
	 * 客户是否有银行账户列表
	 */
	private String isBackFlg;
	
	/**
	 * 是否为网上支付
	 */
	private String isPay;
	
	/**
	 * 到付费用合计（到付金额 + 保管费金额）
	 */
	private BigDecimal totalToPayAmount;
	
	/**
	 * 运单状态
	 */
	private String waybillStatus;
	
	/**
	 * 是否语音通知成功
	 */
	private String isVoiceSuccess;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 车次号
	 */
	private String vehicleAssembleNo;
	
	/**
	 * 是否短信通知成功
	 */
	private String isSmsSuccess;
	/**
	 * 通知客户短信广告语
	 */
	private String smsSloganValue;
	/**
	 * 是否已经查询过广告
	 */
	private boolean isQuerySlogan;
	
	/**
	 * 默认的送货日期
	 */
	private Date deliverDate;
	/**
	 * 库位件数
	 */
	private Integer positionQty;
	/**
	 * 储运事项
	 */
	private String transportationRemark;
	/**
	 * 包装手续费
	 */
	private BigDecimal packageFee;
	/**
	 * 发货客户手机
	 */
	private String deliveryCustomerMobilephone;
	
	/***
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;
	
	/***
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 是否打印
	 */
	private String isPrinted;
	/**
	 * 处理类型:"PDA_ACTIVE"--PDA已补录 ，"PC_ACTIVE"--暂存已开单
	 */
	private String pendingType;
	private String rfcStatus;//更改单状态
	private String isNoticeSuccess;//是否通知成功过
	private String deliverRequire; //送货要求
	private Integer handoverBillState;//交接单状态 add by 329757
	private String goodsStatus;// add by 329757 货物状态
	private String nowAddress;//当前位置 add by 329757
	private String destOrgName;//新增到达部门名称 add by 329757
	private String destOrgCode;//新增到达部门code add by 329757
	private String notificationOrgCode;//新增通知部门 add by 329757
	private String arriveTimeStatus;//add by 329757 到达时间状态
	private Date actualArriveTime;//add by 329757 实际到达时间
	private Date preCustomerPickupTime;//add by 329757 承诺到达时间
	private String origOrgName;//add by 329757 当前部门
	public Integer getHandoverBillState() {
		return handoverBillState;
	}
	public void setHandoverBillState(Integer handoverBillState) {
		this.handoverBillState = handoverBillState;
	}
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public String getNowAddress() {
		return nowAddress;
	}
	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}
	public String getDestOrgName() {
		return destOrgName;
	}
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getNotificationOrgCode() {
		return notificationOrgCode;
	}
	public void setNotificationOrgCode(String notificationOrgCode) {
		this.notificationOrgCode = notificationOrgCode;
	}
	public String getArriveTimeStatus() {
		return arriveTimeStatus;
	}
	public void setArriveTimeStatus(String arriveTimeStatus) {
		this.arriveTimeStatus = arriveTimeStatus;
	}
	public Date getActualArriveTime() {
		return actualArriveTime;
	}
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}
	public String getOrigOrgName() {
		return origOrgName;
	}
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	/**
	 * 发货客户电话
	 */
	private String deliveryCustomerPhone;
	
	/**
	 * 收货客户地址备注
	 */
	private String receiveCustomerAddressNote;
	
	/**
	 * 开单付款方式虚拟字段隐藏不显示-239284
	 */
	private String paidMethodVir;
	
	/**
	 * 
	 * 到付且网上已支付状态; 如果成立，设置值为true 
	 * @author 239284-foss-xiedejie
	 * @date 2015-4-2 下午5:42:02
	 */
	private String isOrPayStatus; 
	/**
	 * 分批配载未到齐
	 * @return
	 */
	private String batchStowageNOtHere;
	
	/**
	 * 是否会展货
	 */
	private String isExhibitCargo;
	
	/**
	 * 特殊增值服务类型   add by 243921
	 */
	private String  specialValueAddedService;
	
	public String getIsVoiceSuccess() {
		return isVoiceSuccess;
	}

	public void setIsVoiceSuccess(String isVoiceSuccess) {
		this.isVoiceSuccess = isVoiceSuccess;
	}

	public String getIsSmsSuccess() {
		return isSmsSuccess;
	}

	public void setIsSmsSuccess(String isSmsSuccess) {
		this.isSmsSuccess = isSmsSuccess;
	}

	/**
	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer; 
	
	/**
	 * Gets the 收货人是否大客户.
	 *
	 * @return the 收货人是否大客户.
	 */
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	/**
	 * Sets the 收货人是否大客户.
	 *
	 * @param receiveBigCustomer the 收货人是否大客户.
	 */
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}
	
	/**
	 * 获取 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * 设置 库存状态.
	 *
	 * @param stockStatus the new 库存状态
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * 获取 库存件数.
	 *
	 * @return the 库存件数
	 */
	public Integer getStockQty() {
		return stockQty;
	}

	/**
	 * 设置 库存件数.
	 *
	 * @param stockQty the new 库存件数
	 */
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	/**
	 * 获取 运费.
	 *
	 * @return the 运费
	 */
	public Long getWayFee() {
		return wayFee;
	}

	/**
	 * 设置 运费.
	 *
	 * @param wayFee the new 运费
	 */
	public void setWayFee(Long wayFee) {
		this.wayFee = wayFee;
	}

	/**
	 * 获取 出发日期.
	 *
	 * @return the 出发日期
	 */
	public Date getDepartureTime() {
		return departureTime;
	}

	/**
	 * 设置 出发日期.
	 *
	 * @param departureTime the new 出发日期
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * 获取 到达日期.
	 *
	 * @return the 到达日期
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 到达日期.
	 *
	 * @param arriveTime the new 到达日期
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * 获取 上次通知日期.
	 *
	 * @return the 上次通知日期
	 */
	public Date getLastNotifiTime() {
		return lastNotifiTime;
	}

	/**
	 * 设置 上次通知日期.
	 *
	 * @param lastNotifiTime the new 上次通知日期
	 */
	public void setLastNotifiTime(Date lastNotifiTime) {
		this.lastNotifiTime = lastNotifiTime;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getHandoverNo() {
		return handoverNo;
	}

	/**
	 * 设置 交接单号.
	 *
	 * @param handoverNo the new 交接单号
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**
	 * 获取 入库时间.
	 *
	 * @return the 入库时间
	 */
	public Date getInStockTime() {
		return inStockTime;
	}

	/**
	 * 设置 入库时间.
	 *
	 * @param inStockTime the new 入库时间
	 */
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	/**
	 * 获取 预计到达时间.
	 *
	 * @return the 预计到达时间
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	/**
	 * 设置 预计到达时间.
	 *
	 * @param planArriveTime the new 预计到达时间
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 交接件数.
	 *
	 * @return the 交接件数
	 */
	public Integer getHandoverGoodsQty() {
		return handoverGoodsQty;
	}

	/**
	 * 设置 交接件数.
	 *
	 * @param handoverGoodsQty the new 交接件数
	 */
	public void setHandoverGoodsQty(Integer handoverGoodsQty) {
		this.handoverGoodsQty = handoverGoodsQty;
	}

	/**
	 * 获取 到达件数.
	 *
	 * @return the 到达件数
	 */
	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}

	/**
	 * 设置 到达件数.
	 *
	 * @param arriveGoodsQty the new 到达件数
	 */
	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}

	/**
	 * 获取 在库天数.
	 *
	 * @return the 在库天数
	 */
	public Integer getStorageDay() {
		return storageDay;
	}

	/**
	 * 设置 在库天数.
	 *
	 * @param storageDay the new 在库天数
	 */
	public void setStorageDay(Integer storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * 获取 查询方式.
	 *
	 * @return the 查询方式
	 */
	public String getSelectType() {
		return selectType;
	}

	/**
	 * 设置 查询方式.
	 *
	 * @param selectType the new 查询方式
	 */
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	/**
	 * 获取 送货习惯.
	 *
	 * @return the 送货习惯
	 */
	public String getReceivingHabits() {
		return receivingHabits;
	}

	/**
	 * 设置 送货习惯.
	 *
	 * @param receivingHabits the new 送货习惯
	 */
	public void setReceivingHabits(String receivingHabits) {
		this.receivingHabits = receivingHabits;
	}

	/**
	 * 获取 最后通知日期与当前日期的间隔天数.
	 *
	 * @return the 最后通知日期与当前日期的间隔天数
	 */
	public Long getNotificationTimeSpace() {
		return notificationTimeSpace;
	}

	/**
	 * 设置 最后通知日期与当前日期的间隔天数.
	 *
	 * @param notificationTimeSpace the new 最后通知日期与当前日期的间隔天数
	 */
	public void setNotificationTimeSpace(Long notificationTimeSpace) {
		this.notificationTimeSpace = notificationTimeSpace;
	}

	/**
	 * 获取 最后通知日期.
	 *
	 * @return the 最后通知日期
	 */
	public Date getNotificationTime() {
		return notificationTime;
	}

	/**
	 * 设置 最后通知日期.
	 *
	 * @param notificationTime the new 最后通知日期
	 */
	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}

	/**
	 * 获取 逾期天数.
	 *
	 * @return the 逾期天数
	 */
	public Integer getOverdueDay() {
		return overdueDay;
	}

	/**
	 * 设置 逾期天数.
	 *
	 * @param overdueDay the new 逾期天数
	 */
	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}

	/**
	 * 获取 异常类型.
	 *
	 * @return the 异常类型
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * 设置 异常类型.
	 *
	 * @param exceptionType the new 异常类型
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * 获取 异常信息.
	 *
	 * @return the 异常信息
	 */
	public String getExceptionNotes() {
		return exceptionNotes;
	}

	/**
	 * 设置 异常信息.
	 *
	 * @param exceptionNotes the new 异常信息
	 */
	public void setExceptionNotes(String exceptionNotes) {
		this.exceptionNotes = exceptionNotes;
	}

	/**
	 * 获取 通知信息.
	 *
	 * @return the 通知信息
	 */
	public String getNoticeContent() {
		return noticeContent;
	}

	/**
	 * 设置 通知信息.
	 *
	 * @param noticeContent the new 通知信息
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	/**
	 * 获取 仓储费.
	 *
	 * @return the 仓储费
	 */
	public BigDecimal getStorageCharge() {
		return storageCharge;
	}

	/**
	 * 设置 仓储费.
	 *
	 * @param storageCharge the new 仓储费
	 */
	public void setStorageCharge(BigDecimal storageCharge) {
		this.storageCharge = storageCharge;
	}

	/**
	 * 获取 车辆到达状态.
	 *
	 * @return the 车辆到达状态
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * 设置 车辆到达状态.
	 *
	 * @param taskStatus the new 车辆到达状态
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * 获取 客户资质（结算方式）.
	 *
	 * @return the 客户资质（结算方式）
	 */
	public String getCustomerQulification() {
		return customerQulification;
	}

	/**
	 * 设置 客户资质（结算方式）.
	 *
	 * @param customerQulification the new 客户资质（结算方式）
	 */
	public void setCustomerQulification(String customerQulification) {
		this.customerQulification = customerQulification;
	}

	/**
	 * 获取 信用额度.
	 *
	 * @return the 信用额度
	 */
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	/**
	 * 设置 信用额度.
	 *
	 * @param creditAmount the new 信用额度
	 */
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * 获取 最后入库时间.
	 *
	 * @return the 最后入库时间
	 */
	public Date getLastInStockTime() {
		return lastInStockTime;
	}

	/**
	 * 设置 最后入库时间.
	 *
	 * @param lastInStockTime the new 最后入库时间
	 */
	public void setLastInStockTime(Date lastInStockTime) {
		this.lastInStockTime = lastInStockTime;
	}

	/**
	 * 获取 始发部门名称.
	 *
	 * @return the 始发部门名称
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * 设置 始发部门名称.
	 *
	 * @param receiveOrgName the new 始发部门名称
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * 获取 客户是否有银行账户列表.
	 *
	 * @return the 客户是否有银行账户列表
	 */
	public String getIsBackFlg() {
		return isBackFlg;
	}

	/**
	 * 设置 客户是否有银行账户列表.
	 *
	 * @param isBackFlg the new 客户是否有银行账户列表
	 */
	public void setIsBackFlg(String isBackFlg) {
		this.isBackFlg = isBackFlg;
	}


	/**
	 * 获取 通知情况.
	 *
	 * @return the 通知情况
	 */
	public String getNoticeResult() {
		return noticeResult;
	}

	/**
	 * 设置 通知情况.
	 *
	 * @param noticeResult the new 通知情况
	 */
	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}
	
	@Override
	public String toString() {
		return "NotifyCustomerDto [stockStatus=" + stockStatus + ", arriveGoodsQty=" + arriveGoodsQty + ", stockQty=" + stockQty + ", storageDay=" + storageDay + ", wayFee=" + wayFee + ", storageCharge=" + storageCharge + ", departureTime="
				+ departureTime + ", arriveTime=" + arriveTime + ", lastNotifiTime=" + lastNotifiTime + ", handoverNo=" + handoverNo + ", noticeResult=" + noticeResult + ", inStockTime=" + inStockTime + ", planArriveTime=" + planArriveTime
				+ ", handoverGoodsQty=" + handoverGoodsQty + ", receivingHabits=" + receivingHabits + ", selectType=" + selectType + ", notificationTimeSpace=" + notificationTimeSpace + ", notificationTime=" + notificationTime + ", overdueDay="
				+ overdueDay + ", exceptionType=" + exceptionType + ", exceptionNotes=" + exceptionNotes + ", noticeContent=" + noticeContent + ", taskStatus=" + taskStatus + ", customerQulification=" + customerQulification + ", creditAmount="
				+ creditAmount + ", lastInStockTime=" + lastInStockTime + ", receiveOrgName=" + receiveOrgName + ", isBackFlg=" + isBackFlg + "]";
	}

	/**
	 * 获取 是否为网上支付.
	 *
	 * @return the 是否为网上支付
	 */
	public String getIsPay() {
		return isPay;
	}

	/**
	 * 设置 是否为网上支付.
	 *
	 * @param isPay the new 是否为网上支付
	 */
	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	/**
	 * 获取 到付费用合计（到付金额 + 保管费金额）.
	 *
	 * @return the 到付费用合计（到付金额 + 保管费金额）
	 */
	public BigDecimal getTotalToPayAmount() {
		return totalToPayAmount;
	}

	/**
	 * 设置 到付费用合计（到付金额 + 保管费金额）.
	 *
	 * @param totalToPayAmount the new 到付费用合计（到付金额 + 保管费金额）
	 */
	public void setTotalToPayAmount(BigDecimal totalToPayAmount) {
		this.totalToPayAmount = totalToPayAmount;
	}
	/**
	 * 获取 运单状态.
	 *
	 * @return the 运单状态
	 */
	public String getWaybillStatus() {
		return waybillStatus;
	}
	
	/**
	 * 获取 设置状态.
	 *
	 * @return the 设置状态
	 */
	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}

	public String getNoticeContentVoice() {
		return noticeContentVoice;
	}

	public void setNoticeContentVoice(String noticeContentVoice) {
		this.noticeContentVoice = noticeContentVoice;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getSmsSloganValue() {
		return smsSloganValue;
	}

	public void setSmsSloganValue(String smsSloganValue) {
		this.smsSloganValue = smsSloganValue;
	}

	public boolean isQuerySlogan() {
		return isQuerySlogan;
	}

	public void setQuerySlogan(boolean isQuerySlogan) {
		this.isQuerySlogan = isQuerySlogan;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	public Integer getPositionQty() {
		return positionQty;
	}

	public void setPositionQty(Integer positionQty) {
		this.positionQty = positionQty;
	}
	public String getTransportationRemark() {
		return transportationRemark;
	}
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}
	public BigDecimal getPackageFee() {
		return packageFee;
	}
	public void setPackageFee(BigDecimal packageFee) {
		this.packageFee = packageFee;
	}
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}
	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}
	public String getIsPrinted() {
		return isPrinted;
	}
	public String getPendingType() {
		return pendingType;
	}
	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}
	public String getRfcStatus() {
		return rfcStatus;
	}
	public void setRfcStatus(String rfcStatus) {
		this.rfcStatus = rfcStatus;
	}
	public String getIsNoticeSuccess() {
		return isNoticeSuccess;
	}
	public void setIsNoticeSuccess(String isNoticeSuccess) {
		this.isNoticeSuccess = isNoticeSuccess;
	}
	
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}
	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
	public String getPaidMethodVir() {
		return paidMethodVir;
	}
	public void setPaidMethodVir(String paidMethodVir) {
		this.paidMethodVir = paidMethodVir;
	}
	public String getIsOrPayStatus() {
		return isOrPayStatus;
	}
	public void setIsOrPayStatus(String isOrPayStatus) {
		this.isOrPayStatus = isOrPayStatus;
	}
	public String getDeliverRequire() {
		return deliverRequire;
	}
	
	public void setDeliverRequire(String deliverRequire) {
		this.deliverRequire = deliverRequire;
	}
	public String getBatchStowageNOtHere() {
		return batchStowageNOtHere;
	}
	public void setBatchStowageNOtHere(String batchStowageNOtHere) {
		this.batchStowageNOtHere = batchStowageNOtHere;
	}
	public String getIsExhibitCargo() {
		return isExhibitCargo;
	}
	public void setIsExhibitCargo(String isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}
	public String getSpecialValueAddedService() {
		return specialValueAddedService;
	}
	public void setSpecialValueAddedService(String specialValueAddedService) {
		this.specialValueAddedService = specialValueAddedService;
	}
	
}
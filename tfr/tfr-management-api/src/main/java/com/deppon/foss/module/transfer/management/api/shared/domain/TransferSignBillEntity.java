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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/TransferSignBillEntity.java
 *  
 *  FILE NAME          :TransferSignBillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 转货车签单entity
 * @author 099197-foss-liming
 * @date 2012-11-29 上午11:49:47
 */
public class TransferSignBillEntity extends BaseEntity{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -3268647208674764920L;

	/**
	 * id 
	 */
	private String id;
	
	/**
	 * 单据类型:
	 * 营业部单据:STORE
	 * 车队单据:MOTORCADE
	 */
	private String transferType;
	
	/**
	 * 交接单编号
	 */
	private String handOverNo;
	
	/**所属车队Code**/
	private String motorcadeCode;
	
	/**所属车队Name**/
	private String motorcadeName;
	
	/**
	 * 出发部门Code
	 */
	private String origOrgCode;

	/**
	 * 出发部门Name
	 */
	private String origOrgName;
	
	/**
	 * 目的部门Code
	 */
	private String destOrgCode;

	/**
	 * 目的部门Name
	 */
	private String destOrgName;

    /**
     * 签单编号
     */
    private String signBillNo;

    /**
     * 用车部门
     */
    private String useTruckOrgCode;

    /**
     * 用车部门名称
     */
    private String useTruckOrgName;
    
    /**
     * 营业部签字
     */
    private String storeSign;

    /**
     * 日期
     */
    private Date signBillDate;
    
    /**
     * 约定用车时间
     */
    private Date arrangeUseTruckDate;

    /**
     * 卸货结束时间
     */
    private Date unloadEndDate;

    /**
     * 出发时间
     */
    private Date origDate;

    /**
     * 到达时间
     */
    private Date destDate;

    /**
     * 司机
     */
    private String driverCode;

    /**
     * 司机姓名
     */
    private String driverName;

    /**
     * 是否带货
     */
    private String isFirstTransfer;

    /**
     * 车牌号
     */
    private String vehicleNo;

    /**
     * 车型
     */
    private String vehicleTypeLength;

    /**
     * 转货里程
     */
    private BigDecimal transferDistance;

    /**
     * 体积
     */
    private BigDecimal volume;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 用车时间
     */
    private BigDecimal useTruckDuration;

    /**
     * 转货提成
     */
    private BigDecimal transferRoyalty;

    /**
     * 币种
     */
    private String currencyCode; 
    
    /**
     * 用车费用划分    
     */
    private BigDecimal useTruckFee;
    
    
    /**
	 *车型 名称
	 */
	private String vehicleTypeLengthName; 
	
	/**
	 * 新增部门
	 */
	private String orgCode;
	
	/**
	 * 新增部门
	 */
	private String orgName;
	
	/**
	 * 获取 车型 名称.
	 *
	 * @return the 车型 名称
	 */
	public String getVehicleTypeLengthName() {
		return vehicleTypeLengthName;
	}

	/**
	 * 设置 车型 名称.
	 *
	 * @param vehicleTypeLengthName the new 车型 名称
	 */
	public void setVehicleTypeLengthName(String vehicleTypeLengthName) {
		this.vehicleTypeLengthName = vehicleTypeLengthName;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 签单编号.
	 *
	 * @return the 签单编号
	 */
	public String getSignBillNo() {
		return signBillNo;
	}

	/**
	 * 设置 签单编号.
	 *
	 * @param signBillNo the new 签单编号
	 */
	public void setSignBillNo(String signBillNo) {
		this.signBillNo = signBillNo;
	}

	/**
	 * 获取 用车部门.
	 *
	 * @return the 用车部门
	 */
	public String getUseTruckOrgCode() {
		return useTruckOrgCode;
	}

	/**
	 * 设置 用车部门.
	 *
	 * @param useTruckOrgCode the new 用车部门
	 */
	public void setUseTruckOrgCode(String useTruckOrgCode) {
		this.useTruckOrgCode = useTruckOrgCode;
	}

	/**
	 * 获取 用车部门名称.
	 *
	 * @return the 用车部门名称
	 */
	public String getUseTruckOrgName() {
		return useTruckOrgName;
	}

	/**
	 * 设置 用车部门名称.
	 *
	 * @param useTruckOrgName the new 用车部门名称
	 */
	public void setUseTruckOrgName(String useTruckOrgName) {
		this.useTruckOrgName = useTruckOrgName;
	}

	/**
	 * 获取 日期.
	 *
	 * @return the 日期
	 */
	public Date getSignBillDate() {
		return signBillDate;
	}

	/**
	 * 设置 日期.
	 *
	 * @param signBillDate the new 日期
	 */
	public void setSignBillDate(Date signBillDate) {
		this.signBillDate = signBillDate;
	}

	/**
	 * 获取 司机.
	 *
	 * @return the 司机
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 司机.
	 *
	 * @param driverCode the new 司机
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获取 司机姓名.
	 *
	 * @return the 司机姓名
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置 司机姓名.
	 *
	 * @param driverName the new 司机姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获取 是否第一个部门转货.
	 *
	 * @return the 是否第一个部门转货
	 */
	public String getIsFirstTransfer() {
		return isFirstTransfer;
	}

	/**
	 * 设置 是否第一个部门转货.
	 *
	 * @param isFirstTransfer the new 是否第一个部门转货
	 */
	public void setIsFirstTransfer(String isFirstTransfer) {
		this.isFirstTransfer = isFirstTransfer;
	}

	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车型.
	 *
	 * @return the 车型
	 */
	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}

	/**
	 * 设置 车型.
	 *
	 * @param vehicleTypeLength the new 车型
	 */
	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}

	/**
	 * 获取 转货里程.
	 *
	 * @return the 转货里程
	 */
	public BigDecimal getTransferDistance() {
		return transferDistance;
	}

	/**
	 * 设置 转货里程.
	 *
	 * @param transferDistance the new 转货里程
	 */
	public void setTransferDistance(BigDecimal transferDistance) {
		this.transferDistance = transferDistance;
	}

	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * 设置 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取 用车时间.
	 *
	 * @return the 用车时间
	 */
	public BigDecimal getUseTruckDuration() {
		return useTruckDuration;
	}

	/**
	 * 设置 用车时间.
	 *
	 * @param useTruckDuration the new 用车时间
	 */
	public void setUseTruckDuration(BigDecimal useTruckDuration) {
		this.useTruckDuration = useTruckDuration;
	}

	/**
	 * 获取 转货提成.
	 *
	 * @return the 转货提成
	 */
	public BigDecimal getTransferRoyalty() {
		return transferRoyalty;
	}

	/**
	 * 设置 转货提成.
	 *
	 * @param transferRoyalty the new 转货提成
	 */
	public void setTransferRoyalty(BigDecimal transferRoyalty) {
		this.transferRoyalty = transferRoyalty;
	}


	/**
	 * 获取 用车费用划分.
	 *
	 * @return the 用车费用划分
	 */
	public BigDecimal getUseTruckFee() {
		return useTruckFee;
	}

	/**
	 * 设置 用车费用划分.
	 *
	 * @param useTruckFee the new 用车费用划分
	 */
	public void setUseTruckFee(BigDecimal useTruckFee) {
		this.useTruckFee = useTruckFee;
	}

	/**
	 * 获取 币种.
	 *
	 * @return the 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置 币种.
	 *
	 * @param currencyCode the new 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**   
	 * transferType   
	 *   
	 * @return  the transferType   
	 */
	
	public String getTransferType() {
		return transferType;
	}

	/**   
	 * @param transferType the transferType to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	/**   
	 * handOverNo   
	 *   
	 * @return  the handOverNo   
	 */
	
	public String getHandOverNo() {
		return handOverNo;
	}

	/**   
	 * @param handOverNo the handOverNo to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setHandOverNo(String handOverNo) {
		this.handOverNo = handOverNo;
	}

	/**   
	 * origOrgCode   
	 *   
	 * @return  the origOrgCode   
	 */
	
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**   
	 * @param origOrgCode the origOrgCode to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**   
	 * origOrgName   
	 *   
	 * @return  the origOrgName   
	 */
	
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**   
	 * @param origOrgName the origOrgName to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**   
	 * destOrgCode   
	 *   
	 * @return  the destOrgCode   
	 */
	
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**   
	 * @param destOrgCode the destOrgCode to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**   
	 * destOrgName   
	 *   
	 * @return  the destOrgName   
	 */
	
	public String getDestOrgName() {
		return destOrgName;
	}

	/**   
	 * @param destOrgName the destOrgName to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**   
	 * storeSign   
	 *   
	 * @return  the storeSign   
	 */
	
	public String getStoreSign() {
		return storeSign;
	}

	/**   
	 * @param storeSign the storeSign to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setStoreSign(String storeSign) {
		this.storeSign = storeSign;
	}

	/**   
	 * arrangeUseTruckDate   
	 *   
	 * @return  the arrangeUseTruckDate   
	 */
	
	public Date getArrangeUseTruckDate() {
		return arrangeUseTruckDate;
	}

	/**   
	 * @param arrangeUseTruckDate the arrangeUseTruckDate to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setArrangeUseTruckDate(Date arrangeUseTruckDate) {
		this.arrangeUseTruckDate = arrangeUseTruckDate;
	}

	/**   
	 * unloadEndDate   
	 *   
	 * @return  the unloadEndDate   
	 */
	
	public Date getUnloadEndDate() {
		return unloadEndDate;
	}

	/**   
	 * @param unloadEndDate the unloadEndDate to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setUnloadEndDate(Date unloadEndDate) {
		this.unloadEndDate = unloadEndDate;
	}

	/**   
	 * origDate   
	 *   
	 * @return  the origDate   
	 */
	
	public Date getOrigDate() {
		return origDate;
	}

	/**   
	 * @param origDate the origDate to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setOrigDate(Date origDate) {
		this.origDate = origDate;
	}

	/**   
	 * destDate   
	 *   
	 * @return  the destDate   
	 */
	
	public Date getDestDate() {
		return destDate;
	}

	/**   
	 * @param destDate the destDate to set
	 * Date:2013-4-1下午5:48:57
	 */
	public void setDestDate(Date destDate) {
		this.destDate = destDate;
	}

	/**   
	 * motorcadeCode   
	 *   
	 * @return  the motorcadeCode   
	 */
	
	public String getMotorcadeCode() {
		return motorcadeCode;
	}

	/**   
	 * @param motorcadeCode the motorcadeCode to set
	 * Date:2013-4-2下午8:24:56
	 */
	public void setMotorcadeCode(String motorcadeCode) {
		this.motorcadeCode = motorcadeCode;
	}

	/**   
	 * motorcadeName   
	 *   
	 * @return  the motorcadeName   
	 */
	
	public String getMotorcadeName() {
		return motorcadeName;
	}

	/**   
	 * @param motorcadeName the motorcadeName to set
	 * Date:2013-4-2下午8:24:56
	 */
	public void setMotorcadeName(String motorcadeName) {
		this.motorcadeName = motorcadeName;
	}

	/**   
	 * orgCode   
	 *   
	 * @return  the orgCode   
	 */
	
	public String getOrgCode() {
		return orgCode;
	}

	/**   
	 * @param orgCode the orgCode to set
	 * Date:2013-5-7下午2:32:23
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**   
	 * orgName   
	 *   
	 * @return  the orgName   
	 */
	
	public String getOrgName() {
		return orgName;
	}

	/**   
	 * @param orgName the orgName to set
	 * Date:2013-5-7下午2:32:23
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
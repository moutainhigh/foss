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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirWaybillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 航空正单
 * @author 099197-foss-zhoudejun
 * @date 2012-10-22 下午3:00:39
 */
public class AirWaybillEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2501190323492848731L;
	
	/**
	 * 航空公司二字码
	 */
	private String airLineTwoletter;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	
	/**
	 * 始发站编码
	 */
	private String deptRegionCode;
	/**
	 * 始发站名称
	 */
	private String deptRegionName;
	/**
	 * 目的站编码
	 */
	private String arrvRegionCode;
	/**
	 * 目的站名称
	 */
	private String arrvRegionName;
	/**
	 * 配载类型
	 */
	private String airAssembleType;
	/**
	 * 到达网点编码
	 */
	private String destOrgCode;
	/**
	 * 到达网点名称             
	 */
	private String dedtOrgName;
	/**
	 * 收货人编码
	 */
	private String receiverCode;
	/**
	 * 收货人名称
	 */
	private String receiverName;
	/**
	 * 收货人电话
	 */
	private String receiverContactPhone;
	/**
	 * 结算事项
	 */
	private String accountItem;
	/**
	 * 填开代理
	 */
	private String billingAgency;
	/**
	 * 收货人地址
	 */
	private String receiverAddress;
	/**
	 * 储运事项
	 */
	private String storageItem;
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 航班日期
	 */
	private Date flightDate; 
	/**
	 * 起飞时间
	 */
	private Date takeOffTime;
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	/**
	 * 运价种类
	 */
	private String rateClass;
	/**
	 * 付款方式
	 */
	private String paymentType;
	/**
	 * 毛重
	 */
	private BigDecimal grossWeight;
	/**
	 * 计费重量
	 */
	private BigDecimal billingWeight;
	/**
	 * 运价
	 */
	private BigDecimal fee;
	/**
	 * 承运人/外发代理_编号
	 */
	private String agenctCode;
	/**
	 * 承运人/外发代理_名称
	 */
	private String agencyName;
	/**
	 * 声明价值
	 */
	private String declareValue;
	/**
	 * 商品代号
	 */
	private String itemCode;
	/**
	 * 件数
	 */
	private Integer goodsQty;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 航空运费
	 */
	private BigDecimal airFee;
	/**
	 * 附加费
	 */
	private BigDecimal extraFee;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 包装说明
	 */
	private String packageStruction;
	/**
	 * 地面运费
	 */
	private BigDecimal groundFee;
	/**
	 * 燃油附加税
	 */
	private BigDecimal fuelSurcharge;
	/**
	 * 运输保险
	 */
	private BigDecimal transportInsurance;
	/**
	 * 保险费
	 */
	private BigDecimal inseranceFee;
	/**
	 * 总金额
	 */
	private BigDecimal feeTotal;
	/**
	 * 费用说明
	 */
	private String feePlain;
	/**
	 * 制单费
	 */
	private BigDecimal billingFee;
	/**
	 * 托运人编号
	 */
	private String shipperCode;
	/**
	 * 托运人名称
	 */
	private String shipperName;
	/**
	 * 托运人地址
	 */
	private String shipperAddress;
	/**
	 * 托运人电话
	 */
	private String shipperContactPhone;
	/**
	 * 提货方式
	 */
	private String pickupType;
	/**
	 * 制单部门编号
	 */
	private String createOrgCode;
	/**
	 * 制单部门名称
	 */
	private String createOrgName;
	/**制单人部门编号
	 * 
	 */
	private String createUserCode;
	/**
	 * 制单人名称
	 */
	private String createUserName;
	/**
	 * 制单时间
	 */
	private Date createTime;
	/**
	 * 修改人编号
	 */
	private String modifyUserCode;
	/**
	 * 修改人名称
	 */
	private String modifyUserName;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 交接状态
	 */
	private String handoverState;
	/**
	 * 是否已做合大票
	 * 是-Y 否-N
	 */
	private String airPickState;
	/**
	 * 币种
	 */
	private String currencyCode;
	/**
	 * 是否付款
	 */
	private String  isNotPayment;
	/**
	 * 合票号
	 */
	private String jointTicketNo;
	/**
	 * 实飞时间
	 */
	private Date actualTakeOffTime;
	/**
	 * 实到时间
	 */
	private Date actualArriveTime;
	/**
	 * 跟踪状态
	 */
	private String trackState;
	
	/**
	 * 机场三字码
	 */
	private String airportCode;
    /**
     * 运输类型
     */
    private String transportType;
    
    /**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 运输性质名称
	 */
	private String productName;
	
	/**
	 * 航空正单明细包含运单号列表
	 */
	private List<String> detailWaybills;

    public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    /**
	 * 获取 航空公司二字码.
	 *
	 * @return the 航空公司二字码
	 */
	public String getAirLineTwoletter() {
		return airLineTwoletter;
	}
	
	/**
	 * 设置 航空公司二字码.
	 *
	 * @param airLineTwoletter the new 航空公司二字码
	 */
	public void setAirLineTwoletter(String airLineTwoletter) {
		this.airLineTwoletter = airLineTwoletter;
	}
	
	/**
	 * 获取 正单号.
	 *
	 * @return the 正单号
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	
	/**
	 * 设置 正单号.
	 *
	 * @param airWaybillNo the new 正单号
	 */
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	
	/**
	 * 获取 始发站编码.
	 *
	 * @return the 始发站编码
	 */
	public String getDeptRegionCode() {
		return deptRegionCode;
	}
	
	/**
	 * 设置 始发站编码.
	 *
	 * @param deptRegionCode the new 始发站编码
	 */
	public void setDeptRegionCode(String deptRegionCode) {
		this.deptRegionCode = deptRegionCode;
	}
	
	/**
	 * 获取 始发站名称.
	 *
	 * @return the 始发站名称
	 */
	public String getDeptRegionName() {
		return deptRegionName;
	}
	
	/**
	 * 设置 始发站名称.
	 *
	 * @param deptRegionName the new 始发站名称
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}
	
	/**
	 * 获取 目的站编码.
	 *
	 * @return the 目的站编码
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}
	
	/**
	 * 设置 目的站编码.
	 *
	 * @param arrvRegionCode the new 目的站编码
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
	
	/**
	 * 获取 目的站名称.
	 *
	 * @return the 目的站名称
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}
	
	/**
	 * 设置 目的站名称.
	 *
	 * @param arrvRegionName the new 目的站名称
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}
	
	/**
	 * 获取 配载类型.
	 *
	 * @return the 配载类型
	 */
	public String getAirAssembleType() {
		return airAssembleType;
	}
	
	/**
	 * 设置 配载类型.
	 *
	 * @param airAssembleType the new 配载类型
	 */
	public void setAirAssembleType(String airAssembleType) {
		this.airAssembleType = airAssembleType;
	}
	
	/**
	 * 获取 到达网点编码.
	 *
	 * @return the 到达网点编码
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * 设置 到达网点编码.
	 *
	 * @param destOrgCode the new 到达网点编码
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * 获取 到达网点名称.
	 *
	 * @return the 到达网点名称
	 */
	public String getDedtOrgName() {
		return dedtOrgName;
	}
	
	/**
	 * 设置 到达网点名称.
	 *
	 * @param dedtOrgName the new 到达网点名称
	 */
	public void setDedtOrgName(String dedtOrgName) {
		this.dedtOrgName = dedtOrgName;
	}
	
	/**
	 * 获取 收货人编码.
	 *
	 * @return the 收货人编码
	 */
	public String getReceiverCode() {
		return receiverCode;
	}
	
	/**
	 * 设置 收货人编码.
	 *
	 * @param receiverCode the new 收货人编码
	 */
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}
	
	/**
	 * 获取 收货人名称.
	 *
	 * @return the 收货人名称
	 */
	public String getReceiverName() {
		return receiverName;
	}
	
	/**
	 * 设置 收货人名称.
	 *
	 * @param receiverName the new 收货人名称
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	/**
	 * 获取 收货人电话.
	 *
	 * @return the 收货人电话
	 */
	public String getReceiverContactPhone() {
		return receiverContactPhone;
	}
	
	/**
	 * 设置 收货人电话.
	 *
	 * @param receiverContactPhone the new 收货人电话
	 */
	public void setReceiverContactPhone(String receiverContactPhone) {
		this.receiverContactPhone = receiverContactPhone;
	}
	
	/**
	 * 获取 结算事项.
	 *
	 * @return the 结算事项
	 */
	public String getAccountItem() {
		return accountItem;
	}
	
	/**
	 * 设置 结算事项.
	 *
	 * @param accountItem the new 结算事项
	 */
	public void setAccountItem(String accountItem) {
		this.accountItem = accountItem;
	}
	
	/**
	 * 获取 填开代理.
	 *
	 * @return the 填开代理
	 */
	public String getBillingAgency() {
		return billingAgency;
	}
	
	/**
	 * 设置 填开代理.
	 *
	 * @param billingAgency the new 填开代理
	 */
	public void setBillingAgency(String billingAgency) {
		this.billingAgency = billingAgency;
	}
	
	/**
	 * 获取 收货人地址.
	 *
	 * @return the 收货人地址
	 */
	public String getReceiverAddress() {
		return receiverAddress;
	}
	
	/**
	 * 设置 收货人地址.
	 *
	 * @param receiverAddress the new 收货人地址
	 */
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	
	/**
	 * 获取 储运事项.
	 *
	 * @return the 储运事项
	 */
	public String getStorageItem() {
		return storageItem;
	}
	
	/**
	 * 设置 储运事项.
	 *
	 * @param storageItem the new 储运事项
	 */
	public void setStorageItem(String storageItem) {
		this.storageItem = storageItem;
	}
	
	/**
	 * 获取 航班号.
	 *
	 * @return the 航班号
	 */
	public String getFlightNo() {
		return flightNo;
	}
	
	/**
	 * 设置 航班号.
	 *
	 * @param flightNo the new 航班号
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	/**
	 * 获取 航班日期.
	 *
	 * @return the 航班日期
	 */
	public Date getFlightDate() {
		return flightDate;
	}
	
	/**
	 * 设置 航班日期.
	 *
	 * @param flightDate the new 航班日期
	 */
	@DateFormat(formate="yyyy-MM-dd")
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	
	/**
	 * 获取 起飞时间.
	 *
	 * @return the 起飞时间
	 */
	public Date getTakeOffTime() {
		return takeOffTime;
	}
	
	/**
	 * 设置 起飞时间.
	 *
	 * @param takeOffTime the new 起飞时间
	 */
	@DateFormat
	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}
	
	/**
	 * 获取 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	
	/**
	 * 设置 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	@DateFormat
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	/**
	 * 获取 运价种类.
	 *
	 * @return the 运价种类
	 */
	public String getRateClass() {
		return rateClass;
	}
	
	/**
	 * 设置 运价种类.
	 *
	 * @param rateClass the new 运价种类
	 */
	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}
	
	/**
	 * 获取 付款方式.
	 *
	 * @return the 付款方式
	 */
	public String getPaymentType() {
		return paymentType;
	}
	
	/**
	 * 设置 付款方式.
	 *
	 * @param paymentType the new 付款方式
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	/**
	 * 获取 毛重.
	 *
	 * @return the 毛重
	 */
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}
	
	/**
	 * 设置 毛重.
	 *
	 * @param grossWeight the new 毛重
	 */
	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}
	
	/**
	 * 获取 计费重量.
	 *
	 * @return the 计费重量
	 */
	public BigDecimal getBillingWeight() {
		return billingWeight;
	}
	
	/**
	 * 设置 计费重量.
	 *
	 * @param billingWeight the new 计费重量
	 */
	public void setBillingWeight(BigDecimal billingWeight) {
		this.billingWeight = billingWeight;
	}
	
	/**
	 * 获取 承运人/外发代理_编号.
	 *
	 * @return the 承运人/外发代理_编号
	 */
	public String getAgenctCode() {
		return agenctCode;
	}
	
	/**
	 * 设置 承运人/外发代理_编号.
	 *
	 * @param agenctCode the new 承运人/外发代理_编号
	 */
	public void setAgenctCode(String agenctCode) {
		this.agenctCode = agenctCode;
	}
	
	/**
	 * 获取 承运人/外发代理_名称.
	 *
	 * @return the 承运人/外发代理_名称
	 */
	public String getAgencyName() {
		return agencyName;
	}
	
	/**
	 * 设置 承运人/外发代理_名称.
	 *
	 * @param agencyName the new 承运人/外发代理_名称
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	
	/**
	 * 获取 声明价值.
	 *
	 * @return the 声明价值
	 */
	public String getDeclareValue() {
		return declareValue;
	}

	/**
	 * 设置 声明价值.
	 *
	 * @param declareValue the new 声明价值
	 */
	public void setDeclareValue(String declareValue) {
		this.declareValue = declareValue;
	}

	/**
	 * 获取 商品代号.
	 *
	 * @return the 商品代号
	 */
	public String getItemCode() {
		return itemCode;
	}
	
	/**
	 * 设置 商品代号.
	 *
	 * @param itemCode the new 商品代号
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	/**
	 * 获取 件数.
	 *
	 * @return the 件数
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * 设置 件数.
	 *
	 * @param goodsQty the new 件数
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}
	
	/**
	 * 获取 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * 设置 货物名称.
	 *
	 * @param goodsName the new 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 获取 包装说明.
	 *
	 * @return the 包装说明
	 */
	public String getPackageStruction() {
		return packageStruction;
	}
	
	/**
	 * 设置 包装说明.
	 *
	 * @param packageStruction the new 包装说明
	 */
	public void setPackageStruction(String packageStruction) {
		this.packageStruction = packageStruction;
	}
	
	/**
	 * 获取 费用说明.
	 *
	 * @return the 费用说明
	 */
	public String getFeePlain() {
		return feePlain;
	}
	
	/**
	 * 设置 费用说明.
	 *
	 * @param feePlain the new 费用说明
	 */
	public void setFeePlain(String feePlain) {
		this.feePlain = feePlain;
	}
	
	/**
	 * 获取 托运人编号.
	 *
	 * @return the 托运人编号
	 */
	public String getShipperCode() {
		return shipperCode;
	}
	
	/**
	 * 设置 托运人编号.
	 *
	 * @param shipperCode the new 托运人编号
	 */
	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}
	
	/**
	 * 获取 托运人名称.
	 *
	 * @return the 托运人名称
	 */
	public String getShipperName() {
		return shipperName;
	}
	
	/**
	 * 设置 托运人名称.
	 *
	 * @param shipperName the new 托运人名称
	 */
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	
	/**
	 * 获取 托运人地址.
	 *
	 * @return the 托运人地址
	 */
	public String getShipperAddress() {
		return shipperAddress;
	}
	
	/**
	 * 设置 托运人地址.
	 *
	 * @param shipperAddress the new 托运人地址
	 */
	public void setShipperAddress(String shipperAddress) {
		this.shipperAddress = shipperAddress;
	}
	
	/**
	 * 获取 托运人电话.
	 *
	 * @return the 托运人电话
	 */
	public String getShipperContactPhone() {
		return shipperContactPhone;
	}
	
	/**
	 * 设置 托运人电话.
	 *
	 * @param shipperContactPhone the new 托运人电话
	 */
	public void setShipperContactPhone(String shipperContactPhone) {
		this.shipperContactPhone = shipperContactPhone;
	}
	
	/**
	 * 获取 提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getPickupType() {
		return pickupType;
	}
	
	/**
	 * 设置 提货方式.
	 *
	 * @param pickupType the new 提货方式
	 */
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}
	
	/**
	 * 获取 制单部门编号.
	 *
	 * @return the 制单部门编号
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	 * 设置 制单部门编号.
	 *
	 * @param createOrgCode the new 制单部门编号
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	 * 获取 制单部门名称.
	 *
	 * @return the 制单部门名称
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	
	/**
	 * 设置 制单部门名称.
	 *
	 * @param createOrgName the new 制单部门名称
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
	/**
	 * 获取 制单人部门编号.
	 *
	 * @return the 制单人部门编号
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	
	/**
	 * 设置 制单人部门编号.
	 *
	 * @param createUserCode the new 制单人部门编号
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	
	/**
	 * 获取 制单人名称.
	 *
	 * @return the 制单人名称
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置 制单人名称.
	 *
	 * @param createUserName the new 制单人名称
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取 制单时间.
	 *
	 * @return the 制单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 制单时间.
	 *
	 * @param createTime the new 制单时间
	 */
	@DateFormat
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取 修改人编号.
	 *
	 * @return the 修改人编号
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	
	/**
	 * 设置 修改人编号.
	 *
	 * @param modifyUserCode the new 修改人编号
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	
	/**
	 * 获取 修改人名称.
	 *
	 * @return the 修改人名称
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	
	/**
	 * 设置 修改人名称.
	 *
	 * @param modifyUserName the new 修改人名称
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	
	/**
	 * 获取 修改时间.
	 *
	 * @return the 修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	
	/**
	 * 设置 修改时间.
	 *
	 * @param modifyTime the new 修改时间
	 */
	@DateFormat
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * 获取 交接状态.
	 *
	 * @return the 交接状态
	 */
	public String getHandoverState() {
		return handoverState;
	}
	
	/**
	 * 设置 交接状态.
	 *
	 * @param handoverState the new 交接状态
	 */
	public void setHandoverState(String handoverState) {
		this.handoverState = handoverState;
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
	 * 获取 是否付款.
	 *
	 * @return the 是否付款
	 */
	public String getIsNotPayment() {
		return isNotPayment;
	}
	
	/**
	 * 设置 是否付款.
	 *
	 * @param isNotPayment the new 是否付款
	 */
	public void setIsNotPayment(String isNotPayment) {
		this.isNotPayment = isNotPayment;
	}
	
	/**
	 * 获取 合票号.
	 *
	 * @return the 合票号
	 */
	public String getJointTicketNo() {
		return jointTicketNo;
	}
	
	/**
	 * 设置 合票号.
	 *
	 * @param jointTicketNo the new 合票号
	 */
	public void setJointTicketNo(String jointTicketNo) {
		this.jointTicketNo = jointTicketNo;
	}
	
	/**
	 * 获取 实飞时间.
	 *
	 * @return the 实飞时间
	 */
	public Date getActualTakeOffTime() {
		return actualTakeOffTime;
	}
	
	/**
	 * 设置 实飞时间.
	 *
	 * @param actualTakeOffTime the new 实飞时间
	 */
	public void setActualTakeOffTime(Date actualTakeOffTime) {
		this.actualTakeOffTime = actualTakeOffTime;
	}
	
	/**
	 * 获取 实到时间.
	 *
	 * @return the 实到时间
	 */
	public Date getActualArriveTime() {
		return actualArriveTime;
	}
	
	/**
	 * 设置 实到时间.
	 *
	 * @param actualArriveTime the new 实到时间
	 */
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}
	
	/**
	 * 获取 跟踪状态.
	 *
	 * @return the 跟踪状态
	 */
	public String getTrackState() {
		return trackState;
	}
	
	/**
	 * 设置 跟踪状态.
	 *
	 * @param trackState the new 跟踪状态
	 */
	public void setTrackState(String trackState) {
		this.trackState = trackState;
	}
	
	/**
	 * 获取 运价.
	 *
	 * @return the 运价
	 */
	public BigDecimal getFee() {
		return fee;
	}
	
	/**
	 * 设置 运价.
	 *
	 * @param fee the new 运价
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
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
	 * 获取 航空运费.
	 *
	 * @return the 航空运费
	 */
	public BigDecimal getAirFee() {
		return airFee;
	}
	
	/**
	 * 设置 航空运费.
	 *
	 * @param airFee the new 航空运费
	 */
	public void setAirFee(BigDecimal airFee) {
		this.airFee = airFee;
	}
	
	/**
	 * 获取 附加费.
	 *
	 * @return the 附加费
	 */
	public BigDecimal getExtraFee() {
		return extraFee;
	}
	
	/**
	 * 设置 附加费.
	 *
	 * @param extraFee the new 附加费
	 */
	public void setExtraFee(BigDecimal extraFee) {
		this.extraFee = extraFee;
	}
	
	/**
	 * 获取 地面运费.
	 *
	 * @return the 地面运费
	 */
	public BigDecimal getGroundFee() {
		return groundFee;
	}
	
	/**
	 * 设置 地面运费.
	 *
	 * @param groundFee the new 地面运费
	 */
	public void setGroundFee(BigDecimal groundFee) {
		this.groundFee = groundFee;
	}
	
	/**
	 * 获取 燃油附加税.
	 *
	 * @return the 燃油附加税
	 */
	public BigDecimal getFuelSurcharge() {
		return fuelSurcharge;
	}
	
	/**
	 * 设置 燃油附加税.
	 *
	 * @param fuelSurcharge the new 燃油附加税
	 */
	public void setFuelSurcharge(BigDecimal fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}
	
	/**
	 * 获取 运输保险.
	 *
	 * @return the 运输保险
	 */
	public BigDecimal getTransportInsurance() {
		return transportInsurance;
	}
	
	/**
	 * 设置 运输保险.
	 *
	 * @param transportInsurance the new 运输保险
	 */
	public void setTransportInsurance(BigDecimal transportInsurance) {
		this.transportInsurance = transportInsurance;
	}
	
	/**
	 * 获取 保险费.
	 *
	 * @return the 保险费
	 */
	public BigDecimal getInseranceFee() {
		return inseranceFee;
	}
	
	/**
	 * 设置 保险费.
	 *
	 * @param inseranceFee the new 保险费
	 */
	public void setInseranceFee(BigDecimal inseranceFee) {
		this.inseranceFee = inseranceFee;
	}
	
	/**
	 * 获取 总金额.
	 *
	 * @return the 总金额
	 */
	public BigDecimal getFeeTotal() {
		return feeTotal;
	}
	
	/**
	 * 设置 总金额.
	 *
	 * @param feeTotal the new 总金额
	 */
	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}
	
	/**
	 * 获取 制单费.
	 *
	 * @return the 制单费
	 */
	public BigDecimal getBillingFee() {
		return billingFee;
	}
	
	/**
	 * 设置 制单费.
	 *
	 * @param billingFee the new 制单费
	 */
	public void setBillingFee(BigDecimal billingFee) {
		this.billingFee = billingFee;
	}
	/**
	 * 获取 机场编码.
	 *
	 * @return the 机场编码
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * 设置 机场编码.
	 *
	 * @param airportCode the new 机场编码
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAirPickState() {
		return airPickState;
	}

	public void setAirPickState(String airPickState) {
		this.airPickState = airPickState;
	}

	public List<String> getDetailWaybills() {
		return detailWaybills;
	}

	public void setDetailWaybills(List<String> detailWaybills) {
		this.detailWaybills = detailWaybills;
	}

}
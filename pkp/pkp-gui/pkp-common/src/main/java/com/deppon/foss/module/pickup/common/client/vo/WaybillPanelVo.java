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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/WaybillPanelVo.java
 * 
 * FILE NAME        	: WaybillPanelVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainNewEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerNewEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;

/**
 * 
 * 运单属性VO
 * 
 * @author 025000-FOSS-helong
 * @date 2012-12-24 下午08:54:36
 */
public class WaybillPanelVo {
	//350909       郭倩云       分拣结果
	private String sortingResult;
	public String getSortingResult() {
		return sortingResult;
	}

	public void setSortingResult(String sortingResult) {
		this.sortingResult = sortingResult;
	}
	/**
	 * 是否客户圈
	 * @param  isCustomerCircle
	 */
	private String isCircle;

	public String getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(String isCircle) {
		this.isCircle = isCircle;
	}

	/**
	 * 是否主客户
	 *@param isMainCustomer
	 *
	 */
	private String isMainCustomer;

	public String getIsMainCustomer() {
		return isMainCustomer;
	}

	public void setIsMainCustomer(String isMainCustomer) {
		this.isMainCustomer = isMainCustomer;
	}

	/**
	 * 主客户编码
	 */
	private String mainCustomerCode;

	public String getMainCustomerCode() {
		return mainCustomerCode;
	}

	public void setMainCustomerCode(String mainCustomerCode) {
		this.mainCustomerCode = mainCustomerCode;
	}

	/**
	 * 实际开单客户,从客户编码
	 */
	private String actualCustomerCode;

	public String getActualCustomerCode() {
		return actualCustomerCode;
	}

	public void setActualCustomerCode(String actualCustomerCode) {
		this.actualCustomerCode = actualCustomerCode;
	}

	/**
	 *实际开单客户统一计算,客户圈统一计算
	 */
	private String isFocusPay;

	public String getIsFocusPay() {
		return isFocusPay;
	}

	public void setIsFocusPay(String isFocusPay) {
		this.isFocusPay = isFocusPay;
	}

	/**
	 * 内部员工发货
	 * dp-foss-dongjialing
	 * 225131
	 */
	//内部发货
	private DataDictionaryValueVo internalDeliveryType;
	//工号
	private String employeeNo;
	//特殊增值服务
	private DataDictionaryValueVo specialValueAddedServiceType;
	/**
	 * 安装费明细 
	 */
    private List<InstallationEntity> installationEntityList;
	/**
	 * 是否修改了发货客户---206860
	 */
	private String isUpdateDeliveryCustomer; 
		
	public String getIsUpdateDeliveryCustomer() {
		return isUpdateDeliveryCustomer;
	}

	public void setIsUpdateDeliveryCustomer(String isUpdateDeliveryCustomer) {
		this.isUpdateDeliveryCustomer = isUpdateDeliveryCustomer;
	}
	
	//保费是否经行后台折扣---206860
	private boolean isCalDiscount;	
	public boolean isCalDiscount() {
		return isCalDiscount;
	}

	public void setCalDiscount(boolean isCalDiscount) {
		this.isCalDiscount = isCalDiscount;
	}

	//折让方式-装卸费
	private  BigDecimal dcServicefee;
	
	public BigDecimal getDcServicefee() {
		return dcServicefee;
	}

	public void setDcServicefee(BigDecimal dcServicefee) {
		this.dcServicefee = dcServicefee;
	}

	//报价费率范围---206860
	private String insuranceRateRange;
	

	// 保价费率---206860
	private String insuranceRateCanvas;
	
	// 保价费率范围---206860
	private String insuranceRateRangeCanvas;
	
	/**
	 * 是否GUI提交方式
	 */
	private String isGuiSubmit;//是否GUI提交方式
	
	/**
	 * GUI标题来源  图片开单、营业部开单、集中开单
	 */
	private String guiTitleName;//GUI标题

	public String getInsuranceRateCanvas() {
		return insuranceRateCanvas;
	}

	public void setInsuranceRateCanvas(String insuranceRateCanvas) {
		this.insuranceRateCanvas = insuranceRateCanvas;
	}

	public String getInsuranceRateRangeCanvas() {
		return insuranceRateRangeCanvas;
	}

	public void setInsuranceRateRangeCanvas(String insuranceRateRangeCanvas) {
		this.insuranceRateRangeCanvas = insuranceRateRangeCanvas;
	}

	public String getInsuranceRateRange() {
		return insuranceRateRange;
	}

	public void setInsuranceRateRange(String insuranceRateRange) {
		this.insuranceRateRange = insuranceRateRange;
	}
	

	public DataDictionaryValueVo getInternalDeliveryType() {
		return internalDeliveryType;
	}

	public void setInternalDeliveryType(
			DataDictionaryValueVo internalDeliveryType) {
		this.internalDeliveryType = internalDeliveryType;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	/**
	 * 始发客户是否统一结算
	 */
	private String startCentralizedSettlement;

	/**
	 * 到达客户是否统一结算
	 */
	private String arriveCentralizedSettlement;

	/**
	 * 始发客户合同部门编码
	 */
	private String startContractOrgCode;

	/**
	 * 到达客户合同部门编码
	 */
	private String arriveContractOrgCode;

	/**
	 * 始发客户合同上催款部门编码
	 */
	private String startReminderOrgCode;

	/**
	 * 到达客户合同上催款部门编码
	 */
	private String arriveReminderOrgCode;

	/**
	 * 始发客户合同部门名称
	 */
	private String startContractOrgName;

	/**
	 * 到达客户合同部门名称
	 */
	private String arriveContractOrgName;
	
	/**
	 * 是否修改活动
	 */
	private boolean isModifyMark;	
	/**
	 * 是否批量开单
	 * dp-foss-dongjialing
	 * 225131
	 */
	private String isBatchCreateWaybill = "N";
	
	/**
	 * 是否在客户圈内
	 */
	private String isCustCircle;
	
	/**
	 * 合同信息
	 */
	private CusBargainNewEntity cusBargainNewEntity;
	
	/**
	 * 客户圈信息
	 * @return
	 */
	private CustomerCircleEntity customerCircleEntity;
	
	/**
	 * 客户信息
	 */
	private CustomerNewEntity customerNewEntity;

	public String getIsCustCircle() {
		return isCustCircle;
	}

	public void setIsCustCircle(String isCustCircle) {
		this.isCustCircle = isCustCircle;
	}

	public CusBargainNewEntity getCusBargainNewEntity() {
		return cusBargainNewEntity;
	}

	public void setCusBargainNewEntity(CusBargainNewEntity cusBargainNewEntity) {
		this.cusBargainNewEntity = cusBargainNewEntity;
	}

	public CustomerCircleEntity getCustomerCircleEntity() {
		return customerCircleEntity;
	}

	public void setCustomerCircleEntity(CustomerCircleEntity customerCircleEntity) {
		this.customerCircleEntity = customerCircleEntity;
	}

	public CustomerNewEntity getCustomerNewEntity() {
		return customerNewEntity;
	}

	public void setCustomerNewEntity(CustomerNewEntity customerNewEntity) {
		this.customerNewEntity = customerNewEntity;
	}

	public String getIsBatchCreateWaybill() {
		return isBatchCreateWaybill;
	}

	public void setIsBatchCreateWaybill(String isBatchCreateWaybill) {
		this.isBatchCreateWaybill = isBatchCreateWaybill;
	}

	public boolean isModifyMark() {
		return isModifyMark;
	}

	public void setModifyMark(boolean isModifyMark) {
		this.isModifyMark = isModifyMark;
	}
	
	/**
	 * 开始开单时间
	 */
	private Date startBillTime;	
	/**
	 * 是否快递标志
	 * 便于增加产品
	 */
	private String isExpress;//是否快递标志
	
	public Date getStartBillTime() {
		return startBillTime;
	}

	public void setStartBillTime(Date startBillTime) {
		this.startBillTime = startBillTime;
	}
	
	/**
	 * 标示是否计算了总运费
	 */
	private String flagCalFee;	
	
	public String getFlagCalFee() {
		return flagCalFee;
	}

	public void setFlagCalFee(String flagCalFee) {
		this.flagCalFee = flagCalFee;
	}

/**

	 * 是否经济自提件
	 */
	private Boolean isEconomyGoods;
	/**
	 * 经济自提件类型
	 */
	private DataDictionaryValueVo economyGoodsType;	
	
	public Boolean getIsEconomyGoods() {
		return isEconomyGoods;
	}

	public void setIsEconomyGoods(Boolean isEconomyGoods) {
		this.isEconomyGoods = isEconomyGoods;
	}
	
	public DataDictionaryValueVo getEconomyGoodsType() {
		return economyGoodsType;
	}

	public void setEconomyGoodsType(DataDictionaryValueVo economyGoodsType) {
		this.economyGoodsType = economyGoodsType;
	}
	// 记录上次的空运运费冲减
	private BigDecimal oldKyyfcjFee;
	public BigDecimal getOldKyyfcjFee() {
		return oldKyyfcjFee;
	}
	public void setOldKyyfcjFee(BigDecimal oldKyyfcjFee) {
		this.oldKyyfcjFee = oldKyyfcjFee;
	}

	/**
	 * 是否计算了公布价
	 */
	private String isCalTraFee;

	public String getIsCalTraFee() {
		return isCalTraFee;
	}

	public void setIsCalTraFee(String isCalTraFee) {
		this.isCalTraFee = isCalTraFee;
	}
	
	/**
	 * 手动添加的其他费用
	 */
	private List<OtherChargeVo> manualFeeList;
	public List<OtherChargeVo> getManualFeeList() {
		return manualFeeList;
	}
	public void setManualFeeList(List<OtherChargeVo> manualFeeList) {
		this.manualFeeList = manualFeeList;
	}
	
	/**
	 * 标示超远派送加收是否已累加
	 */
	private String falgCcddjsFee;
	/**
	 * 标示对外投保保费收入是否已累加
	 */
	private String falgDwtbfFee;
	/**
	 * 标示空运运费冲减是否已累加
	 */
	private String falgKyyfcjFee;
	
	/**
	 * 标示其他费用中超远派送加收是否已减去
	 */
	private String falgOtherFeeForCcddjsFee;
	/**
	 * 标示其他费用中对外投保保费收入是否已减去
	 */
	private String falgOtherFeeForDwtbfFee;
	/**
	 * 标示其他费用中空运运费冲减是否已减去
	 */
	private String falgOtherFeeForKyyfcjFee;

	public String getFalgOtherFeeForCcddjsFee() {
		return falgOtherFeeForCcddjsFee;
	}
	
	public void setFalgOtherFeeForCcddjsFee(String falgOtherFeeForCcddjsFee) {
		this.falgOtherFeeForCcddjsFee = falgOtherFeeForCcddjsFee;
	}

	
	public String getFalgOtherFeeForDwtbfFee() {
		return falgOtherFeeForDwtbfFee;
	}

	
	public void setFalgOtherFeeForDwtbfFee(String falgOtherFeeForDwtbfFee) {
		this.falgOtherFeeForDwtbfFee = falgOtherFeeForDwtbfFee;
	}

	
	public String getFalgOtherFeeForKyyfcjFee() {
		return falgOtherFeeForKyyfcjFee;
	}

	
	public void setFalgOtherFeeForKyyfcjFee(String falgOtherFeeForKyyfcjFee) {
		this.falgOtherFeeForKyyfcjFee = falgOtherFeeForKyyfcjFee;
	}

	public String getFalgCcddjsFee() {
		return falgCcddjsFee;
	}
	
	public void setFalgCcddjsFee(String falgCcddjsFee) {
		this.falgCcddjsFee = falgCcddjsFee;
	}
	
	public String getFalgDwtbfFee() {
		return falgDwtbfFee;
	}
	
	public void setFalgDwtbfFee(String falgDwtbfFee) {
		this.falgDwtbfFee = falgDwtbfFee;
	}
	
	public String getFalgKyyfcjFee() {
		return falgKyyfcjFee;
	}
	
	public void setFalgKyyfcjFee(String falgKyyfcjFee) {
		this.falgKyyfcjFee = falgKyyfcjFee;
	}

	// ---------------------控件属性
	// 纸
	private Integer paper;

	// 木
	private Integer wood;
	private Integer oldWood;
	

	// 纤
	private Integer fibre;

	// 托
	private Integer salver;

	// 膜
	private Integer membrane;

	// 其他
	private String otherPackage;

	// 后期加入
	// 手写金额
	private BigDecimal handWriteMoney;

	// 代打木架
	private WoodYokeVo yokeVo;

	// 储运事项
	private String transportationRemark;
	
	// 外部订单导入的储运事项
	private String transportationRemarkOfWebOrder;

	// 长途短途
	private String longOrShort;

	// 画布中需要新增的属性
	// 公布价运费
	private String transportFeeCanvas;

	// 保价声明
	private String insuranceAmountCanvas;

	// 代收货款
	private String codAmountCanvas;

	// 接货费
	private String pickUpFeeCanvas;

	// 送货费
	private String deliveryGoodsFeeCanvas;

	// 包装费
	private String packageFeeCanvas;

	// 装卸费
	private String serviceFeeCanvas;

	// 其他费用
	private String otherFeeCanvas;

	// 优惠费用
	private String promotionsFeeCanvas;

	// 总费用
	private String totalFeeCanvas;
	
	//其他费用集合
	private List<OtherChargeVo> allChargeList;

	// ----------实体属性
	// 运单实体ID
	private String id;

	// 运单号
	private String waybillNo;
	
	//导入的运单号
	private String waybillNoImported;

	// 订单号
	private String orderNo;
	
	// 订单来源
	private String orderChannel;
	
	// 订单付款方式
	private String orderPayment;

	// 结算付款(是否月结)
	private Boolean chargeMode;
	
	/**
	 * 优惠类型.
	 */
	private String preferentialType;
	
	//合同编号
    private String auditNo;

	// 发货客户ID
	private String deliveryCustomerId;

	/** 
     * 精准代收liyongfei-DMANA-2352
     */
    private String accurateCollection;
    
  //liyongfei 新增计费方式
    private  String caculateType;
    
	// 发货客户编码
	private String deliveryCustomerCode;
	/**
	 * 发货客户是否是大客户
	 */
	private String deliveryBigCustomer;

	// 发货客户名称
	private String deliveryCustomerName;

	// 发货客户手机
	private String deliveryCustomerMobilephone;

	// 发货客户电话
	private String deliveryCustomerPhone;

	// 发货客户联系人
	private String deliveryCustomerContact;

	// 发货客户联系人ID
	private String deliveryCustomerContactId;

	// 发货国家
	private String deliveryCustomerNationCode;

	// 发货省份
	private String deliveryCustomerProvCode;

	// 发货市
	private String deliveryCustomerCityCode;

	// 发货区
	private String deliveryCustomerDistCode;

	// 发货具体地址
	private String deliveryCustomerAddress;
	
	//发票标记
	private String invoice;
	private String invoiceTab;
	
	public String getInvoiceTab() {
		return invoiceTab;
	}

	public void setInvoiceTab(String invoiceTab) {
		this.invoiceTab = invoiceTab;
	}

	/**
	 * 短信标识
	 */
	private String isSMS ;
	
	/**
	 * 是否校验预警线路
	 */
	private boolean checkloadLineName;
	
	public String getIsSMS() {
		return isSMS;
	}

	public void setIsSMS(String isSMS) {
		this.isSMS = isSMS;
	}

	public String getAccurateCollection() {
		return accurateCollection;
	}

	public void setAccurateCollection(String accurateCollection) {
		this.accurateCollection = accurateCollection;
	}

	/**
	 * 发货人区域
	 */
	private String deliveryCustomerArea;

	/**
	 * 发货人省市区(数据绑定用)
	 */
	private AddressFieldDto deliveryCustomerAreaDto;

	// 收货客户ID
	private String receiveCustomerId;

	// 收货客户编码
	private String receiveCustomerCode;
	/**
	 * 收货客户是否是大客户
	 */
	private String receiveBigCustomer;

	// 收货客户名称
	private String receiveCustomerName;

	// 收货客户手机
	private String receiveCustomerMobilephone;

	// 收货客户电话
	private String receiveCustomerPhone;

	// 收货客户联系人
	private String receiveCustomerContact;

	// 收货客户联系人ID
	private String receiveCustomerContactId;

	// 收货国家
	private String receiveCustomerNationCode;

	// 收货省份
	private String receiveCustomerProvCode;

	// 收货市
	private String receiveCustomerCityCode;

	// 收货区
	private String receiveCustomerDistCode;

	// 收货具体地址
	private String receiveCustomerAddress;
	
	/**
	 * 接送货地址ID
	 */
	private String contactAddressId;

	/**
	 * 收货客户区域
	 */
	private String receiveCustomerArea;
	/**
	 * 收货人省市区(数据绑定用)
	 */
	private AddressFieldDto receiveCustomerAreaDto;

	//收货部门开业时间
	private Date receiveOrgCreateTime;
	
	// 收货部门
	private String receiveOrgCode;

	// 收货部门
	private String receiveOrgName;
	//收货部门省份编码
	private String receiveOrgProvCode;
	// 运输性质
	private ProductEntityVo productCode;

	// 提货方式
	private DataDictionaryValueVo receiveMethod;
	
	//发票标记类型
	
	private DataDictionaryValueVo invoiceMode;
	/**
	 * 提货方式标记-标记是否内部带货
	 */
	private String receiveMethodFlag;
	
	//内部带货费用承担部门
	private String innerPickupFeeBurdenDept;

	// 提货网点
	private BranchVo customerPickupOrgCode;

	// 提货网点名称
	private String customerPickupOrgName;

	// 配载类型
	private String loadMethod;

	// 目的站
	private String targetOrgCode;

	// 是否上门接货
	private Boolean pickupToDoor;

	// 司机
	private String driverCode;

	// 是否集中接货
	private Boolean pickupCentralized;
	
	//集中接货开单组所在外场的驻地营业部编码
	private String pickupCentralizedDeptCode;

	// 配载线路编码
	private String loadLineCode;

	// 配载线路名称
	private String loadLineName;

	// 配载部门编号
	private String loadOrgCode;

	// 配载部门名称
	private String loadOrgName;

	// 最终配载部门编号
	private String lastLoadOrgCode;

	// 最终配载部门名称
	private String lastLoadOrgName;

	// 预计出发时间
	private Date preDepartureTime;

	// 预计派送/提货时间
	private Date preCustomerPickupTime;

	// 是否大车直送
	private Boolean carDirectDelivery;
	// 是否大票货
	private Boolean bigTicket;

	// 货物名称
	private String goodsName;

	// 货物总件数
	private Integer goodsQtyTotal;

	// 货物总重量
	private BigDecimal goodsWeightTotal;

	// 货物总体积
	private BigDecimal goodsVolumeTotal;

	// 货物尺寸
	private String goodsSize;

	// 货物类型
	private String goodsType;
	
	//线路是否能开AB货
//	private Boolean goodsTypeIsAB;

	/**
	 * 空运货物类型
	 */
	private DataDictionaryValueVo airGoodsType;

	// 是否贵重物品
	private Boolean preciousGoods;

	// 是否异形物品
	private Boolean specialShapedGoods;

	// 对外备注
	private DataDictionaryValueVo outerNotes;

	// 对内备注
	private String innerNotes;
	
	// 对内备注数据备份
	private String innerNotesBack;
	
	//包装备注
	private String packageRemark;
	
	// 货物包装
	private String goodsPackage;

	// 保险费ID
	private String insuranceId;

	// 保险费code
	private String insuranceCode;

	// 保价声明价值
	private BigDecimal insuranceAmount;

	// 保价费率
	private BigDecimal insuranceRate;
	
	//是否现金
	private Boolean cashPayFlag;
	
	/**
	 * 是否免费接货
	 */
	private Boolean freePickupGoods = false;
	
	 public Boolean getFreePickupGoods() {
		return freePickupGoods;
	}

	public void setFreePickupGoods(Boolean freePickupGoods) {
		this.freePickupGoods = freePickupGoods;
	}

	public Boolean getCashPayFlag() {
		return cashPayFlag;
	}

	public void setCashPayFlag(Boolean cashPayFlag) {
		this.cashPayFlag = cashPayFlag;
	}
	 /**
     * 是否可以修改
     */
    private String canmodify;
    
    /**
     * 最低保价费率
     */
    private BigDecimal minFeeRate;
    
    /**
     * 最高保价费率
     */
    private BigDecimal maxFeeRate;
    
    /**
	 * 运单流水号
	 * zxy 20131118 ISSUE-4391
	 */
	private List<LabeledGoodEntity> labeledGoodEntities;
    

	public Boolean getBigTicket() {
		return bigTicket;
	}

	public void setBigTicket(Boolean bigTicket) {
		this.bigTicket = bigTicket;
	}

	public String getCanmodify() {
		return canmodify;
	}

	public void setCanmodify(String canmodify) {
		this.canmodify = canmodify;
	}

	public BigDecimal getMinFeeRate() {
		return minFeeRate;
	}

	public void setMinFeeRate(BigDecimal minFeeRate) {
		this.minFeeRate = minFeeRate;
	}

	public BigDecimal getMaxFeeRate() {
		return maxFeeRate;
	}

	public void setMaxFeeRate(BigDecimal maxFeeRate) {
		this.maxFeeRate = maxFeeRate;
	}

	// 保价费
	private BigDecimal insuranceFee;
	
	//限保物品虚拟CODE
	private String virtualCode;

	// 保额最大值
	private BigDecimal maxInsuranceAmount;

	// 保价费最低一票
	private BigDecimal mixInsuranceAmount;

	// 代收货款ID
	private String codId;

	// 代收货款Code
	private String codCode;

	// 代收货款
	private BigDecimal codAmount;

	// 代收费率
	private BigDecimal codRate;

	// 代收货款手续费
	private BigDecimal codFee;
	
	//是否能开代收货款
	private String canAgentCollected;
	
	//是否可以货到付款
	private String arriveCharge;

	// 退款类型
	private DataDictionaryValueVo refundType;

	// 返单类别
	private DataDictionaryValueVo returnBillType;

	// 返单费用名称
	private String returnBillChargeName;

	// 预付费保密
	private Boolean secretPrepaid;

	// 到付金额
	private BigDecimal toPayAmount;

	// 预付金额
	private BigDecimal prePayAmount;

	// 送货费
	private BigDecimal deliveryGoodsFee;
	
	//送货费集合（ 送货费、送货进仓费、超远派送费、送货上楼费）
	private List<DeliverChargeEntity> deliverList; 
	
	
	//公里数
	private BigDecimal kilometer;
	
	// 送货进仓费名称
	private String deliveryStorgeName;

	// 其他费用
	private BigDecimal otherFee;

	// 包装手续费
	private BigDecimal packageFee;
	
	// 计算运费获得的包装费
	private BigDecimal calculatedPackageFee;
	
	// 通过计算得到的送货费
	private BigDecimal calculateDeliveryGoodsFee;
	
	// 送货费上限
	private BigDecimal maxDeliveryGoodsFee;
	
	// 优惠费用
	private BigDecimal promotionsFee;

	// 运费计费类型
	private DataDictionaryValueVo billingType;

	// 运费计费费率
	private BigDecimal unitPrice;
	
	// 公布价运费ID
	private String transportFeeId;
	
	// 公布价运费Code
	private String transportFeeCode;

	// 公布价运费
	private BigDecimal transportFee;
	
	// 最低一票费用
	private BigDecimal minTransportFee;

	// 折扣
	private BigDecimal discount;
	
	// 快递续重折扣率
	private BigDecimal expressContinuediscount;

	// 增值费用
	private BigDecimal valueAddFee;

	// 开单付款方式
	private DataDictionaryValueVo paidMethod;

	// 到达类型
	private String arriveType;

	// 运单状态
	private String active;

	// 禁行
	private String forbiddenLine;

	// 合票方式
	private DataDictionaryValueVo freightMethod;

	// 航班类型
	private DataDictionaryValueVo flightNumberType;

	// 航班时间
	private String flightShift;

	// 总费用
	private BigDecimal totalFee;

	// 优惠编码
	private String promotionsCode;

	// 生效日期
	private Date beginTime;

	// 结束日期
	private Date endTime;

	// 开单时间
	private Date createTime;

	// 更新时间
	private Date modifyTime;

	// 开单人
	private String createUserCode;

	// 更新人
	private String modifyUserCode;

	// 开单组织
	private String createOrgCode;

	// 更新组织
	private String modifyOrgCode;

	// 原运单ID
	private String refId;

	// 原运单号
	private String refCode;

	// 币种
	private String currencyCode;

	// 是否整车运单
	private Boolean isWholeVehicle;

	// 是否经过营业部
	private Boolean isPassDept;

	// 整车编号
	private String vehicleNumber;

	// 整车约车报价
	private BigDecimal wholeVehicleAppfee;
	
	// 整车约车报价波动范围上限
	private BigDecimal wholeVehicleActualfeeFlowRangeUp;

	// 整车约车报价波动范围下限
	private BigDecimal wholeVehicleActualfeeFlowRangeLow;
	// 整车开单报价
	private BigDecimal wholeVehicleActualfee;

	//整车运费冲减
	private BigDecimal wholeVehicleFeeChange;
	//其他成本参数
	private BigDecimal wholeVehicleOtherCostParameter;
	//包装费参数
	private BigDecimal wholeVehiclePackageFeeParameter;
	//货物重量参数
	private BigDecimal wholeVehicleWeightParameter;
	//车价参数
	private BigDecimal wholeVehicleCarCostParameter;
	//人力成本参数
	private BigDecimal wholeVehicleHumanCostParameter;
	// 代打木架部门编码
	private String packageOrgCode;

	// 代打木架部门编码名称
	private String packingOrganizationName;

	// 是否可以打木架
	private String doPacking;

	// 打木架货物件数
	private Integer standGoodsNum;

	// 代打木架要求
	private String standRequirement;

	// 打木架货物尺寸
	private String standGoodsSize;

	// 打木架货物体积
	private BigDecimal standGoodsVolume;

	// 打木架费用价格ID
	private String standChargeId;
	
	// 打木架费用价格CODE
	private String standChargeCode;
	
	// ===========lianhe/增加打木架费用字段/2017年1月4日/start=======
	//打木架费用
	private BigDecimal nonWoodPackingAmount;
	
	//用于计算的打木架费用字段
	private BigDecimal nonWoodPackingCharge;
	
	// ===========lianhe/增加打木架费用字段/2017年1月4日/start=======
	
	// 打木架货物费用
	private BigDecimal standCharge;

	// 打木箱货物件数
	private Integer boxGoodsNum;

	// 代打木箱要求
	private String boxRequirement;

	// 打木箱货物尺寸
	private String boxGoodsSize;

	// 打木箱货物体积
	private BigDecimal boxGoodsVolume;
	
	// 打木箱费用价格ID
	private String boxChargeId;
	
	// 打木箱费用价格CODE
	private String boxChargeCode;

	// 打木箱货物费用
	private BigDecimal boxCharge;

	//zxy 20131118 ISSUE-4391 start 新增：木托属性
	// 打木托件数
	private Integer salverGoodsNum;
	
	// 代打木托要求
	private String salverRequirement;
	
	// 打木托货物费用
	private BigDecimal salverGoodsCharge;
	
	// 打木托费用价格ID
	private String salverChargeId;
	
	// 打木托费用价格CODE
	private String salverChargeCode;
	
	// 前一次打木托货物费用
	private BigDecimal preSalverGoodsCharge;
	
	//是否减去前一次差值
	private boolean isSubPreSalverCharge;
	//zxy 20131118 ISSUE-4391 end 新增：木托属性
	
	// 返款帐户开户ID
	private String accountID;
	
	// 返款帐户开户名称
	private String accountName;

	// 返款帐户开户账户
	private String accountCode;
	
	// 返款账户开户银行编码
	private String accountBankCode;

	// 返款帐户开户银行
	private String accountBank;

	// 计费重量
	private BigDecimal billWeight;

	// 接货费
	private BigDecimal pickupFee;

	// 装卸费
	private BigDecimal serviceFee;

	// 装卸费费率
	private BigDecimal serviceFeeRate;

	// 预计到达时间
	private Date preArriveTime;

	// 运输类型
	private String transportType;

	// 打印次数
	private Integer printTimes;

	// 新增时间
	private Date addTime;

	// 是否为PDA运单
	private Boolean isPdaBill;

	// PDA开单时间
	private Date billTime;

	// 运单处理状态
	private String waybillstatus;

	// 弃货处理的运单号
	private List<String> derelictNum;
	
	// 最终外场编码
	private String lastOutLoadOrgCode;

	/* zhangchengfu 20150530 FOSS系统新客户标签需求 begin */
	/**
	 * 客户分群 
	 */
	private DataDictionaryValueVo flabelleavemonth;
	/**
	 * 行业货源品类  
	 */
	private DataDictionaryValueVo industrySourceCategory;
	
	/**
	 * @需求：智能开单项目
	 * @功能：定义时间统计类（二期删除）
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19上午10:06
	 */
	private IntelligenceBillTimeGather ibtg;
	
	public DataDictionaryValueVo getFlabelleavemonth() {
		return flabelleavemonth;
	}

	public void setFlabelleavemonth(DataDictionaryValueVo flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}

	public DataDictionaryValueVo getIndustrySourceCategory() {
		return industrySourceCategory;
	}
	
	public void setIndustrySourceCategory(DataDictionaryValueVo industrySourceCategory) {
		this.industrySourceCategory = industrySourceCategory;
	}

	/* zhangchengfu 20150530 FOSS系统新客户标签需求 end */
	
	//优惠卷
	private CouponInfoDto  couponInfoDto;
    //是否商业区
    private Boolean businessZone;
    //是否住宅区
    private Boolean residentialDistrict;
	/**
	 * 手动修改后的总送货费
	 */
	private BigDecimal handDeliveryFee;
	
	/**
	 * 标示是否已经减去优惠券
	 */
	private String flagTakeCoupon;
	
	/**
	 * 标示是否已经减去综合信息服务费的优惠券金额
	 * 
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	private String flagZhxxCoupon;
	
	// 市场营销活动
	private DataDictionaryValueVo activeInfo;
	
	//营销活动DTO
    private MarkActivitiesQueryConditionDto activeDto;
    
    //是否要计算营销活动折扣（默认为否）
    private boolean isCalActiveDiscount = false;
       
    //从产品价格获取的价格信息
    List<GuiResultBillCalculateDto> guiResultBillCalculateDtos;    
    
    //降價返券需求：獲取當前版本的價格計算折前運費，需要在提交的時候再次計算運費，故保存 产品价格主参数---206860
    private GuiQueryBillCalculateDto productPriceDtoCollection;
    /**
   	 * 伙伴开单
   	 */
   	private Boolean  partnerBilling;
    /**
     * 伙伴名称
     */
	private String partnerName;
	/**
	 * 伙伴手机
	 */
	private String partnerPhone;
	/**
	 * 暂存公布价运费
	 */
	private BigDecimal tempTransportFee;
	
	/**
	 * 是否二级网点
	 */
	private String isTwoLevelNetwork;
	
	/**
	 * 网点模式
	 */
	private String networkModel;
	
	/**
	 * 是否加盟网点（提货网点）,选择目的站网点时赋值
	 * add by xingjun 20160913
	 */
	private String isLeagueSaledept;
	
	public String getIsLeagueSaledept() {
		return isLeagueSaledept;
	}

	public void setIsLeagueSaledept(String isLeagueSaledept) {
		this.isLeagueSaledept = isLeagueSaledept;
	}

	public String getIsTwoLevelNetwork() {
		return isTwoLevelNetwork;
	}

	public void setIsTwoLevelNetwork(String isTwoLevelNetwork) {
		this.isTwoLevelNetwork = isTwoLevelNetwork;
	}

	public String getNetworkModel() {
		return networkModel;
	}

	public void setNetworkModel(String networkModel) {
		this.networkModel = networkModel;
	}

	public BigDecimal getTempTransportFee() {
		return tempTransportFee;
	}

	public void setTempTransportFee(BigDecimal tempTransportFee) {
		this.tempTransportFee = tempTransportFee;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}

	public Boolean getPartnerBilling() {
		return partnerBilling;
	}

	public void setPartnerBilling(Boolean partnerBilling) {
		this.partnerBilling = partnerBilling;
	}

	public GuiQueryBillCalculateDto getProductPriceDtoCollection() {
		return productPriceDtoCollection;
	}

	public void setProductPriceDtoCollection(
			GuiQueryBillCalculateDto productPriceDtoCollection) {
		this.productPriceDtoCollection = productPriceDtoCollection;
	}

    //图片运单ID
    private String pictureWaybillNo;
    //百度ID
    private String baiDuId;
    
    private String changeVolume;
    
	public String getBaiDuId() {
		return baiDuId;
	}

	public void setBaiDuId(String baiDuId) {
		this.baiDuId = baiDuId;
	}

    //图片上传的装卸费
    private BigDecimal pictureServiceFee;
    
	public BigDecimal getPictureServiceFee() {
		return pictureServiceFee;
	}

	public void setPictureServiceFee(BigDecimal pictureServiceFee) {
		this.pictureServiceFee = CalculateFeeTotalUtils.formatNumberInteger(serviceFee);
	}
	
	/**
	 * 图片运单是否现金
	 */
	private String cashPay;
    
	public String getCashPay() {
		return cashPay;
	}

	public void setCashPay(String cashPay) {
		this.cashPay = cashPay;
	}

	public String getPictureWaybillNo() {
		return pictureWaybillNo;
	}

	public void setPictureWaybillNo(String pictureWaybillNo) {
		this.pictureWaybillNo = pictureWaybillNo;
	}
	public List<GuiResultBillCalculateDto> getGuiResultBillCalculateDtos() {
		return guiResultBillCalculateDtos;
	}

	public void setGuiResultBillCalculateDtos(
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos) {
		this.guiResultBillCalculateDtos = guiResultBillCalculateDtos;
	}
    
	public boolean isCalActiveDiscount() {
		return isCalActiveDiscount;
	}

	public void setCalActiveDiscount(boolean isCalActiveDiscount) {
		this.isCalActiveDiscount = isCalActiveDiscount;
	}

	public MarkActivitiesQueryConditionDto getActiveDto() {
		return activeDto;
	}

	public void setActiveDto(MarkActivitiesQueryConditionDto activeDto) {
		this.activeDto = activeDto;
	}

	public DataDictionaryValueVo getActiveInfo() {
		return activeInfo;
	}

	public void setActiveInfo(DataDictionaryValueVo activeInfo) {
		this.activeInfo = activeInfo;
	}

	public String getFlagZhxxCoupon() {
		return flagZhxxCoupon;
	}

	public void setFlagZhxxCoupon(String flagZhxxCoupon) {
		this.flagZhxxCoupon = flagZhxxCoupon;
	}
	public String getFlagTakeCoupon() {
		return flagTakeCoupon;
	}
	public void setFlagTakeCoupon(String flagTakeCoupon) {
		this.flagTakeCoupon = flagTakeCoupon;
	}

	/**
	 * 开户银行信息
	 */
	private CusAccountEntity openBank;
	
	/**
	 * PDA 总金额
	 */
	private BigDecimal totalCountPDA;
	
	/**
	 * CRM订单导入：官网用户名
	 */
	private String channelCustId;
	
	/**
	 * 原运单号
	 */
	private String oldWaybillNo;
	
	/**
	 * 优惠卷费用
	 */
	private BigDecimal couponFree;
		
	/**
	 * 是否是开单暂存补录
	 */
	private Boolean isPCPending;
	
	/**
	 * 限保物品：限制报价金额
	 */
	private BigDecimal  limitedAmount;
	
	/**
	 * 基础接货费
	 */
    private BigDecimal basePickupFee; 
    
    /**
     * 最大接货费
     */
    private BigDecimal maxPickupFee;
    
    /**
     * 最小接货费
     */
    private BigDecimal minPickupFee;
    
	 /**
     * 优惠券优惠费用归集类型
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
     */
    private String couponRankType;
    
    /**
     * ============== 快递专用  ===================
     */
    /**
     * 是否手工修改过保费（参见DEFECT-543）
     */
    private boolean isHandInsuranceFee = false;

    

	//快递优惠活动类型
	
	private DataDictionaryValueVo specialOffer;
	
	/**
	 * 是否精准大票
	 */
	private Boolean isBigGoods;	//zxy 20140626 JZDP 新增
	
	/**
	 *是否展货 
	 */
	private Boolean isExhibitCargo;
	/**
	 * 是否修改了标签信息-仅用于图片开单查询时点击修改操作标识判断 hehaisu
	 */
	private Boolean isModifyLabel;

	

	/**
	 * 发货具体地址备注
	 */
	private String deliveryCustomerAddressNote;
	
	/**
	 * 收货具体地址备注
	 */
	private String receiveCustomerAddressNote;
	 
	/**
	 * 是否电商尊享
	 * @return
	 */
	 private  String isElectricityToEnjoy;
	 
    /**
	  * 是否精准包裹
	  */
	 private String isAccuratePackage;
	 
    /**
     * 返货原因
     */
	private String returnBillReason;

    
	  //果果订单类型serverType
    private String serverType;
	
      //优惠卷费用为推往ptp保存值
    private BigDecimal fossToPtpCouponFree;
	
    
	public BigDecimal getFossToPtpCouponFree() {
		return fossToPtpCouponFree;
	}

	public void setFossToPtpCouponFree(BigDecimal fossToPtpCouponFree) {
		this.fossToPtpCouponFree = fossToPtpCouponFree;
	}

	public Boolean getIsModifyLabel() {
		return isModifyLabel;
	}

	public void setIsModifyLabel(Boolean isModifyLabel) {
		this.isModifyLabel = isModifyLabel;
	}
	public DataDictionaryValueVo getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(DataDictionaryValueVo specialOffer) {
		this.specialOffer = specialOffer;
	}

	/**
	 * @return the waybillNoImported
	 */
	public String getWaybillNoImported() {
		return waybillNoImported;
	}


	/**
	 * @param waybillNoImported the waybillNoImported to set
	 */
	public void setWaybillNoImported(String waybillNoImported) {
		this.waybillNoImported = waybillNoImported;
	}


	public BigDecimal getBasePickupFee() {
		return basePickupFee;
	}


	public void setBasePickupFee(BigDecimal basePickupFee) {
		this.basePickupFee = basePickupFee;
	}


	public BigDecimal getLimitedAmount() {
		return limitedAmount;
	}


	public void setLimitedAmount(BigDecimal limitedAmount) {
		this.limitedAmount = limitedAmount;
	}


	/**
	 * 
	 * 收货部门省份编码
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-27 下午4:03:39
	 */
	public String getReceiveOrgProvCode() {
		return receiveOrgProvCode;
	}


	/**
	 * 
	 * 收货部门省份编码
	 * @param receiveOrgProvCode
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-27 下午4:04:15
	 */
	public void setReceiveOrgProvCode(String receiveOrgProvCode) {
		this.receiveOrgProvCode = receiveOrgProvCode;
	}


	/**
	 * @return  the standChargeId
	 */
	public String getStandChargeId() {
		return standChargeId;
	}

	
	/**
	 * @param standChargeId the standChargeId to set
	 */
	public void setStandChargeId(String standChargeId) {
		this.standChargeId = standChargeId;
	}

	
	/**
	 * @return  the standChargeCode
	 */
	public String getStandChargeCode() {
		return standChargeCode;
	}

	
	/**
	 * @param standChargeCode the standChargeCode to set
	 */
	public void setStandChargeCode(String standChargeCode) {
		this.standChargeCode = standChargeCode;
	}

	
	/**
	 * @return  the boxChargeId
	 */
	public String getBoxChargeId() {
		return boxChargeId;
	}

	
	/**
	 * @param boxChargeId the boxChargeId to set
	 */
	public void setBoxChargeId(String boxChargeId) {
		this.boxChargeId = boxChargeId;
	}

	
	/**
	 * @return  the boxChargeCode
	 */
	public String getBoxChargeCode() {
		return boxChargeCode;
	}

	
	/**
	 * @param boxChargeCode the boxChargeCode to set
	 */
	public void setBoxChargeCode(String boxChargeCode) {
		this.boxChargeCode = boxChargeCode;
	}

	/**
	 * @return  the transportFeeId
	 */
	public String getTransportFeeId() {
		return transportFeeId;
	}
	
	/**
	 * @param transportFeeId the transportFeeId to set
	 */
	public void setTransportFeeId(String transportFeeId) {
		this.transportFeeId = transportFeeId;
	}
	
	/**
	 * @return  the transportFeeCode
	 */
	public String getTransportFeeCode() {
		return transportFeeCode;
	}
	
	/**
	 * @param transportFeeCode the transportFeeCode to set
	 */
	public void setTransportFeeCode(String transportFeeCode) {
		this.transportFeeCode = transportFeeCode;
	}

	/**
	 * 
	 * 获取优惠卷费用
	 * 
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-13 下午3:21:21
	 */
	public BigDecimal getCouponFree() {
		return couponFree;
	}

	/**
	 * 
	 * 设置优惠券费用
	 * 
	 * @param couponFree
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-13 下午3:21:39
	 */
	public void setCouponFree(BigDecimal couponFree) {
		this.couponFree = couponFree;
	}
	
	/**
	 * @param channelCustId the channelCustId to set
	 */
	public void setChannelCustId(String channelCustId) {
		this.channelCustId = channelCustId;
	}

	/**
	 * @return the channelCustId
	 */
	public String getChannelCustId() {
		return channelCustId;
	}

	/**
	 * 保存导入PDA订单时，值改变就设置颜色的控件与值
	 */
	private Map<JTextField,String> changeColorTxt;
	
	/**
	 * @return the totalCountPDA
	 */
	public BigDecimal getTotalCountPDA() {
		return totalCountPDA;
	}

	/**
	 * @param totalCountPDA the totalCountPDA to set
	 */
	public void setTotalCountPDA(BigDecimal totalCountPDA) {
		this.totalCountPDA = totalCountPDA;
	}

	/**
	 * @return 优惠卷
	 */
	public CouponInfoDto getCouponInfoDto() {
		return couponInfoDto;
	}

	/**
	 * @param 优惠卷
	 */
	public void setCouponInfoDto(CouponInfoDto couponInfoDto) {
		this.couponInfoDto = couponInfoDto;
	}

	/**
	 * @return 最终外场编码
	 */
	public String getLastOutLoadOrgCode() {
		return lastOutLoadOrgCode;
	}

	/**
	 * @param 最终外场编码
	 *            the lastOutLoadOrgCode to set
	 */
	public void setLastOutLoadOrgCode(String lastOutLoadOrgCode) {
		this.lastOutLoadOrgCode = lastOutLoadOrgCode;
	}
	
	
	/**
	 * 包装-纸
	 */
	public Integer getPaper() {
		return paper;
	}

	/**
	 * 包装-纸
	 */
	public void setPaper(Integer paper) {
		this.paper = paper;
	}

	/**
	 * 包装-木
	 */
	public Integer getWood() {
		return wood;
	}

	/**
	 * 包装-木
	 */
	public void setWood(Integer wood) {
		this.wood = wood;
	}

	/**
	 * 包装-纤
	 */
	public Integer getFibre() {
		return fibre;
	}

	/**
	 * 包装-纤
	 */
	public void setFibre(Integer fibre) {
		this.fibre = fibre;
	}

	/**
	 * 包装-托
	 */
	public Integer getSalver() {
		return salver;
	}

	/**
	 * 包装-托
	 */
	public void setSalver(Integer salver) {
		this.salver = salver;
	}

	/**
	 * 包装-膜
	 */
	public Integer getMembrane() {
		return membrane;
	}

	/**
	 * 包装-膜
	 */
	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}

	/**
	 * 包装-其他
	 */
	public String getOtherPackage() {
		return otherPackage;
	}

	/**
	 * 包装-其他
	 */
	public void setOtherPackage(String otherPackage) {
		this.otherPackage = otherPackage;
	}

	/**
	 * 手写金额
	 */
	public BigDecimal getHandWriteMoney() {
		return handWriteMoney;
	}

	/**
	 * 手写金额
	 */
	public void setHandWriteMoney(BigDecimal handWriteMoney) {
		//四舍五入取整数
		this.handWriteMoney = CalculateFeeTotalUtils.formatNumberInteger(handWriteMoney);
	}

	/**
	 * 代打木架
	 */
	public WoodYokeVo getYokeVo() {
		return yokeVo;
	}

	/**
	 * 代打木架
	 */
	public void setYokeVo(WoodYokeVo yokeVo) {
		this.yokeVo = yokeVo;
	}

	/**
	 * 储运事项
	 */
	public String getTransportationRemark() {
		return transportationRemark;
	}

	/**
	 * 储运事项
	 */
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}

	/**
	 * 外部订单导入的储运事项
	 * @return
	 */
	public String getTransportationRemarkOfWebOrder() {
		return transportationRemarkOfWebOrder;
	}
	
	/**
	 * 外部订单导入的储运事项
	 * @param transportationRemarkOfWebOrder
	 */
	public void setTransportationRemarkOfWebOrder(
			String transportationRemarkOfWebOrder) {
		this.transportationRemarkOfWebOrder = transportationRemarkOfWebOrder;
	}
	/**
	 * 长途短途
	 */
	public String getLongOrShort() {
		return longOrShort;
	}

	/**
	 * 长途短途
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	/**
	 * 公布价运费
	 */
	public String getTransportFeeCanvas() {
		return transportFeeCanvas;
	}

	/**
	 * 公布价运费
	 */
	public void setTransportFeeCanvas(String transportFeeCanvas) {
		this.transportFeeCanvas = transportFeeCanvas;
	}

	/**
	 * 保价声明
	 */
	public String getInsuranceAmountCanvas() {
		return insuranceAmountCanvas;
	}

	/**
	 * 保价声明
	 */
	public void setInsuranceAmountCanvas(String insuranceAmountCanvas) {
		//四舍五入取整数
		this.insuranceAmountCanvas = CalculateFeeTotalUtils.formatNumberInteger(insuranceAmountCanvas);
	}

	/**
	 * 代收货款
	 */
	public String getCodAmountCanvas() {
		return codAmountCanvas;
	}

	/**
	 * 代收货款
	 */
	public void setCodAmountCanvas(String codAmountCanvas) {
		this.codAmountCanvas = codAmountCanvas;
	}

	/**
	 * 接货费
	 */
	public String getPickUpFeeCanvas() {
		return pickUpFeeCanvas;
	}

	/**
	 * 接货费
	 */
	public void setPickUpFeeCanvas(String pickUpFeeCanvas) {
		this.pickUpFeeCanvas = pickUpFeeCanvas;
	}

	/**
	 * 送货费
	 */
	public String getDeliveryGoodsFeeCanvas() {
		return deliveryGoodsFeeCanvas;
	}

	/**
	 * 送货费
	 */
	public void setDeliveryGoodsFeeCanvas(String deliveryGoodsFeeCanvas) {
		this.deliveryGoodsFeeCanvas = deliveryGoodsFeeCanvas;
	}

	/**
	 * 包装费
	 */
	public String getPackageFeeCanvas() {
		return packageFeeCanvas;
	}

	/**
	 * 包装费
	 */
	public void setPackageFeeCanvas(String packageFeeCanvas) {
		this.packageFeeCanvas = packageFeeCanvas;
	}

	/**
	 * 装卸费
	 */
	public String getServiceFeeCanvas() {
		return serviceFeeCanvas;
	}

	/**
	 * 装卸费
	 */
	public void setServiceFeeCanvas(String serviceFeeCanvas) {
		this.serviceFeeCanvas = serviceFeeCanvas;
	}

	/**
	 * 其他费用
	 */
	public String getOtherFeeCanvas() {
		return otherFeeCanvas;
	}

	/**
	 * 其他费用
	 */
	public void setOtherFeeCanvas(String otherFeeCanvas) {
		this.otherFeeCanvas = otherFeeCanvas;
	}

	/**
	 * 优惠费用
	 */
	public String getPromotionsFeeCanvas() {
		return promotionsFeeCanvas;
	}

	/**
	 * 优惠费用
	 */
	public void setPromotionsFeeCanvas(String promotionsFeeCanvas) {
		//四舍五入取整数
		this.promotionsFeeCanvas = CalculateFeeTotalUtils.formatNumberInteger(promotionsFeeCanvas);
	}

	/**
	 * 总费用
	 */
	public String getTotalFeeCanvas() {
		return totalFeeCanvas;
	}

	/**
	 * 总费用
	 */
	public void setTotalFeeCanvas(String totalFeeCanvas) {
		this.totalFeeCanvas = totalFeeCanvas;
	}

	/**
	 * 运单ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 运单ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 优惠类型
	 * @return
	 */
	public String getPreferentialType() {
		return preferentialType;
	}
	/**
	 * 优惠类型
	 * @param preferentialType
	 */
	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}
	/**
	 * 结算付款(是否月结)
	 */
	public Boolean getChargeMode() {
		return chargeMode;
	}

	/**
	 * 结算付款(是否月结)
	 */
	public void setChargeMode(Boolean chargeMode) {
		this.chargeMode = chargeMode;
	}

	/**
	 * 发货客户ID
	 */
	public String getDeliveryCustomerId() {
		return deliveryCustomerId;
	}

	/**
	 * 发货客户ID
	 */
	public void setDeliveryCustomerId(String deliveryCustomerId) {
		this.deliveryCustomerId = deliveryCustomerId;
	}

	/**
	 * 发货客户编码
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * 发货客户编码
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * 发货客户名称
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * 发货客户名称
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * 发货客户手机
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * 发货客户手机
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * 发货客户电话
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * 发货客户电话
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * 发货客户联系人
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * 发货客户联系人
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * 发货客户联系人ID
	 */
	public String getDeliveryCustomerContactId() {
		return deliveryCustomerContactId;
	}

	/**
	 * 发货客户联系人ID
	 */
	public void setDeliveryCustomerContactId(String deliveryCustomerContactId) {
		this.deliveryCustomerContactId = deliveryCustomerContactId;
	}

	/**
	 * 发货国家
	 */
	public String getDeliveryCustomerNationCode() {
		return deliveryCustomerNationCode;
	}

	/**
	 * 发货国家
	 */
	public void setDeliveryCustomerNationCode(String deliveryCustomerNationCode) {
		this.deliveryCustomerNationCode = deliveryCustomerNationCode;
	}

	/**
	 * 发货省份
	 */
	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	/**
	 * 发货省份
	 */
	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	/**
	 * 发货市
	 */
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	/**
	 * 发货市
	 */
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	/**
	 * 发货区
	 */
	public String getDeliveryCustomerDistCode() {
		return deliveryCustomerDistCode;
	}

	/**
	 * 发货区
	 */
	public void setDeliveryCustomerDistCode(String deliveryCustomerDistCode) {
		this.deliveryCustomerDistCode = deliveryCustomerDistCode;
	}

	/**
	 * 发货具体地址
	 */
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	/**
	 * 发货具体地址
	 */
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}



	/**
	 * 收货客户ID
	 */
	public String getReceiveCustomerId() {
		return receiveCustomerId;
	}

	/**
	 * 收货客户ID
	 */
	public void setReceiveCustomerId(String receiveCustomerId) {
		this.receiveCustomerId = receiveCustomerId;
	}

	/**
	 * 收货客户编码
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * 收货客户编码
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * 收货客户名称
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * 收货客户名称
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * 收货客户手机
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * 收货客户手机
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * 收货客户电话
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * 收货客户电话
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * 收货客户联系人
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * 收货客户联系人
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * 收货客户联系人ID
	 */
	public String getReceiveCustomerContactId() {
		return receiveCustomerContactId;
	}

	/**
	 * 收货客户联系人ID
	 */
	public void setReceiveCustomerContactId(String receiveCustomerContactId) {
		this.receiveCustomerContactId = receiveCustomerContactId;
	}

	/**
	 * 收货客户联系人ID
	 */
	public String getReceiveCustomerNationCode() {
		return receiveCustomerNationCode;
	}

	/**
	 * 收货客户联系人ID
	 */
	public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
		this.receiveCustomerNationCode = receiveCustomerNationCode;
	}

	/**
	 * 收货省份
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	 * 收货省份
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * 收货市
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	 * 收货市
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * 收货区
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	 * 收货区
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	/**
	 * 收货具体地址
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * 收货具体地址
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}



	/**
	 * 收货部门
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * 收货部门
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * 收货部门
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * 收货部门
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * 运输性质
	 */
	public ProductEntityVo getProductCode() {
		return productCode;
	}

	/**
	 * 运输性质
	 */
	public void setProductCode(ProductEntityVo productCode) {
		this.productCode = productCode;
	}

	/**
	 * 提货方式
	 */
	public DataDictionaryValueVo getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * 提货方式
	 */
	public void setReceiveMethod(DataDictionaryValueVo receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * 提货网点
	 */
	public BranchVo getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * 提货网点
	 */
	public void setCustomerPickupOrgCode(BranchVo customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * 提货网点名称
	 */
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	/**
	 * 提货网点名称
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	/**
	 * 配载类型
	 */
	public String getLoadMethod() {
		return loadMethod;
	}

	/**
	 * 配载类型
	 */
	public void setLoadMethod(String loadMethod) {
		this.loadMethod = loadMethod;
	}

	/**
	 * 目的站
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * 目的站
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * 是否上门接货
	 */
	public Boolean getPickupToDoor() {
		return pickupToDoor;
	}

	/**
	 * 是否上门接货
	 */
	public void setPickupToDoor(Boolean pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

	/**
	 * 司机
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 司机
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 是否集中接货
	 */
	public Boolean getPickupCentralized() {
		return pickupCentralized;
	}

	/**
	 * 是否集中接货
	 */
	public void setPickupCentralized(Boolean pickupCentralized) {
		this.pickupCentralized = pickupCentralized;
	}

	/**
	 * 配载线路编码
	 */
	public String getLoadLineCode() {
		return loadLineCode;
	}

	/**
	 * 配载线路编码
	 */
	public void setLoadLineCode(String loadLineCode) {
		this.loadLineCode = loadLineCode;
	}

	/**
	 * 配载线路名称
	 */
	public String getLoadLineName() {
		return loadLineName;
	}

	/**
	 * 配载线路名称
	 */
	public void setLoadLineName(String loadLineName) {
		this.loadLineName = loadLineName;
	}

	/**
	 * 配载部门编号
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * 配载部门编号
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * 配载部门名称
	 */
	public String getLoadOrgName() {
		return loadOrgName;
	}

	/**
	 * 配载部门名称
	 */
	public void setLoadOrgName(String loadOrgName) {
		this.loadOrgName = loadOrgName;
	}

	/**
	 * 最终配载部门编号
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * 最终配载部门编号
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * 最终配载部门名称
	 */
	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	/**
	 * 最终配载部门名称
	 */
	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}

	/**
	 * 预计出发时间
	 */
	public Date getPreDepartureTime() {
		return preDepartureTime;
	}

	/**
	 * 预计出发时间
	 */
	public void setPreDepartureTime(Date preDepartureTime) {
		this.preDepartureTime = preDepartureTime;
	}

	/**
	 * 预计派送/提货时间
	 */
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	/**
	 * 预计派送/提货时间
	 */
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	/**
	 * 是否大车直送
	 */
	public Boolean getCarDirectDelivery() {
		return carDirectDelivery;
	}

	/**
	 * 是否大车直送
	 */
	public void setCarDirectDelivery(Boolean carDirectDelivery) {
		this.carDirectDelivery = carDirectDelivery;
	}

	/**
	 * 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 货物总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * 货物总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * 货物总重量
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * 货物总重量
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * 货物总体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * 货物总体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * 货物尺寸
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * 货物尺寸
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	/**
	 * 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * 是否贵重物品
	 */
	public Boolean getPreciousGoods() {
		return preciousGoods;
	}

	/**
	 * 是否贵重物品
	 */
	public void setPreciousGoods(Boolean preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	/**
	 * 是否异形物品
	 */
	public Boolean getSpecialShapedGoods() {
		return specialShapedGoods;
	}

	/**
	 * 是否异形物品
	 */
	public void setSpecialShapedGoods(Boolean specialShapedGoods) {
		this.specialShapedGoods = specialShapedGoods;
	}

	/**
	 * 对外备注
	 */
	public DataDictionaryValueVo getOuterNotes() {
		return outerNotes;
	}

	/**
	 * 对外备注
	 */
	public void setOuterNotes(DataDictionaryValueVo outerNotes) {
		this.outerNotes = outerNotes;
	}

	/**
	 * 对内备注
	 */
	public String getInnerNotes() {
		return innerNotes;
	}

	/**
	 * 对内备注
	 */
	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}

	/**
	 * 对内备注
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * 对内备注
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * 保险费ID
	 */
	public String getInsuranceId() {
		return insuranceId;
	}

	/**
	 * 保险费ID
	 */
	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	/**
	 * 保险费code
	 */
	public String getInsuranceCode() {
		return insuranceCode;
	}

	/**
	 * 保险费code
	 */
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	/**
	 * 保价声明价值
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * 保价声明价值
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		//四舍五入取整数
		this.insuranceAmount = CalculateFeeTotalUtils.formatNumberInteger(insuranceAmount);
	}

	/**
	 * 保价费率
	 */
	public BigDecimal getInsuranceRate() {
		return insuranceRate;
	}

	/**
	 * 保价费率
	 */
	public void setInsuranceRate(BigDecimal insuranceRate) {
		//保留两位小数
		this.insuranceRate = CalculateFeeTotalUtils.formatNumberTwoDecimal(insuranceRate);
	}

	/**
	 * 保价费
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * 保价费
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		//四舍五入取整数
		this.insuranceFee = CalculateFeeTotalUtils.formatNumberInteger(insuranceFee);
	}

	/**
	 * 保额最大值
	 */
	public BigDecimal getMaxInsuranceAmount() {
		return maxInsuranceAmount;
	}

	/**
	 * 保额最大值
	 */
	public void setMaxInsuranceAmount(BigDecimal maxInsuranceAmount) {
		this.maxInsuranceAmount = maxInsuranceAmount;
	}

	/**
	 * 保价费最低一票
	 */
	public BigDecimal getMixInsuranceAmount() {
		return mixInsuranceAmount;
	}

	/**
	 * 保价费最低一票
	 */
	public void setMixInsuranceAmount(BigDecimal mixInsuranceAmount) {
		this.mixInsuranceAmount = mixInsuranceAmount;
	}

	/**
	 * 代收货款ID
	 */
	public String getCodId() {
		return codId;
	}

	/**
	 * 代收货款ID
	 */
	public void setCodId(String codId) {
		this.codId = codId;
	}

	/**
	 * 代收货款Code
	 */
	public String getCodCode() {
		return codCode;
	}

	/**
	 * 代收货款Code
	 */
	public void setCodCode(String codCode) {
		this.codCode = codCode;
	}

	/**
	 * 代收货款
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * 代收货款
	 */
	public void setCodAmount(BigDecimal codAmount) {
		//四舍五入取整数
//		this.codAmount = CalculateFeeTotalUtils.formatNumberInteger(codAmount);
		this.codAmount = codAmount;
	}

	/**
	 * 代收费率
	 */
	public BigDecimal getCodRate() {
		return codRate;
	}

	/**
	 * 代收费率
	 */
	public void setCodRate(BigDecimal codRate) {
		//保留两位小数
		this.codRate = CalculateFeeTotalUtils.formatNumberTwoDecimal(codRate);
	}

	/**
	 * 代收货款手续费
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * 代收货款手续费
	 */
	public void setCodFee(BigDecimal codFee) {
		//四舍五入取整数
		this.codFee = CalculateFeeTotalUtils.formatNumberInteger(codFee);
	}

	/**
	 * 退款类型
	 */
	public DataDictionaryValueVo getRefundType() {
		return refundType;
	}

	/**
	 * 退款类型
	 */
	public void setRefundType(DataDictionaryValueVo refundType) {
		this.refundType = refundType;
	}

	/**
	 * 返单类别
	 */
	public DataDictionaryValueVo getReturnBillType() {
		return returnBillType;
	}

	/**
	 * 返单类别
	 */
	public void setReturnBillType(DataDictionaryValueVo returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * 返单费用名称
	 */
	public String getReturnBillChargeName() {
		return returnBillChargeName;
	}

	/**
	 * 返单费用名称
	 */
	public void setReturnBillChargeName(String returnBillChargeName) {
		this.returnBillChargeName = returnBillChargeName;
	}

	/**
	 * 预付费保密
	 */
	public Boolean getSecretPrepaid() {
		return secretPrepaid;
	}

	/**
	 * 预付费保密
	 */
	public void setSecretPrepaid(Boolean secretPrepaid) {
		this.secretPrepaid = secretPrepaid;
	}

	/**
	 * 到付金额
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * 到付金额
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		//四舍五入取整数
//		this.toPayAmount = CalculateFeeTotalUtils.formatNumberInteger(toPayAmount);
		this.toPayAmount = toPayAmount;
	}

	/**
	 * 预付金额
	 */
	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	/**
	 * 预付金额
	 */
	public void setPrePayAmount(BigDecimal prePayAmount) {
		//四舍五入取整数
		this.prePayAmount = CalculateFeeTotalUtils.formatNumberInteger(prePayAmount);
	}

	/**
	 * 送货费
	 */
	public BigDecimal getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	/**
	 * 送货费
	 */
	public void setDeliveryGoodsFee(BigDecimal deliveryGoodsFee) {
		//四舍五入取整数
		this.deliveryGoodsFee = CalculateFeeTotalUtils.formatNumberInteger(deliveryGoodsFee);
	}

	/**
	 * 其他费用
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	/**
	 * 其他费用
	 */
	public void setOtherFee(BigDecimal otherFee) {
		//四舍五入取整数
		this.otherFee = CalculateFeeTotalUtils.formatNumberInteger(otherFee);
	}

	/**
	 * 包装手续费
	 */
	public BigDecimal getPackageFee() {
		return packageFee;
	}

	/**
	 * 包装手续费
	 */
	public void setPackageFee(BigDecimal packageFee) {
		//四舍五入取整数
		this.packageFee = CalculateFeeTotalUtils.formatNumberInteger(packageFee);
	}

	/**
	 * 优惠费用
	 */
	public BigDecimal getPromotionsFee() {
		return promotionsFee;
	}

	/**
	 * 优惠费用
	 */
	public void setPromotionsFee(BigDecimal promotionsFee) {
		//四舍五入取整数
		this.promotionsFee = CalculateFeeTotalUtils.formatNumberInteger(promotionsFee);
	}

	/**
	 * 运费计费类型
	 */
	public DataDictionaryValueVo getBillingType() {
		return billingType;
	}

	/**
	 * 运费计费类型
	 */
	public void setBillingType(DataDictionaryValueVo billingType) {
		this.billingType = billingType;
	}

	/**
	 * 运费计费费率
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 运费计费费率
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		//保留两位小数
		this.unitPrice = CalculateFeeTotalUtils.formatNumberTwoDecimal(unitPrice);
	}

	/**
	 * 公布价运费
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * 公布价运费
	 */
	public void setTransportFee(BigDecimal transportFee) {
		//四舍五入取整数
		this.transportFee = CalculateFeeTotalUtils.formatNumberInteger(transportFee);
	}

	/**
	 * 折扣
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * 折扣
	 */
	public void setDiscount(BigDecimal discount) {
		//四舍五入保留两位小数
		this.discount = CalculateFeeTotalUtils.formatNumberTwoDecimal(discount);
	}

	/**
	 * 增值费用
	 */
	public BigDecimal getValueAddFee() {
		return valueAddFee;
	}

	/**
	 * 增值费用
	 */
	public void setValueAddFee(BigDecimal valueAddFee) {
		//四舍五入取整数
		this.valueAddFee = CalculateFeeTotalUtils.formatNumberInteger(valueAddFee);
	}

	/**
	 * 开单付款方式
	 */
	public DataDictionaryValueVo getPaidMethod() {
		return paidMethod;
	}

	/**
	 * 开单付款方式
	 */
	public void setPaidMethod(DataDictionaryValueVo paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * 到达类型
	 */
	public String getArriveType() {
		return arriveType;
	}

	/**
	 * 到达类型
	 */
	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
	}

	/**
	 * 运单状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 运单状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 禁行
	 */
	public String getForbiddenLine() {
		return forbiddenLine;
	}

	/**
	 * 禁行
	 */
	public void setForbiddenLine(String forbiddenLine) {
		this.forbiddenLine = forbiddenLine;
	}

	/**
	 * 合票方式
	 */
	public DataDictionaryValueVo getFreightMethod() {
		return freightMethod;
	}

	/**
	 * 合票方式
	 */
	public void setFreightMethod(DataDictionaryValueVo freightMethod) {
		this.freightMethod = freightMethod;
	}

	/**
	 * 航班类型
	 */
	public DataDictionaryValueVo getFlightNumberType() {
		return flightNumberType;
	}

	/**
	 * 航班类型
	 */
	public void setFlightNumberType(DataDictionaryValueVo flightNumberType) {
		this.flightNumberType = flightNumberType;
	}

	/**
	 * 航班时间
	 */
	public String getFlightShift() {
		return flightShift;
	}

	/**
	 * 航班时间
	 */
	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}

	/**
	 * 总费用
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * 总费用
	 */
	public void setTotalFee(BigDecimal totalFee) {
		//四舍五入取整数
//		this.totalFee = CalculateFeeTotalUtils.formatNumberInteger(totalFee);
		this.totalFee = totalFee;
	}

	/**
	 * 优惠编码
	 */
	public String getPromotionsCode() {
		return promotionsCode;
	}

	/**
	 * 优惠编码
	 */
	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}

	/**
	 * 生效日期
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 生效日期
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 结束日期
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 结束日期
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 开单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 开单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 更新时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 更新时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 开单人
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 开单人
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 更新人
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * 更新人
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * 开单组织
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 开单组织
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 更新组织
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * 更新组织
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * 原运单ID
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * 原运单ID
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	/**
	 * 原运单号
	 */
	public String getRefCode() {
		return refCode;
	}

	/**
	 * 原运单号
	 */
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	/**
	 * 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 是否整车运单
	 */
	public Boolean getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * 是否整车运单
	 */
	public void setIsWholeVehicle(Boolean isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * 是否经过营业部
	 */
	public Boolean getIsPassDept() {
		return isPassDept;
	}

	/**
	 * 是否经过营业部
	 */
	public void setIsPassDept(Boolean isPassDept) {
		this.isPassDept = isPassDept;
	}

	/**
	 * 整车编号
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}

	/**
	 * 整车编号
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	/**
	 * 整车约车报价
	 */
	public BigDecimal getWholeVehicleAppfee() {
		return wholeVehicleAppfee;
	}

	/**
	 * 整车约车报价
	 */
	public void setWholeVehicleAppfee(BigDecimal wholeVehicleAppfee) {
		this.wholeVehicleAppfee = wholeVehicleAppfee;
	}

	/**
	 * 整车开单报价
	 */
	public BigDecimal getWholeVehicleActualfee() {
		return wholeVehicleActualfee;
	}

	/**
	 * 整车开单报价
	 */
	public void setWholeVehicleActualfee(BigDecimal wholeVehicleActualfee) {
		this.wholeVehicleActualfee = wholeVehicleActualfee;
	}

	/**
	 * 代打木架部门编码
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}

	/**
	 * 代打木架部门编码
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}

	/**
	 * 代打木架部门编码名称
	 */
	public String getPackingOrganizationName() {
		return packingOrganizationName;
	}

	/**
	 * 代打木架部门编码名称
	 */
	public void setPackingOrganizationName(String packingOrganizationName) {
		this.packingOrganizationName = packingOrganizationName;
	}

	/**
	 * 是否可以打木架
	 */
	public String getDoPacking() {
		return doPacking;
	}

	/**
	 * 是否可以打木架
	 */
	public void setDoPacking(String doPacking) {
		this.doPacking = doPacking;
	}

	/**
	 * 打木架货物件数
	 */
	public Integer getStandGoodsNum() {
		return standGoodsNum;
	}

	/**
	 * 打木架货物件数
	 */
	public void setStandGoodsNum(Integer standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}

	/**
	 * 代打木架要求
	 */
	public String getStandRequirement() {
		return standRequirement;
	}

	/**
	 * 代打木架要求
	 */
	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}

	/**
	 * 打木架货物尺寸
	 */
	public String getStandGoodsSize() {
		return standGoodsSize;
	}

	/**
	 * 打木架货物尺寸
	 */
	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}

	/**
	 * 打木架货物体积
	 */
	public BigDecimal getStandGoodsVolume() {
		return standGoodsVolume;
	}

	/**
	 * 打木架货物体积
	 */
	public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}

	// ===========lianhe/增加非木包装费/2017年1月4日/start=======
	/**
	 * 非木包装费
	 */
	public BigDecimal getNonWoodPackingAmount() {
		return nonWoodPackingAmount;
	}
	
	/**
	 * 非木包装费
	 */
	public void setNonWoodPackingAmount(BigDecimal nonWoodPackingAmount) {
		this.nonWoodPackingAmount = nonWoodPackingAmount;
	}
	
	/**
	 * 用于计算的打木架费用字段
	 * @return
	 */
	public BigDecimal getNonWoodPackingCharge() {
		return nonWoodPackingCharge;
	}
	
	/**
	 * 用于计算的打木架费用字段
	 * @param nonWoodPackingCharge
	 */
	public void setNonWoodPackingCharge(BigDecimal nonWoodPackingCharge) {
		this.nonWoodPackingCharge = nonWoodPackingCharge;
	}
	// ===========lianhe/增加非木包装费/2017年1月4日/end=======

	/**
	 * 打木架货物费用
	 */
	public BigDecimal getStandCharge() {
		return standCharge;
	}

	/**
	 * 打木架货物费用
	 */
	public void setStandCharge(BigDecimal standCharge) {
		//四舍五入取整数
		this.standCharge = CalculateFeeTotalUtils.formatNumberInteger(standCharge);
	}

	/**
	 * 打木箱货物件数
	 */
	public Integer getBoxGoodsNum() {
		return boxGoodsNum;
	}

	/**
	 * 打木箱货物件数
	 */
	public void setBoxGoodsNum(Integer boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}

	/**
	 * 代打木箱要求
	 */
	public String getBoxRequirement() {
		return boxRequirement;
	}

	/**
	 * 代打木箱要求
	 */
	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}

	/**
	 * 打木箱货物尺寸
	 */
	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}

	/**
	 * 打木箱货物尺寸
	 */
	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}

	/**
	 * 打木箱货物体积
	 */
	public BigDecimal getBoxGoodsVolume() {
		return boxGoodsVolume;
	}

	/**
	 * 打木箱货物体积
	 */
	public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
	}

	/**
	 * 打木箱货物费用
	 */
	public BigDecimal getBoxCharge() {
		return boxCharge;
	}

	/**
	 * 打木箱货物费用
	 */
	public void setBoxCharge(BigDecimal boxCharge) {
		//四舍五入取整数
		this.boxCharge = CalculateFeeTotalUtils.formatNumberInteger(boxCharge);
	}

	/**
	 * 返款帐户开户名称
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * 返款帐户开户名称
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 返款帐户开户账户
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * 返款帐户开户账户
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * 返款帐户开户银行
	 */
	public String getAccountBank() {
		return accountBank;
	}

	/**
	 * 返款帐户开户银行
	 */
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	/**
	 * 计费重量
	 */
	public BigDecimal getBillWeight() {
		return billWeight;
	}

	/**
	 * 计费重量
	 */
	public void setBillWeight(BigDecimal billWeight) {
		this.billWeight = billWeight;
	}

	/**
	 * 接货费
	 */
	public BigDecimal getPickupFee() {
		return pickupFee;
	}

	/**
	 * 接货费
	 */
	public void setPickupFee(BigDecimal pickupFee) {
		//四舍五入取整数
		this.pickupFee = CalculateFeeTotalUtils.formatNumberInteger(pickupFee);
	}

	/**
	 * 装卸费
	 */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	/**
	 * 装卸费
	 */
	public void setServiceFee(BigDecimal serviceFee) {
		//四舍五入取整数
		this.serviceFee = serviceFee;
	}

	/**
	 * 装卸费费率
	 */
	public BigDecimal getServiceFeeRate() {
		return serviceFeeRate;
	}

	/**
	 * 装卸费费率
	 */
	public void setServiceFeeRate(BigDecimal serviceFeeRate) {
		//保留两位小数
		this.serviceFeeRate = CalculateFeeTotalUtils.formatNumberTwoDecimal(serviceFeeRate);
	}
	/**
	 * 劳务费费率
	 */
	private BigDecimal serviceRate;

	public BigDecimal getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}
	/**
	 * 预计到达时间
	 */
	public Date getPreArriveTime() {
		return preArriveTime;
	}

	/**
	 * 预计到达时间
	 */
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	/**
	 * 运输类型
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * 运输类型
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * 打印次数
	 */
	public Integer getPrintTimes() {
		return printTimes;
	}

	/**
	 * 打印次数
	 */
	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	/**
	 * 新增时间
	 */
	public Date getAddTime() {
		return addTime;
	}

	/**
	 * 新增时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * 是否为PDA运单
	 */
	public Boolean getIsPdaBill() {
		return isPdaBill;
	}

	/**
	 * 是否为PDA运单
	 */
	public void setIsPdaBill(Boolean isPdaBill) {
		this.isPdaBill = isPdaBill;
	}

	/**
	 * 开单时间
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * 开单时间
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * 运单处理状态
	 */
	public String getWaybillstatus() {
		return waybillstatus;
	}

	/**
	 * 运单处理状态
	 */
	public void setWaybillstatus(String waybillstatus) {
		this.waybillstatus = waybillstatus;
	}

	/**
	 * 弃货处理的运单号
	 */
	public List<String> getDerelictNum() {
		return derelictNum;
	}

	/**
	 * 弃货处理的运单号
	 */
	public void setDerelictNum(List<String> derelictNum) {
		this.derelictNum = derelictNum;
	}

	/**
	 * 提货方式标记-标记是否内部带货
	 */
	public String getReceiveMethodFlag() {
		return receiveMethodFlag;
	}

	/**
	 * 提货方式标记-标记是否内部带货
	 */
	public void setReceiveMethodFlag(String receiveMethodFlag) {
		this.receiveMethodFlag = receiveMethodFlag;
	}

	/**
	 * 空运货物类型
	 */
	public DataDictionaryValueVo getAirGoodsType() {
		return airGoodsType;
	}

	/**
	 * 空运货物类型
	 */
	public void setAirGoodsType(DataDictionaryValueVo airGoodsType) {
		this.airGoodsType = airGoodsType;
	}
	
	/**
	 * 获取 客户开户银行.
	 *
	 * @return the 客户开户银行
	 */
	public CusAccountEntity getOpenBank() {
		return openBank;
	}

	
	/**
	 * 设置 客户开户银行.
	 *
	 * @param openBank the new 客户开户银行
	 */
	public void setOpenBank(CusAccountEntity openBank) {
		this.openBank = openBank;
	}

	/**
	 *计算运费获得的包装费
	 */
	public void setCalculatedPackageFee(BigDecimal calculatedPackageFee) {
		//四舍五入取整数
		this.calculatedPackageFee = CalculateFeeTotalUtils.formatNumberInteger(calculatedPackageFee);
	}

	/**
	 *计算运费获得的包装费
	 */
	public BigDecimal getCalculatedPackageFee() {
		return calculatedPackageFee;
	}
	
	
	/**
	  * @return  the accountBankCode
	 */
	public String getAccountBankCode() {
		return accountBankCode;
	}

	/**
	 * @param accountBankCode the accountBankCode to set
	 */
	public void setAccountBankCode(String accountBankCode) {
		this.accountBankCode = accountBankCode;
	}

	/**
	 * 
	 * 获取其他费用集合
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:25:06
	 * @return
	 */
	public List<OtherChargeVo> getAllChargeList() {
		return allChargeList;
	}

	/**
	 * 
	 * 设置其他费用集合
	 * @author 025000-FOSS-helong
	 * @date 2013-1-14 上午11:25:06
	 * @return
	 */
	public void setAllChargeList(List<OtherChargeVo> allChargeList) {
		this.allChargeList = allChargeList;
	}

	/**
	 * 
	 * 送货进仓费名称
	 * @author 025000-FOSS-helong
	 * @date 2013-1-15 下午06:21:07
	 * @return
	 */
	public String getDeliveryStorgeName() {
		return deliveryStorgeName;
	}

	/**
	 * 
	 * 送货进仓费名称
	 * @author 025000-FOSS-helong
	 * @date 2013-1-15 下午06:21:24
	 * @param deliveryStorgeName
	 */
	public void setDeliveryStorgeName(String deliveryStorgeName) {
		this.deliveryStorgeName = deliveryStorgeName;
	}

	/**
	  * @return  the orderChannel
	 */
	public String getOrderChannel() {
		return orderChannel;
	}

	/**
	 * @param orderChannel the orderChannel to set
	 */
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	/**
	  * @return  the orderPayment
	 */
	public String getOrderPayment() {
		return orderPayment;
	}

	/**
	 * @param orderPayment the orderPayment to set
	 */
	public void setOrderPayment(String orderPayment) {
		this.orderPayment = orderPayment;
	}

	/**
	 * 
	 * 线路是否能开AB货
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午03:49:55
	 * @return
	 */
//	public Boolean getGoodsTypeIsAB() {
//		return goodsTypeIsAB;
//	}

	/**
	 * 
	 * 线路是否能开AB货
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午03:49:59
	 * @param goodsTypeIsAB
	 */
	/*public void setGoodsTypeIsAB(Boolean goodsTypeIsAB) {
		this.goodsTypeIsAB = goodsTypeIsAB;
	}
*/
	/**
	 * 
	 * 是否能开代收货款
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 上午10:36:59
	 * @return
	 */
	public String getCanAgentCollected() {
		return canAgentCollected;
	}

	/**
	 * 
	 * 是否能开代收货款
	 * @author 025000-FOSS-helong
	 * @date 2013-1-18 上午10:37:15
	 * @param canAgentCollected
	 */
	public void setCanAgentCollected(String canAgentCollected) {
		this.canAgentCollected = canAgentCollected;
	}


	/**
	 * 通过计算得到的送货费
	 * @return the calculateDeliveryGoodsFee
	 */
	public BigDecimal getCalculateDeliveryGoodsFee() {
		return calculateDeliveryGoodsFee;
	}

	/**
	 * 通过计算得到的送货费
	 * @param calculateDeliveryGoodsFee the calculateDeliveryGoodsFee to set
	 */
	public void setCalculateDeliveryGoodsFee(BigDecimal calculateDeliveryGoodsFee) {
		//四舍五入取整数
		this.calculateDeliveryGoodsFee = CalculateFeeTotalUtils.formatNumberInteger(calculateDeliveryGoodsFee);
	}

	/**
	 * 送货费上限
	 * @return the maxDeliveryGoodsFee
	 */
	public BigDecimal getMaxDeliveryGoodsFee() {
		return maxDeliveryGoodsFee;
	}

	/**
	 * 送货费上限
	 * @param maxDeliveryGoodsFee the maxDeliveryGoodsFee to set
	 */
	public void setMaxDeliveryGoodsFee(BigDecimal maxDeliveryGoodsFee) {
		this.maxDeliveryGoodsFee = maxDeliveryGoodsFee;
	}

	/**
	 * 
	 * 限保物品虚拟CODE
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 下午02:45:13
	 * @return
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * 
	 * 限保物品虚拟CODE
	 * @author 025000-FOSS-helong
	 * @date 2013-1-23 下午02:45:13
	 * @return
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	/**
	 * 收货部门开业时间
	 * @return the receiveOrgCreateTime
	 */
	public Date getReceiveOrgCreateTime() {
		return receiveOrgCreateTime;
	}

	/**
	 * 收货部门开业时间
	 * @param receiveOrgCreateTime the receiveOrgCreateTime to set
	 */
	public void setReceiveOrgCreateTime(Date receiveOrgCreateTime) {
		this.receiveOrgCreateTime = receiveOrgCreateTime;
	}

	/**
	 *  最低一票费用
	 * @return the minTransportFee
	 */
	public BigDecimal getMinTransportFee() {
		return minTransportFee;
	}

	/**
	 *  最低一票费用
	 * @param minTransportFee the minTransportFee to set
	 */
	public void setMinTransportFee(BigDecimal minTransportFee) {
		this.minTransportFee = minTransportFee;
	}

	/**
	 * 整车约车报价波动范围
	 * @return the wholeVehicleActualfeeFlowRange
	 */
	public BigDecimal getWholeVehicleActualfeeFlowRangeUp() {
		return wholeVehicleActualfeeFlowRangeUp;
	}

	/**
	 * 整车约车报价波动范围
	 * @param wholeVehicleActualfeeFlowRange the wholeVehicleActualfeeFlowRange to set
	 */
	public void setWholeVehicleActualfeeFlowRangeUp(
			BigDecimal wholeVehicleActualfeeFlowRange) {
		this.wholeVehicleActualfeeFlowRangeUp = wholeVehicleActualfeeFlowRange;
	}

	/**
	 * @return the wholeVehicleActualfeeFlowRangeLow
	 */
	public BigDecimal getWholeVehicleActualfeeFlowRangeLow() {
		return wholeVehicleActualfeeFlowRangeLow;
	}

	/**
	 * @param wholeVehicleActualfeeFlowRangeLow the wholeVehicleActualfeeFlowRangeLow to set
	 */
	public void setWholeVehicleActualfeeFlowRangeLow(
			BigDecimal wholeVehicleActualfeeFlowRangeLow) {
		this.wholeVehicleActualfeeFlowRangeLow = wholeVehicleActualfeeFlowRangeLow;
	}

	/**
	 * @return the changeColorTxt . 
	 */
	public Map<JTextField, String> getChangeColorTxt() {
		return changeColorTxt;
	}

	
	/**
	 *@param changeColorTxt the changeColorTxt to set.
	 */
	public void setChangeColorTxt(Map<JTextField, String> changeColorTxt) {
		this.changeColorTxt = changeColorTxt;
	}

	/**
	 * 
	 * 合同编号
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午09:57:56
	 * @return
	 */
	public String getAuditNo() {
		return auditNo;
	}

	/**
	 * 
	 * 合同编号
	 * @author 025000-FOSS-helong
	 * @date 2013-2-22 上午09:57:56
	 * @return
	 */
	public void setAuditNo(String auditNo) {
		this.auditNo = auditNo;
	}

	
	/**
	 * @return the deliveryCustomerArea .
	 */
	public String getDeliveryCustomerArea() {
		return deliveryCustomerArea;
	}

	
	/**
	 *@param deliveryCustomerArea the deliveryCustomerArea to set.
	 */
	public void setDeliveryCustomerArea(String deliveryCustomerArea) {
		this.deliveryCustomerArea = deliveryCustomerArea;
	}

	
	/**
	 * @return the deliveryCustomerAreaDto .
	 */
	public AddressFieldDto getDeliveryCustomerAreaDto() {
		return deliveryCustomerAreaDto;
	}

	
	/**
	 *@param deliveryCustomerAreaDto the deliveryCustomerAreaDto to set.
	 */
	public void setDeliveryCustomerAreaDto(AddressFieldDto deliveryCustomerAreaDto) {
		this.deliveryCustomerAreaDto = deliveryCustomerAreaDto;
	}

	
	/**
	 * @return the receiveCustomerArea .
	 */
	public String getReceiveCustomerArea() {
		return receiveCustomerArea;
	}

	
	/**
	 *@param receiveCustomerArea the receiveCustomerArea to set.
	 */
	public void setReceiveCustomerArea(String receiveCustomerArea) {
		this.receiveCustomerArea = receiveCustomerArea;
	}

	
	/**
	 * @return the receiveCustomerAreaDto .
	 */
	public AddressFieldDto getReceiveCustomerAreaDto() {
		return receiveCustomerAreaDto;
	}

	
	/**
	 *@param receiveCustomerAreaDto the receiveCustomerAreaDto to set.
	 */
	public void setReceiveCustomerAreaDto(AddressFieldDto receiveCustomerAreaDto) {
		this.receiveCustomerAreaDto = receiveCustomerAreaDto;
	}
	
	/**
	 * 
	 * 公里数
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午04:58:25
	 * @return
	 */
	public BigDecimal getKilometer() {
		return kilometer;
	}

	/**
	 * 
	 * 公里数
	 * @author 025000-FOSS-helong
	 * @date 2013-2-26 下午04:58:25
	 * @return
	 */
	public void setKilometer(BigDecimal kilometer) {
		this.kilometer = kilometer;
	}


	/**
	 * 
	 * 送货费集合
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午07:48:00
	 * @return
	 */
	public List<DeliverChargeEntity> getDeliverList() {
		return deliverList;
	}

	/**
	 * 
	 * 送货费集合
	 * @author 025000-FOSS-helong
	 * @date 2013-3-15 下午07:48:05
	 * @param deliverList
	 */
	public void setDeliverList(List<DeliverChargeEntity> deliverList) {
		this.deliverList = deliverList;
	}


	/**
	 * @return the contactAddressId .
	 */
	public String getContactAddressId() {
		return contactAddressId;
	}

	
	/**
	 *@param contactAddressId the contactAddressId to set.
	 */
	public void setContactAddressId(String contactAddressId) {
		this.contactAddressId = contactAddressId;
	}

	
	/**
	 * @return the oldWaybillNo .
	 */
	public String getOldWaybillNo() {
		return oldWaybillNo;
	}

	
	/**
	 *@param oldWaybillNo the oldWaybillNo to set.
	 */
	public void setOldWaybillNo(String oldWaybillNo) {
		this.oldWaybillNo = oldWaybillNo;
	}


	/**
	  * @return  the isPCPending
	 */
	public Boolean isPCPending() {
		return isPCPending;
	}


	/**
	 * @param isPCPending the isPCPending to set
	 */
	public void setPCPending(Boolean isPCPending) {
		this.isPCPending = isPCPending;
	}


	public String getAccountID() {
		return accountID;
	}


	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}


	public Boolean getIsPCPending() {
		return isPCPending;
	}


	public void setIsPCPending(Boolean isPCPending) {
		this.isPCPending = isPCPending;
	}


	public String getInnerNotesBack() {
		return innerNotesBack;
	}


	public void setInnerNotesBack(String innerNotesBack) {
		this.innerNotesBack = innerNotesBack;
	}
	public String getArriveCharge() {
		return arriveCharge;
	}
	public void setArriveCharge(String arriveCharge) {
		this.arriveCharge = arriveCharge;
	}
	/**
	 * @return the pickupCentralizedDeptCode
	 */
	public String getPickupCentralizedDeptCode() {
		return pickupCentralizedDeptCode;
	}
	/**
	 * @param pickupCentralizedDeptCode the pickupCentralizedDeptCode to set
	 */
	public void setPickupCentralizedDeptCode(String pickupCentralizedDeptCode) {
		this.pickupCentralizedDeptCode = pickupCentralizedDeptCode;
	}
	
	public BigDecimal getHandDeliveryFee() {
		return handDeliveryFee;
	}
	
	public void setHandDeliveryFee(BigDecimal handDeliveryFee) {
		this.handDeliveryFee = handDeliveryFee;
	}

	public String getInvoice() {
		return invoice;
	}



	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public List<LabeledGoodEntity> getLabeledGoodEntities() {
		return labeledGoodEntities;
	}

	public void setLabeledGoodEntities(List<LabeledGoodEntity> labeledGoodEntities) {
		this.labeledGoodEntities = labeledGoodEntities;
	}

	public Integer getSalverGoodsNum() {
		return salverGoodsNum;
	}

	public void setSalverGoodsNum(Integer salverGoodsNum) {
		this.salverGoodsNum = salverGoodsNum;
	}

	public String getSalverRequirement() {
		return salverRequirement;
	}

	public void setSalverRequirement(String salverRequirement) {
		this.salverRequirement = salverRequirement;
	}

	public BigDecimal getSalverGoodsCharge() {
		return salverGoodsCharge;
	}

	public void setSalverGoodsCharge(BigDecimal salverGoodsCharge) {
		this.salverGoodsCharge = salverGoodsCharge;
	}

	public String getSalverChargeId() {
		return salverChargeId;
	}

	public void setSalverChargeId(String salverChargeId) {
		this.salverChargeId = salverChargeId;
	}

	public String getSalverChargeCode() {
		return salverChargeCode;
	}

	public void setSalverChargeCode(String salverChargeCode) {
		this.salverChargeCode = salverChargeCode;
	}

	public BigDecimal getPreSalverGoodsCharge() {
		return preSalverGoodsCharge;
	}

	public void setPreSalverGoodsCharge(BigDecimal preSalverGoodsCharge) {
		this.preSalverGoodsCharge = preSalverGoodsCharge;
	}

	public boolean isSubPreSalverCharge() {
		return isSubPreSalverCharge;
	}

	public void setSubPreSalverCharge(boolean isSubPreSalverCharge) {
		this.isSubPreSalverCharge = isSubPreSalverCharge;
	}
	
	public boolean isHandInsuranceFee() {
		return isHandInsuranceFee;
	}
	
	public void setHandInsuranceFee(boolean isHandInsuranceFee) {
		this.isHandInsuranceFee = isHandInsuranceFee;
	}

	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}

	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}

	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}
	public String getCouponRankType() {
		return couponRankType;
	}

	public void setCouponRankType(String couponRankType) {
		this.couponRankType = couponRankType;
	}
	
	public BigDecimal getMaxPickupFee() {
		return maxPickupFee;
	}
	
	public void setMaxPickupFee(BigDecimal maxPickupFee) {
		this.maxPickupFee = maxPickupFee;
	}
	
	public BigDecimal getMinPickupFee() {
		return minPickupFee;
	}

	public void setMinPickupFee(BigDecimal minPickupFee) {
		this.minPickupFee = minPickupFee;
	}
	public DataDictionaryValueVo getInvoiceMode() {
		return invoiceMode;
	}

	public void setInvoiceMode(DataDictionaryValueVo invoiceMode) {
		this.invoiceMode = invoiceMode;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getInnerPickupFeeBurdenDept() {
		return innerPickupFeeBurdenDept;
	}

	public void setInnerPickupFeeBurdenDept(String innerPickupFeeBurdenDept) {
		this.innerPickupFeeBurdenDept = innerPickupFeeBurdenDept;
	}
	public Boolean getIsBigGoods() {
		return isBigGoods;
	}

	public void setIsBigGoods(Boolean isBigGoods) {
		this.isBigGoods = isBigGoods;
	}

	public Boolean getIsExhibitCargo() {
		return isExhibitCargo;
	}

	public void setIsExhibitCargo(Boolean isExhibitCargo) {
		this.isExhibitCargo = isExhibitCargo;
	}

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
	public String getPackageRemark() {
		return packageRemark;
	}

	public void setPackageRemark(String packageRemark) {
		this.packageRemark = packageRemark;
	}

	public Boolean getBusinessZone() {
		return businessZone;
	}

	public void setBusinessZone(Boolean businessZone) {
		this.businessZone = businessZone;
	}

	public Boolean getResidentialDistrict() {
		return residentialDistrict;
	}

	public void setResidentialDistrict(Boolean residentialDistrict) {
		this.residentialDistrict = residentialDistrict;
	}
	public BigDecimal getWholeVehicleFeeChange() {
		return wholeVehicleFeeChange;
	}

	public void setWholeVehicleFeeChange(BigDecimal wholeVehicleFeeChange) {
		this.wholeVehicleFeeChange = wholeVehicleFeeChange;
	}

	public BigDecimal getWholeVehicleOtherCostParameter() {
		return wholeVehicleOtherCostParameter;
	}

	public void setWholeVehicleOtherCostParameter(
			BigDecimal wholeVehicleOtherCostParameter) {
		this.wholeVehicleOtherCostParameter = wholeVehicleOtherCostParameter;
	}

	public BigDecimal getWholeVehiclePackageFeeParameter() {
		return wholeVehiclePackageFeeParameter;
	}

	public void setWholeVehiclePackageFeeParameter(
			BigDecimal wholeVehiclePackageFeeParameter) {
		this.wholeVehiclePackageFeeParameter = wholeVehiclePackageFeeParameter;
	}

	public BigDecimal getWholeVehicleWeightParameter() {
		return wholeVehicleWeightParameter;
	}

	public void setWholeVehicleWeightParameter(
			BigDecimal wholeVehicleWeightParameter) {
		this.wholeVehicleWeightParameter = wholeVehicleWeightParameter;
	}

	public BigDecimal getWholeVehicleCarCostParameter() {
		return wholeVehicleCarCostParameter;
	}

	public void setWholeVehicleCarCostParameter(
			BigDecimal wholeVehicleCarCostParameter) {
		this.wholeVehicleCarCostParameter = wholeVehicleCarCostParameter;
	}

	public BigDecimal getWholeVehicleHumanCostParameter() {
		return wholeVehicleHumanCostParameter;
	}

	public void setWholeVehicleHumanCostParameter(
			BigDecimal wholeVehicleHumanCostParameter) {
		this.wholeVehicleHumanCostParameter = wholeVehicleHumanCostParameter;
	}
	//wutao - 200945 GETTER AND SETTER Method
	public String getStartCentralizedSettlement() {
		return startCentralizedSettlement;
	}

	public void setStartCentralizedSettlement(String startCentralizedSettlement) {
		this.startCentralizedSettlement = startCentralizedSettlement;
	}

	public String getArriveCentralizedSettlement() {
		return arriveCentralizedSettlement;
	}

	public void setArriveCentralizedSettlement(String arriveCentralizedSettlement) {
		this.arriveCentralizedSettlement = arriveCentralizedSettlement;
	}

	public String getStartContractOrgCode() {
		return startContractOrgCode;
	}

	public void setStartContractOrgCode(String startContractOrgCode) {
		this.startContractOrgCode = startContractOrgCode;
	}

	public String getArriveContractOrgCode() {
		return arriveContractOrgCode;
	}

	public void setArriveContractOrgCode(String arriveContractOrgCode) {
		this.arriveContractOrgCode = arriveContractOrgCode;
	}

	public String getStartReminderOrgCode() {
		return startReminderOrgCode;
	}

	public void setStartReminderOrgCode(String startReminderOrgCode) {
		this.startReminderOrgCode = startReminderOrgCode;
	}

	public String getArriveReminderOrgCode() {
		return arriveReminderOrgCode;
	}

	public void setArriveReminderOrgCode(String arriveReminderOrgCode) {
		this.arriveReminderOrgCode = arriveReminderOrgCode;
	}

	public String getStartContractOrgName() {
		return startContractOrgName;
	}

	public void setStartContractOrgName(String startContractOrgName) {
		this.startContractOrgName = startContractOrgName;
	}

	public String getArriveContractOrgName() {
		return arriveContractOrgName;
	}

	public void setArriveContractOrgName(String arriveContractOrgName) {
		this.arriveContractOrgName = arriveContractOrgName;
	}
	//end
	/**
	 *定价优化项目：降价返券需求
	 *
	 *@author Foss-206860
	 * */
	private QueryBillCacilateDto queryBillCacilateDto;

	public QueryBillCacilateDto getQueryBillCacilateDto() {
		return queryBillCacilateDto;
	}

	public void setQueryBillCacilateDto(QueryBillCacilateDto queryBillCacilateDto) {
		this.queryBillCacilateDto = queryBillCacilateDto;
	}
	/**
	  * 是否电子发票
	  * @author:218371-foss-zhaoyanjun
	  * @date:2014-10-21下午14:12
	  */
	private String isElectronicInvoice;
	
	/**
	 * 发票手机号
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-21下午14:12
	 */
	private String invoiceMobilePhone;
	
	/**
	 * 发票发送邮箱
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-10-21下午14:23
	 */
	private String email;

	public String getIsElectronicInvoice() {
		return isElectronicInvoice;
	}

	public void setIsElectronicInvoice(String isElectronicInvoice) {
		this.isElectronicInvoice = isElectronicInvoice;
	}

	public String getInvoiceMobilePhone() {
		return invoiceMobilePhone;
	}

	public void setInvoiceMobilePhone(String invoiceMobilePhone) {
		this.invoiceMobilePhone = invoiceMobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 特安客户保价上限
	 * @author 218371-foss-zhaoyanjun
	 * @date 2014-10-11 上午08:11
	 */
	private BigDecimal vipInsuranceAmount;
	
	public BigDecimal getVipInsuranceAmount() {
		return vipInsuranceAmount;
	}
	
	public void setVipInsuranceAmount(BigDecimal vipInsuranceAmount) {
		this.vipInsuranceAmount = vipInsuranceAmount;
	}
	
	/**
	 * 特安客户代收货款上限
	 * @author 218371-foss-zhaoyanjun
	 * @date 2014-10-15 上午11:10
	 */
	private BigDecimal vipCollectionPaymentLimit;

	public BigDecimal getVipCollectionPaymentLimit() {
		return vipCollectionPaymentLimit;
	}

	public void setVipCollectionPaymentLimit(BigDecimal vipCollectionPaymentLimit) {
		this.vipCollectionPaymentLimit = vipCollectionPaymentLimit;
	}
	
	/**
	 * 打包装标签中的各属性
	 * @author 218371-foss-zhaoyanjun
	 * @date 2014-11-1上午08:43
	 * @remarks 纸箱,纤袋价格,打包装其他费用属性未加
	 */
	//纸箱一号客户
	 private Integer paperBoxOne;
	 
	 //纸箱二号客户
	 private Integer paperBoxTwo;
	 
	 //纸箱三号客户
	 private Integer paperBoxThree;
	 
	 //纸箱四号客户
	 private Integer paperBoxFour;
	 
	 //纤袋一号蓝
	 private Integer fibelBagBlueOne;
	 
	 //纤袋二号蓝
	 private Integer fibelBagBlueTwo;
	 
	 //纤袋三号蓝
	 private Integer fibelBagBlueThree;
	 
	 //纤袋四号蓝
	 private Integer fibelBagBlueFour;
	 
	 //纤袋一号白
	 private Integer fibelBagWhiteOne;
	 
	 //纤袋二号白
	 private Integer fibelBagWhiteTwo;
	 
	 //纤袋三号白
	 private Integer fibelBagWhiteThree;
	 
	 //纤袋四号白
	 private Integer fibelBagWhiteFour;
	 
	 //纤袋五号布匹白
	 private Integer fibelBagWhiteClothFive;
	 
	 //纤袋六号布匹白
	 private Integer fibelBagWhiteClothSix;
	 
	 //打包装价格合计
	 private BigDecimal packingTotle;
	 
	//纸箱总价
	private BigDecimal paperBoxTotlePrice;
		
	//纤袋总价
	private BigDecimal fibelBagTotlePrice;
	
	//其它费用
	private BigDecimal otherTotle;
	
	//缓冲物费用
	private BigDecimal bufferPrice;
		
	//折扣率
	private BigDecimal discountRate;

	public BigDecimal getOtherTotle() {
		return otherTotle;
	}

	public void setOtherTotle(BigDecimal otherTotle) {
		this.otherTotle = otherTotle;
	}

	public BigDecimal getPaperBoxTotlePrice() {
		return paperBoxTotlePrice;
	}

	public void setPaperBoxTotlePrice(BigDecimal paperBoxTotlePrice) {
		this.paperBoxTotlePrice = paperBoxTotlePrice;
	}

	public BigDecimal getFibelBagTotlePrice() {
		return fibelBagTotlePrice;
	}

	public void setFibelBagTotlePrice(BigDecimal fibelBagTotlePrice) {
		this.fibelBagTotlePrice = fibelBagTotlePrice;
	}

	public Integer getPaperBoxOne() {
		return paperBoxOne;
	}

	public void setPaperBoxOne(Integer paperBoxOne) {
		this.paperBoxOne = paperBoxOne;
	}

	public Integer getPaperBoxTwo() {
		return paperBoxTwo;
	}

	public void setPaperBoxTwo(Integer paperBoxTwo) {
		this.paperBoxTwo = paperBoxTwo;
	}

	public Integer getPaperBoxThree() {
		return paperBoxThree;
	}

	public void setPaperBoxThree(Integer paperBoxThree) {
		this.paperBoxThree = paperBoxThree;
	}

	public Integer getPaperBoxFour() {
		return paperBoxFour;
	}

	public void setPaperBoxFour(Integer paperBoxFour) {
		this.paperBoxFour = paperBoxFour;
	}

	public Integer getFibelBagBlueOne() {
		return fibelBagBlueOne;
	}

	public void setFibelBagBlueOne(Integer fibelBagBlueOne) {
		this.fibelBagBlueOne = fibelBagBlueOne;
	}

	public Integer getFibelBagBlueTwo() {
		return fibelBagBlueTwo;
	}

	public void setFibelBagBlueTwo(Integer fibelBagBlueTwo) {
		this.fibelBagBlueTwo = fibelBagBlueTwo;
	}

	public Integer getFibelBagBlueThree() {
		return fibelBagBlueThree;
	}

	public void setFibelBagBlueThree(Integer fibelBagBlueThree) {
		this.fibelBagBlueThree = fibelBagBlueThree;
	}

	public Integer getFibelBagBlueFour() {
		return fibelBagBlueFour;
	}

	public void setFibelBagBlueFour(Integer fibelBagBlueFour) {
		this.fibelBagBlueFour = fibelBagBlueFour;
	}

	public Integer getFibelBagWhiteOne() {
		return fibelBagWhiteOne;
	}

	public void setFibelBagWhiteOne(Integer fibelBagWhiteOne) {
		this.fibelBagWhiteOne = fibelBagWhiteOne;
	}

	public Integer getFibelBagWhiteTwo() {
		return fibelBagWhiteTwo;
	}

	public void setFibelBagWhiteTwo(Integer fibelBagWhiteTwo) {
		this.fibelBagWhiteTwo = fibelBagWhiteTwo;
	}

	public Integer getFibelBagWhiteThree() {
		return fibelBagWhiteThree;
	}

	public void setFibelBagWhiteThree(Integer fibelBagWhiteThree) {
		this.fibelBagWhiteThree = fibelBagWhiteThree;
	}

	public Integer getFibelBagWhiteFour() {
		return fibelBagWhiteFour;
	}

	public void setFibelBagWhiteFour(Integer fibelBagWhiteFour) {
		this.fibelBagWhiteFour = fibelBagWhiteFour;
	}

	public Integer getFibelBagWhiteClothFive() {
		return fibelBagWhiteClothFive;
	}

	public void setFibelBagWhiteClothFive(Integer fibelBagWhiteClothFive) {
		this.fibelBagWhiteClothFive = fibelBagWhiteClothFive;
	}

	public Integer getFibelBagWhiteClothSix() {
		return fibelBagWhiteClothSix;
	}

	public void setFibelBagWhiteClothSix(Integer fibelBagWhiteClothSix) {
		this.fibelBagWhiteClothSix = fibelBagWhiteClothSix;
	}

	public BigDecimal getPackingTotle() {
		return packingTotle;
	}

	public void setPackingTotle(BigDecimal packingTotle) {
		this.packingTotle = packingTotle;
	}
	public String getCaculateType() {
		return caculateType;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}
	public BigDecimal getExpressContinuediscount() {
		return expressContinuediscount;
	}

	public void setExpressContinuediscount(BigDecimal expressContinuediscount) {
		this.expressContinuediscount = expressContinuediscount;
	}
	
	//菜鸟新需求返货折扣
    private Boolean isCainiao;//是否满足条件
    private String returnWaybillNo;//原始单号
    private String oldreceiveCustomerCode;//原收货人的客户编码
    private String OriginalReceiveOrgCode;//原单号收货部门编码
    private Date returnbilltime;//原始开单时间
    private BigDecimal returnTransportFee;//原单公布价费
    private BigDecimal returnInsuranceFee;//原单保价费
     
	
	public BigDecimal getReturnTransportFee() {
		return returnTransportFee;
	}

	public void setReturnTransportFee(BigDecimal returnTransportFee) {
		this.returnTransportFee = returnTransportFee;
	}

	

	public BigDecimal getReturnInsuranceFee() {
		return returnInsuranceFee;
	}

	public void setReturnInsuranceFee(BigDecimal returnInsuranceFee) {
		this.returnInsuranceFee = returnInsuranceFee;
	}

	public Date getReturnbilltime() {
		return returnbilltime;
	}

	public void setReturnbilltime(Date returnbilltime) {
		this.returnbilltime = returnbilltime;
	}

	public String getOldreceiveCustomerCode() {
		return oldreceiveCustomerCode;
	}

	public void setOldreceiveCustomerCode(String oldreceiveCustomerCode) {
		this.oldreceiveCustomerCode = oldreceiveCustomerCode;
	}

	public Boolean getIsCainiao() {
		return isCainiao;
	}


	public void setIsCainiao(Boolean isCainiao) {
		this.isCainiao = isCainiao;
	}

	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}

	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}

	public String getOriginalReceiveOrgCode() {
		return OriginalReceiveOrgCode;
	}

	public void setOriginalReceiveOrgCode(String originalReceiveOrgCode) {
		OriginalReceiveOrgCode = originalReceiveOrgCode;
	}
	
	/**
	 * 根据Dmana-10888增加bean状态
	 * @author:218371-foss-zhaoyanjun
	 * @dateL2015-03-24上午11:28
	 */
	private String beanState;

	public String getBeanState() {
		return beanState;
	}

	public void setBeanState(String beanState) {
		this.beanState = beanState;
	}
	
	/**
	 * 根据Dmana-10888备份发票标记
	 * @author:218371-foss-zhaoyanjun
	 * @dateL2015-03-24上午11:28
	 */
	private String backupInvoice;

	public String getBackupInvoice() {
		return backupInvoice;
	}

	public void setBackupInvoice(String backupInvoice) {
		this.backupInvoice = backupInvoice;
	}
	
	/**
	 * Dmana-10888发货客户编码备份
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-03-24
	 */
	private String backupDeliveryCustomerCode;

	public String getBackupDeliveryCustomerCode() {
		return backupDeliveryCustomerCode;
	}

	public void setBackupDeliveryCustomerCode(String backupDeliveryCustomerCode) {
		this.backupDeliveryCustomerCode = backupDeliveryCustomerCode;
	}

	public String getChangeVolume() {
		return changeVolume;
	}

	public void setChangeVolume(String changeVolume) {
		this.changeVolume = changeVolume;
	}


	/**
	 * 交易流水号
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-22下午18:55
	 */
	private String transactionSerialNumber;

	public String getTransactionSerialNumber() {
		return transactionSerialNumber;
	}

	public void setTransactionSerialNumber(String transactionSerialNumber) {
		this.transactionSerialNumber = transactionSerialNumber;
	}
	
	/**
	 * Dmana-9885巨商汇客户或者阿里巴巴客户是否满足特殊运费条件
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-05
	 */
	private boolean specialChannelFreight;

	public boolean getSpecialChannelFreight() {
		return specialChannelFreight;
	}

	public void setSpecialChannelFreight(boolean specialChannelFreight) {
		this.specialChannelFreight = specialChannelFreight;
	}
	
	/**
	 * Dmana-9885运费
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02 下午16:49
	 */
	private BigDecimal crmTransportFee;
	
	/**
	 * Dmana-9885重量
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02下午16:50
	 */
	private BigDecimal crmWeight;
	
	/**
	 * Dmana-9885体积
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-02下午16:52
	 */
	private BigDecimal crmVolume;

	public BigDecimal getCrmTransportFee() {
		return crmTransportFee;
	}

	public void setCrmTransportFee(BigDecimal crmTransportFee) {
		this.crmTransportFee = crmTransportFee;
	}

	public BigDecimal getCrmWeight() {
		return crmWeight;
	}

	public void setCrmWeight(BigDecimal crmWeight) {
		this.crmWeight = crmWeight;
	}

	public BigDecimal getCrmVolume() {
		return crmVolume;
	}

	public void setCrmVolume(BigDecimal crmVolume) {
		this.crmVolume = crmVolume;
	}

	public String getIsElectricityToEnjoy() {
		return isElectricityToEnjoy;
	}

	public void setIsElectricityToEnjoy(String isElectricityToEnjoy) {
		this.isElectricityToEnjoy = isElectricityToEnjoy;
	}

	public String getReturnBillReason() {
		return returnBillReason;
	}

	public void setReturnBillReason(String returnBillReason) {
		this.returnBillReason = returnBillReason;
	}
	public DataDictionaryValueVo getSpecialValueAddedServiceType() {
		return specialValueAddedServiceType;
	}

	public void setSpecialValueAddedServiceType(
			DataDictionaryValueVo specialValueAddedServiceType) {
		this.specialValueAddedServiceType = specialValueAddedServiceType;
	}

	public List<InstallationEntity> getInstallationEntityList() {
		return installationEntityList;
	}

	public void setInstallationEntityList(
			List<InstallationEntity> installationEntityList) {
		this.installationEntityList = installationEntityList;
	}
	

	public boolean getCheckloadLineName() {
		return checkloadLineName;
	}

	public void setCheckloadLineName(boolean checkloadLineName) {
		this.checkloadLineName = checkloadLineName;
	}

	public BigDecimal getBufferPrice() {
		return bufferPrice;
	}

	public void setBufferPrice(BigDecimal bufferPrice) {
		this.bufferPrice = bufferPrice;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	public Integer getOldWood() {
		return oldWood;
	}

	public void setOldWood(Integer oldWood) {
		this.oldWood = oldWood;
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
	
	
	//合伙人项目增加字段
	/**
	 * 保价费
	 */
	private BigDecimal supportFee ;
	
	/**
	 * 代收手续费
	 */
	private BigDecimal collectingFee ;

	public BigDecimal getSupportFee() {
		return supportFee;
	}

	public void setSupportFee(BigDecimal supportFee) {
		this.supportFee = supportFee;
	}

	public BigDecimal getCollectingFee() {
		return collectingFee;
	}

	public void setCollectingFee(BigDecimal collectingFee) {
		this.collectingFee = collectingFee;
	}
	
	/**
	 * 修改前的费用
	 */
//	private PtpWaybillOrgVo ptpWaybillOrgVo ;
//
//	public PtpWaybillOrgVo getPtpWaybillOrgVo() {
//		return ptpWaybillOrgVo;
//	}
//
//	public void setPtpWaybillOrgVo(PtpWaybillOrgVo ptpWaybillOrgVo) {
//		this.ptpWaybillOrgVo = ptpWaybillOrgVo;
//	}
	
	//0:成本  1：提成  2：成本和提成
	private String feeType;

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	/**
	 * 是否更改单 Y：更改单；N:开单
	 */
	private String isChanged ;

	public String getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(String isChanged) {
		this.isChanged = isChanged;
	}
		
	//2016年4月15日 15:25:06 葛亮亮
	//代收货款是否清空 
	private Boolean isCodZero = false;
	//如果保价费和接货费被设置为开单时页面提交值，则需要从合伙人折前费用表中取出开单时存入的日
	private Boolean isBeginFee = false;
	//折前公布运价费
	private BigDecimal zqTransportFee;
	//折前接货费
	private BigDecimal zqPickUpFee;
	//折前保价费
	private BigDecimal zqInsuranceFee;
	
	//合伙人到达加收金额 2016年9月1日 09:47:26 葛亮亮
	private BigDecimal overTransportFee;
	//合伙人到达加收金额费率
	private BigDecimal overTransportRate;
	//折前合伙人到达加收金额
	private BigDecimal zqOverTransportFee;
	
	//是否重新计算中转费 20160907 xingjun 判断推送PTP加收费,以及改单修改中转信息时重新计算运费判断使用 true 重新计算
	private boolean isTfrFeeCalculate;
	
	public boolean getIsTfrFeeCalculate() {
		return isTfrFeeCalculate;
	}

	public void setIsTfrFeeCalculate(boolean isTfrFeeCalculate) {
		this.isTfrFeeCalculate = isTfrFeeCalculate;
	}

	public Boolean getIsCodZero() {
		return isCodZero;
	}

	public Boolean getIsBeginFee() {
		return isBeginFee;
	}

	public void setIsCodZero(Boolean isCodZero) {
		this.isCodZero = isCodZero;
	}

	public void setIsBeginFee(Boolean isBeginFee) {
		this.isBeginFee = isBeginFee;
	}

	public BigDecimal getZqTransportFee() {
		return zqTransportFee;
	}

	public BigDecimal getZqPickUpFee() {
		return zqPickUpFee;
	}

	public BigDecimal getZqInsuranceFee() {
		return zqInsuranceFee;
	}

	public void setZqTransportFee(BigDecimal zqTransportFee) {
		this.zqTransportFee = zqTransportFee;
	}

	public void setZqPickUpFee(BigDecimal zqPickUpFee) {
		this.zqPickUpFee = zqPickUpFee;
	}

	public void setZqInsuranceFee(BigDecimal zqInsuranceFee) {
		this.zqInsuranceFee = zqInsuranceFee;
	}

	private PtpWaybillOrgVo ptpWaybillOrgVo ;
	
	private PtpWaybillInfoOrgVo ptpWaybillInfoOrgVo ;

	public PtpWaybillOrgVo getPtpWaybillOrgVo() {
		return ptpWaybillOrgVo;
	}

	public void setPtpWaybillOrgVo(PtpWaybillOrgVo ptpWaybillOrgVo) {
		this.ptpWaybillOrgVo = ptpWaybillOrgVo;
	}

	public PtpWaybillInfoOrgVo getPtpWaybillInfoOrgVo() {
		return ptpWaybillInfoOrgVo;
	}

	public void setPtpWaybillInfoOrgVo(PtpWaybillInfoOrgVo ptpWaybillInfoOrgVo) {
		this.ptpWaybillInfoOrgVo = ptpWaybillInfoOrgVo;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public IntelligenceBillTimeGather getIbtg() {
		return ibtg;
	}

	public void setIbtg(IntelligenceBillTimeGather ibtg) {
		this.ibtg = ibtg;
	}
	
	private String waybillType ;

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	//是否精准计价
	private boolean isAccurateCost;
	

	public String getIsAccuratePackage() {
		return isAccuratePackage;
	}

	public void setIsAccuratePackage(String isAccuratePackage) {
		this.isAccuratePackage = isAccuratePackage;
	}

	public BigDecimal getOverTransportFee() {
		return overTransportFee;
	}

	public void setOverTransportFee(BigDecimal overTransportFee) {
		this.overTransportFee = overTransportFee;
	}

	public BigDecimal getZqOverTransportFee() {
		return zqOverTransportFee;
	}

	public void setZqOverTransportFee(BigDecimal zqOverTransportFee) {
		this.zqOverTransportFee = zqOverTransportFee;
	}

	public BigDecimal getOverTransportRate() {
		return overTransportRate;
	}

	public void setOverTransportRate(BigDecimal overTransportRate) {
		this.overTransportRate = overTransportRate;
	}
	
	public boolean isAccurateCost() {
		return isAccurateCost;
	}
	public void setAccurateCost(boolean isAccurateCost) {
		this.isAccurateCost = isAccurateCost;
	}
	
	// ================优化内容:新增字段"异常操作费"/时间:2016年11月1日下午9:24:23/LianHe/start================
	
	/**
	 * 新计算出的折前异常操作费，
	 * 用于存储系统计算出来的折前异常操作费，满足合伙人推送PTP的需求(合伙人三期开发要求推送系统计算的折前异常操作费，而非修改后的异常操作费)
	 */
	public BigDecimal newZqExceptionOperateFee;
	
	/**
	 * 原已存在的折前异常操作费，
	 * 用于存储系统计算出来的折前异常操作费，满足合伙人推送PTP的需求(合伙人三期开发要求推送系统计算的折前异常操作费，而非修改后的异常操作费)
	 */
	public BigDecimal oldZqExceptionOperateFee;

	public BigDecimal getNewZqExceptionOperateFee() {
		return newZqExceptionOperateFee;
	}

	public void setNewZqExceptionOperateFee(BigDecimal newZqExceptionOperateFee) {
		this.newZqExceptionOperateFee = newZqExceptionOperateFee;
	}

	public BigDecimal getOldZqExceptionOperateFee() {
		return oldZqExceptionOperateFee;
	}

	public void setOldZqExceptionOperateFee(BigDecimal oldZqExceptionOperateFee) {
		this.oldZqExceptionOperateFee = oldZqExceptionOperateFee;
	}
	
	// ================优化内容:/时间:2016年11月1日下午9:24:48/LianHe/end================
	 /**
	    * 偏线费  352676
	    */
	   private BigDecimal partialTransportFee;

		public BigDecimal getPartialTransportFee() {
			return partialTransportFee;
		}

		public void setPartialTransportFee(BigDecimal partialTransportFee) {
			this.partialTransportFee = partialTransportFee;
		}
	   
		/**
		 * 折前偏线费用    by:352676
		 */
		private BigDecimal zqPartialTransportFee;

		public BigDecimal getZqPartialTransportFee() {
			return zqPartialTransportFee;
		}

		public void setZqPartialTransportFee(BigDecimal zqPartialTransportFee) {
			this.zqPartialTransportFee = zqPartialTransportFee;
		}
		/**
		 * 异地调货保存的原发货客户编码
		 */
		private String oldDeliveryCustomerCode;
		public String getOldDeliveryCustomerCode() {
			return oldDeliveryCustomerCode;
		}
		public void setOldDeliveryCustomerCode(String oldDeliveryCustomerCode) {
			this.oldDeliveryCustomerCode = oldDeliveryCustomerCode;
		}
}
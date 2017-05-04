/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.util.List;

/**
 * 偏线扩展对象，用于查询返回或者查询参数
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午9:12:07
 */
public class ExternalBillDto extends ExternalBillEntity {

	private static final long serialVersionUID = -5368891875440086975L;

	// 运单相关信息
	/**
	 * 是否自提
	 */
	private String beAutoDelivery;
	/**
	 * 运单号WAYBILL_NO
	 */
	private String waybillNo;
	/**
	 * 付款方式PAID_METHOD
	 */
	private String paidMethod;
	/**
	 * 到付金额(运单)TO_PAY_AMOUNT
	 */
	private String toPayAmount;
	/**
	 * 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER.AGENT_COMPANY_NAME
	 */
	private String origOrgName;
	/**
	 * 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH.AGENT_DEPT_NAME
	 */
	private String agentDeptName;
	/**
	 * 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH.CONTACT_PHONE
	 */
	private String contactPhone;
	/**
	 * 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH.ADDRESS
	 */
	private String address;
	/**
	 * 外发部门//制单人所在的部门
	 */
	// private String waifabumen;
	/**
	 * 外发部门//制单人所在的部门名称
	 */
	// private String waifabumenName;
	/**
	 * 录入员
	 */
	private String registerUser;
	/**
	 * 目的站//接送货运单TARGET_ORG_CODE
	 */
	private String targetOrgCode;
	/**
	 * 收货日期//开单时间CREATE_TIME
	 */
	private String handGoodsTime;
	/**
	 * 收货部门//开单组织CREATE_ORG_CODE
	 */
	private String createOrgCode;
	/**
	 * 重量//货物总重量GOODS_WEIGHT_TOTAL
	 */
	private String goodsWeightTotal;
	/**
	 * 体积//货物总体积GOODS_VOLUME_TOTAL
	 */
	private String goodsVolumeTotal;
	/**
	 * 件数//货物总件数GOODS_QTY_TOTAL
	 */
	private String goodsQtyTotal;
	/**
	 * 运费//公布价运费TRANSPORT_FEE
	 */
	private String transportFee;
	/**
	 * 货物名称//货物名称GOODS_NAME
	 */
	private String goodsName;
	/**
	 * 保险费//保价费INSURANCE__FEE
	 */
	private String insuranceFee;
	/**
	 * 包装//货物包装GOODS_PACKAGE
	 */
	private String goodsPackage;
	/**
	 * 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME
	 */
	private String deliveryCustomerName;
	/**
	 * 保险价值//保价声明价值INSURANCE_AMOUNT
	 */
	private String insuranceAmount;
	/**
	 * 运输事项//对内备注,对外备注合体
	 */
	private String yunshushixiang;
	/**
	 * 开单金额//总费用TOTAL_FEE
	 */
	private String totalFee;
	/**
	 * 代收货款//代收货款COD_AMOUNT
	 */
	private String codAmount;
	/**
	 * 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME
	 */
	private String receiveCustomerName;
	/**
	 * 代理电话//联系电话T_BAS_BUSINESS_PARTNER.CONTACT_PHONE
	 */
	private String partnerContactPhone;
	/**
	 * 录入日期从
	 */
	private String registerTimeFrom;
	/**
	 * 录入日期到
	 */
	private String registerTimeTo;
	/**
	 * 交接类型
	 */
	private String handoverType;
	/**
	 * 审核状态（数值型）
	 */
	private int adtStatus;
	/**
	 * 运单状态
	 */
	private String active;
	/**
	 * 状态列表
	 */
	private List<String> list;
	/**
	 * 交接单状态
	 */
	private List<Integer> billStatuslist;
	/**
	 * 查询过滤部门编码
	 */
	private String filterOrgCode;
	/**
	 * 审核状态全部查询
	 */
	private String auditAll;

	private List<String> filterOrgCodes;
	
	/**
	 * 是否初始化
	 */
	private String isInit;

	/**
	 * ID
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-12-25 下午6:17:28
	 * @see com.deppon.foss.module.transfer.partialline.api.shared.domain.ExternalBillEntity#getWaybillNo()
	 */
	@Override
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * ID
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-12-25 下午6:17:28
	 * @see com.deppon.foss.module.transfer.partialline.api.shared.domain.ExternalBillEntity#setWaybillNo(java.lang.String)
	 */
	@Override
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 付款方式PAID_METHOD.
	 * 
	 * @return the 付款方式PAID_METHOD
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * 设置 付款方式PAID_METHOD.
	 * 
	 * @param paidMethod
	 *            the new 付款方式PAID_METHOD
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * 获取 到付金额(运单)TO_PAY_AMOUNT.
	 * 
	 * @return the 到付金额(运单)TO_PAY_AMOUNT
	 */
	public String getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * 设置 到付金额(运单)TO_PAY_AMOUNT.
	 * 
	 * @param toPayAmount
	 *            the new 到付金额(运单)TO_PAY_AMOUNT
	 */
	public void setToPayAmount(String toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * 获取 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER.
	 * 
	 * @return the 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * 设置 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER.
	 * 
	 * @param origOrgName
	 *            the new 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * 获取 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @return the 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public String getAgentDeptName() {
		return agentDeptName;
	}

	/**
	 * 设置 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @param agentDeptName
	 *            the new 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public void setAgentDeptName(String agentDeptName) {
		this.agentDeptName = agentDeptName;
	}

	/**
	 * 获取 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @return the 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * 设置 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @param contactPhone
	 *            the new 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * 获取 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @return the 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @param address
	 *            the new 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/*
	 * public String getWaifabumen() { return waifabumen; }
	 * 
	 * public void setWaifabumen(String waifabumen) { this.waifabumen =
	 * waifabumen; }
	 */

	/**
	 * 获取 外发部门//制单人所在的部门.
	 * 
	 * @return the 外发部门//制单人所在的部门
	 */
	public String getRegisterUser() {
		return registerUser;
	}

	/**
	 * 设置 外发部门//制单人所在的部门.
	 * 
	 * @param registerUser
	 *            the new 外发部门//制单人所在的部门
	 */
	public void setRegisterUser(String registerUser) {
		this.registerUser = registerUser;
	}

	/**
	 * 获取 目的站//接送货运单TARGET_ORG_CODE.
	 * 
	 * @return the 目的站//接送货运单TARGET_ORG_CODE
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * 设置 目的站//接送货运单TARGET_ORG_CODE.
	 * 
	 * @param targetOrgCode
	 *            the new 目的站//接送货运单TARGET_ORG_CODE
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * 获取 收货日期//开单时间CREATE_TIME.
	 * 
	 * @return the 收货日期//开单时间CREATE_TIME
	 */
	public String getHandGoodsTime() {
		return handGoodsTime;
	}

	/**
	 * 设置 收货日期//开单时间CREATE_TIME.
	 * 
	 * @param handGoodsTime
	 *            the new 收货日期//开单时间CREATE_TIME
	 */
	public void setHandGoodsTime(String handGoodsTime) {
		this.handGoodsTime = handGoodsTime;
	}

	/**
	 * 获取 收货部门//开单组织CREATE_ORG_CODE.
	 * 
	 * @return the 收货部门//开单组织CREATE_ORG_CODE
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 收货部门//开单组织CREATE_ORG_CODE.
	 * 
	 * @param createOrgCode
	 *            the new 收货部门//开单组织CREATE_ORG_CODE
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 重量//货物总重量GOODS_WEIGHT_TOTAL.
	 * 
	 * @return the 重量//货物总重量GOODS_WEIGHT_TOTAL
	 */
	public String getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * 设置 重量//货物总重量GOODS_WEIGHT_TOTAL.
	 * 
	 * @param goodsWeightTotal
	 *            the new 重量//货物总重量GOODS_WEIGHT_TOTAL
	 */
	public void setGoodsWeightTotal(String goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * 获取 体积//货物总体积GOODS_VOLUME_TOTAL.
	 * 
	 * @return the 体积//货物总体积GOODS_VOLUME_TOTAL
	 */
	public String getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * 设置 体积//货物总体积GOODS_VOLUME_TOTAL.
	 * 
	 * @param goodsVolumeTotal
	 *            the new 体积//货物总体积GOODS_VOLUME_TOTAL
	 */
	public void setGoodsVolumeTotal(String goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * 获取 件数//货物总件数GOODS_QTY_TOTAL.
	 * 
	 * @return the 件数//货物总件数GOODS_QTY_TOTAL
	 */
	public String getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * 设置 件数//货物总件数GOODS_QTY_TOTAL.
	 * 
	 * @param goodsQtyTotal
	 *            the new 件数//货物总件数GOODS_QTY_TOTAL
	 */
	public void setGoodsQtyTotal(String goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * 获取 运费//公布价运费TRANSPORT_FEE.
	 * 
	 * @return the 运费//公布价运费TRANSPORT_FEE
	 */
	public String getTransportFee() {
		return transportFee;
	}

	/**
	 * 设置 运费//公布价运费TRANSPORT_FEE.
	 * 
	 * @param transportFee
	 *            the new 运费//公布价运费TRANSPORT_FEE
	 */
	public void setTransportFee(String transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * 获取 货物名称//货物名称GOODS_NAME.
	 * 
	 * @return the 货物名称//货物名称GOODS_NAME
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 设置 货物名称//货物名称GOODS_NAME.
	 * 
	 * @param goodsName
	 *            the new 货物名称//货物名称GOODS_NAME
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 获取 保险费//保价费INSURANCE__FEE.
	 * 
	 * @return the 保险费//保价费INSURANCE__FEE
	 */
	public String getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * 设置 保险费//保价费INSURANCE__FEE.
	 * 
	 * @param insuranceFee
	 *            the new 保险费//保价费INSURANCE__FEE
	 */
	public void setInsuranceFee(String insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * 获取 包装//货物包装GOODS_PACKAGE.
	 * 
	 * @return the 包装//货物包装GOODS_PACKAGE
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * 设置 包装//货物包装GOODS_PACKAGE.
	 * 
	 * @param goodsPackage
	 *            the new 包装//货物包装GOODS_PACKAGE
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * 获取 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME.
	 * 
	 * @return the 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * 设置 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME.
	 * 
	 * @param deliveryCustomerName
	 *            the new 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * 获取 保险价值//保价声明价值INSURANCE_AMOUNT.
	 * 
	 * @return the 保险价值//保价声明价值INSURANCE_AMOUNT
	 */
	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * 设置 保险价值//保价声明价值INSURANCE_AMOUNT.
	 * 
	 * @param insuranceAmount
	 *            the new 保险价值//保价声明价值INSURANCE_AMOUNT
	 */
	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * 获取 运输事项//对内备注,对外备注合体.
	 * 
	 * @return the 运输事项//对内备注,对外备注合体
	 */
	public String getYunshushixiang() {
		return yunshushixiang;
	}

	/**
	 * 设置 运输事项//对内备注,对外备注合体.
	 * 
	 * @param yunshushixiang
	 *            the new 运输事项//对内备注,对外备注合体
	 */
	public void setYunshushixiang(String yunshushixiang) {
		this.yunshushixiang = yunshushixiang;
	}

	/**
	 * 获取 开单金额//总费用TOTAL_FEE.
	 * 
	 * @return the 开单金额//总费用TOTAL_FEE
	 */
	public String getTotalFee() {
		return totalFee;
	}

	/**
	 * 设置 开单金额//总费用TOTAL_FEE.
	 * 
	 * @param totalFee
	 *            the new 开单金额//总费用TOTAL_FEE
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * 获取 代收货款//代收货款COD_AMOUNT.
	 * 
	 * @return the 代收货款//代收货款COD_AMOUNT
	 */
	public String getCodAmount() {
		return codAmount;
	}

	/**
	 * 设置 代收货款//代收货款COD_AMOUNT.
	 * 
	 * @param codAmount
	 *            the new 代收货款//代收货款COD_AMOUNT
	 */
	public void setCodAmount(String codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * 获取 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME.
	 * 
	 * @return the 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * 设置 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME.
	 * 
	 * @param receiveCustomerName
	 *            the new 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * 获取 代理电话//联系电话T_BAS_BUSINESS_PARTNER.
	 * 
	 * @return the 代理电话//联系电话T_BAS_BUSINESS_PARTNER
	 */
	public String getPartnerContactPhone() {
		return partnerContactPhone;
	}

	/**
	 * 设置 代理电话//联系电话T_BAS_BUSINESS_PARTNER.
	 * 
	 * @param partnerContactPhone
	 *            the new 代理电话//联系电话T_BAS_BUSINESS_PARTNER
	 */
	public void setPartnerContactPhone(String partnerContactPhone) {
		this.partnerContactPhone = partnerContactPhone;
	}

	/**
	 * 获取 录入日期从.
	 * 
	 * @return the 录入日期从
	 */
	public String getRegisterTimeFrom() {
		return registerTimeFrom;
	}

	/**
	 * 设置 录入日期从.
	 * 
	 * @param registerTimeFrom
	 *            the new 录入日期从
	 */
	public void setRegisterTimeFrom(String registerTimeFrom) {
		this.registerTimeFrom = registerTimeFrom;
	}

	/**
	 * 获取 录入日期到.
	 * 
	 * @return the 录入日期到
	 */
	public String getRegisterTimeTo() {
		return registerTimeTo;
	}

	/**
	 * 设置 录入日期到.
	 * 
	 * @param registerTimeTo
	 *            the new 录入日期到
	 */
	public void setRegisterTimeTo(String registerTimeTo) {
		this.registerTimeTo = registerTimeTo;
	}

	/**
	 * 获取 是否自提.
	 * 
	 * @return the 是否自提
	 */
	public String getBeAutoDelivery() {
		return beAutoDelivery;
	}

	/**
	 * 设置 是否自提.
	 * 
	 * @param beAutoDelivery
	 *            the new 是否自提
	 */
	public void setBeAutoDelivery(String beAutoDelivery) {
		this.beAutoDelivery = beAutoDelivery;
	}

	/**
	 * 获取 交接类型.
	 * 
	 * @return the 交接类型
	 */
	public String getHandoverType() {
		return handoverType;
	}

	/**
	 * 设置 交接类型.
	 * 
	 * @param handoverType
	 *            the new 交接类型
	 */
	public void setHandoverType(String handoverType) {
		this.handoverType = handoverType;
	}

	/**
	 * 获取 审核状态（数值型）.
	 * 
	 * @return the 审核状态（数值型）
	 */
	public int getAdtStatus() {
		return adtStatus;
	}

	/**
	 * 设置 审核状态（数值型）.
	 * 
	 * @param adtStatus
	 *            the new 审核状态（数值型）
	 */
	public void setAdtStatus(int adtStatus) {
		this.adtStatus = adtStatus;
	}

	/**
	 * 获取 运单状态.
	 * 
	 * @return the 运单状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 运单状态.
	 * 
	 * @param active
	 *            the new 运单状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 状态列表.
	 * 
	 * @return the 状态列表
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * 设置 状态列表.
	 * 
	 * @param list
	 *            the new 状态列表
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * 获取 交接单状态.
	 * 
	 * @return the 交接单状态
	 */
	public List<Integer> getBillStatuslist() {
		return billStatuslist;
	}

	/**
	 * 设置 交接单状态.
	 * 
	 * @param billStatuslist
	 *            the new 交接单状态
	 */
	public void setBillStatuslist(List<Integer> billStatuslist) {
		this.billStatuslist = billStatuslist;
	}

	public String getFilterOrgCode() {
		return filterOrgCode;
	}

	public void setFilterOrgCode(String filterOrgCode) {
		this.filterOrgCode = filterOrgCode;
	}

	@Override
	public String toString() {
		return "ExternalBillDto [beAutoDelivery=" + beAutoDelivery + ", waybillNo=" + waybillNo + ", paidMethod="
				+ paidMethod + ", toPayAmount=" + toPayAmount + ", origOrgName=" + origOrgName + ", agentDeptName="
				+ agentDeptName + ", contactPhone=" + contactPhone + ", address=" + address + ", registerUser="
				+ registerUser + ", targetOrgCode=" + targetOrgCode + ", handGoodsTime=" + handGoodsTime
				+ ", createOrgCode=" + createOrgCode + ", goodsWeightTotal=" + goodsWeightTotal + ", goodsVolumeTotal="
				+ goodsVolumeTotal + ", goodsQtyTotal=" + goodsQtyTotal + ", transportFee=" + transportFee
				+ ", goodsName=" + goodsName + ", insuranceFee=" + insuranceFee + ", goodsPackage=" + goodsPackage
				+ ", deliveryCustomerName=" + deliveryCustomerName + ", insuranceAmount=" + insuranceAmount
				+ ", yunshushixiang=" + yunshushixiang + ", totalFee=" + totalFee + ", codAmount=" + codAmount
				+ ", receiveCustomerName=" + receiveCustomerName + ", partnerContactPhone=" + partnerContactPhone
				+ ", registerTimeFrom=" + registerTimeFrom + ", registerTimeTo=" + registerTimeTo + ", handoverType="
				+ handoverType + ", adtStatus=" + adtStatus + ", active=" + active + ", list=" + list
				+ ", billStatuslist=" + billStatuslist + ", filterOrgCode=" + filterOrgCode + "]";
	}

	public String getAuditAll() {
		return auditAll;
	}

	public void setAuditAll(String auditAll) {
		this.auditAll = auditAll;
	}

	public List<String> getFilterOrgCodes() {
		return filterOrgCodes;
	}

	public void setFilterOrgCodes(List<String> filterOrgCodes) {
		this.filterOrgCodes = filterOrgCodes;
	}

	/**
	 * @return the isInit
	 */
	public String getIsInit() {
		return isInit;
	}

	/**
	 * @param isInit the isInit to set
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

}
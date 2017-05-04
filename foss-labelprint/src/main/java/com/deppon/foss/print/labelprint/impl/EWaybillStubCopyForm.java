package com.deppon.foss.print.labelprint.impl;

import java.io.Serializable;
import java.util.List;

/**
 * 电子运单打印基础数据Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2014-7-12 08:15:14
 */
public class EWaybillStubCopyForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 条码
	 */
	private List<String> barcode;
	
	/**
	 * Top
	 */
	private int top;
	
	/**
	 * Left
	 */
	private int left;
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 客户订单号
	 */
	private String custOrderLine;
	
	/**
	 * 订单创建时间
	 */
	private String orderTime;
	
	/**
	 * 订单备注
	 */
	private String orderNotes;
	
	/**
	 * 订单付款方式
	 */
	private String orderPaidMethod;

	//发货方信息
	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;

	/**
	 * 发货客户手机
	 */
	private String deliveryCustomerMobilephone;

	/**
	 * 发货客户电话
	 */
	private String deliveryCustomerPhone;

	/**
	 * 发货客户联系人
	 */
	private String deliveryCustomerContact;

	/**
	 * 发货具体地址
	 */
	private String deliveryCustomerAddress;
	
	/**
	 * 发货具体地址换行
	 */
	private String deliveryCustomerAddress1;
	private String deliveryCustomerAddress2;
	
	//收货方信息
	/**
	 * 收货客户名称
	 */
	private String receiveCustomerName;

	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;

	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;

	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;

	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	/**
	 * 收货具体地址换行
	 */
	private String receiveCustomerAddress1;
	private String receiveCustomerAddress2;

	/**
	 * 收货部门(出发部门)
	 */
	private String receiveOrgCode;
	
	/**
	 * 收货部门
	 */
	private String receiveOrgName;
	
	/**
	 * 收货部门(出发部门)
	 */
	private String createOrgCode;
	
	/**
	 * 收货部门(出发部门)
	 */
	private String createOrgName;
	
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;

	/**
	 * 提货网点
	 */
	private String customerPickupOrgName;

	//货物服务信息
	/**
	 * 货物名称
	 */
	private String goodsName;

	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;

	/**
	 * 货物总体积
	 */
	private String goodsVolumeTotal;

	/**
	 * 货物总重量
	 */
	private String goodsWeightTotal;

	/**
	 * 计费重量
	 */
	private String billWeight;
	
	/**
	 * 运输性质编码
	 */
	private String productCode;

	/**
	 * 运输性质名称
	 */
	private String productName;

	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 开单付款方式
	 */
	private String paidMethod;
	
	/**
	 * 总运费
	 */
	private String totalFee;
	
	/**
	 * 公布价
	 */
	private String transportFee;

	/**
	 * 货物类型编码
	 */
	private String goodsTypeCode;
	
	/**
	 * 货物类型名称
	 */
	private String goodsTypeName;

	/**
	 * 是否贵重物品
	 */
	private String preciousGoods;

	/**
	 * 货物包装
	 */
	private String goodsPackage;

	//增值服务信息
	/**
	 * 保价声明价值
	 */
	private String insuranceAmount;
	/**
	 * 保价费用
	 */
	private String insuranceFee;
	/**
	 * 包装费
	 */
	private String packageFee;

	/**
	 * 返单类别
	 */
	private String returnBillType;

	/**
	 * 代收货款
	 */
	private String codAmount;
	
	/**
	 * 代收账号
	 */
	private String accountNo;
	
	/**
	 * 快递员(收)
	 */
	private String deliverMan;
	
	/**
	 * 收货日期
	 */
	private String receiveDate;
	
	/**
	 * 快递员(送)
	 */
	private String receiverMan;

	/**
	 * 派送日期
	 */
	private String deliverDate;
	
	/**
	 * 第二城市外场编码
	 */
	private String secondOutfieldCode;
	
	/**
	 * 第二城市外场名称
	 */
	private String secondOutfieldName;
	
	/**
	 * 最终配载部门编码
	 */
	private String lastLoadOrgCode;
	
	/**
	 * 最终配载部门名称
	 */
	private String lastLoadOrgName;
	
	/**
	 * 出发城市
	 */
	private String leavecity;
	
	/**
	 * 外场名称1
	 */
	private String outerField1;
	
	/**
	 * 外场名称2
	 */
	private String outerField2;
	
	/**
	 * 外场名称3
	 */
	private String outerField3;
	
	/**
	 * 外场名称4
	 */
	private String outerField4;
	
	/**
	 * 外场名称5
	 */
	private String outerField5;
	
	/**
	 * 外场名称6
	 */
	private String outerField6;
	
	/**
	 * 外场编码1
	 */
	private String location1;
	
	/**
	 * 外场编码2
	 */
	private String location2;
	
	/**
	 * 外场编码3
	 */
	private String location3;
	
	/**
	 * 外场编码4
	 */
	private String location4;
	
	/**
	 * 外场编码5
	 */
	private String location5;
	
	/**
	 * 外场编码6
	 */
	private String location6;
	
	/**
	 * 是否打印星标
	 */
	private String isPrintStar;
	/**
	 * 打印流水号
	 */
	private String printSerialNos;
	
	/**
	 * 是否打印@
	 */
	private String isPrintAt;
	/**
	 * 是否隐藏发货人信息
	 */
	private String isHideDeliveryCustomerInfo;
	/**
	 * 是否隐藏计费重量
	 */
	private String isHideBillWeight;
	/**
	 * 是否隐藏运费
	 */
	private String isHideTransportFee;	
	/**
	 * 是否隐藏费用合计
	 */
	private String isHideTotalFee;	
	/**
	 * 目的站后四位
	 */
	private String stationnumber;
	/**
	 * 目的站
	 */
	private String destination;
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * 提货客户大客户
	 */
	private String receiveBigCustomer;
	
	/**
	 * 发货客户大客户
	 */
	private String deliveryBigCustomer;
	
	/**
	 * 是否打印送货
	 */
	private String isDeliver;
	
	/**
	 * 打印二维码(微信地址)
	 */
	private String weixinAddr;
	
	/**
	 *打印操作人 
	 */
	private String optusernum;
	
	/**
	 * 打印所需的流水号的集合
	 */
	private List<String> serialNoList;
	public String getIsHideDeliveryCustomerInfo() {
		return isHideDeliveryCustomerInfo;
	}

	public void setIsHideDeliveryCustomerInfo(String isHideDeliveryCustomerInfo) {
		this.isHideDeliveryCustomerInfo = isHideDeliveryCustomerInfo;
	}

	public String getIsHideBillWeight() {
		return isHideBillWeight;
	}

	public void setIsHideBillWeight(String isHideBillWeight) {
		this.isHideBillWeight = isHideBillWeight;
	}

	public String getIsHideTransportFee() {
		return isHideTransportFee;
	}

	public void setIsHideTransportFee(String isHideTransportFee) {
		this.isHideTransportFee = isHideTransportFee;
	}

	public String getIsHideTotalFee() {
		return isHideTotalFee;
	}

	public void setIsHideTotalFee(String isHideTotalFee) {
		this.isHideTotalFee = isHideTotalFee;
	}
	
	public String getIsDeliver() {
		return isDeliver;
	}
	
	public void setIsDeliver(String isDeliver) {
		this.isDeliver = isDeliver;
	}
	
	public String getOptusernum() {
		return optusernum;
	}
	public void setOptusernum(String optusernum) {
		this.optusernum = optusernum;
	}
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderPaidMethod() {
		return orderPaidMethod;
	}

	public void setOrderPaidMethod(String orderPaidMethod) {
		this.orderPaidMethod = orderPaidMethod;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
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

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(String goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(String goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public String getBillWeight() {
		return billWeight;
	}

	public void setBillWeight(String billWeight) {
		this.billWeight = billWeight;
	}

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

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(String transportFee) {
		this.transportFee = transportFee;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public String getPreciousGoods() {
		return preciousGoods;
	}

	public void setPreciousGoods(String preciousGoods) {
		this.preciousGoods = preciousGoods;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(String codAmount) {
		this.codAmount = codAmount;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getDeliverMan() {
		return deliverMan;
	}

	public void setDeliverMan(String deliverMan) {
		this.deliverMan = deliverMan;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getReceiverMan() {
		return receiverMan;
	}

	public void setReceiverMan(String receiverMan) {
		this.receiverMan = receiverMan;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getSecondOutfieldCode() {
		return secondOutfieldCode;
	}

	public void setSecondOutfieldCode(String secondOutfieldCode) {
		this.secondOutfieldCode = secondOutfieldCode;
	}

	public String getSecondOutfieldName() {
		return secondOutfieldName;
	}

	public void setSecondOutfieldName(String secondOutfieldName) {
		this.secondOutfieldName = secondOutfieldName;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}

	public String getLeavecity() {
		return leavecity;
	}

	public void setLeavecity(String leavecity) {
		this.leavecity = leavecity;
	}

	public String getOuterField1() {
		return outerField1;
	}

	public void setOuterField1(String outerField1) {
		this.outerField1 = outerField1;
	}

	public String getOuterField2() {
		return outerField2;
	}

	public void setOuterField2(String outerField2) {
		this.outerField2 = outerField2;
	}

	public String getOuterField3() {
		return outerField3;
	}

	public void setOuterField3(String outerField3) {
		this.outerField3 = outerField3;
	}

	public String getOuterField4() {
		return outerField4;
	}

	public void setOuterField4(String outerField4) {
		this.outerField4 = outerField4;
	}

	public String getOuterField5() {
		return outerField5;
	}

	public void setOuterField5(String outerField5) {
		this.outerField5 = outerField5;
	}

	public String getOuterField6() {
		return outerField6;
	}

	public void setOuterField6(String outerField6) {
		this.outerField6 = outerField6;
	}

	public String getLocation1() {
		return location1;
	}

	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	public String getLocation3() {
		return location3;
	}

	public void setLocation3(String location3) {
		this.location3 = location3;
	}

	public String getLocation4() {
		return location4;
	}

	public void setLocation4(String location4) {
		this.location4 = location4;
	}

	public String getLocation5() {
		return location5;
	}

	public void setLocation5(String location5) {
		this.location5 = location5;
	}

	public String getLocation6() {
		return location6;
	}

	public void setLocation6(String location6) {
		this.location6 = location6;
	}

	public String getIsPrintStar() {
		return isPrintStar;
	}

	public void setIsPrintStar(String isPrintStar) {
		this.isPrintStar = isPrintStar;
	}

	public String getPrintSerialNos() {
		return printSerialNos;
	}

	public void setPrintSerialNos(String printSerialNos) {
		this.printSerialNos = printSerialNos;
	}

	public String getIsPrintAt() {
		return isPrintAt;
	}

	public void setIsPrintAt(String isPrintAt) {
		this.isPrintAt = isPrintAt;
	}

	public String getStationnumber() {
		return stationnumber;
	}

	public void setStationnumber(String stationnumber) {
		this.stationnumber = stationnumber;
	}

	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}

	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}

	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}

	public List<String> getBarcode() {
		return barcode;
	}

	public void setBarcode(List<String> barcode) {
		this.barcode = barcode;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}
	
	public String getWeixinAddr() {
		return weixinAddr;
	}
	
	public void setWeixinAddr(String weixinAddr) {
		this.weixinAddr = weixinAddr;
	}
	
	public List<String> getSerialNoList() {
		return serialNoList;
	}
	
	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}
	public String getDeliveryCustomerAddress1() {
		return deliveryCustomerAddress1;
	}

	public void setDeliveryCustomerAddress1(String deliveryCustomerAddress1) {
		this.deliveryCustomerAddress1 = deliveryCustomerAddress1;
	}

	public String getDeliveryCustomerAddress2() {
		return deliveryCustomerAddress2;
	}

	public void setDeliveryCustomerAddress2(String deliveryCustomerAddress2) {
		this.deliveryCustomerAddress2 = deliveryCustomerAddress2;
	}

	public String getReceiveCustomerAddress1() {
		return receiveCustomerAddress1;
	}

	public void setReceiveCustomerAddress1(String receiveCustomerAddress1) {
		this.receiveCustomerAddress1 = receiveCustomerAddress1;
	}

	public String getReceiveCustomerAddress2() {
		return receiveCustomerAddress2;
	}

	public void setReceiveCustomerAddress2(String receiveCustomerAddress2) {
		this.receiveCustomerAddress2 = receiveCustomerAddress2;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderNotes() {
		return orderNotes;
	}

	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}

	public String getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(String insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public String getCustOrderLine() {
		return custOrderLine;
	}

	public void setCustOrderLine(String custOrderLine) {
		this.custOrderLine = custOrderLine;
	}
	
	
}


package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 零担差错-重大货物异常自动上报QMS
 * @author 306548-foss-honglujun
 *
 */
public class RecordImportantErrorQmsWaybillDto implements Serializable {

	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 零担差错-短少量
	 */
    private String shortNum;
	
	/**
	 * 零担差错-短少流水号
	 */
	private String goodsLoseFlowcode;
	
	/**
	 * 零担差错-外包装是否完好
	 */
	
	private String packageIsGood;
	
	/**
	 * 零担差错-运输类型
	 */
	private String transType;
	
	/**
	 * 零担差错-返单类型
	 */
	private String returnBillType;
	
	/**
	 * 零担差错-托运人(发货客户联系人)
	 */
	private String shipper;
	
	/**
	 * 零担差错-运输性质
	 */
	private String transNature;

	/**
	 * 收货人电话
	 */
	private String receiverPhone;
	
	/**
	 * 零担差错-提货方式
	 */
	private String pickUpType;
	
	/**
	 * 零担差错-储运事项
	 */
	private String storageTransport;
	
	/**
	 * 零担差错-重量
	 */
	private BigDecimal sumWeight;
	
	/**
	 * 零担差错-体积
	 */
	private BigDecimal sumVolume;
	
	/**
	 * 零担差错-件数
	 */
	private Integer sumNumber;
	
	/**
	 * 错误类型
	 */
	private String errCategoty;
	
	/**
	 * 货物名称
	 */
	private String goodsName;
	
	/**
	 * 零担差错-发货时间(开单时间)
	 */
	private String sendGoodsTime;
	
	/**
	 * 签收时间
	 */
	private Date signTime;
	
	/**
	 * 异常签收时间(与上面签收时间本质上是一样，但qms需要字符串类型，所以避免重复新增字段)
	 */
	private String unnormalSignTime;
	
	/**
	 * 到达部门
	 */
	private String arriveDeptCode;
	
	/**
	 * 到达部门名字
	 */
	private String arriveDeptName;
	
	/**
	 * 零担差错-收货人(收货客户联系人)
	 */
	private String receiverName;
	
	/**
	 * 零担差错-(开单)付款方式
	 */
	private String payType;
	
	/**
	 * 零担差错-保险金额(保价声明价值)
	 */
	private BigDecimal safeMoney;
	
	/**
	 * 货物包装
	 */
	private String goodsPackage;
	
	/**
	 * 零担差错-运费总额
	 */
	private BigDecimal freightSumFee;
	
	/**
	 * 开单部门
	 */
	private String billingDeptCode;
	
	/**
	 * 开单部门名字
	 */
	private String billingDeptName;
	
	/**
	 * 零担差错-经手部门
	 */
	private String goodsHandleDeptCode;
	
	/**
	 * 零担差错-经手部门名称
	 */
	private String goodsHandleDeptName;
	
	/**
	 * 是否集中接货
	 */
	private String isConcentReceiving;
	
	/**
	 * 责任部门
	 */
	private String respDeptCode;
	
	/**
	 * 责任部门名称
	 */
	private String respDeptName;
	
	/**
	 * 责任部门负责人
	 */
	private String respDeptResperCode;
	
	/**
	 * 责任部门负责人名称
	 */
	private String respDeptResperName; 
	
	/**
	 * 责任人
	 */
	private String respEmpCode;
	
	/**
	 * 责任人姓名
	 */
	private String respEmpName;
	
	/**
	 * 零担差错-车牌号
	 */
	private String carCode;
	
	/**
	 * 零担差错-交接单号
	 */
	private String transferBill;
	
	/**
	 * 事件经过
	 */
	private String incident;
	
	/**
	 * 上报人工号(自动上报时为FOSS)
	 */
	private String repEmpcode;
	
	/**
	 * 上报人姓名(自动上报时为null)
	 */
	private String repEmpName;
	
	/**
	 * 上报人职位(自动上报时为null)
	 */
	private String repEmpJob;
	/**
	 * 错误类型编号
	 */
	private String errTypeId;
	
	/**
	 * 文件标准id
	 */
	private String docStandarId;
	
	/**
	 * 运单号
	 */
	private String wayBillNum;
	
	/**
	 * 收货部门标杆编码
	 */
	private String receiveDeptCode;
	
	/**
	 * 收货部门名称
	 */
	private String receiveDeptName;
	
	/**
	 * 上报人所属部门
	 */
	private String repDeptCode;
	
	/**
	 * 上报人所属部门名称
	 */
	private String repDeptName;

	public String getShortNum() {
		return shortNum;
	}

	public void setShortNum(String shortNum) {
		this.shortNum = shortNum;
	}

	public String getGoodsLoseFlowcode() {
		return goodsLoseFlowcode;
	}

	public void setGoodsLoseFlowcode(String goodsLoseFlowcode) {
		this.goodsLoseFlowcode = goodsLoseFlowcode;
	}

	public String getPackageIsGood() {
		return packageIsGood;
	}

	public void setPackageIsGood(String packageIsGood) {
		this.packageIsGood = packageIsGood;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getTransNature() {
		return transNature;
	}

	public void setTransNature(String transNature) {
		this.transNature = transNature;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getPickUpType() {
		return pickUpType;
	}

	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}

	public String getStorageTransport() {
		return storageTransport;
	}

	public void setStorageTransport(String storageTransport) {
		this.storageTransport = storageTransport;
	}

	public BigDecimal getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(BigDecimal sumWeight) {
		this.sumWeight = sumWeight;
	}

	public BigDecimal getSumVolume() {
		return sumVolume;
	}

	public void setSumVolume(BigDecimal sumVolume) {
		this.sumVolume = sumVolume;
	}

	public Integer getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSendGoodsTime() {
		return sendGoodsTime;
	}

	public void setSendGoodsTime(String sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public String getArriveDeptName() {
		return arriveDeptName;
	}

	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getSafeMoney() {
		return safeMoney;
	}

	public void setSafeMoney(BigDecimal safeMoney) {
		this.safeMoney = safeMoney;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public BigDecimal getFreightSumFee() {
		return freightSumFee;
	}

	public void setFreightSumFee(BigDecimal freightSumFee) {
		this.freightSumFee = freightSumFee;
	}

	public String getBillingDeptCode() {
		return billingDeptCode;
	}

	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}

	public String getBillingDeptName() {
		return billingDeptName;
	}

	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
	}

	public String getGoodsHandleDeptCode() {
		return goodsHandleDeptCode;
	}

	public void setGoodsHandleDeptCode(String goodsHandleDeptCode) {
		this.goodsHandleDeptCode = goodsHandleDeptCode;
	}

	public String getGoodsHandleDeptName() {
		return goodsHandleDeptName;
	}

	public void setGoodsHandleDeptName(String goodsHandleDeptName) {
		this.goodsHandleDeptName = goodsHandleDeptName;
	}

	public String getIsConcentReceiving() {
		return isConcentReceiving;
	}

	public void setIsConcentReceiving(String isConcentReceiving) {
		this.isConcentReceiving = isConcentReceiving;
	}

	public String getRespDeptCode() {
		return respDeptCode;
	}

	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}

	public String getRespDeptName() {
		return respDeptName;
	}

	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}

	public String getRespDeptResperCode() {
		return respDeptResperCode;
	}

	public void setRespDeptResperCode(String respDeptResperCode) {
		this.respDeptResperCode = respDeptResperCode;
	}

	public String getRespDeptResperName() {
		return respDeptResperName;
	}

	public void setRespDeptResperName(String respDeptResperName) {
		this.respDeptResperName = respDeptResperName;
	}

	public String getRespEmpCode() {
		return respEmpCode;
	}

	public void setRespEmpCode(String respEmpCode) {
		this.respEmpCode = respEmpCode;
	}

	public String getRespEmpName() {
		return respEmpName;
	}

	public void setRespEmpName(String respEmpName) {
		this.respEmpName = respEmpName;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getTransferBill() {
		return transferBill;
	}

	public void setTransferBill(String transferBill) {
		this.transferBill = transferBill;
	}

	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getUnnormalSignTime() {
		return unnormalSignTime;
	}

	public void setUnnormalSignTime(String unnormalSignTime) {
		this.unnormalSignTime = unnormalSignTime;
	}

	public String getRepEmpcode() {
		return repEmpcode;
	}

	public void setRepEmpcode(String repEmpcode) {
		this.repEmpcode = repEmpcode;
	}

	public String getRepEmpName() {
		return repEmpName;
	}

	public void setRepEmpName(String repEmpName) {
		this.repEmpName = repEmpName;
	}

	public String getRepEmpJob() {
		return repEmpJob;
	}

	public void setRepEmpJob(String repEmpJob) {
		this.repEmpJob = repEmpJob;
	}

	public String getDocStandarId() {
		return docStandarId;
	}

	public void setDocStandarId(String docStandarId) {
		this.docStandarId = docStandarId;
	}

	public String getWayBillNum() {
		return wayBillNum;
	}

	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}

	public String getReceiveDeptCode() {
		return receiveDeptCode;
	}

	public void setReceiveDeptCode(String receiveDeptCode) {
		this.receiveDeptCode = receiveDeptCode;
	}

	public String getReceiveDeptName() {
		return receiveDeptName;
	}

	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}

	public String getRepDeptCode() {
		return repDeptCode;
	}

	public void setRepDeptCode(String repDeptCode) {
		this.repDeptCode = repDeptCode;
	}

	public String getRepDeptName() {
		return repDeptName;
	}

	public void setRepDeptName(String repDeptName) {
		this.repDeptName = repDeptName;
	}

	public String getErrCategoty() {
		return errCategoty;
	}

	public void setErrCategoty(String errCategoty) {
		this.errCategoty = errCategoty;
	}

	public String getErrTypeId() {
		return errTypeId;
	}

	public void setErrTypeId(String errTypeId) {
		this.errTypeId = errTypeId;
	}
}

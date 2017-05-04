package com.deppon.foss.module.transfer.common.api.shared.domain;


public class QmsLDErrSubHasWaybillEntity extends QmsErrorSubEntity {

	private static final long serialVersionUID = 1L;

	//表id
	private Long subHasWaybillId;

	//是否补录运单 
    private String isMakeupWaybill;

    //责任人直接上级编码
    private String respSupervisorCode;

    //责任人直接上级姓名
    private String respSupervisorName;

    //责任经理编码
    private String respManagerCode;

    //责任经理姓名
    private String respManagerName;

    //责任经理直接上级编码
    private String respManagerSuperCode;

    //责任经理直接上级姓名
    private String respManagerSuperName;

    //开单部门标杆编码
    private String billingDeptCode;

    //开单部门名称
    private String billingDeptName;

    //发货客户编码
    private String sendClientCode;

    //收货客户编码
    private String takeOverClientCode;

    //是否集中接货
    private String isConcentReceiving;

    //运输类型
    private String transType;

    //托运人
    private String shipper;

    //运输性质
    private String transNature;

    //收货人
    private String receiverName;

    //实际重量
    private String actualWeight;

    //储运事项
    private String storageTransport;

    //收货人电话
    private String receiverPhone;

    //收货人地址
    private String receiverAddress;

    //提货方式
    private String pickUpType;

    //重量
    private String weight;

    //总重量
    private String sumWeight;

    //体积
    private String volume;

    //总体积
    private String sumVolume;

    //实际体积
    private String actualVolume;

    //件数
    private String num;

    //总件数
    private String sumNumber;

    //货物名称
    private String goodsName;

    //发货时间
    private String sendGoodsTime;

    //开单时间
    private String billingTime;

    //到达部门标杆编码
    private String arriveDeptCode;

    //到达部门名称
    private String arriveDeptName;

    //收货部门标杆编码
    private String takeOverDeptCode;

    //收货部门名称
    private String takeOverDeptName;

    //付款方式
    private String payType;

    //保险金额
    private String safeMoney;

    //保险价值
    private String safeValue;

    //货物包装
    private String goodsPackage;

    //装车部门标杆编码
    private String loadingDeptCode;

    //装车部门名称
    private String loadingDeptName;

    //卸车部门标杆编码
    private String unloadingDeptCode;

    //卸车部门名称
    private String unloadingDeptName;

    //交接单
    private String transferBill;

    //丢货类别
    private String loseGoodsType;

    //少货件数
    private String loseNum;

    //是否整票
    private String isWholeTicket;

    //包装费
    private String packageFee;

    //少货流水号
    private String loseFlowcode;

    //纯运费
    private String pureFee;

    //送货费
    private String deliveryFee;

    //运费总额
    private String freightSumFee;

    //填开人工号
    private String fillopenEmpCode;

    //填开人姓名
    private String fillopenEmpName;

    //填开人所在部门标杆编码
    private String fillBillDeptCode;

    //填开人所在部门名称
    private String fillBillDeptName;

    //司机工号
    private String driverCode;

    //司机姓名
    private String driverName;

    //司机所在部门标杆编码
    private String driverDeptCode;

    //司机所在部门名称
    private String driverDeptName;

    //车牌号
    private String carCode;

    //返单类型
    private String returnBillType;

    //外包装是否完好
    private String packageIsGood;

    //内物短少流水号
    private String goodsLoseFlowcode;

    //短少量
    private String shortNum;

    //卸车任务编号
    private String unloadBusiCode;

    //多货件数
    private String moreGoodsNum;

    //有货无交接
    private String goodsNoTransfer;

    //上一环节部门标杆编码
    private String lastLinkDeptCode;

    //上一环节部门名称
    private String lastLinkDeptName;

    //多货类别
    private String moreGoodsType;

    //多货流水号
    private String moreGoodsFlowcode;

    //损失金额
    private String loseMoney;

    //违规类型
    private String illegalType;

    //破损理赔金额
    private String damageClaimAmount;

    //短少类别
    private String loseType;

    //短少品名
    private String loseName;

    //接送货责任司机标杆编码
    private String receverDriverCode;

    //接送货责任司机姓名
    private String receverDriverName;

    //货物实际经手部门标杆编码
    private String goodsHandleDeptCode;

    //货物实际经手部门名称
    private String goodsHandleDeptName;

    //接送货司机所属部门标杆编码
    private String driverTakeDeptCode;

    //接送货司机所属部门名称
    private String driverTakeDeptName;

    //预付金额
    private String prepayMoney;

    //到付金额
    private String topatMoney;

    //找到件数
    private String findNum;

    //找到类型
    private String findType;

    //未找到件数
    private String unfindNum;

    //找到流水号
    private String findFlowcode;

    //首次分批装车部门标杆编码
    private String fristLoadDeptCode;

    //首次分批装车部门名称
    private String fristLoadDeptName;

    //卸车任务创建人标杆编码
    private String unloadCreatePersonCode;

    //卸车任务创建人名称
    private String unloadCreatePersonName;

    //装车人标杆编码
    private String loadPersonCode;

    //装车人名称
    private String loadPersonName;

    //分批配载流水号
    private String batchFlowcode;

    //卸车人工号
    private String unloadPersonCode;

    //卸车人名称
    private String unloadPersonName;

    //派送距离
    private String deliveryDistance;

    //实收超远派送费
    private String paidInDeliveryFee;

    //应收超远派送费
    private String receivableDeliveryFee;


	/**
	 * subHasWaybillId <p>getter method</p>
	 * @author 150976
	 * @return  the subHasWaybillId
	 */
	public Long getSubHasWaybillId() {
		return subHasWaybillId;
	}


	/**
	 * subHasWaybillId <p>setter method</p>
	 * @author 150976
	 * @param subHasWaybillId the subHasWaybillId to set
	 */
	public void setSubHasWaybillId(Long subHasWaybillId) {
		this.subHasWaybillId = subHasWaybillId;
	}


	/**
	 * isMakeupWaybill <p>getter method</p>
	 * @author 150976
	 * @return  the isMakeupWaybill
	 */
	public String getIsMakeupWaybill() {
		return isMakeupWaybill;
	}


	/**
	 * isMakeupWaybill <p>setter method</p>
	 * @author 150976
	 * @param isMakeupWaybill the isMakeupWaybill to set
	 */
	public void setIsMakeupWaybill(String isMakeupWaybill) {
		this.isMakeupWaybill = isMakeupWaybill;
	}


	/**
	 * respSupervisorCode <p>getter method</p>
	 * @author 150976
	 * @return  the respSupervisorCode
	 */
	public String getRespSupervisorCode() {
		return respSupervisorCode;
	}


	/**
	 * respSupervisorCode <p>setter method</p>
	 * @author 150976
	 * @param respSupervisorCode the respSupervisorCode to set
	 */
	public void setRespSupervisorCode(String respSupervisorCode) {
		this.respSupervisorCode = respSupervisorCode;
	}


	/**
	 * respSupervisorName <p>getter method</p>
	 * @author 150976
	 * @return  the respSupervisorName
	 */
	public String getRespSupervisorName() {
		return respSupervisorName;
	}


	/**
	 * respSupervisorName <p>setter method</p>
	 * @author 150976
	 * @param respSupervisorName the respSupervisorName to set
	 */
	public void setRespSupervisorName(String respSupervisorName) {
		this.respSupervisorName = respSupervisorName;
	}


	/**
	 * respManagerCode <p>getter method</p>
	 * @author 150976
	 * @return  the respManagerCode
	 */
	public String getRespManagerCode() {
		return respManagerCode;
	}


	/**
	 * respManagerCode <p>setter method</p>
	 * @author 150976
	 * @param respManagerCode the respManagerCode to set
	 */
	public void setRespManagerCode(String respManagerCode) {
		this.respManagerCode = respManagerCode;
	}


	/**
	 * respManagerName <p>getter method</p>
	 * @author 150976
	 * @return  the respManagerName
	 */
	public String getRespManagerName() {
		return respManagerName;
	}


	/**
	 * respManagerName <p>setter method</p>
	 * @author 150976
	 * @param respManagerName the respManagerName to set
	 */
	public void setRespManagerName(String respManagerName) {
		this.respManagerName = respManagerName;
	}


	/**
	 * respManagerSuperCode <p>getter method</p>
	 * @author 150976
	 * @return  the respManagerSuperCode
	 */
	public String getRespManagerSuperCode() {
		return respManagerSuperCode;
	}


	/**
	 * respManagerSuperCode <p>setter method</p>
	 * @author 150976
	 * @param respManagerSuperCode the respManagerSuperCode to set
	 */
	public void setRespManagerSuperCode(String respManagerSuperCode) {
		this.respManagerSuperCode = respManagerSuperCode;
	}


	/**
	 * respManagerSuperName <p>getter method</p>
	 * @author 150976
	 * @return  the respManagerSuperName
	 */
	public String getRespManagerSuperName() {
		return respManagerSuperName;
	}


	/**
	 * respManagerSuperName <p>setter method</p>
	 * @author 150976
	 * @param respManagerSuperName the respManagerSuperName to set
	 */
	public void setRespManagerSuperName(String respManagerSuperName) {
		this.respManagerSuperName = respManagerSuperName;
	}


	/**
	 * billingDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the billingDeptCode
	 */
	public String getBillingDeptCode() {
		return billingDeptCode;
	}


	/**
	 * billingDeptCode <p>setter method</p>
	 * @author 150976
	 * @param billingDeptCode the billingDeptCode to set
	 */
	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}


	/**
	 * billingDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the billingDeptName
	 */
	public String getBillingDeptName() {
		return billingDeptName;
	}


	/**
	 * billingDeptName <p>setter method</p>
	 * @author 150976
	 * @param billingDeptName the billingDeptName to set
	 */
	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
	}


	/**
	 * sendClientCode <p>getter method</p>
	 * @author 150976
	 * @return  the sendClientCode
	 */
	public String getSendClientCode() {
		return sendClientCode;
	}


	/**
	 * sendClientCode <p>setter method</p>
	 * @author 150976
	 * @param sendClientCode the sendClientCode to set
	 */
	public void setSendClientCode(String sendClientCode) {
		this.sendClientCode = sendClientCode;
	}


	/**
	 * takeOverClientCode <p>getter method</p>
	 * @author 150976
	 * @return  the takeOverClientCode
	 */
	public String getTakeOverClientCode() {
		return takeOverClientCode;
	}


	/**
	 * takeOverClientCode <p>setter method</p>
	 * @author 150976
	 * @param takeOverClientCode the takeOverClientCode to set
	 */
	public void setTakeOverClientCode(String takeOverClientCode) {
		this.takeOverClientCode = takeOverClientCode;
	}


	/**
	 * isConcentReceiving <p>getter method</p>
	 * @author 150976
	 * @return  the isConcentReceiving
	 */
	public String getIsConcentReceiving() {
		return isConcentReceiving;
	}


	/**
	 * isConcentReceiving <p>setter method</p>
	 * @author 150976
	 * @param isConcentReceiving the isConcentReceiving to set
	 */
	public void setIsConcentReceiving(String isConcentReceiving) {
		this.isConcentReceiving = isConcentReceiving;
	}


	/**
	 * transType <p>getter method</p>
	 * @author 150976
	 * @return  the transType
	 */
	public String getTransType() {
		return transType;
	}


	/**
	 * transType <p>setter method</p>
	 * @author 150976
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}


	/**
	 * shipper <p>getter method</p>
	 * @author 150976
	 * @return  the shipper
	 */
	public String getShipper() {
		return shipper;
	}


	/**
	 * shipper <p>setter method</p>
	 * @author 150976
	 * @param shipper the shipper to set
	 */
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}


	/**
	 * transNature <p>getter method</p>
	 * @author 150976
	 * @return  the transNature
	 */
	public String getTransNature() {
		return transNature;
	}


	/**
	 * transNature <p>setter method</p>
	 * @author 150976
	 * @param transNature the transNature to set
	 */
	public void setTransNature(String transNature) {
		this.transNature = transNature;
	}


	/**
	 * receiverName <p>getter method</p>
	 * @author 150976
	 * @return  the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}


	/**
	 * receiverName <p>setter method</p>
	 * @author 150976
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}


	/**
	 * actualWeight <p>getter method</p>
	 * @author 150976
	 * @return  the actualWeight
	 */
	public String getActualWeight() {
		return actualWeight;
	}


	/**
	 * actualWeight <p>setter method</p>
	 * @author 150976
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(String actualWeight) {
		this.actualWeight = actualWeight;
	}


	/**
	 * storageTransport <p>getter method</p>
	 * @author 150976
	 * @return  the storageTransport
	 */
	public String getStorageTransport() {
		return storageTransport;
	}


	/**
	 * storageTransport <p>setter method</p>
	 * @author 150976
	 * @param storageTransport the storageTransport to set
	 */
	public void setStorageTransport(String storageTransport) {
		this.storageTransport = storageTransport;
	}


	/**
	 * receiverPhone <p>getter method</p>
	 * @author 150976
	 * @return  the receiverPhone
	 */
	public String getReceiverPhone() {
		return receiverPhone;
	}


	/**
	 * receiverPhone <p>setter method</p>
	 * @author 150976
	 * @param receiverPhone the receiverPhone to set
	 */
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}


	/**
	 * receiverAddress <p>getter method</p>
	 * @author 150976
	 * @return  the receiverAddress
	 */
	public String getReceiverAddress() {
		return receiverAddress;
	}


	/**
	 * receiverAddress <p>setter method</p>
	 * @author 150976
	 * @param receiverAddress the receiverAddress to set
	 */
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}


	/**
	 * pickUpType <p>getter method</p>
	 * @author 150976
	 * @return  the pickUpType
	 */
	public String getPickUpType() {
		return pickUpType;
	}


	/**
	 * pickUpType <p>setter method</p>
	 * @author 150976
	 * @param pickUpType the pickUpType to set
	 */
	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}


	/**
	 * weight <p>getter method</p>
	 * @author 150976
	 * @return  the weight
	 */
	public String getWeight() {
		return weight;
	}


	/**
	 * weight <p>setter method</p>
	 * @author 150976
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}


	/**
	 * sumWeight <p>getter method</p>
	 * @author 150976
	 * @return  the sumWeight
	 */
	public String getSumWeight() {
		return sumWeight;
	}


	/**
	 * sumWeight <p>setter method</p>
	 * @author 150976
	 * @param sumWeight the sumWeight to set
	 */
	public void setSumWeight(String sumWeight) {
		this.sumWeight = sumWeight;
	}


	/**
	 * volume <p>getter method</p>
	 * @author 150976
	 * @return  the volume
	 */
	public String getVolume() {
		return volume;
	}


	/**
	 * volume <p>setter method</p>
	 * @author 150976
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}


	/**
	 * sumVolume <p>getter method</p>
	 * @author 150976
	 * @return  the sumVolume
	 */
	public String getSumVolume() {
		return sumVolume;
	}


	/**
	 * sumVolume <p>setter method</p>
	 * @author 150976
	 * @param sumVolume the sumVolume to set
	 */
	public void setSumVolume(String sumVolume) {
		this.sumVolume = sumVolume;
	}


	/**
	 * actualVolume <p>getter method</p>
	 * @author 150976
	 * @return  the actualVolume
	 */
	public String getActualVolume() {
		return actualVolume;
	}


	/**
	 * actualVolume <p>setter method</p>
	 * @author 150976
	 * @param actualVolume the actualVolume to set
	 */
	public void setActualVolume(String actualVolume) {
		this.actualVolume = actualVolume;
	}


	/**
	 * num <p>getter method</p>
	 * @author 150976
	 * @return  the num
	 */
	public String getNum() {
		return num;
	}


	/**
	 * num <p>setter method</p>
	 * @author 150976
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}


	/**
	 * sumNumber <p>getter method</p>
	 * @author 150976
	 * @return  the sumNumber
	 */
	public String getSumNumber() {
		return sumNumber;
	}


	/**
	 * sumNumber <p>setter method</p>
	 * @author 150976
	 * @param sumNumber the sumNumber to set
	 */
	public void setSumNumber(String sumNumber) {
		this.sumNumber = sumNumber;
	}


	/**
	 * goodsName <p>getter method</p>
	 * @author 150976
	 * @return  the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}


	/**
	 * goodsName <p>setter method</p>
	 * @author 150976
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	/**
	 * sendGoodsTime <p>getter method</p>
	 * @author 150976
	 * @return  the sendGoodsTime
	 */
	public String getSendGoodsTime() {
		return sendGoodsTime;
	}


	/**
	 * sendGoodsTime <p>setter method</p>
	 * @author 150976
	 * @param sendGoodsTime the sendGoodsTime to set
	 */
	public void setSendGoodsTime(String sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}


	/**
	 * billingTime <p>getter method</p>
	 * @author 150976
	 * @return  the billingTime
	 */
	public String getBillingTime() {
		return billingTime;
	}


	/**
	 * billingTime <p>setter method</p>
	 * @author 150976
	 * @param billingTime the billingTime to set
	 */
	public void setBillingTime(String billingTime) {
		this.billingTime = billingTime;
	}


	/**
	 * arriveDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the arriveDeptCode
	 */
	public String getArriveDeptCode() {
		return arriveDeptCode;
	}


	/**
	 * arriveDeptCode <p>setter method</p>
	 * @author 150976
	 * @param arriveDeptCode the arriveDeptCode to set
	 */
	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}


	/**
	 * arriveDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the arriveDeptName
	 */
	public String getArriveDeptName() {
		return arriveDeptName;
	}


	/**
	 * arriveDeptName <p>setter method</p>
	 * @author 150976
	 * @param arriveDeptName the arriveDeptName to set
	 */
	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}


	/**
	 * takeOverDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the takeOverDeptCode
	 */
	public String getTakeOverDeptCode() {
		return takeOverDeptCode;
	}


	/**
	 * takeOverDeptCode <p>setter method</p>
	 * @author 150976
	 * @param takeOverDeptCode the takeOverDeptCode to set
	 */
	public void setTakeOverDeptCode(String takeOverDeptCode) {
		this.takeOverDeptCode = takeOverDeptCode;
	}


	/**
	 * takeOverDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the takeOverDeptName
	 */
	public String getTakeOverDeptName() {
		return takeOverDeptName;
	}


	/**
	 * takeOverDeptName <p>setter method</p>
	 * @author 150976
	 * @param takeOverDeptName the takeOverDeptName to set
	 */
	public void setTakeOverDeptName(String takeOverDeptName) {
		this.takeOverDeptName = takeOverDeptName;
	}


	/**
	 * payType <p>getter method</p>
	 * @author 150976
	 * @return  the payType
	 */
	public String getPayType() {
		return payType;
	}


	/**
	 * payType <p>setter method</p>
	 * @author 150976
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}


	/**
	 * safeMoney <p>getter method</p>
	 * @author 150976
	 * @return  the safeMoney
	 */
	public String getSafeMoney() {
		return safeMoney;
	}


	/**
	 * safeMoney <p>setter method</p>
	 * @author 150976
	 * @param safeMoney the safeMoney to set
	 */
	public void setSafeMoney(String safeMoney) {
		this.safeMoney = safeMoney;
	}


	/**
	 * safeValue <p>getter method</p>
	 * @author 150976
	 * @return  the safeValue
	 */
	public String getSafeValue() {
		return safeValue;
	}


	/**
	 * safeValue <p>setter method</p>
	 * @author 150976
	 * @param safeValue the safeValue to set
	 */
	public void setSafeValue(String safeValue) {
		this.safeValue = safeValue;
	}


	/**
	 * goodsPackage <p>getter method</p>
	 * @author 150976
	 * @return  the goodsPackage
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}


	/**
	 * goodsPackage <p>setter method</p>
	 * @author 150976
	 * @param goodsPackage the goodsPackage to set
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}


	/**
	 * loadingDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the loadingDeptCode
	 */
	public String getLoadingDeptCode() {
		return loadingDeptCode;
	}


	/**
	 * loadingDeptCode <p>setter method</p>
	 * @author 150976
	 * @param loadingDeptCode the loadingDeptCode to set
	 */
	public void setLoadingDeptCode(String loadingDeptCode) {
		this.loadingDeptCode = loadingDeptCode;
	}


	/**
	 * loadingDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the loadingDeptName
	 */
	public String getLoadingDeptName() {
		return loadingDeptName;
	}


	/**
	 * loadingDeptName <p>setter method</p>
	 * @author 150976
	 * @param loadingDeptName the loadingDeptName to set
	 */
	public void setLoadingDeptName(String loadingDeptName) {
		this.loadingDeptName = loadingDeptName;
	}


	/**
	 * unloadingDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the unloadingDeptCode
	 */
	public String getUnloadingDeptCode() {
		return unloadingDeptCode;
	}


	/**
	 * unloadingDeptCode <p>setter method</p>
	 * @author 150976
	 * @param unloadingDeptCode the unloadingDeptCode to set
	 */
	public void setUnloadingDeptCode(String unloadingDeptCode) {
		this.unloadingDeptCode = unloadingDeptCode;
	}


	/**
	 * unloadingDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the unloadingDeptName
	 */
	public String getUnloadingDeptName() {
		return unloadingDeptName;
	}


	/**
	 * unloadingDeptName <p>setter method</p>
	 * @author 150976
	 * @param unloadingDeptName the unloadingDeptName to set
	 */
	public void setUnloadingDeptName(String unloadingDeptName) {
		this.unloadingDeptName = unloadingDeptName;
	}


	/**
	 * transferBill <p>getter method</p>
	 * @author 150976
	 * @return  the transferBill
	 */
	public String getTransferBill() {
		return transferBill;
	}


	/**
	 * transferBill <p>setter method</p>
	 * @author 150976
	 * @param transferBill the transferBill to set
	 */
	public void setTransferBill(String transferBill) {
		this.transferBill = transferBill;
	}


	/**
	 * loseGoodsType <p>getter method</p>
	 * @author 150976
	 * @return  the loseGoodsType
	 */
	public String getLoseGoodsType() {
		return loseGoodsType;
	}


	/**
	 * loseGoodsType <p>setter method</p>
	 * @author 150976
	 * @param loseGoodsType the loseGoodsType to set
	 */
	public void setLoseGoodsType(String loseGoodsType) {
		this.loseGoodsType = loseGoodsType;
	}


	/**
	 * loseNum <p>getter method</p>
	 * @author 150976
	 * @return  the loseNum
	 */
	public String getLoseNum() {
		return loseNum;
	}


	/**
	 * loseNum <p>setter method</p>
	 * @author 150976
	 * @param loseNum the loseNum to set
	 */
	public void setLoseNum(String loseNum) {
		this.loseNum = loseNum;
	}


	/**
	 * isWholeTicket <p>getter method</p>
	 * @author 150976
	 * @return  the isWholeTicket
	 */
	public String getIsWholeTicket() {
		return isWholeTicket;
	}


	/**
	 * isWholeTicket <p>setter method</p>
	 * @author 150976
	 * @param isWholeTicket the isWholeTicket to set
	 */
	public void setIsWholeTicket(String isWholeTicket) {
		this.isWholeTicket = isWholeTicket;
	}


	/**
	 * packageFee <p>getter method</p>
	 * @author 150976
	 * @return  the packageFee
	 */
	public String getPackageFee() {
		return packageFee;
	}


	/**
	 * packageFee <p>setter method</p>
	 * @author 150976
	 * @param packageFee the packageFee to set
	 */
	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}


	/**
	 * loseFlowcode <p>getter method</p>
	 * @author 150976
	 * @return  the loseFlowcode
	 */
	public String getLoseFlowcode() {
		return loseFlowcode;
	}


	/**
	 * loseFlowcode <p>setter method</p>
	 * @author 150976
	 * @param loseFlowcode the loseFlowcode to set
	 */
	public void setLoseFlowcode(String loseFlowcode) {
		this.loseFlowcode = loseFlowcode;
	}


	/**
	 * pureFee <p>getter method</p>
	 * @author 150976
	 * @return  the pureFee
	 */
	public String getPureFee() {
		return pureFee;
	}


	/**
	 * pureFee <p>setter method</p>
	 * @author 150976
	 * @param pureFee the pureFee to set
	 */
	public void setPureFee(String pureFee) {
		this.pureFee = pureFee;
	}


	/**
	 * deliveryFee <p>getter method</p>
	 * @author 150976
	 * @return  the deliveryFee
	 */
	public String getDeliveryFee() {
		return deliveryFee;
	}


	/**
	 * deliveryFee <p>setter method</p>
	 * @author 150976
	 * @param deliveryFee the deliveryFee to set
	 */
	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}


	/**
	 * freightSumFee <p>getter method</p>
	 * @author 150976
	 * @return  the freightSumFee
	 */
	public String getFreightSumFee() {
		return freightSumFee;
	}


	/**
	 * freightSumFee <p>setter method</p>
	 * @author 150976
	 * @param freightSumFee the freightSumFee to set
	 */
	public void setFreightSumFee(String freightSumFee) {
		this.freightSumFee = freightSumFee;
	}


	/**
	 * fillopenEmpCode <p>getter method</p>
	 * @author 150976
	 * @return  the fillopenEmpCode
	 */
	public String getFillopenEmpCode() {
		return fillopenEmpCode;
	}


	/**
	 * fillopenEmpCode <p>setter method</p>
	 * @author 150976
	 * @param fillopenEmpCode the fillopenEmpCode to set
	 */
	public void setFillopenEmpCode(String fillopenEmpCode) {
		this.fillopenEmpCode = fillopenEmpCode;
	}


	/**
	 * fillopenEmpName <p>getter method</p>
	 * @author 150976
	 * @return  the fillopenEmpName
	 */
	public String getFillopenEmpName() {
		return fillopenEmpName;
	}


	/**
	 * fillopenEmpName <p>setter method</p>
	 * @author 150976
	 * @param fillopenEmpName the fillopenEmpName to set
	 */
	public void setFillopenEmpName(String fillopenEmpName) {
		this.fillopenEmpName = fillopenEmpName;
	}


	/**
	 * fillBillDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the fillBillDeptCode
	 */
	public String getFillBillDeptCode() {
		return fillBillDeptCode;
	}


	/**
	 * fillBillDeptCode <p>setter method</p>
	 * @author 150976
	 * @param fillBillDeptCode the fillBillDeptCode to set
	 */
	public void setFillBillDeptCode(String fillBillDeptCode) {
		this.fillBillDeptCode = fillBillDeptCode;
	}


	/**
	 * fillBillDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the fillBillDeptName
	 */
	public String getFillBillDeptName() {
		return fillBillDeptName;
	}


	/**
	 * fillBillDeptName <p>setter method</p>
	 * @author 150976
	 * @param fillBillDeptName the fillBillDeptName to set
	 */
	public void setFillBillDeptName(String fillBillDeptName) {
		this.fillBillDeptName = fillBillDeptName;
	}


	/**
	 * driverCode <p>getter method</p>
	 * @author 150976
	 * @return  the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}


	/**
	 * driverCode <p>setter method</p>
	 * @author 150976
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}


	/**
	 * driverName <p>getter method</p>
	 * @author 150976
	 * @return  the driverName
	 */
	public String getDriverName() {
		return driverName;
	}


	/**
	 * driverName <p>setter method</p>
	 * @author 150976
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	/**
	 * driverDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the driverDeptCode
	 */
	public String getDriverDeptCode() {
		return driverDeptCode;
	}


	/**
	 * driverDeptCode <p>setter method</p>
	 * @author 150976
	 * @param driverDeptCode the driverDeptCode to set
	 */
	public void setDriverDeptCode(String driverDeptCode) {
		this.driverDeptCode = driverDeptCode;
	}


	/**
	 * driverDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the driverDeptName
	 */
	public String getDriverDeptName() {
		return driverDeptName;
	}


	/**
	 * driverDeptName <p>setter method</p>
	 * @author 150976
	 * @param driverDeptName the driverDeptName to set
	 */
	public void setDriverDeptName(String driverDeptName) {
		this.driverDeptName = driverDeptName;
	}


	/**
	 * carCode <p>getter method</p>
	 * @author 150976
	 * @return  the carCode
	 */
	public String getCarCode() {
		return carCode;
	}


	/**
	 * carCode <p>setter method</p>
	 * @author 150976
	 * @param carCode the carCode to set
	 */
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}


	/**
	 * returnBillType <p>getter method</p>
	 * @author 150976
	 * @return  the returnBillType
	 */
	public String getReturnBillType() {
		return returnBillType;
	}


	/**
	 * returnBillType <p>setter method</p>
	 * @author 150976
	 * @param returnBillType the returnBillType to set
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}


	/**
	 * packageIsGood <p>getter method</p>
	 * @author 150976
	 * @return  the packageIsGood
	 */
	public String getPackageIsGood() {
		return packageIsGood;
	}


	/**
	 * packageIsGood <p>setter method</p>
	 * @author 150976
	 * @param packageIsGood the packageIsGood to set
	 */
	public void setPackageIsGood(String packageIsGood) {
		this.packageIsGood = packageIsGood;
	}


	/**
	 * goodsLoseFlowcode <p>getter method</p>
	 * @author 150976
	 * @return  the goodsLoseFlowcode
	 */
	public String getGoodsLoseFlowcode() {
		return goodsLoseFlowcode;
	}


	/**
	 * goodsLoseFlowcode <p>setter method</p>
	 * @author 150976
	 * @param goodsLoseFlowcode the goodsLoseFlowcode to set
	 */
	public void setGoodsLoseFlowcode(String goodsLoseFlowcode) {
		this.goodsLoseFlowcode = goodsLoseFlowcode;
	}


	/**
	 * shortNum <p>getter method</p>
	 * @author 150976
	 * @return  the shortNum
	 */
	public String getShortNum() {
		return shortNum;
	}


	/**
	 * shortNum <p>setter method</p>
	 * @author 150976
	 * @param shortNum the shortNum to set
	 */
	public void setShortNum(String shortNum) {
		this.shortNum = shortNum;
	}


	/**
	 * unloadBusiCode <p>getter method</p>
	 * @author 150976
	 * @return  the unloadBusiCode
	 */
	public String getUnloadBusiCode() {
		return unloadBusiCode;
	}


	/**
	 * unloadBusiCode <p>setter method</p>
	 * @author 150976
	 * @param unloadBusiCode the unloadBusiCode to set
	 */
	public void setUnloadBusiCode(String unloadBusiCode) {
		this.unloadBusiCode = unloadBusiCode;
	}


	/**
	 * moreGoodsNum <p>getter method</p>
	 * @author 150976
	 * @return  the moreGoodsNum
	 */
	public String getMoreGoodsNum() {
		return moreGoodsNum;
	}


	/**
	 * moreGoodsNum <p>setter method</p>
	 * @author 150976
	 * @param moreGoodsNum the moreGoodsNum to set
	 */
	public void setMoreGoodsNum(String moreGoodsNum) {
		this.moreGoodsNum = moreGoodsNum;
	}


	/**
	 * goodsNoTransfer <p>getter method</p>
	 * @author 150976
	 * @return  the goodsNoTransfer
	 */
	public String getGoodsNoTransfer() {
		return goodsNoTransfer;
	}


	/**
	 * goodsNoTransfer <p>setter method</p>
	 * @author 150976
	 * @param goodsNoTransfer the goodsNoTransfer to set
	 */
	public void setGoodsNoTransfer(String goodsNoTransfer) {
		this.goodsNoTransfer = goodsNoTransfer;
	}


	/**
	 * lastLinkDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the lastLinkDeptCode
	 */
	public String getLastLinkDeptCode() {
		return lastLinkDeptCode;
	}


	/**
	 * lastLinkDeptCode <p>setter method</p>
	 * @author 150976
	 * @param lastLinkDeptCode the lastLinkDeptCode to set
	 */
	public void setLastLinkDeptCode(String lastLinkDeptCode) {
		this.lastLinkDeptCode = lastLinkDeptCode;
	}


	/**
	 * lastLinkDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the lastLinkDeptName
	 */
	public String getLastLinkDeptName() {
		return lastLinkDeptName;
	}


	/**
	 * lastLinkDeptName <p>setter method</p>
	 * @author 150976
	 * @param lastLinkDeptName the lastLinkDeptName to set
	 */
	public void setLastLinkDeptName(String lastLinkDeptName) {
		this.lastLinkDeptName = lastLinkDeptName;
	}


	/**
	 * moreGoodsType <p>getter method</p>
	 * @author 150976
	 * @return  the moreGoodsType
	 */
	public String getMoreGoodsType() {
		return moreGoodsType;
	}


	/**
	 * moreGoodsType <p>setter method</p>
	 * @author 150976
	 * @param moreGoodsType the moreGoodsType to set
	 */
	public void setMoreGoodsType(String moreGoodsType) {
		this.moreGoodsType = moreGoodsType;
	}


	/**
	 * moreGoodsFlowcode <p>getter method</p>
	 * @author 150976
	 * @return  the moreGoodsFlowcode
	 */
	public String getMoreGoodsFlowcode() {
		return moreGoodsFlowcode;
	}


	/**
	 * moreGoodsFlowcode <p>setter method</p>
	 * @author 150976
	 * @param moreGoodsFlowcode the moreGoodsFlowcode to set
	 */
	public void setMoreGoodsFlowcode(String moreGoodsFlowcode) {
		this.moreGoodsFlowcode = moreGoodsFlowcode;
	}


	/**
	 * loseMoney <p>getter method</p>
	 * @author 150976
	 * @return  the loseMoney
	 */
	public String getLoseMoney() {
		return loseMoney;
	}


	/**
	 * loseMoney <p>setter method</p>
	 * @author 150976
	 * @param loseMoney the loseMoney to set
	 */
	public void setLoseMoney(String loseMoney) {
		this.loseMoney = loseMoney;
	}


	/**
	 * illegalType <p>getter method</p>
	 * @author 150976
	 * @return  the illegalType
	 */
	public String getIllegalType() {
		return illegalType;
	}


	/**
	 * illegalType <p>setter method</p>
	 * @author 150976
	 * @param illegalType the illegalType to set
	 */
	public void setIllegalType(String illegalType) {
		this.illegalType = illegalType;
	}


	/**
	 * damageClaimAmount <p>getter method</p>
	 * @author 150976
	 * @return  the damageClaimAmount
	 */
	public String getDamageClaimAmount() {
		return damageClaimAmount;
	}


	/**
	 * damageClaimAmount <p>setter method</p>
	 * @author 150976
	 * @param damageClaimAmount the damageClaimAmount to set
	 */
	public void setDamageClaimAmount(String damageClaimAmount) {
		this.damageClaimAmount = damageClaimAmount;
	}


	/**
	 * loseType <p>getter method</p>
	 * @author 150976
	 * @return  the loseType
	 */
	public String getLoseType() {
		return loseType;
	}


	/**
	 * loseType <p>setter method</p>
	 * @author 150976
	 * @param loseType the loseType to set
	 */
	public void setLoseType(String loseType) {
		this.loseType = loseType;
	}


	/**
	 * loseName <p>getter method</p>
	 * @author 150976
	 * @return  the loseName
	 */
	public String getLoseName() {
		return loseName;
	}


	/**
	 * loseName <p>setter method</p>
	 * @author 150976
	 * @param loseName the loseName to set
	 */
	public void setLoseName(String loseName) {
		this.loseName = loseName;
	}


	/**
	 * receverDriverCode <p>getter method</p>
	 * @author 150976
	 * @return  the receverDriverCode
	 */
	public String getReceverDriverCode() {
		return receverDriverCode;
	}


	/**
	 * receverDriverCode <p>setter method</p>
	 * @author 150976
	 * @param receverDriverCode the receverDriverCode to set
	 */
	public void setReceverDriverCode(String receverDriverCode) {
		this.receverDriverCode = receverDriverCode;
	}


	/**
	 * receverDriverName <p>getter method</p>
	 * @author 150976
	 * @return  the receverDriverName
	 */
	public String getReceverDriverName() {
		return receverDriverName;
	}


	/**
	 * receverDriverName <p>setter method</p>
	 * @author 150976
	 * @param receverDriverName the receverDriverName to set
	 */
	public void setReceverDriverName(String receverDriverName) {
		this.receverDriverName = receverDriverName;
	}


	/**
	 * goodsHandleDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the goodsHandleDeptCode
	 */
	public String getGoodsHandleDeptCode() {
		return goodsHandleDeptCode;
	}


	/**
	 * goodsHandleDeptCode <p>setter method</p>
	 * @author 150976
	 * @param goodsHandleDeptCode the goodsHandleDeptCode to set
	 */
	public void setGoodsHandleDeptCode(String goodsHandleDeptCode) {
		this.goodsHandleDeptCode = goodsHandleDeptCode;
	}


	/**
	 * goodsHandleDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the goodsHandleDeptName
	 */
	public String getGoodsHandleDeptName() {
		return goodsHandleDeptName;
	}


	/**
	 * goodsHandleDeptName <p>setter method</p>
	 * @author 150976
	 * @param goodsHandleDeptName the goodsHandleDeptName to set
	 */
	public void setGoodsHandleDeptName(String goodsHandleDeptName) {
		this.goodsHandleDeptName = goodsHandleDeptName;
	}


	/**
	 * driverTakeDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the driverTakeDeptCode
	 */
	public String getDriverTakeDeptCode() {
		return driverTakeDeptCode;
	}


	/**
	 * driverTakeDeptCode <p>setter method</p>
	 * @author 150976
	 * @param driverTakeDeptCode the driverTakeDeptCode to set
	 */
	public void setDriverTakeDeptCode(String driverTakeDeptCode) {
		this.driverTakeDeptCode = driverTakeDeptCode;
	}


	/**
	 * driverTakeDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the driverTakeDeptName
	 */
	public String getDriverTakeDeptName() {
		return driverTakeDeptName;
	}


	/**
	 * driverTakeDeptName <p>setter method</p>
	 * @author 150976
	 * @param driverTakeDeptName the driverTakeDeptName to set
	 */
	public void setDriverTakeDeptName(String driverTakeDeptName) {
		this.driverTakeDeptName = driverTakeDeptName;
	}


	/**
	 * prepayMoney <p>getter method</p>
	 * @author 150976
	 * @return  the prepayMoney
	 */
	public String getPrepayMoney() {
		return prepayMoney;
	}


	/**
	 * prepayMoney <p>setter method</p>
	 * @author 150976
	 * @param prepayMoney the prepayMoney to set
	 */
	public void setPrepayMoney(String prepayMoney) {
		this.prepayMoney = prepayMoney;
	}


	/**
	 * topatMoney <p>getter method</p>
	 * @author 150976
	 * @return  the topatMoney
	 */
	public String getTopatMoney() {
		return topatMoney;
	}


	/**
	 * topatMoney <p>setter method</p>
	 * @author 150976
	 * @param topatMoney the topatMoney to set
	 */
	public void setTopatMoney(String topatMoney) {
		this.topatMoney = topatMoney;
	}


	/**
	 * findNum <p>getter method</p>
	 * @author 150976
	 * @return  the findNum
	 */
	public String getFindNum() {
		return findNum;
	}


	/**
	 * findNum <p>setter method</p>
	 * @author 150976
	 * @param findNum the findNum to set
	 */
	public void setFindNum(String findNum) {
		this.findNum = findNum;
	}


	/**
	 * findType <p>getter method</p>
	 * @author 150976
	 * @return  the findType
	 */
	public String getFindType() {
		return findType;
	}


	/**
	 * findType <p>setter method</p>
	 * @author 150976
	 * @param findType the findType to set
	 */
	public void setFindType(String findType) {
		this.findType = findType;
	}


	/**
	 * unfindNum <p>getter method</p>
	 * @author 150976
	 * @return  the unfindNum
	 */
	public String getUnfindNum() {
		return unfindNum;
	}


	/**
	 * unfindNum <p>setter method</p>
	 * @author 150976
	 * @param unfindNum the unfindNum to set
	 */
	public void setUnfindNum(String unfindNum) {
		this.unfindNum = unfindNum;
	}


	/**
	 * findFlowcode <p>getter method</p>
	 * @author 150976
	 * @return  the findFlowcode
	 */
	public String getFindFlowcode() {
		return findFlowcode;
	}


	/**
	 * findFlowcode <p>setter method</p>
	 * @author 150976
	 * @param findFlowcode the findFlowcode to set
	 */
	public void setFindFlowcode(String findFlowcode) {
		this.findFlowcode = findFlowcode;
	}


	/**
	 * fristLoadDeptCode <p>getter method</p>
	 * @author 150976
	 * @return  the fristLoadDeptCode
	 */
	public String getFristLoadDeptCode() {
		return fristLoadDeptCode;
	}


	/**
	 * fristLoadDeptCode <p>setter method</p>
	 * @author 150976
	 * @param fristLoadDeptCode the fristLoadDeptCode to set
	 */
	public void setFristLoadDeptCode(String fristLoadDeptCode) {
		this.fristLoadDeptCode = fristLoadDeptCode;
	}


	/**
	 * fristLoadDeptName <p>getter method</p>
	 * @author 150976
	 * @return  the fristLoadDeptName
	 */
	public String getFristLoadDeptName() {
		return fristLoadDeptName;
	}


	/**
	 * fristLoadDeptName <p>setter method</p>
	 * @author 150976
	 * @param fristLoadDeptName the fristLoadDeptName to set
	 */
	public void setFristLoadDeptName(String fristLoadDeptName) {
		this.fristLoadDeptName = fristLoadDeptName;
	}


	/**
	 * unloadCreatePersonCode <p>getter method</p>
	 * @author 150976
	 * @return  the unloadCreatePersonCode
	 */
	public String getUnloadCreatePersonCode() {
		return unloadCreatePersonCode;
	}


	/**
	 * unloadCreatePersonCode <p>setter method</p>
	 * @author 150976
	 * @param unloadCreatePersonCode the unloadCreatePersonCode to set
	 */
	public void setUnloadCreatePersonCode(String unloadCreatePersonCode) {
		this.unloadCreatePersonCode = unloadCreatePersonCode;
	}


	/**
	 * unloadCreatePersonName <p>getter method</p>
	 * @author 150976
	 * @return  the unloadCreatePersonName
	 */
	public String getUnloadCreatePersonName() {
		return unloadCreatePersonName;
	}


	/**
	 * unloadCreatePersonName <p>setter method</p>
	 * @author 150976
	 * @param unloadCreatePersonName the unloadCreatePersonName to set
	 */
	public void setUnloadCreatePersonName(String unloadCreatePersonName) {
		this.unloadCreatePersonName = unloadCreatePersonName;
	}


	/**
	 * loadPersonCode <p>getter method</p>
	 * @author 150976
	 * @return  the loadPersonCode
	 */
	public String getLoadPersonCode() {
		return loadPersonCode;
	}


	/**
	 * loadPersonCode <p>setter method</p>
	 * @author 150976
	 * @param loadPersonCode the loadPersonCode to set
	 */
	public void setLoadPersonCode(String loadPersonCode) {
		this.loadPersonCode = loadPersonCode;
	}


	/**
	 * loadPersonName <p>getter method</p>
	 * @author 150976
	 * @return  the loadPersonName
	 */
	public String getLoadPersonName() {
		return loadPersonName;
	}


	/**
	 * loadPersonName <p>setter method</p>
	 * @author 150976
	 * @param loadPersonName the loadPersonName to set
	 */
	public void setLoadPersonName(String loadPersonName) {
		this.loadPersonName = loadPersonName;
	}


	/**
	 * batchFlowcode <p>getter method</p>
	 * @author 150976
	 * @return  the batchFlowcode
	 */
	public String getBatchFlowcode() {
		return batchFlowcode;
	}


	/**
	 * batchFlowcode <p>setter method</p>
	 * @author 150976
	 * @param batchFlowcode the batchFlowcode to set
	 */
	public void setBatchFlowcode(String batchFlowcode) {
		this.batchFlowcode = batchFlowcode;
	}


	/**
	 * unloadPersonCode <p>getter method</p>
	 * @author 150976
	 * @return  the unloadPersonCode
	 */
	public String getUnloadPersonCode() {
		return unloadPersonCode;
	}


	/**
	 * unloadPersonCode <p>setter method</p>
	 * @author 150976
	 * @param unloadPersonCode the unloadPersonCode to set
	 */
	public void setUnloadPersonCode(String unloadPersonCode) {
		this.unloadPersonCode = unloadPersonCode;
	}


	/**
	 * unloadPersonName <p>getter method</p>
	 * @author 150976
	 * @return  the unloadPersonName
	 */
	public String getUnloadPersonName() {
		return unloadPersonName;
	}


	/**
	 * unloadPersonName <p>setter method</p>
	 * @author 150976
	 * @param unloadPersonName the unloadPersonName to set
	 */
	public void setUnloadPersonName(String unloadPersonName) {
		this.unloadPersonName = unloadPersonName;
	}


	/**
	 * deliveryDistance <p>getter method</p>
	 * @author 150976
	 * @return  the deliveryDistance
	 */
	public String getDeliveryDistance() {
		return deliveryDistance;
	}


	/**
	 * deliveryDistance <p>setter method</p>
	 * @author 150976
	 * @param deliveryDistance the deliveryDistance to set
	 */
	public void setDeliveryDistance(String deliveryDistance) {
		this.deliveryDistance = deliveryDistance;
	}


	/**
	 * paidInDeliveryFee <p>getter method</p>
	 * @author 150976
	 * @return  the paidInDeliveryFee
	 */
	public String getPaidInDeliveryFee() {
		return paidInDeliveryFee;
	}


	/**
	 * paidInDeliveryFee <p>setter method</p>
	 * @author 150976
	 * @param paidInDeliveryFee the paidInDeliveryFee to set
	 */
	public void setPaidInDeliveryFee(String paidInDeliveryFee) {
		this.paidInDeliveryFee = paidInDeliveryFee;
	}


	/**
	 * receivableDeliveryFee <p>getter method</p>
	 * @author 150976
	 * @return  the receivableDeliveryFee
	 */
	public String getReceivableDeliveryFee() {
		return receivableDeliveryFee;
	}


	/**
	 * receivableDeliveryFee <p>setter method</p>
	 * @author 150976
	 * @param receivableDeliveryFee the receivableDeliveryFee to set
	 */
	public void setReceivableDeliveryFee(String receivableDeliveryFee) {
		this.receivableDeliveryFee = receivableDeliveryFee;
	}

    
}
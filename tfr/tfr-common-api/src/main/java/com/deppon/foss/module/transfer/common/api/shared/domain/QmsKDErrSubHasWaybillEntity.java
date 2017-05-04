package com.deppon.foss.module.transfer.common.api.shared.domain;


public class QmsKDErrSubHasWaybillEntity extends QmsErrorSubEntity { 
	private static final long serialVersionUID = 1L;

	private Long subHasWaybillId;

    //经手部门标杆编码
    private String handedDeptCode;

    //经手部门名称
    private String handedDeptName;

    //开单部门标杆编码
    private String billingDeptCode;

    //开单部门名称
    private String billingDeptName;

    //发货客户编码
    private String deliveryCustomerCode;

    //收货客户编码
    private String receivingCustomerCode;

    //是否集中接货
    private String centralizedPick;

    //是否驻地部门
    private String residentSector;

    //运输类型
    private String transportType;

    //托运人姓名
    private String consignor;

    //运输性质
    private String transportProperties;

    //收货人
    private String consignee;

    //储运事项
    private String storageItems;

    //收货人电话
    private String receiverPhone;

    //收货人地址
    private String consigneeAddress;

    //提货方式
    private String deliveryMethods;

    //重量
    private String weight;

    //体积
    private String volume;

    //尺寸
    private String fsize;

    //件数
    private String fnumber;

    //货物名称
    private String goodsName;

    //发货时间
    private String deliveryTime;

    //到达部门标杆编码
    private String arriveDeptCode;

    //到达部门名称
    private String arriveDeptName;
    
    //付款方式
    private String paymentMethod;

    //保险金额
    private String insuranceAmount;

    //货物包装
    private String goodsPackage;

    //运费总额
    private String totalFreight;

    //填开人工号
    private String fillopenEmpCode;

    //填开人姓名
    private String fillopenEmpName;

    //司机工号
    private String driverEmpCode;

    //司机姓名
    private String driverEmpName;

    //司机所在部门标杆编码
    private String driverDeptCode;

    //司机所在部门名称
    private String driverDeptName;

    //补录时间
    private String makeupTime;

    //运单经手上一部门标杆编码
    private String lastHandedDeptCode;

    //运单经手上一部门名称
    private String lastHandedDeptName;

    //运单补录部门标杆编码
    private String makeupDeptCode;

    //运单补录部门名称
    private String makeupDeptName;

    //是否有闹事倾向
    private String makeTrouble;

    //理赔所属部门标杆编码
    private String claimsDeptCode;

    //理赔所属部门名称
    private String claimsDeptName;

    //签收类型
    private String signType;

    //签收时间
    private String signTime;

    //索赔金额
    private String claimAmount;

    //CRM理赔工作流审批完成时间
    private String crmWfsFinishTime;

    //交接单/配载单编号
    private String eir;

    //车牌号
    private String licensePlateNumber;

    //返单类型
    private String backBillingType;

    //外包装是否完好
    private String packagingOk;

    //内物短少流水号
    private String innerShortSerialCode;

    //短少量
    private String shortAmount;

    //货物实际重量
    private String actualWeight;

    //实际尺寸
    private String actualSize;

    //实际体积
    private String actualVolume;

    //装车部门标杆编码
    private String loadingDeptCode;

    //装车部门名称
    private String loadingDeptName;

    //卸车部门标杆编码
    private String unloadingDeptCode;

    //卸车部门名称
    private String unloadingDeptName;

    //卸车任务编号
    private String unloadingTaskNumber;

    //有货无交接
    private String notransferGoods;

    //建包部门标杆编码
    private String builtPackDeptCode;

    //建包部门名称
    private String builtPackDeptName;

    //解包部门标杆编码
    private String unpackDeptCode;

    //解包部门名称
    private String unpackDeptName;

    //是否破损
    private String broken;

    //违规类型
    private String violationType;

    //多货类型
    private String moreCargoType;

    //多货件数
    private String moreShipments;

    //多货流水号
    private String moregoodsSerialNum;

    //上报方式
    private String reportingMethods;

    //丢货类型
    private String lostCargoType;

    //找到类型
    private String findType;

    //是否整票
    private String entireTicket;

    //发现方式
    private String findWay;

    //责任人直属上级工号
    private String respSupervisorCode;

    //责任人直属上级姓名
    private String respSupervisorName;

    //责任人的高级经理工号
    private String respSeniorManagerCode;

    //责任人的高级经理姓名
    private String respSeniorManagerName;

    //责任部门负责人工号
    private String respDeptResperCode;

    //责任部门负责人姓名
    private String respDeptResperName;

    //责任人部门负责人工号
    private String repserDeptResperCode;

    //责任人部门负责人姓名
    private String repserDeptResperName;

    //责任大区标杆编码
    private String respRegionCode;

    //责任大区名称
    private String respRegionName;

    //责任营业区标杆编码
    private String respBusinessDistrictCode;

    //责任营业区名称
    private String respBusinessDistrictName;

    //目的站
    private String targetStation;
    
    //发现人工号
    private  String findPersonCode;
    
    //发现人姓名
    private String findPersonName;
    
    //开单时间
    private String billingTime;
    
    //开单错误类型
    private String billingErrType;
    
    //是否签收出库
    private String isSignOutLib;
    
    //开单人工号
    private String billingEmpCode;
    
    //开单人姓名
    private String billingEmpName;

	public Long getSubHasWaybillId() {
		return subHasWaybillId;
	}

	public void setSubHasWaybillId(Long subHasWaybillId) {
		this.subHasWaybillId = subHasWaybillId;
	}

	public String getHandedDeptCode() {
		return handedDeptCode;
	}

	public void setHandedDeptCode(String handedDeptCode) {
		this.handedDeptCode = handedDeptCode;
	}

	public String getHandedDeptName() {
		return handedDeptName;
	}

	public void setHandedDeptName(String handedDeptName) {
		this.handedDeptName = handedDeptName;
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

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getReceivingCustomerCode() {
		return receivingCustomerCode;
	}

	public void setReceivingCustomerCode(String receivingCustomerCode) {
		this.receivingCustomerCode = receivingCustomerCode;
	}

	public String getCentralizedPick() {
		return centralizedPick;
	}

	public void setCentralizedPick(String centralizedPick) {
		this.centralizedPick = centralizedPick;
	}

	public String getResidentSector() {
		return residentSector;
	}

	public void setResidentSector(String residentSector) {
		this.residentSector = residentSector;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getConsignor() {
		return consignor;
	}

	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}

	public String getTransportProperties() {
		return transportProperties;
	}

	public void setTransportProperties(String transportProperties) {
		this.transportProperties = transportProperties;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getStorageItems() {
		return storageItems;
	}

	public void setStorageItems(String storageItems) {
		this.storageItems = storageItems;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getDeliveryMethods() {
		return deliveryMethods;
	}

	public void setDeliveryMethods(String deliveryMethods) {
		this.deliveryMethods = deliveryMethods;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getFsize() {
		return fsize;
	}

	public void setFsize(String fsize) {
		this.fsize = fsize;
	}

	public String getFnumber() {
		return fnumber;
	}

	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
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

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(String totalFreight) {
		this.totalFreight = totalFreight;
	}

	public String getFillopenEmpCode() {
		return fillopenEmpCode;
	}

	public void setFillopenEmpCode(String fillopenEmpCode) {
		this.fillopenEmpCode = fillopenEmpCode;
	}

	public String getFillopenEmpName() {
		return fillopenEmpName;
	}

	public void setFillopenEmpName(String fillopenEmpName) {
		this.fillopenEmpName = fillopenEmpName;
	}

	public String getDriverEmpCode() {
		return driverEmpCode;
	}

	public void setDriverEmpCode(String driverEmpCode) {
		this.driverEmpCode = driverEmpCode;
	}

	public String getDriverEmpName() {
		return driverEmpName;
	}

	public void setDriverEmpName(String driverEmpName) {
		this.driverEmpName = driverEmpName;
	}

	public String getDriverDeptCode() {
		return driverDeptCode;
	}

	public void setDriverDeptCode(String driverDeptCode) {
		this.driverDeptCode = driverDeptCode;
	}

	public String getDriverDeptName() {
		return driverDeptName;
	}

	public void setDriverDeptName(String driverDeptName) {
		this.driverDeptName = driverDeptName;
	}

	public String getMakeupTime() {
		return makeupTime;
	}

	public void setMakeupTime(String makeupTime) {
		this.makeupTime = makeupTime;
	}

	public String getLastHandedDeptCode() {
		return lastHandedDeptCode;
	}

	public void setLastHandedDeptCode(String lastHandedDeptCode) {
		this.lastHandedDeptCode = lastHandedDeptCode;
	}

	public String getLastHandedDeptName() {
		return lastHandedDeptName;
	}

	public void setLastHandedDeptName(String lastHandedDeptName) {
		this.lastHandedDeptName = lastHandedDeptName;
	}

	public String getMakeupDeptCode() {
		return makeupDeptCode;
	}

	public void setMakeupDeptCode(String makeupDeptCode) {
		this.makeupDeptCode = makeupDeptCode;
	}

	public String getMakeupDeptName() {
		return makeupDeptName;
	}

	public void setMakeupDeptName(String makeupDeptName) {
		this.makeupDeptName = makeupDeptName;
	}

	public String getMakeTrouble() {
		return makeTrouble;
	}

	public void setMakeTrouble(String makeTrouble) {
		this.makeTrouble = makeTrouble;
	}

	public String getClaimsDeptCode() {
		return claimsDeptCode;
	}

	public void setClaimsDeptCode(String claimsDeptCode) {
		this.claimsDeptCode = claimsDeptCode;
	}

	public String getClaimsDeptName() {
		return claimsDeptName;
	}

	public void setClaimsDeptName(String claimsDeptName) {
		this.claimsDeptName = claimsDeptName;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getCrmWfsFinishTime() {
		return crmWfsFinishTime;
	}

	public void setCrmWfsFinishTime(String crmWfsFinishTime) {
		this.crmWfsFinishTime = crmWfsFinishTime;
	}

	public String getEir() {
		return eir;
	}

	public void setEir(String eir) {
		this.eir = eir;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getBackBillingType() {
		return backBillingType;
	}

	public void setBackBillingType(String backBillingType) {
		this.backBillingType = backBillingType;
	}

	public String getPackagingOk() {
		return packagingOk;
	}

	public void setPackagingOk(String packagingOk) {
		this.packagingOk = packagingOk;
	}

	public String getInnerShortSerialCode() {
		return innerShortSerialCode;
	}

	public void setInnerShortSerialCode(String innerShortSerialCode) {
		this.innerShortSerialCode = innerShortSerialCode;
	}

	public String getShortAmount() {
		return shortAmount;
	}

	public void setShortAmount(String shortAmount) {
		this.shortAmount = shortAmount;
	}

	public String getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(String actualWeight) {
		this.actualWeight = actualWeight;
	}

	public String getActualSize() {
		return actualSize;
	}

	public void setActualSize(String actualSize) {
		this.actualSize = actualSize;
	}

	public String getActualVolume() {
		return actualVolume;
	}

	public void setActualVolume(String actualVolume) {
		this.actualVolume = actualVolume;
	}

	public String getLoadingDeptCode() {
		return loadingDeptCode;
	}

	public void setLoadingDeptCode(String loadingDeptCode) {
		this.loadingDeptCode = loadingDeptCode;
	}

	public String getLoadingDeptName() {
		return loadingDeptName;
	}

	public void setLoadingDeptName(String loadingDeptName) {
		this.loadingDeptName = loadingDeptName;
	}

	public String getUnloadingDeptCode() {
		return unloadingDeptCode;
	}

	public void setUnloadingDeptCode(String unloadingDeptCode) {
		this.unloadingDeptCode = unloadingDeptCode;
	}

	public String getUnloadingDeptName() {
		return unloadingDeptName;
	}

	public void setUnloadingDeptName(String unloadingDeptName) {
		this.unloadingDeptName = unloadingDeptName;
	}

	public String getUnloadingTaskNumber() {
		return unloadingTaskNumber;
	}

	public void setUnloadingTaskNumber(String unloadingTaskNumber) {
		this.unloadingTaskNumber = unloadingTaskNumber;
	}

	public String getNotransferGoods() {
		return notransferGoods;
	}

	public void setNotransferGoods(String notransferGoods) {
		this.notransferGoods = notransferGoods;
	}

	public String getBuiltPackDeptCode() {
		return builtPackDeptCode;
	}

	public void setBuiltPackDeptCode(String builtPackDeptCode) {
		this.builtPackDeptCode = builtPackDeptCode;
	}

	public String getBuiltPackDeptName() {
		return builtPackDeptName;
	}

	public void setBuiltPackDeptName(String builtPackDeptName) {
		this.builtPackDeptName = builtPackDeptName;
	}

	public String getUnpackDeptCode() {
		return unpackDeptCode;
	}

	public void setUnpackDeptCode(String unpackDeptCode) {
		this.unpackDeptCode = unpackDeptCode;
	}

	public String getUnpackDeptName() {
		return unpackDeptName;
	}

	public void setUnpackDeptName(String unpackDeptName) {
		this.unpackDeptName = unpackDeptName;
	}

	public String getBroken() {
		return broken;
	}

	public void setBroken(String broken) {
		this.broken = broken;
	}

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public String getMoreCargoType() {
		return moreCargoType;
	}

	public void setMoreCargoType(String moreCargoType) {
		this.moreCargoType = moreCargoType;
	}

	public String getMoreShipments() {
		return moreShipments;
	}

	public void setMoreShipments(String moreShipments) {
		this.moreShipments = moreShipments;
	}

	public String getMoregoodsSerialNum() {
		return moregoodsSerialNum;
	}

	public void setMoregoodsSerialNum(String moregoodsSerialNum) {
		this.moregoodsSerialNum = moregoodsSerialNum;
	}

	public String getReportingMethods() {
		return reportingMethods;
	}

	public void setReportingMethods(String reportingMethods) {
		this.reportingMethods = reportingMethods;
	}

	public String getLostCargoType() {
		return lostCargoType;
	}

	public void setLostCargoType(String lostCargoType) {
		this.lostCargoType = lostCargoType;
	}

	public String getFindType() {
		return findType;
	}

	public void setFindType(String findType) {
		this.findType = findType;
	}

	public String getEntireTicket() {
		return entireTicket;
	}

	public void setEntireTicket(String entireTicket) {
		this.entireTicket = entireTicket;
	}

	public String getFindWay() {
		return findWay;
	}

	public void setFindWay(String findWay) {
		this.findWay = findWay;
	}

	public String getRespSupervisorCode() {
		return respSupervisorCode;
	}

	public void setRespSupervisorCode(String respSupervisorCode) {
		this.respSupervisorCode = respSupervisorCode;
	}

	public String getRespSupervisorName() {
		return respSupervisorName;
	}

	public void setRespSupervisorName(String respSupervisorName) {
		this.respSupervisorName = respSupervisorName;
	}

	public String getRespSeniorManagerCode() {
		return respSeniorManagerCode;
	}

	public void setRespSeniorManagerCode(String respSeniorManagerCode) {
		this.respSeniorManagerCode = respSeniorManagerCode;
	}

	public String getRespSeniorManagerName() {
		return respSeniorManagerName;
	}

	public void setRespSeniorManagerName(String respSeniorManagerName) {
		this.respSeniorManagerName = respSeniorManagerName;
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

	public String getRepserDeptResperCode() {
		return repserDeptResperCode;
	}

	public void setRepserDeptResperCode(String repserDeptResperCode) {
		this.repserDeptResperCode = repserDeptResperCode;
	}

	public String getRepserDeptResperName() {
		return repserDeptResperName;
	}

	public void setRepserDeptResperName(String repserDeptResperName) {
		this.repserDeptResperName = repserDeptResperName;
	}

	public String getRespRegionCode() {
		return respRegionCode;
	}

	public void setRespRegionCode(String respRegionCode) {
		this.respRegionCode = respRegionCode;
	}

	public String getRespRegionName() {
		return respRegionName;
	}

	public void setRespRegionName(String respRegionName) {
		this.respRegionName = respRegionName;
	}

	public String getRespBusinessDistrictCode() {
		return respBusinessDistrictCode;
	}

	public void setRespBusinessDistrictCode(String respBusinessDistrictCode) {
		this.respBusinessDistrictCode = respBusinessDistrictCode;
	}

	public String getRespBusinessDistrictName() {
		return respBusinessDistrictName;
	}

	public void setRespBusinessDistrictName(String respBusinessDistrictName) {
		this.respBusinessDistrictName = respBusinessDistrictName;
	}

	public String getTargetStation() {
		return targetStation;
	}

	public void setTargetStation(String targetStation) {
		this.targetStation = targetStation;
	}

	public String getFindPersonCode() {
		return findPersonCode;
	}

	public void setFindPersonCode(String findPersonCode) {
		this.findPersonCode = findPersonCode;
	}

	public String getFindPersonName() {
		return findPersonName;
	}

	public void setFindPersonName(String findPersonName) {
		this.findPersonName = findPersonName;
	}

	public String getBillingTime() {
		return billingTime;
	}

	public void setBillingTime(String billingTime) {
		this.billingTime = billingTime;
	}

	public String getBillingErrType() {
		return billingErrType;
	}

	public void setBillingErrType(String billingErrType) {
		this.billingErrType = billingErrType;
	}

	public String getIsSignOutLib() {
		return isSignOutLib;
	}

	public void setIsSignOutLib(String isSignOutLib) {
		this.isSignOutLib = isSignOutLib;
	}

	public String getBillingEmpCode() {
		return billingEmpCode;
	}

	public void setBillingEmpCode(String billingEmpCode) {
		this.billingEmpCode = billingEmpCode;
	}

	public String getBillingEmpName() {
		return billingEmpName;
	}

	public void setBillingEmpName(String billingEmpName) {
		this.billingEmpName = billingEmpName;
	}

}
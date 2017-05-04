package com.deppon.foss.print.labelprint.impl;

import java.util.List;

public class LabelPrintForm 
{
	//350909          郭倩云             零担轻货上分拣取得是城市简称
	private String  simpleLeaveCity;
	private String optNum=" ";
	private String printDate=" ";
	private List<String> barcode;
	public void setBarcode(List<String> barcode) {
		this.barcode = barcode;
	}
	//流水号集合
	private List<String> printPiecesList = null;
   
	private String finalOutCode = " ";
	private String destionalCode = " ";	
	private String finalOutfield=" ";
	//350909     郭倩云        最终外场简称(例如武汉转运场简称武汉)
	private String lastFirstFinalOutField=" ";
	//350909     郭倩云        倒数第二个外场简称(例如武汉转运场简称武汉)
	private String lastSecondFinalOutField=" ";
	//350909     郭倩云        倒数第三个外场简称(例如武汉转运场简称武汉)
	private String lastThirdFinalOutField=" ";
	private int totalPieces=0;
	private String isDelivery=" ";
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	private String goodsType = " ";
	private String leaveCity=" ";
	private String secLoadOrgName=" ";
	private String destination=" ";
	private String waybillNum1=" ";
	private String waybillNum2=" ";
	private String transportProperty=" ";
	private String addr1=" ";
	private String addr2=" ";
	private String addr3=" ";
	private String addr4=" ";
	private String location1=" ";
	private String location2=" ";
	private String location3=" ";
	private String location4=" ";
	private int left;
	private boolean isAgent;
	private String preAssembly = "";
	private boolean signFlag = false;
	//大客户标记
	private String bigCustomer=" ";
	 //wutao === start
	//是否是直达的标识
	private String isNonStop = "";
	//渠道单号
	private String channelNumber="";
	public String getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}
	//对接外场
	private String partnerBillingLogo;
	
	public String getPartnerBillingLogo() {
		return partnerBillingLogo;
	}
	public void setPartnerBillingLogo(String partnerBillingLogo) {
		this.partnerBillingLogo = partnerBillingLogo;
	}
	/**
	 * 转寄退回件新增
	 */
	//转寄退回类型-2015-12-08
	private String forwardRerturn=" ";
	private String receiveCustomerContact=" ";
	private String receiveCustomerphone=" ";
	private String receiveCustomerProvName=" ";
	private String receiveCustomerCityName=" ";
	private String receiveCustomerDistName=" ";
	private String receiveCustomerAddress=" ";
	private String receiveMessage=" ";
	//保价金额
	private int insuranceFee=0;
	//代收货款
	private int codAmount=0;
	//签回单
	private String returnBillType=" ";
	//包装费
	private int packageFee=0;
	//运费】、
	private int transportFee=0;
	//到付款合计
	private int toPayAmount=0;
	
	
	/**
	 * 打木标签需要字段
	 * @return
	 */
	private String waybillNo;//运单号
	private String totalpieces;//总件数
	private String transtype;//运输性质
	private String weight;//总重量
	private String numTotal;//总件数
	private String serialnos;//流水号
	private String printdate;//打印日期
	private String optusernum;//用户工号
	private String packing;//包装类型
	private String Quirement;//包装要求
	private String GoodsSize;//包装尺寸
	private String Num;//包装件数
	private String GoodsVolume;//包装体积
	private String standGoodsSize;//打木架包装尺寸
	private String standGoodsNum;//打木架货物件数
	private String standGoodsVolume;//打木架货物体积
	private String boxGoodsNum;//打木箱货物件数
	private String boxGoodsSize;//打木箱货物尺寸
	private String boxGoodsVolume;//打木箱货物体积
	private String salverGoodsNum;//打木托件数
	//打木架要求
	private String standRequirement;
	//打木箱要求
	private String boxRequirement;
	//打木托要求
	private String salverRequirement;
	
	public String getNum() {
		return Num;
	}
	public void setNum(String num) {
		Num = num;
	}
	public String getGoodsSize() {
		return GoodsSize;
	}
	public void setGoodsSize(String goodsSize) {
		GoodsSize = goodsSize;
	}
	public String getGoodsVolume() {
		return GoodsVolume;
	}
	public void setGoodsVolume(String goodsVolume) {
		GoodsVolume = goodsVolume;
	}
	public String getQuirement() {
		return Quirement;
	}
	public void setQuirement(String quirement) {
		Quirement = quirement;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getStandRequirement() {
		return standRequirement;
	}
	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}
	public String getBoxRequirement() {
		return boxRequirement;
	}
	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}
	public String getSalverRequirement() {
		return salverRequirement;
	}
	public void setSalverRequirement(String salverRequirement) {
		this.salverRequirement = salverRequirement;
	}
	public int getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(int insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public int getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(int codAmount) {
		this.codAmount = codAmount;
	}
	public String getReturnBillType() {
		return returnBillType;
	}
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public int getPackageFee() {
		return packageFee;
	}
	public void setPackageFee(int packageFee) {
		this.packageFee = packageFee;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getTotalpieces() {
		return totalpieces;
	}
	public void setTotalpieces(String totalpieces) {
		this.totalpieces = totalpieces;
	}
	public String getSerialnos() {
		return serialnos;
	}
	public void setSerialnos(String serialnos) {
		this.serialnos = serialnos;
	}
	public String getPrintdate() {
		return printdate;
	}
	public void setPrintdate(String printdate) {
		this.printdate = printdate;
	}
	public String getOptusernum() {
		return optusernum;
	}
	public void setOptusernum(String optusernum) {
		this.optusernum = optusernum;
	}
	public String getStandGoodsSize() {
		return standGoodsSize;
	}
	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}
	public String getStandGoodsNum() {
		return standGoodsNum;
	}
	public void setStandGoodsNum(String standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}
	public String getStandGoodsVolume() {
		return standGoodsVolume;
	}
	public void setStandGoodsVolume(String standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}
	public String getBoxGoodsNum() {
		return boxGoodsNum;
	}
	public void setBoxGoodsNum(String boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}
	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}
	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}
	public String getBoxGoodsVolume() {
		return boxGoodsVolume;
	}
	public void setBoxGoodsVolume(String boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
	}
	public String getSalverGoodsNum() {
		return salverGoodsNum;
	}
	public void setSalverGoodsNum(String salverGoodsNum) {
		this.salverGoodsNum = salverGoodsNum;
	}
	public int getTransportFee() {
		return transportFee;
	}
	public void setTransportFee(int transportFee) {
		this.transportFee = transportFee;
	}
	public int getToPayAmount() {
		return toPayAmount;
	}
	public void setToPayAmount(int toPayAmount) {
		this.toPayAmount = toPayAmount;
	}
	
	public String getForwardRerturn() {
		return forwardRerturn;
	}
	public void setForwardRerturn(String forwardRerturn) {
		this.forwardRerturn = forwardRerturn;
	}
	public String getIsNonStop() {
		return isNonStop;
	}
	public void setIsNonStop(String isNonStop) {
		this.isNonStop = isNonStop;
	}
	//是否展会货
	private String isExhibit="";
		
	public String getIsExhibit() {
			return isExhibit;
		}
	public void setIsExhibit(String isExhibit) {
			this.isExhibit = isExhibit;
		}
	//是否显示“德邦物流”
	private String isPrintLogo;
	public String getIsPrintLogo() {
		return isPrintLogo;
	}
	public void setIsPrintLogo(String isPrintLogo) {
		this.isPrintLogo = isPrintLogo;
	}
	//区域级字段
	private String countyRegion = "";
	public String getCountyRegion() {
		return countyRegion;
	}
	public void setCountyRegion(String countyRegion) {
		this.countyRegion = countyRegion;
	}
	//wutao == end
	public String getBigCustomer() {
		return bigCustomer;
	}
	public void setBigCustomer(String bigCustomer) {
		this.bigCustomer = bigCustomer;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	private int top;
	
	public String getOptNum() {
		return optNum;
	}
	public void setOptNum(String optNum) {
		this.optNum = optNum;
	}
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	public String getFinalOutfield() {
		return finalOutfield;
	}
	public void setFinalOutfield(String finalOutfield) {
		this.finalOutfield = finalOutfield;
	}
	public int getTotalPieces() {
		return totalPieces;
	}
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
	public String getIsDelivery() {
		return isDelivery;
	}
	public void setIsDelivery(String isDelivery) {
		this.isDelivery = isDelivery;
	}
	public String getLeaveCity() {
		return leaveCity;
	}
	public void setLeaveCity(String leaveCity) {
		this.leaveCity = leaveCity;
	}
	public String getSecLoadOrgName() {
		return secLoadOrgName;
	}
	public void setSecLoadOrgName(String secLoadOrgName) {
		this.secLoadOrgName = secLoadOrgName;
	}
	public List<String> getBarcode() {
		return barcode;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getWaybillNum1() {
		return waybillNum1;
	}
	public void setWaybillNum1(String waybillNum1) {
		this.waybillNum1 = waybillNum1;
	}
	public String getWaybillNum2() {
		return waybillNum2;
	}
	public void setWaybillNum2(String waybillNum2) {
		this.waybillNum2 = waybillNum2;
	}
	public String getTransportProperty() {
		return transportProperty;
	}
	public void setTransportProperty(String transportProperty) {
		this.transportProperty = transportProperty;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getAddr4() {
		return addr4;
	}
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
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
	public String getFinalOutCode() {
		return finalOutCode;
	}
	public void setFinalOutCode(String finalOutCode) {
		this.finalOutCode = finalOutCode;
	}
	public String getDestionalCode() {
		return destionalCode;
	}
	public void setDestionalCode(String destionalCode) {
		this.destionalCode = destionalCode;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getPreAssembly()
	{
		return preAssembly;
	}
	public void setPreAssembly(String preAssembly)
	{
		this.preAssembly = preAssembly;
	}
	public boolean isSignFlag() {
		return signFlag;
	}
	public void setSignFlag(boolean signFlag) {
		this.signFlag = signFlag;
	}
	public boolean isAgent() {
		return isAgent;
	}
	public void setAgent(boolean isAgent) {
		this.isAgent = isAgent;
	}
	public List<String> getPrintPiecesList() {
		return printPiecesList;
	}
	public void setPrintPiecesList(List<String> printPiecesList) {
		this.printPiecesList = printPiecesList;
	}
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	public String getReceiveCustomerphone() {
		return receiveCustomerphone;
	}
	public void setReceiveCustomerphone(String receiveCustomerphone) {
		this.receiveCustomerphone = receiveCustomerphone;
	}
	public String getReceiveCustomerProvName() {
		return receiveCustomerProvName;
	}
	public void setReceiveCustomerProvName(String receiveCustomerProvName) {
		this.receiveCustomerProvName = receiveCustomerProvName;
	}
	public String getReceiveCustomerCityName() {
		return receiveCustomerCityName;
	}
	public void setReceiveCustomerCityName(String receiveCustomerCityName) {
		this.receiveCustomerCityName = receiveCustomerCityName;
	}
	public String getReceiveCustomerDistName() {
		return receiveCustomerDistName;
	}
	public void setReceiveCustomerDistName(String receiveCustomerDistName) {
		this.receiveCustomerDistName = receiveCustomerDistName;
	}
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public String getReceiveMessage() {
		return receiveMessage;
	}
	public void setReceiveMessage(String receiveMessage) {
		this.receiveMessage = receiveMessage;
	}
	public String getNumTotal() {
		return numTotal;
	}
	public void setNumTotal(String numTotal) {
		this.numTotal = numTotal;
	}
	
	
	private String isElecLtlWaybill;
	private String otherPackage;
	public String getIsElecLtlWaybill() {
		return isElecLtlWaybill;
	}
	public void setIsElecLtlWaybill(String isElecLtlWaybill) {
		this.isElecLtlWaybill = isElecLtlWaybill;
	}
	public String getOtherPackage() {
		return otherPackage;
	}
	public void setOtherPackage(String otherPackage) {
		this.otherPackage = otherPackage;
	}
	public String getLastSecondFinalOutField() {
		return lastSecondFinalOutField;
	}
	public void setLastSecondFinalOutField(String lastSecondFinalOutField) {
		this.lastSecondFinalOutField = lastSecondFinalOutField;
	}
	public String getLastThirdFinalOutField() {
		return lastThirdFinalOutField;
	}
	public void setLastThirdFinalOutField(String lastThirdFinalOutField) {
		this.lastThirdFinalOutField = lastThirdFinalOutField;
	}
	public String getLastFirstFinalOutField() {
		return lastFirstFinalOutField;
	}
	public void setLastFirstFinalOutField(String lastFirstFinalOutField) {
		this.lastFirstFinalOutField = lastFirstFinalOutField;
	}
	public String getSimpleLeaveCity() {
		return simpleLeaveCity;
	}
	public void setSimpleLeaveCity(String simpleLeaveCity) {
		this.simpleLeaveCity = simpleLeaveCity;
	}
}

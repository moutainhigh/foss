/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.common.api.shared.dto;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 运单基本信息dto 
 * @author 099197-foss-zhoudejun
 * @date 2013-3-14 下午3:18:19
 */
public class AwbBasicDto {
	
	private String awbPrefix;
	private String AwbNo;
	private String awbPostfix;
	private Integer bookID;
	private String agentCode;
	private String agentName;
	private String agentIataCode;
	private String agentAccount;
	private String routing;
	private String carriers;
	private Integer piece;
	private Double weight;
	private String unit;
	private String rateClass;
	private String itemNo;
	private Double volume;
	private String _package;
	private String goodsCode;
	private String goods;
	private String spCode;
	private String shipperName;
	private String shipperAddress;
	private String shipperCity;
	private String shipperState;
	private String shipperCountry;
	private String shipperCountryCode;
	private String shipperZipCode;
	private String shipperTelephone;
	private String shipperAccount;
	private String consigneeName;
	private String consigneeAddress;
	private String consigneeCity;
	private String consigneeState;
	private String consigneeCountry;
	private String consigneeCountryCode;
	private String consigneeZipCode;
	private String consigneeTelephone;
	private String consigneeAccount;
	private String handlingInfo;
	private String clientCode;
	private String signOfShipper;
	private String createdPlace;
	private String office;
	private Integer intDom;
	private Integer groupNo;
	private String createdCity;
	private Integer handlingFlag;
	private String flightNo1;
	private XMLGregorianCalendar flightDate1;
	private String flightNo2;
	private XMLGregorianCalendar flightDate2;
	private String opID;
	private XMLGregorianCalendar opDate;
	private String comment;
	private String serviceCode;
	private String depAirportName;
	private String destAirportName;
	private String notifyName;
	private String notifyAddress;
	private String notifyCity;
	private String notifyState;
	private String notifyCountryCode;
	private String notifyZipCode;
	private String notifyContactID;
	private String notifyTelephone;
	private String currentCity;
	private String exportFlightInfo1;
	private String exportFlightInfo2;
	private String eFreight;
	private String printSerialNo;
	private String isCustomsFiling;
	private XMLGregorianCalendar customsFilingTime;
	private String customsFilingOpID;
	private String isCustomsSupervise;
	private String customsReleaseNo;
	public String getAwbPrefix() {
		return awbPrefix;
	}
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
	public String getAwbNo() {
		return AwbNo;
	}
	public void setAwbNo(String awbNo) {
		AwbNo = awbNo;
	}
	public String getAwbPostfix() {
		return awbPostfix;
	}
	public void setAwbPostfix(String awbPostfix) {
		this.awbPostfix = awbPostfix;
	}
	public Integer getBookID() {
		return bookID;
	}
	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentIataCode() {
		return agentIataCode;
	}
	public void setAgentIataCode(String agentIataCode) {
		this.agentIataCode = agentIataCode;
	}
	public String getAgentAccount() {
		return agentAccount;
	}
	public void setAgentAccount(String agentAccount) {
		this.agentAccount = agentAccount;
	}
	public String getRouting() {
		return routing;
	}
	public void setRouting(String routing) {
		this.routing = routing;
	}
	public String getCarriers() {
		return carriers;
	}
	public void setCarriers(String carriers) {
		this.carriers = carriers;
	}
	public Integer getPiece() {
		return piece;
	}
	public void setPiece(Integer piece) {
		this.piece = piece;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRateClass() {
		return rateClass;
	}
	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public String get_package() {
		return _package;
	}
	public void set_package(String _package) {
		this._package = _package;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public String getSpCode() {
		return spCode;
	}
	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getShipperAddress() {
		return shipperAddress;
	}
	public void setShipperAddress(String shipperAddress) {
		this.shipperAddress = shipperAddress;
	}
	public String getShipperCity() {
		return shipperCity;
	}
	public void setShipperCity(String shipperCity) {
		this.shipperCity = shipperCity;
	}
	public String getShipperState() {
		return shipperState;
	}
	public void setShipperState(String shipperState) {
		this.shipperState = shipperState;
	}
	public String getShipperCountry() {
		return shipperCountry;
	}
	public void setShipperCountry(String shipperCountry) {
		this.shipperCountry = shipperCountry;
	}
	public String getShipperCountryCode() {
		return shipperCountryCode;
	}
	public void setShipperCountryCode(String shipperCountryCode) {
		this.shipperCountryCode = shipperCountryCode;
	}
	public String getShipperZipCode() {
		return shipperZipCode;
	}
	public void setShipperZipCode(String shipperZipCode) {
		this.shipperZipCode = shipperZipCode;
	}
	public String getShipperTelephone() {
		return shipperTelephone;
	}
	public void setShipperTelephone(String shipperTelephone) {
		this.shipperTelephone = shipperTelephone;
	}
	public String getShipperAccount() {
		return shipperAccount;
	}
	public void setShipperAccount(String shipperAccount) {
		this.shipperAccount = shipperAccount;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getConsigneeCity() {
		return consigneeCity;
	}
	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}
	public String getConsigneeState() {
		return consigneeState;
	}
	public void setConsigneeState(String consigneeState) {
		this.consigneeState = consigneeState;
	}
	public String getConsigneeCountry() {
		return consigneeCountry;
	}
	public void setConsigneeCountry(String consigneeCountry) {
		this.consigneeCountry = consigneeCountry;
	}
	public String getConsigneeCountryCode() {
		return consigneeCountryCode;
	}
	public void setConsigneeCountryCode(String consigneeCountryCode) {
		this.consigneeCountryCode = consigneeCountryCode;
	}
	public String getConsigneeZipCode() {
		return consigneeZipCode;
	}
	public void setConsigneeZipCode(String consigneeZipCode) {
		this.consigneeZipCode = consigneeZipCode;
	}
	public String getConsigneeTelephone() {
		return consigneeTelephone;
	}
	public void setConsigneeTelephone(String consigneeTelephone) {
		this.consigneeTelephone = consigneeTelephone;
	}
	public String getConsigneeAccount() {
		return consigneeAccount;
	}
	public void setConsigneeAccount(String consigneeAccount) {
		this.consigneeAccount = consigneeAccount;
	}
	public String getHandlingInfo() {
		return handlingInfo;
	}
	public void setHandlingInfo(String handlingInfo) {
		this.handlingInfo = handlingInfo;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getSignOfShipper() {
		return signOfShipper;
	}
	public void setSignOfShipper(String signOfShipper) {
		this.signOfShipper = signOfShipper;
	}
	public String getCreatedPlace() {
		return createdPlace;
	}
	public void setCreatedPlace(String createdPlace) {
		this.createdPlace = createdPlace;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public Integer getIntDom() {
		return intDom;
	}
	public void setIntDom(Integer intDom) {
		this.intDom = intDom;
	}
	public Integer getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}
	public String getCreatedCity() {
		return createdCity;
	}
	public void setCreatedCity(String createdCity) {
		this.createdCity = createdCity;
	}
	public Integer getHandlingFlag() {
		return handlingFlag;
	}
	public void setHandlingFlag(Integer handlingFlag) {
		this.handlingFlag = handlingFlag;
	}
	public String getFlightNo1() {
		return flightNo1;
	}
	public void setFlightNo1(String flightNo1) {
		this.flightNo1 = flightNo1;
	}
	public XMLGregorianCalendar getFlightDate1() {
		return flightDate1;
	}
	public void setFlightDate1(XMLGregorianCalendar flightDate1) {
		this.flightDate1 = flightDate1;
	}
	public String getFlightNo2() {
		return flightNo2;
	}
	public void setFlightNo2(String flightNo2) {
		this.flightNo2 = flightNo2;
	}
	public XMLGregorianCalendar getFlightDate2() {
		return flightDate2;
	}
	public void setFlightDate2(XMLGregorianCalendar flightDate2) {
		this.flightDate2 = flightDate2;
	}
	public String getOpID() {
		return opID;
	}
	public void setOpID(String opID) {
		this.opID = opID;
	}
	public XMLGregorianCalendar getOpDate() {
		return opDate;
	}
	public void setOpDate(XMLGregorianCalendar opDate) {
		this.opDate = opDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getDepAirportName() {
		return depAirportName;
	}
	public void setDepAirportName(String depAirportName) {
		this.depAirportName = depAirportName;
	}
	public String getDestAirportName() {
		return destAirportName;
	}
	public void setDestAirportName(String destAirportName) {
		this.destAirportName = destAirportName;
	}
	public String getNotifyName() {
		return notifyName;
	}
	public void setNotifyName(String notifyName) {
		this.notifyName = notifyName;
	}
	public String getNotifyAddress() {
		return notifyAddress;
	}
	public void setNotifyAddress(String notifyAddress) {
		this.notifyAddress = notifyAddress;
	}
	public String getNotifyCity() {
		return notifyCity;
	}
	public void setNotifyCity(String notifyCity) {
		this.notifyCity = notifyCity;
	}
	public String getNotifyState() {
		return notifyState;
	}
	public void setNotifyState(String notifyState) {
		this.notifyState = notifyState;
	}
	public String getNotifyCountryCode() {
		return notifyCountryCode;
	}
	public void setNotifyCountryCode(String notifyCountryCode) {
		this.notifyCountryCode = notifyCountryCode;
	}
	public String getNotifyZipCode() {
		return notifyZipCode;
	}
	public void setNotifyZipCode(String notifyZipCode) {
		this.notifyZipCode = notifyZipCode;
	}
	public String getNotifyContactID() {
		return notifyContactID;
	}
	public void setNotifyContactID(String notifyContactID) {
		this.notifyContactID = notifyContactID;
	}
	public String getNotifyTelephone() {
		return notifyTelephone;
	}
	public void setNotifyTelephone(String notifyTelephone) {
		this.notifyTelephone = notifyTelephone;
	}
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getExportFlightInfo1() {
		return exportFlightInfo1;
	}
	public void setExportFlightInfo1(String exportFlightInfo1) {
		this.exportFlightInfo1 = exportFlightInfo1;
	}
	public String getExportFlightInfo2() {
		return exportFlightInfo2;
	}
	public void setExportFlightInfo2(String exportFlightInfo2) {
		this.exportFlightInfo2 = exportFlightInfo2;
	}
	public String geteFreight() {
		return eFreight;
	}
	public void seteFreight(String eFreight) {
		this.eFreight = eFreight;
	}
	public String getPrintSerialNo() {
		return printSerialNo;
	}
	public void setPrintSerialNo(String printSerialNo) {
		this.printSerialNo = printSerialNo;
	}
	public String getIsCustomsFiling() {
		return isCustomsFiling;
	}
	public void setIsCustomsFiling(String isCustomsFiling) {
		this.isCustomsFiling = isCustomsFiling;
	}
	public XMLGregorianCalendar getCustomsFilingTime() {
		return customsFilingTime;
	}
	public void setCustomsFilingTime(XMLGregorianCalendar customsFilingTime) {
		this.customsFilingTime = customsFilingTime;
	}
	public String getCustomsFilingOpID() {
		return customsFilingOpID;
	}
	public void setCustomsFilingOpID(String customsFilingOpID) {
		this.customsFilingOpID = customsFilingOpID;
	}
	public String getIsCustomsSupervise() {
		return isCustomsSupervise;
	}
	public void setIsCustomsSupervise(String isCustomsSupervise) {
		this.isCustomsSupervise = isCustomsSupervise;
	}
	public String getCustomsReleaseNo() {
		return customsReleaseNo;
	}
	public void setCustomsReleaseNo(String customsReleaseNo) {
		this.customsReleaseNo = customsReleaseNo;
	}
	
}
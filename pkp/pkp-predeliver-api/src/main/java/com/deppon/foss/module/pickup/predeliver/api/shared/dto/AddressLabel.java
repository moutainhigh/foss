package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

public class AddressLabel {
	private String waybillNo;//运单号
	private String suggestionTreatment;//预处理建议
	private String consigneeAddress;//送货地址
	private String longitude;//经度
	private String latitude;//纬度
	private String addressNotes;//地址备注
	private String isGis;//是否GIS处理过
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSuggestionTreatment() {
		return suggestionTreatment;
	}
	public void setSuggestionTreatment(String suggestionTreatment) {
		this.suggestionTreatment = suggestionTreatment;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddressNotes() {
		return addressNotes;
	}
	public void setAddressNotes(String addressNotes) {
		this.addressNotes = addressNotes;
	}
	public String getIsGis() {
		return isGis;
	}
	public void setIsGis(String isGis) {
		this.isGis = isGis;
	}
}

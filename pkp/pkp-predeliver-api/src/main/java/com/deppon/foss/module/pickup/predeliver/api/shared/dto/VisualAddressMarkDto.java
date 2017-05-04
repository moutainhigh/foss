package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

/**
 * 
 * @author 239284
 * 运单列表地址坐标标记
 *
 */
public class VisualAddressMarkDto {

	/**
	 *  交单id
	 */
	private String id;
	
	/** 
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 实际送货地址
	 */
	private String actualAddress;
	
	/**
	 * 聚合坐标
	 */
	private String juheAdress;
	
	/**
	 * 地址解析来源类型
	 */
	private String matchType;
	
	/**
	 * 实际小区名称
	 */
	private String actualSmallzoneName;
	
	/**
	 * 实际小区代码
	 */
	private String actualSmallzoneCode;
	
	/** 
	 * 省份 
	 */
	private String receiveCustomerProvCode;
	
	/** 
	 * 城市
	 */
	private String receiveCustomerCityCode;
	/** 
	 * 区县
	 */
	private String receiveCustomerDistCode;
	
	/**
	 * 具体地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 * 地址备注
	 */
	private String receiveCustomerAddressNote;
	
	/** 
	 * 电话
	 */
	private String phone;
	/** 
	 * 手机 
	 */
	private String tel;
	
	/** 
	 * 经度
	 */
	private String longitude;
	
	/** 
	 * 纬度
	 */
	private String latitude;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getActualAddress() {
		return actualAddress;
	}

	public void setActualAddress(String actualAddress) {
		this.actualAddress = actualAddress;
	}

	public String getActualSmallzoneName() {
		return actualSmallzoneName;
	}

	public void setActualSmallzoneName(String actualSmallzoneName) {
		this.actualSmallzoneName = actualSmallzoneName;
	}

	public String getActualSmallzoneCode() {
		return actualSmallzoneCode;
	}

	public void setActualSmallzoneCode(String actualSmallzoneCode) {
		this.actualSmallzoneCode = actualSmallzoneCode;
	}

	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getJuheAdress() {
		return juheAdress;
	}

	public void setJuheAdress(String juheAdress) {
		this.juheAdress = juheAdress;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	
}

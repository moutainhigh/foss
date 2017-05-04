package com.deppon.esb.pojo.domain.foss2ecs;

import java.io.Serializable;
import java.util.Date;

/*
 * 缓存客户地址实体
 * @author 350859 liulu
 * 2017-2-24 上午11:37:55
 * CustomerAddressEntity.java
 * com.deppon.tak.module.waybill.common.shared.domain
 */
public class CustomerAddressFossResponse implements Serializable {

	/*
	 * @author 350859 liulu
	 * 2017-2-24 上午11:37:55
	 * CustomerAddressEntity.java
	 * com.deppon.tak.module.waybill.common.shared.domain
	 */
	private static final long serialVersionUID = 8246583709227931645L;

	 /*国家名称*/
    private String state;

    /*省名称*/
    private String province;

    /*城市名称*/
    private String city;

    /*区县名称*/
    private String district;

    /*乡镇名称*/
    private String township;

    /*街道名称*/
    private String street;
    
    /*国家Code*/
    private String stateCode;
    
    /*省Code*/
    private String provinceCode;
    
    /*城市Code*/
    private String cityCode;
    
    /*区县Code*/
    private String districtCode;
    
    /*乡镇名称Code*/
    private String townshipCode;
    
    /*街道Code*/
    private String streetCode;
	
    /*详细地址*/
    private String contactAddress;

    /*地址时间。在存地址的时候，不要存入值，只是用于查询时返回页面使用*/
    private Date addressTime;
    
    /*客户手机*/
    private String customerMobilePhone;
    
    /*客户电话*/
    private String customerPhone;
    
	/* 350859 liulu
	 * @return the customerMobilePhone
	 * @2017-3-7 下午2:42:18
	 */
	public String getCustomerMobilePhone() {
		return customerMobilePhone;
	}

	/* 350859 liulu
	 * @return the customerMobilePhone
	 * @2017-3-7 下午2:42:18
	 */
	public void setCustomerMobilePhone(String customerMobilePhone) {
		this.customerMobilePhone = customerMobilePhone;
	}

	/* 350859 liulu
	 * @return the customerPhone
	 * @2017-3-7 下午2:42:18
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/* 350859 liulu
	 * @return the customerPhone
	 * @2017-3-7 下午2:42:18
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/* 350859 liulu
	 * @return the addressTime
	 * @2017-2-28 下午5:51:25
	 */
	public Date getAddressTime() {
		return addressTime;
	}

	/* 350859 liulu
	 * @return the addressTime
	 * @2017-2-28 下午5:51:25
	 */
	public void setAddressTime(Date addressTime) {
		this.addressTime = addressTime;
	}

	/* 350859 liulu
	 * @return the state
	 * @2017-2-28 上午10:28:47
	 */
	public String getState() {
		return state;
	}

	/* 350859 liulu
	 * @return the state
	 * @2017-2-28 上午10:28:47
	 */
	public void setState(String state) {
		this.state = state;
	}

	/* 350859 liulu
	 * @return the province
	 * @2017-2-28 上午10:28:47
	 */
	public String getProvince() {
		return province;
	}

	/* 350859 liulu
	 * @return the province
	 * @2017-2-28 上午10:28:47
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/* 350859 liulu
	 * @return the city
	 * @2017-2-28 上午10:28:47
	 */
	public String getCity() {
		return city;
	}

	/* 350859 liulu
	 * @return the city
	 * @2017-2-28 上午10:28:47
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/* 350859 liulu
	 * @return the district
	 * @2017-2-28 上午10:28:47
	 */
	public String getDistrict() {
		return district;
	}

	/* 350859 liulu
	 * @return the district
	 * @2017-2-28 上午10:28:47
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/* 350859 liulu
	 * @return the township
	 * @2017-2-28 上午10:28:47
	 */
	public String getTownship() {
		return township;
	}

	/* 350859 liulu
	 * @return the township
	 * @2017-2-28 上午10:28:47
	 */
	public void setTownship(String township) {
		this.township = township;
	}

	/* 350859 liulu
	 * @return the street
	 * @2017-2-28 上午10:28:47
	 */
	public String getStreet() {
		return street;
	}

	/* 350859 liulu
	 * @return the street
	 * @2017-2-28 上午10:28:47
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/* 350859 liulu
	 * @return the stateCode
	 * @2017-2-28 上午10:28:47
	 */
	public String getStateCode() {
		return stateCode;
	}

	/* 350859 liulu
	 * @return the stateCode
	 * @2017-2-28 上午10:28:47
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/* 350859 liulu
	 * @return the provinceCode
	 * @2017-2-28 上午10:28:47
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/* 350859 liulu
	 * @return the provinceCode
	 * @2017-2-28 上午10:28:47
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/* 350859 liulu
	 * @return the cityCode
	 * @2017-2-28 上午10:28:47
	 */
	public String getCityCode() {
		return cityCode;
	}

	/* 350859 liulu
	 * @return the cityCode
	 * @2017-2-28 上午10:28:47
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/* 350859 liulu
	 * @return the districtCode
	 * @2017-2-28 上午10:28:47
	 */
	public String getDistrictCode() {
		return districtCode;
	}

	/* 350859 liulu
	 * @return the districtCode
	 * @2017-2-28 上午10:28:47
	 */
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	/* 350859 liulu
	 * @return the townshipCode
	 * @2017-2-28 上午10:28:47
	 */
	public String getTownshipCode() {
		return townshipCode;
	}

	/* 350859 liulu
	 * @return the townshipCode
	 * @2017-2-28 上午10:28:47
	 */
	public void setTownshipCode(String townshipCode) {
		this.townshipCode = townshipCode;
	}

	/* 350859 liulu
	 * @return the streetCode
	 * @2017-2-28 上午10:28:47
	 */
	public String getStreetCode() {
		return streetCode;
	}

	/* 350859 liulu
	 * @return the streetCode
	 * @2017-2-28 上午10:28:47
	 */
	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
	}

	/* 350859 liulu
	 * @return the contactAddress
	 * @2017-2-28 上午10:28:47
	 */
	public String getContactAddress() {
		return contactAddress;
	}

	/* 350859 liulu
	 * @return the contactAddress
	 * @2017-2-28 上午10:28:47
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

}

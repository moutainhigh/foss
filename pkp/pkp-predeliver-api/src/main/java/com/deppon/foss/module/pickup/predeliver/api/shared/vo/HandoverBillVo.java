package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: HandoverBillVo 
 * @Description: 已交单Vo
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-29 下午3:28:25 
 *  
 */
public class HandoverBillVo implements Serializable{

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 交单Id
	 */
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilePhone;
	
	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;
	
	/**
	 * 收货具体地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 * 收货地址备注
	 */
	private String receiveCustomerAddressNote;
	
	/**
	 * 经度
	 */
	private String longitude;
	
	/**
	 * 纬度
	 */
	private String latitude;
	
	/**
	 * 预计送货日期
	 */
	private Date preDeliverDate;
	
	/**
	 * 收货省份名称
	 */
	private String receiveCustomerProvName;
	
	/**
	 * 收货城市名称
	 */
	private String receiveCustomerCityName;
	
	/**
	 * 收货区名称
	 */
	private String receiveCustomerDistName;

	/**
	 * 获取id
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取waybillNo
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置waybillNo
	 * @param waybillNo 要设置的waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取receiveCustomerMobilePhone
	 * @return the receiveCustomerMobilePhone
	 */
	public String getReceiveCustomerMobilePhone() {
		return receiveCustomerMobilePhone;
	}

	/**
	 * 设置receiveCustomerMobilePhone
	 * @param receiveCustomerMobilePhone 要设置的receiveCustomerMobilePhone
	 */
	public void setReceiveCustomerMobilePhone(String receiveCustomerMobilePhone) {
		this.receiveCustomerMobilePhone = receiveCustomerMobilePhone;
	}

	/**
	 * 获取receiveCustomerPhone
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * 设置receiveCustomerPhone
	 * @param receiveCustomerPhone 要设置的receiveCustomerPhone
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * 获取receiveCustomerAddress
	 * @return the receiveCustomerAddress
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * 设置receiveCustomerAddress
	 * @param receiveCustomerAddress 要设置的receiveCustomerAddress
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * 获取longitude
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * 设置longitude
	 * @param longitude 要设置的longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 获取latitude
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * 设置latitude
	 * @param latitude 要设置的latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * 获取preDeliverDate
	 * @return the preDeliverDate
	 */
	public Date getPreDeliverDate() {
		return preDeliverDate;
	}

	/**
	 * 设置preDeliverDate
	 * @param preDeliverDate 要设置的preDeliverDate
	 */
	public void setPreDeliverDate(Date preDeliverDate) {
		this.preDeliverDate = preDeliverDate;
	}

	/**
	 * 获取receiveCustomerProvName
	 * @return the receiveCustomerProvName
	 */
	public String getReceiveCustomerProvName() {
		return receiveCustomerProvName;
	}

	/**
	 * 获取receiveCustomerAddressNote
	 * @return the receiveCustomerAddressNote
	 */
	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	/**
	 * 设置receiveCustomerProvName
	 * @param receiveCustomerProvName 要设置的receiveCustomerProvName
	 */
	public void setReceiveCustomerProvName(String receiveCustomerProvName) {
		this.receiveCustomerProvName = receiveCustomerProvName;
	}

	/**
	 * 获取receiveCustomerCityName
	 * @return the receiveCustomerCityName
	 */
	public String getReceiveCustomerCityName() {
		return receiveCustomerCityName;
	}

	/**
	 * 设置receiveCustomerCityName
	 * @param receiveCustomerCityName 要设置的receiveCustomerCityName
	 */
	public void setReceiveCustomerCityName(String receiveCustomerCityName) {
		this.receiveCustomerCityName = receiveCustomerCityName;
	}

	/**
	 * 获取receiveCustomerDistName
	 * @return the receiveCustomerDistName
	 */
	public String getReceiveCustomerDistName() {
		return receiveCustomerDistName;
	}

	/**
	 * 设置receiveCustomerDistName
	 * @param receiveCustomerDistName 要设置的receiveCustomerDistName
	 */
	public void setReceiveCustomerDistName(String receiveCustomerDistName) {
		this.receiveCustomerDistName = receiveCustomerDistName;
	}

	/**
	 * 设置receiveCustomerAddressNote
	 * @param receiveCustomerAddressNote 要设置的receiveCustomerAddressNote
	 */
	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

}

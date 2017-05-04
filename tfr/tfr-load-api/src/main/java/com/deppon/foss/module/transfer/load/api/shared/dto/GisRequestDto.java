/**  
 * Project Name:tfr-load-api  
 * File Name:GisRequestDto.java  
 * Package Name:com.deppon.foss.module.transfer.load.api.shared.dto  
 * Date:2015年6月13日下午2:17:40  
 *  
 */
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;

/**  
 * ClassName: GisRequestDto <br/>  
 * Function: 调用GIS服务时发送的对象. <br/>  
 * date: 2015年6月13日 下午2:17:40 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class GisRequestDto implements Serializable {
	
	/**  
	 * serialVersionUID
	 * @since JDK 1.6  
	 */
	private static final long serialVersionUID = 1646802234430789L;

	/**
	 * 省份编码
	 */
	private String provinceCode;
	
	/**
	 * 省份名称
	 */
	private String provinceName;
	
	/**
	 * 城市编码
	 */
	private String cityCode;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 区县编码
	 */
	private String countyCode;
	
	/**
	 * 区县名称
	 */
	private String countyName;
	
	/**
	 * 镇编码
	 */
	private String townCode;
	
	/**
	 * 镇名称
	 */
	private String townName;
	
	/**
	 * 详细地址
	 */
	private String address;
	
	/**
	 * 运单号
	 */
	private String waybillNum;
	
	/**
	 * 运单ID
	 */
    private String waybillID;

	/**  
	 * provinceCode.  
	 *  
	 * @return  the provinceCode  
	 * @since   JDK 1.6  
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**  
	 * provinceCode.  
	 *  
	 * @param   provinceCode    the provinceCode to set  
	 * @since   JDK 1.6  
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**  
	 * provinceName.  
	 *  
	 * @return  the provinceName  
	 * @since   JDK 1.6  
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**  
	 * provinceName.  
	 *  
	 * @param   provinceName    the provinceName to set  
	 * @since   JDK 1.6  
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**  
	 * cityCode.  
	 *  
	 * @return  the cityCode  
	 * @since   JDK 1.6  
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**  
	 * cityCode.  
	 *  
	 * @param   cityCode    the cityCode to set  
	 * @since   JDK 1.6  
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**  
	 * cityName.  
	 *  
	 * @return  the cityName  
	 * @since   JDK 1.6  
	 */
	public String getCityName() {
		return cityName;
	}

	/**  
	 * cityName.  
	 *  
	 * @param   cityName    the cityName to set  
	 * @since   JDK 1.6  
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**  
	 * countyCode.  
	 *  
	 * @return  the countyCode  
	 * @since   JDK 1.6  
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**  
	 * countyCode.  
	 *  
	 * @param   countyCode    the countyCode to set  
	 * @since   JDK 1.6  
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**  
	 * countyName.  
	 *  
	 * @return  the countyName  
	 * @since   JDK 1.6  
	 */
	public String getCountyName() {
		return countyName;
	}

	/**  
	 * countyName.  
	 *  
	 * @param   countyName    the countyName to set  
	 * @since   JDK 1.6  
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**  
	 * townCode.  
	 *  
	 * @return  the townCode  
	 * @since   JDK 1.6  
	 */
	public String getTownCode() {
		return townCode;
	}

	/**  
	 * townCode.  
	 *  
	 * @param   townCode    the townCode to set  
	 * @since   JDK 1.6  
	 */
	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}

	/**  
	 * townName.  
	 *  
	 * @return  the townName  
	 * @since   JDK 1.6  
	 */
	public String getTownName() {
		return townName;
	}

	/**  
	 * townName.  
	 *  
	 * @param   townName    the townName to set  
	 * @since   JDK 1.6  
	 */
	public void setTownName(String townName) {
		this.townName = townName;
	}

	/**  
	 * address.  
	 *  
	 * @return  the address  
	 * @since   JDK 1.6  
	 */
	public String getAddress() {
		return address;
	}

	/**  
	 * address.  
	 *  
	 * @param   address    the address to set  
	 * @since   JDK 1.6  
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**  
	 * waybillNum.  
	 *  
	 * @return  the waybillNum  
	 * @since   JDK 1.6  
	 */
	public String getWaybillNum() {
		return waybillNum;
	}

	/**  
	 * waybillNum.  
	 *  
	 * @param   waybillNum    the waybillNum to set  
	 * @since   JDK 1.6  
	 */
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	/**  
	 * waybillID.  
	 *  
	 * @return  the waybillID  
	 * @since   JDK 1.6  
	 */
	public String getWaybillID() {
		return waybillID;
	}

	/**  
	 * waybillID.  
	 *  
	 * @param   waybillID    the waybillID to set  
	 * @since   JDK 1.6  
	 */
	public void setWaybillID(String waybillID) {
		this.waybillID = waybillID;
	}
    
}

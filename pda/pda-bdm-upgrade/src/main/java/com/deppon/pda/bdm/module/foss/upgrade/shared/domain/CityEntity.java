package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(城市实体类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-4-10 下午2:27:58,content:TODO </p>
 * @author chengang
 * @date 2013-4-10 下午2:27:58
 * @since
 * @version
 */
public class CityEntity extends BaseEntity {
	/**
	 * TODO（UID）
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 省名称
	 */
	private String province;
	/**
	 * 市名称
	 */
	private String city;
	/**
	 * 区号
	 */
	private String areaCode;
	/**
	 * 邮编
	 */
	private String zipCode;
	/**
	 * 英文名
	 */
	private String cityEng;
	/**
	 * 市简称
	 */
	private String cityAbbr;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否生效
	 */
	private String isActive;
	/**
	 * 操作标记
	 */
	private String operFlag;
	/**
	 * 上一次更新时间
	 */
	private String updTime;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 生效时间
	 */
	private String activeTime;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCityEng() {
		return cityEng;
	}

	public void setCityEng(String cityEng) {
		this.cityEng = cityEng;
	}

	public String getCityAbbr() {
		return cityAbbr;
	}

	public void setCityAbbr(String cityAbbr) {
		this.cityAbbr = cityAbbr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

}

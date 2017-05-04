package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;



/**
 * 省 实体类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-09-23
 */
public class ProvinceEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 省名称
	 */
	private String province;

	/**
	 * 英文名
	 */
	private String provinceEng;

	/**
	 * 省简称
	 */
	private String provinceAbbr;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 *是否激活
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

	public String getProvinceEng() {
		return provinceEng;
	}

	public void setProvinceEng(String provinceEng) {
		this.provinceEng = provinceEng;
	}

	public String getProvinceAbbr() {
		return provinceAbbr;
	}

	public void setProvinceAbbr(String provinceAbbr) {
		this.provinceAbbr = provinceAbbr;
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

package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递代理网店和公司快递点部
 * 
 * @author WangPeng
 * @date   
 *
 */
public class LdpOuterBranchsAndOrginfoEntity extends BaseEntity {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8590145809558277892L;
	
	/**
	 * 网点名称
	 */
	private String deptName;
	
	/**
	 * 网点编码
	 */
	private String  deptCode;
	
	/**
	 *  网点类型
	 */
	
	private String[] type;

	/**
	 *  网点类型名称
	 */
	private String typeName;
	
	/**
	 *  省份编码
	 */
	private String proCode;
	
	/**
	 *  省份名称
	 */
	private String proName;
	
	/**
	 *  城市编码
	 */
	private String cityCode;
	
	/**
	 *  城市名称
	 */
	private String cityName;
	
	/**
	 *  区/县名称
	 */
	private String countyName;
	
	/**
	 *  区/县编码
	 */
	private String countyCode;
	
	/**
	 *  是否有效
	 */
	private String active;
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * @return the countyCode
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * @param countyCode the countyCode to set
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}

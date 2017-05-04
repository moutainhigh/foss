package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 同步快递派送地址库信息到GIS的返回实体
 * @author 198771
 *
 */
public class SyncEDARequestEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1145449489764L;
	/**
	 * FOSS数据库ID
	 */
	private String fossid;
	/**
	 * 营业部编码
	 */
	private String dept_no;
	/**
	 * 省区域编码
	 */
	private String province_code;
	/**
	 * 省区域名称
	 */
	private String province;
	/**
	 * 市区域编码
	 */
	private String city_code;
	/**
	 * 市区域名称
	 */
	private String city;
	/**
	 * 区县区域编码
	 */
	private String county_code;
	/**
	 * 区县区域名称
	 */
	private String county;
	/**
	 * 镇/街区域编码
	 */
	private String town_code;
	/**
	 * 镇/街区域名称
	 */
	private String town;
	/**
	 * 路/街
	 */
	private String street;
	/**
	 * 起始门牌号
	 */
	private String start_house_no;
	/**
	 * 终止门牌号
	 */
	private String end_house_no;
	/**
	 * 生效时间
	 */
	private Date start_time;
	/**
	 * 失效时间
	 */
	private Date end_time;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 地标
	 */
	private String sign;

	/**
	 * 是否有效
	 */
	private String active;
	
   /**
    * 部门名称
    */
	private String deptName;

	public String getFossid() {
		return fossid;
	}

	public void setFossid(String fossid) {
		this.fossid = fossid;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty_code() {
		return county_code;
	}

	public void setCounty_code(String county_code) {
		this.county_code = county_code;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getTown_code() {
		return town_code;
	}

	public void setTown_code(String town_code) {
		this.town_code = town_code;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}


	public String getStart_house_no() {
		return start_house_no;
	}

	public void setStart_house_no(String start_house_no) {
		this.start_house_no = start_house_no;
	}

	public String getEnd_house_no() {
		return end_house_no;
	}

	public void setEnd_house_no(String end_house_no) {
		this.end_house_no = end_house_no;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}

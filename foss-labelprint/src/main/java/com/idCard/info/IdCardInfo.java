package com.idCard.info;


/**
 * 身份证信息
 * @author 038590-foss-wanghui
 * @date 2013-6-28 下午5:17:08
 */
public class IdCardInfo {
	// 姓名
	private String name;
	// 性别
	private String sex;
	// 民族
	private String nation;
	// 出生年月
	private String birthday;
	// 身份证号码
	private String idNo;
	// 地址
	private String address;
	// 签发机关
	private String issuingAuthority;
	// 有效起始日期
	private String beginTime;
	// 有效截至日期
	private String endTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIssuingAuthority() {
		return issuingAuthority;
	}

	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}

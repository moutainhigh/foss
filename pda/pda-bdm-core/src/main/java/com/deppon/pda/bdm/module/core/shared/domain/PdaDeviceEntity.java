package com.deppon.pda.bdm.module.core.shared.domain;

import java.util.Date;

/**
 * 
  * @ClassName PdaDeviceEntity 
  * @Description PDA设备实体类 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class PdaDeviceEntity extends DomainEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5138372410905466487L;
	/**
	 * 设备号
	 */
	private String dvcCode;
	/**
	 * 固定资产编号
	 */
	private String sernbr;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * SIM卡号
	 */
	private String simcardCode;
	/**
	 * 设备型号
	 */
	private String model;
	/**
	 * 所属公司
	 */
	private String companyCode;
	/**
	 * 购买日期
	 */
	private Date purDate;
	/**
	 * 最后更新者工号
	 */
	private String lastUpdaterCode;
	/**
	 * 最后更新者姓名
	 */
	private String lastUpdaterNm;
	/**
	 * 最后更新者日期
	 */
	private Date lastUpddate;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 手机号
	 */
	private String mobilePhone;
	public String getDvcCode() {
		return dvcCode;
	}
	public void setDvcCode(String dvcCode) {
		this.dvcCode = dvcCode;
	}
	public String getSernbr() {
		return sernbr;
	}
	public void setSernbr(String sernbr) {
		this.sernbr = sernbr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSimcardCode() {
		return simcardCode;
	}
	public void setSimcardCode(String simcardCode) {
		this.simcardCode = simcardCode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Date getPurDate() {
		return purDate;
	}
	public void setPurDate(Date purDate) {
		this.purDate = purDate;
	}
	public String getLastUpdaterCode() {
		return lastUpdaterCode;
	}
	public void setLastUpdaterCode(String lastUpdaterCode) {
		this.lastUpdaterCode = lastUpdaterCode;
	}
	public String getLastUpdaterNm() {
		return lastUpdaterNm;
	}
	public void setLastUpdaterNm(String lastUpdaterNm) {
		this.lastUpdaterNm = lastUpdaterNm;
	}
	public Date getLastUpddate() {
		return lastUpddate;
	}
	public void setLastUpddate(Date lastUpddate) {
		this.lastUpddate = lastUpddate;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	
}
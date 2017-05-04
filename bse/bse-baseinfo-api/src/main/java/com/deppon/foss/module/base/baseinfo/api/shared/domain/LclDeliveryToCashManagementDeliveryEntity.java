package com.deppon.foss.module.base.baseinfo.api.shared.domain;



import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class LclDeliveryToCashManagementDeliveryEntity  extends BaseEntity{
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 3724625597925406775L;
	/**
     * 派送部门编码
     */
	 private String orgCode;
	 /**
	  * 派送部门名称
	  */
    private String orgName;
    /**
     * 卸出开始时间
     */
    private Date  startDate;
   /**
    * 卸出结束时间 
    */
    private Date endDate;
    /**
     * 规定兑现时间时分
     */
    private String deliverOnTime;
    /**
     * 规定兑现时间天
     */
    private String deliverOnDay;
    /**
     * 是否启用
     */
    private String active;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 创建人名称
     */
    private String createUser;
    /**
     * 修改人名称
     */
    private String modifyUser;
    /**
     * 创建人工号
     */
    private String createUserCode;
    /**
     * 修改人工号
     */
    private String modifyUserCode;
 
    /**
     * 备注
     */
    private String reMark;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDeliverOnTime() {
		return deliverOnTime;
	}
	public void setDeliverOnTime(String deliverOnTime) {
		this.deliverOnTime = deliverOnTime;
	}
	public String getDeliverOnDay() {
		return deliverOnDay;
	}
	public void setDeliverOnDay(String deliverOnDay) {
		this.deliverOnDay = deliverOnDay;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getReMark() {
		return reMark;
	}
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	
}

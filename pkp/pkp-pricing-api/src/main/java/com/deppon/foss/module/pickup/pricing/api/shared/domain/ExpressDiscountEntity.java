package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * Copyright (C) 2015 Asiainfo-Linkage
 * 
 * 
 * @className:com.deppon.foss.module.pickup.pricing.api.shared.domain.ExpressDiscountEntity
 * @description:快递折扣方案实体类
 * 
 * @version:v1.0.0
 * @author:DP-FOSS-YANGKANG
 * 
 * Modification History: Date Author Version
 * Description
 * ------------------------------------------
 * ----------------------- 2015-1-8 DP-FOSS-YANGKANG
 * v1.0.0 create
 * 
 * 
 */
public class ExpressDiscountEntity extends BaseEntity {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	// 方案名称
	private String name;
	// 方案类型
	private String planType;
	// 渠道来源(一个方案可以对应多个渠道)
	private List<String> channelCodes;
	// 开始时间
	private Date beginTime;
	// 截止时间
	private Date endTime;
	// 方案状态
	private String active;
	// 方案备注
	private String remark;
	// 方案版本
	private Long versionNo;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date modifyTime;
	// 创建人
	private String createUserCode;
	//创建人姓名
	private String createUserName;
	// 修改人
	private String modifyUserCode;
	//修改人姓名
	private String modifyUserName;
	// 创建部门
	private String createOrgCode;
	// 修改部门
	private String modifyOrgCode;
	// 客户名称
	private String customerName;
	// 客户编码
	private String customerCode;
	//业务日期
	private Date billTime;
    //是否当前版本
	private String currentUsedVersion;
	//周特惠编码
	private String weekCodes;
	//周特惠名称
	private String weekNames;
	
	public String getWeekCodes() {
		return weekCodes;
	}

	public void setWeekCodes(String weekCodes) {
		this.weekCodes = weekCodes;
	}

	public String getWeekNames() {
		return weekNames;
	}

	public void setWeekNames(String weekNames) {
		this.weekNames = weekNames;
	}
	public String getCurrentUsedVersion() {
		return currentUsedVersion;
	}

	public void setCurrentUsedVersion(String currentUsedVersion) {
		this.currentUsedVersion = currentUsedVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}


	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public List<String> getChannelCodes() {
		return channelCodes;
	}

	public void setChannelCodes(List<String> channelCodes) {
		this.channelCodes = channelCodes;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}

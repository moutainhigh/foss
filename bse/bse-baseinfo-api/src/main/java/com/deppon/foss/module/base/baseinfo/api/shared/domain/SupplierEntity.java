package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;


/**
 * 供应商entity
 * @author 232607 
 * @date 2015-12-21 下午6:48:28
 * @since
 * @version
 */
public class SupplierEntity {
	/**
	 * 供应商id
	 */
	private String Id;
	/**
	 * 供应商code
	 */
	private String code;
	/**
	 * 供应商全称
	 */
	private String name;
	/**
	 * 供应商简称
	 */
	private String simpleName;
	/**
	 * 联系人
	 */
	private String contactPerson;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 联系地址
	 */
	private String contactAddress;
	/**
	 * 家具
	 */
	private String furnitureFlag;
	/**
	 * 家电
	 */
	private String householdFlag;
	/**
	 * 建材
	 */
	private String constructionFlag;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 禁用时间、启动时间
	 */
	private Date activeTime;
	/**
	 * 是否启用（DOP推送）
	 */
	private String isUsing;
	/**
	 * 是否有效
	 */
	private String active;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getFurnitureFlag() {
		return furnitureFlag;
	}
	public void setFurnitureFlag(String furnitureFlag) {
		this.furnitureFlag = furnitureFlag;
	}
	public String getHouseholdFlag() {
		return householdFlag;
	}
	public void setHouseholdFlag(String householdFlag) {
		this.householdFlag = householdFlag;
	}
	public String getConstructionFlag() {
		return constructionFlag;
	}
	public void setConstructionFlag(String constructionFlag) {
		this.constructionFlag = constructionFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public String getIsUsing() {
		return isUsing;
	}
	public void setIsUsing(String isUsing) {
		this.isUsing = isUsing;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
	
}



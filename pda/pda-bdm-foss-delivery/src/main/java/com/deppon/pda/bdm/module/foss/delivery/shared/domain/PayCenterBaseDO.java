package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 收付款模块实体基体
 * 
 */
public  class PayCenterBaseDO implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1372509362360011358L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 创建日期
	 */
	private Date createDate;
	
	/**
	 * 大区编码
	 */
	private String bigRegionCode;

	/**
	 * 大区名称
	 */
    private String bigRegionName;

    /**
     * 小区编码
     */
    private String smallRegionCode;
    
    /**
     * 小区名称
     */
    private String smallRegionName;
    
	/**	
	 * 创建人名称
	 */
	private String createUserName;

	/**
	 * 创建人编码
	 */
	private String createUserCode;

	/**
	 * 修改时间
	 */
	private Date modifyDate;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 版本号
	 */
	private Long versionNo;

	/**
	 * 扩展字段
	 */
	private String attribute;

	/**
	 * option
	 */
	private Long option;

	/**
	 * 是否有效
	 */
	private String isActive;
	
	/**
	 * 系统操作人名称
	 */
	private String sysOperatorName;
	
	/**
	 * 系统操作人编码
	 */
	private String sysOperatorCode;
	
	/**
	 * 系统操作备注
	 */
	private String sysRemark;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 是否错误
	 */
	private String isError;
	/********getter/setter**************/
	
	
	public String getSysOperatorName() {
		return sysOperatorName;
	}
	
	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSysOperatorName(String sysOperatorName) {
		this.sysOperatorName = sysOperatorName;
	}

	public String getSysOperatorCode() {
		return sysOperatorCode;
	}

	public void setSysOperatorCode(String sysOperatorCode) {
		this.sysOperatorCode = sysOperatorCode;
	}

	public String getSysRemark() {
		return sysRemark;
	}

	public void setSysRemark(String sysRemark) {
		this.sysRemark = sysRemark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Long getOption() {
		return option;
	}

	public void setOption(Long option) {
		this.option = option;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getBigRegionCode() {
		return bigRegionCode;
	}

	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	public String getSmallRegionCode() {
		return smallRegionCode;
	}

	public void setSmallRegionCode(String smallRegionCode) {
		this.smallRegionCode = smallRegionCode;
	}

	public String getSmallRegionName() {
		return smallRegionName;
	}

	public void setSmallRegionName(String smallRegionName) {
		this.smallRegionName = smallRegionName;
	}
	
}

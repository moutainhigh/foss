package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * TODO(基础数据-星号部门实体类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-5-17 下午4:01:26,content:TODO </p>
 * @author chengang
 * @date 2013-5-17 下午4:01:26
 * @since
 * @version
 */
public class SalesDeptAsteriskEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 星号部门ID
	 */
	private String salesDeptId;
	/**
	 * 星号部门名称
	 */
	private String salesDeptName;
	/**
	 * 星号部门Code
	 */
	private String asterisk_code;
	
	/**
	 * 操作标记
	 */
	private String operFlag;
	/**
	 * 上一次更新时间
	 */
	private Date updTime;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 是否生效
	 */
	private String isActive;
	/**
	 * 生效时间
	 */
	private Date activeTime;
	public String getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(String salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
	public String getSalesDeptName() {
		return salesDeptName;
	}
	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}
	public String getAsterisk_code() {
		return asterisk_code;
	}
	public void setAsterisk_code(String asterisk_code) {
		this.asterisk_code = asterisk_code;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	
	
}

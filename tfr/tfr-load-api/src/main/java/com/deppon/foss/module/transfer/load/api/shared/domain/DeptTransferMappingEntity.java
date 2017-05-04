package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
* @description 合伙人网点映射实体类
* @version 1.0
* @author 360903  linhua.yan-tfr
* @update 2016年9月18日
*/
public class DeptTransferMappingEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 营业部名称
	 */
	private String deptName;
	
	/**
	 * 营业部编码
	 */
	private String deptCode;
	/**
	 * 一级合伙人网点编码
	 */
	private String fthNetworkCode;
	/**
	 * 一级合伙人网点名称
	 */
	private String fthNetworkName;
	/**
	 *二级合伙人网点编码
	 */
	private String secNetworkCode;
	/**
	 * 二级合伙人网点名称
	 */
	private String secNetworkName;
	/**
	 * 是否外场
	 */
	private String isOutField;
	/**
	 * 创建人编码
	 */
	private String createUserCode;
	/**
	 * 创建人名称
	 */
	private String createUserName;
	/**
	 * 修改人名称
	 */
	private String updateUserName;
	/**
	 * 修改人编码
	 */
	private String updateUserCode;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 是否启用
	 */
	private String active;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getFthNetworkCode() {
		return fthNetworkCode;
	}
	public void setFthNetworkCode(String fthNetworkCode) {
		this.fthNetworkCode = fthNetworkCode;
	}
	public String getFthNetworkName() {
		return fthNetworkName;
	}
	public void setFthNetworkName(String fthNetworkName) {
		this.fthNetworkName = fthNetworkName;
	}
	public String getSecNetworkCode() {
		return secNetworkCode;
	}
	public void setSecNetworkCode(String secNetworkCode) {
		this.secNetworkCode = secNetworkCode;
	}
	public String getSecNetworkName() {
		return secNetworkName;
	}
	public void setSecNetworkName(String secNetworkName) {
		this.secNetworkName = secNetworkName;
	}
	public String getIsOutField() {
		return isOutField;
	}
	public void setIsOutField(String isOutField) {
		this.isOutField = isOutField;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public String getUpdateUserCode() {
		return updateUserCode;
	}
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
}

package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;


/**
 * 营业部交接应设管理dto
 * @author 273296
 *
 */
public class DeptTransferMappingDto {
	
	private String id;        //主键Id
	private String deptCode;  //营业部code
	private String deptName;  //营业部名称
	private String fthNetworkCode; //一级营业部网点code
	private String fthNetworkName; //一级营业部网点名称
	private String secNetworkCode; //二级营业部网点code
	private String secNetworkName; //二级营业部网点名称
	private String isOutfield;    // 是否外场
	private String createUserName;	//创建人姓名
	private String createUserCode;	//创建人编码
	private String updateUserName;  //修改人姓名
	private String updateUserCode;  //修改人编码
	private String createTime;     //创建时间
	private String modifyTime;		//修改时间
	private String active;       //是否禁用
	
	private Boolean isExists; //是否存在就
	
	private List<String> idList;
	
	
	private List<DeptTransferMappingEntity> deptTransferMappingList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	public String getIsOutfield() {
		return isOutfield;
	}
	public void setIsOutfield(String isOutfield) {
		this.isOutfield = isOutfield;
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
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	public List<DeptTransferMappingEntity> getDeptTransferMappingList() {
		return deptTransferMappingList;
	}
	public void setDeptTransferMappingList(
			List<DeptTransferMappingEntity> deptTransferMappingList) {
		this.deptTransferMappingList = deptTransferMappingList;
	}
	public Boolean getIsExists() {
		return isExists;
	}
	public void setIsExists(Boolean isExists) {
		this.isExists = isExists;
	}
	
	

}

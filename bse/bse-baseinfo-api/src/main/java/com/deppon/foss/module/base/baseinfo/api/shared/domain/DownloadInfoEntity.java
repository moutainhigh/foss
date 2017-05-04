package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class DownloadInfoEntity extends BaseEntity{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3535491275634616362L;

	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 登录人的工号
	 */
	private String empCode;
	
	/**
	 * 登录人所属部门
	 */
	private String orgCode;
	
	/**
	 * 下载的文件名称
	 */
	private String fileName;
	
	/**
	 * 文件下载路径
	 */
	private String fileLoadPath;
	
	/**
	 * 文件存放的物理路径
	 */
	private String fileOuterLoadPath;
	
	/**
	 * 记录是否有效
	 */
	private String active;
	
	/**
	 * 创建人
	 */
	private String creator;
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 修改人
	 */
	private String modifyUser;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 文件保存在本地的路径
	 */
    private String saveLocalPath;
	
	public String getSaveLocalPath() {
		return saveLocalPath;
	}

	public void setSaveLocalPath(String saveLocalPath) {
		this.saveLocalPath = saveLocalPath;
	}

	/**
	 * @return:String
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return:String
	 */
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return:String
	 */
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return:String
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return:String
	 */
	public String getFileLoadPath() {
		return fileLoadPath;
	}

	public void setFileLoadPath(String fileLoadPath) {
		this.fileLoadPath = fileLoadPath;
	}

	/**
	 * @return:String
	 */
	public String getFileOuterLoadPath() {
		return fileOuterLoadPath;
	}

	public void setFileOuterLoadPath(String fileOuterLoadPath) {
		this.fileOuterLoadPath = fileOuterLoadPath;
	}

	/**
	 * @return:String
	 */
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return:String
	 */
	public String getModifyUser() {
		return modifyUser;
	}

	/**
	 * @param:modifyUser
	 */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * @return:Date
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param:createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return:Date
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param:modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}

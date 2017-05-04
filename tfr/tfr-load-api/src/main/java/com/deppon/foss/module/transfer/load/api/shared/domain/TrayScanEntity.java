package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 *托盘任务基本信息实体
 * @author 105869-foss-heyongdong
 * @date 2013-07-25 
 */
public class TrayScanEntity extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1330593526399233954L;
	
	/**
	 * id
	 * **/
	private String id;	
	/**
	 * 绑定托盘的任务号
	 * */
	private String taskNo;
	
	/**
	 * 叉车扫描时间
	 * */
	private Date  trayscanTime;
	
	/**
	 * 叉车司机工号
	 * */
	private String forkliftDriverCode;
	
	
	/**
	 * 叉车司机姓名
	 * */
	private String forkliftDriverName;
	
	/**
	 * 叉车司机所在部门
	 * */
	private String forkliftDepartment;
	
	/**
	 * 叉车司机所在部门code
	 * */
	private String forkliftDepartmentCode;
	
	
	/**
	 * 绑定人工号
	 * */
	private String bindingCode;
	
	/**
	 * 绑定人姓名
	 * */
	private String bindingName;
	
	/**
	 * 绑定人部门
	 * */
	private String bindingDept;
	
	/**
	 * 绑定人部门code
	 * */
	private String bindingDeptCode;
	
	/**
	 * 
	 * 托盘绑定任务时间
	 * 
	 * */
	private Date  traytaskCreatTime;
	
	/**
	 * 
	 * 任务类型
	 * 
	 * */
	private String trayscanType;
	
	/**
	 * 
	 * 任务状态
	 * 
	 * */
	private String statu;
	/**
	 * 
	 * 所属外场
	 * 
	 * **/
	private String outfieldCode;
	
	/**
	 * 
	 * 是否生成叉车工作量
	 * 
	 * */
	private String beCreateWork;
	
	
	/**
	 * 
	 *  卸车任务编号
	 * 
	 * */
    private String unloadTaskNo;
    
	
	public String getBeCreateWork() {
		return beCreateWork;
	}

	public void setBeCreateWork(String beCreateWork) {
		this.beCreateWork = beCreateWork;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOutfieldCode() {
		return outfieldCode;
	}

	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getForkliftDepartmentCode() {
		return forkliftDepartmentCode;
	}

	public void setForkliftDepartmentCode(String forkliftDepartmentCode) {
		this.forkliftDepartmentCode = forkliftDepartmentCode;
	}

	public String getBindingDeptCode() {
		return bindingDeptCode;
	}

	public void setBindingDeptCode(String bindingDeptCode) {
		this.bindingDeptCode = bindingDeptCode;
	}

	

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public Date getTrayscanTime() {
		return trayscanTime;
	}

	public void setTrayscanTime(Date trayscanTime) {
		this.trayscanTime = trayscanTime;
	}

	public String getForkliftDriverCode() {
		return forkliftDriverCode;
	}

	public void setForkliftDriverCode(String forkliftDriverCode) {
		this.forkliftDriverCode = forkliftDriverCode;
	}

	public String getForkliftDriverName() {
		return forkliftDriverName;
	}

	public void setForkliftDriverName(String forkliftDriverName) {
		this.forkliftDriverName = forkliftDriverName;
	}

	public String getForkliftDepartment() {
		return forkliftDepartment;
	}

	public void setForkliftDepartment(String forkliftDepartment) {
		this.forkliftDepartment = forkliftDepartment;
	}

	public String getBindingCode() {
		return bindingCode;
	}

	public void setBindingCode(String bindingCode) {
		this.bindingCode = bindingCode;
	}

	public String getBindingName() {
		return bindingName;
	}

	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}

	public String getBindingDept() {
		return bindingDept;
	}

	public void setBindingDept(String bindingDept) {
		this.bindingDept = bindingDept;
	}

	public Date getTraytaskCreatTime() {
		return traytaskCreatTime;
	}

	public void setTraytaskCreatTime(Date traytaskCreatTime) {
		this.traytaskCreatTime = traytaskCreatTime;
	}

	public String getTrayscanType() {
		return trayscanType;
	}

	public void setTrayscanType(String trayscanType) {
		this.trayscanType = trayscanType;
	}

	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}


	
	
}

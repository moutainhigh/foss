package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkFlowEntity;

public class DeptTempArrearsWorkFlowDto extends WorkFlowEntity implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5277025962785786155L;
	
	
	//当前部门的最大临时欠款额度
	private BigDecimal currentTempArrears;
	
	//新增加的临时欠款额度
	private BigDecimal addNewTempArrears;
	
	//增加后总共的临时欠款额度
	private BigDecimal totalTempArrears;
	
    //页面状态
    private String pageStatus;
    
    //申请类型
    private String applyType;
    
    //申请人岗位
    private String title;
    
    //申请人 申请临时欠款额度部门
    private String applyTempDept;
    //申请人申请临时欠款额度部门名称
    private String applyTempDeptName;

	public BigDecimal getAddNewTempArrears() {
		return addNewTempArrears;
	}

	public void setAddNewTempArrears(BigDecimal addNewTempArrears) {
		this.addNewTempArrears = addNewTempArrears;
	}

	public BigDecimal getTotalTempArrears() {
		return totalTempArrears;
	}

	public void setTotalTempArrears(BigDecimal totalTempArrears) {
		this.totalTempArrears = totalTempArrears;
	}

	public BigDecimal getCurrentTempArrears() {
		return currentTempArrears;
	}

	public void setCurrentTempArrears(BigDecimal currentTempArrears) {
		this.currentTempArrears = currentTempArrears;
	}

	public String getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getApplyTempDept() {
		return applyTempDept;
	}

	public void setApplyTempDept(String applyTempDept) {
		this.applyTempDept = applyTempDept;
	}

	public String getApplyTempDeptName() {
		return applyTempDeptName;
	}

	public void setApplyTempDeptName(String applyTempDeptName) {
		this.applyTempDeptName = applyTempDeptName;
	}
	
}

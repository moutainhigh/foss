package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 营业部与外请车费用承担部门映射信息
 * @author 307196
 *
 */
public class SalesExpenseMappingQueryDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6878655640828967244L;
	 /**
     * 营业部编码
     */
    private String businessDepartmentCode;
    /**
     * 营业部名称
     */
    private String businessDepartmentName;
    /**
     * 预算承担部门编码
     */
    private String budgetDepartmentCode;
    /**
     * 预算承担部门名称
     */
    private String budgetDepartmentName;
	/**
	 * 选择ID集合List
	 */
	private List<String> selectedIdList;
	/**
	 * 已选ID集合
	 */
	private String selectedIds;
	private String active;
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	public List<String> getSelectedIdList() {
		return selectedIdList;
	}
	public void setSelectedIdList(List<String> selectedIdList) {
		this.selectedIdList = selectedIdList;
	}
	public String getBusinessDepartmentCode() {
		return businessDepartmentCode;
	}
	public void setBusinessDepartmentCode(String businessDepartmentCode) {
		this.businessDepartmentCode = businessDepartmentCode;
	}
	public String getBusinessDepartmentName() {
		return businessDepartmentName;
	}
	public void setBusinessDepartmentName(String businessDepartmentName) {
		this.businessDepartmentName = businessDepartmentName;
	}
	public String getBudgetDepartmentCode() {
		return budgetDepartmentCode;
	}
	public void setBudgetDepartmentCode(String budgetDepartmentCode) {
		this.budgetDepartmentCode = budgetDepartmentCode;
	}
	public String getBudgetDepartmentName() {
		return budgetDepartmentName;
	}
	public void setBudgetDepartmentName(String budgetDepartmentName) {
		this.budgetDepartmentName = budgetDepartmentName;
	}
	
}

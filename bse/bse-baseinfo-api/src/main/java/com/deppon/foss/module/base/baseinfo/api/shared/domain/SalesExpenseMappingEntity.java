package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 营业部与外请车费用承担部门映射信息
 * @author 307196
 *
 */
public class SalesExpenseMappingEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
    private String id;
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
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建人名称
     */
    private String createUserName;
    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 修改人名称
     */
    private String modifyUserName;

    /**
     * 是否有效
     */
    private String active;
    

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	

	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getCreateUserName() {
		return createUserName;
	}


	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}


	public String getModifyUser() {
		return modifyUser;
	}


	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	
	public String getModifyUserName() {
		return modifyUserName;
	}


	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}
   
    
    
}
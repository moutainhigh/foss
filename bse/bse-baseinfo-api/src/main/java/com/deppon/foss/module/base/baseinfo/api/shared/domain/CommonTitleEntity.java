package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(职位公共选择对应的Entity实体)
 * @author 187862-dujunhui
 * @date 2014-8-7 下午6:13:41
 * @since
 * @version
 */
public class CommonTitleEntity extends BaseEntity {

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 3294375553459355448L;
	
	/**
	 * 员工工号
	 */
	private String empCode;
	/**
	 * 员工姓名
	 */
	private String empName;
	/**
	 * 部门编码
	 */
	private String orgCode;
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 员工职位编码
	 */
	private String titleValueCode;
	/**
	 * 员工职位名称
	 */
	private String titleValueName;
	/**
	 * 员工职位编码（员工表）
	 */
	private String title;
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * @return  the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	/**
	 * @return  the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return  the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return  the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return  the titleValueCode
	 */
	public String getTitleValueCode() {
		return titleValueCode;
	}
	/**
	 * @param titleValueCode the titleValueCode to set
	 */
	public void setTitleValueCode(String titleValueCode) {
		this.titleValueCode = titleValueCode;
	}
	/**
	 * @return  the titleValueName
	 */
	public String getTitleValueName() {
		return titleValueName;
	}
	/**
	 * @param titleValueName the titleValueName to set
	 */
	public void setTitleValueName(String titleValueName) {
		this.titleValueName = titleValueName;
	}
	/**
	 * @return  the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

}

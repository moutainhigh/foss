/** 
 * Project Name:pkp-waybill-share 
 * File Name:OrgCodeTreeByCodeAndCondition.java 
 * Package Name:com.deppon.foss.module.pickup.waybill.shared.dto 
 * Date:2015-9-6下午3:45:41 
 * Copyright © 2015 德邦物流股份有限公司. All rights reserved
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * Describe：根据机构编码和条件查询机构代码集合
 *
 * @author Foss-105888-Zhangxingwang
 * @Date:2015-9-6  下午3:45:41 
 * Copyright © 2015 德邦物流股份有限公司. All rights reserved
 */
public class OrgCodeTreeByCodeAndCondition  implements Serializable{

	/**
	 *  UID
	 */
	
	private static final long serialVersionUID = 1092243311348430867L;

	/**
	 * 机构编码
	 */
	private String code;
	/**
	 * 是否为开单组
	 */
	private String billingGroup;
	/**
	 * 是否为经营本部
	 */
	private String isManageDepartment;
	/**
	 * 是否为事业部
	 */
	private String division;
	
	/**
	 * 是否启用
	 */
	private String active;
	
	/**
	 * 查询方向   该属性决定是往上查询还是往下查询    默认往下查询
	 * 设置值（无论什么值）往下查询
	 * 不设置值往下查询
	 */
	private String isDownQuery;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBillingGroup() {
		return billingGroup;
	}

	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}

	public String getIsManageDepartment() {
		return isManageDepartment;
	}

	public void setIsManageDepartment(String isManageDepartment) {
		this.isManageDepartment = isManageDepartment;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIsDownQuery() {
		return isDownQuery;
	}

	public void setIsDownQuery(String isDownQuery) {
		this.isDownQuery = isDownQuery;
	}
	
}

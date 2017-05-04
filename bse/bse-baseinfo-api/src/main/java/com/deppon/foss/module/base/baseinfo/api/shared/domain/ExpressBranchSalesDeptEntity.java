package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递分部营业部映射关系
 * @author foss-WeiXing
 * @date 2014-9-22 下午6:20:13
 */
public class ExpressBranchSalesDeptEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	//快递分部
	private String expressBranchCode;
	
	//快递分部名称
	private String expressBranchName;
	
	//营业部
	private String salesDeptCode;
	
	//营业部名称
	private String salesDeptName;
	
	//是否启用
	private String active;
	
	//版本号
	private long versionNo;

	
	public String getExpressBranchCode() {
		return expressBranchCode;
	}

	public void setExpressBranchCode(String expressBranchCode) {
		this.expressBranchCode = expressBranchCode;
	}

	public String getExpressBranchName() {
		return expressBranchName;
	}

	public void setExpressBranchName(String expressBranchName) {
		this.expressBranchName = expressBranchName;
	}

	public String getSalesDeptCode() {
		return salesDeptCode;
	}

	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
	
}

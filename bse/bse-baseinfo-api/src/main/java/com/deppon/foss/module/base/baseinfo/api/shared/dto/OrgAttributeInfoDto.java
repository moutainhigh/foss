package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * 组织属性查询实体
 * @author leo-zeng
 *
 */
@XmlRootElement(name="OrgAttributeInfoDto")
public class OrgAttributeInfoDto {
	/**
	 * 部门标杆编码
	 */
	private String orgCode;
	/**
	 * 是否营业部
	 */
	private String isSalesDepartment;
	/**
	 * 是否快递虚拟营业部
	 */
	private String isExpVitrualSales;
	/**
	 * 失败的消息
	 */
	private String message;
	/**
	 * 是否操作成功
	 */
	private boolean isSuccess;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getIsSalesDepartment() {
		return isSalesDepartment;
	}
	public void setIsSalesDepartment(String isSalesDepartment) {
		this.isSalesDepartment = isSalesDepartment;
	}
	public String getIsExpVitrualSales() {
		return isExpVitrualSales;
	}
	public void setIsExpVitrualSales(String isExpVitrualSales) {
		this.isExpVitrualSales = isExpVitrualSales;
	}
	
	
}

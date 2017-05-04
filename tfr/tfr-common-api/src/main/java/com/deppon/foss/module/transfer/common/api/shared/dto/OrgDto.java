package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 中转提供给CRM接口  匹配任务部门 实体   也用于 匹配责任部门
 * @author 200978  xiaobingcheng
 * 2014-10-22
 */
@XmlRootElement
public class OrgDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6636968498153698757L;
	
	/**任务部门编码（标杆编码）*/
	private String orgUnifiedCode;
	/**是否外场*/
	private String isTransferCenter;
	/**部门编码*/
	private String orgCode;
	/**任务部门名称*/
	private String orgName;
	/**异常信息*/
	private String message;

	public String getOrgUnifiedCode() {
		return orgUnifiedCode;
	}

	public void setOrgUnifiedCode(String orgUnifiedCode) {
		this.orgUnifiedCode = orgUnifiedCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIsTransferCenter() {
		return isTransferCenter;
	}

	public void setIsTransferCenter(String isTransferCenter) {
		this.isTransferCenter = isTransferCenter;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "TaskOrgDto [orgUnifiedCode=" + orgUnifiedCode
				+ ", isTransferCenter=" + isTransferCenter + ", orgCode="
				+ orgCode + ", orgName=" + orgName + ", message=" + message
				+ "]";
	}

}

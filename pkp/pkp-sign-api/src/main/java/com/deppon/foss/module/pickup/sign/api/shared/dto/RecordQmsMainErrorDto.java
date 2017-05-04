package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;


/**
 * @clasaName:com.deppon.foss.module.pickup.sign.api.shared.dto.RecordQmsMainErrorDto
 * @author: foss-bieyexiong
 * @description: foss自动上报QMS差错 主信息
 * @date:2015年5月4日 下午16:01:32
 */
public class RecordQmsMainErrorDto implements Serializable {

	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 上报人工号(自动上报时为FOSS)
	 */
	private String repEmpcode;
	
	/**
	 * 上报人姓名(自动上报时为null)
	 */
	private String repEmpName;
	
	/**
	 * 上报人职位(自动上报时为null)
	 */
	private String repEmpJob;
	
	/**
	 * 文件标准id
	 */
	private String docStandarId;
	
	/**
	 * 运单号
	 */
	private String wayBillNum;
	
	/**
	 * 收货部门标杆编码
	 */
	private String receiveDeptCode;
	
	/**
	 * 收货部门名称
	 */
	private String receiveDeptName;
	
	/**
	 * 上报人所属部门
	 */
	private String repDeptCode;
	
	/**
	 * 上报人所属部门名称
	 */
	private String repDeptName;

	public String getRepEmpcode() {
		return repEmpcode;
	}

	public void setRepEmpcode(String repEmpcode) {
		this.repEmpcode = repEmpcode;
	}

	public String getRepEmpName() {
		return repEmpName;
	}

	public void setRepEmpName(String repEmpName) {
		this.repEmpName = repEmpName;
	}

	public String getRepEmpJob() {
		return repEmpJob;
	}

	public void setRepEmpJob(String repEmpJob) {
		this.repEmpJob = repEmpJob;
	}

	public String getDocStandarId() {
		return docStandarId;
	}

	public void setDocStandarId(String docStandarId) {
		this.docStandarId = docStandarId;
	}

	public String getWayBillNum() {
		return wayBillNum;
	}

	public void setWayBillNum(String wayBillNum) {
		this.wayBillNum = wayBillNum;
	}

	public String getReceiveDeptCode() {
		return receiveDeptCode;
	}

	public void setReceiveDeptCode(String receiveDeptCode) {
		this.receiveDeptCode = receiveDeptCode;
	}

	public String getReceiveDeptName() {
		return receiveDeptName;
	}

	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}

	public String getRepDeptCode() {
		return repDeptCode;
	}

	public void setRepDeptCode(String repDeptCode) {
		this.repDeptCode = repDeptCode;
	}

	public String getRepDeptName() {
		return repDeptName;
	}

	public void setRepDeptName(String repDeptName) {
		this.repDeptName = repDeptName;
	}

}

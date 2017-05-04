package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

/**
 * PDAUserEntity：配合PDA同步用户JSON信息
 * @author 106162-foss-liping
 * @date 2016-05-03 上午8:52:54
 */
public class PDAUserEntity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户编码
	 */
	private String userCode;
	
	/**
	 * PDA编码
	 */
	private String pdaCode;
	
	/**
	 * 部门编码
	 */
	private String deptCode;
	
	/**
	 * 操作编码
	 */
	private String operType;

	/**
	 * 版本号
	 */
	private String pgmVer;
	
	/**
	 * 操作类型
	 */
	private String pdaType;
	
	/**
	 * 用户类型
	 */
	private String userType;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPdaCode() {
		return pdaCode;
	}

	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	

	public String getPgmVer() {
		return pgmVer;
	}

	public void setPgmVer(String pgmVer) {
		this.pgmVer = pgmVer;
	}

	public String getPdaType() {
		return pdaType;
	}

	public void setPdaType(String pdaType) {
		this.pdaType = pdaType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	@Override
	public String toString() {
		return "PDAUserEntity [userCode=" + userCode + ", pdaCode=" + pdaCode
				+ ", deptCode=" + deptCode + ", operCode=" + operType
				+ ", pgmVer=" + pgmVer + ", pdaType=" + pdaType + ", userType="
				+ userType + "]";
	}

}

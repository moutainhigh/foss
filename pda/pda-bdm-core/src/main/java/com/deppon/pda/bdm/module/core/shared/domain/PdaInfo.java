package com.deppon.pda.bdm.module.core.shared.domain;

/**
 * 
  * @ClassName PdaInfo 
  * @Description PDA信息 
  * @author xujun cometzb@126.com 
  * @date 2012-12-25
 */
public class PdaInfo {
	
	/**
	 * 操作类型
	 */
	private String operType;
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * 站点编号
	 */
	private String deptCode;
	/**
	 * 员工编号
	 */
	private String userCode;
	/**
	 * 设备型号
	 */
	private String pdaType;
	/**
	 * 设备版本号
	 */
	private String pgmVer;
	
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
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
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPdaType() {
		return pdaType;
	}
	public void setPdaType(String pdaType) {
		this.pdaType = pdaType;
	}
	public String getPgmVer() {
		return pgmVer;
	}
	public void setPgmVer(String pgmVer) {
		this.pgmVer = pgmVer;
	}

	
	
}
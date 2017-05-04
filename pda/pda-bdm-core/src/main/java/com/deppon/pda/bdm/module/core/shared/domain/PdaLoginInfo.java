package com.deppon.pda.bdm.module.core.shared.domain;

/**
 * 
  * @ClassName PdaLoginInfo 
  * @Description PDA登陆信息 
  * @author xujun cometzb@126.com 
  * @date 2012-12-25
 */
public class PdaLoginInfo {
	private String id;
	/**
	 * 营业部编号
	 */
	private String deptCode;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * PDA编号
	 */
	private String pdaCode;
	/**
	 * PDA数据版本
	 */
	private String pdaDataVer;
	/**
	 * PDA软件版本
	 */
	private String pdaPgmVer;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 员工工号
	 */
	private String userCode;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 用户SIM卡序列号
	 */
	private String pdaSimIMSI;
	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPdaCode() {
		return pdaCode;
	}
	public void setPdaCode(String pdaCode) {
		this.pdaCode = pdaCode;
	}
	public String getPdaDataVer() {
		return pdaDataVer;
	}
	public void setPdaDataVer(String pdaDataVer) {
		this.pdaDataVer = pdaDataVer;
	}
	public String getPdaPgmVer() {
		return pdaPgmVer;
	}
	public void setPdaPgmVer(String pdaPgmVer) {
		this.pdaPgmVer = pdaPgmVer;
	}
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPdaSimIMSI() {
		return pdaSimIMSI;
	}
	public void setPdaSimIMSI(String pdaSimIMSI) {
		this.pdaSimIMSI = pdaSimIMSI;
	}
	
}
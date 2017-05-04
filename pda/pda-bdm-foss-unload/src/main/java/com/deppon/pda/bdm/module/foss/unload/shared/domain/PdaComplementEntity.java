package com.deppon.pda.bdm.module.foss.unload.shared.domain;

/**
 * 补码结果实体
 * @author mt
 * 2013年7月17日17:39:38
 */
public class PdaComplementEntity {
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 部门编码
	 */
	private String deptCode;
	/**
	 * 目的部门
	 */
	private String desDeptCode;
	/**
	 * 送货地址
	 */
	private String deryCrgAddress;
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDesDeptCode() {
		return desDeptCode;
	}
	public void setDesDeptCode(String desDeptCode) {
		this.desDeptCode = desDeptCode;
	}
	public String getDeryCrgAddress() {
		return deryCrgAddress;
	}
	public void setDeryCrgAddress(String deryCrgAddress) {
		this.deryCrgAddress = deryCrgAddress;
	}
	
}

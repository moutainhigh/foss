package com.deppon.pda.bdm.module.foss.clear.shared.domain;


import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class PackageClearScanEntity extends ScanMsgEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 包号
	 */
	private String packageNo;
	/**
	 * 是否多货
	 */
	private String isMore;
	/**
	 * 库位号
	 */
	private String position;

	
	
	public String getIsMore() {
		return isMore;
	}
	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

}

package com.deppon.pda.bdm.module.foss.packaging.shared.domain;

import java.util.Date;
import java.util.List;

/**
 * 
 * TODO(待包装货物信息)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午11:25:57,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午11:25:57
 * @since
 * @version
 */
public class WrapedCrgDetail {
	/**
	 * 运单号
	 */
	private String wblCode;
	
	private List<WrapedSerial> serialNo;
	/**
	 * 开单件数
	 */
	private int billPieces;
	/**
	 * 入包装货区件数
	 */
	private int invtPieces;
	
	/**
	 * 运输性质
	 */
	private String transType;
	/**
	 * 发车时间
	 */
	private Date driveTime;
	/**
	 * 开单部门
	 */
	private String billDeptCode;
	/**
	 * 包装要求
	 */
	private String wrapRequest;
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public int getBillPieces() {
		return billPieces;
	}
	public void setBillPieces(int billPieces) {
		this.billPieces = billPieces;
	}
	public int getInvtPieces() {
		return invtPieces;
	}
	public void setInvtPieces(int invtPieces) {
		this.invtPieces = invtPieces;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Date getDriveTime() {
		return driveTime;
	}
	public void setDriveTime(Date driveTime) {
		this.driveTime = driveTime;
	}
	public String getBillDeptCode() {
		return billDeptCode;
	}
	public void setBillDeptCode(String billDeptCode) {
		this.billDeptCode = billDeptCode;
	}
	public String getWrapRequest() {
		return wrapRequest;
	}
	public void setWrapRequest(String wrapRequest) {
		this.wrapRequest = wrapRequest;
	}
	public List<WrapedSerial> getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(List<WrapedSerial> serialNo) {
		this.serialNo = serialNo;
	}
	
}

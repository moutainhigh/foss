/**
 * 
 */
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;

/**
 * @author niuly
 * @function 清仓差异明细
 */
public class PdaDifferDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	//包号
    private String packageNo;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	//处理状态
	private String handleStatus;
	//目的站
	private String destStation;
	/**
	 * @return the packageNo
	 */
	public String getPackageNo() {
		return packageNo;
	}
	/**
	 * @param packageNo the packageNo to set
	 */
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the handleStatus
	 */
	public String getHandleStatus() {
		return handleStatus;
	}
	/**
	 * @param handleStatus the handleStatus to set
	 */
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	/**
	 * @return the destStation
	 */
	public String getDestStation() {
		return destStation;
	}
	/**
	 * @param destStation the destStation to set
	 */
	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}
}

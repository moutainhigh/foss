/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.io.Serializable;

/***
 * 中转库存Dto
 * @author foss-meiying
 * @date 2012-10-19 下午7:12:23
 * @since
 * @version
 */
public class StockDto implements Serializable {
	private static final long serialVersionUID = -2731217099657318563L;
	/** 
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 旧到达联id
	 */
	private String oldtSrvArriveSheetId;
	/**
	 * 新到达联id
	 */
	private String newtSrvArriveSheetId;
	/**
	 *  运单号
	 */
	private String waybillNo; 
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 是否作废
	 */
	private String destroyed;
	/**
	 * 到达联状态
	 */
	private String status;
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to see
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOldtSrvArriveSheetId() {
		return oldtSrvArriveSheetId;
	}

	public void setOldtSrvArriveSheetId(String oldtSrvArriveSheetId) {
		this.oldtSrvArriveSheetId = oldtSrvArriveSheetId;
	}

	public String getNewtSrvArriveSheetId() {
		return newtSrvArriveSheetId;
	}

	public void setNewtSrvArriveSheetId(String newtSrvArriveSheetId) {
		this.newtSrvArriveSheetId = newtSrvArriveSheetId;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDestroyed() {
		return destroyed;
	}

	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
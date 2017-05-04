package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/***
 * 提货清单Dto
 * @author foss-yuting
 * @date 2014-11-21 上午11:08:31
 * @since
 * @version
 */
@SuppressWarnings("serial")
public class PickupDto implements Serializable{
	/** 
	 * id
	 */
	private String id;
	/** 
	 * 运单号
	 */
	private String waybillNo;
	/**
	 *  操作时间起
	 */
	private Date operTimeStart;
	/** 
	 * 操作时间止
	 */
	private Date operTimeEnd;
	
	
	/**
	 * 操作的状态，下拉框为数据字典配置，包含已告知、已部分签收、已全部签收、已反签收、已撤销、全部，默认显示“已告知”状态；
	 */
	private String state;
	/**
	 * 当前操作部门编码 
	 */
	private String currentDeptCode;
	
	/**
	 * 当前操作部门名称
	 */
	private String currentDeptName;
	
	/**
	 * 驻地外场
	 */
	private String endStockOrgCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getOperTimeStart() {
		return operTimeStart;
	}

	public void setOperTimeStart(Date operTimeStart) {
		this.operTimeStart = operTimeStart;
	}

	public Date getOperTimeEnd() {
		return operTimeEnd;
	}

	public void setOperTimeEnd(Date operTimeEnd) {
		this.operTimeEnd = operTimeEnd;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}
}

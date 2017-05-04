/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 运单查询Dto
 * @author 043258-foss-zhaobin
 * @date 2013-1-6 下午5:30:15
 */
public class WaybillQueryInfoDto implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  状态
	 */
	private String state;

	/**
	 *  运单单号list
	 */
	private List<String> waybillList;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 *  PDA补录处理类型
	 */
	private List<String> pendingTypes;
	/**
	 * 付款方式
	 */
	private String paidMethod;

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * Gets the waybill list.
	 *
	 * @return the waybill list
	 */
	public List<String> getWaybillList()
	{
		return waybillList;
	}

	/**
	 * Sets the waybill list.
	 *
	 * @param waybillList the new waybill list
	 */
	public void setWaybillList(List<String> waybillList)
	{
		this.waybillList = waybillList;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<String> getPendingTypes() {
		return pendingTypes;
	}

	public void setPendingTypes(List<String> pendingTypes) {
		this.pendingTypes = pendingTypes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	
}
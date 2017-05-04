/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.dubbo.api.define;

import java.io.Serializable;


/**
 * 用于封装运单查询参数的DTO
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 下午2:39:52
 */
public class WaybillQueryArgsDto implements Serializable{
	/**
	 * 序列化标识 
	 */
	private static final long serialVersionUID = 4415497196785932002L;
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 订单编号
	 */
	private String orderNo;
	
	/**
	 * 打印流水号
	 */
	private String serialNo;
	
	/**
	 * 状态
	 */
	private String active;
	
	/**
	 * 运单状态
	 */
	private String waybillStatus;

	/**
	 * 类型
	 */
	private String type;	
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo .
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to set.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * Gets the order no.
	 *
	 * @return the orderNo .
	 */
	public String getOrderNo() {
		return orderNo;
	}

	
	/**
	 * Sets the order no.
	 *
	 * @param orderNo the orderNo to set.
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	/**
	 * Gets the active.
	 *
	 * @return the active .
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * Sets the active.
	 *
	 * @param active the active to set.
	 */
	public void setActive(String active) {
		this.active = active;
	}


	/**
	 * Gets the serial no.
	 *
	 * @return the serial no
	 */
	public String getSerialNo()
	{
		return serialNo;
	}


	/**
	 * Sets the serial no.
	 *
	 * @param serialNo the new serial no
	 */
	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}


	/**
	  * @return  the waybillStatus
	 */
	public String getWaybillStatus() {
		return waybillStatus;
	}


	/**
	 * @param waybillStatus the waybillStatus to set
	 */
	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}
	
}
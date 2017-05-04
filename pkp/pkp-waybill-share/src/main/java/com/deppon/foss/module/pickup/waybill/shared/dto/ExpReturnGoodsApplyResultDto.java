package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建返货申请到crm之后，返回crm的信息
 * @author 201287
 *
 */
public class ExpReturnGoodsApplyResultDto implements Serializable{


	private static final long serialVersionUID = 8666163428691396560L;
	/**
	 * 访问时间
	 */
	private Date entryTime;
	/**
	 * 返回时间
	 */
	private Date returnTime;
	/**
	 * 状态 
	 * 0 处理失败  1成功  2其他异常
	 */
	private int statusCode;
	
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}

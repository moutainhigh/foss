package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * mq日志记录表
 * @author 326181
 *
 */
public class MqLogEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String waybillNo;//运单号
	private String esbCode;//esb编码
	private String requestMsg;//请求信息
	private String responseMsg;//响应信息
	private Date createTime;//创建时间
	
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
	public String getEsbCode() {
		return esbCode;
	}
	public void setEsbCode(String esbCode) {
		this.esbCode = esbCode;
	}
	public String getRequestMsg() {
		return requestMsg;
	}
	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

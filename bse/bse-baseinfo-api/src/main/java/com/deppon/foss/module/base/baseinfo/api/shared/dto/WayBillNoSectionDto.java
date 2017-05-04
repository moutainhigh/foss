package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * 运单号段DTO
 * @author 262036 HuangWei
 *
 * @date 2015-6-25 上午8:08:39
 */
@XmlRootElement(name="WayBillNoSectionDto")
public class WayBillNoSectionDto implements Serializable{

	private static final long serialVersionUID = 10593343956L;
	/**
	 * 起始运单号
	 */
	private String startWayBillNo;
	/**
	 * 结束运单号
	 */
	private String endWayBillNo;
	
	/**
	 * 失败的消息
	 */
	private String message;
	/**
	 * 是否操作成功
	 */
	private boolean isSuccess;
	
	public String getStartWayBillNo() {
		return startWayBillNo;
	}
	public void setStartWayBillNo(String startWayBillNo) {
		this.startWayBillNo = startWayBillNo;
	}
	public String getEndWayBillNo() {
		return endWayBillNo;
	}
	public void setEndWayBillNo(String endWayBillNo) {
		this.endWayBillNo = endWayBillNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}

package com.deppon.pda.bdm.module.foss.unload.shared.domain.partner;

import java.io.Serializable;

/**
 * 
 * @ClassName: ScanTaskUploadEntity 
 * @Description: TODO(扫描任务上传参数实体) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午10:10:35 
 *
 */
public class ScanTaskUploadEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	//id
	private String id;
	//任务号
	private String orderTaskNo;
	//运单号
	private String waybillNo;
	//流水号
	private String serialNo;
	//用户编码
	private String userCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
}

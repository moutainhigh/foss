package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * @description:托盘任务明细实体
 * @author: 105869-foss-heyongdong
 * @date: 2013-07-25 
 */
public class TrayScanDetaiEntity extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -2839993335705680530L;
	/**
	 * id
	 * */
	private String id;
	
	/**
	 *包号 
	 ***/
	private String packageNo;
	
	
	/**
	 * 绑定托盘的运单号
	 * */
	private String  waybillNo;
	/**
	 * 绑定托盘的流水号
	 * */
	private String  serialNo;
	
	/**
	 * 绑定托盘的运单对应的目的站
	 * */
	private String  destDeptName;
	
	/**
	 * 绑定托盘的运单对应的目的站编码
	 * */
	private String  destDeptCode;
	
	/**
	 * 托盘任务code
	 * */
	private String  trayScanTaskNo;
	
	/**
	 * 托盘任务明细
	 * */
	private String waybillInfo;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillInfo() {
		return waybillInfo;
	}

	public void setWaybillInfo(String waybillInfo) {
		this.waybillInfo = waybillInfo;
	}



	public String getTrayScanTaskNo() {
		return trayScanTaskNo;
	}

	public void setTrayScanTaskNo(String trayScanTaskNo) {
		this.trayScanTaskNo = trayScanTaskNo;
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

	public String getDestDeptName() {
		return destDeptName;
	}

	public void setDestDeptName(String destDeptName) {
		this.destDeptName = destDeptName;
	}

	public String getDestDeptCode() {
		return destDeptCode;
	}

	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	
	
}

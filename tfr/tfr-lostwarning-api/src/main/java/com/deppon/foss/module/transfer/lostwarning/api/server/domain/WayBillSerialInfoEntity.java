package com.deppon.foss.module.transfer.lostwarning.api.server.domain;

import java.io.Serializable;

/**
 * 运单流水丢货信息
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：WayBillSerialInfoEntity
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-7-13 下午4:48:44
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class WayBillSerialInfoEntity implements Serializable {
	
	private static final long serialVersionUID = 8922664587734764104L;

	// 丢货流水号  
	private String flowCode;

	// 车牌号
	private String carCode;

	// 装车部门编码
	private String loadingDeptCode;

	// 装车部门名称
	private String loadingDeptName;

	// 卸车部门编码
	private String unloadingDeptCode;

	// 卸车部门名称
	private String unloadingDeptName;

	// 交接单号
	private String transferBill;

	// 业务环节编码 参考用户需求文档
	private String activeCode;

	// 业务渠道名称
	private String channelName;

	// 包号
	private String packageNumber;

	// 建包部门编码
	private String packDeptCode;

	// 建包部门名称
	private String packDeptName;

	// 解包部门编码
	private String unpackDeptCode;

	// 解包部门名称
	private String unpackDeptName;

	// 责任人工号
	private String respEmpcode;

	// 责任人姓名
	private String respEmpname;

	// 责任部门编码
	private String respDeptCode;

	// 责任部门名称
	private String respDeptName;

	// 责任事业部门编码
	private String respDivisionCode;

	// 责任事业部名称
	private String respDivisionName;

	// 责任货区
	private String respAreaName;


	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getLoadingDeptCode() {
		return loadingDeptCode;
	}

	public void setLoadingDeptCode(String loadingDeptCode) {
		this.loadingDeptCode = loadingDeptCode;
	}

	public String getLoadingDeptName() {
		return loadingDeptName;
	}

	public void setLoadingDeptName(String loadingDeptName) {
		this.loadingDeptName = loadingDeptName;
	}

	public String getUnloadingDeptCode() {
		return unloadingDeptCode;
	}

	public void setUnloadingDeptCode(String unloadingDeptCode) {
		this.unloadingDeptCode = unloadingDeptCode;
	}

	public String getUnloadingDeptName() {
		return unloadingDeptName;
	}

	public void setUnloadingDeptName(String unloadingDeptName) {
		this.unloadingDeptName = unloadingDeptName;
	}

	public String getTransferBill() {
		return transferBill;
	}

	public void setTransferBill(String transferBill) {
		this.transferBill = transferBill;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}

	public String getPackDeptCode() {
		return packDeptCode;
	}

	public void setPackDeptCode(String packDeptCode) {
		this.packDeptCode = packDeptCode;
	}

	public String getPackDeptName() {
		return packDeptName;
	}

	public void setPackDeptName(String packDeptName) {
		this.packDeptName = packDeptName;
	}

	public String getUnpackDeptCode() {
		return unpackDeptCode;
	}

	public void setUnpackDeptCode(String unpackDeptCode) {
		this.unpackDeptCode = unpackDeptCode;
	}

	public String getUnpackDeptName() {
		return unpackDeptName;
	}

	public void setUnpackDeptName(String unpackDeptName) {
		this.unpackDeptName = unpackDeptName;
	}

	public String getRespEmpcode() {
		return respEmpcode;
	}

	public void setRespEmpcode(String respEmpcode) {
		this.respEmpcode = respEmpcode;
	}

	public String getRespEmpname() {
		return respEmpname;
	}

	public void setRespEmpname(String respEmpname) {
		this.respEmpname = respEmpname;
	}

	public String getRespDeptCode() {
		return respDeptCode;
	}

	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}

	public String getRespDeptName() {
		return respDeptName;
	}

	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}

	public String getRespDivisionCode() {
		return respDivisionCode;
	}

	public void setRespDivisionCode(String respDivisionCode) {
		this.respDivisionCode = respDivisionCode;
	}

	public String getRespDivisionName() {
		return respDivisionName;
	}

	public void setRespDivisionName(String respDivisionName) {
		this.respDivisionName = respDivisionName;
	}

	public String getRespAreaName() {
		return respAreaName;
	}

	public void setRespAreaName(String respAreaName) {
		this.respAreaName = respAreaName;
	}

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

}

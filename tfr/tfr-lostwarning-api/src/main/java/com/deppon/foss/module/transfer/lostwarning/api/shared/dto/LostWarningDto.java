package com.deppon.foss.module.transfer.lostwarning.api.shared.dto;

/**
 * 丢失预警数据
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：LostWarningDto
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-13 下午5:36:12
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostWarningDto{
	//运单编号
	private String wayBillNo;
	//运单流水号
	private String serialNo;
	//责任人工号
	private String respEmpCode;
	//责任人名称
	private String respEmpName;
	//责任部门编号
	private String respDeptCode;
	//责任货区编码
	private String respAreaCode;
	//责任货区名称
	private String respAreaName;
	//车牌号
	private String carCode;
	//装车部门编码
	private String loadingDeptCode;
	//装车部门名称
	private String loadingDeptName;
	//卸车部门编码
	private String unloadingDeptCode;
	//卸车部门名称
	private String unloadingDeptName;
	//交接单号
	private String transferBill;
	//建包部门
	private String packDeptCode;
	//解包部门
	private String unpackDeptCode;
	//包号
	private String packageNumber;
	//装车类型 派送-DELIVER;偏线-PARTIALLINE;长途装车-LONG_DISTANCE、短途装车-SHORT_DISTANCE
	private String businessType;
	
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getRespEmpCode() {
		return respEmpCode;
	}
	public void setRespEmpCode(String respEmpCode) {
		this.respEmpCode = respEmpCode;
	}
	public String getRespDeptCode() {
		return respDeptCode;
	}
	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}
	
	public String getRespAreaCode() {
		return respAreaCode;
	}
	public void setRespAreaCode(String respAreaCode) {
		this.respAreaCode = respAreaCode;
	}
	public String getRespAreaName() {
		return respAreaName;
	}
	public void setRespAreaName(String respAreaName) {
		this.respAreaName = respAreaName;
	}
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
	public String getUnloadingDeptCode() {
		return unloadingDeptCode;
	}
	public void setUnloadingDeptCode(String unloadingDeptCode) {
		this.unloadingDeptCode = unloadingDeptCode;
	}
	public String getTransferBill() {
		return transferBill;
	}
	public void setTransferBill(String transferBill) {
		this.transferBill = transferBill;
	}
	public String getPackDeptCode() {
		return packDeptCode;
	}
	public void setPackDeptCode(String packDeptCode) {
		this.packDeptCode = packDeptCode;
	}
	public String getUnpackDeptCode() {
		return unpackDeptCode;
	}
	public void setUnpackDeptCode(String unpackDeptCode) {
		this.unpackDeptCode = unpackDeptCode;
	}
	public String getPackageNumber() {
		return packageNumber;
	}
	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}
	public String getRespEmpName() {
		return respEmpName;
	}
	public void setRespEmpName(String respEmpName) {
		this.respEmpName = respEmpName;
	}
	public String getLoadingDeptName() {
		return loadingDeptName;
	}
	public void setLoadingDeptName(String loadingDeptName) {
		this.loadingDeptName = loadingDeptName;
	}
	public String getUnloadingDeptName() {
		return unloadingDeptName;
	}
	public void setUnloadingDeptName(String unloadingDeptName) {
		this.unloadingDeptName = unloadingDeptName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
}	

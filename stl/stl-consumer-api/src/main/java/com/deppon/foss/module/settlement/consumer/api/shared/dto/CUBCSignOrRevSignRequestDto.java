package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * CUBC签收，反签收请求参数实体
 * @author 353654
 *
 */
public class CUBCSignOrRevSignRequestDto implements Serializable{
	
	//序列化版本号
	private static final long serialVersionUID = 6281135135234429519L;
		
	//来源编号，运单号
	private String sourceNo;
	
	//运输性质
	private String productType;
	
	//签收部门编码
	private String signOrgCode;
	
	//签收部门名称
	private String signOrgName;
	
	//签收日期
	private Date signDate;
	
	//业务发起时间
	private Date requestDate;
	
	//是否整车
	private String isWholeVehicle;
	
	//是否PDA
	private String isPdaSign;
	
	//是否快递
	private String isExpress;
	
	//操作人编码
	private String operatorCode;
	
	//操作人名称
	private String operatorName;
	
	//签收类型 专线正常签收、偏线/空运正常签收    异常签收类型编码
	private String signType;
	
	//外发流水号
	private String serialNo;
	
	//外发单号
	private List<String> externalWaybillNos;
	
	//签收情况
	private String signSituation;
	
	//用户名
	private String userName;
		
	//员工工号
	private String empCode;
		
	//员工姓名
	private String empName;
		
	//当前登录部门编码
	private String currentDeptCode;
		
	//当前登录部门名称
	private String currentDeptName;
	
	//备注
	private String notes;
	
	public List<String> getExternalWaybillNos() {
		return externalWaybillNos;
	}

	public void setExternalWaybillNos(List<String> externalWaybillNos) {
		this.externalWaybillNos = externalWaybillNos;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSignOrgCode() {
		return signOrgCode;
	}

	public void setSignOrgCode(String signOrgCode) {
		this.signOrgCode = signOrgCode;
	}

	public String getSignOrgName() {
		return signOrgName;
	}

	public void setSignOrgName(String signOrgName) {
		this.signOrgName = signOrgName;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	public String getIsPdaSign() {
		return isPdaSign;
	}

	public void setIsPdaSign(String isPdaSign) {
		this.isPdaSign = isPdaSign;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
}

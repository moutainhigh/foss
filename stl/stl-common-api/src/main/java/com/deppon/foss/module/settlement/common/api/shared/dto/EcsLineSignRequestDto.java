package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 快递业务 签收 确认收入 / 反签收 财务出库 请求的参数DTO
 * @author 243921-zhangtingting
 * @date 2016-04-16 上午10:18:32
 *
 */
public class EcsLineSignRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//当前登录员工工号
	private String empCode;
			
	//当前登录员工姓名
	private String empName;
			
	//当前登录部门编码
	private String currentDeptCode;
			
	//当前登录部门名称
	private String currentDeptName;

	//运单号
	private String waybillNo;
	
	//签收时间/反签收时间
	private Date signDate;
	
	//签收类型 专线（LS）正常签收、快递代理（LDS）正常签收
	private String signType;
	
	//是否整车运单(默认否)
	private String isWholeVehicle;

	//是否PDA签收
	private String isPdaSign;
	
	//运输性质(产品类型)
	private String productType;
	
	//外发流水号
	private String serialNo;
	
	//外发单号
	private String externalWaybillNo;
	
	//签收情况
	private String signSituation;

	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
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

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getExternalWaybillNo() {
		return externalWaybillNo;
	}

	public void setExternalWaybillNo(String externalWaybillNo) {
		this.externalWaybillNo = externalWaybillNo;
	}
}

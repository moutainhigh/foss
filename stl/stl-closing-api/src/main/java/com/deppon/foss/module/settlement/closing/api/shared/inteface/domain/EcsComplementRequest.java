package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.io.Serializable;
import java.util.Date;

public class EcsComplementRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//补码员工号
    private String empCode;
    
    //补码员姓名
    private String empName;
    
    //补码部门编码
    private String currentDeptCode;
    
    //补码部门名称
    private String currentDeptName;
    
    //运单号
    private String waybillNo;
    
    //最新提货网点的到达部门编码（补码后的到达部门）
    private String destOrgCode;
    
    //最新提货网点的到达部门名称（补码后的到达部门）
    private String destOrgName;

    //用于区分自有网点补码，还是快递代理补码 自有网点 Y  快递代理 N
    private String isFreeSite;
    
    //开单时间-ecs用
	private Date billTime;
	
	//收货部门-ecs用
	private String receiveOrgCode;

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

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getIsFreeSite() {
		return isFreeSite;
	}

	public void setIsFreeSite(String isFreeSite) {
		this.isFreeSite = isFreeSite;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
    
}

package com.deppon.foss.module.transfer.pda.api.shared.domain;

public class ExpressPathDetailEntiy {
	//走货路由顺序
	private String routeNo;
	//出发部门
	private String origOrgCode;
	//到达部门
	private String destOrgCode;
	public String getRouteNo() {
		return routeNo;
	}
	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	
	
}

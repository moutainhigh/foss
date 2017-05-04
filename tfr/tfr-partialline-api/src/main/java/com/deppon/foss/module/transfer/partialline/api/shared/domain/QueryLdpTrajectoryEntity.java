package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.util.Date;


/**
 * @author 218427
 * 
 */
public class QueryLdpTrajectoryEntity {
	
	/**运单号**/
	private String wayBillNo;
	/**状态**/
    private String status;

	/**最后一次操作时间**/
	private Date lastOperateTime;
	/**已录入天数**/
	private String inputedDays;
	/**最后一次操作人**/
	private String lastOperator;
    /**快递代理网点名称**/
	private String agentOrgName;
	/**快递代理网点编码**/
	private String agentOrgCode;
	/**快递代理公司编码**/
	private String agentCompanyName;
	/**快递代理公司名称**/
	private String agentCompanyCode;
	
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getAgentOrgName() {
		return agentOrgName;
	}
	public void setAgentOrgName(String agentOrgName) {
		this.agentOrgName = agentOrgName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setLastOperateTime(Date lastOperateTime) {
		this.lastOperateTime = lastOperateTime;
	}
	public String getInputedDays() {
		return inputedDays;
	}
	public void setInputedDays(String inputedDays) {
		this.inputedDays = inputedDays;
	}
	public String getLastOperator() {
		return lastOperator;
	}
	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}
	public String getAgentOrgCode() {
		return agentOrgCode;
	}
	public void setAgentOrgCode(String agentOrgCode) {
		this.agentOrgCode = agentOrgCode;
	}
	public String getAgentCompanyName() {
		return agentCompanyName;
	}
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}
	public Date getLastOperateTime() {
		return lastOperateTime;
	}
	
	
}

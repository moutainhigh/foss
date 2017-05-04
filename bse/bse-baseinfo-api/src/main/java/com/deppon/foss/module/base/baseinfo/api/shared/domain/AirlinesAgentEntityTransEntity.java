package com.deppon.foss.module.base.baseinfo.api.shared.domain;

/**
 * 发送航空公司代理人信息实体类
 * @author 313353
 * @date 2016/05/28
 */
public class AirlinesAgentEntityTransEntity {
	/**
	 * 代理人编码
	 */
	private String agentCode;
	
	/**
     * 代理人名称.
     */
    private String agentName;
    
    /**
     * 配置部门编码.
     */
    private String dispatchDepartCode;
    
    /**
     * 配置部门
     */
    private String dispatchDepartment;
    
    /**
     * 始发城市编码.
     */
    private String originatingCityCode;
    
    /**
     * 始发城市
     */
    private String originatingCity;
    
    /**
     * 是否外部代理
     */
    private String isOutAgent;

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getDispatchDepartCode() {
		return dispatchDepartCode;
	}

	public void setDispatchDepartCode(String dispatchDepartCode) {
		this.dispatchDepartCode = dispatchDepartCode;
	}

	public String getDispatchDepartment() {
		return dispatchDepartment;
	}

	public void setDispatchDepartment(String dispatchDepartment) {
		this.dispatchDepartment = dispatchDepartment;
	}

	public String getOriginatingCityCode() {
		return originatingCityCode;
	}

	public void setOriginatingCityCode(String originatingCityCode) {
		this.originatingCityCode = originatingCityCode;
	}

	public String getOriginatingCity() {
		return originatingCity;
	}

	public void setOriginatingCity(String originatingCity) {
		this.originatingCity = originatingCity;
	}

	public String getIsOutAgent() {
		return isOutAgent;
	}

	public void setIsOutAgent(String isOutAgent) {
		this.isOutAgent = isOutAgent;
	}
    
    
	
}

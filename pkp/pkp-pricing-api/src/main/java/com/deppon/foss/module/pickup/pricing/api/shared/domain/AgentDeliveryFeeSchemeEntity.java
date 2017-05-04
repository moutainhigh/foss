package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 092020-lipengfei
 *	代理送货费方案
 */
public class AgentDeliveryFeeSchemeEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4752382442867839225L;
    /**
     * 方案名称.
     */
	private String schemeName;
	/**
	 * 基础产品：运输类型
	 */
	private String transportType;
	/**
	 * 目的站：来源于偏线代理网点基础资料中的“偏线代理网点名称”字段
	 */
	private String agentDeptName;
	/**
	 * 目的站编码
	 */
	private String agentDeptCode;
	/**
	 * 方案描述
	 */
	private String remark;
	/**
	 * 代理送货费
	 */
	private List<AgentDeliveryFeeEntity> feeEntityList;
	/**
	 * 版本号
	 */
	private Long versionNo;
	
	/**
     * 最后修改人姓名.
     */
    private String modifyUserName;
	
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	/*=======================getter/setter==============================*/
	/**
	 * 版本号
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}
	/**
	 * 版本号
	 * @param versionNo
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * 方案名称
	 * @return schemeName
	 */
	public String getSchemeName() {
		return schemeName;
	}
	/**
	 * 方案名称
	 * @param schemeName
	 */
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	/**
	 * 基础产品：运输类型
	 * @return transportType
	 */
	public String getTransportType() {
		return transportType;
	}
	/**
	 * 基础产品：运输类型
	 * @param transportType
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	/**
	 * 目的站：来源于偏线代理网点基础资料中的“偏线代理网点名称”字段
	 * @return agentDeptName
	 */
	public String getAgentDeptName() {
		return agentDeptName;
	}
	/**
	 * 目的站：来源于偏线代理网点基础资料中的“偏线代理网点名称”字段
	 * @param agentDeptName
	 */
	public void setAgentDeptName(String agentDeptName) {
		this.agentDeptName = agentDeptName;
	}
	/**
	 * 目的站编码
	 * @return agentDeptCode
	 */
	public String getAgentDeptCode() {
		return agentDeptCode;
	}
	/**
	 * 目的站编码
	 * @param agentDeptCode
	 */
	public void setAgentDeptCode(String agentDeptCode) {
		this.agentDeptCode = agentDeptCode;
	}
	/**
	 * 方案描述
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 方案描述
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 代理送货费
	 * @return feeEntityList
	 */
	public List<AgentDeliveryFeeEntity> getFeeEntityList() {
		return feeEntityList;
	}
	/**
	 * 代理送货费
	 * @param feeEntityList
	 */
	public void setFeeEntityList(List<AgentDeliveryFeeEntity> feeEntityList) {
		this.feeEntityList = feeEntityList;
	}

}

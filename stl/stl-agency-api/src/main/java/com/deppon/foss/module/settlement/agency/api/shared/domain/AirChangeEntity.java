package com.deppon.foss.module.settlement.agency.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 变更清单实体
 * @author chenmingchun
 * @date 2013-4-11 下午5:23:00
 * @since
 * @version
 */
public class AirChangeEntity implements Serializable{
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 代理公司编码
	 */
	private String agentCompanyCode;
	
	/**
	 * 代理公司名称
	 */
	private String agentCompanyName;
	
	/**
	 * 制单时间	
	 */
	private Date createTime;
	

	/**
	 * 合票/中转类型
	 */
	private String type;
	
	/**
	 * 变更之后金额
	 */
	private BigDecimal deliverFee;
	
	/**
	 * 到达部门名称
	 */
	
	private String destOrgCode;
	
	/**
	 * 到达部门编码
	 */
	private String destOrgName;

	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return  the agentCompanyCode
	 */
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	/**
	 * @param agentCompanyCode the agentCompanyCode to set
	 */
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	/**
	 * @return  the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return  the deliverFee
	 */
	public BigDecimal getDeliverFee() {
		return deliverFee;
	}

	/**
	 * @param deliverFee the deliverFee to set
	 */
	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}

	/**
	 * @return  the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return  the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * @return  the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return  the agentCompanyName
	 */
	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	/**
	 * @param agentCompanyName the agentCompanyName to set
	 */
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	
	
}

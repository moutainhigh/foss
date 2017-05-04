package com.deppon.foss.module.settlement.agency.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 补码功能DTO
 * @author foss-guxinhua
 * @date 2013-7-29 下午3:24:16
 */
public class SettlementComplementBillDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1441804912045817607L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 最新提货网点的到达部门编码
	 */
	private String destOrgCode;
	
	/**
	 * 最新提货网点的到达部门名称
	 */
	private String destOrgName;
	
	/**
	 * 用于区分自由网点补码，还是快递代理补码
	 * 自由网点 Y
	 * 快递代理 N
	 */
	private String isFreeSite;
	
	/**
	 * 开单时间-ecs用
	 */
	private Date billTime;
	
	/**
	 * 收货部门-ecs用
	 */
	private String receiveOrgCode;
	
	/**
	 * 来源系统是
	 */
	private String sourceSystem;
	
	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getIsFreeSite() {
		return isFreeSite;
	}

	public void setIsFreeSite(String isFreeSite) {
		this.isFreeSite = isFreeSite;
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

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

}

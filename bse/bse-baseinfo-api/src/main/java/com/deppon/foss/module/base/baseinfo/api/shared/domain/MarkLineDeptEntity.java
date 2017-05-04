package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 定义市场推广活动线路（出发到达部门）实体
 *
 * @author 078816
 * @date   2014-04-01
 *
 */
public class MarkLineDeptEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


	//活动的crmId
	private BigDecimal activiteCrmId;
	
	//活动ID（FOSS）
	private String activiteId;
	
	//出发外场编码
	private String origOrgCode;
	
	//出发外场名称
	private String origOrgName;
	
	//到达外场编码
	private String destOrgCode;
	
	//到达外场名称
	private String destOrgName;
	
	//出发区域编码
	private String leaveAreaCode;
	
	//到达区域编码
	private String arriveAreaCode;
	
	//线路类型
	private String lineType;
	
	private BigDecimal crmId;

	//是否启用
	private String active;
	
	public BigDecimal getActiviteCrmId() {
		return activiteCrmId;
	}

	public void setActiviteCrmId(BigDecimal activiteCrmId) {
		this.activiteCrmId = activiteCrmId;
	}

	public String getActiviteId() {
		return activiteId;
	}

	public void setActiviteId(String activiteId) {
		this.activiteId = activiteId;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public BigDecimal getCrmId() {
		return crmId;
	}

	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}

	/**
	 * @return the leaveAreaCode
	 */
	public String getLeaveAreaCode() {
		return leaveAreaCode;
	}

	/**
	 * @param leaveAreaCode the leaveAreaCode to set
	 */
	public void setLeaveAreaCode(String leaveAreaCode) {
		this.leaveAreaCode = leaveAreaCode;
	}

	/**
	 * @return the arriveAreaCode
	 */
	public String getArriveAreaCode() {
		return arriveAreaCode;
	}

	/**
	 * @param arriveAreaCode the arriveAreaCode to set
	 */
	public void setArriveAreaCode(String arriveAreaCode) {
		this.arriveAreaCode = arriveAreaCode;
	}
	 
}

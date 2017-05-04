package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 定义市场推广活动开展部门实体
 *
 * @author 078816
 * @date   2014-04-01
 *
 */
public class MarkActivitiesDeptEntity extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	//活动的crmId
	private BigDecimal activiteCrmId;
	
	//活动ID（FOSS）
	private String activiteId;
	
	//开展部门编码
	private String orgCode;
	
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public BigDecimal getCrmId() {
		return crmId;
	}

	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}
	
	
}

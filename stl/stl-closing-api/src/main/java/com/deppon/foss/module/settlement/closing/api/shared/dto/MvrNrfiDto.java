package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfiEntity;

/**
 * 始发专线往来月报表DTO.
 * 
 * @author 163576-foss-guxinhua
 * @date 2013-11-6 下午5:56:31
 */
public class MvrNrfiDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2775138661986166683L;

	private String period;

	private String customerCode;

	private String orgCode;

	private String orgType;

	private List<MvrNrfiEntity> list;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public List<MvrNrfiEntity> getList() {
		return list;
	}

	public void setList(List<MvrNrfiEntity> list) {
		this.list = list;
	}
}

package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ouyang 外场预算人员查询条件dto
 */
public class TfrCtrPersonnelBudgetQcDto implements Serializable {

	private static final long serialVersionUID = -6261183359673454496L;

	private String transferCenterCode;

	private Date effectiveDate;

	private Date firstDayOfMonth;

	public Date getFirstDayOfMonth() {
		return firstDayOfMonth;
	}

	public void setFirstDayOfMonth(Date firstDayOfMonth) {
		this.firstDayOfMonth = firstDayOfMonth;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}

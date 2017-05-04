package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class ManualInputQcDto implements Serializable{

	private static final long serialVersionUID = 5970810343294197878L;

	private String transferCenterCode;
	
	private String inputMonth;
	
	private Date inputDate;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getInputMonth() {
		return inputMonth;
	}

	public void setInputMonth(String inputMonth) {
		this.inputMonth = inputMonth;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	
}

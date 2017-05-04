package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MqLogEntity;

public class EcsFossLogVo implements Serializable {

	private static final long serialVersionUID = 1251669064772458633L;

	private MqLogEntity logEntityList;
	//运单号
	private String waybillNo;
	//esb编码
	private String esbCode;
	//开始时间
	private Date startDate;
	//结束时间
	private Date endDate;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getEsbCode() {
		return esbCode;
	}

	public void setEsbCode(String esbCode) {
		this.esbCode = esbCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public MqLogEntity getLogEntityList() {
		return logEntityList;
	}

	public void setLogEntityList(MqLogEntity logEntityList) {
		this.logEntityList = logEntityList;
	}

}
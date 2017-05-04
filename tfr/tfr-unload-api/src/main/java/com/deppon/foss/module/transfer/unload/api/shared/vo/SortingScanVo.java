/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.vo
 * FILE    NAME: SortingScanVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanCompareDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.SortingScanDto;

/**
 * 分拣扫描
 * @author dp-duyi
 * @date 2013-7-26 下午2:33:10
 */
public class SortingScanVo implements Serializable {

	private static final long serialVersionUID = 7021776016026132742L;
	
	private String wayBillNo;
	private String serialNo;
	private String operatorCode;
	private String superOrgCode;
	private String scanType;
	private String orgCode;
	private Date queryTimeEnd;
	private Date queryTimeBegin;
	private String scanMode;
	
	//分拣扫描比对dtoList
	private List<SortingScanCompareDto> sortingScanCompareDtoList;
	
	//查询dto
	private SortingScanDto sortingScanDto;
	
	
	public String getSuperOrgCode() {
		return superOrgCode;
	}
	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getQueryTimeEnd() {
		return queryTimeEnd;
	}
	public void setQueryTimeEnd(Date queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}
	public Date getQueryTimeBegin() {
		return queryTimeBegin;
	}
	public void setQueryTimeBegin(Date queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}
	public List<SortingScanCompareDto> getSortingScanCompareDtoList() {
		return sortingScanCompareDtoList;
	}
	public void setSortingScanCompareDtoList(
			List<SortingScanCompareDto> sortingScanCompareDtoList) {
		this.sortingScanCompareDtoList = sortingScanCompareDtoList;
	}
	public SortingScanDto getSortingScanDto() {
		return sortingScanDto;
	}
	public void setSortingScanDto(SortingScanDto sortingScanDto) {
		this.sortingScanDto = sortingScanDto;
	}
	public String getScanMode() {
		return scanMode;
	}
	public void setScanMode(String scanMode) {
		this.scanMode = scanMode;
	}
	
}

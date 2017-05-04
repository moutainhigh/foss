package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.AsyncComplementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AsyncComplementFailedQcDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementResultDto;

/**
 * @className: ComplementVo
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码vo类
 * @date: 2013-7-23 下午4:19:06
 * 
 */
public class ComplementVo {

	/**
	 * 查询条件
	 */
	private ComplementQueryDto queryComplementDto;

	/**
	 * 查询结果
	 */
	private List<ComplementQueryDto> complementList;

	/**
	 * 待补码的运单号list
	 */
	private List<String> waybillNoList;

	/**
	 * 补码后的提货网点code
	 */
	private String pkpOrgCode;

	/**
	 * 补码后的提货网点name
	 */
	private String pkpOrgName;

	/**
	 * 快递大区code
	 */
	private String expressBigAreaCode;

	/**
	 * 转运中心code
	 */
	private String transferCenterCode;

	/**
	 * 补码结果list
	 */
	private List<ComplementResultDto> resultList;

	/**
	 * 补码日志查询条件
	 */
	private ComplementLogDto complementLogDto;

	/**
	 * 补码日志查询结果
	 */
	private List<ComplementLogEntity> complementLogEntityList;

	/**
	 * 运单号，用于补码标签打印
	 */
	private String waybillNo;

	/**
	 * 补码简称，用于补码标签打印
	 */
	private String complementAbbr;

	/**
	 * 补码标签打印时间
	 */
	private Date complementLabelPrintDate;

	/**
	 * 异步补码失败运单的查询条件
	 */
	private AsyncComplementFailedQcDto asyncComplementFailedQcDto;

	/**
	 * 异步补码失败运单的查询结果集
	 */
	private List<AsyncComplementEntity> asyncComplementFailedList;

	/**
	 * 补码部门，用于失败界面；若当前用户属于外场或外场子部门，则取外场，否则区当前用户所属部门
	 */
	private String tfrCtrCode;
	/**
	 * 补码部门，用于失败界面；若当前用户属于外场或外场子部门，则取外场，否则区当前用户所属部门
	 */
	private String tfrCtrName;

	public List<ComplementResultDto> getResultList() {
		return resultList;
	}

	public void setResultList(List<ComplementResultDto> resultList) {
		this.resultList = resultList;
	}

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getExpressBigAreaCode() {
		return expressBigAreaCode;
	}

	public void setExpressBigAreaCode(String expressBigAreaCode) {
		this.expressBigAreaCode = expressBigAreaCode;
	}

	public String getPkpOrgName() {
		return pkpOrgName;
	}

	public void setPkpOrgName(String pkpOrgName) {
		this.pkpOrgName = pkpOrgName;
	}

	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}

	public String getPkpOrgCode() {
		return pkpOrgCode;
	}

	public void setPkpOrgCode(String pkpOrgCode) {
		this.pkpOrgCode = pkpOrgCode;
	}

	public ComplementQueryDto getQueryComplementDto() {
		return queryComplementDto;
	}

	public void setQueryComplementDto(ComplementQueryDto queryComplementDto) {
		this.queryComplementDto = queryComplementDto;
	}

	public List<ComplementQueryDto> getComplementList() {
		return complementList;
	}

	public void setComplementList(List<ComplementQueryDto> complementList) {
		this.complementList = complementList;
	}

	public List<ComplementLogEntity> getComplementLogEntityList() {
		return complementLogEntityList;
	}

	public void setComplementLogEntityList(List<ComplementLogEntity> complementLogEntityList) {
		this.complementLogEntityList = complementLogEntityList;
	}

	public ComplementLogDto getComplementLogDto() {
		return complementLogDto;
	}

	public void setComplementLogDto(ComplementLogDto complementLogDto) {
		this.complementLogDto = complementLogDto;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getComplementAbbr() {
		return complementAbbr;
	}

	public void setComplementAbbr(String complementAbbr) {
		this.complementAbbr = complementAbbr;
	}

	public Date getComplementLabelPrintDate() {
		return complementLabelPrintDate;
	}

	public void setComplementLabelPrintDate(Date complementLabelPrintDate) {
		this.complementLabelPrintDate = complementLabelPrintDate;
	}

	public AsyncComplementFailedQcDto getAsyncComplementFailedQcDto() {
		return asyncComplementFailedQcDto;
	}

	public void setAsyncComplementFailedQcDto(AsyncComplementFailedQcDto asyncComplementFailedQcDto) {
		this.asyncComplementFailedQcDto = asyncComplementFailedQcDto;
	}

	public List<AsyncComplementEntity> getAsyncComplementFailedList() {
		return asyncComplementFailedList;
	}

	public void setAsyncComplementFailedList(List<AsyncComplementEntity> asyncComplementFailedList) {
		this.asyncComplementFailedList = asyncComplementFailedList;
	}

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

}

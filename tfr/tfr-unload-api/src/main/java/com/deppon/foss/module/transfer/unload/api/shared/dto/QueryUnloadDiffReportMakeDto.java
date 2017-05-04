package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * @className: QueryUnloadDiffReportConditionDto
 * @author: gouyangyang 313352
 * @description: 卸车差异报告查询条件dto
 * @date: 2016-07-21 下午5:04:17
 * 
 */
public class QueryUnloadDiffReportMakeDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 卸车差异报告编号
	private String diffReportNo;
	// 车牌号
	private String vehicleNo;
	// 卸车任务编号
	private String unloadTaskNo;
	// 生成开始时间
	private Date beginCreateTime;
	// 生成截止时间
	private Date endCreateTime;
	// 理货员工号
	private String loadManCode;
	// 差异报告处理状态
	private String handleStatus;
	// 报告生成部门
	private String orgCode;
	// 运单号
	private String waybillNo;
	
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDiffReportNo() {
		return diffReportNo;
	}

	public void setDiffReportNo(String diffReportNo) {
		this.diffReportNo = diffReportNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	@DateFormat
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	@DateFormat
	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	@DateFormat
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	@DateFormat
	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getLoadManCode() {
		return loadManCode;
	}

	public void setLoadManCode(String loadManCode) {
		this.loadManCode = loadManCode;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
}

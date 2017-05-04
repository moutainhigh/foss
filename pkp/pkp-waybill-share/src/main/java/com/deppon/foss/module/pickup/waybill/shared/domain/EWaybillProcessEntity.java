/**
 * PDA盲扫信息
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

/**
 * PDA盲扫信息
 * @author FOSS-045925-YANGBIN
 * @date 2014-12-30 14:43:42
 * 
 */
public class EWaybillProcessEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7964574540007997495L;

	/**
	 * id
	 */
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * job占用ID
	 */
	private String jobId;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date modifyTime;
	
	/**
	 * 失败原因
	 */
	private String failReason;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
}

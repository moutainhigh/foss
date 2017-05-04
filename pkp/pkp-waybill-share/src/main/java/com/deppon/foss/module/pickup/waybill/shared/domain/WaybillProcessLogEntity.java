package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class WaybillProcessLogEntity implements Serializable{
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID 
	 */
    private String id;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 结果（success/failure）
     */
    private String operateResult;
    
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 激活线程的JOBID
     */
    private String jobId;
    
    /**
     * 更改项
     */
    private String content;
    
    /**
     * 日志类型，PDA后台日志和激活日志
     */
    private String logType;

    /**
     * 异常原因
     */
    private String failResion;

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
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

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getFailResion() {
		return failResion;
	}

	public void setFailResion(String failResion) {
		this.failResion = failResion;
	}

    

}
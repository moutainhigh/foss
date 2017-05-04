package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.util.Date;

/**
 * 
 * @author 350909   郭倩云
 * 异步调用结算的接口推送运单和待刷卡相关信息给结算的vo,主要操作 数据库表PKP.T_SRV_ADD_ASYN_WAYBILL
 *
 */
public class WSCWaybillProcessVo {
	
	//运单号
	private String wayBillNo;
	//请求类型
	private String requestType;
	
	private String id;

	/**
	 * 任务编号
	 */
    private String jobId;
    /**
	 * 数据库执行后受影响的条数
	 */
    private int resultNum;
    /**
	 * 要查询条数
	 */
    private String queryNum;
    /**
	 * 要查询的JobId
	 */
    private String queryJobId;
    //失败原因
    private String failReason;
    //创建时间
    private Date  createTime;
    //更新时间
    private Date modifyTime;

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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFailReason() {
		return failReason;
	}


	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}


	public int getResultNum() {
		return resultNum;
	}

	
	public void setResultNum(int resultNum) {
		this.resultNum = resultNum;
	}

	
	public String getQueryNum() {
		return queryNum;
	}

	
	public void setQueryNum(String queryNum) {
		this.queryNum = queryNum;
	}

	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getQueryJobId() {
		return queryJobId;
	}

	public void setQueryJobId(String queryJobId) {
		this.queryJobId = queryJobId;
	}


	public String getWayBillNo() {
		return wayBillNo;
	}


	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}


	public String getRequestType() {
		return requestType;
	}


	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
    
}

package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

public class AddWSCWaybillProcessEntity {
	
	//id
	private String id;
	
	//任务号
	private String jobId;
	
	//请求类型
	private String requestType;
	
	//请求时间
	private Date createTime;
	
	//修改时间
	private Date modifyTime;
	
	//请求数据
	private String requestBody;
	
	//失败原因
	private String failReason;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	

}

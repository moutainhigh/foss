package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 待处理发送短信VO
 * 
 * @author WangQianJin
 * @date 2013-5-8 上午9:18:02
 */
public class LaterSpreadDto implements Serializable {
	private static final long serialVersionUID = 1204162860801197116L;
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
	private int queryNum;
	/**
	 * 要查询的JobId
	 */
	private String queryJobId;

	public int getResultNum() {
		return resultNum;
	}

	public void setResultNum(int resultNum) {
		this.resultNum = resultNum;
	}

	public int getQueryNum() {
		return queryNum;
	}

	public void setQueryNum(int queryNum) {
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

}

package com.deppon.foss.module.pickup.waybill.shared.vo;

/**
 * 待处理发送返券VO
 * @author 
 * @date 
 */
public class PendingSendCouponVo {

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
    
}

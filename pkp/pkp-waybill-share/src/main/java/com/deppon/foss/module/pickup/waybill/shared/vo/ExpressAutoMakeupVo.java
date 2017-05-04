package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.util.Date;

/**
 * 生成待激活运单VO
 * @author YangXiaoLong
 * @date 2015-7-8 上午9:18:02
 */
public class ExpressAutoMakeupVo {

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
    /**
	 * 更改jobid的时间优化
	 */
    private Date createTime;
    
    

	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
    
}
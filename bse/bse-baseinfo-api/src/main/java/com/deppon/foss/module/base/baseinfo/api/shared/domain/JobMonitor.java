package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 监控的job信息
 */
public class JobMonitor extends BaseEntity{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 7779290470642571501L;
	/**
     * job所属模块 （BSE,TFR,PKP，STL）
     */
	private String jobModule;
	private String jobName;
	private String jobDesc;
	/**
	 * job预警信息接收人 （默认为微信方式发生预警）
	 */
	private String warningReceiver;
	/**
	 * 预警的阀值
	 */
	private long maxAccumulation;
	/**
	 * 查询job未处理数据量的sql
	 */
	private String querySql;
	/**
	 * 是否生效
	 */
	private String active;
	 private long   dataAccumulation ; 
	public String getJobModule() {
		return jobModule;
	}
	public void setJobModule(String jobModule) {
		this.jobModule = jobModule;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getWarningReceiver() {
		return warningReceiver;
	}
	public void setWarningReceiver(String warningReceiver) {
		this.warningReceiver = warningReceiver;
	}
	public long getMaxAccumulation() {
		return maxAccumulation;
	}
	public void setMaxAccumulation(long maxAccumulation) {
		this.maxAccumulation = maxAccumulation;
	}
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public long getDataAccumulation() {
		return dataAccumulation;
	}
	public void setDataAccumulation(long dataAccumulation) {
		this.dataAccumulation = dataAccumulation;
	}
	
		
}

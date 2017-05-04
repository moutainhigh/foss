package com.deppon.foss.module.base.baseinfo.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 定时任务 数据积累量 记录
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:268984,date:2016-3-24 下午3:18:46,content:TODO </p>
 * @author 268984 
 * @date 2016-3-24 下午3:18:46
 * @since
 * @version
 */
public class JobDataAccumulationRecord extends BaseEntity{

	  /**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 4944316573955740991L;
	 private String  id ;            
	  private String  jobGroup;   
	  private String  jobName;        
	  private String   blongModule;
	  private long  dataAccumulation;/**数据积累量**/
	  private String  statDate;/**统计日期**/
	  private long  latestrecord;/**最新查询到的数据积累量**/
	  private JobMonitor jobMonitor;
	public JobMonitor getJobMonitor() {
		return jobMonitor;
	}
	public void setJobMonitor(JobMonitor jobMonitor) {
		this.jobMonitor = jobMonitor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getBlongModule() {
		return blongModule;
	}
	public void setBlongModule(String blongModule) {
		this.blongModule = blongModule;
	}
	public long getDataAccumulation() {
		return dataAccumulation;
	}
	public void setDataAccumulation(long dataAccumulation) {
		this.dataAccumulation = dataAccumulation;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public long getLatestrecord() {
		return latestrecord;
	}
	public void setLatestrecord(long latestrecord) {
		this.latestrecord = latestrecord;
	}
	
}


package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IJobMonitorService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.JobGridException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.JobMonitorVo;

public class JobMonitorAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   @Autowired
	private IJobMonitorService jobMonitorService;
   	private JobMonitorVo jobMonitorVo =new JobMonitorVo();
	public JobMonitorVo getJobMonitorVo() {
		return jobMonitorVo;
	}
	public void setJobMonitorVo(JobMonitorVo jobMonitorVo) {
		this.jobMonitorVo = jobMonitorVo;
	}
	@JSON
	public String searchMonitorJobByCondition(){
	try{
		List<JobMonitor> jobMonitorList = jobMonitorService.searchMonitorJobByConditionByPage(jobMonitorVo.getJobMonitor(),this.start,this.limit);
	        long total =  jobMonitorService.countMonitorJobBycondition(jobMonitorVo.getJobMonitor());
		//	List<JobMonitor> jobMonitorList = jobMonitorService.searchMonitorJobByCondition(jobMonitorVo.getJobMonitor());
			jobMonitorVo.setJobMonitorList(jobMonitorList);
			this.setTotalCount(total);
			
	}catch (JobGridException e) {
		return this.returnError(e);
	}
	  return this.returnSuccess();	
	}
	@JSON
	public String addMonitorJob(){
		try{
		jobMonitorService.addMonitorJob(jobMonitorVo.getJobMonitor());
		}catch (JobGridException e) {
			return this.returnError(e);
		}
	 return this.returnSuccess();	
	}
	@JSON
	public String modifyMonitorJob(){
		try{
		jobMonitorService.modifyMonitorJob(jobMonitorVo.getJobMonitor());
		}catch (JobGridException e) {
			return this.returnError(e);
		}
	 return this.returnSuccess();	
	}
	
}

package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IJobDataAccumulationService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IJobMonitorService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.JobDataAccumulationVo;
public class JobDataAccumulationAction extends AbstractAction {
    /**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = -3870613439616087117L;
	@Autowired
	private IJobDataAccumulationService jobDataAccumulationService;
	@Autowired
	private IJobMonitorService joMonitorService;
	
	private  JobDataAccumulationVo jobDataAccumulationVo=new JobDataAccumulationVo();
//	private IJobGridService jobGridService;
//	public void setJobGridService(IJobGridService jobGridService) {
//		this.jobGridService = jobGridService;
//	}

	public JobDataAccumulationVo getJobDataAccumulationVo() {
		return jobDataAccumulationVo;
	}

	public void setJobDataAccumulationService(
			IJobDataAccumulationService jobDataAccumulationService) {
		this.jobDataAccumulationService = jobDataAccumulationService;
	}

	public void setJobDataAccumulationVo(JobDataAccumulationVo jobDataAccumulationVo) {
		this.jobDataAccumulationVo = jobDataAccumulationVo;
	}
	
	
	@JSON
	public String queryJobDataAccumulationRecord(){
		
	/*	//List<JobDataAccumulationRecord>  ls = jobDataAccumulationService.queryJobDataAccumulationRecord(jobDataAccumulationVo.getJobGridSchedulesEntity(), jobDataAccumulationVo.getLimitDate());
		Calendar cd = Calendar.getInstance();
		//cd.setTime(new Date());
		Random d = new Random();
		List<JobDataAccumulationRecord>  ls = new ArrayList<JobDataAccumulationRecord>();
       for (int i = 0; i < 30; i++) {
		cd.set(2016, 5, i);
		JobDataAccumulationRecord r = new JobDataAccumulationRecord();
		r.setCreateDate(cd.getTime());
		r.setDataAccumulation(d.nextInt(2000));
		ls.add(r);
		cd.clear();
	}
		//long latestData = jobDataAccumulationService.queryDataAccumulation(jobDataAccumulationVo.getJobGridSchedulesEntity());
		for (JobDataAccumulationRecord record :ls) {
			  //测试数据，--记得修改
			record.setLatestrecord(500);
		}
		jobDataAccumulationVo.setJobDataAccumulationRecord(ls);*/
		
		return this.returnSuccess();
	}
	/**
	 * 根据JobGrids 查询对应的job的数据未处理量
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-4-6 上午10:38:41
	 * @return
	 * @see
	 */
	@JSON
	public String queryJobDataAccumulationByJobGrids(){
	//	List<JobGridSchedulesEntity> jobGridSchedulesEntityList = jobDataAccumulationVo.getJobGridSchedulesEntityList();
		String module = jobDataAccumulationVo.getBlongModule();
		String jobName=null;
		if(jobDataAccumulationVo.getJobGridSchedulesEntity()!=null){
			 jobName =jobDataAccumulationVo.getJobGridSchedulesEntity().getJobName();
		}
		JobGridSchedulesEntity entity = new JobGridSchedulesEntity();
		entity.setBlongModule(module);
		entity.setJobName(jobName);
		List<JobMonitor> jobMonitorList = jobDataAccumulationService.queryDataAccumulationByCondition(entity,this.start,this.limit);
		if(jobMonitorList==null){
			 jobMonitorList = new ArrayList<JobMonitor> ();
			for (int i = 0; i <NumberConstants.NUMBER_10; i++) {
				jobMonitorList.add(new JobMonitor());
			} 
		}else{
			for (int i = NumberConstants.NUMBER_10; i > jobMonitorList.size();) {
				JobMonitor ex = new JobMonitor();
			 jobMonitorList.add(ex);
		}
		}
		JobMonitor jobMonitor = new JobMonitor();
	    jobMonitor.setJobModule(module);
	    jobMonitor.setActive("Y");
	    jobMonitor.setJobName(jobName);
		long total = joMonitorService.countMonitorJobBycondition(jobMonitor);
		jobDataAccumulationVo.setJobMonitorList(jobMonitorList);
		this.setTotalCount(total);
		return this.returnSuccess();
	}
	
	
}

package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IJobDataAccumulationDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IJobMonitorDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IJobMonitorService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.JobGridException;

/**
 * 查询job数据积累量
 * @author 268984
 *
 */
public class JobMonitorService  implements IJobMonitorService{
	@Autowired
	private IJobMonitorDao jobMonitorDao;
	@Autowired
    private IJobDataAccumulationDao jobDataAccumulationDao;
	
	
	@Override
	public List<JobMonitor> searchMonitorJobByCondition(JobMonitor jobmonitor){
		if(jobmonitor==null){
			jobmonitor = new JobMonitor();	
		}
		 List<JobMonitor> jmList = jobMonitorDao.searchMonitorJobByCondition(jobmonitor);
		 return jmList;
	}
	@Override
	public void addMonitorJob(JobMonitor jobMonitor) {
           if(jobMonitor ==null||jobMonitor.getActive()==null||jobMonitor.getMaxAccumulation()==0
        	||jobMonitor.getJobModule() ==null||jobMonitor.getQuerySql() ==null||jobMonitor.getJobDesc()==null  
        		   ){
        	   throw new JobGridException("数据输入不完整！");
           }
           JobMonitor condition = new JobMonitor();
           condition.setJobName(jobMonitor.getJobName());
           condition.setJobModule(jobMonitor.getJobModule());
           List<JobMonitor> results  =  this.searchMonitorJobByCondition( condition);
           if(results!=null&&results.size()>0){
        	   throw new JobGridException("新增失败,任务名称不能重复！");
           }
           String regx = "create |drop |exec | xp_| delete |update | master| truncat |;$ ";
           Pattern pa =Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
         	Matcher ma = pa.matcher(jobMonitor.getQuerySql());
            
         	while(ma.find()){
        	   throw new JobGridException("新增失败,查询脚本不能含有create 、drop 、exec 、 xp_、 delete 、update 、 master、 truncat等关键字！");
        	 }
         //  JobGridSchedulesEntity accCondition = new JobGridSchedulesEntity();
           try{
        	 String sql = jobMonitor.getQuerySql().replace(";", " ");
            jobDataAccumulationDao.queryDataAccumulation(sql);
            jobMonitor.setQuerySql(sql);
           }catch(Exception e){
        	   throw new JobGridException("新增失败,请确认脚本是否正确!");
           }
           jobMonitorDao.addMonitorJob(jobMonitor);
	}

	@Override
	public void modifyMonitorJob(JobMonitor jobMonitor) {
		// TODO Auto-generated method stub
		 if(jobMonitor ==null||jobMonitor.getId()==null){
		        	   throw new JobGridException("数据输入不完整！");
		           }
		if(jobMonitor.getQuerySql()!=null){
			 String regx = "create |drop |exec | xp_| delete |update | master| truncat |;$ ";
	           Pattern pa =Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
	         	Matcher ma = pa.matcher(jobMonitor.getQuerySql());
	           while(ma.find()){
	        	   throw new JobGridException("新增失败,查询脚本不能含有create 、drop 、exec 、 xp_、 delete 、update 、 master、 truncat等关键字！");
	        	 }
		}
		 try{
	            jobDataAccumulationDao.queryDataAccumulation(jobMonitor.getQuerySql());
	           }catch(Exception e){
	        	   throw new JobGridException("修改失败,请确认脚本是否正确!");
	           }
		      jobMonitorDao.modifyMonitorJob(jobMonitor);
	}
	@Override
	public long countMonitorJobBycondition(JobMonitor jobMonitor) {
		// TODO Auto-generated method stub
		if(jobMonitor==null){
			jobMonitor = new JobMonitor();	
		}
		return jobMonitorDao.countMonitorJobBycondition(jobMonitor);
		
	}
	@Override
	public List<JobMonitor> searchMonitorJobByConditionByPage(
			JobMonitor jobMonitor, int start, int limit) {
		if(jobMonitor==null){
			jobMonitor = new JobMonitor();	
		}
		
		 List<JobMonitor> jmList = jobMonitorDao.searchMonitorJobByConditionByPage(jobMonitor,start,limit);
		 return jmList;
	}

}

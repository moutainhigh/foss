package com.deppon.pda.bdm.module.foss.monitor.server.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.pda.bdm.module.core.shared.util.BeanFactory;
import com.deppon.pda.bdm.module.foss.monitor.server.dao.IMonitorDao;

/**
 * 
  * @ClassName MonitorDataClearJob 
  * @Description TODO 监控数据清除
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-8 下午3:24:53
 */
public class MonitorDataClearJob implements Job{
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		IMonitorDao monitorDao = (IMonitorDao) BeanFactory.getBean("monitorDao");
		try{
			//删除昨天的监控记录
			monitorDao.delMonitorInfo();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

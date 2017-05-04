package com.deppon.pda.bdm.module.foss.monitor.server.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.vos.MessgeVo;
import com.deppon.pda.bdm.module.foss.monitor.server.dao.IMonitorDao;

/**
 * 
  * @ClassName MonitorService 
  * @Description TODO 监控业务方法
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-2 下午4:17:04
 */
public class MonitorJob extends Thread{
	private IMonitorDao monitorDao;
	
	private static BlockingQueue<ConcurrentHashMap<String,MessgeVo>> queue = new ArrayBlockingQueue<ConcurrentHashMap<String,MessgeVo>>(1000);
	private static Logger log = Logger.getLogger(MonitorJob.class);
	
	public MonitorJob(){
		this.start();
		//启动JOB
		try{
			SchedulerService.getInstantce().task();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void put(ConcurrentHashMap<String,MessgeVo> map){
		try{
			queue.put(map);
		}catch(Exception e){
			log.error(LogUtil.logFormat(e));
		}
	}
	
	/**
	 * 
	* @Description: TODO 执行入库操作
	* @return void 
	* @author mt hyssmt@vip.qq.com
	* @date 2013-10-2 下午6:10:13
	 */
	public void run(){
		while(true){
			try{
				Thread.sleep(30 * 1000);
				ConcurrentHashMap<String,MessgeVo> msgMap = queue.poll();
				//如果消息队列中有数据，则进行处理
				if(msgMap != null){
					try{
						monitorDao.saveMonitorInfo(msgMap);
					}catch(Exception e){
						log.error(LogUtil.logFormat(e));
					}finally{
						msgMap.clear();
						msgMap = null;
					}
				}
			}
			catch(Exception ex){
				log.error(LogUtil.logFormat(ex));
			}
		}
	}
	
	public void setMonitorDao(IMonitorDao monitorDao) {
		this.monitorDao = monitorDao;
	}
}

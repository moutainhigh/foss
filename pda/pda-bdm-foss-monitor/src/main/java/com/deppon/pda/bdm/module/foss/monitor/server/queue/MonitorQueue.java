package com.deppon.pda.bdm.module.foss.monitor.server.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.monitor.IMonitor;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.util.BeanFactory;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.vos.MessgeVo;
import com.deppon.pda.bdm.module.foss.monitor.server.job.MonitorJob;

/**
 * 
  * @ClassName MonitorQueue 
  * @Description TODO 监控队列
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-1 下午3:45:05
 */
public class MonitorQueue extends Thread implements IMonitor{
	private static Logger log = Logger.getLogger(MonitorQueue.class);
	private ConcurrentHashMap<String,MessgeVo> msgMap = new ConcurrentHashMap<String,MessgeVo>();
	/**
	 * 正常消息队列
	 */
	private static BlockingQueue<MessgeVo> queue = new ArrayBlockingQueue<MessgeVo>(20000);
	
	public MonitorQueue(){
		this.start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try{
						Thread.sleep(60 * 1000);
						//避免重复创建实例
						if(msgMap.size() == 0){
							continue;
						}
						MonitorJob monitorService = (MonitorJob) BeanFactory.getBean(Constant.MONITOR.MONITOR_JOB_NAME);
						ConcurrentHashMap<String,MessgeVo> map = msgMap;
						MessgeVo vo = new MessgeVo();
						vo.setMsgTime(DateUtils.getHHmm());
						map.put(Constant.MONITOR.MONITOR_MSGTIME, vo);
						msgMap = new ConcurrentHashMap<String,MessgeVo>();
						monitorService.put(map);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	/**
	 * 
	* @Description: TODO
	* @param type 业务类型
	* @return void    
	* @author mt hyssmt@vip.qq.com
	* @date 2013-10-2 下午2:50:28
	 */
	public void put(MessgeVo msg){
		try {
			queue.put(msg);
		} catch (Exception e) {
			log.error(LogUtil.logFormat(e));
		}
	}
	
	@Override
	public void run() {
		//将数据进行统计
		while(true){
			try{
				MessgeVo msgVo = queue.take();
				String type = msgVo.getOperType();
				//如果存在该业务类型,则在原来基础上+1,否则为0
				if(msgMap.containsKey(type)){
					MessgeVo vo = msgMap.get(type);
					//正常还是异常消息
					if(vo.getMsgType().equals(Constant.MONITOR.NORMAL)){
						vo.setNormalTotal(vo.getNormalTotal() + 1);
					}else if(vo.getMsgType().equals(Constant.MONITOR.ERROR)){
						vo.setErrTotal(vo.getErrTotal() + 1);
					}
					msgMap.put(type, vo);
				}else{
					//正常还是异常消息
					if(msgVo.getMsgType().equals(Constant.MONITOR.NORMAL)){
						msgVo.setNormalTotal(1);
					}else if(msgVo.getMsgType().equals(Constant.MONITOR.ERROR)){
						msgVo.setErrTotal(1);
					}
					msgMap.put(type, msgVo);
				}
			}catch(Exception e){
				log.error(LogUtil.logFormat(e));
			}
		}
	}
}

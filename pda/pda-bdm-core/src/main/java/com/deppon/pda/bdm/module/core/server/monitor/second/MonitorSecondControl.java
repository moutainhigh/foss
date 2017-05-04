package com.deppon.pda.bdm.module.core.server.monitor.second;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.util.BeanFactory;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;
import com.deppon.pda.bdm.module.core.shared.vos.MessgeVo;

/**
 * 
  * @ClassName MonitorControl 
  * @Description TODO 异常数据填充
  * @author 245955
  * @date 2015-10-18
 */
public class MonitorSecondControl {
	private static Logger log = Logger.getLogger(MonitorSecondControl.class);
	public static void put(AsyncMsg asyncMsg,String msgType){
		try{
			Object obj = BeanFactory.getBean(Constant.MONITOR.MONITOR_QUEUE_NAME);
			if(obj != null){
				MessgeVo msgVo = new MessgeVo();
				//设置业务类型
				msgVo.setOperType(asyncMsg.getOperType());
				msgVo.setMsgType(msgType);
				ISecondMonitor monitor = (ISecondMonitor) obj;
				monitor.put(msgVo);
			}
		}catch(Exception ex){
			log.error(LogUtil.logFormat(ex));
		}
	}
}

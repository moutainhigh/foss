package com.deppon.pda.bdm.module.core.server.monitor;

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
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-3 下午4:02:19
 */
public class MonitorControl {
	private static Logger log = Logger.getLogger(MonitorControl.class);
	public static void put(AsyncMsg asyncMsg,String msgType){
		try{
			Object obj = BeanFactory.getBean(Constant.MONITOR.MONITOR_QUEUE_NAME);
			if(obj != null){
				MessgeVo msgVo = new MessgeVo();
				//设置业务类型
				msgVo.setOperType(asyncMsg.getOperType());
				msgVo.setMsgType(msgType);
				IMonitor monitor = (IMonitor) obj;
				monitor.put(msgVo);
			}
		}catch(Exception ex){
			log.error(LogUtil.logFormat(ex));
		}
	}
}

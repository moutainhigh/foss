package com.deppon.foss.module.pickup.predeliver.bpm;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.bpms.module.shared.domain.BpmsParticipant;
import com.deppon.bpmsapi.module.client.api.IParticipantRuleManager;
import com.deppon.bpmsapi.module.client.domain.ParticipantRule;
import com.deppon.bpmsapi.module.server.api.exception.BPMSException;


/**
 * 参与者规则
 *  @author 136892-foss-李龙根
 *
 */
public class ParticipantRuleManagerImpl implements IParticipantRuleManager{
	/**
	 * 日志文件
	 */
	private static Logger log = LoggerFactory.getLogger(ParticipantRuleManagerImpl.class);
	/**
	 * 获得参与者规则
	* @param rules
	* @param context
	* @return  
	* @see com.deppon.bpmsapi.module.client.api.IParticipantRuleManager#getParticipants(com.deppon.bpmsapi.module.client.domain.ParticipantRule[], java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, BpmsParticipant[]> getParticipants(
			ParticipantRule[] rules, Map context) {
		Map<String, BpmsParticipant[]> res = new HashMap<String, BpmsParticipant[]>();
		//遍历规则数组，进行规则调用
		for (int i = 0; i < rules.length; i++) {
			try {
				//BIZ_类全名与方法名的分离
				String[] temp = rules[i].getRule().split("#");
				// temp[0]类名,temp[1]方法名,通过类的反射调用报账规则类方法
				Class<?> cl = Class.forName(temp[0].replaceAll("BIZ_", ""));
				//报账规则类实例化
				Object obj = cl.newInstance();
				//报账规则类方法获取
				Method me = cl.getMethod(temp[1], new Class[] {ParticipantRule.class, Map.class });
				//报账规则类方法调用
				BpmsParticipant[] bps = (BpmsParticipant[]) me.invoke(obj,new Object[] { rules[i], context });
				//将获取到的参与者添加到返回队列中
				res.put(rules[i].getNextActivityDefId(), bps);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("选人规则"+e);
				throw new BPMSException(e);
			}
		}
		return res;
	}
}
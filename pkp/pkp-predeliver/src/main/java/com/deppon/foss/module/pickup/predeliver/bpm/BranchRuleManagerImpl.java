package com.deppon.foss.module.pickup.predeliver.bpm;

import java.lang.reflect.Method;
import java.util.Map;

import com.deppon.bpmsapi.module.client.api.IBranchRuleManager;
import com.deppon.bpmsapi.module.server.api.exception.BPMSException;

/**
 * 分支规则
 * 
 * @author 136892
 * 
 */
public class BranchRuleManagerImpl implements IBranchRuleManager {

	/**
	 * 
	 * @author 136892-foss-李龙根
	 * @date 2013-8-21 下午3:56:27
	 * @see com.deppon.bpmsapi.module.client.api.IBranchRuleManager#isTrue(java.lang.String[],
	 *      java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean[] isTrue(String[] rule, Map map) {

		boolean[] res = new boolean[rule.length];
		// 遍历规则数组，进行规则调用
		for (int i = 0; i < rule.length; i++) {
			if (rule[i].equals("true") || rule[i].equals("false")) {
				res[i] = Boolean.parseBoolean(rule[i]);
			} else {// 报账规则调用
				boolean result = false;
				try {
					// BIZ_类全名与方法名的分离
					String[] temp = rule[i].split("#");
					// temp[0]类名,temp[1]方法名,通过类的反射调用报账规则类方法
					Class<?> cl = Class.forName(temp[0].replaceAll("BIZ_", ""));
					// 规则类实例化
					Object obj = cl.newInstance();
					// 规则类方法获取
					Method me = cl
							.getMethod(temp[1], new Class[] { Map.class });
					// 规则类方法调用
					result = (Boolean) me.invoke(obj, new Object[] { map });
				} catch (Exception e) {
					throw new BPMSException(e);
				}
				res[i] = result;
			}
		}
		return res;
	}
}

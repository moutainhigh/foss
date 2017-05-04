/**
 * initial comments
 */
/**
 * 
 *Copyright 2013 
 *
 *Licensed under the DEPPON License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.deppon.com/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 *Contributors:
 *038590-foss-wanghui - initial API and implementation
 *
 *PROJECT NAME  : network-mgr
 *
 *package_name  : com.deppon.network.module.mgr.server.bpm
 *
 *FILE NAME    :BranchRuleManagerImpl.java
 *
 *AUTHOR  :  网点规划
 *
 *TIME    :2013-9-16 
 *
 *HOME PAGE    :  http://www.deppon.com
 *
 *COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.util;

import java.lang.reflect.Method;
import java.util.Map;

import com.deppon.bpmsapi.module.client.api.IBranchRuleManager;
import com.deppon.bpmsapi.module.server.api.exception.BPMSException;


/**
 * 分支规则
 * @author 038590-foss-wanghui
 * @date 2013-8-21 下午3:56:05
 */
public class BranchRuleManagerImpl implements IBranchRuleManager{
	
	
	/** 
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-8-21 下午3:56:27
	 * @see com.deppon.bpmsapi.module.client.api.IBranchRuleManager#isTrue(java.lang.String[], java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean[] isTrue(String[] rule, Map map) {
		
		boolean[] res = new boolean[rule.length];
		//遍历规则数组，进行规则调用
		for (int i = 0; i < rule.length; i++) {
			if (rule[i].equals("true") || rule[i].equals("false")) {
				res[i] = Boolean.parseBoolean(rule[i]);
			} else {//报账规则调用
				boolean result = false;
				try {
					//BIZ_类全名与方法名的分离
					String[] temp = rule[i].split("#");
					// temp[0]类名,temp[1]方法名,通过类的反射调用报账规则类方法
					Class<?> cl = Class.forName(temp[0].replaceAll("BIZ_", ""));
					//规则类实例化
					Object obj = cl.newInstance();
					//规则类方法获取
					Method me = cl.getMethod(temp[1], new Class[] { Map.class });
					//规则类方法调用
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

/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: boot/src/main/java/com/deppon/foss/module/boot/client/app/UISecurityManager.java
 * 
 * FILE NAME        	: UISecurityManager.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.boot.client.app;

import java.util.Set;

import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.security.ComponentGuard;
import com.deppon.foss.framework.client.core.security.IAuthorizationDelegate;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
 


/**
 * 客户端界面UI安全控制manager
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:29:14, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:29:14
 * @since
 * @version
 */
public class UISecurityManager {
    	/**
    	 * 定义一个静态的控件保护类对象
    	 */
	private static ComponentGuard componentGuard;
	
	public static ComponentGuard getComponentGuard() {
	    	/**
	    	 * 判断当前类的属性componentGuard是否为空
	    	 */
		if (componentGuard == null) {
		    	/**
		    	 * 创建一个ComponentGuard类的对象
		    	 */
			componentGuard = new ComponentGuard(new UserPermissionAuthorization());
		}
		return componentGuard;
	}
	public static boolean checkPermission(Object permission) {
	    	/**
	    	 * 获取当前用户信息
	    	 */
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		/**
	    	 * 判断user是否为空
	    	 */
		if (user == null) {
		    	/**
		    	 * 如果user为空，则返回false
		    	 */
			return false;
		}
		//服务端组织框架变动
		/**
		 * 修改时间：2012/05/02
		 */
		
		/**
	    	 * 获取当前的FunctionCodes，保存到Set集合
	    	 */
		Set<String> functionSet = SessionContext.getCurrentFunctionCodes();
		/**
	    	 * 判断functionSet是否为空
	    	 */
		if (functionSet == null) {
			return false;	//如果functionSet为空，返回false
		}
		/**
	    	 * 返回一个布尔值（Set集合functionSet是否包含参数permission）
	    	 */
		return functionSet.contains(permission);
		
	}
	private static class UserPermissionAuthorization implements IAuthorizationDelegate{

		@Override
		public boolean check(Object arg0) {
		    	/**
		    	 * 验证权限
		    	 */
			return checkPermission(arg0);
		}
	}
}
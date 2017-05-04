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
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/utills/ExceptionHandler.java
 * 
 * FILE NAME        	: ExceptionHandler.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.utills;

import com.deppon.foss.framework.client.commons.exception.IErrorHandler;
import com.deppon.foss.framework.client.commons.exception.NormalException;
import com.deppon.foss.framework.client.component.exception.ErrorHandler;
import com.deppon.foss.framework.client.core.application.IApplication;
import com.deppon.foss.framework.client.core.context.ApplicationContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 
 * 异常处理类
 * @author 102246-foss-shaohongliang
 * @date 2013-2-1 下午5:31:06
 */
public class ExceptionHandler {

	/**
	 * 
	 * 提示异常信息
	 * @author 102246-foss-shaohongliang
	 * @date 2013-2-1 下午5:32:23
	 */
	public static void alert(Exception ex) {
	    	/**
	    	 * 判断参数ex对象是否是BusinessException的实例
	    	 */
		if(ex instanceof BusinessException){
			//业务异常直接显示
		    	/**
		    	 * 判断错误信息ex.getMessage()是否为空，然后显示错误信息
		    	 */
			if(StringUtil.isEmpty(ex.getMessage())){			    	
				MsgBox.showError(((BusinessException)ex).getErrorCode());
			}else{
				MsgBox.showError(ex.getMessage());
			}
		/**
		 * 判断参数ex对象是否是NormalException的实例
		 */
		}else if(ex instanceof NormalException){
			//GUI提示异常直接显示
			MsgBox.showError(ex.getMessage());
		}else{
			//未知异常采取框架提供的统一方式提示
			IApplication application = ApplicationContext.getApplication();
			/**
			 * 创建IErrorHandler对象errorHandler
			 */
			IErrorHandler errorHandler = new ErrorHandler(application);
			/**
			 * 调用异常处理接口的handle方法，可以根据传入的Throwable类型进行选择性的处理
			 */
			errorHandler.handle(ex);
		}
		
	}

}
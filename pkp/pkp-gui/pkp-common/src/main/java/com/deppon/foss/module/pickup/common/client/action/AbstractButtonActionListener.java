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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/action/AbstractButtonActionListener.java
 * 
 * FILE NAME        	: AbstractButtonActionListener.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.action;

import java.awt.Container;
import java.awt.event.ActionEvent;

import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:surport off line
 * action</small></b> </br> <b
 * style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-4-23 linws 新增 </div>
 ******************************************** 
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractButtonActionListener<T extends Container>
		implements IButtonActionListener {
	/**
	 * 
	 * 功能：actionPerformed
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

	    
	    
	}

	/**
	 * 
	 * 功能：verify
	 * 
	 * @param:
	 * @return boolean
	 * @since:1.6
	 */
	public  boolean verify() {
		
		return true;
	}


	/**
	 * 
	 * 功能：init
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void init() {

	}

	public abstract void setIInjectUI(T ui);

	@SuppressWarnings("unchecked")
	@Override
	public void setInjectUI(Container ui) {
		setIInjectUI((T) ui);
	}

}
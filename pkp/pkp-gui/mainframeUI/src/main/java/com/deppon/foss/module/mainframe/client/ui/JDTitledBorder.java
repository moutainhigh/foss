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
 * FILE PATH        	: mainframeUI/src/main/java/com/deppon/foss/module/mainframe/client/ui/JDTitledBorder.java
 * 
 * FILE NAME        	: JDTitledBorder.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.mainframe.client.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;

import com.deppon.foss.base.util.define.NumberConstants;


/**
 * 
 * 德邦GUI Border样式
 * @author 102246-foss-shaohongliang
 * @date 2013-3-11 上午11:09:48
 */
public class JDTitledBorder extends CompoundBorder{
	

	private static final long serialVersionUID = 2353183664101905050L;

	/**
	 * 
	 * 不带文字的边框样式
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-11 下午6:12:10
	 */
	public JDTitledBorder() {
	    	//执行构造函数
		this("");
	}
	
	/**
	 * 
	 * 带文字，默认灰色边框显示
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-11 下午6:12:10
	 */
	public JDTitledBorder(String title) {
	    	//执行构造函数
		this(title, Color.GRAY);
	}
	
	/**
	 * 
	 * 带文字，自定义边框显示颜色
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-12 上午10:21:22
	 */
	public JDTitledBorder(String title,Color lineColor){
	    	/**
	    	 * 调用父类的构造函数
	    	 */
		super(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),title),
				BorderFactory.createEmptyBorder(0, 0, NumberConstants.NUMBER_4, 0));
	}
	

}
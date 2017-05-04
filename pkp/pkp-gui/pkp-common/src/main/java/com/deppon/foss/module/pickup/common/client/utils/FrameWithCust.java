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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/FrameWithCust.java
 * 
 * FILE NAME        	: FrameWithCust.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.awt.Component;
import java.awt.Dimension;

import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:窗口客户化类，包括对窗口的伸缩等操作</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
*  2012-4-1 linws    新增
* </div>  
********************************************
 */
public class FrameWithCust {

	public FrameWithCust() {
	}

	/**
	 * 
	 * @功能：窗口的伸缩
	 * @参数：component 组件
	 * @返回值：Component
	 * @作者：李凤腾
	 * @日期：2011-6-3
	 */
	public static void flexFrame(Component component) {
		Dimension paneSize = component.getSize();
		Dimension screenSize = component.getToolkit().getScreenSize();
		component.setLocation((screenSize.width - paneSize.width) / 2,
				(int) ((screenSize.height - paneSize.height) * NumberConstants.NUMBER_0_45d));
	}

}
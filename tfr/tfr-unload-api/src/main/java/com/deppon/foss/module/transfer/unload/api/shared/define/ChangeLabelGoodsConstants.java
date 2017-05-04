/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/define/ChangeLabelGoodsConstants.java
 *  
 *  FILE NAME          :ChangeLabelGoodsConstants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.define;

/**
 * 重贴标签货物Constants
 * @author ibm-zhangyixin
 * @date 2012-11-27 上午10:16:51
 */
public class ChangeLabelGoodsConstants {
	
	/**
	 * 处理状态-已处理
	 */
	public static final String HANDLESTATUS_PROCESSED = "PROCESSED";
	/**
	 * 处理状态-未处理
	 */
	public static final String HANDLESTATUS_UNTREATED = "UNTREATED";
	/**发现环节*/
	//清仓
	public static final String FIND_SCENE_STOCK_CHECKING = "STOCK_CHECKING";
	//卸车
	public static final String FIND_SCENE_UNLOAD = "UNLOAD";
	/**更换原因*/
	//手输
	public static final String CHANGE_REASON_BY_HAND = "BY_HAND";
	//有更改
	public static final String CHANGE_REASON_BY_MODIFY = "MODIFY";
}
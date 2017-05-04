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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/BooleanConvertYesOrNo.java
 * 
 * FILE NAME        	: BooleanConvertYesOrNo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import com.deppon.foss.util.define.FossConstants;


public class BooleanConvertYesOrNo {
    /**
     * 
     * 将布尔转换成字符yes 或者no
     * @author 025000-FOSS-helong
     * @date 2012-11-12 下午03:11:35
     */
	public static String booleanToString(Boolean bool) {
		bool = CommonUtils.defaultIfNull(bool);
		if (bool) {
			return FossConstants.YES;
		} else {
			return FossConstants.NO;
		}
	}
    
    /**
     * 
     * 将布尔转换成字符yes 或者no
     * @author 025000-FOSS-helong
     * @date 2012-11-12 下午03:11:35
     */
    public static Boolean stringToBoolean(String value)
    {
    	if(FossConstants.YES.equals(value))
    	{
    	    return true;
    	}else
    	{
    	    return false;
    	}
    }
    
}
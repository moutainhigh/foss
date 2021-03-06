/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-util
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/base/util/define/DateTimeConstants.java
 * 
 * FILE NAME        	: DateTimeConstants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.base.util.define;


/**
 * 定义日期类型的常量类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-26 上午10:46:09</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-26 上午10:46:09
 * @since
 * @version
 */
public class DateTimeConstants {

    /**
     * 默认的"时间"对应的"日期"数据（针对只有时间：例如12:30，需要构造默认的Date为1970-01-01 13:30）
     */
    public static final String DEFAULT_DATE = "1970-01-01";
    
    /**
     * 默认的"日期"对应的"时间"数据（针对只有时间：例如1970-01-01，需要构造默认的Time为00:00:00）
     */
    public static final String DEFAULT_DATE_TIME = "00:00:00";
    
    /**
     * 设置2013-07-01 00:00:00时间常量
     */
    public static final String DEVIDING_DATE = "2013-07-01 00:00:00";
    
}

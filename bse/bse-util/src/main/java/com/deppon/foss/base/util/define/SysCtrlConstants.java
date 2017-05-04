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
 * FILE PATH        	: src/main/java/com/deppon/foss/base/util/define/SysCtrlConstants.java
 * 
 * FILE NAME        	: SysCtrlConstants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.base.util.define;

/**
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:41:34
 * @version 1.0
 */
public class SysCtrlConstants {
    
    /**
     * 查找上级组织时，最大向上找6级
     */
    public static final int ORG_QUERY_RECURRENCE_NUM = 6;
    
    /**
     * 查找上级组织时，最大向上找20级
     */
    public static final int ORG_QUERY_RECURRENCE_NUM_MORE = 20;

    /**
     * 权限互斥判断时，每批权限可传多少个
     */
    public static final int RESOURCE_CONFLICT_JUDGE_BATCH = 512;

}

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
 * PROJECT NAME	: bse-lms-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/util/DataRuleMessageConstant.java
 * 
 * FILE NAME        	: DataRuleMessageConstant.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.util;

import java.io.Serializable;
/**
 * 同步数据中的数据中存在验证的错误信息常量类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-21 下午5:36:44</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-21 下午5:36:44
 * @since
 * @version
 */
public class DataRuleMessageConstant implements Serializable {
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 3339240866523336873L;

    /**
     * 同步对象不存在FOSS系统
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_OBJECTISNULL_ERROR = " ***** 同步的对象不存在于FOSS系统 ***** ";
    
    /**
     * 同步对象已经存在FOSS系统
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_OBJECTISNOTNULL_ERROR = " ***** 同步的对象已经存在于FOSS系统 ***** ";
    
    /**
     * 同步操作动作未约定
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_OPERATE_ERROR = " ***** 同步操作动作未约定 ***** ";
    
    /**
     * 停车计划开始日期
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_PLANBEGINTIME_ERROR = " ***** 停车计划的开始日期字段必填 ***** ";
    
    /**
     * 停车计划结束日期
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_PLANENDTIME_ERROR = " ***** 停车计划的结束日期字段必填 ***** ";
    
    /**
     * 停车原因代码
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_PLANREASONCODE_ERROR = " ***** 停车原因代码字段必填 ***** ";
    
    /**
     * 部门信息不存在
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_DEPARTMENT_ERROR = " ***** 组织机构部门不存在 ***** ";
}

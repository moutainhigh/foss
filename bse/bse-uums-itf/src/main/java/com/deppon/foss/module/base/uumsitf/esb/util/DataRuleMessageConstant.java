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
 * PROJECT NAME	: bse-uums-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/util/DataRuleMessageConstant.java
 * 
 * FILE NAME        	: DataRuleMessageConstant.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.util;

import java.io.Serializable;

/**
 * 同步数据中的数据中存在验证的错误信息常量类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-21 下午5:36:44
 * </p>
 * 
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
     * 同步的对象部门不存在与FOSS系统
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_DEPT_ISNULL_ERROR = " ***** 同步的对象部门不存在于FOSS系统 ***** ";
    
    public static final String DATA_RULE_REASON_DEPT_ISNULL_ERROR_CODE = "100008";

    /**
     * 同步对象不存在FOSS系统
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_OBJECTISNULL_ERROR = " ***** 同步的对象不存在于FOSS系统 ***** ";
    
    public static final String DATA_RULE_REASON_OBJECTISNULL_ERROR_CODE = "100009";

    /**
     * 新增的对象在FOSS系统不存在
     * 
     * @author 087584-foss-lijun
     */
    public static final String DATA_RULE_REASON_OBJECT_NULL_ERROR = " ***** 同步的对象为空或者对象的必填字段为空 ***** ";
    
    public static final String DATA_RULE_REASON_OBJECT_NULL_ERROR_CODE = "100007";

    /**
     * 同步对象已经存在FOSS系统
     * 
     * @author 100847-foss-GaoPeng
     */

    public static final String DATA_RULE_REASON_OBJECTISNOTNULL_ERROR = " ***** 同步的对象已经存在与FOSS系统 ***** ";
    
    public static final String DATA_RULE_REASON_OBJECTISNOTNULL_ERROR_CODE = "100010";

    /**
     * 同步操作动作未约定
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_OPERATE_ERROR = " ***** 同步操作动作未约定 ***** ";
    
    public static final String DATA_RULE_OPERATE_ERROR_CODE = "100006";

    /**
     * 停车计划开始日期
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_PLANBEGINTIME_ERROR = " ***** 停车计划的开始日期字段必填 ***** ";
    
    public static final String DATA_RULE_REASON_PLANBEGINTIME_ERROR_CODE = "100011";

    /**
     * 停车计划结束日期
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_PLANENDTIME_ERROR = " ***** 停车计划的结束日期字段必填 ***** ";
    
    public static final String DATA_RULE_REASON_PLANENDTIME_ERROR_CODE = "100012";

    /**
     * 停车原因代码
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_RULE_REASON_PLANREASONCODE_ERROR = " ***** 停车原因代码字段必填 ***** ";
    
    public static final String DATA_RULE_REASON_PLANREASONCODE_ERROR_CODE = "100013";

    /**
     * 新增的对象在FOSS系统不存在
     * 
     * @author 087584-foss-lijun
     */
    public static final String DATA_RULE_REASON_OBJECT_EXIST_ERROR = " ***** 新增的对象在FOSS系统已存在 ***** ";
    
    public static final String DATA_RULE_REASON_OBJECT_EXIST_ERROR_CODE = "100001";

    /**
     * 修改或者删除的对象在FOSS系统不存在
     * 
     * @author 087584-foss-lijun
     */
    public static final String DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR = "UUMS0104:***** 修改或者删除的对象在FOSS系统不存在 ***** ";

    public static final String DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR_CODE = "100002";
    
    /**
     * 封存的对象在FOSS系统不存在
     * 
     * @author 087584-foss-lijun
     */
    public static final String DATA_RULE_REASON_MOTHBALL_NO_EXIST_ERROR = " ***** 封存的对象在FOSS系统不存在 ***** ";
    
    public static final String DATA_RULE_REASON_MOTHBALL_NO_EXIST_ERROR_CODE = "100003";

    /**
     * 请求操作的对象的标识不存在
     * 
     * @author 087584-foss-lijun
     */
    public static final String DATA_RULE_REASON_IDENTIFY_IS_NULL_ERROR = " ***** 请求操作的对象的标识不存在 ***** ";
    
    public static final String DATA_RULE_REASON_IDENTIFY_IS_NULL_ERROR_CODE = "100004";
}

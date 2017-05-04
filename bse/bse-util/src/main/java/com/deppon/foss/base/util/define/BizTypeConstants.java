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
 * FILE PATH        	: src/main/java/com/deppon/foss/base/util/define/BizTypeConstants.java
 * 
 * FILE NAME        	: BizTypeConstants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.base.util.define;


public class BizTypeConstants {
    
    /**
     * 查询车队类型
     */
    public static final String ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
    
    /**
     * 查询外场类型
     */
    public static final String ORG_TRANSFER_CENTER="TRANSFER_CENTER";
    
    /**
     * 查询空运总调类型
     */
    public static final String ORG_AIR_DISPATCH="AIR_DISPATCH";

    /**
     * 查询事业部类型
     */
    public static final String ORG_DIVISION="DIVISION";
    
    /**
     * 查询大区类型
     */
    public static final String ORG_BIG_REGION="BIG_REGION";
    
    /**
     * 查询小区类型
     */
    public static final String ORG_SMALL_REGION="SMALL_REGION";
    
    /**
     * 快递大区
     */
    public static final String EXPRESS_BIG_REGION="EXPRESS_BIG_REGION";
    
    /**
     * 快递虚拟营业部
     */
    public static final String EXPRESS_SALES_DEPARTMENT="EXPRESS_SALES_DEPARTMENT";
    
    /**
     * 快递点部
     */
    public static final String EXPRESS_PART="EXPRESS_PART";
    
    
    /**
     * 查下级部门
     */

    /**
     * 查询营业部派送部
     */
    public static final String ORG_SALES_DEPARTMENT="SALES_DEPARTMENT";
    
    /**
     * 查询车队组
     */
    public static final String ORG_TRANS_TEAM="TRANS_TEAM";
    /**
     * 查询车队调度组
     */
    public static final String ORG_DISPATCH_TEAM="DISPATCH_TEAM";
    /**
     * 查询经营本部
     */
    public static final String ORG_IS_MANAGE_DEPARTMENT="IS_MANAGE_DEPARTMENT";

    
    //==================================================================
    // 特殊的部门类型
    
    /**
     * 查询 派送排单部门
     */
    public static final String ORG_IS_DELIVER_SCHEDULE="IS_DELIVER_SCHEDULE";
    
    //==================================================================
    /**
     * 中转组：
     * 
     */

    /**
     * 中转-装车
     */
    public static final String ORG_P_ARRANGE_BIZ_TYPE_LOAD="ORG_TFR_LOAD";
    
    /**
     * 中转-卸车
     */
    public static final String ORG_P_ARRANGE_BIZ_TYPE_UNLOAD="ORG_TFR_UNLOAD";

    /**
     * 中转-装卸车
     */
    public static final String ORG_P_ARRANGE_BIZ_TYPE_LOAD_AND_UNLOAD="ORG_TFR_LOAD_AND_UNLOAD";

    /**
     * 中转-派送装卸车
     */
    public static final String ORG_P_ARRANGE_BIZ_TYPE_DELIVER_LOAD="ORG_TFR_DELIVER_LOAD";
    
    //==================================================================
    /**
     * 结算小票-主营业务收入
     */
    public static final String STL_OTHERREVENUE_INCOME_TYPE_PRIMARY="PRIMARY";
    
    /**
     * 结算小票-非主营业务收入
     */
    public static final String STL_OTHERREVENUE_INCOME_TYPE_SECONDARY="SECONDARY";
}

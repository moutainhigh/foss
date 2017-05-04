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
 * FILE PATH        	: src/main/java/com/deppon/foss/base/util/define/ColumnConstants.java
 * 
 * FILE NAME        	: ColumnConstants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.base.util.define;

public class ColumnConstants {

    public static final String ROLE_RESOURCE_OF_ROLE_CODE = "ROLE_CODE";

    // 导出最大量
    public static final int EXPORT_MAX_NUM = 2000;

    // 月台导出
    public static final String EXPORT_PLATFORM_NAME = "月台信息表.xls";

    // 线路导出
    public static final String EXPORT_LINE_NAME = "线路信息表.xls";

    // 到达线路导出名称
    public static final String EXPORT_DESTINATION_LINE_NAME = "到达线路信息表.xls";

    // 始发线路导出名称
    public static final String EXPROT_ORIGINATING_LINE_NAME = "始发线路信息表.xls";
    
    // 在线通知导出名称
    public static final String EXPROT_ONLINEMSG_NAME = "在线通知信息表.xls";

    // 小区导出名称
    public static final String SMALL_ZONE_NAME = "小区信息.xls";

    // 小区导出表头集合
    public static final String[] SMALL_ZONE_TITLE = { "集中接送货小区编码", "集中接送货小区名称","管理部门", "所属大区" };

    // 偏线代理网点名称
    public static final String EXPROT_VEHICLE_AGENCYDEPT_NAME = "偏线代理网点.xls";

    // 偏线代理公司名称
    public static final String EXPROT_VEHICLE_AGENCYCOM_NAME = "偏线代理公司.xls";
    // 大区导出名称
    public static final String EXPRESS_BIG_ZONE_NAME = "大区信息.xls";
    // 快递到达线路导出名称
    public static final String EXPORT_EXPRESS_DESTINATION_LINE_NAME = "快递到达线路信息表.xls";
    // 快递始发线路导出名称
    public static final String EXPROT_EXPRESS_ORIGINATING_LINE_NAME = "快递始发线路信息表.xls";
    // 库区信息导出文件名称
    public static final String EXPROT_GOODS_AREA_FILE_NAME = "库区信息.xls";
    // 合并退款客户信息表导出
    public static final String EXPORT_CODREFUND_NAME = "合并退款客户信息表.xls";
    
    public static final String EXPORT_SOURCE_CATEGORY = "行业货源类别基础信息.xls";
 
    // 快递派送电子地图管理信息表导出
    public static final String EXPORT_EXPRESSDELIVERY_MAPMANAGE_NAME = "快递派送电子地图管理信息表.xls";
    //提前预警
    public static final String EXPORT_EARLY_WARNING = "提前预警线路信息.xls";

    public static final String EXPORT_WAYBILLINFO_NAME = "家装运单交接明细.xls";
 // 重分类基础信息导出名称
    public static final String CLASSIFIED_INCOME_NAME = "重分类基础信息.xls";
    
    /**
     * 导出进仓地址文件名
     */
    public static final String EXPROT_DEPOT_ADDRESS_NAME = "进仓地址信息.xls";
}

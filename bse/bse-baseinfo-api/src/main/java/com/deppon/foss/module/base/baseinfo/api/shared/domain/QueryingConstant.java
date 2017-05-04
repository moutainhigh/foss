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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/domain/QueryingConstant.java
 * 
 * FILE NAME        	: QueryingConstant.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.shared
 * FILE    NAME: QueryingConstant.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

// TODO: Auto-generated Javadoc
/**
 * 综合查询常量类.
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-25 上午9:01:12
 */
public class QueryingConstant {

    /**
     * querying的MYBATIS配置文件命名空间的前缀：
     */
    public static final String MYBATIS_NAMESPACE_BASEINFO_PREFIX = "foss.bse.bse-querying";

    // 车次
    /** The Constant FREIGTHTYPE_VEHICLENO. */
    public static final String FREIGTHTYPE_VEHICLENO = "vehicleAssembleNo";

    // 正单号
    /** The Constant FREIGTHTYPE_AIRWAYBILLNO. */
    public static final String FREIGTHTYPE_AIRWAYBILLNO = "airWaybillNo";

    // 联系方式--发货人手机
    /** The Constant CONTACTWAY_SHIPPERMOBILE. */
    public static final String CONTACTWAY_SHIPPERMOBILE = "shipperMobile";

    // 联系方式--发货人电话
    /** The Constant CONTACTWAY_SHIPPERTEL. */
    public static final String CONTACTWAY_SHIPPERTEL = "shipperTel";

    // 联系方式--收货人手机
    /** The Constant CONTACTWAY_RECEIVERMOBILE. */
    public static final String CONTACTWAY_RECEIVERMOBILE = "receiverMobile";

    // 联系方式--收货人电话
    /** The Constant CONTACTWAY_RECEIVERTEL. */
    public static final String CONTACTWAY_RECEIVERTEL = "receiverTel";

    // 联系人类型--发货人
    /** The Constant CONTACTMANTYPE_SHIPPER. */
    public static final String CONTACTMANTYPE_SHIPPER = "shipper";

    // 联系人类型--收货人
    /** The Constant CONTACTMANTYPE_REVEIVER. */
    public static final String CONTACTMANTYPE_REVEIVER = "reveiver";

    public static final Long THIRTYDAYAFATER_TIMEMILLIS = 30 * 24 * 60 * 60
	    * 1000L;

    // 营业清单 接口 集合 标识符
    public static final String SALESLIST = "salesList";

    // 营业清单 接口 结果 标识符
    public static final String RESULT = "result";

    // 营业清单 接口 总数 标识符
    public static final String COUNT = "count";

    // 营业报表导出名称
    public static final String SALES_STATEMENT_NAME = "营业报表.xls";
    
    //标签打印记录导出名称
    public static final String LABEL_PRINTING_NAME = "标签打印记录.xls";
    
    //跟踪记录导出名称
    public static final String TRACK_RECORDS_NAME = "跟踪记录.xls";
    
    //综合信息查询  ：跟踪类别
    public static final String QUERYING_REMARK = "查询备注";
    
  //综合信息查询  ：跟踪类别
    public static final String ONLINE_TRACKING = "在线跟踪";
    
  //定人定区导出名称
    public static final String REGION_VEHICLE_NAME = "定人定区.xls";
    
    public static final String YES = "是";
    
    public static final String NO = "否";

    // 营业清单 导出 表头集合
    public static final String[] SALES_STATEMENT_ENTITY_TITLE = { "PDA制单时间","FOSS制单时间","是否PDA开单",
	    "运单号", "目的站", "货物总件数", "货物总重量", "预付金额", "货物尺寸", "发货客户联系人","发货人手机","发货人电话", "计费重量",
	    "货物总体积", "包装手续费", "到付金额", "代收货款", "代收货款手续费", "退款金额", "开单送货费",
	    "到付送货费", "开单费","装卸费", "收货部门(出发部门)", "对账部门", "制单部门", "货物名称", "货物包装",
	    "收货客户名称","收货人手机","收货人电话", "收货具体地址", "签收人", "保险费", "保价声明价值", "开单付款方式", "运输类型",
	    "开单人", "运费计费费率", "提货方式", "储运事项", "公布价运费","司机工号","司机姓名"};

    // 营业清单 导出 表头集合
    public static final String[] SALES_STATEMENT_TITLE = { "收货日期", "单号", "目的站",
	    "件数_", "重量_", "预付金额_", "尺寸", "发货人", "计费重量_", "体积_", "包装费_", "运费_",
	    "到付金额_", "代收货款_", "代收货款手续费_", "退款金额_", "开单送货费_", "到付送货费_", "开单金额_",
	    "收货部门", "对账部门", "制单部门", "货物名称", "包装", "收货人", "收货人地址", "签收人", "类型",
	    "保险费_", "保险价值_", "付款方式", "运输性质", "制单人", "运价", "提货方式", "储运事项" };
    //导出列表 totalHead
    public static final String[] SALES_STATEMENT_TOTALHEAD_TITLE = {"总票数","总件数",
	    "总重量", "总预付金额", "总体积", "到付金额", "总代收货款", "总包装费", "收入总额" };

    // 营业报表 汇总 实体
    public static final String TOTAL_FEE_DTO = "totalFeeDto";

    // 营业报表 汇总 字符串
    public static final String TOTAL_STATEMENT = "汇总";

    // 营业报表 空字符串
    public static final String STRING_EMPTY = "";
    // 自定义查询 方案修改 标识 新增
    public static final String ADD_CON = "addCon";
    // 自定义查询 方案修改 标识 修改
    public static final String UPDATE_CON = "updateCon";
    // 自定义查询 方案修改 标识 删除
    public static final String DEL_CON = "delCon";
    // 拼接SQL中1=1
    public static final String SQL_PREFIXION = " WHERE 1=1 ";
    // 拼接SQL中单引号
    public static final String SQL_LSQUO = "'";
    // 拼接SQL中时间 开始
    public static final String SQL_DATE_PREFIXION = "TO_DATE('";
    // 拼接SQL中时间 结束
    public static final String SQL_DATE_END = "','YYYY-MM-DD HH24:MI:SS')";
    // 拼接SQL中LIKE 开始
    public static final String SQL_LIKE_PREFIXION = " '%";
    // 拼接SQL中LIKE 结束
    public static final String SQL_LIKE_END = "%'";
    // 空格
    public static final String SQL_BLANK_STR = " ";
    // 字段类型 STRING
    public static final String C_STRING = "C_STRING";
    // 字段类型 DATE
    public static final String C_DATE = "C_DATE";
    // 字段类型 NUMBER
    public static final String C_NUMBER = "C_NUMBER";
    // 标识为 数据字典
    public static final String IS_DICT = "IS_DICT";
    // 运单标记情况 JJ：紧急
    public static final String MARK_JJ = "JJ";
    // 运单标记情况 FCJJ：非常紧急
    public static final String MARK_FCJJ = "FCJJ";
}

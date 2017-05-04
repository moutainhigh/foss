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
 * FILE PATH        	: src/main/java/com/deppon/foss/base/util/ComnConst.java
 * 
 * FILE NAME        	: ComnConst.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.base.util;


/**
 * 用来放一些常量
 * @author 087584-lijun
 * @date 2012-10-12 上午10:47:18
 */
public class ComnConst {
	
	  /**
     * baseinfo的mybatis配置文件命名空间的前缀：
     */
    public static final String MYBATIS_NAMESPACE_DICT_DEPO="foss.bse.bse-degreePostion";
    /**
     * baseinfo的mybatis配置文件命名空间的前缀：
     */
    public static final String MYBATIS_NAMESPACE_BASEINFO_PREFIX="foss.bse.bse-baseinfo";

    /**
     * dict的mybatis配置文件命名空间的前缀：
     */
    public static final String MYBATIS_NAMESPACE_DICT_PREFIX="foss.bse.bse-dict";
    
    /**
     * 作为外部系统（LMS）同步数据到FOSS时的版本控制中的默认修改/创建的用户（modifyUser/createUser）
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String EXTERNAL_SYSTEM_LMS_USER_ACCOUNT = "lms";
    
    /**
     * 作为外部系统（UUMS）同步数据到FOSS时的版本控制中的默认修改/创建的用户（modifyUser/createUser）
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT = "uums";
    
    /**
     * 资产归属类型：公司
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String ASSETS_OWNERSHIP_TYPE_COMPANY = "Company";
    
    /**
     * 资产归属类型：外请
     * 
     * @author 100847-foss-GaoPeng
     */
    public static final String ASSETS_OWNERSHIP_TYPE_LEASED = "Leased";
    
    /**
     * 集中接送货大、小区
     * 大小区性质：大区
     * @author dp-xieyantao
     */
    public static final String REGION_NATURE_BQ = "BQ";
    
    /**
     * 集中接送货大、小区
     * 大小区性质：小区
     * @author dp-xieyantao
     */
    public static final String REGION_NATURE_SQ = "SQ";
    
    // 数字常量1000,换算单位用
    public static final Long NUMBER1000 = 1000L;
    
    //行政组织根节点的全路径
    public static final String ORG_ROOT_FULLPATH = ".103";
    //财务组织根节点的全路径
    public static final String FINANCIAL_ORG_ROOT_FULLPATH = "DBWL";
    // 集中接送货小区导出数据表头
    public static final String[] PICKUP_SMALLZONE_TITLE = {"集中接送货小区编码", "集中接送货小区名称", "管理部门", "所属大区","区域类型","小区面积（单位平方千米）","导航距离（单位千米）"};
    // 月台导出数据表头
//    public static final String[] PLATFORM_TITLE = {"外场名称", "外场编号", "月台编号", "是否有升降台", "位置", "高度", "宽度", "最大可停靠车型编码", "最大可停靠车型名称", "备注"};
    public static final String[] PLATFORM_TITLE = {"外场名称", "外场编号", "月台编号", "升降台", "高度（米）", "宽度（米）","可停靠车型","位置", "月台类型","横坐标","纵坐标", "备注"};
//	月台类型
    public static final String[] PLATFORM_TYPE = {"长途","短途","接送货"};
    // 运作线路导出数据表头
    public static final String[] LINE_TRANSFER_TITLE = {"线路名称", "线路简码", "线路类型", "出发站", "出发城市", "到达站", "到达城市", "线路距离（公里）", "管理部门","班次", "出发时间", "卡车时效", "普车时效", "到达时间（卡车）", "到达时间（普车）", "中转到达货最晚到达时间", "最后修改人", "最后修改时间", "备注"};
    // 始发和到达线路导出数据表头
    public static final String[] LINE_SOURCE_TARGET_TITLE = {"线路名称", "线路简码", "运输类型", "出发站", "出发城市", "到达站", "到达城市", "线路距离（公里）", "管理车队","班次","出发时间","运行时长","到达时间","最后修改人","最后修改时间","备注","默认始发线路"};
    // 偏线代理网点导出数据表头
    public static final String[] VEHICLE_AGENCY_DEPT_TITLE = {"代理网点编码", "代理网点名称", "代理公司虚拟编码", "代理公司名称", "管理部门编码", "管理部门名称", "代理网点简称", "区县编码", "区县名称", "提货网点编码", "省份编码", "省份名称", "城市编码", "城市名称", "网点地址", "是否可自提","是否送货上门", "是否支持返回签单", "是否支持货到付款", "是否支持代收货款", "自提区域", "派送区域", "可出发", "可中转", "可到达", "联系电话","联系人手机","备注", "拼音", "是否机场"};
    // 偏线代理公司导出数据表头
    public static final String[] VEHICLE_AGENCY_COM_TITLE = {"代理公司编码", "管理部门编码", "管理部门名称", "代理公司名称", "代理简称", "省份编码", "省份名称", "城市编码", "城市名称", "联系地址", "联系电话", "联系人", "联系人电话", "备注"};
    //在线通知表头
    public static final String[] VEHICLE_OLINEMSG_TITLE = {"运单号", "起草部门"," 起草人", "起草时间", "通知内容", "受理状态", "受理备注", "受理部门", "受理人", "受理时间"};
    // 定人定区 最父节点 id[前端对应变量：baseinfo.regionalVehicleIndex.rootParentId]
    public static final String rootParentId = "0";
    //快递派送小区
    public static final String[] EXPRESS_SMALLZONE_TITLE = {"快递收派小区编码", "快递收派小区名称", "管理部门", "所属大区","小区面积（单位平方千米）","小区类型","主责快递员"};
    //快递派送大区
    public static final String[] EXPRESS_BIGZONE_TITLE = {"快递收派大区编码", "快递收派大区名称", "管理部门"};
    //行业货源类别基础资料
    public static final String[] SOURCE_CATEGORY = {"行业货源品类","行业货源品名"};
    
    //提前预警
    public static final String[] EARLY_WARNING = {"出发城市编码","出发城市名称","到达城市编码","到达城市名称","操作票数","兑现票数","兑现率"};
    // 定人定区 最父节点 id
    public static final String parentId = "PARENTID";
    //家装运单明细
    public static final String[] WAYBILL_INFO_TITLE={"运单号","开单件数","供应商","供应商订单号","提货方式","货物名称","重量(KG)","体积","交接确认时间","是否有破损","破损备注","货物品类","提货人身份证号"};
    /**
     * 营业报表清单 清单类型
     * @author FOSS-LIXUEXING
     */
    // 营业报表
    public static final String BUSINESS_REPORT = "BUSINESS_REPORT";
    // 操作清单
    public static final String OPERATION_LIST = "OPERATION_LIST";

    // 同步外场信息到LMS时需要同步大月台(可停靠车长大于等于9.6米的月台个数)个数
    // 和小月台(可停靠车长小于9.6米的月台个数)个数，
    // 此常量用来定义9米6月台的车型
    public static final String SYNC96 = "CX00007";
    
    /**
     * 导出数据常量，月台等
     */
    public static final String YES = "有";
    /**
     * 导出数据常量，月台等
     */
    public static final String NO = "无";
    
    // 网点组用，表示出发营业部
    public static final String ORG_TYPE_SOURCE = "SOURCE";
    // 网点组用，表示到达营业部
    public static final String ORG_TYPE_TARGET = "TARGET";
    
    //营业部自提派送区域扩展描述类型-自提类型
    public static final String SALES_DEPARTMENT_DESC_EXPAND_TYPE_PICKUP="PICKUP";
    //营业部自提派送区域扩展描述类型-自提类型
    public static final String SALES_DEPARTMENT_DESC_EXPAND_TYPE_DELIVERY="DELIVERY";
    
    //字符集格式常量
    public static final String STRING_TYPE_UTF8="UTF-8";
    //营业部自提派送区域扩展描述类型-快递自提类型
    public static final String SALES_DEPARTMENT_DESC_EXPAND_TYPE_EXPRESS_PICKUP ="EXPRESS_PICKUP";
    //营业部自提派送区域扩展描述类型-快递派送类型
    public static final String SALES_DEPARTMENT_DESC_EXPAND_TYPE_EXPRESS_DELIVERY ="EXPRESS_DELIVERY";
    
    // 库区导出数据表头
    public static final String[] GOODS_AREA_TITLE = {"外场编号", "外场名称", "库区编号", "库区名称", "库区类型", "货物类型", "目的站", "库区类别", "计票方式", "星标", "备注", "库区面积", "高度","横坐标","纵坐标", "月台号", "距月台距离"};
	/**
     * 定义和市场活动相关的枚举类型常量
     * 078816_wp_20140416
     */
	//第一行业（活动相关）
    public static final String  FIRST_TRADES_MARK_ACTIVITY = "FIRST_TRADES";
    //第二行业
    public static final String  SECOND_TRADES_MARK_ACTIVITY = "SECOND_TRADES";
    //开单品明
    public static final String  GOODS_NAME_MARK_ACTIVITY = "GOODS_NAME";
    //订单来源
    public static final String  ORDER_RESOURCE_MARK_ACTIVITY = "ORDER_RESOURCE";
    //产品类型
    public static final String  PRODUCT_TYPE_MARK_ACTIVITY = "PRODUCT_TYPE";
    
    public static final int SINGLE_ASYN_EXPORT_FILE_MAX_SIZE = 80000;
    
    public static final int SINGLE_SYNC_EXPORT_FILE_MAX_SIZE = 20000;
    
    /**
     * 定义接口同步时的 操作类型
     */
    //新增
    public static final String SERVICE_OPERATE_ADD ="ADD";
    //作废
    public static final String SERVICE_OPERATE_DELETE ="DELETE";
    //修改
    public static final String SERVICE_OPERATE_MODIFY ="UPDATE";
    
    //代收货款打包退款导出数据表头
    public static final String[] COD_REFUND_TITLE = {"部门名称", "客户编号", "期限", "操作员", "录入时间"};
    //快递派送电子地图管理导出数据表头
    public static final String[] EXPRESSDELIVERY_MAPMANAGE_TITLE = {"营业部名称", "审核状态", "行政区域", "快递员人数",
    				"营业部服务面积(km²)","申请人","申请时间","审核人","审核时间"};
    
  //重分类基础信息
    public static final String[] CLASSIFIED_INCOME_TITLE = {"产品类型名称", "产品类型编码", "所属子公司名称", "所属子公司编码","6%比例","11%比例","开始时间","修改时间","是否有效"};

    /**
     * 进仓信息表头
     */
    public static final String[] DEPOT_ADDRESS_TITLE = {"仓库名称", "仓库状态", "仓库类型", "省份code", "省份", "城市code", "城市", "区县code", "区县","街道地址","经度","纬度","进仓备注","营业部code","营业部","退回原因","确定时间"};
}

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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/define/DictionaryValueConstants.java
 * 
 * FILE NAME        	: DictionaryValueConstants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.define;
/**
 * 数据字典"值代码"常量
 * 
 * 后台使用的固定判断业务逻辑中使用的数据字典"值代码"常量
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-26 上午9:45:16</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-26 上午9:45:16
 * @since
 * @version
 */
public class DictionaryValueConstants {
    
    /**
     * 白名单是否验证CODE
     * @author 094463-foss-xieyantao
     */
    public static final String MAC_WHITE_IS_VALIDATE="MAC_WHITE_IS_VALIDATE";
    /**
     * 浏览器登陆限制CODE
     */
    public static final String BROWSER_LOGIN_VALIDATE ="BROWSER_LOGIN_VALIDATE";

    /********************************************* "车辆类型"值代码 *********************************************/
    /**
     * "拖头"的"值代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String VEHICLE_TYPE_TRACTORS="vehicletype_tractors";
    
    /**
     * "挂车"的"值代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String VEHICLE_TYPE_TRAILER="vehicletype_trailer";
    
    /**
     * "厢式车"的"值代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String VEHICLE_TYPE_VAN="vehicletype_van";
    
    /**
     * "骨架车"的"值代码"
     * @author 187862-dujunhui
     */
    public static final String VEHICLE_TYPE_RQSVC="vehicletype_rqsvc";
    
    
    /************************************* "外请白名单（司机、车）" 白名单状态值代码 ************************************/
    /**
     * "外请车、司机白名单"的白名单状态为"未入库"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_AUDIT_APPLY="audit_apply";
    /**
     * "外请车、司机白名单"的白名单状态为"未审核"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_AUDIT_APPLY_ING="audit_apply_ing";
    /**
     * "外请车、司机白名单"的白名单状态为"可用"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_AUDIT_AVAILABLE="audit_available";
    
    /**
     * "外请车、司机白名单"的白名单状态为"不可用"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_AUDIT_UNAVAILABLE="audit_unavailable";
    
    
    /************************************* "外请白名单（司机、车）" 当前申请值代码  ************************************/
    /**
     * "外请车、司机白名单"的当前申请为"申请入库"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_APPLY_APPLY="apply_apply";
    /**
     * "外请车、司机白名单"的当前申请为"申请修改净空"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_APPLY_UPDARE_SELFVOLUME="apply_selfVolume";
    /**
     * "外请车、司机白名单"的当前申请为"申请可用"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_APPLY_AVAILABLE="apply_available";
    
    /**
     * "外请车、司机白名单"的当前申请为"申请不可用"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_APPLY_UNAVAILABLE="apply_unavailable";
    
    /************************************ "外请白名单类型（司机、车）的"值代码 ****************************************/
    /**
     * "外请白名单"的类型为"外请司机白名单"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_TYPE_DRIVER="BES_WHITELISTS_TYPE_DRIVER";
    
    /**
     * "外请白名单"的类型为"外请车白名单"
     * @author 100847-foss-GaoPeng
     */
    public static final String LEASEDWHITELISTS_TYPE_VEHICLE="BES_WHITELISTS_TYPE_VEHICLE";
    
    /******************************** "数据"的所属类型（公司的、外请的）"的值代码 ***************************************/
    /**
     * "数据"的所属类型为"公司"
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_OWNERSHIP_TYPE_COMPANY="ownership_company";
    
    /**
     * "数据"的所属类型为"外请"
     * @author 100847-foss-GaoPeng
     */
    public static final String DATA_OWNERSHIP_TYPE_LEASED="ownership_leased";
    
    /******************************** "车辆使用类型（接送货车、物流班车、长途车、后勤车）"的"的值代码 ***************************************/
    /**
     * "车辆使用类型"为"接送货车"
     * @author 100847-foss-GaoPeng
     */
    public static final String BES_VEHICLE_USED_TYPE_PKP="used_type_pkp";
    
    /**
     * "车辆使用类型"为"物流班车"
     * @author 100847-foss-GaoPeng
     */
    public static final String BES_VEHICLE_USED_TYPE_BUS="used_type_bus";
    
    /**
     * "车辆使用类型"为"长途车"
     * @author 100847-foss-GaoPeng
     */
    public static final String BES_VEHICLE_USED_TYPE_COACH="used_type_coach";
    
    /**
     * "车辆使用类型"为"后勤车"
     * @author 100847-foss-GaoPeng
     */
    public static final String BES_VEHICLE_USED_TYPE_SERVICE="used_type_service";
    
    /******************************** "单双桥类型"的"的值代码 ***************************************/
    /**
     * "车辆使用类型"为"单桥"
     * @author 100847-foss-GaoPeng
     */
    public static final String VEHICLE_BRIGE_TYPE_SINGLE="brige_single";
    
    /**
     * "车辆使用类型"为"双桥"
     * @author 100847-foss-GaoPeng
     */
    public static final String VEHICLE_BRIGE_TYPE_TWINBRIDGE="brige_twinbridge";
    
    /**
     * "车辆使用类型"为"假双桥"
     * @author 100847-foss-GaoPeng
     */
    public static final String VEHICLE_BRIGE_TYPE_PHONYBRIDGE="brige_phonybridge";
    
    /************************************* "LMS同步的挂车类型" 的值代码  ************************************/
    /**
     * "铁皮柜"的"词条代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String LMS_TRAILER_TYPE_ALGAM="trailer_type_algam";
    /**
     * "玻璃柜"的"词条代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String LMS_TRAILER_TYPE_GLASS="trailer_type_glass";
    
    /************************************* "空运" 的值代码  ************************************/
    /**
     * "早班"的"词条代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String FLIGHT_TYPE_MORNING="MORNING_FLIGHT";   
    
    /**
     * "中班"的"词条代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String FLIGHT_TYPE_MIDDLE="MIDDLE_FLIGHT";
    
    /**
     * "晚班"的"词条代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String FLIGHT_TYPE_NIGHT="NIGHT_FLIGHT";
    
    /**
     * "中转"的"词条代码"
     * @author 100847-foss-sz
     */
    public static final String FLIGHT_TYPE_TRANSFER="TRANSFER_FLIGHT";
    
    /**
     * "客机"的"词条代码"
     * @author 100847-foss-GaoPeng
     */
    public static final String AIRPLANE_TYPE_AIRLINER="airplane_airliner";
    
    /**
     * "货机"的"词条代码"
     * @author 100847-foss-GaoPeng
     */
 
    public static final String AIRPLANE_TYPE_FREIGHTER="airplane_freighter";
    
    /************************************* "权限所属系统类型" 的值代码  ************************************/
    /**
     * 综合管理-权限-权限所属系统类型-WEB
     * @author foss-zhangjiheng
     */
    public static final String BSE_RESOURCE_BELONG_SYSTEM_TYPE_WEB="WEB";
    
    /**
     * 综合管理-权限-权限所属系统类型-GUI
     * @author foss-zhangjiheng
     */
    public static final String BSE_RESOURCE_BELONG_SYSTEM_TYPE_GUI="GUI";
    
    /**
     * 综合管理-线路-始发线路
     * @author foss-zhujunyong
     */
    public static final String BSE_LINE_SORT_SOURCE = "BSE_LINE_SORT_SOURCE";

    /**
     * 综合管理-线路-到达线路
     * @author foss-zhujunyong
     */
    public static final String BSE_LINE_SORT_TARGET = "BSE_LINE_SORT_TARGET";

    /**
     * 综合管理-线路-中转到中转线路
     * @author foss-zhujunyong
     */
    public static final String BSE_LINE_SORT_TRANSFER = "BSE_LINE_SORT_TRANSFER";

    
    /**
     * 综合管理-线路-中转到中转线路-专线
     * PDA组已确定使用此值，不宜变更
     * @author foss-zhujunyong
     */
    public static final String BSE_LINE_TYPE_ZHUANXIAN = "ZX";
    /**
     * 综合管理-线路-中转到中转线路-偏线
     * PDA组已确定使用此值，不宜变更
     * @author foss-zhujunyong
     */
    public static final String BSE_LINE_TYPE_PIANXIAN = "PX";
    /**
     * 综合管理-线路-中转到中转线路-空运
     * PDA组已确定使用此值，不宜变更
     * @author foss-zhujunyong
     */
    public static final String BSE_LINE_TYPE_KONGYUN = "KY";

    /**
     * 综合管理-线路-始发到达线路-汽运
     * @author foss-zhujunyong
     */
    public static final String BSE_LINE_TRANSTYPE_QIYUN = "TRANS_VEHICLE";
    /**
     * 综合管理-线路-始发到达线路-空运
     * @author foss-zhujunyong
     */
    public static final String BSE_LINE_TRANSTYPE_KONGYUN = "TRANS_AIRCRAFT";

    
    
    
    /**
     * 综合管理-库区-库区类型-异常货库区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_EXCEPTION = "BSE_GOODSAREA_TYPE_EXCEPTION";
    /**
     * 综合管理-库区-库区类型-贵重物品库区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_VALUABLE = "BSE_GOODSAREA_TYPE_VALUABLE";
    /**
     * 综合管理-库区-库区类型-包装库区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_PACKING = "BSE_GOODSAREA_TYPE_PACKING";
    /**
     * 综合管理-库区-库区类型-偏线库区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_OTHER = "BSE_GOODSAREA_TYPE_OTHER";
    /**
     * 综合管理-库区-库区类型-驻地派送货区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_STATION = "BSE_GOODSAREA_TYPE_STATION";
    /**
     * 综合管理-库区-库区类型-快递驻地派送货区
     * @author foss-lijie
     */
    public static final String BSE_GOODSAREA_TYPE_EXPRESS_STATION = "BSE_GOODSAREA_TYPE_EXPRESS_STATION";
    /**
     * 综合管理-库区-库区类型-混装货区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_COMMON = "BSE_GOODSAREA_TYPE_COMMON";
    /**
     * 综合管理-库区-库区类型-卡货货区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_FAST = "BSE_GOODSAREA_TYPE_FAST";
    /**
     * 综合管理-库区-库区类型-普货货区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_NORMAL = "BSE_GOODSAREA_TYPE_NORMAL";
    /**
     * 综合管理-库区-库区类型-空运库区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_AIRFREIGHT = "BSE_GOODSAREA_TYPE_AIRFREIGHT";
    
    /**
     * 综合管理-库区-库区类型-快递库区
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_TYPE_EXPRESS = "BSE_GOODSAREA_TYPE_EXPRESS";
    
    /**
     * 综合管理-库区-库区类别-长途
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_USAGE_L = "L";
    /**
     * 综合管理-库区-库区类别-短途
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODSAREA_USAGE_S = "S";

    /**
     * 综合管理-库区-货物类型-A货
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODS_TYPE_A = "A";
    /**
     * 综合管理-库区-货物类型-B货
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODS_TYPE_B = "B";

    /**
     * 综合管理-库区-货物类型-AB货
     * @author foss-zhujunyong
     */
    public static final String BSE_GOODS_TYPE_AB = "AB";

    
    /**
     * 词条：DictionaryConstants.BSE_CUSTOMER_TYPE
     * 客户类型：代理
     * @author 094463-foss-xieyantao
     */
    public static final String CUSTOMER_TYPE_AGENCY = "AGENCY";
    
    /**
     * 词条：DictionaryConstants.BSE_CUSTOMER_TYPE
     * 客户类型：客户
     * @author dp-xieyantao
     */
    public static final String CUSTOMER_TYPE_CUSTOMER = "CUSTOMER";
    
    /**
     * 词代码：DictionaryConstants.CRM_CUSTOMER_ATTRIBUTE
     * CRM-客户属性:出发客户、 到达客户、出发和到达客户
     * 		
     * @author 094463-foss-xieyantao
     */
    //出发客户
    public static final String CRM_CUSTOMER_ATTRIBUTE_LEAVE = "LEAVE_CUSTOMER";
    //到达客户
    public static final String CRM_CUSTOMER_ATTRIBUTE_ARRIVE = "ARRIVE_CUSTOMER";
    //出发和到达客户
    public static final String CRM_CUSTOMER_ATTRIBUTE_LEAVE_ARRIVE = "LEAVE_ARRIVE_CUSTOMER";
    
    /**
     * 词代码：DictionaryConstants.CRM_CUSTOMER_TYPE
     * CRM-客户类型: 企业、个人
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //企业
    public static final String CRM_CUSTOMER_TYPE_ENTERPRISE = "ENTERPRISE";
    //个人
    public static final String CRM_CUSTOMER_TYPE_PERSONAL = "PERSONAL";
    
    /**
     * 词代码：DictionaryConstants.CRM_PREFERENTIAL_TYPE
     * CRM-优惠类型: 价格折扣、月发月送
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //价格折扣
    public static final String CRM_PREFERENTIAL_TYPE_PRICE_REBATE = "PRICE_REBATE";
    //月发越送
    public static final String CRM_PREFERENTIAL_TYPE_MONTH_SEND = "MONTH_SEND";
    //月发越折
    public static final String CRM_PREFERENTIAL_TYPE_MONTH_REBATE = "MONTH_REBATE";
    
    /**
     * 词代码：DictionaryConstants.CRM_NONFIXED_CUSTOMER_TYPE
     * CRM-散客类型: 临代散客、普通散客
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //临代散客
    public static final String CRM_NONFIXED_FOSS_SCATTER = "FOSS_SCATTER";
    //普通散客
    public static final String CRM_NONFIXED_ORDINARY_SCATTER = "ORDINARY_SCATTER";
    
    /**
     * 词代码：DictionaryConstants.CRM_CUSTOMER_GRADE
     * CRM-客户等级: 铂金客户、钻石客户、黄金客户、普通客户、已降级固定客户
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //铂金客户
    public static final String CRM_CUSTOMER_GRADE_PLATINUM = "PLATINUM";
    //钻石客户
    public static final String CRM_CUSTOMER_GRADE_DIAMOND = "DIAMOND";
    //黄金客户
    public static final String CRM_CUSTOMER_GRADE_GOLD = "GOLD";
    //普通客户
    public static final String CRM_CUSTOMER_GRADE_NORMAL = "NORMAL";
    //已降级固定客户
    public static final String CRM_CUSTOMER_GRADE_DEMOTION = "DEMOTION";
    
    /**
     * 词代码：DictionaryConstants.CRM_ADDRESS_TYPE
     * CRM-地址类型: 接货地址、发货地址、接送货地址
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //接货地址
    public static final String CRM_ADDRESS_TYPE_RECEIVE_GOODS = "RECEIVE_GOODS";
    //发货地址
    public static final String CRM_ADDRESS_TYPE_SEND_GOODS = "SEND_GOODS";
    //接送货地址
    public static final String CRM_ADDRESS_TYPE_RECEIVE_SEND_GOODS = "RECEIVE_SEND_GOODS";
    
    /**
     * 词代码：DictionaryConstants.CRM_PAY_WAY
     * CRM-付款方式: 月结、在线支付、现金、到付
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //月结
    public static final String CRM_PAY_WAY_MONTH_PAY = "MONTH_PAY";
    //在线支付
    public static final String CRM_PAY_WAY_PAY_ONLINE = "PAY_ONLINE";
    //现金
    public static final String CRM_PAY_WAY_CASH = "CASH";
    //到付
    public static final String CRM_PAY_WAY_PAY_ARIIVE = "PAY_ARIIVE";
    
    /**
     * 词代码：DictionaryConstants.CRM_ACCOUNT_USE
     * CRM-账户用途: 代收货款和月发月送、月发月送、代收货款
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //代收货款和月发月送
    public static final String CRM_ACCOUNT_USE_SUBSTITUTE_MONTH = "SUBSTITUTE_MONTH";
    //月发月送
    public static final String CRM_ACCOUNT_USE_SEND_MONTH = "SEND_MONTH";
    //代收货款
    public static final String CRM_ACCOUNT_USE_SUBSTITUTE_PAYMENT = "SUBSTITUTE_PAYMENT";
    
    /**
     * 词代码：DictionaryConstants.CRM_ACCOUNT_NATURE
     * CRM-账户性质: 对私账户、对公账户
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //对私账户
    public static final String CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT = "PRIVATE_ACCOUNT";
    //对公账户
    public static final String CRM_ACCOUNT_NATURE_PUBLIC_ACCOUNT = "PUBLIC_ACCOUNT";
    
    /**
     * 词代码：DictionaryConstants.CRM_SEX
     * CRM-性别: 男、女
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //男
    public static final String CRM_SEX_MAN = "M";
    //女
    public static final String CRM_SEX_WOMAN = "W";
    
    /**
     * 词代码：DictionaryConstants.BSE_SITE_GROUP_TYPE
     * CRM-站点组类型: 出发、到达
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //出发
    public static final String BSE_SITE_GROUP_TYPE_CF = "CF";
    //到达
    public static final String BSE_SITE_GROUP_TYPE_DD = "DD";
    
    /**
     * 词代码：DictionaryConstants.BSE_DISTRICT_DEGREE
     * 银企-行政区域等级: 省、市
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //省
    public static final String BSE_DISTRICT_DEGREE_PROVINCE = "1";
    //市
    public static final String BSE_DISTRICT_DEGREE_CITY = "2";
    
    /**
     * 词代码：DictionaryConstants.BSE_TRANS_TYPE
     * CRM-站点组类型: 汽运、空运
     * 	          	
     * @author 094463-foss-xieyantao
     */
    //汽运
    public static final String BSE_TRANS_TYPE_AIRCRAFT = "TRANS_AIRCRAFT";
    //空运
    public static final String BSE_TRANS_TYPE_VEHICLE = "TRANS_VEHICLE";
    
    /**
     * 词代码：DictionaryConstants.BSE_SLOGAN_TYPE
     * 综合管理-广告语-广告语类型 "值代码"
     * @author 094463-foss-xieyantao
     */
    //单据广告语
    public static final String BSE_SLOGAN_TYPE_BILL = "B";
    //短信广告语
    public static final String BSE_SLOGAN_TYPE_SMS = "S";
    
    /**
	 * 客户通知广告模版CODE
	 */
	public static final String PKP_NOTIFY_CUSTOMER = "NOTIFY_CUSTOMER";
	/**
	 * 普通版到达联左上角广告模版CODE
	 */
	public static final String PKP_ARRIVE_TOP_LEFT = "TOP_LEFT";
	/**
	 * 普通版到达联左下角广告模版CODE
	 */
	public static final String PKP_ARRIVE_LOWER_LEFT = "LOWER_LEFT";
	/**
	 * 激光版到达联右下角广告模版CODE
	 */
	public static final String PKP_ARRIVE_LOWER_RIGHT = "LOWER_RIGHT";
    
    /**
     * 综合管理-费控系统（FIN）-账户性质 "值代码"(词代码：FIN_ACCOUNT_TYPE)
     * @author dp-xieyantao
     */
    //德邦子公司账户
    public static final String FIN_ACCOUNT_TYPE_DPSON = "1";
    //收银员卡
    public static final String FIN_ACCOUNT_TYPE_CASHIER = "2";
    //内部员工账户
    public static final String FIN_ACCOUNT_TYPE_INTERNAL = "3";
    //公司外部账户
    public static final String FIN_ACCOUNT_TYPE_EXTERNAL = "4";
    //外部对公账户
    public static final String FIN_ACCOUNT_TYPE_PUBLIC = "5";
    //外部对私账户
    public static final String FIN_ACCOUNT_TYPE_PRIVATE = "6";
    /**
     * 综合管理-站内消息-消息类型 "值代码"
     * @author 101911-foss-zhouChunlai
     */
    //普通消息
    public static final String MSG_TYPE__NORMAL = "NORMAL";
    //全网消息
    public static final String MSG_TYPE__ALLNET = "ALLNET";
    
    /**
	 * 综合管理-站内消息-消息读取状态 "值代码"
	 * 
	 * @author 101911-foss-zhouChunlai
	 */
    //消息已读
	public static final String MSG__READ_STATUS__READ= "R";
	//消息未读
	public static final String MSG__READ_STATUS__UNREAD = "N";
	/**
	 * 综合管理-待办事项-待办状态 "值代码"
	 * @author 101911-foss-zhouChunlai
	 */
    //已处理
	public static final String TODOMSG__STATUS_TYPE__PROCESSED= "D";
	//未处理
	public static final String TODOMSG__STATUS_TYPE__PROCESSING = "G"; 
	
	 /**
     * 综合管理-待办事项-业务类型 "值代码"
     * @author 101911-foss-zhouChunlai
     */
	//快递补录运单
	/**
	 * 快递PAD开单
	 */
	public static final String TODOMSG__BUSINESS_TYPE__ECS_PDA_WAYBILL = "ECS_PDA_WAYBILL";
	/**
	 * 运单补录
	 */
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_PENDING = "ECS_WAYBILL_PENDING";
	//更改单审核
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT = "PKP_WB_AUDIT";
	//更改单受理
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT = "PKP_WB_ACCECPT";
	//更改单标签重打 
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_LABELED_PRINT = "PKP_WB_LAB_PT"; 
	//更改单申请
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY = "PKP_WB_APPLY";
	//快递更改单审核
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_AUDIT_WAYBILL = "PKP_WB_AUDIT_WAY";
	//快递更改单受理
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCECPT_WAYBILL = "PKP_WB_ACCECPT_WAY";
	//快递更改单标签重打 
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_LABELED_PRINT_WAYBILL = "PKP_WB_LAB_PT_WAY"; 
	//快递更改单申请
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_APPLY_WAYBILL = "PKP_WB_APPLY_WAY";
	//快递PDA补录
	public static final String TODOMSG__BUSINESS_TYPE__PDA_WAYBILLRFC = "PKP_PDA_WAYBILLRFC"; 
	//PDA补录
	public static final String TODOMSG__BUSINESS_TYPE__PDA_WAYBILL = "PKP_PDA_WAYBILL"; 
	//转寄退回件工单
	public static final String TODOMSG__BUSINESS_TYPE__RETURN_GOODS = "RETURN_GOODS_TODO";
/**
	 * 快递更改单受理
	 */
	public static final String TODOMSG__BUSINESS_TYPE__WAYBILL_CHANGE_ACCEPTANCE = "PKP_WB_ACCECPT_WAY";
		
    /**
     * 结算方式分为：月结、半月结、非月结(CRM系统目前去掉'半月结')
     * @author dp-xieyantao
     */
    //月结
    public static final String CLEARING_TYPE_MONTH = "MONTH_END";
    //半月结
    public static final String CLEARING_TYPE_HALF_MONTH = "HALF_MONTH";
    //非月结
    public static final String CLEARING_TYPE_NO_MONTH = "NOT_MONTH_END";
    
    /**
     * 集中接送货大、小区
     * 区域类型：接货区
     * @author dp-xieyantao
     */
    public static final String REGION_TYPE_PK = "PK";
    
    /**
     * 集中接送货大、小区
     * 区域类型：送货区
     * @author dp-xieyantao
     */
    public static final String REGION_TYPE_DE = "DE";
    /**
     * 集中接送货大、小区
     * 区域性质：大区
     * @author dp-xieyantao
     */
    public static final String REGION_NATURE_BQ = "BQ";
    //区域性质：小区
    public static final String REGION_NATURE_SQ = "SQ";
    
    /**
     * 外部网点（空运代理网点、偏线代理网点）
     * 网点类型：空运代理网点
     * @author dp-xieyantao
     */
    public static final String OUTERBRANCH_TYPE_KY = "KY";
    
    /**
     * 外部网点（空运代理网点、偏线代理网点）
     * 网点类型：偏线代理网点
     * @author dp-xieyantao
     */
    public static final String OUTERBRANCH_TYPE_PX = "PX";
    
    /**
     * 快递代理公司和网点的类型常量
     * 
     * @author WangPeng
     * 
     */
    public static final String EXPRESS_DELIVERY_TYPE_LDP = "LD";
    
    /**
     * 代理类别（代理公司、代理网点）
     * 代理公司
     * @author dp-xieyantao
     */
    public static final String AGENCY_TYPE_COM = "AGENCY_COM";
    //代理网点
    public static final String AGENCY_TYPE_BRANCH = "AGENCY_BRANCH";
    
    
    /**
     * 德邦自自有网点
     */
    public static final String DEPPON_OWN_ORG="ORG";
    
    /**
     * 机场
     */
    public static final String AIR_PORT="AIRPORT";
    /**
     * 产品  - 快车标识
     * @author DP-Foss-YueHongJie
     */
    public static final String PRODUCT_FAST = "FAST";
   
    /**
     * 产品 - 慢车标识
     * @author DP-Foss-YueHongJie
     */
    public static final String PRODUCT_NORMAL = "NORMAL";
    
    /**
     * COD_TYPE 代收货款类型（参考运单中代收货款退款类型）
     */
    public static final String COD__COD_TYPE__RETURN_1_DAY = "R1"; // 即日退

    public static final String COD__COD_TYPE__RETURN_3_DAY = "R3"; // 三日退

    public static final String COD__COD_TYPE__RETURN_APPROVE = "RA"; // 审核退

    /**
     * CURRENCY_CODE 币种
     */
    public static final String SETTLEMENT__CURRENCY_CODE__RMB = "RMB"; // 人民币

    public static final String SETTLEMENT__CURRENCY_CODE__HKD = "HKD"; // 港币

    public static final String SETTLEMENT__CURRENCY_CODE__NTD = "NTD"; // 台币

    public static final String SETTLEMENT__CURRENCY_CODE__USD = "USD"; // 美元
    
    
    //签收回单类型
    
    public static final String SIGN_RETURN_TYPE__ORIGINAL="ORIGINAL";//原件反单
    
    public static final String SIGN_RETURN_TYPE__FAX="FAX";//传真反单
    
    //包装类型
    public static final String PACKAGE_TYPE__BOX="BOX";//木箱
    public static final String PACKAGE_TYPE__FRAME="FRAME";//木架
    public static final String PACKAGE_TYPE__SALVER="SALVER";//木托 zxy 20131118 ISSUE-4391
    
    /**
     * 外部网点（空运代理网点、偏线代理网点）
     * 增值服务，承运业务
     * @author LIXUEXING
     */
    public static final String ALL = "ALL";//全部
    
    public static final String PAY_COLLECTION = "PAY_COLLECTION";//代收货款
    public static final String CASH_DELIVERY = "CASH_DELIVERY";//货到付款
    public static final String BACK_SIGN = "BACK_SIGN";//返回签单
    
    public static final String DEPARTURE = "DEPARTURE";//出发
    public static final String ARRIVE = "ARRIVE";//到达
    public static final String TRANSFER = "TRANSFER";//中转
    
    // =================================================================
    /**
     *  综合
     */
    /**BEGIN:*************车辆职责类型**************/
    //update by 092020-lipengfei 将定区车分为一、二级定区车,在需要判断是否为定区车而不需要区分一、二级时使用。
    /** 车辆职责类型
     * 一、二级定区车统称
     * 对应DictionaryConstants类的词条VEHICLE_RESPONSIBILITY_CATEGORY
     *  */
    public static final String GENERAL_CONSTANT_AREA_CAR = "CONSTANT_AREA_CAR";
    /** 车辆职责类型
     * 一级定区车
     * 对应DictionaryConstants类的词条VEHICLE_RESPONSIBILITY_CATEGORY
     *  */
    public static final String FIRST_CONSTANT_AREA_CAR = "FIRST_CONSTANT_AREA_CAR";
    /** 车辆职责类型
     * 二级定区车
     * 对应DictionaryConstants类的词条VEHICLE_RESPONSIBILITY_CATEGORY
     *  */
    public static final String SECOND_CONSTANT_AREA_CAR = "SECOND_CONSTANT_AREA_CAR";
    /** 车辆职责类型
     * 机动车
     * 对应DictionaryConstants类的词条VEHICLE_RESPONSIBILITY_CATEGORY
     *  */
    public static final String MOTOR_VEHICLE = "MOTOR_VEHICLE";
    /**END:*************车辆职责类型**************/
    /** BEGIN：******************************* 车队车队组相关的值代码 *********************************/

    /** 车队/车队组类型
     * 长途车队
     * 对应DictionaryConstants类的词条BSE_FLEET_TYPE
     *  */
    public static final String FLEET_TYPE__LONG_DISTANCE = "FLEET_TYPE__LONG_DISTANCE";

    /**
     * 车队/车队组类型
     * 物流班车车队
     * 对应DictionaryConstants类的词条BSE_FLEET_TYPE
     */
    public static final String FLEET_TYPE__SHUTTLE = "FLEET_TYPE__SHUTTLE";

    /**
     * 车队/车队组类型
     * 接送货车队
     * 对应DictionaryConstants类的词条BSE_FLEET_TYPE
     */
    public static final String FLEET_TYPE__SHUTTLE_GOODS = "FLEET_TYPE__SHUTTLE_GOODS";

    /** END：******************************* 车队车队组相关的值代码 *********************************/
    
    /**
     * 营业部出发
     */
    public static final String ORG_DEPARTURE="ORG_DEPARTURE";
    
    /**
     * 营业部到达
     */
    public static final String ORG_ARRIVE="ORG_ARRIVE";
    /**
     * 在职
     */
    public static final String EMPLOYEE_STATUS__IN_OFFICE="1";
    /**
     * 离职
     */
    public static final String EMPLOYEE_STATUS__TO_LEAVE="0";
    // =================================================================
    /**
     *  省市区县 087584-foss-lijun
     *  对应DictionaryConstants类的 DISTRICT_DEGREE（词条代码）
     */
    
    /**
     * 国家
     */
    public static final String DISTRICT_NATION="NATION";
    
    /**
     * 省
     */
    public static final String DISTRICT_PROVINCE="DISTRICT_PROVINCE";
    
    /**
     * 市
     */
    public static final String DISTRICT_CITY="CITY";
    /**
     * 区县
     */
    public static final String DISTRICT_COUNTY="DISTRICT_COUNTY";
    /**
     * 镇街道办事处
     */
    public static final String TOWN_STREET_AGENCY ="TOWN_STREET_AGENCY";
    /**
     * 村路
     */
    public static final String VILLAGE_ROAD ="VILLAGE_ROAD";
    // =================================================================
    /**
     *  省市区县行政区域单位后缀 087584-foss-lijun
     */
    
    /**
     * 国家
     */
    public static final String DISTRICT_SUFFIX_NATION="SUFFIX_NATION";
    
    /**
     * 省
     */
    public static final String DISTRICT_SUFFIX_PROVINCE="SUFFIX_PROVINCE";
    
    /**
     * 市
     */
    public static final String DISTRICT_SUFFIX_CITY="SUFFIX_CITY";
    
    /**
     * 区
     */
    public static final String DISTRICT_SUFFIX_QU="SUFFIX_QU";
    
    /**
     * 县
     */
    public static final String DISTRICT_SUFFIX_XIAN="SUFFIX_XIAN";
    
    /**
     * 自治州
     */
    public static final String DISTRICT_SUFFIX_ZIZHIZHOU="SUFFIX_ZIZHIZHOU";
    
    // =================================================================
    /**
     *  BSE.T_BAS_SYS_CONFIG.DATA_TYPE 系统配置参数的数据类型 087584-foss-lijun
     */

    /**
     * 整形
     */
    public static final String CONFIG__DATA_TYPE__INT ="INT";
    /**
     * 小数
     */
    public static final String CONFIG__DATA_TYPE__FLOAT ="FLAOT";
    /**
     * 日期
     */
    public static final String CONFIG__DATA_TYPE__DATE ="DATE";
    /**
     * 字符串
     */
    public static final String CONFIG__DATA_TYPE__STRING ="STRING";
    
    /**
     *  渠道类型
     */
    /**
     * 诚信通
     */
    public static final String CHANNEL__CXT ="CHANNEL__CXT";
    /**
     * 网上订单
     */
    public static final String CHANNEL__WSDD ="CHANNEL__WSDD";
    /**
     * 字符串
     */
    public static final String CHANNEL__ALBB ="CHANNEL__ALBB";
    
    /**
     * 营业部经理
     */
    public static final String UUMS_POSITION_TERMSCODE_MANAGER = "MANAGER";
    
    
    /**
     * ==================================================================
     * 部门级别，对应类DictionaryConstants的ORG_LEVEL
     */

    /**
     * 部门级别-集团
     */
    public static final String ORG_LEVEL_JS = "ORG_LEVEL_JS";

    /**
     * 部门级别-事业部
     */
    public static final String ORG_LEVEL_SYB = "ORG_LEVEL_SYB";
    /**
     * 部门级别-大区
     */
    public static final String ORG_LEVEL_DQ = "ORG_LEVEL_DQ";
    /**
     * 部门级别-小区
     */
    public static final String ORG_LEVEL_XQ = "ORG_LEVEL_XQ";
    /**
     * 部门级别-部门
     */
    public static final String ORG_LEVEL_BM = "ORG_LEVEL_BM";
    /**
     * 综合查询 自定义查询 比较符 数据字典【COMPARE_SYMBOL】开始
     * 类似 （like）
     */
    /**
     * 比较符-类似 
     */
    public static final String COMPARE_SYMBOL_LIKE = "like";
    /**
     * 综合查询 自定义查询 比较符 数据字典【COMPARE_SYMBOL】结束
     * 类似 like
     */

    /**
     * 星标类型1号线
     */
    public static final String ASTERISK_TYPE_LINE1 = "ASTERISK_TYPE_LINE1";
   
    /**
     * 空运禁运品
     */
    public static final String AIR_CONTRABAND = "AIR_CONTRABAND";
    
    /**
     * 海运禁运品
     */
    public static final String SHIPPING_CONTRABAND = "MARINE_CONTRABAND";
    
  //=========================价格区域常量================================
    /**
     * 到达区域
     */
    public static final String ARRIVE_REGION = "ARRIVE_REGION";
    /**
     * 增值区域
     */
    public static final String VALUEADD_REGION = "VALUEADD_REGION";
    /**
     * 空运区域
     */
    public static final String PRICING_REGION_AIR = "PRICING_REGION_AIR";
    /**
     * 快递代理/试点城市类型：快递代理
     */
    public static final String EXPRESS_CITY_TYPE_LDP = "L";
    /**
     * 快递代理/试点城市类型：试点
     */
    public static final String EXPRESS_CITY_TYPE_PILOT = "P";

    /**
     * 快递代理公司核销方式：不核销
     */
    public static final String LDP_NOWRITEOFF = "NOWRITEOFF";

    /**
     * 快递代理公司核销方式：到付运费冲外发成本
     */
    public static final String LDP_DRWRITEOFF = "DRWRITEOFF";
    
    /**
     * 快递代理公司核销方式：到付运费和代收货款冲成本
     */
    public static final String LDP_DRCRWRITEOFF = "DRCRWRITEOFF";
    
    /**
     * 快递PDA提货方式
     * @author:Wangpeng
     */
    
    /**
     * 派送
     */
    public static final String EXPRESS_PDA_DELIVER_UP = "DELIVER_UP";
    
    /**
     * 自提
     */
    public static final String EXPRESS_PDA_SELF_PICKUP = "SELF_PICKUP";
    
    /**
     * 特殊增值服务
     */
    
    /**
     * 自提
     */
    public static final String FURNITURE_EQUIP = "FURNITURE_EQUIP";
    
    
    /**
     * 客户合同发票标记信息
     * @author:shenweihua
     */
    
    /**
     * 运输专票
     */
    public static final String SETTLEMENT_INVOICE_MARK_ONE ="INVOICE_TYPE_01";
    
    /**
     * 非运输专票
     */
    public static final String SETTLEMENT_INVOICE_MARK_TWO="INVOICE_TYPE_02";
    
    /**
     * 快递派送区域--派送属性
     * @author 130566 ZengJunfan-修改：dujunhui
     */
    /**
     * 全境派送
     */
    public static final String DELIVERY_NATURE_QJPS ="DELIVERY_NATURE_QJPS";
    /**
     * 部分派送
     */
    public static final String DELIVERY_NATURE_BFPS ="DELIVERY_NATURE_BFPS";
    /**
     * 自提 不派送
     */
    public static final String DELIVERY_NATURE_ZTBPS ="DELIVERY_NATURE_ZTBPS";
    /**
     * 发全境派送
     */
    public static final String DELIVERY_NATURE_FQJPS ="DELIVERY_NATURE_FQJPS";
    /**
     * 发部分派送
     */
    public static final String DELIVERY_NATURE_FBFPS ="DELIVERY_NATURE_FBFPS";
    

    
    /**
     * 派送网点类型（零担大客户派送地址库）
     */
    /**
     * 自有●全境派送
     */
    public static final String OWN_WHOLE_DELIVERY ="own_whole_delivery";
    /**
     * 偏线●全境派送
     */
    public static final String PX_WHOLE_DELIVERY ="px_whole_delivery";
    /**
     * ⊕部分派送
     */
    public static final String PART_DELIVERY ="part_delivery";
    /**
     * 不派送
     */
    public static final String NO_DELIVERY ="no_delivery";
    /**
     * 超远派送
     */
    public static final String MUCHHIGHER_DELIVERY ="muchHigher_delivery";
    /**
     * 待叉区到目标区域距离类型
     */
    /**
     * 待叉区到月台距离
     */
    public static final String DISTANCE_BETWEEN_WAITFORKAREA_PLATFORM="BETWEEN_WAITFORKAREA_PLATFORM";
    /**
     * 待叉区到库区距离
     */
    public static final String DISTANCE_BETWEEN_WAITFORKAREA_GOODSAREA="BETWEEN_WAITFORKAREA_GOODSAREA";

    /**
     * 快递员工作类别
     * @author 130566 ZengJunfan
     */
    //工作(出车)
    public static final String PLAN_TYPE_WORK ="WORK";
    //值班
    public static final String PLAN_TYPE_DUTY ="DUTY";
    //休息
    public static final String PLAN_TYPE_REST ="REST";
    //离岗
    public static final String PLAN_TYPE_LEAVE ="LEAVE";
    //培训
    public static final String PLAN_TYPE_TRAINING ="TRAINING";
  /**
   * 快递员属性
   */
    //定区
    public static final String COURIER_NATURE_FIXED ="FIXED";
    //机动
    public static final String COURIER_NATURE_MOTORIZED="MOTORIZED";
	 /**
     * 部门临时欠款额度系数
     */
    public static final String OWED_LIMIT_MULTIPLE="OWED_LIMIT_MULTIPLE";
	
     /**
     * 客户性质
     * @author:078816 wangpeng
     * @date:2014-03-17
     */
    /**
     * 固定客户
     */
    public static final String CUSTOMER_RC_CUSTOMER = "RC_CUSTOMER";
    
    /**
     * 散客
     */
    public static final String CUSTOMER_SC_CUSTOMER = "SC_CUSTOMER";
    
    /**
     * 潜客
     */
    public static final String CUSTOMER_PC_CUSTOMER = "PC_CUSTOMER";
    
    /**
     * 业务类别
     * @author:078816 wangpeng
     * @date:2014-03-17
     */
    
    /**
     * 零担
     */
    public static final String BUSINESS_LTT = "LTT";
    
    /**
     * 快递
     */
    public static final String BUSINESS_EXPRESS = "EXPRESS";
    
    /**
     * 零担和快递
     */
    public static final String BUSINESS_EL = "EL";
    
    /**
     * 客户属性
     * @author:078816 wangpeng
     * @date:2014-04-10
     * 
     */
    
    /**
     * 出发客户
     */
    public static final String LEAVE_CUSTOMER_ATTRIBUTE = "LEAVE_CUSTOMER";
    
    /**
     * 到达客户
     */
    public static final String ARRIVE_CUSTOMER_ATTRIBUTE = "ARRIVE_CUSTOMER";
    
    /**
     * 打印营销内容城市类型
     * @author:187862-dujunhui
     * @date:2014-08-28
     */
    /**
     * 出发城市
     */
    public static final String DEPARTURE_CITY = "DEPARTURE_CITY";
    /**
     * 到达城市
     */
    public static final String ARRIVAL_CITY = "ARRIVAL_CITY";
    /**
     * 外场业务渠道类型
     * @author:187862-dujunhui
     * @date:2014-12-10
     */
    //枢纽外场
    public static final String HUB_OUTFIELD = "HUB_OUTFIELD";
    //一级转运外场
    public static final String ONE_LEVEL_TRANSPORT_OUTFIELD = "ONE_LEVEL_TRANSPORT_OUTFIELD";
    //二级转运外场
    public static final String TWO_LEVEL_TRANSPORT_OUTFIELD = "TWO_LEVEL_TRANSPORT_OUTFIELD";
    //三级转运外场
    public static final String THREE_LEVEL_TRANSPORT_OUTFIELD = "THREE_LEVEL_TRANSPORT_OUTFIELD";
    //一级集配外场
    public static final String ONE_LEVEL_DISTRIBUTION_OUTFIELD = "ONE_LEVEL_DISTRIBUTION_OUTFIELD";
    //二级集配外场
    public static final String TWO_LEVEL_DISTRIBUTION_OUTFIELD = "TWO_LEVEL_DISTRIBUTION_OUTFIELD";
    //运作外场
    public static final String OPERATION_OUTFIELD = "OPERATION_OUTFIELD";
	/**
     * 降价发券方案
     * @author:187862-dujunhui
     * @date:2014-10-2
     */
    /**
     * 活动类型——降价发券方案服务
     */
  	public static final String TYPE_PRICE_COUPON = "COUPON";
  	/**
     * 计费类型——按重量计费
     */
  	public static final String BILLINGWAY_WEIGHT = "WEIGHT";
  	/**
     * 计费类型——按体积计费
     */
  	public static final String BILLINGWAY_VOLUME = "VOLUME";
  	/**
     * 线路区域类型——线路
     */
  	public static final String IS_LINE = "IS_LINE";
  	/**
     * 线路区域类型——出发城市
     */
  	public static final String IS_DEPARTURE_CITY = "IS_DEPARTURE_CITY";

    /**
     * 快递派送区域电子地图管理状态类型
     * @author:187862-dujunhui
     * @date:2015-1-4
     */
    //未编辑
    public static final String EXPRESS_DELIVERY_MAP_MANAGE_NOT_EDITED="NOT_EDITED";
    //未审核
    public static final String EXPRESS_DELIVERY_MAP_MANAGE_NOT_VERIFIED="NOT_VERIFIED";
    //已审核
    public static final String EXPRESS_DELIVERY_MAP_MANAGE_IS_VERIFIED="IS_VERIFIED";
    //已退回
    public static final String EXPRESS_DELIVERY_MAP_MANAGE_IS_TURNBACK="IS_TURNBACK";
    //生效
    public static final String EXPRESS_DELIVERY_MAP_MANAGE_IS_ACTIVED="IS_ACTIVED";
    //全部
    public static final String EXPRESS_DELIVERY_MAP_MANAGE_ALL="ALL";
    
    /**
     * 主数据同步组织信息至UUMS系统中间表发送状态(WAITING_TO_SEND-待发送;SENDING-发送中;SEND_SUCCESS-发送成功;SEND_FAIL-发送失败)
     */
    public static final String ORGINFO_TOUU_SENDSTATUS_WAITING_TO_SEND="WAITING_TO_SEND";
    public static final String ORGINFO_TOUU_SENDSTATUS_SENDING="SENDING";
    public static final String ORGINFO_TOUU_SENDSTATUS_SEND_SUCCESS="SEND_SUCCESS";
    public static final String ORGINFO_TOUU_SENDSTATUS_SEND_FAIL="SEND_FAIL";
    
    /**
     * 主数据项目UUMS传给FOSS的最大修改时间,以及FOSS最大时间
     */
    public static final String MAX_TIME_OF_UU="9999-12-31 23:59:59.000000";
    public static final String MAX_TIME_OF_FOSS="2999-12-31 23:59:59.000000";
    
    //站内消息类型  供应商补录失败消息
    public static final String MSG_TYPE__SUPPLIER_FAIL_PATCH="SUPPLIER_FAIL_PATCH";
    //消息的创建者
    public static final String MSG_CREAT_TYPE_ECS="ECS";
    
}


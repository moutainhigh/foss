package com.deppon.foss.module.transfer.common.api.shared.define;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import java.util.Map;
public class PricingConstants {
	
	/**
	 * 第一,代表国家
	 */
	public static final  int NUMBER_FIRST = 1;
	/**
	 * 第二，代表省份
	 */
	public static final  int NUMBER_SECOND = 2;
	/**
	 * 第三，代表市
	 */
	public static final  int NUMBER_THIRD = 3;
	/**
	 * 第四，代表区县,优先级 区县>城市>省份>国家
	 */
	public static final  int NUMBER_FOURTH = 4;
	
	/**
	 * 合伙人上线时间历史数据处理时间临界值
	 */
	public static final String PKP_PTP_SIGN_INIT_410 = "PKP_PTP_SIGN_INIT_410";

	 //zb modify start
	/**
	 * 价格方案中运输性质类型标志  客户
	 */
	public static final String TRANSPORT_KH_FLAG = "4";
	/**
	 * 客户价格方案，在T_SRV_PRICE_PLAN表存的始发区域ID和CODE
	 */
	public static final String PRICE_REGION = "PRICE_REGION";
	//zb modify end
	/**
	 * 包装类型:打木托
	 */
	public static final String PACKAGE_TYPE_SALVER ="SALVER";
	/**
	 * 包装类型:打木架
	 */
	public static final String PACKAGE_TYPE_WOOD ="WOOD";
	/**
	 * 货币类型
	 */
	public static final String CURRENCY_CODE ="RMB";
	
	/**
	 * 精准大票价格到达区域走货类型  直发
	 * 
	 */
	public  static  final String  BIG_SEND_DIRECTLY ="BIG_SEND_DIRECTLY";
	
	/**
	 * 精准大票价格到达区域走货类型  中转
	 * 
	 */
	public  static  final String  BIG_MIDDLE_TRANSIT ="BIG_MIDDLE_TRANSIT";
	
	/*快递计价服务拆分  xingjun 2017/2/20   begin*/
	/**
	 * 3.60 特惠件
	 */
	public static  final String ROUND_COUPON_PACKAGE ="RCP";
	/**
	 * 包裹
	 */
	public static final String PACKAGE = "PACKAGE";
	//月结
	public final static  String MONTH_PAYMENT = "CT";
	/**
	 *  德邦内部带货客户编码 --
	 */
	public static final String DEPPON_CUSTOMER="4008305555";
	/**
	 * 开单类型 返单
	 */
	public final static String WAYBILL_EXPRESSTYPE_RETURN_WAYBILL ="RETURN_WAYBILL";
	/**
	 * 返单类别  无需返单
	 */
	public final static  String NOT_RETURN_BILL = "NONE";
	/**
	 * 返单类别  原件签收单返回
	 */
	public final static  String RETURNBILLTYPE_ORIGINAL = "ORIGINAL";
	/**
	 * 返单类别  传真件签收单返回
	 */
	public final static  String RETURNBILLTYPE_FAX = "FAX";
	//费用明细-手动输入ID
	public final static String HAND_INPUT = "shoudongshuruid";
	/**
	 * 三级产品-快递商务件
	 */
	public static final String PRICING_PRODUCT_EXPRESS_AIR_FREIGHT="DEAP";
	//我司原因
	public static final String COMPANY_REASON ="COMPANY_REASON";
	/**
	 * 开单类型 返货(按票返)
	 */
	public final static String WAYBILL_EXPRESSTYPE_RETURN_CARGO ="RETURN_CARGO";
	//返货原因：我司原因  
	public final static String RETURNREASON_COMPANY_REASON="COMPANY_REASON";
	public final static  String DEAP = "DEAP";//商务专递
	//到付
	public final static  String ARRIVE_PAYMENT = "FC";
	public final static  String  PERMILLAGE = "1000";//千分比
	//付款方式月结
	public final static  String MONTH_END = "MONTH_END";
	
	//调用优惠劵验证接口失败-数据对象转换错误：
	public static final String CONVERT_VALID_ATE_ERROR="foss.pkp.waybill.crmOrderService.exception.errorConvertValidate";
	/**
	 * 更改单类型-- 更改
	 */
	public static final String CUSTOMER_CHANGE = "CUSTOMER_CHANGE";  
	/**
	 * 货物库存状态 -- 收货部门库存
	 */
	public static final String RECEIVE_STOCK = "RECEIVE_STOCK";  
	/*快递计价服务拆分  xingjun 2017/2/20   end*/

	/**
	 * 获取增值服务-偏线中转费指定的计算时间
	 */
	public static final String OUTER_PRICE_DEFAULT_TIME ="OUTER_PRICE_DEFAULT_TIME";
	
	/**
	 * 定价优化上线时间
	 */
	public static final String PRICING_OPTIMIZE_TIME ="PRICING_OPTIMIZE_TIME";
	
	/**
	 * 获取整车报价指定的校验时间
	 */
	public static final String WHOLE_VEHICLE_VALIDATION_TIME ="WHOLE_VEHICLE_VALIDATION_TIME";
	/**
	 * 配置开单和更改单接货费是否可以改小
	 */
	public static final String PICKUP_FEE_IS_CHANGE_SMALL = "PICKUP_FEE_IS_CHANGE_SMALL";
	/**
	 * 外请车司机判断参数
	 */
	public static final String DRIVER_CODE_OUT = "000000";
	
	/** 基础增值服务CODE **/
	public static final String PRICING_BASIC_CODE = "JCZFA";
	
	/** 产品增值服务CODE **/
	public static final String PRICING_PRODUCT_CODE = "CPZFA";
	
	/** 区域增值服务CODE **/
	public static final String PRICING_REGION_CODE = "QUZFA";
	 //重量开始范围
    public static final String WEIGHT_LEFT_RANGE = "重量开始范围";
    //体积开始范围
    public static final String VOLUME_LEFT_RANGE = "体积开始范围";	
	//空字符串
	public static final String EMPTY_STRING = "";
	
	//时效区域
	public static final String PRESCRIPTION_REGION = "EFFECTIVE_REGION";
	//价格区域
	public static final String PRICING_REGION = "PRICING_REGION";
	//精准大票价格区域
	public static final String PRICING_REGION_BG = "PRICING_REGION_BG";
	//精准大票价格出发区域ID
	public static final String PRICING_START_REGION_ID ="PRICING_START_REGION_ID";
	//精准大票价格到达区域ID
	public static final String PRICING_ARRIVE_REGION_ID ="PRICING_ARRIVE_REGION_ID";
	//增值区域
	public static final String VALUEADD_REGION = "VALUEADD_REGION";
	//到达区域
	public static final String ARRIVE_REGION = "ARRIVE_REGION";
	//时效区域中文名称
	public static final String PRESCRIPTION_REGION_NAME = "时效区域";
	//价格区域中文名称
	public static final String PRICING_REGION_NAME = "价格区域";
	//空运价格区域
	public static final String PRICING_REGION_AIR = "PRICING_REGION_AIR";
	//行政组织
	public static final String REGION_ORGANIZATION_TYPE_ADMINISTRATIVE = "ADMINISTRATIVE";
	//部门组织
	public static final String REGION_ORGANIZATION_TYPE_DEPT = "DEPT";
	//地址信息
	public static final String REGION_ORGANIZATION_TYPE_ADDRESS = "ADDRESS";
	//所有
	public static final String ALL = "ALL";
	
	//全部区域
	public static final String ALL_REGION_NAME = "全部区域";
	//2999年-12-31 23:59:59
	public static final Long ENDTIME = 32503651199965L;
	//根据MANA-1320修改
	//默认截止日期
	public static final String DEFAULT_END_DATE = "2999-12-31";
	//零
	public static final Integer ZERO = 0;
	//1
	public static final Integer ONE = 1;
	//2
	public static final Integer TWO = 2;
	//3
	public static final Integer THREE = 3;
	//4
	public static final Integer FOUR = 4;
	//5
	public static final Integer FIVE = 5;
	//6
	public static final Integer SIX = 6;
	//7
	public static final Integer SEVEN = 7;
	//6
	public static final Integer EIGHT = 8;
	//7
	public static final Integer NINE = 9;
	//默认激活状态
	public static final String DEFAULTACTIVESTATUS = "N";
	//默认折扣规则
	public static final String DISCOUNTRULE = "chargeWeight";
	//默认方案类型
	public static final String DEFAULTPLANTYPE = "setOffDiscount";
	//5
	public static final String PATTEN = "yyyy-MM-dd";
	
	public static final int YUTOFEN= 100;
	
	//重货
	public static final String HEAVY_GOODS ="HEAVYGOODS";
	//轻货
	public static final String LIGHT_GOODS ="LIGHTGOODS";
	
	/**
	 * 价格方案中的运输性质类型标志  汽运
	 */
	public static final String TRANSPORT_QY_FLAG = "0";
	
	/**
	 * 价格方案中运输性质类型标志  空运
	 */
	public static final String TRANSPORT_KY_FLAG = "1";
	/**
	 * 价格方案中运输性质类型标志  快递
	 */
	public static final String TRANSPORT_KD_FLAG = "2";
	/**
	 * 价格方案中运输性质类型标志  大票货
	 */
	public static final String TRANSPORT_BG_FLAG = "3";
	/**
	 * 价格方案中运输性质类型标志  首续重货
	 */
	public static final String TRANSPORT_EC_FLAG = "4";
	
	//计费规则——规则类型——价格方案
	public static final String VALUATION_TYPE_PRICING ="PRICING";
	//计费规则——规则类型——基础增值服务
	public static final String VALUATION_TYPE_BASICVALUEADDED = "BASICVALUEADDED";
	//计费规则——规则类型——区域增值服务
	public static final String VALUATION_TYPE_REGIONVALUEADDED = "REGIONVALUEADDED";
	//计费规则——规则类型——产品增值服务
	public static final String VALUATION_TYPE_PRODUCTVALUEADDED = "PRODUCTVALUEADDED";
	//计费规则——规则类型——客户增值服务
    public static final String VALUATION_TYPE_CUSTOMERVALUEADDED = "CUSTOMERVALUEADDED";
	//计费规则——规则类型——折扣服务
	public static final String VALUATION_TYPE_DISCOUNT = "DISCOUNT";
	//计费规则——规则类型——增值优惠服务
	public static final String VALUATION_TYPE_VALUEADD_DISCOUNT = "VALUEADDDISCOUNT";
	//计价方式——方式类型——比率收费
	public static final String CRITERIA_TYPE_CHARGERATE = "CHARGERATE";

 
	//计价方式明细——计费类别——按重量
	public static final String CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT = "WEIGHT";
	//计价方式明细——计费类别——按体积
	public static final String CRITERIA_DETAIL_CACULATE_TYPE_VOLUME = "VOLUME";
	
	//计价方式明细——计费类别——按公里
	public static final String CRITERIA_DETAIL_CACULATE_TYPE_KILOM = "KILOM";
	//计价方式明细——计费类别——原始费用计费
	public static final String CRITERIA_DETAIL_CACULATE_TYPE_ORIGINALCOST = "ORIGINALCOST";
	//计价方式明细—-按重量最大值
	public static final Long CRITERIA_DETAIL_WEIGHT_MAX = 9999999999L;
	//一天的毫秒数
	public static final Long ONE_DAY_MILLISECOND = 24*60*60*1000L;
	
	//一秒的毫秒数
	public static final Long ONE_MILLISECOND = 1000L;
	
	//计价方式明细——按体积最大值
	public static final Long CRITERIA_DETAIL_VOLUME_MAX = 9999999999L;
	//计价方式明细——按原始费用计费最大值
	public static final Long CRITERIA_DETAIL_ORIGINALCOST_MAX = 9999999999L;

  
	//订单渠道-产品
	public static final String ORDER_CHANNEL_PRODUCT = "CHANNEL__PROD";
	//订单渠道-产品
	public static final String ORDER_CHANNEL_PRODUCT_NAME = "普通产品";
	
	
	//长途短途
	public static final String LONG_OR_SHORT_L = "L";
	public static final String LONG_OR_SHORT_S = "S";
	
	public static final String LONG_OR_SHORT_L_NAME = "长途";
	public static final String LONG_OR_SHORT_S_NAME = "短途";
	
	public static final String CENTRALIZE_PICKUP_YES = "Y";
	public static final String CENTRALIZE_PICKUP_NO = "N";

	public static final String CONSTANTS_YES = "Y";

	public static final String CONSTANTS_NO = "N";

	//承诺时效单位 - 天
	public static final String TIME_DAY = "DAY";
	//承诺时效单位  - 小时
	public static final String TIME_HOURS = "HOURS";
		
	
	//价格计算表达式——简单费率规则
	public static final String PRICE_RULE_RATERULES_ID = "PR003";
	
	//大票货价格计算表达式——简单费率规则
	public static final String BG_PRICE_RULE_RATERULES_ID = "PR007";
	
	//首续重价格计算表达式——简单费率规则
	public static final String EC_PRICE_RULE_RATERULES_ID = "PR008";
	
	//价格计算表达式——带折扣简单费率规则
    public static final String PRICE_RULE_DISCOUNTRATE_ID = "PR002";
	
	//价格计算表达式——固定价
    public static final String PRICE_RULE_FIXED_ID = "PR001";
    
    //添加是否发送快递短信的标记
    //快递
    public static final String IS_PACKAGE="1";
    //临单
    public static final String IS_ORDER="0";
    
	/**
	 *费用计算常量
	 * **/
    public static class CaculateFeeConstants {

	
	public static final String PRICERULE_EXPRESSION_FIXED = "FX"; // 固定值

	public static final String PRICERULE_EXPRESSION_TOTAL = "TL"; // 总重量（体积）

	public static final String PRICERULE_EXPRESSION_WEIGHT = "WG"; // 重量（体积）

	public static final  String PRICERULE_EXPRESSION_WEIGHT_PRICE = "WP"; // 重量（体积）单价 or 费率

	public static final  String PRICERULE_EXPRESSION_FIRST_WEIGHT = "FW"; // 首重（体积）

	public static final  String PRICERULE_EXPRESSION_ADD_WEIGHT = "AW"; // 续重（体积）

	public static final  String PRICERULE_EXPRESSION_FIRST_WEIGHT_PRICE = "FP"; // 首重（体积）单价

	public static final  String PRICERULE_EXPRESSION_ADD_WEIGHT_PRICE = "AP"; // 续重（体积）单价

	public static final  String PRICERULE_EXPRESSION_DISCOUNT = "DC"; // 折扣率

	public static final String PRICERULE_EXPRESSION_PARAM1 = "PARAM1"; // 变量1

	public static final String PRICERULE_EXPRESSION_PARAM2 = "PARAM2"; // 变量2

	public static final String PRICERULE_EXPRESSION_PARAM3 = "PARAM3"; // 变量3

	public static final String PRICERULE_EXPRESSION_PARAM4 = "PARAM4"; // 变量4

	public static final String PRICERULE_EXPRESSION_PARAM5 = "PARAM5"; // 变量5

	public static final String PRICE_BOTTOM_LINE = "BL"; // 最低收费价格

    }
	/**
	 * 计价条目相关
	 */
    public static class PriceEntityConstants {

    	
   	 /**
   	 *  菜鸟原折扣
   	 */
   	public static final String OLD_PREFE ="OLD_PREFE";
   	/**
   	 *  菜鸟原价折扣
   	 */
   	public static final String OLD_PRICE_PREFE ="OLD_PRICE_PREFE";
   	/**
   	 *  菜鸟新折扣
   	 */
   	public static final String NEW_PREFE ="NEW_PREFE";
   	/**
   	 *  菜鸟单票固定价
   	 */
   	public static final String FIXED_PRICE ="FIXED_PRICE";
   	/**
   	 *  菜鸟原价
   	 */
   	public static final String OLD_PRICE ="OLD_PRICE";
	/**
	 * 总费用
	 */
	public static final String PRICING_CODE_TOTAL = "TOTAL";
	
	/** 运费 **/
	public static final String PRICING_CODE_FRT = "FRT";

	/** 到付费 **/
	public static final String PRICING_CODE_FRTFREE = "FRTFREE";
	
	/** 应付费 **/
	public static final String PRICING_CODE_PAYFREE = "PAYFREE";
	
	/** 应收费 **/
	public static final String PRICING_CODE_RECEIVEFREE = "RECEIVEFREE";
	
	/** 快递代理返货费类型 **/
	public static final String PRICING_CODE_RETURNFREE = "RETURNFREE";

	/** 增值服务**/
	public static final String PRICING_CODE_VALUEADDED = "VAS";

	/** 保费 **/
	public static final String PRICING_CODE_BF = "BF";

	/** 代收货款 **/
	public static final String PRICING_CODE_HK = "HK";

	/** 送货费 **/
	public static final String PRICING_CODE_SH = "SH";
	
	
	/** 送货上楼费 **/
	public static final String PRICING_CODE_SHSL = "SHSL";
	
	/**
	 * 大件上楼费
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-12-15下午15:45
	 */
	public static final String PRICING_CODE_DJSL="DJSL";
	
	/** 送货进仓费 **/
	public static final String PRICING_CODE_SHJC = "SHJCF";
	
	public static final String QT_CODE_CZHCZFWF="CZHCZFWF";
	public static final String QT_CODE_CZHCZFWF_SDTJ="CZHCZFWF_SDTJ";
	
	/** 超远派送费 **/
	public static final String  PRICING_CODE_CY="CY";

	/** 接货费 **/
	public static final String PRICING_CODE_JH = "JH";

	/** 签收回单 **/
	public static final String PRICING_CODE_QS = "QS";

	/** 仓储费 **/
	public static final String PRICING_CODE_CCF = "CCF";

	/** 其他费用 **/
	public static final String PRICING_CODE_QT = "QT";
	
	/** 包装费用 **/
	public static final String PRICING_CODE_BZ = "BZ";
	
	/** 中转费 **/
	public static final String PRICING_CODE_ZZ = "ZZ";
	
	/** 更改费 **/
	public static final String PRICING_CODE_GGF = "GGF";
	
	/** 电子优惠券 **/
	public static final String PRICING_CODE_DZYHQ = "DZYHQ";
	
	/** 偏线中转费 **/
	public static final String PRICING_CODE_PXZZF = "PXZZF";
	
	/** 超远派送加收(归集到送货费中) **/
	public static final String PRICING_CODE_CCDDJS = "CCDDJS";
	/** 对外投保保费收入(归集到保费中) **/
	public static final String PRICING_CODE_DWTBF = "DWTBF";
	/** 空运运费冲减(归集到运费中) **/
	public static final String PRICING_CODE_KYYFCJ = "KYYFCJ";
	/** 燃油附加费  **/
	public static final String PRICING_CODE_RYFJ = "RYFJ";
	/** 综合信息费 **/
	public static final String PRICING_CODE_ZHXX = "ZHXX";
	/** 其它费用加收 **/
	public static final String PRICING_CODE_QTFYJS = "QTFYJS";
	/** 快递包装费 */
	public static final String PRICING_CODE_KDBZF = "KDBZF";
	/**安装费 */
	public static final String PRICING_CODE_JZ = "JZ";
	/** 送货安装费 */
	public static final String PRICING_CODE_SHAZ = "SHAZ";
	
	/**
	 * 专业市场自寄费
	 */
	public static final String PRICING_CODE_ZYSCZJH = "ZYSCZJH";
	
	/**
	 * 整车运费冲减
	 */
	public static final String PRICING_CODE_ZCYFCJ = "ZC04";
	/**
     * 纸纤包装
     * @author:218371-foss-zhaoyanjun
     * @date:2014-11-25下午20:56
     */
    public static final String PRICING_CODE_ZQBZ="ZQBZ";
    /**
     * 至尊宝保费费用冲减
     * @author:218371-foss-zhaoyanjun
     * @date:2015-04-14下午14:45
     */
    public static final String PRICING_CODE_ZZB="BJJTFLDJCJ";
    }
	
      /**
       * 
       * 计价产品
       * @author DP-Foss-YueHongJie
       * @date 2012-11-2 上午10:22:32
       */
      public static class ProductEntityConstants {
	  
	/**产品运输类型**/
	
	/** 产品等级  LEVELS说明 ***/
	  
	/* 一级产品 */
	public static final Long PRICING_PRODUCT_LEVEL_1 = 1L;
	/* 二级产品 */
	public static final Long PRICING_PRODUCT_LEVEL_2 = 2L;
	/* 三级产品 */
	public static final Long PRICING_PRODUCT_LEVEL_3 = 3L;
	
	  
	/***一级产品总产品分类*****/
	//一级产品-汽运
	public static final String PRICING_PRODUCT_C1_C20001 = "TRANS_VEHICLE";//  C10001
	
	//一级产品-空运
	public static final String PRICING_PRODUCT_C1_C20002 = "TRANS_AIRCRAFT";//  C10002
	//一级产品-快递
	public static final String PRICING_PRODUCT_C1_C20003 = "TRANS_EXPRESS";//  C10003
	//一级产品-汽运
	public static final String PRICING_PRODUCT_C1_C20001_ZH = "汽运";//  C10001
	
	//一级产品-空运
	public static final String PRICING_PRODUCT_C1_C20002_ZH = "空运";//  C10002
	//一级产品-快递
	public static final String PRICING_PRODUCT_C1_C20003_ZH = "德邦快递";//  C10003
	
	/***二级产品主要针对价格*****/
	//二级产品-精准
	public static final String PRICING_PRODUCT_C2_C20001 = "RECISION_PRODUCTS";// C20001

	//二级产品-普货
	public static final String PRICING_PRODUCT_C2_C20002 = "COMMON_PRODUCTS";//C20002

	//二级产品-整车
	public static final String PRICING_PRODUCT_C2_C20003 = "WHOLE_VEHICLE";// C20003

	//二级产品-空运
	public static final String PRICING_PRODUCT_C2_C20004 = "AIR_FREIGHT";//C20004
	//二级产品-快递
	public static final String PRICING_PRODUCT_C2_C20005 = "EXPRESS";//EXPRESS
	//二级产品-精准大票
	public static final String PRICING_PRODUCT_C2_C20006 = "RECISION_BG_PRODUCTS";//zxy 20140623 新增
	//二级产品-普通大票
	public static final String PRICING_PRODUCT_C2_C20007 = "COMMON_BG_PRODUCTS";//zxy 20140623 新增
	//二级产品-门到门
	public static final String PRICING_PRODUCT_C2_C20008 = "BIG_DOOR_TO_DOOR";//C20008
	//二级产品-场到场
	public static final String PRICING_PRODUCT_C2_C20009 = "BIG_YARD_TO_YARD";//C20009
	//二级产品-电商尊享
	public static final String PRICING_PRODUCT_C2_C20010 ="EXPRESS_EPEP";
	//二级产品-标准空运件/商务专递
	public static final String PRICING_PRODUCT_C2_C20011 ="EXPRESS_DEAP";
	
	public static final String PRICING_PRODUCT_C2_C20012 ="RECISION_EC_PRODUCTS";
	/***三级产品主要针对时效*****/
	/*
        1、精准空运 Air Freight
        2、精准汽运（长途）Long Distance Road Freight
        3、精准卡航 Long Distance Fast Freight
        4、精准城运 Short Distance Fast Freight
        5、汽运偏线 Partial Line （中转的翻译）
        6、精准汽运（短途）Short Distance Road Freight
        
                            精准汽运（长途）LRF
                            精准卡航 FLF
                            精准汽运（短途）SRF
                            精准城运 FSF
                            汽运偏线 PLF
                            精准空运 AF
							 精准大票卡航BGFLF
                           精准大票城运BGFSF 
                           精准大票汽运(长)BGLRF                                         
                           精准大票汽运(短)BGSRF                                 
	 */
	//三级产品-精准卡航
	public static final String PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT = "FLF";//C30001

	//三级产品-精准城运
	public static final String PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT = "FSF";//C30002

	//三级产品-精准汽运(长途)
	public static final String PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT = "LRF";//C30003

	//三级产品-精准汽运(短途)
	public static final String PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT = "SRF";//  C30004
	
	//三级产品-汽运偏线
	public static final String PRICING_PRODUCT_PARTIAL_LINE = "PLF";//  C30007
	
	//三级产品-整车
	public static final String PRICING_PRODUCT_FULL_VEHICLE = "WVH";//C30005 

	//整车三级
	public static final String PRICING_PRODUCT_FULL_VEHICLE_ZHENGCHE = "TRANS_zhengche";
	
	//三级产品-精准空运
	public static final String PRICING_PRODUCT_AIR_FREIGHT = "AF";// C30006
	//三级产品-精准大票卡航
	public static final String PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG = "BGFLF";// C30008
	
	//三级产品-精准大票城运
	public static final String PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG = "BGFSF";// C30009
	
	//三级产品-精准大票汽运(长)
	public static final String PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG = "BGLRF";// C30010
	
	//三级产品-精准大票汽运(短)
	public static final String PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG = "BGSRF";// C30011
	
	//合票方式-单独开单
	public static final String PRICING_PRODUCT_FREIGNT_DDKD = "DDKD";// C30006 
	
	//合票方式-合大票
	public static final String PRICING_PRODUCT_FREIGNT_HDP = "HDP"; 
	//三级产品-快递包裹
	public static final String PRICING_PRODUCT_EXPRESS_PACKAGE = "PACKAGE";// PACKAGE
	//三级产品 3.60 特惠件
	public static final String PRICING_PRODUCT_ROUND_COUPON_PACKAGE ="RCP";
	//三级产品-电商尊享件
	public static final String ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE ="EPEP";
	//三级产品 门到门
	public static final String PRICING_PRODUCT_DOOR_TO_DOOR ="DTD";
	//三级产品 场到场
	public static final String PRICING_PRODUCT_YARD_TO_YARD ="YTY";
	//三级产品 快递空运
	public static final String PRICING_PRODUCT_EXPRESS_AIR_FREIGHT="DEAP";
	//快递报关通-标
	public static final String PRICING_PRODUCT_EXPRESS_GTSE = "GTSE";
	//国际快递-标
	public static final String PRICING_PRODUCT_EXPRESS_ICES = "ICES";
	//快递报关通-快
	public static final String PRICING_PRODUCT_EXPRESS_GTEC = "GTEC";
	//国际快递-标
	public static final String PRICING_PRODUCT_EXPRESS_ICEC = "ICEC";

	//三级产品 精准包裹
	public static final String PRICING_PRODUCT_PCP = "PCP";
	// 快递产品运输性质集合
	public static final List<String> EXPRESS_PRODUCT_CODE_LIST = new ArrayList<String>();
	static {
			EXPRESS_PRODUCT_CODE_LIST.add(PRICING_PRODUCT_EXPRESS_PACKAGE);
			EXPRESS_PRODUCT_CODE_LIST.add(PRICING_PRODUCT_ROUND_COUPON_PACKAGE);
			EXPRESS_PRODUCT_CODE_LIST.add(ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
			EXPRESS_PRODUCT_CODE_LIST.add(PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
			EXPRESS_PRODUCT_CODE_LIST.add(PRICING_PRODUCT_EXPRESS_GTSE);
			EXPRESS_PRODUCT_CODE_LIST.add(PRICING_PRODUCT_EXPRESS_ICES);
			EXPRESS_PRODUCT_CODE_LIST.add(PRICING_PRODUCT_EXPRESS_GTEC);
			EXPRESS_PRODUCT_CODE_LIST.add(PRICING_PRODUCT_EXPRESS_ICEC);
		}
	
      }
      
      
      /**
       * 
       * 货物常量
       * @author DP-Foss-YueHongJie
       * @date 2012-11-12 上午9:12:24
       */
      public static class GoodsTypeEntityConstants{
	  
	  /**
	   *  所有
	   */
	  public static final String GOODSTYPE_GENERAL_H00001 = "H00001";
	  
	  /**
	   *  贵重 
	   */
	  public static final String GOODSTYPE_VALUABLE_H00002 = "H00002";	  
	  
	  /**
	   *  海鲜 
	   */
	  public static final String GOODSTYPE_SEAFOOD_H00003 = "H00003";
      }
      
	public static class DiscountTypeConstants {
		/**
		 * 
		 * 客户合同(大类)
		 * 
		 */
		public static final String DISCOUNT_TYPE__CUSTOMER_CONTRACT = "CUSTOMER_CONTRACT";

		public static final String DISCOUNT_TYPE__CUSTOMER_CONTRACT_NAME = "客户合同";
		//客户合同小类
		public static final String DISCOUNT_TYPE__CONTRACT_NORMAL = "CONTRACT_NORMAL";

		public static final String DISCOUNT_TYPE__CONTRACT_NORMAL_NAME = "普通合同";
		//客户合同小类
		public static final String DISCOUNT_TYPE__MONTH_SEND = "MONTH_SEND";

		public static final String DISCOUNT_TYPE__MONTH_SEND_NAME = "月发越送";
		//普元规则引擎折扣code
		public static final String DISCOUNT_TYPE_RULE_CODE ="DISCOUNT_RULE";
        //梯度折扣
		public static final String DISCOUNT_TYPE__FRT_GRADESREBATE = "FRT_GRADESREBATE";
		public static final String DISCOUNT_TYPE__FRT_GRADESREBATE_NAME = "客户合同-梯度折扣";
		/**
		 * 
		 * 产品
		 * 
		 */
		public static final String DISCOUNT_TYPE__PRODUCT = "PRODUCT";

		public static final String DISCOUNT_TYPE__PRODUCT_NAME = "产品折扣";
		/**
		 * 
		 * 渠道
		 * 
		 */
		public static final String DISCOUNT_TYPE__CHANEL = "CHANNEL";

		public static final String DISCOUNT_TYPE__CHANEL_NAME = "渠道折扣";
		
		/**
		 * 
		 * CRM营销活动
		 * 
		 */
		public static final String DISCOUNT_TYPE__ACTIVE = "CRM_ACTIVE";

		public static final String DISCOUNT_TYPE__ACTIVE_NAME = "CRM推广活动";
		/**
	      * 新加一种折扣类型，员工折扣 
	      */
		public static final String DISCOUNT_TYPE__INEMP = "INEMP_DELIVERY";

		public static final String DISCOUNT_TYPE__INEMP_NAME = "内部发货折扣";
		
	}
      
       /**
        * 
        * 折扣常量类
        * @author DP-Foss-YueHongJie
        * @date 2013-3-17 上午11:55:36
        * @version 1.0
        */
       public static class DiscountOrgConstants {
		/**
		 * 折扣区域
		 */
		public static final String DISCOUNT_ORG_REGION = "DISCOUNT_REGION";
		/**
		 * 折扣城市
		 */
		public static final String DISCOUNT_ORG_CITY = "DISCOUNT_CITY";
		/**
		 * 折扣网点
		 */
		public static final String DISCOUNT_ORG_DEPT = "DISCOUNT_DEPT";
		
		//快递运费续重最低费率
		public static final String PKP_EXPRESS_MIN_FEERATE ="EXPRESS_MIN_FEERATE";
		//快递运费续重最低费率校验开始时间
	    public static final String EXPRESS_MIN_FEERATE_START_DATE ="EXPRESS_MIN_FEERATE_START_DATE";
	    //快递定价开始时间
	    public static final String EXPRESS_PRICING_START_DATE ="EXPRESS_PRICING_START_DATE";
	    //快递价格版本开始时间
	    public static final String EXPRESS_VERSION_PRICING_START_DATE="EXPRESS_VERSION_PRICING_START_DATE";
	}
	
	public static class PricingColumnConstants {
	    //时效方案明细导出
	    public static final String EXPORT_EFFECTIVE_PLAN_DETAIL = "时效方案明细.xls";
	    //时效方案明细表头
	    public static final String[] EFFECTIVE_PLAN_DETAIL_TITLE = {"到达区域","运输性质","承诺最小天数/时间","承诺最大天数/时间","到达营业部承诺时点","是否驻地部门","长短途","派送承诺时点","承诺派送需加天数","备注"};

	    //整车价格参数方案导出
	    public static final String EXPORT_CARLOAD_PRICE_PLAN = "整车价格参数方案.xls";
	    //整车价格参数方案导出
	    public static final String EXPORT_CARLOAD_PRICE_PLAN_DETAIL = "整车价格参数方案明细.xls";
	    //整车方案表头
	    public static final String[] CARLOAD_PRICE_PLAN_TITLE = {"方案名称","组织","组织编码","修改时间","修改人","生效日期","截止日期","数据状态","是否当前版本"};
	    //整车方案明细表头
	    public static final String[] CARLOAD_PRICE_PLAN_DETAIL_TITLE = {"方案名称","组织","发票标记","整车价格波动向上浮动值","整车价格波动向下浮动值","其他成本参数","包装费参数","重量参数","车价参数","人力成本参数","备注"};
	    //价格方案明细导出
	    public static final String EXPORT_PRICE_PLAN = "价格方案.xls";
	    //时效方案明细表头
	    public static final String[] PRICE_PLAN_TITLE = {"方案名称","始发区域","修改时间","修改人","生效日期","截止日期","数据状态"};
	    //快递价格时效方案明细表头
	    public static final String[] EXP_PRICE_PLAN_TITLE = {"客户名称","客户编码","方案名称","始发区域","修改时间","修改人","生效日期","截止日期","数据状态"};
	    //精准大票价格方案导出
	    public static final String[] BIG_PRICE_PLAN_TITLE = {"方案名称","始发区域","修改时间","修改人","生效日期","截止日期","数据状态","是否当前版本"};
	    //首续重价格方案导出--2016.07.19新增
	    public static final String[] EC_PRICE_PLAN_TITLE = {"方案名称","始发区域","修改时间","修改人","生效日期","截止日期","数据状态","是否当前版本"};
	    //价格方案明细导出
	    public static final String EXPORT_PRICE_PLAN_DETAIL = "价格方案明细.xls";
	    //价格方案比对结果导出
	    public static final String EXPORT_PRICE_PLAN_COMPARE_RESULT = "价格方案比对结果.xls";
	    //价格方案比对结果表头
	    public static final String[] EXPORT_PRICE_PLAN_COMPARE_RESULT_TITLE = {"目的站","产品条目","重货价格","轻货价格","最低一票","是否接货"};
	    //首续重价格方案比对结果表头-2016.07.19添加
	    public static final String[] EXPORT_EC_PRICE_PLAN_COMPARE_RESULT_TITLE = {"目的站","产品条目","重货价格","轻货价格","最低一票","是否接货","是否送货"};
	    //时效方案明细表头 [pop] modify 148246-孙建博
	    //public static final String[] PRICE_PLAN_DETAIL_TITLE = {"目的站","产品条目","重货价格","轻货价格","最低一票","是否接货","备注"};
	    public static final String[] PRICE_PLAN_DETAIL_TITLE = {"目的站","产品条目","最低一票","重货价格Ⅰ","轻货价格Ⅰ","重量临界值Ⅱ","体积临界值Ⅱ","重货价格Ⅱ","轻货价格Ⅱ","重量临界值Ⅲ","体积临界值Ⅲ","重货价格Ⅲ","轻货价格Ⅲ","重量临界值Ⅳ","体积临界值Ⅳ","重货价格Ⅳ","轻货价格Ⅳ","重量临界值Ⅴ","体积临界值Ⅴ","重货价格Ⅴ","轻货价格Ⅴ","重量临界值Ⅵ","体积临界值Ⅵ","重货价格Ⅵ","轻货价格Ⅵ","是否接货","备注"};
		//大票价格方案明细表头
	    public static final String[] PRICE_BIG_PLAN_DETAIL_TITLE = {"目的站","产品条目","计费规则","开始范围","结束范围","固定费用","价格","是否接货","备注"};
	    //首续重方案明细表头-2016.07.19添加
	    public static final String[] PRICE_EC_PLAN_DETAIL_TITLE = {"目的站","产品条目","计费类型","下限","上限","费率","首重","是否包含接货","是否包含送货","备注"};
	    
	    public static final String EXPORT_AIR_PRICE_PLAN_DETAIL = "空运价格方案明细.xls";
	    //空运明细方案到处
	    //zxy 20140508 MANA-1253 修改:新增合票类型
	    public static final String[] AIR_PRICE_PLAN_DETAIL_TITLE = {"目的站","航班类型","合票类型","货物类型","重货价格","最低一票","是否接货","备注"};

	    /** 偏线价格方案导出 */
	    public static final String EXPORT_TOADDPARTNERPROGRAM_PLAN = "合伙人到达加收方案.xls";
	    /** 偏线价格方案导出表头 */
	    public static final String[] TOADDPARTNERPROGRAM_PLAN_TITLE = {"方案名称","营业部","运输类型","重货价格","轻货价格","最低一票","数据状态","生效日期","截止日期","修改时间","修改人","是否当前版本"};

	    
	    /** 偏线价格方案导出 */
	    public static final String EXPORT_OUTER_PRICE_PLAN = "偏线价格方案.xls";
	    /** 偏线价格方案导出表头 */
	    public static final String[] OUTER_PRICE_PLAN_TITLE = {"方案名称","目的站","外发外场","运输类型","省份","城市","区县","重货价格","轻货价格","最低一票","数据状态","生效日期","截止日期","修改时间","修改人","是否当前版本"};
	    /** 偏线时效方案导出 */
	    public static final String EXPORT_OUTER_EFFECTIVE_PLAN = "偏线时效方案.xls";
	    /** 偏线时效方案导出表头 */
	    public static final String[] OUTER_EFFECTIVE_PLAN_TITLE = {"方案名称","目的站","外发外场","运输类型","数据状态","生效日期","截止日期","是否当前版本","省份","城市","区县","承诺最大天数/时间","承诺最小天数/时间","到达代理网点承诺时点","派送增加天数","承诺派送时点","修改时间","修改人"};
	    
	    //快递价格方案明细表头
	    public static final String[] EXPRESS_PRICE_PLAN_DETAIL_TITLE = {"目的区域","产品条目","首重","续重1重量下限","续重1重量上限","续重1费率","续重2重量下限","续重2重量上限","续重2费率","是否自提"};
		/** 偏线外发价格方案导出*/
	    public static final String EXPORT_EXTERNAL_PRICE_SCHEME="偏线外发价格方案.xls";
	    /** 偏线价格方案导出表头 */
	    public static final String[] EXPORT_EXTERNAL_PRICE_TITLE = {"方案名称","目的站","外发外场","运输类型","省份","城市","区县","重货价格","轻货价格","最低一票","数据状态","生效日期","截止日期","修改时间","修改人","是否当前版本"};
	   // public static final String[] EXPORT_EXTERNAL_PRICE_TITLE={"序号","方案名称","目的站","运输类型","外发外场","重货费率","轻货费率","最低一票","生效日期","截止日期"};
	
	    public static final String TABLE_NAME_VALUEADD="T_POP_VALUEADDED";
	    //zb modify start
	    public static final String[] PRICE_PLAN_DETAIL_TITLE_KH = {"始发站","目的站","产品条目","最低一票","重货价格","轻货价格","是否接货","备注"};
	    //zb modify end
	  //zb modify start
	    //客户方案明细表头
	    public static final String[] KH_PRICE_PLAN_TITLE = {"客户名称","客户编码","方案名称","修改时间","修改人","生效日期","截止日期","数据状态"};
	    //周
	    private static final String[] WEEKS = {"全周","周日","周一","周二","周三","周四","周五","周六"};
	    //周编码
	    private static final String[] WEEKSCODE = {"ALL","SUN","MON","TUE","WED","THU","FRI","SAT"};
	    //周Map
	    private static Map<String,String> weeksMap ; 
	    
	    //获取周Map
		public static Map<String, String> getALLWeeksMap() {
			weeksMap = new HashMap<String,String>();
			for(int week = ZERO ; week < WEEKS.length ; week++){
	    		weeksMap.put(WEEKS[week], WEEKSCODE[week]);
			}
	    	return weeksMap;
		}
		
		
	}

	public static boolean directDetermineIsExpressByProductCode(
			String productCode) {
		//判定数据是否为空
				if(StringUtils.isEmpty(productCode)){
					return false;
				}
				//在人为判定是否相等
				if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(productCode)
						|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(productCode)
						|| PricingConstants.ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(productCode)
						|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(productCode)){
					return true;
				}
				return false;
	}
}
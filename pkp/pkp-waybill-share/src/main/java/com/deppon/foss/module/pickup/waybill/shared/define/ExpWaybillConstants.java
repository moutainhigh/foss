/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.define
 * FILE    NAME: ExpWaybillConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.define;

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;


/**
 * 快递的常量类
 * @author 026123-foss-lifengteng
 * @date 2013-8-7 上午11:38:27
 */
public class ExpWaybillConstants {
	
	/**
	 *  城市类型  落配载
	 */
	public final static String CITY_TYPE_PEIZAI=DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP;
	
	/**
	 * 城市类型  试点
	 */
	public final static String CITY_TYPE_SHIDIAN=DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT;

	/**
	 * 体积上限
	 */
	public static final String EXPRESS_VOLUME_RANGE_UP = "1101";
	
	/**
	 * 体积下限
	 */
	public static final String EXPRESS_VOLUME_RANGE_LOW = "1102";
	
	/**
	 * 体积
	 */
	public static final String EXPRESS_VOLUME_RANGE_RATE = "1103";

	/**
	 * 快递保价申明价值上限
	 */
	public static final String EXPRESS_INSURRANCE_UP = "1104";
	
	/**
	 * 快递保费价值上限
	 */
	public static final String EXPRESS_INSURRANCE_FEE_UP = "1105";
	/**
	 *  代收货款上限
	 */
	public static final String EXPRESS_CODE_FEE_UP = "1118";
	
	/**
	 * 包裹
	 */
	public static final String PACKAGE = "PACKAGE";
	
	/**
	 * 3.60 特惠件
	 */
	public static  final String ROUND_COUPON_PACKAGE ="RCP";
	/**
	 * 商务件--快递空运项目
	 */
	public static  final String EXPRESS_SWJ ="SWJ";
	
	/**
	 * 快递空运--快递空运项目
	 */
	public static  final String AIR_EXPRESS__PACKAGE ="AEP";
	
	/**
	 * 淘宝
	 */
	public static final String CRM_ORDER_CHANNEL_TAOBAO ="TAOBAO";
	
	/**
	 * 阿里巴巴
	 */
	public static final String CRM_ORDER_CHANNEL_ALIBABA ="ALIBABA";
	
	/**
	 * 淘宝商城
	 */
	public static final String CRM_ORDER_CHANNEL_SHANGCHENG ="SHANGCHENG";
	
	/**
	 * 送货进仓
	 */
	public static final String OTHERFEE_SHJCF ="SHJCF";
	public final static  String  TRANS_EXPRESS = "TRANS_EXPRESS";
	/**
	 *  快递包装费
	 */
	public static final String OTHERFEE_KDBZF ="KDBZF";
	
	/**
	 *  快递默认包装
	 */
	public static final String GOOD_PACKAGE_DEFAULT ="纸";
	
	/**
	 * 快递提货方式开关  是否只显示内部带货自提一种
	 */
	public static final String EXPRESS_PICKUP_TURN = "1117";

	/**
	 * 快递最大下载线程数  不能使用final
	 */
	public static int count = 0;
	
	public static final  String QUERY_ALL="全部";

	/**
	 * 快递单件重量上限
	 */
	public static final double WEIGHT_UPPER_LIMIT = 50;
	
	/**
	 * 快递单件体积上限
	 */
	public static final double VOLUME_UPPER_LIMIT = 0.3;
	
	/**
	 * 根据传递过来的对应的三级产品编码判定是否快递
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-3 19:01:13
	 */
	public static boolean directDetermineIsExpressByProductCode(String productCode){
		//判定数据是否为空
		if(productCode == null || "".equals(productCode)){
			return false;
		}
		//在人为判定是否相等
		if(WaybillConstants.PACKAGE.equals(productCode)
				|| WaybillConstants.ROUND_COUPON_PACKAGE.equals(productCode)
				|| WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(productCode)
				|| WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(productCode)){
			return true;
		}
		return false;
	}
}

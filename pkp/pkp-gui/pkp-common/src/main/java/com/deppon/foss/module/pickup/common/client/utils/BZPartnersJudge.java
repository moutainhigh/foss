package com.deppon.foss.module.pickup.common.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.common.client.define.BZPartnersConstants;

/**
 * 合伙人常用判断（零担+快递）
 * @author 272311
 * @date 2016-1-6
 */
public class BZPartnersJudge {
	
	//************注意：如果本类中添加了属性一定要在init()和initBZPartnerPrower()方法里添加初始化*************//
	private static Logger logger = LoggerFactory.getLogger(BZPartnersJudge.class);
	/**
	 * 内部发货
	 */
	public static  boolean INTERNAL_DELIVERY_EXP = false ;
	
	/**
	 * 快递员工号
	 */
	public static  boolean COURIER_CODE_EXP = false ;
	
	/**
	 * 优惠编码
	 */
	public static  boolean PRIVILEGE_CONDE_EXP = false;
	
	/**
	 * 市场推广活动 
	 */
	public static  boolean GENERALIZE_ACTIVITY_EXP = false ;
	
	/**
	 * 是否是合伙人
	 */
	public static boolean IS_PARTENER = false ;
	
	/**
	 * 司机功能
	 */
	public static boolean DRIVER_CODE = false ;
	
	/**
	 * 精准大票
	 */
	public static  boolean BIG_GOODS = false ;
	
	/**
	 * 内部发货
	 */
	public static  boolean INTERNAL_DELIVERY = false ;
	
	
	/**
	 * 提货方式：内部带货自提 INNER_PICKUP
	 */
//	public static  boolean INNER_PICKUP = false ;
	
	/**
	 * 优惠编码
	 */
	public static  boolean PRIVILEGE_CONDE = false ;
	
	/**
	 * 市场推广活动 
	 */
	public static  boolean GENERALIZE_ACTIVITY = false ;
	
	/**
	 * 装卸费 
	 */
	public static  boolean SERVICE_RATE = false ;
	
	/**
	 * 网上支付 
	 */
	public static  boolean ONLINE_PAYMENT = false ;
	
	/**
	 * 流水号 
	 */
	public static  boolean TRANS_SERIAL_NUMBER = false ;
	
//	“新点10元优惠券”、“城际特惠”、“家居配送折扣”和“VIP/全网活跃线路营销”
	
	/**
	 * 优惠合计
	 */
	public static  boolean PRIVILEGE_TOTAL = false ;
	
	/**
	 * 更改单 内部要求
	 */
	public static boolean INTERNAL_REQUIRE_RFC = false ;
	
	/**
	 * 更改单 目的站
	 */
	public static boolean DETINATION_STATION_RFC = false;
	
	/**
	 * 如果上面添加了属性一定要在这里进行初始化
	 * @author 272311-sangwenhao
	 * @date 2016-1-9
	 */
	public static void init(){
		BZPartnersJudge.BIG_GOODS = false ;
		BZPartnersJudge.COURIER_CODE_EXP = false ;
		BZPartnersJudge.DRIVER_CODE = false ;
		BZPartnersJudge.GENERALIZE_ACTIVITY = false ;
		BZPartnersJudge.GENERALIZE_ACTIVITY_EXP = false ;
//		BZPartnersJudge.INNER_PICKUP = false ;
		BZPartnersJudge.INTERNAL_DELIVERY = false ;
		BZPartnersJudge.IS_PARTENER = false ;
		BZPartnersJudge.ONLINE_PAYMENT = false ;
		BZPartnersJudge.PRIVILEGE_CONDE = false ;
		BZPartnersJudge.PRIVILEGE_CONDE_EXP = false ;
		BZPartnersJudge.PRIVILEGE_TOTAL = false ;
		BZPartnersJudge.SERVICE_RATE = false ;
		BZPartnersJudge.TRANS_SERIAL_NUMBER = false ;
		BZPartnersJudge.INTERNAL_REQUIRE_RFC = false ;
		BZPartnersJudge.DETINATION_STATION_RFC = false ;
		BZPartnersJudge.INTERNAL_DELIVERY_EXP = false ;
	}
	
	/**
	 * 初始化合伙人权限信息
	 * @return 是否完全执行成功 
	 * @author 272311-sangwenhao
	 * @date 2016-1-11
	 */
	public static boolean initBZPartnerPrower(){
		logger.info("初始化合伙人信息 调用 initBZPartnerPrower ");
		boolean flag = true ;
		try {
			BZPartnersContextUtil bzPartnersContextUtil = BZPartnersContextUtil.getBzPatnersContext();
			
//		BZPartnersJudge.init() ;//初始化配置参数
			//判断是否为合伙人
			if(bzPartnersContextUtil.isPartner()){
				BZPartnersJudge.IS_PARTENER = true ;
			}
			//是合伙人，则再判断 控件权限
			if(BZPartnersJudge.IS_PARTENER){
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.DRIVER_CODE)){
					BZPartnersJudge.DRIVER_CODE = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.BIG_GOODS)){
					BZPartnersJudge.BIG_GOODS = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.GENERALIZE_ACTIVITY)){
					BZPartnersJudge.GENERALIZE_ACTIVITY = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.INTERNAL_DELIVERY)){
					BZPartnersJudge.INTERNAL_DELIVERY = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.ONLINE_PAYMENT)){
					BZPartnersJudge.ONLINE_PAYMENT = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.PRIVILEGE_CONDE)){
					BZPartnersJudge.PRIVILEGE_CONDE = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.PRIVILEGE_TOTAL)){
					BZPartnersJudge.PRIVILEGE_TOTAL = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.SERVICE_RATE)){
					BZPartnersJudge.SERVICE_RATE = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.TRANS_SERIAL_NUMBER)){
					BZPartnersJudge.TRANS_SERIAL_NUMBER = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.DETINATION_STATION_RFC)){
					BZPartnersJudge.DETINATION_STATION_RFC = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.INTERNAL_REQUIRE_RFC)){
					BZPartnersJudge.INTERNAL_REQUIRE_RFC = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.COURIER_CODE_EXP)){
					BZPartnersJudge.COURIER_CODE_EXP = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.GENERALIZE_ACTIVITY_EXP)){
					BZPartnersJudge.GENERALIZE_ACTIVITY_EXP = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.PRIVILEGE_CONDE_EXP)){
					BZPartnersJudge.PRIVILEGE_CONDE_EXP = true ;
				}
				if(bzPartnersContextUtil.isBZPartnerPower(BZPartnersConstants.INTERNAL_DELIVERY_EXP)){
					BZPartnersJudge.INTERNAL_DELIVERY_EXP = true ;
				}
			}
			
		} catch (Exception e) {
			flag = false ;
			logger.error("初始化权限信息异常 initBZPartnerPrower："+e.getMessage());
		}
		logger.info("初始化合伙人信息  initBZPartnerPrower 初始化完成 。");
		return flag ; 
	}

}

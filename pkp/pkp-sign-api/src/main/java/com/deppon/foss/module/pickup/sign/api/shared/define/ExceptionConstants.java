package com.deppon.foss.module.pickup.sign.api.shared.define;
/**
 * 物流服务预警产品 异常签收 货物拉回原因 推送运单号和异常类型至CRM
 * @author 159231
 * @date 2015-3-26 上午11:10:20
 */
public class ExceptionConstants {
	
	/** 
	 *  拉回原因---节假日延迟   描述
	 */
	public static final String HOLIDAY_DELAY_DESC = "R04";
	/** 
	 *  拉回原因---客户自提   描述
	 */
	public static final String CUSTOMER_PICKUP_DESC = "R06";
	/** 
	 *  拉回原因---开单地址有误/电话录入错误  描述
	 */
	public static final String PHONE_WRONG_DESC = "R01";
	
	/** 
	 *  拉回原因 ---无法进入单位派送  描述
	 */                        
	public static final String NOT_IN_UNIT_DELIVER_DESC = "R05";
	/** 
	 *  营业员在FOSS选择签收类型为‘异常签收-破损’或者快递员在PDA选择签收类型为‘异常签收-破损’，
	 *  推送运单号和异常类型至CRM，异常类型为‘派送前破损’；
	 */
	public static final String DELIVER_BEFORE_BREAK_DESC = "派送前破损";

	/** 
	 *  拉回原因---节假日延迟
	 */
	public static final String HOLIDAY_DELAY = "HOLIDAY_DELAY";
	/** 
	 *  拉回原因---客户自提
	 */
	public static final String CUSTOMER_PICKUP = "CUSTOMER_PICKUP";
	/** 
	 *  拉回原因---开单地址有误/电话录入错误
	 */
	public static final String PHONE_WRONG = "PHONE_WRONG";
	
	/** 
	 *  拉回原因 ---无法进入单位派送
	 */                        
	public static final String NOT_IN_UNIT_DELIVER = "NOT_IN_UNIT_DELIVER";
}
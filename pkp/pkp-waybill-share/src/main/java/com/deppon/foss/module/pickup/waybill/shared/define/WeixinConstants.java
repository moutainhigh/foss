package com.deppon.foss.module.pickup.waybill.shared.define;

/**
 * @function 微信发送状态常量
 * @author Foss-105888-Zhangxingwang
 * @date 2014-1-11 10:25:19
 *
 */
public class WeixinConstants {	
	/**开单 
	 */
	public static final String WEIXIN_CREATE_TYPE = "CREATE";
	/**更改 
	 */
	public static final String WEIXIN_WAYBILLRFC_TYPE = "WAYBILLRFC";
	/**到达
	 */
	public static final String WEIXIN_DESTARRIVED_TYPE = "ARRIVE";
	/**分批到达
	 */
	public static final String WEIXIN_PART_DESTARRIVED_TYPE = "PART_ARRIVE";
	/**派送
	 */
	public static final String WEIXIN_DELIVER_TYPE = "DELIVERY";
	/**签收 
	 */
	public static final String WEIXIN_SIGN_TYPE = "SIGN";
	/**派送拉回
	 */
	public static final String WEIXIN_DELIVER_RETURN_TYPE = "DELIVERY_RETURN";
	/**正常签收
	 */
	public static final String WEIXIN_NORMAL_SIGN_TYPE = "NORMAL_SIGN";
	/**异常签收 
	 */
	public static final String WEIXIN_EXCEPTION_SIGN_TYPE = "EXCEPTION_SIGN";
	/**部分签收 
	 */ 
	public static final String WEIXIN_PART_SIGN_TYPE = "PART_SIGN";
	/**其他类型
	 */
	public static final String WEIXIN_OTHERS_TYPE = "OTHERS";
	
	/**
	 * Foss推送微信消息至微信平台的编码
	 */
	public static final String ESB_FOSS2ESB_RECEIVE_ORDERSTATUS_CODE = "ESB_FOSS2ESB_RECEIVE_ORDERSTATUS";
	
	/**
	 * Foss推送微信消息至微信平台的版本号
	 */
	public static final String ESB_FOSS2ESB_RECEIVE_ORDERSTATUS_VERSION = "1.0";
}
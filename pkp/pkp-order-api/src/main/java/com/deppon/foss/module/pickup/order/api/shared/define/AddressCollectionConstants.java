package com.deppon.foss.module.pickup.order.api.shared.define;

public class AddressCollectionConstants {
	
	//PDA 返回类型
	public static final String  SUCCED = "1";//PDA返回状态成功
	public static final String  FAILED = "0"; //PDA返回状态失败
	
	//地址类型
	public static final String LTL_PICKUP = "LD_PK"; //零担接货
	public static final String LTL_DELIVER ="LD_DE"; //零担送货
	public static final String PACKAGE_PICKUP = "KD_PK"; //快递接货
	public static final String PACAKGE_DELIVER = "KD_DE";//快递送货
	
	//ESB参数配置
	public static final String ESB_HEADER_SOURCESYSTEMP = "FOSS";
	public static final String ESB_FOSS2ESB_COLLECTADDRESS = "ESB_FOSS2ESB_COLLECT_ADDRESS";
	public static final Integer ESB_HEADER__EXCHANGE_PATTERN = 1; // 请求响应方式
	public static final String ESB_HEADER__VERSION = "1.0"; // 版本号
	public static final String ESB_HEADER__MESSAGE_FORMAT = "SOAP"; // 消息格式:SOAP格式

	
}

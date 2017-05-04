package com.deppon.foss.module.pickup.order.api.shared.define;


public class ExpressWorkerStatusConstants {
	/**
	 * 操作系统
	 * */
	public static final String PDA_SYSTEM = "PDA";//PDA系统
	public static final String FOSS_SYSTEM = "FOSS";//FOSS 系统
	
	/**
	 * 快递员状态操作类型
	 * */
	public static final String OPEN_STATUS ="OPEN";//开启状态
	public static  final String STOP_STATUS = "STOP";//暂停状态
	public static final String ADD_STATUS = "ADD";//新增，用于PDA第一次登陆
	
	
	/**
	 * 业务范围
	 * */
     public static final String ORDER_BUSINESS = "ORDER";//零担业务
     public static final String EXPRESS_ORDER_BUSINESS = "EXPRESS_ORDER"; //快递业务
     
}

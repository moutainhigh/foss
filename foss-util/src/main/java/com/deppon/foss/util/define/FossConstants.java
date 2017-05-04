package com.deppon.foss.util.define;

import java.math.BigDecimal;

public class FossConstants {
	
	/**
	 * 数据初始版本，用于需要版本控制的实体；
	 */
	public static final short INIT_VERSION_NO = 1;

	/**
	 * 布尔类型
	 */
	public static final String YES = "Y";
	public static final String NO = "N";

	/**
	 * 生效/未生效
	 */
	public static final String ACTIVE = "Y";
	public static final String INACTIVE = "N";
	public static final String NOTEXIST = "NOTEXIST";  //不存在
	
	/**
	 * 成功/失败
	 * 对于过程性的方法，返回整型来代表成功或者失败；
	 */
	public static final int SUCCESS = 1;
	public static final int FAILURE = -1;
	
	/**
	 * CURRENCY_CODE 币种
	 */
	public static final String CURRENCY_CODE_RMB = "RMB"; // 人民币

	public static final String CURRENCY_CODE_HKD = "HKD"; // 港币

	public static final String CURRENCY_CODE_NTD = "NTD"; // 台币

	public static final String CURRENCY_CODE_USD = "USD"; // 美元
	
	/**
	 * ORACLE最大IN数量
	 */
	public static final int ORACLE_MAX_IN_SIZE = 1000;

	/**
	 * 集团ORG CODE
	 */
	public static final String ROOT_ORG_CODE= "DIP";

	/**
	 * 总公司标杆编码
	 */
	public static final String ROOT_ORG_UNIFIED_CODE= "DP00002";
	
	/**
	 * 总公司名称
	 */
	public static final String ROOT_ORG_NAME= "总公司";
	
	/**
	 * 公共权限缓存Code 后缀
	 */
	public static final String NOCHECK = "_noCheck";
	
	/**
	 * 空运中，体积（立方米）换算成重量（公斤）需要乘的常量
	 */
	public static final BigDecimal VOLUME_TO_WEIGHT=new BigDecimal(1000.0/6.0);
	
	/**
	 * 角色编码：超级管理员
	 */
	public static final String ROLE_FOSS_SYSTEM_ADMIN = "FOSS_SYSTEM_ADMIN";
	
	public static final String SSO_COOKIE_KEY = "CAS_TGC";
	
	public static final String CAS_SSO_TOKEN_KEY = "ticket";
	
	/**
	 * 默认人员编码：000000
	 */	
	public static final String DEFAULT_USER_CODE = "000000";
	
	/**
	 * 德邦快递   名称
	 */
	public final static String PACKAGE_NAME = "德邦快递"; 
	/**
	 * 下载异常终止
	 */
	public static final String DOWNLOAD_STATUS_EXCEPTION = "-1";
	
	/**
	 * 下载未开始
	 */
	public static final String DOWNLOAD_STATUS_UNDO = "0";
	
	/**
	 * 下载中
	 */
	public static final String DOWNLOAD_STATUS_DOING = "1";
	
	/**
	 * 下载结束
	 */
	public static final String DOWNLOAD_STATUS_DONE = "2";
	
	/**
	 * 同步卫星网点营业部新增
	 */
	public static final int SYNCCRM_ADD = 1;
	
	/**
	 * 修改
	 */
	public static final int SYNCCRM_UPDATE = 2;
	
	/**
	 * 同步卫星网点营业部删除
	 */
	public static final int SYNCCRM_DELETE = 3;
	
	/**
	 * 用于判断快递是否是直达
	 */
	public static final String IS_START_EXPRESS = "出发快递营业部";
	
	/**
	 * 省级别
	 */
	public final static String DISTRICT_PROVINCE_CODE = "DISTRICT_PROVINCE";
	/**
	 * 市级别
	 */
	public final static String DISTRICT_CITY_CODE = "DISTRICT_CITY";
	/**
	 * 区域级别
	 */
	public final static String DISTRICT_COUNTY_CODE = "DISTRICT_COUNTY";
	/**
	 * 快递/零担网点组
	 */
	public final static String NETWORK_GROUP_EXP ="Y";
	public final static String NETWORK_GROUP_LD ="N";
	
	/**
	 * 操作类型 
	 */
	public final static String OPERATE_MODIFY = "MODIFY";
	public final static String OPERATE_DELETE = "DELETE";
	
	/**
	 * 黑名单类型-违禁品 
	 */
	public final static String CUSTOMER_CONTRABAND = "CONTRABAND";
	
	/**
	 * oracle 最大支持insert条数
	 */
	public final static int LIMIT_SIZE = 5000 ;
	/**
	 * 发送数据到esb最大响应时间
	 */
	public final static long MAX_RESPONSE_TIME = 20000;
	
	/**
	 * 截取到达部门(运单的提货网点)名称的起始下角标
	 */
	public final static	int START_INDEX = 0;
	
	/**
	 * 截取到达部门(运单的提货网点)名称的结束下角标
	 */
	public final static int END_INDEX = 3;
	
	/**
	 * 到达部门(运单的提货网点)名称的最小长度
	 */
	public final static int MIN_INDEX = 4;
	
	/**
	 * 到达部门(运单的提货网点)名称以H,J,D开头的部门名称为合伙人
	 */
	public final static	String START_WHITH_H = "【H】";
	public final static String START_WHITH_J = "【J】";
	public final static String START_WHITH_D = "【D】";
	
	/**
	 * 系统编码
	 */
	public final static	String FOSS = "FOSS";
	public final static	String CUBC = "CUBC";
	
	/**
	 * CUBC支付状态
	 */
	public final static	String CUBC_CREATE = "CREATE"; //未支付
	public final static	String CUBC_PAYING = "PAYING"; //支付中
	public final static	String CUBC_DONE = "DONE"; //已支付

	
}

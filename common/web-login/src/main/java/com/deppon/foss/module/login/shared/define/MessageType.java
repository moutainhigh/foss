package com.deppon.foss.module.login.shared.define;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:操作提示类型类</small></b> </br> 
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> 
 * <b style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%">
 * 1 2012-07-31 钟庭杰    新增
 * </div>
 ******************************************** 
 */
public class MessageType {

	/**
	 * 登录成功
	 */
	public static final String LOGIN_SUCCESS = "foss.login.loginSuccess";
	
	/**
	 * 获得当前登录用户信息成功
	 */
	public static final String GETLOGINUSERINFO_SUCCESS = "foss.login.getCurrentLoginUserInfoSuccess";

	/**
	 * 保存成功
	 */
	public static final String SAVE_SUCCESS = "foss.login.saveSuccess";
	
	/**
	 * 第一次登录
	 */
	public static final String FIRST_LOGIN = "foss.login.firstLogin";
	
	/**
	 * 登录密码输入错误
	 */
	public static final String OLD_PASSWORD_ERROR = "foss.login.oldPasswordError";

	/**
	 * MAC地址信息不存在
	 */
	public static final String MAC_INFO_NOT_EXIST = "foss.login.macInfoNotExistError";
	
	/**
	 * 密码应包含字母和数字且长度为6-16位！
	 */
	public static final String PASSWORD_SAMECHAR_ERROR = "foss.login.passwordSamecharError";
	/**
	 * 新密码不能和原来密码一样
	 */
	public static final String PASSWORD_NOT_SAME = "foss.login.passwordNotSame";
	/**
	 * 密码剩余有效期提醒成功
	 */
	public static final String WARNING_SUCCESS = "foss.login.warnSuccess";
	/**
	 * 密码剩余有效期提醒失败
	 */
	public static final String WARNING_FAIL = "foss.login.warnFail";
}

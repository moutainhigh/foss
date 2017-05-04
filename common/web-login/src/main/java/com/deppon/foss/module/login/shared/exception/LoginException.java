package com.deppon.foss.module.login.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class LoginException extends BusinessException {

	private static final long serialVersionUID = -4375232641764945199L;
	
	/**
	 * 登录密码不能为空
	 */
	public static final String USER_PASSWORD_NULL = "foss.login.UserPasswordNullException";

	/**
	 * 登录密码错误
	 */
	public static final String USER_PASSWORD_ERROR = "foss.login.UserPasswordErrorException";

	/**
	 * 该用户不存在
	 */
	public static final String USER_NULL = "foss.login.UserNullException";

	/**
	 * 用户名不能为空
	 */
	public static final String USER_NAME_NULL = "foss.login.UserNameNullException";

	/**
	 * 当前用户已经被禁用
	 */
	public static final String USER_DISABLE = "foss.login.UserIsDisableException";

	/**
	 * 当前用户部门编码不能为空
	 */
	public static final String CURRENT_USER_DEPT_CODE_NULL = "foss.login.CurrentUserDeptCodeException";

	/**
	 * 当前部门不存在
	 */
	public static final String CURRENT_DEPT_ISNOT_EXIST = "foss.login.CurrentDeptIsNotExistException";
	
	/**
	 * 所属部门未配置角色信息
	 */
	public static final String CURRENT_DEPT_NO_ROLE = "foss.login.currentDeptNoRoleInfo";
	
	/**
	 * 当前用户未配置角色信息
	 */
	public static final String CURRENT_USER_NO_ROLE = "foss.login.currentUserNoRoleInfo";
	
	/**
	 * 收银员密码过期
	 */
	public static final String CASHIER_PSW_OVERDUE = "foss.login.cashierPswOverdueInfo";
	
	public LoginException(String errCode){
		super();
		super.errCode = errCode;
	}
}

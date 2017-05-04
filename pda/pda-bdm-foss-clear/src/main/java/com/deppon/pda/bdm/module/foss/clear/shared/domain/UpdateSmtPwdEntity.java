package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.io.Serializable;

public class UpdateSmtPwdEntity implements Serializable {

	/**
	 * @author 268974 wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 * 
	 * @since Ver 1.0
	 */
	private static final long serialVersionUID = 1L;
	// 用户名
	private String userCode;
	// 提交密码
	private String oldSmtPwd;
	// 修改密码
	private String newSmtPwd;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getOldSmtPwd() {
		return oldSmtPwd;
	}
	public void setOldSmtPwd(String oldSmtPwd) {
		this.oldSmtPwd = oldSmtPwd;
	}
	public String getNewSmtPwd() {
		return newSmtPwd;
	}
	public void setNewSmtPwd(String newSmtPwd) {
		this.newSmtPwd = newSmtPwd;
	}
	
	
}

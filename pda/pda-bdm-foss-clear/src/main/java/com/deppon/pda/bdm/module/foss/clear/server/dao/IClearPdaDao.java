package com.deppon.pda.bdm.module.foss.clear.server.dao;

import com.deppon.pda.bdm.module.foss.clear.shared.domain.CheckSmtPwdEntity;

public interface IClearPdaDao {
	   /**
	    * 查询用户密码
	    */
	    String selectUserPwd(String userCode);
	    /**
	     * 保存用户信息
	     */
	    void saveUserInfo(CheckSmtPwdEntity checkSmtPwdEntity);
	    /**
	     * 修改用户信息
	     */
	    void updateUserInfo(String userCode);
}

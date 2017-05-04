package com.deppon.pda.bdm.module.foss.clear.server.dao.impl;

//import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearPdaDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.CheckSmtPwdEntity;

public class ClearPdaDao extends SqlSessionDaoSupport implements IClearPdaDao {

	/**
	 * 查询用户提交密码  268974  2016/06/25
	 */
	@Override
	public String selectUserPwd(String userCode) {	
		return (String) getSqlSession().selectOne(ClearPdaDao.class.getName()+".selectUserPwd",userCode );
	}

	/**
	 * 保存用户提交密码信息 268974  2016/06/25
	 */
	@Override
	public void saveUserInfo(CheckSmtPwdEntity checkSmtPwdEntity) {
		getSqlSession().insert(ClearPdaDao.class.getName()+".saveUserInfo", checkSmtPwdEntity);	
	}

	/**
	 * 更新用户提交密码信息  268974  2016/06/25
	 */
	@Override
	public void updateUserInfo(String userCode) {
		getSqlSession().insert(ClearPdaDao.class.getName()+".updateUserInfo", userCode);	
	}
}

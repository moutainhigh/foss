package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.login.UserNotFoundException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearPdaDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.CheckSmtPwdEntity;

 
public class RestSmtPwdService implements IBusinessService<Void,Void>{
	private static final Log LOG = LogFactory.getLog(RestSmtPwdService.class);
	private IClearPdaDao clearPdaDao;
	private UserCache userCache;

	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException { 
		return null;
	}

	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, Void param) throws PdaBusiException {
		//1：校验参数
		this.validate( asyncMsg);
		//2： 从缓存取出登录密码
		UserEntity userEntity = userCache.getUser(asyncMsg.getUserCode());
		//用户信息不存在
		if(userEntity == null){
			throw new UserNotFoundException();
		}
		try {
			// 3：将原有用户信息置为无效
			clearPdaDao.updateUserInfo(asyncMsg.getUserCode());
			LOG.info("将用户信息置为无效" + asyncMsg.getUserCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
	 //4：设置新密码
		CheckSmtPwdEntity entity = new CheckSmtPwdEntity();
		try {
			// 设置工号
			entity.setUserCode(asyncMsg.getUserCode());
			// 设置新密码
			entity.setSmtPwd(userEntity.getPassword());
			// 设置用户名
			entity.setUsername(userEntity.getUserName());
			// 设置记录时间
			entity.setRecordDate(new Date());
			// 设置修改时间
			entity.setModifyDate(new Date());
			clearPdaDao.saveUserInfo(entity);
			LOG.info("密码重置成功" + asyncMsg.getUserCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		return null;
	}

	 
	private void validate(AsyncMsg asyncMsg) {
		Argument.notNull(asyncMsg, "asyncMsg");
		Argument.hasText(asyncMsg.getUserCode(), "asyncMsg.getUserCode()");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_RESET_PWD.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setClearPdaDao(IClearPdaDao clearPdaDao) {
		this.clearPdaDao = clearPdaDao;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

}

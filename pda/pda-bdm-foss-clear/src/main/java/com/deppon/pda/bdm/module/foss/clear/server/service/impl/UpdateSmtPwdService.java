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
import com.deppon.pda.bdm.module.core.shared.exception.login.PasswordErrorException;
import com.deppon.pda.bdm.module.core.shared.exception.login.UserNotFoundException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearPdaDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.CheckSmtPwdEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.UpdateSmtPwdEntity;

public class UpdateSmtPwdService implements
		IBusinessService<Void, UpdateSmtPwdEntity> {
	private static final Log LOG = LogFactory.getLog(UpdateSmtPwdService.class);
	private IClearPdaDao clearPdaDao;
	private UserCache userCache;

	// 解析参数
	@Override
	public UpdateSmtPwdEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		UpdateSmtPwdEntity entity = JsonUtil.parseJsonToObject(
				UpdateSmtPwdEntity.class, asyncMsg.getContent());
		return entity;
	}

	// 业务方法入口
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, UpdateSmtPwdEntity param)
			throws PdaBusiException {
		// 检验参数
		LOG.info("参数为：工号：" + param.getUserCode() + "，新密码："
				+ param.getNewSmtPwd() + "，旧密码：" + param.getOldSmtPwd());
		this.validate(asyncMsg, param);
		UserEntity userEntity = userCache.getUser(param.getUserCode());
		CheckSmtPwdEntity entity = new CheckSmtPwdEntity();
		// 用户信息不存在
		if (userEntity == null) {
			throw new UserNotFoundException();
		}
		// 查询用户提交密码
		String smtPwd = clearPdaDao.selectUserPwd(param.getUserCode());
		LOG.info("查询到的提交密码为：" + smtPwd);
		if (smtPwd != null && !smtPwd.isEmpty()) {
			// 查询到用户提交密码表中有数据，校验提交密码
			if (!smtPwd.equals(param.getOldSmtPwd())) {
				throw new PasswordErrorException();
			}
			try {
				// 将原有用户信息置为无效
				clearPdaDao.updateUserInfo(param.getUserCode());
				LOG.info("将用户信息置为无效" + param.getUserCode());
			} catch (BusinessException e) {
				throw new FossInterfaceException(e.getCause(), e.getErrorCode());
			}
		} else {
			// 用户提交密码表中没有数据 校验用户提交密码与登录密码
			if (!userEntity.getPassword().equals(param.getOldSmtPwd())) {
				throw new PasswordErrorException();
			}
		}
		try {
			// 设置工号
			entity.setUserCode(param.getUserCode());
			// 设置新密码
			entity.setSmtPwd(param.getNewSmtPwd());
			// 设置用户名
			entity.setUsername(userEntity.getUserName());
			// 设置记录时间
			entity.setRecordDate(new Date());
			// 设置修改时间
			entity.setModifyDate(new Date());
			clearPdaDao.saveUserInfo(entity);
			LOG.info("密码修改成功" + param.getUserCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		return null;
	}

	// 操作类型
	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_UPDATE_PWD.VERSION;
	}

	// 同步
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 
	 * @Title: validate
	 * @Description: TODO(检验参数)
	 * @return void 返回类型
	 * @param asyncMsg
	 * @param param
	 * @author： 268974 wangzhili
	 */
	private void validate(AsyncMsg asyncMsg, UpdateSmtPwdEntity param) {
		Argument.notNull(asyncMsg, "asyncMsg");
		Argument.notNull(param, "param");
		Argument.hasText(param.getUserCode(), "param.getUserCode()");
		Argument.hasText(param.getNewSmtPwd(), "param.getNewSmtPwd()");
		Argument.hasText(param.getOldSmtPwd(), "param.getOldSmtPwd()");
	}

	public void setClearPdaDao(IClearPdaDao clearPdaDao) {
		this.clearPdaDao = clearPdaDao;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

}

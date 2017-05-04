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

/**
 * 
 * @ClassName: CheckSmtPwdService 
 * @Description: TODO(校验提交密码) 
 * @author &268974  wangzhili
 * @date 2016-6-25 上午11:44:24 
 *
 */
public class CheckSmtPwdService implements IBusinessService<Void, CheckSmtPwdEntity>{

	private static final Log LOG = LogFactory.getLog(CheckSmtPwdService.class);
    private IClearPdaDao clearPdaDao;
    private UserCache userCache;
	//解析参数
	@Override
	public CheckSmtPwdEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		CheckSmtPwdEntity entity = JsonUtil.parseJsonToObject(CheckSmtPwdEntity.class, asyncMsg.getContent());
		return entity;
	}

	//业务方法入口
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, CheckSmtPwdEntity param)
			throws PdaBusiException {
		//校验参数
		LOG.info("参数为：工号"+param.getUserCode()+"提交密码"+param.getSmtPwd());
		this.validate(asyncMsg, param);
		UserEntity userEntity = userCache.getUser(param.getUserCode());
		//用户信息不存在
		if (userEntity == null) {
			throw new UserNotFoundException();
		}
		try{
			//查询用户提交密码
			String smtPwd = clearPdaDao.selectUserPwd(param.getUserCode());
			LOG.info("查询到的提交密码为："+smtPwd);
			if(smtPwd==null||smtPwd.isEmpty()){
		    	//没有在提交密码表中查询到密码，将提交密码与登录密码比较
				if (!userEntity.getPassword().equals(param.getSmtPwd())) {
					throw new PasswordErrorException();
				}
				//设置用户名
				param.setUsername(userEntity.getUserName());
				param.setRecordDate(new Date());
				param.setModifyDate(new Date());
				clearPdaDao.saveUserInfo(param);
				LOG.info("保存数据成功");
		    }else{
		    	//提交表中存在密码，比较输入密码与表中密码是否一致
		    	if(!smtPwd.equals(param.getSmtPwd())){
		    		throw new PasswordErrorException();
		    	}
		    }
		}catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}

	//操作类型
	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_CHECK_PWD.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 
	 * @Title: validate 
	 * @Description: TODO(校验参数合法性)  
	 * @return void    返回类型 
	 * @param asyncMsg
	 * @param param
	 * @author： 268974  wangzhili
	 */
	private void validate(AsyncMsg asyncMsg,CheckSmtPwdEntity param){
		Argument.notNull(asyncMsg,"asyncMsg");
		Argument.notNull(param,"param");
		Argument.hasText(param.getSmtPwd(), "param.getSmtPwd()");
		Argument.hasText(param.getUserCode(),"param.getUserCode()");
	}

	public void setClearPdaDao(IClearPdaDao clearPdaDao) {
		this.clearPdaDao = clearPdaDao;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
	

}

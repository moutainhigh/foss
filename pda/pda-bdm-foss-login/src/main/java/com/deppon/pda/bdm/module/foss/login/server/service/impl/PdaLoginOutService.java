package com.deppon.pda.bdm.module.foss.login.server.service.impl;


import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.login.server.dao.ILoginPdaDao;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginDeviceEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginOutInfo;

/**
 * 
  * @ClassName PdaLoginOutService 
  * @Description 登出模块服务类 
  * @author xujun cometzb@126.com 
  * @date 2012-12-25
 */
public class PdaLoginOutService  implements IBusinessService<Void, PdaLoginOutInfo>{
	private static final Logger LOG = Logger.getLogger(PdaLoginOutService.class);
	
	private IPdaSigninLogoutService pdaSigninLogoutService;
	private ILoginPdaDao loginPdaDao;
	
	@Override
	public PdaLoginOutInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PdaLoginOutInfo loginOutInfo = JsonUtil.parseJsonToObject(PdaLoginOutInfo.class, asyncMsg.getContent());
		loginOutInfo.setPdaCode(asyncMsg.getPdaCode());
		loginOutInfo.setScanUser(asyncMsg.getUserCode());
		return loginOutInfo;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, PdaLoginOutInfo param)
			throws PdaBusiException {
		LOG.info("pda logout......");
		this.validate(param);
		try {
			if(PdaSignStatusConstants.USER_TYPE_TALLYPERSON.equals(param.getUserType())){
				//理货员不调接口
			}else{
				if(PdaSignStatusConstants.USER_TYPE_COURIER.equals(param.getUserType())){
					param.setTruckCode("德"+param.getScanUser());
				}
				pdaSigninLogoutService.loginOut(param.getPdaCode(), 
						param.getScanUser(), 
						param.getTruckCode(),
						param.getUserType(),
						new Date());
			}
		//记录PDA退出时间
			try{
			LOG.info("PDA退出时间记录......");
			PdaLoginDeviceEntity outDateEntity=new PdaLoginDeviceEntity();
			outDateEntity.setLoginOutDate(new Date());
			outDateEntity.setDvcCode(param.getPdaCode());
			loginPdaDao.updPdaLoginOutDate(outDateEntity);
			LOG.info("PDA退出时间成功......");
			}catch(Exception e){
				LOG.info("PDA退出时间异常......");
			}
		} catch (BusinessException e) {
			LOG.error("登出异常:"+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//TODO  返回值为空？
		
		return null;
	}

	private void validate(PdaLoginOutInfo param) {
		Argument.notNull(param, "PdaLoginOutInfo");
		Argument.hasText(param.getPdaCode(), "PdaLoginOutInfo.PdaCode");
		Argument.hasText(param.getScanUser(), "PdaLoginOutInfo.ScanUser");
//		Argument.hasText(param.getTruckCode(), "PdaLoginOutInfo.TruckCode");
	}

	@Override
	public String getOperType() {
		return LoginConstant.OPER_TYPE_SYS_LOGIN_OUT.VERSION;
	}

	@Override
	public boolean isAsync() {
		// 同步
		return false;
	}

	public void setPdaSigninLogoutService(
			IPdaSigninLogoutService pdaSigninLogoutService) {
		this.pdaSigninLogoutService = pdaSigninLogoutService;
	}

	public void setLoginPdaDao(ILoginPdaDao loginPdaDao) {
		this.loginPdaDao = loginPdaDao;
	}
	
	
}

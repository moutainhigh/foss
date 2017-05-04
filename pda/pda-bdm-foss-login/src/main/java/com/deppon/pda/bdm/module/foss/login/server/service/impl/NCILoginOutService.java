package com.deppon.pda.bdm.module.foss.login.server.service.impl;

import java.util.Date;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginOutInfo;
/**
 * 
 * @ClassName: NCILoginOutService 
 * @Description: TODO(NCI退出) 
 * @author &268974  wangzhili
 * @date 2016-2-27 下午3:05:17 
 *
 */
public class NCILoginOutService implements IBusinessService<Void, PdaLoginOutInfo>{

	private IPdaSigninLogoutService pdaSigninLogoutService;
	//解析body
	@Override
	public PdaLoginOutInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PdaLoginOutInfo loginOutInfo = JsonUtil.parseJsonToObject(PdaLoginOutInfo.class, asyncMsg.getContent());
		//设备号
		loginOutInfo.setPdaCode(asyncMsg.getPdaCode());
		//用户编号
		loginOutInfo.setScanUser(asyncMsg.getUserCode());
		return loginOutInfo;
	}

	//方法入口
	@Override
	public Void service(AsyncMsg asyncMsg, PdaLoginOutInfo param)
			throws PdaBusiException {
		//校验参数
		this.validate(param);
		try{
			//调用foss接口
			pdaSigninLogoutService.loginOut(param.getPdaCode(), param.getScanUser(), null, param.getUserType(),new Date());
		}catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}catch(Exception e){
			throw new FossInterfaceException(null,"出现未知异常");
		}
		return null;
	}

	//校验参数
	private void validate(PdaLoginOutInfo param) {
		Argument.notNull(param, "PdaLoginOutInfo");
		//POS设备号
		Argument.hasText(param.getPdaCode(), "PdaLoginOutInfo.PdaCode");
		//用户工号
		Argument.hasText(param.getScanUser(), "PdaLoginOutInfo.ScanUser");
	}
	//操作类型
	@Override
	public String getOperType() {
		return LoginConstant.OPER_TYPE_NCI_LOGIN_OUT.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setPdaSigninLogoutService(
			IPdaSigninLogoutService pdaSigninLogoutService) {
		this.pdaSigninLogoutService = pdaSigninLogoutService;
	}

	
	
}

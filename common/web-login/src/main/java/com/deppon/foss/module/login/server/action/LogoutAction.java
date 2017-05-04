package com.deppon.foss.module.login.server.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.deppon.dpap.log.log4j.entity.ExpressLogin;
import com.deppon.dpap.log.log4j.kit.Log4jKit;
import com.deppon.dpap.log.log4j.kit.StaticLogType;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.Cookie;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.login.server.service.ILoginService;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:退出WEB服务</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
 * 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public class LogoutAction extends AbstractAction{

	private static final long serialVersionUID = 4776710729717441838L;
    private static final  org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LoginAction.class);

	private ILoginService loginService;
	private String publicIp;

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	private IBusinessMonitorService businessMonitorService;
	
	public void setBusinessMonitorService(
			IBusinessMonitorService businessMonitorService) {
		this.businessMonitorService = businessMonitorService;
	}

	/**
	 * 退出系统
	 */
	@Override
	public String execute() throws Exception {
		ExpressLogin el=new ExpressLogin();
		HttpServletRequest request = (HttpServletRequest) ServletActionContext.getRequest();
		EmployeeEntity employ = FossUserContext.getCurrentUser().getEmployee();
		loginService.attachUserInfoToEl(el,employ,request);
		el.setOperation_type("logout");
		el.setSource_public_ip(publicIp);
		Log4jKit.setWebAppId("FOSS");
		//注销登录 删除用户在当前部门的登陆信息
		try{
		offline();
		//失效Cookie
		Cookie.invalidateCookie();
		loginService.userLogout();
		el.setResult("success");
		el.setReason(null);
		}catch(BusinessException e){
			el.setResult("fail");
			el.setReason(e.getErrorCode());
		}
		Log4jKit.info(log,LoginAction.class,StaticLogType.EXPRESS_LOGIN,el);
		return super.execute();
	}
	
	/**
	 * 注销的时候 将当前部门中的在线用户信息删除
	 */
	private void offline() {
		CurrentInfo info = FossUserContext.getCurrentInfo();
		businessMonitorService.offline(info);
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}
}

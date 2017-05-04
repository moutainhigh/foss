package com.deppon.foss.module.base.baseinfo.server.service.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.deppon.ar.bamp.client.interceptor.struts2.RequestErrorInterceptor;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 
 * @author 198771
 * Bamp统一拦截器
 */
public class BaseInfoBampInterceptor extends RequestErrorInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 145161464644974979L;

	@Override
	public String getEmpCode() {
		CurrentInfo ci = FossUserContext.getCurrentInfo();
		if(null != ci
				&& StringUtils.isNotBlank(ci.getEmpCode())){
			return ci.getEmpCode();
		}else{
			return "-1";
		}
	}

}

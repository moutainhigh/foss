package com.deppon.foss.module.base.common.server.interceptor;

import org.apache.commons.lang3.StringUtils;

import com.deppon.ar.bamp.client.interceptor.struts2.RequestErrorInterceptor;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 
 * @author 198771
 * Bamp统一拦截器
 */
public class CommonBampInterceptor extends RequestErrorInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1634646464364674L;

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

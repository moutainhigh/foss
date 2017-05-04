package com.deppon.foss.module.transfer.common.server.interceptor;

import org.apache.commons.lang.StringUtils;

import com.deppon.ar.bamp.client.interceptor.struts2.RequestErrorInterceptor;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

public class CommonBampInterceptor extends RequestErrorInterceptor {

	
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * <p>返回登陆人工号</p> 
	 * @author alfred
	 * @date 2015-1-10 上午9:57:41
	 * @return
	 * @see
	 */
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

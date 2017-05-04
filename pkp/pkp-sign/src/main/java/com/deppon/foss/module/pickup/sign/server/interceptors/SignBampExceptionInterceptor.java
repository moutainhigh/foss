/**
 * 
 */
package com.deppon.foss.module.pickup.sign.server.interceptors;

import org.apache.commons.lang3.StringUtils;

import com.deppon.ar.bamp.client.interceptor.struts2.RequestErrorInterceptor;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * @author 045738
 * BAMP的统一拦截器
 */
public class SignBampExceptionInterceptor extends RequestErrorInterceptor{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 功能：
	 * @author 045738-foss-maojianqiang
	 * @date 2014-11-20
	 * @return
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

package com.deppon.foss.module.login.server.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.entity.IFunction;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.exception.security.UserNotLoginException;
import com.deppon.foss.framework.server.components.security.SecurityAccessor;
import com.deppon.foss.framework.server.context.RequestContext;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.framework.server.web.interceptor.AbstractInterceptor;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.server.service.impl.BusinessMonitorService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 应用监控-菜单功能计数
 * 
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-3-4 上午10:32:11,content:TODO </p>
 * @author ningyu
 * @date 2013-3-4 上午10:32:11
 * @since
 * @version
 */
public class ResourceMonitorInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -1237381313345657982L;
	
	public static final Log LOGGER = LogFactory.getLog(ResourceMonitorInterceptor.class);

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		IBusinessMonitorService service = WebApplicationContextHolder.getWebApplicationContext().getBean(BusinessMonitorService.class);
		final String url = RequestContext.getCurrentContext().getRemoteRequestURL();
		try {
			IFunction function = SecurityAccessor.getFunction(url);
			if(function == null) return invocation.invoke();
			CurrentInfo currentInfo = null;
			try {
				currentInfo=FossUserContext.getCurrentInfo();
			} catch(UserNotLoginException e) {
				return invocation.invoke();
			}
			service.counterResource(function.getFunctionCode(), currentInfo);
		} catch(BusinessException e) {
			LOGGER.error("菜单计数失败!",e);
		}
		return invocation.invoke();
	}

}

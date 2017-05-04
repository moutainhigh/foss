package com.deppon.foss.module.pickup.pickup.server.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.define.LocaleConst;
import com.deppon.foss.framework.define.Protocol;
import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.framework.server.Definitions;
import com.deppon.foss.framework.server.context.AppContext;
import com.deppon.foss.framework.server.context.RequestContext;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.context.XssConfigContext;
import com.deppon.foss.framework.server.deploy.struts.ModuleManager;
import com.deppon.foss.framework.server.web.DefaultFilter;
import com.deppon.foss.framework.server.web.session.ISession;
import com.deppon.foss.framework.server.web.xss.ParametersValidator;
import com.deppon.foss.framework.server.web.xss.ParametersValidatorException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:框架过滤器</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2011-4-9 steven.cheng 新增
* </div>  
********************************************
 */
//该filter适用于解决user not login exception
public class PickupFilter extends DefaultFilter  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PickupFilter.class);

	private static ServletContext servletContext;
	    
	public static ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * 初始化Filter，导出模块资源
	 * 
	 * @see com.deppon.foss.framework.server.web.DefaultFilter#init(javax.servlet.FilterConfig)
	 *      init
	 * @param config
	 * @throws ServletException
	 * @since:0.6
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		getServletContext(config);
		ModuleManager.export(servletContext);
	
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(XssConfigContext.EXPRESSION, config.getInitParameter(XssConfigContext.EXPRESSION));
		map.put(XssConfigContext.TACTICS, config.getInitParameter(XssConfigContext.TACTICS));
		map.put(XssConfigContext.PATH, config.getInitParameter(XssConfigContext.PATH));
		AppContext.setParametersValidator(new ParametersValidator(new XssConfigContext(map)));
	}

	public static void getServletContext(FilterConfig config) {
		servletContext = config.getServletContext();
	}
	    
	
    /**
     * 设置应用信息
     * @see com.deppon.foss.framework.server.web.DefaultFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     * doFilter
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     * @since:0.6
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        /** 取得HttpServletRequest,这里可以拿到HttpSession **/
    	HttpServletRequest sreq = null;
    	String client = null;
    	if(request instanceof HttpServletRequest) {
    		
    		sreq = (HttpServletRequest) request;
    		
    		//过滤参数
    		try {
    			AppContext.getParametersValidator().doValidator(sreq, (HttpServletResponse)response);
    		} catch(ParametersValidatorException e) {
    			return;
    		}
    		
    	
    		/** 寻找客户端是否采用Hessian协议,HTTP头部有此定义 **/
    		String remoteReqMethod = sreq.getHeader(Protocol.SECURITY_HEADER);
    		String remoteReqURL = sreq.getRequestURI();
    		String contextPath = sreq.getContextPath();
    		/** 去掉应用名称，具体部署的应用名称是可变的 **/
    		if (contextPath != null && !"/".equals(contextPath) && remoteReqURL.startsWith(contextPath)) {
    			remoteReqURL = remoteReqURL.substring(contextPath.length());
    		}
    		/** 将当前访问的路径和远程头信息放入权限上下文 **/
    		RequestContext.setCurrentContext(remoteReqMethod, remoteReqURL, request.getRemoteHost());
    		
    		/** 会话保留到SessionContext，以便各层使用 **/
    		SessionContext.setSession(sreq.getSession(true));
    		
//    		String ipAddress = sreq.getRemoteAddr();
//    		SessionContext.getSession().setObject("IP_ADDRESS", ipAddress);
    		
    		
    		ISession session=SessionContext.getSession();
    		//set locale to usercontext
    		Locale locale=(Locale)session.getObject(Definitions.KEY_LOCALE);
    		//get request locale
    		if(locale==null){
    			locale=sreq.getLocale();
    			session.setObject(Definitions.KEY_LOCALE, locale);
    		}
    		
    		
    		String newlocaleLanguage = sreq.getParameter(LocaleConst.KEY_LOCALE_LANGUAGE);
    		String newlocaleCountry = sreq.getParameter(LocaleConst.KEY_LOCALE_COUNTRY);
    		if(newlocaleLanguage!=null && newlocaleCountry!=null){
    			locale = new Locale(newlocaleLanguage, newlocaleCountry);
    			session.setObject(Definitions.KEY_LOCALE, locale);
    		}
    		
    		UserContext.setUserLocale(locale);
    		//set user to usercontext
    		String userId =(String)session.getObject(Definitions.KEY_USER);
    		
    		if(StringUtils.isEmpty(userId)){
    			LOGGER.info("*****session.getObject(Definitions.KEY_USER) is empty*****");
    			userId = sreq.getHeader(Definitions.KEY_USER);
    		}
    		if(StringUtils.isNotEmpty(userId)){
    			ICache<String,IUser> userCache=CacheManager.getInstance().getCache(IUser.class.getName());
    			UserContext.setCurrentUser(userCache.get(userId));
    			LOGGER.info("*****userId=" + userId
						+ "; userCache.get(userId)=" + userCache.get(userId)
						+ "*****");
    		}else {
				LOGGER.info("*****sreq.getHeader(Definitions.KEY_USER) is empty*****");
			}
    		String empCode = sreq.getHeader("FRAMEWORK_KEY_EMPCODE");
    		if(StringUtils.isNotEmpty(empCode)){
    			SessionContext.getSession().setObject("FRAMEWORK_KEY_EMPCODE", empCode);
    		}
    		String currentDeptCode = sreq.getHeader("FOSS_KEY_CURRENT_DEPTCODE");
    		if(StringUtils.isNotEmpty(currentDeptCode)){
    			SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE", currentDeptCode);
    			try{
	    			CacheManager cacheManager = CacheManager.getInstance();
	    			ICache cache = cacheManager.getCache(FossTTLCache.ORGANIZATION_ORGCODE_CACHE_UUID);
	    			OrgAdministrativeInfoEntity org = (OrgAdministrativeInfoEntity) cache.get(currentDeptCode);
	    			SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME", org.getName());
    			}catch(Exception e){
    				SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME","");
    			}
    		}
    		
    		
    		client = sreq.getHeader("CLIENT"); 
    		
    		
//    		String token = sreq.getHeader("_TOKEN"); 
//    		String tokenUuid = sreq.getHeader("_TOKEN_UUID"); 
//    		
//    		
//    		
//    		if(remoteReqMethod!=null
//    			&&!"keepSession".equals(remoteReqMethod)
//    			&&!"userLogin".equals(remoteReqMethod)
//    			&&!"keepSessionUuid".equals(remoteReqMethod)
//    			&&!"processSyncData".equals(remoteReqMethod)){
//    			
//    			WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(
//    					sreq.getSession().getServletContext());
//    			TokenCache tokenCache = (TokenCache)context.getBean("tokenCache");
//    			if(StringUtils.isNotEmpty(tokenUuid)){
//    				try{
//	    				String token2 = tokenCache.get(tokenUuid);
//		    			if(token2==null || !token2.equals(token) ){
//		    				Log.error("error client token is : "+ token +"\n"+" server token is : "+ token2);
//		    				response.getWriter().write("userNotLogin"); 
//		    				throw new UserNotLoginException();
//		    			}
//    				}catch(Exception e){
//    					Log.error("error", e);
//    					throw new UserNotLoginException();
//    				}
//    			}else{
//    				throw new UserNotLoginException();
//    			}
//    			
//    			
//    			
//    		}
    		
    		
    	}
        try{
        	super.doFilter(request, response, filterChain);
        	
 
        }finally{
        	
        	if("gui".equals(client)){
	        	try{
	        		SessionContext.getSession().invalidate();
	        	}catch(Exception e){
	        		//to do nothing
	        	}
	        	SessionContext.setSession(null);
        	}
        	//清除ThreadLocal中持有的信息
        	SessionContext.remove();
        	RequestContext.remove();
        	UserContext.remove();
        }
        
    }
    
}

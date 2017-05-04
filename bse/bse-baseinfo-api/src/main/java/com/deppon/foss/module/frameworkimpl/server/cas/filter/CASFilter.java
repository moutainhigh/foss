package com.deppon.foss.module.frameworkimpl.server.cas.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import com.deppon.foss.framework.exception.BusinessException;

/**
 * CASFilter代理edu.yale.its.tp.cas.client.filter.CASFilter
 * 目的是为了将web.xml中的filter配置抽到cas.properties配置文件中去
 * cas.properties在foss-config工程下
 * 
 * @author ningyu
 * @date 2014-3-25
 *
 */
public class CASFilter extends edu.yale.its.tp.cas.client.filter.CASFilter {
	
	private static PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	@Override
	public void init(final FilterConfig c) throws ServletException {
		try {
			Resource[] resources = resolver.getResources("classpath*:cas.properties");
			if(resources == null || resources.length <=0 || resources.length > 1) {
				throw new BusinessException("缺少CAS配置文件或配置文件只允许配置一个!");
			}
			if (resources != null && resources.length == 1) {
				Resource resource = resources[0];
				final Properties p = new Properties();
				p.load(resource.getInputStream());
				FilterConfig config = new FilterConfig() {
					
					@Override
					public ServletContext getServletContext() {
						return c.getServletContext();
					}
					
					@Override
					public Enumeration getInitParameterNames() {
						return p.keys();
					}
					
					@Override
					public String getInitParameter(String name) {
						System.out.println(p.getProperty(name));
						return p.getProperty(name);
					}
					
					@Override
					public String getFilterName() {
						return c.getFilterName();
					}
				};
				super.init(config);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

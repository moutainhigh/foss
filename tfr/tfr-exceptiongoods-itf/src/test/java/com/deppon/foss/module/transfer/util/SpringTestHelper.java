package com.deppon.foss.module.transfer.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.framework.server.context.AppContext;

public class SpringTestHelper {

    protected ApplicationContext appContext = null;

    private static SpringTestHelper instance = new SpringTestHelper();

    private SpringTestHelper() {
	initContext();
    }
    public static SpringTestHelper get() {
	return instance;
    }
    
    protected void initContext() {
		try {
		    appContext = new ClassPathXmlApplicationContext(
			    "com/deppon/foss/module/transfer/stock/server/META-INF/springTest.xml","classpath:com/deppon/foss/module/transfer/common/server/META-INF/spring.xml");
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
    
    
    @SuppressWarnings("unchecked")
    public <T> T getBeanByInterface(Class<T> clazz) {
	if (appContext != null) {
	    String className = clazz.getSimpleName();
	    String otherName = className.substring(1);
	    String anyotherName = otherName.substring(1);
	    String startName = otherName.substring(0, 1);

	    String beanName = StringUtils.lowerCase(startName) + anyotherName;
	    return (T) appContext.getBean(beanName);
	}
	return null;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getBeanByClass(Class<T> clazz) {
	if (appContext != null) {
	    String className = clazz.getSimpleName();
	    String anyotherName = className.substring(1);
	    String startName = className.substring(0, 1);

	    String beanName = StringUtils.lowerCase(startName) + anyotherName;
	    return (T) appContext.getBean(beanName);
	}
	return null;
    }
    
    @SuppressWarnings("unchecked")
	public <T> T getBeanById(String beanId){
    	return (T) appContext.getBean(beanId);
    }
    
}
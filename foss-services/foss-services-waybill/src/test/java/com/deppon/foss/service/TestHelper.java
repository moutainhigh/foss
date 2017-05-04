package com.deppon.foss.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHelper {

    protected ApplicationContext appContext = null;

    private static TestHelper instance = new TestHelper();

    private TestHelper() {
	initContext();
    }

    public static TestHelper get() {
	return instance;
    }

    protected void initContext() {
	try {
	    appContext = new ClassPathXmlApplicationContext(new String[]{
		    "com/deppon/foss/module/pickup/waybill/server/META-INF/springTest.xml",
		    "classpath*:com/deppon/foss/module/settlement/common/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/settlement/consumer/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/pickup/pricing/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/transfer/scheduling/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/transfer/stock/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/transfer/scheduling/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml",		    
		    "classpath*:com/deppon/foss/module/pickup/pricing/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/transfer/packaging/server/META-INF/spring.xml",
		    "classpath*:com/deppon/foss/module/base/dict/server/META-INF/spring.xml",
	    });
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
    
}

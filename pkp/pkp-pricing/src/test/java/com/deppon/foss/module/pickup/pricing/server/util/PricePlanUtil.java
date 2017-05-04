package com.deppon.foss.module.pickup.pricing.server.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.framework.server.context.AppContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;


/**
 * 测试工具类
 * 
 * @author POP-Team-LuoMengXiang
 * 
 * 2014/10/22
 *
 */
public class PricePlanUtil {
	public static ClassPathXmlApplicationContext ac;
	public static IPricePlanDao pricePlanDao;
	static{
		   AppContext.initAppContext("application", "", "");
		   String[] config={"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/Pop-spring-test.xml"};
	       ac=new ClassPathXmlApplicationContext(config);
	        pricePlanDao=(IPricePlanDao) ac.getBean("pricePlanDao");
	   }
}

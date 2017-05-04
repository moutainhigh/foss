package com.deppon.foss.module.pickup.pricing.server.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.framework.server.context.AppContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDetailDao;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title TestUtil.java
 * @package com.deppon.foss.module.pickup.pricing.server 
 * @author xx
 * @version 0.1 2014年10月15日
 */
public class TestUtil {
	
	public static ClassPathXmlApplicationContext factory;
	public static IPopValueAddedDao popValueAddedDao;
	public static IPopValueAddedDetailDao popValueAddedDetailDao;

	

	static {
		AppContext.initAppContext("application", "", "");
		String[] resource = { "classpath*:com/deppon/foss/module/pickup/pricing/server/META-INF/springTest.xml" };
		factory = new ClassPathXmlApplicationContext(resource);
		popValueAddedDao=(IPopValueAddedDao) factory.getBean("popValueAddedDao");
		popValueAddedDetailDao=(IPopValueAddedDetailDao) factory.getBean("popValueAddedDetailDao");

		
	}
	
	

}

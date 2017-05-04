package com.deppon.foss.module.pickup.pricing.server.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.framework.server.context.AppContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PricePlanService;

/****
 * 测试的工具类
 * 
 * @author POP-Team-LuoMengxiang
 *
 *@category 2014/10/21
 */
public class PopUtil {
        public static ClassPathXmlApplicationContext ac;
        //public static IPopPriceDetailSectionDao PopPriceDetailSectionDao;
        public static IPricePlanService pricePlanService;
        static{
        	AppContext.initAppContext("application", "", "");
        	String[] config={"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/Pop-spring-test.xml"};
        	ac=new ClassPathXmlApplicationContext(config);
        	pricePlanService=
        			(IPricePlanService ) ac.getBean("popPriceDetailSection");
        }
}

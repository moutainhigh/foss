package com.deppon.pda.bdm.module.core.shared.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.OperationConstant;

/**
 * spring bean factory
 * @author chengang       
 * @version 1.0     
 * @created 2012-9-7 上午10:18:44
 * 加载Spring配置文件时，如果Spring配置文件中所定义的Bean类实现了
 * ApplicationContextAware 接口，那么在加载Spring配置文件时，
 * 会自动调用ApplicationContextAware 接口中的setApplicationContext方法
 */
public class BeanFactory implements ApplicationContextAware {

  /**
   * 
   */
  private static Logger log = Logger.getLogger(BeanFactory.class);

  /**
   * spring application context
   */
  public static ApplicationContext appContext;
  

  /**
   * return spring applicationContext
   * @return ApplicationContext
   */
  public static ApplicationContext getContext() {
    if (appContext == null) {
      log.error("ApplicationContext in BeanFactory Init Failed!");
    }
    return appContext;
  }

  /**
   * 返回服务
   * @param name
   * @return Object
   */
  public static Object getBean(String name) {
    return getContext().getBean(name);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    appContext = applicationContext;
    Map<String, IBusinessService> map = appContext.getBeansOfType(IBusinessService.class);
    Iterator<String> it = map.keySet().iterator();
    while (it.hasNext()) {
		String beanName = it.next();
		IBusinessService service = map.get(beanName);
		OperationConstant.OPER_SERVICE_MAP.put(service.getOperType(), beanName);
		log.info("服务加载，服务名："+beanName+" 操作类型："+service.getOperType());
		if(service.isAsync()){
			OperationConstant.ASYNC_OPER_TYPE_LIST.add(service.getOperType());
			log.info("异步操作类型加载，服务名："+beanName+" 操作类型："+service.getOperType());
		}
	}
  }
  

}

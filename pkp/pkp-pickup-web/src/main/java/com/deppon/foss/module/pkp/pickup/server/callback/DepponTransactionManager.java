package com.deppon.foss.module.pkp.pickup.server.callback;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;


public class DepponTransactionManager  implements InitializingBean, PlatformTransactionManager,ApplicationContextAware {
	private ApplicationContext applicationContext;

	private PlatformTransactionManager platformTransactionManager;
	
	
	private String jmsName;
	
	//是否本地事务  默认false
	boolean isNative = false;
	

	public TransactionStatus getTransaction(TransactionDefinition definition){
		return platformTransactionManager.getTransaction(definition);
	}

	@Override
	public void commit(TransactionStatus status) throws TransactionException {
		platformTransactionManager.commit(status);
	}

	@Override
	public void rollback(TransactionStatus status) throws TransactionException {
		platformTransactionManager.rollback(status);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//读取配置信息
		String isNativeTransaction = PropertiesUtil.getKeyValue("ISNATIVETRANSACTION");
		if("true".equals(isNativeTransaction) || "TRUE".equals(isNativeTransaction)){
			platformTransactionManager = (JmsTransactionManager)getBean(getJmsName());
		}else{
			platformTransactionManager = (JtaTransactionManager)getBean("jtaManager");
		}
	}
	
	/**
	 * 主要获取jtaManager实例
	 * @param beanName
	 * @return  jtaManager 实例对象
	 */
	public Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	    this.applicationContext = applicationContext;
	}

	public String getJmsName() {
		return jmsName;
	}

	public void setJmsName(String jmsName) {
		this.jmsName = jmsName;
	}
	
	
	
}

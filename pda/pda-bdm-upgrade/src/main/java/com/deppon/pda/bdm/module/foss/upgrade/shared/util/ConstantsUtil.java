package com.deppon.pda.bdm.module.foss.upgrade.shared.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.ISystemFunDao;

public class ConstantsUtil implements ApplicationContextAware{
	public static String DEFAULT_HTTPED_HOST = "http:\\\\pdabase.deppon.com:80";
	public static  String HTTPED_HOST;
	
	private ISystemFunDao systemFunDao;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		HTTPED_HOST = systemFunDao.queryHttpHost();
		if(StringUtils.isEmpty(HTTPED_HOST)){
			HTTPED_HOST = DEFAULT_HTTPED_HOST;
		}
	}

	public void setSystemFunDao(ISystemFunDao systemFunDao) {
		this.systemFunDao = systemFunDao;
	}
	
}

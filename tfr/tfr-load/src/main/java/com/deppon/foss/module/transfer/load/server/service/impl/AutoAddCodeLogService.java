package com.deppon.foss.module.transfer.load.server.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeLogDao;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeLogService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeLogEntity;

/**
 * @title: AutoAddCodeLogService 
 * @description:  自动补码 日志记录
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 10:56:44
 */
public class AutoAddCodeLogService implements IAutoAddCodeLogService {
	
	/**
	 * 记录日志
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * dao层接口
	 */
	private IAutoAddCodeLogDao autoAddCodeLogDao;
	
	/**  
	 * autoAddCodeLogDao.  
	 *  
	 * @param   autoAddCodeLogDao    the autoAddCodeLogDao to set  
	 * @since   JDK 1.6  
	 */
	public void setAutoAddCodeLogDao(IAutoAddCodeLogDao autoAddCodeLogDao) {
		this.autoAddCodeLogDao = autoAddCodeLogDao;
	}

	/**
	 * <pre>
	 * 	   保存操作.
	 * </pre>
	 * @param entity AddCodeAutoStatusEntiy
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Override
	public void insert(AutoAddCodeLogEntity entity) {
		autoAddCodeLogDao.insert(entity);
	}
	
}
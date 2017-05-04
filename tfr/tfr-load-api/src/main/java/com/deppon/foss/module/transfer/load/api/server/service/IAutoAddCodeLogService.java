package com.deppon.foss.module.transfer.load.api.server.service;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeLogEntity;

/**
 * @title: IAddCodeAutoStatusService 
 * @description: 业务接口.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 10:56:44
 */
public interface IAutoAddCodeLogService extends IService{

	/**
	 * <pre>
	 * 	   保存操作.
	 * </pre>
	 * @param entity 自动补码状态Service层
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	void insert(AutoAddCodeLogEntity entity);

}
package com.deppon.foss.module.transfer.load.api.server.dao;

import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeLogEntity;

/**
 * @title: IAddCodeAutoStatusDao 
 * @description: 自动补码状态dao层 数据接口.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 10:56:44
 */
public interface IAutoAddCodeLogDao {
	

	/**
	* 
	* @MethodName: insert 
	* @description: insert方法
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 10:56:44
	* @param entity void
	*/
	void insert(AutoAddCodeLogEntity entity);
	
}
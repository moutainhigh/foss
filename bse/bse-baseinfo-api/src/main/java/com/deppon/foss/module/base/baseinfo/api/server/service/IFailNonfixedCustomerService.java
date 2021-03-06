package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FailNonfixedCustomerEntity;

public interface IFailNonfixedCustomerService extends IService{
	
	/**
	 * 分页查询
	 *273296
	 * @param start
	 * @param limit
	 * @return
	 */
	List<FailNonfixedCustomerEntity> queryListFailNonfixedCustomerEntity(int start,int limit);
	
	/**
	 * 新增记录
	 *273296
	 * @param entity
	 */
	void insert(FailNonfixedCustomerEntity entity);
	
	/**
	 * 变更推送失败记录数
	 *273296
	 * @param id
	 */
	void updateFailCount(FailNonfixedCustomerEntity entity);
	
	/**
	 * 删除推送成功的记录
	 *273296
	 * @param id
	 */
	void deleteFailNonfixedCustomerEntity(String id);
	
	/**
	 * 推送数据到crm
	 *273296
	 */
	void sendInfoToCrm();

}

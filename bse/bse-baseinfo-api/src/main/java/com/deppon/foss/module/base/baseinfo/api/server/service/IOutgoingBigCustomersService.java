package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutgoingBigCustomersEntity;

/**
 * 外发大客户Service
 * @author 310854
 * @date 2016-2-25 下午5:14:18
 */
public interface IOutgoingBigCustomersService  extends IService{
	/**
	 * 新增外发客户
	 * 
	 * @param entity
	 */
	public void addOutgoingBigCustomers(OutgoingBigCustomersEntity entity);

	/**
	 * 修改外发客户
	 * 
	 * @param entity
	 */
	public void updateOutgoingBigCustomers(OutgoingBigCustomersEntity entity);

	/**
	 * 根据条件分页查询
	 * 
	 * @param entity
	 * @param rowBound
	 * @return
	 */
	public List<OutgoingBigCustomersEntity> queryOutgoingBigCustomersEntitys(
			OutgoingBigCustomersEntity entity, int start, int limit);

	/**
	 * 获取总条数
	 * 
	 * @param condition
	 * @return
	 */
	public long getCountByCondition(OutgoingBigCustomersEntity entity);

	/**
	 * 判断大客户是否外发
	 * @param entity
	 * @return
	 */
	public boolean bigCustomersIsOutgoing(String code);
}

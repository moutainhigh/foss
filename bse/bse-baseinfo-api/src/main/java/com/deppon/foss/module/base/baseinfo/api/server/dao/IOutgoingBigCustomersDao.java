package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutgoingBigCustomersEntity;

/**
 * 外发大客户DAO
 * 
 * @author 310854
 * @date 2016-2-25 下午4:43:21
 */
public interface IOutgoingBigCustomersDao {

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
			OutgoingBigCustomersEntity entity, RowBounds rowBound);

	/**
	 * 获取总条数
	 * 
	 * @param condition
	 * @return
	 */
	public long getCountByCondition(OutgoingBigCustomersEntity condition);

	/**
	 * 根据CODE查询
	 * 
	 * @param codes
	 *            客户编码
	 * @return
	 */
	public List<OutgoingBigCustomersEntity> queryOutgoingBigCustomersEntityByCode(
			String codes);
}

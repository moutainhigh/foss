package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryAddressEntity;

/**
 * 快递派送地址库Dao
 * @author 198771
 *
 */
public interface IExpressDeliveryAddressDao {
	/**
	 * 根据条件分页查询
	 * @param condition
	 * @param rowBound
	 * @return
	 */
	List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitys(ExpressDeliveryAddressEntity condition,RowBounds rowBound);

	/**
	 * 获取总条数
	 * @param condition
	 * @return
	 */
	long getCountByCondition(ExpressDeliveryAddressEntity condition);
	
	/**
	 * 插入数据
	 * @param entity
	 */
	void addExpressDeliveryAddressEntity(ExpressDeliveryAddressEntity entity);
	/**
	 * 批量插入插入数据
	 * @param entity
	 */
	void addExpressDeliveryAddressEntity(List<ExpressDeliveryAddressEntity> entitys);
	
	/**
	 * 修改数据
	 * @param entity
	 */
	void updateExpressDeliveryAddressEntity(ExpressDeliveryAddressEntity entity);
	
	/**
	 * 根据条件分页查询
	 * @param condition
	 * @return
	 */
	List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitysByCondition(
			ExpressDeliveryAddressEntity condition);
	
	/**
	 * 根据条件查询重复数据
	 * @param condition
	 * @return
	 */
	List<ExpressDeliveryAddressEntity> queryAlreadyExpressDeliveryAddressEntitysByCondition(
			ExpressDeliveryAddressEntity condition);
	/**
	 * 根据id查询
	 * @param codes
	 * @return
	 */
	List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitysByCodes(
			List<String> codes);
	/**
	 * 删除
	 * @param codes
	 */
	void deleteExpressDeliveryAddressEntityByCodes(Map<String,Object> params);
	
	/**
	 * 批量修改
	 * @param params
	 */
	void updateBatchExpressDeliveryAddressEntity(Map<String,Object> params);
}

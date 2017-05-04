package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryAddressEntity;


/**
 * 快递派送区域地址库的service
 */
public interface IExpressDeliveryAddressService extends IService{
	
	/**
	 * 根据条件分页查询
	 * @param condition
	 * @param rowBound
	 * @return
	 */
	List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitys(ExpressDeliveryAddressEntity condition,int start,int limit);

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
	 * 修改数据
	 * @param entity
	 */
	void updateExpressDeliveryAddressEntity(List<String> ids,ExpressDeliveryAddressEntity entity);
	/**
	 * 根据条件查询
	 * @param condition
	 * @return
	 */
	List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitysByCondition(
			ExpressDeliveryAddressEntity condition);
	/**
	 * 删除
	 * @param ids
	 */
	void deleteExpressDeliveryAddressEntityByCodes(List<String> ids,
			ExpressDeliveryAddressEntity expressDeliveryAddressEntity);
	/**
	 * 批量修改
	 */
	void updateBatchExpressDeliveryAddressEntity(
			List<String> ids,ExpressDeliveryAddressEntity expressDeliveryAddressEntity);
	/**
	 * 根据id查询
	 * @param ids
	 * @return
	 */
	List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitysByCodes(
			List<String> ids);
}

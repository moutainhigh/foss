package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryAddressDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryAddressEntity;

/**
 * 快递派送地址库Dao
 * @author 198771
 *
 */
@SuppressWarnings("unchecked")
public class ExpressDeliveryAddressDao extends SqlSessionDaoSupport implements IExpressDeliveryAddressDao {
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressDeliveryAddress.";
	
	
	/**
	 * 根据条件分页查询
	 */
	@Override
	public List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitys(ExpressDeliveryAddressEntity condition,RowBounds rowBound) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressDeliveryAddressEntitys", condition, rowBound);
	}

	/**
	 * 获取总条数
	 * @param condition
	 * @return
	 */
	@Override
	public long getCountByCondition(ExpressDeliveryAddressEntity condition) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"getCountByCondition", condition);
	}
	/**
	 * 插入数据
	 * @param entity
	 */
	@Override
	public void addExpressDeliveryAddressEntity(
			ExpressDeliveryAddressEntity entity) {
		List<ExpressDeliveryAddressEntity> entitys = new ArrayList<ExpressDeliveryAddressEntity>();
		entitys.add(entity);
		this.addExpressDeliveryAddressEntity(entitys);
	}
	
	/**
	 * 批量插入插入数据
	 * @param entity
	 */
	@Override
	public void addExpressDeliveryAddressEntity(List<ExpressDeliveryAddressEntity> entitys){
		this.getSqlSession().insert(NAMESPACE+"insertExpressDeliveryAddressEntity", entitys);
	}
	/**
	 * 修改数据
	 * @param entity
	 */
	@Override
	public void updateExpressDeliveryAddressEntity(
			ExpressDeliveryAddressEntity entity) {
		this.getSqlSession().update(NAMESPACE+"updateExpressDeliveryAddressEntity", entity);
	}

	/**
	 * 根据条件查询
	 * @param condition
	 * @return
	 */
	@Override
	public List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitysByCondition(
			ExpressDeliveryAddressEntity condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressDeliveryAddressEntitys", condition);
	}

	/**
	 * 删除数据
	 */
	@Override
	public void deleteExpressDeliveryAddressEntityByCodes(Map<String,Object> params) {
		this.getSqlSession().update(NAMESPACE+"deleteExpressDeliveryAddressEntity", params);
	}

	/**
	 * 批量修改
	 */
	@Override
	public void updateBatchExpressDeliveryAddressEntity(
			Map<String, Object> params) {
		this.getSqlSession().update(NAMESPACE+"deleteExpressDeliveryAddressEntity", params);
	}

	/**
	 * 根据id查询
	 * @param ids
	 * @return
	 */
	@Override
	public List<ExpressDeliveryAddressEntity> queryExpressDeliveryAddressEntitysByCodes(
			List<String> ids) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressDeliveryAddressEntitysByCodes", ids);
	}

	@Override
	public List<ExpressDeliveryAddressEntity> queryAlreadyExpressDeliveryAddressEntitysByCondition(
			ExpressDeliveryAddressEntity condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAlreadyExpressDeliveryAddressEntitys", condition);
	}
}

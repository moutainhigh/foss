package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOutgoingBigCustomersDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutgoingBigCustomersEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 外发客户DAO
 * 
 * @author 310854
 * @date 2016-2-25 下午4:55:55
 */
public class OutgoingBigCustomersDao extends SqlSessionDaoSupport implements
		IOutgoingBigCustomersDao {
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.outgoingBigCustomers.";

	@Override
	public void addOutgoingBigCustomers(OutgoingBigCustomersEntity entity) {
		// TODO Auto-generated method stub
		entity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(
				NAMESPACE + "insertOutgoingBigCustomersEntity", entity);
	}

	@Override
	public void updateOutgoingBigCustomers(OutgoingBigCustomersEntity entity) {
		// TODO Auto-generated method stub
		this.getSqlSession().update(
				NAMESPACE + "updateOutgoingBigCustomersEntity", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutgoingBigCustomersEntity> queryOutgoingBigCustomersEntitys(
			OutgoingBigCustomersEntity entity, RowBounds rowBound) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(
				NAMESPACE + "queryOutgoingBigCustomersEntitys", entity,
				rowBound);
	}

	@Override
	public long getCountByCondition(OutgoingBigCustomersEntity entity) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "getCountByCondition", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutgoingBigCustomersEntity> queryOutgoingBigCustomersEntityByCode(
			String code) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(
				NAMESPACE + "queryOutgoingBigCustomersEntityByCode", code);
	}

}

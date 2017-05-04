package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAccessPointDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 接驳点Dao
 * @author 198771
 *
 */
@SuppressWarnings("unchecked")
public class AccessPointDao extends SqlSessionDaoSupport implements IAccessPointDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.accessPoint.";
	
	
	/**
	 * 添加接驳点
	 * @param entity
	 */
	@Override
	public void addAccessPoint(AccessPointEntity entity) {
		this.getSqlSession().insert(NAMESPACE+"insertAccessPoint", entity);
	}


	/**
	 * 根据条件查询
	 * @param entity
	 * @return
	 */
	@Override
	public List<AccessPointEntity> queryAccessPoints(AccessPointEntity entity,int start,int limit) {
		RowBounds rb = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryAccessPointsByCondition"
				, entity,rb);
	}

	/**
	 * 根据条件查询总条数
	 * @param entity
	 * @return
	 */
	@Override
	public long getCount(AccessPointEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"getCountByCondition",entity);
	}


	/**
	 * 根据接驳点名称查找
	 */
	@Override
	public List<AccessPointEntity> queryAccessPointsByName(
			AccessPointEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAccessPointsByName",entity);
	}
	
	/**
	 * 根据中转场编码查找
	 */
	@Override
	public List<AccessPointEntity> queryAccessPointsByTransferCode(
			AccessPointEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAccessPointsByTransferCode",entity);
	}
	
	/**
	 * 根据接驳点名称查找
	 */
	@Override
	public AccessPointEntity queryAccessPointsByCode(
			AccessPointEntity entity) {
		return (AccessPointEntity)this.getSqlSession().selectOne(NAMESPACE+"queryAccessPointsByCode",entity);
	}

	/**
	 * 禁用/启用
	 */
	@Override
	public void updateAccessPointStatu(Map<String,Object> maps) {
		this.getSqlSession().update(NAMESPACE+"updateAccessPointStatu", maps);
	}


	/**
	 * 作废
	 * @param entity
	 */
	@Override
	public void deleteAccessPoint(AccessPointEntity entity) {
		this.getSqlSession().update(NAMESPACE+"deleteAccessPoint", entity);
	}


	/**
	 * 根据参数动态查询
	 * @param entity
	 */
	@Override
	public List<AccessPointEntity> queryAccessPointByCommon(AccessPointEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAccessPointByCommon"
				, entity);
	}

	/**
	 *根据司机工号查询所属部门 
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitysByEmpCode(
			String empCode) {
		return this.getSqlSession().selectList(NAMESPACE+"queryOrgAdministrativeInfoEntitysByEmpCode", empCode);
	}
}

package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDepotAddressDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepotAddressEntity;

/**
 * 
 * @ClassName: DepotAddressDao 
 * @Description: 进仓地址 DAO
 * @author 310854-liuzhenhua
 * @date 2016-9-1 下午3:40:29 
 *
 */
public class DepotAddressDao extends SqlSessionDaoSupport implements IDepotAddressDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.depotAddress.";

	@SuppressWarnings("unchecked")
	@Override
	public List<DepotAddressEntity> queryDepotAddress(
			DepotAddressEntity entity, RowBounds rowBound) {
		// TODO Auto-generated method stub
		try{
			return this.getSqlSession().selectList(
					NAMESPACE + "queryDepotAddress", entity,rowBound);
		}catch(Exception e){
			throw new BusinessException("查询进仓地址出错："+e.getMessage());
		}
	}

	@Override
	public long getCountByCondition(DepotAddressEntity entity) {
		// TODO Auto-generated method stub
		try{
			return (Long) this.getSqlSession().selectOne(
					NAMESPACE + "getCountByCondition", entity);	
		}catch(Exception e){
			throw new BusinessException("查询进仓地址总数出错："+e.getMessage());
		}
	}

	@Override
	public DepotAddressEntity addDepotAddress(DepotAddressEntity entity) {
		// TODO Auto-generated method stub
		DepotAddressEntity newEntity = null;
		try{
			this.getSqlSession().insert(
					NAMESPACE + "addDepotAddress", entity);
			
			newEntity = entity;
		}catch(Exception e){
			throw new BusinessException("新增进仓地址出错："+e.getMessage());
		}
		return newEntity;
	}

	@Override
	public DepotAddressEntity updateDepotAddress(DepotAddressEntity entity) {
		// TODO Auto-generated method stub
		DepotAddressEntity newEntity = null;
		try{
			this.getSqlSession().update(
					NAMESPACE + "updateDepotAddress", entity);
			
			newEntity = entity;
		}catch(Exception e){
			throw new BusinessException("修改进仓地址出错："+e.getMessage());
		}
		return newEntity;
	}

	@Override
	public DepotAddressEntity deleteDepotAddress(DepotAddressEntity entity) {
		// TODO Auto-generated method stub
		DepotAddressEntity newEntity = null;
		try{
			this.getSqlSession().delete(
					NAMESPACE + "deleteDepotAddress", entity);
			
			newEntity = entity;
		}catch(Exception e){
			throw new BusinessException("作废进仓地址出错："+e.getMessage());
		}
		return newEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepotAddressEntity> queryDepotAddressByDepCode(String depCode) {
		// TODO Auto-generated method stub
		try{
			return this.getSqlSession().selectList(
					NAMESPACE + "queryDepotAddressByDepCode", depCode);
		}catch(Exception e){
			throw new BusinessException("通过营业部code获取进仓地址信息出错："+e.getMessage());
		}
	}
}

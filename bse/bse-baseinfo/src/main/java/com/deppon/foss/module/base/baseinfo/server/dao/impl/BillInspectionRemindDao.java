package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillInspectionRemindDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;

public class BillInspectionRemindDao extends SqlSessionDaoSupport implements IBillInspectionRemindDao{

	
	private static final String NAMESPACE="foss.bse.bse-baseinfo.billInspectionRemind.";
	
	/**根据条件分页查询
	 * 
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillInspectionRemindEntity> queryAll(BillInspectionRemindEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryAll", entity, rowBounds);
	}
	/**
	 * 根据条件查询记录数
	 * @param entity
	 * @return
	 */
	@Override
	public Long queryCount(BillInspectionRemindEntity entity) {
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryCount", entity);
	}

	/**
	 * 新增数据
	 * @param entity
	 * @return
	 */
	@Override
	public long addBillInspectionRemind(BillInspectionRemindEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE+"addBillInspectionRemind", entity);
	}

	/**
	 * 删除记录
	 * @param entity
	 * @return
	 */
	@Override
	public long deleteBillInspectionRemind(BillInspectionRemindEntity entity) {
		
		return this.getSqlSession().delete(NAMESPACE+"deleteBillInspectionRemind", entity);
	}

	/**
	 * 更新记录
	 * @param entity
	 * @return
	 */
	@Override
	public long updateBillInspectionRemind(BillInspectionRemindEntity entity) {
		
//		return this.getSqlSession().update(NAMESPACE+"updateBillInspectionRemind", entity);
		return 0;
	}

	/**
	 * 更具ID查询记录
	 * @param id
	 * @return
	 */
	@Override
	public BillInspectionRemindEntity queryBillInspectionRemindById(String id) {
	 
		return (BillInspectionRemindEntity) this.getSqlSession().selectList(NAMESPACE+"queryBillInspectionRemindById", id).get(0);
	}
	
	
	/**
	 * 根据省份城市区县一级区域级别查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BillInspectionRemindEntity queryBillInspectionRemindByRegionCode(
			BillInspectionRemindEntity entity) {
		
	  List<BillInspectionRemindEntity> billInspectionReminds= 
			  this.getSqlSession().selectList(NAMESPACE+"queryBillInspectionRemindByRegionCode", entity);
	  return billInspectionReminds.size()>0?billInspectionReminds.get(0):null;
	}
	
	
	

}

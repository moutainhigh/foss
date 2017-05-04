package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 根据条件查询快递员信息--公共选择器Dao
 * 
 * @author WangPeng
 * @date   2013-08-28 9:30 AM
 *
 */
public class CommonExpressEmployeeDao extends SqlSessionDaoSupport implements ICommonExpressEmployeeDao {
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonExpressEmployee.";
	/**
	 * 根据条件查询快递员信息
	 * 
	 * @author WangPeng
	 * @Date   2013-8-28 上午9:36:24
	 * @param  entity
	 * @param  start
	 * @param  limit
	 * @return List<EmployeeEntity>
	 * 
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeeEntity> queryExpressEmployeeByCondition(EmployeeEntity entity,int start,int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressEmployeeByCondition", entity, rowBounds);
	}
	
	/**
	 * 记录行数
	 * 
	 * @author WangPeng
	 * @Date   2013-8-28 上午9:36:24
	 * @param  entity
	 * @return Long
	 * 
	 *
	 */
	public Long recordQueryCount(EmployeeEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "recordQueryCount", entity);
	}
	
	/**
	 * 根据工号查询出此员工是否为快递员
	 * 提供给中转-232608
	 */
	@Override
	public EmployeeEntity queryCourierByCode(EmployeeEntity entity) {
		// TODO Auto-generated method stub
		entity.setActive(FossConstants.ACTIVE);
		return (EmployeeEntity) getSqlSession().selectOne(NAMESPACE + "queryExpressEmployeeByCondition", entity);
	}
	
	
}

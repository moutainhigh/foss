package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMinFeePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;

public class MinFeePlanDao extends SqlSessionDaoSupport implements IMinFeePlanDao {

	/**
	 * 自提件最低一票Mapper NameSpace
	 */
	private static final String MIN_FEE_PLAN_NAME_SPACE = "com.deppon.foss.module.pickup.pricing.api.server.dao.MinFeePlanEntityMapper.";

	/**
	 * 新增自提件最低一票记录
	 */
	private static final String INSERT_MINFEEPLAN = "insertMinFeePlan";
	
	/**
	 * 根据指定条件查询记录集合
	 */
	public static final String SELECT_MINFEEPLAN_BY_CONDITION = "selectMinFeePlanByCondition";
	
	/**
	 * 分页查询
	 */
	private static final String SELECT_PAGING_MINFEEPLAN = "selectPagingMinFeePlan";
	
	/**
	 * 查询指定条件记录数
	 */
	private static final String COUNT_MINFEEPLAN_BY_CONDITION = "countMinFeePlanByCondition";
	
	/**
	 * 删除指定id的记录
	 */
	private static final String DELETE_MINFEEPLAN_BY_ID = "deleteMinFeePlanById";
	
	/**
	 * 更新记录
	 */
	private static final String UPDATE_MINFEEPLAN_BY_ID = "updateMinFeePlanById";
	
	/**
	 * 根据指定日期查询记录
	 */
	private static final String SELECT_MINFEEPLAN_BY_SPECIFIEDDATE = "selectMinFeePlanBySpecifiedDateAndCondition";
	
	/**
	 * 检查指定的记录时间是否合法
	 */
	private static final String CHECK_BEGINTIME_ENDTIME_VALID = "checkBeginTimeAndEndTimeValid";

	@Override
	public int insertMinFeePlan(MinFeePlanEntity minFeePlanEntity) {
		String sql = MIN_FEE_PLAN_NAME_SPACE + INSERT_MINFEEPLAN;
		return getSqlSession().insert(sql, minFeePlanEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MinFeePlanEntity> selectMinFeePlanList(MinFeePlanEntity minFeePlanEntity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		String sql = MIN_FEE_PLAN_NAME_SPACE + SELECT_PAGING_MINFEEPLAN;
		return getSqlSession().selectList(sql, minFeePlanEntity, rowBounds);
	}

	@Override
	public Long countMinFeePlan(MinFeePlanEntity minFeePlanEntity) {
		String sql = MIN_FEE_PLAN_NAME_SPACE + COUNT_MINFEEPLAN_BY_CONDITION;
		return (Long)getSqlSession().selectOne(sql, minFeePlanEntity);
	}

	@Override
	public int deleteMinFeePlanById(String id) {
		String sql = MIN_FEE_PLAN_NAME_SPACE + DELETE_MINFEEPLAN_BY_ID;
		return getSqlSession().delete(sql, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MinFeePlanEntity selectMinFeePlanById(String id) {
		MinFeePlanEntity minFeePlanEntity = new MinFeePlanEntity();
		minFeePlanEntity.setId(id);
		String sql = MIN_FEE_PLAN_NAME_SPACE + SELECT_MINFEEPLAN_BY_CONDITION;
		List<MinFeePlanEntity> list = getSqlSession().selectList(sql, minFeePlanEntity);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			if(list.size() > 1){
				throw new BusinessException("ID = " + id + "对应多条最低一票记录");			
			}else{
				return list.get(0);
			}
		}
	}

	@Override
	public int updateMinFeePlanEntity(MinFeePlanEntity minFeePlanEntity) {
		String sql = MIN_FEE_PLAN_NAME_SPACE + UPDATE_MINFEEPLAN_BY_ID;
		return getSqlSession().delete(sql, minFeePlanEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MinFeePlanEntity> checkMinFeePlanEntityTimeValid(
			MinFeePlanEntity minFeePlanEntity) {
		String sql = MIN_FEE_PLAN_NAME_SPACE + CHECK_BEGINTIME_ENDTIME_VALID;
		return getSqlSession().selectList(sql, minFeePlanEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MinFeePlanEntity> selectMinFeePlanBySpecifiedDateAndCondition(MinFeePlanEntity minFeePlanEntity){
		String sql = MIN_FEE_PLAN_NAME_SPACE + SELECT_MINFEEPLAN_BY_SPECIFIEDDATE;
		return getSqlSession().selectList(sql, minFeePlanEntity);
	}
	

}

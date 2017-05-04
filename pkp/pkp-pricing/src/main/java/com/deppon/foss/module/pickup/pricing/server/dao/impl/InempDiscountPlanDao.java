package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IInempDiscountPlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;

/**
 * 内部员工折扣方案dao实现类
 * @author 225131
 *
 */
public class InempDiscountPlanDao extends SqlSessionDaoSupport implements IInempDiscountPlanDao {
	/**
	 * ibatis mapper namespace
	 */
    private static final String NAME_SPACE = "foss.pkp.pkp-pricing.InempDiscountPlanEntityMapper.";
    
    /**
     * 新增内部员工折扣方案   根据传入实体属性值是否为空进行选择性的插入
     */
	@Override
	public int insertSelective(InempDiscountPlanEntity entity) {
		
		return getSqlSession().insert(NAME_SPACE+"insertSelective", entity);
	}
	
	/**
	 * 根据内部员工折扣方案的ID删除方案信息 支持批量删除操作
	 */
	@Override
	public int deleteByIds(List<String> inempDiscountPlanIds) {
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("inempDiscountPlanIds", inempDiscountPlanIds);
		return getSqlSession().delete(NAME_SPACE+"deleteByIds", map);
	}
	
	/**
	 * 修改内部员工折扣方案信息
	 */
	@Override
	public int updateByIdSelective(InempDiscountPlanEntity entity) {
		
		return getSqlSession().update(NAME_SPACE+"updateByIdSelective", entity);
	}
	
	/**
	 * 分页查询内部员工折扣方案 根据传入的实体属性进行查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InempDiscountPlanEntity> queryInempDiscountPlanList(
			InempDiscountPlanEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAME_SPACE+"queryInempDiscountPlanList", entity,rowBounds);
	}

	/**
	 * 查询满足当前查询条件的内部员工折扣方案的数量
	 */
	@Override
	public Long queryInempDiscountPlanListCount(InempDiscountPlanEntity entity) {
		return (Long) getSqlSession().selectOne(NAME_SPACE+"queryInempDiscountPlanListCount", entity);
	}

	/**
	 * 根据方案ID激活方案  
	 */
	@Override
	public void activeInempDiscountPlan(InempDiscountPlanEntity entity) {
		
		 getSqlSession().selectList(NAME_SPACE+"activeInempDiscountPlan", entity);
	}
	
	/**
	 * 根据方案ID中止方案  
	 */
	@Override
	public void stopInempDiscountPlan(InempDiscountPlanEntity entity) {
		getSqlSession().selectList(NAME_SPACE+"stopInempDiscountPlan", entity);
	}
	 /**
	  * 根据条件查询方案集合 不分页
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<InempDiscountPlanEntity> queryInempDiscountPlanByCondition(
			InempDiscountPlanEntity entity) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(NAME_SPACE+"queryInempDiscountPlanByCondition", entity);
	}
}

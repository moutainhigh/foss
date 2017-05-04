package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExpressDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
/**
 * 
 * Copyright (C) 2015 Asiainfo-Linkage
 *
 *
 * @className:com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressDiscountPlanDao
 * @description:快递折扣方案实现类
 *
 * @version:v1.0.0
 * @author:DP-FOSS-YANGKANG
 *
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2015-1-8     DP-FOSS-YANGKANG       v1.0.0        create
 *
 *
 */
public class ExpressDiscountPlanDao extends SqlSessionDaoSupport implements IExpressDiscountPlanDao {
	/**
	 * ibatis mapper namespace
	 */
    private static final String NAME_SPACE = "foss.pkp.pkp-pricing.ExpressDiscountPlanEntityMapper.";
    
    /**
     * 新增快递折扣方案   根据传入实体属性值是否为空进行选择性的插入
     */
	@Override
	public int insertSelective(ExpressDiscountEntity entity) {
		
		return getSqlSession().insert(NAME_SPACE+"insertSelective", entity);
	}
	
	/**
	 * 根据快递折扣方案的ID删除方案信息 支持批量删除操作
	 */
	@Override
	public int deleteByIds(List<String> discountPlanIds) {
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("discountPlanIds", discountPlanIds);
		return getSqlSession().delete(NAME_SPACE+"deleteByIds", map);
	}
	
	/**
	 * 修改快递折扣方案信息
	 */
	@Override
	public int updateByIdSelective(ExpressDiscountEntity entity) {
		
		return getSqlSession().update(NAME_SPACE+"updateByIdSelective", entity);
	}
	
	/**
	 * 分页查询快递折扣方案 根据传入的实体属性进行查询   其中方案名称进行模糊查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDiscountEntity> queryExpressDiscountPlanList(
			ExpressDiscountEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAME_SPACE+"queryExpressDiscountPlanList", entity,rowBounds);
	}

	/**
	 * 查询满足当前查询条件的快递折扣方案的数量
	 */
	@Override
	public Long queryExpressDiscountPlanListCount(ExpressDiscountEntity entity) {
		return (Long) getSqlSession().selectOne(NAME_SPACE+"queryExpressDiscountPlanListCount", entity);
	}

	/**
	 * 根据方案ID激活方案  
	 */
	@Override
	public void activeExpressDiscountPlan(ExpressDiscountEntity entity) {
		
		 getSqlSession().selectList(NAME_SPACE+"activeExpressDiscountPlan", entity);
	}
	
	/**
	 * 根据方案ID中止方案  
	 */
	@Override
	public void stopExpressDiscountPlan(ExpressDiscountEntity entity) {
		getSqlSession().selectList(NAME_SPACE+"stopExpressDiscountPlan", entity);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressDiscountPlanDao.queryExpressDiscountPlanDetailList
	 * @Description:分页查询快递折扣方案明细信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-9 下午6:39:27
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-9    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDiscountDto> queryExpressDiscountPlanDetailList(
			ExpressDiscountDto entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAME_SPACE+"queryExpressDiscountPlanDetailList", entity,rowBounds);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressDiscountPlanDao.queryExpressDiscountPlanDetailListCount
	 * @Description:查询快递折扣方案明细信息记录总数
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午10:42:15
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public Long queryExpressDiscountPlanDetailListCount(
			ExpressDiscountDto entity) {
		return (Long)getSqlSession().selectOne(NAME_SPACE+"queryExpressDiscountPlanDetailListCount", entity);
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressDiscountPlanDao.insertDetailSelective
	 * @Description:新增快递折扣方案明细信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午10:51:46
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public int insertDiscountDetailSelective(ExpressDiscountDto detailEntity) {
		return getSqlSession().insert(NAME_SPACE+"insertDiscountDetailSelective", detailEntity);
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressDiscountPlanDao.updateDiscountDetailSelective
	 * @Description:修改快递折扣明细信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午10:58:33
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public int updateDiscountDetailSelective(ExpressDiscountDto detailEntity) {
		return getSqlSession().update(NAME_SPACE+"updateDiscountDetailSelective", detailEntity);
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressDiscountPlanDao.deleteDiscountDetailById
	 * @Description:根据明细ID删除折扣明细信息
	 *
	 * @param detailEntity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午11:00:26
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public int deleteDiscountDetailByIds(List<String> discountDetailIds) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("discountDetailIds", discountDetailIds);
		return getSqlSession().delete(NAME_SPACE+"deleteDiscountDetailByIds",map);
	}
	
	 /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.queryExpressDiscountPlanById
     * @Description:根据查询条件查询快递折扣方案
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-12 下午4:29:12
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-12    DP-FOSS-YANGKANG      v1.0.0         create
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDiscountEntity> queryExpressDiscountPlanByCondition(
			ExpressDiscountEntity entity) {
		return getSqlSession().selectList(NAME_SPACE+"queryExpressDiscountPlanByCondition", entity);
	}
	
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.queryExpressDiscountDetailByCondition
     * @Description:根据查询条件查询快递折扣方案明细信息（不分页）
     *
     * @param detailEntity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-14 上午9:50:10
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-14    DP-FOSS-YANGKANG      v1.0.0         create
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressDiscountDto> queryExpressDiscountDetailByCondition(ExpressDiscountDto detailEntity){
		return getSqlSession().selectList(NAME_SPACE+"queryExpressDiscountDetailByCondition", detailEntity);
	}
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.findExpressDiscountByPlanNames
     * @Description:根据多个方案名称查出折扣方案
     *
     * @param map
     * @return
     *
     * @version:v1.0
     * @author:王增明
     * @date:2015-07-27 上午14:36:00
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-07-27    王增明      v1.0.0         create
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ExpressDiscountEntity> findExpressDiscountByPlanNames(Map map) {
		return getSqlSession().selectList(NAME_SPACE+"findExpressDiscountByPlanNames", map);
	}
}

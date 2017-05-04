package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;

/**
 * 内部员工折扣方案dao接口
 * dp-foss-dongjialing
 * @author 225131
 *
 */
public interface IInempDiscountPlanDao {

	/**
	 * 新增内部员工折扣方案
	 * @param entity
	 * @return
	 */
	int insertSelective(InempDiscountPlanEntity entity);
	
	/**
	 * 根据内部员工折扣方案Id删除方案信息，支持批量删除
	 * @param inempDiscountPlanIds
	 * @return
	 */
	int deleteByIds(List<String> inempDiscountPlanIds);
	
	/**
	 * 修改折扣方案
	 * @param entity
	 * @return
	 */
    int updateByIdSelective(InempDiscountPlanEntity entity);
    
    /**
     * 分页查询内部员工折扣方案
     * @param entity
     * @param start
     * @param limit
     * @return
     */
    List<InempDiscountPlanEntity> queryInempDiscountPlanList(InempDiscountPlanEntity entity,int start,int limit);
   
    /**
     * 查询当前内部员工折扣方案的数量
     * @param entity
     * @return
     */
    Long queryInempDiscountPlanListCount(InempDiscountPlanEntity entity);
    
    /**
     * 根据方案id激活方案
     * @param entity
     */
    void activeInempDiscountPlan(InempDiscountPlanEntity entity);
    /**
     * 根据方案Id中止方案
     * @param entity
     */
	void stopInempDiscountPlan(InempDiscountPlanEntity entity);
	
	 /**
	  * 根据条件查询方案集合 不分页
	  * @param entity
	  * @return
	  */
    List<InempDiscountPlanEntity> queryInempDiscountPlanByCondition(InempDiscountPlanEntity entity);    		
}

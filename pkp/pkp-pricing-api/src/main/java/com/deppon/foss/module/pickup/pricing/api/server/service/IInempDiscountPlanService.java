package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;

/**
 * 内部员工折扣方案
 * dp-foss-dongjialing
 * @author 225131
 *
 */
public interface IInempDiscountPlanService extends IService{
	/**
	 * 新增内部员工折扣方案
	 * @param entity
	 */
	void insertSelective(InempDiscountPlanEntity entity);
	
	/**
	 * 根据内部员工折扣方案Id删除方案,支持批量操作
	 * @param discountPlanIds
	 * @return
	 */
	int deleteByIds(List<String> inempDiscountPlanIds);
	/**
	 * 修改内部员工折扣方案
	 * @param entity
	 * @param oldChannelCodes
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
     * 查询满足当前查询条件的内部员工折扣方案数量
     * @param entity
     * @return
     */
    Long queryInempDiscountPlanListCount(InempDiscountPlanEntity entity);
    /**
     * 根据方案Id激活方案
     * @param entity
     */
    void activeInempDiscountPlan(InempDiscountPlanEntity entity);
    /**
     * 根据方案Id中止方案
     * @param entity
     */
	void stopInempDiscountPlan(InempDiscountPlanEntity entity);
	/**
	 * 根据方案Id查询
	 * @param discountEntity
	 * @return
	 */
	InempDiscountPlanEntity queryInempDiscountPlanById(InempDiscountPlanEntity entity);
	/**
	 * 根据条件查询内部员工折扣方案 不分页
	 */
	List<InempDiscountPlanEntity> queryInempDiscountPanByCondition(InempDiscountPlanEntity entity);

	void upgradeInempDiscountPlan(
			InempDiscountPlanEntity inempDiscountPlanEntity);
}

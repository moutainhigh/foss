package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;

/**
 * 
 * 自提价最低一票Dao
 * 
 * @author 026123-foss-lufeifei
 * @date 2013-8-14 下午3:38:36
 */
public interface IMinFeePlanDao {

	/**
	 * 
	 * 插入一条记录
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 下午3:38:54
	 */
	public int insertMinFeePlan(MinFeePlanEntity minFeePlanEntity);

	/**
	 * 
	 * 根据指定条件查询记录集合
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 下午3:39:16
	 */
	public List<MinFeePlanEntity> selectMinFeePlanList(
			MinFeePlanEntity minFeePlanEntity, int start, int limit);

	/**
	 * 
	 * 查询指定条件掉记录数
	 * 
	 * @author 026123-foss-lufeifei
	 * @date 2013-8-14 下午3:39:31
	 */
	public Long countMinFeePlan(MinFeePlanEntity minFeePlanEntity);

	/**
	 * 
	 * 根据id删除记录
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 上午10:57:14
	 */
	public int deleteMinFeePlanById(String id);

	/**
	 * 
	 * 根据id查询记录
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 上午10:58:21
	 */
	public MinFeePlanEntity selectMinFeePlanById(String id);

	/**
	 * 
	 * 更新记录
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-15 上午11:28:35
	 */
	public int updateMinFeePlanEntity(MinFeePlanEntity minFeePlanEntity);

	/**
	 * 检查与当前方案时间冲突的已激活方案
	 * @param minFeePlanEntity
	 * @return
	 */
	public List<MinFeePlanEntity> checkMinFeePlanEntityTimeValid(MinFeePlanEntity minFeePlanEntity);

	/**
	 * 根据指定业务日期查询最低一票方案
	 * 
	 * @param businessDate
	 * @return
	 */
	public List<MinFeePlanEntity> selectMinFeePlanBySpecifiedDateAndCondition(
			MinFeePlanEntity minFeePlanEntity);
}

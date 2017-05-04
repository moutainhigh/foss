
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;

/**
 * 
 * @ClassName: ISrvDiscountPriorityService
 * 
 * @Description:BUG-55198 优惠方案类型服务
 * 
 *                   客户端在离线使用时，需要开单计算价格。 所以价格信息会提前下载到客户端。每次用户在登录时，
 *                   会根据用户所在组织，查询下载和自己组织相关的信息。 这些信息包括：基础数据，价格，折扣等。
 *                   以下主要描述与价格，折扣相关的数据的下载。 目前框架主要支持的是单表下载， 根据最后更新时间来增量更新数据。
 *                   但是价格里面关系复杂，根据最后更新时间， 不能满足下载到正确的价格的需求。所以，在此方案中，
 *                   会描述如何下载到准确的价格。并且是做增量下载。
 * 
 * @author deppon-157229-zxy
 * 
 * @date 2013-10-12 
 * 
 * 
 */
public interface ISrvDiscountPriorityService {
	
	/**
	 * 插条记录
	 */
	void add(DiscountPriorityEntity entity);
	 
	/**
	 * 更新条记录
	 */
	void update(DiscountPriorityEntity entity);
	
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(DiscountPriorityEntity entity);
	
	/**
	 * 删除记录
	 */
	void deleteID(String id);

}

package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.GradientDiscountItemDto;

/**
 * @author 218392 张永雪
 * @date 2015-06-05 11:33:20
 * 零担梯度折扣 IGradientDiscountDao
 */
public interface IGradientDiscountDao {
	
	/**
	 * 查询数目操作(改为查询实体)
	 * 验证从CRM同步过来的数据在FOSS表中的存在，查询参数必须是客户编码+方案类型(数据字典形式)
	 * 因为一个客户只能对应一种方案类型
	 * 
	 */
	Long queryGradientDiscountNum(GradientDiscountEntity entity);
	
	/**
	 * 根据客户编码查询梯度基础资料的ID和CRM_ID
	 */
	List<GradientDiscountItemDto> queryGradientDiscountByCode(GradientDiscountEntity entity);
	
	/**
	 * 梯度折扣基础资料 --插入操作
	 * 把CRM同步过来的“梯度折扣基础资料”数据增加到FOSS梯度折扣基础资料表中
	 */
	int addGradientDiscountInfo(GradientDiscountEntity entity);
	
	/**
	 * 梯度折扣基础资料详情 -- 插入操作
	 * 把CRM同步过来的“梯度折扣基础资料详情”数据增加到FOSS梯度折扣基础资料详情表中
	 */
	int addGradientDiscItemsInfo(GradientDiscItemEntity entity);
	
	/**
	 * 修改“梯度折基础资料”
	 * 根据 ID进行修改，这样保证修改的是同一条数据
	 */
	void updateGradientDiscount(GradientDiscountEntity entity);

	
	/**
	 * 修改“梯度折基础资料详情”
	 * 根据优惠方案ID DISCOUNT_CRM_ID 进行修改，这样保证此字段关联梯度折基础资料表中的CRM_ID，修改的数据是一致的
	 */
	void updateGradientDiscItem(GradientDiscItemEntity entity);
	
	/**
	 * 查询操作（提供给接送货开发组）
	 */
	List<GradientDiscountItemDto> queryGradientDiscountByCondition(GradientDiscountEntity entity);
	
}

package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.GradientDiscountItemDto;

/**
 * 零担梯度折扣需求 服务端 restful
 * @author 218392
 * @date 2015-06-03 15:11:20
 *
 */
public interface IGradientDiscountService {
	/**
	 * 查询数目操作
	 * 验证从CRM同步过来的数据在FOSS表中的存在，查询参数必须是客户编码+方案类型(数据字典形式)
	 * 因为一个客户只能对应一种方案类型
	 * 
	 */
	Long queryGradientDiscountNum(GradientDiscountEntity entity);
	
	/**
	 * 根据客户编码CODE查询梯度折扣基础资料表中的ID和CRM_ID
	 * @param entity
	 * @return
	 */
	List<GradientDiscountItemDto> queryGradientDiscountByCode(GradientDiscountEntity entity);
	
	/**
	 * 梯度折扣基础资料 --插入操作
	 * 把CRM同步过来的“梯度折扣基础资料”数据增加到FOSS梯度折扣基础资料表中
	 */
	void addGradientDiscountInfo(GradientDiscountEntity entity);
	
	
	/**
	 * 修改操作
	 * 
	 */
	void updateGradientDiscountInfo(GradientDiscountEntity entity);
	
	
	/**
	 * 查询操作（提供给接送货开发组）
	 * 
	 */
	List<GradientDiscountItemDto> queryGradientDiscountByCondition(GradientDiscountEntity entity);
	
	/**
	 * 修改“梯度折基础资料”
	 * 根据 ID进行修改，这样保证修改的是同一条数据
	 */
	void updateGradientDiscount(GradientDiscountEntity entity);

	
	/**
	 * 修改“梯度折基础资料详情”
	 */
	void updateGradientDiscItem(GradientDiscItemEntity entity);
	
}

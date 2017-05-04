package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IGradientDiscountDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.GradientDiscountItemDto;
import com.deppon.foss.util.define.FossConstants;
/**
 * @author 218392
 * @date 2015-06-05 11:34:89
 */
@SuppressWarnings("unchecked")
public class GradientDiscountDao extends SqlSessionDaoSupport implements IGradientDiscountDao {
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.gradientDiscountEntity.";

	/**
	 * 查询数目操作
	 * 验证从CRM同步过来的数据在FOSS表中的存在，查询参数必须是客户编码+方案类型(数据字典形式)
	 * 因为一个客户只能对应一种方案类型
	 */
	@Override
	public Long queryGradientDiscountNum(GradientDiscountEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryGradientDiscountNum", entity);
	}

	/**
	 * 梯度折扣基础资料 --插入操作
	 * 把CRM同步过来的“梯度折扣基础资料”数据增加到FOSS梯度折扣基础资料表中
	 */
	@Override
	public int addGradientDiscountInfo(GradientDiscountEntity entity) {
		/**
		 * set  ID
		 */
		entity.setId(UUID.randomUUID().toString());
		entity.setModifyDate(null);
		return this.getSqlSession().insert(NAMESPACE+"addGradientDiscountInfo", entity);
		
	}
	/**
	 * 梯度折扣基础资料详情 -- 插入操作
	 * 把CRM同步过来的“梯度折扣基础资料详情”数据增加到FOSS梯度折扣基础资料详情表中
	 */
	@Override
	public int addGradientDiscItemsInfo(GradientDiscItemEntity entity) {
		/**
		 * set ID
		 */
		entity.setItemId(UUID.randomUUID().toString());
		entity.setModifyDate(null);
		return this.getSqlSession().insert(NAMESPACE+"addGradientDiscItemsInfo", entity);
		
	}
	
	/**
	 * 查询操作（提供给接送货开发组）
	 */
	@Override
	public List<GradientDiscountItemDto> queryGradientDiscountByCondition(GradientDiscountEntity entity) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryGradientDiscountByCondition", entity);
		
	}

	/**
	 * 根据客户编码查询梯度基础资料的ID和CRM_ID
	 */
	@Override
	public List<GradientDiscountItemDto> queryGradientDiscountByCode(
			GradientDiscountEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryGradientDiscountByCode", entity);
	}

	/**
	 * 修改“梯度折基础资料”
	 * 根据 ID进行修改，这样保证修改的是同一条数据
	 */
	@Override
	public void updateGradientDiscount(GradientDiscountEntity entity) {
		entity.setActive(FossConstants.INACTIVE);	
		entity.setModifyDate(new Date());
		this.getSqlSession().update(NAMESPACE+"updateGradientDiscount", entity);
		
	}

	/**
	 * 修改“梯度折基础资料详情”
	 * 根据优惠方案ID DISCOUNT_CRM_ID 进行修改，这样保证此字段关联梯度折基础资料表中的CRM_ID，修改的数据是一致的
	 */
	@Override
	public void updateGradientDiscItem(GradientDiscItemEntity entity) {
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(new Date());
		this.getSqlSession().update(NAMESPACE+"updateGradientDiscItem", entity);
		
	}
	
}

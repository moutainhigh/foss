package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILtDiscountBackInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.util.define.FossConstants;
/**
 * 零担事后折概要信息DAO实现类
 * @author 261997
 * CSS 2015-08-18
 */
@SuppressWarnings("unchecked")
public class LtDiscountBackInfoDao extends SqlSessionDaoSupport implements ILtDiscountBackInfoDao{
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.cusLtdiscountafter.";
	
	/**
	 * 查询数目操作
	 * 验证从CRM同步过来的数据在FOSS表中的存在，查询参数必须是客户编码+方案类型(数据字典形式)
	 * 因为一个客户只能对应一种方案类型
	 */
	@Override
	public Long queryCusLtdiscountafterNum(CusLtdiscountafterEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryCusLtdiscountafterNum", entity);
	}

	/**
	 * 梯度折扣基础资料 --插入操作
	 * 把CRM同步过来的“梯度折扣基础资料”数据增加到FOSS梯度折扣基础资料表中
	 */
	@Override
	public int addCusLtdiscountafter(CusLtdiscountafterEntity entity) {
		/**
		 * set  ID
		 */
		entity.setId(UUID.randomUUID().toString());
		entity.setModifyDate(null);
		return this.getSqlSession().insert(NAMESPACE+"addCusLtdiscountafter", entity);
		
	}
	
	/**
	 * 梯度折扣基础资料详情 -- 插入操作
	 * 把CRM同步过来的“梯度折扣基础资料详情”数据增加到FOSS梯度折扣基础资料详情表中
	 */
	@Override
	public int addCusLtdiscountafterItem(CusLtdiscountafterItemEntity entity) {
		/**
		 * set ID
		 */
		entity.setItemId(UUID.randomUUID().toString());
		entity.setModifyDate(null);
		entity.setMaxAmount(entity.getMaxAmount() * NumberConstants.NUMBER_100);
		entity.setMinAmount(entity.getMinAmount() * NumberConstants.NUMBER_100);
		return this.getSqlSession().insert(NAMESPACE+"addCusLtdiscountafterItem", entity);
		
	}
	
	/**
	 * 查询操作（提供给接送货开发组）
	 */
	@Override
	public List<CusLtDiscountItemDto> queryCusLtdiscountafterByCondition(CusLtdiscountafterEntity entity) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryCusLtdiscountafterByCondition", entity);
		
	}

	/**
	 * 根据客户编码查询梯度基础资料的ID和CRM_ID
	 */
	@Override
	public List<CusLtDiscountItemDto> queryCusLtdiscountafterByCode(
			CusLtdiscountafterEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE+"queryCusLtdiscountafterByCode", entity);
	}

	/**
	 * 修改“梯度折基础资料”
	 * 根据 ID进行修改，这样保证修改的是同一条数据
	 */
	@Override
	public void updateCusLtdiscountafter(CusLtdiscountafterEntity entity) {
		entity.setEndTime(new Date());
		entity.setActive(FossConstants.INACTIVE);	
		entity.setModifyDate(new Date());
		this.getSqlSession().update(NAMESPACE+"updateCusLtdiscountafter", entity);
		
	}

	/**
	 * 修改“梯度折基础资料详情”
	 * 根据优惠方案ID DISCOUNT_CRM_ID 进行修改，这样保证此字段关联梯度折基础资料表中的CRM_ID，修改的数据是一致的
	 */
	@Override
	public void updateCusLtdiscountafterItem(CusLtdiscountafterItemEntity entity) {
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(new Date());
		this.getSqlSession().update(NAMESPACE+"updateCusLtdiscountafterItem", entity);
		
	}

}

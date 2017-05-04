package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressSortStationMappingDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSortStationMappingEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressSortStationMappingDao extends SqlSessionDaoSupport implements
		IExpressSortStationMappingDao {
	private static final String NAMESPACE ="foss.bse.bse-baseinfo.expressSortStationMapping.";
	/**
	 * 新增快递分拣目的站映射
	 * @param entity
	 * @return
	 */
	@Override
	public int addExpressSortStationMappingEntity(ExpressSortStationMappingEntity entity) {
			
		entity.setModifyUser(entity.getCreateUser());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateDate(new Date());
		entity.setVersionNo(new Date().getTime());
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESPACE+"addExpressSortStationMapping", entity);
	}
	/**
	 * 通过虚拟编码作废映射关系
	 * @param entity
	 * @return
	 */
	@Override
	public int deleteExpSortStationMappingByVirtualCode(ExpressSortStationMappingEntity entity) {
		Map<String, Object> map =new HashMap<String, Object>();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(new Date());
		entity.setVersionNo(new Date().getTime());
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE+"deleteExpSortStationMappingByVirtualCode", map);
	}
	/**
	 * 修改快递分拣目的站映射
	 * @param entity
	 * @return
	 */
	@Override
	public int updateExpSortStationMapping(
			ExpressSortStationMappingEntity entity) {
		if(null ==entity){
			return 0;
		}
		//作废原来的
		int result =this.deleteExpSortStationMappingByVirtualCode(entity);
		if(result>0){
			entity.setId(UUIDUtils.getUUID());
			entity.setVirtualCode(UUIDUtils.getUUID());
			entity.setCreateUser(entity.getModifyUser());
			entity.setCreateDate(new Date());
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			entity.setVersionNo(new Date().getTime());
			return this.addExpressSortStationMappingEntity(entity);
		}
		return 0;
	}
	/**
	 * 分页查询映射实体
	 * @param entity
	 * @param start
	 * @param offet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressSortStationMappingEntity> queryExpSortStationMappingList(
			ExpressSortStationMappingEntity entity, int start, int offet) {
		RowBounds rb =new RowBounds(start, offet);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryExpSortStationMappingList", entity, rb);
	}
	/**
	 * 查询数量
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCount(ExpressSortStationMappingEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryCount",entity);
	}
	/**
	 * 多条件查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressSortStationMappingEntity> queryMappingListByCondition(
			ExpressSortStationMappingEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryMappingListByCondition", entity);
	}

}

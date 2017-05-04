package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(实现IExpressPrintStarDao接口)
 * @author 187862-dujunhui
 * @date 2014-5-21 下午2:09:14
 * @since
 * @version
 */
public class ExpressPrintStarDao extends SqlSessionDaoSupport implements
		IExpressPrintStarDao {
	
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".expressPrintStar.";//foss.bse.bse-baseinfo.expressPrintStar.

	/** 
	 * <p>TODO(添加)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:14
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#addExpressPrintStar(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity)
	 */
	@Override
	public ExpressPrintStarEntity addExpressPrintStar(
			ExpressPrintStarEntity entity) {
		// TODO Auto-generated method stub
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setVirtualCode(entity.getId());
		entity.setCreateDate(now);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setModifyUser(entity.getCreateUser());
		entity.setActive(FossConstants.ACTIVE);
		entity.setVersion(now.getTime());
		int result =  getSqlSession().insert(NAMESPACE + "addExpressPrintStar", entity);
		return result > 0 ? entity : null;
	}

	/** 
	 * <p>TODO(删除)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:14
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#deleteExpressPrintStar(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity)
	 */
	@Override
	public ExpressPrintStarEntity deleteExpressPrintStar(
			ExpressPrintStarEntity entity) {
		// TODO Auto-generated method stub
		Date now = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);
		entity.setVersion(now.getTime());
		int result = getSqlSession().update(NAMESPACE + "deleteExpressPrintStar", entity); 
		return result > 0 ? entity : null;
	}

	/** 
	 * <p>TODO(更改)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:14
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#updateExpressPrintStar(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity)
	 */
	@Override
	public ExpressPrintStarEntity updateExpressPrintStar(
			ExpressPrintStarEntity entity) {
		// TODO Auto-generated method stub
		ExpressPrintStarEntity updateEntity = deleteExpressPrintStar(entity);
		if (updateEntity == null) {
		    return null;
		}
		updateEntity.setId(UUIDUtils.getUUID());
		updateEntity.setCreateDate(entity.getModifyDate());
		updateEntity.setVersion(entity.getModifyDate().getTime());
		updateEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
		updateEntity.setCreateUser(entity.getModifyUser());
		updateEntity.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().insert(NAMESPACE + "addExpressPrintStar", updateEntity);
		return result > 0 ? entity : null;
	}

	/** 
	 * <p>TODO(根据虚拟编号查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:15
	 * @param virtualCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#queryExpressPrintStarByVirtualCode(java.lang.String)
	 */
	@Override
	public ExpressPrintStarEntity queryExpressPrintStarByVirtualCode(
			String virtualCode) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String> ();
		map.put("active", FossConstants.ACTIVE);
		map.put("virtualCode", virtualCode);
		return (ExpressPrintStarEntity) getSqlSession().selectOne(NAMESPACE + "queryExpressPrintStarByVirtualCode", map);
	}

	/** 
	 * <p>TODO(根据指定条件查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:15
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#queryExpressPrintStarByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPrintStarEntity> queryExpressPrintStarByCondition(
			ExpressPrintStarEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		ExpressPrintStarEntity queryentity = entity == null ? new ExpressPrintStarEntity() : entity;
		queryentity.setActive(FossConstants.ACTIVE);
		return (List<ExpressPrintStarEntity>)getSqlSession().selectList(NAMESPACE + "queryExpressPrintStarListByCondition", entity, new RowBounds(start, limit));
	}

	/** 
	 * <p>TODO(根据指定条件查询的结果数)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:15
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#countExpressPrintStarByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity)
	 */
	@Override
	public long countExpressPrintStarByCondition(ExpressPrintStarEntity entity) {
		// TODO Auto-generated method stub
		entity.setActive(FossConstants.ACTIVE);
		return (Long)getSqlSession().selectOne(NAMESPACE + "countExpressPrintStarListByCondition", entity);
	}

	/** 
	 * <p>TODO(根据组织编号查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:15
	 * @param organizationCode
	 * @param goodsAreaType
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#queryExpressPrintStarListByOrganizationCode(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPrintStarEntity> queryExpressPrintStarListByOrganizationCode(
			String organizationCode, String goodsAreaType) {
		// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String> ();
			map.put("active", FossConstants.ACTIVE);
			map.put("organizationCode", organizationCode);
			map.put("goodsAreaType", goodsAreaType);
			return (List<ExpressPrintStarEntity>) getSqlSession().selectList(NAMESPACE + "queryExpressPrintStarListByOrganizationCode", map);
	}

	/** 
	 * <p>TODO(批量删除)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:15
	 * @param virtualCodes
	 * @param modifyUser
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#deleteExpressPrintStars(java.util.List, java.lang.String)
	 */
	@Override
	public int deleteExpressPrintStars(List<String> virtualCodes,
			String modifyUser) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Date now = new Date();
		map.put("active", FossConstants.ACTIVE);
		map.put("inactive", FossConstants.INACTIVE);
		map.put("modifyDate", now);
		map.put("modifyUser", modifyUser);
		map.put("version", now.getTime());
		map.put("virtualCodes", virtualCodes);
		return getSqlSession().update(NAMESPACE + "deleteExpressPrintStars", map);
	}

	/** 
	 * <p>TODO(根据外场编码查询)</p> 
	 * @author 187862
	 * @date 2014-5-21 下午2:09:15
	 * @param orgCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressPrintStarDao#queryExpressPrintStarByTransCenterCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressPrintStarEntity> queryExpressPrintStarByTransCenterCode(
			String organizationCode) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressPrintStarByTransCenterCode", organizationCode);
	}

}

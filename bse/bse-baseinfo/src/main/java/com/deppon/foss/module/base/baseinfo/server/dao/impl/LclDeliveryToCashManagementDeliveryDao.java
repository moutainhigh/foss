package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LclDeliveryToCashManagementDeliveryDao extends
		SqlSessionDaoSupport implements IlclDeliveryToCashManagementDeliveryDao {
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".lclDeliveryToCashManagementDelivery.";
    /**
     * 
     * <p>新增</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:55:18
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#addLclDeliveryToCashManagementDeliveryEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity)
     */
	@Override
	public LclDeliveryToCashManagementDeliveryEntity addLclDeliveryToCashManagementDeliveryEntity(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		// 请求合法性验证：
		if (null == entity) {
			return entity;
		}
		// 得到当前登录用户的信息
		UserEntity userEntity = FossUserContext.getCurrentUser();
		// 创建人编号
		entity.setCreateUserCode(userEntity.getEmployee().getEmpCode());
		// 创建人姓名
		entity.setCreateUser(userEntity.getEmployee().getEmpName());
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(now);
		entity.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().insert(
				NAMESPACE + "addLclDeliveryToCashManagementDeliveryEntity",
				entity);
		return result > 0 ? entity : null;
	}
   /**
    * 
    * <p>删除</p> 
    * @author 273311 
    * @date 2016-8-23 下午3:55:40
    * @param entity
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#deletelclDeliveryToCashManagementDelivery(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity)
    */
	@Override
	public LclDeliveryToCashManagementDeliveryEntity deletelclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		// 得到当前登录用户的信息
		UserEntity userEntity = FossUserContext.getCurrentUser();
		Date now = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);
		entity.setModifyUserCode(userEntity.getEmployee().getEmpCode());
		entity.setModifyUser(userEntity.getEmployee().getEmpName());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(
				NAMESPACE + "deletelclDeliveryToCashManagementDelivery", map);
		return result > 0 ? entity : null;
	}
   /**
    * 
    * <p>批量删除</p> 
    * @author 273311 
    * @date 2016-8-23 下午3:56:05
    * @param ids
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#deletelclDeliveryToCashManagementDeliveryMore(java.lang.String[])
    */
	@Override
	public LclDeliveryToCashManagementDeliveryEntity deletelclDeliveryToCashManagementDeliveryMore(
			String[] ids) {
		LclDeliveryToCashManagementDeliveryEntity entity = new LclDeliveryToCashManagementDeliveryEntity();
		// 处理删除时要更新的数据
		// 得到当前登录用户的信息
		UserEntity userEntity = FossUserContext.getCurrentUser();
		Date now = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);
		entity.setModifyUserCode(userEntity.getEmployee().getEmpCode());
		entity.setModifyUser(userEntity.getEmployee().getEmpName());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(
				NAMESPACE + "deletelclDeliveryToCashManagementDeliveryMore",
				map);
		return result > 0 ? entity : null;
	}
    /**
     * 
     * <p>更新</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:56:31
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#updatelclDeliveryToCashManagementDelivery(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity)
     */
	@Override
	public LclDeliveryToCashManagementDeliveryEntity updatelclDeliveryToCashManagementDelivery(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		// 得到当前登录用户的信息
		UserEntity userEntity = FossUserContext.getCurrentUser();
		// 创建人编号
		entity.setCreateUserCode(userEntity.getEmployee().getEmpCode());
		// 创建人姓名
		entity.setCreateUser(userEntity.getEmployee().getEmpName());
		Date now = new Date();
		entity.setCreateDate(now);
		// entity.setModifyDate(now);
		entity.setActive(FossConstants.ACTIVE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		// 只更新active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(
				NAMESPACE + "updatelclDeliveryToCashManagementDelivery", map);
		return result > NumberConstants.ZERO ? entity : null;
	}
     /**
      * 
      * <p>查询，分页</p> 
      * @author 273311 
      * @date 2016-8-23 下午3:56:49
      * @param entity
      * @param start
      * @param limit
      * @return 
      * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#queryLclDeliveryToCashManagementDeliveryExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity, int, int)
      */
	@SuppressWarnings("unchecked")
	@Override
	public List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryExactByEntity(
			LclDeliveryToCashManagementDeliveryEntity entity, int start,
			int limit) {
		LclDeliveryToCashManagementDeliveryEntity queryEntity;
		if (null == entity) {
			queryEntity = new LclDeliveryToCashManagementDeliveryEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession()
				.selectList(
						NAMESPACE
								+ "queryLclDeliveryToCashManagementDeliveryExactByEntity",
						queryEntity, rowBounds);
	}
    /**
     * 
     * <p>查询，条数</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:57:40
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#queryLclDeliveryToCashManagementDeliveryExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementDeliveryEntity)
     */
	@Override
	public long queryLclDeliveryToCashManagementDeliveryExactByEntityCount(
			LclDeliveryToCashManagementDeliveryEntity entity) {
		LclDeliveryToCashManagementDeliveryEntity queryEntity;
		if (null == entity) {
			queryEntity = new LclDeliveryToCashManagementDeliveryEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession()
				.selectOne(
						NAMESPACE
								+ "queryLclDeliveryToCashManagementDeliveryExactByEntityCount",
						queryEntity);
	}
    /**
     * 
     * <p>code 精确查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:58:24
     * @param code
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#queryLclDeliveryToCashManagementDeliveryByOrgCode(java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<LclDeliveryToCashManagementDeliveryEntity> queryLclDeliveryToCashManagementDeliveryByOrgCode(
			String code) {
		// 构造查询条件：
		LclDeliveryToCashManagementDeliveryEntity entity = new LclDeliveryToCashManagementDeliveryEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setOrgCode(code);
		List<LclDeliveryToCashManagementDeliveryEntity> entitys = this
				.getSqlSession()
				.selectList(
						NAMESPACE
								+ "queryLclDeliveryToCashManagementDeliveryByOrgCode",
						entity);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys;
		}
	}
    /**
     * 
     * <p>code id 精确查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午3:59:11
     * @param code
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IlclDeliveryToCashManagementDeliveryDao#queryLclDeliveryToCashManagementDeliveryByIdCode(java.lang.String, java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public LclDeliveryToCashManagementDeliveryEntity queryLclDeliveryToCashManagementDeliveryByIdCode(
			String code, String id) {
		// 构造查询条件：
		LclDeliveryToCashManagementDeliveryEntity entity = new LclDeliveryToCashManagementDeliveryEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setOrgCode(code);
		entity.setId(id);
		List<LclDeliveryToCashManagementDeliveryEntity> entitys = this
				.getSqlSession()
				.selectList(
						NAMESPACE
								+ "queryLclDeliveryToCashManagementDeliveryByIdCode",
						entity);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys.get(0);
		}
	}

}
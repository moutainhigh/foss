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
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LclDeliveryToCashManagementUnloadingDao extends
		SqlSessionDaoSupport implements
		ILclDeliveryToCashManagementUnloadingDao {
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
			+ ".lclDeliveryToCashManagementUnloading.";
    /**
     * 
     * <p>规定卸出时间管理   新增</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:24:46
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#addlclDeliveryToCashManagementUnloading(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity)
     */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity addlclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity) {
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
				NAMESPACE + "addlclDeliveryToCashManagementUnloading", entity);
		return result > 0 ? entity : null;

	}
    /**
     * 
     * <p>规定卸出时间管理   删除<</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:25:19
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#deletelclDeliveryToCashManagementUnloading(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity)
     */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity deletelclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity) {
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
				NAMESPACE + "deletelclDeliveryToCashManagementUnloading", map);
		return result > 0 ? entity : null;
	}
    /**
     * 
     * <p>规定卸出时间管理   批量删除</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:25:35
     * @param ids
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#deletelclDeliveryToCashManagementUnloadingMore(java.lang.String[])
     */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity deletelclDeliveryToCashManagementUnloadingMore(
			String[] ids) {
		LclDeliveryToCashManagementUnloadingEntity entity = new LclDeliveryToCashManagementUnloadingEntity();
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
				NAMESPACE + "deletelclDeliveryToCashManagementUnloadingMore",
				map);
		return result > 0 ? entity : null;
	}
   /**
    * 
    * <p>规定卸出时间管理   更新</p> 
    * @author 273311 
    * @date 2016-8-23 下午4:25:54
    * @param entity
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#updatelclDeliveryToCashManagementUnloading(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity)
    */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity updatelclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity) {
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
				NAMESPACE + "updatelclDeliveryToCashManagementUnloading", map);
		return result > NumberConstants.ZERO ? entity : null;
	}
    /**
     * 
     * <p>根据部门编码查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:26:15
     * @param startOrgCode
     * @param reachOrgCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#queryLclDeliveryToCashManagementUnloadingEntitytByCode(java.lang.String, java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntitytByCode(
			String startOrgCode, String reachOrgCode) {

		// 构造查询条件：
		LclDeliveryToCashManagementUnloadingEntity entity = new LclDeliveryToCashManagementUnloadingEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setStartOrgCode(startOrgCode);
		entity.setReachOrgCode(reachOrgCode);
		List<LclDeliveryToCashManagementUnloadingEntity> entitys = this
				.getSqlSession()
				.selectList(
						NAMESPACE
								+ "queryLclDeliveryToCashManagementUnloadingEntitytByCode",
						entity);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys;
		}

	}
    /**
     * 
     * <p>ID精确查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:27:00
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#queryLclDeliveryToCashManagementUnloadingEntitytByIdCode(java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public LclDeliveryToCashManagementUnloadingEntity queryLclDeliveryToCashManagementUnloadingEntitytByIdCode(
			String id) {

		// 构造查询条件：
		LclDeliveryToCashManagementUnloadingEntity entity = new LclDeliveryToCashManagementUnloadingEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setId(id);
		List<LclDeliveryToCashManagementUnloadingEntity> entitys = this
				.getSqlSession()
				.selectList(
						NAMESPACE
								+ "queryLclDeliveryToCashManagementUnloadingEntitytByIdCode",
						entity);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys.get(0);
		}
	}
    /**
     *
     * <p> 规定卸出时间管理   查询，分页</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:27:20
     * @param entity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#queryLclDeliveryToCashManagementUnloadingEntityExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity, int, int)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntityExactByEntity(
			LclDeliveryToCashManagementUnloadingEntity entity, int start,
			int limit) {
		LclDeliveryToCashManagementUnloadingEntity queryEntity;
		if (entity == null) {
			queryEntity = new LclDeliveryToCashManagementUnloadingEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession()
				.selectList(
						NAMESPACE
								+ "queryLclDeliveryToCashManagementUnloadingEntityExactByEntity",
						queryEntity, rowBounds);
	}
    /**
     * 
     * <p> 规定卸出时间管理  查询条数 </p> 
     * @author 273311 
     * @date 2016-8-23 下午4:27:53
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao#queryLclDeliveryToCashManagementUnloadingExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity)
     */
	@Override
	public long queryLclDeliveryToCashManagementUnloadingExactByEntityCount(
			LclDeliveryToCashManagementUnloadingEntity entity) {
		LclDeliveryToCashManagementUnloadingEntity queryEntity;
		if (entity == null) {
			queryEntity = new LclDeliveryToCashManagementUnloadingEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession()
				.selectOne(
						NAMESPACE
								+ "queryLclDeliveryToCashManagementUnloadingExactByEntityCount",
						queryEntity);
	}

}

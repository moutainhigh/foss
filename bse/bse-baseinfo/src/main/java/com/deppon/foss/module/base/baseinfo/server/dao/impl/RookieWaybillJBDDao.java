package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRookieWaybillJBDDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RookieWaybillJBDEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TaobaoDepponDistrictMapEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class RookieWaybillJBDDao extends SqlSessionDaoSupport implements IRookieWaybillJBDDao{
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".rookieWaybillJBD.";
	/**
	 * <p> 新增数据</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long addRookieWaybillJBD(RookieWaybillJBDEntity entity) {
		// TODO Auto-generated method stub
		//将必要的数据放进实体，存到数据库中
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(now);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setVersionNo(now.getTime());
		entity.setActive(FossConstants.ACTIVE);
		return getSqlSession().insert(NAMESPACE + "addRookieWaybillJBD",entity);
	}
	/**
	 * <p> 分页查询</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RookieWaybillJBDEntity> queryRookieWaybillJBDByCondition(
			RookieWaybillJBDEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectList(NAMESPACE + "queryRookieWaybillJBDByCondition", entity, new RowBounds(start, limit));
	}
	/**
	 * <p> 查询总数</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long queryCount(RookieWaybillJBDEntity entity) {
		// TODO Auto-generated method stub
		return (Long)getSqlSession().selectOne(NAMESPACE+"queryCount",entity);
	}
	/**
	 * <p> 删除信息</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@Override
	public long deleteRookieWaybillJBD(List<String> ids) {
		// TODO Auto-generated method stub
		Date now=new Date();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("modifyDate", now);
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		map.put("versionNo",now.getTime());
		return getSqlSession().update(NAMESPACE + "deleteRookieWaybillJBD", map);
	}

	/**
	 * <p> 提供给跟新和添加时的查重复</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RookieWaybillJBDEntity> queryRookieWaybillJBDByAddress(
			RookieWaybillJBDEntity entity) {
		return (List<RookieWaybillJBDEntity>)getSqlSession().selectList(NAMESPACE + "queryRookieWaybillJBDByAddress", entity);
		
	}
	/**
	 * <p> 通过ID查询</p> 
	 * @author 232608 
	 * @date 2015-6-18 下午2:20:30
	 * @return 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RookieWaybillJBDEntity> queryRookieWaybillJBDByIds(
			List<String> ids) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		return getSqlSession().selectList(NAMESPACE + "queryRookieWaybillJBDByIds", map);
	}
	@Override
	public TaobaoDepponDistrictMapEntity queryDistrictIfEqual(
			TaobaoDepponDistrictMapEntity entity) {
		// TODO Auto-generated method stub
		return (TaobaoDepponDistrictMapEntity)getSqlSession().selectOne(NAMESPACE+"queryDistrictIfEqual",entity);
	}
	
	 
}

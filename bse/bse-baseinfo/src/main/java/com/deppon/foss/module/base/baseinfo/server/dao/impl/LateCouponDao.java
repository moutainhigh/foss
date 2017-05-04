package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILateCouponDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;


/**
 *  晚到补差价Dao
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-7-30 上午9:13:12,content:TODO </p>
 * @author 232607 
 * @date 2015-7-30 上午9:13:12
 * @since
 * @version
 */
public class LateCouponDao extends SqlSessionDaoSupport implements ILateCouponDao {
	
	private String namespace = "foss.bse.bse-baseinfo.lateCoupon.";
	
	/** 
	 * 分页查询晚到补差价方案
	 * @author 232607 
	 * @date 2015-7-30 上午9:35:57
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILateCouponDao#queryLateCouponByCodition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LateCouponEntity> queryLateCouponByCodition(LateCouponEntity entity, int start, int limit) {
		return getSqlSession().selectList(namespace+"queryLateCouponByCodition", entity, new RowBounds(start,limit));	
	}

	@Override
	public Long countLateCouponByCodition(LateCouponEntity entity) {
		return (Long) getSqlSession().selectOne(namespace+"countLateCouponByCodition", entity);
	}
	
	@Override
	public LateCouponEntity addLateCoupon(LateCouponEntity entity) {		
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		int result=getSqlSession().insert(namespace + "addLateCoupon",entity);
		if(result>0){
			return entity;
		}else{
			return null;
		}
	}
	
	@Override
	public long deleteLateCoupon(List<String> lateCouponIds) {
		//建一个Map，将ids、修改时间、修改人工号作为一个Map集合传给Mapper
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", lateCouponIds);
		map.put("modifyDate", new Date());
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		return getSqlSession().update(namespace + "deleteLateCoupon", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LateCouponEntity> queryRepeat(LateCouponEntity entity) {
		return getSqlSession().selectList(namespace + "queryRepeat", entity);
	}
	
	
	
	
	
	
	
	
	/** 
	 *  查询出当前唯一激活有效方案
	 * @author 232607 
	 * @date 2015-7-30 上午9:15:11
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILateCouponDao#queryActivationScheme()
	 */
	@SuppressWarnings("unchecked")
	public List<LateCouponEntity> queryActivationScheme( ){
		return getSqlSession().selectList(namespace+"selectLateCouponOnly");
	}
	
	/** 
	 * 查询多个大区下属的所有营业部、集中开单组，返回code集合
	 * @author 232607 
	 * @date 2015-7-30 上午9:15:38
	 * @param list
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILateCouponDao#querySubSalesDept(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<String> querySubSalesDept(List<String> list) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", list);
		return getSqlSession().selectList(namespace+"querySubSalesDept",map);
	}

	@Override
	public void activateLateCoupon(String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("modifyDate", new Date());
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		getSqlSession().update(namespace + "activateLateCoupon", map);
	}

	@Override
	public void stopLateCoupon(String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("modifyDate", new Date());
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		getSqlSession().update(namespace + "stopLateCoupon", map);
	}

	@Override
	public String getMaxCode() {
		return (String) getSqlSession().selectOne(namespace+"getMaxCode");
	}

	@Override
	public LateCouponEntity queryLateCouponById(String id) {
		return (LateCouponEntity) getSqlSession().selectOne(namespace+"findById",id);
	}

	/** 
	 *  查询是否存在有效方案
	 * @author 232607 
	 * @date 2015-7-30 上午9:15:11
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILateCouponDao#queryActivationScheme()
	 */
	@Override
	public List<LateCouponEntity> findActivationScheme(LateCouponEntity entity) {
		return getSqlSession().selectList(namespace+"findActivationScheme",entity);
	}









	
	

}
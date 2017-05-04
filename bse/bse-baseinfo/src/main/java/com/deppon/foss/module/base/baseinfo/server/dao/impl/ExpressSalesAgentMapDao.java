package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressSalesAgentMapDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;

/**
 * 虚拟营业部快递代理网点映射的Dao层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-5-21 下午4:14:06,content:TODO </p>
 * @author 232607 
 * @date 2015-5-21 下午4:14:06
 * @since
 * @version
 */
public class ExpressSalesAgentMapDao extends SqlSessionDaoSupport implements IExpressSalesAgentMapDao {
	/**
	 *  命名空间（foss.bse.bse-baseinfo.expressSalesAgentMap.）
	 */
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".expressSalesAgentMap.";
	/**
	 * <p>通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午4:16:24
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	@Override
	@SuppressWarnings("unchecked")
    public List<ExpressSalesAgentMapEntity> queryExpressSalesAgentMapListByCondition(ExpressSalesAgentMapEntity entity, int start, int limit) {
		return getSqlSession().selectList(NAMESPACE + "queryExpressSalesAgentMapListByCondition", entity, new RowBounds(start, limit));
    }
	/** 
	 * <p>通过所有条件进行分页查询的查询总数</p> 
	 * @author 232607 
	 * @date 2015-5-22 上午10:00:00
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressSalesAgentMapDao#queryExpressSalesAgentMapListByConditionCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity)
	 */
	@Override
	public long queryExpressSalesAgentMapListByConditionCount(ExpressSalesAgentMapEntity entity) {
		return (Long)getSqlSession().selectOne(NAMESPACE+"queryExpressSalesAgentMapListByConditionCount",entity);
	}
	/**
	 * <p>批量删除映射关系</p> 
	 * @author 232607 
	 * @date 2015-5-22 上午10:03:23
	 * @param ids
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
    public long deleteExpressSalesAgentMap(List<String> ids) {
		//建一个Map，将ids、修改时间、修改人工号作为一个Map集合传给Mapper
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("modifyDate", new Date());
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		return getSqlSession().update(NAMESPACE + "deleteExpressSalesAgentMap", map);
    }
	/**
	 * <p>新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-5-22 上午10:03:39
	 * @param entity
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public ExpressSalesAgentMapEntity addExpressSalesAgentMap(ExpressSalesAgentMapEntity entity) {
		//生成一个ID并存入实体，以及创建时间、修改时间、创建人工号、修改人工号存入实体当中
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		getSqlSession().insert(NAMESPACE + "addExpressSalesAgentMap",entity);
		return entity;
    }
	/** 
	 * <p>用于查重</p> 
	 * @author 232607 
	 * @date 2015-5-22 下午4:15:48
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressSalesAgentMapDao#queryRepeat(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity)
	 */
	@SuppressWarnings("unchecked")
	public List<ExpressSalesAgentMapEntity> queryRepeat(ExpressSalesAgentMapEntity entity){
		return getSqlSession().selectList(NAMESPACE + "queryRepeat", entity);
	}
	/** 
	 * <p>作为接口给中转调用，
	 *        查询参数为：虚拟营业部code集合，
	 *        返回映射关系实体集合</p> 
	 * @author 232607 
	 * @date 2015-5-29 上午10:38:56
	 * @param codes
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressSalesAgentMapDao#queryExpressSalesAgentMapBySalesCodes(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<ExpressSalesAgentMapEntity> queryExpressSalesAgentMapBySalesCodes(List<String> codes){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("codes", codes);
		return getSqlSession().selectList(NAMESPACE + "queryExpressSalesAgentMapBySalesCodes", map);
	}
}

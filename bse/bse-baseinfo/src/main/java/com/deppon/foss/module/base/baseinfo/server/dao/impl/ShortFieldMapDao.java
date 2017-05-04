package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IShortFieldMapDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;

/**
 *  短距离外场映射的Dao层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-3-26 下午5:31:23,content: </p>
 * @author 232607 
 * @date 2015-3-26 下午5:31:23
 * @since
 * @version
 */
public class ShortFieldMapDao extends SqlSessionDaoSupport implements IShortFieldMapDao {
	/**
	 *  命名空间（foss.bse.bse-baseinfo.shortFieldMap.）
	 */
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".shortFieldMap.";
	/** 
	 * <p> 通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午5:35:33
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IShortFieldMapDao#queryShortFieldMapListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
    public List<ShortFieldMapEntity> queryShortFieldMapListByCondition(ShortFieldMapEntity entity, int start, int limit) {
		return getSqlSession().selectList(NAMESPACE + "queryShortFieldMapListByCondition", entity, new RowBounds(start, limit));
    }
	/** 
	 * <p> 通过所有条件进行分页查询的查询总数</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:35:48
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IShortFieldMapDao#queryShortFieldMapListByConditionCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity)
	 */
	public long queryShortFieldMapListByConditionCount(ShortFieldMapEntity entity) {
		return (Long)getSqlSession().selectOne(NAMESPACE+"queryShortFieldMapListByConditionCount",entity);
	}
	/** 
	 * <p> 批量删除映射关系</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午10:19:00
	 * @param ids
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IShortFieldMapDao#deleteShortFieldMap(java.util.List)
	 */
	@SuppressWarnings("unchecked")
    public long deleteShortFieldMap(List<String> ids) {
		//建一个Map，将ids、修改时间、修改人工号作为一个Map集合传给Mapper
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ids", ids);
		map.put("modifyDate", new Date());
		map.put("modifyUser", FossUserContext.getCurrentInfo().getEmpCode());
		return getSqlSession().update(NAMESPACE + "deleteShortFieldMap", map);
    }
	/** 
	 * <p> 新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午10:21:17
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IShortFieldMapDao#addShortFieldMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity)
	 */
	@SuppressWarnings("unchecked")
	public ShortFieldMapEntity addShortFieldMap(ShortFieldMapEntity entity) {
		//生成一个ID并存入实体，以及创建时间、修改时间、创建人工号、修改人工号存入实体当中
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		getSqlSession().insert(NAMESPACE + "addShortFieldMap",entity);
		return entity;
    }

}

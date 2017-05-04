package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IShortFieldMapDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IShortFieldMapService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ShortFieldMapException;
import com.deppon.foss.base.util.define.NumberConstants;
/**
 *  短距离外场映射的service层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-3-26 下午5:10:49,content: </p>
 * @author 232607 
 * @date 2015-3-26 下午5:10:49
 * @since
 * @version
 */
public class ShortFieldMapService implements IShortFieldMapService{
	/**
	 *  实现本模块的Dao
	 */
	private IShortFieldMapDao shortFieldMapDao;
	/**
	 * <p> Dao的set方法用来注入Dao的Bean</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:52:26
	 * @param shortFieldMapDao
	 * @see
	 */
	public void setShortFieldMapDao(IShortFieldMapDao shortFieldMapDao) {
		this.shortFieldMapDao = shortFieldMapDao;
	}
	/** 
	 * <p> 通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:53:50
	 * @param entity 查询条件实体
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IShortFieldMapService#queryShortFieldMapListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity, int, int)
	 */
	@Override
	public List<ShortFieldMapEntity> queryShortFieldMapListByCondition(ShortFieldMapEntity entity, int start, int limit) {
		//条件的非空判断，如果是空的，那么就新建一个实体
		if (entity == null) {
			entity=new ShortFieldMapEntity();
		}
		//调用Dao层的方法，通过所有条件进行分页查询
		return shortFieldMapDao.queryShortFieldMapListByCondition(entity, start, limit);
	}
	/** 
	 * <p> 通过所有条件进行分页查询的查询总数</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:35:20
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IShortFieldMapService#queryShortFieldMapListByConditionCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity)
	 */
	@Override
	public long queryShortFieldMapListByConditionCount(ShortFieldMapEntity entity) {
		return shortFieldMapDao.queryShortFieldMapListByConditionCount(entity);
	}
	/** 
	 * <p> 批量作废（非物理删除，是将数据的状态改为不可用）</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午4:58:23
	 * @param ids
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IShortFieldMapService#deleteShortFieldMap(java.util.List)
	 */
	@Override
	public long deleteShortFieldMap(List<String> ids) {
		//条件的非空判断，为空则抛出异常
		if (ids == null) {
			throw new ShortFieldMapException("传入参数为空");
		}
		//调用Dao层的方法，批量作废
		return shortFieldMapDao.deleteShortFieldMap(ids);
	}
	/** 
	 * <p> 新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午5:06:02
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IShortFieldMapService#addShortFieldMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity)
	 */
	@Override
	public ShortFieldMapEntity addShortFieldMap(ShortFieldMapEntity entity) {
		//条件的非空判断，为空则抛出异常
		if (entity == null) {
			throw new ShortFieldMapException("传入参数为空");
		}
		//调用Dao层的查询重复的方法，返回一个集合
		List<ShortFieldMapEntity> entitys=shortFieldMapDao.queryShortFieldMapListByCondition(entity, 0, NumberConstants.NUMBER_20);
		//如果集合不是空的，则抛出异常
		if(entitys.size()!=0){
			throw new ShortFieldMapException("已经存在"+entitys.get(0).getName()+"与"+entitys.get(0).getShortFieldName()+"的映射关系！请勿再添加");
		}
		//调用Dao层的方法
		return shortFieldMapDao.addShortFieldMap(entity);
	}
	/** 
	 * <p> 修改映射关系</p> 
	 * @author 232607 
	 * @date 2015-3-26 下午5:08:02
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IShortFieldMapService#updateShortFieldMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity)
	 */
	@Override
	public ShortFieldMapEntity updateShortFieldMap(ShortFieldMapEntity entity) {
		//条件的非空判断，为空则抛出异常
		if (entity == null) {
			throw new ShortFieldMapException("传入参数为空");
		}
		//调用Dao层的查询重复的方法，返回一个集合
		List<ShortFieldMapEntity> entitys=shortFieldMapDao.queryShortFieldMapListByCondition(entity, 0, NumberConstants.NUMBER_20);
		//如果集合不是空的，则抛出异常
		if(entitys.size()!=0){
			throw new ShortFieldMapException("已经存在"+entitys.get(0).getName()+"与"+entitys.get(0).getShortFieldName()+"的映射关系！请勿再添加");
		}
		//新建一个字符串集合，放入前台传过来的ID，作废原数据
		List<String> ids=new ArrayList<String>();
		ids.add(entity.getId());
		shortFieldMapDao.deleteShortFieldMap(ids);
		//调用Dao层的方法，新增映射关系
		return shortFieldMapDao.addShortFieldMap(entity);
	}
	
}

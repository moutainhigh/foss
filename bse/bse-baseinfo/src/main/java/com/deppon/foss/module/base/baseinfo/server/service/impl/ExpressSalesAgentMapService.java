package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressSalesAgentMapDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSalesAgentMapService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressSalesAgentMapException;



/**
 *  虚拟营业部快递代理网点映射的service层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-5-21 下午3:17:16,content:TODO </p>
 * @author 232607 
 * @date 2015-5-21 下午3:17:16
 * @since
 * @version
 */
public class ExpressSalesAgentMapService implements IExpressSalesAgentMapService{
	/**
	 *  实现本模块的Dao
	 */
	private IExpressSalesAgentMapDao expressSalesAgentMapDao;
	/**
	 * <p>Dao的set方法用来注入Dao的Bean</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午3:19:27
	 * @param expressSalesAgentMapDao
	 * @see
	 */
	public void setExpressSalesAgentMapDao(
			IExpressSalesAgentMapDao expressSalesAgentMapDao) {
		this.expressSalesAgentMapDao = expressSalesAgentMapDao;
	}
	/**
	 * <p>通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午3:22:41
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	@Override
	public List<ExpressSalesAgentMapEntity> queryExpressSalesAgentMapListByCondition(ExpressSalesAgentMapEntity entity, int start, int limit) {
		//条件的非空判断
		if (null == entity) {
			//抛出异常：传入参数为空
			throw new ExpressSalesAgentMapException(ExpressSalesAgentMapException.EXPRESSSALESAGENTMAP_PARMS_NULL);
		}
		//调用Dao层的方法，通过所有条件进行分页查询
		return expressSalesAgentMapDao.queryExpressSalesAgentMapListByCondition(entity, start, limit);
	}
	/** 
	 * <p>通过所有条件进行分页查询的查询总数</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午5:06:44
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSalesAgentMapService#queryExpressSalesAgentMapListByConditionCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity)
	 */
	@Override
	public long queryExpressSalesAgentMapListByConditionCount(ExpressSalesAgentMapEntity entity) {
		return expressSalesAgentMapDao.queryExpressSalesAgentMapListByConditionCount(entity);
	}
	/** 
	 * <p> 批量作废（非物理删除，是将数据的状态改为不可用）</p> 
	 * @author 232607 
	 * @date 2015-5-22 下午4:58:23
	 * @param ids
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSalesAgentMapService#deleteExpressSalesAgentMap(java.util.List)
	 */
	@Override
	public long deleteExpressSalesAgentMap(List<String> ids) {
		//条件的非空判断
		if (null == ids) {
			//抛出异常：传入参数为空
			throw new ExpressSalesAgentMapException(ExpressSalesAgentMapException.EXPRESSSALESAGENTMAP_PARMS_NULL);
		}
		//调用Dao层的方法，批量作废
		return expressSalesAgentMapDao.deleteExpressSalesAgentMap(ids);
	}
	/** 
	 * <p> 新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-5-22 下午5:06:02
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSalesAgentMapService#addExpressSalesAgentMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity)
	 */
	@Override
	public ExpressSalesAgentMapEntity addExpressSalesAgentMap(ExpressSalesAgentMapEntity entity) {
		//条件的非空判断
		if (null == entity) {
			//抛出异常：传入参数为空
			throw new ExpressSalesAgentMapException(ExpressSalesAgentMapException.EXPRESSSALESAGENTMAP_PARMS_NULL);
		}
		//查重，重复则抛出异常，不重复则继续
		queryRepeat(entity);
		//调用Dao层的新增方法
		return expressSalesAgentMapDao.addExpressSalesAgentMap(entity);
	}
	/** 
	 * <p>修改映射关系</p> 
	 * @author 232607 
	 * @date 2015-5-22 上午10:58:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSalesAgentMapService#updateExpressSalesAgentMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity)
	 */
	@Override
	public ExpressSalesAgentMapEntity updateExpressSalesAgentMap(ExpressSalesAgentMapEntity entity) {
		//条件的非空判断
		if (null == entity) {
			//抛出异常：传入参数为空
			throw new ExpressSalesAgentMapException(ExpressSalesAgentMapException.EXPRESSSALESAGENTMAP_PARMS_NULL);
		}
		//查重，重复则抛出异常，不重复则继续
		queryRepeat(entity);
		//新建一个字符串集合，放入实体里的ID，作废原数据
		List<String> ids=new ArrayList<String>();
		ids.add(entity.getId());
		expressSalesAgentMapDao.deleteExpressSalesAgentMap(ids);
		//调用Dao层的新增方法
		return expressSalesAgentMapDao.addExpressSalesAgentMap(entity);
	}
	/**
	 * <p>查重，只要存在相同的虚拟营业部就算重复，重复则抛出异常</p>
	 * @author 232607 
	 * @date 2015-5-22 下午2:18:47
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	public void queryRepeat(ExpressSalesAgentMapEntity entity){
		//调用Dao层的查询重复的方法，返回一个集合
		List<ExpressSalesAgentMapEntity> list=expressSalesAgentMapDao.queryRepeat(entity);
		//如果集合不是空的，代表存在重复
		if(list.size()!=0){
			//抛出异常：已经存在相同的虚拟营业部，请勿重复添加
			throw new ExpressSalesAgentMapException(ExpressSalesAgentMapException.EXPRESSSALESAGENTMAP_SALES_REPEAT);
		}
	}
	//作为接口给中转调用，查询参数为：虚拟营业部code集合，返回参数为：映射关系的map集合，其中key为虚拟营业部code，value为快递代理网点code
	
	/** 
	 * <p>作为接口给中转调用，
	 *        查询参数为：虚拟营业部code集合，
	 *        返回参数为：映射关系map，其中key为虚拟营业部code，value为快递代理网点code</p> 
	 * @author 232607 
	 * @date 2015-5-28 下午4:22:11
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSalesAgentMapService#queryExpressSalesAgentMapByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity)
	 */
	@Override
	public Map<String,String> queryExpressSalesAgentMapBySalesCodes(List<String> codes) {
		//条件的非空判断
		if (CollectionUtils.isEmpty(codes)) {
			//抛出异常：传入参数为空
			throw new ExpressSalesAgentMapException(ExpressSalesAgentMapException.EXPRESSSALESAGENTMAP_PARMS_NULL);
		}
		//调用Dao层的方法查询，返回实体集合
		List<ExpressSalesAgentMapEntity> entitys=expressSalesAgentMapDao.queryExpressSalesAgentMapBySalesCodes(codes);
		//新建一个map
		Map<String,String> map=new HashMap<String,String>();
		//遍历实体集合，取出实体中的虚拟营业部code作为key，快递代理网点code作为value，存入map
		for(ExpressSalesAgentMapEntity entity : entitys){
			map.put(entity.getExpressSalesDeptCode() , entity.getExpressAgentDeptCode());	
		}
		//返回map
		return map; 		
	}
}

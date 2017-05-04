package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * @title: AutoAddCodeDaoImpl 
 * @description: 自动补码Dao层 数据接口实现.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 09:22:38
 */
public class AutoAddCodeDao extends iBatis3DaoImpl implements IAutoAddCodeDao {
	/** 命名空间 com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntiy */
	private static final String NAME_SPACE = "com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity";
	/**
	* 
	* @MethodName: insert 
	* @description: insert方法
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 09:22:38
	* @param entity void
	*/
	@Override
	public void insert(AutoAddCodeEntity entity) {
		getSqlSession().insert(NAME_SPACE+".insert", entity);
	}

	/**
	* 
	* @MethodName: deleteByPrimaryKey 
	* @description: 根据主键移除
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 09:22:38
	* @param entity void
	*/
	@Override
	public void deleteEntityByPrimaryKey(AutoAddCodeEntity entity) {
		getSqlSession().delete(NAME_SPACE+".deleteEntityByPrimaryKey", entity);
	}
	
	/**
	* 
	* @MethodName: updateByPrimaryKey 
	* @description: 根据主键更新
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 09:22:38
	* @param entity void
	*/
	@Override
	public void updateByPrimaryKey(AutoAddCodeEntity entity) {
		getSqlSession().update(NAME_SPACE+".updateByPrimaryKey", entity);
	}

	/**
	* 
	* @MethodName: expandByPrimaryKey 
	* @description: 根据主键查询
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 09:22:38
	* @param entity
	* @return AutoAddCodeEntiy
	*/
	@Override
	public AutoAddCodeEntity expandByPrimaryKey(AutoAddCodeEntity entity) {
		return (AutoAddCodeEntity) getSqlSession().selectOne(NAME_SPACE+".selectByPrimaryKey", entity);
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeDao#updateAndGetJobId(java.lang.String, int)  
	 */
	@Override
	public int updateAndGetJobId(String jobid, int limit) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", jobid);
		map.put("limit", limit);
		getSqlSession().update(NAME_SPACE+".updateAndGetJobIdAuto", map);
		return FossConstants.SUCCESS;
	}
	
	
	/**
	* @description 根据jobId值 初始化jobid
	* @param jobId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年6月25日 上午10:53:25
	*/
	@Override
	public int resetDataByJobId(String jobId) {
		return getSqlSession().update(NAME_SPACE+".resetDataByJobId", jobId);
	}
	
	/**
	* @description 根据id值 初始化jobid
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月29日 上午9:42:43
	*/
	@Override
	public int resetDataById(String id) {
		return getSqlSession().update(NAME_SPACE+".resetDataById", id);
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeDao#queryAutoAddCodeEntityByJodId(java.lang.String)  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoAddCodeEntity> queryAutoAddCodeEntityByJodId(String jobId) {
		return this.getSqlSession().selectList(NAME_SPACE+".queryAutoAddCodeEntityByJodId", jobId);
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeDao#resetData()  
	 */
	@Override
	public void resetData() {
		this.getSqlSession().update(NAME_SPACE+".resetData");
	}

	/**
	* 
	* @description: 查询30分钟之前的job不为空的数据
	* @author: 140022-foss-songjie 
	* @date: 2015-10-24 09:22:38
	* @param entity
	* @return AutoAddCodeEntiy
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoAddCodeEntity> queryListJobIdForReset() {
		return this.getSqlSession().selectList(NAME_SPACE+".queryListJobIdForReset");
	}
	
	
}
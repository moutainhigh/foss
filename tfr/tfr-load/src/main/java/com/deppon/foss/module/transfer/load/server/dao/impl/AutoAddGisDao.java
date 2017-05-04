package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddGisDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddGisEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @title: AutoAddCodeDaoImpl 
 * @description: 自动补码Dao层 数据接口实现.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 09:22:38
 */
public class AutoAddGisDao extends iBatis3DaoImpl implements IAutoAddGisDao {
	/** 命名空间 com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntiy */
	private static final String NAME_SPACE = "com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddGisEntity";
	/**
	* 
	* @MethodName: insert 
	* @description: insert方法
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 09:22:38
	* @param entity void
	*/
	@Override
	public int insert(AutoAddGisEntity entity) {
		String id = UUIDUtils.getUUID();
		entity.setTbid(id);
		entity.setJobId("N/A");
		return getSqlSession().insert(NAME_SPACE+".insertGis", entity);
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
	public void deleteEntityByPrimaryKey(AutoAddGisEntity entity) {
		getSqlSession().delete(NAME_SPACE+".deleteGisByPrimaryKey", entity);
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
	public AutoAddGisEntity queryByPrimaryKey(AutoAddGisEntity entity) {
		return (AutoAddGisEntity) getSqlSession().selectOne(NAME_SPACE+".selectByGisPrimaryKey", entity);
	}
	
	
	/**
	* @description 根据autoaddCode的主键id查询对象
	* @param autoAddId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月6日 上午10:22:06
	*/
	@Override
	public AutoAddGisEntity queryByAutoAddId(String autoAddId) {
		@SuppressWarnings("unchecked")
		List<AutoAddGisEntity> backList = getSqlSession().selectList(NAME_SPACE+".queryByAutoAddId", autoAddId);
		if(backList!=null && backList.size() >0 ){
			return backList.get(0);
		}else {
			return null;
		}
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeDao#updateAndGetJobId(java.lang.String, int)  
	 */
	@Override
	public int updateAndGetJobId(String jobid, int limit) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", jobid);
		map.put("limit", limit);
		getSqlSession().update(NAME_SPACE+".updateGisJobIdAuto", map);
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
		return getSqlSession().update(NAME_SPACE+".resetGisDataByJobId", jobId);
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
		return getSqlSession().update(NAME_SPACE+".resetGisDataById", id);
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeDao#queryAutoAddCodeEntityByJodId(java.lang.String)  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoAddGisEntity> queryAutoAddCodeEntityByJodId(String jobId) {
		return this.getSqlSession().selectList(NAME_SPACE+".queryAutoAddGisEntityByJodId", jobId);
	}

	/**  
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IAutoAddCodeDao#resetData()  
	 */
	@Override
	public void resetData() {
		this.getSqlSession().update(NAME_SPACE+".resetGisData");
	}

}
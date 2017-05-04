/**  
 * Project Name:tfr-common  
 * File Name:TfrJobTodoDao.java  
 * Package Name:com.deppon.foss.module.transfer.common.server.dao.impl  
 * Date:2015年4月15日上午11:18:51  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.server.dao.impl;  

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**  
 * ClassName: TfrJobTodoDao <br/>  
 * Function: 待办jobdao. <br/>  
 * date: 2015年4月15日 上午11:18:51 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class TfrJobTodoDao extends iBatis3DaoImpl implements ITfrJobTodoDao{
	
	private static final String NAMESPACE = "foss.tfr.jobtodo.";

	/**  
	 * 新增.  
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao#addJobTodo(com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity)  
	 */
	@Override
	public int addJobTodo(TfrJobTodoEntity tfrJobTodoEntity) {
		this.getSqlSession().insert(NAMESPACE + "addJobTodo", tfrJobTodoEntity);
		return FossConstants.SUCCESS;
	}

	/**  
	 * 查询.  
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao#selectJobTodo(com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto)  
	 */
	@SuppressWarnings("unchecked") 
	@Override
	public List<TfrJobTodoEntity> selectJobTodo(
			TfrJobTodoQueryDto tfrJobTodoQueryDto) {
		return this.getSqlSession().selectList(NAMESPACE + "selectJobTodo", tfrJobTodoQueryDto);
	}

	/**  
	 * 将已经处理的待办job记录迁移.  
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao#addJobTodo2Migration(java.lang.String)  
	 */
	@Override
	public int addJobTodo2Migration(String id) {
		this.getSqlSession().insert(NAMESPACE + "addJobTodo2Migration", id);
		return FossConstants.SUCCESS;
	}
	
	
	
	/*
	 * 查询是否已经处理了的业务ID
	 *  (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao#selectJobTodoMigrationByBusinessId(java.lang.String)
	 */
	@Override
	public int selectJobTodoMigrationByBusinessId(String businessId,String businessGoal) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("businessId", businessId);
		map.put("businessGoal", businessGoal);
		int backNum = 0;
		try {
			backNum = (Integer) this.getSqlSession().selectOne(NAMESPACE + "selectJobTodoMigrationByBusinessId", map);
		} catch (Exception e) {
			return backNum;
		} 
		return backNum;
	}

	/**  
	 * 删除.  
	 * @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao#deleteJobTodoByID(java.lang.String)  
	 */
	@Override
	public int deleteJobTodoByID(String id) {
		this.getSqlSession().delete(NAMESPACE + "deleteJobTodo", id);
		return FossConstants.SUCCESS;
	}

	
	/**
	* @description job表中更新jobid
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao#updateAndGetJobId(java.lang.String, int)
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:04:46
	* @version V1.0
	*/
	@Override
	public int updateAndGetJobId(int count,String jobId,List<String> list) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", jobId);
		map.put("count", count);
		map.put("list", list);
		return this.getSqlSession().update(NAMESPACE+"updateAndGetJobId", map);
	}

	
	/**
	* @description 根据jobId查询出代办job
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao#selectJobTodoByJobId(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:30:15
	* @version V1.0
	*/
	@Override
	@SuppressWarnings("unchecked") 
	public List<TfrJobTodoEntity> selectJobTodoByJobId(int count,String jobId,List<String> list) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", count);
		map.put("jobId", jobId);
		map.put("list", list);
		return this.getSqlSession().selectList(NAMESPACE + "selectJobTodoByJobId", map);
	}

	
	/**
	* @description 根据id把job表中的此条数据的jobId设置为"N/A"
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao#updateJobIdToNA(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:54:11
	* @version V1.0
	*/
	@Override
	public int updateJobIdToNA(String id) {
		return this.getSqlSession().update(NAMESPACE+"updateJobIdToNA", id);
	}

	/**
	 * 
	 * <p>根据业务id,业务目标，操作类型来确定是否唯一</p> 
	 * @author alfred
	 * @date 2015年7月29日 上午10:30:31
	 * @param businessId
	 * @param businessGoal
	 * @param businessScene
	 * @return
	 * @see
	 */
	@Override
	public int selectJobTodoMigrationByBusinessId(String businessId,
			String businessGoal, String businessScene) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("businessId", businessId);
		map.put("businessGoal", businessGoal);
		map.put("businessScene", businessScene);
		int backNum = 0;
		try {
			backNum = (Integer) this.getSqlSession().selectOne(NAMESPACE + "selectJobTodoMigrationByMap", map);
		} catch (Exception e) {
			return backNum;
		} 
		return backNum;
	}
	/**
	 * 
	* @Title: selectJobTodoByBusinessId 
	* @Description: 根据  条件查询代办实体 
	* @param @param businessId 业务Id
	* @param @param businessGoal业务场景
	* @param @param businessScene业务动作
	* @param @return    设定文件 
	* @return List<TfrJobTodoEntity>    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-22  下午6:17:48
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TfrJobTodoEntity>  selectJobTodoByBusinessId(String businessId,
			String businessGoal, String businessScene) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("businessId", businessId);
		map.put("businessGoal", businessGoal);
		map.put("businessScene", businessScene);
		return this.getSqlSession().selectList(NAMESPACE + "selectJobTodoByMap", map);	
	}
}
  

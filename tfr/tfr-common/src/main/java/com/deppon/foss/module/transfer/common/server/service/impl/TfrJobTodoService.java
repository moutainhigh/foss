/**  
 * Project Name:tfr-common  
 * File Name:TfrJobTodoService.java  
 * Package Name:com.deppon.foss.module.transfer.common.server.service.impl  
 * Date:2015年4月15日下午12:50:43  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.server.service.impl;  

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobTodoDao;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**  
 * ClassName: TfrJobTodoService <br/>  
 * Function: 待办job service实现类. <br/>  
 * date: 2015年4月15日 下午12:50:43 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class TfrJobTodoService implements ITfrJobTodoService {
	
	private ITfrJobTodoDao tfrJobTodoDao;

	/**  
	 * tfrJobTodoDao.  
	 *  
	 * @param   tfrJobTodoDao    the tfrJobTodoDao to set  
	 * @since   JDK 1.6  
	 */
	public void setTfrJobTodoDao(ITfrJobTodoDao tfrJobTodoDao) {
		this.tfrJobTodoDao = tfrJobTodoDao;
	}

	/**  
	 * 新增
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService#addJobTodo(java.lang.String, java.lang.String, java.lang.String[], java.util.Date)  
	 */
	@Override
	public int addJobTodo(String businessID, String businessScene,
			String[] businessGoalList, Date businessTime, String operatorCode) {
		if(StringUtils.isBlank(businessID)){
			throw new TfrBusinessException("请传入业务ID。");
		}
		if(StringUtils.isBlank(businessScene)){
			throw new TfrBusinessException("请传入当前业务场景。");
		}
		if(businessGoalList == null || businessGoalList.length == 0){
			throw new TfrBusinessException("请传入至少一个下阶段要达成的业务目标。");
		}
		if(businessTime == null){
			throw new TfrBusinessException("请传入业务发生时间。");
		}
		
		
		for(int i = 0; i < businessGoalList.length; i++){
				String businessGoal = businessGoalList[i];
				TfrJobTodoEntity entity = new TfrJobTodoEntity();
				entity.setId(UUIDUtils.getUUID());
				entity.setBusinessID(businessID);
				entity.setBusinessScene(businessScene);
				entity.setBusinessGoal(businessGoal);
				entity.setBusinessTime(businessTime);
				entity.setCreateTime(new Date());
				entity.setOperatorCode(operatorCode);
				tfrJobTodoDao.addJobTodo(entity);
		}
		return FossConstants.SUCCESS;
	}

	/**  
	 * 更新
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService#updateJobTodo(java.lang.String)  
	 */
	@Override
	public int updateJobTodoByID(String id) {
		tfrJobTodoDao.addJobTodo2Migration(id);
		return tfrJobTodoDao.deleteJobTodoByID(id);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService#deleteJobTodoByID(java.lang.String)
	 */
	@Override
	public int deleteJobTodoByID(String id) {
		return tfrJobTodoDao.deleteJobTodoByID(id);
	}

	/**
	 * 查询是否已经处理了的业务ID
	 * @param businessId
	 * @return
	 */
	@Override
	public int selectJobTodoMigrationByBusinessId(String businessId,String businessGoal) {
		return tfrJobTodoDao.selectJobTodoMigrationByBusinessId(businessId,businessGoal);
	}

	/**  
	 * 查询  
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService#queryJobTodo(com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto)  
	 */
	@Override
	public List<TfrJobTodoEntity> queryJobTodo(
			TfrJobTodoQueryDto tfrJobTodoQueryDto) {
		if(tfrJobTodoQueryDto == null){
			throw new TfrBusinessException("请设置合理的查询条件。");
		}
		if(tfrJobTodoQueryDto.getCount() == 0){
			throw new TfrBusinessException("请设置要查询的总条数。");
		}
		if(tfrJobTodoQueryDto.getBusinessSceneList() == null
				|| tfrJobTodoQueryDto.getBusinessSceneList().length == 0){
			throw new TfrBusinessException("请至少传入一个要查询的业务场景。");
		}
		if(tfrJobTodoQueryDto.getBusinessGoalList() == null 
				|| tfrJobTodoQueryDto.getBusinessGoalList().length == 0){
			throw new TfrBusinessException("请至少传入一个待处理的业务目标。");
		}
		
		return tfrJobTodoDao.selectJobTodo(tfrJobTodoQueryDto);
	}

	
	/**
	* @description 查询未处理的代办Job  业务目标,业务场景允许为空
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService#queryJobTodoToComm(com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto)
	* @author 218381-foss-lijie
	* @update 2015年5月21日 下午2:17:29
	* @version V1.0
	*/
	@Override
	public List<TfrJobTodoEntity> queryJobTodoToComm(
			TfrJobTodoQueryDto tfrJobTodoQueryDto) {
		if(tfrJobTodoQueryDto == null){
			throw new TfrBusinessException("请设置合理的查询条件。");
		}
		if(tfrJobTodoQueryDto.getCount() == 0){
			throw new TfrBusinessException("请设置要查询的总条数。");
		}

		return tfrJobTodoDao.selectJobTodo(tfrJobTodoQueryDto);
	
	}

	
	/**
	* @description job表中更新jobid
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService#updateAndGetJobId()
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:01:13
	* @version V1.0
	*/
	@Override
	public String updateAndGetJobId(int count,List<String> list) {
		String jobid  =  UUIDUtils.getUUID();
		
		tfrJobTodoDao.updateAndGetJobId(count, jobid, list);
		return jobid;
	}

	@Override
	public List<TfrJobTodoEntity> queryJobTodoToCommByJobId(int count,String jobId,List<String> list) { 
		return tfrJobTodoDao.selectJobTodoByJobId(count,jobId,list);
	}

	
	/**
	* @description 根据id把job表中的此条数据的jobId设置为"N/A"
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService#updateJobIdToNA(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:52:24
	* @version V1.0
	*/
	@Override
	public int updateJobIdToNA(String id) {
		
		return tfrJobTodoDao.updateJobIdToNA(id);
	}

	/**
	 * 
	 * <p>根据业务id,业务目标，操作类型来确定是否唯一</p> 
	 * @author alfred
	 * @date 2015年7月29日 上午10:27:49
	 * @param businessId
	 * @param businessGoal
	 * @param businessScene
	 * @return
	 * @see
	 */
	@Override
	public int selectJobTodoMigrationByBusinessId(String businessId,
			String businessGoal, String businessScene) {
		return tfrJobTodoDao.selectJobTodoMigrationByBusinessId(businessId,businessGoal,businessScene);
	}
	/**
	 * 
	* @Title: selectJobTodoByBusinessId 
	* @Description: 根据  条件查询代办实体 
	* @param @param businessId 业务Id
	* @param @param businessGoal业务场景
	* @param @param businessScene业务动作
	* @param @return
	* @return List<TfrJobTodoEntity>    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-22  下午6:17:48
	* @throws
	 */
	@Override
	public List<TfrJobTodoEntity> selectJobTodoByBusinessId(String businessId,
			String businessGoal, String businessScene){
		if(StringUtils.isEmpty(businessId)||
				StringUtils.isEmpty(businessGoal)||
				StringUtils.isEmpty(businessScene)){
			throw new TfrBusinessException("请设置合理的查询条件。");
		}
		return tfrJobTodoDao.selectJobTodoByBusinessId(businessId,businessGoal,businessScene);
	}
}
  

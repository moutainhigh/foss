/**  
 * Project Name:tfr-common-api  
 * File Name:ITfrJobTodoService.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.server.service  
 * Date:2015年4月15日上午11:29:05  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.api.server.service;  

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;

/**  
 * ClassName: ITfrJobTodoService <br/>  
 * Function: 待办jobservice接口. <br/>  
 * date: 2015年4月15日 上午11:29:05 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public interface ITfrJobTodoService extends IService{
	
	/**
	 * 
	 * addJobTodo:(新增待办Job). <br/>  
	 * 在各先发业务场景中调用.<br/>  
	 *  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param businessID 业务ID，可以是先发业务场景产生记录的数据库主键，也可以是业务主键（编号等）
	 * @param businessScene 先发业务场景
	 * @param businessGoalList 先发业务场景之后，下阶段需要达成的业务目标列表
	 * @param businessTime 业务发生的时间，例如车辆出发时间，交接单保存时间等
	 * @param operatorCode 操作人工号
	 * @return  
	 * @since JDK 1.6
	 */
	int addJobTodo(String businessID,String businessScene,String[] businessGoalList,Date businessTime,String operatorCode);
	
	/**
	 * 
	 * updateJobTodo:(名为update，实为对tfr.t_opt_job_todo_mig做insert，再对tfr.t_opt_job_todo做delete). <br/>  
	 *  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param id tfr.t_opt_job_todo的id字段
	 * @return  
	 * @since JDK 1.6
	 */
	int updateJobTodoByID(String id);
	
	/**
	 * @param id
	 * @return
	 */
	int deleteJobTodoByID(String id);
	
	/**
	 * 查询是否已经处理了的业务ID
	 * @param businessId
	 * @param businessGoal
	 * @return
	 */
	int selectJobTodoMigrationByBusinessId(String businessId,String businessGoal);
	
	/**
	 * 
	 * queryJobTodo:(查询未处理的待办Job). <br/>  
	 *  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param tfrJobTodoQueryDto
	 * @return  
	 * @since JDK 1.6
	 */
	List<TfrJobTodoEntity> queryJobTodo(TfrJobTodoQueryDto tfrJobTodoQueryDto);
	
	
	/**
	* @description 查询未处理的代办Job  业务目标,业务场景允许为空
	* @param tfrJobTodoQueryDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月21日 下午2:15:38
	*/
	List<TfrJobTodoEntity> queryJobTodoToComm(TfrJobTodoQueryDto tfrJobTodoQueryDto);
	
	
	/**
	* @description 根据jobid查询出代办job
	* @param jobId
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:27:51
	*/
	List<TfrJobTodoEntity> queryJobTodoToCommByJobId(int count,String jobId,List<String> list);
	/**
	* @description 更新获取jobtodo表中的jobid
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:00:27
	*/
	public String updateAndGetJobId(int count,List<String> list);
	
	
	/**
	* @description 根据id把job表中的此条数据的jobId设置为"N/A"
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:51:26
	*/
	public int updateJobIdToNA(String id);
	
	
	/**
	 * 
	 * <p>根据业务id,业务目标，操作类型来确定是否唯一</p> 
	 * @author alfred
	 * @date 2015年7月29日 上午10:26:04
	 * @param businessId
	 * @param businessGoal
	 * @return
	 * @see
	 */
	int selectJobTodoMigrationByBusinessId(String businessId,String businessGoal,String businessScene );
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
	List<TfrJobTodoEntity> selectJobTodoByBusinessId(String businessId,
			String businessGoal, String businessScene);
}
  

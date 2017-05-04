/**  
 * Project Name:tfr-common-api  
 * File Name:ITfrJobTodoDao.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.server.dao  
 * Date:2015年4月15日上午11:08:06  
 *  
 */  
  
package com.deppon.foss.module.transfer.common.api.server.dao;  

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;

/**  
 * ClassName: ITfrJobTodoDao <br/>  
 * Function: 待办jobDao接口. <br/>  
 * date: 2015年4月15日 上午11:08:06 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public interface ITfrJobTodoDao {
	
	/**
	 * 
	 * addJobTodo:(新增待办Job的时候调用). <br/>  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param tfrJobTodoEntity
	 * @return  
	 * @since JDK 1.6
	 */
	int addJobTodo(TfrJobTodoEntity tfrJobTodoEntity);
	
	/**
	 * 
	 * selectJobTodo:(查询未处理待办Job信息). <br/>  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param tfrJobTodoQueryDto
	 * @return  
	 * @since JDK 1.6
	 */
	List<TfrJobTodoEntity> selectJobTodo(TfrJobTodoQueryDto tfrJobTodoQueryDto);
	
	
	
	/**
	* @description 根据jobid查询出代办job
	* @param jobId
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:29:41
	*/
	List<TfrJobTodoEntity> selectJobTodoByJobId(int count,String jobId,List<String> list);
	
	
	/**
	* @description 根据id把job表中的此条数据的jobId设置为"N/A"
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:53:45
	*/
	int updateJobIdToNA(String id);
	
	/**
	 * 
	 * addJobTodo2Migration:(根据主键ID，将tfr.t_opt_job_todo表中的记录复制到tfr.t_opt_job_todo_mig，做转储). <br/>  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param id
	 * @return  
	 * @since JDK 1.6
	 */
	int addJobTodo2Migration(String id);
	
	/**
	 * 查询是否已经处理了的业务ID
	 * @param businessId
	 * @param businessGoal
	 * @return
	 */
	int selectJobTodoMigrationByBusinessId(String businessId,String businessGoal);
	
	/**
	 * 
	 * deleteJobTodoByID:(处理完毕后，将tfr.t_opt_job_todo里的记录删除). <br/>  
	 *  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param id
	 * @return  
	 * @since JDK 1.6
	 */
	int deleteJobTodoByID(String id);
	
	
	/**
	* @description job表中更新jobid
	* @param jobId
	* @param limit
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月28日 下午7:03:46
	*/
	public int updateAndGetJobId(int count,String jobId,List<String> list);
	
	/**
	 * 
	 * <p>根据业务id,业务目标，操作类型来确定是否唯一</p> 
	 * @author alfred
	 * @date 2015年7月29日 上午10:28:50
	 * @param businessId
	 * @param businessGoal
	 * @return
	 * @see
	 */
	int selectJobTodoMigrationByBusinessId(String businessId,String businessGoal,String businessScene);
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
	List<TfrJobTodoEntity> selectJobTodoByBusinessId(String businessId,
			String businessGoal, String businessScene);
}
  

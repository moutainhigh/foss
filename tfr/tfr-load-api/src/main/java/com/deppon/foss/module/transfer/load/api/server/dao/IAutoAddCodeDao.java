package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity;

/**
 * @title: IAutoAddCodeDao 
 * @description: 自动补码Dao层 数据接口.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 09:22:38
 */
public interface IAutoAddCodeDao {
	
	/**
	* 
	* @MethodName: insert 
	* @description: insert方法
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 09:22:38
	* @param entity void
	*/
	void insert(AutoAddCodeEntity entity);

	/**
	* 
	* @MethodName: deleteByPrimaryKey 
	* @description: 根据主键移除
	* @author: 140022-foss-songjie
	* @date: 2015-05-13 09:22:38
	* @param entity void
	*/
	void deleteEntityByPrimaryKey(AutoAddCodeEntity entity);
	
	/**
	* 
	* @MethodName: updateByPrimaryKey 
	* @description: 根据主键更新
	* @author: 140022-foss-songjie
	* @date: 2015-05-13 09:22:38
	* @param entity void
	*/
	void updateByPrimaryKey(AutoAddCodeEntity entity);

	/**
	* 
	* @MethodName: expandByPrimaryKey 
	* @description: 根据主键查询
	* @author: 140022-foss-songjie 
	* @date: 2015-05-13 09:22:38
	* @param entity
	* @return AutoAddCodeEntiy
	*/
	AutoAddCodeEntity expandByPrimaryKey(AutoAddCodeEntity entity);
	
	/**
	 * updateAndGetJobId:更新jobID. <br/>  
	 *  Date:2015年6月15日下午4:55:27  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param jobid
	 * @param limit
	 * @return  
	 * @since JDK 1.6
	 */
	int updateAndGetJobId(String jobid, int limit);
	
	/**
	* @description 根据jobId值 初始化jobid
	* @param jobId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年6月25日 上午10:53:25
	*/
	int resetDataByJobId(String jobId);
	
	
	/**
	* @description 根据id值 初始化jobid
	* @param id
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月29日 上午9:42:43
	*/
	int resetDataById(String id);
	
	/**
	 * queryAutoAddCodeEntityByJodId:(这里用一句话描述这个方法的作用). <br/>  
	 *  Date:2015年6月15日下午7:29:48  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param jobId
	 * @return  
	 * @since JDK 1.6
	 */
	List<AutoAddCodeEntity> queryAutoAddCodeEntityByJodId(String jobId);
	
	/**
	 * resetData:应用重启时重置异常数据. <br/>  
	 *  Date:2015年6月15日下午9:08:37  
	 * @author shiwei-045923 shiwei@outlook.com    
	 * @since JDK 1.6
	 */
	void resetData();
	
	/**
	* 
	* @description: 查询30分钟之前的job不为空的数据
	* @author: 140022-foss-songjie 
	* @date: 2015-10-24 09:22:38
	* @param entity
	* @return AutoAddCodeEntiy
	*/
	List<AutoAddCodeEntity> queryListJobIdForReset();
	
}
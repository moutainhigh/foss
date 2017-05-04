package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddGisEntity;

/**
 * @title: IAutoAddCodeDao 
 * @description: 自动补码和Gis交互返回数据Dao层 数据接口.
 * @author: 140022-foss-songjie
 * @update 2015年11月4日 上午8:32:01
 */
public interface IAutoAddGisDao {
	
	/**
	* 
	* @MethodName: insert 
	* @description: insert方法
	* @author: 140022-foss-songjie 
	* @date: 2015年11月4日 上午8:32:01
	* @param entity void
	*/
	int insert(AutoAddGisEntity entity);

	/**
	* 
	* @MethodName: deleteByPrimaryKey 
	* @description: 根据主键移除
	* @author: 140022-foss-songjie
	* @date: 2015年11月4日 上午8:32:01
	* @param entity void
	*/
	void deleteEntityByPrimaryKey(AutoAddGisEntity entity);

	/**
	* 
	* @MethodName: expandByPrimaryKey 
	* @description: 根据主键查询
	* @author: 140022-foss-songjie 
	* @date: 2015年11月4日 上午8:32:01
	* @param entity
	* @return AutoAddCodeEntiy
	*/
	AutoAddGisEntity queryByPrimaryKey(AutoAddGisEntity entity);
	
	
	/**
	* @description 根据autoaddCode的主键id删除
	* @param autoAddId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月6日 上午10:22:06
	*/
	AutoAddGisEntity queryByAutoAddId (String autoAddId);
	
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
	 * queryAutoAddCodeEntityByJodId:根据jobid查询对应的记录. <br/>  
	 *  Date:2015年6月15日下午7:29:48  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param jobId
	 * @return  
	 * @since JDK 1.6
	 */
	List<AutoAddGisEntity> queryAutoAddCodeEntityByJodId(String jobId);
	
	/**
	 * resetData:应用重启时重置异常数据. <br/>  
	 *  Date:2015年6月15日下午9:08:37  
	 * @author shiwei-045923 shiwei@outlook.com    
	 * @since JDK 1.6
	 */
	void resetData();

}
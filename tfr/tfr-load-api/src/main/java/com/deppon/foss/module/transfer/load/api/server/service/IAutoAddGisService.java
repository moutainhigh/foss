package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddGisEntity;

/**
 * @title: IAutoAddCodeService 
 * @description: 业务接口.
 * @author: 140022-foss-songjie
 * @Date: 2015-05-13 09:22:38
 */
public interface IAutoAddGisService extends IService {

	/**
	* @description 更新jobid并返回对应的jobid
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午9:51:19
	*/
	String GisUpdateGetJobId();
	
	/**
	* @description 据生成的jobId获取 List<AutoAddGisEntity> 
	* @param jobId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午10:25:19
	*/
	List<AutoAddGisEntity> queryGisAutoAddCodeEntityByJodId(String jobId);
	
	/**
	* @description gis交互返回或未处理掉的运单保存
	* @param entity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年11月4日 上午10:32:32
	*/
	int insertAutoAddGisEntity (AutoAddGisEntity entity);

	/**  
	 *  Date:2015年6月15日下午9:03:45  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param obj  
	 * @since JDK 1.6  
	 */
	void ThreadsPool(Object obj);
	
	/**
	 * resetData:重置异常数据. <br/>  
	 *  Date:2015年6月15日下午9:11:24  
	 * @author shiwei-045923 shiwei@outlook.com    
	 * @since JDK 1.6
	 */
	void resetData();
	
	/**
	* @description 根据jobId重置补码
	* @param jobId
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年10月29日 上午8:39:57
	*/
	void restNaAJobId(String jobId) throws Exception;
}
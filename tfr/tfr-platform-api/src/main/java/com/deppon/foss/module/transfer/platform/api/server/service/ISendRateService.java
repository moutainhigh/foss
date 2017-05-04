package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;


/**
* @description 派送率Serivce
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:24:43
*/
public interface ISendRateService extends IService{
	
	/**
	* @description 查询派送率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:40:57
	*/
	public List<SendRateEntity> querySendRateList(SendRateEntity sendRateEntity,int start,int limit);
	
	
	/**
	* @description 查询派送率的总结记录数
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:42:04
	*/
	public long querySendRateListCount(SendRateEntity sendRateEntity);
	
	/**
	* @description 累计日派送率查询
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:01:20
	*/
	public List<SendRateEntity> querySendRateLogList(SendRateEntity sendRateEntity,int start,int limit);
	
	
	/**
	* @description 累计日派送率查询总数
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:36
	*/
	public long querySendRateLogListCount(SendRateEntity sendRateEntity);
	
	/**
	* @description 派送率定时任务查询 Service
	* @param jobDate
	* @param threadNo
	* @param threadCount
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午11:18:37
	*/
	public void doSendRateJobList(Date jobDate,int threadNo,int threadCount) throws Exception;
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 根据部门and日期 查询当日的预计派送到达货量
	 *@param orgCode,queryDate
	 *@return List<SendRateEntity>
	 *@author 105795
	 *@date 2015年4月10日下午4:14:50 
	 */
	List<SendRateEntity> queryForeCastDeliverGoodsByDate(String orgCode,String queryDate);

}

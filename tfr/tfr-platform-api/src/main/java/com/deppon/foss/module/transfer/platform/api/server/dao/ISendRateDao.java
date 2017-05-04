package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;


/**
* @description 派送率操作
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:24:43
*/
public interface ISendRateDao {
	
	/**
	* @description 查询派送率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:40:57
	*/
	public List<SendRateEntity> querySendRateList(Map<String,String> map,int start,int limit);
	

	/**
	* @description 查询派送率的总结记录数
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:42:04
	*/
	public int querySendRateListCount(Map<String,String> map);
	
	
	/**
	* @description 累计日派送率查询 具体外场
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:19
	*/
	public List<SendRateEntity> querySendRateLogList(Map<String,String> map,int start,int limit);
	
	
	/**
	* @description 累计日派送率查询总数 具体外场
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:36
	*/
	public int querySendRateLogListCount(Map<String,String> map);
	
	/**
	* @description 累计日派送率查询  全国
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:19
	*/
	public List<SendRateEntity> querySendRateLogListAll(Map<String,String> map,int start,int limit);
	
	
	/**
	* @description 累计日派送率查询总数 全国
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:36
	*/
	public int querySendRateLogAllListCount(Map<String,String> map);
	
	
	/**
	* @description 派送率定时任务查询 dao
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午10:56:38
	*/
	public List<SendRateEntity> querySendRateJobList(Map<String,String> map);
	
	
	/**
	* @description 派送率添加 Dao
	* @param sendRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午4:35:56
	*/
	int insertSendRatePojo(SendRateEntity sendRateEntity);
	
	
	/**
	* @description 派送率批量添加 Dao
	* @param sendRateEntityList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月26日 下午6:07:32
	*/
	void batchInsertSendRatePojo(List<SendRateEntity> sendRateEntityList);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据部门and日期 查询当日的预计派送到达货量
	 *@param 
	 *@return List<SendRateEntity>
	 *@author 105795
	 *@date 2015年4月10日下午3:59:10 
	 */
	List<SendRateEntity> queryForeCastDeliverGoodsByDate(String orgCode,String queryDate);
	
}

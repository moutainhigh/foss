package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity;


/**
* @description 退单率操作
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:25:08
*/
public interface IReturnRateDao {
	
	/**
	* @description 查询退单率
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:44:10
	*/
	public List<ReturnRateEntity> queryReturnRateList(Map<String,String> map,int start,int limit);
	
	/**
	* @description 查询退单率总记录
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:44:54
	*/
	public int queryReturnRateListCount(Map<String,String> map);
	
	/**
	* @description 累计退单率查询分页
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:39:16
	*/
	List<ReturnRateEntity> queryReturnRateLogList(Map<String,String> map,int start,int limit);
	
	/**
	* @description 累计退单率查询分页总记录数
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:39:16
	*/
	int queryReturnRateLogListCount(Map<String,String> map);
	
	/**
	* @description 累计退单率查询分页 全国的
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:39:16
	*/
	List<ReturnRateEntity> queryReturnRateAllLogList(Map<String,String> map,int start,int limit);
	
	/**
	* @description 累计退单率查询分页总记录数 全国的
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:39:16
	*/
	
	int queryReturnRateAllLogListCount(Map<String,String> map);
	
	
	
	/**
	* @description 退单率定时执行Dao
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午10:57:29
	*/
	public List<ReturnRateEntity> queryReturnRateJobList(Map<String,String> map);
	
	
	/**
	* @description 添加退单率
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午1:54:10
	*/
	int insertReturnRatePojo(ReturnRateEntity returnRateEntity);
	
	
	/**
	* @description 批量添加退单率
	* @param returnRateEntityList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月26日 下午6:08:18
	*/
	void batchInsertReturnRatePojo(List<ReturnRateEntity> returnRateEntityList);

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 查询外场提交派送装车任务票数
	 *@param 
	 *@return List<ReturnRateEntity>
	 *@author 105795
	 *@date 2015年3月16日下午4:33:33 
	 */
	List<ReturnRateEntity> queryDeliverLoadWaybillQty(List<String> orgCodeList);
	
}

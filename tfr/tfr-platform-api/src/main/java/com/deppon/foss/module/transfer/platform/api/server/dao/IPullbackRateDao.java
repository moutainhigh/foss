package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;


/**
* @description 拉回率操作
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:25:43
*/
public interface IPullbackRateDao {
	
	/**
	* @description 查询拉回率
	* @param pullbackRateEntity
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:48:29
	*/
	public List<PullbackRateEntity> queryPullbackRateList(Map<String,String> map,int start,int limit);
	
	
	/**
	* @description 查询拉回率总记录
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:49:02
	*/
	public int queryPullbackRateListCount(Map<String,String> map);
	
	
	/**
	* @description 拉回率定时执行Dao
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午10:58:11
	*/
	public List<PullbackRateEntity> queryPullbackRateJobList(Map<String,String> map);
	
	/**
	* @description 累计拉回率查询分页
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午3:15:07
	*/
	List<PullbackRateEntity> queryPullbackRateLogList(Map<String,String> map,int start,int limit);
	
	/**
	* @description 累计拉拉回率查询分页总记录数
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午3:15:07
	*/
	int queryPullbackRateLogListCount(Map<String,String> map);
	
	/**
	* @description 累计拉回率查询分页 全国的
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午3:15:07
	*/
	List<PullbackRateEntity> queryPullbackRateAllLogList(Map<String,String> map,int start,int limit);
	
	/**
	* @description 累计拉拉回率查询分页总记录数 全国的
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午3:15:07
	*/
	int queryPullbackRateAllLogListCount(Map<String,String> map);
	
	
	/**
	* @description 添加拉回率记录
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午11:54:42
	*/
	int insertPullbackRatePojo(PullbackRateEntity pojo);
	
	
	/**
	* @description 批量插入
	* @param list
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月26日 下午4:14:18
	*/
	void batchInsertPullbackRatePojo(List<PullbackRateEntity> list);
}

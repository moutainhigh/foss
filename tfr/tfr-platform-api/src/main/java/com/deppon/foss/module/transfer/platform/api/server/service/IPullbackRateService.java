package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;


/**
* @description 拉回率操作
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:25:43
*/
public interface IPullbackRateService extends IService {
	
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
	public List<PullbackRateEntity> queryPullbackRateList(PullbackRateEntity pullbackRateEntity,int start,int limit);
	
	
	/**
	* @description 查询拉回率总记录
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:49:02
	*/
	public long queryPullbackRateListCount(PullbackRateEntity pullbackRateEntity);
	
	
	/**
	* @description 累计拉回率查询分页
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	
	List<PullbackRateEntity> queryPullbackRateLogList(PullbackRateEntity pullbackRateEntity,int start,int limit);
	
	/**
	* @description 累计拉拉回率查询分页总记录数
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	long queryPullbackRateLogListCount(PullbackRateEntity pullbackRateEntity);
	
	/**
	* @description  拉回率定时执行Service
	* @param jobDate
	* @param threadNo
	* @param threadCount
	* @return
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午11:37:01
	*/
	public void doPullbackRateJobList(Date jobDate,
			int threadNo, int threadCount) throws Exception ;
}

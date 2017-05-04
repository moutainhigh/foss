package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.TrackingsRequestCommDto;


/**
* @description 推送轨迹给dop的公共类
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年5月28日 下午7:25:10
*/
public interface IPushTrackSendDopService extends IService  {
	
	/**
	* @description 轨迹表中更新jobid
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:30:14
	*/
	public String updateAndGetJobId();
	
	/**
	* @description 根据jobid查询待推送数据
	* @param jobId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:28:34
	*/
	public List<TrackingsRequestCommDto> queryTrackingsInfobyJobId(String jobId);
	public void ThreadsPool(Object obj);
	public void reSetTrackingsMsgbyJobId();
}

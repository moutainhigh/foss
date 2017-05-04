package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;

/***
 * 货物轨迹推送接口
 *货物轨迹信息存储到异步表，以便后续推送
 *用于不同 业务场景的货物轨迹存储service
 * @author 205109-
 * @date 2015-04-27 上午11:13:20
 ****/
public interface IPushCarGoTrackService extends IService {
	
	/**
	* @description 给DOP推送货物轨迹信息
	* @param bizJobStartTime
	* @param bizJobEndTime
	* @param threadNo
	* @param threadCount
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月21日 下午2:04:24
	*/
	public void pushTrackForDop(Date bizJobStartTime, Date bizJobEndTime, int threadNo, int threadCount);
	
	
	/**
	* @description 给DOP推送货物轨迹信息 线程调用
	* @param count
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 上午10:47:16
	*/
	public int pushTrackForDop(int count,List<String> list);
	
	
	/**
	* @description 重置jobtodo表中的异常数据
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月29日 上午8:39:56
	*/
	public void reSetJobMsgbyJobId();


}

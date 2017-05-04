package com.deppon.foss.module.transfer.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.domain.TrackExternalLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TrackingsRequestCommDto;



public interface ITrackingsDao {
	
	/**
	* @description  轨迹表中更新jobid
	* @param jobid
	* @param limit
	* @param list
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月30日 上午10:15:29
	*/
	public int updateAndGetJobId(String jobid,int limit,List<String> list);
	
	/**
	* @description 根据jobid查询待推送数据
	* @param jobId
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:11:47
	*/
	public List<TrackingsRequestCommDto> queryTrackingsInfobyJobId(String jobId,List<String> list);
	
	/**
	* @description  异常情况下重置
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:11:50
	*/
	public int resetMsgbyJobId();
	
	/**
	* @description 批量更新没有处理完成数据的jobid为N/A
	* @param dtos
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:11:53
	*/
	public int updateTrackingsMsgs(List<TrackingsRequestCommDto> dtos);
	
	/**
	* @description 更新没有处理完成数据的jobid为N/A
	* @param id
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:11:55
	*/
	public int updateTrackingsMsg(String id);
	
	/**
	* @description 处理成功后删除数据
	* @param id
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:11:58
	*/
	public int deleteMsgById(String id);
	
	/**
	* @description 插入推送轨迹日志
	* @param logs
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:12:00
	*/
	public int addTrackLogs(List<TrackExternalLogEntity> logs);
	
	
	/**
	* @description 控制批量新增、修改条数
	* @param saveType
	* @param saveId
	* @param data
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:12:03
	*/
	public int batchSaveProcess(String saveType,String saveId,List<?> data);
}

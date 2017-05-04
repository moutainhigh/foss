package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao;
import com.deppon.foss.module.transfer.common.api.shared.domain.TrackExternalLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TrackingsRequestCommDto;

public class TrackingsDao extends iBatis3DaoImpl implements ITrackingsDao {
	private static final String NAMESPACE = "foss.common.Trackings.Comm.";
	
	/**批量处理条数*/
	public static final int BATCH_COUNT = 100;
	public static final String BATCH_SAVE_TYPE_INSERT = "INSERT";
	public static final String BATCH_SAVE_TYPE_UPDATE = "UPDATE";
	
	/**
	* @description 轨迹表中更新jobid
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao#updateAndGetJobId(java.lang.String, int)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:08:42
	* @version V1.0
	*/
	@Override
	public int updateAndGetJobId(String jobid, int limit,List<String> list) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", jobid);
		map.put("limit", limit);
		map.put("list", list);
		return this.getSqlSession().update(NAMESPACE+"updateAndGetJobId", map);
	}

	
	/**
	* @description 根据jobid查询待推送数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao#queryTrackingsInfobyJobId(java.lang.String)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:08:28
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingsRequestCommDto> queryTrackingsInfobyJobId(String jobId,List<String> list) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", jobId);
		map.put("list", list);
		return this.getSqlSession().selectList(NAMESPACE + "queryTrackingsInfobyJobId", map);
	}

	
	/**
	* @description 异常情况下重置
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao#resetMsgbyJobId()
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:08:15
	* @version V1.0
	*/
	@Override
	public int resetMsgbyJobId() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", "N/A");
		return this.getSqlSession().update(NAMESPACE+"resetMsgbyJobId", map);
	}

	
	/**
	* @description 批量更新没有处理完成数据的jobid为N/A
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao#updateTrackingsMsgs(java.util.List)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:22:18
	* @version V1.0
	*/
	@Override
	public int updateTrackingsMsgs(List<TrackingsRequestCommDto> dtos) {
		return this.batchSaveProcess(BATCH_SAVE_TYPE_UPDATE, 
				NAMESPACE+"updateTrackingsMsg", dtos);
	}

	
	/**
	* @description 更新没有处理完成数据的jobid为N/A
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao#updateTrackingsMsg(java.lang.String)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:07:31
	* @version V1.0
	*/
	@Override
	public int updateTrackingsMsg(String id) {
		return this.getSqlSession().update(NAMESPACE+"updateTrackingsMsg", id);
	}

	
	/**
	* @description 处理成功后删除数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao#deleteMsgById(java.lang.String)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:07:49
	* @version V1.0
	*/
	@Override
	public int deleteMsgById(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteMsgById", id);
	}

	
	/**
	* @description 插入推送轨迹日志
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao#addTrackLogs(java.util.List)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:12:32
	* @version V1.0
	*/
	@Override
	public int addTrackLogs(List<TrackExternalLogEntity> logs) {
		return this.batchSaveProcess(BATCH_SAVE_TYPE_INSERT, NAMESPACE+"insertLoadDestOrg", logs);
	}


	
	/**
	* @description 控制批量新增、修改条数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.ITrackingsDao#batchSaveProcess(java.lang.String, java.lang.String, java.util.List)
	* @author 14022-foss-songjie
	* @update 2015年5月28日 下午8:12:50
	* @version V1.0
	*/
	@Override
	public int batchSaveProcess(String saveType, String saveId, List<?> data) {
		int start = 0;
		int end;
		if(start+BATCH_COUNT<data.size()){
			end = start+BATCH_COUNT;
		}else{
			end = data.size();
		}
		while(start < data.size()){
			if(BATCH_SAVE_TYPE_INSERT.equals(saveType)){
				this.getSqlSession().insert(saveId, data.subList(start, end));
			}else{
				this.getSqlSession().update(saveId, data.subList(start, end));
			}
			start = end;
			if(start+BATCH_COUNT<data.size()){
				end = start+BATCH_COUNT;
			}else{
				end = data.size();
			}
		}
		return data.size();
	}
	
	

}

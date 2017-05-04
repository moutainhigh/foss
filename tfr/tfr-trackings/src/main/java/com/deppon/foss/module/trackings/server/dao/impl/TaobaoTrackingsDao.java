package com.deppon.foss.module.trackings.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao;
import com.deppon.foss.module.trackings.api.shared.domain.TrackExternalLogEntity;
import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsRequestDto;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;

public class TaobaoTrackingsDao extends iBatis3DaoImpl implements
		ITaobaoTrackingsDao {
	private static final String NAMESPACE = "foss.trackings.taobaoTrackings.";
	private IBatchSaveProcessDao batchSaveProcessDao;
	public void setBatchSaveProcessDao(IBatchSaveProcessDao batchSaveProcessDao) {
		this.batchSaveProcessDao = batchSaveProcessDao;
	}

	/**
	 * 
	 * <p>轨迹表中更新jobid</p> 
	 * @author alfred
	 * @date 2015-4-28 下午7:42:47
	 * @param jobId
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao#updateAndGetJobId(java.lang.String, int)
	 */
	@Override
	public int updateAndGetJobId(String jobId, int limit) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", jobId);
		map.put("limit", limit);
		return this.getSqlSession().update(NAMESPACE+"updateAndGetJobId", map);
	}

	/**
	 * 
	 * <p>根据jobid查询待推送数据</p> 
	 * @author alfred
	 * @date 2015-4-28 下午7:42:52
	 * @param jobId
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao#queryTrackingsInfobyJobId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TaobaoTrackingsRequestDto> queryTrackingsInfobyJobId(
			String jobId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryTrackingsInfobyJobId", jobId);
	}

	/**
	 * 
	 * <p>异常情况下重置</p> 
	 * @author alfred
	 * @date 2015-4-29 下午4:26:35
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao#resetMsgbyJobId()
	 */
	@Override
	public int resetMsgbyJobId() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("jobId", "N/A");
		return this.getSqlSession().update(NAMESPACE+"resetMsgbyJobId", map);
	}

	/**
	 * 
	 * <p>批量更新没有处理完成数据的jobid为N/A</p> 
	 * @author alfred
	 * @date 2015-4-29 下午7:58:54
	 * @param dtos
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao#updateTrackingsMsg(java.util.List)
	 */
	@Override
	public int updateTrackingsMsgs(List<TaobaoTrackingsRequestDto> dtos) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_UPDATE, 
				NAMESPACE+"updateTrackingsMsg", dtos);
	}

	/**
	 * 
	 * <p>处理成功后删除数据</p> 
	 * @author alfred
	 * @date 2015-4-29 下午8:38:54
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao#deleteMsgById(java.lang.String)
	 */
	@Override
	public int deleteMsgById(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteMsgById", id);
	}

	/**
	 * 
	 * <p>更新没有处理完成数据的jobid为N/A</p> 
	 * @author alfred
	 * @date 2015-4-29 下午8:44:24
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao#updateTrackingsMsg(java.lang.String)
	 */
	@Override
	public int updateTrackingsMsg(String id) {
		return this.getSqlSession().update(NAMESPACE+"updateTrackingsMsg", id);
	}

	/**
	 * 
	 * <p>插入推送轨迹日志</p> 
	 * @author alfred
	 * @date 2015-5-6 上午11:01:25
	 * @param logs
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.dao.ITaobaoTrackingsDao#addTrackLogs(java.util.List)
	 */
	@Override
	public int addTrackLogs(List<TrackExternalLogEntity> logs) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT, 
				NAMESPACE+"addTrackLogs", logs);
	}

	
}

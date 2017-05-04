package com.deppon.foss.module.trackings.api.server.dao;

import java.util.List;

import com.deppon.foss.module.trackings.api.shared.domain.TrackExternalLogEntity;
import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsRequestDto;

public interface ITaobaoTrackingsDao {
	public int updateAndGetJobId(String jobid,int limit);
	public List<TaobaoTrackingsRequestDto> queryTrackingsInfobyJobId(String jobId);
	public int resetMsgbyJobId();
	public int updateTrackingsMsgs(List<TaobaoTrackingsRequestDto> dtos);
	public int updateTrackingsMsg(String id);
	public int deleteMsgById(String id);
	public int addTrackLogs(List<TrackExternalLogEntity> logs);
}

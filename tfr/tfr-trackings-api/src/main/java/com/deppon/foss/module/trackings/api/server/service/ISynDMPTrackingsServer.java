package com.deppon.foss.module.trackings.api.server.service;

import java.util.List;

import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingsToWQSEntity;

/**
 * DMP大件家装同步轨迹信息TO FOSS-WQS Server层
 * @author 362917
 *
 */
public interface ISynDMPTrackingsServer {
	/**
	 * 添加大件家装轨迹信息进对应WQS的表
	 */
      public void addWQSTrackings(DMPSynTrackingsToWQSEntity trackingsEntity);
      /**
 	  * 查询同步到WQS表的DMP大件家装轨迹信息(根据运单号+货物当前状态)
 	  */
	  public List<DMPSynTrackingsToWQSEntity> queryWQSTrackings(DMPSynTrackingsToWQSEntity trackingsEntity);
	 	/**
	 	  *  删除同步到FOSS的重复内部轨迹表的DMP大件家装轨迹信息
	 	  */	
	  public int deleteWQSTrackings(String wayBillNo, String eventType);
}

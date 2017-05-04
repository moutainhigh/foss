package com.deppon.foss.module.trackings.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.trackings.api.server.dao.ISynDMPTrackingsDao;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingsToWQSEntity;

public class SynDMPTrackingsDao extends iBatis3DaoImpl implements ISynDMPTrackingsDao{
	private static final String NAMESPACE = "foss.trackings.DMPTrackingsWQS.";
	/**
	 * 添加大件家装轨（DMP）迹信息进对应WQS的表
	 */
	@Override
	public void addWQSTrackings(DMPSynTrackingsToWQSEntity trackingsEntity) {
		this.getSqlSession().insert(NAMESPACE+"addWQSTrackings",trackingsEntity );
		
	}
	 /**
	  * 查询同步到WQS表的DMP大件家装轨迹信息(根据运单号+货物当前状态)
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<DMPSynTrackingsToWQSEntity> queryWQSTrackings(DMPSynTrackingsToWQSEntity trackingsEntity) {
		return  this.getSqlSession().selectList(NAMESPACE+"queryWQSTrackings", trackingsEntity);

	}
	/**
	  *  删除同步到WQS的重复内部轨迹表的DMP大件家装轨迹信息
	  */	
	@Override
	public int deleteWQSTrackings(String wayBillNo, String eventType) {
		DMPSynTrackingsToWQSEntity entity=new DMPSynTrackingsToWQSEntity();
		entity.setWayBillNo(wayBillNo);
		entity.setEventType(eventType);
		return this.getSqlSession().delete(NAMESPACE+"deleteWQSTrackings",entity);
	}

}

package com.deppon.foss.module.trackings.server.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.trackings.api.server.dao.ISynTrackingDao;
import com.deppon.foss.module.trackings.api.server.service.ISynTrackingServer;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingEntity;

public class SynTrackingServer implements ISynTrackingServer{
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(SynDMPTrackingsService.class);

	private ISynTrackingDao synTrackingDao;
	/**
    * 添加DMP大件家装同步轨迹信息FOSS的内部轨迹表
    */
	@Override
	public void addDMPTrackings(DMPSynTrackingEntity trackingsEntity) {
		synTrackingDao.addDMPTrackings(trackingsEntity);
		
	}
	 /**
	  * 查询同步到FOSS的内部轨迹表的DMP大件家装轨迹信息(根据运单号+货物当前状态)
	  */
	@Override
	public List<DMPSynTrackingEntity> queryDMPTrackings(DMPSynTrackingEntity synTrackingEntity) {
		return synTrackingDao.queryDMPTrackings(synTrackingEntity);
		
	}
	/**
	  * 查询同步到FOSS的内部轨迹表的DMP大件家装轨迹信息(根据运单号)
	  */
	public List<DMPSynTrackingEntity> queryDMPTrackingsByWayBillNo(String wayBillNo){
		return synTrackingDao.queryDMPTrackingsByWayBillNo(wayBillNo);
		
	}
	
	
	public ISynTrackingDao getSynTrackingDao() {
		return synTrackingDao;
	}

	public void setSynTrackingDao(ISynTrackingDao synTrackingDao) {
		this.synTrackingDao = synTrackingDao;
	}

}

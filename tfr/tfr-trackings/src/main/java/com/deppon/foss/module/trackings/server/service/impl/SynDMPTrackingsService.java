package com.deppon.foss.module.trackings.server.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.trackings.api.server.dao.ISynDMPTrackingsDao;
import com.deppon.foss.module.trackings.api.server.service.ISynDMPTrackingsServer;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingsToWQSEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;


public class SynDMPTrackingsService implements ISynDMPTrackingsServer{
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(SynDMPTrackingsService.class);
	private ISynDMPTrackingsDao synDMPTrackingsDao;
	/**
	 * 添加大件家装轨迹信息进对应WQS的表
	 */
	@Override
	public void addWQSTrackings(DMPSynTrackingsToWQSEntity trackingsEntity) {
		if(trackingsEntity==null){
			throw new TfrBusinessException("DMP大件家装轨迹参数为空");
		}
		if(StringUtils.isBlank(trackingsEntity.getWayBillNo())){
			throw new TfrBusinessException("DMP大件家装轨迹的运单号不能为空");
		}
		synDMPTrackingsDao.addWQSTrackings(trackingsEntity);
		
	}
	 /**
	  * 查询同步到WQS表的DMP大件家装轨迹信息(根据运单号+货物当前状态)
	  */
	public List<DMPSynTrackingsToWQSEntity> queryWQSTrackings(DMPSynTrackingsToWQSEntity trackingsEntity){
		return synDMPTrackingsDao.queryWQSTrackings(trackingsEntity);
	}
	/**
	  *  删除同步到FOSS的重复内部轨迹表的DMP大件家装轨迹信息
	  */	
	public int deleteWQSTrackings(String wayBillNo, String eventType){
		return synDMPTrackingsDao.deleteWQSTrackings(wayBillNo,eventType);
	}
	
	
	public ISynDMPTrackingsDao getSynDMPTrackingsDao() {
		return synDMPTrackingsDao;
	}
	public void setSynDMPTrackingsDao(ISynDMPTrackingsDao synDMPTrackingsDao) {
		this.synDMPTrackingsDao = synDMPTrackingsDao;
	}
}

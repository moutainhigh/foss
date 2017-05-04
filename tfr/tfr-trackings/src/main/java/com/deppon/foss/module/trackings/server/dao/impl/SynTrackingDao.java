package com.deppon.foss.module.trackings.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.trackings.api.server.dao.ISynTrackingDao;
import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingEntity;

public class SynTrackingDao extends iBatis3DaoImpl implements ISynTrackingDao{
	private static final String NAMESPACE = "foss.trackings.DMPTrackingsToFoss.";

	/**
    * 添加DMP大件家装同步轨迹信息FOSS的内部轨迹表
    */
	@Override
	public void addDMPTrackings(DMPSynTrackingEntity trackingsEntity) {
		
		this.getSqlSession().insert(NAMESPACE+"addSynTracking",trackingsEntity );
		
	}

	 /**
	  * 查询同步到FOSS的内部轨迹表的DMP大件家装轨迹信息(根据运单号+货物当前状态)
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<DMPSynTrackingEntity> queryDMPTrackings(DMPSynTrackingEntity synTrackingEntity) {
		
		return  this.getSqlSession().selectList(NAMESPACE + "querySynTracking", synTrackingEntity);
		
	}
	
	/**
	  * 查询同步到FOSS的内部轨迹表的DMP大件家装轨迹信息(根据运单号)
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<DMPSynTrackingEntity> queryDMPTrackingsByWayBillNo(String wayBillNo) {
		
		return  this.getSqlSession().selectList(NAMESPACE + "querySynTrackingsByWayBillNo", wayBillNo);

	}
	/**
	 * 同步DMP更改的信息之前删除原有轨迹信息
	 */
	@Override
	public int deleteDMPTrackings(String wayBillNo, String currentStatus) {
		DMPSynTrackingEntity entity=new DMPSynTrackingEntity();
		entity.setCurrentStatus(currentStatus);
		entity.setWayBillNo(wayBillNo);
		return this.getSqlSession().delete(NAMESPACE+"deleteSynTracking",entity);
	}
	
	

	
}

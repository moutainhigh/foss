package com.deppon.foss.module.trackings.api.server.dao;

import java.util.List;

import com.deppon.foss.module.trackings.api.shared.domain.DMPSynTrackingEntity;

/**
 * DMP大件家装同步轨迹信息FOSS Server层
 * @author 362917
 *
 */
public interface ISynTrackingDao {

	/**
    * 添加DMP大件家装同步轨迹信息FOSS的内部轨迹表
    */
	public void addDMPTrackings(DMPSynTrackingEntity trackingsEntity);
	 /**
	  * 查询同步到FOSS的内部轨迹表的DMP大件家装轨迹信息(根据运单号+货物当前状态)
	  */
	public List<DMPSynTrackingEntity> queryDMPTrackings(DMPSynTrackingEntity synTrackingEntity);
	/**
	  * 查询同步到FOSS的内部轨迹表的DMP大件家装轨迹信息(根据运单号)
	  */
	public List<DMPSynTrackingEntity> queryDMPTrackingsByWayBillNo(String wayBillNo);
	/**
	  *  删除同步到FOSS的重复内部轨迹表的DMP大件家装轨迹信息
	  */	
	public int deleteDMPTrackings(String wayBillNo,String currentStatus);
}

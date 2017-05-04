package com.deppon.foss.module.trackings.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.trackings.api.server.dao.IPushTrackForCaiNiaoDao;
import com.deppon.foss.module.trackings.api.shared.domain.ExpressTrackExternalEntity;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.domain.TrackExternalLogEntity;


public class PushTrackForCaiNiaoDao extends iBatis3DaoImpl implements  IPushTrackForCaiNiaoDao{

	private static final String NAMESPACE = "foss.tfr.pushtrackforcainiao.";
	
	
	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	public void addCarGoTrack(ExpressTrackExternalEntity entity){
		
		this.getSqlSession().insert(NAMESPACE+"addCarGoTrack", entity);
	}
	
	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entityList 快递轨迹实体
	 * @return
	 ****/
	public void addBatchCarGoTrack(List<ExpressTrackExternalEntity> entityList){
		
		this.getSqlSession().insert(NAMESPACE+"addBatchCarGoTrack", entityList);

	}
	
	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	public void addSynTrack(SynTrackingEntity entity){
		
		this.getSqlSession().insert(NAMESPACE+"addSynTrack", entity);
	}
	
	/***
	  *插入快递相关轨迹到货物表(揽派收)
	 * @author 311396-wwb
	 * @date 2016年9月26日14:26:26
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	public void addSynLpsTrack(SynTrackingEntity entity){
		
		this.getSqlSession().insert(NAMESPACE+"addSynLpsTrack", entity);
	}
	
	/***
	 *在绑定快递代理单号的时候将信息插入到相关轨迹的表中
	 * @author 268084
	 * @date 2016-03-17上午10:11:20
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	@Override
	public void addSynTrackToWQS(SynTrackingEntity entity){
		System.out.println("开始插入同步轨迹@@@@@@@@@@@@@@");
		this.getSqlSession().insert(NAMESPACE+"addSynTrackToWQS", entity);
		System.out.println("开始插入同步轨迹结束@@@@@@@@@@@@@@");
	}
	
	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entityList 快递轨迹实体
	 * @return
	 ****/
	public void addBatchSynTrack(List<SynTrackingEntity> entityList){
		
		this.getSqlSession().insert(NAMESPACE+"addBatchSynTrack", entityList);

	}
	
	/***
	 *插入快递相关轨迹到货物表(揽派收)
	 * @author 311396-wwb
	 * @date 2016年9月26日14:26:26
	 * @param entityList 快递轨迹实体
	 * @return
	 ****/
	public void addBatchSynLpsTrack(List<SynTrackingEntity> entityList){
		
		this.getSqlSession().insert(NAMESPACE+"addBatchSynLpsTrack", entityList);

	}
	
	/**
	 * 
	 * <p>校验轨迹记录是否存在</p> 
	 * @author alfred
	 * @date 2015-5-8 上午8:46:24
	 * @param logEntity
	 * @return 
	 * @see com.deppon.foss.module.trackings.api.server.dao.IPushTrackForCaiNiaoDao#checkExistsTrack(com.deppon.foss.module.trackings.api.shared.domain.TrackExternalLogEntity)
	 */
	@Override
	public boolean checkExistsTrack(TrackExternalLogEntity logEntity) {
		int count = (Integer) this.getSqlSession().selectOne(NAMESPACE + "checkExistsTrack",logEntity);
		return count > 0;
	}
}

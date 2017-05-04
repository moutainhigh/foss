package com.deppon.foss.module.trackings.api.server.service;

import java.util.List;

import com.deppon.foss.module.trackings.api.shared.domain.ExpressTrackExternalEntity;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;



/***
 * 货物轨迹推送接口
 *货物轨迹信息存储到异步表，以便后续推送
 *用于不同 业务场景的货物轨迹存储service
 * @author 205109-
 * @date 2015-04-27 上午11:13:20
 ****/
public interface IPushTrackForCaiNiaoService {
	
	
	
	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	public void addCarGoTrack(ExpressTrackExternalEntity entity);
	

	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entityList 快递轨迹实体list
	 * @return
	 ****/
	public void addBatchCarGoTrack(List<ExpressTrackExternalEntity> entityList);
	
	
	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entity taobao快递轨迹实体
	 * @return
	 ****/
	public void addSynTrack(SynTrackingEntity entity);
	
	
	
	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entity taobao快递轨迹实体
	 * @return
	 ****/
	public void addBatchSynTrack(List<SynTrackingEntity> entity);
	

	/***
	 *插入快递相关轨迹到货物表(揽派收)
	 * @author 311396-wwb
	 * @date 2016年9月26日14:26:26
	 * @param entity taobao快递轨迹实体
	 * @return
	 ****/
	public void addSynLpsTrack(SynTrackingEntity entity);
	
	
	
	/***
	 *插入快递相关轨迹到货物表(揽派收)
	 * @author 311396-wwb
	 * @date 2016年9月26日14:26:31
	 * @param entity taobao快递轨迹实体
	 * @return
	 ****/
	public void addBatchSynLpsTrack(List<SynTrackingEntity> entity);
	
	/***
	 *在绑定快递代理单号的时候将信息插入到相关轨迹的表中
	 * @author 268084
	 * @date 2016-03-17上午10:11:20
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	void addSynTrackToWQS(SynTrackingEntity entity);
	
	
	
	
	
	
	/**
	 * 
	 * <p>提供方法给异常库存，判定是否推动轨迹</p> 
	 * @author alfred
	 * @date 2015-5-6 下午4:16:44
	 * @see
	 */
	public void judgeTrakcForExpctionStock(List<String> waybillNos);
}

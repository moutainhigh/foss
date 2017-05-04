package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.IPushCarGoTrackDao;

public class PushCarGoTrackDao extends iBatis3DaoImpl implements  IPushCarGoTrackDao{

	private static final String NAMESPACE = "foss.tfr.pushcargotrack.";
	
	/***
	 *装车派件扫描货物轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到新轨迹表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-07-09 上午11:13:20
	 * @param taskNo 派送装车任务号
	 * @return
	 ****/
	public void pushSentScanTrackNew(String taskNo,String orderChannel){
		Map<String, String> params = new HashMap<String, String>();
		params.put("taskNo", taskNo);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushSentScanTrackNew", params);
	}
	
	
	/**
	 * @description
	 * 装车派件扫描货物轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* @param taskNo
	* @param productSwitch  运输类型开关:如果为空,则为快递产品
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午5:48:53
	* @version V1.0
	*/
	@Override
	public void pushSentScanTrack(String taskNo, String orderChannel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("taskNo", taskNo);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushSentScanTrack", params);
	}

	/***
	 *装车派件扫描货物轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-27 上午11:13:20
	 * @param truckDetailId 车辆明细任务id
	 * @return
	 ****/
	public void pushSentCityTrackNew(String truckDetailId,String orderChannel ){
		Map<String, String> params = new HashMap<String, String>();
		params.put("truckDetailId", truckDetailId);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushSentCityTrackNew", params);
	}

	
	/**
	 *货物到达轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* @description 货物到达轨迹推送
	* @param taskNo
	* @param orderChannel  订单来源
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月20日 下午7:06:52
	*/
	@Override
	public void pushArrivalTrack(String taskNo, String orderChannel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("taskNo", taskNo);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushArrivalTrack",params);
	}
	
	
	/**
	 *货物到达轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* @description 货物到达轨迹推送
	* @param taskNo
	* @param orderChannel  订单来源
	* @version 1.0
	* @author 205109-foss-zenghaibin
	* @update 2015年7月10日 下午15:37:52
	*/
	@Override
	public void pushArrivalTrackNew(String taskNo, String orderChannel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("taskNo", taskNo);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushArrivalTrackNew",params);
	}

	
	/**
	* @description 货物出发轨迹推送
	*1.先去查询未执行的job数据
    *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.IPushCarGoTrackDao#pushDepartureTrack(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月25日 下午2:49:17
	* @version V1.0
	*/
	@Override 
	public void pushDepartureTrack(String taskNo, String orderChannel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("taskNo", taskNo);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushDepartureTrack",params);
	}
	

	/**
	* @description 货物出发轨迹推送
	*1.先去查询未执行的job数据
    *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.IPushCarGoTrackDao#pushDepartureTrack(java.lang.String, java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月25日 下午2:49:17
	* @version V1.0
	*/
	@Override 
	public void pushDepartureTrackNew(String taskNo, String orderChannel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("taskNo", taskNo);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushDepartureTrackNew",params);
	}
	
	/**
	* @description 重置jobtodo表中的异常数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.IPushCarGoTrackDao#reSetJobMsgbyJobId()
	* @author 218381-foss-lijie
	* @update 2015年5月29日 上午8:43:25
	* @version V1.0
	*/
	@Override
	public int reSetJobMsgbyJobId() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("jobId", "N/A");
		return this.getSqlSession().update(NAMESPACE+"resetMsgbyJobId", params);
	}

	

	/**
	* @description 直发中转场提交任务时生成出站轨迹，此出发非彼出发，用心去体会吧
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.IPushCarGoTrackDao#pushStraightDepartureTrack(java.lang.String, java.lang.String)
	* @author 276198-foss-duhao
	* @update 2015-10-23 下午2:20:12
	* @version V1.0
	*/
	@Override
	public void pushStraightDepartureTrack(String taskNo, String orderChannel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("taskNo", taskNo);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushStraightDepartureTrack",params);
		
	}


	
	/**
	* @description 外场分配快递集中接货卸车任务时候生成，此到达非彼到达，请参照出发
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.dao.IPushCarGoTrackDao#pushStraightArrivalTrack(java.lang.String, java.lang.String)
	* @author 276198-foss-duhao
	* @update 2015-10-23 下午2:20:15
	* @version V1.0
	*/
	@Override
	public void pushStraightArrivalTrack(String taskNo, String orderChannel) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("taskNo", taskNo);
		params.put("orderChannel", orderChannel);
		this.getSqlSession().insert(NAMESPACE+"pushStraightArrivalTrack",params);
		
	}


}

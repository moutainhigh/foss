package com.deppon.foss.module.transfer.common.api.server.dao;


/***
 * 货物轨迹推送接口
 *货物轨迹信息存储到异步表，以便后续推送
 *用于不同 业务场景的货物轨迹存储service
 * @author 205109-
 * @date 2015-04-27 上午11:13:20
 ****/
public interface IPushCarGoTrackDao {
	
	
	/***
	 *装车派件扫描货物轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-27 上午11:13:20
	 * @param taskNo 派送装车任务号
	 * @return
	 ****/
	public void pushSentScanTrackNew(String taskNo,String orderChannel);
	
	/**
	 *货物到达轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* @description 货物到达轨迹推送
	* @param taskNo
	* @param orderChannel  订单来源
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月20日 下午5:06:52
	*/
	public void pushArrivalTrack(String taskNo,String orderChannel);
	
	
	/**
	 *货物到达轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* @description 货物到达轨迹推送
	* @param taskNo
	* @param orderChannel  订单来源
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月20日 下午5:06:52
	*/
	public void pushArrivalTrackNew(String taskNo,String orderChannel);
	
	/**
	 * @description
	 * 装车派件扫描货物轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* @param taskNo
	* @param productSwitch  运输类型开关:如果为空,则为快递产品
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月20日 下午5:42:57
	*/
	public void pushSentScanTrack(String taskNo,String orderChannel);
	

	/**
	* @description 货物出发轨迹推送
	* 1.先去查询未执行的job数据
	*2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* @param taskNo
	* @param orderChannel  运输类型开关:如果为空,则为快递产品
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月25日 下午2:46:25
	*/
	public void pushDepartureTrack(String taskNo,String orderChannel);

	/**
	* @description 货物出发轨迹推送
	* 1.先去查询未执行的job数据
	*2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	* @param taskNo
	* @param orderChannel  运输类型开关:如果为空,则为快递产品
	* @version 1.0
	* @author 205109-foss-zenghaibin
	* @update 2015年5月25日 下午2:46:25
	*/
	public void pushDepartureTrackNew(String taskNo,String orderChannel);

	
	/***
	 *装车派件扫描货物轨迹推送
	 *1.先去查询未执行的job数据
	 *2.然后根据这些数据，查询出先关信息，然后插入到信息推送表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-27 上午11:13:20
	 * @param taskNo 派送装车任务号
	 * @return
	 ****/
	public void pushSentCityTrackNew(String truckDetailId,String orderChannel);
	
	
	/**
	* @description 直发中转场提交任务时生成出站轨迹，此出发非彼出发，用心去体会吧
	* @param taskNo
	* @param orderChannel
	* @version 1.0
	* @author 276198-foss-duhao
	* @update 2015-10-23 下午2:17:52
	*/
	public void pushStraightDepartureTrack(String taskNo,String orderChannel);
	
	/**
	* @description 外场分配快递集中接货卸车任务时候生成，此到达非彼到达，请参照出发
	* @param taskNo
	* @param orderChannel
	* @version 1.0
	* @author 276198-foss-duhao
	* @update 2015-10-23 下午2:18:02
	*/
	public void pushStraightArrivalTrack(String taskNo,String orderChannel);
	
	
	
	/**
	* @description 重置jobtodo表中的异常数据
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月29日 上午8:42:19
	*/
	public int reSetJobMsgbyJobId();

}

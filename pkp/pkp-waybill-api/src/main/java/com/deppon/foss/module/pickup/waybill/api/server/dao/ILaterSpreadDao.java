package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.LaterSpreadEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LaterSpreadDto;

public interface ILaterSpreadDao {
	
	void save(LaterSpreadEntity e);
	
	/**
	 * 
	* @Description: 修改这次处理的job数量
	* @author hbhk 
	* @date 2015-7-1 上午8:00:28 
	  @param jobId
	  @param num
	  @return
	 */
	LaterSpreadDto updateLaterSpreadForJobTopNum(String jobId,int num);
	

	/**
	 * 
	* @Description:  根据jobId查询待处理返券信息
	* @author hbhk 
	* @date 2015-7-1 上午8:00:07 
	  @param jobId
	  @return
	 */
	List<LaterSpreadEntity> queryLaterSpreadByJobID(String jobId);
	/**
	 * 
	* @Description: 执行发送短信失败
	* @author hbhk 
	* @date 2015-7-1 上午8:09:55 
	  @param e
	 */
	void   executeSendSMSFail(LaterSpreadEntity e);
	/**
	 * 
	* @Description: 动态查询 
	* @author hbhk 
	* @date 2015-7-16 上午8:16:06 
	  @param laterSpread
	  @return
	 */
	List<LaterSpreadEntity> queryLaterSpreadList(LaterSpreadEntity laterSpread);
	/**
	 * 
	* @Description: 查询运单是否修改目的站 
	* @author hbhk 
	* @date 2015-7-17 上午8:46:27 
	  @param waybillNo
	  @return
	 */
	Long queryWaybillChangeDestinationAndReceiveMethod(String waybillNo);
	
	/**
	 * 
	* @Description: 查询运单是否修改提货方式
	* @author hbhk 
	* @date 2015-7-17 上午8:46:27 
	  @param waybillNo
	  @return
	 */
	Long queryWaybillChangeReceiveMethod(String waybillNo);
	
}

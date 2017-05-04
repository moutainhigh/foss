package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.AccessPointDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;

/**
 * 
 * @author alfred
 * 二程接驳，提供装车接口给PDA
 */
public interface IPDAExpressConnectionService {

	/**@see 新建司机装车任务
	 * @return 返回任务号
	 **/
	public String createDriverTask(LoadTaskDto loadTask);
	
	
	/****
	 * <p>获取进行中、未开始的司机装车任务</p> 
	 * @author alfred
	 * @date 2015-5-15 下午3:14:29
	 * @param condition
	 * @return
	 * @see
	 */
	public List<PDAAssignLoadTaskEntity> queryUnfinishDriverLoadTask(
			QueryAssignedLoadTaskEntity condition);
	
	/**
	 * 
	 * <p>提交司机装车任务</p> 
	 * @author alfred
	 * @date 2015-5-7 上午9:15:35
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @return
	 * @see
	 */
	public String submitDriverLoadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode);
	
	/**
	 * 
	 * <p>取消司机装车任务</p> 
	 * @author alfred
	 * @date 2015-8-4 下午3:33:28
	 * @param taskNo
	 * @return
	 * @see
	 */
	public String cancelDriverLoadTask(String taskNo);
	
	/**@see 新建理货员装车任务
	 * @return 返回任务号
	 **/
	public LoadTaskResultDto createConnectionTask(LoadTaskDto loadTask);
	
	/**
	 * 
	 * <p>点击完成接货，把多个交接任务合成一个交接单</p> 
	 * @author alfred
	 * @date 2015-4-17 上午10:54:23
	 * @return
	 * @see
	 */
	public String finishLoadTask(List<String> taskNo,String vehicleNo);
	
	/**
	 * 
	 * <p>理货员提交装车任务</p> 
	 * @author alfred
	 * @date 2015-4-21 下午5:15:21
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @return
	 * @see
	 */
	public String submitConnectionLoadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode);
	
	
	/**
	 * 
	 * <p>根据外场找接驳点</p> 
	 * @author alfred
	 * @date 2015-5-7 上午9:14:27
	 * @param transferCode
	 * @return
	 * @see
	 */
	public List<AccessPointDto> queryAccessPointsByTransferCode(String transferCode);
	
	
	/****
	 * <p>获取进行中、未开始的接驳装车任务</p> 
	 * @author alfred
	 * @date 2015-5-15 下午3:14:29
	 * @param condition
	 * @return
	 * @see
	 */
	public List<PDAAssignLoadTaskEntity> queryUnfinishLoadTask(
			QueryAssignedLoadTaskEntity condition);
}

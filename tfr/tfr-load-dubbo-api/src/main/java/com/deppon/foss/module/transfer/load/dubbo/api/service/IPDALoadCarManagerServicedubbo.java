package com.deppon.foss.module.transfer.load.dubbo.api.service;

import com.deppon.foss.module.transfer.load.dubbo.api.define.*;
import com.deppon.foss.module.transfer.load.dubbo.api.exception.TfrLoadException;

import java.util.Date;
import java.util.List;

/**
 * 装车管理服务类
 * @author 332209
 *
 */
public interface IPDALoadCarManagerServicedubbo {

	/**
	 * 校验车牌号
	 * @return
	 * @throws TfrLoadException 
	 */
	CheckVehicleNoResultDto checkPdaVehicleNo(LoadTaskDto wkLoadTask) throws TfrLoadException;
	
	/**
	 * 创建装车任务
	 * @param wkLoadTask
	 * @return
	 */
	LoadTaskResultDto createLoadTask(LoadTaskDto wkLoadTask) throws TfrLoadException;
	
	/**
	 * PDA提交时的强制校验
	 * @param taskNo
	 * @return
	 */
	PDADubboResultDto pdaSubmitForceCheck(String taskNo);
	
	/**
	 * 增删理货员
	 * @param taskNo
	 * @param loaderCodes
	 * @param operateTime
	 * @param loaderState
	 * @return
	 */
	String modifyLoader(String taskNo, List<LoaderDto> loaderCodes, Date operateTime, String loaderState) throws TfrLoadException;
	
	/**
	 * 完成装车
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 */
	String finishLoadTask(String taskNo, Date loadEndTime, String deviceNo, String loaderCode) throws TfrLoadException;
	
	/**
	 * 建立任务PDA提交任务需要同步给悟空
	 * @param dto
	 * @return
	 */
	String submitLoadTask(SubmitLoadTaskDto dto) throws TfrLoadException;
	
	/**
	 * 撤销卸车任务
	 * @param taskNo
	 * @param deviceNo
	 * @param operatorCode
	 * @param cancelTime
	 * @return
	 */
	String cancelLoadTask(String taskNo, String deviceNo, String operatorCode, Date cancelTime) throws TfrLoadException;
	
	/**
	 * 查询装车任务列表
	 */
	List<PDAAssignLoadTaskEntity> queryAssignedLoadTask(QueryAssignedLoadTaskEntity condition) throws TfrLoadException;

	/**
	 * 根据任务号查询扫描总件数
	 */
	int queryScanQty(String taskNo);
	
	/**
	 * 装车扫描同步接口
	 * @param scanRecord
	 * @return
	 */
	String loadScan(LoadScanDetailDubboDto scanRecord);

}
/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: IPDAUnloadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDApreplatformEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskResultDto;

/**
 * PDA接口：卸车任务service
 * @author dp-duyi
 * @date 2012-11-28 上午10:10:11
 */
public interface IPDAUnloadTaskService {
	//查询已分配卸车任务：未开始、进行中
	public List<PDAAssignUnloadTaskEntity> pdaQueryAssignedUnloadTask(String loaderCode,String loadOrgCode);
	//建立卸车任务
	public UnloadTaskResultDto createLoadTask(UnloadTaskDto unloadTask);
	//刷新卸车任务
	public List<UnloadGoodsDetailDto> refrushUnloadTaskDetail(String taskNo);
	//中途添加/删除理货员
	public String modifyLoader(String taskNo,List<LoaderDto> loaderCode,Date operateTime,String loaderState);
	//撤销装车任务
	public String cancelUnloadTask(String taskNo,String deviceNo,String operatorCode,Date cancelTime);
	//非建立任务PDA完成任务
	public String finishUnloadTask(String taskNo,Date loadEndTime,String deviceNo);
	//建立任务PDA提交任务
	public String submitUnloadTask(String taskNo,Date loadEndTime,String deviceNo,String loaderCode,String beException,String notes);
	//强制提交任务
	public String forceSubmitUnloadTask(String taskNo,Date loadEndTime,String deviceNo,String loaderCode,String beException,String notes);
	//卸车扫描（现用）
	public String unloadScan(UnloadScanDetailDto scanRecord);
	//卸车多货校验
	public UnloadGoodsDetailDto moreGoodsUnloadVerify(String taskNo,String wayBillNo,String serialNo);
	//刷新卸车任务包明细
	public List<UnloadGoodsDetailDto> refrushUnloadTaskPackage(String taskNo);
	//卸车单据扫描 (占时没有用)
	public String unloadBillScan(UnloadScanDetailDto scanRecord);
	/**
	 * @desc 提供给PDA 查询待卸车辆预分配月台情况
	 * @param currOrgCode 当前部门编码
	 * @param vehicleNo 车牌号  (非必填)
	 * @param platformNo 月台号(非必填)
	 * @param businessType 业务类型(必填)  长途:LONG_DISTANCE ,短途:SHORT_DISTANCE,接送货：DIVISION
	 * @param enterTfrCenterType 入场情况(必填) 已入场 IN,未入场 OUT
	 * @author 105795
	 * @date 2015-05-09
	 * */
	public List<PDApreplatformEntity> queryPreplatformNo(String currOrgCode,String vehicleNo,String platformNo,String businessType,String enterTfrCenterType);
	
}

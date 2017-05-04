/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/dao/IUnloadTaskDao.java
 *  
 *  FILE NAME          :IUnloadTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.server.dao
 * FILE    NAME: IUnloadTaskDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.DeleteFromUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDApreplatformDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PlanUnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryArrivedBillInfoByNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryArrivedBillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;

/**
 * 卸车
 * @author dp-duyi
 * @date 2012-11-26 上午10:25:53
 */
public interface IUnloadTaskDao {
	//接送货接口:根据运单号查询货物交接、卸车情况
	public List<HandOverAndUnloadDto> queryHandOverAndUnloadByWayBillNo(String wayBillNo);
	
	/**
	 * 新增卸车任务时，“快速添加”时，根据车牌号获取本部门待卸的所有单据编号及单据类型list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午4:09:54
	 */
	List<QueryArrivedBillNoDto> queryArrivedBillNoList(String orgCode,String vehicleNo);
	
	/**
	 * 根据交接单号获取待卸的交接单信息list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午4:38:20
	 */
	List<PlanUnloadBillDto> queryArrivedHandOverBillInfoByNo(QueryArrivedBillInfoByNoDto noDto);
	
	/**
	* @description 根据交接单号获取待卸的快递交接单信息list
	* @author 328768-foss-gaojianfu
	* @update 2016年5月27日 下午11:08:33
	 */
	List<PlanUnloadBillDto>  queryArrivedExpressHandOverBillInfoByNo(QueryArrivedBillInfoByNoDto nosDto);
	
	/**
	 * 根据配载车次号获取待卸的配载单信息list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午4:38:20
	 */
	List<PlanUnloadBillDto> queryArrivedVehicleAssembleBillInfoByNo(QueryArrivedBillInfoByNoDto noDto);
	
	/**
	 * 新增卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:15:04
	 */
	int addUnloadTaskBasicInfo(UnloadTaskEntity baseEntity);
	
	/**
	 * 新增卸车任务单据信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:17:02
	 */
	int addUnloadTaskBillDetailList(List<UnloadBillDetailEntity> billDetailList);
	
	/**
	 * 新增卸车任务运单明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:18:27
	 */
	int addUnloadTaskWaybillDetailList(List<UnloadWaybillDetailEntity> waybillDetailList);
	
	/**
	 * 新增卸车任务流水号明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:19:21
	 */
	int addUnloadTaskSerialNoDetailList(List<UnloadSerialNoDetailEntity> serialNoDetailList);
	
	/**
	 * 新增理货员参与卸车情况
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:21:30
	 */
	int addLoaderParticipationList(List<LoaderParticipationEntity> loaderList);
	
	/**
	 * 根据卸车任务ID获取任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:20:38
	 */
	UnloadTaskEntity queryUnloadTaskBaseInfoById(String unloadTaskId);
	
	/**
	 * 根据卸车任务ID获取其下单据列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:21:24
	 */
	List<UnloadBillDetailEntity> queryUnloadTaskBillDetailListById(String unloadTaskId);
	
	/**
	 * 根据卸车任务ID获取其下卸车员列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:22:01
	 */
	List<LoaderParticipationEntity> queryLoaderDetailListById(String unloadTaskId);
	
	/**
	 * 根据卸车任务ID获取卸车任务的创建人
	 * @author 045923-foss-shiwei
	 * @date 2013-2-20 上午11:12:22
	 */
	List<LoaderParticipationEntity> queryTaskCreatorLoaderByTaskId(String unloadTaskId);
	
	/**
	 * 批量传入单据编号和卸车任务ID，将单据从卸车任务中删除
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:05:03
	 */
	int deleteBillDetailListFromUnloadTask(DeleteFromUnloadTaskDto deleteDto);
	
	/**
	 * 批量传入卸车员code和卸车任务ID，将卸车员从卸车员参与情况表中删除
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:05:58
	 */
	int deleteLoaderListFromUnloadTask(DeleteFromUnloadTaskDto deleteDto);
	
	/**
	 * 根据ID更新卸车任务状态
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:36:54
	 */
	int updateUnloadTaskState(String unloadTaskId,String targetState);
	
	/**
	 * 根据ID更新卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:37:32
	 */
	int updateUnloadTaskBasicInfo(UnloadTaskEntity entity);
	
	/**
	 * 根据卸车任务ID删除卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午7:31:25
	 */
	int deleteUnloadTaskBasicInfo(String unloadTaskId);
	
	/**
	 * 用于确认短途卸车界面，快速查询时根据运单号获取交接单号list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 上午10:42:52
	 */
	List<String> queryHandOverBillListByWaybillNo(String unloadTaskId,String waybillNo);
	
	/**
	 * 用于从交接单获取交接单下所有运单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 上午10:14:57
	 */
	List<UnloadWaybillDetailEntity> queryUnloadTaskWaybillDetailByHandOverBillNo(String handOverBillNo);
	
	/**
	 * 用于从配载单下获取配载单下所有运单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 上午10:16:05
	 */
	List<UnloadWaybillDetailEntity> queryUnloadTaskWaybillDetailByVehicleAssembleNo(String vehicleAssembleNo);
	
	/**
	 * 根据卸车任务ID更新卸车人员参与情况的离开任务时间
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 下午7:56:27
	 */
	int updateLoaderLeaveTaskTime(String unloadTaskId,Date leaveTime);
	
	/**
	 * 手工确认卸车任务界面，添加多货时校验运单号、流水号是否合法
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午11:05:58
	 */
	int validateWaybillNoAndSerialNo(String waybillNo,String serialNo);
	
	/**
	 * 校验运单号、流水号是否在某长途卸车任务中
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午3:33:15
	 */
	int validateNosIsInLongUnloadTask(ConfirmUnloadTaskBillsDto dto);
	
	/**
	 * 校验运单号、流水号是否在某短途卸车任务中
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午3:33:59
	 */
	int validateNosIsInShortUnloadTask(ConfirmUnloadTaskBillsDto dto);
	
	/**
	 * 确认卸车任务（长途），快速定位功能，根据运单号获取运单号所在的配载单、交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午5:14:44
	 */
	List<ConfirmUnloadTaskBillsDto>queryBillNosListByWaybillNo(String unloadTaskId,String waybillNo);
	
	/**
	 * 卸车差异模块调用，看某长途卸车中多货的流水号在上一环节是否扫描
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 下午2:16:28
	 */
	List<String> queryLongDistanceLoadTaskIsScaned(ConfirmUnloadTaskBillsDto dto);
	
	/**
	 * 卸车差异模块调用，看某短途卸车中多货的流水号在上一环节是否扫描
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 下午2:16:28
	 */
	List<String> queryShortDistanceLoadTaskIsScaned(ConfirmUnloadTaskBillsDto dto);
	
	/**
	 * 根据卸车差异报告id，少货运单号、流水号获取运单所属单据
	 * @author 045923-foss-shiwei
	 * @date 2013-6-25 上午11:05:14
	 */
	List<UnloadWaybillDetailDto> queryLackGoodsBillNoByWaybillNoAndSerialNo(String reportId,String waybillNo,String serialNo);
	
	
	/**
	 * 
	 * <p>提供接口给接送货查询卸车任务，配合运单中止需求</p> 
	 * @author alfred
	 * @date 2014-5-8 下午3:43:30
	 * @param condition
	 * @return
	 * @see
	 */
	List<String> queryUnloadTaskReport(Map<String,String> condition);
	/**
	 * @author nly
	 * @date 2015年4月22日 上午10:02:06
	 * @function 根据运单号查询所有的卸车运单明细
	 * @param waybillNo
	 * @return
	 */
	List<UnloadWaybillDetailEntity> queryUnloadWaybillDetailByNo(String waybillNo);
	
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
	public List<PDApreplatformDto> queryPreplatformNo(String currOrgCode,String vehicleNo,String platformNo,String businessType,String enterTfrCenterType);

	/**
	 * 快速查询时根据商务专递运单号获取交接单号list
	 * @author chenlei 272681
	 */
	List<String> queryAirHandOverBillListByWaybillNo(String unloadTaskId,
			String waybillNo);
	/**
	 * 校验运单号、流水号是否存在于某商务专递卸车任务中
	 * @author 272681 chenlei
	 * @date 2015/9/8
	 */
	int validateNosIsInBusinessUnloadTask(ConfirmUnloadTaskBillsDto dto);
   
	/**
	 * 根据商务专递交接单号获取待卸的交接单信息list
	 * 272681chenlei
	 */
	List<PlanUnloadBillDto> queryArrivedBusinessAirBillInfoByNo(
			QueryArrivedBillInfoByNoDto noDto);

	/**
	 * 根据商务专递卸车任务ID获取其下单据列表
	 * @author 272681-foss-chenlei
	 * @date 2015/9/16
	 */
	List<UnloadBillDetailEntity> queryAirUnloadTaskBillDetailListById(
			String unloadTaskId);

	/**
	 * 用于从商务专递交接单中获取所有运单信息
	 * @author chenlei 272681
	 * @date 2015/8/31
	 */
	List<UnloadWaybillDetailEntity> queryUnloadTaskWaybillDetailByAirHandOverBillNo(
			String handOverBillNo);


	/**
	* @description 根据卸车任务ID获取卸车任务编号列表
	* @param unloadTaskId
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月28日 上午7:18:51
	 */
    List<String> queryBillNosByUnloadTaskId(String unloadTaskId);

    /**
    * @description 手动添加差异报告
    * @param unloadDiffReportDao
    * @return
    * @version 1.0
    * @author 328768-foss-gaojianfu
    * @update 2016年6月15日 上午6:14:59
     */
	int addUnloadDiffReport(UnloadDiffReportEntity unloadDiffReportDao);
}
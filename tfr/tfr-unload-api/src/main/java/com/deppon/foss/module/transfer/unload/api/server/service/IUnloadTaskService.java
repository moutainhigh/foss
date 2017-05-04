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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/service/IUnloadTaskService.java
 *  
 *  FILE NAME          :IUnloadTaskService.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.server.service
 * FILE    NAME: IUnloadTaskService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.DeleteFromUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCancelUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressQueryUnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.FossToWKResponseDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PlanUnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryArrivedBillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;

/**
 * 卸车
 * @author dp-duyi
 * @date 2012-11-26 上午10:26:26
 */
public interface IUnloadTaskService {
	//接送货接口:根据运单号查询货物交接、卸车情况
	public List<HandOverAndUnloadDto> queryHandOverAndUnloadByWayBillNo(String wayBillNo);
	
	/**
	 * 新增卸车任务时，“快速添加”时，根据车牌号获取本部门待卸的所有单据编号及单据类型list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午4:16:18
	 */
	List<QueryArrivedBillNoDto> queryArrivedBillNoList(String vehicleNo);
	
	/**
	 * 计算月台放空时间
	 * @author 045923-foss-shiwei
	 * @date 2013-3-14 下午6:45:04
	 */
	Date calculatePlanFinishTime(String orgCode,double weightTotal,double volumeTotal,Date unloadStartTime);
	
	/**
	 * 根据交接单号获取待卸的交接单信息list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午6:35:19
	 */
	List<PlanUnloadBillDto> queryArrivedHandOverBillInfoByNo(List<String> handOverBillNoList);
	
	/**
	 * @description 根据交接单号获取待卸的快递交接单信息list
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月27日 下午11:00:54
	 */
	List<PlanUnloadBillDto> queryArrivedExpressHandOverBillInfoByNo(List<String> expressHandOverBillNoList);
	
	/**
	 * 根据配载车次号获取待卸的配载单信息list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午6:35:46
	 */
	List<PlanUnloadBillDto> queryArrivedVehicleAssembleBillInfoByNo(List<String> vehicleAssembleBillNoList);
	
	/**
	 * 新增卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:15:04
	 */
	int addUnloadTaskBasicInfo(UnloadTaskEntity baseEntity);
	
	/**
	 * FOSS同步卸车任务给悟空
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @date 2016年4月27日 下午5:29:40
	 */
	void addExpressUnloadTask(UnloadTaskAddnewDto addnewDto,UnloadTaskEntity baseEntity,List<UnloadBillDetailEntity> billDetailList,List<LoaderParticipationEntity> loaderList) throws Exception;
	
	/**
	 * @description FOSS同步确认卸车任务到WK
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @throws Exception 
	 * @update 2016年5月10日 下午2:21:33
	 */
	void confirmExpressUnloadTask(ConfirmUnloadTaskDto confirmUnloadTaskDto) throws Exception;
	
	/**
	 * 新增卸车任务单据信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:17:02
	 */
	int addUnloadTaskBillDetailList(List<UnloadBillDetailEntity> billDetailList,String unloadType);
	
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
	 * 更改卸车任务单据状态
	 * @author 045923-foss-shiwei
	 * @date 2013-1-12 上午8:23:13
	 */
	int updateArriveBillState(List<UnloadBillDetailDto> bills,String state);
	
	/**
	 * 新增时保存卸车任务
	 * @author 045923-foss-shiwei
	 * @throws Exception 
	 * @date 2012-12-13 下午2:42:31
	 */
	String addUnloadTask(UnloadTaskAddnewDto addnewDto) throws Exception;
	
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
	int deleteBillDetailListFromUnloadTask(DeleteFromUnloadTaskDto deleteDto,String unloadType);
	
	/**
	 * 批量传入卸车员code和卸车任务ID，将卸车员从卸车员参与情况表中删除
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:05:58
	 */
	int deleteLoaderListFromUnloadTask(DeleteFromUnloadTaskDto deleteDto);
	
	/**
	 * @description 根据卸车任务编号返回快递单据明细
	 * @param unloadWaybillDetailDto
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月3日 上午9:12:43
	 */
	List<UnloadBillDetailEntity> queryExpressUnloadWaybillDetail(ExpressQueryUnloadWaybillDetailDto unloadWaybillDetailDto,String unloadType) throws Exception;
	
	/**
	 * 修改卸车任务
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:10:45
	 */
	int updateUnloadTask(UnloadTaskModifyDto modifyDto);
	
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
	 * Foss同步修改卸车任务到悟空
	 * @author 328768-foss-gaojianfu
	 * @date 2016年4月29日 上午11:22:27
	 */
	void updateExpressUnloadTask(UnloadTaskModifyDto modifyDto) throws Exception;
	
	/**
	 * 通过卸车任务ID，取消（物理删除）卸车任务，删除三表数据，t_opt_unload_task，t_opt_unload_bill_detail，t_opt_loader_participation
	 * 同时更新任务内所有单据的卸车分配状态为未分配
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午7:39:01
	 */
	int cancelUnloadTask(String unloadTaskId,String unloadTaskNo);
	
	/**
	 * @description Foss同步取消卸车任务到悟空系统
	 * @param expressCancelUnloadTaskDto
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午12:30:11
	 */
	void expressCancelUnloadTask(ExpressCancelUnloadTaskDto expressCancelUnloadTaskDto) throws Exception;
	
	/**
	 * 手工确认卸车任务界面，根据交接单号获取运单号
	 * @author 045923-foss-shiwei
	 * @date 2012-12-15 下午4:56:55
	 */
	List<HandOverBillDetailEntity> queryWaybillListByHandOverBillNo(String handOverBillNo);
	
	/**
	 * @description 根据交接单编号返回快递笼号，包号，运单号
	 * @param paramMap
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @throws Exception 
	 * @update 2016年5月3日 上午10:29:15
	 */
	List<HandOverBillDetailEntity> expressQueryWaybillListByHandOverBillNo(Map<String,String> paramMap) throws Exception;
	
	/**
	 * 根据交接单号、运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-17 上午10:40:23
	 */
	List<HandOverBillSerialNoDetailEntity> querySerialNoListByHandOverBillNoAndWaybillNo(String handOverBillNo,String waybillNo);
	
	/**
	 * 用于确认短途卸车界面，快速查询时根据运单号获取交接单号list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 上午10:42:52
	 */
	List<String> queryHandOverBillListByWaybillNo(String unloadTaskId,String waybillNo);
	
	/**
	 * 确认卸车任务时，提交数据方法
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 下午7:05:13
	 */
	int confirmUnloadTask(ConfirmUnloadTaskDto confirmUnloadTaskDto);
	
	/**
	 * 确认卸车任务时，提交数据方法
	 * @author 042795-foss-duyi
	 * @date 2012-12-19 下午7:05:13
	 */
	void confirmPDAUnloadTask(String taskNo,Date endTime);
	/**
	 * 根据卸车任务ID更新卸车人员参与情况的离开任务时间
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 下午8:02:52
	 */
	int updateLoaderLeaveTaskTime(String unloadTaskId,Date leaveTime);
	
	/**
	 * 调用配载单模块接口，获取配载单下的交接单列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-21 下午3:49:01
	 */
	List<QueryHandOverBillDto> queryHandOverBillListByVehicleAssembleNo(String vehicleAssembleNo);
	
	/**
	 * 手工确认卸车任务界面，添加多货时校验运单号、流水号是否合法
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午11:05:58
	 */
	int validateWaybillNoAndSerialNo(String waybillNo,String serialNo);
	
	/**
	 * 确认卸车任务界面，添加多货运单、流水号时，校验运单号、流水号是否存在于本次卸车任务中
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午3:39:52
	 */
	int validateWaybillNoAndSerialNoIsInUnloadTask(String unloadTaskId,String unloadType,String waybillNo,String serialNo);
	
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
	 * 获取上级组织实体
	 * @author 045923-foss-shiwei
	 * @date 2013-4-3 下午2:50:36
	 */
	OrgAdministrativeInfoEntity querySuperOrgByOrgCode(String orgCode);
	
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
	 * @date 2014-5-8 下午3:13:19
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public boolean queryUnloadTaskReport(String waybillNo);
	
	/**
	 * @author nly
	 * @date 2015年4月22日 上午10:02:06
	 * @function 根据运单号查询所有的卸车运单明细
	 * @param waybillNo
	 * @return
	 */
	List<UnloadWaybillDetailEntity> queryUnloadWaybillDetailByNo(String waybillNo);
	/**
	 * 
	* @Title: judgeTimeRange 
	* @Description: 判断时间是否在08:00-17:30类
	* 1.在08:00之前返回08：00
	* 2.在17:30之后返回第二天08:00
	* @param  timeRangeStr（08:00-17:30时间区间）
	* @param  departTime 需要判断时间
	* @param @return    设定文件 
	* @return Date    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-14  下午3:36:43
	* @throws
	 */
	Date judgeTimeRange(String timeRangeStr, Date departTime);
	/**
	 * 
	* @Title: addJobTodo 
	* @Description:添加  （创建卸车任务时 发短信 ）代办Job
	* @param  unloadTaskNo(任务编号)
	* @param  orgCode 操作部门Code
	*  BusinessId 任务Id
	*  BusinessScene任务场景
	*  BusinessGoal 业务目标
	* @return void    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-25  下午3:39:11
	* @throws
	 */
	void addJobTodo(String unloadTaskNo, String user, String orgCode);
	/**
	 * 
	* @Title: deleteTfrJob 
	* @Description:删除卸车任务的时候  更新 代办Job取消发送短信
	* 试点期间全国只针对这8个到货大区开通权限，其余大区按原先形式不变，
	* 杭州大区，北京丰台大区，广州白云大区，深圳西部大区，台州大区，保定大区，泉州大区，新乡大区
    * 下的营业部
	* @param @param unloadTaskNo    设定文件 
	* @return void    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-25  下午3:52:29
	* @throws
	 */
	void deleteTfrJob(String unloadTaskNo);

	/**
	 * 根据商务专递交接单号获取交接单运单列表
	 * @author 272681 chenlei
	 * @date 2015/9/17
	 */
	List<HandOverBillDetailEntity> queryWaybillListByAirHandOverBillNo(
			String handOverBillNo);

	/**
	 * 手工确认卸车任务界面，根据商务专递的交接单号、运单号获取流水号列表
	 * @author 272681 
	 * @date 2015/9/2
	 */
	List<HandOverBillSerialNoDetailEntity> querySerialNoListByAirHandOverBillNoAndWaybillNo(
			String handOverBillNo, String waybillNo);

	/**
	 * 用于确认卸车界面，快速查询时根据商务专递运单号获取交接单号list
	 * @author 272681 
	 * @date 2015/9/13
	 */
	List<String> queryAirHandOverBillListByWaybillNo(String unloadTaskId,
			String waybillNo);

	/**
	 * 根据商务专递交接单号获取待卸的交接单信息list
	 * @author 272681
	 * @date 2015/9/15
	 */
	List<PlanUnloadBillDto> queryArrivedBusinessAirBillInfoByNo(
			List<String> businessAirBillNoList);

	/**
	 * 根据商务专递卸车任务ID获取其下单据列表
	 * @author 272681
	 * @date 2015/9/16
	 */
	List<UnloadBillDetailEntity> queryAirUnloadTaskBillDetailListById(
			String unloadTaskId);

	/**
	 * 新增卸车、确认卸车时，更新商务专递到达单据状态
	 * @author 272681
	 * @date 2015/8/24 
	 */
	int updateArriveBusinessAirBillState(List<UnloadBillDetailDto> bills,
			String state, String unloadState);

	/**
	 * @description 添加快递多货时，校验输入的运单号、流水号是否合法
	 * @param map
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月17日 下午7:56:04
	 */
	FossToWKResponseDto validateExpressWaybillNoAndSerialNo(Map<String,String> map) throws Exception;

	/**
	 * @description 根据卸车任务编号查询单据号列表
	 * @param unloadTaskId
	 * @return
	 * @throws Exception
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月17日 下午7:56:04
	 */
	List<String> queryBillNoListByUnloadTaskId(String unloadTaskId);

	/**
	 * @description 查询交接单号list
	 * @param paramMap
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年6月8日 上午8:28:23
	 */
	List<String> queryExpressHandOverBillListByWaybillNo(Map<String, String> paramMap);

	/**
	 * @description 查询交接单号list
	 * @param paramMap
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年6月8日 上午8:28:23
	 */
	List<ConfirmUnloadTaskBillsDto> queryExpressBillNosListByWaybillNo(Map<String, String> paramMap);


}
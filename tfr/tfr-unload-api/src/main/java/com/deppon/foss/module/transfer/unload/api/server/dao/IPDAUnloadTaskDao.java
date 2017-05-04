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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/dao/IPDAUnloadTaskDao.java
 *  
 *  FILE NAME          :IPDAUnloadTaskDao.java
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
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.dao
 * FILE    NAME: IPDAUnloadDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadBillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SCPDAAssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.AssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadGoodsSerialDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDetaiDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialNoDetailDto;

/**
 * PDA接口：卸车任务dao
 * @author dp-duyi
 * @date 2012-11-28 上午10:01:13
 */
public interface IPDAUnloadTaskDao {
	//pda接口：查询已分配卸车任务:未开始、进行中
	public List<PDAAssignUnloadBillEntity> pdaQueryAssignedUnloadTask(Map<String,String> condition);
	//根据任务编号查询卸车任务
	public UnloadTaskEntity queryUnloadTaskByTaskNo(String taskNo);
	//根据单据编号查询单据
	public List<UnloadBillDetailDto> queryUnloadBillsByBillNo(List<String> billNos);
	//根据任务ID查询单据
	public List<UnloadBillDetailDto> queryUnloadBillsByTaskId(String taskId);
	//根据配载单号查询包交接单
	public List<UnloadBillDetailDto> queryHandoverByNO(String vehicleassembleNo);
	/**查询卸车交接单*/
	public List<UnloadGoodsSerialDetailDto> queryHandOverUnloadDetail(List<UnloadBillDetailDto> bills,String taskId);
	/**查询卸车配载单*/
	public List<UnloadGoodsSerialDetailDto> queryAssembleUnloadDetail(List<UnloadBillDetailDto> bills,String taskId);
	/**查询卸车接送货单据*/
	public List<UnloadGoodsSerialDetailDto> queryPickUpUnloadDetail(List<UnloadBillDetailDto> bills,String taskId);
	/**查询电子面单单据//322610*/
	public List<UnloadGoodsSerialDetailDto> queryELookGoodsDetail(List<UnloadBillDetailDto> bills,String taskId);
	/**查询卸车航空正单单据*/
	public List<UnloadGoodsSerialDetailDto> queryAirUnloadDetail(List<UnloadBillDetailDto> bills,String taskId);
	/**
	 * 查询卸车航空正单交接单单据
	 * @Author 263072
	 * @Date 2015-9-11 15:12:15
	 * @param bills
	 * @param taskId
	 * @return
	 */
	public List<UnloadGoodsSerialDetailDto> queryAirHandoverUnloadDetail(List<UnloadBillDetailDto> bills,String taskId);
	/**zwd 200968 查询快递集中卸货*/
	public List<UnloadGoodsSerialDetailDto> queryEwaybillUnloadDetail(List<UnloadBillDetailDto> bills,String taskId);
	//查询卸车包信息
	public List<UnloadGoodsSerialDetailDto> queryPackageUnloadDetail(List<UnloadBillDetailDto> bills);
	//查询交接单中卸车包信息
    public List<UnloadGoodsSerialDetailDto> queryPackageHandUnloadDetail(List<UnloadBillDetailDto> bills);
	//查询配载单中卸车包信息
	public List<UnloadGoodsSerialDetailDto> queryPackageAssembleDetail(List<UnloadBillDetailDto> bills);
	//查询卸车多货包货物
	public List<UnloadGoodsSerialDetailDto> queryUnloadMorePackageGoods(String taskNo);
	//查询卸车多货货物
	public List<UnloadGoodsSerialDetailDto> queryUnloadMoreGoods(String taskNo);
	//查询卸车运单
	public UnloadGoodsSerialDetailDto queryUnloadGoodsDetail(String wayBillNo,String serialNo,String orgCode);
	//查询封签
	public List<SealEntity> querySeal(SealEntity seal);
	//更新已分配卸车任务状态
	public int updateAssignUnloadTaskState(List<AssignUnloadTaskEntity> unloadTasks);
	//根据状态更新已分配卸车任务状态
	public int updateAssignUnloadTaskStateByState(AssignUnloadTaskEntity unloadTask,String beforeState);
	
	/**
	 * 根据状态更新已分配卸车任务状态 商务专递 2015-10-12 13:59:54
	 * @param unloadTask
	 * @param beforeState
	 * @author 263072 
	 * @return
	 */
	public int updateAssignUnloadTaskStateByStateForDEAP(AssignUnloadTaskEntity unloadTask,String beforeState);
		
	//根据任务ID查询理货员
	public List<LoaderParticipationEntity> queryLoaderByTaskId(LoaderParticipationEntity loader);
	//根据任务ID查询扫描记录条数
	public int queryScanSerialNoQTYByTaskId(String taskId);
	//更新卸车任务
	public int updateUnloadTask(UnloadTaskEntity unloadTask);
	//查询任务车辆中未结束卸车的有效单据数
	public int queryUnfinishUnloadedValideBillQty(String billNo);
	//修改任务车辆明细状态
	public int updateTruckTaskDetailState(TruckTaskDetailEntity truckTaskDetail);
	//查询未完成卸车的任务车辆明细记录条数
	public int queryUnfinishUnloadedTruckTaskDetailQty(String truckTaskId);
	//修改任务车辆状态
	public int updateTruckTaskState(TruckTaskEntity truckTask);
	//插入扫描流水号
	public int insertUnloadSerialNoEntity(UnloadSerialNoDetailEntity serialNoEntity);
	//插入扫描运单明细
	public int insertUnloadWayBillEntity(UnloadWaybillDetailEntity wayBillEntity);
	//更新扫描运单明细
	public int updateUnloadWayBillEntity(UnloadWaybillDetailEntity wayBillEntity);
	//重写更新扫描运单明细
    public int newUpdateUnloadWayBillEntity(UnloadWaybillDetailEntity wayBillEntity,String serialNo,Date scanTime,String goodsState);
	//更新扫描流水号
	public int updateUnloadSerialNoEntity(UnloadSerialNoDetailEntity serialNoEntity);
	//重写更新扫描流水号
	public int newUpdateUnloadSerialNoEntity(UnloadSerialNoDetailEntity serialNoEntity);
	//查询卸车运单明细
	public UnloadWaybillDetailEntity queryUnloadWayBillDetail(UnloadWaybillDetailEntity wayBillEntity);
	//查询卸车流水号明细
	public UnloadSerialNoDetailEntity queryUnloadSerialNoEntity(UnloadSerialNoDetailEntity serialNoEntity);
	//查询卸车交接单中流水号:交接单号、运单号、流水号
	public List<UnloadGoodsSerialDetailDto> queryHandOverUnloadSerialDetail(List<UnloadBillDetailDto> bills);
	//查询卸车配载单中流水号：配载单号、运单号、流水号
	public List<UnloadGoodsSerialDetailDto> queryAssembleUnloadSerialDetail(List<UnloadBillDetailDto> bills);
	//查询卸车接送货单据：单据编号、运单号、流水号
	public List<UnloadGoodsSerialDetailDto> queryPickUpUnloadSerialDetail(List<UnloadBillDetailDto> bills);
	//查询商务专递 卸车单据：单据编号、运单号、流水号
	public List<UnloadGoodsSerialDetailDto> queryAireHandOverUnloadSerialDetail(List<UnloadBillDetailDto> bills);
	//zwd 200968 查询快递集中卸货单据：单据编号、运单号、流水号 
	public List<UnloadGoodsSerialDetailDto> queryEWayUnloadSerialDetail(List<UnloadBillDetailDto> bills);
	//查询卸车扫描记录
	public List<UnloadSerialNoDetailDto> queryUnloadScanSerialDetailByTaskId(String taskId);
	//插入更换标签
	public int insertChangeLabelGoodsEntity(ChangeLabelGoodsEntity changeLabelGoods);
	//删除更换标签
	public int deleteChangeLabelGoodsEntity(ChangeLabelGoodsEntity changeLabelGoods);
	//插入卸车单据明细
	public int insertUnloadBillDetails(List<UnloadBillDetailEntity> unloadBillEntitys);
	//查询车辆中到达部门非本部门的单据
	public List<ArriveBillDto> queryUnArriveBillInVehicle(List<String> orgCodes,List<String> unloadBills);
	//查询货物是否在交接单中
	public boolean queryGoodsBeInHandOverBill(List<String> billNos,String wayBillNo,String serialNo);
	//查询货物是否在配载单中
	public boolean queryGoodsBeInAssembleBill(List<String> billNos,String wayBillNo,String serialNo);
	//查询货物是否在接送货单据中，如果存在，则返回接送送件数
	public UnloadBillDetailDto queryGoodsBeInPickUpBill(String taskNo,String wayBillNo,String serialNo);
	
	public UnloadBillDetailDto queryPickUpBillByGoods(List<String> billNos,String wayBillNo,String serialNo);
	public UnloadBillDetailDto queryAirBillByGoods(List<String> billNos,String wayBillNo,String serialNo);
	public UnloadBillDetailDto queryHandOverBillByGoods(List<String> billNos,String wayBillNo,String serialNo);
	public UnloadBillDetailDto queryAssembleBillByGoods(List<String> billNos,String wayBillNo,String serialNo);
	/**zwd 200968 查询快递集中卸货*/
	public UnloadBillDetailDto queryEwayBillByGoods(List<String> billNos,String wayBillNo,String serialNo);
	/** 
	 * @Title: getLoaderParticipationByTaskId 
	 * @Description: 根据卸车任务ID获取卸车任务创建人
	 * @param taskId
	 * @return    
	 * @return LoaderParticipationEntity    返回类型 
	 * getLoaderParticipationByTaskId
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-22下午5:10:41
	 */ 
	public LoaderParticipationEntity getLoaderParticipationByTaskId(
			String taskId);
	//通过单据号查询封签信息
	public List<SealEntity> querySealByBillNo(List<String> billNos,String vehicleNo);
	//查询包运单明细，去除重复运单号记录
	public List<ExpressPackageDetailEntity> queryScanPackageDetails(String packageNo);
	//查询包中某个运单流水号
	public List<String> queryPackageSerialNo(String packageNo,String waybillNo);
	//批量插入扫描流水号
	public int insertUnloadSerialNos(List<UnloadSerialNoDetailEntity> serialNoEntitys);
	//查询包中某个运单流水号
    public List<UnloadWaybillDetailEntity> queryIsPackageScan(String taskid,String packageNo);
    //更新扫描运单明细
  	public int updatePackageWayBillEntity(UnloadWaybillDetailEntity wayBillEntity);
  	//查询包中某个运单明细
    public ExpressPackageDetailEntity queryPackageDetailsByWaybill(String waybillNo,String packageNo);
    //批量更新包中某个运单的流水号
  	public int updateUnloadPackageSerialNo(UnloadSerialNoDetailEntity serialNoEntity);
  	//插入卸车扫描信息到 异步信息表中 
	int insertUnloadAsyncMsg(List<PDAUnloadAsyncBillMsgDto> unloadAsyncMsgs);
	
	//删除异步表中 已入库的运单
	public int deleteUnloadAsyncMsg(List<String> ids);
	//加业务锁 设置jobId
	public int upateUnloadMsgForJob(String jobId,int dataLimit); 
	//查询需要入库的运单集合 同个jobId
	public List<PDAUnloadAsyncBillMsgDto> queryInStockMsg(String jobId);
	//更新jobId为N/A
	public int updateUnloadAsyncBillJobId(List<PDAUnloadAsyncBillMsgDto> inStockMsgs);
	//重置异常数据
	public int reSetUnloadInstockMsg();
	
	
	//二程接驳卸车任务分配
	List<SCPDAAssignUnloadTaskEntity> pdaQueryAssignedSCUnloadTask(
			Map<String, String> condition);

	//二程接驳 扫描后 状态改变
	//public int updateUnloadScanState(String billNo);
	
	//二程接驳创建卸车任务后 状态改变
	public int updateAssignSCUnloadTaskStateByState(List<String> billNos,
			int handoverbillState, String billAssignState);
	
	//查询二程接驳卸车创建任务 单据明细
	public List<UnloadBillDetailDto> querySCUnloadBillsByBillNo(
			List<String> billNos);
	//查询二程接驳刷新 单据明细
	public List<UnloadGoodsSerialDetailDto> querySCUnloadDetail(
			List<UnloadBillDetailDto> scbills, String taskId);
	
	//根据二程接驳卸车任务编号获取卸车任务
	public UnloadTaskEntity querySCUnloadTaskByTaskNo(String unloadTaskNo);
	
	//二程接驳扫描后状态更新
	public int updateState(Map<String, Object> condition);
	
	//二程接驳
	public List<UnloadGoodsSerialDetailDto> querySCUnloadSerialDetail(
			List<UnloadBillDetailDto> scbills);
	
	//查询二程接驳到达营业网点
	public String querySCUnloadOrgCode(String billNo);
	
	//扫描之后 指令状态改变
	public int updateUnloadScanState(String billNo);
	//插入卸车到达时间
//	public  int updateSCArrivalTime(List<String> billNos);
	
	/**通过运单号查询所有卸车流水信息(流水、时间)*/
	public List<UnloadSerialDetaiDto> queryUnloadSerialDetailByWaybillNo(String waybillNo);
	
}
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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IPDALoadDao.java
 *  
 *  FILE NAME          :IPDALoadDao.java
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
 * FILE    NAME: PDATransferLoadDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.deppon.foss.module.transfer.common.api.shared.dto.OaReportClearMore;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadGoodsDetailSerialDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.domain.CreateDeliveryReceiptEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.WaybillInfoEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAWaybillReturnDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PackagePathLoaderDto;

/**
 * PDA中转装车dao接口
 * @author dp-duyi
 * @date 2012-11-19 上午9:11:43
 */
public interface IPDALoadDao {

	//插入电子面单卸车任务（无出发到达-322610）
	public int addLTLHandoverBill(CreateDeliveryReceiptEntity deliveryReceiptEntity);
	//插入电子面单卸车任务详情（无出发到达-322610）
	public int addLTLHandoverBillDetail(WaybillInfoEntity waybill);
	//插入电子面单卸车流水号（无出发到达-322610）
	public int addLTLHandoverSerialNo(WaybillInfoEntity waybill);
	//插入装车任务
	public int insertTransferLoadTask(LoadTaskEntity loadTask);
	//插入到达部门
	public int insertTransferLoadDestOrg(List<LoadDestOrgEntity> loadDestOrg);
	//插入理货员
	public int insertTransferLoaderParticipation(List<LoaderParticipationEntity> loaders);
	//查询理货员
	public List<LoaderParticipationEntity> queryLoaderParticipation(String taskId,String loaderCode,String flag);
	//插入装车运单明细
	public int insertLoadWayBillDetailEntity(LoadWaybillDetailEntity loadWaybillDetailEntity);
	//插入装车流水号明细
	public int insertLoadSerialNoEntity(LoadSerialNoEntity loadSerialNoEntity);
	//插入PDA
	public int insertPDATask(PDATaskEntity pdaEntity);
	//提交装车任务时、更新装车任务,修改参与人数
	public int updateLoadTask(LoadTaskEntity loadTask);
	//提交装车任务时理货员时，根据理货员更新理货员参与情况
	public int updateLoaderParticipationByLoadTask(Map<String,Object> task);
	//删除理货员时，根据理货员更新理货员参与情况
	public int updateLoaderParticipationByLoader(List<LoaderParticipationEntity> loader);
	//根据任务编号查询装车任务
	public LoadTaskEntity queryLoadTaskByTaskNo(String taskNo);
	//根据任务编号查询装车任务
	public LoadTaskEntity queryLoadTaskByTaskNoForUpdate(String taskNo);
	//根据运单号、任务id查询装车任务运单明细
	public LoadWaybillDetailEntity queryLoadWaybillDetailEntityByWayBillNo(LoadWaybillDetailEntity loadTaskWayBillEntity);
	//根据运单号、任务id查询装车任务运单明细 zwd 200968 20150417
	public List<LoadWaybillDetailEntity> queryLoadWaybillDetailEntityByPackageNo(LoadWaybillDetailEntity loadTaskWayBillEntity);
	//更新扫描运单明细 zwd 200968 20150417
  	public int updatePackageWayBillEntity(LoadWaybillDetailEntity wayBillEntity);
	//根据运单号、任务id查询流水号运单明细
	public LoadSerialNoEntity queryLoadSerialNoEntityBySerialNo(LoadSerialNoEntity loadSerialNoEntity);
	//查询装车流水号
	public LoadTaskSerialNoDto queryLoadTaskSerialNoDtoByCondition(Map<String,String> condition);
	//更新装车任务运单明细
	public int updateLoadTaskWayBillDetail(LoadWaybillDetailEntity loadWaybillDetailEntity);
	//更新装车任务运单明细+增加
	public int updateLoadTaskWayBillDetailAdd(LoadWaybillDetailEntity loadWaybillDetailEntity);
	//更新装车流水号信息
	public int updateLoadTaskSerialNo(LoadSerialNoEntity loadSerialNoEntity);
	/**
	 * @author zwd 200968
	 * @ 2015-05-22 
	 * @ 批量更新装车流水号信息
	 */
	public int updateLoadTaskSerialNoNew(LoadSerialNoEntity loadSerialNoEntity);
		
	//暂时没用：删除装车运单明细
	public int deleteLoadWaybillDetailEntity(String id);
	//暂时没用：删除装车流水号
	public int deleteLoadSerialNoEntity(String id);
	//根据任务编号查询pda，condition：任务编号，是否建立任务PDA,任务类型
	public List<PDATaskEntity> queryPDATaskByTaskNo(Map<String,String> condition);
	//更新pdaTask:leaveTime,TaskNo,leaveTime
	public int updatePDATaskEntity(Map<String,Object> parameter);
	//根据任务编号查询未提交任务PDA数
	public int queryUnSubmitPDAQty(String taskNo);
	//根据任务ID查询扫描记录条数
	public int queryScanSerialNoQTYByTaskId(String taskId);
	//查询少货运单
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadLackWayBill(Map<String,String> condition);
	//查询少货流水号
	public List<LoadSerialNoEntity> queryDeliverLoadLackSerial(Map<String,Object> condition);
	//查询多货
	public List<LoadTaskSerialNoDto> queryGoodsByGoodsStates(Map<String,Object> condition);
	//查询装车到达部门
	public List<String> queryLoadDestOrgCodesById(String loadTaskId);
	//查询装车到达部门
	public List<LoadDestOrgEntity> queryLoadDestOrgs(String loadTaskId);
	//根据运单号，流水号查询货件数
	public int queryValidLabeledCount(Map<String,String> condition);
	//查询货物签收状态wayBillNo,SerialNo,active
	public int queryWayBillSignedState(Map<String,String> serial);
	//查询装车出库货物
	public List<LoadTaskSerialNoDto> queryOutStockGoods(Map<String,String> condition);
	//查询已装车货物
	public List<LoadTaskSerialNoDto> queryLoadedGoods(Map<String,String> condition);
	//装车查询OA多货
	public List<OaReportClearMore> queryLoadOaReportMoreGoods(String taskId,Date bizBeginDate);
	//刷新正常货区装车清单
	public List<LoadGoodsDetailSerialDto> refrushNormalTransferLoadTaskDetail(String taskNo,List<String> destOrgCodes,String goodsType,String isExpress);
	//刷新虚拟库存装车清单
	public List<LoadGoodsDetailSerialDto> refrushSaleNormalTransferLoadTaskDetail(String taskNo,List<String> destOrgCodes,String goodsType,String isExpress);
	//刷新混合包装车清单
	public List<LoadGoodsDetailSerialDto> refrushPackageTransferLoadTaskDetail(String taskNo,List<String> destOrgCodes);
	//刷新直达包装车清单
	public List<LoadGoodsDetailSerialDto> refrushDirectPackageTransferLoadDetail(String taskNo,List<String> destOrgCodes);
	/**
	 * 刷新 到达未卸车 装车清单
	 * map => key : origOrgCode,handoverbillStateList,arriveDeptList,start,limit
	 * @author yuyongxiang
	 * @date 2013年8月2日 10:51:54
	 */
	public List<LoadGoodsDetailSerialDto> refrushNormalTransferLoadTaskDetailUnloadingNotReach(Map<String, Object> map);
	//刷新合车货区装车清单
	public List<LoadGoodsDetailSerialDto> refrushTogetherTransferLoadTaskDetail(String taskNo,List<String> destOrgCodes,String goodsType,String isExpress);
	//刷新多货装车清单
	public List<LoadGoodsDetailSerialDto> refrushMoreGoodsLoadTaskDetail(String taskNo,String isExpress);
	//刷新多货装车包清单
	public List<LoadGoodsDetailSerialDto> refrushMorePackageLoadDetail(String taskNo);
	//刷新派送装车运单清单
	public List<LoadGoodsDetailSerialDto> refrushNormalDeliverLoadTaskDetail(String taskNo);
	//查询合车到达部门
	public List<String> queryTogetherDestOrgCodes(Map<String,String> condition);
	//PDA查询已分配装车任务:进行中、未开始的任务
	public List<PDAAssignLoadTaskEntity> queryAssignedLoadTask(QueryAssignedLoadTaskEntity condition);
	//PDA查询未完成装车任务
	public List<PDAAssignLoadTaskEntity> queryUnfinishedLoadTask(QueryAssignedLoadTaskEntity condition);
	//开单非库存流水号
	public List<PDAGoodsSerialNoDto> queryLabeledGoodsNotInStock(String wayBillNo,String orgCode);
	//查询库存流水号
	public List<PDAGoodsSerialNoDto> queryStockSerialNos(String wayBillNo,String orgCode,String goodsAreaCode,int needLoadQty);
	//根据运单号查询库存中为包装流水号
	public List<PDAGoodsSerialNoDto> queryUnpackStockSerialNos(String wayBillNo,String orgCode);
	//查询是否未包装
	public boolean queryBeUnpack(String wayBillNo,String serialNo,String orgCode);
	//查询预配件数是否大于装车件数
	public int stockQtyIsBiggerThanLoadQty(String taskId,String deliverBillNo,String wayBillNo);
	//查询已扫描未装车流水号
	public List<LoadSerialNoEntity> queryScanedUnloadSerialNos(String loadWayBillDetailId);
	//查询库存未装车流水号
	public List<LoadSerialNoEntity> queryStockUnloadSerialNos(String wayBillNo,String origOrgCode,String loadWayBillDetailId,int rownum);
	public List<Integer> queryDeliverStockQty(String taskNo,String wayBillNo);
	public List<String> queryDeliverBillOrgCodeByDeliverBillNo(String deliverBillNo);
	public int queryWayBillStockQty(String wayBillNo,String orgCode);
	public int queryDeliverArrangeQty(String deliverBillNo,String wayBillNo);
	public List<String> queryHandOverBillNoByTaskNo(String loadTaskNo);
	public int updateMakeUpPDAloadAndUnload(Map<String,Object> condition);
	public LoaderParticipationEntity queryLoaderByTaskID(String taskId);

	/**
	 * 根据装车任务表的taskNo查找装车运单明细表中已装车件数小于运单表的货物总件数的运单号信息 
	 * 
	 * @param taskNo
	 * @author dp-zwd
	 */
	public List<String> queryWayBillNo(String taskNo);
	
	//根据装车任务编号查询所有运单号信息
	public List<String> queryWayBillNoList(String taskNo);
	 
	 /**根据扫描时间更新包装车运单明细状态**/
	 public int updateLoadWayBillQTYByScanTime(LoadWaybillDetailEntity loadWayBill,Date scanTime);
	//根据任务id查询包号
	public List<String> queryLoadPackageNoById(String loadTaskId);
	//包生成交接单
	@SuppressWarnings("rawtypes")
	public Map  autoCreatePackHandoverbill(String taskNo,String packageNo,int length,String isDirectPackage);
	
	public void addPickHandoverBill(Map<String,Object> condition);
	//查询交接单是否存在
	public int beExistPickHandover(List<String> taskNos);
	// 插入派送装车运单退回
	public int insertLoadWaybillReturn(PDAWaybillReturnDto pdaWaybillReturnDto);
	
	/**
	 * FOSS根据PDA传过来的运单号和当前所在转运场，查找出该运单走货路径中的下一转运场，并把结果返回给PDA。
	 * @author zwd 200968 2015-07-21
	 * @param waybillNo
	 * @param orgCode
	 * @return 
	 */
	public PackagePathLoaderDto  unlockPackagePathDetail(String waybillNo,String orgCode);
	

	//通过任务号查询出运单  与 子母件单号比对  hongwy-218427
	public LoadTaskEntity queryWayBillNos(String taskNo);
	
	//根据接驳点编码查询接驳点名称
	public String queryAccessPointName(String pointCode);
	
	//判断是不是派送单
	public int judgeIfIsDeliverLoad(String waybillNo);
	//根据运单号查找是否早库存中
	public List<String> queryWaybillInStock(String waybillNo);
	//查找某运单在库存中的数量
	public Integer waybillNoQtyInStock(String waybillNo);
	//查找某运单已装车的数量
	public Integer loadWaybillQty(String waybillNo);
	//查找某个运单的最后入库时间
	public Date lastInstockTime(String waybillNo);
	//查询某个运单的装车时间
	public Date loadTime(String waybillNo);
	//查询任务号 是否已生成 过218427 foss-hongwy 2015.12.29
	public String queryTaskNos(String taskNo);
	//根据运单号查询零担电子运单交接单信息
	public List<CreateDeliveryReceiptEntity> queryLTLPackHandoverbill(String waybillNo);
	//根据运单号查询零担电子运单交接单信息详情信息
	public List<WaybillInfoEntity> queryLTLPackHandoverbillDetail(String waybillNo);
	

	//刷新正常库区零担装车清单(剔除优先货、代办的清单)
	public List<LoadGoodsDetailSerialDto> refreshSimpleLDDetail(String taskNo,List<String> destOrgCodes,String goodsType);
	
	//刷新正常库区快递散货装车清单(剔除优先货、代办的清单)
    public List<LoadGoodsDetailSerialDto> refreshSimpleEXPDetail(String taskNo,List<String> destOrgCodes,String goodsType);
	
    //刷新合车库区装车清单(剔除优先货、代办的清单)
	public List<LoadGoodsDetailSerialDto> refreshTogetherSimpleDetail(String taskNo,List<String> destOrgCodes,String goodsType);
	
	//刷新虚拟库存零担装车清单(剔除优先货、代办的清单)
	public List<LoadGoodsDetailSerialDto> refreshSaleSimpleLDDetail(String taskNo,List<String> destOrgCodes,String goodsType);
	
	//查询装车虚拟出库货物
	public List<LoadTaskSerialNoDto> querySaleOutStockGoods(Map<String,String> condition);
}

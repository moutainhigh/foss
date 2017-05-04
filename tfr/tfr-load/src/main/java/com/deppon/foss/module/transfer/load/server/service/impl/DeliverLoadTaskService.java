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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/DeliverLoadTaskService.java
 *  
 *  FILE NAME          :DeliverLoadTaskService.java
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
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: DeliverLoadTaskService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskSerialNoDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.server.dao.impl.PDALoadDao;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 派送装车任务
 * @author dp-duyi
 * @date 2012-10-30 上午11:08:38
 */
public class DeliverLoadTaskService implements IDeliverLoadTaskService{
	private IDeliverLoadGapReportDao deliverLoadGapReportDao;
	private IDeliverLoadTaskDao deliverLoadTaskDao;
	private IAssignLoadTaskDao assignLoadTaskDao;
	private IPDACommonService pdaCommonService;
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	private ITfrJobTodoService tfrJobTodoService;//待办job service接口
	private IBillNumService billNumService;//我的账单编号接口
	
	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}


	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}
	/**
	 * 用于获取上级组织
	 */
	private ILoadService loadService;
	
	/**
	 * 交接单服务，用于获取流水号库存
	 */
	private IHandOverBillDao handOverBillDao;
	
	/**
	 * 用于生成装车任务编号
	 */
//	private ITfrCommonService tfrCommonService;
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/** 
	 * 组织接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IPDALoadService pdaLoadService;
	
	public void setPdaLoadService(IPDALoadService pdaLoadService) {
		this.pdaLoadService = pdaLoadService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setHandOverBillDao(IHandOverBillDao handOverBillDao) {
		this.handOverBillDao = handOverBillDao;
	}
	
	/*public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}*/
	
	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public void setDeliverLoadGapReportDao(
			IDeliverLoadGapReportDao deliverLoadGapReportDao) {
		this.deliverLoadGapReportDao = deliverLoadGapReportDao;
	}

	public void setDeliverLoadTaskDao(IDeliverLoadTaskDao deliverLoadTaskDao) {
		this.deliverLoadTaskDao = deliverLoadTaskDao;
	}

	public void setAssignLoadTaskDao(IAssignLoadTaskDao assignLoadTaskDao) {
		this.assignLoadTaskDao = assignLoadTaskDao;
	}
	public IPDALoadDao pdaLoadDao;
	public void setPdaLoadDao(PDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}
	private IStockService stockService;
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	/** 
	 * 接送货接口:确认派送装车
	 * @author dp-duyi
	 * @date 2012-10-30 上午11:09:45
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#comfirmLoadTask(java.lang.String)
	 */
	@Override
	public int comfirmLoadTask(String deliverBillNo) {
		//更新装车任务状态：已提交
		LoadTaskEntity task = new LoadTaskEntity();
		task.setDeliverBillNo(deliverBillNo);
		task.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
		List<LoadTaskEntity> tasks = deliverLoadTaskDao.queryLoadTasksByDeliverBillNo(task);
		if(CollectionUtils.isEmpty(tasks)){
			throw new TfrBusinessException("无状态为已完成，派送单号为"+deliverBillNo+"的装车任务");
		}else if(CollectionUtils.isNotEmpty(tasks) && tasks.size() != 1){
			throw new TfrBusinessException("更新装车任务状态失败");
		}
		task.setState(LoadConstants.LOAD_TASK_STATE_SUBMITED);
//		task.setLoadEndTime(this.getCurrentTime());
		task.setTaskNo(tasks.get(0).getTaskNo());
		task.setLoadEndTime(tasks.get(0).getLoadEndTime());
		int updateCount = deliverLoadTaskDao.updateDeliverLoadTaskStateByDeliverBillNo(task);
		
		//更新差异 报告状态: 已确认
		DeliverLoadGapReportEntity report = new DeliverLoadGapReportEntity();
		report.setDeliverBillNo(deliverBillNo);
		report.setBeValid(FossConstants.ACTIVE);
		report.setState(LoadConstants.DELIVER_LOAD_GAP_REPORT_STATE_AFFIRM);
		deliverLoadGapReportDao.updateDeliverLoadGapReportState(report);
		
		//更新分配任务状态：已完成
		Map<String,String> condition  = new HashMap<String,String>();
		condition.put("beCancelled", FossConstants.NO);
		condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_FINISHED);
		condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
		condition.put("deliverBillNo", deliverBillNo);
		assignLoadTaskDao.updateAssignedLoadTaskState(condition);
		try{
			pdaCommonService.updatePlatformStateByFinishTask(task.getTaskNo(), new Date());
		}catch(Exception e){
			LOGGER.info("调用月台接口失败",e);
		}
		return updateCount;
	}

	/** 
	 * 接送货接口:打回派送装车
	 * @author dp-duyi
	 * @date 2012-10-30 上午11:09:45
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#takeBackLoadTask(java.lang.String)
	 */
	@Override
	public int takeBackLoadTask(String deliverBillNo) {
		//定义返回值
		int updateCount = 0;
		//更新装车任务状态：进行中
		LoadTaskEntity task = new LoadTaskEntity();
		task.setDeliverBillNo(deliverBillNo);
		task.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
		
		//获取装车任务
		List<LoadTaskEntity> tasks = deliverLoadTaskDao.queryLoadTasksByDeliverBillNo(task);
		if(CollectionUtils.isEmpty(tasks)){
			throw new TfrBusinessException("无状态为已完成，派送单号为"+deliverBillNo+"的装车任务");
		}else if(CollectionUtils.isNotEmpty(tasks) && tasks.size() != 1){
			throw new TfrBusinessException("更新装车任务状态失败");
		}
		
		//如果为pda生成的卸车任务
		LoadTaskEntity tempEntity = tasks.get(0);
		if(StringUtils.equals(tempEntity.getLoadWay(),UnloadConstants.UNLOAD_TASK_WAY_PDA)){
			//将装车任务状态修改为装车中
			task.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			task.setBeCreateGapRep(FossConstants.NO);
			task.setLoadEndTime(null);
			updateCount = deliverLoadTaskDao.updateDeliverLoadTaskStateByDeliverBillNo(task);
		//如果为非ＰＤＡ生成的装车任务
		}else{
			//将装车任务状态修改为已取消
			task.setState(LoadConstants.LOAD_TASK_STATE_CANCELED);
			task.setLoadEndTime(null);
			updateCount = deliverLoadTaskDao.updateDeliverLoadTaskStateByDeliverBillNo(task);
			
			/*将分配装车任务的状态修改为未开始*/
			Map<String,String> condition  = new HashMap<String,String>();
			//是否取消：否
			condition.put("beCancelled", FossConstants.NO);
			//进行中
			condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
			//未开始
			condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
			//派送单号
			condition.put("deliverBillNo", deliverBillNo);
			assignLoadTaskDao.updateAssignedLoadTaskState(condition);
			
			//修改派送单状态为已分配
			DeliverBillEntity bill = new DeliverBillEntity();
			bill.setBillNo(deliverBillNo);
			bill.setState(DeliverbillConstants.STATUS_ASSIGNED);
			assignLoadTaskDao.updateDeliverBillState(bill,null);
			//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
			if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState()) ) {
				DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
				deliverBillVary.setDeliverBillNo(bill.getBillNo());//派送单号
				deliverBillVary.setDeliverBillStatus(bill.getState());//派送单状态
				deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
			}
			
			/*重新入库流水号*/
			Map<String,String> queryCondition = new HashMap<String,String>();
			queryCondition.put("taskId", tempEntity.getId());
			queryCondition.put("beLoaded", FossConstants.YES);
			List<LoadTaskSerialNoDto> inStockList = pdaLoadDao.queryLoadedGoods(queryCondition);
			//循环入库流水号
			for(LoadTaskSerialNoDto serialNoDto : inStockList){
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				inOutStockEntity.setWaybillNO(serialNoDto.getWayBillNo());
				inOutStockEntity.setSerialNO(serialNoDto.getSerialNo());
				inOutStockEntity.setOrgCode(tasks.get(0).getOrigOrgCode());
				inOutStockEntity.setInOutStockType(StockConstants.CANCEL_DELIVERY_LOAD_IN_STOCK_TYPE);
				inOutStockEntity.setOperatorCode(FossUserContext.getCurrentInfo().getEmpCode());
				inOutStockEntity.setOperatorName(FossUserContext.getCurrentInfo().getEmpName());
				stockService.inStockPC(inOutStockEntity);
			}
		}
		
		//更新差异 报告状态：已打回
		DeliverLoadGapReportEntity report = new DeliverLoadGapReportEntity();
		report.setDeliverBillNo(deliverBillNo);
		report.setBeValid(FossConstants.ACTIVE);
		report.setState(LoadConstants.DELIVER_LOAD_GAP_REPORT_STATE_BACK);
		deliverLoadGapReportDao.updateDeliverLoadGapReportState(report);
		
		return updateCount;
	}
	public String getCurrentTime(){
		Date date=new Date(); 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return df.format(date);
	}

	/** 
	 * 接送货接口:根据派送单号、装车任务id查询全部卸车差异报告（有效，无效）
	 * @author dp-duyi
	 * @date 2012-12-5 下午9:13:39
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#queryDeliverLoadGapRep(java.lang.String, java.lang.String)
	 */
	@Override
	public List<DeliverLoadGapReportEntity> queryDeliverLoadGapRep(
			String taskId, String deliverBillNo) {
		boolean unEmpty = false;
		Map<String,String> condition = new HashMap<String,String>();
		if(StringUtils.isNotEmpty(taskId)){
			unEmpty = true;
			condition.put("taskId", taskId);
		}
		if(StringUtils.isNotEmpty(deliverBillNo)){
			unEmpty = true;
			condition.put("deliverBillNo", deliverBillNo);
		}
		if(unEmpty){
			return deliverLoadGapReportDao.queryAllDeliverLoadGapRepByDeliverNo(condition);
		}else{
			return null;
		}
	}

	/** 
	 * 根据派送单号、运单号，返回最新的装车任务流水号列表
	 * @author dp-duyi
	 * @date 2012-12-27 上午11:36:54
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#queryLastLoadSerialNos(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> queryLastLoadSerialNos(String deliverBillNo,
			String wayBillNo) {
		if(StringUtils.isNotBlank(deliverBillNo) && StringUtils.isNotBlank(wayBillNo)){
			Map<String,String> condition = new HashMap<String,String>();
			condition.put("deliverNo", deliverBillNo);
			condition.put("wayBillNo", wayBillNo);
			return deliverLoadTaskDao.queryLastLoadSerialNos(condition);
		}
		return null;
	}


	/** 
	 * 打回派送单
	 * @author dp-duyi
	 * @date 2013-3-17 下午2:59:32
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#takeBackDeliverBill(java.lang.String)
	 */
	@Override
	public int takeBackDeliverBill(String deliverBillNo) {
		
		//更新装车任务状态：已提交
		LoadTaskEntity task = new LoadTaskEntity();
		task.setDeliverBillNo(deliverBillNo);
		task.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
		List<LoadTaskEntity> tasks = deliverLoadTaskDao.queryLoadTasksByDeliverBillNo(task);
		if(CollectionUtils.isEmpty(tasks)){
			throw new TfrBusinessException("无状态为已完成，派送单号为"+deliverBillNo+"的装车任务");
		}else if(CollectionUtils.isNotEmpty(tasks) && tasks.size() != 1){
			throw new TfrBusinessException("更新装车任务状态失败");
		}
		task.setState(LoadConstants.LOAD_TASK_STATE_SUBMITED);
		task.setLoadEndTime(this.getCurrentTime());
		task.setTaskNo(tasks.get(0).getTaskNo());
		int updateCount = deliverLoadTaskDao.updateDeliverLoadTaskStateByDeliverBillNo(task);
		
		//更新差异 报告状态: 已确认
		DeliverLoadGapReportEntity report = new DeliverLoadGapReportEntity();
		report.setDeliverBillNo(deliverBillNo);
		report.setBeValid(FossConstants.ACTIVE);
		report.setState(LoadConstants.DELIVER_LOAD_GAP_REPORT_STATE_AFFIRM);
		deliverLoadGapReportDao.updateDeliverLoadGapReportState(report);
		
		//更新分配任务状态：已完成
		Map<String,String> condition  = new HashMap<String,String>();
		condition.put("beCancelled", FossConstants.NO);
		condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_FINISHED);
		condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
		condition.put("deliverBillNo", deliverBillNo);
		assignLoadTaskDao.updateAssignedLoadTaskState(condition);
		//调用月台接口修改月台状态
		try{
			pdaCommonService.updatePlatformStateByFinishTask(task.getTaskNo(), new Date());
		}catch(Exception e){
			LOGGER.info("调用月台接口失败",e);
		}
		//入库装车货物
		List<String> goodsStates = new ArrayList<String>();
		//查询多货
		goodsStates.add(LoadConstants.LOAD_GOODS_STATE_MORE);
		goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ENTRAINED);
		goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_MORE_ALLOPATRY_ENTRAINED);
		goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);
		Map<String,Object> cond = new HashMap<String,Object>();
		cond.put("taskId", tasks.get(0).getId());
		cond.put("goodsStates", goodsStates);
		List<LoadTaskSerialNoDto> moreGoodsList = pdaLoadDao.queryGoodsByGoodsStates(cond);
		if(CollectionUtils.isNotEmpty(moreGoodsList)){
			for(LoadTaskSerialNoDto goods: moreGoodsList){
				//入库卸车货物
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				inOutStockEntity.setWaybillNO(goods.getWayBillNo());
				inOutStockEntity.setSerialNO(goods.getSerialNo());
				inOutStockEntity.setOrgCode(tasks.get(0).getOrigOrgCode());
				inOutStockEntity.setInOutStockType(StockConstants.CANCEL_DELIVERY_LOAD_IN_STOCK_TYPE);
				inOutStockEntity.setOperatorCode(FossUserContext.getCurrentInfo().getEmpCode());
				inOutStockEntity.setOperatorName(FossUserContext.getCurrentInfo().getEmpName());
				stockService.inStockPC(inOutStockEntity);
			}
		}
		return updateCount;
	}
	
	/**
	 * 根据派送单号获取派送单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:37:49
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#queryDeliverBillByNo(java.lang.String)
	 */
	@Override
	public DeliverBillEntity queryDeliverBillByNo(String billNo) {
		return this.deliverLoadTaskDao.queryDeliverBillByNo(billNo);
	}

	/**
	 * 根据派送单号获取派送单详情
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:40:25
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#queryDeliverBillDetailListByNo(java.lang.String)
	 */
	@Override
	public List<LoadWaybillDetailEntity> queryDeliverBillDetailListByNo(
			String billNo) {
		//获取上级组织code(如果为派送外场，则获取外场code)
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		String superOrgCode = loadService.querySuperiorOrgCodeIncludeTransferCenterByOrgCode(orgCode);
		DeliverBillQueryConditionDto queryDto = new DeliverBillQueryConditionDto();
		queryDto.setBillNo(billNo);
		queryDto.setLoginOrgCode(superOrgCode);
		//不查询异常库区、贵重物品库区、包装库区的货物；
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queryDto.setUnnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		
		//返回查询结果
		return this.deliverLoadTaskDao.queryDeliverBillDetailListByNo(queryDto);
	}

	/**
	 * 根据运单号获取流水号库存
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午8:29:36
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#querySerialNoStockList(java.lang.String)
	 */
	@Override
	public List<SerialNoStockEntity> querySerialNoStockList(String waybillNo) {
		//构造查询条件
		QuerySerialNoListForWaybillConditionDto queryDto = new QuerySerialNoListForWaybillConditionDto();
		//运单号
		queryDto.setWaybillNo(waybillNo);
		//获取上级组织编码
		//获取上级组织code(如果为派送外场，则获取外场code)
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		String superOrgCode = loadService.querySuperiorOrgCodeIncludeTransferCenterByOrgCode(orgCode);
		queryDto.setCurrentDeptCode(superOrgCode);
		//排除异常库区
		//不查询异常库区、贵重物品库区、包装库区的货物；
		List<String> abnormalGoodsAreaTypeList = new ArrayList<String>();
		//异常库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION);
		//贵重物品库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE);
		//包装库区
		abnormalGoodsAreaTypeList.add(DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING);
		queryDto.setAbnormalGoodsAreaTypeList(abnormalGoodsAreaTypeList);
		return handOverBillDao.querySerialNoStockList(queryDto);
	}

	/**
	 * 根据运单号、到达部门获取运单号被交接过的流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 上午10:59:46
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SerialNoStockEntity> queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(
			String waybillNo) {
		//获取上级组织code(如果为驻地部门，则获取外场code)
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		String superOrgCode = loadService.querySuperiorOrgCodeIncludeTransferCenterByOrgCode(orgCode);
		return handOverBillDao.queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(waybillNo, superOrgCode);
	}

	/**
	 * 手工确认派送装车任务
	 * @author 045923-foss-shiwei
	 * @date 2013-4-17 上午10:22:15
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService#confirmDeliverLoadTaskByHand(java.lang.String, java.util.List, java.util.List)
	 */
	@Override
	@Transactional
	public int confirmDeliverLoadTaskByHand(String billNo,Date operateTime,List<LoadWaybillDetailEntity> waybillList,List<SerialNoStockEntity> serialNoList) {
		//部门code
		String superOrgCode = loadService.querySuperiorOrgCodeIncludeTransferCenterByOrgCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
		//获取派送单信息
		DeliverBillEntity billEntity = this.queryDeliverBillByNo(billNo);
		//对比时间戳
		if(!billEntity.getOperateTime().equals(operateTime)){
			throw new TfrBusinessException(FossConstants.NO);
		}
		//校验车辆是否可用
		loadService.validateVehicleNoCanBeUsed(billEntity.getVehicleNo());
		//校验派送单状态
		String deliverBillState = assignLoadTaskDao.queryDeliverBillState(billNo);
		//如果派送单状态不等于已提交或已分配，则不能建立装车任务
		if(!(DeliverbillConstants.STATUS_SUBMITED.equals(deliverBillState) || DeliverbillConstants.STATUS_ASSIGNED.equals(deliverBillState))){
			throw new TfrBusinessException("派送单不是“已分配”状态！");
		}
		/*构造装车任务实体*/
		LoadTaskEntity taskEntity = new LoadTaskEntity();
		//ID
		taskEntity.setId(UUIDUtils.getUUID());
		//任务类型
		taskEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_DELIVER);
		//任务编号
		String taskNo = billNumService.generateDeliverTaskNo(superOrgCode);//产生派送任务编号;
		taskEntity.setTaskNo(taskNo);
		//派送单号
		taskEntity.setDeliverBillNo(billNo);
		//车牌号
		taskEntity.setVehicleNo(billEntity.getVehicleNo());
		//开始结束时间
		taskEntity.setLoadStartTime(DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT));
		taskEntity.setLoadEndTime(taskEntity.getLoadStartTime());
		//任务状态：已完成
		taskEntity.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
		//出发部门code，name
		taskEntity.setOrigOrgCode(superOrgCode);
		taskEntity.setOrigOrgName(this.orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(superOrgCode));
		//货物类型
		taskEntity.setGoodsType(LoadConstants.LOAD_GOODSTYPE_ALL);
		//卸车方式
		taskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_NO_PDA);
		//是否生成差异报告
		taskEntity.setBeCreateGapRep(FossConstants.NO);
		
		/*构造待插入的运单和流水号*/
		//转换流水号list
		Map<String,List<String>> serialNoMap = convertList2Map(serialNoList);
		for(LoadWaybillDetailEntity waybillDetail : waybillList){
			/*运单下的流水号*/
			String waybillNo = waybillDetail.getWaybillNo();
			//正常的流水号
			List<String> noList = new ArrayList<String>();
			//少货的流水号
			List<String> lackNoList  = new ArrayList<String>();
			//获取库存中的流水号
			List<SerialNoStockEntity> serialNoStockList = this.querySerialNoStockList(waybillNo);
			//获取交接到本部门的流水号
			List<SerialNoStockEntity> handOveredSerialNoList = this.queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(waybillNo);
			//装车件数不可大于排单件数
			if(waybillDetail.getLoadQty() > waybillDetail.getDeliverBillQty()){
				throw new TfrBusinessException("运单：" + waybillDetail.getWaybillNo() + "的“装车件数”大于“排单件数”，请重新选择装车流水号！");
			}
			//装车件数不可小于0
			if(waybillDetail.getLoadQty() < 0){
				throw new TfrBusinessException("运单：" + waybillDetail.getWaybillNo() + "的“装车件数”小于0，请重新勾选！");
			}
			//装车件数不能大于库存件数
			if(waybillDetail.getLoadQty() > serialNoStockList.size()) {
				throw new TfrBusinessException("运单：" + waybillDetail.getWaybillNo() + "的装车件数大于库存件数, 请刷新后操作!");
			}
			//扫描件数
			waybillDetail.setScanQty(0);
			//出发部门code
			waybillDetail.setOrigOrgCode(taskEntity.getOrigOrgCode());
			//装车任务id
			waybillDetail.setLoadTaskId(taskEntity.getId());
			//建立任务时间
			waybillDetail.setTaskBeginTime(DateUtils.strToDate(taskEntity.getLoadStartTime()));
			//id
			waybillDetail.setId(UUIDUtils.getUUID());
			
			if(serialNoMap.get(waybillNo) != null){
				noList = serialNoMap.get(waybillNo);
			}else{
				//如果前台没有勾选，则获取库存中的流水号
				for(int i = 0; i < serialNoStockList.size(); i++){
					if(i < waybillDetail.getLoadQty()){
						noList.add(serialNoStockList.get(i).getSerialNo());
					}
				}
				//如果库存中的流水号不够用，则使用已交接的流水号补充
//				if(noList.size() < waybillDetail.getLoadQty()){
//					//使用交接的流水号
//					for(SerialNoStockEntity handOveredSerialNo : handOveredSerialNoList){
//						String tempSerialNo = handOveredSerialNo.getSerialNo();
//						if(noList.size() < waybillDetail.getLoadQty() && !noList.contains(tempSerialNo)){
//							noList.add(tempSerialNo);
//						}
//						if(noList.size() == waybillDetail.getLoadQty()){
//							break;
//						}
//					}
//				}
			}
			//装车件数为空
			if(CollectionUtils.isEmpty(noList)) {
				throw new TfrBusinessException("运单" + waybillNo + "无可装车的流水, 请刷新页面后再操作！");
			}
			//如果此时正常装车的流水号小于排单件数，则有少货
			if(waybillDetail.getDeliverBillQty().compareTo(noList.size()) == 1){
				//获取差值
				int lackSerialNoCount = waybillDetail.getDeliverBillQty() - noList.size();
				//先使用库存中的流水号作为少货流水号
				for(int i = 0; i < serialNoStockList.size(); i++){
					String tempSerialNo = serialNoStockList.get(i).getSerialNo();
					if(lackNoList.size() < lackSerialNoCount && !noList.contains(tempSerialNo)){
						lackNoList.add(tempSerialNo);
					}
					if(lackNoList.size() == lackSerialNoCount){
						break;
					}
				}
				//如果库存中的流水号不够用，则使用已交接的流水号补充，作为少货流水号
				if(lackNoList.size() < lackSerialNoCount){
					//使用交接的流水号
					for(SerialNoStockEntity handOveredSerialNo : handOveredSerialNoList){
						String tempSerialNo = handOveredSerialNo.getSerialNo();
						if(lackNoList.size() < lackSerialNoCount && !noList.contains(tempSerialNo) && !lackNoList.contains(tempSerialNo)){
							lackNoList.add(tempSerialNo);
						}
						if(lackNoList.size() == lackSerialNoCount){
							break;
						}
					}
				}
			}
			//给正常、少货的流水号补充属性
			//出库装车正常的流水号
			List<InOutStockEntity> outStockList = new ArrayList<InOutStockEntity>();
			for(String serialNo : noList){
				LoadSerialNoEntity loadSerialNo = new LoadSerialNoEntity();
				loadSerialNo.setSerialNo(serialNo);
				//是否装车
				loadSerialNo.setBeLoaded(FossConstants.YES);
				//货物状态：正常
				loadSerialNo.setGoodsState(LoadConstants.LOAD_GOODS_STATE_NORMAL);
				this.addFieldsForLoadSerialNo(loadSerialNo, waybillDetail);
				
				//插入装车任务流水号明细
				this.pdaLoadDao.insertLoadSerialNoEntity(loadSerialNo);
				
				//出库装车正常的流水号
				//记录出入库动作信息
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				//设置 运单号
				inOutStockEntity.setWaybillNO(waybillNo);
				//设置 流水号
				inOutStockEntity.setSerialNO(serialNo);
				//装车出库
				inOutStockEntity.setInOutStockType(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE);
				//设置 操作人编号
				inOutStockEntity.setOperatorCode(FossUserContext.getCurrentInfo().getEmpCode());
				//设置 操作人姓名
				inOutStockEntity.setOperatorName(FossUserContext.getCurrentInfo().getEmpName());
				outStockList.add(inOutStockEntity);
			}
			
			//出库正常的流水号
			stockService.outStockBatchPC(outStockList);
			
			for(String serialNo : lackNoList){
				LoadSerialNoEntity loadSerialNo = new LoadSerialNoEntity();
				loadSerialNo.setSerialNo(serialNo);
				//是否装车
				loadSerialNo.setBeLoaded(FossConstants.NO);
				//货物状态：少货
				loadSerialNo.setGoodsState(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);
				this.addFieldsForLoadSerialNo(loadSerialNo, waybillDetail);
				//插入装车任务流水号明细
				this.pdaLoadDao.insertLoadSerialNoEntity(loadSerialNo);
			}
			//表设计中，stock_qty实际为排单件数
			waybillDetail.setStockQty(waybillDetail.getDeliverBillQty());
			//插入装车任务运单明细
			this.pdaLoadDao.insertLoadWayBillDetailEntity(waybillDetail);
		}
		
		//插入装车任务数据
		this.pdaLoadDao.insertTransferLoadTask(taskEntity);
		
		
		//更新分配装车任务状态为进行中
		Map<String,String> condition  = new HashMap<String,String>();
		//是否取消：否
		condition.put("beCancelled", FossConstants.NO);
		//进行中
		condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
		//未开始
		condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
		//派送单号
		condition.put("deliverBillNo", billNo);
		//更新分配记录状态：进行中、已完成
		assignLoadTaskDao.updateAssignedLoadTaskState(condition);
		
		//更新派送单状态为已装车
		DeliverBillEntity bill = new DeliverBillEntity();
		bill.setBillNo(billNo);
		bill.setState(DeliverbillConstants.STATUS_LOADED);
		int updateQty = assignLoadTaskDao.updateDeliverBillState(bill,DeliverbillConstants.STATUS_ASSIGNED);
		if(updateQty != 1){
			throw new TfrBusinessException("数据过期，请重新查询");
		}else{
			//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
			if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState()) ) {
				DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
				deliverBillVary.setDeliverBillNo(bill.getBillNo());//派送单号
				deliverBillVary.setDeliverBillStatus(bill.getState());//派送单状态
				deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
			}
		}
		//发送通知
		pdaLoadService.noticeDeliverOrg(taskEntity, FossUserContext.getCurrentInfo().getEmpCode(), FossUserContext.getCurrentInfo().getEmpCode());
		
		//再次对比派送单时间戳
		DeliverBillEntity bill2Entity = this.queryDeliverBillByNo(billNo);
		//对比时间戳
		if(!bill2Entity.getOperateTime().equals(operateTime)){
			throw new TfrBusinessException(FossConstants.NO);
		}
		
		LOGGER.error("派件装车扫描"+taskNo+"推送货物轨迹，插入未执行job开始： ");
		String [] businessGoalList=new String []{BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ,BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO,
				BusinessGoalContants.BUSINESS_GOAL_TO_JIAWAYUN};
		try{
			tfrJobTodoService.addJobTodo(taskNo, BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN, businessGoalList, new Date(), FossUserContext.getCurrentInfo().getEmpCode());
			}catch(TfrBusinessException e){
			throw  e;
		}catch(Exception e){
			throw new TfrBusinessException(e.toString());
		}
		LOGGER.error("派件装车扫描"+taskNo+"推送货物轨迹，插入未执行job结束 ");
		
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 私有方法，为装车流水号补充属性
	 * @author 045923-foss-shiwei
	 * @date 2013-4-17 下午5:11:57
	 */
	private void addFieldsForLoadSerialNo(LoadSerialNoEntity loadSerialNo,LoadWaybillDetailEntity waybillDetail){
		//id
		loadSerialNo.setId(UUIDUtils.getUUID());
		//运单明细ID
		loadSerialNo.setLoadWaybillDetailId(waybillDetail.getId());
		//扫描状态
		loadSerialNo.setScanState("N/A");
		//操作时间
		loadSerialNo.setLoadTime(waybillDetail.getTaskBeginTime());
		//创建时间
		loadSerialNo.setCreateTime(loadSerialNo.getLoadTime());
		//建立任务时间
		loadSerialNo.setTaskBeginTime(loadSerialNo.getCreateTime());
		//出发部门code
		loadSerialNo.setOrigOrgCode(waybillDetail.getOrigOrgCode());
	}
	
	/**
	 * 将传入的流水号list转换为map，方便后续比较
	 * @author 045923-foss-shiwei
	 * @date 2013-4-17 下午3:22:59
	 */
	private Map<String,List<String>> convertList2Map(List<SerialNoStockEntity> serialNoList){
		//定义返回值
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		//循环list
		for(SerialNoStockEntity serialNo : serialNoList){
			if(map.get(serialNo.getWaybillNo()) == null){
				List<String> noList = new ArrayList<String>();
				noList.add(serialNo.getSerialNo());
				map.put(serialNo.getWaybillNo(), noList);
			}else{
				List<String> noList = map.get(serialNo.getWaybillNo());
				noList.add(serialNo.getSerialNo());
				map.put(serialNo.getWaybillNo(), noList);
			}
		}
		return map;
	}

	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}
	
}

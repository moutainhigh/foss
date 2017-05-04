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
 *  PROJECT NAME  : tfr-stockchecking
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/service/impl/StReportService.java
 *  
 *  FILE NAME          :StReportService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
//import com.deppon.foss.module.base.querying.server.service.ISalesdeptDeliveryprocService;
//import com.deppon.foss.module.base.querying.shared.domain.SalesdeptDeliveryEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferDictionaryConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferDetailDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStDifferReportDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskListDao;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.ITaskReportService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskListEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.LackGoodsFoundEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class TaskReportService implements ITaskReportService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskReportService.class);
	private IStTaskListDao stTaskListDao;
	private IStResultListDao stResultListDao;
	private IStDifferReportDao stDifferReportDao;
	private IStDifferDetailDao stDifferDetailDao;
	private ITfrCommonService tfrCommonService;
	private IStockService stockService;
	private ICalculateTransportPathService calculateTransportPathService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private ISignDetailService signDetailService;
	
	private IConfigurationParamsService configurationParamsService;
	
	private ILoadService loadService;
	
	private IActualFreightService actualFreightService;
	
	private IWaybillRfcService  waybillRfcService;
	
	//private ISalesdeptDeliveryprocService salesdeptDeliveryprocService;
	
	
	
	//public void setSalesdeptDeliveryprocService(
			//ISalesdeptDeliveryprocService salesdeptDeliveryprocService) {
		//this.salesdeptDeliveryprocService = salesdeptDeliveryprocService;
	//}

	/**
	 * @param waybillRfcService the waybillRfcService to set
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * @param actualFreightService the actualFreightService to set
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setStTaskListDao(IStTaskListDao stTaskListDao) {
		this.stTaskListDao = stTaskListDao;
	}

	public void setStResultListDao(IStResultListDao stResultListDao) {
		this.stResultListDao = stResultListDao;
	}

	public void setStDifferReportDao(IStDifferReportDao stDifferReportDao) {
		this.stDifferReportDao = stDifferReportDao;
	}

	public void setStDifferDetailDao(IStDifferDetailDao stDifferDetailDao) {
		this.stDifferDetailDao = stDifferDetailDao;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}
	
	/**
	 * @author niuly
	 * @date 2013-07-31 14:08:53
	 * @function 新建清仓任务时，获取任务部门的时间配置参数，用于下拉库存
	 * @param deptCode
	 * @return int
	 */
	private int getBeforeTime(String deptCode) {
		//默认为120分钟
		int createTaskTime = ConstantsNumberSonar.SONAR_NUMBER_120;
		try{
			ConfigurationParamsEntity defaultBizHourSlice1 = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_TASK_BEFORE_CREATE_TIME, deptCode);
			createTaskTime = Integer.valueOf(defaultBizHourSlice1.getConfValue()).intValue();
		}catch(Exception e){
			LOGGER.error("获取'清仓任务新建调整时间差'配置参数失败", ExceptionUtils.getFullStackTrace(e));
		}
		return createTaskTime;
	}
	
	/**
	 * @author niuly
	 * @date 2014-3-22下午3:12:01
	 * @function 判断该单是否已作废或中止作废
	 * @param waybillNoMap
	 * @param waybillNo
	 * @param obsolete
	 * @param aborted
	 */
	private Map<String,Boolean> checkWaybillState(Map<String,List<String>> waybillNoMap,String waybillNo) {
		boolean obsolete = false;
		boolean aborted = false;
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		if(waybillNoMap.get("OBSOLETE").contains(waybillNo)) {
			//作废
			obsolete = true;
		} else {
			ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
			if(actualFreightEntity!=null){
				if(StringUtils.equals(WaybillConstants.OBSOLETE, actualFreightEntity.getStatus())){
					//作废
					obsolete = true;
					waybillNoMap.get("OBSOLETE").add(waybillNo);
				}else {
					if(waybillNoMap.get("ABORTED").contains(waybillNo)) {
						//中止作废
						aborted = true;
					} else {
						if(StringUtils.equals(WaybillConstants.ABORTED, actualFreightEntity.getStatus())){
							//中止作废
							aborted = true;
							waybillNoMap.get("ABORTED").add(waybillNo);
						}
					}
				}
			}
		}
		map.put("obsolete", obsolete);
		map.put("aborted", aborted);
		return map;
	}
	
	/**
	 * 增加差异报告
	 * @date 2013-6-5 下午3:47:54
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.service.ITaskReportService#addStTaskReport(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	@Transactional
	@Override
	public void addStTaskReport(StTaskEntity stTask) {
		int surplusQuantity = 0; 				//多货件数
		int lackQuantity = 0;	 				//少货件数
		int successHandleSurplusQuantity = 0;	//记录成功处理的多货件数，不包含"放错货区类型"
		int signQty = 0;					//已签收件数
		int obsoleteQty = 0;				//已作废件数
		int abortedQty = 0;				//中止作废件数
		int totalQuantity = 0;	 				//每个清仓差异报告的差异总件数
		List<StTaskListEntity> surplusList = new ArrayList<StTaskListEntity>();	//多货数据
		List<StTaskListEntity> lackList = new ArrayList<StTaskListEntity>();	//少货数据
		//差异数据
		List<StDifferDetailEntity> stDifferDetailList = new ArrayList<StDifferDetailEntity> ();
		
		StDifferReportEntity stDiffer = new StDifferReportEntity();
		String stDifferId = UUIDUtils.getUUID();
		stDiffer.setId(stDifferId);
		stDiffer.setStTaskId(stTask.getId());
		stDiffer.setReportCode("D" + stTask.getTaskNo());
		stDiffer.setGoodsAreaName(stTask.getGoodsareaname());
		stDiffer.setGoodsAreaCode(stTask.getGoodsareacode());
		stDiffer.setDeptcode(stTask.getDeptcode());
		stDiffer.setExceedGoodsQty(surplusQuantity);
		stDiffer.setLessGoodsQty(lackQuantity);
		stDiffer.setCreateTime(Calendar.getInstance().getTime());
		stDiffer.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DOING);
		
		//任务创建时间
		Date taskTime = stTask.getCreatetime();
		String deptCode = stTask.getDeptcode();
		LOGGER.info("-----差异报告：D" + stTask.getTaskNo() + "生成开始-----");
		LOGGER.info("---差异报告" + stTask.getTaskNo() + "处理多货开始---");
//		查询出多货数据  → 3
		LOGGER.info("差异报告" + stTask.getTaskNo() + "查询多货的差异结果开始");
		surplusList = stResultListDao.queryGapList(stTask.getId(), TransferConstants.GOODS_STATUS_SURPLUS);
		LOGGER.info("差异报告" + stTask.getTaskNo() + "查询多货的差异结果结束");
		//根据部门code获取配置的提前时间
		int beforeTime = this.getBeforeTime(deptCode);
		
		//存储已签收、已作废、中止作废、更改目的站的运单
		Map<String,List<String>> waybillNoMap = new HashMap<String,List<String>>();
		//已作废运单号
		List<String> obsoleteList = new ArrayList<String>();
		//中止作废
		List<String> abortedList = new ArrayList<String>();
		//更改目的站的运单号
		List<String> destList = new ArrayList<String>();
		waybillNoMap.put("OBSOLETE", obsoleteList);
		waybillNoMap.put("ABORTED", abortedList);
		waybillNoMap.put("RFC_DEST", destList);
		
		for(StTaskListEntity surplus: surplusList){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("taskTime", taskTime);
			map.put("deptCode", deptCode);
			map.put("waybillNo", surplus.getWaybillNo());
			map.put("serialNo", surplus.getSerialNo());
			map.put("beforeTime", beforeTime);
			
			
			//作废
			boolean obsolete = false;
			//中止作废
			boolean aborted = false;
			//更改目的站
			boolean rfcDest = false;
			//若未知签收，判断是否签收
			String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(surplus.getWaybillNo(), surplus.getSerialNo());
			//若签收，放在signList中
			if(!StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)) {
				Map<String,Boolean> tempMap = checkWaybillState(waybillNoMap,surplus.getWaybillNo());
				obsolete = tempMap.get("obsolete");
				aborted = tempMap.get("aborted");
			}
			
			//是否放错货区
			boolean isExistOtherGoodsArea = stockService.isExistOtherGoodsAreaStock(stTask.getDeptcode(), surplus.getWaybillNo(), surplus.getSerialNo(), stTask.getGoodsareacode());
			if(isExistOtherGoodsArea) {
				//是否更改目的站
				if(waybillNoMap.get("RFC_DEST").contains(surplus.getWaybillNo())) {
					rfcDest = true;
				} else {
					List<WaybillRfcEntity> rfcList = waybillRfcService.queryWaybillRfcAcceptByWaybillNo(surplus.getWaybillNo());
					for(WaybillRfcEntity dto:rfcList) {
						//任务创建时间至当前时间有被同意受理的更改目的站的数据，则差异类型为更改目的站
						//同意受理时间
						Date time = dto.getOperateTime();
						if((StringUtils.equals("Y", dto.getIsChangeDestination()) 
								||(StringUtils.isNotEmpty(dto.getChangeItems()) 
										&& (dto.getChangeItems().contains("提货网点") || dto.getChangeItems().contains("提貨網點"))))
								&& (taskTime.compareTo(time) <= 0 && (new Date()).compareTo(time) >= 0)){
							rfcDest = true;
							waybillNoMap.get("RFC_DEST").add(surplus.getWaybillNo());
							break;
						}
					}
				}
			}
			
			//当该件未签收且未作废且未中止作废且未更改目的站时，才判断是否要生成差异
			if(!StringUtils.equals(FossConstants.YES, checkSerialNoIsSign) && !obsolete && !aborted && !rfcDest) {
				//多货，查询入库记录表，从建立清仓任务开始的时间-配置提前时间，到当前时间为止，有没有入库动作，如果存在，该流水号不算多货
				LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +surplus.getWaybillNo() + "流水号：" +surplus.getSerialNo()+"查询入库记录开始");
				int count = stResultListDao.queryInStockNumForLack(map);
				LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +surplus.getWaybillNo() + "流水号：" +surplus.getSerialNo()+"查询入库记录结束");
				
				if(count > 0) {
					continue;
				}
			}
			
			++totalQuantity;
			
			StDifferDetailEntity stDifferDetail = new StDifferDetailEntity();
			
			stDifferDetail.setStDifferReportId(stDifferId);
			stDifferDetail.setOaErrorNo(TransferConstants.NULL_VALUE);
			stDifferDetail.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DOING);
			stDifferDetail.setHandleTime(Calendar.getInstance().getTime());
			stDifferDetail.setPackageNo(surplus.getPackageNo());
			stDifferDetail.setWaybillNo(surplus.getWaybillNo());
			stDifferDetail.setSerialNo(surplus.getSerialNo());
			
//			3.1 查询本部门非本货区是否存在此件
			LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +surplus.getWaybillNo() + "流水号：" +surplus.getSerialNo()+"查询是否本货区开始");
			
			
			
			LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +surplus.getWaybillNo() + "流水号：" +surplus.getSerialNo()+"查询是否本货区结束");
			LOGGER.info("差异报告" + stTask.getTaskNo() + "判断非本获取是否存在此货件：" + isExistOtherGoodsArea);
			//运单已签收、已作废、中止作废
			if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign) || obsolete || aborted) {
				if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)) {
					stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_SIGN);
					++signQty;
				} else if(obsolete) {
					stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_OBSOLETE);
					++obsoleteQty;
				} else if(aborted) {
					stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_ABORTED);
					++abortedQty;
				}
				stDiffer.setExceedGoodsQty(++surplusQuantity);
				stDifferDetail.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
			} else {
				
//			3.1.1 如果存在，差异结果为"放错货区" 
				if(isExistOtherGoodsArea){
					stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_SURPLUS_ERROR_GOODSAREA);
					stDiffer.setExceedGoodsQty(++surplusQuantity);
					//若更改目的站
					if(rfcDest) {
						stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_RFC_DEST);
					}
				}else{
//			多货-夹带 和 多货-异地夹带 两种情况生成差错单的动作，不在此处处理，在差错单执行JOB中处理
//			3.1.2.2、如果不在走货路径上，清仓差异结果为"多货-异地夹带"
					try{
						FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTime(surplus.getWaybillNo(), surplus.getSerialNo(), stTask.getDeptcode());
						if(TransportPathConstants.STATUS_WRONG == feedbackDto.getResult()){
							stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS);
						}else{
//					3.1.2.1、如果在走货路径上，清仓差异结果为"多货-夹带"
							stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_SURPLUS_CARRY);
						}
						LOGGER.info("差异报告" + stTask.getTaskNo() + "，夹带情况：" + feedbackDto.getResult());
					}catch(Exception e){
						LOGGER.info("差异报告" + stTask.getTaskNo() + "，夹带情况 → ERROR");
						stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS);
					}
					stDiffer.setExceedGoodsQty(++surplusQuantity);
					
//				3.1.2、如果不存在，在本部门入库此件，("并同时从上一部门出库，并通知上一部门;3.1.2.2.1此动作在入库服务中实现);查询此件是否在当前对应的走货路径上
//				执行清仓差异JOB中，需进行出入库操作，出入库操作不成功的情况，记录日志，但不能影响后续执行动作
					InOutStockEntity stockEntity = new InOutStockEntity();
					try{
						stockEntity.setWaybillNO(surplus.getWaybillNo());
						stockEntity.setSerialNO(surplus.getSerialNo());
						stockEntity.setOrgCode(stTask.getDeptcode());
						stockEntity.setOperatorName(stTask.getCreatorName());
						stockEntity.setOperatorCode(stTask.getCreatorCode());
						stockEntity.setInOutStockType(StockConstants.STOCKCHECKING_MORE_GOODS_IN_STOCK_TYPE);
						stockEntity.setInOutStockBillNO(stTask.getTaskNo());
						
						LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +surplus.getWaybillNo() + "流水号：" +surplus.getSerialNo()+"多货入库开始");
						
						stockService.inStockRequiresNewTransactional(stockEntity);
						
						LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +surplus.getWaybillNo() + "流水号：" +surplus.getSerialNo()+"多货入库结束");
						
						++successHandleSurplusQuantity;
//				更新库存时间后修改此清仓明细状态为“完成”
						stDifferDetail.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
					}catch(Exception e){
						LOGGER.error("差异报告" + stTask.getTaskNo() + "，处理多货入库失败", ExceptionUtils.getFullStackTrace(e));
						
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_REPORT.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_REPORT.getBizCode());
						jobProcessLogEntity.setExecBizId(surplus.getId());
						jobProcessLogEntity.setExecTableName("TFR.T_OPT_ST_RESULT_LIST");
						jobProcessLogEntity.setRemark("运单号：" + surplus.getWaybillNo() + ",流水号：" + surplus.getSerialNo() +",添加多货差异出错，此货物未成功进行出入库操作，清仓差异处理状态保持为'执行中'的状态，需页面重新尝试触发出入库操作！");
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
					
					//更新库位信息
					try {
						//PDA清仓任务且库位号不为空时更新库位信息，PC端不更新库位号，少货不更新库位号
						if(StringUtils.equals("Y", stTask.getIspda()) && StringUtils.isNotEmpty(surplus.getPositionNo())) {
							List<InOutStockEntity> inStockList = new ArrayList<InOutStockEntity>();
							inStockList.add(stockEntity);
							LOGGER.info("生成差异报告时，更新库位号开始，差异编号:D" + stTask.getTaskNo() + "运单号：" + stockEntity.getWaybillNO()+"流水号："+stockEntity.getSerialNO()+"库位号："+surplus.getPositionNo());
							stockService.updateStockStockPosition(inStockList, surplus.getPositionNo(), stTask.getDeptcode(), stTask.getCreatorCode(), stTask.getCreatorName());
							LOGGER.info("生成差异报告时，更新库位号成功结束，差异编号:D" + stTask.getTaskNo() + "运单号：" + stockEntity.getWaybillNO()+"流水号："+stockEntity.getSerialNO()+"库位号："+surplus.getPositionNo());
						}
					} catch(Exception e) {
						e.printStackTrace();
						LOGGER.error("生成差异报告时，更新库位号失败结束，差异编号:D" + stTask.getTaskNo() + "运单号：" + stockEntity.getWaybillNO()+"流水号："+stockEntity.getSerialNO()+"库位号："+surplus.getPositionNo());
					}
				}
			}
			//新增多货差异明细
			stDifferDetailList.add(stDifferDetail);
			//stDifferDetailDao.addStDifferEntity(stDifferDetail);
			//LOGGER.info("差异报告" + stTask.getTaskNo() + "，增加清仓差异报告明细结束");
		}
		LOGGER.info("---差异报告" + stTask.getTaskNo() + "处理多货结束---");
		
//		处理少货情况
		LOGGER.info("---差异报告" + stTask.getTaskNo() + "处理少货开始---");
//		判断是否为PDA处理，PDA处理时，无法直接获取"少货"状态的数据，需将快照表与结果表进行比对得出少货数据
		if(StringUtils.equals(TransferDictionaryConstants.YES, stTask.getIspda())){
			lackList = stTaskListDao.queryPdaLackList(stTask.getId());
		}else{
//		人工清仓的直接取出状态为"少货"数据
			lackList = stResultListDao.queryGapList(stTask.getId(), TransferConstants.GOODS_STATUS_LACK);
		}
		LOGGER.info("差异报告" + stTask.getTaskNo() + "，查询少货记录结束");
		
		for(StTaskListEntity stTaskListEntity: lackList){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("taskTime", taskTime);
			map.put("deptCode", deptCode);
			map.put("waybillNo", stTaskListEntity.getWaybillNo());
			map.put("serialNo", stTaskListEntity.getSerialNo());
			map.put("beforeTime", beforeTime);
			//是否已签收
			String checkSerialNoIsSign = signDetailService.querySerialNoIsSign(stTaskListEntity.getWaybillNo(), stTaskListEntity.getSerialNo());
			//作废
			boolean obsolete = false;
			//中止作废
			boolean aborted = false;
			
			//未签收，判断是否已作废或中止作废
			if(!StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)) {
				Map<String,Boolean> tempMap = checkWaybillState(waybillNoMap,stTaskListEntity.getWaybillNo());
				obsolete = tempMap.get("obsolete");
				aborted = tempMap.get("aborted");
			}
			
			//当该件未签收且未作废且未中止作废时，判断是否需要生成差异
			if(!StringUtils.equals(FossConstants.YES, checkSerialNoIsSign) && !obsolete && !aborted) {
				//发现少货，查询出库记录表，从建立清仓任务开始时间 - 配置时间，到当前时间为止，有没有出库动作，如果存在，该流水号不算少货。
				LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +stTaskListEntity.getWaybillNo() + "流水号：" +stTaskListEntity.getSerialNo()+"查询是否入库开始");
				int count = stResultListDao.queryOutStockNumForSurplus(map);
				LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +stTaskListEntity.getWaybillNo() + "流水号：" +stTaskListEntity.getSerialNo()+"查询是否入库结束");
				
				if(count > 0)
				{
					continue;
				}
				
				//少货，从建立清仓任务开始时间 - 配置时间，到当前时间，有没有装车扫描记录，若有，则不算少货
				LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +stTaskListEntity.getWaybillNo() + "流水号：" +stTaskListEntity.getSerialNo()+"查询是否装车撤销扫描开始");
				//建立清仓任务开始时间 - 配置时间
				Date taskStartTime = new Date(taskTime.getTime() - 1000l * ConstantsNumberSonar.SONAR_NUMBER_60 * beforeTime);
				List<LoadSerialNoEntity> loadSerialEntity = loadService.queryLoadScanState(stTaskListEntity.getWaybillNo(), stTaskListEntity.getSerialNo(), taskStartTime, deptCode);
				LOGGER.info("差异报告" + stTask.getTaskNo() + "运单号：" +stTaskListEntity.getWaybillNo() + "流水号：" + stTaskListEntity.getSerialNo()+"查询是否装车撤销扫描结束");
				
				if(CollectionUtils.isNotEmpty(loadSerialEntity))
				{
					continue;
				}		
			}
			//少货，剔除德邦家装
			String wayStaus = stResultListDao.querySalesDeptDelivery(stTaskListEntity.getWaybillNo());
			if(StringUtils.equals("DE_CONFIRM", wayStaus)) {
				continue;
			}
			//SalesdeptDeliveryEntity salesdeptDeliveryEntity = salesdeptDeliveryprocService.querySalesDeptDeliverysToTfr(stTaskListEntity.getWaybillNo(),deptCode);
			//if(salesdeptDeliveryEntity != null) {
				//if(StringUtils.equals("DE_CONFIRM",salesdeptDeliveryEntity.getWaystaus())) {
					//continue;
				//}
			//}
			++totalQuantity;
			StDifferDetailEntity stDifferDetail = new StDifferDetailEntity();

			stDifferDetail.setStDifferReportId(stDifferId);
			stDifferDetail.setOaErrorNo(TransferConstants.NULL_VALUE);
			if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign) || obsolete || aborted) {
				stDifferDetail.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
			} else {
				stDifferDetail.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DOING);
			}
			stDifferDetail.setHandleTime(Calendar.getInstance().getTime());
			stDifferDetail.setPackageNo(stTaskListEntity.getPackageNo());
			stDifferDetail.setWaybillNo(stTaskListEntity.getWaybillNo());
			stDifferDetail.setSerialNo(stTaskListEntity.getSerialNo());
			stDifferDetail.setUserCode(stTask.getCreatorCode());
			if(StringUtils.equals(FossConstants.YES, checkSerialNoIsSign)) {
				stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_SIGN);
				++signQty;
			} else if(obsolete) {
				stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_OBSOLETE);
				++obsoleteQty;
			} else if(aborted) {
				stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_ABORTED);
				++abortedQty;
			} else {
				stDifferDetail.setDifferenceType(TransferConstants.GOODS_STATUS_LACK);
			}
			stDiffer.setLessGoodsQty(++lackQuantity);			
			//新增少货差异数据
			stDifferDetailList.add(stDifferDetail);
			
		}
		LOGGER.info("---差异报告" + stTask.getTaskNo() + "处理少货结束---");
		
		LOGGER.info("-----差异报告" + stTask.getTaskNo() + "，批量插入差异数据开始----");
		//批量新增差异数据
		for(StDifferDetailEntity stDifferDetail:stDifferDetailList) {
			stDifferDetailDao.addStDifferEntity(stDifferDetail);
		}
		LOGGER.info("-----差异报告" + stTask.getTaskNo() + "，批量插入差异数据成功结束----");
		
		//如果此清仓差异报告对应的多货(夹带、异地夹带)都已成功处理，并且无其他差异明细的，需更新此清仓差异报告状态为TransferConstants.STOCK_CHECKING_REPORT_DONE
		int successHandleQty = signQty + obsoleteQty + abortedQty + successHandleSurplusQuantity;
		if(totalQuantity != 0 && totalQuantity == successHandleQty){
			stDiffer.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
		}
		
//		新增差异报告及差异明细[若无差异(无多货或少货差异记录),不生成差异报告  → 1]
		if(totalQuantity > 0){
			stDifferReportDao.addStDifferReport(stDiffer);
			LOGGER.info("差异报告" + stTask.getTaskNo() + "，成功生成清仓差异报告：" + stDiffer.getReportCode());
		}
		
		LOGGER.info("-----差异报告：D" + stTask.getTaskNo() + "生成结束----");
	}

	@Transactional
	@Override
	public void stReportGapRepair(StDifferDetailEntity differEntity) {
//		查询最新的状态，确认是否上报，只处理未上报未结束状态的数据
		StDifferDetailEntity entity = stDifferDetailDao.queryStDifferDetailEntityById(differEntity.getId());
		StDifferReportEntity reportEntity = stDifferReportDao.queryStReportEntityById(entity.getStDifferReportId());
		boolean isDone = false;
		if(StringUtils.equals(TransferConstants.STOCK_CHECKING_DOING, entity.getHandleStatus()) &&
		   StringUtils.equals(TransferConstants.NULL_VALUE, entity.getOaErrorNo())){
			
//			已经签收出库的，需将此记录置为"DONE"
			String signed = signDetailService.querySerialNoIsSign(differEntity.getWaybillNo(), differEntity.getSerialNo());
//			少货的数据，需查看从此明细处理时间之后，是否存在入库记录，如果存在，需将此记录置为"DONE"
			List<InOutStockEntity> inStockEntityList = stockService.queryInStockInfo(differEntity.getWaybillNo(), differEntity.getSerialNo(), null, differEntity.getHandleTime());
//			若一个未处理状态且未上报OA差错的少货类型的明细，如果在本部门此清仓差异报告生成时间之后，有出库记录，则自动将此明细状态置为已处理，同时备注“此货物已由XXX从XXX部门出库”
			List<InOutStockEntity> outStockEntityList = stockService.queryOutStockInfo(differEntity.getWaybillNo(), differEntity.getSerialNo(), reportEntity.getDeptcode(), differEntity.getHandleTime());
			if(StringUtils.equals(FossConstants.YES, signed)){
				differEntity.setRemark("此件已签收");
				differEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_DONE);
				differEntity.setHandleTime(Calendar.getInstance().getTime());
				stDifferDetailDao.updateByPrimaryKeySelective(differEntity);
				isDone = true;
			}else if(CollectionUtils.isNotEmpty(inStockEntityList)){
				InOutStockEntity inEntity = inStockEntityList.get(0);
				String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(inEntity.getOrgCode());
				
				differEntity.setRemark("此件已由" + inEntity.getOperatorName() + "入库至" + orgName);
				differEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_DONE);
				differEntity.setHandleTime(Calendar.getInstance().getTime());
				differEntity.setUserCode(inEntity.getOperatorCode());
				
				stDifferDetailDao.updateByPrimaryKeySelective(differEntity);
				
				isDone = true;
			}else if(CollectionUtils.isNotEmpty(outStockEntityList)){
				InOutStockEntity outEntity = outStockEntityList.get(0);
				String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(outEntity.getOrgCode());
				
				differEntity.setRemark("此件已由" + outEntity.getOperatorName() + "从" + orgName + "出库");
				differEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_DONE);
				differEntity.setHandleTime(Calendar.getInstance().getTime());
				differEntity.setUserCode(outEntity.getOperatorCode());
				
				stDifferDetailDao.updateByPrimaryKeySelective(differEntity);
				
				isDone = true;
			} else {
				//判断是否在本部门再次清仓
				int oaReportHourRule = ConstantsNumberSonar.SONAR_NUMBER_24;
				try{
					ConfigurationParamsEntity defaultBizHourSlice1 = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_PARM__ST_REPORT_OA_ERROR, reportEntity.getDeptcode());
					oaReportHourRule = Integer.valueOf(defaultBizHourSlice1.getConfValue()).intValue();
				}catch(Exception e){
					LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_OA_ERROR.getBizCode());
					jobProcessLogEntity.setRemark("清仓少货上报OA获取配置参数失败失败！");
					jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				}
				//获取再次清仓信息
				List<StTaskEntity> taskList = stResultListDao.querySecScanDetail(differEntity.getWaybillNo(), differEntity.getSerialNo(),reportEntity.getDeptcode(),differEntity.getHandleTime(),oaReportHourRule);
				if(CollectionUtils.isNotEmpty(taskList)) {
					differEntity.setRemark("此件已由"+ taskList.get(0).getCreatorName()+"在清仓任务" + taskList.get(0).getTaskNo() + "中正常扫描！");
					differEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_DONE);
					differEntity.setHandleTime(Calendar.getInstance().getTime());
					differEntity.setUserCode(taskList.get(0).getCreatorCode());
					
					stDifferDetailDao.updateByPrimaryKeySelective(differEntity);
					isDone = true;
				}
			}
			
			if(isDone){
//				判断此条清仓差异明细数据是否为此清仓差异报告的最后一条"未处理"的数据
				long count = stDifferDetailDao.queryStReportDetailCountByReportId(differEntity.getId(), differEntity.getStDifferReportId(), TransferConstants.STOCK_CHECKING_REPORT_DOING);
//				若是，则更新此清仓差异报告的处理状态
				if(count == 0){
					StDifferReportEntity stDifferReportEntity = new StDifferReportEntity();
					stDifferReportEntity.setId(differEntity.getStDifferReportId());
					stDifferReportEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
					
					stDifferReportDao.updateByPrimaryKeySelective(stDifferReportEntity);
				}
			}
		}
	}

	@Override
	public void stReportGapRepairNoStock(LackGoodsFoundEntity lackGoodsFoundEntity) {
//		查询最新的状态，确认是否上报，只处理已上报未结束状态的数据
		StDifferDetailEntity entity = new StDifferDetailEntity();
		entity.setWaybillNo(lackGoodsFoundEntity.getWaybillNo());
		entity.setSerialNo(lackGoodsFoundEntity.getSerialNo());
		entity.setOaErrorNo(lackGoodsFoundEntity.getOaErrorNo());
		entity.setHandleStatus(TransferConstants.STOCK_CHECKING_DOING);
		
		List<StDifferDetailEntity> entityList = stDifferDetailDao.queryDifferDetailListForGapRepair(entity);

		for(StDifferDetailEntity ent: entityList){
			String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(lackGoodsFoundEntity.getDiscovererOrgCode());
			ent.setRemark("此件已由" + lackGoodsFoundEntity.getDiscovererName() + "入库至" + orgName);
			ent.setHandleStatus(TransferConstants.STOCK_CHECKING_DONE);
			ent.setHandleTime(Calendar.getInstance().getTime());
			ent.setUserCode(lackGoodsFoundEntity.getDiscovererCode());
			
			stDifferDetailDao.updateByPrimaryKeySelective(ent);
			
//			判断此条清仓差异明细数据是否为此清仓差异报告的最后一条"未处理"的数据
			long count = stDifferDetailDao.queryStReportDetailCountByReportId(ent.getId(), ent.getStDifferReportId(), TransferConstants.STOCK_CHECKING_REPORT_DOING);
//			若是，则更新此清仓差异报告的处理状态
			if(count == 0){
				StDifferReportEntity stDifferReportEntity = new StDifferReportEntity();
				stDifferReportEntity.setId(ent.getStDifferReportId());
				stDifferReportEntity.setHandleStatus(TransferConstants.STOCK_CHECKING_REPORT_DONE);
				
				stDifferReportDao.updateByPrimaryKeySelective(stDifferReportEntity);
			}
		}
	}
}
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/DeliverLoadGapReportAction.java
 *  
 *  FILE NAME          :DeliverLoadGapReportAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;





import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferCenterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TransferCenterDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadGoodsDetailSerialDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehicleSealInfoDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAWaybillReturnDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.system.utility.StringUtil;

import javax.annotation.Resource;


/**
 * PDA装车接口.
 *
 *
 *
 * @author dp-duyi
 * @date 2012-11-6 下午12:30:11
 */
public class PDADeliverLoadService extends AbstractPDALoadService implements IPDADeliverLoadService{
	// 日志
	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(PDADeliverLoadService.class);

	@Resource
	public IProductService productService4Dubbo;
//	private ITfrJobTodoService tfrJobTodoService;//待办job service接口
	private IBillNumService billNumService;//我的账单编号接口
	
	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}


	/*public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}*/
	
 	/**
 	 * 查询已分配派送装车任务:进行中、未开始.
 	 *查询未完成的中转装车任务2013-04-19修改
 	 *
 	 *
 	 *
 	 *
 	 *
 	 *
 	 * @param condition the condition
 	 * @return the list
 	 * @author dp-duyi
 	 * @date 2012-11-6 下午12:36:47
 	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService#queryAssignedLoadTask(com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity)
 	 */
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	@Override
	public List<PDAAssignLoadTaskEntity> queryAssignedLoadTask(
			QueryAssignedLoadTaskEntity condition) {
		List<PDAAssignLoadTaskEntity> loadList = new ArrayList<PDAAssignLoadTaskEntity>();
		Date currentTime = new Date();
		if(condition.getQueryTimeBegin()==null || condition.getQueryTimeEnd() == null){
			/*SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
			String currentDay = sf.format(new Date());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				condition.setQueryTimeBegin(format.parse(currentDay+" 00:00:00"));
				condition.setQueryTimeEnd(format.parse(currentDay+" 23:59:59"));
			} catch (ParseException e) {
				//记录日志
				LOGGER.error("查询已分配派送装车任务:进行中、未开始", e);
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_PROCEDURE_ERROR_MESSAGECODE);
			}*/
			condition.setQueryTimeBegin(new Date(currentTime.getTime()-LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
			condition.setQueryTimeEnd(currentTime);
		}
		//PDA查询已分配装车任务:进行中、未开始的任务
		List<PDAAssignLoadTaskEntity> deliverLoadList = pdaLoadDao.queryAssignedLoadTask(condition);
		
		if(condition.getQueryTransportTimeBegin() == null || condition.getQueryTransportTimeEnd() == null){
			condition.setQueryTransportTimeBegin(new Date(currentTime.getTime()-LoadConstants.SONAR_NUMBER_3*LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
			condition.setQueryTransportTimeEnd(currentTime);
		}
		if(CollectionUtils.isNotEmpty(deliverLoadList)){
			//根据装车类型来判断
			List<PDAAssignLoadTaskEntity> deliverLoadLists = new ArrayList<PDAAssignLoadTaskEntity>();
			for(PDAAssignLoadTaskEntity loadTask : deliverLoadList){
				//营业部装车
				if(StringUtils.equals(loadTask.getLoadTaskType(), TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
					deliverLoadLists.add(loadTask);
				}
				//非营业部装车
				if(!StringUtils.equals(loadTask.getLoadTaskType(), TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
					deliverLoadLists.add(loadTask);
				}
			}
			loadList.addAll(deliverLoadLists);
		}
		
		List<PDAAssignLoadTaskEntity> transportLoadList = pdaLoadDao.queryUnfinishedLoadTask(condition);
		
		if(CollectionUtils.isNotEmpty(transportLoadList)){
			//存储集合
			List<PDAAssignLoadTaskEntity> deliverLoadLists = new ArrayList<PDAAssignLoadTaskEntity>();
			//营业部装车
			for(PDAAssignLoadTaskEntity loadTask : transportLoadList){
				if(StringUtils.equals(loadTask.getLoadTaskType(), TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
					deliverLoadLists.add(loadTask);
				}
			}
			//非营业部装车
			for(PDAAssignLoadTaskEntity loadTask : transportLoadList){
				if(!StringUtils.equals(loadTask.getLoadTaskType(), TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
					deliverLoadLists.add(loadTask);
				}
			}
			List<String> destOrgCodes;
			List<String> destOrgNames;
			List<LoadDestOrgEntity> dests;
			for(PDAAssignLoadTaskEntity loadTask : deliverLoadLists){
				dests =  pdaLoadDao.queryLoadDestOrgs(loadTask.getId());
				if(CollectionUtils.isNotEmpty(dests)){
					destOrgCodes = new ArrayList<String>();
					destOrgNames = new ArrayList<String>();
					for(LoadDestOrgEntity dest : dests){
						destOrgCodes.add(dest.getDestOrgCode());
						destOrgNames.add(dest.getDestOrgName());
					}
					loadTask.setDestOrgCodes(destOrgCodes);
					loadTask.setDestOrgNames(destOrgNames);
				}
				
			}
			loadList.addAll(deliverLoadLists);
		}
		return loadList;
	}
	
	/**
	 * 派送装车未装车备注.
	 *
	 *
	 *
	 *
	 *
	 * @param notesRecord the notes record
	 * 
	 * @return the string
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-12-11 上午11:38:48
	 * 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService#deliverLoadNotes(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto)
	 */
	@Override
	public String deliverLoadNotes(LoadScanDetailDto notesRecord) {
		//查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(notesRecord.getTaskNo());
		//只有装车任务状态为装车中的任务可以提交
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			//查询运单明细
			LoadWaybillDetailEntity loadTaskWayBillQC = new LoadWaybillDetailEntity();
			loadTaskWayBillQC.setLoadTaskId(loadTask.getId());
			loadTaskWayBillQC.setWaybillNo(notesRecord.getWayBillNo());
			LoadWaybillDetailEntity loadWayBillEntity = pdaLoadDao.queryLoadWaybillDetailEntityByWayBillNo(loadTaskWayBillQC);
			if(loadWayBillEntity != null){//如果装车件数大于预配件数，则提示失败
				loadWayBillEntity.setNotes(notesRecord.getNotes());
				loadWayBillEntity.setLoadQty(0);
				loadWayBillEntity.setScanQty(0);
				loadWayBillEntity.setLoadVolumeTotal(0);
				loadWayBillEntity.setLoadWeightTotal(0);
				pdaLoadDao.updateLoadTaskWayBillDetail(loadWayBillEntity);
				return TransferPDADictConstants.SUCCESS;
				//sonar 311396 2016年12月21日10:16:38 两部分代码一致
				/*if(loadWayBillEntity.getLoadQty() >= loadWayBillEntity.getStockQty()){
					loadWayBillEntity.setNotes(notesRecord.getNotes());
					loadWayBillEntity.setLoadQty(0);
					loadWayBillEntity.setScanQty(0);
					loadWayBillEntity.setLoadVolumeTotal(0);
					loadWayBillEntity.setLoadWeightTotal(0);
					pdaLoadDao.updateLoadTaskWayBillDetail(loadWayBillEntity);
					return TransferPDADictConstants.SUCCESS;
					//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_REPEAT_OPERATION_MESSAGECODE);
				}else{
					loadWayBillEntity.setNotes(notesRecord.getNotes());
					loadWayBillEntity.setLoadQty(0);
					loadWayBillEntity.setScanQty(0);
					loadWayBillEntity.setLoadVolumeTotal(0);
					loadWayBillEntity.setLoadWeightTotal(0);
					pdaLoadDao.updateLoadTaskWayBillDetail(loadWayBillEntity);
					return TransferPDADictConstants.SUCCESS;
				}*/
			}else{
				loadWayBillEntity = new LoadWaybillDetailEntity();
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date loadStartTime = new Date();
				try {
					loadStartTime = df.parse(loadTask.getLoadStartTime());
				} catch (ParseException e) {
					//记录日志
					LOGGER.error("派送装车备注，装车开始时间转化失败", e);
				}
				WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(notesRecord.getWayBillNo());
				ProductEntity product = productService4Dubbo.getProductByCache(wayBill.getProductCode(), null);
				//生成id
				String loadWaybillDetailId = UUIDUtils.getUUID();
				//设置id
				loadWayBillEntity.setId(loadWaybillDetailId);
				//设置 是否合车
				loadWayBillEntity.setBeJoinCar("N");
				
				//设置 已装车件数
				loadWayBillEntity.setLoadQty(0);
				//设置 装车体积
				loadWayBillEntity.setLoadVolumeTotal(0);
				//设置 装车重量
				loadWayBillEntity.setLoadWeightTotal(0);
				//设置 已扫描件数
				loadWayBillEntity.setScanQty(0);
				//设置 运单号
				loadWayBillEntity.setWaybillNo(notesRecord.getWayBillNo());
				//设置 loadTask_ID
				loadWayBillEntity.setLoadTaskId(loadTask.getId());
				//设置 出发部门编号
				loadWayBillEntity.setOrigOrgCode(loadTask.getOrigOrgCode());
				//设置 建立任务时间
				loadWayBillEntity.setTaskBeginTime(loadStartTime);
				//设置 建立任务时间
				loadWayBillEntity.setNotes(notesRecord.getNotes());
				if(wayBill != null){
					//设置 货名
					loadWayBillEntity.setGoodsName(wayBill.getGoodsName());
					//设置 库存件数
					List<Integer> stockQty = pdaLoadDao.queryDeliverStockQty(notesRecord.getTaskNo(), notesRecord.getWayBillNo());
					if(CollectionUtils.isNotEmpty(stockQty)){
						loadWayBillEntity.setStockQty(stockQty.get(0));
					}else{
						loadWayBillEntity.setStockQty(0);
						//throw new TfrBusinessException(notesRecord.getWayBillNo()+"预排单件数为0");
					}
					//设置 包装
					loadWayBillEntity.setPack(wayBill.getGoodsPackage());
					//设置 到达部门
					loadWayBillEntity.setReachOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(wayBill.getCustomerPickupOrgCode()));
					//设置 收货部门*.
					loadWayBillEntity.setReceiveOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(wayBill.getReceiveOrgCode()));
					//设置 运输性质
					if(product != null){
						loadWayBillEntity.setTransportType(product.getName());
					}
				}
				//插入装车流水号明细记录
				pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBillEntity);
				return TransferPDADictConstants.SUCCESS;
			}
		}else{
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		
	}
	/**
	 * 建立派送装车任务.
	 *
	 *
	 *
	 *
	 *
	 * @param loadTask the load task
	 * 
	 * @return the list
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-12-15 上午10:47:13
	 * 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#createLoadTask(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public LoadTaskResultDto createLoadTask(LoadTaskDto loadTask) {
		VehicleAssociationDto vehicleDto = null;
		//车牌号不能为空
		if(StringUtils.isNotBlank(loadTask.getVehicleNo())){
			try{
				vehicleDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(loadTask.getVehicleNo());
			}catch(Exception e){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
			}
		}
		PDATaskEntity pdaEntity = new PDATaskEntity();
		Date loadBeginTime = new Date();
		String loadTaskNo;
		List<LoaderDto> loaderCodes = new ArrayList<LoaderDto>();
		LoadTaskEntity loadTaskEntity;
		LoaderParticipationEntity creator = null;
		//如果任务编号不为空
		if(StringUtils.isNotBlank(loadTask.getTaskNo())){
			loadTaskNo = loadTask.getTaskNo();
			//查询装车任务
			loadTaskEntity = pdaLoadDao.queryLoadTaskByTaskNo(loadTaskNo);
			//只有装车状态为装车中的任务可以下拉装车清单
			if(loadTaskEntity != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTaskEntity.getState())){
				LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
				loaderQC.setTaskId(loadTaskEntity.getId());
				List<LoaderParticipationEntity> loaders = pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
				if(CollectionUtils.isNotEmpty(loaders)){
					LoaderDto loaderCode;
					for(LoaderParticipationEntity loader : loaders){
						loaderCode = new LoaderDto();
						loaderCode.setLoaderCode(loader.getLoaderCode());
						loaderCode.setFlag(loader.getFlag());
						loaderCodes.add(loaderCode);
						if(FossConstants.YES.equals(loader.getBeCreator())){
							creator = loader;
						}
					}
				}
				
				pdaEntity.setBeCreator("N");
			}else{
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
			}		
		}else{//如果任务编号为空，则新建装车任务
			LOGGER.error("建立派送装车任务开始"+loadTask.getDeliverBillNo()+" "+loadTask.getVehicleNo());
			if(vehicleDto == null){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
			}
			//获取车辆封签
			VehicleSealInfoDto seal = vehicleSealService.queryVehicleSealInfo(loadTask.getVehicleNo());
			//如果车辆已封签，则不能建立装车任务
			if(seal != null){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_SEALED1); 
			}
			String deliverBillState = assignLoadTaskDao.queryDeliverBillState(loadTask.getDeliverBillNo());
			//如果派送单状态不等于已提交或已分配，则不能建立装车任务
			if(!(DeliverbillConstants.STATUS_SUBMITED.equals(deliverBillState) || DeliverbillConstants.STATUS_ASSIGNED.equals(deliverBillState))){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_DELIVER_BILL_MESSAGECODE);
			}
			String assignState = assignLoadTaskDao.queryAssignState(loadTask.getDeliverBillNo(), loadTask.getOperatorCode());
			if(StringUtils.isBlank(assignState)){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_DELIVER_BILL_MESSAGECODE);
			}
			//是否建立任务PDA为是
			pdaEntity.setBeCreator("Y");
			loadTaskEntity = new LoadTaskEntity();
			List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
			LoaderParticipationEntity loader;
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//调用综合接口查询出发部门
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(loadTask.getCreateOrgCode());
			if(origOrg != null){
				//如果是营业部或派送部
				if(FossConstants.YES.equals(origOrg.getSalesDepartment())){
					SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
					if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){
						//origOrg = pdaCommonService.getCurrentOutfieldCode(loadTask.getCreateOrgCode());
						origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
					}
				}else{
					//查找对应外场
					origOrg = pdaCommonService.getCurrentOutfieldCode(origOrg.getCode());
				}
			}
			if(origOrg == null){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}
			String loadTaskId = UUIDUtils.getUUID();
			//TODO 最早出发时间
			//Date lastedDepartTime = new Date();
			//调用综合接口查询月台虚拟编码
			if(StringUtils.isNotBlank(loadTask.getPlatformNo())){
				if(origOrg != null){
					//组织编码不为空且是外场，才查询月台
					if(StringUtils.isNotBlank(origOrg.getCode()) && FossConstants.YES.equals(origOrg.getTransferCenter())){
						PlatformEntity plateform = platformService.queryPlatformByCode(origOrg.getCode(), loadTask.getPlatformNo());
						if(plateform != null){
							loadTaskEntity.setPlatformId(plateform.getVirtualCode());
						}else{
							throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_PLATEFORM);
						}
					}
				}
				loadTaskEntity.setPlatformNo(loadTask.getPlatformNo());
			}
			//生成任务编号
			//loadTaskNo = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.PSZC,origOrg.getCode());
			loadTaskNo = billNumService.generateDeliverTaskNo(origOrg.getCode());//产生派送任务编号
			LOGGER.error("建立派送装车任务开始:"+loadTaskNo);
			if(StringUtils.isNotBlank(loadTaskEntity.getPlatformId())){
				// 调用月台接口修改月台状态
				/*
				 * 占用月台
				 */
				//调用月台服务，占用月台
				/*try{
					pdaCommonService.updatePlatformStateByCreateTask(loadTaskEntity.getPlatformId(),loadTask.getVehicleNo(),loadTaskNo,origOrg.getCode(),
							PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE,
							PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_LOAD,loadBeginTime,lastedDepartTime);
				}catch(Exception e){
					
				}*/
			} 
			//装车任务
			loadTaskEntity.setId(loadTaskId);
			loadTaskEntity.setLoadStartTime(df.format(loadBeginTime));
			loadTaskEntity.setOrigOrgCode(origOrg.getCode());
			loadTaskEntity.setDeliverBillNo(loadTask.getDeliverBillNo());
			//调用综合接口查询部门名称
			if(origOrg != null){
				loadTaskEntity.setOrigOrgName(origOrg.getName());
			}
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			loadTaskEntity.setTaskNo(loadTaskNo);
			loadTaskEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_DELIVER);
			loadTaskEntity.setVehicleNo(loadTask.getVehicleNo());
			loadTaskEntity.setBeCreateGapRep(FossConstants.NO);
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			//理货员
			PorterEntity porter;
			for(LoaderDto loaderCode : loadTask.getLoaderCodes()){
				loader = new LoaderParticipationEntity();
				loader.setJoinTime(loadBeginTime);
				loader.setFlag(loaderCode.getFlag());
				loader.setId(UUIDUtils.getUUID());
				loader.setTaskId(loadTaskId);
				loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_DELIVER_LOAD);
				loader.setLoaderCode(loaderCode.getLoaderCode());
				porter = porterService.queryPorterByEmpCode(loaderCode.getLoaderCode());
				//调用综合接口查询理货员名称、理货员所属装卸车队
				if(porter != null){
					loader.setLoaderName(porter.getEmpName());
					if(StringUtils.isNotBlank(porter.getParentOrgCode())){
						loader.setLoadOrgCode(porter.getParentOrgCode());
						LoadAndUnloadSquadEntity team = loadAndUnloadSquadService.queryLoadAndUnloadSquadByCode(porter.getParentOrgCode());
						if(team != null){
							loader.setLoadOrgName(team.getName());
						}else{
							//非法理货员
							//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
						}
					}
				}else{
					EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loaderCode.getLoaderCode());
					if(emp != null){
						loader.setLoaderName(emp.getEmpName());
					}else{
						throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
					}
					//非法理货员
					//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
				}
				if(loaderCode.getLoaderCode().equals(loadTask.getOperatorCode())){
					loader.setBeCreator(FossConstants.YES);
					creator = loader;
				}else{
					loader.setBeCreator(FossConstants.NO);
				}
				loaders.add(loader);
				LoaderDto loaderItem = new LoaderDto();
				loaderItem.setLoaderCode(loader.getLoaderCode());
				loaderItem.setFlag(loader.getFlag());
				loaderCodes.add(loaderItem);
			}
			if(CollectionUtils.isNotEmpty(loaders)){
				//插入理货员
				pdaLoadDao.insertTransferLoaderParticipation(loaders);
				//参加人数
				loadTaskEntity.setLoaderQty(loaders.size());
			}
			//插入装车任务
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			//更新派送单状态为装车中
			DeliverBillEntity bill = new DeliverBillEntity();
			bill.setBillNo(loadTask.getDeliverBillNo());
			bill.setState(DeliverbillConstants.STATUS_LOADING);
			int updateQty = assignLoadTaskDao.updateDeliverBillState(bill,DeliverbillConstants.STATUS_ASSIGNED);
			if(updateQty != 1){
				throw new TfrBusinessException("数据过期，请重新下拉装车任务");
			}else{
				//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
				if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState()) ) {
					DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
					deliverBillVary.setDeliverBillNo(bill.getBillNo());//派送单号
					deliverBillVary.setDeliverBillStatus(bill.getState());//派送单状态
					deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
				}
			}
			//更新分配装车任务状态为进行中
			Map<String,String> condition  = new HashMap<String,String>();
			//是否取消：否
			condition.put("beCancelled", FossConstants.NO);
			//进行中
			condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
			//未开始
			condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
			//派送单号
			condition.put("deliverBillNo", loadTask.getDeliverBillNo());
			//更新分配记录状态：进行中、已完成
			assignLoadTaskDao.updateAssignedLoadTaskState(condition);
		}
		//插入装车PDA
		pdaEntity.setTaskNo(loadTaskNo);
		pdaEntity.setDeviceNo(loadTask.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		//派送装车-DELIVER_LOAD
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_DELIVER_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		
		LOGGER.error("建立派送装车任务结束"+loadTask.getDeliverBillNo()+" "+loadTask.getVehicleNo()+" "+loadTaskNo);
		return this.createLoadTaskResult(loadTaskNo,loadTaskEntity.getOrigOrgCode(), creator, loaderCodes, vehicleDto);
	}
	/**
	 * 刷新派送装车任务.
	 *
	 *
	 *
	 *
	 * @param taskNo the task no
	 * 
	 * @return the list
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-12-15 上午10:47:13
	 * 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#refrushLoadTaskDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskDetail(String taskNo) {
		LOGGER.error("刷洗装车任务开始:"+taskNo);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		//只有装车状态为装车中的任务可以下拉装车清单
		if(tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())){
			//多货装车清单
			List<LoadGoodsDetailSerialDto> moreGoodsLoadGoodsDetails = pdaLoadDao.refrushMoreGoodsLoadTaskDetail(taskNo,null);
			//派送装车库存清单
			List<LoadGoodsDetailSerialDto> stockGoodsList = pdaLoadDao.refrushNormalDeliverLoadTaskDetail(taskNo);
			List<LoadGoodsDetailSerialDto> loadGoodsList = new ArrayList<LoadGoodsDetailSerialDto>();
			if(CollectionUtils.isNotEmpty(stockGoodsList)){
				loadGoodsList.addAll(stockGoodsList);
			}
			if(CollectionUtils.isNotEmpty(moreGoodsLoadGoodsDetails)){
				loadGoodsList.addAll(moreGoodsLoadGoodsDetails);
			}
			List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
			String wayBillNo = "";
			int stockWayBillQty = 0;
			String isHaveWaitforarea="N";
			//判断是否为外场，及外场有无待叉区（调用综合接口）
			String orgCode = tempLoadTask.getOrigOrgCode();//当前部门编码
			TransferCenterDto transferCenterDto=new TransferCenterDto();//查询外场实体参数
			transferCenterDto.setOrgCode(orgCode);//设置参数
			transferCenterDto.setActive(FossConstants.YES);
			/**调用综合接口，查询外场实体*/
			List<TransferCenterEntity> orgEntityList= commonTransferCenterService.queryTransferCenterByCondition(transferCenterDto,1,0);
			if(orgEntityList!=null&&orgEntityList.size()>0){//如果不为空，
				TransferCenterEntity transferCenterEntity=orgEntityList.get(0);
				if(transferCenterEntity!=null&&StringUtils.equals(transferCenterEntity.getIsHaveWaitforarea(), FossConstants.YES)){
					//如果有待叉区则设置为Y
					isHaveWaitforarea="Y";
				}
			}
			
			
			//遍历
			for(LoadGoodsDetailSerialDto loadGoodsDetailDto : loadGoodsList){
				//去除大于预配件数的流水号
				if(!wayBillNo.equals(loadGoodsDetailDto.getWayBillNo())){
					wayBillNo = loadGoodsDetailDto.getWayBillNo();
					stockWayBillQty = 0;
				}
				//判定货物位置
				if(null !=loadGoodsDetailDto.getSerialNo()){
					if(StringUtils.equals(isHaveWaitforarea, FossConstants.YES)){
						//查询是否进行叉车扫描
						Long count=trayScanService.queryTrayScanTaskDtailCount(loadGoodsDetailDto.getWayBillNo(), loadGoodsDetailDto.getSerialNo(), orgCode);
						if(count>0){
							loadGoodsDetailDto.setGoodsPosition("派送库区");
						}else{
							loadGoodsDetailDto.setGoodsPosition("待叉区");
						}
					}else{
						loadGoodsDetailDto.setGoodsPosition("派送库区");
					}
				}else{
					loadGoodsDetailDto.setGoodsPosition("未卸");
				}
				
				if(++stockWayBillQty <= loadGoodsDetailDto.getStockQty()){
					loadGoodsDetails.add(loadGoodsDetailDto);
				}
				
			}
			List<LoadGoodsDetailDto> result = this.loadSerialDetailToWayBillDetail(loadGoodsDetails,tempLoadTask.getOrigOrgCode());
			//if(CollectionUtils.isNotEmpty(result)){
				LOGGER.error("刷洗装车任务结束:"+taskNo);
				return result;
			//}else{
				//LOGGER.error("刷洗装车任务结束:"+taskNo);
				//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
			//}
		}else{
			LOGGER.error("刷洗装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	/**
	 * 派送装车运单退回
	 *
	 * @param PDAWaybillReturnDto   PDA派送装车运单退回dto
	 * @date 2015-05-04 上午10:29:40
	 */
	@Override
	@Transactional
	public void waybillReturn(PDAWaybillReturnDto pdaWaybillReturnDto) {
		//非空校验
		if(null == pdaWaybillReturnDto) {
			LOGGER.error("PDA传的参数为空！");
			throw new TfrBusinessException("PDA传的参数为空！");
		}
		if(StringUtil.equal("0", pdaWaybillReturnDto.getRetreatType())) {
			pdaWaybillReturnDto.setReturnReason("少货/无货");
		}else if (StringUtil.equal("1", pdaWaybillReturnDto.getRetreatType())) {
			pdaWaybillReturnDto.setReturnReason("异形货（超长/超方）");
		}else if (StringUtil.equal("2", pdaWaybillReturnDto.getRetreatType())) {
			pdaWaybillReturnDto.setReturnReason("排单过多，装不下");
		}else if (StringUtil.equal("3", pdaWaybillReturnDto.getRetreatType())) {
			pdaWaybillReturnDto.setReturnReason("托盘货/木架货");
		}else if(StringUtil.equal("4", pdaWaybillReturnDto.getRetreatType())) {
			pdaWaybillReturnDto.setReturnReason("其他："+pdaWaybillReturnDto.getRetreatReason());
		}else {
			pdaWaybillReturnDto.setReturnReason(pdaWaybillReturnDto.getRetreatType());
		}
		pdaWaybillReturnDto.setOperateQty(0);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(pdaWaybillReturnDto.getLoadTaskNo());
		//调用PKP接口
		try {
			String returns = visibleOrderService.deleteDeliverDetailForPDA(pdaWaybillReturnDto.getWaybillNo(), tempLoadTask.getDeliverBillNo(), pdaWaybillReturnDto.getReturnReason());
			if(StringUtils.equals(returns, "0")) {
				throw new TfrBusinessException("调用结算接口报错");
			}
		} catch (Exception e) {
			LOGGER.error("" + e);
			throw new TfrBusinessException(e.toString());
		}
		//插入派送装车运单退回表
		pdaLoadDao.insertLoadWaybillReturn(pdaWaybillReturnDto);
	}

	
	/**
	 * 建立派送装车任务.
	 * @param loadTask the load task
	 * @return the list
	 * @author dp-332219
	 * @date 2016-11-21
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW) 
	public LoadSaleTaskResultDto createSaleLoadTask(LoadSaleTaskDto loadTask) {
		VehicleAssociationDto vehicleDto = null;
		/**
		 * 营业部交接不用校验车牌号
		 * 车牌号不能为空
		 */
		/*if(StringUtils.isNotBlank(loadTask.getVehicleNo())){
			try{ 
				vehicleDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(loadTask.getVehicleNo());
			}catch(Exception e){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
			}
		}*/
		PDATaskEntity pdaEntity = new PDATaskEntity();
		Date loadBeginTime = new Date();
		String loadTaskNo;
		List<LoaderDto> loaderCodes = new ArrayList<LoaderDto>();
		LoadTaskEntity loadTaskEntity;
		LoaderParticipationEntity creator = null;
		//如果任务编号不为空
		if(StringUtils.isNotBlank(loadTask.getTaskNo())){
			loadTaskNo = loadTask.getTaskNo();
			//查询装车任务
			loadTaskEntity = pdaLoadDao.queryLoadTaskByTaskNo(loadTaskNo);
			//只有装车状态为装车中的任务可以下拉装车清单
			if(loadTaskEntity != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTaskEntity.getState())){
				LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
				loaderQC.setTaskId(loadTaskEntity.getId());
				List<LoaderParticipationEntity> loaders = pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
				if(CollectionUtils.isNotEmpty(loaders)){
					LoaderDto loaderCode;
					for(LoaderParticipationEntity loader : loaders){
						loaderCode = new LoaderDto();
						loaderCode.setLoaderCode(loader.getLoaderCode());
						loaderCode.setFlag(loader.getFlag());
						loaderCodes.add(loaderCode);
						if(FossConstants.YES.equals(loader.getBeCreator())){
							creator = loader;
						}
					}
				}
				
				pdaEntity.setBeCreator("N");
			}else{
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
			}		
		}else{//如果任务编号为空，则新建装车任务
			LOGGER.error("建立派送装车任务开始"+loadTask.getDeliverBillNo()+" "+loadTask.getVehicleNo());
			//营业部交接不需校验车牌号
			/*if(vehicleDto == null){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
			}*/
			/**
			 * 获取车辆封签
			 * 营业部交接不需要校验
			 */
			/*VehicleSealInfoDto seal = vehicleSealService.queryVehicleSealInfo(loadTask.getVehicleNo());
			//如果车辆已封签，则不能建立装车任务
			if(seal != null){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_SEALED1); 
			}*/
			String deliverBillState = assignLoadTaskDao.queryDeliverBillState(loadTask.getDeliverBillNo());
			//如果派送单状态不等于已提交或已分配，则不能建立装车任务
			if(!(DeliverbillConstants.STATUS_SUBMITED.equals(deliverBillState) || DeliverbillConstants.STATUS_ASSIGNED.equals(deliverBillState))){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_DELIVER_BILL_MESSAGECODE);
			}
			String assignState = assignLoadTaskDao.queryAssignState(loadTask.getDeliverBillNo(), loadTask.getOperatorCode());
			if(StringUtils.isBlank(assignState)){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_DELIVER_BILL_MESSAGECODE);
			}
			//是否建立任务PDA为是
			pdaEntity.setBeCreator("Y");
			loadTaskEntity = new LoadTaskEntity();
			List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
			LoaderParticipationEntity loader;
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//调用综合接口查询出发部门
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(loadTask.getCreateOrgCode());
			if(origOrg != null){
				//营业部交接的出发部门是营业部
				SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
				if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){
					origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
				}
			}
			if(origOrg == null){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}
			String loadTaskId = UUIDUtils.getUUID();
			//营业部没有月台
			//调用综合接口查询月台虚拟编码
			/*if(StringUtils.isNotBlank(loadTask.getPlatformNo())){
				if(origOrg != null){
					//组织编码不为空且是外场，才查询月台
					if(StringUtils.isNotBlank(origOrg.getCode()) && FossConstants.YES.equals(origOrg.getTransferCenter())){
						PlatformEntity plateform = platformService.queryPlatformByCode(origOrg.getCode(), loadTask.getPlatformNo());
						if(plateform != null){
							loadTaskEntity.setPlatformId(plateform.getVirtualCode());
						}else{
							throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_PLATEFORM);
						}
					}
				}
				loadTaskEntity.setPlatformNo(loadTask.getPlatformNo());
			}*/
			//生成任务编号
			//loadTaskNo = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.PSZC,origOrg.getCode());
			loadTaskNo = billNumService.generateDeliverTaskNo(origOrg.getCode());//产生派送任务编号
			LOGGER.error("建立派送装车任务开始:"+loadTaskNo);
			if(StringUtils.isNotBlank(loadTaskEntity.getPlatformId())){
				// 调用月台接口修改月台状态
				/*
				 * 占用月台
				 */
				//调用月台服务，占用月台
				/*try{
					pdaCommonService.updatePlatformStateByCreateTask(loadTaskEntity.getPlatformId(),loadTask.getVehicleNo(),loadTaskNo,origOrg.getCode(),
							PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE,
							PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_LOAD,loadBeginTime,lastedDepartTime);
				}catch(Exception e){
					
				}*/
			} 
			//装车任务
			loadTaskEntity.setId(loadTaskId);
			loadTaskEntity.setLoadStartTime(df.format(loadBeginTime));
			loadTaskEntity.setOrigOrgCode(origOrg.getCode());
			loadTaskEntity.setDeliverBillNo(loadTask.getDeliverBillNo());
			//调用综合接口查询部门名称
			if(origOrg != null){
				loadTaskEntity.setOrigOrgName(origOrg.getName());
			}
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			loadTaskEntity.setTaskNo(loadTaskNo);
			loadTaskEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_DELIVER);
			loadTaskEntity.setVehicleNo(loadTask.getVehicleNo());
			loadTaskEntity.setBeCreateGapRep(FossConstants.NO);
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			//理货员
			PorterEntity porter;
			for(LoaderDto loaderCode : loadTask.getLoaderCodes()){
				loader = new LoaderParticipationEntity();
				loader.setJoinTime(loadBeginTime);
				loader.setFlag(loaderCode.getFlag());
				loader.setId(UUIDUtils.getUUID());
				loader.setTaskId(loadTaskId);
				loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_DELIVER_LOAD);
				loader.setLoaderCode(loaderCode.getLoaderCode());
				porter = porterService.queryPorterByEmpCode(loaderCode.getLoaderCode());
				//调用综合接口查询理货员名称、理货员所属装卸车队
				if(porter != null){
					loader.setLoaderName(porter.getEmpName());
					if(StringUtils.isNotBlank(porter.getParentOrgCode())){
						loader.setLoadOrgCode(porter.getParentOrgCode());
						LoadAndUnloadSquadEntity team = loadAndUnloadSquadService.queryLoadAndUnloadSquadByCode(porter.getParentOrgCode());
						if(team != null){
							loader.setLoadOrgName(team.getName());
						}else{
							//非法理货员
							//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
						}
					}
				}else{
					EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loaderCode.getLoaderCode());
					if(emp != null){
						loader.setLoaderName(emp.getEmpName());
					}else{
						throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
					}
					//非法理货员
					//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
				}
				if(loaderCode.getLoaderCode().equals(loadTask.getOperatorCode())){
					loader.setBeCreator(FossConstants.YES);
					creator = loader;
				}else{
					loader.setBeCreator(FossConstants.NO);
				}
				loaders.add(loader);
				LoaderDto loaderItem = new LoaderDto();
				loaderItem.setLoaderCode(loader.getLoaderCode());
				loaderItem.setFlag(loader.getFlag());
				loaderCodes.add(loaderItem);
			}
			if(CollectionUtils.isNotEmpty(loaders)){
				//插入理货员
				pdaLoadDao.insertTransferLoaderParticipation(loaders);
				//参加人数
				loadTaskEntity.setLoaderQty(loaders.size());
			}
			//插入装车任务
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			//更新派送单状态为装车中
			DeliverBillEntity bill = new DeliverBillEntity();
			bill.setBillNo(loadTask.getDeliverBillNo());
			bill.setState(DeliverbillConstants.STATUS_LOADING);
			int updateQty = assignLoadTaskDao.updateDeliverBillState(bill,DeliverbillConstants.STATUS_ASSIGNED);
			if(updateQty != 1){
				throw new TfrBusinessException("数据过期，请重新下拉装车任务");
			}else{
				//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
				if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState()) ) {
					DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
					deliverBillVary.setDeliverBillNo(bill.getBillNo());//派送单号
					deliverBillVary.setDeliverBillStatus(bill.getState());//派送单状态
					deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
				}
			}
			//更新分配装车任务状态为进行中
			Map<String,String> condition  = new HashMap<String,String>();
			//是否取消：否
			condition.put("beCancelled", FossConstants.NO);
			//进行中
			condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
			//未开始
			condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
			//派送单号
			condition.put("deliverBillNo", loadTask.getDeliverBillNo());
			//更新分配记录状态：进行中、已完成
			assignLoadTaskDao.updateAssignedLoadTaskState(condition);
		}
		//插入装车PDA
		pdaEntity.setTaskNo(loadTaskNo);
		pdaEntity.setDeviceNo(loadTask.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		//派送装车-DELIVER_LOAD
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_DELIVER_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		
		LOGGER.error("建立派送装车任务结束"+loadTask.getDeliverBillNo()+" "+loadTask.getVehicleNo()+" "+loadTaskNo);
		return this.createSaleLoadTaskResult(loadTaskNo,loadTaskEntity.getOrigOrgCode(), creator, loaderCodes, vehicleDto);
	}
	
	/**
	 * 刷新派送装车任务.
	 * @param taskNo the task no
	 * @return the list
	 * @author dp-332219
	 * @date 2016-11-21
	 */
	@Override
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskDetail(String taskNo) {
		LOGGER.error("刷新装车任务开始:"+taskNo);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		//只有装车状态为装车中的任务可以下拉装车清单
		if(tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())){
			//多货装车清单
			List<LoadGoodsDetailSerialDto> moreGoodsLoadGoodsDetails = pdaLoadDao.refrushMoreGoodsLoadTaskDetail(taskNo,null);
			//派送装车库存清单
			List<LoadGoodsDetailSerialDto> stockGoodsList = pdaLoadDao.refrushNormalDeliverLoadTaskDetail(taskNo);
			List<LoadGoodsDetailSerialDto> loadGoodsList = new ArrayList<LoadGoodsDetailSerialDto>();
			if(CollectionUtils.isNotEmpty(stockGoodsList)){
				loadGoodsList.addAll(stockGoodsList);
			}
			if(CollectionUtils.isNotEmpty(moreGoodsLoadGoodsDetails)){
				loadGoodsList.addAll(moreGoodsLoadGoodsDetails);
			}
			List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
			String wayBillNo = "";
			int stockWayBillQty = 0;
			String isHaveWaitforarea="N";
			//判断是否为外场，及外场有无待叉区（调用综合接口）
			String orgCode = tempLoadTask.getOrigOrgCode();//当前部门编码
			//营业部交接不涉及外场
			//TransferCenterDto transferCenterDto=new TransferCenterDto();//查询外场实体参数
			//transferCenterDto.setOrgCode(orgCode);//设置参数
			//transferCenterDto.setActive(FossConstants.YES);
			/**调用综合接口，查询外场实体*/
			/*List<TransferCenterEntity> orgEntityList= commonTransferCenterService.queryTransferCenterByCondition(transferCenterDto,1,0);
			if(orgEntityList!=null&&orgEntityList.size()>0){//如果不为空，
				TransferCenterEntity transferCenterEntity=orgEntityList.get(0);
				if(transferCenterEntity!=null&&StringUtils.equals(transferCenterEntity.getIsHaveWaitforarea(), FossConstants.YES)){
					//如果有待叉区则设置为Y
					isHaveWaitforarea="Y";
				}
			}*/
			
			//遍历
			for(LoadGoodsDetailSerialDto loadGoodsDetailDto : loadGoodsList){
				//去除大于预配件数的流水号
				if(!wayBillNo.equals(loadGoodsDetailDto.getWayBillNo())){
					wayBillNo = loadGoodsDetailDto.getWayBillNo();
					stockWayBillQty = 0;
				}
				//判定货物位置
				if(null !=loadGoodsDetailDto.getSerialNo()){
					if(StringUtils.equals(isHaveWaitforarea, FossConstants.YES)){
						//查询是否进行叉车扫描
						Long count=trayScanService.queryTrayScanTaskDtailCount(loadGoodsDetailDto.getWayBillNo(), loadGoodsDetailDto.getSerialNo(), orgCode);
						if(count>0){
							loadGoodsDetailDto.setGoodsPosition("派送库区");
						}else{
							loadGoodsDetailDto.setGoodsPosition("待叉区");
						}
					}else{
						loadGoodsDetailDto.setGoodsPosition("派送库区");
					}
				}else{
					loadGoodsDetailDto.setGoodsPosition("未卸");
				}
				
				if(++stockWayBillQty <= loadGoodsDetailDto.getStockQty()){
					loadGoodsDetails.add(loadGoodsDetailDto);
				}
				
			}
			List<LoadSaleGoodsDetailDto> result = this.loadSaleSerialDetailToWayBillDetail(loadGoodsDetails,tempLoadTask.getOrigOrgCode());
			LOGGER.error("刷洗装车任务结束:"+taskNo);
			return result;
		}else{
			LOGGER.error("刷洗装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}

	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}
}

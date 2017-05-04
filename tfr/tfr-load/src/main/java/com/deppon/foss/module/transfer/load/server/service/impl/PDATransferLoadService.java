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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDeptTransferMappingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadGoodsDetailSerialDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehicleSealInfoDto;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * PDA中转装车.
 *
 *
 * @author dp-duyi
 * @date 2012-11-19 上午10:34:54
 */
public class PDATransferLoadService extends AbstractPDALoadService{
	static final Logger logger = LoggerFactory.getLogger(PDATransferLoadService.class);
	
	private ILineService lineService;
	private IExpressLineService expresslineService ;
	private ITruckDepartPlanDetailService truckDepartPlanDetailService;
	private IVehicleAgencyCompanyService vehicleAgencyCompanyService;
	private IStowagePlansService stowagePlansService;
	private IBillNumService billNumService;
	/** 
	 * 合伙人映射
	 * */
	private IDeptTransferMappingService deptTransferMappingService;
	
	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}
	public void setTruckDepartPlanDetailService(
			ITruckDepartPlanDetailService truckDepartPlanDetailService) {
		this.truckDepartPlanDetailService = truckDepartPlanDetailService;
	}
	public void setVehicleAgencyCompanyService(
			IVehicleAgencyCompanyService vehicleAgencyCompanyService) {
		this.vehicleAgencyCompanyService = vehicleAgencyCompanyService;
	}
	public void setStowagePlansService(IStowagePlansService stowagePlansService) {
		this.stowagePlansService = stowagePlansService;
	}
	
	public void setDeptTransferMappingService(
			IDeptTransferMappingService deptTransferMappingService) {
		this.deptTransferMappingService = deptTransferMappingService;
	}
	
	private IExpressVehiclesService expressVehiclesService;
	public final void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
	/**
	 * PDA建立装车任务接口.
	 *
	 *
	 *
	 * @param loadTask the load task
	 * 
	 * @return the list
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-19 上午10:35:48
	 * 
	 * 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#createTransferLoadTask(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto)
	 */
	@Override
	@Transactional
	public LoadTaskResultDto createLoadTask(LoadTaskDto loadTask) {
		//如果车辆已封签，则不能建立装车任务
		VehicleAssociationDto vehicleDto = null;
		if(StringUtils.isNotBlank(loadTask.getVehicleNo())){
			if(loadTask.getVehicleNo().startsWith("德")){
				ExpressVehiclesEntity entity=new ExpressVehiclesEntity();
				entity.setVehicleNo(loadTask.getVehicleNo());
				entity.setActive("Y");
				List<ExpressVehiclesEntity> expressVehiclesEntitys = expressVehiclesService.queryExpressVehiclesByEntity(entity);
				if(null ==expressVehiclesEntitys ||expressVehiclesEntitys.size()<=0){
					throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
				}
			}else{
				try{
					vehicleDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(loadTask.getVehicleNo());
				}catch(Exception e){
					throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
				}
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
				
				pdaEntity.setBeCreator(FossConstants.NO);
			}else{
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
			}		
		}else{//如果任务编号为空，则新建装车任务
			LOGGER.error("建立装车任务开始"+loadTask.getVehicleNo());
			if(CollectionUtils.isNotEmpty(loadTask.getDestOrgCodes())){
				if(loadTask.getDestOrgCodes().size()>1){
					throw new TfrBusinessException("禁止输入多个到达部门!");
				}
				for(String d : loadTask.getDestOrgCodes()){
					LOGGER.error(d);
				}
			}else{
				throw new TfrBusinessException("到达部门为空,不能建立装车任务!");
			}
			//是否建立任务PDA为是
			pdaEntity.setBeCreator(FossConstants.YES);
			loadTaskEntity = new LoadTaskEntity();
			List<LoadDestOrgEntity> loadDestOrgs = new ArrayList<LoadDestOrgEntity>();
			List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
			LoadDestOrgEntity loadDestOrg;
			LoaderParticipationEntity loader;
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//调用综合接口查询出发部门是否为外场
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(loadTask.getCreateOrgCode());
			boolean isNeedDepartPlan = false;
			if(origOrg != null){
				//如果营业部
				if(FossConstants.YES.equals(origOrg.getSalesDepartment())){
					SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
					if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){
						//origOrg = pdaCommonService.getCurrentOutfieldCode(loadTask.getCreateOrgCode());
						origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
					}
				}
				//装车部门为外场
				else{
					//查询对应装车部门的上级部门
					origOrg = pdaCommonService.getCurrentOutfieldCode(loadTask.getCreateOrgCode());
				}
			}
			if(origOrg == null){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}else{
				if(FossConstants.YES.equals(origOrg.getTransferCenter())){
					//装车类型不是偏线装车、不是落地配装车
					if(StringUtils.isNotBlank(loadTask.getLoadTaskType()) && !LoadConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(loadTask.getLoadTaskType()) && !LoadConstants.LOAD_TASK_TYPE_LDP.equals(loadTask.getLoadTaskType())){
						//调用综合接口查询车辆所服务部门不为接送货部门
						if(vehicleDto != null){
							if(!DictionaryValueConstants.BES_VEHICLE_USED_TYPE_PKP.equals(vehicleDto.getVehicleUsedTypeCode())){
								//分部交接不校验发车计划
								if(!StringUtil.equals(loadTask.getLoadTaskType(),
										TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION)){
									//判定是否属于快递分部，快递分部装车不校验发车计划
									if(!FossConstants.YES.equals(origOrg.getExpressBranch())){
										isNeedDepartPlan = true;
									}
								}
							}
						}
					}
				}
			}
			String loadTaskId = UUIDUtils.getUUID();
			//生成任务编号
			//loadTaskNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.ZC,origOrg.getCode());
			loadTaskNo = billNumService.generateLoadTaskNo(origOrg.getCode());
			
			LOGGER.error("建立装车任务开始"+loadTaskNo);
			//到达部门名称s
			StringBuilder destOrgNames = new StringBuilder();
			//线路名称s
			StringBuilder lineNames = new StringBuilder();
			
			if(vehicleDto != null){
				//校验封签
				VehicleSealInfoDto seal = vehicleSealService.queryVehicleSealInfo(loadTask.getVehicleNo());
				if(seal != null){
					throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_SEALED1); 
				}
				//如果车辆已使用未出发（非本部门），则不能建立装车任务，该方法会自己拋异常的，所以不用在这里抛
				loadService.queryUndepartRecByVehicleNo(origOrg.getCode(),loadTask.getVehicleNo(),TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
				
			}
			//最早出发时间
			Date lastedDepartTime;
			String vehicleNo = loadTask.getVehicleNo();
				lastedDepartTime = new Date((new Date().getTime()+ConstantsNumberSonar.SONAR_NUMBER_24*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_1000));
				//如果到达部门为空，不能建立装车任务
				if(CollectionUtils.isEmpty(loadTask.getDestOrgCodes())){
					throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
				}
				TruckDepartPlanDetailDto departPlan = null;
				for(String destOrgCode : loadTask.getDestOrgCodes()){
					loadDestOrg = new LoadDestOrgEntity();
					//查询发车计划
					if(vehicleDto != null){
						//判定长途装车的车牌号是否是挂牌号（挂牌号与车牌号格式一样）,若是查询挂牌号的发车计划是否存在 alfred 2014-9-12
						if(StringUtils.equals(vehicleDto.getVehicleType(), "vehicletype_trailer")
								&&StringUtils.isNotBlank(vehicleDto.getVehicleMotorcadeCode())){
							if(TransferPDADictConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(loadTask.getLoadTaskType())){
								departPlan = truckDepartPlanDetailService.queryDepartPlanDetailByTrailerVehicleNo(origOrg.getCode(), destOrgCode, loadTask.getVehicleNo(), null);
							}
						}else{
							//根据车牌号查询发车计划
							departPlan = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetail(origOrg.getCode(), destOrgCode, loadTask.getVehicleNo(),null);
						}
					}else{
						if(TransferPDADictConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(loadTask.getLoadTaskType())){
							//根据货柜号查询发车计划
							departPlan = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetailByContainerNo(origOrg.getCode(), destOrgCode, loadTask.getVehicleNo(), null);
							if(departPlan != null){
								vehicleNo = departPlan.getVehicleNo();
								vehicleDto = new VehicleAssociationDto();
								vehicleDto.setVehicleNo(vehicleNo);
								vehicleDto.setVehicleDeadLoad(departPlan.getMaxLoadWeight());
								vehicleDto.setVehicleSelfVolume(departPlan.getTruckVolume());
								vehicleDto.setVehicleUsedTypeName("长途车");
								vehicleDto.setVehicleOrganizationCode(departPlan.getLongCarGroup());
							}
						}
					}
					if(departPlan != null){
						loadDestOrg.setTruckDepartPlanDetailId(departPlan.getId());
						lineNames.append(departPlan.getLineName());
						lineNames.append("");
						if(departPlan.getPlanDepartTime() != null && departPlan.getPlanDepartTime().before(lastedDepartTime)){
							lastedDepartTime = departPlan.getPlanDepartTime();
						}
					}else{
						if(vehicleDto == null && !(TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(loadTask.getLoadTaskType()))){
							if(!loadTask.getVehicleNo().startsWith("德")){
								throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
							}
						}
						//判定快递车辆，是否能使用
						if(StringUtils.isNotBlank(loadTask.getVehicleNo())){
							if(loadTask.getVehicleNo().startsWith("德")){
								if(!(TransferPDADictConstants.LOAD_TASK_TYPE_SHORT_DISTANCE.equals(loadTask.getLoadTaskType())
										&&FossConstants.YES.equals(origOrg.getTransferCenter()))){
									throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NOTUSE_EXPRESS_VEHICLE); 
								}
							}
						}
						if(isNeedDepartPlan){
							throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_DEPARTURE_PLANE);
						}
						//查询线路 ExpressLineService
						DepartureStandardDto departureStandard = lineService.queryDepartureStandardListBySourceTargetDirectly(origOrg.getCode(), destOrgCode, loadBeginTime);
						
						if(departureStandard != null && StringUtils.isNotBlank(departureStandard.getLineVirtualCode())){
							lineNames.append(departureStandard.getLineName());
						}else {
							//没有出发部门到到达部门线路则不能建立任务
							DepartureStandardDto departureStandard2 = expresslineService.queryDepartureStandardListBySourceTargetDirectly(origOrg.getCode(), destOrgCode, loadBeginTime);
							if(departureStandard2 != null && StringUtils.isNotBlank(departureStandard2.getLineVirtualCode())){
								lineNames.append(departureStandard2.getLineName());
							}else {
								//没有出发部门到到达部门线路则不能建立任务
								throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LINE_MESSAGECODE);
							}
						}
					}
					loadDestOrg.setDestOrgCode(destOrgCode);
					loadDestOrg.setBeCreateHandOver(FossConstants.NO);
					loadDestOrg.setLoadTaskId(loadTaskId);
					//如果偏线装车：查询偏线部门
					if(TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(loadTask.getLoadTaskType())){
						BusinessPartnerEntity destOrg = vehicleAgencyCompanyService.queryEntityByCode(destOrgCode);
						if(destOrg != null){
							loadDestOrg.setDestOrgName(destOrg.getAgentCompanyName());
							destOrgNames.append(destOrg.getAgentCompanyName());
							destOrgNames.append(" ");
						}
					}else if(TransferPDADictConstants.LOAD_TASK_TYPE_LDP.equals(loadTask.getLoadTaskType())){//快递:如果落地配装车：查询落地配部门
						BusinessPartnerExpressEntity destOrg = ldpAgencyCompanyService.queryEntityByCode(destOrgCode,FossConstants.YES);
						if(destOrg != null){
							loadDestOrg.setDestOrgName(destOrg.getAgentCompanyName());
							destOrgNames.append(destOrg.getAgentCompanyName());
							destOrgNames.append(" ");
						}
					}else{//其他装车：查询部门
						OrgAdministrativeInfoEntity destOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destOrgCode);
						if(destOrg != null){
							loadDestOrg.setDestOrgName(destOrg.getName());
							destOrgNames.append(destOrg.getName());
							destOrgNames.append(" ");
						}
					}
					//调用综合接口查询部门名称
					
					loadDestOrg.setId(UUIDUtils.getUUID());
					loadDestOrg.setCreateDate(new Date());
					loadDestOrgs.add(loadDestOrg);
				}
				
				if(CollectionUtils.isNotEmpty(loadDestOrgs)){
					pdaLoadDao.insertTransferLoadDestOrg(loadDestOrgs);
					loadTaskEntity.setDestOrgNames(destOrgNames.toString().trim());
					String line = lineNames.toString().trim();
					if(StringUtils.isNotBlank(line)){
						loadTaskEntity.setLine(line);
					}
				}
				//*************************2013-3-11 修改********************
				//如果是外场
				if(FossConstants.YES.equals(origOrg.getTransferCenter())&&!FossConstants.YES.equals(origOrg.getExpressBranch())){
					//调用综合接口查询月台虚拟编码
					if(StringUtils.isNotBlank(loadTask.getPlatformNo())){
						if(origOrg != null){
							if(StringUtils.isNotBlank(origOrg.getCode())){
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
					if(StringUtils.isNotBlank(loadTaskEntity.getPlatformId())){
						//调用月台服务，占用月台
						try{
							pdaCommonService.updatePlatformStateByCreateTask(loadTaskEntity.getPlatformId(),vehicleNo,loadTaskNo,
									origOrg.getCode(),PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE,
									PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_LOAD,loadBeginTime,lastedDepartTime);
							//platformDispatchService.updatePlatformStatusForUsing(distributeEntity);
						}catch(Exception e){
							//352203-sonar
							logger.info("PDATransferLoadService.createLoadTask 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
						}
					}
				}

				
			//200968 2016-01-05 装车配载优化-新建装车任务的时候:满足公司车+长途+挂牌号或厢式车(不能使用拖头)	
			/*	if(vehicleDto != null){
					if(StringUtils.equals(vehicleDto.getVehicleType(), "vehicletype_tractors")){
						if(TransferPDADictConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(loadTask.getLoadTaskType())){
							if(!vehicleDto.getVehicleMotorcadeName().equals("外请车")){
								//公司车+长途+拖头
								throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOAD_VEHICLETYPE);
							}
						}
					}		
		        }
			*/	
			//装车任务
			loadTaskEntity.setId(loadTaskId);
			loadTaskEntity.setGoodsType(loadTask.getGoodsType());
			loadTaskEntity.setLoadStartTime(df.format(loadBeginTime));
			loadTaskEntity.setOrigOrgCode(origOrg.getCode());
			//调用综合接口查询部门名称
			if(origOrg != null){
				loadTaskEntity.setOrigOrgName(origOrg.getName());
			}
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			loadTaskEntity.setTaskNo(loadTaskNo);
			loadTaskEntity.setTaskType(loadTask.getLoadTaskType());
			loadTaskEntity.setVehicleNo(vehicleNo);
			loadTaskEntity.setBeCreateGapRep(FossConstants.NO);
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			loadTaskEntity.setTransitGoodsType(loadTask.getTransitGoodsType());
			
			//理货员
			PorterEntity porter;
			for(LoaderDto loaderCode : loadTask.getLoaderCodes()){
				loader = new LoaderParticipationEntity();
				//理货员取pda传过来的时间 2013-7-26-liubinbin
				loader.setJoinTime(loadTask.getCreateTime()==null?loadBeginTime:loadTask.getCreateTime());
				loader.setFlag(loaderCode.getFlag());
				loader.setId(UUIDUtils.getUUID());
				loader.setTaskId(loadTaskId);
				loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD);
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
		}
		
		//插入装车PDA
		pdaEntity.setTaskNo(loadTaskNo);
		pdaEntity.setDeviceNo(loadTask.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		LOGGER.error("建立装车任务结束"+loadTaskNo);
		return this.createLoadTaskResult(loadTaskNo,loadTaskEntity.getOrigOrgCode(), creator, loaderCodes, vehicleDto);
	}
	/**
	 * 查询货物清单.
	 * 
	 *
	 * @param taskNo the task no
	 * 
	 * @return the list
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-11-19 下午3:00:23
	 * 
	 * modify 2014-12-5 下午2:44:25  alfred 修改只查询零担货量
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#queryLoadTaskDetail(java.lang.String, java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskDetail(String taskNo) {
		LOGGER.error("刷新装车任务开始:"+taskNo);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		//只有装车状态为装车中的任务可以下拉装车清单
		if(tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())){
			if(queryLoadSwitch4PDA()){
				return refreshSimpleDetail( taskNo,tempLoadTask, "No");
			}else{
				return downLoadCompreDetail(taskNo, tempLoadTask,"No");
			}
		}else{
			LOGGER.error("刷新装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	
	
	/**
	 * 
	 * <p>刷新包明细</p> 
	 * @author alfred
	 * @date 2014-10-30 下午4:28:51
	 * @param taskNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.server.service.impl.AbstractPDALoadService#refrushLoadTaskPackageDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskPackageDetail(String taskNo) {
		LOGGER.error("刷新包装车任务开始:"+taskNo);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		if(tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())){
			List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();

			//普通包装车清单
			List<LoadGoodsDetailSerialDto> packageLoadGoodsDetails = null;
			//直达包装车清单
			List<LoadGoodsDetailSerialDto> directpackageLoadDetails = null; 
			//混合包
			if(!TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(tempLoadTask.getTaskType())
					&&!TransferPDADictConstants.LOAD_TASK_TYPE_LDP.equals(tempLoadTask.getTaskType())){
				List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
				//普通包(包括空运包)
				packageLoadGoodsDetails = pdaLoadDao.refrushPackageTransferLoadTaskDetail(taskNo, destOrgCodes);
				//直达包(包括空运直达包)
				directpackageLoadDetails = pdaLoadDao.refrushDirectPackageTransferLoadDetail(taskNo, destOrgCodes);
			}
			//多货装车清单
			List<LoadGoodsDetailSerialDto> moreGoodsLoadGoodsDetails = pdaLoadDao.refrushMorePackageLoadDetail(taskNo);
			if(CollectionUtils.isNotEmpty(moreGoodsLoadGoodsDetails)){
				loadGoodsDetails.addAll(moreGoodsLoadGoodsDetails);
			}
			if(CollectionUtils.isNotEmpty(packageLoadGoodsDetails)){
				loadGoodsDetails.addAll(packageLoadGoodsDetails);
			}
			if(CollectionUtils.isNotEmpty(directpackageLoadDetails)){
				loadGoodsDetails.addAll(directpackageLoadDetails);
			}
			
			List<LoadGoodsDetailDto> result = this.loadSerialDetailToWayBillDetail(loadGoodsDetails,tempLoadTask.getOrigOrgCode());
			if(CollectionUtils.isNotEmpty(result)){
				LOGGER.error("刷新包装车任务结束:"+taskNo);
				return result;
			}else{
				LOGGER.error("刷新包装车任务结束:"+taskNo);
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
			}
		}else{
			LOGGER.error("刷新包装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	/**
	 * 装车下拉接口开关
	 * @return
	 */
	public boolean queryLoadSwitch4PDA() {
		//final String code = "TFR_FOSS_LOAD_DOWNLOAD";
		String value = configurationParamsService.querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, 
				ConfigurationParamsConstants.TFR_FOSS_LOAD_DOWNLOAD,
				FossConstants.ROOT_ORG_CODE);
		return FossConstants.YES.equals(value) ? true : false;
	}
	/***
	 * 
	 * <p>刷新快递散货明细</p> 
	 * @author alfred
	 * @date 2014-12-5 下午2:44:25
	 * @param taskNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#refrushLoadTaskExpressDetail(java.lang.String)
	 */
	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskExpressDetail(String taskNo) {
		LOGGER.error("刷新快递散货装车任务开始:"+taskNo);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		//只有装车状态为装车中的任务可以下拉装车清单
		if(tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())){
			if(queryLoadSwitch4PDA()){
				return refreshSimpleDetail( taskNo,tempLoadTask, "Yes");
			}else{
				return downLoadCompreDetail(taskNo, tempLoadTask,"Yes");
			}
		}else{
			LOGGER.error("刷新快递散货装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	/**
	 * 查询出全面 完整的装车任务明细（使用于非特殊时期）
	 * @param taskNo
	 * @param tempLoadTask
	 * @param flag 标识知否快递货：Yes，快递货；No，零担货量
	 * @return
	 * @author alfred
	 * @date 2016-10-6 14:44:25
	 */
	//TODO
	private List<LoadGoodsDetailDto> downLoadCompreDetail(String taskNo,
			LoadTaskEntity tempLoadTask,String flag) {
		int loadlimit = ConstantsNumberSonar.SONAR_NUMBER_1000;
		if(flag.equals("Yes")){
			final String code = "TFR_FOSS_LOAD_LIMIT";
			String value =configurationParamsService.querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, code,
					FossConstants.ROOT_ORG_CODE);
			if(StringUtils.isNotEmpty(value)){
				loadlimit = Integer.valueOf(value);
			}
		}
		List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
		//正常库区装车清单
		List<LoadGoodsDetailSerialDto> normalLoadGoodsDetails = null;
		//虚拟库存装车清单
		List<LoadGoodsDetailSerialDto> saleLoadGoodsDetails = null;
		//合车库区装车清单
		List<LoadGoodsDetailSerialDto> togetherLoadGoodsDetails = null; 
		if(StringUtils.isBlank(tempLoadTask.getGoodsType())){
			//tempLoadTask.setGoodsType(null);
			//等于空的时候不需要赋值
		}else if("ALL".equals(tempLoadTask.getGoodsType().toUpperCase())){
			tempLoadTask.setGoodsType(null);
		}else if(("B_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))){
			tempLoadTask.setGoodsType("B");
		}else if(("A_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))){
			tempLoadTask.setGoodsType("A");
		}
		//偏线装车
		if(TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(tempLoadTask.getTaskType())){
			List<String>  outerBranchCodes = pdaLoadService.queryOuterBranchCodesByTaskId(tempLoadTask.getId());
			if(CollectionUtils.isNotEmpty(outerBranchCodes)){
				normalLoadGoodsDetails = pdaLoadDao.
						refrushNormalTransferLoadTaskDetail(taskNo,outerBranchCodes,tempLoadTask.getGoodsType(),flag);
				//零担货量查询合车
				if(flag.equals("No")){
					togetherLoadGoodsDetails = pdaLoadDao.
						refrushTogetherTransferLoadTaskDetail(taskNo,outerBranchCodes,tempLoadTask.getGoodsType(),flag);
				}
			}
		}else if(TransferPDADictConstants.LOAD_TASK_TYPE_LDP.equals(tempLoadTask.getTaskType())){//快递:落地配装车
			if(flag.equals("Yes")){
				List<String>  outerBranchCodes = pdaLoadService.queryLDPDeptCodesByTaskId(tempLoadTask.getId());
				if(CollectionUtils.isNotEmpty(outerBranchCodes)){
					normalLoadGoodsDetails = pdaLoadDao.
							refrushNormalTransferLoadTaskDetail(taskNo,outerBranchCodes,null,flag);
					//togetherLoadGoodsDetails = pdaLoadDao.
						//	refrushTogetherTransferLoadTaskDetail(taskNo,outerBranchCodes,null,flag);
				}
			}
		}else{//长短途装车
			List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
			//查询出发部门属性
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(tempLoadTask.getOrigOrgCode());
			//零担货量下拉合伙人
			/*****下拉合伙人一级及二级营业部-alfred 2016-10-1****/
			if(TransferPDADictConstants.LOAD_TASK_TYPE_SHORT_DISTANCE.equals(tempLoadTask.getTaskType())
					&&StringUtils.equals(origOrg.getTransferCenter(), FossConstants.YES)){
				List<DeptTransferMappingEntity> deptTrans = deptTransferMappingService.
						queryDeptTransferMappingListByCode(destOrgCodes.get(0));
				if(null !=deptTrans){
					//查询到达部门集合
					List<String> mapList = this.queryShortArriveDeptList(tempLoadTask.getOrigOrgCode(), destOrgCodes.get(0));
					destOrgCodes.addAll(mapList);
				}
			}
			destOrgCodes.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
			//下拉正常库区货物
			normalLoadGoodsDetails = pdaLoadDao.refrushNormalTransferLoadTaskDetail(taskNo,destOrgCodes,tempLoadTask.getGoodsType(),flag);
			//如果出发部门为营业部才下拉虚拟库区货物
			if(StringUtils.equals(origOrg.getTransferCenter(), "N")){
				saleLoadGoodsDetails = pdaLoadDao.refrushSaleNormalTransferLoadTaskDetail(taskNo,destOrgCodes,tempLoadTask.getGoodsType(),flag);
			}
			//长途装车且是零担下拉合车运单
			if(TransferPDADictConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(tempLoadTask.getTaskType())
					&&flag.equals("No")){
				togetherLoadGoodsDetails = pdaLoadDao.
						refrushTogetherTransferLoadTaskDetail(taskNo,null,tempLoadTask.getGoodsType(),flag);
			}
		}
		//多货装车清单
		List<LoadGoodsDetailSerialDto> moreGoodsLoadGoodsDetails = pdaLoadDao.refrushMoreGoodsLoadTaskDetail(taskNo,flag);
		if(CollectionUtils.isNotEmpty(normalLoadGoodsDetails)){
			loadGoodsDetails.addAll(normalLoadGoodsDetails);
		}
		if(CollectionUtils.isNotEmpty(togetherLoadGoodsDetails)){
			loadGoodsDetails.addAll(togetherLoadGoodsDetails);
		}
		if(CollectionUtils.isNotEmpty(moreGoodsLoadGoodsDetails)){
			loadGoodsDetails.addAll(moreGoodsLoadGoodsDetails);
		}
		if(CollectionUtils.isNotEmpty(saleLoadGoodsDetails)){
			loadGoodsDetails.addAll(saleLoadGoodsDetails);
		}
		
		//限制货物在两千票以内所以这边判定货物在大于两千票则不继续查询则直接返回
		if(CollectionUtils.isEmpty(loadGoodsDetails)){
			loadGoodsDetails=new ArrayList<LoadGoodsDetailSerialDto>();
		}
		
		//控制票数
		Set<String> loadGoodsSet =new HashSet<String>();
		if(CollectionUtils.isNotEmpty(loadGoodsDetails)){
			for(LoadGoodsDetailSerialDto ldd : loadGoodsDetails){
				loadGoodsSet.add(ldd.getWayBillNo());
			}
		}
		
		if(loadGoodsSet.size()<loadlimit){
			// 获取当前部门对象
			OrgAdministrativeInfoEntity orgEntityTemp = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(tempLoadTask.getOrigOrgCode());
			// 如果为当前不是外场,则直接跳过不执行下面的业务逻辑
			if (null != orgEntityTemp&& StringUtils.equals(FossConstants.YES,orgEntityTemp.getTransferCenter())) {

				List<LoadGoodsDetailSerialDto> loadGoodsTemp =new ArrayList<LoadGoodsDetailSerialDto>();

				List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
				//如果没有到达部门则直接跳过不做查询
				if(CollectionUtils.isNotEmpty(destOrgCodes)){
						
					Map<String, Object> map=new HashMap<String, Object>();
					//origOrgCode,handoverbillStateList,arriveDeptList,start,limit
					map.put("origOrgCode", tempLoadTask.getOrigOrgCode());
					
					List<String> handoverbillStateList = new ArrayList<String>(2);
					////40：已到达
					handoverbillStateList.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE));
					//设置查询条件
					map.put("handoverbillStateList", handoverbillStateList);
					//用Set过滤一下重复的值
					Set<String> arriveDeptSetTemp = new HashSet<String>();
					for(String str :destOrgCodes){
						List<String> stowagePlansList = stowagePlansService.queryStowageWithid(tempLoadTask.getOrigOrgCode(), str);
						if(CollectionUtils.isNotEmpty(stowagePlansList)){
							arriveDeptSetTemp.addAll(stowagePlansList);
						}
						arriveDeptSetTemp.add(str);
					}
					List<String> stowagePlansList=new ArrayList<String>(arriveDeptSetTemp);
					
					// 接收到达部门、配载部门所辐射的营业部的list
					List<String> arriveDeptList = new ArrayList<String>();
					for (String arriveDept : stowagePlansList) {
						// 获取该部门对象
						OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCode(arriveDept);
						arriveDeptList.add(arriveDept);
						// 如果为外场，则获取其辐射的营业部code
						if (null != orgEntity
								&& StringUtils.equals(FossConstants.YES,
										orgEntity.getTransferCenter())) {
							
							List<String> list = lineService
									.queryArriveCodeListByTransferCode(arriveDept);
							List<String> explist = expresslineService
									.queryArriveCodeListByTransferCode(arriveDept);
							arriveDeptList.addAll(explist);
							arriveDeptList.addAll(list);
						}
					}
					map.put("arriveDeptList", arriveDeptList);
					//装车任务ID
					map.put("taskNo", taskNo);
					map.put("goodsType", tempLoadTask.getGoodsType());
					
					//设置分页其实页数
					int start=0;
					int limit=ConstantsNumberSonar.SONAR_NUMBER_20000;
					boolean whileTrue=false;
					while(whileTrue == false){
						map.put("start", start);
						map.put("limit", limit);
						map.put("isExpress", flag);
						List<LoadGoodsDetailSerialDto> unloadingNotReachGoodsLoadGoodsDetails = pdaLoadDao.
								refrushNormalTransferLoadTaskDetailUnloadingNotReach(map);
						if(CollectionUtils.isEmpty(unloadingNotReachGoodsLoadGoodsDetails)){
							whileTrue = true;
						}else{
							if(unloadingNotReachGoodsLoadGoodsDetails.size()<limit){
								whileTrue = true;
							}
							loadGoodsTemp.addAll(unloadingNotReachGoodsLoadGoodsDetails);
							
							//添加运单号到Set列表里面
							for(LoadGoodsDetailSerialDto ldd : loadGoodsTemp){
								loadGoodsSet.add(ldd.getWayBillNo());
							}
						
							if(loadGoodsSet.size()>=loadlimit){
								whileTrue = true;
							}
						}
						start+=limit;
					}
				}
				//把库存和到达未卸车的数据汇总
				if(CollectionUtils.isNotEmpty(loadGoodsTemp)){
					loadGoodsDetails.addAll(loadGoodsTemp);
				}
			}
		}
		List<LoadGoodsDetailDto> result = this.loadSerialDetailToWayBillDetail(loadGoodsDetails,tempLoadTask.getOrigOrgCode());
		//如果放回值大于1000条数据则消除大于一千的部分
		if(CollectionUtils.isNotEmpty(result)){
			for(int i=result.size()-1;i>=loadlimit;i--){
				result.remove(i);
			}
		}
		if(CollectionUtils.isNotEmpty(result)){
			LOGGER.error("刷新装车任务结束:"+taskNo);
			return result;
		}else{
			LOGGER.error("刷新装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
		}
	}
	
	
	/**
	 * 查询出必要字段的装车任务明细（使用于特殊时期，如双十一，6.18）
	 * ①删除优先货排序、代办提醒、到达未卸车的明细
	 * @param taskNo
	 * @param tempLoadTask
	 * @return
	 * @author alfred
	 * @param flag 标识知否快递货：Yes，快递货；No，零担货量
	 * @date 2016-10-6 15:20:25
	 */
	private List<LoadGoodsDetailDto> refreshSimpleDetail(String taskNo,
			LoadTaskEntity tempLoadTask,String flag){
		int loadlimit = ConstantsNumberSonar.SONAR_NUMBER_1000;
		//快递查询条数限制
		if(flag.equals("Yes")){
			final String code = "TFR_FOSS_LOAD_LIMIT";
			String value =configurationParamsService.querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, code,
					FossConstants.ROOT_ORG_CODE);
			if(StringUtils.isNotEmpty(value)){
				loadlimit = Integer.valueOf(value);
			}
		}
	
		List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
		//正常库区装车清单
		List<LoadGoodsDetailSerialDto> normalLoadGoodsDetails = null;
		//虚拟库存装车清单
		List<LoadGoodsDetailSerialDto> saleLoadGoodsDetails = null;
		//合车库区装车清单
		List<LoadGoodsDetailSerialDto> togetherLoadGoodsDetails = null; 
		if(StringUtils.isBlank(tempLoadTask.getGoodsType())){
			//tempLoadTask.setGoodsType(null);
			//等于空的时候不需要赋值
		}else if("ALL".equals(tempLoadTask.getGoodsType().toUpperCase())){
			tempLoadTask.setGoodsType(null);
		}else if(("B_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))){
			tempLoadTask.setGoodsType("B");
		}else if(("A_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))){
			tempLoadTask.setGoodsType("A");
		}
		//偏线装车
		if(TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(tempLoadTask.getTaskType())){
			List<String>  outerBranchCodes = pdaLoadService.queryOuterBranchCodesByTaskId(tempLoadTask.getId());
			if(CollectionUtils.isNotEmpty(outerBranchCodes)){
				//零担货
				if(flag.equals("No")){
					normalLoadGoodsDetails = pdaLoadDao.refreshSimpleLDDetail(taskNo,outerBranchCodes,tempLoadTask.getGoodsType());
					togetherLoadGoodsDetails = pdaLoadDao.refreshTogetherSimpleDetail(taskNo,outerBranchCodes,tempLoadTask.getGoodsType());
				}else{//快递货量
					normalLoadGoodsDetails = pdaLoadDao.refreshSimpleEXPDetail(taskNo,outerBranchCodes,tempLoadTask.getGoodsType());
				}
			}
		}else if(TransferPDADictConstants.LOAD_TASK_TYPE_LDP.equals(tempLoadTask.getTaskType())){//快递:落地配装车
			if(flag.equals("Yes")){
				List<String>  outerBranchCodes = pdaLoadService.queryLDPDeptCodesByTaskId(tempLoadTask.getId());
				if(CollectionUtils.isNotEmpty(outerBranchCodes)){
					normalLoadGoodsDetails = pdaLoadDao.
							refrushNormalTransferLoadTaskDetail(taskNo,outerBranchCodes,null,flag);
					//togetherLoadGoodsDetails = pdaLoadDao.
						//	refrushTogetherTransferLoadTaskDetail(taskNo,outerBranchCodes,null,flag);
				}
			}
		}else{//长短途装车
			List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
			//查询出发部门属性
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(tempLoadTask.getOrigOrgCode());
			/*****下拉合伙人一级及二级营业部-alfred 2016-10-1****/
			if(TransferPDADictConstants.LOAD_TASK_TYPE_SHORT_DISTANCE.equals(tempLoadTask.getTaskType())
					&&StringUtils.equals(origOrg.getTransferCenter(), FossConstants.YES)){
				List<DeptTransferMappingEntity> deptTrans = deptTransferMappingService.
						queryDeptTransferMappingListByCode(destOrgCodes.get(0));
				if(null !=deptTrans){
					//查询到达部门集合
					List<String> mapList = this.queryShortArriveDeptList(tempLoadTask.getOrigOrgCode(), destOrgCodes.get(0));
					destOrgCodes.addAll(mapList);
				}
			}
			destOrgCodes.add(StockConstants.AIR_FREIGHT_NEXT_ORG_CODE);
			//零担货量
			if(flag.equals("No")){
				//下拉正常库区货物
				normalLoadGoodsDetails = pdaLoadDao.refreshSimpleLDDetail(taskNo,destOrgCodes,tempLoadTask.getGoodsType());
				//如果出发部门为营业部才下拉虚拟库区货物
				if(StringUtils.equals(origOrg.getTransferCenter(), "N")){
					saleLoadGoodsDetails = pdaLoadDao.refrushSaleNormalTransferLoadTaskDetail(taskNo,destOrgCodes,tempLoadTask.getGoodsType(),flag);
				}
				//长途装车下拉合车运单
				if(TransferPDADictConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(tempLoadTask.getTaskType())){
					togetherLoadGoodsDetails = pdaLoadDao.
							refreshTogetherSimpleDetail(taskNo,null,tempLoadTask.getGoodsType());
				}
			}else{//快递货量
				normalLoadGoodsDetails = pdaLoadDao.refreshSimpleEXPDetail(taskNo,destOrgCodes,tempLoadTask.getGoodsType());
			}
		}
		//多货装车清单
		List<LoadGoodsDetailSerialDto> moreGoodsLoadGoodsDetails = pdaLoadDao.refrushMoreGoodsLoadTaskDetail(taskNo,flag);
		if(CollectionUtils.isNotEmpty(normalLoadGoodsDetails)){
			loadGoodsDetails.addAll(normalLoadGoodsDetails);
		}
		if(CollectionUtils.isNotEmpty(togetherLoadGoodsDetails)){
			loadGoodsDetails.addAll(togetherLoadGoodsDetails);
		}
		if(CollectionUtils.isNotEmpty(moreGoodsLoadGoodsDetails)){
			loadGoodsDetails.addAll(moreGoodsLoadGoodsDetails);
		}
		if(CollectionUtils.isNotEmpty(saleLoadGoodsDetails)){
			loadGoodsDetails.addAll(saleLoadGoodsDetails);
		}
		
		//限制货物在两千票以内所以这边判定货物在大于两千票则不继续查询则直接返回
		if(CollectionUtils.isEmpty(loadGoodsDetails)){
			loadGoodsDetails=new ArrayList<LoadGoodsDetailSerialDto>();
		}
		
		//控制票数
		Set<String> loadGoodsSet =new HashSet<String>();
		if(CollectionUtils.isNotEmpty(loadGoodsDetails)){
			for(LoadGoodsDetailSerialDto ldd : loadGoodsDetails){
				loadGoodsSet.add(ldd.getWayBillNo());
			}
		}
		List<LoadGoodsDetailDto> result = this.loadSerialDetailToWayBillDetail(loadGoodsDetails,tempLoadTask.getOrigOrgCode());
		//如果放回值大于1000条数据则消除大于一千的部分
		if(CollectionUtils.isNotEmpty(result)){
			for(int i=result.size()-1;i>=loadlimit;i--){
				result.remove(i);
			}
		}
		if(CollectionUtils.isNotEmpty(result)){
			LOGGER.error("刷新简易装车任务结束:"+taskNo);
			return result;
		}else{
			LOGGER.error("刷新简易装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
		}
	}
	
	/***
	 * 
	 * <p>刷新快递散货明细</p> 
	 * @author 332219
	 * @date 2016-11-22
	 * @param taskNo
	 * @return 
	 */
	@Override
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskExpressDetail(String taskNo) {
		LOGGER.error("刷新快递散货装车任务开始:"+taskNo);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		//只有装车状态为装车中的任务可以下拉装车清单
		if(tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())){
			return  downSaleLoadCompreDetail(taskNo, tempLoadTask,"Yes");
		}else{
			LOGGER.error("刷新快递散货装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	
	/**
	 * 查询出全面 完整的装车任务明细（使用于非特殊时期）
	 * @param taskNo
	 * @param tempLoadTask
	 * @param flag 标识知否快递货：Yes，快递货；No，零担货量
	 * @return
	 * @author 332219
	 * @date 2016-11-21
	 */
	//TODO
	private List<LoadSaleGoodsDetailDto> downSaleLoadCompreDetail(String taskNo,LoadTaskEntity tempLoadTask,String flag){
		int loadlimit = ConstantsNumberSonar.SONAR_NUMBER_1000;
		if(flag.equals("Yes")){
			final String code = "TFR_FOSS_LOAD_LIMIT";
			String value =configurationParamsService.querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, code,
					FossConstants.ROOT_ORG_CODE);
			if(StringUtils.isNotEmpty(value)){
				loadlimit = Integer.valueOf(value);
			}
		}
		List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
		//正常库区装车清单
		List<LoadGoodsDetailSerialDto> normalLoadGoodsDetails = null;
		//虚拟库区装车清单
		List<LoadGoodsDetailSerialDto> togetherLoadGoodsDetails = null; 
		if(StringUtils.isBlank(tempLoadTask.getGoodsType())){
			//tempLoadTask.setGoodsType(null);
			//等于空的时候不需要赋值
		}else if("ALL".equals(tempLoadTask.getGoodsType().toUpperCase())){
			tempLoadTask.setGoodsType(null);
		}else if(("B_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))){
			tempLoadTask.setGoodsType("B");
		}else if(("A_TYPE".equals(tempLoadTask.getGoodsType().toUpperCase()))){
			tempLoadTask.setGoodsType("A");
		}
		//长短途装车
		List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
		/**
		 * 下拉运单
		 */
		if(TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE.equals(tempLoadTask.getTaskType())){
			Set<String> deptMapList = this.queryArriveDeptList(tempLoadTask.getOrigOrgCode(),destOrgCodes.get(0));
			destOrgCodes.addAll(deptMapList);
		}
		//下拉正常库区货物
		normalLoadGoodsDetails = pdaLoadDao.refrushNormalTransferLoadTaskDetail(taskNo,destOrgCodes,tempLoadTask.getGoodsType(),flag);
		//虚拟库存货物
		togetherLoadGoodsDetails = pdaLoadDao.refrushSaleNormalTransferLoadTaskDetail(taskNo,destOrgCodes,tempLoadTask.getGoodsType(),flag);
		
		if(CollectionUtils.isNotEmpty(normalLoadGoodsDetails)){
			loadGoodsDetails.addAll(normalLoadGoodsDetails);
		}
		if(CollectionUtils.isNotEmpty(togetherLoadGoodsDetails)){
			loadGoodsDetails.addAll(togetherLoadGoodsDetails);
		}
		//限制货物在两千票以内所以这边判定货物在大于两千票则不继续查询则直接返回
		if(CollectionUtils.isEmpty(loadGoodsDetails)){
			loadGoodsDetails=new ArrayList<LoadGoodsDetailSerialDto>();
		}
		//控制票数
		Set<String> loadGoodsSet =new HashSet<String>();
		if(CollectionUtils.isNotEmpty(loadGoodsDetails)){
			for(LoadGoodsDetailSerialDto ldd : loadGoodsDetails){
				loadGoodsSet.add(ldd.getWayBillNo());
			}
		}
		List<LoadSaleGoodsDetailDto> result = this.loadSaleSerialDetailToWayBillDetail(loadGoodsDetails,tempLoadTask.getOrigOrgCode());
		//如果放回值大于1000条数据则消除大于一千的部分
		if(CollectionUtils.isNotEmpty(result)){
			for(int i=result.size()-1;i>=loadlimit;i--){
				result.remove(i);
			}
		}
		if(CollectionUtils.isNotEmpty(result)){
			LOGGER.error("刷新装车任务结束:"+taskNo);
			return result;
		}else{
			LOGGER.error("刷新装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
		}
	}
	
	
	/**
	 * PDA建立装车任务接口.
	 * @param loadTask the load task
	 * @return the list
	 * @author dp-332219
	 * @date 2016-11-21
	 */
	@Override
	@Transactional
	public LoadSaleTaskResultDto createSaleLoadTask(LoadSaleTaskDto loadTask) {
		//如果车辆已封签，则不能建立装车任务
		VehicleAssociationDto vehicleDto = null;
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
				
				pdaEntity.setBeCreator(FossConstants.NO);
			}else{
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
			}		
		}else{//如果任务编号为空，则新建装车任务
			LOGGER.error("建立装车任务开始"+loadTask.getVehicleNo());
			if(CollectionUtils.isNotEmpty(loadTask.getDestOrgCodes())){
				if(loadTask.getDestOrgCodes().size()>1){
					throw new TfrBusinessException("禁止输入多个到达部门!");
				}
				for(String d : loadTask.getDestOrgCodes()){
					LOGGER.error(d);
				}
			}else{
				throw new TfrBusinessException("到达部门为空,不能建立装车任务!");
			}
			//是否建立任务PDA为是
			pdaEntity.setBeCreator(FossConstants.YES);
			loadTaskEntity = new LoadTaskEntity();
			List<LoadDestOrgEntity> loadDestOrgs = new ArrayList<LoadDestOrgEntity>();
			List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
			LoadDestOrgEntity loadDestOrg;
			LoaderParticipationEntity loader;
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//调用综合接口查询出发部门是否为外场
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(loadTask.getCreateOrgCode());
			if(origOrg != null){
				//营业部交接的出发部门只能是营业部
					SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
					if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){
						origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
					}
			}
			if(origOrg == null){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}
			
			String loadTaskId = UUIDUtils.getUUID();
			//生成任务编号
			//loadTaskNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.ZC,origOrg.getCode());
			loadTaskNo = billNumService.generateLoadTaskNo(origOrg.getCode());
			
			LOGGER.error("建立装车任务开始"+loadTaskNo);
			//到达部门名称s
			StringBuilder destOrgNames = new StringBuilder();
			//线路名称s
			StringBuilder lineNames = new StringBuilder();
			
			//最早出发时间
			Date lastedDepartTime;
			lastedDepartTime = new Date((new Date().getTime()+ConstantsNumberSonar.SONAR_NUMBER_24*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_1000));
			//如果到达部门为空，不能建立装车任务
			if(CollectionUtils.isEmpty(loadTask.getDestOrgCodes())){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}
			TruckDepartPlanDetailDto departPlan = null;
			for(String destOrgCode : loadTask.getDestOrgCodes()){
				loadDestOrg = new LoadDestOrgEntity();
				//查询发车计划
				if(departPlan != null){
					loadDestOrg.setTruckDepartPlanDetailId(departPlan.getId());
					lineNames.append(departPlan.getLineName());
					lineNames.append("");
					if(departPlan.getPlanDepartTime() != null && departPlan.getPlanDepartTime().before(lastedDepartTime)){
						lastedDepartTime = departPlan.getPlanDepartTime();
					}
				}else{
					//营业部不校验线路，校验映射关系
					//获取当前部门的code
					String superOrgCode = loadTask.getCreateOrgCode();
					//接收映射数据的所有部门code集合
					List<String> arriveDeptList = new ArrayList<String>();
					//获取当前部门映射数据
					List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(superOrgCode);
					//判断结果集不为空才添加到映射的到达部门
					if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
						for (DeptTransferMappingEntity entity : deptTransferMappinglist) {
							//判断是否是对接外场，否则是对接营业部，是则对接外场  
							if(StringUtils.equals(FossConstants.NO, entity.getIsOutfield())){
								//获取对应营业部的code
								String deptCode = entity.getDeptCode();
								arriveDeptList.add(deptCode);
							}
							//获取一级网点的code
							String fthNetworkCode = entity.getFthNetworkCode();
							if(fthNetworkCode != null){
								arriveDeptList.add(fthNetworkCode);
							}
							//获取二级网点的code
							String secNetworkCode = entity.getSecNetworkCode();
							if(secNetworkCode != null){
								arriveDeptList.add(secNetworkCode);
							}
						}
						//去掉重复的数据再加入集合中
						arriveDeptList.addAll(new HashSet(arriveDeptList));
					}
					//获取到达部门的code
					String arriveDeptCode = loadTask.getDestOrgCodes().get(0);
					//校验是否跨映射做营业部交接单
					if(!arriveDeptList.contains(arriveDeptCode)){
						throw new TfrBusinessException("营业部交接单不能跨映射！");
					}
				}
				loadDestOrg.setDestOrgCode(destOrgCode);
				loadDestOrg.setBeCreateHandOver(FossConstants.NO);
				loadDestOrg.setLoadTaskId(loadTaskId);
				//如果偏线装车：查询偏线部门
				if(TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(loadTask.getLoadTaskType())){
					BusinessPartnerEntity destOrg = vehicleAgencyCompanyService.queryEntityByCode(destOrgCode);
					if(destOrg != null){
						loadDestOrg.setDestOrgName(destOrg.getAgentCompanyName());
						destOrgNames.append(destOrg.getAgentCompanyName());
						destOrgNames.append(" ");
					}
				}else if(TransferPDADictConstants.LOAD_TASK_TYPE_LDP.equals(loadTask.getLoadTaskType())){//快递:如果落地配装车：查询落地配部门
					BusinessPartnerExpressEntity destOrg = ldpAgencyCompanyService.queryEntityByCode(destOrgCode,FossConstants.YES);
					if(destOrg != null){
						loadDestOrg.setDestOrgName(destOrg.getAgentCompanyName());
						destOrgNames.append(destOrg.getAgentCompanyName());
						destOrgNames.append(" ");
					}
				}else{//其他装车：查询部门
					OrgAdministrativeInfoEntity destOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destOrgCode);
					if(destOrg != null){
						loadDestOrg.setDestOrgName(destOrg.getName());
						destOrgNames.append(destOrg.getName());
						destOrgNames.append(" ");
					}
				}
				//调用综合接口查询部门名称
				loadDestOrg.setId(UUIDUtils.getUUID());
				loadDestOrg.setCreateDate(new Date());
				loadDestOrgs.add(loadDestOrg);
			}
			
			if(CollectionUtils.isNotEmpty(loadDestOrgs)){
				pdaLoadDao.insertTransferLoadDestOrg(loadDestOrgs);
				loadTaskEntity.setDestOrgNames(destOrgNames.toString().trim());
				String line = lineNames.toString().trim();
				if(StringUtils.isNotBlank(line)){
					loadTaskEntity.setLine(line);
				}
			}
			//装车任务
			loadTaskEntity.setId(loadTaskId);
			loadTaskEntity.setGoodsType(loadTask.getGoodsType());
			loadTaskEntity.setLoadStartTime(df.format(loadBeginTime));
			loadTaskEntity.setOrigOrgCode(origOrg.getCode());
			//调用综合接口查询部门名称
			if(origOrg != null){
				loadTaskEntity.setOrigOrgName(origOrg.getName());
			}
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			loadTaskEntity.setTaskNo(loadTaskNo);
			loadTaskEntity.setTaskType(loadTask.getLoadTaskType());
			loadTaskEntity.setBeCreateGapRep(FossConstants.NO);
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			loadTaskEntity.setVehicleNo(LoadConstants.VEHICLE_NO_SALE);
			loadTaskEntity.setTransitGoodsType(loadTask.getTransitGoodsType());
			
			//理货员
			PorterEntity porter;
			for(LoaderDto loaderCode : loadTask.getLoaderCodes()){
				loader = new LoaderParticipationEntity();
				//理货员取pda传过来的时间 2013-7-26-liubinbin
				loader.setJoinTime(loadTask.getCreateTime()==null?loadBeginTime:loadTask.getCreateTime());
				loader.setFlag(loaderCode.getFlag());
				loader.setId(UUIDUtils.getUUID());
				loader.setTaskId(loadTaskId);
				loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD);
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
		}
		
		//插入装车PDA
		pdaEntity.setTaskNo(loadTaskNo);
		pdaEntity.setDeviceNo(loadTask.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		LOGGER.error("建立装车任务结束"+loadTaskNo);
		return this.createSaleLoadTaskResult(loadTaskNo,loadTaskEntity.getOrigOrgCode(), creator, loaderCodes, vehicleDto);
	}
	
	/**
	 * 查询货物清单.
	 * @param taskNo the task no
	 * @return the list
	 * @author dp-332219
	 * @date 2016-11-22
	 * modify 2014-12-5 下午2:44:25  alfred 修改只查询零担货量
	 */
	@Override
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskDetail(String taskNo) {
		LOGGER.error("刷新装车任务开始:"+taskNo);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		//只有装车状态为装车中的任务可以下拉装车清单
		if(tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())){
			return downSaleLoadCompreDetail(taskNo, tempLoadTask,"No");
		}else{
			LOGGER.error("刷新装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	
	/**
	 * 
	 * <p>刷新包明细</p> 
	 * @author 332219
	 * @date 2016-12-10
	 * @param taskNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.server.service.impl.AbstractPDALoadService#refrushSaleLoadTaskPackageDetail(java.lang.String)
	 */
	@Override
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskPackageDetail(String taskNo) {
		LOGGER.error("刷新包装车任务开始:"+taskNo);
		LoadTaskEntity tempLoadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		if(tempLoadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(tempLoadTask.getState())){
			List<LoadGoodsDetailSerialDto> loadGoodsDetails = new ArrayList<LoadGoodsDetailSerialDto>();
			//普通包装车清单
			List<LoadGoodsDetailSerialDto> packageLoadGoodsDetails = null;
			//直达包装车清单
			List<LoadGoodsDetailSerialDto> directpackageLoadDetails = null; 
			//混合包
			if(!TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(tempLoadTask.getTaskType())
					&&!TransferPDADictConstants.LOAD_TASK_TYPE_LDP.equals(tempLoadTask.getTaskType())){
				List<String> destOrgCodes = pdaLoadDao.queryLoadDestOrgCodesById(tempLoadTask.getId());
				//普通包(包括空运包)
				packageLoadGoodsDetails = pdaLoadDao.refrushPackageTransferLoadTaskDetail(taskNo, destOrgCodes);
				//直达包(包括空运直达包)
				directpackageLoadDetails = pdaLoadDao.refrushDirectPackageTransferLoadDetail(taskNo, destOrgCodes);
			}
			//多货装车清单
			List<LoadGoodsDetailSerialDto> moreGoodsLoadGoodsDetails = pdaLoadDao.refrushMorePackageLoadDetail(taskNo);
			if(CollectionUtils.isNotEmpty(moreGoodsLoadGoodsDetails)){
				loadGoodsDetails.addAll(moreGoodsLoadGoodsDetails);
			}
			if(CollectionUtils.isNotEmpty(packageLoadGoodsDetails)){
				loadGoodsDetails.addAll(packageLoadGoodsDetails);
			}
			if(CollectionUtils.isNotEmpty(directpackageLoadDetails)){
				loadGoodsDetails.addAll(directpackageLoadDetails);
			}
			
			List<LoadSaleGoodsDetailDto> result = this.loadSaleSerialDetailToWayBillDetail(loadGoodsDetails,tempLoadTask.getOrigOrgCode());
			if(CollectionUtils.isNotEmpty(result)){
				LOGGER.error("刷新包装车任务结束:"+taskNo);
				return result;
			}else{
				LOGGER.error("刷新包装车任务结束:"+taskNo);
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_NO_TASK_DETAIL_MESSAGECODE);
			}
		}else{
			LOGGER.error("刷新包装车任务结束:"+taskNo);
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	/**
	 * 营业部交接根据出发部门和到达部门返回下一部门集合
	 * @param superOrgCode
	 * @param arriveDeptCode
	 * @author 332219
	 * @return
	 */
	private Set<String> queryArriveDeptList(String superOrgCode , String arriveDeptCode){
		 //到达部门的映射
		 List<DeptTransferMappingEntity> deptTrans = deptTransferMappingService.queryDeptTransferMappingListByCode(arriveDeptCode);
		 //返回的集合
		 Set<String> deptMapList = new HashSet<String>();
		 //查询出发部门获取是否是营业部、一级网点、二级网点
		 SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(superOrgCode);
		 //默认为营业部
		 String departWork = "YYB";
		 //营业部数据非空
		 if(departDept != null){
			//判断是否是一级网点
			 String departModel = departDept.getIsLeagueSaleDept();
			 //判断是否是二级网点
			 String departNetwork = departDept.getIsTwoLevelNetwork();
			 //一级网点
			 if("Y".equals(departModel) && "N".equals(departNetwork)){
				 departWork = "YJB";
			 }
			 //二级网点
			 if("Y".equals(departNetwork)){
				 departWork = "EJB";
			 }
		 }else{
			 LOGGER.error("当前部门营业部数据为空！");
		 }
		//查询到达部门获取是否是营业部、一级网点、二级网点
		 SaleDepartmentEntity arriveDept = saleDepartmentService.querySaleDepartmentByCode(arriveDeptCode);
		 //到达部门   营业部："YYB"  , 一级网点 "YJB" , 二级网点 "EJB"
		 //默认为营业部
		 String arriveWork = "YYB";
		 //营业部数据非空
		 if(arriveDept != null){
			//判断是否是一级网点
			 String arriveModel = arriveDept.getIsLeagueSaleDept();
			//判断是否是二级网点
			 String arriveNetwork = arriveDept.getIsTwoLevelNetwork();
			 //一级网点
			 if("Y".equals(arriveModel) && "N".equals(arriveNetwork)){
				 arriveWork = "YJB";
			 }
			 //二级网点
			 if("Y".equals(arriveNetwork)){
				 arriveWork = "EJB";
			 }
		 }else{
			 throw new TfrBusinessException("当前到达部门不是营业部！");
		 }	 
		 //二级网点到一级网点
		 if("EJB".equals(departWork) && "YJB".equals(arriveWork)){
			 //根据出发部门查询到对应的外场
			 List<String> centerCodeList = lineService.queryTransferCodeListBySourceCode(superOrgCode);
			 //替换
			 if(centerCodeList != null && centerCodeList.size()>0){
				 deptMapList.add(centerCodeList.get(0));
				 }
		 }
		 //一级网点到营业部
		 if("YJB".equals(departWork) && "YYB".equals(arriveWork)){
			 //根据出发部门查询到对应的外场
			 List<String> centerCodeList = lineService.queryTransferCodeListBySourceCode(superOrgCode);
			 //添加
			 if(centerCodeList != null && centerCodeList.size()>0){
				 deptMapList.add(centerCodeList.get(0));
			 }
		 }
		 //营业部到一级 
		 if("YYB".equals(departWork) && "YJB".equals(arriveWork)){
			 if(null !=deptTrans){
					for(DeptTransferMappingEntity  deptMappEntity:deptTrans){
						//添加二级网点
						String secNetworkCode = deptMappEntity.getSecNetworkCode();
						if(StringUtils.isNotEmpty(secNetworkCode)){
							deptMapList.add(secNetworkCode);
						}
					}
				}
		 }
		return deptMapList;
	}
	
	/**
	 * 短配交接根据出发部门和到达部门返回下一部门集合
	 * @param superOrgCode
	 * @param arriveDeptCode
	 * @return
	 */
	private List<String> queryShortArriveDeptList(String superOrgCode , String arriveDeptCode){
		 //返回的集合
		 List<String> deptMapList = new ArrayList<String>();
		 //查询出发部门获取是否是营业部
		 SaleDepartmentEntity departDept = saleDepartmentService.querySaleDepartmentByCode(superOrgCode);
		 //出发部门默认是外场
		 String departWork = "YC";
		 //判断不为空
		 if(departDept != null){
			 //判断是否是一级网点
			 String departModel = departDept.getIsLeagueSaleDept();
			 //判断是否是二级网点
			 String departNetwork = departDept.getIsTwoLevelNetwork();
			//出发部门是营业部
			 if("N".equals(departModel) && "N".equals(departNetwork)){
				 departWork = "YYB";
			 }
			 //出发部门一级网点
			 if("Y".equals(departModel) && "N".equals(departNetwork)){
				 departWork = "YJB";
			 }
		 }
		 //查询到达部门是否是营业部
		 SaleDepartmentEntity arriveDept = saleDepartmentService.querySaleDepartmentByCode(arriveDeptCode);
		 //到达部门默认是 外场           外场:"YC" ,  营业部："YYB"  , 一级网点 "YJB"
		 String arriveWork = "YC";
		 //判断不为空
		 if(arriveDept != null){
			 //判断是否是一级网点
			 String arriveModel = arriveDept.getIsLeagueSaleDept();
			 //判断是否是二级网点
			 String arriveNetwork = arriveDept.getIsTwoLevelNetwork();
			 //到达部门是营业部
			 if("N".equals(arriveModel) && "N".equals(arriveNetwork)){
				 arriveWork = "YYB";
			 }
			 //	到达部门一级网点
			 if("Y".equals(arriveModel) && "N".equals(arriveNetwork)){
				 arriveWork = "YJB";
			 }
		 }
		 //外场到营业部 
		 if("YC".equals(departWork) && "YYB".equals(arriveWork)){
			 //根据营业部查询到映射表的数据 
			 List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(arriveDeptCode);
			 //结果集不为空才添加到映射的到达部门
			 if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
				 //映射的到达部门结果集
				 List<String> arrivelist = new ArrayList<String>();
				 for (DeptTransferMappingEntity entity : deptTransferMappinglist) {
						 //获取一级网点的code
					     String fthNetworkCode = entity.getFthNetworkCode();
						 if(fthNetworkCode != null){
							 arrivelist.add(fthNetworkCode);
						 }
						 //获取二级网点的code
						 String secNetworkCode = entity.getSecNetworkCode();
						 if(secNetworkCode != null){
							 arrivelist.add(secNetworkCode);
						 }												 
					}
				  //去掉重复的数据再加入集合中
				 deptMapList.addAll(new HashSet(arrivelist));
			 }
		 }
		 //外场到一级网点
		 if("YC".equals(departWork) && "YJB".equals(arriveWork)){
			 //根据一级网点查询到映射表的数据 
			 List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(arriveDeptCode);
			 //判断结果集不为空才添加到映射的到达部门
			 if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
				 for (DeptTransferMappingEntity entity : deptTransferMappinglist) {
						//获取二级网点的code
						 String secNetworkCode = entity.getSecNetworkCode();
						 if(secNetworkCode != null){
							 	//添加关联
							   deptMapList.add(secNetworkCode);
						 }
					}
			 }
		 }
		 //一级网点到外场
		 if("YJB".equals(departWork) && "YC".equals(arriveWork)){
			 //根据一级网点查询到映射表的数据 
			 List<DeptTransferMappingEntity> deptTransferMappinglist = deptTransferMappingService.queryDeptTransferMappingListByCode(superOrgCode);
			 //判断结果集不为空才添加到映射的到达部门 
			 if(deptTransferMappinglist!=null && deptTransferMappinglist.size()>0){
				 //获取直营网点的code
				 String deptCode = deptTransferMappinglist.get(0).getDeptCode();
				 //替换下一部门code
				 if(deptCode != null){
					 deptMapList.add(deptCode);
				 }
			 }
			 
		 }
		return deptMapList;
	}
}
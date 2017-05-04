package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
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
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKLoadTempDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDALoadWKService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadSourceEnum;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehicleSealInfoDto;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.WKLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PDALoadWKService implements IPDALoadWKService{
	

	private IExpressVehiclesService expressVehiclesService;
	private ITruckDepartPlanDetailService truckDepartPlanDetailService;
	private IBillNumService billNumService;
	private IPassInviteApplyService passInviteApplyService;
	private ILineService lineService;
	private IExpressLineService expresslineService ;
	private IVehicleAgencyCompanyService vehicleAgencyCompanyService;
	private IWKLoadTempDao wkLoadTempDao;
	
	private IFOSSToWkService fossToWkService;
	
	private IEmployeeService employeeService;
	private IPDALoadDao pdaLoadDao;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IPDACommonService pdaCommonService;
	private IVehicleSealService vehicleSealService;
	private ILoadService loadService;
	// 派送单状态更新记录表Service
	/** 子类公用service */
	private IPlatformService platformService;
	private IPorterService porterService;
	private ILoadAndUnloadSquadService loadAndUnloadSquadService;
	private IPDAUnloadTaskDao pdaUnloadTaskDao;
	private ISaleDepartmentService saleDepartmentService;
	private IVehicleService vehicleService;
	private ILdpAgencyCompanyService ldpAgencyCompanyService;
	private IConfigurationParamsService configurationParamsService;
	private IHeavyBubbleRatioService heavyBubbleRatioService;
	
	
	static final Logger LOGGER = LoggerFactory.getLogger(PDALoadWKService.class);
	
	
	private String buildInvokeTrace(Throwable throwable) {
		try {
			StackTraceElement[] elements = throwable.getStackTrace();
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement e : elements) {
				sb.append(e.getClassName()).append(":").append(e.getMethodName()).append(":").append(e.getLineNumber()).append("\r\n");
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	@Transactional
	public LoadTaskResultDto createLoadTask(WKLoadTaskDto wkLoadTask) {
		String trace = buildInvokeTrace(new Throwable());
		LOGGER.info(wkLoadTask.toString() + "\r\n" + trace);
		//如果车辆已封签，则不能建立装车任务
		VehicleAssociationDto vehicleDto = null;
		String vehicleNo = wkLoadTask.getVehicleNo();
		if (StringUtil.isEmpty(vehicleNo)) {
			LOGGER.info("无车牌,不能建立装车任务 ");
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID);
		}
		if(vehicleNo.startsWith("德")){
			ExpressVehiclesEntity entity=new ExpressVehiclesEntity();
			entity.setVehicleNo(vehicleNo);
			entity.setActive(FossConstants.ACTIVE);
			List<ExpressVehiclesEntity> expressVehiclesEntitys = expressVehiclesService.queryExpressVehiclesByEntity(entity);
			if(null ==expressVehiclesEntitys ||expressVehiclesEntitys.size()<=0){
				LOGGER.info("无效车牌,不能建立装车任务 null ==expressVehiclesEntitys ||expressVehiclesEntitys.size()<=0");
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
			}
		}else{
			try{
				vehicleDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
			}catch(Exception e){
				LOGGER.info("无效车牌,不能建立装车任务 " + e.getMessage());
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
		if(StringUtils.isNotBlank(wkLoadTask.getTaskNo())){
			LOGGER.info("任务编号不为空,只有装车状态为装车中的任务可以下拉装车清单");
			loadTaskNo = wkLoadTask.getTaskNo();
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
				LOGGER.info("无效任务 " + wkLoadTask.getTaskNo());
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
			}		
		}else{
			//如果任务编号为空，则新建装车任务
			LOGGER.error("建立装车任务开始,车牌号是:"+vehicleNo + "来源是:" + wkLoadTask.getSendType());
			if(CollectionUtils.isNotEmpty(wkLoadTask.getDestOrgCodes())){
				if(wkLoadTask.getDestOrgCodes().size()>1){
					LOGGER.info("禁止输入多个到达部门!" + wkLoadTask.getDestOrgCodes());
					throw new TfrBusinessException("禁止输入多个到达部门!");
				}
				for(String d : wkLoadTask.getDestOrgCodes()){
					LOGGER.error(d);
				}
			}else{
				LOGGER.info("到达部门为空,不能建立装车任务!");
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
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(wkLoadTask.getCreateOrgCode());
			boolean isNeedDepartPlan = false;
			boolean isSalesDepartment = false;
			if(origOrg != null){
				//如果营业部
				if(FossConstants.YES.equals(origOrg.getSalesDepartment())){
					SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
					if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){//如果是驻地营业部，出发部门设为其外场部门
						//origOrg = pdaCommonService.getCurrentOutfieldCode(loadTask.getCreateOrgCode());
						origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
					}
					
					//灰度过后，悟空上线，这段限制要加上
					isSalesDepartment = true;
					//TODO
//							if(!wkLoadTask.getSendType().equals(LoadSourceEnum.LINGDAN.getCode())) {
//								throw new TfrBusinessException("此任务为营业部零担装车,不可勾选"); 
//							}
				}
				//装车部门为外场
				else{
					
					
					//查询对应装车部门的上级部门
					origOrg = pdaCommonService.getCurrentOutfieldCode(wkLoadTask.getCreateOrgCode());
				}
			}
			if(origOrg == null){
				LOGGER.info("无效出发部门-空!" + wkLoadTask.getCreateOrgCode());
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}else{
				if(FossConstants.YES.equals(origOrg.getTransferCenter())){
					//装车类型不是偏线装车、不是落地配装车
					if (!LoadConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(wkLoadTask.getLoadTaskType())
							&& !LoadConstants.LOAD_TASK_TYPE_LDP.equals(wkLoadTask.getLoadTaskType())) {
						// 调用综合接口查询车辆所服务部门不为接送货部门
						if (vehicleDto != null) {
							if (!DictionaryValueConstants.BES_VEHICLE_USED_TYPE_PKP
									.equals(vehicleDto.getVehicleUsedTypeCode())) {
								// 分部交接不校验发车计划
								if (!StringUtil.equals(wkLoadTask.getLoadTaskType(),
										TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION)) {
									// 判定是否属于快递分部，快递分部装车不校验发车计划
									if (!FossConstants.YES.equals(origOrg.getExpressBranch())) {
										isNeedDepartPlan = true;
									}
								}
							}
						}
						
						//长途外请车要约车
						if (LoadConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(wkLoadTask.getLoadTaskType())) {
							// 验证约车
							int isleasedTruck = wkLoadTempDao.isLeasedTruck(vehicleNo);
							// 判断是否是外请车
							if (isleasedTruck > 0) {
								PassInviteApplyEntity inviteVehicleEntity = passInviteApplyService
										.queryPassInvitePrice(vehicleNo, origOrg.getCode());
								if (inviteVehicleEntity == null) {
									throw new TfrBusinessException("没有约车信息");
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
				//校验封签  = "车辆已封签,不能建立装车任务
				VehicleSealInfoDto seal = vehicleSealService.queryVehicleSealInfo(vehicleNo);
				if(seal != null){
					throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_SEALED1); 
				}
				//如果车辆已使用未出发（非本部门），则不能建立装车任务，该方法会自己拋异常的，所以不用在这里抛
				loadService.queryUndepartRecByVehicleNo(origOrg.getCode(),vehicleNo,TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
				
			}
			//最早出发时间
			Date lastedDepartTime;
			TruckDepartPlanDetailDto departPlan = null;
//			String vehicleNo = vehicleNo;
			lastedDepartTime = new Date((new Date().getTime()+LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
			//只有一个到达部门才合法，否则上面会报错
			String destOrgCode = wkLoadTask.getDestOrgCodes().get(0);
			loadDestOrg = new LoadDestOrgEntity();
			
			//查询发车计划
			if (!isSalesDepartment) {
				departPlan = getTruckDepartPlanDetailDto(vehicleDto, wkLoadTask.getLoadTaskType(), origOrg.getCode(), destOrgCode, vehicleNo);
			}
			if(departPlan != null){
				loadDestOrg.setTruckDepartPlanDetailId(departPlan.getId());
				lineNames.append(departPlan.getLineName());
				lineNames.append("");
				if(departPlan.getPlanDepartTime() != null && departPlan.getPlanDepartTime().before(lastedDepartTime)){
					lastedDepartTime = departPlan.getPlanDepartTime();
				}
			}else{
				if(vehicleDto == null && !(TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(wkLoadTask.getLoadTaskType()))){
					if(!vehicleNo.startsWith("德")){
						throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
					}
				}
				//判定快递车辆，是否能使用
				if(StringUtils.isNotBlank(vehicleNo)){
					if(vehicleNo.startsWith("德")){
						if(!(TransferPDADictConstants.LOAD_TASK_TYPE_SHORT_DISTANCE.equals(wkLoadTask.getLoadTaskType())
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
			if(TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(wkLoadTask.getLoadTaskType())){
				BusinessPartnerEntity destOrg = vehicleAgencyCompanyService.queryEntityByCode(destOrgCode);
				if(destOrg != null){
					loadDestOrg.setDestOrgName(destOrg.getAgentCompanyName());
					destOrgNames.append(destOrg.getAgentCompanyName());
					destOrgNames.append(" ");
				}
			}else if(TransferPDADictConstants.LOAD_TASK_TYPE_LDP.equals(wkLoadTask.getLoadTaskType())){//快递:如果落地配装车：查询落地配部门
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
				if(StringUtils.isNotBlank(wkLoadTask.getPlatformNo())){
					if(origOrg != null){
						if(StringUtils.isNotBlank(origOrg.getCode())){
							PlatformEntity plateform = platformService.queryPlatformByCode(origOrg.getCode(), wkLoadTask.getPlatformNo());
							if(plateform != null){
								loadTaskEntity.setPlatformId(plateform.getVirtualCode());
							}else{
								throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_PLATEFORM);
							}
						}
					}
					loadTaskEntity.setPlatformNo(wkLoadTask.getPlatformNo());
				}
				if(StringUtils.isNotBlank(loadTaskEntity.getPlatformId())){
					//调用月台服务，占用月台
					try{
						pdaCommonService.updatePlatformStateByCreateTask(loadTaskEntity.getPlatformId(),vehicleNo,loadTaskNo,
								origOrg.getCode(),PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE,
								PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_LOAD,loadBeginTime,lastedDepartTime);
						//platformDispatchService.updatePlatformStatusForUsing(distributeEntity);
					}catch(Exception e){
						LOGGER.error("PDALoadWKService.createLoadTask 报错:" + StringUtils.substring(e.getMessage(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
					}
				}
			}

				
			//装车任务
			loadTaskEntity.setId(loadTaskId);
			loadTaskEntity.setGoodsType(wkLoadTask.getGoodsType());
			loadTaskEntity.setLoadStartTime(df.format(loadBeginTime));
			loadTaskEntity.setOrigOrgCode(origOrg.getCode());
			//调用综合接口查询部门名称
			if(origOrg != null){
				loadTaskEntity.setOrigOrgName(origOrg.getName());
			}
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			loadTaskEntity.setTaskNo(loadTaskNo);
			loadTaskEntity.setTaskType(wkLoadTask.getLoadTaskType());
			loadTaskEntity.setVehicleNo(vehicleNo);
			loadTaskEntity.setBeCreateGapRep(FossConstants.NO);
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			loadTaskEntity.setTransitGoodsType(wkLoadTask.getTransitGoodsType());
			
			//理货员
			PorterEntity porter;
			for(LoaderDto loaderCode : wkLoadTask.getLoaderCodes()){
				loader = new LoaderParticipationEntity();
				//理货员取pda传过来的时间 2013-7-26-liubinbin
				loader.setJoinTime(wkLoadTask.getCreateTime()==null?loadBeginTime:wkLoadTask.getCreateTime());
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
				if(loaderCode.getLoaderCode().equals(wkLoadTask.getOperatorCode())){
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
			
			if(LoadSourceEnum.KUIDI.getCode().equals(wkLoadTask.getSendType())) {
				loadTaskEntity.setLoadSource(LoadSourceEnum.KUIDI.getCode());
			}
			
			//插入装车任务
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			
			// 快递,和快递何单同步装车任务给悟空系统
			if(!LoadSourceEnum.LINGDAN.getCode().equals(wkLoadTask.getSendType())) {
				boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
				LOGGER.info("对接悟空开关（是否同步装车任务给悟空系统）：" + tfrSwitch4Ecs);
				if (tfrSwitch4Ecs
						&& !TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION.equals(wkLoadTask.getLoadTaskType())
						&& !TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER.equals(wkLoadTask.getLoadTaskType())) {
					wkLoadTask.getLoadTaskCreateDto().setVehicleNo(vehicleNo);
					wkLoadTask.getLoadTaskCreateDto().setArrivalDeptName(loadTaskEntity.getDestOrgNames());
					wkLoadTask.getLoadTaskCreateDto().setArrivalDeptNo(wkLoadTask.getDestOrgCodes().get(0));
					wkLoadTask.getLoadTaskCreateDto().setLoadBeginTime(loadBeginTime);
					wkLoadTask.getLoadTaskCreateDto().setLoadDeptName(loadTaskEntity.getOrigOrgName());
					wkLoadTask.getLoadTaskCreateDto().setOrigOrgCode(loadTaskEntity.getOrigOrgCode());
					wkLoadTask.getLoadTaskCreateDto().setPlatformNo(loadTaskEntity.getPlatformNo());
					wkLoadTask.getLoadTaskCreateDto().setTaskType(loadTaskEntity.getTaskType());
					wkLoadTask.getLoadTaskCreateDto().setTruckOutboundPlanNo(departPlan == null ? "" : departPlan.getTruckDepartPlanId() );
					wkLoadTask.getLoadTaskCreateDto().setLoadTaskNo(loadTaskNo);
					wkLoadTask.setCreateOrgCode(loadTaskEntity.getOrigOrgCode());
					wkLoadTask.getLoadTaskCreateDto().setLoaders(wkLoadTask.getLoaderCodes());
					wkLoadTask.getLoadTaskCreateDto().setSendType(wkLoadTask.getSendType());
//						addDataToWKLOADTemp(loadTaskNo, wkLoadTask.getLoadTaskCreateDto(), LoadStatueEnum.CREATE.getCode() );
					
					@SuppressWarnings("unchecked")
					Map<String, String> resMap =  (Map<String, String>) fossToWkService.sysnCreateLoadToWK(JSON.toJSONString(wkLoadTask.getLoadTaskCreateDto()));
					if(!resMap.containsKey("result") ) {
						throw new TfrBusinessException("ECS - 同步装车任务失败");
					} else {
						if( "0".equals(resMap.get("result")) ) {
							if(resMap.containsKey("errMsg")) {
								throw new TfrBusinessException("ECS - "+resMap.get("errMsg"));
							} else {
								throw new TfrBusinessException("ECS - 同步装车任务失败,发生未知错误");
							}
						}
					}
				}
			}
		}
				
		//插入装车PDA
		pdaEntity.setTaskNo(loadTaskNo);
		pdaEntity.setDeviceNo(wkLoadTask.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		LOGGER.error("建立装车任务结束"+loadTaskNo);
		return createLoadTaskResult(loadTaskNo,
				loadTaskEntity.getOrigOrgCode(), creator, loaderCodes,
				vehicleDto);
			
	}
	
	/**
	 * com.deppon.foss.module.transfer.load.server.service.impl.
	 * AbstractPDALoadService.createLoadTaskResult(String, String,
	 * LoaderParticipationEntity, List<LoaderDto>, VehicleAssociationDto)
	 * 从上面复制过来的
	 */
	private LoadTaskResultDto createLoadTaskResult(String taskNo, String orgCode, LoaderParticipationEntity creator,
			List<LoaderDto> loaders, VehicleAssociationDto vehicleDto) {
		LoadTaskResultDto loadTaskResultDto = new LoadTaskResultDto();
		if (creator != null) {
			loadTaskResultDto.setCreatorCode(creator.getLoaderCode());
			loadTaskResultDto.setCreatorName(creator.getLoaderName());
		}
		loadTaskResultDto.setLoaders(loaders);

		// 车辆额定载重、额定净空
		if (vehicleDto != null) {
			double vehicleDeadWeight = 0;
			if (vehicleDto.getVehicleDeadLoad() != null) {
				vehicleDeadWeight = vehicleDto.getVehicleDeadLoad().doubleValue();
			}
			double vehicleDeadVolume = 0;
			if (vehicleDto.getVehicleSelfVolume() != null) {
				vehicleDeadVolume = vehicleDto.getVehicleSelfVolume().doubleValue();
			}
			loadTaskResultDto.setVehicleDeadVolume(vehicleDeadVolume);
			loadTaskResultDto.setVehicleDeadWeight(vehicleDeadWeight);
			loadTaskResultDto.setVehicleLengthName(vehicleDto.getVehicleLengthName());
			if (vehicleDto.getVehicleUsedTypeName() != null) {
				if (vehicleDto.getVehicleUsedTypeName().equals("长途车")) {
					if (StringUtils.isNotBlank(vehicleDto.getVehicleOrganizationCode())) {
						loadTaskResultDto.setVehicleLengthName("长途公司车");
					}
				}
			}
		}
		loadTaskResultDto.setTaskNo(taskNo);
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter = conversionParameters(orgCode);
		loadTaskResultDto.setExpressConvertParameter(converParameter);
		return loadTaskResultDto;
	}
	
	/**
	 * 从com.deppon.foss.module.transfer.load.server.service.impl.AbstractPDALoadService.conversionParameters(String)
	 * 复制过来的
	 * @param orgCode
	 * @return
	 */
	private BigDecimal conversionParameters(String orgCode) {
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter = BigDecimal.ZERO;
		String stringValue = "";
		try {
			if (StringUtils.isNotEmpty(orgCode)) {
				// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				stringValue = heavyBubbleRatioService.queryHeavyBubbleParamByOutfield(orgCode);
			}

		} catch (Exception e) {
			throw new TfrBusinessException("调综合接口根据外场编码来查询重泡比参数异常" + e.toString());
		}
		if (stringValue != null && StringUtils.isNotEmpty(stringValue)) {
			double doubleValue = Double.valueOf(stringValue.toString());
			converParameter = new BigDecimal(doubleValue);
			BigDecimal a = new BigDecimal("1.000");
			// 重泡比为重量体积转换参数分之一
			converParameter = a.divide(converParameter, LoadConstants.SONAR_NUMBER_3);
		} else {
			ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
					ConfigurationParamsConstants.TFR_PARM_EXPRESS_CONVERTVOLUME_PARAMETERS, "DIP");
			if (paramEntity != null && StringUtils.isNotEmpty(paramEntity.getConfValue())) {
				converParameter = new BigDecimal(paramEntity.getConfValue());
			}
		}
		return converParameter;
	}
	
	
	
	/**
	* @description 查询发车计划
	* @param vehicleDto
	* @param loadTaskType
	* @param origCode
	* @param destOrgCode
	* @param vehicleNo
	* @return
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月28日 上午7:55:56
	*/
	public TruckDepartPlanDetailDto getTruckDepartPlanDetailDto(VehicleAssociationDto vehicleDto, String loadTaskType,
			String origCode, String destOrgCode, String vehicleNo) {

		TruckDepartPlanDetailDto departPlan = null;
		if (vehicleDto != null) {
			// 判定长途装车的车牌号是否是挂牌号（挂牌号与车牌号格式一样）,若是查询挂牌号的发车计划是否存在 alfred 2014-9-12
			if (StringUtils.equals(vehicleDto.getVehicleType(), "vehicletype_trailer")
					&& StringUtils.isNotBlank(vehicleDto.getVehicleMotorcadeCode())) {
				if (TransferPDADictConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(loadTaskType)) {
					departPlan = truckDepartPlanDetailService.queryDepartPlanDetailByTrailerVehicleNo(origCode,
							destOrgCode, vehicleNo, null);
				}
			} else {
				// 根据车牌号查询发车计划
				departPlan = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetail(origCode, destOrgCode,
						vehicleNo, null);
			}
		} else {
			if (TransferPDADictConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(loadTaskType)) {
				// 根据货柜号查询发车计划
				departPlan = truckDepartPlanDetailService.queryLatestTruckDepartPlanDetailByContainerNo(origCode,
						destOrgCode, vehicleNo, null);
				if (departPlan != null) {
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

		return departPlan;

	}

	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}

	public void setTruckDepartPlanDetailService(
			ITruckDepartPlanDetailService truckDepartPlanDetailService) {
		this.truckDepartPlanDetailService = truckDepartPlanDetailService;
	}

	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}

	public void setPassInviteApplyService(
			IPassInviteApplyService passInviteApplyService) {
		this.passInviteApplyService = passInviteApplyService;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	public void setVehicleAgencyCompanyService(
			IVehicleAgencyCompanyService vehicleAgencyCompanyService) {
		this.vehicleAgencyCompanyService = vehicleAgencyCompanyService;
	}

	public void setWkLoadTempDao(IWKLoadTempDao wkLoadTempDao) {
		this.wkLoadTempDao = wkLoadTempDao;
	}

	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}

	public void setPorterService(IPorterService porterService) {
		this.porterService = porterService;
	}

	public void setLoadAndUnloadSquadService(
			ILoadAndUnloadSquadService loadAndUnloadSquadService) {
		this.loadAndUnloadSquadService = loadAndUnloadSquadService;
	}

	public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
		this.pdaUnloadTaskDao = pdaUnloadTaskDao;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setLdpAgencyCompanyService(
			ILdpAgencyCompanyService ldpAgencyCompanyService) {
		this.ldpAgencyCompanyService = ldpAgencyCompanyService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setHeavyBubbleRatioService(
			IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}

}

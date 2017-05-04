package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
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
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoadManagerExceptionDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKLoadTempDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.server.service.IWKTfrBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadSourceEnum;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskSerialNoDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehicleSealInfoDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.CheckVehicleNoResultDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.LoadScanDetailDubboDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.LoadTaskDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.LoadTaskResultDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.LoaderDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.load.dubbo.api.define.PDADubboResultDto;
import com.deppon.foss.module.transfer.load.dubbo.api.define.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.load.dubbo.api.define.SubmitLoadTaskDto;
import com.deppon.foss.module.transfer.load.dubbo.api.exception.TfrLoadException;
import com.deppon.foss.module.transfer.load.dubbo.api.service.IPDALoadCarManagerServicedubbo;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskCreateDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckDepartPlanDetailService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装车管理服务类
 * @author 332209
 *
 */
public class PDALoadCarManagerServicedubbo implements IPDALoadCarManagerServicedubbo{

	/**
	 * 日志信息记录
	 */
	static final Logger logger = LoggerFactory.getLogger(PDALoadCarManagerServicedubbo.class);
	
	/**
	 * 组织信息 Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 月台服务类Service
	 */
	private IPlatformService platformService;
	
	/**
	 * 装卸车PDA共通service
	 */
	private IPDACommonService pdaCommonService;
	
	/**
	 * 快递车辆服务类
	 */
	private IExpressVehiclesService expressVehiclesService;
	
	/**
	 * 用来提供交互所有关于“车辆（公司、外请）”的数据库对应数据访问复杂的Service
	 */
	private IVehicleService vehicleService;
	
	/**
	 * 营业部 Service
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 绑定与校验装车封签service
	 */
	private IVehicleSealService vehicleSealService;
	
	/**
	 * 装车通用service
	 */
	private ILoadService loadService;
	
	/**
	 * 同步给悟空创建装车,完成装车的临时表(T_OPT_WK_LOAD_TEMP)操作
	 */
	private IWKLoadTempDao wkLoadTempDao;
	
	/**
	 * 外请车受理服务Service
	 */
	private IPassInviteApplyService passInviteApplyService;
	
	/**
	 * 计划明细service
	 */
	private ITruckDepartPlanDetailService truckDepartPlanDetailService;
	
	/**
	 * PDA中转装车dao 
	 */
	private IPDALoadDao pdaLoadDao;
	
	/**
	 * PDA接口：卸车任务dao
	 */
	private IPDAUnloadTaskDao pdaUnloadTaskDao;
	
	/**
	 * 单号生成Service
	 */
	private IBillNumService billNumService;
	
	/**
	 * 线路服务类
	 */
	private ILineService lineService;
	
	/**
	 * 快递线路服务类
	 */
	private IExpressLineService expresslineService;
	
	/**
	 * 偏线代理公司service
	 */
	private IVehicleAgencyCompanyService vehicleAgencyCompanyService;
	
	/**
	 * 快递代理代理公司
	 */
	private ILdpAgencyCompanyService ldpAgencyCompanyService;
	
	/**
	 * Service
	 */
	private IPorterService porterService;
	
	/**
	 * 装卸车小队 Service接
	 */
	private ILoadAndUnloadSquadService loadAndUnloadSquadService;
	
	/**
	 * 人员 Service
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 系统配置参数 Service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * FOSS到 WKServer
	 */
	private IFOSSToWkService fossToWkService;
	
	/**
	 * 重泡比Service
	 */
	private IHeavyBubbleRatioService heavyBubbleRatioService;
	
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 为了解锁前提交扫描事务
	 */
	private IPDALoadService pdaLoadService; 
	
	/**
	 * 悟空交接单service
	 */
	private IWKTfrBillService wKTfrBillService;
	
	/**
	 *  待办jobservice
	 */
	private ITfrJobTodoService tfrJobTodoService;
	
	/**
	 * AssignLoadTaskDao
	 */
	private IAssignLoadTaskDao assignLoadTaskDao;
	
	/**
	 * 派送单状态更新记录表Service
	 */
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	
	/**
	 * 库存操作
	 */
	private IStockService stockService;
	
	/**
	 * autoGenerateHandOverBillDao
	 */
	private IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao;
	
	/**
	 * 任务车辆Service
	 */
	private ITruckTaskService truckTaskService;
	
	/**
	 * expressPackageDao
	 */
	private IExpressPackageDao expressPackageDao;

	/**
	 * 
	 */
	private ILoadManagerExceptionDao loadManagerExceptionDao;
	/**
	 * 校验车牌号
	 * @throws TfrLoadException 
	 */
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public CheckVehicleNoResultDto checkPdaVehicleNo(LoadTaskDto loadTask) throws TfrLoadException {
		logger.error("PDA车牌号校验");
		logger.error("校验PDA输入的车牌号是否可用START");
		boolean isNeedDepartPlan = false;
		boolean isSalesDepartment = false;
		
		CheckVehicleNoResultDto resultDto = new CheckVehicleNoResultDto();
		//获取到达部门
		String destOrgCode = loadTask.getDestOrgCodes().get(0);
		VehicleAssociationDto vehicleDto = null;
		TruckDepartPlanDetailDto departPlan = null;
		String vehicleNo = loadTask.getVehicleNo();
		
		//PDA输入的车牌号是空
		if (StringUtil.isEmpty(vehicleNo)) {
			logger.info("创建装车任务的车牌号是空");
			resultDto.setStatus("0");
			resultDto.setExceptionMsg("PDA输入的车牌号是空");
			return resultDto;
		}
		
		OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(loadTask.getCreateOrgCode());
		
		if (origOrg != null) {
			// 如果营业部
			if (FossConstants.YES.equals(origOrg.getSalesDepartment())) {
				SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
				if (saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())) {// 如果是驻地营业部，出发部门设为其外场部门
					origOrg = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
				}
				// 查询发车计划
				isSalesDepartment = true;
			} else {
				// 查询对应装车部门的上级部门
				origOrg = pdaCommonService.getCurrentOutfieldCode(loadTask.getCreateOrgCode());
			}
		}
		
		
		if(origOrg == null){
			logger.info("无效出发部门-空!" + loadTask.getCreateOrgCode());
			throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
		}else{
			if(FossConstants.YES.equals(origOrg.getTransferCenter())){
				//装车类型不是偏线装车、不是落地配装车
				if (!LoadConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(loadTask.getLoadTaskType())
						&& !LoadConstants.LOAD_TASK_TYPE_LDP.equals(loadTask.getLoadTaskType())) {
					// 调用综合接口查询车辆所服务部门不为接送货部门
					if (vehicleDto != null) {
						if (!DictionaryValueConstants.BES_VEHICLE_USED_TYPE_PKP
								.equals(vehicleDto.getVehicleUsedTypeCode())) {
							// 分部交接不校验发车计划
							if (!StringUtil.equals(loadTask.getLoadTaskType(),
									TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION)) {
								// 判定是否属于快递分部，快递分部装车不校验发车计划
								if (!FossConstants.YES.equals(origOrg.getExpressBranch())) {
									isNeedDepartPlan = true;
								}
							}
						}
					}
					
					//长途外请车要约车
					if (LoadConstants.LOAD_TASK_TYPE_LONG_DISTANCE.equals(loadTask.getLoadTaskType())) {
						// 验证约车
						int isleasedTruck = wkLoadTempDao.isLeasedTruck(vehicleNo);
						// 判断是否是外请车
						if (isleasedTruck > 0) {
							PassInviteApplyEntity inviteVehicleEntity = passInviteApplyService
									.queryPassInvitePrice(vehicleNo, origOrg.getCode());
							if (inviteVehicleEntity == null) {
								resultDto.setStatus("0");
								resultDto.setExceptionMsg("没有约车信息");
								return resultDto;
							}
						}
					}
				}
			}
		}
		
		if ("FINISHED".equals(loadTask.getSubmitStatus())) {
			LoadTaskEntity fossloadTask = pdaLoadDao.queryLoadTaskByTaskNo(loadTask.getTaskNo());
			logger.error("提交的时候校验车牌号开始");
			if (!StringUtils.equals(loadTask.getVehicleNo(), fossloadTask.getVehicleNo()) && StringUtils.isEmpty(fossloadTask.getErrorMsg())) {
				vehicleNo = loadTask.getVehicleNo();
				resultDto.setStatus("1");
				resultDto.setVehicleNo(vehicleNo);
				return resultDto;
			} else {
				//营业部装车和派送装车不校验
				if (!LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getLoadTaskType()) &&! TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE.equals(loadTask.getLoadTaskType())) {
					VehicleSealInfoDto seal = vehicleSealService.queryVehicleSealInfo(loadTask.getVehicleNo());
					if (seal != null) {
						resultDto.setStatus("0");
						resultDto.setExceptionMsg(
								TransferPDAExceptionCode.EXCEPTION_VEHICLE_SEALED + "封签部门: " + seal.getSealdOrgName());
						return resultDto;
					}
					try {
						// 如果车辆已使用未出发（非本部门），则不能建立装车任务，该方法会自己拋异常的，所以不用在这里抛
						loadService.queryUndepartRecByVehicleNo(origOrg.getCode(), loadTask.getVehicleNo(),
								TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
					} catch (Exception e) {
						resultDto.setStatus("0");
						resultDto.setExceptionMsg(e.getMessage());
						return resultDto;
					}
				}
			}
			logger.error("提交的时候校验车牌号结束");
		}
		
		if (vehicleNo.startsWith("德")) {
			ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
			entity.setVehicleNo(vehicleNo);
			entity.setActive(FossConstants.ACTIVE);
			List<ExpressVehiclesEntity> expressVehiclesEntitys = expressVehiclesService
					.queryExpressVehiclesByEntity(entity);
			if (null == expressVehiclesEntitys || expressVehiclesEntitys.size() <= 0) {
				logger.info("无效车牌,不能建立装车任务 null ==expressVehiclesEntitys ||expressVehiclesEntitys.size()<=0");
				resultDto.setStatus("0");
				resultDto.setExceptionMsg(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID);
				return resultDto;
			}
		} else {
			try {
				com.deppon.foss.module.transfer.load.dubbo.api.define.VehicleAssociationDto vehicleAssociationDto = new com.deppon.foss.module.transfer.load.dubbo.api.define.VehicleAssociationDto();
				vehicleDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
				BeanUtils.copyProperties(vehicleDto, vehicleAssociationDto);
				resultDto.setVehicleAssciationDto(vehicleAssociationDto);
			} catch (Exception e) {
				logger.info("无效车牌,不能建立装车任务 " + e.getMessage());
				resultDto.setStatus("0");
				resultDto.setExceptionMsg(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID);
				return resultDto;
			}
		}
		if (!"FINISHED".equals(loadTask.getSubmitStatus())) {
			if(vehicleDto != null){
				//校验封签  = "车辆已封签,不能建立装车任务
				VehicleSealInfoDto seal = vehicleSealService.queryVehicleSealInfo(vehicleNo);
				if(seal != null){
					resultDto.setStatus("0");
					resultDto.setExceptionMsg(TransferPDAExceptionCode.EXCEPTION_VEHICLE_SEALED1);
					return resultDto;
				}
				try {
					//如果车辆已使用未出发（非本部门），则不能建立装车任务，该方法会自己拋异常的，所以不用在这里抛
					loadService.queryUndepartRecByVehicleNo(origOrg.getCode(),vehicleNo,TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
				} catch (TfrBusinessException e) {
					resultDto.setStatus("0");
					resultDto.setExceptionMsg(e.getMessage());
					return resultDto;
				}
			}
		}
		//查询发车计划
		if (!isSalesDepartment) {
			departPlan = getTruckDepartPlanDetailDto(vehicleDto, loadTask.getLoadTaskType(), origOrg.getCode(), destOrgCode, vehicleNo);
		}
		if(departPlan != null){
			resultDto.setLineName(departPlan.getLineName());
			resultDto.setPlanDepartTime(departPlan.getPlanDepartTime());
			resultDto.setId(departPlan.getId());
		}else{
			if(vehicleDto == null && !(TransferPDADictConstants.LOAD_TASK_TYPE_PARTIALLINE.equals(loadTask.getLoadTaskType()))){
				if(!vehicleNo.startsWith("德")){
					resultDto.setStatus("0");
					resultDto.setExceptionMsg(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID);
					return resultDto;
				}
			}
			//判定快递车辆，是否能使用
			if(StringUtils.isNotBlank(vehicleNo)){
				if(vehicleNo.startsWith("德")){
					if(!(TransferPDADictConstants.LOAD_TASK_TYPE_SHORT_DISTANCE.equals(loadTask.getLoadTaskType())
							&&FossConstants.YES.equals(origOrg.getTransferCenter()))){
						resultDto.setStatus("0");
						resultDto.setExceptionMsg(TransferPDAExceptionCode.EXCEPTION_NOTUSE_EXPRESS_VEHICLE);
						return resultDto;
					}
				}
			}
			if(isNeedDepartPlan){
				resultDto.setStatus("0");
				resultDto.setExceptionMsg(TransferPDAExceptionCode.EXCEPTION_NO_DEPARTURE_PLANE);
				return resultDto;
			}
		}
		
		//校验全部通过则设置成功标识
		resultDto.setStatus("1");
		resultDto.setVehicleNo(vehicleNo);
		logger.error("PDA车牌号校验结束");
		//如果是提交装车任务的车牌号校验，并且校验通过
		if ("FINISHED".equals(loadTask.getSubmitStatus())) {
			//更新任务表里的车牌号信息
			updateLoadTaskVehicleNo(loadTask, vehicleNo, origOrg, resultDto);
		}
		
		return resultDto;
	}

	private void updateLoadTaskVehicleNo(LoadTaskDto loadTask, String vehicleNo, OrgAdministrativeInfoEntity origOrg, CheckVehicleNoResultDto resultDto) {
		logger.error("校验车牌号通过，更新线路：" + resultDto.getLineName());
		LoadTaskEntity entity = new LoadTaskEntity();
		// 校验通过，更新车牌号，更新异常信息为空
		entity.setVehicleNo(vehicleNo);
		entity.setErrorMsg(null);
		entity.setTaskNo(loadTask.getTaskNo());
		entity.setLine(resultDto.getLineName());
		pdaLoadDao.updateLoadTask(entity);
		if (loadTask.getSendType() != 0) {
			// 通知悟空修改车牌号
			LoadTaskCreateDto wkLoadTask = new LoadTaskCreateDto();
			wkLoadTask.setLoadTaskNo(loadTask.getTaskNo());
			wkLoadTask.setVehicleNo(vehicleNo);
			wkLoadTask.setOrigOrgCode(origOrg.getCode());
			wkLoadTask.setOperationOrgCode(origOrg.getCode());
			logger.error("origOrg.getCode()" + origOrg.getCode());
			wkLoadTask.setOperatorNo(loadTask.getOperatorCode());
			logger.error("origOrg.getCode()" + loadTask.getOperatorCode());
			wkLoadTask.setDriverNo(null);
			wkLoadTask.setDriverName(null);
			wkLoadTask.setDriverPhone(null);
			String reqMsg = JSON.toJSONString(wkLoadTask);
			
			@SuppressWarnings("unchecked")
			Map<String, String> resMap =  (Map<String, String>)fossToWkService.sysnEditLoadToWkByTaskNo(reqMsg);
			if(!resMap.containsKey("result") ) {
				throw new TfrBusinessException("ECS - 修改装车车牌号失败");
			} else {
				if( "0".equals(resMap.get("result")) ) {
					if(resMap.containsKey("errMsg")) {
						throw new TfrBusinessException("ECS - "+resMap.get("errMsg"));
					} else {
						throw new TfrBusinessException("ECS - 修改装车车牌号失败,发生未知错误");
					}
				}
			}
		}
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
	
	@Override
	@Transactional
	public LoadTaskResultDto createLoadTask(LoadTaskDto loadTask) throws TfrLoadException {
		logger.info(loadTask.toString() + "\r\n");
		String vehicleNo = loadTask.getVehicleNo();
		PDATaskEntity pdaEntity = new PDATaskEntity();
		Date loadBeginTime = new Date();
		String loadTaskNo;
		List<LoaderDto> loaderCodes = new ArrayList<LoaderDto>();
		LoadTaskEntity loadTaskEntity;
		LoaderParticipationEntity creator = null;
		//如果任务编号不为空
		if(StringUtils.isNotBlank(loadTask.getTaskNo())){
			logger.info("任务编号不为空,只有装车状态为装车中的任务可以下拉装车清单");
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
				logger.info("无效任务 " + loadTask.getTaskNo());
				throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
			}		
		}else{
			//如果任务编号为空，则新建装车任务
			logger.error("建立装车任务开始,车牌号是:"+vehicleNo + "来源是:" + loadTask.getSendType());
			if(CollectionUtils.isNotEmpty(loadTask.getDestOrgCodes())){
				if(loadTask.getDestOrgCodes().size()>1){
					logger.info("禁止输入多个到达部门!" + loadTask.getDestOrgCodes());
					throw new TfrLoadException("禁止输入多个到达部门!");
				}
				for(String d : loadTask.getDestOrgCodes()){
					logger.error(d);
				}
			}else{
				logger.info("到达部门为空,不能建立装车任务!");
				throw new TfrLoadException("到达部门为空,不能建立装车任务!");
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
				//如果营业部
				if(FossConstants.YES.equals(origOrg.getSalesDepartment())){
					SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(origOrg.getCode());
					if(saleDetp != null && FossConstants.YES.equals(saleDetp.getStation())){//如果是驻地营业部，出发部门设为其外场部门
						origOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDetp.getTransferCenter());
					}
				} else{
					//查询对应装车部门的上级部门
					origOrg = pdaCommonService.getCurrentOutfieldCode(loadTask.getCreateOrgCode());
				}
			}
			if(origOrg == null){
				logger.info("无效出发部门-空!" + loadTask.getCreateOrgCode());
				throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}
			
			String loadTaskId = loadTask.getLoadTaskUuid();
			//生成任务编号
			loadTaskNo = billNumService.generateLoadTaskNo(origOrg.getCode());
			
			logger.error("建立装车任务开始"+loadTaskNo);
			//到达部门名称s
			StringBuilder destOrgNames = new StringBuilder();
			//线路名称s
			StringBuilder lineNames = new StringBuilder();
			
			//最早出发时间
			Date lastedDepartTime;
			String departPlan = loadTask.getId();
			lastedDepartTime = new Date((new Date().getTime()+LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
			//只有一个到达部门才合法，否则上面会报错
			String destOrgCode = loadTask.getDestOrgCodes().get(0);
			loadDestOrg = new LoadDestOrgEntity();
			if(!StringUtils.isEmpty(departPlan)){
				loadDestOrg.setTruckDepartPlanDetailId(loadTask.getId());
				lineNames.append(loadTask.getLineName());
				lineNames.append("");
				if(loadTask.getPlanDepartTime() != null && loadTask.getPlanDepartTime().before(lastedDepartTime)){
					lastedDepartTime = loadTask.getPlanDepartTime();
				}
			}else{
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
						throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_LINE_MESSAGECODE);
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
				
			if(CollectionUtils.isNotEmpty(loadDestOrgs)){
				pdaLoadDao.insertTransferLoadDestOrg(loadDestOrgs);
				loadTaskEntity.setDestOrgNames(destOrgNames.toString().trim());
				String line = lineNames.toString().trim();
				if(StringUtils.isNotBlank(line)){
					loadTaskEntity.setLine(line);
				}
			}

			//*************************2013-3-11 修改********************
			//调用综合接口查询出发部门是否为外场
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
								throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_PLATEFORM);
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
						logger.error("PDALoadWKService.createLoadTask 报错:" + StringUtils.substring(e.getMessage(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
					}
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
			loadTaskEntity.setVehicleNo(vehicleNo);
			loadTaskEntity.setBeCreateGapRep(FossConstants.NO);
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			loadTaskEntity.setTransitGoodsType(loadTask.getTransitGoodsType());
			
			//上传总票数
			loadTaskEntity.setSubmitTotalCount("0");
			loadTaskEntity.setSendType(loadTask.getSendType());
			
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
							//throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
						}
					}
				}else{
					EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loaderCode.getLoaderCode());
					if(emp != null){
						loader.setLoaderName(emp.getEmpName());
					}else{
						throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
					}
					//非法理货员
					//throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
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
			
			if(LoadSourceEnum.KUIDI.getCode().equals(loadTask.getSendType())) {
				loadTaskEntity.setLoadSource(LoadSourceEnum.KUIDI.getCode());
			}
			if (StringUtils.isNotEmpty(loadTask.getExceptionMsg())) {
				loadTaskEntity.setErrorMsg(loadTask.getExceptionMsg());
			}
			if(StringUtils.isNotEmpty(loadTask.getDeviceNo())){
				loadTaskEntity.setDevice_no(loadTask.getDeviceNo());
			}
			//插入装车任务
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			// 快递,和快递何单同步装车任务给悟空系统
			if(!LoadSourceEnum.LINGDAN.getCode().equals(loadTask.getSendType())) {
				boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
				logger.info("对接悟空开关（是否同步装车任务给悟空系统）：" + tfrSwitch4Ecs);
				if (tfrSwitch4Ecs
						&& !TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION.equals(loadTask.getLoadTaskType())
						&& !TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getLoadTaskType())) {
					loadTask.getLoadTaskCreateDto().setVehicleNo(vehicleNo);
					loadTask.getLoadTaskCreateDto().setArrivalDeptName(loadTaskEntity.getDestOrgNames());
					loadTask.getLoadTaskCreateDto().setArrivalDeptNo(loadTask.getDestOrgCodes().get(0));
					loadTask.getLoadTaskCreateDto().setLoadBeginTime(loadBeginTime);
					loadTask.getLoadTaskCreateDto().setLoadDeptName(loadTaskEntity.getOrigOrgName());
					loadTask.getLoadTaskCreateDto().setOrigOrgCode(loadTaskEntity.getOrigOrgCode());
					loadTask.getLoadTaskCreateDto().setPlatformNo(loadTaskEntity.getPlatformNo());
					loadTask.getLoadTaskCreateDto().setTaskType(loadTaskEntity.getTaskType());
					loadTask.getLoadTaskCreateDto().setTruckOutboundPlanNo(StringUtils.isEmpty(departPlan) ? "" : loadTask.getId());
					loadTask.getLoadTaskCreateDto().setLoadTaskNo(loadTaskNo);
					loadTask.setCreateOrgCode(loadTaskEntity.getOrigOrgCode());
					loadTask.getLoadTaskCreateDto().setLoaders(loadTask.getLoaderCodes());
					loadTask.getLoadTaskCreateDto().setSendType(loadTask.getSendType());
					
					@SuppressWarnings("unchecked")
					Map<String, String> resMap =  (Map<String, String>) fossToWkService.sysnCreateLoadToWK(JSON.toJSONString(loadTask.getLoadTaskCreateDto()));
					if(!resMap.containsKey("result") ) {
						throw new TfrLoadException("ECS - 同步装车任务失败");
					} else {
						if( "0".equals(resMap.get("result")) ) {
							if(resMap.containsKey("errMsg")) {
								throw new TfrLoadException("ECS - "+resMap.get("errMsg"));
							} else {
								throw new TfrLoadException("ECS - 同步装车任务失败,发生未知错误");
							}
						}
					}
				}
			}
		}
				
		//插入装车PDA
		pdaEntity.setTaskNo(loadTaskNo);
		pdaEntity.setDeviceNo(loadTask.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		logger.error("建立装车任务结束"+loadTaskNo);
		VehicleAssociationDto vehicleAssociationDto = new VehicleAssociationDto();
		BeanUtils.copyProperties(loadTask.getVehicleAssciationDto() == null?
				new com.deppon.foss.module.transfer.load.dubbo.api.define.VehicleAssociationDto() :
				loadTask.getVehicleAssciationDto(), vehicleAssociationDto);
		return createLoadTaskResult(loadTaskNo,
				loadTaskEntity.getOrigOrgCode(), creator, loaderCodes,vehicleAssociationDto
				);
	}
	
	/**
	 * com.deppon.foss.module.transfer.load.server.service.impl.
	 * AbstractPDALoadService.createLoadTaskResult(String, String,
	 * LoaderParticipationEntity, List<LoaderDto>, VehicleAssociationDto)
	 * 从上面复制过来的
	 */
	private LoadTaskResultDto createLoadTaskResult(String taskNo, String orgCode, LoaderParticipationEntity creator,
			List<LoaderDto> loaders, VehicleAssociationDto vehicleDto) throws TfrLoadException {
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
	private BigDecimal conversionParameters(String orgCode) throws TfrLoadException {
		// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter = BigDecimal.ZERO;
		String stringValue = "";
		try {
			if (StringUtils.isNotEmpty(orgCode)) {
				// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				stringValue = heavyBubbleRatioService.queryHeavyBubbleParamByOutfield(orgCode);
			}

		} catch (Exception e) {
			throw new TfrLoadException("调综合接口根据外场编码来查询重泡比参数异常" + e.toString());
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
	
	@Override
	@Transactional
	public String submitLoadTask(SubmitLoadTaskDto dto) throws TfrLoadException {
		logger.info("PDA提交装车任务：" + dto.getExceptionMsg() + dto.getLoadTaskNo(), dto.getSendType());
		String taskNo = dto.getLoadTaskNo();
		// 查询派送单号
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		
		// 如果装车对象为空，直接返回正确的值
		if (loadTask == null) {
			// 成功
			return TransferPDADictConstants.SUCCESS;
		}
		//提交的总记录
		loadTask.setSubmitTotalCount(dto.getSubmitTotalTicketCount());
		
		//查询有没有提交的错误消息，如果有，则不生成交接单，如果没有则生成交接单
		if (StringUtils.isNotEmpty(dto.getExceptionMsg())) {
			loadTask.setErrorMsg(dto.getExceptionMsg());
			// 完成装车任务
			finishLoadAndSoOn(loadTask, dto.getLoadEndTime(), dto.getDeviceNo(), dto.getLoaderCode(), dto.getIsPageFlag());
		} else {
			// 完成装车任务
			finishLoadAndSoOn(loadTask, dto.getLoadEndTime(), dto.getDeviceNo(), dto.getLoaderCode(), dto.getIsPageFlag());
			//生成悟空交接单
			//如果是营业部装车，不需要生成悟空交接单---332219
			if(!StringUtils.equals(loadTask.getTaskType(),TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
				if (dto.getSendType() != 0) {
					sendToWKAndGetHandover(dto, loadTask);
				}
			}
		}
		// 成功
		return TransferPDADictConstants.SUCCESS;
	}
	
	public PDADubboResultDto pdaSubmitForceCheck(String taskNo){
		logger.info("PDA提交装车任务强制校验：" + taskNo);
		PDADubboResultDto pDADubboResultDto = new PDADubboResultDto();
		if (pdaLoadDao.queryUnSubmitPDAQty(taskNo) > 1) {
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE);
		} else {
			LoadTaskEntity loadTask = null;
			MutexElement mutexElement = null;
			try {
				// 查询派送单号
				loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
				if (loadTask != null && LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
					mutexElement = new MutexElement(loadTask.getDeliverBillNo(), "派送单编号", MutexElementType.DELIVERBILL_NO);
					// 互斥锁定
					boolean isLocked = businessLockService.lock(mutexElement, 0);
					// 若未上锁
					if (!isLocked) {
						// 抛出派送单异常
						throw new TfrBusinessException("派送单修改中请稍后再试");
					}
				}
				
				pDADubboResultDto.setStatus("1");
			} catch (CannotAcquireLockException e) {
				throw new TfrBusinessException("任务提交中请稍后再试");
			} finally {
				if (mutexElement != null) {
					businessLockService.unlock(mutexElement);
				}
			}
		}
		return pDADubboResultDto;
	}
	
	/**
	 * 生成悟空交接单，并获取交接单信息
	 * @param dto
	 * @param loadTask
	 */
	private void sendToWKAndGetHandover(SubmitLoadTaskDto dto, LoadTaskEntity loadTask) throws TfrLoadException {
		logger.error("生成悟空交接单开始..." + dto);
		//是否是零担，不是零担悟空才生成交接单
		if (!LoadSourceEnum.LINGDAN.getCode().equals(dto.getSendType())) {
			boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
			logger.info("对接悟空开关（是否smtTask悟空同步）：" + tfrSwitch4Ecs);
			if (tfrSwitch4Ecs
					&& !TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION.equals(loadTask.getTaskType())
					&& !TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				logger.info("非纯零担任务： " + dto.getSendType());
				dto.setTaskStatus(null);
				dto.setCancel(false);
				dto.setDeviceNo(null);
				dto.setLoaderCode(null);
				dto.setSubmitTotalTicketCount(null);
				dto.setIsPageFlag(null);

				// 第二套改同步
				Map<String, Object> result = fossToWkService.sysnSubmitLoadToWK(JSON.toJSONString(dto));
				String status = null;
				if (result == null || !result.containsKey("status") || result.get("status") == null) {
					throw new TfrLoadException("ECS - 提交装车任务异常");
				}

				status = result.get("status").toString();
				if (!status.equals("1")) {
					throw new TfrLoadException("ECS - " + result.get("exMsg"));
				}

				WKBillEntity wkBillEntity = null;
				if (result.get("data") == null) {
					logger.error("提交装车任务：悟空返回信息为空");
				} else {
					if (result.get("data") instanceof WKBillEntity) {
						wkBillEntity = (WKBillEntity) result.get("data");
					} else {
						String handoverString = result.get("data").toString();
						logger.info("第二套同步data:" + handoverString);
						wkBillEntity = JSON.parseObject(handoverString, WKBillEntity.class);
					}
					if (wkBillEntity != null) {
						Map<String, String> insertWKBill = wKTfrBillService.insertWKBill(wkBillEntity);
						if ("1".equals(insertWKBill.get("result"))) {
							logger.info("悟空交接单插入成功");
						} else {
							logger.error("没有悟空交接单信息，未插入任何信息");
						}
					}
				}
			}
		}
		Log.error("生成悟空交接单结束..." + dto);
	}
	
	/**
	 * 完成装车任务
	 * 
	 *
	 *
	 * @param taskNo
	 * 
	 * 
	 * @param loadEndTime
	 * @param deviceNo
	 * 
	 * @param loaderCode
	 * 
	 * @return
	 */
	private String finishLoadAndSoOn(LoadTaskEntity loadTask, Date loadEndTime, String deviceNo, String loaderCode,String isPageFlag) throws TfrLoadException {
		logger.error("提交装车任务开始" + loadTask.getTaskNo());
		String errorMsg = loadTask.getErrorMsg();
		String submitCount = loadTask.getSubmitTotalCount();
		// 查询装车任务
		// 只有装车任务状态为装车中的任务可以提交
		if ((loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState()))
				|| (loadTask != null && LoadConstants.LOAD_TASK_STATE_FINISHEN.equals(loadTask.getState()) && 
				StringUtils.equals(isPageFlag, "Y"))) {
			if (LoadConstants.LOAD_TASK_STATE_FINISHEN.equals(loadTask.getState())) {
				if (StringUtils.isEmpty(errorMsg)) {
					generatedHandOverBill(loadTask);
				}
				// 返回成功
				logger.error("提交装车任务结束" + loadTask.getTaskNo());
				return TransferPDADictConstants.SUCCESS;
			} else {
				// 锁住装车记录，只是很单纯的锁
				loadTask = pdaLoadDao.queryLoadTaskByTaskNoForUpdate(loadTask.getTaskNo());
				logger.error("人员设置开始");
				EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loaderCode);
				logger.error("人员设置结束");
				String loaderName = "装车多货";
				if (emp != null) {
					loaderName = emp.getEmpName();
					logger.error("进入人员设置IF判断");
				}
				logger.error("装车时间设置开始");
				// 格式化日期
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// Sets the 装车结束时间
				loadTask.setLoadEndTime(df.format(new Date()));
				logger.error("装车时间设置结束");
				// 状态FINISHED
				loadTask.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
				loadTask.setSubmitTotalCount(submitCount);
				loadTask.setErrorMsg(errorMsg);
				Date loadStartTime = new Date();
				try {
					// 转日期格式
					loadStartTime = df.parse(loadTask.getLoadStartTime());
				} catch (ParseException e1) {
					// 异常 记录日志
					logger.error("装车开始时间转化失败", e1);
				}
				// 修改装车任务
				pdaLoadDao.updateLoadTask(loadTask);
				// 修改装车任务中理货员
				Map<String, Object> param = new HashMap<String, Object>();
				// 任务id
				param.put("id", loadTask.getId());
				// 结束时间,结束的时间用pda传过来的时间，2013-7-26--liubinbin
				param.put("endTime", loadEndTime == null ? new Date() : loadEndTime);
				// 提交装车任务时更新理货员
				pdaLoadDao.updateLoaderParticipationByLoadTask(param);
				// 不为营业部装车，则更新月台状态
				if (!LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType()) || TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE.equals(loadTask.getTaskType())) {
					//
				} else {
					//8月9号版本这段代码被覆盖，现重新提交begin
					//326027 派送装车，添加派送单到JOB_TODO
					logger.error("派件装车扫描"+loadTask.getTaskNo()+"推送货物轨迹，插入未执行job开始： ");
					try{
						tfrJobTodoService.addJobTodo(loadTask.getTaskNo(),
							BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN,
							new String[]{BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ,
										 BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO,
										 BusinessGoalContants.BUSINESS_GOAL_TO_JIAWAYUN
					                    }, 
					        new Date(), 
					        loaderCode);  
					}catch(TfrBusinessException e){
						throw  e;
					}catch(Exception e){
						logger.error(e.getMessage());
						throw new TfrLoadException(e.toString());
					}
					logger.error("派件装车扫描"+loadTask.getTaskNo()+"推送货物轨迹，插入未执行job结束 ");
					//8月9号版本这段代码被覆盖，现重新提交end

					// 更新派送单状态为已装车
					DeliverBillEntity bill = new DeliverBillEntity();
					bill.setBillNo(loadTask.getDeliverBillNo());
					bill.setState(DeliverbillConstants.STATUS_LOADED);
					assignLoadTaskDao.updateDeliverBillState(bill, null);
					// 如果派送单号和派送单状态不为空，添加-派送单状态更新记录
					if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState())) {
						DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
						deliverBillVary.setDeliverBillNo(bill.getBillNo());// 派送单号
						deliverBillVary.setDeliverBillStatus(bill.getState());// 派送单状态
						deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
					}
				}
				// 修改PDA:leaveTime
				Map<String, Object> parameter = new HashMap<String, Object>();
				// 任务号
				parameter.put("taskNo", loadTask.getTaskNo());
				// 设备号
				parameter.put("deviceNo", deviceNo);
				// 离开时间
				parameter.put("leaveTime", new Date());
				pdaLoadDao.updatePDATaskEntity(parameter);

				// 处理多货2013-05-30修改，所有的装车都要处理多货
				pdaLoadService.moreGoodsHandler(loadTask.getOrigOrgCode(), loadTask.getId(), loaderCode, loaderName);

				// 处理派送装车扫描记录
				if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
					// 入库已删除货物
					pdaLoadService.inStockCanceledDeliverScan(loadTask.getOrigOrgCode(), loadTask.getId(), loaderCode,
							loaderName);
					// 生成派送装车少货记录
					this.deliverLoadCreateLackGoods(loadTask.getTaskNo(), loadTask.getOrigOrgCode(), loadTask.getId(),
							loadStartTime);
				} 
				// 出库装车扫描流水号
				logger.error("装车出库开始" + loadTask.getTaskNo());
				this.outStockLoadGoods(loadTask.getId(), loaderCode);
				logger.error("装车出库结束" + loadTask.getTaskNo());
				if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
					// 通知排单员
					try {
						pdaLoadService.noticeDeliverOrg(loadTask, loaderCode, loaderName);
					} catch (Exception e) {
						//
					}
				} else {
					// OA上报多货差错，派送装车不会有多货的情况
					try {
						pdaLoadService.oaReportMoreGoods(loadTask.getId(), loaderCode, loadTask.getOrigOrgCode());
					} catch (Exception e) {
					}
					if (StringUtils.isNotBlank(loadTask.getPlatformId())) {
						// 调用月台接口修改月台状态
						try {
							pdaCommonService.updatePlatformStateByFinishTask(loadTask.getTaskNo(), new Date());
						} catch (Exception e) {
							//
						}
					}
					if (StringUtils.isEmpty(errorMsg)) {
						generatedHandOverBill(loadTask);
					}
					
				}
				// 返回成功
				logger.error("提交装车任务结束" + loadTask.getTaskNo());
				return TransferPDADictConstants.SUCCESS;
			}
		} else {
			// 抛出异常
			logger.error(loadTask.getTaskNo(), TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
			logger.error("提交装车任务结束" + loadTask.getTaskNo());
			return TransferPDADictConstants.SUCCESS;
		}
	}


	private void generatedHandOverBill(LoadTaskEntity loadTask) {
		// 生成交接单
		// 276198-duhao-20151212-增加交接单号参数
		autoGenerateHandOverBillDao.createHandOverBillByTaskNo(loadTask.getTaskNo());
		List<String> handOverBillNos = pdaLoadDao.queryHandOverBillNoByTaskNo(loadTask.getTaskNo());
		// 生成任务车辆
		if (CollectionUtils.isNotEmpty(handOverBillNos)) {
			for (String handOverBillNo : handOverBillNos) {
				truckTaskService.createTruckTaskByHandOverBill(handOverBillNo);
			}
		}

		List<String> packageNoList = pdaLoadDao.queryLoadPackageNoById(loadTask.getId());
		if (CollectionUtils.isNotEmpty(packageNoList)) {
			for (String packageNo : packageNoList) {
				ExpressPackageEntity expressPackageEntity = expressPackageDao
						.queryExpressPackageByPackageNo(packageNo);
				if (expressPackageEntity.getExpressPackageType().equals("THROUGH_ARRIVE")) {
					@SuppressWarnings("rawtypes")
					Map paramsMap = pdaLoadDao.autoCreatePackHandoverbill(loadTask.getTaskNo(), packageNo,
							packageNo.length(), "Y");
					String handoverNo = (String) paramsMap.get("handoverNo");
					if (StringUtils.isNotBlank(handoverNo)) {
						// 生成任务车辆
						truckTaskService.createTruckTaskByHandOverBill(handoverNo);
					}
				} else if (expressPackageEntity.getExpressPackageType().equals("AIRTHROUGH_ARRIVE")) {
					@SuppressWarnings("rawtypes")
					Map paramsMap = pdaLoadDao.autoCreatePackHandoverbill(loadTask.getTaskNo(), packageNo,
							packageNo.length(), "KY");
					String handoverNo3 = (String) paramsMap.get("handoverNo");
					if (StringUtils.isNotBlank(handoverNo3)) {
						// 生成任务车辆
						truckTaskService.createTruckTaskByHandOverBill(handoverNo3);
					}
				} else {
					@SuppressWarnings("rawtypes")
					Map paramsMap = pdaLoadDao.autoCreatePackHandoverbill(loadTask.getTaskNo(), packageNo,
							packageNo.length(), "N");
					String handoverNo2 = (String) paramsMap.get("handoverNo");
					if (StringUtils.isNotBlank(handoverNo2)) {
						// 生成任务车辆
						truckTaskService.createTruckTaskByHandOverBill(handoverNo2);
					}
				}
			}
		}
	}
	
	/**
	 * 派送装车生成少货记录
	 * 
	 * @param taskNo
	 * @param origOrgCode
	 * @param taskId
	 * @param loadBeginTime
	 * @return
	 */
	public void deliverLoadCreateLackGoods(String taskNo, String origOrgCode, String taskId, Date loadBeginTime) {
		Map<String, String> lackWayBillQC = new HashMap<String, String>();
		// 任务号
		lackWayBillQC.put("taskNo", taskNo);
		// 查询少货运单
		List<DeliverLoadGapReportWayBillEntity> lackWayBills = pdaLoadDao.queryDeliverLoadLackWayBill(lackWayBillQC);
		for (DeliverLoadGapReportWayBillEntity lackWayBill : lackWayBills) {
			String loadWayBillDetailId;
			LoadWaybillDetailEntity loadWayBillEntity;
			// 少货运单id不为空
			if (StringUtils.isNotBlank(lackWayBill.getLoadWayBillDetailId())) {
				loadWayBillDetailId = lackWayBill.getLoadWayBillDetailId();
				if (lackWayBill.getStockQty() != lackWayBill.getPlanLoadQty()) {
					loadWayBillEntity = new LoadWaybillDetailEntity();
					loadWayBillEntity.setId(loadWayBillDetailId);
					// 设置 库存件数
					loadWayBillEntity.setStockQty(lackWayBill.getPlanLoadQty());
					loadWayBillEntity.setLoadQty(0);
					loadWayBillEntity.setScanQty(0);
					loadWayBillEntity.setLoadVolumeTotal(0);
					loadWayBillEntity.setLoadWeightTotal(0);
					pdaLoadDao.updateLoadTaskWayBillDetail(loadWayBillEntity);
				}
			} else {
				// 如果不存在装车运运单明细，则插入
				// 不会修改装车运单 明细，因为少货不会修改已存在的装车运单明细
				loadWayBillEntity = new LoadWaybillDetailEntity();
				// id
				loadWayBillDetailId = UUIDUtils.getUUID();
				// 设置id
				loadWayBillEntity.setId(loadWayBillDetailId);
				// 设置 loadTask_ID
				loadWayBillEntity.setLoadTaskId(taskId);
				// 设置 已装车件数
				loadWayBillEntity.setLoadQty(0);
				// 设置 已扫描件数
				loadWayBillEntity.setScanQty(0);
				// 设置 库存件数
				loadWayBillEntity.setStockQty(lackWayBill.getPlanLoadQty());
				// 设置 装车体积
				loadWayBillEntity.setLoadVolumeTotal(0);
				// 设置 装车重量
				loadWayBillEntity.setLoadWeightTotal(0);
				// 设置 出发部门编号
				loadWayBillEntity.setOrigOrgCode(origOrgCode);
				// 设置 运单号
				loadWayBillEntity.setWaybillNo(lackWayBill.getWayBillNo());
				// 设置 建立任务时间
				loadWayBillEntity.setTaskBeginTime(loadBeginTime);
				// 设置 运输性质
				loadWayBillEntity.setTransportType(lackWayBill.getTransportType());
				// 设置 收货部门
				loadWayBillEntity.setReceiveOrgName(lackWayBill.getReceiveOrgName());
				// 设置 到达部门
				loadWayBillEntity.setReachOrgName(lackWayBill.getReachOrgName());
				// 设置 包装
				loadWayBillEntity.setPack(lackWayBill.getPack());
				// 设置 货名
				loadWayBillEntity.setGoodsName(lackWayBill.getGoodsName());
				pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBillEntity);
			}
			int stockUnloadQty = lackWayBill.getLackGoodsQty();
			if (StringUtils.isNotBlank(lackWayBill.getLoadWayBillDetailId())) {
				// 查询已扫描未装车流水号
				/*
				 * List<String> scanUnloadState = new ArrayList<String>();
				 * scanUnloadState.add(TransferPDADictConstants.
				 * LOAD_GOODS_STATE_CANCELED);
				 * scanUnloadState.add(LoadConstants.
				 * LOAD_GOODS_STATE_NOT_LOADING);
				 */
				List<LoadSerialNoEntity> scanedUnloadSerialNos = pdaLoadDao
						.queryScanedUnloadSerialNos(loadWayBillDetailId);
				// 遍历扫描未装车流水号
				if (CollectionUtils.isNotEmpty(scanedUnloadSerialNos)) {
					stockUnloadQty = stockUnloadQty - scanedUnloadSerialNos.size();
					for (LoadSerialNoEntity loadSerialNo : scanedUnloadSerialNos) {
						int i = 0;
						i++;
						if (i <= lackWayBill.getLackGoodsQty()) {
							loadSerialNo.setBeLoaded(FossConstants.NO);
							//// 未装车
							loadSerialNo.setGoodsState(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);
							pdaLoadDao.updateLoadTaskSerialNo(loadSerialNo);
						} else {
							if (LoadConstants.LOAD_GOODS_STATE_NOT_LOADING.equals(loadSerialNo.getGoodsState())) {
								loadSerialNo.setBeLoaded(FossConstants.NO);
								// 未扫描
								loadSerialNo.setGoodsState(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
								pdaLoadDao.updateLoadTaskSerialNo(loadSerialNo);
							}
						}
					}
				}
			}

			// 查询库存少货流水号
			if (stockUnloadQty > 0) {
				List<LoadSerialNoEntity> stockUnloadSerialNos = pdaLoadDao.queryStockUnloadSerialNos(
						lackWayBill.getWayBillNo(), origOrgCode, loadWayBillDetailId, stockUnloadQty);
				if (CollectionUtils.isNotEmpty(stockUnloadSerialNos)) {
					for (LoadSerialNoEntity loadSerialNo : stockUnloadSerialNos) {
						loadSerialNo.setBeLoaded(FossConstants.NO);
						//// 未装车
						loadSerialNo.setGoodsState(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);
						// 主键id
						loadSerialNo.setId(UUIDUtils.getUUID());
						// 明细id
						loadSerialNo.setLoadWaybillDetailId(loadWayBillDetailId);
						loadSerialNo.setCreateTime(new Date());
						loadSerialNo.setOrigOrgCode(origOrgCode);
						loadSerialNo.setScanState("N/A");
						loadSerialNo.setTaskBeginTime(loadBeginTime);
						// 插入装车运单明细记录
						pdaLoadDao.insertLoadSerialNoEntity(loadSerialNo);
					}
				}
			}
		}
	}
	
	/**
	 * 出库装车货物
	 *
	 *
	 * @param taskId
	 * @param loaderCode
	 */
	public void outStockLoadGoods(String taskId, String loaderCode) {
		// 查询出库货物
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("taskId", taskId);
		condition.put("beLoaded", FossConstants.YES);

		// 查询库存的出库货物
		List<LoadTaskSerialNoDto> outStockGoodsList = pdaLoadDao
				.queryOutStockGoods(condition);
		List<InOutStockEntity> inOutStockList = new ArrayList<InOutStockEntity>();
		// 出库货物
		if (CollectionUtils.isNotEmpty(outStockGoodsList)) {
			for (LoadTaskSerialNoDto outStockGoods : outStockGoodsList) {
				// 记录出入库动作信息
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				// 设置 运单号
				inOutStockEntity.setWaybillNO(outStockGoods.getWayBillNo());
				// 设置 当前部门
				inOutStockEntity.setOrgCode(outStockGoods.getOrgCode());
				// 设置 流水号
				inOutStockEntity.setSerialNO(outStockGoods.getSerialNo());
				// 装车出库
				inOutStockEntity
						.setInOutStockType(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE);
				// 设置 操作人编号
				inOutStockEntity.setOperatorCode(loaderCode);
				// 设置 操作人姓名
				inOutStockEntity.setOperatorName(loaderCode);
				// 加入出入库动作信息
				inOutStockList.add(inOutStockEntity);
				// stockService.outStockPC(inOutStockEntity);
				logger.error("装车出库:" + outStockGoods.getWayBillNo()
						+ outStockGoods.getSerialNo() + "/");
			}
			// PC端操作批量按照类型出库
			stockService.outStockBatchPCByType(inOutStockList,
					StockConstants.OUT_STOCK_TYPE_LOADING, taskId);
		}

		// 查出虚拟出库货物
		List<LoadTaskSerialNoDto> ptpWaybills = pdaLoadDao
				.querySaleOutStockGoods(condition);
		List<InOutStockEntity> outPtpSaleStock = new ArrayList<InOutStockEntity>();
		if (CollectionUtils.isNotEmpty(ptpWaybills)) {
			for (LoadTaskSerialNoDto outStockGoods : ptpWaybills) {
				// 记录出入库动作信息
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				// 设置 运单号
				inOutStockEntity.setWaybillNO(outStockGoods.getWayBillNo());
				// 设置 当前部门
				inOutStockEntity.setOrgCode(outStockGoods.getOrgCode());
				// 设置 流水号
				inOutStockEntity.setSerialNO(outStockGoods.getSerialNo());
				// 装车出库
				inOutStockEntity
						.setInOutStockType(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE);
				// 设置 操作人编号
				inOutStockEntity.setOperatorCode(loaderCode);
				// 设置 操作人姓名
				inOutStockEntity.setOperatorName(loaderCode);
				// 加入出入库动作信息
				outPtpSaleStock.add(inOutStockEntity);
				
			}
			stockService.outStockSaleStockInfoSale(outPtpSaleStock);
		}
	}
	
	
	/**
	 * 中途添加、删除理货员
	 * 
	 * @author dp-duyi
	 * @date 2012-11-20 上午11:57:49
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#updateLoader(java.lang.String,
	 *      java.lang.String, java.util.Date, java.lang.String)
	 */
	@Override
	@Transactional
	public String modifyLoader(String taskNo, List<LoaderDto> loaderCodes, Date operateTime, String loaderState) throws TfrLoadException {
		
		List<com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto> targetDtoList = new ArrayList<com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto>();
		com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto targetDto = null;
		for (LoaderDto sourceDto : loaderCodes) {
			targetDto = new com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto();
			BeanUtils.copyProperties(sourceDto, targetDto);
			targetDtoList.add(targetDto);
		}
		
		// 查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		// 只有装车中的任务可以修改理货员
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			int addCount = 0;
			// 装车任务类型
			if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
				addCount = pdaCommonService.modifyLoader(loadTask.getId(),
						LoadConstants.LOADER_PARTICIPATION_DELIVER_LOAD, targetDtoList, operateTime, loaderState);
			} else {
				addCount = pdaCommonService.modifyLoader(loadTask.getId(),
						LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD, targetDtoList, operateTime, loaderState);
			}
			// 增加理货员
			if (TransferPDADictConstants.ADD_LOADR.equals(loaderState)) {
				loadTask.setLoaderQty(loadTask.getLoaderQty() + addCount);
				pdaLoadDao.updateLoadTask(loadTask);
			}
			
			boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
			logger.error("同步修改装车理货员信息到悟空系统开关" + tfrSwitch4Ecs);

			if (tfrSwitch4Ecs) {
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(loadTask.getOrigOrgCode());
				boolean isSalesDepartment = FossConstants.YES.equals(org.getSalesDepartment()) ? true : false;
				if (!isSalesDepartment) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 查询理货员
					LoaderParticipationEntity loaderParticipation = pdaLoadDao.queryLoaderByTaskID(loadTask.getId());
					String operatorNo = null;
					List<String> loaderList = new ArrayList<String>();
					if (null!=loaderParticipation) {
						operatorNo = loaderParticipation.getLoaderCode();
					}
					for (LoaderDto loaderCode : loaderCodes) {
						loaderList.add(loaderCode.getLoaderCode());
					}
					map.put("taskNo", taskNo);
					map.put("loaderCodes", loaderList);
					map.put("operatorNo", operatorNo);
					map.put("operationOrgCode", loadTask.getOrigOrgCode());
					map.put("operationTime", operateTime);
					if (TransferPDADictConstants.ADD_LOADR.equals(loaderState)) {
						map.put("operationType", loaderState);
					} else {
						map.put("operationType", "REMOVE");
					}

					syncPDALoadModifyLoaderToWk(map);
				}
			}
			// 返回成功
			return TransferPDADictConstants.SUCCESS;
		} else {// 装车任务为空，或状态不为装车中，不能修改理货员
			throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	/**
	 * @description 同步PDA装车修改理货员信息到悟空系统
	 * @param syncPDAmodifyLoaderToWk
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月19日 上午9:56:31
	 */
	private void syncPDALoadModifyLoaderToWk(Map<String,Object> map) throws TfrLoadException {
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
		
		String requestJsonStr=null;
		try {
			requestJsonStr = objectMapper.writeValueAsString(map);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		//调用同步数据接口
		FossToWKResponseEntity fossToWKResponseEntity=null;
		try {
			logger.error("同步装车修改理货员信息到悟空系统参数："+requestJsonStr);
			fossToWKResponseEntity = fossToWkService.syncPDALoadModifyLoaderToWk(requestJsonStr);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TfrLoadException("同步装车修改理货员信息到悟空系统失败，错误信息："+e.getMessage());
		}
		if(fossToWKResponseEntity==null){
			logger.error("同步装车修改理货员信息到悟空系统失败!");
			throw new TfrLoadException("同步装车修改理货员信息到悟空系统失败!");
		}
		if("0".equals(fossToWKResponseEntity.getStatus())){
			logger.error("同步装车修改理货员信息到悟空系统失败，错误信息："+fossToWKResponseEntity.getExMsg());
			throw new TfrLoadException("ECS-"+fossToWKResponseEntity.getExMsg());
		}
		
	}
	
	/**
	 * 非建立任务PDA提交任务
	 * 
	 * 
	 * @author dp-duyi
	 * @date 2012-11-20 上午8:41:50
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService#submitLoadTask(java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	@Transactional
	public String finishLoadTask(String taskNo, Date loadEndTime, String deviceNo, String loaderCode) throws TfrLoadException {
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		// 任务为进行中才能提交
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			// 更新pdaTask:leaveTime
			Map<String, Object> parameter = new HashMap<String, Object>();
			// 任务号
			parameter.put("taskNo", taskNo);
			// 设备号
			parameter.put("deviceNo", deviceNo);
			// 离开时间
			parameter.put("leaveTime", loadEndTime);
			// 更新PDA任务
			pdaLoadDao.updatePDATaskEntity(parameter);
			// 返回成功
			return TransferPDADictConstants.SUCCESS;
		} else {
			// 无效任务
			throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	@Override
	public String cancelLoadTask(String taskNo, String deviceNo, String operatorCode, Date cancelTime) throws TfrLoadException {
		logger.error("撤销装车任务开始" + taskNo);
		// 查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		// 状态为进行中
		if (loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())) {
			int scanQty = pdaLoadDao.queryScanSerialNoQTYByTaskId(loadTask.getId());
			// 扫描件数等于0才能取消
			if (scanQty > 0) {
				throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_EXTIST_SCAN_RECORD_MESSAGECODE);
			} else {
				loadTask.setState(LoadConstants.LOAD_TASK_STATE_CANCELED);
				pdaLoadDao.updateLoadTask(loadTask);
				
				if (LoadConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
					// 更新派送单状态为已分配
					DeliverBillEntity bill = new DeliverBillEntity();
					// Gets the 派送单单号
					bill.setBillNo(loadTask.getDeliverBillNo());
					// 已分配
					bill.setState(DeliverbillConstants.STATUS_ASSIGNED);
					// 更新派送单状态
					assignLoadTaskDao.updateDeliverBillState(bill, null);
					// 如果派送单号和派送单状态不为空，添加-派送单状态更新记录
					if (StringUtils.isNotBlank(bill.getBillNo()) && StringUtils.isNotBlank(bill.getState())) {
						DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
						deliverBillVary.setDeliverBillNo(bill.getBillNo());// 派送单号
						deliverBillVary.setDeliverBillStatus(bill.getState());// 派送单状态
						deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
					}
					// 更新分配装车任务状态为未开始
					Map<String, String> condition = new HashMap<String, String>();
					// 是否取消
					condition.put("beCancelled", FossConstants.NO);
					// 已分配派送装车任务状态
					condition.put("changeToTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_UNSTART);
					condition.put("conditionTaskState", LoadConstants.ASSIGN_DELIVER_LOAD_TASK_STATE_PROCESSING);
					// 派送单单号.
					condition.put("deliverBillNo", loadTask.getDeliverBillNo());
					// 更新分配记录状态：进行中、已完成
					assignLoadTaskDao.updateAssignedLoadTaskState(condition);
				}
				// 成功
				logger.error("撤销装车任务结束" + taskNo);
				if (StringUtils.isNotBlank(loadTask.getPlatformId())) {
					logger.error("撤销装车任务释放月台开始" + taskNo);
					pdaCommonService.updatePlatformStateByFinishTask(taskNo, new Date());
					logger.error("撤销装车任务释放月台结束" + taskNo);
				}
				
				//如果是营业部装车，不需要同步悟空---332219
//				if(!StringUtils.equals(loadTask.getTaskType(),TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
//					boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
//					logger.info("对接悟空开关（是否插入smtTask悟空同步表 cancled）：" + tfrSwitch4Ecs);
//					if (tfrSwitch4Ecs && !TransferPDADictConstants.LOAD_TASK_TYPE_DIVISION.equals(loadTask.getTaskType())
//							&& !TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER.equals(loadTask.getTaskType())) {
//						SmtWKLoadTaskDto dto = new SmtWKLoadTaskDto();
//						dto.setLoadTaskNo(taskNo);
//						dto.setCancel(true);
//						//addDataToWKLOADTemp(taskNo, dto, LoadStatueEnum.CREATE.getCode());
//						fossToWkService.sysnSubmitLoadToWK(JSON.toJSONString(dto));
//					}
//				}
				return TransferPDADictConstants.SUCCESS;
			}
		} else if (loadTask != null && LoadConstants.LOAD_TASK_STATE_CANCELED.equals(loadTask.getState())) {
			logger.error("撤销装车任务结束" + taskNo);
			return TransferPDADictConstants.SUCCESS;
		} else {
			// 异常
			logger.error("撤销装车任务结束" + taskNo);
			throw new TfrLoadException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}
	
	/**
 	 *查询未完成的中转装车任务2013-04-19修改
 	 * @param condition the condition
 	 * @return the list
 	 * @author dp-ruilibao
 	 * @date 2012-11-6 下午12:36:47
 	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService#queryAssignedLoadTask(com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity)
 	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryAssignedLoadTask(
			QueryAssignedLoadTaskEntity condition) throws TfrLoadException {
		
		com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity entity = new com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity();
		BeanUtils.copyProperties(condition, entity);
		List<PDAAssignLoadTaskEntity> loadList = new ArrayList<PDAAssignLoadTaskEntity>();
		Date currentTime = new Date();
		if(condition.getQueryTimeBegin()==null || condition.getQueryTimeEnd() == null){
			condition.setQueryTimeBegin(new Date(currentTime.getTime()-LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
			condition.setQueryTimeEnd(currentTime);
		}
		
		//PDA查询已分配装车任务:进行中、未开始的任务
		List<com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity> deliverLoadList = pdaLoadDao.queryAssignedLoadTask(entity);
		
		if(condition.getQueryTransportTimeBegin() == null || condition.getQueryTransportTimeEnd() == null){
			condition.setQueryTransportTimeBegin(new Date(currentTime.getTime()-LoadConstants.SONAR_NUMBER_3*LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
			condition.setQueryTransportTimeEnd(currentTime);
		}
		if(CollectionUtils.isNotEmpty(deliverLoadList)){
			List<PDAAssignLoadTaskEntity> targetList = null;
			try {
				targetList = this.copyTo(deliverLoadList, PDAAssignLoadTaskEntity.class);
			} catch (IllegalAccessException e) {
				throw new TfrLoadException("列表转换异常");
			} catch (InstantiationException e) {
				throw new TfrLoadException("列表转换异常");
			}
			//根据装车类型来判断
			List<PDAAssignLoadTaskEntity> deliverLoadLists = new ArrayList<PDAAssignLoadTaskEntity>();
			for(PDAAssignLoadTaskEntity loadTask : targetList){
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
		
		List<com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity> transportLoadList = pdaLoadDao.queryUnfinishedLoadTask(entity);
		if(CollectionUtils.isNotEmpty(transportLoadList)){
			List<PDAAssignLoadTaskEntity> targetLoadList = null;
			try {
				targetLoadList = this.copyTo(transportLoadList, PDAAssignLoadTaskEntity.class);
			} catch (IllegalAccessException e) {
				throw new TfrLoadException("列表转换异常");
			} catch (InstantiationException e) {
				throw new TfrLoadException("列表转换异常");
			}
			//存储集合
			List<PDAAssignLoadTaskEntity> deliverLoadLists = new ArrayList<PDAAssignLoadTaskEntity>();
			//营业部装车
			for(PDAAssignLoadTaskEntity loadTask : targetLoadList){
				if(StringUtils.equals(loadTask.getLoadTaskType(), TransferPDADictConstants.LOAD_TASK_TYPE_SALES_DISTANCE)){
					deliverLoadLists.add(loadTask);
				}
			}
			//非营业部装车
			for(PDAAssignLoadTaskEntity loadTask : targetLoadList){
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
	 * 派送/中转装车扫描
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年4月18日
	 * @param scanRecord
	 * @return
	 */
	@Override
	public String loadScan(LoadScanDetailDubboDto scanRecord) {
		LoadScanDetailDto scanRow = new LoadScanDetailDto();
		BeanUtils.copyProperties(scanRecord, scanRow);
		// 业务锁
		MutexElement mutex = new MutexElement(scanRecord.getWayBillNo(), "LOAD_SCAN", MutexElementType.PDA_WAYBILL_NO);
		logger.error("load scan begin:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
				+ scanRecord.getSerialNo());
		businessLockService.lock(mutex, LoadConstants.PDA_SCAN_OUTTIME);
		try {
			if (FossConstants.YES.equals(scanRecord.getIsPackageScan())) {
				/** 装车包扫描新方法 zwd 200968 */
				pdaLoadService.unlockPackageLoadScanNew(scanRow);
				// pdaLoadService.unlockPackageLoadScan(scanRecord);
			} else {
				pdaLoadService.unlockLoadScan(scanRow);
			}
		} catch (TfrBusinessException e) {
			// 释放锁
			businessLockService.unlock(mutex);
			// 记录日志
			logger.error("load scan exception:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
					+ scanRecord.getSerialNo(), e);
			throw (e);
		}

		// 释放锁
		businessLockService.unlock(mutex);
		// 记录日志
		logger.error("load scan end:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
				+ scanRecord.getSerialNo());
		// 成功
		return TransferPDADictConstants.SUCCESS;
	}
	
	private <E> List<E> copyTo(List<?> source, Class<E> destinationClass) throws IllegalAccessException, InstantiationException{
	    if (source.size()==0) return Collections.emptyList();  
	    List<E> res = new ArrayList<E>(source.size());  
	    for (Object o : source) {  
	        E e = destinationClass.newInstance();  
	        BeanUtils.copyProperties(o, e);  
	        res.add(e);
	    }  
	    return res;  
	}  
	
	/**
	 * 获取扫描上传件数
	 */
	@Override
	public int queryScanQty(String taskNo) {
		return loadManagerExceptionDao.queryScanQtyCount(taskNo);
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}

	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	public void setExpressVehiclesService(IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}

	public void setWkLoadTempDao(IWKLoadTempDao wkLoadTempDao) {
		this.wkLoadTempDao = wkLoadTempDao;
	}

	public void setPassInviteApplyService(IPassInviteApplyService passInviteApplyService) {
		this.passInviteApplyService = passInviteApplyService;
	}

	public void setTruckDepartPlanDetailService(ITruckDepartPlanDetailService truckDepartPlanDetailService) {
		this.truckDepartPlanDetailService = truckDepartPlanDetailService;
	}

	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}

	public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
		this.pdaUnloadTaskDao = pdaUnloadTaskDao;
	}

	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	public void setVehicleAgencyCompanyService(IVehicleAgencyCompanyService vehicleAgencyCompanyService) {
		this.vehicleAgencyCompanyService = vehicleAgencyCompanyService;
	}

	public void setLdpAgencyCompanyService(ILdpAgencyCompanyService ldpAgencyCompanyService) {
		this.ldpAgencyCompanyService = ldpAgencyCompanyService;
	}

	public void setPorterService(IPorterService porterService) {
		this.porterService = porterService;
	}

	public void setLoadAndUnloadSquadService(ILoadAndUnloadSquadService loadAndUnloadSquadService) {
		this.loadAndUnloadSquadService = loadAndUnloadSquadService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	public void setHeavyBubbleRatioService(IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setPdaLoadService(IPDALoadService pdaLoadService) {
		this.pdaLoadService = pdaLoadService;
	}

	public void setwKTfrBillService(IWKTfrBillService wKTfrBillService) {
		this.wKTfrBillService = wKTfrBillService;
	}

	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}

	public void setAssignLoadTaskDao(IAssignLoadTaskDao assignLoadTaskDao) {
		this.assignLoadTaskDao = assignLoadTaskDao;
	}

	public void setDeliverBillVaryStatusService(IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setAutoGenerateHandOverBillDao(IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao) {
		this.autoGenerateHandOverBillDao = autoGenerateHandOverBillDao;
	}

	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	public void setExpressPackageDao(IExpressPackageDao expressPackageDao) {
		this.expressPackageDao = expressPackageDao;
	}

	public void setLoadManagerExceptionDao(ILoadManagerExceptionDao loadManagerExceptionDao) {
		this.loadManagerExceptionDao = loadManagerExceptionDao;
	}
}
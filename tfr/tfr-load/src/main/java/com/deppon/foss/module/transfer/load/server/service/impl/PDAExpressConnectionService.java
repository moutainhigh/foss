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
import org.springframework.dao.CannotAcquireLockException;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAcceptPointSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAccessPointService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesChildrenDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupQueryDto;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.AccessPointDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PDAExpressConnectionService implements
		IPDAExpressConnectionService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PDAExpressConnectionService.class);
	/**
	 * 共用service-取编码规则
	 */
	private ITfrCommonService tfrCommonService;
	/**
	 * 员工service
	 */
	private IEmployeeService employeeService;
	/**
	 * 组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 车队service
	 */
	private IMotorcadeService motorcadeService;
	/**
	 * 组织service
	 */
	public IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 装车dao
	 */
	private IPDALoadDao pdaLoadDao;
	
	/**
	 * 车辆service
	 */
	public IVehicleService vehicleService;
	
	/**
	 * 卸车dao
	 */
	public IPDAUnloadTaskDao pdaUnloadTaskDao;
	
	/**
	 * 接驳点基础资料
	 */
	private IAccessPointService accessPointService;
	/**
	 * 接驳点辐射营业部
	 */
	private IAcceptPointSalesDeptService acceptPointSalesDeptService;
	
	/**
	 * 自动生成交接单DAO
	 */
	public IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao;
	
	/**
	 * 快递派送装车dao
	 */
	public IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao;
	
	/**
	 * 接送货订单接口
	 */
	public IPdaDispatchOrderService pdaDispatchOrderService;
	/**
	 * 装卸车共用service
	 */
	public IPDACommonService pdaCommonService;
	
	/**
	 * 库存service
	 */
	public IStockService stockService;
	/**
	 * 月台service
	 * */
	public IPlatformService platformService;
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setPdaUnloadTaskDao(IPDAUnloadTaskDao pdaUnloadTaskDao) {
		this.pdaUnloadTaskDao = pdaUnloadTaskDao;
	}

	public void setAccessPointService(IAccessPointService accessPointService) {
		this.accessPointService = accessPointService;
	}

	public void setAcceptPointSalesDeptService(
			IAcceptPointSalesDeptService acceptPointSalesDeptService) {
		this.acceptPointSalesDeptService = acceptPointSalesDeptService;
	}

	public void setAutoGenerateHandOverBillDao(
			IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao) {
		this.autoGenerateHandOverBillDao = autoGenerateHandOverBillDao;
	}

	public void setPdaExpressDeliverLoadDao(
			IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao) {
		this.pdaExpressDeliverLoadDao = pdaExpressDeliverLoadDao;
	}

	public void setPdaDispatchOrderService(
			IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}

	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}

	/**
	 * 
	 * <p>获取未完成司机装车任务</p> 
	 * @author alfred
	 * @date 2015-5-18 下午4:48:47
	 * @param condition
	 * @return 
	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryUnfinishDriverLoadTask(
			QueryAssignedLoadTaskEntity condition) {
		List<PDAAssignLoadTaskEntity> loadList = new ArrayList<PDAAssignLoadTaskEntity>();
		Date currentTime = new Date();
		if(condition.getQueryTransportTimeBegin() == null || condition.getQueryTransportTimeEnd() == null){
			condition.setQueryTransportTimeBegin(new Date(currentTime.getTime()-LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
			condition.setQueryTransportTimeEnd(currentTime);
		}
		condition.setTaskType(LoadConstants.LOAD_TASK_TYPE_EXPRESS_DRIVER_LOAD);
		List<String> states = new ArrayList<String>();
		states.add(LoadConstants.LOAD_TASK_STATE_LOADING);
		states.add(LoadConstants.LOAD_TASK_STATE_FINISHEN);
		condition.setStates(states);
		loadList = pdaExpressDeliverLoadDao.queryUnfinishDriverLoadTask(condition);
		return loadList;
	}

	/**
	 * 
	 * <p>新建司机装车任务</p>
	 * 收货环节，司机输入快递员工号，下拉运单明细 
	 * @author alfred
	 * @date 2015-4-15 下午4:12:19
	 * @param loadTask
	 * @return 
	 */
	@Override
	public String createDriverTask(LoadTaskDto task) {
		LOGGER.error("建立司机装车任务开始"+" 部门:"+task.getCreateOrgCode()+" "+task.getOperatorCode()+" "
					+task.getVehicleNo()+" "+task.getDeviceNo());
		PDATaskEntity pdaEntity = new PDATaskEntity();
		Date loadBeginTime = new Date();
		String taskNo = task.getTaskNo();
		if(StringUtils.isNotBlank(taskNo)){
			pdaEntity.setBeCreator(FossConstants.NO);
		}else{//如果任务编号为空，则新建装车任务
			LOGGER.error("建立司机装车任务开始"+task.getVehicleNo());
			pdaEntity.setBeCreator(FossConstants.YES);
			//任务id
			String taskId = UUIDUtils.getUUID();
			LoadTaskEntity loadTaskEntity = new LoadTaskEntity();
			EmployeeEntity emp = new EmployeeEntity();
			//获取员工信息
			emp = employeeService.queryEmployeeByEmpCode(task.getTallyerCode());
			//出发部门
			String departOrgCode = emp.getOrgCode();
			//交接部门
			String destOrgCode = getTransferCenter(task.getCreateOrgCode());
			//到达部门code list
			List<LoadDestOrgEntity> loadDestOrgs = new ArrayList<LoadDestOrgEntity>();
			if(StringUtils.isBlank(destOrgCode)){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}else{
				LoadDestOrgEntity loadDestOrg = new LoadDestOrgEntity();
				OrgAdministrativeInfoEntity destOrg = orgAdministrativeInfoService.
						queryOrgAdministrativeInfoByCode(destOrgCode);
				if(destOrg != null){
					//装车交接部门
					loadTaskEntity.setDestOrgNames(destOrg.getName());
				}
				loadDestOrg.setDestOrgCode(destOrgCode);
				loadDestOrg.setBeCreateHandOver(FossConstants.NO);
				loadDestOrg.setLoadTaskId(taskId);
				loadDestOrg.setId(UUIDUtils.getUUID());
				loadDestOrg.setCreateDate(new Date());
				loadDestOrgs.add(loadDestOrg);
			}
			
			if(CollectionUtils.isNotEmpty(loadDestOrgs)){
				pdaLoadDao.insertTransferLoadDestOrg(loadDestOrgs);
			}
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//任务号
			taskNo  = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.SCZC,departOrgCode);
			
			//查询任务号 是否已生成 过218427 foss-hongwy 2015.12.29
			String taskNos = pdaLoadDao.queryTaskNos(taskNo);
			//如果重复了 再次调用生成任务号方法 218427 foss-hongwy
			while(StringUtils.isNotBlank(taskNos)){
				taskNo = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.SCZC, departOrgCode);
				taskNos = pdaLoadDao.queryTaskNos(taskNo);
			}
			
			//是否生成差异报告
			loadTaskEntity.setBeCreateGapRep(FossConstants.NO);
			//货物类型
			loadTaskEntity.setGoodsType(task.getGoodsType());
			//装车任务id
			loadTaskEntity.setId(taskId);
			//装车任务开始时间
			loadTaskEntity.setLoadStartTime(df.format(loadBeginTime));
			//装车方式
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			//装车人所在部门code
			loadTaskEntity.setOrigOrgCode(departOrgCode);
			//装车人所在部门名称
			loadTaskEntity.setOrigOrgName(emp.getEmpName());
			//装车状态
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			//装车任务号
			loadTaskEntity.setTaskNo(taskNo);
			//装车方式
			loadTaskEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_EXPRESS_DRIVER_LOAD);
			//车牌号
			loadTaskEntity.setVehicleNo(task.getVehicleNo());
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			
			//插入参与人信息
			List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
			//创建人信息
			LoaderParticipationEntity loader = new LoaderParticipationEntity();
			loader.setBeCreator(FossConstants.YES);
			loader.setId(UUIDUtils.getUUID());
			loader.setJoinTime(loadBeginTime);
			loader.setLoaderCode(task.getOperatorCode());
			loader.setTaskId(taskId);
			loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_EXPRESS_DRIVER_LOAD);
			EmployeeEntity driver = employeeService.queryEmployeeByEmpCode(loader.getLoaderCode());
			if(driver != null){
				loader.setLoaderName(driver.getEmpName());
				loader.setLoadOrgCode(driver.getOrgCode());
				OrgAdministrativeInfoEntity driverOrg = orgAdministrativeInfoService.
						queryOrgAdministrativeInfoByCode(driver.getOrgCode());
				if(driverOrg != null){
					loader.setLoadOrgName(driverOrg.getName());
				}
			}else{
				LOGGER.error("理货员不存在"+loader.getLoaderCode());
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
			}
			loaders.add(loader);
			//快递员信息
			LoaderParticipationEntity tayller = new LoaderParticipationEntity();
			tayller.setBeCreator("S");
			tayller.setId(UUIDUtils.getUUID());
			tayller.setJoinTime(loadBeginTime);
			tayller.setLoaderCode(task.getTallyerCode());
			tayller.setTaskId(taskId);
			tayller.setTaskType(LoadConstants.LOADER_PARTICIPATION_EXPRESS_DRIVER_LOAD);
			tayller.setLoaderName(emp.getEmpName());
			tayller.setLoadOrgCode(emp.getOrgCode());
			tayller.setLoadOrgName(emp.getOrgName());
			loaders.add(tayller);
			pdaLoadDao.insertTransferLoaderParticipation(loaders);
		}
		//插入装车PDA
		pdaEntity.setTaskNo(taskNo);
		pdaEntity.setDeviceNo(task.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_EXPRESS_DRIVER_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		return taskNo;
	}

	

	/**
	 * 
	 * 获取顶级车队，再获取顶级车队对应的驻地外场</p> 
	 * @author alfred
	 * @date 2015-4-17 上午9:54:26
	 * @param code
	 * @return
	 * @see
	 */
	private String getTransferCenter(String code){
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoComplexService.getTopFleetByCode(code);
		if(orgInfo != null){
			MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(orgInfo.getCode());
			if(motorcade != null){
				return motorcade.getTransferCenter();
			}
		}
		return null;
	}

	/**
	 * 
	 * <p>提交司机装车任务</p> 
	 * @author alfred
	 * @date 2015-5-7 上午9:16:46
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @return 
	 */
	@Override
	public String submitDriverLoadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode) {
		LOGGER.error("提交二程接驳司机装车任务开始"+taskNo+" "+loaderCode);
		LoadTaskEntity loadTask = null;
		try{
			loadTask = pdaLoadDao.queryLoadTaskByTaskNoForUpdate(taskNo);
		}catch(CannotAcquireLockException e){
			throw new TfrBusinessException("任务提交中请稍后再试");
		}
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			//更新pda任务
			Map<String,Object> parameter = new HashMap<String,Object>();
			//任务号
			parameter.put("taskNo", taskNo);
			//设备号
			parameter.put("deviceNo", deviceNo);
			//离开时间
			parameter.put("leaveTime", new Date());
			pdaLoadDao.updatePDATaskEntity(parameter);
			
			
			//修改装车任务中理货员
			Map<String,Object> param = new HashMap<String,Object>();
			//任务id
			param.put("id", loadTask.getId());
			//结束时间
			param.put("endTime", new Date());
			//提交装车任务时更新理货员
			pdaLoadDao.updateLoaderParticipationByLoadTask(param);

			//更新装车任务
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//Sets the 装车结束时间
			loadTask.setLoadEndTime(df.format(new Date()));
			//状态
			loadTask.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
			pdaLoadDao.updateLoadTask(loadTask);
			//生成交接单编号
			String handOverNo=tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.JBJJD);
			if(StringUtils.isNotBlank(handOverNo)){
				handOverNo=handOverNo+"p";
			}else{
				throw new TfrBusinessException("生成交接单编号出错");
			}
			//生成交接单
			autoGenerateHandOverBillDao.autoCreatePackHandoverbill(taskNo, handOverNo);
			
			//更改待补录标识
			List<String> waybillNos = new ArrayList<String>();
			//查询装车任务中的运单明细
			List<LoadWaybillDetailEntity> waybillEntitys = pdaExpressDeliverLoadDao.queryLoadWayBillQty(loadTask.getId());
			if(CollectionUtils.isNotEmpty(waybillEntitys)){
				for(LoadWaybillDetailEntity entity:waybillEntitys){
					waybillNos.add(entity.getWaybillNo());
				}
				//校验该任务中运单是否已经完成接货
				ExpressFeederPickupQueryDto queryDto = new ExpressFeederPickupQueryDto();
				queryDto.setWaybillNoList(waybillNos);
				Long s = pdaDispatchOrderService.isExistPickUpDoneByWaybillNoList(queryDto);
				if(s>0){
					//抛出异常
					throw new TfrBusinessException("该快递员已完成接货，无法提交任务");
				}
				// 接送货提供接口，更改待补录运单中的是否交接标识，避免重复生成交接单
				pdaDispatchOrderService.updatePickupDoneExpressFeederPickupDetail(waybillNos);
				
			}
		}else{
			//抛出异常
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		//返回成功
		LOGGER.error("提交二程接驳司机装车任务结束"+loadTask.getTaskNo());
		return TransferPDADictConstants.SUCCESS;
	}
	
	/**
	 * 
	 * <p>取消司机装车任务</p> 
	 * @author alfred
	 * @date 2015-8-4 下午3:42:22
	 * @param taskNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService#cancelDriverLoadTask(java.lang.String)
	 */
	@Override
	public String cancelDriverLoadTask(String taskNo) {
		LOGGER.error("取消司机装车任务开始"+taskNo);
		//查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		//状态为进行中
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			loadTask.setState(LoadConstants.LOAD_TASK_STATE_CANCELED);
			pdaLoadDao.updateLoadTask(loadTask);
		}else if(loadTask != null && LoadConstants.LOAD_TASK_STATE_CANCELED.equals(loadTask.getState())){
			return TransferPDADictConstants.SUCCESS;
		}else{
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		LOGGER.error("取消司机装车任务结束"+taskNo);
		return TransferPDADictConstants.SUCCESS;
	}
	
	/**
	 * 
	 * <p>司机点击完成装车，将当前任务列表中所有单据打包生成一条卸车类型为“快递接驳卸车”的任务单据</p> 
	 * @author alfred
	 * @date 2015-4-17 上午11:05:43
	 * @param taskNo
	 * @param vehicleNo
	 * @return 
	 */
	@Override
	public String finishLoadTask(List<String> taskNos, String vehicleNo) {
		if(CollectionUtils.isNotEmpty(taskNos)){
			//判定交接单是否生成
			int count = pdaLoadDao.beExistPickHandover(taskNos);
			if(count<=0){
				for(String taskNo:taskNos){
					LoadTaskEntity loadTask = new LoadTaskEntity();
					loadTask.setState(LoadConstants.LOAD_TASK_STATE_SUBMITED);
					loadTask.setTaskNo(taskNo);
					pdaLoadDao.updateLoadTask(loadTask);
				}
				return TransferPDADictConstants.SUCCESS;
//				throw new TfrBusinessException("未进行装车扫描的任务不能完成任务");
			}
			//生成交接单编号
			String handOverNo=tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.JBJJD);
			if(StringUtils.isNotBlank(handOverNo)){
				handOverNo=handOverNo+"p";
			}else{
				throw new TfrBusinessException("生成交接单编号出错");
			}
			//任务号s
			StringBuilder loadTaskNos = new StringBuilder();
			for(String taskNo:taskNos){
				//添加任务号
				loadTaskNos.append(taskNo);
				loadTaskNos.append("/");
				
				//状态--用来标识任务已经合并完成
				LoadTaskEntity loadTask = new LoadTaskEntity();
				loadTask.setState(LoadConstants.LOAD_TASK_STATE_SUBMITED);
				loadTask.setTaskNo(taskNo);
				pdaLoadDao.updateLoadTask(loadTask);
			}
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("handOverNo", handOverNo);
			condition.put("taskNos", taskNos);
			condition.put("handOverType",LoadConstants.HANDOVERBILL_TYPE);
			condition.put("loadTaskNos", loadTaskNos.toString());
			pdaLoadDao.addPickHandoverBill(condition);
			
			
		}
		return TransferPDADictConstants.SUCCESS;
	}

	/**
	 * 
	 * <p>获取进行中、未开始的接驳装车任务</p> 
	 * @author alfred
	 * @date 2015-5-15 下午3:16:22
	 * @param condition
	 * @return 
	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryUnfinishLoadTask(
			QueryAssignedLoadTaskEntity condition) {
		List<PDAAssignLoadTaskEntity> loadList = new ArrayList<PDAAssignLoadTaskEntity>();
		Date currentTime = new Date();
		if(condition.getQueryTransportTimeBegin() == null || condition.getQueryTransportTimeEnd() == null){
			condition.setQueryTransportTimeBegin(new Date(currentTime.getTime()-LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
			condition.setQueryTransportTimeEnd(currentTime);
		}
		loadList = pdaExpressDeliverLoadDao.queryUnfinishedLoadTask(condition);
		return loadList;
	}
	
	/**
	 * 
	 * <p>最终外场理货员建立到接驳点的接驳装车任务</p>
	 * 根据外场编码找到所辐射的接驳点 
	 * @author alfred
	 * @date 2015-5-5 下午4:11:21
	 * @param transferCode
	 * @return 
	 */
	@Override
	public List<AccessPointDto> queryAccessPointsByTransferCode(
			String transferCode) {
		List<AccessPointDto> dtos = new ArrayList<AccessPointDto>();
		AccessPointEntity entity = new AccessPointEntity();
		entity.setTransferCode(transferCode);
		List<AccessPointEntity> entitys = accessPointService.queryAccessPointsByTransferCode(entity);
		if(CollectionUtils.isNotEmpty(entitys)){
			for(AccessPointEntity accessEntity:entitys){
				AccessPointDto dto = new AccessPointDto();
			    dto.setName(accessEntity.getName());
			    dto.setCode(accessEntity.getCode());
			    dtos.add(dto);
			}
			return dtos;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 
	 * <p>最终外场理货员建立到接驳点的接驳装车任务</p> 
	 * 理货员输入本外场辐射接驳点，在通过接驳点找到对应的营业部货量
	 * @author alfred
	 * @date 2015-4-15 下午4:12:23
	 * @param loadTask
	 * @return 
	 */
	@Override
	public LoadTaskResultDto createConnectionTask(LoadTaskDto loadTask) {
		VehicleAssociationDto vehicleDto = null;
		if(StringUtils.isNotBlank(loadTask.getVehicleNo())){
			try{
				vehicleDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(loadTask.getVehicleNo());
			}catch(Exception e){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
			}
		}
		if(vehicleDto == null){
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
		}
		PDATaskEntity pdaEntity = new PDATaskEntity();
		Date loadBeginTime = new Date();
		String loadTaskNo;
		LoadTaskEntity loadTaskEntity;
		//如果任务编号不为空
		if(StringUtils.isNotBlank(loadTask.getTaskNo())){
			loadTaskNo = loadTask.getTaskNo();
			//查询装车任务
			loadTaskEntity = pdaLoadDao.queryLoadTaskByTaskNo(loadTaskNo);
			//只有装车状态为装车中的任务可以下拉装车清单
			if(loadTaskEntity != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTaskEntity.getState())){
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
			//交接部门名称s
			StringBuilder destOrgNames = new StringBuilder();
			//到达部门code list
			List<LoadDestOrgEntity> loadDestOrgs = new ArrayList<LoadDestOrgEntity>();
			
			//是否建立任务PDA为是
			pdaEntity.setBeCreator(FossConstants.YES);
			loadTaskEntity = new LoadTaskEntity();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String loadTaskId = UUIDUtils.getUUID();
			//生成任务编号
			loadTaskNo  = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.SCZC,loadTask.getCreateOrgCode());
			//出发部门
			OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService.
					queryOrgAdministrativeInfoByCode(loadTask.getCreateOrgCode());
			//snoar 218427 为空判断
			if(origOrg==null){
				throw new TfrBusinessException("origOrg为空");
			}
			//装车部门为外场
			if(origOrg != null){
				//查询对应装车部门的上级部门
				origOrg = pdaCommonService.getCurrentOutfieldCode(loadTask.getCreateOrgCode());
			}
			
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
		
			//交接部门为空，不能建立装车任务
			if(CollectionUtils.isEmpty(loadTask.getDestOrgCodes())){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}else{
				// 综合提供方法查询接驳对应营业部code
				List<AcceptPointSalesChildrenDeptEntity> saleDepartEntitys =  acceptPointSalesDeptService.
						queryChildrenDeptByAcceptSmallCode(loadTask.getDestOrgCodes().get(0));
				//插入到达部门
				for(AcceptPointSalesChildrenDeptEntity saleDepartEntity :saleDepartEntitys){
					LoadDestOrgEntity loadDestOrg = new LoadDestOrgEntity();
					loadDestOrg.setDestOrgCode(saleDepartEntity.getSalesDepartmentCode());
					loadDestOrg.setBeCreateHandOver(FossConstants.NO);
					loadDestOrg.setLoadTaskId(loadTaskId);
					loadDestOrg.setId(UUIDUtils.getUUID());
					loadDestOrg.setCreateDate(new Date());
					loadDestOrgs.add(loadDestOrg);
					
					destOrgNames.append(saleDepartEntity.getSalesDepartmentName());
					destOrgNames.append(" ");
				}
			}
			if(CollectionUtils.isNotEmpty(loadDestOrgs)){
				pdaLoadDao.insertTransferLoadDestOrg(loadDestOrgs);
				//装车交接部门
				loadTaskEntity.setDestOrgNames(destOrgNames.toString().trim());
			}
			
			//是否生成差异报告
			loadTaskEntity.setBeCreateGapRep(FossConstants.NO);
			//货物类型
			loadTaskEntity.setGoodsType(loadTask.getGoodsType());
			//装车任务id
			loadTaskEntity.setId(loadTaskId);
			//装车任务开始时间
			loadTaskEntity.setLoadStartTime(df.format(loadBeginTime));
			//装车方式
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			//装车人所在部门code
			loadTaskEntity.setOrigOrgCode(origOrg.getCode());
			//装车人所在部门名称
			loadTaskEntity.setOrigOrgName(origOrg.getName());
			//装车状态
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING); 
			//装车任务号
			loadTaskEntity.setTaskNo(loadTaskNo);
			//装车方式
			loadTaskEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_EXPRESS_CONNECTION_LOAD);
			//车牌号
			loadTaskEntity.setVehicleNo(loadTask.getVehicleNo());
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			
			//插入创建人信息
			List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
			LoaderParticipationEntity loader = new LoaderParticipationEntity();
			loader.setBeCreator(FossConstants.YES);
			loader.setId(UUIDUtils.getUUID());
			loader.setJoinTime(loadBeginTime);
			loader.setLoaderCode(loadTask.getOperatorCode());
			loader.setTaskId(loadTaskId);
			loader.setTaskType(LoadConstants.LOADER_PARTICIPATION__EXPRESS_CONNECTION_LOAD);
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loader.getLoaderCode());
			if(emp != null){
				loader.setLoaderName(emp.getEmpName());
				loader.setLoadOrgCode(emp.getOrgCode());
				OrgAdministrativeInfoEntity empOrg = orgAdministrativeInfoService.
						queryOrgAdministrativeInfoByCode(emp.getOrgCode());
				if(empOrg != null){
					loader.setLoadOrgName(empOrg.getName());
				}
			}else{
				LOGGER.error("理货员不存在"+loader.getLoaderCode());
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
			}
			loaders.add(loader);
			pdaLoadDao.insertTransferLoaderParticipation(loaders);
			
			
		}
		//插入装车PDA
		pdaEntity.setTaskNo(loadTaskNo);
		pdaEntity.setDeviceNo(loadTask.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION__EXPRESS_CONNECTION_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		LOGGER.error("建立快递接货装车任务结束"+" 部门:"+loadTask.getCreateOrgCode()+" "+loadTask.getOperatorCode()+" "
				+loadTask.getVehicleNo()+" "+loadTask.getDeviceNo());
		return  this.createLoadTaskResult(loadTaskNo,loadTaskEntity.getOrigOrgCode(), vehicleDto);
	}
	
	public LoadTaskResultDto createLoadTaskResult(String taskNo,String orgCode,VehicleAssociationDto vehicleDto){
		LoadTaskResultDto loadTaskResultDto = new LoadTaskResultDto();
		//车辆额定载重、额定净空
		if(vehicleDto != null){
			double vehicleDeadWeight = 0;
			if(vehicleDto.getVehicleDeadLoad() != null){
				vehicleDeadWeight = vehicleDto.getVehicleDeadLoad().doubleValue();
			}
			double vehicleDeadVolume = 0;
			if(vehicleDto.getVehicleSelfVolume() != null){
				vehicleDeadVolume = vehicleDto.getVehicleSelfVolume().doubleValue();
			}
			loadTaskResultDto.setVehicleDeadVolume(vehicleDeadVolume);
			loadTaskResultDto.setVehicleDeadWeight(vehicleDeadWeight);
			loadTaskResultDto.setVehicleLengthName(vehicleDto.getVehicleLengthName());
			if(vehicleDto.getVehicleUsedTypeName()!=null){
				if(vehicleDto.getVehicleUsedTypeName().equals("长途车")){
					if(StringUtils.isNotBlank(vehicleDto.getVehicleOrganizationCode())){
						loadTaskResultDto.setVehicleLengthName("长途公司车");
					}
				}
			}
		}
		loadTaskResultDto.setTaskNo(taskNo);
		return loadTaskResultDto;
	}
	
	/**
	 * 
	 * <p>理货员提交装车任务，接驳装车任务</p> 
	 * @author alfred
	 * @date 2015-4-21 下午5:18:20
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @return 
	 */
	@Override
	public String submitConnectionLoadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode) {
		LOGGER.error("提交快递接驳装车任务开始"+taskNo+" "+loaderCode);
		LoadTaskEntity loadTask = null;
		try{
			loadTask = pdaLoadDao.queryLoadTaskByTaskNoForUpdate(taskNo);
		}catch(CannotAcquireLockException e){
			throw new TfrBusinessException("任务提交中请稍后再试");
		}
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			//更新pda任务
			Map<String,Object> parameter = new HashMap<String,Object>();
			//任务号
			parameter.put("taskNo", taskNo);
			//设备号
			parameter.put("deviceNo", deviceNo);
			//离开时间
			parameter.put("leaveTime", new Date());
			pdaLoadDao.updatePDATaskEntity(parameter);
			
			//修改装车任务中理货员
			Map<String,Object> param = new HashMap<String,Object>();
			//任务id
			param.put("id", loadTask.getId());
			//结束时间
			param.put("endTime", new Date());
			//提交装车任务时更新理货员
			pdaLoadDao.updateLoaderParticipationByLoadTask(param);
			
			//出库装车扫描流水号
			LOGGER.error("装车出库开始"+loadTask.getTaskNo());
			this.outStockLoadGoods(loadTask.getId(),loaderCode);
			LOGGER.error("装车出库结束"+loadTask.getTaskNo());
			
			//更新装车任务
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//Sets the 装车结束时间
			loadTask.setLoadEndTime(df.format(new Date()));
			//状态
			loadTask.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
			pdaLoadDao.updateLoadTask(loadTask);
			
			//生成交接单编号
			String handOverNo=tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.JBJJD);
			if(StringUtils.isNotBlank(handOverNo)){
				handOverNo=handOverNo+"p";
			}else{
				throw new TfrBusinessException("生成交接单编号出错");
			}
			//生成交接单
			autoGenerateHandOverBillDao.autoCreateConnectionHandover(taskNo, handOverNo);
			
		}else{
			//抛出异常
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		//返回成功
		LOGGER.error("提交快接驳装车任务结束"+loadTask.getTaskNo());
		return TransferPDADictConstants.SUCCESS;
	}

	
	/**
	 * 出库装车货物
	 *
	 *
	 * @param taskId 
	 * @param loaderCode 
	 */
	public void outStockLoadGoods(String taskId,String loaderCode){
		//查询出库货物
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("taskId", taskId);
		condition.put("beLoaded", FossConstants.YES);
		List<LoadTaskSerialNoDto> outStockGoodsList = pdaLoadDao.queryOutStockGoods(condition);
		List<InOutStockEntity> inOutStockList = new ArrayList<InOutStockEntity>();
		//出库货物
		if(CollectionUtils.isNotEmpty(outStockGoodsList)){
			for(LoadTaskSerialNoDto outStockGoods : outStockGoodsList){
				//记录出入库动作信息
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				//设置 运单号
				inOutStockEntity.setWaybillNO(outStockGoods.getWayBillNo());
				//设置 流水号
				inOutStockEntity.setSerialNO(outStockGoods.getSerialNo());
				//装车出库
				inOutStockEntity.setInOutStockType(StockConstants.LOAD_GOODS_OUT_STOCK_TYPE);
				//设置 操作人编号
				inOutStockEntity.setOperatorCode(loaderCode);
				//设置 操作人姓名
				inOutStockEntity.setOperatorName(loaderCode);
				//加入出入库动作信息
				inOutStockList.add(inOutStockEntity);
				//stockService.outStockPC(inOutStockEntity);
				LOGGER.error("装车出库:"+outStockGoods.getWayBillNo()+outStockGoods.getSerialNo()+"/");
			}
			//PC端操作批量出库
			//stockService.outStockBatchPC(inOutStockList);
			
			// PC端操作批量按照类型出库
			stockService.outStockBatchPCByType(inOutStockList, StockConstants.OUT_STOCK_TYPE_LOADING, taskId);
		}
	}

	
	

}

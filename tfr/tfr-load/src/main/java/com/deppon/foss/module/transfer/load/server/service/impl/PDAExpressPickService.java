package com.deppon.foss.module.transfer.load.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;

import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.WayBillNoLocusConstant;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.CreateDeliveryReceiptEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.WaybillInfoEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PDAExpressPickService implements IPDAExpressPickService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PDAExpressPickService.class);
	public IVehicleService vehicleService;
	private IPDALoadDao pdaLoadDao;
	private IPDACommonService pdaCommonService;
	private ITfrCommonService tfrCommonService;
	public IPorterService porterService;
	public IEmployeeService employeeService;
	public ILoadAndUnloadSquadService loadAndUnloadSquadService;
	private IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao;
	public IOrgAdministrativeInfoService orgAdministrativeInfoService;
	public IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao;
	public IWaybillManagerService waybillManagerService;
	public ITruckTaskService truckTaskService;
	
	
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}


	//duhao
	private ITfrJobTodoService tfrJobTodoService;
	private IBillNumService billNumService;
	
	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}
		
	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}


	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}


	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}



	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}


	public void setPorterService(IPorterService porterService) {
		this.porterService = porterService;
	}


	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public void setLoadAndUnloadSquadService(
			ILoadAndUnloadSquadService loadAndUnloadSquadService) {
		this.loadAndUnloadSquadService = loadAndUnloadSquadService;
	}


	public void setPdaExpressDeliverLoadDao(
			IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao) {
		this.pdaExpressDeliverLoadDao = pdaExpressDeliverLoadDao;
	}


	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setAutoGenerateHandOverBillDao(
			IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao) {
		this.autoGenerateHandOverBillDao = autoGenerateHandOverBillDao;
	}


	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}


	/**
	 * 
	 * <p>创建快递接货装车任务</p> 
	 * @author alfred
	 * @date 2015-1-28 下午2:59:48
	 * @param loadTask
	 * @return 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService#createTask(com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto)
	 */
	@Override
	public String createTask(LoadTaskDto task) {
		LOGGER.error("建立快递接货装车任务开始"+" 部门:"+task.getCreateOrgCode()+" "+task.getOperatorCode()+" "+task.getVehicleNo()+" "+task.getDeviceNo());
		VehicleAssociationDto vehicleDto = null;
		
		//车牌号不能为空
		if(StringUtils.isNotBlank(task.getVehicleNo())){
			try{
				vehicleDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(task.getVehicleNo());
			}catch(Exception e){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
			}
		}else{
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
		}
		if(vehicleDto == null){
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_VEHICLE_INVALID); 
		}
		
		PDATaskEntity pdaEntity = new PDATaskEntity();
		Date loadBeginTime = new Date();
		String taskNo = task.getTaskNo();
		if(StringUtils.isNotBlank(taskNo)){
			pdaEntity.setBeCreator(FossConstants.NO);
		}else{//如果任务编号为空，则新建装车任务
			LOGGER.error("建立快递接货装车任务开始"+task.getVehicleNo());
			if(CollectionUtils.isNotEmpty(task.getDestOrgCodes())){
				if(task.getDestOrgCodes().size()>1){
					throw new TfrBusinessException("禁止输入多个交接部门!");
				}
				for(String d : task.getDestOrgCodes()){
					LOGGER.error(d);
				}
			}else{
				throw new TfrBusinessException("交接部门为空,不能建立装车任务!");
			}
			//交接部门名称s
			StringBuilder destOrgNames = new StringBuilder();
			//到达部门code list
			List<LoadDestOrgEntity> loadDestOrgs = new ArrayList<LoadDestOrgEntity>();
			
			OrgAdministrativeInfoEntity origOrg = pdaCommonService.getTopCurrentOutfieldOrSalesDept(task.getCreateOrgCode());
			if(origOrg == null){
				throw new TfrBusinessException("无效部门");
			}
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//taskNo  = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZC,task.getCreateOrgCode());
			taskNo = billNumService.generateLoadTaskNo(task.getCreateOrgCode());
			
			LOGGER.info("任务号:"+taskNo);
			pdaEntity.setBeCreator(FossConstants.YES);
			String taskId = UUIDUtils.getUUID();
			LoadTaskEntity loadTaskEntity = new LoadTaskEntity();
			
			//交接部门为空，不能建立装车任务
			if(CollectionUtils.isEmpty(task.getDestOrgCodes())){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_ORG_MESSAGECODE);
			}else{
				for(String destOrgCode : task.getDestOrgCodes()){
					LoadDestOrgEntity loadDestOrg = new LoadDestOrgEntity();
					OrgAdministrativeInfoEntity destOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destOrgCode);
					if(destOrg != null){
						destOrgNames.append(destOrg.getName());
						destOrgNames.append(" ");
					}
					
					loadDestOrg.setDestOrgCode(destOrgCode);
					loadDestOrg.setBeCreateHandOver(FossConstants.NO);
					loadDestOrg.setLoadTaskId(taskId);
					loadDestOrg.setId(UUIDUtils.getUUID());
					loadDestOrg.setCreateDate(new Date());
					loadDestOrg.setDestOrgName(destOrgNames.toString().trim());
					loadDestOrgs.add(loadDestOrg);
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
			loadTaskEntity.setGoodsType(task.getGoodsType());
			//装车任务id
			loadTaskEntity.setId(taskId);
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
			loadTaskEntity.setTaskNo(taskNo);
			//装车方式
			//修改为短途类型
			loadTaskEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_SHORT_DISTANCE);
			//车牌号
			loadTaskEntity.setVehicleNo(task.getVehicleNo());
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			
			//插入创建人信息
			List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
			LoaderParticipationEntity loader = new LoaderParticipationEntity();
			loader.setBeCreator(FossConstants.YES);
			loader.setId(UUIDUtils.getUUID());
			loader.setJoinTime(loadBeginTime);
			loader.setLoaderCode(task.getOperatorCode());
			loader.setTaskId(taskId);
			loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_EXPRESS_PICK_LOAD);
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loader.getLoaderCode());
			if(emp != null){
				loader.setLoaderName(emp.getEmpName());
				loader.setLoadOrgCode(emp.getOrgCode());
				OrgAdministrativeInfoEntity empOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(emp.getOrgCode());
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
		pdaEntity.setTaskNo(taskNo);
		pdaEntity.setDeviceNo(task.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_EXPRESS_PICK_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		LOGGER.error("建立快递接货装车任务结束"+" 部门:"+task.getCreateOrgCode()+" "+task.getOperatorCode()+" "
		+task.getVehicleNo()+" "+task.getDeviceNo());
		return taskNo;
	}

	/**
	 * 零担电子运单生成交接单生成
	 * @author songjl
	 * @date 2016-5-25 17:32:26
	 * @param deliveryReceiptEntity
	 * @return 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService.createLTLPackHandoverbill(CreateDeliveryReceiptEntity)
	 */
	@Override
	public String createLTLPackHandoverbill(CreateDeliveryReceiptEntity deliveryReceiptEntity) {
		LOGGER.info("零担电子运单生成交接单生成开始 [TaskCode]->"+deliveryReceiptEntity.getTaskCode()
				+" [DriverCode]->"+deliveryReceiptEntity.getDriverCode()
				+" [truckCode]->"+deliveryReceiptEntity.getTruckCode());
		if(deliveryReceiptEntity!=null){
			deliveryReceiptEntity.setHandOverType(WayBillNoLocusConstant.OPERATE_TYPE_CREATE_PDA_BSE_PIC);	// 图片开单
			deliveryReceiptEntity.setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);	// 未分配
			
			//生成交接单（无出发到达）
			String handOverNo = autoGenerateHandOverBillDao.createLTLHandOverBillByTaskNo(deliveryReceiptEntity.getTaskCode());
			deliveryReceiptEntity.setHandOverNo(handOverNo);
			
			Log.info("零担电子运单生成交接单（无出发到达）："+handOverNo);
			
			pdaLoadDao.addLTLHandoverBill(deliveryReceiptEntity);
			
			List<WaybillInfoEntity> waybillListInfo = deliveryReceiptEntity.getWaybillInfoEntity();
			for(WaybillInfoEntity waybillInfo:waybillListInfo){
				waybillInfo.setHandOverNo(handOverNo);
				pdaLoadDao.addLTLHandoverBillDetail(waybillInfo);
				
				int serailNum = waybillInfo.getPieces();
				Log.info(handOverNo+"生成流水号：[Pieces]->"+waybillInfo.getPieces());
				if(serailNum>=LoadConstants.SONAR_NUMBER_10000){
					LOGGER.error(handOverNo+"生成流水号异常serailNum过大=>"+serailNum);
					return TransferPDADictConstants.FAIL;
				}
				for(int i=1; i<=serailNum; i++){
					waybillInfo.setSerailNo(getSerailnoByNum(i+""));
					pdaLoadDao.addLTLHandoverSerialNo(waybillInfo);
				}
			}
		}
		//返回成功
		LOGGER.error("零担电子运单生成交接单生成任务结束");
		return TransferPDADictConstants.SUCCESS;
	}
	
	/**
	 * 根据当前件数生成流水号
	 * @param serialNo
	 * @return
	 */
	private String getSerailnoByNum(String serialNo){
		int j =LoadConstants.SONAR_NUMBER_4 - serialNo.length();
		//字符串拼接改成sbf,311396 sonar优化 2016年12月21日10:46:22
		StringBuffer sbf = new StringBuffer();
		while(j>0){
//			serialNo = "0"+serialNo;
			sbf.append("0");
			j--;
		}
		sbf.append(serialNo);
		return sbf.toString();
	}
	
	/**根据单号查询零担电子运单交接单信息*/
	@Override
	public List<CreateDeliveryReceiptEntity> queryLTLPackHandoverbill(String waybillNo){
		List<CreateDeliveryReceiptEntity> createDeliveryReceiptList=pdaLoadDao.queryLTLPackHandoverbill(waybillNo);
		List<WaybillInfoEntity> waybillInfoEntityList = pdaLoadDao.queryLTLPackHandoverbillDetail(waybillNo);
		//判断查询是否为空
		if(createDeliveryReceiptList!=null){
			for(CreateDeliveryReceiptEntity createDeliveryReceiptEntity :createDeliveryReceiptList)
			createDeliveryReceiptEntity.setWaybillInfoEntity(waybillInfoEntityList);
		}
		return createDeliveryReceiptList;
	}
	
	
	/**
	 * 
	 * <p>提交快递接货装车任务</p> 
	 * @author alfred
	 * @date 2015-1-29 上午10:44:56
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @return 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService#submitLoadTask(java.lang.String, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public String submitLoadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode) {
		LOGGER.error("提交快递接货装车任务开始"+taskNo+" "+loaderCode);
		LoadTaskEntity loadTask = null;
		try{
			loadTask = pdaLoadDao.queryLoadTaskByTaskNoForUpdate(taskNo);
		}catch(CannotAcquireLockException e){
			throw new TfrBusinessException("任务提交中请稍后再试");
		}
		//218427 sonar 为空判断
		if(loadTask==null){
			throw new TfrBusinessException("loadTask为空");
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
			
			//更新装车任务
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//Sets the 装车结束时间
			loadTask.setLoadEndTime(df.format(new Date()));
			//状态
			loadTask.setState(LoadConstants.LOAD_TASK_STATE_FINISHEN);
			pdaLoadDao.updateLoadTask(loadTask);
			String handOverNo=tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.BH,"");
			if(!StringUtils.isBlank(handOverNo)&&handOverNo.length()==LoadConstants.SONAR_NUMBER_13){
				int length=handOverNo.length();
				handOverNo="k"+handOverNo.substring(2, length);
			}else{
				throw new TfrBusinessException("生成交接单编号出错");
			}
			//生成交接单
			//autoGenerateHandOverBillDao.autoCreatePackHandoverbill(taskNo, handOverNo);
			//String handOverBillNo = autoGenerateHandOverBillDao.createHandOverBillByTaskNo(taskNo);
			autoGenerateHandOverBillDao.createHandOverBillByTaskNo(taskNo);
			List<String> handOverBillNos = pdaLoadDao.queryHandOverBillNoByTaskNo(taskNo);
			// 生成任务车辆
			if (CollectionUtils.isNotEmpty(handOverBillNos)) {
				for (String handOverBillNo : handOverBillNos) {
					truckTaskService.createTruckTaskByHandOverBill(handOverBillNo);
				}
			}
			
			//276198-duhao-20151212-增加交接单号参数
			//将装车任务轨迹生成任务，插入到todo表中duhao-276198-20151019
			tfrJobTodoService.addJobTodo(loadTask.getTaskNo(),
					BusinessSceneConstants.BUSINESS_SCENE_TRUCK_STRAIGHT_DEPARTURE,
					new String[]{BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO}, 
					new Date(), 
					loadTask.getCreateUser());
		}
		//返回成功
		LOGGER.error("提交快递接货装车任务结束"+loadTask.getTaskNo());
		return TransferPDADictConstants.SUCCESS;
	}

	/**
	 * 
	 * <p>快递接货装车扫描接口</p> 
	 * @author alfred
	 * @date 2015-1-29 上午10:46:25
	 * @param scanDto 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService#scan(com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto)
	 */
	@Override
	public void scan(LoadScanDetailDto scanRecord) {
		LOGGER.error("快递接货装车扫描开始"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()
				+scanRecord.getType()+scanRecord.getScanTime());
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(scanRecord.getTaskNo());
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			/**
			 * 保存装车扫描
			 */
			LoadWaybillDetailEntity loadWayBill = null;
			LoadSerialNoEntity loadSerialNo = null;
			WaybillEntity wayBill = null;
			double waybillVolume = scanRecord.getVolume();
			double waybillWeight = scanRecord.getWeight();
			if(scanRecord.getVolume() <=0 && scanRecord.getWeight()<=0){
				wayBill = waybillManagerService.queryWaybillBasicByNo(scanRecord.getWayBillNo());
				if(wayBill != null && wayBill.getGoodsVolumeTotal() != null
						&& wayBill.getGoodsWeightTotal() != null&& wayBill.getGoodsQtyTotal() > 0){
					waybillVolume =  wayBill.getGoodsVolumeTotal().doubleValue() / wayBill.getGoodsQtyTotal();
					waybillWeight = wayBill.getGoodsWeightTotal().doubleValue() / wayBill.getGoodsQtyTotal();
				}else{
					waybillVolume=0.0;
					waybillWeight = 0.0;
				}
			}
			//查询运单明细
			LoadWaybillDetailEntity loadTaskWayBillTemp = new LoadWaybillDetailEntity();
			loadTaskWayBillTemp.setLoadTaskId(loadTask.getId());
			loadTaskWayBillTemp.setWaybillNo(scanRecord.getWayBillNo());
			loadWayBill = pdaLoadDao.queryLoadWaybillDetailEntityByWayBillNo(loadTaskWayBillTemp);
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			if(loadWayBill != null){
				//查询是否存在装车流水号
				LoadSerialNoEntity loadSerialNoEntityTemp = new LoadSerialNoEntity();
				loadSerialNoEntityTemp.setLoadWaybillDetailId(loadWayBill.getId());
				loadSerialNoEntityTemp.setSerialNo(scanRecord.getSerialNo());
				loadSerialNo = pdaLoadDao.queryLoadSerialNoEntityBySerialNo(loadSerialNoEntityTemp);
			}
			/**装车运单不存在*/
			if(loadWayBill == null){
				loadWayBill = new LoadWaybillDetailEntity();
				loadWayBill.setBeJoinCar(FossConstants.NO);
				loadWayBill.setId(UUIDUtils.getUUID());
				loadWayBill.setLoadTaskId(loadTask.getId());
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
					loadWayBill.setLoadVolumeTotal(0.0);
					loadWayBill.setLoadWeightTotal(0.0);
					loadWayBill.setLoadQty(0);
					loadWayBill.setScanQty(0);
				}else{
					loadWayBill.setLoadVolumeTotal(waybillVolume);
					loadWayBill.setLoadWeightTotal(waybillWeight);
					loadWayBill.setLoadQty(1);
					loadWayBill.setScanQty(1);
				}
				loadWayBill.setNotes(null);
				loadWayBill.setStockQty(0);
				try {
					loadWayBill.setTaskBeginTime(df.parse(loadTask.getLoadStartTime()));
				} catch (ParseException e) {
					loadWayBill.setTaskBeginTime(new Date());
				}
				loadWayBill.setWaybillNo(scanRecord.getWayBillNo());
				loadWayBill.setOrigOrgCode(loadTask.getOrigOrgCode());
				try {
					pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBill);
				} catch (org.springframework.dao.DuplicateKeyException e) {
					LoadWaybillDetailEntity loadWayBillEntity = pdaLoadDao
							.queryLoadWaybillDetailEntityByWayBillNo(loadWayBill);
					if (loadWayBillEntity != null) {
						loadWayBill.setId(loadWayBillEntity.getId());
						pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWayBill, scanRecord.getSerialNo(), 
								scanRecord.getScanTime());
					} else {
						throw e;
					}
				}
			}else{/**装车运单存在*/
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){//取消扫描
					loadWayBill.setStockQty(0);
					loadWayBill.setLoadVolumeTotal(-waybillVolume);
					loadWayBill.setLoadWeightTotal(-waybillWeight);
					loadWayBill.setLoadQty(-1);
					loadWayBill.setScanQty(-1);
				}else{//扫描
					loadWayBill.setStockQty(0);
					loadWayBill.setLoadVolumeTotal(waybillVolume);
					loadWayBill.setLoadWeightTotal(waybillWeight);
					loadWayBill.setLoadQty(1);
					loadWayBill.setScanQty(1);
				}
				//按扫描时间修改防并发
				pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWayBill, scanRecord.getSerialNo(), 
						scanRecord.getScanTime());
			}
			/**装车流水号不存在*/
			if(loadSerialNo == null){
				loadSerialNo = new LoadSerialNoEntity();
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
					loadSerialNo.setBeLoaded(FossConstants.NO);
				}else{
					loadSerialNo.setBeLoaded(FossConstants.YES);
				}
				loadSerialNo.setCreateTime(new Date());
				loadSerialNo.setDeviceNo(scanRecord.getDeviceNo());
				loadSerialNo.setId(UUIDUtils.getUUID());
				loadSerialNo.setLoadTime(scanRecord.getScanTime());
				loadSerialNo.setLoadWaybillDetailId(loadWayBill.getId());
				loadSerialNo.setOrigOrgCode(loadTask.getOrigOrgCode());
				loadSerialNo.setSerialNo(scanRecord.getSerialNo());
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
					loadSerialNo.setGoodsState(scanRecord.getType());
				}else{
					loadSerialNo.setGoodsState(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);
				}
				loadSerialNo.setScanState(TransferPDADictConstants.SCAN_STATE_SCANED);
				try {
					loadSerialNo.setTaskBeginTime(df.parse(loadTask.getLoadStartTime()));
				} catch (ParseException e) {
					loadSerialNo.setTaskBeginTime(new Date());
				}
				pdaLoadDao.insertLoadSerialNoEntity(loadSerialNo);
			}else{/**装车流水号存在*/
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
					loadSerialNo.setBeLoaded(FossConstants.NO);
				}else{
					loadSerialNo.setBeLoaded(FossConstants.YES);
				}
				loadSerialNo.setLoadTime(scanRecord.getScanTime());
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
					loadSerialNo.setGoodsState(scanRecord.getType());
				}else{
					loadSerialNo.setGoodsState(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);
				}
				loadSerialNo.setScanState(TransferPDADictConstants.SCAN_STATE_SCANED);
				loadSerialNo.setDeviceNo(scanRecord.getDeviceNo());
				//按扫描时间修改防并发
				pdaExpressDeliverLoadDao.updateLoadSerialNoByLoadTime(loadSerialNo);
			}
			LOGGER.error("快递接货装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+
					scanRecord.getType()+scanRecord.getScanTime());
		}else{
			LOGGER.error("快递接货装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+
					scanRecord.getSerialNo()+scanRecord.getType()+scanRecord.getScanTime());
			throw new TfrBusinessException("无效装车任务");
		}
	}


	/**
	 * 
	 * <p>完成接货任务
	 * 为满足PDA新增接货 支持营业部卸车提供给接口</p> 
	 * @author alfred
	 * @date 2015-3-22 下午4:47:20
	 * @param taskID
	 * @param orgCode
	 * @param vehicle 
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService#finishPickupTask(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void finishPickupTask(String taskID, String orgCode, String vehicleNo,String operatorCode) {
		String handOverNo=tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.BH,"");
		if(!StringUtils.isBlank(handOverNo)&&handOverNo.length()==LoadConstants.SONAR_NUMBER_13){
			int length=handOverNo.length();
			handOverNo="k"+handOverNo.substring(2, length);
		}else{
			throw new TfrBusinessException("生成交接单编号出错");
		}
		
		//生成交接单
		autoGenerateHandOverBillDao.autoCreatePKPHandoverbill(taskID, orgCode, vehicleNo, handOverNo, operatorCode);
	}

}

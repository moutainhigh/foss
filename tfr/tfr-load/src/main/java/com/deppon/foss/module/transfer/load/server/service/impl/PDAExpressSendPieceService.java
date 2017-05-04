package com.deppon.foss.module.transfer.load.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;


import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillExpressTaskService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillExpressTaskDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillSendPieceEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressSendPieceService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 派件交接装车任务
 * 用于派送员之间的交接
 */
public class PDAExpressSendPieceService implements IPDAExpressSendPieceService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(PDAExpressDeliverLoadService.class);
	private IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao;
	private IPDALoadDao pdaLoadDao;
	private ITfrCommonService tfrCommonService;
	private IWaybillManagerService waybillManagerService;
	@Resource
	private IProductService productService4Dubbo;
	private IPDACommonService pdaCommonService;
	public IWaybillRfcService waybillRfcService;
    private IDeliverbillExpressTaskService deliverbillExpressTaskService;
    private IEmployeeService employeeService;
    private ILoadAndUnloadSquadService loadAndUnloadSquadService;
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    private IPorterService porterService;

	/**
	 * 
	 * 更改单服务接口
	 * @param waybillRfcService 
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	

	public void setPdaExpressDeliverLoadDao(
			IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao) {
		this.pdaExpressDeliverLoadDao = pdaExpressDeliverLoadDao;
	}
	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

/*	public void setProductService(IProductService productService) {
		this.productService = productService;
	}*/
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}	
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public void setLoadAndUnloadSquadService(
			ILoadAndUnloadSquadService loadAndUnloadSquadService) {
		this.loadAndUnloadSquadService = loadAndUnloadSquadService;
	}


	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setPorterService(IPorterService porterService) {
		this.porterService = porterService;
	}


	public void setDeliverbillExpressTaskService(
			IDeliverbillExpressTaskService deliverbillExpressTaskService) {
		this.deliverbillExpressTaskService = deliverbillExpressTaskService;
	}

	/**
	 * 
	 * <p>创建任务</p> 
	 * @author alfred
	 * @date 2015-5-13 上午10:37:57
	 * @param task
	 * @return 
	 */
	@Override
	public String createTask(ExpressDeliverLoadTaskDto task) {
		LOGGER.error("建立快递派件装车任务开始"+" 部门:"+task.getCreateOrgCode()+" "+task.getOperatorCode()+" "
				+task.getVehicleNo()+" "+task.getDeviceNo());
		PDATaskEntity pdaEntity = new PDATaskEntity();
		Date loadBeginTime = new Date();
		String taskNo = task.getTaskNo();
		if(StringUtils.isNotBlank(taskNo)){
			pdaEntity.setBeCreator(FossConstants.NO);
		}else{
			OrgAdministrativeInfoEntity origOrg = pdaCommonService.getTopCurrentOutfieldOrSalesDept(task.getCreateOrgCode());
			if(origOrg == null){
				throw new TfrBusinessException("无效部门");
			}
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			taskNo  = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.SCZC,task.getCreateOrgCode());
			LOGGER.info("任务号:"+taskNo);
			pdaEntity.setBeCreator(FossConstants.YES);
			String taskId = UUIDUtils.getUUID();
			LoadTaskEntity loadTaskEntity = new LoadTaskEntity();
			loadTaskEntity.setBeCreateGapRep("C");
			loadTaskEntity.setGoodsType(task.getGoodsType());
			loadTaskEntity.setId(taskId);
			loadTaskEntity.setLoaderQty(1);
			loadTaskEntity.setLoadStartTime(df.format(loadBeginTime));
			loadTaskEntity.setLoadWay(UnloadConstants.UNLOAD_TASK_WAY_PDA);
			loadTaskEntity.setOrigOrgCode(origOrg.getCode());
			loadTaskEntity.setOrigOrgName(origOrg.getName());
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			loadTaskEntity.setTaskNo(taskNo);
			loadTaskEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_EXPRESS_SENDPIECE_LOAD);
			loadTaskEntity.setVehicleNo(task.getVehicleNo());
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			this.insertLoadParticipator(taskId, loadBeginTime,task.getOperatorCode(),task.getCourier());
		}
		//插入装车PDA
		pdaEntity.setTaskNo(taskNo);
		pdaEntity.setDeviceNo(task.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_EXPRESS_SENDPIECE_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		LOGGER.error("建立快递派件装车任务结束"+" 部门:"+task.getCreateOrgCode()+" "+task.getOperatorCode()+" "
		+task.getVehicleNo()+" "+task.getDeviceNo());
		return taskNo;
	}

	
	/**
	 * 
	 * <p>插入参与人信息</p> 
	 * courierCode：表示操作人输入的是需交接人员的工号
	 * @author alfred
	 * @date 2015-5-26 上午9:07:37
	 * @param taskId
	 * @param loadBeginTime
	 * @param operatorCode
	 * @param courierCode
	 * @see
	 */
	private void insertLoadParticipator(String taskId,Date loadBeginTime,String operatorCode,String courierCode){
		List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
		LoaderParticipationEntity loader = new LoaderParticipationEntity();
		loader.setBeCreator(FossConstants.YES);
		loader.setId(UUIDUtils.getUUID());
		loader.setJoinTime(loadBeginTime);
		loader.setLoaderCode(operatorCode);
		loader.setTaskId(taskId);
		loader.setTaskType(LoadConstants.LOAD_TASK_TYPE_EXPRESS_SENDPIECE_LOAD);
		//登录人工号
		PorterEntity porter = porterService.queryPorterByEmpCode(loader.getLoaderCode());
		//调用综合接口查询理货员名称、理货员所属装卸车队
		if(porter != null){
			loader.setLoaderName(porter.getEmpName());
			if(StringUtils.isNotBlank(porter.getParentOrgCode())){
				loader.setLoadOrgCode(porter.getParentOrgCode());
				LoadAndUnloadSquadEntity team = loadAndUnloadSquadService.
									queryLoadAndUnloadSquadByCode(porter.getParentOrgCode());
				if(team != null){
					loader.setLoadOrgName(team.getName());
				}
			}
		}else{
			LOGGER.error("查询理货员开始"+loader.getLoaderCode());
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
		}
		loaders.add(loader);
		
		//交接人工号，即下拉时输入的工号
		LoaderParticipationEntity courier = new LoaderParticipationEntity();
		courier.setBeCreator("S");
		courier.setId(UUIDUtils.getUUID());
		courier.setJoinTime(loadBeginTime);
		courier.setLoaderCode(courierCode);
		courier.setTaskId(taskId);
		courier.setTaskType(LoadConstants.LOAD_TASK_TYPE_EXPRESS_SENDPIECE_LOAD);
		LOGGER.error("查询接送货员开始"+courier.getLoaderCode());
		porter = porterService.queryPorterByEmpCode(courier.getLoaderCode());
		//调用综合接口查询理货员名称、理货员所属装卸车队
		if(porter != null){
			courier.setLoaderName(porter.getEmpName());
			if(StringUtils.isNotBlank(porter.getParentOrgCode())){
				courier.setLoadOrgCode(porter.getParentOrgCode());
				LoadAndUnloadSquadEntity team = loadAndUnloadSquadService.
									queryLoadAndUnloadSquadByCode(porter.getParentOrgCode());
				if(team != null){
					courier.setLoadOrgName(team.getName());
				}
			}
		}else{
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(courier.getLoaderCode());
			if(emp != null){
				courier.setLoaderName(emp.getEmpName());
				courier.setLoadOrgCode(emp.getOrgCode());
				OrgAdministrativeInfoEntity empOrg = orgAdministrativeInfoService.
										queryOrgAdministrativeInfoByCode(emp.getOrgCode());
				if(empOrg != null){
					courier.setLoadOrgName(empOrg.getName());
				}
			}else{
				LOGGER.error("接送货员不存在"+courier.getLoaderCode());
				throw new TfrBusinessException("无效接送货员");
			}
		}
		loaders.add(courier);
		pdaLoadDao.insertTransferLoaderParticipation(loaders);
	}
	
	/**
	 * 
	 * <p>扫描任务</p> 
	 * @author alfred
	 * @date 2015-5-13 上午10:38:00
	 * @param scanDto 
	 */
	@Override
	public void scan(ExpressDeliverScanDto scanRecord) {
		LOGGER.error("装车扫描开始"+scanRecord.getTaskNo()+" "+scanRecord.getWayBillNo()+" "+
				scanRecord.getSerialNo()+" "+scanRecord.getType()+" "+scanRecord.getScanTime());
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(scanRecord.getTaskNo());
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			/**
			 * 货物校验
			 */
			WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(scanRecord.getWayBillNo());
			if(wayBill == null){
				LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("该运单不存在");
			}
			if(!LoadConstants.PRODUCT_CODE_PACKAGE.equals(wayBill.getProductCode())&&!LoadConstants.PRODUCT_CODE_RCP.equals(wayBill.getProductCode())){
				LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("非快递货");
			}
			
			/**
			 * 保存装车扫描
			 */
			LoadWaybillDetailEntity loadWayBill = null;
			LoadSerialNoEntity loadSerialNo = new LoadSerialNoEntity();
			//流水号基本信息
			loadSerialNo.setCreateTime(new Date());
			loadSerialNo.setDeviceNo(scanRecord.getDeviceNo());
			loadSerialNo.setGoodsState(scanRecord.getType());
			loadSerialNo.setLoadTime(scanRecord.getScanTime());
			loadSerialNo.setOrigOrgCode(loadTask.getOrigOrgCode());
			try {
				loadSerialNo.setTaskBeginTime(df.parse(loadTask.getLoadStartTime()));
			} catch (ParseException e) {
				loadSerialNo.setTaskBeginTime(new Date());
			}
		
			loadSerialNo.setScanState(scanRecord.getScanState());
			loadSerialNo.setSerialNo(scanRecord.getSerialNo());
			//查询运单明细
			LoadWaybillDetailEntity loadTaskWayBillTemp = new LoadWaybillDetailEntity();
			loadTaskWayBillTemp.setLoadTaskId(loadTask.getId());
			loadTaskWayBillTemp.setWaybillNo(scanRecord.getWayBillNo());
			loadWayBill = pdaLoadDao.queryLoadWaybillDetailEntityByWayBillNo(loadTaskWayBillTemp);
			
			if(loadWayBill != null){
				//查询是否存在装车流水号
				LoadSerialNoEntity loadSerialNoEntityTemp = new LoadSerialNoEntity();
				loadSerialNoEntityTemp.setLoadWaybillDetailId(loadWayBill.getId());
				loadSerialNoEntityTemp.setSerialNo(scanRecord.getSerialNo());
				LoadSerialNoEntity updateloadSerialNo = pdaLoadDao.queryLoadSerialNoEntityBySerialNo(loadSerialNoEntityTemp);
				//更新装车运单明细
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){//取消扫描
					//派件装车中，库存字段保存到达联件数（也就是该单可排单件数）
					loadWayBill.setStockQty(scanRecord.getArrivePieces());
					loadWayBill.setLoadVolumeTotal(-wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadWeightTotal(-wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadQty(-1);
					loadWayBill.setScanQty(-1);
				}else{//扫描
					//派件装车中，库存字段保存到达联件数（也就是该单可排单件数）
					loadWayBill.setStockQty(scanRecord.getArrivePieces());
					loadWayBill.setLoadVolumeTotal(wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadWeightTotal(wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadQty(1);
					loadWayBill.setScanQty(1);
				}
				//按扫描时间修改防并发
				pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWayBill, scanRecord.getSerialNo(), 
						scanRecord.getScanTime());
				
				//判定流水号信息是否存在
				if(updateloadSerialNo != null){
					if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
						updateloadSerialNo.setBeLoaded(FossConstants.NO);
					}else{
						updateloadSerialNo.setBeLoaded(FossConstants.YES);
					}
					updateloadSerialNo.setDeviceNo(scanRecord.getDeviceNo());
					updateloadSerialNo.setScanState(scanRecord.getScanState());
					// 扫描状态
					updateloadSerialNo.setLoadTime(scanRecord.getScanTime());
					//按扫描时间修改防并发
					pdaExpressDeliverLoadDao.updateLoadSerialNoByLoadTime(updateloadSerialNo);
					
				}else{/**装车流水号存在*/
					if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
						loadSerialNo.setBeLoaded(FossConstants.NO);
					}else{
						loadSerialNo.setBeLoaded(FossConstants.YES);
					}
					loadSerialNo.setId(UUIDUtils.getUUID());
					loadSerialNo.setLoadWaybillDetailId(loadWayBill.getId());
					pdaLoadDao.insertLoadSerialNoEntity(loadSerialNo);
				}
			}else{/**装车运单不存在*/
				loadWayBill = new LoadWaybillDetailEntity();
				loadWayBill.setBeJoinCar(FossConstants.NO);
				loadWayBill.setGoodsName(wayBill.getGoodsName());
				String waybillDetailid = UUIDUtils.getUUID();
				loadWayBill.setId(waybillDetailid);
				loadWayBill.setLoadTaskId(loadTask.getId());
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
					loadWayBill.setLoadVolumeTotal(0.0);
					loadWayBill.setLoadWeightTotal(0.0);
					loadWayBill.setLoadQty(0);
					loadWayBill.setScanQty(0);
				}else{
					loadWayBill.setLoadVolumeTotal(wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadWeightTotal(wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadQty(1);
					if(TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())){
						loadWayBill.setScanQty(1);
					}else{
						loadWayBill.setScanQty(0);
					}
				}
				loadWayBill.setNotes(null);
				loadWayBill.setPack(wayBill.getGoodsPackage());
				loadWayBill.setReachOrgName(wayBill.getCustomerPickupOrgName());
				loadWayBill.setReceiveOrgName(wayBill.getReceiveOrgName());
				//派件装车中，库存字段保存到达联件数（也就是该单可排单件数）
				loadWayBill.setStockQty(scanRecord.getArrivePieces());
				try {
					loadWayBill.setTaskBeginTime(df.parse(loadTask.getLoadStartTime()));
				} catch (ParseException e) {
					loadWayBill.setTaskBeginTime(new Date());
				}
				ProductEntity product = productService4Dubbo.getProductByCache(wayBill.getProductCode(), null);
				loadWayBill.setTransportType(product.getName());
				loadWayBill.setWaybillGoodsQty(wayBill.getGoodsQtyTotal());
				loadWayBill.setWaybillNo(wayBill.getWaybillNo());
				loadWayBill.setOrigOrgCode(loadTask.getOrigOrgCode());
				try {
					pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBill);
					/**插入运单明细与派送单关系**/
					LoadWaybillSendPieceEntity sendPieceEntity = new LoadWaybillSendPieceEntity();
					sendPieceEntity.setId(UUIDUtils.getUUID());
					sendPieceEntity.setLoadWaybillDetailId(waybillDetailid);
					sendPieceEntity.setWaybillNo(wayBill.getWaybillNo());
					sendPieceEntity.setDeliverNo(scanRecord.getDeliverNo());
					sendPieceEntity.setTaskNo(scanRecord.getTaskNo());
					sendPieceEntity.setCreateDate(new Date());
					pdaExpressDeliverLoadDao.insertLoadSendPieceEntity(sendPieceEntity);
					
				} catch (Exception e) {
					LoadWaybillDetailEntity loadWayBillEntity = pdaLoadDao
							.queryLoadWaybillDetailEntityByWayBillNo(loadWayBill);
					if (loadWayBillEntity != null) {
						loadWayBill.setId(loadWayBillEntity.getId());
						waybillDetailid = loadWayBillEntity.getId();
						pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWayBill, scanRecord.getSerialNo(), 
								scanRecord.getScanTime());
					}
				}
				// 插入装车流水号
				loadSerialNo.setLoadWaybillDetailId(waybillDetailid);
				loadSerialNo.setId(UUIDUtils.getUUID());
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){
					loadSerialNo.setBeLoaded(FossConstants.NO);
				}else{
					loadSerialNo.setBeLoaded(FossConstants.YES);
				}
				pdaLoadDao.insertLoadSerialNoEntity(loadSerialNo);
			}
			LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+
					scanRecord.getType()+scanRecord.getScanTime());
		}else{
			LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+
					scanRecord.getType()+scanRecord.getScanTime());
			throw new TfrBusinessException("无效装车任务");
		}
	}

	/**
	 * 
	 * <p>提交任务</p> 
	 * @author alfred
	 * @date 2015-5-13 上午10:38:33
	 * @param taskNo
	 * @param loadEndTime
	 * @param deviceNo
	 * @param loaderCode
	 * @return 
	 */
	@Override
	public String submitLoadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode) {
		LOGGER.error("提交装车任务开始"+taskNo+" "+loaderCode);
		LoadTaskEntity loadTask = null;
		try{
			loadTask = pdaLoadDao.queryLoadTaskByTaskNoForUpdate(taskNo);
		}catch(CannotAcquireLockException e){
			throw new TfrBusinessException("任务提交中请稍后再试");
		}
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//Sets the 装车结束时间
			loadTask.setLoadEndTime(df.format(new Date()));
			//状态SUBMITED
			loadTask.setState(LoadConstants.LOAD_TASK_STATE_SUBMITED);
			//生成派送单、 生成到达联
			DeliverbillExpressTaskDto deliverbillExpressTaskDto  = new DeliverbillExpressTaskDto();
			List<DeliverbillExpressTaskDto> dtos = new ArrayList<DeliverbillExpressTaskDto>();
			//查询派件装车运单明细和派送单号
			List<LoadWaybillDetailEntity> loadWayBills = pdaExpressDeliverLoadDao.
					queryLoadWayBillAndDeliverNo(loadTask.getId());
			if(CollectionUtils.isNotEmpty(loadWayBills)){
				
				for(LoadWaybillDetailEntity goods : loadWayBills){
					if(!goods.getStockQty().equals(goods.getLoadQty())&&goods.getLoadQty()>0){
						throw new TfrBusinessException("运单"+goods.getWaybillNo()+"未全部扫描，不能提交装车");
					}
					DeliverbillExpressTaskDto expressTaskDto  = new DeliverbillExpressTaskDto();
					expressTaskDto.setWaybillNo(goods.getWaybillNo());
					expressTaskDto.setDeliverbillNo(goods.getBillNo());
					expressTaskDto.setGoodQtyTotal(goods.getLoadQty());
					dtos.add(expressTaskDto);
				}
			}
			//装车运单明细
			deliverbillExpressTaskDto.setList(dtos);
			//车牌号
			deliverbillExpressTaskDto.setVehicleNo(loadTask.getVehicleNo());
			//创建部门编码
			deliverbillExpressTaskDto.setCreateOrgCode(loadTask.getOrigOrgCode());
			//创建部门名称
			deliverbillExpressTaskDto.setCreateOrgName(loadTask.getOrigOrgName());
			//创建人
			List<LoaderParticipationEntity> loaders = pdaExpressDeliverLoadDao.queryLoader(loadTask.getId());
			if(CollectionUtils.isNotEmpty(loaders)){
				for(LoaderParticipationEntity loader : loaders){
					if(loader.getBeCreator().equals(FossConstants.YES)){
						//创建人编码
						deliverbillExpressTaskDto.setCreateUserCode(loader.getLoaderCode());
						//创建人名称
						deliverbillExpressTaskDto.setCreateUserName(loader.getLoaderName());
					}
				}
			}
			//调用接送货接口，生成派送单
			DeliverbillExpressTaskDto deliverBill = deliverbillExpressTaskService.
						createDeliverbillExpressTask(deliverbillExpressTaskDto);
			if(null != deliverBill){
				LOGGER.error("装车任务编号"+taskNo+" "+"派送单编号"+deliverBill.getDeliveryNo());
				loadTask.setDeliverBillNo(deliverBill.getDeliveryNo());
				//修改装车任务
				pdaLoadDao.updateLoadTask(loadTask);
			}else{
				throw new TfrBusinessException("生成派送单失败!");
			}
		}else{
			//抛出异常
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		return TransferPDADictConstants.SUCCESS;
	}

	/**
	 * 
	 * <p>作废任务</p> 
	 * @author alfred
	 * @date 2015-5-13 上午10:38:36
	 * @param taskNo
	 * @return 
	 */
	@Override
	public String cancelLoadTask(String taskNo) {

		LOGGER.error("取消装车任务开始"+taskNo);
		//查询装车任务
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
		//状态为进行中
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			int scanQty = pdaLoadDao.queryScanSerialNoQTYByTaskId(loadTask.getId());
			//扫描件数等于0才能取消
			if(scanQty > 0){
				throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_EXTIST_SCAN_RECORD_MESSAGECODE);
			}else{
				loadTask.setState(LoadConstants.LOAD_TASK_STATE_CANCELED);
				pdaLoadDao.updateLoadTask(loadTask);
			}
		}else if(loadTask != null && LoadConstants.LOAD_TASK_STATE_CANCELED.equals(loadTask.getState())){
			return TransferPDADictConstants.SUCCESS;
		}
		else{
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
		LOGGER.error("取消装车任务结束"+taskNo);
		return TransferPDADictConstants.SUCCESS;
	
	}

	/**
	 * 
	 * <p>获取未完成的派件装车指令</p> 
	 * @author alfred
	 * @date 2015-5-23 下午4:36:13
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
		condition.setTaskType(LoadConstants.LOAD_TASK_TYPE_EXPRESS_SENDPIECE_LOAD);
		List<String> states = new ArrayList<String>();
		states.add(LoadConstants.LOAD_TASK_STATE_LOADING);
		condition.setStates(states);
		loadList = pdaExpressDeliverLoadDao.queryUnfinishDriverLoadTask(condition);
		return loadList;
	}

}

/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: PDAExpressDeliverLoadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadSquadService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPorterService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.shared.define.OrderConstant;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressArrivalSheetDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.pickup.waybill.shared.util.ExpCommonUtils;
import com.deppon.foss.module.transfer.common.api.server.service.IBillNumService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *快递派送装车
 * @author dp-duyi
 * @date 2013-7-24 下午3:16:23
 */
public class PDAExpressDeliverLoadService implements IPDAExpressDeliverLoadService{
	public static final Logger LOGGER = LoggerFactory.getLogger(PDAExpressDeliverLoadService.class);
	private IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao;
	private IPDALoadDao pdaLoadDao;
	private IPlatformService platformService;
	private IPorterService porterService;
	private ILoadAndUnloadSquadService loadAndUnloadSquadService;
	private IEmployeeService employeeService;	
	private IStockService stockService;
	private IWaybillManagerService waybillManagerService;
	@Resource
	private IProductService productService4Dubbo;
	private IPDACommonService pdaCommonService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IWaybillExpressService waybillExpressService;
	private ISaleDepartmentService saleDepartmentService;
	public IWaybillRfcService waybillRfcService;
	private IRepaymentService repaymentService;
	private ICustomerService customerService;
	private ITfrJobTodoService tfrJobTodoService;//待办job service接口
	private IBillNumService billNumService;
	
	public void setBillNumService(IBillNumService billNumService) {
		this.billNumService = billNumService;
	}

	private IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}

	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}

	/**
	 * 
	 * 更改单服务接口
	 * @param waybillRfcService 
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	/** 
	 * 运单状态数据持久层
	 */
	private IActualFreightService actualFreightService;
	
	private IWeixinSendService weixinSendService;
	
	private ICustomerDao customerDao;
	
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	/**
	 *  客户通知
	 */
	private INotifyCustomerService notifyCustomerService;
	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;
	
	private ICommonExpressVehicleService commonExpressVehicleService;
	
	public void setCommonExpressVehicleService(
			ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setPdaExpressDeliverLoadDao(
			IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao) {
		this.pdaExpressDeliverLoadDao = pdaExpressDeliverLoadDao;
	}
	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
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
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
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
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/** 
	 * 查询未完成快递派送装车任务

	 * @author dp-duyi
	 * @date 2013-7-24 下午3:16:52
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService#queryExpressDeliverLoadTask(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryExpressDeliverLoadTask(
			String loaderCode, String loaderOrg, Date queryTimeBegin,
			Date queryTimeEnd) {
		LOGGER.error("查询未完成快递派送装车任务开始"+" 工号"+loaderCode+" 部门"+loaderOrg+" 查询开始时间:"+queryTimeBegin+" 查询结束时间"+queryTimeEnd);
		List<PDAAssignLoadTaskEntity> result = pdaExpressDeliverLoadDao.queryUnfinishedLoadTask(loaderCode, loaderOrg, queryTimeBegin, queryTimeEnd);
		if(CollectionUtils.isNotEmpty(result)){
			for(PDAAssignLoadTaskEntity r : result){
				LOGGER.error("任务号:"+r.getTaskNo()+" 任务类型:"+r.getLoadTaskType()+" 车牌号:"+r.getVehicleNo()+" 任务状态:"+r.getState());
			}
		}
		LOGGER.error("查询未完成快递派送装车任务结束"+" 工号"+loaderCode+" 部门"+loaderOrg+" 查询开始时间:"+queryTimeBegin+" 查询结束时间"+queryTimeEnd);
		return result;
	}

	/** 
	 * 建立任务
	 * @author dp-duyi
	 * @date 2013-7-24 下午3:16:52
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService#createTask(com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto)
	 */
	@Override
	public String createTask(ExpressDeliverLoadTaskDto task) {
		LOGGER.error("建立快递派送装车任务开始"+" 部门:"+task.getCreateOrgCode()+" "+task.getOperatorCode()+" "+task.getVehicleNo()+" "+task.getCourier()+" "+task.getDeviceNo());
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

			//taskNo  = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.ZC,task.getCreateOrgCode());
			taskNo = billNumService.generateLoadTaskNo(task.getCreateOrgCode());
			
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
			PlatformEntity plateform = platformService.queryPlatformByCode(origOrg.getCode(), task.getPlatformNo());
			if(plateform != null){
				loadTaskEntity.setPlatformId(plateform.getVirtualCode());
			}
			loadTaskEntity.setPlatformNo(task.getPlatformNo());
			loadTaskEntity.setState(LoadConstants.LOAD_TASK_STATE_LOADING);
			loadTaskEntity.setTaskNo(taskNo);
			loadTaskEntity.setTaskType(LoadConstants.LOAD_TASK_TYPE_DELIVER);
			loadTaskEntity.setVehicleNo(task.getVehicleNo());
			pdaLoadDao.insertTransferLoadTask(loadTaskEntity);
			this.insertLoadParticipator(taskId, loadBeginTime, task.getOperatorCode(), task.getCourier());
		}
		//插入装车PDA
		pdaEntity.setTaskNo(taskNo);
		pdaEntity.setDeviceNo(task.getDeviceNo());
		pdaEntity.setId(UUIDUtils.getUUID());
		pdaEntity.setJoinTime(loadBeginTime);
		pdaEntity.setTaskType(LoadConstants.LOADER_PARTICIPATION_TRANSFER_LOAD);
		pdaLoadDao.insertPDATask(pdaEntity);
		LOGGER.error("建立快递派送装车任务结束"+" 部门:"+task.getCreateOrgCode()+" "+task.getOperatorCode()+" "+task.getVehicleNo()+" "+task.getCourier()+" "+task.getDeviceNo());
		return taskNo;
	}
	
	/**
	 * 
	 * 根据装车任务表的taskNo查找装车运单明细表中已装车件数小于运单表的货物总件数的运单号信息 
	 * @param taskNo
	 * @return
		
	 */
      public List<String> queryWayBillNo(String taskNo){
	  //queryWayBillNo
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(taskNo);
     //任务为进行中才能提交
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			List<String>  listWayBill = new ArrayList<String>();
			//根据装车任务表的taskNo查找装车运单明细表中已装车件数小于运单表的货物总件数的运单号信息 
			listWayBill = pdaLoadDao.queryWayBillNo(taskNo);
			LoadTaskEntity waybillNos =  pdaLoadDao.queryWayBillNos(taskNo);
			LoadTaskEntity loadTaskEntity = new LoadTaskEntity();
			if(waybillNos !=null){
				loadTaskEntity.setIsPicPackage("Y");
			}else{
				loadTaskEntity.setIsPicPackage("N");
			}
		   listWayBill.add(loadTaskEntity.getIsPicPackage());
		
		    //返回成功
			return listWayBill;
		}else{
			//无效任务
			throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_TASK_MESSAGECODE);
		}
	}

	/** 
	 * 取消快递派送装车任务
	 * @author dp-duyi
	 * @date 2013-7-24 下午3:16:52
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService#cancelLoadTask(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public String cancelLoadTask(String taskNo, String deviceNo,
			String operatorCode, Date cancelTime) {
		LOGGER.error("取消装车任务开始"+taskNo+" "+operatorCode);
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
		LOGGER.error("取消装车任务结束"+taskNo+" "+operatorCode);
		return TransferPDADictConstants.SUCCESS;
	}

	/** 
	 * 提交快递派送装车任务
	 * @author dp-duyi
	 * @date 2013-7-24 下午3:16:52
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService#submitLoadTask(java.lang.String, java.util.Date, java.lang.String, java.lang.String)
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
		
		if(loadTask == null){
			LOGGER.error("提交装车任务结束"+taskNo+" 查询不到LOADING状态");
			return TransferPDADictConstants.SUCCESS;
		}
		
		//根据任务编号查询运单号
		List<String> waybillList =  pdaLoadDao.queryWayBillNoList(taskNo);
		for(String s : waybillList){
			//转寄退回件
			ReturnGoodsRequestEntity returnGoodsRequestEntity = 
					returnGoodsRequestEntityService.queryReturnGoodsEntityByWayBillNo(s);
			//未上报工单:isHandle为null,上报工单未处理:isHandle为N,上报工单已处理:isHandle为Y,如果为N,不允许提交
			String isHandle = null;
			if(returnGoodsRequestEntity != null){
				isHandle = returnGoodsRequestEntity.getIsHandle();
			}
			if(isHandle != null && "N".equalsIgnoreCase(isHandle)){
				throw new TfrBusinessException("运单号: " +s+"已上报工单且未处理!");
			}
		}
		
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			//查询出库货物
			Map<String,String> condition = new HashMap<String,String>();
			condition.put("taskId", loadTask.getId());
			condition.put("beLoaded", FossConstants.YES);
			List<LoadTaskSerialNoDto> outStockGoodsList = pdaLoadDao.queryOutStockGoods(condition);
			
			
			/*//查询未受理的更改单
			List<String> list =  waybillRfcService.isExsitsWayBillRfcs(waybillNolist);
			if (CollectionUtils.isNotEmpty(list)){
				throw new TfrBusinessException("存在未受理的更改单"+list+"不能提交任务!");
			}*/
			List<InOutStockEntity> inOutStockList = new ArrayList<InOutStockEntity>();
			
			
			//出库货物
			LOGGER.error("出库装车货物开始"+taskNo);
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
					LOGGER.error("出库"+inOutStockEntity.getWaybillNO()+inOutStockEntity.getSerialNO());
				}
				//PC端操作批量出库
				stockService.outStockBatchPC(inOutStockList);
			}
			LOGGER.error("出库装车货物结束"+taskNo);
			//修改装车任务中理货员
			Map<String,Object> param = new HashMap<String,Object>();
			//任务id
			param.put("id", loadTask.getId());
			//结束时间
			param.put("endTime", new Date());
			//提交装车任务时更新理货员
			pdaLoadDao.updateLoaderParticipationByLoadTask(param);
			
			Map<String,Object> parameter = new HashMap<String,Object>();
			//任务号
			parameter.put("taskNo", taskNo);
			//设备号
			parameter.put("deviceNo", deviceNo);
			//离开时间
			parameter.put("leaveTime", new Date());
			pdaLoadDao.updatePDATaskEntity(parameter);
			
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			//Sets the 装车结束时间
			loadTask.setLoadEndTime(df.format(new Date()));
			//状态SUBMITED
			loadTask.setState(LoadConstants.LOAD_TASK_STATE_SUBMITED);
			//生成派送单、 生成到达联
			WaybillExpressArrivalSheetDto arrivalSheetDto = new WaybillExpressArrivalSheetDto();
			//创建部门编码
			arrivalSheetDto.setCreateOrgCode(loadTask.getOrigOrgCode());
			//创建部门名称
			arrivalSheetDto.setCreateOrgName(loadTask.getOrigOrgName());
			//车牌号
			arrivalSheetDto.setVehicleNo(loadTask.getVehicleNo());
			List<LoaderParticipationEntity> loaders = pdaExpressDeliverLoadDao.queryLoader(loadTask.getId());
			if(CollectionUtils.isNotEmpty(loaders)){
				for(LoaderParticipationEntity loader : loaders){
					if(FossConstants.YES.equals(loader.getBeCreator())){
						//创建人编码
						arrivalSheetDto.setCreateUserCode(loader.getLoaderCode());
						//创建人名称
						arrivalSheetDto.setCreateUserName(loader.getLoaderName());
					}else if(LoadConstants.BE_LOAD_COURIER.equals(loader.getBeCreator())){
						//司机编码
						arrivalSheetDto.setDriverCode(loader.getLoaderCode());
						//司机名称
						arrivalSheetDto.setDriverName(loader.getLoaderName());
					}
				}
			}
			
			/*EmployeeEntity emp = new EmployeeEntity();
			if(StringUtils.isEmpty(arrivalSheetDto.getDriverCode())){
				emp = employeeService.queryEmployeeByEmpCode(arrivalSheetDto.getCreateUserCode());
			}else{
				emp = employeeService.queryEmployeeByEmpCode(arrivalSheetDto.getDriverCode());
			}*/
			//短信或微信  --根据工号查询司机信息
			ExpressVehicleDto expressVeh = new ExpressVehicleDto();
			expressVeh.setVehicleNo(loadTask.getVehicleNo());
			expressVeh.setActive(FossConstants.ACTIVE);
			List<ExpressVehicleDto> dtos = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh, LoadConstants.SONAR_NUMBER_50, 0);
			if (CollectionUtils.isNotEmpty(dtos)) {
				expressVeh = dtos.get(0);
			}
			//查询装车运单明细
			List<WaybillExpressArrivalSheetDto> arrivalSheetDetails = null;
			List<LoadWaybillDetailEntity> loadWayBills = pdaExpressDeliverLoadDao.queryLoadWayBillQty(loadTask.getId());
			if(CollectionUtils.isNotEmpty(loadWayBills)){
				arrivalSheetDetails = new ArrayList<WaybillExpressArrivalSheetDto>();
				WaybillExpressArrivalSheetDto arrivalSheetDetail = null;
				for(LoadWaybillDetailEntity goods : loadWayBills){
					arrivalSheetDetail = new WaybillExpressArrivalSheetDto();
					//运单号
					arrivalSheetDetail.setWaybillNo(goods.getWaybillNo());
					//扫描件数
					arrivalSheetDetail.setGoodQtyTotal(goods.getLoadQty());
					arrivalSheetDetails.add(arrivalSheetDetail);
				}
			}
			if(CollectionUtils.isNotEmpty(arrivalSheetDetails)){
				//装车运单明细
				arrivalSheetDto.setList(arrivalSheetDetails);
			}
			/**调用接送货接口生成派送单和到达联*/
			WaybillExpressArrivalSheetDto deliverBill = waybillExpressService.createExpressArrivalSheetAndDeliveryBill(arrivalSheetDto);
			if(deliverBill != null){
				LOGGER.error("装车任务编号"+taskNo+" "+"派送单编号"+deliverBill.getDeliveryNo());
				loadTask.setDeliverBillNo(deliverBill.getDeliveryNo());
				//修改装车任务
				pdaLoadDao.updateLoadTask(loadTask);
				if(CollectionUtils.isNotEmpty(arrivalSheetDetails)){
					//发送短信
					this.sendMessge(arrivalSheetDetails, expressVeh, loadTask);
					//推送微信
					this.doWeiXin(arrivalSheetDetails, expressVeh, arrivalSheetDto.getCreateOrgCode());
				}
				
			}else{
				throw new TfrBusinessException("生成派送单失败!");
			}
		}
		
		/****
		 *@author 205109-foss-zenghaibin
		 *派送装车时插入轨迹job，后续菜鸟网消息推送  
		 ****/
		LOGGER.error("派件装车扫描"+taskNo+"推送货物轨迹，插入未执行job开始： ");
		String [] businessGoalList=new String []{BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO};
		try{
			tfrJobTodoService.addJobTodo(taskNo, BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN, businessGoalList, new Date(), "SENT_SCAN");
			}catch(TfrBusinessException e){
			throw  e;
		}catch(Exception e){
			throw new TfrBusinessException(e.toString());
		}
		LOGGER.error("派件装车扫描"+taskNo+"推送货物轨迹，插入未执行job结束 ");
		
		LOGGER.error("建立派送装车任务结束"+loadTask.getDeliverBillNo()+" "+loadTask.getVehicleNo()+" "+taskNo);
		
		LOGGER.error("提交装车任务结束"+taskNo+" "+loaderCode);
		return TransferPDADictConstants.SUCCESS;
	}
	
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	/**
	 * update：快递派送收货人短信 根据CRM传递过来的状态标示是否需要发送通知短信
	 * 快递派送收货人短信
	 * <p>发送短信</p> 
	 * @author alfred
	 * @date 2014-3-4 上午8:27:19
	 * @param arrivalSheetDetails
	 * @param emp
	 * @param loadTask
	 * @see
	 */
	private void sendMessge(List<WaybillExpressArrivalSheetDto> arrivalSheetDetails,ExpressVehicleDto expressVeh,LoadTaskEntity loadTask){
		for(WaybillExpressArrivalSheetDto dto:arrivalSheetDetails){
			//短信内容
			String customercontent = "";
			WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
			
			BigDecimal payTotalAmount = pdaExpressDeliverLoadDao.queryWaybillAmount(dto.getWaybillNo());
			if(payTotalAmount.equals(new BigDecimal(0))){
				customercontent = getSmsContent(wayBill,expressVeh,payTotalAmount,
						loadTask.getOrigOrgCode(),LoadConstants.EXPRESS_NO_TOPAY_SMS);
			}else{
				customercontent = getSmsContent(wayBill,expressVeh,payTotalAmount,
						loadTask.getOrigOrgCode(),LoadConstants.EXPRESS_TOPAY_SMS);
			}
			try {
				//update start
				String receiveCustomerCode = wayBill.getReceiveCustomerCode();
				if(StringUtils.isNotEmpty(receiveCustomerCode)){
					CustomerEntity customerEntity = customerDao.queryCustStateByCode(receiveCustomerCode);
					CustomerEntity deliveryCustomerEntity = customerDao.queryCustStateByCode
							(wayBill.getDeliveryCustomerCode());
					boolean isSend = true;
					if(customerEntity!=null &&deliveryCustomerEntity!=null){
						isSend = isSendMessageByCustomerStatus(customerEntity,deliveryCustomerEntity);
						//增加三种类型停发短信类型  
						if(!isSend){
							//停发短信
						}else{
							if(StringUtils.isNotEmpty(wayBill.getDeliveryCustomerCode())){
							 CustomerDto customerDto = customerService.queryCustInfoByCode(wayBill.getDeliveryCustomerCode());
							
						     if(StringUtils.isEmpty(customerDto.getFixedReceiveMobile())||!StringUtils.equals(
						        customerDto.getFixedReceiveMobile(), wayBill.getReceiveCustomerMobilephone())){
						    	 //发送短信
						    	 sendMess(expressVeh, customercontent, loadTask, wayBill);
						     }
						    }else {
						    	sendMess(expressVeh, customercontent, loadTask, wayBill);
						    }
						}
					}else{
						//发送短信
						sendMess(expressVeh, customercontent, loadTask, wayBill);
					}
				}else{
					sendMess(expressVeh, customercontent, loadTask, wayBill);
				}
				//update end
			} catch (Exception e) {
				LOGGER.error("--短信发送失败!" + e.getMessage(), e);//记录日志
			}
		}
	}
	
	/**
	 * 
	 * <p>判断一个客户是否需要发送短信  1.当CRM勾选“客户作为收件人短信停发”时，停止对客户推送快递派送收件人短信、快递开单收件人短信、快递签收收件人短信。
	    * 2.当CRM勾选“客户的收件人短信停发”时，停止对客户推送快递派送收件人短信、快递开单收件人短信、快递签收收件人短信。
	    * 3.当CRM勾选“两者都停发”时，停止对客户和该客户的收件人推送快递派送收件人短信、快递开单收件人短信、快递签收收件人短信</p> 
	 * @author alfred
	 * @date 2015-9-14 下午4:08:32
	 * @param customerEntity
	 * @param deliveryCustomerEntity
	 * @return
	 * @see
	 */
	private boolean isSendMessageByCustomerStatus(CustomerEntity customerEntity,CustomerEntity deliveryCustomerEntity){
		String receiverSms = customerEntity.getReceiverSms();
		String deliverySms = deliveryCustomerEntity.getReceiverSms();

		// 依次分3种情况
		if (OrderConstant.STOP_MESSAGE_FOR_RECEIPT.equals(receiverSms) ||
				OrderConstant.STOP_MESSAGE_FOR_CUST_RECEIPT.equals(deliverySms) ||
				(OrderConstant.STOP_MESSAGE_FOR_DOUBLE.equals(deliverySms)||OrderConstant.STOP_MESSAGE_FOR_DOUBLE.equals(receiverSms))) {
			return false;
		}/* else if (OrderConstant.STOP_MESSAGE_FOR_CUST_RECEIPT.equals(deliverySms)) {
			return false;
		} else if (OrderConstant.STOP_MESSAGE_FOR_DOUBLE.equals(deliverySms)||OrderConstant.STOP_MESSAGE_FOR_DOUBLE.equals(receiverSms)) {
			return false;
		}*/
		return true;
	}
	
	/**
	 * 
	 * <p>发送微信</p> 
	 * @author alfred
	 * @date 2014-3-21 上午10:04:11
	 * @param arrivalSheetDetails
	 * @param emp
	 * @param orgCode
	 * @see
	 */
	private void doWeiXin(List<WaybillExpressArrivalSheetDto> arrivalSheetDetails,ExpressVehicleDto expressVeh,String orgCode) {
		//微信dto
		List<WeixinSendDto> dtoList = new ArrayList<WeixinSendDto>();
		for(WaybillExpressArrivalSheetDto dto:arrivalSheetDetails) {
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			actualFreightEntity = this.actualFreightService.queryByWaybillNo(dto.getWaybillNo());
			WeixinSendDto weiXinDto = new WeixinSendDto();
			//运单号
			weiXinDto.setWaybillNo(dto.getWaybillNo());
			//状态类型
			weiXinDto.setStateType(WeixinConstants.WEIXIN_DELIVER_TYPE);
			//状态发生时间
			weiXinDto.setCreateTime(new Date());
			//当前处理件数
			weiXinDto.setCurrentPieces(dto.getGoodQtyTotal());
			//已处理件数
			weiXinDto.setProcessedPieces(actualFreightEntity.getArrangeGoodsQty());
			//派送人电话
			weiXinDto.setDeliverManMobile(expressVeh.getMobilePhone());
			//派送人姓名
			weiXinDto.setDeliverManName(expressVeh.getEmpName());
			//派送网点编码
			weiXinDto.setDeliverOrgCode(orgCode);
			dtoList.add(weiXinDto);
		}
		for(WeixinSendDto dto:dtoList) {
			weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(dto,WeixinConstants.WEIXIN_DELIVER_TYPE);
		}
	}

	/**
	 * 
	 *  发送短信内容 
	 * @author alfred
	 * @date 2014-3-4 上午8:26:38
	 * @param wayBill
	 * @param emp
	 * @param payTotalAmount
	 * @param orgCode
	 * @param smsCode
	 * @return
	 * @see
	 */
	public String getSmsContent(WaybillEntity wayBill, ExpressVehicleDto expressVeh,BigDecimal payTotalAmount,
			String  orgCode,String smsCode) {
		String sms = ""; // 返回短信
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		// 部门编码
		smsParamDto.setOrgCode(orgCode);
		smsParamDto.setMap(getSmsParam(wayBill.getWaybillNo(), expressVeh, payTotalAmount));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {//捕获异常
			LOGGER.error("短信内容为空", e);//记录日志
			throw new SignException(SignException.MESS_CONTENT_ISNULL, e);//短信内容为空
		}
		if (StringUtil.isBlank(sms)) {
			LOGGER.error("没有对应的短信模版");//记录日志
			throw new SignException(SignException.NO_SMS_TEMPLATES);//没有对应的短信模版
		}
		return sms;
	}
	
	/**
	 * 
	 * 短信内容字段定义
	 * @author alfred
	 * @date 2014-3-4 上午8:27:19
	 * @param waybillNo
	 * @param emp
	 * @param payTotalAmount
	 * @return
	 * @see
	 */
	private Map<String, String> getSmsParam(String waybillNo,ExpressVehicleDto expressVeh,BigDecimal payTotalAmount) {
		Map<String, String> map = new HashMap<String, String>();
		// 运单号
		map.put("waybillNo", waybillNo);
		// 快递员
		map.put("expressDeliveryName", expressVeh.getEmpName());
		// 快递员手机
		map.put("expressDeliveryContact", expressVeh.getMobilePhone());
		// 总付金额
		map.put("toPayTotal", payTotalAmount.toString());
		return map;
	}
	
	/**
	 * 
	 * 发送短信 
	 * @author alfred
	 * @date 2014-3-4 上午8:26:53
	 * @param emp
	 * @param customerSms
	 * @param loadTask
	 * @param waybill
	 * @return
	 * @see
	 */
	public boolean sendMess(ExpressVehicleDto expressVeh,String customerSms,LoadTaskEntity loadTask,WaybillEntity waybill) {
		boolean sendMessResult=false;
		LOGGER.info("短信发送开始!");//记录日志
		try {
			NotificationEntity notificationEntity = new NotificationEntity();
			// 运单号
			notificationEntity.setWaybillNo(waybill.getWaybillNo());
			// 通知类型为短信通知
			notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
			// 通知内容  ---
			notificationEntity.setNoticeContent(customerSms);
			// 操作人
			notificationEntity.setOperator(expressVeh.getEmpName());
			// 操作人编码
			notificationEntity.setOperatorNo(expressVeh.getEmpCode());
			// 操作部门
			notificationEntity.setOperateOrgName(loadTask.getOrigOrgName());
			// 操作部门编码
			notificationEntity.setOperateOrgCode(loadTask.getOrigOrgCode());
			// 收货人
			notificationEntity.setConsignee(waybill.getReceiveCustomerContact());
			// 手机号
			notificationEntity.setMobile(waybill.getReceiveCustomerMobilephone());
			// 操作时间
			notificationEntity.setOperateTime(new Date());
			// 模块名称
			notificationEntity.setModuleName(NotifyCustomerConstants.SMS_PKP_NOTIFY_EXP);
			// 发送短信
			notifyCustomerService.sendMessage(notificationEntity);
			sendMessResult = true;
		} catch (NotifyCustomerException e) {//捕获异常
			// 异常处理
			LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);//记录日志
			sendMessResult =false;
		}
		return sendMessResult;

	}
	
	/** 
	 * 装车扫描
	 * @author dp-duyi
	 * @date 2013-7-24 下午3:16:52
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService#scan(com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto)
	 */
	@Override
	public void scan(ExpressDeliverScanDto scanRecord) {
		LOGGER.error("装车扫描开始"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType()+scanRecord.getScanTime());
		LoadTaskEntity loadTask = pdaLoadDao.queryLoadTaskByTaskNo(scanRecord.getTaskNo());
		if(loadTask != null && LoadConstants.LOAD_TASK_STATE_LOADING.equals(loadTask.getState())){
			/**
			 * 货物校验
			 */
			WaybillEntity wayBill = waybillManagerService.queryWaybillBasicByNo(scanRecord.getWayBillNo());
			if(wayBill == null){
				LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("该运单不存在");
			}
			
			if(!LoadConstants.PRODUCT_CODE_PACKAGE.equals(wayBill.getProductCode())&&!LoadConstants.PRODUCT_CODE_RCP.equals(wayBill.getProductCode())
					&&!LoadConstants.PRODUCT_CODE_EPEP.equals(wayBill.getProductCode())&&!LoadConstants.PRODUCT_CODE_DEAP.equals(wayBill.getProductCode())){
				LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("非快递货");
			}
			/**校验网上支付**/
			RepaymentDto repaymentDto;
			try{
				 repaymentDto =repaymentService.queryPaymentByWaybillNo(scanRecord.getWayBillNo());
			}catch(Exception e){
				LOGGER.error("装车扫描结束，调用结算接口异常"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("调用综合接口：查询网上运单支付信息异常"+e.toString());
			}
			
			if(repaymentDto!=null&&StringUtils.equals(repaymentDto.getIsPayByOL(), FossConstants.NO)){
				LOGGER.error("装车扫描结束，网上支付未完成"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("网上支付未完成");
			}
			
			if(!loadTask.getOrigOrgCode().equals(wayBill.getCustomerPickupOrgCode())){
				boolean beFinalArriveOrg = false;
				//根据外场查询到达驻地部门
				List<SaleDepartmentEntity> stationOrgs = queryArriveStationSaleByTransferCenterCode(loadTask.getOrigOrgCode());
				if(CollectionUtils.isNotEmpty(stationOrgs)){
					for(SaleDepartmentEntity station : stationOrgs){
						if(station.getCode().equals(wayBill.getCustomerPickupOrgCode())){
							beFinalArriveOrg = true;
						}
					}
				}
				if(!beFinalArriveOrg){
					LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
					throw new TfrBusinessException("装车部门不为补码最终到达部门");
				}
				
			}
			//ISSUE-4230
			if(ExpCommonUtils.verdictPickUpSelf(wayBill.getReceiveMethod())){
				LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("货物提货方式为自提不能装车");
			}
			StockEntity stock = stockService.queryUniqueStock(wayBill.getWaybillNo(), scanRecord.getSerialNo());
			if(stock == null || !loadTask.getOrigOrgCode().equals(stock.getOrgCode())){

				LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("货物不在本部门库存");
			}
			boolean isExsits = waybillRfcService.isExsitsWayBillRfc(wayBill.getWaybillNo());
			if(isExsits){
				LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType());
				throw new TfrBusinessException("存在未受理的更改单");
			}
			
			//查询标签是否有效
			Map<String,String> condition = new HashMap<String,String>();
			//运单号
			condition.put("wayBillNo", wayBill.getWaybillNo());
			//流水号
			condition.put("serialNo", scanRecord.getSerialNo());
			//有效状态
			condition.put("active", FossConstants.ACTIVE);
			//查询货签是否有效
			int valideLabeledCount = pdaLoadDao.queryValidLabeledCount(condition);
			//查询货物是否已签收
			int signedQty = pdaLoadDao.queryWayBillSignedState(condition);
			//货签无效或已签收，则货物状态为无效
			if(valideLabeledCount <= 0 || signedQty > 0 ){
				throw new TfrBusinessException("无效流水号");
			}
			/**
			 * 保存装车扫描
			 */
			LoadWaybillDetailEntity loadWayBill = null;
			LoadSerialNoEntity loadSerialNo = null;
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
				loadWayBill.setGoodsName(wayBill.getGoodsName());
				loadWayBill.setId(UUIDUtils.getUUID());
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
				int stockQty = pdaLoadDao.queryWayBillStockQty(wayBill.getWaybillNo(), loadTask.getOrigOrgCode());
				loadWayBill.setStockQty(stockQty);
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
				pdaLoadDao.insertLoadWayBillDetailEntity(loadWayBill);
			}else{/**装车运单存在*/
				if(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED.equals(scanRecord.getType())){//取消扫描
					int stockQty = pdaLoadDao.queryWayBillStockQty(wayBill.getWaybillNo(), loadTask.getOrigOrgCode());
					loadWayBill.setStockQty(stockQty);
					loadWayBill.setLoadVolumeTotal(-wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadWeightTotal(-wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadQty(-1);
					if(StringUtils.equals(TransferPDADictConstants.SCAN_STATE_SCANED, scanRecord.getScanState())){
						loadWayBill.setScanQty(-1);
					}else{
						loadWayBill.setScanQty(0);
					}
				}else{//扫描
					int stockQty = pdaLoadDao.queryWayBillStockQty(wayBill.getWaybillNo(), loadTask.getOrigOrgCode());
					loadWayBill.setStockQty(stockQty);
					loadWayBill.setLoadVolumeTotal(wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadWeightTotal(wayBill.getGoodsVolumeTotal().doubleValue()/wayBill.getGoodsQtyTotal());
					loadWayBill.setLoadQty(1);
					if(TransferPDADictConstants.SCAN_STATE_SCANED.equals(scanRecord.getScanState())){
						loadWayBill.setScanQty(1);
					}else{
						loadWayBill.setScanQty(0);
					}
				}
				//按扫描时间修改防并发
				pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWayBill, scanRecord.getSerialNo(), scanRecord.getScanTime());
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
				loadSerialNo.setGoodsState(scanRecord.getType());
				loadSerialNo.setId(UUIDUtils.getUUID());
				loadSerialNo.setLoadTime(scanRecord.getScanTime());
				loadSerialNo.setLoadWaybillDetailId(loadWayBill.getId());
				loadSerialNo.setOrigOrgCode(loadTask.getOrigOrgCode());
				loadSerialNo.setScanState(scanRecord.getScanState());
				loadSerialNo.setSerialNo(scanRecord.getSerialNo());
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
				loadSerialNo.setGoodsState(scanRecord.getType());
				loadSerialNo.setScanState(scanRecord.getScanState());
				loadSerialNo.setDeviceNo(scanRecord.getDeviceNo());
				//按扫描时间修改防并发
				pdaExpressDeliverLoadDao.updateLoadSerialNoByLoadTime(loadSerialNo);
			}
			LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType()+scanRecord.getScanTime());
		}else{
			LOGGER.error("装车扫描结束"+scanRecord.getTaskNo()+scanRecord.getWayBillNo()+scanRecord.getSerialNo()+scanRecord.getType()+scanRecord.getScanTime());
			throw new TfrBusinessException("无效装车任务");
		}
	}
	/** 
	 * 快递派送装车理货员
	 * @author dp-duyi
	 * @date 2013-7-24 下午3:16:52
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService#scan(com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto)
	 */
	private void insertLoadParticipator(String taskId,Date loadBeginTime,String operatorCode,String courierCode){
		List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
		LoaderParticipationEntity loader = new LoaderParticipationEntity();
		loader.setBeCreator(FossConstants.YES);
		loader.setId(UUIDUtils.getUUID());
		loader.setJoinTime(loadBeginTime);
		loader.setLoaderCode(operatorCode);
		loader.setTaskId(taskId);
		loader.setTaskType(LoadConstants.LOADER_PARTICIPATION_DELIVER_LOAD);
		PorterEntity porter = porterService.queryPorterByEmpCode(loader.getLoaderCode());
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
			LOGGER.error("查询理货员开始"+loader.getLoaderCode());
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
			//非法理货员
			//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
		}
		loaders.add(loader);
		LoaderParticipationEntity courier = new LoaderParticipationEntity();
		courier.setBeCreator(LoadConstants.BE_LOAD_COURIER);
		courier.setId(UUIDUtils.getUUID());
		courier.setJoinTime(loadBeginTime);
		courier.setLoaderCode(courierCode);
		courier.setTaskId(taskId);
		courier.setTaskType(LoadConstants.LOADER_PARTICIPATION_DELIVER_LOAD);
		LOGGER.error("查询接送货员开始"+courier.getLoaderCode());
		porter = porterService.queryPorterByEmpCode(courier.getLoaderCode());
		//调用综合接口查询理货员名称、理货员所属装卸车队
		if(porter != null){
			courier.setLoaderName(porter.getEmpName());
			if(StringUtils.isNotBlank(porter.getParentOrgCode())){
				courier.setLoadOrgCode(porter.getParentOrgCode());
				LoadAndUnloadSquadEntity team = loadAndUnloadSquadService.queryLoadAndUnloadSquadByCode(porter.getParentOrgCode());
				if(team != null){
					courier.setLoadOrgName(team.getName());
				}else{
					//非法理货员
					//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
				}
			}
		}else{
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(courier.getLoaderCode());
			if(emp != null){
				courier.setLoaderName(emp.getEmpName());
				loader.setLoadOrgCode(emp.getOrgCode());
				OrgAdministrativeInfoEntity empOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(emp.getOrgCode());
				if(empOrg != null){
					loader.setLoadOrgName(empOrg.getName());
				}
			}else{
				LOGGER.error("接送货员不存在"+courier.getLoaderCode());
				throw new TfrBusinessException("无效接送货员");
			}
			//非法理货员
			//throw new TfrBusinessException(TransferPDAExceptionCode.EXCEPTION_INVALID_LOADER_MESSAGECODE);
		}
		loaders.add(courier);
		pdaLoadDao.insertTransferLoaderParticipation(loaders);
	}
	/** 
	 * 快递派送装车理货员
	 * @author dp-duyi
	 * @date 2013-10-8 下午2:46:52
	 * @see 
	 */
	private List<SaleDepartmentEntity> queryArriveStationSaleByTransferCenterCode(String transferCenterCode){
		// 声明一个对象做查询条件
		SaleDepartmentEntity condition = new SaleDepartmentEntity();
		// 设置外场部门编码
		condition.setTransferCenter(transferCenterCode);
		// 设置驻地营业部
		condition.setStation(FossConstants.YES);
		// 设为到达营业部
		condition.setArrive(FossConstants.YES);
		return saleDepartmentService.querySimpleSaleDepartmentExactByEntity(condition, 0, LoadConstants.SONAR_NUMBER_1000);
	}
}

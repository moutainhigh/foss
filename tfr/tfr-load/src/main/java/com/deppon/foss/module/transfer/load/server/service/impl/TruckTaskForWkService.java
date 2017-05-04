package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskBillDetailWkEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskDetailWkEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskInfoDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.TruckTaskInfoResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.UpdateWkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKTfrBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskForWkService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class TruckTaskForWkService implements ITruckTaskForWkService {
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/** 
	 * 车辆任务dao.
	 *  
	 */
	private ITruckTaskDao truckTaskDao;
	
	private IWKTfrBillDao wKTfrBillDao;
	
	private IHandOverBillService handOverBillService;
	
	private ITruckTaskService truckTaskService;
	
	private ITfrNotifyService tfrNotifyService;
	
	private IFOSSToWkService fossToWkService;
	
	/* 组织信息 Service接口 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * @return the orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	/** 
	 * The line service. 
	 * 
	 */
	private ILineService lineService;
	
	private IExpressLineService expresslineService ;
	
	
	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	public void setTruckTaskDao(ITruckTaskDao truckTaskDao) {
		this.truckTaskDao = truckTaskDao;
	}
	
	public void setwKTfrBillDao(IWKTfrBillDao wKTfrBillDao) {
		this.wKTfrBillDao = wKTfrBillDao;
	}

	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	
	/**
	* @description 针对快递系统交接单信息生成车辆任务
	* @param handOverDto
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年5月4日 下午4:48:02
	*/
	public void createTruckTaskFromWk(TruckTaskHandOverDto handOverDto,TfrNotifyEntity createTruckNotifyEntity) { 
		/**异步通知任务实体   创建通知快递系统异步通知任务的job实体*/
//		TfrNotifyEntity notifyEntity=new TfrNotifyEntity();
//		notifyEntity.setId(UUIDUtils.getUUID());
//		notifyEntity.setNotifyType(NotifyWkConstants.NOTIFY_TYPE_PUSH_TRUCK_TASK);
		
		LOGGER.error("[快递交接单]交接单生成任务车辆开始"+handOverDto.getHandOverBillNo());
		String truckTaskId = truckTaskDao.queryBeCreateTruckTask(handOverDto);
		//如果是挂牌号则设置挂牌号
		if(StringUtils.equals(handOverDto.getBeTrailerVehicleNo(),FossConstants.YES)){
			handOverDto.setTrailerVehicleNo(handOverDto.getVehicleNo());
		}
		//查询最新生成的单据明细是否大于7天,先去掉啊，加上出问题啊，又不知道问题在哪啊，先去掉试试啊，纠结
		/*if(StringUtils.isNotBlank(truckTaskId)){
			String taskBillId = truckTaskDao.queryLastedCreateTruckTaskBill(truckTaskId, null, null);
			if(StringUtils.isBlank(taskBillId)){
				truckTaskId = null;
			}
		}*/
		//不存在任务车辆
		if(StringUtils.isBlank(truckTaskId)){
			/**调用综合接口查询线路等*/
			DepartureStandardDto departureStandard=returnComprehensiveLine(handOverDto);
			/**调用快递接口查询线路*/
			DepartureStandardDto departureStandard2=returnWkLine(handOverDto,departureStandard);
			
			handOverDto.setTruckTaskId(UUIDUtils.getUUID());
			LOGGER.error("[快递交接单]生成任务车辆主表开始"+handOverDto.getHandOverBillNo());
			/**异步通知任务记录需要通知的主表id*/
//			notifyEntity.setNotifyParam1(this.insertTruckTask(handOverDto,departureStandard,departureStandard2));
			this.insertTruckTask(handOverDto,departureStandard,departureStandard2);
			LOGGER.error("[快递交接单]生成任务车辆主表结束"+handOverDto.getHandOverBillNo());
			
			handOverDto.setTruckTaskDettailId(UUIDUtils.getUUID());
			LOGGER.error("[快递交接单]生成任务车辆明细开始"+handOverDto.getHandOverBillNo());
//			notifyEntity.setNotifyParam2(this.insertTruckTaskDetail(handOverDto,departureStandard,departureStandard2));
			this.insertTruckTaskDetail(handOverDto,departureStandard,departureStandard2);
			LOGGER.error("[快递交接单]生成任务车辆明细结束"+handOverDto.getHandOverBillNo());
			//存在任务车辆
		}else{
			handOverDto.setTruckTaskId(truckTaskId);
			TruckTaskDetailEntity truckTaskDetailEntity = truckTaskDao.queryBeCreateTruckTaskDetail(handOverDto);
			if(truckTaskDetailEntity == null || StringUtils.isBlank(truckTaskDetailEntity.getId())){//不存在任务车辆明细
				/**调用综合接口查询线路等*/
				DepartureStandardDto departureStandard=returnComprehensiveLine(handOverDto);
				/**调用快递接口查询线路*/
				DepartureStandardDto departureStandard2=returnWkLine(handOverDto,departureStandard);
				
				handOverDto.setTruckTaskDettailId(UUIDUtils.getUUID());
				LOGGER.error("[快递交接单]生成任务车辆明细开始"+handOverDto.getHandOverBillNo());
//				notifyEntity.setNotifyParam2(this.insertTruckTaskDetail(handOverDto,departureStandard,departureStandard2));
				this.insertTruckTaskDetail(handOverDto,departureStandard,departureStandard2);
				LOGGER.error("[快递交接单]生成任务车辆明细结束"+handOverDto.getHandOverBillNo());
			}else{
				handOverDto.setTruckTaskDettailId(truckTaskDetailEntity.getId());
				//如果该存在的任务是整车，但是新增的任务不是整车则提示
				if(StringUtils.equals(truckTaskDetailEntity.getBeCarLoad(),FossConstants.YES) && StringUtils.equals(handOverDto.getBeCarLoad(),FossConstants.NO) ){					
					LOGGER.error("[快递交接单]该车辆已做了整车不能再做非整车交接"+handOverDto.getHandOverBillNo());
					throw new TfrBusinessException("该车辆已做了整车不能再做非整车交接");
				}else if(StringUtils.equals(truckTaskDetailEntity.getBeCarLoad(),FossConstants.NO) && StringUtils.equals(handOverDto.getBeCarLoad(),FossConstants.YES)){
					LOGGER.error("该车辆已做了非整车不能再做整车交接"+handOverDto.getHandOverBillNo());
					throw new TfrBusinessException("该车辆已做了非整车不能再做整车交接");
				}
				/*if(truckTaskDetailEntity.getState().equals(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY)){
					beDepart = 1;
				}*/
			}
		}
		LOGGER.error("[快递交接单]生成任务车辆单据开始"+handOverDto.getHandOverBillNo());
//		notifyEntity.setNotifyParam3(this.insertTruckTaskBill(handOverDto,TaskTruckConstant.BILL_TYPE_HANDOVER));
		this.insertTruckTaskBill(handOverDto,TaskTruckConstant.BILL_TYPE_HANDOVER);
		LOGGER.error("[快递交接单]生成任务车辆单据开始"+handOverDto.getHandOverBillNo());
		
		/**更新交接单是否生成任务车辆状态*/
		Map<String,String> handOverBill = new HashMap<String,String>();
		//交接单号
		handOverBill.put("billNo", handOverDto.getHandOverBillNo());
		//新增车辆任务
		handOverBill.put("beCreateTruckTask", FossConstants.YES);
		
		//若已出发，则更新交接单状态
		//if(beDepart == 1){
			//handOverBill.put("HANDOVERBILL_STATE", "30");
		//}
		int updateCount = truckTaskDao.updateHandOverBillStateWk(handOverBill);
		if(updateCount == 1){
			LOGGER.error("[快递交接单]交接单生成任务车辆结束"+handOverDto.getHandOverBillNo());
			/**异步通知任务实体   创建通知快递系统异步通知任务的job实体*/
			TfrNotifyEntity notifyEntity=new TfrNotifyEntity();
			/**生成同步车辆任务到快递系统的异步通知任务*/
			notifyEntity.setId(UUIDUtils.getUUID());
			notifyEntity.setNotifyType(NotifyWkConstants.NOTIFY_TYPE_PUSH_PLAN_ARRIVALTIME_TO_WK);
			notifyEntity.setNotifyParam1(handOverDto.getTruckTaskDettailId());
			WKTfrBillEntity wkBillEntity=new WKTfrBillEntity();
			wkBillEntity.setHandoverBillNo(handOverDto.getHandOverBillNo());
			WKTfrBillEntity  wKTfrBillEntity =wKTfrBillDao.getWKTfrBillEntity(wkBillEntity);
			String currentUserInfo = wKTfrBillEntity.getCreateNo() + ","
					+ wKTfrBillEntity.getCreateName() + "," + wKTfrBillEntity.getCreateOrgCode();
			String paramJson = handOverDto.getHandOverBillNo() + "," + handOverDto.getOrigOrgCode();
			notifyEntity.setNotifyParam3(currentUserInfo);
			notifyEntity.setParamJson(paramJson);
			tfrNotifyService.addTfrNotifyEntity(notifyEntity);
			LOGGER.error("同步预计到达时间到悟空系统"+notifyEntity.getId());
//			LOGGER.info("生成同步快递系统车辆任务job任务数据"+notifyEntity.getId());
			/**更新本次车辆任务生成job*/
			tfrNotifyService.updateTfrNotifySuccess(createTruckNotifyEntity.getId());
		}else{
			LOGGER.error("[快递交接单]交接单生成任务车辆异常更新交接单件数不为一"+handOverDto.getHandOverBillNo());
			throw new TfrBusinessException("无效交接单");
		} 
	}

	
	
	/**
	* @description 若发车计划为空， 调用综合接口查询线路也为空，则调用快递系统查询线路信息
	* @param handOverDto
	* @param departureStandard
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年5月4日 下午7:53:18
	*/
	private DepartureStandardDto returnWkLine(TruckTaskHandOverDto handOverDto,
			DepartureStandardDto departureStandard) {
		DepartureStandardDto departureStandard2=null;
		Date baseDate=new Date();
		if(StringUtils.isBlank(handOverDto.getDeptPlanDetailId())&&departureStandard==null)
			//调用快递接口查询线路
			//departureStandard2 = null;//foss.queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
			departureStandard2 = expresslineService .queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
		return departureStandard2;
	}

	/**
	* @description 若发车计划为空， 调用综合接口查询线路等
	* @param handOverDto
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年5月4日 下午7:50:24
	*/
	private DepartureStandardDto returnComprehensiveLine(
			TruckTaskHandOverDto handOverDto) {
		DepartureStandardDto departureStandard=null;
		Date baseDate=new Date();
		if(StringUtils.isBlank(handOverDto.getDeptPlanDetailId()))
			//若发车计划为空， 调用综合接口查询线路等
			departureStandard = lineService.queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
		return departureStandard;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createTruckTaskByHandOverBillFromWk(
			TfrNotifyEntity notifyEntity) {
		/**获取交接单号*/
		String handOverBillNo=notifyEntity.getNotifyParam1();
		List<TruckTaskHandOverDto> handOverDtos = truckTaskDao.queryUnCreateTaskTruckHandOverForWk(handOverBillNo);
		if(CollectionUtils.isNotEmpty(handOverDtos)&&handOverDtos.size()> 0){
			for(TruckTaskHandOverDto handOverDto : handOverDtos){
				LOGGER.error("[快递交接单]生成任务车辆开始"+handOverDto.getHandOverBillNo());
				this.createTruckTaskFromWk(handOverDto,notifyEntity);
				LOGGER.error("[快递交接单]生成任务车辆结束"+handOverDto.getHandOverBillNo());
				/*try{
					LOGGER.error("调用交接单监控服务开始"+handOverDto.getHandOverBillNo());
					truckTaskService.handOverBillCountMonitor(handOverDto);
					LOGGER.error("调用交接单监控服务结束"+handOverDto.getHandOverBillNo());
				}catch(Exception e){
					LOGGER.error("交接单号：" + handOverDto.getHandOverBillNo() + "监控失败异常！",e);
				}*/
			}
		}else{
			throw new TfrBusinessException("无效的交接单号");
		}
		
	}
	/**
	* @description 生成车辆任务主表信息
	* @param handOverDto
	* @param truckTaskInfo
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 下午8:37:01
	*/
	private String insertTruckTask(TruckTaskHandOverDto handOverDto,DepartureStandardDto departureStandard,DepartureStandardDto departureStandard2){
		TruckTaskEntity truckTask = new TruckTaskEntity();
		truckTask.setId(handOverDto.getTruckTaskId());
		truckTask.setBusinessType(handOverDto.getHandOverType());
		truckTask.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
		truckTask.setDriverCode1(handOverDto.getDriverCode1());
		truckTask.setDriverCode2(handOverDto.getDriverCode2());
		truckTask.setDriverName1(handOverDto.getDriverName1());
		truckTask.setDriverName2(handOverDto.getDriverName2());
		truckTask.setDriverPhone1(handOverDto.getDriverPhone1());
		truckTask.setDriverPhone2(handOverDto.getDriverPhone2());
		truckTask.setOrigOrgCode(handOverDto.getOrigOrgCode());
		if(StringUtils.isNotBlank(handOverDto.getDeptPlanDetailId())){
			truckTask.setLineName(handOverDto.getLineName());
			truckTask.setLineVirtualCode(handOverDto.getLineVirtualCode());
		}else{
			//若发车计划为空， 调用综合接口查询线路等
			//Date baseDate = new Date();
			//DepartureStandardDto departureStandard = lineService.queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
			if(departureStandard != null){
				if(departureStandard.getOrder() != null){
					truckTask.setLineVirtualCode(departureStandard.getOrder().toString());
				}
				truckTask.setLineName(departureStandard.getLineName());
			}else{
				// 调用快递接口查询线路
				//DepartureStandardDto departureStandard2 = expresslineService.queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
				if(departureStandard2!=null){
					if(departureStandard2.getOrder() != null){
						truckTask.setLineVirtualCode(departureStandard2.getOrder().toString());
					}
					truckTask.setLineName(departureStandard2.getLineName());
				}
				
			}
		}
		truckTask.setVehicleNo(handOverDto.getVehicleNo());
		truckTask.setCreateTime(new Date());
		truckTask.setBeCarLoad(handOverDto.getBeCarLoad());
		truckTaskDao.insertTruckTask(truckTask);
		return truckTask.getId();
	}
	
	
	
	/**
	* @description 生成任务车辆明细开始
	* @param handOverDto
	* @param truckTaskInfo
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月26日 下午8:37:51
	*/
	private String insertTruckTaskDetail(TruckTaskHandOverDto handOverDto,DepartureStandardDto departureStandard,DepartureStandardDto departureStandard2){
		TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
		truckTaskDetail.setCreateTime(new Date());
		//未出发-UNDEPART
		truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
		//车辆任务明细id
		truckTaskDetail.setId(handOverDto.getTruckTaskDettailId());
		truckTaskDetail.setBusinessType(handOverDto.getHandOverType());
		truckTaskDetail.setDestOrgCode(handOverDto.getDestOrgCode());
		truckTaskDetail.setDestOrgName(handOverDto.getDestOrgName());
		truckTaskDetail.setOrigOrgCode(handOverDto.getOrigOrgCode());
		truckTaskDetail.setOrigOrgName(handOverDto.getOrigOrgName());
		truckTaskDetail.setBeCarLoad(handOverDto.getBeCarLoad());
		truckTaskDetail.setParentId(handOverDto.getTruckTaskId());
		truckTaskDetail.setLoaderCode(handOverDto.getLoaderCode());
		truckTaskDetail.setLoaderName(handOverDto.getLoaderName());
		//如果发车计划不为空则线路、计划出发时间、计划到达时间、线路为发车计划中相应值
		if(StringUtils.isNotBlank(handOverDto.getDeptPlanDetailId())){
			truckTaskDetail.setPlanArriveTime(handOverDto.getPlanArriveTime());
			truckTaskDetail.setPlanDepartTime(handOverDto.getPlanDepartTime());
			setRuningTime(handOverDto, truckTaskDetail);
			truckTaskDetail.setLineVirtualCode(handOverDto.getLineVirtualCode());
			truckTaskDetail.setLineName(handOverDto.getLineName());
			truckTaskDetail.setFrequecyNo(handOverDto.getFrequecyNo());
		}else{
			//若发车计划为空， 调用综合接口查询线路等
			Date baseDate = new Date();
			//DepartureStandardDto departureStandard =new DepartureStandardDto();;
			if(departureStandard != null){
				if(departureStandard.getOrder() != null){
					truckTaskDetail.setFrequecyNo(departureStandard.getOrder().toString());
					truckTaskDetail.setPlanArriveTime(departureStandard.getArriveDate(baseDate));
					truckTaskDetail.setPlanDepartTime(departureStandard.getLeaveDate(baseDate));
					
					setRuningTime(handOverDto, truckTaskDetail);
				}else{
					long commonAging = 0;
					if(departureStandard.getCommonAging() != null){
						commonAging = departureStandard.getCommonAging()*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60;
					}
					Date planArriveTime = new Date(baseDate.getTime() + commonAging);
					truckTaskDetail.setPlanArriveTime(planArriveTime);
					truckTaskDetail.setPlanDepartTime(baseDate);
					
					setRuningTime(handOverDto, truckTaskDetail);
				}
				truckTaskDetail.setLineName(departureStandard.getLineName());
				// 班次虚拟编码
				truckTaskDetail.setLineVirtualCode(departureStandard.getLineVirtualCode());
			}else{
				//调用快递接口
				//DepartureStandardDto departureStandard2 = expresslineService.queryDepartureStandardListBySourceTargetDirectly(handOverDto.getOrigOrgCode(), handOverDto.getDestOrgCode(), baseDate);
				//DepartureStandardDto departureStandard2 = new DepartureStandardDto();
				if(departureStandard2 != null){
					if(departureStandard2.getOrder() != null){
						truckTaskDetail.setFrequecyNo(departureStandard2.getOrder().toString());
						truckTaskDetail.setPlanArriveTime(departureStandard2.getArriveDate(baseDate));
						truckTaskDetail.setPlanDepartTime(departureStandard2.getLeaveDate(baseDate));
						
						setRuningTime(handOverDto, truckTaskDetail);
					}else{
						long commonAging = 0;
						if(departureStandard2.getCommonAging() != null){
							commonAging = departureStandard2.getCommonAging()*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60;
						}
						Date planArriveTime = new Date(baseDate.getTime() + commonAging);
						truckTaskDetail.setPlanArriveTime(planArriveTime);
						truckTaskDetail.setPlanDepartTime(baseDate);
						
						setRuningTime(handOverDto, truckTaskDetail);
					}
					truckTaskDetail.setLineName(departureStandard2.getLineName());
					// 班次虚拟编码
					truckTaskDetail.setLineVirtualCode(departureStandard2.getLineVirtualCode());
				}
			}
		}
		truckTaskDetail.setVehicleNo(handOverDto.getVehicleNo());
		//如果是挂牌号则该任务要填充挂牌号字段
		if(StringUtils.equals(handOverDto.getBeTrailerVehicleNo(), FossConstants.YES)){
			truckTaskDetail.setTrailerVehicleNo(handOverDto.getTrailerVehicleNo());
		}
		//调用综合接口查询车型、GPS设备号、车辆所属部门编码、车辆所属部门名称
		VehicleAssociationDto vehicleDto = truckTaskService.getVehicle(handOverDto.getVehicleNo());
		if(vehicleDto != null){
			truckTaskDetail.setTruckType(vehicleDto.getVehicleLengthName());
			truckTaskDetail.setVehicleOrgCode(vehicleDto.getVehicleMotorcadeCode());
			truckTaskDetail.setVehicleOrgName(vehicleDto.getVehicleMotorcadeName());
			truckTaskDetail.setVehicleOwnerType(vehicleDto.getVehicleOwnershipType());
		}else{
			LOGGER.info("调用综合接口查询车型、GPS设备号、车辆所属部门编码、车辆所属部门名称,查询失败"+handOverDto.getVehicleNo());
		}
		//封签、放行
		List<TruckTaskHandOverDto> truckSeals = truckTaskDao.queryVehicleSeal(truckTaskDetail);
		if(CollectionUtils.isNotEmpty(truckSeals)){
			TruckTaskHandOverDto truckSeal = truckSeals.get(0);
			truckTaskDetail.setTruckDepartId(truckSeal.getTruckDepartId());
			//若已封封签，则绑定封签
			if(StringUtils.isNotBlank(truckSeal.getSealId())){
				truckTaskDetail.setVehicleSealId(truckSeal.getSealId());
				//更新封签
				truckTaskDao.updateVehicleSeal(truckTaskDetail);
			}
			//若已放行，则绑定放行记录
			if(StringUtils.isNotBlank(truckSeal.getTruckDepartId())){
				truckTaskDetail.setTruckDepartId(truckSeal.getTruckDepartId());
				//如果出发时间不为空
				if(truckSeal.getActualDepartTime() != null){
					truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
					truckTaskDetail.setActualDepartTime(truckSeal.getActualDepartTime());
					truckTaskDetail.setActualDepartType(truckSeal.getActualDepartType());
					//修改任务车辆状态为在途
					TruckTaskEntity truckTask = new TruckTaskEntity();
					truckTask.setId(handOverDto.getTruckTaskId());
					truckTask.setState(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY);
					//若有放行ID，则将任务车辆状态更新为在途
					truckTaskDao.updateTruckTaskState(truckTask);
				}
			}
		}
		truckTaskDao.insertTruckTaskDetail(truckTaskDetail);
		//GPS设备号
		if(vehicleDto != null){
			if(StringUtils.equals(truckTaskDetail.getBusinessType(),TaskTruckConstant.BUSINESS_TYPE_SHORT_DISTANCE)) {
				TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
				truckGPSTask.setId(UUIDUtils.getUUID());
				truckGPSTask.setGpsDeviceNo(vehicleDto.getVehicleGpsNo());
				truckGPSTask.setDestOrgCode(handOverDto.getDestOrgCode());
				truckGPSTask.setOrigOrgCode(handOverDto.getOrigOrgCode());
				truckGPSTask.setTruckTaskDetailId(handOverDto.getTruckTaskDettailId());
				truckGPSTask.setVehicleNo(handOverDto.getVehicleNo());
				truckGPSTask.setOperateType(TaskTruckConstant.GPS_OPERATE_TYPE_NEW);
				truckGPSTask.setBeSuccess(FossConstants.NO);
				truckTaskDao.insertTruckGPSTask(truckGPSTask);
			}  else if(StringUtils.equals(truckTaskDetail.getBusinessType(),TaskTruckConstant.BUSINESS_TYPE_LONG_DISTANCE)) {
				if(vehicleDto.isHasGps()){
					TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
					truckGPSTask.setId(UUIDUtils.getUUID());
					truckGPSTask.setGpsDeviceNo(vehicleDto.getVehicleGpsNo());
					truckGPSTask.setDestOrgCode(handOverDto.getDestOrgCode());
					truckGPSTask.setOrigOrgCode(handOverDto.getOrigOrgCode());
					truckGPSTask.setTruckTaskDetailId(handOverDto.getTruckTaskDettailId());
					truckGPSTask.setVehicleNo(handOverDto.getVehicleNo());
					truckGPSTask.setOperateType(TaskTruckConstant.GPS_OPERATE_TYPE_NEW);
					truckGPSTask.setBeSuccess(FossConstants.NO);
					truckTaskDao.insertTruckGPSTask(truckGPSTask);
					//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
					//this.synAddTruckGPSTask(truckGPSTask, handOverDto);
					}
			}
		}
		//更新证件包
		truckTaskDao.updateCertificateBag(truckTaskDetail);
		return truckTaskDetail.getId();
	}
	
	
	
	/**
	* @description 插入任务车辆单据
	* @param handOverDto
	* @param billType
	* @param truckTaskInfo
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月27日 下午2:21:07
	*/
	private String insertTruckTaskBill(TruckTaskHandOverDto handOverDto,String billType){
		TruckTaskBillEntity truckTaskBill = new TruckTaskBillEntity();
		truckTaskBill.setId(UUIDUtils.getUUID());
		truckTaskBill.setBillNo(handOverDto.getHandOverBillNo());
		truckTaskBill.setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);
		HandOverBillEntity handOverBillEntity = handOverBillService.queryHandOverBillByNo(handOverDto.getHandOverBillNo());
		if(handOverBillEntity != null && LoadConstants.HANDOVER_TYPE_LONG_DISTANCE.equals(handOverBillEntity.getHandOverType())){
			//长途交接单交接单状态为已交接的时候单据级别为1，否则为0
			if(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER == handOverBillEntity.getHandOverBillState()){
				truckTaskBill.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
			}else{
				truckTaskBill.setBillLevel(TaskTruckConstant.BILL_LEVEL_UNVALID);
			}
		}else{
			truckTaskBill.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
		}
		truckTaskBill.setBillType(billType);
		truckTaskBill.setParentId(handOverDto.getTruckTaskDettailId());
		truckTaskBill.setBillingTime(handOverDto.getBillingTime());
		truckTaskBill.setLoadTaskNo(handOverDto.getLoadTaskNo());
		truckTaskBill.setCreateTime(new Date());
		/*if(TaskTruckConstant.BILL_TYPE_VEHICLEASSEMBLE.equals(billType)){
			
		}*/
		truckTaskDao.insertTruckTaskBill(truckTaskBill);
		return truckTaskBill.getId();
	}
	
	
	public void setRuningTime(TruckTaskHandOverDto handOverDto, TruckTaskDetailEntity truckTaskDetail) {
		Date pdt = truckTaskDetail.getPlanDepartTime();
		if(pdt == null) {
			pdt = new Date();
		}
		Date pat = truckTaskDetail.getPlanArriveTime();
		if(pat == null) {
			pat = new Date();
		}
		Long runingTimes = DateUtils.getMinuteDiff(pdt, pat);
		truckTaskDetail.setRuningTimes(runingTimes.intValue());
	}

	
	/**
	* @description FOSS系统同步车辆任务信息到快递系统接口 106162
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskForWkService#pushTruckTaskToWk(com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity)
	* @author 283250-foss-liuyi
	* @update 2016年4月29日 下午8:34:54
	* @version V1.0
	*/
	@Override
	public void pushTruckTaskToWk(TfrNotifyEntity notifyEntity) {
		TruckTaskInfoDto truckTaskInfo=new TruckTaskInfoDto();
		//查询主表信息
		String operationOrgCode=null;
		String operationOrgName=null;
		String truckType=null;
		//查询单据信息
		TruckTaskBillEntity truckTaskBill = truckTaskDao.queryTruckTaskBillById(notifyEntity.getNotifyParam3());
		String truckTaskDetailId=null;
		if(notifyEntity.getNotifyParam2()==null){
			if(truckTaskBill!=null){
				truckTaskDetailId=truckTaskBill.getParentId();
			}
		}else{
			truckTaskDetailId=notifyEntity.getNotifyParam2();
		}	
		
		//查询快递明细表信息
		TruckTaskDetailEntity truckTaskDetail=truckTaskDao.queryTruckTaskDetailById(truckTaskDetailId);
		if(truckTaskDetail!=null){
			operationOrgCode=truckTaskDetail.getOrigOrgCode();
			operationOrgName=truckTaskDetail.getOrigOrgName();
			truckType=truckTaskDetail.getTruckType();
			/**封装返回给快递系统,车辆任务明细*/
			TruckTaskDetailWkEntity truckTaskDetailWk=new TruckTaskDetailWkEntity();
			//车辆任务明细编号
			truckTaskDetailWk.setTruckTaskDetailNo(truckTaskDetail.getId());
			//车辆任务编号
			truckTaskDetailWk.setTruckTaskId(truckTaskDetail.getParentId());
			//车辆到达编号   车辆放行编号
			truckTaskDetailWk.setTruckReleaseNo(truckTaskDetail.getTruckDepartId());
			truckTaskDetailWk.setTruckDestNo(truckTaskDetail.getTruckArriveId());
			//车牌号
			truckTaskDetailWk.setVehicleNo(truckTaskDetail.getVehicleNo());
			//线路编号+名称
			truckTaskDetailWk.setLineNo(truckTaskDetail.getLineVirtualCode());
			truckTaskDetailWk.setLineName(truckTaskDetail.getLineName());
			//车辆业务类型
			truckTaskDetailWk.setBusinessType(truckTaskDetail.getBusinessType());
			//出发、到达部门编码+名称
			truckTaskDetailWk.setOrigOrgCode(truckTaskDetail.getOrigOrgCode());
			truckTaskDetailWk.setOrigOrgName(truckTaskDetail.getOrigOrgName());
			truckTaskDetailWk.setDestOrgCode(truckTaskDetail.getDestOrgCode());
			truckTaskDetailWk.setDestOrgName(truckTaskDetail.getDestOrgName());
			//出发时间、到达时间  生成时候不涉及
			//truckTaskDetailWk.setDepartTime(departTime);
			//truckTaskDetailWk.setDestTime(destTime);
			//106162 2016-08-29
			truckTaskDetailWk.setDepartTime(truckTaskDetail.getActualDepartTime());
			truckTaskDetailWk.setDestTime(truckTaskDetail.getActualArriveTime());
			//车辆明细状态
			truckTaskDetailWk.setStatus(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
			//计划出发时间、计划到达时间
			truckTaskDetailWk.setPlannedDepartTime(truckTaskDetail.getPlanDepartTime());
			truckTaskDetailWk.setPlannedDestTime(truckTaskDetail.getPlanArriveTime());
			//实际到达类型、实际到达类型 不涉及
			//车辆归属部门编码+车辆归属部门名称+车辆归属类型+班次号
			truckTaskDetailWk.setTruckOwnDeptNo(truckTaskDetail.getVehicleOrgCode());
			truckTaskDetailWk.setTruckOwnDeptName(truckTaskDetail.getVehicleOrgName());
			truckTaskDetailWk.setTruckOwnType(truckTaskDetail.getVehicleOwnerType());
			truckTaskDetailWk.setShift(truckTaskDetail.getFrequecyNo());
			//整车类型
			truckTaskDetailWk.setWholeTruckType(truckTaskDetail.getBeCarLoad());
			//车辆类型
			truckTaskDetailWk.setTruckType(truckTaskDetail.getTruckType());
			//月台编号 PLATFORM_DISTRIBUTE_ID 
			truckTaskDetailWk.setPlatformNo(truckTaskDetail.getPlatformDistributeId());
			//手工放行信息 生成不涉及
			//运行时长
			truckTaskDetailWk.setTravelDuration(new BigDecimal(truckTaskDetail.getRuningTimes()));
			//挂牌号
			truckTaskDetailWk.setBrandNo(truckTaskDetail.getTrailerVehicleNo());
			//创建时间+更新时间
			truckTaskDetailWk.setCreateTime(truckTaskDetail.getCreateTime());
			truckTaskDetailWk.setUpdateTime(truckTaskDetail.getModifyTime());
			truckTaskDetailWk.setCreateName(truckTaskDetail.getCreater());
			truckTaskInfo.setTruckTaskDetail(truckTaskDetailWk);
		}
		String truckTaskId=null;
		if(notifyEntity.getNotifyParam1()==null){
			if(truckTaskDetail!=null){
				truckTaskId=truckTaskDetail.getParentId();
			}
		}else{
			truckTaskId=notifyEntity.getNotifyParam1();
		}
		TruckTaskEntity truckTask=truckTaskDao.queryTruckTaskById(truckTaskId);
		if(truckTask!=null){
			/**封装返回给快递系统,车辆任务主表信息*/
			//truckTaskInfo.setBusinessType(businessType);
			//信息同步接口不关心操作类型
			//truckTaskInfo.setOperationType(truckTask.get);
			//车辆任务编号
			truckTaskInfo.setTruckTaskNo(truckTask.getId());
			//车辆类型
			truckTaskInfo.setTruckTaskType(truckType);
			//车牌号
			truckTaskInfo.setVehicleNo(truckTask.getVehicleNo());
			//出发部门编码
			truckTaskInfo.setLineNo(truckTask.getLineVirtualCode());
			//truckTaskInfo.setLineNo(truckTask.getLineVirtualCode());
			//车辆业务类型
			truckTaskInfo.setBusinessType(truckTask.getBusinessType());
			//主司机信息
			truckTaskInfo.setMasterDriverCode(truckTask.getDriverCode1());
			truckTaskInfo.setMasterDriverName(truckTask.getDriverName1());
			truckTaskInfo.setMasterDriverPhone(truckTask.getDriverPhone1());
			//副驾司机信息
			truckTaskInfo.setSlaveDriverCode(truckTask.getDriverCode2());
			truckTaskInfo.setSlaveDriverName(truckTask.getDriverName2());
			truckTaskInfo.setSlaveDriverPhone(truckTask.getDriverPhone2());
			/*车辆状态：未出发-UNDEPART 在途-ONTHEWAY 已到达-ARRIVED 作废-CANCLED 已卸车-UNLOADED 中途到达-HALFWAY_ARRIVE  */
			truckTaskInfo.setTruckStatus(TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART);
			//对应FOSS的“是否整车”字段
			truckTaskInfo.setTruckType(truckTask.getBeCarLoad());
			truckTaskInfo.setCreateTime(truckTask.getCreateTime());
			truckTaskInfo.setUpdateTime(truckTask.getModifyTime());
			truckTaskInfo.setOperationOrgCode(truckTask.getOrigOrgCode());
			truckTaskInfo.setOperationOrgName(operationOrgName);
		}
		
		
		/**封装返回给快递系统,车辆任务单据*/
		if(truckTaskBill!=null){
			TruckTaskBillDetailWkEntity billDetailWk=new TruckTaskBillDetailWkEntity();
			//id
			//billDetailWk.setId(truckTaskBill.getId());
			//车辆任务明细编号
			billDetailWk.setVehicleTaskDetailNo(truckTaskBill.getParentId());
			//交接单号
			billDetailWk.setHandoverBillNo(truckTaskBill.getBillNo());
			//单据级别
			billDetailWk.setBillLevel(truckTaskBill.getBillLevel());
			//分配状态
			billDetailWk.setAssignmentStatus(truckTaskBill.getAssignState());
			//创建时间
			billDetailWk.setCreateTime(truckTaskBill.getCreateTime());
			/**快递受均衡字段限制需要传输部门组织信息,单据部门信息绑定的是车辆任务明细的出发部门信息*/
			if(operationOrgCode!=null){
				billDetailWk.setOperationOrgCode(operationOrgCode);
				billDetailWk.setOperationOrgName(operationOrgName);
			}else{
				TruckTaskDetailEntity detail=truckTaskDao.queryTruckTaskDetailById(truckTaskBill.getParentId());
				billDetailWk.setOperationOrgCode(detail.getOrigOrgCode());
				billDetailWk.setOperationOrgName(detail.getOrigOrgName());
			}
			List<TruckTaskBillDetailWkEntity> list=new ArrayList<TruckTaskBillDetailWkEntity>();
			list.add(billDetailWk);
			truckTaskInfo.setTruckTaskBillDetailList(list);
		}
		
		//同步数据到快递系统
		TruckTaskInfoResponseDto respons=fossToWkService.pushTruckTaskToWk(truckTaskInfo);
		if(respons.getBeSuccess()){
			//成功就更新通知状态
			tfrNotifyService.updateTfrNotifySuccess(notifyEntity.getId());
		}else{
			//失败就排除带原因异常
			throw new RuntimeException(respons.getFailureReason());
		}
		
	}

	
	/**
	* @description FOSS系统更新车辆任务信息到快递系统接口
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskForWkService#updateTruckTaskToWk(com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity)
	* @author 283250-foss-liuyi
	* @update 2016年4月30日 上午11:44:49
	* @version V1.0
	*/
	@Override
	public void updateTruckTaskToWk(TfrNotifyEntity notifyEntity) {
		LOGGER.error("updateTruckTaskToWk方法开始" + notifyEntity);
		String notifyType=notifyEntity.getNotifyType();
		UpdateWkHandOverBillEntity entity=new UpdateWkHandOverBillEntity();
		/**类型转换*/
		//1、发车确认；2、取消发车；3、到达确认；4、取消到达； 6卸车
		String operationType="";
		if(NotifyWkConstants.NOTIFY_TYPE_TRUCK_DEPARTURE.
				equals(notifyType)){
			operationType="1";
		}else if(NotifyWkConstants.NOTIFY_TYPE_CANCEL_TRUCK_DEPARTURE.
				equals(notifyType)){
			operationType="2";
		}else if(NotifyWkConstants.NOTIFY_TYPE_TRUCK_ARRIVAL.
				equals(notifyType)){
			operationType="3";
		}else if(NotifyWkConstants.NOTIFY_TYPE_CANCEL_TRUCK_ARRIVAL.
				equals(notifyType)){
			operationType="4";
		}else if(NotifyWkConstants.NOTIFY_TYPE_UNLOAD_UPDATE.
				equals(notifyType)){
			operationType="6";
		} else if (NotifyWkConstants.NOTIFY_TYPE_PUSH_PLAN_ARRIVALTIME_TO_WK.
				equals(notifyType)) {
			operationType = "7";
		}
		//设置请求类型 
		entity.setOperationType(operationType);

		// 取得车辆任务明细ID
		String truckTaskDetailID = notifyEntity.getNotifyParam1();
		//根据车辆任务明细ID查询出发时间到达时间
		TruckTaskDetailEntity truckTaskDetail=truckTaskDao.queryTruckTaskDetailById(truckTaskDetailID);
		if(truckTaskDetail!=null){
			//设置出发时间
			if (truckTaskDetail.getActualDepartTime() == null) {
				entity.setDepartTime(new Date());
			} else {
				entity.setDepartTime(truckTaskDetail.getActualDepartTime());
			}
			//设置到达时间
			if (truckTaskDetail.getActualArriveTime() == null) {
				entity.setArriveTime(new Date());
			} else {
				entity.setArriveTime(truckTaskDetail.getActualArriveTime());
			}
			//设置预计到达时间
			if (truckTaskDetail.getPlanArriveTime() == null || "".equals(truckTaskDetail.getPlanArriveTime())) {
				entity.setPlanArriveTime(new Date());
			} else {
				entity.setPlanArriveTime(truckTaskDetail.getPlanArriveTime());
			}
		}
		
		
		//设置推送的交接单号和出发部门
		List<WkHandOverBillEntity> list = new ArrayList<WkHandOverBillEntity>();
		WkHandOverBillEntity wkHandOverBillEntity = new WkHandOverBillEntity();
		
		//解析交接单和交接单的出发部门
		String paramJson = notifyEntity.getParamJson();
		/**交接单和交接单的出发部门  params[0]交接单，  params[1]出发部门 */
		//接收解析数组
		String[] params = null;
		if (paramJson != null) {
			params=paramJson.split(",");
			wkHandOverBillEntity.setHandoverBillNo(params[0]);
			wkHandOverBillEntity.setOperationOrgCode(params[1]);
		}
		list.add(wkHandOverBillEntity);
		entity.setTruckHandoverBillList(list);
		
		/*交接单状态*/
		entity.setHandoverState(notifyEntity.getNotifyParam2());
		
		//解析交接单和交接单的出发部门
		String currentUserInfo = notifyEntity.getNotifyParam3();
		/**操作人工号和操作人名称和当前操作部门编码分割  params[0]操作人工号，  params[1]操作人名称 ,params[2]当前操作部门*/
		//接收解析数组
		String[] currentInfo = null;
		if (currentUserInfo != null) {
			currentInfo=currentUserInfo.split(",");
			/*操作人工号*/
			entity.setOperatorCode(currentInfo[0]);
			/*操作人名称*/
			entity.setOperatorName(currentInfo[1]);
			String orgCode = currentInfo[2];
			/*当前操作部门编码*/
			entity.setOrgCode(orgCode);
			/*当前操作部门名称*/
			entity.setOrgName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(orgCode));
		}
		
		Log.error("同步交接单数据给悟空开始...");
		
		//同步数据到快递系统
		Map<String, Object> result = fossToWkService.editHandOverBillToWk(JSON.toJSONString(entity));
		String status = null;
		if (result == null || !result.containsKey("status") || result.get("status") == null) {
			throw new TfrBusinessException("ECS - 同步交接单数据失败");
		}
		
		status = result.get("status").toString();
		if (!status.equals("1")) {
			throw new RuntimeException(result.get("exMsg").toString());
		} else {
			//成功就更新通知状态
			tfrNotifyService.updateTfrNotifySuccess(notifyEntity.getId());
		}
		
		Log.error("同步交接单数据给悟空结束...");
		
	}
	
	/**
	* @description 快递系统交接单作废，触发车辆任务作废
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskForWkService#deleteTruckTaskByWkHandOverBill(java.lang.String)
	* @author 283250-foss-liuyi
	* @update 2016年5月12日 上午10:33:06
	* @version V1.0
	*/
	@Override
	@Transactional
	public int deleteTruckTaskByWkHandOverBill(String billNo){
		/**异步通知任务实体   创建通知快递系统异步通知任务的job实体*/
//		TfrNotifyEntity notifyEntity=new TfrNotifyEntity();
//		notifyEntity.setId(UUIDUtils.getUUID());
//		notifyEntity.setNotifyType(NotifyWkConstants.NOTIFY_TYPE_DELETE_TRUCK_TASK);
		
		//根据交接单查询任务车辆ID，任务车辆明细ID
		TruckTaskHandOverDto truckTask = truckTaskDao.queryTruckTaskIdByHandOverBill(billNo);
		//查询任务主表数据，后面会用到出发部门Code
//		TruckTaskEntity task = truckTaskDao.queryTruckTaskById(truckTask.getTruckTaskId());
		//任务车辆id不为空
		//任务车辆明细不为空
		if(!(truckTask != null&&
				StringUtils.isNotBlank(truckTask.getTruckTaskId()) 
				&& StringUtils.isNotBlank(truckTask.getTruckTaskDettailId())))
			return  0;
			
		//任务车辆id
		String truckTaskId = truckTask.getTruckTaskId();
		//任务车辆明细id
		String truckTaskDetailId = truckTask.getTruckTaskDettailId();
		//任务车辆id不为空
		/**满足快递均衡字段需要传车牌号、出发部门信息   
		 * 车牌号,出发部门编码,交接单号
		 * */
//		StringBuffer paramBig=new StringBuffer();
//		paramBig.append(truckTask.getVehicleNo()==null?"null":truckTask.getVehicleNo()).
//			append(",").append(truckTask.getOrigOrgCode()==null?"null":truckTask.getOrigOrgCode())
//			.append(",").append(billNo).append(",").append(task.getOrigOrgCode());
//		notifyEntity.setParamJson(paramBig.toString());
		
		truckTaskDao.selectBillForUpdateByTruckTaskId(truckTaskId);
		//查询装车任务中单据数 
		if(truckTaskDao.queryBillCountByTruckTask(truckTaskId)>1){
			//查询装车任务明细中单据数
			if(truckTaskDao.queryBillCountByTruckTaskDetail(truckTaskDetailId)<=1){
				//删除任务车辆明细
				truckTaskDao.deleteTruckTaskDetail(truckTaskDetailId);
				if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
					String truckGPSTaskId = truckTask.getTruckGPSTaskId();
					//GPS任务列表
					TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
					truckGPSTask.setId(truckGPSTaskId);
					truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
					truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
					//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
					//this.synDeleteTruckGPSTask(truckGPSTask);
				}
				/**设置待删除车辆任务明细id*/
//				notifyEntity.setNotifyParam2(truckTaskDetailId);
			}
		}else{
			// 删除任务车辆
			truckTaskDao.deleteTruckTask(truckTaskId);
			// 删除任务车辆明细
			truckTaskDao.deleteTruckTaskDetail(truckTaskDetailId);
			if(StringUtils.isNotBlank(truckTask.getTruckGPSTaskId())){
				String truckGPSTaskId = truckTask.getTruckGPSTaskId();
				TruckGPSTaskEntity truckGPSTask = new TruckGPSTaskEntity();
				truckGPSTask.setId(truckGPSTaskId);
				//同步到gps操作类型 -删除
				truckGPSTask.setOperateType(TaskTruckConstant.GSP_OPERATE_TYPE_DELETE);
				truckTaskDao.deleteTruckTaskGPSDetail(truckGPSTaskId);
				//调用gps接口，同步跟踪车辆数据,同步成功，则更新待跟踪gps信息为成功，否则更新待跟踪gps信息为失败
				//this.synDeleteTruckGPSTask(truckGPSTask);
			}
//			/**设置待删除车辆任务id*/
//			notifyEntity.setNotifyParam1(truckTaskId);
//			/**设置待删除车辆任务明细id*/
//			notifyEntity.setNotifyParam2(truckTaskDetailId);
		}
//		if(StringUtils.isNotBlank(notifyEntity.getNotifyParam2())){
//			/**生成同步车辆任务到快递系统的异步通知任务   交接单撤销foss删除车辆任务信息 同时通知快递系统删除车辆任务信息*/
//			tfrNotifyService.addTfrNotifyEntity(notifyEntity);
//			LOGGER.error("快递作废交接单,生成车辆任务更新异步通知任务:"+billNo+"||"+notifyEntity.getId());
//		}
		//删除任务车辆单据
		return truckTaskDao.deleteTruckTaskBill(billNo);
	}

	
	/**
	* @description 快递系统交接单更新,触发车辆任务更新(车牌号信息变更时候才调用)
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskForWkService#updateTruckTaskByWkHandOverBill(java.lang.String)
	* @author 283250-foss-liuyi
	* @update 2016年5月12日 下午8:14:18
	* @version V1.0
	*/
	@Override
	@Transactional
	public void updateTruckTaskByWkHandOverBill(String billNo,TruckTaskHandOverDto truckTask) {
		/*
		 * this.deleteTruckTaskByWkHandOverBill(handOverBillNo);
		 *//** 更新交接单状态 */
		/*
		 * Map<String,String> handOverBill = new HashMap<String,String>();
		 * //交接单号 handOverBill.put("billNo",handOverBillNo); //新增车辆任务
		 * handOverBill.put("beCreateTruckTask", FossConstants.NO); int
		 * updateCount = truckTaskDao.updateHandOverBillStateWk(handOverBill);
		 * if(updateCount>0){
		 *//** 创建生成车辆任务的异步任务 *//*
								 * 
								 * TfrNotifyEntity notify=new
								 * TfrNotifyEntity(UUIDUtils.getUUID(),
								 * NotifyWkConstants.
								 * NOTIFY_TYPE_CREATE_TRUCK_TASK,
								 * handOverBillNo,null,null);
								 * tfrNotifyService.addTfrNotifyEntity(notify);
								 * LOGGER.info("快递发起更新交接单,生成更新车辆任务异步通知任务:"+
								 * notify.getId()); }
								 */

		// 删除任务车辆
//		TruckTaskEntity task = truckTaskDao.queryTruckTaskById(truckTask.getTruckTaskId());

		int del = truckTaskService.deleteTruckTaskByHandOverBill(billNo);

		//推送删除给ECS系统， 给JOB用
		if (del > 0) {
//			String parmartJson = truckTask.getVehicleNo()+","+truckTask.getOrigOrgCode() + "," + billNo
//					+ "," + task.getOrigOrgCode();
//			tfrNotifyService.addTfrNotifyEntity(
//					new TfrNotifyEntity(UUIDUtils.getUUID(), NotifyWkConstants.NOTIFY_TYPE_DELETE_TRUCK_TASK,
//							truckTask.getTruckTaskId(), truckTask.getTruckTaskDettailId(), null, parmartJson));
		}

		/** 更新交接单状态 */
		Map<String, String> params = new HashMap<String, String>();
		// 交接单号
		params.put("billNo", billNo);
		// 新增车辆任务
		params.put("beCreateTruckTask", FossConstants.NO);
		int create = truckTaskDao.updateHandOverBillStateWk(params);

		if (create > 0) {
			TfrNotifyEntity notify = new TfrNotifyEntity(UUIDUtils.getUUID(),
					NotifyWkConstants.NOTIFY_TYPE_CREATE_TRUCK_TASK, billNo, null, null);
			tfrNotifyService.addTfrNotifyEntity(notify);
			LOGGER.info("快递发起更新交接单,生成更新车辆任务异步通知任务:" + notify.getId());
		}
		
	}
}

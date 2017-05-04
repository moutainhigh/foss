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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskDao.java
 *  
 *  FILE NAME          :IStTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.service.impl;
import java.io.IOException;
/**
 * 引入包
 */
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 * 引入包
 */
import java.util.Calendar;
import java.util.Date;
/**
 * 引入包
 */
import java.util.HashMap;
import java.util.Iterator;
/**
 * 引入包
 */
import java.util.List;
/**
 * 引入包
 */
import java.util.Map;

/**
 * 引入包
 */
import org.apache.commons.collections.CollectionUtils;
/**
 * 引入包
 */
import org.apache.commons.lang.StringUtils;
/**
 * 引入包
 */
import org.apache.commons.lang.math.NumberUtils;
import org.jfree.util.Log;import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * 引入包
 */
import org.springframework.transaction.annotation.Propagation;
/**
 * 引入包
 */
import org.springframework.transaction.annotation.Transactional;

/**
 * 引入包
 */
import com.deppon.foss.base.util.define.BizTypeConstants;
/**
 * 引入包
 */
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBranchSalesDeptService;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoEmployeeService;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgAdministrativeInfoEmployeeDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.load.api.shared.exception.AssignLoadTaskExceptionCode;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCancelAssignUnloadInstructDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCreateAssignUnloadInstructDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressUnloadcmdAssignDetailDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.TruckTaskDetailDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.exception.AssignUnloadTaskExceptionCode;
import com.deppon.foss.module.transfer.unload.api.shared.vo.AssignUnloadTaskVo;
import com.deppon.foss.module.transfer.unload.api.tools.UnloadCommonUtils;
/**
 * 引入包
 */
import com.deppon.foss.util.DateUtils;
/**
 * 引入包
 */
import com.deppon.foss.util.UUIDUtils;
/**
 * 引入包
 */
import com.deppon.foss.util.define.FossConstants;


/**
 * 本来接送货车辆中的单据和中转车辆中的单据是完全分开的，
 * 但是后来接送货的转货归到到达车辆单据表里，所以类型为DELIVER的要做两部分事情.
 *
 * @author dp-duyi
 * @date 2012-10-18 下午5:23:40
 */
public class AssignUnloadTaskService implements IAssignUnloadTaskService{
	//分配卸车任务dao
	/**
	 *  The assign unload task dao. 
	 */
	private IAssignUnloadTaskDao assignUnloadTaskDao;
	private ITruckTaskService truckTaskService;
	private IExpressBranchSalesDeptService expressBranchSalesDeptService;
	
	private IVehicleSealService vehicleSealService;
	
	private ITfrNotifyService tfrNotifyService;
	
	/**
	 * @param vehicleSealService the vehicleSealService to set
	 */
	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}
	/**
	 * Sets the assign unload task dao.
	 *
	 * @param assignUnloadTaskDao the new assign unload task dao
	 */
	public void setAssignUnloadTaskDao(IAssignUnloadTaskDao assignUnloadTaskDao){
		this.assignUnloadTaskDao = assignUnloadTaskDao;
	}
	/** 
	 * The org administrative info complex service. 
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/** 
	 * The org administrative info employee service.
	  */
	private IOrgAdministrativeInfoEmployeeService orgAdministrativeInfoEmployeeService;
	/**
	 * Sets the org administrative info complex service.
	 *
	 * @param orgAdministrativeInfoComplexService the new org administrative info complex service
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * Sets the org administrative info employee service.
	 *
	 * @param orgAdministrativeInfoEmployeeService the new org administrative info employee service
	 */
	public void setOrgAdministrativeInfoEmployeeService(IOrgAdministrativeInfoEmployeeService orgAdministrativeInfoEmployeeService) {
		this.orgAdministrativeInfoEmployeeService = orgAdministrativeInfoEmployeeService;
	}
	public final void setExpressBranchSalesDeptService(
			IExpressBranchSalesDeptService expressBranchSalesDeptService) {
		this.expressBranchSalesDeptService = expressBranchSalesDeptService;
	}
	/** 
	 * The employee service.
	  */
	private IEmployeeService employeeService;
	/**
	 * Sets the employee service.
	 *
	 * @param employeeService the new employee service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/** The platform service. */
	private IPlatformService platformService;
	/**
	 * Sets the platform service.
	 *
	 * @param platformService the new platform service
	 */
	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}
	/** 
	 * The org administrative info service. 
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * Sets the org administrative info service.
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	private IOutfieldService outfieldService;
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	/** 
	 * 数据字典接口
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}
	
	//duhao-276198-20151023
	private ITfrJobTodoService tfrJobTodoService;
	
	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	
	/**
	 * @Title: getChildDept 
	 * @Description: 获取当前部门下所有的子部门 
	 * @param orgCode
	 * @return    
	 * @return List<String>    返回类型 
	 * getChildDept
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-16上午10:56:30
	 */
	private List<String> getChildDept(String orgCode) {
		if(StringUtils.isEmpty(orgCode)) {
			return null;
		}
		//根据部门编码获取所属及下属部门信息 此部门及下属的所有部门。
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfos = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoEntityAllSubByCode(orgCode);
		List<String> orgCodes = new ArrayList<String>(orgAdministrativeInfos.size());
		for(OrgAdministrativeInfoEntity orgAdministrativeInfo : orgAdministrativeInfos) {
			orgCodes.add(orgAdministrativeInfo.getCode());
		}
		//返回部门code
		return orgCodes;
	}
	
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//空运总调类型
		bizTypesList.add(BizTypeConstants.ORG_AIR_DISPATCH);
		//营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			//获取上级部门失败
			throw new TfrBusinessException("获取上级部门失败, 无上级部门");
		}
	}
	
	/**
	 * 新增分配记录.
	 *
	 * @param loader the loader
	 * @param vehicle the vehicle
	 * @param bills the bills
	 * @return the int
	 * @author dp-duyi
	 * @date 2012-10-18 下午5:27:14
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#insertAssignUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto, com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveVehicleDto, java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int insertAssignUnloadTask(LoaderDto loader,	AssignUnloadTaskTotalDto vehicle, List<ArriveBillDto> bills) {
		if(null != loader && StringUtils.isNotBlank(loader.getLoaderCode()) &&
		   null != vehicle && StringUtils.isNotBlank(vehicle.getVehicleNo()) &&
			CollectionUtils.isNotEmpty(bills)){
			UserEntity user = FossUserContext.getCurrentUser();
			List<String> taskNos = assignUnloadTaskDao.queryUnfinishedTask(loader.getLoaderCode(),vehicle.getVehicleNo());
			//营业部交接不校验车辆信息
			if(!StringUtils.equals(vehicle.getUnloadType(),UnloadConstants.UNLOAD_TASK_TYPE_DEPARTMENT)){
				if(CollectionUtils.isNotEmpty(taskNos)){
					StringBuilder sb = new StringBuilder();
					for(String taskNo : taskNos){
						sb.append(taskNo);
						sb.append("/");
					}
					throw new TfrBusinessException("该车辆有未完成的卸车任务("+sb.toString()+"),请提交一下");
				}
			}
			
			//查询分配的单据状态
			List<ArriveBillDto> billsState;
			if(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(vehicle.getUnloadType())){
				//接送货
				List<ArriveBillDto> pickUpBillsState = assignUnloadTaskDao.queryPickUpBillState(bills);
				//转货
				List<ArriveBillDto> transshipBillsState = assignUnloadTaskDao.queryTransferBillState(bills);
				billsState = new ArrayList<ArriveBillDto>();
				if(CollectionUtils.isNotEmpty(pickUpBillsState)){
					billsState.addAll(pickUpBillsState);
				}
				if(CollectionUtils.isNotEmpty(transshipBillsState)){
					billsState.addAll(transshipBillsState);
				}
			}else if(UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS.equals(vehicle.getUnloadType()) ||
					UnloadConstants.UNLOAD_TASK_TYPE_DRIVER.equals(vehicle.getUnloadType())
					){//2015.02.02chenmingyan新增：快递集中卸车
				billsState = assignUnloadTaskDao.queryExpressBillState(bills);
			}else if(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(vehicle.getUnloadType())){
				//2015/8/25 272681 卸车类型是商务专递查询单据状态
				billsState = assignUnloadTaskDao.queryAirBusinessBillState(bills);
			}else if(UnloadConstants.UNLOAD_ELECTRANSPORT.equals(vehicle.getUnloadType())){
				//2016-7-26 322610 零担电子面单类型
				billsState = assignUnloadTaskDao.queryElecTransportBillState(bills);
			}else{
				billsState = assignUnloadTaskDao.queryTransferBillState(bills);
			}
			if(CollectionUtils.isEmpty(bills) || CollectionUtils.isEmpty(billsState)){
				throw new TfrBusinessException(AssignUnloadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
			}else{
				if(bills.size() != billsState.size()){
					throw new TfrBusinessException(AssignUnloadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
				}
				List<AssignUnloadTaskDto> tasks = new ArrayList<AssignUnloadTaskDto>();
				AssignUnloadTaskDto task = new AssignUnloadTaskDto();
				int i=0;
				for(ArriveBillDto item : billsState){
					//如果单据级别为0，或分配状态为已分配，则抛出异常
					if((StringUtils.isNotBlank(item.getBillLevel()) && TaskTruckConstant.BILL_LEVEL_UNVALID.equals(item.getBillLevel())) || TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED.equals(item.getAssignState())){
						throw new TfrBusinessException(AssignUnloadTaskExceptionCode.BILLBEASSIGNED_MESSAGECODE);
					}else{
						// 更新单据状态为已分配
						int updateQty = 0;
						if(item.getBillType().equals(TransferPDADictConstants.UNLOAD_ORDER_TYPE_HANDOVER) || item.getBillType().equals(TransferPDADictConstants.UNLOAD_ORDER_TYPE_VEHICLEASSEMBLE)){
							updateQty = assignUnloadTaskDao.updateArriveTransferBillByState(item.getBillNo(), TaskTruckConstant.BILL_LEVEL_VALID, TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN, TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED);
						}else if(item.getBillType().equals(UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS)  ||
								UnloadConstants.UNLOAD_TASK_TYPE_DRIVER.equals(item.getBillType())
								){//chenmingyan2015.02.02快递集中交接
							updateQty = assignUnloadTaskDao.updateArriveExpressBillByState(item.getBillNo(), TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN, TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED);
							if(item.getBillType().equals(UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS)){
								int count = 0;
								count = assignUnloadTaskDao.isExistAllotedUnload(item.getBillNo());
								if(count==0){
									//将装车任务轨迹生，插入到todo表中duhao-276198-20151019TODO查询卸车任务，如果存在就不推
									tfrJobTodoService.addJobTodo(item.getBillNo(),
											BusinessSceneConstants.BUSINESS_SCENE_TRUCK_STRAIGHT_ARRIVAL,
											new String[]{BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO}, 
											new Date(), 
											user.getUserName());
								}
							}
						}else if(item.getBillType().equals(UnloadConstants.BILL_TYPE_AIR_HANDOVERTYPE)){
							//272681  商务专递更新单据状态
							updateQty = assignUnloadTaskDao.updateArriveBusinessAirBillByState(item.getBillNo(),TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN, TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED);
						}else if(item.getBillType().equals(TransferPDADictConstants.UNLOAD_ORDER_TYPE_ELECBILL)){
							//322610  零担电子运单更新单据状态
							updateQty = assignUnloadTaskDao.updateElecTransportBillByState(item.getBillNo(),TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN, TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED);
						}else{
							updateQty = assignUnloadTaskDao.updateArrivePickUpBillByState(item.getBillNo(), TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN, TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED);
						}
						if(updateQty != 1&&!UnloadCommonUtils.isExpressHandOver(item.getBillNo())){
							throw new TfrBusinessException("单据:"+item.getBillNo()+"状态已变化，请重新查询");
						}
						task.setId(UUIDUtils.getUUID());
						task.setBeCanceled(FossConstants.INACTIVE);
						task.setState(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_UNSTART);
						task.setLoader(loader);
						//bills.get(i).setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_ASSIGNED);
						task.setBill(bills.get(i));
						if(vehicle != null && StringUtils.isNotBlank(vehicle.getPlatformNo())){
							try{
								String currentOutFieldCode = getCurrentOutfieldCode();
								if(StringUtils.isNotBlank(currentOutFieldCode)){
									PlatformEntity plateform = platformService.queryPlatformByCode(currentOutFieldCode, vehicle.getPlatformNo());
									if(plateform != null){
										vehicle.setPlatformVirtualCode(plateform.getVirtualCode());
									}else{
										//非法月台
										throw new TfrBusinessException(AssignUnloadTaskExceptionCode.INVALID_PLATEFORM_MESSAGECODE);
									}
								}else{
									//非法月台
									throw new TfrBusinessException(AssignUnloadTaskExceptionCode.INVALID_PLATEFORM_MESSAGECODE);
								}
							}catch(Exception e){
								//非法月台
								throw new TfrBusinessException(AssignUnloadTaskExceptionCode.INVALID_PLATEFORM_MESSAGECODE);
							}
						}
						task.setVehicle(vehicle);
						task.setCreateTime(DateUtils.convert(Calendar.getInstance().getTime(), DateUtils.DATE_TIME_FORMAT));
						task.setModifyTime(DateUtils.convert(Calendar.getInstance().getTime(), DateUtils.DATE_TIME_FORMAT));
						OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
						task.setCreateOrgCode(currentDept.getCode());
						task.setCreateOrgName(currentDept.getName());
						task.setCreateUserCode(user.getUserName());
						task.setCreateUserName(user.getEmpName());
						task.setModifyUserCode(user.getUserName());//工号，前台问题name存工号
						task.setModifyUserName(user.getEmpName());
						tasks.add(task);
						task = new AssignUnloadTaskDto();
						i++;
					}
				}
				/*//更新单据状态为已分配
				if(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(vehicle.getUnloadType())){
					int updateQty = 0;
					//更新接送货部分
					assignUnloadTaskDao.updateArrivePickUpBillState(bills);
					//更新转货部分
					assignUnloadTaskDao.updateArriveTransferBillState(bills);
				}else{
					assignUnloadTaskDao.updateArriveTransferBillState(bills);
				}*/
				
				int result=assignUnloadTaskDao.insert(tasks);
				
				/**新增批量更新快递交接单状态 328768*/
				List<AssignUnloadTaskDto> newTasks=new ArrayList<AssignUnloadTaskDto>();
				for(AssignUnloadTaskDto assignUnloadTaskDto:tasks){
						if(UnloadCommonUtils.isExpressHandOver(assignUnloadTaskDto.getBill().getBillNo())){
							assignUnloadTaskDto.getBill().setAssignState(UnloadConstants.EXPRESS_BILL_STATE_ASSIGNED);
							newTasks.add(assignUnloadTaskDto);
						}
				}
				//修改快递交接单分配状态
				assignUnloadTaskDao.update(newTasks);
				
				//插入分配记录
				return result;
			}
		}else{
			throw new TfrBusinessException(AssignUnloadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
		}
	}
	/**
	 * 查询到达车辆：不查询业务类型为偏线的到达车辆.
	 *
	 *
	 *
	 *
	 * @param vehicleQC the vehicle qc
	 * 
	 * @return the list
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-10-19 下午2:27:18
	 * 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#queryArriveVehicle(com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveVehicleDto, int, int)
	 */
	@Override
	public List<AssignUnloadTaskTotalDto> queryArriveVehicle(AssignUnloadTaskTotalDto vehicleQC) {
		List<AssignUnloadTaskTotalDto> oldVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		List<AssignUnloadTaskTotalDto> pickUpVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		List<AssignUnloadTaskTotalDto> transferVehicles;
		//商务专递 272681
		List<AssignUnloadTaskTotalDto> businessVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		//快递集中交接
		List<AssignUnloadTaskTotalDto> transferExpressVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		//零担电子面单
		List<AssignUnloadTaskTotalDto> elecTransportVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		List<String> destOrgCodes = new ArrayList<String>();
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
			return null;
		}
		if(vehicleQC.getUnloadType().equals("ALL")){
			vehicleQC.setUnloadType("");
		}
		if(vehicleQC.getProductCode().equals("ALL")){
			vehicleQC.setProductCode("");
		}
		//如果营业部则返回营业部下面人员
		if("Y".equals(loginOrg.getSalesDepartment())){
			//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
			ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
			branchSalesDeptEntity.setSalesDeptCode(loginOrg.getCode());
			ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
					queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
			if(branchSalesDept !=null){
				destOrgCodes.add(branchSalesDept.getExpressBranchCode());
			} 
			//当前登录部门
			destOrgCodes.add(loginOrg.getCode());
			vehicleQC.setDestOrgCodes(destOrgCodes);
			transferVehicles  = assignUnloadTaskDao.queryArriveTransferVehicle(vehicleQC);
			if(vehicleQC.getDepartOrg().equals("")){
				if(vehicleQC.getUnloadType().equals("") ||
						vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER)){
					destOrgCodes.add("N/A");
					vehicleQC.setDestOrgCodes(destOrgCodes);
					pickUpVehicles = assignUnloadTaskDao.queryArrivePickUpVehicle(vehicleQC);
				}
			}
			if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS)){
				//查询到达本部门的快递集中车辆2015.02.03chenmingyan
				transferExpressVehicles = assignUnloadTaskDao.queryArriveExpressVehicle(vehicleQC);
			}
			//商务专递272681
			if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
				businessVehicles = assignUnloadTaskDao.queryArriveBusinessVehicle(vehicleQC);
			}
		}else{
			String currentOutfield;
			try{
				currentOutfield = getCurrentOutfieldCode();
			}catch(TfrBusinessException e){
				return null;
			}
			//查询外场到达车辆
			if(StringUtils.isNotBlank(currentOutfield)){
				destOrgCodes.add(currentOutfield);
				//查询空运总调到达车辆
				OutfieldEntity outfieldEntity= outfieldService.queryOutfieldByOrgCode(currentOutfield);
				if(outfieldEntity != null){
					if(StringUtils.isNotBlank(outfieldEntity.getAirDispatchCode())){
						destOrgCodes.add(outfieldEntity.getAirDispatchCode());
					}
				}
				vehicleQC.setDestOrgCodes(destOrgCodes);
				transferVehicles = assignUnloadTaskDao.queryArriveTransferVehicle(vehicleQC);
				if(vehicleQC.getDepartOrg().equals("")){
					if(vehicleQC.getUnloadType().equals("") ||
							vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER)){
						List<String> pickUpDestOrgCodes = new ArrayList<String>();
						pickUpDestOrgCodes.add(currentOutfield);
						pickUpDestOrgCodes.add("N/A");
						vehicleQC.setDestOrgCodes(pickUpDestOrgCodes);
						pickUpVehicles = assignUnloadTaskDao.queryArrivePickUpVehicle(vehicleQC);
					}
				}
				if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS)){
					//查询到达本部门的快递集中车辆2015.02.03chenmingyan
					transferExpressVehicles = assignUnloadTaskDao.queryArriveExpressVehicle(vehicleQC);
				}
				//商务专递272681
				if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
					businessVehicles = assignUnloadTaskDao.queryArriveBusinessVehicle(vehicleQC);
				}
				/***可以根据航空正单号分配卸车任务***/

				//零担电子面单322610
				if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_ELECTRANSPORT)){
					elecTransportVehicles = assignUnloadTaskDao.queryElecTransportVehicle(vehicleQC);
				}
			}else{
				return null;
			}
		}
		if(CollectionUtils.isNotEmpty(pickUpVehicles)){
			oldVehicles.addAll(pickUpVehicles);
		}
		if(CollectionUtils.isNotEmpty(transferVehicles)){
			oldVehicles.addAll(transferVehicles);
		}
		//快递集中车辆
		if(CollectionUtils.isNotEmpty(transferExpressVehicles)){
			oldVehicles.addAll(transferExpressVehicles);
		}
		//商务专递272681
		if(CollectionUtils.isNotEmpty(businessVehicles)){
			oldVehicles.addAll(businessVehicles);
		}
		//零担电子面单322610
		if(CollectionUtils.isNotEmpty(elecTransportVehicles)){
			oldVehicles.addAll(elecTransportVehicles);
		}
		if(CollectionUtils.isNotEmpty(oldVehicles)){
			for(int i=0; i <= oldVehicles.size()-1;i++) {
				AssignUnloadTaskTotalDto vehicle = oldVehicles.get(i);
	            for( int j = oldVehicles.size() - 1 ; j > i; j --) {
	            	AssignUnloadTaskTotalDto tempVehicle = oldVehicles.get(j);
	            	if (StringUtils.equals(vehicle.getVehicleNo(), tempVehicle.getVehicleNo())
	                        && vehicle.getArriveTime()!=null
	                        && tempVehicle.getArriveTime()!=null
	                        &&vehicle.getArriveTime().equals(tempVehicle.getArriveTime())) {
		                	vehicle.setUnloadType(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER);
		                	vehicle.setWeightTotal(vehicle.getWeightTotal() + tempVehicle.getWeightTotal());
		                	vehicle.setVolumeTotal(vehicle.getVolumeTotal() + tempVehicle.getVolumeTotal());
		                	vehicle.setFastWayBillQtyTotal(vehicle.getFastWayBillQtyTotal() + tempVehicle.getFastWayBillQtyTotal());
		                	vehicle.setGoodsQtyTotal(vehicle.getGoodsQtyTotal() + tempVehicle.getGoodsQtyTotal());
		                	vehicle.setExpressWayBillQty(vehicle.getExpressWayBillQty()+tempVehicle.getExpressWayBillQty());
		                	oldVehicles.remove(j);
		                }
	            }
	            if(StringUtils.isBlank(vehicle.getVehicleType())){
	            	VehicleAssociationDto vehicleDto = truckTaskService.getVehicle(vehicle.getVehicleNo());
	            	if(vehicleDto != null){
	            		oldVehicles.get(i).setVehicleType(vehicleDto.getVehicleLengthName());
	            	}
	            }
	        }
		}
		
		return oldVehicles;
	}
	/**
	 * 
	 * 查询到达单据：
	 * 
	 * 1、不查询业务类型为偏线的到达车辆
	 * 
	 * 2、卸车中、完成卸车的单据状态为已分配.
	 *
	 *
	 *
	 *
	 * @param bill the bill
	 * 
	 * @return the list
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-10-19 下午2:27:18
	 * 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#queryArriveBill(com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto)
	 */
	@Override
	public List<ArriveBillDto> queryArriveBill(ArriveBillDto bill) {
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
			return null;
		}
		//去数据字典中拿 20150506 wqh
		String isPrePlatform=assignUnloadTaskDao.queryDictionaryValueNameByValueCode(UnloadConstants.TFR_UNLOAD_PRE_PLAMFORM_VALE);
		//如果营业部则返回营业部下面人员
		if("Y".equals(loginOrg.getSalesDepartment())){
			List<ArriveBillDto> bills = new ArrayList<ArriveBillDto>();
			bill.setDestOrgCode(loginOrg.getCode());
			List<ArriveBillDto> tranpBills = this.queryArriveBillByDept(bill);
			//配合灰度改造，灰度以后还需要放开这段业务逻辑
//			for(int i=0;i<tranpBills.size();i++){
//				if(UnloadCommonUtils.isExpressHandOver(tranpBills.get(i).getBillNo())){
//					tranpBills.remove(i);
//					--i;
//				}
//			}
			if(CollectionUtils.isNotEmpty(tranpBills)){
				bills = tranpBills;
			}
			//查询到达本部门的快递集中卸车2015.02.04chenmingyan
			List<ArriveBillDto> expressBills = assignUnloadTaskDao.queryArriveExpressBill(bill);
			if(CollectionUtils.isNotEmpty(expressBills)){
				bills.addAll(expressBills);
			}
			//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
			ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
			branchSalesDeptEntity.setSalesDeptCode(loginOrg.getCode());
			ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
					queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
			if(branchSalesDept !=null){
				bill.setDestOrgCode(branchSalesDept.getExpressBranchCode());
				List<ArriveBillDto> tranBills =this.queryArriveBillByDept(bill);
				if(CollectionUtils.isNotEmpty(tranBills)){
					bills.addAll(tranBills);
				}
			} 
			//查询零担电子面单卸车2016-7-23 322610
			List<ArriveBillDto> elecWayBills = assignUnloadTaskDao.queryElecWayBill(bill);
			if(CollectionUtils.isNotEmpty(elecWayBills)){
				bills.addAll(elecWayBills);
			}
			return bills;
		}else{
			List<ArriveBillDto> bills = new ArrayList<ArriveBillDto>();
			String currentOutfield;
			try{
				currentOutfield = getCurrentOutfieldCode();
			}catch(TfrBusinessException e){
				return null;
			}
			//查询外场到达单据
			if(StringUtils.isNotBlank(currentOutfield)){
				bill.setDestOrgCode(currentOutfield);
				List<ArriveBillDto> outFieldBills = this.queryArriveBillByDept(bill);
				if(CollectionUtils.isNotEmpty(outFieldBills)){
					bills.addAll(outFieldBills);
				}

				
				//查询商务专递卸车 272681 2015/11/12
				List<ArriveBillDto> businessBills = assignUnloadTaskDao.queryArriveBusinessBill(bill);
				
				List<ArriveBillDto> removeBills = new ArrayList<ArriveBillDto>();
				if(CollectionUtils.isNotEmpty(outFieldBills)&&CollectionUtils.isNotEmpty(businessBills)){
					for(ArriveBillDto outArriveBillDto:outFieldBills){
						for(ArriveBillDto arriveBillDto:businessBills){
							if(outArriveBillDto.getBillNo().equals(arriveBillDto.getBillNo())){
								removeBills.add(arriveBillDto);
							}
						}
					}
					
				}
				
				if(CollectionUtils.isNotEmpty(businessBills)){
					//去重复单据
					businessBills.removeAll(removeBills);
					bills.addAll(businessBills);
				}else{
					OutfieldEntity outfieldEntity = new OutfieldEntity();
					if(UnloadConstants.UNLOAD_ELECTRANSPORT.equals(bill.getUnloadType())){
						
						//查询零担电子面单卸车2016-7-23 322610
						List<ArriveBillDto> elecWayBills = assignUnloadTaskDao.queryElecWayBill(bill);
						
						if(CollectionUtils.isNotEmpty(elecWayBills)){
							bills.addAll(elecWayBills);
						}
						
					}else{
						//查询到达本部门的快递集中卸车2015.02.04chenmingyan
						List<ArriveBillDto> expressBills = assignUnloadTaskDao.queryArriveExpressBill(bill);
						if(CollectionUtils.isNotEmpty(expressBills)){
							bills.addAll(expressBills);
						}
						
						//查询空运总调到达中转单据
						outfieldEntity= outfieldService.queryOutfieldByOrgCode(currentOutfield);
						if(outfieldEntity != null){
							if(StringUtils.isNotBlank(outfieldEntity.getAirDispatchCode())){
								bill.setDestOrgCode(outfieldEntity.getAirDispatchCode());
								List<ArriveBillDto> airDispatchBills = assignUnloadTaskDao.queryArriveTransderBill(bill);
								if(CollectionUtils.isNotEmpty(airDispatchBills)){
									bills.addAll(airDispatchBills);
								}
							}
						}
					}
					/**
					 * @desc 对预分配月台进行校验
					 * @author 105795
					 * @date 2015-05-06
					 * */
					String goOn="Y";
					if(StringUtil.isNotEmpty(isPrePlatform)&&"Y".equalsIgnoreCase(isPrePlatform)){
						
						List<String> unloadTypeList=new ArrayList<String>();
						//长途卸车
						unloadTypeList.add("LONG_DISTANCE");
						//短途卸车
						unloadTypeList.add("SHORT_DISTANCE");
	
						//如果为  长途卸车or短途卸车 需要校验是否进行了月台预分配
						String unloadType=bill.getUnloadType();
						if(StringUtil.isNotEmpty(unloadType)&&unloadTypeList.contains(unloadType)){
							//查询任务车辆id
							String truckTaskId=assignUnloadTaskDao.queryTruckTaskIdByVehicleNo(bill.getVehicleNo());
							
							if(StringUtil.isNotEmpty(truckTaskId)){
								//根据任务车辆去拿单据信息
								List<String>  handoverNos= queryHandNosByTruckTaskId(truckTaskId);
									//List<BillInfoDto> billInfoList=vehicleSealService.queryBillInfoByTruckTaskId(truckTaskId);
							   int countK=0;
							   if(handoverNos!=null&&handoverNos.size()>0){
									for(String handoverNo:handoverNos){
											if(handoverNo.contains("K")||handoverNo.contains("k")||handoverNo.contains("B")||handoverNo.contains("b")){
												countK++;
											}
										}
									//整个任务车辆都是快递则不带出月台号
									if(countK==handoverNos.size()){
										goOn="N";
									}
										
									
								 }
								if(goOn.equals("Y")){
									//外场
									String prePlatformNo= vehicleSealService.queryPrePlatformCodeByTruckTaskId(currentOutfield, truckTaskId);
									if(StringUtil.isEmpty(prePlatformNo)&&StringUtil.isNotEmpty(outfieldEntity.getAirDispatchCode())){
										//空运总调
										prePlatformNo= vehicleSealService.queryPrePlatformCodeByTruckTaskId(outfieldEntity.getAirDispatchCode(), truckTaskId);
										
									}
									//如果找不到说明该车没有预分配月台
									if(StringUtil.isEmpty(prePlatformNo)){
										throw new TfrBusinessException("车辆："+bill.getVehicleNo()+" 没有预分配月台");
									}
									
									for(ArriveBillDto billDto:bills){
										billDto.setPrePlatformNo(prePlatformNo);
									}
									
								}
								
								
							}
							
						}
						
					}
				}
				return bills;
			}else{
				return null;
			}
		}
	}

	
	private List<ArriveBillDto> queryArriveBillByDept(ArriveBillDto bill){
		List<ArriveBillDto> bills ;
		if(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(bill.getUnloadType())){
			bills = new ArrayList<ArriveBillDto>();
			//接送货
			List<ArriveBillDto> pickUpBills = assignUnloadTaskDao.queryArrivePickUpBill(bill);
			//转货
			List<ArriveBillDto> transhipBills = assignUnloadTaskDao.queryArriveTransderBill(bill);
			if(CollectionUtils.isNotEmpty(pickUpBills)){
				//当前部门
				String currentOrgCode = FossUserContext.getCurrentDeptCode();
				//遍历接送货单
				for(ArriveBillDto pickupBill : pickUpBills){
					pickupBill.setOrigOrgCode(currentOrgCode);
					pickupBill.setDestOrgCode(currentOrgCode);
				}
				bills.addAll(pickUpBills);
			}
			if(CollectionUtils.isNotEmpty(transhipBills)){
				bills.addAll(transhipBills);
			}
		}else{
			//查询已到达中转单据
			bills = assignUnloadTaskDao.queryArriveTransderBill(bill);
		}
		//不为空
		if(CollectionUtils.isNotEmpty(bills)){
			return bills;
		}else{
			return null;//所属外场为空
		}
	}
	/**
	 * 获取当前登录部门所属外场编码.
	 *
	 *
	 *
	 * @return the current outfield code
	 * @throws TfrBusinessException the tfr business exception
	 * @author 042795-foss-duyi
	 * @date 2012-10-15 下午8:12:53
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAssignLoadTaskService#queryDeliverBill(com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto, int, int)
	 */
	private String getCurrentOutfieldCode()throws TfrBusinessException{
				String currentOrgCode = FossUserContext.getCurrentDeptCode();
				List<String> bizTypes = new ArrayList<String>();
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				try{
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(currentOrgCode,bizTypes);
					if(org != null){
						/*if(FossConstants.YES.equals(org.getTransferCenter())){
							return org.getCode();
						}else{
							if(StringUtils.isNotBlank(org.getArrangeOutfield())){
								return org.getArrangeOutfield();
							}else{
								throw new TfrBusinessException("所属外场为空");//所属外场为空
							}
						}*/
						if(StringUtils.isNotBlank(org.getCode())){
							return org.getCode();
						}else{
							throw new TfrBusinessException(AssignLoadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
						}
					}else{
						throw new TfrBusinessException("部门信息为空");//部门信息为空
					}
				}catch(BusinessException e){
					throw new TfrBusinessException(AssignLoadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
				}		
	}
	/**
	 * 查询理货员.
	 *
	 *
	 *
	 * @param loaderQC the loader qc
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author dp-duyi
	 * @date 2012-10-19 下午2:27:18
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#queryLoader(com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto, int, int)
	 */
	@Override
	public List<LoaderDto> queryLoader(LoaderDto loaderQC, int limit, int start) {
		List<OrgAdministrativeInfoEmployeeDto> loaderEmps = new ArrayList<OrgAdministrativeInfoEmployeeDto>();
		//返货值
		List<LoaderDto> loaders = new ArrayList<LoaderDto>();
		//查询条件
		EmployeeEntity employee = new EmployeeEntity();
		//理货员编码
		employee.setEmpCode(loaderQC.getLoaderCode());
		//理货员职位
		employee.setTitle(loaderQC.getTitle());
		//查询登录部门
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
			return null;
		}
		//如果营业部则返回营业部下面人员
		if("Y".equals(loginOrg.getSalesDepartment())){
			employee.setOrgCode(FossUserContext.getCurrentDeptCode());
			if(StringUtils.isNotBlank(loaderQC.getOrgCode())){
				employee.setOrgCode(loaderQC.getOrgCode());
			}
			List<EmployeeEntity> emps = employeeService.queryEmployeeExactByEntity(employee,start,limit);
			if(emps != null){
				OrgAdministrativeInfoEmployeeDto loaderEmp;
				for(EmployeeEntity emp : emps){
					loaderEmp = new OrgAdministrativeInfoEmployeeDto();
					loaderEmp.setEmployeeEmpCode(emp.getEmpCode());
					loaderEmp.setEmployeeEmpName(emp.getEmpName());
					loaderEmp.setOrgAdministrativeInfoCode(emp.getOrgCode());
					if(!emp.getOrgCode().equals(FossUserContext.getCurrentDeptCode())){
						OrgAdministrativeInfoEntity loaderOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
						if(loaderOrg != null){
							loaderEmp.setOrgAdministrativeInfoName(loaderOrg.getName());
						}
					}else{
						loaderEmp.setOrgAdministrativeInfoName(loginOrg.getName());
					}
					//职位数据字典
					DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.UUMS_POSITION_TERMSCODE, emp.getTitle());
					//职位
					if(dictEntity1 != null){
						loaderEmp.setEmployeeTitleName(dictEntity1.getValueName());
					}
					loaderEmps.add(loaderEmp);
				}
			}
		}else{
		//查询外场人员
			//查询理货员:调用综合接口
			OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
			//理货员部门
			org.setCode(loaderQC.getOrgCode());
			//当前理货部门所属外场 
			try{
				org.setArrangeOutfield(this.getCurrentOutfieldCode());
			}catch(TfrBusinessException e){
				return null;
			}
			//理货业务类型:派送装车
			org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_DELIVER_LOAD);
			//查询派送装车理货员
			List<OrgAdministrativeInfoEmployeeDto> deliverLoaderEmps= orgAdministrativeInfoEmployeeService.getPorter(org, employee, start, limit);
			//理货业务类型:装卸车
			org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_LOAD_AND_UNLOAD);
			//查询装卸车理货员
			List<OrgAdministrativeInfoEmployeeDto> loadAndUnloaderEmps= orgAdministrativeInfoEmployeeService.getPorter(org, employee, start, limit);
			//理货业务类型:卸车理
			org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_UNLOAD);
			//查询卸车理货员
			List<OrgAdministrativeInfoEmployeeDto> unloadLoaderEmps= orgAdministrativeInfoEmployeeService.getPorter(org, employee, start, limit);
			
			//将不同业务类型理货员放在一个list里:loaderDto
			
			if(CollectionUtils.isNotEmpty(deliverLoaderEmps)){
				loaderEmps.addAll(deliverLoaderEmps);
			}
			if(CollectionUtils.isNotEmpty(loadAndUnloaderEmps)){
				loaderEmps.addAll(loadAndUnloaderEmps);
			}
			if(CollectionUtils.isNotEmpty(unloadLoaderEmps)){
				loaderEmps.addAll(unloadLoaderEmps);
			}
		}
		if(CollectionUtils.isEmpty(loaderEmps)){
			return null;
			//throw new TfrBusinessException(AssignUnloadTaskExceptionCode.DATA_NOTFIND_MESSAGECODE);
		}else{
			//loaders赋值：基础属性
			List<LoaderDto> loadersBse = new ArrayList<LoaderDto>();
			LoaderDto loader;
			for(OrgAdministrativeInfoEmployeeDto loaderEmp : loaderEmps){
				loader = new LoaderDto();
				loader.setLoaderCode(loaderEmp.getEmployeeEmpCode());
				loader.setLoaderName(loaderEmp.getEmployeeEmpName());
				loader.setOrgCode(loaderEmp.getOrgAdministrativeInfoCode());
				loader.setOrgName(loaderEmp.getOrgAdministrativeInfoName());
				loader.setTitle(loaderEmp.getEmployeeTitleName());
				loadersBse.add(loader);
			}
			//查询理货员状态
			List<LoaderDto> loadersState = assignUnloadTaskDao.queryLoaderState(loadersBse);
			//查询理货员已完成货量
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("queryTimeBegin", loaderQC.getQueryTimeBegin());
			condition.put("queryTimeEnd", loaderQC.getQueryTimeEnd());
			condition.put("loaders", loadersBse);
			List<LoaderDto> finishedWorkLoads = assignUnloadTaskDao.queryLoaderFinishedWorkLoad(condition);
			//查询理货员未完成货量
			List<LoaderDto> unFinishedWorkLoads = assignUnloadTaskDao.queryLoaderUnFinishedWorkLoad(loadersBse);
			String loaderCode;
			for(LoaderDto loaderBse : loadersBse){
				loaderCode = loaderBse.getLoaderCode();
				loader = new LoaderDto();
				//基础属性复制
				loader.setLoaderCode(loaderBse.getLoaderCode());
				loader.setLoaderName(loaderBse.getLoaderName());
				loader.setOrgCode(loaderBse.getOrgCode());
				loader.setOrgName(loaderBse.getOrgName());
				loader.setTitle(loaderBse.getTitle());
				//理货员状态：在线、离线
				if(CollectionUtils.isNotEmpty(loadersState)){
					for(LoaderDto state : loadersState){
						if(state.getLoaderCode().equals(loaderCode)){
							loader.setState(state.getState());
						}
					}
				}
				//未完成货量
				if(CollectionUtils.isNotEmpty(unFinishedWorkLoads)){
					for(LoaderDto unfinishedWorkLoad : unFinishedWorkLoads){
						if(unfinishedWorkLoad.getLoaderCode().equals(loaderCode)){
							loader.setUnfinishedTaskQty(unfinishedWorkLoad.getUnfinishedTaskQty());
							loader.setUnfinishedWeight(unfinishedWorkLoad.getUnfinishedWeight());
						}
					}
				}
				//已完成货量
				if(CollectionUtils.isNotEmpty(finishedWorkLoads)){
					for(LoaderDto finishedWorkLoad : finishedWorkLoads){
						if(finishedWorkLoad.getLoaderCode().equals(loaderCode)){
							loader.setFinishedWeight(finishedWorkLoad.getFinishedWeight());
							loader.setAssignedWeight(finishedWorkLoad.getAssignedWeight());
						}
					}
				}
				loaders.add(loader);
			}
			return loaders;
		}
	}
	/**
	 * 查询理货员记录条数.
	 * 
	 * 
	 * 
	 *
	 * @param loaderQC the loader qc
	 * @return the loader count
	 * @author dp-duyi
	 * @date 2012-10-19 下午2:27:18
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#getLoaderCount(com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto)
	 */
	@Override
	public Long getLoaderCount(LoaderDto loaderQC) {
		Long totalCount = NumberUtils.LONG_ZERO;
		//查询理货员:调用综合接口
		OrgAdministrativeInfoEntity org = new OrgAdministrativeInfoEntity();
		EmployeeEntity employee = new EmployeeEntity();
		//理员编码
		employee.setEmpCode(loaderQC.getLoaderCode());
		//理货员职位
		employee.setTitle(loaderQC.getTitle());
		//理货员部门
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
				return totalCount;
		}
				//如果营业部则返回营业部下面人员
		if("Y".equals(loginOrg.getSalesDepartment())){
			employee.setOrgCode(FossUserContext.getCurrentDeptCode());
			if(StringUtils.isNotBlank(loaderQC.getOrgCode())){
				employee.setOrgCode(loaderQC.getOrgCode());
			}
			totalCount = employeeService.queryEmployeeExactByEntityCount(employee);
		}else{	
				org.setCode(loaderQC.getOrgCode());
				//当前理货部门所属外场
				try{
					org.setArrangeOutfield(this.getCurrentOutfieldCode());
				}catch(TfrBusinessException e){
					return NumberUtils.LONG_ZERO;
				}
				//理货业务类型:派送装车
				org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_DELIVER_LOAD);
				//查询派送装车理货员
				Long deliverLoaderEmpCount= orgAdministrativeInfoEmployeeService.getPorterCount(org, employee);
				//理货业务类型:装卸车
				org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_LOAD_AND_UNLOAD);
				//查询装卸车理货员
				Long loadAndUnloaderEmpCount= orgAdministrativeInfoEmployeeService.getPorterCount(org, employee);
				//理货业务类型:卸车理
				org.setArrangeBizType(BizTypeConstants.ORG_P_ARRANGE_BIZ_TYPE_UNLOAD);
				//查询卸车理货员
				Long unloadLoaderEmpCount = orgAdministrativeInfoEmployeeService.getPorterCount(org, employee);
				
				if(deliverLoaderEmpCount != null){
					totalCount = totalCount + deliverLoaderEmpCount;
				}
				if(loadAndUnloaderEmpCount != null){
					totalCount = totalCount + loadAndUnloaderEmpCount;
				}
				if(unloadLoaderEmpCount != null){
					totalCount = totalCount + unloadLoaderEmpCount;
				}
				}
		return totalCount;
	}
	/**
	 * 刷新.
	 * 
	 * 
	 *
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author dp-duyi
	 * @date 2012-10-23 下午4:29:51
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#queryUnStartTask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto, int, int)
	 */
	@Override
	public List<AssignUnloadTaskTotalDto> queryUnStartTask( int limit, int start) {
		AssignUnloadTaskDto taskState = new AssignUnloadTaskDto();
		taskState.setBeCanceled(FossConstants.INACTIVE);
		taskState.setState(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_UNSTART);
		taskState.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		List<AssignUnloadTaskTotalDto> unStartTasks = assignUnloadTaskDao.queryUnStartTask(taskState, limit, start);
		if(CollectionUtils.isEmpty(unStartTasks)){
			return null;
		}
		return unStartTasks;
	}
	/**
	 * 刷新记录数.
	 * 
	 * 
	 * 
	 *
	 * @return the long
	 * @author dp-duyi
	 * @date 2012-10-31 上午8:21:52
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#queryUnStartTaskCount()
	 */
	@Override
	public Long queryUnStartTaskCount() {
		//分配卸车任务
		AssignUnloadTaskDto taskState = new AssignUnloadTaskDto();
		//未生效
		taskState.setBeCanceled(FossConstants.INACTIVE);
		//未开始
		taskState.setState(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_UNSTART);
		taskState.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		//查询刷新记录条数
		return assignUnloadTaskDao.queryUnStartTaskCount(taskState);
	}
	/**
	 * 取消分配.
	 *
	 *
	 *
	 *
	 * @param assignMsg the assign msg
	 * @return the int
	 * @author dp-duyi
	 * @date 2012-10-24 下午1:58:29
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#cancelAssignUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int cancelAssignUnloadTask(AssignUnloadTaskDto assignMsg) {
		List<AssignUnloadTaskDto> tasks = assignUnloadTaskDao.queryTaskByVehicle(assignMsg);
		if(CollectionUtils.isNotEmpty(tasks)){
			List<AssignUnloadTaskDto> updateTasks = new ArrayList<AssignUnloadTaskDto>();
			List<ArriveBillDto> bills = new ArrayList<ArriveBillDto>();
			ArriveBillDto bill;
			for(AssignUnloadTaskDto task: tasks){
				if(FossConstants.ACTIVE.equals(task.getBeCanceled())){
					throw new TfrBusinessException(AssignUnloadTaskExceptionCode.TASKBECENCELED_MESSAGECODE);
				}
				if(!UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_UNSTART.equals(task.getState())){
					throw new TfrBusinessException(AssignUnloadTaskExceptionCode.TASKBESTARTED_MESSAGECODE);
				}
				bill = new ArriveBillDto();
				bill.setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);
				bill.setBillNo(task.getBillNo());
				bills.add(bill);
				task.setBeCanceled(FossConstants.ACTIVE);
				task.setModifyTime(DateUtils.convert(Calendar.getInstance().getTime(), DateUtils.DATE_TIME_FORMAT));
				UserEntity user = FossUserContext.getCurrentUser();
				task.setModifyUserCode(user.getUserName());//工号，前台问题name存工号
				task.setModifyUserName(user.getEmpName());
				updateTasks.add(task);
				int updateQty = assignUnloadTaskDao.cancelAssignUnloadTask(task);
				if(updateQty != 1){
					throw new TfrBusinessException("数据过期，请刷新");
				}
			}
			if(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(assignMsg.getVehicle().getUnloadType())){
				//更新接送货部分
				assignUnloadTaskDao.updateArrivePickUpBillState(bills);
				//更新转货部分
				assignUnloadTaskDao.updateArriveTransferBillState(bills);
			}else if(UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS.equals(assignMsg.getVehicle().getUnloadType())){
				//更新快递集中卸车单据状态chenmingyan
				assignUnloadTaskDao.updateArriveExpressBillState(bills);
			}else if(UnloadConstants.UNLOAD_ELECTRANSPORT.equals(assignMsg.getVehicle().getUnloadType())){
				//更新零担电子面单卸车单据状态322610
				assignUnloadTaskDao.updateElecWayBillState(bills);
			}else if(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(assignMsg.getVehicle().getUnloadType())){
				//2015/8/26 272681 更新商务专递单据状态
				assignUnloadTaskDao.updateBusinessAirBillState(bills);
			}else{
				assignUnloadTaskDao.updateArriveTransferBillState(bills);
			}
			return tasks.size();
		}else{
			throw new TfrBusinessException(AssignUnloadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
		}
	}
	/**
	 * 查询分配记录.
	 *
	 *
	 *
	 * @param task the task
	 * @param limit the limit
	 * @param start the start
	 * @return the list
	 * @author dp-duyi
	 * @date 2012-10-31 下午3:17:06
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#queryAssignUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto, int, int)
	 */
	@Override
	public List<AssignUnloadTaskTotalDto> queryAssignUnloadTask(
			AssignUnloadTaskDto task, int limit, int start) {
		OrgAdministrativeInfoEntity orgAdministrativeInfo = querySuperiorOrgByOrgCode(FossUserContext.getCurrentDeptCode());
		//当前部门顶级组织code
		String orgCode = orgAdministrativeInfo.getCode();
		List<String> orgCodes = getChildDept(orgCode);
		task.setOrgCodes(orgCodes);
		task.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		
		String allState = UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_ALL;
		if(StringUtils.isNotBlank(task.getState())){
			if(allState.equals(task.getState())){
				task.setState("");
				task.setBeCanceled("");
			}else if(task.getState().equals(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_CANCELED)){
				task.setBeCanceled(FossConstants.ACTIVE);
			}else{
				task.setBeCanceled(FossConstants.INACTIVE);
			}
		}
		if(task.getVehicle()!=null&&StringUtils.isNotBlank(task.getVehicle().getUnloadType())){
			if(allState.equals(task.getVehicle().getUnloadType())){
				task.getVehicle().setUnloadType("");
			}
		}
		List<AssignUnloadTaskTotalDto> tasks = assignUnloadTaskDao.queryAssignUnloadTask(task, limit, start);
		if(CollectionUtils.isEmpty(tasks)){
			return null;
		}
		return tasks;
	}
	/**
	 * 查询分配记录数.
	 *
	 *
	 *
	 * @param task the task
	 * @return the long
	 * @author dp-duyi
	 * @date 2012-10-31 下午3:17:06
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#queryAssignUnloadTaskCount(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	public Long queryAssignUnloadTaskCount(AssignUnloadTaskDto task) {
		OrgAdministrativeInfoEntity orgAdministrativeInfo = querySuperiorOrgByOrgCode(FossUserContext.getCurrentDeptCode());
		//当前部门顶级组织code
		String orgCode = orgAdministrativeInfo.getCode();
		List<String> orgCodes = getChildDept(orgCode);
		task.setOrgCodes(orgCodes);
		task.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		if(StringUtils.isNotBlank(task.getState())){
			if(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_ALL.equals(task.getState())){
				task.setState("");
			}
		}
		if(task.getVehicle()!=null&&StringUtils.isNotBlank(task.getVehicle().getUnloadType())){
			if(UnloadConstants.UNLOAD_TASK_TYPE_ALL.equals(task.getVehicle().getUnloadType())){
				task.getVehicle().setUnloadType("");
			}
		}
		return assignUnloadTaskDao.queryAssignUnloadTaskCount(task);
	}
	/**
	 * 查询分配记录明细.
	 *
	 * @param task the task
	 * 
	 * @return the list
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-10-31 下午3:17:06
	 * 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#queryAssignUnloadTaskDetail(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	public List<ArriveBillDto> queryAssignUnloadTaskDetail(
			AssignUnloadTaskDto task) {
		task.setBeCanceled(FossConstants.INACTIVE);
		if(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER.equals(task.getVehicle().getUnloadType())){
			//接送货
			List<ArriveBillDto> pickUpAssignUnloadTaskDetail = assignUnloadTaskDao.queryAssignPickUpUnloadTaskDetail(task);
			//转货
			List<ArriveBillDto> transhipAssignUnloadTaskDetail = assignUnloadTaskDao.queryAssignTransferUnloadTaskDetail(task);
			
			List<ArriveBillDto> assignUnloadTaskDetail = new ArrayList<ArriveBillDto>();
			
			if(CollectionUtils.isNotEmpty(pickUpAssignUnloadTaskDetail)){
				assignUnloadTaskDetail.addAll(pickUpAssignUnloadTaskDetail);
			}
			if(CollectionUtils.isNotEmpty(transhipAssignUnloadTaskDetail)){
				assignUnloadTaskDetail.addAll(transhipAssignUnloadTaskDetail);
			}
			return assignUnloadTaskDetail;
		}else if (UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS.equals(task.getVehicle().getUnloadType())){
			//快递集中卸车chenmingyan
			List<ArriveBillDto> assignUnloadExpressDetail = assignUnloadTaskDao.queryAssignExpressUnloadTaskDetail(task);
			return assignUnloadExpressDetail;
		}else if(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(task.getVehicle().getUnloadType())){
			//2015/8/26 272681 商务专递卸车
			List<ArriveBillDto> assignUnloadBusinessDetail = assignUnloadTaskDao.queryAssignBusinessUnloadTaskDetail(task);
			return assignUnloadBusinessDetail;
		}else if(UnloadConstants.UNLOAD_ELECTRANSPORT.equals(task.getVehicle().getUnloadType())){
			//2016-7-26 322610零担电子运单卸车
			List<ArriveBillDto> assignUnloadBusinessDetail = assignUnloadTaskDao.queryElecWayUnloadTaskDetail(task);
			return assignUnloadBusinessDetail;
		}else{
			List<ArriveBillDto> assignUnloadTaskDetail = assignUnloadTaskDao.queryAssignTransferUnloadTaskDetail(task);
			return assignUnloadTaskDetail;
		}
	}
	/**
	 * 刷新界面明细查询、返回单据明细及理货员信息.
	 *
	 *
	 *
	 * @param assignMsg the assign msg
	 * 
	 * @return the assign unload task dto
	 * 
	 * @author dp-duyi
	 * 
	 * @date 2012-12-28 上午10:25:40
	 * 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService1#refreshAssignedTaskDetail(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	public AssignUnloadTaskDto refreshAssignedTaskDetail(AssignUnloadTaskDto assignMsg) {
		AssignUnloadTaskDto task;
		if(assignMsg != null){
			task = new AssignUnloadTaskDto();
			//查询理货员信息
			if(assignMsg.getLoader() != null){
				List<LoaderDto> loadersBse = new ArrayList<LoaderDto>();
				LoaderDto loader = assignMsg.getLoader();
				loadersBse.add(loader);
				//查询理货员状态
				List<LoaderDto> loadersState = assignUnloadTaskDao.queryLoaderState(loadersBse);
				if(CollectionUtils.isNotEmpty(loadersState)){
					loader.setState(loadersState.get(0).getState());
				}
				//查询理货员未完成货量
				List<LoaderDto> unFinishedWorkLoads = assignUnloadTaskDao.queryLoaderUnFinishedWorkLoad(loadersBse);
				if(CollectionUtils.isNotEmpty(unFinishedWorkLoads)){
					loader.setUnfinishedTaskQty(unFinishedWorkLoads.get(0).getUnfinishedTaskQty());
					loader.setUnfinishedWeight(unFinishedWorkLoads.get(0).getUnfinishedWeight());
				}
				//查询理货员职位
				EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(loader.getLoaderCode());
				if(emp != null){
					//职位数据字典
					DataDictionaryValueEntity dictEntity1 = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.UUMS_POSITION_TERMSCODE, emp.getTitle());
					//职位
					if(dictEntity1 != null){
						loader.setTitle(dictEntity1.getValueName());
					}
				}
				task.setLoader(loader);
			}else{
				throw new TfrBusinessException(AssignUnloadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
			}
			//查询单据明细
			if(assignMsg.getVehicle() != null){
				List<ArriveBillDto> taskList = this.queryAssignUnloadTaskDetail(assignMsg);
				if(CollectionUtils.isNotEmpty(taskList)){
					List<ArriveBillDto> bills = new ArrayList<ArriveBillDto>();
					bills.addAll(taskList);
					task.setBills(bills);
				}
			}else{
				throw new TfrBusinessException(AssignUnloadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
			}
			return task;
		}else{
			throw new TfrBusinessException(AssignUnloadTaskExceptionCode.PARAMETERERROR_MESSAGECODE);
		}
	}
	/**
	 * 
	 * 分配任务界面导出 
	 * @author alfred
	 * @date 2014-2-15 下午5:53:13
	 * @param vehicle
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService#exportAssignunloadtask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto)
	 */
	@Override
	public InputStream exportAssignunloadtask(AssignUnloadTaskTotalDto vehicle) {
		InputStream excelStream = null;
		List<AssignUnloadTaskTotalDto> vehicles = new ArrayList<AssignUnloadTaskTotalDto> ();
		vehicles = queryArriveVehicle(vehicle);
		
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		if(CollectionUtils.isEmpty(vehicles)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}else{
			for(AssignUnloadTaskTotalDto unloadTask : vehicles){
				//每行的列List
				List<String> columnList = new ArrayList<String>();
				//车牌号
				columnList.add(unloadTask.getVehicleNo());
		
				//车型
				columnList.add(unloadTask.getVehicleType());
				
				//到达时间
				columnList.add(unloadTask.getArriveTime());
				
				//线路
				columnList.add(unloadTask.getLine());
				
				//总重量
				columnList.add(Double.toString(unloadTask.getWeightTotal()));
				
				//总体积
				columnList.add(Double.toString(unloadTask.getVolumeTotal()));
				
				//总件数
				columnList.add(Integer.toString(unloadTask.getGoodsQtyTotal()));
		
				//单据数
				columnList.add(Integer.toString(unloadTask.getBillQtyTotal()));
		
				//月台
				columnList.add(unloadTask.getPlatformNo());
		
				//卡货总票数
				columnList.add(Integer.toString(unloadTask.getFastWayBillQtyTotal()));
				
				//城际票数
				columnList.add(Integer.toString(unloadTask.getFsfWayBillQtyTotal()));
				
				//城际重量
				columnList.add(Double.toString(unloadTask.getFsfWeightTotal()));
				
				//城际体积
				columnList.add(Double.toString(unloadTask.getFsfVolumeTotal()));
				
				//空运票数
				columnList.add(Integer.toString(unloadTask.getAfWayBillQtyTotal()));
				
				//空运重量
				columnList.add(Double.toString(unloadTask.getAfWeightTotal()));
				
				//空运体积
				columnList.add(Double.toString(unloadTask.getAfVolumeTotal()));
				
				//卡航票数
				columnList.add(Integer.toString(unloadTask.getFlfWayBillQtyTotal()));
				
				//卡航重量
				columnList.add(Double.toString(unloadTask.getFlfWeightTotal()));
				
				//卡航体积
				columnList.add(Double.toString(unloadTask.getFlfVolumeTotal()));
				
				rowList.add(columnList);
			}
		}
		String[] rowHeads = {"车牌号","车型","到达时间","线路","总重量","总体积","总件数","单据数",
				"月台","卡货总票数","城际票数","城际重量","城际体积","空运票数","空运重量","空运体积",
				"卡航票数","卡航重量","卡航体积"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("未分配卸车任务");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	/**
	 * 
	 * 统计到达未卸车数量 
	 * @author alfred
	 * @date 2014-2-15 下午5:53:47
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService#queryArriveBillCount()
	 */
	@Override
	public String queryArriveBillCount() {
		List<String> destOrgCodes = new ArrayList<String>();
		StringBuffer sf = new StringBuffer();
		int longVehicles = 0;
		int shortVehicles = 0;
		int pickUpVehicles = 0;
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
			return null;
		}
		if("Y".equals(loginOrg.getSalesDepartment())){
			destOrgCodes.add(loginOrg.getCode());
			longVehicles =assignUnloadTaskDao.queryTransferArrivedCount(destOrgCodes, "LONG_DISTANCE");
			shortVehicles =assignUnloadTaskDao.queryTransferArrivedCount(destOrgCodes, "SHORT_DISTANCE");
			
			destOrgCodes.add("N/A");
			pickUpVehicles = assignUnloadTaskDao.queryPickUpArrivedCount(destOrgCodes);
		}else{
			String currentOutfield;
			try{
				currentOutfield = getCurrentOutfieldCode();
			}catch(TfrBusinessException e){
				return null;
			}
			//查询外场到达车辆
			if(StringUtils.isNotBlank(currentOutfield)){
				destOrgCodes.add(currentOutfield);
				//查询空运总调到达车辆
				OutfieldEntity outfieldEntity= outfieldService.queryOutfieldByOrgCode(currentOutfield);
				if(outfieldEntity != null){
					if(StringUtils.isNotBlank(outfieldEntity.getAirDispatchCode())){
						destOrgCodes.add(outfieldEntity.getAirDispatchCode());
					}
				}
				longVehicles =assignUnloadTaskDao.queryTransferArrivedCount(destOrgCodes, "LONG_DISTANCE");
				shortVehicles =assignUnloadTaskDao.queryTransferArrivedCount(destOrgCodes, "SHORT_DISTANCE");
				
			    List<String> pickUpDestOrgCodes = new ArrayList<String>();
				pickUpDestOrgCodes.add(currentOutfield);
				pickUpDestOrgCodes.add("N/A");
				pickUpVehicles = assignUnloadTaskDao.queryPickUpArrivedCount(pickUpDestOrgCodes);
			}else{
				return null;
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		sf.append("温馨提示：截止到");
		sf.append(df.format(time));
		sf.append("本部门还有");
		sf.append(longVehicles+shortVehicles+pickUpVehicles);
		sf.append("辆车未分配卸车任务，其中长途卸车");
		sf.append(longVehicles);
		sf.append("辆，短途卸车");
		sf.append(shortVehicles);
		sf.append("辆，集中卸车");
		sf.append(pickUpVehicles);
		sf.append("辆，请及时安排人员卸车。");
		return sf.toString();
	}
	/**
	 * 
	 * 已分配卸车查询界面导出
	 * @author alfred
	 * @date 2014-2-15 下午5:54:30
	 * @param task
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService#exportAssignunloadedtask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	@Override
	public InputStream exportAssignunloadedtask(AssignUnloadTaskDto task) {
		InputStream excelStream = null;
		
		List<AssignUnloadTaskTotalDto> tasks = new ArrayList<AssignUnloadTaskTotalDto>();
		tasks =queryAssignUnloadedTask(task);
				
		List<List<String>> rowList = new ArrayList<List<String>>();
		if(CollectionUtils.isEmpty(tasks)){
			List<String> columnList = new ArrayList<String>();
			rowList.add(columnList);
		}else{
			for(AssignUnloadTaskTotalDto assignunloadedTask : tasks){
				//每行的列List
				List<String> columnList = new ArrayList<String>();
				//车牌号
				columnList.add(assignunloadedTask.getVehicleNo());
	
				//车型
				columnList.add(assignunloadedTask.getVehicleType());
				
				//线路
				columnList.add(assignunloadedTask.getLine());
				String unloadType = assignunloadedTask.getUnloadType();
				//卸车类型
				if (StringUtils.equals(unloadType, "DELIVER")) {	
					columnList.add("派送");//'派送';
				} else if (StringUtils.equals(unloadType, "PARTIALLINE")) {					
					columnList.add("偏线");//'偏线';
				}else if (StringUtils.equals(unloadType, "LONG_DISTANCE")) {					
					columnList.add("长途");//'长途';
				}else if (StringUtils.equals(unloadType, "SHORT_DISTANCE")) {					
					columnList.add("短途");//'短途';
				}else if (StringUtils.equals(unloadType, "EXPREE_PICK")){
					columnList.add("快递集中卸车");//快递集中卸车chenmingyan
				}else if (StringUtils.equals(unloadType, "BUSINESS_AIR")){
					columnList.add("商务专递卸车");//商务专递卸车 272681
				}else{
					columnList.add(unloadType);
				}
				//卸车状态
				String taskState = assignunloadedTask.getTaskState();
				if (StringUtils.equals(taskState, "UNSTART")) {					
					columnList.add("未开始");//'未开始';
				} else if (StringUtils.equals(taskState, "PROCESSING")) {					
					columnList.add("进行中");//'进行中';
				}else if (StringUtils.equals(taskState, "FINISHED")) {					
					columnList.add("已完成");//'已完成';
				}else if(StringUtils.equals(taskState,  "CANCELED")){
					columnList.add("已取消");//'已取消';
				}else{
					columnList.add(taskState);
				}
				//到达时间
				columnList.add(assignunloadedTask.getArriveTime());
				
				//总重量
				columnList.add(Double.toString(assignunloadedTask.getWeightTotal()));
				
				//总体积
				columnList.add(Double.toString(assignunloadedTask.getVolumeTotal()));
				
				//总件数
				columnList.add(Integer.toString(assignunloadedTask.getGoodsQtyTotal()));
	
				//单据数
				columnList.add(Integer.toString(assignunloadedTask.getBillQtyTotal()));
	
				//分配人
				columnList.add(assignunloadedTask.getModifyUserName());
				//月台
				columnList.add(assignunloadedTask.getPlatformNo());
	
				//卡货票数
				columnList.add(Integer.toString(assignunloadedTask.getFastWayBillQtyTotal()));
				 
				//分配时间
				columnList.add(assignunloadedTask.getCreateTime());
				
				//理货员
				columnList.add(assignunloadedTask.getLoaderName());
				
				//工号
				columnList.add(assignunloadedTask.getLoaderCode());
				
				//部门
				columnList.add(assignunloadedTask.getLoaderOrgName());
				
				rowList.add(columnList);
			}
		}
		String[] rowHeads = {"车牌号","车型","线路","卸车类型","卸车状态","到达时间","总重量","总体积","总件数","单据数","分配人",
				"月台","卡货票数","分配时间","理货员","工号","部门"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("分配卸车任务");
		exportSetting.setSize(ConstantsNumberSonar.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	/**
	 * 
	 * <p>查询已分配卸车任务</p> 
	 * @author alfred
	 * @date 2014-2-14 上午9:48:29
	 * @param task
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService#queryAssignUnloadedTask(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto)
	 */
	//TODO
	@Override
	public List<AssignUnloadTaskTotalDto> queryAssignUnloadedTask(AssignUnloadTaskDto task) {
		OrgAdministrativeInfoEntity orgAdministrativeInfo = querySuperiorOrgByOrgCode(FossUserContext.getCurrentDeptCode());
		//当前部门顶级组织code
		String orgCode = orgAdministrativeInfo.getCode();
		List<String> orgCodes = getChildDept(orgCode);
		task.setOrgCodes(orgCodes);
		task.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
		
		String allState = UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_ALL;
		if(StringUtils.isNotBlank(task.getState())){
			if(allState.equals(task.getState())){
				task.setState("");
				task.setBeCanceled("");
			}else if(task.getState().equals(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_CANCELED)){
				task.setBeCanceled(FossConstants.ACTIVE);
			}else{
				task.setBeCanceled(FossConstants.INACTIVE);
			}
		}
		if(task.getVehicle()!=null&&StringUtils.isNotBlank(task.getVehicle().getUnloadType())){
			if(allState.equals(task.getVehicle().getUnloadType())){
				task.getVehicle().setUnloadType("");
			}
		}
		List<AssignUnloadTaskTotalDto> tasks = assignUnloadTaskDao.queryAssignUnloadedTask(task);
		if(CollectionUtils.isEmpty(tasks)){
			return null;
		}
		return tasks;
	}
	/**
	 * 
	 * 分页查询方法</p> 
	 * @author alfred
	 * @date 2014-2-14 下午6:34:32
	 * @param vehicle
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService#queryArriveVehicle(com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto, int, int)
	 */
	@Override
	public List<AssignUnloadTaskTotalDto> queryArriveVehicle(
			AssignUnloadTaskTotalDto vehicleQC, int limit, int start) {
		List<AssignUnloadTaskTotalDto> oldVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		List<AssignUnloadTaskTotalDto> pickUpVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		List<AssignUnloadTaskTotalDto> transferVehicles;
		List<AssignUnloadTaskTotalDto> transferExpressVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		List<AssignUnloadTaskTotalDto> elecTransportVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		//272681
		List<AssignUnloadTaskTotalDto> businessVehicles = new ArrayList<AssignUnloadTaskTotalDto>();
		List<String> destOrgCodes = new ArrayList<String>();
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
			return null;
		}
		if(vehicleQC.getUnloadType().equals("ALL")){
			vehicleQC.setUnloadType("");
		}
		if(vehicleQC.getProductCode().equals("ALL")){
			vehicleQC.setProductCode("");
		}
		//如果营业部则返回营业部下面人员
		if("Y".equals(loginOrg.getSalesDepartment())){
			//判定营业部是否有对应快递分部，若有查询出该分部的到达车辆信息
			ExpressBranchSalesDeptEntity  branchSalesDeptEntity = new ExpressBranchSalesDeptEntity();
			branchSalesDeptEntity.setSalesDeptCode(loginOrg.getCode());
			ExpressBranchSalesDeptEntity branchSalesDept= expressBranchSalesDeptService.
					queryByExpressBranchSalesDeptCode(branchSalesDeptEntity);
			if(branchSalesDept !=null){
				destOrgCodes.add(branchSalesDept.getExpressBranchCode());
			} 
			//当前登录部门
			destOrgCodes.add(loginOrg.getCode());
			vehicleQC.setDestOrgCodes(destOrgCodes);
			/**add by 328768*/
			transferVehicles  = assignUnloadTaskDao.queryArriveTransferVehicle(vehicleQC,  limit,  start);
			
			if(vehicleQC.getDepartOrg().equals("")){
				if(vehicleQC.getUnloadType().equals("") ||
						vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER)){
					destOrgCodes.add("N/A");
					vehicleQC.setDestOrgCodes(destOrgCodes);
					pickUpVehicles = assignUnloadTaskDao.queryArrivePickUpVehicle(vehicleQC,  limit,  start);
				}
			}
			
			//查询到达本部门的快递集中车辆2015.02.04chenmingyan
			if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS)){
				transferExpressVehicles = assignUnloadTaskDao.queryArriveExpressVehicle(vehicleQC, limit,  start);
			}

			//查询到达本部门的商务专递  272681
			if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
				businessVehicles = assignUnloadTaskDao.queryArriveBusinessVehicle(vehicleQC, limit, start);
			}
		}else{
			String currentOutfield;
			try{
				currentOutfield = getCurrentOutfieldCode();
			}catch(TfrBusinessException e){
				return null;
			}
			//查询外场到达车辆
			if(StringUtils.isNotBlank(currentOutfield)){
				destOrgCodes.add(currentOutfield);
				//查询空运总调到达车辆
				OutfieldEntity outfieldEntity= outfieldService.queryOutfieldByOrgCode(currentOutfield);
				if(outfieldEntity != null){
					if(StringUtils.isNotBlank(outfieldEntity.getAirDispatchCode())){
						destOrgCodes.add(outfieldEntity.getAirDispatchCode());
					}
				}
				vehicleQC.setDestOrgCodes(destOrgCodes);
				transferVehicles = assignUnloadTaskDao.queryArriveTransferVehicle(vehicleQC,  limit, start);
				if(vehicleQC.getDepartOrg().equals("")){
					if(vehicleQC.getUnloadType().equals("") ||
							vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER)){
						List<String> pickUpDestOrgCodes = new ArrayList<String>();
						pickUpDestOrgCodes.add(currentOutfield);
						pickUpDestOrgCodes.add("N/A");
						vehicleQC.setDestOrgCodes(pickUpDestOrgCodes);
						pickUpVehicles = assignUnloadTaskDao.queryArrivePickUpVehicle(vehicleQC,  limit,  start);
					}
				}
				
				//查询到达本部门的快递集中车辆2015.02.04chenmingyan
				if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_EXPRESS)){
					transferExpressVehicles = assignUnloadTaskDao.queryArriveExpressVehicle(vehicleQC, limit,  start);
				}
				
				//查询到达本部门的商务专递  272681
				if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
					businessVehicles = assignUnloadTaskDao.queryArriveBusinessVehicle(vehicleQC, limit, start);
				}
				//查询零担电子面单卸车任务 322610
				if(vehicleQC.getUnloadType().equals("") || vehicleQC.getUnloadType().equals(UnloadConstants.UNLOAD_ELECTRANSPORT)){
					elecTransportVehicles = assignUnloadTaskDao.queryElecTransportVehicle(vehicleQC, limit, start);
				}
			}else{
				return null;
			}
		}
		if(CollectionUtils.isNotEmpty(pickUpVehicles)){
			oldVehicles.addAll(pickUpVehicles);
		}
		if(CollectionUtils.isNotEmpty(transferVehicles)){
			oldVehicles.addAll(transferVehicles);
		}
		if(CollectionUtils.isNotEmpty(transferExpressVehicles)){
			oldVehicles.addAll(transferExpressVehicles);
		}
		//商务专递272681
		if(CollectionUtils.isNotEmpty(businessVehicles)){
			oldVehicles.addAll(businessVehicles);
		}
		Log.info("查询零担电子面单卸车任务size=>"+elecTransportVehicles.size());
		if(CollectionUtils.isNotEmpty(elecTransportVehicles)){
			oldVehicles.addAll(elecTransportVehicles);
		}
		if(CollectionUtils.isNotEmpty(oldVehicles)){
			for(int i=0; i <= oldVehicles.size()-1;i++) {
				AssignUnloadTaskTotalDto vehicle = oldVehicles.get(i);
	            for( int j = oldVehicles.size() - 1 ; j > i; j --) {
	            	AssignUnloadTaskTotalDto tempVehicle = oldVehicles.get(j);
            		if (StringUtils.equals(vehicle.getVehicleNo(), tempVehicle.getVehicleNo())
            				&& vehicle.getArriveTime()!=null
	                        && tempVehicle.getArriveTime()!=null
	                        && vehicle.getArriveTime().equals(tempVehicle.getArriveTime())){
		                	vehicle.setUnloadType(UnloadConstants.UNLOAD_TASK_TYPE_DELIVER);
		                	vehicle.setWeightTotal(vehicle.getWeightTotal() + tempVehicle.getWeightTotal());
		                	vehicle.setVolumeTotal(vehicle.getVolumeTotal() + tempVehicle.getVolumeTotal());
		                	vehicle.setFastWayBillQtyTotal(vehicle.getFastWayBillQtyTotal() + tempVehicle.getFastWayBillQtyTotal());
		                	vehicle.setGoodsQtyTotal(vehicle.getGoodsQtyTotal() + tempVehicle.getGoodsQtyTotal());
		                	vehicle.setExpressWayBillQty(vehicle.getExpressWayBillQty()+tempVehicle.getExpressWayBillQty());
		                	vehicle.setAfWeightTotal(vehicle.getAfWeightTotal()+tempVehicle.getAfWeightTotal());
		                	vehicle.setAfVolumeTotal(vehicle.getAfVolumeTotal()+tempVehicle.getAfVolumeTotal());
		                	vehicle.setAfWayBillQtyTotal(vehicle.getAfWayBillQtyTotal()+tempVehicle.getAfWayBillQtyTotal());
		                	oldVehicles.remove(j);
		                }
	            }
	            if(StringUtils.isBlank(vehicle.getVehicleType())){
	            	VehicleAssociationDto vehicleDto = truckTaskService.getVehicle(vehicle.getVehicleNo());
	            	if(vehicleDto != null){
	            		oldVehicles.get(i).setVehicleType(vehicleDto.getVehicleLengthName());
	            	}
	            }
	        }
		}
		
		return oldVehicles;
	}
	
	
	
	/**
	 * @desc: 根据车牌号查询任务车辆id
	 * @date :2015-05-06
	 * @author:wqh
	 * 
	 * */
	public String queryTruckTaskIdByVehicleNo(String vehicleNo) {
		if(StringUtils.isEmpty(vehicleNo)){
			throw new TfrBusinessException("查询任务车辆 车牌号为空！");
		}
		String truckTaskId= assignUnloadTaskDao.queryTruckTaskIdByVehicleNo(vehicleNo);
		if(StringUtils.isNotEmpty(truckTaskId)){
			return truckTaskId;
		}else{
			return null;
		}
	}
	/**
	 * @desc: 根据数据字典valueCode查询valueName
	 * @date :2015-05-06
	 * @author:wqh
	 * 
	 * */
	public String queryDictionaryValueNameByValueCode(String valueCode) {
		
		if(StringUtil.isEmpty(valueCode)){
			throw new TfrBusinessException("查询valueCode为空！");
		}
		return assignUnloadTaskDao.queryDictionaryValueNameByValueCode(valueCode);
		
	}
	/**
	 * @desc 根据车牌号查询预分配月台号
	 * @ wqh
	 * @ date 2015-06-03 
	 * */
	public String queryPrePlatformNo(String vechicleNo,String billNo){
		//根据当前部门获取组织
		OrgAdministrativeInfoEntity loginOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(FossUserContext.getCurrentDeptCode());
		if(loginOrg == null){
			return null;
		}
		//去数据字典中拿 20150506 wqh
		String isPrePlatform=assignUnloadTaskDao.queryDictionaryValueNameByValueCode(UnloadConstants.TFR_UNLOAD_PRE_PLAMFORM_VALE);
		if("Y".equals(loginOrg.getSalesDepartment())){
			//营业部没有月台不管
			
		}else{
			//外场编码
			String currentOutfield;
			try{
				currentOutfield = getCurrentOutfieldCode();
			}catch(TfrBusinessException e){
				return null;
			}
			if(StringUtil.isNotEmpty(isPrePlatform)&&"Y".equalsIgnoreCase(isPrePlatform)){
				List<String> unloadTypeList=new ArrayList<String>();
				//长途卸车
				unloadTypeList.add("LONG_DISTANCE");
				//短途卸车
				unloadTypeList.add("SHORT_DISTANCE");

				TruckTaskDetailDto dto=queryTruckTaskDetailByBillNoAndVechicleNo(vechicleNo,billNo,currentOutfield);
				if(dto==null){
					throw new TfrBusinessException("车牌：【"+vechicleNo+"】 单据：【"+billNo+"】：没有查询到分配卸车任务！");
				}
				//如果为  长途卸车or短途卸车 需要校验是否进行了月台预分配
				String unloadType=dto.getBusinesstype();
				String goOn="Y";
				if(StringUtil.isNotEmpty(unloadType)&&unloadTypeList.contains(unloadType)){
					//查询任务车辆id
					String truckTaskId=assignUnloadTaskDao.queryTruckTaskIdByVehicleNo(vechicleNo);
					
					if(StringUtil.isNotEmpty(truckTaskId)){
						
						List<String>  handoverNos= queryHandNosByTruckTaskId(truckTaskId);
						//List<BillInfoDto> billInfoList=vehicleSealService.queryBillInfoByTruckTaskId(truckTaskId);
					   int countK=0;
					   if(handoverNos!=null&&handoverNos.size()>0){
							for(String handoverNo:handoverNos){
								if(handoverNo.contains("K")||handoverNo.contains("k")||handoverNo.contains("B")||handoverNo.contains("b")){
									countK++;
								}
							}
							//整个任务车辆都是快递则不带出月台号
							if(countK==handoverNos.size()){
								goOn="N";
							}
								
						}
						if(goOn.equals("Y")){
							//外场
							String prePlatformNo= vehicleSealService.queryPrePlatformCodeByTruckTaskId(currentOutfield, truckTaskId);
							
							prePlatformNo= vehicleSealService.queryPrePlatformCodeByTruckTaskId(currentOutfield, truckTaskId);
							//如果找不到说明该车没有预分配月台
							if(StringUtil.isEmpty(prePlatformNo)){
								throw new TfrBusinessException("车辆：【"+vechicleNo+"】 没有预分配月台");
							}
							
							if(StringUtil.isNotEmpty(prePlatformNo)){
								return prePlatformNo;
							}
							
						}
					}
					
				}
				return null;
			}
			
			
		}
		
		
		return null;
	}
	
	/***
	 * 根据单据编号，车牌号查询分配卸车任务 by wqh
	 * */
	public AssignUnloadTaskTotalDto queryAssUnloadTaskByBillNo(String vechicleNo,String billNo){
		if(StringUtil.isEmpty(vechicleNo)){
			throw new TfrBusinessException("车牌号为空！");
		}
		if(StringUtil.isEmpty(billNo)){
			throw new TfrBusinessException("单据编号为空！");
		}
		return assignUnloadTaskDao.queryAssUnloadTaskByBillNo(vechicleNo, billNo);
	}
	
	//根据 到达部门、单据编号，车牌号查询部分任务车辆信息by wqh
	public TruckTaskDetailDto queryTruckTaskDetailByBillNoAndVechicleNo(String vechicleNo,String billNo,String destOrgCode){
		if(StringUtil.isEmpty(vechicleNo)){
			throw new TfrBusinessException("车牌号为空！");
		}
		if(StringUtil.isEmpty(billNo)){
			throw new TfrBusinessException("单据编号为空！");
		}
		if(StringUtil.isEmpty(destOrgCode)){
			throw new TfrBusinessException("到达部门编码为空！");
		}
		//根据到达部门找顶级外场
		List<String> bizTypesList=new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(destOrgCode, bizTypesList);
		if(orgEntity==null){
			throw new TfrBusinessException("根据到达部门向上找顶级外场失败！");
		}
		return assignUnloadTaskDao.queryTruckTaskDetailByBillNoAndVechicleNo(vechicleNo, billNo, orgEntity.getCode());
	}
	
	//根据卸车任务id查询所有的交接单
	public List<String> queryHandNosByTruckTaskId(String truckTaskId){
		if(StringUtil.isEmpty(truckTaskId)){
			throw new TfrBusinessException("卸车任务ID为空");
		}
		return assignUnloadTaskDao.queryHandNosByTruckTaskId(truckTaskId);
		
	}
	
	/**
	 * @description FOSS同步取消分配卸车任务给悟空
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService#cancelAssignedUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCancelAssignUnloadInstructDto)
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:10:22
	 * @version V1.0
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@Override
	@Transactional
	public void cancelAssignedUnloadTask(
			ExpressCancelAssignUnloadInstructDto cancelAssignUnloadInstructDto) throws Exception {
		
		//设置 当前操作人编号
		cancelAssignUnloadInstructDto.setUpdateNo(FossUserContext.getCurrentInfo().getEmpCode());
		//设置 当前操作人部门编号
		cancelAssignUnloadInstructDto.setUpdateOrgCode(FossUserContext.getCurrentInfo().getCurrentDeptCode());
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
			
		String requestJsonStr=objectMapper.writeValueAsString(cancelAssignUnloadInstructDto);
		
		/**异步通知任务实体*/
		TfrNotifyEntity notifyEntity=new TfrNotifyEntity();
		notifyEntity.setId(UUIDUtils.getUUID());
		notifyEntity.setNotifyType(UnloadConstants.SYNC_CANCEL_ASSIGN_UNLOAD_TASK_TO_WK);
		notifyEntity.setParamJson(requestJsonStr);
		
		/**生成同步车辆任务到快递系统的异步通知任务*/
		tfrNotifyService.addTfrNotifyEntity(notifyEntity);
		
	}
	
	/**
	 * @description FOSS同步分配卸车任务给悟空
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService#assignUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressCreateAssignUnloadInstructDto)
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:33:28
	 * @version V1.0
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@Override
	@Transactional
	public void assignUnloadTask(AssignUnloadTaskVo vo) throws Exception {
		
		//快递分配卸车任务DTO
    	ExpressCreateAssignUnloadInstructDto createAssignUnloadInstructDto=new ExpressCreateAssignUnloadInstructDto();
		
    	//从分配卸车任务VO中获取数据封装到快递分配卸车任务DTO
    	AssignUnloadTaskTotalDto assignUnloadTaskTotalDto=vo.getVehicle();
    	
    	//设置 分配卸车指令编号
    	createAssignUnloadInstructDto.setCommandNo(assignUnloadTaskTotalDto.getId());
    	//设置 车牌号
    	createAssignUnloadInstructDto.setVehicleNo(assignUnloadTaskTotalDto.getVehicleNo());
    	//设置 车型
    	createAssignUnloadInstructDto.setVehicleTypeLength(assignUnloadTaskTotalDto.getVehicleType());
    	//设置 卸车类型
    	createAssignUnloadInstructDto.setUnloadType(assignUnloadTaskTotalDto.getUnloadType());
    	//设置 当前操作部门编号
    	createAssignUnloadInstructDto.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
    	//设置 到达时间
    	try {
			createAssignUnloadInstructDto.setArriveTime(new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT).parse(assignUnloadTaskTotalDto.getArriveTime()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
    	//设置 总重量
    	createAssignUnloadInstructDto.setTotalWeight(BigDecimal.valueOf(assignUnloadTaskTotalDto.getWeightTotal()));
    	//设置 总体积
    	createAssignUnloadInstructDto.setTotalVolume(BigDecimal.valueOf(assignUnloadTaskTotalDto.getVolumeTotal()));
    	//设置 总件数TODO
    	createAssignUnloadInstructDto.setTotalQty(Long.valueOf(assignUnloadTaskTotalDto.getWayBillQtyTotal()));
    	//设置 月台号
    	createAssignUnloadInstructDto.setPlatformNo(assignUnloadTaskTotalDto.getPlatformNo());
    	//设置 创建人工号
    	createAssignUnloadInstructDto.setCreateNo(FossUserContext.getCurrentInfo().getEmpCode());
    	//设置 创建时间
    	createAssignUnloadInstructDto.setCreateTime(new Date());
    	
    
		List<ExpressUnloadcmdAssignDetailDto> list=new ArrayList<ExpressUnloadcmdAssignDetailDto>();
		
		
		ExpressUnloadcmdAssignDetailDto expressUnloadcmdAssignDetailDto=null;
		ArriveBillDto arriveBillDto=null;
		//遍历待卸单据列表中的数据
		for(Iterator<ArriveBillDto> its=vo.getBills().iterator();its.hasNext();){
			arriveBillDto=its.next();
			expressUnloadcmdAssignDetailDto=new ExpressUnloadcmdAssignDetailDto();
			//设置 分配卸车指令编号
			expressUnloadcmdAssignDetailDto.setCommandNo(assignUnloadTaskTotalDto.getId());
			//设置 交接单号
			expressUnloadcmdAssignDetailDto.setHandoverBillNo(arriveBillDto.getBillNo());
			//设置 理货员编号
			expressUnloadcmdAssignDetailDto.setTallymanNo(vo.getLoader().getLoaderCode());
			//设置 理货员名称
			expressUnloadcmdAssignDetailDto.setTallymanName(vo.getLoader().getLoaderName());
			//设置 重量
			expressUnloadcmdAssignDetailDto.setWeight(BigDecimal.valueOf(arriveBillDto.getWeightTotal()));
			//设置 体积
			expressUnloadcmdAssignDetailDto.setVolume(BigDecimal.valueOf(arriveBillDto.getVolumeTotal()));
			expressUnloadcmdAssignDetailDto.setGoodsQty(new Double(arriveBillDto.getGoodsQtyTotal()).longValue());
			//设置 单据出发部门编码
			expressUnloadcmdAssignDetailDto.setHandoverBillDepartCode(arriveBillDto.getOrigOrgCode());
			
			//设置 单据出发部门名称
			expressUnloadcmdAssignDetailDto.setHandoverBillDepartName(arriveBillDto.getOrigOrgName());
			//设置 单据到达部门名称
			expressUnloadcmdAssignDetailDto.setHandoverBillDestCode(arriveBillDto.getDestOrgCode());
			
			list.add(expressUnloadcmdAssignDetailDto);
		}
		createAssignUnloadInstructDto.setUnloadcmdAssignDetailList(list);
		
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
			
		
		String requestJsonStr=objectMapper.writeValueAsString(createAssignUnloadInstructDto);
		
		/**异步通知任务实体*/
		TfrNotifyEntity notifyEntity=new TfrNotifyEntity();
		notifyEntity.setId(UUIDUtils.getUUID());
		notifyEntity.setNotifyType(UnloadConstants.SYNC_ASSIGN_UNLOAD_TASK_TO_WK);
		notifyEntity.setParamJson(requestJsonStr);
		
		/**生成同步车辆任务到快递系统的异步通知任务*/
		tfrNotifyService.addTfrNotifyEntity(notifyEntity);
		
	}
}
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
/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: TruckTaskCallESBService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToGPSService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TaskVehicleDto;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskCallESBDao;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskCallESBService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckGPSTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 任务车辆通知CRM，GPS
 * @author dp-duyi
 * @date 2013-5-27 上午11:01:55
 */
public class TruckTaskCallESBService implements ITruckTaskCallESBService{
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/** 
	 * CRM订单更新服务.
	 * 
	 */
	private ICrmOrderJMSService crmOrderJMSService;
	
	private ILineService lineService;
	
	private IExpressLineService expresslineService ;
	//调用综合接口根据车牌号查询车辆类型
	private ILeasedVehicleService leasedVehicleService;
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	/**
	 * Sets the crm order jms service.
	 * 
	 *
	 * @param crmOrderJMSService the new crm order jms service
	 */
	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * Sets the org administrative info service.
	 *
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	private ITruckTaskCallESBDao truckTaskCallESBDao;
	public void setTruckTaskCallESBDao(ITruckTaskCallESBDao truckTaskCallESBDao) {
		this.truckTaskCallESBDao = truckTaskCallESBDao;
	}
	/** 
	 * 车辆任务dao.
	 *  
	 */
	private ITruckTaskDao truckTaskDao;
	/**
	 * Sets the truck task dao.
	 *
	 * @param truckTaskDao the new truck task dao
	 */
	public void setTruckTaskDao(ITruckTaskDao truckTaskDao) {
		this.truckTaskDao = truckTaskDao;
	}
	/** 
	 * The foss to gps service. 
	 * 
	 */
	private IFOSSToGPSService fossToGPSService;
	/**
	 * Sets the foss to gps service.
	 *
	 *
	 * @param fossToGPSService the new foss to gps service
	 */
	public void setFossToGPSService(IFOSSToGPSService fossToGPSService) {
		this.fossToGPSService = fossToGPSService;
	}
	
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	private ITruckTaskService truckTaskService;
	
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}
	private ITfrCommonService tfrCommonService;
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}


	/** 
	 * @author dp-duyi
	 * @date 2013-5-27 上午11:06:27
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskCallESBService#updateCRMGoodsState(java.util.List)
	 */
	public void updateCRMGoodsState(List<HandOverBillDetailDto> handOverDetails) {
		/**更新CRM货物状态*/
		LOGGER.info("根据交接单更新CRM货物状态开始");
		if(CollectionUtils.isNotEmpty(handOverDetails)){
			List<String> handOverBills = new ArrayList<String>();
			for(HandOverBillDetailDto goods: handOverDetails){
				if(StringUtils.isNotBlank(goods.getOrderNo())){
					LOGGER.info("更新CRM订单开始：" + goods + "-" + "IN_TRANSIT");
					// 获取CRM的映射订单状态
					String crmOrderStatus = "IN_TRANSIT";
					// 为空则表明不需要将状态反馈给CRM
					if (StringUtils.isEmpty(crmOrderStatus)) {
						return;
					}
					// 更新CRM订单
					CrmModifyInfoDto crmModifyInfoDto = new CrmModifyInfoDto();
					// 订单号
					crmModifyInfoDto.setOrderNumber(goods.getOrderNo());
					// 司机姓名
					crmModifyInfoDto.setDriverName(goods.getDriverName());
					// 司机手机号
					crmModifyInfoDto.setDriverMobile(goods.getDriverPhone());
					// 操作人编码
					if(StringUtils.isEmpty(goods.getCreatorCode())){
						crmModifyInfoDto.setOprateUserNum("999999");
					}
					crmModifyInfoDto.setOprateUserNum(goods.getCreatorCode());
					// 操作人组织code
					OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(goods.getOrigOrgCode());
					if(org != null){
						crmModifyInfoDto.setOprateDeptCode(org.getUnifiedCode());
					}
					// 订单状态
					crmModifyInfoDto.setGoodsStatus(crmOrderStatus);
					// 操作备注
					//crmModifyInfoDto.setBackInfo(orderHandleDto.getOperateNotes());
					// 调用CRM订单修改接口
					LOGGER.error("任务车辆更新CRM订单状态开始"+goods.getHandOverBillNo()+" "+goods.getWaybillNo());
					crmOrderJMSService.sendModifyOrder(crmModifyInfoDto);
					LOGGER.error("任务车辆更新CRM订单状态结束"+goods.getHandOverBillNo()+" "+goods.getWaybillNo());
				}
				boolean beExists = false;
				for(String handBillNo : handOverBills){
					if(goods.getHandOverBillNo().equals(handBillNo)){
						beExists = true;
					}
				}
				if(beExists){
					handOverBills.add(goods.getHandOverBillNo());
				}
			}
		}
		LOGGER.info("根据交接单更新CRM货物状态结束");
	}
	
	public void batchFailedSynTruckGpsTask(int queryCount) {
		
		List<TaskVehicleDto> truckGPSTasks = truckTaskDao.queryFailedTruckGPSTasks(queryCount);
		List<TaskVehicleDto> truckGPSShortTasks = truckTaskDao.queryFailedShortTruckGPSTasks(queryCount);
		LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<<<长途任务件数:"+truckGPSTasks.size()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
		LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<<<短途任务件数:"+truckGPSShortTasks.size()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
		if(CollectionUtils.isNotEmpty(truckGPSTasks)){
			for(TaskVehicleDto taskVehicle : truckGPSTasks){
				String vehicleType=leasedVehicleService.queryVehicleType(taskVehicle.getVehicleNo());//获取车辆类型
				boolean flag=StringUtils.isBlank(vehicleType);//判断是否为null或""
				LOGGER.info("获取车辆类型是否为空:"+flag);
				LOGGER.info("获取的车辆类型是："+vehicleType);
				if(flag==false&&!vehicleType.equals("leasedVehicle")){//判断不是外请车执行以下操作
					try{
						LOGGER.info("非外请车执行任务，将任务车辆同步至gps>>>>>>>>>>>>>>>>>>>长途车进行中.............");
						//046130-xuduowei-foss,限制非运作到运作任务上传GPS
						//出发站编码
						String orginalOrganizationCode = taskVehicle.getStartDept();
						//到达站编码
						String destinationOrganizationCode = taskVehicle.getArrivalDept();
						LineEntity line = new LineEntity();
						line.setOrginalOrganizationCode(orginalOrganizationCode);
						line.setDestinationOrganizationCode(destinationOrganizationCode);
						//DictionaryValueConstants.BSE_LINE_SORT_TRANSFER 中转到中转
						line.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TRANSFER);
						line.setActive(FossConstants.ACTIVE);
						
						ExpressLineEntity expline = new ExpressLineEntity();
						expline.setOrginalOrganizationCode(orginalOrganizationCode);
						expline.setDestinationOrganizationCode(destinationOrganizationCode);
						//DictionaryValueConstants.BSE_LINE_SORT_TRANSFER 中转到中转
						expline.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TRANSFER);
						expline.setActive(FossConstants.ACTIVE);
						
						List<LineEntity> lineList = lineService.querySimpleLineListByCondition(line);
						LOGGER.info("查询到线路符合条件的车辆数是："+lineList.size());
						List<ExpressLineEntity> explineList = expresslineService.querySimpleLineListByCondition(expline);
						LOGGER.info("查询到线路符合条件的快递车辆数是："+explineList.size());
						//||(explineList != null && !lineList.isEmpty())
						if(lineList != null && !lineList.isEmpty()){
							LOGGER.info("符合条件的线路条数：" + lineList.size());
							LOGGER.error("任务车辆同步车辆信息至GPS开始"+taskVehicle.getVehicleNo());
							boolean beSuccess = fossToGPSService.notifyTaskVehicleInfo(taskVehicle);
							LOGGER.info("同步至GPS是否成功:"+beSuccess);
							LOGGER.error("任务车辆同步车辆信息至GPS结束"+taskVehicle.getVehicleNo()+" "+beSuccess);
							if(beSuccess){
								TruckGPSTaskEntity taskGpsTaskEntity = new TruckGPSTaskEntity();
								taskGpsTaskEntity.setId(taskVehicle.getVehicleId());
								taskGpsTaskEntity.setBeSuccess(FossConstants.YES);
								truckTaskDao.updateTruckGPSTask(taskGpsTaskEntity);
							}else{
								truckTaskDao.updateTruckGPSTaskTimes(taskVehicle.getVehicleId());
							}
						}else{
	                        if(explineList != null && !lineList.isEmpty()){
								LOGGER.info("符合条件的线路条数：" + explineList.size());
								LOGGER.error("任务车辆同步车辆信息至GPS开始"+taskVehicle.getVehicleNo());
								boolean beSuccess = fossToGPSService.notifyTaskVehicleInfo(taskVehicle);
								LOGGER.info("同步至GPS是否成功:"+beSuccess);
								LOGGER.error("任务车辆同步车辆信息至GPS结束"+taskVehicle.getVehicleNo()+" "+beSuccess);
								if(beSuccess){
									TruckGPSTaskEntity taskGpsTaskEntity = new TruckGPSTaskEntity();
									taskGpsTaskEntity.setId(taskVehicle.getVehicleId());
									taskGpsTaskEntity.setBeSuccess(FossConstants.YES);
									truckTaskDao.updateTruckGPSTask(taskGpsTaskEntity);
								}else{
									truckTaskDao.updateTruckGPSTaskTimes(taskVehicle.getVehicleId());
								}
							}
							TruckGPSTaskEntity taskGpsTaskEntity = new TruckGPSTaskEntity();
							taskGpsTaskEntity.setId(taskVehicle.getVehicleId());
							taskGpsTaskEntity.setBeSuccess(LoadConstants.VEHICLE_GPS_TASK_NOT_FAIL);
							truckTaskDao.updateTruckGPSTask(taskGpsTaskEntity);
						}
				
					}catch(Exception e){
						//记录出错日志
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.VEHICLE_TASK_CALL_ESB.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.VEHICLE_TASK_CALL_ESB.getBizCode());
						jobProcessLogEntity.setRemark("调用GPS接口，同步长途任务id："+taskVehicle.getVehicleId()+"失败！");
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
						truckTaskDao.updateTruckGPSTaskTimes(taskVehicle.getVehicleId());
						LOGGER.error("批量同步至gps系统的待跟踪车辆失败",e);
					}
				}
			}
		}
		//用于同步短途车辆任务给GPS
		if(CollectionUtils.isNotEmpty(truckGPSShortTasks)){
			for(TaskVehicleDto taskVehicle : truckGPSShortTasks){
				String vehicleType=leasedVehicleService.queryVehicleType(taskVehicle.getVehicleNo());//获取车辆类型
				boolean flag=StringUtils.isBlank(vehicleType);//判断是否为null或""
				LOGGER.info("获取车辆类型是否为空:"+flag+"短途车...................");
				LOGGER.info("获取的车辆类型是："+vehicleType+"短途车................");
				if(flag==false&&!vehicleType.equals("leasedVehicle")){//如果不是外请车就执行以下操作
					try{
						LOGGER.info("非外请车执行任务，将任务车辆同步至gps>>>>>>>>>>>>>>>>>>>短途车进行中.............");
						//限制始发和到达线路上传GPS
						//出发站编码
						String orginalOrganizationCode = taskVehicle.getStartDept();
						//到达站编码
						String destinationOrganizationCode = taskVehicle.getArrivalDept();
						
						LineEntity line = new LineEntity();
						ExpressLineEntity expline = new ExpressLineEntity();
						line.setOrginalOrganizationCode(orginalOrganizationCode);
						line.setDestinationOrganizationCode(destinationOrganizationCode);
						line.setActive(FossConstants.ACTIVE);
						//DictionaryValueConstants.BSE_LINE_SORT_TARGET 到达线路
						line.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
						
						expline.setOrginalOrganizationCode(orginalOrganizationCode);
						expline.setDestinationOrganizationCode(destinationOrganizationCode);
						expline.setActive(FossConstants.ACTIVE);
						//DictionaryValueConstants.BSE_LINE_SORT_TARGET 到达线路
						expline.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
						LOGGER.info("短途查询开始lineListTagert.........................................");
						List<LineEntity> lineListTagert = lineService.querySimpleLineListByCondition(line);
						LOGGER.info("短途查询结束lineListTagert.........................................");
						LOGGER.info("短途查询开始explineListTagert.........................................");
						List<ExpressLineEntity> explineListTagert = expresslineService .querySimpleLineListByCondition(expline);
						LOGGER.info("短途查询结束explineListTagert.........................................");
						//DictionaryValueConstants.BSE_LINE_SORT_SOURCE 始发线路
						line.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
						LOGGER.info("短途查询开始lineListSource.........................................");
						List<LineEntity> lineListSource = lineService.querySimpleLineListByCondition(line);
						LOGGER.info("短途查询结束lineListSource.........................................");
						LOGGER.info("符合线路的车辆数为:"+lineListSource.size()+"短途车辆..............................");
						expline.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
						List<ExpressLineEntity> explineListSource = expresslineService.querySimpleLineListByCondition(expline);
						LOGGER.info("符合线路的快递车辆数为explineListSource:"+explineListSource.size()+"短途车辆..............................");
						LOGGER.info("符合线路的快递车辆数为:lineListTagert"+lineListTagert.size()+"短途车辆..............................");
						//////////////////////////////***********************************************************************/////////
						if((lineListTagert != null && !lineListTagert.isEmpty())||
								(lineListSource != null && !lineListSource.isEmpty())){
							LOGGER.info("符合条件的线路条数：" + lineListTagert.size());
							LOGGER.error("任务车辆同步车辆信息至GPS开始"+taskVehicle.getVehicleNo());
							boolean beSuccess = fossToGPSService.synchronousTaskVehicleInfo(taskVehicle);
							LOGGER.error("任务车辆同步车辆信息至GPS结束"+taskVehicle.getVehicleNo()+" "+beSuccess);
							if(beSuccess){
								TruckGPSTaskEntity taskGpsTaskEntity = new TruckGPSTaskEntity();
								taskGpsTaskEntity.setId(taskVehicle.getVehicleId());
								taskGpsTaskEntity.setBeSuccess(FossConstants.YES);
								truckTaskDao.updateTruckShortGPSTask(taskGpsTaskEntity);
							}else{
								truckTaskDao.updateTruckShortGPSTaskTimes(taskVehicle.getVehicleId());
							}
						}else{
							if((explineListTagert != null && !explineListTagert.isEmpty())||
									(explineListSource != null && !explineListSource.isEmpty())){
								LOGGER.info("符合条件的线路条数：" + explineListTagert.size());
								LOGGER.error("任务车辆同步车辆信息至GPS开始"+taskVehicle.getVehicleNo());
								boolean beSuccess = fossToGPSService.synchronousTaskVehicleInfo(taskVehicle);
								LOGGER.error("任务车辆同步车辆信息至GPS结束"+taskVehicle.getVehicleNo()+" "+beSuccess);
								if(beSuccess){
									TruckGPSTaskEntity taskGpsTaskEntity = new TruckGPSTaskEntity();
									taskGpsTaskEntity.setId(taskVehicle.getVehicleId());
									taskGpsTaskEntity.setBeSuccess(FossConstants.YES);
									truckTaskDao.updateTruckShortGPSTask(taskGpsTaskEntity);
								}else{
									truckTaskDao.updateTruckShortGPSTaskTimes(taskVehicle.getVehicleId());
								}
							}
							TruckGPSTaskEntity taskGpsTaskEntity = new TruckGPSTaskEntity();
							taskGpsTaskEntity.setId(taskVehicle.getVehicleId());
							taskGpsTaskEntity.setBeSuccess(LoadConstants.VEHICLE_GPS_TASK_NOT_FAIL);
							truckTaskDao.updateTruckShortGPSTask(taskGpsTaskEntity);
						}
					}catch(Exception e){
						//记录出错日志
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.VEHICLE_TASK_CALL_ESB.getBizName());
						jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.VEHICLE_TASK_CALL_ESB.getBizCode());
						jobProcessLogEntity.setRemark("调用GPS接口，同步短途任务id："+taskVehicle.getVehicleId()+"失败！");
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
						truckTaskDao.updateTruckShortGPSTaskTimes(taskVehicle.getVehicleId());
						LOGGER.error("批量同步至gps系统的待跟踪车辆失败",e);
					}
				}
			}
		}
	}
	/** 
	 * 
	 * @author dp-duyi
	 * @date 2013-5-27 上午11:20:16
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskCallESBService#callEsb()
	 */
	@Override
	public void callEsb(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount) {
	

		LOGGER.info("任务车辆查询更新crm交接单那开始");
		List<HandOverBillDetailDto> handOverDetails = truckTaskCallESBDao.queryUnupdateCRMHandOverBill(bizJobStartTime,bizJobEndTime,threadNo,threadCount);
		LOGGER.info("任务车辆查询更新crm交接单那结束");
		
		//同步同步失败的待跟踪车辆至gps
		LOGGER.info("同步失败的待跟踪车辆至gps开始");
		this.batchFailedSynTruckGpsTask(TaskTruckConstant.GPS_SYN_COUNT);
		LOGGER.info("同步失败的待跟踪车辆至gps结束");
		

		/**更新CRM货物状态*/
		LOGGER.info("根据交接单更新CRM货物状态开始");
		this.updateCRMGoodsState(handOverDetails);
		LOGGER.info("根据交接单更新CRM货物状态结束");
		if(CollectionUtils.isNotEmpty(handOverDetails)){
			for(HandOverBillDetailDto d : handOverDetails){
				truckTaskService.updateHandOverBillBeUpdateCRM(d.getHandOverBillNo());
			}
		}
		
	}
	
}

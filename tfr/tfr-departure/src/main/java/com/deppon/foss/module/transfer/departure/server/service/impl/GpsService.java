/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
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
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/GpsService.java
 *  
 *  FILE NAME          :GpsService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.settlement.common.api.server.service.ITruckArriveConfirmService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.TruckArriveConfirmDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.arrival.api.server.dao.IArrivalDao;
import com.deppon.foss.module.transfer.arrival.api.shared.define.ArrivalConstant;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToLMSService;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.server.service.IWkBillAddTfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcTruckConfirmArrivalResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.LMSVehicleStateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IDepartureService;
import com.deppon.foss.module.transfer.departure.api.server.service.IGpsService;
import com.deppon.foss.module.transfer.departure.api.server.service.IUpdateTaskStatusService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.QueryDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsNotifyDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsVehicleDailySummaryDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehicleInfoForGpsDTO;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.shared.define.SealConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 提供GPS接口的实现
 * 
 * 业务规则：
 * GPS跟踪的车辆为在途车辆，车辆状态为“已出发”的车辆。
 * 跟踪的车辆业务类型分为：长途、短途班车、接送货。
 * 
 * 
 * 车辆出发前：FOSS发送的系统“任务车辆”信息【FOSS传送给GPS参数】给GPS系统；
 * 
 * 
 * 定时30分钟，GPS系统调用定位跟踪接口传送数据参数：车牌号、车辆预计到达时间、
 * 车辆所处路段、当前状态（离线、运行、静止、事故车辆）、任务标识号给FOSS系统。
 * 
 * 
 * 当调GPS系统用接口失败后，会进行重试处理，重试3次后（间隔10分钟） ,
 * 若仍然调用失败，GPS系统则不用发送信息给FOSS；重新发送的信息为最新信息，
 * 假如为空，FOSS跟踪页面仍显示最后一次跟踪信息。
 * 
 * 
 * 【最后一次跟踪时间】来源“SUC-246查询待跟踪车辆”。
 * FOSS系统手动跟踪业务规则：
 * 长途车辆间隔4小时，人工手动补录车辆跟踪信息。
 * 短途车辆有需要时，人工补录车辆跟踪信息。
 * 长途车辆间隔时间为可配置（变动一次的周期较长）
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-6 上午11:30:12
 */
public class GpsService implements IGpsService {
	/****************** 日志打印 ***************************/
	private static final Logger LOGGER = LogManager.getLogger(GpsService.class);
	
	private ISharedDao sharedDao;
	
	private ITruckArriveConfirmService truckArriveConfirmService;
	
	//调用tfr-departure的接口供GPS到达时结算使用
	public void setSharedDao(ISharedDao sharedDao) {
		this.sharedDao = sharedDao;
	}
	//调用stl-common的接口供确认车辆到达后结算
	public void setTruckArriveConfirmService(
			ITruckArriveConfirmService truckArriveConfirmService) {
		this.truckArriveConfirmService = truckArriveConfirmService;
	}

	private ITfrJobTodoService tfrJobTodoService;

	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	
	/**
	 * 同步给快递车辆任务 通知表
	 */
	private ITfrNotifyService tfrNotifyService;
	
	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}
	
	// 屏蔽ECS系统接口服务类
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 屏蔽ECS系统接口服务类
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	private IWkBillAddTfrNotifyService wkBillAddTfrNotifyService;
	
	/**
	 * @param wkBillAddTfrNotifyService the wkBillAddTfrNotifyService to set
	 */
	public void setWkBillAddTfrNotifyService(IWkBillAddTfrNotifyService wkBillAddTfrNotifyService) {
		this.wkBillAddTfrNotifyService = wkBillAddTfrNotifyService;
	}
	
	private IFossToCubcService fossToCubcService;
	
	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}
	
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}
	/**
	 * GPS将车辆到达时间发送给FOSS 装有GPS且有任务的在途车辆碰触到达部门的固定电子围栏 ，
	 * GPS采集车辆到达时间后
	 * ，同步调用车辆到达时间接口，将车牌号、车辆到达时间 、任务唯一标识、发送给FOSS系统。
	 * 当调用接口失败后，会进行重试处理，重试3次后（间隔10分钟） ,若仍然调用失败，
	 * GPS系统记录日志并弹窗提示车队调度，
	 * ＧＰＳ系统停发车辆到达信息给FOSS。 FOSS系统在规定车辆到达时间之后30分钟 ，
	 * 自动检索车牌到达时间字段是否为空
	 * ，为空时，FOSS系统此条车辆信息颜色区分 ，参见业务规则SR-10。
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	@Override
	@Transactional
	public void notifyArrivaltime(String vehicleId, Date gpsArriveTime) {
		if (vehicleId == null || "".equals(vehicleId)) {
			// 传入参数不能为空
			throw new TfrBusinessException("传入参数不能为空", "");
		}
		// GPS数据更新车辆放行信息
		GpsNotifyDTO gpsNotifyDTO = new GpsNotifyDTO();
		gpsNotifyDTO.setGpsTaskId(vehicleId);
		gpsNotifyDTO.setStatus(DepartureConstant.DEPART_STATUS_DEPARTURE);
		gpsNotifyDTO.setGpsArriveTime(gpsArriveTime);
		departureDao.notifyArrivaltime(gpsNotifyDTO);
		// 更新车辆状态
		LMSVehicleStateDto lmsVehicleDto = new LMSVehicleStateDto();
		lmsVehicleDto.setState(TransferConstants.VEHICLE_STATUS_ARRIVE);
		//////////////////////////////下面的方法查询放行信息列表////////////////////////////////////////////////
		List<VehicleInfoForGpsDTO> list = departureDao
				.queryTruckTaskDetailByGpsId(vehicleId);
		if (list != null && list.size() > 0) {
			lmsVehicleDto.setVehicleNo(list.get(0).getVehicleNo());
			// 查询城市信息
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(list.get(0)
							.getDestOrgCode());
			if (org != null) {
				// 城市编码
				lmsVehicleDto.setCityCode(org.getCityCode());
			}
		}
		// 到达时间
		lmsVehicleDto.setArriveDate(gpsArriveTime);

		PDADepartDto pdaDto = new PDADepartDto();
		if (list != null && list.size() > 0) {
			// 放行的时候判断是否是集配交接单，集配交接单不能放行BUG-27052
			departureService.taskIsDistanceHandover(list.get(0).getTruckTaskId(),null,list.get(0).getDestOrgCode());
			TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
			truckTaskDetailEntity.setTruckTaskId(list.get(0).getTruckTaskId());
			truckTaskDetailEntity.setDestOrgCode(list.get(0).getDestOrgCode());
			truckTaskDetailEntity.setVehicleNo(lmsVehicleDto.getVehicleNo());
			truckTaskDetailEntity
					.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
			truckTaskDetailEntity.setId(list.get(0).getId());
			List<TruckTaskDetailEntity> truckTaskDetailList = departureDao//查询车辆所有的任务详情
					.queryTruckTaskDetailByTaskId(truckTaskDetailEntity);
			if (truckTaskDetailList == null || truckTaskDetailList.size() == 0) {
				truckTaskDetailEntity.setStatus(null);
				List<TruckTaskDetailEntity> truckList = departureDao
						.queryTruckTaskDetailByTaskId(truckTaskDetailEntity);
				if (truckList == null || truckList.size() == 0) {
					return;
				} else {
					pdaDto.setOperatingTime(gpsArriveTime);
					pdaDto.setOperator("gps");
					pdaDto.setOrgCode(truckList.get(0).getDestOrgCode());
					pdaDto.setVehicleNo(lmsVehicleDto.getVehicleNo());
				}
			} else {
				pdaDto.setOperatingTime(gpsArriveTime);
				pdaDto.setOperator("gps");
				pdaDto.setOrgCode(truckTaskDetailList.get(0).getDestOrgCode());
				pdaDto.setVehicleNo(lmsVehicleDto.getVehicleNo());
			}
			//////////////////此处需要根据TruckTaskId，DestOrgCode，VehicleNo去查询之后再做判断调用结算接口///////////////////////
			judgeIsFirstArrival(truckTaskDetailEntity);//GPS第一次到达时需要调用结算接口
			// 查看该车牌号，该到达部门，该任务下面有没有未出发的任务明细
			OperationDTO dto = new OperationDTO();
			dto.setTruckTaskId(list.get(0).getTruckTaskId());
			// 到达部门
			dto.setDestOrgCode(list.get(0).getDestOrgCode());
			dto.setId(list.get(0).getId());
			// 状态
			dto.setVehicleStatus(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART);
			List<OperationDTO> unDepartTaskDetailList = arrivalDao
					.queryTruckDetailByTaskId(dto);
			if(unDepartTaskDetailList!=null&&unDepartTaskDetailList.size()>0)
			{
				LOGGER.error("存在未出发的车辆，不能到达");
				//直接更新到达时间
				TruckTaskDetailEntity taskDetailEntity = new TruckTaskDetailEntity();
				taskDetailEntity.setId(list.get(0).getTruckTaskDetailId());
				taskDetailEntity.setActualArriveType(DepartureConstant.ACTUAL_DEPART_TYPE_GPS);
				taskDetailEntity.setActualArriveTime(gpsArriveTime);
				departureDao.updateTaskByManual(taskDetailEntity);
				// 更新LMS信息
				fossToLMSService.updateVehicleState(lmsVehicleDto);
				return;
			}
			updateTaskStatusService.updateArriveStatus(truckTaskDetailList,
					pdaDto, DepartureConstant.ACTUAL_DEPART_TYPE_GPS,
					list.get(0).getTruckTaskId(), list.get(0).getDestOrgCode());

		}
		// 更新LMS信息
		fossToLMSService.updateVehicleState(lmsVehicleDto);
		
		//新增GSP到达推送给悟空，告诉悟空车辆到达时间
		for (VehicleInfoForGpsDTO vDto : list){
			//调用ECS系统接口开关
			if (configurationParamsService.queryTfrSwitch4Ecs()) {
				/** 新增同步给快递系统 车辆任务信息 通知任务 */
				LOGGER.error("调用插入临时表，通过JOB推送数据给悟空");
				// 根据车辆任务明细ID查询出悟空交接单
				List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
						.queryWkHandOverBillByTruckTaskDetailId(vDto.getTruckTaskDetailId());
				// 已交接单为单位插入到临时表
				for (WkHandOverBillEntity entity : wkHandoverbillList) {
					String currentUserInfo = "GPS," + "GPS," + vDto.getDestOrgCode();
					String paramJson = entity.getHandoverBillNo() + "," + entity.getOperationOrgCode();
					tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(UUIDUtils.getUUID(),
							NotifyWkConstants.NOTIFY_TYPE_TRUCK_ARRIVAL, vDto.getTruckTaskDetailId(),
							BusinessSceneConstants.WK_HANDORVERBILL_HAVE_ARRIVE, currentUserInfo, paramJson));
				}
			}
		}
	}

	/**
	 * 根据车辆任务编号，到达部门编号，车牌号查找车辆任务详情，判断是否为GPS第一次到达，如果是就调用结算接口，不是什么都不做
	 * @param truckTaskDetailEntity 只需要根据车辆任务编号，到达部门编号，车牌号这3个查询条件查询就可以了
	 */
	public void judgeIsFirstArrival(TruckTaskDetailEntity truckTaskDetailEntity){
		List<TruckTaskDetailEntity> truckTaskDetailList = departureDao
				.queryJudgeIsFirstArrival(truckTaskDetailEntity);//查询车辆所有的任务详情
		//将非空校验提出来-352203
		if(truckTaskDetailList==null || truckTaskDetailList.size()<=0){
			return;
		}
			for(int k=0;k<truckTaskDetailList.size();k++){
				if(truckTaskDetailList.get(k).getStatus().equals("UNDEPART")||
						truckTaskDetailList.get(k).getStatus().equals("ONTHEWAY")){
					//注意：truckTaskDetailList中的实体的ID才是truckTaskDetailId
					List<String> vehicleBillNoList= sharedDao.getVehicleAssembByDetail(truckTaskDetailList.get(k).getId());
				    if(vehicleBillNoList!=null&&vehicleBillNoList.size()>0){
				    	for(int i=0;i<vehicleBillNoList.size();i++){
				    		String vehicleBillNo=vehicleBillNoList.get(i);
				    		if(StringUtil.isBlank(vehicleBillNo)){
				    			continue;
				    		}
				    		TruckArriveConfirmDto truckArriveConfirmDto=new TruckArriveConfirmDto();
				    		truckArriveConfirmDto.setHandleNo(vehicleBillNo);
				    		truckArriveConfirmDto.setConfirmTime(new Date());
				    		truckArriveConfirmDto.setConfirmType(SettlementDictionaryConstants.TRUCK_ARRIVE_CONFIRM);
				    		
						// 封装灰度实体，类型是配载单
						GrayParameterDto parDto = new GrayParameterDto();
						parDto.setSourceBillType(CUBCGrayUtil.SBType.PZ.getName());
						parDto.setSourceBillNos(new String[] { vehicleBillNo });
						VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());

						if (CUBCGrayContants.SYSTEM_CODE_FOSS
								.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
							// 调结算接口
							try {

								truckArriveConfirmService.truckConfirm(truckArriveConfirmDto);
							} catch (SettlementException e) {
								LOGGER.error("(在做gps到达时)调结算接口异常：" + e.getErrorCode());
								// throw new
								// TfrBusinessException("调结算接口异常："+e.getErrorCode());

							}
						} else {
							String json = com.alibaba.fastjson.JSONObject.toJSONString(truckArriveConfirmDto);
							net.sf.json.JSONObject paramJson = net.sf.json.JSONObject.fromObject(json);
							paramJson.put("empCode", FossUserContext.getCurrentInfo().getEmpCode());
							paramJson.put("empName", FossUserContext.getCurrentInfo().getEmpName());
							paramJson.put("currentDeptName", FossUserContext.getCurrentInfo().getCurrentDeptName());
							paramJson.put("currentDeptCode", FossUserContext.getCurrentInfo().getCurrentDeptCode());
							// 调CUBC接口
							CubcTruckConfirmArrivalResponse cubcTruckConfirmArrivalResponse = fossToCubcService
									.truckConfirm(truckArriveConfirmDto);
							if (null != cubcTruckConfirmArrivalResponse) {
								if (0 == cubcTruckConfirmArrivalResponse.getResult()) {
									throw new TfrBusinessException(
											"调CUBC接口异常：" + cubcTruckConfirmArrivalResponse.getReason());
								}
							} else {
								throw new TfrBusinessException("调CUBC接口异常");
							}
						}
					}

				}

			}
		}
//		}	
		
	}
	/**
	 * GPS将车辆出发时间发送给FOSS 装有GPS且有任务的出发车辆碰触出发部门的固定电子围栏 ，GPS采集车辆出发时间后
	 * ，同步调用车辆出发时间接口，将车牌号、车辆出发时间 、任务唯一标示号、发送给FOSS系统。
	 * 当调用接口失败后，会进行重试处理，重试3次后（间隔10分钟） ,若仍然调用失败，GPS系统记录日志并弹窗提示车队调度，
	 * GPS系统停发车辆出发信息给FOSS。 FOSS系统在规定车辆发车30分钟后 ，自动检索车牌出发时间字段是否为空
	 * ，为空时，FOSS系统颜色区分显示。
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-27 下午3:05:49
	 */
	@Override
	@Transactional //BUG-31067
	public void notifyStarttime(String vehicleId, Date startTime) {
		if (vehicleId == null || "".equals(vehicleId)) {
			// 传入参数不能为空
			throw new TfrBusinessException("传入参数不能为空", "");
		}
		// GPS数据更新车辆放行信息
		GpsNotifyDTO gpsNotifyDTO = new GpsNotifyDTO();
		// GPS任务ID
		gpsNotifyDTO.setGpsTaskId(vehicleId);
		// 状态
		gpsNotifyDTO.setStatus(DepartureConstant.DEPART_STATUS_DEPARTURE);
		// 放行时间
		gpsNotifyDTO.setGpsDepartTime(startTime);
		// 更新放行时间
		departureDao.notifyStarttime(gpsNotifyDTO);
		// 更新车辆状态
		LMSVehicleStateDto lmsVehicleDto = new LMSVehicleStateDto();
		lmsVehicleDto.setState(TransferConstants.VEHICLE_STATUS_DEPARTURE);
		List<VehicleInfoForGpsDTO> list = departureDao
				.queryTruckTaskDetailByGpsId(vehicleId);
		if (list != null && list.size() > 0) {
			lmsVehicleDto.setVehicleNo(list.get(0).getVehicleNo());
			lmsVehicleDto.setArriveDate(list.get(0).getPlanArriveTime());
			// 查询城市信息
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(list.get(0)
							.getOrigOrgCode());
			if (org != null) {
				// 城市编码
				lmsVehicleDto.setCityCode(org.getCityCode());
			}
			//校验同一个车辆任务不能含有 两个有运费的车次
			List<String> vehicleAssemNOs=vehicleAssembleBillService.queryTruckBillByDetailId(list.get(0).getTruckTaskDetailId(), null);
			if(CollectionUtils.isNotEmpty(vehicleAssemNOs)&&vehicleAssemNOs.size()>1){
				throw  new TfrBusinessException("同一车牌号，快递与零担均显示运费，无法出发！"); 
			}
			// 根据任务车辆明细判断是否是集配交接单，集配交接单不能出发，GPS不改变状态，只记录时间
			departureService.taskIsDistanceHandover(list.get(0).getTruckTaskId(),list.get(0).getOrigOrgCode(),null);
			//没做封签，不给放行
			SealEntity sealEntity = new SealEntity();
			sealEntity.setTruckTaskDetailId(list.get(0).getTruckTaskId());
			sealEntity.setSealType(SealConstant.SEAL_TYPE_BIND);
			List<SealEntity> seallist = vehicleSealService
					.querySealByTruckTaskId(sealEntity);
			if (seallist == null || seallist.size() <= 0) {
				LOGGER.error("未找到该车辆的封签信息，不能放行");
				throw new TfrBusinessException("未找到该车辆的封签信息，不能放行");
			}
		}else
		{
			return;
		}
		
		lmsVehicleDto.setStartDate(startTime);
		// 更新LMS信息
		fossToLMSService.updateVehicleState(lmsVehicleDto);

		//暂时取消GPS出发修改车辆的状态
		// 找到该车牌号对应的车辆放行信息,先拼装预置的条件
		QueryDepartEntity queryDepartEntity = new QueryDepartEntity();
		queryDepartEntity.setVehicleNo(lmsVehicleDto.getVehicleNo());
		// 设置状态为待放行
		List<String> statuses = new ArrayList<String>();
		statuses.add(DepartureConstant.DEPART_STATUS_WAIT);
		statuses.add(DepartureConstant.DEPART_STATUS_Fail);
		queryDepartEntity.setStatuses(statuses);
		// 查询所有待放行的信息
		List<TruckDepartEntity> truckList = departureDao
				.queryDepart(queryDepartEntity);
		for(TruckDepartEntity truckDepartEntity :truckList)
		{
			TruckDepartEntity manualEntity = new TruckDepartEntity();
			manualEntity.setId(truckDepartEntity.getId());
			manualEntity.setGpsDepartTime(startTime);
			departureDao.saveManual(manualEntity);
		}
		
		/**
		 * 因为下面的代码注释掉了，没有调用updateTaskStatusService.updateTaskStatus方法，所以在此处调用tps 的接口
		 */
		for (VehicleInfoForGpsDTO vDto : list) {
			/**
			 * 调用tps接口同步tps出发信息
			 */
//			try {
//				LOGGER.error("调用tps接口--同步到出发始!车辆任务id："+vDto.getTruckTaskDetailId());
//				this.departureService.synchDepartArriveInfoToTps(vDto.getTruckTaskDetailId(), startTime, "start");
//				LOGGER.error("调用tps接口--同步出发结束!车辆任务id："+vDto.getTruckTaskDetailId());
//			} catch (Exception e) {
//				LOGGER.error("调用tps接口--同步出发失败,错误信息："+e.toString());
//			}
			this.tfrJobTodoService.addJobTodo(vDto.getTruckTaskDetailId(),
					BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE, 
					new String[]{BusinessGoalContants.BUSINESS_GOAL_EXPRESS100,
						BusinessGoalContants.BUSINESS_GOAL_TPS,BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAOJZ},startTime, "GPS");
			//调用ECS系统接口开关
			if (configurationParamsService.queryTfrSwitch4Ecs()) {
				/** 新增同步给快递系统 车辆任务信息 通知任务 */
				LOGGER.error("调用插入临时表，通过JOB推送数据给悟空");
				// 根据车辆任务明细ID查询出悟空交接单
				List<WkHandOverBillEntity> wkHandoverbillList = wkBillAddTfrNotifyService
						.queryWkHandOverBillByTruckTaskDetailId(vDto.getTruckTaskDetailId());
				// 已交接单为单位插入到临时表
				for (WkHandOverBillEntity entity : wkHandoverbillList) {
					String currentUserInfo = "GPS," + "GPS," + vDto.getOrigOrgCode();
					String paramJson = entity.getHandoverBillNo() + "," + entity.getOperationOrgCode();
					tfrNotifyService.addTfrNotifyEntity(new TfrNotifyEntity(UUIDUtils.getUUID(),
							NotifyWkConstants.NOTIFY_TYPE_TRUCK_DEPARTURE, vDto.getTruckTaskDetailId(),
							BusinessSceneConstants.WK_HANDORVERBILL_HAVE_DEPART, currentUserInfo, paramJson));
				}
			}
		}
	}

	/**
	 * 记录GPS的轨迹
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-5 上午11:04:11
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IGpsService#updateVehicleTrack(com.deppon.foss.module.transfer.departure.api.shared.dto.GpsNotifyDTO)
	 */
	@Override
	@Transactional
	public void updateVehicleTrack(GpsNotifyDTO gpsNotifyDTO) {// 增加一条在途车辆跟踪明细
		List<VehicleInfoForGpsDTO> list = departureDao
				.queryTruckTaskDetailByGpsId(gpsNotifyDTO.getVehicleId());
		gpsNotifyDTO.setId(UUIDUtils.getUUID());
		if (list != null && list.size() > 0) {
			gpsNotifyDTO.setTruckTaskDetailId(list.get(0)
					.getTruckTaskDetailId());
			gpsNotifyDTO.setVehicleNo(list.get(0).getVehicleNo());
		}else{//352203-sonar-list未校验非空-原逻辑若未走if，会在下面判断任务id时抛异常
			throw new TfrBusinessException("根据GPS的ID未查询到车辆明细信息", "");
		}
		gpsNotifyDTO.setTrackingTime(new Date());
		// 类型为GPS
		gpsNotifyDTO.setTrackingType(DepartureConstant.ACTUAL_DEPART_TYPE_GPS);
		gpsNotifyDTO.setIsLatest(FossConstants.YES);
		if (gpsNotifyDTO.getTruckTaskDetailId() == null) {
			LOGGER.error("任务ID不能为空");
			// 任务ID不能为空
			throw new TfrBusinessException("任务ID不能为空", "");
		}
		if (gpsNotifyDTO.getVehicleNo() == null) {
			LOGGER.error("车牌号不能为空");
			// 车牌号不能为空
			throw new TfrBusinessException("车牌号不能为空", "");
		}
		// 插入轨迹信息，更新任务车辆明细的预计到达时间，更新走货路径的预计到达时间
		
		if(list.get(0).getStatus().equals(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART)||
				list.get(0).getStatus().equals(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY)){
			departureDao.updateVehicleTrack(gpsNotifyDTO);
		}
	}

	/************************ 车辆出发底层实现 ****************************/
	private IDepartureDao departureDao;
	/************************ foss传数据给LMS系统服务 ****************************/
	private IFOSSToLMSService fossToLMSService;
	/************************ 查询部门信息接口 ****************************/
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/************************ 封签服务接口 ****************************/
	IVehicleSealService vehicleSealService;
	/** The update task status service. */
	private IUpdateTaskStatusService updateTaskStatusService;
	/**
	 * ****************** 车辆服务接口 *************************.
	 */
	IDepartureService departureService;
	/******************* 到达底层接口 ***************************/
	private IArrivalDao arrivalDao;

	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	/**
	 * 设置 **********************车辆出发底层实现* ******************** ******.
	 * 
	 * @param departureDao
	 *            the new *************** *******车辆出发底层实现*****
	 *            **********************
	 */
	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	public void setDepartureService(IDepartureService departureService) {
		this.departureService = departureService;
	}

	/**
	 * 设置 ********************** foss传数据给LMS系统服务************** *************.
	 * 
	 * @param fossToLMSService
	 *            the new ********************** foss传数据给LMS系统服务 *******
	 *            ********************
	 */
	public void setFossToLMSService(IFOSSToLMSService fossToLMSService) {
		this.fossToLMSService = fossToLMSService;
	}

	/**
	 * 设置 **********************查询部门信息接口* ******************** ******.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new *************** *******查询部门信息接口*****
	 *            **********************
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setUpdateTaskStatusService(
			IUpdateTaskStatusService updateTaskStatusService) {
		this.updateTaskStatusService = updateTaskStatusService;
	}

	public void setVehicleSealService(IVehicleSealService vehicleSealService) {
		this.vehicleSealService = vehicleSealService;
	}

	public void setArrivalDao(IArrivalDao arrivalDao) {
		this.arrivalDao = arrivalDao;
	}
	
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	/**
	 *GPS供应商同步车辆信息经过DOP到ESB再到FOSS
	 * @author heyongdong
	 * @param GpsVehicleDailySummaryDTO
	 * @date 2014年11月20日 14:22:34 
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IGpsService#SynchVehicleDailySummary()
	 */
	@Override
	@Transactional
	public void SynchVehicleDailySummary(List<GpsVehicleDailySummaryDTO> vehicleDailys) {
		
		departureDao.insertVehicleDailySummary(vehicleDailys);
	}
}
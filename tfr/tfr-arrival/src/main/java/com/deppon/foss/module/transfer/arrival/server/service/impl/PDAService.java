/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  PROJECT NAME  : tfr-arrival
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/server/service/impl/PDAService.java
 *  
 *  FILE NAME          :PDAService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity;
import com.deppon.foss.module.settlement.common.api.server.service.ITruckArriveConfirmService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.TruckArriveConfirmDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.arrival.api.shared.define.ArrivalConstant;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcTruckConfirmArrivalResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IUpdateTaskStatusService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.define.ErrorConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.utils.Constants;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAArriveService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;

/**
 * PDA接口的实现.
 * 用例描述：车辆到达时，PDA扫描车辆条码时，
 * 将PDA扫描信息发送给FOSS系统，且获取FOSS成功响应。
 *
 * FOSS系统：
 * 车辆中的对应的交接单或配载单有多个，只有交接单或
 * 配载单中的交接目的部门为本部门的，才为记录为到达。
 * 
 * 
 * 车辆到达：PDA发送请求，未获得FOSS成功响应时，
 * 间隔10秒，持续发送，直至FOSS响应成功。
 * 可接受的系统延迟时间为30s，30s释放一次，再重新发送。
 * 无论响应成功与否，都允许车辆进场。
 * 
 * 
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:48:37
 */
public class PDAService implements IPDAArriveService {
	private ISharedDao sharedDao;
	public void setSharedDao(ISharedDao sharedDao) {
		this.sharedDao = sharedDao;
	}
	private ITruckArriveConfirmService truckArriveConfirmService;
	public void setTruckArriveConfirmService(
			ITruckArriveConfirmService truckArriveConfirmService) {
		this.truckArriveConfirmService = truckArriveConfirmService;
	}
	private ILeasedVehicleService LeasedVehicleService;
	
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		LeasedVehicleService = leasedVehicleService;
	}

	private IFossToCubcService fossToCubcService;

	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}
	/** *******日志*********. */
	private static final Logger LOGGER = LogManager.getLogger(PDAService.class);

	
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}

	/**
	 * 记录PDA到达时间.
	 * 
	 * @param pdaDto
	 *            the pda dto
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-31 下午6:27:07
	 */
	@Override
	@Transactional
	public String writeArriveData(PDADepartDto pdaDto) {
		
		if (pdaDto == null) {
			LOGGER.error("PDA出发，传入参数不能为空");
			// PDA出发，传入参数不能为空
			throw new TfrBusinessException(
					ErrorConstant.PDA_DEPART_CONDITION_NOT_NULL);
		}
		if (pdaDto.getVehicleNo() == null || "".equals(pdaDto.getVehicleNo())) {
			LOGGER.error("PDA出发，车牌号不能为空");
			// PDA出发，车牌号不能为空
			throw new TfrBusinessException("车牌号不能为空", "");
		}
		try {
			// 设置车牌号
			pdaDto.setVehicleNo(Constants.convertVehicleCode2Name(pdaDto
					.getVehicleNo()));
		} catch (BusinessException e) {
			LOGGER.error(e.getErrorCode());
			throw new TfrBusinessException(e.getErrorCode(), "");
		}
		// 任务车辆绑定表车如一条关联数据，先根据车牌号跟放行ID查到该任务，在进行更新
		TruckTaskDetailEntity truckTaskDetailEntity = new TruckTaskDetailEntity();
		// 车牌号
		truckTaskDetailEntity.setVehicleNo(pdaDto.getVehicleNo());
		// 状态
		truckTaskDetailEntity
				.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ONTHEWAY);
		List<TruckTaskDetailEntity> taskDetailList = departureDao
				.queryTruckTaskDetail(truckTaskDetailEntity);
		//当前存在在途的任务，并且同一任务下存在未出发的任务
		if(taskDetailList!=null&&taskDetailList.size()>0){
			// 查看该车牌号，该到达部门，该任务下面有没有未出发的任务明细
			truckTaskDetailEntity.setTruckTaskId(taskDetailList.get(0).getTruckTaskId());
			truckTaskDetailEntity.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_UNDEPART);
			truckTaskDetailEntity.setDestOrgCode(taskDetailList.get(0).getDestOrgCode());
			List<TruckTaskDetailEntity> unDepartTaskDetailList = departureDao
					.queryTruckTaskDetail(truckTaskDetailEntity);
			StringBuffer bf = new StringBuffer();
			if (unDepartTaskDetailList != null
					&& unDepartTaskDetailList.size() > 0) {
				for (TruckTaskDetailEntity op : unDepartTaskDetailList) {
					bf.append(op.getLineNo() + ",");
				}
				if (bf.length() > 0) {
					LOGGER.error("存在未出发的车辆，不能到达");
				throw new TfrBusinessException("同一任务下存在未出发的车辆，不能到达，未出发的线路是："
						+ bf.toString().substring(0,
								bf.toString().length() - 1), "");
				}
			}
			
		}
		if (null == taskDetailList || taskDetailList.size() <= 0) {
			// 如果没有取得记录，直接返回不能放行
			LOGGER.info("没有任务，查看有没有派送信息");
			// 判断是否是接送货的到达
			List<StayHandoverEntity> staylist = stayHandoverDao
					.queryByVehicleNo(pdaDto.getVehicleNo());
			if (staylist == null || staylist.size() <= 0) {
				//如果也没有派送信息，查询到达的信息
				truckTaskDetailEntity
						.setStatus(ArrivalConstant.ARRIVAL_VEHICLE_ARRIVED);
				// 调用综合接口查询出发部门是否为外场
				OrgAdministrativeInfoEntity origOrg = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(truckTaskDetailEntity
								.getOrigOrgCode());
				if (origOrg != null) {
					// 如果营业部
					if (FossConstants.YES.equals(origOrg.getSalesDepartment())) {
						SaleDepartmentEntity saleDetp = saleDepartmentService
								.querySaleDepartmentByCode(origOrg.getCode());
						if (saleDetp != null
								&& FossConstants.YES.equals(saleDetp.getStation())) {
							origOrg = pdaCommonService
									.getCurrentOutfieldCode(truckTaskDetailEntity
											.getOrigOrgCode());
						}
					}
					// 装车部门为外场
					else {
						origOrg = pdaCommonService
								.getCurrentOutfieldCode(truckTaskDetailEntity
										.getOrigOrgCode());
					}
				}
				// 外场不为空
				if (origOrg != null) {
					truckTaskDetailEntity.setDestOrgCode(origOrg.getCode());
				}// 营业部
				else {
					// 无需操作
				}
				taskDetailList = departureDao
						.queryTruckTaskDetail(truckTaskDetailEntity);
				if(taskDetailList==null||taskDetailList.size()==0){
					throw new TfrBusinessException(
							ErrorConstant.PDA_ARRIVAL_RESULT_NOT_FIND, "");
				}else{
					updateTaskStatusService.updateArriveStatus(taskDetailList, pdaDto,
							DepartureConstant.ACTUAL_DEPART_TYPE_PDA,null,null);
				}

			}
			for (StayHandoverEntity stayHandoverEntity : staylist) {
				LOGGER.info("没有任务，直接派送");
				// 增加一条到达信息
				String arriveId = updateTaskStatusService.addTruckArrival(
						pdaDto, DepartureConstant.ACTUAL_DEPART_TYPE_PDA,null);
				// 修改接送货的状态为到达
				StayHandoverEntity record = new StayHandoverEntity();
				// id
				record.setId(stayHandoverEntity.getId());
				// 实际到达时间
				record.setActualArriveTime(pdaDto.getOperatingTime());
				// 到达ID
				record.setTruckArriveId(arriveId);
				record.setAssignState("1");
				// 更新接送货的状态
				stayHandoverDao.updateByPrimaryKeySelective(record);
			}
		}
		updateTaskStatusService.updateArriveStatus(taskDetailList, pdaDto,
				DepartureConstant.ACTUAL_DEPART_TYPE_PDA,null,null);
	      LeasedTruckEntity leasedTruckEntity=LeasedVehicleService.queryLeasedVehicleByVehicleNo(pdaDto.getVehicleNo());	
	    if( leasedTruckEntity!=null){
	    	 deliveTruckConfirm(taskDetailList.get(0).getId());
	 		// 返回成功
		}
		return DepartureConstant.PDA_CAN_ARRIVAL;
	}

	/**
	 * ********************************* ****************.
	 */
	/***** 放行底层实现 ******/
	private IDepartureDao departureDao;
	/** ***接送货*****. */
	private IStayHandoverDao stayHandoverDao;
	
	/**
	 * 查询部门service接口
	 * 
	 */
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 营业部service接口
	 * 
	 */
	ISaleDepartmentService saleDepartmentService;
	/**
	 * 装卸车PDA共通service接口
	 * 
	 */
	public IPDACommonService pdaCommonService;
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/** The update task status service. */
	private IUpdateTaskStatusService updateTaskStatusService;

	/**
	 * Sets the departure dao.
	 * 
	 * @param departureDao
	 *            the new departure dao
	 */
	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	/**
	 * Sets the stay handover dao.
	 * 
	 * @param stayHandoverDao
	 *            the new stay handover dao
	 */
	public void setStayHandoverDao(IStayHandoverDao stayHandoverDao) {
		this.stayHandoverDao = stayHandoverDao;
	}

	public void setUpdateTaskStatusService(
			IUpdateTaskStatusService updateTaskStatusService) {
		this.updateTaskStatusService = updateTaskStatusService;
	}
	/**
	 * 根据车辆任务编号，到达部门编号，车牌号查找车辆任务详情，判断是否为GPS第一次到达，如果是就调用结算接口，不是什么都不做
	 * @param truckTaskDetailEntity 只需要根据车辆任务编号，到达部门编号，车牌号这3个查询条件查询就可以了
	 */
	public void judgeIsFirstArrival(TruckTaskDetailEntity truckTaskDetailEntity){
		List<TruckTaskDetailEntity> truckTaskDetailList = departureDao
				.queryJudgeIsFirstArrival(truckTaskDetailEntity);//查询车辆所有的任务详情
		if(truckTaskDetailList!=null&&truckTaskDetailList.size()>0){
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
										.truckConfirm(paramJson);
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
		}	
		
	}
	

	
	
	
	
	
	
	
	
	/**
	 * 271297  张鹏
	 * 根据车辆任务明细编号查询单据号传递数据到结算报表
	 * 20160301
	 */
	public void deliveTruckConfirm(String  truckTaskDetailId){
	    List<String> vehicleBillNoList= sharedDao.getVehicleAssembByDetail(truckTaskDetailId);
	    if(vehicleBillNoList!=null&&vehicleBillNoList.size()>0)
	    {
	    	for(int i=0;i<vehicleBillNoList.size();i++)
	    	{
	    		String vehicleBillNo=vehicleBillNoList.get(i);
	    		if(StringUtil.isBlank(vehicleBillNo))
	    		{
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
						LOGGER.error("调结算接口异常：" + e.getErrorCode());
						throw new TfrBusinessException("调结算接口异常：" + e.getErrorCode());
	
					}
				} else {
					String json = com.alibaba.fastjson.JSONObject.toJSONString(truckArriveConfirmDto);
					net.sf.json.JSONObject paramJson = net.sf.json.JSONObject.fromObject(json);
					paramJson.put("empCode", FossUserContext.getCurrentInfo().getEmpCode());
					paramJson.put("empName", FossUserContext.getCurrentInfo().getEmpName());
					paramJson.put("currentDeptName", FossUserContext.getCurrentInfo().getCurrentDeptName());
					paramJson.put("currentDeptCode", FossUserContext.getCurrentInfo().getCurrentDeptCode());
					// 调CUBC接口
					CubcTruckConfirmArrivalResponse cubcTruckConfirmArrivalResponse = fossToCubcService.truckConfirm(paramJson);
					if (null != cubcTruckConfirmArrivalResponse) {
						if (0 == cubcTruckConfirmArrivalResponse.getResult()) {
							throw new TfrBusinessException("调CUBC接口异常：" + cubcTruckConfirmArrivalResponse.getReason());
						}
					} else {
						throw new TfrBusinessException("调CUBC接口异常");
					}
				}
			}
	
		}

	}
		
}
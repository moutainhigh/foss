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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/SharedService.java
 *  
 *  FILE NAME          :SharedService.java
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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.arrival.api.server.service.IArrivalService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISharedDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IInStockForJobService;
import com.deppon.foss.module.transfer.departure.api.server.service.ISharedService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckActionDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverRefershDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.WayBillRefershDTO;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateOptimalPlatformService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 出发、到达、取消出发、取消到达、入库、取消入库，
 * 更改走货路径的状态， 更改交接单，配载单的的状态
 * 本服务用于取得需要更新的数据，现在这些任务分为三个任务去完成
 * 1、更新走货路径的状态
 * 2、更新交接单、配载单的状态
 * 3、进行，入库、并更新入库后走货路径的状态.
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:57:39
 */
public class SharedService implements ISharedService {
	/** *********** 日志 **************. */
	private static final Logger LOGGER = LogManager
			.getLogger(SharedService.class);
	/**
	 * *********** 中转公用类 **************.
	 */
	private ITfrCommonService tfrCommonService;

	/**
	 * Sets the tfr common service.
	 * 
	 * @param tfrCommonService
	 *            the new tfr common service
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	
	/**
	 * ****************** 线路服务接口 *************************.
	 */
	private ILineService lineService;
	
	/**
	 * 综合车辆
	 * */
	private IVehicleService vehicleService;
	/**
	 * ********************快递线路服务接口*******************************
	 */
	private IExpressLineService expresslineService;
	/**
	 * 查询到达数据
	 */
	private IArrivalService arrivalService;
	/**发车标准service**/
	private IDepartureStandardService departureStandardService;
	/**
	 * 更新运单的状态.
	 * 
	 * @param dto
	 *            the dto
	 * @throws TfrBusinessException
	 *             the tfr business exception
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:20:30
	 */
	private void refreshDataForWaybill(WayBillRefershDTO dto)
			throws TfrBusinessException {
		// 获取货件
		List<WayBillRefershDTO> waybilllist = sharedDao
				.queryDataForWaybillRefresh(dto);
		// 获取运单
		List<String> waybillNos = sharedDao.getWaybillNoByDetail(dto
				.getTruckTaskDetailId());
		// 存放货件的集合
		List<String> waybillNoList;
		if (waybilllist != null && waybilllist.size() > 0 && waybillNos != null
				&& waybillNos.size() > 0) {
			for (String str : waybillNos) {
				// 遍历货件找到货件对应的运单号
				waybillNoList = new ArrayList<String>();
				WayBillRefershDTO temp = new WayBillRefershDTO();
				for (WayBillRefershDTO wayBillRefershDTO : waybilllist) {
					if (str.equals(wayBillRefershDTO.getWaybillNo())) {
						waybillNoList.add(wayBillRefershDTO.getSerialNo());
						temp.setActualDepartTime(wayBillRefershDTO
								.getActualDepartTime());
						temp.setOrigOrgCode(wayBillRefershDTO.getOrigOrgCode());
						temp.setDestOrgCode(wayBillRefershDTO.getDestOrgCode());
						temp.setVehicleNo(wayBillRefershDTO.getVehicleNo());
						temp.setTruckTaskDetailId(wayBillRefershDTO
								.getTruckTaskDetailId());
						temp.setActualArriveTime(wayBillRefershDTO
								.getActualArriveTime());
					}
				}
				if (DepartureConstant.JOB_TRUCK_DEPART
						.equals(dto.getBundType())) {
					// 更新出发的运单状态
					try {
						calculateTransportPathService
								.depart(str,
										waybillNoList,
										temp.getOrigOrgCode(),
										temp.getActualDepartTime(),
										temp.getVehicleNo(),
										TransportPathConstants.TRANSPORTPATH_STATUS_DEPART,
										temp.getTruckTaskDetailId());
					} catch (Exception e) {
						LOGGER.error("出错的运单号："+str, e);
						// throw new TfrBusinessException("出错的运单号："+str);
					}
				} else if (DepartureConstant.JOB_TRUCK_ARRIVAL.equals(dto
						.getBundType())) {
					try {
						calculateTransportPathService
								.arrive(str,
										waybillNoList,
										temp.getDestOrgCode(),
										temp.getActualArriveTime(),
										TransportPathConstants.TRANSPORTPATH_STATUS_ARRIVE,
										temp.getVehicleNo());
					} catch (Exception e) {
						LOGGER.error("######################更新运单走货路径失败，出错的运单号："
								+ str);
						LOGGER.error(e.getMessage(), e);
						// throw new TfrBusinessException("出错的运单号："+str);
					}
				}
			}
		}
		for (WayBillRefershDTO wayBillRefershDTO : waybilllist) {
			try {
				if (DepartureConstant.JOB_TRUCK_DEPART_CANCLE.equals(dto
						.getBundType())) {
					// 更新取消出发的运单状态
					calculateTransportPathService.rollBack(
							wayBillRefershDTO.getWaybillNo(),
							wayBillRefershDTO.getSerialNo(),
							wayBillRefershDTO.getVehicleNo(),
							TransportPathConstants.TRANSPORTPATH_STATUS_DEPART);
				} else if (DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE
						.equals(dto.getBundType())) {
					// 更新取消到达的运单状态
					calculateTransportPathService.rollBack(
							wayBillRefershDTO.getWaybillNo(),
							wayBillRefershDTO.getSerialNo(),
							wayBillRefershDTO.getVehicleNo(),
							TransportPathConstants.TRANSPORTPATH_STATUS_ARRIVE);
				}
			} catch (Exception e) {
				LOGGER.error("######################更新运单走火路径失败，出错的运单号："
						+ wayBillRefershDTO.getWaybillNo());
				LOGGER.error(e.getMessage(), e);
				// throw new
				// TfrBusinessException("出错的运单号："+wayBillRefershDTO.getWaybillNo());
			}
		}
	}

	/**
	 * 更新交接单的状态.
	 * 
	 * @param dto
	 *            the dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:20:45
	 */
	private void refreshDataForHandoverBill(HandoverRefershDTO dto) {
		if (DepartureConstant.JOB_TRUCK_DEPART.equals(dto.getStatus())) {
			// 更新出发的交接单状态
			if (LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER == dto
					.getHandoverStates()
					|| LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER == dto
							.getHandoverStates()
					|| LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE == dto
							.getHandoverStates()) {
				// 只对状态小于出发的状态做更新
				handOverBillService.updateHandOverBillStateByNo(
						dto.getHandoverNo(),
						LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART);
			}
			// 车辆放行的时候，通知营业部有交接单到达
			String msg = truckTaskService.queryHandOverBillMsg(dto
					.getHandoverNo());
			try {
				if (msg != null) {
					// 如果不为空的话，就表示是推送给营业部的
					// 设置消息参数
					InstationJobMsgEntity msgEntity = new InstationJobMsgEntity();
					msgEntity.setId(UUIDUtils.getUUID());
					// 通过交接单编号取得制单人编码，制单人名称
					HandOverBillEntity handoverBillEntity = handOverBillService
							.queryHandOverBillByNo(dto.getHandoverNo());
					if(handoverBillEntity!=null)
					{
					// 发送人统一用系统推送
					msgEntity.setContext(msg);
					msgEntity.setReceiveOrgCode(dto.getDestOrgCode());
					msgEntity
							.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
					msgEntity
							.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
					msgEntity.setSenderCode(handoverBillEntity
							.getCreateUserCode());
					// 制单人编码
					msgEntity.setSenderName(handoverBillEntity
							.getCreateUserName());
					// 制单人名称
					msgEntity.setSenderOrgCode(handoverBillEntity
							.getDepartDeptCode());
					// 发出部门code
					msgEntity.setSenderOrgName(handoverBillEntity
							.getDepartDept());
					// 发出部门名称
					msgEntity.setPostDate(new Date());
					messageService.createBatchInstationMsg(msgEntity);
					}
				}
			} catch (Exception e) {
				LOGGER.error("通知营业部出错" + e.getMessage(), e);
			}
		} else if (DepartureConstant.JOB_TRUCK_ARRIVAL.equals(dto.getStatus())) {
			// 更新到达的运单状态
			handOverBillService.updateHandOverBillStateByNo(
					dto.getHandoverNo(),
					LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE);
		} else if (DepartureConstant.JOB_TRUCK_DEPART_CANCLE.equals(dto
				.getStatus())) {
			// 更新取消出发的运单状态
			if(LoadConstants.HANDOVER_TYPE_LONG_DISTANCE.equals(dto.getHandoverType()))
			{//配载过的改成已配载
				//已出发的才改成已配载
				if((dto.getHandoverStates()==LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART)){
					handOverBillService.updateHandOverBillStateByNo(
							dto.getHandoverNo(),
							LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE);
				}
			}else{
				handOverBillService.updateHandOverBillStateByNo(
						dto.getHandoverNo(),
						LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
			}
		} else if (DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE.equals(dto
				.getStatus())) {
			// 更新取消到达的运单状态
			handOverBillService.updateHandOverBillStateByNo(
					dto.getHandoverNo(),
					LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART);
		}
	}

	/**
	 * 更新卸车的状态（PDA、手工），执行入库的操作. 先入库，更改走货路径的的状态为入库的状态
	 * 
	 * @param dto
	 *            the dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:20:45
	 */
	private void refreshInStorage(WayBillRefershDTO dto) {
		// 查询卸车时需要更新的运单号
		List<WayBillRefershDTO> waybilllist = sharedDao
				.queryUnloadInfoByUnloadId(dto.getTruckTaskDetailId());
		// 查询卸车时取得运单号的集合
		List<String> unloadlist = sharedDao.queryWaybillByUnloadId(dto
				.getTruckTaskDetailId());
		// 取得卸车人，卸车人工号
		List<LoaderParticipationEntity> loaderlist = unloadTaskService
				.queryTaskCreatorLoaderByTaskId(dto.getTruckTaskDetailId());
		LoaderParticipationEntity loaderParticipationEntity = new LoaderParticipationEntity();
		if (loaderlist != null && loaderlist.size() > 0) {
			loaderParticipationEntity = loaderlist.get(0);
		}
		inStockForJobService.refreshInStorage(waybilllist,
				loaderParticipationEntity);
		
		
		//sonar-352203
		if(CollectionUtils.isEmpty(waybilllist)){
			return;
		}
		//返回获取的交接单实体 by linhua.yan 360903
		HandOverBillEntity billEntity = handOverBillService.queryHandOverBillByNo(waybilllist.get(0).getBillNo());
		//sonar-352203
		if(billEntity !=null && billEntity.getHandOverType().equals(LoadConstants.HANDOVER_TYPE_SALES_DEPARTMENT)){
			return;
		}
			// 更新入库后的走货路径的状态
			for (String waybillNo : unloadlist) {
				Calendar cal = Calendar.getInstance();
				long startTime = cal.getTimeInMillis();
				LOGGER.info("更新卸车的状态开始时间：" + startTime + "毫秒############运单号："
						+ waybillNo + "##########卸车总票数：" + unloadlist.size());
				unloadlist = new ArrayList<String>();
				WayBillRefershDTO wayDTO = new WayBillRefershDTO();
				for (WayBillRefershDTO wayBillRefershDTO : waybilllist) {
					if (waybillNo.equals(wayBillRefershDTO.getWaybillNo())) {
						unloadlist.add(wayBillRefershDTO.getSerialNo());
						wayDTO.setWaybillNo(waybillNo);
						wayDTO.setOrigOrgCode(wayBillRefershDTO.getOrigOrgCode());
					}
				}
				try {
					if (waybilllist.size() > 0 && null != wayDTO.getWaybillNo()) {
						if (DepartureConstant.JOB_TRUCK_UNLOAD_PDA.equals(dto
								.getBundType())) {
							// 更新PDA卸车的运单状态
							calculateTransportPathService
									.inStorageList(
											waybillNo,
											unloadlist,
											wayDTO.getOrigOrgCode(),
											TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE);
						}
						if (DepartureConstant.JOB_TRUCK_UNLOAD_MANUAL.equals(dto
								.getBundType())) {
							// 更新手工卸车的运单状态
							calculateTransportPathService
									.inStorageList(
											waybillNo,
											unloadlist,
											wayDTO.getOrigOrgCode(),
											TransportPathConstants.TRANSPORTPATH_STATUS_INSTORE);
						}
					}
				} catch (Exception e) {
					try {
						LOGGER.error("######################更新运单走货路径失败（卸车），出错的运单号："
								+ wayDTO.getWaybillNo());
						LOGGER.error(e.getMessage(), e);
						String bizCode = "";
						String bizName = "";
						// 入库，更新入库后走货路径的状态
						bizCode = TfrJobBusinessTypeEnum.TRUCK_TASK_STOCK_JOB_TRANS
								.getBizCode();
						bizName = TfrJobBusinessTypeEnum.TRUCK_TASK_STOCK_JOB_TRANS
								.getBizName();
						// 记录出错日志
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						jobProcessLogEntity.setBizName(bizName);
						jobProcessLogEntity.setBizCode(bizCode);
						jobProcessLogEntity
								.setRemark("sharedService.refreshDataForActionTask call method <doRefresh> failed");
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils
								.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance()
								.getTime());
						jobProcessLogEntity.setExecBizId(e.getMessage().substring(0, LoadConstants.SONAR_NUMBER_30));
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					} catch (Exception e1) {
						LOGGER.error("SharedService.refreshInStorage 报错:" + StringUtils.substring(e.getMessage(), 0, LoadConstants.SONAR_NUMBER_100));
					}
				}
				LOGGER.info("更新卸车的状态截至时间：" + cal.getTimeInMillis()
						+ "毫秒############运单号：" + waybillNo);
				LOGGER.info("更新卸车的状态所用时间：" + (cal.getTimeInMillis() - startTime)
						+ "毫秒############运单号：" + waybillNo);
			}
//		}
//		}
	}

	/**
	 * 更新走货路径的状态.
	 * 
	 * @param dto
	 *            the dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:20:45
	 */
	private void refreshTruckDepartPlanDetail(HandoverRefershDTO dto) {
		if (DepartureConstant.JOB_TRUCK_DEPART.equals(dto.getStatus())) {
			// 更新出发的发车计划的状态
			sharedDao.updateTruckDepartPlanDetailStatus(dto);
		} else if (DepartureConstant.JOB_TRUCK_DEPART_CANCLE.equals(dto
				.getStatus())) {
			// 更新取消出发的发车计划的状态
			sharedDao.updateTruckDepartPlanNotDetailStatus(dto);
		}
	}

	/**
	 * 更新配载单的状态.
	 * 
	 * @param dto
	 *            the dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:20:45
	 */
	private void refreshDataForVehicleAssembelBill(HandoverRefershDTO dto) {
		if (DepartureConstant.JOB_TRUCK_DEPART.equals(dto.getStatus())) {
			// 更新出发的配载单状态
			vehicleAssembleBillService.updateVehicleAssembleBillStateByVNo(
					dto.getHandoverNo(),
					LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_DEPART);
			// 整车跟外请车出发时、外请车有尾款的车辆已出发后，车牌号、预计到达时间、尾款金额要通知到达部门；
			try {
				// 根据配载单号查询配载单实体
				List<VehicleAssembleBillEntity> list = vehicleAssembleBillService
						.queryVehicleAssembleBillByNo(dto.getHandoverNo());
				if (list != null && list.size() > 0) {
					// 整理消息内容
					StringBuffer bf = new StringBuffer();
					bf.append("从部门");
					bf.append(list.get(0).getDestOrgName());
					bf.append("出发的外请车车牌：");
					bf.append(list.get(0).getVehicleNo());
					bf.append("，尾款：");
					bf.append(list.get(0).getArriveFeeTotal());
					bf.append("元，预计");
					bf.append(DepartureHandle.getDateStr(dto
							.getPlanArriveTime()));
					bf.append("到达本部门，请周知");
					// 设置消息参数
					InstationJobMsgEntity msgEntity = new InstationJobMsgEntity();
					msgEntity.setId(UUIDUtils.getUUID());
					// 发送人统一用系统推送
					// msgEntity.setContext(msg);
					msgEntity.setReceiveOrgCode(list.get(0).getDestOrgCode());
					msgEntity
							.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
					msgEntity
							.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
					msgEntity.setSenderCode(list.get(0).getOrigOrgCode());
					// 制单人编码
					msgEntity.setSenderName(list.get(0).getCreateUserCode());
					// 制单人名称
					msgEntity.setSenderOrgCode(list.get(0).getCreateUserName());
					// 发出部门code
					msgEntity.setSenderOrgName(list.get(0).getOrigOrgName());
					// 发出部门名称
					msgEntity.setPostDate(new Date());
					messageService.createBatchInstationMsg(msgEntity);
				}
			} catch (BusinessException e) {
				LOGGER.error("通知到达部门尾款发生错误" + e.getMessage(), e);
			} catch (Exception e) {
				LOGGER.error("通知到达部门尾款发生错误" + e.getMessage(), e);
			}
		} else if (DepartureConstant.JOB_TRUCK_ARRIVAL.equals(dto.getStatus())) {
			// 更新到达的配载单状态
			vehicleAssembleBillService.updateVehicleAssembleBillStateByVNo(
					dto.getHandoverNo(),
					LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_ARRIVE);
		} else if (DepartureConstant.JOB_TRUCK_DEPART_CANCLE.equals(dto
				.getStatus())) {
			// 更新取消出发的配载单状态
			vehicleAssembleBillService.updateVehicleAssembleBillStateByVNo(
					dto.getHandoverNo(),
					LoadConstants.VEHICLEASSEMBLEBILL_STATE_NOT_DEPART);
		} else if (DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE.equals(dto
				.getStatus())) {
			// 更新取消到达的配载单状态
			vehicleAssembleBillService.updateVehicleAssembleBillStateByVNo(
					dto.getHandoverNo(),
					LoadConstants.VEHICLEASSEMBLEBILL_STATE_ALREADY_DEPART);
		}
	}

	/**
	 * 到达或者撤销到达时往PKP的job表插一条记录. 到达跟取消到达时，需要关闭或重新打开GPS任务
	 * 
	 * @param action
	 *            the action
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-6 上午9:54:26
	 */
	private void insertTempForPKP(TruckActionDetailEntity action) {
		AutoTaskDTO dto = new AutoTaskDTO();
		dto.setId(UUIDUtils.getUUID());
		dto.setTaskDetailId(action.getTruckTaskDetailId());
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(action.getDestOrgCode());
		String currentOrgCode;
		try {
			if (FossConstants.YES.equals(orgAdministrativeInfoEntity
					.getAirDispatch())) {
				// 空运中心的编码转成外场的编码
				currentOrgCode = outfieldService
						.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity
								.getCode());
				if (DepartureConstant.JOB_TRUCK_ARRIVAL.equals(action
						.getBundType())) {
					// 到达
					dto.setTaskDetailType(DepartureConstant.ARRIVAL_FOR_PKP_ARI);
					dto.setStockOrgCode(currentOrgCode);
					sharedDao.insertTempForPKP(dto);
				} else if (DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE
						.equals(action.getBundType())) {
					// 取消到达
					dto.setTaskDetailType(DepartureConstant.CANCLE_ARRIVAL_FOR_PKP);
					dto.setStockOrgCode("N/A");
					sharedDao.insertTempForPKP(dto);
				}
			} else {
				if (DepartureConstant.JOB_TRUCK_ARRIVAL.equals(action
						.getBundType())) {
					// 到达
					dto.setTaskDetailType(DepartureConstant.ARRIVAL_FOR_PKP);
					dto.setStockOrgCode("N/A");
					sharedDao.insertTempForPKP(dto);
				} else if (DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE
						.equals(action.getBundType())) {
					// 取消到达
					dto.setTaskDetailType(DepartureConstant.CANCLE_ARRIVAL_FOR_PKP);
					dto.setStockOrgCode("N/A");
					sharedDao.insertTempForPKP(dto);
				}
			}
			//出发的时候更新走货预计到达时间
			try {
				//352203-sonar
				if(!DepartureConstant.JOB_TRUCK_DEPART.equals(action
						.getBundType()))
				{
					return;
				}
					
					
					//计算预计到达时间
				//352203-sonar
					if(action.getActualDepartTime()==null || action.getPlanArriveTime()==null || action.getPlanDepartTime()==null)
					{
						return;
					}
						//Integer runingTimes = action.getRuningTimes();
						//取得时间相差的分钟数
//						int l = DateUtils.getMinuteDiff(action.getPlanDepartTime(),action.getPlanArriveTime()).intValue();
//						Date planArriveTime = DepartureHandle.adddate(action.getActualDepartTime(), 0, l);
						//出发时间加上预计在途的时间
						Long aging = 0L;
						/**
						 * @desc 这里需要重新去拿运行时效
						 * @author wqh
						 * @date 2014-11-26
						 * */
						Date planArriveTime=action.getActualDepartTime();
						//根据任务车辆明细id去查询任务车辆
						TruckTaskDetailEntity truckTaskDetailEntity=departureDao.queryTruckTaskDetailById(action.getId());
						//352203-sonar
						if(truckTaskDetailEntity==null || truckTaskDetailEntity.getVehicleNo()==null)
						{
							return;
						}
							
							//根据车牌去查车辆所属类型
							String vehicleNo=truckTaskDetailEntity.getVehicleNo();
							VehicleAssociationDto vehicleAssociationDto=vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
							
							String vehicleOwnerType= vehicleAssociationDto.getVehicleOwnershipType();
							LOGGER.error("开始获取线路信息");
							LineEntity line=getLine(truckTaskDetailEntity.getOrigOrgCode(),truckTaskDetailEntity.getDestOrgCode());
							/**
							 * @author wqh
							 * @date 2014-01-14
							 * @desc 处理 始发 及 到达路段
							 * */
							ExpressLineEntity expressLine = null;
							LOGGER.error("获取线路信息结束");
							if (line == null) {
								LOGGER.error("开始获取快递线路信息");
								//如果零担线路为null，则查询快递线路
								expressLine = getExpressLine(truckTaskDetailEntity.getOrigOrgCode(), truckTaskDetailEntity.getDestOrgCode());
								LOGGER.error("获取快递线路信息结束");
								if(expressLine == null){
									LOGGER.error("线路为空, 不跟新预计到达时间!");
									return ;
								}
							}
							LOGGER.error("获取线路信息结束");
							String lineSort =null;
							if(line!=null){
								lineSort = line.getLineSort();
							}else{
								lineSort = expressLine.getLineSort();
							}
							
							if (DictionaryValueConstants.BSE_LINE_SORT_TRANSFER.equals(lineSort) && line != null) {
								// 运作到运作线路，公司车取卡车时效,外请车取普车时效
								aging = DepartureConstant.TRUCK_TYPE_OWN.equals(vehicleOwnerType) ? line.getFastAging() : line.getCommonAging();
								Integer runingTimes = (int)(aging/LoadConstants.SONAR_NUMBER_1000*LoadConstants.SONAR_NUMBER_60);
								
								planArriveTime = DepartureHandle.adddate(action.getActualDepartTime(), 0, runingTimes);
								/*TruckTaskDetailEntity entity = new TruckTaskDetailEntity();
								entity.setId(action.getTruckTaskDetailId());
								entity.setPlanArriveTime(planArriveTime);
								departureDao.updateTaskByManual(entity);*/
							}else{

								//注：下面的代码是copy过来的
								// 线路虚拟编码
								String lineVirtualCode = null;
								if(line != null){
									lineVirtualCode = line.getVirtualCode();
								}else{
									lineVirtualCode = expressLine.getVirtualCode();
								}
								if (StringUtils.isNotEmpty(lineVirtualCode)) {
									// 根据线路虚拟编码获取对应发车标准
									LOGGER.error("开始获取发车标准");
									List<DepartureStandardEntity> departStandards = departureStandardService
											.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);
									LOGGER.error("结束获取发车标准");
									if (CollectionUtils.isNotEmpty(departStandards)) {
										// 取第一班发车标准，根据到达出发时间，到达时间计算时效(千分之小时)
										DepartureStandardEntity departStandard = departStandards
												.get(0);

										DateFormat dateFormat = new SimpleDateFormat("HHmm");

										// 出发时间
										Date leaveTime = null;
										// 到达时间
										Date arriveTime = null;
										try {
											leaveTime = dateFormat.parse(departStandard
													.getLeaveTime());
											arriveTime = dateFormat.parse(departStandard
													.getArriveTime());
										} catch (ParseException e) {
											LOGGER.error("departStandard.getLeaveTime() = " + departStandard.getLeaveTime());
											LOGGER.error("departStandard.getArriveTime() = " + departStandard.getArriveTime());

											LOGGER.error(e.getMessage());
											return ;
										}

										// 天数
										Long arriveDay = departStandard.getArriveDay() == null ? 0L
												: departStandard.getArriveDay();
										// 时效(千分之小时)
										aging = (new BigDecimal(arriveTime.getTime()
												- leaveTime.getTime()).divide(new BigDecimal(
												LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_60), 0, BigDecimal.ROUND_HALF_UP)).longValue()
												+ arriveDay * LoadConstants.SONAR_NUMBER_24 * LoadConstants.SONAR_NUMBER_1000;
										GregorianCalendar calendar = new GregorianCalendar();
										calendar.setTime(action.getActualDepartTime());
										calendar.add(Calendar.SECOND,
												new Long(aging * LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_60 / LoadConstants.SONAR_NUMBER_1000).intValue());
										LOGGER.error("计算预计到达时间结束");
										
									    planArriveTime = calendar.getTime();
										
										
									}
									
								}
							}
							LOGGER.error("开始更新预计到达时间");
							TruckTaskDetailEntity entity = new TruckTaskDetailEntity();
							entity.setId(action.getTruckTaskDetailId());
							entity.setPlanArriveTime(planArriveTime);
							departureDao.updateTaskByManual(entity);
							LOGGER.error("更新预计到达时间结束");
//						}
						
//					}
//				}
			} catch (Exception e) {
				LOGGER.info("出发后更新预计到达时间失败");
			}
		} catch (Exception e) {
			LOGGER.info("到达或者撤销到达时往PKP的job表插一条记录");
		}
	}
	
	/**
	 * 根据出发、到达部门获取线路 ,此方法从起来地方copy过来的哈，希望原作者不要介意啊
	 * @param sourceCode
	 * @param targetCode
	 * @return LineEntity
	 * @date 2014-11-26
	 * @author wqh
	 */
	private LineEntity getLine(String sourceCode, String targetCode) {

		LOGGER.error("sourceCode = " + sourceCode + "targetCode = " + targetCode);
		if (StringUtils.isEmpty(sourceCode) || StringUtils.isEmpty(targetCode)) {
			return null;
		}

		LineEntity condition = new LineEntity();
		condition.setOrginalOrganizationCode(sourceCode);
		condition.setDestinationOrganizationCode(targetCode);
		condition.setValid(FossConstants.YES);
		condition.setActive(FossConstants.ACTIVE);

		// 根据两点查询线路
		List<LineEntity> lineList = lineService
				.querySimpleLineListByCondition(condition);

		if (CollectionUtils.isEmpty(lineList)) {
			return null;
		}

		// 若只有一条，则返回
		if (lineList.size() == 1) {
			return lineList.get(0);
		}

		// 始发线路，取汽运线路；运作到运作，取专线线路，到达线路两部门间只有一条
		String lineType = null;
		String transType = null;
		for (int i = 0, size = lineList.size(); i < size; i++) {
			LineEntity line = lineList.get(i);
			transType = line.getTransType();
			lineType = line.getLineType();
			if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN
					.equals(transType)
					|| DictionaryValueConstants.BSE_LINE_TYPE_ZHUANXIAN
							.equals(lineType)) {
				return line;
			}
			
			//数据正常的情况，上面的部分一定会取到正确的线路
			if(i == size -1){
				return line;
			}
		}

		return null;
	}
	
	
	/**
	 * @desc 处理快递线路
	 */
	private ExpressLineEntity getExpressLine(String sourceCode, String targetCode) {

		LOGGER.error("sourceCode = " + sourceCode + "targetCode = " + targetCode);
		if (StringUtils.isEmpty(sourceCode) || StringUtils.isEmpty(targetCode)) {
			return null;
		}

		ExpressLineEntity condition = new ExpressLineEntity();
		condition.setOrginalOrganizationCode(sourceCode);
		condition.setDestinationOrganizationCode(targetCode);
		condition.setValid(FossConstants.YES);
		condition.setActive(FossConstants.ACTIVE);

		// 根据两点查询线路
		List<ExpressLineEntity> expressLineList = expresslineService
				.querySimpleLineListByCondition(condition);

		if (CollectionUtils.isEmpty(expressLineList)) {
			return null;
		}

		// 若只有一条，则返回
		if (expressLineList.size() == 1) {
			return expressLineList.get(0);
		}

		// 始发线路，取汽运线路；运作到运作，取专线线路，到达线路两部门间只有一条
		String lineType = null;
		String transType = null;
		for (int i = 0, size = expressLineList.size(); i < size; i++) {
			ExpressLineEntity line = expressLineList.get(i);
			transType = line.getTransType();
			lineType = line.getLineType();
			if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN
					.equals(transType)
					|| DictionaryValueConstants.BSE_LINE_TYPE_ZHUANXIAN
							.equals(lineType)) {
				return line;
			}
			
			//数据正常的情况，上面的部分一定会取到正确的线路
			if(i == size -1){
				return line;
			}
		}

		return null;
	}
	/**
	 * 出发时需要插入月台的信息，取消时需要删除
	 * 
	 * @param action
	 *            the action
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-6 上午9:54:26
	 */
	private void updatePlatform(TruckActionDetailEntity action,
			List<HandoverRefershDTO> handoverList) {
		try {
			if (DepartureConstant.JOB_TRUCK_DEPART.equals(action.getStatus())) {
				// 出发需要取出交接单号
				if (handoverList == null || handoverList.size() <= 0)
					return;
				List<String> strlist = new ArrayList<String>();

				// 判断当前登录部门是否是空运总调
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(handoverList.get(0)
								.getDestOrgCode());
				if (StringUtil.equals(FossConstants.YES,
						orgAdministrativeInfoEntity.getAirDispatch())) {
					try {
						String transferCode = outfieldService
								.queryTransferCenterByAirDispatchCode(orgAdministrativeInfoEntity
										.getCode());
						handoverList.get(0).setDestOrgCode(transferCode);
					} catch (Exception e) {
						LOGGER.error("转换部门出错" + action.getTruckTaskDetailId());
					}
				}
				for (HandoverRefershDTO dto : handoverList) {
					strlist.add(dto.getHandoverNo());
				}
				// 车辆出发后更新月台的信息
				calculateOptimalPlatformService.calcOptimalPlatformJob(action
						.getTruckTaskDetailId(), strlist, handoverList.get(0)
						.getDestOrgCode(), handoverList.get(0).getVehicleNo());
			} else if (DepartureConstant.JOB_TRUCK_DEPART_CANCLE.equals(action
					.getStatus())) {
				// 车辆取消出发后删除月台的信息
				calculateOptimalPlatformService.deleteOptimalPlatformJob(action
						.getTruckTaskDetailId());
			}
		} catch (BusinessException e) {
			LOGGER.error("放行时更新月台信息出错，出错的任务车辆明细ID"
					+ action.getTruckTaskDetailId());
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("放行时更新月台信息出错，出错的任务车辆明细ID"
					+ action.getTruckTaskDetailId());
			LOGGER.error(e.getMessage(), e);
			throw new TfrBusinessException(e.getMessage(),e);
		}
	}

	/**
	 * 单件出入库插入pkp临时表记录.
	 * 
	 * @param dto
	 *            dto.taskDetailId 运单号 dto.taskDetailType 单票入库-I ；单票出库-O
	 *            dto.ioQty 单票出入库数量 dto.stockOrgCode 单票出入库部门CODE
	 * @throws TfrBusinessException
	 *             the tfr business exception
	 * @author ibm-wangfei
	 * @date Feb 25, 2013 1:39:01 PM
	 */
	@Override
	public void insertIOTempForPKP(AutoTaskDTO dto) throws TfrBusinessException {
		if (dto == null || StringUtil.isEmpty(dto.getTaskDetailId())
				|| StringUtil.isEmpty(dto.getTaskDetailType())
				|| dto.getIoQty() == null
				|| StringUtil.isEmpty(dto.getStockOrgCode())) {
			throw new TfrBusinessException("必输字段必须录入");
		}
		Calendar cal = Calendar.getInstance();
		long startTime = cal.getTimeInMillis();
		LOGGER.info("更新PKP的状态开始时间：" + startTime);
		sharedDao.insertIOTempForPKP(dto);
		cal = Calendar.getInstance();
		LOGGER.info("更新PKP的状态截至时间：" + cal.getTimeInMillis());
		LOGGER.info("更新PKP的状态所用时间：" + (cal.getTimeInMillis() - startTime));
	}

	/**
	 * 更新运单、交接单状态.
	 * 
	 * @param taskFlag
	 *            the task flag
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 上午10:45:22
	 */
	@Override
	public void refreshDataForActionTask(String taskFlag) {
		// 取得所有的未完成状态更新的车辆任务
		TruckActionDetailEntity truckActionDetailEntity = new TruckActionDetailEntity();
		if (DepartureConstant.TASK_FLAG_CALCULATE_TRANSPORT_PATH
				.equals(taskFlag)) {
			// 更新走火路径的状态,需要传如两个条件，类型不是卸车（PDA，手工）的状态，走货路径的状态是未完成
			truckActionDetailEntity.setJob1(DepartureConstant.JOB_NOT_START);
		} else if (DepartureConstant.TASK_FLAG_BILL_STATUS.equals(taskFlag)) {
			// 更新交接单、配载单的状态
			truckActionDetailEntity.setJob2(DepartureConstant.JOB_NOT_START);
		} else if (DepartureConstant.TASK_FLAG_INSTOCK.equals(taskFlag)) {
			// 入库，更新入库后走货路径的状态
			truckActionDetailEntity.setJob3(DepartureConstant.JOB_NOT_START);
		} else if (DepartureConstant.TASK_FLAG_PLATFORM.equals(taskFlag)) {
			// 入库，更新入库后走货路径的状态
			truckActionDetailEntity.setJob4(DepartureConstant.JOB_NOT_START);
		}
		
		while(true){
			//一次性查询出5000进行处理		
			List<TruckActionDetailEntity> actionlist = sharedDao
					.queryTruckActionDetail(truckActionDetailEntity); 		
			if(CollectionUtils.isEmpty(actionlist)){
				return;
			}
			for (TruckActionDetailEntity action : actionlist) {
				//先将状态更新为已开始
				if (DepartureConstant.TASK_FLAG_INSTOCK.equals(taskFlag)) {
					TruckActionDetailEntity a = new TruckActionDetailEntity();
					a.setJob3(DepartureConstant.JOB_START);
					a.setId(action.getId());
					sharedDao.updateActionDetail(a);
				}
				if (DepartureConstant.TASK_FLAG_CALCULATE_TRANSPORT_PATH.equals(taskFlag)) {
					TruckActionDetailEntity a = new TruckActionDetailEntity();
					a.setJob1(DepartureConstant.JOB_START);
					a.setId(action.getId());
					sharedDao.updateActionDetail(a);
				}
				try {
					String id=action.getId();
					// 更新该记录为执行中,只有入库的时候使用
//				 if("8436".equals(action.getId()))
//				 {
					int b=sharedDao.updateNotModify(id);
					if(b==0){
						continue;
					}
							
					doRefresh(new WayBillRefershDTO(), new HandoverRefershDTO(),
							action, DepartureConstant.ACTION_TASK_NOT_OFFSET,
							taskFlag);
//				 }
				} catch (Exception e) {
					// 更新该记录为未执行,只有入库的时候使用
					if (DepartureConstant.TASK_FLAG_INSTOCK.equals(taskFlag)) {
						TruckActionDetailEntity a = new TruckActionDetailEntity();
						a.setJob3(DepartureConstant.JOB_NOT_START);
						a.setId(action.getId());
						sharedDao.updateActionDetail(a);
					}
					if (DepartureConstant.TASK_FLAG_CALCULATE_TRANSPORT_PATH.equals(taskFlag)) {
						TruckActionDetailEntity a = new TruckActionDetailEntity();
						a.setJob1(DepartureConstant.JOB_NOT_START);
						a.setId(action.getId());
						sharedDao.updateActionDetail(a);
					}
					LOGGER.error("", e);
					String bizCode = "";
					String bizName = "";
					if (DepartureConstant.TASK_FLAG_CALCULATE_TRANSPORT_PATH
							.equals(taskFlag)) {
						// 更新走火路径的状态,需要传如两个条件，类型不是卸车（PDA，手工）的状态，走货路径的状态是未完成
						bizCode = TfrJobBusinessTypeEnum.TRUCK_TASK_TRANSFER_CENTER_JOB
								.getBizCode();
						bizName = TfrJobBusinessTypeEnum.TRUCK_TASK_TRANSFER_CENTER_JOB
								.getBizName();
					} else if (DepartureConstant.TASK_FLAG_BILL_STATUS
							.equals(taskFlag)) {
						// 更新交接单、配载单的状态
						bizCode = TfrJobBusinessTypeEnum.TRUCK_TASK_BILL_NO_STATUS_JOB
								.getBizCode();
						bizName = TfrJobBusinessTypeEnum.TRUCK_TASK_BILL_NO_STATUS_JOB
								.getBizName();
					} else if (DepartureConstant.TASK_FLAG_INSTOCK.equals(taskFlag)) {
						// 入库，更新入库后走货路径的状态
						bizCode = TfrJobBusinessTypeEnum.TRUCK_TASK_STOCK_JOB
								.getBizCode();
						bizName = TfrJobBusinessTypeEnum.TRUCK_TASK_STOCK_JOB
								.getBizName();
					} else if (DepartureConstant.TASK_FLAG_PLATFORM
							.equals(taskFlag)) {
						// 入库，更新入库后走货路径的状态
						bizCode = TfrJobBusinessTypeEnum.TRUCK_TASK_FLAG_PLATFORM
								.getBizCode();
						bizName = TfrJobBusinessTypeEnum.TRUCK_TASK_FLAG_PLATFORM
								.getBizName();
					}
					// 记录出错日志
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(bizName);
					jobProcessLogEntity.setBizCode(bizCode);
					jobProcessLogEntity
					.setRemark("sharedService.refreshDataForActionTask call method <doRefresh> failed");
					jobProcessLogEntity.setExceptionInfo(ExceptionUtils
							.getFullStackTrace(e));
					jobProcessLogEntity.setCreateTime(Calendar.getInstance()
							.getTime());
					jobProcessLogEntity.setExecBizId(e.getMessage());
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				}finally{
					sharedDao.updateModify(action.getId());
					
				}
			}
		}
	}

	/**
	 * 刷新单条任务绑定信息的运单、交接单的状态， 更新完之后更改绑定信息的状态.
	 * 
	 * @param dtoW
	 *            the dto w
	 * @param dtoH
	 *            the dto h
	 * @param action
	 *            the action
	 * @param flag
	 *            the flag
	 * @param taskFlag
	 *            the task flag
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-11-2 下午3:18:54
	 */
	@Transactional
	private void doRefresh(WayBillRefershDTO dtoW, HandoverRefershDTO dtoH,
			TruckActionDetailEntity action, String flag, String taskFlag) {
		if (((DepartureConstant.JOB_TRUCK_ARRIVAL.equals(action.getBundType()) || DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE
				.equals(action.getBundType())))
				&& (FossConstants.YES.equals(action.getBeCarLoad()))) {
			// 整车直接更新状态，不做处理
			// 处理完毕后更新绑定表的状态
			if (DepartureConstant.TASK_FLAG_CALCULATE_TRANSPORT_PATH
					.equals(taskFlag)) {
				// 更新走货路径的状态,需要传如两个条件，类型不是卸车（PDA，手工）的状态，走货路径的状态是未完成
				action.setJob1(DepartureConstant.JOB_END);
			} else if (DepartureConstant.TASK_FLAG_BILL_STATUS.equals(taskFlag)) {
				// 更新交接单、配载单的状态
				action.setJob2(DepartureConstant.JOB_END);
			} else if (DepartureConstant.TASK_FLAG_INSTOCK.equals(taskFlag)) {
				// 入库，更新入库后走货路径的状态
				action.setJob3(DepartureConstant.JOB_END);
			} else if (DepartureConstant.TASK_FLAG_PLATFORM.equals(taskFlag)) {
				// 入库，更新入库后走货路径的状态
				action.setJob4(DepartureConstant.JOB_END);
			}
//			sharedDao.updateActionDetail(action);
//			return;//如果为整车同样需要更新交接单配载单状态MANA-404
		}
		if (!DepartureConstant.ACTION_TASK_OFFSET.equals(flag)) {
			// 有相互抵消的动作则不需要更新状态
			dtoW.setBundType(action.getBundType());
			dtoW.setTruckTaskDetailId(action.getTruckTaskDetailId());
			dtoH.setStatus(action.getBundType());
			dtoH.setDestOrgCode(action.getDestOrgCode());
			// 获取交接单号
			List<HandoverRefershDTO> handoverList = sharedDao
					.getHandoverByDetail(action.getTruckTaskDetailId());
			// 获取配载单号
			List<String> vehicleAssembList = sharedDao
					.getVehicleAssembByDetail(action.getTruckTaskDetailId());
			if (DepartureConstant.JOB_TRUCK_DEPART.equals(dtoW.getBundType())
					|| DepartureConstant.JOB_TRUCK_ARRIVAL.equals(dtoW
							.getBundType())
					|| DepartureConstant.JOB_TRUCK_DEPART_CANCLE.equals(dtoW
							.getBundType())
					|| DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE.equals(dtoW
							.getBundType())) {
				// 放行、取消放行、到达、取消到达
				try {
					if (DepartureConstant.TASK_FLAG_CALCULATE_TRANSPORT_PATH
							.equals(taskFlag)) {
						refreshDataForWaybill(dtoW);
					}
				} catch (TfrBusinessException e) {
					LOGGER.error(
							"出发到达更新走货路径失败，失败的任务明细ID"
									+ action.getTruckTaskDetailId(), e);
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			} else if (DepartureConstant.JOB_TRUCK_UNLOAD_PDA.equals(dtoW
					.getBundType())
					|| DepartureConstant.JOB_TRUCK_UNLOAD_MANUAL.equals(dtoW
							.getBundType())) {
				// 卸车,入库
				try {
					if (DepartureConstant.TASK_FLAG_INSTOCK.equals(taskFlag)) {
						refreshInStorage(dtoW);
					}
				} catch (TfrBusinessException e) {
					LOGGER.error(
							"卸车入库失败，失败的卸车任务明细ID"
									+ action.getTruckTaskDetailId(), e);
					LOGGER.error(e.getMessage(), e);
					throw new TfrBusinessException(e.getMessage(), e);
				}
			}
			if (DepartureConstant.TASK_FLAG_BILL_STATUS.equals(taskFlag)) {
				if (null != handoverList) {
					// 更新交接单状态
					LOGGER.info("更新交接单总开始,车辆任务ID：："
							+ action.getTruckTaskDetailId() + "########总交接单数："
							+ handoverList.size());
					for (HandoverRefershDTO handover : handoverList) {
						// 更新交接单
						dtoH.setHandoverNo(handover.getHandoverNo());
						dtoH.setHandoverStates(handover.getHandoverStates());
						dtoH.setHandoverType(handover.getHandoverType());
						refreshDataForHandoverBill(dtoH);
						dtoH.setBillType("HANDOVER");
						refreshTruckDepartPlanDetail(dtoH);
					}
				}
				if (null != vehicleAssembList) {
					// 更新配载单状态
					for (String str : vehicleAssembList) {
						// 更新配载单
						dtoH.setHandoverNo(str);
						// 预计到达时间
						dtoH.setPlanArriveTime(action.getPlanArriveTime());
						// 车辆归属类型
						dtoH.setVehicleOwnerType(action.getVehicleOwnerType());
						// 是否外请车
						dtoH.setBeCarLoad(action.getBeCarLoad());
						// 更新配载单
						refreshDataForVehicleAssembelBill(dtoH);
						dtoH.setBillType("VEHICLEASSEMBLE");
						// 更新走货路径
						refreshTruckDepartPlanDetail(dtoH);
					}
				}
				// 获取快递交接单 获取交接单
				//TODO 283250
				List<HandoverRefershDTO> wkhandoverList = sharedDao
						.getWKHandoverByDetail(action.getTruckTaskDetailId());
				if (null != wkhandoverList) {
					// 更新配载单状态
					for (HandoverRefershDTO wkhandover : wkhandoverList) {
						// 更新交接单
						dtoH.setHandoverNo(wkhandover.getHandoverNo());
						dtoH.setHandoverStates(wkhandover.getHandoverStates());
						dtoH.setHandoverType(wkhandover.getHandoverType());
						this.refreshDataForWKHandoverBill(dtoH);
						dtoH.setBillType("HANDOVER");
						/**快递交接单依然用的foss发车计划数据 所以要更新*/
						if(wkhandover.getHandoverNo()!=null){
							refreshTruckDepartPlanDetail(dtoH);
						}
					}
				}
				
			}
			// 车辆出发时更新月台信息
			if (DepartureConstant.TASK_FLAG_PLATFORM.equals(taskFlag)) {
				updatePlatform(action, handoverList);
			}
		}
		if (DepartureConstant.TASK_FLAG_BILL_STATUS.equals(taskFlag)) {
			// 接送获表里插入一条数据,放在交接单更新的job里面
			insertTempForPKP(action);
		}
		// 处理完毕后更新绑定表的状态
		if (DepartureConstant.TASK_FLAG_CALCULATE_TRANSPORT_PATH
				.equals(taskFlag)) {
			// 更新走货路径的状态,需要传如两个条件，类型不是卸车（PDA，手工）的状态，走货路径的状态是未完成
			action.setJob1(DepartureConstant.JOB_END);
		} else if (DepartureConstant.TASK_FLAG_BILL_STATUS.equals(taskFlag)) {
			// 更新交接单、配载单的状态
			action.setJob2(DepartureConstant.JOB_END);
		} else if (DepartureConstant.TASK_FLAG_INSTOCK.equals(taskFlag)) {
			// 入库，更新入库后走货路径的状态
			action.setJob3(DepartureConstant.JOB_END);
		} else if (DepartureConstant.TASK_FLAG_PLATFORM.equals(taskFlag)) {
			// 入库，更新入库后走货路径的状态
			action.setJob4(DepartureConstant.JOB_END);
		}
		sharedDao.updateActionDetail(action);
	}

	
	/**
	* @description 更新快递交接单的状态.
	* @param dtoH
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年6月1日 上午10:19:12
	*/
	private void refreshDataForWKHandoverBill(HandoverRefershDTO dto) {
		if (DepartureConstant.JOB_TRUCK_DEPART.equals(dto.getStatus())) {
			// 更新出发的交接单状态
			if (LoadConstants.HANDOVERBILL_STATE_PREPARE_HANDOVER == dto
					.getHandoverStates()
					|| LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER == dto
							.getHandoverStates()
					|| LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE == dto
							.getHandoverStates()) {
				// 只对状态小于出发的状态做更新
				handOverBillService.updateWKHandOverBillStateByNo(
						dto.getHandoverNo(),
						LoadConstants.WKHANDOVERBILL_STATE_ALREADY_DEPART);
			}
			// 车辆放行的时候，通知营业部有交接单到达
			/*String msg = truckTaskService.queryHandOverBillMsg(dto
					.getHandoverNo());
			try {
				if (msg != null) {
					// 如果不为空的话，就表示是推送给营业部的
					// 设置消息参数
					InstationJobMsgEntity msgEntity = new InstationJobMsgEntity();
					msgEntity.setId(UUIDUtils.getUUID());
					// 通过交接单编号取得制单人编码，制单人名称
					HandOverBillEntity handoverBillEntity = handOverBillService
							.queryHandOverBillByNo(dto.getHandoverNo());
					if(handoverBillEntity!=null)
					{
					// 发送人统一用系统推送
					msgEntity.setContext(msg);
					msgEntity.setReceiveOrgCode(dto.getDestOrgCode());
					msgEntity
							.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
					msgEntity
							.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
					msgEntity.setSenderCode(handoverBillEntity
							.getCreateUserCode());
					// 制单人编码
					msgEntity.setSenderName(handoverBillEntity
							.getCreateUserName());
					// 制单人名称
					msgEntity.setSenderOrgCode(handoverBillEntity
							.getDepartDeptCode());
					// 发出部门code
					msgEntity.setSenderOrgName(handoverBillEntity
							.getDepartDept());
					// 发出部门名称
					msgEntity.setPostDate(new Date());
					messageService.createBatchInstationMsg(msgEntity);
					}
				}
			} catch (Exception e) {
				LOGGER.error("通知营业部出错" + e.getMessage(), e);
			}*/
		} else if (DepartureConstant.JOB_TRUCK_ARRIVAL.equals(dto.getStatus())) {
			// 更新到达的运单状态
			handOverBillService.updateWKHandOverBillStateByNo(
					dto.getHandoverNo(),
					LoadConstants.WKHANDOVERBILL_STATE_ALREADY_ARRIVE);
		} else if (DepartureConstant.JOB_TRUCK_DEPART_CANCLE.equals(dto
				.getStatus())) {
			// 更新取消出发的运单状态
			handOverBillService.updateWKHandOverBillStateByNo(
					dto.getHandoverNo(),
					LoadConstants.WKHANDOVERBILL_STATE_FORMAL_HANDOVER);
		} else if (DepartureConstant.JOB_TRUCK_ARRIVAL_CANCLE.equals(dto
				.getStatus())) {
			// 更新取消到达的运单状态
			handOverBillService.updateWKHandOverBillStateByNo(
					dto.getHandoverNo(),
					LoadConstants.WKHANDOVERBILL_STATE_ALREADY_DEPART);
		}
	}


	/**
	 * ************************ 常量 *************************.
	 */
	/************************** 定时任务底层实现 **************************/
	private ISharedDao sharedDao;
	/**
	 * ************************ 车辆任务Service *************************.
	 */
	private ITruckTaskService truckTaskService;
	/**
	 * ************************ 更新配载单 *************************.
	 */
	private IVehicleAssembleBillService vehicleAssembleBillService; // 更新配载单
	/**
	 * ************************ 更新交接单 *************************.
	 */
	private IHandOverBillService handOverBillService; // 更新交接单
	/**
	 * ************************ 更新运单 *************************.
	 */
	private ICalculateTransportPathService calculateTransportPathService;// 更新运单
	/**
	 * ************************ 系统配置参数 *************************.
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * ************************ 卸车*************************.
	 */
	private IUnloadTaskService unloadTaskService;
	
	private IDepartureDao departureDao;
	/** 站内消息. */
	private IMessageService messageService;
	/** 通过部门编码获取部门的信息. */
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/** 获取空运的信息. */
	IOutfieldService outfieldService;
	ICalculateOptimalPlatformService calculateOptimalPlatformService;

	IInStockForJobService inStockForJobService;

	/**
	 * 设置 ************************ 更新配载单*************************.
	 * 
	 * @param vehicleAssembleBillService
	 *            the new *************** ********* 更新配载单********
	 *            *****************
	 */
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	/**
	 * 设置 ************************ 常量 *************************.
	 * 
	 * @param sharedDao
	 *            the new *************** ********* 常量 **********
	 *            ***************
	 */
	public void setSharedDao(ISharedDao sharedDao) {
		this.sharedDao = sharedDao;
	}

	/**
	 * 设置 ************************ 更新运单*************************.
	 * 
	 * @param calculateTransportPathService
	 *            the new *************** ********* 更新运单*********
	 *            ****************
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * 设置 ************************ 更新交接单*************************.
	 * 
	 * @param handOverBillService
	 *            the new *************** ********* 更新交接单********
	 *            *****************
	 */
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	/**
	 * 设置 ************************ 车辆任务Service *************************.
	 * 
	 * @param truckTaskService
	 *            the new *************** ********* 车辆任务Service** **********
	 *            *************
	 */
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	/**
	 * Sets the message service.
	 * 
	 * @param messageService
	 *            the new message service
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-3-18 下午3:48:31
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ISharedService#autoCancle()
	 */
	@Override
	public void autoCancle() {
		int rule = 30;
		try {
			// 从业务规则中获取配置的放行超时时间的业务规则，默认为30分钟内，部门选择总公司
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							ConfigurationParamsConstants.TFR_PARM__CANCEL_DEPARTURE,
							FossConstants.ROOT_ORG_CODE);
			rule = Integer.valueOf(defaultBizHourSlice.getConfValue())
					.intValue();
		} catch (Exception e) {
			LOGGER.error("获取配置参数失败", e);
		}
		sharedDao.autoCancle(rule);
	}
	
	/**
	 * 整车入库Job
	 * @author 316759-wangruipeng
	 * @date 2016-09-10 上午9:47:20
	 */
	@Override
	public void pushForWholeVehicle() {
		try {
			Date pushStartTime = null;
			try{
				ConfigurationParamsEntity configurationParamsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, DepartureConstant.TFR_PRAM_PUSH_WHOLE_VEHICLE_BEGIN_TIME, FossConstants.ROOT_ORG_CODE);
				pushStartTime = com.deppon.foss.util.DateUtils.convert(configurationParamsEntity.getConfValue(), "yyyy-MM-dd HH:mm:ss");
			}catch(Exception e){
				LOGGER.error("获取配置参数失败", e);
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.TRUCK_TASK_STOCK_JOB.getBizCode());
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.TRUCK_TASK_STOCK_JOB.getBizName());
				jobProcessLogEntity.setRemark("整车入库获取开始时间获取配置参数失败！");
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
			// 设置传入参数
			TruckActionDetailEntity truckActionDetailEntity = new TruckActionDetailEntity();
			truckActionDetailEntity.setJob3(DepartureConstant.JOB_NOT_START);
			truckActionDetailEntity.setCreateTime(pushStartTime);
			// 查询未完成车辆任务绑定信息
			List<TruckActionDetailEntity> actionlist = sharedDao.pushForWholeVehicle(truckActionDetailEntity);
			// 判断车辆任务绑定信息是否为空
			//352203-sonar
			if (CollectionUtils.isEmpty(actionlist)) {
				return;
			}
				// 循环车辆任务绑定信息
				for (TruckActionDetailEntity action : actionlist) {
					// 判断是否为整车
					if (FossConstants.YES.equals(action.getBeCarLoad())) {
						LOGGER.info("=========车辆任务为整车，执行入库操作=========");
						try {
							// 在修改数据的时候先更新占位符
							int b = sharedDao.updateNotModify(action.getId());
							if (b == 0) {
								continue;
							}
							
							// 更新状态为已开始
							TruckActionDetailEntity entity = new TruckActionDetailEntity();
							entity.setJob3(DepartureConstant.JOB_START);
							entity.setId(action.getId());
							sharedDao.updateActionDetail(entity);

							// 开始执行整车入库操作
							arrivalService.pushForWholeVehicle(action.getTruckTaskDetailId());

							// 入库，更新入库后走货路径的状态
							entity.setJob3(DepartureConstant.JOB_END);
							sharedDao.updateActionDetail(entity);
						} catch (Exception e) {
							// 出现异常，将状态改为未执行状态
							TruckActionDetailEntity entity = new TruckActionDetailEntity();
							entity.setJob3(DepartureConstant.JOB_NOT_START);
							entity.setId(action.getId());
							sharedDao.updateActionDetail(entity);
							LOGGER.error("", e);
							// 入库，更新入库后状态
							String bizCode = TfrJobBusinessTypeEnum.TRUCK_TASK_STOCK_JOB
									.getBizCode();
							String bizName = TfrJobBusinessTypeEnum.TRUCK_TASK_STOCK_JOB
									.getBizName();
							// 记录出错日志
							TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
							jobProcessLogEntity.setBizName(bizName);
							jobProcessLogEntity.setBizCode(bizCode);
							jobProcessLogEntity
									.setRemark("sharedService.pushForWholeVehicle the error message");
							jobProcessLogEntity.setExceptionInfo(ExceptionUtils
									.getFullStackTrace(e));
							jobProcessLogEntity.setCreateTime(Calendar
									.getInstance().getTime());
							jobProcessLogEntity.setExecBizId(e.getMessage());
							tfrCommonService.addJobProcessLog(jobProcessLogEntity);
						} finally {
							// 无论是否异常，返回初始标识位
							sharedDao.updateModify(action.getId());
						}
					}
				}
//			}
		} catch (Exception e) {
			throw new TfrBusinessException("异常信息：" + e.getMessage());
		}
	}

	/**
	 * Sets the configuration params service.
	 * 
	 * @param configurationParamsService
	 *            the new configuration params service
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * Sets the unload task service.
	 * 
	 * @param unloadTaskService
	 *            the new unload task service
	 */
	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}

	/**
	 * Sets the org administrative info service.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * Sets the outfield service.
	 * 
	 * @param outfieldService
	 *            the new outfield service
	 */
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public void setCalculateOptimalPlatformService(
			ICalculateOptimalPlatformService calculateOptimalPlatformService) {
		this.calculateOptimalPlatformService = calculateOptimalPlatformService;
	}

	public void setInStockForJobService(
			IInStockForJobService inStockForJobService) {
		this.inStockForJobService = inStockForJobService;
	}

	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	public void setDepartureStandardService(
			IDepartureStandardService departureStandardService) {
		this.departureStandardService = departureStandardService;
	}

	public void setArrivalService(IArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}
	
	
}
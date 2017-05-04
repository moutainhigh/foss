/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/WaybillArriveTempService.java
 * 
 * FILE NAME        	: WaybillArriveTempService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IWaybillArriveTempDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybillArriveTempService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleArriveNotifyEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleArriveNotifyJZEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivalGoodsWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单到达数量、到达时间临时表实现类
 * 
 * @author ibm-wangfei
 * @date Nov 20, 2012 3:26:45 PM
 * 
 * 原为车辆到达后通知客户，现改为建立卸车任务后通知客户 DMANA-3766
 * @ClassName: WaybillArriveTempService 
 * @author 200664-yangjinheng
 * @date 2014年10月2日 上午10:04:31
 */
public class WaybillArriveTempService implements IWaybillArriveTempService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillArriveTempService.class);
	private static final int NUMBER_180 = 180;
	/**
	 * 运单到达数量、到达时间临时表DAO
	 */
	private IWaybillArriveTempDao waybillArriveTempDao;
	private IConfigurationParamsService configurationParamsService;
	private INotifyCustomerService notifyCustomerService;
	private IShareJobDao shareJobDao;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IUnloadTaskService unloadTaskService;
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 
	 * 运单到达数量、到达时间计算
	 * 
	 * @author ibm-wangfei
	 * @date Dec 27, 2012 10:49:30 AM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybillArriveTempService#preprocess()
	 */
	@Override
	public void preprocess() {
		waybillArriveTempDao.callCalculateWaybillArriveInfo();
	}

	/**
	 * 获取 运单到达数量、到达时间临时表DAO.
	 * 
	 * @param waybillArriveTempDao the new 运单到达数量、到达时间临时表DAO
	 */
	public void setWaybillArriveTempDao(IWaybillArriveTempDao waybillArriveTempDao) {
		this.waybillArriveTempDao = waybillArriveTempDao;
	}

	/**
	 * 
	 * 自动通知
	 * 
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 4:20:46 PM
	 */
	@Override
	public void autoNotify() {
		// 查询出符合条件的卸车信息数量
		VehicleArriveNotifyEntity vehicleArriveNotifyEntity = new VehicleArriveNotifyEntity();
		vehicleArriveNotifyEntity.setStatus(NotifyCustomerConstants.HANDLE_NO);
		vehicleArriveNotifyEntity.setStatusN(NotifyCustomerConstants.HANDLE_ING);
		String jobId = UUIDUtils.getUUID();
		vehicleArriveNotifyEntity.setJobIdN(jobId);
		vehicleArriveNotifyEntity.setJobId("N/A");
		// 查询的建立卸车任务时间<=（当前时间-配置时间）
		Date curdate = new Date();
		vehicleArriveNotifyEntity.setCreateTime(getOperatorDate(curdate,NotifyCustomerConstants.ARRIVE_NOTIFY_MINUTE));
		int count = this.waybillArriveTempDao.updateByParam(vehicleArriveNotifyEntity);
		boolean hasLongRecord = count > 0;
		List<VehicleArriveNotifyEntity> vehicleArriveNotifyEntityList = null;
		// 预计提货时间
		String estimatedPickupTime = getEstimatedPickupTime();
		while (hasLongRecord) {
			LOGGER.info("可执行明细记录数：{}", count);
			if (count <= NotifyCustomerConstants.ROWNUM) {
				hasLongRecord = false;
			}
			// 查询符合条件的记录数，记录数 <= NotifyCustomerConstants.ROWNUM
			vehicleArriveNotifyEntity = new VehicleArriveNotifyEntity();
			vehicleArriveNotifyEntity.setJobId(jobId);
			vehicleArriveNotifyEntity.setStatus(NotifyCustomerConstants.HANDLE_ING);
			vehicleArriveNotifyEntity.setRowNum(NotifyCustomerConstants.ROWNUM);
			vehicleArriveNotifyEntityList = this.waybillArriveTempDao.queryVehicleArriveNotify(vehicleArriveNotifyEntity);
			if (CollectionUtils.isEmpty(vehicleArriveNotifyEntityList)) {
				break;
			}
			for (VehicleArriveNotifyEntity entity : vehicleArriveNotifyEntityList) {
				// 判断卸车任务的目的地是否是营业部
				if (checkSalesDepartment(entity.getTruckTaskDetailId()) == false) {
					// 不是
					// 转储临时表
					// 添加job成功日志 执行成功将运单号及详细信息插入到t_srv_job_success_log并删除运单号
					JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
					jobSuccessLogEntity.setJobName(NotifyCustomerConstants.AUTO_WAYBILL_NOTIFY);// job名称
					jobSuccessLogEntity.setSuccessMsg("车辆任务id:" + entity.getTruckTaskDetailId() + "卸车时间：" + entity.getCreateTime() + " 非到达营业部，直接转储"); // 设置成功运单编号
					jobSuccessLogEntity.setSuccessId(entity.getId());
					this.shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
					// 删除临时表
					waybillArriveTempDao.deleteByPrimaryKey(entity.getId());
					continue;
				}
				execute(entity, estimatedPickupTime);
				// 添加job成功日志 执行成功将运单号及详细信息插入到t_srv_job_success_log并删除运单号
				JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
				jobSuccessLogEntity.setJobName(NotifyCustomerConstants.AUTO_WAYBILL_NOTIFY);// job名称
				jobSuccessLogEntity.setSuccessMsg("车辆任务id:" + entity.getTruckTaskDetailId() + "卸车时间：" + entity.getCreateTime() + "自动通知成功"); // 设置成功运单编号
				jobSuccessLogEntity.setSuccessId(entity.getId());
				this.shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
				// 删除临时表
				waybillArriveTempDao.deleteByPrimaryKey(entity.getId());
			}
			// 减去已执行的记录数
			count -= NotifyCustomerConstants.ROWNUM;
		}
	}

	/**
	 * 
	 * 按车辆任务ID执行
	 * 
	 * @param entity
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 5:16:29 PM
	 */
	private void execute(VehicleArriveNotifyEntity entity, String estimatedPickupTime) {
		// 查询符合条件的运单记录
		List<NotifyCustomerDto> dtos = waybillArriveTempDao.queryAutoWaybillInfo(entity.getTruckTaskDetailId());
		if (CollectionUtils.isEmpty(dtos)) {
			return;
		}
		boolean  isPilotOrgCode =false;
	//校验当前部门是否为试点部门
		if(notifyCustomerService.checkIsPilotOrgCode(dtos.get(0).getCustomerPickupOrgCode())){
			isPilotOrgCode=true;
		}
		for (NotifyCustomerDto dto : dtos) {
			// 对每一条运单执行自动通知
			notifyCustomerService.autoSendMessageDetail(estimatedPickupTime, dto,isPilotOrgCode);
		}
	}
	
	/**
	 * 
	 * 判断车辆任务到达部门是否是营业部
	 * 
	 * @param entity
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 25, 2013 7:44:19 PM
	 * 
	 * 判断卸车操作部门是否是营业部
	 * @Title: checkSalesDepartment 
	 * @author 200664-yangjinheng
	 * @date 2014年10月3日 上午11:30:00
	 * @throws
	 */
	private boolean checkSalesDepartment(String truckTaskDetailId) {
		Long count = waybillArriveTempDao.querySalesDepartmentCount(truckTaskDetailId);
		return count.intValue() > 0 ? true : false;
	}

	/**
	 * 获取执行时间
	 * 
	 * @param curdate
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 4:52:40 PM
	 */
	private Date getOperatorDate(Date curdate,String code) {
		String confValue = configurationParamsService.queryConfValueByCode(code);
		Integer minutes = 0;
		
		// 查询系统配置的建立卸车任务后X分钟通知
		if(NotifyCustomerConstants.ARRIVE_NOTIFY_MINUTE.equals(code)){
			try {
				if (StringUtil.isBlank(confValue)) {
					// 分钟默认值 30分钟
					minutes = NumberConstants.NUMBER_30;
				} else {
					minutes = Integer.valueOf(confValue);
				}
			} catch (Exception e) {
				minutes = NumberConstants.NUMBER_30;
			}
		}else if(NotifyCustomerConstants.JZ_ARRIVALGOODS_NOTIFY_MINUTE.equals(code)){//家装运单自动推送到货信息JOB配置的时间(分钟)
			try {
				if (StringUtil.isBlank(confValue)) {
					// 分钟默认值 180分钟
					minutes = NUMBER_180;
				} else {
					minutes = Integer.valueOf(confValue);
				}
			} catch (Exception e) {
				minutes = NUMBER_180;
			}
		}
		
		Timestamp ts = new Timestamp(curdate.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(ts);
		c.add(Calendar.MINUTE, minutes * -1);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());
	}

	/**
	 * 获取预计提货时间
	 * 
	 * @param curdate
	 * @return
	 * @author ibm-wangfei
	 * @date Jun 24, 2013 4:52:40 PM
	 */
	private String getEstimatedPickupTime() {
		// 查询预计提货时间
		String confValue = configurationParamsService.queryConfValueByCode(NotifyCustomerConstants.PLAN_PICK_UP_GOODS_HOUR);
		if (StringUtil.isBlank(confValue)) {
			// 默认三小时
			return "3";
		}
		return confValue;
	}

	/**
	 * 自动推送到货信息JOB（家装运单）
	 * @author 243921-FOSS-zhangtingting
	 * @date 2015-12-02 上午08:58:03
	 */
	@Override
	public void autoSendArrivalGoods() {
		// 查询出符合条件的卸车信息数量
		VehicleArriveNotifyJZEntity vehicleArriveNotifyJZEntity = new VehicleArriveNotifyJZEntity();
		vehicleArriveNotifyJZEntity.setStatus(NotifyCustomerConstants.HANDLE_NO);
		vehicleArriveNotifyJZEntity.setStatusN(NotifyCustomerConstants.HANDLE_ING);
		String jobId = UUIDUtils.getUUID();
		vehicleArriveNotifyJZEntity.setJobIdN(jobId);
		vehicleArriveNotifyJZEntity.setJobId("N/A");
		// 查询的建立卸车任务时间>=（当前时间-配置时间）
		Date curdate = new Date();
		vehicleArriveNotifyJZEntity.setCreateTimeTo(getOperatorDate(curdate,NotifyCustomerConstants.JZ_ARRIVALGOODS_NOTIFY_MINUTE));
		int count = this.waybillArriveTempDao.updateJZWaybillByParam(vehicleArriveNotifyJZEntity);
		boolean hasLongRecord = count > 0;
		List<VehicleArriveNotifyJZEntity> vehicleArriveNotifyJZEntityList = null;
		while (hasLongRecord) {
			LOGGER.info("可执行明细记录数：{}", count);
			if (count <= NotifyCustomerConstants.ROWNUM) {
				hasLongRecord = false;
			}
			// 查询符合条件的记录数，记录数 <= NotifyCustomerConstants.ROWNUM
			vehicleArriveNotifyJZEntity = new VehicleArriveNotifyJZEntity();
			vehicleArriveNotifyJZEntity.setJobId(jobId);
			vehicleArriveNotifyJZEntity.setStatus(NotifyCustomerConstants.HANDLE_ING);
			vehicleArriveNotifyJZEntity.setRowNum(NotifyCustomerConstants.ROWNUM);
			vehicleArriveNotifyJZEntityList = this.waybillArriveTempDao.queryVehicleArriveNotifyJZ(vehicleArriveNotifyJZEntity);
			if (CollectionUtils.isEmpty(vehicleArriveNotifyJZEntityList)) {
				break;
			}
			for (VehicleArriveNotifyJZEntity entity : vehicleArriveNotifyJZEntityList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("truckTaskDetailId", entity.getTruckTaskDetailId());
				LOGGER.info("卸车任务Id:"+entity.getTruckTaskDetailId());
				// 判断卸车任务的目的地是否是营业部
				if (checkSalesDepartment(entity.getTruckTaskDetailId())) {
					LOGGER.info("卸车任务的目的地是营业部。。。。");
					//是营业部
					map.put("isSaleDept", "SUCCESS");
					executeArrivalGoods(map);
					// 删除临时表
					waybillArriveTempDao.deleteJZWaybillByPrimaryKey(entity.getId());
					JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
					jobSuccessLogEntity.setJobName(NotifyCustomerConstants.SEND_ARRIVALGOODS_INFORMATION_JOB_NAME);// job名称
					jobSuccessLogEntity.setSuccessMsg("车辆任务id:" + entity.getTruckTaskDetailId() + "卸车时间：" + entity.getCreateTimeTo()+"自动推送到货信息成功"); // 设置成功运单编号
					jobSuccessLogEntity.setSuccessId(entity.getId());
					this.shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
				}else{ //不是
					LOGGER.info("卸车任务的目的地非营业部。。。。");
					//根据卸车任务id，获取创建卸车任务的部门
					UnloadTaskEntity unloadTaskEntity = unloadTaskService.queryUnloadTaskBaseInfoById(entity.getTruckTaskDetailId());
					if(null != unloadTaskEntity){
						//根据部门code，获取组织信息，判断是否是外场
						OrgAdministrativeInfoEntity transferCenter = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(unloadTaskEntity.getUnloadOrgCode());
						if(null != transferCenter && FossConstants.YES.equals(transferCenter.getTransferCenter())){//是外场
							//根据外场code获取驻地部门
							List<SaleDepartmentEntity> orgList = saleDepartmentService.queryStationListByTransferCenterCode(transferCenter.getCode());
							if(CollectionUtils.isNotEmpty(orgList)){
								if(orgList.size() == 1){
									SaleDepartmentEntity saleDepartmentEntity = orgList.get(0);
									map.put("orgCode", saleDepartmentEntity.getCode());
								}else{
									List<String> orgCodes = new ArrayList<String>();
									for(SaleDepartmentEntity departmentEntity : orgList){
										orgCodes.add(departmentEntity.getCode());
									}
									map.put("orgCodes", orgCodes);
								}
								executeArrivalGoods(map);
								// 删除临时表
								waybillArriveTempDao.deleteJZWaybillByPrimaryKey(entity.getId());
								JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
								jobSuccessLogEntity.setJobName(NotifyCustomerConstants.SEND_ARRIVALGOODS_INFORMATION_JOB_NAME);// job名称
								jobSuccessLogEntity.setSuccessMsg("车辆任务id:" + entity.getTruckTaskDetailId() + "卸车时间：" + entity.getCreateTimeTo()+"自动推送到货信息成功"); // 设置成功运单编号
								jobSuccessLogEntity.setSuccessId(entity.getId());
								this.shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
							}
						}
					}
				}
				
			}
			// 减去已执行的记录数
			count -= NotifyCustomerConstants.ROWNUM;
		}
	}
	
	/**
	 * 按车辆任务ID执行自动推送到货信息JOB
	 * @author 243921-FOSS-zhangtingting
	 * @date 2015-12-03 上午09:36:43
	 */
	private void executeArrivalGoods(Map<String,Object> map) {
		// 查询符合条件的运单记录
		List<ArrivalGoodsWaybillDto> dtos = waybillArriveTempDao.queryAutoWaybillInfoForSendDop(map);
		if (CollectionUtils.isEmpty(dtos)) {
			LOGGER.info("无符合条件的运单记录。。。。");
			return;
		}
		LOGGER.info("符合条件的运单总数:"+dtos.size());
		//对符合条件的运单进行推送到货信息
		for (ArrivalGoodsWaybillDto dto : dtos) {
			int unloadQty = dto.getOperationGoodsQty().intValue(); //卸车件数
			int totoalQty = dto.getGoodsQtyTotal().intValue(); //开单件数（运单总件数）
			int arriveQty = dto.getArriveGoodsQty().intValue(); //到达件数
			LOGGER.info("符合条件的运单号:"+dto.getWaybillNo()+",卸车件数："+unloadQty+",开单件数："+totoalQty+",到达件数："+arriveQty);
			
			if(totoalQty <= unloadQty){ //若卸车件数等于开单件数，则该单货物不是分批配载，可推送到货信息 ***多货亦可进行到货通知
				LOGGER.info("卸车件数大于等于开单件数(包含多货情况),可推送到货信息。。");
				notifyCustomerService.autoSendArrivalGoods(dto);
			}else{//卸车件数不等于开单件数,则分批配载
				if(totoalQty == arriveQty){//若到达件数等于开单件数，则该单货物全部到达，可推送到货信息
					LOGGER.info("到达件数等于开单件数,可推送到货信息。。");
					notifyCustomerService.autoSendArrivalGoods(dto);
				}else{//到达件数不等于开单件数，则不推送到货信息
					LOGGER.info("到达件数不等于开单件数,该运单不推送到货信息，执行下一运单！");
					continue;
				}
			}
			
		}
	}
	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

}
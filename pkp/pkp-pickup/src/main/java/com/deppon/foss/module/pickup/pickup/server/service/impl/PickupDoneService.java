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
 * PROJECT NAME	: pkp-pickup
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/service/impl/PickupDoneService.java
 * 
 * FILE NAME        	: PickupDoneService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.pickup.pda.api.server.service.IPickupDoneService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PickupDoneDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.sign.api.server.service.IDeliverHandlerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverService;
import com.deppon.foss.module.pickup.sign.api.server.service.IStayHandoverserialService;
import com.deppon.foss.module.pickup.sign.api.shared.define.DeliverHandlerConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverserialEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.DeliverHandlerException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 完成接货服务
 * @author foss-meiying
 * @date 
 * 		2012-12-10 下午3:30:49
 * @since
 * @version
 */
public class PickupDoneService implements IPickupDoneService{
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PickupDoneService.class);
	
	private static final int NUMBER_3 = 3;
	/**
	 * 交接service
	 */
	private IStayHandoverService stayHandoverService;
	/**
	 * 交接明细service
	 */
	private IStayHandoverDetailService stayHandoverDetailService;
	/**
	 * 派送处理service
	 */
	private IDeliverHandlerService deliverHandlerService;
	/**
	 * 派送单service
	 */
	private IDeliverbillService deliverbillService;
	/**
	 * 运单待处理service
	 */
	private IWaybillPendingService waybillPendingService;
	/**
	 * 交接流水号service
	 */
	private IStayHandoverserialService stayHandoverserialService;
	/**
	 * 组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 车队service
	 */
	private IMotorcadeService motorcadeService;
	
	private IExpressVehiclesService expressVehiclesService;
	/** 
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 
	 * 进行接货处理
	 * @author foss-meiying
	 * @date 2012-12-10 下午3:37:17
	 * @param record.vehicleNo
	 * 			 车牌号
	 * 		 record.driverCode 
	 * 			司机工号
	 * 		 record.createUserName
	 * 			 创建人
	 * 		 record.createTime
	 * 			 创建时间
	 * @return 
	 * @see
	 */
	@Transactional
	public boolean donePickUp(PickupDoneDto record){
		LOGGER.info("完成接货接口传入参数 "+ReflectionToStringBuilder.toString(record));//记录日志
		//如果传入的对象为空
		if(record == null){
			LOGGER.error("接口传入的数据为空");//记录日志
			throw new PdaProcessException("接口传入的数据为空");//接口传入的数据为空
		}
		//如果传入的司机工号为空
		if (StringUtil.isBlank(record.getDriverCode())) {
			//记录日志
			LOGGER.error("司机工号为空");//记录日志
			//抛出异常
			throw new PdaProcessException("司机工号不能为空");//司机工号不能为空
		}
		String temp =record.getVehicleNo();
		//如果传入的车牌号为空
		if ( StringUtil.isBlank(temp)) {
			//记录日志
			LOGGER.error("车牌号为空");//记录日志
			//抛出异常
			throw new PdaProcessException("车牌号不能为空");//车牌号不能为空
		}

		MutexElement mutexElement = new MutexElement(temp, "接送货卸车车牌号", MutexElementType.STAY_HANDOVER_VEHICLE_NO);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//如果没有上锁
		if(!isLocked){
			LOGGER.error("当前车辆（" + temp + "）正在卸车操作中，请稍后再试！");//记录日志
			throw new PdaProcessException("当前车辆（" + temp + "）正在操作中，请稍后再试！");
		}
	// 获取驻地外场
		
		String transferCenter = null;
		
		ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity ();
		expressVehiclesEntity.setEmpCode(record.getDriverCode());
		boolean isDriver = true;
		//查询快递车辆信息
		List<ExpressVehiclesEntity> list = expressVehiclesService.queryExpressVehiclesList(expressVehiclesEntity, 0, 2);
		if(list.size()>0){ //快递
			isDriver= false; 
			transferCenter = list.get(0).getOrgCode();//这里肯定是唯一的一个营业部
		}else{//零担
			transferCenter = getTransferCenter(record.getOrgCode());
		}
		//add by 329757 判断司机工号是否是6个0，如果是6个0说明是外请车，以车牌号查询
		StayHandoverEntity pickup = null;
		if(record.getDriverCode().equals("000000")){
			pickup = stayHandoverService.queryPickUpStayHandOverInfoByVehicleNo(record.getVehicleNo(),isDriver);
		}else{
			//得到接货交接信息
			pickup = stayHandoverService.queryPickUpStayHandOverInfo(record.getDriverCode(),isDriver);
		}
		if(pickup != null){
			// 创建人编码
			pickup.setCreateUserCode(record.getDriverCode());
			// 车牌号
			pickup.setVehicleNo(record.getVehicleNo());
			// 创建人
			pickup.setCreateUserName(record.getCreateUserName());
			// 创建时间
			pickup.setCreateTime(record.getCreateTime());
			// 驻地外场
			pickup.setTransferCenter(transferCenter);
			// 添加接货交接信息
			this.addPickupInfo(record, pickup,isDriver);
		}else{
			LOGGER.error("接货---根据司机工号查询运单待处理信息为空");//记录日志
		}
		try {
			// 构造查询条件，PDA已下拉但未生成交接单
			DeliverbillDto querydeliverbillDto =new DeliverbillDto();
			querydeliverbillDto.setVehicleNo(record.getVehicleNo());
			querydeliverbillDto.setDriverCode(record.getDriverCode());
			if(isDriver){
				querydeliverbillDto.setIsExpress("NO");
			}else {
				querydeliverbillDto.setIsExpress("YES");
			}
			querydeliverbillDto.setStatus(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
			//根据司机工号和车牌号查询派送单id集合 
			List<DeliverbillDto> ids =deliverbillService.queryPdaDownloadedDeliverbillList(querydeliverbillDto);
			if(CollectionUtils.isEmpty(ids)){//如果查询派送单id集合为空
				businessLockService.unlock(mutexElement);//解锁
				return true;
			}
			List<String> idList = new ArrayList<String>();
			List<String> deliverbillNos = new ArrayList<String>();
			for (DeliverbillDto deliverbillDto : ids) {//循环派送单id
				//得到派送单id
				idList.add(deliverbillDto.getId());
				//得到派送单编号
				deliverbillNos.add(deliverbillDto.getDeliverbillNo());
			}
			DeliverbillDetailDto dto = this.initDeliverbillDetailDto(record);
			dto.setIds(idList);//得到派送单id集合
			dto.setDeliverbillNos(deliverbillNos);//得到派送单编号集合
			//如果存在有到达联没有签收确认
			if(CollectionUtils.isNotEmpty(deliverHandlerService.queryArrivesheetIsSign(dto))){
				//记录错误信息
				LOGGER.error("存在到达联还没有进行签收确认，不能生成送货交接列表！");
				businessLockService.unlock(mutexElement);//解锁
				throw new PdaProcessException("存在到达联还没有进行签收确认，不能生成送货交接列表！");//存在到达联还没有进行签收确认，不能生成送货交接列表！
			}
			//添加货物拉回交接信息
			int result =deliverHandlerService.addPullbackInfo(dto,transferCenter);
			if(result == NUMBER_3){
				businessLockService.unlock(mutexElement);//解锁
				throw new PdaProcessException("存在派送单状态为已签收确认的单，不能生成送货交接列表！");
			}
			LOGGER.info("拉回货物完成");//记录日志
			businessLockService.unlock(mutexElement);//解锁
			return true;
		} catch (DeliverHandlerException e) {//捕获异常
			LOGGER.error(e.getMessage(),e);//记录日志
			businessLockService.unlock(mutexElement);//解锁
			throw new PdaProcessException(e.getMessage(),e);//抛出异常
		}
	}
	/**
	 * 对传入的派送明细信息dto进行实例化处理
	 * @author foss-meiying
	 * @date 2012-12-11 下午7:47:21
	 * @param record 接货dto
	 * 			record.vehicleNo 
	 * 				车牌号
	 * 			record.driverCode 
	 * 				司机工号
	 * 			record.createTime 
	 * 				创建时间
	 * 			record.create.createUserName 
	 * 				创建 人
	 * @return
	 * @see
	 */
	private DeliverbillDetailDto initDeliverbillDetailDto(PickupDoneDto record){
		DeliverbillDetailDto dto = new DeliverbillDetailDto();
		//车牌号
		dto.setVehicleNo(record.getVehicleNo());
		//司机工号
		dto.setDriverCode(record.getDriverCode());
		//创建时间
		dto.setCreateTime(record.getCreateTime());
		//司机工号传入创建人编码
		dto.setCreateUserCode(record.getDriverCode());
		//创建人
		dto.setCreateUserName(record.getCreateUserName());
		return dto;
	}
	/**
	 * 
	 * 添加接货信息
	 * @author foss-meiying
	 * @date 2012-12-11 下午4:25:23
	 * @see
	 */
	private void addPickupInfo(PickupDoneDto record,StayHandoverEntity pickup, boolean isDriver){
		LOGGER.info("接货-----添加交接信息开始"+ReflectionToStringBuilder.toString(record));//记录日志
		//添加交接信息
	    stayHandoverService.addStayHandover(pickup);
		List<StayHandoverDetailDto> stayhandoverDetails = null;
		//add by 329757
		//判断司机是否是外请车司机
		if("000000".equals(record.getDriverCode())){
			//说明是外请车司机 ，根据车号查询货物信息
			stayhandoverDetails = stayHandoverDetailService.queryPickupByWaybillPendingByVehicleNo(record.getVehicleNo(),isDriver);
		}else{
			stayhandoverDetails = stayHandoverDetailService.queryPickupByWaybillPendingDetail(record.getDriverCode(),isDriver);
		}
		if(CollectionUtils.isNotEmpty(stayhandoverDetails)){
			//批量添加交接明细信息
			for (StayHandoverDetailDto stayHandoverDetailDto : stayhandoverDetails) {
				StayHandoverDetailEntity sDetailEntity = new StayHandoverDetailEntity();
				//交接id
				sDetailEntity.settSrvStayHandoverId(pickup.getId());
				//运单号
				sDetailEntity.setWaybillNo(stayHandoverDetailDto.getWaybillNo());
				//货物总件数
				sDetailEntity.setGoodsQty(stayHandoverDetailDto.getGoodsQty());
				//包装备注(图片开单使用)
				sDetailEntity.setPackageRemark(stayHandoverDetailDto.getPackageRemark());
				//添加交接明细
				StayHandoverDetailEntity detail = stayHandoverDetailService.addStayHandoverDetail(sDetailEntity);
				if(null != detail){
					//根据运单号查询labeled_good_pda货件流水号
					List<StayHandoverserialEntity> serials = stayHandoverserialService.querySerialNosByWaybillNo(stayHandoverDetailDto.getWaybillNo());
					//如果查询出的流水号不为空
					if(CollectionUtils.isNotEmpty(serials)){		
						for (StayHandoverserialEntity stayHandoverserialEntity : serials) {
							//得到交接明细的id
							stayHandoverserialEntity.settSrvStayHandoverdetailId(detail.getId());
							//执行添加操作
							stayHandoverserialService.add(stayHandoverserialEntity);
						}
						if(serials.size()!=detail.getGoodsQty()){
							detail.setGoodsQty(serials.size());
						}
						stayHandoverDetailService.updateByPrimaryKeySelective(detail);
						
					}else {
						LOGGER.info("根据运单号查询货件表里的流水号为空");//记录日志
					}
				}
				//如果运单待处理信息为无效,删除当前运单。
				if(FossConstants.NO.equals(stayHandoverDetailDto.getActive())){
					// 调用waybillPending 删除当前运单
					waybillPendingService.deleteByWaybillNo(stayHandoverDetailDto.getWaybillNo());
				}else {
					WaybillPendingEntity pending = new WaybillPendingEntity();
					pending.setId(stayHandoverDetailDto.getId());
					//已生成交接
					pending.setHandoverStatus(FossConstants.YES);
					// 修改运单待处理状态为Y 已生成交接
					waybillPendingService.updateByPrimaryKeySelective(pending);
				}
			}
		}
		LOGGER.info("接货-----添加交接信息完成");//记录日志
	}
	
	/**
	 * 
	 * 获取顶级车队，再获取顶级车队对应的驻地外场
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-24 下午2:15:05
	 */
	private String getTransferCenter(String code){
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoComplexService.getTopFleetByCode(code);
		if(orgInfo != null){
			MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(orgInfo.getCode());
			if(motorcade != null){
				return motorcade.getTransferCenter();
			}
		}
		return DeliverHandlerConstants.DEFAULT_TRANSFER_CENTER;
	}
	
	/**
	 * Sets
	 * 	 the 交接service.
	 *
	 * @param 
	 * 		stayHandoverService the new 交接service
	 */
	public void setStayHandoverService(IStayHandoverService stayHandoverService) {
		this.stayHandoverService = stayHandoverService;
	}
	/**
	 * Sets 
	 * 	the 交接明细service.
	 *
	 * @param stayHandoverDetailService 
	 * 	the new 交接明细service
	 */
	public void setStayHandoverDetailService(
			IStayHandoverDetailService stayHandoverDetailService) {
		this.stayHandoverDetailService = stayHandoverDetailService;
	}
	
	/**
	 * Sets 
	 * 	the 派送处理service.
	 *
	 * @param deliverHandlerService 
	 * 	the new 派送处理service
	 */
	public void setDeliverHandlerService(
			IDeliverHandlerService deliverHandlerService) {
		this.deliverHandlerService = deliverHandlerService;
	}
	
	
	/**
	 * Sets 
	 * 	the 派送单service.
	 *
	 * @param deliverbillService 
	 * 	the new 派送单service
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	
	/**
	 * Gets 
	 * 		the 交接service.
	 *
	 * @return 
	 * 		the 交接service
	 * 		
	 */
	public IStayHandoverService getStayHandoverService() {
		return stayHandoverService;
	}
	
	/**
	 * Sets 
	 * 		the 运单待处理service.
	 *
	 * @param waybillPendingService
	 * 		 the new 运单待处理service
	 */
	public void setWaybillPendingService(IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}
	
	/**
	 * Sets 
	 * 		the 交接流水号service.
	 *
	 * @param stayHandoverserialService 
	 * 		the new 交接流水号service
	 */
	public void setStayHandoverserialService(IStayHandoverserialService stayHandoverserialService) {
		this.stayHandoverserialService = stayHandoverserialService;
	}
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}
	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
		
	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	
}
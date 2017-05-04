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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/service/impl/OrderPreprocessService.java
 * 
 * FILE NAME        	: OrderPreprocessService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusLogDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderVehViewDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderVehViewService;
import com.deppon.foss.module.pickup.order.api.shared.define.ExpressWorkerStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverBillCountDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderVehViewSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleViewDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleViewSmallZoneDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.util.UUIDUtils;


/**
 * 
 * @ClassName: OrderVehViewService 
 * @Description: 订单可视化查询服务 
 * @author YANGBIN
 * @date 2014-5-10 上午9:07:25 
 *
 */
public class OrderVehViewService implements IOrderVehViewService{
	// 日志信息
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderVehViewService.class);
	// 组织service
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	// 可视化查询DAO
	private IOrderVehViewDao orderVehViewDao;
	// 工作状态DAO
	private IExpressWorkerStatusDao expressWorkerStatusDao;
	// 工作状态日志DAO
	private IExpressWorkerStatusLogDao expressWorkerStatusLogDao;
	/**
     * 
     * @Title: queryDriverByCommon 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<OwnTruckSignDto>    返回类型 
     * @throws
     */
	@Override
	public List<OwnTruckSignDto> queryDriverByCommon(
			DriverQueryDto driverQueryDto, int start, int limit) {
		List<OwnTruckSignDto> newSignList = new ArrayList<OwnTruckSignDto>();
		List<OwnTruckSignDto> signDtoList = orderVehViewDao.queryDriverByCommon(driverQueryDto, start, limit);
		// 判断是否为空
		if(CollectionUtils.isNotEmpty(signDtoList)){
			// 循环查询数据
			for(OwnTruckSignDto ownTruckSignDto : signDtoList){
				//取得对应的车牌号
				String vehicleNo = ownTruckSignDto.getVehicleNo();
				// 如果车牌号、司机工号都不为空
				if(StringUtils.isNotEmpty(vehicleNo)){
					//取得对应的司机工号
					DriverQueryDto queryDto = new DriverQueryDto();
					queryDto.setFleetCodeList(driverQueryDto.getFleetCodeList());
					queryDto.setVehicleNo(vehicleNo);
					List<DriverBillCountDto> billCountDtos = new ArrayList<DriverBillCountDto>();
					// 定义取得接货订单数据
					billCountDtos = orderVehViewDao.queryOrderBillCount(queryDto);
					if(CollectionUtils.isNotEmpty(billCountDtos)){
						Integer completeCount = billCountDtos.get(0).getCompleteCount();
						Integer receiveCount = billCountDtos.get(0).getReceiveCount();
						ownTruckSignDto.setReceiveOrderTotal(receiveCount);
						ownTruckSignDto.setReceiveWaybillOrderTotal(completeCount);
						for(int i=0;i<billCountDtos.size();i++){
							 ownTruckSignDto.setWeightAndVolume(billCountDtos.get(i).getTotalWeight()+"KG"+"/"+billCountDtos.get(i).getTotalVolume()+"方"); 
						}
					}
					// 定义取得派送运单数据
					billCountDtos = orderVehViewDao.queryDeliverBillCount(queryDto);
					validaOwnTruckExtracted(ownTruckSignDto, billCountDtos);
				}else {
					ownTruckSignDto.setWeightAndVolume(""+0/0);
					ownTruckSignDto.setSignWaybillTotal(0);
					ownTruckSignDto.setDeliverWaybillTotal(0);
					ownTruckSignDto.setReceiveOrderTotal(0);
					ownTruckSignDto.setReceiveWaybillOrderTotal(0);
				}
				newSignList.add(ownTruckSignDto);
			}
			return newSignList;
		}else{
			return null;
		}
	}

	private void validaOwnTruckExtracted(OwnTruckSignDto ownTruckSignDto,
			List<DriverBillCountDto> billCountDtos) {
		if(CollectionUtils.isNotEmpty(billCountDtos)){
			Integer completeCount = billCountDtos.get(0).getCompleteCount();
			Integer receiveCount = billCountDtos.get(0).getReceiveCount();
			// 设置派送数据
			if(null != receiveCount){
				ownTruckSignDto.setDeliverWaybillTotal(receiveCount);
				if(null != completeCount){
					int noDeliverCount = receiveCount.intValue()-completeCount.intValue();
					ownTruckSignDto.setSignWaybillTotal(Integer.valueOf(noDeliverCount));
				}else{
					ownTruckSignDto.setSignWaybillTotal(receiveCount);
				}
			}else{
				ownTruckSignDto.setSignWaybillTotal(0);
				ownTruckSignDto.setDeliverWaybillTotal(0);
			}
		}
	}
	
	/**
     * 
     * @Title: queryDriverByCommonAll 
     * @Description: 查询出对应签到司机统计信息,总数据
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<VehicleViewSmallZoneDto>    返回类型 
     * @throws
     */
	@Override
	public List<VehicleViewSmallZoneDto> queryDriverByCommonAll(
			DriverQueryDto driverQueryDto, int start, int limit) {
		List<OwnTruckSignDto> list = orderVehViewDao.queryDriverByCommon(driverQueryDto, start, limit);
		List<VehicleViewSmallZoneDto> smallZoneList = new ArrayList<VehicleViewSmallZoneDto>();
		List<OwnTruckSignDto> newList = new ArrayList<OwnTruckSignDto>();
		List<String> setSmall = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(list)
				&& list.size() > 0){
			for(OwnTruckSignDto dto : list){
				// 判断GISid是否为空
				if(StringUtils.isNotEmpty(dto.getGisId())){
					for(OwnTruckSignDto newDto : list){
						if(StringUtils.isNotEmpty(newDto.getRegionCode())
								&& StringUtils.equals(dto.getGisId(), newDto.getGisId())){
							newList.add(newDto);
							setSmall.add(newDto.getGisId());
						}
					}
	
				}
				
			}
		}else {
			return null;
		}
		smallZoneList = getSmallZones(newList,setSmall);
		return smallZoneList;
	}
	
	/**
     * 
     * @Title: getSmallZones 
     * @Description: 获得组装小区的信息
     * @param @param newList
     * @param @param newList
     * @param @return    设定文件 
     * @return List<VehicleViewSmallZoneDto>    返回类型 
     * @throws
     */
	private List<VehicleViewSmallZoneDto> getSmallZones(List<OwnTruckSignDto> newList,List<String> setSmall){
		List<VehicleViewSmallZoneDto> smallZoneList = new ArrayList<VehicleViewSmallZoneDto>();
		if(CollectionUtils.isNotEmpty(newList)
				&& newList.size() > 0){
			Set<String> smallZoneSet = new HashSet<String>();
			CollectionUtils.addAll(smallZoneSet, setSmall.toArray());
			setSmall.removeAll(setSmall);
			CollectionUtils.addAll(setSmall, smallZoneSet.iterator());
			for(int i = 0 ; i < setSmall.size(); i ++){
				String  gisId = setSmall.get(i);
				String regionCode = null;
				String regionName = null;
				List<VehicleViewDto> vehicleList = new ArrayList<VehicleViewDto>();
				for(OwnTruckSignDto newSmallDto : newList){
					if(StringUtils.equals(gisId, newSmallDto.getGisId())){
						boolean exists = false;
						if(CollectionUtils.isNotEmpty(vehicleList)
								&& vehicleList.size() > 0){
							for(VehicleViewDto vehDto : vehicleList){
								if(StringUtils.equals(vehDto.getVehicle_no(), newSmallDto.getVehicleNo())){
									exists = true;
									break;
								}
							}
						}
						if(!exists){
							VehicleViewDto vehicleViewDto = new VehicleViewDto();
							vehicleViewDto.setBigzone(newSmallDto.getRegionCode());
							vehicleViewDto.setDriver_name(newSmallDto.getDriverName());
							vehicleViewDto.setDriver_tel(newSmallDto.getDriverMobilePhone());
							vehicleViewDto.setResponsibletype(newSmallDto.getVehicleType());
							vehicleViewDto.setSmallzone(newSmallDto.getRegionName());
							vehicleViewDto.setStatus(newSmallDto.getRecieveOrderStatus());
							vehicleViewDto.setVehicle_no(newSmallDto.getVehicleNo());
							vehicleViewDto.setVehicle_type("公司车");
							vehicleList.add(vehicleViewDto);
							regionCode = newSmallDto.getRegionCode();
							regionName = newSmallDto.getRegionName();
						}
						
					}
				}
				VehicleViewSmallZoneDto vehicleViewSmallZoneDto = new VehicleViewSmallZoneDto();
				vehicleViewSmallZoneDto.setId(regionCode);
				vehicleViewSmallZoneDto.setName(regionName);
				vehicleViewSmallZoneDto.setPolygonID(gisId);
				vehicleViewSmallZoneDto.setVehicleArry(vehicleList);
				smallZoneList.add(vehicleViewSmallZoneDto);
			}
		}else{
			smallZoneList = null;
		}
		return smallZoneList;
	}
	
	/**
     * 
     * @Title: queryDriverByCommonCount 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return Long    返回类型 
     * @throws
     */
	@Override
	public Long queryDriverByCommonCount(DriverQueryDto driverQueryDto) {
		initQueryCondition(driverQueryDto);
		return orderVehViewDao.queryDriverByCommonCount(driverQueryDto);
	}
	
	/**
     * 
     * @Title: queryOrderVehViewByCommon 
     * @Description: 查询司机、车牌对应的订单信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<OrderVehViewSignDto>    返回类型 
     * @throws
     */
	@Override
	public List<OrderVehViewSignDto> queryOrderVehViewByCommon(
			DriverQueryDto driverQueryDto) {
		// 如果订单号不为空， 将顶级车队下所有车队组加入查询参数中
		if(StringUtils.isNotEmpty(driverQueryDto.getOrderNo())){
			initQueryCondition(driverQueryDto);
		}	
		return orderVehViewDao.queryOrderVehViewByCommon(driverQueryDto);
	}	
	/**
     * 
     * @Title: openExpressWorker 
     * @Description: 开启车辆接收
     * @param @param dto
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
     */
	@Transactional (propagation = Propagation.REQUIRED)
	@Override
	public int openExpressWorker(ExpressWorkerStatusDto dto) {
		int rownum = 0;
		int addNum = 0;
		int updateNum = 0;
		List<String> vehicleList = dto.getVehicleNoList();
		List<String> vehicleList2 = new ArrayList<String>();
		String businessArea = ExpressWorkerStatusConstants.ORDER_BUSINESS;
		String fossSystem = ExpressWorkerStatusConstants.FOSS_SYSTEM;
		String active = "Y";
		//判断车牌号是否为空
		if(CollectionUtils.isEmpty(vehicleList)){
			throw new DispatchException("开启的车牌号集合为空！");
		}else{
			//去除重复
			for(String s:vehicleList){
				if(!vehicleList2.contains(s)){
					vehicleList2.add(s);
				}
			}
		}
		//获取需要操作的快递员工作状态集合
		ExpressWorkerStatusEntity queryEntity = new ExpressWorkerStatusEntity();
		queryEntity.setActive(active);
		queryEntity.setBusinessArea(businessArea);
		//根据车牌号、业务类型，获得对应车辆的状态信息
		List<ExpressWorkerStatusEntity> workerStatusEntitys =
			expressWorkerStatusDao.queryWorkStatusByVehicle(vehicleList2,queryEntity);
		// 判断是否为空，如果不为空，判断是否都为开启状态
		if(CollectionUtils.isNotEmpty(workerStatusEntitys)){
			// 实际需要新增车牌集合
			List<String> addVehicleList = new ArrayList<String>();
			List<String> vehicleNoExistList = new ArrayList<String>();
			List<String> vehicleNoExistList2 = new ArrayList<String>();
			for(ExpressWorkerStatusEntity entity : workerStatusEntitys){
//				String workStatus = (String) entity.getWorkStatus();
//				String vehicleNo = (String) entity.getVehicleNo();
//				if(StringUtils.equals(ExpressWorkerStatusConstants.OPEN_STATUS, workStatus)){
//					throw new DispatchException("车牌号为："+vehicleNo+"的状态已经为开启状态！");
//				}
				vehicleNoExistList.add(entity.getId());
				vehicleNoExistList2.add(entity.getVehicleNo());
			}
			//判断查询出的车牌号与传入的车牌号进行对比，如果存在，则进行更新，否则进行新增
			for(String vehicleNo : vehicleList2){
				if(!vehicleNoExistList2.contains(vehicleNo)){//14.9.15 gcl ON-1169
					addVehicleList.add(vehicleNo);
				}
//				for(ExpressWorkerStatusEntity entity : workerStatusEntitys){
//					String existVehicleNo = (String) entity.getVehicleNo();
//					if(!StringUtils.equals(existVehicleNo, vehicleNo)){
//						addVehicleList.add(vehicleNo);
//					}
//				}
			}
			// 判断更新的车牌集合是否为空
			if(CollectionUtils.isNotEmpty(vehicleNoExistList)
					&& vehicleNoExistList.size() > 0){
				ExpressWorkerStatusDto workDtoParam = new ExpressWorkerStatusDto();
				workDtoParam.setVehicleNoList(vehicleNoExistList);
				workDtoParam.setOperateType(ExpressWorkerStatusConstants.OPEN_STATUS);
				workDtoParam.setActive(active);
				workDtoParam.setOperateSystem(fossSystem);
				workDtoParam.setBusinessArea(businessArea);	
				//先进行更新操作
				updateNum = expressWorkerStatusDao.updateByVehicleNos(workDtoParam);
				workDtoParam.setVehicleNoList(vehicleNoExistList2);
				addExpressWorkerLog(workDtoParam);
			}
			// 判断新增的车牌集合是否为空
			if(CollectionUtils.isNotEmpty(addVehicleList)
					&& addVehicleList.size() > 0){
				ExpressWorkerStatusDto workDtoParam = new ExpressWorkerStatusDto();
				workDtoParam.setVehicleNoList(addVehicleList);
				workDtoParam.setOperateType(ExpressWorkerStatusConstants.OPEN_STATUS);
				workDtoParam.setOperateSystem(fossSystem);
				workDtoParam.setActive(active);
				workDtoParam.setBusinessArea(businessArea);	
				addNum = addVehcileWorker(workDtoParam);
				addExpressWorkerLog(workDtoParam);
			}
		}else {
			ExpressWorkerStatusDto workDtoParam = new ExpressWorkerStatusDto();
			workDtoParam.setVehicleNoList(vehicleList2);
			workDtoParam.setOperateType(ExpressWorkerStatusConstants.OPEN_STATUS);
			workDtoParam.setOperateSystem(fossSystem);
			workDtoParam.setActive(active);
			workDtoParam.setBusinessArea(businessArea);	
			addNum = addVehcileWorker(workDtoParam);
			addExpressWorkerLog(workDtoParam);
		}
		rownum = updateNum+addNum;
		return rownum;
	}

	
	/**
     * 
     * @author:YANGBIN
     * @Description：新增工作状态
     * @date:2014-4-21 上午9:17:04
     * @return void
     */
	private int addVehcileWorker(ExpressWorkerStatusDto dto){
		int count = 0;
		int row = 0;
		ExpressWorkerStatusEntity worker = new ExpressWorkerStatusEntity();
		worker.setWorkStatus(dto.getOperateType());
		worker.setActive(dto.getActive());
		worker.setCreateTime(new Date());
		UserEntity entity = FossUserContext.getCurrentUser();
		worker.setCreateUsercode(entity.getEmpCode());
		worker.setCreateUser(entity.getEmpName());
		worker.setModifyTime(new Date());
		worker.setBusinessArea(dto.getBusinessArea());
		//封装并且插入数据库
		for(int i=0;i< dto.getVehicleNoList().size();i++){
			worker.setId(UUIDUtils.getUUID());
			worker.setEmpCode("000000");
			worker.setEmpName("FOSS");
			worker.setVehicleNo(dto.getVehicleNoList().get(i));
			row = expressWorkerStatusDao.insertSelective(worker);
			count = count+row;
		}
		return count;
	}
	
    /**
     * 
     * @author:lianghaisheng
     * @Description：构建日志实体
     * @date:2014-4-21 上午9:17:04
     * @return void
     */
	private void addExpressWorkerLog(ExpressWorkerStatusDto dto){
		//构建并且插入日志
		UserEntity entity = FossUserContext.getCurrentUser();
		ExpressWorkerStatusLogEntity logEntity = new ExpressWorkerStatusLogEntity();
		logEntity.setOperateType(dto.getOperateType());
		logEntity.setOperateSystem(dto.getOperateSystem());
		logEntity.setCreateTime(new Date());
		logEntity.setCreateUsercode(entity.getEmpCode());
		logEntity.setModifyTime(new Date());
		logEntity.setPdaNo(null);
		logEntity.setModifyUsercode(dto.getCreatorCode());
		logEntity.setBusinessArea(dto.getBusinessArea()); 
		//封装并且插入数据库
		for(int i=0;i< dto.getVehicleNoList().size();i++){
			logEntity.setId(UUID.randomUUID().toString());
			logEntity.setEmpCode(dto.getVehicleNoList().get(i));
			expressWorkerStatusLogDao.insertSelective(logEntity);
		}
	}
	
	/**
     * 
     * @Title: stopExrpessWorker 
     * @Description: 停止车辆接收
     * @param @param dto
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
     */
	@Override
	public int stopExrpessWorker(ExpressWorkerStatusDto dto) {
		int rownum = 0;
		int addNum = 0;
		int updateNum = 0;
		List<String> vehicleList = dto.getVehicleNoList();
		List<String> vehicleList2 = new ArrayList<String>();
		String businessArea = ExpressWorkerStatusConstants.ORDER_BUSINESS;
		String fossSystem = ExpressWorkerStatusConstants.FOSS_SYSTEM;
		String active = "Y";
		//判断车牌号是否为空
		if(CollectionUtils.isEmpty(vehicleList)){
			throw new DispatchException("暂停的车牌号集合为空！");
		}else{
			//去除重复
			for(String s:vehicleList){
				if(!vehicleList2.contains(s)){
					vehicleList2.add(s);
				}
			}
		}
		//获取需要操作的快递员工作状态集合
		ExpressWorkerStatusEntity queryEntity = new ExpressWorkerStatusEntity();
		queryEntity.setActive(active);
		queryEntity.setBusinessArea(businessArea);
		//根据车牌号、业务类型，获得对应车辆的状态信息
		List<ExpressWorkerStatusEntity> workerStatusEntitys =
					expressWorkerStatusDao.queryWorkStatusByVehicle(vehicleList2, queryEntity);
		// 判断是否为空，如果不为空，判断是否都为暂停状态
		if(CollectionUtils.isNotEmpty(workerStatusEntitys)){
		// 实际需要新增车牌集合
		List<String> addVehicleList = new ArrayList<String>();
			List<String> vehicleNoExistList = new ArrayList<String>();
			List<String> vehicleNoExistList2 = new ArrayList<String>();
			for(ExpressWorkerStatusEntity entity : workerStatusEntitys){
//					String workStatus = (String) entity.getWorkStatus();
//					String vehicleNo = (String) entity.getVehicleNo();
//					if(StringUtils.equals(ExpressWorkerStatusConstants.STOP_STATUS, workStatus)){
//						throw new DispatchException("车牌号为："+vehicleNo+"的状态已经为暂停状态！");
//					}
					vehicleNoExistList.add(entity.getId());
					vehicleNoExistList2.add(entity.getVehicleNo());
			}
			//判断查询出的车牌号与传入的车牌号进行对比，如果存在，则进行更新，否则进行新增
			for(String vehicleNo : vehicleList2){
				if(!vehicleNoExistList2.contains(vehicleNo)){//14.9.15 gcl ON-1169
					addVehicleList.add(vehicleNo);
				}
//				for(ExpressWorkerStatusEntity entity : workerStatusEntitys){
//					String existVehicleNo = (String) entity.getVehicleNo();
//					if(!StringUtils.equals(existVehicleNo, vehicleNo)){
//						addVehicleList.add(vehicleNo);
//					}
//				}
			}
			// 判断更新的车牌集合是否为空
			if(CollectionUtils.isNotEmpty(vehicleNoExistList)
							&& vehicleNoExistList.size() > 0){
				ExpressWorkerStatusDto workDtoParam = new ExpressWorkerStatusDto();
				workDtoParam.setVehicleNoList(vehicleNoExistList);
				workDtoParam.setOperateType(ExpressWorkerStatusConstants.STOP_STATUS);
				workDtoParam.setActive(active);
				workDtoParam.setOperateSystem(fossSystem);
				workDtoParam.setBusinessArea(businessArea);	
				//先进行更新操作
				updateNum = expressWorkerStatusDao.updateByVehicleNos(workDtoParam);
				workDtoParam.setVehicleNoList(vehicleNoExistList2);
				addExpressWorkerLog(workDtoParam);
			}
			// 判断新增的车牌集合是否为空
			if(CollectionUtils.isNotEmpty(addVehicleList)
							&& addVehicleList.size() > 0){
						ExpressWorkerStatusDto workDtoParam = new ExpressWorkerStatusDto();
						workDtoParam.setVehicleNoList(addVehicleList);
						workDtoParam.setOperateType(ExpressWorkerStatusConstants.STOP_STATUS);
						workDtoParam.setOperateSystem(fossSystem);
						workDtoParam.setActive(active);
						workDtoParam.setBusinessArea(businessArea);	
						addNum = addVehcileWorker(workDtoParam);
						addExpressWorkerLog(workDtoParam);
					}
			}else {
					ExpressWorkerStatusDto workDtoParam = new ExpressWorkerStatusDto();
					workDtoParam.setVehicleNoList(vehicleList2);
					workDtoParam.setOperateType(ExpressWorkerStatusConstants.STOP_STATUS);
					workDtoParam.setOperateSystem(fossSystem);
					workDtoParam.setActive(active);
					workDtoParam.setBusinessArea(businessArea);	
					addNum = addVehcileWorker(workDtoParam);
					addExpressWorkerLog(workDtoParam);
			}
			rownum = updateNum+addNum;
			return rownum;
	}
	
	/**
	 * 根据大区编码查询小区编码 
	 */
	@Override
	public List<DriverQueryDto> querySmallZoneCodesByBigZoneCodes(
			DriverQueryDto driverQueryDto) {
		return orderVehViewDao.querySmallZoneCodesByBigZoneCodes(driverQueryDto);
	}
	/**
     * 
     * @Title: initQueryCondition 
     * @Description: 初始化查询信息
     * @param @param 
     * @param @return    设定文件 
     * @return List<String>    返回类型 
     * @throws
     */
	private void initQueryCondition(DriverQueryDto driverQueryDto){
		List<OrgAdministrativeInfoEntity> fleetList = new ArrayList<OrgAdministrativeInfoEntity>();
		// 查询车队组
		List<OrgAdministrativeInfoEntity> teamList = new ArrayList<OrgAdministrativeInfoEntity>();
		List<String> fleetConditionList = new ArrayList<String>();
		try{
			String deptCode = FossUserContext.getCurrentDeptCode();
			String fleetCode = null;
			// 调用综合组的服务获取当前组织所在的车队
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.getTopFleetByCode(deptCode);
			if (orgAdministrativeInfoEntity != null) {
				// 设置车队的code
				fleetCode = orgAdministrativeInfoEntity.getCode();
			}
			// 如果顶级车队不为空
			if(StringUtils.isNotEmpty(fleetCode)){
				// 添加本顶级车队
				fleetConditionList.add(fleetCode);
			}
			// 查询顶级车队下的所有车队
			fleetList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(fleetCode,BizTypeConstants.ORG_TRANS_DEPARTMENT);
			// 查询车队组
			teamList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(fleetCode,BizTypeConstants.ORG_TRANS_TEAM);
			// 赋值
			if(fleetList != null
					&& fleetList.size() > 0){
				for(OrgAdministrativeInfoEntity fleet : fleetList){
					fleetConditionList.add(fleet.getCode());
				}
			}
			// 赋值
			if(teamList != null
					&& teamList.size() > 0){
				for(OrgAdministrativeInfoEntity team : teamList){
					fleetConditionList.add(team.getCode());
				}
			}
			// 判断当前组织所属顶级车队下所有车队信息集合是否存在
			if(CollectionUtils.isEmpty(fleetConditionList)){
				throw new DispatchException("当前组织所属顶级车队下服务车队不存在");
			}
			// 取得当前传入的车队编码
			String cadeCode = driverQueryDto.getCadeCode();
			// 判断是否为空,如果不为空，则判断是否有查询该车队数据的权限
			if(StringUtils.isNotEmpty(cadeCode)){
				boolean flag = CollectionUtils
							.exists(fleetConditionList, PredicateUtils.equalPredicate(cadeCode));
				// 判断是否存在此车队编码
				if(!flag){
					throw new DispatchException("选择的车队组织不在登录组织所属顶级服务车队中");
				}else{
					List<String> cadeCodes = new ArrayList<String>();
					cadeCodes.add(cadeCode);
					driverQueryDto.setFleetCodeList(cadeCodes);
				}
			}else {
				// 过滤当前组织中，所有车队信息
				driverQueryDto.setFleetCodeList(fleetConditionList);
			}
			
			// 设置司机接收状态
			if(PdaSignStatusConstants.DRIVER_RECEIVE_ALL.equals(driverQueryDto.getRecieveOrderStatus())){
				driverQueryDto.setRecieveOrderStatus(null);
			}else if(PdaSignStatusConstants.DRIVER_RECEIVE_OPEN.equals(driverQueryDto.getRecieveOrderStatus())){
				driverQueryDto.setRecieveOrderStatus(PdaSignStatusConstants.DRIVER_RECEIVE_OPEN);
			}else if(PdaSignStatusConstants.DRIVER_RECEIVE_STOP.equals(driverQueryDto.getRecieveOrderStatus())){
				driverQueryDto.setRecieveOrderStatus(PdaSignStatusConstants.DRIVER_RECEIVE_STOP);
			}
			// 设置大小区编码集合
			if(StringUtils.isEmpty(driverQueryDto.getSmallZoneCode())){
				if(StringUtils.isNotEmpty(driverQueryDto.getBigZoneCode())){
					driverQueryDto.setBigZoneCode(driverQueryDto.getBigZoneCode());
				}
			}else{
				driverQueryDto.setBigZoneCode(null);
			}
			
		}catch(Exception e){
			LOGGER.info(e.getMessage());
			throw new DispatchException(e.getMessage());
		}
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setOrderVehViewDao(IOrderVehViewDao orderVehViewDao) {
		this.orderVehViewDao = orderVehViewDao;
	}

	public void setExpressWorkerStatusDao(
			IExpressWorkerStatusDao expressWorkerStatusDao) {
		this.expressWorkerStatusDao = expressWorkerStatusDao;
	}

	public void setExpressWorkerStatusLogDao(
			IExpressWorkerStatusLogDao expressWorkerStatusLogDao) {
		this.expressWorkerStatusLogDao = expressWorkerStatusLogDao;
	}
	
}
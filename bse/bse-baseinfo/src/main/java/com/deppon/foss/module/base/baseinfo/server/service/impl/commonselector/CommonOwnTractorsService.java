/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonOwnTractorsService.java
 * 
 * FILE NAME        	: CommonOwnTractorsService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector
 * FILE    NAME: CommonOwnTractorsService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOwnVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnTractorsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TruckDto;

/**
 * 公共选择器--公司车辆查询serivce.
 *
 * @author panGuangJun
 * @date 2012-12-3 上午8:54:30
 */
public class CommonOwnTractorsService implements ICommonOwnTractorsService {
	// "公司车辆"接口DAO
	/** The common own vehicle dao. */
	private ICommonOwnVehicleDao commonOwnVehicleDao;
	/** The org administrative info service. */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 组织Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 根据条件查询公司车辆总数.
	 *
	 * @param ownTruck the own truck
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:57:14
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnTractorsService#queryOwnTractorsRecordCountByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
	 */
	@Override
	public long queryOwnTractorsRecordCountByCondition(OwnTruckEntity ownTruck) {
		List<String> codes = new ArrayList<String>();
		//如果传入组织编码，就查询组织下所有子组织
		if(StringUtils.isNotBlank(ownTruck.getParentOrgCode())){
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntitys = 
					orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(ownTruck.getParentOrgCode());
			for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity:orgAdministrativeInfoEntitys){
				codes.add(orgAdministrativeInfoEntity.getCode());					
			}
			ownTruck.setOrgIds(codes);
		}	
		return commonOwnVehicleDao
				.queryOwnVehicleRecordCountBySelectiveCondition(ownTruck);
	}

	/**
	 * 根据条件查询公司车辆信息.
	 *
	 * @param ownTruck the own truck
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:57:14
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnTractorsService#queryOwnTractorsListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity,
	 * int, int)
	 */
	@Override
	public List<OwnTruckEntity> queryOwnTractorsListByCondition(OwnTruckEntity ownTruck, int offset, int limit) {		
		List<String> codes = new ArrayList<String>();
		//如果传入组织编码，就查询组织下所有子组织
		if(StringUtils.isNotBlank(ownTruck.getParentOrgCode())){
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntitys = 
					orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(ownTruck.getParentOrgCode());
			for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity:orgAdministrativeInfoEntitys){
				codes.add(orgAdministrativeInfoEntity.getCode());					
			}
			ownTruck.setOrgIds(codes);
		}	
		List<OwnTruckEntity> ownTruckList=commonOwnVehicleDao.queryOwnVehicleListBySelectiveCondition(ownTruck, offset, limit);
		if(CollectionUtils.isNotEmpty(ownTruckList)){
			for(OwnTruckEntity entity : ownTruckList){
				OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getOrgId());
				if(null != orgInfo){
					entity.setOrgName(orgInfo.getName());
				}
			}
		}
		return ownTruckList;
	}

	/**
	 * 设置参数ordIds
	 * @Title: getOwnTruckEntityOrgIds 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ownTruck
	 * @param @return    设定文件 
	 * @return OwnTruckEntity    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public OwnTruckEntity setOwnTruckEntityOrgIds(OwnTruckEntity ownTruck){
		List<String> codes = new ArrayList<String>();
		//如果传入组织编码，就查询组织下所有子组织
		if(StringUtils.isNotBlank(ownTruck.getParentOrgCode())){
			List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntitys = 
					orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(ownTruck.getParentOrgCode());
			for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity:orgAdministrativeInfoEntitys){
				codes.add(orgAdministrativeInfoEntity.getCode());					
			}
			ownTruck.setOrgIds(codes);
		}	
		return ownTruck;
	}
	
	/**		
	 * 设置结果集OrgName
	 * @Title: setownTruckListOrgName 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ownTruckList
	 * @param @return    设定文件 
	 * @return List<OwnTruckEntity>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public List<OwnTruckEntity> setownTruckListOrgName(List<OwnTruckEntity> ownTruckList){
		if(CollectionUtils.isNotEmpty(ownTruckList)){
			for(OwnTruckEntity entity : ownTruckList){
				OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getOrgId());
				if(null != orgInfo){
					entity.setOrgName(orgInfo.getName());
				}
			}
		}
		return ownTruckList;
	}

	/**
	 * setter.
	 *
	 * @param commonOwnVehicleDao the new common own vehicle dao
	 */
	public void setCommonOwnVehicleDao(ICommonOwnVehicleDao commonOwnVehicleDao) {
		this.commonOwnVehicleDao = commonOwnVehicleDao;
	}

	/**
	 * 根据条件有选择的查询外请车或者公司车总数.
	 *
	 * @param ownTruck the own truck
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午11:18:10
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnTractorsService#queryTractorsRecordCountByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
	 */
	@Override
	public long queryTractorsRecordCountByCondition(OwnTruckEntity ownTruck) {
		if (CollectionUtils.isNotEmpty(ownTruck.getLoopOrgCode())) {
			List<OrgAdministrativeInfoEntity>  orgAdministrativeInfoEntitys= new ArrayList<OrgAdministrativeInfoEntity>();
			List<OrgAdministrativeInfoEntity>  orgs= new ArrayList<OrgAdministrativeInfoEntity>();
			List<String> loopOrgCodes = ownTruck.getLoopOrgCode();
			for(String loopOrgCode : loopOrgCodes){
				orgAdministrativeInfoEntitys = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(loopOrgCode);	
				orgs.addAll(orgAdministrativeInfoEntitys);
			}
			List<String> result = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(orgs)){
				for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity:orgs){
					result.add(orgAdministrativeInfoEntity.getCode());
				}
				ownTruck.setOrgIds(result);
			}else{
				ownTruck.setOrgIds(ownTruck.getLoopOrgCode());
			}
			
		}
		if(StringUtils.isNotBlank(ownTruck.getTopFleetOrgCode())){
			List<String> result = new ArrayList<String>();
			result = orgAdministrativeInfoComplexService.queryMotorcadeOwnerCodeListByOrgCode(ownTruck.getTopFleetOrgCode());
			if(CollectionUtils.isEmpty(result)){
				result.add(ownTruck.getTopFleetOrgCode());
			}
			ownTruck.setOrgIds(result);
		}
		return commonOwnVehicleDao
				.queryVehicleRecordCountBySelectiveCondition(ownTruck);
	}

	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	 * 设置参数：orgIds、ReginName
	 * @Title: setTractorsOrgIds 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ownTruck
	 * @param @return    设定文件 
	 * @return OwnTruckEntity    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public OwnTruckEntity setTractorsOrgIds(OwnTruckEntity ownTruck){
		if (CollectionUtils.isNotEmpty(ownTruck.getLoopOrgCode())) {
			List<OrgAdministrativeInfoEntity>  orgAdministrativeInfoEntitys= new ArrayList<OrgAdministrativeInfoEntity>();
			List<OrgAdministrativeInfoEntity>  orgs= new ArrayList<OrgAdministrativeInfoEntity>();
			List<String> loopOrgCodes = ownTruck.getLoopOrgCode();
			for(String loopOrgCode : loopOrgCodes){
				orgAdministrativeInfoEntitys = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(loopOrgCode);	
				orgs.addAll(orgAdministrativeInfoEntitys);
			}
			List<String> result = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(orgs)){
				for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity:orgs){
					result.add(orgAdministrativeInfoEntity.getCode());
				}
				ownTruck.setOrgIds(result);
			}else{
				ownTruck.setOrgIds(ownTruck.getLoopOrgCode());
			}
			
		}
		if(StringUtils.isNotBlank(ownTruck.getTopFleetOrgCode())){
			List<String> result = new ArrayList<String>();
			result = orgAdministrativeInfoComplexService.queryMotorcadeOwnerCodeListByOrgCode(ownTruck.getTopFleetOrgCode());
			if(CollectionUtils.isEmpty(result)){
				result.add(ownTruck.getTopFleetOrgCode());
			}
			ownTruck.setOrgIds(result);
		}
		if(StringUtils.isNotBlank(ownTruck.getReginName())&&StringUtils.isNotBlank(ownTruck.getVehicleNo())){
			ownTruck.setReginName(null);//14.7.18 gcl AUTO-178 处理订单界面 有接货小区和对车牌号 模糊查询时 设置接货小区为空 只对车牌模糊查询
		}
		return ownTruck;
	}
	
	/**
	 * 设置结果集OrgName、IsTrailer
	 * @Title: setTractorsOrgName 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ownTruckList
	 * @param @return    设定文件 
	 * @return List<TruckDto>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public List<TruckDto> setTractorsOrgName(List<TruckDto> ownTruckList){
		List<TruckDto> ownTruckDtolist= new ArrayList<TruckDto>();
		if(CollectionUtils.isNotEmpty(ownTruckList)){
			for(TruckDto entityDto : ownTruckList){
				OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entityDto.getOrgId());
				if(null != orgInfo){
					entityDto.setOrgName(orgInfo.getName());	
				}
				//如果是公车车里的挂车 显示挂车号+挂牌号
				if(StringUtils.isNotBlank(entityDto.getVehicleType())&&StringUtils.isNotBlank(entityDto.getTruckType())){
					if(entityDto.getVehicleType().equals("vehicletype_trailer")&&entityDto.getTruckType().equals("公司车")){
						entityDto.setIsTrailer("挂牌号");
					}
				}
					
				ownTruckDtolist.add(entityDto);
			}
		}
		return ownTruckDtolist;
	}
	
	/**
	 * 根据条件有选择的查询外请车或者公司车.
	 *
	 * @param ownTruck the own truck
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午11:18:10
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnTractorsService#queryTractorsListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity,
	 * int, int)
	 */
	@Override
	public List<TruckDto> queryTractorsListByCondition(OwnTruckEntity ownTruck,
			int offset, int limit) {
		
		if (CollectionUtils.isNotEmpty(ownTruck.getLoopOrgCode())) {
			List<OrgAdministrativeInfoEntity>  orgAdministrativeInfoEntitys= new ArrayList<OrgAdministrativeInfoEntity>();
			List<OrgAdministrativeInfoEntity>  orgs= new ArrayList<OrgAdministrativeInfoEntity>();
			List<String> loopOrgCodes = ownTruck.getLoopOrgCode();
			for(String loopOrgCode : loopOrgCodes){
				orgAdministrativeInfoEntitys = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllSubByCode(loopOrgCode);	
				orgs.addAll(orgAdministrativeInfoEntitys);
			}
			List<String> result = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(orgs)){
				for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity:orgs){
					result.add(orgAdministrativeInfoEntity.getCode());
				}
				ownTruck.setOrgIds(result);
			}else{
				ownTruck.setOrgIds(ownTruck.getLoopOrgCode());
			}
			
		}
		if(StringUtils.isNotBlank(ownTruck.getTopFleetOrgCode())){
			List<String> result = new ArrayList<String>();
			result = orgAdministrativeInfoComplexService.queryMotorcadeOwnerCodeListByOrgCode(ownTruck.getTopFleetOrgCode());
			if(CollectionUtils.isEmpty(result)){
				result.add(ownTruck.getTopFleetOrgCode());
			}
			ownTruck.setOrgIds(result);
		}
		if(StringUtils.isNotBlank(ownTruck.getReginName())&&StringUtils.isNotBlank(ownTruck.getVehicleNo())){
			ownTruck.setReginName(null);//14.7.18 gcl AUTO-178 处理订单界面 有接货小区和对车牌号 模糊查询时 设置接货小区为空 只对车牌模糊查询
		}
		//填充部门名称
		List<TruckDto> ownTruckDtolist= new ArrayList<TruckDto>();
		List<TruckDto> ownTruckList=commonOwnVehicleDao.queryVehicleListBySelectiveCondition(
				ownTruck, offset, limit);
		if(CollectionUtils.isNotEmpty(ownTruckList)){
			for(TruckDto entityDto : ownTruckList){
				OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entityDto.getOrgId());
				if(null != orgInfo){
					entityDto.setOrgName(orgInfo.getName());	
				}
				//如果是公车车里的挂车 显示挂车号+挂牌号
				if(StringUtils.isNotBlank(entityDto.getVehicleType())&&StringUtils.isNotBlank(entityDto.getTruckType())){
					if(entityDto.getVehicleType().equals("vehicletype_trailer")&&entityDto.getTruckType().equals("公司车")){
						entityDto.setIsTrailer("挂牌号");
					}
				}
					
				ownTruckDtolist.add(entityDto);
			}
		}
		return ownTruckDtolist;
	}

	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

}

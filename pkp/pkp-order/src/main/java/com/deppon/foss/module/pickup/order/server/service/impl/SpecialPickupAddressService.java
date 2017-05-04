package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.ISpecialPickupAddressDao;
import com.deppon.foss.module.pickup.order.api.server.service.ISpecialPickupAddressService;
import com.deppon.foss.module.pickup.order.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.SpecialAddressConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class SpecialPickupAddressService implements ISpecialPickupAddressService{
	
	private ISpecialPickupAddressDao specialPickupAddressDao;
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	@Override
	public int addSpecialAddress(SpecialAddressEntity record) {
		record.setId(UUIDUtils.getUUID());
		record.setOperator(FossUserContextHelper.getUserName());
		record.setOperatorCode(FossUserContextHelper.getUserCode());
		record.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		record.setOperateOrgName(FossUserContextHelper.getOrgName());
		record.setOperateTime(new Date());
		return specialPickupAddressDao.addSpecialAddress(record);
	}

	@Override
	public int deleteSpecialAddress(String id) {
		return specialPickupAddressDao.deleteSpecialAddress(id);
	}

	@Override
	public SpecialAddressEntity selectByAddress(String address) {
		return specialPickupAddressDao.selectByAddress(address);
	}

	@Override
	public List<SpecialAddressEntity> querySpecialAddressByCommon(
			SpecialAddressConditionDto conditionDto, int start, int limit) {
		String orgCode = FossUserContextHelper.getOrgCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(orgAdministrativeInfoEntity != null)
		{
			if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getSalesDepartment()))
			{
				List<String> orgConditionList = new ArrayList<String>();
				orgConditionList.add(orgCode);
				conditionDto.setOrgList(orgConditionList);
			}else{
				// 调用综合组的服务获取当前组织所在的顶级车队 先找到当前部门对应的顶级车队，然后再遍历往下找车队和车队组 把所有的车队
				// 车队组 都添加到查询条件
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
				if (orgAdministrativeInfoEntity1 != null) 
				{
					// 查询顶级车队下的所有车队
					List<OrgAdministrativeInfoEntity> fleetList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(orgAdministrativeInfoEntity1.getCode(),BizTypeConstants.ORG_TRANS_DEPARTMENT);
					// 查询车队组
					List<OrgAdministrativeInfoEntity> teamList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(orgAdministrativeInfoEntity1.getCode(),BizTypeConstants.ORG_TRANS_TEAM);
					List<String> orgConditionList = new ArrayList<String>();
					//添加本部门
					orgConditionList.add(orgCode);
					// 添加本顶级车队
					orgConditionList.add(orgAdministrativeInfoEntity1.getCode());
					// 赋值
					if(fleetList != null){
						for(OrgAdministrativeInfoEntity fleet : fleetList){
							orgConditionList.add(fleet.getCode());
						}
					}
					// 赋值
					if(teamList != null){
						for(OrgAdministrativeInfoEntity team : teamList){
							orgConditionList.add(team.getCode());
						}
					}
					conditionDto.setOrgList(orgConditionList);
				}else
				{
					List<String> orgConditionList = new ArrayList<String>();
					orgConditionList.add(orgCode);
					conditionDto.setOrgList(orgConditionList);
				}
			}
		}else{
			List<String> orgConditionList = new ArrayList<String>();
			orgConditionList.add(orgCode);
			conditionDto.setOrgList(orgConditionList);
		}
		return specialPickupAddressDao.querySpecialAddressByCommon(conditionDto, start, limit);
	}

	@Override
	public Long queryCountByCondition(SpecialAddressConditionDto conditionDto) {
		String orgCode = FossUserContextHelper.getOrgCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(orgAdministrativeInfoEntity != null)
		{
			if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getSalesDepartment()))
			{
				List<String> orgConditionList = new ArrayList<String>();
				orgConditionList.add(orgCode);
				conditionDto.setOrgList(orgConditionList);
			}else{
				// 调用综合组的服务获取当前组织所在的顶级车队 先找到当前部门对应的顶级车队，然后再遍历往下找车队和车队组 把所有的车队
				// 车队组 都添加到查询条件
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
				if (orgAdministrativeInfoEntity1 != null) 
				{
					// 查询顶级车队下的所有车队
					List<OrgAdministrativeInfoEntity> fleetList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(orgAdministrativeInfoEntity1.getCode(),BizTypeConstants.ORG_TRANS_DEPARTMENT);
					// 查询车队组
					List<OrgAdministrativeInfoEntity> teamList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByMotorcade(orgAdministrativeInfoEntity1.getCode(),BizTypeConstants.ORG_TRANS_TEAM);
					List<String> orgConditionList = new ArrayList<String>();
					//添加本部门
					orgConditionList.add(orgCode);
					// 添加本顶级车队
					orgConditionList.add(orgAdministrativeInfoEntity1.getCode());
					// 赋值
					if(fleetList != null){
						for(OrgAdministrativeInfoEntity fleet : fleetList){
							orgConditionList.add(fleet.getCode());
						}
					}
					// 赋值
					if(teamList != null){
						for(OrgAdministrativeInfoEntity team : teamList){
							orgConditionList.add(team.getCode());
						}
					}
					conditionDto.setOrgList(orgConditionList);
				}else
				{
					List<String> orgConditionList = new ArrayList<String>();
					orgConditionList.add(orgCode);
					conditionDto.setOrgList(orgConditionList);
				}
			}
		}else{
				List<String> orgConditionList = new ArrayList<String>();
				orgConditionList.add(orgCode);
				conditionDto.setOrgList(orgConditionList);
		}
		return specialPickupAddressDao.queryCountByCondition(conditionDto);
	}
	
	public void setSpecialPickupAddressDao(ISpecialPickupAddressDao specialPickupAddressDao) {
		this.specialPickupAddressDao = specialPickupAddressDao;
	}

	@Override
	public int updateVehicleByid(SpecialAddressEntity record) {
		record.setOperator(FossUserContextHelper.getUserName());
		record.setOperatorCode(FossUserContextHelper.getUserCode());
		record.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		record.setOperateOrgName(FossUserContextHelper.getOrgName());
		record.setOperateTime(new Date());
		return specialPickupAddressDao.updateVehicleByid(record);
	}

	@Override
	public int updateVehicleByAddress(SpecialAddressEntity record) {
		record.setOperator(FossUserContextHelper.getUserName());
		record.setOperatorCode(FossUserContextHelper.getUserCode());
		record.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		record.setOperateOrgName(FossUserContextHelper.getOrgName());
		record.setOperateTime(new Date());
		return specialPickupAddressDao.updateVehicleByAddress(record);
	}
	
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

}

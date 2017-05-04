package com.deppon.foss.module.base.baseinfo.server.service.impl;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICenterAndDeliveryAreaDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICenterAndDeliveryAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CenterAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryAreaDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliveryBigRegionAreaDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CenterAndDeliverySamlleRegionAreaDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CenterAndDeliveryAreaException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class CenterAndDeliveryAreaService implements ICenterAndDeliveryAreaService {

	private ICenterAndDeliveryAreaDao centerAndDeliveryAreaDao;
	
	public void setCenterAndDeliveryAreaDao(
			ICenterAndDeliveryAreaDao centerAndDeliveryAreaDao) {
		this.centerAndDeliveryAreaDao = centerAndDeliveryAreaDao;
	}
	/**
	 * 1、车队和车牌号为条件
	 * 2、车队和大区为条件
	 * 3、一车队为条件
	 * 4、车队和小区为条件
	 */

	@Override
	public CenterAndDeliveryAreaEntity queryVehicleInfo(CenterAndDeliveryAreaDto centerAndDeliveryAreaDto) {
		int i=0;
//		根据车队查询车队范围信息
        CenterAndDeliveryAreaEntity entity=null;
		List<CenterAndDeliveryAreaEntity> areaEntities=centerAndDeliveryAreaDao.queryVehicleDept(centerAndDeliveryAreaDto);
		if(areaEntities.size()<=0){
			entity=new CenterAndDeliveryAreaEntity();
			entity.setManageMentName(centerAndDeliveryAreaDto.getManageMentName());
			entity.setManageMentCode(centerAndDeliveryAreaDto.getManageMent());
		}else{
			entity=areaEntities.get(0);
		}
//		1、当车牌号或者小区编码不为空的时候按照小区类型查询（查询某个小区）
//		2、当大区名称或者编码不为空且小区和车牌号时一大区为单位查询某个大小下的所有小区
//		3、当大小区以及车牌号都为空的时候查询车队下的所有小区以及每个大区下的所有小区
		if(!StringUtils.isEmpty(centerAndDeliveryAreaDto.getVehicleNo())||!StringUtils.isEmpty(centerAndDeliveryAreaDto.getSmallRegionCode())){
			entity.setType("SMALLREGION");
//			if(StringUtils.isEmpty(centerAndDeliveryAreaDto.getBigRegionCode())){
				return queryBySmallCodeAndVehicleNO(entity,centerAndDeliveryAreaDto);
//			}
		}
		//根据大区编码和车队查询编码查询大区列表
		List<CenterAndDeliveryBigRegionAreaDto> bigRegionAreaDtos=centerAndDeliveryAreaDao.queryBigRegion(centerAndDeliveryAreaDto);
		
		if(bigRegionAreaDtos.size()<=0){
					
			throw new CenterAndDeliveryAreaException("", "你所查询的车队下面没有对应的大区或大区没有对应小区！");
			}
		
		if (!StringUtils.isEmpty(centerAndDeliveryAreaDto.getBigRegionCode())){
			
			entity.setType("BIGREGION");
		}else{
			entity.setType("FLEET");
		}
		//根据每个大区的虚拟编码查询大区下面的小区
		for(CenterAndDeliveryBigRegionAreaDto areaDto:bigRegionAreaDtos){
			List<CenterAndDeliverySamlleRegionAreaDto> samlleRegionAreaDtos=centerAndDeliveryAreaDao.querySmallRegion(areaDto.getVirtualCode());
			if(samlleRegionAreaDtos.size()<=0){
				i++;
			}
			areaDto.setAndDeliverySmalleRegionAreaDtos(samlleRegionAreaDtos);
		}
		if(i==bigRegionAreaDtos.size()){
			throw new CenterAndDeliveryAreaException("", "你所查询的车队下面没有对应的大区或大区没有对应小区！");
		} 
		
		entity.setBigRegions(bigRegionAreaDtos);
		
		return entity;
	}

	 
	
	/**
	 * 当大区为空小区或者车牌号不为空的时候
	 * @param entity
	 * @param centerAndDeliveryAreaDto
	 * @return
	 */
	private  CenterAndDeliveryAreaEntity queryBySmallCodeAndVehicleNO(CenterAndDeliveryAreaEntity entity,CenterAndDeliveryAreaDto centerAndDeliveryAreaDto){
		
		List<CenterAndDeliverySamlleRegionAreaDto> areaDto=centerAndDeliveryAreaDao.queryManageMentSmallCodevehicleNo(centerAndDeliveryAreaDto);
		if(areaDto.size()<=0){
			throw new CenterAndDeliveryAreaException("", "找不到该车牌号和小区信息！");
		}
		if(StringUtils.isEmpty(areaDto.get(0).getSmallRegionName())||StringUtils.isEmpty(areaDto.get(0).getSmallRegionCode())){
			throw new CenterAndDeliveryAreaException("", "找不到该车牌号以及对应的小区信息！");
		}
		centerAndDeliveryAreaDto.setVirtualCode(areaDto.get(0).getBigZoneCode());
		List<CenterAndDeliveryBigRegionAreaDto> bigs =centerAndDeliveryAreaDao.queryBigRegion(centerAndDeliveryAreaDto);
		
		if(bigs.size()<=0){
			throw new CenterAndDeliveryAreaException("", "该小区没有对应的大区！");
		}
		CenterAndDeliveryBigRegionAreaDto bigDto=bigs.get(0);
//		if(!StringUtils.equals(centerAndDeliveryAreaDto.getBigRegionCode(), bigDto.getBigRegionCode())){
//			
//			throw new CenterAndDeliveryAreaException("", "该大区下没有该小区！");
//		}
		
		bigDto.setAndDeliverySmalleRegionAreaDtos(areaDto);
		entity.setBigRegions(bigs);
		return entity;
	}
	
	/**
	 * 保存地图信息
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	@Override
	public boolean addVehicleInfo(CenterAndDeliveryAreaEntity andDeliveryAreaEntity) {
		if(StringUtils.isEmpty(andDeliveryAreaEntity.getManageMentCode())){
//			return false;
			throw new CenterAndDeliveryAreaException("", "顶级车队为空，保存失败！");
		}
		CenterAndDeliveryAreaDto centerAndDeliveryAreaDto=new CenterAndDeliveryAreaDto();
		centerAndDeliveryAreaDto.setManageMent(andDeliveryAreaEntity.getManageMentCode());
		List<CenterAndDeliveryAreaEntity> andDeliveryAreaEntities=centerAndDeliveryAreaDao.queryVehicleDept(centerAndDeliveryAreaDto);
		if(andDeliveryAreaEntities.size()>0){
			throw new CenterAndDeliveryAreaException("", "该顶级车队已经被圈画！");
//			return false;
		}
		long l=centerAndDeliveryAreaDao.addVehicleInfo(andDeliveryAreaEntity);
		return l>0?true:false;
	}
	/**
	 * 根据GISID更新保存地图信息
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	@Override
	public boolean updateVehicleInfo(
			CenterAndDeliveryAreaEntity andDeliveryAreaEntity) {
		long l=centerAndDeliveryAreaDao.updateVehicleInfo(andDeliveryAreaEntity);
		return l>0?true:false;
	}
	/**
	 * 根据GISID删除系统中存在数据
	 * @param andDeliveryAreaEntity
	 * @return
	 */
	@Override
	public boolean deleteVehicleInfo(
			CenterAndDeliveryAreaEntity andDeliveryAreaEntity) {
		long l=centerAndDeliveryAreaDao.deleteVehicleInfo(andDeliveryAreaEntity);
		return l>0?true:false;
	}
	
	/**
	 *  查询用户的角色权限
	 */
	@Override
	public CenterAndDeliveryAreaEntity queryRolePermissions() {
		/*CenterAndDeliveryAreaEntity andDeliveryAreaEntity=new CenterAndDeliveryAreaEntity();
		List<String> roleCodes=centerAndDeliveryAreaDao.queryRolePermissions(FossUserContext.getCurrentInfo().getEmpCode());
//		('TFR_TRUCK_SCHEDULINGER', 'TFR_TRUCK_HIGHEST_RESPONSE_PERSON')
		if(roleCodes.contains("TFR_TRUCK_SCHEDULINGER")||roleCodes.contains("TFR_TRUCK_HIGHEST_RESPONSE_PERSON")){
			long l=centerAndDeliveryAreaDao.queryDataPermissions(FossUserContext.getCurrentInfo().getCurrentDeptCode(), FossUserContext.getCurrentInfo().getEmpCode());
			if(l>0){
				CenterAndDeliveryAreaDto centerAndDeliveryAreaDto=new CenterAndDeliveryAreaDto();
				centerAndDeliveryAreaDto.setManageMent(FossUserContext.getCurrentInfo().getCurrentDeptCode());
				andDeliveryAreaEntity=queryVehicleInfo(centerAndDeliveryAreaDto);
				if(null!=andDeliveryAreaEntity){
					andDeliveryAreaEntity.setRolePermissions("Y");
					if(roleCodes.contains("TFR_TRUCK_HIGHEST_RESPONSE_PERSON")){
						
						andDeliveryAreaEntity.setDrawMap("Y"); 
					}
				}
			}
		}*/
		
		/*List<String> roleCodes=centerAndDeliveryAreaDao.queryRolePermissions(FossUserContext.getCurrentInfo().getEmpCode());
		CenterAndDeliveryAreaEntity andDeliveryAreaEntity=new CenterAndDeliveryAreaEntity();
		if(roleCodes.contains("TFR_TRUCK_SCHEDULINGER")||roleCodes.contains("TFR_TRUCK_HIGHEST_RESPONSE_PERSON")){
			CenterAndDeliveryAreaDto centerAndDeliveryAreaDto=new CenterAndDeliveryAreaDto();
			centerAndDeliveryAreaDto.setManageMent(FossUserContext.getCurrentInfo().getCurrentDeptCode());
			andDeliveryAreaEntity=queryVehicleInfo(centerAndDeliveryAreaDto);
			if(null!=andDeliveryAreaEntity){
				andDeliveryAreaEntity.setRolePermissions("Y");
				if(roleCodes.contains("TFR_TRUCK_HIGHEST_RESPONSE_PERSON")){
					andDeliveryAreaEntity.setDrawMap("Y"); 
				}
			}
		}*/
		List<String> roleCodes=centerAndDeliveryAreaDao.queryRolePermissions(FossUserContext.getCurrentInfo().getEmpCode());
		CenterAndDeliveryAreaEntity andDeliveryAreaEntity=new CenterAndDeliveryAreaEntity();
		if(roleCodes.contains("TFR_TRUCK_HIGHEST_RESPONSE_PERSON")){
			andDeliveryAreaEntity.setDrawMap("Y"); 
		}else{
			andDeliveryAreaEntity.setDrawMap("N");
		}
		return andDeliveryAreaEntity;
	}
	
	/**
	 * 根据用户的部门名称和编码查询改用户的数据权限
	 * @param deptCode
	 * @param empCode
	 * @return
	 */
	@Override
	public long queryDataPermissions(String deptCode, String empCode) {
		return centerAndDeliveryAreaDao.queryDataPermissions(deptCode, empCode);
	}
	
	/**
	 * 根据用户的编码查询用户的角色
	 * @param empCode
	 * @return
	 */
	@Override
	public boolean queryRoles(String empCode) {
		List<String> roles=centerAndDeliveryAreaDao.queryRolePermissions(empCode);
		if(roles.contains("TFR_TRUCK_HIGHEST_RESPONSE_PERSON")){
			return true;
		}
		return false;
	}
}

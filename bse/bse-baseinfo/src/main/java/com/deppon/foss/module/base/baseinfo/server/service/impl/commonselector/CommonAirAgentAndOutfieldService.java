package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirAgentAndOutfieldDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirAgentAndOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonAirPartAndDeptEntity;
import com.deppon.foss.util.CollectionUtils;
/**
 * 查询空运代理网点的公共选择器Service 实现类
 * @author 130566
 *
 */
public class CommonAirAgentAndOutfieldService implements
		ICommonAirAgentAndOutfieldService {
	
	private IAdministrativeRegionsService administrativeRegionsService;
	
	private ICommonAirAgentAndOutfieldDao agentAndOutfieldDao;
	
	public void setAgentAndOutfieldDao(
			ICommonAirAgentAndOutfieldDao agentAndOutfieldDao) {
		this.agentAndOutfieldDao = agentAndOutfieldDao;
	}
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * 分页查询
	 */
	@Override
	public List<CommonAirPartAndDeptEntity> queryDepartmentsByEntity(
			CommonAirPartAndDeptEntity airPartAndDeptEntity, int offset,
			int limit) {
		if(null ==airPartAndDeptEntity){
			airPartAndDeptEntity = new CommonAirPartAndDeptEntity();
		}
		List<CommonAirPartAndDeptEntity> entityList=agentAndOutfieldDao.queryDepartmentsByEntity(airPartAndDeptEntity, offset, limit);
		return attachName(entityList);
	}
	/**
	 * 添加名称
	 * @param entityList
	 * @return
	 */
	private List<CommonAirPartAndDeptEntity> attachName(
			List<CommonAirPartAndDeptEntity> entityList) {
		//
		if(CollectionUtils.isNotEmpty(entityList)){
			for (CommonAirPartAndDeptEntity commonAirPartAndDeptEntity : entityList) {
				attachName(commonAirPartAndDeptEntity);
			}
			
		}
		return entityList;
	}
	/**
	 * 
	 * @param commonAirPartAndDeptEntity
	 */
	private void attachName(
			CommonAirPartAndDeptEntity commonAirPartAndDeptEntity) {
		if(null !=commonAirPartAndDeptEntity){
			if(StringUtils.isNotBlank(commonAirPartAndDeptEntity.getCityCode())){
				commonAirPartAndDeptEntity.setCityName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(commonAirPartAndDeptEntity.getCityCode()));
			}
		}
		
	}
	/**
	 * 查询总数
	 */
	@Override
	public Long queryCount(CommonAirPartAndDeptEntity airPartAndDeptEntity) {
		if(null ==airPartAndDeptEntity){
			airPartAndDeptEntity = new CommonAirPartAndDeptEntity();
		}
		return agentAndOutfieldDao.queryCount(airPartAndDeptEntity);
	}

}

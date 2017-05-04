package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IStationOtherFuncAreaDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IStationOtherFuncAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.StationOtherFuncAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.StationOtherFuncAreaException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class StationOtherFuncAreaService implements
		IStationOtherFuncAreaService {

	private IStationOtherFuncAreaDao stationOtherFuncAreaDao;
	
	public void setStationOtherFuncAreaDao(
			IStationOtherFuncAreaDao stationOtherFuncAreaDao) {
		this.stationOtherFuncAreaDao = stationOtherFuncAreaDao;
	}
private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * 查询所有
	 */
	@Override
	public List<StationOtherFuncAreaEntity> queryAll(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		 
//		Set<String> set=FossUserContext.getCurrentUser().getRoleids();
//		
//		if(set.contains("TFR_ASTERISK_MAINTENANCE")){
//		}
//		List<String> deptCodes = FossUserContext.getCurrentUserManagerDeptCodes();
//		stationOtherFuncAreaEntity.setEmpNo(FossUserContext.getCurrentInfo().getEmpCode());
		return stationOtherFuncAreaDao.queryAll(stationOtherFuncAreaEntity);
	}

	/**
	 * 根据参数查询
	 */
	@Override
	public StationOtherFuncAreaEntity queryByEntityParam(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		 
		return stationOtherFuncAreaDao.queryByEntityParam(stationOtherFuncAreaEntity);
	}

	/**
	 * 数据更新
	 */
	@Override
	public Integer updateStationOtherFuncArea(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		StationOtherFuncAreaEntity stationOtherFuncAreaChild=new StationOtherFuncAreaEntity();
		stationOtherFuncAreaChild.setDeptNo(stationOtherFuncAreaEntity.getDeptNo());
		List<StationOtherFuncAreaEntity> stationOtherFuncAreas=stationOtherFuncAreaDao.queryAll(stationOtherFuncAreaChild);
		
		if(!CollectionUtils.isEmpty(stationOtherFuncAreas)){
			List<String> funcAreaNames=new ArrayList<String>();
			List<String> funcAreaNos=new ArrayList<String>();
			
			for(StationOtherFuncAreaEntity stationOtherFuncAreaParm:stationOtherFuncAreas){
				
				if(StringUtils.equals(stationOtherFuncAreaParm.getId(), stationOtherFuncAreaEntity.getId())){
					if(StringUtils.equals(stationOtherFuncAreaParm.getFuncAreaName(), stationOtherFuncAreaEntity.getFuncAreaName())&&
							StringUtils.equals(stationOtherFuncAreaParm.getFuncAreaNo(), stationOtherFuncAreaEntity.getFuncAreaNo())){
						break;
					}
					
					if(!StringUtils.equals(stationOtherFuncAreaParm.getFuncAreaName(), stationOtherFuncAreaEntity.getFuncAreaName())){
						funcAreaNames.add(stationOtherFuncAreaParm.getFuncAreaName());
					}
					if(!StringUtils.equals(stationOtherFuncAreaParm.getFuncAreaNo(), stationOtherFuncAreaEntity.getFuncAreaNo())){
						funcAreaNos.add(stationOtherFuncAreaParm.getFuncAreaNo());
					}
				}else{
					funcAreaNames.add(stationOtherFuncAreaParm.getFuncAreaName());
					funcAreaNos.add(stationOtherFuncAreaParm.getFuncAreaNo());
				}
//				this.stationOtherFuncAreaHandler(stationOtherFuncAreaParm,stationOtherFuncAreaEntity);
			}
			if(funcAreaNames.contains(stationOtherFuncAreaEntity.getFuncAreaName())){
				throw new StationOtherFuncAreaException(StationOtherFuncAreaException.STATIONOTHERFUNCAREA_CODE_NULL_ERROR_CODE,
						 "该外场场内其他功能区基础资料功能区名称已经存在！");
			}
			if(funcAreaNos.contains(stationOtherFuncAreaEntity.getFuncAreaNo())){
				throw new StationOtherFuncAreaException(StationOtherFuncAreaException.STATIONOTHERFUNCAREA_CODE_NULL_ERROR_CODE,
						 "该外场场内其他功能区基础资料功能区编码已经存在！");
			}
		}
			 
		stationOtherFuncAreaDao.deleteStationOtherFuncAreaById(stationOtherFuncAreaEntity);
		return stationOtherFuncAreaDao.addStationOtherFuncArea(stationOtherFuncAreaEntity);
		
	}

	/**
	 * 新增
	 */
	@Override
	public Integer addStationOtherFuncArea(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		List<StationOtherFuncAreaEntity> stationOtherFuncAreas=stationOtherFuncAreaDao.queryAll(stationOtherFuncAreaEntity);
		if(null!=stationOtherFuncAreas&&stationOtherFuncAreas.size()>0){
			for(StationOtherFuncAreaEntity entity:stationOtherFuncAreas){
			  this.stationOtherFuncAreaHandler(entity,stationOtherFuncAreaEntity);
		    }
		}
		return stationOtherFuncAreaDao.addStationOtherFuncArea(stationOtherFuncAreaEntity);
	}

	/**
	 * 作废
	 */
	@Override
	public Integer deleteStationOtherFuncArea(
			String codeStr) {
		
		String str[]=codeStr.split(",");
		return stationOtherFuncAreaDao.deleteStationOtherFuncArea(str);
	}
	
	/**
	 * 
	 * @param stationOtherFuncAreas
	 * @param stationOtherFuncAreaEntity
	 */
	private void stationOtherFuncAreaHandler(StationOtherFuncAreaEntity stationOtherFuncAreaChild,StationOtherFuncAreaEntity stationOtherFuncAreaEntity){
				
				if(StringUtils.equals(stationOtherFuncAreaChild.getFuncAreaName(), stationOtherFuncAreaEntity.getFuncAreaName())){
					
					 throw new StationOtherFuncAreaException(StationOtherFuncAreaException.STATIONOTHERFUNCAREA_CODE_NULL_ERROR_CODE,
							 "该外场场内其他功能区基础资料功能区名称已经存在！");
				}
				if(!StringUtils.isEmpty(stationOtherFuncAreaEntity.getFuncAreaNo())&&StringUtils.equals(stationOtherFuncAreaChild.getFuncAreaNo(), stationOtherFuncAreaEntity.getFuncAreaNo())){
					
					 throw new StationOtherFuncAreaException(StationOtherFuncAreaException.STATIONOTHERFUNCAREA_CODE_NULL_ERROR_CODE,
							 "该外场场内其他功能区基础资料功能区编码已经存在！");
				}
			
	}

	 
	@Override
	public Long countAll(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity) {
		List<String> bizTypes = new ArrayList<String> ();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		
		 if(StringUtils.isEmpty(stationOtherFuncAreaEntity.getDeptNo())){

				OrgAdministrativeInfoEntity outfield = orgAdministrativeInfoComplexService.
						queryOrgAdministrativeInfoIncludeSelfByCode(FossUserContext.getCurrentDept().getCode(), bizTypes);
				if(null!=outfield){
					stationOtherFuncAreaEntity.setDeptCode(outfield.getCode());
				}
		 }
		return stationOtherFuncAreaDao.countAll(stationOtherFuncAreaEntity);
	}

	/**
	 * 
	 */
	@Override
	public List<StationOtherFuncAreaEntity> queryAllByParam(
			StationOtherFuncAreaEntity stationOtherFuncAreaEntity, int offset,
			int limit) {
//		Set<String> set=FossUserContext.getCurrentUser().getRoleids();
//		
//		if(!set.contains("TFR_ASTERISK_MAINTENANCE")){
//			stationOtherFuncAreaEntity.setEmpNo(FossUserContext.getCurrentInfo().getEmpCode());
//		}
		List<String> bizTypes = new ArrayList<String> ();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		
		 if(StringUtils.isEmpty(stationOtherFuncAreaEntity.getDeptNo())){

				OrgAdministrativeInfoEntity outfield = orgAdministrativeInfoComplexService.
						queryOrgAdministrativeInfoIncludeSelfByCode(FossUserContext.getCurrentDept().getCode(), bizTypes);
				if(null!=outfield){
					stationOtherFuncAreaEntity.setDeptCode(outfield.getCode());
				}
		 }
		return stationOtherFuncAreaDao.queryAllByParam(stationOtherFuncAreaEntity, offset, limit);
		
	}

}

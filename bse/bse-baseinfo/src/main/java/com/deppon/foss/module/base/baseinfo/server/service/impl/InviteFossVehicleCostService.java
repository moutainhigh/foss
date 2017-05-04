package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IinviteFossVehicleCostDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IinviteFossVehicleCostService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.InviteFossVehicleCostQueryDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


public class InviteFossVehicleCostService implements IinviteFossVehicleCostService  {
	/**
	 * 日志打印对象申明
	 */
	private static final Logger log = Logger.getLogger(InviteFossVehicleCostService.class);
	
	
	private IinviteFossVehicleCostDao inviteFossVehicleCostDao;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setInviteFossVehicleCostDao(
			IinviteFossVehicleCostDao inviteFossVehicleCostDao) {
		this.inviteFossVehicleCostDao = inviteFossVehicleCostDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}



	/**
	 * 
	 * 导入零担外请车单票费用信息,导入之前，先匹配之前信息若有则修改没有则新增
	 * @author 332219
	 * @date 2016-03-22 下午7:13:55
	 * @param InviteFossVehicleCostEntitys 
	 */
	@Override
	@Transactional
	public void importInviteFossVehicleCost(List<InviteFossVehicleCostEntity> inviteFossVehicleCostEntitys) {
		// 获取当前登录用户信息
		UserEntity userEntity = FossUserContext.getCurrentUser();
		//用户编号
		String userCode = userEntity.getEmployee().getEmpCode();
		//用户名
		String userName = userEntity.getEmployee().getEmpName();
		
		for(InviteFossVehicleCostEntity se : inviteFossVehicleCostEntitys){
			InviteFossVehicleCostEntity entity = new InviteFossVehicleCostEntity();
			entity.setBusinessName(se.getBusinessName());
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService.queryOrgAdministrationInfoByName(se.getBusinessName());
			if(orgEntity != null){
				entity.setBusinessCode(orgEntity.getCode());
			}else{
				continue;
			}
			entity.setRegionalName(se.getRegionalName());
			OrgAdministrativeInfoEntity orgEntity1 = orgAdministrativeInfoComplexService.queryOrgAdministrationInfoByName(se.getRegionalName());
			if(orgEntity1 != null){
				entity.setRegionalCode(orgEntity1.getCode());
			}else{
				continue;
			}
			String inviteId = inviteFossVehicleCostDao.queryTotalByBusinessAndRegional(entity);
			if(inviteId != null && inviteId != ""){
				entity.setId(inviteId);
				entity.setVehicleCostMax(se.getVehicleCostMax());
				entity.setUpdateUser(userCode);
				entity.setUpdateUserName(userName);
				entity.setUpdateTime(new Date());
				//修改
				inviteFossVehicleCostDao.updateInviteFossVehicleCost(entity);
			}else{
				entity.setId(UUIDUtils.getUUID());
				entity.setVehicleCostMax(se.getVehicleCostMax());
				entity.setCreateUser(userCode);
				entity.setCreateUserName(userName);
				entity.setCreateTime(new Date());
				entity.setUpdateTime(new Date());
				entity.setActive(FossConstants.ACTIVE);
				//插入导入的数据
				inviteFossVehicleCostDao.insertInviteFossVehicleCost(entity);
			}
		}
		
	}
	
	/**
	 * 表单回显外请车单票费用总条数
	 */
	@Override
	public long queryTotalByVehicleCost(InviteFossVehicleCostQueryDto queryDto) {		
		return inviteFossVehicleCostDao.queryTotalByVehicleCost(queryDto);
	}
	
	/**
	 * 表单回显所有外请车单票费用信息
	 */
	@Override
	public List<InviteFossVehicleCostEntity> queryVehicleDrivingByVehicleCost(InviteFossVehicleCostQueryDto queryDto,int start, int limit) {
		
		return inviteFossVehicleCostDao.queryVehicleDrivingByVehicleCost(queryDto, start, limit);
	}
	
	/**
	 * 修改外请车单票费用信息
	 */
	@Override
	public void updateInviteFossVehicleCost(InviteFossVehicleCostEntity entity) {
		// 获取当前登录用户信息
		UserEntity userEntity = FossUserContext.getCurrentUser();
		//用户编号
		String userCode = userEntity.getEmployee().getEmpCode();
		//用户名
		String userName = userEntity.getEmployee().getEmpName();
		entity.setUpdateUser(userCode);
		entity.setUpdateUserName(userName);
		entity.setUpdateTime(new Date());
		//修改
		inviteFossVehicleCostDao.updateInviteFossVehicleCost(entity);
		
	}
	
	/**
	 * 根据大区查询部门费用信息
	 */
	@Override
	public InviteFossVehicleCostEntity queryVehicleDrivingByRegionalCode(String regionalCode) {
		
		return inviteFossVehicleCostDao.queryVehicleDrivingByRegionalCode(regionalCode);
	}
}
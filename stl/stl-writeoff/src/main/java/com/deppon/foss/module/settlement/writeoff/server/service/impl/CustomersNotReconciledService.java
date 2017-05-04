/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.ICustomersNotReconciledDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.ICustomersNotReconciledService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomersNotReconciledEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomersNotReconciledDto;

public class CustomersNotReconciledService implements ICustomersNotReconciledService {
	
	private ICustomersNotReconciledDao customersNotReconciledDao;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	@Override
	public List<CustomersNotReconciledEntity> queryCustomersNotReconciled(
			CustomersNotReconciledDto dto) {
		// 获取当前登录用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		// 将orgCode设置给dto
		dto.setEmpCode(currentInfo.getEmpCode());
		validateDto(dto);
		List<CustomersNotReconciledEntity> list = customersNotReconciledDao.queryCustomersNotReconciled(dto);
		return list;
	}
	
	private void validateDto(CustomersNotReconciledDto dto) {
		//声明目标部门集合
		List<String> targetLsit = new ArrayList<String>();
		
		//如果部门存在，则获取当前部门与用户所管辖部门的交集
		if (StringUtils.isNotBlank(dto.getStatementOrgCode())) {
			targetLsit.add(dto.getStatementOrgCode());
		} else{
			//如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集	
			if(StringUtils.isNotBlank(dto.getSmallRegionCode())){
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(dto.getSmallRegionCode());
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					targetLsit.add(en.getCode());
				}
			}else{
				//如果部门、小区都不存在，而大区存在，	则获取大区底下所有部门与该用户所管辖部门交集	
				if(StringUtils.isNotBlank(dto.getLargeRegionCode())){
					//调用综合方法查询
					List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(dto.getLargeRegionCode());
					//循环部门，获取用户所管辖部门与查询到部门的交集
					for(OrgAdministrativeInfoEntity en: orgList){
						targetLsit.add(en.getCode());
					}
				}
			}
		}
		// 设置可查询部门结果集
		dto.setOrgCodeList(targetLsit);
	}
	
	public ICustomersNotReconciledDao getCustomersNotReconciledDao() {
		return customersNotReconciledDao;
	}
	public void setCustomersNotReconciledDao(
			ICustomersNotReconciledDao customersNotReconciledDao) {
		this.customersNotReconciledDao = customersNotReconciledDao;
	}

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
}

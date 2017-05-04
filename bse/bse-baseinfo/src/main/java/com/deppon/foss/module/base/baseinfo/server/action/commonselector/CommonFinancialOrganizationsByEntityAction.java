package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;

public class CommonFinancialOrganizationsByEntityAction extends AbstractAction implements
IQueryAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IFinancialOrganizationsService financialOrganizationsService;
	private List<FinancialOrganizationsEntity> financialOrgs;
	private FinancialOrganizationsEntity entity;
	@Override
	public String query() {
		financialOrgs = financialOrganizationsService.queryFinancialOrganizationsByEntity(entity, start, limit);
		setTotalCount(financialOrganizationsService.queryFinancialOrganizationsByEntityCount(entity));
		// TODO Auto-generated method stub
		return returnSuccess();
	}
	public FinancialOrganizationsEntity getEntity() {
		return entity;
	}
	public void setEntity(FinancialOrganizationsEntity entity) {
		this.entity = entity;
	}
	public void setFinancialOrganizationsService(
			IFinancialOrganizationsService financialOrganizationsService) {
		this.financialOrganizationsService = financialOrganizationsService;
	}
	public List<FinancialOrganizationsEntity> getFinancialOrgs() {
		return financialOrgs;
	}
	public void setFinancialOrgs(List<FinancialOrganizationsEntity> financialOrgs) {
		this.financialOrgs = financialOrgs;
	}
	

}

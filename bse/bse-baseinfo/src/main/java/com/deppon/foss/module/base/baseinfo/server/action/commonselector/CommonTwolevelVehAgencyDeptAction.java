package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IToAddPartnerProgramService;

/**
 * TODO(合伙人到达加收方案：目的站网点名称查询)
 * version:V1.0,author:352676,date:2016年9月1日 下午3:01:26,content:TODO
 * </p>
 * 
 * @author 352676
 * @date 2016年9月1日 下午3:01:26
 * @since
 * @version
 */
public class CommonTwolevelVehAgencyDeptAction extends AbstractAction implements
		IQueryAction {

	private static final long serialVersionUID = 1245218334534507L;

	private SaleDepartmentEntity saleDepartmentEntity = new SaleDepartmentEntity();
	
	private List<SaleDepartmentEntity> saleDepartmentEntitys;
	
	private IToAddPartnerProgramService toAddPartnerProgramService;

	/**
	 * <p>
	 * TODO(合伙人到达加收方案：目的站网点名称查询)
	 * </p>
	 * 
	 * @author 352676
	 * @date 2016年9月1日 下午2:59:25
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		saleDepartmentEntitys = toAddPartnerProgramService.queryTwolevelAgencyDeptsByCondition(saleDepartmentEntity, limit, start);
		this.setTotalCount(toAddPartnerProgramService.queryTwolevelAgencyDeptsCount(saleDepartmentEntity));
		return returnSuccess();
	}

	public SaleDepartmentEntity getSaleDepartmentEntity() {
		return saleDepartmentEntity;
	}

	public void setSaleDepartmentEntity(SaleDepartmentEntity saleDepartmentEntity) {
		this.saleDepartmentEntity = saleDepartmentEntity;
	}

	public List<SaleDepartmentEntity> getSaleDepartmentEntitys() {
		return saleDepartmentEntitys;
	}

	public void setSaleDepartmentEntitys(
			List<SaleDepartmentEntity> saleDepartmentEntitys) {
		this.saleDepartmentEntitys = saleDepartmentEntitys;
	}


 	

	public void setToAddPartnerProgramService(
			IToAddPartnerProgramService toAddPartnerProgramService) {
		this.toAddPartnerProgramService = toAddPartnerProgramService;
	}
	
	
	
	
}

package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

public interface ICommonPartnerDeptDao {

	List<SaleDepartmentEntity> queryPartnerDept(
			SaleDepartmentEntity saleDepartmentEntity, int start, int limit);

	Long queryPartnerDeptCount(SaleDepartmentEntity saleDepartmentEntity);

}

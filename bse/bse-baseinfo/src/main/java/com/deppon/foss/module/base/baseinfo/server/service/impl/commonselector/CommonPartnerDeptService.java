package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPartnerDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPartnerDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.util.CollectionUtils;

public class CommonPartnerDeptService implements ICommonPartnerDeptService{

	ICommonPartnerDeptDao commonPartnerDeptDao;
	@Override
	public List<SaleDepartmentEntity> queryPartnerDeptsByCondition(SaleDepartmentEntity saleDepartmentEntity, int limit, int start) {
		
		List<SaleDepartmentEntity> list=commonPartnerDeptDao.queryPartnerDept(saleDepartmentEntity, start, limit);
		
		if(CollectionUtils.isNotEmpty(list)){
			for (SaleDepartmentEntity resultEntity : list) {
				if(StringUtil.isEmpty(resultEntity.getCode())){
					resultEntity.setCode(" ");
				}
			}
		}
		return list;
	}

	@Override
	public Long queryPartnerDeptDeptsCount(
			SaleDepartmentEntity saleDepartmentEntity) {
		if (null == saleDepartmentEntity) {
			saleDepartmentEntity = new SaleDepartmentEntity();
		}
		return commonPartnerDeptDao.queryPartnerDeptCount(saleDepartmentEntity);
	}

	public void setCommonPartnerDeptDao(ICommonPartnerDeptDao commonPartnerDeptDao) {
		this.commonPartnerDeptDao = commonPartnerDeptDao;
	}

}

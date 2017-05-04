package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBindingLeasedTruckDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BindingLeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;

public class BindingLeasedTruckDao extends SqlSessionDaoSupport implements IBindingLeasedTruckDao {

	 private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".bindingLeasedtruck";
	
	/**
     * 外请车司机登录APP
     * PDA调用，通过车牌号获取车队code、操作人code
     * @author 310854
     * @date 2016-5-28
     */
	@Override
	public BindingLeasedTruckEntity queryBindingLeasedDate(
			LeasedTruckEntity leasedTruck) {

		return (BindingLeasedTruckEntity)getSqlSession().selectOne(NAMESPACE + ".queryBindingLeasedTruck", leasedTruck);
	}

}

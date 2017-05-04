package com.deppon.foss.module.base.baseinfo.api.server.dao;


import com.deppon.foss.module.base.baseinfo.api.shared.domain.BindingLeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;

public interface IBindingLeasedTruckDao {

	/**
     * 外请车司机登录APP
     * PDA调用，通过车牌号获取车队code、操作人code
     * @author 310854
     * @date 2016-5-28
     */
    BindingLeasedTruckEntity queryBindingLeasedDate(LeasedTruckEntity leasedTruck);
}

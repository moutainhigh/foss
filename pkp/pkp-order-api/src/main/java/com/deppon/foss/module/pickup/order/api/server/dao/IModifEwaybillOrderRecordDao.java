package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.ModifyEwaybillOrderLogEntity;

public interface IModifEwaybillOrderRecordDao {
	/**
     * 插入日志
     * */
    int insertSelective(ModifyEwaybillOrderLogEntity record);
}

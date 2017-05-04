package com.deppon.foss.module.settlement.consumer.api.server.dao;


import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditLogEntity;

import java.util.List;

public interface ICodAuditLogDao {
    /**
     * 插入日志
     * @param record
     * @return
     */
    int insert(CodAuditLogEntity record);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertBath(List<CodAuditLogEntity> list);

    /**
     * 根据单号查询
     * @param waybillNo
     * @return
     */
    List<CodAuditLogEntity> selectByWaybillNo(String waybillNo);

}
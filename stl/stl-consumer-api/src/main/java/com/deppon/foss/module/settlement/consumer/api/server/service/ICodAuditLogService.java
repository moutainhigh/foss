package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditLogEntity;

import java.util.List;

/**
 * Created by 073615 on 2015/12/13.
 */
public interface ICodAuditLogService extends IService {

    /**
     * 单个插入
     * @param record
     * @return
     */
    int insertOne(CodAuditLogEntity record);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertBath(List<CodAuditLogEntity> list);

    /**
     * 通过运单号查询
     * @param WaybillNo
     * @return
     */
    List<CodAuditLogEntity> selectByWaybillNo(String waybillNo);
}

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.deppon.foss.module.settlement.consumer.api.server.dao.ICodAuditLogDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditLogService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditLogEntity;

import java.util.List;

/**
 * Created by 073615 on 2015/12/13.
 */
public class CodAuditLogService implements ICodAuditLogService {

    ICodAuditLogDao codAuditLogDao;
    /**
     * 单个插入
     * @param record
     * @return
     */
    @Override
    public int insertOne(CodAuditLogEntity record) {
        return codAuditLogDao.insert(record);
    }

    /**
     * 批量插入
     * @param list
     * @return
     */
    @Override
    public int insertBath(List<CodAuditLogEntity> list) {
        return codAuditLogDao.insertBath(list);
    }

    /**
     * 根据单号查询
     * @param waybillNo
     * @return
     */
    @Override
    public List<CodAuditLogEntity> selectByWaybillNo(String waybillNo) {
        return codAuditLogDao.selectByWaybillNo(waybillNo);
    }

    public ICodAuditLogDao getCodAuditLogDao() {
        return codAuditLogDao;
    }

    public void setCodAuditLogDao(ICodAuditLogDao codAuditLogDao) {
        this.codAuditLogDao = codAuditLogDao;
    }
}

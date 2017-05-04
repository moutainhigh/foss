package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICodAuditLogDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditLogEntity;

import java.util.List;

/**
 * Created by 073615 on 2015/11/30.
 */
public class CodAuditLogDao extends iBatis3DaoImpl implements ICodAuditLogDao {

    private static final String NAMESPACE ="foss.stl.codAuditLogMapper.";

    /**
     * 插入日志
     *
     * @param record
     * @return
     */
    @Override
    public int insert(CodAuditLogEntity record) {

        return getSqlSession().insert(NAMESPACE+"insertLogOne",record);
    }

    /**
     * 批量插入日志
     * @param list
     * @return
     */
    @Override
    public int insertBath(List<CodAuditLogEntity> list) {
        return getSqlSession().insert(NAMESPACE+"insertLogBath",list);
    }

    /**
     * 根据运单号查询
     *
     * @param waybillNo
     * @return
     */
    @Override
    public List<CodAuditLogEntity> selectByWaybillNo(String waybillNo) {
        return getSqlSession().selectList(NAMESPACE+"selectByWaybillNo",waybillNo);
    }
}

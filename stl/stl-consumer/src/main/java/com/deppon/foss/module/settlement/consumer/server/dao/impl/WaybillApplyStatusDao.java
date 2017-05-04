package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IWaybillApplyStatusDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillApplyStatusEntity;

/**
 * Created by 322906 on 2016/6/14.
 */
public class WaybillApplyStatusDao extends iBatis3DaoImpl implements IWaybillApplyStatusDao {
    private static String NAMESPACE = "foss.stl.WaybillApplyStatusDao.";
    @Override
    public int add(WaybillApplyStatusEntity waybillApplyStatusEntity) {
        return getSqlSession().insert(NAMESPACE + "insert",waybillApplyStatusEntity);

    }

    @Override
    public void updateBillStatus(WaybillApplyStatusEntity waybillApplyStatusEntity) {
         getSqlSession().update(NAMESPACE + "updateBillStatus",waybillApplyStatusEntity);
    }

    @Override
    public WaybillApplyStatusEntity queryByBillNo(String billNo) {
        return (WaybillApplyStatusEntity)getSqlSession().selectOne(NAMESPACE + "queryByBillNo", billNo);
    }

}

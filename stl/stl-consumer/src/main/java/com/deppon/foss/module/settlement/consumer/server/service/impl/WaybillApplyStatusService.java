package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.deppon.foss.module.settlement.consumer.api.server.dao.IWaybillApplyStatusDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillApplyStatusService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillApplyStatusEntity;

/**
 * Created by 322906 on 2016/6/14.
 */
public class WaybillApplyStatusService implements IWaybillApplyStatusService {

    private IWaybillApplyStatusDao waybillApplyStatusDao;

    @Override
    public void add(WaybillApplyStatusEntity waybillApplyStatusEntity) {
        waybillApplyStatusDao.add(waybillApplyStatusEntity);
    }

    @Override
    public WaybillApplyStatusEntity queryByBillNo(String billNo) {
        WaybillApplyStatusEntity entity = waybillApplyStatusDao.queryByBillNo(billNo);
        return entity;
    }

    @Override
    public void updateBillStatus(WaybillApplyStatusEntity waybillApplyStatusEntity) {
        waybillApplyStatusDao.updateBillStatus(waybillApplyStatusEntity);
    }


    public void setWaybillApplyStatusDao(IWaybillApplyStatusDao waybillApplyStatusDao) {
        this.waybillApplyStatusDao = waybillApplyStatusDao;
    }
}

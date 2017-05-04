package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillApplyStatusEntity;

/**
 * Created by 322906 on 2016/6/14.
 */
public interface IWaybillApplyStatusService {
    void add(WaybillApplyStatusEntity entity);
    WaybillApplyStatusEntity queryByBillNo(String billNo);
    void updateBillStatus(WaybillApplyStatusEntity waybillApplyStatusEntity);
}

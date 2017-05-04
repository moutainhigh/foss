package com.deppon.foss.module.settlement.consumer.api.server.dao;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillApplyStatusEntity;

/**
 * Created by 322906 on 2016/6/14.
 */
public interface IWaybillApplyStatusDao {
    int add(WaybillApplyStatusEntity waybillApplyStatusEntity);
    void updateBillStatus(WaybillApplyStatusEntity waybillApplyStatusEntity);

    /**
     * 通过单号查询该单号是否存在
     * @param billNo
     * @return
     */
    WaybillApplyStatusEntity queryByBillNo(String billNo);
}

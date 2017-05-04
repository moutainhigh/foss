package com.deppon.foss.module.settlement.consumer.api.server.service;



import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;

import java.util.List;

/**
 * Created by 322906 on 2016/6/30.
 */
public interface IWaybillDetailService {
    void addWaybillDetailEntity(WaybillDetailEntity waybillDetailEntity);
    void updateWaybillDetail(WaybillDetailEntity waybillDetailEntity);

    /**
     * 根据合并单号查询已经合并的运单信息
     * @param mergeWaybillNo
     * @return
     */
    List<WaybillDetailEntity> queryWaybillDetailByMergeWaybillNo(String mergeWaybillNo);

    WaybillDetailEntity queryWaybillDetailByWaybillNo(String waybillNo);



    /**
     * 根据运单号查询运单详细表中合并运单号
     * @param waybillNo
     * @return
     */
    String queryMergeWaybillNoByWaybillNo(String waybillNo);


    /**
     * 作废合并运单时，将对应的运单删除
     */
    void deleteWaybill(String mergeWaybillNo);
    /**
     * 作废运单时，将对应的运单删除
     */
    void delete(String waybillNo);


}

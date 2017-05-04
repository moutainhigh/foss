package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.deppon.foss.module.settlement.consumer.api.server.dao.IWaybillDetailDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillDetailService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;

import java.util.List;

/**
 * Created by 322906 on 2016/6/30.
 */
public class WaybillDetailService  implements IWaybillDetailService{


    private IWaybillDetailDao waybillDetailDao;


    /**
     * 合并操作时，保存运单信息在详细表中
     * @param waybillDetailEntity
     */
    @Override
    public void addWaybillDetailEntity(WaybillDetailEntity waybillDetailEntity) {
        waybillDetailDao.addWaybillDetailEntity(waybillDetailEntity);
    }

    @Override
    public void updateWaybillDetail(WaybillDetailEntity waybillDetailEntity) {
        waybillDetailDao.updateWaybillDetailEntity(waybillDetailEntity);
    }

    /**
     * 通过合并运单号 查询 运单详细信息
     * @param mergeWaybillNo
     * @return
     */
    @Override
    public List<WaybillDetailEntity> queryWaybillDetailByMergeWaybillNo(String mergeWaybillNo) {
        List<WaybillDetailEntity> waybillDetailEntityList = waybillDetailDao.queryWaybillDetailByMergeWaybillNo(mergeWaybillNo);
        return waybillDetailEntityList;
    }

    @Override
    public WaybillDetailEntity queryWaybillDetailByWaybillNo(String waybillNo) {
        return waybillDetailDao.queryWaybillByWaybillNo(waybillNo);
    }

    /**
     * 通过运单号 查询合并运单号
     * @param waybillNo
     * @return
     */
    @Override
    public String queryMergeWaybillNoByWaybillNo(String waybillNo) {
        String mergeWaybillNo = waybillDetailDao.queryMergeWaybillNoByWaybillNo(waybillNo);
        return mergeWaybillNo;
    }


    /**
     * 根据合并运单号删除运单
     * @param mergeWaybillNo
     */
    @Override
    public void deleteWaybill(String mergeWaybillNo) {
        waybillDetailDao.deleteWaybill(mergeWaybillNo);
    }

    @Override
    public void delete(String waybillNo) {
        waybillDetailDao.delete(waybillNo);
    }


    public void setWaybillDetailDao(IWaybillDetailDao waybillDetailDao) {
        this.waybillDetailDao = waybillDetailDao;
    }
}

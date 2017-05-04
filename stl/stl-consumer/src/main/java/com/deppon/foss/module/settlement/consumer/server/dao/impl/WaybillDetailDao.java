package com.deppon.foss.module.settlement.consumer.server.dao.impl;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;

import com.deppon.foss.module.settlement.consumer.api.server.dao.IWaybillDetailDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;

import java.util.List;

/**
 * Created by 322906 on 2016/6/30.
 */
public class WaybillDetailDao extends iBatis3DaoImpl implements IWaybillDetailDao {

    private static final String NAMESPACE = "foss.stl.WaybillDetailDao.";// 命名空间


    /**
     * 合并过的运单
     * 添加运单信息包含合并运单号
     * @param waybillDetailEntity
     * @return
     */
    @Override
    public int addWaybillDetailEntity(WaybillDetailEntity waybillDetailEntity) {
        return (Integer)getSqlSession().insert(NAMESPACE + "addWaybillDetailEntity", waybillDetailEntity);
    }

    @Override
    public void updateWaybillDetailEntity(WaybillDetailEntity waybillDetailEntity) {
         getSqlSession().update(NAMESPACE + "updateWaybillDetailEntity", waybillDetailEntity);
    }

    @Override
    public List<WaybillDetailEntity> queryWaybillDetailByMergeWaybillNo(String mergeWaybillNo) {
        return getSqlSession().selectList(NAMESPACE + "queryWaybillDetailByMergeWaybillNo", mergeWaybillNo);
    }

    @Override
    public String queryMergeWaybillNoByWaybillNo(String waybillNo) {
        return (String)this.getSqlSession().selectOne(NAMESPACE + "queryMergeWaybillNoByWaybillNo", waybillNo);

    }

    @Override
    public WaybillDetailEntity queryWaybillByWaybillNo(String waybillNo) {
        return (WaybillDetailEntity)getSqlSession().selectOne(NAMESPACE + "queryWaybillDetailByWaybillNo", waybillNo);
    }

    @Override
    public void deleteWaybill(String mergeWaybillNo) {
         this.getSqlSession().delete(NAMESPACE + "cancelWaybill", mergeWaybillNo);
    }

    @Override
    public void delete(String waybillNo) {
        this.getSqlSession().delete(NAMESPACE + "delete", waybillNo);
    }
}

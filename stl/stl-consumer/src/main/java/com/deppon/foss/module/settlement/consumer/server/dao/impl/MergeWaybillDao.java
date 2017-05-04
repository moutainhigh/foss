package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IMergeWaybillDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.MergeWaybillEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.*;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 322906 on 2016/6/13.
 */
public class MergeWaybillDao extends iBatis3DaoImpl implements IMergeWaybillDao {

    private static final String NAMESPACE = "foss.stl.MergeWaybillDao.";// 命名空间

    /**
     * 更新合并运单
     * @param mergeWaybillEntity
     */
    @Override
    public void updateMergeWaybill(MergeWaybillEntity mergeWaybillEntity) {
          getSqlSession().update(NAMESPACE + "updateMergeWaybill", mergeWaybillEntity);
    }


    /**
     * 作废合并运单
     * @param mergeWaybillNo
     */
    @Override
    public void cancelMergeWaybill(String mergeWaybillNo) {
          getSqlSession().delete(NAMESPACE + "cancelMergeWaybill", mergeWaybillNo);
    }


    /**
     * 通过合并运单号，税务号查询合并运单信息
     * @param mergeWaybillNo
     * @param taxId
     * @return
     */
    @Override
    public MergeWaybill4FimsDto queryMergeWaybillByMergeWaybillNo(String mergeWaybillNo,String taxId) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("mergeWaybillNo",mergeWaybillNo);
        map.put("taxId",taxId);
        return  (MergeWaybill4FimsDto)getSqlSession().selectOne(NAMESPACE+"queryMergeWaybillByMergeWaybillNoAndTaxID", map);
    }

    @Override
    public MergeWaybillDto queryMergeWaybillByMergeWaybillNo(String mergeWaybillNo) {
        return (MergeWaybillDto)getSqlSession().selectOne(NAMESPACE+"queryMergeWaybillByMergeWaybillNo", mergeWaybillNo);
    }

    @Override
    public List<MergeWaybill4FimsDto> queryMergeWaybillByMergeWaybillNo(List mergeWaybillNos, String taxId) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("mergeWaybillNos",mergeWaybillNos);
        map.put("taxId",taxId);
        return  getSqlSession().selectList(NAMESPACE+"queryMergeWaybillsByMergeWaybillNoAndTaxID", map);
    }

    @Override
    public List<WaybillInvoiceDto> queryMergeWaybillByMergeWaybillNo(List mergeWaybillNos) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("mergeWaybillNos",mergeWaybillNos);
        return  getSqlSession().selectList(NAMESPACE+"queryMergeWaybillsByMergeWaybillNos", map);
    }


    /**
     * 新增合并运单
     * @param waybillEntity
     * @return
     */
    @Override
    public int add(MergeWaybillEntity waybillEntity) {
        return (Integer)getSqlSession().insert(NAMESPACE + "addMergeWaybill", waybillEntity);
    }



    /**
     * 按条件查询合并运单
     * @param mergeWaybillQueryDto
     * @param start
     * @param limit
     * @return
     */
    @Override
    public List<MergeWaybillDto> queryMergeWaybillByConditions(MergeWaybillQueryDto mergeWaybillQueryDto,int start, int limit) {
        RowBounds rowBounds = new RowBounds(start,limit);
        return this.getSqlSession().selectList(NAMESPACE + "queryMergeWaybillByConditions", mergeWaybillQueryDto, rowBounds);
    }

    /**
     * 条数
     * @param mergeWaybillQueryDto
     * @return
     */
    @Override
    public int queryMergeWaybillByConditionsCounts(MergeWaybillQueryDto mergeWaybillQueryDto) {
        return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryMergeWaybillByConditionsCounts", mergeWaybillQueryDto);
    }




}

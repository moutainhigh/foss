package com.deppon.foss.module.settlement.consumer.api.server.dao;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.MergeWaybillEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.*;

import java.util.List;

/**
 * Created by 322906 on 2016/6/13.
 */
public interface IMergeWaybillDao {
    /**
     * 更细合并运单
     * @param mergeWaybillEntity
     */
    void updateMergeWaybill(MergeWaybillEntity mergeWaybillEntity);


    /**
     * 作废合并运单
     * 需要操作俩张表（T_STL_WAYBILL_DETAIL 和 T_STL_MERGE_WAYBILL）,
     * 根据合并运单号删除
     * @param mergeWaybillNo
     */
    void cancelMergeWaybill(String mergeWaybillNo);


    /**
     * 通过合并运单号和税务号查询合并运单详细信息
     * @param mergeWaybillNo
     * @param taxId
     * @return
     */
    MergeWaybill4FimsDto queryMergeWaybillByMergeWaybillNo(String mergeWaybillNo,String taxId);

    /**
     * 通过合并运单号查询合并运单信息
     * @param mergeWaybillNo
     * @return
     */
    MergeWaybillDto queryMergeWaybillByMergeWaybillNo(String mergeWaybillNo);

    List<MergeWaybill4FimsDto> queryMergeWaybillByMergeWaybillNo(List mergeWaybillNos,String taxId);

    /**
     * 通过合并运单号集合查询合并运单信息返回给发票系统
     * @param mergeWaybillNos
     * @return
     */
    List<WaybillInvoiceDto> queryMergeWaybillByMergeWaybillNo(List mergeWaybillNos);



    /**
     * 添加合并运单实体
     * @param waybillEntity
     * @return
     */
    int add(MergeWaybillEntity waybillEntity);



    /**
     * 按页面条件查询合并运单信息
     * @param mergeWaybillQueryDto
     * @return
     */
    List<MergeWaybillDto> queryMergeWaybillByConditions(MergeWaybillQueryDto mergeWaybillQueryDto,int start, int limit);
    int queryMergeWaybillByConditionsCounts(MergeWaybillQueryDto mergeWaybillQueryDto);


}

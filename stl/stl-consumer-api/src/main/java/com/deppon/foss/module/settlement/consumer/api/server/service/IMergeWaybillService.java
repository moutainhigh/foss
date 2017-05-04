package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.MergeWaybillEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.*;

import java.util.List;

/**
 * Created by 322906 on 2016/6/13.
 */
public interface IMergeWaybillService {
    /**
     * 更新合并运单信息
     * @param mergeWaybillEntity
     */
    void updateMergeWaybill(MergeWaybillEntity mergeWaybillEntity);

    /**
     * 根据合并运单号查询运单详细信息
     * @param mergeWaybillNo
     * @return
     */
    List<WaybillDetailEntity> queryWaybillDetailByMergeWaybillNo(String mergeWaybillNo);

    /**
     * 根据合并运单号查询合并运单信息
     * @param mergeWaybillNo
     * @return
     */
    MergeWaybillDto queryMergeWaybillByMergeWaybillNo(String mergeWaybillNo);

    /**
     * 作废合并运单
     * @param mergeWaybillNo
     */
    void cancelMergeWaybill(String mergeWaybillNo);

    /**
     * 添加合并运单
     * @param mergeWaybillEntity
     */
    void addMergeWaybill(MergeWaybillEntity mergeWaybillEntity);


    /**
     * 根据运单号和税务号获取合并运单明细信息
     * @param waybillNo
     * @param taxId
     * @return
     */
    MergeWaybill4FimsDto getMergeWaybillByMergeWaybillNo(String waybillNo,String taxId);

    List<MergeWaybill4FimsDto> getMergeWaybillByMergeWaybillNo(List waybillNos,String taxId);

    /**
     * 根据合并运单号获取合并运单信息
     * @param waybillNos
     * @return
     */
    List<WaybillInvoiceDto> getMergeWaybillByMergeWaybillNo(List waybillNos);


    /**
     * 分页查询合并运单
     * @param mergeWaybillQueryDto
     * @param start
     * @param limit
     * @return
     */
    List<MergeWaybillDto> queryMergeWaybillByConditions(MergeWaybillQueryDto mergeWaybillQueryDto,int start, int limit);

    /**
     * 获取合并运单条数
     * @param mergeWaybillQueryDto
     * @return
     */
    int queryMergeWaybillByConditionsCounts(MergeWaybillQueryDto mergeWaybillQueryDto);










}

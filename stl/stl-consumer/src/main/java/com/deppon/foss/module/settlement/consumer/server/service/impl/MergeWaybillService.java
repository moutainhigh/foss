package com.deppon.foss.module.settlement.consumer.server.service.impl;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IMergeWaybillDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IMergeWaybillService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillApplyStatusService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillDetailService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.MergeWaybillEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillApplyStatusEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 322906 on 2016/6/13.
 */
public class MergeWaybillService implements IMergeWaybillService {

    public static final String YES = "Y";
    public static final String NO = "N";

    private IMergeWaybillDao mergeWaybillDao;


    /**
     * 运单，合并运单申请状态服务
     */
    private IWaybillApplyStatusService waybillApplyStatusService;

    private IWaybillDetailService waybillDetailService;

    /**
     * 运单发更改是更新合并运单里面的金额
     * @param mergeWaybillEntity
     */
    @Override
    public void updateMergeWaybill(MergeWaybillEntity mergeWaybillEntity) {
        mergeWaybillDao.updateMergeWaybill(mergeWaybillEntity);
    }

    /**
     * 根据合并运单号查询合并运单详细
     * @param mergeWaybillNo
     * @return
     */
    @Override
    public List<WaybillDetailEntity> queryWaybillDetailByMergeWaybillNo(String mergeWaybillNo) {
        List<WaybillDetailEntity> waybillDetailEntityList = waybillDetailService.queryWaybillDetailByMergeWaybillNo(mergeWaybillNo);
        return waybillDetailEntityList;
    }

    @Override
    public MergeWaybillDto queryMergeWaybillByMergeWaybillNo(String mergeWaybillNo) {
        return mergeWaybillDao.queryMergeWaybillByMergeWaybillNo(mergeWaybillNo);
    }

    /**
     * 根据合并运单号作废合并运单
     * 1.作废合并运单
     * 2.删除运单详细表中的已经合并的运单
     * @param billNo
     */
    @Transactional
    @Override
    public void cancelMergeWaybill(String billNo) {
        WaybillApplyStatusEntity waybillApplyStatusEntity = new WaybillApplyStatusEntity();
        waybillApplyStatusEntity.setBillNo(billNo);
        WaybillApplyStatusEntity entity = waybillApplyStatusService.queryByBillNo(billNo);
        //判断合并运单是否已经开具发票

        if(entity !=null && YES.equals(entity.getStatus())){
            throw new SettlementException("合并运单已经开具发票，不能作废");
        }

        mergeWaybillDao.cancelMergeWaybill(billNo);
        waybillDetailService.deleteWaybill(billNo);
    }

    /**
     * 新增合并运单
     * @param mergeWaybillEntity
     */
    @Override
    public void addMergeWaybill(MergeWaybillEntity mergeWaybillEntity) {
        mergeWaybillDao.add(mergeWaybillEntity);
    }


    /**
     * 通过合并运单号,税务号查询合并运单
     * @param waybillNo
     * @return
     */
    @Override
    public MergeWaybill4FimsDto getMergeWaybillByMergeWaybillNo(String waybillNo,String taxId) {
        MergeWaybill4FimsDto mergeWaybillDto = mergeWaybillDao.queryMergeWaybillByMergeWaybillNo(waybillNo,taxId);
        return mergeWaybillDto;
    }

    @Override
    public List<MergeWaybill4FimsDto> getMergeWaybillByMergeWaybillNo(List waybillNos, String taxId) {
        List<MergeWaybill4FimsDto> mergeWaybillDto = mergeWaybillDao.queryMergeWaybillByMergeWaybillNo(waybillNos,taxId);
        return mergeWaybillDto;
    }

    @Override
    public List<WaybillInvoiceDto> getMergeWaybillByMergeWaybillNo(List waybillNos) {
        List<WaybillInvoiceDto> mergeWaybillDto = mergeWaybillDao.queryMergeWaybillByMergeWaybillNo(waybillNos);
        return mergeWaybillDto;
    }


    /**
     * 根据条件，分页查询合并运单
     * @param mergeWaybillQueryDto
     * @param start
     * @param limit
     * @return
     */
    @Override
    public List<MergeWaybillDto> queryMergeWaybillByConditions(MergeWaybillQueryDto mergeWaybillQueryDto,int start, int limit) {
        List<MergeWaybillDto> mergeWaybillDtoList = mergeWaybillDao.queryMergeWaybillByConditions(mergeWaybillQueryDto, start, limit);
        return mergeWaybillDtoList;
    }


    @Override
    public int queryMergeWaybillByConditionsCounts(MergeWaybillQueryDto mergeWaybillQueryDto) {
        int count = mergeWaybillDao.queryMergeWaybillByConditionsCounts(mergeWaybillQueryDto);
        return count;
    }


    public void setMergeWaybillDao(IMergeWaybillDao mergeWaybillDao) {
        this.mergeWaybillDao = mergeWaybillDao;
    }

    public void setWaybillApplyStatusService(IWaybillApplyStatusService waybillApplyStatusService) {
        this.waybillApplyStatusService = waybillApplyStatusService;
    }

    public void setWaybillDetailService(IWaybillDetailService waybillDetailService) {
        this.waybillDetailService = waybillDetailService;
    }
}

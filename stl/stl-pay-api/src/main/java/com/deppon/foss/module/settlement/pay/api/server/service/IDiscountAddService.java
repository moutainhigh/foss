package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.ReceivableBillDto;

import java.util.List;

/**
 * Created by 073615 on 2015/2/6.
 */
public interface IDiscountAddService extends IService{
    /**
     * 查询应收单
     * @param dto
     * @return
     */
    List<BillReceivableEntity> queryReceiableByCondition(DiscountAddDto dto,int start,int limit);

    /**
     * 查询应收单费用明细总金额
     * @param dto
     * @return
     */
    ReceivableBillDto queryReceiableAmountByCondition(DiscountAddDto dto);


    /**
     * 生成折扣单
     * @param dto
     * @return
     */
    void   createDiscount(DiscountAddDto dto);

    /**
     * 批量生成折扣单明细
     * @param dto
     */
    void discountDetailBathAdd(DiscountAddDto dto);
    /**
     *根据折扣单明细生成折扣单
     */
    void discountBillAddByDetail(DiscountAddDto dto);


}

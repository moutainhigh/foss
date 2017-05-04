package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountRateEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountRateDto;

import java.util.List;

/**
 * Created by 073615 on 2015/2/6.
 */
public interface IDiscountRateService  extends IService{
    /**
     * 作废折扣率
     * @param entity
     * @return
     */
    int discountRateCancel(DiscountRateEntity entity);

    /**
     * 生成折扣率
     * @return
     */
    DiscountRateEntity makeDiscountRate(DiscountRateDto dto);

    /**
     * 查询折扣单
     * @param dto
     * @return
     */
    List<DiscountRateEntity> queryDiscountRate(DiscountRateDto dto);

}

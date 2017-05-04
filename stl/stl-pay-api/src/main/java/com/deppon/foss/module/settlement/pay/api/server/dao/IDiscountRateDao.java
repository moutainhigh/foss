package com.deppon.foss.module.settlement.pay.api.server.dao;

import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountRateEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountRateDto;

import java.util.List;

/**
 * Created by 073615 on 2015/2/6.
 */
public interface IDiscountRateDao {
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

    /**
     * 插入折扣率
     * @param entity
     */
    int insertDiscountRate(DiscountRateEntity entity);

    /**
     * add by 329757 零担生成折扣率条件
     * @param rateDto
     * @return
     */
	DiscountRateEntity makeDisCountRateNoExe(DiscountRateDto rateDto);
}

package com.deppon.foss.module.settlement.pay.server.service.impl;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDiscountRateDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IDiscountRateService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountRateEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountRateDto;

import java.util.List;

/**
 * Created by 073615 on 2015/2/6.
 */
public class DiscountRateService implements IDiscountRateService {
    //蛇口单Dao
    IDiscountRateDao discountRateDao;

    /**
     * 取消折扣率
     * @param entity
     * @return
     */
    @Override
    public int discountRateCancel(DiscountRateEntity entity) {
        return discountRateDao.discountRateCancel(entity);
    }

    /**
     * 生成折扣率,并且做相应的记录
     * @param dto
     * @return
     */
    @Override
    public DiscountRateEntity makeDiscountRate(DiscountRateDto dto) {
        DiscountRateEntity discountRate = discountRateDao.makeDiscountRate(dto);
        if(discountRate == null){
            throw new SettlementException("折扣率生成失败：<br><br>客户在该期间没有收入或这该客户的折扣率区间失效！");
        }
        discountRateDao.insertDiscountRate(discountRate);
        return discountRate;
    }

    /**
     * 查询折扣单
     * @param dto
     * @return
     */
    @Override
    public List<DiscountRateEntity> queryDiscountRate(DiscountRateDto dto) {
        return discountRateDao.queryDiscountRate(dto);
    }

    public void setDiscountRateDao(IDiscountRateDao discountRateDao) {
        this.discountRateDao = discountRateDao;
    }
}

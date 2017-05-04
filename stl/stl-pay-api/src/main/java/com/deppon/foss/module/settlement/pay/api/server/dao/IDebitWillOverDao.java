package com.deppon.foss.module.settlement.pay.api.server.dao;


import com.deppon.foss.module.settlement.pay.api.shared.domain.DebitWillOverEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DebitWillOverDto;

import java.util.List;

public interface IDebitWillOverDao {
    /**
     * 根据条件查询明细
     * @param dto
     * @return
     */
    List<DebitWillOverEntity> getDebitInfoByCondition(DebitWillOverDto dto);

    /**
     * 根据条件查询总条数
     * @param dto
     * @return
     */
    long getDebitInfoCount(DebitWillOverDto dto);

    /**
     * 根据条件分页查询
     * @param start
     * @param limit
     * @param dto
     * @return
     */
    List<DebitWillOverEntity> getDebitInfoByPages(int start,int limit,DebitWillOverDto dto);

}
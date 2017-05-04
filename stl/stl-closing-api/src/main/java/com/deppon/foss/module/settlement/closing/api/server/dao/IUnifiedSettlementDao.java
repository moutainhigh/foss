package com.deppon.foss.module.settlement.closing.api.server.dao;


import com.deppon.foss.module.settlement.closing.api.shared.domain.UnifiedSettlementEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.UnifiedSettlementDto;

import java.util.List;

public interface IUnifiedSettlementDao {
    /**
     * 根据条件查询结果
     * @param dto
     * @return
     */
    List<UnifiedSettlementEntity> queryByCondition(UnifiedSettlementDto dto);

    /**
     * 根据条件分页查询
     * @param dto
     * @param start
     * @param limit
     * @return
     */
    List<UnifiedSettlementEntity> queryByCondition(UnifiedSettlementDto dto,
                                                   int start,int limit);

    /**
     * 根据条件查询总行数
     * @param dto
     * @return
     */
    int queryByConditionCount(UnifiedSettlementDto dto);

    /**
     * 根据条件汇总
     * @param dto
     * @return
     */
    UnifiedSettlementEntity queryAmountSum(UnifiedSettlementDto dto);

}
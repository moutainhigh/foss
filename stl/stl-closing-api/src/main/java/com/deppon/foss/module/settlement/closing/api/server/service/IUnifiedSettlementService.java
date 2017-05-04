package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.UnifiedSettlementEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.UnifiedSettlementDto;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 073615 on 2014/11/17.
 */
public interface IUnifiedSettlementService extends IService {
    /**
     * 查询数据分页查询
     * @return
     */
    List<UnifiedSettlementEntity> queryByCondition(UnifiedSettlementDto dto,int start,int limit);

    /**
     * 非分页查询
     * @param dto
     * @return
     */
    List<UnifiedSettlementEntity> queryBycondition(UnifiedSettlementDto dto);

    /**
     * 根据查询条件查询总行数
     * @param dto
     * @return
     */
    int queryByConditionCount(UnifiedSettlementDto dto);
    /**
     * 根据查询条件查询总行数
     * @param dto
     * @return
     */
    UnifiedSettlementEntity queryByConditionSum(UnifiedSettlementDto dto);
    /**
     * 导出Excel
     * @return
     */
    InputStream exportUnifiedSettlementExcel(UnifiedSettlementDto dto);


}

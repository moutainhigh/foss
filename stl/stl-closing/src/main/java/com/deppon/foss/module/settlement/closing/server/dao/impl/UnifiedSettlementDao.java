package com.deppon.foss.module.settlement.closing.server.dao.impl;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IUnifiedSettlementDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.UnifiedSettlementEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.UnifiedSettlementDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by 073615 on 2014/11/17.
 */
public class UnifiedSettlementDao extends iBatis3DaoImpl implements IUnifiedSettlementDao{
    private static final String NAMESPACE="closing.mvrUnifiedSettlement.";
    @Override
    public List<UnifiedSettlementEntity> queryByCondition(UnifiedSettlementDto dto) {
        List<UnifiedSettlementEntity> list = getSqlSession().selectList(NAMESPACE + "selectByConditions", dto);
        return list;
    }

    @Override
    public List<UnifiedSettlementEntity> queryByCondition(UnifiedSettlementDto dto, int start, int limit) {
        RowBounds rowbounds = new RowBounds(start,limit);
        List<UnifiedSettlementEntity> list = getSqlSession().selectList(NAMESPACE+"selectByConditions",dto,rowbounds);
        return list;
    }

    @Override
    public int queryByConditionCount(UnifiedSettlementDto dto) {
        int rowCount = (Integer)getSqlSession().selectOne(NAMESPACE+"selectConditionCount",dto);
        return rowCount;
    }

    @Override
    public UnifiedSettlementEntity queryAmountSum(UnifiedSettlementDto dto) {
        return (UnifiedSettlementEntity)getSqlSession().selectOne(NAMESPACE+"amountDetailSum",dto);
    }
}

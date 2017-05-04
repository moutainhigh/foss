package com.deppon.foss.module.settlement.pay.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDebitWillOverDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DebitWillOverEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DebitWillOverDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by 073615 on 2014/12/26.
 */
public class DebitWillOverDao extends iBatis3DaoImpl implements IDebitWillOverDao {
    private static final String NAMESPACE ="foss.stl.pay.debitWillOver.";

    /**
     * 根据条件查询结果
     * @param dto
     * @return
     */
    @Override
    public List<DebitWillOverEntity> getDebitInfoByCondition(DebitWillOverDto dto) {
        return getSqlSession().selectList(NAMESPACE+"selectByCondition",dto);
    }

    /**
     * 根据条件查询总行数
     * @param dto
     * @return
     */
    @Override
    public long getDebitInfoCount(DebitWillOverDto dto) {
        return (Long)getSqlSession().selectOne(NAMESPACE+"selectCount",dto);
    }

    /**
     * 根据条件分页查询
     * @param start
     * @param limit
     * @param dto
     * @return
     */
    @Override
    public List<DebitWillOverEntity> getDebitInfoByPages(int start, int limit, DebitWillOverDto dto) {
        RowBounds rowBounds = new RowBounds(start,limit);
        return getSqlSession().selectList(NAMESPACE+"selectByCondition",dto,rowBounds);
    }
}

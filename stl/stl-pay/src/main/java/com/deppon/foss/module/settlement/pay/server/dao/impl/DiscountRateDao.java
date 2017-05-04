package com.deppon.foss.module.settlement.pay.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDiscountRateDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountRateEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountRateDto;

import java.util.List;

/**
 * Created by 073615 on 2015/2/6.
 */
public class DiscountRateDao extends iBatis3DaoImpl implements IDiscountRateDao  {
    //命名空间
    private static final String NAMESPACE="stl.pay.discountRate.";

    @Override
    public int discountRateCancel(DiscountRateEntity entity) {
        long count= getSqlSession().insert(NAMESPACE+"cancelDiscountRate",entity);
        return (int)count;
    }

    @Override
    public DiscountRateEntity makeDiscountRate(DiscountRateDto dto) {
        return (DiscountRateEntity)getSqlSession().selectOne(NAMESPACE + "makeDiscountRate", dto);
    }

    @Override
    public List<DiscountRateEntity> queryDiscountRate(DiscountRateDto dto) {
        return getSqlSession().selectList(NAMESPACE + "selectByCondition",dto);
    }

    @Override
    public int insertDiscountRate(DiscountRateEntity entity) {
        long count= getSqlSession().insert(NAMESPACE+"insertDiscountRate",entity);
        return (int)count;
    }

    /**
     * add by 329757
     * 查询零担生成折扣率的条件
     */
	@Override
	public DiscountRateEntity makeDisCountRateNoExe(DiscountRateDto rateDto) {
		 return (DiscountRateEntity)getSqlSession().selectOne(NAMESPACE + "makeDisCountRateNoExe", rateDto);
	}
}

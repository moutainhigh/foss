package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IWaybill4FIMSDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillQueryDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by 322906 on 2016/6/29.
 */
public class Waybill4FIMSDao extends iBatis3DaoImpl implements IWaybill4FIMSDao {
    private static final String NAMESPACE = "foss.stl.WaybillDao.";// 命名空间


    @Override
    public List<WaybillDetailEntity> queryWaybillByConditions(WaybillQueryDto waybillQueryDto,int start, int limit) {
        RowBounds rb = new RowBounds(start,limit);
        return  getSqlSession().selectList(NAMESPACE + "queryWaybillByConditions", waybillQueryDto, rb);
    }

    @Override
    public int queryWaybillByConditionsCount(WaybillQueryDto waybillQueryDto) {
        return  (Integer)getSqlSession().selectOne(NAMESPACE + "queryWaybillByConditionsCount", waybillQueryDto);
    }

    @Override
    public List<WaybillDetailEntity> queryWaybill(WaybillQueryDto waybillQueryDto) {
        return  this.getSqlSession().selectList(NAMESPACE + "queryWaybill", waybillQueryDto);
    }
}

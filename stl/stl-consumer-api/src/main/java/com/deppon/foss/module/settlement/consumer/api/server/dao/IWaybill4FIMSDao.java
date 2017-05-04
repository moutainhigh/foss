package com.deppon.foss.module.settlement.consumer.api.server.dao;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillQueryDto;

import java.util.List;


/**
 * Created by 322906 on 2016/6/29.
 */
public interface IWaybill4FIMSDao {
    /**
     * 根据条件分页查询运单
     * @param waybillQueryDto
     * @param start
     * @param limit
     * @return
     */
    List<WaybillDetailEntity> queryWaybillByConditions(WaybillQueryDto waybillQueryDto,int start, int limit);

    int queryWaybillByConditionsCount(WaybillQueryDto waybillQueryDto);

    /**
     * 根据条件查询运单
     * @param waybillQueryDto
     * @return
     */
    List<WaybillDetailEntity> queryWaybill(WaybillQueryDto waybillQueryDto);
}

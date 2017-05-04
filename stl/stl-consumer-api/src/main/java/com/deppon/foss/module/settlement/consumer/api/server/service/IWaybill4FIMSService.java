package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillQueryDto;

import java.util.List;
import java.util.Map;

/**
 * Created by 322906 on 2016/6/29.
 */
public interface IWaybill4FIMSService {
    /**
     * 根据条件查询运单
     * @param waybillQueryDto
     * @param currentInfo
     * @param start
     * @param limit
     * @return
     */
    List<WaybillDetailEntity> queryWaybillByConditions(WaybillQueryDto waybillQueryDto,CurrentInfo currentInfo,int start, int limit);
    int queryWaybillByConditionsCount(WaybillQueryDto waybillQueryDto);

    /**
     * 获取纳税人信息
     * @param customerCodes
     * @return
     */
    Map<String,String> getTaxPayerInfo(List<String> customerCodes);
    void mergeWaybill(WaybillQueryDto waybillQueryDto);
}

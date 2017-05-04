package com.deppon.foss.module.settlement.dubbo.api.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.dubbo.api.define.BillDepositReceivedEntity;

/**
 * 预收单服务
 * @author foss-qiaolifeng
 * @date 2012-10-22 下午2:07:44
 */
public interface IBillDepositReceivedService4dubbo extends IService {
    /**
     * 根据汇款编号查询合伙人预收单是否存在
     * @param remitNo
     * @param active
     * @return BillDepositReceivedEntity 预收单
     */
    BillDepositReceivedEntity queryByRemitNo(String remitNo, String active);
}

package com.deppon.foss.module.settlement.common.api.server.service;


import com.deppon.foss.module.settlement.common.api.shared.dto.OnlinePayInfoDto;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by 105762 on 2014/9/24.
 */
public interface IFinanceOnlinePayWSProxy {
    /**
     * 占用
     *
     * @param onlinePayCode
     * @param amount
     */
    void obtain(String onlinePayCode, BigDecimal amount);
    /**
     * 释放
     */
    void release(String onlinePayCode, BigDecimal amount);

    /**
     * 查询
     */
    OnlinePayInfoDto query(String onlinePayCode);
    
    /**
     * 悟空补码 达到应收网上支付 更改财务自助占用部门
     * 2016-10-02 update by 231434-bieyexiong-foss
     */
    void updateObtainDept(Map<String,Object> map);
}

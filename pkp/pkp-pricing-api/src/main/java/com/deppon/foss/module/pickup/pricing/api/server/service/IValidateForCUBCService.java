package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.framework.service.IService;

import java.math.BigDecimal;

/**
 * Created by 343617 on 2016/11/3.
 * 和CUBC系统交互校验的接口
 */
public interface IValidateForCUBCService extends IService {

    Object[] canModForCUBC(String waybillNo, String method);

    Object[] isBeBebtForCUBC(String customerCode, String orgCode, String debtType, BigDecimal debtAmount);
}

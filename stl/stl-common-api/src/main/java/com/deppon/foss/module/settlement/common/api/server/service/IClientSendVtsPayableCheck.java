package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.RequestPayableCheckEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ResponsePayableCheckEntity;

/**
 * @author 218392 zhangyongxue
 * @date 2016-06-22 11:08:15
 * VTS整车项目：FOSS付款的时候，校验单号+来源单号（合同编码）
 * 1.单号是否作废
 * 2.单号+合同编码是否唯一
 * 3.合同编码是否存在
 */
public interface IClientSendVtsPayableCheck {

	public ResponsePayableCheckEntity sendVtsPayableCheck(List<RequestPayableCheckEntity> requestPayableCheckEntity);
	
}

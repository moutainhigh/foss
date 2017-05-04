package com.deppon.foss.module.settlement.common.api.server.service;

import java.io.IOException;

import com.deppon.foss.module.settlement.common.api.shared.dto.AbnormalBillApprovalDto;

/**
 * @author 347069
 * @date 2016-9-22 从QMS查询异常货处置审批是否结束
 */
public interface IAbnormalCargoExamineVtsCheck {

	public AbnormalBillApprovalDto sendVtsPayableCheck(String paycode) throws IOException;
}

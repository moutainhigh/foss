package com.deppon.foss.module.settlement.writeoff.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.dto.vtsWriteomodifyBillWriteOffDto;
/**
 * 
 * @author 331556 fanjingwei
 *
 */
public interface IModifyBillWriteoffToVTSClient {
	public void sendWriteoffWrapToVTS(
			vtsWriteomodifyBillWriteOffDto vtsWriteomodifyBillWriteOffDto);
}

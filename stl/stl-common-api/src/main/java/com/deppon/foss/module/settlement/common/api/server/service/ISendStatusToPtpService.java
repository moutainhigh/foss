package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.dto.WithholdStatusDto;

/**
 * 对接PTP系统服务
 * 
 * @author 269044
 * @date 2016-12-23
 */
public interface ISendStatusToPtpService {
	
	/**
	 * SPBP-用户需求说明书-FOSS-D到H客户网上支付的需求优化
	 * 当客户进行网上支付，FOSS到达应收单已核销， FOSS进行校验该单的到达部门是否加盟网点，如果“是“，则将运单号、单据子类型, 
	 * 到达部门，到达部门编码，通知给PTP（FOSS扣款状态不变，仍为“未扣款”）
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-12-22
     */

	public void sendStatusToPtp(List<WithholdStatusDto> withholdStatusDtoList);
	
}

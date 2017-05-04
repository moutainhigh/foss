package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;
import com.deppon.fssc.inteface.domain.payment.ExpenseDetail;


/**
 * 生成费控付款工作流接口
 * @author 045738-foss-maojianqiang
 * @date 2012-11-30 上午9:32:29
 */
public interface IPayToFSSCSendService {
	
	/**
	 * 传递数据给费控
	 * @author 045738-foss-maojianqiang
	 * @date  2013-4-16 上午11:07:26
	 */
	void payToCostcontrol(PayToCostcontrolDto dto);

	/**
	 * 进行理赔应付单拆分
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-16 上午11:07:26
	 * @param detail
	 * @param payableNo
	 * @param billType
	 * @param businessDate
	 * @param payDesc
	 */
	void sharePayableForBad(List<ExpenseDetail> detail,BillPayableEntity entity, String payDesc);
}

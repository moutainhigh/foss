package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyResultDto;

/**
 * 超时装卸费付款申请处理类，包括申请，审核功能
 * <p style="display:none">
 * modifyRecord</p>
 * <p style="display:none">
 * version:V1.0,author:105762,date:2014-3-28 下午3:55:35,content:TODO </p>
 * @author 105762
 * @date 2014-3-28 下午3:55:35
 * @since java 1.6
 * @version 1.0
 */
public interface IOverdueSFPaymentApplyService {

	/**
	 * <p>查询超时装卸费付款申请</p>
	 * @author 105762
	 * @date 2014-4-23 下午3:21:44
	 * @param dto
	 * @param dto
	 *            currentInfo
	 * @return List
	 * @see
	 */
	OverdueSFPaymentApplyResultDto query(OverdueSFPaymentApplyQueryDto dto, CurrentInfo currentInfo);

	/**
	 * <p>
	 * 提交超时装卸费付款申请
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-3-28 下午3:54:57
	 * @param dto
	 * @see
	 */
	void applyOrAudit(OverdueSFPaymentApplyQueryDto dto, CurrentInfo currentInfo);

	/**
	 * <p>
	 * 按应付单号查询付款申请对应的有效单据
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-13 下午4:47:31
	 * @param payableNo
	 * @return
	 * @see
	 */
	OverdueSFPaymentApplyDto queryOneByPayableNo(String payableNo);

}

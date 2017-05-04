package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;


/**
 * 付款单服务
 * @author 045738-foss-maojianqiang
 * @date 2012-11-27 下午7:41:44
 */
public interface IBillPaymentManageService extends IService{
	/**
	 * 申请备用金工作流服务
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:25:58
	 */
	void applyPettyCash(PayToCostcontrolDto dto,CurrentInfo currentInfo);
	
	/**
	 * 更新备用金工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午4:55:40
	 */
	void updateApplyWorkFlow(String paymentNos, String applyWorkflowNo);	
	
	/**
	 * 审核付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:25:58
	 */
	void auditPaymentOrderBill(List<String> paymentNos,String opinions,CurrentInfo currentInfo);
	
	/**
	 * 审核付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:25:58
	 */
	void reverseAuditPaymentOrder(List<String> paymentNos,String opinions,CurrentInfo currentInfo);
	
	/**
	 * 作废付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 下午5:25:58
	 */
	void invalidPaymentOrder(List<String> paymentNos,String opinions,CurrentInfo currentInfo);

	/**
	 * 作废付款单时，根据付款单反写其对应明细
	 * @author 045738-foss-maojianqiang
	 * @return 
	 * @date 2012-12-6 下午4:40:34
	 */
	List<BillPayableEntity> writeBackPaymentEntryByPaymentNo(BillPaymentEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 处理作废功能
	 * @author 045738-foss-maojianqiang
	 * @return 
	 * @date 2012-12-6 下午3:02:41
	 */
	List<BillPayableEntity> dealInvalidPayment(List<BillPaymentEntity> paymentList, String opinions,CurrentInfo currentInfo);
	
	void updatePaymentBatchNo(BillPaymentDto billPaymentDto, CurrentInfo currentInfo);

	void updatePaymentBatchNoBack(BillPaymentDto billPaymentDto, CurrentInfo currentInfo);
}

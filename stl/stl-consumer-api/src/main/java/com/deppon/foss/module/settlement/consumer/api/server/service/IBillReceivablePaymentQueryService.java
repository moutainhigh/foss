package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;

/**
 * 应收应付单查询Service接口
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 10:05:28 AM
 */
public interface IBillReceivablePaymentQueryService extends IService {
	
	/**
	 * 应收应付单查询Service接口--通过单号查询收款单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:06:28 AM
	 */
	List<BillRepaymentEntity> queryRepaymentBillByWayBillNOs(List<String> wayBillNos,
			String active);
	
	/**
	 * 应收应付单查询Service接口--通过单号查询收款单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:06:28 AM
	 */
	List<BillRepaymentEntity> queryRepaymentBillByWayBillNOsAndOrgCodes(List<String> wayBillNos, List<String> orgCodes, String active,String isRedBack);
	
	/**
	 * 应收应付单查询Service接口--通过单号查询付款单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:07:28 AM
	 */
	List<BillPaymentEntity> queryPaymentBillByWayBillNOs(List<String> wayBillNos,
			String active);
	
	/**
	 * 应收应付单查询Service接口--通过运单单号、付款部门查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:57:43 PM
	 */
	List<BillPaymentEntity> queryPaymentBillByWayBillNOsAndOrgCodes(List<String> wayBillNos, List<String> orgCodes, String active);
	
	/**
	 * 应收应付单查询Service接口-通过单号查询核销单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:08:28 AM
	 */
	List<BillWriteoffEntity> queryWriteoffBillByWayBillNOs(List<String> wayBillNos,
			String active);
	
	/**
	 * 应收应付单查询Service接口-通过运单单号、部门查询核销单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:59:43 PM
	 */
	List<BillWriteoffEntity> queryWriteoffBillByWayBillNOsAndOrgCodes(List<String> wayBillNos, List<String> orgCodes, String active);
	
	/**
	 * 应收应付单查询Service接口-通过运单单号查询应收单
	 * 
	 * @author foss-chenmingchun
	 * @date 2013-4-22 4:59:43 PM
	 */
	 List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(
				List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo);
	 
	 /**
		 * 应收应付单查询Service接口-通过运单单号查询应付单
		 * 
		 * @author foss-chenmingchun
		 * @date 2013-4-22 4:59:43 PM
		 */
	List<BillPayableEntity> queryPayableByWaybillNosAndOrgCodes(
					List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo);
		 
	 /**
	 * 应收应付单查询Service接口-通过运单单号查询现金收款单
	 * 
	 * @author foss-chenmingchun
	 * @date 2013-4-22 4:59:43 PM
	 */
	List<BillCashCollectionEntity> queryCashinBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos,String sourceBillType,List<String> orgCodes, String active,CurrentInfo currentInfo);
	 	
		
    
}

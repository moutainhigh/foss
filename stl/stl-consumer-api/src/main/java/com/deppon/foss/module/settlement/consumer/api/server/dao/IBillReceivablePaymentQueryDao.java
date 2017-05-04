package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;

/**
 * 查询应收应付单Dao接口
 * @author foss-zhangxiaohui
 * @date Oct 29, 2012 10:23:11 AM
 */
public interface IBillReceivablePaymentQueryDao {

	/**
	 * 查询应收应付单Dao接口--通过单号查询收款单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:26:11 AM
	 */
	List<BillRepaymentEntity> queryRepaymentBillByWayBillNOs(List<String> wayBillNos,
			String active);
	
	/**
	 * 查询应收应付单Dao接口--通过运单单号,收入部门查询收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:25:08 AM
	 */
	List<BillRepaymentEntity> queryRepaymentBillByWayBillNOsAndOrgCodes(List<String> wayBillNos, List<String> orgCodes, String active, String isRedBack);
	
	/**
	 * 查询应收应付单Dao接口--通过单号查询付款单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:27:28 AM
	 */
	List<BillPaymentEntity> queryPaymentBillByWayBillNOs(List<String> wayBillNos,
			String active);
	
	/**
	 * 查询应收应付单Dao接口--通过运单单号、付款部门查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:27:08 AM
	 */
	List<BillPaymentEntity> queryPaymentBillByWayBillNOsAndOrgCodes(
			List<String> wayBillNos, List<String> orgCodes, String active);
	
	/**
	 * 查询应收应付单Dao接口--通过单号查询核销单
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:28:28 AM
	 */
	List<BillWriteoffEntity> queryWriteoffBillByWayBillNOs(List<String> wayBillNos,
			String active);
	
	/**
	 * 查询应收应付单Dao接口--通过运单单号、部门查询核销单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 29, 2012 10:29:08 AM
	 */
	List<BillWriteoffEntity> queryWriteoffBillByWayBillNOsAndOrgCodes(
			List<String> wayBillNos, List<String> orgCodes, String active);
	
	
	/**
     * 按照运单号和应付部门编码集合查询应收单信息
     * 
     * @author foss-chenmingchun
     * @date 2012-12-28 下午3:14:31
     * @param waybillNos
     * @param orgCodeList
     * @param active
     * @return
     */
    List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo);
    
    
    /**
	 * 按照运单号和应付部门编码集合查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:10:35
	 * @param waybillNos
	 * @param orgCodeList
	 * @param active
	 * @return
	 */
	List<BillPayableEntity> queryPayableByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo);

	  /**
     * 根据来源单号集合和部门编码集合，查询现金收款单信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2013-1-22 上午9:15:06
     * @param sourceBillNos
     * @param sourceBillType
     * @param orgCodes
     * @param active
     * @param currentInfo
     * @return
     */
    List<BillCashCollectionEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos,String sourceBillType,List<String> orgCodes, String active,CurrentInfo currentInfo);
}

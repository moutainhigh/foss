package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentResultDto;

/**
 *  还款单管理
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-30 下午5:09:06
 */
public interface IBillRepaymentManageService extends IService {

	/**
	 * 
	 * 查询还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午2:59:38
	 */
	BillRepaymentManageResultDto queryBillRepayment(
			BillRepaymentManageDto billRepaymentManageDto,int start,int limit,CurrentInfo currentInfo);
	
	/**
	 * 查询还款单核销明细
	 * @author foss-qiaolifeng
	 * @date 2012-11-13 上午10:12:07
	 */
	List<BillWriteoffEntity> queryBillWriteoffEntityDetail(String repaymentNo);
	
	

	/**
	 * 审核还款单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午3:00:51
	 */
	BillRepaymentManageResultDto auditBillRepayment(
			BillRepaymentManageDto billRepaymentManageDto,CurrentInfo cInfo);
	
	/**
	 * 反审核还款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 下午4:05:09
	 */
	BillRepaymentManageResultDto auditBackBillRepayment(
			BillRepaymentManageDto billRepaymentManageDto,CurrentInfo cInfo);

	/**
	 * 作废还款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-9 下午4:05:21
	 */
	BillRepaymentManageResultDto disableBillRepayment(
			BillRepaymentManageDto billRepaymentManageDto,CurrentInfo cInfo);
	
	
	/**
	 * 还款/批量还款无事务公用
	 * @author foss-qiaolifeng
	 * @date 2012-11-26 下午4:42:30
	 */
	void repayment(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,CurrentInfo cInfo);
	
	/**
	 * 还款/批量还款含事务
	 * @author foss-qiaolifeng
	 * @date 2012-11-30 下午4:37:13
	 */
	void repaymentForStatement(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,CurrentInfo cInfo);
	
	/**
	 * 根据汇款单号查询汇款信息
	 * @author foss-qiaolifeng
	 * @date 2012-11-30 下午4:37:13
	 */
	BillStatementToPaymentResultDto queryRemittanceDetail(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,CurrentInfo cInfo);
	
	
	/**
	 * 
	 *导出还款单查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午2:59:38
	 */
	HSSFWorkbook queryExportBillRepayment(
			BillRepaymentManageDto billRepayDto,CurrentInfo cInfo);
	
}

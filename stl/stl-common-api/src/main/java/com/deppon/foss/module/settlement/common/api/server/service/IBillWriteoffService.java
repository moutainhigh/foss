package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWritebackOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;

/**
 * 核销服务
 * 
 * @author foss-wangxuemin
 * @date 2012-10-18 上午10:23:40
 */
public interface IBillWriteoffService extends IService {
	
	/**
	 * 新增核销单
	 * 
	 * @author foss-jiangxun
	 * @date 2016-06-02 11:04:00 AM
	 */
	public void addWriteoff(BillWriteoffEntity entity);


	/**
	 * 根据ID红冲核销单/反核销
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-22 上午10:28:42
	 */
	BillWritebackOperationDto writeBackBillWriteoffById(String id,
			CurrentInfo currentInfo);

	/**
	 * 根据实体红冲核销单/反核销
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-23 下午2:49:56
	 */
	BillWritebackOperationDto writeBackBillWriteoffByEntity(
			BillWriteoffEntity billWriteoffEntity, CurrentInfo currentInfo);

	/**
	 * 根据应收单号和应付单号反核销/红冲(偏线外发反审核)
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 27, 2012 4:14:43 PM
	 */
	public void writeBackByRecNoAndPayNo(String receivableNo, String payableNo,
			CurrentInfo currentInfo);

	/**
	 * 根据还款单号红冲/反核销
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-22 下午2:02:22
	 */
	void writeBackBillWriteoffByRepayment(String repaymentNo,
			CurrentInfo currentInfo);

	/**
	 * 根据付款单号红冲/反核销
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 9, 2012 9:48:11 AM
	 */
	void writeBackBillWriteoffByPayment(String paymentNo,
			CurrentInfo currentInfo);

	/**
	 * 根据开始目的来源编号查询核销记录
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-24 下午2:45:59
	 */
	List<BillWriteoffEntity> queryBillWriteoffByBeginOrEndNo(
			String beginOrEndNo, String active, String createType);
	
	/**
	 * 
	 * 根据运单号查询是否存在手工核销的应收单
	 * @Title: queryHandWriteoffReceivableByWaybillNo 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-12 上午11:34:36
	 * @param @param waybillNo
	 * @param @return    设定文件 
	 * @return List<BillWriteoffEntity>    返回类型 
	 * @throws
	 */
	List<BillWriteoffEntity> queryHandWriteoffReceivableByWaybillNo(String waybillNo);

	/**
	 * 根据还款单号查询核销记录
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 12, 2012 3:29:53 PM
	 */
	List<BillWriteoffEntity> queryByRepayment(String repaymentNo, String active);

	/**
	 * 根据对账单好查询核销单记录
	 * 
	 * @author foss-wangxuemin
	 * @date Mar 20, 2013 3:57:09 PM
	 */
	List<BillWriteoffEntity> queryByStatementBillNo(String statementBillNo,
			String beginNo, String endNo, String writeoffType, String active);

	/**
	 * 预收冲应收
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-18 上午10:28:32
	 * @param billWriteoffOperationDto
	 *            核销操作dto
	 */
	BillWriteoffOperationDto writeoffDepositAndReceivable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo);

	/**
	 * 应收冲应付
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-18 上午10:46:53
	 * @param billWriteoffOperationDto
	 *            核销操作dto
	 */
	BillWriteoffOperationDto writeoffReceibableAndPayable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo);

	/**
	 * 预付冲应付
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-18 上午10:50:06
	 * @param billWriteoffOperationDto
	 *            核销操作dto
	 */
	BillWriteoffOperationDto writeoffAdvancedAndPayable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo);

	/**
	 * 还款冲应收
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-19 下午4:19:12
	 */
	BillWriteoffOperationDto writeoffRepaymentAndReceibable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo);

	/**
	 * 付款冲应付（代收货款）
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 1, 2012 11:46:15 AM
	 */
	void writeoffCODPaymentAndPayable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo);

	/**
	 * 付款冲应收（客户付款）
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 2:00:26 PM
	 */
	void writeoffCustPaymentAndPayable(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo);

	/**
	 * 付款冲应收
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 6, 2012 11:22:57 AM
	 */
	void writeoffCustPaymentAndDeposit(
			BillWriteoffOperationDto billWriteoffOperationDto,
			CurrentInfo currentInfo);

	/**
	 * 坏账核销
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:21:52 PM
	 */
	void writeoffBadAccountAndReceivable(
			BillWriteoffOperationDto billWriteoffOperationDto, String billType,
			CurrentInfo currentInfo);
}

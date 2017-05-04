package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentResultDto;


/**
 * 付款单查询接口
 * @author foss-qiaolifeng
 * @date 2012-11-19 下午12:37:02
 */
public interface IPaymentQueryService extends IService {

	
	/**
	 * 分页查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	BillPaymentResultDto queryPaymentBill(BillPaymentQueryDto dto,int start,int limit,CurrentInfo cInfo);	
	/**
	 * 不分页查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-12-6 上午10:37:05
	 */
	BillPaymentResultDto queryPaymentBillNotPage(BillPaymentQueryDto dto,CurrentInfo cInfo);	
	
	/**
	 * 根据付款单号查询付款单信息及其关联应付单信息
	 * @author foss-qiaolifeng
	 * @date 2012-12-4 下午2:34:44
	 */
	BillPaymentResultDto queryPaymentBillDetial(BillPaymentQueryDto dto, int start, int limit,CurrentInfo cInfo);
	
	/**
	 * 申请备用金
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:04:12
	 */
	BillPaymentResultDto applySpareMoney(BillPaymentQueryDto dto,CurrentInfo cInfo);
	
	/**
	 * 根据来源单号查付款单
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:04:12
	 */
	List<BillPaymentEntity> queryBillPaymentListBySourceBillNo(BillPaymentQueryDto dto);
	
	/**
	 * 保存申请备用金
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:04:12
	 */
	void saveApplySpareMoney(BillPaymentQueryDto dto, CurrentInfo cInfo);
	
	/**
	 * 审核付款单
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:04:31
	 */
	void aduitPayment(BillPaymentQueryDto dto, CurrentInfo cInfo);
	
	/**
	 * 反审核付款单
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:04:38
	 */
	void revAduitPayment(BillPaymentQueryDto dto, CurrentInfo cInfo);
	
	/**
	 * 作废付款单
	 * @author foss-qiaolifeng
	 * @date 2012-12-3 下午4:04:50
	 */
	void disabledPayment(BillPaymentQueryDto dto, CurrentInfo cInfo);
	
}

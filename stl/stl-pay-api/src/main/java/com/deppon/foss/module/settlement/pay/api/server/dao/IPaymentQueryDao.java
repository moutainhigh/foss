package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentResultDto;


/**
 * 付款单查询接口
 * @author foss-qiaolifeng
 * @date 2012-11-19 上午11:16:40
 */
public interface IPaymentQueryDao {
	
	/**
	 * 按输入参数查询分页付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	List<BillPaymentEntity> queryPaymentByPageAndParams(BillPaymentQueryDto dto,int start,int limit);
	
	/**
	 * 按输入参数查询付款单总条数、总金额
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	BillPaymentResultDto queryPaymentTotalByParams(BillPaymentQueryDto dto);
	
	/**
	 * 按付款单号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	List<BillPaymentEntity> queryPaymentByPaymentNos(BillPaymentQueryDto dto);
	
	/**
	 * 按运单号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	List<BillPaymentEntity> queryPaymentByWaybillNos(BillPaymentQueryDto dto);
	
	
	/**
	 * 按输入参数查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	List<BillPaymentEntity> queryPaymentByParams(BillPaymentQueryDto dto);
	
	/**
	 * 按来源 单号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	List<BillPaymentEntity> queryPaymentBysourceBillNos(BillPaymentQueryDto dto);
	
	/** 
	 *按来源 单号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2013-3-11 上午10:56:24
	 */
	List<BillPaymentEntity> queryPaymentBysourceNoAsPayable(BillPaymentQueryDto dto);
	
	/**
	 * 按工作流号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2013-6-17 下午6:28:05
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	List<BillPaymentEntity> queryPaymentByWorkFlowNos(BillPaymentQueryDto dto);

	/**
	 * 按输入参数查询分页付款单明细
	 * 
	 * @author foss-guxinhua
	 * @date 2014-06-24 上午11:15:58
	 */
	List<BillPaymentAddDto> queryPaymentDetialByPageAndParams(BillPaymentQueryDto dto,
			BillPayableManageDto queryPayableDto,BillDepositReceivedPayDto queryDepositReceivedDto, int start, int limit);
	
	/**
	 * 按输入参数查询分页付款单明细总条数
	 * 
	 * @author foss-guxinhua
	 * @date 2014-06-24 上午11:15:58
	 */
	long queryPaymentDetialByPageAndParamsSize(BillPaymentQueryDto dto,
			BillPayableManageDto queryPayableDto,BillDepositReceivedPayDto queryDepositReceivedDto);
}

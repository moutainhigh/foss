package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayFailedQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayableQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODStartApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODWaybillNoQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODMergeDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODQueryByWaybillDto;

/**
 * 代收货款DAO
 * 
 * @author dp-zengbinwen
 * @date 2012-10-13 下午2:55:43
 */
public interface ICODEntityDao {

	/**
	 * 新增代收货款实体
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-13 下午2:56:33
	 */
	int addCod(CODEntity entity);

	/**
	 * 更新银行帐号信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午3:27:47
	 */
	int updateBankAccounts(CODEntity entity);

	/**
	 * 更新代收货款冻结状态
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午3:59:45
	 */
	int updateFreezeState(CODEntity entity);

	/**
	 * 更新付款状态
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午4:01:40
	 */
	int updatePaymentState(CODEntity entity);

	/**
	 * 按运单查询代收货款信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 下午2:50:28
	 */
	CODEntity queryByWaybill(CODQueryByWaybillDto dto);
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-07-08 09:21:00
	 * 查询已签收但长期未支付代收货款-短期冻结
	 */
	List<BillPayableEntity> queryShortNonRefundCodLock(String billType);
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-07-08 09:21:00
	 * 查询已签收但长期未支付代收货款-短期冻结
	 */
	List<BillPayableEntity> queryLongNonRefundCodLock(String billType);

	/**
	 * @author 218392 ZHANGYONGXUE 2015-12-25 16:12:50
	 * 代收货款支付界面：按单号查询总数
	 */
	CODDto queryByWaybillNoListSum(BillCODWaybillNoQueryDto queryDto);
	
	
	/**
	 * 根据运单号查询代收货款总条件总金额，冻结总条件总金额
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 下午3:41:07
	 * @param queryDto
	 * @return
	 */
	CODDto queryByWaybillNoSum(BillCODWaybillNoQueryDto queryDto);
	
	/**
	 * @author 218392 张永雪 2015-12-25 15:25:26
	 * 代收货款支付界面：按单号查询
	 */
	List<CODDto> queryCodListByWaybillNo(BillCODWaybillNoQueryDto queryDto);
	
	/**
	 * 
	 * 根据运单号查询代收货款信息，代收货款状态、应付部门可选
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午8:43:02
	 */
	List<CODDto> queryByWaybillNo(BillCODWaybillNoQueryDto queryDto);

	/**
	 * 
	 * 出发申请查询
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:19:04
	 */
	List<CODDto> queryStartApplyBillCOD(BillCODStartApplyQueryDto queryDto);

	/**
	 * 
	 * 出发申请查询大小
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:20:35
	 */
	int queryStartApplyBillCODSize(BillCODStartApplyQueryDto queryDto);

	/**
	 * 查询代收货款及应付单
	 * @author foss-guxinhua
	 * @date 2013-5-8 上午11:05:11
	 * @param queryDto
	 * @return
	 */
	List<CODPayableDto> queryAllCODAndBillPayable(BillCODPayableQueryDto queryDto);
	
	/**
	 * 查询可退款的并可合并付款的代收货款及应付单明细
	 * @author foss-guxinhua
	 * @date 2014-10-14 上午11:05:11
	 * @param queryDto
	 * @return
	 */
	List<CODMergeDto> queryAllMergeCODAndBillPayable(BillCODPayableQueryDto queryDto);
	
	/**
	 * 按合并编号进行查询
	 * 
	 * @date 2014-11-16 上午10:04:12
	 */
	List<CODEntity> queryListByMergeCode(CODEntity cod); 
	
	/**
	 * 
	 * 代收货款付款失败查询
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:56:17
	 */
	List<CODDto> queryBillCODPayFailed(BillCODPayFailedQueryDto queryDto);

	/**
	 * 
	 * 代收货款付款失败查询大小,合计金额
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:57:26
	 */
	CODDto queryBillCODPayFailedSize(BillCODPayFailedQueryDto queryDto);

	/**
	 * 
	 * 代收货款汇款查询,合计总条数总金额，冻结总条数总金额
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午11:08:18
	 */
	CODDto queryBillCODPayableSum(BillCODPayableQueryDto queryDto); 
	
	/**
	 * 
	 * 代收货款汇款查询
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午11:08:18
	 */
	List<CODDto> queryBillCODPayable(BillCODPayableQueryDto queryDto);

	/**
	 * 
	 * 查询代收货款汇款确认
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:41:12
	 */
	List<CODDto> queryPayConfirm(BillCODPayConfirmDto dto, int start, int offset);

	/**
	 * 
	 * 查询代收货款汇款确认大小,合计金额
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:41:40
	 */
	CODDto queryPayConfirmSize(BillCODPayConfirmDto dto);

	/**
	 * 
	 * 更新代收货款作废状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午10:59:37
	 */
	int updateCODCancelStatus(CODEntity entity);

	/**
	 * 
	 * 更新代收货款营业部冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:00:57
	 */
	int updateOrgFreezeStatus(CODEntity entity);

	/**
	 * 
	 * 更新代收货款营业部审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:01:45
	 */
	int updateOrgAuditStatus(CODEntity entity);

	/**
	 * 
	 * 更新代收货款合并编号状态
	 * 
	 * @author 163576
	 * @date 2014-11-16 上午11:03:19
	 */
	int updateCodMergeCode(CODMergeDto mergeDto); 
	
	/**
	 * 
	 * 更新代收货款营业部经理审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:02:27
	 */
	int updateManagerAuditStatus(CODEntity entity);

	/**
	 * 
	 * 更新代收货款资金部冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:03:19
	 */
	int updateTusyorgFreezeStatus(CODEntity entity);
	
	/**
	 * 批量更新代收货款资金部冻结状态
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午3:46:13
	 * @param cod
	 * @return
	 */
	int updateTusyorgFreezeStatusByBatch(CODDto cod);
	

	/**
	 * 
	 * 更新代收货款资金部反冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:43:49
	 */
	int updateTusyorgClfreezeStatus(CODEntity entity);
	
	/**
	 * 批量更新代收货款资金部反冻结状态
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午3:46:13
	 * @param cod
	 * @return
	 */
	int updateTusyorgClfreezeStatusByBatch(CODDto cod);

	/**
	 * 
	 * 更新代收货款付款失败申请审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:44:55
	 */
	int updatePaymentFailedAuditStatus(CODEntity entity);

	/**
	 * 
	 * 更新代收货款线下汇款导出状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:48:30
	 */
	int updatePayCODOfflineStatus(CODEntity entity);
	
	/**
	 * 批量更新代收货款线下汇款导出状态
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午3:46:13
	 * @param cod
	 * @return
	 */
	int updatePayCODOfflineStatusByBatch(CODDto cod);

	/**
	 * 
	 * 更新代收货款线上汇款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:49:25
	 */
	int updatePayCODOnlineStatus(CODEntity entity);
	
	/**
	 * 批量更新代收货款汇款成功状态
	 * 
	 * @author foss-guxinhua
	 * @date 2014-9-27 下午3:46:13
	 * @param cod
	 * @return
	 */
	int updatePayCODSuccessByBatch(CODDto cod);
	
	/**
	 * 批量更新代收货款线上汇款状态
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午3:46:13
	 * @param cod
	 * @return
	 */
	int updatePayCODOnlineStatusByBatch(CODDto cod);

	/**
	 * 
	 * 更新代收货款汇款成功状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:48:54
	 */
	int updatePayCODSuccess(CODEntity entity);

	/**
	 * 
	 * 更新代收货款汇款失败状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:49:39
	 */
	int updatePayCODFailure(CODEntity entity);

	/**
	 * 
	 * 更新代收货款反汇款成功状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:51:08
	 */
	int updatePayCODAntiRemittance(CODEntity entity);

	/**
	 * 更新空运代收货款记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午5:55:48
	 */
	int updateAirPaidCodStatus(CODEntity entity, String newAirStatus,
			String oldAirStatus);
	
	/**
	 * 快递代理代收货款记录状态
	 * ISSUE-3389 小件业务
	 * @author guxinhua
	 * @date 2012-11-7 下午5:39:03
	 */
	int updateLandPaidCodStatus(CODEntity codEntity, String newLandStatus,
			String oldLandStatus);

	/**
	 * 
	 * 根据主键查询代收货款实体
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-13 下午2:56:53
	 */
	CODEntity queryById(String id);

	/**
	 * 
	 * 根据主键集合查询代收货款实体
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-13 下午2:56:53
	 */
	List<CODEntity> queryByIds(List<String> ids);
	
	/**
	 * 
	 * 根据主键集合查询可合并代收货款实体
	 * 
	 * @date 2014-10-13 下午2:56:53
	 */
	List<CODMergeDto> queryMergeCodByIds(List<String> ids);

	/**
	 * 
	 * 根据批次号查询代收货款
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-23 上午9:50:01
	 */
	List<CODEntity> queryByBatchNumber(String batchNumber);

	/**
	 * 
	 * 更新银企整批通回代收货款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-26 上午9:53:30
	 */
	int updateCODBankBatchBackStatus(CODEntity entity);

	/**
	 * 
	 * 按运单号集合查询（官网代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午1:58:19
	 */
	List<CODDto> queryCODByWaybillNums(BillCODWaybillNoQueryDto dto,
			int offset, int limit);
	
	/**
	 * 
	 * 按运单号集合查询（CC代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午1:58:19
	 */
	List<CODDto> queryCODByWaybillNumsForCC(BillCODWaybillNoQueryDto dto,
			int offset, int limit);

	/**
	 * 
	 * 按运单号集合查询大小（官网代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午1:58:22
	 */
	int queryCODByWaybillNumsSize(BillCODWaybillNoQueryDto dto);

	/**
	 * 根据应付单条件及代收货款批次号查询应付单集合
	 * @author foss-guxinhua
	 * @date 2013-5-29 上午10:06:14
	 * @param dto
	 * @return
	 */
	List<BillPayableEntity> queryBillPayableByCodBatchNumber(CODPayableDto dto);
}

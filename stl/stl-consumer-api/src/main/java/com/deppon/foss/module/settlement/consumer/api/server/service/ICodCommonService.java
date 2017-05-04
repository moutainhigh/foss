package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayFailedQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayableQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODStartApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODWaybillNoQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODMergeDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;

/**
 * 代收货款记录管理服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-25 下午4:43:02
 */
public interface ICodCommonService {

	/**
	 * 
	 * 创建代收货款记录信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午5:54:52
	 */
	void createCOD(CODEntity item, CurrentInfo currentInfo);

	/**
	 * 
	 * 根据ID查询代收货款
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-9 上午9:11:44
	 */
	CODEntity queryById(String entityId);

	/**
	 * 按Id集合进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-15 下午4:22:33
	 */
	List<CODEntity> queryByIds(List<String> ids);
	
	/**
	 * 按Id集合批量进行查询可合并代收货款.
	 *
	 * @param ids the ids
	 * @return the list
	 * @date 2014-11-15 11:23:48
	 */
	List<CODMergeDto> queryMergeCodByIds(List<String> ids);
	
	/**
	 * 按合并编号进行查询代收货款.
	 *
	 * @param 
	 * @return the list
	 * @date 2014-11-15 下午4:23:48
	 */
	List<CODEntity> queryCODEntitiListByMergeCode(String mergeCode);

	/**
	 * 更新银行帐号信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @return
	 * @date 2012-10-25 下午5:47:37
	 */
	void updateBankAccounts(CODEntity item, CurrentInfo currentInfo);

	/**
	 * 按运单查询代收货款信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 下午2:50:28
	 */
	CODEntity queryByWaybill(String waybill);

	/**
	 * 检查运单对应的代收货款是否已付款（冻结之后）
	 * 
	 * @author guxinhua
	 * @date 2013-3-11 下午6:10:54
	 * @param waybillNo
	 * @param signType
	 *            String . 签收类型: 专线正常签收("LS")、偏线正常签收("PLS")、空运正常签收("AS")
	 * @return true:检查通过;false:检查不通过
	 */
	boolean checkCodHasPaymentByWaybillNo(String waybillNo, String signType);

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
	 * 查询可支付的代收货款及应付单
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-8 上午11:04:24
	 * @param queryDto
	 * @return
	 */
	List<CODPayableDto> queryAllCODAndBillPayable(
			BillCODPayableQueryDto queryDto);
	
	/**
	 * 查询可退款的代收货款及应付单，并可合并付款的代收明细
	 * @author 163576
	 * @date 2014-10-13 11:04:24
	 * @param queryDto
	 * @return
	 */
	List<CODMergeDto> queryAllMergeCODAndBillPayable(BillCODPayableQueryDto queryDto);
	

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
	 * 代收货款付款失败查询大小，合计金额
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:57:26
	 */
	CODDto queryBillCODPayFailedSize(BillCODPayFailedQueryDto queryDto);

	/**
	 * 代收货款汇款查询,合计总条数总金额，冻结总条数总金额
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 下午3:51:20
	 * @param queryDto
	 * @return
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
	 * 查询代收货款汇款确认大小,总金额
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
	void updateCODCancelStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款营业部冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:00:57
	 */
	void updateOrgFreezeStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款营业部审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:01:45
	 */
	void updateOrgAuditStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款营业部经理审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:02:27
	 */
	void updateManagerAuditStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 更新代收货款合并编号状态
	 * 
	 * @author foss-guxinhua
	 * @date 2014-11-16 下午3:32:22
	 * @param 
	 * @param currentInfo
	 */
	void updateCodMergeCode(CODMergeDto mergeDto);
	
	/**
	 * 更新营业部经理退回状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 163576-foss-guxinhua
	 * @date 2013-10-19 上午10:46:44
	 */
	public void updateManagerBackStatus(CODEntity entity,CurrentInfo currentInfo);
	
	/**
	 * 
	 * 更新代收货款资金部冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:03:19
	 */
	void updateTusyorgFreezeStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 批量更新代收货款资金部冻结状态
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午3:31:49
	 * @param entity
	 * @param currentInfo
	 */
	void updateTusyorgFreezeStatusByBatch(CODDto dto, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款资金部反冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:43:49
	 */
	void updateTusyorgClfreezeStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款付款失败申请审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:44:55
	 */
	void updatePaymentFailedAuditStatus(CODEntity entity,
			CurrentInfo currentInfo);
	
	/**
	 * 更新汇款失败审核退回状态
	 * @author foss-guxinhua
	 * @date 2013-5-20 上午10:58:22
	 * @param entity
	 * @param currentInfo
	 */
	void updatePaymentFailedReturnedStatus(CODEntity entity,
			CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款线下汇款导出状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:48:30
	 */
	void updatePayCODOfflineStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款线上汇款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:49:25
	 */
	void updatePayCODOnlineStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款汇款成功状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:48:54
	 */
	void updatePayCODSuccess(CODEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 批量更新汇款成功状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 163576
	 * @date 2014-09-27 下午6:48:42
	 */
	void updatePayCODSuccessByBatch(CODDto dto, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款汇款失败状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:49:39
	 */
	void updatePayCODFailure(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * 更新代收货款反汇款成功状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:51:08
	 */
	void updatePayCODAntiRemittance(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 更新空运代收货款记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午5:55:48
	 */
	void updateAirPaidCodStatus(CODEntity entity, String newAirStatus,
			String oldAirStatus, CurrentInfo currentInfo);
	
	/**
	 * 更新快递代理代收货款记录
	 * ISSUE-3389 小件业务
	 * @author foss-guxinhua
	 * @date 2013-07-18 下午5:55:48
	 */
	void updateLandPaidCodStatus(CODEntity entity, String newLandStatus,
			String oldLandStatus, CurrentInfo currentInfo);

	/**
	 * 
	 * 获取代收货款对应的应付单
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-20 下午5:13:25
	 */
	BillPayableEntity getBillPayableEntity(CODEntity entity);
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-07-08 09:21:00
	 * 查询已签收但长期未支付代收货款-短期冻结
	 */
	List<BillPayableEntity> queryShortNonRefundCod(String billType);
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-07-08 09:21:00
	 * 查询已签收但长期未支付代收货款-长期冻结
	 */
	List<BillPayableEntity> queryLongNonRefundCod(String billType);
	
	/**
	 * 按运单号、代收货款状态、应付部门查询代收货款【运单号不能为空 代收货款状态、应付部门可为空】
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:33:19
	 */
	List<CODDto> queryBillCODByWaybillNo(List<String> waybillNos,
			List<String> statuses, CurrentInfo currentInfo);

	/**
	 * 按运单号、代收货款状态、应付部门查询线下导出代收货款【运单号不能为空 代收货款状态】
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:33:19
	 */
	List<CODDto> queryBillOfflineCODByWaybillNo(List<String> waybillNos,
			List<String> statuses);

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
	void updateCODBankBatchBackStatus(CODEntity entity, CurrentInfo currentInfo);

	/**
	 * 
	 * 按运单号集合查询（官网代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午2:04:11
	 */
	List<CODDto> queryCODByWaybillNums(List<String> waybillNos, int offset,
			int limit);
	
	/**
	 * 
	 * 按运单号集合查询（CC代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午2:04:11
	 */
	List<CODDto> queryCODByWaybillNumsForCC(List<String> waybillNos, int offset,
			int limit);

	/**
	 * 
	 * 按运单号集合查询大小（官网代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午2:04:23
	 */
	int queryCODByWaybillNumsSize(List<String> waybillNos);

	/**
	 * 按运单号集合合计总条数总金额，冻结总条数总金额查询
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 下午3:38:51
	 * @return
	 */
	CODDto queryBillCODPayableByWaybillNosSum(List<String> waybillNos);

	/**
	 * 
	 * 按运单号查询可支付的代收货款
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-21 上午11:29:41
	 */
	List<CODDto> queryBillCODPayableByWaybillNos(List<String> waybillNos,String sortProperty,String sortDirection)
			throws SettlementException;

	/**
	 * 判断集合中codEntity属于三日退（审核退）类型:R3,RA，还是即日退:R1 。返回 R3,RA 或 R1
	 * 
	 * @author ibm-guxinhua
	 * @date 2012-11-30 上午10:05:12
	 */
	public String codEntityListInstanceofType(List<?> codEntityList);

	/**
	 * 批量更新资金部取消冻结状态.
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-8 下午4:44:38
	 * @param dto
	 * @param currentInfo
	 */
	void updateTusyorgClfreezeStatusByBatch(CODDto dto, CurrentInfo currentInfo);

	/**
	 * 批量更新线下汇款状态.
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-8 下午4:41:56
	 * @param dto
	 * @param currentInfo
	 */
	void updatePayCODOfflineStatusByBatch(CODDto dto, CurrentInfo currentInfo);

	/**
	 * 批量更新线上汇款状态.
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-8 下午4:37:59
	 * @param dto
	 * @param currentInfo
	 */
	void updatePayCODOnlineStatusByBatch(CODDto dto, CurrentInfo currentInfo);

	/**
	 * 根据代收货款批次号查询应付单集合
	 * @author foss-guxinhua
	 * @date 2013-5-29 上午9:53:26
	 * @param batchNumber
	 * @return
	 */
	List<BillPayableEntity> queryBillPayableByCodBatchNumber(String batchNumber);
}

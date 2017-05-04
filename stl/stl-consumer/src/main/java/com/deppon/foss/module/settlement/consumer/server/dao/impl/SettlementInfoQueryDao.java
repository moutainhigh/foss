package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ISettlementInfoQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.DestFeeDetailInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.DestFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrgSettlementInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrigFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillSettlementInfoDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询运单的到付金额、代收货款、装卸费、发票信息DAO
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-21 上午8:49:36
 * @since
 * @version
 */
public class SettlementInfoQueryDao extends iBatis3DaoImpl implements ISettlementInfoQueryDao {

	private static final String NAMESPACE = "foss.stl.SettlementInfoQueryDao.";

	/**
	 * 根据运单号和开单日期查询运单的代收货款信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:49:53
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillSettlementInfoDto> queryCodFeeByWaybillNO(String waybillNo, Date startDate,
			Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("active", FossConstants.ACTIVE);
		map.put("isRedBack", SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		map.put("receivableBillTypes", new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD });// 代收货款应收单

		map.put("payableBillType",
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);// 代收货款应付单
		return this.getSqlSession().selectList(NAMESPACE + "queryCodFeeByWaybillNo", map);
	}

	/**
	 * 根据运单号和开单日期查询运单的装卸费信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:50:11
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillSettlementInfoDto queryServiceFeeByWaybillNO(String waybillNo, Date startDate,
			Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("active", FossConstants.ACTIVE);
		map.put("isRedBack", SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		map.put("payableBillType",
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);// 装卸费应付单
		List<WaybillSettlementInfoDto> list = this.getSqlSession().selectList(
				NAMESPACE + "queryServiceFeeByWaybillNo", map);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);// 按照实际业务只能有一条数据
		}
		return null;
	}

	/**
	 * 根据运单号和开单日期查询运单的发票信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:50:31
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillSettlementInfoDto> queryInvoiceByWaybillNO(String waybillNo, Date startDate,
			Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("active", FossConstants.ACTIVE);
		map.put("sourceBillType", SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL);
		return this.getSqlSession().selectList(NAMESPACE + "queryInvoiceByWaybillNo", map);
	}

	/**
	 * 根据部门编码和部门创建日期查找该部门的应收、应付、预收、预付未核销的金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-26 下午9:02:08
	 * @param orgCode
	 * @param orgCreateTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgSettlementInfoDto> queryOrgSettlementInfo(String orgCode, Date orgCreateTime) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("orgCode", orgCode);
		map.put("active", FossConstants.ACTIVE);
		map.put("isRedBack", SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		map.put("orgCreateTime", orgCreateTime);
		map.put("receivableBill", SettlementConstants.SETTLEMENT_BILL_TYPE_RECEIVABLE);// 应收单
		map.put("payableBill", SettlementConstants.SETTLEMENT_BILL_TYPE_PAYABLE);// 应付单
		map.put("depositBill", SettlementConstants.SETTLEMENT_BILL_TYPE_DEPOSIT_RECEIVED);// 预收单
		map.put("adpaymentBill", SettlementConstants.SETTLEMENT_BILL_TYPE_ADVANCED_PAYMENT);// 预付单
		return this.getSqlSession().selectList(NAMESPACE + "selectOrgSettlementInfo", map);
	}

	/**
	 * 查询始发运费现金收款单
	 * 
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrigFeeInfo queryOrigFee(String waybillNo) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("waybillNo", waybillNo);
		params.put("active", FossConstants.ACTIVE);
		params.put("billType",
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		params.put("sourceBillType", SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL);
		List<OrigFeeInfo> result = getSqlSession().selectList(NAMESPACE + "queryOrigFee", params);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}

		return null;

	}

	/**
	 * 查询到付费用
	 * 
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DestFeeInfo queryDestFee(String waybillNo) {

		DestFeeInfo info = null;

		// 构造参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waybillNo", waybillNo);
		params.put("createType", SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		params.put("active", FossConstants.ACTIVE);

		// 查询还款单
		List<DestFeeDetailInfo> queryList = this.getSqlSession().selectList(
				NAMESPACE + "queryDestFee-Repayment", params);

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal transportAmount = BigDecimal.ZERO;
		List<String> paymentTypes = new ArrayList<String>();
		Date paymentDate = null;

		if (CollectionUtils.isNotEmpty(queryList)) {
			info = new DestFeeInfo();
				// 到付运费类型
			List<String> transTypeList = Arrays
					.asList(new String[] {
							SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE });
			for (DestFeeDetailInfo detail : queryList) {

				// 查询出还款金额、来源类型、付款方式、会计日期
				BigDecimal amount = detail.getAmount();
				String sourceBillType = detail.getSourceBillType();
				String paymentType = detail.getPaymentType();
				Date accountDate = detail.getAccountDate();

				if (amount != null && StringUtils.isNotEmpty(sourceBillType)
						&& StringUtils.isNotEmpty(paymentType) && accountDate != null) {

					// 总金额
					totalAmount = totalAmount.add(amount);

					// 运费金额
					if (transTypeList.contains(sourceBillType)) {
						transportAmount = transportAmount.add(amount);
					}

					// 付款方式
					if (!paymentTypes.contains(paymentType)) {
						paymentTypes.add(paymentType);
					}

					// 付款日期
					if (paymentDate == null) {
						paymentDate = accountDate;
					} else if (accountDate.after(paymentDate)) {
						paymentDate = accountDate;
					}
				}

				info.setTotalAmount(totalAmount);
				info.setTransportAmount(transportAmount);
				if (CollectionUtils.isNotEmpty(paymentTypes)) {
					info.setPaymentTypes(paymentTypes.toArray(new String[paymentTypes.size()]));
				}
				info.setPaymentDate(paymentDate);
			}

		} else {

			// 构造应收单的查询条件
			params.put("paymentTypes", Arrays.asList(
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT));
			params.put(
					"billType",
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);

			// 查询临欠月结应收单
			queryList = this.getSqlSession().selectList(NAMESPACE + "queryDestFee-Receivable",
					params);

			if (CollectionUtils.isNotEmpty(queryList)) {
				info = new DestFeeInfo();

				for (DestFeeDetailInfo detail : queryList) {

					BigDecimal amount = detail.getAmount();
					String paymentType = detail.getPaymentType();
					Date accountDate = detail.getAccountDate();

					if (amount != null && StringUtils.isNotEmpty(paymentType)) {
						totalAmount = totalAmount.add(amount);
					}

					// 付款方式
					if (!paymentTypes.contains(paymentType)) {
						paymentTypes.add(paymentType);
					}

					// 付款日期
					if (paymentDate == null) {
						paymentDate = accountDate;
					} else if (accountDate.after(paymentDate)) {
						paymentDate = accountDate;
					}
				}
			}

			if (info != null) {
				info.setTotalAmount(totalAmount);
				info.setTransportAmount(totalAmount);
				if (CollectionUtils.isNotEmpty(paymentTypes)) {
					info.setPaymentTypes(paymentTypes.toArray(new String[paymentTypes.size()]));
				}
				info.setPaymentDate(paymentDate);
			}
		}

		return info;
	}

	/**
	 * 查询代收货款费用
	 * 
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CODFeeInfo queryCODFee(String waybillNo) {

		CODFeeInfo info = null;

		// 构建查询参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.ACTIVE);
		map.put("sourceBillType",
				SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD);
		map.put("createType", SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		map.put("writeoffType",
				SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE);
		map.put("billType",
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		// 查询实收代收货款
		List<CODFeeInfo> queryList = this.getSqlSession().selectList(
				NAMESPACE + "queryCODFee-Repayment", map);

		if (CollectionUtils.isNotEmpty(queryList)) {

			BigDecimal codAmount = BigDecimal.ZERO;
			List<String> paymentTypes = new ArrayList<String>();

			for (CODFeeInfo cod : queryList) {
				codAmount = codAmount.add(cod.getCodAmount());
				
				if (!paymentTypes.contains(cod.getPaymentType())) {
					paymentTypes.add(cod.getPaymentType());
				}
			}

			info = new CODFeeInfo();
			info.setCodAmount(codAmount);
			info.setPaymentTypes(paymentTypes.toArray(new String[paymentTypes.size()]));
		}
		// 查询应付代收货款
		String codStatus = (String)this.getSqlSession().selectOne(NAMESPACE + "queryCODFee-CODStatus", map);
		if(SettlementDictionaryConstants.COD__STATUS__RETURNED.equals(codStatus)){
			queryList = this.getSqlSession().selectList(NAMESPACE + "queryCODFee-Payment", map);
		}else{
			queryList = this.getSqlSession().selectList(NAMESPACE + "queryCODFee-Payable", map);
		}
		
		if (CollectionUtils.isNotEmpty(queryList)) {

			if (info == null) {
				info = queryList.get(0);
			} else {
				CODFeeInfo queryCOD = queryList.get(0);
				info.setPaymentStatus(queryCOD.getPaymentStatus());
				info.setReturnableAmount(queryCOD.getReturnableAmount());
				info.setVerifyRcvAmount(queryCOD.getVerifyRcvAmount());
			}

		}

		return info;
	}

	/**
	 * 查询其它费用
	 * 
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OtherFeeInfo> queryOtherFee(String waybillNo) {

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("waybillNo", waybillNo);
		param.put("active", FossConstants.ACTIVE);

		final List<String> billTypes = new ArrayList<String>();
		billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);
		billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
		billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND);

		param.put("billTypes", billTypes);
		param.put("writeoffType",
				SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE);

		return this.getSqlSession().selectList(NAMESPACE + "queryOtherFee", param);

	}

	/**
	 * 查询开发票费用
	 * 
	 * @param sourceBills
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceFeeInfo> queryInvoiceFee(List<String> sourceBills) {
		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sourceBills", sourceBills);
		param.put("active", FossConstants.ACTIVE);

		return this.getSqlSession().selectList(NAMESPACE + "queryInvoiceFee", param);
	}
}

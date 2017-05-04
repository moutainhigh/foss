package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsTakingService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementExceptionType;
import com.deppon.foss.util.define.FossConstants;

public class VtsTakingService implements IVtsTakingService{
	private static final Logger LOGGER = LogManager
			.getLogger(VtsTakingService.class);
	/**
	 * 应收单公用Service
	 */
	private IBillReceivableService billReceivableService;
	/**
	 * 现金收款单公共Service
	 */
	private IBillCashCollectionService billCashCollectionService;

	/**
	 * vts确认收入 （修改现金收款单、应收单的确认收入日期为签收日期）
	 * @author 310970-caopeng
	 * @date 2016-6-14
	 * @param entity
	 * @param currentInfo
	 */
	@Override
	public void confirmIncome(LineSignDto dto,
			CurrentInfo currentInfo) {
		if(dto==null){
			throw new SettlementException(
					SettlementExceptionType.INTERFACE_AFFERFENT_DATA_IS_EMPTY);
		}
		LOGGER.info(" 签收操作调用：确认收入方法 ，运单号：" + dto.getWaybillNo());
		BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto(
				dto.getWaybillNo());
		billReceivableConDto
		.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		billReceivableConDto
		.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE});
		//根据运单号查询运单对应的应收单
		List<BillReceivableEntity> receivables = this.billReceivableService
				.queryBillReceivableByCondition(billReceivableConDto);
		//校验应收单
		if (CollectionUtils.isNotEmpty(receivables)){
			this.checkBillReceivable(dto,receivables);
			
			// 对所有查询的应收单，进行收入确认操作，包含（代收货款应收单，便于后续统计使用）确认收入日期
			for (BillReceivableEntity receEntity : receivables) {
				// 设置签收日期
				receEntity.setConrevenDate(dto.getSignDate());

				// 签收时，确认应收单收入
				this.billReceivableService.updateBillReceivableByConfirmIncome(
						receEntity, currentInfo);

				// 为代收货款应收单时，设置dto的CodReceivableEntity属性值，便于后续使用
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(receEntity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
								.equals(receEntity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
								.equals(receEntity.getBillType())) {
					// 设置代收货款实体
					dto.setCodReceivableEntity(receEntity);
				}
			}
		}
		// 查询现金收款单---更新现金收款单的确认收入日期
		List<String> sourceBillNos = new ArrayList<String>();
		sourceBillNos.add(dto.getWaybillNo());
		List<BillCashCollectionEntity> cashCollections = billCashCollectionService
				.queryBySourceBillNOs(
						sourceBillNos,
						SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.ACTIVE);
		if (CollectionUtils.isNotEmpty(cashCollections)) {
			// 设置存在结算单据标记
			dto.setStlBillCounts(1);
			if (cashCollections.size() > 1) {
				throw new SettlementException("存在多条现金收款单");
			}
			BillCashCollectionEntity cashCollectionEntity = cashCollections
					.get(0);
			if (cashCollectionEntity != null) {
				/*if (cashCollectionEntity.getConrevenDate() != null) {
					throw new SettlementException("现金收款单已确认收入，不能进行签收操作");
				}
				cashCollectionEntity.setConrevenDate(dto.getSignDate());*/

				// 签收时 确认现金收款单收入
				this.billCashCollectionService.confirmIncomeBillCashCollection(
						cashCollectionEntity, currentInfo);
			}
		}

		LOGGER.info(" 确认收入end------");
		
	}
	
	/**
	 * 专线签收时，代收货款应收单必须全部还款， 到达运费应收单付款方式为到付的，应收单的未核销金额必须等于0
	 * @author 310970 caopeng
	 * @date 2012-10-23 下午3:48:05
	 * @param entity
	 * @param receivables
	 */
	private void checkBillReceivable(LineSignDto dto,
			List<BillReceivableEntity> receivables) {
		if (dto == null) {
			throw new SettlementException(
					SettlementExceptionType.INTERFACE_AFFERFENT_DATA_IS_EMPTY);
		}
		
		// 验证同一个运单是否存在多条相同类型的应收单
		this.billReceivableService
				.validateWaybillForBillReceivable(receivables);
		for (int i = 0; i < receivables.size(); i++) {
			BillReceivableEntity receivableEntity = receivables.get(i);

			/*// 应收单的签收日期不为空和签收状态为已签收时
			if (receivableEntity.getConrevenDate() != null) {
				throw new SettlementException("应收单已确认收入，不能重复调用签收接口");
			}*/
			// 应收单未核销金额大于0的
			if (receivableEntity.getUnverifyAmount() != null
					&& receivableEntity.getUnverifyAmount().compareTo(
							BigDecimal.ZERO) > 0) {
				
				// 到达运费应收单 到付，且未核销金额大于0，抛出异常
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(receivableEntity.getBillType())
						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
								.equals(receivableEntity.getPaymentType())) {
					throw new SettlementException("到付运费应收单存在未核销金额，不能进行生效操作");
				}
				// 专线签收，需要判断代收货款的未核销金额必须等于0，未核销金额大于0，抛出异常
				else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
								.equals(receivableEntity.getBillType())) {
					throw new SettlementException("代收货款应收单存在未核销金额，不能进行生效操作");
				}
			}
		}
	}
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
	
	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}

}

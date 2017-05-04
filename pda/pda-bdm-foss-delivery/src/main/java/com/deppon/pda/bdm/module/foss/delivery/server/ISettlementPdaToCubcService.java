package com.deppon.pda.bdm.module.foss.delivery.server;

import com.deppon.foss.module.settlement.common.api.shared.domain.VerificationEntity;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetBushCardEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentSuccessEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PrepaymentEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SettlementOfPaymentSuccessEntity;

public interface ISettlementPdaToCubcService {

	/**
	 * 对账单刷卡
	 * @param asyncMsg
	 * @param entity
	 * @return
	 */
	public PayInfoDO wrapCommonQueryPayInfoDO(AsyncMsg asyncMsg,
			AccountStatementEntitys entity);
	/**
	 * 代刷卡数据上传
	 * @param asyncMsg
	 * @param entity
	 * @return
	 */
	public PayInfoDO wrapRecordQueryPayInfoDO(AsyncMsg asyncMsg,
			GetBushCardEntitys param);
	/**
	 * 预存款刷卡上传
	 * @param asyncMsg
	 * @param entity
	 * @return
	 */
	public PayInfoDO wrapPosCardQueryPayInfoDO(AsyncMsg asyncMsg,
			PrepaymentEntity param);
	/**
	 * 结清货款数据上传
	 * @param asyncMsg
	 * @param entity
	 * @return
	 */
	public PayInfoDO settlementOfPaymentPosCard(AsyncMsg asyncMsg,
			GetPaymentSuccessEntitys param);
	
	/**
	 * 支付宝条码信息上传
	 */
	public PayInfoDO  setVerificationPosCard(VerificationEntity entity,String isSettleCredit);
}

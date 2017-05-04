package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillingEWaybillEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.ReturnBillingEntity;

/**
 * @ClassName ReturnBillingEWaybillService.java
 * @Description
 * @author 201638
 * @date 2015-3-9
 */
public class ReturnBillingEWaybillService implements IBusinessService<ReturnBillingEWaybillEntity, ReturnBillingEntity> {

	private Logger log = Logger.getLogger(getClass());
	private IPdaDispatchOrderService pdaDispatchOrderService;

	public void setPdaDispatchOrderService(IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}

	@Override
	public ReturnBillingEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ReturnBillingEntity returnBillingEWaybillEntity = JsonUtil.parseJsonToObject(ReturnBillingEntity.class, asyncMsg.getContent());
		return returnBillingEWaybillEntity;
	}

	@Override
	public ReturnBillingEWaybillEntity service(AsyncMsg asyncMsg, ReturnBillingEntity returnBillingEWaybillEntity) throws PdaBusiException {
		this.validate(asyncMsg, returnBillingEWaybillEntity);
		ReturnBillingEWaybillEntity result = null;
		log.debug("---调用FOSS接货接收订单接口开始---");
		try {
			// 根据运单号下拉返货明细
			result = pdaDispatchOrderService.getReturnWaybillEntity(returnBillingEWaybillEntity.getWayBillCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		return result;
	}

	private void validate(AsyncMsg asyncMsg, ReturnBillingEntity returnBillingEWaybillEntity) throws PdaBusiException {
		// 运单号不能为空
		Argument.hasText(returnBillingEWaybillEntity.getWayBillCode(), "wayBillCode");
	}

	/**
	 * @description
	 * @return
	 * @author 201638
	 * @date 2015-3-9
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_RETURN_BILLING.VERSION;
	}

	/**
	 * @description 下拉接口，同步
	 * @return false
	 * @author 201638
	 * @date 2015-3-9
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

}

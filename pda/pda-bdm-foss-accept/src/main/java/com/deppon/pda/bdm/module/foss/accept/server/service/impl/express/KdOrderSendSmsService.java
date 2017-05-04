package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdOrderSendSmsEntity;

/**
 * 短信优化提醒
 * @author 245955
 * Date:2015-09-16
 *
 */
public class KdOrderSendSmsService implements IBusinessService<Void, KdOrderSendSmsEntity> {
	private Logger log = Logger.getLogger(getClass());
	private IPdaDispatchOrderService pdaDispatchOrderService;
	/**
	 * 获取包头信息
	 * 
	 */
	@Override
	public KdOrderSendSmsEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
        KdOrderSendSmsEntity kdOrderSendSmsEntity=JsonUtil.parseJsonToObject(KdOrderSendSmsEntity.class, asyncMsg.getContent());
		return kdOrderSendSmsEntity;
	}

	/**
	 * 短信发送的Service
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, KdOrderSendSmsEntity param)
			throws PdaBusiException {
		this.validate(asyncMsg, param);
		try {
			log.debug("---调用FOSS发送短信接口开始---");
			//给FOSS的订单编号赋值
			PdaOrderDto pdaOrderDto=new PdaOrderDto();
			pdaOrderDto.setOrderNo(param.getOrderCode());
			// 调用FOSS接口,根据订单号发送短信
			pdaDispatchOrderService.acceptOrder(pdaOrderDto);
			log.debug("---调用FOSS发送短信接口结束---");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		return null;
	}

	/**
	 * 参数验证
	 * @param asyncMsg
	 * @param returnBillingEWaybillEntity
	 * @throws PdaBusiException
	 */
	private void validate(AsyncMsg asyncMsg, KdOrderSendSmsEntity param) throws PdaBusiException {
		//订单号不能为空
		Argument.hasText(param.getOrderCode(),"orderCode");
	}

	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_ORDER_SEND_SMS.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaDispatchOrderService(
			IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}
  
}

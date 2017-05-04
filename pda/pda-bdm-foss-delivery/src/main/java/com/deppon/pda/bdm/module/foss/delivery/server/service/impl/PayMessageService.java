package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverTaskService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaFinancialDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaFinancialParamDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayMessageEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayMessageResultEntity;

/**
 * 
  * @ClassName PayMessageService 
  * @Description 
  * @author 
  * @date 
 */
public class PayMessageService implements IBusinessService<PayMessageResultEntity, PayMessageEntity>{
	
	private static final Log LOG = LogFactory.getLog(PayMessageService.class);
	
	private IPdaDeliverTaskService pdaDeliverTaskService;
	
	/**
	 * 解析包体
	 */
	@Override
	public PayMessageEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PayMessageEntity payMessageEntity = JsonUtil.parseJsonToObject(PayMessageEntity.class, asyncMsg.getContent());
		return payMessageEntity;
	}
	/**
	 * 服务方法
	 */
	@Override
	public PayMessageResultEntity service(AsyncMsg asyncMsg, PayMessageEntity payMessageEntity) throws PdaBusiException {
		LOG.info(payMessageEntity);
		PayMessageResultEntity payMessageResultEntity = null;
		PdaFinancialParamDto pdaFinancialParamDto = null;
		try {
			//验证数据有效性,运单号不能为空
			this.validate(asyncMsg,payMessageEntity);
			//封装实体
			pdaFinancialParamDto = new PdaFinancialParamDto();
			pdaFinancialParamDto.setWaybillNo(payMessageEntity.getWaybillNo());
			//调用foss接口查询运单号的付款信息
			 PdaFinancialDto pdaFinancialDto = pdaDeliverTaskService.selectFinancial(pdaFinancialParamDto);
			//封装返回实体
			 payMessageResultEntity = new PayMessageResultEntity();
			 payMessageResultEntity.setCodAmount(pdaFinancialDto.getCodAmount());
			 payMessageResultEntity.setToPayAmount(pdaFinancialDto.getToPayAmount());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return payMessageResultEntity;
	}
	
	/**
	 * 验证数据
	 * @param asyncMsg
	 * @param selfDeryScan
	 */
	private void validate(AsyncMsg asyncMsg, PayMessageEntity payMessageEntity){
		//运单号
		Argument.notNull(payMessageEntity.getWaybillNo(), "PayMessageEntity.waybillNo");
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_RETURN_BILLING_PAY_MESSAGE.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaDeliverTaskService(IPdaDeliverTaskService pdaDeliverTaskService) {
		this.pdaDeliverTaskService = pdaDeliverTaskService;
	}
	
}

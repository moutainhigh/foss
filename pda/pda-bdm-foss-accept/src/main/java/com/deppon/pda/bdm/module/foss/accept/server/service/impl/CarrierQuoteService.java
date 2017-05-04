package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPriceService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PreferentialDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryQuotesEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QuotesEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file CarrierQuoteService.java 
 * @description PDA承运报价
 * @author ChenLiang
 * @created 2012-12-26 下午2:10:36    
 * @version 1.0
 */
public class CarrierQuoteService implements IBusinessService<QryQuotesEntity, QuotesEntity> {
	
	private Logger log = Logger.getLogger(getClass());
	
	private IPdaPriceService pdaPriceService;

	public void setPdaPriceService(IPdaPriceService pdaPriceService) {
		this.pdaPriceService = pdaPriceService;
	}
	
	/**
	 * 
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public QuotesEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		QuotesEntity quotesVo = JsonUtil.parseJsonToObject(QuotesEntity.class, asyncMsg.getContent());
		return quotesVo;
	}

	/**
	 * 
	 * @description 数据校验
	 * @param asyncMsg
	 * @param quotes
	 * @throws PdaBusiException 
	 * @created 2012-12-29 下午7:49:21
	 */
	private void validate(AsyncMsg asyncMsg, QuotesEntity quotes) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		Argument.notNull(quotes, "QuotesEntity");
		//客户编号
		Argument.hasText(quotes.getCustomerCode(), "QuotesEntity.customerCode");
	}
	
	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param quotes
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public QryQuotesEntity service(AsyncMsg asyncMsg, QuotesEntity quotes) throws PdaBusiException {
		this.validate(asyncMsg, quotes);
		
		log.info("---调用FOSS查询PDA承运报价接接口开始---");
		PreferentialDto preferentialDto = null;
		QryQuotesEntity qryQuotes = null;
		try {
			preferentialDto = pdaPriceService.queryPreferentialInfo(quotes.getCustomerCode(), quotes.getDate());
			if(preferentialDto==null){
				throw new FossInterfaceException(null,"客户编号不存在");
			}
			qryQuotes = new QryQuotesEntity();
			// 代收货款费率
			qryQuotes.setAgentGathRate(preferentialDto.getAgentGathRate());
			// 运费折扣费率
			qryQuotes.setChargeRebate(preferentialDto.getChargeRebate());
			// 保价费率
			qryQuotes.setInsureDpriceRate(preferentialDto.getInsureDpriceRate());
			// 接货费率
			qryQuotes.setReceivePriceRate(preferentialDto.getReceivePriceRate());
			//申请欠款额度
			qryQuotes.setArrearsAmount((preferentialDto.getArrearsAmount()==null)?0:preferentialDto.getArrearsAmount().doubleValue());
			log.info(preferentialDto.getChargeType());
			if(preferentialDto.getChargeType()!=null&&preferentialDto.getChargeType().equals("MONTH_END")){
				qryQuotes.setIsMonthEnd("Y");
			}else{
				qryQuotes.setIsMonthEnd("N");
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.info("---调用FOSS查询PDA承运报价接接口结束---");
		return qryQuotes;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_PDA_QUOTES.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
}

package com.deppon.foss.module.settlement.pay.server.action;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardDeductService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

/** 
 * 合伙人奖罚对账单管理--扣款操作
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-07 
 */
public class PartnerStatementOfAwardDeductAction extends AbstractAction{

	//定义序列号
	private static final long serialVersionUID = 1L;
	
	//声明日志
	private static final Logger logger = LoggerFactory.getLogger(PartnerStatementOfAwardDeductAction.class);
	
	/**
	 * 注入合伙人奖罚扣款Service
	 */
	private IPartnerStatementOfAwardDeductService partnerStatementOfAwardDeductService;
	
	/**
	 * 注入奖罚dto
	 */
	private PartnerStatementOfAwardDto dto ;

	@JSON
	public String pAwardToDeduct(){
		try {		
			//非空判断传入对账单号集合
			if(CollectionUtils.isEmpty(dto.getStatementBillNos())){
				throw new SettlementException("传入对账单号为空！");
			}
			
			//调用扣款Service
			this.partnerStatementOfAwardDeductService.partnerStatementOfAwardToDeduct(dto.getStatementBillNos());
			//正常返回
			return returnSuccess();
			//异常处理
		} catch (BusinessException e) {
			//记录异常日志
			logger.error(e.getErrorCode(),e);
			//返回异常
			return returnError(e);
		}
	}
	
	
	
	public void setPartnerStatementOfAwardDeductService(
			IPartnerStatementOfAwardDeductService partnerStatementOfAwardDeductService) {
		this.partnerStatementOfAwardDeductService = partnerStatementOfAwardDeductService;
	}



	public PartnerStatementOfAwardDto getDto() {
		return dto;
	}



	public void setDto(PartnerStatementOfAwardDto dto) {
		this.dto = dto;
	}
	
	
	
}

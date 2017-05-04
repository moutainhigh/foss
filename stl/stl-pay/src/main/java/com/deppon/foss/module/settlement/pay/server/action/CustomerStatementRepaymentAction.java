package com.deppon.foss.module.settlement.pay.server.action;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.ICustomerStatementRepaymentService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillRepaymentManageVo;

/**
 * 大客户对账单还款Action
 * 
 *  @author 269044
 *  @date 2015-12-3
 */
public class CustomerStatementRepaymentAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(WoodenStatementRepaymentAction.class);
	
	private BillRepaymentManageVo billRepaymentManageVo = new BillRepaymentManageVo();
	
	//大客户对账单还款Service
	private ICustomerStatementRepaymentService customerStatementRepaymentService;

	/**
	 * 大客户对账单还款
	 * 
	 *  @author 269044
	 *  @date 2015-12-3
	 */
	@JSON
	public String customerToRepayment() {
		try {
			LOGGER.debug("大客户对账单还款开始......");
			//获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//获取批量作废还款单的 当前用户			
			if(null==cInfo.getEmpName()){
				//抛出异常
				throw new SettlementException("大客户对账单还款获取用户名为空", "");
			}
			LOGGER.debug("大客户对账单还款的当前登录用户：" + cInfo.getUserName());
			//还款
			String repaymentNo = customerStatementRepaymentService.customerToRepayment(billRepaymentManageVo.getBillStatementToPaymentQueryDto(),cInfo);
			//获取还款单号
			BillRepaymentManageDto billRepaymentManageDto = new BillRepaymentManageDto();
			billRepaymentManageDto.setRepaymentNo(repaymentNo);
			//设置返回还款单号
			billRepaymentManageVo.setBillRepaymentManageDto(billRepaymentManageDto);
			//还款结束
			LOGGER.debug("还款success...");
			return returnSuccess();
		} catch (SettlementException e) {
			//批量作废还款单logger
			LOGGER.error("还款、批量还款异常"+e.getMessage(), e);
			return returnError(e);
		}
	}
	public BillRepaymentManageVo getBillRepaymentManageVo() {
		return billRepaymentManageVo;
	}

	public void setBillRepaymentManageVo(BillRepaymentManageVo billRepaymentManageVo) {
		this.billRepaymentManageVo = billRepaymentManageVo;
	}
	public void setCustomerStatementRepaymentService(
			ICustomerStatementRepaymentService customerStatementRepaymentService) {
		this.customerStatementRepaymentService = customerStatementRepaymentService;
	}

}

package com.deppon.foss.module.settlement.consumer.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.server.service.IInvoiceRegisterService;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.InvoiceRegisterVo;

/**
 * 定额发票登记action.
 *
 * @author 163576
 * @date 2013-1-29 下午2:03:39
 */
public class InvoiceRegisterAction extends AbstractAction {

	/** 定额发票登记action序列号. */
	private static final long serialVersionUID = 3852171886471592876L;

	/** 声明日志对象. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InvoiceRegisterAction.class);

	/** 定额发票登记Vo. */
	private InvoiceRegisterVo vo;

	/** 注入定额发票登记服务service. */
	private IInvoiceRegisterService invoiceRegisterService;
	
	/**
	 * 运单关联小票
	 *
	 * @return the string
	 * @author ddw
	 * @date 2015-04-09
	 */
	@JSON
	public String queryOtherRevenueNosByWaybillNos() {
		try {
			// 获取当前登录用户信息
			InvoiceRegisterVo returnVo = invoiceRegisterService.queryOtherRevenueNosByWaybillNos(vo);
			// 设置返回值
			this.setVo(returnVo);
		} catch (BusinessException e) {

			LOGGER.error("验证及获得发票金额：" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 验证及获得发票金额.
	 *
	 * @return the string
	 * @author 163576
	 * @date 2013-1-29 下午2:03:39
	 */
	@JSON
	public String validateAndQueryInvoiceAmounts() {
		try {
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			InvoiceRegisterVo returnVo = invoiceRegisterService.validateAndQueryInvoiceAmounts(vo,cInfo);
			// 设置返回值
			this.setVo(returnVo);
		} catch (BusinessException e) {

			LOGGER.error("验证及获得发票金额：" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}

		return returnSuccess();
	}
	
	/**
	 * 定额发票登记申请.
	 *
	 * @return the string
	 * @author 163576
	 * @date 2013-1-29 下午2:03:39
	 */
	@JSON
	public String registerDoInvoice() {

		try {
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			InvoiceRegisterVo returnVo = invoiceRegisterService.registerInvoice(vo, cInfo);
			// 设置返回值
			this.setVo(returnVo);	
		} catch (BusinessException e) {

			LOGGER.error("定额发票登记申请：" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * @return the vo
	 */
	public InvoiceRegisterVo getVo() {
		return vo;
	}

	/**
	 * @param vo the vo to set
	 */
	public void setVo(InvoiceRegisterVo vo) {
		this.vo = vo;
	}

	/**
	 * @param invoiceRegisterService the invoiceRegisterService to set
	 */
	public void setInvoiceRegisterService(
			IInvoiceRegisterService invoiceRegisterService) {
		this.invoiceRegisterService = invoiceRegisterService;
	}

}

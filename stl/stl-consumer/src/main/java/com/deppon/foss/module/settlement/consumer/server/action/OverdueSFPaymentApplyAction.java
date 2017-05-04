package com.deppon.foss.module.settlement.consumer.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOverdueSFPaymentApplyService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.OverdueSFPaymentApplyVo;

/**
 * 超时装卸费付款申请
 * @author 105762
 * @date 2014-4-23 下午4:32:58
 * @since
 * @version
 */
public class OverdueSFPaymentApplyAction extends AbstractAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3536119814566304229L;

	private static final Logger LOGGER = LoggerFactory.getLogger(OverdueSFPaymentApplyAction.class);

	/**
	 * servce
	 */
	private IOverdueSFPaymentApplyService overdueSFPaymentApplyService;

	/**
	 * vo
	 */
	private OverdueSFPaymentApplyVo overdueSFPaymentApplyVo;

	// 获取当前用户信息
	CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

	/**
	 * <p>
	 * 查询
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-4-23 下午4:32:47
	 * @param overdueSFPaymentApplyVo
	 * @return
	 * @see
	 */
	@JSON
	public String query() {
		LOGGER.info("超时装卸费付款申请 查询开始...");
		try {
			// 校验参数
			validateQueryDtoIsNotNull();

			// 获取queryDto
			OverdueSFPaymentApplyQueryDto dto = overdueSFPaymentApplyVo.getOverdueSFPaymentApplyQueryDto();

			// 设置 start limit
			dto.setStart(this.getStart());
			dto.setLimit(this.getLimit());
			// 查询
			overdueSFPaymentApplyVo.setOverdueSFPaymentApplyResultDto(overdueSFPaymentApplyService.query(dto, currentInfo));
		} catch (SettlementException e) {
			LOGGER.info("超时装卸费付款申请 查询异常" + e.getMessage());
			return returnError(e);
		}
		LOGGER.info("超时装卸费付款申请 查询结束");
		return returnSuccess();
	}

	/**
	 * <p>
	 * 申请审批
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-7 下午5:39:56
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	@JSON
	public String applyOrAudit() {
		LOGGER.info("申请or审核付款操作开始");
		// 校验参数
		validateQueryDtoIsNotNull();

		try {
			// 获取queryDto
			OverdueSFPaymentApplyQueryDto dto = overdueSFPaymentApplyVo.getOverdueSFPaymentApplyQueryDto();
			// 提交申请
			overdueSFPaymentApplyService.applyOrAudit(dto, currentInfo);
		} catch (SettlementException e) {
			LOGGER.info("申请付款操作异常" + e.getMessage());
			return returnError(e);
		}
		LOGGER.info("申请or付款操作结束 ");
		return returnSuccess();
	}


	/**
	 * <p>
	 * 验证QueryDto
	 * </p>
	 * 
	 * @author 105762
	 * @date 2014-5-7 下午5:50:44
	 * @see
	 */
	private void validateQueryDtoIsNotNull() {
		if (overdueSFPaymentApplyVo == null || overdueSFPaymentApplyVo.getOverdueSFPaymentApplyQueryDto() == null) {
			throw new SettlementException("参数 overdueSFPaymentApplyVo 为空");
		}
	}

	/**
	 * @return the overdueSFPaymentApplyVo
	 */
	public OverdueSFPaymentApplyVo getOverdueSFPaymentApplyVo() {
		return overdueSFPaymentApplyVo;
	}

	/**
	 * @param overdueSFPaymentApplyVo
	 *            the overdueSFPaymentApplyVo to set
	 */
	public void setOverdueSFPaymentApplyVo(OverdueSFPaymentApplyVo overdueSFPaymentApplyVo) {
		this.overdueSFPaymentApplyVo = overdueSFPaymentApplyVo;
	}

	/**
	 * @param overdueSFPaymentApplyService
	 *            the overdueSFPaymentApplyService to set
	 */
	public void setOverdueSFPaymentApplyService(IOverdueSFPaymentApplyService overdueSFPaymentApplyService) {
		this.overdueSFPaymentApplyService = overdueSFPaymentApplyService;
	}

}

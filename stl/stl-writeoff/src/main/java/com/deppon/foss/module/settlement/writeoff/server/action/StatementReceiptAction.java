/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.action;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementConfReceiptDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementConfReceiptVo;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementOfAccountMakeQueryResultVo;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementOfAccountMakeQueryVo;

/**
 * 对账单回执action
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-2 下午5:35:25
 */
public class StatementReceiptAction extends AbstractAction {

	/**
	 * 对账单回执action序列号
	 */
	private static final long serialVersionUID = 5901988518238433399L;

	/**
	 * 对账单回执Vo参数类
	 */
	private StatementConfReceiptVo statementConfReceiptVo = new StatementConfReceiptVo();

	/**
	 * 制作对账单查询结果（对账单及对账单明细单据）
	 */
	private StatementOfAccountMakeQueryResultVo statementOfAccountMakeQueryResultVo = new StatementOfAccountMakeQueryResultVo();

	/**
	 * 前台查询参数
	 */
	private StatementOfAccountMakeQueryVo statementOfAccountMakeQueryVo = new StatementOfAccountMakeQueryVo();

	/**
	 * 对账单回执service
	 */
	private IStatementReceiptService statementReceiptService;

	/**
	 * 对账单service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 生成对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-2 下午1:34:09
	 */
	public String addStatementConfReceipt() {

		try {
			// 获取对账单Id
			String statementId = statementOfAccountMakeQueryVo.getStatementOfAccountMakeQueryDto().getStatementBillId();

			// 根据对账单ID查询对账单
			StatementOfAccountEntity entity = statementOfAccountService.queryByPrimaryKey(statementId);

			// 将对账单设置到Vo中返回
			StatementOfAccountMakeQueryResultDto dto = new StatementOfAccountMakeQueryResultDto();

			// 设置返回的对账单以还款金额,如果未还款金额为null或者0，以还款金额为本期发生发生金额
			if (entity.getUnpaidAmount() == null|| entity.getUnpaidAmount().compareTo(BigDecimal.ZERO) == 0) {
				//设置还款金额
				dto.setPaidAmount(entity.getPeriodAmount());
				//设置未还金额
				entity.setUnpaidAmount(BigDecimal.ZERO);

				// 如果未还款金额>0，以还款金额为本期发生发生金额减去未还款金额
			} else {
				//设置还款金额为本期发生发生金额减去未还款金额
				dto.setPaidAmount(entity.getPeriodAmount().subtract(entity.getUnpaidAmount()));
			}

			// 设置返回的对账单
			dto.setStatementOfAccount(entity);
			//设置返回值
			statementOfAccountMakeQueryResultVo.setStatementOfAccountMakeQueryResultDto(dto);

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 查询最新的打印对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-5 上午10:23:55
	 */
	public String queryStatementReceipt() {

		try {

			// 获取对账单号
			String statementBillNo = statementConfReceiptVo.getStatementConfReceiptDto().getStatementBillNo();

			// 对账单号不能为空
			if (StringUtils.isEmpty(statementBillNo)) {
				//提示查询回执时,对账单号不能为空
				throw new SettlementException("查询回执时,对账单号不能为空!");
			}

			// 查询最新的打印对账单回执信息
			StatementConfReceiptEntity statementConfReceiptEntity = statementReceiptService.queryLastPrintReceipt(statementBillNo);

			// 如果对账单号没有相关打印数据，提示该对账单还没有打印过任何回执
			if (statementConfReceiptEntity == null) {
				//该对账单还没有打印过任何回执
				throw new SettlementException("该对账单还没有打印过任何回执!");
			}

			// 设置返回值Vo实体
			StatementConfReceiptDto statementConfReceiptDto = new StatementConfReceiptDto();
			//设置返回dto值
			statementConfReceiptDto.setStatementConfReceiptEntity(statementConfReceiptEntity);
			//设置返回vo值
			statementConfReceiptVo.setStatementConfReceiptDto(statementConfReceiptDto);

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 打印对账单回执
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-5 上午10:24:40
	 */
	public String printStatementReceipt() {

		try {

			// 获取对账单号
			String statementBillId = statementConfReceiptVo.getStatementConfReceiptDto().getStatementBillId();

			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

			// 调用service，保存对账单回执，并调用公共打印接口打印对账单
			statementReceiptService.printStatementConfReceipt(statementBillId,currentInfo);

			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * @return statementConfReceiptVo
	 */
	public StatementConfReceiptVo getStatementConfReceiptVo() {
		return statementConfReceiptVo;
	}

	/**
	 * @param statementConfReceiptVo
	 */
	public void setStatementConfReceiptVo(
			StatementConfReceiptVo statementConfReceiptVo) {
		this.statementConfReceiptVo = statementConfReceiptVo;
	}

	/**
	 * @return statementOfAccountMakeQueryResultVo
	 */
	public StatementOfAccountMakeQueryResultVo getStatementOfAccountMakeQueryResultVo() {
		return statementOfAccountMakeQueryResultVo;
	}

	/**
	 * @param statementOfAccountMakeQueryResultVo
	 */
	public void setStatementOfAccountMakeQueryResultVo(
			StatementOfAccountMakeQueryResultVo statementOfAccountMakeQueryResultVo) {
		this.statementOfAccountMakeQueryResultVo = statementOfAccountMakeQueryResultVo;
	}

	/**
	 * @return statementOfAccountMakeQueryVo
	 */
	public StatementOfAccountMakeQueryVo getStatementOfAccountMakeQueryVo() {
		return statementOfAccountMakeQueryVo;
	}

	/**
	 * @param statementOfAccountMakeQueryVo
	 */
	public void setStatementOfAccountMakeQueryVo(
			StatementOfAccountMakeQueryVo statementOfAccountMakeQueryVo) {
		this.statementOfAccountMakeQueryVo = statementOfAccountMakeQueryVo;
	}

	/**
	 * @param statementReceiptService
	 */
	public void setStatementReceiptService(
			IStatementReceiptService statementReceiptService) {
		this.statementReceiptService = statementReceiptService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

}

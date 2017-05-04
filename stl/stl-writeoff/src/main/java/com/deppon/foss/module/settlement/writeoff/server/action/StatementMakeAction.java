package com.deppon.foss.module.settlement.writeoff.server.action;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementOfAccountMakeQueryResultVo;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementOfAccountMakeQueryVo;
import com.deppon.foss.util.DateUtils;

/**
 * 对账单Action请求处理类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-11 下午4:52:06
 */

public class StatementMakeAction extends AbstractAction {

	private static final Logger logger = LoggerFactory.getLogger(StatementMakeAction.class);
	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 7827213280759899253L;

	/**
	 * 制作对账单查询结果（对账单及对账单明细单据）
	 */
	private StatementOfAccountMakeQueryResultVo statementOfAccountMakeQueryResultVo = new StatementOfAccountMakeQueryResultVo();

	/**
	 * 前台查询参数
	 */
	private StatementOfAccountMakeQueryVo statementOfAccountMakeQueryVo = new StatementOfAccountMakeQueryVo();

	/**
	 * 制作对账单服务类接口
	 */
	private IStatementMakeService statementMakeService;

	/**
	 * 制作对账单时根据输入参数返回对账单及对账单明细单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-11 下午5:45:05
	 */
	@JSON
	public String queryForStatementMake() {
		// 获取查询DTO
		StatementOfAccountMakeQueryDto queryDto = statementOfAccountMakeQueryVo.getStatementOfAccountMakeQueryDto();
		// 获取当前登录信息
		CurrentInfo info = FossUserContext.getCurrentInfo();
		/*
		 * 首先校验前台传入的非空参数是否为空 
		 * 
		 * 1、当按客户制作时，客户编码、账期不能为空；
		 *    单据明细子类型不能为空单据子类型不能同时为预收和预付单，不能只为预收或预付单 
		 * 2、当按单号制作时，传入的明细单号不能为空
		 */
		try {
			//按日期查询
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
				//客户编码不能为空
				if (StringUtils.isEmpty(queryDto.getCustomerCode())) {
					//为空，提示客户编码为空，不能制作对账单
					throw new SettlementException("客户编码为空，不能制作对账单！");
				}
				//账期的开始或结束日期不能为空
				if (queryDto.getPeriodBeginDate() == null|| queryDto.getPeriodEndDate() == null) {
					//为空，提示账期的开始或结束日期为空，不能制作对账单
					throw new SettlementException("账期的开始或结束日期为空，不能制作对账单！");
				}
				// 给结束日期加1天
				queryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(queryDto.getPeriodEndDate(), 1),DateUtils.DATE_FORMAT));
				//单据明细子类型不能为空
				if (queryDto.getBillDetailTypes() == null|| queryDto.getBillDetailTypes().length == 0) {
					//为空，提示单据明细子类型没有选择，不能制作对账单
					throw new SettlementException("单据明细子类型没有选择，不能制作对账单！");
				}
				//单据明细子类型不能只选择预付单
				if (queryDto.getBillDetailTypes().length == 2) {
					if (queryDto.getIsAdvancedPayment()&& queryDto.getIsDepositReceived()) {
						//提示单据明细子类型只选择预付单，不能制作对账单
						throw new SettlementException("单据明细子类型只选择预付单，不能制作对账单！");
					}
				}
				//单据明细子类型不能只为预付单
				if (queryDto.getBillDetailTypes().length == 1) {
					if (queryDto.getIsAdvancedPayment()) {
						//提示单据明细子类型只为预付单，不能制作对账单
						throw new SettlementException("单据明细子类型只为预付单，不能制作对账单！");
					}
				}
				//判断是否统一结算
				if(StringUtils.isEmpty(queryDto.getReceivableUnified())){
					throw new SettlementException("应收统一结算不能为空！");
				}
			//按单号查询
			} else if(SettlementConstants.TAB_QUERY_BY_BILL_NO.equals(queryDto.getQueryPage())){
				//单号不能为空
				if (queryDto.getBillDetailNos() == null|| queryDto.getBillDetailNos().length == 0) {
					//为空，提示单号为空，不能制作对账单
					throw new SettlementException("单号为空，不能制作对账单！");
				}
			//按	航空正单号查询
			} else{
				//客户编码不能为空
				if (StringUtils.isEmpty(queryDto.getCustomerCode())) {
					//为空，提示客户编码为空，不能制作对账单
					throw new SettlementException("客户编码为空，不能制作对账单！");
				}
				//起始单号不能为空
				if(StringUtils.isEmpty(queryDto.getStartNumber())){
					//为空，提示客户编码为空，不能制作对账单
					throw new SettlementException("起始正单号为空，不能制作对账单！");
				}
				//结束单号不能为空
				if(StringUtils.isEmpty(queryDto.getEndNumber())){
					//为空，提示客户编码为空，不能制作对账单
					throw new SettlementException("结束正单号为空，不能制作对账单！");
				}
			}

			// 返回对账单明细及对账单结果集
			StatementOfAccountMakeQueryResultDto resultDto = statementMakeService.queryForStatementMake(queryDto, info);
			//设置对账单返回结果
			statementOfAccountMakeQueryResultVo.setStatementOfAccountMakeQueryResultDto(resultDto);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * 制作对账单保存操作
	 * 
	 * @author 045738-foss-maojianqiang
	 * @date 2012-10-19 上午10:48:46
	 */
	@JSON
	public String addStatement() {
		//记录日志
		logger.info("制作对账单action开始");
		// 获取当前登录信息
		CurrentInfo info = FossUserContext.getCurrentInfo();
		// 获取前台保存时传入的dto
		StatementOfAccountMakeQueryResultDto resultDto = statementOfAccountMakeQueryResultVo.getStatementOfAccountMakeQueryResultDto();
		try {
			logger.info("制作对账单service进入");
			// 调用保存对账单接口
			StatementOfAccountMakeQueryResultDto dto = statementMakeService.addStatement(resultDto, info);
			//如果返回结果为空，新建对账单实体
			if (dto == null) {
				//新建对账单制作dto
				dto = new StatementOfAccountMakeQueryResultDto();
			}
			//返回制作对账单结果
			statementOfAccountMakeQueryResultVo.setStatementOfAccountMakeQueryResultDto(dto);
			//记录日志
			logger.info("制作对账单action结束");
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	/**
	 * @return statementOfAccountMakeQueryResultVo
	 */
	public StatementOfAccountMakeQueryResultVo getStatementOfAccountMakeQueryResultVo() {
		// statementOfAccountMakeQueryResultVo
		return statementOfAccountMakeQueryResultVo;
	}

	/**
	 * @param statementOfAccountMakeQueryResultVo
	 */
	public void setStatementOfAccountMakeQueryResultVo(StatementOfAccountMakeQueryResultVo statementOfAccountMakeQueryResultVo) {
		// statementOfAccountMakeQueryResultVo
		this.statementOfAccountMakeQueryResultVo = statementOfAccountMakeQueryResultVo;
	}

	/**
	 * @return statementOfAccountMakeQueryVo
	 */
	public StatementOfAccountMakeQueryVo getStatementOfAccountMakeQueryVo() {
		// statementOfAccountMakeQueryVo
		return statementOfAccountMakeQueryVo;
	}

	/**
	 * @param statementOfAccountMakeQueryVo
	 */
	public void setStatementOfAccountMakeQueryVo(StatementOfAccountMakeQueryVo statementOfAccountMakeQueryVo) {
		// statementOfAccountMakeQueryVo
		this.statementOfAccountMakeQueryVo = statementOfAccountMakeQueryVo;
	}

	/**
	 * @param statementMakeService
	 */
	public void setStatementMakeService(IStatementMakeService statementMakeService) {
		// statementMakeService
		this.statementMakeService = statementMakeService;
	}

}

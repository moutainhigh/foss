package com.deppon.foss.module.settlement.consumer.server.action;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashIncomeStatementsService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CashIncomeStatementsVo;
import com.deppon.foss.util.DateUtils;

/**
 * 查询现金收入明细报表Action.
 *
 * @author 099995-foss-wujiangtao
 * @date 2012-11-8 下午2:11:26
 * @since
 * @version
 */
public class CashIncomeStatementsQueryAction extends AbstractAction {

	/** 序列化ID. */
	private static final long serialVersionUID = 4121826856403006580L;

	/** 现金收入明细报表Service. */
	private ICashIncomeStatementsService cashIncomeStatementsService;
	
	
	/** 现金收入报表Vo. */
	private CashIncomeStatementsVo vo;

	/**
	 * 根据查询条件查询各种实收单明细记录.
	 *
	 * @return the string
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午2:07:06
	 * @see
	 */
	@JSON
	public String queryCashIncomeStatementsByCondition() {
		if (vo == null || StringUtils.isEmpty(vo.getQueryType())
				|| vo.getDto() == null) {
			return returnError("查询参数不能为空！");
		}
		CashIncomeStatementsQueryDto dto = vo.getDto();
		
		//设置当前操作者编码
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		dto.setEmpCode(currentInfo.getEmpCode());
		
		CashIncomeStatementsResultDto resultDto = null;
		dto.setPaging(true);// 设置为分页

		// 按照日期查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(vo.getQueryType())) {
			if (dto.getStartDate() == null || dto.getEndDate() == null) {
				return returnError("日期参数不能为空！");
			}

			// Long i 表示开始日期和结束日期相差的天数
			Long i = DateUtils.getTimeDiff(DateUtils.convert(
					dto.getStartDate(), DateUtils.DATE_FORMAT), DateUtils
					.convert(dto.getEndDate(), DateUtils.DATE_FORMAT));

			// 默认为31天
			int maxDays=SettlementConstants.DATE_LIMIT_DAYS_MONTH;
			
			if (i != null && i.longValue() > maxDays) {
				return returnError("查询开始日期和结束日期不能超过"+maxDays+"天！");
			}
			
			// 处理结束日期，在原结束日期上加1
			dto.setEndDate(DateUtils.convert(
					DateUtils.addDay(dto.getEndDate(), 1),
					DateUtils.DATE_FORMAT));

			// 按照记账日期查询
			if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(dto
					.getDateType())) {
				dto.setAccountStartDate(dto.getStartDate());
				dto.setAccountEndDate(dto.getEndDate());
			}
			// 按照确认收银日期
			else if (SettlementConstants.TAB_DATE_TYPE_FOR_CONFIRM.equals(dto
					.getDateType())) {
				dto.setCashConfirmStartDate(dto.getStartDate());
				dto.setCashConfirmEndDate(dto.getEndDate());
			}

			// 按照单据类型进行查询时
			if (StringUtils.isNotEmpty(dto.getBillType())) {
				if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION
						.equals(dto.getBillType())) {
					// 选择单据类型为现金收款单时
					resultDto = this.cashIncomeStatementsService
							.queryBillCashByCondition(dto, this.getStart(),
									this.getLimit());
				} else if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
						.equals(dto.getBillType())) {
					// 选择单据类型为预收单时
					resultDto = this.cashIncomeStatementsService
							.queryBillDepositReceivedByCondition(dto,
									this.getStart(), this.getLimit());
				} else if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT
						.equals(dto.getBillType())) {
					// 选择单据类型为还款时
					resultDto = this.cashIncomeStatementsService
							.queryBillRepaymentByCondition(dto,
									this.getStart(), this.getLimit());
				}
			} else {
				// 未选择单据类型，默认查询三种单据（现金收款单/还款单/预收单）
				resultDto = this.cashIncomeStatementsService.queryByCondition(
						dto, this.getStart(), this.getLimit());
			}

		} else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(vo
				.getQueryType())) {
			// 按照对账单号进行查询
			if (CollectionUtils.isEmpty(dto.getStatementBillNos())) {
				return returnError("对账单号不能为空");
			}
			resultDto = this.cashIncomeStatementsService
					.queryBillRepaymentByStatementBillNOs(dto);
		} else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(vo
				.getQueryType())) {
			// 按照运单号进行查询
			if (CollectionUtils.isEmpty(dto.getWaybillNos())) {
				return returnError("运单号不能为空");
			}
			resultDto = this.cashIncomeStatementsService.queryByWaybillNos(dto);

		} else if (SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO
				.equals(vo.getQueryType())) {
			// 按照预收单号进行查询
			if (CollectionUtils.isEmpty(dto.getDepositReceivedNos())) {
				return returnError("预收单号不能为空");
			}
			resultDto = this.cashIncomeStatementsService
					.queryBillDepositReceivedByNOs(dto);
		} else {
			return returnError("输入查询参数不合法！");
		}
		fillVoProperty(resultDto);
		return returnSuccess("查询成功");
	}

	/**
	 * 设置vo属性值.
	 *
	 * @param resultDto the result dto
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 下午8:15:13
	 */
	private void fillVoProperty(CashIncomeStatementsResultDto resultDto) {
		if (resultDto != null) {
			vo.setList(resultDto.getList());// 设置返回集合
			this.setTotalCount(resultDto.getTotalCount());
			vo.setTotalAmount(resultDto.getTotalAmount());// 设置总金额
			vo.setChTotalAmount(resultDto.getChTotalAmount());// 设置现金总金额
			vo.setCdTotalAmount(resultDto.getCdTotalAmount());
			vo.setNtTotalAmount(resultDto.getNtTotalAmount());
			vo.setTtTotalAmount(resultDto.getTtTotalAmount());
			vo.setOlTotalAmount(resultDto.getOlTotalAmount());
		}
	}
	

	/**
	 * Sets the cash income statements service.
	 *
	 * @param cashIncomeStatementsService the new cash income statements service
	 */
	public void setCashIncomeStatementsService(
			ICashIncomeStatementsService cashIncomeStatementsService) {
		this.cashIncomeStatementsService = cashIncomeStatementsService;
	}

	/**
	 * Gets the vo.
	 *
	 * @return vo
	 */
	public CashIncomeStatementsVo getVo() {
		return vo;
	}

	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(CashIncomeStatementsVo vo) {
		this.vo = vo;
	}

}

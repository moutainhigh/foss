package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashIncomeStatementsService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CashIncomeStatementsVo;
import com.deppon.foss.util.DateUtils;

/**
 * 导出实时收入明细报表
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-9 下午8:18:13
 * @since
 * @version
 */
public class CashIncomeStatementsExportExeclAction extends AbstractAction {
	private final Logger logger = LoggerFactory
			.getLogger(CashIncomeStatementsExportExeclAction.class);

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1931743005704906356L;

	/**
	 * 现金收入明细报表Service
	 */
	private ICashIncomeStatementsService cashIncomeStatementsService;
	
	

	private CashIncomeStatementsVo vo;

	/**
	 * 导出输出流
	 */
	private ByteArrayInputStream inputStream;

	/**
	 * Excel名称
	 */
	private String execlName;
	
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	private IProductService  productService;
	
	/**
	 * 导出现金收入明细报表
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午8:14:53
	 * @return
	 */
	public String cashIncomeStatementsExportExcel() {
		try {
			// vo对象不为空和 查询类型不能为空
			if (vo == null || StringUtils.isEmpty(vo.getQueryType())
					|| vo.getDto() == null) {
				return returnError("查询参数不能为空！");
			}
			CashIncomeStatementsQueryDto dto = vo.getDto();
			CashIncomeStatementsResultDto resultDto = null;
			
			//设置当前操作者编码
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			dto.setEmpCode(currentInfo.getEmpCode());

			// 按照日期查询
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(vo.getQueryType())) {
				
				if (dto.getStartDate() == null || dto.getEndDate() == null) {
					return returnError("日期参数不能为空！");
				}

				// Long i 表示开始日期和结束日期相差的天数
				Long i = DateUtils.getTimeDiff(DateUtils.convert(
						dto.getStartDate(), DateUtils.DATE_FORMAT), DateUtils
						.convert(dto.getEndDate(), DateUtils.DATE_FORMAT));

				//日期最大查询范围， 默认为7天
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
				else if (SettlementConstants.TAB_DATE_TYPE_FOR_CONFIRM
						.equals(dto.getDateType())) {
					dto.setCashConfirmStartDate(dto.getStartDate());
					dto.setCashConfirmEndDate(dto.getEndDate());
				}

				// 按照单据类型进行查询时
				if (StringUtils.isNotEmpty(dto.getBillType())) {

					// 选择单据类型为现金收款单时
					if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION
							.equals(dto.getBillType())) {
						resultDto = this.cashIncomeStatementsService
								.queryBillCashByCondition(
										dto,
										SettlementConstants.EXPORT_EXCEL_START_NUMBER,
										SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
					}
					// 选择单据类型为预收单时
					else if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
							.equals(dto.getBillType())) {
						resultDto = this.cashIncomeStatementsService
								.queryBillDepositReceivedByCondition(
										dto,
										SettlementConstants.EXPORT_EXCEL_START_NUMBER,
										SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
					}
					// 选择单据类型为还款时
					else if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT
							.equals(dto.getBillType())) {
						resultDto = this.cashIncomeStatementsService
								.queryBillRepaymentByCondition(
										dto,
										SettlementConstants.EXPORT_EXCEL_START_NUMBER,
										SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
					}
				} else {// 未选择单据类型，默认查询三种单据（现金收款单/还款单/预收单）
					resultDto = this.cashIncomeStatementsService
							.queryByCondition(dto, SettlementConstants.EXPORT_EXCEL_START_NUMBER,
									SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
				}
			}
			// 按照对账单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(vo
					.getQueryType())) {
				if (CollectionUtils.isEmpty(dto.getStatementBillNos())) {
					return returnError("对账单号不能为空");
				}
				resultDto = this.cashIncomeStatementsService
						.queryBillRepaymentByStatementBillNOs(dto);
			}
			// 按照运单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(vo
					.getQueryType())) {
				if (CollectionUtils.isEmpty(dto.getWaybillNos())) {
					return returnError("运单号不能为空");
				}
				resultDto = this.cashIncomeStatementsService
						.queryByWaybillNos(dto);
			}
			// 按照预收单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO
					.equals(vo.getQueryType())) {
				if (CollectionUtils.isEmpty(dto.getDepositReceivedNos())) {
					return returnError("预收单号不能为空");
				}
				resultDto = this.cashIncomeStatementsService
						.queryBillDepositReceivedByNOs(dto);
			}

			if (resultDto == null
					|| CollectionUtils.isEmpty(resultDto.getList())) {
				return returnError("导出数据为空！");
			}

			// 生成Excel代码
			try {
				StringBuffer orgName = new StringBuffer("");

				// 收款部门编码不为空时
				if (StringUtils.isNotEmpty(dto.getCollectionOrgName())) {
					orgName.append(SettlementConstants.CASH_COLLECTION_ORG_NAME
							+ SettlementConstants.CHINESE_COLON
							+ dto.getCollectionOrgName());
				} else if (StringUtils.isNotEmpty(dto.getGeneratingOrgName())) {
					// 收入部门编码不为空时
					orgName.append(SettlementConstants.GENERATION_ORG_NAME
							+ SettlementConstants.CHINESE_COLON
							+ dto.getGeneratingOrgName());
				}

				// 日期不为空
				if (dto.getStartDate() != null && dto.getEndDate() != null) {
					
					dto.setEndDate(DateUtils.addDayToDate(dto.getEndDate(), -1));
				
					
					orgName.append(DateUtils.convert(dto.getStartDate(),
							DateUtils.DATE_FORMAT)
							+ SettlementConstants.EXCEL_DATE_TIME_MERGE_OPERATOR
							+DateUtils.convert(dto.getEndDate(),DateUtils.DATE_FORMAT));
				} else {
					orgName.append(DateUtils.convert(new Date(),
							DateUtils.DATE_FORMAT));
				}
				// 设置Excel名称
				orgName.append(SettlementConstants.CASH_INCOM_STATEMENTS_EXCEL_NAME);
				setExeclName(new String(orgName.toString().getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
				
			} catch (UnsupportedEncodingException e) {
				logger.error(e.toString(), e);
				return returnError("导出Excel失败");
			}
			ExcelExport export = new ExcelExport();
			// 设置每次最多导出10万条数据
			HSSFWorkbook work = export.exportExcel(
					fillSheetDataByCashIncomeStatements(resultDto),
					SettlementConstants.EXCEL_SHEET_NAME,
					SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				work.write(baos);
				inputStream = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				logger.error(e.toString(), e);
				return returnError("导出Excel失败");
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (IOException e) {
						logger.error(e.toString(), e);
						return returnError("导出Excel失败");
					}
				}
			}// 生成Excel
		} catch (BusinessException e) {
			logger.error(e.toString(), e);
			return returnError("导出参数不能为空！");
		}
		return returnSuccess();
	}

	/**
	 * 填充Excel(现金收入明细报表)
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 上午8:29:56
	 * @param resultDto
	 * @return
	 */
	private SheetData fillSheetDataByCashIncomeStatements(
			CashIncomeStatementsResultDto resultDto) {
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "记账日期", "单据编号","运输性质", "收款部门", "收入部门",
				"运单号", "单据类别", "单据状态", "付款方式", "金额", "收银员", "收银时间" });
		
		List<CashIncomeStatementsDto> lists = resultDto.getList();
		if (CollectionUtils.isNotEmpty(lists)) {
			List<String> types=new ArrayList<String>();
			types.add(DictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);// 单据类型
			types.add(DictionaryConstants.BILL_REPAYMENT__STATUS);// 单据状态
			types.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);// 付款方式
			
			//后台获取数据字典对应的数据
			Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(types);
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (CashIncomeStatementsDto dto : lists) {
				List<String> cellList = new ArrayList<String>();
				cellList.add(DateUtils.convert(dto.getAccountDate(),
						DateUtils.DATE_TIME_FORMAT));// 记账日期
				cellList.add(dto.getBillNo());// 单据编号
				ProductEntity productEntity =  productService.getProductByCache(dto.getProductCode(),new Date());
				cellList.add(productEntity==null?"":productEntity.getName());// 运输性质
				cellList.add(dto.getCollectionOrgName());// 收款部门
				cellList.add(dto.getGeneratingOrgName());// 收入部门
				cellList.add(dto.getWaybillNo());// 运单号
				cellList.add( SettlementUtil.getDataDictionaryByTermsName(map, 
						DictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE,dto.getBillType()));
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, 
						DictionaryConstants.BILL_REPAYMENT__STATUS,dto.getStatus()));
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map, 
						DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,dto.getPaymentType()));
				cellList.add(dto.getAmount() != null ? dto.getAmount() + ""
						: "");// 金额
				cellList.add(dto.getCashConfirmUserName());//收银确认人
				cellList.add(DateUtils.convert(dto.getCashConfirmTime(),
						DateUtils.DATE_TIME_FORMAT));// 收银确认时间
				rowList.add(cellList);
			}
			sheetData.setRowList(rowList);
		}
		return sheetData;
	}
	
	/**
	 * @param cashIncomeStatementsService
	 */
	public void setCashIncomeStatementsService(
			ICashIncomeStatementsService cashIncomeStatementsService) {
		this.cashIncomeStatementsService = cashIncomeStatementsService;
	}

	/**
	 * @return vo
	 */
	public CashIncomeStatementsVo getVo() {
		return vo;
	}

	/**
	 * @param vo
	 */
	public void setVo(CashIncomeStatementsVo vo) {
		this.vo = vo;
	}

	/**
	 * @return inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return execlName
	 */
	public String getExeclName() {
		return execlName;
	}

	/**
	 * @param execlName
	 */
	public void setExeclName(String execlName) {
		this.execlName = execlName;
	}

	/**
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

}

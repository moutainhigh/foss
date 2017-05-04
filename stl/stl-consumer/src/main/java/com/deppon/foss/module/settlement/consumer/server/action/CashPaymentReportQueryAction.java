package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashPaymentReportQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashPaymentReportDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CashPaymentReportVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.NumberUtils;

/**
 * 现金支出报表Action
 * 
 * @author foss-zhangxiaohui
 * @date Dec 11, 2012 4:29:57 PM
 */
public class CashPaymentReportQueryAction extends AbstractAction {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 199048059141874455L;

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CashPaymentReportQueryAction.class);

	/**
	 * 声明生成的Excel文件的名称
	 */
	private static final String EXPORT_EXCEL_NAME = "现金支出报表";

	/**
	 * 应收单VO
	 */
	private CashPaymentReportVo cashPaymentReportVo;

	/**
	 * 付款单Entity 查询Service
	 */
	private ICashPaymentReportQueryService cashPaymentReportQueryService;

	/**
	 * 导出excel名称
	 */
	private String excelName;

	/**
	 * 导出流
	 */
	private ByteArrayInputStream inputStream;

	/**
	 * 
	 * 导出现金支出报表
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-27 下午8:08:59
	 */
	public String exportReport() {
		try {
			// 查询的参数不能为空
			if (null == cashPaymentReportVo
					|| null == cashPaymentReportVo.getDto()) {
				// 查询的参数对象Dto为空则抛出异常
				throw new SettlementException("查询条件不能为空");
			}
			// 开始日期非空校验
			if (null == cashPaymentReportVo.getDto().getStartDate()) {
				// 开始日期为空则抛出异常
				throw new SettlementException("开始日期不能为空");
			}
			// 结束日期非空校验
			if (null == cashPaymentReportVo.getDto().getEndDate()) {
				// 结束日期为空则抛出异常
				throw new SettlementException("结束日期不能为空");
			}
			// 判断开始日期是否小于结束日期
			if (cashPaymentReportVo.getDto().getStartDate() != null
					&& cashPaymentReportVo.getDto().getEndDate() != null) {
				Date startDateTemp = DateUtils.truncate(cashPaymentReportVo
						.getDto().getStartDate(), Calendar.SECOND);
				Date endDateTemp = DateUtils.truncate(cashPaymentReportVo
						.getDto().getEndDate(), Calendar.SECOND);
				// 判断开始日期是否在结束日期之后
				if (startDateTemp.after(endDateTemp)) {
					// 如果开始日期在结束日期之后则抛出异常
					throw new SettlementException("开始日期大于结束日期！");
				}
			}
			// 获取参数并赋值给Dto
			CashPaymentReportDto dto = cashPaymentReportVo.getDto();
			// 给结束日期加一天
			//Date endDateTemp = DateUtils.addDays(dto.getEndDate(), 1);
			//dto.setEndDate(endDateTemp);

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 再查询符合条件的当前页的数据
			List<BillPaymentEntity> billList = cashPaymentReportQueryService
					.queryCashPaymentReportByDto(0, Integer.MAX_VALUE,
							cashPaymentReportVo.getDto(), cInfo);
			// 判断查询结果是否为空
			if (CollectionUtils.isNotEmpty(billList)) {
				// 判断导出结果的数量
				if (billList.size() > SettlementConstants.EXPORT_MAX_COUNT) {
					throw new SettlementException("每次最多只能导出"
							+ SettlementConstants.EXPORT_MAX_COUNT
							+ "条数据,请重新查询并导出");
				}
				// 设置excel文件名
				try {
					// 设置Excel文件名称
					setExcelName(new String(EXPORT_EXCEL_NAME.getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
				}
				// 捕获异常
				catch (UnsupportedEncodingException e) {
					// 打印错误信息
					LOGGER.error("现金支出报表导出" + e.getMessage(), e);
					// 返回错误信息
					return returnError("文件名编码错误");
				}
				// 声明Excel导出对象
				ExcelExport exporter = new ExcelExport();
				// 声明一个工作簿对象
				HSSFWorkbook wb = exporter
						.exportExcel(toSheetDate(billList), EXPORT_EXCEL_NAME,
								SettlementConstants.EXPORT_MAX_COUNT);
				// 声明输出流对象
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					// 将查询结果写入输出流中
					wb.write(baos);
					// 把输出流转换成字节流数组赋值给输入流
					inputStream = new ByteArrayInputStream(baos.toByteArray());
				} catch (IOException e) {
					LOGGER.error("现金收入报表导出" + e.getMessage(), e);
				} finally {
					// 关闭流
					try {
						baos.close();
					} catch (IOException e) {
						LOGGER.error("现金支出报表导出" + e.getMessage(), e);
					}
				}
			}
		}
		// 捕获业务异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("现金支出报表导出" + e.getMessage(), e);
			// 返回错误信息
			return returnError("现金支出报表导出" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 
	 * 将业务数据转换成需要导出excel的格式
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-27 下午8:25:28
	 */
	private SheetData toSheetDate(List<BillPaymentEntity> billList) {
		// 声明SheetData对象
		SheetData sheetData = new SheetData();
		// 设置要生产的Excel表头
		String[] rowHeaders = { "付款单号", "付款公司", "付款部门", "会计日期", "收款客户", "金额",
				"付款方式", "单据子类型", "单据状态"};
		sheetData.setRowHeads(rowHeaders);
		// 设置需要导出的excel内容
		if (CollectionUtils.isNotEmpty(billList)) {
			List<List<String>> rowList = new ArrayList<List<String>>();
			List<String> cellList = null;
			for (BillPaymentEntity bill : billList) {
				cellList = new ArrayList<String>();

				// 付款单号、付款公司、付款部门
				cellList.add(bill.getPaymentNo());
				cellList.add(bill.getPaymentCompanyName());
				cellList.add(bill.getPaymentOrgName());

				// 会计日期、收款客户、金额
				cellList.add(com.deppon.foss.util.DateUtils.convert(
						bill.getAccountDate(),
						com.deppon.foss.util.DateUtils.DATE_TIME_FORMAT));
				cellList.add(bill.getPayeeName());
				cellList.add(bill.getAmount() == null ? null : bill.getAmount()
						.toString());

				// 付款方式、单据子类型、单据状态
				cellList.add(changePaymentTypeToName(bill.getPaymentType()));
				//转换并添加单据子类型
				cellList.add(changeBillTypeToName(bill.getSourceBillType()));
				//转换并添加单据状态
				cellList.add(changeRemitStatusToName(bill.getRemitStatus()));

				// 放置到rowList中
				rowList.add(cellList);
			}

			// 设置excel导出内容
			sheetData.setRowList(rowList);
		}

		return sheetData;
	}

	/**
	 * 单据类型转换
	 * 
	 * @author foss-zhangxiaohui
	 * @date 2013-4-2 下午7:20:42
	 */
	private String changeBillTypeToName(String billType){
		//判断是否是应付单
		if(StringUtils.equals(billType, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT)){
			//返回应付单
			return "应付单";
		}
		//判断是否是预收单
		else if(StringUtils.equals(billType, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED)){
			//返回预收单
			return "预收单";
		}
		//判断是否是对账单
		else if(StringUtils.equals(billType, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__STATEMENT)){
			//返回对账单
			return "对账单";
		}
		//判断是否是外发单
		else if(StringUtils.equals(billType, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__PARTIAL_LINE)){
			//返回外发单
			return "外发单";
		}
		else{
			//返回未知单据子类型
			return "未知单据子类型";
		}
	}
	
	/**
	 * 汇款状态转换
	 * 
	 * @author foss-zhangxiaohui
	 * @date Mar 20, 2013 2:35:33 PM
	 */
	private String changeRemitStatusToName(String remitStatus){
		//判断是否是未汇款
		if(StringUtils.equals(remitStatus, SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER)){
			//返回未汇款
			return "未汇款";
		}
		//判断是否是汇款中
		else if(StringUtils.equals(remitStatus, SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING)){
			//返回汇款中
			return "汇款中";
		}
		//判断是否是已汇款
		else if(StringUtils.equals(remitStatus, SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED)){
			//返回已汇款
			return "已汇款";
		}
		else{
			//返回位置汇款状态
			return "未知汇款状态";
		}
	}
	
	/**
	 * 付款方式编码转换
	 * 
	 * @author foss-zhangxiaohui
	 * @param 
	 * @date Jan 9, 2013 11:03:03 AM
	 * @return 
	 */
	private String changePaymentTypeToName(String paymentType) {
		//判断是否是现金
		if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH)) {
			//返回现金
			return "现金";
		} 
		//判断是否是银行卡
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD)) {
			//返回银行卡
			return "银行卡";
		}
		//判断是否是电汇
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)) {
			//返回电汇
			return "电汇";
		}
		//判断是否是支票
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE)) {
			//返回支票
			return "支票";
		}
		//判断是否是网上支付
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE)) {
			//返回网上支付
			return "网上支付";
		}
		//判断是否是月结
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT)) {
			//返回月结
			return "月结";
		}
		//判断是否是临时欠款
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT)) {
			//返回临时欠款
			return "临时欠款";
		}
		//判断是否是到付
		else if (StringUtils.equals(paymentType, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT)) {
			//返回到付
			return "到付";
		}
		//不符合所有类型则返回为未知
		else {
			return "未知收款类别";
		}
	}
	
	/**
	 * 现金支出报表Action--根据Dto查询现金支出报表
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 11, 2012 4:35:06 PM
	 */
	@JSON
	public String queryCashPaymentReportByDto() {
		try {
			// 查询的参数不能为空
			if (null == cashPaymentReportVo
					|| null == cashPaymentReportVo.getDto()) {
				// 查询的参数对象Dto为空则抛出异常
				throw new SettlementException("查询条件不能为空");
			}
			// 开始日期非空校验
			if (null == cashPaymentReportVo.getDto().getStartDate()) {
				// 开始日期为空则抛出异常
				throw new SettlementException("开始日期不能为空");
			}
			// 结束日期非空校验
			if (null == cashPaymentReportVo.getDto().getEndDate()) {
				// 结束日期为空则抛出异常
				throw new SettlementException("结束日期不能为空");
			}
			// 判断开始日期是否小于结束日期
			if (cashPaymentReportVo.getDto().getStartDate() != null
					&& cashPaymentReportVo.getDto().getEndDate() != null) {
				Date startDate = DateUtils.truncate(cashPaymentReportVo
						.getDto().getStartDate(), Calendar.SECOND);
				Date endDate = DateUtils.truncate(cashPaymentReportVo.getDto()
						.getEndDate(), Calendar.SECOND);
				// 判断开始日期是否在结束日期之后
				if (startDate.after(endDate)) {
					// 如果开始日期在结束日期之后则抛出异常
					throw new SettlementException("开始日期大于结束日期！");
				}
			}
			// 获取参数并赋值给Dto
			CashPaymentReportDto dto = cashPaymentReportVo.getDto();
			// 给结束日期加一天
			//Date endDate = DateUtils.addDays(dto.getEndDate(), 1);
			//dto.setEndDate(endDate);

			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();

			// 页面参数Dto不为空则先查询符合条件的数据库记录总条数
			List<CashPaymentReportDto> dtoList = cashPaymentReportQueryService
					.queryTotalRecordsInDB(dto,cInfo);
			// 如果查询的符合条件的返回结果为空则抛出异常
			if (CollectionUtils.isNotEmpty(dtoList)) {
				//声明现金条数
				long totalDtoCount = 0;
				// 声明现金总金额
				BigDecimal totalCashAmount = null;
				// 声明电汇总金额
				BigDecimal totalTelegraphicAmount = null;
				// 声明银行卡总金额
				BigDecimal totalBankCardAmount = null;
				// 声明支票总金额
				BigDecimal totalChequeAmount = null;
				// 审核总金额
				BigDecimal totalAuditAmount = null;
				// 未审核总金额
				BigDecimal totalUnauditAmount = null;
				// 查询总记录条数不为零的话继续遍历查询结果得到总金额
				for (int i = 0; i < dtoList.size(); i++) {
					// 当付款方式为现金时
					if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
							.equals(dtoList.get(i).getPaymentType())) {
						// 计算按现金付款的总金额并赋值到Vo
						totalCashAmount = NumberUtils.add(totalCashAmount,
								dtoList.get(i).getTotalAmount());
					}
					// 当付款方式为银行卡时
					if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD
							.equals(dtoList.get(i).getPaymentType())) {
						// 计算按银行卡付款的总金额并赋值到Vo
						totalBankCardAmount = NumberUtils.add(
								totalBankCardAmount, dtoList.get(i)
										.getTotalAmount());
					}
					// 当付款方式为电汇时
					if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
							.equals(dtoList.get(i).getPaymentType())) {
						// 计算按电汇付款的总金额并赋值到Vo
						totalTelegraphicAmount = NumberUtils.add(
								totalTelegraphicAmount, dtoList.get(i)
										.getTotalAmount());
					}
					// 当付款方式为支票时
					if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE
							.equals(dtoList.get(i).getPaymentType())) {
						// 计算按支票付款的总金额并赋值到Vo
						totalChequeAmount = NumberUtils.add(totalChequeAmount,
								dtoList.get(i).getTotalAmount());
					}
					// 当单据状态为审核时来计算审核总金额
					if (SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE
							.equals(dtoList.get(i).getAuditStatus())) {
						// 计算按现金付款的总金额并赋值到Vo
						totalAuditAmount = NumberUtils.add(totalAuditAmount,
								dtoList.get(i).getTotalAmount());
					}
					// 当单据状态为未审核时来计算审核总金额
					if (SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT
							.equals(dtoList.get(i).getAuditStatus())) {
						totalUnauditAmount = NumberUtils.add(
								totalUnauditAmount, dtoList.get(i)
										.getTotalAmount());
					}
					
					//累加总条数
					totalDtoCount = totalDtoCount+dtoList.get(i).getTotalRecordsInDB();
					
				}
				// 设置数据库取出来的数据总条数
				setTotalCount(Long.valueOf(totalDtoCount));
				// 计算按现金付款的总金额并赋值到Vo
				cashPaymentReportVo.setTotalCashAmount(totalCashAmount);
				// 计算按银行卡付款的总金额并赋值到Vo
				cashPaymentReportVo.setTotalBankCardAmount(totalBankCardAmount);
				// 计算按电汇付款的总金额并赋值到Vo
				cashPaymentReportVo
						.setTotalTelegraphicAmount(totalTelegraphicAmount);
				// 计算按支票付款的总金额并赋值到Vo
				cashPaymentReportVo.setTotalChequeAmount(totalChequeAmount);
				// 计算按现金付款的审核总金额并赋值到Vo
				cashPaymentReportVo.setTotalAuditAmount(totalAuditAmount);
				// 计算按现金付款的未审核总金额并赋值到Vo
				cashPaymentReportVo.setTotalUnauditAmount(totalUnauditAmount);
				// 再查询符合条件的当前页的数据
				List<BillPaymentEntity> list = cashPaymentReportQueryService
						.queryCashPaymentReportByDto(getStart(), getLimit(),
								cashPaymentReportVo.getDto(),cInfo);
				// 判断查询出来的数据是否为空
				if (CollectionUtils.isNotEmpty(list)) {
					// 把查询结果返回页面
					cashPaymentReportVo.setBillPaymentList(list);
				}
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 输出打印的错误信息
			LOGGER.error("现金支出报表查询" + e.getMessage(), e);
			// 返回错误信息
			return returnError(e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * @return cashPaymentReportVo
	 */
	public CashPaymentReportVo getCashPaymentReportVo() {
		return cashPaymentReportVo;
	}

	/**
	 * @param cashPaymentReportVo
	 */
	public void setCashPaymentReportVo(CashPaymentReportVo cashPaymentReportVo) {
		this.cashPaymentReportVo = cashPaymentReportVo;
	}

	/**
	 * @param cashPaymentReportQueryService
	 */
	public void setCashPaymentReportQueryService(
			ICashPaymentReportQueryService cashPaymentReportQueryService) {
		this.cashPaymentReportQueryService = cashPaymentReportQueryService;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	/**
	 * @return inputStream
	 */
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param  inputStream  
	 */
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
}

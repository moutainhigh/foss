/**
 * Copyright 2013 STL TEAM
 */
package com.deppon.foss.module.settlement.consumer.server.action;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableMonthlyQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.BillReceivableVo;
import com.deppon.foss.util.DateUtils;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

/**
 * 查询应收单月结
 * @author foss-youkun
 * @date 2016/1/8
 */
public class BillReceivableMonthlyQueryAction extends AbstractAction {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(BillReceivableMonthlyQueryAction.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	//应收单Vo
	private BillReceivableVo billReceivableVo ;

	private IBillReceivableMonthlyQueryService billReceivableMonthlyQueryService;
	
	/**
	 * excel名字
	 */
	private String excelName;
	/**
     * 输出流
     */
    private InputStream inputStream;
    /**
     * 导出文件名
     */
	private static final String EXCELNAME = "月结应收单";


	/**
	 * 查询月结应收单
	 * @return
	 */
	@JSON
	public String billReceivableMonthlySelect(){
		//判断用户是否能查询合伙人营业部月结金额(备用)
//		billReceivableMonthlyQueryService.judgeDepartmentPermission(billReceivableVo.getDto());
		//声明billReceivableEntityList
		List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();
		//声明总记录数
		Long count = 0L;
		//总金额
		String amount=null;
		//总记录数
		try{
			count = billReceivableMonthlyQueryService.countBillReceivableByData(billReceivableVo.getDto());
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			return returnError(e.getMessage());
		}
		//页面总记录条数
		setTotalCount(count);
		//如果总记录数为零就直接返回不用查询了
		if(count<=0){
			billReceivableVo.setBillReceivableList(billReceivableEntityList);
			return returnSuccess();
		}

		//如果总记录数不为零就查询
		try{
			billReceivableEntityList = billReceivableMonthlyQueryService.queryBillReceivableByData(this.getStart(), this.getLimit(), billReceivableVo.getDto());
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			return returnError(e.getMessage());
		}
		//计算总金额返回
		try{
			amount = billReceivableMonthlyQueryService.amountBillReceivableByParam(billReceivableVo.getDto());
		}catch (SettlementException e){
			LOGGER.error(e.getMessage());
			return returnError(e.getMessage());
		}
		if(amount!=null&&!("").equals(amount)){
			billReceivableVo.setTotalAmount(new BigDecimal(amount));
		}
		billReceivableVo.setBillReceivableList(billReceivableEntityList);
		return returnSuccess();
	}
	
	/**
	 * 导出月结应收单方法
	 * @return
	 */
	@JSON
	public String billRecivableMonthlyToExcel() {
		if ((this.billReceivableVo == null)
				|| (this.billReceivableVo.getDto() == null)) {
			returnError("导出参数不能为空");
		}
		//获取类头名称
		String[] header = this.billReceivableVo.getDto().getArrayColumns();
		//获取获取列头中文名
		String[] headerName = this.billReceivableVo.getDto()
				.getArrayColumnNames();
		try {
			try {
				// 设置导出excel名称
				this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage());
				return returnError("导出excel失败!");
			}
			//查询月结应收单记录
			List<BillReceivableEntity> billReceivableList;
			try {
				billReceivableList = this.billReceivableMonthlyQueryService
						.queryBillReceivableEntityListForExport(this.billReceivableVo
								.getDto());
			} catch (SettlementException e) {
				LOGGER.error(e.getMessage());
				return returnError(e.getMessage());
			}
			// 导出格式数据
			ExportResource exportResource = importExportResourceXLS(
					billReceivableList, header, headerName);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();
			// 输出到导出流中
			this.inputStream = executor.exportSync(exportResource,
					exportSetting);
		} catch (SettlementException e) {
			// 记录日志并返回失败
			LOGGER.error(e.getMessage());
			return returnError("导出月结应收单异常:" + e.getMessage());
		}
		return returnSuccess();
	}
	/**
	 * 
	 * @param list
	 * @param header
	 * @param headerName
	 * @return
	 */
	private ExportResource importExportResourceXLS(
			List<BillReceivableEntity> list, String[] header,
			String[] headerName) {
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		// 循环进行封装
		for (BillReceivableEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(
						BillReceivableEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					if(fieldValue != null){
						// 如果为日期，需要进行格式化
						if(Date.class.equals(field.getType())){
							// 日期转化
							fieldValue = DateUtils.convert((Date) fieldValue,
									"yyyy-MM-dd HH:mm:ss");
						}
						if (columnName != null) {
							// 处理付款方式
							if ((columnName.equals("paymentType"))
									&& (StringUtils.isNotEmpty(fieldValue
											.toString()))) {
								fieldValue = convert(fieldValue);
							}
							// 处理运输性质
							if ((columnName.equals("productCode"))
									&& (StringUtils.isNotEmpty(fieldValue
											.toString()))) {
								fieldValue = convert(fieldValue);
							}
							// 处理货款状态
							if ((columnName.equals("withholdStatus"))
									&& (StringUtils.isNotEmpty(fieldValue
											.toString()))) {
								fieldValue = convert(fieldValue);
							}
							// 处理单据子类型
							if ((columnName.equals("billType"))
									&& (StringUtils.isNotEmpty(fieldValue
											.toString()))) {
								fieldValue = convert(fieldValue);
							}
						}
						rowList.add(fieldValue.toString());
					}else{
						rowList.add("");
					}
				}
			}
			sheetList.add(rowList);
		}
		// 封装数据
		data.setRowList(sheetList);
		data.setHeads(headerName);
		return data;
	}
	/**
	 * 转换函数
	 * 作用：将传入的值进行转换
	 * @param value
	 * @return
	 */
	private Object convert(Object value) {
		if ("CT".equals(value))
			value = "月结";
		if ("AF".equals(value))
			value = "精准空运";
		if ("FLF".equals(value))
			value = "精准卡航";
		if ("FSF".equals(value))
			value = "精准城运";
		if ("LRF".equals(value))
			value = "精准汽运(长途)";
		if ("PLF".equals(value))
			value = "汽运偏线";
		if ("SRF".equals(value))
			value = "精准汽运(短途)";
		if ("WVH".equals(value))
			value = "整车（三级）";
		if ("PACKAGE".equals(value))
			value = "标准快递";
		if ("RCP".equals(value))
			value = "3.60特惠件";
		if ("BGFLF".equals(value))
			value = "精准大票卡航";
		if ("BGLRF".equals(value))
			value = "精准大票汽运(长)";
		if ("BGFSF".equals(value))
			value = "精准大票城运";
		if ("BGSRF".equals(value))
			value = "精准大票汽运(短)";
		if ("DTD".equals(value))
			value = "精准大票.经济件";
		if ("YTY".equals(value))
			value = "精准大票.标准件";
		if ("EPEP".equals(value))
			value = "电商尊享";
		if ("DEAP".equals(value))
			value = "商务专递";
		if ("UWH".equals(value))
			value = "未扣款";
		if ("WHS".equals(value))
			value = "扣款成功";
		if ("WHF".equals(value))
			value = "扣款失败";
		if ("POR".equals(value))
			value = "始发提成";
		if ("PDFR".equals(value))
			value = "委托派费";
		return value;
	}

	public void setBillReceivableMonthlyQueryService(
			IBillReceivableMonthlyQueryService billReceivableMonthlyQueryService) {
		this.billReceivableMonthlyQueryService = billReceivableMonthlyQueryService;
	}

	public BillReceivableVo getBillReceivableVo() {
		return this.billReceivableVo;
	}

	public void setBillReceivableVo(BillReceivableVo billReceivableVo) {
		this.billReceivableVo = billReceivableVo;
	}

	public String getExcelName() {
		return this.excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}

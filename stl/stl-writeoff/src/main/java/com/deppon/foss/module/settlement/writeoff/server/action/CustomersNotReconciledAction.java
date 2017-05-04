package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.writeoff.api.server.service.ICustomersNotReconciledService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomersNotReconciledEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomersNotReconciledDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.CustomersNotReconciledVo;

public class CustomersNotReconciledAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CustomersNotReconciledAction.class);
	private ICustomersNotReconciledService customersNotReconciledService;
	private CustomersNotReconciledVo customersNotReconciledVo;
	private static final String EXCELNAME = "未对账客户信息";
	private String excelName;
	private InputStream inputStream;
	
	public String queryCustomersNotReconciled(){
		CustomersNotReconciledDto customersNotReconciledDto = customersNotReconciledVo.getCustomersNotReconciledDto();
		List<CustomersNotReconciledEntity> list = customersNotReconciledService.queryCustomersNotReconciled(customersNotReconciledDto);
		customersNotReconciledDto.setCustomersNotReconciledList(list);
		return returnSuccess();
	}
	public String exportXLS(){
		// 查询参数不能为空
		if (customersNotReconciledVo == null) {
			return returnError("导出参数不能为空!");
		}
		// 获取类头名称
		String[] header = customersNotReconciledVo.getArrayColumns();
		// 获取列头中文名
		String[] headerName = customersNotReconciledVo.getArrayColumnNames();
		try {

			// 设置导出excel名称
			try {
				this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			CustomersNotReconciledDto queryDto = this.customersNotReconciledVo.getCustomersNotReconciledDto();
			// 给第结束日期加1天
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 将orgCode设置给dto
			queryDto.setEmpCode(currentInfo.getEmpCode());
			List<CustomersNotReconciledEntity> list = this.customersNotReconciledService.queryCustomersNotReconciled(queryDto);
			// 导出格式数据
			ExportResource exportResource = packExportResourceXLS(list,header,headerName);

			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
			
			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();

			// 输出到导出流中
			inputStream = executor.exportSync(exportResource, exportSetting);

		} catch (BusinessException e) {
			// 记录日志并返回失败
			logger.error(e.getMessage(),e);
			return returnError("导出对账单异常:" + e.getMessage());

		}

		return returnSuccess();
	}
	
	private ExportResource packExportResourceXLS(List<CustomersNotReconciledEntity> list,String[] header,String[] headerName){
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		// 循环进行封装
		for (CustomersNotReconciledEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(CustomersNotReconciledEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					// 将字段封装到list中
					if(columnName.equals("amountTotal")){
							fieldValue = entity.getAmountOneMonth().add(entity.getAmountTwoMonth().add(entity.getAmountThreeMonth()));
					}
					if (fieldValue != null) {
						rowList.add(fieldValue.toString());
					} else {
						rowList.add(null);
					}
				}
			}
			sheetList.add(rowList);
		}
		//封装数据
		data.setRowList(sheetList);
		data.setHeads(headerName);
		return data;
	}
	
	public void setCustomersNotReconciledService(
			ICustomersNotReconciledService customersNotReconciledService) {
		this.customersNotReconciledService = customersNotReconciledService;
	}
	public CustomersNotReconciledVo getCustomersNotReconciledVo() {
		return customersNotReconciledVo;
	}
	public void setCustomersNotReconciledVo(
			CustomersNotReconciledVo customersNotReconciledVo) {
		this.customersNotReconciledVo = customersNotReconciledVo;
	}
	public String getExcelName() {
		return excelName;
	}
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
}

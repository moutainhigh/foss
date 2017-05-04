package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IHomeStatementsService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeItemDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.HomeStatementsVo;
import com.deppon.foss.util.DateUtils;

public class HomeStatementsAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	private HomeStatementsVo vo;
	
	private IHomeStatementsService homeStatementsService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeStatementsAction.class);
	
	/**
	 * 家装对账单导出文件名字
	 */
	private static final String EXCELNAME = "家装对账单明细导出";
	/**
	 * excel名字
	 */
	private String excelName;
	/**
	 * 输出流
	 */
	private InputStream inputStream;
	
	/**
	 * 查询家装对账单
	 * 页面查询显示
	 */
	@JSON
	public String queryHome(){
		//获取前台参数
		HomeItemDto dto=vo.getHomedto();
		//判断查询页签，按客户查询，否则按应收应付单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//如果结束日期不为空，则需要对其进行+1操作
			if(dto.getPreDate() != null){
				//结束日期加1天
				dto.setPreDate(DateUtils.convert(DateUtils.addDay(dto.getPreDate(), 1)));
			}
		}
		//调用查询对账单接口
		HomeItemDto homeItemDto = homeStatementsService.queryHome(dto, this.getStart(), this.getLimit());
		//返回查询结果到前台
		vo.setHomedto(homeItemDto);
		//分页查询返回总行数
		this.setTotalCount(homeItemDto.getCount());
		return returnSuccess();
	}
	
	/**
	 * 双击对账单记录查看明细，按对账单单号查询
	 * @author 268217
	 */
	@JSON
	public String queryHomeStatementBillNo(){
		//获取前台参数
		HomeItemDto dto=vo.getHomedto();
		//调用查询对账单接口
		HomeItemDto homeItemDto = homeStatementsService.queryHomeStatementBillNo(dto, start, limit);
		//返回查询结果到前台
		vo.setHomedto(homeItemDto);
		//分页查询返回总行数
		this.setTotalCount(homeItemDto.getCount());
		return returnSuccess();
	}
	/**
	 * 家装对账单添加明细查询应收应付单
	 * @author 268217
	 */
	@JSON
	public String queryAddHomeByNo(){
		//获取前台参数
		HomeItemDto dto=vo.getHomedto();
		//调用查询对账单接口
		HomeItemDto homeItemDto = homeStatementsService.queryAddHomeByNo(dto, start, limit);
		//返回查询结果到前台
		vo.setHomedto(homeItemDto);
		//分页查询返回总行数
		this.setTotalCount(homeItemDto.getCount());
		return returnSuccess();
	}
	/**
	 * 家装对账单添加明细
	 * @author 268217
	 */
	@JSON
	public String addHomeStatement(){
		//获取前台参数
		HomeItemDto dto=vo.getHomedto();
		try {
			//调用添加明细接口
			HomeItemDto homeItemDto=homeStatementsService.addHomeStatementD(dto);
			//返回金额、对账单单据类型等数据
			vo.setHomedto(homeItemDto);
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * 家装对账单删除明细查询应收应付单
	 * @author 268217
	 */
	@JSON
	public String queryDelHomeByNo(){
		//获取前台参数
		HomeItemDto dto=vo.getHomedto();
		//调用查询对账单接口
		HomeItemDto homeItemDto = homeStatementsService.queryDelHomeByNo(dto, start, limit);
		//返回查询结果到前台
		vo.setHomedto(homeItemDto);
		//分页查询返回总行数
		this.setTotalCount(homeItemDto.getCount());
		return returnSuccess();
	}
	/**
	 * 家装对账单删除明细
	 * @author 268217
	 */
	@JSON
	public String delHomeStatement(){
		//获取前台参数
		HomeItemDto dto=vo.getHomedto();
		try {
			//调用添加明细接口
			HomeItemDto homeItemDto=homeStatementsService.delHomeStatement(dto);
			//返回金额、对账单单据类型等数据
			vo.setHomedto(homeItemDto);
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 确认家装对账单
	 * @author 268217
	 */
	@JSON
	public String confirmWoodenStatement(){
		//获取前台参数
		HomeItemDto dto=vo.getHomedto();
		try {
			//调用添加明细接口
			homeStatementsService.confirmWoodenStatement(dto);
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 根据对账单号查询付款单明细
	 * @author 268217
	 */
	/*@JSON
	public String queryPayment(){
		//获取前台参数
		HomeItemDto dto=vo.getHomedto();
		try {
			//调用添加明细接口
			HomeItemDto homeItemDto=homeStatementsService.queryHomePayment(dto);
			//返回金额、对账单单据类型等数据
			vo.setHomedto(homeItemDto);
			return returnSuccess();
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return returnError(e);
		}
	}*/
	
	/**
	 * 家装对账单导出对账单明细
	 * @author 268217
	 */
	@JSON
	public String homeExportXLS(){
		// 获取类头名称
		String[] header = vo.getArrayColumns();
		// 获取列头中文名
		String[] headerName = vo.getArrayColumnNames();
		try {
			// 查询参数不能为空
			if (vo == null) {
				return returnError("导出参数不能为空!");
			}
			try {
				// 设置导出excel名称
				this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			//获取前台参数
			HomeItemDto homeItemDto = this.vo.getHomedto();
			//查询对账单明细
			List<HomeStatementDEntity> list = this.homeStatementsService.queryByStatementBillNo(homeItemDto.getStatementBillNo());
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
	/**
	 * 家装对账单导出明细数据封装
	 * @author 268217
	 */
	private ExportResource packExportResourceXLS(List<HomeStatementDEntity> list,String[] header,String[] headerName){
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		// 循环进行封装
		for (HomeStatementDEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(HomeStatementDEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					if (fieldValue != null) {
						// 如果为日期，需要进行格式化
						fieldValue = validaHomeStatementDEntity(entity,
								columnName, field, fieldValue);
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

	private Object validaHomeStatementDEntity(HomeStatementDEntity entity,
			String columnName, Field field, Object fieldValue) {
		if (Date.class.equals(field.getType())) {
			//日期转化
			fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
		}
		if(columnName.equals("billType")){
			//如果数据产品类型编码不为空
			if(StringUtils.isNotEmpty(fieldValue.toString())){
				if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__HOME_IMPROVEMENT.equals(entity.getBillType())){
					fieldValue = "家装应付单";
				}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__HOME_IMPROVEMENT.equals(entity.getBillType())){
					fieldValue = "家装应收单";
				}else{
					fieldValue = entity.getBillType();
				}
			}
		}
		return fieldValue;
	}
	
	public HomeStatementsVo getVo() {
		return vo;
	}


	public void setVo(HomeStatementsVo vo) {
		this.vo = vo;
	}

	public void setHomeStatementsService(IHomeStatementsService homeStatementsService) {
		this.homeStatementsService = homeStatementsService;
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

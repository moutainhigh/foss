/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
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
import com.deppon.foss.module.settlement.writeoff.api.server.service.IWoodenStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.WoodenStatementDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.WoodenStatementVo;
import com.deppon.foss.util.DateUtils;

public class WoodenStatementAction extends AbstractAction  {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WoodenStatementAction.class);
	/*
	 * 代打木架对账单VO
	 */
	private WoodenStatementVo woodenStatementVo;
	/*
	 * 代打木架对账单服务
	 */
	private IWoodenStatementService woodenStatementService;
	/*
	 * 代打木架对账单导出文件名字
	 */
	private static final String EXCELNAME = "代打木架对账单明细";
	/*
	 * excel名字
	 */
	private String excelName;
	/*
	 * 输出流
	 */
	private InputStream inputStream;
	
	/**
	 * 代打木架对账单新增查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryWoodenStatementD(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		//判断查询页签，按客户查询，否则按应收应付单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(woodenStatementDto.getQueryTabType())){
			//如果结束日期不为空，则需要对其进行+1操作
			if(woodenStatementDto.getPeriodEndDate()!=null){
				//结束日期加1天
				woodenStatementDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(woodenStatementDto.getPeriodEndDate(), 1)));
			}
		}
		
		//调用查询应收应付单接口
		WoodenStatementDto dto = woodenStatementService.queryWoodenStatementD(woodenStatementDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		woodenStatementVo.setWoodenStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	/**
	 * 查询代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryWoodenStatement(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		//判断查询页签，按客户查询，否则按应收应付单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(woodenStatementDto.getQueryTabType())){
			//如果结束日期不为空，则需要对其进行+1操作
			if(woodenStatementDto.getPeriodEndDate()!=null){
				//结束日期加1天
				woodenStatementDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(woodenStatementDto.getPeriodEndDate(), 1)));
			}
		}
		//调用查询对账单接口
		WoodenStatementDto dto = woodenStatementService.queryWoodenStatement(woodenStatementDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		woodenStatementVo.setWoodenStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	/**
	 * 双击对账单记录查看明细，按对账单单号查询
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryWoodenDByStatementBillNo(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		//调用按对账单单号查询对账单明细接口
		WoodenStatementDto dto = woodenStatementService.queryWoodenDByStatementBillNo(woodenStatementDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		woodenStatementVo.setWoodenStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	/**
	 * 生成代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String woodenStatementSave(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		try {
			//判断查询页签，按客户查询，否则按应收应付单号查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(woodenStatementDto.getQueryTabType())){
				//如果结束日期不为空，则需要对其进行+1操作
				if(woodenStatementDto.getPeriodEndDate()!=null){
					//结束日期加1天
					woodenStatementDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(woodenStatementDto.getPeriodEndDate(), 1)));
				}
			}
			//调用对账单保存接口
			WoodenStatementDto dto = woodenStatementService.woodenStatementSave(woodenStatementDto);
			//保存成功返回对账单单号
			woodenStatementVo.setWoodenStatementDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	/**
	 * 确认代打木架对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String confirmWoodenStatement(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		try {
			//调用对账单确认接口
			woodenStatementService.confirmWoodenStatement(woodenStatementDto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	/**
	 * 代打木架对账单添加明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String addWoodenStatementD(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		try {
			//判断查询页签，按客户查询，否则按应收应付单号查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(woodenStatementDto.getQueryTabType())){
				//如果结束日期不为空，则需要对其进行+1操作
				if(woodenStatementDto.getPeriodEndDate()!=null){
					//结束日期加1天
					woodenStatementDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(woodenStatementDto.getPeriodEndDate(), 1)));
				}
			}
			//调用添加明细接口
			WoodenStatementDto dto = woodenStatementService.addWoodenStatementD(woodenStatementDto);
			//返回金额、对账单单据类型等数据
			woodenStatementVo.setWoodenStatementDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	/**
	 * 代打木架对账单删除明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String delWoodenStatementD(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		try {
			//调用删除明细接口
			WoodenStatementDto dto = woodenStatementService.delWoodenStatementD(woodenStatementDto);
			//返回金额、对账单单据类型等数据
			woodenStatementVo.setWoodenStatementDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	/**
	 * 代打木架对账单添加明细查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryAddWoodenStatementD(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		//调用添加对账单明细查询应收应付单
		WoodenStatementDto dto = woodenStatementService.queryAddWoodenStatementD(woodenStatementDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		woodenStatementVo.setWoodenStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	/**
	 * 代打木架对账单删除明细查询应收应付明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryDelWoodenStatementD(){
		//获取前台参数
		WoodenStatementDto woodenStatementDto = woodenStatementVo.getWoodenStatementDto();
		//调用删除对账单明细查询应收应付明细
		WoodenStatementDto dto = woodenStatementService.queryDelWoodenStatementD(woodenStatementDto);
		//返回查询结果到前台
		woodenStatementVo.setWoodenStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	/**
	 * 代打木架对账单导出对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String woodenExportXLS(){
		// 查询参数不能为空
		if (woodenStatementVo == null) {
			return returnError("导出参数不能为空!");
		}
		// 获取类头名称
		String[] header = woodenStatementVo.getArrayColumns();
		// 获取列头中文名
		String[] headerName = woodenStatementVo.getArrayColumnNames();
		try {
			
			try {
				// 设置导出excel名称
				this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			//获取前台参数
			WoodenStatementDto woodenStatementDto = this.woodenStatementVo.getWoodenStatementDto();
			//查询对账单明细
			List<WoodenStatementDEntity> list = this.woodenStatementService.queryByStatementBillNo(woodenStatementDto.getStatementBillNo());
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
	 * 代打木架对账单导出明细数据封装
	 * @author ddw
	 * @date 2014-06-16
	 */
	private ExportResource packExportResourceXLS(List<WoodenStatementDEntity> list,String[] header,String[] headerName){
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		// 循环进行封装
		for (WoodenStatementDEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(WoodenStatementDEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())&& fieldValue != null) {
						//日期转化
						fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
					}
					fieldValue = validaWoodenStatement(entity, columnName,
							fieldValue);
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
	private Object validaWoodenStatement(WoodenStatementDEntity entity,
			String columnName, Object fieldValue) {
		if(columnName.equals("packType")){
			//如果数据产品类型编码不为空
			if(StringUtils.isNotEmpty(fieldValue.toString())){
				if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MAIN_PACKING.equals(entity.getPackType())){
					fieldValue = "主要包裝";
				}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MINOR_PACKING.equals(entity.getPackType())){
					fieldValue = "辅助包裝";
				}else{
					fieldValue = entity.getPackType();
				}
			}
		}
		return fieldValue;
	}
	
	public WoodenStatementVo getWoodenStatementVo() {
		return woodenStatementVo;
	}

	public void setWoodenStatementVo(WoodenStatementVo woodenStatementVo) {
		this.woodenStatementVo = woodenStatementVo;
	}

	public void setWoodenStatementService(IWoodenStatementService woodenStatementService) {
		this.woodenStatementService = woodenStatementService;
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

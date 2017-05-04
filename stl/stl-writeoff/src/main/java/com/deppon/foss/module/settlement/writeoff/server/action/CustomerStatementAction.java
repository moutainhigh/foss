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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.service.ICustomerStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomerStatementDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.CustomerStatementVo;
import com.deppon.foss.util.DateUtils;

public class CustomerStatementAction extends AbstractAction  {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CustomerStatementAction.class);
	/*
	 * 客户对账单VO
	 */
	private CustomerStatementVo customerStatementVo;
	/*
	 * 客户对账单服务
	 */
	private ICustomerStatementService customerStatementService;
	/*
	 * 客户对账单导出文件名字
	 */
	private static final String EXCELNAME = "大客户对账单明细";
	/*
	 * excel名字
	 */
	private String excelName;
	/*
	 * 输出流
	 */
	private InputStream inputStream;
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService  productService;
	/**
	 * 客户对账单新增查询应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryCustomerStatementD(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		//判断查询页签，按客户查询，否则按应收应付单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(customerStatementDto.getQueryTabType())){
			//如果结束日期不为空，则需要对其进行+1操作
			if(customerStatementDto.getPeriodEndDate()!=null){
				//结束日期加1天
				customerStatementDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(customerStatementDto.getPeriodEndDate(), 1)));
			}
		}
		
		//调用查询应收应付单接口
		CustomerStatementDto dto = customerStatementService.queryCustomerStatementD(customerStatementDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		customerStatementVo.setCustomerStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	
	/**
	 * 生成客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String customerStatementSave(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		try {
			//判断查询页签，按客户查询，否则按来源单号查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(customerStatementDto.getQueryTabType())){
				//如果结束日期不为空，则需要对其进行+1操作
				if(customerStatementDto.getPeriodEndDate()!=null){
					//结束日期加1天
					customerStatementDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(customerStatementDto.getPeriodEndDate(), 1)));
				}
			}
			//调用对账单保存接口
			CustomerStatementDto dto = customerStatementService.customerStatementSave(customerStatementDto);
			//保存成功返回对账单单号
			customerStatementVo.setCustomerStatementDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	
	/**
	 * 查询客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryCustomerStatement(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		//判断查询页签，按客户查询，否则按应收应付单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(customerStatementDto.getQueryTabType())){
			//如果结束日期不为空，则需要对其进行+1操作
			if(customerStatementDto.getPeriodEndDate()!=null){
				//结束日期加1天
				customerStatementDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(customerStatementDto.getPeriodEndDate(), 1)));
			}
		}
		//调用查询对账单接口
		CustomerStatementDto dto = customerStatementService.queryCustomerStatement(customerStatementDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		customerStatementVo.setCustomerStatementDto(dto);
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
	public String queryCustomerDByStatementBillNo(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		//调用按对账单单号查询对账单明细接口
		CustomerStatementDto dto = customerStatementService.queryCustomerDByStatementBillNo(customerStatementDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		customerStatementVo.setCustomerStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	
	/**
	 * 确认或反确认对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String confirmOrUnConfirmStatement(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		try {
			//调用大客户对账单确认接口
			customerStatementService.confirmOrUnConfirmStatement(customerStatementDto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	
	/**
	 * 大客户对账单添加明细查询应收应付单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryAddCustomerStatementD(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		//调用添加对账单明细查询应收应付单
		CustomerStatementDto dto = customerStatementService.queryAddCustomerStatementD(customerStatementDto,this.getStart(),this.getLimit());
		//返回查询结果到前台
		customerStatementVo.setCustomerStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	
	/**
	 * 大客户对账单添加明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String addCustomerStatementD(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		try {
			//判断查询页签，按客户查询，否则按应收应付单号查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(customerStatementDto.getQueryTabType())){
				//如果结束日期不为空，则需要对其进行+1操作
				if(customerStatementDto.getPeriodEndDate()!=null){
					//结束日期加1天
					customerStatementDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(customerStatementDto.getPeriodEndDate(), 1)));
				}
			}
			//调用添加明细接口
			CustomerStatementDto dto = customerStatementService.addCustomerStatementD(customerStatementDto);
			//返回金额、对账单单据类型等数据
			customerStatementVo.setCustomerStatementDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	
	/**
	 * 大客户对账单删除明细查询应收应付明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String queryDelCustomerStatementD(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		//调用删除对账单明细查询应收应付明细
		CustomerStatementDto dto = customerStatementService.queryDelCustomerStatementD(customerStatementDto);
		//返回查询结果到前台
		customerStatementVo.setCustomerStatementDto(dto);
		//分页查询返回总行数
		this.setTotalCount(dto.getCount());
		
		return returnSuccess();
	}
	
	/**
	 * 大客户对账单删除明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String delCustomerStatementD(){
		//获取前台参数
		CustomerStatementDto customerStatementDto = customerStatementVo.getCustomerStatementDto();
		try {
			//调用删除明细接口
			CustomerStatementDto dto = customerStatementService.delCustomerStatementD(customerStatementDto);
			//返回金额、对账单单据类型等数据
			customerStatementVo.setCustomerStatementDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	
	/**
	 * 大客户对账单导出对账单明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@JSON
	public String customerExportXLS(){
		// 查询参数不能为空
		if (customerStatementVo == null) {
			return returnError("导出参数不能为空!");
		}
		// 获取类头名称
		String[] header = customerStatementVo.getArrayColumns();
		// 获取列头中文名
		String[] headerName = customerStatementVo.getArrayColumnNames();
		try {
			
			try {
				// 设置导出excel名称
				this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			//获取前台参数
			CustomerStatementDto custoemrStatementDto = this.customerStatementVo.getCustomerStatementDto();
			//查询对账单明细
			List<CustomerStatementDEntity> list = this.customerStatementService.queryByStatementBillNo(custoemrStatementDto.getStatementBillNo());
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
	 * 大客户对账单导出明细数据封装
	 * @author ddw
	 * @date 2014-06-16
	 */
	private ExportResource packExportResourceXLS(List<CustomerStatementDEntity> list,String[] header,String[] headerName){
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE);//单据父类型
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);//支付方式
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS);//支付方式
		termCodes.add(DictionaryConstants.PICKUP_GOODS);//提货方式
		termCodes.add(DictionaryConstants.PICKUP_GOODS_AIR);//提货方式
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);//应收单
		//获取对账单要用到的所有数据字典 
		Map<String,Map<String,Object>> termMaps = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
				
		//获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}		
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		// 循环进行封装
		for (CustomerStatementDEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(CustomerStatementDEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())&& fieldValue != null) {
						//日期转化
						fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
					}
					if (fieldValue != null) {
						// 单据父类型进行转化
						fieldValue = validaExtracted(termMaps, productMap,
								entity, columnName, fieldValue);		
						//设置属性值
						if(fieldValue!=null){
							rowList.add(fieldValue.toString());
						}else{
							rowList.add(null);
						}
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

	private Object validaExtracted(Map<String, Map<String, Object>> termMaps,
			Map<String, String> productMap, CustomerStatementDEntity entity,
			String columnName, Object fieldValue) {
		if (columnName.equals("billParentType")) {
			fieldValue =SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE, fieldValue.toString());
			// 付款方式进行转化
		} else if (columnName.equals("paymentType")) {
			//数据字典转换
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, fieldValue.toString());
			// 单据子类型进行转化
		} else if (columnName.equals("billType")&& StringUtils.isNotBlank(entity.getBillParentType())) {
			//声明单据父类型
			String parentType = entity.getBillParentType();
			// 应收单
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(parentType)) {
				//数据字典转换
				fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE, fieldValue.toString());
			}
		//产品类型
		}else if(columnName.equals("productCode")){
			//如果数据产品类型编码不为空
			if(StringUtils.isNotEmpty(fieldValue.toString())){
				//将产品类型转换编码为名称
				fieldValue=productMap.get(fieldValue.toString());
			}
		//提货方式
		}else if(columnName.equals("receiveMethod")){
			String receiveMethod = fieldValue.toString();
			//先去汽运提货方式词条中拿
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.PICKUP_GOODS, receiveMethod);
			//如果汽运提货方式没拿到，则去空运词条中拿
			if(fieldValue==null ||receiveMethod==fieldValue){
				fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.PICKUP_GOODS_AIR,receiveMethod);
			}
		//审核状态 
		}else if(columnName.equals("auditStatus")){
			fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS, fieldValue.toString());
		}
		return fieldValue;
	}
	
	public CustomerStatementVo getCustomerStatementVo() {
		return customerStatementVo;
	}

	public void setCustomerStatementVo(CustomerStatementVo customerStatementVo) {
		this.customerStatementVo = customerStatementVo;
	}
	
	public void setCustomerStatementService(ICustomerStatementService customerStatementService) {
		this.customerStatementService = customerStatementService;
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
	/**
	 * @SET
	 * @param productService
	 */
	public void setProductService(IProductService productService) {
		/*
		 *@set
		 *@this.productService = productService
		 */
		this.productService = productService;
	}

	
}

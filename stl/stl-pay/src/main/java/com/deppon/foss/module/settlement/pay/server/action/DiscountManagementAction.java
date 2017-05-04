package com.deppon.foss.module.settlement.pay.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.pay.api.server.service.IDiscountManagementService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountManagementDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.DiscountManagementVo;
import com.deppon.foss.util.DateUtils;

public class DiscountManagementAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(DiscountManagementAction.class);

	/*
	 * 折扣单VO
	 */
	private DiscountManagementVo discountManagementVo;
	/*
	 * 折扣单服务
	 */
	private IDiscountManagementService discountManagementService;
	/*
	 * 折扣单导出文件名字
	 */
	private static final String EXCELNAME = "折扣单明细";
	/*
	 * excel名字
	 */
	private String excelName;
	/*
	 * 输出流
	 */
	private InputStream inputStream;
	/*
	 * 产品服务
	 */
	private IProductService productService;

	/**
	 * 按客户查询折扣单
	 * 
	 * @author wy
	 * @date 2015-02-04
	 */
	@JSON
	public String queryDiscountByCust() {
		
//		productService.getLevel3ForLogistics();
		
		// 获取前台参数
		DiscountManagementDto DiscountManagementDto = discountManagementVo.getDiscountManagementDto();
		if (StringUtils.isNotBlank(DiscountManagementDto.getDiscountNo())) {
			queryDiscountByNumber();
		} else {
			// 如果结束日期不为空，则需要对其进行+1操作
			if (DiscountManagementDto.getPeriodEndDate() != null) {
				// 结束日期加1天
				DiscountManagementDto.setPeriodEndDate(DateUtils
						.convert(DateUtils.addDay(
								DiscountManagementDto.getPeriodEndDate(), 1)));
			}
			// 如果开始日期不为空，则需要对其进行-1操作
			else if (DiscountManagementDto.getPeriodBeginDate() != null) {
				DiscountManagementDto
						.setPeriodBeginDate(DateUtils.convert(DateUtils.addDay(
								DiscountManagementDto.getPeriodBeginDate(), -1)));
			}
			// 调用查询折扣单接口
			DiscountManagementDto dto = discountManagementService
					.queryDiscountByCust(DiscountManagementDto,
							this.getStart(), this.getLimit());
			// 返回查询结果到前台
			discountManagementVo.setDiscountManagementDto(dto);
			// 分页查询返回总行数
			this.setTotalCount(dto.getCount());
		}
		return returnSuccess();
	}

	/**
	 * 按单号查询折扣单
	 * 
	 * @author wy
	 * @date 2015-02-04
	 */
	@JSON
	public String queryDiscountByNumber() {
		// 获取前台参数
		DiscountManagementDto DiscountManagementDto = discountManagementVo.getDiscountManagementDto();
		// 调用查询折扣单接口
		DiscountManagementDto dto = discountManagementService.queryDiscountByNumber(DiscountManagementDto, this.getStart(),this.getLimit());
		// 返回查询结果到前台
		discountManagementVo.setDiscountManagementDto(dto);
		// 分页查询返回总行数
		this.setTotalCount(dto.getCount());
		return returnSuccess();
	}

	/**
	 * 双击折扣单记录查看明细，按折扣单号查询
	 * 
	 * @author wy
	 * @date 2015-02-04
	 */
	@JSON
	public String queryDiscountDEntity() {
		// 获取前台参数
		DiscountManagementDto DiscountManagementDto = discountManagementVo
				.getDiscountManagementDto();
		// 调用按折扣单单号查询折扣单明细接口
		List<DiscountManagementDEntity> dto = discountManagementService.queryDiscountDEntity(DiscountManagementDto.getDiscountNo(),this.getStart(), this.getLimit());
		// 返回查询结果到前台
		DiscountManagementDto dd = new DiscountManagementDto();
		dd.setDiscountManagementDList(dto);
		this.setTotalCount((long) discountManagementService
				.queryCountByDiscount(DiscountManagementDto.getDiscountNo()));
		discountManagementVo.setDiscountManagementDto(dd);
		return returnSuccess();
	}

	/**
	 * 折扣单导出折扣单明细
	 * 
	 * @author wy
	 * @date 2015-02-4
	 */
	@JSON
	public String discountExportXLS() {
		// 获取类头名称
		String[] header = discountManagementVo.getArrayColumns();
		// 获取列头中文名
		String[] headerName = discountManagementVo.getArrayColumnNames();
		try {
			// 查询参数不能为空
			if (discountManagementVo == null) {
				return returnError("导出参数不能为空!");
			}
			try {
				// 设置导出excel名称
				this.setExcelName(new String((EXCELNAME)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			// 获取前台参数
			DiscountManagementDto discountManagementDto = this.discountManagementVo
					.getDiscountManagementDto();
			// 查询折扣单明细
			List<DiscountManagementDEntity> list = this.discountManagementService
					.queryDiscountDEntityExport(discountManagementDto
							.getDiscountNo());
			// 导出格式数据
			ExportResource exportResource = packExportResourceXLS(list, header,
					headerName);
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
			logger.error(e.getMessage(), e);
			return returnError("导出折扣单异常:" + e.getMessage());
		}

		return returnSuccess();
	}

	/**
	 * 折扣单导出明细数据封装
	 * 
	 * @author wy
	 * @date 2015-02-4
	 */
	private ExportResource packExportResourceXLS(
			List<DiscountManagementDEntity> list, String[] header,
			String[] headerName) {
		Map<String,String> productCache = new HashMap<String, String>();
		List<ProductEntity>  productList = productService.getLevel3ForExpress();
		for(ProductEntity entity :productList){
			productCache.put(entity.getCode(),entity.getName());
		}
		Map<String,String> invorkMarkCache = new HashMap<String, String>();
		invorkMarkCache.put(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE, "01—运输专票");
		invorkMarkCache.put(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO, "02—非运输专票");
		
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		// 循环进行封装
		for (DiscountManagementDEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(
						DiscountManagementDEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())
							&& fieldValue != null) {
						// 日期转化
						fieldValue = DateUtils.convert((Date) fieldValue,
								DateUtils.DATE_FORMAT);
					}
//					if (columnName.equals("receivableBusinessDate")) {
//						
//						String time  = bartDateFormat.format(bartDateFormat.parse("2012-03-26 17:49:37.0")); 
//						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
//						
//						fieldValue =formatter.format(fieldValue);
//					}
					if(columnName.equals("productCode")){
						fieldValue = productCache.get(fieldValue);
					}
					if(columnName.equals("invoiceMark")){
						fieldValue = invorkMarkCache.get(fieldValue);
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
		// 封装数据
		data.setRowList(sheetList);
		data.setHeads(headerName);
		return data;
	}

	/**
	 * 确认折扣单
	 * 
	 * @author wy
	 * @date 2015-02-4
	 */
	@JSON
	public String confirmDiscount() {
		try {
			// 获取dto
			DiscountManagementDto discountManagementDto = discountManagementVo
					.getDiscountManagementDto();
			// 调用折扣单确认service
			discountManagementService.confirmDiscount(discountManagementDto);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}

	/**
	 * 作废折扣单
	 * 
	 * @author wy
	 * @date 2015-02-4
	 */
	@JSON
	public String discountDelete() {
		try {
			// 获取dto
			DiscountManagementDto discountManagementDto = discountManagementVo
					.getDiscountManagementDto();
			// 调用折扣单确认service
			discountManagementService.discountDelete(discountManagementDto);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	public DiscountManagementVo getDiscountManagementVo() {
		return discountManagementVo;
	}

	public void setDiscountManagementVo(
			DiscountManagementVo discountManagementVo) {
		this.discountManagementVo = discountManagementVo;
	}

	public void setDiscountManagementService(
			IDiscountManagementService discountManagementService) {
		this.discountManagementService = discountManagementService;
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

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
}

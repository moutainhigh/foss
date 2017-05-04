package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.server.components.export.excel.ExcelStyle;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfiEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfiEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfiDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 出发到达往来报表查询service.
 *
 * @author 045738-foss-maojianqiang
 * @date 2013-3-7 下午2:49:22
 */
public class MvrRfiEntityService implements IMvrRfiEntityService{
	
	/** 注入日志. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MvrRfiEntityService.class);
	
	/** 注入对应dao. */
	private IMvrRfiEntityDao mvrRfiEntityDao;
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	private IProductService  productService;

	
	/**
	 * 查询始发到达往来报表.
	 *
	 * @param start the start
	 * @param limit the limit
	 * @param dto the dto
	 * @return the list
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 */
	public List<MvrRfiEntity> queryReportByConditions(int start,int limit,MvrRfiDto dto) {
		//校验分页参数
		if(start <0 || limit <0){
			throw new SettlementException("分页参数有误！");
		}
		//校验dto参数
		if(StringUtils.isBlank(dto.getPeriod())){
			throw new SettlementException("查询期间不能为空！");
		}
		//封装分页参数
		RowBounds rb = new RowBounds(start,limit);
		//调用dao进行查询
		return mvrRfiEntityDao.selectByConditions(rb, dto,FossConstants.YES);
	}
	
	/**
	 * 查询始发到达往来报表总条数.
	 *
	 * @param dto the dto
	 * @return the mvr rfi entity
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:56:19
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfiEntityService#queryTotalCounts(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfiDto)
	 */
	@Override
	public MvrRfiEntity queryTotalCounts(MvrRfiDto dto) {
		//校验dto参数
		if(StringUtils.isBlank(dto.getPeriod())){
			// 查询期间不能为空
			throw new SettlementException("查询期间不能为空！");
		}
		return mvrRfiEntityDao.queryTotalCounts(dto);
		
	}
	
	/**
	 * 导出excel.
	 *
	 * @param dto the dto
	 * @return the hSSF workbook
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-11 下午6:22:08
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfiEntityService#export(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfiDto)
	 */
	public ExportResource export(MvrRfiDto dto) {
		LOGGER.info("导出报表开始");
		//校验dto参数
		if(StringUtils.isBlank(dto.getPeriod())){
			throw new SettlementException("查询期间不能为空！");
		}
		// 获取导出数据源
		return getDataList(dto);
	}

	/**
	 * 获取导出数据源.
	 *
	 * @param dto the dto
	 * @return the data list
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-12 上午8:43:40
	 */
	private ExportResource getDataList(MvrRfiDto dto) {
		//封装分页参数 --此处分页不需要，但是接口参数有，公用一个接口，故而传递进去
		RowBounds rb = new RowBounds(0,Integer.MAX_VALUE);
		// 报表查询
		List<MvrRfiEntity> list = mvrRfiEntityDao.selectByConditions(rb, dto,FossConstants.NO);
		//如果没有查询到数据，则抛出停止导出操作
		if(CollectionUtils.isEmpty(list)){
			throw new SettlementException("没有查询到要导出的数据！");
		}
		//默认导出列名称
		String[] headerName = { "期间", "产品类型", "客户编码", "客户名称", "部门编码", "部门名称",
				"部门类型",/* "客户类型", */
				"还款现金未签收", "还款银行未签收", "还款现金已签收", "还款银行已签收", "理赔冲收入",
				"理赔冲到达应收已签收", "理赔冲到达应收未签收", "还款代收货款现金未签收", "还款代收货款银行未签收",
				"应付代收货款冲应收到付运费已签收", "应付代收货款冲应收到付运费未签收", "应付代收货款冲应收始发运费已签收",
				"应付代收货款冲应收始发运费未签收", "应付代收货款冲小票应收", "预收客户冲应收到付运费未签收",
				"预收客户冲应收到付运费已签收", "退运费冲收入", "退运费冲到达应收已签收", "退运费冲到达应收未签收" };
		
		// 默认导出列
		String[] header = { "period", "productCode", "customerCode",
				"customerName", "orgCode", "orgName", "orgType",/*
																 * "customerType",
																 */
				/** 还款现金未签收 */
				"urDestChNpod",
				/** 还款银行未签收 */
				"urDestCdNpod",
				/** 还款现金已签收 */
				"urDestChPod",
				/** 还款银行已签收 */
				"urDestCdPod",
				/** 理赔冲收入 */
				"claimDestWoIncome",
				/** 理赔冲到达应收已签收 */
				"claimWoDestRcvPod",
				/** 理赔冲到达应收未签收 */
				"claimWoDestRcvNpod",
				/** 还款代收货款现金未签收 */
				"codUrChNpod",
				/** 还款代收货款银行未签收 */
				"codUrCdNpod",
				/** 应付代收货款冲应收到付运费已签收 */
				"codDestRcvPod",
				/** 应付代收货款冲应收到付运费未签收 */
				"codDestRcvNpod",
				/** 应付代收货款冲应收始发运费已签收 */
				"codOrigRcvPod",
				/** 应付代收货款冲应收始发运费未签收 */
				"codOrigRcvNpod",
				/** 应付代收货款冲小票应收 */
				"codWoOrRcv", "custDrDestRcvNpod", "custDrDestRcvPod",
				/** 退运费冲收入 */
				"rdDestWoIncome", "rdWoDestRcvPod", "rdWoDestRcvNpod" };

		
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		
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
		//数据字典转换
		List<String> types = new ArrayList<String>();
		types.add(DictionaryConstants.MVRRFI_VOUCHER__ORG_TYPE);// 始发到达往来月报表出发/到达
		// 获取全部待转换缓存
		Map<String, Map<String, Object>> map = SettlementUtil
				.getDataDictionaryByTermsCodes(types);
		
		// 循环进行封装
		for (MvrRfiEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(MvrRfiEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 将字段封装到list中
					validaExtracted(productMap, map, rowList, columnName,
							fieldValue);
				}
			}
			//将行添加到sheetlist中
			sheetList.add(rowList);
		}
		
		//声明导出数据源
		ExportResource resource = new ExportResource();
		//绑定数据源
		resource.setRowList(sheetList);
		//绑定数据源
		resource.setHeads(headerName);
		
		resource.setHeaderHeight(2);
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.SIX, "数据统计维度"); // 7
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.SEVEN, SettlementReportNumber.TEN, "还款运单总运费（到付）"); // 4
		HeaderRows rowContent3 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ELEVEN, SettlementReportNumber.THIRTEEN, "理赔"); // 3
		HeaderRows rowContent4 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.FOURTEEN, SettlementReportNumber.TWENTY, "代收货款");  // 7
		HeaderRows rowContent5 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.TWENTY_ONE, SettlementReportNumber.TWENTY_TWO, "营业部预收客户");  // 2
		HeaderRows rowContent6 = new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.TWENTY_THREE, SettlementReportNumber.TWENTY_FIVE, "退运费");  // 3
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		headerList.add(rowContent3);
		headerList.add(rowContent4);
		headerList.add(rowContent5);
		headerList.add(rowContent6);
		resource.setHeaderList(headerList);
		return resource;
	}

	private void validaExtracted(Map<String, String> productMap,
			Map<String, Map<String, Object>> map, List<String> rowList,
			String columnName, Object fieldValue) {
		if (fieldValue != null) {
			if (columnName.equals("productCode")) {
				
				//默认的产品类型为空
				String productEntityName = "";
				//如果数据产品类型编码不为空
				if(StringUtils.isNotEmpty(fieldValue.toString())){
					//将产品类型转换编码为名称
					productEntityName=productMap.get(fieldValue.toString());
				}
				fieldValue = productEntityName;
				
			}
			// 如果为单据子类型，则需要转化
			else if (columnName.equals("orgType")) {
				fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.MVRRFI_VOUCHER__ORG_TYPE,fieldValue.toString());
			}
			rowList.add(fieldValue.toString());
		} else {
			rowList.add(null);
		}
	}

	
	// 导出数据集至Excel
	public void exportData(HSSFWorkbook workbook, SheetData sheetData, 
			String sheetName, int sheetSize) {
		String[] heads = sheetData.getRowHeads();
		List<List<String>> data = sheetData.getRowList();
        int dataSize = sheetData.getRowList().size();
		double sheetNo = Math.ceil((double)dataSize / sheetSize);
		HSSFCellStyle headStyle = ExcelStyle.headStyle(workbook);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headStyle.setBorderBottom((short)1);
		headStyle.setBorderLeft((short)1);
		headStyle.setBorderRight((short)1);
		headStyle.setBorderTop((short)1);
		
		HSSFCellStyle bodyStyle = ExcelStyle.bodyStyle(workbook);
		
        for(int index=0; index<=sheetNo; index++) {
            HSSFSheet sheet = createSheet(workbook, sheetName + index);
            sheet.setDefaultColumnWidth(SettlementReportNumber.TWENTY_FIVE);
    		HSSFRow row = sheet.createRow(0);
    		HSSFCell cell;
    		//获取表头
    		List<HeaderRows> headerList = sheetData.getHeaderList() ;
    		//判断是否有多级表头
    		if(!CollectionUtils.isEmpty(headerList)){
    			//封装多级列头
        		for(int r=0;r<sheetData.getHeaderHeight();r++){
        			row = sheet.createRow(r);
        			for(int i=0, len=heads.length; i<len; i++) {
               			cell = row.createCell(i);
            			cell.setCellStyle(headStyle);
            			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            		}
        		}
        		//绑定表头
        		for(int i=0;i<headerList.size();i++){
        			HeaderRows headerRow = headerList.get(i);
        			sheet.addMergedRegion(new CellRangeAddress(headerRow.getStartRow(),headerRow.getEndRow(),headerRow.getStartColumn(),headerRow.getEndColumn()));
        			sheet.getRow(headerRow.getStartRow()).getCell(headerRow.getStartColumn()).setCellValue(headerRow.getContent());
        		}
    		}
   
			
    		for(int i=0, len=heads.length; i<len; i++) {
       			cell = row.createCell(i);
    			cell.setCellStyle(headStyle);
    			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    			cell.setCellValue(heads[i]);
    		}
    		
            int startNo = index * (sheetSize - 1);
            int endNo = Math.min(startNo + sheetSize - 1, dataSize);
    		List<String> list = new ArrayList<String>();
            for (int i=startNo; i<endNo; i++) {
    			list = data.get(i);
    			if(!CollectionUtils.isEmpty(headerList)){
    				row = sheet.createRow(i + sheetData.getHeaderHeight() - startNo);
    			}else{
    				row = sheet.createRow(i + 1 - startNo);
    			}
                
                for (int j=0, listSize=list.size(); j<listSize; j++) {
                    cell = row.createCell(j);
        			cell.setCellStyle(bodyStyle);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    				String value = list.get(j);
                    if (value!=null && !value.equals("null")) {
                        cell.setCellValue(value);
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
        }
	}
	
	// 导出Excel功能
	public HSSFWorkbook exportExcel(SheetData sheetData, String sheetName, int sheetSize) {
		if(sheetSize >= SettlementReportNumber.SIXTY_FIVE_THOUSAND_FIVE_HUNDRED_AND_THIRTY_SIX) {
			sheetSize = SettlementReportNumber.SIXTY_FIVE_THOUSAND_FIVE_HUNDRED_AND_THIRTY;
		}
        HSSFWorkbook workbook = createWorkBook();
        exportData(workbook, sheetData, sheetName, sheetSize);
        return workbook;
	}
	

	// 创建工作薄对象
	public HSSFWorkbook createWorkBook() {
		return new HSSFWorkbook();
	}
	
	// 创建工作表对象
	public HSSFSheet createSheet(HSSFWorkbook workbook, String sheetName) {
		return workbook.createSheet(sheetName);
	}
		
	
	
	
	/**
	 * @return  the productService
	 */
	public IProductService getProductService() {
		return productService;
	}

	
	/**
	 * @param productService the productService to set
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * Gets the mvr rfi entity dao.
	 *
	 * @return the mvr rfi entity dao
	 */
	public IMvrRfiEntityDao getMvrRfiEntityDao() {
		return mvrRfiEntityDao;
	}
	
	/**
	 * Sets the mvr rfi entity dao.
	 *
	 * @param mvrRfiEntityDao the new mvr rfi entity dao
	 */
	public void setMvrRfiEntityDao(IMvrRfiEntityDao mvrRfiEntityDao) {
		this.mvrRfiEntityDao = mvrRfiEntityDao;
	}
	
}

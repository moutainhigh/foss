package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrOrcciDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrOrcciService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccIEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrOrcciQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.CollectionUtils;


public class MvrOrcciService implements IMvrOrcciService {
	private static final String PRODUCT_CODE_STR = "productCode";
    IMvrOrcciDao mvrOrcciDao;
	IProductService productService;
    
    /**
     * 分页查询凭证相关数据
     */
	@Override
	public List<MvrOrccIEntity>  queryMvrOrcciByParam(MvrOrcciQueryDto dto,
			int start, int limit) {
		return mvrOrcciDao.selectMvrOrcciByConditions(dto,start,limit);
	}

    /**
     * 导出凭证数据service
     */
	@Override
	public ExportResource exportMvrOrcci(MvrOrcciQueryDto dto, CurrentInfo cInfo) {
		List<MvrOrccIEntity> orccEntities = mvrOrcciDao.selectMvrOrcciByConditions(dto);
		if(CollectionUtils.isEmpty(orccEntities)){
			throw new SettlementException("没有数据，无法导出!");
		}
		
		List<List<String>> resultList = new ArrayList<List<String>>();
		String[] exportColumns = exportColumns();
		List<String> columnList = null;
		Object fieldValue = null;
		String cellValue = null;
		// 获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService
				.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (CollectionUtils.isNotEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}
		//封装数据到List内部方便放入sheet中
		for(MvrOrccIEntity entity : orccEntities){
			columnList = new ArrayList<String>();
			for(String col : exportColumns){
				fieldValue = ReflectionUtils.getFieldValue(entity, col);				
				//设置单元格内容
				cellValue = fieldValue == null?"":fieldValue.toString();
				// 产品类型需要转换
				if (PRODUCT_CODE_STR.equalsIgnoreCase(col)) {

					// 如果数据产品类型编码不为空
					if (StringUtils.isNotEmpty(cellValue)) {
						// 将产品类型转换编码为名称
						cellValue = productMap.get(cellValue);
					}
				}
				columnList.add(cellValue);
			}
			resultList.add(columnList);
		}
		
		//创建一个sheet对象
		ExportResource sheet = new ExportResource();
		sheet.setHeads(orccExportHeader());
		
		sheet.setHeaderHeight(2);
		
		List<HeaderRows> headerList = new ArrayList<HeaderRows>();
		HeaderRows rowContent1 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.FIVE,"数据统计维度");
		HeaderRows rowContent2 = new HeaderRows(SettlementReportNumber.ZERO,SettlementReportNumber.ZERO,SettlementReportNumber.SIX,SettlementReportNumber.SIX,"外请车成本");
		
		headerList.add(rowContent1);
		headerList.add(rowContent2);
		
		sheet.setHeaderList(headerList);
		sheet.setRowList(resultList);
		return sheet;
	}

	/**
	 * 返回查询寻条件的行数
	 */
	@Override
	public Long queryMvrOrcciByParamCount(MvrOrcciQueryDto dto) {
		return mvrOrcciDao.selectMvrOrcciByConditionsCount(dto);
	}
	
	private String[] exportColumns(){
		//导出数据：期间  产品类型  客户编码 客户名称  始发部门编码 始发部门名称  
		//到达部门编码 到达部门名称 
		// 出发付款（预付>0)  到达时确认首款成本（预付>0）到达时确认尾款成本（预付>0）反到达时红冲首款成本 反到达时红冲尾款成本
		//付款申请（始发付尾款）付款申请（到达付尾款）（预付>0）
		 String[] columns = { "period","productCode","customerCode","customerName","orgCode","orgName","destPayTail"};
		return columns;
	}
	
	private String[] orccExportHeader(){
		String[] header =  {
				"期间","产品类型","客户编码","客户名称","部门编码","部门名称","付款申请（到达付尾款）（预付>0）"
		};
		return header;
	}	

	public IMvrOrcciDao getMvrOrcciDao() {
		return mvrOrcciDao;
	}

	public void setMvrOrcciDao(IMvrOrcciDao mvrOrcciDao) {
		this.mvrOrcciDao = mvrOrcciDao;
	}

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

    
	
}

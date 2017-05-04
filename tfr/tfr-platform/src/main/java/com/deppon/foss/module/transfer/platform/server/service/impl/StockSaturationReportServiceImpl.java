package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationReportDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockSaturationReportService;
import com.deppon.foss.module.transfer.platform.api.shared.define.StockSaturationConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationReportEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;

public class StockSaturationReportServiceImpl implements
		IStockSaturationReportService {

	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 库存饱和度数据监控报表DAo
	* @fields stockSaturationReportDao
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午3:18:33
	* @version V1.0
	*/
	private IStockSaturationReportDao stockSaturationReportDao;
	
	
	/**
	 * 库存副表的Service
	* @fields stockPairService
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午3:20:12
	* @version V1.0
	*/
	private IStockPairService stockPairService;
	
	/**
	* @description 仓库饱和度数据监控报表查询
	* @param queryDateA
	* @param queryDateB
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 上午9:59:34
	*/
	@Override
	public List<StockSaturationReportEntity> queryStockSaturationReport(
			StockSaturationReportEntity pojo, int start, int limit) {
		List<StockSaturationReportEntity> backList = stockSaturationReportDao.queryStockSaturationReport(pojo.getQueryDateA(),pojo.getQueryDateB(), start, limit);
		if(backList!=null && backList.size()>0){
			List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllOutTransOrg();
			for (StockSaturationReportEntity stockSaturationReportEntity : backList) {
				TransCenterOrgEntity tcoe = this.queryNameOrgUp(stockSaturationReportEntity.getOrgCode(),transCenterList);
				stockSaturationReportEntity.setBigArea(tcoe.getBigArea());
				stockSaturationReportEntity.setBigdept(tcoe.getBigdept());
				stockSaturationReportEntity.setDivision(tcoe.getDivision());
				stockSaturationReportEntity.setQueryDateB(pojo.getQueryDateB());
			}
			
		}else{
			return null;
		}
		return backList;
	}
	
	
	
	/**
	* @description 仓库饱和度数据监控报表查询 的总记录数（即全国外场的总个数，无需参数）
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:29:42
	*/
	@Override
	public long queryStockSaturationReportCount() {
		return stockSaturationReportDao.queryStockSaturationReportCount();
	}


	
	/**
	* @description 导出
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IStockSaturationReportService#exportStockSaturationReport(com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationReportEntity)
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:49:22
	* @version V1.0
	*/
	@Override
	public InputStream exportStockSaturationReport(
			StockSaturationReportEntity pojo) {
		InputStream excelStream = null;
		try{
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			List<StockSaturationReportEntity> dbList = queryStockSaturationReport(pojo,-1,-1);
			for (StockSaturationReportEntity dbEntity : dbList) {
				List<String> columnList = new ArrayList<String>();
				/*
				 *"本部","事业部","大区","转运场","日期","仓库饱和度日数据","仓库饱和度月累计","派送率日数据","派送率度月累计",
				 *"派送拉回率日数据","派送拉回率月累计","货区密度日数据","货区密度月累计","退单率日数据","退单率月累计" 
				*/
				columnList.add(dbEntity.getBigdept());
				columnList.add(dbEntity.getDivision());
				columnList.add(dbEntity.getBigArea());
				columnList.add(dbEntity.getOrgName());
				columnList.add(dbEntity.getQueryDateB());
				columnList.add(dbEntity.getSaturationDay()==null?"0":dbEntity.getSaturationDay()+"%");
				columnList.add(dbEntity.getSaturationMonth()==null?"0":dbEntity.getSaturationMonth()+"%");
				columnList.add(dbEntity.getSendrateDay()==null?"0":dbEntity.getSendrateDay()+"%");
				columnList.add(dbEntity.getSendrateMonth()==null?"0":dbEntity.getSendrateMonth()+"%");
				columnList.add(dbEntity.getPullbackDay()==null?"0":dbEntity.getPullbackDay()+"%");
				columnList.add(dbEntity.getPullbackMonth()==null?"0":dbEntity.getPullbackMonth()+"%");
				columnList.add(dbEntity.getDensityDay()==null?"0":dbEntity.getDensityDay()+"%");
				columnList.add(dbEntity.getDensityMonth()==null?"0":dbEntity.getDensityMonth()+"%");
				columnList.add(dbEntity.getReturndateDay()==null?"0":dbEntity.getReturndateDay()+"%");
				columnList.add(dbEntity.getReturndateMoth()==null?"0":dbEntity.getReturndateMoth()+"%");
				rowList.add(columnList);
			}
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(StockSaturationConstants.STOCK_SATURATION_REPORT_ROW_HEADS);
			sheetData.setRowList(rowList);
			
			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, StockSaturationConstants.STOCK_SATURATION_REPORT_SHEET_NAME,
					StockSaturationConstants.SHEET_SIZE));
		}catch(Exception e){
			LOGGER.error("导出仓库饱和度数据监控报表异常", e);
			throw new BusinessException("导出仓库饱和度数据监控报表异常", e);
		}
		return excelStream;
	}



	/**
	* @description  获取上级的名称
	* @param orgCode
	* @param upLevel  事业部 BizTypeConstants.ORG_DIVISION    BizTypeConstants.ORG_BIG_REGION
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月13日 上午9:14:44
	*/
	private TransCenterOrgEntity queryNameOrgUp(String orgCode,List<TransCenterOrgEntity> transCenterList){
		/**
		List<String> bizTypesList = new ArrayList<String>();
		//事业部 BizTypeConstants.ORG_DIVISION
		//大区     BizTypeConstants.ORG_BIG_REGION
		bizTypesList.add(upLevel);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			return orgAdministrativeInfoEntity.getName();
		}else{
			return "";
		}
		*/
		TransCenterOrgEntity backPojo = new TransCenterOrgEntity();
		if(transCenterList!=null){
			for (TransCenterOrgEntity transCenterOrgEntity : transCenterList) {
				if(StringUtils.endsWith(transCenterOrgEntity.getOrgCode(), orgCode)){
					backPojo = transCenterOrgEntity;
					break;
				}
			}
			return backPojo;
		}else{
			return backPojo;
		}
	}

	
	
	
	
	
	/**
	* @description 库存饱和度数据监控报表Dao
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:26:03
	*/
	public IStockSaturationReportDao getStockSaturationReportDao() {
		return stockSaturationReportDao;
	}


	
	/**
	* @description 库存饱和度数据监控报表Dao
	* @param stockSaturationReportDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:26:10
	*/
	public void setStockSaturationReportDao(
			IStockSaturationReportDao stockSaturationReportDao) {
		this.stockSaturationReportDao = stockSaturationReportDao;
	}

	
	/**
	* @description 库存副表的Service
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午3:21:01
	*/
	public IStockPairService getStockPairService() {
		return stockPairService;
	}


	
	/**
	* @description 库存副表的Service
	* @param stockPairService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午3:21:04
	*/
	public void setStockPairService(IStockPairService stockPairService) {
		this.stockPairService = stockPairService;
	}
	
	
}

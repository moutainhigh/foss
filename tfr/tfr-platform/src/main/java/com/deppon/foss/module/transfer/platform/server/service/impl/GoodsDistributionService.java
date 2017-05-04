package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExporter;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IGoodsDistributionDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IGoodsDistributionService;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsDistributionEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 货量流动分布
 * @author 200978
 * 2015-3-10
 */
public class GoodsDistributionService implements IGoodsDistributionService {
 
	private static final Logger LOG = LoggerFactory.getLogger(GoodsDistributionService.class);
	
	private IGoodsDistributionDao goodsDistributionDao;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setGoodsDistributionDao(IGoodsDistributionDao goodsDistributionDao) {
		this.goodsDistributionDao = goodsDistributionDao;
	}

	//注入查询经营本部service
	private IPlatformCommonService platformCommonService;


	@Override
	public void statisticGoodsDistribution(Date statisticDate) {
		//统计时间减去30分钟
		Date startDate = DateUtils.getDateAdd(statisticDate, -1000*PlatformConstants.SONAR_NUMBER_60*PlatformConstants.SONAR_NUMBER_30);
		//拿到统计时间
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		String staTime = sf.format(statisticDate);
		//统计转运场货物流动分布
		List<GoodsDistributionEntity> list =  this.goodsDistributionDao.statisticGoodsDistribution(startDate,statisticDate);
		//查询经营本部，存储临时经营本部用
		GoodsDistributionEntity temp = new GoodsDistributionEntity();
		for (GoodsDistributionEntity goodsDistributionEntity : list) {
			//根据外场查经营本部
			temp = this.queryOperationDeptCodeByCurrentCode(goodsDistributionEntity.getTransferCenterCode());
			goodsDistributionEntity.setOperationDeptCode(temp.getOperationDeptCode());
			goodsDistributionEntity.setOperationDeptName(temp.getOperationDeptName());
			goodsDistributionEntity.setStaDate(statisticDate);
			goodsDistributionEntity.setStaTime(staTime);
			goodsDistributionEntity.setId(UUIDUtils.getUUID());
			//保存统计记录
			this.goodsDistributionDao.saveGoodsDistribution(goodsDistributionEntity);
		}
		
	}

	
	/**
	 * 查询给定部门code所属外场
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	@Override
	public Map<String, String> queryParentTfrCtrCode(String code) {
		Map<String, String> result = null;
		// 调用综合接口判断当前部门是否外场或外场子部门
		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);
		if (orgEntity != null) {
			result = new HashMap<String, String>();
			result.put("code", orgEntity.getCode());
			result.put("name", orgEntity.getName());
		}
		return result;
	}
	
	/**
	 * 查询给定部门code所对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	@Override
	public Map<String, String> queryOperationDeptCode(String code) {
		Map<String, String> result = null;
		//查询经营本部
		GoodsDistributionEntity temp = this.queryOperationDeptCodeByCurrentCode(code);
		if(temp !=null){
			result = new HashMap<String, String>();
			result.put("code", temp.getOperationDeptCode());
			result.put("name", temp.getOperationDeptName());
		}
		return result;
	}
	
	/**
	 * 根据部门编码查询所属的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	private GoodsDistributionEntity queryOperationDeptCodeByCurrentCode(String code){
		if(StringUtil.isEmpty(code)){
			throw new TfrBusinessException("根据部门查询所属经营本部失败！查询参数为空！");
		}
		//return this.goodsDistributionDao.queryOperationDeptCodeByCurrentCode(code);
		GoodsDistributionEntity goodsDistributionEntity=new GoodsDistributionEntity();
		Map<String,String> hpEntity= platformCommonService.findSupHq(code);
		if(hpEntity!=null){
			
			goodsDistributionEntity.setOperationDeptCode( hpEntity.get("HQ_CODE")==null?"":hpEntity.get("HQ_CODE"));
			goodsDistributionEntity.setOperationDeptName(hpEntity.get("HQ_NAME")==null?"":hpEntity.get("HQ_NAME"));
		}
		return goodsDistributionEntity;
	}
	
	
	@Override
	public List<GoodsDistributionEntity> queryGoodsDistributionByDay(
			GoodsDistributionEntity entity) {
		/**
		 * @dessc 日数据
		 * @author 105795
		 * @date 2015-05-29
		 * */
	
		List<GoodsDistributionEntity> resultList=this.goodsDistributionDao.queryGoodsDistributionByDay(entity);
		
		return resultList;
	} 
	@Override
	public List<GoodsDistributionEntity> queryGoodsDistributionByMonth(
			GoodsDistributionEntity entity) {
		
		/**
		 * @desc 月数据
		 * @author 105795
		 * @date 2015-05-29
		 * */
		
		List<GoodsDistributionEntity> resultList=this.goodsDistributionDao.queryGoodsDistributionByMonth(entity);
		return resultList;
		
	}
	
	
	
	
	
	/**
	 * 生成导出文件名称
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param fileName
	 * @return
	 * @throws TfrBusinessException
	 */
	@Override
	public String encodeFileName(String fileName) throws TfrBusinessException {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			LOG.error("转换文件名编码失败", e);
			throw new TfrBusinessException(StockException.EXPORT_FILE_ERROR_CODE, "");
		}
	}
	
	/**
	 * 按日 转运场货量流动分布 导出 
	 * @Author 200978
	 * 2015-3-13
	 * @param goodsDistributionEntity
	 * @return
	 * @throws TfrBusinessException
	 */
	public InputStream goodsDistributionByDayExport(GoodsDistributionEntity goodsDistributionEntity) throws TfrBusinessException{

		InputStream excelStream = null;
		List<GoodsDistributionEntity> goodsDistributionList = new ArrayList<GoodsDistributionEntity>();
		// 获取导出信息
		goodsDistributionList = this.queryGoodsDistributionByDay(goodsDistributionEntity);
		// 行List
		List<List<String>> rowGoodsList = new ArrayList<List<String>>();
		for (GoodsDistributionEntity goodsDistribution : goodsDistributionList) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//"日期",
			columnList.add(String.valueOf(DateUtils.convert(goodsDistribution.getStaDate(), DateUtils.DATE_FORMAT)));
			//"时间点",
			columnList.add(String.valueOf(goodsDistribution.getStaTime()));
			//"转运场",
			columnList.add(String.valueOf(goodsDistribution.getTransferCenterName()));
			//"到达货量",
			columnList.add(String.valueOf(goodsDistribution.getArriveCargo()));
			//"出发货量",
			columnList.add(String.valueOf(goodsDistribution.getDepartCargo()));
			//"实际流入量",
			columnList.add(String.valueOf(goodsDistribution.getActualInCargo()));
			//"实际流出量 	",
			columnList.add(String.valueOf(goodsDistribution.getActualOutCargo()));
			//"货台库存",
			columnList.add(String.valueOf(goodsDistribution.getGoodsStockWeight()));
			
			rowGoodsList.add(columnList);
		}
		ExportResource goodsResource = new ExportResource();
		goodsResource.setHeads(PlatformConstants.EXCEL_HEADER_GOODS_DISTRIBUTION_DAY);
		if(CollectionUtils.isEmpty(rowGoodsList)){
			rowGoodsList.add(new ArrayList<String>(0));
		}
		goodsResource.setRowList(rowGoodsList);
		ArrayList<ExportResource> exportResources = new ArrayList<ExportResource>();
		exportResources.add(goodsResource);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(PlatformConstants.SHEET_SIZE);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream = objExcelExportor.exportBySheet(exportResources, exportSetting, new String[] {PlatformConstants.GOODS_DISTRIBUTION_DAY_SHEET_NAME});
		return excelStream;
	}
	
	/**
	 * 按月均 转运场货量流动分布 导出 
	 * @Author 200978
	 * 2015-3-13
	 * @param goodsDistributionEntity
	 * @return
	 * @throws TfrBusinessException
	 */
	public InputStream goodsDistributionByMonthExport(GoodsDistributionEntity goodsDistributionEntity) throws TfrBusinessException{

		InputStream excelStream = null;
		List<GoodsDistributionEntity> goodsDistributionList = new ArrayList<GoodsDistributionEntity>();
		// 获取导出信息
		goodsDistributionList = this.queryGoodsDistributionByMonth(goodsDistributionEntity);
		// 行List
		List<List<String>> rowGoodsList = new ArrayList<List<String>>();
		for (GoodsDistributionEntity goodsDistribution : goodsDistributionList) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//"月份",
			columnList.add(String.valueOf(DateUtils.convert(goodsDistribution.getStaDate(), "yyyy-MM")));
			//"时间点",
			columnList.add(String.valueOf(goodsDistribution.getStaTime()));
			//"转运场",
			columnList.add(String.valueOf(goodsDistribution.getTransferCenterName()));
			//"到达货量",
			columnList.add(String.valueOf(goodsDistribution.getArriveCargo()));
			//"出发货量",
			columnList.add(String.valueOf(goodsDistribution.getDepartCargo()));
			//"实际流入量",
			columnList.add(String.valueOf(goodsDistribution.getActualInCargo()));
			//"实际流出量 	",
			columnList.add(String.valueOf(goodsDistribution.getActualOutCargo()));
			//"货台库存",
			columnList.add(String.valueOf(goodsDistribution.getGoodsStockWeight()));
			
			rowGoodsList.add(columnList);
		}
		ExportResource goodsResource = new ExportResource();
		goodsResource.setHeads(PlatformConstants.EXCEL_HEADER_GOODS_DISTRIBUTION_MONTH);
		if(CollectionUtils.isEmpty(rowGoodsList)){
			rowGoodsList.add(new ArrayList<String>(0));
		}
		goodsResource.setRowList(rowGoodsList);
		ArrayList<ExportResource> exportResources = new ArrayList<ExportResource>();
		exportResources.add(goodsResource);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(PlatformConstants.SHEET_SIZE);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream = objExcelExportor.exportBySheet(exportResources, exportSetting, new String[] {PlatformConstants.GOODS_DISTRIBUTION_MONTH_SHEET_NAME});
		return excelStream;
	}
	/**
	 * @param platformCommonService the platformCommonService to set
	 */
	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
	
	
}

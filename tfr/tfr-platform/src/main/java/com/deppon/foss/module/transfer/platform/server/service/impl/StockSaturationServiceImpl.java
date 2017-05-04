package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITitleBaseInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationSmsDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IDailyLoadVolumeService;
import com.deppon.foss.module.transfer.platform.api.server.service.IQuantityStaService;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockSaturationService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.StockSaturationConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.DailyLoadVolumeEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PersonForTransferEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationSmsEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DailyLoadVolumeDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;


/**
* @description 仓库饱和度Service
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月27日 下午4:38:41
*/
public class StockSaturationServiceImpl implements IStockSaturationService {
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	/**
	* @fields stockSaturationDao
	* @author 14022-foss-songjie
	* @update 2014年3月27日 下午4:39:10
	* @version V1.0
	*/
	private IStockSaturationDao stockSaturationDao;
	
	
	/**
	* @fields stockSaturationSmsDao
	* @author 14022-foss-songjie
	* @update 2014年4月24日 下午2:43:33
	* @version V1.0
	*/
	private IStockSaturationSmsDao stockSaturationSmsDao;
	
	/**
	 * 组织部门service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 仓库预警短信接收岗位基础资料Service层接口
	 */
	private ITitleBaseInfoService titleBaseInfoService;
	
	/**
	 * 库存副表的Service
	* @fields stockPairService
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午10:23:50
	* @version V1.0
	*/
	private IStockPairService stockPairService; 
	
	
	/**
	 * 日承载货量service
	* @fields dailyLoadVolumeService
	* @author 14022-foss-songjie
	* @update 2014年7月1日 上午11:10:56
	* @version V1.0
	*/
	private IDailyLoadVolumeService dailyLoadVolumeService;
	
	
	/**
	 * 用来操作交互“短信信息”的数据库对应数据访问Service接口
	* @fields smsSendLogService
	* @author 14022-foss-songjie
	* @update 2014年4月12日 下午4:54:30
	* @version V1.0
	*/
	private ISMSSendLogService smsSendLogService;
	
	/**
	 * 货量预测的Service
	* @fields forecastServicePlatform
	* @author 14022-foss-songjie
	* @update 2014年4月9日 上午8:44:47
	* @version V1.0
	*/
	private IQuantityStaService quantityStaService;
	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;
	
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}
	
	
	
	/**
	 * set 仓库预警短信接收岗位基础资料Service层接口
	 * @param titleBaseInfoService
	 */
	public void setTitleBaseInfoService(ITitleBaseInfoService titleBaseInfoService) {
		this.titleBaseInfoService = titleBaseInfoService;
	}




	/**
	 * 用来操作交互“短信信息”的数据库对应数据访问Service接口
	* @description 用一句话说明这个方法做什么
	* @param smsSendLogService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 下午4:54:54
	*/
	public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
		this.smsSendLogService = smsSendLogService;
	}
	
	
	
	
	/**
	* @description 日承载货量service
	* @param dailyLoadVolumeService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年7月1日 上午11:11:19
	*/
	public void setDailyLoadVolumeService(
			IDailyLoadVolumeService dailyLoadVolumeService) {
		this.dailyLoadVolumeService = dailyLoadVolumeService;
	}

	
	/**
	* @description 用来操作交互“短信信息”的数据库对应数据访问Service接口
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月16日 下午2:48:50
	*/
	public ISMSSendLogService getSmsSendLogService() {
		return smsSendLogService;
	}
	


	/**
	* @description 用来操作交互“短信信息”的数据库对应数据访问Service接口
	* @param stockSaturationSmsDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月16日 下午2:48:54
	*/
	public void setStockSaturationSmsDao(
			IStockSaturationSmsDao stockSaturationSmsDao) {
		this.stockSaturationSmsDao = stockSaturationSmsDao;
	}


	/**
	* @description设置 库存副表的Service
	* @param stockPairService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午10:23:50
	*/
	public void setStockPairService(IStockPairService stockPairService) {
		this.stockPairService = stockPairService;
	}


	/**
	* @description stockSaturationDao
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 下午4:39:15
	*/
	public IStockSaturationDao getStockSaturationDao() {
		return stockSaturationDao;
	}

	
	
	
	/**
	* @description 货量预测的Service
	* @param forecastServicePlatform
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月9日 上午9:51:15
	*/
	public void setQuantityStaService(IQuantityStaService quantityStaService) {
		this.quantityStaService = quantityStaService;
	}




	/**
	* @description 组织部门service
	* @param orgAdministrativeInfoComplexService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:54:11
	*/
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}




	/**
	* @description stockSaturationDao
	* @param stockSaturationDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 下午4:39:17
	*/
	public void setStockSaturationDao(IStockSaturationDao stockSaturationDao) {
		this.stockSaturationDao = stockSaturationDao;
	}


	
	/**
	* @Title: queryOutfieldInfo 
	* @Description: 查询外场信息，查询不到则视为统计部门
	* @author shiwei shiwei@outlook.com
	* @date 2014年3月3日 上午9:49:35 
	* @param @return    设定文件 
	* @return String[]    返回类型 
	* @throws
	 */
	@Override
	public String[] queryOutfieldInfo() {
		//获取当前登录部门code
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门code name字符串数组
			return new String[]{orgAdministrativeInfoEntity.getCode(),orgAdministrativeInfoEntity.getName()};
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级转运场失败，视为统计部门登录！##########");
			return null;
		}
	}
	
	




	@Override
	public String[] queryOutfieldInfo(String orgCode) {
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门code name字符串数组
			return new String[]{orgAdministrativeInfoEntity.getCode(),orgAdministrativeInfoEntity.getName()};
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级转运场失败，视为统计部门登录！##########");
			return null;
		}
	}


	/**
	* @description  定时任务执行的service
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IStockSaturationService#doStockSaturationJob(java.util.Date, int, int)
	* @author 14022-foss-songjie
	* @update 2014年3月27日 下午4:55:02
	* @version V1.0
	*/
	@Override
	public void doStockSaturationJob(Date queryDate, int threadNo,
			int threadCount) throws Exception {
		String queryDateStrA = DateUtils.convert(DateUtils.addDayToDate(queryDate, -1), DateUtils.DATE_FORMAT);
		String queryDateStr = DateUtils.convert(queryDate,DateUtils.DATE_FORMAT);
		queryDate = DateUtils.convert(queryDateStr, DateUtils.DATE_FORMAT);
		//查询对应的数据
		Map<String,String> map = new HashMap<String, String>();
		map.put("threadCount", threadCount+"");
		map.put("threadNo", threadNo+"");
		map.put("queryDateA", queryDateStrA);
		map.put("queryDateB", queryDateStr);
		
		List<StockSaturationEntity> list = stockSaturationDao.stockSaturationJobQuery(map);
		if(list!=null && list.size()>0){
			for (StockSaturationEntity stockSaturationEntity : list) {
				stockSaturationEntity.setSaturationId(UUIDUtils.getUUID());
				//统计的数据为12点到12点 = 定时任务执行时间 减去 1天的日期(下午12点的日期时间)
				stockSaturationEntity.setStatisticsTimeTheory(DateUtils.addDayToDate(queryDate, -1));
				DailyLoadVolumeDto dayPojo = queryDailyLoadVolume(stockSaturationEntity.getOrgCode(), DateUtils.addDayToDate(queryDate, -1));
				BigDecimal warnBigDec = null;
				BigDecimal dangerBigDec = null;
				try{
					warnBigDec = dayPojo.getFullValue();
				}catch(Exception e){
					warnBigDec = BigDecimal.ZERO;
				}
				try{
					dangerBigDec = dayPojo.getDangerValue();
				}catch(Exception ex){
					dangerBigDec = BigDecimal.ZERO;
				}
				stockSaturationEntity.setWarnrange(warnBigDec);//警戒值
				stockSaturationEntity.setDangerrange(dangerBigDec);//危险值
				//stockSaturationEntity.setSmssendflag(FossConstants.NO);//默认未发短信
				//添加月承载量，日操作货量，月操作货量，日饱和度，月饱和度
				//日承载量
				stockSaturationEntity.setSustainDayMeasure(dayPojo.getDailyLoadVolume());
				//月承载量
				stockSaturationEntity.setSustainMonthMeasure(dayPojo.getMonthLoadVolume());
				//日操作货量
				//计算日操作货量 
				stockSaturationEntity = sumOperateMeasureDay(stockSaturationEntity);
				stockSaturationEntity.setOperateMeasureDay(stockSaturationEntity.getOperateMeasureDay());
				//月操作货量
				//计算月操作货量 
				stockSaturationEntity = sumOperateMeasureMonth(stockSaturationEntity, DateUtils.addDayToDate(queryDate, -1), stockSaturationEntity.getOrgCode());
				stockSaturationEntity.setOperateMeasureMonth(stockSaturationEntity.getOperateMeasureMonth());
				//计算饱和度  转换为吨
				stockSaturationEntity = countStockSaturation(stockSaturationEntity,StockSaturationConstants.KILOTOTONYES);
				//日饱和度
				stockSaturationEntity.setSaturationDay(stockSaturationEntity.getSaturationDay());
				//月饱和度
				stockSaturationEntity.setSaturationMonth(stockSaturationEntity.getSaturationMonth());
				
				stockSaturationDao.insertStockSaturation(stockSaturationEntity);
			}
		}
		
	}
	
	/**
	* @description 仓库饱和度查询
	* @param pojo
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月1日 上午10:06:29
	*/
	@Override
	public List<StockSaturationEntity> queryStockSaturationList(
			StockSaturationEntity pojo, int start, int limit) {
		//查询对应的数据
		Map<String,String> map = new HashMap<String, String>();
		map.put("orgCode", pojo.getOrgCode());
		//String beginDateStr = pojo.getBeginDate();
		String endDateStr = pojo.getEndDate();
		//Date dateA = DateUtils.convert(beginDateStr, DateUtils.DATE_FORMAT);
		Date dateB = DateUtils.convert(endDateStr, DateUtils.DATE_FORMAT);
		//map.put("queryDateA", DateUtils.convert(dateA, DateUtils.DATE_FORMAT));
		map.put("queryDateB", DateUtils.convert(dateB, DateUtils.DATE_FORMAT));
		
		//当前查询的部门 
		List<String> orgList=null;
		
		List<StockSaturationEntity> dbStockSaturationList = stockSaturationDao.queryStockSaturationList(map, start, limit);
		
		if(dbStockSaturationList!=null){
			//需要货量预测的时间集合
			List<Date> forecastDateList = decideIsOrNoForecast(dateB);
			//货量预测的所有数据
			List<StockSaturationEntity> forecastListAll = new ArrayList<StockSaturationEntity>();
			//需要货量预测的数据
			if(forecastDateList!=null && forecastDateList.size()>0){
				//抽取查询结果对应的外场code
				List<String> orgCodeList = getDBQueryOrgCode(dbStockSaturationList);
				orgList=orgCodeList;
				for (Date time : forecastDateList) {
					//货量预测的某一日数据
					List<StockSaturationEntity> forecastListToDay = forecastMeasure(orgCodeList,time);
					if(forecastListToDay!=null){
						forecastListAll.addAll(forecastListToDay);
					}
				}
				
			}
			
			//所有外场对应的本部和事业部
			List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllOutTransOrg();
			
			if(forecastListAll!=null && forecastListAll.size()>0){
				//合并预测的数据的操作货量
				forecastListAll = sumStockSaturationAll(forecastListAll,pojo.getOrgCode());
				for (StockSaturationEntity stockSaturationEntity : forecastListAll) {
					//计算饱和度(货量预测的)
					stockSaturationEntity = countStockSaturation(stockSaturationEntity,StockSaturationConstants.KILOTOTONYES);
				}
				//计算饱和度 以及匹配本部、事业部
				List<StockSaturationEntity> backList = bigdeptAndDivision(forecastListAll,transCenterList);
				//统计当月预警天数与当月危险预警天数
				if(CollectionUtils.isEmpty(orgList))
				{
					orgList=getDBQueryOrgCode(dbStockSaturationList);
				}
				List<StockSaturationEntity> calculateList=calculateWarningAndDangerMothDays(orgList,pojo.getEndDate());
				handResutList(backList,calculateList);
				
				return backList;
			}else{
				//计算饱和度 以及匹配本部、事业部
				List<StockSaturationEntity> backList = bigdeptAndDivision(dbStockSaturationList,transCenterList);
				//统计当月预警天数与当月危险预警天数
				if(CollectionUtils.isEmpty(orgList))
				{
					orgList=getDBQueryOrgCode(dbStockSaturationList);
				}
				List<StockSaturationEntity> calculateList=calculateWarningAndDangerMothDays(orgList,pojo.getEndDate());
				handResutList(backList,calculateList);
				return backList;
			}
		}else{
			return null;
		}
	}
	

	/**
	* @description 仓库饱和度导出
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 上午10:15:10
	*/
	@Override
	public InputStream exportStockSaturationList(
			StockSaturationEntity pojo) {
		InputStream excelStream = null;
		try{
			List<StockSaturationEntity> dbList = queryStockSaturationList(pojo,-1,-1);
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (StockSaturationEntity stockSaturationEntity : dbList) {
				List<String> columnList = new ArrayList<String>();
				//"本部","事业部","外场","日承载货量(T)","当日操作货量(T)","当日仓库饱和度","当月操作货量(T)","当月仓库饱和度" "当月预警天数","当月危险预警天数"
				columnList.add(stockSaturationEntity.getBigdept());
				columnList.add(stockSaturationEntity.getDivision());
				columnList.add(stockSaturationEntity.getOrgName());
				columnList.add(stockSaturationEntity.getSustainDayMeasure()==null?"0":stockSaturationEntity.getSustainDayMeasure()+"");
				columnList.add(stockSaturationEntity.getOperateMeasureDay()==null?"0":stockSaturationEntity.getOperateMeasureDay()+"");
				columnList.add(stockSaturationEntity.getSaturationDay()==null?"0":stockSaturationEntity.getSaturationDay()+"%");
				columnList.add(stockSaturationEntity.getOperateMeasureMonth()==null?"0":stockSaturationEntity.getOperateMeasureMonth()+"");
				columnList.add(stockSaturationEntity.getSaturationMonth()==null?"0":stockSaturationEntity.getSaturationMonth()+"%");
				columnList.add(stockSaturationEntity.getWarningMothDayCount()+"");
				columnList.add(stockSaturationEntity.getDangerMothDayCount()+"");
				rowList.add(columnList);
			}
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(StockSaturationConstants.STOCK_SATURATION_ROW_HEADS);
			sheetData.setRowList(rowList);
			
			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, StockSaturationConstants.STOCK_SATURATION_SHEET_NAME,
					StockSaturationConstants.SHEET_SIZE));
		}catch(Exception e){
			LOGGER.error("导出仓库饱和度异常", e);
			throw new BusinessException("导出仓库饱和度异常", e);
		}
		return excelStream;
	}


	/**
	* @description 日饱和度
	* @param map 外场code 和日期必须有
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 下午7:58:03
	*/
	@Override
	public List<StockSaturationEntity> queryStockSaturationDayList(
			StockSaturationEntity pojo) {
		//查询对应的数据
		Map<String,String> map = new HashMap<String, String>();
		map.put("orgCode", pojo.getOrgCode());
		String beginDateStr = pojo.getBeginDate();
		String endDateStr = pojo.getEndDate();
		Date dateA = DateUtils.convert(beginDateStr, DateUtils.DATE_FORMAT);
		Date dateB = DateUtils.convert(endDateStr, DateUtils.DATE_FORMAT);
		map.put("queryDateA", DateUtils.convert(dateA, DateUtils.DATE_FORMAT));
		map.put("queryDateB", DateUtils.convert(dateB, DateUtils.DATE_FORMAT));
		
		List<StockSaturationEntity> dbStockSaturationList = stockSaturationDao.queryStockSaturationDayList(map);
		if(dbStockSaturationList!=null){
			//需要货量预测的时间集合
			List<Date> forecastDateList = decideIsOrNoForecast(dateB);
			//货量预测的所有数据
			List<StockSaturationEntity> forecastListAll = new ArrayList<StockSaturationEntity>();
			//需要货量预测的数据
			if(forecastDateList!=null && forecastDateList.size()>0){
				//抽取查询结果对应的外场code
				List<String> orgCodeList = new ArrayList<String>();
				orgCodeList.add(pojo.getOrgCode());
				if(StringUtils.isNotEmpty(pojo.getOrgCode())){
					for (Date time : forecastDateList) {
						//货量预测的某一日数据
						List<StockSaturationEntity> forecastListToDay = forecastMeasure(orgCodeList,time);
						if(forecastListToDay!=null){
							forecastListAll.addAll(forecastListToDay);
						}
					}
				}
				
			}
			
			if(forecastListAll!=null && forecastListAll.size()>0){
				//合并预测的数据的操作货量
				forecastListAll = sumStockSaturationAll(forecastListAll,pojo.getOrgCode());
				for (StockSaturationEntity stockSaturationEntity : forecastListAll) {
					//计算饱和度(货量预测的)
					stockSaturationEntity = countStockSaturation(stockSaturationEntity,StockSaturationConstants.KILOTOTONYES);
				}
				
				if(dbStockSaturationList!=null && dbStockSaturationList.size()>0){
					for (StockSaturationEntity dbEntity : dbStockSaturationList) {
						for (StockSaturationEntity entity : forecastListAll) {
							managerForecastListAll(dbEntity, entity);
						}
						
					}
				}
			}
			return dbStockSaturationList;
		}else{//返回预测的数据
			//需要货量预测的时间集合
			List<Date> forecastDateList = decideIsOrNoForecast(dateB);
			//货量预测的所有数据
			List<StockSaturationEntity> forecastListAll = new ArrayList<StockSaturationEntity>();
			//需要货量预测的数据
			if(forecastDateList!=null && forecastDateList.size()>0){
				//抽取查询结果对应的外场code
				List<String> orgCodeList = new ArrayList<String>();
				orgCodeList.add(pojo.getOrgCode());
				if(StringUtils.isNotEmpty(pojo.getOrgCode())){
					for (Date time : forecastDateList) {
						//货量预测的某一日数据
						List<StockSaturationEntity> forecastListToDay = forecastMeasure(orgCodeList,time);
						if(forecastListToDay!=null){
							forecastListAll.addAll(forecastListToDay);
						}
					}
				}
				
			}
			
			if(forecastListAll!=null && forecastListAll.size()>0){
				//合并预测的数据的操作货量
				forecastListAll = sumStockSaturationAll(forecastListAll,pojo.getOrgCode());
				for (StockSaturationEntity stockSaturationEntity : forecastListAll) {
					//计算饱和度(货量预测的)
					stockSaturationEntity = countStockSaturation(stockSaturationEntity,StockSaturationConstants.KILOTOTONYES);
				}
			}
			return forecastListAll;
		}
	}


	
	/**
	 * sonar优化 减少一个内嵌 
	 * @param dbEntity
	 * @param entity
	 */
	private void managerForecastListAll(StockSaturationEntity dbEntity,
			StockSaturationEntity entity) {
		String dbDateStr = dbEntity.getTimeStatisticsGroup();
		String dateStr = entity.getTimeStatisticsGroup();
		if(dateStr.equals(dbDateStr)){
			dbEntity.setOperateMeasureDay(entity.getOperateMeasureDay());
			dbEntity.setOperateMeasureMonth(entity.getOperateMeasureMonth());
			dbEntity.setSustainDayMeasure(entity.getSustainDayMeasure());
			dbEntity.setSustainMonthMeasure(entity.getSustainMonthMeasure());
			dbEntity.setSaturationDay(entity.getSaturationDay());
			dbEntity.setSaturationMonth(entity.getSaturationMonth());
		}
	}

	
	/**
	* @description 日饱和度导出
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午1:55:15
	*/
	@Override
	public InputStream exportStockSaturationDayList(StockSaturationEntity pojo) {
		InputStream excelStream = null;
		try{
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			List<StockSaturationEntity> dbList = queryStockSaturationDayList(pojo);
			for (StockSaturationEntity stockSaturationEntity : dbList) {
				List<String> columnList = new ArrayList<String>();
				//"日期","当日的操作货量(T)","当日承载货量(T)","当日仓库饱和度"
				columnList.add(stockSaturationEntity.getTimeStatisticsGroup());
				columnList.add(stockSaturationEntity.getOperateMeasureDay()+"");
				columnList.add(stockSaturationEntity.getSustainDayMeasure()+"");
				columnList.add(stockSaturationEntity.getSaturationDay()+"");
				rowList.add(columnList);
			}
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(StockSaturationConstants.STOCK_SATURATION_DAY_ROW_HEADS);
			sheetData.setRowList(rowList);
			
			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, StockSaturationConstants.STOCK_SATURATION_DAY_SHEET_NAME,
					StockSaturationConstants.SHEET_SIZE));
		}catch(Exception e){
			LOGGER.error("导出日仓库饱和度异常", e);
			throw new BusinessException("导出日仓库饱和度异常", e);
		}
		return excelStream;
	}


	/**
	* @description 月仓库饱和度
	* @param map 外场code 和日期必须有
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 下午7:58:03
	*/
	@Override
	public List<StockSaturationEntity> queryStockSaturationMonthList(
			StockSaturationEntity pojo) {
		//查询对应的数据
		Map<String,String> map = new HashMap<String, String>();
		map.put("orgCode", pojo.getOrgCode());
		String beginDateStr = pojo.getBeginDate();
		String endDateStr = pojo.getEndDate();
		map.put("queryDateA", beginDateStr);
		map.put("queryDateB", endDateStr);
		List<StockSaturationEntity> dbStockSaturationList = stockSaturationDao.queryStockSaturationMonthList(map);
		if(dbStockSaturationList!=null){
//			for (StockSaturationEntity stockSaturationEntity : dbStockSaturationList) {
//				//合计日操作货量
//				stockSaturationEntity = sumOperateMeasureDay(stockSaturationEntity);
//				//该方法的日操作货量实际值是月的操作货量
//				stockSaturationEntity.setOperateMeasureMonth(stockSaturationEntity.getOperateMeasureDay());
//				//计算饱和度  转换为吨
//				stockSaturationEntity = countStockSaturation(stockSaturationEntity,StockSaturationConstants.KILOTOTONNO);
//			}
			return dbStockSaturationList;
		}else{
			return null;
		}
	}
	
	
	/**
	* @description 月仓库饱和度导出
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午2:09:52
	*/
	@Override
	public InputStream exportStockSaturationMonthList(StockSaturationEntity pojo) {
		InputStream excelStream = null;
		try{
			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			List<StockSaturationEntity> dbList = queryStockSaturationMonthList(pojo);
			for (StockSaturationEntity stockSaturationEntity : dbList) {
				List<String> columnList = new ArrayList<String>();
				//"月份","当月的操作货量(T)","当月承载货量(T)","当月仓库饱和度"
				columnList.add(stockSaturationEntity.getTimeStatisticsGroup());
				columnList.add(stockSaturationEntity.getOperateMeasureDay()+"");
				columnList.add(stockSaturationEntity.getSustainDayMeasure()+"");
				columnList.add(stockSaturationEntity.getSaturationDay()+"");
				rowList.add(columnList);
			}
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(StockSaturationConstants.STOCK_SATURATION_MONTH_ROW_HEADS);
			sheetData.setRowList(rowList);
			
			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, StockSaturationConstants.STOCK_SATURATION_MONTH_SHEET_NAME,
					StockSaturationConstants.SHEET_SIZE));
		}catch(Exception e){
			LOGGER.error("导出月仓库饱和度异常", e);
			throw new BusinessException("导出月仓库饱和度异常", e);
		}
		return excelStream;
	}


	/**
	* @description 仓库饱和度查询的总记录数
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月1日 上午10:07:15
	*/
	@Override
	public long queryStockSaturationListCount(StockSaturationEntity pojo) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("orgCode", pojo.getOrgCode());
		return stockSaturationDao.queryStockSaturationListCount(map);
	}

	/**
	* @description 判断当前时间是否大于12点
	* @return true:中午12点之后； false:12点之前
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午9:58:40
	*/
	@Override
	public boolean decideTimeTwelve(){
		Calendar c = Calendar.getInstance();
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		return currentHour>=PlatformConstants.SONAR_NUMBER_12;
	}
	
	/**
	* @description 根据已统计到的历史数据的最大日期 来判断调用货量预测 具体日期的数据
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午8:47:22
	*/
	private List<Date> decideIsOrNoForecast(Date endDate){
		List<Date> backDateList = null;
		if(endDate!=null){
			
			long yestodayDiff = DateUtils.getTimeDiff(DateUtils.addDayToDate(new Date(), -1),endDate);
			if(yestodayDiff==0){
				//判读是否大于中午12点
				if(!decideTimeTwelve()){
					backDateList = new ArrayList<Date>();
					backDateList.add(endDate);
					return backDateList;
				}
				
			}
			//当前日期减一 和 截至日期做比较
			long dayDiff = DateUtils.getTimeDiff(new Date(), endDate);
			int dayDiffInt = Integer.parseInt(dayDiff+"");
			if(dayDiffInt>=0){
				backDateList = new ArrayList<Date>();
				//判读是否大于中午12点
				if(decideTimeTwelve()){
					backDateList.add(DateUtils.addDayToDate(new Date(), 1));
					backDateList.add(new Date());
				}else{
					backDateList.add(new Date());
					backDateList.add(DateUtils.addDayToDate(new Date(), -1));
				}
				
			}else{
				//截至为昨日 历史数据理论上已经统计 无需调用货量预测的数据
			}
		}
		return backDateList;
	}
	
	/**
	* @description 货量预测的数据（缺少月操作货量，需要月操作货量时需要单独计算）
	* @param orgCodeList
	* @param forecastDate
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月16日 下午3:21:52
	*/
	private List<StockSaturationEntity> forecastMeasure(List<String> orgCodeList,Date forecastDate){
		List<StockSaturationEntity>  backList = new ArrayList<StockSaturationEntity>();
		try{
			Date queryDate = querySaturationCurrentDay(forecastDate);
			if(orgCodeList!=null && orgCodeList.size()>0){
				for (String transferCenterCode : orgCodeList) {
					StockSaturationEntity pojo = new StockSaturationEntity();
					DailyLoadVolumeDto volumeDayPojo = new DailyLoadVolumeDto();
					volumeDayPojo = queryDailyLoadVolume(transferCenterCode,queryDate);
					//外场code
					pojo.setOrgCode(transferCenterCode);
					List<QuantityStaDto> staDto = quantityStaService.queryTotalFcst(transferCenterCode, forecastDate);
					if(staDto!=null&& staDto.size()>0){
						QuantityStaDto quantityStaDto = staDto.get(0);
						//日操作货量
						if(quantityStaDto.getForecastWeightTotal()!=null){
							BigDecimal b = new BigDecimal(PlatformConstants.SONAR_NUMBER_1000);
							pojo.setOperateMeasureDay(quantityStaDto.getForecastWeightTotal().multiply(b));
						}else{
							pojo.setOperateMeasureDay(BigDecimal.ZERO);
						}
					}else{
						//日操作货量
						pojo.setOperateMeasureDay(BigDecimal.ZERO);
					}
					
					//日承载量
					pojo.setSustainDayMeasure(volumeDayPojo.getDailyLoadVolume());
					//月操作货量
					//pojo.setOperateMeasureMonth(pojo.getOperateMeasureMonth());
					//月承载量
					pojo.setSustainMonthMeasure(volumeDayPojo.getMonthLoadVolume());
					//计算饱和度
					//pojo = countStockSaturation(pojo,StockSaturationConstants.KILOTOTONYES);
					//日期==日期+（系统当前查询的时分秒）
					String currentDateStr = DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT);
					String forecastDateStr = DateUtils.convert(forecastDate, DateUtils.DATE_FORMAT)+currentDateStr.substring(PlatformConstants.SONAR_NUMBER_10);
					pojo.setStatisticsTimeTheory(DateUtils.convert(forecastDateStr));
					pojo.setTimeStatisticsGroup(DateUtils.convert(forecastDate,DateUtils.DATE_FORMAT));
					backList.add(pojo);
				}
			}
		}catch (Exception e) {
			LOGGER.error("调用forecastServicePlatform.queryForecastWeightByRelevantOrgCode 接口出错", e);
			return null;
		}
		return backList;
	}
	
	
	/**
	* @description 将查询的数据
	* @param dbStockSaturationList
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午9:07:58
	*/
	private List<String> getDBQueryOrgCode(List<StockSaturationEntity> dbStockSaturationList){
		if(dbStockSaturationList!=null){
			List<String> orgCodeList = new ArrayList<String>();
			for (StockSaturationEntity stockSaturationEntity : dbStockSaturationList) {
				orgCodeList.add(stockSaturationEntity.getOrgCode());
			}
			return orgCodeList;
		}else{
			return null;
		}
	}
	
	
	/**
	* @description 计算饱和度
	* @param stockSaturationEntity
	* @param kiloToTonFlag 0:无需单位转换；1：将千克转换为吨
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年7月3日 下午2:52:51
	*/
	private StockSaturationEntity countStockSaturation(StockSaturationEntity stockSaturationEntity,int kiloToTonFlag){
		if(stockSaturationEntity!=null){
			//日承载货量
			BigDecimal a = stockSaturationEntity.getSustainDayMeasure();
			if(kiloToTonFlag==1){
				a = kiloToTon(a);
				stockSaturationEntity.setSustainDayMeasure(a);
			}
			//日操作货量
			BigDecimal b = stockSaturationEntity.getOperateMeasureDay();
			if(kiloToTonFlag==1){
				b = kiloToTon(b);
				stockSaturationEntity.setOperateMeasureDay(b);
			}
			//日饱和度
			if(b!=null && a!=null){
				try{
					BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
					c = c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
					stockSaturationEntity.setSaturationDay(c);
				}catch(Exception e){
					stockSaturationEntity.setSaturationDay(BigDecimal.ZERO);
				}
			}else{
				stockSaturationEntity.setSaturationDay(BigDecimal.ZERO);
			}
			
			//月承载货量
			BigDecimal d = stockSaturationEntity.getSustainMonthMeasure();
			if(kiloToTonFlag==1){
				d = kiloToTon(d);
				stockSaturationEntity.setSustainMonthMeasure(d);
			}
			//月操作货量
			BigDecimal e = stockSaturationEntity.getOperateMeasureMonth();
			if(kiloToTonFlag==1){
				e = kiloToTon(e);
				stockSaturationEntity.setOperateMeasureMonth(e);
			}
			//月饱和度
			if(e!=null && d!=null){
				try{
					BigDecimal f = e.divide(d, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
					f = f.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
					stockSaturationEntity.setSaturationMonth(f);
				}catch(Exception e1){
					stockSaturationEntity.setSaturationMonth(BigDecimal.ZERO);
				}
			}else{
				stockSaturationEntity.setSaturationMonth(BigDecimal.ZERO);
			}
			return stockSaturationEntity;
		}else{
			return null;
		}
	}
	
	/**
	* @description 匹配本部、事业部
	* @param dbStockSaturationList
	* @param transCenterList 不为空时:需要填充本部、事业部； 为空时:不需要填充本部、事业部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 下午8:14:24
	*/
	private List<StockSaturationEntity> bigdeptAndDivision(List<StockSaturationEntity> dbStockSaturationList,List<TransCenterOrgEntity> transCenterList){
		if(dbStockSaturationList!=null && dbStockSaturationList.size()>0){
			for (StockSaturationEntity stockSaturationEntity : dbStockSaturationList) {
				if(transCenterList!=null){
					//外场code
					String orgCode = stockSaturationEntity.getOrgCode();
					TransCenterOrgEntity tcoe = queryNameOrgUp(orgCode,transCenterList);
					//本部
					stockSaturationEntity.setBigdept(tcoe.getBigdept());
					//事业部
					stockSaturationEntity.setDivision(tcoe.getDivision());
				}
			}
			return dbStockSaturationList;
		}else{
			return null;
		}
	}
	
	
	/**
	* @description 合并货量预测总的操作货量
	* @param dbStockSaturationList
	* @param forecastListAll
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 下午5:44:48
	*/
	private List<StockSaturationEntity> sumStockSaturationAll(List<StockSaturationEntity> forecastListAll,String orgOutCode){
		//合计月操作货量、月承载量
		if(forecastListAll!=null && forecastListAll.size()>0){//有货量预测的数据
			//重新组合后需要返回的集合
			List<StockSaturationEntity> newPojoList = new ArrayList<StockSaturationEntity>();
			
			//全国所有的外场
			List<TransCenterOrgEntity> transCenterList = stockPairService.queryAllOutTransOrg();
			
			//对外场进行分组合计
			 if(StringUtils.isNotBlank(orgOutCode)){//针对部门的查询
							//将货量预测的数据进行合并
				for (StockSaturationEntity forecastPojo : forecastListAll) {
					int breakForFlag = 0;//标示newPojoList里是否包含了orgOutCode
					for (StockSaturationEntity sse : newPojoList) {
						if(StringUtils.equals(sse.getOrgCode(), orgOutCode) && forecastPojo.getOperateMeasureDay()!=null){
							//月操作货量 合计月操作货量
							sse.setOperateMeasureMonth(sse.getOperateMeasureMonth().add(forecastPojo.getOperateMeasureDay()));
							breakForFlag=1;
							break;
						}
					}
					 if(breakForFlag==0){
						 StockSaturationEntity newPojo = new StockSaturationEntity();
							newPojo.setOrgCode(orgOutCode);
							TransCenterOrgEntity tcoe = queryNameOrgUp(orgOutCode,transCenterList);
							if(tcoe!=null){
								newPojo.setOrgName(tcoe.getName());
							}
							
						 	//日承载量
							newPojo.setSustainDayMeasure(forecastPojo.getSustainDayMeasure());
							//月承载量
							newPojo.setSustainMonthMeasure(forecastPojo.getSustainMonthMeasure());
							//日操作货量
							newPojo.setOperateMeasureDay(forecastPojo.getOperateMeasureDay());
							//月操作货量初始化0
							newPojo.setOperateMeasureMonth(BigDecimal.ZERO);
							//月操作货量 (货量预测的日操作货量，在月操作货量字段内容)
							if(forecastPojo.getOperateMeasureDay()!=null){
								newPojo.setOperateMeasureMonth(forecastPojo.getOperateMeasureDay());
							}
							newPojo.setStatisticsTimeTheory(forecastPojo.getStatisticsTimeTheory());
							newPojo.setTimeStatisticsGroup(forecastPojo.getTimeStatisticsGroup());
							newPojoList.add(newPojo);
					 }
				}
			 }else{//针对全国的
						//将货量预测的数据进行合并
				for (StockSaturationEntity forecastPojo : forecastListAll) {
					 for (TransCenterOrgEntity transCenterOrgEntity : transCenterList) {
						 if(StringUtils.equals(forecastPojo.getOrgCode(), transCenterOrgEntity.getOrgCode())){
							 //相等的外场已做处理
								 int breakForFlag = 0;//退出transCenterList循环的标示(1:退出循环)
								 String orgCode = transCenterOrgEntity.getOrgCode();
								 for (StockSaturationEntity sse : newPojoList) {
									if(StringUtils.equals(sse.getOrgCode(), forecastPojo.getOrgCode()) && forecastPojo.getOperateMeasureDay()!=null){
										//月操作货量 (货量预测的日操作货量，在月操作货量字段内容)
										//月操作货量 合计月操作货量
										sse.setOperateMeasureMonth(sse.getOperateMeasureMonth().add(forecastPojo.getOperateMeasureDay()));
										breakForFlag=1;
										break;
									}
								 }
								 if(breakForFlag==0 && StringUtils.equals(forecastPojo.getOrgCode(), transCenterOrgEntity.getOrgCode())){
									 StockSaturationEntity newPojo = new StockSaturationEntity();
										newPojo.setOrgCode(orgCode);
										newPojo.setOrgName(transCenterOrgEntity.getName());
										//日承载量
										newPojo.setSustainDayMeasure(forecastPojo.getSustainDayMeasure());
										//月承载量
										newPojo.setSustainMonthMeasure(forecastPojo.getSustainMonthMeasure());
										//日操作货量
										newPojo.setOperateMeasureDay(forecastPojo.getOperateMeasureDay());
										//月操作货量初始化0
										newPojo.setOperateMeasureMonth(BigDecimal.ZERO);
										//月操作货量 (货量预测的日操作货量，在月操作货量字段内容)
										if(forecastPojo.getOperateMeasureDay()!=null){
											newPojo.setOperateMeasureMonth(forecastPojo.getOperateMeasureDay());
										}
										newPojo.setStatisticsTimeTheory(forecastPojo.getStatisticsTimeTheory());
										newPojo.setTimeStatisticsGroup(forecastPojo.getTimeStatisticsGroup());
										newPojoList.add(newPojo);
									 }
							 break;
						 }
					}
				}
			 }
			 return newPojoList;
		}else{
			return null;
		}
	}
	
	
	
	/**
	* @description 合计月操作货量
	* @param stockSaturationEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年7月3日 下午4:10:32
	*/
	private StockSaturationEntity sumOperateMeasureMonth(StockSaturationEntity stockSaturationEntity,Date queryDate,String orgCode){
		if(queryDate!=null && StringUtils.isNotBlank(orgCode)){
			if(stockSaturationEntity!=null &&stockSaturationEntity.getOperateMeasureDay()!=null){
				//计算开始日期
				String queryDateStr = DateUtils.convert(queryDate, DateUtils.DATE_FORMAT);
				String endTimeSub = queryDateStr.substring(0,PlatformConstants.SONAR_NUMBER_8); 
				String beginTime = endTimeSub+"01";
				
				Map<String,String> map= new HashMap<String, String>();
				map.put("queryDateA", beginTime);
				map.put("queryDateB", queryDateStr);
				map.put("orgCode", orgCode);
				
				//查询日期之前的日操作货量之和
				try{
					stockSaturationEntity.setOperateMeasureMonth(stockSaturationDao.queryOperateMeasureMonth(map));
				}catch(Exception e){
					stockSaturationEntity.setOperateMeasureMonth(BigDecimal.ZERO);
					LOGGER.error("################查询日期之前的日操作货量之和code：" + orgCode + "查询日期是"+queryDateStr+"##########");
				}
				if(stockSaturationEntity.getOperateMeasureMonth()!=null){//查询日期之前的日操作货量之和 加上本次的日操作货量
					//临时将单位吨转换成千克
					BigDecimal omdTemp = stockSaturationEntity.getOperateMeasureMonth();
					stockSaturationEntity.setOperateMeasureMonth(omdTemp.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_1000)));
					//查询日期之前的日操作货量之和 加上本次的日操作货量=月操作货量
					BigDecimal temNum = stockSaturationEntity.getOperateMeasureMonth().add(stockSaturationEntity.getOperateMeasureDay());
					stockSaturationEntity.setOperateMeasureMonth(temNum);
				}else{
					stockSaturationEntity.setOperateMeasureMonth(stockSaturationEntity.getOperateMeasureDay());
				}
			}
		}	
		return stockSaturationEntity;
	}
	
	/**
	* @description 合计日操作货量
	* @param dbStockSaturation
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 下午5:44:48
	*/
	private StockSaturationEntity sumOperateMeasureDay(StockSaturationEntity stockSaturationEntity){
		if (stockSaturationEntity != null) {
				// 日操作货量
				if (stockSaturationEntity.getOperateMeasureDay() == null) {
					stockSaturationEntity
							.setOperateMeasureDay(new BigDecimal(0));
				}
					
				// 装车量
				if (stockSaturationEntity.getLoadMeasure() != null) {
					stockSaturationEntity
							.setOperateMeasureDay(stockSaturationEntity
									.getOperateMeasureDay()
									.add(stockSaturationEntity
											.getLoadMeasure()));
				}
				// 卸车量
				if (stockSaturationEntity.getUnloadMeasure() != null) {
					stockSaturationEntity
							.setOperateMeasureDay(stockSaturationEntity
									.getOperateMeasureDay()
									.add(stockSaturationEntity
											.getUnloadMeasure()));
				}
				// 自提
				if (stockSaturationEntity.getSelfSign() != null) {
					stockSaturationEntity
							.setOperateMeasureDay(stockSaturationEntity
									.getOperateMeasureDay()
									.add(stockSaturationEntity
											.getSelfSign()));
				}
				// 派送签收
				if (stockSaturationEntity.getDeliverSign() != null) {
					stockSaturationEntity
							.setOperateMeasureDay(stockSaturationEntity
									.getOperateMeasureDay()
									.add(stockSaturationEntity
											.getDeliverSign()));
				}
				// 派送拉回
				if (stockSaturationEntity.getDeliverBack() != null) {
					stockSaturationEntity
							.setOperateMeasureDay(stockSaturationEntity
									.getOperateMeasureDay()
									.add(stockSaturationEntity
											.getDeliverBack()));
				}
				// 驻地营业部开单
				if (stockSaturationEntity.getStationMeasure() != null) {
					stockSaturationEntity
							.setOperateMeasureDay(stockSaturationEntity
									.getOperateMeasureDay()
									.add(stockSaturationEntity
											.getStationMeasure()));
				}
				// 集中开单
				if (stockSaturationEntity.getReceiveMeasure() != null) {
					stockSaturationEntity
							.setOperateMeasureDay(stockSaturationEntity
									.getOperateMeasureDay()
									.add(stockSaturationEntity
											.getReceiveMeasure()));
				}

			return stockSaturationEntity;
		} else {
			return null;
		}
	}
	
	
	/**
	* @description 千克转吨
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IStockSaturationService#kiloToTon(java.math.BigDecimal)
	* @author 14022-foss-songjie
	* @update 2014年4月10日 下午4:03:20
	* @version V1.0
	*/
	@Override
	public BigDecimal kiloToTon(BigDecimal kilo) {
		BigDecimal back = new BigDecimal(0);
		if(kilo!=null){
			BigDecimal b = new BigDecimal(PlatformConstants.SONAR_NUMBER_1000);
			try{
				back = kilo.divide(b, 2, BigDecimal.ROUND_HALF_UP);
			}catch(Exception e){
				return back;
			}
		}
		return back;
	}

	
	/**
	 * 每隔一个小时，后台计算一次当日及未来一日仓库饱和度，其中的操作货量取自货量统计中的预测货量。
	 * 若两者（当日及未来一日仓库饱和度）之一超过危险值，则发送
	 * 领导你好，XX外场XX日仓库饱和度达到xx%，已经超过了危险值，请做好预防措施，防止发生爆仓！
	 * 当日、未来一日及此前五日的仓库饱和度中有三次超过警戒值，则向外场负责人发送预警短信。
	 * 若两者之一超过危险值，则发送
	 * 短信模板：
	 * 领导你好，XX外场（实际外场名称）近一周仓库饱和度已有三天超过警戒值，分别是：XX日xx%,XX日xx%,XX日xx%
	 * 每小时查询一次
	 */
	@Override
	public void smsStockSaturationServiceJob() {
		//根据当前时间是否大于12点判断获取的日期
		Map<String,List<String>> dateRange = smsSendForDateList();
		List<String> dangerListDateStr = dateRange.get("dangerRange");
		List<String> warnListDateStr = dateRange.get("warnRange");
		//所有外场对应负责人的联系电话
		List<PersonForTransferEntity> transCenterList = stockSaturationDao.queryPersonForTranferCenter();
		//所有外场对应的执行状态map：外场code，状态
		Map<String,String> statusOrgListMap = new HashMap<String, String>();
		//所有外场负责人对应的手机号 map：外场code，手机
		Map<String,String> photoOrgListMap = new HashMap<String, String>();
		//所有外场对应的名称 map：外场code，外场Name
		Map<String,String> orgNameListMap = new HashMap<String, String>();
		//所有外场对应的超出警戒或危险值的信息记录:外场code,内容摘要
		Map<String,Map<String,String>> contentOrgListMap = new HashMap<String, Map<String,String>>();
		List<String> orgList = new ArrayList<String>();
		for (PersonForTransferEntity transCenterOrgEntity : transCenterList) {
			orgList.add(transCenterOrgEntity.getOrgCode());
			LOGGER.info("仓库预警外场"+transCenterOrgEntity.getOrgCode());
			//初始化 0：无值  D1\D2:危险值有数据 W1\W2\W3\W4\W5\W6\W7：警戒值有数据
			statusOrgListMap.put(transCenterOrgEntity.getOrgCode(), "0");
			orgNameListMap.put(transCenterOrgEntity.getOrgCode(), transCenterOrgEntity.getOrgName());
			//初始化 null Map<String,String>:日仓库饱和度 、日期、外场名称
			contentOrgListMap.put(transCenterOrgEntity.getOrgCode(), null);
			//外场对应负责人的电话号码(无手机号或手机号不足11位的赋值 null)
			//11位的手机号
			String moblePhoto = transCenterOrgEntity.getMobilePhone();
			if(StringUtils.isNotBlank(moblePhoto) && moblePhoto.trim().length()>=PlatformConstants.SONAR_NUMBER_11){
				moblePhoto = moblePhoto.substring(0,PlatformConstants.SONAR_NUMBER_11);
			}else{
				moblePhoto = null;
			}
			photoOrgListMap.put(transCenterOrgEntity.getOrgCode(),moblePhoto);
		}
		
		/**
		 * 
			查询两日内的饱和度超过了危险值（）
			if(超过危险值)
			发短信：领导你好，XX外场XX日仓库饱和度达到xx%，已经超过了危险值，请做好预防措施，防止发生爆仓！
			else if(7天内有3日的饱和度超过了警戒值)
			导你好，XX外场（实际外场名称）近一周仓库饱和度已有三天超过警戒值，分别是：XX日xx%,XX日xx%,XX日xx%
			else 不做任何处理
		*/
		
		if(warnListDateStr!=null && dangerListDateStr!=null && dangerListDateStr.size()==2 && warnListDateStr.size()==2){
			//7日内的全国外场仓库饱和度
			List<StockSaturationEntity> dbDayList = queryStockSaturationDayListJobService(dangerListDateStr,warnListDateStr,orgList);
			//危险值警戒值查询
			for (StockSaturationEntity stockSaturationEntity : dbDayList) {
				String dbDate = stockSaturationEntity.getTimeStatisticsGroup();
				String tmpOrgCode = stockSaturationEntity.getOrgCode();
				String tmpOrgName = stockSaturationEntity.getOrgName();
				LOGGER.info("仓库饱和度超过危险值开始");
				LOGGER.info("仓库饱和度超过危险值"+tmpOrgCode+tmpOrgName);
				//日饱和度
				BigDecimal saturationDay = stockSaturationEntity.getSaturationDay();
				if(saturationDay==null) {
					saturationDay = new BigDecimal(0);
				}
				//危险值
				BigDecimal dangerDay = stockSaturationEntity.getDangerrange();
				//警戒值
				BigDecimal warnDay = stockSaturationEntity.getWarnrange();
				//危险日期
				if(dangerListDateStr.contains(dbDate)){
					//超出危险值
					if(dangerDay!=null){
					if(saturationDay.compareTo(dangerDay) >= 0){
						Map<String,String> contentMap = contentOrgListMap.get(tmpOrgCode);
						String statusStr = statusOrgListMap.get(tmpOrgCode);
						try{
							statusStr = statusStr.substring(1);
						}catch(Exception e){
							statusStr = "0";
						}
						int statusInt = strToIntStockSaturation(statusStr);
						if(statusInt==0){
							statusOrgListMap.put(tmpOrgCode, "D1");//危险值 有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"danger");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
						if(statusInt==1){
							statusOrgListMap.put(tmpOrgCode, "D2");//危险值 有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"danger");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
						LOGGER.info("仓库饱和度验证上海枢纽中心是否符合");
						LOGGER.info("仓库饱和度验证是否符合"+tmpOrgCode);
					}
					}else{
						//dangerDay危险值有异常
						LOGGER.debug(tmpOrgName+"("+tmpOrgCode+") 的危险值有异常");
					}
				}
				
				//超出警戒的日期
				if(warnDay!=null){
				//超出警戒值
				if(saturationDay.compareTo(warnDay) >= 0){
					Map<String,String> contentMap = contentOrgListMap.get(tmpOrgCode);
					if(contentMap==null){
						contentMap=new HashMap<String, String>();
					}
					String statusStr = statusOrgListMap.get(tmpOrgCode);
					if(StringUtils.isNotBlank(statusStr)){
					//如果没有记录危险数据   直接发送危险警告信息
					if(!statusStr.contains("D")){
						try{
							statusStr = statusStr.substring(1);
						}catch(Exception e){
							statusStr = "0";
						}
						int statusInt = strToIntStockSaturation(statusStr);
						if(statusInt ==0){
							statusOrgListMap.put(tmpOrgCode, "W1");//警戒值 有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"warn");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
						if(statusInt ==1){
							statusOrgListMap.put(tmpOrgCode, "W2");//警戒值 有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"warn");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
						if(statusInt ==2){
							statusOrgListMap.put(tmpOrgCode, "W3");//警戒值有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"warn");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
						if(statusInt==PlatformConstants.SONAR_NUMBER_3){
							statusOrgListMap.put(tmpOrgCode, "W4");//警戒值 有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"warn");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
						if(statusInt==PlatformConstants.SONAR_NUMBER_4){
							statusOrgListMap.put(tmpOrgCode, "W5");//警戒值 有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"warn");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
						if(statusInt==PlatformConstants.SONAR_NUMBER_5){
							statusOrgListMap.put(tmpOrgCode, "W6");//警戒值 有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"warn");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
						if(statusInt==PlatformConstants.SONAR_NUMBER_6){
							statusOrgListMap.put(tmpOrgCode, "W7");//警戒值 有数据查询对应的map
							/**
							* @param contentMap
							* @param saturationDay
							* @param dbDate
							* @param tmpOrgName
							* @param tmpOrgCode
							* @param statusOrgListMap
							* @param type
							*/
							contentMap = mapContentAgain(contentMap,saturationDay,dbDate,tmpOrgName,tmpOrgCode,statusOrgListMap,"warn");
							contentOrgListMap.put(tmpOrgCode, contentMap);
						}
					}//end 不包括危险值的
					}
				}

				}else{//警戒值有异常
					LOGGER.debug(tmpOrgName+"("+tmpOrgCode+") 的警戒值有异常");
				}
			}
			//发送短信  -- begin
			/**			 
				Map<String,String> photoOrgListMap = new HashMap<String, String>();
				Map<String,Map<String,String>> contentOrgListMap = new HashMap<String, Map<String,String>>();
			 */
			for(@SuppressWarnings("rawtypes") Map.Entry statusMap:statusOrgListMap.entrySet()){
				String orgCode = statusMap.getKey().toString();
				//外场对应的负责人手机号
				
		        LOGGER.info("仓库饱和度部门编码"+orgCode);
				String mobleStr = photoOrgListMap.get(orgCode);
				/**
				 	* @param contentMap  外场对应的内容摘要
					* @param orgName 外场名称
					* @param statusOrg 查询状态
					* @param type danger；warn
				 */
				String statusStr = statusMap.getValue().toString();
				//符合条件的调用对应的短信内容模板,不符合条件或异常的不发送短信
				String orgName = orgNameListMap.get(orgCode);
				LOGGER.info("仓库饱和度orgName信息");
				LOGGER.info("仓库饱和度"+orgName);
				String contentStr = smsContent(contentOrgListMap.get(orgCode),orgNameListMap.get(orgCode),statusStr);
				//发送短信(外场负责人)
				smsSendCommPre(contentStr,mobleStr,orgCode,orgName);
				
				//综合维护的需要发送短信的手机号  发送短信
				List<String> mobileStrList = titleBaseInfoService.queryPhoneInfoByOrgCode(orgCode);
				if(mobileStrList!=null&&mobileStrList.size()>0){
					for (String modStr : mobileStrList) {
						smsSendCommPre(contentStr,modStr,orgCode,orgName);
					}
				}else{//不发送短信
					
				}
				
			} 
			//发送短信 -- end
		}else{
			//不做任何处理
		}
		
	}
	
	/**
	 * @description 发送短信的私有方法 参数封装
	 * @author 14022-foss-songjie
	 * @update 2014年8月12日
	 * @throws Exception
	 * @param contentStr
	 * @param mobleStr
	 * @param orgCode
	 * @param orgName
	 */
	private void smsSendCommPre(String contentStr,String mobleStr,String orgCode,String orgName){
		if(StringUtils.isNotBlank(contentStr) && StringUtils.isNotBlank(mobleStr)){
			try {
				String smsSendTime = DateUtils.convert(new Date(), DateUtils.DATE_FORMAT);
				Map<String,String> smsMap = new HashMap<String, String>();
				smsMap.put("orgCode", orgCode);
				smsMap.put("smssendtime",smsSendTime);
				smsMap.put("mobiletelephone",mobleStr);
				StockSaturationSmsEntity smsPojo = stockSaturationSmsDao.queryStockSaturationSms(smsMap);
				if(smsPojo!=null){//已发过短信不再发送
					
				}else{
					//发送成功后记录发送外场及日期
					StockSaturationSmsEntity dbToPojo = new StockSaturationSmsEntity();
					dbToPojo.setOrgCode(orgCode);
					dbToPojo.setSmssendtime(smsSendTime);
					dbToPojo.setMobileTelephone(mobleStr);
					dbToPojo.setSmsstatus(1);
					stockSaturationSmsDao.insertStockSaturationSms(dbToPojo);
					smsSendComm(orgCode,orgName,mobleStr,contentStr);//发送短信成功
				}
				
			} catch (Exception e) {
				Log.error("仓库饱和度发送短信失败:手机号："+mobleStr+";发送内容："+contentStr,e);
			}
		}else{
			//不发送短信
		}
	}
	
	
	/**
	* @description 发送短信的私有方法
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月14日 下午5:16:11
	*/
	private void smsSendComm(String orgCode,String orgName,String mobleStr,String content) throws Exception{
		SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
		//发送部门编码
		smsSendLog.setSenddeptCode(orgCode);
		// 电话
		smsSendLog.setMobile(mobleStr);
		// 短信内容
		smsSendLog.setContent(content);
		// 发送部门
		smsSendLog.setSenddept(orgName);
		//发送人员编码
		smsSendLog.setSenderCode(orgCode);
		// 发送人
		smsSendLog.setSender(orgName);
		// 业务类型
//		smsSendLog.setMsgtype("platForm");
		smsSendLog.setMsgtype("YWLX20140509162902");
		// 短信来源
//		smsSendLog.setMsgsource("FOSS仓库饱和度");
		smsSendLog.setMsgsource("FOSS");
		// 唯一标识
		smsSendLog.setUnionId(UUIDUtils.getUUID());
		// 发送时间
		smsSendLog.setSendTime(new Date());
		// 服务类型 （1:短信、2:语音、3:短信语音）
		smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
		
		LOGGER.info("短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
		// 发送短信内容
		smsSendLogService.sendSMS(smsSendLog);
	}


	/**
	* @description 危险值查询范围是2天(预测)，警戒值查询范围是7天(最后2天为预测的日期)
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月11日 上午9:30:35
	*/
	private Map<String,List<String>> smsSendForDateList(){
		Map<String,List<String>> backList = new HashMap<String, List<String>>();
		Date maxDate = null;
		Date sysDate= DateUtils.convert(DateUtils.convert(new Date()), DateUtils.DATE_FORMAT);
		//大于12点=最大日期为当前系统日期+1Day
		if(decideTimeTwelve()){
			maxDate = DateUtils.addDayToDate(sysDate, 1);
		}else{//小与12点=最大日期为当前系统日期
			maxDate = sysDate;
		}
		List<String> dangerList = new ArrayList<String>();
		List<String> warnList = new ArrayList<String>();
		String dateStr = DateUtils.convert(maxDate, DateUtils.DATE_FORMAT);
		dangerList.add(dateStr);
		Date tempDate = DateUtils.addDayToDate(maxDate, -1);
		String dateStr2 = DateUtils.convert(tempDate, DateUtils.DATE_FORMAT);
		dangerList.add(dateStr2);
		Date tempDate2 = DateUtils.addDayToDate(maxDate, -PlatformConstants.SONAR_NUMBER_6);
		String dateStr3 = DateUtils.convert(tempDate2, DateUtils.DATE_FORMAT);
		warnList.add(dateStr3);
		Date tempDate3 = DateUtils.addDayToDate(maxDate, -2);
		String dateStr4 = DateUtils.convert(tempDate3, DateUtils.DATE_FORMAT);
		warnList.add(dateStr4);
		
		backList.put("dangerRange", dangerList);
		backList.put("warnRange", warnList);
		return backList;
				
	}
	
	
	/**
	* @description 短信定时检查jobService
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:50:38
	*/
	private List<StockSaturationEntity> queryStockSaturationDayListJobService(List<String> dangerListDateStr,
			List<String> warnListDateStr,List<String> orgCode) {
		//查询对应的数据
		Map<String,String> map = new HashMap<String, String>();
		String beginDateStr = warnListDateStr.get(0);//日期最小
		String endDateStr = dangerListDateStr.get(0);//日期最大
		Date dateA = DateUtils.convert(beginDateStr, DateUtils.DATE_FORMAT);
		Date dateB = DateUtils.convert(endDateStr, DateUtils.DATE_FORMAT);
		map.put("queryDateA", DateUtils.convert(dateA, DateUtils.DATE_FORMAT));
		map.put("queryDateB", DateUtils.convert(dateB, DateUtils.DATE_FORMAT));
		
		StockSaturationEntity pojo = new StockSaturationEntity();
		pojo.setBeginDate(beginDateStr);
		pojo.setEndDate(endDateStr);
		List<StockSaturationEntity> dbStockSaturationList = queryStockSaturationDayList(pojo);
		return dbStockSaturationList;
	}

	/**
	* @description 匹配对应的本部和事业部
	* @param orgCode
	* @param transCenterList
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午10:26:58
	*/
	private TransCenterOrgEntity queryNameOrgUp(String orgCode,List<TransCenterOrgEntity> transCenterList){
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
	* @description 获取综合提供的数据 包含日承载量  月承载量  警戒值 危险值
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月16日 下午2:54:07
	*/
	private DailyLoadVolumeDto queryDailyLoadVolume(String orgCode,Date queryDate){
		DailyLoadVolumeDto dto = new DailyLoadVolumeDto();
		DailyLoadVolumeEntity dlve =  dailyLoadVolumeService.queryDailyLoadVolumeByOrgCodeAndDate(orgCode,queryDate);
		if(dlve!=null){
			dto.setOrgCode(orgCode);
			dto.setOrgName(dlve.getOrgName());
			//日承载量
			BigDecimal tempbig = dlve.getDailyLoadVolume();
			if(tempbig!=null){
				//吨暂时转换为千克，计算饱和度时 统一转换。
				dto.setDailyLoadVolume(tempbig.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_1000)));
			}else{
				dto.setDailyLoadVolume(BigDecimal.ZERO);
			}
			
			BigDecimal dangerBig= new BigDecimal(dlve.getDangerValue());
			//dangerBig = dangerBig.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);//转换为小数
			dangerBig = dangerBig.setScale(2, BigDecimal.ROUND_HALF_UP);
			dto.setDangerValue(dangerBig);
			BigDecimal fullBig= new BigDecimal(dlve.getFullValue());
			//fullBig = fullBig.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);//转换为小数
			fullBig = fullBig.setScale(2, BigDecimal.ROUND_HALF_UP);
			dto.setFullValue(fullBig);
			//查询月承载量
			BigDecimal tempbig2 = dailyLoadVolumeService.queryMonthLoadVolumeByOrgCodeAndDate(orgCode, queryDate);
			if(tempbig2!=null){
				dto.setMonthLoadVolume(tempbig2.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_1000)));
			}else{
				dto.setMonthLoadVolume(BigDecimal.ZERO);
			}
		}
		 return dto;
	}
	
	
	/**
	* @description map参数组装
	* @param contentMap
	* @param saturationDay
	* @param dbDate
	* @param tmpOrgName
	* @param tmpOrgCode
	* @param statusOrgListMap
	* @param type
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 下午2:57:44
	*/
	private Map<String,String> mapContentAgain(Map<String,String> contentMap,BigDecimal saturationDay,String dbDate,String tmpOrgName,String tmpOrgCode,Map<String,String> statusOrgListMap,String type){
		if(contentMap==null){
			contentMap=new HashMap<String, String>();
		}
		contentMap.put(type+statusOrgListMap.get(tmpOrgCode)+"_saturation", saturationDay+"");
		contentMap.put(type+statusOrgListMap.get(tmpOrgCode)+"_time", dbDate);
		contentMap.put(type+statusOrgListMap.get(tmpOrgCode)+"_orgName", tmpOrgName);
		return contentMap;
	}
	
	
	/**
	* @description 获取短信内容
	* @param contentMap  外场对应的内容摘要
	* @param orgName 外场名称
	* @param statusOrg 查询状态
	* @param type danger；warn
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月14日 下午3:14:31
	*/
	private String smsContent(Map<String,String> contentMap,String orgName,String statusOrg){
		LOGGER.info("仓库饱和度orgName信息");
		LOGGER.info("仓库饱和度"+orgName);
		String backStr = "";
		StringBuffer dangerContent = new StringBuffer();
		StringBuffer warnContent = new StringBuffer();
		String[] strsWarn=null;
		String[] strsDanger=null;
		String type = "";
		try{
			if(statusOrg.contains("D")){
				type = "danger";
			}else if(statusOrg.contains("W")){
				type = "warn";
			}else if(StringUtils.equals(statusOrg, "0")){
				return backStr;
			}
			statusOrg = statusOrg.substring(1);
		}catch(Exception e){
			statusOrg = "0";
		}
		int statusOrgInt = strToIntStockSaturation(statusOrg);
		
		/**
		 * (a)	仓库饱和度超过危险值时：
			领导你好，xx外场（实际外场名称）xx日仓库饱和度达到xx%（若两日都超过，一并附上），已经超过了危险值，请做好预防措施，防止发生爆仓！
			(b)	仓库饱和度有三日超过警戒值时：
			领导你好，xx外场（实际外场名称）近一周仓库饱和度已经有超过三日超过警戒值，分别是：xx日xx%（七日内有多少天附多少天），请做好预防措施，防止发生爆仓！
		 */
		//从短信模板中查出对应的数据
		if(StringUtils.equals(type, "danger")){
			//查询出仓库饱和度超过危险值对应的短信模板
			String smsDangerContent = "";
			smsDangerContent = getSmsContent(orgName,StockSaturationConstants.WAREHOUSE_SATURATION_DANGER);
			if(smsDangerContent.length()>0){
				strsDanger = smsDangerContent.split("&");
			    dangerContent = new StringBuffer(strsDanger[0]);
			}
			
		}else if(StringUtils.equals(type, "warn")){
		   //查询出仓库饱和度有三日超过警戒值时对应的短信模板
			String smsWarnContent = "";
			smsWarnContent = getSmsContent(orgName,StockSaturationConstants.WAREHOUSE_SATURATION_WARN);
			if(smsWarnContent.length()>0){
				strsWarn = smsWarnContent.split("&");
			    warnContent = new StringBuffer(strsWarn[0]);
			}
		}
		for (int i = 1; i <= statusOrgInt; i++) {
			if(StringUtils.equals(type, "danger")){
				if(StringUtils.isNotBlank(contentMap.get("dangerD"+i+"_saturation"))){
					String tmpStrSaturation = contentMap.get("dangerD"+i+"_saturation");
					String tmpStrTime = contentMap.get("dangerD"+i+"_time");
					SmsParamDto smsParamDto = new SmsParamDto();
					smsParamDto.setMap(getSmsDangerParam(tmpStrSaturation,tmpStrTime));
					if(i<statusOrgInt){
						dangerContent.append(repalceParam(smsParamDto.getMap(),strsDanger[1])).append(" ");
					}else {
						dangerContent.append(repalceParam(smsParamDto.getMap(),strsDanger[1]));	
					}
					
				}
			}else if(StringUtils.equals(type, "warn")){
				if(StringUtils.isNotBlank(contentMap.get("warnW"+i+"_saturation"))){
					String tmpStrSaturation = contentMap.get("warnW"+i+"_saturation");
					String tmpStrTime = contentMap.get("warnW"+i+"_time");
					SmsParamDto smsParamDto = new SmsParamDto();
					smsParamDto.setMap(getSmsDangerParam(tmpStrSaturation,tmpStrTime));
					if(i<statusOrgInt){
						warnContent.append(repalceParam(smsParamDto.getMap(),strsWarn[1])).append(" ");
					}else{
						warnContent.append(repalceParam(smsParamDto.getMap(),strsWarn[1]));	
					}
				}
			}
			
		}
		
		//如果有超过危险值的数据  只发送危险值的短信
		if(StringUtils.equals(type, "danger")){
			backStr = dangerContent.toString()+strsDanger[2];
		}else if(StringUtils.equals(type, "warn")){
			if(statusOrgInt>=StockSaturationConstants.WARN_SEND_NUM){//仓库饱和度有三日以上(包含三日)超过警戒值时
				backStr = warnContent+strsWarn[2];
			}
		}
		LOGGER.info(backStr);
		return backStr;
	}
	
	
	/**
	* @description 获取日饱和度的日期
	* @param forecastDate
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月27日 下午3:21:17
	*/
	private Date querySaturationCurrentDay(Date forecastDate){
		try{
			long diffDayTmp = DateUtils.getTimeDiff(new Date(), forecastDate);
			int diffDay = Integer.parseInt(diffDayTmp+"");
			Date queryDate = new Date();
			if(diffDay<0){
				//queryDate = DateUtils.addDayToDate(new Date(), -1);
				queryDate = forecastDate;
			}
			return queryDate;
		}catch(Exception e){
			return forecastDate;
		}
	}
	
	
	/**
	* @description 字符串转整数容错方法
	* @param str
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 下午3:20:56
	*/
	private int strToIntStockSaturation(String str){
		int backInt = 0;
		try{
			backInt = Integer.parseInt(str);
		}catch(Exception e){
			return backInt;
		}
		return backInt;
	}
	
	/**
	 *@desc 统计当月预警天数与当月危险预警天数
	 *@param orgCode、startTime、endTime
	 *@return List<StockSaturationEntity>
	 *@author 105795
	 *@date 2015年3月12日下午3:36:36 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StockSaturationEntity> calculateWarningAndDangerMothDays(List<String> orgCodeList,String queryDate)
	{
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		//参数检查
		if(CollectionUtils.isEmpty(orgCodeList)||orgCodeList.size()<0)
		{
			throw new TfrBusinessException("部门编码为空");
		}
		if(StringUtil.isEmpty(queryDate))
		{
		    throw new TfrBusinessException("查询日期为空");	
		}
		String startTime="";
		String endTime="";
		//根据查询日期获取查询月的第一天
		try {
			Date endDate=format.parse(queryDate);//保证日期如2015-03-12
			endTime=format.format(endDate);
			startTime=endTime.substring(0,PlatformConstants.SONAR_NUMBER_7)+"-01";//得到查询月的第一天 如2015-03-01
			
		} catch (ParseException e) {
			throw new TfrBusinessException("时间转换格式异常！");
		}
		//查询后台数据
		return stockSaturationDao.calculateWarningAndDangerMothDays(orgCodeList, startTime, endTime);
	}
	
	/**
	 * 将查询出来的结果增加当月预警天数与当月危险预警天数
	 * */
	private void handResutList(List<StockSaturationEntity> sourList,List<StockSaturationEntity> destList)
	{
		for(StockSaturationEntity entity:sourList)
		{
			for(int i=0;i<destList.size();i++)
			{
				if(entity.getOrgCode().equalsIgnoreCase(destList.get(i).getOrgCode()))
				{
					entity.setWarningMothDayCount(destList.get(i).getWarningMothDayCount());
					entity.setDangerMothDayCount(destList.get(i).getDangerMothDayCount());
				}
			}
		}
		
		
	}
	/**
	 * 获取对应的短信模板内容
		* @param orgName
		* @param smsCode
		* @return String
		* @author 336785
		* @update 2016-9-19
	 */
	public String getSmsContent(String orgName,String smsCode) {
		String sms = ""; // 返回短信
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		smsParamDto.setMap(getSmsParam(orgName));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {//捕获异常
			LOGGER.error("短信内容为空", e);//记录日志
			throw new SignException(SignException.MESS_CONTENT_ISNULL, e);//短信内容为空
		}
		if (StringUtil.isBlank(sms)) {
			LOGGER.error("没有对应的短信模版");//记录日志
			throw new SignException(SignException.NO_SMS_TEMPLATES);//没有对应的短信模版
		}
		return sms;
	}
	/**
	 * 仓库饱和度预警设置组织名称到对应的Map中
		* @param orgName
		* @return
		* Map<String,String>
		* @author 336785
		* @update 2016-9-19
	 */
	private Map<String, String> getSmsParam(String orgName) {
		Map<String, String> map = new HashMap<String, String>();
		// 实际外场名称
		map.put("orgName", orgName);
		return map;
	}
	/**
	 * 仓库饱和度预警设置对应的时间和饱和度到Map中
		* @param tmpStrSaturation
		* @param tmpStrTime
		* @return
		* Map<String,String>
		* @author 336785
		* @update 2016-9-19
	 */
	private Map<String, String> getSmsDangerParam(String tmpStrSaturation,String tmpStrTime ) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tmpStrSaturation", tmpStrSaturation);
		map.put("tmpStrTime", tmpStrTime);
		return map;
	}
	 /**
     * <p>替换短信内容里面的参数</p>.
     *
     * @param map 
     * @param smsContent 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午11:21:10
     * @see
     */
    private String repalceParam(Map<String,String> map,String smsContent){
	if(StringUtil.isNotBlank(smsContent)){
	    for(Map.Entry<String, String> entry : map.entrySet()){
		    //获取参数对应的字符串
		    String paramValue = entry.getValue();
		    String paramKey = entry.getKey();
		    //把短信模板的内容根据传入的参数替换
		    smsContent = smsContent.replace("^"+paramKey+"^",paramValue != null ? paramValue : "");
	    }
	}
	
	return smsContent;
    }
	
}

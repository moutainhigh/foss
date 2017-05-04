package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITruckEfficiencyDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.server.service.ITruckEfficiencyService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TruckEfficiencyEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 装卸车 效率统计service
 * @author 200978  xiaobingcheng
 * 2015-1-19
 */
public class TruckEfficiencyService implements ITruckEfficiencyService {
	
	//日志
	private static final Logger LOG = LoggerFactory.getLogger(TruckEfficiencyService.class);
	
	private ITruckEfficiencyDao truckEfficiencyDao;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private ITfrCommonService tfrCommonService;
	//注入查询经营本部service
	private IPlatformCommonService platformCommonService;
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setTruckEfficiencyDao(ITruckEfficiencyDao truckEfficiencyDao) {
		this.truckEfficiencyDao = truckEfficiencyDao;
	}

	/**
	 * 装卸车效率job主方法
	 */
	@Override
	public void loadAndUnloadEfficiency() {
		//统计日期   eg:2015-01-19 00:00:00
		Date statisticDate = DateUtils.addDayToDate(DateUtils.getStartDatetime(new Date()), -1);
		List<TruckEfficiencyEntity> truckEfficiencyEntityList = this.truckEfficiencyDao.loadAndUnloadEfficiency(statisticDate);
		SimpleDateFormat sf  = new SimpleDateFormat("yyyyMM");
		String month = sf.format(statisticDate);
		int statisticMonth = Integer.parseInt(month);
		LOG.info("月份:"+month+";整形月份："+statisticMonth);
		//遍历统计的信息  赋值创建时间、月份、 主键和日期 并保存
		if(CollectionUtils.isNotEmpty(truckEfficiencyEntityList)){
			for (TruckEfficiencyEntity truckEfficiencyEntity : truckEfficiencyEntityList) {
				LOG.info("当前统计外场为："+truckEfficiencyEntity.getTransferCenterName());
				truckEfficiencyEntity.setId(UUIDUtils.getUUID());//设置主键
				truckEfficiencyEntity.setCreateTime(new Date());//设置创建时间
				truckEfficiencyEntity.setStatisticMonth(statisticMonth);
				truckEfficiencyEntity.setStatisticDate(statisticDate);//设置统计日期  eg:2015-01-20 00:00:00
				
				try{
					TruckEfficiencyEntity temp = this.queryOperationDeptCodeByCurrentCode(truckEfficiencyEntity.getTransferCenterCode());
					if(temp == null){
						LOG.error("查询经营本部失败！查询结果为空!查询部门编码是："+truckEfficiencyEntity.getTransferCenterCode());
						throw new TfrBusinessException("查询经营本部失败！查询结果为空!查询部门编码是："+truckEfficiencyEntity.getTransferCenterCode());
					}
					//经营本部   赋值
					truckEfficiencyEntity.setOperationDeptCode(temp.getOperationDeptCode());
					truckEfficiencyEntity.setOperationDeptName(temp.getOperationDeptName());
					//保存装卸车数据
					this.truckEfficiencyDao.saveTruckEfficiencyEntity(truckEfficiencyEntity);
					LOG.info("装卸车效率保存结束！");
				}catch (TfrBusinessException e) {
					LOG.error("任务执行失败！",e);
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity
							.setBizName(TfrJobBusinessTypeEnum.TRUCK_EFFICIENCY
									.getBizName());
					jobProcessLogEntity
							.setBizCode(TfrJobBusinessTypeEnum.TRUCK_EFFICIENCY
									.getBizCode());
					jobProcessLogEntity.setExecBizId("查询经营本部失败！查询结果为空!查询部门编码是："+truckEfficiencyEntity.getTransferCenterCode());
					jobProcessLogEntity
							.setRemark("查询经营本部失败！查询结果为空!查询部门编码是："+truckEfficiencyEntity.getTransferCenterCode());
					jobProcessLogEntity.setExceptionInfo(e.getStackTrace().toString());
					jobProcessLogEntity.setCreateTime(Calendar.getInstance()
							.getTime());
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				}
			}
		}
	}
	
	/**
	 * 查询给定部门code所属外场
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
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
	public Map<String, String> queryOperationDeptCode(String code) {
		Map<String, String> result = null;
		//查询经营本部
		TruckEfficiencyEntity temp = this.queryOperationDeptCodeByCurrentCode(code);
		if(temp !=null){
			result = new HashMap<String, String>();
			result.put("code", temp.getOperationDeptCode());
			result.put("name", temp.getOperationDeptName());
		}
		return result;
	}

	/**
	 * 按日期查询装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	public List<TruckEfficiencyEntity> queryTruckEfficiencyByDay(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount){
		if(truckEfficiencyEntity.getStatisticDate() ==null){
			throw new TfrBusinessException("查询参数有异常，查询时间为空！");
		}
		return this.truckEfficiencyDao.queryTruckEfficiencyByDay(truckEfficiencyEntity,start,totalCount);
	}
	
	public Long queryTruckEfficiencyByDayCount(TruckEfficiencyEntity truckEfficiencyEntity){
		if(truckEfficiencyEntity.getStatisticDate() ==null){
			throw new TfrBusinessException("查询参数有异常，查询时间为空！");
		}
		return this.truckEfficiencyDao.queryTruckEfficiencyByDayCount(truckEfficiencyEntity);
	}
	
	
	/**
	 * 按日期查询装卸车效率    对于有转运场的 部门查询结果显示  当月1号到当前查询日期的记录
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	public List<TruckEfficiencyEntity> queryTruckEfficiencyByDayOfTransfer(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount){
		if(truckEfficiencyEntity.getStatisticDate() ==null){
			throw new TfrBusinessException("查询参数有异常，查询时间为空！");
		}
		return this.truckEfficiencyDao.queryTruckEfficiencyByDayOfTransfer(truckEfficiencyEntity,start,totalCount);
	}
	
	public Long queryTruckEfficiencyByDayOfTransferCount(TruckEfficiencyEntity truckEfficiencyEntity){
		if(truckEfficiencyEntity.getStatisticDate() ==null){
			throw new TfrBusinessException("查询参数有异常，查询时间为空！");
		}
		return this.truckEfficiencyDao.queryTruckEfficiencyByDayOfTransferCount(truckEfficiencyEntity);
	}
	
	/**
	 * 按月份查询装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param truckEfficiencyEntity
	 * @return
	 */
	public List<TruckEfficiencyEntity> queryTruckEfficiencyByMonth(TruckEfficiencyEntity truckEfficiencyEntity,int start,int totalCount){
		if(truckEfficiencyEntity.getStatisticDate() ==null){
			throw new TfrBusinessException("查询参数有异常，查询时间为空！");
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
		String month = sf.format(truckEfficiencyEntity.getStatisticDate());
		int statisticMonth = Integer.parseInt(month);
		LOG.info("日期："+truckEfficiencyEntity.getStatisticDate()+"\t转化后的月份："+statisticMonth);
		truckEfficiencyEntity.setStatisticMonth(statisticMonth);
		return this.truckEfficiencyDao.queryTruckEfficiencyByMonth(truckEfficiencyEntity,start,totalCount);
	}
	
	public Long queryTruckEfficiencyByMonthCount(TruckEfficiencyEntity truckEfficiencyEntity){
		if(truckEfficiencyEntity.getStatisticDate() ==null){
			throw new TfrBusinessException("查询参数有异常，查询时间为空！");
		}
		return this.truckEfficiencyDao.queryTruckEfficiencyByMonthCount(truckEfficiencyEntity);
	}
	
	/**
	 * 生成导出文件名称
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param fileName
	 * @return
	 * @throws TfrBusinessException
	 */
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
	 * 按日期导出装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 * @throws TfrBusinessException
	 */
	public InputStream queryTruckEfficiencyByDayExcelStream(TruckEfficiencyEntity truckEfficiencyEntity) throws TfrBusinessException{

		InputStream excelStream = null;
		List<TruckEfficiencyEntity> truckEfficiencyList = new ArrayList<TruckEfficiencyEntity>();
		if(truckEfficiencyEntity.getTransferCenterCode() == null||"".equals(truckEfficiencyEntity.getTransferCenterCode())){
			// 获取导出信息
			truckEfficiencyList = this.queryTruckEfficiencyByDay(truckEfficiencyEntity, 0, Integer.MAX_VALUE);
		}else{
			truckEfficiencyList = this.queryTruckEfficiencyByDayOfTransfer(truckEfficiencyEntity, 0, Integer.MAX_VALUE);
		}
		// 行List
		List<List<String>> rowTruckList = new ArrayList<List<String>>();
		for (TruckEfficiencyEntity truckEfficiency : truckEfficiencyList) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//"日期",
			columnList.add(String.valueOf(DateUtils.convert(truckEfficiency.getStatisticDate(), DateUtils.DATE_FORMAT)));
			//"转运场",
			columnList.add(String.valueOf(truckEfficiency.getTransferCenterName()));
			//"装车操作货量",
			columnList.add(String.valueOf(truckEfficiency.getLoadTotalWeight()));
			//"卸车操作货量",
			columnList.add(String.valueOf(truckEfficiency.getUnloadTotalWeight().toString()));
			//"装车操作时长",
			columnList.add(String.valueOf(truckEfficiency.getLoadTotalDuration().toString()));
			//"卸车操作时长 	",
			columnList.add(String.valueOf(truckEfficiency.getUnloadTotalDuration().toString()));
			//"装车效率",
			columnList.add(String.valueOf(truckEfficiency.getLoadRatio().toString()));
			//"卸车效率",
			columnList.add(String.valueOf(truckEfficiency.getUnloadRatio().toString()));
			//"总效率",
			columnList.add(String.valueOf(truckEfficiency.getTotalRatio().toString()));
			
			rowTruckList.add(columnList);
		}
		ExportResource truckResource = new ExportResource();
		truckResource.setHeads(PlatformConstants.EXCEL_HEADER_TRUCK_EFFICIENCY_DAY);
		if(CollectionUtils.isEmpty(rowTruckList)){
			rowTruckList.add(new ArrayList<String>(0));
		}
		truckResource.setRowList(rowTruckList);
		ArrayList<ExportResource> exportResources = new ArrayList<ExportResource>();
		exportResources.add(truckResource);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(PlatformConstants.SHEET_SIZE);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream = objExcelExportor.exportBySheet(exportResources, exportSetting, new String[] {PlatformConstants.TRUCK_EFFICIENCY_DAY_SHEET_NAME});
		return excelStream;
	}
	
	
	/**
	 * 按月导出装卸车效率
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param truckEfficiencyEntity
	 * @return
	 * @throws TfrBusinessException
	 */
	public InputStream queryTruckEfficiencyByMonthExcelStream(TruckEfficiencyEntity truckEfficiencyEntity) throws TfrBusinessException{

		InputStream excelStream = null;
			// 获取导出信息
		List<TruckEfficiencyEntity>	truckEfficiencyList = this.queryTruckEfficiencyByMonth(truckEfficiencyEntity, 0, Integer.MAX_VALUE);
		// 行List
		List<List<String>> rowTruckList = new ArrayList<List<String>>();
		for (TruckEfficiencyEntity truckEfficiency : truckEfficiencyList) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			
			//"月份",
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
			columnList.add(String.valueOf(sf.format(truckEfficiency.getStatisticDate())));
			//"转运场",
			columnList.add(String.valueOf(truckEfficiency.getTransferCenterName()));
			//"装车操作货量",
			columnList.add(String.valueOf(truckEfficiency.getLoadTotalWeight()));
			//"卸车操作货量",
			columnList.add(String.valueOf(truckEfficiency.getUnloadTotalWeight().toString()));
			//"装车操作时长",
			columnList.add(String.valueOf(truckEfficiency.getLoadTotalDuration().toString()));
			//"卸车操作时长 	",
			columnList.add(String.valueOf(truckEfficiency.getUnloadTotalDuration().toString()));
			//"装车效率",
			columnList.add(String.valueOf(truckEfficiency.getLoadRatio().toString()));
			//"卸车效率",
			columnList.add(String.valueOf(truckEfficiency.getUnloadRatio().toString()));
			//"总效率",
			columnList.add(String.valueOf(truckEfficiency.getTotalRatio().toString()));
			
			rowTruckList.add(columnList);
		}
		ExportResource truckResource = new ExportResource();
		truckResource.setHeads(PlatformConstants.EXCEL_HEADER_TRUCK_EFFICIENCY_MONTH);
		if(CollectionUtils.isEmpty(rowTruckList)){
			rowTruckList.add(new ArrayList<String>(0));
		}
		truckResource.setRowList(rowTruckList);
		ArrayList<ExportResource> exportResources = new ArrayList<ExportResource>();
		exportResources.add(truckResource);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(PlatformConstants.SHEET_SIZE);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream = objExcelExportor.exportBySheet(exportResources, exportSetting, new String[] {PlatformConstants.TRUCK_EFFICIENCY_MONTH_SHEET_NAME});
		return excelStream;
	}
	
	/**
	 * 根据部门编码查询所属的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	public TruckEfficiencyEntity queryOperationDeptCodeByCurrentCode(String code){
		if(StringUtil.isEmpty(code)){
			throw new TfrBusinessException("根据部门查询所属经营本部失败！查询参数为空！");
		}
		//return this.truckEfficiencyDao.queryOperationDeptCodeByCurrentCode(code);
		TruckEfficiencyEntity truckEfficiencyEntity=new TruckEfficiencyEntity();
		Map<String,String> hpEntity= platformCommonService.findSupHq(code);
		if(hpEntity!=null){
			
			truckEfficiencyEntity.setOperationDeptCode( hpEntity.get("HQ_CODE")==null?"":hpEntity.get("HQ_CODE"));
			truckEfficiencyEntity.setOperationDeptName(hpEntity.get("HQ_NAME")==null?"":hpEntity.get("HQ_NAME"));
		}
		return truckEfficiencyEntity;
	}
	/**
	 * @param platformCommonService the platformCommonService to set
	 */
	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
	
}

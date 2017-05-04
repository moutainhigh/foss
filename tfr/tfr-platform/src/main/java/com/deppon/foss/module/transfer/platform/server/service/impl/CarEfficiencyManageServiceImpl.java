package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.dao.ICarEfficiencyManageDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ICarEfficiencyManageService;
import com.deppon.foss.module.transfer.platform.api.shared.define.CarEfficiencyConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity;
import com.deppon.foss.util.DateUtils;

public class CarEfficiencyManageServiceImpl implements ICarEfficiencyManageService {
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(CarEfficiencyManageServiceImpl.class);
	
	
	/**
	 * 车辆装卸车效率管理Dao
	* @fields carEfficiencyManageDao
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午4:39:49
	* @version V1.0
	*/
	private ICarEfficiencyManageDao carEfficiencyManageDao;
	/**
	 * 组织部门service
	* @fields orgAdministrativeInfoComplexService
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:25:30
	* @version V1.0
	*/
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	* @description 车辆装卸车效率管理Dao 注入
	* @param carEfficiencyManageDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午4:40:21
	*/
	public void setCarEfficiencyManageDao(
			ICarEfficiencyManageDao carEfficiencyManageDao) {
		this.carEfficiencyManageDao = carEfficiencyManageDao;
	}

	/** 
	 * @param orgAdministrativeInfoComplexService 要设置的 orgAdministrativeInfoComplexService 
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	/**
	* @description 查询外场信息，查询不到则视为统计部门
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:23:44
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



	/**
	* @description 长途装卸车效率查询
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:11:20
	*/
	@Override
	public List<CarEfficiencyEntity> queryCarEfficiencyWayLong (
			CarEfficiencyEntity carEfficiency, int start, int limit) {
		if(carEfficiency==null){
			LOGGER.error("################ queryCarEfficiencyWayLong:参数异常 ##########");
			return null;
		}
		if(carEfficiency.getOrgCode()==null){
			LOGGER.error("################ queryCarEfficiencyWayLong:外场code不能为空 ##########");
			return null;
		}
		if(carEfficiency.getActualDepartTime()==null){
			LOGGER.error("################ queryCarEfficiencyWayLong:车辆出发时间不能为空  ##########");
			return null;
		}
		if(carEfficiency.getTypeLoad()==null){
			carEfficiency.setTypeLoad("0");
		}
		return carEfficiencyManageDao.queryCarEfficiencyWayLong(carEfficiency,start,limit);
	}
	

	/**
	* @description 长途装卸车效率查询 总记录数
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:48:29
	*/
	@Override
	public long queryCarEfficiencyWayLongCount(CarEfficiencyEntity carEfficiency) {
		if(carEfficiency==null){
			LOGGER.error("################ queryCarEfficiencyWayLongCount:参数异常 ##########");
			return 0;
		}
		if(carEfficiency.getOrgCode()==null){
			LOGGER.error("################ queryCarEfficiencyWayLongCount:外场code不能为空 ##########");
			return 0;
		}
		if(carEfficiency.getActualDepartTime()==null){
			LOGGER.error("################ queryCarEfficiencyWayLongCount:车辆出发时间不能为空  ##########");
			return 0;
		}
		if(carEfficiency.getTypeLoad()==null){
			carEfficiency.setTypeLoad("0");
		}
		return carEfficiencyManageDao.queryCarEfficiencyWayLongCount(carEfficiency);
	}
	
	/**
	* @description 长途装卸车效率 导出
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:59:33
	*/
	@Override
	public InputStream carEfficiencyLongWayExport(
			CarEfficiencyEntity carEfficiency) {
		InputStream excelStream = null;
		try {
			List<CarEfficiencyEntity> queryCarEfficiencyWayLongList;
			queryCarEfficiencyWayLongList = this.queryCarEfficiencyWayLong(carEfficiency, -1, -1);

			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (queryCarEfficiencyWayLongList != null) {
				for (CarEfficiencyEntity dto : queryCarEfficiencyWayLongList) {
					// 每行的列List
					//"日期","车次号","车牌号","外场","操作类型","重量(T)","任务建立时间","任务完成时间","时长(H)","效率","是否合格
					List<String> columnList = new ArrayList<String>();
					columnList.add(dto.getActualDepartTime());
					columnList.add(dto.getBillNo());
					columnList.add(dto.getVehicleNo());
					columnList.add(dto.getOrgName());
					if(StringUtils.isNotBlank(dto.getTypeLoad())){
						if(StringUtils.equals(CarEfficiencyConstants.CAREFFICIENCY_TYPE_LOAD, dto.getTypeLoad())){
							columnList.add("装车");
						}
						if(StringUtils.equals(CarEfficiencyConstants.CAREFFICIENCY_TYPE_UNLOAD, dto.getTypeLoad())){
							columnList.add("卸车");
						}
					}else{
						columnList.add("");
					}
					columnList.add(dto.getWeightTotal()+"");
					columnList.add(DateUtils.convert(dto.getStartTime(), DateUtils.DATE_TIME_FORMAT));
					columnList.add(DateUtils.convert(dto.getEndTime(), DateUtils.DATE_TIME_FORMAT));
					columnList.add(dto.getTimeSection()+"");
					columnList.add(dto.getEfficiency()+"");
					if(StringUtils.isNotBlank(dto.getFlagPassing())){
						if(StringUtils.equals(CarEfficiencyConstants.CAREFFICIENCY_FLAG_YES, dto.getFlagPassing())){
							columnList.add("合格");
						}
						if(StringUtils.equals(CarEfficiencyConstants.CAREFFICIENCY_FLAG_NO, dto.getFlagPassing())){
							columnList.add("不合格");
						}
					}else{
						columnList.add("");
					}
					rowList.add(columnList);
				}
			}
						
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(CarEfficiencyConstants.CAREFFICIENCY_LONG_WAY_ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, CarEfficiencyConstants.CAREFFICIENCY_LONG_WAY_SHEET_NAME,
							CarEfficiencyConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出长途装卸车效率异常", e);
			throw new BusinessException("导出长途装卸车效率异常", e);
		}
		return excelStream;
	}


	/**
	* @description 短装卸车效率查询
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:11:14
	*/
	@Override
	public List<CarEfficiencyEntity> queryCarEfficiencyWayShort(
			CarEfficiencyEntity carEfficiency, int start, int limit) {
		if(carEfficiency==null){
			LOGGER.error("################ queryCarEfficiencyWayShort:参数异常 ##########");
			return null;
		}
		if(carEfficiency.getOrgCode()==null){
			LOGGER.error("################ queryCarEfficiencyWayShort:外场code不能为空 ##########");
			return null;
		}
		if(carEfficiency.getActualDepartTime()==null){
			LOGGER.error("################ queryCarEfficiencyWayShort:车辆出发时间不能为空  ##########");
			return null;
		}
		if(carEfficiency.getTypeLoad()==null){
			carEfficiency.setTypeLoad("0");
		}
		return carEfficiencyManageDao.queryCarEfficiencyWayShort(carEfficiency,start,limit);
	}

	/**
	* @description 短装卸车效率查询 总记录数
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:48:43
	*/
	@Override
	public long queryCarEfficiencyWayShortCount(
			CarEfficiencyEntity carEfficiency) {
		if(carEfficiency==null){
			LOGGER.error("################ queryCarEfficiencyWayShortCount:参数异常 ##########");
			return 0;
		}
		if(carEfficiency.getOrgCode()==null){
			LOGGER.error("################ queryCarEfficiencyWayShortCount:外场code不能为空 ##########");
			return 0;
		}
		if(carEfficiency.getActualDepartTime()==null){
			LOGGER.error("################ queryCarEfficiencyWayShortCount:车辆出发时间不能为空  ##########");
			return 0;
		}
		if(carEfficiency.getTypeLoad()==null){
			carEfficiency.setTypeLoad("0");
		}
		return carEfficiencyManageDao.queryCarEfficiencyWayShortCount(carEfficiency);
	}

	/**
	* @description 短途装卸车效率 导出
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:59:35
	*/
	@Override
	public InputStream carEfficiencyShortWayExport(
			CarEfficiencyEntity carEfficiency) {
		InputStream excelStream = null;
		try {
			List<CarEfficiencyEntity> queryCarEfficiencyWayShortList;
			queryCarEfficiencyWayShortList = this.queryCarEfficiencyWayShort(carEfficiency, -1, -1);

			// 行List
			List<List<String>> rowList = new ArrayList<List<String>>();
			if (queryCarEfficiencyWayShortList != null) {
				for (CarEfficiencyEntity dto : queryCarEfficiencyWayShortList) {
					// 每行的列List
					//"日期","车牌号","外场","操作类型","重量(T)","任务建立时间","任务完成时间","时长(H)","效率","是否合格
					List<String> columnList = new ArrayList<String>();
					columnList.add(dto.getActualDepartTime());
					columnList.add(dto.getVehicleNo());
					columnList.add(dto.getOrgName());
					if(StringUtils.isNotBlank(dto.getTypeLoad())){
						if(StringUtils.equals(CarEfficiencyConstants.CAREFFICIENCY_TYPE_LOAD, dto.getTypeLoad())){
							columnList.add("装车");
						}
						if(StringUtils.equals(CarEfficiencyConstants.CAREFFICIENCY_TYPE_UNLOAD, dto.getTypeLoad())){
							columnList.add("卸车");
						}
					}else{
						columnList.add("");
					}
					columnList.add(dto.getWeightTotal()+"");
					columnList.add(DateUtils.convert(dto.getStartTime(), DateUtils.DATE_TIME_FORMAT));
					columnList.add(DateUtils.convert(dto.getEndTime(), DateUtils.DATE_TIME_FORMAT));
					columnList.add(dto.getTimeSection()+"");
					columnList.add(dto.getEfficiency()+"");
					if(StringUtils.isNotBlank(dto.getFlagPassing())){
						if(StringUtils.equals(CarEfficiencyConstants.CAREFFICIENCY_FLAG_YES, dto.getFlagPassing())){
							columnList.add("合格");
						}
						if(StringUtils.equals(CarEfficiencyConstants.CAREFFICIENCY_FLAG_NO, dto.getFlagPassing())){
							columnList.add("不合格");
						}
					}else{
						columnList.add("");
					}
					rowList.add(columnList);
				}
			}
						
			SheetData sheetData = new SheetData();
			sheetData.setRowHeads(CarEfficiencyConstants.CAREFFICIENCY_SHORT_WAY_ROW_HEADS);
			sheetData.setRowList(rowList);

			ExcelExport excelExportUtil = new ExcelExport();
			excelStream = excelExportUtil.inputToClient(excelExportUtil
					.exportExcel(sheetData, CarEfficiencyConstants.CAREFFICIENCY_SHOET_WAY_SHEET_NAME,
							CarEfficiencyConstants.SHEET_SIZE));
		} catch (BusinessException e) {
			LOGGER.error("导出长途装卸车效率异常", e);
			throw new BusinessException("导出长途装卸车效率异常", e);
		}
		return excelStream;
	}
	/**
	* @description 相关的理货员
	* @param list
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午2:44:24
	*/
	@Override
	public List<CarEfficiencyEntity> queryLoaderListByTaskId(String taskIds) {
		if(StringUtils.isNotBlank(taskIds)){
			String[] taskIdArray = taskIds.split(",");
			if(taskIdArray!=null&&taskIdArray.length>0){
				List<String> taskIdList = Arrays.asList(taskIdArray);
				return carEfficiencyManageDao.queryLoaderListByTaskId(taskIdList);
			}else{
				LOGGER.error("################ queryLoaderListByTaskId:taskid获取失败  ##########");
				return null;
			}
		}else{
			LOGGER.error("################ queryLoaderListByTaskId:参数异常 ##########");
			return null;
		}
	}
	
	
	
}

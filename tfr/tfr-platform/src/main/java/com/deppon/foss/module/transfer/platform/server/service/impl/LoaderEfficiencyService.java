package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.ILoaderEfficiencyDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ILoaderEfficiencyService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.LoaderEfficiencyEntity;
import com.deppon.foss.util.DateUtils;

/**
 * 个人装卸车效率管理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2014-4-29 上午8:58:30 </p>
 * @author 163580
 * @date 2014-4-29 上午8:58:30
 * @since
 * @version
 */
public class LoaderEfficiencyService implements ILoaderEfficiencyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoaderEfficiencyService.class);
	/**个人装卸车效率管理Dao**/
	private ILoaderEfficiencyDao loaderEfficiencyDao;
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	
	
	
	/**
	 * <p>日均装卸车效率查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:16
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	@Override
	public List<LoaderEfficiencyEntity> queryLoaderEfficiencyByDay(
			LoaderEfficiencyEntity loaderEfficiency, int limit, int start) {
		LOGGER.info("queryLoaderEfficiencyByDay");
		
		return loaderEfficiencyDao.queryLoaderEfficiencyByDay(loaderEfficiency, limit, start);
	}

	/**
	 * <p>日均装卸车效率总记录数查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:46
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	@Override
	public Long queryLoaderEfficiencyByDayCount(
			LoaderEfficiencyEntity loaderEfficiency) {
		LOGGER.info("queryLoaderEfficiencyByDayCount");
		return loaderEfficiencyDao.queryLoaderEfficiencyByDayCount(loaderEfficiency);
	}

	/**
	 * <p>月均装卸车效率查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:16
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	@Override
	public List<LoaderEfficiencyEntity> queryLoaderEfficiencyByMonth(
			LoaderEfficiencyEntity loaderEfficiency, int limit, int start) {
		LOGGER.info("queryLoaderEfficiencyByMonth");
		return loaderEfficiencyDao.queryLoaderEfficiencyByMonth(loaderEfficiency, limit, start);
	}

	/**
	 * <p>月均装卸车效率总记录数查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:46
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	@Override
	public Long queryLoaderEfficiencyByMonthCount(
			LoaderEfficiencyEntity loaderEfficiency) {
		LOGGER.info("queryLoaderEfficiencyByMonthCount");
		return loaderEfficiencyDao.queryLoaderEfficiencyByMonthCount(loaderEfficiency);
	}
	
	/**
	 * <p>日均装车效率导出</p> 
	 * @author 163580
	 * @date 2014-4-30 上午10:45:20
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	@Override
	public InputStream exportLoaderEfficiencyByDayExcel(
			LoaderEfficiencyEntity loaderEfficiency) {
		InputStream excelStream = null;
		List<LoaderEfficiencyEntity> loaderEfficiencys = queryLoaderEfficiencyByDay(loaderEfficiency, Integer.MAX_VALUE, 0);
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(LoaderEfficiencyEntity le : loaderEfficiencys){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//日期
			columnList.add(DateUtils.convert(le.getQueryDate(), "yyyy-MM-dd"));
			//理货员
			columnList.add(le.getLoaderName());
			//理货员工号
			columnList.add(le.getLoaderCode());
			//部门名称
			columnList.add(le.getOrgName());
			//队组别
			columnList.add(le.getLoaderOrgName());
			//操作类型
			columnList.add(getHandleTypeMap().get(le.getHandleType()));
			//重量
			if(le.getWeight() != null) {
				columnList.add(le.getWeight().toString());
			} else {
				columnList.add(null);
			}
			//操作时长
			if(le.getDuration() != null) {
				columnList.add(le.getDuration().toString());
			} else {
				columnList.add(null);
			}
			//日均效率
			if(le.getEfficiencyOfDay() != null) {
				columnList.add(le.getEfficiencyOfDay().toString());
			} else {
				columnList.add(null);
			}
			rowList.add(columnList);
		}
		String[] rowHeads = {"日期","理货员","工号","部门名称","队组别","操作类型","重量(吨)","操作时长(小时)","日均效率"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("个人日均装车效率");
		exportSetting.setSize(PlatformConstants.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		return excelStream;
	}

	/**
	 * <p>月均装车效率导出</p> 
	 * @author 163580
	 * @date 2014-4-30 上午10:46:22
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	@Override
	public InputStream exportLoaderEfficiencyByMonthExcel(
			LoaderEfficiencyEntity loaderEfficiency) {
		InputStream excelStream = null;
		List<LoaderEfficiencyEntity> loaderEfficiencys = queryLoaderEfficiencyByMonth(loaderEfficiency, Integer.MAX_VALUE, 0);
		//行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(LoaderEfficiencyEntity le : loaderEfficiencys){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//月份
			columnList.add(le.getQueryMonth());
			//理货员
			columnList.add(le.getLoaderName());
			//理货员工号
			columnList.add(le.getLoaderCode());
			//部门名称
			columnList.add(le.getOrgName());
			//队组别
			columnList.add(le.getLoaderOrgName());
			//操作类型
			columnList.add(getHandleTypeMap().get(le.getHandleType()));
			//重量
			if(le.getWeight() != null) {
				columnList.add(le.getWeight().toString());
			} else {
				columnList.add(null);
			}
			//操作时长
			if(le.getDuration() != null) {
				columnList.add(le.getDuration().toString());
			} else {
				columnList.add(null);
			}
			//月均效率
			if(le.getEfficiencyOfMonth() != null) {
				columnList.add(le.getEfficiencyOfMonth().toString());
			} else {
				columnList.add(null);
			}
			rowList.add(columnList);
		}
		String[] rowHeads = {"月份","理货员","工号","部门名称","队组别","操作类型","重量(吨)","操作时长(小时)","月均效率"};//定义表头
		
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("个人月均装车效率");
		exportSetting.setSize(PlatformConstants.SONAR_NUMBER_5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
		return excelStream;
	}

	/**
	 * 
	 * <p>根据部门code查询出当前部门下(包括当前部门)所有的子部门</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:46
	 * @param orgCode
	 * @return
	 * @see
	 */
	@Override
	public List<String> getChildDept(String orgCode) {
		LOGGER.info("getChildDept");
		if(StringUtils.isEmpty(orgCode)) {
			return null;
		}
		//根据部门编码获取所属及下属部门信息 此部门及下属的所有部门。
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfos = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoEntityAllSubByCode(orgCode);
		List<String> orgCodes = new ArrayList<String>(orgAdministrativeInfos.size());
		for(OrgAdministrativeInfoEntity orgAdministrativeInfo : orgAdministrativeInfos) {
			orgCodes.add(orgAdministrativeInfo.getCode());
		}
		//返回部门code
		return orgCodes;
	}
	
	/**
	 * <p>根据当前部门code获取上级外场 </p> 
	 * @author 163580
	 * @date 2014-4-30 下午3:42:46
	 * @param currentDeptCode
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.ILoaderEfficiencyService#querySuperiorOrgByOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(
			String currentDeptCode) {
		//设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		//外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//查询上级部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
				queryOrgAdministrativeInfoIncludeSelfByCode(currentDeptCode, bizTypesList);
		if(orgAdministrativeInfoEntity != null){
			//返回部门
			return orgAdministrativeInfoEntity;
		}else{
			//获取上级部门失败
			LOGGER.error("################查询组织（code：" + currentDeptCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
			throw new TfrBusinessException("获取上级部门失败, 无上级部门");
		}
	}
	
	/**
	 * 操作类型map
	 */
	private Map<String, String> m_handleTypeMap;
	/**
	 * 操作类型map
	 * @Title: getHandleTypeMap 
	 * @Description: 操作类型map 
	 * @return Map<String,String>    返回类型 
	 * getHandleTypeMap
	 * @author: 163580
	 * @throws 
	 * Date: 2014年4月30日 10:59:02
	 */
	private Map<String, String> getHandleTypeMap() {
		if(m_handleTypeMap == null) {
			m_handleTypeMap = new HashMap<String, String>(2) {
				private static final long serialVersionUID = 5894620534260613736L;

				@Override
				public String get(Object key) {
					if(super.get(key) != null) {
						return super.get(key);
					}
					return (String)key;
				}
			};
			m_handleTypeMap.put("LOAD", "装车");
			m_handleTypeMap.put("UNLOAD", "卸车");
		}
		return m_handleTypeMap;
	}

//	/**
//	 * @Title: querySuperiorOrgByOrgCode 
//	 * @Description: 根据部门code找顶级组织 
//	 * @param orgCode
//	 * @return    
//	 * @return OrgAdministrativeInfoEntity    返回类型 
//	 * querySuperiorOrgByOrgCode
//	 * @author: 163580
//	 * @throws 
//	 * Date:2014-4-29 上午10:40:46
//	 */
//	@Override
//	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode) {
//		
//		//设置查询参数
//		List<String> bizTypesList = new ArrayList<String>();
//		//外场类型
//		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
//		//查询上级部门
//		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
//				queryOrgAdministrativeInfoIncludeSelfByCode(orgCode, bizTypesList);
//		if(orgAdministrativeInfoEntity != null){
//			//返回部门
//			return orgAdministrativeInfoEntity;
//		}else{
//			//获取上级部门失败
//			LOGGER.error("################查询组织（code：" + orgCode + "）所属的上级部门失败(包括营业部、派送部、外场、总调)！##########");
//			throw new TfrBusinessException("获取上级部门失败, 无上级部门");
//		}
//	}
	
	public void setLoaderEfficiencyDao(ILoaderEfficiencyDao loaderEfficiencyDao) {
		this.loaderEfficiencyDao = loaderEfficiencyDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}
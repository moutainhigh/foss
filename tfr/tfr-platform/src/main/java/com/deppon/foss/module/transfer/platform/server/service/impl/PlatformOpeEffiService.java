package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IPlatformOpeEffiDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformOpeEffiService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PlatformOpeEffiEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PlatformOpeEffiCondiDto;
/**
 * 
* @ClassName: PlatformOpeEffiService 
* @Description: 月台操作效率service
* @author 105944
* @date 2015-3-21 下午2:48:08
 */
public class PlatformOpeEffiService implements IPlatformOpeEffiService{
	//月台操作效率dao
	private IPlatformOpeEffiDao platformOpeEffiDao;
	//查询经营本部信息的service
	private IPlatformCommonService platformCommonService;
	//综合组织
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
	public void setPlatformOpeEffiDao(IPlatformOpeEffiDao platformOpeEffiDao) {
		this.platformOpeEffiDao = platformOpeEffiDao;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 
	* @Title: statisticPlatformOpeEffi 
	* @Description: 统计月台操作效率
	* @author 105944
	* @date 2015-3-21 上午10:37:28  
	* @param      
	* @return void    
	* @throws
	 */
	@Transactional
	@Override
	public void statisticPlatformOpeEffi() {
		// 查询需要统计月台操作效率的外场信息
		List<PlatformOpeEffiEntity> platformOpeEffiList = platformOpeEffiDao.queryOutfieldInfoList();
		//获取当前时间
		Date dNow = new Date();
		//获取日历
		Calendar calendar = Calendar.getInstance();
		//将当前时间赋给日历
		calendar.setTime(dNow);
		//设置日历时间为前一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		//设置统计时间为当前时间的前一天
		Date statisticDate = calendar.getTime();
		for(int i = 0; i < platformOpeEffiList.size(); i++){
			//获取需要统计月台操作效率的外场信息
			PlatformOpeEffiEntity platformOpeEffi = platformOpeEffiList.get(i);			
			//根据外场id查询经营本部信息
			if(null != platformOpeEffi.getOutfieldCode()){
				Map<String,String> businessDeptInfo = platformCommonService.findSupHq(platformOpeEffi.getOutfieldCode());
				if(null != businessDeptInfo){
					platformOpeEffi.setBusinessDeptCode(businessDeptInfo.get("HQ_CODE"));
					platformOpeEffi.setBusinessDept(businessDeptInfo.get("HQ_NAME"));
				}
			}
			//设置统计时间
			platformOpeEffi.setStatisticDate(statisticDate);
			//插入月台操作效率明细日数据
			boolean isInsertLine = platformOpeEffiDao.insertPlatformOpeEffiDayDetailData(platformOpeEffi);
			//如果统计当日产生了日明细数据，则进行相应更新和插入
			if(isInsertLine){
				//更新月台操作效率明细月数据
				platformOpeEffiDao.updatePlatformOpeEffiMonthDetailData(platformOpeEffi);
				// 插入月台操作效率数据
				platformOpeEffiDao.insertPlatformOpeEffiData(platformOpeEffi);
			}
		}
	}

	/**
	 * 
	* @Title: queryPlatformOpeEffi 
	* @Description: 查询月台操作效率
	* @author 105944
	* @date 2015-3-21 上午10:42:37  
	* @param platformOpeEffi
	* @param start
	* @param totalCount
	* @param @return    
	* @return List<PlatformOpeEffiEntity>    
	* @throws
	 */
	@Override
	public Map<String, Object> queryPlatformOpeEffi(
			PlatformOpeEffiCondiDto queryCondition, int start, int limit) {
		
		List<PlatformOpeEffiEntity> platformOpeEffiList = platformOpeEffiDao.queryPlatformOpeEffi(queryCondition, start, limit);
		int totalCount = platformOpeEffiDao.queryPlatformOpeEffiCount(queryCondition);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("platformOpeEffiList", platformOpeEffiList);
		result.put("totalCount", totalCount);
		return result;
	}

	/**
	 * 
	* @Title: queryPlatformOpeEffi 
	* @Description: 查询月台操作效率明细
	* @author 105944
	* @date 2015-3-21 上午10:42:37  
	* @param platformOpeEffi
	* @param start
	* @param totalCount
	* @param @return    
	* @return List<PlatformOpeEffiEntity>    
	* @throws
	 */
	@Override
	public Map<String, Object> queryPlatformOpeEffiDetail(
			PlatformOpeEffiCondiDto queryCondition, int start, int limit) {
		if(queryCondition == null){
			throw new TfrBusinessException("查询参数有异常，查询条件为空！");
		}
		if(queryCondition.getStatisticDate() ==null){
			throw new TfrBusinessException("查询参数有异常，查询时间为空！");
		}
		List<PlatformOpeEffiEntity> platformOpeEffiList = platformOpeEffiDao.queryPlatformOpeEffiDetail(queryCondition, start, limit);
		int totalCount = platformOpeEffiDao.queryPlatformOpeEffiDetailCount(queryCondition);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("platformOpeEffiList", platformOpeEffiList);
		result.put("totalCount", totalCount);
		return result;
	}

	/**
	 * 
	* @Title: exportPlatformOpeEffiData 
	* @Description: 按查询条件导出月台操作信息
	* @author 105944
	* @date 2015-3-24 下午3:02:48  
	* @param @param queryCondition
	* @param @return    
	* @return ExportResource    
	* @throws
	 */
	@Override
	public ExportResource exportPlatformOpeEffiData(
			PlatformOpeEffiCondiDto queryCondition) {
		if(queryCondition == null){
			throw new TfrBusinessException("查询参数有异常，查询条件为空！");
		}
		if(queryCondition.getStatisticDate() ==null){
			throw new TfrBusinessException("查询参数有异常，查询时间为空！");
		}
		//查询月台操作信息数据
		List<PlatformOpeEffiEntity> platformOpeEffiList = platformOpeEffiDao.queryPlatformOpeEffi4Whole(queryCondition);
		//如果未查询到相关信息，则给予提示
		if(null == platformOpeEffiList || platformOpeEffiList.size() == 0){
			throw new TfrBusinessException("查询结果为空！");
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> result = null;

		for (PlatformOpeEffiEntity platformOpeEffi : platformOpeEffiList) {
			result = new ArrayList<String>();
			// 日期
			result.add(String.format("%1$tF", platformOpeEffi.getStatisticDate()));
			// 月份
			result.add(String.format("%1$s", platformOpeEffi.getStatisticMonth()));
			// 经营本部
			result.add(platformOpeEffi.getBusinessDept());
			// 外场
			result.add(platformOpeEffi.getOutfield());
			//当日装车吞吐量
			result.add(String.format("%1$,.2f", platformOpeEffi.getLoadAmountByDay()));
			//当日卸车吞吐量
			result.add(String.format("%1$,.2f", platformOpeEffi.getDownloadAmountByDay()));						
			//当日装车有效操作时长
			result.add(String.format("%1$,.2f", platformOpeEffi.getLoadTimeByDay()));			
			//当日卸车有效操作时长
			result.add(String.format("%1$,.2f", platformOpeEffi.getDownloadTimeByDay()));
			//当日月台操作效率
			result.add(String.format("%1$,.2f", platformOpeEffi.getPlatformOpeEffiByDay()));
			//当月装车吞吐量
			result.add(String.format("%1$,.2f", platformOpeEffi.getLoadAmountByMonth()));
			//当月卸车吞吐量
			result.add(String.format("%1$,.2f", platformOpeEffi.getDownloadAmountByMonth()));						
			//当月装车有效操作时长
			result.add(String.format("%1$,.2f", platformOpeEffi.getLoadTimeByMonth()));			
			//当月卸车有效操作时长
			result.add(String.format("%1$,.2f", platformOpeEffi.getDownloadTimeByMonth()));
			//当月月台操作效率
			result.add(String.format("%1$,.2f", platformOpeEffi.getPlatformOpeEffiByMonth()));
			rowList.add(result);
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(PlatformConstants.EXCEL_HEADER_EXPORTPLATFORMOPEEFFIDATA);
		sheet.setRowList(rowList);
		return sheet;
	}

	/**
	 * 
	* @Title: queryOutfieldInfoByDeptCode 
	* @Description: 根据当前部门信息查询该部门所属外场
	* @author 105944
	* @date 2015-3-24 下午4:15:26  
	* @param @param currentDeptCode
	* @param @return    
	* @return OrgAdministrativeInfoEntity    
	* @throws
	 */
	@Override
	public OrgAdministrativeInfoEntity queryOutfieldInfoByDeptCode(
			String currentDeptCode) {
		if (StringUtils.isEmpty(currentDeptCode)) {
			return null;
		}

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		// 调用综合接口，查询部门所属外场
		OrgAdministrativeInfoEntity outfield = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(currentDeptCode, bizTypesList);

		return outfield;
	}
}

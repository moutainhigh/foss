package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.LoaderEfficiencyEntity;

/**
 * 个人装卸车效率管理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2014-4-29 上午8:56:50,content</p>
 * @author 163580
 * @date 2014-4-29 上午8:56:50
 * @since
 * @version
 */
public interface ILoaderEfficiencyService {

	/**
	 * <p>日均装卸车效率查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:16
	 * @param loaderEfficiency
	 * @param j 
	 * @param i 
	 * @return
	 * @see
	 */
	List<LoaderEfficiencyEntity> queryLoaderEfficiencyByDay(
			LoaderEfficiencyEntity loaderEfficiency, int limit, int start);

	/**
	 * <p>日均装卸车效率总记录数查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:46
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	Long queryLoaderEfficiencyByDayCount(LoaderEfficiencyEntity loaderEfficiency);

	/**
	 * <p>月均装卸车效率查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:16
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	List<LoaderEfficiencyEntity> queryLoaderEfficiencyByMonth(
			LoaderEfficiencyEntity loaderEfficiency, int limit, int start);

	/**
	 * <p>月均装卸车效率总记录数查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:46
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	Long queryLoaderEfficiencyByMonthCount(
			LoaderEfficiencyEntity loaderEfficiency);

	/**
	 * 查询当前部门下所有子部门
	 * @author 163580
	 * @date 2014-4-29 下午3:23:11
	 * @param orgCode
	 * @return
	 * @see
	 */
	List<String> getChildDept(String orgCode);

	/**
	 * <p>日均装车效率导出</p> 
	 * @author 163580
	 * @date 2014-4-30 上午10:45:20
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	InputStream exportLoaderEfficiencyByDayExcel(
			LoaderEfficiencyEntity loaderEfficiency);

	/**
	 * <p>月均装车效率导出</p> 
	 * @author 163580
	 * @date 2014-4-30 上午10:46:22
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	InputStream exportLoaderEfficiencyByMonthExcel(
			LoaderEfficiencyEntity loaderEfficiency);

	/**
	 * <p>TODO根据当前部门code获取上级外场 </p> 
	 * @author 163580
	 * @date 2014-4-30 下午3:41:52
	 * @param currentDeptCode
	 * @return
	 * @see
	 */
	OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String currentDeptCode);
	
}

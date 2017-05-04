package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.LoaderEfficiencyEntity;


/**
 * 个人装卸车效率管理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2014-4-29 上午8:57:39 </p>
 * @author 163580
 * @date 2014-4-29 上午8:57:39
 * @since
 * @version
 */
public interface ILoaderEfficiencyDao {

	/**
	 * <p>日均装卸车效率查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:16
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	List<LoaderEfficiencyEntity> queryLoaderEfficiencyByDay(
			LoaderEfficiencyEntity loaderEfficiency, int limit, int start);

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
	 * <p>日均装卸车效率总记录数查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:46
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	Long queryLoaderEfficiencyByDayCount(LoaderEfficiencyEntity loaderEfficiency);

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
	
}
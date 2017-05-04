package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ILoaderEfficiencyDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.LoaderEfficiencyEntity;

/**
 * 个人装卸车效率管理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2014-4-29 上午8:59:00 </p>
 * @author 163580
 * @date 2014-4-29 上午8:59:00
 * @since
 * @version
 */
public class LoaderEfficiencyDao extends iBatis3DaoImpl implements ILoaderEfficiencyDao {
	private static final String NAMESPACE = "foss.platform.loaderefficiency.";

	/**
	 * <p>日均装卸车效率查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:16
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderEfficiencyEntity> queryLoaderEfficiencyByDay(
			LoaderEfficiencyEntity loaderEfficiency, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryLoaderEfficiencyByDay", loaderEfficiency, rowBounds);
	}

	/**
	 * <p>月均装卸车效率查询</p> 
	 * @author 163580
	 * @date 2014-4-29 上午10:40:16
	 * @param loaderEfficiency
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderEfficiencyEntity> queryLoaderEfficiencyByMonth(
			LoaderEfficiencyEntity loaderEfficiency, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryLoaderEfficiencyByMonth", loaderEfficiency, rowBounds);
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
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryLoaderEfficiencyByDayCount", loaderEfficiency);
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
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryLoaderEfficiencyByMonthCount", loaderEfficiency);
	}

}

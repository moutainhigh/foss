package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.FossCashDataDto;


/**
 * FOSS现金缴款同步接口规格文档
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-28 下午3:41:09,content:TODO </p>
 * @author 105762
 * @date 2014-7-28 下午3:41:09
 * @since
 * @version
 */
public interface ISyncIncomeRptCHDao {

	/**
	 * <p>查询现金缴款每日统计数据</p> 
	 * @author 105762
	 * @date 2014-7-28 下午3:41:15
	 * @param rptDate
	 * @return
	 * @see
	 */
	List<FossCashDataDto> queryIncomeRptCHByDateStr(String dateStr);

}

package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;

/**
 * 
 * 客户圈信息dao接口
 * @author 308861 
 * @date 2017-1-3 上午10:30:18
 * @since
 * @version
 */
public interface ICustomerCircleRelationDao {
	
	/**
	 * 
	 * 根据 客户编码 精准查询开单时间 有效的客户圈的信息  
	 * @author 308861 
	 * @date 2017-2-7 下午5:16:54
	 * @param custCode
	 * @param date
	 * @return
	 * @see
	 */
	CustomerCircleEntity getByCustCode(String custCode,Date date);
	/**
	 * 
	 * 根据从客户圈编码查询主客户的有效客户圈信息  
	 * @author 308861 
	 * @date 2017-2-21 下午6:01:05
	 * @param custCircleCode
	 * @param date
	 * @return
	 * @see
	 */
	CustomerCircleEntity selectMainCust(String custCircleCode,Date date);
}

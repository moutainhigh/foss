package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;

/**
 * 
 * TODO(GXG大客户合并)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:183272,date:2015年9月17日 下午5:33:44,content:TODO
 * </p>
 * 
 * @author 183272
 * @date 2015年9月17日 下午5:33:44
 * @since
 * @version
 */

public interface ICombinateBillService extends IService {
	/**
	 * 
	 * <p>
	 * GXG大客户合并
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午5:33:00
	 * @param bigCustomes
	 * @see
	 */
	public String combine(Date timeFrom, Date timeTo);
	/**
	 * 
	 * <p>
	 * GXG大客户合并
	 * </p>
	 * 
	 * @author 183272
	 * @date 2015年9月17日 下午5:33:00
	 * @param bigCustomes
	 * @see
	 */
	public String disCombine(Date timeFrom, Date timeTo);
}

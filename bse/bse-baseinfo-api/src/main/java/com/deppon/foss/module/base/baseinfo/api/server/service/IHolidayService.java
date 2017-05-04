/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity;

/**
 * 法定节假日基础资料Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:187862,date:2015-3-19 下午3:32:06,content:TODO </p>
 * @author 187862-dujunhui 
 * @date 2015-3-19 下午3:32:06
 * @since
 * @version
 */
public interface IHolidayService extends IService {
	
	/**
	 * 新增法定节假日基础资料
	 */
	int addHoliday(HolidayEntity entity);
	/**
	 * 修改法定节假日基础资料
	 */
	int updateHoliday(HolidayEntity entity);
	/**
	 * 作废法定节假日基础资料
	 */
	int deleteHoliday(String[] ids);
	/**
	 * 根据条件查询法定节假日基础资料
	 */
	List<HolidayEntity> queryHolidayListByCondition(HolidayEntity entity,int start,int limit);
	/**
	 * 根据条件查询法定节假日基础资料条数
	 */
	long queryHolidayListCountByCondition(HolidayEntity entity);
	/**
	 * 查询法定节假日日期是否冲突
	 */
	List<HolidayEntity> queryCountInterCrossDate(HolidayEntity entity);

}

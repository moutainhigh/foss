package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;

/**
 * 定义市场活动接口类Dao
 * 
 * @author 078816
 * @date   2014-03-31
 *
 */
public interface IMarkActivitiesDao {

	/**
	 * 新增一条活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int addMarkActivities(MarkActivitiesEntity entity);
	
	/**
	 * 修改一条活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int updateMarkActivities(MarkActivitiesEntity entity);
	
	/**
	 * 根据不同的查询条件查询活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	List<MarkActivitiesQueryConditionDto> queryMarkActivitiesByCondition(MarkActivitiesQueryConditionDto entity);
	
	/**
	 * 根据活动编码查询活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	MarkActivitiesEntity queryMarkActivitiesByCode(String activityCode);
	
	/**
	 * 根据活动编码和开单时间查询活动信息(不考虑活动是否有效)
	 *
	 * auther:WangQianJin
	 * date:2014-06-12
	 *
	 */
	MarkActivitiesEntity queryMarkActivitiesByCodeAndBilltime(String activityCode,Date billlingTime);
	
	
	/**
	 * 根据活动名称查询活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	MarkActivitiesEntity queryMarkActivitiesByName(String activityName);
	
	
	/**
	 * 根据crmId查询活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	MarkActivitiesEntity queryMarkActivitiesByCrmId(BigDecimal crmId);
}

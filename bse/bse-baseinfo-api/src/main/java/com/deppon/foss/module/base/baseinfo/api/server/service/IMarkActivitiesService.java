package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;

/**
 * 定义市场活动接口类
 * 
 * @author 078816
 * @date   2014-03-31
 *
 */
public interface IMarkActivitiesService extends IService {

	/**
	 * 新增一条活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int addMarkActivities(MarkActivitiesEntity entity);
	
	/**
	 * 作废一条活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int deleteMarkActivities(MarkActivitiesEntity entity);
	
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
	
	
	/* 根据活动crmId查询活动信息是否存在
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	boolean queryIsExistsMarkActivitiesByCrmId(BigDecimal crmId);
	
	/**
	 * 根据开单界面传递过来的条件查询对应的折扣信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	MarkActivitiesQueryConditionDto queryMarkActivitiesInfoByCondition(MarkActivitiesQueryConditionDto entity);
	/**
	 * 根据开单界面传递过来的条件查询对应的折扣信息
	 *
	 *该方法只适合于快递自动补录调用
	 * author：220125  cellphone：18721080868
	 * date:2015-10-13
	 *
	 */
	MarkActivitiesQueryConditionDto queryMarkActivitiesInfoByConditionExpressAuto(MarkActivitiesQueryConditionDto entity);
	/**
	 * 根据不同的查询条件查询活动信息
	 * 满足PDA快递开单调用的，不含一二级行业的
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-24
	 *
	 */
	List<MarkActivitiesQueryConditionDto> queryMarkActivitiesNoIndurstryByCondition(MarkActivitiesQueryConditionDto entity);
}

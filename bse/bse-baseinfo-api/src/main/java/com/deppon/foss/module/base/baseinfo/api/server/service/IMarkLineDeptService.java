package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkLineDeptEntity;

/**
 * 定义市场推广活动线路（出发到达部门）接口类
 *
 * @author 078816
 * @date   2014-04-01
 *
 */
public interface IMarkLineDeptService extends IService {

	/**
	 * 新增一条活动线路（出发到达部门）信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int addMarkActivitiesLineDept(List<MarkLineDeptEntity> list);
	
	/**
	 * 修改一条活动线路（出发到达部门）信息
	 *
	 * auther:shenweihua-132599
	 * date:2014-11-17
	 *
	 */
	int updateMarkActivitiesLineDept(List<MarkLineDeptEntity> list);
	
	/**
	 * 作废一条活动线路（出发到达部门）信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int deleteMarkActivitiesLineDept(MarkLineDeptEntity entity);
	
	/**
	 * 修改一条活动线路（出发到达部门）信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int updateMarkActivitiesLineDept(MarkLineDeptEntity entity);
	
	/**
	 * 根据crmId查询线路信息是否存在
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	boolean queryIsExistsMarkActivitiesLineDeptByCrmId(BigDecimal crmId);
	
	/**
	 * 查询活动的线路部门信息（目前根据活动crmId和部门编码查询）
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-17
	 *
	 */
	List<MarkLineDeptEntity> queryMarkActivityLineDept(MarkLineDeptEntity entity);
}

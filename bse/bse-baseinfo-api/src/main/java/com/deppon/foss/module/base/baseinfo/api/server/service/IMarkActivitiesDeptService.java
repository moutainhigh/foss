package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesDeptEntity;

/**
 * 定义市场推广活动开展部门接口类
 *
 * @author 078816
 * @date   2014-04-01
 *
 */
public interface IMarkActivitiesDeptService extends IService {

	/**
	 * 新增一条活动开展部门信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int addMarkActivitiesDept(List<MarkActivitiesDeptEntity> list);
	
	/**
	 * 修改一条活动开展部门信息
	 *
	 * auther:shenweihua-132599
	 * date:2014-11-17
	 *
	 */
	int updateMarkActivitiesDept(List<MarkActivitiesDeptEntity> list);
	
	/**
	 * 作废一条活动开展部门信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int deleteMarkActivitiesDept(MarkActivitiesDeptEntity entity);
	
	/**
	 * 修改一条活动开展部门信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int updateMarkActivitiesDept(MarkActivitiesDeptEntity entity);
	
	/**
	 * 根据crmId查询活动开展部门信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	boolean queryIsExistsMarkActivitiesDeptByCrmId(BigDecimal crmId);
	
	/**
	 * 查询活动开展部门信息（活动crmId和部门编码）
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-17
	 *
	 */
	List<MarkActivitiesDeptEntity> queryMarkActivitiesDept(MarkActivitiesDeptEntity entity);
	
	/**
	 * 查询活动开展部门信息（活动crmId和部门编码）(根据时间建模，适用于更改单)
	 *
	 * auther:WangQianJin
	 * date:2014-08-03
	 *
	 */
	List<MarkActivitiesDeptEntity> queryMarkActivitiesDeptByBillTime(MarkActivitiesDeptEntity entity,Date billlingTime);
			
}

package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesDeptEntity;

/**
 * 定义市场推广活动开展部门接口类Dao层
 *
 * @author 078816
 * @date   2014-04-01
 *
 */
public interface IMarkActivitiesDeptDao {

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
	MarkActivitiesDeptEntity queryMarkActivitiesDeptByCrmId(BigDecimal crmId);
	
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

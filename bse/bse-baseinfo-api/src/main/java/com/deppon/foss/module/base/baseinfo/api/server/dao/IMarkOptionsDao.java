package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkOptionsEntity;

/**
 * 定义市场推广活动多选接口类Dao层
 * 价格折扣信息
 * @author 078816
 * @date   2014-03-31
 *
 */
public interface IMarkOptionsDao {

	/**
	 * 新增一条活动多选信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int addMarkActivitiesOptions(List<MarkOptionsEntity> list);

	/**
	 * 修改一条活动多选信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int updateMarkActivitiesOptions(MarkOptionsEntity entity);
	
	/**
	 * 根据crmId查询活动多选信息
	 * 折扣信息
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	MarkOptionsEntity queryMarkActivitiesOptionsByCrmId(BigDecimal crmId);
	
	 /**
	  * 查询活动的折扣信息（目前根据活动crmId和部门编码查询）
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-17
	  *
	  */
	 List<MarkOptionsEntity> queryMarkActivityOptions(MarkOptionsEntity entity);
	 
	/**
	 * 根据crmId查询活动多选信息（根据时间建模查询，适用于更改单）
	 * 折扣信息
	 * auther:WangQianJin
	 * date:2014-08-03
	 *
	 */
	 List<MarkOptionsEntity> queryMarkActivityOptionsByBillTime(MarkOptionsEntity entity,Date billlingTime);

}

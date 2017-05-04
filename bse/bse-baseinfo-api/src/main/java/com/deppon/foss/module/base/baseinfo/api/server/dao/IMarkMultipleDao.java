package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkMultipleEntity;

/**
 * 定义市场推广活动枚举接口类Dao层
 * 
 * @author 078816
 * @date   2014-04-01
 *
 */
public interface IMarkMultipleDao {

	/**
	 * 新增一条活动枚举信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int addMarkActivitiesMultiple(List<MarkMultipleEntity> list);
	
	/**
	 * 修改一条活动枚举信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int updateMarkActivitiesMultiple(List<MarkMultipleEntity> list);
	
	/**
	 * 根据活动crmID和类型查询该活动的枚举信息是否存在
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	List<MarkMultipleEntity> queryMarkActivitiesMultiplieByCrmId(BigDecimal crmId,String type);
	
	/**
	  * 查询活动的枚举信息（目前根据活动crmId和部门编码查询）
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-17
	  *
	  */
	 List<MarkMultipleEntity> queryMarkActivityMultiplie(MarkMultipleEntity entity);
	 
	 /**
	  * 根据活动CRMID作废其关联的枚举信息
	  *
	  * auther:wangpeng_078816
	  * date:2014-6-27
	  *
	  */
	 int deleteMarkActivitiesMultiple(MarkMultipleEntity entity);
}

package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkMultipleEntity;

/**
 * 定义市场推广活动枚举接口类
 * 
 * @author 078816
 * @date   2014-04-01
 *
 */
public interface IMarkMultipleService extends IService {

	/**
	 * 新增一条活动枚举信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int addMarkActivitiesMultiple(List<MarkMultipleEntity> entity);
	
	/**
	 * 批量修改活动枚举信息
	 *
	 * auther:shenweihua_132599
	 * date:2014-11-18
	 *
	 */
	int updateMarkActivitiesMultiple(List<MarkMultipleEntity> list);
	
	/**
	 * 作废一条活动枚举信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int deleteMarkActivitiesMultiple(MarkMultipleEntity entity);
	
	/**
	 * 修改一条活动枚举信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	int updateMarkActivitiesMultiple(List<MarkMultipleEntity> list,String isActive);
	
	/**
	 * 根据活动crmID和枚举类型查询枚举信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-1
	 *
	 */
	List<MarkMultipleEntity> queryIsExistsMarkActivitiesMultiplieByCrmId(BigDecimal crmId,String type);
	
	/**
	  * 查询活动的枚举信息（目前根据活动crmId和部门编码查询）
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-17
	  *
	  */
	 List<MarkMultipleEntity> queryMarkActivityMultiplie(MarkMultipleEntity entity);

}

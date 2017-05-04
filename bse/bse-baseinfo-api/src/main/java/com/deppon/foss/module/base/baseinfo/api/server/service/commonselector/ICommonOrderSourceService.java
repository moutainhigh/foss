package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrderSourceEntity;

/**
 * 公共组件查询--订单来源查询
 * @author dujunhui-187862
 * @date 2014-9-22 下午2:40:32
 */
public interface ICommonOrderSourceService {
	/**
	 * description 通过条件查询订单来源信息
	 * @author dujunhui-187862
	 * @param entity:查询的实体 -封装条件
	 * @return List<CommonTitleEntity>:符合条件的组织集合
	 * @date 2014-9-22 上午2:41:33
	 */
	List<CommonOrderSourceEntity> searchOrderSourceByCondition(CommonOrderSourceEntity entity,int limit, int start);
	
	/**
	 *description 通过条件查询订单来源信息的总数
	 * @author dujunhui-187862
	 * @param entity:查询条件-实体
	 * @return 总数条数
	 * @date 2014-9-22 上午2:42:27
	 */
	Long countOrderSourceByCondition(CommonOrderSourceEntity entity);

}
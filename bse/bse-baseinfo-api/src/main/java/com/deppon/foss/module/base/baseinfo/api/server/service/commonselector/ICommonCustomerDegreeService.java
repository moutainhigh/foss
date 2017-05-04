package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerDegreeEntity;
/**
 * 公共组件查询--客户等级查询
 * @author dujunhui-187862
 * @date 2014-9-23 上午8:26:44
 */
public interface ICommonCustomerDegreeService {
	/**
	 * description 通过条件查询客户等级信息
	 * @author dujunhui-187862
	 * @param entity:查询的实体 -封装条件
	 * @return List<CommonTitleEntity>:符合条件的组织集合
	 * @date 2014-9-23 上午8:27:20
	 */
	List<CommonCustomerDegreeEntity> searchCustomerDegreeByCondition(CommonCustomerDegreeEntity entity,int limit, int start);
	
	/**
	 *description 通过条件查询客户等级信息的总数
	 * @author dujunhui-187862
	 * @param entity:查询条件-实体
	 * @return 总数条数
	 * @date 2014-9-23 上午8:28:33
	 */
	Long countCustomerDegreeByCondition(CommonCustomerDegreeEntity entity);

}
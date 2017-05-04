package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerProfessionEntity;

/**
 * 公共组件查询--客户行业查询
 * @author dujunhui-187862
 * @date 2014-9-23 上午10:37:42
 */
public interface ICommonCustomerProfessionService {
	/**
	 * description 通过条件查询客户行业信息
	 * @author dujunhui-187862
	 * @param entity:查询的实体 -封装条件
	 * @return List<CommonTitleEntity>:符合条件的组织集合
	 * @date 2014-9-23 上午10:38:38
	 */
	List<CommonCustomerProfessionEntity> searchCustomerProfessionByCondition(CommonCustomerProfessionEntity entity,int limit, int start);
	
	/**
	 *description 通过条件查询客户等级信息的总数
	 * @author dujunhui-187862
	 * @param entity:查询条件-实体
	 * @return 总数条数
	 * @date 2014-9-23 上午10:39:40
	 */
	Long countCustomerProfessionByCondition(CommonCustomerProfessionEntity entity);

}
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity;

/**
 * 公共组件查询--组织查询
 * @author dujunhui-187862
 * @date 2014-08-07 下午6:23:37
 */
public interface ICommonTitleService {
	/**
	 * description 通过条件查询组织信息--返回组织的编码和名称
	 * @author dujunhui-187862
	 * @param entity:查询的实体 -封装条件
	 * @return List<CommonTitleEntity>:符合条件的组织集合
	 * @date 2014-08-08 上午8:11:51
	 */
	List<CommonTitleEntity> searchTitleByCondition(CommonTitleEntity entity,int limit, int start);
	
	/**
	 *description 通过条件查询组织信息的总数--返回组织的编码和名称
	 * @author dujunhui-187862
	 * @param entity:查询条件-实体
	 * @return 总数条数
	 * @date 2014-08-08 上午8:13:44
	 */
	Long countTitleByCondition(CommonTitleEntity entity);

}


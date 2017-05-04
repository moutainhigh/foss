package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;


import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
/**
 * 
 * 网点映射关系  营业部service接口  公共选择器
 * @author 308861 
 * @date 2016-8-20 下午2:58:37
 * @since
 * @version
 */
public interface ICommonPartnerDepartmentService extends IService{
	/**
	 * 
	 * 根据   是否二级网点 与  是否可用  模糊查询营业部编码名称数据 
	 * @author 308861 
	 * @date 2016-8-20 下午3:01:07
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	public List<SaleDepartmentEntity> queryByIsTwoLevelNetwork(SaleDepartmentEntity entity,int start,int limit);
	/**
	 * 
	 * 统计根据   是否二级网点 与  是否可用  模糊查询营业部编码名称数据总数用于分页 
	 * @author 308861 
	 * @date 2016-8-20 下午3:01:51
	 * @param entity
	 * @return
	 * @see
	 */
	long queryByIsTwoLevelNetworkCount(SaleDepartmentEntity entity);
	/**
	 * 
	 * 根据   是否加盟网点 与  是否可用  模糊查询营业部编码名称数据
	 * @author 308861 
	 * @date 2016-8-20 下午3:03:05
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	public List<SaleDepartmentEntity> queryByIsLeagueSaleDept(SaleDepartmentEntity entity,int start,int limit);
	
	/**
	 * 
	 * 用于统计根据   是否加盟网点 与  是否可用  模糊查询营业部编码名称数据 
	 * @author 308861 
	 * @date 2016-8-20 下午3:03:41
	 * @param entity
	 * @return
	 * @see
	 */
	long queryByIsLeagueSaleDeptCount(SaleDepartmentEntity entity);
}

package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
/**
 * 
 * 公共选择器  网点映射关系  营业部dao接口
 * @author 308861 
 * @date 2016-8-20 上午11:37:42
 * @since
 * @version
 */
public interface ICommonPartnerDepartmentDao {
	
	/**
	 * 根据   是否二级网点 与  是否可用  模糊查询营业部编码名称数据
	 * @author 308861 
	 * @date 2016-8-20 上午11:39:34
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<SaleDepartmentEntity> queryByIsTwoLevelNetwork(SaleDepartmentEntity entity,int start,int limit);
	
	/**
	 * 
	 * 查询总条数 -----用于分页统计
	 * 根据   是否二级网点 与  是否可用  模糊查询数据 
	 * @author 308861 
	 * @date 2016-8-20 上午11:40:55
	 * @param entity
	 * @return
	 * @see
	 */
	long queryByIsTwoLevelNetworkCount(SaleDepartmentEntity entity);
	
	/**
	 * 
	 * 根据   是否加盟网点 与  是否可用 模糊查数据 
	 * @author 308861 
	 * @date 2016-8-20 上午11:42:4 9
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<SaleDepartmentEntity> queryByIsLeagueSaleDept(SaleDepartmentEntity entity,int start,int limit);
	
	/**
	 * 
	 * 查询总条数 -----------用于分页统计
	 * @author 308861 
	 * @date 2016-8-20 上午11:43:08
	 * @param entity
	 * @return
	 * @see
	 */
	long queryByIsLeagueSaleDeptCount(SaleDepartmentEntity entity);
}

package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;


import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPartnerDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPartnerDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.util.CollectionUtils;
/**
 * 
 * 网点映射关系service实现 公共选择器
 * @author 308861 
 * @date 2016-8-20 下午3:06:20
 * @since
 * @version
 */
public class CommonPartnerDepartmentService implements ICommonPartnerDepartmentService{
	/**
	 * 注入commonPartnerDepartmentDao
	 */
	private ICommonPartnerDepartmentDao commonPartnerDepartmemtDao;
	
	public void setCommonPartnerDepartmemtDao(
			ICommonPartnerDepartmentDao commonPartnerDepartmemtDao) {
		this.commonPartnerDepartmemtDao = commonPartnerDepartmemtDao;
	}

	/**
	 * 
	 * 根据   是否二级网点 与  是否可用  模糊查询营业部编码名称数据 
	 * @author 308861 
	 * @date 2016-8-20 下午3:15:12
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 */
	@Override
	public List<SaleDepartmentEntity> queryByIsTwoLevelNetwork(
			SaleDepartmentEntity entity, int start, int limit) {
		List<SaleDepartmentEntity> list=commonPartnerDepartmemtDao.
				queryByIsTwoLevelNetwork(entity, start, limit);
		if(CollectionUtils.isNotEmpty(list)){
			for (SaleDepartmentEntity resultEntity : list) {
				if(StringUtil.isEmpty(resultEntity.getCode())){
					resultEntity.setCode(" ");
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 统计根据   是否二级网点 与  是否可用  模糊查询营业部编码名称数据 
	 * @author 308861 
	 * @date 2016-8-20 下午3:16:20
	 * @param entity
	 * @return 
	 */
	@Override
	public long queryByIsTwoLevelNetworkCount(SaleDepartmentEntity entity) {
		return commonPartnerDepartmemtDao.queryByIsTwoLevelNetworkCount(entity);
	}
	/**
	 * 
	 * 根据   是否加盟网点 与  是否可用  模糊查询营业部编码名称数据 
	 * @author 308861 
	 * @date 2016-8-20 下午3:16:44
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 */
	@Override
	public List<SaleDepartmentEntity> queryByIsLeagueSaleDept(
			SaleDepartmentEntity entity, int start, int limit) {
		List<SaleDepartmentEntity> list=commonPartnerDepartmemtDao.
				queryByIsLeagueSaleDept(entity, start, limit);
		if(CollectionUtils.isNotEmpty(list)){
			for (SaleDepartmentEntity resultEntity : list) {
				if(StringUtil.isEmpty(resultEntity.getCode())){
					resultEntity.setCode(" ");
				}
			}
		}
		return list;
	}
	/**
	 * 
	 * 统计根据   是否加盟网点 与  是否可用  模糊查询营业部编码名称数据 
	 * @author 308861 
	 * @date 2016-8-20 下午3:36:10
	 * @param entity
	 * @return 
	 */
	@Override
	public long queryByIsLeagueSaleDeptCount(SaleDepartmentEntity entity) {
		return commonPartnerDepartmemtDao.queryByIsLeagueSaleDeptCount(entity);
	}
	
}

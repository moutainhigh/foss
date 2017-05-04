package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPartnerDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
/**
 * 
 * 公共选择器   网点映射  营业部名称编码Dao实现 公共选择器
 * @author 308861 
 * @date 2016-8-20 下午2:42:12
 * @since
 * @version
 */
public class CommonPartnerDepartmentDao extends SqlSessionDaoSupport implements ICommonPartnerDepartmentDao{
	
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonPartnerDepartment.";
	
	/**
	 * 
	 * 根据 二级网点名称  查询网点映射关系
	 * @author 308861 
	 * @date 2016-8-20 下午2:45:57
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SaleDepartmentEntity> queryByIsTwoLevelNetwork(
			SaleDepartmentEntity entity, int start, int limit) {
		RowBounds bounds=new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryByIsTwoLevelNetwork", entity,bounds);
	}

	/**
	 * 
	 * 统计总数----用于分页（根据二级网点名称查询） 
	 * @author 308861 
	 * @date 2016-8-20 下午2:47:49
	 * @param entity
	 * @return 
	 */
	@Override
	public long queryByIsTwoLevelNetworkCount(SaleDepartmentEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryByIsTwoLevelNetworkCount",entity);
	}

	/**
	 * 
	 * 根据一级网点名称查询网点映射关系 
	 * @author 308861 
	 * @date 2016-8-20 下午2:50:12
	 * @param entity
	 * @param start
	 * @param limit
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SaleDepartmentEntity> queryByIsLeagueSaleDept(
			SaleDepartmentEntity entity, int start, int limit) {
		RowBounds bounds=new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryByIsLeagueSaleDept", entity, bounds);
	}

	/**
	 * 
	 * 统计总数------用于分页（根据一级网点名称查询） 
	 * @author 308861 
	 * @date 2016-8-20 下午2:51:40
	 * @param entity
	 * @return 
	 */
	@Override
	public long queryByIsLeagueSaleDeptCount(SaleDepartmentEntity entity) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryByIsLeagueSaleDeptCount", entity);
	}
}

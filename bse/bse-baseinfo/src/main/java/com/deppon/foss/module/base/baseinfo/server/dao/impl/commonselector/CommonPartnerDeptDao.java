package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPartnerDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

public class CommonPartnerDeptDao extends SqlSessionDaoSupport  implements ICommonPartnerDeptDao{
 /**
  * <p>TODO(根据条件查询合伙人部门)</p> 
  * @author Foss-352676-YUANHB 
  * @date 2016-9-21 上午10:07:49
  * @param saleDepartmentEntity
  * @param start
  * @param limit
  * @return 
  * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPartnerDeptDao#queryPartnerDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity, int, int)
  */
	@SuppressWarnings("unchecked")
	@Override
	public List<SaleDepartmentEntity> queryPartnerDept(
			SaleDepartmentEntity saleDepartmentEntity, int start, int limit) {
		RowBounds bounds=new RowBounds(start, limit);
		String sql = "foss.pkp.SaleDepartmentEntityMapper.";
		return this.getSqlSession().selectList(sql+"queryPartnerDept", saleDepartmentEntity,bounds);
	}

	/**
	 * <p>TODO(根据条件查询合伙人部门数量)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-21 上午10:08:15
	 * @param saleDepartmentEntity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPartnerDeptDao#queryPartnerDeptCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity)
	 */
	@Override
	public Long queryPartnerDeptCount(SaleDepartmentEntity saleDepartmentEntity) {
		String sql = "foss.pkp.SaleDepartmentEntityMapper.";
		Long count=(Long) this.getSqlSession().selectOne(sql+"queryPartnerDeptCount", saleDepartmentEntity);
		return count ;
	}

}

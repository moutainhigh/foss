package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTransTeamDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTransTeamEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 组织公共组件根据组织编码查询下属车队组dao.
 *
 * @author dujunhui-187862
 * @date 2014-8-13 上午8:41:50
 */
public class CommonTransTeamDao extends SqlSessionDaoSupport implements ICommonTransTeamDao {
	
	/** The namespace. */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.orgAdministrativeInfo.";

	/**
	 * @description 通过组织编码查询其下所属车队组信息
	 * @param orgCode
	 * @return the list
	 * @author dujunhui-187862
	 * @date 2014-8-13 上午8:45:22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgAdministrativeInfoEntity> queryTransTeams(CommonTransTeamEntity entity, int limit, int start) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryOrgAdministrativeInfoSubTrans", entity, bounds);
	}

	/**
	 * @description 通过条件查询组织信息总数
	 * @param orgCode
	 * @return the long
	 * @author dujunhui-187862
	 * @date 2012-11-28 上午11:21:00
	 */
	@Override
	public Long countTransTeams(CommonTransTeamEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countOrgAdministrativeInfoSubTrans", entity);
	}

}

package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonQueryLdpAndExpressAndOrgInfosDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LdpOuterBranchsAndOrginfoEntity;

public class CommonQueryLdpAndExpressAndOrgInfosDao extends
		SqlSessionDaoSupport implements ICommonQueryLdpAndExpressAndOrgInfosDao {
	
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".commonQueryLdpAndExpressAndOrgInfos.";

	/**
	 * 
     * 查询自有网点+快递代理网点+虚拟网点
     * @author WangPeng
     * @date 2013-11-05 10:33AM
     * 
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpOuterBranchsAndOrginfoEntity> queryLdpAndExpressAndOrgInfoList(
			LdpOuterBranchsAndOrginfoEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryLdpAndExpressAndOrgInfoList", entity, rowBounds);
	}

	 /**
	  * 
     * 计数
     * @author WangPeng
     * @date 2013-11-05 10:33AM
     * 
     */
	@Override
	public Long recordRowCount(LdpOuterBranchsAndOrginfoEntity entity) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"recordRowCount", entity);
	}

}

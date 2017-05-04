package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonLdpAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;

/**
 * 
 * @author WangPeng
 * @Date   2013-7-19 下午5:24:28
 *
 */
public class CommonLdpAgencyCompanyDao extends SqlSessionDaoSupport 
					implements ICommonLdpAgencyCompanyDao {
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonBusinessPartnerExpress.";


	/**
	 * 
	 * 
	 * @author WangPeng
	 * @Date   2013-7-19 下午5:24:28
	 * @param  entity
	 * @param  limit
	 * @param  start
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessPartnerExpressEntity> queryLdpAgencyCompanysByCondtion(
			BusinessPartnerExpressEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryLdpAgencyCompanysByCondtion",
				entity, rowBounds);
	}

	/**
	 * 
	 * 
	 * @author WangPeng
	 * @Date   2013-7-19 下午5:24:34
	 * @param  entity
	 * @return
	 *
	 */
	@Override
	public Long countRecordByCondition(BusinessPartnerExpressEntity entity) {
		return (Long) this.getSqlSession()
				         .selectOne(NAMESPACE+"queryLdpAgencyCompanysCount", entity);
	}

}

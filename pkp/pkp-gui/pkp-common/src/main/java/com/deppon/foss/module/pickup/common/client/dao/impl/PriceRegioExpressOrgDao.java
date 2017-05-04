/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.common.client.dao.IPriceRegioExpressOrgDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;

/**
 * @author ibm-foss-sxw
 *
 */
public class PriceRegioExpressOrgDao implements IPriceRegioExpressOrgDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.PriceRegioOrgnExpressEntityMapper.";
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addPriceRegioExpressOrg(PriceRegioOrgnExpressEntity priceRegionExpress) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", priceRegionExpress.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", priceRegionExpress);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * 功能：更新记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public void updatePriceRegioExpressOrg(PriceRegioOrgnExpressEntity priceRegionExpress) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective",priceRegionExpress);
	}

	

	

}


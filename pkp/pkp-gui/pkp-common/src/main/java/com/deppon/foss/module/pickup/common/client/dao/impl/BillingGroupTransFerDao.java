/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.pickup.common.client.dao.IBillingGroupTransFerDao;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class BillingGroupTransFerDao  implements IBillingGroupTransFerDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.BillingGroupTransFerEntityMapper.";
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
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addBillingGroupTransFer(
			BillingGroupTransFerEntity e) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", e.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", e);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateBillingGroupTransFer(
			BillingGroupTransFerEntity e) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", e);

	}

	/**
	 * 删除
	 */
	public void delete(BillingGroupTransFerEntity e) {
		sqlSession.delete(NAMESPACE + "delete", e);	
	}
	
	
}



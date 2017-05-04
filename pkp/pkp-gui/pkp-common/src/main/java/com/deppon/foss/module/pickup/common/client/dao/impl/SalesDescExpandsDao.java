/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
import com.deppon.foss.module.pickup.common.client.dao.ISalesDescExpandsDao;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SalesDescExpandsDao  implements ISalesDescExpandsDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.SalesDescExpandEntityMapper.";
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
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateSalesDescExpandEntity(
			SalesDescExpandEntity e) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", e);

	}

	/**
	 * 删除
	 */
	public void delete(SalesDescExpandEntity e) {
		sqlSession.delete(NAMESPACE + "delete", e);	
	}
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addSalesDescExpandEntity(SalesDescExpandEntity e) {
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
	 * 查询网点提货区域
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	@Override
	public List<String> queryByCodeAndPickup(String code) {
		return sqlSession.selectList(NAMESPACE + "selectByCodeAndPickup",code);
	}

	/**
	 * 查询网点派送区域
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	@Override
	public List<String> queryByCodeAndDelivery(String code) {
		return sqlSession.selectList(NAMESPACE + "selectByCodeAndDelivery",code);
	}
	
}


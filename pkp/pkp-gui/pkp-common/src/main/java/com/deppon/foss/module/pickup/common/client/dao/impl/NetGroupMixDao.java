/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;
import com.deppon.foss.module.pickup.common.client.dao.INetGroupMixDao;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class NetGroupMixDao implements INetGroupMixDao {

	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.NetGroupMixEntityMapper.";
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
	@Override
	public boolean addNetGroupMix(NetGroupMixEntity netGroup) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", netGroup.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", netGroup);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * 功能：update记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void updateNetGroupMix(NetGroupMixEntity netGroup) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", netGroup);
		
	}

	/**
	 * 
	 * 功能：delete记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void delete(NetGroupMixEntity netGroupEntity) {
		sqlSession.delete(NAMESPACE + "delete", netGroupEntity);
		
	}

}

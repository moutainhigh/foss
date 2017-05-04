/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;
import com.deppon.foss.module.pickup.common.client.dao.IAsteriskSalesDeptDao;
import com.google.inject.Inject;

/**
 * 加星标营业部dao（全国到达青岛区域的部分目的营业部需要加星标）
 * @author 026123-foss-lifengteng
 *
 */
public class AsteriskSalesDeptDao  implements IAsteriskSalesDeptDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.AsteriskSalesDeptEntityMapper.";
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
	public boolean addAsteriskSalesDept(
			AsteriskSalesDeptEntity asteriskSalesDept) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", asteriskSalesDept.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", asteriskSalesDept);
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
	public void updateAsteriskSalesDept(
			AsteriskSalesDeptEntity asteriskSalesDept) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", asteriskSalesDept);

	}

	/**
	 * 删除
	 */
	public void delete(AsteriskSalesDeptEntity asteriskSalesDept) {
		sqlSession.delete(NAMESPACE + "delete", asteriskSalesDept);	
	}
	
	
}


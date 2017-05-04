/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.pickup.common.client.dao.ISalesBillingGroupDao;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SalesBillingGroupDao  implements ISalesBillingGroupDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.SalesBillingGroupEntityMapper.";
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
	@Override
	public boolean addSalesBillingGroup(
			SalesBillingGroupEntity salesBillingGroup) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", salesBillingGroup.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", salesBillingGroup);
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
	public void updateSalesBillingGroup(
			SalesBillingGroupEntity salesBillingGroup) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", salesBillingGroup);

	}

	
	@Override
	public void delete(SalesBillingGroupEntity salesBillingGroup) {
		sqlSession.delete(NAMESPACE + "delete", salesBillingGroup);	
	}
	
	/**
	 * 根据集中开单部门编码查询旗下所有营业部
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	@Override
	public List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(List<String> code){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("active", FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "querySalesListByBillingGroupCode", map);
	}
	
	/**
	 * 根据营业部编码查询所属集中开单组
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	@Override
	public List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String code){
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("active", FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "queryBillingGroupListBySalesCode", map);
	}
}

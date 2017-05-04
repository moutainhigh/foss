package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.esb.inteface.domain.fins.PartnerAccountInfo;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPartnerAccountInfoDao;

/**
 * @title: PartnerAccountInfoDaoImpl
 * @description: 合伙人账户基本数据 数据接口实现.
 * @author: 302302 李文广
 * @Date: 2016-01-08 10:44:00
 */
public class PartnerAccountInfoDaoImpl extends SqlSessionDaoSupport implements
		IPartnerAccountInfoDao {
	/**
	 * 
	 * @MethodName: insert
	 * @description: insert方法
	 * @author: 302302 李文广
	 * @date: 2016-01-08 10:44:00
	 * @param entity
	 *            void
	 */
	public void insert(PartnerAccountInfo entity) {
		getSqlSession().insert(NAME_SPACE + ".insert", entity);
	}

	/**
	 * 
	 * @MethodName: updateByPrimaryKey
	 * @description: 根据主键更新
	 * @author: 302302 李文广
	 * @date: 2016-01-08 10:44:00
	 * @param entity
	 *            void
	 */
	public void updateAccount(PartnerAccountInfo entity) {
		getSqlSession().update(NAME_SPACE + ".updateAccount", entity);
	}

	/**
	 * 
	 * @MethodName: updateByPrimaryKey
	 * @description: 根据主键更新销户信息
	 * @author: 302302 李文广
	 * @date: 2016-01-08 10:44:00
	 * @param entity
	 *            void
	 */
	public void closingAccount(PartnerAccountInfo entity) {
		getSqlSession().update(NAME_SPACE + ".closingAccount",
				entity);
	}

	/**
	 * 
	 * @MethodName: selectId
	 * @description: 查询主键是否存在
	 * @author: 302302 李文广
	 * @date: 2016-01-08 10:44:00
	 * @param entity
	 * @return PartnerAccountInfo
	 */
	public String selectId(PartnerAccountInfo entity) {
		// TODO Auto-generated method stub
		return (String) getSqlSession().selectOne(NAME_SPACE + ".selectId",
				entity);
	}
    /**
     * 模糊查询合伙人账户信息
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-1-23 下午5:33:04
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPartnerAccountInfoDao#queryParnerAccountInfoByCondition(com.deppon.esb.inteface.domain.fins.PartnerAccountInfo)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerAccountInfo> queryParnerAccountInfoByCondition(
			PartnerAccountInfo entity ,int start ,int limit) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAME_SPACE+".queryParnerAccountInfoByCondition", entity, new RowBounds(start, limit));
	}
    /**
     * 统计查询总条数，对应分页查询方法queryParnerAccountInfoByCondition
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-1-25 下午2:17:37
     * @param partnerEntity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPartnerAccountInfoDao#countPartnerPayeeInfoByCondition(com.deppon.esb.inteface.domain.fins.PartnerAccountInfo)
     */
	@Override
	public long countPartnerPayeeInfoByCondition(PartnerAccountInfo partnerEntity) {
		// TODO Auto-generated method stub
		
		return  (Long) this.getSqlSession().selectOne(NAME_SPACE+".countPartnerPayeeInfoByCondition",partnerEntity);
	}

}
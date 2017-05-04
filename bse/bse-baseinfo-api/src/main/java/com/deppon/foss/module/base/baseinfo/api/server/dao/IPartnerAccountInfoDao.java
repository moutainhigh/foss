package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.esb.inteface.domain.fins.PartnerAccountInfo;

/**
 * @title: IPartnerAccountInfoDao
 * @description: 合伙人账户基本数据 数据接口.
 * @author: 302302 李文广
 * @Date: 2016-01-08 10:44:00
 */
public interface IPartnerAccountInfoDao {

	/** 命名空间 com.deppon.dpap.module.base.shared.domain.PartnerAccountInfo */
	String NAME_SPACE = "com.deppon.esb.inteface.domain.fins.PartnerAccountInfo";

	/**
	 * 
	 * @MethodName: insert
	 * @description: insert方法
	 * @author: 302302 李文广
	 * @date: 2016-01-08 10:44:00
	 * @param entity
	 *            void
	 */
	void insert(PartnerAccountInfo entity);

	/**
	 * 
	 * @MethodName: updateAccount
	 * @description: 根据主键更新
	 * @author: 302302 李文广
	 * @date: 2016-01-08 10:44:00
	 * @param entity
	 *            void
	 */
	void updateAccount(PartnerAccountInfo entity);

	/**
	 * 
	 * @MethodName: closingAccount
	 * @description: 根据主键更新销户信息
	 * @author: 302302 李文广
	 * @date: 2016-01-08 10:44:00
	 * @param entity
	 *            void
	 */
	void closingAccount(PartnerAccountInfo entity);

	/**
	 * 
	 * @MethodName: expandByPrimaryKey
	 * @description: 查询主键是否存在
	 * @author: 302302 李文广
	 * @date: 2016-01-08 10:44:00
	 * @param entity
	 * @return PartnerAccountInfo
	 */
	String selectId(PartnerAccountInfo entity);
    /**
     * 根据条件模糊查询合伙人账户信息
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-1-23 下午5:11:32
     * @param entity
     * @return
     * @see
     */

	List<PartnerAccountInfo> queryParnerAccountInfoByCondition(
			PartnerAccountInfo entity, int start, int limit);
   /**
    * 统计查询总条数，对应分页查询方法queryParnerAccountInfoByCondition
    * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
    * @author 268984 
    * @date 2016-1-25 下午2:16:48
    * @param partnerEntity
    * @return
    * @see
    */
	long countPartnerPayeeInfoByCondition(PartnerAccountInfo partnerEntity);

}
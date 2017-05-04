package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.sql.SQLException;
import java.util.List;

import com.deppon.esb.inteface.domain.fins.PartnerAccountInfo;

/**
 * @title: PartnerAccountInfoService
 * @description: 业务接口.
 * @author: 302302 李文广
 * @Date: 2016-01-08 10:44:00
 */
public interface IPartnerAccountInfoService {

	/**
	 * <pre>
	 * 	   保存操作.
	 * </pre>
	 * 
	 * @param entity
	 *            合伙人账户基本数据
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	void save(PartnerAccountInfo entity) throws IllegalArgumentException,
			IllegalAccessException;

	/**
	 * <pre>
	 * 	   更新操作.
	 * </pre>
	 * 
	 * @param entity
	 *            合伙人账户基本数据
	 * @throws SQLException
	 */
	void update(PartnerAccountInfo entity) throws SQLException;

	/**
	 * <pre>
	 * 	   销户操作.
	 * </pre>
	 * 
	 * @param entity
	 *            合伙人账户基本数据
	 * @throws SQLException
	 */
	void updateClosingAccount(PartnerAccountInfo entity) throws SQLException;

	/**
	 * <pre>
	 * 	   合伙人账户信息同步操作.
	 * </pre>
	 * 
	 * @param entity
	 *            合伙人账户基本数据
	 * @throws SQLException
	 */
	void operation(List<PartnerAccountInfo> list);

	/**
	 * <pre>
	 * 	   查询Id是否存在操作.
	 * </pre>
	 * 
	 * @param entity
	 *            合伙人账户基本数据
	 * @return String
	 * @throws SQLException
	 */
	String findId(PartnerAccountInfo entity) throws SQLException;
   /**
    * 根据条件查询模糊查询账户信息
    * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
    * @author 268984 
    * @date 2016-1-23 下午5:09:20
    * @return
    * @see
    */
    List<PartnerAccountInfo> queryParnerAccountInfoByCondition(
		PartnerAccountInfo entity, int start, int limit);
  /**
   * 统计查询总条数，对应分页查询方法queryParnerAccountInfoByCondition
   * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
   * @author 268984 
   * @date 2016-1-25 下午2:14:40
   * @param partnerEntity
   * @return
   * @see
   */
    long countPartnerPayeeInfoByCondition(PartnerAccountInfo partnerEntity);

}
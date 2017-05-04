package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.sql.SQLException;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.fins.PartnerAccountInfo;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPartnerAccountInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPartnerAccountInfoService;


/**
 * @title: PartnerAccountInfoService
 * @description: 业务接口实现.
 * @author: 302302 李文广
 * @Date: 2016-01-08 10:44:00
 */
public class PartnerAccountInfoServiceImpl implements
		IPartnerAccountInfoService {
	private static final Logger logger = LoggerFactory
			.getLogger(PartnerAccountInfoServiceImpl.class);
	/**
	 * dao层接口
	 */
	@Autowired
	private IPartnerAccountInfoDao partnerAccountInfoDao;

	/**
	 * <pre>
	 * 	   保存操作.
	 * </pre>
	 * 
	 * @param entity
	 *            PartnerAccountInfo
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void save(PartnerAccountInfo entity)
			throws IllegalArgumentException, IllegalAccessException {
		partnerAccountInfoDao.insert(entity);
	}

	/**
	 * <pre>
	 * 	   更新操作.
	 * </pre>
	 * 
	 * @param entity
	 *            PartnerAccountInfo
	 * @throws SQLException
	 */
	public void update(PartnerAccountInfo entity) throws SQLException {
		partnerAccountInfoDao.updateAccount(entity);
	}

	/**
	 * <pre>
	 * 	  销户操作.
	 * </pre>
	 * 
	 * @param entity
	 *            PartnerAccountInfo
	 * @throws SQLException
	 */
	public void updateClosingAccount(PartnerAccountInfo entity)
			throws SQLException {
		partnerAccountInfoDao.closingAccount(entity);
	}

	/**
	 * <pre>
	 * 	  查询ID是否存在操作.
	 * </pre>
	 * 
	 * @param entity
	 *            PartnerAccountInfo
	 * @throws SQLException
	 */
	public String findId(PartnerAccountInfo entity) throws SQLException {
		// TODO Auto-generated method stub
		return partnerAccountInfoDao.selectId(entity);
	}

	/**
	 * <pre>
	 * 	   合伙人账户信息同步操作.
	 * </pre>
	 * 
	 * @param entity
	 *            合伙人账户基本数据
	 * @throws SQLException
	 */
	@Transactional
	public void operation(List<PartnerAccountInfo> list) {
		for (PartnerAccountInfo entity : list) {
			String state = entity.getState();
			// 判断状态 1是新增或修改 2是销户 否则 抛出异常
			if (state != null && state.equals("1")) {
				// 查询id是否存在，返回1或0
				String id = partnerAccountInfoDao.selectId(entity);
				// id 为0 进行新增操作 否则 进行修改操作
				if (id != null && id.equals("0")) {
					partnerAccountInfoDao.insert(entity);
				} else {
					partnerAccountInfoDao.updateAccount(entity);
				}
			} else if (state != null && state.equals("2")) {
				partnerAccountInfoDao.closingAccount(entity);
			} else {
				logger.info(" 合伙人账户信息同步操作状态信息不符合", entity);
				throw new BusinessException(
						"PartnerAccountInfo.getState()信息不符合", entity);
			}
		}
	}
    
	@Override
	public List<PartnerAccountInfo> queryParnerAccountInfoByCondition(PartnerAccountInfo entity,int start ,int limit){
		
		return  partnerAccountInfoDao.queryParnerAccountInfoByCondition(entity, start, limit);
		
	}
	/**
	 * @param partnerAccountInfoDao
	 *            the partnerAccountInfoDao to set
	 */
	public void setPartnerAccountInfoDao(
			IPartnerAccountInfoDao partnerAccountInfoDao) {
		this.partnerAccountInfoDao = partnerAccountInfoDao;
	}
     
	/**
	 * 统计查询总条数，对应分页查询的方法queryParnerAccountInfoByCondition
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-1-25 下午2:15:28
	 * @param partnerEntity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPartnerAccountInfoService#countPartnerPayeeInfoByCondition(com.deppon.esb.inteface.domain.fins.PartnerAccountInfo)
	 */
	@Override
	public long countPartnerPayeeInfoByCondition(PartnerAccountInfo partnerEntity) {
		// TODO Auto-generated method stub
		return partnerAccountInfoDao.countPartnerPayeeInfoByCondition(partnerEntity);
	}

}
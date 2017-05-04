package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncCustomerCircleRelationDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;
/**
 * 
 * 同步客户圈Dao实现
 * @author 308861 
 * @date 2016-12-21 下午3:31:40
 * @since
 * @version
 */
public class SyncCustomerCircleRelationDao extends SqlSessionDaoSupport implements ISyncCustomerCircleRelationDao{
	/**
     * mybatis 命名空间
     */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.customerCircleRelation.";

    /**
     * 
     * 新增 
     * @author 308861 
     * @date 2016-12-21 下午4:23:30
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncCustomerCircleRelationDao#addCustomerCircle(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity)
     */
	@Override
	public int addCustomerCircle(CustomerCircleEntity entity) {
		return getSqlSession().insert(NAMESPACE + "addCustomerCircle", entity);
	}

	/**
	 * 
	 * 作废  
	 * @author 308861 
	 * @date 2016-12-26 下午4:34:03
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncCustomerCircleRelationDao#delCustomerCircle(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity)
	 */
	@Override
	public int delCustomerCircle(CustomerCircleEntity entity) {
		return getSqlSession().update(NAMESPACE + "delCustomerCircle", entity);
	}

	/**
	 * 
	 * 根据 crmId 精确查询有效的客户圈信息数据   
	 * @author 308861 
	 * @date 2016-12-22 上午11:23:24
	 * @param crmId
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncCustomerCircleRelationDao#getCustomerCircleById(java.lang.String)
	 */
	@Override
	public CustomerCircleEntity getCustomerCircleById(String crmId) {
		return (CustomerCircleEntity)getSqlSession().selectOne(NAMESPACE+"getCustomerCircleById",crmId);
	}
}

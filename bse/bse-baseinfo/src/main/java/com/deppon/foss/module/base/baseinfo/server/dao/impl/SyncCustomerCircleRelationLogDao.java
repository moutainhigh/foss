package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncCustomerCircleRelationLogDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleLogEntity;
/**
 * 
 * 同步客户圈Dao实现
 * @author 308861 
 * @date 2016-12-21 下午3:31:40
 * @since
 * @version
 */
public class SyncCustomerCircleRelationLogDao extends SqlSessionDaoSupport implements ISyncCustomerCircleRelationLogDao{
	/**
     * mybatis 命名空间
     */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.customerCircleRelationLog.";

    /**
     * 
     * CRM-FOSS客户圈信息日志新增 
     * @author 308861 
     * @date 2016-12-29 上午11:37:57
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncCustomerCircleRelationLogDao#addCustomerCircleLog(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleLogEntity)
     */
	@Override
	public int addCustomerCircleLog(CustomerCircleLogEntity entity) {
		return getSqlSession().insert(NAMESPACE+"addCustomerCircleLog",entity);
	}
}

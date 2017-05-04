package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendSMSLogDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSLogEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 待处理发送短信日志实现类
 * @author WangQianJin
 * @date 2013-4-11 上午10:27:43
 */
public class PendingSendSMSLogDao extends iBatis3DaoImpl implements
		IPendingSendSMSLogDao {

	private static final String NAMESPACE = "foss.pkp.PendingSendSMSLogEntityMapper.";
	
	/**
	 * 添加待处理发送短信日志
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@Override
	public int addPendingSendmailLogEntity(PendingSendSMSLogEntity record) {
		// 设置UUID
		record.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(NAMESPACE+"insert", record);
	}

}

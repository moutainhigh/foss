package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.ILogDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MqLogEntity;

/**
 * 
 * 日志DAO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-15 下午3:28:03
 */
public class LogDao extends iBatis3DaoImpl implements ILogDao {

	/**
	 * mybatis配置namespace
	 */
	private static final String NAME_SPACE = "foss.stl.MqLogEntityMapper.";

	@SuppressWarnings("unchecked")
	@Override
	public List<MqLogEntity> queryFailLogEntity(Map<String, Object> params, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAME_SPACE + "findEcsFossFailLog", params, rowBounds);
	}

	@Override
	public void doDelFailLogById(String logId) {
		this.getSqlSession().update(NAME_SPACE + "delEcsFossLogById", logId);
	}
	
	@Override
	public void insertSuccessLog(MqLogEntity log){
		this.getSqlSession().insert(NAME_SPACE + "insertSuccessLog", log);
	}
	
	@Override
	public void insertFailLog(MqLogEntity log){
		this.getSqlSession().insert(NAME_SPACE + "insertFailLog", log);
    }

	@Override
	public Long queryLogTotalCount(Map<String, Object> params) {
		return (Long)this.getSqlSession().selectOne(NAME_SPACE + "findLogTotalCount", params);
	}
}

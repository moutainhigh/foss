package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.Date;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IJOBTimestampDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.JOBTimestampEntity;

/**
 * 
 * 定时任务时间戳DAO实现
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-7 下午2:37:44
 */
public class JOBTimestampDao extends iBatis3DaoImpl implements IJOBTimestampDao {

	/**
	 * mapper的namespace
	 */
	private static final String NAMESPACE = "foss.stl.JOBTimestampDao.";
	
	/**
	 * 
	 * 新增定时任务时间戳配置
	 * @Title: addJOBTimestamp 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 下午2:35:43
	 * @param @param entity
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	@Override
	public int addJOBTimestamp(JOBTimestampEntity entity) {
		return getSqlSession().insert(NAMESPACE+"addJOBTimestamp", entity);
	}

	/**
	 * 
	 * 根据CODE取出时间戳
	 * 
	 * @Title: getJOBTimestamp
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 上午10:40:23
	 * @param @param code
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	@Override
	public Date getJOBTimestamp(String code) {
		Date timestamp = (Date) getSqlSession().selectOne(NAMESPACE+"getJOBTimestamp", code);
		return timestamp;
	}

	/**
	 * 
	 * 更新时间戳
	 * 
	 * @Title: updateJOBTimestamp
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-7 上午10:41:27
	 * @param @param code
	 * @param @param timestamp 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	public int updateJOBTimestamp(JOBTimestampEntity entity) {
		return getSqlSession().update(NAMESPACE+"updateJOBTimestamp", entity);
	}

}

package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IOperatingLogQueryDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto;

/**
 * 操作日志dao
 * 
 * @author foss-qiaolifeng
 * @date 2012-12-10 下午7:07:07
 */
public class OperatingLogQueryDao extends iBatis3DaoImpl implements
		IOperatingLogQueryDao {

	private static final String NAME_SPACES = "foss.stl.OperatingLogEntityDao.";

	/**
	 * 根据日期查询日志
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午7:08:04
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IOperatingLogQueryDao#queryOperatingLogByDate(com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OperatingLogEntity> queryOperatingLogByDate(
			OperatingLogQueryDto dto) {

		return this.getSqlSession().selectList(
				NAME_SPACES + "queryOperatingLogListByDate", dto);
	}

	/**
	 * 根据日期查询日志总条数
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午7:08:09
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IOperatingLogQueryDao#queryTotalRowsbyDate(com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto)
	 */
	@Override
	public Long queryTotalRowsbyDate(OperatingLogQueryDto dto) {

		return (Long) this.getSqlSession().selectOne(
				NAME_SPACES + "queryOperatingLogTotalByDate", dto);
	}

	/** 
	 * 根据日期分页查询日志
	 * @author foss-qiaolifeng
	 * @date 2012-12-10 下午8:18:11
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IOperatingLogQueryDao#queryOperatingLogByDateAndPage(com.deppon.foss.module.settlement.pay.api.shared.dto.OperatingLogQueryDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OperatingLogEntity> queryOperatingLogByDateAndPage(
			OperatingLogQueryDto dto, int start, int limit) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAME_SPACES + "queryOperatingLogListByDate", dto,rowBounds);
	}

}

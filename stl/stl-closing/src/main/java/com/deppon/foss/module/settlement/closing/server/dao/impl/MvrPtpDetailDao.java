package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrPtpDetailDao;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpAllDetailsDto;

/**
 * 合伙人日报表明细查询
 * @author 311396
 * @date 2016-3-22 下午4:32:41
 */
public class MvrPtpDetailDao extends iBatis3DaoImpl implements IMvrPtpDetailDao {

	private static final String NAMESPACE = "foss.stv.MvrPtpAllDetailsEntityMapper.";

	/**
	 * 合伙人日报表明细查询
	 * @author 311396
	 * @date 2016-3-22 下午4:32:41
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<MvrPtpAllDetailResultDto> selectByConditions(MvrPtpAllDetailsDto dto,
			int offset, int limit) {

		RowBounds rb = new RowBounds(offset, limit);
		return getSqlSession().selectList(NAMESPACE + "selectDetailByParams",
				dto, rb);

	}

	/**
	 * 合伙人日报表总条数
	 * @author 311396
	 * @date 2016-3-22 下午4:32:41
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	public MvrPtpAllDetailResultDto queryTotalCounts(MvrPtpAllDetailsDto dto) 
	{
		return (MvrPtpAllDetailResultDto) getSqlSession().selectOne(NAMESPACE+"countDetailByParams",dto);
	}

}
package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDetailDao;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.VoucherDetailsDto;

/**
 * 始发月报dao
 * 
 * @author foss-qiaolifeng
 * @date 2013-4-8 下午1:57:48
 */
public class MvrDetailDao extends iBatis3DaoImpl implements IMvrDetailDao {

	private static final String NAMESPACE = "foss.stv.VoucherDetailEntityMapper.";

	/**
	 * 报表查询
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-4-8 下午1:57:49
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<VoucherDetailResultDto> selectByConditions(VoucherDetailsDto dto,
			int offset, int limit) {

		RowBounds rb = new RowBounds(offset, limit);
		return getSqlSession().selectList(NAMESPACE + "selectDetailByParams",
				dto, rb);

	}

	/**
	 * 查询事发月报报表总条数
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-4-8 下午1:57:51
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	public VoucherDetailResultDto queryTotalCounts(VoucherDetailsDto dto) 
	{
		return (VoucherDetailResultDto) getSqlSession().selectOne(NAMESPACE+"countDetailByParams",dto);
	}

	@Override
	public List<VoucherDetailResultDto> selectEgByConditions(
			VoucherDetailsDto dto, int offset, int limit) {
		
		RowBounds rb = new RowBounds(offset, limit);
		return getSqlSession().selectList(NAMESPACE + "selectEgDetailByParams",
				dto, rb);
	}

	@Override
	public VoucherDetailResultDto queryEgTotalCounts(VoucherDetailsDto dto) {
		return (VoucherDetailResultDto) getSqlSession().selectOne(NAMESPACE+"countEgDetailByParams",dto);
	}
}
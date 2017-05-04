package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ILineCargoDao;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoSerialNoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoTotalDto;

public class LineCargoDao extends iBatis3DaoImpl implements ILineCargoDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.ILineCargoDao.";

	/**
	 * @desc 查询各种数据
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午3:03:57
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoDto> queryNoHandover(LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryNoHandover";
		return super.getSqlSession().selectList(statement, parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoDto> queryHandover(LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryHandover";
		return super.getSqlSession().selectList(statement, parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoDto> queryInStock(LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryInStock";
		return super.getSqlSession().selectList(statement, parameter);
	}

	/**
	 * @desc 分页查询各种数据
	 * @param parameter
	 * @param rowBounds
	 * @return
	 * @date 2014年11月18日 下午3:03:45
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoDto> queryNoHandover(LineCargoQcDto parameter,
			RowBounds rowBounds) {
		String statement = NAMESPACE + "queryNoHandover";
		return super.getSqlSession()
				.selectList(statement, parameter, rowBounds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoDto> queryHandover(LineCargoQcDto parameter,
			RowBounds rowBounds) {
		String statement = NAMESPACE + "queryHandover";
		return super.getSqlSession()
				.selectList(statement, parameter, rowBounds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoDto> queryInStock(LineCargoQcDto parameter,
			RowBounds rowBounds) {
		String statement = NAMESPACE + "queryInStock";
		return super.getSqlSession()
				.selectList(statement, parameter, rowBounds);
	}

	/**
	 * @desc 查询各数据总量
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午3:03:22
	 * @author Ouyang
	 */
	@Override
	public LineCargoTotalDto queryNoHandoverTotal(LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryNoHandoverTotal";
		return (LineCargoTotalDto) super.getSqlSession().selectOne(statement,
				parameter);
	}

	@Override
	public LineCargoTotalDto queryHandoverTotal(LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryHandoverTotal";
		return (LineCargoTotalDto) super.getSqlSession().selectOne(statement,
				parameter);
	}

	@Override
	public LineCargoTotalDto queryInStockTotal(LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryInStockTotal";
		return (LineCargoTotalDto) super.getSqlSession().selectOne(statement,
				parameter);
	}

	/**
	 * @desc 查询运单流水号
	 * @param parameter
	 * @return
	 * @date 2014年11月19日 下午12:16:20
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoSerialNoDto> queryNoHandoverSerialNos(
			LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryNoHandoverSerialNos";
		return super.getSqlSession().selectList(statement, parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoSerialNoDto> queryHandoverSerialNos(
			LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryHandoverSerialNos";
		return super.getSqlSession().selectList(statement, parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LineCargoSerialNoDto> queryInStockSerialNos(
			LineCargoQcDto parameter) {
		String statement = NAMESPACE + "queryInStockSerialNos";
		return super.getSqlSession().selectList(statement, parameter);
	}

}

package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoSerialNoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoTotalDto;

public interface ILineCargoDao {

	/**
	 * @desc 未交接
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午3:03:57
	 * @author Ouyang
	 */
	List<LineCargoDto> queryNoHandover(LineCargoQcDto parameter);

	List<LineCargoDto> queryNoHandover(LineCargoQcDto parameter,
			RowBounds rowBounds);

	LineCargoTotalDto queryNoHandoverTotal(LineCargoQcDto parameter);

	List<LineCargoSerialNoDto> queryNoHandoverSerialNos(LineCargoQcDto parameter);

	/**
	 * @desc 在库
	 * @param parameter
	 * @return
	 * @date 2014年11月19日 下午12:08:24
	 * @author Ouyang
	 */
	List<LineCargoDto> queryInStock(LineCargoQcDto parameter);

	List<LineCargoDto> queryInStock(LineCargoQcDto parameter,
			RowBounds rowBounds);

	LineCargoTotalDto queryInStockTotal(LineCargoQcDto parameter);

	List<LineCargoSerialNoDto> queryInStockSerialNos(LineCargoQcDto parameter);

	/**
	 * @desc 交接未出发
	 * @param parameter
	 * @return
	 * @date 2014年11月19日 下午12:06:32
	 * @author Ouyang
	 */
	List<LineCargoDto> queryHandover(LineCargoQcDto parameter);

	List<LineCargoDto> queryHandover(LineCargoQcDto parameter,
			RowBounds rowBounds);

	LineCargoTotalDto queryHandoverTotal(LineCargoQcDto parameter);

	List<LineCargoSerialNoDto> queryHandoverSerialNos(
			LineCargoQcDto parameter);

}

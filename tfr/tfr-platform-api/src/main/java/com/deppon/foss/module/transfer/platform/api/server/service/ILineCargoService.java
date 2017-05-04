package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoSerialNoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoTotalDto;

public interface ILineCargoService extends IService {

	/**
	 * @desc 查询3级产品列表
	 * @return
	 * @date 2014年11月18日 上午10:56:17
	 * @author Ouyang
	 */
	List<BaseDataDictDto> queryProducts();

	/**
	 * @desc 根据子部门编码查询所属上级外场
	 * @param code
	 * @return
	 * @date 2014年11月18日 上午11:10:39
	 * @author Ouyang
	 */
	OrgAdministrativeInfoEntity queryTfrCtrBySubCode(String code);

	/**
	 * @desc 查询线路货量
	 * @param parameter
	 * @param start
	 * @param limit
	 * @return
	 * @date 2014年11月18日 下午5:16:42
	 * @author Ouyang
	 */
	List<LineCargoDto> queryLineCargo(LineCargoQcDto parameter,
			RowBounds rowBounds);

	/**
	 * @desc 总量查询
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午5:24:16
	 * @author Ouyang
	 */
	LineCargoTotalDto queryLineCargoTotal(LineCargoQcDto parameter);

	/**
	 * @desc 查询流水号
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午5:52:20
	 * @author Ouyang
	 */
	List<LineCargoSerialNoDto> querySerialNos(LineCargoQcDto parameter);

	/**
	 * @desc 导出Excel
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午5:31:39
	 * @author Ouyang
	 */
	ExportResource exportReport(LineCargoQcDto parameter);
}

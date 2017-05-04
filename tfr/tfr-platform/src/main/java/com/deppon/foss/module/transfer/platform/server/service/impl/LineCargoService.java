package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.ILineCargoDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ILineCargoService;
import com.deppon.foss.module.transfer.platform.api.shared.define.LineCargoConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoSerialNoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LineCargoTotalDto;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;

public class LineCargoService implements ILineCargoService {

	private IStockService stockService;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	private ILineCargoDao lineCargoDao;

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setLineCargoDao(ILineCargoDao lineCargoDao) {
		this.lineCargoDao = lineCargoDao;
	}

	/**
	 * @desc 查询3级产品列表
	 * @return
	 * @date 2014年11月18日 上午10:56:17
	 * @author Ouyang
	 */
	@Override
	public List<BaseDataDictDto> queryProducts() {
		return stockService.queryProductList();
	}

	/**
	 * @desc 根据子部门编码查询所属上级外场
	 * @param code
	 * @return
	 * @date 2014年11月18日 上午11:10:39
	 * @author Ouyang
	 */
	@Override
	public OrgAdministrativeInfoEntity queryTfrCtrBySubCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return new OrgAdministrativeInfoEntity();
		}

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		// 调用综合接口，查询部门所属外场
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);

		return orgEntity;
	}

	/**
	 * @desc 查询线路货量
	 * @param parameter
	 * @param start
	 * @param limit
	 * @return
	 * @date 2014年11月18日 下午4:35:11
	 * @author Ouyang
	 */
	@Override
	public List<LineCargoDto> queryLineCargo(LineCargoQcDto parameter,
			RowBounds rowBounds) {
		if (parameter == null) {
			return new ArrayList<LineCargoDto>();
		}

		String statusCode = parameter.getStatusCode();

		if (LineCargoConstants.NO_HANDOVER.equals(statusCode)) {
			return queryNoHandover(parameter, rowBounds);
		}

		if (LineCargoConstants.IN_STOCK.equals(statusCode)) {
			return queryInStock(parameter, rowBounds);
		}

		List<Integer> handoverStatus = new ArrayList<Integer>();
		if (LineCargoConstants.HANDOVER_NO_DEPART.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_20);
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_21);
		} else if (LineCargoConstants.ON_THE_WAY.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_30);
		} else if (LineCargoConstants.ARRIVED_NO_UNLOAD.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_40);
		} else {
			return new ArrayList<LineCargoDto>();
		}

		parameter.setHandoverStatus(handoverStatus);
		return queryHandover(parameter, rowBounds);
	}

	/**
	 * @desc 未交接：a->b->c b为当前外场，c为下一部门 ， 库存在a
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午4:36:41
	 * @author Ouyang
	 */
	private List<LineCargoDto> queryNoHandover(LineCargoQcDto parameter,
			RowBounds rowBounds) {
		if (rowBounds == null) {
			return lineCargoDao.queryNoHandover(parameter);
		}
		return lineCargoDao.queryNoHandover(parameter, rowBounds);
	}

	/**
	 * @desc 在库：b->c b为当前外场，c为下一部门，库存在b
	 * @param parameter
	 * @param rowBounds
	 * @return
	 * @date 2014年11月18日 下午4:41:03
	 * @author Ouyang
	 */
	private List<LineCargoDto> queryInStock(LineCargoQcDto parameter,
			RowBounds rowBounds) {
		if (rowBounds == null) {
			return lineCargoDao.queryInStock(parameter);
		}
		return lineCargoDao.queryInStock(parameter, rowBounds);
	}

	/**
	 * @desc <p>
	 *       交接未出发：a->b->c b为当前外场，c为下一部门 ，a->b做了交接,未出发
	 *       </p>
	 *       <p>
	 *       在途：a->b->c b为当前外场，c为下一部门 ，a->b交接已出发，未到达
	 *       </p>
	 *       <p>
	 *       到达未卸车：a->b->c b为当前外场，c为下一部门，a->b交接已到达
	 *       </p>
	 * @param parameter
	 * @param rowBounds
	 * @return
	 * @date 2014年11月18日 下午4:39:19
	 * @author Ouyang
	 */
	private List<LineCargoDto> queryHandover(LineCargoQcDto parameter,
			RowBounds rowBounds) {
		if (rowBounds == null) {
			return lineCargoDao.queryHandover(parameter);
		}
		return lineCargoDao.queryHandover(parameter, rowBounds);
	}

	/**
	 * @desc 总量查询
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午5:26:04
	 * @author Ouyang
	 */
	@Override
	public LineCargoTotalDto queryLineCargoTotal(LineCargoQcDto parameter) {

		if (parameter == null) {
			return new LineCargoTotalDto();
		}

		String statusCode = parameter.getStatusCode();

		if (LineCargoConstants.NO_HANDOVER.equals(statusCode)) {
			return lineCargoDao.queryNoHandoverTotal(parameter);
		}

		if (LineCargoConstants.IN_STOCK.equals(statusCode)) {
			return lineCargoDao.queryInStockTotal(parameter);
		}

		List<Integer> handoverStatus = new ArrayList<Integer>();
		if (LineCargoConstants.HANDOVER_NO_DEPART.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_20);
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_21);
		} else if (LineCargoConstants.ON_THE_WAY.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_30);
		} else if (LineCargoConstants.ARRIVED_NO_UNLOAD.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_40);
		} else {
			return new LineCargoTotalDto();
		}

		parameter.setHandoverStatus(handoverStatus);
		return lineCargoDao.queryHandoverTotal(parameter);
	}

	/**
	 * @desc 查询流水号
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午5:52:47
	 * @author Ouyang
	 */
	@Override
	public List<LineCargoSerialNoDto> querySerialNos(LineCargoQcDto parameter) {
		if (parameter == null) {
			return new ArrayList<LineCargoSerialNoDto>();
		}

		String statusCode = parameter.getStatusCode();

		if (LineCargoConstants.NO_HANDOVER.equals(statusCode)) {
			return lineCargoDao.queryNoHandoverSerialNos(parameter);
		}

		if (LineCargoConstants.IN_STOCK.equals(statusCode)) {
			return lineCargoDao.queryInStockSerialNos(parameter);
		}

		List<Integer> handoverStatus = new ArrayList<Integer>();
		if (LineCargoConstants.HANDOVER_NO_DEPART.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_20);
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_21);
		} else if (LineCargoConstants.ON_THE_WAY.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_30);
		} else if (LineCargoConstants.ARRIVED_NO_UNLOAD.equals(statusCode)) {
			handoverStatus.add(PlatformConstants.SONAR_NUMBER_40);
		} else {
			handoverStatus.add(0);
		}
		parameter.setHandoverStatus(handoverStatus);
		return lineCargoDao.queryHandoverSerialNos(parameter);
	}

	/**
	 * @desc 导出Excel
	 * @param parameter
	 * @return
	 * @date 2014年11月18日 下午5:45:41
	 * @author Ouyang
	 */
	@Override
	@Deprecated
	public ExportResource exportReport(LineCargoQcDto parameter) {
		List<LineCargoDto> lineCargoDtos = queryLineCargo(parameter, null);

		if (CollectionUtils.isEmpty(lineCargoDtos)) {
			throw new TfrBusinessException("没有符合条件的记录。");
		}

		List<List<String>> resultList = new ArrayList<List<String>>();

		for (LineCargoDto item : lineCargoDtos) {
			List<String> result = new ArrayList<String>();
			// 下一部门
			result.add(item.getDestDeptName());
			// 运单号
			result.add(item.getWaybillNo());
			// 货物状态
			result.add(item.getStatusName());
			// 运单件数
			result.add(String.valueOf(item.getWaybillQty()));
			// 当前件数
			result.add(String.valueOf(item.getCurrentQty()));
			// 重量
			result.add(String.valueOf(item.getCurrentWeight()));
			// 体积
			result.add(String.valueOf(item.getCurrentVolume()));
			// 产品名称
			result.add(item.getProductName());
			// 开单时间
			Date staTime = item.getBillTime();
			result.add(String.format("%1$tF %2$tT", staTime, staTime));
			// 车次号
			result.add(item.getVehicleassembleNo());
			// 车牌号
			result.add(item.getVehicleNo());

			resultList.add(result);
		}

		ExportResource sheet = new ExportResource();
		sheet.setHeads(LineCargoConstants.HEADER);
		sheet.setRowList(resultList);
		return sheet;
	}

}

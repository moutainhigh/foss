package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfiEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfiEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfiDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

public class MvrNrfiEntityService implements IMvrNrfiEntityService {

	private IMvrNrfiEntityDao mvrNrfiEntityDao;

	public void setMvrNrfiEntityDao(IMvrNrfiEntityDao mvrNrfiEntityDao) {
		this.mvrNrfiEntityDao = mvrNrfiEntityDao;
	}

	/**
	 * 查询始发专线往来月报表
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	public List<MvrNrfiEntity> selectColumnsByConditions(MvrNrfiDto dto,
			int start, int limit) throws SettlementException {

		checkParams(dto);
		return this.mvrNrfiEntityDao.selectColumnsByConditions(dto, start,
				limit);

	}

	/**
	 * 查询始发专线往来月报表合计
	 * 
	 * @author 163576-foss-guxinhua
	 * @date 2013-11-6 下午5:56:31
	 * @param dto
	 * @return
	 */
	public MvrNrfiEntity selectTotalByConditions(MvrNrfiDto dto)
			throws SettlementException {

		checkParams(dto);
		return this.mvrNrfiEntityDao.selectTotalByConditions(dto);
	}

	private void checkParams(MvrNrfiDto dto) {

		if (dto == null) {
			throw new SettlementException("查询参数不能为空");
		}

		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询参数期间不能为空");
		}

	}
}

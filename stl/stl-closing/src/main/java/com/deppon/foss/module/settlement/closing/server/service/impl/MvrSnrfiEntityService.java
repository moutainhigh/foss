package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrSnrfiEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrSnrfiEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfiDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

public class MvrSnrfiEntityService implements IMvrSnrfiEntityService{
	private IMvrSnrfiEntityDao mvrSnrfiEntityDao;
	
	public void setMvrSnrfiEntityDao(IMvrSnrfiEntityDao mvrSnrfiEntityDao) {
		this.mvrSnrfiEntityDao = mvrSnrfiEntityDao;
	}
	/**
	 * 查询始发专线往来月报表
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-05-29 下午5:06:31
	 * @param dto
	 * @return
	 */
	@Override
	public List<MvrNrfiEntity> selectColumnsByConditions(MvrNrfiDto dto,
			int start, int limit) throws SettlementException {
		checkParams(dto);
		return this.mvrSnrfiEntityDao.selectColumnsByConditions(dto, start,
				limit);
	}
	/**
	 * 查询始发专线往来月报表合计
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-05-29 下午5:06:31
	 * @param dto
	 * @return
	 */
	@Override
	public MvrNrfiEntity selectTotalByConditions(MvrNrfiDto dto)
			throws SettlementException {
		checkParams(dto);
		return this.mvrSnrfiEntityDao.selectTotalByConditions(dto);
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

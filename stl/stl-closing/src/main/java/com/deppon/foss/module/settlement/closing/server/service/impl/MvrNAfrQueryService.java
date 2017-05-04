package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNAfrEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNAfrQueryService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNAfrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNAfrDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 空运月报表查询服务实现.
 *
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午9:55:37
 */
public class MvrNAfrQueryService implements IMvrNAfrQueryService {

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrNAfrQueryService.class);

	/** 数据库接口. */
	private IMvrNAfrEntityDao mvrNAfrEntityDao;

	/**
	 * 查询空运月报表列表.
	 *
	 * @param dto the dto
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrNAfrQueryService#queryMvrNAfr(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNAfrDto,
	 * int, int)
	 */
	@Override
	public List<MvrNAfrEntity> queryMvrNAfr(MvrNAfrDto dto, int offset, int limit) {

		LOGGER.info("查询空运月报表列表,dto:" + dto);

		// 查询参数为空，抛出异常
		if (dto == null) {
			throw new SettlementException("查询参数为空");
		}

		// 查询期间为空，抛出异常
		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询报表期间为空");
		}

		// 查询结果
		List<MvrNAfrEntity> queryList = mvrNAfrEntityDao.queryMvrNAfr(dto, offset,
				limit);

		LOGGER.info("结束查询空运月报表列表");

		return queryList;
	}

	/**
	 * 查询空运月报表汇总.
	 *
	 * @param dto the dto
	 * @return the mvr afr dto
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrNAfrQueryService#queryMvrNAfrTotal(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNAfrDto)
	 */
	@Override
	public MvrNAfrDto queryMvrNAfrTotal(MvrNAfrDto dto) {

		LOGGER.info("查询空运月报表汇总,dto:" + dto);

		// 查询参数为空，抛出异常
		if (dto == null) {
			throw new SettlementException("查询参数为空");
		}

		// 查询期间为空，抛出异常
		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询报表期间为空");
		}

		// 查询结果
		MvrNAfrDto queryDto = mvrNAfrEntityDao.queryMvrNAfrTotal(dto);

		LOGGER.info("结束查询空运月报表汇总");

		return queryDto;
	}

	/**
	 * Sets the mvr afr entity dao.
	 *
	 * @param mvrNAfrEntityDao the new mvr afr entity dao
	 */
	public void setMvrNAfrEntityDao(IMvrNAfrEntityDao mvrNAfrEntityDao) {
		this.mvrNAfrEntityDao = mvrNAfrEntityDao;
	}

}

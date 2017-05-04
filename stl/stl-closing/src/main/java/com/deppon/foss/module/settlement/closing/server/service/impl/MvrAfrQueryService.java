package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrAfrEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfrQueryService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrAfrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfrDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 空运月报表查询服务实现.
 *
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午9:55:37
 */
public class MvrAfrQueryService implements IMvrAfrQueryService {

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrAfrQueryService.class);

	/** 数据库接口. */
	private IMvrAfrEntityDao mvrAfrEntityDao;

	/**
	 * 查询空运月报表列表.
	 *
	 * @param dto the dto
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfrQueryService#queryMvrAfr(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfrDto,
	 * int, int)
	 */
	@Override
	public List<MvrAfrEntity> queryMvrAfr(MvrAfrDto dto, int offset, int limit) {

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
		List<MvrAfrEntity> queryList = mvrAfrEntityDao.queryMvrAfr(dto, offset,
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
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrAfrQueryService#queryMvrAfrTotal(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrAfrDto)
	 */
	@Override
	public MvrAfrDto queryMvrAfrTotal(MvrAfrDto dto) {

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
		MvrAfrDto queryDto = mvrAfrEntityDao.queryMvrAfrTotal(dto);

		LOGGER.info("结束查询空运月报表汇总");

		return queryDto;
	}

	/**
	 * Sets the mvr afr entity dao.
	 *
	 * @param mvrAfrEntityDao the new mvr afr entity dao
	 */
	public void setMvrAfrEntityDao(IMvrAfrEntityDao mvrAfrEntityDao) {
		this.mvrAfrEntityDao = mvrAfrEntityDao;
	}

}

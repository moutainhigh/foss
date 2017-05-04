package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrLwoEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrLwoService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLwoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLwoDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 快递代理月报表查询服务实现.
 *
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午9:55:37
 */
public class MvrLwoService implements IMvrLwoService {

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(MvrLwoService.class);

	/** 数据库接口. */
	private IMvrLwoEntityDao mvrLwoEntityDao;

	/**
	 * 查询快递代理月报表列表.
	 *
	 * @param dto the dto
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrLwoQueryService#queryMvrLwo(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLwoDto,
	 * int, int)
	 */
	@Override
	public List<MvrLwoEntity> queryMvrLwo(MvrLwoDto dto, int offset, int limit) {

		LOGGER.info("查询快递代理月报表列表,dto:" + dto);

		// 查询参数为空，抛出异常
		if (dto == null) {
			throw new SettlementException("查询参数为空");
		}

		// 查询期间为空，抛出异常
		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询报表期间为空");
		}

		// 查询结果
		List<MvrLwoEntity> queryList = mvrLwoEntityDao.queryMvrLwo(dto, offset,
				limit);

		LOGGER.info("结束查询快递代理月报表列表");

		return queryList;
	}

	/**
	 * 查询快递代理月报表汇总.
	 *
	 * @param dto the dto
	 * @return the mvr afr dto
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrLwoQueryService#queryMvrLwoTotal(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLwoDto)
	 */
	@Override
	public MvrLwoDto queryMvrLwoTotal(MvrLwoDto dto) {

		LOGGER.info("查询快递代理月报表汇总,dto:" + dto);

		// 查询参数为空，抛出异常
		if (dto == null) {
			throw new SettlementException("查询参数为空");
		}

		// 查询期间为空，抛出异常
		if (StringUtils.isEmpty(dto.getPeriod())) {
			throw new SettlementException("查询报表期间为空");
		}

		// 查询结果
		MvrLwoDto queryDto = mvrLwoEntityDao.queryMvrLwoTotal(dto);

		LOGGER.info("结束查询快递代理月报表汇总");

		return queryDto;
	}

	/**
	 * Sets the mvr afr entity dao.
	 *
	 * @param MvrLwoEntityDao the new mvr afr entity dao
	 */
	public void setMvrLwoEntityDao(IMvrLwoEntityDao mvrLwoEntityDao) {
		this.mvrLwoEntityDao = mvrLwoEntityDao;
	}

}

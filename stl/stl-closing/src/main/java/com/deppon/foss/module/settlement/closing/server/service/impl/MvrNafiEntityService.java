package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNafiEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNafiEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNafiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNafiDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 空运往来月报表Service.
 *
 * @author guxinhua
 * @date 2013-3-6 下午2:22:28
 */
public class MvrNafiEntityService implements IMvrNafiEntityService {

	private final static Logger LOGGER = LogManager.getLogger(MvrNafiEntityService.class);
	
	/** 空运往来月报表Dao. */
	private IMvrNafiEntityDao mvrNafiEntityDao;

	/**
	 * 查询空运往来月报表.
	 *
	 * @param dto the dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author guxinhua
	 * @date 2013-3-6 下午2:23:13
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto,
	 * int, int)
	 */
	@Override
	public List<MvrNafiEntity> selectByConditions(MvrNafiDto dto, int start,
			int limit)  throws SettlementException {
		if(null == dto){
			//内部错误，空运往来月报表DTO参数为空
			throw new SettlementException("内部错误，空运往来月报表DTO参数为空！");
		}
		LOGGER.info("查询空运往来月报表报表 Start：" + dto.getPeriod());
		
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询空运往来月报表报表
		List<MvrNafiEntity> list = mvrNafiEntityDao.selectByConditions(dto, start, limit);
		
		LOGGER.info("查询空运往来月报表报表 End.");
		
		return list;
	}

	/**
	 * 查询空运往来月报表合计.
	 *
	 * @param dto the dto
	 * @return the mvr rfd entity
	 * @author Administrator
	 * @date 2013-3-6 下午2:23:29
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto)
	 */
	@Override
	public MvrNafiDto selectTotalByConditions(MvrNafiDto dto)  throws SettlementException {
		if(null == dto){
			//内部错误，空运往来月报表DTO参数为空
			throw new SettlementException("内部错误，空运往来月报表DTO参数为空！");
		}
		LOGGER.info("查询空运往来月报表报表合计 Start：" + dto.getPeriod());
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询空运往来月报表报表合计
		MvrNafiDto mvrRfdDto = mvrNafiEntityDao.selectTotalByConditions(dto);

		LOGGER.info("查询空运往来月报表报表合计 End.");
		return mvrRfdDto;
	}

	/**
	 * 验证MvrRfdDto
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 下午2:27:54
	 */
	private void validationMvrRfdDto(MvrNafiDto dto){
		
		if (StringUtil.isBlank(dto.getPeriod())){
			//统计时间期间不能为空
			throw new SettlementException("统计时间期间不能为空！");
		}
		
	}

	/**
	 * @param mvrNafiEntityDao the mvrNafiEntityDao to set
	 */
	public void setMvrNafiEntityDao(IMvrNafiEntityDao mvrNafiEntityDao) {
		this.mvrNafiEntityDao = mvrNafiEntityDao;
	}
 
}

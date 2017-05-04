package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfdoEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfdoEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfdoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfdoDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 01到达月报表Service.
 *
 * @author guxinhua
 * @date 2013-3-6 下午2:22:28
 */
public class MvrNrfdoEntityService implements IMvrNrfdoEntityService {

	private final static Logger LOGGER = LogManager.getLogger(MvrNrfdoEntityService.class);
	
	/** 01到达月报表Dao. */
	private IMvrNrfdoEntityDao mvrNrfdoEntityDao;

	/**
	 * 查询01到达月报表.
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
	public List<MvrNrfdoEntity> selectByConditions(MvrNrfdoDto dto, int start,
			int limit)  throws SettlementException {
		if(null == dto){
			//内部错误，专线到达DTO参数为空
			throw new SettlementException("内部错误，专线到达DTO参数为空！");
		}
		LOGGER.info("查询专线到达报表 Start：" + dto.getPeriod());
		
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询专线到达报表
		List<MvrNrfdoEntity> list = mvrNrfdoEntityDao.selectByConditions(dto, start, limit);
		
		LOGGER.info("查询专线到达报表 End.");
		
		return list;
	}

	/**
	 * 查询01到达月报表合计.
	 *
	 * @param dto the dto
	 * @return the mvr rfd entity
	 * @author Administrator
	 * @date 2013-3-6 下午2:23:29
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto)
	 */
	@Override
	public MvrNrfdoDto selectTotalByConditions(MvrNrfdoDto dto)  throws SettlementException {
		if(null == dto){
			//内部错误，专线到达DTO参数为空
			throw new SettlementException("内部错误，专线到达DTO参数为空！");
		}
		LOGGER.info("查询专线到达报表合计 Start：" + dto.getPeriod());
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询专线到达报表合计
		MvrNrfdoDto mvrRfdDto = mvrNrfdoEntityDao.selectTotalByConditions(dto);

		LOGGER.info("查询专线到达报表合计 End.");
		return mvrRfdDto;
	}

	/**
	 * 验证MvrRfdDto
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 下午2:27:54
	 */
	private void validationMvrRfdDto(MvrNrfdoDto dto){
		
		if (StringUtil.isBlank(dto.getPeriod())){
			//统计时间期间不能为空
			throw new SettlementException("统计时间期间不能为空！");
		}

	}

	/**
	 * @param mvrNrfdoEntityDao the mvrNrfdoEntityDao to set
	 */
	public void setMvrNrfdoEntityDao(IMvrNrfdoEntityDao mvrNrfdoEntityDao) {
		this.mvrNrfdoEntityDao = mvrNrfdoEntityDao;
	}
 
}

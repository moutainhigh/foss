package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfdEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.ClosingPeriodDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 专线到达Service.
 *
 * @author guxinhua
 * @date 2013-3-6 下午2:22:28
 */
public class MvrRfdEntityService implements IMvrRfdEntityService {

	private final static Logger LOGGER = LogManager.getLogger(MvrRfdEntityService.class);
	
	/** 专线到达Dao. */
	private IMvrRfdEntityDao mvrRfdEntityDao;

	/**
	 * 查询专线到达报表.
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
	public List<MvrRfdEntity> selectByConditions(MvrRfdDto dto, int start,
			int limit)  throws SettlementException {
		if(null == dto){
			//内部错误，专线到达DTO参数为空
			throw new SettlementException("内部错误，专线到达DTO参数为空！");
		}
		LOGGER.info("查询专线到达报表 Start：" + dto.getPeriod());
		
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询专线到达报表
		List<MvrRfdEntity> list = mvrRfdEntityDao.selectByConditions(dto, start, limit);
		
		LOGGER.info("查询专线到达报表 End.");
		
		return list;
	}

	/**
	 * 查询专线到达报表合计.
	 *
	 * @param dto the dto
	 * @return the mvr rfd entity
	 * @author Administrator
	 * @date 2013-3-6 下午2:23:29
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto)
	 */
	@Override
	public MvrRfdDto selectTotalByConditions(MvrRfdDto dto)  throws SettlementException {
		if(null == dto){
			//内部错误，专线到达DTO参数为空
			throw new SettlementException("内部错误，专线到达DTO参数为空！");
		}
		LOGGER.info("查询专线到达报表合计 Start：" + dto.getPeriod());
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询专线到达报表合计
		MvrRfdDto mvrRfdDto = mvrRfdEntityDao.selectTotalByConditions(dto);

		LOGGER.info("查询专线到达报表合计 End.");
		return mvrRfdDto;
	}

	/**
	 * 验证MvrRfdDto
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 下午2:27:54
	 */
	private void validationMvrRfdDto(MvrRfdDto dto){
		
		if (StringUtil.isBlank(dto.getPeriod())){
			//统计时间期间不能为空
			throw new SettlementException("统计时间期间不能为空！");
		}

	}
	
	/**
	 * Sets the mvr rfd entity dao.
	 *
	 * @param mvrRfdEntityDao the new mvr rfd entity dao
	 */
	public void setMvrRfdEntityDao(IMvrRfdEntityDao mvrRfdEntityDao) {
		this.mvrRfdEntityDao = mvrRfdEntityDao;
	}
	
	@Override
	public List<ClosingPeriodDto> queryClosingPeriodList(){
		return mvrRfdEntityDao.queryClosingPeriodList();
	}

}

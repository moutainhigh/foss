package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.closing.api.server.dao.IDvdDhkEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IDvdDhkEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.DvdDhkEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.DvdDhkDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.DateUtils;

/**
 * 代汇款明细报表Service.
 *
 * @author guxinhua
 * @date 2013-3-6 下午2:22:28
 */
public class DvdDhkEntityService implements IDvdDhkEntityService {

	private final static Logger LOGGER = LogManager.getLogger(DvdDhkEntityService.class);
	//定义常量--一个月31天
	public final static int  MONTH  = 31;
	
	/** 代汇款明细报表Dao. */
	private IDvdDhkEntityDao dvdDhkEntityDao;

	/**
	 * 查询代汇款明细报表.
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
	public List<DvdDhkEntity> selectByConditions(DvdDhkDto dto, int start,
			int limit)  throws SettlementException {
		if(null == dto){
			//内部错误，代汇款明细报表DTO参数为空
			throw new SettlementException("内部错误，代汇款明细报表DTO参数为空！");
		}
		LOGGER.info("查询代汇款明细报表 Start：" + dto.getPeriod());
		
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询代汇款明细报表
		List<DvdDhkEntity> list = dvdDhkEntityDao.selectByConditions(dto, start, limit);
		
		LOGGER.info("查询代汇款明细报表 End.");
		
		return list;
	}

	/**
	 * 查询代汇款明细报表合计.
	 *
	 * @param dto the dto
	 * @return the mvr rfd entity
	 * @author Administrator
	 * @date 2013-3-6 下午2:23:29
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfdEntityService#selectTotalByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfdDto)
	 */
	@Override
	public DvdDhkDto selectTotalByConditions(DvdDhkDto dto)  throws SettlementException {
		if(null == dto){
			//内部错误，代汇款明细报表DTO参数为空
			throw new SettlementException("内部错误，代汇款明细报表DTO参数为空！");
		}
		LOGGER.info("查询代汇款明细报表合计 Start：" + dto.getPeriod());
		// 验证MvrRfdDto
		validationMvrRfdDto(dto);
		// 查询代汇款明细报表合计
		DvdDhkDto mvrRfdDto = dvdDhkEntityDao.selectTotalByConditions(dto);

		LOGGER.info("查询代汇款明细报表合计 End.");
		return mvrRfdDto;
	}

	/**
	 * 验证MvrRfdDto
	 * 
	 * @author guxinhua
	 * @date 2013-3-6 下午2:27:54
	 */
	private void validationMvrRfdDto(DvdDhkDto dto){
		if(dto.getStartDate() == null){
			throw new SettlementException("开始时间不能为空！");
		}
		
		if(dto.getEndDate() == null){
			throw new SettlementException("结束时间不能为空！");
		}
		
		if(DateUtils.getTimeDiff(dto.getStartDate(), dto.getEndDate()) > MONTH){
			throw new SettlementException("查询日期范围不能超过31天！");
		}
		
		if(StringUtil.isBlank(dto.getRemitOrgCode()) 
		 &&StringUtil.isBlank(dto.getByremitOrgCode())){
			throw new SettlementException("代汇款部门、被代汇款部门，两者必须选择一项！");
		}

	}

	/**
	 * @param dvdDhkEntityDao the dvdDhkEntityDao to set
	 */
	public void setDvdDhkEntityDao(IDvdDhkEntityDao dvdDhkEntityDao) {
		this.dvdDhkEntityDao = dvdDhkEntityDao;
	}
 
}

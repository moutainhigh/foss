package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfonoEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfonoEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfonoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfonoEntityTotalDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 01普通业务始发月报表Service接口实现
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfonoEntityService implements IMvrNrfonoEntityService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(MvrNrfonoEntityService.class);

	private IMvrNrfonoEntityDao mvrNrfonoEntityDao;

	/**
	 * 根据条件查询始发应收列表（分页）
	 * 
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.ImvrNrfonoEntityService#selectByConditionsAndPage(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfonoEntityQueryDto,
	 *      int, int)
	 */
	@Override
	public MvrNrfonoEntityResultDto selectByConditionsAndPage(
			MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto, int start, int limit) {

		logger.debug("根据条件查询始发应收列表（分页）开始...");

		// 输入参数不能为空
		if (mvrNrfonoEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrNrfonoEntityResultDto rtnDto = new MvrNrfonoEntityResultDto();

		// 查询统计信息结果
		MvrNrfonoEntityTotalDto mvrNrfonoEntityTotalDto = mvrNrfonoEntityDao
				.selectTotalByConditions(mvrNrfonoEntityQueryDto);
		// 如果统计结果存在，分页查询始发应收
		if (mvrNrfonoEntityTotalDto != null
				&& mvrNrfonoEntityTotalDto.getMvrNrfonoEntityTotalRows() > 0) {

			// 查询始发应收结果
			List<MvrNrfonoEntity> mvrNrfonoEntityList = mvrNrfonoEntityDao
					.selectByConditionsAndPage(mvrNrfonoEntityQueryDto, start,
							limit);

			mvrNrfonoEntityTotalDto.setDestOrgName("合计：");
			// 如果始发应收结果数据存在
			if (mvrNrfonoEntityList.size() > 0) {
				
				// 生成始发应收统计信息实体
				MvrNrfonoEntity mvrNrfonoEntity = new MvrNrfonoEntity();

				// 将查询出的统计信息结果赋值到始发应收统计信息实体
				BeanUtils.copyProperties(mvrNrfonoEntityTotalDto, mvrNrfonoEntity);

				//加入统计信息
				mvrNrfonoEntityList.add(mvrNrfonoEntity);
				
				// 设置到返回Dto中
				rtnDto.setMvrNrfonoEntityList(mvrNrfonoEntityList);
			}

			// 将查询出的统计信息结果总条数设置到返回Dto中
			rtnDto.setMvrNrfonoEntityTotalRows(mvrNrfonoEntityTotalDto
					.getMvrNrfonoEntityTotalRows());
		}

		logger.debug("根据条件查询始发应收列表（分页）结束...");
		return rtnDto;
	}

	/**
	 * 根据条件查询始发应收列表（不分页，提供给导出使用）
	 * 
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.ImvrNrfonoEntityService#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfonoEntityQueryDto)
	 */
	@Override
	public MvrNrfonoEntityResultDto selectByConditions(
			MvrNrfonoEntityQueryDto mvrNrfonoEntityQueryDto) {

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）开始...");

		// 输入参数不能为空
		if (mvrNrfonoEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrNrfonoEntityResultDto rtnDto = new MvrNrfonoEntityResultDto();

		// 查询始发应收结果
		List<MvrNrfonoEntity> mvrNrfonoEntityList = mvrNrfonoEntityDao
				.selectByConditions(mvrNrfonoEntityQueryDto);

		// 如果始发应收结果数据存在
		if (mvrNrfonoEntityList.size() > 0) {

			// 设置到返回Dto中
			rtnDto.setMvrNrfonoEntityList(mvrNrfonoEntityList);
		}

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）结束...");
		return rtnDto;
	}

	public IMvrNrfonoEntityDao getmvrNrfonoEntityDao() {
		return mvrNrfonoEntityDao;
	}

	public void setMvrNrfonoEntityDao(IMvrNrfonoEntityDao mvrNrfonoEntityDao) {
		this.mvrNrfonoEntityDao = mvrNrfonoEntityDao;
	}

}

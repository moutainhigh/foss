package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfostEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfostEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityTotalDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发应收月报Service接口实现
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午4:28:49
 */
public class MvrNrfostEntityService implements IMvrNrfostEntityService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(MvrNrfostEntityService.class);

	private IMvrNrfostEntityDao mvrNrfostEntityDao;

	/**
	 * 根据条件查询始发应收列表（分页）
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:59:45
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfostEntityService#selectByConditionsAndPage(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto,
	 *      int, int)
	 */
	@Override
	public MvrNrfostEntityResultDto selectByConditionsAndPage(
			MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto, int start, int limit) {

		logger.debug("根据条件查询始发应收列表（分页）开始...");

		// 输入参数不能为空
		if (mvrNrfostEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrNrfostEntityResultDto rtnDto = new MvrNrfostEntityResultDto();

		// 查询统计信息结果
		MvrNrfostEntityTotalDto mvrNrfostEntityTotalDto = mvrNrfostEntityDao
				.selectTotalByConditions(mvrNrfostEntityQueryDto);
		// 如果统计结果存在，分页查询始发应收
		if (mvrNrfostEntityTotalDto != null
				&& mvrNrfostEntityTotalDto.getMvrNrfostEntityTotalRows() > 0) {

			// 查询始发应收结果
			List<MvrNrfostEntity> mvrNrfostEntityList = mvrNrfostEntityDao
					.selectByConditionsAndPage(mvrNrfostEntityQueryDto, start,
							limit);

			// 如果始发应收结果数据存在
			if (mvrNrfostEntityList.size() > 0) {
				
				// 生成始发应收统计信息实体
				MvrNrfostEntity mvrNrfostEntity = new MvrNrfostEntity();

				// 将查询出的统计信息结果赋值到始发应收统计信息实体
				BeanUtils.copyProperties(mvrNrfostEntityTotalDto, mvrNrfostEntity);

				//加入统计信息
				mvrNrfostEntityList.add(mvrNrfostEntity);
				
				// 设置到返回Dto中
				rtnDto.setMvrNrfostEntityList(mvrNrfostEntityList);
			}

			// 将查询出的统计信息结果总条数设置到返回Dto中
			rtnDto.setMvrNrfostEntityTotalRows(mvrNrfostEntityTotalDto
					.getMvrNrfostEntityTotalRows());
		}

		logger.debug("根据条件查询始发应收列表（分页）结束...");
		return rtnDto;
	}

	/**
	 * 根据条件查询始发应收列表（不分页，提供给导出使用）
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:29:49
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfostEntityService#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostEntityQueryDto)
	 */
	@Override
	public MvrNrfostEntityResultDto selectByConditions(
			MvrNrfostEntityQueryDto mvrNrfostEntityQueryDto) {

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）开始...");

		// 输入参数不能为空
		if (mvrNrfostEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrNrfostEntityResultDto rtnDto = new MvrNrfostEntityResultDto();

		// 查询始发应收结果
		List<MvrNrfostEntity> mvrNrfostEntityList = mvrNrfostEntityDao
				.selectByConditions(mvrNrfostEntityQueryDto);

		// 如果始发应收结果数据存在
		if (mvrNrfostEntityList.size() > 0) {

			// 设置到返回Dto中
			rtnDto.setMvrNrfostEntityList(mvrNrfostEntityList);
		}

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）结束...");
		return rtnDto;
	}

	public IMvrNrfostEntityDao getMvrNrfostEntityDao() {
		return mvrNrfostEntityDao;
	}

	public void setMvrNrfostEntityDao(IMvrNrfostEntityDao mvrNrfostEntityDao) {
		this.mvrNrfostEntityDao = mvrNrfostEntityDao;
	}

}

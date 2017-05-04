package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfontEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfontEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityTotalDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发应收月报Service接口实现
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午4:28:49
 */
public class MvrNrfontEntityService implements IMvrNrfontEntityService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(MvrNrfontEntityService.class);

	private IMvrNrfontEntityDao mvrNrfontEntityDao;

	/**
	 * 根据条件查询始发应收列表（分页）
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:59:45
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfontEntityService#selectByConditionsAndPage(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto,
	 *      int, int)
	 */
	@Override
	public MvrNrfontEntityResultDto selectByConditionsAndPage(
			MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto, int start, int limit) {

		logger.debug("根据条件查询始发应收列表（分页）开始...");

		// 输入参数不能为空
		if (mvrNrfontEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrNrfontEntityResultDto rtnDto = new MvrNrfontEntityResultDto();

		// 查询统计信息结果
		MvrNrfontEntityTotalDto mvrNrfontEntityTotalDto = mvrNrfontEntityDao
				.selectTotalByConditions(mvrNrfontEntityQueryDto);
		// 如果统计结果存在，分页查询始发应收
		if (mvrNrfontEntityTotalDto != null
				&& mvrNrfontEntityTotalDto.getMvrNrfontEntityTotalRows() > 0) {

			// 查询始发应收结果
			List<MvrNrfontEntity> mvrNrfontEntityList = mvrNrfontEntityDao
					.selectByConditionsAndPage(mvrNrfontEntityQueryDto, start,
							limit);

			// 如果始发应收结果数据存在
			if (mvrNrfontEntityList.size() > 0) {
				
				// 生成始发应收统计信息实体
				MvrNrfontEntity mvrNrfontEntity = new MvrNrfontEntity();

				// 将查询出的统计信息结果赋值到始发应收统计信息实体
				BeanUtils.copyProperties(mvrNrfontEntityTotalDto, mvrNrfontEntity);

				//加入统计信息
				mvrNrfontEntityList.add(mvrNrfontEntity);
				
				// 设置到返回Dto中
				rtnDto.setMvrNrfontEntityList(mvrNrfontEntityList);
			}

			// 将查询出的统计信息结果总条数设置到返回Dto中
			rtnDto.setMvrNrfontEntityTotalRows(mvrNrfontEntityTotalDto
					.getMvrNrfontEntityTotalRows());
		}

		logger.debug("根据条件查询始发应收列表（分页）结束...");
		return rtnDto;
	}

	/**
	 * 根据条件查询始发应收列表（不分页，提供给导出使用）
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:29:49
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfontEntityService#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfontEntityQueryDto)
	 */
	@Override
	public MvrNrfontEntityResultDto selectByConditions(
			MvrNrfontEntityQueryDto mvrNrfontEntityQueryDto) {

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）开始...");

		// 输入参数不能为空
		if (mvrNrfontEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrNrfontEntityResultDto rtnDto = new MvrNrfontEntityResultDto();

		// 查询始发应收结果
		List<MvrNrfontEntity> mvrNrfontEntityList = mvrNrfontEntityDao
				.selectByConditions(mvrNrfontEntityQueryDto);

		// 如果始发应收结果数据存在
		if (mvrNrfontEntityList.size() > 0) {

			// 设置到返回Dto中
			rtnDto.setMvrNrfontEntityList(mvrNrfontEntityList);
		}

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）结束...");
		return rtnDto;
	}

	public IMvrNrfontEntityDao getMvrNrfontEntityDao() {
		return mvrNrfontEntityDao;
	}

	public void setMvrNrfontEntityDao(IMvrNrfontEntityDao mvrNrfontEntityDao) {
		this.mvrNrfontEntityDao = mvrNrfontEntityDao;
	}

}

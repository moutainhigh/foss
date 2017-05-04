package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrRfoEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfoEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityTotalDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 始发应收月报Service接口实现
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午4:28:49
 */
public class MvrRfoEntityService implements IMvrRfoEntityService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(MvrRfoEntityService.class);

	private IMvrRfoEntityDao mvrRfoEntityDao;

	/**
	 * 根据条件查询始发应收列表（分页）
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:59:45
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfoEntityService#selectByConditionsAndPage(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto,
	 *      int, int)
	 */
	@Override
	public MvrRfoEntityResultDto selectByConditionsAndPage(
			MvrRfoEntityQueryDto mvrRfoEntityQueryDto, int start, int limit) {

		logger.debug("根据条件查询始发应收列表（分页）开始...");

		// 输入参数不能为空
		if (mvrRfoEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrRfoEntityResultDto rtnDto = new MvrRfoEntityResultDto();

		// 查询统计信息结果
		MvrRfoEntityTotalDto mvrRfoEntityTotalDto = mvrRfoEntityDao
				.selectTotalByConditions(mvrRfoEntityQueryDto);
		// 如果统计结果存在，分页查询始发应收
		if (mvrRfoEntityTotalDto != null
				&& mvrRfoEntityTotalDto.getMvrRfoEntityTotalRows() > 0) {

			// 查询始发应收结果
			List<MvrRfoEntity> mvrRfoEntityList = mvrRfoEntityDao
					.selectByConditionsAndPage(mvrRfoEntityQueryDto, start,
							limit);

			// 如果始发应收结果数据存在
			if (mvrRfoEntityList.size() > 0) {
				
				// 生成始发应收统计信息实体
				MvrRfoEntity mvrRfoEntity = new MvrRfoEntity();

				// 将查询出的统计信息结果赋值到始发应收统计信息实体
				BeanUtils.copyProperties(mvrRfoEntityTotalDto, mvrRfoEntity);

				//加入统计信息
				mvrRfoEntityList.add(mvrRfoEntity);
				
				// 设置到返回Dto中
				rtnDto.setMvrRfoEntityList(mvrRfoEntityList);
			}

			// 将查询出的统计信息结果总条数设置到返回Dto中
			rtnDto.setMvrRfoEntityTotalRows(mvrRfoEntityTotalDto
					.getMvrRfoEntityTotalRows());
		}

		logger.debug("根据条件查询始发应收列表（分页）结束...");
		return rtnDto;
	}

	/**
	 * 根据条件查询始发应收列表（不分页，提供给导出使用）
	 * 
	 * @author foss-qiaolifeng
	 * @date 2013-3-6 下午4:29:49
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrRfoEntityService#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfoEntityQueryDto)
	 */
	@Override
	public MvrRfoEntityResultDto selectByConditions(
			MvrRfoEntityQueryDto mvrRfoEntityQueryDto) {

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）开始...");

		// 输入参数不能为空
		if (mvrRfoEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrRfoEntityResultDto rtnDto = new MvrRfoEntityResultDto();

		// 查询始发应收结果
		List<MvrRfoEntity> mvrRfoEntityList = mvrRfoEntityDao
				.selectByConditions(mvrRfoEntityQueryDto);

		// 如果始发应收结果数据存在
		if (mvrRfoEntityList.size() > 0) {

			// 设置到返回Dto中
			rtnDto.setMvrRfoEntityList(mvrRfoEntityList);
		}

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）结束...");
		return rtnDto;
	}

	public IMvrRfoEntityDao getMvrRfoEntityDao() {
		return mvrRfoEntityDao;
	}

	public void setMvrRfoEntityDao(IMvrRfoEntityDao mvrRfoEntityDao) {
		this.mvrRfoEntityDao = mvrRfoEntityDao;
	}

}

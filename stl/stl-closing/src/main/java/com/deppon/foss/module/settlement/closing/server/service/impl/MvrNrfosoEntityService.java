package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrNrfosoEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrNrfosoEntityService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfosoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfosoEntityTotalDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 01特殊业务始发月报表Service接口实现
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfosoEntityService implements IMvrNrfosoEntityService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(MvrNrfosoEntityService.class);

	private IMvrNrfosoEntityDao mvrNrfosoEntityDao;

	/**
	 * 根据条件查询始发应收列表（分页）
	 * 
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.ImvrNrfosoEntityService#selectByConditionsAndPage(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfosoEntityQueryDto,
	 *      int, int)
	 */
	@Override
	public MvrNrfosoEntityResultDto selectByConditionsAndPage(
			MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto, int start, int limit) {

		logger.debug("根据条件查询始发应收列表（分页）开始...");

		// 输入参数不能为空
		if (mvrNrfosoEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrNrfosoEntityResultDto rtnDto = new MvrNrfosoEntityResultDto();

		// 查询统计信息结果
		MvrNrfosoEntityTotalDto mvrNrfosoEntityTotalDto = mvrNrfosoEntityDao
				.selectTotalByConditions(mvrNrfosoEntityQueryDto);
		// 如果统计结果存在，分页查询始发应收
		if (mvrNrfosoEntityTotalDto != null
				&& mvrNrfosoEntityTotalDto.getMvrNrfosoEntityTotalRows() > 0) {

			// 查询始发应收结果
			List<MvrNrfosoEntity> mvrNrfosoEntityList = mvrNrfosoEntityDao
					.selectByConditionsAndPage(mvrNrfosoEntityQueryDto, start,
							limit);

			mvrNrfosoEntityTotalDto.setDestOrgName("合计：");
			// 如果始发应收结果数据存在
			if (mvrNrfosoEntityList.size() > 0) {
				
				// 生成始发应收统计信息实体
				MvrNrfosoEntity mvrNrfosoEntity = new MvrNrfosoEntity();

				// 将查询出的统计信息结果赋值到始发应收统计信息实体
				BeanUtils.copyProperties(mvrNrfosoEntityTotalDto, mvrNrfosoEntity);

				//加入统计信息
				mvrNrfosoEntityList.add(mvrNrfosoEntity);
				
				// 设置到返回Dto中
				rtnDto.setMvrNrfosoEntityList(mvrNrfosoEntityList);
			}

			// 将查询出的统计信息结果总条数设置到返回Dto中
			rtnDto.setMvrNrfosoEntityTotalRows(mvrNrfosoEntityTotalDto
					.getMvrNrfosoEntityTotalRows());
		}

		logger.debug("根据条件查询始发应收列表（分页）结束...");
		return rtnDto;
	}

	/**
	 * 根据条件查询始发应收列表（不分页，提供给导出使用）
	 * 
	 * @author ddw
	 * @date 2013-11-08
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.ImvrNrfosoEntityService#selectByConditions(com.deppon.foss.module.settlement.closing.api.shared.dto.mvrNrfosoEntityQueryDto)
	 */
	@Override
	public MvrNrfosoEntityResultDto selectByConditions(
			MvrNrfosoEntityQueryDto mvrNrfosoEntityQueryDto) {

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）开始...");

		// 输入参数不能为空
		if (mvrNrfosoEntityQueryDto == null) {
			throw new SettlementException("输入参数为空,根据条件查询始发应收列表失败!");
		}

		// 生成始发应收返回Dto
		MvrNrfosoEntityResultDto rtnDto = new MvrNrfosoEntityResultDto();

		// 查询始发应收结果
		List<MvrNrfosoEntity> mvrNrfosoEntityList = mvrNrfosoEntityDao
				.selectByConditions(mvrNrfosoEntityQueryDto);

		// 如果始发应收结果数据存在
		if (mvrNrfosoEntityList.size() > 0) {

			// 设置到返回Dto中
			rtnDto.setMvrNrfosoEntityList(mvrNrfosoEntityList);
		}

		logger.debug("根据条件查询始发应收列表（不分页，提供给导出使用）结束...");
		return rtnDto;
	}

	public IMvrNrfosoEntityDao getMvrNrfosoEntityDao() {
		return mvrNrfosoEntityDao;
	}

	public void setMvrNrfosoEntityDao(IMvrNrfosoEntityDao mvrNrfosoEntityDao) {
		this.mvrNrfosoEntityDao = mvrNrfosoEntityDao;
	}

}

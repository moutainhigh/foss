package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IGoodsAreaDensityDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IGoodsAreaDensityService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensity4SumDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensityQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;

public class GoodsAreaDensityService implements IGoodsAreaDensityService {

	private IGoodsAreaDensityDao goodsAreaDensityDao;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setGoodsAreaDensityDao(IGoodsAreaDensityDao goodsAreaDensityDao) {
		this.goodsAreaDensityDao = goodsAreaDensityDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 查询给定部门code所属外场
	 * @param code
	 * @return
	 * @date 2014-3-12
	 * @author Ouyang
	 */
	@Override
	public Map<String, String> queryParentTfrCtrCode(String code) {
		Map<String, String> result = null;

		// 调用综合接口判断当前部门是否外场或外场子部门
		List<String> bizTypesList = new ArrayList<String>();

		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);
		if (orgEntity != null) {
			result = new HashMap<String, String>();
			result.put("code", orgEntity.getCode());
			result.put("name", orgEntity.getName());
		}

		return result;
	}

	/**
	 * 货区密度查询参数验证
	 * @param dto
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	private void validate4Sum(GoodsAreaDensityQcDto dto) {

		if (dto == null || dto.getStatisticDate() == null) {
			throw new TfrBusinessException("请正确选择查询条件！");
		}
	}

	/**
	 * 货区密度查询参数处理
	 * @param dto
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	private void handler4Sum(GoodsAreaDensityQcDto dto) {

		Date statisticDate = dto.getStatisticDate();

		// 设置统计月份
		dto.setStatisticMonth(PlatformUtils.formatDate2String(statisticDate,
				"yyyy-MM"));
		dto.setBeginStatisticDate(PlatformUtils
				.getFirstDayOfMonth(statisticDate));
		dto.setEndStatisticDate(statisticDate);
	}

	/**
	 * 货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-12
	 * @author Ouyang
	 */
	@Override
	public List<GoodsAreaDensity4SumDto> selectGoodsAreaDensity4Sum(
			GoodsAreaDensityQcDto dto) {

		// 查询前参数验证
		validate4Sum(dto);

		// 参数处理 设置统计月份
		handler4Sum(dto);

		return goodsAreaDensityDao.selectGoodsAreaDensity4Sum(dto);
	}

	@Override
	public List<GoodsAreaDensity4SumDto> selectGoodsAreaDensity4Sum(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		// 查询前参数验证
		validate4Sum(dto);

		// 参数处理 设置统计月份
		handler4Sum(dto);

		return goodsAreaDensityDao
				.selectGoodsAreaDensity4Sum(dto, start, limit);
	}

	@Override
	public Long selectCntGoodsAreaDensity4Sum(GoodsAreaDensityQcDto dto) {
		// 参数处理 设置统计月份
		handler4Sum(dto);

		return goodsAreaDensityDao.selectCntGoodsAreaDensity4Sum(dto);
	}

	private String parseObj2Str(Object obj) {
		return (obj == null) ? null : obj.toString();
	}

	/**
	 * 货区密度导出
	 * @param qcDto
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportGoodsAreaDensity4Sum(GoodsAreaDensityQcDto dto) {

		try {
			// 参数校验
			validate4Sum(dto);
		} catch (TfrBusinessException e) {
			ExportResource exportResource = new ExportResource();
			List<List<String>> rowList = new ArrayList<List<String>>();
			exportResource.setRowList(rowList);
			return exportResource;
		}

		// 参数处理 设置统计月份
		handler4Sum(dto);

		// 查询满足条件的货区密度信息
		List<GoodsAreaDensity4SumDto> goodsAreaDensity4SumDtos = selectGoodsAreaDensity4Sum(dto);

		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> result = null;

		for (GoodsAreaDensity4SumDto item : goodsAreaDensity4SumDtos) {
			result = new ArrayList<String>();

			// 本部
			result.add(item.getHeadquartersName());
			// 事业部
			result.add(item.getBusinessUnitName());
			// 外场名称
			result.add(item.getTransferCenterName());
			// 日库区总容量
			result.add(parseObj2Str(item.getSumGoodsAreaVolume4Daily()));
			// 日库区总货量
			result.add(parseObj2Str(item.getSumGoodsVolume4Daily()));

			// 日库区平均密度
			BigDecimal avgDensity4Daily = item.getAvgDensity4Daily();
			result.add(avgDensity4Daily == null ? null : avgDensity4Daily
					.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100)).setScale(1,
							BigDecimal.ROUND_HALF_UP)
					+ "%");
			// 当月截至到查询日期总货量
			result.add(parseObj2Str(item.getSumGoodsVolume4Monthly()));
			// 当月截至到查询日期货区平均密度
			BigDecimal avgDensity4Monthly = item.getAvgDensity4Monthly();
			result.add(avgDensity4Monthly == null ? null : avgDensity4Monthly
					.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100)).setScale(1,
							BigDecimal.ROUND_HALF_UP)
					+ "%");
			// 日期
			result.add(String.format("%1$tF", dto.getStatisticDate()));

			resultList.add(result);
		}

		ExportResource sheet = new ExportResource();
		sheet.setHeads(PlatformConstants.EXCEL_TITLE_GAD_4SUM);
		sheet.setRowList(resultList);
		return sheet;
	}

	/**
	 * 各外场各货区整点密度查询参数校验
	 * @param dto
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	private void validate4Timely(GoodsAreaDensityQcDto dto) {

		if (dto == null) {
			throw new TfrBusinessException("请正确选择查询条件！");
		}

		// 外场code
		String transferCenterCode = dto.getTransferCenterCode();
		// 查询时间点
		String statisticTimePoint = dto.getStatisticTimePoint();
		// 查询开始日期
		Date beginStatisticDate = dto.getBeginStatisticDate();
		// 查询结束日期
		Date endStatisticDate = dto.getEndStatisticDate();

		if (StringUtils.isEmpty(transferCenterCode)
				|| StringUtils.isBlank(statisticTimePoint)
				|| beginStatisticDate == null || endStatisticDate == null
				|| beginStatisticDate.after(endStatisticDate)) {

			throw new TfrBusinessException("请正确选择查询条件！");
		}

		// 货区code是否为空
		boolean isGoodsAreaCodeNull = StringUtils.isEmpty(dto
				.getGoodsAreaCode());

		// 若货区code为空，则查询日期跨度为16天
		if (isGoodsAreaCodeNull
				&& PlatformUtils.is2DatesDifferN(beginStatisticDate,
						endStatisticDate, Calendar.DAY_OF_YEAR,
						PlatformConstants.QUERY_DATE_SPAN_HALF_MONTH)) {

			throw new TfrBusinessException("请选择货区，或将查询周期缩短至"
					+ PlatformConstants.QUERY_DATE_SPAN_HALF_MONTH + "天内！");
		}

		// 若货区code为空，则查询周期为1月
		if (!isGoodsAreaCodeNull
				&& PlatformUtils.is2DatesDifferN(beginStatisticDate,
						endStatisticDate, Calendar.DAY_OF_YEAR,
						PlatformConstants.QUERY_DATE_SPAN_1MONTH)) {

			throw new TfrBusinessException("请将查询周期缩短至"
					+ PlatformConstants.QUERY_DATE_SPAN_1MONTH + "天内！");
		}
	}

	/**
	 * 各外场各货区整点货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-12
	 * @author Ouyang
	 */
	@Override
	public List<GoodsAreaDensityEntity> selectGoodsAreaDensity4Timely(
			GoodsAreaDensityQcDto dto) {

		// 参数校验
		validate4Timely(dto);

		return goodsAreaDensityDao.selectGoodsAreaDensity4Timely(dto);
	}

	/**
	 * 各外场各货区整点货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-12
	 * @author Ouyang
	 */
	@Override
	public List<GoodsAreaDensityEntity> selectGoodsAreaDensity4Timely(
			GoodsAreaDensityQcDto dto, int start, int limit) {
		// 参数校验
		validate4Timely(dto);

		return goodsAreaDensityDao.selectGoodsAreaDensity4Timely(dto, start,
				limit);
	}

	@Override
	public Long selectCntGoodsAreaDensity4Timely(GoodsAreaDensityQcDto dto) {
		return goodsAreaDensityDao.selectCntGoodsAreaDensity4Timely(dto);
	}

	/**
	 * 各外场各货区整点货区密度导出
	 * @param qcDto
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportGoodsAreaDensity4Timely(
			GoodsAreaDensityQcDto dto) {
		try {
			// 参数校验
			validate4Timely(dto);
		} catch (TfrBusinessException e) {
			ExportResource exportResource = new ExportResource();
			List<List<String>> rowList = new ArrayList<List<String>>();
			exportResource.setRowList(rowList);
			return exportResource;
		}

		// 查询出各外场各货区整点货区密度
		List<GoodsAreaDensityEntity> goodsAreaDensityEntities = selectGoodsAreaDensity4Timely(dto);

		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> result = null;

		for (GoodsAreaDensityEntity item : goodsAreaDensityEntities) {

			result = new ArrayList<String>();

			// 外场
			result.add(item.getTransferCenterName());
			// 货区类型
			result.add(item.getGoodsAreaTypeName());
			// 货区名称
			result.add(item.getGoodsAreaName());
			// 日期
			result.add(String.format("%1$tF", item.getStatisticDate()));
			// 库区容量
			result.add(parseObj2Str(item.getGoodsAreaVolume()));
			// 库区货量
			result.add(parseObj2Str(item.getGoodsVolume()));
			// 库区密度
			BigDecimal density = item.getDensity();
			result.add(density == null ? null : density.multiply(
					new BigDecimal(PlatformConstants.SONAR_NUMBER_100)).setScale(1, BigDecimal.ROUND_HALF_UP)
					+ "%");
			// 整点数
			result.add(item.getStatisticTimePoint());

			resultList.add(result);
		}

		ExportResource sheet = new ExportResource();
		sheet.setHeads(PlatformConstants.EXCEL_TITLE_GAD_4TIMELY);
		sheet.setRowList(resultList);
		return sheet;
	}

	/**
	 * 各外场各货区各天平均货区密度查询参数校验
	 * @param dto
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	private void validateTcGaAvg4Daily(GoodsAreaDensityQcDto dto) {

		if (dto == null) {
			throw new TfrBusinessException("请正确选择查询条件！");
		}

		// 外场code
		String transferCenterCode = dto.getTransferCenterCode();
		// 查询开始日期
		Date beginStatisticDate = dto.getBeginStatisticDate();
		// 查询结束日期
		Date endStatisticDate = dto.getEndStatisticDate();

		if (StringUtils.isEmpty(transferCenterCode)
				|| beginStatisticDate == null || endStatisticDate == null
				|| beginStatisticDate.after(endStatisticDate)) {

			throw new TfrBusinessException("请正确选择查询条件！");
		}

		if (PlatformUtils.is2DatesDifferN(beginStatisticDate, endStatisticDate,
				Calendar.DAY_OF_YEAR, PlatformConstants.QUERY_DATE_SPAN_1MONTH)) {
			throw new TfrBusinessException("请选择货区，或将查询周期缩短至"
					+ PlatformConstants.QUERY_DATE_SPAN_1MONTH + "天内！");
		}
	}

	/**
	 * 各外场各货区各天平均货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto) {

		// 查询参数校验
		validateTcGaAvg4Daily(dto);

		return goodsAreaDensityDao.selectTfrCtrGoodsAreaAvgDensity4Daily(dto);
	}

	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		// 查询参数校验
		validateTcGaAvg4Daily(dto);

		return goodsAreaDensityDao.selectTfrCtrGoodsAreaAvgDensity4Daily(dto,
				start, limit);
	}

	@Override
	public Long selectCntTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto) {
		return goodsAreaDensityDao
				.selectCntTfrCtrGoodsAreaAvgDensity4Daily(dto);
	}

	/**
	 * 各外场各货区各天平均货区密度导出
	 * @param qcDto
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto) {

		try {
			// 查询参数校验
			validateTcGaAvg4Daily(dto);
		} catch (TfrBusinessException e) {
			ExportResource exportResource = new ExportResource();
			List<List<String>> rowList = new ArrayList<List<String>>();
			exportResource.setRowList(rowList);
			return exportResource;
		}

		// 查询出各外场各货区各天平均货区密度
		List<GoodsAreaDensityEntity> goodsAreaDensityEntities = selectTfrCtrGoodsAreaAvgDensity4Daily(dto);

		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> result = null;

		for (GoodsAreaDensityEntity item : goodsAreaDensityEntities) {

			result = new ArrayList<String>();

			// 外场
			result.add(item.getTransferCenterName());
			// 货区类型
			result.add(item.getGoodsAreaTypeName());
			// 货区名称
			result.add(item.getGoodsAreaName());
			// 库区总容量
			result.add(parseObj2Str(item.getGoodsAreaVolume()));
			// 库区总货量
			result.add(parseObj2Str(item.getGoodsVolume()));
			// 库区平均密度
			BigDecimal density = item.getDensity();
			result.add(density == null ? null : density.multiply(
					new BigDecimal(PlatformConstants.SONAR_NUMBER_100)).setScale(1, BigDecimal.ROUND_HALF_UP)
					+ "%");
			// 日期
			result.add(String.format("%1$tF", item.getStatisticDate()));

			resultList.add(result);
		}

		ExportResource sheet = new ExportResource();
		sheet.setHeads(PlatformConstants.EXCEL_TITLE_GAD_4DAILY);
		sheet.setRowList(resultList);
		return sheet;
	}

	/**
	 * 各外场各天平均货区密度查询参数校验
	 * @param dto
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	private void validateTcAvg4Daily(GoodsAreaDensityQcDto dto) {

		if (dto == null) {
			throw new TfrBusinessException("请正确选择查询条件！");
		}

		// 外场code
		String transferCenterCode = dto.getTransferCenterCode();
		// 查询开始日期
		Date beginStatisticDate = dto.getBeginStatisticDate();
		// 查询结束日期
		Date endStatisticDate = dto.getEndStatisticDate();

		if (StringUtils.isEmpty(transferCenterCode)
				|| beginStatisticDate == null || endStatisticDate == null
				|| beginStatisticDate.after(endStatisticDate)) {

			throw new TfrBusinessException("请正确选择查询条件！");
		}

		if (PlatformUtils.is2DatesDifferN(beginStatisticDate, endStatisticDate,
				Calendar.DAY_OF_YEAR, PlatformConstants.QUERY_DATE_SPAN_1MONTH)) {
			throw new TfrBusinessException("请选择货区，或将查询周期缩短至"
					+ PlatformConstants.QUERY_DATE_SPAN_1MONTH + "天内！");
		}
	}

	/**
	 * 各外场各天平均货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Daily(
			GoodsAreaDensityQcDto dto) {

		// 查询参数校验
		validateTcAvg4Daily(dto);

		return goodsAreaDensityDao.selectTfrCtrAvgDensity4Daily(dto);
	}

	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Daily(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		// 查询参数校验
		validateTcAvg4Daily(dto);

		return goodsAreaDensityDao.selectTfrCtrAvgDensity4Daily(dto, start,
				limit);
	}

	@Override
	public Long selectCntTfrCtrAvgDensity4Daily(GoodsAreaDensityQcDto dto) {
		return goodsAreaDensityDao.selectCntTfrCtrAvgDensity4Daily(dto);
	}

	/**
	 * 各外场各货区各月平均货区密度查询参数校验
	 * @param dto
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	private void validateTcGaAvg4Monthly(GoodsAreaDensityQcDto dto) {
		if (dto == null) {
			throw new TfrBusinessException("请正确选择查询条件！");
		}

		// 外场code
		String transferCenterCode = dto.getTransferCenterCode();
		// 查询开始月份
		String beginStatisticMonth = dto.getBeginStatisticMonth();
		// 查询结束月份
		String endStatisticMonth = dto.getEndStatisticMonth();

		if (StringUtils.isEmpty(transferCenterCode)
				|| StringUtils.isEmpty(beginStatisticMonth)
				|| StringUtils.isEmpty(endStatisticMonth)) {

			throw new TfrBusinessException("请正确选择查询条件！");
		}

		// 查询开始日期
		Date beginStatisticDate = PlatformUtils.parseString2Date(
				beginStatisticMonth, "yyyy-MM");
		// 查询结束日期
		Date endStatisticDate = PlatformUtils.parseString2Date(
				endStatisticMonth, "yyyy-MM");

		endStatisticDate = PlatformUtils.getLastDayOfMonth(endStatisticDate);

		if (beginStatisticDate.after(endStatisticDate)) {
			throw new TfrBusinessException("请正确选择查询日期！");
		}

		if (PlatformUtils.is2DatesDifferN(beginStatisticDate, endStatisticDate,
				Calendar.DAY_OF_YEAR, PlatformConstants.QUERY_DATE_SPAN_1YEAR)) {
			throw new TfrBusinessException("请将查询周期缩短至1年内！");
		}
	}

	/**
	 * 各外场各货区各月平均货区密度查询参数处理
	 * @param dto
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	private void handlerTcGaAvg4Monthly(GoodsAreaDensityQcDto dto) {

		// 查询开始月份
		String beginStatisticMonth = dto.getBeginStatisticMonth();
		// 查询结束月份
		String endStatisticMonth = dto.getEndStatisticMonth();

		// 查询开始日期
		Date beginStatisticDate = PlatformUtils.parseString2Date(
				beginStatisticMonth, "yyyy-MM");
		// 查询结束日期
		Date endStatisticDate = PlatformUtils.parseString2Date(
				endStatisticMonth, "yyyy-MM");

		endStatisticDate = PlatformUtils.getLastDayOfMonth(endStatisticDate);

		// 设置查询日期
		dto.setBeginStatisticDate(beginStatisticDate);
		dto.setEndStatisticDate(endStatisticDate);
	}

	/**
	 * 各外场各货区各月平均货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto) {

		// 查询参数校验
		validateTcGaAvg4Monthly(dto);
		// 参数处理，设置查询日期
		handlerTcGaAvg4Monthly(dto);

		return goodsAreaDensityDao.selectTfrCtrGoodsAreaAvgDensity4Monthly(dto);
	}

	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		// 查询参数校验
		validateTcGaAvg4Monthly(dto);
		// 参数处理，设置查询日期
		handlerTcGaAvg4Monthly(dto);

		return goodsAreaDensityDao.selectTfrCtrGoodsAreaAvgDensity4Monthly(dto,
				start, limit);
	}

	@Override
	public Long selectCntTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto) {

		// 参数处理，设置查询日期
		handlerTcGaAvg4Monthly(dto);

		return goodsAreaDensityDao
				.selectCntTfrCtrGoodsAreaAvgDensity4Monthly(dto);
	}

	/**
	 * 各外场各货区各月平均货区密度导出
	 * @param qcDto
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto) {

		try {
			// 查询参数校验
			validateTcGaAvg4Monthly(dto);
		} catch (TfrBusinessException e) {
			ExportResource exportResource = new ExportResource();
			List<List<String>> rowList = new ArrayList<List<String>>();
			exportResource.setRowList(rowList);
			return exportResource;
		}
		// 参数处理，设置查询月份
		handlerTcGaAvg4Monthly(dto);

		// 查询出各外场各货区各天平均货区密度
		List<GoodsAreaDensityEntity> goodsAreaDensityEntities = selectTfrCtrGoodsAreaAvgDensity4Monthly(dto);

		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> result = null;

		for (GoodsAreaDensityEntity item : goodsAreaDensityEntities) {

			result = new ArrayList<String>();

			// 外场
			result.add(item.getTransferCenterName());
			// 货区类型
			result.add(item.getGoodsAreaTypeName());
			// 货区名称
			result.add(item.getGoodsAreaName());
			// 月
			result.add(item.getStatisticMonth());
			// 库区总容量
			result.add(parseObj2Str(item.getGoodsAreaVolume()));
			// 库区总货量
			result.add(parseObj2Str(item.getGoodsVolume()));
			// 库区平均密度
			BigDecimal density = item.getDensity();
			result.add(density == null ? null : density.multiply(
					new BigDecimal(PlatformConstants.SONAR_NUMBER_100)).setScale(1, BigDecimal.ROUND_HALF_UP)
					+ "%");

			resultList.add(result);
		}

		ExportResource sheet = new ExportResource();
		sheet.setHeads(PlatformConstants.EXCEL_TITLE_GAD_4MONTHLY);
		sheet.setRowList(resultList);
		return sheet;
	}

	/**
	 * 各外场各月平均货区密度查询参数校验
	 * @param dto
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	private void validateTcAvg4Monthly(GoodsAreaDensityQcDto dto) {
		if (dto == null) {
			throw new TfrBusinessException("请正确选择查询条件！");
		}

		// 外场code
		String transferCenterCode = dto.getTransferCenterCode();

		// 查询开始月份
		String beginStatisticMonth = dto.getBeginStatisticMonth();
		// 查询结束月份
		String endStatisticMonth = dto.getEndStatisticMonth();

		if (StringUtils.isEmpty(transferCenterCode)
				|| StringUtils.isEmpty(beginStatisticMonth)
				|| StringUtils.isEmpty(endStatisticMonth)) {

			throw new TfrBusinessException("请正确选择查询条件！");
		}

		// 查询开始日期
		Date beginStatisticDate = PlatformUtils.parseString2Date(
				beginStatisticMonth, "yyyy-MM");
		// 查询结束日期
		Date endStatisticDate = PlatformUtils.parseString2Date(
				endStatisticMonth, "yyyy-MM");

		endStatisticDate = PlatformUtils.getLastDayOfMonth(endStatisticDate);

		if (beginStatisticDate.after(endStatisticDate)) {
			throw new TfrBusinessException("请正确选择查询日期！");
		}

		if (PlatformUtils.is2DatesDifferN(beginStatisticDate, endStatisticDate,
				Calendar.DAY_OF_YEAR, PlatformConstants.QUERY_DATE_SPAN_1YEAR)) {
			throw new TfrBusinessException("请将查询周期缩短至1年内！");
		}
	}

	/**
	 * 各外场各月平均货区密度查询参数处理
	 * @param dto
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	private void handlerTcAvg4Monthly(GoodsAreaDensityQcDto dto) {
		// 查询开始月份
		String beginStatisticMonth = dto.getBeginStatisticMonth();
		// 查询结束月份
		String endStatisticMonth = dto.getEndStatisticMonth();

		// 查询开始日期
		Date beginStatisticDate = PlatformUtils.parseString2Date(
				beginStatisticMonth, "yyyy-MM");
		// 查询结束日期
		Date endStatisticDate = PlatformUtils.parseString2Date(
				endStatisticMonth, "yyyy-MM");

		endStatisticDate = PlatformUtils.getLastDayOfMonth(endStatisticDate);

		// 设置查询日期
		dto.setBeginStatisticDate(beginStatisticDate);
		dto.setEndStatisticDate(endStatisticDate);
	}

	/**
	 * 各外场各月平均货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto) {

		// 参数校验
		validateTcAvg4Monthly(dto);
		// 参数处理
		handlerTcAvg4Monthly(dto);

		return goodsAreaDensityDao.selectTfrCtrAvgDensity4Monthly(dto);
	}

	@Override
	public List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto, int start, int limit) {

		// 参数校验
		validateTcAvg4Monthly(dto);
		// 参数处理
		handlerTcAvg4Monthly(dto);

		return goodsAreaDensityDao.selectTfrCtrAvgDensity4Monthly(dto, start,
				limit);
	}

	@Override
	public Long selectCntTfrCtrAvgDensity4Monthly(GoodsAreaDensityQcDto dto) {

		// 参数处理
		handlerTcAvg4Monthly(dto);

		return goodsAreaDensityDao.selectCntTfrCtrAvgDensity4Monthly(dto);
	}

	/**
	 * 生成整点货区密度，有定时任务整点调用
	 * @date 2014-3-17
	 * @author Ouyang
	 */
	@Override
	public void generateGoodsAreaDensity() {
		Date date = new Date();
		goodsAreaDensityDao.generateGoodsAreaDensity(date);
	}

}

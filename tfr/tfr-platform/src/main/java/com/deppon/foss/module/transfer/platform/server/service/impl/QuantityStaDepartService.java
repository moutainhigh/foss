package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQuantityStaDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQuantityStaDepartDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IQuantityStaDepartService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.QuantityStaConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.QuantityStaFcstEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaGoodsCondDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrCondDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrPerLineDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrTotalDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * T_OPT_STA_DEPART 出发实际货量表；
 * 
 * 每次在新增数据前， 清空T_OPT_STA_DEPART，再生成最新的出发货量实际货量，
 * 同时插入将最新数据插入T_OPT_STA_DEPART_16DAY；
 * 
 * T_OPT_STA_DEPART_16DAY 最近16天的实际货量表；因为预测货量需要用到最近16天的数据 ；
 * 
 * T_OPT_STA_DEPART_HIS 出发实际货量历史数据；
 * 起一个任务定时将T_OPT_STA_DEPART_16DAY中超过16天的数据迁移至历史表;
 * 
 * T_OPT_STA_DEPART_FCST 出发预测货量表；保留最近8天的数据，因为界面中的图表需要查询最近8天的数据 ；
 * 
 * T_OPT_STA_DEPART_FCST_HIS 出发预测货量历史数据；
 * 起一个任务定时将T_OPT_STA_DEPART_FCST中超过8天的数据迁移至历史表
 * 
 * T_OPT_STA_DEPART_2ND 第2天出发实际货量表；
 * 每次在新增数据前，将表里的数据转移删除，T_OPT_STA_DEPART_2ND只保留最新的数据；
 * 
 * T_OPT_STA_DEPART_2ND_FCST 第2天出发预测货量表；
 * 每次在新增数据前，将表里的数据删除，T_OPT_STA_DEPART_2ND_FCST只保留最新的数据；
 */
public class QuantityStaDepartService implements IQuantityStaDepartService {

	private static final Logger LOGGER = Logger
			.getLogger(QuantityStaDepartService.class);

	private IQuantityStaDao quantityStaDao;

	private IQuantityStaDepartDao quantityStaDepartDao;

	public void setQuantityStaDao(IQuantityStaDao quantityStaDao) {
		this.quantityStaDao = quantityStaDao;
	}

	public void setQuantityStaDepartDao(
			IQuantityStaDepartDao quantityStaDepartDao) {
		this.quantityStaDepartDao = quantityStaDepartDao;
	}

	/**
	 * @desc 完善各外场货量参数
	 * @param createTime货量创建时间
	 * @param is2ndDay是否是预测第2天的货量
	 * @param defaultConfigValue外场配置参数的默认值
	 * @param staGoodsCondDtos货量条件
	 * @date 2014年8月28日下午5:01:17
	 * @author Ouyang
	 */
	private boolean attachGoodsCond(Date createTime, boolean is2ndDay,
			String defaultConfigValue,
			List<QuantityStaGoodsCondDto> staGoodsCondDtos) {

		LOGGER.info("***** attachGoodsCond  begin ***** is2ndDay=" + is2ndDay
				+ ", defaultConfigValue=" + defaultConfigValue);

		// 统计时间点
		Integer staHh = Integer.valueOf(PlatformUtils.formatDate2String(
				createTime, "HH"));

		// 货量所属日期
		Date dataBelongDate = PlatformUtils.getFirstMomentOfDay(createTime);

		// 统计日期
		Date staDate = PlatformUtils.getFirstMomentOfDay(createTime);

		if (is2ndDay) {
			dataBelongDate = PlatformUtils.addDate(dataBelongDate,
					Calendar.DAY_OF_YEAR, 1);
		}

		String patternNoTime = "yyyyMMdd";

		String beginDateStrNoTime = PlatformUtils.formatDate2String(
				dataBelongDate, patternNoTime);
		String endDateStrNoTime = PlatformUtils.formatDate2String(
				PlatformUtils.addDate(dataBelongDate, Calendar.DAY_OF_YEAR, 1),
				patternNoTime);

		String pattern = "yyyyMMddHHmm";
		String beginDateStr = null;
		String endDateStr = null;

		// 外场对应配置参数的开始时间
		Date beginDate = null;
		// 外场对应配置参数的结束时间
		Date endDate = null;

		for (QuantityStaGoodsCondDto item : staGoodsCondDtos) {
			String configValue = item.getConfigValue();
			if (StringUtils.isEmpty(configValue)) {
				configValue = defaultConfigValue;
			}

			if (StringUtils.isEmpty(configValue)) {
				continue;
			}

			beginDateStr = beginDateStrNoTime + configValue;
			endDateStr = endDateStrNoTime + configValue;

			beginDate = PlatformUtils.parseString2Date(beginDateStr, pattern);
			endDate = PlatformUtils.parseString2Date(endDateStr, pattern);

			if (beginDate == null || endDate == null) {

				LOGGER.info("***** attachGoodsCond false end *****");

				return false;
			}

			// 完善参数
			item.setBeginDate(beginDate);
			item.setEndDate(endDate);
			item.setCreateTime(createTime);
			item.setDataBelongDate(dataBelongDate);
			item.setStaHh(staHh);
			item.setStaDate(staDate);
		}

		LOGGER.info("***** attachGoodsCond  true end *****");

		return true;
	}

	/**
	 * @desc 获取各外场货量预测的配置
	 * @param createTime
	 * @param is2ndDay是否是预测第2天的货量
	 * @param threadCount
	 *            线程数-1；如启动5个job，则threadCount为4
	 * @param threadNo
	 *            线程号；如启动5个job，则threadNo分别为0、1、2、3、4
	 * @return
	 * @date 2014年8月29日下午3:57:20
	 * @author Ouyang
	 */
	private List<QuantityStaGoodsCondDto> acquireGoodsCond(Date createTime,
			boolean is2ndDay, Integer threadCount, Integer threadNo) {

		LOGGER.info("***** acquireGoodsCond  begin ***** is2ndDay=" + is2ndDay
				+ ", threadCount=" + threadCount + ", threadNo=" + threadNo);

		Map<String, String> defaultMap = new HashMap<String, String>();

		defaultMap.put("type", DictionaryConstants.SYSTEM_CONFIG_PARM__TFR);
		defaultMap.put("code",
				ConfigurationParamsConstants.TFR_PARM__FORECAST_START);
		defaultMap.put("dip", QuantityStaConstants.DIP);

		// 获取全公司货量预测参数的默认配置
		String defaultConfigValue = quantityStaDao
				.queryDefaultForecastStartConfigValue(defaultMap);

		Map<String, Object> specialMap = new HashMap<String, Object>();

		specialMap.put("type", DictionaryConstants.SYSTEM_CONFIG_PARM__TFR);
		specialMap.put("code",
				ConfigurationParamsConstants.TFR_PARM__FORECAST_START);
		specialMap.put("threadCount", threadCount);
		specialMap.put("threadNo", threadNo);

		// 获取外场货量预测参数的特别配置
		List<QuantityStaGoodsCondDto> staGoodsCondDtos = quantityStaDao
				.querySpecialForecastStartConfigValue(specialMap);

		// 完善各外场货量参数
		boolean flag = attachGoodsCond(createTime, is2ndDay,
				defaultConfigValue, staGoodsCondDtos);
		if (!flag) {
			LOGGER.info("***** acquireGoodsCond flag false  end *****");

			return new ArrayList<QuantityStaGoodsCondDto>();
		}

		LOGGER.info("***** acquireGoodsCond flag true end *****");

		return staGoodsCondDtos;
	}

	/**
	 * @desc 生成出发预测货量
	 * @param staGoodsCondDtos
	 * @date 2014年8月31日上午11:14:08
	 * @author Ouyang
	 */
	private void generateDepartFcst(QuantityStaGoodsCondDto item) {

		LOGGER.info("***** generateDepartFcst begin *****");

		// 外场
		String transferCenterCode = item.getTransferCenterCode();
		// 统计日期
		Date staDate = item.getStaDate();
		// 当前时间点
		Integer staHh = item.getStaHh();
		// 统计日期的上一周
		Date staDateLastWeek = PlatformUtils.addDate(staDate,
				Calendar.DAY_OF_MONTH, -PlatformConstants.SONAR_NUMBER_7);

		// 查询当前外场各货量类型的总货量
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transferCenterCode", transferCenterCode);
		params.put("staDate", staDate);
		params.put("staHh", staHh);
		List<QuantityStaTfrCtrTotalDto> tfrCtrTotals = quantityStaDepartDao
				.selectTfrCtrTotal(params);

		if (CollectionUtils.isEmpty(tfrCtrTotals)) {
			LOGGER.info("***** generateDepartFcst end tfrCtrTotals is empty***** transferCenterCode="
					+ transferCenterCode
					+ ", staDate="
					+ staDate
					+ ", staHh="
					+ staHh);
			return;
		}

		// 查询上周当天外场最后一次的统计时间点
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transferCenterCode", transferCenterCode);
		map.put("staDate", staDateLastWeek);
		Integer staHhLastWeekLastPoint = quantityStaDepartDao
				.selectLastStaHh16Day(map);
		if (staHhLastWeekLastPoint == null) {
			return;
		}

		// 外场生成出发预测货量
		generateFcstPerTfrCtr(item, tfrCtrTotals, staHhLastWeekLastPoint);

		LOGGER.info("***** generateDepartFcst end *****");
	}

	/**
	 * @desc 每个外场分别生成出发预测货量
	 * @param tfrCtrInfo外场信息
	 * @param tfrCtrTotals当前外场各货量类型的总货量集合
	 * @param staHhLastWeekLastPoint上周当天外场最后一次的统计时间点
	 * @date 2014年9月1日上午10:15:09
	 * @author Ouyang
	 */
	private void generateFcstPerTfrCtr(QuantityStaGoodsCondDto tfrCtrInfo,
			List<QuantityStaTfrCtrTotalDto> tfrCtrTotals,
			Integer staHhLastWeekLastPoint) {

		LOGGER.info("***** generateFcstPerTfrCtr begin *****");

		// 外场
		String transferCenterCode = tfrCtrInfo.getTransferCenterCode();
		// 统计日期
		Date staDate = tfrCtrInfo.getStaDate();
		// 当前统计时间点
		Integer staHh = tfrCtrInfo.getStaHh();
		// 统计日期的上一周
		Date staDateLastWeek = PlatformUtils.addDate(staDate,
				Calendar.DAY_OF_MONTH, -PlatformConstants.SONAR_NUMBER_7);

		// 循环外场的出发货量类型，对每中类型分别生成出发预测货量
		for (QuantityStaTfrCtrTotalDto tfrCtrTotal : tfrCtrTotals) {
			// 货量类型
			String dataType = tfrCtrTotal.getDataType();

			// 上周当前统计时间点外场的总货量的查询参数
			QuantityStaTfrCtrCondDto lastWeekTotalCondDto = new QuantityStaTfrCtrCondDto();
			lastWeekTotalCondDto.setTransferCenterCode(transferCenterCode);
			lastWeekTotalCondDto.setStaDate(staDateLastWeek);
			lastWeekTotalCondDto.setStaHh(staHh);
			lastWeekTotalCondDto.setDataType(dataType);

			// 查询上周当前统计时间点某货量类型的外场总货量
			QuantityStaTfrCtrTotalDto lastWeekTotal = quantityStaDepartDao
					.selectTfrCtrTotal16Day(lastWeekTotalCondDto);

			if (lastWeekTotal == null) {
				continue;
			}

			// 查询现在外场某货量类型每条线的货量
			Map<String, Object> perLineMap = new HashMap<String, Object>();
			perLineMap.put("transferCenterCode", transferCenterCode);
			perLineMap.put("dataType", dataType);
			perLineMap.put("staDate", staDate);
			perLineMap.put("staHh", staHh);
			List<QuantityStaTfrCtrPerLineDto> tfrCtrPerLine = quantityStaDepartDao
					.selectTfrCtrPerLine(perLineMap);

			generateFcstPerType(tfrCtrInfo, tfrCtrTotal, lastWeekTotal,
					tfrCtrPerLine, staHhLastWeekLastPoint);

			LOGGER.info("***** generateFcstPerTfrCtr end *****");
		}
	}

	/**
	 * @desc 外场每个货量类型分别生成出发预测货量
	 * @param tfrCtrInfo外场信息
	 * @param tfrCtrTotal当前某种货量类型的总货量
	 * @param lastWeekTotal上周当前时间点同种类型的总货量
	 * @param tfrCtrPerLine当前同种类型的各条线的货量集合
	 * @param staHhLastWeekLastPoint上周当天外场最后一次的统计时间点
	 * @date 2014年9月1日上午10:29:52
	 * @author Ouyang
	 */
	private void generateFcstPerType(QuantityStaGoodsCondDto tfrCtrInfo,
			QuantityStaTfrCtrTotalDto tfrCtrTotal,
			QuantityStaTfrCtrTotalDto lastWeekTotal,
			List<QuantityStaTfrCtrPerLineDto> tfrCtrPerLine,
			Integer staHhLastWeekLastPoint) {

		LOGGER.info("***** generateFcstPerType begin *****");

		// 创建时间
		Date createTime = tfrCtrInfo.getCreateTime();
		// 货量所属日期
		Date dataBelongDate = tfrCtrInfo.getDataBelongDate();
		// 外场
		String transferCenterCode = tfrCtrInfo.getTransferCenterCode();
		// 统计日期
		Date staDate = tfrCtrInfo.getStaDate();
		// 当前统计时间点
		Integer staHh = tfrCtrInfo.getStaHh();
		// 统计日期的上一周
		Date staDateLastWeek = PlatformUtils.addDate(staDate,
				Calendar.DAY_OF_MONTH, -PlatformConstants.SONAR_NUMBER_7);
		// 货量类型
		String dataType = tfrCtrTotal.getDataType();

		// 当前总重量、体积、票数
		BigDecimal weightTotal = tfrCtrTotal.getWeightTotal();
		BigDecimal volumeTotal = tfrCtrTotal.getVolumeTotal();
		Integer qtyTotal = tfrCtrTotal.getQtyTotal();

		// 上周当前统计时间点总重量、体积、票数
		BigDecimal weightLastWeekTotal = lastWeekTotal.getWeightTotal();
		BigDecimal volumeLastWeekTotal = lastWeekTotal.getVolumeTotal();
		Integer qtyLastWeekTotal = lastWeekTotal.getQtyTotal();

		// 循环每条线，查询上周当天最后统计时间点的各条线货量
		for (QuantityStaTfrCtrPerLineDto index : tfrCtrPerLine) {

			// 当前外场每天先的货量
			BigDecimal weightPerLine = index.getWeightTotal();
			BigDecimal volumePerLine = index.getVolumeTotal();
			Integer qtyPerLine = index.getQtyTotal();

			// 线路的相关部门
			String relevantOrgCode = index.getRelevantOrgCode();

			// 上周当天最后统计时间点的各条线货量的查询条件
			QuantityStaTfrCtrCondDto lastPointPerLineCondDto = new QuantityStaTfrCtrCondDto();
			lastPointPerLineCondDto.setTransferCenterCode(transferCenterCode);
			lastPointPerLineCondDto.setRelevantOrgCode(relevantOrgCode);
			lastPointPerLineCondDto.setStaDate(staDateLastWeek);
			lastPointPerLineCondDto.setStaHh(staHhLastWeekLastPoint);
			lastPointPerLineCondDto.setDataType(dataType);

			// 上周当天最后统计时间点某类型的各条线货量
			QuantityStaTfrCtrPerLineDto lastPointTfrCtrPerLine = quantityStaDepartDao
					.selectTfrCtrPerLine16Day(lastPointPerLineCondDto);

			if (lastPointTfrCtrPerLine == null) {
				continue;
			}

			// 上周当天最后一统计时间点某类型各条线货量
			BigDecimal weightLastPointPerLine = lastPointTfrCtrPerLine
					.getWeightTotal();
			BigDecimal volumeLastPointPerLine = lastPointTfrCtrPerLine
					.getVolumeTotal();
			Integer qtyLastPointPerLine = lastPointTfrCtrPerLine.getQtyTotal();

			// 当日各线路货量预测值=当前线路总货量*上周最后一统计时间点各线路货量/上周当前时间点各线路货量
			BigDecimal weightFcst = weightLastWeekTotal
					.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
					: weightTotal.multiply(weightLastPointPerLine).divide(
							weightLastWeekTotal, PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
			BigDecimal volumeFcst = volumeLastWeekTotal
					.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
					: volumeTotal.multiply(volumeLastPointPerLine).divide(
							volumeLastWeekTotal, PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);

			Integer qtyFcst = qtyLastWeekTotal == 0 ? 0 : qtyTotal
					* qtyLastPointPerLine / qtyLastWeekTotal;

			// 当日未开单货量预测值
			BigDecimal weightFcstNoBilling = weightFcst.subtract(weightPerLine)
					.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO
					: weightFcst.subtract(weightPerLine);

			BigDecimal volumeFcstNoBilling = volumeFcst.subtract(volumePerLine)
					.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO
					: volumeFcst.subtract(volumePerLine);

			Integer qtyFcstNoBilling = qtyFcst - qtyPerLine < 0 ? 0 : qtyFcst
					- qtyPerLine;

			// 构造货量预测信息
			QuantityStaFcstEntity fcstEntity = new QuantityStaFcstEntity();
			fcstEntity.setId(UUIDUtils.getUUID());

			fcstEntity.setOrgCode(transferCenterCode);
			fcstEntity.setRelevantOrgCode(relevantOrgCode);
			fcstEntity.setDataType(dataType);
			fcstEntity.setCreateTime(createTime);
			fcstEntity.setStaDate(staDate);
			fcstEntity.setStaHh(staHh);
			fcstEntity.setDataBelongDate(dataBelongDate);

			fcstEntity.setWeightTotal(weightFcst);
			fcstEntity.setWeightNoBilling(weightFcstNoBilling);
			fcstEntity.setVolumeTotal(volumeFcst);
			fcstEntity.setVolumeNoBilling(volumeFcstNoBilling);
			fcstEntity.setQtyTotal(qtyFcst);
			fcstEntity.setQtyNoBilling(qtyFcstNoBilling);

			// 保存货量预测信息
			quantityStaDepartDao.insertFcst(fcstEntity);

			LOGGER.info("***** generateFcstPerType end *****");
		}
	}

	/**
	 * @desc 生成当天的出发货量
	 * @date 2014年8月28日下午5:38:06
	 * @author Ouyang
	 */
	@Override
	public void generateDepart(Date createTime, Integer threadCount,
			Integer threadNo) {

		LOGGER.info("***** generateDepart begin ***** threadCount="
				+ threadCount + ", threadNo=" + threadNo);

		List<QuantityStaGoodsCondDto> staGoodsCondDtos = acquireGoodsCond(
				createTime, false, threadCount, threadNo);

		for (QuantityStaGoodsCondDto item : staGoodsCondDtos) {
			// 将出发货量表的原有数据清空
			quantityStaDepartDao.deleteDepart(item);
			// 生成出发实际货量
			quantityStaDepartDao.proInsertActual(item);
			// 再将最新的出发货量数据复制到16天数据的表
			quantityStaDepartDao.insert16Day(item);

			// 生成出发预测货量
			generateDepartFcst(item);
		}

		LOGGER.info("***** generateDepart end *****");
	}

	/**
	 * @desc 生成第2天的出发货量
	 * @date 2014年8月28日下午5:38:19
	 * @author Ouyang
	 */
	@Override
	public void generateDepart2ndDay(Date createTime, Integer threadCount,
			Integer threadNo) {

		LOGGER.info("***** generateDepart2ndDay begin *****");

		List<QuantityStaGoodsCondDto> staGoodsCondDtos = acquireGoodsCond(
				createTime, true, threadCount, threadNo);
		for (QuantityStaGoodsCondDto dto : staGoodsCondDtos) {
			// 将第2天 上一个时间点的出发实际货量删除
			quantityStaDepartDao.delete2ndDayDepart(dto);
			// 生成第2天的出发实际货量
			quantityStaDepartDao.proInsert2ndDayActual(dto);

			// 将第2天 上一个时间点的出发预测货量删除
			quantityStaDepartDao.delete2ndDayDepartFcst(dto);
			// 生成第2天的出发预测货量
			generate2ndDayDepartFcst(dto);
		}

		LOGGER.info("***** generateDepart2ndDay end *****");
	}

	/**
	 * @desc 生成第2天的出发预测货量
	 * @param dto
	 * @date 2014年9月1日下午5:23:56
	 * @author Ouyang
	 */
	private void generate2ndDayDepartFcst(QuantityStaGoodsCondDto dto) {

		LOGGER.info("***** generate2ndDayDepartFcst begin *****");

		// 外场
		String transferCenterCode = dto.getTransferCenterCode();
		// 当前统计日期
		Date staDate = dto.getStaDate();

		// 1、6、8、13、15天前日期
		Date oneDayAgo = PlatformUtils.addDate(staDate, Calendar.DAY_OF_YEAR,
				-1);
		Date sixDayAgo = PlatformUtils.addDate(staDate, Calendar.DAY_OF_YEAR,
				-PlatformConstants.SONAR_NUMBER_6);
		Date eightDayAgo = PlatformUtils.addDate(staDate, Calendar.DAY_OF_YEAR,
				-PlatformConstants.SONAR_NUMBER_8);
		Date thirteenDayAgo = PlatformUtils.addDate(staDate,
				Calendar.DAY_OF_YEAR, -PlatformConstants.SONAR_NUMBER_13);
		Date fifteenDayAgo = PlatformUtils.addDate(staDate,
				Calendar.DAY_OF_YEAR, -PlatformConstants.SONAR_NUMBER_15);

		// 构造一个统计日期集合，存放预测货量需要用的统计日期
		List<Date> staDates = new ArrayList<Date>();
		staDates.add(oneDayAgo);
		staDates.add(sixDayAgo);
		staDates.add(eightDayAgo);
		staDates.add(thirteenDayAgo);
		staDates.add(fifteenDayAgo);

		// 统计时间点集合，存放各统计日期的最后统计时间点
		List<Integer> staHhs = new ArrayList<Integer>();

		// 查询最后统计时间点的参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transferCenterCode", transferCenterCode);

		Date nDayAgo = null;
		for (int i = 0, j = staDates.size(); i < j; i++) {
			nDayAgo = staDates.get(i);
			map.put("staDate", nDayAgo);
			// 查询外场，再各统计日期的最后统计时间点
			Integer lastStaHhDaysAgo = quantityStaDepartDao
					.selectLastStaHh16Day(map);

			if (lastStaHhDaysAgo == null) {
				return;
			}

			staHhs.add(lastStaHhDaysAgo);
		}

		// 出发货量类型集合
		String[] dataTypes = {
				QuantityStaConstants.STA_TYPE_DEPARTURE_LOCAL_DEPARTURE,
				QuantityStaConstants.STA_TYPE_DEPARTURE_ARRIVAL_TRANSFER,
				QuantityStaConstants.STA_TYPE_DEPARTURE_DELIVER };

		// 生成第2天的出发预测货量
		generate2ndDayFcstAllType(dto, staDates, staHhs, dataTypes);

		LOGGER.info("***** generate2ndDayDepartFcst end *****");
	}

	/**
	 * @desc 生成第2天的出发预测货量
	 * @param transferCenterCode外场
	 * @param staDates前1
	 *            、6、8、13、15天日期集合
	 * @param staHhs前1
	 *            、6、8、13、15天日期对应的最后统计时间点集合
	 * @param dataTypes出发货量类型集合
	 * @date 2014年9月2日上午10:52:42
	 * @author Ouyang
	 */
	private void generate2ndDayFcstAllType(QuantityStaGoodsCondDto dto,
			List<Date> staDates, List<Integer> staHhs, String[] dataTypes) {

		LOGGER.info("***** generate2ndDayFcstAllType begin *****");

		String transferCenterCode = dto.getTransferCenterCode();
		Date staDate = dto.getStaDate();
		Integer staHh = dto.getStaHh();

		for (String dataType : dataTypes) {
			// 查询当前每条线的货量
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("transferCenterCode", transferCenterCode);
			map.put("dataType", dataType);
			map.put("staDate", staDate);
			map.put("staHh", staHh);
			List<QuantityStaTfrCtrPerLineDto> tfrCtrPerLine = quantityStaDepartDao
					.select2ndDayTfrCtrPerLine(map);

			if (CollectionUtils.isEmpty(tfrCtrPerLine)) {
				continue;
			}

			// 外场某天最后一统计时间的总货量查询参数
			QuantityStaTfrCtrCondDto param = new QuantityStaTfrCtrCondDto();
			param.setTransferCenterCode(transferCenterCode);
			param.setDataType(dataType);

			// 用与存放 1、6、8、13、15天前外场的总货量
			List<QuantityStaTfrCtrTotalDto> totalDaysAgos = new ArrayList<QuantityStaTfrCtrTotalDto>();

			for (int i = 0, j = staDates.size(); i < j; i++) {
				Date staDateDaysAgo = staDates.get(i);
				Integer staHhDaysAgo = staHhs.get(i);

				param.setStaDate(staDateDaysAgo);
				param.setStaHh(staHhDaysAgo);

				// 查询外场某天最后一统计时间的总货量
				QuantityStaTfrCtrTotalDto info = quantityStaDepartDao
						.selectTfrCtrTotal16Day(param);

				if (info == null) {
					break;
				}

				totalDaysAgos.add(info);
			}

			if (CollectionUtils.isEmpty(totalDaysAgos)
					|| totalDaysAgos.size() < PlatformConstants.SONAR_NUMBER_5) {
				continue;
			}

			generate2ndDayFcstPerType(dto, tfrCtrPerLine, staDates.get(1),
					staHhs.get(1), dataType, totalDaysAgos);
		}

		LOGGER.info("***** generate2ndDayFcstAllType end *****");
	}

	/**
	 * @desc
	 * @param tfrCtrPerLine当前外场某类型各条线的货量
	 * @param staHh6DayAgo6天前最后统计时间
	 * @param dataType出发货量类型
	 * @param totalDaysAgos外场前1
	 *            、6、8、13、15天总货量
	 * @date 2014年9月2日下午3:08:16
	 * @author Ouyang
	 */
	private void generate2ndDayFcstPerType(QuantityStaGoodsCondDto dto,
			List<QuantityStaTfrCtrPerLineDto> tfrCtrPerLine,
			Date staDate6DayAgo, Integer staHh6DayAgo, String dataType,
			List<QuantityStaTfrCtrTotalDto> totalDaysAgos) {

		LOGGER.info("***** generate2ndDayFcstPerType begin *****");

		Date createTime = dto.getCreateTime();
		Date staDate = dto.getStaDate();
		Date dataBelongDate = dto.getDataBelongDate();
		Integer staHh = dto.getStaHh();

		// 1、6、8、13、15天前外场的总货量
		QuantityStaTfrCtrTotalDto total1DayAgo = totalDaysAgos.get(0);
		QuantityStaTfrCtrTotalDto total6DayAgo = totalDaysAgos.get(1);
		QuantityStaTfrCtrTotalDto total8DayAgo = totalDaysAgos.get(2);
		QuantityStaTfrCtrTotalDto total13DayAgo = totalDaysAgos.get(PlatformConstants.SONAR_NUMBER_3);
		QuantityStaTfrCtrTotalDto total15DayAgo = totalDaysAgos.get(PlatformConstants.SONAR_NUMBER_4);

		// 1、6、8、13、15天前外场的总重量、体积、票数
		BigDecimal weightTotal1DayAgo = total1DayAgo.getWeightTotal();
		BigDecimal volumeTotal1DayAgo = total1DayAgo.getVolumeTotal();
		Integer qtyTotal1DayAgo = total1DayAgo.getQtyTotal();

		BigDecimal weightTotal6DayAgo = total6DayAgo.getWeightTotal();
		BigDecimal volumeTotal6DayAgo = total6DayAgo.getVolumeTotal();
		Integer qtyTotal6DayAgo = total6DayAgo.getQtyTotal();

		BigDecimal weightTotal8DayAgo = total8DayAgo.getWeightTotal();
		BigDecimal volumeTotal8DayAgo = total8DayAgo.getVolumeTotal();
		Integer qtyTotal8DayAgo = total8DayAgo.getQtyTotal();

		BigDecimal weightTotal13DayAgo = total13DayAgo.getWeightTotal();
		BigDecimal volumeTotal13DayAgo = total13DayAgo.getVolumeTotal();
		Integer qtyTotal13DayAgo = total13DayAgo.getQtyTotal();

		BigDecimal weightTotal15DayAgo = total15DayAgo.getWeightTotal();
		BigDecimal volumeTotal15DayAgo = total15DayAgo.getVolumeTotal();
		Integer qtyTotal15DayAgo = total15DayAgo.getQtyTotal();

		// x=1*(6+13)/(8+15)
		BigDecimal weightX = weightTotal8DayAgo.add(weightTotal15DayAgo)
				.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
				: weightTotal1DayAgo.multiply(
						weightTotal6DayAgo.add(weightTotal13DayAgo)).divide(
						weightTotal8DayAgo.add(weightTotal15DayAgo), PlatformConstants.SONAR_NUMBER_3,
						BigDecimal.ROUND_HALF_UP);

		BigDecimal volumeX = volumeTotal8DayAgo.add(volumeTotal15DayAgo)
				.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
				: volumeTotal1DayAgo.multiply(
						volumeTotal6DayAgo.add(volumeTotal13DayAgo)).divide(
						volumeTotal8DayAgo.add(volumeTotal15DayAgo), PlatformConstants.SONAR_NUMBER_3,
						BigDecimal.ROUND_HALF_UP);

		Integer qtyX = qtyTotal8DayAgo + qtyTotal15DayAgo == 0 ? 0
				: qtyTotal1DayAgo * (qtyTotal6DayAgo + qtyTotal13DayAgo)
						/ (qtyTotal8DayAgo + qtyTotal15DayAgo);

		for (QuantityStaTfrCtrPerLineDto index : tfrCtrPerLine) {
			// 当前外场每条线的货量
			BigDecimal weightPerLine = index.getWeightTotal();
			BigDecimal volumePerLine = index.getVolumeTotal();
			Integer qtyPerLine = index.getQtyTotal();

			String transferCenterCode = index.getTransferCenterCode();
			String relevantOrgCode = index.getRelevantOrgCode();

			// 查询6天前某条线路货量
			QuantityStaTfrCtrCondDto sixDayAgoCondDto = new QuantityStaTfrCtrCondDto();
			sixDayAgoCondDto.setTransferCenterCode(transferCenterCode);
			sixDayAgoCondDto.setRelevantOrgCode(relevantOrgCode);
			sixDayAgoCondDto.setStaDate(staDate6DayAgo);
			sixDayAgoCondDto.setStaHh(staHh6DayAgo);
			sixDayAgoCondDto.setDataType(dataType);
			QuantityStaTfrCtrPerLineDto tfrCtr1Line6DayAgo = quantityStaDepartDao
					.selectTfrCtrPerLine16Day(sixDayAgoCondDto);

			if (tfrCtr1Line6DayAgo == null) {
				continue;
			}

			// 6天前某条线的重量、体积、票数
			BigDecimal weight1Line6DayAgo = tfrCtr1Line6DayAgo.getWeightTotal();
			BigDecimal volume1Line6DayAgo = tfrCtr1Line6DayAgo.getVolumeTotal();
			Integer qty1Line6DayAgo = tfrCtr1Line6DayAgo.getQtyTotal();

			// 计算预测货量
			BigDecimal weightFcst = weightTotal6DayAgo
					.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
					: weightX.multiply(weight1Line6DayAgo).divide(
							weightTotal6DayAgo, PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
			BigDecimal volumeFcst = volumeTotal6DayAgo
					.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
					: volumeX.multiply(volume1Line6DayAgo).divide(
							volumeTotal6DayAgo, PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
			Integer qtyFcst = qtyTotal6DayAgo == 0 ? 0 : qtyX * qty1Line6DayAgo
					/ qtyTotal6DayAgo;

			// 预测未开单货量
			BigDecimal weightFcstNoBilling = weightFcst.subtract(weightPerLine)
					.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO
					: weightFcst.subtract(weightPerLine);
			BigDecimal volumeFcstNoBilling = volumeFcst.subtract(volumePerLine)
					.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO
					: volumeFcst.subtract(volumePerLine);
			Integer qtyFcstNoBilling = qtyFcst - qtyPerLine < 0 ? 0 : qtyFcst
					- qtyPerLine;

			// 构造货量预测信息
			QuantityStaFcstEntity fcstEntity = new QuantityStaFcstEntity();
			fcstEntity.setId(UUIDUtils.getUUID());

			fcstEntity.setOrgCode(transferCenterCode);
			fcstEntity.setRelevantOrgCode(relevantOrgCode);
			fcstEntity.setDataType(dataType);
			fcstEntity.setCreateTime(createTime);
			fcstEntity.setStaDate(staDate);
			fcstEntity.setStaHh(staHh);
			fcstEntity.setDataBelongDate(dataBelongDate);

			fcstEntity.setWeightTotal(weightFcst);
			fcstEntity.setWeightNoBilling(weightFcstNoBilling);
			fcstEntity.setVolumeTotal(volumeFcst);
			fcstEntity.setVolumeNoBilling(volumeFcstNoBilling);
			fcstEntity.setQtyTotal(qtyFcst);
			fcstEntity.setQtyNoBilling(qtyFcstNoBilling);

			// 保存货量预测信息
			quantityStaDepartDao.insert2ndDayFcst(fcstEntity);
		}

		LOGGER.info("***** generate2ndDayFcstPerType end *****");
	}

	/**
	 * @desc 将T_OPT_STA_DEPART_16DAY中超过16天的数据迁移至T_OPT_STA_DEPART_HIS
	 * @date 2014年9月3日 下午3:41:38
	 * @author Ouyang
	 */
	@Override
	public void migrate16Day2His() {
		Date date = PlatformUtils.addDate(
				PlatformUtils.getFirstMomentOfDay(new Date()),
				Calendar.DAY_OF_YEAR, -PlatformConstants.SONAR_NUMBER_16);
		// 将T_OPT_STA_DEPART_16DAY中超过16天的数据复制至T_OPT_STA_DEPART_HIS
		quantityStaDepartDao.insertHis(date);
		// 将T_OPT_STA_DEPART_16DAY中超过16天的数据删除
		quantityStaDepartDao.delete16Day(date);
	}

	/**
	 * @desc 将T_OPT_STA_DEPART_FCST中超过8天的数据迁移至T_OPT_STA_DEPART_FCST_HIS
	 * @date 2014年9月3日 下午3:42:32
	 * @author Ouyang
	 */
	@Override
	public void migrateFcst2His() {
		Date date = PlatformUtils.addDate(
				PlatformUtils.getFirstMomentOfDay(new Date()),
				Calendar.DAY_OF_YEAR, -PlatformConstants.SONAR_NUMBER_8);
		// 将T_OPT_STA_DEPART_FCST中超过8天的数据复制至T_OPT_STA_DEPART_FCST_HIS
		quantityStaDepartDao.insertFcstHis(date);
		// 将T_OPT_STA_DEPART_FCST中超过8天的数据删除
		quantityStaDepartDao.deleteFcst(date);
	}

}

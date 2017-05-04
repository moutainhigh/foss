package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.transfer.platform.api.server.dao.ICargoFcstDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ICargoFcstService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CargoDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CargoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstResultDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 外场某天某条线路货量预测：某天某条线的预测总操作货量 = (x-2) * ((x-7)+(x-14)) / ((x-9)+(x-16)) x-n
 * 表示前n天的某天某条线的实际总操作货量。
 * 
 * R1、若参数 x-2为节假日，则参数往前推一星期，则为x-9,若x-9为节假日，则再往前推为x-16，以此类推;
 * R2、若参数x-7或参数x-9为节假日，则((x-7)+(x-14)) / ((x-9)+(x-16))四参数同时往前推一星期，以此类推；
 * R3、若参数x-14或参数x-16为节假日，则((x-14)+(x-16))两参数同事往前推一星期，以此类推；
 * PS:R1只关心1参数,R2关心4个参数,R3关心2参数
 * 
 * 外场某天某条线实际总操作货量：外场某天某条线到达货量+外场某天某条线出发操作货量 外场某天某条线到达货量 =
 * (外场某天某条线)长途到达+短途到达+集中接货 外场某天某条线出发货量 = (外场某天某条线)长途出发+短途出发+派送货量
 * 
 * 长途到达：某天某条线(外场)->本外场已到达的货。 短途到达：某天某条线(营业部)->本外场已到达的货。
 * 集中接货：开单时间在某天内的集中接送货的运单，某条线(运单的收获部门)->本外场(运单的开单部门对应的外场)
 * 
 * 长途出发：某天本外场->某条线(外场)已出发的货。 短途出发：某天本外场->某条线(营业部)已出发的货。
 * 派送货量：某天本外场派送库区已出库的货。线路为本外场对应的驻地派送部。
 * 
 * 
 * 某天某条线长途到达预测货量 = 某天某条线的预测总货量 * ((y-7) + (y-14))/((z-7) + (z-14))
 * y-n表示前n天的某天某条线的实际长途到达货量， z-n表示前n天的某天某条线的实际总操作货量
 * 
 * 短途到达预测和集中接货预测同上
 * 
 * 长途出发预测、短途出发预测、派送货量预测同到达货量预测。
 */
public class CargoFcstService implements ICargoFcstService {

	/**
	 * 货量预测配置里，全公司的编码
	 */
	private final String DIP = "DIP";

	/**
	 * 到达货量类型
	 */
	private final String[] GOODS_TYPE_ARRIVAL = { "ARRIVAL_LONG",
			"ARRIVAL_SHORT", "ARRIVAL_PICKUP" };

	/**
	 * 出发货量类型
	 */
	private final String[] GOODS_TYPE_DEPAET = { "DEPART_LONG", "DEPART_SHORT",
			"DEPART_DISPATCH" };

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	private ICargoFcstDao cargoFcstDao;

	public void setCargoFcstDao(ICargoFcstDao cargoFcstDao) {
		this.cargoFcstDao = cargoFcstDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @desc 货量预测，实际货量和预测货量生成；
	 * 
	 *       每个外场的配置不一样，业务规定配置最晚到6点，所以任务6点后执行 未预防整点的定时任务太多，目前定于每天6点5分执行
	 * @date 2015年3月19日 下午3:05:11
	 * @author Ouyang
	 */
	@Override
	public void fcstCargo() {
		Date date = new Date();
		// 生成实际货量
		genreateActual(date);
		// 生成预测货量
		generateFcst(date);
	}

	/**
	 * @desc 判断某天是否节假日
	 * @param date
	 * @return
	 * @date 2015年3月18日 上午11:19:44
	 * @author Ouyang
	 */
	private boolean isHoliday(Date date) {
		return cargoFcstDao.findHoliday(date) != PlatformConstants.SONAR_NUMBER_0;
	}

	/**
	 * @desc 查询全国货量预测默认参数
	 * @return
	 * @date 2015年3月19日 上午11:36:57
	 * @author Ouyang
	 */
	@Override
	public String findDefaultConfig() {
		Map<String, String> map = new HashMap<String, String>();

		map.put("type", DictionaryConstants.SYSTEM_CONFIG_PARM__TFR);
		map.put("code", ConfigurationParamsConstants.TFR_PARM__FORECAST_START);
		map.put("tfrCtrCode", DIP);

		return cargoFcstDao.findFcstConfig(map);
	}

	/**
	 * @desc 查询外场货量预测特别参数
	 * @param tfrCtrCode
	 * @return
	 * @date 2015年3月19日 上午11:37:15
	 * @author Ouyang
	 */
	@Override
	public String findTfrCtrConfig(String tfrCtrCode) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("type", DictionaryConstants.SYSTEM_CONFIG_PARM__TFR);
		map.put("code", ConfigurationParamsConstants.TFR_PARM__FORECAST_START);
		map.put("tfrCtrCode", tfrCtrCode);

		return cargoFcstDao.findFcstConfig(map);
	}

	/**
	 * @desc 生成实际货量
	 * @param parameter
	 * @date 2015年3月18日 上午11:29:22
	 * @author Ouyang
	 */
	private void insertActual(CargoFcstDto parameter) {
		// 生成到达实际货量，长途、短途、接送货
		cargoFcstDao.insertActualArrLng(parameter);
		cargoFcstDao.insertActualArrSht(parameter);
		cargoFcstDao.insertActualArrPickup(parameter);
		// 生成出发实际货量，长途、短途、派送
		cargoFcstDao.insertActualDptLng(parameter);
		cargoFcstDao.insertActualDptSht(parameter);
		cargoFcstDao.insertActualDptDispatch(parameter);
		// 实际货量汇总
		cargoFcstDao.insertActual(parameter);
	}

	/**
	 * @desc 生成实际货量
	 * @date 2015年3月19日 上午11:42:55
	 * @author Ouyang
	 */
	private void genreateActual(Date date) {
		// 比如2015-03-18 03:00~2015-03-19
		// 03:00的货量记为18号的货量，19号跑的是18号的实际货量，所以staDate为2015-03-18

		// beginTime和endTime记为2015-03-18 03:00和2015-03-19 03:00
		Date staDate = DateUtils.getStartDatetime(DateUtils.addDayToDate(date,
				-1));

		// 节假日不生成实际货量
		if (isHoliday(staDate)) {
			return;
		}

		// 查询全国默认配置
		String defaultConfig = findDefaultConfig();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String beginDateStr = df.format(staDate);
		String endDateStr = df.format(DateUtils.addDayToDate(staDate, PlatformConstants.SONAR_NUMBER_1));

		String parTime = "yyyyMMddHHmm";

		// 所有外场编码(非本部)
		List<String> tfrCtrCodes = cargoFcstDao.findTfrCtrs();

		// 循环所有外场
		for (String tfrCtrCode : tfrCtrCodes) {
			// 外场特别配置
			String tfrCtrConfig = findTfrCtrConfig(tfrCtrCode);
			// 货量预测配置
			String config = StringUtils.isNotEmpty(tfrCtrConfig) ? tfrCtrConfig
					: defaultConfig;

			if (StringUtils.isEmpty(config)) {
				continue;
			}

			Date beginTime = DateUtils.convert(beginDateStr + config, parTime);
			Date endTime = DateUtils.convert(endDateStr + config, parTime);

			if (beginTime == null || endTime == null) {
				continue;
			}

			// 构造参数
			CargoFcstDto parameter = new CargoFcstDto();
			parameter.setTfrCtrCode(tfrCtrCode);
			parameter.setStaDate(staDate);
			parameter.setBeginTime(beginTime);
			parameter.setEndTime(endTime);
			// 生成实际货量
			insertActual(parameter);
		}
	}

	/**
	 * @desc 生成预测货量
	 * @date 2015年3月19日 下午2:55:47
	 * @author Ouyang
	 */
	private void generateFcst(Date date) {
		// 2015-03-19日期内跑20号的预测货量,所以staDate为2015-03-20
		Date staDate = DateUtils.getStartDatetime(DateUtils.addDayToDate(date,
				PlatformConstants.SONAR_NUMBER_1));

		// 节假日不计算预测货量
		if (isHoliday(staDate)) {
			return;
		}

		// 预测货量所需各参数对应的日期处理
		Date[] dates = calcStaDate(staDate);

		// 所有外场编码(非本部)
		List<String> tfrCtrCodes = cargoFcstDao.findTfrCtrs();

		// 循环所有外场
		for (String tfrCtrCode : tfrCtrCodes) {
			// 计算外场的预测货量，以及预测货量明细
			calcFcstPerTfrCtr(tfrCtrCode, dates);
		}
	}

	/**
	 * @desc 计算外场的预测货量，以及预测货量明细
	 * @param tfrCtrCode
	 * @param staDate
	 * @param dates
	 * @date 2015年3月18日 下午7:25:35
	 * @author Ouyang
	 */
	private void calcFcstPerTfrCtr(String tfrCtrCode, Date[] dates) {
		// 查询本外场的到达线路
		List<Map<String, String>> arrLines = cargoFcstDao
				.findArrLines(tfrCtrCode);
		// 计算到达预测货量
		calcFcstTypes(tfrCtrCode, dates, arrLines, GOODS_TYPE_ARRIVAL, false);

		// 查询本外场的出发线路
		List<Map<String, String>> departLines = cargoFcstDao
				.findDepartLines(tfrCtrCode);
		// 计算出发预测货量
		calcFcstTypes(tfrCtrCode, dates, departLines, GOODS_TYPE_DEPAET, true);
	}

	/**
	 * @desc 计算出发/到达预测货量
	 * @param tfrCtrCode
	 * @param staDate
	 * @param dates
	 * @param lines
	 * @param goodsTypes
	 * @date 2015年3月19日 下午2:55:20
	 * @author Ouyang
	 */
	private void calcFcstTypes(String tfrCtrCode, Date[] dates,
			List<Map<String, String>> lines, String[] goodsTypes,
			boolean checkExists) {
		// 循环各线路
		for (Map<String, String> line : lines) {
			String lineCode = line.get("CODE");
			String lineName = line.get("NAME");
			// 计算外场某天预测总货量
			CargoEntity[] cargos = calcFcst(tfrCtrCode, lineCode, lineName,
					dates);

			if (cargos == null) {
				continue;
			}

			CargoEntity fcst = cargos[PlatformConstants.SONAR_NUMBER_0];
			// 写预测货量
			if (checkExists) {
				if (cargoFcstDao.cntFcst(tfrCtrCode, lineCode, dates[PlatformConstants.SONAR_NUMBER_0]) == PlatformConstants.SONAR_NUMBER_0) {
					cargoFcstDao.insertFcst(fcst);
				}
			} else {
				cargoFcstDao.insertFcst(fcst);
			}

			for (String goodsType : goodsTypes) {
				// 计算外场某条线，某类型的预测货量
				CargoDetailEntity fcstDetail = calcFcstDetail(tfrCtrCode,
						lineCode, lineName, goodsType, dates, cargos);

				if (fcstDetail != null) {
					// 写预测货量明细
					cargoFcstDao.insertFcstDetail(fcstDetail);
				}
			}
		}
	}

	/**
	 * @desc 计算外场各条线的总预测货量; 某天某条线的预测总操作货量 = (x-2) * ((x-7)+(x-14)) /
	 *       ((x-9)+(x-16)) x-n表示前n天的某天某条线的实际总操作货量。
	 * @param tfrCtrCode
	 * @param lineCode
	 * @param lineName
	 * @param dates
	 * @return CargoEntity[] 0：预测总货量，1：7天前实际总货量，2：14天前实际总货量
	 * @date 2015年3月18日 下午5:37:46
	 * @author Ouyang
	 */
	private CargoEntity[] calcFcst(String tfrCtrCode, String lineCode,
			String lineName, Date[] dates) {

		Date staDate = dates[PlatformConstants.SONAR_NUMBER_0];

		Date staDate2 = dates[PlatformConstants.SONAR_NUMBER_1];
		Date staDate7 = dates[PlatformConstants.SONAR_NUMBER_2];
		Date staDate14 = dates[PlatformConstants.SONAR_NUMBER_3];
		Date staDate9 = dates[PlatformConstants.SONAR_NUMBER_4];
		Date staDate16 = dates[PlatformConstants.SONAR_NUMBER_5];

		CargoEntity actual16 = cargoFcstDao.findActual(tfrCtrCode, staDate16,
				lineCode);
		if (actual16 == null) {
			return null;
		}
		CargoEntity actual14 = cargoFcstDao.findActual(tfrCtrCode, staDate14,
				lineCode);
		if (actual14 == null) {
			return null;
		}
		CargoEntity actual9 = cargoFcstDao.findActual(tfrCtrCode, staDate9,
				lineCode);
		if (actual9 == null) {
			return null;
		}
		CargoEntity actual7 = cargoFcstDao.findActual(tfrCtrCode, staDate7,
				lineCode);
		if (actual7 == null) {
			return null;
		}
		CargoEntity actual2 = cargoFcstDao.findActual(tfrCtrCode, staDate2,
				lineCode);
		if (actual2 == null) {
			return null;
		}

		BigDecimal a2Weight = actual2.getWeight();
		BigDecimal a7Weight = actual7.getWeight();
		BigDecimal a14Weight = actual14.getWeight();
		BigDecimal a9Weight = actual9.getWeight();
		BigDecimal a16Weight = actual16.getWeight();

		BigDecimal a2Volume = actual2.getVolume();
		BigDecimal a7Volume = actual7.getVolume();
		BigDecimal a14Volume = actual14.getVolume();
		BigDecimal a9Volume = actual9.getVolume();
		BigDecimal a16Volume = actual16.getVolume();

		int a2Vote = actual2.getVote();
		int a7Vote = actual7.getVote();
		int a14Vote = actual14.getVote();
		int a9Vote = actual9.getVote();
		int a16Vote = actual16.getVote();

		// 按公式计算预测数据
		BigDecimal weight = calcFormula(a2Weight, a7Weight, a14Weight,
				a9Weight, a16Weight);
		BigDecimal volume = calcFormula(a2Volume, a7Volume, a14Volume,
				a9Volume, a16Volume);
		int vote = calcFormula(a2Vote, a7Vote, a14Vote, a9Vote, a16Vote);

		if (weight == null || volume == null) {
			return null;
		}

		CargoEntity fcst = new CargoEntity();
		fcst.setId(UUIDUtils.getUUID());
		fcst.setTfrCtrCode(tfrCtrCode);
		fcst.setLineCode(lineCode);
		fcst.setLineName(lineName);
		fcst.setWeight(weight);
		fcst.setVolume(volume);
		fcst.setVote(vote);
		fcst.setStaDate(staDate);

		return new CargoEntity[] { fcst, actual7, actual14 };
	}

	/**
	 * @desc 计算外场某条线，某类型的预测货量 某天某条线长途到达预测货量 = 某天某条线的预测总货量 * ((y-7) +
	 *       (y-14))/((z-7) + (z-14))；
	 * 
	 *       y-n表示前n天的某天某条线的实际长途到达货量， z-n表示前n天的某天某条线的实际总操作货量
	 * 
	 *       短途到达预测和集中接货预测同上 长途出发预测、短途出发预测、派送货量预测同到达货量预测。
	 * @param cargoEntity
	 * @param goodsType
	 * @param dates
	 * @param cargos
	 * @return
	 * @date 2015年3月19日 上午9:30:22
	 * @author Ouyang
	 */
	private CargoDetailEntity calcFcstDetail(String tfrCtrCode,
			String lineCode, String lineName, String goodsType, Date[] dates,
			CargoEntity cargos[]) {
		CargoEntity fcst = cargos[PlatformConstants.SONAR_NUMBER_0];
		CargoEntity actual7 = cargos[PlatformConstants.SONAR_NUMBER_1];
		CargoEntity actual14 = cargos[PlatformConstants.SONAR_NUMBER_2];

		Date staDate7 = dates[PlatformConstants.SONAR_NUMBER_2];
		Date staDate14 = dates[PlatformConstants.SONAR_NUMBER_3];

		CargoDetailEntity detail7 = cargoFcstDao.findActualDetail(tfrCtrCode,
				staDate7, lineCode, goodsType);
		CargoDetailEntity detail14 = cargoFcstDao.findActualDetail(tfrCtrCode,
				staDate14, lineCode, goodsType);

		if (detail7 == null || detail14 == null) {
			return null;
		}

		BigDecimal fcstWeight = fcst.getWeight();
		BigDecimal d7Weight = detail7.getWeight();
		BigDecimal d14Weight = detail14.getWeight();
		BigDecimal a7Weight = actual7.getWeight();
		BigDecimal a14Weight = actual14.getWeight();

		BigDecimal fcstVolume = fcst.getVolume();
		BigDecimal d7Volume = detail7.getVolume();
		BigDecimal d14Volume = detail14.getVolume();
		BigDecimal a7Volume = actual7.getVolume();
		BigDecimal a14Volume = actual14.getVolume();

		int fcstVote = fcst.getVote();
		int d7Vote = detail7.getVote();
		int d14Vote = detail14.getVote();
		int a7Vote = actual7.getVote();
		int a14Vote = actual14.getVote();

		// 按公式计算预测数据
		BigDecimal weight = calcFormula(fcstWeight, d7Weight, d14Weight,
				a7Weight, a14Weight);
		BigDecimal volume = calcFormula(fcstVolume, d7Volume, d14Volume,
				a7Volume, a14Volume);
		int vote = calcFormula(fcstVote, d7Vote, d14Vote, a7Vote, a14Vote);

		if (weight == null || volume == null) {
			return null;
		}

		CargoDetailEntity fcstDetail = new CargoDetailEntity();
		fcstDetail.setId(UUIDUtils.getUUID());
		fcstDetail.setTfrCtrCode(tfrCtrCode);
		fcstDetail.setLineCode(lineCode);
		fcstDetail.setLineName(lineName);
		fcstDetail.setWeight(weight);
		fcstDetail.setVolume(volume);
		fcstDetail.setVote(vote);
		fcstDetail.setGoodsType(goodsType);
		fcstDetail.setStaDate(dates[PlatformConstants.SONAR_NUMBER_0]);
		return fcstDetail;
	}

	/**
	 * @desc 公式计算
	 * @param n1
	 * @param n2
	 * @param n3
	 * @param n4
	 * @param n5
	 * @return
	 * @date 2015年3月18日 下午6:07:27
	 * @author Ouyang
	 */
	private BigDecimal calcFormula(BigDecimal n1, BigDecimal n2, BigDecimal n3,
			BigDecimal n4, BigDecimal n5) {

		try {
			return n1.multiply(n2.add(n3)).divide(n4.add(n5), PlatformConstants.SONAR_NUMBER_3,
					BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			return null;
		}
	}

	private int calcFormula(int n1, int n2, int n3, int n4, int n5) {
		try {
			return n1 * (n2 + n3) / (n4 + n5);
		} catch (Exception e) {
			return PlatformConstants.SONAR_NUMBER_0;
		}
	}

	/**
	 * @desc 预测货量所需各参数对应的日期处理
	 *       比如计算20号的预测货量，需要用的18号(2天前)，13号(7天前)，6号(14天前)，11号(9天前)，4号(16天前)的实际货量，
	 *       后面5个日期，按节假日规则处理后。 则返回，20号，以及后面5日期处理后的结果
	 * @param staDate
	 * @return
	 * @date 2015年3月19日 下午12:20:31
	 * @author Ouyang
	 */
	private Date[] calcStaDate(Date staDate) {
		// 2天前
		Date staDate2 = DateUtils.addDayToDate(staDate, -2);

		// 7天前
		Date staDate7 = DateUtils.addDayToDate(staDate, -7);
		// 14天前
		Date staDate14 = DateUtils.addDayToDate(staDate, -14);
		// 9天前
		Date staDate9 = DateUtils.addDayToDate(staDate, -9);
		// 16天前
		Date staDate16 = DateUtils.addDayToDate(staDate, -16);

		// 处理第1个时间参数
		Date date = queryNotHoliday(staDate2);

		if (date == null) {
			return null;
		}

		// 处理后面4个时间参数
		Date[] dates = queryNotHoliday(staDate7, staDate14, staDate9, staDate16);

		if (dates == null) {
			return null;
		}

		return new Date[] { staDate, date, dates[PlatformConstants.SONAR_NUMBER_0], dates[PlatformConstants.SONAR_NUMBER_1], dates[PlatformConstants.SONAR_NUMBER_2],
				dates[PlatformConstants.SONAR_NUMBER_3] };
	}

	/**
	 * @desc 1个月内，以步长为7，往前推，直至找到非节假日；否则返回null
	 * @param staDate
	 * @return
	 * @date 2015年3月18日 下午3:48:23
	 * @author Ouyang
	 */
	private Date queryNotHoliday(Date d1) {

		if (d1 == null) {
			return d1;
		}

		for (int i = PlatformConstants.SONAR_NUMBER_0; i <= PlatformConstants.SONAR_NUMBER_31; i += PlatformConstants.SONAR_NUMBER_7) {
			Date p1 = DateUtils.addDayToDate(d1, -i);
			if (!isHoliday(p1)) {
				return p1;
			}
		}
		return null;
	}

	/**
	 * @desc 若d1或d3为节假日，则4参数同时往前推1星期，若d2或d4为节假日，则d2和d4同时往前推1星期
	 * @param d1
	 * @param d2
	 * @param d3
	 * @param d4
	 * @return
	 * @date 2015年3月18日 下午4:56:30
	 * @author Ouyang
	 */
	private Date[] queryNotHoliday(Date d1, Date d2, Date d3, Date d4) {

		if (d1 == null || d2 == null || d3 == null || d4 == null) {
			return null;
		}

		for (int i = PlatformConstants.SONAR_NUMBER_0; i <= PlatformConstants.SONAR_NUMBER_31; i += PlatformConstants.SONAR_NUMBER_7) {
			Date p1 = DateUtils.addDayToDate(d1, -i);
			Date p2 = DateUtils.addDayToDate(d2, -i);
			Date p3 = DateUtils.addDayToDate(d3, -i);
			Date p4 = DateUtils.addDayToDate(d4, -i);

			if (!isHoliday(p1) && !isHoliday(p3)) {
				Date[] dates = queryNotHoliday(p2, p4);
				if (dates == null) {
					return null;
				}
				return new Date[] { p1, dates[PlatformConstants.SONAR_NUMBER_0], p3, dates[PlatformConstants.SONAR_NUMBER_1] };
			}
		}
		return null;
	}

	/**
	 * @desc 若d1或d2为节假日，则同时往前推1星期
	 * @param d1
	 * @param d2
	 * @return
	 * @date 2015年3月18日 下午4:58:13
	 * @author Ouyang
	 */
	private Date[] queryNotHoliday(Date d1, Date d2) {

		if (d1 == null || d2 == null) {
			return null;
		}

		for (int i = PlatformConstants.SONAR_NUMBER_0; i <= PlatformConstants.SONAR_NUMBER_31; i += PlatformConstants.SONAR_NUMBER_7) {
			Date p1 = DateUtils.addDayToDate(d1, -i);
			Date p2 = DateUtils.addDayToDate(d2, -i);

			if (!isHoliday(p1) && !isHoliday(p2)) {
				return new Date[] { p1, p2 };
			}
		}
		return null;
	}

	/**
	 * @desc 界面查询
	 * @param parameter
	 * @return
	 * @date 2015年3月18日 上午11:21:34
	 * @author Ouyang
	 */
	@Override
	public List<CargoFcstResultDto> findFcstResult(CargoFcstDto parameter) {
		List<CargoFcstResultDto> result = cargoFcstDao
				.findFcstResult(parameter);
		List<CargoFcstResultDto> total = cargoFcstDao
				.findFcstResultTotal(parameter);
		result.addAll(PlatformConstants.SONAR_NUMBER_0, total);
		return result;
	}

	/**
	 * @desc 查询部门所属外场
	 * @param orgCode
	 * @return
	 * @date 2015年3月19日 下午4:07:46
	 * @author Ouyang
	 */
	@Override
	public OrgAdministrativeInfoEntity queryTfrCtrBySubCode(String orgCode) {
		if (StringUtils.isEmpty(orgCode)) {
			return new OrgAdministrativeInfoEntity();
		}

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		// 调用综合接口，查询部门所属外场
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(orgCode,
						bizTypesList);

		return orgEntity;
	}

}

package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrStaffDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrStaffService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrStaffNoDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrStaffQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.util.UUIDUtils;

public class TfrCtrStaffService implements ITfrCtrStaffService {

	private ITfrCtrStaffDao tfrCtrStaffDao;

	/**
	 * 综合组织
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setTfrCtrStaffDao(ITfrCtrStaffDao tfrCtrStaffDao) {
		this.tfrCtrStaffDao = tfrCtrStaffDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 获取部门所属外场
	 * @param code
	 * @return
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	@Override
	public OrgAdministrativeInfoEntity getTfrCtrBySubCode(String code) {

		if (StringUtils.isEmpty(code)) {
			return null;
		}

		List<String> bizTypesList = new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		// 调用综合接口，查询部门所属外场
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);

		return orgEntity;
	}

	/**
	 * 根据岗位code，获取员工
	 * @param posts
	 * @return
	 * @date 2014-4-23
	 * @author Ouyang
	 */
	private List<EmployeeEntity> getEmployeesByPosts(String[] posts) {
		List<String> asList = Arrays.asList(posts);
		return tfrCtrStaffDao.getEmployeesByPosts(asList);
	}

	/**
	 * 判断装卸车员是否未出勤
	 * @param loader
	 * @return
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	private boolean isLoaderNoDuty(TfrCtrStaffNoDutyEntity loader) {
		return tfrCtrStaffDao.selectOneWorkLoad(loader) == 0;
	}

	/**
	 * 判断叉车司机是否未出勤
	 * @param forkliftDriver
	 * @return
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	private boolean isForkliftDriverNoDuty(
			TfrCtrStaffNoDutyEntity forkliftDriver) {
		return tfrCtrStaffDao.selectOneTrayScan(forkliftDriver) == 0;
	}

	/**
	 * 判断未出勤的员工是否最近3天连续未出勤
	 * @param entity 当天未出勤的员工
	 * @return
	 * @date 2014-4-23
	 * @author Ouyang
	 */
	private boolean isStaff3DayNoDuty(TfrCtrStaffNoDutyEntity entity) {

		// 首先判断未出勤员工在统计日期当月是否存在连续3天未出勤的记录，如果存在，则不再继续统计此员工
		// 如果不存在，则查询TfrCtrStaffNoDutyEntity.statisticDate前两天，该人员工号的未出勤记录数；
		// 如果每天都未出勤，则应该每天一条，若前两天的记录数为2，则表示改员工连续3天未出勤
		return tfrCtrStaffDao.selectOneStaff3DayNoDuty(entity) == 0
				&& tfrCtrStaffDao.selectCntStaffNoDutyInPre2Day(entity) == 2;
	}

	/**
	 * 根据员工构造一个TfrCtrStaffNoDutyEntity
	 * @param employee
	 * @param statisticDate
	 * @param statisticMonth
	 * @return
	 * @date 2014-4-23
	 * @author Ouyang
	 */
	private TfrCtrStaffNoDutyEntity constructTfrCtrStaffNoDuty(
			EmployeeEntity employee, Date statisticDate, String statisticMonth) {
		// 员工所属部门
		String orgCode = employee.getOrgCode();
		String orgName = employee.getOrgName();

		// 员工工号、姓名、岗位code、岗位名称
		String empCode = employee.getEmpCode();
		String empName = employee.getEmpName();
		String postCode = employee.getTitle();
		String postName = employee.getTitleName();

		if (StringUtils.isEmpty(orgCode)) {
			return null;
		}

		// 查询员工所属外场
		OrgAdministrativeInfoEntity orgEntity = getTfrCtrBySubCode(orgCode);
		// 只考虑外场员工
		if (orgEntity == null || StringUtils.isEmpty(orgEntity.getCode())) {
			return null;
		}

		// 外场编码、名称
		String transferCenterCode = orgEntity.getCode();
		String transferCenterName = orgEntity.getName();

		// 构造员工、外场关系
		TfrCtrStaffNoDutyEntity tfrCtrStaffNoDutyEntity = new TfrCtrStaffNoDutyEntity();

		tfrCtrStaffNoDutyEntity.setId(UUIDUtils.getUUID());

		tfrCtrStaffNoDutyEntity.setEmpCode(empCode);
		tfrCtrStaffNoDutyEntity.setEmpName(empName);
		tfrCtrStaffNoDutyEntity.setPostCode(postCode);
		tfrCtrStaffNoDutyEntity.setPostName(postName);
		tfrCtrStaffNoDutyEntity.setOrgCode(orgCode);
		tfrCtrStaffNoDutyEntity.setOrgName(orgName);
		tfrCtrStaffNoDutyEntity.setTransferCenterCode(transferCenterCode);
		tfrCtrStaffNoDutyEntity.setTransferCenterName(transferCenterName);
		tfrCtrStaffNoDutyEntity.setStatisticDate(statisticDate);
		tfrCtrStaffNoDutyEntity.setStatisticMonth(statisticMonth);

		return tfrCtrStaffNoDutyEntity;
	}

	/**
	 * 获取员工的出勤情况
	 * @param employees
	 * @param beginQueryDate
	 * @param endQueryDate
	 * @param statisticDate
	 * @param statisticMonth
	 * @param type 0表示理货员、1表示叉车司机
	 * @return
	 * @date 2014-4-23
	 * @author Ouyang
	 */
	private Map<String, List<TfrCtrStaffNoDutyEntity>> getTfrCtrStaffNoDuty(
			List<EmployeeEntity> employees, Date beginQueryDate,
			Date endQueryDate, Date statisticDate, String statisticMonth,
			int type) {

		Map<String, List<TfrCtrStaffNoDutyEntity>> result = new HashMap<String, List<TfrCtrStaffNoDutyEntity>>();

		// 统计日期未出勤的员工;key为noDuty
		List<TfrCtrStaffNoDutyEntity> noDuty = new ArrayList<TfrCtrStaffNoDutyEntity>();
		// 连续3天未出勤的员工;key为noDuty3Day
		List<TfrCtrStaffNoDutyEntity> noDuty3Day = new ArrayList<TfrCtrStaffNoDutyEntity>();
		// 统计日期出勤的员工;key为onDuty
		List<TfrCtrStaffNoDutyEntity> onDuty = new ArrayList<TfrCtrStaffNoDutyEntity>();

		if (type == 0) {
			for (EmployeeEntity employee : employees) {
				// 根据员工信息构造一个TfrCtrStaffNoDutyEntity
				TfrCtrStaffNoDutyEntity tfrCtrStaffNoDutyEntity = constructTfrCtrStaffNoDuty(
						employee, statisticDate, statisticMonth);

				if (tfrCtrStaffNoDutyEntity == null) {
					continue;
				}
				tfrCtrStaffNoDutyEntity.setBeginQueryDate(beginQueryDate);
				tfrCtrStaffNoDutyEntity.setEndQueryDate(endQueryDate);

				// 判断理货员是否未出勤
				if (isLoaderNoDuty(tfrCtrStaffNoDutyEntity)) {
					noDuty.add(tfrCtrStaffNoDutyEntity);
					// 判断是否连续3天未出勤
					if (isStaff3DayNoDuty(tfrCtrStaffNoDutyEntity)) {
						noDuty3Day.add(tfrCtrStaffNoDutyEntity);
					}
				} else {
					onDuty.add(tfrCtrStaffNoDutyEntity);
				}
			}
		} else if (type == 1) {
			for (EmployeeEntity employee : employees) {
				// 根据员工信息构造一个TfrCtrStaffNoDutyEntity
				TfrCtrStaffNoDutyEntity tfrCtrStaffNoDutyEntity = constructTfrCtrStaffNoDuty(
						employee, statisticDate, statisticMonth);

				if (tfrCtrStaffNoDutyEntity == null) {
					continue;
				}
				tfrCtrStaffNoDutyEntity.setBeginQueryDate(beginQueryDate);
				tfrCtrStaffNoDutyEntity.setEndQueryDate(endQueryDate);

				// 判断叉车司机是否未出勤
				if (isForkliftDriverNoDuty(tfrCtrStaffNoDutyEntity)) {
					noDuty.add(tfrCtrStaffNoDutyEntity);
					// 判断是否连续3天未出勤
					if (isStaff3DayNoDuty(tfrCtrStaffNoDutyEntity)) {
						noDuty3Day.add(tfrCtrStaffNoDutyEntity);
					}
				} else {
					onDuty.add(tfrCtrStaffNoDutyEntity);
				}
			}
		}

		result.put("noDuty", noDuty);
		result.put("noDuty3Day", noDuty3Day);
		result.put("onDuty", onDuty);

		return result;
	}

	/**
	 * 获取获取员工表中职位为装卸车员的出勤情况
	 * @param beginQueryDate
	 * @param endQueryDate
	 * @param statisticDate
	 * @param statisticMonth
	 * @return
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	private Map<String, List<TfrCtrStaffNoDutyEntity>> getLoadersNoDuty(
			Date beginQueryDate, Date endQueryDate, Date statisticDate,
			String statisticMonth) {

		// 查询岗位的理货员、经营外场组长、运营外场组长code的员工
		List<EmployeeEntity> loaders = getEmployeesByPosts(PlatformConstants.POST_LOADER_CODES);
		if (CollectionUtils.isEmpty(loaders)) {
			return null;
		}
		// 设置理货员所属外场，并获取未出勤的理货员
		return getTfrCtrStaffNoDuty(loaders, beginQueryDate, endQueryDate,
				statisticDate, statisticMonth, 0);
	}

	/**
	 * 获取员工表中职位为电叉司机的出勤情况
	 * @return
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	private Map<String, List<TfrCtrStaffNoDutyEntity>> getForkliftDriversNoDuty(
			Date beginQueryDate, Date endQueryDate, Date statisticDate,
			String statisticMonth) {

		// 查询岗位的电叉司机、电叉司机组长code的员工
		List<EmployeeEntity> forkliftDrivers = getEmployeesByPosts(PlatformConstants.POST_FORKLIFT_DRIVER_CODES);
		if (CollectionUtils.isEmpty(forkliftDrivers)) {
			return null;
		}

		// 设置电叉司机所属外场，并获取未出勤的电叉司机
		return getTfrCtrStaffNoDuty(forkliftDrivers, beginQueryDate,
				endQueryDate, statisticDate, statisticMonth, 1);
	}

	/**
	 * 保存装卸车员(叉车司机)的出勤情况
	 * @param map
	 * @date 2014-4-25
	 * @author Ouyang
	 */
	private void insertStaff(Map<String, List<TfrCtrStaffNoDutyEntity>>... maps) {

		if (maps == null) {
			return;
		}

		for (Map<String, List<TfrCtrStaffNoDutyEntity>> map : maps) {
			if (MapUtils.isEmpty(map)) {
				return;
			}

			List<TfrCtrStaffNoDutyEntity> noDuty = map.get("noDuty");
			List<TfrCtrStaffNoDutyEntity> noDuty3Day = map.get("noDuty3Day");
			List<TfrCtrStaffNoDutyEntity> onDuty = map.get("onDuty");

			if (CollectionUtils.isNotEmpty(noDuty)) {
				for (TfrCtrStaffNoDutyEntity item : noDuty) {
					tfrCtrStaffDao.insertStaffNoDuty(item);
				}
			}

			if (CollectionUtils.isNotEmpty(noDuty3Day)) {
				for (TfrCtrStaffNoDutyEntity item : noDuty3Day) {
					tfrCtrStaffDao.insertStaff3DayNoDuty(item);
				}
			}

			if (CollectionUtils.isNotEmpty(onDuty)) {
				for (TfrCtrStaffNoDutyEntity item : onDuty) {
					tfrCtrStaffDao.insertStaffOnDuty(item);
				}
			}
		}
	}

	/**
	 * 保存每天保存装卸车员(叉车司机)的出勤情况
	 * @date 2014-4-22
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insertTfrCtrStaff() {
		// 当天0点
		Date currentDay = PlatformUtils.getFirstMomentOfDay(new Date());
		// 统计日期；今天统计昨天的数据
		Date statisticDate = PlatformUtils.addDate(currentDay,
				Calendar.DAY_OF_MONTH, -1);
		// 统计月份
		String statisticMonth = PlatformUtils.formatDate2String(statisticDate,
				"yyyy-MM");

		// 需求定义用前一天的6:00到当天的6:00到，
		// 卸车员是否有货量、叉车司机是否有叉车票来判断前一天是否出勤;
		// 而此处的beginQueryDate就为前一天的6:00
		Date beginQueryDate = PlatformUtils.addDate(statisticDate,
				Calendar.HOUR_OF_DAY, PlatformConstants.SONAR_NUMBER_6);
		// 此处的endQueryDate就为当天的6:00
		Date endQueryDate = PlatformUtils.addDate(currentDay,
				Calendar.HOUR_OF_DAY, PlatformConstants.SONAR_NUMBER_6);

		// 装卸车员的出勤情况
		Map<String, List<TfrCtrStaffNoDutyEntity>> loader = getLoadersNoDuty(
				beginQueryDate, endQueryDate, statisticDate, statisticMonth);

		// 电车司机的出勤情况
		Map<String, List<TfrCtrStaffNoDutyEntity>> drivers = getForkliftDriversNoDuty(
				beginQueryDate, endQueryDate, statisticDate, statisticMonth);

		// 保存装卸车员(叉车司机)的出勤情况
		insertStaff(loader, drivers);
	}

	/**
	 * 给外场人员情况添加离职率和统计时间
	 * @param item
	 * @param firstMomOfQueryMonth
	 * @param queryDate
	 * @date 2014-4-25
	 * @author Ouyang
	 */
	private void attachDimissionRate(TfrCtrStaffDto item,
			Date firstMomOfQueryMonth, Date queryDate) {
		// 离职率
		String dimissionRate = null;

		// 月累计离职数
		Integer dimissionAccumulated = item.getDimissionAccumulated();

		if (dimissionAccumulated != null) {
			if (dimissionAccumulated == 0) {
				dimissionRate = "0%";
			} else {
				// 外场code
				String transferCenterCode = item.getTransferCenterCode();

				// 当日外场总实际
				Integer totalQtyActual = item.getTotalQtyActual() == null ? 0
						: item.getTotalQtyActual();

				// 查询当月1号外场总实际
				Integer totalQtyActualFirstDay = tfrCtrStaffDao
						.queryTfrCtrActual(transferCenterCode,
								firstMomOfQueryMonth);

				totalQtyActualFirstDay = totalQtyActualFirstDay == null ? 0
						: totalQtyActualFirstDay;
				if (totalQtyActual + totalQtyActualFirstDay != 0) {
					// 离职率的分母 = (当月1号外场总实际+当日外场总实际)/2
					BigDecimal denominator = new BigDecimal(totalQtyActual
							+ totalQtyActualFirstDay).divide(new BigDecimal(2),
							2, BigDecimal.ROUND_HALF_UP);
					// 离职率 = 月累计离职数/上述分母
					dimissionRate = new BigDecimal(dimissionAccumulated)
							.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100)).divide(denominator,
									2, BigDecimal.ROUND_HALF_UP)
							+ "%";
				}
			}
		}
		item.setStatisticDate(queryDate);
		item.setDimissionRate(dimissionRate);
	}

	/**
	 * 查询外场人员情况
	 * @param dto
	 * @return
	 * @date 2014-4-25
	 * @author Ouyang
	 */
	@Override
	public List<TfrCtrStaffDto> queryTfrCtrStaffDtos(TfrCtrStaffQcDto dto) {

		if (dto == null || dto.getQueryDate() == null) {
			return new ArrayList<TfrCtrStaffDto>();
		}

		// 查询日期
		Date queryDate = dto.getQueryDate();

		// 查询日期所在月份第一天的0点
		Date firstMomOfQueryMonth = PlatformUtils.getFirstDayOfMonth(queryDate);
		dto.setFirstMomOfQueryMonth(firstMomOfQueryMonth);

		// 查询日期的23点59分59秒
		Date lastMomOfQueryDate = PlatformUtils.getLastMomentOfDay(queryDate);
		dto.setLastMomOfQueryDate(lastMomOfQueryDate);

		// 查询日期所在月份
		String queryMonth = PlatformUtils.formatDate2String(queryDate,
				"yyyy-MM");
		dto.setQueryMonth(queryMonth);

		// 查询外场预算人员、实际人员、离职数、月累计离职数、异常数、月累计异常数、异常率、出勤数、未出勤数、月累计连续3日未出勤数；
		// 还少离职率(需要当月1号总实际)、
		List<TfrCtrStaffDto> tfrCtrStaffDtos = tfrCtrStaffDao
				.queryTfrCtrStaffDtos(dto);

		for (TfrCtrStaffDto item : tfrCtrStaffDtos) {
			// 设置离职率
			attachDimissionRate(item, firstMomOfQueryMonth, queryDate);
		}

		return tfrCtrStaffDtos;
	}

	/**
	 * 查询某外场某月月累计连续3天未出勤人员
	 * @param transferCenterCode
	 * @param queryMonth
	 * @return
	 * @date 2014-4-28
	 * @author Ouyang
	 */
	@Override
	public List<TfrCtrStaffNoDutyEntity> queryTfrCtrStaff3DayNoDuty(
			String transferCenterCode, Date queryDate) {
		// 查询日期所在月份
		String queryMonth = PlatformUtils.formatDate2String(queryDate,
				"yyyy-MM");
		return tfrCtrStaffDao.queryTfrCtrStaff3DayNoDuty(transferCenterCode,
				queryMonth);
	}

	/**
	 * 导出外场月累计连续3日未出勤名单
	 * @param transferCenterCode
	 * @param queryDate
	 * @return
	 * @date 2014-4-30
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportTfrCtrStaff3DayNoDuty(
			String transferCenterCode, Date queryDate) {

		List<TfrCtrStaffNoDutyEntity> staff3DayNoDuty = queryTfrCtrStaff3DayNoDuty(
				transferCenterCode, queryDate);

		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> result = null;

		for (TfrCtrStaffNoDutyEntity entity : staff3DayNoDuty) {
			result = new ArrayList<String>();

			// 外场
			result.add(entity.getTransferCenterName());
			// 部门
			result.add(entity.getOrgName());
			// 工号
			result.add(entity.getEmpCode());
			// 姓名
			result.add(entity.getEmpName());
			// 岗位
			result.add(entity.getPostName());
			// 新增日期
			result.add(String.format("%1$tF", entity.getStatisticDate()));

			rowList.add(result);
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(PlatformConstants.EXCEL_HEADER_TFRCTRSTAFF3DAYNODUT);
		sheet.setRowList(rowList);
		return sheet;
	}

	private String parseInteger2Str(Integer source) {
		return source == null ? null : source.toString();
	}

	/**
	 * 导出外场人员情况
	 * @param dto
	 * @return
	 * @date 2014-4-30
	 * @author Ouyang
	 */
	@Override
	public ExportResource exportTfrCtrStaffDtos(TfrCtrStaffQcDto dto) {

		List<TfrCtrStaffDto> tfrCtrStaffDtos = queryTfrCtrStaffDtos(dto);
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> result = null;

		for (TfrCtrStaffDto item : tfrCtrStaffDtos) {
			result = new ArrayList<String>();

			result.add(String.format("%1$tF", item.getStatisticDate()));

			result.add(item.getTransferCenterName());

			result.add(parseInteger2Str(item.getTotalQtyBudeget()));
			result.add(parseInteger2Str(item.getLoaderBudget()));
			result.add(parseInteger2Str(item.getDriverBudget()));

			result.add(parseInteger2Str(item.getTotalQtyActual()));
			result.add(parseInteger2Str(item.getLoaderActual()));
			result.add(parseInteger2Str(item.getDriverActual()));

			result.add(parseInteger2Str(item.getTotalRemainder()));
			result.add(parseInteger2Str(item.getLoaderRemainder()));
			result.add(parseInteger2Str(item.getDriverRemainder()));

			result.add(parseInteger2Str(item.getAbsenteeDayCnt()));
			result.add(parseInteger2Str(item.getAbsenteeAccumulatedCnt()));
			result.add(item.getAbsentRate());

			result.add(parseInteger2Str(item.getDimission()));
			result.add(parseInteger2Str(item.getDimissionAccumulated()));
			result.add(item.getDimissionRate());

			result.add(parseInteger2Str(item.getOnDutyCnt()));
			result.add(parseInteger2Str(item.getNoDutyCnt()));
			result.add(parseInteger2Str(item.getNoDuty3DayCnt()));

			rowList.add(result);
		}

		HeaderRows[] headerRows = { new HeaderRows(0, 1, 0, 0, "日期"),
				new HeaderRows(0, 1, 1, 1, "外场"),
				new HeaderRows(0, 0, 2, PlatformConstants.SONAR_NUMBER_4, "预算人员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_5, PlatformConstants.SONAR_NUMBER_7, "现有人员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_8, PlatformConstants.SONAR_NUMBER_10, "缺口"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_11, PlatformConstants.SONAR_NUMBER_13, "异常人员"),
				new HeaderRows(0, 0, PlatformConstants.SONAR_NUMBER_14, PlatformConstants.SONAR_NUMBER_16, "离职人员"),
				new HeaderRows(0, 1, PlatformConstants.SONAR_NUMBER_17, PlatformConstants.SONAR_NUMBER_17, "当日出勤人数"),
				new HeaderRows(0, 1, PlatformConstants.SONAR_NUMBER_18, PlatformConstants.SONAR_NUMBER_18, "当日未出勤人数"),
				new HeaderRows(0, 1, PlatformConstants.SONAR_NUMBER_19, PlatformConstants.SONAR_NUMBER_19, "月累计连续3日未出勤人数") };

		ExportResource sheet = new ExportResource();
		sheet.setHeaderHeight(2);

		sheet.setHeaderList(Arrays.asList(headerRows));
		String[] source = { "总人数", "理货员", "叉车司机" };
		String[] heads = new String[2];
		for (int i = 0; i < PlatformConstants.SONAR_NUMBER_3; i++) {
			heads = (String[]) ArrayUtils.addAll(heads, source);
		}
		heads = (String[]) ArrayUtils.addAll(heads, new String[] { "当日异常数",
				"月累计异常数", "异常率", "当日离职数", "月累计离职数", "离职率" });
		heads = (String[]) ArrayUtils.addAll(heads, new String[PlatformConstants.SONAR_NUMBER_3]);
		sheet.setHeads(heads);
		sheet.setRowList(rowList);

		return sheet;
	}
}

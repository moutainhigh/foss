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
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockDurationDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockDurationService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDuration;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationDispatch;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationTransfer;
import com.deppon.foss.module.transfer.platform.api.shared.dto.Dates4StockDurationDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.KeyValueDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.StockDurationQcDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class StockDurationService implements IStockDurationService {

	private IStockDurationDao stockDurationDao;

	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	public void setStockDurationDao(IStockDurationDao stockDurationDao) {
		this.stockDurationDao = stockDurationDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @desc 查询提货方式
	 * @return
	 * @date 2015年4月10日 上午11:36:03
	 * @author Ouyang
	 */
	@Override
	public List<KeyValueDto> findReceiveMethod() {
		return stockDurationDao.findReceiveMethod();
	}

	/**
	 * @desc 生成快递货库存时长数据
	 * @date 2015年3月24日 下午5:27:00
	 * @author Ouyang
	 */
	@Override
	public void generateExp(Integer threadCount, Integer threadNo) {
		// 比如2015-03-24号跑的是2015-03-23~2015-03-24的出库数据，staDate记为2015-03-23,endDate记为2015-03-24
		Date staDate = DateUtils.getStartDatetime(new Date(), -1);
		Date endDate = DateUtils.addDayToDate(staDate, 1);

		// 统计月份
		int staMonth = Integer.valueOf(
				new SimpleDateFormat("yyyyMM").format(staDate)).intValue();

		// 查询所有非分部外场，及是否含待叉区
		List<Map<String, String>> tfrCtrs = stockDurationDao.findTfrCtrs(
				threadCount, threadNo);

		for (Map<String, String> item : tfrCtrs) {
			String tfrCtrCode = item.get("TFR_CTR_CODE");
			String tfrCtrName = item.get("TFR_CTR_NAME");
			boolean hasForkArea = FossConstants.ACTIVE.equals(item
					.get("HAS_FORK_AREA"));

			// 处理快递中转货物
			handleTfrExp(tfrCtrCode, tfrCtrName, staDate, endDate, staMonth,
					hasForkArea);

			// 处理快递派送货物
			handleDptExp(tfrCtrCode, tfrCtrName, staDate, endDate, staMonth,
					hasForkArea);

			// 快递数据日汇总
			StockDuration info = new StockDuration();
			info.setTfrCtrCode(tfrCtrCode);
			info.setTfrCtrName(tfrCtrName);
			info.setStaDate(staDate);
			info.setStaMonth(staMonth);
			handleTotalExpD(info, hasForkArea);

			// 快递数据月汇总
			stockDurationDao.insertExpMonth(tfrCtrCode, staDate, staMonth);
		}
	}

	/**
	 * @desc 处理快递中转货物
	 * @param tfrCtrCode
	 * @param tfrCtrName
	 * @param staDate
	 * @param endDate
	 * @param staMonth
	 * @param hasForkArea
	 * @date 2015年3月24日 下午5:51:24
	 * @author Ouyang
	 */
	private void handleTfrExp(String tfrCtrCode, String tfrCtrName,
			Date staDate, Date endDate, int staMonth, boolean hasForkArea) {
		// 查询外场某天快递货中转装车数据
		// 此接口已填充tfrCtrCode,tfrCtrName,waybillNo,staDate,staMonth,tfrLoadScanTime,tfrLoadTaskSubmitTime属性
		List<StockDurationTransfer> tfrLoadExp = stockDurationDao
				.findTfrLoadExp(tfrCtrCode, tfrCtrName, staDate, endDate,
						staMonth);
		// 循环所有运单，填充各种时间
		for (StockDurationTransfer item : tfrLoadExp) {
			StockDurationTransfer info = attchTfr(hasForkArea, item);
			stockDurationDao.insertTfrExp(info);
		}
	}

	/**
	 * @desc 处理快递派送货物
	 * @param tfrCtrCode
	 * @param tfrCtrName
	 * @param staDate
	 * @param endDate
	 * @param staMonth
	 * @param hasForkArea
	 * @date 2015年3月24日 下午5:51:55
	 * @author Ouyang
	 */
	private void handleDptExp(String tfrCtrCode, String tfrCtrName,
			Date staDate, Date endDate, int staMonth, boolean hasForkArea) {
		// 查询外场某天快递货派送装车数据
		// 此接口已填充tfrCtrCode,tfrCtrName,waybillNo,staDate,staMonth,receiveMethod,dptLoadScanTime,dptLoadTaskSubmitTime属性
		List<StockDurationDispatch> dptLoadExp = stockDurationDao
				.findDptLoadExp(tfrCtrCode, tfrCtrName, staDate, endDate,
						staMonth);
		// 循环所有运单，填充各种时间
		for (StockDurationDispatch item : dptLoadExp) {
			StockDurationDispatch info = attchDpt(hasForkArea, item);
			stockDurationDao.insertDptExp(info);
		}
	}

	/**
	 * @desc 快递日数据汇总
	 * @param info
	 * @param hasForkArea
	 * @date 2015年3月26日 上午11:42:30
	 * @author Ouyang
	 */
	private void handleTotalExpD(StockDuration info, boolean hasForkArea) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tfrCtrCode", info.getTfrCtrCode());
		map.put("staDate", info.getStaDate());

		// 卸车等待时长
		BigDecimal unloadWaitTime = null;
		// 卸车等待票数
		BigDecimal unloadWaitVote = null;
		// 平均卸车等待时长
		BigDecimal avgUnloadWaitTime = null;
		// 查询卸车等待时长
		Map<String, BigDecimal> unloadWaitTimeExp = stockDurationDao
				.findUnloadWaitTimeExp(map);
		if (unloadWaitTimeExp != null) {
			// 卸车等待时长
			unloadWaitTime = unloadWaitTimeExp.get("UNLOAD_WAIT_TIME");
			// 卸车等待票数
			unloadWaitVote = unloadWaitTimeExp.get("UNLOAD_WAIT_VOTE");
			// 平均卸车等待时长
			avgUnloadWaitTime = unloadWaitTimeExp.get("AVG_UNLOAD_WAIT_TIME");
		}

		// 卸车时长
		BigDecimal unloadTime = null;
		// 卸车票数
		BigDecimal unloadVote = null;
		// 平均卸车时长
		BigDecimal avgUnloadTime = null;
		// 查询卸车时长
		Map<String, BigDecimal> unloadTimeExp = stockDurationDao
				.findUnloadTimeExp(map);
		if (unloadTimeExp != null) {
			// 卸车时长
			unloadTime = unloadTimeExp.get("UNLOAD_TIME");
			// 卸车票数
			unloadVote = unloadTimeExp.get("UNLOAD_VOTE");
			// 平均卸车时长
			avgUnloadTime = unloadTimeExp.get("AVG_UNLOAD_TIME");
		}

		// 待叉区停留时长
		BigDecimal forkAreaStayTime = null;
		// 待叉区停留票数
		BigDecimal forkAreaStayVote = null;
		// 平均待叉区停留时长
		BigDecimal avgForkAreaStayTime = null;
		// 查询待叉区停留时长
		if (hasForkArea) {
			Map<String, BigDecimal> forkAreaStayTimeExp = stockDurationDao
					.findForkAreaStayTimeExp(map);
			if (forkAreaStayTimeExp != null) {
				// 待叉区停留时长
				forkAreaStayTime = forkAreaStayTimeExp
						.get("FORK_AREA_STAY_TIME");
				// 待叉区停留票数
				forkAreaStayVote = forkAreaStayTimeExp
						.get("FORK_AREA_STAY_VOTE");
				// 平均待叉区停留时长
				avgForkAreaStayTime = forkAreaStayTimeExp
						.get("AVG_FORK_AREA_STAY_TIME");
			}
		}

		// 包装库区停留时长
		BigDecimal pkgAreaStayTime = null;
		// 包装库区停留票数
		BigDecimal pkgAreaStayVote = null;
		// 平均包装库区停留时长
		BigDecimal avgPkgAreaStayTime = null;
		// 查询包装库区停留时长
		Map<String, BigDecimal> pkgAreaStayTimeExp = stockDurationDao
				.findPkgAreaStayTimeExp(map);
		if (pkgAreaStayTimeExp != null) {
			// 包装库区停留时长
			pkgAreaStayTime = pkgAreaStayTimeExp.get("PKG_AREA_STAY_TIME");
			// 包装库区停留票数
			pkgAreaStayVote = pkgAreaStayTimeExp.get("PKG_AREA_STAY_VOTE");
			// 平均包装库区停留时长
			avgPkgAreaStayTime = pkgAreaStayTimeExp
					.get("AVG_PKG_AREA_STAY_TIME");
		}

		// 中转库区停留时长
		BigDecimal tfrAreaTime = null;
		// 中转库区停留票数
		BigDecimal tfrAreaVote = null;
		// 平均中转库区停留时长
		BigDecimal avgTfrAreaTime = null;
		// 查询中转库区停留时长
		Map<String, BigDecimal> tfrAreaTimeExp = stockDurationDao
				.findTfrAreaTimeExp(map);
		if (tfrAreaTimeExp != null) {
			// 中转库区停留时长
			tfrAreaTime = tfrAreaTimeExp.get("TFR_AREA_TIME");
			// 中转库区停留票数
			tfrAreaVote = tfrAreaTimeExp.get("TFR_AREA_VOTE");
			// 平均中转库区停留时长
			avgTfrAreaTime = tfrAreaTimeExp.get("AVG_TFR_AREA_TIME");
		}

		// 派送库区时长
		BigDecimal dptAreaTime = null;
		// 派送库区票数
		BigDecimal dptAreaVote = null;
		// 平均派送库区时长
		BigDecimal avgDptAreaTime = null;

		// 查询派送库区时长
		Map<String, BigDecimal> dptAreaTimeExp = stockDurationDao
				.findDptAreaTimeExp(map);
		if (dptAreaTimeExp != null) {
			// 派送库区时长
			dptAreaTime = dptAreaTimeExp.get("DPT_AREA_TIME");
			// 派送库区票数
			dptAreaVote = dptAreaTimeExp.get("DPT_AREA_VOTE");
			// 平均派送库区时长
			avgDptAreaTime = dptAreaTimeExp.get("AVG_DPT_AREA_TIME");
		}

		// 填充各属性
		info.setUnloadWaitTime(unloadWaitTime);
		info.setUnloadWaitVote(unloadWaitVote);

		info.setUnloadTime(unloadTime);
		info.setUnloadVote(unloadVote);

		info.setForkAreaStayTime(forkAreaStayTime);
		info.setForkAreaStayVote(forkAreaStayVote);

		info.setPkgAreaStayTime(pkgAreaStayTime);
		info.setPkgAreaStayVote(pkgAreaStayVote);

		info.setTfrAreaTime(tfrAreaTime);
		info.setTfrAreaVote(tfrAreaVote);

		info.setDptAreaTime(dptAreaTime);
		info.setDptAreaVote(dptAreaVote);

		info.setAvgUnloadWaitTime(avgUnloadWaitTime);
		info.setAvgUnloadTime(avgUnloadTime);
		info.setAvgForkAreaStayTime(avgForkAreaStayTime);
		info.setAvgPkgAreaStayTime(avgPkgAreaStayTime);
		info.setAvgTfrAreaTime(avgTfrAreaTime);
		info.setAvgDptAreaTime(avgDptAreaTime);

		info.setId(UUIDUtils.getUUID());

		stockDurationDao.insertExpDay(info);
	}

	/**
	 * @desc 填充中转货物各种时间
	 * @param hasForkArea
	 * @param info
	 * @return
	 * @date 2015年3月24日 下午5:51:46
	 * @author Ouyang
	 */
	private StockDurationTransfer attchTfr(boolean hasForkArea,
			StockDurationTransfer info) {
		// 外场编码
		String tfrCtrCode = info.getTfrCtrCode();
		// 运单号
		String waybillNo = info.getWaybillNo();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("waybillNo", waybillNo);

		// 运单下一部门
		String nextOrgCode = null;
		String nextOrgName = null;
		Map<String, String> nextOrg = stockDurationDao.findNextOrg(map);
		if (nextOrg != null) {
			nextOrgCode = nextOrg.get("NEXT_ORG_CODE");
			nextOrgName = nextOrg.get("NEXT_ORG_NAME");
		}

		// 运单到达本外场时间
		Date arrivedTime = stockDurationDao.findArrivedTime(map);

		// 运单在外场的卸车任务开始时间和卸车扫描时间
		Date unloadTaskBeginTime = null;
		Date unloadScanTime = null;
		Dates4StockDurationDto unloadTaskBeginAndScanTime = stockDurationDao
				.findUnloadTaskBeginAndScanTime(map);
		if (unloadTaskBeginAndScanTime != null) {
			unloadTaskBeginTime = unloadTaskBeginAndScanTime
					.getUnloadTaskBeginTime();
			unloadScanTime = unloadTaskBeginAndScanTime.getUnloadScanTime();
		}

		// 托盘绑定时间和叉车扫描时间
		Date trayBindingTime = null;
		Date forkliftScanTime = null;
		if (hasForkArea) {
			Dates4StockDurationDto trayBindingAndForkliftScanTime = stockDurationDao
					.findTrayBindingAndForkliftScanTime(map);
			if (trayBindingAndForkliftScanTime != null) {
				trayBindingTime = trayBindingAndForkliftScanTime
						.getTrayBindingTime();
				forkliftScanTime = trayBindingAndForkliftScanTime
						.getForkliftScanTime();
			}
		}

		// 出包装库区时间
		Date outPkgAreaTime = stockDurationDao.findOutPkgAreaTime(map);

		// 临时变量，有叉车扫描时间取叉车扫描时间，否则取卸车扫描时间
		Date tmpDate = forkliftScanTime != null ? forkliftScanTime
				: unloadScanTime;

		// 入包装库区时间
		Date inPkgAreaTime = null;
		if (outPkgAreaTime != null) {
			inPkgAreaTime = tmpDate;
		}

		// 入中转库区时间
		Date inTfrAreaTime = outPkgAreaTime != null ? outPkgAreaTime : tmpDate;

		// 填充各属性
		info.setNextOrgCode(nextOrgCode);
		info.setNextOrgName(nextOrgName);
		info.setArrivedTime(arrivedTime);
		info.setUnloadTaskBeginTime(unloadTaskBeginTime);
		info.setUnloadScanTime(unloadScanTime);
		info.setTrayBindingTime(trayBindingTime);
		info.setForkliftScanTime(forkliftScanTime);
		info.setInPkgAreaTime(inPkgAreaTime);
		info.setOutPkgAreaTime(outPkgAreaTime);
		info.setInTfrAreaTime(inTfrAreaTime);

		info.setId(UUIDUtils.getUUID());
		return info;
	}

	/**
	 * @desc 填充派送货物各种时间
	 * @param hasForkArea
	 * @param info
	 * @return
	 * @date 2015年3月24日 下午5:52:11
	 * @author Ouyang
	 */
	private StockDurationDispatch attchDpt(boolean hasForkArea,
			StockDurationDispatch info) {
		// 外场编码
		String tfrCtrCode = info.getTfrCtrCode();
		// 运单号
		String waybillNo = info.getWaybillNo();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("waybillNo", waybillNo);

		// 运单上一部门
		String preOrgCode = null;
		String preOrgName = null;
		Map<String, String> preOrg = stockDurationDao.findPreOrg(map);
		if (preOrg != null) {
			preOrgCode = preOrg.get("PRE_ORG_CODE");
			preOrgName = preOrg.get("PRE_ORG_NAME");
		}

		// 运单到达本外场时间
		Date arrivedTime = stockDurationDao.findArrivedTime(map);

		// 运单在外场的卸车任务开始时间和卸车扫描时间
		Date unloadTaskBeginTime = null;
		Date unloadScanTime = null;
		Dates4StockDurationDto unloadTaskBeginAndScanTime = stockDurationDao
				.findUnloadTaskBeginAndScanTime(map);
		if (unloadTaskBeginAndScanTime != null) {
			unloadTaskBeginTime = unloadTaskBeginAndScanTime
					.getUnloadTaskBeginTime();
			unloadScanTime = unloadTaskBeginAndScanTime.getUnloadScanTime();
		}

		// 托盘绑定时间和叉车扫描时间
		Date trayBindingTime = null;
		Date forkliftScanTime = null;
		if (hasForkArea) {
			Dates4StockDurationDto trayBindingAndForkliftScanTime = stockDurationDao
					.findTrayBindingAndForkliftScanTime(map);
			if (trayBindingAndForkliftScanTime != null) {
				trayBindingTime = trayBindingAndForkliftScanTime
						.getTrayBindingTime();
				forkliftScanTime = trayBindingAndForkliftScanTime
						.getForkliftScanTime();
			}
		}

		// 出包装库区时间
		Date outPkgAreaTime = stockDurationDao.findOutPkgAreaTime(map);

		// 临时变量，有叉车扫描时间取叉车扫描时间，否则取卸车扫描时间
		Date tmpDate = forkliftScanTime != null ? forkliftScanTime
				: unloadScanTime;

		// 入包装库区时间
		Date inPkgAreaTime = null;
		if (outPkgAreaTime != null) {
			inPkgAreaTime = tmpDate;
		}

		// 入派送库区时间
		Date inDptAreaTime = outPkgAreaTime != null ? outPkgAreaTime : tmpDate;

		// 运单签收时间
		Date signTime = stockDurationDao.findSignTime(waybillNo);

		// 填充各属性
		info.setPreOrgCode(preOrgCode);
		info.setPreOrgName(preOrgName);
		info.setArrivedTime(arrivedTime);
		info.setUnloadTaskBeginTime(unloadTaskBeginTime);
		info.setUnloadScanTime(unloadScanTime);
		info.setTrayBindingTime(trayBindingTime);
		info.setForkliftScanTime(forkliftScanTime);
		info.setInPkgAreaTime(inPkgAreaTime);
		info.setOutPkgAreaTime(outPkgAreaTime);
		info.setInDptAreaTime(inDptAreaTime);
		info.setSignTime(signTime);

		info.setId(UUIDUtils.getUUID());
		return info;
	}

	/**
	 * @desc 生成零担货库存时长数据
	 * @param threadCount
	 * @param threadNo
	 * @date 2015年3月24日 下午5:51:07
	 * @author Ouyang
	 */
	@Override
	public void generateLtc(Integer threadCount, Integer threadNo) {
		// 比如2015-03-24号跑的是2015-03-23~2015-03-24的出库数据，staDate记为2015-03-23,endDate记为2015-03-24
		Date staDate = DateUtils.getStartDatetime(new Date(), -1);
		Date endDate = DateUtils.addDayToDate(staDate, 1);

		// 统计月份
		int staMonth = Integer.valueOf(
				new SimpleDateFormat("yyyyMM").format(staDate)).intValue();

		// 查询所有非分部外场，及是否含待叉区
		List<Map<String, String>> tfrCtrs = stockDurationDao.findTfrCtrs(
				threadCount, threadNo);

		for (Map<String, String> item : tfrCtrs) {
			String tfrCtrCode = item.get("TFR_CTR_CODE");
			String tfrCtrName = item.get("TFR_CTR_NAME");
			boolean hasForkArea = FossConstants.ACTIVE.equals(item
					.get("HAS_FORK_AREA"));

			// 处理零担中转货物
			handleTfrLtc(tfrCtrCode, tfrCtrName, staDate, endDate, staMonth,
					hasForkArea);

			// 处理零担派送货物
			handleDptLtc(tfrCtrCode, tfrCtrName, staDate, endDate, staMonth,
					hasForkArea);

			// 零担数据汇总
			StockDuration info = new StockDuration();
			info.setTfrCtrCode(tfrCtrCode);
			info.setTfrCtrName(tfrCtrName);
			info.setStaDate(staDate);
			info.setStaMonth(staMonth);
			handleTotalLtcD(info, hasForkArea);

			// 快递数据月汇总
			stockDurationDao.insertLtcMonth(tfrCtrCode, staDate, staMonth);
		}
	}

	/**
	 * @desc 处理零担中转货物
	 * @param tfrCtrCode
	 * @param tfrCtrName
	 * @param staDate
	 * @param endDate
	 * @param staMonth
	 * @param hasForkArea
	 * @date 2015年3月24日 下午5:51:35
	 * @author Ouyang
	 */
	private void handleTfrLtc(String tfrCtrCode, String tfrCtrName,
			Date staDate, Date endDate, int staMonth, boolean hasForkArea) {
		// 查询外场某天零担货中转装车数据
		// 此接口已填充tfrCtrCode,tfrCtrName,waybillNo,staDate,staMonth,tfrLoadScanTime,tfrLoadTaskSubmitTime属性
		List<StockDurationTransfer> tfrLoadLtc = stockDurationDao
				.findTfrLoadLtc(tfrCtrCode, tfrCtrName, staDate, endDate,
						staMonth);
		// 循环所有运单，填充各种时间
		for (StockDurationTransfer item : tfrLoadLtc) {
			StockDurationTransfer info = attchTfr(hasForkArea, item);
			stockDurationDao.insertTfrLtc(info);
		}
	}

	/**
	 * @desc 处理零担派送货物
	 * @param tfrCtrCode
	 * @param tfrCtrName
	 * @param staDate
	 * @param endDate
	 * @param staMonth
	 * @param hasForkArea
	 * @date 2015年3月24日 下午5:52:03
	 * @author Ouyang
	 */
	private void handleDptLtc(String tfrCtrCode, String tfrCtrName,
			Date staDate, Date endDate, int staMonth, boolean hasForkArea) {
		// 查询外场某天快递货派送装车数据
		// 此接口已填充tfrCtrCode,tfrCtrName,waybillNo,staDate,staMonth,receiveMethod,dptLoadScanTime,dptLoadTaskSubmitTime属性
		List<StockDurationDispatch> dptLoadExp = stockDurationDao
				.findDptLoadLtc(tfrCtrCode, tfrCtrName, staDate, endDate,
						staMonth);
		// 循环所有运单，填充各种时间
		for (StockDurationDispatch item : dptLoadExp) {
			StockDurationDispatch info = attchDpt(hasForkArea, item);
			stockDurationDao.insertDptLtc(info);
		}
	}

	/**
	 * @desc 零担日数据汇总
	 * @param info
	 * @param hasForkArea
	 * @date 2015年3月26日 上午11:42:48
	 * @author Ouyang
	 */
	private void handleTotalLtcD(StockDuration info, boolean hasForkArea) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tfrCtrCode", info.getTfrCtrCode());
		map.put("staDate", info.getStaDate());

		// 查询卸车等待时长
		BigDecimal unloadWaitTime = null;
		BigDecimal unloadWaitVote = null;
		BigDecimal avgUnloadWaitTime = null;
		Map<String, BigDecimal> unloadWaitTimeLtc = stockDurationDao
				.findUnloadWaitTimeLtc(map);
		if (unloadWaitTimeLtc != null) {
			// 卸车等待时长
			unloadWaitTime = unloadWaitTimeLtc.get("UNLOAD_WAIT_TIME");
			// 卸车等待票数
			unloadWaitVote = unloadWaitTimeLtc.get("UNLOAD_WAIT_VOTE");
			// 平均卸车等待时长
			avgUnloadWaitTime = unloadWaitTimeLtc.get("AVG_UNLOAD_WAIT_TIME");
		}

		// 查询卸车时长
		BigDecimal unloadTime = null;
		BigDecimal unloadVote = null;
		BigDecimal avgUnloadTime = null;
		Map<String, BigDecimal> unloadTimeLtc = stockDurationDao
				.findUnloadTimeLtc(map);
		if (unloadTimeLtc != null) {
			// 卸车时长
			unloadTime = unloadTimeLtc.get("UNLOAD_TIME");
			// 卸车票数
			unloadVote = unloadTimeLtc.get("UNLOAD_VOTE");
			// 平均卸车时长
			avgUnloadTime = unloadTimeLtc.get("AVG_UNLOAD_TIME");
		}

		// 查询待叉区停留时长
		BigDecimal forkAreaStayTime = null;
		BigDecimal forkAreaStayVote = null;
		BigDecimal avgForkAreaStayTime = null;
		if (hasForkArea) {
			Map<String, BigDecimal> forkAreaStayTimeLtc = stockDurationDao
					.findForkAreaStayTimeLtc(map);
			if (forkAreaStayTimeLtc != null) {
				// 待叉区停留时长
				forkAreaStayTime = forkAreaStayTimeLtc
						.get("FORK_AREA_STAY_TIME");
				// 待叉区停留票数
				forkAreaStayVote = forkAreaStayTimeLtc
						.get("FORK_AREA_STAY_VOTE");
				// 平均待叉区停留时长
				avgForkAreaStayTime = forkAreaStayTimeLtc
						.get("AVG_FORK_AREA_STAY_TIME");
			}
		}

		// 查询包装库区停留时长
		// 包装库区停留时长
		BigDecimal pkgAreaStayTime = null;
		// 包装库区停留票数
		BigDecimal pkgAreaStayVote = null;
		// 平均包装库区停留时长
		BigDecimal avgPkgAreaStayTime = null;
		Map<String, BigDecimal> pkgAreaStayTimeLtc = stockDurationDao
				.findPkgAreaStayTimeLtc(map);
		if (pkgAreaStayTimeLtc != null) {
			// 包装库区停留时长
			pkgAreaStayTime = pkgAreaStayTimeLtc.get("PKG_AREA_STAY_TIME");
			// 包装库区停留票数
			pkgAreaStayVote = pkgAreaStayTimeLtc.get("PKG_AREA_STAY_VOTE");
			// 平均包装库区停留时长
			avgPkgAreaStayTime = pkgAreaStayTimeLtc
					.get("AVG_PKG_AREA_STAY_TIME");
		}

		// 查询中转库区停留时长
		// 中转库区停留时长
		BigDecimal tfrAreaTime = null;
		// 中转库区停留票数
		BigDecimal tfrAreaVote = null;
		// 平均中转库区停留时长
		BigDecimal avgTfrAreaTime = null;
		Map<String, BigDecimal> tfrAreaTimeLtc = stockDurationDao
				.findTfrAreaTimeLtc(map);
		if (tfrAreaTimeLtc != null) {
			// 中转库区停留时长
			tfrAreaTime = tfrAreaTimeLtc.get("TFR_AREA_TIME");
			// 中转库区停留票数
			tfrAreaVote = tfrAreaTimeLtc.get("TFR_AREA_VOTE");
			// 平均中转库区停留时长
			avgTfrAreaTime = tfrAreaTimeLtc.get("AVG_TFR_AREA_TIME");
		}

		// 查询派送库区时长
		// 派送库区时长
		BigDecimal dptAreaTime = null;
		// 派送库区票数
		BigDecimal dptAreaVote = null;
		// 平均派送库区时长
		BigDecimal avgDptAreaTime = null;
		Map<String, BigDecimal> dptAreaTimeLtc = stockDurationDao
				.findDptAreaTimeLtc(map);
		if (dptAreaTimeLtc != null) {
			// 派送库区时长
			dptAreaTime = dptAreaTimeLtc.get("DPT_AREA_TIME");
			// 派送库区票数
			dptAreaVote = dptAreaTimeLtc.get("DPT_AREA_VOTE");
			// 平均派送库区时长
			avgDptAreaTime = dptAreaTimeLtc.get("AVG_DPT_AREA_TIME");
		}

		// 填充各属性
		info.setUnloadWaitTime(unloadWaitTime);
		info.setUnloadWaitVote(unloadWaitVote);

		info.setUnloadTime(unloadTime);
		info.setUnloadVote(unloadVote);

		info.setForkAreaStayTime(forkAreaStayTime);
		info.setForkAreaStayVote(forkAreaStayVote);

		info.setPkgAreaStayTime(pkgAreaStayTime);
		info.setPkgAreaStayVote(pkgAreaStayVote);

		info.setTfrAreaTime(tfrAreaTime);
		info.setTfrAreaVote(tfrAreaVote);

		info.setDptAreaTime(dptAreaTime);
		info.setDptAreaVote(dptAreaVote);

		info.setAvgUnloadWaitTime(avgUnloadWaitTime);
		info.setAvgUnloadTime(avgUnloadTime);
		info.setAvgForkAreaStayTime(avgForkAreaStayTime);
		info.setAvgPkgAreaStayTime(avgPkgAreaStayTime);
		info.setAvgTfrAreaTime(avgTfrAreaTime);
		info.setAvgDptAreaTime(avgDptAreaTime);

		info.setId(UUIDUtils.getUUID());

		stockDurationDao.insertLtcDay(info);
	}

	/**
	 * @desc 查询快递数据日汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:06
	 * @author Ouyang
	 */
	@Override
	public List<StockDuration> findExpDay(StockDurationQcDto parameter) {
		return stockDurationDao.findExpDay(parameter);
	}

	/**
	 * @desc 查询快递数据月汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:09
	 * @author Ouyang
	 */
	@Override
	public List<StockDuration> findExpMonth(StockDurationQcDto parameter) {
		return stockDurationDao.findExpMonth(parameter);
	}

	/**
	 * @desc 查询零担数据日汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:11
	 * @author Ouyang
	 */
	@Override
	public List<StockDuration> findLtcDay(StockDurationQcDto parameter) {
		return stockDurationDao.findLtcDay(parameter);
	}

	/**
	 * @desc 查询零担数据月汇总
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:20:14
	 * @author Ouyang
	 */
	@Override
	public List<StockDuration> findLtcMonth(StockDurationQcDto parameter) {
		return stockDurationDao.findLtcMonth(parameter);
	}

	/**
	 * @desc 查询快递中转数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	@Override
	public List<StockDurationTransfer> findTfrExp(StockDurationQcDto parameter,
			int start, int limit) {
		return stockDurationDao.findTfrExp(parameter, start, limit);
	}

	/**
	 * @desc 查询零担中转数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	@Override
	public List<StockDurationTransfer> findTfrLtc(StockDurationQcDto parameter,
			int start, int limit) {
		return stockDurationDao.findTfrLtc(parameter, start, limit);
	}

	/**
	 * @desc 查询快递派送数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	@Override
	public List<StockDurationDispatch> findDptExp(StockDurationQcDto parameter,
			int start, int limit) {
		return stockDurationDao.findDptExp(parameter, start, limit);
	}

	/**
	 * @desc 查询零担派送数据
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	@Override
	public List<StockDurationDispatch> findDptLtc(StockDurationQcDto parameter,
			int start, int limit) {
		return stockDurationDao.findDptLtc(parameter, start, limit);
	}

	/**
	 * @desc 查询快递中转数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	@Override
	public Long findTfrExpCnt(StockDurationQcDto parameter) {
		return stockDurationDao.findTfrExpCnt(parameter);
	}

	/**
	 * @desc 查询零担中转数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	@Override
	public Long findTfrLtcCnt(StockDurationQcDto parameter) {
		return stockDurationDao.findTfrLtcCnt(parameter);
	}

	/**
	 * @desc 查询快递派送数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	@Override
	public Long findDptExpCnt(StockDurationQcDto parameter) {
		return stockDurationDao.findDptExpCnt(parameter);
	}

	/**
	 * @desc 查询零担派送数据总数
	 * @param parameter
	 * @return
	 * @date 2015年3月26日 下午4:40:00
	 * @author Ouyang
	 */
	@Override
	public Long findDptLtcCnt(StockDurationQcDto parameter) {
		return stockDurationDao.findDptLtcCnt(parameter);
	}

	/**
	 * @desc 查询部门所属外场
	 * @param orgCode
	 * @return
	 * @date 2015年3月26日 下午5:32:15
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

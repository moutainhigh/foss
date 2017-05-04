package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.deppon.foss.module.transfer.platform.api.server.dao.ICockpitDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ICockpitService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CockpitResultDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.CockpitVo;

public class CockpitService implements ICockpitService {

	private static final Logger LOGGER = Logger.getLogger(CockpitService.class);

	private ICockpitDao cockpitDao;

	public void setCockpitDao(ICockpitDao cockpitDao) {
		this.cockpitDao = cockpitDao;
	}

	/**
	 * @description 驾驶舱查询
	 * @param orgCode
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2015年9月2日 下午3:24:01
	 */
	@Override
	public CockpitVo queryCockpitByOrgCode(String orgCode) {
		return cockpitDao.queryCockpitByOrgCode(orgCode);
	}

	/**
	 * @desc 驾驶舱查询
	 * @param tfrCtrCode
	 * @return
	 * @date 2015年8月11日下午4:17:46
	 */
	@Override
	public CockpitResultDto findCockpitResult(String tfrCtrCode) {

		// 返回结果
		CockpitResultDto result = new CockpitResultDto();

		// 当前时间
		Date currentTime = new Date();

		result.setCurrentTime(currentTime);
		
		if (tfrCtrCode == null) {
			return result;
		}

		// 查询参数
		Map<String, Object> map = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_2);
		map.put("tfrCtrCode", tfrCtrCode);
		map.put("currentTime", currentTime);

		Map<Integer, Future<String>> futures = new HashMap<Integer, Future<String>>();
		
		final int poolSize = PlatformConstants.SONAR_NUMBER_15;

		ExecutorService pool = Executors.newFixedThreadPool(poolSize);
		try {
			for (int i = PlatformConstants.SONAR_NUMBER_1; i <= poolSize; i++) {
				Callable<String> task = new Task(i, map);
				Future<String> future = pool.submit(task);

				futures.put(i, future);
			}
		} finally {
			pool.shutdown();
		}

		for (int i = PlatformConstants.SONAR_NUMBER_1, j = futures.size(); i <= j; i++) {
			try {
				handleResult(i, futures.get(i).get(PlatformConstants.SONAR_NUMBER_5, TimeUnit.MINUTES), result);
			} catch (Exception e) {
				LOGGER.info("~~~驾驶舱查询~~~" + e);
				StackTraceElement[] trace = e.getStackTrace();
				for (int index = PlatformConstants.SONAR_NUMBER_0; index < trace.length; index++) {
					LOGGER.info("\tat " + trace[index]);
				}
			}
		}

		return result;
	}

	private void handleResult(int index, String prop, CockpitResultDto result) {
		switch (index) {
		case PlatformConstants.SONAR_NUMBER_1:
			result.setOnDutyNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_2:
			result.setAbsenteeNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_3:
			result.setTallyNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_4:
			result.setForkNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_5:
			result.setStockWeight(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_6:
			result.setWaitUnloadWeight(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_7:
			result.setLngDisOnTheWayNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_8:
			result.setShtDisOnTheWayNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_9:
			result.setLngDisArrivedNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_10:
			result.setShtDisArrivedNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_11:
			result.setLoadUnloadProgressAbnormalNums(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_12:
			result.setPlatformUsageRate(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_13:
			result.setDispatchStockVolume(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_14:
			result.setSendBackPct(prop);
			break;
		case PlatformConstants.SONAR_NUMBER_15:
			result.setStockSaturation(prop);
			break;
		default:
			break;
		}
	}

	class Task implements Callable<String> {

		private int index;
		private Map<String, Object> map;

		public Task(int index, Map<String, Object> map) {
			this.index = index;
			this.map = map;
		}

		@Override
		public String call() throws Exception {
			String result = null;

			switch (index) {
			case PlatformConstants.SONAR_NUMBER_1:
				result = cockpitDao.onDutyNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_2:
				result = cockpitDao.absenteeNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_3:
				result = cockpitDao.tallyNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_4:
				result = cockpitDao.eForkNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_5:
				result = cockpitDao.stockWeight(map);
				break;
			case PlatformConstants.SONAR_NUMBER_6:
				result = cockpitDao.waitUnloadWeight(map);
				break;
			case PlatformConstants.SONAR_NUMBER_7:
				result = cockpitDao.lngDisOnTheWayNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_8:
				result = cockpitDao.shtDisOnTheWayNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_9:
				result = cockpitDao.lngDisArrivedNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_10:
				result = cockpitDao.shtDisArrivedNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_11:
				result = cockpitDao.loadUnloadProgressAbnormalNums(map);
				break;
			case PlatformConstants.SONAR_NUMBER_12:
				result = cockpitDao.platformUsageRate(map);
				break;
			case PlatformConstants.SONAR_NUMBER_13:
				result = cockpitDao.dispatchStockVolume(map);
				break;
			case PlatformConstants.SONAR_NUMBER_14:
				result = cockpitDao.sendBackPct(map);
				break;
			case PlatformConstants.SONAR_NUMBER_15:
				result = cockpitDao.stockSaturation(map);
				break;
			default:
				result = null;
				break;
			}

			return result;
		}

	}
}

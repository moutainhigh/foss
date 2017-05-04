package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.server.dao.IDensityDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IDensityService;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.DptAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrDensityPeakEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityTrendDto;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.util.DateUtils;

public class DensityService implements IDensityService {

	private IDensityDao densityDao;

	private IPlatformCommonService platformCommonService;

	public void setDensityDao(IDensityDao densityDao) {
		this.densityDao = densityDao;
	}

	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}

	/**
	 * @desc 每小时时间各种库区密度
	 * @param threadCount
	 * @param threadNo
	 * @date 2015年4月7日 上午11:14:37
	 * @author Ouyang
	 */
	@Override
	public void generateDensityHour(Integer threadCount, Integer threadNo) {

		Date date = new Date();
		Date staDate = DateUtils.getStartDatetime(date);
		int staMonth = Integer.valueOf(PlatformUtils.formatDate2String(date,
				"yyyyMM"));
		int staHour = Integer.valueOf(PlatformUtils.formatDate2String(date,
				"HH"));

		// 查询所有非分部外场
		List<Map<String, String>> tfrCtrs = densityDao.findTfrCtrs(threadCount,
				threadNo);
		// 循环所有外场
		for (Map<String, String> info : tfrCtrs) {
			// 外场编码、名称
			String tfrCtrCode = info.get("TFR_CTR_CODE");
			String tfrCtrName = info.get("TFR_CTR_NAME");
			// 查询外场所有经营本部
			Map<String, String> hq = platformCommonService
					.findSupHq(tfrCtrCode);
			// 经营本部编码、名称
			String hqCode = hq.get("HQ_CODE");
			String hqName = hq.get("HQ_NAME");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hqCode", hqCode);
			map.put("hqName", hqName);
			map.put("tfrCtrCode", tfrCtrCode);
			map.put("tfrCtrName", tfrCtrName);
			map.put("staDate", staDate);
			map.put("staMonth", staMonth);
			map.put("staHour", staHour);
			densityDao.generateDensityHour(map);
		}
	}

	/**
	 * @desc 每天计算各种库区密度峰值
	 * @param threadCount
	 * @param threadNo
	 * @date 2015年4月7日 上午11:14:29
	 * @author Ouyang
	 */
	@Override
	public void generateDensityDay(Integer threadCount, Integer threadNo) {
		Date date = new Date();
		// 统计日期
		Date staDate = DateUtils.getStartDatetime(DateUtils.addDayToDate(date,
				-1));
		// 统计月份
		int staMonth = Integer.valueOf(PlatformUtils.formatDate2String(date,
				"yyyyMM"));
		// 查询所有非分部外场
		List<Map<String, String>> tfrCtrs = densityDao.findTfrCtrs(threadCount,
				threadNo);
		// 循环所有外场
		for (Map<String, String> info : tfrCtrs) {
			// 外场编码、名称
			String tfrCtrCode = info.get("TFR_CTR_CODE");
			String tfrCtrName = info.get("TFR_CTR_NAME");
			// 查询外场所有经营本部
			Map<String, String> hq = platformCommonService
					.findSupHq(tfrCtrCode);
			// 经营本部编码、名称
			String hqCode = hq.get("HQ_CODE");
			String hqName = hq.get("HQ_NAME");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hqCode", hqCode);
			map.put("hqName", hqName);
			map.put("tfrCtrCode", tfrCtrCode);
			map.put("tfrCtrName", tfrCtrName);
			map.put("staDate", staDate);
			map.put("staMonth", staMonth);
			densityDao.generateDensityDay(map);
		}
	}

	/**
	 * @desc 查询外场密度峰值
	 * @param dto
	 * @return
	 * @date 2015年4月3日 下午3:44:22
	 * @author Ouyang
	 */
	@Override
	public List<TfrCtrDensityPeakEntity> findTfrCtrDensityPeak(DensityQcDto dto) {
		return densityDao.findTfrCtrDensityPeak(dto);
	}

	/**
	 * @desc 待叉区密度查询
	 * @param dto
	 * @return
	 * @date 2015年4月3日 下午3:45:37
	 * @author Ouyang
	 */
	@Override
	public List<ForkAreaDensityEntity> findForkAreaDensity(DensityQcDto dto) {
		return densityDao.findForkAreaDensity(dto);
	}

	/**
	 * @desc 派送库区密度查询
	 * @param dto
	 * @return
	 * @date 2015年4月3日 下午3:45:50
	 * @author Ouyang
	 */
	@Override
	public List<DptAreaDensityEntity> findDptAreaDensity(DensityQcDto dto) {
		return densityDao.findDptAreaDensity(dto);
	}

	/**
	 * @desc 中转库区密度查询
	 * @param dto
	 * @return
	 * @date 2015年4月3日 下午3:46:01
	 * @author Ouyang
	 */
	@Override
	public List<TfrAreaDensityEntity> findTfrAreaDensity(DensityQcDto dto) {
		return densityDao.findTfrAreaDensity(dto);
	}

	/**
	 * @desc 日密度趋势查询
	 * @param dto
	 * @return
	 * @date 2015年4月14日 上午11:17:35
	 * @author Ouyang
	 */
	@Override
	public List<DensityTrendDto> findTrendDay(DensityQcDto dto) {
		return densityDao.findTrendDay(dto);
	}

	/**
	 * @desc 月密度趋势查询
	 * @param dto
	 * @return
	 * @date 2015年4月14日 上午11:17:35
	 * @author Ouyang
	 */
	@Override
	public List<DensityTrendDto> findTrendMonth(DensityQcDto dto) {
		return densityDao.findTrendMonth(dto);
	}

}

package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.DptAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrDensityPeakEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityTrendDto;

public interface IDensityService {

	/**
	 * @desc 每小时时间各种库区密度
	 * @param threadCount
	 * @param threadNo
	 * @date 2015年4月7日 上午11:14:37
	 * @author Ouyang
	 */
	void generateDensityHour(Integer threadCount, Integer threadNo);

	/**
	 * @desc 每天计算各种库区密度峰值
	 * @param threadCount
	 * @param threadNo
	 * @date 2015年4月7日 上午11:14:29
	 * @author Ouyang
	 */
	void generateDensityDay(Integer threadCount, Integer threadNo);

	/**
	 * @desc 查询外场密度峰值
	 * @param dto
	 * @return
	 * @date 2015年4月3日 下午3:44:22
	 * @author Ouyang
	 */
	List<TfrCtrDensityPeakEntity> findTfrCtrDensityPeak(DensityQcDto dto);

	/**
	 * @desc 待叉区密度查询
	 * @param dto
	 * @return
	 * @date 2015年4月3日 下午3:45:37
	 * @author Ouyang
	 */
	List<ForkAreaDensityEntity> findForkAreaDensity(DensityQcDto dto);

	/**
	 * @desc 派送库区密度查询
	 * @param dto
	 * @return
	 * @date 2015年4月3日 下午3:45:50
	 * @author Ouyang
	 */
	List<DptAreaDensityEntity> findDptAreaDensity(DensityQcDto dto);

	/**
	 * @desc 中转库区密度查询
	 * @param dto
	 * @return
	 * @date 2015年4月3日 下午3:46:01
	 * @author Ouyang
	 */
	List<TfrAreaDensityEntity> findTfrAreaDensity(DensityQcDto dto);

	/**
	 * @desc 日密度趋势查询
	 * @param dto
	 * @return
	 * @date 2015年4月14日 上午11:17:35
	 * @author Ouyang
	 */
	List<DensityTrendDto> findTrendDay(DensityQcDto dto);

	/**
	 * @desc 月密度趋势查询
	 * @param dto
	 * @return
	 * @date 2015年4月14日 上午11:18:58
	 * @author Ouyang
	 */
	List<DensityTrendDto> findTrendMonth(DensityQcDto dto);
}

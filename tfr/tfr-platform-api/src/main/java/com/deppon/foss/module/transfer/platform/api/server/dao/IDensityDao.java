package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.DptAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrDensityPeakEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityTrendDto;

public interface IDensityDao {
	
	/**
	 * @desc 查询分组外场
	 * @param threadCount
	 * @param threadNo
	 * @return map TFR_CTR_CODE外场编码,TFR_CTR_NAME外场名称
	 * @date 2015年3月24日 上午9:40:49
	 * @author Ouyang
	 */
	List<Map<String, String>> findTfrCtrs(Integer threadCount, Integer threadNo);
	
	/**
	 * @desc 每小时时间各种库区密度
	 * @date 2015年4月3日 下午5:05:07
	 * @author Ouyang
	 */
	void generateDensityHour(Map<String,Object> map);

	/**
	 * @desc 每天计算各种库区密度峰值
	 * @date 2015年4月3日 下午5:05:15
	 * @author Ouyang
	 */
	void generateDensityDay(Map<String,Object> map);

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

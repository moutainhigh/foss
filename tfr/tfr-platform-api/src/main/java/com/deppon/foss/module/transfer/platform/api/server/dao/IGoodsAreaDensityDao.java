package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensity4SumDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensityQcDto;

public interface IGoodsAreaDensityDao {

	/**
	 * 货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-12
	 * @author Ouyang
	 */
	List<GoodsAreaDensity4SumDto> selectGoodsAreaDensity4Sum(
			GoodsAreaDensityQcDto dto);

	List<GoodsAreaDensity4SumDto> selectGoodsAreaDensity4Sum(
			GoodsAreaDensityQcDto dto, int start, int limit);

	Long selectCntGoodsAreaDensity4Sum(GoodsAreaDensityQcDto dto);

	/**
	 * 各外场各货区整点货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-12
	 * @author Ouyang
	 */
	List<GoodsAreaDensityEntity> selectGoodsAreaDensity4Timely(
			GoodsAreaDensityQcDto dto);

	List<GoodsAreaDensityEntity> selectGoodsAreaDensity4Timely(
			GoodsAreaDensityQcDto dto, int start, int limit);

	Long selectCntGoodsAreaDensity4Timely(GoodsAreaDensityQcDto dto);

	/**
	 * 各外场各货区各天平均货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto);

	List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Daily(
			GoodsAreaDensityQcDto dto, int start, int limit);

	Long selectCntTfrCtrGoodsAreaAvgDensity4Daily(GoodsAreaDensityQcDto dto);

	/**
	 * 各外场各天平均货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Daily(
			GoodsAreaDensityQcDto dto);

	List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Daily(
			GoodsAreaDensityQcDto dto, int start, int limit);

	Long selectCntTfrCtrAvgDensity4Daily(GoodsAreaDensityQcDto dto);

	/**
	 * 各外场各货区各月平均货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto);

	List<GoodsAreaDensityEntity> selectTfrCtrGoodsAreaAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto, int start, int limit);

	Long selectCntTfrCtrGoodsAreaAvgDensity4Monthly(GoodsAreaDensityQcDto dto);

	/**
	 * 各外场各月平均货区密度查询
	 * @param dto
	 * @return
	 * @date 2014-3-13
	 * @author Ouyang
	 */
	List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto);

	List<GoodsAreaDensityEntity> selectTfrCtrAvgDensity4Monthly(
			GoodsAreaDensityQcDto dto, int start, int limit);

	Long selectCntTfrCtrAvgDensity4Monthly(GoodsAreaDensityQcDto dto);
	
	/**
	 * 生成货区密度
	 * @param date 统计时间
	 * @date 2014-3-17
	 * @author Ouyang
	 */
	void generateGoodsAreaDensity(Date date);

}

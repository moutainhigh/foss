package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.GoodsAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensity4SumDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.GoodsAreaDensityQcDto;

/**
 * @author Ouyang
 *
 */
public interface IGoodsAreaDensityService extends IService {

	/**
	 * 查询传入部门所属外场 map.put("code") = 外场code map.put("name") = 外场name
	 * @param code
	 * @return
	 * @date 2014-3-12
	 * @author Ouyang
	 */
	Map<String, String> queryParentTfrCtrCode(String code);

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
	 * 货区密度导出
	 * @param qcDto
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	ExportResource exportGoodsAreaDensity4Sum(GoodsAreaDensityQcDto dto);
	
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
	 * 各外场各货区整点货区密度导出
	 * @param qcDto
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	ExportResource exportGoodsAreaDensity4Timely(GoodsAreaDensityQcDto dto);

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
	 * 各外场各货区各天平均货区密度导出
	 * @param qcDto
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	ExportResource exportTfrCtrGoodsAreaAvgDensity4Daily(GoodsAreaDensityQcDto dto);
	
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
	 * 各外场各货区各月平均货区密度导出
	 * @param qcDto
	 * @return
	 * @date 2014-3-14
	 * @author Ouyang
	 */
	ExportResource exportTfrCtrGoodsAreaAvgDensity4Monthly(GoodsAreaDensityQcDto dto);
	
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
	 * 生成整点货区密度，有定时任务整点调用
	 * @date 2014-3-17
	 * @author Ouyang
	 */
	void generateGoodsAreaDensity();
	
}

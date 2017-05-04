package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantitySta4ChartDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaGoodsCondDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaQcDto;

public interface IQuantityStaDao {

	/**
	 * @desc 查询货量预测默认配置参数
	 * @param map
	 * @return
	 * @date 2014年8月31日下午5:20:06
	 * @author Ouyang
	 */
	String queryDefaultForecastStartConfigValue(Map<String, String> map);

	/**
	 * @desc 查询货量预测特别外场配置参数
	 * @param map
	 * @return
	 * @date 2014年8月31日下午5:20:33
	 * @author Ouyang
	 */
	List<QuantityStaGoodsCondDto> querySpecialForecastStartConfigValue(
			Map<String, Object> map);
	
	/**
	 * @desc 查询出发货量
	 * @return
	 * @date 2014年8月31日下午5:44:11
	 * @author Ouyang
	 */
	List<QuantityStaDto> queryDepart(QuantityStaQcDto dto);
	
	
	/**
	 * @desc 查询第2天出发货量
	 * @return
	 * @date 2014年8月31日下午5:44:11
	 * @author Ouyang
	 */
	List<QuantityStaDto> query2ndDayDepart(QuantityStaQcDto dto);
	
	/**
	 * @desc 查询一天各时间点的出发货量
	 * @return
	 * @date 2014年8月31日下午5:44:11
	 * @author Ouyang
	 */
	List<QuantitySta4ChartDto> queryDepart1Day4Chart(QuantityStaQcDto dto);

	/**
	 * @desc 查询到达货量
	 * @return
	 * @date 2014年8月31日下午5:44:11
	 * @author Ouyang
	 */
	List<QuantityStaDto> queryArrive(QuantityStaQcDto dto);

	/**
	 * @desc 查询第2天到达货量
	 * @return
	 * @date 2014年8月31日下午5:44:11
	 * @author Ouyang
	 */
	List<QuantityStaDto> query2ndDayArrive(QuantityStaQcDto dto);
	
	/**
	 * @desc 查询一天各时间点的到达货量
	 * @return
	 * @date 2014年8月31日下午5:44:11
	 * @author Ouyang
	 */
	List<QuantitySta4ChartDto> queryArrive1Day4Chart(QuantityStaQcDto dto);
	
	
	List<QuantitySta4ChartDto> queryDepartFewDays1Hh4Chart(QuantityStaQcDto dto);
	
	List<QuantitySta4ChartDto> queryArriveFewDays1Hh4Chart(QuantityStaQcDto dto);
	
	
	/**
	 * @desc 查询外场第2天的派送出发预测货量
	 * @param map
	 * @return
	 * @date 2014年9月16日 下午2:34:07
	 * @author Ouyang
	 */
	List<QuantityStaDto> query2ndDepartDeliverFcst(Map<String, Object> map);
	
	
	/**
	 * @desc 查询某天最后一次预测时间
	 * @param dto
	 * @return
	 */
	Integer queryDepartLastFcstStaHh(Date staDate);
	
	/**
	 * @desc 查询某天最后一次预测时间
	 * @param dto
	 * @return
	 */
	Integer queryArriveLastFcstStaHh(Date staDate);
	
	/**
	 * @desc 查询外场某天的预测总货量
	 * @param map
	 * @return
	 * @date 2014年9月16日 下午2:38:53
	 * @author Ouyang
	 */
	List<QuantityStaDto> queryTotalFcst(Map<String, Object> map);
	
	/**
	 * @desc 查询外场第2天的预测总货量
	 * @param map
	 * @return
	 * @date 2014年9月16日 下午2:38:53
	 * @author Ouyang
	 */
	List<QuantityStaDto> query2ndTotalFcst(Map<String, Object> map);
	
	
	/**
	 * @desc 查询总货量
	 * @param dto
	 * @return
	 * @date 2014年9月23日 下午5:32:20
	 * @author Ouyang
	 */
	QuantityStaDto queryDepartTotal(QuantityStaQcDto dto);
	QuantityStaDto queryDepart2ndTotal(QuantityStaQcDto dto);
	QuantityStaDto queryArriveTotal(QuantityStaQcDto dto);
	QuantityStaDto queryArrive2ndTotal(QuantityStaQcDto dto);
}

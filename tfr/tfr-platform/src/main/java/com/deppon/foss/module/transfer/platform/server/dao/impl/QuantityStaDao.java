package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQuantityStaDao;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantitySta4ChartDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaGoodsCondDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaQcDto;

public class QuantityStaDao extends iBatis3DaoImpl implements IQuantityStaDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.api.server.dao.IQuantityStaDao.";

	/**
	 * @desc 查询货量预测默认配置参数
	 * @param map
	 * @return
	 * @date 2014年8月31日下午5:20:06
	 * @author Ouyang
	 */
	@Override
	public String queryDefaultForecastStartConfigValue(Map<String, String> map) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "queryDefaultForecastStartConfigValue", map);
	}

	/**
	 * @desc 查询货量预测特别外场配置参数
	 * @param map
	 * @return
	 * @date 2014年8月31日下午5:20:33
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaGoodsCondDto> querySpecialForecastStartConfigValue(
			Map<String, Object> map) {
		return (List<QuantityStaGoodsCondDto>) super.getSqlSession()
				.selectList(NAMESPACE + "querySpecialForecastStartConfigValue",
						map);
	}

	/**
	 * @desc 查询出发货量
	 * @param dto
	 * @return
	 * @date 2014年8月31日下午5:44:40
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaDto> queryDepart(QuantityStaQcDto dto) {
		return (List<QuantityStaDto>) super.getSqlSession().selectList(
				NAMESPACE + "queryDepart", dto);
	}

	/**
	 * @desc 查询第2天出发货量
	 * @param dto
	 * @return
	 * @date 2014年8月31日下午5:44:40
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaDto> query2ndDayDepart(QuantityStaQcDto dto) {
		return (List<QuantityStaDto>) super.getSqlSession().selectList(
				NAMESPACE + "query2ndDayDepart", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantitySta4ChartDto> queryDepart1Day4Chart(QuantityStaQcDto dto) {
		return (List<QuantitySta4ChartDto>) super.getSqlSession().selectList(
				NAMESPACE + "queryDepart1Day4Chart", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaDto> queryArrive(QuantityStaQcDto dto) {
		return (List<QuantityStaDto>) super.getSqlSession().selectList(
				NAMESPACE + "queryArrive", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaDto> query2ndDayArrive(QuantityStaQcDto dto) {
		return (List<QuantityStaDto>) super.getSqlSession().selectList(
				NAMESPACE + "query2ndDayArrive", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantitySta4ChartDto> queryArrive1Day4Chart(QuantityStaQcDto dto) {
		return (List<QuantitySta4ChartDto>) super.getSqlSession().selectList(
				NAMESPACE + "queryArrive1Day4Chart", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantitySta4ChartDto> queryDepartFewDays1Hh4Chart(
			QuantityStaQcDto dto) {
		return (List<QuantitySta4ChartDto>) super.getSqlSession().selectList(
				NAMESPACE + "queryDepartFewDays1Hh4Chart", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantitySta4ChartDto> queryArriveFewDays1Hh4Chart(
			QuantityStaQcDto dto) {
		return (List<QuantitySta4ChartDto>) super.getSqlSession().selectList(
				NAMESPACE + "queryArriveFewDays1Hh4Chart", dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaDto> query2ndDepartDeliverFcst(
			Map<String, Object> map) {
		return (List<QuantityStaDto>) super.getSqlSession().selectList(
				NAMESPACE + "query2ndDepartDeliverFcst", map);
	}

	@Override
	public Integer queryDepartLastFcstStaHh(Date staDate) {
		return (Integer) super.getSqlSession().selectOne(
				NAMESPACE + "queryDepartLastFcstStaHh", staDate);
	}
	
	@Override
	public Integer queryArriveLastFcstStaHh(Date staDate) {
		return (Integer) super.getSqlSession().selectOne(
				NAMESPACE + "queryArriveLastFcstStaHh", staDate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaDto> queryTotalFcst(Map<String, Object> map) {
		return (List<QuantityStaDto>) super.getSqlSession().selectList(
				NAMESPACE + "queryTotalFcst", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuantityStaDto> query2ndTotalFcst(Map<String, Object> map) {
		return (List<QuantityStaDto>) super.getSqlSession().selectList(
				NAMESPACE + "query2ndTotalFcst", map);
	}

	@Override
	public QuantityStaDto queryDepartTotal(QuantityStaQcDto dto) {
		return (QuantityStaDto) super.getSqlSession().selectOne(
				NAMESPACE + "queryDepartTotal", dto);
	}

	@Override
	public QuantityStaDto queryDepart2ndTotal(QuantityStaQcDto dto) {
		return (QuantityStaDto) super.getSqlSession().selectOne(
				NAMESPACE + "queryDepart2ndTotal", dto);
	}

	@Override
	public QuantityStaDto queryArriveTotal(QuantityStaQcDto dto) {
		return (QuantityStaDto) super.getSqlSession().selectOne(
				NAMESPACE + "queryArriveTotal", dto);
	}

	@Override
	public QuantityStaDto queryArrive2ndTotal(QuantityStaQcDto dto) {
		return (QuantityStaDto) super.getSqlSession().selectOne(
				NAMESPACE + "queryArrive2ndTotal", dto);
	}

}

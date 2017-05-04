package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.QuantityStaFcstEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaGoodsCondDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrCondDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrPerLineDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaTfrCtrTotalDto;

public interface IQuantityStaDepartDao {

	/**
	 * @desc 将出发实际货量插入最近16天的表
	 * @date 2014年8月29日下午5:38:54
	 * @author Ouyang
	 */
	void insert16Day(QuantityStaGoodsCondDto dto);

	/**
	 * @desc 将出发实际货量表清空
	 * @date 2014年8月29日下午7:13:45
	 * @author Ouyang
	 */
	void deleteDepart(QuantityStaGoodsCondDto dto);

	/**
	 * @desc 调用pkg_opt_quantity_sta_depart.pro_insert_actual生成出发实际货量
	 * @param dto
	 * @date 2014年9月3日 下午12:22:11
	 * @author Ouyang
	 */
	void proInsertActual(QuantityStaGoodsCondDto dto);

	/**
	 * @desc 将第2天出发实际货量表清空
	 * @date 2014年8月29日下午7:14:01
	 * @author Ouyang
	 */
	void delete2ndDayDepart(QuantityStaGoodsCondDto dto);

	/**
	 * @desc 将第2天出发预测货量表清空
	 * @param dto
	 * @date 2014年9月3日 下午12:24:35
	 * @author Ouyang
	 */
	void delete2ndDayDepartFcst(QuantityStaGoodsCondDto dto);

	/**
	 * @desc 调用pkg_opt_quantity_sta_depart. pro_insert_2nd_actual生成第2天出发实际货量
	 * @param dto
	 * @date 2014年9月3日 下午12:23:18
	 * @author Ouyang
	 */
	void proInsert2ndDayActual(QuantityStaGoodsCondDto dto);

	/**
	 * @desc 查询当前某外场各出发货量类型的总货量
	 * @param map
	 * @return
	 * @date 2014年8月31日上午10:20:17
	 * @author Ouyang
	 */
	List<QuantityStaTfrCtrTotalDto> selectTfrCtrTotal(Map<String, Object> map);

	/**
	 * @desc 查询某天某时间点某出发货量类型某外场总货量
	 * @param dto
	 * @return
	 * @date 2014年8月31日上午10:32:33
	 * @author Ouyang
	 */
	QuantityStaTfrCtrTotalDto selectTfrCtrTotal16Day(
			QuantityStaTfrCtrCondDto dto);

	/**
	 * @desc 查询某外场某天最后一个统计时间点
	 * @param dto
	 * @return
	 * @date 2014年8月31日上午11:19:37
	 * @author Ouyang
	 */
	Integer selectLastStaHh16Day(Map<String, Object> map);

	/**
	 * @desc 查询现在某外场每条线的实际货量
	 * @param map
	 * @return
	 * @date 2014年8月31日下午2:28:01
	 * @author Ouyang
	 */
	List<QuantityStaTfrCtrPerLineDto> selectTfrCtrPerLine(
			Map<String, Object> map);

	/**
	 * @desc 查询现在某外场每条线的第2天的实际货量
	 * @param map
	 * @return
	 * @date 2014年9月3日 下午12:26:35
	 * @author Ouyang
	 */
	List<QuantityStaTfrCtrPerLineDto> select2ndDayTfrCtrPerLine(
			Map<String, Object> map);

	/**
	 * @desc 查询某天某时间点某外场某条线的实际货量
	 * @param dto
	 * @return
	 * @date 2014年8月31日下午2:49:17
	 * @author Ouyang
	 */
	QuantityStaTfrCtrPerLineDto selectTfrCtrPerLine16Day(
			QuantityStaTfrCtrCondDto dto);

	/**
	 * @desc 插入出发预测货量
	 * @param entity
	 * @date 2014年8月31日下午4:05:40
	 * @author Ouyang
	 */
	void insertFcst(QuantityStaFcstEntity entity);

	/**
	 * @desc 插入第2天出发预测货量
	 * @param entity
	 * @date 2014年8月31日下午4:05:40
	 * @author Ouyang
	 */
	void insert2ndDayFcst(QuantityStaFcstEntity entity);

	/**
	 * @desc 将T_OPT_STA_DEPART_16DAY中超过16天的数据复制至T_OPT_STA_DEPART_HIS
	 * @param date
	 * @date 2014年9月3日 下午4:11:56
	 * @author Ouyang
	 */
	void insertHis(Date date);

	/**
	 * @desc 将T_OPT_STA_DEPART_16DAY中超过16天的数据删除
	 * @param date
	 * @date 2014年9月3日 下午4:11:56
	 * @author Ouyang
	 */
	void delete16Day(Date date);

	/**
	 * @desc 将T_OPT_STA_DEPART_FCST中超过8天的数据复制至T_OPT_STA_DEPART_FCST_HIS
	 * @param date
	 * @date 2014年9月3日 下午4:11:56
	 * @author Ouyang
	 */
	void insertFcstHis(Date date);

	/**
	 * @desc 将T_OPT_STA_DEPART_FCST中超过8天的数据删除
	 * @param date
	 * @date 2014年9月3日 下午4:11:56
	 * @author Ouyang
	 */
	void deleteFcst(Date date);
}

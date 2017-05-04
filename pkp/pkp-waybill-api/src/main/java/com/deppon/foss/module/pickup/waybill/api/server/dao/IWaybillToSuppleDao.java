package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillSupplementLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleCondtionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleResultDto;

public interface IWaybillToSuppleDao {

	/**
	 * 添加待补录数据记录日志
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年8月13日 18:48:16
	 * @param waybillSupplementLogEntity
	 * @return
	 */
	int addWaybillToSuppleRecord(WaybillSupplementLogEntity waybillSupplementLogEntity);

	/**
	 * 查询待补录记录数据记录
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-13 18:48:34
	 * @param waybillToSuppleCondtionDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<WaybillToSuppleResultDto> queryWaybillToSuppleAction(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto, int start, int limit);

	/**
	 * 查询待补录记录数据记录条数
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-13 18:48:34
	 * @param waybillToSuppleCondtionDto
	 * @param start
	 * @param limit
	 * @return
	 */
	Long queryWaybillToSuppleActionCount(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto);

	/**
	 * 调用存储过程
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-13 18:49:41
	 * @param waybillNo
	 * @param beginDate
	 * @param endDate
	 */
	void renewWaybillByProcedure(String waybillNo, Date beginDate, Date endDate, String claimPay);

	/**
	 * 查询待补录记录数据记录条数
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-13 18:48:34
	 * @param waybillToSuppleCondtionDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<WaybillToSuppleResultDto> queryWaybillToSuppleActionAll(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto);
}

package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessLogEntity;

/**
 * 零担电子面单处理日志DAO
 *
 * @author 325220-foss-liuhui
 * @date 2016年5月14日
 */
public interface IWaybillProcessLogDao {
	/**
	 * 新增一条运单激活处理日志
	 * @param waybillProcessLogEntity
	 */
	int addWaybillProcessLogEntity(WaybillProcessLogEntity waybillProcessLogEntity);
	
	/**
	 * 添加日志
	 * @param content
	 * @param orderNo
	 * @param waybillNo
	 * @param currentDate
	 * @param logType
	 * @param operateResult
	 * @param failReason
	 */
	int saveLog(String content,String orderNo,String waybillNo,Date currentDate,String logType,String operateResult,String failReason);
}

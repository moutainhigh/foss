package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.Map;

import com.deppon.esb.inteface.domain.ecs.EcsFossComplementRequest;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.WayBillRequest;

/**
 * 同步运单信息接口
 * @author ECS-326181
 * @date 2016-5-9 上午9:30:00
 *
 */
public interface IEcsSendWaybillService {

	/**
	 * 同步运单信息
	 * @param wayBillRequest 同步数据
	 */
	public void doSynchroWaybill (WayBillRequest wayBillRequest, boolean flag);
	
	/**
	 * 操作运单信息
	 * @param waybillEntity
	 */
	Map<Integer, String> saveOrUpdateWaybillEntity(WaybillEntity waybillEntity, boolean flag, String operationType);
	
	/**
	 * 操作实际承运信息
	 * @param actualFreight
	 * @param waybillEntity
	 */
	void saveOrUpdateActualFreightEntity(ActualFreightEntity actualFreight, WaybillEntity waybillEntity, String operationType);
	
	/**
	 * 操作快递信息
	 * @param waybillExpress
	 * @param waybillEntity
	 * @param waybillIdMap
	 */
	void saveOrUpdateWaybillExpressEntity(WaybillExpressEntity waybillExpress, WaybillEntity waybillEntity, Map<Integer, String> waybillIdMap, String operationType);
	
	/**
	 * 操作运单更改信息
	 * @param waybillRfc
	 */
	void saveOrUpdateWaybillRfcEntity(WaybillRfcEntity waybillRfc, String operationType);
	
	/**
	 * ECS补码更新运信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-07-20 15:20
	 */
	void updateWaybillByComplement(EcsFossComplementRequest req);
}

package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillStatusSyncToFossEntity;


/**
 * 整车项目同步运单和实际承运表数据
 * 
 * @ClassName: IVtsSyncWaybillAndActual
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-5-5 下午6:42:38
 */
public interface IVtsSyncWaybillAndActualService {
	/**
	 * 同步运单数据
	 * 
	 * @Title: syncWayBillMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:44:15
	 */
	int syncWayBillMessage(WaybillEntity entity);
	
	/**
	 * 同步实际承运表信息数据
	 * 
	 * @Title: syncActualFreightMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:44:15
	 */
	int syncActualFreightMessage(ActualFreightEntity entity);

	/**
	 * 同步运单更改表信息数据
	 * 
	 * @Title: syncActualFreightMessage
	 * @author： 306579 |guoxinru
	 * @date 2016-5-17
	 */
	int syncWayBillRfcMessage(WaybillRfcEntity entity);

	/**
	 * 同步签收结果表信息数据
	 * 
	 * @Title: syncActualFreightMessage
	 * @author： 306579 |guoxinru
	 * @date 2016-5-23
	 */
	int syncWaybillSignResult(WaybillSignResultEntity entity);
	/**
	 * 同步更改运单状态
	 * @author 339073|guojing
	 * @date 2016-6-15
	 */
	int syncWaybillUpdateState(WaybillStatusSyncToFossEntity entity);
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-06-24 17:26:30
	 * 查询签收结果表:根据运单号+active为Y
	 */
	VTSWaybillSignResultEntity queryWaybillSignResultByNo(VTSWaybillSignResultEntity entity);
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-06-24 19:41:23
	 * 查询签收结果表不为空
	 */
	void handlerSignResultWaybill(VTSWaybillSignResultEntity signEntity,WaybillSignResultEntity entity);
	
	
}

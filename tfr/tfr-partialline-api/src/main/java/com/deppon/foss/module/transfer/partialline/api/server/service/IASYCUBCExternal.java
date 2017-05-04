package com.deppon.foss.module.transfer.partialline.api.server.service;

import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillResponse;

/**
 * 异步接口同步给CUBC录入偏线外发单的信息
 * @author 310248
 *
 */
public interface IASYCUBCExternal {

	
	public CubcExternalBillResponse pushAddExternalBill(CubcExternalBillRequest cubcExternalBillRequest);
}

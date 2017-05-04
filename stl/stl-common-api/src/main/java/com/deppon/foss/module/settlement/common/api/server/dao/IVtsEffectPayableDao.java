package com.deppon.foss.module.settlement.common.api.server.dao;

import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VtsWaybillFRcQueryByWaybillNoDto;

public interface IVtsEffectPayableDao {

	boolean isExsitsWayBillRfc(VtsWaybillFRcQueryByWaybillNoDto waybillFRcQueryByWaybillNoDto);

	WaybillEntity queryPartWaybillByNo(String waybillNo);

	WaybillEntity queryFirstWaybillByWaybillNo(String waybillNo);

}

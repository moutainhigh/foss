package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;

public interface ISyncWxdbService {
	void syncWxdbToCrm(List<SatellitePartSalesDeptEntity> addDeptEntities,int operatorSign);
}

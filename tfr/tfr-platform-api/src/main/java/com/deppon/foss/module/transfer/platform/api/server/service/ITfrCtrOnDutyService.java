package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrOnDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrOnDutyQcDto;

public interface ITfrCtrOnDutyService extends IService {

	void insertTfrCtrOnDuty(List<TfrCtrOnDutyEntity> tfrCtrOnDutys);

	void insertSpecial(TfrCtrOnDutyEntity parameter);

	void updateTfrCtrOnDuty(TfrCtrOnDutyEntity parameter);

	void deleteTfrCtrOnDuty(String id);

	List<TfrCtrOnDutyEntity> findTfrCtrOnDutys(TfrCtrOnDutyQcDto parameter,
			int start, int limit);
	
	Long cntTfrCtrOnDutys(TfrCtrOnDutyQcDto parameter);

	List<TfrCtrOnDutyEntity> findInfos4Add(TfrCtrOnDutyQcDto parameter);

}

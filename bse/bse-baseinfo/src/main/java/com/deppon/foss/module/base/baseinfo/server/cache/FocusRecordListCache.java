package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;
import com.deppon.foss.util.common.FossTTLCache;

public class FocusRecordListCache extends FossTTLCache<List<FocusRecordManagementEntity>>{

	@Override
	public String getUUID() {
		// TODO Auto-generated method stub
		return FOCUS_RECORD_LIST_UUID;
	}

}

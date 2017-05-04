package com.deppon.foss.module.transfer.load.api.server.service;

import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.WKLoadTaskDto;


public interface IPDALoadWKService {
	LoadTaskResultDto createLoadTask(WKLoadTaskDto wkLoadTask);

}

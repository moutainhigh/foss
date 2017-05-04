package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoResultDto;

/**
 * Created by 105762 on 2015/2/4.
 */
public interface IMvrDcoService {

    MvrDcoResultDto query(MvrDcoQueryDto queryDto);
}

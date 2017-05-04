package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciResultDto;

/**
 * Created by 105762 on 2015/2/4.
 */
public interface IMvrDciService {

    MvrDciResultDto query(MvrDciQueryDto queryDto);
}

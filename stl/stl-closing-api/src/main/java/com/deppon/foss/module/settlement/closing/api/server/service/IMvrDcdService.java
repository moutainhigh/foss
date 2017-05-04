package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdResultDto;

/**
 * Created by 105762 on 2015/2/4.
 */
public interface IMvrDcdService {

    MvrDcdResultDto query(MvrDcdQueryDto queryDto);
}

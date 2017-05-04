package com.deppon.foss.module.settlement.closing.api.server.dao;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDcdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdResultDto;

import java.util.List;

/**
 * Created by 105762 on 2015/3/31.
 */
public interface IMvrDcdDao {
    List<MvrDcdEntity> query(MvrDcdQueryDto dto);

    MvrDcdResultDto queryTotal(MvrDcdQueryDto dto);
}
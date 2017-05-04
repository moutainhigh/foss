package com.deppon.foss.module.settlement.closing.api.server.dao;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDciEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciResultDto;

import java.util.List;

/**
 * Created by 105762 on 2015/3/31.
 */
public interface IMvrDciDao {
    List<MvrDciEntity> query(MvrDciQueryDto dto);

    MvrDciResultDto queryTotal(MvrDciQueryDto dto);
}
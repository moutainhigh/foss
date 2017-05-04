package com.deppon.foss.module.settlement.closing.api.server.dao;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDcoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoResultDto;

import java.util.List;

/**
 * Created by 105762 on 2015/3/31.
 */
public interface IMvrDcoDao {
    List<MvrDcoEntity> query(MvrDcoQueryDto dto);

    MvrDcoResultDto queryTotal(MvrDcoQueryDto dto);
}
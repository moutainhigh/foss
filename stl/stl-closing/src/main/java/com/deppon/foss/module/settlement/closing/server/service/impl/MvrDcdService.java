package com.deppon.foss.module.settlement.closing.server.service.impl;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDcdDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDcdService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdResultDto;
import org.springframework.beans.factory.annotation.Autowired;


public class MvrDcdService implements IMvrDcdService {

    @Autowired
    private IMvrDcdDao MvrDcdDao;

    @Override
    public MvrDcdResultDto query(MvrDcdQueryDto queryDto) {
        MvrDcdResultDto resultDto =  MvrDcdDao.queryTotal(queryDto);

        if(resultDto != null && resultDto.getCount() != null && resultDto.getCount() > 0L){
            resultDto.setEntityList(MvrDcdDao.query(queryDto));
        }

        return resultDto;
    }

    /* getter & setter */
    public void setMvrDcdDao(IMvrDcdDao MvrDcdDao) {
        this.MvrDcdDao = MvrDcdDao;
    }
}

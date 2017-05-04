package com.deppon.foss.module.settlement.closing.server.service.impl;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDciDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDciService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDciResultDto;
import org.springframework.beans.factory.annotation.Autowired;


public class MvrDciService implements IMvrDciService {

    @Autowired
    private IMvrDciDao MvrDciDao;

    @Override
    public MvrDciResultDto query(MvrDciQueryDto queryDto) {
        MvrDciResultDto resultDto =  MvrDciDao.queryTotal(queryDto);

        if(resultDto != null && resultDto.getCount() != null && resultDto.getCount() > 0L){
            resultDto.setEntityList(MvrDciDao.query(queryDto));
        }

        return resultDto;
    }

    /* getter & setter */
    public void setMvrDciDao(IMvrDciDao MvrDciDao) {
        this.MvrDciDao = MvrDciDao;
    }
}

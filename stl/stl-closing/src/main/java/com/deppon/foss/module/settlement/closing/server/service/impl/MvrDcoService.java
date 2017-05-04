/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-closing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/server/service/impl/MvrRfdEntityService.java
 * 
 * FILE NAME        	: MvrRfdEntityService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.closing.server.service.impl;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrDcoDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDcoService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoResultDto;
import org.springframework.beans.factory.annotation.Autowired;


public class MvrDcoService implements IMvrDcoService {

    @Autowired
    private IMvrDcoDao mvrDcoDao;

    @Override
    public MvrDcoResultDto query(MvrDcoQueryDto queryDto) {
        MvrDcoResultDto resultDto =  mvrDcoDao.queryTotal(queryDto);

        if(resultDto != null && resultDto.getCount() != null && resultDto.getCount() > 0L){
            resultDto.setEntityList(mvrDcoDao.query(queryDto));
        }

        return resultDto;
    }

    /* getter & setter */
    public void setMvrDcoDao(IMvrDcoDao mvrDcoDao) {
        this.mvrDcoDao = mvrDcoDao;
    }
}

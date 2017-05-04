/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/dao/IWaybillMarkDao.java
 * 
 * FILE NAME        	: IWaybillMarkDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillMarkEntity;

/**
 * 运单紧急情况标记DAO接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:073586-FOSS-LIXUEXING,date:2013-01-21 20:33
 * </p>
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2013-01-21 20:33
 * @since
 * @version
 */
public interface IWaybillMarkDao {

    /**
     * 新增运单紧急情况标记
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @return
     * @see
     */
    int addWaybillMark(WaybillMarkEntity entity);

    /**
     * 修改运单紧急情况标记
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @return
     * @see
     */
    int updateWaybillMark(WaybillMarkEntity entity);

    /**
     * 根据传入对象查询符合条件所有运单紧急情况标记
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<WaybillMarkEntity> queryWaybillMarks(WaybillMarkEntity entity);
    /**
     * 批量新增 标记
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    int addWaybillMarkList(String[] codeStr, String markStatus);

}

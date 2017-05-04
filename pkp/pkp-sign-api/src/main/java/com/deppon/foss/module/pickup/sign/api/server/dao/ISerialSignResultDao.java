/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/ISerialSignResultDao.java
 * 
 * FILE NAME        	: ISerialSignResultDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SerialSignResultEntity;
/**
 * 
 * 外发流水签收Dao接口
 * @author foss-sunyanfei
 * @date 2015-10-19 下午3:44:09
 * @since
 * @version
 */
public interface ISerialSignResultDao {
    
    /**
     * 根据运单号、流水号查询外发流水签收信息
     * @author foss-sunyanfei
     * @date 2015-10-24 下午16:36:21
     * @param serialSignResultEntity
     * @return
     */
    SerialSignResultEntity queryByConditions(SerialSignResultEntity serialSignResultEntity);
    
    /**
     * 添加外发流水签收信息
     * @author foss-sunyanfei
     * @date 2015-10-19 上午9:36:15
     * @param serialSignResultEntity
     * @return
     * @see
     */
    int insertSerialSignResult(SerialSignResultEntity serialSignResultEntity);

     /**
      * 根据运单号作废该运单号的外发流水签收结果记录
      * @param serialSignResultEntity
      * @return
      */
     int updateBySerialSignResult(SerialSignResultEntity serialSignResultEntity);
}
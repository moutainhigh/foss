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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ILeasedVehicleWhitelistService.java
 * 
 * FILE NAME        	: ILeasedVehicleWhitelistService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleWhitelistException;
/**
 * 用来操作交互“外请车白名单”申请（入库、可用、不可用、撤回）的数据库对应数据访问Service接口：SUC-104
 * <p>需要版本控制</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 下午4:43:23</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 下午4:43:23
 * @since
 * @version
 */
public interface ILeasedVehicleWhitelistService extends IService {

    /**
     * <p>修改一个“外请白名单车”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:44
     * @param whitelistAuditParameter “外请白名单车”实体
     * @param isVersion true：需要版本控制，false：不需要版本控制
     * @return 1：成功；0：失败
     * @see
     */
     int updateLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, boolean isVersion) throws LeasedVehicleWhitelistException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车白名单”实体列表（条件做自动判断，只选择实体中非空值）</p>
     * <p>包括已审核过和未审核的所有状态（可用、不可用、未入库）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:48:46
     * @param whitelistAudit 以“外请车白名单”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @see
     */
     PaginationDto queryLeasedVehicleWhitelistsListBySelectiveCondition(WhitelistAuditQueryDto whitelistAuditQueryDto, int offset, int limit) throws LeasedVehicleWhitelistException;
    
    /**
     * <p>根据条件有选择的检索出一条符合条件的“外请车白名单”实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:48
     * @param whitelistAuditParameter 以“外请车白名单”实体承载的条件参数实体
     * @return “外请车白名单”实体
     * @see
     */
     WhitelistAuditEntity queryLeasedVehicleWhitelistsBySelectiveCondition(WhitelistAuditEntity whitelistAudit) throws LeasedVehicleWhitelistException;
    
    /**
     * <p>申请一个“外请车白名单”实体入库</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:52
     * @param whitelistAudit “外请车白名单”实体
     * @param applyNotes 申请意见备注
     * @param modifyUser 申请人
     * @return 1：成功；-1：失败
     * @see
     */
     int applyLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, String applyNotes, String modifyUser) throws LeasedVehicleWhitelistException;
    
    /**
     * <p>申请一个“外请车白名单”为“可用”状态</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:17:32
     * @param whitelistAuditParameter “外请车白名单”实体
     * @param applyNotes 申请意见备注
     * @param modifyUser 申请人
     * @return 1：成功；-1：失败
     * @see
     */
     int availableLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, String applyNotes, String modifyUser) throws LeasedVehicleWhitelistException;
    
    /**
     * <p>申请一个“外请车白名单”为“不可用”状态</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:19:04
     * @param whitelistAuditParameter “外请车白名单”实体
     * @param applyNotes 申请意见备注
     * @param modifyUser 申请人
     * @return 1：成功；-1：失败
     * @see
     */
     int unavailableLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, String applyNotes, String modifyUser) throws LeasedVehicleWhitelistException;
    
    /**
     * <p>撤销对一个“外请车白名单”的当前申请（申请入库、申请可用、申请不可用）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:19:24
     * @param whitelistAuditParameter “外请车白名单”实体
     * @param modifyUser 申请人
     * @return 1：成功；-1：失败
     * @see
     */
     int withdrawLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, String modifyUser) throws LeasedVehicleWhitelistException;
     
     /**
      * <p>根据车牌号修改一个“外请白名单车”实体入库</p>.
      *
      * @param whitelistAuditParameter “外请白名单车”实体 
      * @return 1：成功；0：失败
      * @author 100847-foss-GaoPeng
      * @date 2012-10-20 下午4:07:44
      */	
     int updateLeasedWhitelistAuditByVehicleNo(WhitelistAuditEntity whitelistAuditParameter);
}

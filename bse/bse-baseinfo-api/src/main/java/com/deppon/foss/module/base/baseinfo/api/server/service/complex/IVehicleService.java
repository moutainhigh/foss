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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/complex/IVehicleService.java
 * 
 * FILE NAME        	: IVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
/**
 * 用来提供交互所有关于“车辆（公司、外请）”的数据库对应数据访问复杂的Service接口：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-30 下午4:46:42</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-30 下午4:46:42
 * @since
 * @version
 */
public interface IVehicleService extends IService {
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午4:31:23
     * @param vehicleNo 车牌号
     * @return VehicleAssociationDto封装了传送对象
     * @throws BusinessException
     * @see
     */
    public VehicleAssociationDto queryVehicleAssociationDtoByVehicleNo(String vehicleNo) throws BusinessException;
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 上午10:50:33
     * @param vehicleNos 车牌号集合
     * @return VehicleAssociationDto封装了传送对象集合
     * @throws BusinessException
     * @see
     */
    public List<VehicleAssociationDto> queryVehicleAssociationDtosByVehicleNos(String[] vehicleNos) throws BusinessException;

    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO(包括拖头)</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 313353-foss-qiupeng
     * @date 2016-07-06 上午09:31:23
     * @param vehicleNo 车牌号
     * @return VehicleAssociationDto封装了传送对象
     * @throws BusinessException
     * @see
     */
    public VehicleAssociationDto queryVehicleByVehicleNoIncludeTractors(String vehicleNo) throws BusinessException;
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合(包括拖头)</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 313353-foss-qiupeng
     * @date 2016-07-06 上午09:31:23
     * @param vehicleNos 车牌号集合
     * @return VehicleAssociationDto封装了传送对象集合
     * @throws BusinessException
     * @see
     */
    public List<VehicleAssociationDto> queryVehicleByVehicleNosIncludeTractors(String[] vehicleNos) throws BusinessException;


}

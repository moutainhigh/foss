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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/service/IIntegrativeQueryService.java
 * 
 * FILE NAME        	: IIntegrativeQueryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.server.service
 * FILE    NAME: IIntegrativeQueryService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.querying.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillMarkEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDefinedQueryDto;
import com.deppon.foss.module.base.querying.shared.domain.WaybillEntity;
import com.deppon.foss.module.base.querying.shared.domain.WaybillSearchCondition;
import com.deppon.foss.module.base.querying.shared.dto.QueryClaimbillResultDto;
import com.deppon.foss.module.base.querying.shared.exception.QueryingBussinessException;
import com.deppon.foss.module.base.querying.shared.vo.WaybillVo;

/**
 * 综合查询service接口.
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-24 下午4:59:53
 */
public interface IIntegrativeQueryService {

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
     * 根据传入对象查询符合条件所有跟踪记录方案
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<TrackRecordEntity> queryTrackRecords(
	    TrackRecordEntity entity);
    /**
     * 根据条件查询运单信息.
     * 
     * @param condition
     *            the condition
     * @return List<WaybillEntity>
     * @author 078823-foss-panGuangJun
     * @date 2012-12-24 下午5:19:08
     */
    public List<WaybillEntity> searchWaybillByCondtion(
	    WaybillSearchCondition condition) throws QueryingBussinessException;

    /**
     * 根据运单号查询运单所有信息.
     * 
     * @param waybillNo
     *            the waybill no
     * @return WaybillVo
     * @author 078823-foss-panGuangJun
     * @date 2012-12-24 下午5:20:22
     */
    public WaybillVo searchWaybillInfoByWaybillNo(String waybillNo)
	    throws QueryingBussinessException;

    /**
     * 根据传入对象查询符合条件所有自定义查询方案和条件
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @param limit
     * @param start
     * @return List<UserDefinedQueryDto>
     * @see
     */
    List<UserDefinedQueryDto> queryUserDefinedQueryDtos(
	    UserDefinedQueryDto entity);

    /**
     * 根据 自定义查询条件查询运单list.
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @param entity
     * @param limit
     * @param start
     * @return List<UserDefinedQueryDto>
     * @see
     */
    List<WaybillEntity> queryWayBillListByUserDefinedQueryDto(
	    UserDefinedQueryDto entity);
    /**
     * 根据运单号,流水号 查询轨迹信息
     * 
     * @param waybillNo 运单号
     *        serialNo  流水号
     * @return WaybillVo
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-24 下午5:20:22
     */
    WaybillVo searchWaybillInfoByWaybillNo(String waybillNo, String serialNo);
    
    /**
     *<p>Title: queryOtherClaimbillByWaybillNo</p>
     *<p>获取理赔责任信息</p>
     *@author 130566-ZengJunfan
     *@date 2014-6-3下午3:20:09
     * @param claimbillResultDto
     * @return
     */
	QueryClaimbillResultDto queryOtherClaimbillByWaybillNo(
			QueryClaimbillResultDto claimbillResultDto,String waybillNo);
}

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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ICusBargainService.java
 * 
 * FILE NAME        	: ICusBargainService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;

/**
 * 客户合同信息Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 上午9:33:05 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 上午9:33:05
 * @since
 * @version
 */
public interface ICusBargainService extends IService {
    
    /**
     * 新增客户合同信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @param entity
     *            客户合同信息实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addCusBargain(CusBargainEntity entity);

    /**
     * 根据code作废客户合同信息
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @param crmId
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteCusBargainByCode(BigDecimal crmId, String modifyUser);

    /**
     * 修改客户合同信息
     * 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @param entity
     *            客户合同信息实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateCusBargain(CusBargainEntity entity);
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户合同是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:15:29
     * @param crmId
     * @param lastupdatetime
     * @return
     * @see
     */
     boolean queryCusBargainByCrmId(BigDecimal crmId,Date lastupdatetime);
    
    /**
     * <p>根据客户编码查询客户合同中月结客户的最大欠款天数</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-17 下午2:03:26
     * @param custCode 客户编码
     * @return CusBargainEntity(int debtDays:月结客户的最大欠款天数)
     * @see
     */
     CusBargainEntity queryCusBargainByCustCode(String custCode);
    
    /**
     * <p>根据客户编码、开单时间查询客户合同信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-11 上午10:18:58
     * @param custCode 客户编码
     * @param billDate 开单时间
     * @return
     * @see
     */
     CusBargainEntity queryCusBargainInfos(String custCode,Date billDate);
    
    /**
     * <p>根据合同编码和部门编码查询合同信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-23 上午10:35:43
     * @param bargainCode 合同编号
     * @param deptCode 部门编码
     * @return
     * @see
     */
     CusBargainEntity queryCusBargainByParams(String bargainCode,String deptCode);
     
     /**
      * 根据Vo获取客户信息
      * @author WangQianJin
      * @date 2013-7-30 下午8:33:30
      */
     CusBargainEntity queryCusBargainByVo(CusBargainVo vo);
     
     /**
      * 根据Vo获取客户信息用于更改单
      * @author WangQianJin
      * @date 2013-8-31 下午16:33:30
      */
     CusBargainEntity queryCusBargainByVoForRfc(CusBargainVo vo);

	CusBargainEntity queryCusBargainByVoExp(CusBargainVo vo);

	CusBargainEntity queryCusBargainByVoForRfcExp(CusBargainVo vo);

}

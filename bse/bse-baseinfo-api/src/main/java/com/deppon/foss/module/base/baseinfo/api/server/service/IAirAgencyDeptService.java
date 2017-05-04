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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IAirAgencyDeptService.java
 * 
 * FILE NAME        	: IAirAgencyDeptService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyBranchException;

/**
 * 空运代理网点接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-xieyantao,date:2012-10-15 上午11:28:56</p>
 * @author dp-xieyantao
 * @date 2012-10-15 上午11:28:56
 * @since
 * @version
 */
public interface IAirAgencyDeptService extends IService {
    
    /**
     * 新增空运代理网点 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:23:05
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addAirAgencyDept(OuterBranchEntity entity) ;
    
    /**
     * 根据code作废空运代理网点 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:23:05
     * @param codeStr 偏线代理网点虚拟编码拼接字符串
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteAirAgencyDeptByCode(String codeStr,String modifyUser);
    
    /**
     * 修改空运代理网点 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:23:05
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateAirAgencyDept(OuterBranchEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有空运代理网点信息 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:23:05
     * @param entity 空运/偏线代理网点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
     List<OuterBranchEntity> queryAirAgencyDepts(OuterBranchEntity entity,int limit,int start);
    
    /**
     * <p>根据代理公司虚拟编码查询所属代理网点</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-29 下午2:42:43
     * @param comVirtualCode 代理公司虚拟编码
     * @return
     * @see
     */
     List<OuterBranchEntity> queryAirAgencyDeptsByComVirtualCode(String comVirtualCode);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:23:05
     * @param entity 空运/偏线代理网点实体
     * @return
     * @see
     */
     Long queryRecordCount(OuterBranchEntity entity);

     /**
      * 
      * @param entity 空运/偏线代理网点实体
      * @param limit 每页最大显示记录数
      * @param start 开始记录数
      * @return 符合条件的实体列表
      * @throws AirAgencyBranchException
      */
	List<OuterBranchEntity> queryAgencyBranchByAgentDeptName(
			OuterBranchEntity entity, int limit, int start)
			throws AirAgencyBranchException;

}

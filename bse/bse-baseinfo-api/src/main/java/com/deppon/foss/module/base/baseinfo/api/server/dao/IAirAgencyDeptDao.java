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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IAirAgencyDeptDao.java
 * 
 * FILE NAME        	: IAirAgencyDeptDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
/**
 * 用来操作交互“空运代理网点”的数据库对应数据访问DAO接口：SUC-720
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-12 下午2:34:43</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-12 下午2:34:43
 * @since
 * @version
 */
public interface IAirAgencyDeptDao {
    
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
     * @param codes 偏线代理网点虚拟编码数组
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteAirAgencyDeptByCode(String[] codes,String modifyUser);
    
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
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-15 上午11:23:05
     * @param entity 空运/偏线代理网点实体
     * @return
     * @see
     */
    Long queryRecordCount(OuterBranchEntity entity);
    
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
     * 根据传入对象查询符合条件所有空运代理网点信息
     * @author 313353-foss-qiupeng
     * @date 2016-05-19 上午11:34:26
     * @param entity 空运/偏线代理网点实体
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao#queryAgencyBranchByAgentDeptName(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)
     */
	List<OuterBranchEntity> queryAgencyBranchByAgentDeptName(OuterBranchEntity entity);
}

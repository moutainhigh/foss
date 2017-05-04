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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IUserDeptDataService.java
 * 
 * FILE NAME        	: IUserDeptDataService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

/**
 * 用户数据权限service接口
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-17 下午8:40:11
 */
public interface IUserDeptDataService {

    /**
     * 
     * <p>
     * 通过用户的编码得到用户的部门数据权限集合
     * </p>
     * 
     * @author ztjie
     * @date 2013-2-21 下午3:33:07
     * @param userCode
     * @return
     * @see
     */
    List<String> queryUserDeptDataByCode(String userCode);

    /**
     * 
     * <p>
     * 刷新用户部门数据权限
     * </p>
     * 
     * @author ztjie
     * @date 2013-2-21 下午3:34:58
     * @see
     */
    void refreshUserDeptData(String userCode);

    /**
     * 
     * <p>同步组织的时候，进行用户部门数据的增加</p> 
     * @author ztjie
     * @date 2013-7-25 下午2:27:51
     * @param orgCode
     * @see
     */
    void addUserDeptBySyncOrg(String orgCode);
    /**
     * 
     * 通过用户的编码得到用户的外场部门数据权限集合
     * @author foss-zhujunyong
     * @date Mar 7, 2013 3:23:04 PM
     * @param userCode
     * @return
     * @see
     */
    List<String> queryUserDeptDataInTransferCenter(String userCode, String deptCode);

}

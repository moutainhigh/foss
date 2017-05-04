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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/complex/IOrgAdministrativeInfoComplexDao.java
 * 
 * FILE NAME        	: IOrgAdministrativeInfoComplexDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.complex;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;


/**
 * 部门复杂查询DAO 接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午11:25:21
 */
public interface IOrgAdministrativeInfoComplexDao {
    /**
     * 下面是zhangjiheng提的方法
     */

    /**
     * 根据部门编码获取所属及下属部门信息
     * 此部门及下属的所有部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllSubByCode(String code);

    /**
     * 根据部门编码获取所属及下属部门信息
     * 此部门及下属的所有部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllUpByCode(String code);

    /**
     * 根据财务部门编码获取管辖大区信息
     * 大区 是营业大区域
     * 财务部门，不是财务组织的部门，不是一个虚拟的部门，是OA中一个实际存在的部门
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityBigAreaByFinance(String code);

    /**
     * 根据大区编码获取下属小区信息
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitySmallAreaByBig(String code);
    

    /**
     * 根据部门编码获取下属营业部部门信息
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitySalesByUp(String code);
    
    /** 
     * 根据 部门编码（一般为小区编码，大区编码）获取下属营业部部门信息
     * 
     * 返回的编码要在指定的list中
     * 
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * 主要提供给结算
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-3 下午8:26:39
     * @param code部门编码，不是标杆编码
     * @param existCode 包含的营业部编码
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitySalesByUpCode(
	    String code, List<String> existCodes);
	    
    /**
     * 根据部门编码获取下级指定类型的部门。
     * 
     * 
    bizTypes请看BizTypeConstants类，目前包括：    
    ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
    ORG_TRANSFER_CENTER="TRANSFER_CENTER";
    ORG_AIR_DISPATCH="AIR_DISPATCH";
    ORG_DIVISION="DIVISION";
    ORG_BIG_REGION="BIG_REGION";
    ORG_SMALL_REGION="SMALL_REGION";
    EXPRESS_BIG_REGION="EXPRESS_BIG_REGION";
    EXPRESS_SALES_DEPARTMENT="EXPRESS_SALES_DEPARTMENT";
    EXPRESS_PART="EXPRESS_PART";
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:41:09
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoSubByBizType(
	    String code, String bizType);
    /**
     * 根据部门编码获取下级指定类型的部门。
     * 
     * 
    bizTypes请看BizTypeConstants类，目前包括：    
    ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
    ORG_TRANSFER_CENTER="TRANSFER_CENTER";
    ORG_AIR_DISPATCH="AIR_DISPATCH";
    ORG_DIVISION="DIVISION";
    ORG_BIG_REGION="BIG_REGION";
    ORG_SMALL_REGION="SMALL_REGION";
    EXPRESS_BIG_REGION="EXPRESS_BIG_REGION";
    EXPRESS_SALES_DEPARTMENT="EXPRESS_SALES_DEPARTMENT";
    EXPRESS_PART="EXPRESS_PART";
     * 
     * @author 088933-foss-zhangjiheng
     * @date 2012-11-6 上午9:41:09
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoUpByBizType(
	    String code, String bizType);
    
    /**
	 * 根据外场编码查询所对应的虚拟营业部
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-15 上午10:47:27
	 * @param   codeList
	 * @return  List<OrgAdministrativeInfoEntity>
	 * ：
	 *
	 */
	List<OrgAdministrativeInfoEntity> queryExpressSalesDepartmentByTransCenterCode(List<String> codeList);
	
	List<String> queryExpressExpressPartByDeptCode(String empCode);
	/**
	 * 
	 *<p>根据大区编码查询所下属的快递点部集合</p>
	 * @author 130566-foss-ZengJunfan
	 * @date 2014-9-5 上午9:36:18 
	 * @param code
	 * @return
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdminExpressPartsByBig(String code);
}

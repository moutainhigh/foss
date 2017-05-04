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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/complex/IOrgAdministrativeInfoComplexService.java
 * 
 * FILE NAME        	: IOrgAdministrativeInfoComplexService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;


/**
 * 部门 复杂查询 service
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午11:04:19
 */
public interface IOrgAdministrativeInfoComplexService {
    
    /**
     * 根据CODE查询组织及组织的上组织或者下级，
     * 
     * isUp 为true时查本级和上级，为false时，查级和下级
     * @author 087584-foss-lijun
     * @date 2012-10-20 下午3:40:59
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoUpDown(String code,boolean isUp);

    /**
     * 通过传入一个车队或车队下调度组的code，查询出车队下的所有接送货组
     * 经过跟王辉沟通，实际返回为车队组，如果车队下面没有车队组，则返回为空
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> querySubOrgByCode(String code);
    
    
    
    /**
     * 下面是提供给综合组的方法
     * 
     * 
     */
    
    /**
     * 通过部门编码获得顶级车队组织
     * 
     * @author 087584-foss-lijun
     * @date 2013-1-12 下午4:17:18
     */
    OrgAdministrativeInfoEntity getTopFleetByCode(String code);
    
    /**
     * 下面是结算组提的方法
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
     * 根据部门编码获取所属及上级部门信息
     * 此部门及上级的所有部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllUpByCode(String code);
    /**
     * 根据部门编码获取所属及上级部门信息
     * 此部门及上级的所有部门。(方法中没有调用到缓存的方法)
     * TODO（方法详细描述说明、方法参数的具体涵义）
     *  @author 130566-foss-zengjunfan
     * @date 2014-4-24 下午4:16:22
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntityAllUpNOCache(String code);
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
     * 根据大区编码获取下属营业部部门信息
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitySalesByBig(String code);
   
    /**
     * 根据小区编码获取下属营业部部门信息
     * 这里的大区，小区均是营业区域，不是定人定区中的大小区。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoEntitySalesBySmall(String code);
    
    /**
     *<p>根据大区获取下属的快递点部部门信息</p>
     * @author 130566-foss-ZengJunfan
     * @date 2014-9-5 上午9:23:47 
     * @param code
     * @return
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdminExpressPartsBybig(String code);

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
     * 提供给高鹏的方法
     * 查询上级/本级车队
     * 如果是车队，直接返回，如果不是车队，一直往上查
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-5 下午10:37:58
     */
    OrgAdministrativeInfoEntity getOrgAdministrativeInfoMotorcadeByCode(String code);
    
    
    
    /**
     * 下面两个方法是张东平要求提供给冯俊的方法
     * 查询上级部门中指定集中中类型的部门，找到上级中有一个在bizTypes的即返回
     * 
     * bizTypes请看BizTypeConstants类，目前包括：    
    ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
    ORG_TRANSFER_CENTER="TRANSFER_CENTER";
    ORG_AIR_DISPATCH="AIR_DISPATCH";
    ORG_DIVISION="DIVISION";
    ORG_BIG_REGION="BIG_REGION";
    ORG_SMALL_REGION="SMALL_REGION";
    ORG_SALES_DEPARTMENT="SALES_DEPARTMENT";
    ORG_TRANS_TEAM="TRANS_TEAM";
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-23 下午2:00:07
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService#queryOrgAdministrativeInfoByCode(java.lang.String, java.util.List)
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(
	    String code, List<String> bizTypes);
    
    /**
     * 
     * 查询上级部门(包括自己)中指定列表中类型的部门，找到上级中有一个在bizTypes的即返回
     * 
     * bizTypes请看BizTypeConstants类，目前包括：    
    ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
    ORG_TRANSFER_CENTER="TRANSFER_CENTER";
    ORG_AIR_DISPATCH="AIR_DISPATCH";
    ORG_DIVISION="DIVISION";
    ORG_BIG_REGION="BIG_REGION";
    ORG_SMALL_REGION="SMALL_REGION";
    ORG_IS_DELIVER_SCHEDULE="IS_DELIVER_SCHEDULE";
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-23 下午2:00:07
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService#queryOrgAdministrativeInfoByCode(java.lang.String, java.util.List)
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoIncludeSelfByCode(String code, List<String> bizTypes);
    

    /**
     * 根据部门编码获取下级指定类型的部门。
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 上午10:14:19
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoSubByBizType(
	    String code, String bizType) ;
    /**
     * 
     *<p>根据部门code 查找下级部门是车队 或者车队组的部门
     *ps：若顶级车队下级还有顶级车队组织，剔除该下级顶级车队挂的组织
     *</p>
     *@author 130566-zengJunfan
     *@date   2013-7-22下午7:05:30
     * @param code
     * @param bizType
     * @return
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoSubByMotorcade(String code, String bizType);
    /**
     * 
     * <p>根据部门编码获取下级指定类型的部门</p> 
     * @author foss-zhujunyong
     * @date May 17, 2013 12:07:18 PM
     * @param code
     * @param bizTypeList
     * @return
     * @see
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoSubByBizTypeList(String code, List<String> bizTypeList);

    /**
     * 根据外场组织CODE，查询该外场的驻地派送部门对象（包含CODE）
     * 
     * 返回多个
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:41:09
     * 
     * @param code 外场编码
     */
    List<SaleDepartmentEntity> queryStationDeliverOrgByOutfieldCode(String code);
    
    /**
     * 
     * <p>根据外场部门编码，查询该外场的驻地可出发营业部对象</p> 
     * @author foss-zhujunyong
     * @date Apr 2, 2013 2:05:25 PM
     * @param code
     * @return
     * @see
     */
    SaleDepartmentEntity queryStationLeaveOrgByOutfieldCode(String code);

    /**
     * 根据外场组织CODE，查询该外场的驻地派送部门对象（包含CODE）
     * 
     * 返回一个
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-6 上午9:41:09
     * 
     * @param code 外场编码
     */
    SaleDepartmentEntity queryStationDeliverOrgOneByOutfieldCode(String code);
    
    /**
     * 下面是提供给接送货的方法
     */
    
    /**
     * 查询上级部门中“排单部门”，查到后，再查出外场，并返回外场对象
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-23 上午9:29:35
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoOfConnOutfieldByCode(String code);
    
    /**
     * 
     * <p>查找指定部门的上级第一个顶级车队，然后找该顶级车队下属的所有有集中接送货属性的车队的部门编码集合</p> 
     * @author foss-zhujunyong
     * @date Mar 29, 2013 1:16:39 PM
     * @param code
     * @return
     * @see
     */
    List<String> queryDeptCodeListFromTopFleetByCode(String code);   
    
    
    /**
     * 
     * <p>查找指定部门下属的所有有集中接送货属性的车队的部门编码集合</p> 
     * @author foss-zhujunyong
     * @date Mar 29, 2013 5:05:30 PM
     * @param code
     * @return
     * @see
     */
    List<String> queryDeptCodeListByCode(String code, List<String> fleetTypeList);
    
    /**
     * 
     * <p>查找指定部门下属的所有有集中接送货小组属性的车队组的部门编码集合</p> 
     * @author foss-zhujunyong
     * @date Mar 29, 2013 1:14:11 PM
     * @param code
     * @return
     * @see
     */
    List<String> queryTransTeamDeptCodeListByCode(String code, List<String> fleetTypeList);

    /**
     * 
     * <p>根据传入的部门编码找到对应的车队部门编码列表</p>
     * 传入为营业部部门编码时，通过营业部与车队的关系，找对应的车队编码列表
     * 传入为外场部门编码时，通过外场所属车队，找对应的一个车队编码
     *  
     * @author foss-zhujunyong
     * @date Apr 10, 2013 4:03:14 PM
     * @param code
     * @return
     * @see
     */
    List<String> queryMotorcadeCodeListByOrgCode(String code);

    
    /**
     * 
     * <p>根据传入的部门编码找到对应的车队部门编码列表</p>
     * 
     * 传入为营业部部门编码时，通过营业部与车队的关系，找对应的车队编码列表
     * 传入为外场部门编码时，通过外场所属车队，找对应的一个车队编码
     * 
     * 找对应的顶级车队下的所有部门（各个部门均可能下挂车辆）
     *  
     * @author foss-zhujunyong
     * @date Apr 10, 2013 4:03:14 PM
     * @param code
     * @return
     * @see
     */
    List<String> queryMotorcadeOwnerCodeListByOrgCode(String code);

    /**
     * 
     * <p>查找指定多个部门的上级第一个顶级车队，然后找该顶级车队下属的所有有"车队调度组"属性的部门编码集合</p> 
     * @author foss-zhujunyong
     * @date Jun 26, 2013 5:45:25 PM
     * @param codeList
     * @return
     * @see
     */
    List<String> queryDispatchTeamDeptCodeListFromTopFleetByCodeList(List<String> codeList);

    /**
     * 
     * <p>查找指定部门下属的所有车队调度组的部门编码集合</p> 
     * @author foss-zhujunyong
     * @date Mar 29, 2013 1:14:11 PM
     * @param code
     * @return
     * @see
     */
    List<String> queryDispatchTeamDeptCodeListByCode(String code);

	/**
	 * 获取上级指定类型部门信息
	 * 
	 * @author 087584-foss-zhangjiheng
	 * @date 2013.7.31
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoUpByBizType(
			String code, String bizType);
	
	/**
	 * 根据营业部code所属快递大区的所有虚拟营业部
	 * 
	 * @author  WangPeng
	 * @Date    2013-7-26 下午2:08:31
	 * @param code 部门编码
	 * @return：List<OrgAdministrativeInfoEntity>
	 * 
	 */
	List<OrgAdministrativeInfoEntity> queryExpressSalesDepartmentByDeptCode(String code);
	
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
	/**
	 * 根据外场编码查询所对应的虚拟营业部
	 * 
	 * @author  zhangpeng
	 * @Date    2013-8-15 上午10:47:27
	 * @param   codeList
	 * @return  List<OrgAdministrativeInfoEntity>
	 * ：
	 *
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoOfConnOutfieldCenterByCode(String code);
	
	List<String> queryExpressExpressPartByDeptCode(String empCode);
	
	/**
     * 根据名称查询组织表
     * @author 332219-foss
     */
	OrgAdministrativeInfoEntity queryOrgAdministrationInfoByName(String name);
}

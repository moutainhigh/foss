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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IOrgAdministrativeInfoDao.java
 * 
 * FILE NAME        	: IOrgAdministrativeInfoDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
/**
 * 部门 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:41:10
 */
public interface IOrgAdministrativeInfoDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    OrgAdministrativeInfoEntity addOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    OrgAdministrativeInfoEntity deleteOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity);

    /**
     * 根据CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    @Deprecated
    OrgAdministrativeInfoEntity deleteOrgAdministrativeInfoMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    OrgAdministrativeInfoEntity updateOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String code);	
    
    /**
     * 通过 标识编码CODE,是否有效ACTIVE精确查询
     * 
     * 两个参数都可传空，当传空时，不做为查询条件，查询时，取更新时间最近的一条
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @param code 标识编码（组织编码）
     * @param active FossConstants.YES:FossConstants.NO
     */
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByCodeActive(
	    List<String> codes, String active);
	
    /**
     * 精确查询
     * 根据多个CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     * @see com.deppon.foss.module.base.dict.api.server.dao.IOrgAdministrativeInfoDao#queryOrgAdministrativeInfoByCode(java.lang.String)
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoExactByEntity(
	    OrgAdministrativeInfoEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    long queryOrgAdministrativeInfoExactByEntityCount(OrgAdministrativeInfoEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByEntity(OrgAdministrativeInfoEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryOrgAdministrativeInfoByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:11
     */
    long queryOrgAdministrativeInfoByEntityCount(OrgAdministrativeInfoEntity entity);
		
	
    /**
     * 根据entity精确查询
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午6:45:4
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoForDownload(OrgAdministrativeInfoEntity entity);
    /**
     * <p>根据行政区域CODE，查询该行政区域下面所有的组织机构</p> 
     * @author zhangdongping
     * @date 2012-11-10 上午10:26:02
     * @param districtCode 行政区域code
     * @param billDate   开单日期
     * @return
     * @see
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByDistrictCode(
	    String districtCode, Date billDate);
    
    
    /**
     * <p>根据组织CODE，查询所有的组织机构，包括已经删除的</p> 
     * @author zhangdongping
     * @date 2012-11-10 上午10:26:02
     * @param orgCode 组织机构code 必输
     * @param billDate   开单日期 可空 
     * @return
     * @see
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoForCache(
	    String orgCode, Date billDate);
	
    /**
     * 
     * <p>根据code查询name</p> 
     * @author foss-zhujunyong
     * @date Jan 25, 2013 11:02:44 AM
     * @param code
     * @return
     * @see
     */
    String queryOrgNameByCode(String code);
 
    /**
     * 
     * <p>根据组织编码取最新的一条组织实体（无论是否active）</p> 
     * @author foss-zhujunyong
     * @date Feb 21, 2013 10:04:55 AM
     * @param code
     * @return
     * @see
     */
    OrgAdministrativeInfoEntity queryLastestOrgAdministrativeInfoByCode(String code);
    
    /**
     * 查询车队班车部下的小组
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-12-3 下午4:55:43
     */
    List<OrgAdministrativeInfoEntity> searchShuttleGroup(String transDepartmentCode);

	/**
	 * 根据历史时间和组织编码查询组织信息（查询历史组织信息）
	 * 
	 * 若时间为空，则只根据组织编码查询组织信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的部门
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String code, Date billTime);

	/**
	 * 分页下载组织信息数据
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	Object queryOrgAdministrativeInfoForDownloadByPage(
			OrgAdministrativeInfoEntity entity, int start, int limited);
	
	/**
	 *通过 组织标杆编码查询该组织下的所有全部组织信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午6:40:03
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	List<OrgAdministrativeInfoEntity> queryAllOrgAdministrativeInfoByParentOrgUnicodeCode(String parentOrgUnicode, String active);
	
	/**
     * <p>根据上级组织标杆编码parentOrgUnifiedCode查询上级组织名称</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-7-29 上午11:33:31
     * @param parentOrgUnifiedCode
     * @param lastupdatetime
     * @return
     * @see
     */
     String queryParentOrgNameByParentOrgUnifiedCode(String parentOrgUnicode);
     
     /**
      * <p>根据组织标杆编码orgUnifiedCode查询组织编码</p> 
      * @author 132599-foss-shenweihua
      * @date 2013-12-10 下午8:33:31
      * @param parentOrgUnifiedCode
      * @param lastupdatetime
      * @return
      * @see
      */
      String queryOrgCodeByOrgUnifiedCode(String orgUnicode);
      
      /**
       * 根据标杆编码获取部门名称
       * @remark 
       * @author WangQianJin
       * @date 2014-3-12 下午4:04:56
       */
	  String queryDeptNameByUnifiedCode(String unifiedCode); 
	  
	/**
	 * 根据部门编码向下查找其下的所有子部门，判断当前部门是否存在其中
	 * 
	 * auther:wangpeng_078816 date:2014-4-19
	 * 
	 */
	  long queryDeptInfoByCode(List<String> codes,
			String code);
	  
	/**
	 * 根据部门编码查询标杆编码
	 * @param code
	 * @return
	 */
    String queryUnifiedCodeByCode(String code);
    
    /**
	  * 配合主数据项目接口新增组织信息
	  * @author 187862-dujunhui
	  * @date 2015-4-16 上午9:26:46
	  */
	 OrgAdministrativeInfoEntity addOrgAdministrativeInfoOfUU(OrgAdministrativeInfoEntity entity);
	 
	 /**
	  * 配合主数据项目接口更新组织信息
	  * @author 187862-dujunhui
	  * @date 2015-4-16 下午3:19:2
	  */
	 OrgAdministrativeInfoEntity updateOrgAdministrativeInfoOfUU(OrgAdministrativeInfoEntity entity);
	 
	 /**
	  * 配合主数据项目接口作废组织信息
	  * @author 187862-dujunhui
	  * @date 2015-4-16 下午3:50:14
	  */
	 OrgAdministrativeInfoEntity deleteOrgAdministrativeInfoOfUU(OrgAdministrativeInfoEntity entity);
	 
	 /**
	  * 
	  * <p>配合主数据项目，根据UUMS至FOSS的中间表关联FOSS组织表查询组织信息</p> 
	  * @author 187862-dujunhui 
	  * @date 2015-4-28 上午10:24:41
	  * @param entity
	  * @return
	  * @see
	  */
	 OrgAdministrativeInfoEntity queryOrgAdministrationInfoByMidTable(OrgAdministrativeInfoEntity entity);
	 
	 /**
	  * 根据部门名称查询其所在城市名称
	  * @author 187862-dujunhui
	  * @date 2015-9-9 下午4:18:24
	  * @param orgName
	  * @return
	  */
	 String queryCityNameByOrgName(String orgName);
	 
	 /**
	  * 根据机构号集合返回机构对应的集合名称
	  * @author Foss-278328-hujinyang
	  * @date 2015-12-15 15:28:10
	  * @param orgs	机构号集合
	  * @return  Map<code,orgName>  
	  */
	 public Map<String, String> queryOrgNameMapsByCodes(List<String> orgs);

	List<OrgAdministrativeInfoEntity> queryCurrentUserChangeDeptsByDeptNameLike(
			String empCode, String deptName, int start, int limit);

	Long queryCurrentUserChangeDeptsCountsByDeptNameLike(String empCode,
			String deptName);
	
	 
	 OrgAdministrativeInfoEntity queryOrgAdministrationInfoByName(String name);
}

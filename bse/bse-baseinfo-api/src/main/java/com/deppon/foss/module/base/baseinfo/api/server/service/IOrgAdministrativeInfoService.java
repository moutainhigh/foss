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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOrgAdministrativeInfoService.java
 * 
 * FILE NAME        	: IOrgAdministrativeInfoService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OrgAdministrativeInfoVo;

/**
 * 组织信息 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:41:47
 */
public interface IOrgAdministrativeInfoService extends IService {
	
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    OrgAdministrativeInfoEntity addOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity);
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    OrgAdministrativeInfoEntity deleteOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    //OrgAdministrativeInfoEntity deleteOrgAdministrativeInfoMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    OrgAdministrativeInfoEntity updateOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity, boolean isRename);

    /**
     * 精确查询 通过 CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:34
     * @param 只返回表里面的基本数据
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCodeClean(String code);

    /**
     * 精确查询 通过 CODE和 billDate 查询
     * 
     * @author zhangdongping
     * @date 2012-11-2 下午2:41:34
     * @param 只返回表里面的基本数据
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCodeClean(String code,Date billDate);
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String code);	
    	
    /**
     * 精确查询 (不走缓存)
     * 通过 CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:34
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCodeNoCache(String code);
    
    /**
     * 根据编码查询(走缓存)
     * 
     * @author zhangdongping
     * @date 2012-11-2 下午2:41:47
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String code,Date billDate);	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#queryOrgAdministrativeInfoByCode(java.lang.String)
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoExactByEntity(
	    OrgAdministrativeInfoEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    long queryOrgAdministrativeInfoExactByEntityCount(OrgAdministrativeInfoEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByEntity(
	    OrgAdministrativeInfoEntity entity,int start, int limit);
	
    /**
     * 
     * <p>queryOrgAdministrativeInfoByEntity的优化方法，不查冗余字段</p> 
     * @author foss-zhujunyong
     * @date May 2, 2013 4:34:19 PM
     * @param entity
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<OrgAdministrativeInfoEntity> querySimpleOrgAdministrativeInfoByEntity(OrgAdministrativeInfoEntity entity, int start, int limit);
    
    /**
     * 查询queryOrgAdministrativeInfoByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:41:47
     */
    long queryOrgAdministrativeInfoByEntityCount(OrgAdministrativeInfoEntity entity);

    /**
     * 提供给高鹏（特殊查询，不走缓存）
     * 精确查询 通过 组织标杆编码unifiedCode 查询组织;
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-20 下午4:26:46
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService#queryOrgAdministrativeInfoNameByCode(java.lang.String)
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCode(String unifiedCode);

    /**
     * 
     * <p>通过 组织标杆编码unifiedCode 查询组织</p> 
     * @author foss-zhujunyong
     * @date Mar 1, 2013 3:18:37 PM
     * @param unifiedCode
     * @return
     * @see
     */
    OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCodeClean(String unifiedCode);

    /**
     * 精确查询 通过 CODE 查询 NAME
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-20 下午4:26:46
     */
    String queryOrgAdministrativeInfoNameByCode(String code);	   
    
    /**
     * <p>根据行政区域CODE，查询该行政区域下面所有的组织机构</p> 
     * @author zhangdongping
     * @date 2012-11-10 上午10:26:02
     * @param districtCode 行政区域code
     * @param billDate   开单日期
     * @return
     * @see
     */
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByDistrictCode(String districtCode,Date billDate);

    /**
     * 给部门加上部门名称
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    OrgAdministrativeInfoEntity attachSubsidiaryName(OrgAdministrativeInfoEntity entity);  
    
    /**
     * 给部门加上部门名称
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    List<OrgAdministrativeInfoEntity> attachSubsidiaryName(List<OrgAdministrativeInfoEntity> entitys);
    
    /**
     * 给部门加上国家地址，省，市，区县的名字
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    OrgAdministrativeInfoEntity attachDistrictName(OrgAdministrativeInfoEntity entity);   
    
    /**
     * 给部门加上国家地址，省，市，区县的名字
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-3 下午4:55:43
     */
    List<OrgAdministrativeInfoEntity> attachDistrictName(List<OrgAdministrativeInfoEntity> entitys);
    
    /**
     * 查询车队班车部下的小组
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-12-3 下午4:55:43
     */
    List<OrgAdministrativeInfoEntity> searchShuttleGroup(String transDepartmentCode);

    /**
     * 
     * <p>通过编码在缓存中查名称(包括德邦自有部门和代理网点)</p> 
     * 
     * @author foss-zhujunyong
     * @date Feb 28, 2013 3:25:33 PM
     * @param code
     * @return
     * @see
     */
    String queryCommonNameByCommonCodeFromCache(String code);
    
    /**
     * 
     * <p>通过编码查名称(包括德邦自有部门和代理网点)</p> 
     * 
     * @author foss-zhujunyong
     * @date Feb 28, 2013 3:25:33 PM
     * @param code
     * @return
     * @see
     */
    String queryCommonNameByCommonCode(String code);

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
    OrgAdministrativeInfoEntity queryOrgInfoByCodeAndTime(String code, Date billTime);
    /**
     * 
     *<p>修改行政组织基本信息（组织主要信息）</p>
     *@author 130566-zengJunfan
     *@date   2013-7-11下午2:31:22
     * @param vo
     * @param userCode
     */
    void updateOrgAdministrativeInfo(OrgAdministrativeInfoVo vo, String userCode);
    /**
     * .
     * <p>
     * 修改行政组织数据和营业部信息<br/>
     * 方法名：updateOutfield
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-11-07
     * @since JDK1.6
     */
    void updateAllOrgAdministrativeInfo(OrgAdministrativeInfoVo vo, String userCode);    
    /**
     * 
     *<p>修改行政组织数据和车队相关数据</p>
     *@author 130566-zengJunfan
     *@date   2013-7-27下午1:36:16
     * @param allList
     * @param selectedList
     * @return
     */
    void updateOrgAndMotorcadeInfo(OrgAdministrativeInfoVo vo, String userCode);
    
    /**
     *<p>修改行政组织数据和外场相关数据</p>
     *@author 130566-zengJunfan
     *@date   2013-7-27下午1:39:13
     * @param vo
     * @param userCode
     */
    void updateOrgAndOutfieldInfo(OrgAdministrativeInfoVo vo,String userCode);
    /**
     * 
     *<p>修改行政组织数据和集中开单组数据</p>
     *@author 130566-zengJunfan
     *@date   2013-7-27下午1:58:26
     * @param vo
     * @param userCode
     */
    void updateOrgAndBillingGroupInfo(OrgAdministrativeInfoVo vo,String userCode);
    
    List<OrgAdministrativeInfoEntity> removeDuplicate(List<OrgAdministrativeInfoEntity> allList, List<SalesBillingGroupEntity> selectedList);    
    
    
    
	/**
	 * 根据组织标杆编码查询组织信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-7-2 上午11:26:44
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByUnifiedCodeNoCache(String unifiedCode);
	
	/**
	 * 根据组织标杆编码查询所有下级第一级子组织
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-7-2 下午3:55:16
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByUnifiedCodeNoCacheList(String unifiedCode);
	
	
	/**
	 *通过 组织标杆编码查询该组织下的所有全部组织信息
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午6:40:03
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	List<OrgAdministrativeInfoEntity> queryAllOrgAdministrativeInfoByParentOrgUnicodeCode(String parentOrgUnicode);
	
	/**
     * <p>根据上级组织标杆编码parentOrgUnifiedCode查询上级组织名称</p> 
     * @author 132599-foss-shenweihua
     * @date 2012-11-29 上午11:33:31
     * @param parentOrgUnifiedCode
     * @param lastupdatetime
     * @return
     * @see
     */
     String queryParentOrgNameByParentOrgUnifiedCode(String parentOrgUnicode);
     /**
 	 * 精确查询 通过 部门编码CODE 查询补码
 	 * 
 	 * @author 130346-foss-lifanghong
 	 * @date 2014-01-03 下午2:41:34
 	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoService#queryOrgAdministrativeInfoByCode(java.lang.String)
 	 */
	String queryComplementSimpleNameInfoByCode(String code);
	
	/**
	 * 根据标杆编码获取部门名称
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-3-12 下午4:00:13
	 */
	String queryDeptNameByUnifiedCode(String unifiedCode); 
	
	/**
	 * 根据部门编码向下查找其下的所有子部门，判断当前部门是否存在其中
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-19
	 *
	 */
	long queryDeptInfoByCode(List<String> codes,String code);
	/**
	 * .
	 * <p>
	 * 修改保安组数据<br/>
	 * 方法名：updateOutfield
	 * </p>
	 * 
	 * @author 130346-foss-lifanghong
	 * @时间 2014-05-20
	 * @since JDK1.6
	 */
	void updateSecurityTfrMotorcadeInfoByOrg(OrgAdministrativeInfoVo vo,
			String userCode);
	/**
	 * 
	 *<p>Title: updateSaleOrgByGisMap</p>
	 *<p>修改GIS地图，更新营业部信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-6-6下午5:30:28
	 * @param orgAdministrativeInfoVo
	 * @param userCode
	 */
	void updateSaleOrgByGisMap(OrgAdministrativeInfoVo orgAdministrativeInfoVo,
			String userCode);
	
	/**
	 * 根据部门编码查询标杆编码
	 * @param code
	 * @return
	 */
    String queryUnifiedCodeByCode(String code);	
    
    /**
     * 配合主数据项目接口新增组织信息
     * @author 187862-dujunhui
     * @date 2015-4-16 下午3:10:16
     */
    OrgAdministrativeInfoEntity addOrgAdministrativeInfoOfUU(OrgAdministrativeInfoEntity entity);
    /**
     * 配合主数据项目接口更新组织信息
     * @author 187862-dujunhui
     * @date 2015-4-16 下午3:34:22
     */
    OrgAdministrativeInfoEntity updateOrgAdministrativeInfoOfUU(OrgAdministrativeInfoEntity entity, boolean isRename);
    /**
     * 配合主数据项目接口作废组织信息
     * @author 187862-dujunhui
     * @date 2015-4-16 下午3:46:15
     */
    OrgAdministrativeInfoEntity deleteOrgAdministrativeInfoOfUU(OrgAdministrativeInfoEntity entity);
    /**
	 * 配合主数据项目接口查询组织信息
	 * @author 187862-dujunhui
	 * @date 2015-4-20 下午5:40:30
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoOfUUByCodeAndTime(
			OrgAdministrativeInfoEntity entity);
	/**
	 * 
	 * <p>配合主数据项目，插入UUMS至FOSS的组织信息</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-4-27 下午2:52:29
	 * @see
	 */
	OrgAdministrativeInfoEntity addUUMSToFOSS(OrgAdministrativeInfoEntity entity); 
	/**
	 * 作废单个组织时，关联作废 营业部，车队，外场
	 * @author 187862-dujunhui
	 * @date 2015-4-28 上午9:29:23
	 */
	void deleteRely(OrgAdministrativeInfoEntity entity);
	/**
	 * 
	 * <p>配合主数据项目，根据UUMS至FOSS的中间表关联FOSS组织表查询组织信息</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-4-28 上午10:28:42
	 * @param entity
	 * @return
	 * @see
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrationInfoByMidTable(OrgAdministrativeInfoEntity entity);
	
	/**
	 * 
	 * <p>配合主数据项目将同步组织信息给官网等下游系统的接口方法公有化</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-4-28 下午3:33:06
	 * @param entitys
	 * @see
	 */
	void syncToOfficialWebsite(List<OrgAdministrativeInfoEntity> entitys);
	
	/**
	 * 根据多个编码查询组织信息
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 262036 - huangwei
	 * @date 2015-8-24 下午5:04:02
	 * @param codes
	 * @return
	 * @see
	 */
    List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoBatchByCodeClean(String[] codes);
	
	/**
	 * <p>根据部门名称查询其所在城市名称</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-9-9 下午4:25:45
	 * @param String orgName
	 * @see
	 */
	String queryCityNameByOrgName(String orgName);
	
	/**
	 * <p>根据部门标杆编码查询部门编码</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-10-13 上午8:22:34
	 * @param String unifiedCode
	 * @see
	 */
	String queryOrgCodeByUnifiedCode(String unifiedCode);
	
	/**
	 * 提供给中转查询方法：出发到达确认需求
	 * @author 187862-dujunhui
	 * @date 2015-11-2 下午2:06:23
	 */
	String queryDepartureOrArriveFleet(String empCode);

	/**
	 * 根据机构号集合返回机构对应的集合名称
	 * @author Foss-278328-hujinyang
	 * @date 2015-12-15 15:28:10
	 * @param orgs	机构号集合
	 * @return  Map<code,orgName>  
	 */
	public Map<String, String> queryOrgNameMapsByCodes(List<String> orgs);
	
	
	/**
     * 根据编码查询(结算专用——只提供名称，编码，子公司信息）
	 * @param Code
	 * @return
	 */
	OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCodeToPkp(String code);
	
	/**
     * 根据编码查询(只查询组织信息)
	 * @param Code
	 * @return
	 */
	OrgAdministrativeInfoEntity querySimpleOrgAdministrativeInfoByCode(String code);

	/**
	 * <p>根据部门编码跟开单时间查询组织信息，不包含关联各种组织名称之类，只查询基础信息</p> 
	 * 先从缓存进行查询，查询不到再从DB获取
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-4-27 下午6:43:35
	 * @param code
	 * @param billDate
	 * @return
	 * @see
	 */
	OrgAdministrativeInfoEntity queryOrgInfoByCodeAndTimeNoAttach(String code,
			Date billDate);
	/**
	 * 精确查询 通过 CODE 查询通过组织编码在缓存中查找组织，如果缓存中没有找到再走一次数据库查询（新增方法）
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-2-24 下午2:13:36
	 * @param code
	 * @return
	 * @see
	 */
	OrgAdministrativeInfoEntity querySimpleOrgAdministrativeInfoByCodeCache(
			String code);
	/**
     * 根据编码查询组织名称
	 * @param Code
	 * @return
	 */
	String querySimpleNameByCode(String reportDepartmentCode);

	List<OrgAdministrativeInfoEntity> queryCurrentUserChangeDeptsByDeptNameLike(
			String userName, String deptName, int start, int limit);

	Long queryCurrentUserChangeDeptsCountsByDeptNameLike(String userName,
			String deptName);
}
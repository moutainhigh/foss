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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IVehicleAgencyDeptService.java
 * 
 * FILE NAME        	: IVehicleAgencyDeptService.java
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

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
/**
 * 偏线代理网点service 接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-xieyantao,date:2012-10-15 上午10:32:36</p>
 * @author dp-xieyantao
 * @date 2012-10-15 上午10:32:36
 * @since
 * @version
 */
public interface IVehicleAgencyDeptService extends IService {
    
    /**
     * 新增偏线代理网点 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:32:36
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addVehicleAgencyDept(OuterBranchEntity entity) ;
    
    /**
     * 根据code作废偏线代理网点 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:32:36
     * @param codeStr 偏线代理网点虚拟编码拼接字符串
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteVehicleAgencyDeptByCode(String codeStr,String modifyUser);
    
    /**
     * 修改偏线代理网点 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:32:36
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateVehicleAgencyDept(OuterBranchEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有偏线代理网点信息 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:32:36
     * @param entity 空运/偏线代理网点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
     List<OuterBranchEntity> queryVehicleAgencyDepts(OuterBranchEntity entity,int limit,int start);
    
    /**
     * <p>根据代理公司虚拟编码查询该公司的所有代理网点</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-30 下午1:56:35
     * @param comVirtualCode 代理公司的虚拟编码
     * @return
     * @see
     */
     List<OuterBranchEntity> queryOuterBranchsByComCode(String comVirtualCode);
     
     /**
      * <p>根据代理公司编码查询该代理公司的所有代理网点</p> 
      * @author 094463-foss-xieyantao
      * @date 2013-5-13 下午4:10:27
      * @param comCode 代理公司编码
      * @return
      * @see
      */
     List<OuterBranchEntity> queryOuterBrangchsByCode(String comCode);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-15 上午10:32:36
     * @param entity 空运/偏线代理网点实体
     * @return
     * @see
     */
     Long queryRecordCount(OuterBranchEntity entity);
     
     /**
      * <p>根据传入条件 导出查询结果</p> 
      * @author 094463-foss-xieyantao
      * @date 2013-7-5 下午4:57:26
      * @param entity
      * @return
      * @see
      */
     ExportResource exportVehicleAgencyDeptList(OuterBranchEntity entity);
     
     
    
    /**
     * <p>根据外部网点、网点类别查询外部网点是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-20 下午5:52:06
     * @param branchCode 外部网点编码
     * @param branchType 网点类型 :DictionaryValueConstants.OUTERBRANCH_TYPE_KY(空运)
     *                          DictionaryValueConstants.OUTERBRANCH_TYPE_PX(偏线)
     * @return
     * @see
     */
     OuterBranchEntity queryOuterBranchByBranchCode(String branchCode,String branchType);
    
    /**
     * --------------------------以下为对外调用接口----------------------------------
     */
    
    /**
     * 
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-24 上午9:21:53
     * @param agencyBranchCode 代理网点编码
     * @return AgencyBranchOrCompanyDto
     * @see
     */
     AgencyBranchOrCompanyDto queryAgencyBranchCompanyInfo(String agencyBranchCode);
    
    /**
     * 
     * 根据传入参数查询代理网点（空运代理网点和偏线代理网点） 
     * @author 094463-foss-xieyantao
     * @date 2012-11-1 下午3:43:18
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、是否可自提、是否送货上门、用于版本控制时间）
     * @return
     * @see
     */
     List<OuterBranchEntity> queryOuterBranchs(OuterBranchParamsDto dto);

    /**
     * <p>根据行政区域code查找该行政区域下面所有的代理网点信息</p> 
     * @author zhangdongping
     * @date 2013-1-13 下午2:36:41
     * @param districtCode 行政区域code
     * @param billDate 开单日期
     * @return
     * @see
     */
     List<OuterBranchEntity> queryOuterBranchsByDistrictCode(String districtCode,
	    Date billDate);
    
    /**
     * 
     * 根据传入参数查询代理网点（空运代理网点和偏线代理网点） 的简要信息，不做任何包装。
     * @author zhangdongping
     * @date 2012-11-1 下午3:43:18
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、是否可自提、是否送货上门、用于版本控制时间）
     * @return
     * @see
     */
     List<OuterBranchEntity> queryOuterBranchsSimpleInfo(OuterBranchParamsDto dto);

     /**
  	 * 根据历史时间和代理网点编码查询代理网点信息（查询历史代理网点信息）
  	 * 
  	 * 若时间为空，则只根据代理网点编码查询代理网点信息
  	 * 否则将根据时间，查询在creatTime和modifyTime时间段的代理网点
  	 * 不根据Active='Y'来查询
  	 * 
  	 * @author 026123-foss-lifengteng
  	 * @date 2013-4-17 下午6:02:26
  	 */
	OuterBranchEntity queryOuterBranchByCode(String code, Date billTime);
	
	 /**
     * <p>根据区县代码查询出落地配网点坐标和服务范围坐标</p> 
     * @author foss-WeiXing
     * @date 2014-08-28 下午03:56:35
     * @param countyCode 区县代码
     * @return
     * @see
     */
	List<OuterBranchEntity> queryServerCoordinatesByCountyCode(String countyCode);
	
	/**
	 * 根据代理网点编码集合返回机构对应的代理网点名称
	 * @author Foss-278328-hujinyang
	 * @date 2015-12-16
	 * @param orgs
	 * @return
	 */
	public Map<String, String>  queryAgentNameMapsByAgentCode(List<String> orgs);
}

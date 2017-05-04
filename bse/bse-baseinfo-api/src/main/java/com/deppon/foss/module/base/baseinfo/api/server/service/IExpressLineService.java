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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ILineService.java
 * 
 * FILE NAME        	: ILineService.java
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

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DepartureStandardDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineAgingDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LineStationsDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupSiteDto;


/**
 * 线路服务类
 * @author foss-zhujunyong
 * @date Oct 26, 2012 3:14:20 PM
 * @version 1.0
 */
public interface IExpressLineService extends IService{

    /**
     * 
     * <p>添加线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:15 AM
     * @param line
     * @return
     * @see
     */
    ExpressLineEntity addLine(ExpressLineEntity line);
    
    /**
     * 
     * <p>作废线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:30 AM
     * @param line
     * @return
     * @see
     */
    ExpressLineEntity deleteLine(ExpressLineEntity line);
    

    /**
     * 
     * <p>批量作废线路</p> 
     * @author foss-zhujunyong
     * @date Jan 10, 2013 5:46:00 PM
     * @param virtualCodes
     * @param modifyUser
     * @return
     * @see
     */
    void deleteLineList(List<String> virtualCodes, String modifyUser);
    
    /**
     * 
     * <p>更新线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:32:46 AM
     * @param line
     * @return
     * @see
     */
    ExpressLineEntity updateLine(ExpressLineEntity line);

    /**
     * 
     * <p>根据虚拟编码查询线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:33:00 AM
     * @param id
     * @return
     * @see
     */
    ExpressLineEntity queryLineByVirtualCode(String virtualCode);
    
    /**
     * 
     * <p>根据虚拟编码查询线路,不包含其他冗余属性</p> 
     * @author foss-zhujunyong
     * @date Mar 14, 2013 11:36:03 AM
     * @param virtualCode
     * @return
     * @see
     */
    ExpressLineEntity querySimpleLineByVirtualCode(String virtualCode);  
    
    /**
     * 
     * <p>根据条件查询线路</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:34:06 AM
     * @param line
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<ExpressLineEntity> queryLineListByCondition(ExpressLineEntity line, int start, int limit);

    /**
     * 
     * <p>根据查询条件查询线路,不包括出发站目的站名称等冗余信息</p> 
     * @author foss-zhujunyong
     * @date Mar 23, 2013 5:04:03 PM
     * @param line
     * @return
     * @see
     */
    List<ExpressLineEntity> querySimpleLineListByCondition(ExpressLineEntity line);
    
    /** 
     * <p>根据查询条件查询线路,不包括出发站目的站名称等冗余信息</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:22:56 PM
     * @param line
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService#queryLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity, int, int)
     */
    List<ExpressLineEntity> querySimpleLineListByCondition(ExpressLineEntity line, int start, int limit);

    /**
     * 
     * <p>根据查询条件查询线路,不带分页</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 5:20:59 PM
     * @param line
     * @return
     * @see
     */
    List<ExpressLineEntity> queryLineListByCondition(ExpressLineEntity line);
    
    /**
     * 
     * <p>根据线路简码查询线路实体</p>
     * 因为线路简码不能重复，所以只能查出一条线路 
     * @author foss-zhujunyong
     * @date Nov 16, 2012 11:16:12 AM
     * @param simpleCode
     * @return
     * @see
     */
    ExpressLineEntity queryLineBySimpleCode(String simpleCode);
    
    /**
     * 
     * <p>根据线路简码查询线路实体, 不包含冗余属性</p> 
     * @author foss-zhujunyong
     * @date Mar 25, 2013 3:43:40 PM
     * @param simpleCode
     * @return
     * @see
     */
    ExpressLineEntity querySimpleLineBySimpleCode(String simpleCode);
    
    /**<p>根据线路简码查询线路实体, 不包含冗余属性(不走缓存查询)</p> 
     *  @author 130566-zengjunfan
     * @param simpleCode
     * @return
     */
    ExpressLineEntity querySimpleLineBySimpleCodeNoCache(String simpleCode);
    /**
     * 
     * <p>根据条件计算线路数量</p> 
     * @author foss-zhujunyong
     * @date Oct 25, 2012 11:34:24 AM
     * @param line
     * @return
     * @see
     */
    long countLineListByCondition(ExpressLineEntity line);

    /**
     * 
     * <p>根据出发部门（外场）编码查询所有直接到达的部门（外场）编码</p> 
     * @author foss-zhujunyong
     * @date Nov 5, 2012 3:41:28 PM
     * @param sourceCode 出发外场的部门编码
     * @return
     * @see
     */
    List<String> queryTargetCodeListBySourceCode(String sourceCode);
    

    
    /**
     * 
     * <p>通过始发营业部查询默认始发配置外场
     * 或通过到达营业部查询默认到达外场</p> 
     * @author foss-zhujunyong
     * @date Nov 12, 2012 4:03:28 PM
     * @param saleCode
     * @param productCode
     * @param lineSort
     * @return
     * @see
     */
    String queryDefaultTransCode(String saleCode, String productCode, String lineSort);

    /**
     * 
     * <p>通过始发营业部查询默认始发配置外场
     * 或通过到达营业部查询默认到达外场</p> 
     * 按时间建模查找
     * @author foss-zhujunyong
     * @date Nov 12, 2012 4:03:28 PM
     * @param saleCode
     * @param productCode
     * @param lineSort
     * @param date
     * @return
     * @see
     */
    String queryDefaultTransCode(String saleCode, String productCode, String lineSort, Date date);
    
    
    /**
     * 
     * <p>计算在一条线路上指定起点和终点以及指定产品类型的时效</p> 
     * @author foss-zhujunyong
     * @date Dec 4, 2012 5:18:02 PM
     * @param line
     * @param sourceCode
     * @param targetCode
     * @param productCode
     * @return
     * @see
     */
    Long calculateAging(ExpressLineEntity line, String sourceCode, String targetCode, String productCode);
   
    /**
     * 
     * 计算在一条线路上指定起点和终点的半截线路的卡车时效和普车时效
     * @author foss-zhujunyong
     * @date Nov 9, 2012 11:13:39 AM
     * @param line 线路实体
     * @param sourceCode 出发部门
     * @param targetCode 到达部门
     * @return
     * @see
     */
    FreightRouteLineAgingDto calculateAging(ExpressLineEntity line, String sourceCode, String targetCode);

    
    /**
     * 
     * 计算在一条线路上指定起点和终点的半截线路的卡车时效和普车时效
     * @author foss-zhujunyong
     * @date Nov 18, 2012 14:13:39 PM
     * @param lineVirtualCode 线路虚拟编码
     * @param sourceCode 出发部门
     * @param targetCode 到达部门
     * @param productCode 第三级产品代码
     * @return
     * @see
     */
    Long calculateAging(String lineVirtualCode, String sourceCode, String targetCode, String productCode);
    

    /**
     * 
     * <p>根据出发部门和到达部门查询班次列表</p> 
     * 只能找到专线的，因为偏线和空运没有班次的说法
     * @author foss-zhujunyong
     * @date Nov 9, 2012 2:04:40 PM
     * @param sourceCode
     * @param targetCode
     * @return
     * @see
     */
    List<DepartureStandardDto> queryDepartureStandardListBySourceTarget(String sourceCode, String targetCode);
    
    /**
     * 
     * <p>根据出发部门和到达部门查询班次列表</p> 
     * 只能找到专线的，因为偏线和空运没有班次的说法
     * @author foss-zhujunyong
     * @date Nov 19, 2012 2:50:12 PM
     * @param sourceCode 出发部门
     * @param targetCode 到达部门
     * @param productCode 第三极产品类型
     * @return
     * @see
     */
    List<DepartureStandardDto> queryDepartureStandardListBySourceTarget(String sourceCode, String targetCode, String productCode);
    
    
    /**
     * 
     * <p>通过线路虚拟编码和班次，查出发车时间和到达时间</p> 
     * @author foss-zhujunyong
     * @date Nov 14, 2012 11:17:08 AM
     * @param lineVirtualCode
     * @param sequence
     * @return
     * @see
     */
    DepartureStandardDto queryDepartureStandardByLineSequence(String lineVirtualCode, int sequence);
    
    /**
     * 
     * <p>只需要判断出发部门直达到达部门的线路是否存在，如果不存在则返回空</p> 
     * 要根据传入的时间找出最近的一个班次返回
     * @author foss-zhujunyong
     * @date Nov 15, 2012 11:17:53 AM
     * @param sourceCode
     * @param targetCode
     * @param date
     * @return
     * @see
     */
    DepartureStandardDto queryDepartureStandardListBySourceTargetDirectly(
	    String sourceCode, String targetCode, Date date);    


    /**
     * 
     * <p>通过线路虚拟编码，出发部门和到达部门编码查询出发部门和到达部门之间的距离</p>
     * 如果出发部门和到达部门传空，则返回线路总距离 
     * @author foss-zhujunyong
     * @date Nov 16, 2012 11:29:36 AM
     * @param lineVirtualCode
     * @param sourceCode
     * @param targetCode
     * @return
     * @see
     */
    Long queryDistanceBySourceTarget(String lineVirtualCode, String sourceCode, String targetCode);

    
    /**
     * 
     * <p>通过外场的部门编码查询所有辐射到达的营业部</p> 
     * @author foss-zhujunyong
     * @date Nov 16, 2012 3:08:33 PM
     * @param transferCode
     * @return
     * @see
     */
    List<String> queryArriveCodeListByTransferCode(String transferCode);
    

    /**
     * 
     * <p>通过外场编码查询所有的辐射到的出发营业部</p>
     * 包括默认始发配载和非默认的
     * 不包括驻地营业部（驻地营业部只能从所属外场出发） 
     * 主要给走货路径添加修改界面，给网点组选择对应的营业部使用,getIsDefault可以判断是否为默认
     * @author foss-zhujunyong
     * @date Dec 4, 2012 11:29:33 AM
     * @param transferCode
     * @return
     * @see
     */
    List<NetGroupSiteDto> querySourceLineListByTransferCode(String transferCode,String productCode);
    
    /**
     * 
     * <p>通过外场编码查询所有的辐射到的到达营业部</p>
     * 包括默认始发配载和非默认的
     * 不包括驻地营业部（驻地营业部只能从所属外场出发） 
     * 主要给走货路径添加修改界面，给网点组选择对应的营业部使用,getIsDefault可以判断是否为默认
     * @author foss-zhujunyong
     * @date Dec 4, 2012 11:31:20 AM
     * @param transferCode
     * @return
     * @see
     */
    List<NetGroupSiteDto> queryTargetLineListByTransferCode(String transferCode);
    
    /**
     * 
     * <p>按出发部门查询空运线路的到达部门</p> 
     * @author foss-zhujunyong
     * @date Dec 7, 2012 10:21:10 AM
     * @param sourceCode 出发部门编码
     * @return
     * @see
     */
    List<String> queryAirLineBySource(String sourceCode);
    
    /**
     * 
     * <p>导出线路到excel</p>
     * 线路对象的线路类别必须指定，用来判断是导出运作线路还是始发或到达线路
     * 运作线路lineSort类型： DictionaryValueConstants.BSE_LINE_SORT_TRANSFER
     * 始发或到达lineSort类型：DictionaryValueConstants.BSE_LINE_SORT_SOURCE
     * 或DictionaryValueConstants.BSE_LINE_SORT_TARGET
     * @author foss-zhujunyong
     * @date Dec 19, 2012 7:54:17 PM
     * @param line
     * @return
     * @see
     */
    ExportResource exportLineList(ExpressLineEntity line); 
    
    /**
     * 
     * <p>通过中转线路虚拟编码查出该线路途径的外场列表</p> 
     * @author foss-zhujunyong
     * @date Dec 13, 2012 5:08:29 PM
     * @param lineVirtualCode
     * @return
     * @see
     */
    LineStationsDto queryLineStations(String lineVirtualCode);
    
    /**
     * 
     * <p>通过出发部门和到达部门找出最早一班途经班车的发车标准dto</p> 
     * @author foss-zhujunyong
     * @date Dec 28, 2012 3:38:31 PM
     * @param sourceCode
     * @param targetCode
     * @return
     * @see
     */
    DepartureStandardDto queryEarliestDepartureStandard(String sourceCode, String targetCode);
 
    /**
     * 
     * <p>使线路生效，需要验证业务规则</p> 
     * @author foss-zhujunyong
     * @date Jan 9, 2013 2:11:17 PM
     * @param virtualCode
     * @param modifyUser
     * @return
     * @see
     */
    ExpressLineEntity valid(String virtualCode, String modifyUser);
    
    /**
     * 
     * <p>使线路失效</p> 
     * @author foss-zhujunyong
     * @date Jan 9, 2013 2:39:35 PM
     * @param virtualCode
     * @param modifyUser
     * @return
     * @see
     */
    ExpressLineEntity invalid(String virtualCode, String modifyUser);
    
    /**
     * 
     * <p>查询以指定站点为起点的，所有能到达的所有站点列表</p> 
     * @author foss-zhujunyong
     * @date Jan 18, 2013 11:09:25 AM
     * @param sourceCode
     * @param orgName 模糊查询部门名称
     * @return
     * @see
     */
    Map<String, String> queryTargetSiteForSiteGroup(String sourceCode, String orgName);

    /**
     * 
     * <p>查询以指定站点为终点的，所有能出发的所有站点列表</p> 
     * @author foss-zhujunyong
     * @date Jan 18, 2013 11:09:25 AM
     * @param targetCode
     * @param orgName 模糊查询部门名称
     * @return
     * @see
     */
    Map<String, String> querySourceSiteForSiteGroup(String targetCode, String orgName);
    
    /**
     * 
     * <p>通过营业部查找默认的到达配载外场编码</p> 
     * @author foss-zhujunyong
     * @date Jan 28, 2013 3:25:56 PM
     * @param saleOrgCode
     * @return
     * @see
     */
    MapDto queryDefaultArriveTransferOrgCode(String saleOrgCode);    
    
    /**
     * 
     * <p>查找指定营业部所能到达的所有始发配置部门</p> 
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:12:41 AM
     * @param sourceCode
     * @return
     * @see
     */
    List<String> queryTransferCodeListBySourceCode(String sourceCode);

    /**
     * 
     * <p>通过出发部门查询到达部门列表（运作线路），只查询线路的最终到达部门列表，传入的出发部门可以是线路的起点或中转点</p> 
     * @author foss-zhujunyong
     * @date Apr 10, 2013 10:45:00 AM
     * @param sourceCode
     * @return
     * @see
     */
    List<String> queryArriveOrgListBySource(String sourceCode);

    /**
     * 
     * <p>刷新缓存</p> 
     * @author foss-zhujunyong
     * @date Jan 24, 2013 2:33:50 PM
     * @return
     * @see
     */
    void invalidEntity(String key);
    
    /**
     * 
     * <p>批量重命名</p> 
     * @author foss-zhujunyong
     * @date Jun 13, 2013 10:14:31 AM
     * @param orgCode
     * @param orgName
     * @param modifyUser
     * @see
     */
    void rename(String orgCode, String orgName, String modifyUser);
    
    /**
     * 根据出发外场和到达快递代理公司编码查询到达线路
     * 
     * @author WangPeng
     * @Date   2013-9-11 下午6:49:56
     * @param  sourceCode
     * @param  targetCode
     * @param  date
     * @return DepartureStandardDto
     * 
     *
     */
    DepartureStandardDto queryDepartureStandardListByLdpAgentDepts(
    	    String sourceCode, String ldpCompanyCode, Date date); 
    
    /**
     * <p>根据始发站、到达站编码查询运作到运作线路（是否不奖线路）</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-12-11 上午11:19:07
     * @param sourceCode 始发站编码
     * @param targetCode 到达站编码
     * @return
     * @see
     */
    ExpressLineEntity queryExpressLineEntityByCondition(String sourceCode, String targetCode);
    /**
     * 
     * 向GPS同步线路数据(供修改新增发车标准时候用)</p> 
     * @author foss-lifanghong
     * @param line 线路实体
     * @param isDeleted 操作标识：1：新增 2：修改 3：删除 删除 默认0.
     * @return
     * @see
     */
	void sendLineInfoToGps(ExpressLineEntity line, int isDeleted);
}

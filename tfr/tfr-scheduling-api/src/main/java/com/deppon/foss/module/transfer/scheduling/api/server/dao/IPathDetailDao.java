/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IPathDetailDao.java
 * 
 *  FILE NAME     :IPathDetailDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;

/**
 * 路径明细dao
 * @author huyue
 * @date 2012-10-11 下午9:16:55
 */
/**
 * 
 * @author OuYangJiYao
 * @date 2013-6-18 上午10:42:15
 */
public interface IPathDetailDao{

	/**
	 * 查询路径明细LIST
	 * @author huyue
	 * @date 2012-10-23 下午5:49:53
	 */
	List<PathDetailEntity> queryPathDetailList(PathDetailEntity pathDetailEntity);
	
	/**
	 * 查询简单路径明细LIST,重新生成走货路径界面使用
	 * @author peng.z
	 * @date 2013-05-29 下午5:49:53
	 */
	List<PathDetailEntity> querySimplePathDetailList(PathDetailEntity pathDetailEntity);
	
	/**
	 * 查询路径明细LIST 如果状态为空则默认查询状态不等于再离开的条目
	 * @author huyue
	 * @date 2013-1-17 上午9:53:38
	 */
	List<PathDetailEntity> pathDetailNotReleave(PathDetailEntity pathDetailEntity);
	
	
	
	/**
	 * 上面pathDetailNotReleave方法取ROUTE_NO最小的一条
	 * @author OuYangJiYao
     * @date 2013-6-18 上午10:42:23
	 */
    List<PathDetailEntity> pathDetailNotReleave4OnlyOne(PathDetailEntity pathDetailEntity);
	
	
	/**
	 * 查询路径明细
	 * @author huyue
	 * @date 2012-10-25 下午5:26:04
	 */
	PathDetailEntity queryPathDetail(PathDetailEntity pathDetailEntity);
	
	/**
	 * 用于多货修改路径 找到ARRIVE_OR_LEAVE字段不为空的LIST倒序排列
	 * @author huyue
	 * @date 2012-11-12 下午8:15:22
	 */
	List<PathDetailEntity> queryWrong(PathDetailEntity pathDetailEntity);
	
	/**
	 * 新增路径明细信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午6:50:41
	 */
	int addPathDetailSelect(PathDetailEntity pathDetailEntity);
	
	/**
	 * 新增路径明细信息
	 * @author huyue
	 * @date 2012-10-18 下午6:50:51
	 */
	int addPathDetail(PathDetailEntity pathDetailEntity);

	/**
	 * 批量新增路径明细信息
	 * @author huyue
	 * @date 2012-10-18 下午6:51:01
	 */
	int addListPathDetail(List<PathDetailEntity> pathDetailList);
	
	
	/**
	 * 批量新增路径明细信息
	 * @author 140222 songjie
	 * @date 2015-10-27上午8:51:01
	 * @param pathDetailList
	 * @param insertPathType
	 * @return
	 */
	int addListPathDetail(List<PathDetailEntity> pathDetailList,String insertPathType);
	
	/**
	 * 根据PKID更新路径明细信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午6:51:10
	 */
	int updatePathDetailSelect(PathDetailEntity pathDetailEntity);
	
	/**
	 * 根据PKID更新路径明细信息
	 * @author huyue
	 * @date 2012-10-18 下午6:51:19
	 */
	int updatePathDetail(PathDetailEntity pathDetailEntity);
	
	/**
	 * 批量更新路径明细信息
	 * @author huyue
	 * @date 2012-10-18 下午6:51:28
	 */
	int updateListPathDetail(List<PathDetailEntity> pathDetailList);
	
	/**
	 * 批量删除路径明细信息
	 * @author huyue
	 * @date 2012-10-26 上午9:18:08
	 */
	int deleteListPathDetail(List<PathDetailEntity> pathDetailList);
	
	/**
	 * 根据运单号查询所有运单内货件号.如果已包装则查询包装后件号
	 * @author huyue
	 * @date 2012-10-26 上午10:09:14
	 */
	List<String> listQueryGoodsNo(String waybillNo);
	
	/**
	 * 查询运单中货件号,以剩余线路段数排序  by notLeave
	 * @author huyue
	 * @date 2012-11-19 下午5:12:44
	 */
	List<String> listFastGoodsNo(PathDetailEntity pathDetailEntity);
	
	/**
	 * 查询运单中货件号,以剩余线路段数排序 by releave
	 * @author huyue
	 * @date 2012-11-19 下午5:12:44
	 */
	List<String> listFastGoodsNoByReleave(PathDetailEntity pathDetailEntity);
	
	/**
	 * 根据运单号,路段标号等字段查询路段编号大于该段段号的走货路径明细条目
	 * @author huyue
	 * @date 2012-10-26 上午10:42:18
	 */
	List<PathDetailEntity> listQueryNextStep(PathDetailEntity pathDetailEntity);
	
	/**
	 * 查询外场到达时间最晚一条
	 * @author huyue
	 * @date 2012-11-23 下午5:44:20
	 */
	Date queryMaxArriveTime(String objectiveOrgCode);
	
	/**
	 * 查询外场出发时间最晚一条
	 * @author huyue
	 * @date 2012-11-23 下午5:44:39
	 */
	Date queryMaxStartTime(String origOrgCode);
	
	/**
	 * 查询外场到达的部门LIST
	 * @author huyue
	 * @date 2012-12-18 下午12:34:49
	 */
	List<String> queryArriveRelevantOrgCode(Map<String, Object> map);
	
	/**
	 * 查询外场到达货量
	 * @author huyue
	 * @date 2012-11-23 下午5:44:55
	 */
	List<PathDetailEntity> queryArrive(Map<String, Object> map);
	
	/**
	 * 查询外场出发的部门LIST
	 * @author huyue
	 * @date 2012-12-18 下午2:52:31
	 */
	List<String> departRelevantOrgCode(Map<String, Object> map);
	
	/**
	 * 查询外场出发开单货量
	 * @author huyue
	 * @date 2012-11-23 下午5:45:11
	 */
	List<PathDetailEntity> queryStartBilling(Map<String, Object> map);
	
	/**
	 * 查询外场出发非开单货量
	 * @author huyue
	 * @date 2012-11-23 下午5:45:26
	 */
	List<PathDetailEntity> queryStartNotBilling(Map<String, Object> map);
	
	/**
	 * 查询非开单或到达目的营业部的明细list group by 启始部门,到达部门,启始时间,到达时间,下一到达部门
	 * @author huyue
	 * @date 2012-12-24 下午5:49:51
	 */
	List<PathDetailEntity> generalQuery(PathDetailEntity pathDetailEntity);
	
	/**
	 * 查询开单或到达目的地的明细list group by 启始部门,到达部门,启始时间,到达时间,下一到达部门
	 * @author huyue
	 * @date 2012-12-24 下午5:50:08
	 */
	List<PathDetailEntity> generalQueryNot(PathDetailEntity pathDetailEntity);
	
	/**
	 * 根据条件获取匹配的运单号
	 * @author huyue
	 * @date 2012-12-24 下午5:50:24
	 */
	List<String> generalGoodsNoList(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: pathDetailNotReleaveFromMigration 
	* @Description: 从数据迁移表中查询路径明细LIST 如果状态为空则默认查询状态不等于再离开的条目
	* @param pathDetailEntity
	* @return  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see pathDetailNotReleaveFromMigration
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-1 下午2:56:49   
	* @throws 
	*/ 
	List<PathDetailEntity> pathDetailNotReleaveFromMigration(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: queryPathDetailFromMigration 
	* @Description: 从做货路径详细信息数目迁移表中查询走货路径信息
	* @param pathDetailEntity
	* @return  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see queryPathDetailFromMigration
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-20 上午11:26:42   
	* @throws 
	*/ 
	List<PathDetailEntity> queryPathDetailFromMigration(PathDetailEntity pathDetailEntity);

	// modify by liangfuxiang 2013-6-8上午1:01:59 begin
	/**
	 * @Title: listFastGoodsNoFromMigration
	 * @Description: 从走货路径详细信息备份表中，查询货件信息
	 * @param pathDetail
	 * @return 设定文件
	 * @return List<String> 返回类型
	 * @see listFastGoodsNoFromMigration
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-8 上午12:55:20
	 * @throws
	 */
	List<String> listFastGoodsNoFromMigration(PathDetailEntity pathDetail);

	/**
	 * @Title: listFastGoodsNoByReleaveFromMigration
	 * @Description: 从走货路径详细信息备份表中，查询已经离开状态的货件信息
	 * @param pathDetail
	 * @return 设定文件
	 * @return List<String> 返回类型
	 * @see listFastGoodsNoByReleaveFromMigration
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-8 上午12:55:49
	 * @throws
	 */
	List<String> listFastGoodsNoByReleaveFromMigration(PathDetailEntity pathDetail);
	// modify by liangfuxiang 2013-6-8上午1:02:05 end;

	// modify by liangfuxiang 2013-6-8下午6:38:24 begin
	/** 
	* @Title: queryPathDetailReleave 
	* @Description: 查询已经离开状态的做货路径
	* @param pathDetailEntity
	* @return  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see queryPathDetailReleave
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-8 下午6:37:19   
	* @throws 
	*/ 
	List<PathDetailEntity> queryPathDetailReleave(PathDetailEntity pathDetailEntity);
	// modify by liangfuxiang 2013-6-8下午6:38:35 end;
	
	
	/** 
	* @Title: queryMaxRouteNo 
	* @Description: 查询最大的路段号
	* @param maxRouteNoMap
	* @return  设定文件 
	* @return List<String>    返回类型 
	* @see queryMaxRouteNo
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-24 下午4:36:40   
	* @throws 
	*/ 
	List<String> queryMaxRouteNo(Map<String, String> maxRouteNoMap);

	/** 
	* @Title: getNeedsOldPathDetailList 
	* @Description: 查询小于指定路段号的走货路径详细信息
	* @param needsOldMap
	* @return  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see getNeedsOldPathDetailList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-24 下午4:55:44   
	* @throws 
	*/ 
	List<PathDetailEntity> getNeedsOldPathDetailList(Map<String, String> needsOldMap);

	/** 
	* @Title: queryMaxReleavePathDetailEntityList 
	* @Description: 查询已离开状态，最大路段信息
	* @param pathDetailEntity
	* @return  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see queryMaxReleavePathDetailEntityList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-25 上午9:46:53   
	* @throws 
	*/ 
	List<PathDetailEntity> queryMaxReleavePathDetailEntityList(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: updateBeforePathDetailList 
	* @Description: 将之前的路段信息置为已离开状态
	* @param maxReleavePathDetailEntity  设定文件 
	* @return void    返回类型 
	* @see updateBeforePathDetailList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-25 上午9:58:35   
	* @throws 
	*/ 
	void updateBeforePathDetailList(PathDetailEntity maxReleavePathDetailEntity);

	/** 
	* @Title: updatePathDetailMaxRouteNo 
	* @Description: 更新最大路段的目标部门----只所以考虑到选择最大路段,是因为前面走过的路段，状态不准确，只能加入最大路段作为过滤条件。
	* @param pathDetailEntity  设定文件 
	* @return void    返回类型 
	* @see updatePathDetailMaxRouteNo
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-26 上午10:40:43   
	* @throws 
	*/ 
	void updatePathDetailMaxRouteNo(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: updateBeforePathDetailEntityForReleave 
	* @Description: 将之前的路径，全部更新为RELEAVE----主要原因是：前面的状态不一定正确，会给其他业务带来异常。
	* @param pathDetailEntity  设定文件 
	* @return void    返回类型 
	* @see updateBeforePathDetailEntityForReleave
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-26 下午1:39:47   
	* @throws 
	*/ 
	void updateBeforePathDetailEntityForReleave(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: updateBehindPathDetailForNotLeave 
	* @Description: 将当前走货路径的后面的走货路段置为NOTLEAVE
	* @param notLeavePathDetailEntity  设定文件 
	* @return void    返回类型 
	* @see updateBehindPathDetailForNotLeave
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-27 上午10:27:09   
	* @throws 
	*/ 
	void updateBehindPathDetailForNotLeave(PathDetailEntity notLeavePathDetailEntity);

	/** 
	* @Title: deleteBehindPathDetailEntityWithOrigOrgCode 
	* @Description: 删除当前部门作为起始部门的最大路段的走货路径(含当前)之后的所有路段信息
	* @param pde  设定文件 
	* @return void    返回类型 
	* @see deleteBehindPathDetailEntityWithOrigOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-8 下午3:48:11   
	* @throws 
	*/ 
	void deleteBehindPathDetailEntityWithOrigOrgCode(PathDetailEntity pde);

	/** 
	* @Title: updateBeforePathDetailEntityWithOrigOrgCode 
	* @Description: 新当前部门作为起始部门的最大路段的走货路径之前的路段状态为RELEAVE
	* @param pde  设定文件 
	* @return void    返回类型 
	* @see updateBeforePathDetailEntityWithOrigOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-8 下午3:49:03   
	* @throws 
	*/ 
	void updateBeforePathDetailEntityWithOrigOrgCode(PathDetailEntity pde);

	/** 
	* @Title: queryLastPathDetailEntity 
	* @Description:查询走货路径最后一条数据
	* @param tempPathDetailEntity
	* @return  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see queryLastPathDetailEntity
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-16 下午4:51:31   
	* @throws 
	*/ 
	List<PathDetailEntity> queryLastPathDetailEntity(PathDetailEntity tempPathDetailEntity);

	/** 
	* @Title: queryMaxRouteNoPathDetailEntityWithObjOrgCode 
	* @Description: 获取相同目标部门的最大路段信息
	* @param pathDetailEntity
	* @return  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see queryMaxRouteNoPathDetailEntityWithObjOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-18 上午10:57:41   
	* @throws 
	*/ 
	List<PathDetailEntity> queryMaxRouteNoPathDetailEntityWithObjOrgCode(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: queryGoodsNoList 
	* @Description: 根据运单号，查询走货路径对应的流水号信息
	* @param waybillNo
	* @return  设定文件 
	* @return List<String>    返回类型 
	* @see queryGoodsNoList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-22 上午11:41:40   
	* @throws 
	*/ 
	List<PathDetailEntity> queryGoodsNoList(String waybillNo);

	/**
	 * @Title: changePathDetailDestOrgCode
	 * @Description: 修改走货路径明细信息的目的站
	 * @param pathDetailEntity 设定文件
	 * @return void 返回类型
	 * @see changePathDetailDestOrgCode
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-7-22 上午11:57:21
	 * @throws
	 */
	void changePathDetailDestOrgCode(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: changePathDetailEntityNextOrgCode 
	* @Description: 修改走货路径明细倒数第二段的nextOrgCode
	* @param pathDetailEntity  设定文件 
	* @return void    返回类型 
	* @see changePathDetailEntityNextOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-22 下午2:02:24   
	* @throws 
	*/ 
	void changePathDetailEntityNextOrgCode(PathDetailEntity pathDetailEntity);
	
	/**
	* @description 根据出发部门修改查询的最大路由的前一段明细的nextOrgCode
	* @param pathDetailEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年10月26日 上午10:18:42
	*/
	void changePathDetailPojoNextOrgCode(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: queryEqualsLastSecondPathDetailCount 
	* @Description: 查询倒数第二段走货路径编码是否与传入的编码相同
	* @param PathDetailEntity
	* @return  设定文件 
	* @return int    返回类型 
	* @see queryEqualsLastSecondPathDetailCount
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-22 下午3:13:32   
	* @throws 
	*/ 
	int queryEqualsLastSecondPathDetailCount(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: queryBeforePathDetailList 
	* @Description: 查询指定目标部门和路段号的之前的部门，包含当前路段号
	* @param pathDetailEntity
	* @return  设定文件 
	* @return List<PathDetailEntity>    返回类型 
	* @see queryBeforePathDetailList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-26 上午11:13:10   
	* @throws 
	*/ 
	List<PathDetailEntity> queryBeforePathDetailList(PathDetailEntity pathDetailEntity);

	/** 
	* @Title: deletePathDetailList 
	* @Description: 删除走货路径信息
	* @param deletePathDetailEntity  设定文件 
	* @return void    返回类型 
	* @see deletePathDetailList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-7-29 下午2:29:41   
	* @throws 
	*/ 
	void deletePathDetailList(PathDetailEntity deletePathDetailEntity);
	
	/**
	 * 根据运单号和流水号获取最新的交接单号
	 * @param pathDetailEntity
	 * @return
	 */
	String queryNewHandoverNoByWaybillNo(PathDetailEntity pathDetailEntity);
	
	/**
	* @description 根据运单号查询出运单第一次补码前的虚拟营业部(针对快递)
	* @param waybill_no
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年3月18日 上午9:56:05
	*/
	String queryBeforeOrgCodeByWaybillNO(String waybillNo);
	
	/**
	 * @author nly
	 * @date 2015年5月9日 下午2:38:09
	 * @function 根据运单号或运单号流水号查询走货路径明细
	 * @param waybillNo
	 * @param serialNoList
	 * @return
	 */
	List<PathDetailEntity> queryPathDetailByNos(String waybillNo, List<String> serialNoList);
}
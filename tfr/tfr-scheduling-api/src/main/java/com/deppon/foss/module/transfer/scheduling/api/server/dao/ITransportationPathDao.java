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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/ITransportationPathDao.java
 * 
 *  FILE NAME     :ITransportationPathDao.java
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

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;

/**
 * 走货路径dao
 * @author huyue
 * @date 2012-10-11 下午9:16:55
 */
public interface ITransportationPathDao{
	
	/**
	 * 查询走货路径LIST分页
	 * @author huyue
	 * @date 2012-10-11 下午9:17:09
	 */
	List<TransportPathEntity> queryTransportPathforPage(
			TransportPathEntity transportPathEntity, int limit, int start);

	/**
	 * 查询走货路径LIST的行数
	 * @author huyue
	 * @date 2012-10-11 下午9:17:11
	 */
	Long getCount(TransportPathEntity transportPathEntity);
	
	/**
	 * 查询走货路径LIST
	 * @author huyue
	 * @date 2012-10-23 上午11:52:00
	 */
	List<TransportPathEntity> queryTransportPathList(TransportPathEntity transportPathEntity);
	
	/**
	 * 根据waybillNo查询一条走货路径信息
	 * @author huyue
	 * @date 2012-10-23 上午11:52:25
	 */
	TransportPathEntity queryTransportPath(String waybillNo);
	
	/**
	 * 新增走货路径信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午3:09:24
	 */
	int addTransportPathSelect(TransportPathEntity transportPathEntity);
	
	/**
	 * 新增走货路径信息
	 * @author huyue
	 * @date 2012-10-18 下午3:09:42
	 */
	int addTransportPath(TransportPathEntity transportPathEntity);

	/**
	 * 批量新增走货路径信息
	 * @author huyue
	 * @date 2012-10-18 下午6:44:41
	 */
	int addListTransportPath(List<TransportPathEntity> transportPathList);
	
	/**
	 * 根据PKID更新走货路径信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午3:10:01
	 */
	int updateTransportPathSelect(TransportPathEntity transportPathEntity);
	
	/**
	 * 根据PKID更新走货路径信息
	 * @author huyue
	 * @date 2012-10-18 下午3:10:18
	 */
	int updateTransportPath(TransportPathEntity transportPathEntity);
	
	/**
	 * 批量更新走货路径信息
	 * @author huyue
	 * @date 2012-10-18 下午6:45:01
	 */
	int updateListTransportPath(List<TransportPathEntity> transportPathList);
	
	/**
	 * 批量更新走货路径信息 非空判断
	 * @author huyue
	 * @date 2012-11-2 下午2:02:28
	 */
	int updateListTransportPathSelective(List<TransportPathEntity> transportPathList);
	
	/**
	 * 删除走货路径信息
	 * @author huyue
	 * @date 2012-10-23 下午8:17:10
	 */
	int deleteTransportPath(TransportPathEntity transportPathEntity);

	/** 
	* @Title: migrateTransportPathData 
	* @Description:  对于已经签收的货物，走货路径需要迁移至备用表
	* @param params  设定文件 
	* @return void    返回类型 
	* @see migrateTransportPathData
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-27 下午7:14:17   
	* @throws 
	*/ 
	void migrateTransportPathData(Map<String, String> params);

	/** 
	* @Title: queryTransportPathMigration 
	* @Description: 查询数据迁移表
	* @param waybillNo
	* @return  设定文件 
	* @return TransportPathEntity    返回类型 
	* @see queryTransportPathMigration
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-28 上午10:33:30   
	* @throws 
	*/ 
	List<TransportPathEntity> queryTransportPathMigration(String waybillNo);

	/** 
	* @Title: recoverTransportPathMigration 
	* @Description: 数据迁移表中恢复走货路径主表以及详细信息到走货路径信息
	* @param waybillNo  设定文件 
	* @return void    返回类型 
	* @see recoverTransportPathMigration
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-28 上午10:33:46   
	* @throws 
	*/ 
	void recoverTransportPathMigration(String waybillNo);

	/** 
	* @Title: getCountByWayBillNo 
	* @Description: 根据运单查询走货路径数量
	* @param waybillNo
	* @return  设定文件 
	* @return Long    返回类型 
	* @see getCountByWayBillNo
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-10 上午9:40:51   
	* @throws 
	*/ 
	Long getCountByWayBillNo(String waybillNo);

	/**
	 * @Title: queryLastInStockOrgCode
	 * @Description: 查询货物最后一次入库的部门编码
	 * @param paramsMap
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryLastInStockOrgCode
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-6-24 下午3:45:45
	 * @throws
	 */
	List<String> queryLastInStockOrgCode(Map<String, String> paramsMap);
	
	/**
	 * @author 106162
	 * @date 2017年3月24日 下午2:39:48
	 * @function 根据运单号、运单号流水号、当前操作部门 三个参数查询走货路径明细对应的下一站部门
	 * @param waybillNo、goodsNo、origOrgCode
	 * @return nextOrgName
	 */
	String queryPathByWgoDao(String waybillNo,String goodsNo,String origOrgCode);

}
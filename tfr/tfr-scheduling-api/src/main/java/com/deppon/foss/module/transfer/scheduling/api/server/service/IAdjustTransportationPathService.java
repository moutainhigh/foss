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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IAdjustTransportationPathService.java
 * 
 *  FILE NAME     :IAdjustTransportationPathService.java
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
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangePathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.DepartureDto;

/**
 * 修改走货路径service
 * @author huyue
 * @date 2012-10-12 下午6:43:17
 */
public interface IAdjustTransportationPathService extends IService{
	
	/**
	 * 查询调整走货路径LIST,匹配运单表,库存表,只精确到库区, 有边界值
	 * @author huyue
	 * @date 2012-10-12 下午6:41:59
	 */
	List<AdjustEntity> queryLevel1(AdjustEntity adjustEntity, int limit, int start)throws TfrBusinessException;
	
	/**
	 * 获取本库区中以运单为单位的条目
	 * @author huyue
	 * @date 2012-11-13 上午9:41:39
	 */
	List<AdjustEntity> queryByGoodsArea(AdjustEntity adjustEntity)
			throws TfrBusinessException;
	
	/**
	 * 查询调整走货路径LIST,匹配运单表,库存表,只精确到waybill
	 * @author huyue
	 * @date 2012-10-12 下午8:14:53
	 */
	List<AdjustEntity> queryLevel2(AdjustEntity adjustEntity)throws TfrBusinessException;
	
	/**
	 * 查询走货路径count
	 * @author huyue
	 * @date 2012-10-12 下午6:42:57
	 */
	Long getCount(AdjustEntity adjustEntity)throws TfrBusinessException;

	/**
	 * 修改走货路径第三层查询,根据运单匹配在库货件号
	 * @author huyue
	 * @date 2012-10-12 下午7:59:36
	 */
	List<AdjustEntity> queryLevel3(AdjustEntity adjustEntity)throws TfrBusinessException;

	/**
	 * 合车调整走货路径  保存调整至改变表
	 * @author huyue
	 * @throws Exception 
	 * @throws IllegalAccessException 
	 * @date 2012-10-29 下午7:47:36
	 */
	void joinVehicle(List<String> waybillList, List<String> areaCodeList, ChangePathEntity changePathEntity, AdjustEntity adjustEntity, String userCode, String userName) throws TfrBusinessException, Exception;
	
	/**
	 * 根据现部门code 找寻可能到达的部门code list
	 * @author huyue
	 * @date 2012-11-6 下午7:21:03
	 */
	List<String> findObjectiveOrgCode(String origOrgCode)throws TfrBusinessException;
	
	/**
	 * 根据现部门和下一部门找到所有的班次
	 * @author huyue
	 * @date 2012-11-6 下午7:21:58
	 */
	List<DepartureDto> findDepartMsg(String origOrgCode, String destOrgCode) throws TfrBusinessException;
	
	/**
	 * 非合车调整走货路径 保存至改变表 更新相应走货路径
	 * @author huyue
	 * @date 2012-11-6 下午7:20:40
	 */
	void modifyPath(List<String> waybillList, List<String> areaCodeList,
			ChangePathEntity changePathEntity, List<ChangePathEntity> changePathList, AdjustEntity adjustEntity,String userCode, String userName)throws TfrBusinessException, Exception;
	/**
	 * 
	* @Title: findOutOrgCode 
	* @Description: 获取当前登录部门对应的外场编码
	* @return
	* @throws TfrBusinessException  设定文件 
	* @return String    返回类型 
	* @see findOutOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-6-1 下午3:16:35   
	* @throws
	 */
	String findOutOrgCode() throws TfrBusinessException;
	
	
	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param adjustEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月18日 上午10:09:35
	*/
	List<BaseDataDictDto> queryNextOrgByStock(AdjustEntity adjustEntity,int start,int limit);
	
	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param adjustEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月18日 上午10:09:35
	*/
	long queryNextOrgByStockCount(AdjustEntity adjustEntity);
}
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
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/dao/ILdpExternalBillDao.java
 * 
 *  FILE NAME     :ILdpExternalBillDao.java
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
package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillWayBillInfoDto;

/**
 * 落地配Dao接口
 * @author ibm-liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public interface ILdpExternalBillDao {

	/**
	 * 插入新的纪录
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午9:04:53
	 */
	int insert(LdpExternalBillDto record);

	/**
	 * 根据主键查询记录
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午9:05:18
	 */
	LdpExternalBillDto queryByPrimaryKey(String id);

	/**
	 * 更新记录
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午9:05:51
	 */
	int updateByPrimaryKey(LdpExternalBillDto record);

	/**
	 * 查询落地配列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午9:03:45
	 */
	List<LdpExternalBillDto> selectByParams(LdpExternalBillDto dto, int limit, int start);
	
	/**
	 * 查询落地配列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-01  上午9:03:45
	 */
	List<LdpExternalBillDto> queryLdpExternalBillList(LdpExternalBillDto dto);

	/**
	 * 根据运单号查询，被录入的落地配外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午11:36:12
	 */
	List<LdpExternalBillDto> queryByWaybillNo(LdpExternalBillDto dto);

	/**
	 * 根据运单号查询,特定时间之后的被录入的外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午10:25:49
	 */
	Long queryByWaybillNoAndRegisterTime(LdpExternalBillDto dto);

	/**
	 * 根据落地配外发单号及落地配公司编号 查询,非作废 的外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午1:47:31
	 */
	List<LdpExternalBillDto> queryByExternalBillNo(LdpExternalBillDto dto);

	/**
	 * 获取总条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午2:31:28
	 */
	Long queryCount(LdpExternalBillDto dto);

	/**
	 * 查询运单号是否存在未录入的交接单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  上午11:29:29
	 */
	Long queryHandedUninputWaybill(LdpExternalBillDto dto);

	/**
	 * 根据查询条件及sql查询相应的条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午2:31:28
	 */
	Long queryCount(LdpExternalBillDto dto, String mapperSqlId);

	/**
	 * 根据ID 更新审核状态
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午12:17:08
	 */
	void updateAuditStatusByPrimaryKey(List<String> auditIds, String auditStatus);

	/**
	 * 根据主键列表查询落地配外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午3:08:15
	 */
	List<LdpExternalBillDto> selectLdpExternalBillByPrimaryKeys(List<String> externalBillIds);

	/**
	 * 根据运单号查询落地配运单信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午5:23:23
	 */
	LdpExternalBillWayBillInfoDto selectWayBillByWaybillNo(LdpExternalBillDto dto);

	/**
	 * 根据运单号查询落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午3:42:17
	 */
	LdpExternalBillDto queryLdpExternalBillByWaybillNo(LdpExternalBillDto tempDto);

	/**
	 * 运单号查询是否已存在有效的非中转外发的外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17  下午3:56:20
	 */
	LdpExternalBillDto queryLdpExternalBillByWaybillNoForChange(LdpExternalBillDto tempDto);

	/**
	 * 通过运单号查询非中转的落地配外发单数量
	 * @param waybillNo
	 * @return
	 */
	long queryLdpExternalBillCountByWaybillNo(String waybillNo);

	Long queryCountIfExistValidLdpExternalBillForStl(LdpExternalBillDto ldpExternalBillDto);

	
	Long queryCountHasAuditedLdpExternalBill(LdpExternalBillDto ldpExternalBillDto);
	
	/**
	 * 通过运单号、落地配网点获取落地配信息
	 * @param waybillNo
	 * @return
	 */
	List<LdpExternalBillDto> queryExternalBillListForLdpSign(String waybillNo);
	
	/**
	 * @author nly
	 * @date 2014年9月3日 上午9:02:54
	 * @function 更新落地配外发单是否上报OA字段
	 * @param billId
	 * @param isReport
	 * @return
	 */
	int updateReportOAByPrimaryKey(String billId, String isReport);
	/* 
	 * @author nly
	 * @date 2015年1月19日 下午3:50:10
	 * @function 新增运单号流水号至中间表，用于并发控制
	 * @return
	 */
	int addUnInputWaybillNo(String id,String waybillNo,String serialNo);
	/**
	 * @author nly
	 * @date 2015年1月20日 上午9:41:04
	 * @function 更新中间表中的状态
	 * @param waybillNo
	 * @param serialNo
	 */
	int updateUnInputWaybillNo(String waybillNo, String serialNo);
	/**
	 * @author nly
	 * @date 2015年1月20日 上午11:05:54
	 * @function 更新isAdd
	 * @param waybillNo
	 * @param string
	 */
	void updateIsAdd(String waybillNo, String serialNo);
	
	/**
	 * @author chigo
	 * @date 2015年1月29日 下午5:10:56
	 * @function 生成外发单号
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */

	String createExternalBillNo(String waybillNo,String serialNo);
	/**
	 * 通过运单号查询外发单信息列表
	 * @author zwd 200968
	 * @date 2015-04-24 
	 * @param waybillNo
	 * @return
	 */
	public List<LdpExternalBillDto> queryExternalBillListByWaybillNo(
			String waybillNo) ;
	/**
	 * 按照运单号查找外发单号和外发公司  zwd 200968 2016-2-24 
	 * @return
	 */
	public List<LdpExternalBillDto> queryLdpExternalBillNoList(List<String> waybillList);
	
	/**
	 * 根据运单号查询交接单流水号表，得出第一个流水号
	 * @author 269701--lln
	 * @date 2015-12-23
	 * @param waybillNo
	 * @return
	 */
	String queryFirstSerialNoByWayBill(String waybillNo);
}
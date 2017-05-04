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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/service/ILdpExternalBillService.java
 * 
 *  FILE NAME     :ILdpExternalBillService.java
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
package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillWayBillInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.LdpExternalBillVo;

/**
 * 落地配service接口
 * @author ibm-liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public interface ILdpExternalBillService {

	/**
	 * 查询落地配列表(分页)
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午9:03:45
	 */
	List<LdpExternalBillDto> selectByParams(LdpExternalBillDto dto, int limit, int start, CurrentInfo currentInfo);

	/**
	 * 查询落地配列表（外发单信息+运单信息）
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午9:03:45
	 */
	List<LdpExternalBillDto> queryExternalBillInfoList(LdpExternalBillDto dto, boolean flag);
	
	/**
	 * 查询落地配列表（外发单信息+运单信息）
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-01 上午9:03:45
	 */
	List<LdpExternalBillDto> queryOrigLdpExternalBillList(LdpExternalBillDto dto);
	/**
	 * 获取落地配外发单总条数
	 * 
	 * @author 0ibm-liuzhaowei
	 * @date 2013-07-17 下午2:32:16
	 */
	Long queryCount(LdpExternalBillDto dto, CurrentInfo currentInfo);

	/**
	 * 生成落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @param waybillNo 要生成落地配外发单的运单
	 * @param currentInfo 用户信息
	 * @return 生成落地配外发失败的运单
	 * @date 2013-07-17 上午11:46:12
	 */	
	//void addOneLdpExternalBill(String waybillNo, CurrentInfo currentInfo);
	/**
	 * 保存落地配外发单，并同步结算数据
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:47:04
	 * @update-author foss--269701--lln
	 * @update-date 2015/10/23
	 * @update-param wayBillSerialNo 批量生成外发单失败的运单和流水号;
	 */
	void addOneLdpExternalBill(String wayBillSerialNo,String serialNo ,CurrentInfo currentInfo);
	/**
	 * 更新落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午8:38:46
	 */
	LdpExternalBillDto updateLdpExternalBill(LdpExternalBillDto ldpExternalBillDto, CurrentInfo currentInfo);

	/**
	 * 查询运单信息及代理信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:20:50
	 */
	LdpExternalBillWayBillInfoDto queryWaybillInfo(LdpExternalBillDto tempDto, String validateWaybillNo, CurrentInfo user);

	/**
	 * 审核落地配
	 * 
	 * @author ibm-liuzhaowei
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2013-07-17 上午11:48:12
	 */
	int auditLdpExternalBill(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 反审核落地配
	 * 
	 * @author ibm-liuzhaowei
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2013-07-17 下午1:40:15
	 */
	int deAuditLdpExternalBill(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 作废落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2013-07-17 下午1:41:08
	 */
	int invalideLdpExternalBill(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException;

	

	/**
	 * 根据运单号查询外发单信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午3:39:17
	 */
	LdpExternalBillDto queryLdpExternalBillByWaybillNo(LdpExternalBillDto tempDto);

	/**
	 * 运单号查询是否已存在有效的非中转外发的外发单(用于更改单的查询)
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午3:56:20
	 */
	LdpExternalBillDto queryLdpExternalBillByWaybillNoForChange(LdpExternalBillDto tempDto);

	/**
	 * 
	 * 导出落地配外发单列表
	 * 
	 * 
	 * @author ibm-liuzhaowei
	 * 
	 * @date 2013-7-17 上午9:24:40
	 */
	InputStream exportLdpExternalBill(List<LdpExternalBillDto> ldpExternalBillList,LdpExternalBillVo vo);

	/**
	 * 根据运单号，落地配公司代码查询外发单(用于结算查询是否存在有效外发单)
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-22 上午10:57:54
	 */
	boolean checkIfExistValidLdpExternalBillForStl(String waybillNo,String agentCompanyCode);
	
	
	boolean existHasAuditedLdpExternalBillForStl(String waybillNo,String agentCompanyCode);
	
	/**
	 * 查询条件查询有效落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-09  上午11:35:54
	 */
	LdpExternalBillDto queryExternalBillByWaybillNo(LdpExternalBillDto ldpExternalBillDto);
	
	/**
	 * 按运单号查询是否存在落地配外发单
	 * 
	 * @param waybillNo
	 * @return true → 存在;  false → 不存在   
	 */
	boolean checkExistLdpExternalBillByWaybillNo(String waybillNo);

	/**
	 * 按外发单主键查询明细信息
	 * @param id
	 * @return
	 */
	LdpExternalBillDto queryLdpExternalBillDetail(String id);

	/**
	 * 通过运单号查询外发单信息列表
	 * @param waybillNo
	 * @return
	 */
	List<LdpExternalBillDto> queryExternalBillListForLdpSign(String waybillNo);
	
	OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String currentDeptCode);
	/**
	 * @author nly
	 * @date 2014年9月3日 上午9:02:54
	 * @function 更新落地配外发单是否上报OA字段
	 * @param billId 落地配外发单id
	 * @param isReport 是否上报OA，是：Y 否：N
	 * @return
	 */
	int updateReportOAByPrimaryKey(String billId,String isReport);
	
	/**
	 * 通过运单号查询外发单信息列表
	 * @author zwd 200968
	 * @date 2015-04-24 
	 * @param waybillNo
	 * @return
	 */
	List<LdpExternalBillDto> queryExternalBillListByWaybillNo(String waybillNo);
	/**
	 * 按照运单号查找外发单号和外发公司  zwd 200968 2016-2-24 
	 * @return
	 */
	public List<LdpExternalBillDto> queryLdpExternalBillNoList(List<String> waybillList);
	
	/**
	 * @author nly
	 * @date 2015年5月5日 下午5:03:35
	 * @function 根据单号查找有效的落地配外发单
	 * @param waybillNo
	 * @return
	 */
	List<LdpExternalBillDto> queryByWaybillNo(String waybillNo);
}
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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/IContrabandGoodsService.java
 * 
 *  FILE NAME          :IContrabandGoodsService.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity;

/**
 * 定义了违禁品的业务操作方法
 * 
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:03:04
 */
public interface IContrabandGoodsService extends IService {

	/**
	 * 查询违禁品
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 下午4:21:46
	 */
	List<ContrabandGoodsEntity> queryContrabandGoods(ContrabandGoodsEntity contrabandGoods, String currentOrgCode, int limit, int start);

	/**
	 * 查询违禁品 总数
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 下午5:23:38
	 */
	Long queryContrabandGoodsCount(ContrabandGoodsEntity contrabandGoods, String currentOrgCode);

	/**
	 * 在OA中上报违禁品时调用此接口将违禁品同步到FOSS
	 * 
	 * @param waybillNo
	 *            运单号
	 * @param oaErrorNo
	 *            差错编号
	 * @param findOrgCode
	 *            发现部门
	 * @param findTime
	 *            发现时间
	 * @param isContrabandGoods
	 *            是否是违禁品
	 * @paramo perateType 操作类型： 1 --上报, 2 -- 处理结果
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-13 上午9:07:22
	 */
	int oaToFossContrabandGoods(String waybillNo, String oaErrorNo, String findOrgCode, Date findTime, Boolean isContrabandGoods, int operateType);

	/**
	 * 移交违禁品到驻地派送部 出库异常货区 入库派送部货区 修改走货路径 驻地派送部为最终到达部门 更新移交状态
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-15 上午10:41:08
	 */
	int handoverContrabandGoods(List<ContrabandGoodsEntity> contrabandGoodsList, String operatorCode, String operatorName);

	/**
	 * 判断部门类型
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-18 上午11:01:32
	 */
	String queryCurrentOrgType(OrgAdministrativeInfoEntity currentOrg);

	/**
	 * 获取当前用户所属外场
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-18 上午11:57:23
	 */
	OrgAdministrativeInfoEntity queryTransferCenterCode(OrgAdministrativeInfoEntity currentOrg);

	/**
	 * 查询运单是否已上报违禁品
	 * 
	 * @param waybillNo
	 *            运单号
	 * @return: ExceptionGoodsConstants.SUSPICION_CONTRABAND 疑似违禁品
	 *          ExceptionGoodsConstants.CONTRABAND 违禁品
	 *          ExceptionGoodsConstants.NO_CONTRABAND 非违禁品
	 *          ExceptionGoodsConstants.NO_REPORT_CONTRABAND 未上报过违禁品
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-30 下午2:38:34
	 */
	String queryContrabandGoodsStatus(String waybillNo);

	/**
	 * 在QMS中上报违禁品时调用此接口将违禁品同步到FOSS
	 * 
	 * @param waybillNo
	 *            运单号
	 * @param qmsErrorNo
	 *            差错编号
	 * @param goodsName
	 *            货物名称
	 * @param exceptionStatus
	 *            差错状态
	 * @param exceptionResult
	 *            差错结果
	 * @author 316759-foss-ruipengwang
	 * @date 2016-05-19 下午15:58:22
	 */
	int qmsToFossContrabandGoods(String waybillNo, String qmsErrorNo, String goodsName, int exceptionStatus, int exceptionResult) throws Exception;

}
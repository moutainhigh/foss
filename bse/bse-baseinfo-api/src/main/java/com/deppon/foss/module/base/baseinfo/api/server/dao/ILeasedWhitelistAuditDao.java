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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ILeasedWhitelistAuditDao.java
 * 
 * FILE NAME        	: ILeasedWhitelistAuditDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;

/**
 * 用来操作交互“外请白名单（司机、车）”的数据库对应数据访问DAO接口：SUC-750、SUC-104
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-10-25 上午10:39:18
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-25 上午10:39:18
 * @since
 * @version
 */
public interface ILeasedWhitelistAuditDao {

	/**
	 * <p>
	 * 新增一个“外请白名单（司机、车）”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:39
	 * @param whitelistAudit
	 *            “外请白名单（司机、车）”实体
	 * @return 影响记录数
	 * @see
	 */
	int addLeasedWhitelistAudit(WhitelistAuditEntity whitelistAudit);

	/**
	 * <p>
	 * 新增一个“外请白名单（司机、车）”实体入库 （只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:40
	 * @param whitelistAudit
	 *            “外请白名单（司机、车）”实体
	 * @return 影响记录数
	 * @see
	 */
	int addLeasedWhitelistAuditBySelective(WhitelistAuditEntity whitelistAudit);

	/**
	 * <p>
	 * 根据“外请白名单（司机、车）”记录唯一标识作废一条“外请白名单（司机、车）”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:38
	 * @param id
	 *            记录唯一标识
	 * @return 影响记录数
	 * @see
	 */
	int deleteLeasedWhitelistAudit(String id);

	/**
	 * <p>
	 * 修改一个“外请白名单（司机、车）”实体入库 （只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:43
	 * @param whitelistAudit
	 *            “外请白名单（司机、车）”实体
	 * @return 影响记录数
	 * @see
	 */
	int updateLeasedWhitelistAuditBySelective(
			WhitelistAuditEntity whitelistAudit);

	/**
	 * <p>
	 * 修改一个“外请白名单（司机、车）”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:44
	 * @param whitelistAudit
	 *            “外请白名单（司机、车）”实体
	 * @return 影响记录数
	 * @see
	 */
	int updateLeasedWhitelistAudit(WhitelistAuditEntity whitelistAudit);

	/**
	 * <p>
	 * 根据条件唯一标识查询“外请白名单（司机、车）”唯一实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:41
	 * @param id
	 *            记录唯一标识
	 * @return “外请白名单（司机、车）”实体
	 * @see
	 */
	WhitelistAuditEntity queryLeasedWhitelistAuditBySelective(String id);

	/**
	 * <p>
	 * 根据条件有选择查询“外请白名单（司机、车）”唯一实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:41
	 * @param whitelistAudit
	 *            “外请白名单（司机、车）”参数实体
	 * @return “外请白名单（司机、车）”实体
	 * @see
	 */
	WhitelistAuditEntity queryLeasedWhitelistAuditBySelective(
			WhitelistAuditEntity whitelistAudit);

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的待审核“外请白名单（司机、车）”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:42
	 * @param whitelistAudit
	 *            以“外请白名单（司机、车）”实体承载的条件参数实体
	 * @return 符合条件的待审核“外请白名单（司机、车）”实体列表
	 * @see
	 */
	List<WhitelistAuditEntity> queryLeasedWhitelistAuditListBySelective(
			WhitelistAuditEntity whitelistAudit);

	/**
	 * <p>
	 * 根据条件（分页模糊）有选择的统计出符合条件的待审核“外请白名单（司机、车）”实体记录数（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-25 上午10:47:25
	 * @param leasedTruck
	 *            以“外请白名单（司机、车）”实体承载的条件参数实体
	 * @return 符合条件的待审核“外请白名单（司机、车）”实体记录条数
	 * @see
	 */
	long queryLeasedWhitelistAuditRecordCountBySelectiveCondition(
			WhitelistAuditEntity whitelistAudit);

	/**
	 * <p>
	 * 根据条件（分页模糊）有选择的检索出符合条件的待审核“外请白名单（司机、车）”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 下午4:07:42
	 * @param whitelistAudit
	 *            以“外请白名单（司机、车）”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的待审核“外请白名单（司机、车）”实体列表
	 * @see
	 */
	List<WhitelistAuditEntity> queryLeasedWhitelistAuditListBySelectiveCondition(
			WhitelistAuditEntity whitelistAudit, int offset, int limit);

	/**
	 * <p>
	 * 根据条件有选择的统计出符合条件的申请“外请白名单（司机、车）”实体记录数（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-26 上午8:53:14
	 * @param leasedTruck
	 *            以“外请白名单（司机、车）”实体承载的条件参数实体
	 * @return 符合条件的待申请“外请白名单（司机、车）”实体记录条数
	 * @see
	 */
	long queryLeasedWhitelistApplyRecordCountBySelectiveCondition(
			WhitelistAuditQueryDto whitelistAuditQueryDto);

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的申请“外请白名单（司机、车）”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-26 上午8:53:17
	 * @param whitelistAudit
	 *            以“外请白名单（司机、车）”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的申请“外请白名单（司机、车）”实体列表
	 * @see
	 */
	List<WhitelistAuditEntity> queryLeasedWhitelistApplyListBySelectiveCondition(
			WhitelistAuditQueryDto whitelistAuditQueryDto, int offset, int limit);
	
	 /**
     * <p>根据身份证ID或车牌号修改一个“外请白名单（司机、车）”实体入库）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午11:05:12
     * @param whitelistAudit “外请白名单（司机、车）”实体
     * @return 影响记录数
     */
    int updateLeasedWhitelistAuditByCardIdOrVehicleNo(WhitelistAuditEntity whitelistAudit);
    
    
    /**
     * 查询车辆白名单，包含申请部门
     *
     * @author foss-qiaolifeng
     * @date 2013-7-4 下午3:29:55
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    List<WhitelistAuditEntity> queryLeasedWhitelistApplyListBySelectiveConditionAndApplyOrg(
			WhitelistAuditQueryDto whitelistAuditQueryDto, int offset, int limit);
    
    /**
     * 查询车辆白名单总数，包含申请部门
     *
     * @author foss-qiaolifeng
     * @date 2013-7-4 下午3:29:58
     * @param
     * @return 成功失败标记
     * @exception SettlementException
     * @see
     */
    long queryLeasedWhitelistApplyRecordCountBySelectiveConditionAndApplyOrg(
			WhitelistAuditQueryDto whitelistAuditQueryDto);
}

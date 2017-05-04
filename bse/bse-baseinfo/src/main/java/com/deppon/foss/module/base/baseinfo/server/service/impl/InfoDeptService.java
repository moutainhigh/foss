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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/InfoDeptService.java
 * 
 * FILE NAME        	: InfoDeptService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 修订记录 日期 修订内容 修订人员 版本号 2012-6-4 新增 谢艳涛 V0.1 2012-07-10
 * 根据ITA评审意见，删除填表人、审核人、填表日期、总体评述，增加营业执照编号 石巍 V0.2 2012-08-09
 * 通过业务部门审核，版本升至0.9 石巍 V0.9
 * 1. SUC-222-新增_修改_作废_查询信息部信息 1.1 相关业务用例 BUC_FOSS_5.10.20_026 提出外请车需求
 * BUC_FOSS_5.70.10_012 整车约车 BUC_FOSS_5.70.10_016 整车：受理约车 1.2 用例描述
 * 该系统用例用于对信息部基础资料的维护，可以对信息部基础资料进行新增、修改、作废、查询。 1.3 用例条件 条件类型 描述 引用系统用例 前置条件
 * 后置条件 1、 为“提交外请车约车申请”系统用例提供信息部基础资料查询 SUC-375 提交外请车约车申请 1.4 操作用户角色 操作用户 描述
 * 系统管理员 系统管理员对信息部基础信息进行新增、修改、作废、查询操作。 1.5 界面要求 1.5.1 表现方式 Web页面 1.5.2
 * 界面原型-主界面 图一：信息部信息管理主界面 1.5.3 界面描述-主界面 1. 功能按钮区域 1)
 * 新增按钮：点击新增按钮进入新增界面，参见【图二：信息部基础资料新增和修改界面】。 2)
 * 查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。 3) 重置按钮：点击重置按钮，重置查询条件。 4)
 * 作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；需要弹出确认提示框。 5)
 * 修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：信息部基础资料新增和修改界面】。 6) 分页按钮：实现分页功能。 2. 列表区域 1)
 * 列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。 2)
 * 列表中显示：信息部名称、业务联系人、联系电话、手机号码、传真号码、信息部性质、总分、审核人、日期。 3. 字段输入区域 1)
 * 查询条件包括信息部名称、业务联系人、手机号码、信息部性质。 1.1 信息部名称：文本，支持模糊查询 1.2 业务联系人：文本，支持模糊查询 1.3
 * 手机号码：数字，支持模糊查询 1.4 信息部性质：下拉框，包含企业公司、个体户、其他、全部，默认为全部 1.5.4 界面原型-新增和修改界面
 * 
 * 图二：信息部基础资料新增和修改界面 1.5.5 界面描述-新增和修改界面 1. 字段输入区域 1) 信息部名称： 必填，文本 2)
 * 业务联系人：必填，文本 3) 信息部性质：必填，下拉框，包含企业公司、个体户、其他 4) 手机号码：必填，数字，遵循手机号码规则，只能为11位
 * 5) 传真号码：选填，数字 6) 联系电话：选填，数字 7) 注册资本：选填，数字 8) 法人代表：选填，文本 9) 联系地址：必填，文本 10)
 * 标准得分1：必填，数字 11) 标准得分2：必填，数字 12) 标准得分3：必填，数字 13) 标准得分4：必填，数字 14)
 * 标准得分5：必填，数字 15) 标准得分6：必填，数字 16) 备注1：选填 17) 备注2：选填 18) 备注3：选填 19) 备注4：选填
 * 20) 备注5：选填 21) 备注6：选填 22) 总体评述（15分）：必填 23) 总分：必填，数字，自动生成 24) 采用意见：选填 25)
 * 信息部老板身份证正面（图片）：必填 26) 身份证反面（图片）：必填 27) 营业执照复印件（图片）：选填 28) 信息部照片：选填 2.
 * 功能按钮区域 1) 本地照片按钮：点击本地照片按钮，弹出文件选择框，选择要上传的图片。 2)
 * 保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功
 * ，关闭当前页面，返回到主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。。 3) 重置按钮：点击重置按钮，回到当前界面的初始状态。
 * 4) 取消按钮：点击取消按钮，退出当前界面，返回主界面。 1.6 操作步骤 1.6.1 新增信息部信息操作步骤 序号 基本步骤 相关数据 补充步骤
 * 1 进入信息部信息管理主界面 【信息部基础资料列表信息】 2 点击新增按钮，进入新增和修改界面 3 输入信息部详细信息，点击保存。
 * 参见业务规则SR-1、SR-2 【信息部基础资料信息】 4 返回信息部信息管理主界面
 * 
 * 序号 扩展事件 相关数据 备注 3a 点击取消按钮，退出当前界面，返回主界面 3b 若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面
 * 
 * 1.6.2 修改信息部信息操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入信息部信息管理主界面 【信息部基础资料列表信息】 2
 * 点击修改按钮，进入新增和修改界面 3 修改信息部详细信息,点击保存 参见业务规则SR-1、SR-2 【信息部基础资料信息】 4
 * 返回信息部信息管理主界面
 * 
 * 序号 扩展事件 相关数据 备注 3a 点击取消按钮，退出当前界面，返回主界面 3b 若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面
 * 
 * 1.6.3 作废信息部信息操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入信息部信息管理主界面 【信息部基础资料列表信息】 2
 * 点击作废图标，可以作废当前记录；选择一行记录或多行记录，点击作废按钮，可以作废多条记录。 弹出确认对话框。 3 点击确定按钮。
 * 
 * 序号 扩展事件 相关数据 备注 2a 点击取消按钮，退出当前界面，返回主界面 2b 若作废失败，需提示用户作废失败以及失败原因
 * 
 * 1.6.4 查询信息部信息操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入信息部信息管理主界面 【信息部基础资料列表信息】 2
 * 输入查询条件，点击查询按钮 【信息部基础资料查询条件】 支持模糊查询，系统返回查询结果 1.7 业务规则 序号 描述 SR-1
 * 总分等于各项选择标准得分之和，为自动计算生成，不支持手动编辑； SR-2
 * 信息部老板身份证正面、身份证反面、营业执照复印件、信息部照片上传只支持图片格式；
 * 
 * 1.8 数据元素 1.8.1 信息部基础资料信息 字段名称 说明 输入限制 输入项提示文本 长度 是否必填 备注 信息部名称 信息部名称 文本
 * 50 是 业务联系人 业务联系人名称 文本 10 是 联系电话 联系电话 数字 15 否 手机号码 手机号码 数字 11 是 传真号码 传真号码
 * 数字 15 否 信息部性质 营业部性质：企业公司、个体户、其他、全部 文本 2 是 注册资本 注册资本（万元） 数字 4 否 法人代表 法人代表
 * 文本 10 否 营业执照编号 信息部营业执照编号 文本 40 是 联系地址 信息部所在地址 文本 200 是 标准得分1
 * 标准得分1：不能大于35分 数字 标准得分1不能大于35分 2 是 标准得分2 标准得分2：不能大于15分 数字 标准得分2不能大于15分 2 是
 * 标准得分3 标准得分3：不能大于10分 数字 标准得分3不能大于10分 2 是 标准得分4 标准得分4：不能大于20分 数字
 * 标准得分4不能大于20分 2 是 标准得分5 标准得分5：不能大于15分 数字 标准得分5不能大于15分 2 是 标准得分6
 * 标准得分6：不能大于5分 数字 2 是 备注1 备注1 文本 200 否 备注2 备注2 文本 200 否 备注3 备注3 文本 200 否
 * 备注4 备注4 文本 200 否 备注5 备注5 文本 200 否 备注6 备注6 文本 200 否 总分 总分：自动计算得，不允许编辑 数字 3
 * 是 采用意见 采用意见 文本 100 否 信息部老板身份证正面（图片） 信息部老板身份证正面（图片） 图片 上传格式为图片格式 5M 是
 * 身份证反面（图片） 身份证反面（图片） 图片 上传格式为图片格式 5M 是 营业执照复印件（图片） 营业执照复印件（图片） 图片
 * 上传格式为图片格式 5M 否 信息部照片 信息部照片 图片 上传格式为图片格式 5M 否 1.8.2 信息部基础资料列表信息 字段名称 说明
 * 输入限制 长度 是否必填 备注 信息部名称 信息部名称 N/A 50 N/A 业务联系人 业务联系人名称 N/A 10 N/A 联系电话 联系电话
 * N/A 15 N/A 手机号码 手机号码 N/A 11 N/A 传真号码 传真号码 N/A 15 N/A 信息部性质
 * 营业部性质：企业公司、个体户、其他、全部，默认为全部 N/A 2 N/A 营业执照编号 信息部营业执照编号 N/A 2 N/A 1.8.3
 * 信息部基础资料查询条件 字段名称 说明 输入限制 长度 是否必填 备注 信息部名称 信息部名称 文本 50 否 业务联系人 业务联系人名称 文本
 * 10 否 信息部性质 营业部性质：企业公司、个体户、其他、全部，默认为全部 下拉框 2 是 默认为全部
 * 
 * 1.9 非功能性需求 使用量 2012年全网估计用户数 响应要求（如果与全系统要求 不一致的话） 使用时间段 高峰使用时间段
 * 
 * 1.10 接口描述 接口名称 对方系统（外部系统或内部其他模块） 接口描述
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncInfoDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.InfoDeptException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互“信息部”的数据库对应数据访问Service接口实现类：SUC-222
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-12-17 下午3:44:25
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-12-17 下午3:44:25
 * @since
 * @version
 */
public class InfoDeptService implements IInfoDeptService {

    // "信息部"Dao
    private IInfoDeptDao infoDeptDao;

    // "信息部得分"Service
    private InfoDeptScoresService infoDeptScoresService;
    
    //同步信息部接口
    private ISyncInfoDeptService syncInfoDeptService;
    /**
     * <p>
     * 新增一个“信息部”实体入库（忽略实体中是否存在空值）
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:43
     * @param infoDept
     *            “信息部”实体
     * @param createUser
     *            创建人
     * @param ignoreNull
     *            true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @return
     * @throws InfoDeptException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptService#addInfoDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity,
     *      java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addInfoDept(InfoDeptEntity infoDept, String createUser,
	    boolean ignoreNull) throws InfoDeptException {
	if (null == infoDept) {
	    throw new InfoDeptException("信息部为空");
	}
	if (StringUtils.isBlank(infoDept.getName())) {
	    throw new InfoDeptException("信息部名称为空");
	}
	if (StringUtils.isBlank(infoDept.getOperateLicense())) {
	    throw new InfoDeptException("营业部执照编号为空");
	}

	// 验证对应身份证号"信息部"是否已经存在
	InfoDeptEntity oldInfoDept, tempInfoDept = new InfoDeptEntity();
	tempInfoDept.setOperateLicense(infoDept.getOperateLicense());
	oldInfoDept = queryInfoDeptBySelective(tempInfoDept);

	if (null != oldInfoDept) {
	    throw new InfoDeptException("信息部已经存在");
	} else {
	    // "信息部"存储
	    infoDept.setCreateUser(createUser);
	    int result1=0;
	    int result2=0;
	    if (ignoreNull) {
	    	result1=infoDeptDao.addInfoDeptBySelective(infoDept);
	    } else {
	    	result2=infoDeptDao.addInfoDept(infoDept);
	    }
	    if (result1>0||result2>0) {
	    	syncInfoDeptToCUBC(infoDept,"1");	
		}
	    // "信息部得分"存储
	    List<InfoDeptScoresEntity> infoDeptScoresList = infoDept
		    .getInfoDeptScoresList();
	    if (CollectionUtils.isNotEmpty(infoDeptScoresList)) {
		for (InfoDeptScoresEntity infoDeptScores : infoDeptScoresList) {
		    infoDeptScores.setInfodeptId(infoDept.getId());
		    infoDeptScoresService.addInfoDeptScores(infoDeptScores,
			    createUser, ignoreNull);
		}
	    }
	}
	return FossConstants.SUCCESS;
    }

    private void syncInfoDeptToCUBC(InfoDeptEntity infoDept, String operateType) {
	 List<InfoDeptEntity> entitys = new ArrayList<InfoDeptEntity>();
	 entitys.add(infoDept);
	 if (CollectionUtils.isNotEmpty(entitys)) {
		 syncInfoDeptService.SyncInfoDept(entitys, operateType);
	}
	
	}

	/**
     * <p>
     * 根据“信息部”记录标识集合删除（物理删除）多条“信息部”记录
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:42
     * @param ids
     *            记录唯一标识集合
     * @param modifyUser
     *            修改人
     * @return 1：成功；0：失败
     * @throws InfoDeptException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptService#deleteInfoDept(java.util.List,
     *      java.lang.String)
     */
    @Override
    @Transactional
    public int deleteInfoDept(List<String> ids, String modifyUser)
	    throws InfoDeptException {
	if (CollectionUtils.isEmpty(ids)) {
	    throw new InfoDeptException("ID 为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new InfoDeptException("修改人信息为空");
	}
	// 待优化实现
	for (String id : ids) {
	    // 验证对应身份证号"信息部"是否已经存在
	    InfoDeptEntity infoDept = queryInfoDeptBySelective(id);
	    if (null == infoDept) {
		throw new InfoDeptException("信息部不存在");
	    } else {
		infoDept.setModifyUser(modifyUser);
		infoDept.setActive(FossConstants.INACTIVE);
	    }
	    // 逻辑删除，直接“停用”
	    int result =infoDeptDao.updateInfoDeptBySelective(infoDept);
	    if (result>0) {
	    	syncInfoDeptToCUBC(infoDept,"3");
		}
	    // "信息部得分"逻辑删除
	    InfoDeptScoresEntity tempInfoDeptScores = new InfoDeptScoresEntity();
	    tempInfoDeptScores.setInfodeptId(infoDept.getId());
	    List<InfoDeptScoresEntity> infoDeptScoresList = infoDeptScoresService
		    .queryInfoDeptScoresListBySelectiveCondition(tempInfoDeptScores);
	    for (InfoDeptScoresEntity infoDeptScores : infoDeptScoresList) {
		infoDeptScores.setModifyUser(modifyUser);
		infoDeptScores.setActive(FossConstants.INACTIVE);
		infoDeptScoresService.updateInfoDeptScores(infoDeptScores,
			modifyUser, true);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>
     * 修改一个“信息部”实体入库（忽略实体中是否存在空值）
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:48
     * @param infoDept
     *            “信息部”实体
     * @param modifyUser
     *            修改人
     * @param ignoreNull
     *            true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws InfoDeptException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptService#updateInfoDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity,
     *      java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateInfoDept(InfoDeptEntity infoDept, String modifyUser,
	    boolean ignoreNull) throws InfoDeptException {
	if (null == infoDept) {
	    throw new InfoDeptException("信息部为空");
	}
	if (StringUtils.isBlank(infoDept.getId())) {
	    throw new InfoDeptException("信息部ID为空");
	}
	if (StringUtils.isBlank(infoDept.getName())) {
	    throw new InfoDeptException("信息部名称为空");
	}
	if (StringUtils.isBlank(infoDept.getOperateLicense())) {
	    throw new InfoDeptException("营业部执照编号为空");
	}

	// 验证对应身份证号"信息部"是否已经存在
	InfoDeptEntity oldInfoDept, tempInfoDept = new InfoDeptEntity();
	if (StringUtils.isNotBlank(infoDept.getId())) {
	    tempInfoDept.setId(infoDept.getId());
	} else {
	    tempInfoDept.setOperateLicense(infoDept.getOperateLicense());
	}
	oldInfoDept = queryInfoDeptBySelective(tempInfoDept);
	if (null == oldInfoDept) {
	    throw new InfoDeptException("信息部不存在");
	} else {
	    infoDept.setModifyUser(modifyUser);
	    int result1=0;
	    int result2=0;
	    if (ignoreNull) {
	    	result1=infoDeptDao.updateInfoDeptBySelective(infoDept);
	    } else {
	    	result2=infoDeptDao.updateInfoDept(infoDept);
	    }
	   if ((result1>0||result2>0)) {
			syncInfoDeptToCUBC(infoDept, "2");
		}
	    // "信息部得分"存储
	    List<InfoDeptScoresEntity> infoDeptScoresList = infoDept
		    .getInfoDeptScoresList();
	    /**
	     * <p>
	     * 增加 判空
	     * </p>
	     * 
	     * @author 073586-FOSS-LIXUEXING
	     * @date 201303091607
	     */
	    if (CollectionUtils.isNotEmpty(infoDeptScoresList)) {
		for (InfoDeptScoresEntity infoDeptScores : infoDeptScoresList) {
		    if (StringUtils.isBlank(infoDeptScores.getId())) {
			infoDeptScores.setInfodeptId(infoDept.getId());
			infoDeptScoresService.addInfoDeptScores(infoDeptScores,
				modifyUser, ignoreNull);
		    } else {
			infoDeptScoresService.updateInfoDeptScores(
				infoDeptScores, modifyUser, ignoreNull);
		    }
		}
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>
     * 根据“信息部”记录唯一标识查询出一条“信息部”记录
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:45
     * @param id
     *            记录唯一标识
     * @return “信息部”实体
     * @throws InfoDeptException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptService#queryInfoDeptBySelective(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public InfoDeptEntity queryInfoDeptBySelective(String id)
	    throws InfoDeptException {
	if (StringUtils.isBlank(id)) {
	    throw new InfoDeptException("ID 为空");
	}
	InfoDeptEntity infoDept = new InfoDeptEntity();
	infoDept.setId(id);
	return infoDeptDao.queryInfoDeptBySelective(infoDept);
    }

    /**
     * <p>
     * 根据条件有选择的检索出符合条件一个的“信息部”实体（条件做自动判断，只选择实体中非空值）
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:23:53
     * @param infoDept
     *            以“信息部”实体承载的条件参数实体
     * @return 符合条件的“信息部”实体
     * @throws InfoDeptException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptService#queryInfoDeptBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity)
     */
    @Override
    @Transactional(readOnly = true)
    public InfoDeptEntity queryInfoDeptBySelective(InfoDeptEntity infoDept)
	    throws InfoDeptException {
	if (null == infoDept) {
	    throw new InfoDeptException("信息部为空");
	}
	return infoDeptDao.queryInfoDeptBySelective(infoDept);
    }

    /**
     * <p>
     * 根据条件有选择的检索出符合条件的“信息部”实体列表（条件做自动判断，只选择实体中非空值）
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:46
     * @param ownTruck
     *            以“信息部”实体承载的条件参数实体
     * @param offset
     *            开始记录数
     * @param limit
     *            限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @throws InfoDeptException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptService#queryInfoDeptListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity,
     *      int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryInfoDeptListBySelectiveCondition(
	    InfoDeptEntity infoDept, int offset, int limit)
	    throws InfoDeptException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	List<InfoDeptEntity> infoDeptList = infoDeptDao
		.queryInfoDeptListBySelectiveCondition(infoDept, offset, limit);
	if (CollectionUtils.isNotEmpty(infoDeptList)) {
	    Long totalCount = infoDeptDao
		    .queryInfoDeptRecordCountBySelectiveCondition(infoDept);
	    paginationDto.setPaginationDtos(infoDeptList);
	    paginationDto.setTotalCount(totalCount);
	}
	
	return paginationDto;
    }

    /**
     * @param infoDeptDao
     *            the infoDeptDao to set
     */
    public void setInfoDeptDao(IInfoDeptDao infoDeptDao) {
	this.infoDeptDao = infoDeptDao;
    }

    /**
     * @param infoDeptScoresService
     *            the infoDeptScoresService to set
     */
    public void setInfoDeptScoresService(
	    InfoDeptScoresService infoDeptScoresService) {
	this.infoDeptScoresService = infoDeptScoresService;
    }

	public void setSyncInfoDeptService(ISyncInfoDeptService syncInfoDeptService) {
		this.syncInfoDeptService = syncInfoDeptService;
	}
    
}

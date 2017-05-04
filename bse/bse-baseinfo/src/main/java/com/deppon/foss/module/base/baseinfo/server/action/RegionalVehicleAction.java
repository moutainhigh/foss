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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/RegionalVehicleAction.java
 * 
 * FILE NAME        	: RegionalVehicleAction.java
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

    /**
     * 1.5.3 界面描述-定人定区查询主界面 
     * 
     * 该界面分为四个部分：
     * 
     * 集中接送货大小区树形展示区，
     * 
     * 查询条件区域、查询结果列表、
     * 
     * 
     * 功能按钮区； 1.
     * 
     * 集中接送货大小区树形展示区：
     * 
     * 该区域的树形结构数据来源于集
     * 
     * 中接送货大区基础资料和集中接
     * 
     * 送货小区基础资料； 2.
     * 
     * 查询条件区域：查询条件包括车队
     * 
     * 、车牌号、车辆职责类别； 1.1
     * 
     * 车牌号：共用选择框，读取公司厢
     * 
     * 式车基础资料中车辆所属部门为该界
     * 
     * 面“车队”的接送货车； 1.2 车队：
     * 
     * 读取当前登录部门对应的车队，
     * 
     * 不可输入； 1.3
     * 车辆职责类别：下拉框，包括全部、
     * 
     * 定区车、机动车，默认为全部； 3.
     * 
     * 查询结果列表：数据元素参见
     * 
     * 【定人定区查询结果列表】，
     * 
     * 双击列表中某行记录可
     * 
     * 打开定人定区详情界面； 4.
     * 
     * 
     * 功能按钮区：按钮包括查找、
     * 
     * 新增、修改、作废、查询、重置； 
     * 
     * 
     * 查找：该按钮位于集中接送货大小
     * 
     * 区树形展示区的顶部，输入区域名
     * 
     * 称或者编号后，点击该按钮，
     * 
     * 可在树形区域中定位输入的区域； 
     * 
     * 新增：此按钮位于系统悬浮工具条中，
     * 
     * 点击可打开定人定区新增界面(图2)； 
     * 
     * 
     * 修改：图标按钮，位于【定人定区
     * 
     * 查询结果列表】最前面的操作列，
     * 1.5.3 界面描述-定人定区查询主界面 
     * 
     * 该界面分为四个部分：
     * 
     * 集中接送货大小区树形展示区，
     * 
     * 查询条件区域、查询结果列表、
     * 
     * 
     * 功能按钮区； 1.
     * 
     * 集中接送货大小区树形展示区：
     * 
     * 该区域的树形结构数据来源于集
     * 
     * 中接送货大区基础资料和集中接
     * 
     * 送货小区基础资料； 2.
     * 
     * 查询条件区域：查询条件包括车队
     * 
     * 、车牌号、车辆职责类别； 1.1
     * 
     * 车牌号：共用选择框，读取公司厢
     * 
     * 式车基础资料中车辆所属部门为该界
     * 
     * 面“车队”的接送货车； 1.2 车队：
     * 
     * 读取当前登录部门对应的车队，
     * 
     * 不可输入； 1.3
     * 车辆职责类别：下拉框，包括全部、
     * 
     * 定区车、机动车，默认为全部； 3.
     * 
     * 查询结果列表：数据元素参见
     * 
     * 【定人定区查询结果列表】，
     * 
     * 双击列表中某行记录可
     * 
     * 打开定人定区详情界面； 4.
     * 
     * 
     * 功能按钮区：按钮包括查找、
     * 
     * 新增、修改、作废、查询、重置； 
     * 
     * 
     * 查找：该按钮位于集中接送货大小
     * 
     * 区树形展示区的顶部，输入区域名
     * 
     * 称或者编号后，点击该按钮，
     * 
     * 可在树形区域中定位输入的区域； 
     * 
     * 新增：此按钮位于系统悬浮工具条中，
     * 
     * 点击可打开定人定区新增界面(图2)； 
     * 
     * 
     * 修改：图标按钮，位于【定人定区
     * 
     * 查询结果列表】最前面的操作列，
     * 
     * 点击可打开定人定区修改界面(图2)； 
     * 
     * 作废：【定人定区查询结果列表】
     * 
     * 顶部和下部各有一个
     * 
     * 
     * 
     * 
     * “作废”按钮，点击可作废选中的定人
     * 
     * 定区信息；另外存在“作废”的图标按
     * 
     * 钮，位于【定人定区查询结果列表】
     * 
     * 最前面的操作列，点击可作废该行
     * 
     * 定人定区信息；
     * 
     *  查询：点击此按钮查询符合条件
     * 
     * 的定人定区信息；  重置：重新初
     * 
     * 始化【定人定区查询条件】； 5.
     * 
     * 
     * 提供的相关用例链接或提示信息：
     * 
     * 作废定人定区信息成功后
     * 
     * 
     * ，提示操作成功，同时刷新【定人
     * 
     * 定区查询结果列表】，停留在查询页
     * 
     * 面，可继续执行本用例；作废失败后
     * 
     * ，提示失败原因。 1.5.5
     * 
     * 界面描述-定人定区新增、修改界面 在
     * 
     * 
     * 
     * 图1界面中，点击悬浮工具条中的“新增
     * 
     * ”按钮，弹出该界面；该界面分为两个部
     * 
     * 分：定人定区信息输入区域，功能按钮区
     * ；
     * 1. 定人定区信息输入区域：字段包括区域
     * 
     * 、区域编码、车牌号、车辆所属车队、
     * 
     * 车辆职责类别； 1.1
     * 
     * 区域：共用选择框，读取集中接送货大区、
     * 
     * 小区基础资料； 1.2 区域编码：根据所选择
     * 
     * 的大区或者小区自动读取； 1.3
     * 
     * 
     * 接送货类型：如果选择的“区域”为大区，则
     * 
     * 
     * 读取该大区的区域类型；如果选择的“区域
     * 
     * ”为小区，则读取该小区所属大区的区域类
     * 
     * 型； 1.4
     * 车牌号：共用选择框，读取公司厢式车基
     * 
     * 础资料中的接送货车； 1.5 车辆所属车队
     * 
     * ：根据车牌号自动读取； 1.6
     * 
     * 
     * 
     * 车辆职责类型：如果选择的区域为大区，此
     * 
     * 处默认为机动车；如果选择的区域为小区，
     * 
     * 此处默认为定区车； 2. 功能按钮区：按钮包
     * 
     * 括保存、取消、重置； 
     * 
     * 
     * 保存：点击此按钮保存定人定区信息；  取
     * 
     * 消：退出当前界面；  重置：点击此按钮重
     * 
     * 新初始化输入区域各控件； 3.
     * 
     * 
     * 提供的相关用例链接或提示信息：保存成功
     * 
     * 后，提示操作成功，停留在当前界面，界面中
     * 
     * 各控件不可输入；保存失败后，提示失败原因。 1.5.7
     * 
     * 界面描述-查看大区对应车辆信息界面
     * 
     * 
     * 点击左边大小区树形结构的某大区后，主界
     * 
     * 面的右下方区域(图3红框区域)显示点击
     * 
     * 的大区的基本信息和该大区所对应的机动车信息
     * 
     * 
     * ；该部分界面分为三部分：大区基本信息、
     * 
     * 大区机动车列表、功能按钮； 1. 大区基本
     * 
     * 信息：包括大区名称、大区编码、大区类
     * 
     * 型、管理部门； 1.1
     * 
     * 
     * “查看电子地图”超链接：点击该链接，
     * 
     * 打开该大区的电子地图界面； 2.
     * 
     * 
     * 大区机动车列表：列表显示大区对应的机
     * 
     * 动车，包括车牌号和车辆所属车队，
     * 
     * 第一列为操作列，包括“作废”和“修改”的图标按钮； 3.
     * 
     * 功能按钮：按钮包括添加机动车、作废、修改； 3.1
     * 
     * 
     * 添加机动车：点击该按钮弹出图4界面，
     * 
     * 该界面的“区域”、“区域编码”、“接送货
     * 
     * 类型”默认为树形结构中选中的大区
     * 
     * 
     * 、大区编码及大区接送货类型，“车辆所属
     * 
     * 车队”根据车牌号自动读取，
     * 
     * “车辆职责类型”默认为机动车； 3.2
     * 
     * 
     * 作废：机动车列表中的操作列存在“作废
     * 
     * ”的图标按钮
     * 
     * 
     * 
     * ，点击该按钮，将该车辆从当前大
     * 
     * 区中移除；机动车列表左下方存在“
     * 
     * 作废”按钮，点击该按钮，将列
     * 
     * 表中选中的车辆从当前大区中移除； 3.3
     * 
     * 
     * 修改：该图标按钮位于机动车列表
     * 
     * 的操作列中，点击该按钮弹出图
     * 
     * 4界面； 1.5.9 界面描述-查看
     * 
     * 小区对应车辆信息界面
     * 
     * 
     * 点击左边大小区树形结构的某小区
     * 后
     * 
     * ，主界面的右下方(图5红框区域)
     * 
     * 区域显示点击的小区的基本信息和
     * 
     * 该小区所对应的定区车信息；
     * 
     * 该部分界面分为三部分：
     * 
     * 小区基本信息
     * 
     * 、小区定区车列表、
     * 
     * 功能按钮； 1. 小区基本信息：
     * 
     * 包括小区名称、小区编码、
     * 
     * 管理部门； 1.1
     * 
     * 
     * “查看电子地图”超链接：点击
     * 
     * 
     * 该链接，打开该小区的电子地图
     * 
     * 界面； 2.
     * 
     * 
     * 小区定区车列表：列表显示小区
     * 
     * 对应的定区车，包括车牌号和车辆所属
     * 
     * 车队，第一列为操作列，包括“作废”和
     * 
     * “修改”的图标按钮； 3.
     * 
     * 
     * 功能按钮：按钮包括添加定区车、作
     * 
     * 废、修改； 3.1
     * 添加定区车：点击该按钮弹出图6界
     * 
     * 面，该界面的“区域”、“区域编码”读
     * 
     * 取树形结构中选中的小区及小区编码
     * 
     * ，“接送货类型”读取该小区所属大区的
     * 
     * 区域类型，“车辆所属车队”根据车牌号
     * 
     * 自动读取，“车辆职责类型”默认为定区车； 3.2
     * 
     * 
     * 作废：定区车列表中的操作列存在
     * 
     * “作废”的图标按钮，点击该按钮，
     * 
     * 将该车辆从当前小区中移除；
     * 
     * 定区车列表左下方存在“作废”按钮
     * 
     * ，点击该按钮，将列表中选中的车辆从当前小区中移除；
     * 
     * 
     * 3.3 修改：该图标按钮位于定区车列表的操作列中，
     * 
     * 点击该按钮弹出图6界面；
     * 
     *  SR-1 “车牌号”和“区域”的组合不可重复；
     *  
     * 一辆车只能对应一种“车辆职责类型”； 
     * 
     * SR-2
     * 一个集中接送货小区是否允许有两个及
     * 
     * 两个以上的“接送货类型”相同的定区车
     * 
     * 可配置。当配置为“不允许”时，
     * 
     * 用户在“定人定区新增界面”点击“保存”
     * 
     * 时，若所选小区已有
     * 
     * “接送货类型”相同的定区车时，
     * 
     * 则不允许保存。在“查看小区对应车辆信息界面”中，
     * 
     * 若定区车列表不为空时，则隐藏“添加定区车”按钮。
     * 
     *  SR-3
     * 当为某小区或大区添加车辆时，
     * 
     * 只能选择负责管理该小区或大区的管理部门
     * 
     * 对应的车队所拥有的接送货车； 
     * 
     * SR-4
     * 查询定人定区信息时，界面左方的大区小区
     * 
     * 树形结构中只显示当前登录部门负责管理的大
     * 
     * 
     * 、小区；查询条件中的“车牌号”只可选择当
     * 
     * 前登录部门所对应车队所属的车辆；
     * 
     *  SR-5 用户登录后默认显示对应车队
     *  
     *  的定人定区信息，且不可编辑。
     *  
     * SR-6 当车队有多级时，
     * 
     * 用户可以查看和修改其所在级
     * 
     * 别及所在级别管辖的其它所有车队的定人定区信息。
     * 
     * 用户可通过选择或筛选获得
     * 
     * 其可查看的其它车队的定人定区信息。
     * 
     */
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.RegionalVehicleException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PAndDeliveryZoneRegionVo;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.MessageType;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定人定区action.
 *
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 14:10:10
 * @since
 * @version 0.01
 */
@SuppressWarnings("rawtypes")
public class RegionalVehicleAction extends AbstractAction {

    /**
     * 下面是声明的属性.
     */
    private static final long serialVersionUID = -1518535363712925187L;

    /**
     * 定人定区service接口.
     */
    private IRegionalVehicleService regionalVehicleService;

    /**
     * 接送货大区service接口.
     */
    private IPickupAndDeliveryBigZoneService pickupAndDeliveryBigZoneService;

    /**
     * 接送货小区service接口.
     */
    private IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService;
    
    /**
     * 部门 复杂查询 service.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    /**
     * 系统配置参数 Service接口.
     */
    private IConfigurationParamsService configurationParamsService;

    /**
     * 定人定区 使用VO.
     */
    private PAndDeliveryZoneRegionVo objectVo = new PAndDeliveryZoneRegionVo();

    /**
     * 树节点.
     */
    private List<TreeNode> nodes;

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(RegionalVehicleAction.class);
    
	/**
     * 导出Excel 文件名.
     */
    private String downloadFileName;

    /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;
    
    
    
    /**
     * 设置 系统配置参数 Service接口.
     *
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    /**
     * <p>
     * 查询定人定区
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     */
    @JSON
    public String queryRegionalVehicleByEntity() {
	RegionVehicleInfoDto entity = objectVo.getRegionVehicleDto();
	//获取当前部门编码
	String currentUserDeptCode = FossUserContext. getCurrentDeptCode();
	//查找指定部门的上级第一个顶级车队，然后找该顶级车队下属的所有有集中接送货属性的车队的部门编码集合
	List<String> deptCodeList = orgAdministrativeInfoComplexService.queryDeptCodeListFromTopFleetByCode(currentUserDeptCode); 
	entity.setVehicleDeptList(deptCodeList);
	// 返回的结果显示在表格中：
	objectVo.setRegionVehicleDtoList(regionalVehicleService
		.queryRegionalVehicleInfoDtos(entity, limit, start));
	totalCount = regionalVehicleService.queryRecordCount(entity);
	return returnSuccess();
    }
    
    /**
     * <p>根据当前登录用户部门编码查找指定部门的上级第一个顶级车队，然后找该顶级车队下属的所有有集中接送货属性的车队的部门编码集合</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-3-29 下午2:43:24
     * @see
     */
    @JSON
    public String queryDeptCodes(){
	try {
	    //获取当前部门名称
	    String currentUserDeptCode = FossUserContext. getCurrentDeptCode();
	    //查找指定部门的上级第一个顶级车队，然后找该顶级车队下属的所有有集中接送货属性的车队的部门编码集合
	    List<String> deptCodeList = orgAdministrativeInfoComplexService.queryDeptCodeListFromTopFleetByCode(currentUserDeptCode); 
	    //设置 部门编码集合.
	    objectVo.setDeptCodeList(deptCodeList);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
	
    }

    /**
     * <p>
     * 通过所选菜单区域，查询定车定区
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     */
    //update by 092020-lipengfei 为了统一管理，前台统一使用RegionVehicleInfoDto
    public String queryBigZoneDetailByCode() {
	RegionVehicleInfoDto entityCondition = objectVo.getRegionVehicleDto();
	RegionVehicleDto rDto = regionalVehicleService.queryInfoByCode(
		entityCondition.getRegionCode(),
		entityCondition.getRegionNature());
	objectVo.setRegionVehicleDtoList(rDto.getRegionVehicleInfoDtoList());
	return returnSuccess();
    }

    /**
     * <p>
     * 通过条件查询小区详情
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     */
    public String querySmallZoneDetailByCode() {
	// TODO 查询出 对应 小区信息以及 定人定区
	return returnSuccess();
    }

    /**
     * <p>
     * 通过条件查询大小区树
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     */
    public String queryZoneByNameOrNum() {
	nodes = new ArrayList<TreeNode>();
	String parentCode = objectVo.getCodeStr();
	if (StringUtils.isBlank(parentCode)) {
	    // 如果查询的名称为空，则直接返回，通过根结点来展开树
	    return returnSuccess();
	}
	// 优先 按照 区域 编码,名称 查询 大区
	BigZoneEntity entityConditionB = new BigZoneEntity();
	entityConditionB.setRegionCode(parentCode);
	entityConditionB.setRegionName(parentCode);
	List<BigZoneEntity> bigEntityList = pickupAndDeliveryBigZoneService
		.queryBigZonesByNameOrCode(entityConditionB);
	if (CollectionUtils.isNotEmpty(bigEntityList)) {
	    BigZoneEntity big = bigEntityList.get(0);
	    objectVo.setCodeStr(SymbolConstants.EN_PERIOD + ComnConst.rootParentId
		    + SymbolConstants.EN_PERIOD + big.getVirtualCode());
	} else {
	    SmallZoneEntity entityConditionS = new SmallZoneEntity();
	    entityConditionS.setRegionCode(parentCode);
	    entityConditionS.setRegionName(parentCode);
	    List<SmallZoneEntity> smallEntityList = pickupAndDeliverySmallZoneService
		    .querySmallZonesByNameOrCode(entityConditionS);
	    nodes = getSmallZoneTreeNotes(nodes, smallEntityList,
		    ComnConst.rootParentId);
	    if (CollectionUtils.isEmpty(smallEntityList)) {
		objectVo.setCodeStr(null);
	    } else {
		SmallZoneEntity small = smallEntityList.get(0);
		objectVo.setCodeStr(SymbolConstants.EN_PERIOD + ComnConst.rootParentId
			+ SymbolConstants.EN_PERIOD + small.getBigzonecode()
			+ SymbolConstants.EN_PERIOD + small.getRegionCode());
	    }
	}

	return returnSuccess();
    }

    /**
     * <p>
     * 通过 大区 code或者name查询 大区树节点
     * </p>.
     *
     * @param nodes2 
     * @param bigEntityList 
     * @param parentCode 
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     */
    private List<TreeNode> getBigZoneTreeNotes(List<TreeNode> nodes2,
	    List<BigZoneEntity> bigEntityList, String parentCode) {
	if (CollectionUtils.isNotEmpty(bigEntityList)) {
	    for (BigZoneEntity pojo : bigEntityList) {
		TreeNode<BigZoneEntity, TreeNode> treeNode = new TreeNode<BigZoneEntity, TreeNode>();
		treeNode.setId(pojo.getVirtualCode());
		treeNode.setText(pojo.getRegionName()+"【"+pojo.getRegionCode()+"】");
		treeNode.setLeaf(false);
		treeNode.setParentId(parentCode);
		treeNode.setEntity(pojo);
		nodes2.add(treeNode);
	    }
	}
	return nodes2;
    }

    /**
     * <p>
     * 通过 小区 code或者name查询 大区树节点
     * </p>.
     *
     * @param nodes 
     * @param smallEntityList 
     * @param parentCode 
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     */
    private List<TreeNode> getSmallZoneTreeNotes(List<TreeNode> nodes,
	    List<SmallZoneEntity> smallEntityList, String parentCode) {
	if (CollectionUtils.isNotEmpty(smallEntityList)) {
	    for (SmallZoneEntity pojo : smallEntityList) {
		TreeNode<SmallZoneEntity, TreeNode> treeNode = new TreeNode<SmallZoneEntity, TreeNode>();
		treeNode.setId(pojo.getRegionCode());
		treeNode.setText(pojo.getRegionName()+"【"+pojo.getRegionCode()+"】");
		treeNode.setLeaf(true);
		treeNode.setParentId(parentCode);
		treeNode.setEntity(pojo);
		nodes.add(treeNode);
	    }
	}
	return nodes;
    }

    /**
     * <p>
     * 查询大小区树
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     */
    public String queryPickupAndDeliveryZone() {
	//获取当前部门编码
	List<String> deptCodes = FossUserContext. getCurrentUserManagerDeptCodes();
	// 查找指定多个部门的上级第一个顶级车队，然后找该顶级车队下属的所有有"车队调度组"属性的部门编码集合
	List<String> deptCodeList = orgAdministrativeInfoComplexService.queryDispatchTeamDeptCodeListFromTopFleetByCodeList(deptCodes);
	ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(
		DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,
		ConfigurationParamsConstants.BAS_REGIONAL_VEHICLE_SYSTEM_ADMINSTRATOR, FossUserContext.getCurrentDeptCode());
	
	nodes = new ArrayList<TreeNode>();
	String parentCode = objectVo.getCodeStr();
	//查询区域类型
	String regionType =objectVo.getRegionType();
	if (ComnConst.rootParentId.equals(parentCode)) {
	    // 得到 所有大区
	    BigZoneEntity entityCondition = new BigZoneEntity();
	    if (entity != null) {
		String parmValue = entity.getConfValue();
		if (StringUtils.isNotBlank(parmValue)) {
		    if (!StringUtils.equals(FossConstants.YES, parmValue)) {
			deptCodeList.add(FossUserContext.getCurrentDeptCode());
		    } else {
			deptCodeList = new ArrayList<String>();
		    }
		}
	    } else {
		deptCodeList.add(FossUserContext.getCurrentDeptCode());
	    }
	    //若是区域编码不为空
	    if(StringUtils.isNotBlank(regionType)){
	    	entityCondition.setType(regionType);
	    }
	    //根据多个管理部门查询大区
	    entityCondition.setManagementCodeList(deptCodeList);
	    List<BigZoneEntity> bigEntityList = pickupAndDeliveryBigZoneService
		    .queryPickupAndDeliveryBigZones(entityCondition,
			    Integer.MAX_VALUE, start);
	    nodes = getBigZoneTreeNotes(nodes, bigEntityList,
		    ComnConst.rootParentId);
	} else {
		
	    // 得到 所有小区
	    SmallZoneEntity entityCondition = new SmallZoneEntity();
	  //若是区域编码不为空
	    if(StringUtils.isNotBlank(regionType)){
	    	entityCondition.setRegionType(regionType);
	    }
	    entityCondition.setBigzonecode(parentCode);
	    List<SmallZoneEntity> smallEntityList = pickupAndDeliverySmallZoneService
		    .queryPickupAndDeliverySmallZones(entityCondition,
			    Integer.MAX_VALUE, start);
	    nodes = getSmallZoneTreeNotes(nodes, smallEntityList, parentCode);
	}
	return returnSuccess();
    }

    /**
     * 作废定人定区.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     * @see
     */
    @JSON
    public String deleteRegionalVehicle() {
	try {
		int result =regionalVehicleService.deleteRegionalVehicleByCode(objectVo.getCodeStr(),FossUserContext.getCurrentInfo().getEmpCode());
	    objectVo.setReturnInt(result);
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改定人定区.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     * @see
     */
    public String updateRegionalVehicle() {
	try {
	    objectVo.setReturnInt(regionalVehicleService
		    .updateRegionalVehicle(objectVo.getRegionVehicleDto()));
	    return returnSuccess();
	} catch (RegionalVehicleException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增定人定区.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     * @see
     */
    public String addRegionalVehicle() {
	try {
	    objectVo.setReturnInt(regionalVehicleService
		    .addRegionalVehicle(objectVo.getRegionVehicleDto()));
	    return returnSuccess();
	} catch (RegionalVehicleException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 定人定区 是否重复
     * </p>.
     *
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-12 14:10:10
     */
    public String regionalVehicleIsExist() {
	return returnSuccess();
    }
    
    
    
    /**
     * <p>
     * 导出定人定区EXCEl
     * </p>
     * @return
     * @author 132599-foss-shenweihua
     * @date 2013-8-23 下午2:00:06
     * @see
     */
    //update by 092020-lipengfei 为了统一管理，前台统一使用RegionVehicleInfoDto
    public String exportRegionalVehicleList() {
    	try {
    	    // 导出文件名称
    	    downloadFileName = URLEncoder.encode(
    	    	QueryingConstant.REGION_VEHICLE_NAME, "UTF-8");
    	    // 获取查询参数
    	    RegionVehicleInfoDto entity = objectVo.getRegionVehicleDto();
    	    if (null == entity) {
    	    	entity = new RegionVehicleInfoDto();
    	    }
    	    //获取当前部门编码
    		String currentUserDeptCode = FossUserContext. getCurrentDeptCode();
    		//查找指定部门的上级第一个顶级车队，然后找该顶级车队下属的所有有集中接送货属性的车队的部门编码集合
    		List<String> deptCodeList = orgAdministrativeInfoComplexService.queryDeptCodeListFromTopFleetByCode(currentUserDeptCode); 
    	    //当数据权限小于三千个，设置，否者不设置数据权限进行导出
    		if(deptCodeList.size()<NumberConstants.NUMBER_3500){
    		   entity.setVehicleDeptList(deptCodeList);
    	   }
    	    // 获取导出数据对象
    	    inputStream = regionalVehicleService.queryExportRegionVehicles(entity);

    	    ExportSetting exportSetting = new ExportSetting();
    	    // 设置名称
    	    exportSetting.setSheetName(QueryingConstant.REGION_VEHICLE_NAME);
    	    return returnSuccess();
    	} catch (BusinessException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	} catch (UnsupportedEncodingException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError("UnsupportedEncodingException", e);
    	}
        }
    /**
     * ASP项目优化：leo-zeng 处理
     * 校验区域所属车队是否和用户所选车连车队 是否一致
     * @author leo-zeng
     * @return
     */
    @JSON
    public String validSelectOrgIsRegionOrg(){
    	try {
    		//用户选择的车连所属车队
        	String motorOrg =objectVo.getMotorOrg();
        	//区域编码
        	String regionCode =objectVo.getRegionVehicleDto().getCode();
        	//区域属性
        	String regionNatrue =objectVo.getRegionVehicleDto().getRegionNature();
        	//获取用户所属顶级车队
        	OrgAdministrativeInfoEntity selectOrg=orgAdministrativeInfoComplexService.getTopFleetByCode(motorOrg);
        	//小区
        	if(StringUtils.isNotBlank(regionNatrue)&&StringUtils.equals(regionNatrue, "SQ")){
        		//查询小区
        		SmallZoneEntity	entity=pickupAndDeliverySmallZoneService.querySmallZoneByCode(regionCode);
        		//
        		if(null !=entity){
        			//获取区域所属顶级车队
        			OrgAdministrativeInfoEntity topFeet	=orgAdministrativeInfoComplexService.getTopFleetByCode(entity.getManagement());
        			
        			if(null !=selectOrg && null !=topFeet && StringUtils.equals(selectOrg.getCode(), topFeet.getCode())){
        				objectVo.setReturnInt(1);
        			}else{
        				objectVo.setReturnInt(0);
        			}
        		}
        	//大区	
        	}else if(StringUtils.isNotBlank(regionNatrue)&&StringUtils.equals(regionNatrue, "BQ")){
        		//查询大区
        		BigZoneEntity entity =pickupAndDeliveryBigZoneService.queryBigzoneByCode(regionCode);
        		
        		if(null !=entity){
        			//获取区域所属顶级车队
        			OrgAdministrativeInfoEntity topFeet	=orgAdministrativeInfoComplexService.getTopFleetByCode(entity.getManagement());
        			
        			if(null !=selectOrg && null !=topFeet && StringUtils.equals(selectOrg.getCode(), topFeet.getCode())){
        				objectVo.setReturnInt(1);
        			}else{
        				objectVo.setReturnInt(0);
        			}
        		}
        	}
        	return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
		}
    	
    }
    
    /*
     * =================================================================
     * 下面是get,set方法：
     */
    /**
     * 获取 定人定区 使用VO.
     *
     * @return the 定人定区 使用VO
     */
    public PAndDeliveryZoneRegionVo getObjectVo() {
	return objectVo;
    }

    /**
     * 设置 定人定区 使用VO.
     *
     * @param objectVo the new 定人定区 使用VO
     */
    public void setObjectVo(PAndDeliveryZoneRegionVo objectVo) {
	this.objectVo = objectVo;
    }
    
    /**
     * 设置 部门 复杂查询 service.
     *
     * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
     */
    public void setOrgAdministrativeInfoComplexService(
    	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    /**
     * 获取 树节点.
     *
     * @return the 树节点
     */
    public List<TreeNode> getNodes() {
	return nodes;
    }

    /**
     * 设置 定人定区service接口.
     *
     * @param regionalVehicleService the new 定人定区service接口
     */
    public void setRegionalVehicleService(
	    IRegionalVehicleService regionalVehicleService) {
	this.regionalVehicleService = regionalVehicleService;
    }

    /**
     * 设置 接送货大区service接口.
     *
     * @param pickupAndDeliveryBigZoneService the new 接送货大区service接口
     */
    public void setPickupAndDeliveryBigZoneService(
	    IPickupAndDeliveryBigZoneService pickupAndDeliveryBigZoneService) {
	this.pickupAndDeliveryBigZoneService = pickupAndDeliveryBigZoneService;
    }

    /**
     * 设置 接送货小区service接口.
     *
     * @param pickupAndDeliverySmallZoneService the new 接送货小区service接口
     */
    public void setPickupAndDeliverySmallZoneService(
	    IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService) {
	this.pickupAndDeliverySmallZoneService = pickupAndDeliverySmallZoneService;
    }
    
    /**
     * 获取导出Excel 文件名
     * @return
     */
    public String getDownloadFileName() {
		return downloadFileName;
	}
    
    /**
     * 设置导出Excel 文件名
     * @param downloadFileName
     */
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	/**
	 * 获取导出Excel 文件流
	 * @return
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	
	/**
	 * 设置导出Excel 文件流
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}

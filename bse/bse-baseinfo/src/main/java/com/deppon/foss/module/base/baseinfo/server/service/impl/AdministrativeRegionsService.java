/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * 
 * See the License for the specific language governing permissions and
 * 
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AdministrativeRegionsService.java
 * 
 * FILE NAME        	: AdministrativeRegionsService.java
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
 *      
 *      
 *      
 *      
 *      
 *      
 * *****************************************************************************
 **相关SUC
 *修订记录 	
 日期 	修订内容 	修订人员 	版本号 
 2012-04-24 	新增 	谢艳涛	V0.1
 2012-4-26	修改查询界面	谢艳涛	V0.2
 2012-4-26	 修改相关业务用例	谢艳涛	V0.3 
 2012-5-21	根据系统用例模板及陈兴波GIS行政区域基础数据接口要求进行修改	谢艳涛	V0.4
 2012-7-9	根据ITA评审意见修改：“虚拟城市”改为“虚拟行政区域”；增加“可用名称”属性；界面行政区域树位置移到左边。	谢艳涛	V0.41
 2012-8-6	通过业务部门审核，版本升至0.9	石巍	V0.9

 1.	SUC-33-新增_修改_作废_查询行政区域
 1.1	相关业务用例
 BUS_FOSS_5.20.30_510 确认承运信息
 1.2	用例描述
 本系统用例统一维护所有业务用例中涉及的行政区域的基础信息，可对行政区域基础资料进行新增、修改、作废、查询操作。
 1.3	用例条件
 条件类型	描述	引用系统用例
 前置条件		
 后置条件	1、	为始发线路、到达线路、运作到运作线路等其他系统用例提供行政区域基础资料查询	SUC-284  新增_修改_作废_查询始发线路
 SUC-218  新增_修改_作废_查询运作到运作线路信息
 SUC-740  新增_修改_作废_查询到达线路
 1.4	操作用户角色
 操作用户	描述
 系统维护人员	系统维护人员负责对行政区域基础资料进行新增，修改，作废，查询操作。
 1.5	界面要求
 1.5.1	表现方式
 Web页面
 1.5.2	界面原型-主界面
 图一：行政区域主界面

 图二：查看行政区域信息（一）

 图三：查看行政区域信息（二）
 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	新增按钮：点击新增按钮进入新增界面，参见【图四：新增/修改行政区域】。
 2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
 3)	重置按钮：点击重置按钮，清空查询条件。
 4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废图标，作废各行记录，作废时连同作废该行政区域所包含的子行政区域，需要弹出确认提示框。
 5)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息，参见【图三：查看行政区域信息（二）】；点击左侧行政区域树的某一行政区域也可以查看该行政区域的详细信息，参见【图二：查看行政区域信息（一）】。
 6)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：新增/修改行政区域】。
 7)	分页按钮：实现分页功能。
 2.	列表区域
 1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
 2)	列表中显示：编码、区域全称、简称、可用名称、上级区域名称、虚拟行政区域。
 3.	字段输入区域
 1)	查询条件包括区域全称、简称、虚拟行政区域。
 1.1	区域全称：文本，行政区域名称，支持模糊查询
 1.2	简称：文本，行政区域简称，支持模糊查询
 1.3	虚拟行政区域：下拉框，默认全部，包含全部、是、否；表示行政区域是否为虚拟的；
 1.5.4	界面原型-新增/修改行政区域

 图四：新增/修改行政区域
 1.5.5	界面描述-新增和修改始发线路
 1.	字段输入区域
 1)	编码： 必填，文本，编码必须唯一
 2)	区域全称：必填，文本，行政区域全称，单位从下拉框选择，包含省、自治州、市、区、县 
 3)	简称：必填，文本，行政区域简称
 4)	可用名称：必填，文本，行政区域有哪些可用的名称
 5)	上级行政区域：必填，选择框，从行政区域树基础资料中选取
 6)	虚拟行政区域：必填，单选框，默认为否，表示该行政区域是否是虚拟的行政区域；
 7)	行政区域级别：必填，单选框，包括：国家、省、市、区县
 7)8)	界面新增加一个是否热门城市单选框
 2.	功能按钮区域
 1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。
 2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
 3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
 1.6	操作步骤
 1.6.1	添加行政区域操作步骤
 序号	基本步骤	相关数据	补充步骤
 1	进入行政区域管理主界面	【行政区域列表信息】	
 2	点击新增按钮，进入【图四：新增/修改行政区域】界面		
 3	输入行政区域详细信息，点击保存。
 参见业务规则SR-1、SR-2、SR-3	【行政区域新增/修改信息】	
 4	返回行政区域管理主界面		

 序号	扩展事件	相关数据	备注
 3a	点击取消按钮，退出当前界面，返回主界面		
 3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

 1.6.2	修改行政区域操作步骤
 序号	基本步骤	相关数据	补充步骤
 1	进入行政区域管理主界面	【行政区域列表信息】	
 2	点击修改按钮，进入【图四：新增/修改行政区域】界面		
 3	修改行政区域详细信息,点击保存
 参见业务规则SR-1、SR-2、SR-3	【行政区域新增/修改信息】	
 4	返回行政区域管理主界面		

 序号	扩展事件	相关数据	备注
 3a	点击取消按钮，退出当前界面，返回主界面		
 3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

 1.6.3	作废始发线路操作步骤
 序号	基本步骤	相关数据	补充步骤
 1	进入行政区域管理主界面	【行政区域列表信息】	
 2	选择一行或者多行记录，点击作废按钮。		作废时连同作废该行政区域所包含的子行政区域，弹出确认对话框。
 3	点击确定按钮。		

 序号	扩展事件	相关数据	备注
 2a	点击取消按钮，退出当前界面，返回主界面		
 2b	若作废失败，需提示用户作废失败以及失败原因		

 1.6.4	查询行政区域操作步骤
 序号	基本步骤	相关数据	补充步骤
 1	进入行政区域管理主界面	【行政区域列表信息】	
 2	输入查询条件，点击查询按钮。参见业务规则SR-4	【行政区域查询条件】	系统返回查询结果
 1.7	业务规则
 序号	描述
 SR-1	新增/修改页面，行政区域“编码”不能重复； 
 SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
 SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
 SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；

 1.8	数据元素
 1.8.1	行政区域新增/修改信息
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 编码	行政区域编码	文本		10	是	
 区域全称	行政区域名称	文本		50	是	区域全称后面还有下拉框显示行政区域单位，包含：省、自治州、市、区、县
 简称	行政区域名称简称	文本		50	是	
 可用名称	行政区域曾经用过和现在用过的名称	文本		100	是	
 上级行政区域	行政区域上级区域名称	选择框		50	是	从行政区域树基础资料中选择
 虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在	单选框		10	是	默认为否
 行政区域级别	行政区域所属级别，包括：国家、省、市、区县	单选框		10	是	
 1.8.2	行政区域列表信息
 字段名称 	说明 	输入限制	长度	是否必填	备注
 编码	行政区域编码	N/A	10	N/A	
 区域全称	行政区域名称	N/A	50	N/A	
 简称	行政区域名称简称	N/A	50	N/A	
 可用名称	行政区域曾经用过和现在用过的名称	N/A	100	N/A	
 上级行政区域	行政区域上级区域名称	N/A	50	N/A	
 虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在	N/A	10	N/A	
 1.8.3	行政区域查询条件
 字段名称 	说明 	输入限制	长度	是否必填	备注
 区域全称	行政区域名称，支持模糊查询	文本	50	否	
 简称	行政区域名称简称，支持模糊查询	文本	50	否	
 虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在，默认为全部，包含：全部、是、否	文本	10	否	
 1.9	非功能性需求
 使用量	
 2012年全网估计用户数	
 响应要求（如果与全系统要求 不一致的话）	
 使用时间段	
 高峰使用时间段	

 1.10	接口描述
 接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncDistrictService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;
import com.deppon.foss.module.base.baseinfo.server.util.PinyinUtil;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AdministrativeRegionsDto;

/**
 * 行政区域 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-31 下午4:9:39
 */
public class AdministrativeRegionsService implements
		IAdministrativeRegionsService {

	/**
	 * 日志打印对象声明
	 */
	private static final Logger log = Logger
			.getLogger(AdministrativeRegionsService.class);

	/**
	 * 同步行政区域
	 */
	private ISyncDistrictService syncDistrictService;
	/**
	 * 快递派送区域Service
	 */
	private IExpressDeliveryRegionsService expressDeliveryRegionsService;
	
	private IBigcusDeliveryAddressService bigcusDeliveryAddressService;

	
	public void setBigcusDeliveryAddressService(
			IBigcusDeliveryAddressService bigcusDeliveryAddressService) {
		this.bigcusDeliveryAddressService = bigcusDeliveryAddressService;
	}

	/**
	 * 下面是dao对象的声明及get,set方法：
	 * 
	 * "@Inject"是给接送货组使用，用于重用综合组代码
	 */
	@Inject
	private IAdministrativeRegionsDao administrativeRegionsDao;

	/**
	 * 同步行政区域信息到OWS
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-15 下午5:22:39
	 * @return
	 */
	private void syncDistrictDataToOws(
			List<AdministrativeRegionsEntity> entityLst) {
		syncDistrictService.syncDistrictDataToOws(entityLst);
	}

	/**
	 * 
	 * @date Mar 11, 2013 2:13:17 PM
	 * @param administrativeRegionsDao
	 * @see
	 */
	public void setAdministrativeRegionsDao(
			IAdministrativeRegionsDao administrativeRegionsDao) {
		this.administrativeRegionsDao = administrativeRegionsDao;
	}

	/**
	 * 
	 * @date Mar 11, 2013 2:12:17 PM
	 * @param syncDistrictService
	 * @see
	 */
	public void setSyncDistrictService(ISyncDistrictService syncDistrictService) {
		this.syncDistrictService = syncDistrictService;
	}

	public void setExpressDeliveryRegionsService(
			IExpressDeliveryRegionsService expressDeliveryRegionsService) {
		this.expressDeliveryRegionsService = expressDeliveryRegionsService;
	}

	/**
	 * 行政区域 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-31 下午4:9:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 *      IAdministrativeRegionsService#addAdministrativeRegions
	 *      (com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity)
	 *      1 进入行政区域管理主界面 【行政区域列表信息】
	 * 
	 *      2点击新增按钮，进入【图四：新增/修改行政区域】界面
	 * 
	 *      3 输入行政区域详细信息， 点击保存。 参见业务规则SR-1、SR-2、SR-3【行政区域新增/修改信息】
	 * 
	 *      4 返回行政区域管理主界面
	 * 
	 *      序号 扩展事件 相关数据 备注
	 * 
	 *      3a 点击取消按钮，退出当前界面，返回主界面
	 * 
	 *      3b若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面
	 */
	@Override
	@Transactional
	public AdministrativeRegionsEntity addAdministrativeRegions(
			AdministrativeRegionsEntity entity) {
		// 检查参数的合法性
		if (null == entity) {
			return null;
		}
		// Add by Foss-zhangxiaohui
		// 判断行政区域名字不为空
		if (null == entity.getName()) {
			return null;
		}
		// 转换城市名字为拼音
		entity.setPinYin(PinyinUtil.hanziToPinYinWithNoSeparator(entity
				.getName()));
		// 转换城市名字为拼音简码
		try {
			// 设置行政区域拼音简码
			entity.setPinYinAbbr(PinyinUtil.getPinYinHeadChar(entity.getName()));
		} catch (BadHanyuPinyinOutputFormatCombination badFormatException) {
			// 打印log日志
			log.info("区域名称转换成拼音输出格式错误", badFormatException);
			// 抛出异常
			throw new BusinessException("区域拼音转换异常");
		}
		// 首先校验该行政区域是否存在
		AdministrativeRegionsEntity oldEntity = administrativeRegionsDao
				.queryAdministrativeRegionsByCode(entity.getCode());
		if(oldEntity!=null){
			throw new BusinessException("该行政区域信息已经存在，不能继续新增！");
		}
		//优化行政区域：新增/修改时父子不可同级-dujunhui-187862
		AdministrativeRegionsEntity parentEntity=this.queryAdministrativeRegionsByCode(entity.getParentDistrictCode());
		if(parentEntity==null){
			throw new BusinessException("新增的行政区域父节点不存在！");
		}
		//设置parent_district_nameTODO
		entity.setParentDistrictName(parentEntity.getName());
		if(StringUtil.equals(parentEntity.getDegree(), entity.getDegree())){
			throw new BusinessException("新增的行政区域级别不允许与上级级别相同！");
		}
		// 执行新增操作
		AdministrativeRegionsEntity resultEntity = administrativeRegionsDao
				.addAdministrativeRegions(entity);
		if (null != resultEntity) {
			try {
				// 清缓存
				invalidEntity(resultEntity.getCode());
				
				//先更新给快递派送区域
				expressDeliveryRegionsService.addExpressDeliveryRegions(transEntity(resultEntity,new ExpressDeliveryRegionsEntity()));
				bigcusDeliveryAddressService.addBigcusFromAdRegion(entity);

				List<AdministrativeRegionsEntity> districtList = new ArrayList<AdministrativeRegionsEntity>();
				districtList.add(resultEntity);
				// 保存成功后，需要同步官网、呼叫中心、CRM（只发一次请求，由ESB分发）
				syncDistrictDataToOws(districtList);
				
			} catch (Exception e) {
				log.debug("", e);
			}
		}
		return resultEntity;
	}

	/**
	 * 通过行政区域编码CODE删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-31 下午4:9:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.
	 *      dao.IAdministrativeRegionsDao#deleteAdministrativeRegions(java.lang.String)
	 *      1 进入行政区域管理主界面 【行政区域列表信息】
	 * 
	 *      2 选择一行或者多行记录，点击作废按钮。
	 * 
	 *      作废时连同作废该行政区域所包含的子行政区域，弹出确认对话框。
	 * 
	 *      3 点击确定按钮。
	 * 
	 * 
	 *      序号 扩展事件 相关数据 备注
	 * 
	 *      2a 点击取消按钮，退出当前界面，返回主界面
	 * 
	 *      2b 若作废失败，需提示用户作废失败以及失败原因
	 */
	@Override
	@Transactional
	public AdministrativeRegionsEntity deleteAdministrativeRegions(
			AdministrativeRegionsEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		// 执行删除操作
		AdministrativeRegionsEntity resultEntity = administrativeRegionsDao
				.deleteAdministrativeRegions(entity);
		if (null != resultEntity) {
			try {
				String[] codes ={resultEntity.getCode()};
				expressDeliveryRegionsService.syncDeleteExpressDeliveryRegions(codes, entity.getModifyUser());
				bigcusDeliveryAddressService.deleteBigcusFromAdRegion(codes);
				// 清缓存
				invalidEntity(resultEntity.getCode());
				List<AdministrativeRegionsEntity> districtList = new ArrayList<AdministrativeRegionsEntity>();
				districtList.add(resultEntity);
				// 同步行政区域数据到OWS
				syncDistrictDataToOws(districtList);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return resultEntity;
	}

	/**
	 * 通过行政区域编码code标识来批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午12:54:43
	 * @see com.deppon.foss.module.base.baseinfo.api.
	 *      server.dao.IAdministrativeRegionsDao#deleteAdministrativeRegionsMore(java.lang.String[])
	 *      1 进入行政区域管理主界面 【行政区域列表信息】
	 * 
	 *      2 选择一行或者多行记录，点击作废按钮。
	 * 
	 *      作废时连同作废该行政区域所包含的子行政区域，弹出确认对话框。
	 * 
	 *      3 点击确定按钮。
	 * 
	 *      序号 扩展事件 相关数据 备注
	 * 
	 *      2a 点击取消按钮，退出当前界面，返回主界面
	 * 
	 *      2b 若作废失败，需提示用户作废失败以及失败原因
	 * 
	 */
	@Override
	@Transactional
	public AdministrativeRegionsEntity deleteAdministrativeRegionsMore(
			String[] codes, String deleteUser) {
		// 执行批量删除操作
		AdministrativeRegionsEntity resultEntity = administrativeRegionsDao
				.deleteAdministrativeRegionsMore(codes, deleteUser);
		if (null != resultEntity) {
			try {
				//同步更新给快递派送区域
				expressDeliveryRegionsService.syncDeleteExpressDeliveryRegions(codes, deleteUser);
				bigcusDeliveryAddressService.deleteBigcusFromAdRegion(codes);
				// 清缓存
				for (String code : codes) {
					invalidEntity(code);
				}
				// 执行查询操作
				List<AdministrativeRegionsEntity> districtList = administrativeRegionsDao
						.queryAdministrativeRegionsByCodeActive(
								Arrays.asList(codes), FossConstants.INACTIVE);
				districtList.add(resultEntity);
				// 同步行政区域数据到OWS
				syncDistrictDataToOws(districtList);
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return resultEntity;
	}

	/**
	 * 根据行政区域编码CODE更新操作
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-31 下午4:9:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
	 *      IAdministrativeRegionsDao#updateAdministrativeRegions
	 *      (com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity)
	 *      1 进入行政区域管理主界面 【行政区域列表信息】
	 * 
	 *      2 点击修改按钮，进入【图四：新增/修改行政区域】界面
	 * 
	 *      3 修改行政区域详细信息,点击保存
	 * 
	 *      参见业务规则SR-1、SR-2、SR-3 【行政区域新增/修改信息】
	 * 
	 *      4 返回行政区域管理主界面
	 * 
	 *      序号 扩展事件 相关数据 备注
	 * 
	 *      3a 点击取消按钮，退出当前界面，返回主界面
	 * 
	 *      3b 若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面
	 */
	@Override
	@Transactional
	public AdministrativeRegionsEntity updateAdministrativeRegions(
			AdministrativeRegionsEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		// Add by Foss-zhangxiaohui
		// 判断行政区域名字不为空
		if (null == entity.getName()) {
			return null;
		}
		// 转换城市名字为拼音
		entity.setPinYin(PinyinUtil.hanziToPinYinWithNoSeparator(entity
				.getName()));
		// 转换城市名字为拼音简码
		try {
			// 设置行政区域拼音简码
			entity.setPinYinAbbr(PinyinUtil.getPinYinHeadChar(entity.getName()));
		} catch (BadHanyuPinyinOutputFormatCombination badFormatException) {
			// 打印log日志
			log.info("区域名称转换成拼音输出格式错误", badFormatException);
			// 抛出异常
			throw new BusinessException("区域拼音转换异常");
		}
		//优化行政区域：新增/修改时父子不可同级-dujunhui-187862
		AdministrativeRegionsEntity parentEntity=this.queryAdministrativeRegionsByCode(entity.getParentDistrictCode());
		if(parentEntity==null){
			throw new BusinessException("修改的行政区域父节点不存在！");
		}
		if(StringUtil.equals(parentEntity.getDegree(), entity.getDegree())){
			throw new BusinessException("修改的行政区域级别不允许与上级级别相同！");
		}
		AdministrativeRegionsEntity resultEntity = administrativeRegionsDao
				.updateAdministrativeRegions(entity);
		if (null != resultEntity) {
			try {
				// 清缓存
				invalidEntity(resultEntity.getCode());
				//查询数据库中的快递派送区域
				ExpressDeliveryRegionsEntity expressDeliveryRegions =expressDeliveryRegionsService.queryExpressDeliveryRegionsByCode(resultEntity.getCode());
				//同时更新快递派送区域
				expressDeliveryRegionsService.updateExpressDeliveryRegions(transEntity(resultEntity,expressDeliveryRegions));
				bigcusDeliveryAddressService.updateBigcusFromAdRegion(resultEntity);
				List<AdministrativeRegionsEntity> districtList = administrativeRegionsDao
						.queryAdministrativeRegionsByCodeActive(
								Arrays.asList(entity.getCode()),
								FossConstants.INACTIVE);
				if (CollectionUtils.isNotEmpty(districtList)) {
					districtList.add(resultEntity);
					// 同步行政区域数据到OWS
					syncDistrictDataToOws(districtList);
				}
			} catch (Exception e) {
				log.error("", e);
			}
		}
		return resultEntity;
	}
	
	/**
	 * 以下全为查询
	 */
	/**
	 * 精确查询 通过 行政区域编码CODE 查询行政区域信息
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-31 下午4:9:39
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.
	 *      api.server.dao.IAdministrativeRegionsService#queryAdministrativeRegionsByCode(java.lang.String)
	 *      1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *      输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *      新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *      下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public AdministrativeRegionsEntity queryAdministrativeRegionsByCode(
			String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		AdministrativeRegionsEntity entity = null;
		if (SqlUtil.loadCache) {
			entity = queryEntityCache(code);
		} else {
			entity = administrativeRegionsDao
					.queryAdministrativeRegionsByCode(code);
		}
		return entity;
	}

	/**
	 * 精确查询 根据行政区域编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-18 下午4:1:47
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.
	 *      server.dao.IAdministrativeRegionsService#queryAdministrativeRegionsByCode(java.lang.String)
	 *      1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *      输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *      新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *      下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public List<AdministrativeRegionsEntity> queryAdministrativeRegionsBatchByCode(
			String[] codes) {
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}
		return administrativeRegionsDao
				.queryAdministrativeRegionsBatchByCode(codes);
	}

	/**
	 * 根据AdministrativeRegionsEntity实体属性动态分页查询行政区域信息 只查询有效信息
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-19 上午11:11:15
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
	 *      IAdministrativeRegionsService#queryAdministrativeRegionsExactByEntity
	 *      (com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity,
	 *      int, int) 1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面
	 *      【行政区域列表信息】 2 输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则
	 *      序号 描述 SR-1 新增/修改页面，行政区域“编码”不能重复； SR-2
	 *      除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3 下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
	 *      SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public List<AdministrativeRegionsEntity> queryAdministrativeRegionsExactByEntity(
			AdministrativeRegionsEntity entity, int start, int limit) {
		AdministrativeRegionsEntity entityCondition = entity == null ? new AdministrativeRegionsEntity()
				: entity;
		return administrativeRegionsDao
				.queryAdministrativeRegionsExactByEntity(entityCondition,
						start, limit);
	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-19 上午11:09:53
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.
	 *      dao.IAdministrativeRegionsService#queryAdministrativeRegionsExactByEntityCount
	 *      (com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity)
	 *      1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *      输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *      新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *      下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public long queryAdministrativeRegionsExactByEntityCount(
			AdministrativeRegionsEntity entity) {
		AdministrativeRegionsEntity entityCondition = entity == null ? new AdministrativeRegionsEntity()
				: entity;
		return administrativeRegionsDao
				.queryAdministrativeRegionsExactByEntityCount(entityCondition);
	}

	/**
	 * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-31 下午4:9:39
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
	 *      IAdministrativeRegionsService#deleteAdministrativeRegionsMore(java.lang.String[])
	 *      1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *      输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *      新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *      下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public List<AdministrativeRegionsEntity> queryAdministrativeRegionsByEntity(
			AdministrativeRegionsEntity entity, int start, int limit) {
		AdministrativeRegionsEntity entityCondition = entity == null ? new AdministrativeRegionsEntity()
				: entity;
		return administrativeRegionsDao.queryAdministrativeRegionsByEntity(
				entityCondition, start, limit);
	}

	/**
	 * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-31 下午4:9:39
	 * 
	 * @see com.deppon.foss.module.baseinfo.server.service.
	 *      IAdministrativeRegionsService#queryAdministrativeRegionsCountByEntity
	 *      (com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.AdministrativeRegionsEntity)
	 *      1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *      输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *      新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *      下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public long queryAdministrativeRegionsByEntityCount(
			AdministrativeRegionsEntity entity) {
		AdministrativeRegionsEntity entityCondition = entity == null ? new AdministrativeRegionsEntity()
				: entity;
		return administrativeRegionsDao
				.queryAdministrativeRegionsByEntityCount(entityCondition);
	}

	/**
	 * 下面是特殊查询
	 */

	/**
	 * 根据行政区域上级编码查询行政区域列表
	 * 
	 * @author 087584-lijun
	 * 
	 * @date 2012-10-12 上午10:18:55
	 * 
	 *       1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *       输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *       新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *       下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	public List<AdministrativeRegionsEntity> queryAdministrativeRegionsByParentDistrictCode(
			String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return administrativeRegionsDao
				.queryAdministrativeRegionsByParentDistrictCode(code);
	}

	/**
	 * 查询行政区域根结点
	 * 
	 * @author 087584-lijun
	 * 
	 * @date 2012-10-12 上午10:19:29
	 * 
	 *       1.6.4 查询行政区域操作步骤
	 * 
	 *       序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *       输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *       新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *       下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	public List<AdministrativeRegionsEntity> queryRoot() {
		return administrativeRegionsDao.queryRoot();
	}

	/**
	 * 根据code查询name
	 * 
	 * @author 087584-lijun
	 * 
	 * @date 2012-10-12 上午10:33:07
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryCount(java.lang.String,
	 *      java.lang.String) 1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面
	 *      【行政区域列表信息】 2 输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则
	 *      序号 描述 SR-1 新增/修改页面，行政区域“编码”不能重复； SR-2
	 *      除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3 下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
	 *      SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public String queryAdministrativeRegionsNameByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		AdministrativeRegionsEntity entity = queryAdministrativeRegionsByCode(code);
		return entity == null ? null : entity.getName();
	}

	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * 
	 * @date 2012-10-18 下午4:1:47
	 * 
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
	 *      IAdministrativeRegionsService#queryAdministrativeRegionsByCode(java.lang.String)
	 *      1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *      输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *      新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *      下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public List<AdministrativeRegionsEntity> queryAdministrativeRegionsBatchByCode(
			List<String> codes) {
		if (CollectionUtils.isEmpty(codes)) {
			return null;
		}
		// 去掉空的code
		List<String> codesList = new ArrayList<String>();
		for (String code : codes) {
			if (StringUtils.isNotBlank(code)) {
				codesList.add(code);
			}
		}
		String[] codeArray = (String[]) codesList.toArray(new String[0]);
		return administrativeRegionsDao
				.queryAdministrativeRegionsBatchByCode(codeArray);
	}

	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-18 下午4:1:47
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
	 *      IAdministrativeRegionsService#queryAdministrativeRegionsByCode(java.lang.String)
	 *      1.6.4 查询行政区域操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *      输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *      新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *      下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public Map<String, AdministrativeRegionsEntity> queryAdministrativeRegionsBatchByCodeToMap(
			List<String> codes) {
		if (CollectionUtils.isEmpty(codes)) {
			return null;
		}
		// 去掉空的code
		codes.removeAll(Collections.singleton(null));

		String[] codeArray = (String[]) codes
				.toArray(new String[NumberConstants.ZERO]);
		// 批量查询
		List<AdministrativeRegionsEntity> entityResults = administrativeRegionsDao
				.queryAdministrativeRegionsBatchByCode(codeArray);
		//防止空指针进行优化-187862-dujunhui
		Map<String, AdministrativeRegionsEntity> map =null;
		if(CollectionUtils.isNotEmpty(entityResults)){
			map = new HashMap<String, AdministrativeRegionsEntity>(
					entityResults.size());
			for (AdministrativeRegionsEntity entity : entityResults) {
				if (entity != null) {
					map.put(entity.getCode(), entity);
				}
			}
		}
		return map;
	}

	/**
	 * 下面是特殊方法
	 */
	/**
	 * 根据行政区域编码获取行政区域名称
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2013-1-7 上午10:33:17
	 * 
	 *       1.6.4 查询行政区域操作步骤
	 * 
	 *       序号 基本步骤 相关数据 补充步骤 1 进入行政区域管理主界面 【行政区域列表信息】 2
	 *       输入查询条件，点击查询按钮。参见业务规则SR-4 【行政区域查询条件】 系统返回查询结果 1.7 业务规则 序号 描述 SR-1
	 *       新增/修改页面，行政区域“编码”不能重复； SR-2 除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空 SR-3
	 *       下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别； SR-4 查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
	 */
	@Override
	public String gainDistrictNameByCode(String code) {
		return queryAdministrativeRegionsNameByCode(code);
	}

	/**
	 * 获取最后更新时间
	 * 
	 * @author dp-yangtong
	 * 
	 * @date 2012-10-30 下午4:07:16
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public Date getLastUpdateTime() {
		return administrativeRegionsDao.getLastModifyTime();
	}

	/**
	 * 获得行政区域信息，用于行政区域信息同步
	 * 
	 * @param fromDate
	 * 
	 * @param toDate
	 * 
	 * @param pageIndex
	 * 
	 * @param PageSize
	 * 
	 * @return Customer
	 */
	@Override
	public List<AdministrativeRegionsEntity> getAdministrativeRegions(
			Date fromDate, String userID) {
		return administrativeRegionsDao.getSyncData(fromDate, userID);
	}

	@Override
	public AdministrativeRegionsEntity queryAdministrativeRegionsByName(
			String cityName) {
		if (StringUtil.isNotBlank(cityName)) {
			return administrativeRegionsDao
					.queryAdministrativeRegionsByName(cityName);
			// 抛出异常
		} else {
			throw new BusinessException("输入的查询参数名称不能为空");
		}
	}
	/**
	 * 
	 *<P>转换行政区域实体为派送快递区域实体，用于同步数据给快递派送区域<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-2-26下午2:59:17
	 * @param info
	 * @return
	 */
	private ExpressDeliveryRegionsEntity transEntity(AdministrativeRegionsEntity info,ExpressDeliveryRegionsEntity entity){
		if(null == entity){
			entity = new ExpressDeliveryRegionsEntity();
		}
		if(null == info || StringUtils.isBlank(info.getCode())){
			return entity;
		}
		//将行政区域的值设置给派送区域
		entity.setCode(info.getCode());
		entity.setName(info.getName());
		entity.setParentDistrictCode(info.getParentDistrictCode());
		entity.setParentDistrictName(info.getParentDistrictName());
		entity.setVirtualDistrictId(info.getVirtualDistrictId());
		entity.setDegree(info.getDegree());
		entity.setCreateUser(info.getCreateUser());
		entity.setModifyUser(info.getModifyUser());
		return entity;
	}
	/**
	 * 
	 * <p>
	 * 清空指定的缓存实体
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Mar 5, 2013 5:49:55 PM
	 * @param key
	 * @see
	 */
	@SuppressWarnings("unchecked")
	private void invalidEntity(String key) {
		((ICache<String, AdministrativeRegionsEntity>) CacheManager
				.getInstance()
				.getCache(FossTTLCache.DISTRICT_ENTITY_CACHE_UUID))
				.invalid(key);
	}

	/**
	 * 
	 * <p>
	 * 取缓存中的数据实体
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Mar 5, 2013 5:49:23 PM
	 * @param key
	 * @return
	 * @see
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AdministrativeRegionsEntity queryEntityCache(String key) {
		AdministrativeRegionsEntity result = null;
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if (cacheManager == null) {
				return null;
			}
			ICache cache = cacheManager
					.getCache(FossTTLCache.DISTRICT_ENTITY_CACHE_UUID);
			if (cache == null) {
				return null;
			}
			result = (AdministrativeRegionsEntity) cache.get(key);
		} catch (Exception t) {
			log.error("cache找不到", t);
		}
		return result;
	}

	/** 
	 * 根据城市code获取行政区域信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 下午1:53:59
	 * @param code
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService#queryAdministrativeRegionsByCodeNotCache(java.lang.String)
	 */
	@Override
	public AdministrativeRegionsEntity queryAdministrativeRegionsByCodeNotCache(
			String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		AdministrativeRegionsEntity entity = administrativeRegionsDao.queryAdministrativeRegionsByCode(code);
		return entity;
	}
	
	/**
	 * 根据行政区域代码查询快递试点网点的点坐标和范围坐标
	 * 
	 * @author WeiXing
	 * @date 2014-08-27 上午10:18:55
	 * 
	 */
	public List<AdministrativeRegionsDto> queryServerCoordinatesByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return administrativeRegionsDao.queryServerCoordinatesByCode(code);
	}
	
	/**
	 * 根据行政区域代码查询非试点快递网点的点坐标和范围坐标
	 * 
	 * @author WeiXing
	 * @date 2014-08-27 上午10:18:55
	 * 
	 */
	public List<AdministrativeRegionsDto> queryServerCoordinatesNotByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return administrativeRegionsDao.queryServerCoordinatesNotByCode(code);
	}
	
	
	@Override
	public String queryRegionName(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		AdministrativeRegionsEntity entity = null;
		if (SqlUtil.loadCache) {
			entity = queryEntityCache(code);
			return entity.getName();
		}
		return administrativeRegionsDao.queryRegionNameByCode(code);
	}
    //350909      郭倩云       零担轻货上分拣取得是城市简称
	public String gainDistrictSimpleNameByCode(String code) {
		return queryAdministrativeRegionsSimpleNameByCode(code);
	}
	//350909      郭倩云       零担轻货上分拣取得是城市简称
	private String queryAdministrativeRegionsSimpleNameByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		AdministrativeRegionsEntity entity = queryAdministrativeRegionsByCode(code);
		return entity == null ? null : entity.getSimpleName();
	}
}

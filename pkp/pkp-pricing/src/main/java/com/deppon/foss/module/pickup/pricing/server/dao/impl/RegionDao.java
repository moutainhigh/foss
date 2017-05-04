/**
*  
*  
* SR1	
*  1.	区域名称： 区域名称不能出现重复、
*  
*  校验时考虑左右空格情况、
*  
*  区域所含信息是比较广义的、
*  
*  它可以将所在地信息化为一个区域也可以讲对应所在地的部门网点作为一个区域。
*  
*  区域名称总长度不超过100。
*  
* 2.	区域编号：
* 
*  区域的编号不能出现重复信息、
*  
*  区域编号由字母加数字组成不能超过50个长度。
*  
* 3.	 目前改为按照行政区域，
* 
* 或者按照网点信息进行规划成一个逻辑区域
* 
*SR2	
*
*是否激活：
*
*代表是否立即启用该区域，
*
*规则只有未激活的区域可以做激活。
*
*激活区域时，
*
*需要选择生效时间。
*
* SR3	
* 
* 一个区域的类型，
* 
* 只能在行政区域和组织之间选择。
* 
* 不能两者都是，
* 
* 并且修改时不可以修改类型。
* 
* 但选择行政区域类型时，
* 
* 可以在逻辑区域与国家行政区域之间建立关联。
* 
* 但选择组织类型时，
* 
* 则下部部门信息可以添加：
* 
* 德邦内部组织，
* 
* 偏线代理，
* 
* 空运代理。
* 
*SR4	
*
*点击提交时存入区域类型字段其目的是在列表查询时
*
*显示以部门作为区域定义的需要给出超链接查询部门明细信息，
*
*区域类型是根据部门信息是否存在而决定的， 
*
*部门信息无它是以所在地信息作为一个区域，
*
*部门信息有则是遵守所在地对应的部门然后
*
*经过管理员选择筛选后所成立的逻辑区域信息。
*
*对于前者区域类型字段设置为所在地类型，
*
*对于后者区域类型设置为部门。
*
* SR5	
*
*同一个所在地不能重复建立区域信息
*
*SR6
*	
*新增区域原则： 
*
*同一个网点组织，\
*
*在同一时间段，
*
*不能所属两个逻辑区域信息，
*
*以免造成前台开单时读取区域信息出现混淆。
*
*前台开单按照异常优先级来决定在本用例维护中所定位的区域信息，
*
*获取区域信息优先级别如下：
*
*依次顺序为网点组织、区县、城市、省份
*
*根据开单时所提供的部门名称首先寻找本用例中所维护的部门区域类型是否存在不存在再依次
*
*往上找区县、市、省份直到找到为止， 
*	
*SR7	
*
*部门信息弹出窗口， 
*
*主要为网点筛选器，
*
*提供按照名称，
*
*编码、拼音，以及省，市，区，组织属性，
*
*类型等不同维度条件来筛选合适的网点信息。 
*
*组织类型包括、空运代理、偏线代理、内部组织。
*
*将这些网点组装起来划成一个逻辑区域来满足各种不同的特殊场景。
*
*（例如将上海某个公司内部网点与杭州公司某个网点划成一个逻辑区域）
*
*SR8	
*
*网点信息不能在同一个时间段存在多个逻辑区域信息中，
*
*例如(A逻辑区域包含上海浦东XX路营业部从有效起始时间2012-05-31 到 有效截止时间 2999-01-01，
*
* 这段时间内该营业部不能同时出现在其他逻辑区域中)，偏线、空运、代理同理
* 
*SR9	
*
*立即中止功能：
*
* 修改时部门查询列表中，
* 
* 点击“立即中止”超链接弹出立即中提示框，
* 
* 填写中止时间 ,选择“确认”系统将选中的方案截止时间调整为当前设置的中止的时间，
* 
* 出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” ，
* 
* 即时间可以在今天和今天以后任意调整，但是不能调整为昨天的数据。
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
*  
* 维护始发区域和目的区域
* 
*SR1	
*
*弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成： 
*
*出发信息包括： 
*
*a)	生效日期: 
*
*设定生效日期。
*
*b)	始发区域: 
*
*区域信息来自产品基础数据维护下的区域来填充该下拉表请先熟悉 DP-FOSS-接送货系统用例-产品价格-城市列表-录入区域SUC-587-V0.1用例。          
*
* C) 未被激活的版本信息部能被正常使用。D) 方案描述：  对建立新方案的一些描述信息。
* 
*目的地信息：  由于可以设置至少一个到N个元素信息.故使用明细列表的方式来暂存数据一起提交。
*
*SR2	
*
*目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
*
*SR3	
*
*添加目的地明细信息时可一选择基础产品设置运营时效和取货时间，是否长短途， 在提交目的地设置相关信息时点击 “提交”按钮需要校验，目的地、基础产品、长短途在后台不能有相同的数据已存在， 提示如“广州-精准卡航-短途时效信息已经存在不能重复添加!”
*
*SR4
*
*图3中产品下拉列表中数据需要过滤规则：  只能显示该始发区域与目的地区域在 区域与产品基础数据中所维护的产品信息。
*
*SR5	
*
*新增时，所选择的区域信息（始发区域/目的地区域）都需要过滤，
*
*只能取时效相关的区域信息作为数据源。
*
*如：图2中界面元素下拉列标的始发区域，
*
*图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为时效的区域,”。
*	
*SR6	
*
*新增时效方案信息时，
*
* 必须先保存出发地信息，
* 
* 然后目的地信息中的“新增”、“删除”、“修改”、
* 
* 功能才可以被使用。 否则为禁用状态。
 *
*SR7	
*
*所有新建方案都以草稿数据存储。
*
*针对草稿数据，
*
*我们可以随时进行任意操作。
*
*一旦在查询列表中做过激活的方案，
*
*就不能再做删除与修改了。
*
*只能做方案的复制对该方案进行延续不同时间段内体现不同时效信息。
*
*SR8	
*
*在草稿状态的方案被建立之后做激活时，
*
*会去检查具体草稿方案信息下的目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
*
*重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品（三级），
*
*如果存在则提示“该方案下xxx目的地xxx产品xxx已经在另一个xxx方案下存在，
*
*请确认是否以该方案为准,请将xxx方案中止。”
*
*SR9	
*
*立即中止功能： 
*
*在时效查询列表中，
*
*只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
*
*点击中止，
*
*选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
*
*出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 。
*
 *
*SR10	
*
*立即激活功能： 
*
*在时效查询列表中，
*
*只能选择一条未激活的数据点击列表中“立即激活”按钮弹出
*
 *选择生效时间，
 *
 *点击激活按钮，
 *
 *选择“是”系统将选中的方案生效时间调整为当前设置的生效时间，
* 
* 出现小于当前营业日期系统提示“立即激活操作的生效时间必须大于等于营业日期!” 。
*
*
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/RegionDao.java
 * 
 * FILE NAME        	: RegionDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

/**
 * 
 * 区域新增修改查询dao
 * 
 * @author 078838 DP-Foss-zhangbin
 * 
 * @date 2012-11-12 下午4:33:08
 */
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @Description: 区域DAO
 * 
 * RegionDao.java Create on 2013-3-19 下午6:04:11
 * 
 * Company:IBM
 * 
 * @author FOSSDP-Administrator
 * 
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * 
 * @version V1.0
 */
@Repository
public class RegionDao extends SqlSessionDaoSupport implements IRegionDao {
    	/** 价格区域. */
	private static final String PRICING_REGION_NAMESPACE = "foss.pkp.pkp-pricing.pricingRegion.";
	/** 时效区域. */
	private static final String PRESCRIPTION_REGION_NAMESPACE = "foss.pkp.pkp-pricing.perscriptionRegion.";
	/** 根据条件查询区域. */
	private static final String SEARCH_REGION = "searchRegionByCondition";
	/** 根据条件查询价格区域部门信息. */
	private static final String SELECT_PRICING_REGION = "selectPriceRegionByCondition";
	/** 根据条件查询价格区域部门信息 *. */
	private static final String SELECT_PRICING_REGION_ORG = "selectPriceRegionOrgByCondition";
	/** 根据条件查询时效区域信息 *. */
	private static final String SELECT_EFFECTIVE_REGION = "selectEffectiveRegionByCondition";
	/** 根据条件查询时效区域部门信息 *. */
	private static final String SELECT_EFFECTIVE_REGION_ORG = "selectEffectiveRegionOrgByCondition";
	/** 根据行政区域查询区域 *. */
	private static final String SEARCH_REGION_BY_DIS = "searchRegionByDistrict";
	/** 根据条件查询区域个数 *. */
	private static final String COUNT_REGION = "countRegionByCondition";
	/** 插入区域信息 *. */
	private static final String INSERT_REGION = "insertRegion";
	/** 插入区域部门信息 *. */
	private static final String INSERT_REGIONORG = "insertRegionOrg";
	/** 插入区域部门信息 *. */
	private static final String UPDATE_REGIONORG = "updateRegionOrg";
	/** 根据条件查询区域部门信息 *. */
	private static final String SEARCH_REGIONORG = "searchRegionOrgByCondition";
	/** 激活区域 *. */
	private static final String ACTIVE_REGION = "activeRegion";
	/** 删除区域 *. */
	private static final String DELETE_REGION = "deleteRegion";
	/** 删除区域关联部门 *. */
	private static final String DELETE_REGION_ORG = "deleteRegionOrg";
	/** 激活区域org *. */
	private static final String ACTIVE_REGIONORG = "activeRegionOrg";
	/** 修改区域 *. */
	private static final String UPDATE_REGION = "updateRegion";
	/** 根据区域名称查询 *. */
	private static final String SEARCH_REGION_BY_NAME = "searchRegionByName";
	/** 根据区域ID查询区域 *. */
	private static final String SEARCH_REGION_BY_ID = "searchRegionByID";
	/** 
	 * 
	 * <p>
	 * 根据条件查询区域信息（分页）
	 * 
	 * 方法名：searchRegionByCondition <br/>
	 * 
	 * </p> 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionEntity
	 * 
	 * @param start
	 * 
	 * @param limit
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#searchRegionByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity, int, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionEntity> searchRegionByCondition(PriceRegionEntity regionEntity, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + SEARCH_REGION;
		} else if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION;
		}
		return getSqlSession().selectList(sqlAddress, regionEntity, rowBounds);
	}
	/** 
	 * 
	 * <p>
	 * 
	 * 根据条件查询区域信息<br/>
	 * 
	 * 方法名：findRegionByCondition </p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#findRegionByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionEntity> findRegionByCondition(PriceRegionEntity regionEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + SEARCH_REGION;
		} else if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION;
		}
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}
	/** 
	 * 
	 * <p> 根据条件查询区域信息个数<br/>
	 * 
	 * 方法名：countRegionByCondition</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#countRegionByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		// 设置分页条件
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + COUNT_REGION;// 时效区域
		} else if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + COUNT_REGION;// 价格区域
		}
		return (Long) getSqlSession().selectOne(sqlAddress, regionEntity);
	}
	/** 
	 * 
	 * <p>新增区域<br/>
	 * 
	 * 方法名：addRegion</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionEntity 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#addRegion(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	public void addRegion(PriceRegionEntity regionEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + INSERT_REGION;
		} else if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGION;
		}
		getSqlSession().insert(sqlAddress, regionEntity);
	}
	/** 
	 * 
	 * <p>新增区域与部门的关联<br/>
	 * 
	 * 方法名：addRegionOrg</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param priceRegioOrgnEntity 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#addRegionOrg(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity)
	 */
	@Override
	public void addRegionOrg(PriceRegioOrgnEntity priceRegioOrgnEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(priceRegioOrgnEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + INSERT_REGIONORG;
		} else if (PricingConstants.PRICING_REGION.equals(priceRegioOrgnEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + INSERT_REGIONORG;
		}
		getSqlSession().insert(sqlAddress, priceRegioOrgnEntity);
	}
	/** 
	 * 
	 * <p>根据条件查询区域关联部门信息<br/>
	 * 
	 * 方法名：searchRegionOrgByCondition</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param priceRegioOrgnEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#searchRegionOrgByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegioOrgnEntity> searchRegionOrgByCondition(PriceRegioOrgnEntity priceRegioOrgnEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(priceRegioOrgnEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + SEARCH_REGIONORG;
		} else if (PricingConstants.PRICING_REGION.equals(priceRegioOrgnEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGIONORG;
		}
		return getSqlSession().selectList(sqlAddress, priceRegioOrgnEntity);
	}
	/** 
	 * 
	 * <p>激活区域<br/>
	 * 
	 * 方法名：activeRegion</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionIds
	 * 
	 * @param regionNature 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#activeRegion(java.util.List, java.lang.String)
	 */
	@Override
	public void activeRegion(List<String> regionIds, String regionNature) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionNature)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + ACTIVE_REGION;
		} else if (PricingConstants.PRICING_REGION.equals(regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + ACTIVE_REGION;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regionIds", regionIds);
		map.put("versionNo", new Date().getTime());
		getSqlSession().update(sqlAddress, map);
	}
	/** 
	 * 
	 * <p>删除区域<br/>
	 * 
	 * 方法名：deleteRegion</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionIds
	 * 
	 * @param regionNature 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#deleteRegion(java.util.List, java.lang.String)
	 */
	@Override
	public void deleteRegion(List<String> regionIds, String regionNature) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionNature)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + DELETE_REGION;
		} else if (PricingConstants.PRICING_REGION.equals(regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + DELETE_REGION;
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("regionIds", regionIds);
		getSqlSession().update(sqlAddress, map);
	}
	/** 
	 * 
	 * <p>删除区域下关联部门<br/>
	 * 
	 * 方法名：deleteRegion</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionIds
	 * 
	 * @param regionNature 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#deleteRegionOrg(java.util.List, java.lang.String)
	 */
	@Override
	public void deleteRegionOrg(List<String> regionIds, String regionNature) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionNature)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + DELETE_REGION_ORG;
		} else if (PricingConstants.PRICING_REGION.equals(regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + DELETE_REGION_ORG;
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("regionIds", regionIds);
		getSqlSession().update(sqlAddress, map);
	}
	/** 
	 * 
	 * <p>激活区域下关联的区域部门<br/>
	 * 
	 * 方法名：activeRegionOrg</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionId
	 * 
	 * @param regionNature 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#activeRegionOrg(java.lang.String, java.lang.String)
	 */
	@Override
	public void activeRegionOrg(String regionId, String regionNature) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionNature)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + ACTIVE_REGIONORG;
		} else if (PricingConstants.PRICING_REGION.equals(regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + ACTIVE_REGIONORG;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regionId", regionId);
		map.put("versionNo", new Date().getTime());
		getSqlSession().update(sqlAddress, map);
	}
	/** 
	 * 
	 * <p>修改区域<br/>
	 * 
	 * 方法名：updateRegion</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionEntity 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#updateRegion(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	public void updateRegion(PriceRegionEntity regionEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + UPDATE_REGION;
		} else if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGION;
		}
		getSqlSession().update(sqlAddress, regionEntity);
	}
	/** 
	 * 
	 * <p>修改区域与部门的关联<br/>
	 * 
	 * 方法名：updateRegionOrg</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param priceRegioOrgnEntity 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#updateRegionOrg(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity)
	 */
	@Override
	public void updateRegionOrg(PriceRegioOrgnEntity priceRegioOrgnEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(priceRegioOrgnEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + UPDATE_REGIONORG;
		} else if (PricingConstants.PRICING_REGION.equals(priceRegioOrgnEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + UPDATE_REGIONORG;
		}
		getSqlSession().update(sqlAddress, priceRegioOrgnEntity);
	}
	/** 
	 * 
	 * <p>根据行政区域查逻辑区域</p> 
	 * 
	 *  行政区域由小到达，逐级过滤。传入的各级行政区域为或者的关系。</p> 
	 *  
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#searchRegionByDistrict(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionEntity> searchRegionByDistrict(PriceRegionEntity regionEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + SEARCH_REGION_BY_DIS;
		} else if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_DIS;
		}
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}
	
	/** 
	 * 
	 * <p>根据行政区域查逻辑区域（区域查询专用，其他不可用）</p> 
	 * 
	 *  行政区域由小到达，逐级过滤。传入的各级行政区域为或者的关系。</p> 
	 *  
	 * @author DP-Foss-SZ
	 * 
	 * @date 2013-6-5  下午2:21:32
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#searchRegionByDistrict(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionEntity> searchRegionByDistrictNew(PriceRegionEntity regionEntity) {
		String sqlAddress = null;
		if (PricingConstants.PRESCRIPTION_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "searchRegionByDistrictNew";
		} else if (PricingConstants.PRICING_REGION.equals(regionEntity.getRegionNature())) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionByDistrictNew";
		}
		return getSqlSession().selectList(sqlAddress, regionEntity);
	}
	/** 
	 * 
	 * <p>根据区域名称区域信息<br/>
	 * 
	 * 方法名：searchRegionByName</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param regionName
	 * 
	 * @param regionNature
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#searchRegionByName(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceRegionEntity> searchRegionByName(String regionName, String regionNature) {
		String sqlAddress = null;
		if (regionNature != null && regionNature.equals(PricingConstants.PRESCRIPTION_REGION)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + SEARCH_REGION_BY_NAME;
		} else if (regionNature != null && regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_NAME;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("regionName", regionName);
		return getSqlSession().selectList(sqlAddress, map);
	}
	/** 
	 * 
	 * <p> 根据ID与区域标识查询区域信息<br/>
	 * 
	 * 方法名：searchRegionByID</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param id
	 * 
	 * @param regionNature
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#searchRegionByID(java.lang.String, java.lang.String)
	 */
	@Override
	public PriceRegionEntity searchRegionByID(String id, String regionNature) {
		String sqlAddress = null;
		if (regionNature != null && regionNature.equals(PricingConstants.PRESCRIPTION_REGION)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + SEARCH_REGION_BY_ID;
		} else if (regionNature != null && regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE + SEARCH_REGION_BY_ID;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (PriceRegionEntity) getSqlSession().selectOne(sqlAddress, map);
	}
	/** 
	 *
	 * <p> 根据条件查询价格区域信息<br/>
	 * 
	 * 方法名： selectPriceRegionByCondition
	 * 
	 * </p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param priceRegionEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#selectPriceRegionByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionEntity> selectPriceRegionByCondition(PriceRegionEntity priceRegionEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SELECT_PRICING_REGION;
		return getSqlSession().selectList(sqlAddress, priceRegionEntity);
	}
	/** 
	 * 
	 * <p>
	 * 
	 * 根据条件查询价格区域部门信息<br/>
	 * 
	 * 方法名： selectPriceRegionOrgByCondition
	 * 
	 * </p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param priceRegioOrgnEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#selectPriceRegionOrgByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegioOrgnEntity> selectPriceRegionOrgByCondition(PriceRegioOrgnEntity priceRegioOrgnEntity) {
		String sqlAddress = PRICING_REGION_NAMESPACE + SELECT_PRICING_REGION_ORG;
		return getSqlSession().selectList(sqlAddress, priceRegioOrgnEntity);
	}
	/** 
	 * 
	 * <p>根据条件查询时效区域信息<br/>
	 * 
	 * 方法名： selectEffectiveRegionByCondition	
	 * 
	 * </p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param effectiveRegionEntity
	 * 
	 * @return 
	 * @see com.deppo
	 * n.foss.module.pickup.pricing.api.server.dao.IRegionDao#selectEffectiveRegionByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EffectiveRegionEntity> selectEffectiveRegionByCondition(EffectiveRegionEntity effectiveRegionEntity) {
		String sqlAddress = PRESCRIPTION_REGION_NAMESPACE + SELECT_EFFECTIVE_REGION;
		return getSqlSession().selectList(sqlAddress, effectiveRegionEntity);
	}
	/** 
	 * 
	 * <p>
	 * 
	 * 根据条件查询时效区域部门信息<br/>
	 * 
	 * 方法名： selectEffectiveRegionOrgByCondition
	 * 
	 * </p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param effectiveRegionOrgEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#selectEffectiveRegionOrgByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionOrgEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EffectiveRegionOrgEntity> selectEffectiveRegionOrgByCondition(
			EffectiveRegionOrgEntity effectiveRegionOrgEntity) {
		String sqlAddress = PRESCRIPTION_REGION_NAMESPACE + SELECT_EFFECTIVE_REGION_ORG;
		return getSqlSession().selectList(sqlAddress, effectiveRegionOrgEntity);
	}
	/**
	 * 
	 * 根据区域名称批量查找区域信息
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-22 下午8:26:15
	 * 
	 * @param names 区域名称List
	 * 
	 * @param regionNature 区域性质
	 * 
	 * @param billDate 开单日期
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, PriceRegionEntity> findRegionByNames(List<String> names, String regionNature, Date billDate) {
		String sqlAddress = null;
		if (regionNature != null && regionNature.equals(PricingConstants.PRESCRIPTION_REGION)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "findRegionByNames";
		} else if (regionNature != null && regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "findRegionByNames";
		}
		Map map = new HashMap();
		map.put("regionNames", names);
		map.put("billDate", billDate);
		map.put("active", FossConstants.ACTIVE);
		List<PriceRegionEntity> regionEntityList = getSqlSession().selectList(sqlAddress, map);
		if (CollectionUtils.isNotEmpty(regionEntityList)) {
			Map<String, PriceRegionEntity> returnMap = new HashMap<String, PriceRegionEntity>();
			for (int loop = 0; loop < regionEntityList.size(); loop++) {
				returnMap.put(regionEntityList.get(loop).getRegionName(), regionEntityList.get(loop));
			}
			return returnMap;

		}
		return null;
	}
	/**
	 * 
	 * Find region by codes.
	 *
	 * @param codes the codes
	 * 
	 * @param regionNature the region nature
	 * 
	 * @param billDate the bill date
	 * 
	 * @return the list
	 * 
	 * @Description: 根据区域CODE 批量查找区域信息 Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-8 下午8:09:34
	 * 
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PriceRegionEntity> findRegionByCodes(List<String> codes, String regionNature, Date billDate) {
		String sqlAddress = null;
		if (regionNature != null && regionNature.equals(PricingConstants.PRESCRIPTION_REGION)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "findRegionByCodes";
		} else if (regionNature != null && regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "findRegionByCodes";
		}
		Map map = new HashMap();
		map.put("regionCodes", codes);
		map.put("billDate", billDate);
		map.put("active", FossConstants.ACTIVE);
		return getSqlSession().selectList(sqlAddress, map);
	}
	/** 
	 * 
	 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:21:32
	 * 
	 * @param administrativeRegionCodes
	 * 
	 * @param regionNature
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao#checkRegionOrganizationType(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionEntity> checkRegionOrganizationType(String id,String administrativeRegionCodes, String regionNature) {
		String sqlAddress = null;
		if (regionNature != null && regionNature.equals(PricingConstants.PRESCRIPTION_REGION)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "checkRegionOrganizationType";
		} else if (regionNature != null && regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "checkRegionOrganizationType";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("administrativeRegionCodes", administrativeRegionCodes);
		map.put("id", id);
		return getSqlSession().selectList(sqlAddress, map);
	}
	/**
	 * 
	 * Search region by district for cache.
	 *
	 * @param provinceCode the province code
	 * 
	 * @param cityCode the city code
	 * 
	 * @param countyCode the county code
	 * 
	 * @param regionNature the region nature
	 * 
	 * @param billDate the bill date
	 * 
	 * @return the list
	 * 
	 * @Description: 根据District CODE查询区域信息
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-2-20 上午11:04:55
	 * 
	 * @version V1.0
	 * 
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PriceRegionEntity> searchRegionByDistrictForCache(String provinceCode, String cityCode,
			String countyCode, String regionNature, Date billDate) {
		String sqlAddress = null;
		if (regionNature != null
				&& regionNature.equals(PricingConstants.PRESCRIPTION_REGION)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE+"searchRegionByDistrictForCache";
		} else if (regionNature != null
				&& regionNature.equals(PricingConstants.PRICING_REGION)) {
			sqlAddress = PRICING_REGION_NAMESPACE+"searchRegionByDistrictForCache";
		}
		Map parameterMap = new HashMap();
		parameterMap.put("proCode", provinceCode);
		parameterMap.put("cityCode", cityCode);
		parameterMap.put("countyCode", countyCode);
		parameterMap.put("active", FossConstants.ACTIVE);
		parameterMap.put("billDate", billDate);
		return getSqlSession().selectList(sqlAddress, parameterMap);
	}
	/**
	 * Search region org by dept no.
	 *
	 * @param deptNo the dept no
	 * 
	 * @param regionNature the region nature
	 * 
	 * @return the list
	 * 
	 * @Description: 根据网点CODE查询价格区域关联部门
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-2-19 下午1:54:28
	 * 
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegioOrgnEntity> searchRegionOrgByDeptNo(String deptNo, String regionNature) {
		String sqlAddress = null;
		if (StringUtil.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "searchRegionOrgByDeptNo";
		} else if (StringUtil.equals(PricingConstants.PRICING_REGION, regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByDeptNo";
		}
		return getSqlSession().selectList(sqlAddress, deptNo);
	}
	/**
	 * 
	 * @Description: 根据区域ID查询价格区域关联部门 
	 * @author FOSSDP-sz
	 * @date 2013-4-22 下午1:50:42
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegioOrgnEntity> searchRegionOrgByRegionId(String deptRegionId, String regionNature) {
		String sqlAddress = null;
		Map map = new HashMap();
		map.put("id", deptRegionId);
		map.put("billDate", new Date());
		if (StringUtil.equals(PricingConstants.PRESCRIPTION_REGION, regionNature)) {
			sqlAddress = PRESCRIPTION_REGION_NAMESPACE + "searchRegionOrgByRegionId";
		} else if (StringUtil.equals(PricingConstants.PRICING_REGION, regionNature)) {
			sqlAddress = PRICING_REGION_NAMESPACE + "searchRegionOrgByRegionId";
		}
		return getSqlSession().selectList(sqlAddress, map);
	}
}



























































/**
 ** 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 */

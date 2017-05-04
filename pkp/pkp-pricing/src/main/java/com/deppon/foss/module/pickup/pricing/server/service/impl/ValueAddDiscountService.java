/**
 * 
*增值折扣计算
*
*1.	查询条件区域：
*
*所属方案、
*
*方案状态、
*
*有效期。
*
*a)	出发：
*
*填写出发组织
*
*b)	到达：
*
*填写到达组织
*
*c)	状态：
*
*选择状态
*
*d)	基础产品：
*
*选择产品列表
*
*e)	货物类型： 
*
*选择货物类型列表
*
*2.	查询结果列表
*
*a)	查询结果列表字段：
*
*出发，
*
*到达，
*
*出发类型，
*
*到达类型，
*
*开始范围，
*
*结束范围，
*
*折扣规则，
*
*产品，
*
*折扣率，
*
*状态
*
*折扣方案模块列表由查询条件区域和查询结果列表组成。
*
*用户通过菜单查询优惠活动列表点击进入此界面，列表要求显示
*
*1.	查询条件区域：增值优惠服务方案，折扣状态，业务日期。
*
*a)	增值优惠服务方案：输入要查询的折扣方案名称
*
*b)	折扣状态：增值优惠的状态（未激活，激活）。
*
*c)	业务日期：选择业务日期过滤增值优惠查询列表
*
*2.	查询结果列表
*
*a)	查询结果列表字段：
*
*编码，
*
*方案名称，
*
*状态，
*
*创建人，
*
*修改时间，
*
*起始时间，
*
*截止时间。
*
*b)	查询结果列表数据行打钩控件，
*
*勾选中的记录可以进行后面的删除，
*
*激活，中止的批量操作功能。
*
*c)	功能按钮：
*
*新增，
*
*点击此按钮打开“图2-增值优惠方案新增”的界面进入优惠活动新增的定义信息输入操作，
*
*输入完成后点击保存完成信息新增操作。新增加的优惠活动数据默认状态为“未激活”，
*
*参考SR1。
*
*d)	功能按钮：
*
*删除，
*
*用户勾选一个或者多个数据记录之后可以点击删除按钮，
*
*系统弹出提示“确定删除所选记录?”,
*
*用户点击确定之后执行删除操作，
*
*删除完成后，
*
*查询界面自动刷新过滤掉已被删除的数据。
*
*参考SR2。
*
*e)	功能按钮：
*
*激活，
*
*用户勾选一个或者多个数据记录之后可以点击启用按钮，
*
*系统弹出提示“确定启用所选记录?”,
*
*用户点击确定之后执行启用操作，
*
*启用完成后，
*
*查询界面自动刷新显示数据记录最新的状态。
*
*用户所选的记录必须都是“未激活”状态的数据，
*
*否则系统弹出提示“该方案已经激活，
*
*不能再次激活”。
*
*参考SR2。
*
*f)	功能按钮：中止，
*
*用户勾选一个记录之后可以点击“中止”按钮，
*
*系统弹出“设置失效时间”对话框，
*
*查询界面自动刷新显示数据记录最新的状态。
*
*用户所选的记录必须都是“启用”状态的数据，
*
*否则系统弹出提示“该方案尚未激活，
*
*不能中止”。
*
*参考SR2
*
*
*SR1	根据页面所录入的方案名称与后台数据库校验是否
*
*已经存在、存在提示“该方案名称已经存在，不能再次使用”
*
*新建方案不能与某个已经存在的方案重名，
*
*如果是对于某个选定的方案进行版本升级操作的话，
*
*可以保持方案的名称不变，
*
*同时升级版本的方案有效期可以修改，
*
*结束的方案不能再启用;
*
*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
*
*最后3位数字自动增长
*
*如下格式：ZZFA20120323001;
*
*SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
*
*“未激活”状态的方案可以进行修改和删除操作。
*
*方案数据记录状态包括“未激活”、“激活”2个。
*
*“未激活”状态的方案可以勾选，进行激活操作，
*
*已经启用的方案可以进行勾选“中止”操作，
*
*这些状态的操作均不能回退。
*
*SR3	折扣的计算。
*
*折扣的计算按优先级类型分为三大类：客户合同、渠道、产品。
*
*该优先级的级别顺序维护在ＰＫＰ．T_SRV_DISCOUNT_PRIORITY表中，
*
*根据字段值LEVELS来排序，数值越小则优先级越高。在折扣的实际计算时，
*
*根据优先级依次来判断可以符合适用的折扣 计算类型，如果有满足的计算类型，
*
*则不再使用后面的优先级类别来计算。
*
*a)	客户合同
*
*根据传入的客户编号查找相对应的客户合同的折扣率，如果查询到相应信息，
*
*则按此的折扣率进行价格计算。
*
*b)	渠道
*
*根据传入的渠道编号查找相对应的折扣率。查找的规则是，
*
*根据渠道编码以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
*
*然后按照产品、货物类型和所属行业依次进行匹配，即首先匹配产品相对应，
*
*在产品相对应的情况 下货物类型相应，然后是所属行业相对应，
*
*如果依据上述三个原则对应上的情况下则匹配到折扣条目，
*
*可以利用折扣条目所对应的数据进行折扣计算，
*
*如果存在多条匹配的记录，选取折扣最低的进行计算。
*
*计算出减免的费用需要大于等于该折扣设定的最低一票并且小于等于设定的最高一票。
*
*否则，最低减免按最低一票减免，最高减免按最高一票减免。
*
*c)	产品
*
*折扣条目的匹配原则，
*
*根据普通产品渠道以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
*
*然后按照产品、货物类型和所属行业依次进行匹配，
*
*即首先匹配产品相对应，
*
*在产品相对应的情况 下货物类型相应，
*
*然后是所属行业相对应，
*
*如果依据上述三个原则对应上的情况下则匹配到折扣条目，
*
*可以利用折扣条目所对应的数据进行折扣计算，
*
*如果存在多条匹配的记录，
*
*选取折扣最低的进行计算。
*
*计算出减免的费用需要大于该折扣设定的最低一票并且小于设定的最高一票。
*
*否则，最低减免按最低一票减免，
*
*最高减免按最高一票减免。
*
*
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/ValueAddDiscountService.java
 * 
 * FILE NAME        	: ValueAddDiscountService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPricingOrgDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.server.cache.AdministrativeRegionCacheDeal;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDiscountOrgDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventChannelDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventDAO;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IValueAddDiscountDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IValueAddDiscountService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountOrgConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.FlightPricePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PriceDiscountException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceDiscountVo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * @Description: 增值优惠方案操作类
 * 
 * ValueAddDiscountService.java Create on 2012-12-21 上午10:42:03
 * 
 * Company:IBM
 * 
 * @author IBMDP-sz
 * 
 * Copyright (c) 2012 Company,Inc. All Rights Reserved
 * 
 * @version V1.0
 */
public class ValueAddDiscountService implements IValueAddDiscountService {
	
	private static final int NUMBER_2999 = 2999;
	
	private static final long NUMBER_100 = 100L;
	
	private static final Logger log = Logger.getLogger(ValueAddDiscountService.class);
	/**
	 * 增值优惠操作DAO
	 */
    @Inject
    private IValueAddDiscountDao valueAddDiscountDao;
    /**
	 * 折扣方案（市场活动）操作DAO
	 */
    @Inject
    IMarketingEventDAO marketingEventDao;
    /**
	 * 折扣方案渠道（市场活动渠道）操作DAO
	 */    
    @Inject
    private IMarketingEventChannelDao marketingEventChannelDao;
    /**
	 * 折扣适用网点操作DAO
	 */ 
    @Inject
    private IDiscountOrgDao discountOrgDao;
    /**
	 * 计费规则操作DAO
	 */ 
    @Inject
    private IPriceValuationDao priceValuationDao;
    /**
   	 * 计价明细操作DAO
   	 */ 
    @Inject
    private IPriceCriteriaDetailDao priceCriteriaDetailDao;
    /**
   	 * 员工操作SERVICE
   	 */ 
    @Inject
    private IEmployeeService employeeService;
    /**
     * 计价条目DAO
     */
    @Inject
    private IPriceEntryDao priceEntryDao;
    /**
     * 产品SERVICE
     */
    @Inject
    private IProductService productService;
    /**
     * 货物类型DAO
     */
    @Inject
    private IGoodsTypeDao goodsTypeDao;
    /**
   	 * 数据字典SERVICE
   	 */ 
    @Inject
    private IDataDictionaryValueService dataDictionaryValueService;
    /**
     * 价格区域DAO
     */
    @Inject
	private IRegionDao regionDao;
    /**
     * 部门DAO
     */
    @Inject
    private IPricingOrgDao pricingOrgDao;
    /**
     * 行政区域操作类
     */
    private AdministrativeRegionCacheDeal administrativeRegionCacheDeal = new AdministrativeRegionCacheDeal();
    /**
     * @Description: 查询所有的二级计价条目
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-27 上午11:46:45
     * 
     * @return
     * 
     * @version V1.0
     */
    public List<PriceEntity> queryAllPricingEntries() {
    	//设置查询条件
		PriceEntity priceEntity = new PriceEntity();
		priceEntity.setActive(FossConstants.ACTIVE);
		priceEntity.setReceiveDate(new Date());
		//根据条件进行查询
		List<PriceEntity> priceEntities = priceEntryDao.searchPriceEntryByConditions(priceEntity);
		List<PriceEntity> list = null;
		//根据条件判断组装数据
		if(priceEntities != null && priceEntities.size() > 0) {
			list = new ArrayList<PriceEntity>();
			for (int i = 0; i < priceEntities.size(); i++) {
				PriceEntity entity = priceEntities.get(i);
				if(StringUtil.isNotBlank(entity.getRefCode()) && !StringUtil.equals(entity.getCode(), PriceEntityConstants.PRICING_CODE_QT)) {
					list.add(entity);
				}
			}
		}
		return list;
	}
    /**
     * *a)	增值优惠服务方案：输入要查询的折扣方案名称
	*
	* b)	折扣状态：增值优惠的状态（未激活，激活）。
	*
	*c)	业务日期：选择业务日期过滤增值优惠查询列表
     *查询结果列表字段：
	*
	*编码，
	*
	*方案名称，
	*
	*状态，
	*
	*创建人，
	*
	*修改时间，
	*
	*起始时间，
	*
	*截止时间。
	*/
	/**
	 * <p>根据条件查询价格折扣方案总数</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-13 下午8:56:26
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public Long countPriceProgramByCodition(MarketingEventEntity marketintEvent) {
		if (PricingConstants.ALL.equals(marketintEvent.getActive())) {
			marketintEvent.setActive(null);
		}
		return marketingEventDao.countMarketingEvent(marketintEvent);
	}
	/**
	 * *a)	增值优惠服务方案：输入要查询的折扣方案名称
	*
	*b)	折扣状态：增值优惠的状态（未激活，激活）。
	*
	*c)	业务日期：选择业务日期过滤增值优惠查询列表
    *查询结果列表字段：
	*
	*编码，
	*
	*方案名称，
	*
	*状态，
	*
	*创建人，
	*
	*修改时间，
	*
	*起始时间，
	*
	*截止时间。
	*/
	/**
	 * 
	 * <p>根据条件查询价格折扣方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-4 下午8:22:26
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public List<MarketingEventEntity> selectPriceProgramByCodition(MarketingEventEntity marketingEvent, int start, int limit) {
		if (PricingConstants.ALL.equals(marketingEvent.getActive())) {
			marketingEvent.setActive(null);
		}
		//根据条件进行查询
		List<MarketingEventEntity> list = marketingEventDao.queryMarketingEventList(marketingEvent, start, limit);
		List<MarketingEventEntity> marketingEventEntities = null;
		//根据条件判断组装数据
		if(CollectionUtils.isNotEmpty(list)) {
			marketingEventEntities = this.boxCatalogNameForMarketing(list);
			this.boxStaffNameForMarketing(marketingEventEntities);
		}
		return marketingEventEntities;
	}
	/**
	 * *a)	增值优惠服务方案：输入要查询的折扣方案名称
	*
	*b)	折扣状态：增值优惠的状态（未激活，激活）。
	*
	*c)	业务日期：选择业务日期过滤增值优惠查询列表
    *查询结果列表字段：
	*
	*编码，
	*
	*方案名称，
	*
	*状态，
	*
	*创建人，
	*
	*修改时间，
	*
	*起始时间，
	*
	*截止时间。
	*/
	/**
	 * <p>根据条件查询价格折扣明细</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-4 下午8:22:26
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public List<PriceDiscountDto> selectPriceDiscountByCodition(PriceDiscountDto dto) {
		List<PriceDiscountDto> list = valueAddDiscountDao.selectPriceDiscountByCodition(dto);
		List<PriceDiscountDto> priceDiscountDtos = null;
		if(CollectionUtils.isNotEmpty(list)) {
			priceDiscountDtos = this.boxCatalogName(list);
		} 
		return priceDiscountDtos;
	}
	/**
	 * *a)	增值优惠服务方案：输入要查询的折扣方案名称
	*
	*b)	折扣状态：增值优惠的状态（未激活，激活）。
	*
	*c)	业务日期：选择业务日期过滤增值优惠查询列表
    *查询结果列表字段：
	*
	*编码，
	*
	*方案名称，
	*
	*状态，
	*
	*创建人，
	*
	*修改时间，
	*
	*起始时间，
	*
	*截止时间。
	*/
	/**
	 * <p>根据条件查询价格折扣明细总数</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-13 下午8:56:26
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public Long countPriceDiscountByCodition(PriceDiscountDto dto) {
		if (PricingConstants.ALL.equals(dto.getActive())) {
			dto.setActive(null);
		}
		return valueAddDiscountDao.countPriceDiscountByCodition(dto);
	}
	/**
	 * *a)	增值优惠服务方案：输入要查询的折扣方案名称
	*
	*b)	折扣状态：增值优惠的状态（未激活，激活）。
	*
	*c)	业务日期：选择业务日期过滤增值优惠查询列表
    *查询结果列表字段：
	*
	*编码，
	*
	*方案名称，
	*
	*状态，
	*
	*创建人，
	*
	*修改时间，
	*
	*起始时间，
	*
	*截止时间。
	*/
	/**
	 * <p>根据条件查询价格折扣明细(分页)</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-4 下午8:22:26
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public List<PriceDiscountDto> selectPriceDiscountPagingByCodition(PriceDiscountDto dto, int start, int limit) {
		if (PricingConstants.ALL.equals(dto.getActive())) {
			dto.setActive(null);
		}
		if (PricingConstants.ALL.equals(dto.getProductId())){
			dto.setProductId(null);
		}
		// 货物类型的“全部”有问题
		if (PricingConstants.ALL.equals(dto.getGoodsTypeId())){
			dto.setGoodsTypeId(null);
		}
		List<PriceDiscountDto> list = valueAddDiscountDao.selectPriceDiscountByCodition(dto, start, limit);
		List<PriceDiscountDto> priceDiscountDtos = null;
		if(CollectionUtils.isNotEmpty(list)) {
			priceDiscountDtos = this.boxCatalogName(list);
		} 
		List<PriceDiscountDto> result = checkOrgNameIfNecessary(dto.getDeptOrgName(), dto.getArrvOrgName(), priceDiscountDtos);
		return result;
	}
	
	/**
	 * 如果出发和到达的orgName不为空，需要检查list中的记录是否含有这些name
	 * @param deptOrgName
	 * @param arrvOrgName
	 * @param list
	 * @return
	 */
	private List<PriceDiscountDto> checkOrgNameIfNecessary(String deptOrgName, String arrvOrgName, List<PriceDiscountDto> list){
		boolean deptFlag = (deptOrgName != null && deptOrgName.trim().length() > 0) ? true : false;
		boolean arrvFlag = (arrvOrgName != null && arrvOrgName.trim().length() > 0) ? true : false;
		List<PriceDiscountDto> result = null;
		if(deptFlag && arrvFlag){
			result = new ArrayList<PriceDiscountDto>();
			if(CollectionUtils.isNotEmpty(list)){
				for(PriceDiscountDto priceDiscountDto : list){
					if(priceDiscountDto.getDeptOrgName() != null && priceDiscountDto.getArrvOrgName() != null){
						if(priceDiscountDto.getDeptOrgName().indexOf(deptOrgName) > -1 && priceDiscountDto.getArrvOrgName().indexOf(arrvOrgName) > -1){
							result.add(priceDiscountDto);
						}
					}
				}
			}
			return result;
		}else if(deptFlag && !arrvFlag){
			result = new ArrayList<PriceDiscountDto>();
			if(CollectionUtils.isNotEmpty(list)){
				for(PriceDiscountDto priceDiscountDto : list){
					if(priceDiscountDto.getDeptOrgName() != null){
						if(priceDiscountDto.getDeptOrgName().indexOf(deptOrgName) > -1){
							result.add(priceDiscountDto);
						}
					}
				}
			}
			return result;
		}else if(!deptFlag && arrvFlag){
			result = new ArrayList<PriceDiscountDto>();
			if(CollectionUtils.isNotEmpty(list)){
				for(PriceDiscountDto priceDiscountDto : list){
					if(priceDiscountDto.getArrvOrgName() != null){
						if(priceDiscountDto.getArrvOrgName().indexOf(arrvOrgName) > -1){
							result.add(priceDiscountDto);
						}
					}
				}
			}
			return result;
		}else{
			return list;
		}
	}
	
	/**
	 *新增，
	*
	*点击此按钮打开“图2-增值优惠方案新增”的界面进入优惠活动新增的定义信息输入操作，
	*
	*输入完成后点击保存完成信息新增操作。新增加的优惠活动数据默认状态为“未激活”，
	*
	*参考SR1。
	*SR1
	*根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	 */
	/**
	 * <p>增加折扣价格方案明细</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-4 下午8:19:49
	 * 
	 * @param dto
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void addDiscountProgramItem(List<DiscountOrgEntity> startDepts, List<DiscountOrgEntity> endDepts, PriceDiscountDto dto) {
		//判断初始条件是否满足
		if(CollectionUtils.isNotEmpty(startDepts)&& CollectionUtils.isNotEmpty(endDepts)) {
			String currentOrgCode = getCurrentOrgCode();
			String currentUserCode = getCurrentUserCode();
			//遍历始发和到达部门
			//外层遍历始发部门
			for (int i = 0; i < startDepts.size(); i++) {
				DiscountOrgEntity startOrg = (DiscountOrgEntity)startDepts.get(i);
				//内层遍历始发部门
				for (int j = 0; j < endDepts.size(); j++) {
					DiscountOrgEntity arrvOrg = (DiscountOrgEntity)endDepts.get(j);
					//封装PriceValuationEntity
					//市场活动信息、折扣期限、货物类型信息、产品信息、所属行业，来自前台
					PriceValuationEntity valuationEntity = new PriceValuationEntity();
					String valuationEntityId = UUIDUtils.getUUID();
					valuationEntity.setId(valuationEntityId);
					PriceEntity priceEntity = null;
					if(StringUtil.isNotBlank(dto.getPriceEntryCode())) {
						priceEntity = priceEntryDao.queryPriceEntryByCode(dto.getPriceEntryCode());
						if(priceEntity != null) {
							//当为“其他”的情况
							if(priceEntity.getRefCode() != null && StringUtil.equals(priceEntity.getRefCode(), PriceEntityConstants.PRICING_CODE_QT)) {
								PriceEntity priceEntity2 = priceEntryDao.queryPriceEntryByCode(PriceEntityConstants.PRICING_CODE_QT);
								valuationEntity.setPricingEntryId(priceEntity2.getId());
								valuationEntity.setPricingEntryCode(priceEntity2.getCode());
							//当为“其他”之外的类型的情况
							} else {
								valuationEntity.setPricingEntryId(priceEntity.getId());
								valuationEntity.setPricingEntryCode(priceEntity.getCode());
							}
						} else {
							throw new PriceDiscountException("无法查询到相关计价条目信息", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
						}
					} else {
						throw new PriceDiscountException("计价条目不能为空", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
					}
					//组装计费规则数据，进行插入操作
					valuationEntity.setMarketingEventId(dto.getMarketId());
					valuationEntity.setMarketingEventCode(dto.getMarketCode());
					valuationEntity.setBeginTime(dto.getBeginDate());
					valuationEntity.setEndTime(dto.getEndDate());
					valuationEntity.setGoodsTypeId(dto.getGoodsTypeId());
					valuationEntity.setGoodsTypeCode(dto.getGoodsTypeCode());
					valuationEntity.setProductId(dto.getProductId());
					valuationEntity.setProductCode(dto.getProductCode());
					valuationEntity.setPricingIndustryId(dto.getPricingIndustryId());
					valuationEntity.setPricingIndustryCode(dto.getPricingIndustryCode());
					valuationEntity.setVersionNo(new Date().getTime());
					valuationEntity.setActive(FossConstants.INACTIVE);
					valuationEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
					valuationEntity.setCurrencyCode("RMB");
					valuationEntity.setCode(valuationEntity.getPricingEntryCode());
					valuationEntity.setCreateUser(currentUserCode);
					valuationEntity.setCreateOrgCode(currentOrgCode);
					valuationEntity.setCreateDate(new Date());
					priceValuationDao.insertSelective(valuationEntity);
					//封装DiscountOrgEntity
					//始发和目的地点信息
					DiscountOrgEntity orgEntity = new DiscountOrgEntity();
					String orgEntityUUId = UUIDUtils.getUUID();
					orgEntity.setId(orgEntityUUId);
					orgEntity.setDeptOrgId(startOrg.getDeptOrgId());
					orgEntity.setDeptOrgCode(startOrg.getDeptOrgCode());
					orgEntity.setDeptOrgTypeCode(startOrg.getDeptOrgTypeCode());
					orgEntity.setArrvOrgId(arrvOrg.getArrvOrgId());
					orgEntity.setArrvOrgCode(arrvOrg.getArrvOrgCode());
					orgEntity.setArrvOrgTypeCode(arrvOrg.getArrvOrgTypeCode());
					orgEntity.setBeginTime(dto.getBeginDate());
					orgEntity.setEndTime(dto.getEndDate());
					orgEntity.setCreateUserCode(currentUserCode);
					orgEntity.setCreateOrgCode(currentOrgCode);
					orgEntity.setCreateTime(new Date());
					orgEntity.settSrvPricingValuationId(valuationEntityId);
					orgEntity.setActive(FossConstants.INACTIVE);
					orgEntity.setVersionNo(new Date().getTime());
					discountOrgDao.insertSelective(orgEntity);
					
					//封装PriceCriteriaDetailEntity
					//折扣规则、开始结束范围、折扣率、最低一票、最高一票， 来自前台
					PriceCriteriaDetailEntity criteriaDetailEntity = new PriceCriteriaDetailEntity();
					String criteriaDetailEntityUUId = UUIDUtils.getUUID();
					criteriaDetailEntity.setId(criteriaDetailEntityUUId);
					criteriaDetailEntity.setCaculateType(dto.getCaculateType());
					criteriaDetailEntity.setLeftrange(dto.getLeftRange());
					criteriaDetailEntity.setRightrange(dto.getRightRange());
					criteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_DISCOUNTRATE_ID);
					criteriaDetailEntity.setDiscountRate(dto.getDiscountRate());
					criteriaDetailEntity.setMinFee(dto.getMinFee());
					criteriaDetailEntity.setMaxFee(dto.getMaxFee());
					criteriaDetailEntity.setActive(FossConstants.INACTIVE);
					criteriaDetailEntity.setVersionNo(new Date().getTime());
					criteriaDetailEntity.setPricingValuationId(valuationEntityId);
					if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QT, valuationEntity.getPricingEntryCode())) {
						criteriaDetailEntity.setSubType(priceEntity.getCode());
					}
					priceCriteriaDetailDao.insertSelective(criteriaDetailEntity);
				}
			}
		} else {
			throw new PriceDiscountException("没有选择始发或目的地点 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
		}
	}
	/**
	 *新增，
	*
	*点击此按钮打开“图2-增值优惠方案新增”的界面进入优惠活动新增的定义信息输入操作，
	*
	*输入完成后点击保存完成信息新增操作。新增加的优惠活动数据默认状态为“未激活”，
	*
	*参考SR1。
	*SR1
	*根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	 */
	/**
	 * 
	 * <p>增加折扣价格方案明细</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-4 下午8:19:49
	 * 
	 * @param dto
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void addDiscountProgramItem(String startAllNet, String arrvAllNet, List<DiscountOrgEntity> startDepts, List<DiscountOrgEntity> endDepts, PriceDiscountDto dto) {
		String currentOrgCode = getCurrentOrgCode();
		String currentUserCode = getCurrentUserCode();
		//第一种情况      始发地--目的地
		if(CollectionUtils.isNotEmpty(startDepts) && CollectionUtils.isNotEmpty(endDepts)) {
			//外层始发部门遍历
			for (int i = 0; i < startDepts.size(); i++) {
				DiscountOrgEntity startOrg = (DiscountOrgEntity)startDepts.get(i);
				//内层到达部门遍历
				for (int j = 0; j < endDepts.size(); j++) {
					DiscountOrgEntity arrvOrg = (DiscountOrgEntity)endDepts.get(j);
					//封装PriceValuationEntity
					//市场活动信息、折扣期限、货物类型信息、产品信息、所属行业，来自前台
					PriceValuationEntity valuationEntity = new PriceValuationEntity();
					String valuationEntityId = UUIDUtils.getUUID();
					valuationEntity.setId(valuationEntityId);
					PriceEntity priceEntity = null;
					if(StringUtil.isNotBlank(dto.getPriceEntryCode())) {
						priceEntity = priceEntryDao.queryPriceEntryByCode(dto.getPriceEntryCode());
						if(priceEntity != null) {
							//当为“其他”的情况
							if(priceEntity.getRefCode() != null && StringUtil.equals(priceEntity.getRefCode(), PriceEntityConstants.PRICING_CODE_QT)) {
								PriceEntity priceEntity2 = priceEntryDao.queryPriceEntryByCode(PriceEntityConstants.PRICING_CODE_QT);
								valuationEntity.setPricingEntryId(priceEntity2.getId());
								valuationEntity.setPricingEntryCode(priceEntity2.getCode());
								//当为“其他”之外的类型的情况
							} else {
								valuationEntity.setPricingEntryId(priceEntity.getId());
								valuationEntity.setPricingEntryCode(priceEntity.getCode());
							}
						} else {
							throw new PriceDiscountException("无法查询到相关计价条目信息", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
						}
					} else {
						throw new PriceDiscountException("计价条目不能为空", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
					}
					//组装计费规则数据，进行插入操作
					valuationEntity.setMarketingEventId(dto.getMarketId());
					valuationEntity.setMarketingEventCode(dto.getMarketCode());
					valuationEntity.setBeginTime(dto.getBeginDate());
					valuationEntity.setEndTime(dto.getEndDate());
					valuationEntity.setGoodsTypeId(dto.getGoodsTypeId());
					valuationEntity.setGoodsTypeCode(dto.getGoodsTypeCode());
					valuationEntity.setProductId(dto.getProductId());
					valuationEntity.setProductCode(dto.getProductCode());
					valuationEntity.setPricingIndustryId(dto.getPricingIndustryId());
					valuationEntity.setPricingIndustryCode(dto.getPricingIndustryCode());
					valuationEntity.setVersionNo(new Date().getTime());
					valuationEntity.setActive(FossConstants.INACTIVE);
					valuationEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
					valuationEntity.setCurrencyCode("RMB");
					valuationEntity.setCode(valuationEntity.getPricingEntryCode());
					valuationEntity.setCreateUser(currentUserCode);
					valuationEntity.setCreateOrgCode(currentOrgCode);
					valuationEntity.setCreateDate(new Date());
					priceValuationDao.insertSelective(valuationEntity);
					//封装DiscountOrgEntity
					//始发和目的地点信息、
					DiscountOrgEntity orgEntity = new DiscountOrgEntity();
					String orgEntityUUId = UUIDUtils.getUUID();
					orgEntity.setId(orgEntityUUId);
					orgEntity.setDeptOrgId(startOrg.getDeptOrgId());
					orgEntity.setDeptOrgCode(startOrg.getDeptOrgCode());
					orgEntity.setDeptOrgTypeCode(startOrg.getDeptOrgTypeCode());
					orgEntity.setArrvOrgId(arrvOrg.getArrvOrgId());
					orgEntity.setArrvOrgCode(arrvOrg.getArrvOrgCode());
					orgEntity.setArrvOrgTypeCode(arrvOrg.getArrvOrgTypeCode());
					orgEntity.setBeginTime(dto.getBeginDate());
					orgEntity.setEndTime(dto.getEndDate());
					orgEntity.setCreateUserCode(currentUserCode);
					orgEntity.setCreateOrgCode(currentOrgCode);
					orgEntity.setCreateTime(new Date());
					orgEntity.settSrvPricingValuationId(valuationEntityId);
					orgEntity.setActive(FossConstants.INACTIVE);
					orgEntity.setVersionNo(new Date().getTime());
					discountOrgDao.insertSelective(orgEntity);
					//封装PriceCriteriaDetailEntity
					//折扣规则、开始结束范围、折扣率、最低一票、最高一票， 来自前台
					PriceCriteriaDetailEntity criteriaDetailEntity = new PriceCriteriaDetailEntity();
					String criteriaDetailEntityUUId = UUIDUtils.getUUID();
					criteriaDetailEntity.setId(criteriaDetailEntityUUId);
					criteriaDetailEntity.setCaculateType(dto.getCaculateType());
					criteriaDetailEntity.setLeftrange(dto.getLeftRange());
					criteriaDetailEntity.setRightrange(dto.getRightRange());
					criteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_DISCOUNTRATE_ID);
					criteriaDetailEntity.setDiscountRate(dto.getDiscountRate());
					criteriaDetailEntity.setMinFee(dto.getMinFee());
					criteriaDetailEntity.setMaxFee(dto.getMaxFee());
					criteriaDetailEntity.setActive(FossConstants.INACTIVE);
					criteriaDetailEntity.setVersionNo(new Date().getTime());
					criteriaDetailEntity.setPricingValuationId(valuationEntityId);
					if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QT, valuationEntity.getPricingEntryCode())) {
						criteriaDetailEntity.setSubType(priceEntity.getCode());
					}
					priceCriteriaDetailDao.insertSelective(criteriaDetailEntity);
				}
			}
		}
		//第二种情况      始发地--全网
		if(CollectionUtils.isNotEmpty(startDepts) && StringUtils.equals(arrvAllNet, DictionaryValueConstants.MSG_TYPE__ALLNET)) {
			for (int i = 0; i < startDepts.size(); i++) {
				DiscountOrgEntity startOrg = (DiscountOrgEntity)startDepts.get(i);
				//封装PriceValuationEntity
				//市场活动信息、折扣期限、货物类型信息、产品信息、所属行业，来自前台
				PriceValuationEntity valuationEntity = new PriceValuationEntity();
				String valuationEntityId = UUIDUtils.getUUID();
				valuationEntity.setId(valuationEntityId);
				PriceEntity priceEntity = null;
				if(StringUtil.isNotBlank(dto.getPriceEntryCode())) {
					priceEntity = priceEntryDao.queryPriceEntryByCode(dto.getPriceEntryCode());
					if(priceEntity != null) {
						//当为“其他”的情况
						if(priceEntity.getRefCode() != null && StringUtil.equals(priceEntity.getRefCode(), PriceEntityConstants.PRICING_CODE_QT)) {
							PriceEntity priceEntity2 = priceEntryDao.queryPriceEntryByCode(PriceEntityConstants.PRICING_CODE_QT);
							valuationEntity.setPricingEntryId(priceEntity2.getId());
							valuationEntity.setPricingEntryCode(priceEntity2.getCode());
							//当为“其他”之外的类型的情况
						} else {
							valuationEntity.setPricingEntryId(priceEntity.getId());
							valuationEntity.setPricingEntryCode(priceEntity.getCode());
						}
					} else {
						throw new PriceDiscountException("无法查询到相关计价条目信息", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
					}
				} else {
					throw new PriceDiscountException("计价条目不能为空", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
				}
				//组装计费规则数据，进行插入操作
				valuationEntity.setMarketingEventId(dto.getMarketId());
				valuationEntity.setMarketingEventCode(dto.getMarketCode());
				valuationEntity.setBeginTime(dto.getBeginDate());
				valuationEntity.setEndTime(dto.getEndDate());
				valuationEntity.setGoodsTypeId(dto.getGoodsTypeId());
				valuationEntity.setGoodsTypeCode(dto.getGoodsTypeCode());
				valuationEntity.setProductId(dto.getProductId());
				valuationEntity.setProductCode(dto.getProductCode());
				valuationEntity.setPricingIndustryId(dto.getPricingIndustryId());
				valuationEntity.setPricingIndustryCode(dto.getPricingIndustryCode());
				valuationEntity.setVersionNo(new Date().getTime());
				valuationEntity.setActive(FossConstants.INACTIVE);
				valuationEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
				valuationEntity.setCurrencyCode("RMB");
				valuationEntity.setCode(valuationEntity.getPricingEntryCode());
				valuationEntity.setCreateUser(currentUserCode);
				valuationEntity.setCreateOrgCode(currentOrgCode);
				valuationEntity.setCreateDate(new Date());
				priceValuationDao.insertSelective(valuationEntity);
				//封装DiscountOrgEntity
				//始发和目的地点信息、
				DiscountOrgEntity orgEntity = new DiscountOrgEntity();
				String orgEntityUUId = UUIDUtils.getUUID();
				orgEntity.setId(orgEntityUUId);
				orgEntity.setDeptOrgId(startOrg.getDeptOrgId());
				orgEntity.setDeptOrgCode(startOrg.getDeptOrgCode());
				orgEntity.setDeptOrgTypeCode(startOrg.getDeptOrgTypeCode());
				orgEntity.setArrvOrgId(DictionaryValueConstants.MSG_TYPE__ALLNET);
				orgEntity.setArrvOrgCode(DictionaryValueConstants.MSG_TYPE__ALLNET);
				orgEntity.setArrvOrgTypeCode(DictionaryValueConstants.MSG_TYPE__ALLNET);
				orgEntity.setBeginTime(dto.getBeginDate());
				orgEntity.setEndTime(dto.getEndDate());
				orgEntity.setCreateUserCode(currentUserCode);
				orgEntity.setCreateOrgCode(currentOrgCode);
				orgEntity.setCreateTime(new Date());
				orgEntity.settSrvPricingValuationId(valuationEntityId);
				orgEntity.setActive(FossConstants.INACTIVE);
				orgEntity.setVersionNo(new Date().getTime());
				discountOrgDao.insertSelective(orgEntity);
				//封装PriceCriteriaDetailEntity
				//折扣规则、开始结束范围、折扣率、最低一票、最高一票， 来自前台
				PriceCriteriaDetailEntity criteriaDetailEntity = new PriceCriteriaDetailEntity();
				String criteriaDetailEntityUUId = UUIDUtils.getUUID();
				criteriaDetailEntity.setId(criteriaDetailEntityUUId);
				criteriaDetailEntity.setCaculateType(dto.getCaculateType());
				criteriaDetailEntity.setLeftrange(dto.getLeftRange());
				criteriaDetailEntity.setRightrange(dto.getRightRange());
				criteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_DISCOUNTRATE_ID);
				criteriaDetailEntity.setDiscountRate(dto.getDiscountRate());
				criteriaDetailEntity.setMinFee(dto.getMinFee());
				criteriaDetailEntity.setMaxFee(dto.getMaxFee());
				criteriaDetailEntity.setActive(FossConstants.INACTIVE);
				criteriaDetailEntity.setVersionNo(new Date().getTime());
				criteriaDetailEntity.setPricingValuationId(valuationEntityId);
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QT, valuationEntity.getPricingEntryCode())) {
					criteriaDetailEntity.setSubType(priceEntity.getCode());
				}
				priceCriteriaDetailDao.insertSelective(criteriaDetailEntity);
			}
		}
		//第三种情形   全网--目的地
		if(StringUtils.equals(startAllNet, DictionaryValueConstants.MSG_TYPE__ALLNET) && CollectionUtils.isNotEmpty(endDepts)) {
			for (int i = 0; i < endDepts.size(); i++) {
				DiscountOrgEntity arrvOrg = (DiscountOrgEntity)endDepts.get(i);
				//封装PriceValuationEntity
				//市场活动信息、折扣期限、货物类型信息、产品信息、所属行业，来自前台
				PriceValuationEntity valuationEntity = new PriceValuationEntity();
				String valuationEntityId = UUIDUtils.getUUID();
				valuationEntity.setId(valuationEntityId);
				PriceEntity priceEntity = null;
				if(StringUtil.isNotBlank(dto.getPriceEntryCode())) {
					priceEntity = priceEntryDao.queryPriceEntryByCode(dto.getPriceEntryCode());
					if(priceEntity != null) {
						//当为“其他”的情况
						if(priceEntity.getRefCode() != null && StringUtil.equals(priceEntity.getRefCode(), PriceEntityConstants.PRICING_CODE_QT)) {
							PriceEntity priceEntity2 = priceEntryDao.queryPriceEntryByCode(PriceEntityConstants.PRICING_CODE_QT);
							valuationEntity.setPricingEntryId(priceEntity2.getId());
							valuationEntity.setPricingEntryCode(priceEntity2.getCode());
							//当为“其他”之外的类型的情况
						} else {
							valuationEntity.setPricingEntryId(priceEntity.getId());
							valuationEntity.setPricingEntryCode(priceEntity.getCode());
						}
					} else {
						throw new PriceDiscountException("无法查询到相关计价条目信息", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
					}
				} else {
					throw new PriceDiscountException("计价条目不能为空", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
				}
				//组装计费规则数据，进行插入操作
				valuationEntity.setMarketingEventId(dto.getMarketId());
				valuationEntity.setMarketingEventCode(dto.getMarketCode());
				valuationEntity.setBeginTime(dto.getBeginDate());
				valuationEntity.setEndTime(dto.getEndDate());
				valuationEntity.setGoodsTypeId(dto.getGoodsTypeId());
				valuationEntity.setGoodsTypeCode(dto.getGoodsTypeCode());
				valuationEntity.setProductId(dto.getProductId());
				valuationEntity.setProductCode(dto.getProductCode());
				valuationEntity.setPricingIndustryId(dto.getPricingIndustryId());
				valuationEntity.setPricingIndustryCode(dto.getPricingIndustryCode());
				valuationEntity.setVersionNo(new Date().getTime());
				valuationEntity.setActive(FossConstants.INACTIVE);
				valuationEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
				valuationEntity.setCurrencyCode("RMB");
				valuationEntity.setCode(valuationEntity.getPricingEntryCode());
				valuationEntity.setCreateUser(currentUserCode);
				valuationEntity.setCreateOrgCode(currentOrgCode);
				valuationEntity.setCreateDate(new Date());
				priceValuationDao.insertSelective(valuationEntity);
				//封装DiscountOrgEntity
				//始发和目的地点信息、
				DiscountOrgEntity orgEntity = new DiscountOrgEntity();
				String orgEntityUUId = UUIDUtils.getUUID();
				orgEntity.setId(orgEntityUUId);
				orgEntity.setDeptOrgId(DictionaryValueConstants.MSG_TYPE__ALLNET);
				orgEntity.setDeptOrgCode(DictionaryValueConstants.MSG_TYPE__ALLNET);
				orgEntity.setDeptOrgTypeCode(DictionaryValueConstants.MSG_TYPE__ALLNET);
				orgEntity.setArrvOrgId(arrvOrg.getArrvOrgId());
				orgEntity.setArrvOrgCode(arrvOrg.getArrvOrgCode());
				orgEntity.setArrvOrgTypeCode(arrvOrg.getArrvOrgTypeCode());
				orgEntity.setBeginTime(dto.getBeginDate());
				orgEntity.setEndTime(dto.getEndDate());
				orgEntity.setCreateUserCode(currentUserCode);
				orgEntity.setCreateOrgCode(currentOrgCode);
				orgEntity.setCreateTime(new Date());
				orgEntity.settSrvPricingValuationId(valuationEntityId);
				orgEntity.setActive(FossConstants.INACTIVE);
				orgEntity.setVersionNo(new Date().getTime());
				discountOrgDao.insertSelective(orgEntity);
				//封装PriceCriteriaDetailEntity
				//折扣规则、开始结束范围、折扣率、最低一票、最高一票， 来自前台
				PriceCriteriaDetailEntity criteriaDetailEntity = new PriceCriteriaDetailEntity();
				String criteriaDetailEntityUUId = UUIDUtils.getUUID();
				criteriaDetailEntity.setId(criteriaDetailEntityUUId);
				criteriaDetailEntity.setCaculateType(dto.getCaculateType());
				criteriaDetailEntity.setLeftrange(dto.getLeftRange());
				criteriaDetailEntity.setRightrange(dto.getRightRange());
				criteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_DISCOUNTRATE_ID);
				criteriaDetailEntity.setDiscountRate(dto.getDiscountRate());
				criteriaDetailEntity.setMinFee(dto.getMinFee());
				criteriaDetailEntity.setMaxFee(dto.getMaxFee());
				criteriaDetailEntity.setActive(FossConstants.INACTIVE);
				criteriaDetailEntity.setVersionNo(new Date().getTime());
				criteriaDetailEntity.setPricingValuationId(valuationEntityId);
				if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QT, valuationEntity.getPricingEntryCode())) {
					criteriaDetailEntity.setSubType(priceEntity.getCode());
				}
				priceCriteriaDetailDao.insertSelective(criteriaDetailEntity);
			}
		}
		//第四种情形   全网--全网
		if(StringUtils.equals(startAllNet, DictionaryValueConstants.MSG_TYPE__ALLNET) && StringUtils.equals(arrvAllNet, DictionaryValueConstants.MSG_TYPE__ALLNET)) {
			//封装PriceValuationEntity
			//市场活动信息、折扣期限、货物类型信息、产品信息、所属行业，来自前台
			PriceValuationEntity valuationEntity = new PriceValuationEntity();
			String valuationEntityId = UUIDUtils.getUUID();
			valuationEntity.setId(valuationEntityId);
			PriceEntity priceEntity = null;
			if(StringUtil.isNotBlank(dto.getPriceEntryCode())) {
				priceEntity = priceEntryDao.queryPriceEntryByCode(dto.getPriceEntryCode());
				if(priceEntity != null) {
					//当为“其他”的情况
					if(priceEntity.getRefCode() != null && StringUtil.equals(priceEntity.getRefCode(), PriceEntityConstants.PRICING_CODE_QT)) {
						PriceEntity priceEntity2 = priceEntryDao.queryPriceEntryByCode(PriceEntityConstants.PRICING_CODE_QT);
						valuationEntity.setPricingEntryId(priceEntity2.getId());
						valuationEntity.setPricingEntryCode(priceEntity2.getCode());
						//当为“其他”之外的类型的情况
					} else {
						valuationEntity.setPricingEntryId(priceEntity.getId());
						valuationEntity.setPricingEntryCode(priceEntity.getCode());
					}
				} else {
					throw new PriceDiscountException("无法查询到相关计价条目信息", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
				}
			} else {
				throw new PriceDiscountException("计价条目不能为空", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
			}
			//组装计费规则数据，进行插入操作
			valuationEntity.setMarketingEventId(dto.getMarketId());
			valuationEntity.setMarketingEventCode(dto.getMarketCode());
			valuationEntity.setBeginTime(dto.getBeginDate());
			valuationEntity.setEndTime(dto.getEndDate());
			valuationEntity.setGoodsTypeId(dto.getGoodsTypeId());
			valuationEntity.setGoodsTypeCode(dto.getGoodsTypeCode());
			valuationEntity.setProductId(dto.getProductId());
			valuationEntity.setProductCode(dto.getProductCode());
			valuationEntity.setPricingIndustryId(dto.getPricingIndustryId());
			valuationEntity.setPricingIndustryCode(dto.getPricingIndustryCode());
			valuationEntity.setVersionNo(new Date().getTime());
			valuationEntity.setActive(FossConstants.INACTIVE);
			valuationEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
			valuationEntity.setCurrencyCode("RMB");
			valuationEntity.setCode(valuationEntity.getPricingEntryCode());
			valuationEntity.setCreateUser(currentUserCode);
			valuationEntity.setCreateOrgCode(currentOrgCode);
			valuationEntity.setCreateDate(new Date());
			priceValuationDao.insertSelective(valuationEntity);
			//封装DiscountOrgEntity
			//始发和目的地点信息、
			DiscountOrgEntity orgEntity = new DiscountOrgEntity();
			String orgEntityUUId = UUIDUtils.getUUID();
			orgEntity.setId(orgEntityUUId);
			orgEntity.setDeptOrgId(DictionaryValueConstants.MSG_TYPE__ALLNET);
			orgEntity.setDeptOrgCode(DictionaryValueConstants.MSG_TYPE__ALLNET);
			orgEntity.setDeptOrgTypeCode(DictionaryValueConstants.MSG_TYPE__ALLNET);
			orgEntity.setArrvOrgId(DictionaryValueConstants.MSG_TYPE__ALLNET);
			orgEntity.setArrvOrgCode(DictionaryValueConstants.MSG_TYPE__ALLNET);
			orgEntity.setArrvOrgTypeCode(DictionaryValueConstants.MSG_TYPE__ALLNET);
			orgEntity.setBeginTime(dto.getBeginDate());
			orgEntity.setEndTime(dto.getEndDate());
			orgEntity.setCreateUserCode(currentUserCode);
			orgEntity.setCreateOrgCode(currentOrgCode);
			orgEntity.setCreateTime(new Date());
			orgEntity.settSrvPricingValuationId(valuationEntityId);
			orgEntity.setActive(FossConstants.INACTIVE);
			orgEntity.setVersionNo(new Date().getTime());
			discountOrgDao.insertSelective(orgEntity);
			//封装PriceCriteriaDetailEntity
			//折扣规则、开始结束范围、折扣率、最低一票、最高一票， 来自前台
			PriceCriteriaDetailEntity criteriaDetailEntity = new PriceCriteriaDetailEntity();
			String criteriaDetailEntityUUId = UUIDUtils.getUUID();
			criteriaDetailEntity.setId(criteriaDetailEntityUUId);
			criteriaDetailEntity.setCaculateType(dto.getCaculateType());
			criteriaDetailEntity.setLeftrange(dto.getLeftRange());
			criteriaDetailEntity.setRightrange(dto.getRightRange());
			criteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_DISCOUNTRATE_ID);
			criteriaDetailEntity.setDiscountRate(dto.getDiscountRate());
			criteriaDetailEntity.setMinFee(dto.getMinFee());
			criteriaDetailEntity.setMaxFee(dto.getMaxFee());
			criteriaDetailEntity.setActive(FossConstants.INACTIVE);
			criteriaDetailEntity.setVersionNo(new Date().getTime());
			criteriaDetailEntity.setPricingValuationId(valuationEntityId);
			if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QT, valuationEntity.getPricingEntryCode())) {
				criteriaDetailEntity.setSubType(priceEntity.getCode());
			}
			priceCriteriaDetailDao.insertSelective(criteriaDetailEntity);
		}
	}
	/**
	  *a)	增值优惠服务方案：输入要查询的折扣方案名称
		*
		* b)	折扣状态：增值优惠的状态（未激活，激活）。
		*
		*c)	业务日期：选择业务日期过滤增值优惠查询列表
	  *查询结果列表字段：
		*
		*编码，
		*
		*方案名称，
		*
		*状态，
		*
		*创建人，
		*
		*修改时间，
		*
		*起始时间，
		*
		*截止时间。
		*/
	/**
	 * 
	 * <p>查询折扣价格方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-4 下午8:19:49
	 * 
	 * @param dto
	 * 
	 * @see
	 */
	@Override
	public PriceDiscountVo selectDiscountProgram(String marketEventId) {
		PriceDiscountVo vo = new PriceDiscountVo();
		MarketingEventEntity marketingEventEntity = marketingEventDao.selectByPrimaryKey(marketEventId);
		if(marketingEventEntity != null) {
			marketingEventEntity = this.boxCatalogNameForMarketing(marketingEventEntity);
			List<MarketingEventChannelEntity> channelEntityList = marketingEventChannelDao.queryMarketingEventChannelListByEventId(marketEventId);
			vo.setMarketingEventEntity(marketingEventEntity);
			vo.setChannelEntityList(channelEntityList);
		}
		return vo;
	}
	/**
	 *新增，
	*
	*点击此按钮打开“图2-增值优惠方案新增”的界面进入优惠活动新增的定义信息输入操作，
	*
	*输入完成后点击保存完成信息新增操作。新增加的优惠活动数据默认状态为“未激活”，
	*
	*参考SR1。
	*SR1
	*根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	 */
	/**
	 * 
	 * <p>新增折扣价格方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-4 下午8:19:49
	 * 
	 * @param dto
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public PriceDiscountVo addDiscountProgram(MarketingEventEntity marketingEventEntity, List <MarketingEventChannelEntity> channelEntityList) {
		if(marketingEventEntity != null && StringUtil.isNotBlank(marketingEventEntity.getName())) {
			List<MarketingEventEntity> marketingEventEntities = marketingEventDao.queryMarketingEventByName(marketingEventEntity.getName());
			if(CollectionUtils.isNotEmpty(marketingEventEntities)) {
				throw new PriceDiscountException("该方案名称已经存在，不能再次使用 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
			}
			String currentOrgCode = getCurrentOrgCode();
			String currentUserCode = getCurrentUserCode();
			//折扣方案NAME、有效期、方案描述、计价条目，来自前端
			String marketUUId = UUIDUtils.getUUID();
			String marketCode = generateProgramCode();
			if(StringUtil.isNotBlank(marketCode)) {
				marketingEventEntity.setCode(marketCode);
			} else {
				throw new PriceDiscountException("方案编号无法生成 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
			}
			//封装marketingEventEntity
			//进行数据库插入操作
			marketingEventEntity.setId(marketUUId);
			marketingEventEntity.setCreateUser(currentUserCode);
			marketingEventEntity.setCreateOrgCode(currentOrgCode);
			marketingEventEntity.setVersionNo(new Date().getTime());
			marketingEventEntity.setPriceRegionCode("");
			marketingEventEntity.setPriceRegionId("");
			marketingEventEntity.setActive(FossConstants.INACTIVE);
			marketingEventEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
			marketingEventDao.insertSelective(marketingEventEntity);
			//渠道，来自前端
			//当前端有选择渠道类型时
			if(CollectionUtils.isNotEmpty(channelEntityList)) {
				for (int i = 0; i < channelEntityList.size(); i++) {
					//封装MarketingEventChannelEntity
					//进行数据库插入操作
					String channelUUId = UUIDUtils.getUUID();
					MarketingEventChannelEntity channelEntity = channelEntityList.get(i);
					channelEntity.setId(channelUUId);
					channelEntity.setMarketingEventId(marketUUId);
					channelEntity.setBeginTime(marketingEventEntity.getBeginTime());
					channelEntity.setEndTime(marketingEventEntity.getEndTime());
					channelEntity.setCreateUser(currentUserCode);
					channelEntity.setCreateOrgCode(currentOrgCode);
					channelEntity.setCreateDate(new Date());
					channelEntity.setVersionNo(new Date().getTime());
					channelEntity.setActive(FossConstants.INACTIVE);
					marketingEventChannelDao.insertSelective(channelEntity);
				}
				//当前端未选择渠道类型时，渠道添加普通类型
			} else {
				String channelUUId = UUIDUtils.getUUID();
				//封装MarketingEventChannelEntity
				//进行数据库插入操作
				MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity(); 
				channelEntity.setId(channelUUId);
				channelEntity.setSalesChannelId(PricingConstants.ORDER_CHANNEL_PRODUCT);
				channelEntity.setSalesChannelCode(PricingConstants.ORDER_CHANNEL_PRODUCT);
				channelEntity.setMarketingEventId(marketUUId);
				channelEntity.setBeginTime(marketingEventEntity.getBeginTime());
				channelEntity.setEndTime(marketingEventEntity.getEndTime());
				channelEntity.setCreateUser(currentUserCode);
				channelEntity.setCreateOrgCode(currentOrgCode);
				channelEntity.setCreateDate(new Date());
				channelEntity.setVersionNo(new Date().getTime());
				channelEntity.setActive(FossConstants.INACTIVE);
				marketingEventChannelDao.insertSelective(channelEntity);
			}
		} else {
			throw new PriceDiscountException("折扣方案的CODE或NAME为空 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
		}
		PriceDiscountVo vo = new PriceDiscountVo();
		vo.setMarketingEventEntity(marketingEventEntity);
		vo.setChannelEntityList(channelEntityList);
		return vo;
	}
	/**
		*SR1	
		*
		*根据页面所录入的方案名称与后台数据库校验是否
		*
		*已经存在、存在提示“该方案名称已经存在，不能再次使用”
		*
		*新建方案不能与某个已经存在的方案重名，
		*
		*如果是对于某个选定的方案进行版本升级操作的话，
		*
		*可以保持方案的名称不变，
		*
		*同时升级版本的方案有效期可以修改，
		*
		*结束的方案不能再启用;
		*
		*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
		*
		*最后3位数字自动增长
		*
		*如下格式：ZZFA20120323001;
		*
		*SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
		*
		*“未激活”状态的方案可以进行修改和删除操作。
		*
		*方案数据记录状态包括“未激活”、“激活”2个。
		*
		*“未激活”状态的方案可以勾选，进行激活操作，
		*
		*已经启用的方案可以进行勾选“中止”操作，
		*
		*这些状态的操作均不能回退。
	 */
	/**
	 * <p>修改价格折扣方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:04:15
	 * 
	 * @param marketingEventEntity
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void updateDiscountProgram(
			MarketingEventEntity marketingEventEntity, List <MarketingEventChannelEntity> eventChannelEntityList) {
		if(marketingEventEntity != null && StringUtil.isNotBlank(marketingEventEntity.getId())) {
			List<MarketingEventEntity> marketingEventEntities = marketingEventDao.queryMarketingEventByName(marketingEventEntity.getName());
			if(CollectionUtils.isNotEmpty(marketingEventEntities) && marketingEventEntities.size() > 1) {
				throw new PriceDiscountException("该方案名称已经存在，不能再次使用 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
			}
			MarketingEventEntity eventEntity = marketingEventDao.selectByPrimaryKey(marketingEventEntity.getId());
			if(eventEntity != null && eventEntity.getId().equals(marketingEventEntity.getId())) {
				String currentOrgCode = getCurrentOrgCode();
				String currentUserCode = getCurrentUserCode();
				//首先删除之前的渠道信息，再重新建立
				marketingEventChannelDao.deleteByMarketEventId(marketingEventEntity.getId());
				if(eventChannelEntityList != null && eventChannelEntityList.size() > 0) {
					for (int i = 0; i < eventChannelEntityList.size(); i++) {
						//封装MarketingEventChannelEntity
						//进行数据库修改操作
						MarketingEventChannelEntity eventChannelEntity = eventChannelEntityList.get(i);
						MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity(); 
						String channelUUId = UUIDUtils.getUUID();
						channelEntity.setId(channelUUId);
						channelEntity.setSalesChannelId(eventChannelEntity.getSalesChannelId());
						channelEntity.setSalesChannelCode(eventChannelEntity.getSalesChannelCode());
						channelEntity.setMarketingEventId(marketingEventEntity.getId());
						channelEntity.setBeginTime(marketingEventEntity.getBeginTime());
						channelEntity.setEndTime(marketingEventEntity.getEndTime());
						channelEntity.setCreateUser(currentUserCode);
						channelEntity.setCreateDate(new Date());
						channelEntity.setCreateOrgCode(currentOrgCode);
						channelEntity.setModifyUser(currentUserCode);
						channelEntity.setModifyDate(new Date());
						channelEntity.setModifyOrgCode(currentOrgCode);
						channelEntity.setVersionNo(new Date().getTime());
						marketingEventChannelDao.insertSelective(channelEntity);
					}
				} else {
					String channelUUId = UUIDUtils.getUUID();
					//封装MarketingEventChannelEntity
					//进行数据库插入操作
					MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity(); 
					channelEntity.setId(channelUUId);
					channelEntity.setSalesChannelId(PricingConstants.ORDER_CHANNEL_PRODUCT);
					channelEntity.setSalesChannelCode(PricingConstants.ORDER_CHANNEL_PRODUCT);
					channelEntity.setMarketingEventId(marketingEventEntity.getId());
					channelEntity.setBeginTime(marketingEventEntity.getBeginTime());
					channelEntity.setEndTime(marketingEventEntity.getEndTime());
					channelEntity.setCreateUser(currentUserCode);
					channelEntity.setCreateOrgCode(currentOrgCode);
					channelEntity.setCreateDate(new Date());
					channelEntity.setVersionNo(new Date().getTime());
					channelEntity.setActive(FossConstants.INACTIVE);
					marketingEventChannelDao.insertSelective(channelEntity);
				}
				//根据优惠主键ID查询
				PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
				priceDiscountDto.setMarketId(marketingEventEntity.getId());
				List<PriceDiscountDto> priceDiscountDtos = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
				if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
					for (int i = 0; i < priceDiscountDtos.size(); i++) {
						PriceDiscountDto dto = priceDiscountDtos.get(i);
						PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
						PriceEntity priceEntity = null;
						if(StringUtil.isNotBlank(marketingEventEntity.getPricingEntryCode())) {
							priceEntity = priceEntryDao.queryPriceEntryByCode(marketingEventEntity.getPricingEntryCode());
							if(priceEntity != null) {
								//当为“其他”的情况
								if(priceEntity.getRefCode() != null && StringUtil.equals(priceEntity.getRefCode(), PriceEntityConstants.PRICING_CODE_QT)) {
									PriceEntity priceEntity2 = priceEntryDao.queryPriceEntryByCode(PriceEntityConstants.PRICING_CODE_QT);
									priceValuationEntity.setPricingEntryId(priceEntity2.getId());
									priceValuationEntity.setPricingEntryCode(priceEntity2.getCode());
								//当为“其他”之外的类型的情况
								} else {
									priceValuationEntity.setPricingEntryId(priceEntity.getId());
									priceValuationEntity.setPricingEntryCode(priceEntity.getCode());
								}
							} else {
								throw new PriceDiscountException("无法查询到相关计价条目信息", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
							}
						} else {
							throw new PriceDiscountException("计价条目不能为空", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
						}
						//封装PriceValuationEntity
						//进行数据库修改操作
						priceValuationEntity.setId(dto.getPriceValuationId());
						priceValuationEntity.setBeginTime(marketingEventEntity.getBeginTime());
						priceValuationEntity.setEndTime(marketingEventEntity.getEndTime());
						priceValuationEntity.setCode(priceValuationEntity.getCode());
						priceValuationEntity.setModifyUser(currentUserCode);
						priceValuationEntity.setModifyDate(new Date());
						priceValuationEntity.setModifyOrgCode(currentOrgCode);
						priceValuationEntity.setVersionNo(new Date().getTime());
						priceValuationDao.updateValuation(priceValuationEntity);
						
						//封装PriceCriteriaDetailEntity
						//进行数据库修改操作
						PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
						priceCriteriaDetailEntity.setId(dto.getPriceCriteriaId());
						priceCriteriaDetailEntity.setModifyUser(currentUserCode);
						priceCriteriaDetailEntity.setModifyDate(new Date());
						priceCriteriaDetailEntity.setVersionNo(new Date().getTime());
						if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QT, priceValuationEntity.getPricingEntryCode())) {
							priceCriteriaDetailEntity.setSubType(priceEntity.getCode());
						} else {
							priceCriteriaDetailEntity.setSubType(null);
						}
						priceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
						//封装DiscountOrgEntity
						//进行数据库修改操作
						DiscountOrgEntity discountOrgEntity = new DiscountOrgEntity();
						discountOrgEntity.setId(dto.getDiscountOrgId());
						discountOrgEntity.setBeginTime(marketingEventEntity.getBeginTime());
						discountOrgEntity.setEndTime(marketingEventEntity.getEndTime());
						discountOrgEntity.setModifyUser(currentUserCode);
						discountOrgEntity.setModifyTime(new Date());
						discountOrgEntity.setModifyOrgCode(currentOrgCode);
						discountOrgEntity.setVersionNo(new Date().getTime());
						discountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
					}
				}
				//封装marketingEventEntity
				//进行数据库修改操作
				eventEntity.setCode(marketingEventEntity.getCode());
				eventEntity.setName(marketingEventEntity.getName());
				eventEntity.setBeginTime(marketingEventEntity.getBeginTime());
				eventEntity.setEndTime(marketingEventEntity.getEndTime());
				eventEntity.setRemark(marketingEventEntity.getRemark());
				eventEntity.setPricingEntryId(marketingEventEntity.getPricingEntryId());
				eventEntity.setPricingEntryCode(marketingEventEntity.getPricingEntryCode());
				eventEntity.setModifyUser(currentUserCode);
				eventEntity.setModifyOrgCode(currentOrgCode);
				eventEntity.setVersionNo(new Date().getTime());
				marketingEventDao.updateByPrimaryKeySelective(eventEntity);
			} else {
				throw new PriceDiscountException("没有查询到相应的市场活动实体记录 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
			}
		} else {
			throw new PriceDiscountException("没有查询到相应的市场活动实体记录 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
		}
	}
	/**
	 用户勾选一个记录之后可以点击“中止”按钮，
	*
	*系统弹出“设置失效时间”对话框，
	*
	*查询界面自动刷新显示数据记录最新的状态。
	*
	*用户所选的记录必须都是“启用”状态的数据，
	*
	*否则系统弹出提示“该方案尚未激活，
	*
	*不能中止”。
	*
	*参考SR2。

	*SR1	根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	*
	*SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
	*
	*“未激活”状态的方案可以进行修改和删除操作。
	*
	*方案数据记录状态包括“未激活”、“激活”2个。
	*
	*“未激活”状态的方案可以勾选，进行激活操作，
	*
	*已经启用的方案可以进行勾选“中止”操作，
	*
	*这些状态的操作均不能回退。
 */
	/**
	 * 
	 * @Description: 中止折扣方案
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-19 下午8:54:30
	 * 
	 * @param marketEventId
	 * 
	 * @version V1.0
	 */
	@Override
	@Transactional
	public void terminateDiscountProgram(String marketEventId, Date endTime) {
		if(marketEventId != null && !"".equals(marketEventId)) {
			MarketingEventEntity eventEntity = marketingEventDao.selectByPrimaryKey(marketEventId);
			if(eventEntity != null && marketEventId.equals(eventEntity.getId())) {
				if(StringUtil.equals(eventEntity.getActive(), FossConstants.INACTIVE)) {
					throw new PriceDiscountException("该方案尚未激活，不能中止 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
				}
				if(endTime.after(eventEntity.getEndTime()))
        		{
        		    throw new PricePlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
        		}
				if(endTime.before(new Date())){
		    	    throw new FlightPricePlanException("中止日期必须大于当前营业日期!",null);
		    	}
				PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
				priceDiscountDto.setMarketId(marketEventId);
				//封装MarketingEventEntity
				//进行数据库修改操作
				eventEntity.setEndTime(endTime);
				eventEntity.setModifyDate(new Date());
				eventEntity.setModifyUser(getCurrentUserCode());
				eventEntity.setModifyOrgCode(getCurrentOrgCode());
				marketingEventDao.updateByPrimaryKeySelective(eventEntity);
				List<MarketingEventChannelEntity> list1 = marketingEventChannelDao.queryMarketingEventChannelListByEventId(marketEventId);
				if(list1 != null && list1.size() > 0) {
					for (int i = 0; i < list1.size(); i++) {
						//封装MarketingEventChannelEntity
						//进行数据库修改操作
						MarketingEventChannelEntity channelEntity = list1.get(i);
						channelEntity.setEndTime(endTime);
						channelEntity.setModifyDate(new Date());
						channelEntity.setModifyUser(getCurrentUserCode());
						channelEntity.setModifyOrgCode(getCurrentOrgCode());
						marketingEventChannelDao.updateByPrimaryKeySelective(channelEntity);
					}
				}
				//明细
				List<PriceDiscountDto> list2 = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
				if(list2 != null && list2.size() > 0) {
					for (int i = 0; i < list2.size(); i++) {
						PriceDiscountDto dto = list2.get(i);
						//复制计费规则
						PriceValuationEntity priceValuationEntity = priceValuationDao.selectByPrimaryKey(dto.getPriceValuationId());
						priceValuationEntity.setEndTime(endTime);
						priceValuationEntity.setModifyDate(new Date());
						priceValuationEntity.setModifyUser(getCurrentUserCode());
						priceValuationEntity.setModifyOrgCode(getCurrentOrgCode());
						priceValuationDao.updateValuation(priceValuationEntity);
						//复制市场活动适用渠道
						DiscountOrgEntity discountOrgEntity = discountOrgDao.selectByPrimaryKey(dto.getDiscountOrgId());
						discountOrgEntity.setEndTime(endTime);
						discountOrgEntity.setModifyDate(new Date());
						discountOrgEntity.setModifyUser(getCurrentUserCode());
						discountOrgEntity.setModifyOrgCode(getCurrentOrgCode());
						discountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
					}
				}
			}
		}
	}
	/**
	* 用户勾选一个记录之后可以点击“中止”按钮，
	*
	*系统弹出“设置失效时间”对话框，
	*
	*查询界面自动刷新显示数据记录最新的状态。
	*
	*用户所选的记录必须都是“启用”状态的数据，
	*
	*否则系统弹出提示“该方案尚未激活，
	*
	*不能中止”。
	*
	*参考SR2。
	*
	*SR1	
	* 
	* 根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	*
	*SR2	
	*
	* 点击按钮保存后、所录入的记录默认状态为“未激活”状态，
	*
	*“未激活”状态的方案可以进行修改和删除操作。
	*
	*方案数据记录状态包括“未激活”、“激活”2个。
	*
	*“未激活”状态的方案可以勾选，进行激活操作，
	*
	*已经启用的方案可以进行勾选“中止”操作，
	*
	*这些状态的操作均不能回退。
	*/
	/**
	 * 
	 * @Description: 立即中止折扣方案
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-19 下午8:54:30
	 * 
	 * @param marketEventId
	 * 
	 * @version V1.0
	 */
	@Override
	@Transactional
	public void terminateImmediatelyDiscountProgram(String marketEventId, Date endTime) {
		if(marketEventId != null && !"".equals(marketEventId)) {
			MarketingEventEntity eventEntity = marketingEventDao.selectByPrimaryKey(marketEventId);
			if(eventEntity != null && marketEventId.equals(eventEntity.getId())) {
				if(StringUtil.equals(eventEntity.getActive(), FossConstants.INACTIVE)) {
					throw new PriceDiscountException("该方案尚未激活，不能中止 !", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
				}
				if(endTime.after(eventEntity.getEndTime()))
        		{
        		    throw new PricePlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
        		}
				if(endTime.before(new Date())){
		    	    throw new FlightPricePlanException("中止日期必须大于当前营业日期!",null);
		    	}
				PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
				priceDiscountDto.setMarketId(marketEventId);
				//封装MarketingEventEntity
				//进行数据库修改操作
				eventEntity.setEndTime(endTime);
				eventEntity.setModifyDate(new Date());
				eventEntity.setModifyUser(getCurrentUserCode());
				eventEntity.setModifyOrgCode(getCurrentOrgCode());
				marketingEventDao.updateByPrimaryKeySelective(eventEntity);
				List<MarketingEventChannelEntity> list1 = marketingEventChannelDao.queryMarketingEventChannelListByEventId(marketEventId);
				if(list1 != null && list1.size() > 0) {
					for (int i = 0; i < list1.size(); i++) {
						//封装MarketingEventChannelEntity
						//进行数据库修改操作
						MarketingEventChannelEntity channelEntity = list1.get(i);
						channelEntity.setEndTime(endTime);
						channelEntity.setModifyDate(new Date());
						channelEntity.setModifyUser(getCurrentUserCode());
						channelEntity.setModifyOrgCode(getCurrentOrgCode());
						marketingEventChannelDao.updateByPrimaryKeySelective(channelEntity);
					}
				}
				//明细
				List<PriceDiscountDto> list2 = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
				if(list2 != null && list2.size() > 0) {
					for (int i = 0; i < list2.size(); i++) {
						PriceDiscountDto dto = list2.get(i);
						//复制计费规则
						PriceValuationEntity priceValuationEntity = priceValuationDao.selectByPrimaryKey(dto.getPriceValuationId());
						priceValuationEntity.setEndTime(endTime);
						priceValuationEntity.setModifyDate(new Date());
						priceValuationEntity.setModifyUser(getCurrentUserCode());
						priceValuationEntity.setModifyOrgCode(getCurrentOrgCode());
						priceValuationDao.updateValuation(priceValuationEntity);
						//复制市场活动适用渠道
						DiscountOrgEntity discountOrgEntity = discountOrgDao.selectByPrimaryKey(dto.getDiscountOrgId());
						discountOrgEntity.setEndTime(endTime);
						discountOrgEntity.setModifyDate(new Date());
						discountOrgEntity.setModifyUser(getCurrentUserCode());
						discountOrgEntity.setModifyOrgCode(getCurrentOrgCode());
						discountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
					}
				}
			}
		}
	}
	/**
	 *删除，
	*
	*用户勾选一个或者多个数据记录之后可以点击删除按钮，
	*
	*系统弹出提示“确定删除所选记录?”,
	*
	*用户点击确定之后执行删除操作，
	*
	*删除完成后，
	*
	*查询界面自动刷新过滤掉已被删除的数据。
	*
	*参考SR2。
	*SR1	
	*
	*根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	*
	*SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
	*
	*“未激活”状态的方案可以进行修改和删除操作。
	*
	*方案数据记录状态包括“未激活”、“激活”2个。
	*
	*“未激活”状态的方案可以勾选，进行激活操作，
	*
	*已经启用的方案可以进行勾选“中止”操作，
	*
	*这些状态的操作均不能回退。
	*/
	/**
	 * 
	 * <p>删除价格折扣方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:02:48
	 * 
	 * @param marketingEventId
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void deleteDiscountProgram(List<String> marketingEventIds) {
		if(marketingEventIds != null && marketingEventIds.size() > 0) {
			for (int k = 0; k < marketingEventIds.size(); k++) {
				String marketEventId = marketingEventIds.get(k);
				PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
				priceDiscountDto.setMarketId(marketEventId);
				List<PriceDiscountDto> list = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
				if(list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						PriceDiscountDto dto = list.get(i);
						//删除优惠适用区域信息
						discountOrgDao.deleteByPrimaryKey(dto.getDiscountOrgId());
						//删除计价明细信息
						priceCriteriaDetailDao.deleteByPrimaryKey(dto.getPriceCriteriaId());
						//删除计费明细信息
						priceValuationDao.deleteByPrimaryKey(dto.getPriceValuationId());
					}
				}
				//删除渠道明细信息
				marketingEventChannelDao.deleteByMarketEventId(marketEventId);
				//删除优惠项目信息
				marketingEventDao.deleteByPrimaryKey(marketEventId);
			}
		}
	}
	/**
	*SR1	
	*
	*根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	*
	*SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
	*
	*“未激活”状态的方案可以进行修改和删除操作。
	*
	*方案数据记录状态包括“未激活”、“激活”2个。
	*
	*“未激活”状态的方案可以勾选，进行激活操作，
	*
	*已经启用的方案可以进行勾选“中止”操作，
	*
	*这些状态的操作均不能回退。
 */
	/**
	 * 
	 * <p>修改价格折扣方案状态</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:04:15
	 * 
	 * @param marketingEventEntity
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void activateDiscountProgram(List<String> marketEventIds) {
		if(marketEventIds != null && marketEventIds.size() > 0) {
			for (int k = 0; k < marketEventIds.size(); k++) {
				String marketEventId = marketEventIds.get(k);
				MarketingEventEntity eventEntity = marketingEventDao.selectByPrimaryKey(marketEventId);
				if(eventEntity != null && eventEntity.getId().equals(marketEventId)) {
					if(FossConstants.ACTIVE.equals(eventEntity.getActive())) {
						throw new PriceDiscountException("该方案已经激活，不能再次激活", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
					}
					//根据条件查询相应信息
					//用于数据比较的基本信息
					PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
					priceDiscountDto.setMarketId(marketEventId);
					List<PriceDiscountDto> allList = valueAddDiscountDao.selectPriceDiscountAllByCodition(priceDiscountDto);
					if(CollectionUtils.isNotEmpty(allList)) {
						//进行数据的比较
						for (int i = 0; i < allList.size(); i++) {
							PriceDiscountDto dto = allList.get(i);
							checkProgramIntersection(dto);
						}
						//比较成功进行状态的激活
						List<PriceDiscountDto> list = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
						for (int i = 0; i < list.size(); i++) {
							//封装PriceValuationEntity
							//进行数据库修改操作
							PriceDiscountDto dto = list.get(i);
							PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
							priceValuationEntity.setId(dto.getPriceValuationId());
							priceValuationEntity.setActive(FossConstants.ACTIVE);
							priceValuationDao.updateValuation(priceValuationEntity);
							//封装PriceCriteriaDetailEntity
							//进行数据库修改操作
							PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
							priceCriteriaDetailEntity.setId(dto.getPriceCriteriaId());
							priceCriteriaDetailEntity.setActive(FossConstants.ACTIVE);
							priceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
							//封装DiscountOrgEntity
							//进行数据库修改操作
							DiscountOrgEntity discountOrgEntity = new DiscountOrgEntity();
							discountOrgEntity.setId(dto.getDiscountOrgId());
							discountOrgEntity.setActive(FossConstants.ACTIVE);
							discountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
						}
					} else {
						throw new PriceDiscountException("该方案没有具体明细，不能激活", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
					}
					//封装MarketingEventEntity
					//进行数据库修改操作
					eventEntity.setActive(FossConstants.ACTIVE);
					marketingEventDao.updateByPrimaryKeySelective(eventEntity);
					//封装MarketingEventChannelEntity
					//进行数据库修改操作
					MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity();
					channelEntity.setActive(FossConstants.ACTIVE);
					channelEntity.setMarketingEventId(marketEventId);
					marketingEventChannelDao.updateByMarketEventIdSelective(channelEntity);
				}
			}
		}
	}
	/**
	 * 
	*激活，
	*
	*用户勾选一个或者多个数据记录之后可以点击启用按钮，
	*
	*系统弹出提示“确定启用所选记录?”,
	*
	*用户点击确定之后执行启用操作，
	*
	*启用完成后，
	*
	*查询界面自动刷新显示数据记录最新的状态。
	*
	*用户所选的记录必须都是“未激活”状态的数据，
	*
	*否则系统弹出提示“该方案已经激活，
	*
	*不能再次激活”。
	*
	*参考SR2。
	*
	*SR1	
	*
	*根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	*
	*SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
	*
	*“未激活”状态的方案可以进行修改和删除操作。
	*
	*方案数据记录状态包括“未激活”、“激活”2个。
	*
	*“未激活”状态的方案可以勾选，进行激活操作，
	*
	*已经启用的方案可以进行勾选“中止”操作，
	*
	*这些状态的操作均不能回退。
	*/
	/**
	 * 
	 * <p>立即修改价格折扣方案状态</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:04:15
	 * 
	 * @param marketingEventEntity
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void activateImmediatelyDiscountProgram(String marketEventId, Date beginTime) {
		MarketingEventEntity eventEntity = marketingEventDao.selectByPrimaryKey(marketEventId);
		if(eventEntity != null && eventEntity.getId().equals(marketEventId)) {
			if(FossConstants.ACTIVE.equals(eventEntity.getActive())) {
				throw new PriceDiscountException("该方案已经激活,不能再次激活", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
			}
			List<MarketingEventEntity> marketingEventEntities = marketingEventDao.selectByMarketCode(eventEntity.getCode(), beginTime);
			if(CollectionUtils.isNotEmpty(marketingEventEntities)) {
				for (MarketingEventEntity marketingEventEntity : marketingEventEntities) {
					terminateImmediatelyDiscountProgram(marketingEventEntity.getId(), beginTime);
				}
			} 
			//根据条件查询相应信息
			//用于数据比较的基本信息
			PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
			priceDiscountDto.setMarketId(marketEventId);
			List<PriceDiscountDto> alllist = valueAddDiscountDao.selectPriceDiscountAllByCodition(priceDiscountDto);
			if(CollectionUtils.isNotEmpty(alllist)) {
				//进行数据的比较
				for (int i = 0; i < alllist.size(); i++) {
					PriceDiscountDto dto = alllist.get(i);
					checkProgramIntersection(dto);
				}
				//比较成功进行状态的激活
				List<PriceDiscountDto> list = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
				for (int i = 0; i < list.size(); i++) {
					PriceDiscountDto dto = list.get(i);
					//封装PriceValuationEntity
					//进行数据库修改操作
					PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
					priceValuationEntity.setId(dto.getPriceValuationId());
					priceValuationEntity.setActive(FossConstants.ACTIVE);
					priceValuationDao.updateValuation(priceValuationEntity);
					//封装PriceCriteriaDetailEntity
					//进行数据库修改操作
					PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
					priceCriteriaDetailEntity.setId(dto.getPriceCriteriaId());
					priceCriteriaDetailEntity.setActive(FossConstants.ACTIVE);
					priceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
					//封装DiscountOrgEntity
					//进行数据库修改操作
					DiscountOrgEntity discountOrgEntity = new DiscountOrgEntity();
					discountOrgEntity.setId(dto.getDiscountOrgId());
					discountOrgEntity.setActive(FossConstants.ACTIVE);
					discountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
				}
			} else {
				throw new PriceDiscountException("该方案没有具体明细,不能激活", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
			}
			//封装MarketingEventEntity
			//进行数据库修改操作
			eventEntity.setBeginTime(beginTime);
			eventEntity.setActive(FossConstants.ACTIVE);
			marketingEventDao.updateByPrimaryKeySelective(eventEntity);
			//封装MarketingEventChannelEntity
			//进行数据库修改操作
			MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity();
			channelEntity.setActive(FossConstants.ACTIVE);
			channelEntity.setMarketingEventId(marketEventId);
			marketingEventChannelDao.updateByMarketEventIdSelective(channelEntity);
		}
	}
//	public void activateImmediatelyDiscountProgram(String marketEventId, Date beginTime) {
//		MarketingEventEntity eventEntity = marketingEventDao.selectByPrimaryKey(marketEventId);
//		if(eventEntity != null && eventEntity.getId().equals(marketEventId)) {
//			if(FossConstants.ACTIVE.equals(eventEntity.getActive())) {
//				throw new PriceDiscountException("该方案已经激活，不能再次激活", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
//			}
//			//根据条件查询相应信息
//			//用于数据比较的基本信息
//			PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
//			priceDiscountDto.setMarketId(marketEventId);
//			List<PriceDiscountDto> Alllist = valueAddDiscountDao.selectPriceDiscountAllByCodition(priceDiscountDto);
//			if(CollectionUtils.isNotEmpty(Alllist)) {
//				//进行数据的比较
//				for (int i = 0; i < Alllist.size(); i++) {
//					PriceDiscountDto dto = Alllist.get(i);
//					checkProgramIntersection(dto);
//				}
//				//比较成功进行状态的激活
//				List<PriceDiscountDto> list = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
//				for (int i = 0; i < list.size(); i++) {
//					PriceDiscountDto dto = list.get(i);
//					//封装PriceValuationEntity
//					//进行数据库修改操作
//					PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
//					priceValuationEntity.setId(dto.getPriceValuationId());
//					priceValuationEntity.setActive(FossConstants.ACTIVE);
//					priceValuationDao.updateValuation(priceValuationEntity);
//					//封装PriceCriteriaDetailEntity
//					//进行数据库修改操作
//					PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
//					priceCriteriaDetailEntity.setId(dto.getPriceCriteriaId());
//					priceCriteriaDetailEntity.setActive(FossConstants.ACTIVE);
//					priceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
//					//封装DiscountOrgEntity
//					//进行数据库修改操作
//					DiscountOrgEntity discountOrgEntity = new DiscountOrgEntity();
//					discountOrgEntity.setId(dto.getDiscountOrgId());
//					discountOrgEntity.setActive(FossConstants.ACTIVE);
//					discountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
//				}
//			} else {
//				throw new PriceDiscountException("该方案没有具体明细，不能激活", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
//			}
//			//封装MarketingEventEntity
//			//进行数据库修改操作
//			eventEntity.setBeginTime(beginTime);
//			eventEntity.setActive(FossConstants.ACTIVE);
//			marketingEventDao.updateByPrimaryKeySelective(eventEntity);
//			//封装MarketingEventChannelEntity
//			//进行数据库修改操作
//			MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity();
//			channelEntity.setActive(FossConstants.ACTIVE);
//			channelEntity.setMarketingEventId(marketEventId);
//			marketingEventChannelDao.updateByMarketEventIdSelective(channelEntity);
//		}
//	}
	/**
	 *新增，
	*
	*点击此按钮打开“图2-增值优惠方案新增”的界面进入优惠活动新增的定义信息输入操作，
	*
	*输入完成后点击保存完成信息新增操作。新增加的优惠活动数据默认状态为“未激活”，
	*
	*参考SR1。
	*SR1
	*根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	 */
	/**
	 * 
	 * <p>拷贝价格折扣方案</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-6 下午3:04:15
	 * 
	 * @param marketingEventEntity
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public PriceDiscountVo copyDiscountProgram(String marketEventId) {
		MarketingEventEntity eventEntity2 = null;
		List<MarketingEventChannelEntity> marketingEventChannelEntities = new ArrayList<MarketingEventChannelEntity>();
		if(marketEventId != null && !"".equals(marketEventId)) {
			MarketingEventEntity eventEntity = marketingEventDao.selectByPrimaryKey(marketEventId);
			if(eventEntity != null && marketEventId.equals(eventEntity.getId())) {
				PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
				priceDiscountDto.setMarketId(marketEventId);
				//方案的拷贝
				eventEntity2 = new MarketingEventEntity();
				try {
					PropertyUtils.copyProperties(eventEntity2, eventEntity);
				} catch (IllegalAccessException e) {
					log.info(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					log.info(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					log.info(e.getMessage(), e);
				}
				String eventEntityUUId = UUIDUtils.getUUID();
				eventEntity2.setId(eventEntityUUId);
				//封装marketingEventEntity
				//进行数据库插入操作
				eventEntity2.setActive(FossConstants.INACTIVE);
				eventEntity2.setVersionNo(new Date().getTime());
				eventEntity2.setBeginTime(getNextBusinessDate());
				eventEntity2.setEndTime(getMaxBusinessDate());
				eventEntity2.setCreateDate(new Date());
				eventEntity2.setCreateUser(getCurrentUserCode());
				eventEntity2.setCreateOrgCode(getCurrentOrgCode());
				eventEntity2.setModifyDate(new Date());
				eventEntity2.setModifyUser(getCurrentUserCode());
				eventEntity2.setModifyOrgCode(getCurrentOrgCode());
				marketingEventDao.insertSelective(eventEntity2);
				List<MarketingEventChannelEntity> list1 = marketingEventChannelDao.queryMarketingEventChannelListByEventId(marketEventId);
				if(list1 != null && list1.size() > 0) {
					for (int i = 0; i < list1.size(); i++) {
						MarketingEventChannelEntity channelEntity = list1.get(i);
						//渠道的拷贝
						MarketingEventChannelEntity channelEntity2 = new MarketingEventChannelEntity();
						try {
							PropertyUtils.copyProperties(channelEntity2, channelEntity);
						} catch (IllegalAccessException e) {
							log.info(e.getMessage(), e);
						} catch (InvocationTargetException e) {
							log.info(e.getMessage(), e);
						} catch (NoSuchMethodException e) {
							log.info(e.getMessage(), e);
						}
						//封装MarketingEventChannelEntity
						//进行数据库插入操作
						String channelEntityUUId = UUIDUtils.getUUID();
						channelEntity2.setId(channelEntityUUId);
						channelEntity2.setMarketingEventId(eventEntityUUId);
						channelEntity2.setVersionNo(new Date().getTime());
						channelEntity2.setActive(FossConstants.INACTIVE);
						channelEntity2.setBeginTime(getNextBusinessDate());
						channelEntity2.setEndTime(getMaxBusinessDate());
						channelEntity2.setCreateDate(new Date());
						channelEntity2.setCreateUser(getCurrentUserCode());
						channelEntity2.setCreateOrgCode(getCurrentOrgCode());
						channelEntity2.setModifyDate(new Date());
						channelEntity2.setModifyUser(getCurrentUserCode());
						channelEntity2.setModifyOrgCode(getCurrentOrgCode());
						marketingEventChannelDao.insertSelective(channelEntity2);
						marketingEventChannelEntities.add(channelEntity2);
					}
				}
				//明细
				List<PriceDiscountDto> list2 = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
				if(list2 != null && list2.size() > 0) {
					for (int i = 0; i < list2.size(); i++) {
						PriceDiscountDto dto = list2.get(i);
						//复制计费规则
						PriceValuationEntity priceValuationEntity = priceValuationDao.selectByPrimaryKey(dto.getPriceValuationId());
						PriceValuationEntity priceValuationEntity2 = new PriceValuationEntity();
						try {
							if(priceValuationEntity!=null){
								PropertyUtils.copyProperties(priceValuationEntity2, priceValuationEntity);
							}
						} catch (IllegalAccessException e) {
							log.info(e.getMessage(), e);
						} catch (InvocationTargetException e) {
							log.info(e.getMessage(), e);
						} catch (NoSuchMethodException e) {
							log.info(e.getMessage(), e);
						}
						//封装PriceValuationEntity
						//进行数据库插入操作
						String valuationEntityUUId = UUIDUtils.getUUID();
						priceValuationEntity2.setId(valuationEntityUUId);
						priceValuationEntity2.setMarketingEventId(eventEntityUUId);
						priceValuationEntity2.setVersionNo(new Date().getTime());
						priceValuationEntity2.setActive(FossConstants.INACTIVE);
						priceValuationEntity2.setBeginTime(getNextBusinessDate());
						priceValuationEntity2.setEndTime(getMaxBusinessDate());
						priceValuationEntity2.setCreateDate(new Date());
						priceValuationEntity2.setCreateUser(getCurrentUserCode());
						priceValuationEntity2.setCreateOrgCode(getCurrentOrgCode());
						priceValuationEntity2.setModifyDate(new Date());
						priceValuationEntity2.setModifyUser(getCurrentUserCode());
						priceValuationEntity2.setModifyOrgCode(getCurrentOrgCode());
						priceValuationDao.insertSelective(priceValuationEntity2);
						//复制市场活动适用渠道
						DiscountOrgEntity discountOrgEntity = discountOrgDao.selectByPrimaryKey(dto.getDiscountOrgId());
						DiscountOrgEntity discountOrgEntity2 = new DiscountOrgEntity();
						try {
							if(discountOrgEntity!= null){
								PropertyUtils.copyProperties(discountOrgEntity2, discountOrgEntity);
							}
						} catch (IllegalAccessException e) {
							log.info(e.getMessage(), e);
						} catch (InvocationTargetException e) {
							log.info(e.getMessage(), e);
						} catch (NoSuchMethodException e) {
							log.info(e.getMessage(), e);
						}
						//封装DiscountOrgEntity
						//进行数据库插入操作
						String discountOrgEntityUUId = UUIDUtils.getUUID();
						discountOrgEntity2.setId(discountOrgEntityUUId);
						discountOrgEntity2.settSrvPricingValuationId(valuationEntityUUId);
						discountOrgEntity2.setVersionNo(new Date().getTime());
						discountOrgEntity2.setActive(FossConstants.INACTIVE);
						discountOrgEntity2.setBeginTime(getNextBusinessDate());
						discountOrgEntity2.setEndTime(getMaxBusinessDate());
						discountOrgEntity2.setCreateDate(new Date());
						discountOrgEntity2.setCreateUser(getCurrentUserCode());
						discountOrgEntity2.setCreateOrgCode(getCurrentOrgCode());
						discountOrgEntity2.setModifyDate(new Date());
						discountOrgEntity2.setModifyUser(getCurrentUserCode());
						discountOrgEntity2.setModifyOrgCode(getCurrentOrgCode());
						discountOrgDao.insertSelective(discountOrgEntity2);
						//复制计价方式明细
						PriceCriteriaDetailEntity priceCriteriaDetailEntity = priceCriteriaDetailDao.selectByPrimaryKey(dto.getPriceCriteriaId());
						PriceCriteriaDetailEntity priceCriteriaDetailEntity2 = new PriceCriteriaDetailEntity();
						try {
							if(priceCriteriaDetailEntity != null){
								PropertyUtils.copyProperties(priceCriteriaDetailEntity2, priceCriteriaDetailEntity);
							}
						} catch (IllegalAccessException e) {
							log.info(e.getMessage(), e);
						} catch (InvocationTargetException e) {
							log.info(e.getMessage(), e);
						} catch (NoSuchMethodException e) {
							log.info(e.getMessage(), e);
						}
						//封装PriceCriteriaDetailEntity
						//进行数据库插入操作
						String criteriaDetailEntityUUId = UUIDUtils.getUUID();
						priceCriteriaDetailEntity2.setId(criteriaDetailEntityUUId);
						priceCriteriaDetailEntity2.setPricingValuationId(valuationEntityUUId);
						priceCriteriaDetailEntity2.setVersionNo(new Date().getTime());
						priceCriteriaDetailEntity2.setActive(FossConstants.INACTIVE);
						priceCriteriaDetailDao.copyOriginalSelective(priceCriteriaDetailEntity2);
					}
				}
			}
		}
		PriceDiscountVo vo = new PriceDiscountVo();
		vo.setMarketingEventEntity(eventEntity2);
		vo.setChannelEntityList(marketingEventChannelEntities);
		return vo;
	}
	   /**
		*删除，
		*
		*用户勾选一个或者多个数据记录之后可以点击删除按钮，
		*
		*系统弹出提示“确定删除所选记录?”,
		*
		*用户点击确定之后执行删除操作，
		*
		*删除完成后，
		*
		*查询界面自动刷新过滤掉已被删除的数据。
		*
		*参考SR2。
		*
		*SR1	
		*
		*根据页面所录入的方案名称与后台数据库校验是否
		*
		*已经存在、存在提示“该方案名称已经存在，不能再次使用”
		*
		*新建方案不能与某个已经存在的方案重名，
		*
		*如果是对于某个选定的方案进行版本升级操作的话，
		*
		*可以保持方案的名称不变，
		*
		*同时升级版本的方案有效期可以修改，
		*
		*结束的方案不能再启用;
		*
		*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
		*
		*最后3位数字自动增长
		*
		*如下格式：ZZFA20120323001;
		*
		*SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
		*
		*“未激活”状态的方案可以进行修改和删除操作。
		*
		*方案数据记录状态包括“未激活”、“激活”2个。
		*
		*“未激活”状态的方案可以勾选，进行激活操作，
		*
		*已经启用的方案可以进行勾选“中止”操作，
		*
		*这些状态的操作均不能回退。
	 */
	/**
	 * 
	 * <p>删除价格折扣方案明细</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-7 下午3:56:58
	 * 
	 * @param priceValuationId
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void deleteDiscountProgramItem(String priceValuationId) {
		if(priceValuationId != null && !"".equals(priceValuationId)) {
			PriceValuationEntity priceValuationEntity = priceValuationDao.selectByPrimaryKey(priceValuationId);
			if(priceValuationEntity != null && priceValuationId.equals(priceValuationEntity.getId())) {
				discountOrgDao.deleteByPriceValuationId(priceValuationEntity.getId());
				priceCriteriaDetailDao.deleteCriteriaDetailByValuationId(priceValuationEntity.getId());
				priceValuationDao.deleteByPrimaryKey(priceValuationId);
			}
		}
	}
	/**
	*SR1	
	*
	*根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	*
	*SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
	*
	*“未激活”状态的方案可以进行修改和删除操作。
	*
	*方案数据记录状态包括“未激活”、“激活”2个。
	*
	*“未激活”状态的方案可以勾选，进行激活操作，
	*
	*已经启用的方案可以进行勾选“中止”操作，
	*
	*这些状态的操作均不能回退。
 */
	/**
	 * 
	 * <p>修改价格折扣方案明细</p> 
	 * 
	 * @author sz
	 * 
	 * @date 2012-12-7 下午3:57:14
	 * 
	 * @param priceValuationId
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void updateDiscountProgramItem(PriceDiscountDto discountDto) {
		if(StringUtil.isNotBlank(discountDto.getPriceValuationId())) {
			PriceValuationEntity priceValuationEntity = priceValuationDao.selectByPrimaryKey(discountDto.getPriceValuationId());
			if(priceValuationEntity != null && StringUtil.equals(discountDto.getPriceValuationId(), priceValuationEntity.getId())) {
				PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
				priceDiscountDto.setPriceValuationId(discountDto.getPriceValuationId());
				List<PriceDiscountDto> list = valueAddDiscountDao.selectPriceDiscountByCodition(priceDiscountDto);
				if(CollectionUtils.isNotEmpty(list)) {
					MarketingEventEntity eventEntity = marketingEventDao.selectByPrimaryKey(priceValuationEntity.getMarketingEventId());
					PriceEntity priceEntity = null;
					if(StringUtil.isNotBlank(eventEntity.getPricingEntryCode())) {
						priceEntity = priceEntryDao.queryPriceEntryByCode(eventEntity.getPricingEntryCode());
						if(priceEntity != null) {
							//当为“其他”的情况
							if(priceEntity.getRefCode() != null && StringUtil.equals(priceEntity.getRefCode(), PriceEntityConstants.PRICING_CODE_QT)) {
								PriceEntity priceEntity2 = priceEntryDao.queryPriceEntryByCode(PriceEntityConstants.PRICING_CODE_QT);
								priceValuationEntity.setPricingEntryId(priceEntity2.getId());
								priceValuationEntity.setPricingEntryCode(priceEntity2.getCode());
								//当为“其他”之外的类型的情况
							} else {
								priceValuationEntity.setPricingEntryId(priceEntity.getId());
								priceValuationEntity.setPricingEntryCode(priceEntity.getCode());
							}
						} else {
							throw new PriceDiscountException("无法查询到相关计价条目信息", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
						}
					} else {
						throw new PriceDiscountException("计价条目不能为空", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
					}
					for (int i = 0; i < list.size(); i++) {
						PriceDiscountDto dto = list.get(i);
						PriceCriteriaDetailEntity priceCriteriaDetailEntity = priceCriteriaDetailDao.selectByPrimaryKey(dto.getPriceCriteriaId());
						if(priceCriteriaDetailEntity != null) {
							//封装priceCriteriaDetailEntity
							//进行数据库修改操作
							priceCriteriaDetailEntity.setCaculateType(discountDto.getCaculateType());
							priceCriteriaDetailEntity.setLeftrange(discountDto.getLeftRange());
							priceCriteriaDetailEntity.setRightrange(discountDto.getRightRange());
							priceCriteriaDetailEntity.setDiscountRate(discountDto.getDiscountRate());
							priceCriteriaDetailEntity.setMinFee(discountDto.getMinFee());
							priceCriteriaDetailEntity.setMaxFee(discountDto.getMaxFee());
							priceCriteriaDetailEntity.setModifyDate(new Date());
							priceCriteriaDetailEntity.setModifyUser(getCurrentUserCode());
							if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_QT, priceValuationEntity.getPricingEntryCode())) {
								priceCriteriaDetailEntity.setSubType(priceEntity.getCode());
							}
							priceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
						}
					}
					//封装priceValuationEntity
					//进行数据库修改操作
					priceValuationEntity.setGoodsTypeId(discountDto.getGoodsTypeId());
					priceValuationEntity.setGoodsTypeCode(discountDto.getGoodsTypeCode());
					priceValuationEntity.setProductId(discountDto.getProductId());
					priceValuationEntity.setProductCode(discountDto.getProductCode());
					priceValuationEntity.setPricingIndustryId(discountDto.getPricingIndustryId());
					priceValuationEntity.setPricingIndustryCode(discountDto.getPricingIndustryCode());
					priceValuationEntity.setModifyUser(getCurrentUserCode());
					priceValuationEntity.setModifyOrgCode(getCurrentOrgCode());
					priceValuationDao.updateValuation(priceValuationEntity);
				}
			}
		}
	}
	/**
	 *SR3	折扣的计算。
	*
	*折扣的计算按优先级类型分为三大类：客户合同、渠道、产品。
	*
	*该优先级的级别顺序维护在ＰＫＰ．T_SRV_DISCOUNT_PRIORITY表中，
	*
	*根据字段值LEVELS来排序，数值越小则优先级越高。在折扣的实际计算时，
	*
	*根据优先级依次来判断可以符合适用的折扣 计算类型，如果有满足的计算类型，
	*
	*则不再使用后面的优先级类别来计算。
	*
	*a)	客户合同
	*
	*根据传入的客户编号查找相对应的客户合同的折扣率，如果查询到相应信息，
	*
	*则按此的折扣率进行价格计算。
	*
	*b)	渠道
	*
	*根据传入的渠道编号查找相对应的折扣率。查找的规则是，
	*
	*根据渠道编码以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
	*
	*然后按照产品、货物类型和所属行业依次进行匹配，即首先匹配产品相对应，
	*
	*在产品相对应的情况 下货物类型相应，然后是所属行业相对应，
	*
	*如果依据上述三个原则对应上的情况下则匹配到折扣条目，
	*
	*可以利用折扣条目所对应的数据进行折扣计算，
	*
	*如果存在多条匹配的记录，选取折扣最低的进行计算。
	*
	*计算出减免的费用需要大于等于该折扣设定的最低一票并且小于等于设定的最高一票。
	*
	*否则，最低减免按最低一票减免，最高减免按最高一票减免。
	*
	*c)	产品
	*
	*折扣条目的匹配原则，
	*
	*根据普通产品渠道以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
	*
	*然后按照产品、货物类型和所属行业依次进行匹配，
	*
	*即首先匹配产品相对应，
	*
	*在产品相对应的情况 下货物类型相应，
	*
	*然后是所属行业相对应，
	*
	*如果依据上述三个原则对应上的情况下则匹配到折扣条目，
	*
	*可以利用折扣条目所对应的数据进行折扣计算，
	*
	*如果存在多条匹配的记录，
	*
	*选取折扣最低的进行计算。
	*
	*计算出减免的费用需要大于该折扣设定的最低一票并且小于设定的最高一票。
	*
	*否则，最低减免按最低一票减免，
	*
	*最高减免按最高一票减免。
	 */
	/**
	 * 
	 * @Description: 检查条件交集
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午3:00:29
	 * 
	 * @param priceDiscountDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private void checkProgramIntersection (PriceDiscountDto priceDiscountDto) {
		PriceDiscountDto selectDto = new PriceDiscountDto();
		//封装PriceDiscountDto
		selectDto.setActive(FossConstants.ACTIVE);
		selectDto.setSaleChannelCode(priceDiscountDto.getSaleChannelCode());
		selectDto.setDeptOrgId(priceDiscountDto.getDeptOrgId());
		selectDto.setDeptOrgTypeCode(priceDiscountDto.getDeptOrgTypeCode());
		selectDto.setArrvOrgId(priceDiscountDto.getArrvOrgId());
		selectDto.setArrvOrgTypeCode(priceDiscountDto.getArrvOrgTypeCode());
		selectDto.setCaculateType(priceDiscountDto.getCaculateType());
		selectDto.setProductId(priceDiscountDto.getProductId());
		selectDto.setGoodsTypeId(priceDiscountDto.getGoodsTypeId());
		selectDto.setPriceEntryCode(priceDiscountDto.getPriceEntryCode());
		//根据设置的输入参数查询是否存在重合的数据
		List<PriceDiscountDto> priceDiscountDtos = valueAddDiscountDao.selectPriceDiscountAllByCodition(selectDto);
		if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
			 for (int i = 0; i < priceDiscountDtos.size(); i++) {
				 PriceDiscountDto checkDto = priceDiscountDtos.get(i);
				 if(checkDto.getPriceValuationId() == null 
						 || priceDiscountDto.getPriceValuationId() == null 
						 || priceDiscountDto.getPriceValuationId().equals(checkDto.getPriceValuationId())
						 || StringUtils.equals(checkDto.getMarketCode(), priceDiscountDto.getMarketCode())) {
					 continue;
				 } else {
					 //判断时间范围是否有重合
					 boolean flag1 = this.checkDateInteval(priceDiscountDto, checkDto);
					 //判断适用范围是否有重合
					 boolean flag2 = this.checkRangeInteval(priceDiscountDto, checkDto);
					 //二者都重合的情况视为有冲突，不能激活
					 if(flag1 && flag2) {
						throw new PriceDiscountException("该方案不能激活,与"+filterStr(checkDto.getMarketName())+"存在重合的信息", PriceDiscountException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE);
					 } 
				 }
			}
		} 
	}
	
	private String filterStr(String str) {
		String result = str;
		result = result.replace("\"", "");
		return result;
	}
	/**
	 *SR3	折扣的计算。
	*
	*折扣的计算按优先级类型分为三大类：客户合同、渠道、产品。
	*
	*该优先级的级别顺序维护在ＰＫＰ．T_SRV_DISCOUNT_PRIORITY表中，
	*
	*根据字段值LEVELS来排序，数值越小则优先级越高。在折扣的实际计算时，
	*
	*根据优先级依次来判断可以符合适用的折扣 计算类型，如果有满足的计算类型，
	*
	*则不再使用后面的优先级类别来计算。
	*
	*a)	客户合同
	*
	*根据传入的客户编号查找相对应的客户合同的折扣率，如果查询到相应信息，
	*
	*则按此的折扣率进行价格计算。
	*
	*b)	渠道
	*
	*根据传入的渠道编号查找相对应的折扣率。查找的规则是，
	*
	*根据渠道编码以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
	*
	*然后按照产品、货物类型和所属行业依次进行匹配，即首先匹配产品相对应，
	*
	*在产品相对应的情况 下货物类型相应，然后是所属行业相对应，
	*
	*如果依据上述三个原则对应上的情况下则匹配到折扣条目，
	*
	*可以利用折扣条目所对应的数据进行折扣计算，
	*
	*如果存在多条匹配的记录，选取折扣最低的进行计算。
	*
	*计算出减免的费用需要大于等于该折扣设定的最低一票并且小于等于设定的最高一票。
	*
	*否则，最低减免按最低一票减免，最高减免按最高一票减免。
	*
	*c)	产品
	*
	*折扣条目的匹配原则，
	*
	*根据普通产品渠道以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
	*
	*然后按照产品、货物类型和所属行业依次进行匹配，
	*
	*即首先匹配产品相对应，
	*
	*在产品相对应的情况 下货物类型相应，
	*
	*然后是所属行业相对应，
	*
	*如果依据上述三个原则对应上的情况下则匹配到折扣条目，
	*
	*可以利用折扣条目所对应的数据进行折扣计算，
	*
	*如果存在多条匹配的记录，
	*
	*选取折扣最低的进行计算。
	*
	*计算出减免的费用需要大于该折扣设定的最低一票并且小于设定的最高一票。
	*
	*否则，最低减免按最低一票减免，
	*
	*最高减免按最高一票减免。
	 */
	/**
	 * 
	 * @Description: 检查适用范围交集
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午3:00:29
	 * 
	 * @param priceDiscountDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private boolean checkRangeInteval(PriceDiscountDto dto, PriceDiscountDto checkDto) {
		boolean flag = false;
		if(checkDto.getRightRange().doubleValue() > dto.getLeftRange().doubleValue()) {
			flag = true;
		}
		if(checkDto.getLeftRange().doubleValue() < dto.getRightRange().doubleValue()) {
			flag = true;
		}
		return flag;
	}
	/**
	 *SR3	折扣的计算。
	*
	*折扣的计算按优先级类型分为三大类：客户合同、渠道、产品。
	*
	*该优先级的级别顺序维护在ＰＫＰ．T_SRV_DISCOUNT_PRIORITY表中，
	*
	*根据字段值LEVELS来排序，数值越小则优先级越高。在折扣的实际计算时，
	*
	*根据优先级依次来判断可以符合适用的折扣 计算类型，如果有满足的计算类型，
	*
	*则不再使用后面的优先级类别来计算。
	*
	*a)	客户合同
	*
	*根据传入的客户编号查找相对应的客户合同的折扣率，如果查询到相应信息，
	*
	*则按此的折扣率进行价格计算。
	*
	*b)	渠道
	*
	*根据传入的渠道编号查找相对应的折扣率。查找的规则是，
	*
	*根据渠道编码以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
	*
	*然后按照产品、货物类型和所属行业依次进行匹配，即首先匹配产品相对应，
	*
	*在产品相对应的情况 下货物类型相应，然后是所属行业相对应，
	*
	*如果依据上述三个原则对应上的情况下则匹配到折扣条目，
	*
	*可以利用折扣条目所对应的数据进行折扣计算，
	*
	*如果存在多条匹配的记录，选取折扣最低的进行计算。
	*
	*计算出减免的费用需要大于等于该折扣设定的最低一票并且小于等于设定的最高一票。
	*
	*否则，最低减免按最低一票减免，最高减免按最高一票减免。
	*
	*c)	产品
	*
	*折扣条目的匹配原则，
	*
	*根据普通产品渠道以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
	*
	*然后按照产品、货物类型和所属行业依次进行匹配，
	*
	*即首先匹配产品相对应，
	*
	*在产品相对应的情况 下货物类型相应，
	*
	*然后是所属行业相对应，
	*
	*如果依据上述三个原则对应上的情况下则匹配到折扣条目，
	*
	*可以利用折扣条目所对应的数据进行折扣计算，
	*
	*如果存在多条匹配的记录，
	*
	*选取折扣最低的进行计算。
	*
	*计算出减免的费用需要大于该折扣设定的最低一票并且小于设定的最高一票。
	*
	*否则，最低减免按最低一票减免，
	*
	*最高减免按最高一票减免。
	 */
	/**
	 * @Description: 检查有效期范围交集
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午3:00:29
	 * 
	 * @param priceDiscountDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private boolean checkDateInteval(PriceDiscountDto dto, PriceDiscountDto checkDto) {
		boolean flag = false;
		if(checkDto.getEndDate().getTime() > dto.getBeginDate().getTime()) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 
	 * @Description: 根据计费规则主键查询折扣明细
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-19 下午8:47:06
	 * 
	 * @param priceValuationId
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public PriceDiscountDto selectPriceDiscountItemByValuationId(
			String priceValuationId) {
		return valueAddDiscountDao.selectPriceDiscountItemByValuationId(priceValuationId);
	}
	/**
	 * 
	 * @Description: 根据条件计算渠道折扣价格
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-19 下午3:08:59
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public List<PriceDiscountDto> calculateChannelDiscount(DiscountParmDto discountParmDto) {
		return valueAddDiscountDao.calculateChannelDiscountByCodition(discountParmDto);
	}
	/**
	 * @Description: 根据条件计算产品折扣价格
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-19 下午3:08:59
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public List<PriceDiscountDto> calculateProductDiscount(DiscountParmDto discountParmDto) {
		return valueAddDiscountDao.calculateProductDiscountByCodition(discountParmDto);
	}
	/**
	 *
	*SR1	根据页面所录入的方案名称与后台数据库校验是否
	*
	*已经存在、存在提示“该方案名称已经存在，不能再次使用”
	*
	*新建方案不能与某个已经存在的方案重名，
	*
	*如果是对于某个选定的方案进行版本升级操作的话，
	*
	*可以保持方案的名称不变，
	*
	*同时升级版本的方案有效期可以修改，
	*
	*结束的方案不能再启用;
	*
	*编号生成规则为: ZZFA +8位日期+3位数字实别 , 
	*
	*最后3位数字自动增长
	*
	*如下格式：ZZFA20120323001;
	 */
	/**
	 * 
	 * @Description: 自动生成优惠项CODE
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-17 下午4:21:00
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private String generateProgramCode() {
		String marketCode = marketingEventDao.getMarketingEventMaxCode(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
		String code = null;
		//根据当前日期生成日期字符串
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH)+1;
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);
		String currentYearStr =  String.valueOf(currentYear);
		String currentMonthStr = String.format("%02d", currentMonth);
		String currentDayStr = String.format("%02d", currentDay);
		//截取相应字符串
		if(StringUtil.isNotBlank(marketCode)) {
			String year = marketCode.substring(NumberConstants.NUMBER_4, NumberConstants.NUMBER_8);
			String month = marketCode.substring(NumberConstants.NUMBER_8, NumberConstants.NUMBER_10);
			String day = marketCode.substring(NumberConstants.NUMBER_10, NumberConstants.NUMBER_12);
			String seq = marketCode.substring(NumberConstants.NUMBER_12, NumberConstants.NUMBER_15);
			//组装成新的CODE
			if(StringUtil.equals(year, currentYearStr) && StringUtil.equals(month, currentMonthStr) && StringUtil.equals(day, currentDayStr)) {
				code = "ZZFA" + year + month + day;
				int num = Integer.parseInt(seq);
				num = num + 1;
				code = code + String.format("%03d", num);
			} else {
				code = "ZZFA" + currentYearStr + currentMonthStr + currentDayStr + "001" ;
			}
		} else {
			code = "ZZFA" + currentYearStr + currentMonthStr + currentDayStr + "001" ;
		}
		return code;
	}
	/**
	 * 
	 * @Description: 获取有效期最大值
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:01
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private Date getMaxBusinessDate() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(NUMBER_2999, NumberConstants.NUMBER_11, NumberConstants.NUMBER_31);
    	Date maxDate = cal.getTime();
    	return maxDate;
    }
	/**
	 * 
	 * @Description: 获取有效期起启值
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:32
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private Date getNextBusinessDate() {
    	return new Date();
    }
	/**
	 * 
	 * @Description: 获得当前部门值
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
	/**
	 * 
	 * @Description: 获得当前人
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		return currentUser.getEmployee().getEmpCode();
	}
	/**
	 * 
	 * @Description: 封装方案集合中的名称数据
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-27 下午2:36:48
	 * 
	 * @param priceDiscountDtos
	 * 
	 * @version V1.0
	 * 
	 */
	private List<MarketingEventEntity>  boxCatalogNameForMarketing(List<MarketingEventEntity> marketingEventEntities) {
		List<MarketingEventEntity> list = null;
		if(CollectionUtils.isNotEmpty(marketingEventEntities)) {
			Map<String, String> entryMap = getUtilPricingEntryNames(marketingEventEntities);
			list = new ArrayList<MarketingEventEntity>();
			for (int i = 0; i < marketingEventEntities.size(); i++) {
				MarketingEventEntity marketingEventEntity = marketingEventEntities.get(i);
				//封装计价条目
				if(StringUtil.isNotBlank(marketingEventEntity.getPricingEntryCode())) {
					String entryName = entryMap.get(marketingEventEntity.getPricingEntryCode());
					if(StringUtil.isNotBlank(entryName)) {
						marketingEventEntity.setPricingEntryName(entryName);
					}
				}
				list.add(marketingEventEntity);
			}
		}
		return list;
	}
	
	/**
	 * 根据empCode查询empName
	 * @param list
	 */
	private void boxStaffNameForMarketing(List<MarketingEventEntity> list){
		if(CollectionUtils.isNotEmpty(list)){
			String[] codeArray = new String[list.size()];
			for(int i = 0; i< list.size(); i++){
				MarketingEventEntity entity = list.get(i);
				if(entity.getCreateUser() != null){
					codeArray[i] = entity.getCreateUser();
				}
			}
			List<EmployeeEntity> employList = employeeService.queryEmployeeBatchByEmpCode(codeArray);
			Map<String, String> codeAndName = new HashMap<String, String>();
			if(CollectionUtils.isNotEmpty(employList)){
				for(EmployeeEntity employ : employList){
					codeAndName.put(employ.getEmpCode(), employ.getEmpName());
				}
			}
			for(MarketingEventEntity entity : list){
				if(entity.getCreateUser() != null){
					entity.setCreateUserName(codeAndName.get(entity.getCreateUser()));
				}
			}
			
		}
	}
	
	/**
	 * 
	 * @Description: 封装方案集合中的名称数据
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-27 下午2:36:48
	 * 
	 * @param priceDiscountDtos
	 * 
	 * @version V1.0
	 * 
	 */
	private MarketingEventEntity  boxCatalogNameForMarketing(MarketingEventEntity marketingEventEntity) {
		MarketingEventEntity entity = marketingEventEntity;
		if(entity != null) {
			List<MarketingEventEntity> marketingEventEntities = new ArrayList<MarketingEventEntity>();
			marketingEventEntities.add(entity);
			Map<String, String> entryMap = getUtilPricingEntryNames(marketingEventEntities);
			//封装计价条目
			if(StringUtil.isNotBlank(entity.getPricingEntryCode())) {
				String entryName = entryMap.get(entity.getPricingEntryCode());
				if(StringUtil.isNotBlank(entryName)) {
					entity.setPricingEntryName(entryName);
				}
			}
		}
		return entity;
	}
	/**
	 * 
	 * @Description: 封装集合中的名称数据
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-27 下午2:36:48
	 * 
	 * @param priceDiscountDtos
	 * 
	 * @version V1.0
	 */
	private List<PriceDiscountDto>  boxCatalogName(List<PriceDiscountDto> priceDiscountDtos) {
		List<PriceDiscountDto> list = null;
		if(CollectionUtils.isNotEmpty(priceDiscountDtos)) {
			list = new ArrayList<PriceDiscountDto>();
			Map<String, String> userNames = getUtilUserNames(priceDiscountDtos);
			Map<String, String> channelNames = getUtilChannelNames(priceDiscountDtos);
			Map<String, String> productNames = getUtilProductNames(priceDiscountDtos); 
			Map<String, String> goodsTypeNames = getUtilGoodsTypeNames(priceDiscountDtos);
			Map<String, String> regionNames = getUtilRegionNames(priceDiscountDtos);
			Map<String, String> cityNames = getUtilCityNames(priceDiscountDtos);
			Map<String, String> deptNames = getUtilDeptNames(priceDiscountDtos);
			long yb = NUMBER_100;
			for (int i = 0; i < priceDiscountDtos.size(); i++) {
				PriceDiscountDto priceDiscountDto = priceDiscountDtos.get(i);
				//分转元
				if(priceDiscountDto.getMinFee() != 0) {
					priceDiscountDto.setMinFee(priceDiscountDto.getMinFee()/yb);
				}
				if(priceDiscountDto.getMaxFee() != 0) {
					priceDiscountDto.setMaxFee(priceDiscountDto.getMaxFee()/yb);
				}
				//封装渠道NAME
				if(channelNames != null && channelNames.size() > 0) {
					if(StringUtil.isNotBlank(priceDiscountDto.getSaleChannelCode())) {
						String channelName = channelNames.get(priceDiscountDto.getSaleChannelCode());
						if(StringUtil.isNotBlank(channelName)) {
							priceDiscountDto.setSaleChannelName(channelName);
						}
					}
				}
				//封装姓名NAME
				if(userNames != null && userNames.size() > 0) {
					if(StringUtil.isNotBlank(priceDiscountDto.getCreateUserCode())) {
						String userName = userNames.get(priceDiscountDto.getCreateUserCode());
						if(StringUtil.isNotBlank(userName)) {
							priceDiscountDto.setCreateUserName(userName);
						}
					}
				}
				//封装产品NAME
				if(productNames != null && productNames.size() > 0) {
					if(StringUtil.isNotBlank(priceDiscountDto.getProductCode())) {
						String productName = productNames.get(priceDiscountDto.getProductCode());
						if(StringUtil.isNotBlank(productName)) {
							priceDiscountDto.setProductName(productName);
						}
					}
				}
				//封装货物类型NAME
				if(goodsTypeNames != null && goodsTypeNames.size() > 0) {
					if(StringUtil.isNotBlank(priceDiscountDto.getGoodsTypeCode())) {
						String goodsTypeName = goodsTypeNames.get(priceDiscountDto.getGoodsTypeCode());
						if(StringUtil.isNotBlank(goodsTypeName)) {
							priceDiscountDto.setGoogsTypeName(goodsTypeName);
						}
					}
				}
				//封装出发地点名称
				if(StringUtil.isNotBlank(priceDiscountDto.getDeptOrgTypeCode())) {
					if(StringUtil.equals(priceDiscountDto.getDeptOrgTypeCode(), DiscountOrgConstants.DISCOUNT_ORG_DEPT)) {
						priceDiscountDto.setDeptOrgName(deptNames.get(priceDiscountDto.getDeptOrgCode()));
					} else if(StringUtil.equals(priceDiscountDto.getDeptOrgTypeCode(), DiscountOrgConstants.DISCOUNT_ORG_CITY)) {
						priceDiscountDto.setDeptOrgName(cityNames.get(priceDiscountDto.getDeptOrgCode()));
					} else  if(StringUtil.equals(priceDiscountDto.getDeptOrgTypeCode(), DiscountOrgConstants.DISCOUNT_ORG_REGION)) {
						priceDiscountDto.setDeptOrgName(regionNames.get(priceDiscountDto.getDeptOrgCode()));
					} else if(StringUtil.equals(priceDiscountDto.getDeptOrgTypeCode(), DictionaryValueConstants.MSG_TYPE__ALLNET)) {
						priceDiscountDto.setDeptOrgName("全网");
					}
				}
				//封装到达地点名称
				if(StringUtil.isNotBlank(priceDiscountDto.getArrvOrgTypeCode())) {
					if(StringUtil.equals(priceDiscountDto.getArrvOrgTypeCode(), DiscountOrgConstants.DISCOUNT_ORG_DEPT)) {
						priceDiscountDto.setArrvOrgName(deptNames.get(priceDiscountDto.getArrvOrgCode()));
					} else if(StringUtil.equals(priceDiscountDto.getArrvOrgTypeCode(), DiscountOrgConstants.DISCOUNT_ORG_CITY)) {
						priceDiscountDto.setArrvOrgName(cityNames.get(priceDiscountDto.getArrvOrgCode()));
					} else  if(StringUtil.equals(priceDiscountDto.getArrvOrgTypeCode(), DiscountOrgConstants.DISCOUNT_ORG_REGION)) {
						priceDiscountDto.setArrvOrgName(regionNames.get(priceDiscountDto.getArrvOrgCode()));
					} else  if(StringUtil.equals(priceDiscountDto.getArrvOrgTypeCode(), DictionaryValueConstants.MSG_TYPE__ALLNET)) {
						priceDiscountDto.setArrvOrgName("全网");
					}
				}
				//组成LIST集合
				list.add(priceDiscountDto);
			}
		}
		return list;
	}
	/**
	 * 
	 * @Description: 根据集合中的用户CODE查询用户NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-28 上午9:48:26
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String> getUtilUserNames(List<PriceDiscountDto> list) {
		String[] userCodes = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			PriceDiscountDto dto = list.get(i);
			if(dto != null && dto.getCreateUserCode() != null) {
				userCodes[i] = dto.getCreateUserCode();
			}
		}
		//根据CODe查询相应实体类
		Map<String, String> employeeMap = null;
		if(userCodes != null && userCodes.length > 0) {
			List<EmployeeEntity> employeeEntities = employeeService.queryEmployeeBatchByEmpCode(userCodes);
			employeeMap = new HashMap<String, String>();
			if(CollectionUtils.isNotEmpty(employeeEntities)) {
				for (int i = 0; i < employeeEntities.size(); i++) {
					EmployeeEntity employeeEntity = employeeEntities.get(i);
					//封装名称
					employeeMap.put(employeeEntity.getEmpCode(), employeeEntity.getEmpName());
				}
			}
		}
		return employeeMap;
	}
	/**
	 * 
	 * @Description: 根据集合中的渠道CODE查询渠道NAME
	 * 
	 * Company:IBM
	 * 
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-28 上午9:48:26
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String> getUtilChannelNames(List<PriceDiscountDto> list) {
		Map<String, String> channelMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			PriceDiscountDto priceDiscountDto = list.get(i);
			if(StringUtil.isNotBlank(priceDiscountDto.getSaleChannelCode())) {
				DataDictionaryValueEntity dictionaryValueEntityForChannel = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.PKP_PRICE_CHANNAL, priceDiscountDto.getSaleChannelCode());
				if(dictionaryValueEntityForChannel != null && dictionaryValueEntityForChannel.getValueName() != null) {
					channelMap.put(dictionaryValueEntityForChannel.getValueCode(), dictionaryValueEntityForChannel.getValueName());
				} 
			}
		}
		return channelMap;
	}
	/**
	 * 
	 * @Description: 根据集合中的计价条目CODE查询计价条目NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-28 上午9:48:26
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String> getUtilPricingEntryNames(List<MarketingEventEntity> marketingEventEntities) {
		//价格条目NAME获取
		List<String> entryCodes = new ArrayList<String>();
		for (int i = 0; i < marketingEventEntities.size(); i++) {
			String entryCode = marketingEventEntities.get(i).getPricingEntryCode();
			if(StringUtil.isNotBlank(entryCode)) {
				entryCodes.add(entryCode);
			}
		}
		Map<String, String> entryMap = null;
		if(CollectionUtils.isNotEmpty(entryCodes)) {
			List<PriceEntity> priceEntities = priceEntryDao.queryPriceEntryNameByEntryCodes(entryCodes);
			entryMap = new HashMap<String, String>();
			if(CollectionUtils.isNotEmpty(priceEntities)) {
				for (int i = 0; i < priceEntities.size(); i++) {
					PriceEntity priceEntity = priceEntities.get(i);
					entryMap.put(priceEntity.getCode(), priceEntity.getName());
				}
			}
		}
		return entryMap;
	}
	/**
	 * 
	 * @Description: 根据集合中的产品CODE查询产品NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-28 上午9:48:26
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String> getUtilProductNames(List<PriceDiscountDto> list) {
		Map<String, String> productMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			String productCode = list.get(i).getProductCode();
			if(StringUtil.isNotBlank(productCode)) {
				if(StringUtils.equals("ALL", productCode)) {
					productMap.put(productCode, "全部");
					continue;
				}
				ProductEntity productEntity = productService.getProductByCache(productCode, new Date());
				if(productEntity != null && productEntity.getName() != null) {
					productMap.put(productEntity.getCode(), productEntity.getName());
				}
			}
		}
		return productMap;
	}
	/**
	 * 
	 * @Description: 根据集合中的货物类型CODE查询货物类型NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-28 上午9:48:26
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String> getUtilGoodsTypeNames(List<PriceDiscountDto> list) {
		Map<String, String> goodsTypeMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			String goodsTypeCode = list.get(i).getGoodsTypeCode();
			if(StringUtil.isNotBlank(goodsTypeCode)) {
				GoodsTypeEntity goodsTypeEntity = goodsTypeDao.queryGoodsTypeByCache(goodsTypeCode, new Date());
				if(goodsTypeEntity != null && goodsTypeEntity.getName() != null) {
					goodsTypeMap.put(goodsTypeEntity.getCode(), goodsTypeEntity.getName());
				}
			}
		}
		return goodsTypeMap;
	}
	/**
	 * 
	 * @Description: 根据集合中的区域CODE查询区域NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-8 下午8:24:39
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String>  getUtilRegionNames(List<PriceDiscountDto> list) {
		List<String> regionList = new ArrayList<String>();
		Map<String, String> regionMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			PriceDiscountDto priceDiscountDto = list.get(i);
			String deptOrgTypeCode = priceDiscountDto.getDeptOrgTypeCode();
			String arrvOrgTypeCode = priceDiscountDto.getArrvOrgTypeCode();
			//将始发区域组成集合
			if(StringUtil.equals(deptOrgTypeCode, DiscountOrgConstants.DISCOUNT_ORG_REGION)) {
				if(!regionList.contains(priceDiscountDto.getDeptOrgCode())) {
					regionList.add(priceDiscountDto.getDeptOrgCode());
				}
			}
			//将目的区域组成集合
			if(StringUtil.equals(arrvOrgTypeCode, DiscountOrgConstants.DISCOUNT_ORG_REGION)) {
				if(!regionList.contains(priceDiscountDto.getArrvOrgCode())) {
					regionList.add(priceDiscountDto.getArrvOrgCode());
				}
			}
		}
		//根据CODE查询实体类，并封装名称
		if(CollectionUtils.isNotEmpty(regionList)) {
			List<PriceRegionEntity> regionEntities = regionDao.findRegionByCodes(regionList, PricingConstants.PRICING_REGION, new Date());
			if(CollectionUtils.isNotEmpty(regionEntities)) {
				for (int i = 0; i < regionEntities.size(); i++) {
					PriceRegionEntity priceRegionEntity = regionEntities.get(i);
					regionMap.put(priceRegionEntity.getRegionCode(), priceRegionEntity.getRegionName());
				}
			}
		}
		return regionMap;
	}
	/**
	 * 
	 * @Description: 根据集合中的城市CODE查询城市NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-8 下午8:24:39
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String>  getUtilCityNames(List<PriceDiscountDto> list) {
		List<String> cityList = new ArrayList<String>();
		Map<String, String> cityMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			PriceDiscountDto priceDiscountDto = list.get(i);
			String deptOrgTypeCode = priceDiscountDto.getDeptOrgTypeCode();
			String arrvOrgTypeCode = priceDiscountDto.getArrvOrgTypeCode();
			//将始发城市组成集合
			if(StringUtil.equals(deptOrgTypeCode, DiscountOrgConstants.DISCOUNT_ORG_CITY)) {
				if(!cityList.contains(priceDiscountDto.getDeptOrgCode())) {
					cityList.add(priceDiscountDto.getDeptOrgCode());
				}
			}
			//将目的城市组成集合
			if(StringUtil.equals(arrvOrgTypeCode, DiscountOrgConstants.DISCOUNT_ORG_CITY)) {
				if(!cityList.contains(priceDiscountDto.getArrvOrgCode())) {
					cityList.add(priceDiscountDto.getArrvOrgCode());
				}
			}
		}
		//根据CODE查询实体类，并封装名称
		if(CollectionUtils.isNotEmpty(cityList)) {
			for (int i = 0; i < cityList.size(); i++) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionCacheDeal.searchRegionByCode(cityList.get(i));
				if(administrativeRegionsEntity != null && StringUtil.isNotBlank(administrativeRegionsEntity.getCode())) {
					cityMap.put(administrativeRegionsEntity.getCode(), administrativeRegionsEntity.getName());
				}
			}
		}
		return cityMap;
	} 
	/**
	 * 
	 * @Description: 获取部门名称
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-17 下午4:28:53
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String>  getUtilDeptNames(List<PriceDiscountDto> list) {
		List<String> deptList = new ArrayList<String>();
		Map<String, String> deptMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			PriceDiscountDto priceDiscountDto = list.get(i);
			String deptOrgTypeCode = priceDiscountDto.getDeptOrgTypeCode();
			String arrvOrgTypeCode = priceDiscountDto.getArrvOrgTypeCode();
			//将始发部门组成集合
			if(StringUtil.equals(deptOrgTypeCode, DiscountOrgConstants.DISCOUNT_ORG_DEPT)) {
				if(!deptList.contains(priceDiscountDto.getDeptOrgCode())) {
					deptList.add(priceDiscountDto.getDeptOrgCode());
				}
			}
			//将目的部门组成集合
			if(StringUtil.equals(arrvOrgTypeCode, DiscountOrgConstants.DISCOUNT_ORG_DEPT)) {
				if(!deptList.contains(priceDiscountDto.getArrvOrgCode())) {
					deptList.add(priceDiscountDto.getArrvOrgCode());
				}
			}
		}
		//根据CODE查询实体类，并封装名称
		if(CollectionUtils.isNotEmpty(deptList)) {
			List<CommonOrgEntity> commonOrgEntities = pricingOrgDao.queryBatchOrgByCondition(deptList, null, FossConstants.ACTIVE, "ORG");
			if(CollectionUtils.isNotEmpty(commonOrgEntities)) {
				for (int i = 0; i < commonOrgEntities.size(); i++) {
					CommonOrgEntity commonOrgEntity = commonOrgEntities.get(i);
					deptMap.put(commonOrgEntity.getCode(), commonOrgEntity.getName());
				}
			}
		}
		return deptMap;
	}
	/**
	 * 获取 折扣方案（市场活动）操作DAO.
	 *
	 * @return the 折扣方案（市场活动）操作DAO
	 */
	public IMarketingEventDAO getMarketingEventDao() {
		return marketingEventDao;
	}
	/**
	 * 设置 折扣方案（市场活动）操作DAO.
	 *
	 * @param marketingEventDao the new 折扣方案（市场活动）操作DAO
	 */
	public void setMarketingEventDao(IMarketingEventDAO marketingEventDao) {
		this.marketingEventDao = marketingEventDao;
	}
	/**
	 * 获取 折扣方案渠道（市场活动渠道）操作DAO.
	 *
	 * @return the 折扣方案渠道（市场活动渠道）操作DAO
	 */
	public IMarketingEventChannelDao getMarketingEventChannelDao() {
		return marketingEventChannelDao;
	}
	/**
	 * 设置 折扣方案渠道（市场活动渠道）操作DAO.
	 *
	 * @param marketingEventChannelDao the new 折扣方案渠道（市场活动渠道）操作DAO
	 */
	public void setMarketingEventChannelDao(
			IMarketingEventChannelDao marketingEventChannelDao) {
		this.marketingEventChannelDao = marketingEventChannelDao;
	}
	/**
	 * 获取 折扣适用网点操作DAO.
	 *
	 * @return the 折扣适用网点操作DAO
	 */
	public IDiscountOrgDao getDiscountOrgDao() {
		return discountOrgDao;
	}
	/**
	 * 设置 折扣适用网点操作DAO.
	 *
	 * @param discountOrgDao the new 折扣适用网点操作DAO
	 */
	public void setDiscountOrgDao(IDiscountOrgDao discountOrgDao) {
		this.discountOrgDao = discountOrgDao;
	}
	/**
	 * 获取 计费规则操作DAO.
	 *
	 * @return the 计费规则操作DAO
	 */
	public IPriceValuationDao getPriceValuationDao() {
		return priceValuationDao;
	}
	/**
	 * 设置 计费规则操作DAO.
	 *
	 * @param priceValuationDao the new 计费规则操作DAO
	 */
	public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
		this.priceValuationDao = priceValuationDao;
	}
	/**
	 * 获取 计价明细操作DAO.
	 *
	 * @return the 计价明细操作DAO
	 */
	public IPriceCriteriaDetailDao getPriceCriteriaDetailDao() {
		return priceCriteriaDetailDao;
	}
	/**
	 * 设置 计价明细操作DAO.
	 *
	 * @param priceCriteriaDetailDao the new 计价明细操作DAO
	 */
	public void setPriceCriteriaDetailDao(
			IPriceCriteriaDetailDao priceCriteriaDetailDao) {
		this.priceCriteriaDetailDao = priceCriteriaDetailDao;
	}
	/**
	 * 设置 员工操作SERVICE.
	 *
	 * @param employeeService the new 员工操作SERVICE
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * 设置 增值优惠操作DAO.
	 *
	 * @param valueAddDiscountDao the new 增值优惠操作DAO
	 */
	public void setValueAddDiscountDao(IValueAddDiscountDao valueAddDiscountDao) {
		this.valueAddDiscountDao = valueAddDiscountDao;
	}
	/**
	 * 设置 计价条目DAO.
	 *
	 * @param priceEntryDao the new 计价条目DAO
	 */
	public void setPriceEntryDao(IPriceEntryDao priceEntryDao) {
		this.priceEntryDao = priceEntryDao;
	}
	/**
	 * 设置 数据字典SERVICE.
	 *
	 * @param dataDictionaryValueService the new 数据字典SERVICE
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	 * 设置 产品SERVICE.
	 *
	 * @param productService the new 产品SERVICE
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * 设置 货物类型DAO.
	 *
	 * @param goodsTypeDao the new 货物类型DAO
	 */
	public void setGoodsTypeDao(IGoodsTypeDao goodsTypeDao) {
		this.goodsTypeDao = goodsTypeDao;
	}
	/**
	 * 设置 价格区域DAO.
	 *
	 * @param regionDao the new 价格区域DAO
	 */
	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}
	/**
	 * 设置 部门DAO.
	 *
	 * @param pricingOrgDao the new 部门DAO
	 */
	public void setPricingOrgDao(IPricingOrgDao pricingOrgDao) {
		this.pricingOrgDao = pricingOrgDao;
	}
}






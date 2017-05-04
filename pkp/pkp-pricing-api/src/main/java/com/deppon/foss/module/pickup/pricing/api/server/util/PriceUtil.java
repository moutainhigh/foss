/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *   initial comments.
 *   
 *   
 * 一、运费折扣计算
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
 *用户通过菜单查询优惠活动列表点击进入此界面，
 *
 *列表要求显示
 *
 *1.	查询条件区域：
 *
 *折扣方案，
 *
 *折扣状态，
 *
 *业务日期。
 *
 *a)	折扣方案：
 *
 *输入要查询的折扣方案名称
 *
 *b)	折扣状态：
 *
 *折扣方案的状态（新建，启用）。
 *
 *c)	业务日期：
 *
 *选择业务日期过滤折扣方案查询列表
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
 *勾选中的记录可以进行后面
 *
 *的删除，激活，中止的批量操作功能。
 *
 *c)	功能按钮：
 *
 *新增，
 *
 *点击此按钮打开“图2-折扣方案新增”的界面进入优惠活动新增的定义信息输入操作，
 *
 *输入完成后点击保存完成信息新增操作。
 *
 *新增加的优惠活动数据默认状态为“新建”，
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
 *系统弹出提示“确定启用所选记录?”,用户点击确定之后执行启用操作，
 *
 *启用完成后，
 *
 *查询界面自动刷新显示数据记录最新的状态。
 *
 *用户所选的记录必须都是“未激活”状态的数据，
 *
 *否则系统弹出提示“该方案已经激活，不能再次激活”。
 *
 *参考SR2。
 *
 *f)	功能按钮：
 *
 *中止，
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
 *不能中止”。参考SR2
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
 *可以保持方案的名称不变，同时升级版本的方案有效期可以修改，
 *
 *结束的方案不能再启用
 *
 *编号生成规则为: ZKFA +8位日期+3位数字实别 , 
 *
 *最后3位数字自动增长
 *
 *如下格式：ZKFA20120323001;
 *
 *
 *SR2	点击按钮保存后、所录入的记录默认状态为“未激活”状态，
 *
 *“未激活”状态的方案可以进行修改和删除操作。方案数据记录状态包括“未激活”、“激活”2个。
 *
 *“新建”状态的方案可以勾选，进行激活操作，已经启用的方案可以进行勾选“结束”操作，这些状态的操作均不能回退。
 *
 *具体状态操作可以参考SUC-792
 * 
 * 
 * 
 * 
 *
 *
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
 *根据传入的客户编号查找相对应的客户合同的折扣率，
 *
 *如果查询到相应信息，
 *
 *则按此的折扣率进行价格计算。
 *
 *b)	渠道
 *根据传入的渠道编号查找相对应的折扣率。查找的规则是，
 *
 *根据渠道编码以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
 *
 *然后按照产品、货物类型依次进行匹配，即首先匹配产品相对应，
 *
 *在产品相对应的情况 下货物类型相应，
 *
 *如果依据上述二个原则对应上的情况下则匹配到折扣条目，
 *
 *可以利用折扣条目所对应的数据进行折扣计算，如果存在多条匹配的记录，
 *
 *选取折扣最低的进行计算。计算出减免的费用需要大于等于该折扣设定的最低一票，
 *
 *否则，最低减免按最低一票减免。
 *
 *
 *c)	产品
 *折扣条目的匹配原则，
 *
 *根据普通产品渠道以及出发地和目的地编码以及适用范围是否可以找到有相对应的渠道折扣。
 *
 *然后按照产品、货物类型依次进行匹配，
 *
 *即首先匹配产品相对应，
 *
 *在产品相对应的情况 下货物类型相应，
 *
 *如果依据上述二个原则对应上的情况下则匹配到折扣条目，
 *
 *可以利用折扣条目所对应的数据进行折扣计算，如果存在多条匹配的记录，
 *
 *选取折扣最低的进行计算。计算出减免的费用需要大于等于该折扣设定的最低一票，
 *
 *否则，最低减免按最低一票减免。
 *
 *
 *二、增值折扣计算
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
 * 新建方案不能与某个已经存在的方案重名，
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/util/PriceUtil.java
 * 
 * FILE NAME        	: PriceUtil.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.NumericConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountOrgConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CaculateFeeDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountResultDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.NewResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

import static com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME;

/**
 * 
 * @Description: 价格计算工具类
 * PriceUtil.java Create on 2013-3-19 上午9:22:00
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceUtil {  
    /**
     * 日志类
     */
    static final Logger log = Logger.getLogger(PriceUtil.class);
    /**
     * 小数点保留位数
     */
    private static int newScale=2;
    private static final int newScaleTen=10;
    
    
    /**
     * 
     * <p>运费计算接口输入参数必输字段信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-17 上午11:51:53
     * 
     * @param queryBillCacilateDto
     * 
     * @see
     */
    public static void checkQueryBillCacilateDtoDate(QueryBillCacilateDto queryBillCacilateDto){
	    // 固定必输项目
	    if(null == queryBillCacilateDto){
		throw new BillCaculateServiceException("条件不能为空!","条件不能为空!");
	    }
	    if(StringUtil.isEmpty(queryBillCacilateDto.getOriginalOrgCode())){
		throw new BillCaculateServiceException("出发部门编码不能为空!","出发部门编码不能为空!");
	    }
	    Date receiveDate = queryBillCacilateDto.getReceiveDate();
	    if(null == receiveDate){
		queryBillCacilateDto.setReceiveDate(new Date());
	    }
	    if(null == queryBillCacilateDto.getWeight()){
		throw new BillCaculateServiceException("重量不能为空!","重量不能为空!");
	    }
	    if(null == queryBillCacilateDto.getVolume()){
		throw new BillCaculateServiceException("体积不能为空!","体积不能为空!");
	    }
	    if(StringUtil.isEmpty(queryBillCacilateDto.getProductCode())){
		throw new BillCaculateServiceException("产品编码不能为空!","产品编码不能为空!");
	    }
	    // 特殊情况
	    String flightShift = queryBillCacilateDto.getFlightShift();
	    // 如果当前GUI端输入的三级产品为精准空运则航班号也不能为空，需要校验
	    if (!StringUtil.isEmpty(queryBillCacilateDto.getProductCode()) && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(queryBillCacilateDto.getProductCode())) {
		if (StringUtil.isEmpty(flightShift)) {
		    throw new BillCaculateServiceException("航班号不能为空!", "航班号不能为空!");
		}
	    }
	}
	
	/**
     * 
     * <p>快递计费数据检查</p> 
     * 
     * @author zdp
     * 
     * @date 2013-8-1 上午11:51:53
     * 
     * @param queryBillCacilateDto
     * 
     * @see
     */
    public static void checkExpressQueryBillCacilateDtoData(QueryBillCacilateDto queryBillCacilateDto){
	    // 固定必输项目
	    if(null == queryBillCacilateDto){
		throw new BillCaculateServiceException("条件不能为空!","条件不能为空!");
	    }
	    if(StringUtil.isEmpty(queryBillCacilateDto.getOriginalOrgCode())){
		throw new BillCaculateServiceException("出发部门编码不能为空!","出发部门编码不能为空!");
	    }
	    Date receiveDate = queryBillCacilateDto.getReceiveDate();
	    if(null == receiveDate){
		queryBillCacilateDto.setReceiveDate(new Date());
	    }
	    if(null == queryBillCacilateDto.getWeight()){
		throw new BillCaculateServiceException("重量不能为空!","重量不能为空!");
	    }
	   
	    if(StringUtil.isEmpty(queryBillCacilateDto.getProductCode())){
		throw new BillCaculateServiceException("产品编码不能为空!","产品编码不能为空!");
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
	 *	
	 *	轮循共16种情况，顺序如下：
	*		1、 网点到网点
	*		
	*		判断依据原则：产品相等，货物类型相等，所属行业相等
	*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
	*		判断依据原则：产品相等，货物类型为通用，所属行业相等
	*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
	*		判断依据原则：产品为全部，货物类型相等，所属行业相等
	*		判断依据原则：产品为全部，货物类型相等，所属行业相等
	*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
	*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
	*		 
		*	2、 网点到城市
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	3、网点到区域
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	4、城市到网点、
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	5、城市到城市、
		*	
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	6、城市到区域
		*	
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	7、区域到网点、
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	8、区域到城市
		*	
		*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	9、 区域到区域
		*	
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	10、全网到网点
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	11、全网到城市
		*	
		*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	12、全网到区域
		*	
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	13、 网点到全网
		*	
		*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	14、城市到全网
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	15、区域到全网
		*	
		*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	16、全网到全网
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	
		*	编码	折扣条目定义的编码	文本
		*	名称	折扣条目定义的名称	文本
		*	状态	折扣条目数据目前的状态	文本
		*	产品	关联的产品名称	文本
		*	货物类型	关联的货物类型	文本
		*	增值服务	关联的增值服务名称	文本
		*	出发地	关联的出发地区域	文本
		*	目的地	关联的目的地区域	文本
		*	出发类型	关联的出发地区域	文本
		*	目的类型	关联的目的地区域	文本
		*	折扣规则	体积、重量、费用	文本
		*	折扣率	打折的比率	数值
	 */
    /**
	 * 
	 * @Description: 检验PDA价格计算条件必输项
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-14 下午2:07:00
	 * 
	 * @param billCalculateDto
	 * 
	 * @version V1.0
	 */
    public static void checkPdaQueryBillCalcuateDto(PdaQueryBillCalculateDto billCalculateDto){
	    // 固定必输项目
    	if(null == billCalculateDto){
	    	throw new BillCaculateServiceException("输入条件不能为空!","输入条件不能为空!");
	    }
	    if(StringUtil.isEmpty(billCalculateDto.getOriginalOrgCode())){
	    	throw new BillCaculateServiceException("出发部门编码不能为空!","出发部门编码不能为空!");
	    }
	    if(StringUtil.isEmpty(billCalculateDto.getProductCode())){
	    	throw new BillCaculateServiceException("产品编码不能为空!","产品编码不能为空!");
	    }
	    Date receiveDate = billCalculateDto.getReceiveDate();
	    if(null == receiveDate){
		billCalculateDto.setReceiveDate(new Date());
	    }
	    if(null == billCalculateDto.getWeight()){
	    	throw new BillCaculateServiceException("重量不能为空!","重量不能为空!");
	    }
	    if(null == billCalculateDto.getVolume()){
	    	throw new BillCaculateServiceException("体积不能为空!","体积不能为空!");
	    }
	  
	    if(CollectionUtils.isEmpty(billCalculateDto.getPriceEntities())){
	    	throw new BillCaculateServiceException("计价条目列表不能为空!","计价条目列表不能为空!");
	    }
	}
    
    /**
     * CRM营销活动校验
     * @创建时间 2014-4-24 上午10:46:02   
     * @创建人： WangQianJin
     */												
    public static void validateActiveDiscountPda(QueryBillCacilateDto billCalculateDto,IMarkActivitiesService markActivitiesService) {
    	MarkActivitiesQueryConditionDto activeDto=billCalculateDto.getActiveDto();
		//判断市场营销活动是否为空
		if(activeDto==null || StringUtils.isEmpty(activeDto.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(billCalculateDto.getProductCode())){
			billCalculateDto.setCalActiveDiscount(false);
		}else{	
			//是否展货
			activeDto.setIsExhibitionGoods(billCalculateDto.getIsExhibitCargo()?FossConstants.ACTIVE:FossConstants.INACTIVE);
			//订单来源
			activeDto.setOrderResource(billCalculateDto.getChannelCode());
			//产品类型
			activeDto.setProductType(billCalculateDto.getProductCode());
			//开展部门
			activeDto.setDevelopDeptCode(billCalculateDto.getOriginalOrgCode());
			//出发外场
			activeDto.setStartOutFieldCode(billCalculateDto.getLoadOrgCode());
			//到达外场
			activeDto.setArriveOutFieldCode(billCalculateDto.getLastOutLoadOrgCode());	
			//发货部门
			activeDto.setLeaveAreaCode(billCalculateDto.getOriginalOrgCode());
			//到达部门
			activeDto.setArriveAreaCode(billCalculateDto.getDestinationOrgCode());
			//开单时间
			activeDto.setBilllingTime(billCalculateDto.getReceiveDate());			
			//开单金额
			activeDto.setBilllingAmount(billCalculateDto.getTransportFee());						
			//开单重量
			activeDto.setBilllingWeight(billCalculateDto.getWeight());
			//开单体积
			activeDto.setBilllingVolumn(billCalculateDto.getVolume());	
			//PDA不校验开单品名和行业信息
			activeDto.setPda(true);
			//查询活动折扣信息
			MarkActivitiesQueryConditionDto result=markActivitiesService.queryMarkActivitiesInfoByCondition(activeDto);
			if(result!=null && result.getOptionList()!=null && result.getOptionList().size()>0){
				//计算市场营销活动折扣信息
				billCalculateDto.setCalActiveDiscount(true);
				//将营销活动设置到DTO中
				billCalculateDto.setActiveDto(result);
			}else{
				billCalculateDto.setCalActiveDiscount(false);
				//您选择的市场营销活动不符合条件！				
				throw new BillCaculateServiceException("您选择的市场推广活动不符合条件！");
			}
		}		
	}
    
    /**
     * CRM营销活动校验
     * @创建时间 2014-4-24 上午10:46:02   
     * @创建人： WangQianJin
     */
    public static void validateActiveDiscountPda(QueryBillCacilateValueAddDto billCalculateDto,IMarkActivitiesService markActivitiesService) {
    	MarkActivitiesQueryConditionDto activeDto=billCalculateDto.getActiveDto();
		//判断市场营销活动是否为空
		if(activeDto==null || StringUtils.isEmpty(activeDto.getCode())
				|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(billCalculateDto.getProductCode())){
			billCalculateDto.setCalActiveDiscount(false);
		}else{						
			//订单来源
			activeDto.setOrderResource(billCalculateDto.getChannelCode());
			//产品类型
			activeDto.setProductType(billCalculateDto.getProductCode());
			//开展部门
			activeDto.setDevelopDeptCode(billCalculateDto.getOriginalOrgCode());
			//出发外场
			activeDto.setStartOutFieldCode(billCalculateDto.getLoadOrgCode());
			//到达外场
			activeDto.setArriveOutFieldCode(billCalculateDto.getLastOutLoadOrgCode());	
			//发货部门
			activeDto.setLeaveAreaCode(billCalculateDto.getOriginalOrgCode());
			//到达部门
			activeDto.setArriveAreaCode(billCalculateDto.getDestinationOrgCode());
			//开单时间
			activeDto.setBilllingTime(billCalculateDto.getReceiveDate());			
			//开单金额
			activeDto.setBilllingAmount(billCalculateDto.getTransportFee());						
			//开单重量
			activeDto.setBilllingWeight(billCalculateDto.getWeight());
			//开单体积
			activeDto.setBilllingVolumn(billCalculateDto.getVolume());
			//PDA不校验开单品名和行业信息
			activeDto.setPda(true);
			//查询活动折扣信息
			MarkActivitiesQueryConditionDto result=markActivitiesService.queryMarkActivitiesInfoByCondition(activeDto);
			if(result!=null && result.getOptionList()!=null && result.getOptionList().size()>0){
				//计算市场营销活动折扣信息
				billCalculateDto.setCalActiveDiscount(true);
				//将营销活动设置到DTO中
				billCalculateDto.setActiveDto(result);
			}else{
				billCalculateDto.setCalActiveDiscount(false);
				//您选择的市场营销活动不符合条件！				
				throw new BillCaculateServiceException("您选择的市场推广活动不符合条件！");
			}
		}		
	}
    
    
    /**
	 * 校验开单金额是否符合营销活动条件
	 * @创建时间 2014-4-24 下午4:29:48   
	 * @创建人： WangQianJin
	 */
	public static void validateBillAmount(GuiQueryBillCalculateDto billCalculateDto,List<GuiResultBillCalculateDto> billCalculateDtos,
			IMarkActivitiesService markActivitiesService){
		if(billCalculateDto.isCalActiveDiscount()){
			if(CollectionUtils.isNotEmpty(billCalculateDtos)) {
				BigDecimal billlingAmount=BigDecimal.ZERO;
				for(GuiResultBillCalculateDto dto:billCalculateDtos){
					billlingAmount=billlingAmount.add(dto.getCaculateFee());
				}
				//校验开单金额	
				MarkActivitiesQueryConditionDto activeDto=billCalculateDto.getActiveDto();
				activeDto.setBilllingAmount(billlingAmount);
				MarkActivitiesQueryConditionDto result=markActivitiesService.queryMarkActivitiesInfoByCondition(activeDto);
				if(result!=null && result.getOptionList()!=null && result.getOptionList().size()>0){
					//计算市场营销活动折扣信息
					billCalculateDto.setActiveDto(result);
				}else{
					billCalculateDto.setCalActiveDiscount(false);
					//您选择的市场营销活动不符合条件！
					throw new BillCaculateServiceException("您选择的市场营销活动不符合条件!");
				}
			}
		}
	}
    
	/**
	 * 
	 * @Description: 检验PDA价格计算条件必输项
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-14 下午2:07:00
	 * 
	 * @param billCalculateDto
	 * 
	 * @version V1.0
	 */
   public static void checkExpressPdaQueryBillCalcuateDto(PdaQueryBillCalculateDto billCalculateDto){
	    // 固定必输项目
   	if(null == billCalculateDto){
	    	throw new BillCaculateServiceException("输入条件不能为空!","输入条件不能为空!");
	    }
	    if(StringUtil.isEmpty(billCalculateDto.getOriginalOrgCode())){
	    	throw new BillCaculateServiceException("出发部门编码不能为空!","出发部门编码不能为空!");
	    }
	    if(StringUtil.isEmpty(billCalculateDto.getProductCode())){
	    	throw new BillCaculateServiceException("产品编码不能为空!","产品编码不能为空!");
	    }
	    Date receiveDate = billCalculateDto.getReceiveDate();
	    if(null == receiveDate){
		billCalculateDto.setReceiveDate(new Date());
	    }
	    if(null == billCalculateDto.getWeight()){
	    	throw new BillCaculateServiceException("重量不能为空!","重量不能为空!");
	    }
	      	  
	    if(CollectionUtils.isEmpty(billCalculateDto.getPriceEntities())){
	    	throw new BillCaculateServiceException("计价条目列表不能为空!","计价条目列表不能为空!");
	    }
	}
   
	   /**
	  	 * 
	  	 * @Description: 检验PDA价格计算条件必输项（针对RFOSS2015041729）
	  	 * 
	  	 * @param billCalculateDto
	  	 * 
	  	 * @version V1.0
	  	 * 
	  	 * @author foss-206860
	  	 */
	   public static void checkExpressPdaQueryBillCalcuateDtoNoProduct(PdaQueryBillCalculateDto billCalculateDto){
	  	    // 固定必输项目
	   		if(null == billCalculateDto){
	  	    	throw new BillCaculateServiceException("输入条件不能为空!","输入条件不能为空!");
	  	    }
	  	    if(StringUtil.isEmpty(billCalculateDto.getOriginalOrgCode())){
	  	    	throw new BillCaculateServiceException("出发部门编码不能为空!","出发部门编码不能为空!");
	  	    }
	  	    Date receiveDate = billCalculateDto.getReceiveDate();
	  	    if(null == receiveDate){
	  		billCalculateDto.setReceiveDate(new Date());
	  	    }
	  	    if(null == billCalculateDto.getWeight()){
	  	    	throw new BillCaculateServiceException("重量不能为空!","重量不能为空!");
	  	    }
	  	      	  
	  	    if(CollectionUtils.isEmpty(billCalculateDto.getPriceEntities())){
	  	    	throw new BillCaculateServiceException("计价条目列表不能为空!","计价条目列表不能为空!");
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
	 *	
	 *	轮循共16种情况，顺序如下：
	*		1、 网点到网点
	*		
	*		判断依据原则：产品相等，货物类型相等，所属行业相等
	*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
	*		判断依据原则：产品相等，货物类型为通用，所属行业相等
	*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
	*		判断依据原则：产品为全部，货物类型相等，所属行业相等
	*		判断依据原则：产品为全部，货物类型相等，所属行业相等
	*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
	*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
	*		 
		*	2、 网点到城市
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	3、网点到区域
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	4、城市到网点、
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	5、城市到城市、
		*	
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	6、城市到区域
		*	
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	7、区域到网点、
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	8、区域到城市
		*	
		*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	9、 区域到区域
		*	
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	10、全网到网点
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	11、全网到城市
		*	
		*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	12、全网到区域
		*	
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	13、 网点到全网
		*	
		*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	14、城市到全网
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	15、区域到全网
		*	
		*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	 
		*	16、全网到全网
		*	
		*	判断依据原则：产品相等，货物类型相等，所属行业相等
		*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*	判断依据原则：产品相等，货物类型为通用，所属行业相等
		*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型相等，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*	
	 */
    /**
	 * 
	 * 根据重量、体积和明细费率计算实际费用
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 上午8:41:48
	 * 
	 * @param originnalCost 费用
	 * 
	 * @param weight 重量
	 * 
	 * @param volume 体积
	 * 
	 * @param priceValuationEntity  计费规则 
	 * 
	 * @param criteriaDetailentityList 费用明细list
	 * 
	 */
   public static List<ProductPriceDto> calculateCostServices(BigDecimal weight, BigDecimal volume,
			List<ResultProductPriceDto> resultList, Date receiveDate, ProductEntity productEntity,
			GoodsTypeEntity goodsTypeEntity, PriceEntity priceEntity) throws BillCaculateServiceException {

		List<ProductPriceDto> productPriceList = new ArrayList<ProductPriceDto>();
		BigDecimal weightFeeRate = null;
		BigDecimal volumeFeeRate = null;
		
		for (ResultProductPriceDto resultProductPriceDto : resultList) {
			String caculateType = resultProductPriceDto.getCaculateType();
			Double total = null;
			/* 按重量 */
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)) {
				if (null != weight) {
					double doubleWeight = weight.doubleValue();
					double tempLeftrange = resultProductPriceDto.getLeftrange();
					double tempRightrange = resultProductPriceDto.getRightrange();
					weightFeeRate = resultProductPriceDto.getFeeRate();
					/* 筛选合适的重量范围计价区间 */
					if (doubleWeight >= tempLeftrange && doubleWeight < tempRightrange) {
						CaculateFeeDto freight = costPrepareCaculateProperty(resultProductPriceDto);
						total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(weight.doubleValue(), freight));
						productPriceList.add(converPriceClientData(total, resultProductPriceDto,
								caculateType, priceEntity));
					}
				}
			}
			/* 按体积 */
			if (CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)) {
				if (null != volume) {
					double doubleVolume = volume.doubleValue();
					double tempLeftrange = resultProductPriceDto.getLeftrange();
					double tempRightrange = resultProductPriceDto.getRightrange();
					volumeFeeRate = resultProductPriceDto.getFeeRate();
					/* 筛选合适的体积范围计价区间 */
					if (doubleVolume >= tempLeftrange && doubleVolume < tempRightrange) {
						CaculateFeeDto freight = costPrepareCaculateProperty(resultProductPriceDto);
						total = new Double(PriceRuleCalculator.calcFreightDetail(volume.doubleValue(), freight));
						productPriceList.add(converPriceClientData(total, resultProductPriceDto,
								caculateType, priceEntity));
					}
				}
			}
		}
		List<ProductPriceDto> productPriceDtoList = new ArrayList<ProductPriceDto>();
		// 处理重货轻货费用，计费类型不一致
		if (productPriceList.size() > 1) {
			ProductPriceDto weightDto = null;
			ProductPriceDto volumeDto = null;
			for (int loop = 0; loop < productPriceList.size(); loop++) {
				ProductPriceDto temp = productPriceList.get(loop);
				if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
						PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT)) {
					weightDto = temp;
				}
				if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
						CRITERIA_DETAIL_CACULATE_TYPE_VOLUME)) {
					volumeDto = temp;
				}
			}
			ProductPriceDto compareDto;
			
			if(weightDto == null || volumeDto == null){
				return null;
			}
			
			compareDto = weightDto.getCaculateFee().doubleValue() > volumeDto.getCaculateFee().doubleValue() ? weightDto
					: volumeDto;
				//判断是否为最低一票
				if(compareDto.getCaculateFee().compareTo(compareDto.getMinFee())==0){
						BigDecimal weightValue = weight.multiply(weightFeeRate.divide(BigDecimal.valueOf(NumericConstants.NUM_100)));
						BigDecimal volumeValue = volume.multiply(volumeFeeRate.divide(BigDecimal.valueOf(NumericConstants.NUM_100)));
						//重货/轻货相同默认以重量为准， 重量大计费方式改为重量计费，轻货大计费方式改为轻货计费
						if(weightValue.compareTo(volumeValue)==0 || weightValue.compareTo(volumeValue)==1){
							compareDto.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
							compareDto = weightDto;
						}else{
							compareDto.setCaculateType(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
							compareDto = volumeDto;
						}
				}
		

			// 设置产品信息
			compareDto.setProductCode(productEntity.getCode());
			compareDto.setProductName(productEntity.getName());
			// 设置货物信息
			if (goodsTypeEntity != null) {
				compareDto.setGoodsTypeCode(goodsTypeEntity.getCode());
				compareDto.setGoodsTypeName(goodsTypeEntity.getName());
			}

			// 重货,轻货相互补充数据给予客户端展示
			if (StringUtils.equalsIgnoreCase(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME,
					compareDto.getCaculateType())) {
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(compareDto.getFeeRate()); 
				// 设置重货费率
				compareDto.setHeavyFeeRate(weightDto.getFeeRate()); 
				// 设置当前轻货费率
				compareDto.setLightFeeRate(compareDto.getFeeRate()); 
			} else {
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(compareDto.getFeeRate()); 
				// 设置轻货费率
				compareDto.setLightFeeRate(volumeDto.getFeeRate()); 
				// 设置当前重货费率
				compareDto.setHeavyFeeRate(compareDto.getFeeRate()); 
			}
			productPriceDtoList.add(compareDto);
		} else {
			return productPriceList;
		}
		return productPriceDtoList;
	}
	
   
   /**
 	 * 
 	 * 根据重量、体积和明细费率计算实际费用(零担汽运新规则)
 	 * 
 	 * @author DP-Foss-liyongfei
 	 * 
 	 * @date 2012-11-7 上午8:41:48
 	 * 
 	 * @param originnalCost 费用
 	 * 
 	 * @param weight 重量
 	 * 
 	 * @param volume 体积
 	 * 
 	 * @param priceValuationEntity  计费规则 
 	 * 
 	 * @param criteriaDetailentityList 费用明细list
 	 * 
 	 */
    public static List<ProductPriceDto> newCalculateCostServices(BigDecimal weight, BigDecimal volume,
 			List<NewResultProductPriceDto> resultList, Date receiveDate, ProductEntity productEntity,
 			GoodsTypeEntity goodsTypeEntity, PriceEntity priceEntity) throws BillCaculateServiceException {
    	List<ProductPriceDto> productPriceList = new ArrayList<ProductPriceDto>();
 		//先过滤下所落在那个分段区间和所落在区间的下一个区间
// 		List<NewResultProductPriceDto> weightList = new ArrayList<NewResultProductPriceDto>();
// 		List<NewResultProductPriceDto> volumeList = new ArrayList<NewResultProductPriceDto>();
 		NewResultProductPriceDto weightDtos =null;//当前区间的按重量计费的实体
 		NewResultProductPriceDto volumeDtos =null;//当前区间的按体积计费的实体
 		double maxWeightCriti = 0;//循环时记录最大的重量临界值
 		double maxVolumeCriti = 0;//循环时记录最大的体积临界值
 		for (NewResultProductPriceDto resultProductPriceDto : resultList) {
 			String caculateType = resultProductPriceDto.getCaculateType();
 			double criticalValue = resultProductPriceDto.getCriticalValue();
 			//提取符合条件的按重量计费的实体
 			if (null != weight) {
 				double doubleWeight = weight.doubleValue();
	 			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)) {
	 				if(doubleWeight*NumericConstants.NUM_100 >= criticalValue && maxWeightCriti <= criticalValue){//临界值概念：比如第一段临界值是0，第二段段临界值是5000，则重量50是落在第一段，而不是第二段
	 					weightDtos = resultProductPriceDto;
	 					maxWeightCriti = criticalValue;
	 				}
	 			}
 			}
 			//提取符合条件的按体积计费的实体
 			if (null != volume) {
 				double doubleVolume = volume.doubleValue();
	 			if (CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)) {
	 				if(doubleVolume*NumericConstants.NUM_100 >= criticalValue && maxVolumeCriti <= criticalValue){//临界值概念：比如第一段临界值是0，第二段段临界值是5000，则体积50是落在第一段，而不是第二段
	 					volumeDtos = resultProductPriceDto;
	 					maxVolumeCriti = criticalValue;
	 				}
	 			}
 			}
 		}
 		
 		//主要用于处理，只有一段，没有下段的情况,直接计算
 		if(weightDtos!=null){
 			CaculateFeeDto freight = newCostPrepareCaculateProperty(weightDtos);
			double total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(weight.doubleValue(), freight));
			productPriceList.add(newConverPriceClientData(total, weightDtos,
					PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, priceEntity));
 		}
 		if(volumeDtos!=null){
 			CaculateFeeDto freight = newCostPrepareCaculateProperty(volumeDtos);
			double total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(volume.doubleValue(), freight));
			productPriceList.add(newConverPriceClientData(total, volumeDtos,
					CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, priceEntity));
 		}
// 		
// 		if(weightList.size()==2){
// 			NewResultProductPriceDto currentDto =weightList.get(0);
// 			NewResultProductPriceDto nextDto =weightList.get(1);
// 			CaculateFeeDto freight = newCostPrepareCaculateProperty(currentDto);
//			double current = Double.valueOf(PriceRuleCalculator.calcFreightDetail(weight.doubleValue(), freight));
//			freight = newCostPrepareCaculateProperty(nextDto);
//			double criticalValue=(double)nextDto.getCriticalValue()/100;
//			double next = Double.valueOf(PriceRuleCalculator.calcFreightDetail(criticalValue, freight));
//			if(current<=next){
//				productPriceList.add(newConverPriceClientData(current, currentDto,
//						PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, priceEntity));
//			}else{
//				productPriceList.add(newConverPriceClientData(next, nextDto,
//						PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT, priceEntity));
//			}
//				
// 		}
// 		
// 		if(volumeList.size()==2){
// 			NewResultProductPriceDto currentDto =volumeList.get(0);
// 			NewResultProductPriceDto nextDto =volumeList.get(1);
// 			CaculateFeeDto freight = newCostPrepareCaculateProperty(currentDto);
//			double current = Double.valueOf(PriceRuleCalculator.calcFreightDetail(volume.doubleValue(), freight));
//			freight = newCostPrepareCaculateProperty(nextDto);
//			double criticalValue=(double)nextDto.getCriticalValue()/100;			
//			double next = Double.valueOf(PriceRuleCalculator.calcFreightDetail(criticalValue, freight));
//			if(current<=next){
//				productPriceList.add(newConverPriceClientData(current, currentDto,
//						PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, priceEntity));
//			}else{
//				productPriceList.add(newConverPriceClientData(next, nextDto,
//						PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME, priceEntity));
//			}
//				
// 		}
 		List<ProductPriceDto> productPriceDtoList = new ArrayList<ProductPriceDto>();
		// 处理重货轻货费用，计费类型不一致
		if (productPriceList.size() > 1) {
			ProductPriceDto weightDto = null;
			ProductPriceDto volumeDto = null;
			for (int loop = 0; loop < productPriceList.size(); loop++) {
				ProductPriceDto temp = productPriceList.get(loop);
				if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
						PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT)) {
					weightDto = temp;
				}
				if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
						CRITERIA_DETAIL_CACULATE_TYPE_VOLUME)) {
					volumeDto = temp;
				}
			}
			ProductPriceDto compareDto;
			if(weightDto == null || volumeDto == null){
				return null;
			}
			compareDto = weightDto.getCaculateFee().doubleValue() > volumeDto.getCaculateFee().doubleValue() ? weightDto
					: volumeDto;
			
				//判断是否为最低一票
				if(compareDto.getCaculateFee().compareTo(compareDto.getMinFee())==0){
						BigDecimal weightValue = weight.multiply(weightDto.getCaculateFee().divide(BigDecimal.valueOf(NumericConstants.NUM_100)));
						BigDecimal volumeValue = volume.multiply(volumeDto.getCaculateFee().divide(BigDecimal.valueOf(NumericConstants.NUM_100)));
						//重货/轻货相同默认以重量为准， 重量大计费方式改为重量计费，轻货大计费方式改为轻货计费
						if(weightValue.compareTo(volumeValue)==0 || weightValue.compareTo(volumeValue)==1){
							compareDto.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
							compareDto = weightDto;
						}else{
							compareDto.setCaculateType(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
							compareDto = volumeDto;
						}
				}
		

			// 设置产品信息
			compareDto.setProductCode(productEntity.getCode());
			compareDto.setProductName(productEntity.getName());
			// 设置货物信息
			if (goodsTypeEntity != null) {
				compareDto.setGoodsTypeCode(goodsTypeEntity.getCode());
				compareDto.setGoodsTypeName(goodsTypeEntity.getName());
			}

			// 重货,轻货相互补充数据给予客户端展示
			if (StringUtils.equalsIgnoreCase(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME,
					compareDto.getCaculateType())) {
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(compareDto.getFeeRate()); 
				// 设置重货费率
				compareDto.setHeavyFeeRate(weightDto.getFeeRate()); 
				// 设置当前轻货费率
				compareDto.setLightFeeRate(compareDto.getFeeRate()); 
			} else {
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(compareDto.getFeeRate()); 
				// 设置轻货费率
				compareDto.setLightFeeRate(volumeDto.getFeeRate()); 
				// 设置当前重货费率
				compareDto.setHeavyFeeRate(compareDto.getFeeRate()); 
			}
			productPriceDtoList.add(compareDto);
		} else {
			return productPriceList;
		}
		return productPriceDtoList;
 		
 	}

   
	/**
	 * * @author DP-Foss-潘国仰
	 * @param weight
	 * @param volume
	 * @param resultList
	 * @param receiveDate
	 * @param productEntity
	 * @param goodsTypeEntity
	 * @param priceEntity
	 * @return
	 * @throws BillCaculateServiceException
	 */
	public static List<ProductPriceDto> bigGoodscalculateCostServices(BigDecimal weight, BigDecimal volume,
			List<ResultProductPriceDto> resultList, Date receiveDate, ProductEntity productEntity,
			GoodsTypeEntity goodsTypeEntity, PriceEntity priceEntity) throws BillCaculateServiceException {

		List<ProductPriceDto> productPriceList = new ArrayList<ProductPriceDto>();
		
		for (ResultProductPriceDto resultProductPriceDto : resultList) {
			String caculateType = resultProductPriceDto.getCaculateType();
			Double total = null;
			/* 按重量 */
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)) {
				if (null != weight) {
					double doubleWeight = weight.doubleValue();
					double tempLeftrange = resultProductPriceDto.getLeftrange();
					double tempRightrange = resultProductPriceDto.getRightrange();
					/* 筛选合适的重量范围计价区间 */
					if (doubleWeight > tempLeftrange && doubleWeight <= tempRightrange) {
						CaculateFeeDto freight = costPrepareCaculateProperty(resultProductPriceDto);
						total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(weight.doubleValue(), freight));
						productPriceList.add(converPriceClientData(total, resultProductPriceDto,
								caculateType, priceEntity));
					}
				}
			}
			/* 按体积 */
			if (CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)) {
				if (null != volume) {
					double doubleVolume = volume.doubleValue();
					double tempLeftrange = resultProductPriceDto.getLeftrange();
					double tempRightrange = resultProductPriceDto.getRightrange();
					/* 筛选合适的体积范围计价区间 */
					if (doubleVolume > tempLeftrange && doubleVolume <= tempRightrange) {
						CaculateFeeDto freight = costPrepareCaculateProperty(resultProductPriceDto);
						total = new Double(PriceRuleCalculator.calcFreightDetail(volume.doubleValue(), freight));
						productPriceList.add(converPriceClientData(total, resultProductPriceDto,
								caculateType, priceEntity));
					}
				}
			}
		}
		List<ProductPriceDto> productPriceDtoList = new ArrayList<ProductPriceDto>();
		// 处理重货轻货费用，计费类型不一致
		if (productPriceList.size() > 1) {
			ProductPriceDto weightDto = null;
			ProductPriceDto volumeDto = null;
			for (int loop = 0; loop < productPriceList.size(); loop++) {
				ProductPriceDto temp = productPriceList.get(loop);
				if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
						PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT)) {
					weightDto = temp;
				}
				if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
						CRITERIA_DETAIL_CACULATE_TYPE_VOLUME)) {
					volumeDto = temp;
				}
			}
			ProductPriceDto compareDto;
			if(weightDto == null || volumeDto == null){
				return null;
			}
			compareDto = weightDto.getCaculateFee().doubleValue() > volumeDto.getCaculateFee().doubleValue() ? weightDto
					: volumeDto;
				

			// 设置产品信息
			compareDto.setProductCode(productEntity.getCode());
			compareDto.setProductName(productEntity.getName());
			// 设置货物信息
			if (goodsTypeEntity != null) {
				compareDto.setGoodsTypeCode(goodsTypeEntity.getCode());
				compareDto.setGoodsTypeName(goodsTypeEntity.getName());
			}
         
			// 重货,轻货相互补充数据给予客户端展示
			if (StringUtils.equalsIgnoreCase(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME,
					compareDto.getCaculateType())) {
				// 设置最终实际计算的费率(总费用/体积)
				compareDto.setActualFeeRate(compareDto.getCaculateFee().divide(volume,2,BigDecimal.ROUND_HALF_DOWN)); 
				// 设置重货费率
				//compareDto.setHeavyFeeRate(weightDto.getFeeRate()); 
				// 设置当前轻货费率
				compareDto.setLightFeeRate(compareDto.getFeeRate()); 
			} else {
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(compareDto.getCaculateFee().divide(weight,2,BigDecimal.ROUND_HALF_DOWN)); 
				// 设置轻货费率
				//compareDto.setLightFeeRate(volumeDto.getFeeRate()); 
				// 设置当前重货费率
				compareDto.setHeavyFeeRate(compareDto.getFeeRate()); 
			}
			productPriceDtoList.add(compareDto);
		} else {
			if(CollectionUtils.isNotEmpty(productPriceList)){
				ProductPriceDto dto =productPriceList.get(0);
				if(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(dto.getCaculateType())){
					dto.setLightFeeRate(dto.getFeeRate());
					dto.setActualFeeRate(dto.getCaculateFee().divide(volume, 2, BigDecimal.ROUND_HALF_DOWN));
				}
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(dto.getCaculateType())){
					dto.setHeavyFeeRate(dto.getFeeRate());
					dto.setActualFeeRate(dto.getCaculateFee().divide(weight, 2, BigDecimal.ROUND_HALF_DOWN));
				}
				return productPriceList;
			}else{
				throw new BillCaculateServiceException("没有找到运费!");
			}
			
		}
		return productPriceDtoList;
	}
	
	/**
	 * 
	 * 根据重量和明细费率计算快递实际费用
	 * 
	 * @author DP-Foss-zdp
	 * 
	 * @date 2013-8-5 上午8:41:48
	 * 
	 * @param originnalCost 费用
	 * 
	 * @param weight 重量
	 * 
	 * @param volume 体积
	 * 
	 * @param priceValuationEntity  计费规则 
	 * 
	 * @param criteriaDetailentityList 费用明细list
	 * 
	 */
	public static List<ProductPriceDto> calculateExpressCostServices(BigDecimal weight, BigDecimal volume,
			List<ResultProductPriceDto> resultList, Date receiveDate, ProductEntity productEntity,
			GoodsTypeEntity goodsTypeEntity, PriceEntity priceEntity,double selfPickUpSubStract,BigDecimal volumeWeight) throws BillCaculateServiceException {
		
		List<ProductPriceDto> productPriceList = new ArrayList<ProductPriceDto>();
		ProductPriceDto compareDto = new ProductPriceDto();
		if (volume == null) {
			volume = BigDecimal.ZERO;
		}
		/**
		 * @author YANGKANG 
		 * DMANA-3936 
		 * 快递运费折扣运费调整 赋值初始化的默认值 方便后续打折费率限制的判断
		 */
		compareDto.setFirstTempRate(BigDecimal.ZERO);
		compareDto.setSecondTempRate(BigDecimal.ZERO);
		compareDto.setFirstWeight(BigDecimal.ZERO);
		compareDto.setSecondWeight(BigDecimal.ZERO);

		// BigDecimal volumeWeight =
		// volume.multiply(FossConstants.VOLUME_TO_WEIGHT).setScale(3,
		// BigDecimal.ROUND_HALF_UP);
		// weight=weight.setScale( 0, BigDecimal.ROUND_HALF_UP);
		if (volumeWeight.compareTo(weight) > 0) {
			weight = volumeWeight;
			compareDto.setCaculateType(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);

		} else {
			compareDto.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
		}

		compareDto.setVolumeWeight(weight);

		int flagLoop = 0;
		double doubleWeight = weight.doubleValue();
		BigDecimal firstRate = BigDecimal.ZERO;
		BigDecimal tempRate = BigDecimal.ZERO;
		BigDecimal tempFee = BigDecimal.ZERO;
		BigDecimal totalFee = BigDecimal.ZERO;

		// 设置产品信息
		compareDto.setProductCode(productEntity.getCode());
		compareDto.setProductName(productEntity.getName());
		// 设置货物信息
		if (goodsTypeEntity != null) {
			compareDto.setGoodsTypeCode(goodsTypeEntity.getCode());
			compareDto.setGoodsTypeName(goodsTypeEntity.getName());
		}
		compareDto.setPriceEntityName(priceEntity.getName());
		compareDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_FRT);
		compareDto.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);

		for (ResultProductPriceDto resultProductPriceDto : resultList) {

			flagLoop++;
			tempRate = resultProductPriceDto.getFeeRate();
			tempFee = BigDecimal.ZERO;
			double tempLeftrange = resultProductPriceDto.getLeftrange();
			double tempRightrange = resultProductPriceDto.getRightrange();
			if (flagLoop == 1) {
				if (doubleWeight > tempLeftrange&& doubleWeight <= tempRightrange) {
					// 若在等级1区间段，价格为等级1对应的价格
					// firstRate=BigDecimal.valueOf(resultProductPriceDto.getFee()).divide(BigDecimal.valueOf(PricingConstants.YUTOFEN),
					// newScale, BigDecimal.ROUND_HALF_UP);
					firstRate = BigDecimal.valueOf(resultProductPriceDto.getFee());
					compareDto.setFirstRateFee(firstRate);
					// MathContext mc = new MathContext(newScale,
					// RoundingMode.HALF_UP);
					// 分变元，两个小数点四舍五入
					firstRate = firstRate.divide(BigDecimal.valueOf(PricingConstants.YUTOFEN),newScale, BigDecimal.ROUND_HALF_UP);
					if (firstRate.compareTo(new BigDecimal(selfPickUpSubStract)) == 1) {
						// firstRate=firstRate.subtract(BigDecimal.valueOf(selfPickUpSubStract),
						// mc);
						firstRate = firstRate.subtract(BigDecimal.valueOf(selfPickUpSubStract));
					}

					compareDto.setCaculateFee(firstRate);
					compareDto.setDiscountFee(firstRate);
					compareDto.setActualFeeRate(firstRate.multiply(BigDecimal.valueOf(PricingConstants.YUTOFEN)).divide(weight, newScale,BigDecimal.ROUND_HALF_UP));
					productPriceList.add(compareDto);
					return productPriceList;
				} else if (doubleWeight > tempRightrange) {
					// 大于等级一，先记下首重费用
					weight = weight.setScale(0, BigDecimal.ROUND_HALF_UP);
					doubleWeight = weight.doubleValue();
					// firstRate=BigDecimal.valueOf(resultProductPriceDto.getFee()).divide(BigDecimal.valueOf(PricingConstants.YUTOFEN),
					// newScale, BigDecimal.ROUND_HALF_UP);
					firstRate = BigDecimal.valueOf(resultProductPriceDto.getFee());
					/**
					 * @author YANGKANG 
					 * DMANA-3936 快递运费折扣运费调整 记录首重费用到产品价格实体中
					 * 方便后续打折运费的计算
					 */
					compareDto.setFirstRateFee(firstRate);
				} else {
					// 没有价格满足
					return null;
				}

			} else {
				if (doubleWeight > tempLeftrange
						&& doubleWeight <= tempRightrange) {

					// tempFee=
					// tempRate.multiply(BigDecimal.valueOf(doubleWeight -
					// tempLeftrange).divide(BigDecimal.valueOf(resultProductPriceDto.getDimension()),newScale,BigDecimal.ROUND_HALF_UP)).
					// divide(BigDecimal.valueOf(PricingConstants.YUTOFEN),
					// newScale,BigDecimal.ROUND_HALF_UP);
					tempFee = tempRate.multiply(BigDecimal.valueOf(
							doubleWeight - tempLeftrange).divide(
							BigDecimal.valueOf(resultProductPriceDto
									.getDimension()), newScaleTen,
							BigDecimal.ROUND_HALF_UP));
					/**
					 * @author YANGKANG 
					 * DMANA-3936 快递运费折扣运费调整 记录续重区间的重量和费率
					 * 方便后续打折运费的计算 循环到该逻辑处计算的可能是续重区间1的费用，同时也可能是续重区间2的费用
					 * 首先通过判断续重区间1的重量和费率是否有值来判断计算的续重区间
					 */
					if (compareDto.getFirstTempRate()
							.compareTo(BigDecimal.ZERO) > 0
							&& compareDto.getFirstWeight().compareTo(
									BigDecimal.ZERO) > 0) {
						compareDto.setSecondTempRate(tempRate);
						compareDto.setSecondWeight(BigDecimal
								.valueOf(doubleWeight - tempLeftrange));
					} else {
						compareDto.setFirstTempRate(tempRate);
						compareDto.setFirstWeight(BigDecimal
								.valueOf(doubleWeight - tempLeftrange));
					}
				} else if (doubleWeight > tempRightrange) {

					// tempFee=
					// tempRate.multiply(BigDecimal.valueOf(tempRightrange -
					// tempLeftrange).divide(BigDecimal.valueOf(resultProductPriceDto.getDimension()),newScale,BigDecimal.ROUND_HALF_UP)).
					// divide(BigDecimal.valueOf(PricingConstants.YUTOFEN),newScale,
					// BigDecimal.ROUND_HALF_UP);
					tempFee = tempRate.multiply(BigDecimal.valueOf(
							tempRightrange - tempLeftrange).divide(
							BigDecimal.valueOf(resultProductPriceDto
									.getDimension()), newScaleTen,
							BigDecimal.ROUND_HALF_UP));
					/**
					 * @author YANGKANG 
					 * DMANA-3936 快递运费折扣运费调整 记录续重区间的重量和费率
					 * 方便后续打折运费的计算 循环到该逻辑处计算的是续重区间1的运费
					 */
					compareDto.setFirstTempRate(tempRate);
					compareDto.setFirstWeight(BigDecimal.valueOf(tempRightrange
							- tempLeftrange));
				}
			}
			totalFee = totalFee.add(tempFee);

		}
		// 加上首重
		totalFee = totalFee.add(firstRate);
		// MathContext mc = new MathContext(newScale, RoundingMode.HALF_UP);
		// 分变元，两个小数点四舍五入
		totalFee = totalFee.divide(
				BigDecimal.valueOf(PricingConstants.YUTOFEN), newScale,
				BigDecimal.ROUND_HALF_UP);
		// 減去自提費用
		if (totalFee.compareTo(new BigDecimal(selfPickUpSubStract)) == 1) {
			// totalFee=totalFee.subtract(BigDecimal.valueOf(selfPickUpSubStract),
			// mc);
			totalFee = totalFee.subtract(BigDecimal
					.valueOf(selfPickUpSubStract));
		}

		compareDto.setCaculateFee(totalFee);
		compareDto.setDiscountFee(totalFee);

		ResultProductPriceDto weightDto = null;
		ResultProductPriceDto volumeDto = null;
		for (int loop = 0; loop < resultList.size(); loop++) {
			ResultProductPriceDto temp = resultList.get(loop);
			if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
					PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT)) {
				weightDto = temp;
			}
			if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
					CRITERIA_DETAIL_CACULATE_TYPE_VOLUME)) {
				volumeDto = temp;
			}
		}
		// 重货,轻货相互补充数据给予客户端展示
		if (StringUtils.equalsIgnoreCase(
				PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT,
				compareDto.getCaculateType())) {
			if(weightDto != null){
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(weightDto.getFeeRate());
				// 设置重货费率
				compareDto.setHeavyFeeRate(weightDto.getFeeRate());
				// 设置当前轻货费率
				compareDto.setLightFeeRate(weightDto.getFeeRate());
			}
		} else {
			if(volumeDto != null){
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(volumeDto.getFeeRate());
				// 设置轻货费率
				compareDto.setLightFeeRate(volumeDto.getFeeRate());
				// 设置当前重货费率
				compareDto.setHeavyFeeRate(volumeDto.getFeeRate());
			}
		}

		productPriceList.add(compareDto);
		return productPriceList;
		
		 
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/***
	 * 
	 * 根据重量、体积和明细费率计算空运费用
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 上午8:41:48
	 * 
	 * @param originnalCost 费用
	 * 
	 * @param weight 重量
	 * 
	 * @param volume 体积
	 * 
	 * @param priceValuationEntity  计费规则 
	 * 
	 * @param criteriaDetailentityList 费用明细list
	 * 
	 */
	public static List<ProductPriceDto> calculateAirCostServices(BigDecimal weight, BigDecimal volume,
			List<ResultProductPriceDto> resultList, Date receiveDate, ProductEntity productEntity,
			GoodsTypeEntity goodsTypeEntity, PriceEntity priceEntity) throws BillCaculateServiceException {
		List<ProductPriceDto> productPriceList = new ArrayList<ProductPriceDto>();
		if (volume == null) {
			volume = BigDecimal.ZERO;
		}
		BigDecimal volumeWeight = volume.multiply(FossConstants.VOLUME_TO_WEIGHT).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal volumeTemp = volumeWeight;
		BigDecimal weightTemp = weight;
		for (ResultProductPriceDto resultProductPriceDto : resultList) {
			String caculateType = resultProductPriceDto.getCaculateType();
			Double total = null;
			/* 按重量 */
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)) {
				if (null != weight) {
					double doubleWeight = weight.doubleValue();
					double tempLeftrange = resultProductPriceDto.getLeftrange();
					double tempRightrange = resultProductPriceDto.getRightrange();
					/* 筛选合适的重量范围计价区间 */
					if (doubleWeight >= tempLeftrange && doubleWeight < tempRightrange) {
						CaculateFeeDto freight = costPrepareCaculateProperty(resultProductPriceDto);

						if (volumeWeight.compareTo(weight) > 0) {
							weight = volumeWeight;
						}

						total = new Double(PriceRuleCalculator.calcFreightDetail(weight.doubleValue(), freight));
						productPriceList.add(converPriceClientData(total, resultProductPriceDto,
								caculateType, priceEntity));
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(productPriceList)) {
			for (int loop = 0; loop < productPriceList.size(); loop++) {
				ProductPriceDto compareDto = productPriceList.get(loop);
				// 设置产品信息
				compareDto.setProductCode(productEntity.getCode());
				compareDto.setProductName(productEntity.getName());
				// 设置货物信息
				if (goodsTypeEntity != null) {
					compareDto.setGoodsTypeCode(goodsTypeEntity.getCode());
					compareDto.setGoodsTypeName(goodsTypeEntity.getName());
				}
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(compareDto.getFeeRate()); 
				// 设置重货费率
				compareDto.setHeavyFeeRate(compareDto.getFeeRate()); 
				// 设置当前轻货费率
				compareDto.setLightFeeRate(compareDto.getFeeRate()); 
				compareDto.setVolumeWeight(volumeWeight);
				if (volumeTemp.compareTo(weightTemp) > 0) {
					compareDto.setCaculateType(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
				} else {
					compareDto.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
				}
			}
			return productPriceList;
		} else {
			return null;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 复制属性
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-19 上午9:25:24
	 * 
	 * @param productPriceDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private static CaculateFeeDto costPrepareCaculateProperty(ResultProductPriceDto productPriceDto) {
	    CaculateFeeDto freight=new CaculateFeeDto();
	    freight.setExpression(productPriceDto.getExperssion());
	    freight.setFeeRate(productPriceDto.getFeeRate());
	    freight.setFixPrice(productPriceDto.getFee()) ;
	    freight.setMaxFee(productPriceDto.getMaxFee());
	    freight.setMinFee(productPriceDto.getMinFee());
	    freight.setFirstWeight(productPriceDto.getLeftrange());
	    freight.setParm1(productPriceDto.getParm1());
	    freight.setParm2(productPriceDto.getParm2());
	    freight.setParm3(productPriceDto.getParm3());
	    freight.setParm4(productPriceDto.getParm4());
	    freight.setParm5(productPriceDto.getParm5());
	    return freight;
	}
	
	/**
	 * 
	 * @Description: 复制属性
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-19 上午9:25:24
	 * 
	 * @param productPriceDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private static CaculateFeeDto newCostPrepareCaculateProperty(NewResultProductPriceDto productPriceDto) {
	    CaculateFeeDto freight=new CaculateFeeDto();
	    freight.setExpression(productPriceDto.getExperssion());
	    freight.setFeeRate(productPriceDto.getFeeRate());
	    freight.setFixPrice(productPriceDto.getFee()) ;
	    freight.setMaxFee(productPriceDto.getMaxFee());
	    freight.setMinFee(productPriceDto.getMinFee());
	    freight.setFirstWeight(productPriceDto.getLeftrange());
	    freight.setParm1(productPriceDto.getParm1());
	    freight.setParm2(productPriceDto.getParm2());
	    freight.setParm3(productPriceDto.getParm3());
	    freight.setParm4(productPriceDto.getParm4());
	    freight.setParm5(productPriceDto.getParm5());
	    return freight;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * 组装运费信息dto
	 * 
	 * @param productPriceDto 暴露给外部接口使用运费服务价格计算的dto
	 * 
	 * @param finalValue 经过计算后的费用 caculateFee
	 * 
	 * @param resultProductPriceDto 运费计算明细DTO
	 * 
	 * @param caculateType  计费类别
	 * 
	 * @param receiveDate  营业收货日期
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-9 下午2:41:23
	 */
	private static ProductPriceDto converPriceClientData(Double finalValue,ResultProductPriceDto resultProductPriceDto,
		String caculateType, PriceEntity priceEntity){
		ProductPriceDto productPriceDto = new ProductPriceDto();
	    finalValue=finalValue/PricingConstants.YUTOFEN;
	    productPriceDto.setCaculateFee(new BigDecimal(String.valueOf(finalValue)));
	    Double feeRate = resultProductPriceDto.getFeeRate().doubleValue()/PricingConstants.YUTOFEN;
	    productPriceDto.setFeeRate(new BigDecimal(String.valueOf(feeRate)));
	    if(null != resultProductPriceDto.getFee())
	    {
		 productPriceDto.setFee(new BigDecimal(String.valueOf(resultProductPriceDto.getFee()/PricingConstants.YUTOFEN)));
	    }
	    if(null != resultProductPriceDto.getMinFee()){
		 productPriceDto.setMinFee(new BigDecimal(String.valueOf(resultProductPriceDto.getMinFee()/PricingConstants.YUTOFEN))); 
	    }
	    if(null != resultProductPriceDto.getMaxFee()){
		 productPriceDto.setMaxFee(new BigDecimal(String.valueOf(resultProductPriceDto.getMaxFee()/PricingConstants.YUTOFEN))); 
	    }
	    productPriceDto.setCaculateType(caculateType); 
	    productPriceDto.setPriceEntityCode(priceEntity.getCode());
	    productPriceDto.setPriceEntityName(priceEntity.getName()); 
	    productPriceDto.setFlightShiftNo(resultProductPriceDto.getFlightShift());
	    productPriceDto.setId(resultProductPriceDto.getPricingCriteriaDetailId()); 
	    productPriceDto.setCaculateExpression(resultProductPriceDto.getExperssion());
        //如果最后数据含有是否接送货，就把其封装进结果集
        productPriceDto.setCentralizePickup(resultProductPriceDto.getCentralizePickup());
	    productPriceDto.setCentralizeDelivery(resultProductPriceDto.getCentralizeDelivery());
		 
	    return productPriceDto;
	}
	/**
	 * 
	 * 组装运费信息dto（用于零担汽运新规则）
	 * 
	 * @param productPriceDto 暴露给外部接口使用运费服务价格计算的dto
	 * 
	 * @param finalValue 经过计算后的费用 caculateFee
	 * 
	 * @param resultProductPriceDto 运费计算明细DTO
	 * 
	 * @param caculateType  计费类别
	 * 
	 * @param receiveDate  营业收货日期
	 * 
	 * @author DP-Foss-liyongfei
	 * 
	 * @date 2012-11-9 下午2:41:23
	 */
	private static ProductPriceDto newConverPriceClientData(Double finalValue,NewResultProductPriceDto resultProductPriceDto,
			String caculateType, PriceEntity priceEntity){
			ProductPriceDto productPriceDto = new ProductPriceDto();
		    finalValue=finalValue/PricingConstants.YUTOFEN;
		    productPriceDto.setCaculateFee(new BigDecimal(String.valueOf(finalValue)));
		    Double feeRate = resultProductPriceDto.getFeeRate().doubleValue()/PricingConstants.YUTOFEN;
		    productPriceDto.setFeeRate(new BigDecimal(String.valueOf(feeRate)));
		    if(null != resultProductPriceDto.getFee())
		    {
			 productPriceDto.setFee(new BigDecimal(String.valueOf(resultProductPriceDto.getFee()/PricingConstants.YUTOFEN)));
		    }
		    if(null != resultProductPriceDto.getMinFee()){
			 productPriceDto.setMinFee(new BigDecimal(String.valueOf(resultProductPriceDto.getMinFee()/PricingConstants.YUTOFEN))); 
		    }
		    if(null != resultProductPriceDto.getMaxFee()){
			 productPriceDto.setMaxFee(new BigDecimal(String.valueOf(resultProductPriceDto.getMaxFee()/PricingConstants.YUTOFEN))); 
		    }
		    productPriceDto.setCaculateType(caculateType); 
		    productPriceDto.setPriceEntityCode(priceEntity.getCode());
		    productPriceDto.setPriceEntityName(priceEntity.getName()); 
		    productPriceDto.setFlightShiftNo(resultProductPriceDto.getFlightShift());
		    productPriceDto.setId(resultProductPriceDto.getPricingCriteriaDetailId()); 
		    productPriceDto.setCaculateExpression(resultProductPriceDto.getExperssion());  
		
			 
		    return productPriceDto;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 增值服务相关
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-19 上午9:27:55
	 * 
	 * @param queryBillCacilateValueAddDto
	 * 
	 * @version V1.0
	 */
	public static void checkQueryBillCacilateValueAddDtoDate(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		BigDecimal originnalCost = queryBillCacilateValueAddDto.getOriginnalCost();
		if (null == originnalCost
				&& StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(),
						PriceEntityConstants.PRICING_CODE_BF)) {
			return;
		}
		//如果是大礼包活动
		if(StringUtil.isNotEmpty(queryBillCacilateValueAddDto.getCityMarketCode())){
			// did nothing
		}else{// 如果是代收货款，只有即日退和三日退的费用。遇到不是即日退的参数，都转换为三日退
			if (StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(),
					PriceEntityConstants.PRICING_CODE_HK)) {
				if (StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getSubType(),
						DictionaryValueConstants.COD__COD_TYPE__RETURN_1_DAY)) {
					// did nothing
				} else {
					queryBillCacilateValueAddDto.setSubType(DictionaryValueConstants.COD__COD_TYPE__RETURN_3_DAY);
				}
			}
		}
		// 固定必输项目
		if (StringUtil.isEmpty(queryBillCacilateValueAddDto.getPricingEntryCode())) {
			throw new BillCaculateServiceException("费用代码不能为空!", "费用代码不能为空!");
		}
		if (StringUtil.isEmpty(queryBillCacilateValueAddDto.getOriginalOrgCode())) {
			throw new BillCaculateServiceException("出发部门编码不能为空!", "出发部门编码不能为空!");
		}
		Date receiveDate = queryBillCacilateValueAddDto.getReceiveDate();
		if (null == receiveDate) {
			queryBillCacilateValueAddDto.setReceiveDate(new Date());
		}
		/**
		 * 增加判断条件，功能不变
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-4上午11:29
		 */
		if (null == queryBillCacilateValueAddDto.getWeight()&&!(StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(),
				PriceEntityConstants.PRICING_CODE_ZQBZ))) {
			throw new BillCaculateServiceException("重量不能为空!", "重量不能为空!");
		}
		/**
		 * 增加判断条件，功能不变
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-4上午11:29
		 */
		if (null == queryBillCacilateValueAddDto.getVolume()&&!(StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(),
				PriceEntityConstants.PRICING_CODE_ZQBZ))) {
			throw new BillCaculateServiceException("体积不能为空!", "体积不能为空!");
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/***
	 * 
	 * 根据运费、重量、体积和明细费率计算实际增值服务费用
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 上午8:41:48
	 * 
	 * @param originnalCost 费用
	 * 
	 * @param weight 重量
	 * 
	 * @param volume 体积
	 * 
	 * @param priceValuationEntity  计费规则 
	 * 
	 * @param criteriaDetailentityList 费用明细list
	 * 
	 */
	public static List<ValueAddDto> calculateValueAddedServices(BigDecimal originnalCost, BigDecimal weight,
			BigDecimal volume, BigDecimal kilom, List<ResultProductPriceDto> resultList, Date receiveDate,
			String priceEntryCode) throws BillCaculateServiceException {
		List<ValueAddDto> valueList = new ArrayList<ValueAddDto>();
		ValueAddDto valueAddDto = null;
		if(resultList==null)
		{
		    return null;
		}
		for (ResultProductPriceDto resultDto : resultList) {
			Double total = null;
			String caculateType = resultDto.getCaculateType();
			//获取增值计费类型
			String valueAddCaculateType = resultDto.getValueAddCaculateType();
			boolean hasValueAdd= true;//是否有增值计费类型
			if(valueAddCaculateType==null){
				hasValueAdd = false;//没有增值计费类型
			}
			//当增值计费类型为大件上楼和纸纤包装时,没有增值计费类型
			if(StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_ZQBZ)){
				hasValueAdd = false;
			}
			//如果有增值计费类型，则根据增值计费类型对应的类型范围确定是否符合区间
			boolean flag = true;//是否落在增值计费类型区间的标示
			if (StringUtils.equalsIgnoreCase(priceEntryCode,PriceEntityConstants.PRICING_CODE_QT)
					&& weight.compareTo(BigDecimal.ZERO) == 0 
					&& volume.compareTo(BigDecimal.ZERO) == 0) {
				//如果是初始化其它费用，则不做增值计费类型范围的判断
				hasValueAdd = false;
			}else{
				// 如果是签收返单。其它费用，没有区间的说法，直接计算
				if(hasValueAdd){
					// 按重量
					if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(valueAddCaculateType)) {
						if ((weight.doubleValue() > resultDto.getValueAddLeftrange() && weight.doubleValue() <= resultDto.getValueAddRightrange()) 
								|| weight.compareTo(BigDecimal.ZERO) == 0){
							flag = true;
						}else{
							if((new BigDecimal(resultDto.getValueAddLeftrange()).compareTo(BigDecimal.ZERO) == 0 
									&& new BigDecimal(resultDto.getValueAddRightrange()).compareTo(BigDecimal.ZERO) == 0)
									&& (weight.doubleValue() >= resultDto.getLeftrange() 
									&& weight.doubleValue() <= resultDto.getRightrange())){
								flag = true;
							}else{
								flag = false;
							}
						}
					}else{//按体积计费
						if ((volume.doubleValue() > resultDto.getValueAddLeftrange() && volume.doubleValue() <= resultDto.getValueAddRightrange()) 
								|| volume.compareTo(BigDecimal.ZERO) == 0){
							flag = true;
						}else{
							if((new BigDecimal(resultDto.getValueAddLeftrange()).compareTo(BigDecimal.ZERO) == 0 
									&& new BigDecimal(resultDto.getValueAddRightrange()).compareTo(BigDecimal.ZERO) == 0)
									&& (volume.doubleValue() >= resultDto.getLeftrange()
											&& volume.doubleValue() <= resultDto.getRightrange())){
								flag = true;
							}else{
								flag = false;
							}
						}
					}
				}
			}
			
			
			
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_ORIGINALCOST.equals(caculateType)) {// 原始费用计费
				if (StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_QS)
						|| StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_QT)) {
					
					//如果没有增值计费类型或 （重量或体积）落在了增值的区间
					if(flag){
						CaculateFeeDto freight = prepareCaculateProperty(resultDto);
						total = new Double(PriceRuleCalculator.calcFreightDetail(originnalCost.doubleValue(), freight));
						valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
						valueAddDto.setValueAddCaculateType(valueAddCaculateType);
						valueList.add(valueAddDto);
					}
					

				} else {
					if(StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_HK)){
						//代收金额范围校验
						if ((originnalCost.longValue() >(resultDto.getLeftrange())
								&& originnalCost.longValue() <= (resultDto.getRightrange()))
								|| (originnalCost.longValue() == 0
										&& originnalCost.longValue() == (resultDto.getLeftrange())
										&& originnalCost.longValue() <= (resultDto.getRightrange()))) {
							if(flag){//按重量或按体积范围校验
								CaculateFeeDto freight = prepareCaculateProperty(resultDto);
								total = new Double(PriceRuleCalculator.calcFreightDetail(originnalCost.doubleValue(), freight));
								valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
								valueAddDto.setValueAddCaculateType(valueAddCaculateType);
								valueList.add(valueAddDto);	
							}
							
						}
					}else if(StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_ZQBZ)){
						/**
						 * 如果是纸纤包装，是为了获取CRITERIA_DETAIL_ID，则直接获取,纸纤费用在客户端计算
						 * @author:218371-foss-zhaoyanjun
						 * @date:2014-12-3下午15:53
						 */						
						valueAddDto = converValueaddClientData(0D, resultDto, caculateType, receiveDate);
						valueAddDto.setValueAddCaculateType(valueAddCaculateType);
						valueList.add(valueAddDto);
					}else{
						if ((originnalCost.longValue() > (resultDto.getLeftrange() * PricingConstants.YUTOFEN)
								&& originnalCost.longValue() <= (resultDto.getRightrange() * PricingConstants.YUTOFEN))
								|| (originnalCost.longValue() == 0
										&& originnalCost.longValue() == (resultDto.getLeftrange() * PricingConstants.YUTOFEN)
										&& originnalCost.longValue() <= (resultDto.getRightrange() * PricingConstants.YUTOFEN))) {
							if (StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_BF)) {
								// 需要确认保费是否需最大值,暂时置为最大
								resultDto.setMaxFee(PricingConstants.CRITERIA_DETAIL_ORIGINALCOST_MAX);
							}
							if(flag){//按重量或按体积范围校验
								CaculateFeeDto freight = prepareCaculateProperty(resultDto);
								total = new Double(PriceRuleCalculator.calcFreightDetail(originnalCost.doubleValue(), freight));
								valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
								valueAddDto.setValueAddCaculateType(valueAddCaculateType);
								if (StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_BF)) {
									valueAddDto.setDefaultBF(new BigDecimal(String.valueOf(new Double(originnalCost
											.doubleValue() / PricingConstants.YUTOFEN))));
								}
								valueList.add(valueAddDto);
							}
							
						}
					}
					
				}

			}
			
			// 按重量
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)) {
				//定价优化，送货，送货上楼，只判断是否落在增值计费类型对应的范围
				if (StringUtils.equalsIgnoreCase(priceEntryCode,
						PriceEntityConstants.PRICING_CODE_SH)
						|| StringUtils.equalsIgnoreCase(priceEntryCode,
								PriceEntityConstants.PRICING_CODE_SHSL)
						|| StringUtils.equalsIgnoreCase(priceEntryCode,
								PriceEntityConstants.PRICING_CODE_BZ)
						||StringUtils.equalsIgnoreCase(priceEntryCode, 
								PriceEntityConstants.PRICING_CODE_DJSL)) {
					if(flag){
						double number = 0.0;
						//如果是包装费,若是木托，则个数计算，volume存的是木托的个数,其他的则根据增值计费类型，按重量就重量乘费率，按体积则体积乘费率
						if (StringUtils.equalsIgnoreCase(priceEntryCode,
								PriceEntityConstants.PRICING_CODE_BZ)
								//修复pop-337
//								&& StringUtils.equalsIgnoreCase(
//										resultDto.getSubType(), "SALVER")
								) {
							number = volume.doubleValue();
						}else{
							//如果增值计费类型是按重量
							if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(valueAddCaculateType)){
								number = weight.doubleValue();
							}
							else{
								number = volume.doubleValue();
							}
						}
						CaculateFeeDto freight = prepareCaculateProperty(resultDto);
						//当服务类型为包装费的木托时，调用按个计算的方法...里面的最高和最低都乘以了个数
						if(StringUtils.equalsIgnoreCase(priceEntryCode,
								PriceEntityConstants.PRICING_CODE_BZ) && StringUtils.equalsIgnoreCase(
										resultDto.getSubType(), "SALVER")){
							total = Double.valueOf(PriceRuleCalculator.calcFreightDetailForSingleton(number, freight));
						}else{
							total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(number, freight));
						}
						valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
						valueAddDto.setValueAddCaculateType(valueAddCaculateType);
						valueList.add(valueAddDto);
					}
				}else{
					// 只对符合的重量范围才进行计算
					if ((weight.doubleValue() > resultDto.getValueAddLeftrange()
							&& weight.doubleValue() <= resultDto.getValueAddRightrange())
							|| (weight.doubleValue() > resultDto.getLeftrange()
							&& weight.doubleValue() <= resultDto.getRightrange())) {
						if(flag){
							CaculateFeeDto freight = prepareCaculateProperty(resultDto);
							//接货费 按每票/元取值
							if(PriceEntityConstants.PRICING_CODE_JH.equals(priceEntryCode)){
								//当存在接货费的固定费用即合同客户的默认接货费时，获取默认接货费
								if(null != resultDto.getFee()){
									total = resultDto.getFee().doubleValue();
								}else{
									if(null != resultDto.getFeeRate()){
										total= resultDto.getFeeRate().doubleValue();
									}
									//Online-定价体系优化项目DJTWO-246
									if(resultDto.getMinFee() != null && new BigDecimal(resultDto.getMinFee()).compareTo(resultDto.getFeeRate()) > 0){
										total= resultDto.getMinFee().doubleValue();
									}
									if(resultDto.getMaxFee() != null && new BigDecimal(resultDto.getMaxFee()).compareTo(resultDto.getFeeRate()) < 0){
										total= resultDto.getMaxFee().doubleValue();
									}
								}
							}else{
								total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(weight.doubleValue(), freight));
							}
							valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
							valueAddDto.setValueAddCaculateType(valueAddCaculateType);
							valueList.add(valueAddDto);
						}
					}
				}
				
				
			}
			// 按体积
			if (CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)) {
				//定价优化，送货，送货上楼，只判断是否落在增值计费类型对应的范围
				if (StringUtils.equalsIgnoreCase(priceEntryCode,
						PriceEntityConstants.PRICING_CODE_SH)
						|| StringUtils.equalsIgnoreCase(priceEntryCode,
								PriceEntityConstants.PRICING_CODE_SHSL)
						|| StringUtils.equalsIgnoreCase(priceEntryCode,
								PriceEntityConstants.PRICING_CODE_BZ)
						||StringUtils.equalsIgnoreCase(priceEntryCode, 
								PriceEntityConstants.PRICING_CODE_DJSL)) {
					if(flag){
						double number = 0.0;
						//如果是包装费,若是木托，则个数计算，volume存的是木托的个数,其他的则根据增值计费类型，按重量就重量乘费率，按体积则体积乘费率
						if (StringUtils.equalsIgnoreCase(priceEntryCode,
								PriceEntityConstants.PRICING_CODE_BZ)
								//修复pop-337
//								&& StringUtils.equalsIgnoreCase(
//										resultDto.getSubType(), "SALVER")
										) {
							number = volume.doubleValue();
						}else{
							//如果增值计费类型是按重量
							if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(valueAddCaculateType)){
								number = weight.doubleValue();
							}
							else{
								number = volume.doubleValue();
							}
						}
						CaculateFeeDto freight = prepareCaculateProperty(resultDto);
						//当服务类型为包装费的木托时，调用按个计算的方法...里面的最高和最低都乘以了个数
						if(StringUtils.equalsIgnoreCase(priceEntryCode,
								PriceEntityConstants.PRICING_CODE_BZ) && StringUtils.equalsIgnoreCase(
										resultDto.getSubType(), "SALVER")){
							total = Double.valueOf(PriceRuleCalculator.calcFreightDetailForSingleton(number, freight));
						}else{
							total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(number, freight));
						}
						valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
						valueAddDto.setValueAddCaculateType(valueAddCaculateType);
						valueList.add(valueAddDto);
					}
				}else{
					// 只对符合的体积范围才进行计算
					if ((volume.doubleValue() > resultDto.getValueAddLeftrange()
							&& volume.doubleValue() <= resultDto.getValueAddRightrange())
							|| (volume.doubleValue() > resultDto.getLeftrange()
							&& volume.doubleValue() <= resultDto.getRightrange())) {
						if(flag){
							CaculateFeeDto freight = prepareCaculateProperty(resultDto);
							//接货费 按每票/元取值
							if(PriceEntityConstants.PRICING_CODE_JH.equals(priceEntryCode)){
								//当存在接货费的固定费用即合同客户的默认接货费时，获取默认接货费
								if(null != resultDto.getFee()){
									total = resultDto.getFee().doubleValue();
								}else{
									if(null != resultDto.getFeeRate()){
										total= resultDto.getFeeRate().doubleValue();
									}
									//Online-定价体系优化项目DJTWO-246
									if(resultDto.getMinFee() != null && new BigDecimal(resultDto.getMinFee()).compareTo(resultDto.getFeeRate()) > 0){
										total= resultDto.getMinFee().doubleValue();
									}
									if(resultDto.getMaxFee() != null && new BigDecimal(resultDto.getMaxFee()).compareTo(resultDto.getFeeRate()) < 0){
										total= resultDto.getMaxFee().doubleValue();
									}
								}
							}else{
								total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(volume.doubleValue(), freight));
							}
							valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
							valueAddDto.setValueAddCaculateType(valueAddCaculateType);
							valueList.add(valueAddDto);
						}
						
					}
				}
			}
			// 按公里
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_KILOM.equals(caculateType)) {
				// 只对符合的公里范围才进行计算
				if ((kilom.doubleValue() > resultDto.getLeftrange() && kilom.doubleValue() <= resultDto.getRightrange())
						||(kilom.doubleValue() > resultDto.getValueAddLeftrange() && kilom.doubleValue() <= resultDto.getValueAddRightrange())) {
					if(flag){
						CaculateFeeDto freight = prepareCaculateProperty(resultDto);
						total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(kilom.doubleValue(), freight));
						valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
						valueAddDto.setValueAddCaculateType(valueAddCaculateType);
						valueList.add(valueAddDto);
					}
					
				}
			}
			
		}
		
		/**
		 * 由于增值服务中接货费的最大值最小值是按重量来算的，故此处还需要做下微调
		 * 时间：2014-03-25
		 * 内容：MANA-257接货费优化
		 * 作者：026123
		 */
		if(PriceEntityConstants.PRICING_CODE_JH.equals(priceEntryCode)){
			if(CollectionUtils.isNotEmpty(valueList)){
				//存储按重量计费的最大值
				BigDecimal weightMaxFee = null;
				for (ValueAddDto dto : valueList) {
					// 按重量
					if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(dto.getValueAddCaculateType())) {
						weightMaxFee = dto.getMaxFee();
					}
				}
				
				//将按重量计费的最大值赋值给按体积计费的最大值
				for (ValueAddDto dto : valueList) {
					// 按重量
					if (CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(dto.getValueAddCaculateType())) {
						dto.setMaxFee(weightMaxFee);
					}
				}
			}
		}
		
		List<ValueAddDto> valueAddDtoList = new ArrayList<ValueAddDto>();
		// 特殊处理重货轻货费用，计费类型不一致
		if (valueList.size() > 1
				&& (!StringUtils.equalsIgnoreCase(valueList.get(0)
						.getCaculateType(), valueList.get(1).getCaculateType()) || !StringUtils
						.equalsIgnoreCase(valueList.get(0).getValueAddCaculateType(),
								valueList.get(1).getValueAddCaculateType()))) {
			ValueAddDto weightDto = null;
			ValueAddDto volumeDto = null;
			for (int loop = 0; loop < valueList.size(); loop++) {
				ValueAddDto temp = valueList.get(loop);
				if (StringUtils.equalsIgnoreCase(temp.getValueAddCaculateType(),
								PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT)) {
					weightDto = temp;
				}
				if (StringUtils.equalsIgnoreCase(temp.getValueAddCaculateType(),
								CRITERIA_DETAIL_CACULATE_TYPE_VOLUME)) {
					volumeDto = temp;
				}
			}
			ValueAddDto compareDto=null;
			//定价体系优化项目POP-547
			BigDecimal lightFeeRate = BigDecimal.ZERO;
			BigDecimal heavyFeeRate = BigDecimal.ZERO;
			if(null!=weightDto && null!=weightDto.getCaculateFee() && null!=volumeDto && null!=volumeDto.getCaculateFee()){
				compareDto = weightDto.getCaculateFee().doubleValue() >= volumeDto.getCaculateFee().doubleValue() ? weightDto
				: volumeDto;
			}else if(null!=weightDto && null!=weightDto.getCaculateFee() &&null==volumeDto){
				compareDto = weightDto;
				heavyFeeRate = weightDto.getFeeRate();
			}else if(null!=volumeDto && null!=volumeDto.getCaculateFee() &&null==weightDto){
				compareDto = volumeDto;
				lightFeeRate = volumeDto.getFeeRate();
			}
			
			// 重货,轻货相互补充数据给予客户端展示
			if(null!=compareDto){
				 if (StringUtils.equalsIgnoreCase(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME,
				compareDto.getCaculateType()) 
				|| StringUtils.equalsIgnoreCase(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME,
				compareDto.getValueAddCaculateType())) {
					// 设置最终实际计算的费率
					compareDto.setActualFeeRate(compareDto.getFeeRate()); 
					// 设置重货费率
					compareDto.setHeavyFeeRate(heavyFeeRate); 
					// 设置当前轻货费率
					compareDto.setLightFeeRate(lightFeeRate); 
				} else {
					// 设置最终实际计算的费率
					compareDto.setActualFeeRate(compareDto.getFeeRate()); 
					// 设置轻货费率
					compareDto.setLightFeeRate(lightFeeRate); 
					// 设置当前重货费率
					compareDto.setHeavyFeeRate(heavyFeeRate); 
				}
				valueAddDtoList.add(compareDto);
			}
		} else {
			return valueList;
		}
		return valueAddDtoList;
	}
	
	public static List<ValueAddDto> calculateValueAddedServicesPic(List<ResultProductPriceDto> resultList) throws BillCaculateServiceException {
		List<ValueAddDto> valueAddDtoList = new ArrayList<ValueAddDto>();
		for (ResultProductPriceDto resultDto : resultList) {
			ValueAddDto valueAddDto = new ValueAddDto();
			if(null == resultDto.getFee()){
				valueAddDto.setFee(BigDecimal.ZERO);
				valueAddDto.setCaculateFee(BigDecimal.ZERO);
			}else{
				valueAddDto.setFee(new BigDecimal(resultDto.getFee()));
				valueAddDto.setCaculateFee(new BigDecimal(resultDto.getFee()));
			}
//			valueAddDto.setFee(new BigDecimal(String.valueOf(resultDto.getFee())));
			valueAddDto.setCaculateExpression(resultDto.getExperssion());
			valueAddDto.setSubType(resultDto.getSubType());
			valueAddDto.setPriceEntityName(resultDto.getPriceEntityName());
			valueAddDto.setPriceEntityCode(resultDto.getPricingEntryCode());
			valueAddDto.setCanmodify(resultDto.getCanModify());
			valueAddDto.setCandelete(resultDto.getCanDelete());
			if (null == resultDto.getMaxFee()) {
				resultDto.setMaxFee(NumberUtils.LONG_ZERO);
			}
			if (null == resultDto.getMinFee()) {
				resultDto.setMinFee(NumberUtils.LONG_ZERO);
			}
			valueAddDto.setMaxFee(new BigDecimal(new Double(String.valueOf(resultDto.getMaxFee()
					/ PricingConstants.YUTOFEN))));
			valueAddDto.setMinFee(new BigDecimal(new Double(String.valueOf(resultDto.getMinFee()
					/ PricingConstants.YUTOFEN))));
			if (resultDto.getFee() != null) {
				valueAddDto.setFee(new BigDecimal(new Double(String.valueOf(resultDto.getFee()
						/ PricingConstants.YUTOFEN))));
			}
			valueAddDto.setProductCode(resultDto.getProductCode());
			valueAddDto.setGoodsTypeCode(resultDto.getGoodsTypeCode());
			valueAddDto.setId(resultDto.getPricingCriteriaDetailId());
			valueAddDto.setFeeRate(resultDto.getFeeRate());
			valueAddDto.setActualFeeRate(resultDto.getFeeRate());
			// 添加最低和最高费率
			valueAddDto.setMinFeeRate(resultDto.getMinFeeRate());
			valueAddDto.setMaxFeeRate(resultDto.getMaxFeeRate());
			//zxy 20131118 KDTE-5995 添加：描述Description
			valueAddDto.setDescription(resultDto.getDescription());
			valueAddDtoList.add(valueAddDto);
		}
		return valueAddDtoList;
	}
	/***
	 * 
	 * 根据运费、重量、体积和明细费率计算实际增值服务费用
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 上午8:41:48
	 * 
	 * @param originnalCost 费用
	 * 
	 * @param weight 重量
	 * 
	 * @param volume 体积
	 * 
	 * @param priceValuationEntity  计费规则 
	 * 
	 * @param criteriaDetailentityList 费用明细list
	 * 
	 */
	public static List<ValueAddDto> calculateExpressValueAddedServices(BigDecimal originnalCost, BigDecimal weight,
			BigDecimal volume, BigDecimal kilom, List<ResultProductPriceDto> resultList, Date receiveDate,
			String priceEntryCode) throws BillCaculateServiceException {
		List<ValueAddDto> valueList = new ArrayList<ValueAddDto>();
		ValueAddDto valueAddDto = null;
		if(resultList==null)
		{
		    return null;
		}
		for (ResultProductPriceDto resultDto : resultList) {
			String caculateType = resultDto.getCaculateType();
			Double total = null;
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_ORIGINALCOST.equals(caculateType)) {// 原始费用计费
				if (StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_QS)
						|| StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_QT)) {
					// 如果是签收返单。其它费用，没有区间的说法，直接计算
					CaculateFeeDto freight = prepareCaculateProperty(resultDto);
					total = new Double(PriceRuleCalculator.calcFreightDetail(originnalCost.doubleValue(), freight));
					valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
					valueList.add(valueAddDto);

				} else {
					if(StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_HK)){
						if ((originnalCost.doubleValue() > (resultDto.getLeftrange())
								&& originnalCost.doubleValue() <= (resultDto.getRightrange()))
								|| (originnalCost.compareTo(BigDecimal.ZERO) == 0
										&& originnalCost.compareTo(BigDecimal.valueOf(resultDto.getLeftrange())) == 0
										&& originnalCost.doubleValue() <= (resultDto.getRightrange() ))) {
							CaculateFeeDto freight = prepareCaculateProperty(resultDto);
							total = new Double(PriceRuleCalculator.calcFreightDetail(originnalCost.doubleValue(), freight));
							valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);
							valueList.add(valueAddDto);
						}
					}else{
						if ((originnalCost.doubleValue() > (resultDto.getLeftrange() )
								&& originnalCost.doubleValue() <= (resultDto.getRightrange()))
								|| (originnalCost.compareTo(BigDecimal.ZERO) == 0
										&& originnalCost.compareTo(BigDecimal.valueOf(resultDto.getLeftrange())) == 0
										&& originnalCost.doubleValue() <= resultDto.getRightrange())) {
							if (StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_BF)) {
								// 需要确认保费是否需最大值,暂时置为最大
								resultDto.setMaxFee(PricingConstants.CRITERIA_DETAIL_ORIGINALCOST_MAX);
							}
							CaculateFeeDto freight = prepareCaculateProperty(resultDto);
							total = new Double(PriceRuleCalculator.calcFreightDetail(originnalCost.doubleValue(), freight));
							valueAddDto = converValueaddClientData(total, resultDto, caculateType, receiveDate);

							if (StringUtils.equalsIgnoreCase(priceEntryCode, PriceEntityConstants.PRICING_CODE_BF)) {
								valueAddDto.setDefaultBF(new BigDecimal(String.valueOf(new Double(originnalCost
										.doubleValue() / PricingConstants.YUTOFEN))));
							}
							valueList.add(valueAddDto);
						}
					}
					
				}

			}
			// 按重量
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)) {
				// 只对符合的重量范围才进行计算
				if (weight.doubleValue() >= resultDto.getLeftrange()
						&& weight.doubleValue() < resultDto.getRightrange()) {
					CaculateFeeDto freight = prepareCaculateProperty(resultDto);
					total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(weight.doubleValue(), freight));
					valueList.add(converValueaddClientData(total, resultDto, caculateType, receiveDate));
				}
			}
			// 按体积
			if (CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)) {
				// 只对符合的体积范围才进行计算
				if (volume.doubleValue() >= resultDto.getLeftrange()
						&& volume.doubleValue() < resultDto.getRightrange()) {
					CaculateFeeDto freight = prepareCaculateProperty(resultDto);
					total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(volume.doubleValue(), freight));
					valueList.add(converValueaddClientData(total, resultDto, caculateType, receiveDate));
				}
			}
			// 按公里
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_KILOM.equals(caculateType)) {
				// 只对符合的公里范围才进行计算
				if (kilom.doubleValue() >= resultDto.getLeftrange() && kilom.doubleValue() < resultDto.getRightrange()) {
					CaculateFeeDto freight = prepareCaculateProperty(resultDto);
					total = Double.valueOf(PriceRuleCalculator.calcFreightDetail(kilom.doubleValue(), freight));
					valueList.add(converValueaddClientData(total, resultDto, caculateType, receiveDate));
				}
			}
		}
		List<ValueAddDto> valueAddDtoList = new ArrayList<ValueAddDto>();
		// 特殊处理重货轻货费用，计费类型不一致
		if (valueList.size() > 1 
				&& !StringUtils.equalsIgnoreCase(valueList.get(0).getCaculateType(), valueList.get(1).getCaculateType())) {
			ValueAddDto weightDto = null;
			ValueAddDto volumeDto = null;
			for (int loop = 0; loop < valueList.size(); loop++) {
				ValueAddDto temp = valueList.get(loop);
				if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
						PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT)) {
					weightDto = temp;
				}
				if (StringUtils.equalsIgnoreCase(temp.getCaculateType(),
						CRITERIA_DETAIL_CACULATE_TYPE_VOLUME)) {
					volumeDto = temp;
				}
			}
			ValueAddDto compareDto;
			compareDto = weightDto.getCaculateFee().doubleValue() > volumeDto.getCaculateFee().doubleValue() ? weightDto
					: volumeDto;
			// 重货,轻货相互补充数据给予客户端展示
			if (StringUtils.equalsIgnoreCase(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME,
					compareDto.getCaculateType())) {
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(compareDto.getFeeRate()); 
				// 设置重货费率
				compareDto.setHeavyFeeRate(weightDto.getFeeRate()); 
				// 设置当前轻货费率
				compareDto.setLightFeeRate(volumeDto.getFeeRate()); 
			} else {
				// 设置最终实际计算的费率
				compareDto.setActualFeeRate(compareDto.getFeeRate()); 
				// 设置轻货费率
				compareDto.setLightFeeRate(volumeDto.getFeeRate()); 
				// 设置当前重货费率
				compareDto.setHeavyFeeRate(weightDto.getFeeRate()); 
			}
			valueAddDtoList.add(compareDto);
		} else {
			return valueList;
		}
		return valueAddDtoList;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * 组装增值服务dto
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @param valueAddDto 暴露给外部接口使用增值服务价格计算的dto
	 * 
	 * @param total  经过计算后的费用 caculateFee
	 * 
	 * @param priceCriteriaDetailBean 计价明细实体
	 * 
	 * @param priceValuationEntity 计费规则实体
	 * 
	 * @param caculateType 计费类型
	 * 
	 * @param receiveDate 营业收货日期
	 * 
	 * @date 2012-11-9 下午2:12:29
	 */
	private static ValueAddDto converValueaddClientData(Double total,
			ResultProductPriceDto priceCriteriaDetailBean, String caculateType, Date receiveDate) {
		total = total / PricingConstants.YUTOFEN;
		ValueAddDto valueAddDto = new ValueAddDto();
		valueAddDto.setCaculateFee(new BigDecimal(String.valueOf(total)));
		if (null == priceCriteriaDetailBean.getFee()) {
			priceCriteriaDetailBean.setFee(NumberUtils.LONG_ZERO);
		}
		valueAddDto.setFee(new BigDecimal(String.valueOf(priceCriteriaDetailBean.getFee())));
		valueAddDto.setCaculateExpression(priceCriteriaDetailBean.getExperssion());
		valueAddDto.setCaculateType(caculateType);
		valueAddDto.setSubType(priceCriteriaDetailBean.getSubType());

		valueAddDto.setPriceEntityName(priceCriteriaDetailBean.getPriceEntityName());
		valueAddDto.setPriceEntityCode(priceCriteriaDetailBean.getPricingEntryCode());
		
		valueAddDto.setCanmodify(priceCriteriaDetailBean.getCanModify());
		valueAddDto.setCandelete(priceCriteriaDetailBean.getCanDelete());
		if (null == priceCriteriaDetailBean.getMaxFee()) {
			priceCriteriaDetailBean.setMaxFee(NumberUtils.LONG_ZERO);
		}
		if (null == priceCriteriaDetailBean.getMinFee()) {
			priceCriteriaDetailBean.setMinFee(NumberUtils.LONG_ZERO);
		}
		valueAddDto.setMaxFee(new BigDecimal(new Double(String.valueOf(priceCriteriaDetailBean.getMaxFee()
				/ PricingConstants.YUTOFEN))));
		valueAddDto.setMinFee(new BigDecimal(new Double(String.valueOf(priceCriteriaDetailBean.getMinFee()
				/ PricingConstants.YUTOFEN))));
		if (priceCriteriaDetailBean.getFee() != null) {
			valueAddDto.setFee(new BigDecimal(new Double(String.valueOf(priceCriteriaDetailBean.getFee()
					/ PricingConstants.YUTOFEN))));
		}
		valueAddDto.setProductCode(priceCriteriaDetailBean.getProductCode());
		valueAddDto.setGoodsTypeCode(priceCriteriaDetailBean.getGoodsTypeCode());
		valueAddDto.setId(priceCriteriaDetailBean.getPricingCriteriaDetailId());
		valueAddDto.setFeeRate(priceCriteriaDetailBean.getFeeRate());
		valueAddDto.setActualFeeRate(priceCriteriaDetailBean.getFeeRate());
		// 添加最低和最高费率
		valueAddDto.setMinFeeRate(priceCriteriaDetailBean.getMinFeeRate());
		valueAddDto.setMaxFeeRate(priceCriteriaDetailBean.getMaxFeeRate());
		//zxy 20131118 KDTE-5995 添加：描述Description
		valueAddDto.setDescription(priceCriteriaDetailBean.getDescription());
		
		/**
		* 时间：2014-02-27
		* 内容：MANA-257接货费优化
		* 作者：026123
		*/
		//若增值服务费的类型是接货，则直接返回最大值和最小值，费用值默认为最小值（将原值覆盖掉）
		if(PriceEntityConstants.PRICING_CODE_JH.equals(priceCriteriaDetailBean.getPricingEntryCode())){
			valueAddDto.setFee(null);
		}
		
		return valueAddDto;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 	
	 * @Description: 复制数据
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-19 上午9:32:16
	 * 
	 * @param resultCriteriaDetailBean
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private static CaculateFeeDto prepareCaculateProperty(ResultProductPriceDto resultCriteriaDetailBean) {
	    CaculateFeeDto freight = new CaculateFeeDto();
	    freight.setDiscountRate(resultCriteriaDetailBean.getDiscountRate()); 
	    freight.setExpression(resultCriteriaDetailBean.getExperssion());
	    freight.setFeeRate(resultCriteriaDetailBean.getFeeRate());
	    freight.setFixPrice(resultCriteriaDetailBean.getFee()) ;
	    freight.setMaxFee(resultCriteriaDetailBean.getMaxFee());
	    freight.setMinFee(resultCriteriaDetailBean.getMinFee());
	    freight.setParm1(resultCriteriaDetailBean.getParm1());
	    freight.setParm2(resultCriteriaDetailBean.getParm2());
	    freight.setParm3(resultCriteriaDetailBean.getParm3());
	    freight.setParm4(resultCriteriaDetailBean.getParm4());
	    freight.setParm5(resultCriteriaDetailBean.getParm5());
	    return freight;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 获取第一级计价条目
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-19 上午9:34:08
	 * 
	 * @param entryCode
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public static String getFirstLevelEntryCode(String entryCode) {
		String parentEntryCode = null;
		if (StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, entryCode)) {
			// 运费
			parentEntryCode = PriceEntityConstants.PRICING_CODE_FRT;
		} else {
			// 对应不上，按运费处理
			parentEntryCode = PriceEntityConstants.PRICING_CODE_VALUEADDED;
		}
		return parentEntryCode;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 计算客户折扣
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-19 上午9:34:18
	 * 
	 * @param discountResult
	 * 
	 * @param parm
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public static DiscountResultDto calculateCustomDiscountClientData(DiscountResultDto discountResult, DiscountParmDto parm, boolean isExpress) {
		//基本价格
		BigDecimal discountValue = parm.getOriginnalCost();
		//折扣率
		BigDecimal discountRate = discountResult.getDiscountRate();
		
		/**
		* @功能：
		* (1)MANA-257接货费优化(026123)
		*/
		//若增值服务费的类型是接货，则设置减免为1（主要为了让接货费不打折，在些需求中该接货率的值只代表crm过来接货费的值）
		if(PriceEntityConstants.PRICING_CODE_JH.equals(parm.getPricingEntryCode())){
			//覆盖折扣率
			discountRate = BigDecimal.ONE;
			//将原价格变为CRM接货费
			discountValue = discountResult.getDiscountRate();
		}
		//减免折扣率
		BigDecimal reduceRate = null;
		//减免价格
		BigDecimal reduceValue = null;
		if(discountRate != null){
			//减免折扣率
			reduceRate = BigDecimal.ONE.subtract(discountRate);
			//减免价格
			reduceValue = parm.getOriginnalCost().multiply(reduceRate);
			//减免后价格
			discountValue = discountValue.subtract(reduceValue);
			discountResult.setDiscountValue(discountValue);
		}else{
			//快递开单界面计算总运费报错
			discountRate=BigDecimal.ONE;
		}
		
		
		//减免方式
		discountResult.setType("DISCOUNT");
		//客户编号
		discountResult.setId(parm.getCustomCode());
		//组装返回的数据模型
		PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
		//设置原始费用
		priceDiscountDto.setOriginnalCost(parm.getOriginnalCost());
		//组装计费类型
		priceDiscountDto.setMarketName(parm.getPricingEntryName());
		//组装打折类型CODE
		priceDiscountDto.setSaleChannelCode(DiscountTypeConstants.DISCOUNT_TYPE__CONTRACT_NORMAL);
		//组装打折类型NAME
		priceDiscountDto.setSaleChannelName(DiscountTypeConstants.DISCOUNT_TYPE__CONTRACT_NORMAL_NAME);
		//组装折扣率
		priceDiscountDto.setDiscountRate(discountRate);
		//组装计价明细ID
		priceDiscountDto.setChargeDetailId(parm.getCriteriaDetailId());
		if(DiscountTypeConstants.DISCOUNT_TYPE__FRT_GRADESREBATE.equals(discountResult.getContractType())) {
			priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__FRT_GRADESREBATE);
			priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__FRT_GRADESREBATE_NAME);
			/**
			 * 以后有问题放开
			 */
			//discountResult.setContractType(DiscountTypeConstants.DISCOUNT_TYPE__CONTRACT_NORMAL);
		} else {
		//组装返回的折扣模型
		priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT);
		//组装返回的折扣模型
		priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT_NAME);
		}
		
		//组装计费类型CODE
		priceDiscountDto.setPriceEntryCode(parm.getPricingEntryCode());
		priceDiscountDto.setPriceEntryName(parm.getPricingEntryName());
		//组装计费子类型
		priceDiscountDto.setSubType(parm.getSubType());
		//修复超重货服务费子类型不显示问题
		if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(priceDiscountDto.getSubType())){
			priceDiscountDto.setSubTypeName(parm.getPricingEntryName());
		}
		/**
		 * @BUG号：DEFECT-8626
		 * @功能：获取“送货进仓”子类型不显示问题
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-05-26
		 */
		if(StringUtils.equals(PriceEntityConstants.PRICING_CODE_SHJC, priceDiscountDto.getSubType())){
			priceDiscountDto.setSubTypeName(parm.getPricingEntryName());
		}
		//组装用户合同折扣ID
		priceDiscountDto.setDiscountId(discountResult.getId());		
		//公用的，放在最前面
		priceDiscountDto.setReduceFee(reduceValue);
		
		/**
		* @需求：DN201503240002
		* @功能：
		* (1)MANA-257接货费优化(026123)
		* (2)送货进仓折扣不考虑最低一票
		* @author:218371-foss-zhaoyanjun
		* @date:2015-05-07下午16:50
		*/
		//若增值服务费的类型是接货，则直接跳过最低一票的判断，因为对于接货费不适用
		if(!PriceEntityConstants.PRICING_CODE_JH.equals(parm.getPricingEntryCode())
				/**
				 * @BUG号：DEFECT-8626
				 * @功能：“送货进仓”折扣不考虑最低一票
				 * @author:218371-foss-zhaoyanjun
				 * @date:2015-05-26
				 */
				&&!PriceEntityConstants.PRICING_CODE_SHJC.equals(parm.getSubType())){
			//根据合同类型来判断
			//如果普通合同则折扣后价格应高于最低一票
			//如果月发越送合同则折扣后价格可以低于最低一票
			String contractType = discountResult.getContractType();
			if(StringUtils.equals(contractType, DiscountTypeConstants.DISCOUNT_TYPE__MONTH_SEND)) {
				//月发越送直接打折
				log.debug("月发越送折扣可以高于最低一票");
				priceDiscountDto.setReduceFee(reduceValue);
				priceDiscountDto.setSaleChannelCode(DiscountTypeConstants.DISCOUNT_TYPE__MONTH_SEND);
				priceDiscountDto.setSaleChannelName(DiscountTypeConstants.DISCOUNT_TYPE__MONTH_SEND_NAME);
			} else {
				if(isExpress){
					//DMANA-3793 快递代收货款最低一票收费标准需求 要求代收货款手续费打折之后不能低于最低一票的值
					if(PriceEntityConstants.PRICING_CODE_HK.equals(parm.getPricingEntryCode())){
						//当存在合同客户的单票手续费时，以单票手续费为主
						if(discountResult.getSinTicketCharge() != null){
							//单票手续费
							discountResult.setDiscountValue(discountResult.getSinTicketCharge());
							priceDiscountDto.setSaleChannelName("单票手续费");
							priceDiscountDto.setDiscountRate(discountResult.getSinTicketCharge().divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
							priceDiscountDto.setReduceFee(parm.getOriginnalCost().subtract(discountResult.getSinTicketCharge()));
						}else{
							//当存在合同客户的最低代收手续费时，优先于合同为主
							if(discountResult.getLowestCharge() != null){
								if(discountValue.compareTo(discountResult.getLowestCharge()) < 0) {
									log.debug("折扣价格低于合同客户最低代收手续费");
									reduceValue = parm.getOriginnalCost().subtract(discountResult.getLowestCharge());
									if(reduceValue.compareTo(BigDecimal.ZERO) < 0){
										reduceValue = BigDecimal.ZERO;
									}
									//最低一票
									discountResult.setDiscountValue(discountResult.getLowestCharge());
									priceDiscountDto.setSaleChannelName("最低一票");
									priceDiscountDto.setDiscountRate(discountResult.getLowestCharge().divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
									priceDiscountDto.setReduceFee(reduceValue);
								}
							}else{
								if(discountValue.doubleValue() >= parm.getMinFee().doubleValue()) {
									log.debug("折扣价格高于最低一票");
									priceDiscountDto.setReduceFee(reduceValue);
								}else{
									
									log.debug("折扣价格低于最低一票");
									reduceValue = parm.getOriginnalCost().subtract(parm.getMinFee());
									//最低一票
									discountResult.setDiscountValue(parm.getMinFee());
									priceDiscountDto.setSaleChannelName("最低一票");
									priceDiscountDto.setDiscountRate(parm.getMinFee().divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
									priceDiscountDto.setReduceFee(reduceValue);
								}
							}
						}
						
					}else if(PriceEntityConstants.PRICING_CODE_BF.equals(parm.getPricingEntryCode())){
						//当存在合同客户的单票手续费时，以单票手续费为主
						if(discountResult.getSinTicketCharge() != null){
							//单票手续费
							discountResult.setDiscountValue(discountResult.getSinTicketCharge());
							priceDiscountDto.setSaleChannelName("单票手续费");
							priceDiscountDto.setDiscountRate(discountResult.getSinTicketCharge().divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
							priceDiscountDto.setReduceFee(parm.getOriginnalCost().subtract(discountResult.getSinTicketCharge()));
						}else{
							priceDiscountDto.setReduceFee(reduceValue);
						}
					}else{
						priceDiscountDto.setReduceFee(reduceValue);
					}
					//判断折扣后的价格是否低于最低一票，如果折扣价低于最低一票，则以最低一票为最终计算价格(排除包装类型为木托的情况)
				} else if(discountValue.doubleValue() >= parm.getMinFee().doubleValue()
						&& !PriceEntityConstants.PRICING_CODE_BZ.equals(parm.getPricingEntryCode()) 
						&& !PricingConstants.PACKAGE_TYPE_SALVER.equals(parm.getSubType())) {
					log.debug("折扣价格高于最低一票");
					priceDiscountDto.setReduceFee(reduceValue);
				} else {
					if(PriceEntityConstants.PRICING_CODE_SH.equals(parm.getPricingEntryCode())){
						log.debug("折扣价格低于最低一票");
						/**
						 * DMANA-5564
						 * 送货费折扣读取规则变更
						 * 折扣之后的价格小于最低一票  打折前的价格可能大于小于或者等于最低一票
						 * 当折扣前的价格小于等于最低一票则按照最低一票打折
						 */
						if(parm.getOriginnalCost().compareTo(parm.getMinFee())<=0){
							//减免费用
							reduceValue = parm.getOriginnalCost().subtract(parm.getMinFee().multiply(discountRate));
							//最低一票
							BigDecimal minFee= parm.getMinFee();
							//送货费优惠后小于最低一票，则取最低一票去优惠
							discountResult.setDiscountValue(minFee.multiply(discountRate));
							priceDiscountDto.setSaleChannelName("最低一票");
							priceDiscountDto.setDiscountRate(minFee.multiply(discountRate).divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
						}
					}else{
						//当增值服务费的类型为包装费，且包装类型为木托时,最小值为“最低一票*木托个数”.	
						if(PriceEntityConstants.PRICING_CODE_BZ.equals(parm.getPricingEntryCode()) 
								&& PricingConstants.PACKAGE_TYPE_SALVER.equals(parm.getSubType())){
							parm.setMinFee(parm.getMinFee().multiply(parm.getVolume()));
						}
						if(discountValue.compareTo(parm.getMinFee()) < 0){
							log.debug("折扣价格低于最低一票");
							reduceValue = parm.getOriginnalCost().subtract(parm.getMinFee());
							priceDiscountDto.setReduceFee(reduceValue);
							discountResult.setDiscountValue(parm.getMinFee());
							priceDiscountDto.setSaleChannelName("最低一票");
							priceDiscountDto.setDiscountRate(parm.getMinFee().divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
						}
					}
					
				}
			}
		}
		List<PriceDiscountDto> priceDiscountDtos = new ArrayList<PriceDiscountDto>(); 
		priceDiscountDtos.add(priceDiscountDto);
		 //修复pop-416 保价费率 有误
		discountResult.setDiscountRate(priceDiscountDto.getDiscountRate());
		discountResult.setDiscountPrograms(priceDiscountDtos);
		return discountResult;
	}
	/**
	 * 内部员工折扣计算
	 * @param discountResult
	 * @param parm
	 * @return
	 */
	public static DiscountResultDto calculateInempDiscountClientData(DiscountResultDto discountResult, DiscountParmDto parm) {
		//基本价格
		BigDecimal discountValue = parm.getOriginnalCost();
		//折扣率
		BigDecimal discountRate = discountResult.getDiscountRate();
		//减免折扣率
		BigDecimal reduceRate = BigDecimal.ONE.subtract(discountRate);
		//减免价格
		BigDecimal reduceValue = parm.getOriginnalCost().multiply(reduceRate);
		//减免后价格
		discountValue = discountValue.subtract(reduceValue);
		discountResult.setDiscountValue(discountValue);
		//减免方式
		discountResult.setType("DISCOUNT");
		discountResult.setOriginnalCost(parm.getOriginnalCost());
		//组装返回的数据模型
		PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
		//设置原始费用
		priceDiscountDto.setOriginnalCost(parm.getOriginnalCost());
		//组装计费类型
		priceDiscountDto.setMarketName(parm.getPricingEntryName());
		//组装打折类型CODE
		priceDiscountDto.setSaleChannelCode(DiscountTypeConstants.DISCOUNT_TYPE__INEMP);
		//组装打折类型NAME
		priceDiscountDto.setSaleChannelName(DiscountTypeConstants.DISCOUNT_TYPE__INEMP_NAME);
		//组装折扣率
		priceDiscountDto.setDiscountRate(discountRate);
		//组装计价明细ID
		priceDiscountDto.setChargeDetailId(parm.getCriteriaDetailId());
		//组装返回的折扣模型
		priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__INEMP);
		//组装返回的折扣模型
		priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__INEMP_NAME);
		//组装计费类型CODE
		priceDiscountDto.setPriceEntryCode(parm.getPricingEntryCode());
		priceDiscountDto.setPriceEntryName(parm.getPricingEntryName());
		//组装折扣ID
		priceDiscountDto.setDiscountId(discountResult.getId());		
		//公用的，放在最前面
		priceDiscountDto.setReduceFee(reduceValue);
		
		List<PriceDiscountDto> priceDiscountDtos = new ArrayList<PriceDiscountDto>(); 
		priceDiscountDtos.add(priceDiscountDto);
		discountResult.setDiscountPrograms(priceDiscountDtos);
		return discountResult;
	}
	
	/**
	 * 计算营销活动的折扣
	 * @创建时间 2014-4-21 下午7:58:54   
	 * @创建人： WangQianJin
	 */
	public static PriceDiscountDto calculateActiveDiscountClientData(String discountType,BigDecimal discountRate,
			BigDecimal caculateFee,String id,MarkActivitiesQueryConditionDto activeDto,BigDecimal crmId,String subType) {
		PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
		//折扣费率
		priceDiscountDto.setDiscountRate(discountRate);
		//活动编码
		String activeCode=activeDto.getCode();
		//活动名称
		String activeName=activeDto.getName();
		if(PriceEntityConstants.PRICING_CODE_FRT.equals(discountType)){		            			
			priceDiscountDto.setMarketName("运费");
			priceDiscountDto.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
			priceDiscountDto.setPriceEntryName("运费");
		}else if(PriceEntityConstants.PRICING_CODE_JH.equals(discountType)){
			priceDiscountDto.setMarketName("接货费");
			priceDiscountDto.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_JH);
			priceDiscountDto.setPriceEntryName("接货费");
		}else if(PriceEntityConstants.PRICING_CODE_SH.equals(discountType)){
			priceDiscountDto.setMarketName("送货费");
			priceDiscountDto.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_SH);
			priceDiscountDto.setPriceEntryName("送货费");
		}else if(PriceEntityConstants.PRICING_CODE_BF.equals(discountType)){
			priceDiscountDto.setMarketName("保价费");
			priceDiscountDto.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_BF);
			priceDiscountDto.setPriceEntryName("保价费");
		}else if(PriceEntityConstants.PRICING_CODE_BZ.equals(discountType)){
			if(DictionaryValueConstants.PACKAGE_TYPE__FRAME.equals(subType)){
				priceDiscountDto.setMarketName("包装费(木架费)");
				priceDiscountDto.setPriceEntryName("包装费(木架费)");
				priceDiscountDto.setPriceEntryCode(subType);
			}else if(DictionaryValueConstants.PACKAGE_TYPE__BOX.equals(subType)){
				priceDiscountDto.setMarketName("包装费(木箱费)");
				priceDiscountDto.setPriceEntryName("包装费(木箱费)");
				priceDiscountDto.setPriceEntryCode(subType);
			}else if(DictionaryValueConstants.PACKAGE_TYPE__SALVER.equals(subType)){
				priceDiscountDto.setMarketName("包装费(木托费)");
				priceDiscountDto.setPriceEntryName("包装费(木托费)");
				priceDiscountDto.setPriceEntryCode(subType);
			}else if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(subType)){
				priceDiscountDto.setMarketName("快递包装费");
				priceDiscountDto.setPriceEntryName("快递包装费");
				priceDiscountDto.setPriceEntryCode(subType);
			}else{
				priceDiscountDto.setMarketName("包装费");
				priceDiscountDto.setPriceEntryName("包装费");
				priceDiscountDto.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_BZ);
			}						
		}else if(PriceEntityConstants.PRICING_CODE_HK.equals(discountType)){
			priceDiscountDto.setMarketName("代收货款");
			priceDiscountDto.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_HK);
			priceDiscountDto.setPriceEntryName("代收货款");
		}else if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(discountType)){
			priceDiscountDto.setMarketName("综合信息服务费");
			priceDiscountDto.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_QT);
			priceDiscountDto.setPriceEntryName("综合信息服务费");
		}	
		priceDiscountDto.setSubType(subType);
		//减免折扣率
		BigDecimal reduceRate = BigDecimal.ONE.subtract(discountRate);
		//减免价格
		BigDecimal reduceValue = caculateFee.multiply(reduceRate);		
		priceDiscountDto.setReduceFee(reduceValue);		
		//活动编码
		priceDiscountDto.setSaleChannelCode(activeCode);
		//活动名称
		priceDiscountDto.setSaleChannelName(activeName);
		//折扣ID
		priceDiscountDto.setDiscountId(activeDto.getId());
		//折扣类型
		priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE);
		//折扣类型名称
		priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE_NAME);
		//费用明细ID
		priceDiscountDto.setChargeDetailId(id);
		//营销活动编码
		priceDiscountDto.setActiveCode(activeCode);
		//营销活动名称
		priceDiscountDto.setActiveName(activeName);
		//营销活动开始时间
		priceDiscountDto.setActiveStartTime(activeDto.getActiveStartTime());
		//营销活动结束时间
		priceDiscountDto.setActiveEndTime(activeDto.getActiveEndTime());
		//营销活动折扣相应的CRM_ID
		priceDiscountDto.setOptionsCrmId(crmId);
		return priceDiscountDto;
	}
	
	/**
	 * 校验是否可以享有市场营销活动
	 * @创建时间 2014-4-28 下午6:49:19   
	 * @创建人： WangQianJin
	 */
	public static void validateActiveDiscount(QueryBillCacilateDto queryBillCacilateDto,
			ICustomerService customerService,IMarkActivitiesService markActivitiesService) {		
		//判断市场营销活动是否为空
		if(StringUtils.isEmpty(queryBillCacilateDto.getActiveCode())
				|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(queryBillCacilateDto.getProductCode())){
			queryBillCacilateDto.setCalActiveDiscount(false);
		}else{
			MarkActivitiesQueryConditionDto activeDto=new MarkActivitiesQueryConditionDto();
			//开单品名
			activeDto.setGoodsName(queryBillCacilateDto.getGoodsName());
			if(StringUtils.isNotEmpty(queryBillCacilateDto.getDeliveryCustomerCode())){
				CustomerDto customer=customerService.queryCustInfoByCode(queryBillCacilateDto.getDeliveryCustomerCode());
				if(customer!=null){
					//一级行业
					activeDto.setFirstTrade(customer.getOneLevelIndustry());
					//二级行业
					activeDto.setSecondeTrade(customer.getTwoLevelIndustry());
				}
			}
			//是否展货
			if(null!=queryBillCacilateDto.getIsExhibitCargo()){
				activeDto.setIsExhibitionGoods(queryBillCacilateDto.getIsExhibitCargo()?FossConstants.ACTIVE:FossConstants.INACTIVE);
			}else{
				activeDto.setIsExhibitionGoods(FossConstants.INACTIVE);
			}
			
			//订单来源
			activeDto.setOrderResource(queryBillCacilateDto.getOrderChannel());
			//产品类型
			activeDto.setProductType(queryBillCacilateDto.getProductCode());
			//开展部门
			activeDto.setDevelopDeptCode(queryBillCacilateDto.getReceiveOrgCode());
			//出发外场
			activeDto.setStartOutFieldCode(queryBillCacilateDto.getLoadOrgCode());
			//到达外场
			activeDto.setArriveOutFieldCode(queryBillCacilateDto.getLastOutLoadOrgCode());	
			//发货部门
			activeDto.setLeaveAreaCode(queryBillCacilateDto.getReceiveOrgCode());
			//到达部门
			activeDto.setArriveAreaCode(queryBillCacilateDto.getCustomerPickupOrgCode());
			//开单时间
			activeDto.setBilllingTime(queryBillCacilateDto.getBillTime());
			//开单金额(纯运费)
			activeDto.setBilllingAmount(queryBillCacilateDto.getTransportFee());				
			//开单重量
			activeDto.setBilllingWeight(queryBillCacilateDto.getGoodsWeightTotal());
			//开单体积
			activeDto.setBilllingVolumn(queryBillCacilateDto.getGoodsVolumeTotal());
			//活动编码
			activeDto.setCode(queryBillCacilateDto.getActiveCode());
			//快递集中录单
			if(null!=queryBillCacilateDto.getActiveDto() && null!=queryBillCacilateDto.getActiveDto().getIsExpressFous()){
				activeDto.setIsExpressFous("Y");
			}
			//查询活动折扣信息
			MarkActivitiesQueryConditionDto result=markActivitiesService.queryMarkActivitiesInfoByCondition(activeDto);
			if(result!=null && result.getOptionList()!=null && result.getOptionList().size()>0){
				//计算市场营销活动折扣信息
				queryBillCacilateDto.setCalActiveDiscount(true);
				//快递集中录单
				if(null!=queryBillCacilateDto.getActiveDto() && null!=queryBillCacilateDto.getActiveDto().getIsExpressFous()){
				//将营销活动设置到DTO中
				queryBillCacilateDto.setActiveDto(result);
					queryBillCacilateDto.getActiveDto().setIsExpressFous("Y");
				}else{
					//将营销活动设置到DTO中
					queryBillCacilateDto.setActiveDto(result);
				}
			}else{
				queryBillCacilateDto.setCalActiveDiscount(false);
				//您选择的市场营销活动不符合条件！
				throw new BillCaculateServiceException("您选择的市场推广活动不符合条件！");
			}
		}		
	}
	
	/**
	 * 校验是否可以享有市场营销活动
	 * @创建时间 2014-4-28 下午6:49:19
	 * @创建人： WangQianJin
	 */
	public static void validateActiveDiscount(QueryBillCacilateValueAddDto queryBillCacilateDto,
			ICustomerService customerService,IMarkActivitiesService markActivitiesService) {		
		//判断市场营销活动是否为空
		if(StringUtils.isEmpty(queryBillCacilateDto.getActiveCode()) 
				|| ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(queryBillCacilateDto.getProductCode())){
			queryBillCacilateDto.setCalActiveDiscount(false);
		}else{
			MarkActivitiesQueryConditionDto activeDto=new MarkActivitiesQueryConditionDto();
			//开单品名
			activeDto.setGoodsName(queryBillCacilateDto.getGoodsName());
			if(StringUtils.isNotEmpty(queryBillCacilateDto.getDeliveryCustomerCode())){
				CustomerDto customer=customerService.queryCustInfoByCode(queryBillCacilateDto.getDeliveryCustomerCode());
				if(customer!=null){
					//一级行业
					activeDto.setFirstTrade(customer.getOneLevelIndustry());
					//二级行业
					activeDto.setSecondeTrade(customer.getTwoLevelIndustry());
				}
			}
			//订单来源
			activeDto.setOrderResource(queryBillCacilateDto.getOrderChannel());
			//产品类型
			activeDto.setProductType(queryBillCacilateDto.getProductCode());
			//开展部门
			activeDto.setDevelopDeptCode(queryBillCacilateDto.getReceiveOrgCode());
			//出发外场
			activeDto.setStartOutFieldCode(queryBillCacilateDto.getLoadOrgCode());
			//到达外场
			activeDto.setArriveOutFieldCode(queryBillCacilateDto.getLastOutLoadOrgCode());	
			//发货部门
			activeDto.setLeaveAreaCode(queryBillCacilateDto.getReceiveOrgCode());
			//到达部门
			activeDto.setArriveAreaCode(queryBillCacilateDto.getCustomerPickupOrgCode());
			//开单时间
			activeDto.setBilllingTime(queryBillCacilateDto.getBillTime());
			//开单金额(纯运费)
			activeDto.setBilllingAmount(queryBillCacilateDto.getTransportFee());				
			//开单重量
			activeDto.setBilllingWeight(queryBillCacilateDto.getGoodsWeightTotal());
			//开单体积
			activeDto.setBilllingVolumn(queryBillCacilateDto.getGoodsVolumeTotal());
			//活动编码
			activeDto.setCode(queryBillCacilateDto.getActiveCode());
			//快递集中录单
			if(null!=queryBillCacilateDto.getActiveDto()&& null!=queryBillCacilateDto.getActiveDto().getIsExpressFous()){
				activeDto.setIsExpressFous("Y");
			}
			//查询活动折扣信息
			MarkActivitiesQueryConditionDto result=markActivitiesService.queryMarkActivitiesInfoByCondition(activeDto);
			if(result!=null && result.getOptionList()!=null && result.getOptionList().size()>0){
				//计算市场营销活动折扣信息
				queryBillCacilateDto.setCalActiveDiscount(true);
				//快递集中录单
				if(null!=queryBillCacilateDto.getActiveDto() && null!=queryBillCacilateDto.getActiveDto().getIsExpressFous()){
				//将营销活动设置到DTO中
				queryBillCacilateDto.setActiveDto(result);
					queryBillCacilateDto.getActiveDto().setIsExpressFous("Y");
				}else{
					//将营销活动设置到DTO中
					queryBillCacilateDto.setActiveDto(result);
				}
			}else{
				queryBillCacilateDto.setCalActiveDiscount(false);
				//您选择的市场营销活动不符合条件！
				throw new BillCaculateServiceException("您选择的市场推广活动不符合条件！");
			}
		}		
	}
	
//	public static DiscountResultDto calculateCustomDiscountClientData(DiscountResultDto discountResult, DiscountParmDto parm) {
//		BigDecimal discountValue = parm.getOriginnalCost();
//		BigDecimal discountRate = discountResult.getDiscountRate();
//		BigDecimal reduceRate = BigDecimal.ONE.subtract(discountRate);
//		BigDecimal reduceValue = parm.getOriginnalCost().multiply(reduceRate);
//		discountValue = discountValue.subtract(reduceValue);
//		discountResult.setDiscountValue(discountValue);
//		discountResult.setType("DISCOUNT");
//		discountResult.setId(parm.getCustomCode());
//		PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
//		priceDiscountDto.setMarketName(parm.getPricingEntryName());
//		priceDiscountDto.setSaleChannelName("客户合同");
//		priceDiscountDto.setDiscountRate(discountRate);
//		priceDiscountDto.setChargeDetailId(parm.getCriteriaDetailId());
//		priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT);
//		priceDiscountDto.setPriceEntryCode(parm.getPricingEntryCode());
//		priceDiscountDto.setDiscountId(UUIDUtils.getUUID());
//		//判断折扣后的价格是否低于最低一票，如果折扣价低于最低一票，则以最低一票为最终计算价格
//		if(discountValue.doubleValue() >= parm.getMinFee().doubleValue()) {
//			log.debug("折扣价格高于最低一票");
//			priceDiscountDto.setReduceFee(reduceValue);
//		} else {
//			log.debug("折扣价格低于最低一票");
//			reduceValue = parm.getOriginnalCost().subtract(parm.getMinFee());
//			priceDiscountDto.setReduceFee(reduceValue);
//			discountResult.setDiscountValue(parm.getMinFee());
//			priceDiscountDto.setSaleChannelName("最低一票");
//			priceDiscountDto.setDiscountRate(BigDecimal.ZERO);
//		}
//		List<PriceDiscountDto> priceDiscountDtos = new ArrayList<PriceDiscountDto>(); 
//		priceDiscountDtos.add(priceDiscountDto);
//		discountResult.setDiscountPrograms(priceDiscountDtos);
//		return discountResult;
//	}
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 计算非客户折扣
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-19 上午9:35:54
	 * 
	 * @param discountResult
	 * 
	 * @param parm
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public static DiscountResultDto calculatePriceDiscountClientData(DiscountResultDto discountResult, DiscountParmDto parm) {
		BigDecimal discountValue = parm.getOriginnalCost();
		if(discountResult != null && discountResult.getId() != null) {
			BigDecimal discountRate = discountResult.getDiscountRate().divide(new BigDecimal(PricingConstants.YUTOFEN));
			BigDecimal reduceRate = BigDecimal.ONE.subtract(discountRate);
			BigDecimal reduceValue = parm.getOriginnalCost().multiply(reduceRate);
			PriceDiscountDto priceDiscountDto = discountResult.getDiscountPrograms().get(0);
			//设置原始费用
			priceDiscountDto.setOriginnalCost(parm.getOriginnalCost());
			if(StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT, discountResult.getDiscountMode())) {
				if(reduceValue.doubleValue() < (discountResult.getMinFee().doubleValue())) {
					discountValue = discountValue.subtract(discountResult.getMinFee());
					discountResult.setType("DERATE");
					discountResult.setDiscountValue(discountValue);
					discountResult.setDiscountRate(discountValue.divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
					priceDiscountDto.setReduceFee(discountResult.getMinFee());
					priceDiscountDto.setDiscountRate(discountValue.divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
				} else {
					discountValue = discountValue.subtract(reduceValue);
					discountResult.setType("DISCOUNT");
					discountResult.setDiscountValue(discountValue);
					discountResult.setDiscountRate(discountRate);
					priceDiscountDto.setReduceFee(reduceValue);
					priceDiscountDto.setDiscountRate(discountRate);
				}
			} else if (StringUtil.equals(PriceEntityConstants.PRICING_CODE_VALUEADDED, discountResult.getDiscountMode())) {
				if(reduceValue.doubleValue() < discountResult.getMinFee().doubleValue()) {
					discountValue = discountValue.subtract(discountResult.getMinFee());
					discountResult.setType("DERATE");
					discountResult.setDiscountValue(discountValue);
					discountResult.setDiscountRate(discountValue.divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
					priceDiscountDto.setReduceFee(discountResult.getMinFee());
					priceDiscountDto.setDiscountRate(discountValue.divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
				} else if (reduceValue.doubleValue() >= discountResult.getMinFee().doubleValue() 
						&& reduceValue.doubleValue() < discountResult.getMaxFee().doubleValue()) {
					discountValue = discountValue.subtract(reduceValue);
					discountResult.setType("DISCOUNT");
					discountResult.setDiscountValue(discountValue);
					discountResult.setDiscountRate(discountRate);
					priceDiscountDto.setReduceFee(reduceValue);
					priceDiscountDto.setDiscountRate(discountRate);
				} else if (reduceValue.doubleValue() > discountResult.getMaxFee().doubleValue()) {
					discountValue = discountValue.subtract(discountResult.getMaxFee());
					discountResult.setType("DERATE");
					discountResult.setDiscountValue(discountValue);
					discountResult.setDiscountRate(discountValue.divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
					priceDiscountDto.setReduceFee(discountResult.getMaxFee());
					priceDiscountDto.setDiscountRate(discountValue.divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
				}
			}
			//判断折扣后的价格是否低于最低一票，如果折扣价低于最低一票，则以最低一票为最终计算价格
			if(discountResult.getDiscountValue().doubleValue() >= parm.getMinFee().doubleValue()) {
				log.debug("折扣价格高于最低一票");
			} else {
				log.debug("折扣价格低于最低一票");
				reduceValue = parm.getOriginnalCost().subtract(parm.getMinFee());
				priceDiscountDto.setReduceFee(reduceValue);
				discountResult.setDiscountValue(parm.getMinFee());
				discountResult.setDiscountRate(parm.getMinFee().divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
				priceDiscountDto.setSaleChannelName("最低一票");
				priceDiscountDto.setDiscountRate(parm.getMinFee().divide(parm.getOriginnalCost(), 2, RoundingMode.HALF_DOWN));
			}
			List<PriceDiscountDto> priceDiscountDtos = new ArrayList<PriceDiscountDto>(); 
			priceDiscountDtos.add(priceDiscountDto);
			discountResult.setDiscountPrograms(priceDiscountDtos);
		}
		return discountResult;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 过滤适用的优惠信息
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-6 下午2:07:28
	 * 
	 * @param priceDiscountDtos
	 * 
	 * @param parm
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public static PriceDiscountDto filterValueAddClientData(List<PriceDiscountDto> priceDiscountDtos, DiscountParmDto parm) {
		List<PriceDiscountDto> list = new ArrayList<PriceDiscountDto>();
		PriceDiscountDto priceDiscountDto = null;
		if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
			/**
			 * 轮循共16种情况，顺序如下：
			 * 网点到网点、
			 * 网点到城市、
			 * 网点到区域
			 * 城市到网点、
			 * 城市到城市、
			 * 城市到区域
			 * 区域到网点、
			 * 区域到城市、
			 * 区域到区域
			 * 全网到网点、
			 * 全网到城市、
			 * 全网到区域
			 * 网点到全网、
			 * 城市到全网、
			 * 区域到全网
			 * 全网到全网
			 */
			//网点起始-网点结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：网点起始-网点结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：网点起始-网点结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			
			//网点起始-城市结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：网点起始-城市结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：网点起始-城市结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			
			//网点起始-区域结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：网点起始-区域结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：网点起始-区域结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//城市起始-网点结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：城市起始-网点结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：城市起始-网点结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//城市起始-城市结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：城市起始-城市结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：城市起始-城市结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//城市起始-区域结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：城市起始-区域结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：城市起始-区域结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//区域起始-网点结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：区域起始-网点结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：区域起始-网点结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//区域起始-城市结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：区域起始-城市结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：区域起始-城市结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//区域起始-区域结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：区域起始-区域结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：区域起始-区域结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//全网起始-网点结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：全网起始-网点结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：全网起始-网点结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//全网起始-城市结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：全网起始-城市结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：全网起始-城市结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//全网起始-区域结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：全网起始-区域结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：全网起始-区域结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//网点起始-全网结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：网点起始-全网结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：网点起始-全网结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//城市起始-全网结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：城市起始-全网结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：城市起始-全网结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//区域起始-全网结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：区域起始-全网结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：区域起始-全网结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//全网起始-全网结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
								log.debug("获取折扣原则：全网起始-全网结束, 产品相等，货物类型相等，所属行业相等");
								list.add(priceDiscountDtoj);
								break;
							} 
						//判断依据原则：产品相等，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
								log.debug("获取折扣原则：全网起始-全网结束, 产品相等，货物类型相等，所属行业为全部");
								list.add(priceDiscountDtoj);
								break;
							}
						//判断依据原则：产品相等，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品相等，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode()) 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品相等，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品为全部，货物类型相等，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品为全部，货物类型相等，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业相等
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), parm.getIndustryCode())) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品为全部，货物类型为通用，所属行业相等");
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用，所属行业为全部
						if(StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL") 
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)
								&& StringUtil.equals(priceDiscountDtoj.getPricingIndustryCode(), "ALL")) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品为全部，货物类型为通用，所属行业为全部");
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(list)) {
			priceDiscountDto = list.get(0);
		}
		return priceDiscountDto;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 过滤适用的折扣信息
	 * @author FOSSDP-sz
	 * @date 2013-2-26 下午3:33:29
	 * @param priceDiscountDtos
	 * @param parm
	 * @return
	 * @version V1.0
	 */
	public static PriceDiscountDto filterPriceDiscountClientData(List<PriceDiscountDto> priceDiscountDtos, DiscountParmDto parm) {
		List<PriceDiscountDto> list = new ArrayList<PriceDiscountDto>();
		PriceDiscountDto priceDiscountDto = null;
		if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
			/**
			 * 轮循共16种情况，顺序如下：
			 * 网点到网点、
			 * 网点到城市、
			 * 网点到区域
			 * 城市到网点、
			 * 城市到城市、
			 * 城市到区域
			 * 区域到网点、
			 * 区域到城市、
			 * 区域到区域
			 * 全网到网点、
			 * 全网到城市、
			 * 全网到区域
			 * 网点到全网、
			 * 城市到全网、
			 * 区域到全网
			 * 全网到全网
			 */
			//网点起始-网点结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品相等，货物类型相等，级别1");
							priceDiscountDtoj.setPriorityLevel(1);
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品相等，货物类型为通用，级别2");
							priceDiscountDtoj.setPriorityLevel(2);
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品为全部，货物类型相等，级别3");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_3);
							list.add(priceDiscountDtoj);
							break;
						}
						//判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：网点起始-网点结束, 产品为全部，货物类型为通用，级别4");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_4);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//网点起始-城市结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品相等，货物类型相等，级别5");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_5);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品相等，货物类型为通用，级别6");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_6);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品为全部，货物类型相等，级别7");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_7);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：网点起始-城市结束, 产品为全部，货物类型为通用，级别8");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_8);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//网点起始-区域结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品相等，货物类型相等，级别9");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_9);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品相等，货物类型为通用，级别10");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_10);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品为全部，货物类型相等，级别11");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_11);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：网点起始-区域结束, 产品为全部，货物类型为通用，级别12");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_12);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//城市起始-网点结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品相等，货物类型相等，级别13");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_13);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品相等，货物类型为通用，级别14");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_14);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品为全部，货物类型相等，级别15");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_15);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：城市起始-网点结束, 产品为全部，货物类型为通用，级别16");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_16);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//城市起始-城市结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品相等，货物类型相等，级别17");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_17);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品相等，货物类型为通用，级别18");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_18);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品为全部，货物类型相等，级别19");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_19);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：城市起始-城市结束, 产品为全部，货物类型为通用，级别20");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_20);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//城市起始-区域结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品相等，货物类型相等，级别21");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_21);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品相等，货物类型为通用，级别22");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_22);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品为全部，货物类型相等，级别23");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_23);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：城市起始-区域结束, 产品为全部，货物类型为通用，级别24");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_24);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//区域起始-网点结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品相等，货物类型相等，级别25");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_25);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品相等，货物类型为通用，级别26");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_26);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品为全部，货物类型相等，级别27");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_27);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：区域起始-网点结束, 产品为全部，货物类型为通用，级别28");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_28);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//区域起始-城市结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品相等，货物类型相等，级别29");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_29);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品相等，货物类型为通用，级别30");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_30);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品为全部，货物类型相等，级别31");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_31);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：区域起始-城市结束, 产品为全部，货物类型为通用，级别32");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_32);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//区域起始-区域结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品相等，货物类型相等，级别33");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_33);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品相等，货物类型为通用，级别34");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_34);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品为全部，货物类型相等，级别35");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_35);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：区域起始-区域结束, 产品为全部，货物类型为通用，级别36");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_36);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//全网起始-网点结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品相等，货物类型相等，级别37");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_37);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品相等，货物类型为通用，级别38");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_38);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品为全部，货物类型相等，级别39");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_39);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：全网起始-网点结束, 产品为全部，货物类型为通用，级别40");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_40);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//全网起始-城市结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					if(CollectionUtils.isNotEmpty(list) && list.size() > 0) 
						break;
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品相等，货物类型相等，级别41");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_41);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品相等，货物类型为通用，级别42");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_42);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品为全部，货物类型相等，级别43");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_43);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：全网起始-城市结束, 产品为全部，货物类型为通用，级别44");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_44);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//全网起始-区域结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品相等，货物类型相等，级别45");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_45);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品相等，货物类型为通用，级别46");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_46);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品为全部，货物类型相等，级别47");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_47);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：全网起始-区域结束, 产品为全部，货物类型为通用，级别48");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_48);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//网点起始-全网结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_DEPT, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品相等，货物类型相等，级别49");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_49);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品相等，货物类型为通用，级别50");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_50);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品为全部，货物类型相等，级别51");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_51);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：网点起始-全网结束, 产品为全部，货物类型为通用，级别52");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_52);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//城市起始-全网结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_CITY, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品相等，货物类型相等，级别53");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_53);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品相等，货物类型为通用，级别54");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_54);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品为全部，货物类型相等，级别55");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_55);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：城市起始-全网结束, 产品为全部，货物类型为通用，级别56");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_56);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//区域起始-全网结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DiscountOrgConstants.DISCOUNT_ORG_REGION, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品相等，货物类型相等，级别57");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_57);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品相等，货物类型为通用，级别58");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_58);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品为全部，货物类型相等，级别59");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_59);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：区域起始-全网结束, 产品为全部，货物类型为通用，级别60");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_60);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
			//全网起始-全网结束
			if(list.size() < 1) {
				for (int j = 0; j < priceDiscountDtos.size(); j++) {
					PriceDiscountDto priceDiscountDtoj = priceDiscountDtos.get(j);
					if(StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getDeptOrgTypeCode()) 
							&& StringUtil.equals(DictionaryValueConstants.MSG_TYPE__ALLNET, priceDiscountDtoj.getArrvOrgTypeCode())) {
						//判断依据原则：产品相等，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品相等，货物类型相等，级别61");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_61);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品相等，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), parm.getProductCode())
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品相等，货物类型为通用，级别62");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_62);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型相等
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(), parm.getGoodsTypeCode())) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品为全部，货物类型相等，级别63");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_63);
							list.add(priceDiscountDtoj);
							break;
						}
						// 判断依据原则：产品为全部，货物类型为通用
						if (StringUtil.equals(priceDiscountDtoj.getProductCode(), "ALL")
								&& StringUtil.equals(priceDiscountDtoj.getGoodsTypeCode(),
										GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001)) {
							log.debug("获取折扣原则：全网起始-全网结束, 产品为全部，货物类型为通用，级别64");
							priceDiscountDtoj.setPriorityLevel(NumericConstants.NUM_64);
							list.add(priceDiscountDtoj);
							break;
						}
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(list)) {
			priceDiscountDto = list.get(0);
		}
		return priceDiscountDto;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 比较体积重量与直接费用的折扣
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-6 上午10:41:27
	 * 
	 * @param priceDiscountDtos
	 * 
	 * @param parm
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public static DiscountResultDto comparePriceDiscount(List<PriceDiscountDto> priceDiscountDtos, DiscountParmDto parm) {
		PriceDiscountDto discountDto = null;
		if(CollectionUtils.isNotEmpty(priceDiscountDtos)) {
			List<PriceDiscountDto> volumnWeightList = new ArrayList<PriceDiscountDto>();
			List<PriceDiscountDto> costList = new ArrayList<PriceDiscountDto>();
			for (int i = 0; i < priceDiscountDtos.size(); i++) {
				PriceDiscountDto priceDiscountDto = priceDiscountDtos.get(i);
				if (StringUtils.equals(priceDiscountDto.getCaculateType(), "VOLUME")
						|| StringUtils.equals(priceDiscountDto.getCaculateType(), "WEIGHT")) {
					volumnWeightList.add(priceDiscountDto);
				} else if(StringUtils.equals(priceDiscountDto.getCaculateType(), "ORIGINALCOST")) {
					costList.add(priceDiscountDto);
				}
			}
			//筛选体积、重量折扣
			PriceDiscountDto vwDto = PriceUtil.filterPriceDiscountClientData(volumnWeightList, parm);
			//筛选费用折扣
			PriceDiscountDto ctDto = PriceUtil.filterPriceDiscountClientData(costList, parm);
			if(vwDto != null && vwDto.getPriorityLevel() > 0 && ctDto != null && ctDto.getPriorityLevel() > 0) {
				if(vwDto.getPriorityLevel() == ctDto.getPriorityLevel()) {
					discountDto = compareVWandCT(vwDto, ctDto, parm);
				} else if(vwDto.getPriorityLevel() > ctDto.getPriorityLevel()) {
					discountDto = ctDto;
				} else if(vwDto.getPriorityLevel() < ctDto.getPriorityLevel()) {
					discountDto = vwDto;
				}
			} else if(vwDto != null && ctDto == null) {
				discountDto = vwDto;
			} else if(vwDto == null && ctDto != null) {
				discountDto = ctDto;
			} 
		}
		if(discountDto == null) {
			return null;
		} else {
			return callPriceDiscountResult(discountDto);
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 体积重量与直接费用同条件比较方法
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-6 上午10:55:18
	 * 
	 * @param vwDto
	 * 
	 * @param ctDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private static PriceDiscountDto compareVWandCT(PriceDiscountDto vwDto, PriceDiscountDto ctDto, DiscountParmDto parm) {
		BigDecimal discountValue = parm.getOriginnalCost();
		// 体积重量计算折扣
		BigDecimal vmdiscountRate = vwDto.getDiscountRate().divide(new BigDecimal(PricingConstants.YUTOFEN));
		BigDecimal vmreduceRate = BigDecimal.ONE.subtract(vmdiscountRate);
		BigDecimal vmreduceValue = discountValue.multiply(vmreduceRate);
		long vmminFee = vwDto.getMinFee();
		long vmdiscountValue;
		if (vmreduceValue.longValue() >= vmminFee) {
			vmdiscountValue = vmreduceValue.longValue();
		} else {
			vmdiscountValue = vmminFee;
		}
		// 直接费用计算
		BigDecimal ctdiscountRate = ctDto.getDiscountRate().divide(new BigDecimal(PricingConstants.YUTOFEN));
		BigDecimal ctreduceRate = BigDecimal.ONE.subtract(ctdiscountRate);
		BigDecimal ctreduceValue = discountValue.multiply(ctreduceRate);
		long ctminFee = ctDto.getMinFee();
		long ctdiscountValue;

		if (ctreduceValue.longValue() >= ctminFee) {
			ctdiscountValue = ctreduceValue.longValue();
		} else {
			ctdiscountValue = ctminFee;
		}
		// 判断折扣后的价格是否低于最低一票，如果折扣价低于最低一票，则以最低一票为最终计算价格
		if (ctdiscountValue > parm.getMinFee().longValue() && vmdiscountValue <= parm.getMinFee().longValue()) {
			log.debug("直接费用折扣高于最低一票，体积重量折扣低于等于最低一票，获取体积重量折扣");
			return vwDto;
		} else if (ctdiscountValue <= parm.getMinFee().longValue() && vmdiscountValue > parm.getMinFee().longValue()) {
			log.debug("直接费用折扣低于等于最低一票，体积重量折扣高于最低一票，获取直接费用折扣");
			return ctDto;
		} else if (ctdiscountValue < parm.getMinFee().longValue() && vmdiscountValue == parm.getMinFee().longValue()) {
			log.debug("直接费用折扣低于最低一票，体积重量折扣等于最低一票，获取体积重量折扣");
			return vwDto;
		} else if (ctdiscountValue == parm.getMinFee().longValue() && vmdiscountValue < parm.getMinFee().longValue()) {
			log.debug("直接费用折扣等于最低一票，体积重量折扣低于最低一票，获取直接费用折扣");
			return ctDto;
		} else {
			if (ctdiscountValue >= vmdiscountValue) {
				log.debug("体积重量折扣与直接费用折扣都高于/低于最低一票，获取直接费用折扣");
				return ctDto;
			} else {
				log.debug("体积重量折扣与直接费用折扣都高于/低于最低一票，获取体积重量折扣");
				return vwDto;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 比较体积重量与直接费用的增值优惠（尚示启用，过渡）
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-6 下午2:03:49
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	public static DiscountResultDto compareValueAddDiscount(List<PriceDiscountDto> priceDiscountDtos, DiscountParmDto parm) {
		PriceDiscountDto priceDiscountDto = filterValueAddClientData(priceDiscountDtos, parm);
		if(priceDiscountDto != null) {
			return callPriceDiscountResult(priceDiscountDto);
		} else {
			return null;
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
		 *	
		 *	轮循共16种情况，顺序如下：
		*		1、 网点到网点
		*		
		*		判断依据原则：产品相等，货物类型相等，所属行业相等
		*		 判断依据原则：产品相等，货物类型相等，所属行业为全部
		*		判断依据原则：产品相等，货物类型为通用，所属行业相等
		*		判断依据原则：产品相等，货物类型为通用，所属行业为全部
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型相等，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业相等
		*		判断依据原则：产品为全部，货物类型为通用，所属行业为全部
		*		 
			*	2、 网点到城市
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	3、网点到区域
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	4、城市到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	5、城市到城市、
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	6、城市到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	7、区域到网点、
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	8、区域到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	9、 区域到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	10、全网到网点
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	11、全网到城市
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	12、全网到区域
			*	
			*		判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	13、 网点到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	14、城市到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	15、区域到全网
			*	
			*	 	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	 
			*	16、全网到全网
			*	
			*	判断依据原则：产品相等，货物类型相等，所属行业相等
			*	 判断依据原则：产品相等，货物类型相等，所属行业为全部
			*	判断依据原则：产品相等，货物类型为通用，所属行业相等
			*	判断依据原则：产品相等，货物类型为通用，所属行业为全部
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型相等，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业相等
			*	判断依据原则：产品为全部，货物类型为通用，所属行业为全部
			*	
		 */
	/**
	 * 
	 * @Description: 封装折扣数据
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-6 下午1:57:40
	 * 
	 * @param discountDto
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private static DiscountResultDto callPriceDiscountResult(PriceDiscountDto discountDto) {
		DiscountResultDto discountResult = new DiscountResultDto();
		discountResult.setId(discountDto.getPriceCriteriaId());
		if(discountDto.getMinFee() != null) {
			discountResult.setMinFee(new BigDecimal(discountDto.getMinFee()));
		}
		if(discountDto.getMaxFee() != null) {
			discountResult.setMaxFee(new BigDecimal(discountDto.getMaxFee()));
		}
		discountResult.setDiscountRate(discountDto.getDiscountRate());
		discountResult.setDiscountMode(getFirstLevelEntryCode(discountDto.getPriceEntryCode()));
		List<PriceDiscountDto> priceDiscountDtos = new ArrayList<PriceDiscountDto>();
		priceDiscountDtos.add(discountDto);
		discountResult.setDiscountPrograms(priceDiscountDtos);
		return discountResult;
	}
	
	/**
	 * 
	 * @Description: 增值服务相关
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-19 上午9:27:55
	 * 
	 * @param queryBillCacilateValueAddDto
	 * 
	 * @version V1.0
	 */
	public static void checkExpressQueryBillCacilateValueAddDtoDate(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		BigDecimal originnalCost = queryBillCacilateValueAddDto.getOriginnalCost();
		if (null == originnalCost
				&& StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(),
						PriceEntityConstants.PRICING_CODE_BF)) {
			return;
		}
		// 固定必输项目
		if (StringUtil.isEmpty(queryBillCacilateValueAddDto.getPricingEntryCode())) {
			throw new BillCaculateServiceException("费用代码不能为空!", "费用代码不能为空!");
		}
		if (StringUtil.isEmpty(queryBillCacilateValueAddDto.getOriginalOrgCode())) {
			throw new BillCaculateServiceException("出发部门编码不能为空!", "出发部门编码不能为空!");
		}
		Date receiveDate = queryBillCacilateValueAddDto.getReceiveDate();
		if (null == receiveDate) {
			queryBillCacilateValueAddDto.setReceiveDate(new Date());
		}
		if (null == queryBillCacilateValueAddDto.getWeight()) {
			throw new BillCaculateServiceException("重量不能为空!", "重量不能为空!");
		}
	}

	public static List<ValueAddDto> calculateDLBValueAddedServices(
			BigDecimal originnalCost, CityMarketPlanEntity cityMarketPlanEntity,QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		String subType= queryBillCacilateValueAddDto.getSubType();//子类型
		List<ValueAddDto> valueList = new ArrayList<ValueAddDto>();
		ValueAddDto valueAddDto = null;
		if(cityMarketPlanEntity==null)
		{
		    return null;
		}
		Double total = null;
		if(StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(), PriceEntityConstants.PRICING_CODE_HK)){//代收货款
			double rate = 0;
			if(StringUtil.equals(DictionaryValueConstants.COD__COD_TYPE__RETURN_1_DAY,subType)){
				rate=cityMarketPlanEntity.getR1Rate().doubleValue();
			}else if(StringUtil.equals(DictionaryValueConstants.COD__COD_TYPE__RETURN_3_DAY,subType)){
				rate=cityMarketPlanEntity.getR3Rate().doubleValue();
			}else if(StringUtil.equals(DictionaryValueConstants.COD__COD_TYPE__RETURN_APPROVE,subType)){
				rate=cityMarketPlanEntity.getRaRate().doubleValue();
			}
			double result = rate*originnalCost.doubleValue();
			total = Double.valueOf((double) Math.round((result + 2e-15) * NumericConstants.NUM_100) / NumericConstants.NUM_100);
			valueAddDto = converDLBValueaddClientData(total, cityMarketPlanEntity, queryBillCacilateValueAddDto,BigDecimal.valueOf(rate));
			valueList.add(valueAddDto);
		}else if(StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(), PriceEntityConstants.PRICING_CODE_BF)){//代收货款
			double rate = cityMarketPlanEntity.getBfRate().doubleValue();
			double result = rate*originnalCost.doubleValue();
			total = Double.valueOf((double) Math.round((result + 2e-15) * NumericConstants.NUM_100) / NumericConstants.NUM_100);
			valueAddDto = converDLBValueaddClientData(total, cityMarketPlanEntity, queryBillCacilateValueAddDto,BigDecimal.valueOf(rate));
			if (StringUtils.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(), PriceEntityConstants.PRICING_CODE_BF)) {
				valueAddDto.setDefaultBF(new BigDecimal(String.valueOf(new Double(originnalCost
						.doubleValue() / PricingConstants.YUTOFEN))));
			}
			valueList.add(valueAddDto);
		}
		return valueList;
	
	}
	
	private static ValueAddDto converDLBValueaddClientData(Double total,
			CityMarketPlanEntity cityMarketPlanEntity, QueryBillCacilateValueAddDto queryBillCacilateValueAddDto, BigDecimal rate) {
		total = total / PricingConstants.YUTOFEN;
		ValueAddDto valueAddDto = new ValueAddDto();
		valueAddDto.setCaculateFee(new BigDecimal(String.valueOf(total)));
		String caculateType = PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_ORIGINALCOST;
		String subType= queryBillCacilateValueAddDto.getSubType();//子类型
//------------需要封装		
//		if (null == priceCriteriaDetailBean.getFee()) {
//			priceCriteriaDetailBean.setFee(NumberUtils.LONG_ZERO);
//		}
		valueAddDto.setFee(BigDecimal.ZERO);
//		valueAddDto.setCaculateExpression(priceCriteriaDetailBean.getExperssion());
		valueAddDto.setCaculateType(caculateType);
		valueAddDto.setSubType(subType);

		valueAddDto.setPriceEntityCode(queryBillCacilateValueAddDto.getPricingEntryCode());
		
//		valueAddDto.setCanmodify(priceCriteriaDetailBean.getCanModify());
//		valueAddDto.setCandelete(priceCriteriaDetailBean.getCanDelete());
//		if (null == cityMarketPlanEntity.getMaxFee()) {
//			priceCriteriaDetailBean.setMaxFee(NumberUtils.LONG_ZERO);
//		}
//		if (null == cityMarketPlanEntity.getMinFee()) {
//			priceCriteriaDetailBean.setMinFee(NumberUtils.LONG_ZERO);
//		}
		valueAddDto.setMaxFee(new BigDecimal(new Double(String.valueOf(cityMarketPlanEntity.getMaxFee()
				/ PricingConstants.YUTOFEN))));
		valueAddDto.setMinFee(new BigDecimal(new Double(String.valueOf(cityMarketPlanEntity.getMinFee()
				/ PricingConstants.YUTOFEN))));
//		if (priceCriteriaDetailBean.getFee() != null) {
//			valueAddDto.setFee(new BigDecimal(new Double(String.valueOf(priceCriteriaDetailBean.getFee()
//					/ PricingConstants.YUTOFEN))));
//		}
//		valueAddDto.setProductCode(priceCriteriaDetailBean.getProductCode());
//		valueAddDto.setGoodsTypeCode(priceCriteriaDetailBean.getGoodsTypeCode());
		valueAddDto.setId(cityMarketPlanEntity.getId());
		valueAddDto.setFeeRate(rate);
		valueAddDto.setActualFeeRate(rate);
		//zxy 20131118 KDTE-5995 添加：描述Description
		valueAddDto.setDescription(cityMarketPlanEntity.getDescription());

		return valueAddDto;
	}

	public static DiscountResultDto calculateDLBDiscountClientData(
			CityMarketPlanEntity cityMarketPlanEntity,
			DiscountParmDto parm) {
		DiscountResultDto discountResult = new DiscountResultDto();
		BigDecimal discountValue = parm.getOriginnalCost();
		if(cityMarketPlanEntity != null) {
			//折扣率
			BigDecimal discountRate = cityMarketPlanEntity.getShippingdiscount();
			BigDecimal reduceRate = BigDecimal.ONE.subtract(discountRate);
			BigDecimal reduceValue = parm.getOriginnalCost().multiply(reduceRate);
			PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
			discountValue = discountValue.subtract(reduceValue);
			discountResult.setType("DISCOUNT");
			discountResult.setDiscountValue(discountValue);
			discountResult.setDiscountRate(discountRate);
			priceDiscountDto.setReduceFee(reduceValue);
			priceDiscountDto.setDiscountRate(discountRate);
			//--------参考ProductDiscount.java160行，解决保存大礼包运费折扣异常
			////设置计费ID
			//priceDiscountDto.setChargeDetailId(parm.getCriteriaDetailId());
			////设置打折ID
			//priceDiscountDto.setDiscountId(priceDiscountDto.getPriceCriteriaId());			
			//设置计费ID
			priceDiscountDto.setChargeDetailId(cityMarketPlanEntity.getId());
			//设置打折ID
			priceDiscountDto.setDiscountId(cityMarketPlanEntity.getId());
			
			//设置打折类型CODE
			//priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__PRODUCT);
			priceDiscountDto.setType(cityMarketPlanEntity.getCode());
			//设置打折类型NAME
			//priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__PRODUCT_NAME);
			priceDiscountDto.setTypeName(cityMarketPlanEntity.getName());
			//设置打折活动名称
			//priceDiscountDto.setMarketName(parm.getPricingEntryName());
			//priceDiscountDto.setPriceEntryCode(parm.getPricingEntryCode());
			//priceDiscountDto.setPriceEntryName(parm.getPricingEntryName());
			priceDiscountDto.setMarketName(parm.getPricingEntryName());
			priceDiscountDto.setPriceEntryCode(cityMarketPlanEntity.getCode());
			priceDiscountDto.setPriceEntryName(cityMarketPlanEntity.getName());
			////设置打折**名称
			//priceDiscountDto.setSaleChannelName(cityMarketPlanEntity.getName());
			List<PriceDiscountDto> priceDiscountDtos = new ArrayList<PriceDiscountDto>(); 
			priceDiscountDtos.add(priceDiscountDto);
			discountResult.setDiscountPrograms(priceDiscountDtos);
		}
		return discountResult;		
	}
	
 /**
   * 
   *
   * @Function: com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil.calculateExpressReginDiscountClientData
   * @Description:根据快递区域折扣方案   对快递运费进行打折计算  且打折计算过程中 续重最低费率  不能低于客户合同中的续重最低费率
   *
   * @return
   *
   * @version:v1.0
   * @author:DP-FOSS-YANGKANG
   * @date:2015-1-23 下午5:40:31
   *
   * Modification History:
   * Date         Author      Version     Description
   * -----------------------------------------------------------------
   * 2015-1-23    DP-FOSS-YANGKANG      v1.0.0         create
   */
  public static DiscountResultDto calculateExpressReginDiscountClientData(DiscountResultDto discountResult, DiscountParmDto parm,ProductPriceDto productPriceDto,ExpressDiscountDto resultDiscount){
	  	//打折之后的返回
	  	List<PriceDiscountDto> priceDiscountDtos = new ArrayList<PriceDiscountDto>();   
	  	//打折前费用
	    BigDecimal discountValue = parm.getOriginnalCost();
	    //首重折扣   由于存储的时候是按照*100存储 需要进行除以100的处理
	    BigDecimal firstDiscountRate = discountResult.getDiscountRate().divide(BigDecimal.valueOf(NumericConstants.NUM_100));
	    //续重折扣  由于存储的时候是按照*100存储 需要进行除以100的处理
	    BigDecimal renewalDiscountRate = discountResult.getRenewalDiscountRate().divide(BigDecimal.valueOf(NumericConstants.NUM_100));
	    
	    //获取续重区间打折之后的费率
	    BigDecimal firstDiscountFee = BigDecimal.ZERO;
	    BigDecimal secondDiscountFee = BigDecimal.ZERO;
	    if(productPriceDto.getFirstTempRate().compareTo(BigDecimal.ZERO)>0){
	    	//原续重区间费率做了*100处理  这里需要除以100
	    	firstDiscountFee = renewalDiscountRate.multiply(productPriceDto.getFirstTempRate()).divide(BigDecimal.valueOf(NumericConstants.NUM_100));
	    }
	    if(productPriceDto.getSecondTempRate().compareTo(BigDecimal.ZERO)>0){
	    	//原续重区间费率做了*100处理  这里需要除以100
	    	secondDiscountFee = renewalDiscountRate.multiply(productPriceDto.getSecondTempRate()).divide(BigDecimal.valueOf(NumericConstants.NUM_100));
	    }
	    		
	    //将打折之后的续重费率与CRM合同约定的最低费率做比较 如果小于最低费率  则取最低费率做计算
	    if(resultDiscount!=null){
	    	
	    	if(resultDiscount.getContinueHeavyLowestRate()!=null 
	    			&&firstDiscountFee.compareTo(resultDiscount.getContinueHeavyLowestRate())<0){
	    		firstDiscountFee = resultDiscount.getContinueHeavyLowestRate();
	    	}
	    	
	    	if(resultDiscount.getContinueHeavyLowestRate()!=null 
	    			&&secondDiscountFee.compareTo(resultDiscount.getContinueHeavyLowestRate())<0){
	    		secondDiscountFee = resultDiscount.getContinueHeavyLowestRate();
	    	}
      }
      
      	BigDecimal discountTotalFee = BigDecimal.ZERO;
      	//首重打折之后的费用
        BigDecimal discountFirstFee = productPriceDto.getFirstRateFee().multiply(firstDiscountRate).divide(BigDecimal.valueOf(NumericConstants.NUM_100));
        BigDecimal firstWeightFee = productPriceDto.getFirstWeight().multiply(firstDiscountFee);
        BigDecimal secondWeightFee = productPriceDto.getSecondWeight().multiply(secondDiscountFee);
        discountTotalFee =discountFirstFee.add(firstWeightFee).add(secondWeightFee);
        //重新计算优惠金额  原始费用与当前运费的差值
        BigDecimal reduceFee = BigDecimal.ZERO;
        if(discountValue.subtract(discountTotalFee).compareTo(BigDecimal.ZERO)>0){
        	reduceFee = discountValue.subtract(discountTotalFee);
        }
	    //打折后费用
	    discountResult.setDiscountValue(discountTotalFee);
	    //减免方式
	    discountResult.setType("DISCOUNT");
	    //客户编号
	    discountResult.setId(parm.getCustomCode());
	    //组装返回的数据模型
	    PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
	    //设置原始费用
	    priceDiscountDto.setOriginnalCost(parm.getOriginnalCost());
	    //组装计费类型
	    priceDiscountDto.setMarketName(parm.getPricingEntryName());
	    //组装打折类型CODE
	    priceDiscountDto.setSaleChannelCode(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT);
	    //组装打折类型NAME
	    priceDiscountDto.setSaleChannelName(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT_NAME);
	    //组装折扣率
	    priceDiscountDto.setDiscountRate(firstDiscountRate);
	    //续重折扣率
	    priceDiscountDto.setRenewalDiscountRate(renewalDiscountRate);
	    //组装计价明细ID
	    priceDiscountDto.setChargeDetailId(parm.getCriteriaDetailId());
	    //组装返回的折扣模型
	    priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT);
	    //组装返回的折扣模型
	    priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT_NAME);
	    //组装计费类型CODE
	    priceDiscountDto.setPriceEntryCode(parm.getPricingEntryCode());
	    priceDiscountDto.setPriceEntryName(parm.getPricingEntryName());
	    //组装用户合同折扣ID
	    priceDiscountDto.setDiscountId(discountResult.getId());    
	    //优惠费用
	    priceDiscountDto.setReduceFee(reduceFee);
	    
	    
	    priceDiscountDtos.add(priceDiscountDto);
	    discountResult.setDiscountPrograms(priceDiscountDtos);
	    return discountResult;
  }
  /**
   * 内部发货折扣
   */
  public static DiscountResultDto calculateExpressInempDiscountClientData(DiscountResultDto discountResult, DiscountParmDto parm,ProductPriceDto productPriceDto){
	  	//打折之后的返回
	  	List<PriceDiscountDto> priceDiscountDtos = new ArrayList<PriceDiscountDto>();   
	  	//打折前费用
	    BigDecimal discountValue = parm.getOriginnalCost();
	    //首重折扣   
	    BigDecimal firstDiscountRate = discountResult.getDiscountRate();
	    //续重折扣  
	    BigDecimal renewalDiscountRate = discountResult.getRenewalDiscountRate();
	    
	    //获取续重区间打折之后的费率
	    BigDecimal firstDiscountFee = BigDecimal.ZERO;
	    BigDecimal secondDiscountFee = BigDecimal.ZERO;
	    if(productPriceDto.getFirstTempRate().compareTo(BigDecimal.ZERO)>0){
	    	//原续重区间费率做了*100处理  这里需要除以100
	    	firstDiscountFee = renewalDiscountRate.multiply(productPriceDto.getFirstTempRate()).divide(BigDecimal.valueOf(NumericConstants.NUM_100));
	    }
	    if(productPriceDto.getSecondTempRate().compareTo(BigDecimal.ZERO)>0){
	    	//原续重区间费率做了*100处理  这里需要除以100
	    	secondDiscountFee = renewalDiscountRate.multiply(productPriceDto.getSecondTempRate()).divide(BigDecimal.valueOf(NumericConstants.NUM_100));
	    }		   
    	BigDecimal discountTotalFee = BigDecimal.ZERO;
    	//首重打折之后的费用
      BigDecimal discountFirstFee = productPriceDto.getFirstRateFee().multiply(firstDiscountRate).divide(BigDecimal.valueOf(NumericConstants.NUM_100));
      BigDecimal firstWeightFee = productPriceDto.getFirstWeight().multiply(firstDiscountFee);
      BigDecimal secondWeightFee = productPriceDto.getSecondWeight().multiply(secondDiscountFee);
      discountTotalFee =discountFirstFee.add(firstWeightFee).add(secondWeightFee);
      //重新计算优惠金额  原始费用与当前运费的差值
      BigDecimal reduceFee = BigDecimal.ZERO;
      if(discountValue.subtract(discountTotalFee).compareTo(BigDecimal.ZERO)>0){
      	reduceFee = discountValue.subtract(discountTotalFee);
      }
	    //打折后费用
	    discountResult.setDiscountValue(discountTotalFee);
	    //减免方式
	    discountResult.setType("DISCOUNT");
	    //组装返回的数据模型
	    PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
	    //设置原始费用
	    priceDiscountDto.setOriginnalCost(parm.getOriginnalCost());
	    //组装计费类型
	    priceDiscountDto.setMarketName(parm.getPricingEntryName());
	    //组装打折类型CODE
	    priceDiscountDto.setSaleChannelCode(DiscountTypeConstants.DISCOUNT_TYPE__INEMP);
	    //组装打折类型NAME
	    priceDiscountDto.setSaleChannelName(DiscountTypeConstants.DISCOUNT_TYPE__INEMP_NAME);
	    //组装折扣率
	    priceDiscountDto.setDiscountRate(firstDiscountRate);
	    //续重折扣率
	    priceDiscountDto.setRenewalDiscountRate(renewalDiscountRate);
	    //组装计价明细ID
	    priceDiscountDto.setChargeDetailId(parm.getCriteriaDetailId());
	    //组装返回的折扣模型
	    priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE__INEMP);
	    //组装返回的折扣模型
	    priceDiscountDto.setTypeName(DiscountTypeConstants.DISCOUNT_TYPE__INEMP_NAME);
	    //组装计费类型CODE
	    priceDiscountDto.setPriceEntryCode(parm.getPricingEntryCode());
	    priceDiscountDto.setPriceEntryName(parm.getPricingEntryName());
	    //组装用户合同折扣ID
	    priceDiscountDto.setDiscountId(discountResult.getId());    
	    //优惠费用
	    priceDiscountDto.setReduceFee(reduceFee);
	    
	    
	    priceDiscountDtos.add(priceDiscountDto);
	    discountResult.setDiscountPrograms(priceDiscountDtos);
	    discountResult.setOriginnalCost(parm.getOriginnalCost());
	    return discountResult;
}


    /**
     * 精准包裹的计算方法
     * @param weight 重量
     * @param volume 体积
     * @param resultList 计价方案结果Dto的集合
     * @param priceEntity 计价条目实体
     * @param isBubbleRate 是否重泡比计算
     * @param isVolumeWeightEqual 体积重量是否与重量相等
     * @return productPriceList 价格计算的最后结果
     */

    public static List<ProductPriceDto> ecGoodsCalculateCostServices(BigDecimal weight, BigDecimal volume, List<ResultProductPriceDto> resultList,ProductEntity productEntity,
                                                                     GoodsTypeEntity goodsTypeEntity, PriceEntity priceEntity,boolean isBubbleRate,boolean isVolumeWeightEqual) {

        List<ProductPriceDto> productPriceList = new ArrayList<ProductPriceDto>();

        //将重量和体积转换成double格式
        Double doubleVolume ;
        Double doubleWeight ;
        //最终的计算结果
        Double totalV = 0.0;
        Double totalW = 0.0;
        //最终的计价条目-体积
        ResultProductPriceDto resultVFinal = null;
        ResultProductPriceDto resultWFinal = null;


        if(volume!=null&&weight!=null){
            doubleVolume = volume.doubleValue();
            doubleWeight = weight.doubleValue();
            //封装方法，遍历resultList
            GetTotalVOrWClass getTotalVOrWClass = new GetTotalVOrWClass(weight, volume, resultList, isBubbleRate, doubleVolume, doubleWeight, totalV, totalW, resultVFinal, resultWFinal).invoke();
            totalV = getTotalVOrWClass.getTotalV();
            totalW = getTotalVOrWClass.getTotalW();
            resultVFinal = getTotalVOrWClass.getResultVFinal();
            resultWFinal = getTotalVOrWClass.getResultWFinal();
            //采取何种显示
            if (getTotalVOrW(priceEntity, isVolumeWeightEqual, productPriceList, totalV, totalW, resultVFinal, resultWFinal))
                return null;


            //抽取方法减少复杂度
            return getProductPriceListMethod(weight, volume, productPriceList,productEntity,goodsTypeEntity);

        }else{
            return null;
        }

    }

    //抽取方法看取哪个区间
    private static boolean getTotalVOrW(PriceEntity priceEntity, boolean isVolumeWeightEqual, List<ProductPriceDto> productPriceList, Double totalV, Double totalW, ResultProductPriceDto resultVFinal, ResultProductPriceDto resultWFinal) {
        //相同优先取体积
        if(totalV>=totalW) {
            //下面值为空说明不在范围内，反过来取，都取不到就置空
            if (resultVFinal != null) {
                if(totalV>0.0 ){
                    productPriceList.add(converPriceClientData(totalV, resultVFinal, resultVFinal.getCaculateType(), priceEntity));
                }else{
                    return true;
                }
            }else{
                if(resultWFinal != null){
                    productPriceList.add(converPriceClientData(totalW,resultWFinal,resultWFinal.getCaculateType(),priceEntity));
                }else{
                    return true;
                }
            }
        }else{
            if (resultWFinal != null) {
                if(totalW>0.0){
                    //重泡比大于等于重量，计费类型改为按照体积计费
                    if(isVolumeWeightEqual){
                        resultWFinal.setCaculateType(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
                    }
                    productPriceList.add(converPriceClientData(totalW,resultWFinal,resultWFinal.getCaculateType(),priceEntity));
                }
            }else{
                if(resultVFinal != null){
                    productPriceList.add(converPriceClientData(totalV, resultVFinal, resultVFinal.getCaculateType(), priceEntity));
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    private static List<ProductPriceDto> getProductPriceListMethod(BigDecimal weight, BigDecimal volume, List<ProductPriceDto> productPriceList,ProductEntity productEntity,
                                                                   GoodsTypeEntity goodsTypeEntity) {
        //补充数据显示
        if(CollectionUtils.isNotEmpty(productPriceList)){
            ProductPriceDto dto =productPriceList.get(0);
            // 设置产品信息
            if( productEntity!=null){
                dto.setProductCode(productEntity.getCode());
                dto.setProductName(productEntity.getName());
            }
            // 设置货物信息
            if (goodsTypeEntity != null) {
                dto.setGoodsTypeCode(goodsTypeEntity.getCode());
                dto.setGoodsTypeName(goodsTypeEntity.getName());
            }
            if(CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(dto.getCaculateType())){
                dto.setLightFeeRate(dto.getFeeRate());
                dto.setHeavyFeeRate(dto.getCaculateFee().divide(weight, 2, BigDecimal.ROUND_HALF_DOWN));
                dto.setActualFeeRate(dto.getCaculateFee().divide(volume, 2, BigDecimal.ROUND_HALF_DOWN));
            }
            if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(dto.getCaculateType())){
                dto.setHeavyFeeRate(dto.getFeeRate());
                dto.setLightFeeRate(dto.getCaculateFee().divide(volume, 2, BigDecimal.ROUND_HALF_DOWN));
                dto.setActualFeeRate(dto.getCaculateFee().divide(weight, 2, BigDecimal.ROUND_HALF_DOWN));
            }
            return productPriceList;
        }else{
            return null;
        }
    }


    //获取最终结果的类
    private static class GetTotalVOrWClass {
        private BigDecimal weight;
        private BigDecimal volume;
        private List<ResultProductPriceDto> resultList;
        private boolean isBubbleRate;
        private Double doubleVolume;
        private Double doubleWeight;
        private Double totalV;
        private Double totalW;
        private ResultProductPriceDto resultVFinal;
        private ResultProductPriceDto resultWFinal;

        public GetTotalVOrWClass(BigDecimal weight, BigDecimal volume, List<ResultProductPriceDto> resultList, boolean isBubbleRate, Double doubleVolume, Double doubleWeight, Double totalV, Double totalW, ResultProductPriceDto resultVFinal, ResultProductPriceDto resultWFinal) {
            this.weight = weight;
            this.volume = volume;
            this.resultList = resultList;
            this.isBubbleRate = isBubbleRate;
            this.doubleVolume = doubleVolume;
            this.doubleWeight = doubleWeight;
            this.totalV = totalV;
            this.totalW = totalW;
            this.resultVFinal = resultVFinal;
            this.resultWFinal = resultWFinal;
        }

        public Double getTotalV() {
            return totalV;
        }

        public Double getTotalW() {
            return totalW;
        }

        public ResultProductPriceDto getResultVFinal() {
            return resultVFinal;
        }

        public ResultProductPriceDto getResultWFinal() {
            return resultWFinal;
        }

        public GetTotalVOrWClass invoke() {
            for (ResultProductPriceDto resultProductPriceDto : resultList) {
                //获取计价方式
                String caculateType = resultProductPriceDto.getCaculateType();
                double wp;
                //按体积计费
                if (CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)) {
                    //判断是否有重泡比，有的话按照就不走体积计费
                    if(isBubbleRate){
                        continue;
                    }
                    //获取左区间和右区间
                    double tempLeftrange = resultProductPriceDto.getLeftrange();
                    double tempRightrange = resultProductPriceDto.getRightrange();
                    //封装参数为了传入方法
                    CaculateFeeDto freight = costPrepareCaculateProperty(resultProductPriceDto);
                    //首重和费率只能有一个

                    freight.setFeeRate(freight.getFixPrice() != null && freight.getFixPrice() != 0 ?
                            BigDecimal.ZERO : freight.getFeeRate());
                    //落区间三种情况,最后一种情况不需要考虑
                    if(doubleVolume>tempLeftrange&&doubleVolume<=tempRightrange){
                        resultVFinal = resultProductPriceDto;
                        //BigDecimal减法运算
                        wp = volume.subtract(BigDecimal.valueOf(tempLeftrange)).doubleValue();
                        //传入值和公式计算
                        totalV=BigDecimal.valueOf(totalV).add(BigDecimal.valueOf(new Double(PriceRuleCalculator.calcFreightDetail(wp,freight)))).doubleValue();
                    }else if(doubleVolume>=tempRightrange){
                        wp = BigDecimal.valueOf(tempRightrange).subtract(BigDecimal.valueOf(tempLeftrange)).doubleValue();
                        //传入值和公式计算
                        totalV=BigDecimal.valueOf(totalV).add(BigDecimal.valueOf(new Double(PriceRuleCalculator.calcFreightDetail(wp,freight)))).doubleValue();
                    }
                }
                //按重量计费
                if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)) {
                    //获取左区间和右区间
                    double tempLeftrange = resultProductPriceDto.getLeftrange();
                    double tempRightrange = resultProductPriceDto.getRightrange();
                    //封装参数为了传入方法
                    CaculateFeeDto freight = costPrepareCaculateProperty(resultProductPriceDto);
                    //首重和费率只能有一个
                   if(freight.getFixPrice()!=null&&freight.getFixPrice()!=0){
                        freight.setFeeRate(BigDecimal.ZERO);
                    }
                    //落区间三种情况,最后一种情况不需要考虑
                    if(doubleWeight>tempLeftrange&&doubleWeight<=tempRightrange){
                        //BigDecimal减法运算
                        wp = weight.subtract(BigDecimal.valueOf(tempLeftrange)).doubleValue();
                        //传入值和公式计算
                        totalW=BigDecimal.valueOf(totalW).add(BigDecimal.valueOf(new Double(PriceRuleCalculator.calcFreightDetail(wp,freight)))).doubleValue();
                        resultWFinal = resultProductPriceDto;
                    }else if(doubleWeight>=tempRightrange){
                        wp = BigDecimal.valueOf(tempRightrange).subtract(BigDecimal.valueOf(tempLeftrange)).doubleValue();
                        //传入值和公式计算
                        totalW=BigDecimal.valueOf(totalW).add(BigDecimal.valueOf(new Double(PriceRuleCalculator.calcFreightDetail(wp,freight)))).doubleValue();
                    }
                }
            }
            return this;
        }
    }
}
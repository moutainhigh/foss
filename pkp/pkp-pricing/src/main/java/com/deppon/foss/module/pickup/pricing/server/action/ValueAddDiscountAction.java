/**
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/ValueAddDiscountAction.java
 * 
 * FILE NAME        	: ValueAddDiscountAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IValueAddDiscountService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceDiscountVo;

/**
 * 公布价管理ACTION
 * 
 * @author 张斌
 * 
 * @date 2012-10-23
 * 
 * @version 1.0
 */
public class ValueAddDiscountAction extends AbstractAction {
    	/**
    	 * 序列化
    	 */
    	private static final long serialVersionUID = 2883644272419312426L;
    	/**
    	 * 
    	 * 日志处理 
    	 * 
    	 **/
	private static final Logger LOGGER = LoggerFactory.getLogger(ValueAddDiscountAction.class);
	/**
	 *
	 * 折扣vo
	 * 
	 */
	private PriceDiscountVo priceDiscountVo  = new PriceDiscountVo();
	/**
	 *
	 *获得折扣vo
	 * 
	 */
	public PriceDiscountVo getPriceDiscountVo() {
		return priceDiscountVo;
	}
	/**
	 *
	 * 设置折扣vo 
	 * 
	 */
	public void setPriceDiscountVo(PriceDiscountVo priceDiscountVo) {
		this.priceDiscountVo = priceDiscountVo;
	}
	/**
	 *
	 *折扣服务
	 *
	 */
	private IValueAddDiscountService valueAddDiscountService;
	/**
	 * 
	 * 注入折扣服务
	 * 
	 */
	public void setValueAddDiscountService(
			IValueAddDiscountService valueAddDiscountService) {
		this.valueAddDiscountService = valueAddDiscountService;
	}
	/**
	 * .
	 * <p>
	 * 根据条件查询折扣方案明细<br/>
	 * 方法名：selectValueAddDiscountByCodition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-12
	 * @since JDK1.6
	 */
	@JSON
	public String selectValueAddDiscountByCodition() {
		try {
			List<PriceDiscountDto> priceDiscountDtoList = valueAddDiscountService.selectPriceDiscountPagingByCodition(priceDiscountVo.getPriceDiscountDto(), start, limit);
			priceDiscountVo.setPriceDiscountDtoList(priceDiscountDtoList);
			this.setTotalCount(valueAddDiscountService.countPriceDiscountByCodition(priceDiscountVo.getPriceDiscountDto()));
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("根据条件查询折扣方案明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 根据条件查询折扣方案主信息<br/>
	 * 方法名：selectValueAddProgramByCodition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-12
	 * @since JDK1.6
	 */
	@JSON
	public String selectValueAddProgramByCodition() {
		try {
			MarketingEventEntity marketingEventEntity = new MarketingEventEntity();
			if(priceDiscountVo.getMarketingEventEntity()==null){
				marketingEventEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
			}else{
				marketingEventEntity = priceDiscountVo.getMarketingEventEntity();
				marketingEventEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
			}
			List<MarketingEventEntity> marketingEventEntityList = valueAddDiscountService.selectPriceProgramByCodition(marketingEventEntity, start, limit);
			priceDiscountVo.setMarketingEventEntityList(marketingEventEntityList);
			this.setTotalCount(valueAddDiscountService.countPriceProgramByCodition(marketingEventEntity));
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("根据条件查询折扣方案主信息: "+e.getMessage());
		    	return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 删除折扣方案主信息<br/>
	 * 方法名：deleteValueAddDiscount
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String deleteValueAddDiscount() {
		try {
			valueAddDiscountService.deleteDiscountProgram(priceDiscountVo.getPriceDiscountIds());
			return returnSuccess(MessageType.DELETE_PRICEDISCOUNT_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("删除折扣方案主信息: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 激活折扣方案<br/>
	 * 方法名：activeValueAddDiscount
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String activeValueAddDiscount() {
		try {
			valueAddDiscountService.activateDiscountProgram(priceDiscountVo.getPriceDiscountIds());
			return returnSuccess(MessageType.ACTIVE_PRICEDISCOUNT_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("激活折扣方案: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 新增折扣方案主信息<br/>
	 * 方法名：addValueAddDiscount
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String addValueAddDiscount() {
		try {
			MarketingEventEntity marketingEventEntity = new MarketingEventEntity();
			if(priceDiscountVo.getMarketingEventEntity()==null){
				marketingEventEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
			}else{
				marketingEventEntity = priceDiscountVo.getMarketingEventEntity();
				marketingEventEntity.setType(PricingConstants.VALUATION_TYPE_VALUEADD_DISCOUNT);
			}
			priceDiscountVo = valueAddDiscountService.addDiscountProgram(marketingEventEntity, priceDiscountVo.getChannelEntityList());
			return returnSuccess(MessageType.SAVE_PRICEDISCOUNT_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("新增折扣方案主信息: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 修改折扣方案主信息<br/>
	 * 方法名：updateValueAddDiscount
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String updateValueAddDiscount() {
		try {
			valueAddDiscountService.updateDiscountProgram(priceDiscountVo.getMarketingEventEntity(), priceDiscountVo.getChannelEntityList());
			return returnSuccess(MessageType.SAVE_PRICEDISCOUNT_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("修改折扣方案主信息: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 新增折扣方案明细<br/>
	 * 方法名：addValueAddDiscountProgramItem
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String addValueAddDiscountProgramItem() {
		try {
			valueAddDiscountService.addDiscountProgramItem(priceDiscountVo.getStartAllNet()
					,priceDiscountVo.getArrvAllNet(),priceDiscountVo.getStartDiscountOrgEntityList()
					, priceDiscountVo.getEndDiscountOrgEntityList(), priceDiscountVo.getPriceDiscountDto());
			return returnSuccess(MessageType.SAVE_PRICEDISCOUNTDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("新增折扣方案明细: "+e.getMessage());
		    	return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 删除折扣方案明细<br/>
	 * 方法名：deleteValueAddDiscountProgramItem
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-12
	 * @since JDK1.6
	 */
	@JSON
	public String deleteValueAddDiscountProgramItem() {
		try {
			valueAddDiscountService.deleteDiscountProgramItem(priceDiscountVo.getPriceDiscountDto().getPriceValuationId());
			return returnSuccess(MessageType.DELETE_PRICEDISCOUNTDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("删除折扣方案明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 修改折扣方案明细<br/>
	 * 方法名：updateValueAddDiscountProgramItem
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String updateValueAddDiscountProgramItem() {
		try {
			valueAddDiscountService.updateDiscountProgramItem(priceDiscountVo.getPriceDiscountDto());
			return returnSuccess(MessageType.UPDATE_PRICEDISCOUNTDETAIL_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("修改折扣方案明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询折扣主方案信息<br/>
	 * 方法名：selectValueAddMarketingByPrimaryKey
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String selectValueAddMarketingByPrimaryKey() {
		try {
			priceDiscountVo = valueAddDiscountService.selectDiscountProgram(priceDiscountVo.getMarketingEventEntity().getId());
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("查询折扣主方案信息: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询折扣方案明细(一条)<br/>
	 * 方法名：selectValueAddPriceValuation
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String selectValueAddPriceValuation() {
		try {
			PriceDiscountDto priceDiscountDto = valueAddDiscountService.selectPriceDiscountItemByValuationId(priceDiscountVo.getPriceDiscountDto().getPriceValuationId());
			priceDiscountVo.setPriceDiscountDto(priceDiscountDto);
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("查询折扣方案明细: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 升级版本<br/>
	 * 方法名：copyValueAddDiscountProgram
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String copyValueAddDiscountProgram() {
		try {
			priceDiscountVo = valueAddDiscountService.copyDiscountProgram(priceDiscountVo.getMarketingEventEntity().getId());
			return returnSuccess(MessageType.COPY_PRICEDISCOUNT_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("升级版本: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 中止版本<br/>
	 * 方法名：stopValueAddDiscountProgram
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-12
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String stopValueAddDiscountProgram() {
		try {
			valueAddDiscountService.terminateDiscountProgram(priceDiscountVo.getMarketingEventEntity().getId(), priceDiscountVo.getMarketingEventEntity().getEndTime());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("中止版本: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询所有增值服务<br/>
	 * 方法名：queryAllPricingEntries
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-12-27
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String queryAllPricingEntries() {
		try {
			List<PriceEntity> priceEntityList =  valueAddDiscountService.queryAllPricingEntries();
			priceDiscountVo.setPriceEntityList(priceEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
		    	LOGGER.error("查询所有增值服务: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 立即修改价格折扣方案状态<br/>
	 * 方法名：activateImmediatelyDiscountProgram
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2013-02-28
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String activateImmediatelyDiscountProgram() {
		try {
			 Date nowDate= new Date();
			    if(nowDate!= null && priceDiscountVo.getMarketingEventEntity().getBeginTime()!= null && nowDate.compareTo(priceDiscountVo.getMarketingEventEntity().getBeginTime())>0)
			    {
			    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
			    }
			valueAddDiscountService.activateImmediatelyDiscountProgram(priceDiscountVo.getMarketingEventEntity().getId()
					,priceDiscountVo.getMarketingEventEntity().getBeginTime());
			return returnSuccess(MessageType.ACTIVE_PRICEDISCOUNT_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("立即修改价格折扣方案状态: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 立即中止折扣方案<br/>
	 * 方法名：terminateImmediatelyDiscountProgram
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2013-02-28
	 * 
	 * @since JDK1.6
	 */
	@JSON
	public String terminateImmediatelyDiscountProgram() {
		try {
			valueAddDiscountService.terminateImmediatelyDiscountProgram(priceDiscountVo.getMarketingEventEntity().getId()
					,priceDiscountVo.getMarketingEventEntity().getEndTime());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("立即中止折扣方案: "+e.getMessage());
			return returnError(e);
		}
	}
}








/**
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
 */

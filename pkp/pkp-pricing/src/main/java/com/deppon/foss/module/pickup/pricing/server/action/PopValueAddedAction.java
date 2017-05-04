package com.deppon.foss.module.pickup.pricing.server.action;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 输入信息
 * 
 * 服务名称	服务名称	文本框		 50	否
 * 
 * 
 * 输出信息
 * 
 *  服务名称	服务名称	输出信息		80	是
 *  
 *状态	启用状态	输出信息		20	是
 *
 *最后修改人	默认当前操作人	输出信息		20	是
 *
 *修改时间	最后修改时间	输出信息		20	是
 *
 *生效日期	生效日期	输出信息		10	是
 *
 *截止日期	截止日期	输出信息		10	是
 *
 *操作列	做修改	输出信息		20	是
 *
 *长短途	长短途定义	输出信息		20	是
 *
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/PricingValueAddedAction.java
 * 
 * FILE NAME        	: PricingValueAddedAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILimitedWarrantyItemsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPopValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.pop.PriceValueAddedVo;
import com.deppon.foss.util.DateUtils;
import com.google.inject.Inject;

/**
 * 
 * <p>
 * Description: 增值服务管理ACTION<br />
 * </p>
 * @title PopValueAddedAction.java
 * @package com.deppon.foss.module.pickup.pricing.server.action 
 * @author xx
 * @version 0.1 2014年10月13日
 */
public class PopValueAddedAction extends AbstractAction {
	/**
	 * 
	 * 序列化
	 * 
	 */
	private static final long serialVersionUID = 2883644272419312426L;
	/**
	 * 
	 * 日志处理
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PricingValueAddedAction.class);
	/**
	 * 
	 * 增值服务交互对象VO
	 * 
	 */
	private PriceValueAddedVo pricingValuationVo = new PriceValueAddedVo();
	@Inject
	private ILimitedWarrantyItemsService limitedWarrantyItemsService;

	/**
	 * <p>
	 * Description:limitedWarrantyItemsService<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setLimitedWarrantyItemsService(ILimitedWarrantyItemsService limitedWarrantyItemsService) {
		this.limitedWarrantyItemsService = limitedWarrantyItemsService;
	}

	/**
	 * 
	 * <p>
	 * Description:获得增值服务交互对象VO<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @return priceValueAddedVo
	 */
	public PriceValueAddedVo getPricingValuationVo() {
		return pricingValuationVo;
	}

	/**
	 * 
	 * <p>
	 * Description:设置服务交互对象VO<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @param priceValueAddedVo
	 *            void
	 */
	public void setPricingValuationVo(PriceValueAddedVo priceValueAddedVo) {
		this.pricingValuationVo = priceValueAddedVo;
	}
	/**
	 * 
	 * <p>
	 * Description:新增增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @return String
	 */
	@JSON
	public String addValueAdded() {
		try {
			popValueAddedService.addValueAdded(pricingValuationVo.getPriceValuationEntity(), pricingValuationVo.getPriceCriteriaDetailEntityList());
			return returnSuccess(MessageType.SAVE_SUCCESS);// 保存成功
		} catch (BusinessException e) {
			LOGGER.error("新增增值服务: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * <p>
	 * Description:查询所有增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return String
	 */
	@JSON
	public String searchPricingValuation() {
		try {
			pricingValuationVo.setPriceValuationEntityList(popValueAddedService.selectByCodition(pricingValuationVo.getPriceValuationEntity(), this.getStart(),
					this.getLimit()));// 增值服务查询结果集
			this.setTotalCount(popValueAddedService.countByCodition(pricingValuationVo.getPriceValuationEntity()));// 查询总数
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询所有的增值服务: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * <p>
	 * Description:查询增值服务明细<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return String
	 */
	@JSON
	public String searchCriteriaDetailById() {
		try {
			pricingValuationVo.setPriceCriteriaDetailEntityList(popValueAddedService.selectByValueAddedId(pricingValuationVo.getValuationId()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询所有的增值服务: " + e.getMessage());
			return returnError(e);
		}
	}

	/**
	 * 
	 * <p>
	 * Description:修改增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return String
	 */
	@JSON
	public String updateValueAdded() {
		try {
			popValueAddedService.updateValueAdded(pricingValuationVo.getPriceValuationEntity(), pricingValuationVo.getPriceCriteriaDetailEntityList(),
					pricingValuationVo.getUpdatePriceCriteriaDetailEntityList(), pricingValuationVo.getDeletePriceCriteriaDetailEntityList());
			return returnSuccess(MessageType.UPDATE_SUCCESS);// 修改成功
		} catch (BusinessException e) {
			LOGGER.error("修改增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:查询增值服务类型<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String searchValueAddedType() {
		try {
			pricingValuationVo.setPriceEntityList(popValueAddedService.searchValueAddedType(pricingValuationVo.getPriceEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询增值服务类型: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:激活增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String activeValueAdded() {
		try {
			popValueAddedService.activeValueAdded(pricingValuationVo.getValueAddedIds());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);// 激活成功
		} catch (BusinessException e) {
			LOGGER.error("激活增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:立即激活激活增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	
	@JSON
	public String activeFast() {
		try {
			 Date nowDate= new Date();
			    if(nowDate!= null && pricingValuationVo.getPriceValuationEntity().getBeginTime()!= null && nowDate.compareTo(pricingValuationVo.getPriceValuationEntity().getBeginTime())>0)
			    {
			    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
			    }
			    popValueAddedService.activeFast(pricingValuationVo.getPriceValuationEntity());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);// 激活成功
		} catch (BusinessException e) {
			LOGGER.error("立即激活激活增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:删除未激活的增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String deleteValueAdded() {
		try {
			popValueAddedService.deleteValueAdded(pricingValuationVo.getValueAddedIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);// 删除成功
		} catch (BusinessException e) {
			LOGGER.error("删除未激活的增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description: 获取服务器当前时间<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String haveServerNowTime() {
		try {
			Date begintime=DateUtils.convert(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);//当天的0点00分00秒
			pricingValuationVo.setNowTime(begintime);
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("获取服务器当前时间: "+e.getMessage());
		    return returnError(e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * Description:获取默认截止时间<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String haveDefaultEndTime() {
		//根据MANA-1320修改
		try {
			Date defaultEndTime=DateUtils.convert(PricingConstants.DEFAULT_END_DATE,DateUtils.DATE_FORMAT);
			pricingValuationVo.setDefaultEndTime(defaultEndTime);
			return returnSuccess();
		} catch (BusinessException e) {
		    LOGGER.error("获取默认截止时间: "+e.getMessage());
		    return returnError(e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * Description:获取产品条目<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String findProductItemByCondiction() {
		try {
			pricingValuationVo.setProductItemEntityList(popValueAddedService.findProductItemByCondiction());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取产品条目: " + e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * <p>
	 * Description:获取基础产品<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String findProductByCondition() {
		try {
			pricingValuationVo.setProductEntityList(popValueAddedService.findProductByCondition());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取基础产品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description: 获取二级产品<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String findTwoLevelProduct() {
		try {
			pricingValuationVo.setProductEntityList(popValueAddedService.findTwoLevelProduct());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取二级产品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:获取一级产品<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String findOneLevelProduct() {
		try {
			pricingValuationVo.setProductEntityList(popValueAddedService.findOneLevelProduct());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取一级产品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:获取三级产品<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String findThreeLevelProduct() {
		try {
			pricingValuationVo.setProductEntityList(popValueAddedService.findThreeLevelProduct());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取三级产品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:获取货物类型<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String findGoodsTypeByCondiction() {
		try {
			pricingValuationVo.setGoodsTypeEntityList(popValueAddedService.findGoodsTypeByCondiction());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取货物类型: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:获取限保物品<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String queryLimitedWarrantyItemsByEntity() {
		try {
			pricingValuationVo.setLimitedWarrantyItemsEntityList(limitedWarrantyItemsService.queryLimitedWarrantyItemsByEntity(
					new LimitedWarrantyItemsEntity(), PricingConstants.ZERO, Integer.MAX_VALUE));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取限保物品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description: 修改增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String updatePriceValuation() {
		try {
			popValueAddedService.updatePriceValueAdded(pricingValuationVo.getPriceValuationEntity());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("修改计费规则: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * Description:快速中止<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * String
	 */
	@JSON
	public String stopFast() {
		try {
			Date date = new Date();
			if (pricingValuationVo.getPriceValuationEntity().getEndTime().before(date)) {
				LOGGER.error("中止时间不能小于系统当前时间！ ");
			    return returnError("中止时间不能小于系统当前时间！ ");
			}
			popValueAddedService.updatePriceValueAdded(pricingValuationVo.getPriceValuationEntity());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("快速中止: "+e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 查询所有二级行业
	 * @return
	 */
	@JSON
	public String queryAllSecProfession(){
		try{
			pricingValuationVo.setCustomerIndustryEntityList(
					popValueAddedService.queryAllSecProfession(
							pricingValuationVo.getValuationId()));
		}catch(BusinessException e){
			return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * 查询基础产品
	 */
	@JSON
	public String queryProductList(){
		try{
			pricingValuationVo.setProductEntityList(
					popValueAddedService.queryProductList(
							pricingValuationVo.getValuationId()));
		}catch(BusinessException e){
			return returnError(e);
		}
		return SUCCESS;
	}
	/**
	 * 声明增值服务
	 */
	@Inject
	private IPopValueAddedService popValueAddedService;
	/**
	 * 
	 * <p>
	 * Description:注入增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @param popValueAddedService
	 *            void
	 */
	public void setpopValueAddedService(IPopValueAddedService popValueAddedService) {
		this.popValueAddedService = popValueAddedService;
	}
}

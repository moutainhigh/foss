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
package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILimitedWarrantyItemsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICustomerValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PricingValuationVo;
import com.deppon.foss.util.DateUtils;

/**
 * 客户增值服务管理ACTION
 * 
 * @author dp-foss-dongjialing
 * 
 * 225131
 * 
 */
public class CustomerAddvaluePlanAction extends AbstractAction {
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
	private static final Logger LOGGER = LoggerFactory.getLogger(AirPricePlanAction.class);
	/**
	 * 
	 * 增值服务交互对象VO
	 * 
	 */
	private PricingValuationVo pricingValuationVo = new PricingValuationVo();
	/**
	 * 
	 * <p>获得增值服务交互对象VO</p> 
	 * @author DP-Foss-dongjialing
	   225131
	 */
	public PricingValuationVo getPricingValuationVo() {
		return pricingValuationVo;
	}
	/**
	 * 
	 * <p>设置服务交互对象VO</p> 
	 * @author DP-Foss-dongjialing
	 225131
	 */
	public void setPricingValuationVo(PricingValuationVo pricingValuationVo) {
		this.pricingValuationVo = pricingValuationVo;
	}
	/**
	 * 
	 * <p>声明增值服务</p> 
	 * @author DP-Foss-dongjialing
	   225131
	 */
	private ICustomerValueAddedService customerValueAddedService;
	
	public void setCustomerValueAddedService(
			ICustomerValueAddedService customerValueAddedService) {
		this.customerValueAddedService = customerValueAddedService;
	}
	/**
	 * 
	 * 声明限保物品服务
	 * 
	 */
	private ILimitedWarrantyItemsService limitedWarrantyItemsService;
	/**
	 * 
	 * 注入限保物品服务 
	 * 
	 */
	public void setLimitedWarrantyItemsService(
			ILimitedWarrantyItemsService limitedWarrantyItemsService) {
		this.limitedWarrantyItemsService = limitedWarrantyItemsService;
	}
	/**
	 * .
	 * <p>
	 * 查询所有的增值服务<br/>
	 * 方法名：searchPricingValuation
	 * </p>
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String searchPricingValuation() {
		try {
			pricingValuationVo.setPriceValuationEntityList(customerValueAddedService.selectByCodition(pricingValuationVo.getPriceValuationEntity(),
					this.getStart(), this.getLimit()));// 增值服务查询结果集
			this.setTotalCount(customerValueAddedService.countByCodition(pricingValuationVo.getPriceValuationEntity()));// 查询总数
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询所有的增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询计价方式明细<br/>
	 * 方法名：searchCriteriaDetailById
	 * </p>
	 * 
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String searchCriteriaDetailById() {
		try {
			/**
			 * 修改action层代码，增加编码判断
			 * @author:218371-foss-zhaoyanjun
			 * @author:
			 */
			pricingValuationVo.setPriceCriteriaDetailEntityList(customerValueAddedService.selectByValuationId(pricingValuationVo.getValuationId()));
			if("ZQBZ".equals(pricingValuationVo.getPriceEntity().getCode())){
				for(PriceCriteriaDetailEntity priceCriteriaDetailEntity:pricingValuationVo.getPriceCriteriaDetailEntityList()){
					List<PriceFibelPaperPackingEntity> priceFibelPaperPackingEntityList=customerValueAddedService.selectByValuationIdAndCodeTrue(pricingValuationVo.getValuationId());
					for(PriceFibelPaperPackingEntity priceFibelPaperPackingEntity:priceFibelPaperPackingEntityList){
						priceCriteriaDetailEntity.setPriceFibelPaperPackingEntity(priceFibelPaperPackingEntity);
					}
				}
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询所有的增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 新增增值服务<br/>
	 * 方法名：addValueAdded
	 * </p>
	 * 
     dp-foss-dongjialing
     225131
	 */
	@JSON
	public String addValueAdded() {
		try {
			customerValueAddedService.addValueAdded(pricingValuationVo.getPriceValuationEntity(), pricingValuationVo.getPriceCriteriaDetailEntityList());
			return returnSuccess(MessageType.SAVE_SUCCESS);// 保存成功
		} catch (BusinessException e) {
			LOGGER.error("新增增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 修改增值服务<br/>
	 * 方法名：updateValueAdded
	 * </p>
	 * 
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String updateValueAdded() {
		try {
			customerValueAddedService.updateValueAdded(pricingValuationVo.getPriceValuationEntity(), 
					pricingValuationVo.getPriceCriteriaDetailEntityList(),
					pricingValuationVo.getUpdatePriceCriteriaDetailEntityList(),
					pricingValuationVo.getDeletePriceCriteriaDetailEntityList());
			return returnSuccess(MessageType.UPDATE_SUCCESS);// 修改成功
		} catch (BusinessException e) {
			LOGGER.error("修改增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询增值服务类型<br/>
	 * 方法名：searchValueAddedType
	 * </p>
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String searchValueAddedType() {
		try {
			pricingValuationVo.setPriceEntityList(customerValueAddedService.searchValueAddedType(pricingValuationVo.getPriceEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("查询增值服务类型: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * (激活增值服务)
	 * </p>
	 dp-foss-dongjialing
	 225131
	 
	 */
	@JSON
	public String activeValueAdded() {
		try {
			customerValueAddedService.activeValueAdded(pricingValuationVo.getValuationIds());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);// 激活成功
		} catch (BusinessException e) {
			LOGGER.error("激活增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * (立即激活激活增值服务)
	 * </p>
	 * 
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String activeFast() {
		try {
			 Date nowDate= new Date();
			    if(nowDate!= null && pricingValuationVo.getPriceValuationEntity().getBeginTime()!= null && nowDate.compareTo(pricingValuationVo.getPriceValuationEntity().getBeginTime())>0)
			    {
			    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
			    }
			    customerValueAddedService.activeFast(pricingValuationVo.getPriceValuationEntity());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);// 激活成功
		} catch (BusinessException e) {
			LOGGER.error("立即激活激活增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * (删除未激活的增值服务)
	 * </p>
	 * 
	dp-foss-dongjialing
	225131
	 */
	@JSON
	public String deleteValueAdded() {
		try {
			customerValueAddedService.deleteValueAdded(pricingValuationVo.getValuationIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);// 删除成功
		} catch (BusinessException e) {
			LOGGER.error("删除未激活的增值服务: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 获取服务器当前时间
	 * </p>
	 dp-foss-dongjialing
	 225131
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
	 * 获取默认截止时间
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-3-14 下午5:33:09
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
	 * 获取产品条目
	 * </p>
	 * 
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String findProductItemByCondiction() {
		try {
			pricingValuationVo.setProductItemEntityList(customerValueAddedService.findProductItemByCondiction());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取产品条目: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 获取基础产品
	 * </p>
	 * 
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String findProductByCondition() {
		try {
			pricingValuationVo.setProductEntityList(customerValueAddedService.findProductByCondition());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取基础产品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 获取二级产品
	 * </p>
	 * 
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String findTwoLevelProduct() {
		try {
			pricingValuationVo.setProductEntityList(customerValueAddedService.findTwoLevelProduct());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取二级产品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 获取一级产品
	 * </p>
	dp-foss-dongjialing
	225131
	 */
	@JSON
	public String findOneLevelProduct() {
		try {
			pricingValuationVo.setProductEntityList(customerValueAddedService.findOneLevelProduct());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取一级产品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 获取三级产品
	 * </p>
	 * 
	 * dp-foss-dongjialing
	 * 225131
	 */
	@JSON
	public String findThreeLevelProduct() {
		try {
			pricingValuationVo.setProductEntityList(customerValueAddedService.findThreeLevelProduct());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取三级产品: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 获取货物类型
	 * </p>
	dp-foss-dongjialing
	225131
	 */
	@JSON
	public String findGoodsTypeByCondiction() {
		try {
			pricingValuationVo.setGoodsTypeEntityList(customerValueAddedService.findGoodsTypeByCondiction());
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("获取货物类型: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 获取限保物品
	 * </p>
	 * dp-foss-dongjialing
	 * 225131
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
	 * 修改计费规则
	 * </p>
	 * 
	dp-foss-dongjialing
	225131
	 */
	@JSON
	public String updatePriceValuation() {
		try {
			customerValueAddedService.updatePriceValuation(pricingValuationVo.getPriceValuationEntity());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("修改计费规则: " + e.getMessage());
			return returnError(e);
		}
	}
	/**
	 * 
	 * <p>
	 * 快速中止
	 * </p>
	 dp-foss-dongjialing
	 225131
	 */
	@JSON
	public String stopFast() {
		try {
			Date date = new Date();
			if (pricingValuationVo.getPriceValuationEntity().getEndTime().before(date)) {
				LOGGER.error("中止时间不能小于系统当前时间！ ");
			    return returnError("中止时间不能小于系统当前时间！ ");
			}
			customerValueAddedService.updatePriceValuation(pricingValuationVo.getPriceValuationEntity());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
		    	LOGGER.error("快速中止: "+e.getMessage());
			return returnError(e);
		}
	}
}

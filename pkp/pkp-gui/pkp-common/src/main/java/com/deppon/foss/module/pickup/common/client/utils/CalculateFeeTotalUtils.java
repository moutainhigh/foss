/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/CalculateFeeTotalUtils.java
 * 
 * FILE NAME        	: CalculateFeeTotalUtils.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 计算总费用公共类
 * 
 * @author 025000-FOSS-helong
 * @date 2012-11-28 上午11:00:17
 */
public class CalculateFeeTotalUtils {
	// log object
	private static final Log LOG = LogFactory.getLog(CalculateFeeTotalUtils.class);

	
	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:35:20
	 */
	public static void calculateFee(WaybillPanelVo bean) {
		//清空包装费
		calculateFee(bean, false);
		                   
	}
	
	

	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * 清空包装费额外功能
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:35:20
	 */
	public static void calculateFee(WaybillPanelVo bean, boolean cleanPackageFee) {
		calculateFee(bean, cleanPackageFee, false);
		            
	}
	
	
	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * 清空包装费额外功能
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:35:20
	 */
	public static void calculateFee(WaybillPanelVo bean, boolean cleanPackageFee, boolean needMinusCoupen) {
		
			// 重新赋值是因为添加装卸费需要重新计算价格
			BigDecimal transportFee = bean.getTransportFee();			
			
			if(transportFee==null){
				transportFee = BigDecimal.ZERO;
			}	
			
			// 公布价运费
			if(bean.isAccurateCost()){
				transportFee = transportFee.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
			}else{
				transportFee = transportFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			}
			
			bean.setTransportFeeCanvas(transportFee.toString());
	
			// 增值费
			BigDecimal incrementFee = calculateIncrement(bean, cleanPackageFee);
			incrementFee = incrementFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			
	
			bean.setTransportFee(transportFee);
			bean.setValueAddFee(incrementFee);
			//优惠费用
			//bean.setCouponFree(bean.getPromotionsFee());
			calculateTotalFee(bean,needMinusCoupen);
		
		                   
	}
	/**
	 * 
	 * 计算增值费用
	 * 清空包装费额外功能
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 下午08:16:23
	 */
	private static BigDecimal calculateIncrement(WaybillPanelVo bean , boolean cleanPackageFee) {
		BigDecimal incrementFee = BigDecimal.ZERO;// 增值服务费
		BigDecimal deliveryGoodsFee = bean.getDeliveryGoodsFee();// 送货费
		if(bean.getProductCode() == null){
			throw new BusinessException("产品类型不允许为空~！");
		}
		//快递送货费优化 
//		if(deliveryGoodsFee==null || 
//				ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(bean.getProductCode().getCode())||
//				ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(bean.getProductCode().getCode())
//				){
//			deliveryGoodsFee=BigDecimal.ZERO;
//		}
		BigDecimal packageFee = bean.getPackageFee();// 包装费
		if(packageFee==null){
			packageFee=BigDecimal.ZERO;
		}
		BigDecimal insuranceFee = bean.getInsuranceFee();// 保价费
		if(insuranceFee==null){
			insuranceFee=BigDecimal.ZERO;
		}
		BigDecimal codFee = bean.getCodFee();// 代收手续费
		if(codFee==null){
			codFee=BigDecimal.ZERO;
		}
		BigDecimal pickUpFee = bean.getPickupFee();// 接货费
		if(pickUpFee==null){
			pickUpFee=BigDecimal.ZERO;
		}
		/*
		BigDecimal supportFee = bean.getSupportFee(); //保价手续费（修改后的）
		if(supportFee == null){
			supportFee = BigDecimal.ZERO ;
		}
		BigDecimal collectingFee = bean.getCollectingFee();//代收手续费(修改后的)
		if(collectingFee == null){
			collectingFee = BigDecimal.ZERO ;
		}
		*/
		BigDecimal otherFee = bean.getOtherFee();// 其他费用合计、
		if(otherFee==null){
			otherFee=BigDecimal.ZERO;
		}
		packageFee = calculateIncrementPackageFee(bean, cleanPackageFee,
				packageFee);

		// 增值服务费=送货费+包装费+保价费+代收手续费+接货费+其他费用合计
		/*if(BZPartnersJudge.IS_PARTENER && !"Y".equals(bean.getIsExpress())){
			incrementFee = deliveryGoodsFee.add(packageFee).add(supportFee)
					.add(collectingFee).add(pickUpFee).add(otherFee);
		}else{*/
			incrementFee = deliveryGoodsFee.add(packageFee).add(insuranceFee)
					.add(codFee).add(pickUpFee).add(otherFee);
			
//		}
		LOG.info("incrementFee fee" +incrementFee);
		return incrementFee;
	}

	private static BigDecimal calculateIncrementPackageFee(WaybillPanelVo bean,
			boolean cleanPackageFee, BigDecimal packageFee) {
		BigDecimal calculatePackageFee = BigDecimal.ZERO;//计算得到的包装费 

		// ===========lianhe/增加非木包装费/2017年1月4日/start=======
		//判断非木包装费是否为空
		if (bean.getNonWoodPackingAmount() == null) {
			calculatePackageFee = BigDecimal.ZERO;
		} else {
			calculatePackageFee = calculatePackageFee.add(bean.getNonWoodPackingCharge());
		}
		
		// ===========lianhe/增加非木包装费/2017年1月4日/end=======
		
		if (bean.getStandGoodsNum() ==null || bean.getStandGoodsNum()<= 0) {
			calculatePackageFee = calculatePackageFee.add(BigDecimal.ZERO);
		}else{
			// 包装费=输入包装费+木架费+木箱费
			if(bean.getStandCharge()!=null){
				calculatePackageFee = calculatePackageFee.add(bean.getStandCharge());
			}
		}
		
		if (bean.getBoxGoodsNum()==null || bean.getBoxGoodsNum() <=0) {
			calculatePackageFee = calculatePackageFee.add(BigDecimal.ZERO);
		}else{
			if(bean.getBoxCharge()!=null){
				calculatePackageFee = calculatePackageFee.add(bean.getBoxCharge());
			}
		}
		
		//zxy 20131118 ISSUE-4391 start 新增：打木托费用计算到包装费中
		if (bean.getSalverGoodsNum()==null || bean.getSalverGoodsNum() <=0) {
			calculatePackageFee = calculatePackageFee.add(BigDecimal.ZERO);
		}else{
			if(bean.getSalverGoodsCharge()!=null){
				calculatePackageFee = calculatePackageFee.add(bean.getSalverGoodsCharge());
			}
		}
		//zxy 20131118 ISSUE-4391 end 新增：打木托费用计算到包装费中
		
		/**
		 * 增加纸纤总价
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-12-4
		 */
		if(bean.getPackingTotle()==null||bean.getPackingTotle().doubleValue()<=0){
			calculatePackageFee = calculatePackageFee.add(BigDecimal.ZERO);
		}else{
			calculatePackageFee = calculatePackageFee.add(bean.getPackingTotle());
		}
		//POP-446，包装费打折逻辑有问题，最新确认，包装费是不可以手动修改的。如果可以手动修改，这段逻辑要修改
		//原有问题，营业部开单，包装费的总费用是没有打折的是折前费用，而不是折后费用
		//包装费的显示金额= 计算金额 + 原来输入框里面的手写值 
		if(FossConstants.YES.equals(bean.getIsExpress()) || bean.getPickupCentralized()){
			//如果是快递，需要添加手动包装费的逻辑
			if(!cleanPackageFee){//不清空包装费
				BigDecimal packageFeeTmp = packageFee.add(calculatePackageFee);
				if(packageFeeTmp.doubleValue()<calculatePackageFee.doubleValue()){
					packageFee = calculatePackageFee;
				}else{
					packageFee = packageFeeTmp;
				}
			}else{//清空包装费
				packageFee=calculatePackageFee;
			}
		}else{
			packageFee=calculatePackageFee;//直接设置为折后费用
		}
		bean.setPackageFee(packageFee.setScale(0, BigDecimal.ROUND_HALF_UP));

		// 计算值
		bean.setCalculatedPackageFee(calculatePackageFee.setScale(0, BigDecimal.ROUND_HALF_UP));
		bean.setPackageFeeCanvas(packageFee.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		return packageFee;
	}
	
	/**
	 * 
	 * 计算增值费用
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-31 下午08:16:23
	 */
	public static void calculateTotalFee(WaybillPanelVo bean){
		calculateTotalFee(bean, false);
	}
	
	
	/**
	 * 
	 * 计算总费用
	 * @author 025000-FOSS-helong
	 * @date 2013-2-18 上午10:16:18
	 * @param bean
	 */
	public static void calculateTotalFee(WaybillPanelVo bean, boolean needMinusCoupen){
		
		
			BigDecimal	transportFee=bean.getTransportFee();
			BigDecimal	incrementFee=bean.getValueAddFee();
			
			/**
			 * 判断公布价运费是否为空，如果为空，则设置为0
			 */
			if(transportFee==null){
				transportFee = BigDecimal.ZERO;
			}
			/**
			 * 判断增值费用是否为空，如果为空，则设置为0
			 */
			if(incrementFee==null){
				incrementFee = BigDecimal.ZERO;
			}
			/**
			 * 判断代收货款是否为空，如果为空，则设置为0
			 */
			BigDecimal codAmout = bean.getCodAmount();
			if(codAmout==null){					
				codAmout = BigDecimal.ZERO;
			}
			/**
			 * 判断最低一票是否为空，如果为空，则设置为0
			 */
			BigDecimal minTransportFee= bean.getMinTransportFee();
			if(minTransportFee==null){
				minTransportFee = BigDecimal.ZERO;
			}
			
			// 优惠费用
			BigDecimal couponFree = bean.getCouponFree();
			
			/**
			 * 若冲减类型为运费时，才可以对运费进行冲减
			 * MANA-1961 营销活动与优惠券编码关联
			 * 2014-04-10 026123
			 */
			if(couponFree!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(bean.getCouponRankType())){
				couponFree = couponFree.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			}else{
				couponFree=BigDecimal.ZERO;
			}
			
			transportFee = calculateTransportFeeExp(bean, needMinusCoupen,transportFee, couponFree);
			bean.setTransportFee(transportFee);

			if(transportFee == null){
				transportFee = BigDecimal.ZERO;
			}
			/**
			 * 总运费=公布价运费+增值服务费
			 */
			BigDecimal totalFee = transportFee.add(incrementFee);
			/**
			 * 如果总运费小于0，赋值为0
			 */
			//快递上门发货优惠
			if(bean.getProductCode() != null){
				if(CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode())
						&& WaybillConstants.YES.equals(bean.getFlagCalFee())){				
					if(BigDecimal.ZERO.compareTo(totalFee)>0)
					{
						throw new BusinessException("冲减后的运费不能小于0");
					}
				}
			}
			
			try{
			if(bean.getPaidMethod()!=null){
				if (WaybillConstants.ARRIVE_PAYMENT.equals(bean.getPaidMethod()
						.getValueCode())) {
					
					totalFee = totalFee.add(codAmout);
					// 总费用
					bean.setTotalFee(totalFee);
					bean.setTotalFeeCanvas(totalFee.toString());
					//画布-总费用
					bean.setTotalFeeCanvas(totalFee.toString());
					// 到付金额
					bean.setToPayAmount(totalFee);
					// 预付金额
					bean.setPrePayAmount(BigDecimal.ZERO);
				} else {
					// 预付金额
					bean.setPrePayAmount(totalFee);
					// 总金额加上代收货款
					totalFee = totalFee.add(codAmout);
					// 总费用
					bean.setTotalFee(totalFee);
					bean.setTotalFeeCanvas(totalFee.toString());
					bean.setToPayAmount(codAmout);
				}
			}else{
				// 预付金额
				bean.setPrePayAmount(totalFee);
	 			// 总金额加上代收货款
				totalFee = totalFee.add(bean.getCodAmount());
				// 总费用
				bean.setTotalFee(totalFee);
				bean.setTotalFeeCanvas(totalFee.toString());
				bean.setToPayAmount(bean.getCodAmount());
			}
			}catch(Exception e)
			{
				//to do nothing
			}
			LOG.info("result setTotalFee" +totalFee);
		
		
	}



	private static BigDecimal calculateTransportFeeExp(WaybillPanelVo bean,
			boolean needMinusCoupen, BigDecimal transportFee,
			BigDecimal couponFree) {
		/**
		 * 公布价运费需要减去优惠费用
		 */
		if(needMinusCoupen && !FossConstants.YES.equals(bean.getFlagTakeCoupon())){
			transportFee=transportFee.subtract(couponFree);		
		}
		
		if(transportFee!=null && transportFee.doubleValue()<0){
			//小件只能把运费冲减到零
			if(bean.getProductCode()!= null && (CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode()))){
				//Online-定价体系优化项目DJTWO-175-DJTWO-251
				if(needMinusCoupen && !FossConstants.YES.equals(bean.getFlagTakeCoupon())){
					transportFee = BigDecimal.ZERO;
				}
			}
		}

		/**
		 * 不是内部发货才和最低一票比较
		 * dp-foss-dongjialing
		 * 225131
		 */
		if(bean.getInternalDeliveryType()==null || StringUtil.isBlank(bean.getInternalDeliveryType().getValueCode())
				|| StringUtil.isBlank(bean.getEmployeeNo()))
		{				

		if(transportFee!=null){
			if(bean.getProductCode() != null && !CommonUtils.directDetermineIsExpressByProductCode(bean.getProductCode().getCode())){
				////针对存在冲减才判断--Online-定价体系优化项目DJTWO-175-DJTWO-251
//					if(needMinusCoupen && !FossConstants.YES.equals(bean.getFlagTakeCoupon()) && bean.getTransportFee().compareTo(minTransportFee) > 0){
//						//运费金额-优惠券金额 < 最低一票
//						if(transportFee.compareTo(minTransportFee) < 0){
//							//零担只能把运费冲减到最小一票
//							transportFee = minTransportFee;
//						}
//					}
				//RFOSS2015052801（DN201505260016）  取消优惠券最低一票校验  --206860
				if(needMinusCoupen && !FossConstants.YES.equals(bean.getFlagTakeCoupon())){
					//当运费小于0，将运费修改为0
					if(transportFee.compareTo(BigDecimal.ZERO) < 0){
						transportFee = BigDecimal.ZERO;
					}
				}
			}	
		 }
		}
		return transportFee;
	}

	/**
	 * 四舍五入取整数
	 * @author WangQianJin
	 * @date 2013-5-27 下午6:31:26
	 */
	public static BigDecimal formatNumberInteger(BigDecimal number){
		BigDecimal result=number;
		if(number!=null && number.doubleValue()>0){
			result = number.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		return result;
	}
	
	/**
	 * 四舍五入保留两位小数
	 * @author WangQianJin
	 * @date 2013-5-27 下午6:31:26
	 */
	public static BigDecimal formatNumberTwoDecimal(BigDecimal number){
		BigDecimal result=number;
		if(number!=null && number.doubleValue()>0){
			result = number.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		return result;
	}
	
	/**
	 * 四舍五入取整数
	 * @author WangQianJin
	 * @date 2013-5-27 下午6:31:26
	 */
	public static String formatNumberInteger(String number){
		String result=number;		
		if(number!=null && !"".equals(result)){
			BigDecimal decimal=new BigDecimal(number);
			if(decimal.doubleValue()>0){
				result = decimal.setScale(0, BigDecimal.ROUND_HALF_UP).toString(); // 四舍五入
			}			
		}
		return result;
	}
	
	/**
	 * 四舍五入保留两位小数
	 * @author WangQianJin
	 * @date 2013-5-27 下午6:31:26
	 */
	public static String formatNumberTwoDecimal(String number){
		String result=number;		
		if(number!=null && !"".equals(result)){
			BigDecimal decimal=new BigDecimal(number);
			if(decimal.doubleValue()>0){
				result = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString(); // 四舍五入
			}			
		}
		return result;
	}
	
	/**
	 * 
	 * 重新计算增值费用
	 * @author WangQianJin
	 * @date 2013-08-05
	 */
	private static BigDecimal resetCalculateIncrement(WaybillPanelVo bean) {
		BigDecimal incrementFee = BigDecimal.ZERO;// 增值服务费
		BigDecimal deliveryGoodsFee = bean.getDeliveryGoodsFee();// 送货费
		if(deliveryGoodsFee==null){
			deliveryGoodsFee=BigDecimal.ZERO;
		}
		BigDecimal packageFee = bean.getPackageFee();// 包装费
		if(packageFee==null){
			packageFee=BigDecimal.ZERO;
		}
		BigDecimal insuranceFee = bean.getInsuranceFee();// 保价费
		if(insuranceFee==null){
			insuranceFee=BigDecimal.ZERO;
		}
		BigDecimal codFee = bean.getCodFee();// 代收手续费
		if(codFee==null){
			codFee=BigDecimal.ZERO;
		}
		BigDecimal pickUpFee = bean.getPickupFee();// 接货费
		if(pickUpFee==null){
			pickUpFee=BigDecimal.ZERO;
		}
		BigDecimal otherFee = bean.getOtherFee();// 其他费用合计
		if(otherFee==null){
			otherFee=BigDecimal.ZERO;
		}
		// 增值服务费=送货费+包装费+保价费+代收手续费+接货费+其他费用合计
		incrementFee = deliveryGoodsFee.add(packageFee).add(insuranceFee)
				.add(codFee).add(pickUpFee).add(otherFee);
		LOG.info("incrementFee fee" +incrementFee);
		return incrementFee;
	}
	
	/**
	 * 
	 * 重新计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * @author WangQianJin
	 * @date 2013-08-05
	 */
	public static void resetCalculateFee(WaybillPanelVo bean) {
		
		// 重新赋值是因为添加装卸费需要重新计算价格
		BigDecimal transportFee = bean.getTransportFee();			
		
		if(transportFee==null){
			transportFee = BigDecimal.ZERO;
		}	
		if(bean.isAccurateCost()){
			// 公布价运费
			transportFee = transportFee.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		else{
			// 公布价运费
			transportFee = transportFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		bean.setTransportFeeCanvas(transportFee.toString());

		// 增值费
		BigDecimal incrementFee = resetCalculateIncrement(bean);
		incrementFee = incrementFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		

		bean.setTransportFee(transportFee);
		bean.setValueAddFee(incrementFee);

		calculateTotalFee(bean,false);
		
		                   
	}
	
	/**
	 * 处理增值优惠券费用
	 * @创建时间 2014-5-1 上午10:41:52   
	 * @创建人： WangQianJin
	 */
	public static void calculateIncrementFeeCoupon(WaybillPanelVo bean) {
		// 增值费
		BigDecimal incrementFee = resetCalculateIncrement(bean);
		// 四舍五入
		incrementFee = incrementFee.setScale(0, BigDecimal.ROUND_HALF_UP);
		bean.setValueAddFee(incrementFee);		                   
	}
	
	/**
	 * 清空优惠券费用
	 * @创建时间 2014-5-1 下午2:24:53   
	 * @创建人： WangQianJin
	 */
	public static void cleanCouponFree(WaybillPanelVo bean) {
		bean.setCouponFree(BigDecimal.ZERO);	
	}

}
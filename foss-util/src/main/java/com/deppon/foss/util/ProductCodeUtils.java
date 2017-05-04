package com.deppon.foss.util;

import org.apache.commons.lang.StringUtils;

public class ProductCodeUtils {
	// 快递产品-标准快递
	public static final String PRICING_PRODUCT_EXPRESS_PACKAGE = "PACKAGE";// PACKAGE
	// 快递产品-3.60 特惠件
	public static final String PRICING_PRODUCT_ROUND_COUPON_PACKAGE = "RCP";
	// 快递产品 电商尊享
	public static final String ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE = "EPEP";
	// 快递商务件（航空）
	public static final String PRICING_PRODUCT_EXPRESS_AIR_FREIGHT = "DEAP";
	/**
	 * 判断运单运输性质是否为快递（PACKAGE、RCP、EPEP、DEAP）
	 * @author Foss-程道林
	 * @date 2015-07-20
	 * @param productCode 运单运输性质
	 * @return boolean 判断结果
	 */
	public static boolean isExpress(String productCode) {
		if (StringUtils.isBlank(productCode)){  //判断传入参数是否为空
			return false; //如果空，返回false
		}else{//不空，返回比较结果
			return productCode.equals(PRICING_PRODUCT_EXPRESS_PACKAGE)
					|| productCode.equals(PRICING_PRODUCT_ROUND_COUPON_PACKAGE)
					|| productCode.equals(ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE)
					|| productCode.equals(PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
		}
	}
}
